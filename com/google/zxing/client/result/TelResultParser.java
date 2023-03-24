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
/*    */ public final class TelResultParser
/*    */   extends ResultParser
/*    */ {
/*    */   public TelParsedResult parse(Result result) {
/* 30 */     String rawText = getMassagedText(result);
/* 31 */     if (!rawText.startsWith("tel:") && !rawText.startsWith("TEL:")) {
/* 32 */       return null;
/*    */     }
/*    */     
/* 35 */     String telURI = rawText.startsWith("TEL:") ? ("tel:" + rawText.substring(4)) : rawText;
/*    */     
/* 37 */     int queryStart = rawText.indexOf('?', 4);
/* 38 */     String number = (queryStart < 0) ? rawText.substring(4) : rawText.substring(4, queryStart);
/* 39 */     return new TelParsedResult(number, telURI, null);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\TelResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */