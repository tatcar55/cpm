/*    */ package com.sun.xml.ws.api.databinding;
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
/*    */ 
/*    */ public enum SoapBodyStyle
/*    */ {
/* 55 */   DocumentBare,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 60 */   DocumentWrapper,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 65 */   RpcLiteral,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 72 */   RpcEncoded,
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 77 */   Unspecificed;
/*    */   
/*    */   public boolean isDocument() {
/* 80 */     return (equals(DocumentBare) || equals(DocumentWrapper));
/*    */   }
/*    */   
/*    */   public boolean isRpc() {
/* 84 */     return (equals(RpcLiteral) || equals(RpcEncoded));
/*    */   }
/*    */   
/*    */   public boolean isLiteral() {
/* 88 */     return (equals(RpcLiteral) || isDocument());
/*    */   }
/*    */   
/*    */   public boolean isBare() {
/* 92 */     return equals(DocumentBare);
/*    */   }
/*    */   
/*    */   public boolean isDocumentWrapper() {
/* 96 */     return equals(DocumentWrapper);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\databinding\SoapBodyStyle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */