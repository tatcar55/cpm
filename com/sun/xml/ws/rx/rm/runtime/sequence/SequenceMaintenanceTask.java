/*    */ package com.sun.xml.ws.rx.rm.runtime.sequence;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import com.sun.istack.logging.Logger;
/*    */ import com.sun.xml.ws.commons.DelayedTaskManager;
/*    */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.concurrent.TimeUnit;
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
/*    */ public class SequenceMaintenanceTask
/*    */   implements DelayedTaskManager.DelayedTask
/*    */ {
/* 56 */   private static final Logger LOGGER = Logger.getLogger(SequenceMaintenanceTask.class);
/*    */   private final WeakReference<SequenceManager> smReference;
/*    */   private final long period;
/*    */   private final TimeUnit timeUnit;
/*    */   private final String endpointUid;
/*    */   
/*    */   public SequenceMaintenanceTask(@NotNull SequenceManager sequenceManager, long period, @NotNull TimeUnit timeUnit) {
/* 63 */     assert sequenceManager != null;
/* 64 */     assert period > 0L;
/* 65 */     assert timeUnit != null;
/*    */     
/* 67 */     this.smReference = new WeakReference<SequenceManager>(sequenceManager);
/* 68 */     this.period = period;
/* 69 */     this.timeUnit = timeUnit;
/* 70 */     this.endpointUid = sequenceManager.uniqueEndpointId();
/*    */   }
/*    */   
/*    */   public void run(DelayedTaskManager manager) {
/* 74 */     SequenceManager sequenceManager = this.smReference.get();
/* 75 */     if (sequenceManager != null && sequenceManager.onMaintenance()) {
/* 76 */       if (!manager.isClosed()) {
/* 77 */         boolean registrationSuccesfull = manager.register(this, this.period, this.timeUnit);
/*    */         
/* 79 */         if (!registrationSuccesfull) {
/* 80 */           LOGGER.config(LocalizationMessages.WSRM_1150_UNABLE_TO_RESCHEDULE_SEQUENCE_MAINTENANCE_TASK(this.endpointUid));
/*    */         }
/*    */       } 
/*    */     } else {
/* 84 */       LOGGER.config(LocalizationMessages.WSRM_1151_TERMINATING_SEQUENCE_MAINTENANCE_TASK(this.endpointUid));
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getName() {
/* 89 */     return "sequence maintenance task";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\sequence\SequenceMaintenanceTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */