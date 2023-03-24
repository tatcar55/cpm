/*    */ package com.google.zxing.client.result;
/*    */ 
/*    */ import com.google.zxing.Result;
/*    */ import java.util.regex.Matcher;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class URIResultParser
/*    */   extends ResultParser
/*    */ {
/* 32 */   private static final Pattern URL_WITH_PROTOCOL_PATTERN = Pattern.compile("[a-zA-Z][a-zA-Z0-9+-.]+:");
/* 33 */   private static final Pattern URL_WITHOUT_PROTOCOL_PATTERN = Pattern.compile("([a-zA-Z0-9\\-]+\\.)+[a-zA-Z]{2,}(:\\d{1,5})?(/|\\?|$)");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public URIParsedResult parse(Result result) {
/* 40 */     String rawText = getMassagedText(result);
/*    */ 
/*    */     
/* 43 */     if (rawText.startsWith("URL:") || rawText.startsWith("URI:")) {
/* 44 */       return new URIParsedResult(rawText.substring(4).trim(), null);
/*    */     }
/* 46 */     rawText = rawText.trim();
/* 47 */     return isBasicallyValidURI(rawText) ? new URIParsedResult(rawText, null) : null;
/*    */   }
/*    */   
/*    */   static boolean isBasicallyValidURI(String uri) {
/* 51 */     if (uri.contains(" "))
/*    */     {
/* 53 */       return false;
/*    */     }
/* 55 */     Matcher m = URL_WITH_PROTOCOL_PATTERN.matcher(uri);
/* 56 */     if (m.find() && m.start() == 0) {
/* 57 */       return true;
/*    */     }
/* 59 */     m = URL_WITHOUT_PROTOCOL_PATTERN.matcher(uri);
/* 60 */     return (m.find() && m.start() == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\URIResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */