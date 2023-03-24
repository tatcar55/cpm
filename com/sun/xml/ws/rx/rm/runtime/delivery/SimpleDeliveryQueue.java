/*    */ package com.sun.xml.ws.rx.rm.runtime.delivery;
/*    */ 
/*    */ import com.sun.xml.ws.rx.RxRuntimeException;
/*    */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*    */ import com.sun.xml.ws.rx.rm.runtime.ApplicationMessage;
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class SimpleDeliveryQueue
/*    */   implements DeliveryQueue
/*    */ {
/*    */   private final Postman postman;
/*    */   private final Postman.Callback deliveryCallback;
/*    */   private final AtomicBoolean isClosed;
/*    */   
/*    */   SimpleDeliveryQueue(Postman postman, Postman.Callback deliveryCallback) {
/* 59 */     this.postman = postman;
/* 60 */     this.deliveryCallback = deliveryCallback;
/* 61 */     this.isClosed = new AtomicBoolean(false);
/*    */   }
/*    */   
/*    */   public void put(ApplicationMessage message) throws RxRuntimeException {
/* 65 */     if (this.isClosed.get()) {
/* 66 */       throw new RxRuntimeException(LocalizationMessages.WSRM_1160_DELIVERY_QUEUE_CLOSED());
/*    */     }
/*    */     
/* 69 */     this.postman.deliver(message, this.deliveryCallback);
/*    */   }
/*    */   
/*    */   public long getRemainingMessageBufferSize() {
/* 73 */     return -1L;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onSequenceAcknowledgement() {}
/*    */ 
/*    */   
/*    */   public void close() {
/* 81 */     this.isClosed.set(true);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\delivery\SimpleDeliveryQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */