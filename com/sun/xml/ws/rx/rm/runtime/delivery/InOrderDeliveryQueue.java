/*     */ package com.sun.xml.ws.rx.rm.runtime.delivery;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.rm.runtime.ApplicationMessage;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.PriorityBlockingQueue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class InOrderDeliveryQueue
/*     */   implements DeliveryQueue
/*     */ {
/*     */   private static final class MessageIdComparator
/*     */     implements Comparator<ApplicationMessage>
/*     */   {
/*     */     private MessageIdComparator() {}
/*     */     
/*     */     public int compare(ApplicationMessage o1, ApplicationMessage o2) {
/*  65 */       return (o1.getMessageNumber() < o2.getMessageNumber()) ? -1 : ((o1.getMessageNumber() > o2.getMessageNumber()) ? 1 : 0);
/*     */     } }
/*     */   
/*  68 */   private static final Logger LOGGER = Logger.getLogger(InOrderDeliveryQueue.class);
/*  69 */   private static final MessageIdComparator MSG_ID_COMPARATOR = new MessageIdComparator();
/*     */   
/*     */   @NotNull
/*     */   private final Postman postman;
/*     */   
/*     */   @NotNull
/*     */   private final Postman.Callback deliveryCallback;
/*     */   
/*     */   @NotNull
/*     */   private final Sequence sequence;
/*     */   
/*     */   public InOrderDeliveryQueue(@NotNull Postman postman, @NotNull Postman.Callback deliveryCallback, @NotNull Sequence sequence, long maxMessageBufferSize) {
/*  81 */     assert postman != null;
/*  82 */     assert deliveryCallback != null;
/*  83 */     assert sequence != null;
/*  84 */     assert maxMessageBufferSize >= -1L;
/*     */     
/*  86 */     this.postman = postman;
/*  87 */     this.deliveryCallback = deliveryCallback;
/*  88 */     this.sequence = sequence;
/*     */     
/*  90 */     this.maxMessageBufferSize = maxMessageBufferSize;
/*  91 */     this.postponedMessageQueue = new PriorityBlockingQueue<ApplicationMessage>(32, MSG_ID_COMPARATOR);
/*     */     
/*  93 */     this.isClosed = false;
/*     */   }
/*     */   private final long maxMessageBufferSize; @NotNull
/*     */   private final BlockingQueue<ApplicationMessage> postponedMessageQueue; private volatile boolean isClosed;
/*     */   public void put(ApplicationMessage message) {
/*  98 */     assert message.getSequenceId().equals(this.sequence.getId());
/*     */     
/*     */     try {
/* 101 */       this.postponedMessageQueue.put(message);
/* 102 */     } catch (InterruptedException ex) {
/* 103 */       throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSRM_1147_ADDING_MSG_TO_QUEUE_INTERRUPTED(), ex));
/*     */     } 
/*     */     
/* 106 */     tryDelivery();
/*     */   }
/*     */   
/*     */   public void onSequenceAcknowledgement() {
/* 110 */     if (!this.isClosed) {
/* 111 */       tryDelivery();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void tryDelivery() {
/* 117 */     if (this.isClosed) {
/* 118 */       throw new RxRuntimeException(LocalizationMessages.WSRM_1160_DELIVERY_QUEUE_CLOSED());
/*     */     }
/*     */     
/* 121 */     if (!this.postponedMessageQueue.isEmpty()) {
/*     */       while (true) {
/* 123 */         ApplicationMessage deliverableMessage = null;
/*     */         
/* 125 */         synchronized (this.postponedMessageQueue) {
/* 126 */           ApplicationMessage queueHead = this.postponedMessageQueue.peek();
/*     */ 
/*     */ 
/*     */           
/* 130 */           if (queueHead != null && isDeliverable(queueHead)) {
/* 131 */             deliverableMessage = this.postponedMessageQueue.poll();
/* 132 */             assert isDeliverable(deliverableMessage);
/*     */           } 
/*     */         } 
/*     */         
/* 136 */         if (deliverableMessage != null) {
/*     */           
/* 138 */           this.postman.deliver(deliverableMessage, this.deliveryCallback);
/*     */           continue;
/*     */         } 
/*     */         break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public long getRemainingMessageBufferSize() {
/* 147 */     return (this.maxMessageBufferSize == -1L) ? this.maxMessageBufferSize : (this.maxMessageBufferSize - this.postponedMessageQueue.size());
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 152 */     this.isClosed = true;
/*     */   }
/*     */   
/*     */   private boolean isDeliverable(ApplicationMessage message) {
/* 156 */     List<Sequence.AckRange> ackedIds = this.sequence.getAcknowledgedMessageNumbers();
/* 157 */     if (ackedIds.isEmpty()) {
/* 158 */       return (message.getMessageNumber() == 1L);
/*     */     }
/* 160 */     Sequence.AckRange firstRange = ackedIds.get(0);
/* 161 */     return (firstRange.lower != 1L) ? ((message.getMessageNumber() == 1L)) : ((message.getMessageNumber() == firstRange.upper + 1L));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\delivery\InOrderDeliveryQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */