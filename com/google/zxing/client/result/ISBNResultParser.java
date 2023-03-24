/*    */ package com.google.zxing.client.result;
/*    */ 
/*    */ import com.google.zxing.BarcodeFormat;
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
/*    */ 
/*    */ public final class ISBNResultParser
/*    */   extends ResultParser
/*    */ {
/*    */   public ISBNParsedResult parse(Result result) {
/* 34 */     BarcodeFormat format = result.getBarcodeFormat();
/* 35 */     if (format != BarcodeFormat.EAN_13) {
/* 36 */       return null;
/*    */     }
/* 38 */     String rawText = getMassagedText(result);
/* 39 */     int length = rawText.length();
/* 40 */     if (length != 13) {
/* 41 */       return null;
/*    */     }
/* 43 */     if (!rawText.startsWith("978") && !rawText.startsWith("979")) {
/* 44 */       return null;
/*    */     }
/*    */     
/* 47 */     return new ISBNParsedResult(rawText);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\ISBNResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */