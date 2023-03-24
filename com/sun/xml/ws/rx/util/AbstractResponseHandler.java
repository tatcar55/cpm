/*    */ package com.sun.xml.ws.rx.util;
/*    */ 
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AbstractResponseHandler
/*    */ {
/*    */   protected final SuspendedFiberStorage suspendedFiberStorage;
/*    */   private String correlationId;
/*    */   
/*    */   public AbstractResponseHandler(SuspendedFiberStorage suspendedFiberStorage, String correlationId) {
/* 54 */     this.suspendedFiberStorage = suspendedFiberStorage;
/* 55 */     this.correlationId = correlationId;
/*    */   }
/*    */   
/*    */   protected final String getCorrelationId() {
/* 59 */     return this.correlationId;
/*    */   }
/*    */   
/*    */   protected final void setCorrelationId(String newCorrelationId) {
/* 63 */     this.correlationId = newCorrelationId;
/*    */   }
/*    */   
/*    */   protected final void resumeParentFiber(Packet response) throws ResumeFiberException {
/* 67 */     this.suspendedFiberStorage.resumeFiber(this.correlationId, response);
/*    */   }
/*    */   
/*    */   protected final void resumeParentFiber(Throwable error) throws ResumeFiberException {
/* 71 */     this.suspendedFiberStorage.resumeFiber(this.correlationId, error);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\r\\util\AbstractResponseHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */