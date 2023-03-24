/*     */ package com.sun.xml.rpc.security;
/*     */ 
/*     */ import com.sun.xml.rpc.client.StreamingSenderState;
/*     */ import com.sun.xml.rpc.server.StreamingHandlerState;
/*     */ import com.sun.xml.rpc.soap.message.SOAPMessageContext;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.SecurityEnvironment;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.SecurityAnnotator;
/*     */ import com.sun.xml.wss.impl.SecurityRecipient;
/*     */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*     */ import com.sun.xml.wss.impl.config.ApplicationSecurityConfiguration;
/*     */ import com.sun.xml.wss.impl.config.DeclarativeSecurityConfiguration;
/*     */ import com.sun.xml.wss.impl.config.SecurityConfigurationXmlReader;
/*     */ import com.sun.xml.wss.impl.configuration.StaticApplicationContext;
/*     */ import com.sun.xml.wss.impl.filter.DumpFilter;
/*     */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.StaticPolicyContext;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.util.Iterator;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.JAXRPCException;
/*     */ import javax.xml.rpc.soap.SOAPFaultException;
/*     */ import javax.xml.soap.SOAPBody;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPEnvelope;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.soap.SOAPPart;
/*     */ import org.w3c.dom.Attr;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityPluginUtil
/*     */ {
/*  98 */   String port = null;
/*     */   
/* 100 */   private CallbackHandler _callbackHandler = null;
/* 101 */   private SecurityEnvironment _securityEnvironment = null;
/*     */   
/* 103 */   private ApplicationSecurityConfiguration _sConfig = null;
/*     */   
/*     */   private static final String CONTEXT_OPERATION = "context.operation.name";
/*     */   private static final String ENCRYPTED_BODY_QNAME = "{http://www.w3.org/2001/04/xmlenc#}EncryptedData";
/*     */   
/*     */   public SecurityPluginUtil(String config, String port, boolean isStub) throws Exception {
/* 109 */     if (config != null) {
/*     */       
/* 111 */       int versionStart = config.indexOf('[');
/* 112 */       if (versionStart != -1) {
/* 113 */         int versionEnd = config.indexOf(']');
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 118 */         config = config.substring(versionEnd + 1);
/*     */       } 
/*     */       
/* 121 */       this.port = port;
/*     */       
/* 123 */       this._sConfig = SecurityConfigurationXmlReader.createApplicationSecurityConfiguration(new ByteArrayInputStream(config.getBytes()));
/*     */ 
/*     */       
/* 126 */       this._callbackHandler = (CallbackHandler)Class.forName(this._sConfig.getSecurityEnvironmentHandler(), true, Thread.currentThread().getContextClassLoader()).newInstance();
/*     */       
/* 128 */       this._securityEnvironment = (SecurityEnvironment)new DefaultSecurityEnvironmentImpl(this._callbackHandler);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void copyToMessageContext(SOAPMessageContext messageContext, ProcessingContext context) throws Exception {
/* 135 */     messageContext.setMessage(context.getSOAPMessage());
/*     */     
/* 137 */     Iterator<String> i = context.getExtraneousProperties().keySet().iterator();
/* 138 */     while (i.hasNext()) {
/* 139 */       String name = i.next();
/* 140 */       Object value = context.getExtraneousProperties().get(name);
/* 141 */       messageContext.setProperty(name, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void copyToProcessingContext(ProcessingContext context, SOAPMessageContext messageContext) throws Exception {
/* 147 */     context.setSOAPMessage(messageContext.getMessage());
/*     */     
/* 149 */     Iterator<String> i = messageContext.getPropertyNames();
/* 150 */     while (i.hasNext()) {
/* 151 */       String name = i.next();
/* 152 */       Object value = messageContext.getProperty(name);
/*     */       
/* 154 */       context.setExtraneousProperty(name, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private StaticApplicationContext getPolicyContext() {
/* 160 */     ApplicationSecurityConfiguration config = this._sConfig.getAllTopLevelApplicationSecurityConfigurations().iterator().next();
/*     */ 
/*     */ 
/*     */     
/* 164 */     StaticApplicationContext iContext = config.getAllContexts().next();
/*     */     
/* 166 */     StaticApplicationContext sContext = new StaticApplicationContext(iContext);
/* 167 */     sContext.setPortIdentifier(this.port);
/*     */     
/* 169 */     return sContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public void _preHandlingHook(StreamingSenderState state) throws Exception {
/*     */     try {
/* 175 */       SOAPMessageContext messageContext = state.getMessageContext();
/* 176 */       SOAPMessage message = state.getResponse().getMessage();
/*     */       
/* 178 */       String operation = (String)messageContext.getProperty("context.operation.name");
/*     */       
/* 180 */       StaticApplicationContext sContext = getPolicyContext();
/* 181 */       sContext.setOperationIdentifier(operation);
/*     */       
/* 183 */       SecurityPolicy policy = this._sConfig.getSecurityConfiguration(sContext);
/*     */       
/* 185 */       ProcessingContext context = new ProcessingContext();
/* 186 */       copyToProcessingContext(context, messageContext);
/*     */       
/* 188 */       context.setPolicyContext((StaticPolicyContext)sContext);
/*     */       
/* 190 */       if (PolicyTypeUtil.declarativeSecurityConfiguration(policy)) {
/* 191 */         context.setSecurityPolicy((SecurityPolicy)((DeclarativeSecurityConfiguration)policy).receiverSettings());
/*     */       } else {
/*     */         
/* 194 */         context.setSecurityPolicy(policy);
/*     */       } 
/*     */       
/* 197 */       context.setSecurityEnvironment(this._securityEnvironment);
/* 198 */       context.isInboundMessage(true);
/*     */       
/* 200 */       if (this._sConfig.retainSecurityHeader()) {
/* 201 */         context.retainSecurityHeader(true);
/*     */       }
/*     */       
/* 204 */       if (this._sConfig.resetMustUnderstand()) {
/* 205 */         context.resetMustUnderstand(true);
/*     */       }
/*     */       
/* 208 */       SecurityRecipient.validateMessage(context);
/*     */       
/* 210 */       copyToMessageContext(messageContext, context);
/*     */     }
/* 212 */     catch (WssSoapFaultException soapFaultException) {
/* 213 */       throw getSOAPFaultException(soapFaultException);
/* 214 */     } catch (XWSSecurityException xwse) {
/* 215 */       QName qname = null;
/*     */       
/* 217 */       if (xwse.getCause() instanceof com.sun.xml.wss.impl.PolicyViolationException) {
/* 218 */         qname = MessageConstants.WSSE_RECEIVER_POLICY_VIOLATION;
/*     */       } else {
/* 220 */         qname = MessageConstants.WSSE_FAILED_AUTHENTICATION;
/*     */       } 
/* 222 */       WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(qname, xwse.getMessage(), (Throwable)xwse);
/*     */ 
/*     */ 
/*     */       
/* 226 */       throw getSOAPFaultException(wsfe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean _preRequestSendingHook(StreamingSenderState state) throws Exception {
/*     */     try {
/* 233 */       SOAPMessageContext messageContext = state.getMessageContext();
/* 234 */       SOAPMessage message = state.getRequest().getMessage();
/*     */       
/* 236 */       String operation = getOperationName(message);
/* 237 */       messageContext.setProperty("context.operation.name", operation);
/*     */       
/* 239 */       StaticApplicationContext sContext = getPolicyContext();
/* 240 */       sContext.setOperationIdentifier(operation);
/*     */       
/* 242 */       SecurityPolicy policy = this._sConfig.getSecurityConfiguration(sContext);
/*     */       
/* 244 */       ProcessingContext context = new ProcessingContext();
/* 245 */       copyToProcessingContext(context, messageContext);
/*     */       
/* 247 */       context.setPolicyContext((StaticPolicyContext)sContext);
/*     */       
/* 249 */       if (PolicyTypeUtil.declarativeSecurityConfiguration(policy)) {
/* 250 */         context.setSecurityPolicy((SecurityPolicy)((DeclarativeSecurityConfiguration)policy).senderSettings());
/*     */       } else {
/*     */         
/* 253 */         context.setSecurityPolicy(policy);
/*     */       } 
/*     */       
/* 256 */       context.setSecurityEnvironment(this._securityEnvironment);
/* 257 */       context.isInboundMessage(false);
/*     */       
/* 259 */       SecurityAnnotator.secureMessage(context);
/*     */       
/* 261 */       copyToMessageContext(messageContext, context);
/* 262 */     } catch (WssSoapFaultException soapFaultException) {
/* 263 */       throw getSOAPFaultException(soapFaultException);
/* 264 */     } catch (XWSSecurityException xwse) {
/*     */       
/* 266 */       throw new JAXRPCException(xwse);
/*     */     } 
/*     */     
/* 269 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean preHandlingHook(StreamingHandlerState state) throws Exception {
/*     */     try {
/* 278 */       SOAPMessageContext messageContext = state.getMessageContext();
/* 279 */       SOAPMessage message = state.getRequest().getMessage();
/*     */       
/* 281 */       StaticApplicationContext sContext = new StaticApplicationContext(getPolicyContext());
/* 282 */       ProcessingContext context = new ProcessingContext();
/*     */       
/* 284 */       copyToProcessingContext(context, messageContext);
/* 285 */       String operation = getOperationName(message);
/*     */       
/* 287 */       if (operation.equals("{http://www.w3.org/2001/04/xmlenc#}EncryptedData") && this._sConfig.hasOperationPolicies()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 293 */         ApplicationSecurityConfiguration config = this._sConfig.getSecurityPolicies((StaticPolicyContext)sContext).next();
/*     */ 
/*     */ 
/*     */         
/* 297 */         if (config != null) {
/* 298 */           context.setPolicyContext((StaticPolicyContext)sContext);
/* 299 */           context.setSecurityPolicy((SecurityPolicy)config);
/*     */         } else {
/* 301 */           ApplicationSecurityConfiguration config0 = this._sConfig.getAllTopLevelApplicationSecurityConfigurations().iterator().next();
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 306 */           context.setPolicyContext((StaticPolicyContext)sContext);
/* 307 */           context.setSecurityPolicy((SecurityPolicy)config0);
/*     */         } 
/*     */       } else {
/* 310 */         sContext.setOperationIdentifier(operation);
/* 311 */         messageContext.setProperty("context.operation.name", operation);
/* 312 */         SecurityPolicy policy = this._sConfig.getSecurityConfiguration(sContext);
/*     */         
/* 314 */         context.setPolicyContext((StaticPolicyContext)sContext);
/*     */         
/* 316 */         if (PolicyTypeUtil.declarativeSecurityConfiguration(policy)) {
/* 317 */           context.setSecurityPolicy((SecurityPolicy)((DeclarativeSecurityConfiguration)policy).receiverSettings());
/*     */         } else {
/*     */           
/* 320 */           context.setSecurityPolicy(policy);
/*     */         } 
/*     */       } 
/*     */       
/* 324 */       context.setSecurityEnvironment(this._securityEnvironment);
/* 325 */       context.isInboundMessage(true);
/*     */       
/* 327 */       if (this._sConfig.retainSecurityHeader()) {
/* 328 */         context.retainSecurityHeader(true);
/*     */       }
/*     */       
/* 331 */       SecurityRecipient.validateMessage(context);
/*     */       
/* 333 */       messageContext.setProperty("context.operation.name", getOperationName(message));
/*     */       
/* 335 */       copyToMessageContext(messageContext, context);
/* 336 */     } catch (WssSoapFaultException soapFaultException) {
/* 337 */       state.getResponse().setFailure(true);
/* 338 */       throw getSOAPFaultException(soapFaultException);
/*     */     }
/* 340 */     catch (XWSSecurityException xwse) {
/* 341 */       QName qname = null;
/*     */       
/* 343 */       if (xwse.getCause() instanceof com.sun.xml.wss.impl.PolicyViolationException) {
/* 344 */         qname = MessageConstants.WSSE_RECEIVER_POLICY_VIOLATION;
/*     */       } else {
/* 346 */         qname = MessageConstants.WSSE_FAILED_AUTHENTICATION;
/*     */       } 
/* 348 */       WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(qname, xwse.getMessage(), (Throwable)xwse);
/*     */ 
/*     */ 
/*     */       
/* 352 */       state.getResponse().setFailure(true);
/*     */       
/* 354 */       throw getSOAPFaultException(wsfe);
/*     */     } 
/*     */     
/* 357 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postResponseWritingHook(StreamingHandlerState state) throws Exception {
/*     */     try {
/* 363 */       SOAPMessageContext messageContext = state.getMessageContext();
/* 364 */       SOAPMessage message = state.getResponse().getMessage();
/*     */       
/* 366 */       ProcessingContext context = new ProcessingContext();
/*     */       
/* 368 */       copyToProcessingContext(context, messageContext);
/*     */       
/* 370 */       if (state.getResponse().isFailure()) {
/* 371 */         DumpFilter.process(context);
/*     */         
/*     */         return;
/*     */       } 
/* 375 */       String operation = (String)messageContext.getProperty("context.operation.name");
/*     */       
/* 377 */       StaticApplicationContext sContext = new StaticApplicationContext(getPolicyContext());
/* 378 */       sContext.setOperationIdentifier(operation);
/*     */       
/* 380 */       SecurityPolicy policy = this._sConfig.getSecurityConfiguration(sContext);
/* 381 */       context.setPolicyContext((StaticPolicyContext)sContext);
/*     */       
/* 383 */       if (PolicyTypeUtil.declarativeSecurityConfiguration(policy)) {
/* 384 */         context.setSecurityPolicy((SecurityPolicy)((DeclarativeSecurityConfiguration)policy).senderSettings());
/*     */       } else {
/*     */         
/* 387 */         context.setSecurityPolicy(policy);
/*     */       } 
/*     */       
/* 390 */       context.setSecurityEnvironment(this._securityEnvironment);
/* 391 */       context.isInboundMessage(false);
/*     */       
/* 393 */       SecurityAnnotator.secureMessage(context);
/*     */       
/* 395 */       copyToMessageContext(messageContext, context);
/* 396 */     } catch (WssSoapFaultException soapFaultException) {
/* 397 */       throw getSOAPFaultException(soapFaultException);
/* 398 */     } catch (XWSSecurityException xwse) {
/*     */       
/* 400 */       WssSoapFaultException wsfe = SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INTERNAL_SERVER_ERROR, xwse.getMessage(), (Throwable)xwse);
/*     */ 
/*     */ 
/*     */       
/* 404 */       throw getSOAPFaultException(wsfe);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMessageForMUCheck(SOAPMessage message) throws Exception {
/* 410 */     setMUValue(message, "0");
/*     */   }
/*     */ 
/*     */   
/*     */   public void restoreMessageAfterMUCheck(SOAPMessage message) throws Exception {
/* 415 */     setMUValue(message, "1");
/*     */   }
/*     */ 
/*     */   
/*     */   private void setMUValue(SOAPMessage message, String value) throws Exception {
/* 420 */     SOAPPart sp = message.getSOAPPart();
/* 421 */     SOAPEnvelope se = sp.getEnvelope();
/* 422 */     SOAPHeader sh = se.getHeader();
/*     */     
/* 424 */     if (sh != null) {
/*     */       
/* 426 */       SOAPElement secHeader = null;
/* 427 */       Node currentChild = sh.getFirstChild();
/*     */       
/* 429 */       while (currentChild != null && currentChild.getNodeType() != 1)
/*     */       {
/* 431 */         currentChild = currentChild.getNextSibling();
/*     */       }
/*     */       
/* 434 */       if (currentChild != null && 
/* 435 */         "Security".equals(currentChild.getLocalName()) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(currentChild.getNamespaceURI()))
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 440 */         secHeader = (SOAPElement)currentChild;
/*     */       }
/*     */ 
/*     */       
/* 444 */       if (secHeader != null) {
/*     */         
/* 446 */         Attr attr = secHeader.getAttributeNodeNS("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand");
/*     */ 
/*     */ 
/*     */         
/* 450 */         if (attr != null) {
/* 451 */           secHeader.setAttributeNS(attr.getNamespaceURI(), attr.getName(), value);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public SOAPFaultException getSOAPFaultException(WssSoapFaultException sfe) {
/* 458 */     return new SOAPFaultException(sfe.getFaultCode(), sfe.getFaultString(), sfe.getFaultActor(), sfe.getDetail());
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
/*     */   private String getOperationName(SOAPMessage message) throws Exception {
/* 474 */     Node node = null;
/* 475 */     String key = null;
/* 476 */     SOAPBody body = null;
/*     */     
/* 478 */     if (message != null) {
/* 479 */       body = message.getSOAPBody();
/*     */     } else {
/* 481 */       throw new XWSSecurityException("SOAPMessage in message context is null");
/*     */     } 
/*     */     
/* 484 */     if (body != null) {
/* 485 */       node = body.getFirstChild();
/*     */     } else {
/* 487 */       throw new XWSSecurityException("No body element identifying an operation is found");
/*     */     } 
/*     */     
/* 490 */     StringBuffer tmp = new StringBuffer("");
/* 491 */     String operation = "";
/*     */     
/* 493 */     for (; node != null; node = node.getNextSibling())
/* 494 */       tmp.append("{" + node.getNamespaceURI() + "}" + node.getLocalName() + ":"); 
/* 495 */     operation = tmp.toString();
/*     */     
/* 497 */     return operation.substring(0, operation.length() - 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\security\SecurityPluginUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */