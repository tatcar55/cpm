/*     */ package com.sun.xml.ws.tx.at;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.tx.at.Transactional;
/*     */ import com.sun.xml.ws.tx.at.common.CoordinatorIF;
/*     */ import com.sun.xml.ws.tx.at.common.ParticipantIF;
/*     */ import com.sun.xml.ws.tx.at.common.WSATVersion;
/*     */ import com.sun.xml.ws.tx.at.common.client.CoordinatorProxyBuilder;
/*     */ import com.sun.xml.ws.tx.at.common.client.ParticipantProxyBuilder;
/*     */ import com.sun.xml.ws.tx.at.internal.BranchXidImpl;
/*     */ import com.sun.xml.ws.tx.at.internal.TransactionServicesImpl;
/*     */ import com.sun.xml.ws.tx.at.internal.XidImpl;
/*     */ import com.sun.xml.ws.tx.at.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.tx.at.runtime.TransactionIdHelper;
/*     */ import com.sun.xml.ws.tx.at.runtime.TransactionServices;
/*     */ import com.sun.xml.ws.tx.dev.WSATRuntimeConfig;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import javax.transaction.Transaction;
/*     */ import javax.transaction.xa.XAException;
/*     */ import javax.transaction.xa.Xid;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.handler.MessageContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSATHelper<T>
/*     */ {
/*  83 */   private static final Logger LOGGER = Logger.getLogger(WSATHelper.class);
/*     */   
/*  85 */   public static final WSATHelper V10 = (new WSATHelper()).WSATVersion(WSATVersion.v10);
/*  86 */   public static final WSATHelper V11 = (new WSATHelper()
/*     */     {
/*     */       public String getRegistrationCoordinatorAddress() {
/*  89 */         return getHostAndPort() + "/__wstx-services/RegistrationPortTypeRPC11";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getCoordinatorAddress() {
/*  94 */         return getHostAndPort() + "/__wstx-services/CoordinatorPortType11";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getParticipantAddress() {
/*  99 */         return getHostAndPort() + "/__wstx-services/ParticipantPortType11";
/*     */       }
/*     */ 
/*     */       
/*     */       public String getRegistrationRequesterAddress() {
/* 104 */         return getHostAndPort() + "/__wstx-services/RegistrationRequesterPortType11";
/*     */       }
/*     */     }).WSATVersion(WSATVersion.v11);
/*     */ 
/*     */ 
/*     */   
/* 110 */   private Map<WSATXAResource, ParticipantIF<T>> m_durableParticipantPortMap = new HashMap<WSATXAResource, ParticipantIF<T>>();
/* 111 */   private final Object m_durableParticipantPortMapLock = new Object();
/* 112 */   private Map<Xid, WSATXAResource> m_durableParticipantXAResourceMap = new HashMap<Xid, WSATXAResource>();
/*     */   
/* 114 */   private final Object m_durableParticipantXAResourceMapLock = new Object();
/*     */   
/* 116 */   private Map<Xid, ParticipantIF<T>> m_volatileParticipantPortMap = new HashMap<Xid, ParticipantIF<T>>();
/* 117 */   private final Object m_volatileParticipantPortMapLock = new Object();
/* 118 */   private Map<Xid, WSATSynchronization> m_volatileParticipantSynchronizationMap = new HashMap<Xid, WSATSynchronization>();
/* 119 */   private final Object m_volatileParticipantSynchronizationMapLock = new Object();
/* 120 */   private final int m_waitForReplyTimeout = (new Integer(System.getProperty("com.sun.xml.ws.tx.at.reply.timeout", "120"))).intValue();
/*     */   
/* 122 */   private final boolean m_isUseLocalServerAddress = Boolean.valueOf(System.getProperty("com.sun.xml.ws.tx.at.use.local.server.address", "false")).booleanValue();
/*     */   
/*     */   protected WSATVersion<T> builderFactory;
/* 125 */   private Map<Xid, Transaction> m_xidToTransactionMap = new HashMap<Xid, Transaction>();
/*     */   
/*     */   WSATHelper WSATVersion(WSATVersion<T> builderFactory) {
/* 128 */     this.builderFactory = builderFactory;
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WSATHelper getInstance() {
/* 138 */     return V10;
/*     */   }
/*     */   
/*     */   public static WSATHelper getInstance(Transactional.Version version) {
/* 142 */     if (version == Transactional.Version.WSAT10 || version == Transactional.Version.DEFAULT)
/* 143 */       return V10; 
/* 144 */     if (version == Transactional.Version.WSAT12 || version == Transactional.Version.WSAT11)
/* 145 */       return V11; 
/* 146 */     throw new WebServiceException("not supported WSAT version");
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
/*     */   public static TransactionServices getTransactionServices() {
/* 158 */     return TransactionServicesImpl.getInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWaitForReplyTimeout() {
/* 168 */     return this.m_waitForReplyTimeout * 1000;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setDurableParticipantStatus(Xid xid, String status) {
/*     */     WSATXAResource wsatXAResourceLock;
/* 180 */     synchronized (this.m_durableParticipantXAResourceMapLock) {
/* 181 */       wsatXAResourceLock = getDurableParticipantXAResourceMap().get(new BranchXidImpl(xid));
/*     */     } 
/* 183 */     if (wsatXAResourceLock == null) {
/* 184 */       return false;
/*     */     }
/* 186 */     synchronized (wsatXAResourceLock) {
/* 187 */       wsatXAResourceLock.setStatus(status);
/* 188 */       wsatXAResourceLock.notifyAll();
/* 189 */       return true;
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
/*     */   boolean setVolatileParticipantStatus(Xid xid, String status) {
/*     */     WSATSynchronization wsatSynchronization;
/* 202 */     synchronized (this.m_volatileParticipantSynchronizationMapLock) {
/* 203 */       wsatSynchronization = this.m_volatileParticipantSynchronizationMap.get(xid);
/*     */     } 
/* 205 */     if (wsatSynchronization == null) {
/* 206 */       if (isDebugEnabled())
/* 207 */         LOGGER.info(LocalizationMessages.WSAT_4581_XID_NOT_IN_DURABLE_RESOURCE_MAP(xid, status)); 
/* 208 */       return false;
/*     */     } 
/* 210 */     synchronized (wsatSynchronization) {
/* 211 */       wsatSynchronization.setStatus(status);
/* 212 */       wsatSynchronization.notifyAll();
/* 213 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeDurableParticipant(WSATXAResource wsatXAResource) {
/* 223 */     synchronized (this.m_durableParticipantPortMapLock) {
/* 224 */       if (getDurableParticipantPortMap().containsKey(wsatXAResource)) {
/* 225 */         this.m_durableParticipantPortMap.remove(wsatXAResource);
/* 226 */         if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4583_DURABLE_PORT_REMOVED(wsatXAResource)); 
/*     */       } 
/*     */     } 
/* 229 */     synchronized (this.m_durableParticipantXAResourceMapLock) {
/* 230 */       if (getDurableParticipantXAResourceMap().containsKey(wsatXAResource.getXid())) {
/* 231 */         getDurableParticipantXAResourceMap().remove(wsatXAResource.getXid());
/* 232 */         if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4584_DURABLE_XARESOURCE_REMOVED(wsatXAResource));
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void removeVolatileParticipant(Xid xid) {
/* 243 */     synchronized (this.m_volatileParticipantPortMapLock) {
/* 244 */       if (this.m_volatileParticipantPortMap.containsKey(new BranchXidImpl(xid))) {
/* 245 */         this.m_volatileParticipantPortMap.remove(new BranchXidImpl(xid));
/* 246 */         if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4585_VOLATILE_PORT_REMOVED(new BranchXidImpl(xid))); 
/*     */       } 
/*     */     } 
/* 249 */     synchronized (this.m_volatileParticipantSynchronizationMapLock) {
/* 250 */       if (this.m_volatileParticipantSynchronizationMap.containsKey(new BranchXidImpl(xid))) {
/* 251 */         this.m_volatileParticipantSynchronizationMap.remove(new BranchXidImpl(xid));
/* 252 */         if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4586_VOLATILE_SYNCHRONIZATION_REMOVED(xid));
/*     */       
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
/*     */   public void prepare(EndpointReference epr, Xid xid, WSATXAResource wsatXAResource) throws XAException {
/* 267 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4587_ABOUT_TO_SEND_PREPARE(xid, Thread.currentThread())); 
/* 268 */     synchronized (this.m_durableParticipantXAResourceMapLock) {
/* 269 */       putInDurableParticipantXAResourceMap(wsatXAResource, xid);
/*     */     } 
/* 271 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4589_DURABLE_PARTICIPANT_XARESOURCE_PLACED_IN_CACHE_FROM_PREPARE(xid)); 
/* 272 */     ParticipantIF<T> port = getDurableParticipantPort(epr, xid, wsatXAResource);
/* 273 */     T notification = (T)this.builderFactory.newNotificationBuilder().build();
/* 274 */     port.prepare(notification);
/* 275 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4588_PREPARE_SENT(xid, Thread.currentThread()));
/*     */   
/*     */   }
/*     */   
/*     */   private void putInDurableParticipantXAResourceMap(WSATXAResource wsatXAResource, Xid xid) {
/* 280 */     byte[] xidBqual = xid.getBranchQualifier();
/* 281 */     byte[] bqual = new byte[xidBqual.length - 1];
/* 282 */     System.arraycopy(xidBqual, 0, bqual, 0, bqual.length);
/*     */     
/* 284 */     XidImpl xidImpl = new XidImpl(xid.getFormatId(), xid.getGlobalTransactionId(), bqual);
/* 285 */     BranchXidImpl branchXid = new BranchXidImpl((Xid)xidImpl);
/* 286 */     getDurableParticipantXAResourceMap().put(branchXid, wsatXAResource);
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
/*     */   public void commit(EndpointReference epr, Xid xid, WSATXAResource wsatXAResource) throws XAException {
/* 300 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4590_ABOUT_TO_SEND_COMMIT(xid, Thread.currentThread())); 
/* 301 */     T notification = (T)this.builderFactory.newNotificationBuilder().build();
/* 302 */     getDurableParticipantPort(epr, xid, wsatXAResource).commit(notification);
/* 303 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4591_COMMIT_SENT(xid, Thread.currentThread()));
/*     */   
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
/*     */   public void rollback(EndpointReference epr, Xid xid, WSATXAResource wsatXAResource) throws XAException {
/* 317 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4592_ABOUT_TO_SEND_ROLLBACK(xid, Thread.currentThread())); 
/* 318 */     synchronized (this.m_durableParticipantXAResourceMapLock) {
/* 319 */       putInDurableParticipantXAResourceMap(wsatXAResource, xid);
/*     */     } 
/* 321 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4594_ROLLBACK_PARTICIPANT_XARESOURCE_PLACED_IN_CACHE(xid)); 
/* 322 */     T notification = (T)this.builderFactory.newNotificationBuilder().build();
/* 323 */     getDurableParticipantPort(epr, xid, wsatXAResource).rollback(notification);
/* 324 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4593_ROLLBACK_SENT(xid, Thread.currentThread()));
/*     */   
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
/*     */   public void beforeCompletion(EndpointReference epr, Xid xid, WSATSynchronization wsatSynchronization) throws SOAPException {
/* 338 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4595_ABOUT_TO_SEND_PREPARE_VOLATILE(xid, Thread.currentThread()));
/*     */     
/* 340 */     T notification = (T)this.builderFactory.newNotificationBuilder().build();
/* 341 */     getVolatileParticipantPort(epr, xid).prepare(notification);
/* 342 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4596_PREPARE_VOLATILE_SENT(xid, Thread.currentThread())); 
/* 343 */     synchronized (this.m_volatileParticipantSynchronizationMapLock) {
/* 344 */       this.m_volatileParticipantSynchronizationMap.put(new BranchXidImpl(xid), wsatSynchronization);
/*     */     } 
/* 346 */     if (isDebugEnabled()) {
/* 347 */       LOGGER.info(LocalizationMessages.WSAT_4597_PREPARE_PARTICIPANT_SYNCHRONIZATION_PLACED_IN_CACHE(xid));
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
/*     */   private ParticipantIF<T> getVolatileParticipantPort(EndpointReference epr, Xid xid) throws SOAPException {
/* 361 */     synchronized (this.m_volatileParticipantPortMapLock) {
/* 362 */       participantPort = this.m_volatileParticipantPortMap.get(new BranchXidImpl(xid));
/*     */     } 
/* 364 */     if (participantPort != null) {
/* 365 */       if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4598_VOLATILE_PARTICIPANT_RETRIEVED_FROM_CACHE(xid)); 
/* 366 */       return participantPort;
/*     */     } 
/* 368 */     ParticipantIF<T> participantPort = getParticipantPort(epr, xid, null);
/* 369 */     synchronized (this.m_volatileParticipantPortMapLock) {
/* 370 */       this.m_volatileParticipantPortMap.put(new BranchXidImpl(xid), participantPort);
/*     */     } 
/* 372 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4599_VOLATILE_PARTICIPANT_PORT_PLACED_IN_CACHE(xid)); 
/* 373 */     return participantPort;
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
/*     */   private ParticipantIF<T> getDurableParticipantPort(EndpointReference epr, Xid xid, WSATXAResource wsatXAResource) throws XAException {
/*     */     ParticipantIF<T> participantPort;
/* 388 */     synchronized (this.m_durableParticipantPortMapLock) {
/* 389 */       participantPort = getDurableParticipantPortMap().get(wsatXAResource);
/*     */     } 
/* 391 */     if (participantPort != null) {
/* 392 */       if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4600_DURABLE_PARTICIPANT_PORT_RETREIVED_FROM_CACHE(xid)); 
/* 393 */       return participantPort;
/*     */     } 
/*     */     try {
/* 396 */       participantPort = getParticipantPort(epr, xid, new String(wsatXAResource.getXid().getBranchQualifier()));
/* 397 */     } catch (SOAPException e) {
/* 398 */       if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4601_CANNOT_CREATE_DURABLE_PARTICIPANT_PORT(xid)); 
/* 399 */       e.printStackTrace();
/* 400 */       XAException xaException = new XAException("Unable to create durable participant port:" + e);
/* 401 */       xaException.initCause(e);
/* 402 */       xaException.errorCode = -7;
/* 403 */       throw xaException;
/*     */     } 
/* 405 */     synchronized (this.m_durableParticipantXAResourceMapLock) {
/* 406 */       putInDurableParticipantXAResourceMap(wsatXAResource, xid);
/*     */     } 
/* 408 */     synchronized (this.m_durableParticipantPortMapLock) {
/* 409 */       getDurableParticipantPortMap().put(wsatXAResource, participantPort);
/*     */     } 
/* 411 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4602_DURABLE_PARTICIPANT_PORT_PLACED_IN_CACHE(xid)); 
/* 412 */     return participantPort;
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
/*     */   public ParticipantIF<T> getParticipantPort(EndpointReference epr, Xid xid, String bqual) throws SOAPException {
/* 428 */     String txId = TransactionIdHelper.getInstance().xid2wsatid(xid);
/* 429 */     ParticipantProxyBuilder<T> proxyBuilder = this.builderFactory.newParticipantProxyBuilder();
/* 430 */     ParticipantIF<T> participantProxyIF = ((ParticipantProxyBuilder)((ParticipantProxyBuilder)proxyBuilder.to(epr)).txIdForReference(txId, bqual)).build();
/* 431 */     if (isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4603_SUCCESSFULLY_CREATED_PARTICIPANT_PORT(participantProxyIF, xid)); 
/* 432 */     return participantProxyIF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CoordinatorIF<T> getCoordinatorPort(EndpointReference epr, Xid xid) {
/* 442 */     if (isDebugEnabled()) debug("WSATHelper.getCoordinatorPort xid:" + xid + " epr:" + epr); 
/* 443 */     String txId = TransactionIdHelper.getInstance().xid2wsatid(xid);
/* 444 */     CoordinatorProxyBuilder<T> proxyBuilder = this.builderFactory.newCoordinatorProxyBuilder();
/* 445 */     CoordinatorIF<T> coordinatorProxy = ((CoordinatorProxyBuilder)((CoordinatorProxyBuilder)proxyBuilder.to(epr)).txIdForReference(txId, "")).build();
/* 446 */     if (isDebugEnabled()) {
/* 447 */       debug("WSATHelper.getCoordinatorPort xid:" + xid + " epr:" + epr + " coordinatorProxy:" + coordinatorProxy);
/*     */     }
/* 449 */     return coordinatorProxy;
/*     */   }
/*     */   
/*     */   public String getRoutingAddress() {
/* 453 */     return "none";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static String getHostAndPort() {
/* 461 */     return WSATRuntimeConfig.getInstance().getHostAndPort();
/*     */   }
/*     */   
/*     */   public String getRegistrationCoordinatorAddress() {
/* 465 */     return getHostAndPort() + "/__wstx-services/RegistrationPortTypeRPC";
/*     */   }
/*     */   
/*     */   public String getCoordinatorAddress() {
/* 469 */     return getHostAndPort() + "/__wstx-services/CoordinatorPortType";
/*     */   }
/*     */   
/*     */   public String getParticipantAddress() {
/* 473 */     return getHostAndPort() + "/__wstx-services/ParticipantPortType";
/*     */   }
/*     */   
/*     */   public String getRegistrationRequesterAddress() {
/* 477 */     return getHostAndPort() + "/__wstx-services/RegistrationRequesterPortType";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Xid getXidFromWebServiceContextHeaderList(WebServiceContext context) {
/* 487 */     String txId = getWSATTidFromWebServiceContextHeaderList(context);
/* 488 */     return TransactionIdHelper.getInstance().wsatid2xid(txId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWSATTidFromWebServiceContextHeaderList(WebServiceContext context) {
/* 498 */     MessageContext messageContext = context.getMessageContext();
/* 499 */     HeaderList headerList = (HeaderList)messageContext.get("com.sun.xml.ws.api.message.HeaderList");
/*     */     
/* 501 */     Iterator<Header> headers = headerList.getHeaders(WSATConstants.TXID_QNAME, false);
/* 502 */     if (!headers.hasNext()) {
/* 503 */       throw new WebServiceException("txid does not exist in header");
/*     */     }
/* 505 */     String txId = ((Header)headers.next()).getStringContent();
/* 506 */     return txId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBQualFromWebServiceContextHeaderList(WebServiceContext context) {
/* 515 */     MessageContext messageContext = context.getMessageContext();
/* 516 */     HeaderList headerList = (HeaderList)messageContext.get("com.sun.xml.ws.api.message.HeaderList");
/*     */     
/* 518 */     Iterator<Header> headers = headerList.getHeaders(WSATConstants.BRANCHQUAL_QNAME, false);
/* 519 */     if (!headers.hasNext())
/* 520 */       throw new WebServiceException("branchqual does not exist in header"); 
/* 521 */     String bqual = ((Header)headers.next()).getStringContent();
/* 522 */     if (bqual != null) bqual = bqual.replaceAll("&#044;", ","); 
/* 523 */     if (isDebugEnabled())
/* 524 */       debug("WSATHelper.getBQualFromWebServiceContextHeaderList returning bqual:" + bqual + " on thread:" + Thread.currentThread()); 
/* 525 */     return bqual;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isDebugEnabled() {
/* 534 */     return true;
/*     */   }
/*     */   
/*     */   public Map<WSATXAResource, ParticipantIF<T>> getDurableParticipantPortMap() {
/* 538 */     return this.m_durableParticipantPortMap;
/*     */   }
/*     */   
/*     */   Map<Xid, WSATXAResource> getDurableParticipantXAResourceMap() {
/* 542 */     return this.m_durableParticipantXAResourceMap;
/*     */   }
/*     */   
/*     */   public Map<Xid, WSATSynchronization> getVolatileParticipantSynchronizationMap() {
/* 546 */     return this.m_volatileParticipantSynchronizationMap;
/*     */   }
/*     */   
/*     */   public Map<Xid, ParticipantIF<T>> getVolatileParticipantPortMap() {
/* 550 */     return this.m_volatileParticipantPortMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putToXidToTransactionMap(Xid xid, Transaction transaction) {
/* 559 */     this.m_xidToTransactionMap.put(new XidImpl(xid), transaction);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Transaction getFromXidToTransactionMap(Xid xid) {
/* 568 */     Transaction transaction = this.m_xidToTransactionMap.get(xid);
/* 569 */     return transaction;
/*     */   }
/*     */   
/*     */   public void removeFromXidToTransactionMap(Xid xid) {
/* 573 */     this.m_xidToTransactionMap.remove(xid);
/*     */   }
/*     */   
/*     */   private void debug(String msg) {
/* 577 */     LOGGER.log(Level.INFO, msg);
/*     */   }
/*     */   
/*     */   public static String assignUUID() {
/* 581 */     return UUID.randomUUID().toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\WSATHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */