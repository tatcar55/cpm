/*    */ package com.sun.xml.ws.client.sei;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.concurrent.Future;
/*    */ import javax.xml.ws.AsyncHandler;
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
/*    */ final class CallbackMethodHandler
/*    */   extends AsyncMethodHandler
/*    */ {
/*    */   private final int handlerPos;
/*    */   
/*    */   CallbackMethodHandler(SEIStub owner, Method m, int handlerPos) {
/* 64 */     super(owner, m);
/* 65 */     this.handlerPos = handlerPos;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   Future<?> invoke(Object proxy, Object[] args) throws WebServiceException {
/* 75 */     AsyncHandler handler = (AsyncHandler)args[this.handlerPos];
/*    */     
/* 77 */     return doInvoke(proxy, args, handler);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\sei\CallbackMethodHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */