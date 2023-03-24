/*     */ package com.sun.xml.ws.tx.at;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.tx.at.Transactional;
/*     */ import com.sun.xml.ws.tx.at.common.TransactionManagerImpl;
/*     */ import com.sun.xml.ws.tx.at.localization.LocalizationMessages;
/*     */ import javax.transaction.Synchronization;
/*     */ import javax.transaction.SystemException;
/*     */ import javax.transaction.Transaction;
/*     */ import javax.transaction.xa.Xid;
/*     */ import javax.xml.ws.EndpointReference;
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
/*     */ public class WSATSynchronization
/*     */   implements Synchronization
/*     */ {
/*  57 */   private static final Logger LOGGER = Logger.getLogger(WSATSynchronization.class);
/*     */   Xid m_xid;
/*  59 */   String m_status = "UNKNOWN";
/*     */   private static final String UNKNOWN = "UNKNOWN";
/*     */   boolean m_isRemovedFromMap = false;
/*     */   Transactional.Version m_version;
/*     */   EndpointReference m_epr;
/*     */   
/*     */   public WSATSynchronization(EndpointReference epr, Xid xid) {
/*  66 */     this(Transactional.Version.WSAT10, epr, xid);
/*     */   }
/*     */   
/*     */   public WSATSynchronization(Transactional.Version version, EndpointReference epr, Xid xid) {
/*  70 */     this.m_version = version;
/*  71 */     this.m_xid = xid;
/*  72 */     this.m_epr = epr;
/*  73 */     if (WSATHelper.isDebugEnabled())
/*  74 */       LOGGER.info(LocalizationMessages.WSAT_4526_WSAT_SYNCHRONIZATION(this.m_epr.toString(), this.m_xid, "")); 
/*     */   }
/*     */   
/*     */   public void setStatus(String status) {
/*  78 */     this.m_status = status;
/*     */   }
/*     */   
/*     */   public void beforeCompletion() {
/*  82 */     if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4527_BEFORE_COMPLETION_ENTERED(this.m_epr.toString(), this.m_xid)); 
/*     */     try {
/*  84 */       WSATHelper.getInstance().beforeCompletion(this.m_epr, this.m_xid, this);
/*  85 */       synchronized (this) {
/*  86 */         if (this.m_status.equals("Committed")) {
/*  87 */           if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4528_BEFORE_COMPLETION_COMMITTED_BEFORE_WAIT(this.m_epr.toString(), this.m_xid));
/*     */           
/*     */           return;
/*     */         } 
/*  91 */         if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4529_BEFORE_COMPLETION_WAITING_FOR_REPLY(this.m_epr.toString(), this.m_xid));
/*     */         
/*  93 */         wait(WSATHelper.getInstance().getWaitForReplyTimeout());
/*  94 */         if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4530_BEFORE_COMPLETION_FINISHED_WAITING_FOR_REPLY(this.m_epr.toString(), this.m_xid));
/*     */       
/*     */       } 
/*  97 */       LOGGER.info(LocalizationMessages.WSAT_4531_BEFORE_COMPLETION_RECEIVED_REPLY_WITH_STATUS(this.m_status, this.m_epr.toString(), this.m_xid));
/*     */       
/*  99 */       if (!this.m_status.equals("Committed")) {
/* 100 */         LOGGER.severe(LocalizationMessages.WSAT_4532_BEFORE_COMPLETION_UNEXCEPTED_STATUS(this.m_status, this.m_epr.toString(), this.m_xid));
/*     */         
/* 102 */         setRollbackOnly();
/*     */       } 
/* 104 */     } catch (InterruptedException e) {
/* 105 */       LOGGER.severe(LocalizationMessages.WSAT_4533_BEFORE_COMPLETION_INTERRUPTED_EXCEPTION(this.m_epr.toString(), this.m_xid), e);
/* 106 */       setRollbackOnly();
/* 107 */     } catch (Exception e) {
/* 108 */       LOGGER.severe(LocalizationMessages.WSAT_4534_BEFORE_COMPLETION_EXCEPTION(this.m_epr.toString(), this.m_xid), e);
/* 109 */       setRollbackOnly();
/*     */     } finally {
/* 111 */       WSATHelper.getInstance().removeVolatileParticipant(this.m_xid);
/* 112 */       this.m_isRemovedFromMap = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setRollbackOnly() {
/*     */     try {
/* 118 */       Transaction transaction = TransactionManagerImpl.getInstance().getTransaction();
/* 119 */       if (transaction != null) {
/* 120 */         transaction.setRollbackOnly();
/*     */       } else {
/* 122 */         LOGGER.info(LocalizationMessages.WSAT_4536_BEFORE_COMPLETION_TRANSACTION_NULL_DURING_SET_ROLLBACK_ONLY(this.m_epr.toString(), this.m_xid));
/*     */       } 
/* 124 */     } catch (SystemException e) {
/* 125 */       LOGGER.info(LocalizationMessages.WSAT_4535_BEFORE_COMPLETION_SYSTEM_EXCEPTION_DURING_SET_ROLLBACK_ONLY(e, this.m_epr.toString(), this.m_xid));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void afterCompletion(int status) {
/* 131 */     if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4537_AFTER_COMPLETION_STATUS(this.m_epr.toString(), this.m_xid, "" + status));
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Xid getXid() {
/* 141 */     return this.m_xid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 151 */     return (obj instanceof WSATSynchronization && ((WSATSynchronization)obj).getXid().equals(this.m_xid));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 160 */     super.finalize();
/* 161 */     if (!this.m_isRemovedFromMap) WSATHelper.getInstance().removeVolatileParticipant(this.m_xid); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\WSATSynchronization.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */