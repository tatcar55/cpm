/*     */ package com.sun.xml.ws.rx.rm.runtime.delivery;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.commons.NamedThreadFactory;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.rx.rm.runtime.ApplicationMessage;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.logging.Level;
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
/*     */ public final class Postman
/*     */ {
/*  56 */   private static final Logger LOGGER = Logger.getLogger(Postman.class);
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
/*  73 */   private final ExecutorService executor = Executors.newCachedThreadPool((ThreadFactory)new NamedThreadFactory("postman-executor"));
/*     */ 
/*     */   
/*     */   public void deliver(final ApplicationMessage message, final Callback deliveryCallback) {
/*  77 */     final HaContext.State state = HaContext.currentState();
/*     */     
/*  79 */     if (LOGGER.isLoggable(Level.FINER)) {
/*  80 */       LOGGER.finer(String.format("Scheduling delivery execution of a message with number [ %d ] on a sequence [ %s ] using current HA context state [ %s ]", new Object[] { Long.valueOf(message.getMessageNumber()), message.getSequenceId(), state.toString() }));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     this.executor.execute(new Runnable()
/*     */         {
/*     */           public void run() {
/*  91 */             if (Postman.LOGGER.isLoggable(Level.FINER)) {
/*  92 */               Postman.LOGGER.finer(String.format("Executing delivery of a message with number [ %d ] on a sequence [ %s ]", new Object[] { Long.valueOf(this.val$message.getMessageNumber()), this.val$message.getSequenceId() }));
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*  98 */             HaContext.State oldState = HaContext.initFrom(state);
/*     */             
/*     */             try {
/* 101 */               deliveryCallback.deliver(message);
/*     */             } finally {
/* 103 */               HaContext.initFrom(oldState);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     void deliver(ApplicationMessage param1ApplicationMessage);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\delivery\Postman.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */