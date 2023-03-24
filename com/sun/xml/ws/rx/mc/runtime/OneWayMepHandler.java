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
/*    */ 
/*    */ class OneWayMepHandler
/*    */   extends McResponseHandlerBase
/*    */ {
/*    */   public OneWayMepHandler(McConfiguration configuration, MakeConnectionSenderTask mcSenderTask, SuspendedFiberStorage suspendedFiberStorage, String correlationId) {
/* 55 */     super(configuration, mcSenderTask, suspendedFiberStorage, correlationId);
/*    */   }
/*    */   
/*    */   public void onCompletion(Packet response) {
/* 59 */     Message responseMessage = response.getMessage();
/*    */     
/* 61 */     if (responseMessage != null) {
/* 62 */       processMakeConnectionHeaders(responseMessage);
/* 63 */     } else if (this.configuration.isReliableMessagingEnabled()) {
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 68 */       this.mcSenderTask.scheduleMcRequest();
/*    */     } 
/*    */     
/* 71 */     resumeParentFiber(response);
/*    */   }
/*    */   
/*    */   public void onCompletion(Throwable error) {
/* 75 */     if (this.configuration.isReliableMessagingEnabled() && isIOError(error))
/*    */     {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 83 */       this.mcSenderTask.scheduleMcRequest();
/*    */     }
/*    */     
/* 86 */     resumeParentFiber(error);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean isIOError(Throwable error) {
/* 92 */     return (error instanceof java.io.IOException || error.getCause() instanceof java.io.IOException);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\runtime\OneWayMepHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */