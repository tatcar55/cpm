/*     */ package com.sun.xml.ws.tx.at.tube;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.tx.at.Transactional;
/*     */ import com.sun.xml.ws.tx.at.WSATHelper;
/*     */ import com.sun.xml.ws.tx.at.common.TransactionImportManager;
/*     */ import com.sun.xml.ws.tx.at.common.TransactionManagerImpl;
/*     */ import com.sun.xml.ws.tx.at.internal.ForeignRecoveryContext;
/*     */ import com.sun.xml.ws.tx.at.internal.ForeignRecoveryContextManager;
/*     */ import com.sun.xml.ws.tx.at.internal.WSATGatewayRM;
/*     */ import com.sun.xml.ws.tx.at.internal.XidImpl;
/*     */ import com.sun.xml.ws.tx.at.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.tx.at.runtime.TransactionIdHelper;
/*     */ import com.sun.xml.ws.tx.coord.common.CoordinationContextBuilder;
/*     */ import com.sun.xml.ws.tx.coord.common.RegistrationIF;
/*     */ import com.sun.xml.ws.tx.coord.common.WSCBuilderFactory;
/*     */ import com.sun.xml.ws.tx.coord.common.client.RegistrationMessageBuilder;
/*     */ import com.sun.xml.ws.tx.coord.common.client.RegistrationProxyBuilder;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterType;
/*     */ import com.sun.xml.ws.tx.coord.common.types.CoordinationContextIF;
/*     */ import javax.transaction.xa.Xid;
/*     */ import javax.xml.ws.EndpointReference;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSATServerHelper
/*     */   implements WSATServer
/*     */ {
/*  73 */   private static final Logger LOGGER = Logger.getLogger(WSATServerHelper.class);
/*     */   Xid xidToResume;
/*     */   
/*     */   public void doHandleRequest(HeaderList headers, TransactionalAttribute tx) {
/*  77 */     if (WSATHelper.isDebugEnabled())
/*  78 */       debug("processRequest HeaderList:" + headers + " TransactionalAttribute:" + tx + " isEnabled:" + tx.isEnabled()); 
/*  79 */     CoordinationContextBuilder ccBuilder = CoordinationContextBuilder.headers(headers, tx.getVersion());
/*  80 */     if (ccBuilder != null)
/*  81 */     { while (!WSATGatewayRM.isReadyForRuntime) {
/*  82 */         debug("WS-AT recovery is enabled but WS-AT is not ready for runtime.  Processing WS-AT recovery log files...");
/*  83 */         WSATGatewayRM.getInstance().recover();
/*     */       } 
/*  85 */       this.xidToResume = processIncomingTransaction(ccBuilder); }
/*     */     
/*  87 */     else if (tx.isRequired()) { throw new WebServiceException("transaction context is required to be inflowed"); }
/*     */   
/*     */   }
/*     */   
/*     */   public void doHandleResponse(TransactionalAttribute transactionalAttribute) {
/*  92 */     if (this.xidToResume != null) {
/*  93 */       debug("doHandleResponse about to suspend " + this.xidToResume);
/*  94 */       TransactionImportManager.getInstance().release(this.xidToResume);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void doHandleException(Throwable throwable) {
/*  99 */     if (this.xidToResume != null) {
/* 100 */       debug("doHandleException about to suspend " + this.xidToResume + " Exception:" + throwable);
/* 101 */       TransactionImportManager.getInstance().release(this.xidToResume);
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
/*     */   private Xid processIncomingTransaction(CoordinationContextBuilder builder) {
/*     */     XidImpl xidImpl;
/* 118 */     if (WSATHelper.isDebugEnabled()) debug("in processingIncomingTransaction builder:" + builder);
/*     */     
/* 120 */     CoordinationContextIF cc = builder.buildFromHeader();
/* 121 */     long timeout = cc.getExpires().getValue();
/* 122 */     String tid = cc.getIdentifier().getValue().replace("urn:", "").replaceAll("uuid:", "");
/* 123 */     boolean isRegistered = false;
/* 124 */     Xid foreignXid = null;
/*     */     try {
/* 126 */       foreignXid = WSATHelper.getTransactionServices().importTransaction((int)timeout, tid.getBytes());
/* 127 */       if (foreignXid != null) isRegistered = true; 
/* 128 */       if (!isRegistered) {
/* 129 */         xidImpl = new XidImpl(tid.getBytes());
/* 130 */         register(builder, cc, (Xid)xidImpl, timeout, tid);
/*     */       } 
/* 132 */     } catch (Exception e) {
/* 133 */       if (xidImpl != null) {
/* 134 */         TransactionImportManager.getInstance().release((Xid)xidImpl);
/*     */       } else {
/* 136 */         debug("in processingIncomingTransaction WSATException foreignXid is null");
/*     */       } 
/* 138 */       throw new WebServiceException(e);
/*     */     } 
/* 140 */     return (Xid)xidImpl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void register(CoordinationContextBuilder builder, CoordinationContextIF cc, Xid foreignXid, long timeout, String participantId) {
/* 147 */     participantId = TransactionIdHelper.getInstance().xid2wsatid(foreignXid);
/* 148 */     Transactional.Version version = builder.getVersion();
/* 149 */     WSCBuilderFactory factory = WSCBuilderFactory.newInstance(version);
/* 150 */     RegistrationMessageBuilder rrBuilder = factory.newWSATRegistrationRequestBuilder();
/* 151 */     BaseRegisterType registerType = rrBuilder.durable(true).txId(participantId).routing().build();
/* 152 */     RegistrationProxyBuilder proxyBuilder = factory.newRegistrationProxyBuilder();
/* 153 */     proxyBuilder.to(cc.getRegistrationService()).txIdForReference(participantId).timeout(timeout);
/*     */ 
/*     */ 
/*     */     
/* 157 */     RegistrationIF proxyIF = proxyBuilder.build();
/* 158 */     BaseRegisterResponseType registerResponseType = proxyIF.registerOperation(registerType);
/* 159 */     if (WSATHelper.isDebugEnabled()) debug("Return from registerOperation call:" + registerResponseType); 
/* 160 */     if (registerResponseType != null) {
/* 161 */       EndpointReference epr = registerResponseType.getCoordinatorProtocolService();
/* 162 */       ForeignRecoveryContext frc = ForeignRecoveryContextManager.getInstance().addAndGetForeignRecoveryContextForTidByteArray(foreignXid);
/*     */ 
/*     */       
/* 165 */       frc.setEndpointReference(epr, builder.getVersion());
/* 166 */       TransactionManagerImpl.getInstance().putResource("com.sun.xml.ws.tx.foreignContext", frc);
/*     */     } else {
/*     */       
/* 169 */       log("Sending fault. Context refused registerResponseType is null (this may be due to request timeout)");
/* 170 */       throw new WebServiceException("Sending fault. Context refused registerResponseType is null (this may be due to request timeout)");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void log(String message) {
/* 176 */     LOGGER.info(LocalizationMessages.WSAT_4612_WSAT_SERVERHELPER(message));
/*     */   }
/*     */   
/*     */   private void debug(String message) {
/* 180 */     LOGGER.info(message);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\tube\WSATServerHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */