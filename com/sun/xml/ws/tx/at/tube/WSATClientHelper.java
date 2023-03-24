/*     */ package com.sun.xml.ws.tx.at.tube;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.Headers;
/*     */ import com.sun.xml.ws.tx.at.WSATHelper;
/*     */ import com.sun.xml.ws.tx.at.common.TransactionImportManager;
/*     */ import com.sun.xml.ws.tx.at.common.TransactionManagerImpl;
/*     */ import com.sun.xml.ws.tx.at.internal.WSATGatewayRM;
/*     */ import com.sun.xml.ws.tx.at.internal.XidImpl;
/*     */ import com.sun.xml.ws.tx.at.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.tx.at.runtime.TransactionIdHelper;
/*     */ import com.sun.xml.ws.tx.coord.common.WSATCoordinationContextBuilder;
/*     */ import com.sun.xml.ws.tx.coord.common.WSCBuilderFactory;
/*     */ import com.sun.xml.ws.tx.coord.common.types.CoordinationContextIF;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import javax.transaction.InvalidTransactionException;
/*     */ import javax.transaction.SystemException;
/*     */ import javax.transaction.Transaction;
/*     */ import javax.transaction.xa.Xid;
/*     */ import javax.xml.bind.JAXBContext;
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
/*     */ 
/*     */ public class WSATClientHelper
/*     */   implements WSATClient
/*     */ {
/*  72 */   private volatile int counter = 0;
/*  73 */   private static final Logger LOGGER = Logger.getLogger(WSATClientHelper.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Header> doHandleRequest(TransactionalAttribute transactionalAttribute, Map<String, Object> map) {
/*     */     try {
/*  83 */       if (TransactionManagerImpl.getInstance().getTransactionManager().getTransaction() == null)
/*  84 */         return new ArrayList<Header>(); 
/*  85 */     } catch (SystemException e) {
/*  86 */       e.printStackTrace();
/*  87 */       return new ArrayList<Header>();
/*     */     } 
/*  89 */     List<Header> addedHeaders = processTransactionalRequest(transactionalAttribute, map);
/*  90 */     return addedHeaders;
/*     */   }
/*     */   
/*     */   public boolean doHandleResponse(Map<String, Object> map) {
/*  94 */     return resumeAndClearXidTxMap(map);
/*     */   }
/*     */   
/*     */   public void doHandleException(Map<String, Object> map) {
/*  98 */     LOGGER.info(LocalizationMessages.WSAT_4569_INBOUND_APPLICATION_MESSAGE());
/*  99 */     resumeAndClearXidTxMap(map);
/*     */   }
/*     */   
/*     */   private boolean resumeAndClearXidTxMap(Map<String, Object> map) {
/* 103 */     if (WSATHelper.isDebugEnabled())
/* 104 */       LOGGER.info(LocalizationMessages.WSAT_4569_INBOUND_APPLICATION_MESSAGE()); 
/* 105 */     Xid xid = getWSATXidFromMap(map);
/* 106 */     if (xid != null) {
/* 107 */       WSATHelper.getInstance().removeFromXidToTransactionMap(xid);
/*     */     }
/* 109 */     Transaction transaction = getWSATTransactionFromMap(map);
/* 110 */     return (transaction == null || resume(transaction));
/*     */   }
/*     */   
/*     */   private Transaction getWSATTransactionFromMap(Map map) {
/* 114 */     Transaction transaction = (Transaction)map.get("wsat.transaction");
/* 115 */     return transaction;
/*     */   }
/*     */   
/*     */   private Xid getWSATXidFromMap(Map map) {
/* 119 */     Xid xid = (Xid)map.get("wsat.transaction.xid");
/* 120 */     return xid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean resume(Transaction transaction) {
/* 129 */     if (WSATHelper.isDebugEnabled())
/* 130 */       LOGGER.info(LocalizationMessages.WSAT_4570_WILL_RESUME_IN_CLIENT_SIDE_HANDLER(transaction, Thread.currentThread())); 
/*     */     try {
/* 132 */       TransactionManagerImpl.getInstance().getTransactionManager().resume(transaction);
/* 133 */       if (WSATHelper.isDebugEnabled())
/* 134 */         LOGGER.info(LocalizationMessages.WSAT_4571_RESUMED_IN_CLIENT_SIDE_HANDLER(transaction, Thread.currentThread())); 
/* 135 */       return true;
/* 136 */     } catch (InvalidTransactionException e) {
/* 137 */       if (WSATHelper.isDebugEnabled()) {
/* 138 */         LOGGER.severe(LocalizationMessages.WSAT_4572_INVALID_TRANSACTION_EXCEPTION_IN_CLIENT_SIDE_HANDLER(transaction, Thread.currentThread()), e);
/*     */       }
/*     */       try {
/* 141 */         transaction.setRollbackOnly();
/* 142 */       } catch (IllegalStateException ex) {
/* 143 */         Logger.getLogger(WSATClientHelper.class).log(Level.SEVERE, null, ex);
/* 144 */         return false;
/* 145 */       } catch (SystemException ex) {
/* 146 */         Logger.getLogger(WSATClientHelper.class).log(Level.SEVERE, null, (Throwable)ex);
/* 147 */         return false;
/*     */       } 
/* 149 */       return false;
/* 150 */     } catch (SystemException e) {
/* 151 */       if (WSATHelper.isDebugEnabled()) {
/* 152 */         LOGGER.severe(LocalizationMessages.WSAT_4573_SYSTEM_EXCEPTION_IN_CLIENT_SIDE_HANDLER(transaction, Thread.currentThread()), (Throwable)e);
/*     */       }
/*     */       try {
/* 155 */         transaction.setRollbackOnly();
/* 156 */         return false;
/* 157 */       } catch (IllegalStateException ex) {
/* 158 */         Logger.getLogger(WSATClientHelper.class).log(Level.SEVERE, null, ex);
/* 159 */         return false;
/* 160 */       } catch (SystemException ex) {
/* 161 */         Logger.getLogger(WSATClientHelper.class).log(Level.SEVERE, null, (Throwable)ex);
/* 162 */         return false;
/*     */       } 
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<Header> processTransactionalRequest(TransactionalAttribute transactionalAttribute, Map<String, Object> map) {
/* 181 */     while (!WSATGatewayRM.isReadyForRuntime) {
/* 182 */       LOGGER.info("WS-AT recovery is enabled but WS-AT is not ready for runtime.  Processing WS-AT recovery log files...");
/* 183 */       WSATGatewayRM.getInstance().recover();
/*     */     } 
/* 185 */     List<Header> headers = new ArrayList<Header>();
/*     */     
/* 187 */     byte[] activityId = WSATHelper.assignUUID().getBytes();
/* 188 */     LOGGER.info("WS-AT activityId:" + activityId);
/* 189 */     XidImpl xidImpl = new XidImpl(1234, (new String(System.currentTimeMillis() + "-" + this.counter++)).getBytes(), new byte[0]);
/* 190 */     String txId = TransactionIdHelper.getInstance().xid2wsatid((Xid)xidImpl);
/* 191 */     long ttl = 0L;
/*     */     try {
/* 193 */       ttl = TransactionImportManager.getInstance().getTransactionRemainingTimeout();
/* 194 */       if (WSATHelper.isDebugEnabled()) {
/* 195 */         LOGGER.info(LocalizationMessages.WSAT_4575_WSAT_INFO_IN_CLIENT_SIDE_HANDLER(txId, Long.valueOf(ttl), "suspendedTransaction", Thread.currentThread()));
/*     */       }
/* 197 */     } catch (SystemException ex) {
/* 198 */       Logger.getLogger(WSATClientHelper.class).log(Level.SEVERE, null, (Throwable)ex);
/*     */     } 
/* 200 */     if (WSATHelper.isDebugEnabled()) {
/* 201 */       LOGGER.info(LocalizationMessages.WSAT_4575_WSAT_INFO_IN_CLIENT_SIDE_HANDLER(txId, Long.valueOf(ttl), "suspendedTransaction", Thread.currentThread()));
/*     */     }
/* 203 */     WSCBuilderFactory builderFactory = WSCBuilderFactory.newInstance(transactionalAttribute.getVersion());
/*     */     
/* 205 */     WSATCoordinationContextBuilder builder = builderFactory.newWSATCoordinationContextBuilder();
/*     */     
/* 207 */     CoordinationContextIF cc = builder.txId(txId).expires(ttl).soapVersion(transactionalAttribute.getSoapVersion()).mustUnderstand(true).build();
/*     */ 
/*     */     
/* 210 */     Header coordinationHeader = Headers.create((JAXBContext)cc.getJAXBRIContext(), cc.getDelegate());
/*     */     
/* 212 */     headers.add(coordinationHeader);
/* 213 */     if (WSATHelper.isDebugEnabled()) {
/* 214 */       LOGGER.info(LocalizationMessages.WSAT_4568_OUTBOUND_APPLICATION_MESSAGE_TRANSACTION_AFTER_ADDING_CONTEXT("suspendedTransaction"));
/*     */     }
/* 216 */     Transaction suspendedTransaction = suspend(map);
/* 217 */     map.put("wsat.transaction.xid", xidImpl);
/* 218 */     WSATHelper.getInstance().putToXidToTransactionMap((Xid)xidImpl, suspendedTransaction);
/* 219 */     return headers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Transaction suspend(Map<String, Object> map) {
/* 228 */     Transaction suspendedTransaction = null;
/*     */     try {
/* 230 */       suspendedTransaction = TransactionManagerImpl.getInstance().getTransactionManager().suspend();
/* 231 */       if (WSATHelper.isDebugEnabled()) {
/* 232 */         LOGGER.info(LocalizationMessages.WSAT_4577_ABOUT_TO_SUSPEND_IN_CLIENT_SIDE_HANDLER(suspendedTransaction, Thread.currentThread()));
/*     */       }
/* 234 */       map.put("wsat.transaction", suspendedTransaction);
/* 235 */       if (WSATHelper.isDebugEnabled()) {
/* 236 */         LOGGER.info(LocalizationMessages.WSAT_4578_SUSPENDED_IN_CLIENT_SIDE_HANDLER(suspendedTransaction, Thread.currentThread()));
/*     */       }
/* 238 */     } catch (SystemException e) {
/*     */       
/* 240 */       return null;
/*     */     } 
/* 242 */     return suspendedTransaction;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\tube\WSATClientHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */