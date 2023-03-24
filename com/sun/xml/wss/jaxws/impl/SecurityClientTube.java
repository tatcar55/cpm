/*     */ package com.sun.xml.wss.jaxws.impl;
/*     */ 
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.security.CallbackHandlerFeature;
/*     */ import com.sun.xml.ws.api.security.secconv.client.SCTokenConfiguration;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.api.security.trust.client.IssuedTokenConfiguration;
/*     */ import com.sun.xml.ws.api.security.trust.client.IssuedTokenManager;
/*     */ import com.sun.xml.ws.api.security.trust.client.STSIssuedTokenConfiguration;
/*     */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.developer.WSBindingProvider;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.impl.kerberos.KerberosContext;
/*     */ import com.sun.xml.ws.security.impl.policyconv.SecurityPolicyHolder;
/*     */ import com.sun.xml.ws.security.opt.impl.util.CertificateRetriever;
/*     */ import com.sun.xml.ws.security.policy.IssuedToken;
/*     */ import com.sun.xml.ws.security.policy.SecureConversationToken;
/*     */ import com.sun.xml.ws.security.policy.Token;
/*     */ import com.sun.xml.ws.security.secconv.SecureConversationInitiator;
/*     */ import com.sun.xml.ws.security.secconv.WSSecureConversationException;
/*     */ import com.sun.xml.ws.security.secconv.impl.client.DefaultSCTokenConfiguration;
/*     */ import com.sun.xml.ws.security.trust.GenericToken;
/*     */ import com.sun.xml.ws.security.trust.STSIssuedTokenFeature;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.impl.client.DefaultSTSIssuedTokenConfiguration;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.SecurityEnvironment;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.PolicyResolver;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.ProcessingContextImpl;
/*     */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.misc.DefaultCallbackHandler;
/*     */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import com.sun.xml.wss.jaxws.impl.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.provider.wsit.PolicyAlternativeHolder;
/*     */ import com.sun.xml.wss.provider.wsit.PolicyResolverFactory;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ public class SecurityClientTube
/*     */   extends SecurityTubeBase
/*     */   implements SecureConversationInitiator
/*     */ {
/* 140 */   private IssuedTokenManager itm = IssuedTokenManager.getInstance();
/* 141 */   private Hashtable<String, String> scPolicyIDtoSctIdMap = new Hashtable<String, String>();
/*     */   
/* 143 */   private Set trustConfig = null;
/* 144 */   private Set wsscConfig = null;
/* 145 */   private Set<PolicyAssertion> configAssertions = null;
/* 146 */   Properties props = new Properties();
/*     */   
/*     */   private ClientTubelineAssemblyContext wsitContext;
/*     */ 
/*     */   
/*     */   public SecurityClientTube(ClientTubelineAssemblyContext wsitContext, Tube nextTube) {
/* 152 */     super(new ClientTubeConfiguration(wsitContext.getPolicyMap(), wsitContext.getWsdlPort(), wsitContext.getBinding()), nextTube);
/*     */     
/*     */     try {
/* 155 */       for (PolicyAlternativeHolder p : this.policyAlternatives) {
/* 156 */         Iterator<SecurityPolicyHolder> it = p.getOutMessagePolicyMap().values().iterator();
/* 157 */         while (it.hasNext()) {
/* 158 */           SecurityPolicyHolder holder = it.next();
/* 159 */           if (this.configAssertions != null) {
/* 160 */             this.configAssertions.addAll(holder.getConfigAssertions("http://schemas.sun.com/2006/03/wss/client"));
/*     */           } else {
/* 162 */             this.configAssertions = holder.getConfigAssertions("http://schemas.sun.com/2006/03/wss/client");
/*     */           } 
/* 164 */           if (this.trustConfig != null) {
/* 165 */             this.trustConfig.addAll(holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/trust/client"));
/*     */           } else {
/* 167 */             this.trustConfig = holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/trust/client");
/*     */           } 
/* 169 */           if (this.wsscConfig != null) {
/* 170 */             this.wsscConfig.addAll(holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/sc/client")); continue;
/*     */           } 
/* 172 */           this.wsscConfig = holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/sc/client");
/*     */         } 
/*     */       } 
/*     */       
/* 176 */       this.wsitContext = wsitContext;
/*     */ 
/*     */ 
/*     */       
/* 180 */       CallbackHandler handler = configureClientHandler(this.configAssertions, this.props);
/* 181 */       this.secEnv = (SecurityEnvironment)new DefaultSecurityEnvironmentImpl(handler, this.props);
/* 182 */     } catch (Exception e) {
/* 183 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0023_ERROR_CREATING_NEW_INSTANCE_SEC_CLIENT_TUBE(), e);
/*     */       
/* 185 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0023_ERROR_CREATING_NEW_INSTANCE_SEC_CLIENT_TUBE(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SecurityClientTube(SecurityClientTube that, TubeCloner cloner) {
/* 192 */     super(that, cloner);
/* 193 */     this.trustConfig = that.trustConfig;
/* 194 */     this.wsscConfig = that.wsscConfig;
/* 195 */     this.scPolicyIDtoSctIdMap = that.scPolicyIDtoSctIdMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 200 */     return (AbstractTubeImpl)new SecurityClientTube(this, cloner);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet packet) {
/*     */     try {
/* 207 */       HaContext.initFrom(packet);
/*     */ 
/*     */       
/* 210 */       if (this.wsitContext != null) {
/* 211 */         WSBindingProvider bpr = this.wsitContext.getWrappedContext().getBindingProvider();
/* 212 */         WSEndpointReference epr = bpr.getWSEndpointReference();
/* 213 */         X509Certificate x509Cert = (X509Certificate)bpr.getRequestContext().get("server-certificate");
/* 214 */         if (x509Cert == null) {
/* 215 */           if (epr != null) {
/* 216 */             WSEndpointReference.EPRExtension idExtn = null;
/* 217 */             XMLStreamReader xmlReader = null;
/*     */             try {
/* 219 */               QName ID_QNAME = new QName("http://schemas.xmlsoap.org/ws/2006/02/addressingidentity", "Identity");
/* 220 */               idExtn = epr.getEPRExtension(ID_QNAME);
/* 221 */               if (idExtn != null) {
/* 222 */                 xmlReader = idExtn.readAsXMLStreamReader();
/* 223 */                 CertificateRetriever cr = new CertificateRetriever();
/*     */                 
/* 225 */                 byte[] bstValue = cr.getBSTFromIdentityExtension(xmlReader);
/* 226 */                 X509Certificate certificate = null;
/* 227 */                 if (bstValue != null) {
/* 228 */                   certificate = cr.constructCertificate(bstValue);
/*     */                 }
/* 230 */                 if (certificate != null) {
/* 231 */                   this.props.put("SERVER_CERT", certificate);
/* 232 */                   this.serverCert = certificate;
/*     */                 } 
/*     */               } 
/* 235 */             } catch (XMLStreamException ex) {
/* 236 */               log.log(Level.WARNING, ex.getMessage());
/*     */             }
/*     */           
/*     */           } 
/*     */         } else {
/*     */           
/* 242 */           this.props.put("SERVER_CERT", x509Cert);
/* 243 */           this.serverCert = x509Cert;
/*     */         } 
/*     */       } 
/*     */       try {
/* 247 */         packet = processClientRequestPacket(packet);
/* 248 */       } catch (Throwable t) {
/* 249 */         if (!(t instanceof WebServiceException)) {
/* 250 */           t = new WebServiceException(t);
/*     */         }
/* 252 */         return doThrow(t);
/*     */       } 
/* 254 */       return doInvoke(this.next, packet);
/*     */     } finally {
/* 256 */       HaContext.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet processClientRequestPacket(Packet packet) {
/* 263 */     if ("true".equals(packet.invocationProperties.get("isTrustMessage"))) {
/* 264 */       String action = (String)packet.invocationProperties.get("trustAction");
/* 265 */       HeaderList headers = packet.getMessage().getHeaders();
/* 266 */       headers.fillRequestAddressingHeaders(packet, this.addVer, this.soapVersion, false, action);
/*     */     } 
/*     */ 
/*     */     
/* 270 */     Message msg = packet.getInternalMessage();
/*     */     
/* 272 */     boolean isSCMessage = isSCMessage(packet);
/*     */     
/* 274 */     if (!isSCMessage && !isSCCancel(packet))
/*     */     {
/*     */       
/* 277 */       invokeSCPlugin(packet);
/*     */     }
/*     */ 
/*     */     
/* 281 */     invokeTrustPlugin(packet, isSCMessage);
/*     */ 
/*     */     
/* 284 */     ProcessingContext ctx = initializeOutgoingProcessingContext(packet, isSCMessage);
/* 285 */     ((ProcessingContextImpl)ctx).setIssuedTokenContextMap(this.issuedTokenContextMap);
/* 286 */     ((ProcessingContextImpl)ctx).setSCPolicyIDtoSctIdMap(this.scPolicyIDtoSctIdMap);
/* 287 */     ctx.isClient(true);
/*     */     try {
/* 289 */       if (hasKerberosTokenPolicy()) {
/* 290 */         populateKerberosContext(packet, (ProcessingContextImpl)ctx, isSCMessage);
/*     */       }
/* 292 */     } catch (XWSSecurityException xwsse) {
/* 293 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0024_ERROR_SECURING_OUTBOUND_MSG(), (Throwable)xwsse);
/*     */       
/* 295 */       throw new WebServiceException(LogStringsMessages.WSSTUBE_0024_ERROR_SECURING_OUTBOUND_MSG(), xwsse);
/*     */     } 
/*     */ 
/*     */     
/* 299 */     if (isSCRenew(packet)) {
/*     */ 
/*     */       
/* 302 */       DefaultSCTokenConfiguration defaultSCTokenConfiguration = new DefaultSCTokenConfiguration(this.wsscVer.getNamespaceURI());
/* 303 */       defaultSCTokenConfiguration.getOtherOptions().put("MessagePolicy", (MessagePolicy)ctx.getSecurityPolicy());
/* 304 */       IssuedTokenContext itc = this.itm.createIssuedTokenContext((IssuedTokenConfiguration)defaultSCTokenConfiguration, packet.endpointAddress.toString());
/*     */       try {
/* 306 */         this.itm.renewIssuedToken(itc);
/* 307 */       } catch (WSTrustException se) {
/* 308 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0035_ERROR_ISSUEDTOKEN_CREATION(), (Throwable)se);
/*     */         
/* 310 */         throw new WebServiceException(LogStringsMessages.WSSTUBE_0035_ERROR_ISSUEDTOKEN_CREATION(), se);
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 315 */       if (!this.optimized) {
/* 316 */         if (!isSCMessage) {
/* 317 */           cacheOperation(msg);
/*     */         }
/* 319 */         SOAPMessage soapMessage = msg.readAsSOAPMessage();
/* 320 */         soapMessage = secureOutboundMessage(soapMessage, ctx);
/* 321 */         msg = Messages.create(soapMessage);
/*     */       } else {
/* 323 */         msg = secureOutboundMessage(msg, ctx);
/*     */       } 
/* 325 */     } catch (WssSoapFaultException ex) {
/* 326 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0024_ERROR_SECURING_OUTBOUND_MSG(), (Throwable)ex);
/*     */       
/* 328 */       throw new WebServiceException(LogStringsMessages.WSSTUBE_0024_ERROR_SECURING_OUTBOUND_MSG(), getSOAPFaultException(ex));
/*     */     }
/* 330 */     catch (SOAPException se) {
/* 331 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0024_ERROR_SECURING_OUTBOUND_MSG(), se);
/*     */       
/* 333 */       throw new WebServiceException(LogStringsMessages.WSSTUBE_0024_ERROR_SECURING_OUTBOUND_MSG(), se);
/*     */     } 
/*     */     
/* 336 */     packet.setMessage(msg);
/*     */     
/* 338 */     if (isSCRenew(packet)) {
/*     */ 
/*     */       
/* 341 */       Token scToken = (Token)packet.invocationProperties.get(Constants.SC_ASSERTION);
/* 342 */       DefaultSCTokenConfiguration defaultSCTokenConfiguration = new DefaultSCTokenConfiguration(this.wsscVer.getNamespaceURI(), false);
/* 343 */       defaultSCTokenConfiguration.getOtherOptions().put("MessagePolicy", getOutgoingXWSBootstrapPolicy(scToken));
/* 344 */       IssuedTokenContext itc = this.itm.createIssuedTokenContext((IssuedTokenConfiguration)defaultSCTokenConfiguration, packet.endpointAddress.toString());
/*     */       try {
/* 346 */         this.itm.renewIssuedToken(itc);
/* 347 */       } catch (WSTrustException se) {
/* 348 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0035_ERROR_ISSUEDTOKEN_CREATION(), (Throwable)se);
/*     */         
/* 350 */         throw new WebServiceException(LogStringsMessages.WSSTUBE_0035_ERROR_ISSUEDTOKEN_CREATION(), se);
/*     */       } 
/*     */     } 
/*     */     
/* 354 */     return packet;
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet ret) {
/*     */     try {
/* 360 */       HaContext.initFrom(ret);
/*     */       try {
/* 362 */         ret = processClientResponsePacket(ret);
/* 363 */       } catch (Throwable t) {
/* 364 */         if (!(t instanceof WebServiceException)) {
/* 365 */           t = new WebServiceException(t);
/*     */         }
/* 367 */         return doThrow(t);
/*     */       } 
/* 369 */       return doReturnWith(ret);
/*     */     } finally {
/* 371 */       HaContext.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Packet processClientResponsePacket(Packet ret) {
/* 376 */     boolean isTrustMsg = false;
/* 377 */     if ("true".equals(ret.invocationProperties.get("isTrustMessage"))) {
/* 378 */       isTrustMsg = true;
/*     */     }
/*     */ 
/*     */     
/* 382 */     if (ret.getInternalMessage() == null) {
/* 383 */       return ret;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 403 */     ProcessingContext ctx = initializeInboundProcessingContext(ret);
/* 404 */     ctx.isClient(true);
/*     */     
/* 406 */     ((ProcessingContextImpl)ctx).setIssuedTokenContextMap(this.issuedTokenContextMap);
/* 407 */     ((ProcessingContextImpl)ctx).setSCPolicyIDtoSctIdMap(this.scPolicyIDtoSctIdMap);
/* 408 */     PolicyResolver pr = PolicyResolverFactory.createPolicyResolver(this.policyAlternatives, this.cachedOperation, this.tubeConfig, this.addVer, true, this.rmVer, this.mcVer);
/* 409 */     ctx.setExtraneousProperty("OperationResolver", pr);
/* 410 */     Message msg = null;
/*     */     try {
/* 412 */       msg = ret.getInternalMessage();
/*     */       
/* 414 */       if (msg == null) {
/* 415 */         return ret;
/*     */       }
/*     */       
/* 418 */       if (!this.optimized) {
/* 419 */         SOAPMessage soapMessage = msg.readAsSOAPMessage();
/* 420 */         soapMessage = verifyInboundMessage(soapMessage, ctx);
/* 421 */         if (msg.isFault()) {
/*     */ 
/*     */ 
/*     */           
/* 425 */           SOAPFault fault = soapMessage.getSOAPBody().getFault();
/* 426 */           if ((new QName(this.wsscVer.getNamespaceURI(), "RenewNeeded")).equals(fault.getFaultCodeAsQName())) {
/* 427 */             renewSCT(ctx, ret);
/*     */           }
/* 429 */           log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0034_FAULTY_RESPONSE_MSG(fault));
/* 430 */           throw new SOAPFaultException(fault);
/*     */         } 
/* 432 */         msg = Messages.create(soapMessage);
/*     */       } else {
/* 434 */         msg = verifyInboundMessage(msg, ctx);
/*     */       } 
/* 436 */     } catch (XWSSecurityException xwse) {
/* 437 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0025_ERROR_VERIFY_INBOUND_MSG(), (Throwable)xwse);
/*     */       
/* 439 */       throw new WebServiceException(LogStringsMessages.WSSTUBE_0025_ERROR_VERIFY_INBOUND_MSG(), getSOAPFaultException(xwse));
/*     */     }
/* 441 */     catch (SOAPException se) {
/* 442 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0025_ERROR_VERIFY_INBOUND_MSG(), se);
/*     */       
/* 444 */       throw new WebServiceException(LogStringsMessages.WSSTUBE_0025_ERROR_VERIFY_INBOUND_MSG(), se);
/*     */     } 
/* 446 */     resetCachedOperation();
/* 447 */     ret.setMessage(msg);
/*     */     
/* 449 */     if (isTrustMsg)
/*     */     {
/* 451 */       getAction(ret);
/*     */     }
/*     */     
/* 454 */     return ret;
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processException(Throwable t) {
/* 459 */     if (!(t instanceof WebServiceException)) {
/* 460 */       t = new WebServiceException(t);
/*     */     }
/* 462 */     return doThrow(t);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void invokeSCPlugin(Packet packet) {
/* 468 */     List<PolicyAssertion> policies = getOutBoundSCP(packet.getMessage());
/*     */     
/* 470 */     PolicyAssertion scClientAssertion = null;
/* 471 */     if (this.wsscConfig != null) {
/* 472 */       Iterator<PolicyAssertion> it = this.wsscConfig.iterator();
/* 473 */       while (it != null && it.hasNext()) {
/* 474 */         scClientAssertion = it.next();
/*     */       }
/*     */     } 
/*     */     
/* 478 */     for (PolicyAssertion scAssertion : policies) {
/* 479 */       Token scToken = (Token)scAssertion;
/* 480 */       if (this.issuedTokenContextMap.get(scToken.getTokenId()) == null) {
/*     */         try {
/* 482 */           DefaultSCTokenConfiguration defaultSCTokenConfiguration = new DefaultSCTokenConfiguration(this.wsscVer.getNamespaceURI(), (SecureConversationToken)scToken, this.tubeConfig.getWSDLPort(), this.tubeConfig.getBinding(), (Tube)this, packet, this.addVer, scClientAssertion, this.next);
/*     */ 
/*     */           
/* 485 */           IssuedTokenContext ctx = this.itm.createIssuedTokenContext((IssuedTokenConfiguration)defaultSCTokenConfiguration, packet.endpointAddress.toString());
/* 486 */           this.itm.getIssuedToken(ctx);
/* 487 */           this.issuedTokenContextMap.put(scToken.getTokenId(), ctx);
/*     */ 
/*     */           
/* 490 */           SCTokenConfiguration sctConfig = ctx.getSecurityPolicy().get(0);
/* 491 */           this.scPolicyIDtoSctIdMap.put(scToken.getTokenId(), sctConfig.getTokenId());
/* 492 */         } catch (WSTrustException se) {
/* 493 */           log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0035_ERROR_ISSUEDTOKEN_CREATION(), (Throwable)se);
/*     */           
/* 495 */           throw new WebServiceException(LogStringsMessages.WSSTUBE_0035_ERROR_ISSUEDTOKEN_CREATION(), se);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<PolicyAssertion> getIssuedTokenPolicies(Packet packet, String scope) {
/* 504 */     List<PolicyAssertion> ret = new ArrayList<PolicyAssertion>();
/* 505 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/*     */       
/* 507 */       WSDLBoundOperation operation = null;
/* 508 */       if (isTrustMessage(packet)) {
/* 509 */         operation = getWSDLOpFromAction(packet, false);
/* 510 */         this.cachedOperation = operation;
/*     */       } else {
/* 512 */         operation = getOperation(packet.getMessage());
/*     */       } 
/*     */       
/* 515 */       SecurityPolicyHolder sph = (SecurityPolicyHolder)p.getOutMessagePolicyMap().get(operation);
/* 516 */       if (sph != null) {
/* 517 */         ret.addAll(sph.getIssuedTokens());
/*     */       }
/*     */     } 
/* 520 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBElement startSecureConversation(Packet packet) throws WSSecureConversationException {
/* 526 */     List<PolicyAssertion> toks = getOutBoundSCP(packet.getMessage());
/* 527 */     if (toks.isEmpty()) {
/* 528 */       if (log.isLoggable(Level.FINE)) {
/* 529 */         log.log(Level.FINE, LogStringsMessages.WSSTUBE_0026_NO_POLICY_FOUND_FOR_SC());
/*     */       }
/*     */ 
/*     */       
/* 533 */       return null;
/*     */     } 
/*     */     
/* 536 */     Token tok = (Token)toks.get(0);
/* 537 */     IssuedTokenContext ctx = this.issuedTokenContextMap.get(tok.getTokenId());
/*     */ 
/*     */     
/* 540 */     PolicyAssertion scClientAssertion = null;
/* 541 */     if (this.wsscConfig != null) {
/* 542 */       Iterator<PolicyAssertion> it = this.wsscConfig.iterator();
/* 543 */       while (it != null && it.hasNext()) {
/* 544 */         scClientAssertion = it.next();
/*     */       }
/*     */     } 
/*     */     
/* 548 */     if (ctx == null) {
/*     */       try {
/* 550 */         DefaultSCTokenConfiguration defaultSCTokenConfiguration = new DefaultSCTokenConfiguration(this.wsscVer.getNamespaceURI(), (SecureConversationToken)tok, this.tubeConfig.getWSDLPort(), this.tubeConfig.getBinding(), (Tube)this, packet, this.addVer, scClientAssertion, this.next);
/*     */ 
/*     */ 
/*     */         
/* 554 */         ctx = this.itm.createIssuedTokenContext((IssuedTokenConfiguration)defaultSCTokenConfiguration, packet.endpointAddress.toString());
/* 555 */         this.itm.getIssuedToken(ctx);
/* 556 */         this.issuedTokenContextMap.put(tok.getTokenId(), ctx);
/*     */         
/* 558 */         SCTokenConfiguration sctConfig = ctx.getSecurityPolicy().get(0);
/* 559 */         this.scPolicyIDtoSctIdMap.put(tok.getTokenId(), sctConfig.getTokenId());
/* 560 */       } catch (WSTrustException se) {
/* 561 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0035_ERROR_ISSUEDTOKEN_CREATION(), (Throwable)se);
/*     */         
/* 563 */         throw new WebServiceException(LogStringsMessages.WSSTUBE_0035_ERROR_ISSUEDTOKEN_CREATION(), se);
/*     */       } 
/*     */     }
/*     */     
/* 567 */     SecurityTokenReference str = (SecurityTokenReference)ctx.getUnAttachedSecurityTokenReference();
/*     */     
/* 569 */     return WSTrustElementFactory.newInstance().toJAXBElement(str);
/*     */   }
/*     */   
/*     */   private void cancelSecurityContextToken() {
/* 573 */     Enumeration<String> keys = this.issuedTokenContextMap.keys();
/* 574 */     while (keys.hasMoreElements()) {
/* 575 */       String id = keys.nextElement();
/* 576 */       IssuedTokenContext ctx = this.issuedTokenContextMap.get(id);
/*     */ 
/*     */       
/* 579 */       if (ctx.getSecurityToken() instanceof com.sun.xml.ws.security.SecurityContextToken) {
/*     */         try {
/* 581 */           this.itm.cancelIssuedToken(ctx);
/* 582 */           this.issuedTokenContextMap.remove(id);
/* 583 */           this.scPolicyIDtoSctIdMap.remove(id);
/* 584 */         } catch (WSTrustException se) {
/* 585 */           log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0035_ERROR_ISSUEDTOKEN_CREATION(), (Throwable)se);
/*     */           
/* 587 */           throw new WebServiceException(LogStringsMessages.WSSTUBE_0035_ERROR_ISSUEDTOKEN_CREATION(), se);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void preDestroy() {
/* 595 */     cancelSecurityContextToken();
/* 596 */     if (this.next != null) {
/* 597 */       this.next.preDestroy();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void invokeTrustPlugin(Packet packet, boolean isSCMessage) {
/* 604 */     List<PolicyAssertion> policies = null;
/*     */ 
/*     */     
/* 607 */     if (isSCMessage) {
/* 608 */       Token scToken = (Token)packet.invocationProperties.get(Constants.SC_ASSERTION);
/* 609 */       policies = getIssuedTokenPoliciesFromBootstrapPolicy(scToken);
/*     */     } else {
/* 611 */       policies = getIssuedTokenPolicies(packet, Constants.OPERATION_SCOPE);
/*     */     } 
/*     */ 
/*     */     
/* 615 */     PolicyAssertion preSetSTSAssertion = null;
/* 616 */     if (this.trustConfig != null) {
/* 617 */       Iterator<PolicyAssertion> it = this.trustConfig.iterator();
/* 618 */       while (it != null && it.hasNext()) {
/* 619 */         preSetSTSAssertion = it.next();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 624 */     for (PolicyAssertion issuedTokenAssertion : policies) {
/*     */ 
/*     */       
/* 627 */       STSIssuedTokenConfiguration rtConfig = null;
/* 628 */       STSIssuedTokenFeature stsFeature = (STSIssuedTokenFeature)this.tubeConfig.getBinding().getFeature(STSIssuedTokenFeature.class);
/* 629 */       if (stsFeature != null) {
/* 630 */         rtConfig = stsFeature.getSTSIssuedTokenConfiguration();
/*     */       }
/*     */ 
/*     */       
/* 634 */       STSIssuedTokenConfiguration config = null;
/* 635 */       if (this.issuedTokenContextMap.get(((Token)issuedTokenAssertion).getTokenId()) == null || rtConfig != null) {
/*     */         try {
/*     */           DefaultSTSIssuedTokenConfiguration defaultSTSIssuedTokenConfiguration;
/* 638 */           String stsEndpoint = (String)packet.invocationProperties.get("sts-endpoint");
/* 639 */           if (stsEndpoint != null) {
/* 640 */             String stsMEXAddress = (String)packet.invocationProperties.get("sts-mex-address");
/* 641 */             if (stsMEXAddress == null) {
/* 642 */               String stsNamespace = (String)packet.invocationProperties.get("sts-namespace");
/* 643 */               String stsWSDLLocation = (String)packet.invocationProperties.get("sts-wsdlLocation");
/* 644 */               String stsServiceName = (String)packet.invocationProperties.get("sts-service-name");
/* 645 */               String stsPortName = (String)packet.invocationProperties.get("sts-port-name");
/* 646 */               defaultSTSIssuedTokenConfiguration = new DefaultSTSIssuedTokenConfiguration(this.wsTrustVer.getNamespaceURI(), stsEndpoint, stsWSDLLocation, stsServiceName, stsPortName, stsNamespace);
/*     */             } else {
/* 648 */               defaultSTSIssuedTokenConfiguration = new DefaultSTSIssuedTokenConfiguration(this.wsTrustVer.getNamespaceURI(), stsEndpoint, stsMEXAddress);
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 653 */           if (defaultSTSIssuedTokenConfiguration == null) {
/* 654 */             defaultSTSIssuedTokenConfiguration = new DefaultSTSIssuedTokenConfiguration(this.wsTrustVer.getNamespaceURI(), (IssuedToken)issuedTokenAssertion, preSetSTSAssertion);
/*     */           }
/*     */           
/* 657 */           defaultSTSIssuedTokenConfiguration.getOtherOptions().putAll(packet.invocationProperties);
/*     */ 
/*     */           
/* 660 */           X509Certificate x509ServerCertificate = (X509Certificate)this.props.get("SERVER_CERT");
/*     */           
/* 662 */           if (x509ServerCertificate != null) {
/* 663 */             if (!this.isCertValidityVerified) {
/* 664 */               CertificateRetriever cr = new CertificateRetriever();
/* 665 */               this.isCertValid = cr.setServerCertInTheSTSConfig((STSIssuedTokenConfiguration)defaultSTSIssuedTokenConfiguration, this.secEnv, x509ServerCertificate);
/* 666 */               cr = null;
/* 667 */               this.isCertValidityVerified = true;
/*     */             }
/* 669 */             else if (this.isCertValid == true) {
/* 670 */               defaultSTSIssuedTokenConfiguration.getOtherOptions().put("Identity", x509ServerCertificate);
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 676 */           if (rtConfig != null) {
/* 677 */             rtConfig.getOtherOptions().put("IssuedToken", defaultSTSIssuedTokenConfiguration);
/* 678 */             rtConfig.getOtherOptions().put("AppliesTo", packet.endpointAddress.toString());
/* 679 */             defaultSTSIssuedTokenConfiguration.copy(rtConfig);
/*     */             
/* 681 */             defaultSTSIssuedTokenConfiguration.getOtherOptions().put("RunTimeConfig", rtConfig);
/*     */           } 
/*     */ 
/*     */           
/* 685 */           IssuedTokenContext ctx = this.itm.createIssuedTokenContext((IssuedTokenConfiguration)defaultSTSIssuedTokenConfiguration, packet.endpointAddress.toString());
/* 686 */           this.itm.getIssuedToken(ctx);
/* 687 */           this.issuedTokenContextMap.put(((Token)issuedTokenAssertion).getTokenId(), ctx);
/*     */ 
/*     */           
/* 690 */           updateMPForIssuedTokenAsEncryptedSupportingToken(packet, ctx, ((Token)issuedTokenAssertion).getTokenId());
/*     */         }
/* 692 */         catch (WSTrustException se) {
/* 693 */           log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0035_ERROR_ISSUEDTOKEN_CREATION(), (Throwable)se);
/*     */           
/* 695 */           throw new WebServiceException(LogStringsMessages.WSSTUBE_0035_ERROR_ISSUEDTOKEN_CREATION(), se);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected SecurityPolicyHolder addOutgoingMP(WSDLBoundOperation operation, Policy policy, PolicyAlternativeHolder ph) throws PolicyException {
/* 702 */     SecurityPolicyHolder sph = constructPolicyHolder(policy, false, false);
/* 703 */     ph.getOutMessagePolicyMap().put(operation, sph);
/* 704 */     return sph;
/*     */   }
/*     */   
/*     */   protected SecurityPolicyHolder addIncomingMP(WSDLBoundOperation operation, Policy policy, PolicyAlternativeHolder ph) throws PolicyException {
/* 708 */     SecurityPolicyHolder sph = constructPolicyHolder(policy, false, true);
/* 709 */     ph.getInMessagePolicyMap().put(operation, sph);
/* 710 */     return sph;
/*     */   }
/*     */   
/*     */   protected void addIncomingProtocolPolicy(Policy effectivePolicy, String protocol, PolicyAlternativeHolder ph) throws PolicyException {
/* 714 */     ph.getInProtocolPM().put(protocol, constructPolicyHolder(effectivePolicy, false, true, true));
/*     */   }
/*     */   
/*     */   protected void addOutgoingProtocolPolicy(Policy effectivePolicy, String protocol, PolicyAlternativeHolder ph) throws PolicyException {
/* 718 */     ph.getOutProtocolPM().put(protocol, constructPolicyHolder(effectivePolicy, false, false, false));
/*     */   }
/*     */   
/*     */   protected void addIncomingFaultPolicy(Policy effectivePolicy, SecurityPolicyHolder sph, WSDLFault fault) throws PolicyException {
/* 722 */     SecurityPolicyHolder faultPH = constructPolicyHolder(effectivePolicy, false, true);
/* 723 */     sph.addFaultPolicy(fault, faultPH);
/*     */   }
/*     */   
/*     */   protected void addOutgoingFaultPolicy(Policy effectivePolicy, SecurityPolicyHolder sph, WSDLFault fault) throws PolicyException {
/* 727 */     SecurityPolicyHolder faultPH = constructPolicyHolder(effectivePolicy, false, false);
/* 728 */     sph.addFaultPolicy(fault, faultPH);
/*     */   }
/*     */   
/*     */   protected String getAction(WSDLOperation operation, boolean inComming) {
/* 732 */     if (!inComming) {
/* 733 */       return operation.getInput().getAction();
/*     */     }
/* 735 */     return operation.getOutput().getAction();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populateKerberosContext(Packet packet, ProcessingContextImpl ctx, boolean isSCMessage) throws XWSSecurityException {
/* 740 */     List<PolicyAssertion> toks = getOutBoundKTP(packet, isSCMessage);
/* 741 */     if (toks.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 745 */     KerberosContext krbContext = ctx.getSecurityEnvironment().doKerberosLogin();
/*     */     
/*     */     try {
/* 748 */       byte[] krbSha1 = MessageDigest.getInstance("SHA-1").digest(krbContext.getKerberosToken());
/* 749 */       String encKrbSha1 = Base64.encode(krbSha1);
/* 750 */       ctx.setExtraneousProperty("KerbSHA1Value", encKrbSha1);
/* 751 */       ctx.setKerberosContext(krbContext);
/* 752 */     } catch (NoSuchAlgorithmException nsae) {
/* 753 */       throw new XWSSecurityException(nsae);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateMPForIssuedTokenAsEncryptedSupportingToken(Packet packet, IssuedTokenContext ctx, String issuedTokenPolicyId) {
/* 763 */     Message message = packet.getMessage();
/* 764 */     for (PolicyAlternativeHolder p : this.policyAlternatives) {
/* 765 */       WSDLBoundOperation operation = message.getOperation(this.tubeConfig.getWSDLPort());
/* 766 */       SecurityPolicyHolder sph = (SecurityPolicyHolder)p.getOutMessagePolicyMap().get(operation);
/* 767 */       if (sph != null && sph.isIssuedTokenAsEncryptedSupportingToken()) {
/* 768 */         MessagePolicy policy = sph.getMessagePolicy();
/* 769 */         ArrayList list = policy.getPrimaryPolicies();
/* 770 */         Iterator<SecurityPolicy> i = list.iterator();
/* 771 */         boolean breakOuterLoop = false;
/* 772 */         while (i.hasNext()) {
/* 773 */           SecurityPolicy primaryPolicy = i.next();
/* 774 */           if (PolicyTypeUtil.encryptionPolicy(primaryPolicy)) {
/* 775 */             EncryptionPolicy encPolicy = (EncryptionPolicy)primaryPolicy;
/* 776 */             EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)encPolicy.getFeatureBinding();
/* 777 */             ArrayList targetList = featureBinding.getTargetBindings();
/* 778 */             ListIterator<EncryptionTarget> iterator = targetList.listIterator();
/* 779 */             while (iterator.hasNext()) {
/* 780 */               EncryptionTarget encryptionTarget = iterator.next();
/* 781 */               String targetURI = encryptionTarget.getValue();
/* 782 */               if (targetURI.equals(issuedTokenPolicyId) && 
/* 783 */                 ctx != null) {
/* 784 */                 GenericToken issuedToken = (GenericToken)ctx.getSecurityToken();
/* 785 */                 encryptionTarget.setValue(issuedToken.getId());
/* 786 */                 sph.setMessagePolicy(policy);
/* 787 */                 p.getOutMessagePolicyMap().put(operation, sph);
/* 788 */                 breakOuterLoop = true;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 793 */             if (breakOuterLoop) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private CallbackHandler configureClientHandler(Set<PolicyAssertion> configAssertions, Properties props) {
/* 804 */     CallbackHandlerFeature chf = (CallbackHandlerFeature)this.tubeConfig.getBinding().getFeature(CallbackHandlerFeature.class);
/* 805 */     if (chf != null) {
/* 806 */       return chf.getHandler();
/*     */     }
/*     */ 
/*     */     
/* 810 */     String ret = populateConfigProperties(configAssertions, props);
/*     */     try {
/* 812 */       if (ret != null) {
/* 813 */         Class handler = loadClass(ret);
/* 814 */         Object obj = handler.newInstance();
/* 815 */         if (!(obj instanceof CallbackHandler)) {
/* 816 */           log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0033_INVALID_CALLBACK_HANDLER_CLASS(ret));
/*     */           
/* 818 */           throw new RuntimeException(LogStringsMessages.WSSTUBE_0033_INVALID_CALLBACK_HANDLER_CLASS(ret));
/*     */         } 
/*     */         
/* 821 */         return (CallbackHandler)obj;
/*     */       } 
/* 823 */       return (CallbackHandler)new DefaultCallbackHandler("client", props);
/* 824 */     } catch (Exception e) {
/* 825 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0027_ERROR_CONFIGURE_CLIENT_HANDLER(), e);
/*     */       
/* 827 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0027_ERROR_CONFIGURE_CLIENT_HANDLER(), e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void renewSCT(ProcessingContext ctx, Packet ret) {
/* 832 */     DefaultSCTokenConfiguration defaultSCTokenConfiguration = new DefaultSCTokenConfiguration(this.wsscVer.getNamespaceURI());
/* 833 */     defaultSCTokenConfiguration.getOtherOptions().put("MessagePolicy", (MessagePolicy)ctx.getSecurityPolicy());
/* 834 */     IssuedTokenContext itc = this.itm.createIssuedTokenContext((IssuedTokenConfiguration)defaultSCTokenConfiguration, ret.endpointAddress.toString());
/*     */     try {
/* 836 */       this.itm.renewIssuedToken(itc);
/* 837 */     } catch (WSTrustException se) {
/* 838 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0035_ERROR_ISSUEDTOKEN_CREATION(), (Throwable)se);
/*     */       
/* 840 */       throw new WebServiceException(LogStringsMessages.WSSTUBE_0035_ERROR_ISSUEDTOKEN_CREATION(), se);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\jaxws\impl\SecurityClientTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */