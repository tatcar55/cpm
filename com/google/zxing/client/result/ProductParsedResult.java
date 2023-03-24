/*    */ package com.google.zxing.client.result;
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
/*    */ public final class ProductParsedResult
/*    */   extends ParsedResult
/*    */ {
/*    */   private final String productID;
/*    */   private final String normalizedProductID;
/*    */   
/*    */   ProductParsedResult(String productID) {
/* 28 */     this(productID, productID);
/*    */   }
/*    */   
/*    */   ProductParsedResult(String productID, String normalizedProductID) {
/* 32 */     super(ParsedResultType.PRODUCT);
/* 33 */     this.productID = productID;
/* 34 */     this.normalizedProductID = normalizedProductID;
/*    */   }
/*    */   
/*    */   public String getProductID() {
/* 38 */     return this.productID;
/*    */   }
/*    */   
/*    */   public String getNormalizedProductID() {
/* 42 */     return this.normalizedProductID;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayResult() {
/* 47 */     return this.productID;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\ProductParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */