/*     */ package com.sun.xml.messaging.saaj.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParseUtil
/*     */ {
/*     */   private static char unescape(String s, int i) {
/*  51 */     return (char)Integer.parseInt(s.substring(i + 1, i + 3), 16);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String decode(String s) {
/*  60 */     StringBuffer sb = new StringBuffer();
/*     */     
/*  62 */     int i = 0;
/*  63 */     while (i < s.length()) {
/*  64 */       char c = s.charAt(i);
/*     */ 
/*     */       
/*  67 */       if (c != '%') {
/*  68 */         i++;
/*     */       } else {
/*     */         try {
/*  71 */           c = unescape(s, i);
/*  72 */           i += 3;
/*     */           
/*  74 */           if ((c & 0x80) != 0) {
/*  75 */             char c2; char c3; switch (c >> 4) { case 12:
/*     */               case 13:
/*  77 */                 c2 = unescape(s, i);
/*  78 */                 i += 3;
/*  79 */                 c = (char)((c & 0x1F) << 6 | c2 & 0x3F);
/*     */                 break;
/*     */               
/*     */               case 14:
/*  83 */                 c2 = unescape(s, i);
/*  84 */                 i += 3;
/*  85 */                 c3 = unescape(s, i);
/*  86 */                 i += 3;
/*  87 */                 c = (char)((c & 0xF) << 12 | (c2 & 0x3F) << 6 | c3 & 0x3F);
/*     */                 break;
/*     */ 
/*     */ 
/*     */               
/*     */               default:
/*  93 */                 throw new IllegalArgumentException(); }
/*     */           
/*     */           } 
/*  96 */         } catch (NumberFormatException e) {
/*  97 */           throw new IllegalArgumentException();
/*     */         } 
/*     */       } 
/*     */       
/* 101 */       sb.append(c);
/*     */     } 
/*     */     
/* 104 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\ParseUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */