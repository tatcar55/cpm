/*     */ package com.sun.xml.ws.tx.coord.common.endpoint;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.tx.at.Transactional;
/*     */ import com.sun.xml.ws.tx.at.WSATException;
/*     */ import com.sun.xml.ws.tx.at.WSATFaultFactory;
/*     */ import com.sun.xml.ws.tx.at.WSATHelper;
/*     */ import com.sun.xml.ws.tx.at.WSATSynchronization;
/*     */ import com.sun.xml.ws.tx.at.WSATXAResource;
/*     */ import com.sun.xml.ws.tx.at.common.TransactionManagerImpl;
/*     */ import com.sun.xml.ws.tx.at.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.tx.at.runtime.TransactionIdHelper;
/*     */ import com.sun.xml.ws.tx.at.runtime.TransactionServices;
/*     */ import com.sun.xml.ws.tx.coord.common.EndpointReferenceBuilder;
/*     */ import com.sun.xml.ws.tx.coord.common.RegistrationIF;
/*     */ import com.sun.xml.ws.tx.coord.common.WSCUtil;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
/*     */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterType;
/*     */ import java.util.logging.Level;
/*     */ import javax.transaction.Synchronization;
/*     */ import javax.transaction.SystemException;
/*     */ import javax.transaction.xa.XAResource;
/*     */ import javax.transaction.xa.Xid;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseRegistration<T extends EndpointReference, K, P>
/*     */   implements RegistrationIF<T, K, P>
/*     */ {
/*  66 */   private static final Logger LOGGER = Logger.getLogger(BaseRegistration.class);
/*     */   WebServiceContext context;
/*     */   Transactional.Version version;
/*     */   
/*     */   protected BaseRegistration(WebServiceContext context, Transactional.Version version) {
/*  71 */     this.context = context;
/*  72 */     this.version = version;
/*     */   }
/*     */   
/*     */   public BaseRegisterResponseType<T, P> registerOperation(BaseRegisterType<T, K> parameters) {
/*  76 */     if (WSATHelper.isDebugEnabled())
/*  77 */       LOGGER.info(LocalizationMessages.WSAT_4504_REGISTER_OPERATION_ENTERED(parameters)); 
/*  78 */     String txId = WSATHelper.getInstance().getWSATTidFromWebServiceContextHeaderList(this.context);
/*  79 */     Xid xidFromWebServiceContextHeaderList = TransactionIdHelper.getInstance().wsatid2xid(txId);
/*  80 */     Xid xid = processRegisterTypeAndEnlist(parameters, xidFromWebServiceContextHeaderList);
/*  81 */     BaseRegisterResponseType<T, P> registerResponseType = createRegisterResponseType(xid);
/*     */     try {
/*  83 */       TransactionManagerImpl.getInstance().getTransactionManager().suspend();
/*  84 */     } catch (SystemException ex) {
/*  85 */       ex.printStackTrace();
/*  86 */       Logger.getLogger(BaseRegistration.class).log(Level.SEVERE, null, (Throwable)ex);
/*     */     } 
/*  88 */     if (WSATHelper.isDebugEnabled())
/*  89 */       LOGGER.info(LocalizationMessages.WSAT_4505_REGISTER_OPERATION_EXITED(registerResponseType)); 
/*  90 */     return registerResponseType;
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
/*     */   Xid processRegisterTypeAndEnlist(BaseRegisterType<T, K> parameters, Xid xid) {
/* 107 */     if (parameters == null) throw new WebServiceException("The message contained invalid parameters and could not be processed. Parameter argument for registration was null");
/*     */ 
/*     */     
/* 110 */     String protocolIdentifier = parameters.getProtocolIdentifier();
/* 111 */     if (parameters.isDurable())
/* 112 */       return enlistResource(xid, (T)parameters.getParticipantProtocolService()); 
/* 113 */     if (parameters.isVolatile()) {
/* 114 */       registerSynchronization(xid, (T)parameters.getParticipantProtocolService());
/* 115 */       return null;
/*     */     } 
/* 117 */     LOGGER.severe(LocalizationMessages.WSAT_4580_UNKNOWN_PARTICIPANT_IDENTIFIER(protocolIdentifier));
/* 118 */     throw new WebServiceException("Unknown participant identifier:" + protocolIdentifier);
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
/*     */   BaseRegisterResponseType<T, P> createRegisterResponseType(Xid xid) {
/* 132 */     BaseRegisterResponseType<T, P> registerResponseType = newRegisterResponseType();
/* 133 */     String coordinatorHostAndPort = getCoordinatorAddress();
/* 134 */     String txId = TransactionIdHelper.getInstance().xid2wsatid(xid);
/* 135 */     String branchQual = new String(xid.getBranchQualifier());
/* 136 */     EndpointReferenceBuilder<T> builder = getEndpointReferenceBuilder();
/* 137 */     EndpointReference endpointReference = builder.address(coordinatorHostAndPort).referenceParameter(new Element[] { WSCUtil.referenceElementTxId(txId), WSCUtil.referenceElementBranchQual(branchQual), WSCUtil.referenceElementRoutingInfo() }).build();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     registerResponseType.setCoordinatorProtocolService(endpointReference);
/* 143 */     return registerResponseType;
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
/*     */   private Xid enlistResource(Xid xid, T epr) {
/* 158 */     if (WSATHelper.isDebugEnabled()) LOGGER.info(LocalizationMessages.WSAT_4503_ENLIST_RESOURCE(epr, xid)); 
/* 159 */     WSATXAResource wsatXAResource = new WSATXAResource(this.version, (EndpointReference)epr, xid);
/*     */     try {
/* 161 */       Xid xidFromEnlist = getTransactionServices().enlistResource((XAResource)wsatXAResource, xid);
/* 162 */       wsatXAResource.setXid(xidFromEnlist);
/* 163 */       wsatXAResource.setBranchQualifier(xidFromEnlist.getBranchQualifier());
/* 164 */       return xidFromEnlist;
/* 165 */     } catch (WSATException e) {
/* 166 */       e.printStackTrace();
/* 167 */       throw new WebServiceException(e);
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
/*     */   private void registerSynchronization(Xid xid, T epr) {
/* 179 */     LOGGER.info(LocalizationMessages.WSAT_4525_REGISTER_SYNCHRONIZATION(epr, xid));
/* 180 */     WSATSynchronization wsatXAResource = new WSATSynchronization(this.version, (EndpointReference)epr, xid);
/*     */     try {
/* 182 */       getTransactionServices().registerSynchronization((Synchronization)wsatXAResource, xid);
/* 183 */     } catch (WSATException e) {
/* 184 */       LOGGER.severe(LocalizationMessages.WSAT_4507_EXCEPTION_DURING_REGISTER_SYNCHRONIZATION(), (Throwable)e);
/* 185 */       WSATFaultFactory.throwContextRefusedFault();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract EndpointReferenceBuilder<T> getEndpointReferenceBuilder();
/*     */   
/*     */   protected abstract BaseRegisterResponseType<T, P> newRegisterResponseType();
/*     */   
/*     */   protected abstract String getCoordinatorAddress();
/*     */   
/*     */   protected TransactionServices getTransactionServices() {
/* 196 */     return WSATHelper.getTransactionServices();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\endpoint\BaseRegistration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */