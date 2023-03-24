/*     */ package com.sun.xml.ws.util;
/*     */ 
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class VersionUtil
/*     */ {
/*     */   public static final String JAXWS_VERSION_20 = "2.0";
/*     */   public static final String JAXWS_VERSION_DEFAULT = "2.0";
/*     */   
/*     */   public static boolean isVersion20(String version) {
/*  55 */     return "2.0".equals(version);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidVersion(String version) {
/*  63 */     return isVersion20(version);
/*     */   }
/*     */   
/*     */   public static String getValidVersionString() {
/*  67 */     return "2.0";
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
/*     */   public static int[] getCanonicalVersion(String version) {
/*  83 */     int[] canonicalVersion = new int[4];
/*     */ 
/*     */     
/*  86 */     canonicalVersion[0] = 1;
/*  87 */     canonicalVersion[1] = 1;
/*  88 */     canonicalVersion[2] = 0;
/*  89 */     canonicalVersion[3] = 0;
/*     */     
/*  91 */     String DASH_DELIM = "_";
/*  92 */     String DOT_DELIM = ".";
/*     */     
/*  94 */     StringTokenizer tokenizer = new StringTokenizer(version, ".");
/*     */     
/*  96 */     String token = tokenizer.nextToken();
/*     */ 
/*     */     
/*  99 */     canonicalVersion[0] = Integer.parseInt(token);
/*     */ 
/*     */     
/* 102 */     token = tokenizer.nextToken();
/* 103 */     if (token.indexOf("_") == -1) {
/*     */       
/* 105 */       canonicalVersion[1] = Integer.parseInt(token);
/*     */     } else {
/*     */       
/* 108 */       StringTokenizer subTokenizer = new StringTokenizer(token, "_");
/*     */       
/* 110 */       canonicalVersion[1] = Integer.parseInt(subTokenizer.nextToken());
/*     */ 
/*     */       
/* 113 */       canonicalVersion[3] = Integer.parseInt(subTokenizer.nextToken());
/*     */     } 
/*     */ 
/*     */     
/* 117 */     if (tokenizer.hasMoreTokens()) {
/* 118 */       token = tokenizer.nextToken();
/* 119 */       if (token.indexOf("_") == -1) {
/*     */         
/* 121 */         canonicalVersion[2] = Integer.parseInt(token);
/*     */ 
/*     */         
/* 124 */         if (tokenizer.hasMoreTokens()) {
/* 125 */           canonicalVersion[3] = Integer.parseInt(tokenizer.nextToken());
/*     */         }
/*     */       } else {
/* 128 */         StringTokenizer subTokenizer = new StringTokenizer(token, "_");
/*     */ 
/*     */         
/* 131 */         canonicalVersion[2] = Integer.parseInt(subTokenizer.nextToken());
/*     */ 
/*     */         
/* 134 */         canonicalVersion[3] = Integer.parseInt(subTokenizer.nextToken());
/*     */       } 
/*     */     } 
/*     */     
/* 138 */     return canonicalVersion;
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
/*     */   public static int compare(String version1, String version2) {
/* 151 */     int[] canonicalVersion1 = getCanonicalVersion(version1);
/* 152 */     int[] canonicalVersion2 = getCanonicalVersion(version2);
/*     */     
/* 154 */     if (canonicalVersion1[0] < canonicalVersion2[0])
/* 155 */       return -1; 
/* 156 */     if (canonicalVersion1[0] > canonicalVersion2[0]) {
/* 157 */       return 1;
/*     */     }
/* 159 */     if (canonicalVersion1[1] < canonicalVersion2[1])
/* 160 */       return -1; 
/* 161 */     if (canonicalVersion1[1] > canonicalVersion2[1]) {
/* 162 */       return 1;
/*     */     }
/* 164 */     if (canonicalVersion1[2] < canonicalVersion2[2])
/* 165 */       return -1; 
/* 166 */     if (canonicalVersion1[2] > canonicalVersion2[2]) {
/* 167 */       return 1;
/*     */     }
/* 169 */     if (canonicalVersion1[3] < canonicalVersion2[3])
/* 170 */       return -1; 
/* 171 */     if (canonicalVersion1[3] > canonicalVersion2[3]) {
/* 172 */       return 1;
/*     */     }
/* 174 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\VersionUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */