/*    */ package com.sun.xml.ws.rx.rm.runtime;
/*    */ 
/*    */ import com.sun.istack.logging.Logger;
/*    */ import com.sun.xml.ws.api.message.Packet;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class ServerSourceDeliveryCallback
/*    */   implements Postman.Callback
/*    */ {
/* 55 */   private static final Logger LOGGER = Logger.getLogger(ServerSourceDeliveryCallback.class);
/*    */   private final RuntimeContext rc;
/*    */   
/*    */   public ServerSourceDeliveryCallback(RuntimeContext rc) {
/* 59 */     this.rc = rc;
/*    */   }
/*    */   
/*    */   public void deliver(ApplicationMessage message) {
/* 63 */     if (message instanceof JaxwsApplicationMessage) {
/* 64 */       deliver(JaxwsApplicationMessage.class.cast(message));
/*    */     } else {
/* 66 */       throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSRM_1141_UNEXPECTED_MESSAGE_CLASS(message.getClass().getName(), JaxwsApplicationMessage.class.getName())));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void deliver(JaxwsApplicationMessage message) {
/* 73 */     this.rc.sourceMessageHandler.attachAcknowledgementInfo(message);
/*    */     
/* 75 */     Packet outboundPacketCopy = message.getPacket().copy(true);
/*    */     
/* 77 */     this.rc.protocolHandler.appendSequenceHeader(outboundPacketCopy.getMessage(), message);
/* 78 */     this.rc.protocolHandler.appendAcknowledgementHeaders(outboundPacketCopy, message.getAcknowledgementData());
/*    */     
/* 80 */     this.rc.suspendedFiberStorage.resumeFiber(message.getCorrelationId(), outboundPacketCopy);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\ServerSourceDeliveryCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */