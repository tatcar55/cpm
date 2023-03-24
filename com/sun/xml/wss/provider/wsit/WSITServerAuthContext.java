/*     */ package com.sun.xml.wss.provider.wsit;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.ws.api.security.secconv.WSSecureConversationRuntimeException;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.runtime.dev.Session;
/*     */ import com.sun.xml.ws.runtime.dev.SessionManager;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.impl.IssuedTokenContextImpl;
/*     */ import com.sun.xml.ws.security.impl.policyconv.SecurityPolicyHolder;
/*     */ import com.sun.xml.ws.security.message.stream.LazyStreamBasedMessage;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.SecurityRecipient;
/*     */ import com.sun.xml.ws.security.opt.impl.util.SOAPUtil;
/*     */ import com.sun.xml.ws.security.policy.SecureConversationToken;
/*     */ import com.sun.xml.ws.security.policy.Token;
/*     */ import com.sun.xml.ws.security.secconv.WSSCContract;
/*     */ import com.sun.xml.ws.security.secconv.WSSCFactory;
/*     */ import com.sun.xml.ws.security.secconv.WSSecureConversationException;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSRequest;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.RequestSecurityToken;
/*     */ import com.sun.xml.wss.NonceManager;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.RealmAuthenticationAdapter;
/*     */ import com.sun.xml.wss.SecurityEnvironment;
/*     */ import com.sun.xml.wss.SubjectAccessor;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.NewSecurityRecipient;
/*     */ import com.sun.xml.wss.impl.PolicyResolver;
/*     */ import com.sun.xml.wss.impl.ProcessingContextImpl;
/*     */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.filter.DumpFilter;
/*     */ import com.sun.xml.wss.impl.misc.DefaultCallbackHandler;
/*     */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*     */ import com.sun.xml.wss.impl.misc.WSITProviderSecurityEnvironment;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import com.sun.xml.wss.jaxws.impl.Constants;
/*     */ import com.sun.xml.wss.provider.wsit.logging.LogStringsMessages;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.net.URI;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.message.AuthException;
/*     */ import javax.security.auth.message.AuthStatus;
/*     */ import javax.security.auth.message.MessageInfo;
/*     */ import javax.security.auth.message.config.ServerAuthContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.SOAPFaultException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSITServerAuthContext
/*     */   extends WSITAuthContextBase
/*     */   implements ServerAuthContext
/*     */ {
/*     */   protected static final String TRUE = "true";
/*     */   static final String SERVICE_ENDPOINT = "SERVICE_ENDPOINT";
/* 137 */   private SessionManager sessionManager = null;
/*     */ 
/*     */ 
/*     */   
/* 141 */   private Set trustConfig = null;
/* 142 */   private Set wsscConfig = null;
/* 143 */   private CallbackHandler handler = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 149 */   WeakReference<WSEndpoint> endPoint = null;
/*     */ 
/*     */   
/* 152 */   WSITServerAuthModule authModule = null;
/*     */ 
/*     */   
/*     */   static final String PIPE_HELPER = "PIPE_HELPER";
/*     */ 
/*     */   
/*     */   public WSITServerAuthContext(String operation, Subject subject, Map<Object, Object> map, CallbackHandler callbackHandler) {
/* 159 */     super(map);
/*     */ 
/*     */ 
/*     */     
/* 163 */     this.endPoint = new WeakReference<WSEndpoint>((WSEndpoint)map.get("ENDPOINT"));
/* 164 */     boolean isSC = false;
/* 165 */     if (!getInBoundSCP(null).isEmpty() || !getOutBoundSCP(null).isEmpty()) {
/* 166 */       isSC = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 174 */     Set configAssertions = null;
/* 175 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/* 176 */       Iterator<SecurityPolicyHolder> it = p.getInMessagePolicyMap().values().iterator();
/* 177 */       while (it.hasNext()) {
/* 178 */         SecurityPolicyHolder holder = it.next();
/* 179 */         if (configAssertions != null) {
/* 180 */           configAssertions.addAll(holder.getConfigAssertions("http://schemas.sun.com/2006/03/wss/server"));
/*     */         } else {
/* 182 */           configAssertions = holder.getConfigAssertions("http://schemas.sun.com/2006/03/wss/server");
/*     */         } 
/* 184 */         if (this.trustConfig != null) {
/* 185 */           this.trustConfig.addAll(holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/trust/server"));
/*     */         } else {
/* 187 */           this.trustConfig = holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/trust/server");
/*     */         } 
/* 189 */         if (this.wsscConfig != null) {
/* 190 */           this.wsscConfig.addAll(holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/sc/server")); continue;
/*     */         } 
/* 192 */         this.wsscConfig = holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/sc/server");
/*     */       } 
/*     */     } 
/*     */     
/* 196 */     String isGF = System.getProperty("com.sun.aas.installRoot");
/* 197 */     Properties props = new Properties();
/* 198 */     if (isGF != null) {
/*     */       
/*     */       try {
/* 201 */         populateConfigProperties(configAssertions, props);
/* 202 */         String jmacHandler = props.getProperty("jmac.callbackhandler");
/* 203 */         if (jmacHandler != null) {
/* 204 */           this.handler = loadGFHandler(false, jmacHandler);
/* 205 */         } else if (callbackHandler != null) {
/* 206 */           this.handler = callbackHandler;
/*     */         } 
/* 208 */         if (this.handler == null) {
/* 209 */           this.handler = loadGFHandler(false, jmacHandler);
/*     */         }
/*     */         
/* 212 */         this.secEnv = (SecurityEnvironment)new WSITProviderSecurityEnvironment(this.handler, map, props);
/* 213 */       } catch (XWSSecurityException ex) {
/* 214 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0048_ERROR_POPULATING_SERVER_CONFIG_PROP(), (Throwable)ex);
/*     */         
/* 216 */         throw new WebServiceException(LogStringsMessages.WSITPVD_0048_ERROR_POPULATING_SERVER_CONFIG_PROP(), ex);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 224 */       this.handler = configureServerHandler(configAssertions, props);
/* 225 */       String jmacHandler = props.getProperty("jmac.callbackhandler");
/* 226 */       if (jmacHandler != null) {
/*     */         try {
/* 228 */           this.handler = loadGFHandler(false, jmacHandler);
/* 229 */           this.secEnv = (SecurityEnvironment)new WSITProviderSecurityEnvironment(this.handler, map, props);
/* 230 */         } catch (XWSSecurityException ex) {
/* 231 */           log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0048_ERROR_POPULATING_SERVER_CONFIG_PROP(), (Throwable)ex);
/*     */           
/* 233 */           throw new WebServiceException(LogStringsMessages.WSITPVD_0048_ERROR_POPULATING_SERVER_CONFIG_PROP(), ex);
/*     */         } 
/*     */       } else {
/*     */         
/* 237 */         this.secEnv = (SecurityEnvironment)new DefaultSecurityEnvironmentImpl(this.handler, props);
/*     */       } 
/*     */     } 
/*     */     
/* 241 */     this.sessionManager = SessionManager.getSessionManager(this.endPoint.get(), isSC, props);
/*     */ 
/*     */     
/* 244 */     this.authModule = new WSITServerAuthModule();
/*     */     try {
/* 246 */       this.authModule.initialize(null, null, null, map);
/* 247 */     } catch (AuthException e) {
/* 248 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0028_ERROR_INIT_AUTH_MODULE(), (Throwable)e);
/* 249 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0028_ERROR_INIT_AUTH_MODULE(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthStatus validateRequest(MessageInfo messageInfo, Subject clientSubject, Subject serviceSubject) throws AuthException {
/*     */     try {
/* 257 */       Packet packet = getRequestPacket(messageInfo);
/* 258 */       HaContext.initFrom(packet);
/* 259 */       Packet ret = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 267 */         ret = validateRequest(packet, clientSubject, serviceSubject, messageInfo.getMap());
/* 268 */       } catch (XWSSecurityException ex) {
/* 269 */         throw getSOAPFaultException(ex);
/*     */       } 
/*     */       
/* 272 */       if (messageInfo.getMap().get("THERE_WAS_A_FAULT") != null) {
/* 273 */         setResponsePacket(messageInfo, ret);
/* 274 */         return AuthStatus.SEND_FAILURE;
/*     */       } 
/*     */       
/* 277 */       boolean isSCMessage = (messageInfo.getMap().get("IS_SC_ISSUE") != null || messageInfo.getMap().get("IS_SC_CANCEL") != null);
/*     */       
/* 279 */       if (isSCMessage) {
/*     */         
/* 281 */         setResponsePacket(messageInfo, ret);
/*     */         
/* 283 */         return AuthStatus.SEND_SUCCESS;
/*     */       } 
/*     */       
/* 286 */       setRequestPacket(messageInfo, ret);
/*     */       
/* 288 */       return AuthStatus.SUCCESS;
/*     */     } finally {
/* 290 */       HaContext.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuthStatus secureResponse(MessageInfo messageInfo, Subject serviceSubject) throws AuthException {
/*     */     try {
/* 302 */       Packet retPacket = getResponsePacket(messageInfo);
/* 303 */       HaContext.initFrom(retPacket);
/*     */ 
/*     */ 
/*     */       
/* 307 */       Packet ret = null;
/*     */       try {
/* 309 */         ret = secureResponse(retPacket, serviceSubject, messageInfo.getMap());
/* 310 */       } catch (XWSSecurityException ex) {
/*     */         
/* 312 */         throw getSOAPFaultException(ex);
/*     */       } 
/*     */       
/* 315 */       setResponsePacket(messageInfo, ret);
/*     */       
/* 317 */       if (messageInfo.getMap().get("THERE_WAS_A_FAULT") != null) {
/* 318 */         return AuthStatus.SEND_FAILURE;
/*     */       }
/*     */       
/* 321 */       return AuthStatus.SUCCESS;
/*     */     } finally {
/* 323 */       HaContext.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cleanSubject(MessageInfo messageInfo, Subject subject) throws AuthException {
/* 328 */     this.issuedTokenContextMap.clear();
/* 329 */     SessionManager.removeSessionManager(this.endPoint.get());
/* 330 */     NonceManager.deleteInstance(this.endPoint.get());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet validateRequest(Packet packet, Subject clientSubject, Subject serviceSubject, Map<Object, Object> sharedState) throws XWSSecurityException {
/* 336 */     Message msg = packet.getInternalMessage();
/*     */     
/* 338 */     boolean isSCIssueMessage = false;
/* 339 */     boolean isSCCancelMessage = false;
/* 340 */     boolean isTrustMessage = false;
/* 341 */     String msgId = null;
/* 342 */     String action = null;
/*     */     
/* 344 */     boolean thereWasAFault = false;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 349 */     ProcessingContext ctx = initializeInboundProcessingContext(packet);
/*     */ 
/*     */     
/* 352 */     ctx.setExtraneousProperty("javax.security.auth.Subject", clientSubject);
/* 353 */     PolicyResolver pr = PolicyResolverFactory.createPolicyResolver(this.policyAlternatives, cachedOperation(packet), this.pipeConfig, this.addVer, false, this.rmVer, this.mcVer);
/*     */     
/* 355 */     ctx.setExtraneousProperty("OperationResolver", pr);
/*     */     
/* 357 */     ctx.setExtraneousProperty("SessionManager", this.sessionManager);
/*     */     try {
/* 359 */       if (!this.optimized) {
/* 360 */         SOAPMessage soapMessage = msg.readAsSOAPMessage();
/* 361 */         soapMessage = verifyInboundMessage(soapMessage, ctx);
/* 362 */         msg = Messages.create(soapMessage);
/*     */       } else {
/* 364 */         msg = verifyInboundMessage(msg, ctx);
/*     */       } 
/* 366 */     } catch (WssSoapFaultException ex) {
/* 367 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), (Throwable)ex);
/*     */       
/* 369 */       thereWasAFault = true;
/* 370 */       SOAPFaultException sfe = SOAPUtil.getSOAPFaultException(ex, this.soapFactory, this.soapVersion);
/* 371 */       msg = Messages.create(sfe, this.soapVersion);
/* 372 */     } catch (XWSSecurityException xwse) {
/* 373 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), (Throwable)xwse);
/*     */       
/* 375 */       thereWasAFault = true;
/* 376 */       SOAPFaultException sfe = SOAPUtil.getSOAPFaultException((Exception)xwse, this.soapFactory, this.soapVersion);
/* 377 */       msg = Messages.create(sfe, this.soapVersion);
/*     */     }
/* 379 */     catch (XWSSecurityRuntimeException xwse) {
/* 380 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), (Throwable)xwse);
/*     */       
/* 382 */       thereWasAFault = true;
/* 383 */       SOAPFaultException sfe = SOAPUtil.getSOAPFaultException((Exception)xwse, this.soapFactory, this.soapVersion);
/* 384 */       msg = Messages.create(sfe, this.soapVersion);
/*     */     }
/* 386 */     catch (WebServiceException xwse) {
/* 387 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), xwse);
/*     */       
/* 389 */       thereWasAFault = true;
/* 390 */       SOAPFaultException sfe = SOAPUtil.getSOAPFaultException(xwse, this.soapFactory, this.soapVersion);
/* 391 */       msg = Messages.create(sfe, this.soapVersion);
/*     */     }
/* 393 */     catch (WSSecureConversationRuntimeException wsre) {
/* 394 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), (Throwable)wsre);
/*     */       
/* 396 */       thereWasAFault = true;
/* 397 */       QName faultCode = wsre.getFaultCode();
/* 398 */       if (faultCode != null) {
/* 399 */         faultCode = new QName(this.wsscVer.getNamespaceURI(), faultCode.getLocalPart());
/*     */       }
/* 401 */       SOAPFaultException sfe = SOAPUtil.getSOAPFaultException(faultCode, wsre, this.soapFactory, this.soapVersion);
/* 402 */       msg = Messages.create(sfe, this.soapVersion);
/* 403 */     } catch (SOAPException se) {
/*     */       
/* 405 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), se);
/*     */       
/* 407 */       thereWasAFault = true;
/* 408 */       SOAPFaultException sfe = SOAPUtil.getSOAPFaultException(se, this.soapFactory, this.soapVersion);
/* 409 */       msg = Messages.create(sfe, this.soapVersion);
/* 410 */     } catch (Exception ex) {
/*     */       
/* 412 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), ex);
/*     */       
/* 414 */       thereWasAFault = true;
/* 415 */       SOAPFaultException sfe = SOAPUtil.getSOAPFaultException(ex, this.soapFactory, this.soapVersion);
/* 416 */       msg = Messages.create(sfe, this.soapVersion);
/*     */     } 
/*     */     
/* 419 */     if (thereWasAFault) {
/* 420 */       sharedState.put("THERE_WAS_A_FAULT", Boolean.valueOf(thereWasAFault));
/* 421 */       if (isAddressingEnabled()) {
/* 422 */         if (this.optimized) {
/* 423 */           packet.setMessage(((JAXBFilterProcessingContext)ctx).getPVMessage());
/*     */         }
/* 425 */         Packet ret = packet.createServerResponse(msg, this.addVer, this.soapVersion, this.addVer.getDefaultFaultAction());
/*     */         
/* 427 */         return ret;
/*     */       } 
/* 429 */       packet.setMessage(msg);
/* 430 */       return packet;
/*     */     } 
/*     */ 
/*     */     
/* 434 */     packet.setMessage(msg);
/*     */     
/* 436 */     if (isAddressingEnabled()) {
/* 437 */       action = getAction(packet);
/* 438 */       if (this.wsscVer.getSCTRequestAction().equals(action) || this.wsscVer.getSCTRenewRequestAction().equals(action)) {
/* 439 */         isSCIssueMessage = true;
/* 440 */         sharedState.put("IS_SC_ISSUE", "true");
/* 441 */         if (this.wsscConfig != null) {
/* 442 */           packet.invocationProperties.put("http://schemas.sun.com/ws/2006/05/sc/server", this.wsscConfig.iterator());
/*     */         }
/*     */       }
/* 445 */       else if (this.wsscVer.getSCTCancelRequestAction().equals(action)) {
/* 446 */         isSCCancelMessage = true;
/* 447 */         sharedState.put("IS_SC_CANCEL", "true");
/* 448 */       } else if (this.wsTrustVer.getIssueRequestAction().equals(action) || this.wsTrustVer.getValidateRequestAction().equals(action)) {
/*     */         
/* 450 */         isTrustMessage = true;
/* 451 */         sharedState.put("IS_TRUST_MESSAGE", "true");
/* 452 */         sharedState.put("TRUST_REQUEST_ACTION", action);
/*     */ 
/*     */         
/* 455 */         if (this.trustConfig != null) {
/* 456 */           packet.invocationProperties.put("http://schemas.sun.com/ws/2006/05/trust/server", this.trustConfig.iterator());
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 461 */         packet.invocationProperties.put("SecurityEnvironment", this.secEnv);
/* 462 */         packet.invocationProperties.put("WSTrustVersion", this.wsTrustVer);
/* 463 */         IssuedTokenContext ictx = ((ProcessingContextImpl)ctx).getTrustContext();
/* 464 */         if (ictx != null && ictx.getAuthnContextClass() != null) {
/* 465 */           packet.invocationProperties.put("AuthnContextClass", ictx.getAuthnContextClass());
/*     */         }
/*     */       } 
/*     */       
/* 469 */       if (isSCIssueMessage) {
/* 470 */         List<PolicyAssertion> policies = getInBoundSCP(packet.getMessage());
/* 471 */         if (!policies.isEmpty()) {
/* 472 */           packet.invocationProperties.put(Constants.SC_ASSERTION, policies.get(0));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 477 */     if (!isSCIssueMessage) {
/* 478 */       WSDLBoundOperation cachedOperation = cacheOperation(msg, packet);
/* 479 */       if (cachedOperation == null && 
/* 480 */         this.addVer != null) {
/* 481 */         cachedOperation = getWSDLOpFromAction(packet, true);
/* 482 */         packet.invocationProperties.put("WSDL_BOUND_OPERATION", cachedOperation);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 487 */     sharedState.put("VALIDATE_REQ_PACKET", packet);
/*     */     
/* 489 */     Packet retPacket = null;
/*     */     
/* 491 */     if (isSCIssueMessage || isSCCancelMessage) {
/*     */       
/* 493 */       retPacket = invokeSecureConversationContract(packet, ctx, isSCIssueMessage);
/*     */ 
/*     */       
/* 496 */       retPacket = secureResponse(retPacket, serviceSubject, sharedState);
/*     */     } else {
/* 498 */       updateSCSessionInfo(packet);
/* 499 */       retPacket = packet;
/*     */     } 
/*     */     
/* 502 */     return retPacket;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet secureResponse(Packet retPacket, Subject serviceSubject, Map<String, Boolean> sharedState) throws XWSSecurityException {
/* 508 */     boolean isSCIssueMessage = (sharedState.get("IS_SC_ISSUE") != null);
/* 509 */     boolean isSCCancelMessage = (sharedState.get("IS_SC_CANCEL") != null);
/* 510 */     boolean isTrustMessage = (sharedState.get("IS_TRUST_MESSAGE") != null);
/*     */     
/* 512 */     Packet packet = (Packet)sharedState.get("VALIDATE_REQ_PACKET");
/* 513 */     Boolean thereWasAFaultSTR = (Boolean)sharedState.get("THERE_WAS_A_FAULT");
/* 514 */     boolean thereWasAFault = (thereWasAFaultSTR != null) ? thereWasAFaultSTR.booleanValue() : false;
/*     */     
/* 516 */     if (thereWasAFault) {
/* 517 */       return retPacket;
/*     */     }
/*     */ 
/*     */     
/* 521 */     if (!this.optimized) {
/*     */       try {
/* 523 */         SOAPMessage sm = retPacket.getMessage().readAsSOAPMessage();
/* 524 */         Message newMsg = Messages.create(sm);
/* 525 */         retPacket.setMessage(newMsg);
/* 526 */       } catch (SOAPException ex) {
/* 527 */         throw new WebServiceException(ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 532 */     ProcessingContext ctx = initializeOutgoingProcessingContext(retPacket, isSCIssueMessage);
/* 533 */     ctx.setExtraneousProperty("SessionManager", this.sessionManager);
/* 534 */     Message msg = retPacket.getMessage();
/*     */ 
/*     */     
/*     */     try {
/* 538 */       if (ctx.getSecurityPolicy() != null && ((MessagePolicy)ctx.getSecurityPolicy()).size() > 0) {
/* 539 */         if (!this.optimized) {
/* 540 */           SOAPMessage soapMessage = msg.readAsSOAPMessage();
/* 541 */           soapMessage = secureOutboundMessage(soapMessage, ctx);
/* 542 */           msg = Messages.create(soapMessage);
/*     */         } else {
/* 544 */           msg = secureOutboundMessage(msg, ctx);
/*     */         } 
/*     */       }
/* 547 */     } catch (WssSoapFaultException ex) {
/* 548 */       sharedState.put("THERE_WAS_A_FAULT", Boolean.valueOf(true));
/* 549 */       msg = Messages.create(getSOAPFault(ex));
/* 550 */     } catch (SOAPException se) {
/*     */       
/* 552 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0029_ERROR_SECURING_OUTBOUND_MSG(), se);
/*     */       
/* 554 */       throw new WebServiceException(LogStringsMessages.WSITPVD_0029_ERROR_SECURING_OUTBOUND_MSG(), se);
/*     */     } finally {
/*     */       
/* 557 */       if (isSCCancel(retPacket)) {
/* 558 */         removeContext(packet);
/*     */       }
/*     */     } 
/* 561 */     resetCachedOperation(retPacket);
/* 562 */     retPacket.setMessage(msg);
/* 563 */     return retPacket;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SOAPMessage verifyInboundMessage(SOAPMessage message, ProcessingContext ctx) throws WssSoapFaultException, XWSSecurityException {
/* 568 */     if (debug) {
/* 569 */       DumpFilter.process(ctx);
/*     */     }
/* 571 */     ctx.setSOAPMessage(message);
/* 572 */     NewSecurityRecipient.validateMessage(ctx);
/* 573 */     return ctx.getSOAPMessage();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Message verifyInboundMessage(Message message, ProcessingContext ctx) throws XWSSecurityException {
/* 578 */     JAXBFilterProcessingContext context = (JAXBFilterProcessingContext)ctx;
/*     */     
/* 580 */     LazyStreamBasedMessage lazyStreamMessage = (LazyStreamBasedMessage)message;
/* 581 */     AttachmentSet attachSet = null;
/* 582 */     if (!LazyStreamBasedMessage.mtomLargeData()) {
/* 583 */       attachSet = lazyStreamMessage.getAttachments();
/*     */     }
/* 585 */     SecurityRecipient recipient = null;
/* 586 */     if (attachSet == null || attachSet.isEmpty()) {
/* 587 */       recipient = new SecurityRecipient(lazyStreamMessage.readMessage(), this.soapVersion);
/*     */     } else {
/* 589 */       recipient = new SecurityRecipient(lazyStreamMessage.readMessage(), this.soapVersion, attachSet);
/*     */     } 
/* 591 */     recipient.setBodyPrologue(lazyStreamMessage.getBodyPrologue());
/* 592 */     recipient.setBodyEpilogue(lazyStreamMessage.getBodyEpilogue());
/*     */     
/* 594 */     return recipient.validateMessage(context);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected ProcessingContext initializeOutgoingProcessingContext(Packet packet, boolean isSCMessage) {
/* 600 */     ProcessingContextImpl ctx = null;
/* 601 */     if (this.optimized) {
/* 602 */       JAXBFilterProcessingContext jAXBFilterProcessingContext = new JAXBFilterProcessingContext(packet.invocationProperties);
/* 603 */       jAXBFilterProcessingContext.setAddressingVersion(this.addVer);
/* 604 */       jAXBFilterProcessingContext.setSOAPVersion(this.soapVersion);
/*     */     } else {
/* 606 */       ctx = new ProcessingContextImpl(packet.invocationProperties);
/*     */     } 
/* 608 */     if (this.addVer != null) {
/* 609 */       ctx.setAction(getAction(packet));
/*     */     }
/*     */     
/* 612 */     ctx.setTimestampTimeout(this.timestampTimeOut);
/* 613 */     ctx.setSecurityPolicyVersion(this.spVersion.namespaceUri);
/*     */     try {
/* 615 */       MessagePolicy policy = null;
/* 616 */       PolicyAlternativeHolder applicableAlternative = resolveAlternative(packet, isSCMessage);
/*     */ 
/*     */       
/* 619 */       if (packet.getMessage().isFault()) {
/* 620 */         policy = getOutgoingFaultPolicy(packet);
/* 621 */       } else if (isRMMessage(packet) || isMakeConnectionMessage(packet)) {
/* 622 */         SecurityPolicyHolder holder = applicableAlternative.getOutProtocolPM().get("RM");
/* 623 */         policy = holder.getMessagePolicy();
/* 624 */       } else if (isSCCancel(packet)) {
/* 625 */         SecurityPolicyHolder holder = applicableAlternative.getOutProtocolPM().get("SC-CANCEL");
/* 626 */         policy = holder.getMessagePolicy();
/*     */       } else {
/* 628 */         policy = getOutgoingXWSSecurityPolicy(packet, isSCMessage);
/*     */       } 
/*     */       
/* 631 */       if (debug && policy != null) {
/* 632 */         policy.dumpMessages(true);
/*     */       }
/*     */ 
/*     */       
/* 636 */       if (policy != null) {
/* 637 */         ctx.setSecurityPolicy((SecurityPolicy)policy);
/*     */       }
/* 639 */       if (isTrustMessage(packet)) {
/* 640 */         ctx.isTrustMessage(true);
/*     */       }
/*     */ 
/*     */       
/* 644 */       if (isSCMessage) {
/* 645 */         ctx.setAlgorithmSuite(policy.getAlgorithmSuite());
/*     */       } else {
/* 647 */         ctx.setAlgorithmSuite(getAlgoSuite(getBindingAlgorithmSuite(packet)));
/*     */       } 
/* 649 */       ctx.setSecurityEnvironment(this.secEnv);
/* 650 */       ctx.isInboundMessage(false);
/*     */       
/* 652 */       Map<Object, Object> extProps = ctx.getExtraneousProperties();
/* 653 */       extProps.put("WSDLPort", this.pipeConfig.getWSDLPort());
/* 654 */     } catch (XWSSecurityException e) {
/* 655 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0006_PROBLEM_INIT_OUT_PROC_CONTEXT(), (Throwable)e);
/*     */       
/* 657 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0006_PROBLEM_INIT_OUT_PROC_CONTEXT(), e);
/*     */     } 
/*     */     
/* 660 */     return (ProcessingContext)ctx;
/*     */   }
/*     */   
/*     */   private void removeContext(Packet packet) {
/* 664 */     SecurityContextToken sct = (SecurityContextToken)packet.invocationProperties.get("Incoming_SCT");
/* 665 */     if (sct != null) {
/* 666 */       String strId = sct.getIdentifier().toString();
/* 667 */       if (strId != null) {
/* 668 */         this.issuedTokenContextMap.remove(strId);
/* 669 */         this.sessionManager.terminateSession(strId);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected MessagePolicy getOutgoingXWSSecurityPolicy(Packet packet, boolean isSCMessage) {
/* 677 */     if (isSCMessage) {
/* 678 */       Token scToken = (Token)packet.invocationProperties.get(Constants.SC_ASSERTION);
/* 679 */       return getOutgoingXWSBootstrapPolicy(scToken);
/*     */     } 
/*     */ 
/*     */     
/* 683 */     MessagePolicy mp = null;
/* 684 */     PolicyAlternativeHolder applicableAlternative = resolveAlternative(packet, isSCMessage);
/*     */     
/* 686 */     WSDLBoundOperation wsdlOperation = cachedOperation(packet);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 692 */     if (applicableAlternative.getOutMessagePolicyMap() == null)
/*     */     {
/* 694 */       return new MessagePolicy();
/*     */     }
/*     */     
/* 697 */     if (isTrustMessage(packet)) {
/*     */       
/* 699 */       wsdlOperation = getWSDLOpFromAction(packet, false);
/* 700 */       cacheOperation(wsdlOperation, packet);
/*     */     } 
/*     */     
/* 703 */     SecurityPolicyHolder sph = applicableAlternative.getOutMessagePolicyMap().get(wsdlOperation);
/*     */     
/* 705 */     if (sph == null) {
/* 706 */       return new MessagePolicy();
/*     */     }
/* 708 */     mp = sph.getMessagePolicy();
/* 709 */     return mp;
/*     */   }
/*     */   
/*     */   protected MessagePolicy getOutgoingFaultPolicy(Packet packet) {
/* 713 */     WSDLBoundOperation cachedOp = cachedOperation(packet);
/* 714 */     PolicyAlternativeHolder applicableAlternative = resolveAlternative(packet, false);
/*     */     
/* 716 */     if (cachedOp != null) {
/* 717 */       WSDLOperation wsdlOperation = cachedOp.getOperation();
/* 718 */       QName faultDetail = packet.getMessage().getFirstDetailEntryName();
/* 719 */       WSDLFault fault = null;
/* 720 */       if (faultDetail != null) {
/* 721 */         fault = wsdlOperation.getFault(faultDetail);
/*     */       }
/* 723 */       SecurityPolicyHolder sph = applicableAlternative.getOutMessagePolicyMap().get(cachedOp);
/* 724 */       if (fault == null) {
/* 725 */         MessagePolicy faultPolicy1 = (sph != null) ? sph.getMessagePolicy() : new MessagePolicy();
/* 726 */         return faultPolicy1;
/*     */       } 
/* 728 */       SecurityPolicyHolder faultPolicyHolder = sph.getFaultPolicy(fault);
/* 729 */       MessagePolicy faultPolicy = (faultPolicyHolder == null) ? new MessagePolicy() : faultPolicyHolder.getMessagePolicy();
/* 730 */       return faultPolicy;
/*     */     } 
/* 732 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CallbackHandler configureServerHandler(Set configAssertions, Properties props) {
/* 739 */     String ret = populateConfigProperties(configAssertions, props);
/*     */     try {
/* 741 */       if (ret != null) {
/* 742 */         Class hdlr = loadClass(ret);
/* 743 */         Object obj = hdlr.newInstance();
/* 744 */         if (!(obj instanceof CallbackHandler)) {
/* 745 */           log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0031_INVALID_CALLBACK_HANDLER_CLASS(ret));
/*     */           
/* 747 */           throw new RuntimeException(LogStringsMessages.WSITPVD_0031_INVALID_CALLBACK_HANDLER_CLASS(ret));
/*     */         } 
/*     */         
/* 750 */         return (CallbackHandler)obj;
/*     */       } 
/*     */       
/* 753 */       RealmAuthenticationAdapter adapter = getRealmAuthenticationAdapter(this.endPoint.get());
/* 754 */       return (CallbackHandler)new DefaultCallbackHandler("server", props, adapter);
/*     */     }
/* 756 */     catch (Exception e) {
/* 757 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0043_ERROR_CONFIGURE_SERVER_HANDLER(), e);
/*     */       
/* 759 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0043_ERROR_CONFIGURE_SERVER_HANDLER(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean bindingHasIssuedTokenPolicy() {
/* 766 */     return this.hasIssuedTokens;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean bindingHasSecureConversationPolicy() {
/* 771 */     return this.hasSecureConversation;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean bindingHasRMPolicy() {
/* 776 */     return this.hasReliableMessaging;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Packet invokeSecureConversationContract(Packet packet, ProcessingContext ctx, boolean isSCTIssue) {
/* 783 */     IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/* 784 */     issuedTokenContextImpl.getOtherProperties().put("SessionManager", this.sessionManager);
/* 785 */     Message msg = packet.getMessage();
/* 786 */     Message retMsg = null;
/* 787 */     String retAction = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 792 */       Subject subject = SubjectAccessor.getRequesterSubject(ctx);
/* 793 */       issuedTokenContextImpl.setRequestorSubject(subject);
/*     */       
/* 795 */       WSTrustElementFactory wsscEleFac = WSTrustElementFactory.newInstance(this.wsscVer);
/* 796 */       JAXBElement rstEle = (JAXBElement)msg.readPayloadAsJAXB(WSTrustElementFactory.getContext(this.wsTrustVer).createUnmarshaller());
/* 797 */       RequestSecurityToken requestSecurityToken = wsscEleFac.createRSTFrom(rstEle);
/*     */       
/* 799 */       URI requestType = requestSecurityToken.getRequestType();
/* 800 */       BaseSTSResponse rstr = null;
/* 801 */       WSSCContract scContract = WSSCFactory.newWSSCContract(this.wsscVer);
/* 802 */       scContract.setWSSCServerConfig((Iterator)packet.invocationProperties.get("http://schemas.sun.com/ws/2006/05/sc/server"));
/*     */       
/* 804 */       if (requestType.toString().equals(this.wsTrustVer.getIssueRequestTypeURI())) {
/* 805 */         List<PolicyAssertion> policies = getOutBoundSCP(packet.getMessage());
/* 806 */         rstr = scContract.issue((BaseSTSRequest)requestSecurityToken, (IssuedTokenContext)issuedTokenContextImpl, (SecureConversationToken)policies.get(0));
/* 807 */         retAction = this.wsscVer.getSCTResponseAction();
/* 808 */         SecurityContextToken sct = (SecurityContextToken)issuedTokenContextImpl.getSecurityToken();
/* 809 */         String sctId = sct.getIdentifier().toString();
/*     */         
/* 811 */         Session session = this.sessionManager.getSession(sctId);
/* 812 */         if (session == null) {
/* 813 */           log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0044_ERROR_SESSION_CREATION());
/*     */           
/* 815 */           throw new WSSecureConversationException(LogStringsMessages.WSITPVD_0044_ERROR_SESSION_CREATION());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 820 */         packet.invocationProperties.put("com.sun.xml.ws.sessionid", sctId);
/*     */ 
/*     */         
/* 823 */         packet.invocationProperties.put("com.sun.xml.ws.session", session.getUserData());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 831 */       else if (requestType.toString().equals(this.wsTrustVer.getRenewRequestTypeURI())) {
/* 832 */         List<PolicyAssertion> policies = getOutBoundSCP(packet.getMessage());
/* 833 */         retAction = this.wsscVer.getSCTRenewResponseAction();
/* 834 */         rstr = scContract.renew((BaseSTSRequest)requestSecurityToken, (IssuedTokenContext)issuedTokenContextImpl, (SecureConversationToken)policies.get(0));
/* 835 */       } else if (requestType.toString().equals(this.wsTrustVer.getCancelRequestTypeURI())) {
/* 836 */         retAction = this.wsscVer.getSCTCancelResponseAction();
/* 837 */         rstr = scContract.cancel((BaseSTSRequest)requestSecurityToken, (IssuedTokenContext)issuedTokenContextImpl);
/*     */       } else {
/* 839 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0045_UNSUPPORTED_OPERATION_EXCEPTION(requestType));
/*     */         
/* 841 */         throw new UnsupportedOperationException(LogStringsMessages.WSITPVD_0045_UNSUPPORTED_OPERATION_EXCEPTION(requestType));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 847 */       retMsg = Messages.create(WSTrustElementFactory.getContext(this.wsTrustVer).createMarshaller(), wsscEleFac.toJAXBElement(rstr), this.soapVersion);
/*     */     }
/* 849 */     catch (JAXBException ex) {
/* 850 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0001_PROBLEM_MAR_UNMAR(), ex);
/* 851 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0001_PROBLEM_MAR_UNMAR(), ex);
/* 852 */     } catch (XWSSecurityException ex) {
/* 853 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0046_ERROR_INVOKE_SC_CONTRACT(), (Throwable)ex);
/* 854 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0046_ERROR_INVOKE_SC_CONTRACT(), ex);
/* 855 */     } catch (WSSecureConversationException ex) {
/* 856 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0046_ERROR_INVOKE_SC_CONTRACT(), (Throwable)ex);
/* 857 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0046_ERROR_INVOKE_SC_CONTRACT(), ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 865 */     Packet retPacket = addAddressingHeaders(packet, retMsg, retAction);
/* 866 */     if (isSCTIssue) {
/* 867 */       List<PolicyAssertion> policies = getOutBoundSCP(packet.getMessage());
/*     */       
/* 869 */       if (!policies.isEmpty()) {
/* 870 */         retPacket.invocationProperties.put(Constants.SC_ASSERTION, policies.get(0));
/*     */       }
/*     */     } 
/*     */     
/* 874 */     return retPacket;
/*     */   }
/*     */ 
/*     */   
/*     */   private Packet addAddressingHeaders(Packet packet, Message retMsg, String action) {
/* 879 */     Packet retPacket = packet.createServerResponse(retMsg, this.addVer, this.soapVersion, action);
/*     */     
/* 881 */     retPacket.proxy = packet.proxy;
/* 882 */     retPacket.invocationProperties.putAll(packet.invocationProperties);
/*     */     
/* 884 */     return retPacket;
/*     */   }
/*     */   
/*     */   protected SecurityPolicyHolder addOutgoingMP(WSDLBoundOperation operation, Policy policy, PolicyAlternativeHolder ph) throws PolicyException {
/* 888 */     SecurityPolicyHolder sph = constructPolicyHolder(policy, true, true);
/* 889 */     ph.getInMessagePolicyMap().put(operation, sph);
/* 890 */     return sph;
/*     */   }
/*     */   
/*     */   protected SecurityPolicyHolder addIncomingMP(WSDLBoundOperation operation, Policy policy, PolicyAlternativeHolder ph) throws PolicyException {
/* 894 */     SecurityPolicyHolder sph = constructPolicyHolder(policy, true, false);
/* 895 */     ph.getOutMessagePolicyMap().put(operation, sph);
/* 896 */     return sph;
/*     */   }
/*     */   
/*     */   protected void addIncomingProtocolPolicy(Policy effectivePolicy, String protocol, PolicyAlternativeHolder ph) throws PolicyException {
/* 900 */     ph.getOutProtocolPM().put(protocol, constructPolicyHolder(effectivePolicy, true, false, true));
/*     */   }
/*     */   
/*     */   protected void addOutgoingProtocolPolicy(Policy effectivePolicy, String protocol, PolicyAlternativeHolder ph) throws PolicyException {
/* 904 */     ph.getInProtocolPM().put(protocol, constructPolicyHolder(effectivePolicy, true, true, false));
/*     */   }
/*     */   
/*     */   protected void addIncomingFaultPolicy(Policy effectivePolicy, SecurityPolicyHolder sph, WSDLFault fault) throws PolicyException {
/* 908 */     SecurityPolicyHolder faultPH = constructPolicyHolder(effectivePolicy, true, false);
/* 909 */     sph.addFaultPolicy(fault, faultPH);
/*     */   }
/*     */   
/*     */   protected void addOutgoingFaultPolicy(Policy effectivePolicy, SecurityPolicyHolder sph, WSDLFault fault) throws PolicyException {
/* 913 */     SecurityPolicyHolder faultPH = constructPolicyHolder(effectivePolicy, true, true);
/* 914 */     sph.addFaultPolicy(fault, faultPH);
/*     */   }
/*     */   
/*     */   protected String getAction(WSDLOperation operation, boolean inComming) {
/* 918 */     if (inComming) {
/* 919 */       return operation.getInput().getAction();
/*     */     }
/* 921 */     return operation.getOutput().getAction();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private RealmAuthenticationAdapter getRealmAuthenticationAdapter(WSEndpoint wSEndpoint) {
/* 927 */     String className = "javax.servlet.ServletContext";
/* 928 */     Class<?> ret = null;
/* 929 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 930 */     if (loader != null) {
/*     */       try {
/* 932 */         ret = loader.loadClass(className);
/* 933 */       } catch (ClassNotFoundException e) {
/* 934 */         return null;
/*     */       } 
/*     */     }
/* 937 */     if (ret == null) {
/*     */       
/* 939 */       loader = getClass().getClassLoader();
/*     */       try {
/* 941 */         ret = loader.loadClass(className);
/* 942 */       } catch (ClassNotFoundException e) {
/* 943 */         return null;
/*     */       } 
/*     */     } 
/* 946 */     if (ret != null) {
/* 947 */       Object obj = wSEndpoint.getContainer().getSPI(ret);
/* 948 */       if (obj != null) {
/* 949 */         return RealmAuthenticationAdapter.newInstance(obj);
/*     */       }
/*     */     } 
/* 952 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateSCSessionInfo(Packet packet) {
/* 957 */     SecurityContextToken sct = (SecurityContextToken)packet.invocationProperties.get("Incoming_SCT");
/*     */     
/* 959 */     if (sct != null) {
/*     */       
/* 961 */       String sessionId = sct.getIdentifier().toString();
/*     */ 
/*     */       
/* 964 */       packet.invocationProperties.put("com.sun.xml.ws.sessionid", sessionId);
/* 965 */       packet.invocationProperties.put("com.sun.xml.ws.session", this.sessionManager.getSession(sessionId).getUserData());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\WSITServerAuthContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */