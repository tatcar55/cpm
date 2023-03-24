/*     */ package com.google.zxing.pdf417.encoder;
/*     */ 
/*     */ import com.google.zxing.WriterException;
/*     */ import com.google.zxing.common.CharacterSetECI;
/*     */ import java.math.BigInteger;
/*     */ import java.nio.charset.Charset;
/*     */ import java.nio.charset.CharsetEncoder;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ final class PDF417HighLevelEncoder
/*     */ {
/*     */   private static final int TEXT_COMPACTION = 0;
/*     */   private static final int BYTE_COMPACTION = 1;
/*     */   private static final int NUMERIC_COMPACTION = 2;
/*     */   private static final int SUBMODE_ALPHA = 0;
/*     */   private static final int SUBMODE_LOWER = 1;
/*     */   private static final int SUBMODE_MIXED = 2;
/*     */   private static final int SUBMODE_PUNCTUATION = 3;
/*     */   private static final int LATCH_TO_TEXT = 900;
/*     */   private static final int LATCH_TO_BYTE_PADDED = 901;
/*     */   private static final int LATCH_TO_NUMERIC = 902;
/*     */   private static final int SHIFT_TO_BYTE = 913;
/*     */   private static final int LATCH_TO_BYTE = 924;
/*     */   private static final int ECI_USER_DEFINED = 925;
/*     */   private static final int ECI_GENERAL_PURPOSE = 926;
/*     */   private static final int ECI_CHARSET = 927;
/* 115 */   private static final byte[] TEXT_MIXED_RAW = new byte[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 38, 13, 9, 44, 58, 35, 45, 46, 36, 47, 43, 37, 42, 61, 94, 0, 32, 0, 0, 0 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   private static final byte[] TEXT_PUNCTUATION_RAW = new byte[] { 59, 60, 62, 64, 91, 92, 93, 95, 96, 126, 33, 13, 9, 44, 58, 10, 45, 46, 36, 47, 34, 124, 42, 40, 41, 63, 123, 125, 39, 0 };
/*     */ 
/*     */ 
/*     */   
/* 126 */   private static final byte[] MIXED = new byte[128];
/* 127 */   private static final byte[] PUNCTUATION = new byte[128];
/*     */   
/* 129 */   private static final Charset DEFAULT_ENCODING = Charset.forName("ISO-8859-1");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 136 */     Arrays.fill(MIXED, (byte)-1); byte i;
/* 137 */     for (i = 0; i < TEXT_MIXED_RAW.length; i = (byte)(i + 1)) {
/* 138 */       byte b = TEXT_MIXED_RAW[i];
/* 139 */       if (b > 0) {
/* 140 */         MIXED[b] = i;
/*     */       }
/*     */     } 
/* 143 */     Arrays.fill(PUNCTUATION, (byte)-1);
/* 144 */     for (i = 0; i < TEXT_PUNCTUATION_RAW.length; i = (byte)(i + 1)) {
/* 145 */       byte b = TEXT_PUNCTUATION_RAW[i];
/* 146 */       if (b > 0) {
/* 147 */         PUNCTUATION[b] = i;
/*     */       }
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String encodeHighLevel(String msg, Compaction compaction, Charset encoding) throws WriterException {
/* 166 */     StringBuilder sb = new StringBuilder(msg.length());
/*     */     
/* 168 */     if (encoding == null) {
/* 169 */       encoding = DEFAULT_ENCODING;
/* 170 */     } else if (!DEFAULT_ENCODING.equals(encoding)) {
/* 171 */       CharacterSetECI eci = CharacterSetECI.getCharacterSetECIByName(encoding.name());
/* 172 */       if (eci != null) {
/* 173 */         encodingECI(eci.getValue(), sb);
/*     */       }
/*     */     } 
/*     */     
/* 177 */     int len = msg.length();
/* 178 */     int p = 0;
/* 179 */     int textSubMode = 0;
/*     */ 
/*     */     
/* 182 */     if (compaction == Compaction.TEXT) {
/* 183 */       encodeText(msg, p, len, sb, textSubMode);
/*     */     }
/* 185 */     else if (compaction == Compaction.BYTE) {
/* 186 */       byte[] bytes = msg.getBytes(encoding);
/* 187 */       encodeBinary(bytes, p, bytes.length, 1, sb);
/*     */     }
/* 189 */     else if (compaction == Compaction.NUMERIC) {
/* 190 */       sb.append('Ά');
/* 191 */       encodeNumeric(msg, p, len, sb);
/*     */     } else {
/*     */       
/* 194 */       int encodingMode = 0;
/* 195 */       while (p < len) {
/* 196 */         int n = determineConsecutiveDigitCount(msg, p);
/* 197 */         if (n >= 13) {
/* 198 */           sb.append('Ά');
/* 199 */           encodingMode = 2;
/* 200 */           textSubMode = 0;
/* 201 */           encodeNumeric(msg, p, n, sb);
/* 202 */           p += n; continue;
/*     */         } 
/* 204 */         int t = determineConsecutiveTextCount(msg, p);
/* 205 */         if (t >= 5 || n == len) {
/* 206 */           if (encodingMode != 0) {
/* 207 */             sb.append('΄');
/* 208 */             encodingMode = 0;
/* 209 */             textSubMode = 0;
/*     */           } 
/* 211 */           textSubMode = encodeText(msg, p, t, sb, textSubMode);
/* 212 */           p += t; continue;
/*     */         } 
/* 214 */         int b = determineConsecutiveBinaryCount(msg, p, encoding);
/* 215 */         if (b == 0) {
/* 216 */           b = 1;
/*     */         }
/* 218 */         byte[] bytes = msg.substring(p, p + b).getBytes(encoding);
/* 219 */         if (bytes.length == 1 && encodingMode == 0) {
/*     */           
/* 221 */           encodeBinary(bytes, 0, 1, 0, sb);
/*     */         } else {
/*     */           
/* 224 */           encodeBinary(bytes, 0, bytes.length, encodingMode, sb);
/* 225 */           encodingMode = 1;
/* 226 */           textSubMode = 0;
/*     */         } 
/* 228 */         p += b;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 234 */     return sb.toString();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int encodeText(CharSequence msg, int startpos, int count, StringBuilder sb, int initialSubmode) {
/* 253 */     StringBuilder tmp = new StringBuilder(count);
/* 254 */     int submode = initialSubmode;
/* 255 */     int idx = 0;
/*     */     while (true) {
/* 257 */       char ch = msg.charAt(startpos + idx);
/* 258 */       switch (submode) {
/*     */         case 0:
/* 260 */           if (isAlphaUpper(ch)) {
/* 261 */             if (ch == ' ') {
/* 262 */               tmp.append('\032'); break;
/*     */             } 
/* 264 */             tmp.append((char)(ch - 65));
/*     */             break;
/*     */           } 
/* 267 */           if (isAlphaLower(ch)) {
/* 268 */             submode = 1;
/* 269 */             tmp.append('\033'); continue;
/*     */           } 
/* 271 */           if (isMixed(ch)) {
/* 272 */             submode = 2;
/* 273 */             tmp.append('\034');
/*     */             continue;
/*     */           } 
/* 276 */           tmp.append('\035');
/* 277 */           tmp.append((char)PUNCTUATION[ch]);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 1:
/* 283 */           if (isAlphaLower(ch)) {
/* 284 */             if (ch == ' ') {
/* 285 */               tmp.append('\032'); break;
/*     */             } 
/* 287 */             tmp.append((char)(ch - 97));
/*     */             break;
/*     */           } 
/* 290 */           if (isAlphaUpper(ch)) {
/* 291 */             tmp.append('\033');
/* 292 */             tmp.append((char)(ch - 65));
/*     */             break;
/*     */           } 
/* 295 */           if (isMixed(ch)) {
/* 296 */             submode = 2;
/* 297 */             tmp.append('\034');
/*     */             continue;
/*     */           } 
/* 300 */           tmp.append('\035');
/* 301 */           tmp.append((char)PUNCTUATION[ch]);
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case 2:
/* 307 */           if (isMixed(ch)) {
/* 308 */             tmp.append((char)MIXED[ch]); break;
/*     */           } 
/* 310 */           if (isAlphaUpper(ch)) {
/* 311 */             submode = 0;
/* 312 */             tmp.append('\034'); continue;
/*     */           } 
/* 314 */           if (isAlphaLower(ch)) {
/* 315 */             submode = 1;
/* 316 */             tmp.append('\033');
/*     */             continue;
/*     */           } 
/* 319 */           if (startpos + idx + 1 < count) {
/* 320 */             char next = msg.charAt(startpos + idx + 1);
/* 321 */             if (isPunctuation(next)) {
/* 322 */               submode = 3;
/* 323 */               tmp.append('\031');
/*     */               continue;
/*     */             } 
/*     */           } 
/* 327 */           tmp.append('\035');
/* 328 */           tmp.append((char)PUNCTUATION[ch]);
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/* 333 */           if (isPunctuation(ch)) {
/* 334 */             tmp.append((char)PUNCTUATION[ch]); break;
/*     */           } 
/* 336 */           submode = 0;
/* 337 */           tmp.append('\035');
/*     */           continue;
/*     */       } 
/*     */       
/* 341 */       idx++;
/* 342 */       if (idx >= count) {
/*     */         break;
/*     */       }
/*     */     } 
/* 346 */     char h = Character.MIN_VALUE;
/* 347 */     int len = tmp.length();
/* 348 */     for (int i = 0; i < len; i++) {
/* 349 */       boolean odd = (i % 2 != 0);
/* 350 */       if (odd) {
/* 351 */         h = (char)(h * 30 + tmp.charAt(i));
/* 352 */         sb.append(h);
/*     */       } else {
/* 354 */         h = tmp.charAt(i);
/*     */       } 
/*     */     } 
/* 357 */     if (len % 2 != 0) {
/* 358 */       sb.append((char)(h * 30 + 29));
/*     */     }
/* 360 */     return submode;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void encodeBinary(byte[] bytes, int startpos, int count, int startmode, StringBuilder sb) {
/* 379 */     if (count == 1 && startmode == 0) {
/* 380 */       sb.append('Α');
/*     */     } else {
/* 382 */       boolean sixpack = (count % 6 == 0);
/* 383 */       if (sixpack) {
/* 384 */         sb.append('Μ');
/*     */       } else {
/* 386 */         sb.append('΅');
/*     */       } 
/*     */     } 
/*     */     
/* 390 */     int idx = startpos;
/*     */     
/* 392 */     if (count >= 6) {
/* 393 */       char[] chars = new char[5];
/* 394 */       while (startpos + count - idx >= 6) {
/* 395 */         long t = 0L; int j;
/* 396 */         for (j = 0; j < 6; j++) {
/* 397 */           t <<= 8L;
/* 398 */           t += (bytes[idx + j] & 0xFF);
/*     */         } 
/* 400 */         for (j = 0; j < 5; j++) {
/* 401 */           chars[j] = (char)(int)(t % 900L);
/* 402 */           t /= 900L;
/*     */         } 
/* 404 */         for (j = chars.length - 1; j >= 0; j--) {
/* 405 */           sb.append(chars[j]);
/*     */         }
/* 407 */         idx += 6;
/*     */       } 
/*     */     } 
/*     */     
/* 411 */     for (int i = idx; i < startpos + count; i++) {
/* 412 */       int ch = bytes[i] & 0xFF;
/* 413 */       sb.append((char)ch);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void encodeNumeric(String msg, int startpos, int count, StringBuilder sb) {
/* 418 */     int idx = 0;
/* 419 */     StringBuilder tmp = new StringBuilder(count / 3 + 1);
/* 420 */     BigInteger num900 = BigInteger.valueOf(900L);
/* 421 */     BigInteger num0 = BigInteger.valueOf(0L);
/* 422 */     while (idx < count) {
/* 423 */       tmp.setLength(0);
/* 424 */       int len = Math.min(44, count - idx);
/* 425 */       String part = '1' + msg.substring(startpos + idx, startpos + idx + len);
/* 426 */       BigInteger bigint = new BigInteger(part);
/*     */       do {
/* 428 */         tmp.append((char)bigint.mod(num900).intValue());
/* 429 */         bigint = bigint.divide(num900);
/* 430 */       } while (!bigint.equals(num0));
/*     */ 
/*     */       
/* 433 */       for (int i = tmp.length() - 1; i >= 0; i--) {
/* 434 */         sb.append(tmp.charAt(i));
/*     */       }
/* 436 */       idx += len;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isDigit(char ch) {
/* 442 */     return (ch >= '0' && ch <= '9');
/*     */   }
/*     */   
/*     */   private static boolean isAlphaUpper(char ch) {
/* 446 */     return (ch == ' ' || (ch >= 'A' && ch <= 'Z'));
/*     */   }
/*     */   
/*     */   private static boolean isAlphaLower(char ch) {
/* 450 */     return (ch == ' ' || (ch >= 'a' && ch <= 'z'));
/*     */   }
/*     */   
/*     */   private static boolean isMixed(char ch) {
/* 454 */     return (MIXED[ch] != -1);
/*     */   }
/*     */   
/*     */   private static boolean isPunctuation(char ch) {
/* 458 */     return (PUNCTUATION[ch] != -1);
/*     */   }
/*     */   
/*     */   private static boolean isText(char ch) {
/* 462 */     return (ch == '\t' || ch == '\n' || ch == '\r' || (ch >= ' ' && ch <= '~'));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int determineConsecutiveDigitCount(CharSequence msg, int startpos) {
/* 473 */     int count = 0;
/* 474 */     int len = msg.length();
/* 475 */     int idx = startpos;
/* 476 */     if (idx < len) {
/* 477 */       char ch = msg.charAt(idx);
/* 478 */       while (isDigit(ch) && idx < len) {
/* 479 */         count++;
/* 480 */         idx++;
/* 481 */         if (idx < len) {
/* 482 */           ch = msg.charAt(idx);
/*     */         }
/*     */       } 
/*     */     } 
/* 486 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int determineConsecutiveTextCount(CharSequence msg, int startpos) {
/* 497 */     int len = msg.length();
/* 498 */     int idx = startpos;
/* 499 */     while (idx < len) {
/* 500 */       char ch = msg.charAt(idx);
/* 501 */       int numericCount = 0;
/* 502 */       while (numericCount < 13 && isDigit(ch) && idx < len) {
/* 503 */         numericCount++;
/* 504 */         idx++;
/* 505 */         if (idx < len) {
/* 506 */           ch = msg.charAt(idx);
/*     */         }
/*     */       } 
/* 509 */       if (numericCount >= 13) {
/* 510 */         return idx - startpos - numericCount;
/*     */       }
/* 512 */       if (numericCount > 0) {
/*     */         continue;
/*     */       }
/*     */       
/* 516 */       ch = msg.charAt(idx);
/*     */ 
/*     */       
/* 519 */       if (!isText(ch)) {
/*     */         break;
/*     */       }
/* 522 */       idx++;
/*     */     } 
/* 524 */     return idx - startpos;
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
/*     */   
/*     */   private static int determineConsecutiveBinaryCount(String msg, int startpos, Charset encoding) throws WriterException {
/* 537 */     CharsetEncoder encoder = encoding.newEncoder();
/* 538 */     int len = msg.length();
/* 539 */     int idx = startpos;
/* 540 */     while (idx < len) {
/* 541 */       char ch = msg.charAt(idx);
/* 542 */       int numericCount = 0;
/*     */       
/* 544 */       while (numericCount < 13 && isDigit(ch)) {
/* 545 */         numericCount++;
/*     */         
/* 547 */         int i = idx + numericCount;
/* 548 */         if (i >= len) {
/*     */           break;
/*     */         }
/* 551 */         ch = msg.charAt(i);
/*     */       } 
/* 553 */       if (numericCount >= 13) {
/* 554 */         return idx - startpos;
/*     */       }
/* 556 */       ch = msg.charAt(idx);
/*     */       
/* 558 */       if (!encoder.canEncode(ch)) {
/* 559 */         throw new WriterException("Non-encodable character detected: " + ch + " (Unicode: " + ch + ')');
/*     */       }
/* 561 */       idx++;
/*     */     } 
/* 563 */     return idx - startpos;
/*     */   }
/*     */   
/*     */   private static void encodingECI(int eci, StringBuilder sb) throws WriterException {
/* 567 */     if (eci >= 0 && eci < 900) {
/* 568 */       sb.append('Ο');
/* 569 */       sb.append((char)eci);
/* 570 */     } else if (eci < 810900) {
/* 571 */       sb.append('Ξ');
/* 572 */       sb.append((char)(eci / 900 - 1));
/* 573 */       sb.append((char)(eci % 900));
/* 574 */     } else if (eci < 811800) {
/* 575 */       sb.append('Ν');
/* 576 */       sb.append((char)(810900 - eci));
/*     */     } else {
/* 578 */       throw new WriterException("ECI number not in valid range from 0..811799, but was " + eci);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\pdf417\encoder\PDF417HighLevelEncoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */