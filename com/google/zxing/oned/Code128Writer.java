/*     */ package com.google.zxing.oned;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.EncodeHintType;
/*     */ import com.google.zxing.WriterException;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ public final class Code128Writer
/*     */   extends OneDimensionalCodeWriter
/*     */ {
/*     */   private static final int CODE_START_B = 104;
/*     */   private static final int CODE_START_C = 105;
/*     */   private static final int CODE_CODE_B = 100;
/*     */   private static final int CODE_CODE_C = 99;
/*     */   private static final int CODE_STOP = 106;
/*     */   private static final char ESCAPE_FNC_1 = 'ñ';
/*     */   private static final char ESCAPE_FNC_2 = 'ò';
/*     */   private static final char ESCAPE_FNC_3 = 'ó';
/*     */   private static final char ESCAPE_FNC_4 = 'ô';
/*     */   private static final int CODE_FNC_1 = 102;
/*     */   private static final int CODE_FNC_2 = 97;
/*     */   private static final int CODE_FNC_3 = 96;
/*     */   private static final int CODE_FNC_4_B = 100;
/*     */   
/*     */   public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
/*  58 */     if (format != BarcodeFormat.CODE_128) {
/*  59 */       throw new IllegalArgumentException("Can only encode CODE_128, but got " + format);
/*     */     }
/*  61 */     return super.encode(contents, format, width, height, hints);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean[] encode(String contents) {
/*  66 */     int length = contents.length();
/*     */     
/*  68 */     if (length < 1 || length > 80) {
/*  69 */       throw new IllegalArgumentException("Contents length should be between 1 and 80 characters, but got " + length);
/*     */     }
/*     */ 
/*     */     
/*  73 */     for (int i = 0; i < length; i++) {
/*  74 */       char c = contents.charAt(i);
/*  75 */       if (c < ' ' || c > '~') {
/*  76 */         switch (c) {
/*     */           case 'ñ':
/*     */           case 'ò':
/*     */           case 'ó':
/*     */           case 'ô':
/*     */             break;
/*     */           default:
/*  83 */             throw new IllegalArgumentException("Bad character in input: " + c);
/*     */         } 
/*     */       
/*     */       }
/*     */     } 
/*  88 */     Collection<int[]> patterns = (Collection)new ArrayList<>();
/*  89 */     int checkSum = 0;
/*  90 */     int checkWeight = 1;
/*  91 */     int codeSet = 0;
/*  92 */     int position = 0;
/*     */     
/*  94 */     while (position < length) {
/*     */       
/*  96 */       int newCodeSet, patternIndex, requiredDigitCount = (codeSet == 99) ? 2 : 4;
/*     */       
/*  98 */       if (isDigits(contents, position, requiredDigitCount)) {
/*  99 */         newCodeSet = 99;
/*     */       } else {
/* 101 */         newCodeSet = 100;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 106 */       if (newCodeSet == codeSet) {
/*     */ 
/*     */         
/* 109 */         switch (contents.charAt(position)) {
/*     */           case 'ñ':
/* 111 */             patternIndex = 102;
/*     */             break;
/*     */           case 'ò':
/* 114 */             patternIndex = 97;
/*     */             break;
/*     */           case 'ó':
/* 117 */             patternIndex = 96;
/*     */             break;
/*     */           case 'ô':
/* 120 */             patternIndex = 100;
/*     */             break;
/*     */           
/*     */           default:
/* 124 */             if (codeSet == 100) {
/* 125 */               patternIndex = contents.charAt(position) - 32; break;
/*     */             } 
/* 127 */             patternIndex = Integer.parseInt(contents.substring(position, position + 2));
/* 128 */             position++;
/*     */             break;
/*     */         } 
/* 131 */         position++;
/*     */       }
/*     */       else {
/*     */         
/* 135 */         if (codeSet == 0) {
/*     */           
/* 137 */           if (newCodeSet == 100) {
/* 138 */             patternIndex = 104;
/*     */           } else {
/*     */             
/* 141 */             patternIndex = 105;
/*     */           } 
/*     */         } else {
/*     */           
/* 145 */           patternIndex = newCodeSet;
/*     */         } 
/* 147 */         codeSet = newCodeSet;
/*     */       } 
/*     */ 
/*     */       
/* 151 */       patterns.add(Code128Reader.CODE_PATTERNS[patternIndex]);
/*     */ 
/*     */       
/* 154 */       checkSum += patternIndex * checkWeight;
/* 155 */       if (position != 0) {
/* 156 */         checkWeight++;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 161 */     checkSum %= 103;
/* 162 */     patterns.add(Code128Reader.CODE_PATTERNS[checkSum]);
/*     */ 
/*     */     
/* 165 */     patterns.add(Code128Reader.CODE_PATTERNS[106]);
/*     */ 
/*     */     
/* 168 */     int codeWidth = 0;
/* 169 */     for (int[] pattern : patterns) {
/* 170 */       for (int width : pattern) {
/* 171 */         codeWidth += width;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 176 */     boolean[] result = new boolean[codeWidth];
/* 177 */     int pos = 0;
/* 178 */     for (int[] pattern : patterns) {
/* 179 */       pos += appendPattern(result, pos, pattern, true);
/*     */     }
/*     */     
/* 182 */     return result;
/*     */   }
/*     */   
/*     */   private static boolean isDigits(CharSequence value, int start, int length) {
/* 186 */     int end = start + length;
/* 187 */     int last = value.length();
/* 188 */     for (int i = start; i < end && i < last; i++) {
/* 189 */       char c = value.charAt(i);
/* 190 */       if (c < '0' || c > '9') {
/* 191 */         if (c != 'ñ') {
/* 192 */           return false;
/*     */         }
/* 194 */         end++;
/*     */       } 
/*     */     } 
/* 197 */     return (end <= last);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\Code128Writer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */