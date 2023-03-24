/*     */ package com.google.zxing.qrcode.decoder;
/*     */ 
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.common.BitSource;
/*     */ import com.google.zxing.common.CharacterSetECI;
/*     */ import com.google.zxing.common.DecoderResult;
/*     */ import com.google.zxing.common.StringUtils;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DecodedBitStreamParser
/*     */ {
/*  45 */   private static final char[] ALPHANUMERIC_CHARS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', '$', '%', '*', '+', '-', '.', '/', ':' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int GB2312_SUBSET = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static DecoderResult decode(byte[] bytes, Version version, ErrorCorrectionLevel ecLevel, Map<DecodeHintType, ?> hints) throws FormatException {
/*  60 */     BitSource bits = new BitSource(bytes);
/*  61 */     StringBuilder result = new StringBuilder(50);
/*  62 */     List<byte[]> byteSegments = (List)new ArrayList<>(1);
/*  63 */     int symbolSequence = -1;
/*  64 */     int parityData = -1;
/*     */     try {
/*     */       Mode mode;
/*  67 */       CharacterSetECI currentCharacterSetECI = null;
/*  68 */       boolean fc1InEffect = false;
/*     */ 
/*     */       
/*     */       do {
/*  72 */         if (bits.available() < 4) {
/*     */           
/*  74 */           mode = Mode.TERMINATOR;
/*     */         } else {
/*  76 */           mode = Mode.forBits(bits.readBits(4));
/*     */         } 
/*  78 */         if (mode == Mode.TERMINATOR)
/*  79 */           continue;  if (mode == Mode.FNC1_FIRST_POSITION || mode == Mode.FNC1_SECOND_POSITION) {
/*     */           
/*  81 */           fc1InEffect = true;
/*  82 */         } else if (mode == Mode.STRUCTURED_APPEND) {
/*  83 */           if (bits.available() < 16) {
/*  84 */             throw FormatException.getFormatInstance();
/*     */           }
/*     */ 
/*     */           
/*  88 */           symbolSequence = bits.readBits(8);
/*  89 */           parityData = bits.readBits(8);
/*  90 */         } else if (mode == Mode.ECI) {
/*     */           
/*  92 */           int value = parseECIValue(bits);
/*  93 */           currentCharacterSetECI = CharacterSetECI.getCharacterSetECIByValue(value);
/*  94 */           if (currentCharacterSetECI == null) {
/*  95 */             throw FormatException.getFormatInstance();
/*     */           
/*     */           }
/*     */         }
/*  99 */         else if (mode == Mode.HANZI) {
/*     */           
/* 101 */           int subset = bits.readBits(4);
/* 102 */           int countHanzi = bits.readBits(mode.getCharacterCountBits(version));
/* 103 */           if (subset == 1) {
/* 104 */             decodeHanziSegment(bits, result, countHanzi);
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 109 */           int count = bits.readBits(mode.getCharacterCountBits(version));
/* 110 */           if (mode == Mode.NUMERIC) {
/* 111 */             decodeNumericSegment(bits, result, count);
/* 112 */           } else if (mode == Mode.ALPHANUMERIC) {
/* 113 */             decodeAlphanumericSegment(bits, result, count, fc1InEffect);
/* 114 */           } else if (mode == Mode.BYTE) {
/* 115 */             decodeByteSegment(bits, result, count, currentCharacterSetECI, (Collection<byte[]>)byteSegments, hints);
/* 116 */           } else if (mode == Mode.KANJI) {
/* 117 */             decodeKanjiSegment(bits, result, count);
/*     */           } else {
/* 119 */             throw FormatException.getFormatInstance();
/*     */           }
/*     */         
/*     */         }
/*     */       
/* 124 */       } while (mode != Mode.TERMINATOR);
/* 125 */     } catch (IllegalArgumentException iae) {
/*     */       
/* 127 */       throw FormatException.getFormatInstance();
/*     */     } 
/*     */     
/* 130 */     return new DecoderResult(bytes, result
/* 131 */         .toString(), 
/* 132 */         byteSegments.isEmpty() ? null : byteSegments, (ecLevel == null) ? null : ecLevel
/* 133 */         .toString(), symbolSequence, parityData);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void decodeHanziSegment(BitSource bits, StringBuilder result, int count) throws FormatException {
/* 145 */     if (count * 13 > bits.available()) {
/* 146 */       throw FormatException.getFormatInstance();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 151 */     byte[] buffer = new byte[2 * count];
/* 152 */     int offset = 0;
/* 153 */     while (count > 0) {
/*     */       
/* 155 */       int twoBytes = bits.readBits(13);
/* 156 */       int assembledTwoBytes = twoBytes / 96 << 8 | twoBytes % 96;
/* 157 */       if (assembledTwoBytes < 959) {
/*     */         
/* 159 */         assembledTwoBytes += 41377;
/*     */       } else {
/*     */         
/* 162 */         assembledTwoBytes += 42657;
/*     */       } 
/* 164 */       buffer[offset] = (byte)(assembledTwoBytes >> 8 & 0xFF);
/* 165 */       buffer[offset + 1] = (byte)(assembledTwoBytes & 0xFF);
/* 166 */       offset += 2;
/* 167 */       count--;
/*     */     } 
/*     */     
/*     */     try {
/* 171 */       result.append(new String(buffer, "GB2312"));
/* 172 */     } catch (UnsupportedEncodingException ignored) {
/* 173 */       throw FormatException.getFormatInstance();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void decodeKanjiSegment(BitSource bits, StringBuilder result, int count) throws FormatException {
/* 181 */     if (count * 13 > bits.available()) {
/* 182 */       throw FormatException.getFormatInstance();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 187 */     byte[] buffer = new byte[2 * count];
/* 188 */     int offset = 0;
/* 189 */     while (count > 0) {
/*     */       
/* 191 */       int twoBytes = bits.readBits(13);
/* 192 */       int assembledTwoBytes = twoBytes / 192 << 8 | twoBytes % 192;
/* 193 */       if (assembledTwoBytes < 7936) {
/*     */         
/* 195 */         assembledTwoBytes += 33088;
/*     */       } else {
/*     */         
/* 198 */         assembledTwoBytes += 49472;
/*     */       } 
/* 200 */       buffer[offset] = (byte)(assembledTwoBytes >> 8);
/* 201 */       buffer[offset + 1] = (byte)assembledTwoBytes;
/* 202 */       offset += 2;
/* 203 */       count--;
/*     */     } 
/*     */     
/*     */     try {
/* 207 */       result.append(new String(buffer, "SJIS"));
/* 208 */     } catch (UnsupportedEncodingException ignored) {
/* 209 */       throw FormatException.getFormatInstance();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void decodeByteSegment(BitSource bits, StringBuilder result, int count, CharacterSetECI currentCharacterSetECI, Collection<byte[]> byteSegments, Map<DecodeHintType, ?> hints) throws FormatException {
/*     */     String encoding;
/* 220 */     if (8 * count > bits.available()) {
/* 221 */       throw FormatException.getFormatInstance();
/*     */     }
/*     */     
/* 224 */     byte[] readBytes = new byte[count];
/* 225 */     for (int i = 0; i < count; i++) {
/* 226 */       readBytes[i] = (byte)bits.readBits(8);
/*     */     }
/*     */     
/* 229 */     if (currentCharacterSetECI == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 235 */       encoding = StringUtils.guessEncoding(readBytes, hints);
/*     */     } else {
/* 237 */       encoding = currentCharacterSetECI.name();
/*     */     } 
/*     */     try {
/* 240 */       result.append(new String(readBytes, encoding));
/* 241 */     } catch (UnsupportedEncodingException ignored) {
/* 242 */       throw FormatException.getFormatInstance();
/*     */     } 
/* 244 */     byteSegments.add(readBytes);
/*     */   }
/*     */   
/*     */   private static char toAlphaNumericChar(int value) throws FormatException {
/* 248 */     if (value >= ALPHANUMERIC_CHARS.length) {
/* 249 */       throw FormatException.getFormatInstance();
/*     */     }
/* 251 */     return ALPHANUMERIC_CHARS[value];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void decodeAlphanumericSegment(BitSource bits, StringBuilder result, int count, boolean fc1InEffect) throws FormatException {
/* 259 */     int start = result.length();
/* 260 */     while (count > 1) {
/* 261 */       if (bits.available() < 11) {
/* 262 */         throw FormatException.getFormatInstance();
/*     */       }
/* 264 */       int nextTwoCharsBits = bits.readBits(11);
/* 265 */       result.append(toAlphaNumericChar(nextTwoCharsBits / 45));
/* 266 */       result.append(toAlphaNumericChar(nextTwoCharsBits % 45));
/* 267 */       count -= 2;
/*     */     } 
/* 269 */     if (count == 1) {
/*     */       
/* 271 */       if (bits.available() < 6) {
/* 272 */         throw FormatException.getFormatInstance();
/*     */       }
/* 274 */       result.append(toAlphaNumericChar(bits.readBits(6)));
/*     */     } 
/*     */     
/* 277 */     if (fc1InEffect)
/*     */     {
/* 279 */       for (int i = start; i < result.length(); i++) {
/* 280 */         if (result.charAt(i) == '%') {
/* 281 */           if (i < result.length() - 1 && result.charAt(i + 1) == '%') {
/*     */             
/* 283 */             result.deleteCharAt(i + 1);
/*     */           } else {
/*     */             
/* 286 */             result.setCharAt(i, '\035');
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void decodeNumericSegment(BitSource bits, StringBuilder result, int count) throws FormatException {
/* 297 */     while (count >= 3) {
/*     */       
/* 299 */       if (bits.available() < 10) {
/* 300 */         throw FormatException.getFormatInstance();
/*     */       }
/* 302 */       int threeDigitsBits = bits.readBits(10);
/* 303 */       if (threeDigitsBits >= 1000) {
/* 304 */         throw FormatException.getFormatInstance();
/*     */       }
/* 306 */       result.append(toAlphaNumericChar(threeDigitsBits / 100));
/* 307 */       result.append(toAlphaNumericChar(threeDigitsBits / 10 % 10));
/* 308 */       result.append(toAlphaNumericChar(threeDigitsBits % 10));
/* 309 */       count -= 3;
/*     */     } 
/* 311 */     if (count == 2) {
/*     */       
/* 313 */       if (bits.available() < 7) {
/* 314 */         throw FormatException.getFormatInstance();
/*     */       }
/* 316 */       int twoDigitsBits = bits.readBits(7);
/* 317 */       if (twoDigitsBits >= 100) {
/* 318 */         throw FormatException.getFormatInstance();
/*     */       }
/* 320 */       result.append(toAlphaNumericChar(twoDigitsBits / 10));
/* 321 */       result.append(toAlphaNumericChar(twoDigitsBits % 10));
/* 322 */     } else if (count == 1) {
/*     */       
/* 324 */       if (bits.available() < 4) {
/* 325 */         throw FormatException.getFormatInstance();
/*     */       }
/* 327 */       int digitBits = bits.readBits(4);
/* 328 */       if (digitBits >= 10) {
/* 329 */         throw FormatException.getFormatInstance();
/*     */       }
/* 331 */       result.append(toAlphaNumericChar(digitBits));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int parseECIValue(BitSource bits) throws FormatException {
/* 336 */     int firstByte = bits.readBits(8);
/* 337 */     if ((firstByte & 0x80) == 0)
/*     */     {
/* 339 */       return firstByte & 0x7F;
/*     */     }
/* 341 */     if ((firstByte & 0xC0) == 128) {
/*     */       
/* 343 */       int secondByte = bits.readBits(8);
/* 344 */       return (firstByte & 0x3F) << 8 | secondByte;
/*     */     } 
/* 346 */     if ((firstByte & 0xE0) == 192) {
/*     */       
/* 348 */       int secondThirdBytes = bits.readBits(16);
/* 349 */       return (firstByte & 0x1F) << 16 | secondThirdBytes;
/*     */     } 
/* 351 */     throw FormatException.getFormatInstance();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\qrcode\decoder\DecodedBitStreamParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */