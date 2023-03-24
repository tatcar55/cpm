/*     */ package org.jvnet.mimepull;
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
/*     */ final class ASCIIUtility
/*     */ {
/*     */   public static int parseInt(byte[] b, int start, int end, int radix) throws NumberFormatException {
/*  58 */     if (b == null) {
/*  59 */       throw new NumberFormatException("null");
/*     */     }
/*     */     
/*  62 */     int result = 0;
/*  63 */     boolean negative = false;
/*  64 */     int i = start;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  69 */     if (end > start) {
/*  70 */       int limit; if (b[i] == 45) {
/*  71 */         negative = true;
/*  72 */         limit = Integer.MIN_VALUE;
/*  73 */         i++;
/*     */       } else {
/*  75 */         limit = -2147483647;
/*     */       } 
/*  77 */       int multmin = limit / radix;
/*  78 */       if (i < end) {
/*  79 */         int digit = Character.digit((char)b[i++], radix);
/*  80 */         if (digit < 0) {
/*  81 */           throw new NumberFormatException("illegal number: " + toString(b, start, end));
/*     */         }
/*     */ 
/*     */         
/*  85 */         result = -digit;
/*     */       } 
/*     */       
/*  88 */       while (i < end) {
/*     */         
/*  90 */         int digit = Character.digit((char)b[i++], radix);
/*  91 */         if (digit < 0) {
/*  92 */           throw new NumberFormatException("illegal number");
/*     */         }
/*  94 */         if (result < multmin) {
/*  95 */           throw new NumberFormatException("illegal number");
/*     */         }
/*  97 */         result *= radix;
/*  98 */         if (result < limit + digit) {
/*  99 */           throw new NumberFormatException("illegal number");
/*     */         }
/* 101 */         result -= digit;
/*     */       } 
/*     */     } else {
/* 104 */       throw new NumberFormatException("illegal number");
/*     */     } 
/* 106 */     if (negative) {
/* 107 */       if (i > start + 1) {
/* 108 */         return result;
/*     */       }
/* 110 */       throw new NumberFormatException("illegal number");
/*     */     } 
/*     */     
/* 113 */     return -result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(byte[] b, int start, int end) {
/* 123 */     int size = end - start;
/* 124 */     char[] theChars = new char[size];
/*     */     
/* 126 */     for (int i = 0, j = start; i < size;) {
/* 127 */       theChars[i++] = (char)(b[j++] & 0xFF);
/*     */     }
/*     */     
/* 130 */     return new String(theChars);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\ASCIIUtility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */