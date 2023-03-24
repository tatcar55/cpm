/*     */ package com.sun.xml.xwss;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.ProcessingContextImpl;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.SecurityAnnotator;
/*     */ import com.sun.xml.wss.impl.SecurityRecipient;
/*     */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*     */ import com.sun.xml.wss.impl.config.ApplicationSecurityConfiguration;
/*     */ import com.sun.xml.wss.impl.config.DeclarativeSecurityConfiguration;
/*     */ import com.sun.xml.wss.impl.configuration.StaticApplicationContext;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.StaticPolicyContext;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.jar.Attributes;
/*     */ import javax.servlet.ServletContext;
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
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XWSSServerTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*     */   private WSEndpoint endPoint;
/*     */   private WSDLPort port;
/*  99 */   private SecurityConfiguration config = null;
/*     */   
/* 101 */   protected SOAPFactory soapFactory = null;
/* 102 */   protected MessageFactory messageFactory = null;
/* 103 */   protected SOAPVersion soapVersion = null;
/*     */   
/*     */   protected boolean isSOAP12 = false;
/*     */   
/*     */   protected static final String FAILURE = "com.sun.xml.ws.shd.failure";
/*     */   
/*     */   protected static final String TRUE = "true";
/*     */   
/*     */   protected static final String FALSE = "false";
/*     */   protected static final String CONTEXT_WSDL_OPERATION = "com.sun.xml.ws.wsdl.operation";
/*     */   private static final String SERVLET_CONTEXT_CLASSNAME = "javax.servlet.ServletContext";
/*     */   private static final String ENCRYPTED_BODY_QNAME = "{http://www.w3.org/2001/04/xmlenc#}EncryptedData";
/*     */   
/*     */   public XWSSServerTube(WSEndpoint epoint, WSDLPort prt, Tube nextTube) {
/* 117 */     super(nextTube);
/* 118 */     this.endPoint = epoint;
/* 119 */     this.port = prt;
/*     */     try {
/* 121 */       this.config = new SecurityConfiguration(getServerConfig());
/* 122 */     } catch (XWSSecurityException ex) {
/* 123 */       throw new WebServiceException(ex);
/*     */     } 
/* 125 */     this.soapVersion = this.endPoint.getBinding().getSOAPVersion();
/* 126 */     this.isSOAP12 = (this.soapVersion == SOAPVersion.SOAP_12);
/* 127 */     this.soapFactory = this.soapVersion.saajSoapFactory;
/* 128 */     this.messageFactory = this.soapVersion.saajMessageFactory;
/*     */   }
/*     */ 
/*     */   
/*     */   public XWSSServerTube(XWSSServerTube that, TubeCloner cloner) {
/* 133 */     super(that, cloner);
/* 134 */     this.endPoint = that.endPoint;
/* 135 */     this.port = that.port;
/*     */     
/* 137 */     this.soapFactory = that.soapFactory;
/* 138 */     this.messageFactory = that.messageFactory;
/* 139 */     this.soapVersion = that.soapVersion;
/* 140 */     this.isSOAP12 = that.isSOAP12;
/* 141 */     this.config = that.config;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void preDestroy() {}
/*     */ 
/*     */   
/*     */   private InputStream getServerConfigStream() {
/* 150 */     QName serviceQName = this.endPoint.getServiceName();
/* 151 */     String serviceName = serviceQName.getLocalPart();
/*     */     
/* 153 */     String serverConfig = "/WEB-INF/" + serviceName + "_" + "security_config.xml";
/* 154 */     ServletContext context = (ServletContext)this.endPoint.getContainer().getSPI(ServletContext.class);
/* 155 */     if (context == null) {
/* 156 */       return null;
/*     */     }
/* 158 */     InputStream in = context.getResourceAsStream(serverConfig);
/*     */     
/* 160 */     if (in == null) {
/* 161 */       serverConfig = "/WEB-INF/server_security_config.xml";
/* 162 */       in = context.getResourceAsStream(serverConfig);
/*     */     } 
/*     */     
/* 165 */     return in;
/*     */   }
/*     */   
/*     */   private URL getServerConfig() {
/* 169 */     QName serviceQName = this.endPoint.getServiceName();
/* 170 */     String serviceName = serviceQName.getLocalPart();
/*     */ 
/*     */     
/* 173 */     Container container = this.endPoint.getContainer();
/*     */     
/* 175 */     Object ctxt = null;
/* 176 */     if (container != null) {
/*     */       try {
/* 178 */         Class<?> contextClass = Class.forName("javax.servlet.ServletContext");
/* 179 */         ctxt = container.getSPI(contextClass);
/* 180 */       } catch (ClassNotFoundException e) {}
/*     */     }
/*     */ 
/*     */     
/* 184 */     String serverName = "server";
/* 185 */     URL url = null;
/* 186 */     if (ctxt != null) {
/* 187 */       String serverConfig = "/WEB-INF/" + serverName + "_" + "security_config.xml";
/* 188 */       url = SecurityUtil.loadFromContext(serverConfig, ctxt);
/* 189 */       if (url == null) {
/* 190 */         serverConfig = "/WEB-INF/" + serviceName + "_" + "security_config.xml";
/* 191 */         url = SecurityUtil.loadFromContext(serverConfig, ctxt);
/*     */       } 
/*     */       
/* 194 */       if (url != null) {
/* 195 */         return url;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 200 */       String serverConfig = "META-INF/" + serverName + "_" + "security_config.xml";
/* 201 */       url = SecurityUtil.loadFromClasspath(serverConfig);
/* 202 */       if (url == null) {
/* 203 */         serverConfig = "META-INF/" + serviceName + "_" + "security_config.xml";
/* 204 */         url = SecurityUtil.loadFromClasspath(serverConfig);
/*     */       } 
/*     */       
/* 207 */       if (url != null) {
/* 208 */         return url;
/*     */       }
/*     */     } 
/* 211 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet validateRequest(Packet packet) throws Exception {
/* 222 */     if (this.config == null) {
/* 223 */       return packet;
/*     */     }
/*     */     
/* 226 */     ProcessingContext context = null;
/* 227 */     SOAPMessage message = packet.getMessage().readAsSOAPMessage();
/*     */     
/*     */     try {
/* 230 */       StaticApplicationContext sContext = new StaticApplicationContext(getPolicyContext(packet));
/*     */ 
/*     */       
/* 233 */       ProcessingContextImpl processingContextImpl = new ProcessingContextImpl(packet.invocationProperties);
/*     */       
/* 235 */       processingContextImpl.setSOAPMessage(message);
/*     */       
/* 237 */       String operation = getOperationName(message);
/*     */       
/* 239 */       ApplicationSecurityConfiguration _sConfig = this.config.getSecurityConfiguration();
/*     */ 
/*     */       
/* 242 */       if (operation.equals("{http://www.w3.org/2001/04/xmlenc#}EncryptedData") && _sConfig.hasOperationPolicies()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 249 */         ApplicationSecurityConfiguration appconfig = _sConfig.getSecurityPolicies((StaticPolicyContext)sContext).next();
/*     */ 
/*     */ 
/*     */         
/* 253 */         if (appconfig != null) {
/* 254 */           processingContextImpl.setPolicyContext((StaticPolicyContext)sContext);
/* 255 */           processingContextImpl.setSecurityPolicy((SecurityPolicy)appconfig);
/*     */         } else {
/* 257 */           ApplicationSecurityConfiguration config0 = _sConfig.getAllTopLevelApplicationSecurityConfigurations().iterator().next();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 263 */           processingContextImpl.setPolicyContext((StaticPolicyContext)sContext);
/* 264 */           processingContextImpl.setSecurityPolicy((SecurityPolicy)config0);
/*     */         } 
/*     */       } else {
/* 267 */         sContext.setOperationIdentifier(operation);
/* 268 */         packet.invocationProperties.put("com.sun.xml.ws.wsdl.operation", operation);
/* 269 */         SecurityPolicy policy = _sConfig.getSecurityConfiguration(sContext);
/*     */ 
/*     */         
/* 272 */         processingContextImpl.setPolicyContext((StaticPolicyContext)sContext);
/*     */         
/* 274 */         if (PolicyTypeUtil.declarativeSecurityConfiguration(policy)) {
/* 275 */           processingContextImpl.setSecurityPolicy((SecurityPolicy)((DeclarativeSecurityConfiguration)policy).receiverSettings());
/*     */         }
/*     */         else {
/*     */           
/* 279 */           processingContextImpl.setSecurityPolicy(policy);
/*     */         } 
/*     */       } 
/*     */       
/* 283 */       processingContextImpl.setSecurityEnvironment(this.config.getSecurityEnvironment());
/* 284 */       processingContextImpl.isInboundMessage(true);
/*     */       
/* 286 */       if (_sConfig.retainSecurityHeader()) {
/* 287 */         processingContextImpl.retainSecurityHeader(true);
/*     */       }
/*     */       
/* 290 */       if (_sConfig.resetMustUnderstand()) {
/* 291 */         processingContextImpl.resetMustUnderstand(true);
/*     */       }
/*     */       
/* 294 */       SecurityRecipient.validateMessage((ProcessingContext)processingContextImpl);
/* 295 */       String operationName = getOperationName(message);
/*     */       
/* 297 */       packet.invocationProperties.put("com.sun.xml.ws.wsdl.operation", operationName);
/* 298 */       packet.setMessage(Messages.create(processingContextImpl.getSOAPMessage()));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 303 */       return packet;
/*     */     }
/* 305 */     catch (WssSoapFaultException soapFaultException) {
/*     */       
/* 307 */       packet.invocationProperties.put("com.sun.xml.ws.shd.failure", "true");
/* 308 */       addFault(soapFaultException, message, this.isSOAP12);
/* 309 */       packet.setMessage(Messages.create(message));
/* 310 */       return packet;
/*     */     }
/* 312 */     catch (XWSSecurityException xwse) {
/* 313 */       QName qname = null;
/*     */       
/* 315 */       if (xwse.getCause() instanceof com.sun.xml.wss.impl.PolicyViolationException) {
/* 316 */         qname = MessageConstants.WSSE_RECEIVER_POLICY_VIOLATION;
/*     */       } else {
/* 318 */         qname = MessageConstants.WSSE_FAILED_AUTHENTICATION;
/*     */       } 
/* 320 */       WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(qname, xwse.getMessage(), (Throwable)xwse);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 325 */       packet.invocationProperties.put("com.sun.xml.ws.shd.failure", "true");
/* 326 */       addFault(wsfe, message, this.isSOAP12);
/* 327 */       packet.setMessage(Messages.create(message));
/*     */       
/* 329 */       return packet;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet secureResponse(Packet packet) throws Exception {
/* 338 */     if (this.config == null) {
/* 339 */       return packet;
/*     */     }
/*     */     try {
/* 342 */       ProcessingContextImpl processingContextImpl = new ProcessingContextImpl(packet.invocationProperties);
/*     */       
/* 344 */       String operation = (String)packet.invocationProperties.get("com.sun.xml.ws.wsdl.operation");
/*     */       
/* 346 */       StaticApplicationContext sContext = new StaticApplicationContext(getPolicyContext(packet));
/*     */       
/* 348 */       sContext.setOperationIdentifier(operation);
/*     */       
/* 350 */       ApplicationSecurityConfiguration _sConfig = this.config.getSecurityConfiguration();
/*     */ 
/*     */       
/* 353 */       SecurityPolicy policy = _sConfig.getSecurityConfiguration(sContext);
/* 354 */       processingContextImpl.setPolicyContext((StaticPolicyContext)sContext);
/*     */       
/* 356 */       if (PolicyTypeUtil.declarativeSecurityConfiguration(policy)) {
/* 357 */         processingContextImpl.setSecurityPolicy((SecurityPolicy)((DeclarativeSecurityConfiguration)policy).senderSettings());
/*     */       } else {
/* 359 */         processingContextImpl.setSecurityPolicy(policy);
/*     */       } 
/*     */       
/* 362 */       processingContextImpl.setSecurityEnvironment(this.config.getSecurityEnvironment());
/* 363 */       processingContextImpl.isInboundMessage(false);
/* 364 */       processingContextImpl.setSOAPMessage(packet.getMessage().readAsSOAPMessage());
/* 365 */       SecurityAnnotator.secureMessage((ProcessingContext)processingContextImpl);
/* 366 */       packet.setMessage(Messages.create(processingContextImpl.getSOAPMessage()));
/* 367 */       return packet;
/* 368 */     } catch (WssSoapFaultException soapFaultException) {
/* 369 */       Message msg = Messages.create(getSOAPFault(soapFaultException));
/* 370 */       packet.setMessage(msg);
/* 371 */       return packet;
/* 372 */     } catch (XWSSecurityException xwse) {
/*     */       
/* 374 */       WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INTERNAL_SERVER_ERROR, xwse.getMessage(), (Throwable)xwse);
/*     */ 
/*     */ 
/*     */       
/* 378 */       Message msg = Messages.create(getSOAPFault(wsfe));
/* 379 */       packet.setMessage(msg);
/* 380 */       return packet;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private StaticApplicationContext getPolicyContext(Packet packet) {
/* 386 */     ApplicationSecurityConfiguration appconfig = this.config.getSecurityConfiguration();
/*     */ 
/*     */     
/* 389 */     StaticApplicationContext iContext = appconfig.getAllContexts().next();
/*     */     
/* 391 */     StaticApplicationContext sContext = new StaticApplicationContext(iContext);
/*     */ 
/*     */     
/* 394 */     QName portQname = null;
/* 395 */     if (this.port != null) {
/* 396 */       portQname = this.port.getName();
/*     */     }
/* 398 */     String prt = null;
/*     */     
/* 400 */     if (portQname == null) {
/* 401 */       prt = "";
/*     */     } else {
/* 403 */       prt = portQname.toString();
/*     */     } 
/*     */     
/* 406 */     sContext.setPortIdentifier(prt);
/* 407 */     return sContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFault(WssSoapFaultException sfe, SOAPMessage soapMessage, boolean isSOAP12) throws SOAPException {
/* 413 */     SOAPBody body = soapMessage.getSOAPBody();
/* 414 */     body.removeContents();
/* 415 */     soapMessage.removeAllAttachments();
/* 416 */     QName faultCode = sfe.getFaultCode();
/* 417 */     Attributes.Name faultCodeName = null;
/*     */     
/* 419 */     if (faultCode == null) {
/* 420 */       faultCode = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Client");
/*     */     }
/* 422 */     if (isSOAP12) {
/* 423 */       SOAPFault fault = body.addFault(SOAPConstants.SOAP_SENDER_FAULT, sfe.getMessage());
/* 424 */       fault.appendFaultSubcode(faultCode);
/*     */     } else {
/* 426 */       body.addFault(faultCode, sfe.getMessage());
/*     */     } 
/* 428 */     NodeList list = soapMessage.getSOAPPart().getEnvelope().getElementsByTagNameNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");
/* 429 */     if (list.getLength() > 0) {
/* 430 */       Node node = list.item(0);
/* 431 */       node.getParentNode().removeChild(node);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SOAPFault getSOAPFault(WssSoapFaultException sfe) {
/* 437 */     SOAPFault fault = null;
/*     */     try {
/* 439 */       if (this.isSOAP12) {
/* 440 */         fault = this.soapFactory.createFault(sfe.getFaultString(), SOAPConstants.SOAP_SENDER_FAULT);
/* 441 */         fault.appendFaultSubcode(sfe.getFaultCode());
/*     */       } else {
/* 443 */         fault = this.soapFactory.createFault(sfe.getFaultString(), sfe.getFaultCode());
/*     */       } 
/* 445 */     } catch (Exception e) {
/* 446 */       throw new RuntimeException("Security Pipe: Internal Error while trying to create a SOAPFault");
/*     */     } 
/* 448 */     return fault;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPFaultException getSOAPFaultException(WssSoapFaultException sfe, boolean isSOAP12) {
/* 454 */     SOAPFault fault = null;
/*     */     try {
/* 456 */       if (isSOAP12) {
/* 457 */         fault = this.soapFactory.createFault(sfe.getFaultString(), SOAPConstants.SOAP_SENDER_FAULT);
/*     */         
/* 459 */         fault.appendFaultSubcode(sfe.getFaultCode());
/*     */       } else {
/* 461 */         fault = this.soapFactory.createFault(sfe.getFaultString(), sfe.getFaultCode());
/*     */       } 
/* 463 */     } catch (Exception e) {
/* 464 */       throw new RuntimeException(this + ": Internal Error while trying to create a SOAPFault");
/*     */     } 
/* 466 */     return new SOAPFaultException(fault);
/*     */   }
/*     */ 
/*     */   
/*     */   private String getOperationName(SOAPMessage message) throws Exception {
/* 471 */     Node node = null;
/* 472 */     String key = null;
/* 473 */     SOAPBody body = null;
/*     */     
/* 475 */     if (message != null) {
/* 476 */       body = message.getSOAPBody();
/*     */     } else {
/* 478 */       throw new XWSSecurityException("SOAPMessage in message context is null");
/*     */     } 
/*     */     
/* 481 */     if (body != null) {
/* 482 */       node = body.getFirstChild();
/*     */     } else {
/* 484 */       throw new XWSSecurityException("No body element identifying an operation is found");
/*     */     } 
/*     */     
/* 487 */     StringBuffer tmp = new StringBuffer("");
/* 488 */     String operation = "";
/*     */     
/* 490 */     for (; node != null; node = node.getNextSibling())
/* 491 */       tmp.append("{" + node.getNamespaceURI() + "}" + node.getLocalName() + ":"); 
/* 492 */     operation = tmp.toString();
/* 493 */     if (operation.length() > 0) {
/* 494 */       return operation.substring(0, operation.length() - 1);
/*     */     }
/* 496 */     return operation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 502 */     return (AbstractTubeImpl)new XWSSServerTube(this, cloner);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet packet) {
/*     */     try {
/* 508 */       Packet ret = validateRequest(packet);
/* 509 */       if ("true".equals(ret.invocationProperties.get("com.sun.xml.ws.shd.failure"))) {
/* 510 */         return doReturnWith(ret);
/*     */       }
/* 512 */       return doInvoke(this.next, ret);
/* 513 */     } catch (Throwable t) {
/* 514 */       if (!(t instanceof WebServiceException)) {
/* 515 */         t = new WebServiceException(t);
/*     */       }
/* 517 */       return doThrow(t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet ret) {
/*     */     try {
/* 525 */       if (ret.getMessage() == null) {
/* 526 */         return doReturnWith(ret);
/*     */       }
/* 528 */       Packet response = secureResponse(ret);
/* 529 */       return doReturnWith(response);
/* 530 */     } catch (Throwable t) {
/* 531 */       if (!(t instanceof WebServiceException)) {
/* 532 */         t = new WebServiceException(t);
/*     */       }
/* 534 */       return doThrow(t);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\xwss\XWSSServerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */