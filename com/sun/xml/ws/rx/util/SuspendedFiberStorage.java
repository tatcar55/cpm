/*     */ package com.sun.xml.ws.rx.util;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
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
/*     */ public class SuspendedFiberStorage
/*     */   extends TimestampedCollection<String, Fiber>
/*     */ {
/*  53 */   private static final Logger LOGGER = Logger.getLogger(SuspendedFiberStorage.class);
/*     */ 
/*     */   
/*     */   public Fiber register(String correlationId, Fiber subject) {
/*  57 */     if (LOGGER.isLoggable(Level.FINER)) {
/*  58 */       LOGGER.finer(String.format("Registering fiber [ %s ] with correlationId [ %s ] for suspend", new Object[] { subject.toString(), correlationId }));
/*     */     }
/*     */     
/*  61 */     return super.register(correlationId, subject);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean register(long timestamp, Fiber subject) {
/*  66 */     if (LOGGER.isLoggable(Level.FINER)) {
/*  67 */       LOGGER.finer(String.format("Registering fiber [ %s ] with timestamp [ %d ] for suspend", new Object[] { subject.toString(), Long.valueOf(timestamp) }));
/*     */     }
/*     */     
/*  70 */     return super.register(timestamp, subject);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void resumeFiber(String correlationId, Packet response) throws ResumeFiberException {
/*  76 */     Fiber fiber = remove(correlationId);
/*  77 */     if (fiber == null) {
/*  78 */       throw (ResumeFiberException)LOGGER.logSevereException(new ResumeFiberException(String.format("Unable to resume fiber with a response packet: No registered fiber found for correlationId [ %s ].", new Object[] { correlationId })));
/*     */     }
/*     */     
/*  81 */     if (LOGGER.isLoggable(Level.FINER)) {
/*  82 */       LOGGER.finer(String.format("Resuming fiber [ %s ] with a response", new Object[] { fiber.toString() }));
/*     */     }
/*     */     
/*  85 */     fiber.resume(response);
/*     */   }
/*     */   
/*     */   public void resumeFiber(String correlationId, Throwable error) throws ResumeFiberException {
/*  89 */     Fiber fiber = remove(correlationId);
/*  90 */     if (fiber == null) {
/*  91 */       throw (ResumeFiberException)LOGGER.logSevereException(new ResumeFiberException(String.format("Unable to resume fiber with a response packet: No registered fiber found for correlationId [ %s ].", new Object[] { correlationId })));
/*     */     }
/*     */     
/*  94 */     if (LOGGER.isLoggable(Level.FINER)) {
/*  95 */       LOGGER.finer(String.format("Resuming fiber [ %s ] with an exception", new Object[] { fiber.toString() }));
/*     */     }
/*     */     
/*  98 */     fiber.resume(error);
/*     */   }
/*     */   
/*     */   public void resumeAllFibers(Throwable error) {
/* 102 */     for (Fiber fiber : removeAll())
/* 103 */       fiber.resume(error); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\r\\util\SuspendedFiberStorage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */