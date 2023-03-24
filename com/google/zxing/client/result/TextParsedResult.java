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
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class TextParsedResult
/*    */   extends ParsedResult
/*    */ {
/*    */   private final String text;
/*    */   private final String language;
/*    */   
/*    */   public TextParsedResult(String text, String language) {
/* 31 */     super(ParsedResultType.TEXT);
/* 32 */     this.text = text;
/* 33 */     this.language = language;
/*    */   }
/*    */   
/*    */   public String getText() {
/* 37 */     return this.text;
/*    */   }
/*    */   
/*    */   public String getLanguage() {
/* 41 */     return this.language;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayResult() {
/* 46 */     return this.text;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\result\TextParsedResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */