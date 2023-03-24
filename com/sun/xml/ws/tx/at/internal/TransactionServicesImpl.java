/*     */ package com.sun.xml.ws.tx.at.internal;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.tx.at.WSATException;
/*     */ import com.sun.xml.ws.tx.at.WSATHelper;
/*     */ import com.sun.xml.ws.tx.at.common.TransactionImportManager;
/*     */ import com.sun.xml.ws.tx.at.runtime.TransactionServices;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import javax.transaction.RollbackException;
/*     */ import javax.transaction.Synchronization;
/*     */ import javax.transaction.SystemException;
/*     */ import javax.transaction.Transaction;
/*     */ import javax.transaction.xa.XAException;
/*     */ import javax.transaction.xa.XAResource;
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
/*     */ public class TransactionServicesImpl
/*     */   implements TransactionServices
/*     */ {
/*     */   private static TransactionServices INSTANCE;
/*  62 */   private static Logger LOGGER = Logger.getLogger(TransactionServicesImpl.class);
/*     */ 
/*     */   
/*  65 */   static List<Xid> importedXids = new ArrayList<Xid>();
/*     */ 
/*     */   
/*     */   public static TransactionServices getInstance() {
/*  69 */     if (INSTANCE == null) INSTANCE = new TransactionServicesImpl(); 
/*  70 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   private TransactionServicesImpl() {
/*  74 */     ForeignRecoveryContextManager.getInstance().start();
/*     */   }
/*     */   
/*     */   public byte[] getGlobalTransactionId() {
/*  78 */     return new byte[] { 97 };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeFromImportedXids(Xid xid) {
/*  88 */     importedXids.remove(xid);
/*     */   }
/*     */ 
/*     */   
/*     */   public Xid enlistResource(XAResource resource, Xid xid) throws WSATException {
/*  93 */     WSATGatewayRM wsatgw = WSATGatewayRM.getInstance();
/*  94 */     if (wsatgw == null) throw new WSATException("WS-AT gateway not deployed."); 
/*  95 */     Transaction transaction = WSATHelper.getInstance().getFromXidToTransactionMap(xid);
/*     */     try {
/*  97 */       return wsatgw.registerWSATResource(xid, resource, transaction);
/*  98 */     } catch (IllegalStateException e) {
/*  99 */       throw new WSATException(e);
/* 100 */     } catch (RollbackException e) {
/* 101 */       throw new WSATException(e);
/* 102 */     } catch (SystemException e) {
/* 103 */       throw new WSATException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void registerSynchronization(Synchronization synchronization, Xid xid) throws WSATException {
/* 108 */     debug("regsync");
/*     */   }
/*     */ 
/*     */   
/*     */   public Xid importTransaction(int timeout, byte[] tId) throws WSATException {
/* 113 */     XidImpl xidImpl = new XidImpl(tId);
/* 114 */     if (importedXids.contains(xidImpl)) return xidImpl; 
/* 115 */     TransactionImportManager.getInstance().recreate(xidImpl, timeout);
/* 116 */     importedXids.add(xidImpl);
/* 117 */     return null;
/*     */   }
/*     */   public String prepare(byte[] tId) throws WSATException {
/*     */     int vote;
/* 121 */     debug("prepare:" + new String(tId));
/* 122 */     XidImpl xidImpl = new XidImpl(tId);
/* 123 */     removeFromImportedXids(xidImpl);
/* 124 */     ForeignRecoveryContextManager.getInstance().persist(xidImpl);
/*     */     
/*     */     try {
/* 127 */       vote = TransactionImportManager.getInstance().getXATerminator().prepare(xidImpl);
/* 128 */     } catch (XAException ex) {
/* 129 */       LOGGER.warning(ex.getMessage() + " errorcode:" + ex.errorCode, ex);
/* 130 */       throw new WSATException(ex.getMessage() + " errorcode:" + ex.errorCode, ex);
/*     */     } finally {
/* 132 */       TransactionImportManager.getInstance().release(xidImpl);
/*     */     } 
/* 134 */     if (vote == 3) {
/* 135 */       debug("deleting record due to readonly reply from prepare for txid:" + new String(tId));
/* 136 */       ForeignRecoveryContextManager.getInstance().delete(xidImpl);
/*     */     } 
/* 138 */     return (vote == 0) ? "Prepared" : "ReadOnly";
/*     */   }
/*     */   
/*     */   public void commit(byte[] tId) throws WSATException {
/* 142 */     debug("commit:" + new String(tId));
/* 143 */     XidImpl xidImpl = new XidImpl(tId);
/*     */     try {
/* 145 */       TransactionImportManager.getInstance().getXATerminator().commit(xidImpl, false);
/* 146 */       ForeignRecoveryContextManager.getInstance().delete(xidImpl);
/* 147 */     } catch (XAException ex) {
/* 148 */       LOGGER.warning(ex.getMessage() + " errorcode:" + ex.errorCode, ex);
/* 149 */       throw new WSATException(ex.getMessage() + " errorcode:" + ex.errorCode, ex);
/*     */     } finally {
/* 151 */       TransactionImportManager.getInstance().release(xidImpl);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rollback(byte[] tId) throws WSATException {
/* 156 */     debug("rollback:" + new String(tId));
/* 157 */     XidImpl xidImpl = new XidImpl(tId);
/* 158 */     removeFromImportedXids(xidImpl);
/*     */     try {
/* 160 */       TransactionImportManager.getInstance().getXATerminator().rollback(xidImpl);
/* 161 */       ForeignRecoveryContextManager.getInstance().delete(xidImpl);
/* 162 */     } catch (XAException ex) {
/* 163 */       LOGGER.warning(ex.getMessage() + " errorcode:" + ex.errorCode, ex);
/* 164 */       if (ex.errorCode == -4 || ex.errorCode == -6)
/* 165 */         ForeignRecoveryContextManager.getInstance().delete(xidImpl); 
/* 166 */       throw new WSATException(ex.getMessage() + " errorcode:" + ex.errorCode, ex);
/*     */     } finally {
/* 168 */       TransactionImportManager.getInstance().release(xidImpl);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void replayCompletion(String tId, XAResource xaResource) throws WSATException {
/* 173 */     debug("replayCompletion tid:" + tId + " xaResource:" + xaResource);
/* 174 */     XidImpl xid = new XidImpl(tId.getBytes());
/* 175 */     ForeignRecoveryContext foreignRecoveryContext = ForeignRecoveryContextManager.getInstance().getForeignRecoveryContext(xid);
/*     */     
/* 177 */     if (WSATHelper.isDebugEnabled())
/* 178 */       debug("replayCompletion() tid=" + tId + " xid=" + xid + " foreignRecoveryContext=" + foreignRecoveryContext); 
/* 179 */     if (foreignRecoveryContext == null) {
/*     */       try {
/* 181 */         xaResource.rollback(xid);
/* 182 */         ForeignRecoveryContextManager.getInstance().delete(xid);
/* 183 */       } catch (XAException xae) {
/* 184 */         debug("replayCompletion() tid=" + tId + " (" + xid + "), XAException (" + JTAHelper.xaErrorCodeToString(xae.errorCode, false) + ") rolling back imported transaction: " + xae);
/*     */         
/* 186 */         throw new WSATException("XAException on rollback of subordinate in response to replayCompletion for " + xid + "(tid=" + tId + ")", xae);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EndpointReference getParentReference(Xid xid) {
/* 196 */     debug("getParentReference xid:" + xid);
/* 197 */     if (xid == null) throw new IllegalArgumentException("No subordinate transaction parent reference as xid is null"); 
/* 198 */     ForeignRecoveryContext foreignRecoveryContext = ForeignRecoveryContextManager.getInstance().getForeignRecoveryContext(xid);
/*     */     
/* 200 */     if (foreignRecoveryContext == null)
/* 201 */       throw new AssertionError("No recovery context associated with transaction " + xid); 
/* 202 */     return foreignRecoveryContext.getEndpointReference();
/*     */   }
/*     */   
/*     */   private void debug(String msg) {
/* 206 */     LOGGER.log(Level.INFO, msg);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\internal\TransactionServicesImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */