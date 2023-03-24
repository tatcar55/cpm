/*     */ package com.sun.xml.wss.impl;
/*     */ 
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.SecurityHeader;
/*     */ import com.sun.xml.wss.impl.filter.AuthenticationTokenFilter;
/*     */ import com.sun.xml.wss.impl.filter.DumpFilter;
/*     */ import com.sun.xml.wss.impl.filter.EncryptionFilter;
/*     */ import com.sun.xml.wss.impl.filter.SignatureConfirmationFilter;
/*     */ import com.sun.xml.wss.impl.filter.SignatureFilter;
/*     */ import com.sun.xml.wss.impl.filter.TimestampFilter;
/*     */ import com.sun.xml.wss.impl.policy.PolicyAlternatives;
/*     */ import com.sun.xml.wss.impl.policy.PolicyUtils;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import com.sun.xml.wss.impl.policy.verifier.MessagePolicyVerifier;
/*     */ import com.sun.xml.wss.impl.policy.verifier.TargetResolver;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NewSecurityRecipient
/*     */ {
/*  80 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*  84 */   private static SOAPFactory sFactory = null;
/*     */   
/*     */   static {
/*     */     try {
/*  88 */       sFactory = SOAPFactory.newInstance();
/*  89 */     } catch (Exception ex) {
/*  90 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0397_SOAP_FACTORY_EXCEPTION(), ex);
/*  91 */       throw new RuntimeException(ex);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void validateMessage(ProcessingContext context) throws XWSSecurityException {
/* 119 */     HarnessUtil.validateContext(context);
/* 120 */     FilterProcessingContext fpContext = new FilterProcessingContext(context);
/* 121 */     fpContext.isInboundMessage(true);
/* 122 */     SecurityPolicy pol = fpContext.getSecurityPolicy();
/* 123 */     MessagePolicy msgPolicy = null;
/* 124 */     List<MessagePolicy> messagePolicies = null;
/*     */ 
/*     */     
/* 127 */     if (pol instanceof MessagePolicy) {
/* 128 */       msgPolicy = (MessagePolicy)pol;
/* 129 */     } else if (pol instanceof PolicyAlternatives) {
/* 130 */       messagePolicies = ((PolicyAlternatives)pol).getSecurityPolicy();
/*     */     } 
/*     */     
/* 133 */     if (msgPolicy != null && msgPolicy.dumpMessages()) {
/* 134 */       DumpFilter.process(fpContext);
/*     */     }
/* 136 */     fpContext.setSecurityPolicyVersion(((ProcessingContextImpl)context).getSecurityPolicyVersion());
/*     */     
/* 138 */     fpContext.setExtraneousProperty("EnableWSS11PolicyReceiver", "true");
/* 139 */     List scList = new ArrayList();
/* 140 */     fpContext.setExtraneousProperty("receivedSignValues", scList);
/* 141 */     fpContext.setMode(3);
/*     */     
/* 143 */     pProcess(fpContext);
/*     */     
/* 145 */     if (PolicyUtils.isEmpty(pol)) {
/* 146 */       PolicyResolver opResolver = (PolicyResolver)fpContext.getExtraneousProperty("OperationResolver");
/*     */       
/* 148 */       if (opResolver != null) {
/* 149 */         pol = opResolver.resolvePolicy(fpContext);
/*     */       }
/*     */     } 
/*     */     
/* 153 */     if (pol instanceof MessagePolicy) {
/* 154 */       msgPolicy = (MessagePolicy)pol;
/* 155 */     } else if (pol instanceof PolicyAlternatives) {
/* 156 */       messagePolicies = ((PolicyAlternatives)pol).getSecurityPolicy();
/*     */       
/* 158 */       msgPolicy = (messagePolicies != null) ? messagePolicies.get(0) : null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 164 */       if (msgPolicy == null || (msgPolicy.size() == 0 && fpContext.getSOAPMessage().getSOAPBody().hasFault())) {
/*     */ 
/*     */         
/* 167 */         fpContext.getSecurableSoapMessage().deleteSecurityHeader();
/* 168 */         fpContext.getSOAPMessage().saveChanges();
/*     */         return;
/*     */       } 
/* 171 */     } catch (Exception ex) {
/* 172 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0307_NONCE_ENCTYPE_INVALID(), ex);
/* 173 */       throw new XWSSecurityException(ex);
/*     */     } 
/*     */ 
/*     */     
/* 177 */     TargetResolver targetResolver = new TargetResolverImpl(context);
/* 178 */     MessagePolicyVerifier mpv = new MessagePolicyVerifier(context, targetResolver);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 198 */     mpv.verifyPolicy((SecurityPolicy)fpContext.getInferredSecurityPolicy(), (SecurityPolicy)msgPolicy);
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 203 */       fpContext.getSecurableSoapMessage().deleteSecurityHeader();
/* 204 */       fpContext.getSOAPMessage().saveChanges();
/* 205 */     } catch (Exception ex) {
/* 206 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0370_ERROR_DELETING_SECHEADER(), ex);
/* 207 */       throw new XWSSecurityException(ex);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void processCurrentHeader(FilterProcessingContext fpContext, SOAPElement current, boolean isSecondary) throws XWSSecurityException {
/* 225 */     String elementName = current.getLocalName();
/*     */     
/* 227 */     if (isSecondary) {
/* 228 */       if ("UsernameToken".equals(elementName)) {
/* 229 */         AuthenticationTokenFilter.processUserNameToken(fpContext);
/* 230 */       } else if ("Timestamp".equals(elementName)) {
/* 231 */         TimestampFilter.process(fpContext);
/* 232 */       } else if ("SignatureConfirmation".equals(elementName)) {
/* 233 */         SignatureConfirmationFilter.process(fpContext);
/* 234 */       } else if (!"BinarySecurityToken".equals(elementName)) {
/*     */         
/* 236 */         if ("Assertion".equals(elementName)) {
/* 237 */           AuthenticationTokenFilter.processSamlToken(fpContext);
/* 238 */         } else if (!"SecurityTokenReference".equals(elementName)) {
/*     */           
/* 240 */           if ("SecurityContextToken".equals(elementName));
/*     */         }
/*     */       
/*     */       } 
/* 244 */     } else if ("Signature".equals(elementName)) {
/* 245 */       SignatureFilter.process(fpContext);
/* 246 */     } else if ("EncryptedKey".equals(elementName)) {
/* 247 */       Iterator iter = null;
/*     */       try {
/* 249 */         iter = current.getChildElements(sFactory.createName("ReferenceList", "xenc", "http://www.w3.org/2001/04/xmlenc#"));
/*     */       
/*     */       }
/* 252 */       catch (Exception e) {
/* 253 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0252_FAILEDTO_GET_CHILD_ELEMENT(), e);
/* 254 */         throw new XWSSecurityException(e);
/*     */       } 
/* 256 */       if (iter.hasNext()) {
/* 257 */         EncryptionFilter.process(fpContext);
/*     */       }
/*     */     }
/* 260 */     else if ("ReferenceList".equals(elementName)) {
/* 261 */       EncryptionFilter.process(fpContext);
/*     */     }
/* 263 */     else if ("EncryptedData".equals(elementName)) {
/* 264 */       EncryptionFilter.process(fpContext);
/*     */     }
/* 266 */     else if (!HarnessUtil.isSecondaryHeaderElement(current)) {
/* 267 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0204_ILLEGAL_HEADER_BLOCK(elementName));
/* 268 */       HarnessUtil.throwWssSoapFault("Unrecognized header block: " + elementName);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void pProcess(FilterProcessingContext fpContext) throws XWSSecurityException {
/* 293 */     SecurityHeader header = fpContext.getSecurableSoapMessage().findSecurityHeader();
/* 294 */     MessagePolicy policy = (MessagePolicy)fpContext.getSecurityPolicy();
/*     */     
/* 296 */     if (header == null) {
/* 297 */       if (policy != null) {
/* 298 */         if (PolicyTypeUtil.messagePolicy((SecurityPolicy)policy)) {
/* 299 */           if (!policy.isEmpty()) {
/* 300 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0253_INVALID_MESSAGE());
/* 301 */             throw new XWSSecurityException("Message does not conform to configured policy: No Security Header found in incoming message");
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 307 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0253_INVALID_MESSAGE());
/* 308 */           throw new XWSSecurityException("Message does not conform to configured policy: No Security Header found in incoming message");
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 317 */     if (policy != null && policy.dumpMessages()) {
/* 318 */       DumpFilter.process(fpContext);
/*     */     }
/* 320 */     SOAPElement current = header.getCurrentHeaderBlockElement();
/* 321 */     SOAPElement first = current;
/*     */     
/* 323 */     while (current != null) {
/* 324 */       processCurrentHeader(fpContext, current, false);
/* 325 */       current = header.getCurrentHeaderBlockElement();
/*     */     } 
/*     */     
/* 328 */     current = first;
/* 329 */     header.setCurrentHeaderElement(current);
/*     */     
/* 331 */     while (current != null) {
/* 332 */       processCurrentHeader(fpContext, current, true);
/* 333 */       current = header.getCurrentHeaderBlockElement();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void handleFault(ProcessingContext context) {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\NewSecurityRecipient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */