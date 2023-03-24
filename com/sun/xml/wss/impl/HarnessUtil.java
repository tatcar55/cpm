/*     */ package com.sun.xml.wss.impl;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.SecurityEnvironment;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.callback.DynamicPolicyCallback;
/*     */ import com.sun.xml.wss.impl.filter.AuthenticationTokenFilter;
/*     */ import com.sun.xml.wss.impl.filter.EncryptionFilter;
/*     */ import com.sun.xml.wss.impl.filter.SignatureConfirmationFilter;
/*     */ import com.sun.xml.wss.impl.filter.SignatureFilter;
/*     */ import com.sun.xml.wss.impl.filter.TimestampFilter;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.StaticPolicyContext;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.security.auth.callback.UnsupportedCallbackException;
/*     */ import javax.xml.soap.SOAPBody;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class HarnessUtil
/*     */ {
/*  84 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void processWSSPolicy(FilterProcessingContext fpContext) throws XWSSecurityException {
/*  97 */     WSSPolicy policy = (WSSPolicy)fpContext.getSecurityPolicy();
/*  98 */     if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)policy))
/*  99 */     { SignatureFilter.process(fpContext); }
/* 100 */     else if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)policy))
/* 101 */     { EncryptionFilter.process(fpContext); }
/* 102 */     else if (PolicyTypeUtil.timestampPolicy((SecurityPolicy)policy))
/* 103 */     { TimestampFilter.process(fpContext); }
/* 104 */     else if (PolicyTypeUtil.signatureConfirmationPolicy((SecurityPolicy)policy))
/* 105 */     { SignatureConfirmationFilter.process(fpContext); }
/*     */     
/* 107 */     else if (PolicyTypeUtil.authenticationTokenPolicy((SecurityPolicy)policy))
/* 108 */     { fpContext.getExtraneousProperties().put("token.policy", policy);
/* 109 */       WSSPolicy authPolicy = (WSSPolicy)policy.getFeatureBinding();
/* 110 */       if (PolicyTypeUtil.usernameTokenPolicy((SecurityPolicy)authPolicy)) {
/* 111 */         AuthenticationTokenPolicy.UsernameTokenBinding utb = (AuthenticationTokenPolicy.UsernameTokenBinding)authPolicy;
/*     */         
/* 113 */         if (!utb.isEndorsing()) {
/* 114 */           AuthenticationTokenFilter.processUserNameToken(fpContext);
/*     */         }
/* 116 */       } else if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)authPolicy)) {
/* 117 */         AuthenticationTokenPolicy.SAMLAssertionBinding samlPolicy = (AuthenticationTokenPolicy.SAMLAssertionBinding)authPolicy;
/*     */         
/*     */         try {
/* 120 */           if (samlPolicy.getAssertionType() == "SV")
/*     */           {
/* 122 */             AuthenticationTokenFilter.processSamlToken(fpContext);
/*     */           }
/* 124 */         } catch (Exception ex) {
/* 125 */           log.log(Level.WARNING, ex.getMessage());
/*     */         } 
/* 127 */       } else if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)authPolicy)) {
/* 128 */         AuthenticationTokenFilter.processX509Token(fpContext);
/* 129 */       } else if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)authPolicy)) {
/* 130 */         AuthenticationTokenFilter.processIssuedToken(fpContext);
/* 131 */       } else if (PolicyTypeUtil.keyValueTokenBinding((SecurityPolicy)authPolicy)) {
/* 132 */         AuthenticationTokenFilter.processRSAToken(fpContext);
/*     */       }  }
/* 134 */     else { if (PolicyTypeUtil.isMandatoryTargetPolicy((SecurityPolicy)policy)) {
/*     */         return;
/*     */       }
/* 137 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0801_ILLEGAL_SECURITYPOLICY());
/* 138 */       throw new XWSSecurityException("Invalid WSSPolicy Type"); }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void processDeep(FilterProcessingContext fpContext) throws XWSSecurityException {
/* 277 */     if (fpContext.getSecurityPolicy() instanceof WSSPolicy) {
/* 278 */       processWSSPolicy(fpContext);
/*     */     } else {
/* 280 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0801_ILLEGAL_SECURITYPOLICY());
/* 281 */       throw new XWSSecurityException("Invalid SecurityPolicy Type");
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
/*     */   static void validateContext(ProcessingContext context) throws XWSSecurityException {
/* 294 */     SOAPMessage message = null;
/* 295 */     Message jaxWsMessage = null;
/* 296 */     if (context instanceof JAXBFilterProcessingContext) {
/* 297 */       jaxWsMessage = ((JAXBFilterProcessingContext)context).getJAXWSMessage();
/*     */     } else {
/* 299 */       message = context.getSOAPMessage();
/* 300 */     }  SecurityEnvironment handler = context.getSecurityEnvironment();
/* 301 */     SecurityPolicy policy = context.getSecurityPolicy();
/* 302 */     boolean isInboundMessage = context.isInboundMessage();
/* 303 */     StaticPolicyContext staticContext = context.getPolicyContext();
/*     */     
/* 305 */     if (message == null && jaxWsMessage == null) {
/* 306 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0803_SOAPMESSAGE_NOTSET());
/* 307 */       throw new XWSSecurityException("javax.xml.soap.SOAPMessage parameter not set in the ProcessingContext");
/*     */     } 
/*     */ 
/*     */     
/* 311 */     if (handler == null) {
/* 312 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0804_CALLBACK_HANDLER_NOTSET());
/* 313 */       throw new XWSSecurityException("SecurityEnvironment/javax.security.auth.callback.CallbackHandler implementation not set in the ProcessingContext");
/*     */     } 
/*     */ 
/*     */     
/* 317 */     if (policy == null && !isInboundMessage && 
/* 318 */       log.isLoggable(Level.WARNING)) {
/* 319 */       log.log(Level.WARNING, LogStringsMessages.WSS_0805_POLICY_NULL());
/*     */     }
/*     */ 
/*     */     
/* 323 */     if (staticContext == null);
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
/*     */   static String resolvePolicyIdentifier(SOAPMessage message) throws XWSSecurityException {
/* 335 */     Node firstChild = null;
/* 336 */     SOAPBody body = null;
/*     */     
/*     */     try {
/* 339 */       body = message.getSOAPBody();
/* 340 */     } catch (SOAPException se) {
/* 341 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0807_NO_BODY_ELEMENT(), se);
/* 342 */       throw new XWSSecurityException(se);
/*     */     } 
/*     */     
/* 345 */     if (body != null) {
/* 346 */       firstChild = body.getFirstChild();
/*     */     } else {
/* 348 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0808_NO_BODY_ELEMENT_OPERATION());
/* 349 */       throw new XWSSecurityException("No body element identifying an operation is found");
/*     */     } 
/*     */ 
/*     */     
/* 353 */     String id = (firstChild != null) ? ("{" + firstChild.getNamespaceURI() + "}" + firstChild.getLocalName()) : null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     return id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isSecondaryHeaderElement(SOAPElement element) {
/* 367 */     if (element.getLocalName().equals("EncryptedKey")) {
/* 368 */       NodeList nl = element.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "ReferenceList");
/* 369 */       if (nl.getLength() == 0) {
/* 370 */         return true;
/*     */       }
/*     */     } 
/* 373 */     return (element.getLocalName().equals("BinarySecurityToken") || element.getLocalName().equals("UsernameToken") || element.getLocalName().equals("Assertion") || element.getLocalName().equals("Timestamp") || element.getLocalName().equals("SignatureConfirmation") || element.getLocalName().equals("SecurityTokenReference") || element.getLocalName().equals("DerivedKeyToken") || element.getLocalName().equals("SecurityContextToken"));
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
/*     */   static SOAPElement getNextElement(SOAPElement current) {
/* 387 */     if (current == null) {
/* 388 */       return null;
/*     */     }
/* 390 */     Node node = current.getNextSibling();
/* 391 */     while (null != node && node.getNodeType() != 1)
/* 392 */       node = node.getNextSibling(); 
/* 393 */     return (SOAPElement)node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static SOAPElement getPreviousElement(SOAPElement current) {
/* 400 */     Node node = current.getPreviousSibling();
/* 401 */     while (node != null && node.getNodeType() != 1)
/* 402 */       node = node.getPreviousSibling(); 
/* 403 */     return (SOAPElement)node;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void throwWssSoapFault(String message) throws WssSoapFaultException {
/* 412 */     XWSSecurityException xwsse = new XWSSecurityException(message);
/* 413 */     log.log(Level.SEVERE, LogStringsMessages.WSS_0809_FAULT_WSSSOAP(), (Throwable)xwsse);
/* 414 */     throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, message, xwsse);
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
/*     */   public static void makeDynamicPolicyCallback(DynamicPolicyCallback callback, CallbackHandler callbackHandler) throws XWSSecurityException {
/* 428 */     if (callbackHandler == null) {
/*     */       return;
/*     */     }
/*     */     try {
/* 432 */       Callback[] callbacks = { (Callback)callback };
/* 433 */       callbackHandler.handle(callbacks);
/* 434 */     } catch (UnsupportedCallbackException ex) {
/*     */     
/* 436 */     } catch (Exception e) {
/* 437 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0237_FAILED_DYNAMIC_POLICY_CALLBACK(), e);
/* 438 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\HarnessUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */