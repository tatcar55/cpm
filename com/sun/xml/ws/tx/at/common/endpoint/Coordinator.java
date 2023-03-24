/*     */ package com.sun.xml.ws.tx.at.common.endpoint;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.tx.at.WSATException;
/*     */ import com.sun.xml.ws.tx.at.WSATHelper;
/*     */ import com.sun.xml.ws.tx.at.WSATXAResource;
/*     */ import com.sun.xml.ws.tx.at.common.CoordinatorIF;
/*     */ import com.sun.xml.ws.tx.at.common.WSATVersion;
/*     */ import com.sun.xml.ws.tx.at.internal.XidImpl;
/*     */ import com.sun.xml.ws.tx.at.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.tx.at.runtime.TransactionServices;
/*     */ import javax.transaction.xa.XAResource;
/*     */ import javax.transaction.xa.Xid;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Coordinator<T>
/*     */   implements CoordinatorIF<T>
/*     */ {
/*  66 */   private static final Logger LOGGER = Logger.getLogger(Coordinator.class);
/*     */   
/*     */   private WebServiceContext context;
/*     */   private WSATVersion<T> version;
/*     */   
/*     */   public Coordinator(WebServiceContext m_context, WSATVersion<T> m_version) {
/*  72 */     this.context = m_context;
/*  73 */     this.version = m_version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preparedOperation(T parameters) {
/*  83 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4509_PREPARED_OPERATION_ENTERED(parameters)); 
/*  84 */     Xid xidFromWebServiceContextHeaderList = getXid();
/*  85 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4510_PREPARED_OPERATION(xidFromWebServiceContextHeaderList)); 
/*  86 */     if (!getWSATHelper().setDurableParticipantStatus(xidFromWebServiceContextHeaderList, "Prepared"))
/*     */     {
/*  88 */       replayOperation(parameters);
/*     */     }
/*  90 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4511_PREPARED_OPERATION_EXITED(parameters));
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void abortedOperation(T parameters) {
/*  99 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4512_ABORTED_OPERATION_ENTERED(parameters)); 
/* 100 */     Xid xidFromWebServiceContextHeaderList = getXid();
/* 101 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4513_ABORTED_OPERATION(xidFromWebServiceContextHeaderList)); 
/* 102 */     getWSATHelper().setDurableParticipantStatus(xidFromWebServiceContextHeaderList, "Aborted");
/* 103 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4514_ABORTED_OPERATION_EXITED(parameters));
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readOnlyOperation(T parameters) {
/* 112 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4515_READ_ONLY_OPERATION_ENTERED(parameters)); 
/* 113 */     Xid xidFromWebServiceContextHeaderList = getXid();
/* 114 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4516_READ_ONLY_OPERATION(xidFromWebServiceContextHeaderList)); 
/* 115 */     getWSATHelper().setDurableParticipantStatus(xidFromWebServiceContextHeaderList, "ReadOnly");
/* 116 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4517_READ_ONLY_OPERATION_EXITED(parameters));
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void committedOperation(T parameters) {
/* 125 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4518_COMMITTED_OPERATION_ENTERED(parameters)); 
/* 126 */     Xid xidFromWebServiceContextHeaderList = getXid();
/* 127 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4519_COMMITTED_OPERATION(xidFromWebServiceContextHeaderList)); 
/* 128 */     getWSATHelper().setDurableParticipantStatus(xidFromWebServiceContextHeaderList, "Committed");
/* 129 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4520_COMMITTED_OPERATION_EXITED(parameters));
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replayOperation(T parameters) {
/* 138 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4521_REPLAY_OPERATION_ENTERED(parameters)); 
/* 139 */     Xid xidFromWebServiceContextHeaderList = getXid();
/* 140 */     String wsatTid = getWSATHelper().getWSATTidFromWebServiceContextHeaderList(this.context);
/* 141 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4522_REPLAY_OPERATION(xidFromWebServiceContextHeaderList)); 
/*     */     try {
/* 143 */       getTransactionServices().replayCompletion(wsatTid, (XAResource)createWSATXAResourceForXidFromReplyTo(xidFromWebServiceContextHeaderList));
/*     */     }
/* 145 */     catch (WSATException e) {
/* 146 */       if (isDebugEnabled()) {
/* 147 */         LOGGER.severe(LocalizationMessages.WSAT_4523_REPLAY_OPERATION_SOAPEXCEPTION(xidFromWebServiceContextHeaderList), (Throwable)e);
/*     */       }
/*     */     } 
/* 150 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4514_ABORTED_OPERATION_EXITED(parameters));
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TransactionServices getTransactionServices() {
/* 158 */     return WSATHelper.getTransactionServices();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   WSATXAResource createWSATXAResourceForXidFromReplyTo(Xid xid) {
/* 167 */     HeaderList headerList = (HeaderList)this.context.getMessageContext().get("com.sun.xml.ws.api.message.HeaderList");
/*     */     
/* 169 */     WSEndpointReference wsReplyTo = headerList.getReplyTo(AddressingVersion.W3C, SOAPVersion.SOAP_12);
/* 170 */     EndpointReference replyTo = wsReplyTo.toSpec();
/* 171 */     return new WSATXAResource(this.version.getVersion(), replyTo, xid, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Xid getXid() {
/* 179 */     Xid xid = getWSATHelper().getXidFromWebServiceContextHeaderList(this.context);
/* 180 */     String bqual = getWSATHelper().getBQualFromWebServiceContextHeaderList(this.context);
/* 181 */     return (Xid)new XidImpl(xid.getFormatId(), xid.getGlobalTransactionId(), bqual.getBytes());
/*     */   }
/*     */   
/*     */   boolean isDebugEnabled() {
/* 185 */     return WSATHelper.isDebugEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WSATHelper getWSATHelper() {
/* 193 */     return this.version.getWSATHelper();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\common\endpoint\Coordinator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */