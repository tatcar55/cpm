/*    */ package com.sun.xml.ws.rx.rm.runtime;
/*    */ 
/*    */ import com.sun.istack.logging.Logger;
/*    */ import com.sun.xml.ws.commons.DelayedTaskManager;
/*    */ import com.sun.xml.ws.commons.ha.HaContext;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.logging.Level;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ enum RedeliveryTaskExecutor
/*    */ {
/* 54 */   INSTANCE; private final DelayedTaskManager delayedTaskManager; static {
/* 55 */     LOGGER = Logger.getLogger(RedeliveryTaskExecutor.class);
/*    */   }
/*    */   private static final Logger LOGGER;
/*    */   RedeliveryTaskExecutor() {
/* 59 */     this.delayedTaskManager = DelayedTaskManager.createManager("redelivery-task-executor", 5);
/*    */   }
/*    */   
/*    */   public boolean register(final ApplicationMessage message, long delay, TimeUnit timeUnit, final MessageHandler messageHandler) {
/* 63 */     final HaContext.State state = HaContext.currentState();
/*    */     
/* 65 */     if (LOGGER.isLoggable(Level.FINER)) {
/* 66 */       LOGGER.finer(String.format("A message with number [ %d ] has been scheduled for a redelivery on a sequence [ %s ] with a delay of %d %s using current HA context state [ %s ]", new Object[] { Long.valueOf(message.getMessageNumber()), message.getSequenceId(), Long.valueOf(delay), timeUnit.toString().toLowerCase(), state.toString() }));
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 77 */     return this.delayedTaskManager.register(new DelayedTaskManager.DelayedTask()
/*    */         {
/*    */           public void run(DelayedTaskManager manager) {
/* 80 */             if (RedeliveryTaskExecutor.LOGGER.isLoggable(Level.FINER)) {
/* 81 */               RedeliveryTaskExecutor.LOGGER.finer(String.format("Attempting redelivery of a message with number [ %d ] on a sequence [ %s ]", new Object[] { Long.valueOf(this.val$message.getMessageNumber()), this.val$message.getSequenceId() }));
/*    */             }
/*    */ 
/*    */ 
/*    */             
/* 86 */             HaContext.State oldState = HaContext.initFrom(state);
/*    */             try {
/* 88 */               messageHandler.putToDeliveryQueue(message);
/*    */             } finally {
/* 90 */               HaContext.initFrom(oldState);
/*    */             } 
/*    */           }
/*    */           
/*    */           public String getName() {
/* 95 */             return String.format("redelivery of a message with number [ %d ] on a sequenece [ %s ]", new Object[] { Long.valueOf(this.val$message.getMessageNumber()), this.val$message.getSequenceId() });
/*    */           }
/*    */         }delay, timeUnit);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\RedeliveryTaskExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */