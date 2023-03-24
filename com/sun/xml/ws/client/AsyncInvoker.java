/*    */ package com.sun.xml.ws.client;
/*    */ 
/*    */ import javax.xml.ws.WebServiceException;
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
/*    */ public abstract class AsyncInvoker
/*    */   implements Runnable
/*    */ {
/*    */   protected AsyncResponseImpl responseImpl;
/*    */   protected boolean nonNullAsyncHandlerGiven;
/*    */   
/*    */   public void setReceiver(AsyncResponseImpl responseImpl) {
/* 65 */     this.responseImpl = responseImpl;
/*    */   }
/*    */   
/*    */   public AsyncResponseImpl getResponseImpl() {
/* 69 */     return this.responseImpl;
/*    */   }
/*    */   
/*    */   public void setResponseImpl(AsyncResponseImpl responseImpl) {
/* 73 */     this.responseImpl = responseImpl;
/*    */   }
/*    */   
/*    */   public boolean isNonNullAsyncHandlerGiven() {
/* 77 */     return this.nonNullAsyncHandlerGiven;
/*    */   }
/*    */   
/*    */   public void setNonNullAsyncHandlerGiven(boolean nonNullAsyncHandlerGiven) {
/* 81 */     this.nonNullAsyncHandlerGiven = nonNullAsyncHandlerGiven;
/*    */   }
/*    */   
/*    */   public void run() {
/*    */     try {
/* 86 */       do_run();
/* 87 */     } catch (WebServiceException e) {
/* 88 */       throw e;
/* 89 */     } catch (Throwable t) {
/*    */       
/* 91 */       throw new WebServiceException(t);
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract void do_run();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\AsyncInvoker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */