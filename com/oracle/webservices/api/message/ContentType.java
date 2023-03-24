/*    */ package com.oracle.webservices.api.message;
/*    */ 
/*    */ import com.sun.xml.ws.encoding.ContentTypeImpl;
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
/*    */ public interface ContentType
/*    */ {
/*    */   String getContentType();
/*    */   
/*    */   String getSOAPActionHeader();
/*    */   
/*    */   String getAcceptHeader();
/*    */   
/*    */   public static class Builder
/*    */   {
/*    */     private String contentType;
/*    */     private String soapAction;
/*    */     private String accept;
/*    */     private String charset;
/*    */     
/*    */     public Builder contentType(String s) {
/* 84 */       this.contentType = s; return this;
/* 85 */     } public Builder soapAction(String s) { this.soapAction = s; return this; }
/* 86 */     public Builder accept(String s) { this.accept = s; return this; } public Builder charset(String s) {
/* 87 */       this.charset = s; return this;
/*    */     }
/*    */     public ContentType build() {
/* 90 */       return (ContentType)new ContentTypeImpl(this.contentType, this.soapAction, this.accept, this.charset);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\oracle\webservices\api\message\ContentType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */