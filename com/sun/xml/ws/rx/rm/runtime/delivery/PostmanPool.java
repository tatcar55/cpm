/*    */ package com.sun.xml.ws.rx.rm.runtime.delivery;
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
/*    */ public enum PostmanPool
/*    */ {
/*    */   private final Postman singletonPostman;
/* 48 */   INSTANCE;
/*    */   PostmanPool() {
/* 50 */     this.singletonPostman = new Postman();
/*    */   }
/*    */   public Postman getPostman() {
/* 53 */     return this.singletonPostman;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\delivery\PostmanPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */