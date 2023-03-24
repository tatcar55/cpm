/*    */ package com.google.zxing.client.result;
/*    */ 
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class URIParsedResult
/*    */   extends ParsedResult
/*    */ {
/* 26 */   private static final Pattern USER_IN_HOST = Pattern.compile(":/*([^/@]+)@[^/]+");
/*    */   
/*    */   private final String uri;
/*    */   private final String title;
/*    */   
/*    */   public URIParsedResult(String uri, String title) {
/* 32 */     super(ParsedResultType.URI);
/* 33 */     this.uri = massageURI(uri);
/* 34 */     this.title = title;
/*    */   }
/*    */   
/*    */   public String getURI() {
/* 38 */     return this.uri;
/*    */   }
/*    */   
/*    */   public String getTitle() {
/* 42 */     return this.title;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isPossiblyMaliciousURI() {
/* 54 */     return USER_IN_HOST.matcher(this.uri).find();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayResult() {
/* 59 */     StringBuilder result = new StringBuilder(30);
/* 60 */     maybeAppend(this.title, result);
/* 61 */     maybeAppend(this.uri, result);
/* 62 */     return result.toString();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static String massageURI(String uri) {
/* 70 */     uri = uri.trim();
/* 71 */     int protocolEnd = uri.indexOf(':');
/* 72 */     if (protocolEnd < 0 || isColonFollowedByPortNumber(uri, protocolEnd))
/*    */     {
/*    */       
/* 75 */       uri = "http://" + uri;
/*    */     }
/* 77 */     return uri;
/*    */   }
/*    */   
/*    */   private static boolean isColonFollowedByPortNumber(String uri, int protocolEnd) {
/* 81 */     int start = protocolEnd + 1;
/* 82 */     int nextSlash = uri.indexOf('/', start);
/* 83 */     if (nextSlash < 0) {
/* 84 */       nextSlash = uri.length();
/*    */     }
/* 86 */     return ResultParser.isSubstringOfDigits(uri, start, nextSlash - start);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\URIParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */