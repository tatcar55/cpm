/*    */ package com.sun.xml.ws.rx.rm.runtime;
/*    */ 
/*    */ import com.sun.istack.logging.Logger;
/*    */ import com.sun.xml.ws.rx.RxRuntimeException;
/*    */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*    */ import com.sun.xml.ws.rx.rm.runtime.delivery.Postman;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ClientDestinationDeliveryCallback
/*    */   implements Postman.Callback
/*    */ {
/* 50 */   private static final Logger LOGGER = Logger.getLogger(ClientDestinationDeliveryCallback.class);
/*    */   private final RuntimeContext rc;
/*    */   
/*    */   public ClientDestinationDeliveryCallback(RuntimeContext rc) {
/* 54 */     this.rc = rc;
/*    */   }
/*    */   
/*    */   public void deliver(ApplicationMessage message) {
/* 58 */     if (message instanceof JaxwsApplicationMessage) {
/* 59 */       deliver(JaxwsApplicationMessage.class.cast(message));
/*    */     } else {
/* 61 */       throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSRM_1141_UNEXPECTED_MESSAGE_CLASS(message.getClass().getName(), JaxwsApplicationMessage.class.getName())));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void deliver(JaxwsApplicationMessage message) {
/* 68 */     this.rc.suspendedFiberStorage.resumeFiber(message.getCorrelationId(), message.getPacket());
/* 69 */     this.rc.destinationMessageHandler.acknowledgeApplicationLayerDelivery(message);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\ClientDestinationDeliveryCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */