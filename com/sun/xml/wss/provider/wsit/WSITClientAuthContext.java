/*      */ package com.sun.xml.wss.provider.wsit;
/*      */ 
/*      */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*      */ import com.sun.xml.ws.api.message.AttachmentSet;
/*      */ import com.sun.xml.ws.api.message.HeaderList;
/*      */ import com.sun.xml.ws.api.message.Message;
/*      */ import com.sun.xml.ws.api.message.Messages;
/*      */ import com.sun.xml.ws.api.message.Packet;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*      */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*      */ import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
/*      */ import com.sun.xml.ws.api.pipe.Fiber;
/*      */ import com.sun.xml.ws.api.security.secconv.client.SCTokenConfiguration;
/*      */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*      */ import com.sun.xml.ws.api.security.trust.client.IssuedTokenConfiguration;
/*      */ import com.sun.xml.ws.api.security.trust.client.IssuedTokenManager;
/*      */ import com.sun.xml.ws.api.security.trust.client.STSIssuedTokenConfiguration;
/*      */ import com.sun.xml.ws.commons.ha.HaContext;
/*      */ import com.sun.xml.ws.developer.WSBindingProvider;
/*      */ import com.sun.xml.ws.model.wsdl.WSDLPortImpl;
/*      */ import com.sun.xml.ws.policy.Policy;
/*      */ import com.sun.xml.ws.policy.PolicyAssertion;
/*      */ import com.sun.xml.ws.policy.PolicyException;
/*      */ import com.sun.xml.ws.security.IssuedTokenContext;
/*      */ import com.sun.xml.ws.security.impl.kerberos.KerberosContext;
/*      */ import com.sun.xml.ws.security.impl.policyconv.SecurityPolicyHolder;
/*      */ import com.sun.xml.ws.security.message.stream.LazyStreamBasedMessage;
/*      */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*      */ import com.sun.xml.ws.security.opt.impl.incoming.SecurityRecipient;
/*      */ import com.sun.xml.ws.security.opt.impl.util.CertificateRetriever;
/*      */ import com.sun.xml.ws.security.policy.IssuedToken;
/*      */ import com.sun.xml.ws.security.policy.SecureConversationToken;
/*      */ import com.sun.xml.ws.security.policy.Token;
/*      */ import com.sun.xml.ws.security.secconv.WSSecureConversationException;
/*      */ import com.sun.xml.ws.security.secconv.impl.client.DefaultSCTokenConfiguration;
/*      */ import com.sun.xml.ws.security.trust.GenericToken;
/*      */ import com.sun.xml.ws.security.trust.STSIssuedTokenFeature;
/*      */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*      */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*      */ import com.sun.xml.ws.security.trust.impl.client.DefaultSTSIssuedTokenConfiguration;
/*      */ import com.sun.xml.wss.ProcessingContext;
/*      */ import com.sun.xml.wss.SecurityEnvironment;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.impl.MessageConstants;
/*      */ import com.sun.xml.wss.impl.NewSecurityRecipient;
/*      */ import com.sun.xml.wss.impl.PolicyResolver;
/*      */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*      */ import com.sun.xml.wss.impl.ProcessingContextImpl;
/*      */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*      */ import com.sun.xml.wss.impl.SecurityAnnotator;
/*      */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*      */ import com.sun.xml.wss.impl.filter.DumpFilter;
/*      */ import com.sun.xml.wss.impl.misc.Base64;
/*      */ import com.sun.xml.wss.impl.misc.DefaultCallbackHandler;
/*      */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*      */ import com.sun.xml.wss.impl.misc.WSITProviderSecurityEnvironment;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*      */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*      */ import com.sun.xml.wss.jaxws.impl.Constants;
/*      */ import com.sun.xml.wss.provider.wsit.logging.LogStringsMessages;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.logging.Level;
/*      */ import javax.security.auth.Subject;
/*      */ import javax.security.auth.callback.CallbackHandler;
/*      */ import javax.security.auth.message.AuthException;
/*      */ import javax.security.auth.message.AuthStatus;
/*      */ import javax.security.auth.message.MessageInfo;
/*      */ import javax.security.auth.message.config.ClientAuthContext;
/*      */ import javax.xml.bind.JAXBElement;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.soap.SOAPException;
/*      */ import javax.xml.soap.SOAPFault;
/*      */ import javax.xml.soap.SOAPMessage;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamReader;
/*      */ import javax.xml.ws.WebServiceException;
/*      */ import javax.xml.ws.soap.SOAPFaultException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class WSITClientAuthContext
/*      */   extends WSITAuthContextBase
/*      */   implements ClientAuthContext
/*      */ {
/*      */   private IssuedTokenManager itm;
/*  168 */   private Set trustConfig = null;
/*  169 */   private Set wsscConfig = null;
/*  170 */   private CallbackHandler handler = null;
/*      */   
/*  172 */   WSITClientAuthModule authModule = null;
/*  173 */   private Hashtable<String, String> scPolicyIDtoSctIdMap = new Hashtable<String, String>();
/*      */ 
/*      */   
/*      */   protected WeakReference<WSITClientAuthConfig> authConfig;
/*      */ 
/*      */   
/*      */   protected int tubeOrPipeHashCode;
/*      */ 
/*      */   
/*      */   public WSITClientAuthContext(String operation, Subject subject, Map<Object, Object> map, CallbackHandler callbackHandler) {
/*  183 */     super(map);
/*  184 */     this.authConfig = new WeakReference<WSITClientAuthConfig>((WSITClientAuthConfig)map.get("AUTH_CONFIG"));
/*  185 */     this.tubeOrPipeHashCode = map.get("SECURITY_PIPE").hashCode();
/*  186 */     WSDLPortImpl wpi = (WSDLPortImpl)map.get("WSDL_MODEL");
/*  187 */     ClientTubeAssemblerContext context = (ClientTubeAssemblerContext)map.get("WRAPPED_CONTEXT");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  194 */     this.itm = IssuedTokenManager.getInstance();
/*      */ 
/*      */     
/*  197 */     Set configAssertions = null;
/*  198 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/*  199 */       Iterator<SecurityPolicyHolder> it = p.getOutMessagePolicyMap().values().iterator();
/*  200 */       while (it.hasNext()) {
/*  201 */         SecurityPolicyHolder holder = it.next();
/*  202 */         if (configAssertions != null) {
/*  203 */           configAssertions.addAll(holder.getConfigAssertions("http://schemas.sun.com/2006/03/wss/client"));
/*      */         } else {
/*  205 */           configAssertions = holder.getConfigAssertions("http://schemas.sun.com/2006/03/wss/client");
/*      */         } 
/*  207 */         if (this.trustConfig != null) {
/*  208 */           this.trustConfig.addAll(holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/trust/client"));
/*      */         } else {
/*  210 */           this.trustConfig = holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/trust/client");
/*      */         } 
/*      */         
/*  213 */         if (this.wsscConfig != null) {
/*  214 */           this.wsscConfig.addAll(holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/sc/client")); continue;
/*      */         } 
/*  216 */         this.wsscConfig = holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/sc/client");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  222 */     Properties props = new Properties();
/*  223 */     if (callbackHandler != null) {
/*  224 */       populateConfigProperties(configAssertions, props);
/*      */       try {
/*  226 */         String jmacHandler = props.getProperty("jmac.callbackhandler");
/*  227 */         if (jmacHandler != null) {
/*  228 */           this.handler = loadGFHandler(true, jmacHandler);
/*      */         } else {
/*  230 */           this.handler = callbackHandler;
/*      */         } 
/*  232 */         this.secEnv = (SecurityEnvironment)new WSITProviderSecurityEnvironment(this.handler, map, props);
/*  233 */       } catch (XWSSecurityException ex) {
/*  234 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0027_ERROR_POPULATING_CLIENT_CONFIG_PROP(), (Throwable)ex);
/*      */         
/*  236 */         throw new WebServiceException(LogStringsMessages.WSITPVD_0027_ERROR_POPULATING_CLIENT_CONFIG_PROP(), ex);
/*      */       } 
/*      */     } else {
/*      */       
/*  240 */       this.handler = configureClientHandler(configAssertions, props);
/*  241 */       String jmacHandler = props.getProperty("jmac.callbackhandler");
/*  242 */       if (jmacHandler != null) {
/*      */         try {
/*  244 */           this.handler = loadGFHandler(true, jmacHandler);
/*  245 */           this.secEnv = (SecurityEnvironment)new WSITProviderSecurityEnvironment(this.handler, map, props);
/*  246 */         } catch (XWSSecurityException ex) {
/*  247 */           log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0027_ERROR_POPULATING_CLIENT_CONFIG_PROP(), (Throwable)ex);
/*      */           
/*  249 */           throw new WebServiceException(LogStringsMessages.WSITPVD_0027_ERROR_POPULATING_CLIENT_CONFIG_PROP(), ex);
/*      */         } 
/*      */       } else {
/*      */         
/*  253 */         this.secEnv = (SecurityEnvironment)new DefaultSecurityEnvironmentImpl(this.handler, props);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  258 */     X509Certificate cert = getCertificateFromEPR(context, wpi);
/*  259 */     if (cert != null) {
/*  260 */       props.put("SERVER_CERT", cert);
/*  261 */       this.serverCert = cert;
/*      */     } 
/*      */     
/*  264 */     this.authModule = new WSITClientAuthModule();
/*      */     try {
/*  266 */       this.authModule.initialize(null, null, null, map);
/*  267 */     } catch (AuthException e) {
/*  268 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0028_ERROR_INIT_AUTH_MODULE(), (Throwable)e);
/*  269 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0028_ERROR_INIT_AUTH_MODULE(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public AuthStatus secureRequest(MessageInfo messageInfo, Subject clientSubject) throws AuthException {
/*      */     try {
/*      */       try {
/*  277 */         Packet packet = getRequestPacket(messageInfo);
/*  278 */         HaContext.initFrom(packet);
/*      */         
/*  280 */         boolean isTrustMsg = false;
/*  281 */         if ("true".equals(packet.invocationProperties.get("isTrustMessage"))) {
/*  282 */           isTrustMsg = true;
/*  283 */           String action = (String)packet.invocationProperties.get("trustAction");
/*  284 */           HeaderList headers = packet.getMessage().getHeaders();
/*  285 */           headers.fillRequestAddressingHeaders(packet, this.addVer, this.soapVersion, false, action);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  290 */         Map<Object, Object> msgInfoMap = messageInfo.getMap();
/*  291 */         msgInfoMap.put("IS_TRUST_MSG", Boolean.valueOf(isTrustMsg));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  297 */         invokeSCPlugin(packet);
/*      */ 
/*      */         
/*  300 */         Packet ret = secureRequest(packet, clientSubject, false);
/*      */ 
/*      */         
/*  303 */         setRequestPacket(messageInfo, ret);
/*      */       }
/*  305 */       catch (XWSSecurityException e) {
/*  306 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0050_ERROR_SECURE_REQUEST(), (Throwable)e);
/*      */         
/*  308 */         throw new WebServiceException(LogStringsMessages.WSITPVD_0050_ERROR_SECURE_REQUEST(), getSOAPFaultException(e));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  313 */       return AuthStatus.SEND_SUCCESS;
/*      */     } finally {
/*  315 */       HaContext.clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet secureRequest(Packet packet, Subject clientSubject, boolean isSCMessage) throws XWSSecurityException {
/*  322 */     Message msg = packet.getInternalMessage();
/*  323 */     invokeTrustPlugin(packet, isSCMessage);
/*      */     
/*  325 */     ProcessingContext ctx = initializeOutgoingProcessingContext(packet, isSCMessage);
/*  326 */     ((ProcessingContextImpl)ctx).setIssuedTokenContextMap(this.issuedTokenContextMap);
/*  327 */     ((ProcessingContextImpl)ctx).setSCPolicyIDtoSctIdMap(this.scPolicyIDtoSctIdMap);
/*  328 */     ctx.isClient(true);
/*  329 */     if (hasKerberosTokenPolicy()) {
/*  330 */       populateKerberosContext(packet, (ProcessingContextImpl)ctx, isSCMessage);
/*      */     }
/*  332 */     if (isSCRenew(packet)) {
/*  333 */       DefaultSCTokenConfiguration defaultSCTokenConfiguration = new DefaultSCTokenConfiguration(this.wsscVer.getNamespaceURI());
/*  334 */       defaultSCTokenConfiguration.getOtherOptions().put("MessagePolicy", (MessagePolicy)ctx.getSecurityPolicy());
/*  335 */       IssuedTokenContext itc = this.itm.createIssuedTokenContext((IssuedTokenConfiguration)defaultSCTokenConfiguration, packet.endpointAddress.toString());
/*      */       try {
/*  337 */         this.itm.renewIssuedToken(itc);
/*  338 */       } catch (WSTrustException se) {
/*  339 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0052_ERROR_ISSUEDTOKEN_CREATION(), (Throwable)se);
/*  340 */         throw new WebServiceException(LogStringsMessages.WSITPVD_0052_ERROR_ISSUEDTOKEN_CREATION(), se);
/*      */       } 
/*      */     } 
/*      */     
/*      */     try {
/*  345 */       if (!this.optimized) {
/*  346 */         if (!isSCMessage) {
/*  347 */           cacheOperation(msg, packet);
/*      */         }
/*  349 */         SOAPMessage soapMessage = msg.readAsSOAPMessage();
/*  350 */         soapMessage = secureOutboundMessage(soapMessage, ctx);
/*  351 */         msg = Messages.create(soapMessage);
/*      */       } else {
/*  353 */         msg = secureOutboundMessage(msg, ctx);
/*      */       } 
/*  355 */     } catch (WssSoapFaultException ex) {
/*  356 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0029_ERROR_SECURING_OUTBOUND_MSG(), (Throwable)ex);
/*      */       
/*  358 */       throw new WebServiceException(LogStringsMessages.WSITPVD_0029_ERROR_SECURING_OUTBOUND_MSG(), getSOAPFaultException(ex));
/*      */     }
/*  360 */     catch (SOAPException se) {
/*  361 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0029_ERROR_SECURING_OUTBOUND_MSG(), se);
/*      */       
/*  363 */       throw new WebServiceException(LogStringsMessages.WSITPVD_0029_ERROR_SECURING_OUTBOUND_MSG(), se);
/*      */     } 
/*      */     
/*  366 */     packet.setMessage(msg);
/*  367 */     if (isSCMessage) {
/*  368 */       if (isSCRenew(packet)) {
/*  369 */         Token scToken = (Token)packet.invocationProperties.get(Constants.SC_ASSERTION);
/*  370 */         DefaultSCTokenConfiguration defaultSCTokenConfiguration = new DefaultSCTokenConfiguration(this.wsscVer.getNamespaceURI(), false);
/*  371 */         defaultSCTokenConfiguration.getOtherOptions().put("MessagePolicy", getOutgoingXWSBootstrapPolicy(scToken));
/*  372 */         IssuedTokenContext itc = this.itm.createIssuedTokenContext((IssuedTokenConfiguration)defaultSCTokenConfiguration, packet.endpointAddress.toString());
/*      */         try {
/*  374 */           this.itm.renewIssuedToken(itc);
/*  375 */         } catch (WSTrustException se) {
/*  376 */           log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0052_ERROR_ISSUEDTOKEN_CREATION(), (Throwable)se);
/*  377 */           throw new WebServiceException(LogStringsMessages.WSITPVD_0052_ERROR_ISSUEDTOKEN_CREATION(), se);
/*      */         } 
/*      */       } 
/*  380 */       Packet responsePacket = null;
/*  381 */       if (this.nextPipe != null) {
/*      */         
/*  383 */         responsePacket = this.nextPipe.process(packet);
/*      */       }
/*  385 */       else if (this.nextTube != null) {
/*  386 */         responsePacket = (Fiber.current()).owner.createFiber().runSync(this.nextTube, packet);
/*      */       } 
/*      */       
/*  389 */       packet = validateResponse(responsePacket, (Subject)null, (Subject)null);
/*      */     } 
/*  391 */     return packet;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AuthStatus validateResponse(MessageInfo messageInfo, Subject clientSubject, Subject serviceSubject) throws AuthException {
/*      */     try {
/*      */       try {
/*  400 */         Packet ret = getResponsePacket(messageInfo);
/*  401 */         HaContext.initFrom(ret);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  415 */         ret = validateResponse(ret, clientSubject, serviceSubject);
/*  416 */         resetCachedOperation(ret);
/*      */         
/*  418 */         Boolean trustMsgProp = (Boolean)messageInfo.getMap().get("IS_TRUST_MSG");
/*  419 */         boolean isTrustMsg = (trustMsgProp != null) ? trustMsgProp.booleanValue() : false;
/*  420 */         if (isTrustMsg)
/*      */         {
/*  422 */           getAction(ret);
/*      */         }
/*      */         
/*  425 */         setResponsePacket(messageInfo, ret);
/*      */       }
/*  427 */       catch (XWSSecurityException ex) {
/*  428 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0033_ERROR_VALIDATE_RESPONSE(), (Throwable)ex);
/*      */         
/*  430 */         throw new WebServiceException(LogStringsMessages.WSITPVD_0033_ERROR_VALIDATE_RESPONSE(), getSOAPFaultException(ex));
/*      */       } 
/*      */ 
/*      */       
/*  434 */       return AuthStatus.SUCCESS;
/*      */     } finally {
/*  436 */       HaContext.clear();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void cleanSubject(MessageInfo messageInfo, Subject subject) throws AuthException {
/*  441 */     cancelSecurityContextToken();
/*  442 */     ((WSITClientAuthConfig)this.authConfig.get()).cleanupAuthContext(Integer.valueOf(this.tubeOrPipeHashCode));
/*  443 */     this.authConfig.clear();
/*  444 */     this.nextPipe = null;
/*  445 */     this.nextTube = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Packet validateResponse(Packet req, Subject clientSubject, Subject serviceSubject) throws XWSSecurityException {
/*  452 */     ProcessingContext ctx = initializeInboundProcessingContext(req);
/*  453 */     ctx.isClient(true);
/*      */     
/*  455 */     ((ProcessingContextImpl)ctx).setIssuedTokenContextMap(this.issuedTokenContextMap);
/*  456 */     ((ProcessingContextImpl)ctx).setSCPolicyIDtoSctIdMap(this.scPolicyIDtoSctIdMap);
/*  457 */     PolicyResolver pr = PolicyResolverFactory.createPolicyResolver(this.policyAlternatives, cachedOperation(req), this.pipeConfig, this.addVer, true, this.rmVer, this.mcVer);
/*  458 */     ctx.setExtraneousProperty("OperationResolver", pr);
/*  459 */     Message msg = req.getInternalMessage();
/*      */     
/*      */     try {
/*  462 */       if (!this.optimized) {
/*  463 */         SOAPMessage soapMessage = msg.readAsSOAPMessage();
/*  464 */         soapMessage = verifyInboundMessage(soapMessage, ctx);
/*  465 */         if (msg.isFault()) {
/*  466 */           if (debug) {
/*  467 */             DumpFilter.process(ctx);
/*      */           }
/*  469 */           SOAPFault fault = soapMessage.getSOAPBody().getFault();
/*      */ 
/*      */           
/*  472 */           throw new SOAPFaultException(fault);
/*      */         } 
/*  474 */         msg = Messages.create(soapMessage);
/*      */       } else {
/*  476 */         msg = verifyInboundMessage(msg, ctx);
/*      */       } 
/*  478 */     } catch (XWSSecurityException xwse) {
/*  479 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), (Throwable)xwse);
/*      */       
/*  481 */       throw new WebServiceException(LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), getSOAPFaultException(xwse));
/*      */     
/*      */     }
/*  484 */     catch (SOAPException se) {
/*  485 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), se);
/*      */       
/*  487 */       throw new WebServiceException(LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), se);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  492 */     req.setMessage(msg);
/*  493 */     return req;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SOAPMessage secureOutboundMessage(SOAPMessage message, ProcessingContext ctx) {
/*      */     try {
/*  499 */       ctx.setSOAPMessage(message);
/*  500 */       SecurityAnnotator.secureMessage(ctx);
/*  501 */       return ctx.getSOAPMessage();
/*  502 */     } catch (WssSoapFaultException soapFaultException) {
/*  503 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0029_ERROR_SECURING_OUTBOUND_MSG(), (Throwable)soapFaultException);
/*      */       
/*  505 */       throw new WebServiceException(LogStringsMessages.WSITPVD_0029_ERROR_SECURING_OUTBOUND_MSG(), getSOAPFaultException(soapFaultException));
/*      */     
/*      */     }
/*  508 */     catch (XWSSecurityException xwse) {
/*  509 */       WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INTERNAL_SERVER_ERROR, xwse.getMessage(), (Throwable)xwse);
/*      */ 
/*      */ 
/*      */       
/*  513 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0029_ERROR_SECURING_OUTBOUND_MSG(), (Throwable)wsfe);
/*      */       
/*  515 */       throw new WebServiceException(LogStringsMessages.WSITPVD_0029_ERROR_SECURING_OUTBOUND_MSG(), getSOAPFaultException(wsfe));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Message secureOutboundMessage(Message message, ProcessingContext ctx) {
/*      */     try {
/*  524 */       JAXBFilterProcessingContext context = (JAXBFilterProcessingContext)ctx;
/*  525 */       context.setSOAPVersion(this.soapVersion);
/*  526 */       context.setAllowMissingTimestamp(this.allowMissingTimestamp);
/*  527 */       context.setMustUnderstandValue(this.securityMUValue);
/*  528 */       context.setJAXWSMessage(message, this.soapVersion);
/*  529 */       context.isOneWayMessage(message.isOneWay(this.pipeConfig.getWSDLPort()));
/*  530 */       context.setDisableIncPrefix(this.disableIncPrefix);
/*  531 */       context.setEncHeaderContent(this.encHeaderContent);
/*  532 */       SecurityAnnotator.secureMessage((ProcessingContext)context);
/*  533 */       return context.getJAXWSMessage();
/*  534 */     } catch (XWSSecurityException xwse) {
/*  535 */       WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INTERNAL_SERVER_ERROR, xwse.getMessage(), (Throwable)xwse);
/*      */ 
/*      */ 
/*      */       
/*  539 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0029_ERROR_SECURING_OUTBOUND_MSG(), (Throwable)wsfe);
/*      */       
/*  541 */       throw new WebServiceException(LogStringsMessages.WSITPVD_0029_ERROR_SECURING_OUTBOUND_MSG(), getSOAPFaultException(wsfe));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SOAPMessage verifyInboundMessage(SOAPMessage message, ProcessingContext ctx) throws WssSoapFaultException, XWSSecurityException {
/*      */     try {
/*  550 */       ctx.setSOAPMessage(message);
/*  551 */       if (debug) {
/*  552 */         DumpFilter.process(ctx);
/*      */       }
/*  554 */       NewSecurityRecipient.validateMessage(ctx);
/*  555 */       return ctx.getSOAPMessage();
/*  556 */     } catch (WssSoapFaultException soapFaultException) {
/*  557 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), (Throwable)soapFaultException);
/*      */       
/*  559 */       throw new WebServiceException(LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), getSOAPFaultException(soapFaultException));
/*      */     
/*      */     }
/*  562 */     catch (XWSSecurityException xwse) {
/*  563 */       WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INTERNAL_SERVER_ERROR, xwse.getMessage(), (Throwable)xwse);
/*      */ 
/*      */ 
/*      */       
/*  567 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), (Throwable)wsfe);
/*      */       
/*  569 */       throw new WebServiceException(LogStringsMessages.WSITPVD_0035_ERROR_VERIFY_INBOUND_MSG(), getSOAPFaultException(wsfe));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected Message verifyInboundMessage(Message message, ProcessingContext ctx) throws XWSSecurityException {
/*  576 */     JAXBFilterProcessingContext context = (JAXBFilterProcessingContext)ctx;
/*      */     
/*  578 */     if (debug) {
/*      */       try {
/*  580 */         ((LazyStreamBasedMessage)message).print();
/*  581 */       } catch (XMLStreamException ex) {
/*  582 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0003_PROBLEM_PRINTING_MSG(), ex);
/*  583 */         throw new XWSSecurityException(LogStringsMessages.WSITPVD_0003_PROBLEM_PRINTING_MSG(), ex);
/*      */       } 
/*      */     }
/*  586 */     LazyStreamBasedMessage lazyStreamMessage = (LazyStreamBasedMessage)message;
/*  587 */     AttachmentSet attachSet = lazyStreamMessage.getAttachments();
/*  588 */     SecurityRecipient recipient = null;
/*  589 */     if (attachSet == null || attachSet.isEmpty()) {
/*  590 */       recipient = new SecurityRecipient(lazyStreamMessage.readMessage(), this.soapVersion);
/*      */     } else {
/*      */       
/*  593 */       recipient = new SecurityRecipient(lazyStreamMessage.readMessage(), this.soapVersion, attachSet);
/*      */     } 
/*      */     
/*  596 */     return recipient.validateMessage(context);
/*      */   }
/*      */   
/*      */   protected SecurityPolicyHolder addOutgoingMP(WSDLBoundOperation operation, Policy policy, PolicyAlternativeHolder ph) throws PolicyException {
/*  600 */     SecurityPolicyHolder sph = constructPolicyHolder(policy, false, false);
/*  601 */     ph.getOutMessagePolicyMap().put(operation, sph);
/*  602 */     return sph;
/*      */   }
/*      */   
/*      */   protected SecurityPolicyHolder addIncomingMP(WSDLBoundOperation operation, Policy policy, PolicyAlternativeHolder ph) throws PolicyException {
/*  606 */     SecurityPolicyHolder sph = constructPolicyHolder(policy, false, true);
/*  607 */     ph.getInMessagePolicyMap().put(operation, sph);
/*  608 */     return sph;
/*      */   }
/*      */   
/*      */   protected void addIncomingProtocolPolicy(Policy effectivePolicy, String protocol, PolicyAlternativeHolder ph) throws PolicyException {
/*  612 */     ph.getInProtocolPM().put(protocol, constructPolicyHolder(effectivePolicy, false, true, true));
/*      */   }
/*      */   
/*      */   protected void addOutgoingProtocolPolicy(Policy effectivePolicy, String protocol, PolicyAlternativeHolder ph) throws PolicyException {
/*  616 */     ph.getOutProtocolPM().put(protocol, constructPolicyHolder(effectivePolicy, false, false, false));
/*      */   }
/*      */   
/*      */   protected void addIncomingFaultPolicy(Policy effectivePolicy, SecurityPolicyHolder sph, WSDLFault fault) throws PolicyException {
/*  620 */     SecurityPolicyHolder faultPH = constructPolicyHolder(effectivePolicy, false, true);
/*  621 */     sph.addFaultPolicy(fault, faultPH);
/*      */   }
/*      */   
/*      */   protected void addOutgoingFaultPolicy(Policy effectivePolicy, SecurityPolicyHolder sph, WSDLFault fault) throws PolicyException {
/*  625 */     SecurityPolicyHolder faultPH = constructPolicyHolder(effectivePolicy, false, false);
/*  626 */     sph.addFaultPolicy(fault, faultPH);
/*      */   }
/*      */   
/*      */   protected String getAction(WSDLOperation operation, boolean inComming) {
/*  630 */     if (!inComming) {
/*  631 */       return operation.getInput().getAction();
/*      */     }
/*  633 */     return operation.getOutput().getAction();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   JAXBElement startSecureConversation(Packet packet) throws WSSecureConversationException {
/*  640 */     List<PolicyAssertion> toks = getOutBoundSCP(packet.getMessage());
/*  641 */     if (toks.isEmpty()) {
/*      */       
/*  643 */       if (log.isLoggable(Level.FINE)) {
/*  644 */         log.log(Level.FINE, LogStringsMessages.WSITPVD_0030_NO_POLICY_FOUND_FOR_SC());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  649 */       return null;
/*      */     } 
/*      */     
/*  652 */     Token tok = (Token)toks.get(0);
/*  653 */     IssuedTokenContext ctx = this.issuedTokenContextMap.get(tok.getTokenId());
/*      */ 
/*      */     
/*  656 */     PolicyAssertion scClientAssertion = null;
/*  657 */     if (this.wsscConfig != null) {
/*  658 */       Iterator<PolicyAssertion> it = this.wsscConfig.iterator();
/*  659 */       while (it != null && it.hasNext()) {
/*  660 */         scClientAssertion = it.next();
/*      */       }
/*      */     } 
/*      */     
/*  664 */     if (ctx == null) {
/*      */       
/*      */       try {
/*      */         
/*  668 */         DefaultSCTokenConfiguration defaultSCTokenConfiguration = new DefaultSCTokenConfiguration(this.wsscVer.getNamespaceURI(), (SecureConversationToken)tok, this.pipeConfig.getWSDLPort(), this.pipeConfig.getBinding(), packet, this.addVer, scClientAssertion);
/*  669 */         defaultSCTokenConfiguration.getOtherOptions().put("WSITClientAuthContext", this);
/*  670 */         ctx = this.itm.createIssuedTokenContext((IssuedTokenConfiguration)defaultSCTokenConfiguration, packet.endpointAddress.toString());
/*  671 */         this.itm.getIssuedToken(ctx);
/*  672 */         this.issuedTokenContextMap.put(tok.getTokenId(), ctx);
/*      */         
/*  674 */         SCTokenConfiguration sctConfig = ctx.getSecurityPolicy().get(0);
/*  675 */         this.scPolicyIDtoSctIdMap.put(tok.getTokenId(), sctConfig.getTokenId());
/*  676 */       } catch (WSTrustException se) {
/*  677 */         log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0052_ERROR_ISSUEDTOKEN_CREATION(), (Throwable)se);
/*  678 */         throw new WebServiceException(LogStringsMessages.WSITPVD_0052_ERROR_ISSUEDTOKEN_CREATION(), se);
/*      */       } 
/*      */     }
/*      */     
/*  682 */     SecurityTokenReference str = (SecurityTokenReference)ctx.getUnAttachedSecurityTokenReference();
/*      */     
/*  684 */     return WSTrustElementFactory.newInstance().toJAXBElement(str);
/*      */   }
/*      */ 
/*      */   
/*      */   private CallbackHandler configureClientHandler(Set configAssertions, Properties props) {
/*  689 */     String ret = populateConfigProperties(configAssertions, props);
/*      */     try {
/*  691 */       if (ret != null) {
/*  692 */         Class handlerClass = loadClass(ret);
/*  693 */         Object obj = handlerClass.newInstance();
/*  694 */         if (!(obj instanceof CallbackHandler)) {
/*  695 */           log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0031_INVALID_CALLBACK_HANDLER_CLASS(ret));
/*      */           
/*  697 */           throw new RuntimeException(LogStringsMessages.WSITPVD_0031_INVALID_CALLBACK_HANDLER_CLASS(ret));
/*      */         } 
/*      */         
/*  700 */         return (CallbackHandler)obj;
/*      */       } 
/*  702 */       return (CallbackHandler)new DefaultCallbackHandler("client", props);
/*  703 */     } catch (Exception e) {
/*  704 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0032_ERROR_CONFIGURE_CLIENT_HANDLER(), e);
/*      */       
/*  706 */       throw new RuntimeException(LogStringsMessages.WSITPVD_0032_ERROR_CONFIGURE_CLIENT_HANDLER(), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private X509Certificate getCertificateFromEPR(ClientTubeAssemblerContext context, WSDLPortImpl wpi) {
/*  712 */     X509Certificate x509Cert = null;
/*  713 */     if (context != null) {
/*  714 */       WSBindingProvider bpr = context.getBindingProvider();
/*  715 */       x509Cert = (X509Certificate)bpr.getRequestContext().get("server-certificate");
/*  716 */       if (x509Cert != null)
/*      */       {
/*  718 */         return x509Cert;
/*      */       }
/*      */     } 
/*  721 */     if (wpi != null) {
/*  722 */       WSEndpointReference epr = wpi.getEPR();
/*  723 */       if (epr != null) {
/*  724 */         WSEndpointReference.EPRExtension idExtn = null;
/*  725 */         XMLStreamReader xmlReader = null;
/*      */         try {
/*  727 */           QName ID_QNAME = new QName("http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", "Identity");
/*  728 */           idExtn = epr.getEPRExtension(ID_QNAME);
/*  729 */           if (idExtn != null) {
/*  730 */             xmlReader = idExtn.readAsXMLStreamReader();
/*  731 */             CertificateRetriever cr = new CertificateRetriever();
/*      */             
/*  733 */             byte[] bstValue = cr.getBSTFromIdentityExtension(xmlReader);
/*  734 */             X509Certificate certificate = null;
/*  735 */             if (bstValue != null) {
/*  736 */               certificate = cr.constructCertificate(bstValue);
/*      */             }
/*  738 */             return certificate;
/*      */           } 
/*  740 */         } catch (XMLStreamException ex) {
/*  741 */           log.log(Level.WARNING, ex.getMessage());
/*      */         } 
/*      */       } 
/*      */       
/*  745 */       return null;
/*      */     } 
/*  747 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void invokeSCPlugin(Packet packet) {
/*  753 */     List<PolicyAssertion> policies = getOutBoundSCP(packet.getMessage());
/*      */     
/*  755 */     PolicyAssertion scClientAssertion = null;
/*  756 */     if (this.wsscConfig != null) {
/*  757 */       Iterator<PolicyAssertion> it = this.wsscConfig.iterator();
/*  758 */       while (it != null && it.hasNext()) {
/*  759 */         scClientAssertion = it.next();
/*      */       }
/*      */     } 
/*      */     
/*  763 */     for (PolicyAssertion scAssertion : policies) {
/*  764 */       Token scToken = (Token)scAssertion;
/*  765 */       if (this.issuedTokenContextMap.get(scToken.getTokenId()) == null) {
/*      */         
/*      */         try {
/*  768 */           DefaultSCTokenConfiguration defaultSCTokenConfiguration = new DefaultSCTokenConfiguration(this.wsscVer.getNamespaceURI(), (SecureConversationToken)scToken, this.pipeConfig.getWSDLPort(), this.pipeConfig.getBinding(), packet, this.addVer, scClientAssertion);
/*  769 */           defaultSCTokenConfiguration.getOtherOptions().put("WSITClientAuthContext", this);
/*  770 */           IssuedTokenContext ctx = this.itm.createIssuedTokenContext((IssuedTokenConfiguration)defaultSCTokenConfiguration, packet.endpointAddress.toString());
/*  771 */           this.itm.getIssuedToken(ctx);
/*  772 */           this.issuedTokenContextMap.put(scToken.getTokenId(), ctx);
/*      */           
/*  774 */           SCTokenConfiguration sctConfig = ctx.getSecurityPolicy().get(0);
/*  775 */           this.scPolicyIDtoSctIdMap.put(scToken.getTokenId(), sctConfig.getTokenId());
/*  776 */         } catch (WSTrustException se) {
/*  777 */           log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0052_ERROR_ISSUEDTOKEN_CREATION(), (Throwable)se);
/*  778 */           throw new WebServiceException(LogStringsMessages.WSITPVD_0052_ERROR_ISSUEDTOKEN_CREATION(), se);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void cancelSecurityContextToken() {
/*  785 */     Enumeration<String> keys = this.issuedTokenContextMap.keys();
/*  786 */     while (keys.hasMoreElements()) {
/*  787 */       String id = keys.nextElement();
/*  788 */       IssuedTokenContext ctx = this.issuedTokenContextMap.get(id);
/*      */ 
/*      */       
/*  791 */       if (ctx.getSecurityToken() instanceof com.sun.xml.ws.security.SecurityContextToken) {
/*      */         try {
/*  793 */           this.itm.cancelIssuedToken(ctx);
/*  794 */           this.issuedTokenContextMap.remove(id);
/*  795 */           this.scPolicyIDtoSctIdMap.remove(id);
/*  796 */         } catch (WSTrustException se) {
/*  797 */           log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0052_ERROR_ISSUEDTOKEN_CREATION(), (Throwable)se);
/*  798 */           throw new WebServiceException(LogStringsMessages.WSITPVD_0052_ERROR_ISSUEDTOKEN_CREATION(), se);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void invokeTrustPlugin(Packet packet, boolean isSCMessage) {
/*  805 */     List<PolicyAssertion> policies = null;
/*      */ 
/*      */     
/*  808 */     if (isSCMessage) {
/*  809 */       Token scToken = (Token)packet.invocationProperties.get(Constants.SC_ASSERTION);
/*  810 */       policies = getIssuedTokenPoliciesFromBootstrapPolicy(scToken);
/*      */     } else {
/*  812 */       policies = getIssuedTokenPolicies(packet, Constants.OPERATION_SCOPE);
/*      */     } 
/*      */ 
/*      */     
/*  816 */     PolicyAssertion preSetSTSAssertion = null;
/*  817 */     if (this.trustConfig != null) {
/*  818 */       Iterator<PolicyAssertion> it = this.trustConfig.iterator();
/*  819 */       while (it != null && it.hasNext()) {
/*  820 */         preSetSTSAssertion = it.next();
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  825 */     for (PolicyAssertion issuedTokenAssertion : policies) {
/*      */       
/*  827 */       STSIssuedTokenConfiguration rtConfig = null;
/*  828 */       STSIssuedTokenFeature stsFeature = (STSIssuedTokenFeature)this.pipeConfig.getBinding().getFeature(STSIssuedTokenFeature.class);
/*  829 */       if (stsFeature != null) {
/*  830 */         rtConfig = stsFeature.getSTSIssuedTokenConfiguration();
/*      */       }
/*      */ 
/*      */       
/*  834 */       STSIssuedTokenConfiguration config = null;
/*  835 */       if (this.issuedTokenContextMap.get(((Token)issuedTokenAssertion).getTokenId()) == null || rtConfig != null) {
/*      */         try {
/*      */           DefaultSTSIssuedTokenConfiguration defaultSTSIssuedTokenConfiguration;
/*  838 */           String stsEndpoint = (String)packet.invocationProperties.get("sts-endpoint");
/*  839 */           if (stsEndpoint != null) {
/*  840 */             String stsMEXAddress = (String)packet.invocationProperties.get("sts-mex-address");
/*  841 */             if (stsMEXAddress == null) {
/*  842 */               String stsNamespace = (String)packet.invocationProperties.get("sts-namespace");
/*  843 */               String stsWSDLLocation = (String)packet.invocationProperties.get("sts-wsdlLocation");
/*  844 */               String stsServiceName = (String)packet.invocationProperties.get("sts-service-name");
/*  845 */               String stsPortName = (String)packet.invocationProperties.get("sts-port-name");
/*  846 */               defaultSTSIssuedTokenConfiguration = new DefaultSTSIssuedTokenConfiguration(this.wsTrustVer.getNamespaceURI(), stsEndpoint, stsWSDLLocation, stsServiceName, stsPortName, stsNamespace);
/*      */             } else {
/*  848 */               defaultSTSIssuedTokenConfiguration = new DefaultSTSIssuedTokenConfiguration(this.wsTrustVer.getNamespaceURI(), stsEndpoint, stsMEXAddress);
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  853 */           if (defaultSTSIssuedTokenConfiguration == null) {
/*  854 */             defaultSTSIssuedTokenConfiguration = new DefaultSTSIssuedTokenConfiguration(this.wsTrustVer.getNamespaceURI(), (IssuedToken)issuedTokenAssertion, preSetSTSAssertion);
/*      */           }
/*      */           
/*  857 */           defaultSTSIssuedTokenConfiguration.getOtherOptions().putAll(packet.invocationProperties);
/*      */ 
/*      */ 
/*      */           
/*  861 */           if (this.serverCert != null) {
/*  862 */             if (!this.isCertValidityVerified) {
/*  863 */               CertificateRetriever cr = new CertificateRetriever();
/*  864 */               this.isCertValid = cr.setServerCertInTheSTSConfig((STSIssuedTokenConfiguration)defaultSTSIssuedTokenConfiguration, this.secEnv, this.serverCert);
/*  865 */               cr = null;
/*  866 */               this.isCertValidityVerified = true;
/*      */             }
/*  868 */             else if (this.isCertValid == true) {
/*  869 */               defaultSTSIssuedTokenConfiguration.getOtherOptions().put("Identity", this.serverCert);
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*  874 */           if (rtConfig != null) {
/*  875 */             rtConfig.getOtherOptions().put("IssuedToken", defaultSTSIssuedTokenConfiguration);
/*  876 */             rtConfig.getOtherOptions().put("AppliesTo", packet.endpointAddress.toString());
/*  877 */             defaultSTSIssuedTokenConfiguration.copy(rtConfig);
/*      */           } 
/*      */ 
/*      */           
/*  881 */           IssuedTokenContext ctx = this.itm.createIssuedTokenContext((IssuedTokenConfiguration)defaultSTSIssuedTokenConfiguration, packet.endpointAddress.toString());
/*  882 */           this.itm.getIssuedToken(ctx);
/*  883 */           this.issuedTokenContextMap.put(((Token)issuedTokenAssertion).getTokenId(), ctx);
/*      */ 
/*      */           
/*  886 */           updateMPForIssuedTokenAsEncryptedSupportingToken(packet, ctx, ((Token)issuedTokenAssertion).getTokenId());
/*      */         }
/*  888 */         catch (WSTrustException se) {
/*  889 */           log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0052_ERROR_ISSUEDTOKEN_CREATION(), (Throwable)se);
/*  890 */           throw new WebServiceException(LogStringsMessages.WSITPVD_0052_ERROR_ISSUEDTOKEN_CREATION(), se);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<PolicyAssertion> getIssuedTokenPolicies(Packet packet, String scope) {
/*  900 */     List<PolicyAssertion> ret = new ArrayList<PolicyAssertion>();
/*  901 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/*      */       
/*  903 */       WSDLBoundOperation operation = null;
/*  904 */       if (isTrustMessage(packet)) {
/*  905 */         operation = getWSDLOpFromAction(packet, false);
/*      */       } else {
/*  907 */         operation = getOperation(packet.getMessage(), packet);
/*      */       } 
/*      */       
/*  910 */       SecurityPolicyHolder sph = p.getOutMessagePolicyMap().get(operation);
/*  911 */       if (sph != null) {
/*  912 */         ret.addAll(sph.getIssuedTokens());
/*      */       }
/*      */     } 
/*  915 */     return ret;
/*      */   }
/*      */   
/*      */   protected void populateKerberosContext(Packet packet, ProcessingContextImpl ctx, boolean isSCMessage) throws XWSSecurityException {
/*  919 */     List<PolicyAssertion> toks = getOutBoundKTP(packet, isSCMessage);
/*  920 */     if (toks.isEmpty()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  925 */     KerberosContext krbContext = ctx.getSecurityEnvironment().doKerberosLogin();
/*      */     
/*      */     try {
/*  928 */       byte[] krbSha1 = MessageDigest.getInstance("SHA-1").digest(krbContext.getKerberosToken());
/*  929 */       String encKrbSha1 = Base64.encode(krbSha1);
/*  930 */       ctx.setExtraneousProperty("KerbSHA1Value", encKrbSha1);
/*  931 */       ctx.setKerberosContext(krbContext);
/*  932 */     } catch (NoSuchAlgorithmException nsae) {
/*  933 */       throw new XWSSecurityException(nsae);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateMPForIssuedTokenAsEncryptedSupportingToken(Packet packet, IssuedTokenContext ctx, String issuedTokenPolicyId) {
/*  943 */     Message message = packet.getMessage();
/*  944 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/*  945 */       WSDLBoundOperation operation = message.getOperation(this.pipeConfig.getWSDLPort());
/*  946 */       SecurityPolicyHolder sph = p.getOutMessagePolicyMap().get(operation);
/*  947 */       if (sph != null && sph.isIssuedTokenAsEncryptedSupportingToken()) {
/*  948 */         MessagePolicy policy = sph.getMessagePolicy();
/*  949 */         ArrayList list = policy.getPrimaryPolicies();
/*  950 */         Iterator<SecurityPolicy> i = list.iterator();
/*  951 */         boolean breakOuterLoop = false;
/*  952 */         while (i.hasNext()) {
/*  953 */           SecurityPolicy primaryPolicy = i.next();
/*  954 */           if (PolicyTypeUtil.encryptionPolicy(primaryPolicy)) {
/*  955 */             EncryptionPolicy encPolicy = (EncryptionPolicy)primaryPolicy;
/*  956 */             EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)encPolicy.getFeatureBinding();
/*  957 */             ArrayList targetList = featureBinding.getTargetBindings();
/*  958 */             ListIterator<EncryptionTarget> iterator = targetList.listIterator();
/*  959 */             while (iterator.hasNext()) {
/*  960 */               EncryptionTarget encryptionTarget = iterator.next();
/*  961 */               String targetURI = encryptionTarget.getValue();
/*  962 */               if (targetURI.equals(issuedTokenPolicyId) && 
/*  963 */                 ctx != null) {
/*  964 */                 GenericToken issuedToken = (GenericToken)ctx.getSecurityToken();
/*  965 */                 encryptionTarget.setValue(issuedToken.getId());
/*  966 */                 sph.setMessagePolicy(policy);
/*  967 */                 p.getOutMessagePolicyMap().put(operation, sph);
/*  968 */                 breakOuterLoop = true;
/*      */                 
/*      */                 break;
/*      */               } 
/*      */             } 
/*  973 */             if (breakOuterLoop) {
/*      */               break;
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void copyStandardSecurityProperties(Packet packet, Packet requestPacket) {
/*  983 */     String username = (String)packet.invocationProperties.get("username");
/*  984 */     if (username != null) {
/*  985 */       requestPacket.invocationProperties.put("username", username);
/*      */     }
/*  987 */     String password = (String)packet.invocationProperties.get("password");
/*  988 */     if (password != null) {
/*  989 */       requestPacket.invocationProperties.put("password", password);
/*      */     }
/*      */   }
/*      */   
/*      */   private void renewSCT(ProcessingContext ctx, Packet ret) {
/*  994 */     DefaultSCTokenConfiguration defaultSCTokenConfiguration = new DefaultSCTokenConfiguration(this.wsscVer.getNamespaceURI());
/*  995 */     defaultSCTokenConfiguration.getOtherOptions().put("MessagePolicy", (MessagePolicy)ctx.getSecurityPolicy());
/*  996 */     IssuedTokenContext itc = this.itm.createIssuedTokenContext((IssuedTokenConfiguration)defaultSCTokenConfiguration, ret.endpointAddress.toString());
/*      */     try {
/*  998 */       this.itm.renewIssuedToken(itc);
/*  999 */     } catch (WSTrustException se) {
/* 1000 */       log.log(Level.SEVERE, LogStringsMessages.WSITPVD_0052_ERROR_ISSUEDTOKEN_CREATION(), (Throwable)se);
/* 1001 */       throw new WebServiceException(LogStringsMessages.WSITPVD_0027_ERROR_POPULATING_CLIENT_CONFIG_PROP(), se);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\provider\wsit\WSITClientAuthContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */