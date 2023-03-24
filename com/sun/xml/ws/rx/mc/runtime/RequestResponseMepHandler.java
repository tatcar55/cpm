/*    */ package com.sun.xml.ws.rx.mc.runtime;
/*    */ 
/*    */ import com.sun.xml.ws.api.message.Message;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.rx.util.SuspendedFiberStorage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class RequestResponseMepHandler
/*    */   extends McResponseHandlerBase
/*    */ {
/*    */   public RequestResponseMepHandler(McConfiguration configuration, MakeConnectionSenderTask mcSenderTask, SuspendedFiberStorage suspendedFiberStorage, String correlationId) {
/* 54 */     super(configuration, mcSenderTask, suspendedFiberStorage, correlationId);
/*    */   }
/*    */   
/*    */   public void onCompletion(Packet response) {
/* 58 */     Message responseMessage = response.getMessage();
/* 59 */     if (responseMessage != null) {
/* 60 */       processMakeConnectionHeaders(responseMessage);
/*    */       
/* 62 */       if (responseMessage.hasPayload()) {
/* 63 */         resumeParentFiber(response);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onCompletion(Throwable error) {
/* 71 */     resumeParentFiber(error);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\runtime\RequestResponseMepHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */