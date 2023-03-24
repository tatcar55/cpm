/*     */ package com.sun.xml.xwss;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.ProcessingContextImpl;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.SecurityAnnotator;
/*     */ import com.sun.xml.wss.impl.SecurityRecipient;
/*     */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.impl.config.ApplicationSecurityConfiguration;
/*     */ import com.sun.xml.wss.impl.config.DeclarativeSecurityConfiguration;
/*     */ import com.sun.xml.wss.impl.configuration.StaticApplicationContext;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.StaticPolicyContext;
/*     */ import java.net.URL;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.MessageFactory;
/*     */ import javax.xml.soap.SOAPBody;
/*     */ import javax.xml.soap.SOAPConstants;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.soap.SOAPFaultException;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XWSSClientTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*  92 */   protected WSDLPort port = null;
/*  93 */   protected WSService service = null;
/*  94 */   protected WSBinding binding = null;
/*  95 */   protected SOAPFactory soapFactory = null;
/*  96 */   protected MessageFactory messageFactory = null;
/*  97 */   protected SOAPVersion soapVersion = null;
/*     */   
/*     */   protected boolean isSOAP12 = false;
/*     */   
/*     */   private static final String MESSAGE_SECURITY_CONFIGURATION = "com.sun.xml.ws.security.configuration";
/*     */   
/*     */   private static final String CONTEXT_WSDL_OPERATION = "com.sun.xml.ws.wsdl.operation";
/*     */   
/*     */   protected boolean wasConfigChecked = false;
/*     */   
/*     */   protected SecurityConfiguration sConfig;
/*     */ 
/*     */   
/*     */   public XWSSClientTube(WSDLPort prt, WSService svc, WSBinding bnd, Tube nextTube) {
/* 111 */     super(nextTube);
/* 112 */     this.port = prt;
/* 113 */     this.service = svc;
/* 114 */     this.binding = bnd;
/*     */     
/* 116 */     this.soapVersion = bnd.getSOAPVersion();
/* 117 */     this.isSOAP12 = (this.soapVersion == SOAPVersion.SOAP_12);
/* 118 */     this.soapFactory = this.soapVersion.saajSoapFactory;
/* 119 */     this.messageFactory = this.soapVersion.saajMessageFactory;
/*     */   }
/*     */ 
/*     */   
/*     */   public XWSSClientTube(XWSSClientTube that, TubeCloner cloner) {
/* 124 */     super(that, cloner);
/* 125 */     this.binding = that.binding;
/* 126 */     this.port = that.port;
/* 127 */     this.service = that.service;
/* 128 */     this.soapFactory = that.soapFactory;
/* 129 */     this.messageFactory = that.messageFactory;
/* 130 */     this.soapVersion = that.soapVersion;
/* 131 */     this.isSOAP12 = that.isSOAP12;
/* 132 */     this.sConfig = that.sConfig;
/* 133 */     this.wasConfigChecked = that.wasConfigChecked;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 139 */     return (AbstractTubeImpl)new XWSSClientTube(this, cloner);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet packet) {
/*     */     try {
/* 145 */       this.sConfig = (SecurityConfiguration)packet.invocationProperties.get("com.sun.xml.ws.security.configuration");
/*     */       
/* 147 */       if (this.sConfig == null) {
/*     */ 
/*     */         
/* 150 */         URL url = null;
/* 151 */         if (!this.wasConfigChecked) {
/* 152 */           this.wasConfigChecked = true;
/* 153 */           String configUrl = "META-INF/client_security_config.xml";
/* 154 */           url = SecurityUtil.loadFromClasspath(configUrl);
/*     */         } 
/* 156 */         if (url != null) {
/*     */           try {
/* 158 */             this.sConfig = new SecurityConfiguration(url);
/* 159 */             packet.invocationProperties.put("com.sun.xml.ws.security.configuration", this.sConfig);
/* 160 */           } catch (XWSSecurityException e) {
/* 161 */             throw new XWSSecurityRuntimeException(e);
/*     */           } 
/*     */         } else {
/* 164 */           return doInvoke(this.next, packet);
/*     */         } 
/*     */       } 
/*     */       
/* 168 */       Packet ret = secureRequest(packet);
/* 169 */       return doInvoke(this.next, ret);
/* 170 */     } catch (Throwable t) {
/* 171 */       if (!(t instanceof WebServiceException)) {
/* 172 */         t = new WebServiceException(t);
/*     */       }
/* 174 */       return doThrow(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet ret) {
/*     */     try {
/* 181 */       if (ret == null || ret.getMessage() == null) {
/* 182 */         return doReturnWith(ret);
/*     */       }
/* 184 */       Packet response = validateResponse(ret);
/* 185 */       return doReturnWith(response);
/* 186 */     } catch (Throwable t) {
/* 187 */       if (!(t instanceof WebServiceException)) {
/* 188 */         t = new WebServiceException(t);
/*     */       }
/* 190 */       return doThrow(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void preDestroy() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet validateResponse(Packet packet) {
/*     */     try {
/* 202 */       SecurityConfiguration sConfig = (SecurityConfiguration)packet.invocationProperties.get("com.sun.xml.ws.security.configuration");
/*     */       
/* 204 */       if (sConfig == null) {
/* 205 */         return packet;
/*     */       }
/* 207 */       SOAPMessage message = null;
/*     */       try {
/* 209 */         message = packet.getMessage().readAsSOAPMessage();
/* 210 */       } catch (SOAPException ex) {
/* 211 */         throw new WebServiceException(ex);
/*     */       } 
/*     */       
/* 214 */       String operation = (String)packet.invocationProperties.get("com.sun.xml.ws.wsdl.operation");
/*     */       
/* 216 */       StaticApplicationContext sContext = getPolicyContext(packet, sConfig);
/*     */       
/* 218 */       sContext.setOperationIdentifier(operation);
/*     */       
/* 220 */       ApplicationSecurityConfiguration config = sConfig.getSecurityConfiguration();
/*     */ 
/*     */       
/* 223 */       SecurityPolicy policy = config.getSecurityConfiguration(sContext);
/*     */       
/* 225 */       ProcessingContextImpl processingContextImpl = new ProcessingContextImpl(packet.invocationProperties);
/* 226 */       processingContextImpl.setPolicyContext((StaticPolicyContext)sContext);
/* 227 */       processingContextImpl.setSOAPMessage(message);
/*     */       
/* 229 */       if (PolicyTypeUtil.declarativeSecurityConfiguration(policy)) {
/* 230 */         processingContextImpl.setSecurityPolicy((SecurityPolicy)((DeclarativeSecurityConfiguration)policy).receiverSettings());
/*     */       } else {
/*     */         
/* 233 */         processingContextImpl.setSecurityPolicy(policy);
/*     */       } 
/*     */       
/* 236 */       processingContextImpl.setSecurityEnvironment(sConfig.getSecurityEnvironment());
/* 237 */       processingContextImpl.isInboundMessage(true);
/*     */       
/* 239 */       if (config.retainSecurityHeader()) {
/* 240 */         processingContextImpl.retainSecurityHeader(true);
/*     */       }
/*     */       
/* 243 */       if (config.resetMustUnderstand()) {
/* 244 */         processingContextImpl.resetMustUnderstand(true);
/*     */       }
/*     */       
/* 247 */       SecurityRecipient.validateMessage((ProcessingContext)processingContextImpl);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 253 */       packet.setMessage(Messages.create(processingContextImpl.getSOAPMessage()));
/* 254 */       return packet;
/*     */     }
/* 256 */     catch (WssSoapFaultException soapFaultException) {
/* 257 */       throw getSOAPFaultException(soapFaultException, this.isSOAP12);
/* 258 */     } catch (XWSSecurityException xwse) {
/* 259 */       QName qname = null;
/*     */       
/* 261 */       if (xwse.getCause() instanceof com.sun.xml.wss.impl.PolicyViolationException) {
/* 262 */         qname = MessageConstants.WSSE_RECEIVER_POLICY_VIOLATION;
/*     */       } else {
/* 264 */         qname = MessageConstants.WSSE_FAILED_AUTHENTICATION;
/*     */       } 
/* 266 */       WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(qname, xwse.getMessage(), (Throwable)xwse);
/*     */ 
/*     */ 
/*     */       
/* 270 */       throw getSOAPFaultException(wsfe, this.isSOAP12);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Packet secureRequest(Packet packet) {
/* 276 */     ProcessingContext context = null;
/* 277 */     SOAPMessage message = null;
/*     */     try {
/* 279 */       message = packet.getMessage().readAsSOAPMessage();
/* 280 */     } catch (SOAPException ex) {
/* 281 */       throw new WebServiceException(ex);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 286 */       SecurityConfiguration sConfig = (SecurityConfiguration)packet.invocationProperties.get("com.sun.xml.ws.security.configuration");
/*     */ 
/*     */       
/* 289 */       if (sConfig == null) {
/* 290 */         return packet;
/*     */       }
/*     */       
/* 293 */       WSDLBoundOperation op = null;
/* 294 */       if (this.port != null) {
/* 295 */         op = packet.getMessage().getOperation(this.port);
/*     */       }
/*     */       
/* 298 */       QName operationQName = null;
/* 299 */       if (op != null) {
/* 300 */         operationQName = op.getName();
/*     */       }
/*     */       
/* 303 */       String operation = null;
/*     */       try {
/* 305 */         if (operationQName == null) {
/* 306 */           operation = getOperationName(message);
/*     */         } else {
/* 308 */           operation = operationQName.toString();
/*     */         } 
/* 310 */       } catch (XWSSecurityException e) {
/* 311 */         throw new WebServiceException(e);
/*     */       } 
/*     */       
/* 314 */       packet.invocationProperties.put("com.sun.xml.ws.wsdl.operation", operation);
/*     */       
/* 316 */       StaticApplicationContext sContext = getPolicyContext(packet, sConfig);
/*     */       
/* 318 */       sContext.setOperationIdentifier(operation);
/*     */       
/* 320 */       ApplicationSecurityConfiguration config = sConfig.getSecurityConfiguration();
/*     */ 
/*     */       
/* 323 */       SecurityPolicy policy = config.getSecurityConfiguration(sContext);
/*     */       
/* 325 */       ProcessingContextImpl processingContextImpl = new ProcessingContextImpl(packet.invocationProperties);
/*     */       
/* 327 */       processingContextImpl.setPolicyContext((StaticPolicyContext)sContext);
/*     */       
/* 329 */       if (PolicyTypeUtil.declarativeSecurityConfiguration(policy)) {
/* 330 */         processingContextImpl.setSecurityPolicy((SecurityPolicy)((DeclarativeSecurityConfiguration)policy).senderSettings());
/*     */       } else {
/*     */         
/* 333 */         processingContextImpl.setSecurityPolicy(policy);
/*     */       } 
/*     */       
/* 336 */       processingContextImpl.setSecurityEnvironment(sConfig.getSecurityEnvironment());
/* 337 */       processingContextImpl.isInboundMessage(false);
/* 338 */       processingContextImpl.setSOAPMessage(message);
/* 339 */       SecurityAnnotator.secureMessage((ProcessingContext)processingContextImpl);
/* 340 */       packet.setMessage(Messages.create(processingContextImpl.getSOAPMessage()));
/* 341 */       return packet;
/*     */     }
/* 343 */     catch (WssSoapFaultException soapFaultException) {
/* 344 */       throw new WebServiceException(soapFaultException);
/* 345 */     } catch (XWSSecurityException xwse) {
/*     */       
/* 347 */       throw new WebServiceException(xwse);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getOperationName(SOAPMessage message) throws XWSSecurityException {
/* 353 */     Node node = null;
/* 354 */     String key = null;
/* 355 */     SOAPBody body = null;
/*     */     
/* 357 */     if (message != null) {
/*     */       try {
/* 359 */         body = message.getSOAPBody();
/* 360 */       } catch (SOAPException ex) {
/* 361 */         throw new XWSSecurityException(ex);
/*     */       } 
/*     */     } else {
/* 364 */       throw new XWSSecurityException("SOAPMessage in message context is null");
/*     */     } 
/*     */ 
/*     */     
/* 368 */     if (body != null) {
/* 369 */       node = body.getFirstChild();
/*     */     } else {
/* 371 */       throw new XWSSecurityException("No body element identifying an operation is found");
/*     */     } 
/*     */     
/* 374 */     StringBuffer tmp = new StringBuffer("");
/* 375 */     String operation = "";
/*     */     
/* 377 */     for (; node != null; node = node.getNextSibling()) {
/* 378 */       tmp.append("{" + node.getNamespaceURI() + "}" + node.getLocalName() + ":");
/*     */     }
/* 380 */     operation = tmp.toString();
/* 381 */     if (operation.length() > 0) {
/* 382 */       return operation.substring(0, operation.length() - 1);
/*     */     }
/* 384 */     return operation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPFaultException getSOAPFaultException(WssSoapFaultException sfe, boolean isSOAP12) {
/* 391 */     SOAPFault fault = null;
/*     */     try {
/* 393 */       if (isSOAP12) {
/* 394 */         fault = this.soapFactory.createFault(sfe.getFaultString(), SOAPConstants.SOAP_SENDER_FAULT);
/*     */         
/* 396 */         fault.appendFaultSubcode(sfe.getFaultCode());
/*     */       } else {
/* 398 */         fault = this.soapFactory.createFault(sfe.getFaultString(), sfe.getFaultCode());
/*     */       } 
/* 400 */     } catch (Exception e) {
/* 401 */       throw new RuntimeException(this + ": Internal Error while trying to create a SOAPFault");
/*     */     } 
/* 403 */     return new SOAPFaultException(fault);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private StaticApplicationContext getPolicyContext(Packet packet, SecurityConfiguration config) {
/* 409 */     ApplicationSecurityConfiguration appconfig = config.getSecurityConfiguration();
/*     */ 
/*     */     
/* 412 */     StaticApplicationContext iContext = appconfig.getAllContexts().next();
/*     */     
/* 414 */     StaticApplicationContext sContext = new StaticApplicationContext(iContext);
/*     */ 
/*     */     
/* 417 */     QName portQname = null;
/* 418 */     if (this.port != null) {
/* 419 */       portQname = this.port.getName();
/*     */     }
/* 421 */     String prt = null;
/*     */     
/* 423 */     if (portQname == null) {
/* 424 */       prt = "";
/*     */     } else {
/* 426 */       prt = portQname.toString();
/*     */     } 
/*     */     
/* 429 */     sContext.setPortIdentifier(prt);
/* 430 */     return sContext;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\xwss\XWSSClientTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */