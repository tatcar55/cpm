/*    */ package com.sun.xml.ws.rx.rm.runtime.sequence;
/*    */ 
/*    */ import com.sun.istack.logging.Logger;
/*    */ import com.sun.xml.ws.rx.rm.faults.AbstractSoapFaultException;
/*    */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*    */ import com.sun.xml.ws.rx.rm.runtime.ApplicationMessage;
/*    */ import com.sun.xml.ws.rx.rm.runtime.delivery.DeliveryQueueBuilder;
/*    */ import com.sun.xml.ws.rx.util.TimeSynchronizer;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class InboundSequence
/*    */   extends AbstractSequence
/*    */ {
/* 57 */   private static final Logger LOGGER = Logger.getLogger(InboundSequence.class);
/*    */   public static final long INITIAL_LAST_MESSAGE_ID = 0L;
/*    */   
/*    */   public InboundSequence(SequenceData data, DeliveryQueueBuilder deliveryQueueBuilder, TimeSynchronizer timeSynchronizer) {
/* 61 */     super(data, deliveryQueueBuilder, timeSynchronizer);
/*    */   }
/*    */   
/*    */   public void registerMessage(ApplicationMessage message, boolean storeMessageFlag) throws DuplicateMessageRegistrationException, IllegalStateException {
/* 65 */     getState().verifyAcceptingMessageRegistration(getId(), AbstractSoapFaultException.Code.Receiver);
/*    */     
/* 67 */     if (!getId().equals(message.getSequenceId())) {
/* 68 */       throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSRM_1149_DIFFERENT_MSG_SEQUENCE_ID(message.getSequenceId(), getId())));
/*    */     }
/*    */ 
/*    */ 
/*    */     
/* 73 */     this.data.registerReceivedUnackedMessageNumber(message.getMessageNumber());
/* 74 */     if (storeMessageFlag) {
/* 75 */       this.data.attachMessageToUnackedMessageNumber(message);
/*    */     }
/*    */   }
/*    */   
/*    */   public void acknowledgeMessageNumbers(List<Sequence.AckRange> ranges) {
/* 80 */     throw new UnsupportedOperationException(String.format("This operation is not supported on %s class", new Object[] { getClass().getName() }));
/*    */   }
/*    */   
/*    */   public void acknowledgeMessageNumber(long messageId) throws IllegalStateException {
/* 84 */     getState().verifyAcceptingAcknowledgement(getId(), AbstractSoapFaultException.Code.Receiver);
/*    */     
/* 86 */     this.data.markAsAcknowledged(messageId);
/*    */     
/* 88 */     getDeliveryQueue().onSequenceAcknowledgement();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\InboundSequence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */