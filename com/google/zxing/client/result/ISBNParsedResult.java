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
/*    */ public final class ISBNParsedResult
/*    */   extends ParsedResult
/*    */ {
/*    */   private final String isbn;
/*    */   
/*    */   ISBNParsedResult(String isbn) {
/* 27 */     super(ParsedResultType.ISBN);
/* 28 */     this.isbn = isbn;
/*    */   }
/*    */   
/*    */   public String getISBN() {
/* 32 */     return this.isbn;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayResult() {
/* 37 */     return this.isbn;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\ISBNParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */