/*     */ package com.sun.xml.bind.v2.schemagen;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Util
/*     */ {
/*     */   public static String escapeURI(String s) {
/*  62 */     StringBuilder sb = new StringBuilder();
/*  63 */     for (int i = 0; i < s.length(); i++) {
/*  64 */       char c = s.charAt(i);
/*  65 */       if (Character.isSpaceChar(c)) {
/*  66 */         sb.append("%20");
/*     */       } else {
/*  68 */         sb.append(c);
/*     */       } 
/*     */     } 
/*  71 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getParentUriPath(String uriPath) {
/*  81 */     int idx = uriPath.lastIndexOf('/');
/*     */     
/*  83 */     if (uriPath.endsWith("/")) {
/*  84 */       uriPath = uriPath.substring(0, idx);
/*  85 */       idx = uriPath.lastIndexOf('/');
/*     */     } 
/*     */     
/*  88 */     return uriPath.substring(0, idx) + "/";
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
/*     */   public static String normalizeUriPath(String uriPath) {
/* 104 */     if (uriPath.endsWith("/")) {
/* 105 */       return uriPath;
/*     */     }
/*     */ 
/*     */     
/* 109 */     int idx = uriPath.lastIndexOf('/');
/* 110 */     return uriPath.substring(0, idx + 1);
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
/*     */   public static boolean equalsIgnoreCase(String s, String t) {
/* 122 */     if (s == t) return true; 
/* 123 */     if (s != null && t != null) {
/* 124 */       return s.equalsIgnoreCase(t);
/*     */     }
/* 126 */     return false;
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
/*     */   public static boolean equal(String s, String t) {
/* 138 */     if (s == t) return true; 
/* 139 */     if (s != null && t != null) {
/* 140 */       return s.equals(t);
/*     */     }
/* 142 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */