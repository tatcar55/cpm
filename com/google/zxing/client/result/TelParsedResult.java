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
/*    */ public final class TelParsedResult
/*    */   extends ParsedResult
/*    */ {
/*    */   private final String number;
/*    */   private final String telURI;
/*    */   private final String title;
/*    */   
/*    */   public TelParsedResult(String number, String telURI, String title) {
/* 29 */     super(ParsedResultType.TEL);
/* 30 */     this.number = number;
/* 31 */     this.telURI = telURI;
/* 32 */     this.title = title;
/*    */   }
/*    */   
/*    */   public String getNumber() {
/* 36 */     return this.number;
/*    */   }
/*    */   
/*    */   public String getTelURI() {
/* 40 */     return this.telURI;
/*    */   }
/*    */   
/*    */   public String getTitle() {
/* 44 */     return this.title;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayResult() {
/* 49 */     StringBuilder result = new StringBuilder(20);
/* 50 */     maybeAppend(this.number, result);
/* 51 */     maybeAppend(this.title, result);
/* 52 */     return result.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\TelParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */