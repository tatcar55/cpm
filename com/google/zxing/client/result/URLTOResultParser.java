/*    */ package com.google.zxing.client.result;
/*    */ 
/*    */ import com.google.zxing.Result;
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
/*    */ 
/*    */ public final class URLTOResultParser
/*    */   extends ResultParser
/*    */ {
/*    */   public URIParsedResult parse(Result result) {
/* 32 */     String rawText = getMassagedText(result);
/* 33 */     if (!rawText.startsWith("urlto:") && !rawText.startsWith("URLTO:")) {
/* 34 */       return null;
/*    */     }
/* 36 */     int titleEnd = rawText.indexOf(':', 6);
/* 37 */     if (titleEnd < 0) {
/* 38 */       return null;
/*    */     }
/* 40 */     String title = (titleEnd <= 6) ? null : rawText.substring(6, titleEnd);
/* 41 */     String uri = rawText.substring(titleEnd + 1);
/* 42 */     return new URIParsedResult(uri, title);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\URLTOResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */