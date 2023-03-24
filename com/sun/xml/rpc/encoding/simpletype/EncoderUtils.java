/*     */ package com.sun.xml.rpc.encoding.simpletype;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncoderUtils
/*     */ {
/*     */   public static boolean needsCollapsing(String str) {
/*  35 */     int len = str.length();
/*  36 */     int spanLen = 0;
/*     */     
/*  38 */     for (int idx = 0; idx < len; idx++) {
/*  39 */       if (Character.isWhitespace(str.charAt(idx))) {
/*  40 */         spanLen++;
/*  41 */       } else if (spanLen > 0) {
/*  42 */         if (spanLen == idx)
/*     */         {
/*  44 */           return true;
/*     */         }
/*     */         
/*  47 */         if (str.charAt(idx - spanLen) != ' ')
/*     */         {
/*  49 */           return true;
/*     */         }
/*  51 */         if (spanLen > 1)
/*     */         {
/*  53 */           return true;
/*     */         }
/*     */ 
/*     */         
/*  57 */         spanLen = 0;
/*     */       } 
/*     */     } 
/*     */     
/*  61 */     if (spanLen > 0)
/*     */     {
/*  63 */       return true;
/*     */     }
/*     */     
/*  66 */     return false;
/*     */   }
/*     */   
/*     */   public static String collapseWhitespace(String str) {
/*  70 */     if (!needsCollapsing(str)) {
/*  71 */       return str;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  77 */     int len = str.length();
/*  78 */     char[] buf = new char[len];
/*  79 */     str.getChars(0, len, buf, 0);
/*     */     
/*  81 */     int leadingWSLen = 0;
/*  82 */     int trailingWSLen = 0;
/*  83 */     int spanLen = 0;
/*     */     
/*  85 */     for (int idx = 0; idx < len; idx++) {
/*  86 */       if (Character.isWhitespace(buf[idx])) {
/*  87 */         spanLen++;
/*  88 */       } else if (spanLen > 0) {
/*  89 */         if (spanLen == idx) {
/*     */           
/*  91 */           leadingWSLen = spanLen;
/*     */         
/*     */         }
/*     */         else {
/*     */           
/*  96 */           int firstWSIdx = idx - spanLen;
/*  97 */           buf[firstWSIdx] = ' ';
/*     */           
/*  99 */           if (spanLen > 1) {
/*     */             
/* 101 */             System.arraycopy(buf, idx, buf, firstWSIdx + 1, len - idx);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 107 */             len -= spanLen - 1;
/* 108 */             idx = firstWSIdx + 1;
/*     */           } 
/*     */         } 
/*     */         
/* 112 */         spanLen = 0;
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     if (spanLen > 0)
/*     */     {
/* 118 */       trailingWSLen = spanLen;
/*     */     }
/*     */     
/* 121 */     return new String(buf, leadingWSLen, len - leadingWSLen - trailingWSLen);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String removeWhitespace(String str) {
/* 128 */     int len = str.length();
/* 129 */     StringBuffer buf = new StringBuffer();
/* 130 */     int firstNonWS = 0;
/* 131 */     int idx = 0;
/* 132 */     for (; idx < len; idx++) {
/* 133 */       if (Character.isWhitespace(str.charAt(idx))) {
/* 134 */         if (firstNonWS < idx)
/* 135 */           buf.append(str.substring(firstNonWS, idx)); 
/* 136 */         firstNonWS = idx + 1;
/*     */       } 
/*     */     } 
/* 139 */     if (firstNonWS < idx)
/* 140 */       buf.append(str.substring(firstNonWS, idx)); 
/* 141 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\simpletype\EncoderUtils.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */