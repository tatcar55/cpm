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
/*    */ public final class BookmarkDoCoMoResultParser
/*    */   extends AbstractDoCoMoResultParser
/*    */ {
/*    */   public URIParsedResult parse(Result result) {
/* 28 */     String rawText = result.getText();
/* 29 */     if (!rawText.startsWith("MEBKM:")) {
/* 30 */       return null;
/*    */     }
/* 32 */     String title = matchSingleDoCoMoPrefixedField("TITLE:", rawText, true);
/* 33 */     String[] rawUri = matchDoCoMoPrefixedField("URL:", rawText, true);
/* 34 */     if (rawUri == null) {
/* 35 */       return null;
/*    */     }
/* 37 */     String uri = rawUri[0];
/* 38 */     return URIResultParser.isBasicallyValidURI(uri) ? new URIParsedResult(uri, title) : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\BookmarkDoCoMoResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */