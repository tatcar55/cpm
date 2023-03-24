/*    */ package com.google.zxing.client.result;
/*    */ 
/*    */ import com.google.zxing.BarcodeFormat;
/*    */ import com.google.zxing.Result;
/*    */ import com.google.zxing.oned.UPCEReader;
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
/*    */ public final class ProductResultParser
/*    */   extends ResultParser
/*    */ {
/*    */   public ProductParsedResult parse(Result result) {
/*    */     String normalizedProductID;
/* 33 */     BarcodeFormat format = result.getBarcodeFormat();
/* 34 */     if (format != BarcodeFormat.UPC_A && format != BarcodeFormat.UPC_E && format != BarcodeFormat.EAN_8 && format != BarcodeFormat.EAN_13)
/*    */     {
/* 36 */       return null;
/*    */     }
/* 38 */     String rawText = getMassagedText(result);
/* 39 */     if (!isStringOfDigits(rawText, rawText.length())) {
/* 40 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 46 */     if (format == BarcodeFormat.UPC_E && rawText.length() == 8) {
/* 47 */       normalizedProductID = UPCEReader.convertUPCEtoUPCA(rawText);
/*    */     } else {
/* 49 */       normalizedProductID = rawText;
/*    */     } 
/*    */     
/* 52 */     return new ProductParsedResult(rawText, normalizedProductID);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\ProductResultParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */