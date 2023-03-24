/*     */ package com.sun.xml.rpc.util;
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
/*     */ public final class VersionUtil
/*     */   implements Version
/*     */ {
/*     */   public static final String JAXRPC_VERSION_101 = "1.0.1";
/*     */   public static final String JAXRPC_VERSION_103 = "1.0.3";
/*     */   public static final String JAXRPC_VERSION_11 = "1.1";
/*     */   public static final String JAXRPC_VERSION_111 = "1.1.1";
/*     */   public static final String JAXRPC_VERSION_112 = "1.1.2";
/*     */   public static final String JAXRPC_VERSION_112_01 = "1.1.2_01";
/*     */   public static final String JAXRPC_VERSION_112_02 = "1.1.2_02";
/*     */   public static final String JAXRPC_VERSION_113 = "1.1.3";
/*     */   public static final String JAXRPC_VERSION_DEFAULT = "1.1.3";
/*     */   
/*     */   public static boolean isJavaVersionGreaterThan1_3() {
/*     */     try {
/*  45 */       Class.forName("java.net.URI");
/*  46 */       return true;
/*  47 */     } catch (ClassNotFoundException e) {
/*  48 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isJavaVersionGreaterThan1_4() {
/*     */     try {
/*  59 */       Class.forName("java.util.UUID");
/*  60 */       return true;
/*  61 */     } catch (ClassNotFoundException e) {
/*  62 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getJAXRPCCompleteVersion() {
/*  73 */     return "JAX-RPC Standard Implementation1.1.3R2";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getJAXRPCVersion() {
/*  82 */     return "1.1.3";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getJAXRPCBuildNumber() {
/*  90 */     return "R2";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getJAXRPCProductName() {
/*  98 */     return "JAX-RPC Standard Implementation";
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
/*     */   public static boolean isVersion101(String version) {
/* 111 */     return "1.0.1".equals(version);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isVersion103(String version) {
/* 121 */     return "1.0.3".equals(version);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isVersion11(String version) {
/* 129 */     return "1.1".equals(version);
/*     */   }
/*     */   
/*     */   public static boolean isVersion111(String version) {
/* 133 */     return "1.1.1".equals(version);
/*     */   }
/*     */   
/*     */   public static boolean isVersion112(String version) {
/* 137 */     return ("1.1.2".equals(version) || "1.1.2_01".equals(version) || "1.1.2_02".equals(version));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isValidVersion(String version) {
/* 146 */     return (isVersion101(version) || isVersion103(version) || isVersion11(version) || isVersion111(version) || isVersion112(version));
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
/*     */   
/*     */   public static int[] getCanonicalVersion(String version) {
/* 166 */     int[] canonicalVersion = new int[4];
/*     */ 
/*     */     
/* 169 */     canonicalVersion[0] = 1;
/* 170 */     canonicalVersion[1] = 1;
/* 171 */     canonicalVersion[2] = 0;
/* 172 */     canonicalVersion[3] = 0;
/*     */     
/* 174 */     String DASH_DELIM = "_";
/* 175 */     String DOT_DELIM = ".";
/*     */     
/* 177 */     StringTokenizer tokenizer = new StringTokenizer(version, ".");
/*     */     
/* 179 */     String token = tokenizer.nextToken();
/*     */ 
/*     */     
/* 182 */     canonicalVersion[0] = Integer.parseInt(token);
/*     */ 
/*     */     
/* 185 */     token = tokenizer.nextToken();
/* 186 */     if (token.indexOf("_") == -1) {
/*     */       
/* 188 */       canonicalVersion[1] = Integer.parseInt(token);
/*     */     } else {
/*     */       
/* 191 */       StringTokenizer subTokenizer = new StringTokenizer(token, "_");
/*     */       
/* 193 */       canonicalVersion[1] = Integer.parseInt(subTokenizer.nextToken());
/*     */ 
/*     */       
/* 196 */       canonicalVersion[3] = Integer.parseInt(subTokenizer.nextToken());
/*     */     } 
/*     */ 
/*     */     
/* 200 */     if (tokenizer.hasMoreTokens()) {
/* 201 */       token = tokenizer.nextToken();
/* 202 */       if (token.indexOf("_") == -1) {
/*     */         
/* 204 */         canonicalVersion[2] = Integer.parseInt(token);
/*     */ 
/*     */         
/* 207 */         if (tokenizer.hasMoreTokens()) {
/* 208 */           canonicalVersion[3] = Integer.parseInt(tokenizer.nextToken());
/*     */         }
/*     */       } else {
/* 211 */         StringTokenizer subTokenizer = new StringTokenizer(token, "_");
/*     */ 
/*     */         
/* 214 */         canonicalVersion[2] = Integer.parseInt(subTokenizer.nextToken());
/*     */ 
/*     */         
/* 217 */         canonicalVersion[3] = Integer.parseInt(subTokenizer.nextToken());
/*     */       } 
/*     */     } 
/*     */     
/* 221 */     return canonicalVersion;
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
/* 234 */     int[] canonicalVersion1 = getCanonicalVersion(version1);
/* 235 */     int[] canonicalVersion2 = getCanonicalVersion(version2);
/*     */     
/* 237 */     if (canonicalVersion1[0] < canonicalVersion2[0])
/* 238 */       return -1; 
/* 239 */     if (canonicalVersion1[0] > canonicalVersion2[0]) {
/* 240 */       return 1;
/*     */     }
/* 242 */     if (canonicalVersion1[1] < canonicalVersion2[1])
/* 243 */       return -1; 
/* 244 */     if (canonicalVersion1[1] > canonicalVersion2[1]) {
/* 245 */       return 1;
/*     */     }
/* 247 */     if (canonicalVersion1[2] < canonicalVersion2[2])
/* 248 */       return -1; 
/* 249 */     if (canonicalVersion1[2] > canonicalVersion2[2]) {
/* 250 */       return 1;
/*     */     }
/* 252 */     if (canonicalVersion1[3] < canonicalVersion2[3])
/* 253 */       return -1; 
/* 254 */     if (canonicalVersion1[3] > canonicalVersion2[3]) {
/* 255 */       return 1;
/*     */     }
/* 257 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\VersionUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */