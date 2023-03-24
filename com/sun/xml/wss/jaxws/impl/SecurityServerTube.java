/*     */ package com.sun.xml.wss.jaxws.impl;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.message.stream.InputStreamMessage;
/*     */ import com.sun.xml.ws.api.message.stream.XMLStreamReaderMessage;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLFault;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLOperation;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.security.CallbackHandlerFeature;
/*     */ import com.sun.xml.ws.api.security.secconv.WSSecureConversationRuntimeException;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.server.WebServiceContextDelegate;
/*     */ import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
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
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
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
/*     */ import com.sun.xml.wss.impl.misc.DefaultCallbackHandler;
/*     */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import com.sun.xml.wss.jaxws.impl.logging.LogStringsMessages;
/*     */ import com.sun.xml.wss.provider.wsit.PolicyAlternativeHolder;
/*     */ import com.sun.xml.wss.provider.wsit.PolicyResolverFactory;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import javax.security.auth.Subject;
/*     */ import javax.security.auth.callback.CallbackHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityServerTube
/*     */   extends SecurityTubeBase
/*     */ {
/*     */   private static final String WSCONTEXT_DELEGATE = "META-INF/services/com.sun.xml.ws.api.server.WebServiceContextDelegate";
/* 145 */   private Class contextDelegate = null;
/* 146 */   private SessionManager sessionManager = null;
/*     */   
/* 148 */   private Set trustConfig = null;
/* 149 */   private Set wsscConfig = null;
/* 150 */   private CallbackHandler handler = null;
/*     */   private Packet tmpPacket;
/*     */   private boolean isTrustMessage;
/*     */   private boolean isSCIssueMessage;
/*     */   private boolean isSCCancelMessage;
/* 155 */   private String reqAction = null;
/* 156 */   private WSEndpoint wsEndpoint = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityServerTube(ServerTubelineAssemblyContext context, Tube nextTube) {
/* 161 */     super(new ServerTubeConfiguration(context.getPolicyMap(), context.getWsdlPort(), context.getEndpoint()), nextTube);
/* 162 */     this.wsEndpoint = context.getEndpoint();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 168 */       Set<PolicyAssertion> configAssertions = null;
/* 169 */       for (PolicyAlternativeHolder p : this.policyAlternatives) {
/* 170 */         Iterator<SecurityPolicyHolder> it = p.getInMessagePolicyMap().values().iterator();
/* 171 */         while (it.hasNext()) {
/* 172 */           SecurityPolicyHolder holder = it.next();
/* 173 */           if (configAssertions != null) {
/* 174 */             configAssertions.addAll(holder.getConfigAssertions("http://schemas.sun.com/2006/03/wss/server"));
/*     */           } else {
/* 176 */             configAssertions = holder.getConfigAssertions("http://schemas.sun.com/2006/03/wss/server");
/*     */           } 
/* 178 */           if (this.trustConfig != null) {
/* 179 */             this.trustConfig.addAll(holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/trust/server"));
/*     */           } else {
/* 181 */             this.trustConfig = holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/trust/server");
/*     */           } 
/* 183 */           if (this.wsscConfig != null) {
/* 184 */             this.wsscConfig.addAll(holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/sc/server")); continue;
/*     */           } 
/* 186 */           this.wsscConfig = holder.getConfigAssertions("http://schemas.sun.com/ws/2006/05/sc/server");
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 191 */       Properties props = new Properties();
/* 192 */       this.handler = configureServerHandler(configAssertions, props);
/* 193 */       this.secEnv = (SecurityEnvironment)new DefaultSecurityEnvironmentImpl(this.handler, props);
/* 194 */       String cntxtClass = getMetaINFServiceClass("META-INF/services/com.sun.xml.ws.api.server.WebServiceContextDelegate");
/* 195 */       if (cntxtClass != null) {
/* 196 */         this.contextDelegate = loadClass(cntxtClass);
/*     */       }
/* 198 */       boolean isSC = false;
/* 199 */       if (!getSecureConversationPolicies((Message)null, (String)null).isEmpty()) {
/* 200 */         isSC = true;
/*     */       }
/* 202 */       this.sessionManager = SessionManager.getSessionManager(((ServerTubeConfiguration)this.tubeConfig).getEndpoint(), isSC, props);
/* 203 */       props.put("ENDPOINT", context.getEndpoint());
/* 204 */       props.put("POLICY", context.getPolicyMap());
/* 205 */       props.put("WSDL_MODEL", context.getWsdlPort());
/*     */ 
/*     */     
/*     */     }
/* 209 */     catch (Exception e) {
/* 210 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0028_ERROR_CREATING_NEW_INSTANCE_SEC_SERVER_TUBE(), e);
/*     */       
/* 212 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0028_ERROR_CREATING_NEW_INSTANCE_SEC_SERVER_TUBE(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SecurityServerTube(SecurityServerTube that, TubeCloner cloner) {
/* 219 */     super(that, cloner);
/* 220 */     this.sessionManager = that.sessionManager;
/* 221 */     this.trustConfig = that.trustConfig;
/* 222 */     this.wsscConfig = that.wsscConfig;
/* 223 */     this.handler = that.handler;
/* 224 */     this.contextDelegate = that.contextDelegate;
/*     */   }
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 228 */     return (AbstractTubeImpl)new SecurityServerTube(this, cloner);
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
/*     */   public NextAction processRequest(Packet packet) {
/*     */     try {
/* 243 */       HaContext.initFrom(packet);
/*     */       
/* 245 */       Message msg = packet.getInternalMessage();
/* 246 */       this.isSCIssueMessage = false;
/* 247 */       this.isSCCancelMessage = false;
/* 248 */       this.isTrustMessage = false;
/* 249 */       this.tmpPacket = null;
/*     */ 
/*     */       
/* 252 */       boolean thereWasAFault = false;
/*     */ 
/*     */       
/* 255 */       if (this.contextDelegate != null) {
/*     */         try {
/* 257 */           WebServiceContextDelegate current = packet.webServiceContextDelegate;
/* 258 */           Constructor<WebServiceContextDelegate> ctor = this.contextDelegate.getConstructor(new Class[] { WebServiceContextDelegate.class });
/* 259 */           packet.webServiceContextDelegate = ctor.newInstance(new Object[] { current });
/* 260 */         } catch (InstantiationException ex) {
/* 261 */           log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0036_ERROR_INSTATIATE_WEBSERVICE_CONTEXT_DELEGATE(), ex);
/* 262 */           throw new RuntimeException(ex);
/* 263 */         } catch (IllegalAccessException ex) {
/* 264 */           log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0036_ERROR_INSTATIATE_WEBSERVICE_CONTEXT_DELEGATE(), ex);
/* 265 */           throw new RuntimeException(ex);
/* 266 */         } catch (IllegalArgumentException ex) {
/* 267 */           log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0036_ERROR_INSTATIATE_WEBSERVICE_CONTEXT_DELEGATE(), ex);
/* 268 */           throw new RuntimeException(ex);
/* 269 */         } catch (InvocationTargetException ex) {
/* 270 */           log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0036_ERROR_INSTATIATE_WEBSERVICE_CONTEXT_DELEGATE(), ex);
/* 271 */           throw new RuntimeException(ex);
/* 272 */         } catch (NoSuchMethodException ex) {
/* 273 */           log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0036_ERROR_INSTATIATE_WEBSERVICE_CONTEXT_DELEGATE(), ex);
/* 274 */           throw new RuntimeException(ex);
/* 275 */         } catch (SecurityException ex) {
/* 276 */           log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0036_ERROR_INSTATIATE_WEBSERVICE_CONTEXT_DELEGATE(), ex);
/* 277 */           throw new RuntimeException(ex);
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 283 */       ProcessingContext ctx = initializeInboundProcessingContext(packet);
/*     */       
/* 285 */       PolicyResolver pr = PolicyResolverFactory.createPolicyResolver(this.policyAlternatives, this.cachedOperation, this.tubeConfig, this.addVer, false, this.rmVer, this.mcVer);
/*     */       
/* 287 */       ctx.setExtraneousProperty("OperationResolver", pr);
/* 288 */       ctx.setExtraneousProperty("SessionManager", this.sessionManager);
/*     */       try {
/* 290 */         if (!this.optimized) {
/* 291 */           SOAPMessage soapMessage = msg.readAsSOAPMessage();
/* 292 */           soapMessage = verifyInboundMessage(soapMessage, ctx);
/* 293 */           msg = Messages.create(soapMessage);
/*     */         } else {
/* 295 */           msg = verifyInboundMessage(msg, ctx);
/*     */         } 
/* 297 */       } catch (WssSoapFaultException ex) {
/* 298 */         thereWasAFault = true;
/* 299 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0025_ERROR_VERIFY_INBOUND_MSG(), (Throwable)ex);
/* 300 */         SOAPFaultException sfe = SOAPUtil.getSOAPFaultException(ex, this.soapFactory, this.soapVersion);
/* 301 */         if (sfe.getCause() == null) {
/* 302 */           sfe.initCause((Throwable)ex);
/*     */         }
/* 304 */         msg = Messages.create(sfe, this.soapVersion);
/* 305 */       } catch (XWSSecurityException xwse) {
/* 306 */         thereWasAFault = true;
/* 307 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0025_ERROR_VERIFY_INBOUND_MSG(), (Throwable)xwse);
/* 308 */         SOAPFaultException sfe = SOAPUtil.getSOAPFaultException((Exception)xwse, this.soapFactory, this.soapVersion);
/* 309 */         if (sfe.getCause() == null) {
/* 310 */           sfe.initCause((Throwable)xwse);
/*     */         }
/* 312 */         msg = Messages.create(sfe, this.soapVersion);
/*     */       }
/* 314 */       catch (XWSSecurityRuntimeException xwse) {
/* 315 */         thereWasAFault = true;
/* 316 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0025_ERROR_VERIFY_INBOUND_MSG(), (Throwable)xwse);
/* 317 */         SOAPFaultException sfe = SOAPUtil.getSOAPFaultException((Exception)xwse, this.soapFactory, this.soapVersion);
/* 318 */         if (sfe.getCause() == null) {
/* 319 */           sfe.initCause((Throwable)xwse);
/*     */         }
/* 321 */         msg = Messages.create(sfe, this.soapVersion);
/*     */       }
/* 323 */       catch (WebServiceException xwse) {
/* 324 */         thereWasAFault = true;
/* 325 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0025_ERROR_VERIFY_INBOUND_MSG(), xwse);
/* 326 */         SOAPFaultException sfe = SOAPUtil.getSOAPFaultException(xwse, this.soapFactory, this.soapVersion);
/* 327 */         if (sfe.getCause() == null) {
/* 328 */           sfe.initCause(xwse);
/*     */         }
/* 330 */         msg = Messages.create(sfe, this.soapVersion);
/*     */       }
/* 332 */       catch (WSSecureConversationRuntimeException wsre) {
/* 333 */         thereWasAFault = true;
/* 334 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0025_ERROR_VERIFY_INBOUND_MSG(), (Throwable)wsre);
/* 335 */         QName faultCode = wsre.getFaultCode();
/* 336 */         if (faultCode != null) {
/* 337 */           faultCode = new QName(this.wsscVer.getNamespaceURI(), faultCode.getLocalPart());
/*     */         }
/* 339 */         SOAPFaultException sfe = SOAPUtil.getSOAPFaultException(faultCode, wsre, this.soapFactory, this.soapVersion);
/* 340 */         msg = Messages.create(sfe, this.soapVersion);
/* 341 */       } catch (SOAPException se) {
/*     */ 
/*     */         
/* 344 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0025_ERROR_VERIFY_INBOUND_MSG(), se);
/*     */         
/* 346 */         thereWasAFault = true;
/* 347 */         SOAPFaultException sfe = SOAPUtil.getSOAPFaultException(se, this.soapFactory, this.soapVersion);
/* 348 */         if (sfe.getCause() == null) {
/* 349 */           sfe.initCause(se);
/*     */         }
/* 351 */         msg = Messages.create(sfe, this.soapVersion);
/*     */       } 
/*     */       
/* 354 */       Packet retPacket = null;
/* 355 */       if (thereWasAFault)
/*     */       {
/* 357 */         if (isAddressingEnabled()) {
/* 358 */           if (this.optimized) {
/* 359 */             packet.setMessage(((JAXBFilterProcessingContext)ctx).getPVMessage());
/*     */           }
/* 361 */           retPacket = packet.createServerResponse(msg, this.addVer, this.soapVersion, this.addVer.getDefaultFaultAction());
/*     */         } else {
/*     */           
/* 364 */           packet.setMessage(msg);
/* 365 */           retPacket = packet;
/*     */         } 
/*     */       }
/*     */       
/* 369 */       packet.setMessage(msg);
/*     */       
/* 371 */       if (isAddressingEnabled()) {
/* 372 */         this.reqAction = getAction(packet);
/* 373 */         if (this.wsscVer.getSCTRequestAction().equals(this.reqAction) || this.wsscVer.getSCTRenewRequestAction().equals(this.reqAction)) {
/* 374 */           this.isSCIssueMessage = true;
/* 375 */           if (this.wsscConfig != null) {
/* 376 */             packet.invocationProperties.put("http://schemas.sun.com/ws/2006/05/sc/server", this.wsscConfig.iterator());
/*     */           }
/* 378 */         } else if (this.wsscVer.getSCTCancelRequestAction().equals(this.reqAction)) {
/* 379 */           this.isSCCancelMessage = true;
/* 380 */         } else if (this.wsTrustVer.getIssueRequestAction().equals(this.reqAction) || this.wsTrustVer.getValidateRequestAction().equals(this.reqAction)) {
/*     */           
/* 382 */           this.isTrustMessage = true;
/*     */ 
/*     */           
/* 385 */           if (this.trustConfig != null) {
/* 386 */             packet.invocationProperties.put("http://schemas.sun.com/ws/2006/05/trust/server", this.trustConfig.iterator());
/*     */           }
/*     */ 
/*     */           
/* 390 */           packet.invocationProperties.put("SecurityEnvironment", this.secEnv);
/* 391 */           packet.invocationProperties.put("WSTrustVersion", this.wsTrustVer);
/* 392 */           IssuedTokenContext ictx = ((ProcessingContextImpl)ctx).getTrustContext();
/* 393 */           if (ictx != null && ictx.getAuthnContextClass() != null) {
/* 394 */             packet.invocationProperties.put("AuthnContextClass", ictx.getAuthnContextClass());
/*     */           }
/*     */         } 
/*     */         
/* 398 */         if (this.isSCIssueMessage) {
/* 399 */           List<PolicyAssertion> policies = getInBoundSCP(packet.getMessage());
/* 400 */           if (!policies.isEmpty()) {
/* 401 */             packet.invocationProperties.put(Constants.SC_ASSERTION, policies.get(0));
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 406 */       if (!this.isSCIssueMessage) {
/* 407 */         this.cachedOperation = msg.getOperation(this.tubeConfig.getWSDLPort());
/* 408 */         if (this.cachedOperation == null && 
/* 409 */           this.addVer != null) {
/* 410 */           if (thereWasAFault) {
/* 411 */             this.cachedOperation = getWSDLOpFromAction(packet, true, true);
/*     */           } else {
/* 413 */             this.cachedOperation = getWSDLOpFromAction(packet, true);
/*     */           } 
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 421 */       if (!thereWasAFault) {
/*     */         
/* 423 */         if (this.isSCIssueMessage || this.isSCCancelMessage) {
/*     */ 
/*     */           
/* 426 */           retPacket = invokeSecureConversationContract(packet, ctx, this.isSCIssueMessage, this.reqAction);
/*     */           
/* 428 */           this.tmpPacket = packet;
/* 429 */           return processResponse(retPacket);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 435 */         updateSCBootstrapCredentials(packet, ctx);
/* 436 */         this.tmpPacket = packet;
/* 437 */         return doInvoke(this.next, packet);
/*     */       } 
/*     */       
/* 440 */       return processResponse(retPacket);
/*     */     }
/*     */     finally {
/*     */       
/* 444 */       HaContext.clear();
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
/*     */   public NextAction processResponse(Packet retPacket) {
/* 456 */     if (retPacket.getMessage() == null) {
/* 457 */       return doReturnWith(retPacket);
/*     */     }
/*     */ 
/*     */     
/* 461 */     if (!this.optimized) {
/*     */       try {
/* 463 */         SOAPMessage sm = retPacket.getMessage().readAsSOAPMessage();
/* 464 */         Message newMsg = Messages.create(sm);
/* 465 */         retPacket.setMessage(newMsg);
/* 466 */       } catch (SOAPException ex) {
/* 467 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0005_PROBLEM_PROC_SOAP_MESSAGE(), ex);
/*     */         
/* 469 */         return doThrow(new WebServiceException(LogStringsMessages.WSSTUBE_0005_PROBLEM_PROC_SOAP_MESSAGE(), ex));
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 474 */     ProcessingContext ctx = initializeOutgoingProcessingContext(retPacket, this.isSCIssueMessage, this.isTrustMessage);
/* 475 */     ctx.setExtraneousProperty("SessionManager", this.sessionManager);
/* 476 */     Message msg = null;
/*     */     try {
/* 478 */       msg = retPacket.getMessage();
/* 479 */       if (ctx.getSecurityPolicy() != null && ((MessagePolicy)ctx.getSecurityPolicy()).size() > 0) {
/* 480 */         if (!this.optimized) {
/* 481 */           SOAPMessage soapMessage = msg.readAsSOAPMessage();
/* 482 */           soapMessage = secureOutboundMessage(soapMessage, ctx);
/* 483 */           msg = Messages.create(soapMessage);
/*     */         } else {
/* 485 */           msg = secureOutboundMessage(msg, ctx);
/*     */         } 
/*     */       }
/* 488 */     } catch (WssSoapFaultException ex) {
/* 489 */       msg = Messages.create(getSOAPFault(ex));
/* 490 */     } catch (SOAPException se) {
/*     */       
/* 492 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0024_ERROR_SECURING_OUTBOUND_MSG(), se);
/*     */       
/* 494 */       return doThrow(new WebServiceException(LogStringsMessages.WSSTUBE_0024_ERROR_SECURING_OUTBOUND_MSG(), se));
/*     */     } finally {
/* 496 */       if (isSCCancel(retPacket)) {
/* 497 */         removeContext(this.tmpPacket);
/*     */       }
/* 499 */       this.tmpPacket = null;
/*     */     } 
/* 501 */     resetCachedOperation();
/* 502 */     retPacket.setMessage(msg);
/* 503 */     return doReturnWith(retPacket);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processException(Throwable t) {
/* 508 */     if (!(t instanceof WebServiceException)) {
/* 509 */       t = new WebServiceException(t);
/*     */     }
/* 511 */     return doThrow(t);
/*     */   }
/*     */   
/*     */   private void removeContext(Packet packet) {
/* 515 */     SecurityContextToken sct = (SecurityContextToken)packet.invocationProperties.get("Incoming_SCT");
/* 516 */     if (sct != null) {
/* 517 */       String strId = sct.getIdentifier().toString();
/* 518 */       if (strId != null) {
/* 519 */         this.issuedTokenContextMap.remove(strId);
/* 520 */         this.sessionManager.terminateSession(strId);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void preDestroy() {
/* 527 */     if (this.next != null) {
/* 528 */       this.next.preDestroy();
/*     */     }
/* 530 */     this.issuedTokenContextMap.clear();
/* 531 */     SessionManager.removeSessionManager(((ServerTubeConfiguration)this.tubeConfig).getEndpoint());
/* 532 */     NonceManager.deleteInstance(this.wsEndpoint);
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet processMessage(XMLStreamReaderMessage msg) {
/* 537 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStreamMessage processInputStream(XMLStreamReaderMessage msg) {
/* 542 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStreamMessage processInputStream(Message msg) {
/* 547 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected ProcessingContext initializeOutgoingProcessingContext(Packet packet, boolean isSCMessage, boolean isTrustMessage) {
/* 552 */     ProcessingContext ctx = initializeOutgoingProcessingContext(packet, isSCMessage);
/* 553 */     return ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ProcessingContext initializeOutgoingProcessingContext(Packet packet, boolean isSCMessage) {
/*     */     ProcessingContextImpl ctx;
/* 562 */     if (this.optimized) {
/* 563 */       JAXBFilterProcessingContext jAXBFilterProcessingContext = new JAXBFilterProcessingContext(packet.invocationProperties);
/* 564 */       jAXBFilterProcessingContext.setAddressingVersion(this.addVer);
/* 565 */       jAXBFilterProcessingContext.setSOAPVersion(this.soapVersion);
/* 566 */       jAXBFilterProcessingContext.setBSP(this.bsp10);
/*     */     } else {
/* 568 */       ctx = new ProcessingContextImpl(packet.invocationProperties);
/*     */     } 
/* 570 */     if (this.addVer != null) {
/* 571 */       ctx.setAction(getAction(packet));
/*     */     }
/* 573 */     ctx.setSecurityPolicyVersion(this.spVersion.namespaceUri);
/*     */     try {
/* 575 */       MessagePolicy policy = null;
/* 576 */       PolicyAlternativeHolder applicableAlternative = resolveAlternative(packet, isSCMessage);
/*     */ 
/*     */       
/* 579 */       if (packet.getMessage().isFault()) {
/* 580 */         policy = getOutgoingFaultPolicy(packet);
/* 581 */       } else if (isRMMessage(packet) || isMakeConnectionMessage(packet)) {
/* 582 */         SecurityPolicyHolder holder = (SecurityPolicyHolder)applicableAlternative.getOutProtocolPM().get("RM");
/* 583 */         policy = holder.getMessagePolicy();
/* 584 */       } else if (isSCCancel(packet)) {
/* 585 */         SecurityPolicyHolder holder = (SecurityPolicyHolder)applicableAlternative.getOutProtocolPM().get("SC-CANCEL");
/* 586 */         policy = holder.getMessagePolicy();
/*     */       } else {
/* 588 */         policy = getOutgoingXWSSecurityPolicy(packet, isSCMessage);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 596 */       if (policy != null) {
/* 597 */         ctx.setSecurityPolicy((SecurityPolicy)policy);
/*     */       }
/* 599 */       if (isTrustMessage(packet)) {
/* 600 */         ctx.isTrustMessage(true);
/*     */       }
/*     */ 
/*     */       
/* 604 */       if (isSCMessage) {
/* 605 */         ctx.setAlgorithmSuite(policy.getAlgorithmSuite());
/*     */       } else {
/* 607 */         ctx.setAlgorithmSuite(getAlgoSuite(getBindingAlgorithmSuite(packet)));
/*     */       } 
/* 609 */       ctx.setSecurityEnvironment(this.secEnv);
/* 610 */       ctx.isInboundMessage(false);
/* 611 */       ctx.getExtraneousProperties().put("WSDLPort", this.tubeConfig.getWSDLPort());
/* 612 */     } catch (XWSSecurityException e) {
/* 613 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0006_PROBLEM_INIT_OUT_PROC_CONTEXT(), (Throwable)e);
/*     */       
/* 615 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0006_PROBLEM_INIT_OUT_PROC_CONTEXT(), e);
/*     */     } 
/*     */     
/* 618 */     return (ProcessingContext)ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected MessagePolicy getOutgoingXWSSecurityPolicy(Packet packet, boolean isSCMessage) {
/* 624 */     if (isSCMessage) {
/* 625 */       Token scToken = (Token)packet.invocationProperties.get(Constants.SC_ASSERTION);
/* 626 */       return getOutgoingXWSBootstrapPolicy(scToken);
/*     */     } 
/*     */     
/* 629 */     MessagePolicy mp = null;
/* 630 */     PolicyAlternativeHolder applicableAlternative = resolveAlternative(packet, isSCMessage);
/*     */     
/* 632 */     WSDLBoundOperation wsdlOperation = this.cachedOperation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 638 */     if (applicableAlternative.getOutMessagePolicyMap() == null)
/*     */     {
/* 640 */       return new MessagePolicy();
/*     */     }
/*     */     
/* 643 */     if (isTrustMessage(packet) || this.cachedOperation == null) {
/* 644 */       this.cachedOperation = getWSDLOpFromAction(packet, false);
/*     */     }
/*     */     
/* 647 */     SecurityPolicyHolder sph = (SecurityPolicyHolder)applicableAlternative.getOutMessagePolicyMap().get(this.cachedOperation);
/* 648 */     if (sph == null) {
/* 649 */       return new MessagePolicy();
/*     */     }
/* 651 */     mp = sph.getMessagePolicy();
/* 652 */     return mp;
/*     */   }
/*     */   
/*     */   protected MessagePolicy getOutgoingFaultPolicy(Packet packet) {
/* 656 */     PolicyAlternativeHolder applicableAlternative = resolveAlternative(packet, false);
/*     */     
/* 658 */     if (this.cachedOperation != null) {
/* 659 */       WSDLOperation operation = this.cachedOperation.getOperation();
/* 660 */       QName faultDetail = packet.getMessage().getFirstDetailEntryName();
/* 661 */       WSDLFault fault = null;
/* 662 */       if (faultDetail != null) {
/* 663 */         fault = operation.getFault(faultDetail);
/*     */       }
/* 665 */       SecurityPolicyHolder sph = (SecurityPolicyHolder)applicableAlternative.getOutMessagePolicyMap().get(this.cachedOperation);
/* 666 */       if (fault == null) {
/* 667 */         MessagePolicy faultPolicy1 = (sph != null) ? sph.getMessagePolicy() : new MessagePolicy();
/* 668 */         return faultPolicy1;
/*     */       } 
/* 670 */       SecurityPolicyHolder faultPolicyHolder = sph.getFaultPolicy(fault);
/* 671 */       MessagePolicy faultPolicy = (faultPolicyHolder == null) ? new MessagePolicy() : faultPolicyHolder.getMessagePolicy();
/* 672 */       return faultPolicy;
/*     */     } 
/* 674 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SOAPMessage verifyInboundMessage(SOAPMessage message, ProcessingContext ctx) throws WssSoapFaultException, XWSSecurityException {
/* 681 */     ctx.setSOAPMessage(message);
/* 682 */     NewSecurityRecipient.validateMessage(ctx);
/* 683 */     return ctx.getSOAPMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Packet invokeSecureConversationContract(Packet packet, ProcessingContext ctx, boolean isSCTIssue, String action) {
/*     */     Message retMsg;
/*     */     String retAction;
/* 692 */     IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/* 693 */     issuedTokenContextImpl.getOtherProperties().put("SessionManager", this.sessionManager);
/*     */     
/* 695 */     Message msg = packet.getMessage();
/*     */ 
/*     */     
/*     */     try {
/*     */       BaseSTSResponse rstr;
/*     */       
/* 701 */       Subject subject = SubjectAccessor.getRequesterSubject(ctx);
/*     */       
/* 703 */       issuedTokenContextImpl.setRequestorSubject(subject);
/*     */       
/* 705 */       WSTrustElementFactory wsscEleFac = WSTrustElementFactory.newInstance(this.wsscVer);
/*     */       
/* 707 */       JAXBElement rstEle = (JAXBElement)msg.readPayloadAsJAXB(WSTrustElementFactory.getContext(this.wsTrustVer).createUnmarshaller());
/*     */ 
/*     */       
/* 710 */       RequestSecurityToken requestSecurityToken = wsscEleFac.createRSTFrom(rstEle);
/* 711 */       URI requestType = requestSecurityToken.getRequestType();
/*     */       
/* 713 */       WSSCContract scContract = WSSCFactory.newWSSCContract(this.wsscVer);
/* 714 */       scContract.setWSSCServerConfig((Iterator)packet.invocationProperties.get("http://schemas.sun.com/ws/2006/05/sc/server"));
/*     */       
/* 716 */       if (requestType.toString().equals(this.wsTrustVer.getIssueRequestTypeURI())) {
/* 717 */         List<PolicyAssertion> policies = getOutBoundSCP(packet.getMessage());
/* 718 */         rstr = scContract.issue((BaseSTSRequest)requestSecurityToken, (IssuedTokenContext)issuedTokenContextImpl, (SecureConversationToken)policies.get(0));
/* 719 */         retAction = this.wsscVer.getSCTResponseAction();
/* 720 */         SecurityContextToken sct = (SecurityContextToken)issuedTokenContextImpl.getSecurityToken();
/* 721 */         String sctId = sct.getIdentifier().toString();
/*     */         
/* 723 */         Session session = this.sessionManager.getSession(sctId);
/* 724 */         if (session == null) {
/* 725 */           log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0029_ERROR_SESSION_CREATION());
/* 726 */           throw new WSSecureConversationException(LogStringsMessages.WSSTUBE_0029_ERROR_SESSION_CREATION());
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 731 */         packet.invocationProperties.put("com.sun.xml.ws.sessionid", sctId);
/*     */ 
/*     */         
/* 734 */         packet.invocationProperties.put("com.sun.xml.ws.session", session.getUserData());
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 739 */       else if (requestType.toString().equals(this.wsTrustVer.getRenewRequestTypeURI())) {
/* 740 */         List<PolicyAssertion> policies = getOutBoundSCP(packet.getMessage());
/* 741 */         retAction = this.wsscVer.getSCTRenewResponseAction();
/* 742 */         rstr = scContract.renew((BaseSTSRequest)requestSecurityToken, (IssuedTokenContext)issuedTokenContextImpl, (SecureConversationToken)policies.get(0));
/* 743 */       } else if (requestType.toString().equals(this.wsTrustVer.getCancelRequestTypeURI())) {
/* 744 */         retAction = this.wsscVer.getSCTCancelResponseAction();
/* 745 */         rstr = scContract.cancel((BaseSTSRequest)requestSecurityToken, (IssuedTokenContext)issuedTokenContextImpl);
/*     */       } else {
/* 747 */         log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0030_UNSUPPORTED_OPERATION_EXCEPTION(requestType));
/*     */         
/* 749 */         throw new UnsupportedOperationException(LogStringsMessages.WSSTUBE_0030_UNSUPPORTED_OPERATION_EXCEPTION(requestType));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 755 */       retMsg = Messages.create(WSTrustElementFactory.getContext(this.wsTrustVer).createMarshaller(), wsscEleFac.toJAXBElement(rstr), this.soapVersion);
/* 756 */     } catch (XWSSecurityException ex) {
/* 757 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0031_ERROR_INVOKE_SC_CONTRACT(), (Throwable)ex);
/* 758 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0031_ERROR_INVOKE_SC_CONTRACT(), ex);
/* 759 */     } catch (JAXBException ex) {
/* 760 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0001_PROBLEM_MAR_UNMAR(), ex);
/* 761 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0001_PROBLEM_MAR_UNMAR(), ex);
/* 762 */     } catch (WSSecureConversationException ex) {
/* 763 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0031_ERROR_INVOKE_SC_CONTRACT(), (Throwable)ex);
/* 764 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0031_ERROR_INVOKE_SC_CONTRACT(), ex);
/*     */     } 
/*     */ 
/*     */     
/* 768 */     Packet retPacket = addAddressingHeaders(packet, retMsg, retAction);
/* 769 */     if (isSCTIssue) {
/* 770 */       List<PolicyAssertion> policies = getOutBoundSCP(packet.getMessage());
/*     */       
/* 772 */       if (!policies.isEmpty()) {
/* 773 */         retPacket.invocationProperties.put(Constants.SC_ASSERTION, policies.get(0));
/*     */       }
/*     */     } 
/*     */     
/* 777 */     return retPacket;
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStreamMessage processInputStream(Packet packet) {
/* 782 */     throw new UnsupportedOperationException("Will be supported for optimized path");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SecurityPolicyHolder addOutgoingMP(WSDLBoundOperation operation, Policy policy, PolicyAlternativeHolder ph) throws PolicyException {
/* 813 */     SecurityPolicyHolder sph = constructPolicyHolder(policy, true, true);
/* 814 */     ph.getInMessagePolicyMap().put(operation, sph);
/* 815 */     return sph;
/*     */   }
/*     */   
/*     */   protected SecurityPolicyHolder addIncomingMP(WSDLBoundOperation operation, Policy policy, PolicyAlternativeHolder ph) throws PolicyException {
/* 819 */     SecurityPolicyHolder sph = constructPolicyHolder(policy, true, false);
/* 820 */     ph.getOutMessagePolicyMap().put(operation, sph);
/* 821 */     return sph;
/*     */   }
/*     */   
/*     */   protected void addIncomingProtocolPolicy(Policy effectivePolicy, String protocol, PolicyAlternativeHolder ph) throws PolicyException {
/* 825 */     ph.getOutProtocolPM().put(protocol, constructPolicyHolder(effectivePolicy, true, false, true));
/*     */   }
/*     */   
/*     */   protected void addOutgoingProtocolPolicy(Policy effectivePolicy, String protocol, PolicyAlternativeHolder ph) throws PolicyException {
/* 829 */     ph.getInProtocolPM().put(protocol, constructPolicyHolder(effectivePolicy, true, true, false));
/*     */   }
/*     */   
/*     */   protected void addIncomingFaultPolicy(Policy effectivePolicy, SecurityPolicyHolder sph, WSDLFault fault) throws PolicyException {
/* 833 */     SecurityPolicyHolder faultPH = constructPolicyHolder(effectivePolicy, true, false);
/* 834 */     sph.addFaultPolicy(fault, faultPH);
/*     */   }
/*     */   
/*     */   protected void addOutgoingFaultPolicy(Policy effectivePolicy, SecurityPolicyHolder sph, WSDLFault fault) throws PolicyException {
/* 838 */     SecurityPolicyHolder faultPH = constructPolicyHolder(effectivePolicy, true, true);
/* 839 */     sph.addFaultPolicy(fault, faultPH);
/*     */   }
/*     */   
/*     */   protected String getAction(WSDLOperation operation, boolean inComming) {
/* 843 */     if (inComming) {
/* 844 */       return operation.getInput().getAction();
/*     */     }
/* 846 */     return operation.getOutput().getAction();
/*     */   }
/*     */ 
/*     */   
/*     */   private Packet addAddressingHeaders(Packet packet, Message retMsg, String action) {
/* 851 */     Packet retPacket = packet.createServerResponse(retMsg, this.addVer, this.soapVersion, action);
/*     */     
/* 853 */     retPacket.proxy = packet.proxy;
/* 854 */     retPacket.invocationProperties.putAll(packet.invocationProperties);
/*     */     
/* 856 */     return retPacket;
/*     */   }
/*     */ 
/*     */   
/*     */   private CallbackHandler configureServerHandler(Set<PolicyAssertion> configAssertions, Properties props) {
/* 861 */     CallbackHandlerFeature cbFeature = (CallbackHandlerFeature)this.tubeConfig.getBinding().getFeature(CallbackHandlerFeature.class);
/*     */     
/* 863 */     if (cbFeature != null) {
/* 864 */       return cbFeature.getHandler();
/*     */     }
/* 866 */     String ret = populateConfigProperties(configAssertions, props);
/*     */     try {
/* 868 */       if (ret != null) {
/* 869 */         Object obj = loadClass(ret).newInstance();
/* 870 */         if (!(obj instanceof CallbackHandler)) {
/* 871 */           log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0033_INVALID_CALLBACK_HANDLER_CLASS(ret));
/*     */           
/* 873 */           throw new RuntimeException(LogStringsMessages.WSSTUBE_0033_INVALID_CALLBACK_HANDLER_CLASS(ret));
/*     */         } 
/*     */         
/* 876 */         return (CallbackHandler)obj;
/*     */       } 
/*     */ 
/*     */       
/* 880 */       RealmAuthenticationAdapter adapter = getRealmAuthenticationAdapter(((ServerTubeConfiguration)this.tubeConfig).getEndpoint());
/* 881 */       return (CallbackHandler)new DefaultCallbackHandler("server", props, adapter);
/*     */     }
/* 883 */     catch (Exception e) {
/* 884 */       log.log(Level.SEVERE, LogStringsMessages.WSSTUBE_0032_ERROR_CONFIGURE_SERVER_HANDLER(), e);
/*     */       
/* 886 */       throw new RuntimeException(LogStringsMessages.WSSTUBE_0032_ERROR_CONFIGURE_SERVER_HANDLER(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private RealmAuthenticationAdapter getRealmAuthenticationAdapter(WSEndpoint wSEndpoint) {
/* 892 */     String className = "javax.servlet.ServletContext";
/* 893 */     Class<?> ret = null;
/* 894 */     ClassLoader loader = Thread.currentThread().getContextClassLoader();
/* 895 */     if (loader != null) {
/*     */       try {
/* 897 */         ret = loader.loadClass(className);
/* 898 */       } catch (ClassNotFoundException e) {
/* 899 */         return null;
/*     */       } 
/*     */     }
/* 902 */     if (ret == null) {
/*     */       
/* 904 */       loader = getClass().getClassLoader();
/*     */       try {
/* 906 */         ret = loader.loadClass(className);
/* 907 */       } catch (ClassNotFoundException e) {
/* 908 */         return null;
/*     */       } 
/*     */     } 
/* 911 */     if (ret != null) {
/* 912 */       Object obj = wSEndpoint.getContainer().getSPI(ret);
/* 913 */       if (obj != null) {
/* 914 */         return RealmAuthenticationAdapter.newInstance(obj);
/*     */       }
/*     */     } 
/* 917 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateSCBootstrapCredentials(Packet packet, ProcessingContext ctx) {
/* 924 */     SecurityContextToken sct = (SecurityContextToken)packet.invocationProperties.get("Incoming_SCT");
/*     */     
/* 926 */     if (sct != null) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 932 */       String sessionId = sct.getIdentifier().toString();
/*     */ 
/*     */       
/* 935 */       packet.invocationProperties.put("com.sun.xml.ws.sessionid", sessionId);
/* 936 */       packet.invocationProperties.put("com.sun.xml.ws.session", this.sessionManager.getSession(sessionId).getUserData());
/*     */ 
/*     */       
/* 939 */       IssuedTokenContext itctx = this.sessionManager.getSecurityContext(sessionId, true);
/* 940 */       if (itctx != null) {
/* 941 */         Subject from = itctx.getRequestorSubject();
/* 942 */         Subject to = DefaultSecurityEnvironmentImpl.getSubject(packet.invocationProperties);
/* 943 */         copySubject(from, to);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void copySubject(final Subject from, final Subject to) {
/* 950 */     if (from == null || to == null) {
/*     */       return;
/*     */     }
/* 953 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Object run() {
/* 956 */             to.getPrincipals().addAll(from.getPrincipals());
/* 957 */             to.getPublicCredentials().addAll(from.getPublicCredentials());
/* 958 */             to.getPrivateCredentials().addAll(from.getPrivateCredentials());
/* 959 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static String getMetaINFServiceClass(String metaInfService) {
/* 965 */     URL url = loadFromClasspath(metaInfService);
/* 966 */     if (url != null) {
/* 967 */       InputStream is = null;
/*     */       try {
/* 969 */         is = url.openStream();
/* 970 */         ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 971 */         int val = is.read();
/* 972 */         while (val != -1) {
/* 973 */           os.write(val);
/* 974 */           val = is.read();
/*     */         } 
/* 976 */         String classname = os.toString();
/* 977 */         return classname;
/*     */       }
/* 979 */       catch (IOException ex) {
/* 980 */         log.log(Level.SEVERE, (String)null, ex);
/* 981 */         throw new WebServiceException(ex);
/*     */       } finally {
/*     */         try {
/* 984 */           is.close();
/* 985 */         } catch (IOException ex) {
/* 986 */           log.log(Level.WARNING, (String)null, ex);
/*     */         } 
/*     */       } 
/*     */     } 
/* 990 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static URL loadFromClasspath(String configFileName) {
/* 995 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/* 996 */     if (cl == null) {
/* 997 */       return ClassLoader.getSystemResource(configFileName);
/*     */     }
/* 999 */     return cl.getResource(configFileName);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\jaxws\impl\SecurityServerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */