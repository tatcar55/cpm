/*     */ package com.sun.xml.ws.tx.at.common.endpoint;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.tx.at.WSATException;
/*     */ import com.sun.xml.ws.tx.at.WSATHelper;
/*     */ import com.sun.xml.ws.tx.at.common.CoordinatorIF;
/*     */ import com.sun.xml.ws.tx.at.common.ParticipantIF;
/*     */ import com.sun.xml.ws.tx.at.common.WSATVersion;
/*     */ import com.sun.xml.ws.tx.at.common.client.CoordinatorProxyBuilder;
/*     */ import com.sun.xml.ws.tx.at.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.tx.at.runtime.TransactionIdHelper;
/*     */ import com.sun.xml.ws.tx.at.runtime.TransactionServices;
/*     */ import com.sun.xml.ws.tx.dev.WSATRuntimeConfig;
/*     */ import javax.transaction.xa.Xid;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceContext;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Participant<T>
/*     */   implements ParticipantIF<T>
/*     */ {
/*  75 */   private static final Logger LOGGER = Logger.getLogger(Participant.class);
/*     */   
/*     */   private WebServiceContext m_context;
/*     */   private WSATVersion<T> m_version;
/*     */   
/*     */   public Participant(WebServiceContext context, WSATVersion<T> version) {
/*  81 */     this.m_context = context;
/*  82 */     this.m_version = version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prepare(T parameters) {
/*  90 */     if (WSATHelper.isDebugEnabled()) debug("prepare enter:" + parameters); 
/*  91 */     CoordinatorIF<T> coordinatorPort = null;
/*  92 */     byte[] tid = null;
/*     */     try {
/*  94 */       tid = getWSATTid();
/*  95 */       coordinatorPort = getCoordinatorPortType();
/*  96 */       String vote = getTransactionaService().prepare(tid);
/*  97 */       if (WSATHelper.isDebugEnabled())
/*  98 */         debug("preparedOperation complete vote:" + vote + " for tid:" + stringForTidByteArray(tid)); 
/*  99 */       if (vote.equals("ReadOnly"))
/* 100 */       { coordinatorPort.readOnlyOperation(createNotification()); }
/* 101 */       else if (vote.equals("Prepared"))
/* 102 */       { coordinatorPort.preparedOperation(createNotification()); } 
/* 103 */     } catch (WSATException e) {
/* 104 */       e.printStackTrace();
/* 105 */       if (WSATRuntimeConfig.getInstance().isRollbackOnFailedPrepare()) {
/*     */         try {
/* 107 */           log("prepare resulted in exception, issuing rollback for tid:" + stringForTidByteArray(tid) + " " + e);
/* 108 */           getTransactionaService().rollback(tid);
/* 109 */         } catch (WSATException e1) {
/* 110 */           e1.printStackTrace();
/*     */         } 
/*     */       }
/* 113 */       log("prepare resulted in exception, sending aborted for tid:" + stringForTidByteArray(tid) + " " + e);
/* 114 */       if (coordinatorPort != null) { coordinatorPort.abortedOperation(createNotification()); }
/*     */       else
/* 116 */       { log("prepare resulted in exception, unable to send abort as coordinatorPort was nullfor tid:" + stringForTidByteArray(tid) + " " + e);
/*     */         
/* 118 */         throw new WebServiceException("coordinator port null during prepare"); }
/*     */     
/*     */     } 
/* 121 */     if (WSATHelper.isDebugEnabled()) {
/* 122 */       debug("prepare exit:" + parameters + " for tid:" + stringForTidByteArray(tid));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void commit(T parameters) {
/* 130 */     if (WSATHelper.isDebugEnabled()) debug("commit enter:" + parameters); 
/* 131 */     CoordinatorIF<T> coordinatorPort = null;
/* 132 */     boolean isCommitSuccessful = false;
/* 133 */     byte[] tid = null;
/* 134 */     Exception exception = null;
/*     */     try {
/* 136 */       tid = getWSATTid();
/*     */       
/* 138 */       coordinatorPort = getCoordinatorPortTypeForReplyTo();
/* 139 */       if (WSATHelper.isDebugEnabled())
/* 140 */         debug("Participant.commit coordinatorPort:" + coordinatorPort + " for tid:" + stringForTidByteArray(tid)); 
/* 141 */       if (isInForeignContextMap()) getTransactionaService().commit(tid); 
/* 142 */       isCommitSuccessful = true;
/* 143 */       if (WSATHelper.isDebugEnabled())
/* 144 */         debug("committedOperation complete for tid:" + stringForTidByteArray(tid)); 
/* 145 */     } catch (WSATException e) {
/* 146 */       WSATException wSATException1 = e;
/* 147 */       log("WSATException during commit for tid:" + stringForTidByteArray(tid) + " " + e);
/* 148 */     } catch (IllegalArgumentException e) {
/* 149 */       exception = e;
/* 150 */       log("IllegalArgumentException during commit for tid:" + stringForTidByteArray(tid) + " " + e);
/*     */     } 
/* 152 */     if (coordinatorPort == null) {
/* 153 */       if (WSATHelper.isDebugEnabled()) {
/* 154 */         debug("Participant.commit coordinatorPort null, about to create from replyto for tid:" + stringForTidByteArray(tid) + " ");
/*     */       }
/* 156 */       coordinatorPort = getCoordinatorPortType();
/* 157 */       if (WSATHelper.isDebugEnabled()) {
/* 158 */         debug("Participant.commit coordinatorPort null attempting to create from replyto coordinatorPort:" + coordinatorPort + "for tid:" + stringForTidByteArray(tid));
/*     */       }
/* 160 */       if (coordinatorPort == null) {
/* 161 */         throw new WebServiceException("WS-AT coordinator port null during commit for transaction id:" + stringForTidByteArray(tid));
/*     */       }
/* 163 */       if (WSATHelper.isDebugEnabled()) {
/* 164 */         debug("Participant.commit coordinatorPort obtained from replyto:" + coordinatorPort + "for tid:" + stringForTidByteArray(tid));
/*     */       }
/*     */     } 
/* 167 */     if (!isCommitSuccessful && 
/* 168 */       WSATHelper.isDebugEnabled()) {
/* 169 */       debug("Participant.commit was not successful, presuming previous completion occurred and sending committed for tid:" + stringForTidByteArray(tid) + " Exception:" + exception);
/*     */     }
/* 171 */     coordinatorPort.committedOperation(createNotification());
/* 172 */     if (WSATHelper.isDebugEnabled()) {
/* 173 */       debug("committed reply sent, local commit success is " + isCommitSuccessful + " , coordinatorPort:" + coordinatorPort + " for tid:" + stringForTidByteArray(tid));
/*     */     }
/* 175 */     if (WSATHelper.isDebugEnabled()) {
/* 176 */       debug("commit exit:" + parameters + " for tid:" + stringForTidByteArray(tid));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollback(T parameters) {
/* 184 */     if (WSATHelper.isDebugEnabled()) debug("rollback parameters:" + parameters); 
/* 185 */     CoordinatorIF<T> coordinatorPort = null;
/* 186 */     byte[] tid = null;
/*     */     try {
/* 188 */       tid = getWSATTid();
/* 189 */       coordinatorPort = getCoordinatorPortTypeForReplyTo();
/* 190 */       if (isInForeignContextMap()) getTransactionaService().rollback(tid); 
/* 191 */       if (WSATHelper.isDebugEnabled()) debug("rollback abortedOperation complete for tid:" + stringForTidByteArray(tid)); 
/* 192 */     } catch (IllegalArgumentException e) {
/*     */       
/* 194 */       log("rollback IllegalArgumentException for tid:" + stringForTidByteArray(tid) + " " + e);
/* 195 */     } catch (WSATException e) {
/* 196 */       log("rollback WSATException for tid:" + stringForTidByteArray(tid) + " " + e);
/* 197 */       if (e.errorCode != -4 && e.errorCode != -6) {
/* 198 */         throw new WebServiceException("Participant.rollback WSATException for tid:" + stringForTidByteArray(tid) + " " + e);
/*     */       }
/*     */     } 
/* 201 */     if (coordinatorPort != null) {
/* 202 */       coordinatorPort.abortedOperation(createNotification());
/*     */     } else {
/* 204 */       if (WSATHelper.isDebugEnabled()) {
/* 205 */         debug("Participant.rollback coordinatorPort null attempting to create from replyto for tid:" + stringForTidByteArray(tid));
/*     */       }
/* 207 */       coordinatorPort = getCoordinatorPortType();
/* 208 */       if (WSATHelper.isDebugEnabled()) {
/* 209 */         debug("Participant.rollback coordinatorPort null attempting to create from replyto for tid:" + stringForTidByteArray(tid) + " coordinatorPort:" + coordinatorPort);
/*     */       }
/* 211 */       if (coordinatorPort != null) { coordinatorPort.abortedOperation(createNotification()); }
/*     */       else
/* 213 */       { log("Coordinator port null during rollback for tid:" + stringForTidByteArray(tid) + " about to throw exception/fault.");
/*     */         
/* 215 */         throw new WebServiceException("WS-AT Coordinator port null during rollback for tid:" + stringForTidByteArray(tid)); }
/*     */     
/*     */     } 
/*     */     
/* 219 */     if (WSATHelper.isDebugEnabled()) debug("rollback exit:" + parameters + " for tid:" + stringForTidByteArray(tid));
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CoordinatorIF<T> getCoordinatorPortTypeForReplyTo() {
/* 227 */     HeaderList headerList = (HeaderList)this.m_context.getMessageContext().get("com.sun.xml.ws.api.message.HeaderList");
/*     */ 
/*     */     
/* 230 */     AddressingVersion av = this.m_version.getAddressingVersion();
/* 231 */     WSEndpointReference wsReplyTo = headerList.getReplyTo(av, this.m_version.getSOPAVersion());
/* 232 */     if (wsReplyTo != null && !wsReplyTo.isNone() && wsReplyTo.isAnonymous()) {
/* 233 */       Header header = headerList.get(av.fromTag, true);
/* 234 */       if (header != null)
/*     */         try {
/* 236 */           wsReplyTo = header.readAsEPR(av);
/* 237 */         } catch (XMLStreamException e) {
/* 238 */           log("XMLStreamException while reading ReplyTo EndpointReference:" + e);
/*     */         }  
/*     */     } 
/* 241 */     if (wsReplyTo == null || wsReplyTo.isNone() || wsReplyTo.isAnonymous()) {
/* 242 */       return null;
/*     */     }
/* 244 */     EndpointReference replyTo = wsReplyTo.toSpec();
/* 245 */     CoordinatorProxyBuilder<T> builder = (CoordinatorProxyBuilder<T>)this.m_version.newCoordinatorProxyBuilder().to(replyTo);
/* 246 */     CoordinatorIF<T> coordinatorPort = builder.build();
/* 247 */     if (WSATHelper.isDebugEnabled())
/* 248 */       debug("getCoordinatorPortType replytocoordinatorPort:" + coordinatorPort + "for wsReplyTo/from:" + wsReplyTo + " and replyTo/from:" + replyTo); 
/* 249 */     return coordinatorPort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   TransactionServices getTransactionaService() {
/* 258 */     return WSATHelper.getTransactionServices();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] getWSATTid() {
/* 266 */     String tidFromHeader = getWSATHelper().getWSATTidFromWebServiceContextHeaderList(this.m_context).replace("urn:", "").replace("uuid:", "");
/*     */     
/* 268 */     Xid xidFromWebServiceContextHeaderList = TransactionIdHelper.getInstance().wsatid2xid(tidFromHeader);
/* 269 */     byte[] tid = xidFromWebServiceContextHeaderList.getGlobalTransactionId();
/* 270 */     if (WSATHelper.isDebugEnabled()) debug("getWSATTid tid:" + stringForTidByteArray(tid)); 
/* 271 */     return tid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   CoordinatorIF<T> getCoordinatorPortType() {
/* 279 */     String txid = getWSATHelper().getWSATTidFromWebServiceContextHeaderList(this.m_context);
/* 280 */     Xid xidFromWebServiceContextHeaderList = TransactionIdHelper.getInstance().wsatid2xid(txid);
/* 281 */     EndpointReference parentReference = getTransactionaService().getParentReference(xidFromWebServiceContextHeaderList);
/* 282 */     CoordinatorProxyBuilder<T> builder = (CoordinatorProxyBuilder<T>)this.m_version.newCoordinatorProxyBuilder().to(parentReference);
/* 283 */     CoordinatorIF<T> coordinatorPort = builder.build();
/* 284 */     if (WSATHelper.isDebugEnabled()) {
/* 285 */       debug("getCoordinatorPortType coordinatorPort:" + coordinatorPort + "for txid:" + txid + " xid:" + xidFromWebServiceContextHeaderList + " parentRef:" + parentReference);
/*     */     }
/* 287 */     return coordinatorPort;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isInForeignContextMap() {
/*     */     try {
/* 296 */       String txid = getWSATHelper().getWSATTidFromWebServiceContextHeaderList(this.m_context);
/* 297 */       Xid xidFromWebServiceContextHeaderList = TransactionIdHelper.getInstance().wsatid2xid(txid);
/* 298 */       getTransactionaService().getParentReference(xidFromWebServiceContextHeaderList);
/* 299 */     } catch (Throwable t) {
/* 300 */       return false;
/*     */     } 
/* 302 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private String stringForTidByteArray(byte[] tid) {
/* 307 */     return (tid == null) ? null : new String(tid);
/*     */   }
/*     */   
/*     */   protected T createNotification() {
/* 311 */     return (T)this.m_version.newNotificationBuilder().build();
/*     */   }
/*     */   
/*     */   protected WSATHelper getWSATHelper() {
/* 315 */     return this.m_version.getWSATHelper();
/*     */   }
/*     */   
/*     */   private void log(String msg) {
/* 319 */     LOGGER.info(LocalizationMessages.WSAT_4613_WSAT_PARTICIPANT(msg));
/*     */   }
/*     */   
/*     */   private void debug(String msg) {
/* 323 */     LOGGER.info(msg);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\common\endpoint\Participant.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */