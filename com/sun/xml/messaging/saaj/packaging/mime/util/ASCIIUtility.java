/*     */ package com.sun.xml.messaging.saaj.packaging.mime.util;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public class ASCIIUtility
/*     */ {
/*     */   public static int parseInt(byte[] b, int start, int end, int radix) throws NumberFormatException {
/*  67 */     if (b == null) {
/*  68 */       throw new NumberFormatException("null");
/*     */     }
/*  70 */     int result = 0;
/*  71 */     boolean negative = false;
/*  72 */     int i = start;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     if (end > start) {
/*  78 */       int limit; if (b[i] == 45) {
/*  79 */         negative = true;
/*  80 */         limit = Integer.MIN_VALUE;
/*  81 */         i++;
/*     */       } else {
/*  83 */         limit = -2147483647;
/*     */       } 
/*  85 */       int multmin = limit / radix;
/*  86 */       if (i < end) {
/*  87 */         int digit = Character.digit((char)b[i++], radix);
/*  88 */         if (digit < 0) {
/*  89 */           throw new NumberFormatException("illegal number: " + toString(b, start, end));
/*     */         }
/*     */ 
/*     */         
/*  93 */         result = -digit;
/*     */       } 
/*     */       
/*  96 */       while (i < end) {
/*     */         
/*  98 */         int digit = Character.digit((char)b[i++], radix);
/*  99 */         if (digit < 0) {
/* 100 */           throw new NumberFormatException("illegal number");
/*     */         }
/* 102 */         if (result < multmin) {
/* 103 */           throw new NumberFormatException("illegal number");
/*     */         }
/* 105 */         result *= radix;
/* 106 */         if (result < limit + digit) {
/* 107 */           throw new NumberFormatException("illegal number");
/*     */         }
/* 109 */         result -= digit;
/*     */       } 
/*     */     } else {
/* 112 */       throw new NumberFormatException("illegal number");
/*     */     } 
/* 114 */     if (negative) {
/* 115 */       if (i > start + 1) {
/* 116 */         return result;
/*     */       }
/* 118 */       throw new NumberFormatException("illegal number");
/*     */     } 
/*     */     
/* 121 */     return -result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String toString(byte[] b, int start, int end) {
/* 131 */     int size = end - start;
/* 132 */     char[] theChars = new char[size];
/*     */     
/* 134 */     for (int i = 0, j = start; i < size;) {
/* 135 */       theChars[i++] = (char)(b[j++] & 0xFF);
/*     */     }
/* 137 */     return new String(theChars);
/*     */   }
/*     */   
/*     */   public static byte[] getBytes(String s) {
/* 141 */     char[] chars = s.toCharArray();
/* 142 */     int size = chars.length;
/* 143 */     byte[] bytes = new byte[size];
/*     */     
/* 145 */     for (int i = 0; i < size;)
/* 146 */       bytes[i] = (byte)chars[i++]; 
/* 147 */     return bytes;
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
/*     */   public static byte[] getBytes(InputStream is) throws IOException {
/* 159 */     ByteOutputStream bos = new ByteOutputStream();
/*     */     try {
/* 161 */       bos.write(is);
/*     */     } finally {
/* 163 */       is.close();
/*     */     } 
/* 165 */     return bos.toByteArray();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mim\\util\ASCIIUtility.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */