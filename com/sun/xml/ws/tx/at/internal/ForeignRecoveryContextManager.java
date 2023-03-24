/*     */ package com.sun.xml.ws.tx.at.internal;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.tx.at.WSATHelper;
/*     */ import com.sun.xml.ws.tx.at.common.CoordinatorIF;
/*     */ import com.sun.xml.ws.tx.at.common.WSATVersion;
/*     */ import com.sun.xml.ws.tx.dev.WSATRuntimeConfig;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.transaction.SystemException;
/*     */ import javax.transaction.Transaction;
/*     */ import javax.transaction.xa.Xid;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public class ForeignRecoveryContextManager
/*     */ {
/*  62 */   private static final int REPLAY_TIMER_INTERVAL_MS = (new Integer(System.getProperty("com.sun.xml.ws.tx.at.internal.indoubt.timeout.interval", "10000"))).intValue();
/*     */ 
/*     */ 
/*     */   
/*  66 */   private static final int INDOUBT_TIMEOUT = (new Integer(System.getProperty("com.sun.xml.ws.tx.at.internal.indoubt.timeout", "90000"))).intValue();
/*     */   
/*  68 */   private static ForeignRecoveryContextManager singleton = new ForeignRecoveryContextManager();
/*     */   volatile int counter;
/*  70 */   private static final Logger LOGGER_ContextRunnable = Logger.getLogger(ContextRunnable.class);
/*  71 */   private static final Logger LOGGER_RecoveryContextWorker = Logger.getLogger(RecoveryContextWorker.class);
/*     */   
/*  73 */   private Map<Xid, RecoveryContextWorker> recoveredContexts = new HashMap<Xid, RecoveryContextWorker>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ForeignRecoveryContextManager getInstance() {
/*  79 */     return singleton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized ForeignRecoveryContext addAndGetForeignRecoveryContextForTidByteArray(Xid xid) {
/*  91 */     RecoveryContextWorker rc = this.recoveredContexts.get(xid);
/*  92 */     if (rc != null) return rc.context; 
/*  93 */     ForeignRecoveryContext frc = new ForeignRecoveryContext(xid);
/*  94 */     add(frc, false);
/*  95 */     return frc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void start() {
/* 102 */     (new Thread(new ContextRunnable())).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void add(ForeignRecoveryContext context) {
/* 110 */     add(context, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void add(ForeignRecoveryContext context, boolean isRecovery) {
/* 119 */     if (context == null)
/* 120 */       return;  this.recoveredContexts.put(context.getXid(), new RecoveryContextWorker(context, isRecovery ? -1 : 0));
/*     */   }
/*     */   
/*     */   synchronized void persist(Xid xid) {
/* 124 */     if (WSATRuntimeConfig.getInstance().isWSATRecoveryEnabled()) {
/* 125 */       ForeignRecoveryContext contextWorker = ((RecoveryContextWorker)this.recoveredContexts.get(xid)).getContext();
/*     */ 
/*     */       
/*     */       try {
/* 129 */         String logLocation = WSATGatewayRM.txlogdirInbound + File.separator + System.currentTimeMillis() + "-" + this.counter++;
/*     */         
/* 131 */         contextWorker.setTxLogLocation(logLocation);
/* 132 */         FileOutputStream fos = new FileOutputStream(logLocation);
/* 133 */         ObjectOutputStream out = new ObjectOutputStream(fos);
/* 134 */         out.writeObject(contextWorker);
/* 135 */         out.close();
/* 136 */         fos.flush();
/* 137 */       } catch (Throwable e) {
/* 138 */         throw new WebServiceException("Unable to persist log for inbound transaction Xid:" + xid, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void delete(XidImpl xid) {
/* 144 */     if (WSATRuntimeConfig.getInstance().isWSATRecoveryEnabled()) {
/* 145 */       ForeignRecoveryContext contextWorker = ((RecoveryContextWorker)this.recoveredContexts.get(xid)).getContext();
/* 146 */       String logLocation = contextWorker.getTxLogLocation();
/*     */       try {
/* 148 */         (new File(logLocation)).delete();
/* 149 */       } catch (Throwable e) {
/* 150 */         LOGGER_RecoveryContextWorker.warning("Unable to delete WS-AT log file:" + logLocation);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   Map<Xid, RecoveryContextWorker> getRecoveredContexts() {
/* 157 */     return this.recoveredContexts;
/*     */   }
/*     */   
/*     */   public ForeignRecoveryContext getForeignRecoveryContext(Xid xid) {
/* 161 */     RecoveryContextWorker recoveryContextWorker = this.recoveredContexts.get(xid);
/* 162 */     return (recoveryContextWorker == null) ? null : recoveryContextWorker.getContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   synchronized void remove(Xid fxid) {
/* 170 */     this.recoveredContexts.remove(fxid);
/*     */   }
/*     */   
/*     */   private class ContextRunnable
/*     */     implements Runnable {
/*     */     private ContextRunnable() {}
/*     */     
/*     */     public void run() {
/*     */       while (true) {
/* 179 */         doRun();
/*     */         try {
/* 181 */           Thread.sleep(300000L);
/* 182 */         } catch (InterruptedException e) {
/* 183 */           e.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     public void doRun() {
/* 189 */       List<ForeignRecoveryContextManager.RecoveryContextWorker> replayList = new ArrayList<ForeignRecoveryContextManager.RecoveryContextWorker>();
/* 190 */       synchronized (ForeignRecoveryContextManager.this) {
/* 191 */         for (ForeignRecoveryContextManager.RecoveryContextWorker rc : ForeignRecoveryContextManager.this.recoveredContexts.values()) {
/* 192 */           long lastReplay = rc.getLastReplayMillis();
/* 193 */           if (lastReplay == -1L) { replayList.add(rc); continue; }
/*     */           
/*     */           try {
/* 196 */             Transaction transaction = rc.context.getTransaction();
/* 197 */             if (isEligibleForBottomUpQuery(rc, transaction)) {
/* 198 */               if (lastReplay == 0L) rc.setLastReplayMillis(System.currentTimeMillis()); 
/* 199 */               replayList.add(rc);
/*     */             }
/*     */           
/* 202 */           } catch (Throwable e) {
/* 203 */             debug("ForeignRecoveryContextManager$ContextTimerListener.timerExpired error scheduling work for recovery context:" + rc.context + " Exception getting transaction status, transaction may be null:" + e);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 211 */       if (!replayList.isEmpty()) {
/* 212 */         debug("ForeignRecoveryContextManager$ContextTimerListener.timerExpired replayList.size():" + replayList.size());
/*     */       }
/*     */ 
/*     */       
/* 216 */       for (ForeignRecoveryContextManager.RecoveryContextWorker rc : replayList) {
/* 217 */         boolean isScheduled = rc.isScheduled();
/* 218 */         if (!isScheduled && 
/* 219 */           System.currentTimeMillis() - rc.getLastReplayMillis() > (ForeignRecoveryContextManager.INDOUBT_TIMEOUT * rc.getRetryCount())) {
/*     */           
/* 221 */           rc.setScheduled(true);
/* 222 */           rc.incrementRetryCount();
/* 223 */           (new Thread(rc)).start();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isEligibleForBottomUpQuery(ForeignRecoveryContextManager.RecoveryContextWorker rc, Transaction transaction) throws SystemException {
/* 231 */       return (rc.context.isRecovered() || (transaction != null && transaction.getStatus() == 2));
/*     */     }
/*     */ 
/*     */     
/*     */     private void debug(String message) {
/* 236 */       ForeignRecoveryContextManager.LOGGER_ContextRunnable.info(message);
/*     */     }
/*     */   }
/*     */   
/*     */   private class RecoveryContextWorker
/*     */     implements Runnable
/*     */   {
/*     */     ForeignRecoveryContext context;
/*     */     long lastReplayMillis;
/*     */     boolean scheduled;
/* 246 */     private int retryCount = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     RecoveryContextWorker(ForeignRecoveryContext context, int lastReplay) {
/* 257 */       this.context = context;
/* 258 */       this.lastReplayMillis = lastReplay;
/*     */     }
/*     */     
/*     */     synchronized long getLastReplayMillis() {
/* 262 */       return this.lastReplayMillis;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     synchronized void setLastReplayMillis(long lastReplayMillis) {
/* 271 */       this.lastReplayMillis = lastReplayMillis;
/*     */     }
/*     */     
/*     */     synchronized boolean isScheduled() {
/* 275 */       return this.scheduled;
/*     */     }
/*     */     
/*     */     synchronized void setScheduled(boolean b) {
/* 279 */       this.scheduled = b;
/*     */     }
/*     */     
/*     */     public void run() {
/*     */       try {
/* 284 */         Xid xid = this.context.getXid();
/* 285 */         if (xid == null) {
/* 286 */           debug("no Xid mapping for recovered context " + this.context);
/*     */           return;
/*     */         } 
/* 289 */         debug("about to send Prepared recovery call for " + this.context);
/* 290 */         CoordinatorIF coordinatorPort = WSATHelper.getInstance(this.context.getVersion()).getCoordinatorPort(this.context.getEndpointReference(), xid);
/*     */ 
/*     */         
/* 293 */         debug("About to send Prepared recovery call for " + this.context + " with coordinatorPort:" + coordinatorPort);
/* 294 */         Object notification = WSATVersion.getInstance(this.context.getVersion()).newNotificationBuilder().build();
/* 295 */         Transaction transaction = this.context.getTransaction();
/* 296 */         if (isEligibleForBottomUpQuery(this, transaction)) coordinatorPort.preparedOperation(notification); 
/* 297 */         debug("Prepared recovery call for " + this.context + " returned successfully");
/* 298 */       } catch (Throwable e) {
/* 299 */         debug("Prepared recovery call error for " + this.context + " exception:" + e);
/*     */       } finally {
/* 301 */         synchronized (this) {
/* 302 */           this.scheduled = false;
/* 303 */           this.lastReplayMillis = System.currentTimeMillis();
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     boolean isEligibleForBottomUpQuery(RecoveryContextWorker rc, Transaction transaction) throws SystemException {
/* 309 */       return (rc.context.isRecovered() || (transaction != null && transaction.getStatus() == 2));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     void incrementRetryCount() {
/* 315 */       if (this.retryCount * 2 * ForeignRecoveryContextManager.INDOUBT_TIMEOUT < 715827882) this.retryCount *= 2; 
/* 316 */       debug("Next recovery call for " + this.context + " in:" + (this.retryCount * ForeignRecoveryContextManager.INDOUBT_TIMEOUT) + "ms");
/*     */     }
/*     */     
/*     */     int getRetryCount() {
/* 320 */       return this.retryCount;
/*     */     }
/*     */     
/*     */     ForeignRecoveryContext getContext() {
/* 324 */       return this.context;
/*     */     }
/*     */     
/*     */     private void debug(String message) {
/* 328 */       ForeignRecoveryContextManager.LOGGER_RecoveryContextWorker.info(message);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\internal\ForeignRecoveryContextManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */