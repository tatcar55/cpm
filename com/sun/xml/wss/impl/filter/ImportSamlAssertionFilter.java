/*     */ package com.sun.xml.wss.impl.filter;
/*     */ 
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.SecurityHeader;
/*     */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*     */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.logging.impl.filter.LogStringsMessages;
/*     */ import com.sun.xml.wss.saml.Assertion;
/*     */ import com.sun.xml.wss.saml.AssertionUtil;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import org.w3c.dom.Element;
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
/*     */ public class ImportSamlAssertionFilter
/*     */ {
/*  72 */   protected static final Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void process(FilterProcessingContext context) throws XWSSecurityException {
/*  83 */     SecurableSoapMessage secureMessage = context.getSecurableSoapMessage();
/*  84 */     SecurityHeader wsseSecurity = secureMessage.findSecurityHeader();
/*  85 */     Assertion samlAssertion = null;
/*  86 */     SOAPElement samlElement = null;
/*     */     
/*  88 */     if (context.getMode() == 0 || context.getMode() == 2 || context.getMode() == 3) {
/*     */ 
/*     */ 
/*     */       
/*  92 */       NodeList nl = null;
/*  93 */       Element elem = null;
/*     */       
/*  95 */       for (Iterator iter = wsseSecurity.getChildElements(); iter.hasNext(); ) {
/*  96 */         Object obj = iter.next();
/*     */ 
/*     */ 
/*     */         
/* 100 */         if (obj instanceof org.w3c.dom.Text) {
/*     */           continue;
/*     */         }
/* 103 */         if (obj instanceof Element) {
/* 104 */           elem = (Element)obj;
/* 105 */           if (elem.getAttributeNode("ID") != null) {
/* 106 */             nl = wsseSecurity.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Assertion");
/*     */             break;
/*     */           } 
/* 109 */           if (elem.getAttributeNode("AssertionID") != null) {
/* 110 */             nl = wsseSecurity.getElementsByTagNameNS("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 124 */       if (nl == null) {
/* 125 */         throw new XWSSecurityException("SAMLAssertion is null");
/*     */       }
/* 127 */       int nodeListLength = nl.getLength();
/* 128 */       int countSamlInsideAdviceElement = 0;
/* 129 */       for (int i = 0; i < nodeListLength; i++) {
/* 130 */         if (nl.item(i).getParentNode().getLocalName().equals("Advice")) {
/* 131 */           countSamlInsideAdviceElement++;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 136 */       if (nodeListLength == 0) {
/* 137 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1431_NO_SAML_FOUND());
/* 138 */         throw new XWSSecurityException("No SAML Assertion found, Reciever requirement not met");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 144 */       samlElement = (SOAPElement)nl.item(0);
/*     */       try {
/* 146 */         samlAssertion = AssertionUtil.fromElement(samlElement);
/* 147 */       } catch (Exception e) {
/* 148 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1432_SAML_IMPORT_EXCEPTION(), e);
/* 149 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Exception while importing SAML Token", e);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 156 */       if (context.getMode() == 0)
/*     */       {
/*     */         
/* 159 */         AuthenticationTokenPolicy policy = (AuthenticationTokenPolicy)context.getSecurityPolicy();
/* 160 */         AuthenticationTokenPolicy.SAMLAssertionBinding sAMLAssertionBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)policy.getFeatureBinding();
/*     */ 
/*     */ 
/*     */         
/* 164 */         if (!"".equals(sAMLAssertionBinding.getAuthorityIdentifier()) && 
/* 165 */           !sAMLAssertionBinding.getAuthorityIdentifier().equals(samlAssertion.getSamlIssuer()))
/*     */         {
/* 167 */           XWSSecurityException xwse = new XWSSecurityException("Invalid Assertion Issuer, expected " + sAMLAssertionBinding.getAuthorityIdentifier() + ", found " + samlAssertion.getSamlIssuer());
/*     */           
/* 169 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1434_SAML_ISSUER_VALIDATION_FAILED(), (Throwable)xwse);
/* 170 */           throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "Received SAML Assertion has invalid Issuer", xwse);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 180 */       if (context.getMode() == 1) {
/* 181 */         throw new XWSSecurityException("Internal Error: Called ImportSAMLAssertionFilter in POSTHOC Mode");
/*     */       }
/*     */ 
/*     */       
/* 185 */       if (context.getMode() == 3) {
/* 186 */         AuthenticationTokenPolicy.SAMLAssertionBinding bind = new AuthenticationTokenPolicy.SAMLAssertionBinding();
/*     */         
/* 188 */         context.getInferredSecurityPolicy().append((SecurityPolicy)bind);
/*     */       } 
/*     */       
/*     */       try {
/* 192 */         samlAssertion = AssertionUtil.fromElement(wsseSecurity.getCurrentHeaderElement());
/* 193 */       } catch (Exception ex) {
/* 194 */         log.log(Level.SEVERE, LogStringsMessages.WSS_1432_SAML_IMPORT_EXCEPTION(), ex);
/* 195 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "Exception while importing SAML Assertion", ex);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 202 */     HashMap<String, Assertion> tokenCache = context.getTokenCache();
/*     */     
/* 204 */     tokenCache.put(samlAssertion.getAssertionID(), samlAssertion);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 225 */     context.getSecurityEnvironment().validateSAMLAssertion(context.getExtraneousProperties(), samlElement);
/*     */     
/* 227 */     context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(context), samlAssertion);
/*     */ 
/*     */     
/* 230 */     AuthenticationTokenPolicy.SAMLAssertionBinding samlPolicy = new AuthenticationTokenPolicy.SAMLAssertionBinding();
/* 231 */     samlPolicy.setUUID(samlAssertion.getAssertionID());
/* 232 */     context.getInferredSecurityPolicy().append((SecurityPolicy)samlPolicy);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\filter\ImportSamlAssertionFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */