/*    */ package com.sun.xml.ws.api.model;
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
/*    */ public enum MEP
/*    */ {
/* 49 */   REQUEST_RESPONSE(false),
/* 50 */   ONE_WAY(false),
/* 51 */   ASYNC_POLL(true),
/* 52 */   ASYNC_CALLBACK(true);
/*    */ 
/*    */   
/*    */   public final boolean isAsync;
/*    */ 
/*    */ 
/*    */   
/*    */   MEP(boolean async) {
/* 60 */     this.isAsync = async;
/*    */   }
/*    */   
/*    */   public final boolean isOneWay() {
/* 64 */     return (this == ONE_WAY);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\model\MEP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */