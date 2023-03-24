/*     */ package com.google.zxing.common;
/*     */ 
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import java.nio.charset.Charset;
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
/*     */ public final class StringUtils
/*     */ {
/*  32 */   private static final String PLATFORM_DEFAULT_ENCODING = Charset.defaultCharset().name();
/*     */   public static final String SHIFT_JIS = "SJIS";
/*     */   public static final String GB2312 = "GB2312";
/*     */   private static final String EUC_JP = "EUC_JP";
/*     */   private static final String UTF8 = "UTF8";
/*     */   private static final String ISO88591 = "ISO8859_1";
/*  38 */   private static final boolean ASSUME_SHIFT_JIS = ("SJIS"
/*  39 */     .equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING) || "EUC_JP"
/*  40 */     .equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING));
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
/*     */   public static String guessEncoding(byte[] bytes, Map<DecodeHintType, ?> hints) {
/*  52 */     if (hints != null) {
/*  53 */       String characterSet = (String)hints.get(DecodeHintType.CHARACTER_SET);
/*  54 */       if (characterSet != null) {
/*  55 */         return characterSet;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  60 */     int length = bytes.length;
/*  61 */     boolean canBeISO88591 = true;
/*  62 */     boolean canBeShiftJIS = true;
/*  63 */     boolean canBeUTF8 = true;
/*  64 */     int utf8BytesLeft = 0;
/*     */     
/*  66 */     int utf2BytesChars = 0;
/*  67 */     int utf3BytesChars = 0;
/*  68 */     int utf4BytesChars = 0;
/*  69 */     int sjisBytesLeft = 0;
/*     */     
/*  71 */     int sjisKatakanaChars = 0;
/*     */     
/*  73 */     int sjisCurKatakanaWordLength = 0;
/*  74 */     int sjisCurDoubleBytesWordLength = 0;
/*  75 */     int sjisMaxKatakanaWordLength = 0;
/*  76 */     int sjisMaxDoubleBytesWordLength = 0;
/*     */ 
/*     */     
/*  79 */     int isoHighOther = 0;
/*     */     
/*  81 */     boolean utf8bom = (bytes.length > 3 && bytes[0] == -17 && bytes[1] == -69 && bytes[2] == -65);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     int i = 0;
/*  87 */     for (; i < length && (canBeISO88591 || canBeShiftJIS || canBeUTF8); 
/*  88 */       i++) {
/*     */       
/*  90 */       int value = bytes[i] & 0xFF;
/*     */ 
/*     */       
/*  93 */       if (canBeUTF8) {
/*  94 */         if (utf8BytesLeft > 0) {
/*  95 */           if ((value & 0x80) == 0) {
/*  96 */             canBeUTF8 = false;
/*     */           } else {
/*  98 */             utf8BytesLeft--;
/*     */           } 
/* 100 */         } else if ((value & 0x80) != 0) {
/* 101 */           if ((value & 0x40) == 0) {
/* 102 */             canBeUTF8 = false;
/*     */           } else {
/* 104 */             utf8BytesLeft++;
/* 105 */             if ((value & 0x20) == 0) {
/* 106 */               utf2BytesChars++;
/*     */             } else {
/* 108 */               utf8BytesLeft++;
/* 109 */               if ((value & 0x10) == 0) {
/* 110 */                 utf3BytesChars++;
/*     */               } else {
/* 112 */                 utf8BytesLeft++;
/* 113 */                 if ((value & 0x8) == 0) {
/* 114 */                   utf4BytesChars++;
/*     */                 } else {
/* 116 */                   canBeUTF8 = false;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 127 */       if (canBeISO88591) {
/* 128 */         if (value > 127 && value < 160) {
/* 129 */           canBeISO88591 = false;
/* 130 */         } else if (value > 159 && (
/* 131 */           value < 192 || value == 215 || value == 247)) {
/* 132 */           isoHighOther++;
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 142 */       if (canBeShiftJIS) {
/* 143 */         if (sjisBytesLeft > 0) {
/* 144 */           if (value < 64 || value == 127 || value > 252) {
/* 145 */             canBeShiftJIS = false;
/*     */           } else {
/* 147 */             sjisBytesLeft--;
/*     */           } 
/* 149 */         } else if (value == 128 || value == 160 || value > 239) {
/* 150 */           canBeShiftJIS = false;
/* 151 */         } else if (value > 160 && value < 224) {
/* 152 */           sjisKatakanaChars++;
/* 153 */           sjisCurDoubleBytesWordLength = 0;
/* 154 */           sjisCurKatakanaWordLength++;
/* 155 */           if (sjisCurKatakanaWordLength > sjisMaxKatakanaWordLength) {
/* 156 */             sjisMaxKatakanaWordLength = sjisCurKatakanaWordLength;
/*     */           }
/* 158 */         } else if (value > 127) {
/* 159 */           sjisBytesLeft++;
/*     */           
/* 161 */           sjisCurKatakanaWordLength = 0;
/* 162 */           sjisCurDoubleBytesWordLength++;
/* 163 */           if (sjisCurDoubleBytesWordLength > sjisMaxDoubleBytesWordLength) {
/* 164 */             sjisMaxDoubleBytesWordLength = sjisCurDoubleBytesWordLength;
/*     */           }
/*     */         } else {
/*     */           
/* 168 */           sjisCurKatakanaWordLength = 0;
/* 169 */           sjisCurDoubleBytesWordLength = 0;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 174 */     if (canBeUTF8 && utf8BytesLeft > 0) {
/* 175 */       canBeUTF8 = false;
/*     */     }
/* 177 */     if (canBeShiftJIS && sjisBytesLeft > 0) {
/* 178 */       canBeShiftJIS = false;
/*     */     }
/*     */ 
/*     */     
/* 182 */     if (canBeUTF8 && (utf8bom || utf2BytesChars + utf3BytesChars + utf4BytesChars > 0)) {
/* 183 */       return "UTF8";
/*     */     }
/*     */     
/* 186 */     if (canBeShiftJIS && (ASSUME_SHIFT_JIS || sjisMaxKatakanaWordLength >= 3 || sjisMaxDoubleBytesWordLength >= 3)) {
/* 187 */       return "SJIS";
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 194 */     if (canBeISO88591 && canBeShiftJIS) {
/* 195 */       return ((sjisMaxKatakanaWordLength == 2 && sjisKatakanaChars == 2) || isoHighOther * 10 >= length) ? "SJIS" : "ISO8859_1";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 200 */     if (canBeISO88591) {
/* 201 */       return "ISO8859_1";
/*     */     }
/* 203 */     if (canBeShiftJIS) {
/* 204 */       return "SJIS";
/*     */     }
/* 206 */     if (canBeUTF8) {
/* 207 */       return "UTF8";
/*     */     }
/*     */     
/* 210 */     return PLATFORM_DEFAULT_ENCODING;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\common\StringUtils.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */