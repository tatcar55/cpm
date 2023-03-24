/*     */ package com.sun.xml.ws.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
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
/*     */ public class ASCIIUtility
/*     */ {
/*     */   public static int parseInt(byte[] b, int start, int end, int radix) throws NumberFormatException {
/*  64 */     if (b == null) {
/*  65 */       throw new NumberFormatException("null");
/*     */     }
/*  67 */     int result = 0;
/*  68 */     boolean negative = false;
/*  69 */     int i = start;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     if (end > start) {
/*  75 */       int limit; if (b[i] == 45) {
/*  76 */         negative = true;
/*  77 */         limit = Integer.MIN_VALUE;
/*  78 */         i++;
/*     */       } else {
/*  80 */         limit = -2147483647;
/*     */       } 
/*  82 */       int multmin = limit / radix;
/*  83 */       if (i < end) {
/*  84 */         int digit = Character.digit((char)b[i++], radix);
/*  85 */         if (digit < 0) {
/*  86 */           throw new NumberFormatException("illegal number: " + toString(b, start, end));
/*     */         }
/*     */ 
/*     */         
/*  90 */         result = -digit;
/*     */       } 
/*     */       
/*  93 */       while (i < end) {
/*     */         
/*  95 */         int digit = Character.digit((char)b[i++], radix);
/*  96 */         if (digit < 0) {
/*  97 */           throw new NumberFormatException("illegal number");
/*     */         }
/*  99 */         if (result < multmin) {
/* 100 */           throw new NumberFormatException("illegal number");
/*     */         }
/* 102 */         result *= radix;
/* 103 */         if (result < limit + digit) {
/* 104 */           throw new NumberFormatException("illegal number");
/*     */         }
/* 106 */         result -= digit;
/*     */       } 
/*     */     } else {
/* 109 */       throw new NumberFormatException("illegal number");
/*     */     } 
/* 111 */     if (negative) {
/* 112 */       if (i > start + 1) {
/* 113 */         return result;
/*     */       }
/* 115 */       throw new NumberFormatException("illegal number");
/*     */     } 
/*     */     
/* 118 */     return -result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(byte[] b, int start, int end) {
/* 128 */     int size = end - start;
/* 129 */     char[] theChars = new char[size];
/*     */     
/* 131 */     for (int i = 0, j = start; i < size;) {
/* 132 */       theChars[i++] = (char)(b[j++] & 0xFF);
/*     */     }
/* 134 */     return new String(theChars);
/*     */   }
/*     */   
/*     */   public static void copyStream(InputStream is, OutputStream out) throws IOException {
/* 138 */     int size = 1024;
/* 139 */     byte[] buf = new byte[size];
/*     */     
/*     */     int len;
/* 142 */     while ((len = is.read(buf, 0, size)) != -1)
/* 143 */       out.write(buf, 0, len); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\ASCIIUtility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */