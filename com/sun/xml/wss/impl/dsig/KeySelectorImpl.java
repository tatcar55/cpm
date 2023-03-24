/*      */ package com.sun.xml.wss.impl.dsig;
/*      */ 
/*      */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*      */ import com.sun.xml.ws.api.security.trust.client.IssuedTokenConfiguration;
/*      */ import com.sun.xml.ws.api.security.trust.client.IssuedTokenManager;
/*      */ import com.sun.xml.ws.runtime.dev.SessionManager;
/*      */ import com.sun.xml.ws.security.IssuedTokenContext;
/*      */ import com.sun.xml.ws.security.SecurityContextToken;
/*      */ import com.sun.xml.ws.security.SecurityContextTokenInfo;
/*      */ import com.sun.xml.ws.security.Token;
/*      */ import com.sun.xml.ws.security.impl.DerivedKeyTokenImpl;
/*      */ import com.sun.xml.ws.security.secconv.impl.client.DefaultSCTokenConfiguration;
/*      */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*      */ import com.sun.xml.ws.security.trust.elements.BinarySecret;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.core.DerivedKeyTokenHeaderBlock;
/*      */ import com.sun.xml.wss.core.EncryptedKeyToken;
/*      */ import com.sun.xml.wss.core.KeyInfoHeaderBlock;
/*      */ import com.sun.xml.wss.core.ReferenceElement;
/*      */ import com.sun.xml.wss.core.SecurityContextTokenImpl;
/*      */ import com.sun.xml.wss.core.SecurityToken;
/*      */ import com.sun.xml.wss.core.SecurityTokenReference;
/*      */ import com.sun.xml.wss.core.X509SecurityToken;
/*      */ import com.sun.xml.wss.core.reference.DirectReference;
/*      */ import com.sun.xml.wss.core.reference.KeyIdentifier;
/*      */ import com.sun.xml.wss.core.reference.X509IssuerSerial;
/*      */ import com.sun.xml.wss.impl.AlgorithmSuite;
/*      */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*      */ import com.sun.xml.wss.impl.MessageConstants;
/*      */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*      */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*      */ import com.sun.xml.wss.impl.XMLUtil;
/*      */ import com.sun.xml.wss.impl.misc.Base64;
/*      */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*      */ import com.sun.xml.wss.impl.misc.KeyResolver;
/*      */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*      */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*      */ import com.sun.xml.wss.logging.impl.dsig.LogStringsMessages;
/*      */ import com.sun.xml.wss.saml.Assertion;
/*      */ import com.sun.xml.wss.saml.AssertionUtil;
/*      */ import com.sun.xml.wss.saml.SAMLException;
/*      */ import com.sun.xml.wss.saml.util.SAMLUtil;
/*      */ import java.math.BigInteger;
/*      */ import java.net.URI;
/*      */ import java.security.Key;
/*      */ import java.security.KeyException;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.PublicKey;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.crypto.spec.SecretKeySpec;
/*      */ import javax.security.auth.Subject;
/*      */ import javax.xml.crypto.AlgorithmMethod;
/*      */ import javax.xml.crypto.KeySelector;
/*      */ import javax.xml.crypto.KeySelectorException;
/*      */ import javax.xml.crypto.KeySelectorResult;
/*      */ import javax.xml.crypto.MarshalException;
/*      */ import javax.xml.crypto.NodeSetData;
/*      */ import javax.xml.crypto.URIDereferencer;
/*      */ import javax.xml.crypto.URIReference;
/*      */ import javax.xml.crypto.URIReferenceException;
/*      */ import javax.xml.crypto.XMLCryptoContext;
/*      */ import javax.xml.crypto.XMLStructure;
/*      */ import javax.xml.crypto.dom.DOMStructure;
/*      */ import javax.xml.crypto.dsig.SignatureMethod;
/*      */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*      */ import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
/*      */ import javax.xml.crypto.dsig.keyinfo.KeyName;
/*      */ import javax.xml.crypto.dsig.keyinfo.KeyValue;
/*      */ import javax.xml.crypto.dsig.keyinfo.X509Data;
/*      */ import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.soap.SOAPElement;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
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
/*      */ public class KeySelectorImpl
/*      */   extends KeySelector
/*      */ {
/*  161 */   private static KeySelectorImpl keyResolver = null;
/*  162 */   private static Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.dsig", "com.sun.xml.wss.logging.impl.dsig.LogStrings");
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  167 */     keyResolver = new KeySelectorImpl();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static KeySelector getInstance() {
/*  178 */     return keyResolver;
/*      */   }
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
/*      */   public KeySelectorResult select(KeyInfo keyInfo, KeySelector.Purpose purpose, AlgorithmMethod method, XMLCryptoContext context) throws KeySelectorException {
/*  191 */     if (keyInfo == null) {
/*  192 */       if (logger.getLevel() == Level.SEVERE) {
/*  193 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1317_KEYINFO_NULL());
/*      */       }
/*  195 */       throw new KeySelectorException("Null KeyInfo object!");
/*      */     } 
/*      */ 
/*      */     
/*  199 */     if (logger.isLoggable(Level.FINEST)) {
/*  200 */       logger.log(Level.FINEST, "KeySelectorResult::select Purpose =  " + purpose);
/*  201 */       logger.log(Level.FINEST, "KeySelectorResult::select Algorithm is " + method.getAlgorithm());
/*  202 */       logger.log(Level.FINEST, "KeySelectorResult::select ParameterSpec is " + method.getParameterSpec());
/*      */     } 
/*      */     try {
/*  205 */       SignatureMethod sm = (SignatureMethod)method;
/*  206 */       List<XMLStructure> list = keyInfo.getContent();
/*  207 */       FilterProcessingContext wssContext = (FilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/*      */       
/*  209 */       SecurityPolicy securityPolicy = wssContext.getSecurityPolicy();
/*  210 */       boolean isBSP = false;
/*  211 */       if (securityPolicy != null) {
/*  212 */         if (PolicyTypeUtil.messagePolicy(securityPolicy)) {
/*  213 */           isBSP = ((MessagePolicy)securityPolicy).isBSP();
/*      */         } else {
/*  215 */           isBSP = ((WSSPolicy)securityPolicy).isBSP();
/*      */         } 
/*      */       }
/*      */       
/*  219 */       if (isBSP && list.size() > 1) {
/*  220 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1350_ILLEGAL_BSP_VIOLATION_KEY_INFO());
/*  221 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "BSP Violation of R5402: KeyInfo MUST have exactly one child", null);
/*      */       } 
/*      */ 
/*      */       
/*  225 */       boolean isStr = false;
/*      */       
/*  227 */       for (int i = 0; i < list.size(); i++) {
/*  228 */         XMLStructure xmlStructure = list.get(i);
/*  229 */         if (xmlStructure instanceof KeyValue) {
/*  230 */           PublicKey pk = null;
/*      */           try {
/*  232 */             pk = ((KeyValue)xmlStructure).getPublicKey();
/*  233 */           } catch (KeyException ke) {
/*  234 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1351_EXCEPTION_KEYSELECTOR_PUBLICKEY(), ke);
/*  235 */             throw new KeySelectorException(ke);
/*      */           } 
/*      */ 
/*      */           
/*  239 */           if (purpose == KeySelector.Purpose.VERIFY) {
/*  240 */             X509Certificate cert = wssContext.getSecurityEnvironment().getCertificate(wssContext.getExtraneousProperties(), pk, false);
/*  241 */             wssContext.getSecurityEnvironment().validateCertificate(cert, wssContext.getExtraneousProperties());
/*      */           } 
/*      */           
/*  244 */           if (algEquals(sm.getAlgorithm(), pk.getAlgorithm())) {
/*  245 */             return new SimpleKeySelectorResult(pk);
/*      */           }
/*  247 */         } else if (xmlStructure instanceof DOMStructure) {
/*  248 */           SOAPElement reference = (SOAPElement)((DOMStructure)xmlStructure).getNode();
/*  249 */           if (isSecurityTokenReference(reference)) {
/*  250 */             isStr = true;
/*  251 */             final Key key = resolve(reference, context, purpose);
/*  252 */             return new KeySelectorResult() {
/*      */                 public Key getKey() {
/*  254 */                   return key;
/*      */                 }
/*      */               };
/*      */           } 
/*  258 */         } else if (xmlStructure instanceof KeyName) {
/*  259 */           KeyName keyName = (KeyName)xmlStructure;
/*  260 */           Key returnKey = wssContext.getSecurityEnvironment().getSecretKey(wssContext.getExtraneousProperties(), keyName.getName(), false);
/*      */           
/*  262 */           if (returnKey == null) {
/*  263 */             X509Certificate cert = wssContext.getSecurityEnvironment().getCertificate(wssContext.getExtraneousProperties(), keyName.getName(), false);
/*      */             
/*  265 */             if (cert != null && algEquals(sm.getAlgorithm(), cert.getPublicKey().getAlgorithm())) {
/*      */               
/*  267 */               wssContext.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(wssContext), cert);
/*      */               
/*  269 */               return new SimpleKeySelectorResult(cert.getPublicKey());
/*      */             } 
/*      */           } else {
/*  272 */             return new SimpleKeySelectorResult(returnKey);
/*      */           } 
/*  274 */         } else if (xmlStructure instanceof X509Data) {
/*  275 */           final Key key = resolveX509Data(wssContext, (X509Data)xmlStructure, purpose);
/*  276 */           return new SimpleKeySelectorResult(key);
/*      */         } 
/*      */       } 
/*      */       
/*  280 */       if (isBSP && !isStr) {
/*  281 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1379_ILLEGAL_BSP_VIOLATION_OF_R_5409());
/*  282 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "BSP Violation of R5409: Child element of KeyInfo MUST be a STR element", null);
/*      */       }
/*      */     
/*      */     }
/*  286 */     catch (KeySelectorException kse) {
/*  287 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1352_EXCEPTION_KEYSELECTOR(), kse);
/*  288 */       throw kse;
/*  289 */     } catch (Exception ex) {
/*  290 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1353_UNABLE_RESOLVE_KEY_INFORMATION(), ex.getMessage());
/*  291 */       throw new KeySelectorException(ex);
/*      */     } 
/*  293 */     logger.log(Level.SEVERE, LogStringsMessages.WSS_1354_NULL_KEY_VALUE());
/*  294 */     throw new KeySelectorException("No KeyValue element found!");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean algEquals(String algURI, String algName) {
/*  305 */     if (algName.equalsIgnoreCase("DSA") && algURI.equalsIgnoreCase("http://www.w3.org/2000/09/xmldsig#dsa-sha1"))
/*      */     {
/*  307 */       return true; } 
/*  308 */     if (algName.equalsIgnoreCase("RSA") && algURI.equalsIgnoreCase("http://www.w3.org/2000/09/xmldsig#rsa-sha1"))
/*      */     {
/*  310 */       return true;
/*      */     }
/*  312 */     return false;
/*      */   }
/*      */   
/*      */   private static class SimpleKeySelectorResult implements KeySelectorResult {
/*      */     private Key pk;
/*      */     
/*      */     SimpleKeySelectorResult(Key pk) {
/*  319 */       this.pk = pk;
/*      */     } public Key getKey() {
/*  321 */       return this.pk;
/*      */     } }
/*      */   
/*      */   private static Key resolve(SOAPElement securityTokenReference, XMLCryptoContext context, KeySelector.Purpose purpose) throws KeySelectorException {
/*      */     try {
/*  326 */       FilterProcessingContext wssContext = (FilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/*  327 */       SecurableSoapMessage secureMsg = wssContext.getSecurableSoapMessage();
/*  328 */       AlgorithmSuite algSuite = wssContext.getAlgorithmSuite();
/*  329 */       String encAlgo = null;
/*  330 */       boolean isPolicyRecipient = (wssContext.getMode() == 3);
/*  331 */       if (algSuite != null) {
/*  332 */         encAlgo = algSuite.getEncryptionAlgorithm();
/*      */       }
/*      */       
/*  335 */       SecurityPolicy securityPolicy = wssContext.getSecurityPolicy();
/*  336 */       boolean isBSP = false;
/*  337 */       if (securityPolicy != null) {
/*  338 */         if (PolicyTypeUtil.messagePolicy(securityPolicy)) {
/*  339 */           isBSP = ((MessagePolicy)securityPolicy).isBSP();
/*      */         } else {
/*  341 */           isBSP = ((WSSPolicy)securityPolicy).isBSP();
/*      */         } 
/*      */       }
/*      */       
/*  345 */       SecurityTokenReference str = new SecurityTokenReference(securityTokenReference, isBSP);
/*  346 */       ReferenceElement refElement = str.getReference();
/*  347 */       HashMap<String, X509SecurityToken> tokenCache = wssContext.getTokenCache();
/*  348 */       HashMap insertedX509Cache = wssContext.getInsertedX509Cache();
/*  349 */       SignaturePolicy policy = (SignaturePolicy)wssContext.getInferredPolicy();
/*  350 */       SignaturePolicy inferredSignaturePolicy = null;
/*  351 */       if (isPolicyRecipient) {
/*  352 */         int i = wssContext.getInferredSecurityPolicy().size() - 1;
/*      */         
/*  354 */         if (PolicyTypeUtil.signaturePolicy(wssContext.getInferredSecurityPolicy().get(i))) {
/*  355 */           inferredSignaturePolicy = (SignaturePolicy)wssContext.getInferredSecurityPolicy().get(i);
/*      */         }
/*      */       } 
/*      */       
/*  359 */       AuthenticationTokenPolicy.X509CertificateBinding keyBinding = null;
/*      */ 
/*      */       
/*  362 */       if (policy != null) {
/*  363 */         keyBinding = (AuthenticationTokenPolicy.X509CertificateBinding)policy.newX509CertificateKeyBinding();
/*      */       }
/*      */       
/*  366 */       Key returnKey = null;
/*  367 */       boolean isSymmetric = false;
/*  368 */       if (refElement instanceof KeyIdentifier) {
/*  369 */         KeyIdentifier keyId = (KeyIdentifier)refElement;
/*  370 */         if (keyBinding != null) {
/*  371 */           keyBinding.setReferenceType("Identifier");
/*  372 */           keyBinding.setValueType(keyId.getValueType());
/*      */         } 
/*      */ 
/*      */         
/*  376 */         if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier".equals(keyId.getValueType()) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3SubjectKeyIdentifier".equals(keyId.getValueType())) {
/*      */           
/*  378 */           if (isPolicyRecipient && inferredSignaturePolicy != null) {
/*  379 */             MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/*  380 */             AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  381 */             x509Binding.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier");
/*  382 */             x509Binding.setReferenceType("Identifier");
/*      */             
/*  384 */             if (inferredKB == null) {
/*  385 */               inferredSignaturePolicy.setKeyBinding((MLSPolicy)x509Binding);
/*  386 */             } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  387 */               ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  388 */               isSymmetric = true;
/*  389 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  390 */               DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  391 */               if (dktBind.getOriginalKeyBinding() == null) {
/*  392 */                 ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  393 */               } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  394 */                 dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*  395 */                 isSymmetric = true;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/*  400 */           if (purpose == KeySelector.Purpose.VERIFY) {
/*  401 */             byte[] keyIdBytes = XMLUtil.getDecodedBase64EncodedData(keyId.getReferenceValue());
/*  402 */             wssContext.setExtraneousProperty("requester.keyid", new String(keyIdBytes));
/*      */             
/*  404 */             X509Certificate cert = wssContext.getSecurityEnvironment().getCertificate(wssContext.getExtraneousProperties(), keyIdBytes);
/*      */             
/*  406 */             if (!isSymmetric) {
/*  407 */               wssContext.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(wssContext), cert);
/*      */             }
/*      */             
/*  410 */             returnKey = cert.getPublicKey();
/*  411 */           } else if (purpose == KeySelector.Purpose.SIGN) {
/*  412 */             returnKey = wssContext.getSecurityEnvironment().getPrivateKey(wssContext.getExtraneousProperties(), XMLUtil.getDecodedBase64EncodedData(keyId.getReferenceValue()));
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*  418 */         else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1".equals(keyId.getValueType())) {
/*  419 */           if (isPolicyRecipient && inferredSignaturePolicy != null) {
/*  420 */             MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/*  421 */             AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  422 */             x509Binding.setValueType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1");
/*  423 */             x509Binding.setReferenceType("Identifier");
/*  424 */             if (inferredKB == null) {
/*  425 */               inferredSignaturePolicy.setKeyBinding((MLSPolicy)x509Binding);
/*  426 */             } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  427 */               ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  428 */               isSymmetric = true;
/*  429 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  430 */               DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  431 */               if (dktBind.getOriginalKeyBinding() == null) {
/*  432 */                 ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  433 */               } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  434 */                 dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*  435 */                 isSymmetric = true;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/*  440 */           if (purpose == KeySelector.Purpose.VERIFY) {
/*  441 */             byte[] keyIdBytes = XMLUtil.getDecodedBase64EncodedData(keyId.getReferenceValue());
/*  442 */             wssContext.setExtraneousProperty("requester.keyid", new String(keyIdBytes));
/*      */             
/*  444 */             X509Certificate cert = wssContext.getSecurityEnvironment().getCertificate(wssContext.getExtraneousProperties(), keyIdBytes, "Thumbprint");
/*      */             
/*  446 */             if (!isSymmetric) {
/*  447 */               wssContext.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(wssContext), cert);
/*      */             }
/*      */             
/*  450 */             returnKey = cert.getPublicKey();
/*  451 */           } else if (purpose == KeySelector.Purpose.SIGN) {
/*  452 */             returnKey = wssContext.getSecurityEnvironment().getPrivateKey(wssContext.getExtraneousProperties(), XMLUtil.getDecodedBase64EncodedData(keyId.getReferenceValue()), "Thumbprint");
/*      */           
/*      */           }
/*      */         
/*      */         }
/*  457 */         else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1".equals(keyId.getValueType())) {
/*  458 */           if (isPolicyRecipient && inferredSignaturePolicy != null) {
/*  459 */             MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/*  460 */             SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/*  461 */             AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  462 */             x509Binding.setReferenceType("Identifier");
/*  463 */             skBinding.setKeyBinding((MLSPolicy)x509Binding);
/*      */             
/*  465 */             if (inferredKB == null) {
/*  466 */               inferredSignaturePolicy.setKeyBinding((MLSPolicy)skBinding);
/*  467 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  468 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  469 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)skBinding);
/*      */             } 
/*      */           } 
/*      */           
/*  473 */           String ekSha1RefValue = (String)wssContext.getExtraneousProperty("EncryptedKeySHA1");
/*  474 */           Key secretKey = (Key)wssContext.getExtraneousProperty("SecretKey");
/*  475 */           String keyRefValue = keyId.getReferenceValue();
/*  476 */           if (ekSha1RefValue != null && secretKey != null) {
/*  477 */             if (ekSha1RefValue.equals(keyRefValue))
/*  478 */               returnKey = secretKey; 
/*      */           } else {
/*  480 */             String message = "EncryptedKeySHA1 reference not correct";
/*  481 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1306_UNSUPPORTED_KEY_IDENTIFIER_REFERENCE_TYPE(), new Object[] { message });
/*  482 */             throw new KeySelectorException(message);
/*      */           }
/*      */         
/*  485 */         } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(keyId.getValueType()) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(keyId.getValueType())) {
/*      */ 
/*      */           
/*  488 */           String assertionID = keyId.getReferenceValue();
/*  489 */           Element tokenElement = wssContext.getIssuedSAMLToken();
/*  490 */           if (tokenElement == null) {
/*  491 */             Assertion samlAssertion = (Assertion)tokenCache.get(assertionID);
/*  492 */             if (samlAssertion == null) {
/*  493 */               if (str.getSamlAuthorityBinding() != null) {
/*  494 */                 tokenElement = wssContext.getSecurityEnvironment().locateSAMLAssertion(wssContext.getExtraneousProperties(), str.getSamlAuthorityBinding(), assertionID, secureMsg.getSOAPPart());
/*      */               }
/*      */               else {
/*      */                 
/*  498 */                 tokenElement = SAMLUtil.locateSamlAssertion(assertionID, secureMsg.getSOAPPart());
/*  499 */                 if (!"true".equals(wssContext.getExtraneousProperty("Saml_Signature_resolved")) || "false".equals(wssContext.getExtraneousProperty("Saml_Signature_resolved")))
/*      */                 {
/*  501 */                   wssContext.setExtraneousProperty("Saml_Signature_resolved", "false");
/*      */                 }
/*      */               } 
/*      */             } else {
/*      */               try {
/*  506 */                 tokenElement = samlAssertion.toElement(null);
/*  507 */               } catch (Exception e) {
/*  508 */                 logger.log(Level.SEVERE, LogStringsMessages.WSS_1355_UNABLETO_RESOLVE_SAML_ASSERTION(), e.getMessage());
/*  509 */                 throw new KeySelectorException(e);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/*  514 */           if (isPolicyRecipient && inferredSignaturePolicy != null) {
/*  515 */             MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/*  516 */             IssuedTokenKeyBinding itkBinding = new IssuedTokenKeyBinding();
/*  517 */             if (inferredKB == null) {
/*  518 */               if (wssContext.hasIssuedToken()) {
/*  519 */                 inferredSignaturePolicy.setKeyBinding((MLSPolicy)itkBinding);
/*      */               } else {
/*  521 */                 inferredSignaturePolicy.setKeyBinding((MLSPolicy)new AuthenticationTokenPolicy.SAMLAssertionBinding());
/*      */               } 
/*  523 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  524 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  525 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)itkBinding);
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  530 */           returnKey = resolveSamlAssertion(context, tokenElement, purpose, assertionID);
/*  531 */           addAuthorityId(tokenElement, wssContext);
/*  532 */           if (wssContext.hasIssuedToken() && returnKey != null) {
/*  533 */             SecurityUtil.initInferredIssuedTokenContext(wssContext, (Token)str, returnKey);
/*      */           
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  539 */           String assertionID = keyId.getDecodedReferenceValue();
/*  540 */           Element samlAssertion = null;
/*      */           try {
/*  542 */             samlAssertion = resolveSAMLToken(str, assertionID, wssContext);
/*  543 */           } catch (Exception e) {
/*  544 */             if (logger.isLoggable(Level.FINEST)) {
/*  545 */               logger.log(Level.FINEST, "Error occurred while trying to resolve SAML assertion" + e.getMessage());
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/*  550 */           if (samlAssertion != null) {
/*  551 */             if (isPolicyRecipient && inferredSignaturePolicy != null) {
/*  552 */               MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/*  553 */               IssuedTokenKeyBinding itkBinding = new IssuedTokenKeyBinding();
/*  554 */               if (inferredKB == null) {
/*  555 */                 if (wssContext.hasIssuedToken()) {
/*  556 */                   inferredSignaturePolicy.setKeyBinding((MLSPolicy)itkBinding);
/*      */                 } else {
/*  558 */                   inferredSignaturePolicy.setKeyBinding((MLSPolicy)new AuthenticationTokenPolicy.SAMLAssertionBinding());
/*      */                 } 
/*  560 */               } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  561 */                 (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  562 */                 ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)itkBinding);
/*      */               } 
/*      */             } 
/*  565 */             returnKey = resolveSamlAssertion(context, samlAssertion, purpose, assertionID);
/*  566 */             addAuthorityId(samlAssertion, wssContext);
/*      */ 
/*      */             
/*  569 */             if (wssContext.hasIssuedToken() && returnKey != null) {
/*  570 */               SecurityUtil.initInferredIssuedTokenContext(wssContext, (Token)str, returnKey);
/*      */             
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/*  576 */             if (isPolicyRecipient && inferredSignaturePolicy != null) {
/*  577 */               MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/*  578 */               AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  579 */               x509Binding.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier");
/*  580 */               x509Binding.setReferenceType("Identifier");
/*  581 */               if (inferredKB == null) {
/*  582 */                 inferredSignaturePolicy.setKeyBinding((MLSPolicy)x509Binding);
/*  583 */               } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  584 */                 ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  585 */               } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  586 */                 DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  587 */                 if (dktBind.getOriginalKeyBinding() == null) {
/*  588 */                   ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  589 */                 } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  590 */                   dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */             
/*  595 */             if (purpose == KeySelector.Purpose.VERIFY) {
/*  596 */               byte[] keyIdBytes = XMLUtil.getDecodedBase64EncodedData(keyId.getReferenceValue());
/*  597 */               wssContext.setExtraneousProperty("requester.keyid", new String(keyIdBytes));
/*      */               
/*  599 */               X509Certificate cert = wssContext.getSecurityEnvironment().getCertificate(wssContext.getExtraneousProperties(), XMLUtil.getDecodedBase64EncodedData(keyId.getReferenceValue()));
/*      */ 
/*      */               
/*  602 */               wssContext.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(wssContext), cert);
/*      */               
/*  604 */               returnKey = cert.getPublicKey();
/*      */             }
/*  606 */             else if (purpose == KeySelector.Purpose.SIGN) {
/*  607 */               returnKey = wssContext.getSecurityEnvironment().getPrivateKey(wssContext.getExtraneousProperties(), XMLUtil.getDecodedBase64EncodedData(keyId.getReferenceValue()));
/*      */             }
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  615 */       else if (refElement instanceof DirectReference) {
/*  616 */         if (keyBinding != null) {
/*  617 */           keyBinding.setReferenceType("Direct");
/*      */         }
/*      */         
/*  620 */         String uri = ((DirectReference)refElement).getURI();
/*  621 */         if (isBSP && !uri.startsWith("#")) {
/*  622 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1356_VIOLATION_BSP_R_5204());
/*  623 */           throw new XWSSecurityException("Violation of BSP R5204 : When a SECURITY_TOKEN_REFERENCE uses a Direct Reference to an INTERNAL_SECURITY_TOKEN, it MUST use a Shorthand XPointer Reference");
/*      */         } 
/*      */ 
/*      */         
/*  627 */         String valueType = ((DirectReference)refElement).getValueType();
/*  628 */         if ("http://schemas.xmlsoap.org/ws/2005/02/sc/dk".equals(valueType) || "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/dk".equals(valueType))
/*      */         {
/*      */           
/*  631 */           valueType = null;
/*      */         }
/*      */         
/*  634 */         if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(valueType) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1".equals(valueType)) {
/*      */           
/*  636 */           if (keyBinding != null) {
/*  637 */             keyBinding.setValueType(valueType);
/*      */           }
/*  639 */           String wsuId = SecurableSoapMessage.getIdFromFragmentRef(uri);
/*  640 */           X509SecurityToken token = (X509SecurityToken)insertedX509Cache.get(wsuId);
/*      */ 
/*      */ 
/*      */           
/*  644 */           if (token == null) {
/*  645 */             token = (X509SecurityToken)resolveToken(wsuId, context);
/*  646 */             if (token == null) {
/*  647 */               logger.log(Level.SEVERE, LogStringsMessages.WSS_1357_UNABLETO_LOCATE_TOKEN());
/*  648 */               throw new KeySelectorException("Token with Id " + wsuId + "not found");
/*      */             } 
/*  650 */             tokenCache.put(wsuId, token);
/*      */           } 
/*      */ 
/*      */           
/*  654 */           if (isPolicyRecipient && inferredSignaturePolicy != null) {
/*  655 */             MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/*  656 */             AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  657 */             x509Binding.setReferenceType("Direct");
/*  658 */             x509Binding.setValueType(valueType);
/*  659 */             if (inferredKB == null) {
/*  660 */               inferredSignaturePolicy.setKeyBinding((MLSPolicy)x509Binding);
/*  661 */             } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  662 */               ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  663 */               isSymmetric = true;
/*  664 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  665 */               DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  666 */               if (dktBind.getOriginalKeyBinding() == null) {
/*  667 */                 dktBind.setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  668 */               } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  669 */                 dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*  670 */                 isSymmetric = true;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  676 */           returnKey = resolveX509Token(wssContext, token, purpose, isSymmetric);
/*      */         }
/*  678 */         else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(valueType)) {
/*  679 */           String wsuId = SecurableSoapMessage.getIdFromFragmentRef(uri);
/*  680 */           SecurityToken token = (SecurityToken)tokenCache.get(wsuId);
/*  681 */           if (token == null) {
/*  682 */             token = resolveToken(wsuId, context);
/*  683 */             if (token == null) {
/*  684 */               logger.log(Level.SEVERE, LogStringsMessages.WSS_1357_UNABLETO_LOCATE_TOKEN());
/*  685 */               throw new KeySelectorException("Token with Id " + wsuId + "not found");
/*      */             } 
/*  687 */             tokenCache.put(wsuId, token);
/*      */           } 
/*      */           
/*  690 */           KeyInfoHeaderBlock kiHB = ((EncryptedKeyToken)token).getKeyInfo();
/*  691 */           SecurityTokenReference sectr = kiHB.getSecurityTokenReference(0);
/*  692 */           SOAPElement se = sectr.getAsSoapElement();
/*  693 */           ReferenceElement refElem = sectr.getReference();
/*  694 */           if (isPolicyRecipient && inferredSignaturePolicy != null) {
/*  695 */             MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/*  696 */             SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/*  697 */             AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  698 */             skBinding.setKeyBinding((MLSPolicy)x509Binding);
/*      */             
/*  700 */             if (inferredKB == null) {
/*  701 */               inferredSignaturePolicy.setKeyBinding((MLSPolicy)skBinding);
/*  702 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  703 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  704 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)skBinding);
/*      */             } 
/*      */           } 
/*      */ 
/*      */           
/*  709 */           Key privKey = resolve(se, context, KeySelector.Purpose.SIGN);
/*  710 */           Element cipherData = ((EncryptedKeyToken)token).getAsSoapElement().getChildElements(new QName("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc")).next();
/*  711 */           String cipherValue = cipherData.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue").item(0).getTextContent();
/*  712 */           byte[] decodedCipher = Base64.decode(cipherValue);
/*  713 */           byte[] ekSha1 = MessageDigest.getInstance("SHA-1").digest(decodedCipher);
/*      */           
/*  715 */           String encEkSha1 = Base64.encode(ekSha1);
/*  716 */           wssContext.setExtraneousProperty("EKSHA1Value", encEkSha1);
/*      */           
/*  718 */           returnKey = ((EncryptedKeyToken)token).getSecretKey(privKey, encAlgo);
/*  719 */           wssContext.setExtraneousProperty("SecretKeyValue", returnKey);
/*  720 */         } else if ("http://schemas.xmlsoap.org/ws/2005/02/sc/sct".equals(valueType) || "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct".equals(valueType)) {
/*      */           
/*  722 */           String sctId = SecurableSoapMessage.getIdFromFragmentRef(uri);
/*  723 */           SecurityToken securityToken = (SecurityToken)tokenCache.get(sctId);
/*      */           
/*  725 */           if (securityToken == null) {
/*  726 */             SecurityContextTokenImpl securityContextTokenImpl = SecurityUtil.locateBySCTId(wssContext, uri);
/*  727 */             if (securityContextTokenImpl == null) {
/*  728 */               securityToken = resolveToken(sctId, context);
/*      */             }
/*      */             
/*  731 */             if (securityToken == null) {
/*  732 */               logger.log(Level.SEVERE, LogStringsMessages.WSS_1358_UNABLETO_LOCATE_SCT_TOKEN());
/*  733 */               throw new KeySelectorException("SCT Token with Id " + sctId + "not found");
/*      */             } 
/*  735 */             tokenCache.put(sctId, securityToken);
/*      */           } 
/*      */ 
/*      */           
/*  739 */           if (securityToken instanceof SecurityContextToken) {
/*      */             
/*  741 */             if (isPolicyRecipient && inferredSignaturePolicy != null) {
/*  742 */               MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/*  743 */               SecureConversationTokenKeyBinding sctBinding = new SecureConversationTokenKeyBinding();
/*  744 */               if (inferredKB == null) {
/*  745 */                 inferredSignaturePolicy.setKeyBinding((MLSPolicy)sctBinding);
/*  746 */               } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  747 */                 ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)sctBinding);
/*      */               } 
/*      */             } 
/*  750 */             returnKey = resolveSCT(wssContext, (SecurityContextTokenImpl)securityToken, purpose);
/*      */           } else {
/*      */             
/*  753 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1359_INVALID_VALUETYPE_NON_SC_TTOKEN());
/*  754 */             throw new KeySelectorException("Incorrect ValueType: http://schemas.xmlsoap.org/ws/2005/02/sc/sct, specified for a Non SCT Token");
/*      */           }
/*      */         
/*  757 */         } else if (null == valueType) {
/*      */           SecurityContextTokenImpl securityContextTokenImpl;
/*      */ 
/*      */ 
/*      */           
/*  762 */           String wsuId = SecurableSoapMessage.getIdFromFragmentRef(uri);
/*  763 */           SecurityToken token = (SecurityToken)tokenCache.get(wsuId);
/*      */           
/*  765 */           if (token == null) {
/*  766 */             token = resolveToken(wsuId, context);
/*  767 */             if (token == null) {
/*  768 */               securityContextTokenImpl = SecurityUtil.locateBySCTId(wssContext, uri);
/*      */             }
/*      */             
/*  771 */             if (securityContextTokenImpl == null) {
/*  772 */               logger.log(Level.SEVERE, LogStringsMessages.WSS_1357_UNABLETO_LOCATE_TOKEN());
/*  773 */               throw new KeySelectorException("Token with Id " + wsuId + "not found");
/*      */             } 
/*  775 */             tokenCache.put(wsuId, securityContextTokenImpl);
/*      */           } 
/*      */ 
/*      */           
/*  779 */           if (securityContextTokenImpl instanceof X509SecurityToken) {
/*  780 */             if (isPolicyRecipient && inferredSignaturePolicy != null) {
/*  781 */               MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/*  782 */               AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  783 */               x509Binding.setReferenceType("Direct");
/*  784 */               if (inferredKB == null) {
/*  785 */                 inferredSignaturePolicy.setKeyBinding((MLSPolicy)x509Binding);
/*  786 */               } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  787 */                 ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  788 */                 isSymmetric = true;
/*  789 */               } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  790 */                 DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  791 */                 if (dktBind.getOriginalKeyBinding() == null) {
/*  792 */                   dktBind.setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  793 */                 } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  794 */                   dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*  795 */                   isSymmetric = true;
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */             
/*  800 */             returnKey = resolveX509Token(wssContext, (X509SecurityToken)securityContextTokenImpl, purpose, isSymmetric);
/*      */           }
/*  802 */           else if (securityContextTokenImpl instanceof EncryptedKeyToken) {
/*      */             
/*  804 */             if (isPolicyRecipient && inferredSignaturePolicy != null) {
/*  805 */               MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/*  806 */               SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/*  807 */               AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  808 */               skBinding.setKeyBinding((MLSPolicy)x509Binding);
/*      */               
/*  810 */               if (inferredKB == null) {
/*  811 */                 inferredSignaturePolicy.setKeyBinding((MLSPolicy)skBinding);
/*  812 */               } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  813 */                 (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  814 */                 ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)skBinding);
/*      */               } 
/*      */             } 
/*      */             
/*  818 */             KeyInfoHeaderBlock kiHB = ((EncryptedKeyToken)securityContextTokenImpl).getKeyInfo();
/*  819 */             SecurityTokenReference sectr = kiHB.getSecurityTokenReference(0);
/*  820 */             SOAPElement se = sectr.getAsSoapElement();
/*  821 */             ReferenceElement refElem = sectr.getReference();
/*  822 */             Key privKey = resolve(se, context, KeySelector.Purpose.SIGN);
/*      */             
/*  824 */             Element cipherData = ((EncryptedKeyToken)securityContextTokenImpl).getAsSoapElement().getChildElements(new QName("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc")).next();
/*  825 */             String cipherValue = cipherData.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue").item(0).getTextContent();
/*  826 */             byte[] decodedCipher = Base64.decode(cipherValue);
/*  827 */             byte[] ekSha1 = MessageDigest.getInstance("SHA-1").digest(decodedCipher);
/*      */             
/*  829 */             String encEkSha1 = Base64.encode(ekSha1);
/*  830 */             wssContext.setExtraneousProperty("EKSHA1Value", encEkSha1);
/*      */             
/*  832 */             returnKey = ((EncryptedKeyToken)securityContextTokenImpl).getSecretKey(privKey, encAlgo);
/*  833 */             wssContext.setExtraneousProperty("SecretKeyValue", returnKey);
/*      */           }
/*  835 */           else if (securityContextTokenImpl instanceof SecurityContextToken) {
/*      */             
/*  837 */             if (isPolicyRecipient && inferredSignaturePolicy != null) {
/*  838 */               MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/*  839 */               SecureConversationTokenKeyBinding sctBinding = new SecureConversationTokenKeyBinding();
/*  840 */               if (inferredKB == null) {
/*  841 */                 inferredSignaturePolicy.setKeyBinding((MLSPolicy)sctBinding);
/*  842 */               } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  843 */                 ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)sctBinding);
/*      */               } 
/*      */             } 
/*  846 */             returnKey = resolveSCT(wssContext, securityContextTokenImpl, purpose);
/*      */           }
/*  848 */           else if (securityContextTokenImpl instanceof DerivedKeyTokenHeaderBlock) {
/*  849 */             if (isPolicyRecipient && inferredSignaturePolicy != null) {
/*  850 */               MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/*  851 */               DerivedTokenKeyBinding dtkBinding = new DerivedTokenKeyBinding();
/*  852 */               if (inferredKB == null) {
/*  853 */                 inferredSignaturePolicy.setKeyBinding((MLSPolicy)dtkBinding);
/*  854 */               } else if (!PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*      */ 
/*      */                 
/*  857 */                 logger.log(Level.SEVERE, LogStringsMessages.WSS_1360_INVALID_DERIVED_KEY_TOKEN());
/*  858 */                 throw new XWSSecurityException("A derived Key Token should be a top level key binding");
/*      */               } 
/*      */             } 
/*  861 */             returnKey = resolveDKT(context, (DerivedKeyTokenHeaderBlock)securityContextTokenImpl);
/*      */           } else {
/*      */             
/*  864 */             String message = " Cannot Resolve URI " + uri;
/*  865 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1307_UNSUPPORTED_DIRECTREF_MECHANISM(message), new Object[] { message });
/*  866 */             KeySelectorException xwsse = new KeySelectorException(message);
/*      */             
/*  868 */             throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_SECURITY_TOKEN_UNAVAILABLE, xwsse.getMessage(), xwsse);
/*      */           } 
/*      */         } else {
/*      */           
/*  872 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1307_UNSUPPORTED_DIRECTREF_MECHANISM(new Object[] { ((DirectReference)refElement).getValueType() }));
/*  873 */           throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "unsupported directreference ValueType " + ((DirectReference)refElement).getValueType(), null);
/*      */         }
/*      */       
/*  876 */       } else if (refElement instanceof X509IssuerSerial) {
/*  877 */         if (keyBinding != null) {
/*  878 */           keyBinding.setReferenceType("IssuerSerialNumber");
/*      */         }
/*  880 */         X509IssuerSerial xisElement = (X509IssuerSerial)refElement;
/*      */         
/*  882 */         BigInteger serialNumber = xisElement.getSerialNumber();
/*  883 */         String issuerName = xisElement.getIssuerName();
/*  884 */         if (isPolicyRecipient && inferredSignaturePolicy != null) {
/*  885 */           MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/*  886 */           AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  887 */           x509Binding.setReferenceType("IssuerSerialNumber");
/*  888 */           if (inferredKB == null) {
/*  889 */             inferredSignaturePolicy.setKeyBinding((MLSPolicy)x509Binding);
/*  890 */           } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  891 */             ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  892 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  893 */             DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  894 */             if (dktBind.getOriginalKeyBinding() == null) {
/*  895 */               dktBind.setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  896 */             } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  897 */               dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*      */             } 
/*      */           } 
/*      */         } 
/*  901 */         if (purpose == KeySelector.Purpose.VERIFY) {
/*  902 */           wssContext.setExtraneousProperty("requester.serial", serialNumber);
/*  903 */           wssContext.setExtraneousProperty("requester.issuername", issuerName);
/*      */           
/*  905 */           X509Certificate cert = wssContext.getSecurityEnvironment().getCertificate(wssContext.getExtraneousProperties(), serialNumber, issuerName);
/*      */           
/*  907 */           wssContext.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(wssContext), cert);
/*      */           
/*  909 */           returnKey = cert.getPublicKey();
/*      */         }
/*  911 */         else if (purpose == KeySelector.Purpose.SIGN) {
/*  912 */           returnKey = wssContext.getSecurityEnvironment().getPrivateKey(wssContext.getExtraneousProperties(), serialNumber, issuerName);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  917 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1308_UNSUPPORTED_REFERENCE_MECHANISM());
/*  918 */         KeySelectorException xwsse = new KeySelectorException("Key reference mechanism not supported");
/*      */ 
/*      */         
/*  921 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_UNSUPPORTED_SECURITY_TOKEN, xwsse.getMessage(), xwsse);
/*      */       } 
/*      */       
/*  924 */       return returnKey;
/*  925 */     } catch (XWSSecurityException xwsExp) {
/*  926 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1353_UNABLE_RESOLVE_KEY_INFORMATION(), (Throwable)xwsExp);
/*  927 */       throw new KeySelectorException(xwsExp);
/*  928 */     } catch (MarshalException me) {
/*  929 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1353_UNABLE_RESOLVE_KEY_INFORMATION(), me);
/*  930 */       throw new KeySelectorException(me);
/*  931 */     } catch (Exception ex) {
/*  932 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1353_UNABLE_RESOLVE_KEY_INFORMATION(), ex);
/*  933 */       throw new KeySelectorException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Key resolveSamlAssertion(XMLCryptoContext dsigContext, Element samlAssertion, KeySelector.Purpose purpose, String assertionID) throws MarshalException, KeySelectorException, XWSSecurityException {
/*  941 */     FilterProcessingContext context = (FilterProcessingContext)dsigContext.get("http://wss.sun.com#processingContext");
/*  942 */     String samlSignatureResolved = (String)context.getExtraneousProperty("Saml_Signature_resolved");
/*  943 */     Element elem = null;
/*  944 */     Key key = (Key)context.getSamlIdVSKeyCache().get(assertionID);
/*  945 */     if (key != null) {
/*  946 */       return key;
/*      */     }
/*      */     
/*  949 */     if (samlAssertion == null) {
/*  950 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1355_UNABLETO_RESOLVE_SAML_ASSERTION());
/*  951 */       throw new XWSSecurityException("Cannot Resolve SAML Assertion");
/*      */     } 
/*      */ 
/*      */     
/*  955 */     if (purpose == KeySelector.Purpose.VERIFY || "false".equals(samlSignatureResolved)) {
/*  956 */       NodeList nl = samlAssertion.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
/*      */       
/*  958 */       if (nl.getLength() == 0) {
/*  959 */         XWSSecurityException e = new XWSSecurityException("Unsigned SAML Assertion encountered");
/*  960 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1309_SAML_SIGNATURE_VERIFY_FAILED(), (Throwable)e);
/*  961 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Exception during Signature verfication in SAML Assertion", e);
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  966 */       boolean topLevelSigElement = false;
/*  967 */       int returnSigNodeIndex = 0;
/*      */       
/*  969 */       for (int i = 0; i < nl.getLength(); ) {
/*  970 */         if (nl.item(i).getParentNode().getParentNode().getLocalName().equals("Advice")) {
/*      */           i++; continue;
/*      */         } 
/*  973 */         topLevelSigElement = true;
/*  974 */         returnSigNodeIndex = i;
/*      */       } 
/*      */ 
/*      */       
/*  978 */       if (topLevelSigElement) {
/*  979 */         elem = (Element)nl.item(returnSigNodeIndex);
/*      */       } else {
/*  981 */         XWSSecurityException e = new XWSSecurityException("Unsigned SAML Assertion encountered");
/*  982 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1309_SAML_SIGNATURE_VERIFY_FAILED(), (Throwable)e);
/*  983 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Exception during Signature verfication in SAML Assertion", e);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  990 */       SignaturePolicy policy = (SignaturePolicy)context.getInferredPolicy();
/*      */       
/*  992 */       AuthenticationTokenPolicy.SAMLAssertionBinding keyBinding = null;
/*      */       
/*  994 */       if (policy != null) {
/*  995 */         keyBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)policy.newSAMLAssertionKeyBinding();
/*      */       }
/*      */       
/*      */       try {
/*  999 */         if (!SignatureProcessor.verifySignature(elem, context)) {
/* 1000 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1310_SAML_SIGNATURE_INVALID());
/* 1001 */           throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "SAML Assertion has invalid Signature", new Exception("SAML Assertion has invalid Signature"));
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/* 1007 */       catch (XWSSecurityException ex) {
/* 1008 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1310_SAML_SIGNATURE_INVALID());
/* 1009 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "SAML Assertion has invalid Signature", ex);
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1016 */     if ("false".equals(samlSignatureResolved)) {
/* 1017 */       context.setExtraneousProperty("Saml_Signature_resolved", "true");
/*      */     }
/*      */     
/* 1020 */     Element keyInfoElem = AssertionUtil.getSubjectConfirmationKeyInfo(samlAssertion);
/*      */     
/* 1022 */     KeyInfo keyInfo = KeyInfoFactory.getInstance().unmarshalKeyInfo(new DOMStructure(keyInfoElem));
/* 1023 */     List keyInfoList = keyInfo.getContent();
/* 1024 */     Iterator content = keyInfoList.iterator();
/* 1025 */     if (content.hasNext()) {
/* 1026 */       Object data = content.next();
/* 1027 */       if (data instanceof KeyName) {
/* 1028 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1361_UNSUPPORTED_KEY_NAME_SAML());
/* 1029 */         throw new XWSSecurityException("Unsupported KeyName under SAML SubjectConfirmation");
/* 1030 */       }  if (data instanceof KeyValue) {
/* 1031 */         key = resolveKeyValue(context, (KeyValue)data, purpose);
/*      */       }
/* 1033 */       else if (data instanceof X509Data) {
/* 1034 */         key = resolveSAMLX509Data(context, (X509Data)data, purpose);
/*      */       }
/* 1036 */       else if (data instanceof DOMStructure) {
/*      */         
/* 1038 */         SOAPElement reference = (SOAPElement)((DOMStructure)data).getNode();
/* 1039 */         if (isSecurityTokenReference(reference)) {
/* 1040 */           key = resolve(reference, dsigContext, purpose);
/*      */         }
/* 1042 */         else if (SecurityUtil.isBinarySecret(reference)) {
/* 1043 */           BinarySecret bs = null;
/*      */           try {
/* 1045 */             bs = WSTrustElementFactory.newInstance().createBinarySecret(reference);
/* 1046 */           } catch (WSTrustException ex) {
/* 1047 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1362_EXCEPTION_WS_TRUST_CREATING_BINARY_SECRET(), (Throwable)ex);
/* 1048 */             throw new XWSSecurityException(ex);
/*      */           } 
/*      */           
/* 1051 */           if (bs.getType() == null || bs.getType().equals("http://schemas.xmlsoap.org/ws/2005/02/trust/SymmetricKey")) {
/* 1052 */             String algo = "AES";
/* 1053 */             if (context.getAlgorithmSuite() != null) {
/* 1054 */               algo = SecurityUtil.getSecretKeyAlgorithm(context.getAlgorithmSuite().getEncryptionAlgorithm());
/*      */             }
/* 1056 */             key = new SecretKeySpec(bs.getRawValue(), algo);
/*      */           } else {
/*      */             
/* 1059 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1312_UNSUPPORTED_KEYINFO());
/* 1060 */             throw new KeySelectorException("Unsupported wst:BinarySecret Type");
/*      */           } 
/* 1062 */         } else if (SecurityUtil.isEncryptedKey(reference)) {
/* 1063 */           EncryptedKeyToken ekToken = new EncryptedKeyToken(reference);
/* 1064 */           KeyInfoHeaderBlock kiHB = ekToken.getKeyInfo();
/*      */           
/* 1066 */           if (kiHB.containsSecurityTokenReference()) {
/*      */             
/* 1068 */             Key privKey = KeyResolver.processSTR(kiHB, false, context);
/*      */             
/* 1070 */             String dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
/* 1071 */             if (context.getAlgorithmSuite() != null) {
/* 1072 */               dataEncAlgo = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*      */             }
/* 1074 */             key = ekToken.getSecretKey(privKey, dataEncAlgo);
/*      */           } else {
/*      */             
/* 1077 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1312_UNSUPPORTED_KEYINFO());
/* 1078 */             throw new KeySelectorException("Unsupported Key Information Inside EncryptedKey");
/*      */           } 
/*      */         } else {
/*      */           
/* 1082 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1312_UNSUPPORTED_KEYINFO());
/* 1083 */           throw new KeySelectorException("Unsupported Key Information");
/*      */         } 
/*      */       } else {
/*      */         
/* 1087 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1312_UNSUPPORTED_KEYINFO());
/* 1088 */         throw new KeySelectorException("Unsupported Key Information");
/*      */       } 
/*      */     } 
/* 1091 */     context.getSamlIdVSKeyCache().put(assertionID, key);
/* 1092 */     context.setExtraneousProperty("incoming_saml_assertion", samlAssertion);
/*      */ 
/*      */     
/*      */     try {
/* 1096 */       context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(context), AssertionUtil.fromElement(samlAssertion));
/*      */     }
/* 1098 */     catch (SAMLException ex) {}
/*      */ 
/*      */ 
/*      */     
/* 1102 */     return key;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Key resolveKeyValue(FilterProcessingContext context, KeyValue keyValue, KeySelector.Purpose purpose) throws KeySelectorException {
/*      */     try {
/* 1109 */       if (purpose == KeySelector.Purpose.VERIFY)
/* 1110 */         return keyValue.getPublicKey(); 
/* 1111 */       if (purpose == KeySelector.Purpose.SIGN) {
/* 1112 */         return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), keyValue.getPublicKey(), true);
/*      */       }
/*      */     }
/* 1115 */     catch (Exception e) {
/* 1116 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1313_ILLEGAL_KEY_VALUE(""), e.getMessage());
/* 1117 */       throw new KeySelectorException(e);
/*      */     } 
/* 1119 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Key resolveX509Data(FilterProcessingContext context, X509Data x509Data, KeySelector.Purpose purpose) throws KeySelectorException {
/* 1127 */     X509Certificate cert = null;
/*      */     
/*      */     try {
/* 1130 */       List data = x509Data.getContent();
/* 1131 */       Iterator iterator = data.iterator();
/* 1132 */       while (iterator.hasNext()) {
/* 1133 */         Object content = iterator.next();
/* 1134 */         if (content instanceof X509Certificate) {
/*      */           
/* 1136 */           cert = (X509Certificate)content;
/* 1137 */           if (purpose == KeySelector.Purpose.VERIFY) {
/*      */             
/* 1139 */             context.getSecurityEnvironment().validateCertificate(cert, context.getExtraneousProperties());
/* 1140 */             context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(context), cert);
/*      */             
/* 1142 */             return cert.getPublicKey();
/* 1143 */           }  if (purpose == KeySelector.Purpose.SIGN)
/* 1144 */             return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), cert); 
/*      */           continue;
/*      */         } 
/* 1147 */         if (content instanceof byte[]) {
/* 1148 */           byte[] ski = (byte[])content;
/* 1149 */           if (purpose == KeySelector.Purpose.VERIFY) {
/*      */             
/* 1151 */             cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), ski);
/* 1152 */             context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(context), cert);
/*      */             
/* 1154 */             return cert.getPublicKey();
/* 1155 */           }  if (purpose == KeySelector.Purpose.SIGN)
/* 1156 */             return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), ski); 
/*      */           continue;
/*      */         } 
/* 1159 */         if (content instanceof String) {
/* 1160 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1312_UNSUPPORTED_KEYINFO());
/* 1161 */           throw new KeySelectorException("X509SubjectName child element of X509Data is not yet supported by our implementation");
/*      */         } 
/* 1163 */         if (content instanceof X509IssuerSerial) {
/* 1164 */           X509IssuerSerial xis = (X509IssuerSerial)content;
/* 1165 */           if (purpose == KeySelector.Purpose.VERIFY) {
/*      */             
/* 1167 */             cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), xis.getSerialNumber(), xis.getIssuerName());
/*      */ 
/*      */             
/* 1170 */             context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(context), cert);
/*      */             
/* 1172 */             return cert.getPublicKey();
/* 1173 */           }  if (purpose == KeySelector.Purpose.SIGN) {
/* 1174 */             return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), xis.getSerialNumber(), xis.getIssuerName());
/*      */           }
/*      */           
/*      */           continue;
/*      */         } 
/* 1179 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1312_UNSUPPORTED_KEYINFO());
/* 1180 */         throw new KeySelectorException("Unsupported child element of X509Data encountered");
/*      */       
/*      */       }
/*      */     
/*      */     }
/* 1185 */     catch (Exception e) {
/* 1186 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1314_ILLEGAL_X_509_DATA(""), e.getMessage());
/* 1187 */       throw new KeySelectorException(e);
/*      */     } 
/* 1189 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Key resolveSAMLX509Data(FilterProcessingContext context, X509Data x509Data, KeySelector.Purpose purpose) throws KeySelectorException {
/* 1195 */     X509Certificate cert = null;
/*      */     
/*      */     try {
/* 1198 */       List data = x509Data.getContent();
/* 1199 */       Iterator iterator = data.iterator();
/* 1200 */       if (iterator.hasNext()) {
/* 1201 */         Object content = iterator.next();
/* 1202 */         if (content instanceof X509Certificate) {
/* 1203 */           cert = (X509Certificate)content;
/* 1204 */           context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(context), cert);
/*      */           
/* 1206 */           if (purpose == KeySelector.Purpose.VERIFY) {
/* 1207 */             return cert.getPublicKey();
/*      */           }
/* 1209 */           return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), cert);
/*      */         } 
/* 1211 */         if (content instanceof byte[]) {
/* 1212 */           byte[] ski = (byte[])content;
/*      */           
/* 1214 */           cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), ski);
/* 1215 */           context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(context), cert);
/*      */           
/* 1217 */           if (purpose == KeySelector.Purpose.VERIFY) {
/* 1218 */             return cert.getPublicKey();
/*      */           }
/* 1220 */           return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), cert);
/*      */         } 
/* 1222 */         if (content instanceof String) {
/* 1223 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1312_UNSUPPORTED_KEYINFO());
/* 1224 */           throw new KeySelectorException("X509SubjectName child element of X509Data is not yet supported by our implementation");
/*      */         } 
/* 1226 */         if (content instanceof X509IssuerSerial) {
/* 1227 */           X509IssuerSerial xis = (X509IssuerSerial)content;
/*      */           
/* 1229 */           cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), xis.getSerialNumber(), xis.getIssuerName());
/*      */           
/* 1231 */           context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(context), cert);
/*      */           
/* 1233 */           if (purpose == KeySelector.Purpose.VERIFY) {
/* 1234 */             return cert.getPublicKey();
/*      */           }
/* 1236 */           return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), cert);
/*      */         } 
/*      */ 
/*      */         
/* 1240 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1312_UNSUPPORTED_KEYINFO());
/* 1241 */         throw new KeySelectorException("Unsupported child element of X509Data encountered");
/*      */       }
/*      */     
/*      */     }
/* 1245 */     catch (Exception e) {
/* 1246 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1314_ILLEGAL_X_509_DATA(""), e.getMessage());
/* 1247 */       throw new KeySelectorException(e);
/*      */     } 
/* 1249 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Key resolveX509Token(FilterProcessingContext context, X509SecurityToken token, KeySelector.Purpose purpose, boolean isSymmetric) throws XWSSecurityException {
/* 1255 */     if (purpose == KeySelector.Purpose.VERIFY) {
/*      */ 
/*      */       
/* 1258 */       X509Certificate cert = token.getCertificate();
/* 1259 */       if (!isSymmetric) {
/* 1260 */         context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(context), cert);
/*      */       }
/*      */       
/* 1263 */       return cert.getPublicKey();
/* 1264 */     }  if (purpose == KeySelector.Purpose.SIGN || purpose == KeySelector.Purpose.DECRYPT) {
/* 1265 */       return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), token.getCertificate());
/*      */     }
/*      */     
/* 1268 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isSecurityTokenReference(Element reference) {
/* 1273 */     if ("SecurityTokenReference".equals(reference.getLocalName()))
/* 1274 */       return true; 
/* 1275 */     return false;
/*      */   }
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
/*      */   protected static SecurityToken resolveToken(final String uri, XMLCryptoContext context) throws URIReferenceException, XWSSecurityException {
/* 1290 */     URIDereferencer resolver = context.getURIDereferencer();
/* 1291 */     URIReference uriRef = new URIReference()
/*      */       {
/*      */         public String getURI() {
/* 1294 */           return uri;
/*      */         }
/*      */         
/*      */         public String getType() {
/* 1298 */           return null;
/*      */         }
/*      */       };
/*      */     
/* 1302 */     FilterProcessingContext wssContext = (FilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/*      */ 
/*      */     
/* 1305 */     SecurityPolicy securityPolicy = wssContext.getSecurityPolicy();
/*      */     
/* 1307 */     boolean isBSP = false;
/*      */     
/* 1309 */     if (securityPolicy != null) {
/* 1310 */       if (PolicyTypeUtil.messagePolicy(securityPolicy)) {
/* 1311 */         isBSP = ((MessagePolicy)securityPolicy).isBSP();
/*      */       } else {
/* 1313 */         isBSP = ((WSSPolicy)securityPolicy).isBSP();
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1319 */       NodeSetData set = (NodeSetData)resolver.dereference(uriRef, context);
/* 1320 */       Iterator<Node> itr = set.iterator();
/* 1321 */       while (itr.hasNext()) {
/* 1322 */         Node node = itr.next();
/* 1323 */         if ("BinarySecurityToken".equals(node.getLocalName())) {
/* 1324 */           X509SecurityToken token = new X509SecurityToken((SOAPElement)node, isBSP);
/* 1325 */           X509Certificate cert = null;
/*      */           try {
/* 1327 */             cert = token.getCertificate();
/* 1328 */           } catch (XWSSecurityException xwe) {
/* 1329 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1363_INVALID_SECURITY_TOKEN());
/* 1330 */             throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "A Invalid security token was provided ", xwe);
/*      */           } 
/*      */           
/* 1333 */           if (!wssContext.getSecurityEnvironment().validateCertificate(cert, wssContext.getExtraneousProperties())) {
/* 1334 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1364_UNABLETO_VALIDATE_CERTIFICATE());
/* 1335 */             throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "Certificate validation failed", null);
/*      */           } 
/*      */           
/* 1338 */           return (SecurityToken)token;
/* 1339 */         }  if ("EncryptedKey".equals(node.getLocalName()))
/* 1340 */           return (SecurityToken)new EncryptedKeyToken((SOAPElement)node); 
/* 1341 */         if ("SecurityContextToken".equals(node.getLocalName()))
/* 1342 */           return (SecurityToken)new SecurityContextTokenImpl((SOAPElement)node); 
/* 1343 */         if ("DerivedKeyToken".equals(node.getLocalName())) {
/* 1344 */           return (SecurityToken)new DerivedKeyTokenHeaderBlock((SOAPElement)node);
/*      */         }
/*      */       }
/*      */     
/* 1348 */     } catch (URIReferenceException ure) {
/* 1349 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1304_FC_SECURITY_TOKEN_UNAVAILABLE(), ure);
/* 1350 */       throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_SECURITY_TOKEN_UNAVAILABLE, "Referenced Security Token could not be retrieved", ure);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1356 */     logger.log(Level.SEVERE, LogStringsMessages.WSS_1305_UN_SUPPORTED_SECURITY_TOKEN());
/* 1357 */     throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_UNSUPPORTED_SECURITY_TOKEN, "A Unsupported token was provided ", null);
/*      */   }
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
/*      */   private static Element resolveSAMLToken(SecurityTokenReference tokenRef, String assertionId, FilterProcessingContext context) throws XWSSecurityException {
/* 1370 */     Element tokenElement = context.getIssuedSAMLToken();
/* 1371 */     if (tokenElement != null) {
/* 1372 */       context.setExtraneousProperty("Saml_Signature_resolved", "false");
/*      */     }
/* 1374 */     if (tokenElement == null) {
/* 1375 */       if (tokenRef.getSamlAuthorityBinding() != null) {
/* 1376 */         tokenElement = context.getSecurityEnvironment().locateSAMLAssertion(context.getExtraneousProperties(), tokenRef.getSamlAuthorityBinding(), assertionId, context.getSOAPMessage().getSOAPPart());
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/* 1384 */         tokenElement = SAMLUtil.locateSamlAssertion(assertionId, context.getSOAPMessage().getSOAPPart());
/*      */         
/* 1386 */         if (!"true".equals(context.getExtraneousProperty("Saml_Signature_resolved")) || "false".equals(context.getExtraneousProperty("Saml_Signature_resolved")))
/*      */         {
/* 1388 */           context.setExtraneousProperty("Saml_Signature_resolved", "false");
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1396 */       if ("EncryptedData".equals(tokenElement.getLocalName()));
/*      */     
/*      */     }
/* 1399 */     catch (Exception e) {
/* 1400 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1355_UNABLETO_RESOLVE_SAML_ASSERTION(), e);
/* 1401 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/* 1404 */     return tokenElement;
/*      */   }
/*      */   
/*      */   private static void addAuthorityId(Element assertion, FilterProcessingContext fp) {
/* 1408 */     String issuer = null;
/*      */     
/* 1410 */     SignaturePolicy ep = (SignaturePolicy)fp.getInferredPolicy();
/* 1411 */     if (ep != null) {
/* 1412 */       AuthenticationTokenPolicy.SAMLAssertionBinding kb = (AuthenticationTokenPolicy.SAMLAssertionBinding)ep.newSAMLAssertionKeyBinding();
/*      */ 
/*      */       
/* 1415 */       if (assertion.getAttributeNode("ID") != null) {
/*      */         
/* 1417 */         NodeList nl = assertion.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "Issuer");
/*      */         
/* 1419 */         Element issuerElement = (Element)nl.item(0);
/* 1420 */         issuer = issuerElement.getTextContent();
/*      */       }
/*      */       else {
/*      */         
/* 1424 */         issuer = assertion.getAttribute("Issuer");
/*      */       } 
/* 1426 */       kb.setAuthorityIdentifier(issuer);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Key resolveSCT(FilterProcessingContext context, SecurityContextTokenImpl token, KeySelector.Purpose purpose) throws XWSSecurityException {
/* 1439 */     context.setExtraneousProperty("Incoming_SCT", token);
/*      */     
/* 1441 */     String scId = token.getSCId();
/* 1442 */     IssuedTokenContext ctx = null;
/*      */ 
/*      */     
/* 1445 */     String protocol = context.getWSSCVersion(context.getSecurityPolicyVersion());
/* 1446 */     if (context.isClient()) {
/* 1447 */       DefaultSCTokenConfiguration defaultSCTokenConfiguration = new DefaultSCTokenConfiguration(protocol, scId, !context.isExpired(), !context.isInboundMessage());
/* 1448 */       ctx = IssuedTokenManager.getInstance().createIssuedTokenContext((IssuedTokenConfiguration)defaultSCTokenConfiguration, null);
/*      */       try {
/* 1450 */         IssuedTokenManager.getInstance().getIssuedToken(ctx);
/* 1451 */       } catch (WSTrustException e) {
/* 1452 */         throw new XWSSecurityException(e);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1471 */       ctx = ((SessionManager)context.getExtraneousProperty("SessionManager")).getSecurityContext(scId, !context.isExpired());
/* 1472 */       URI sctId = null;
/* 1473 */       String sctIns = null;
/* 1474 */       String wsuId = null;
/* 1475 */       SecurityContextToken sct = (SecurityContextToken)ctx.getSecurityToken();
/* 1476 */       if (sct != null) {
/* 1477 */         sctId = sct.getIdentifier();
/* 1478 */         sctIns = sct.getInstance();
/* 1479 */         wsuId = sct.getWsuId();
/*      */       } else {
/* 1481 */         SecurityContextTokenInfo sctInfo = ctx.getSecurityContextTokenInfo();
/* 1482 */         sctId = URI.create(sctInfo.getIdentifier());
/* 1483 */         sctIns = sctInfo.getInstance();
/* 1484 */         wsuId = sctInfo.getExternalId();
/*      */       } 
/* 1486 */       ctx.setSecurityToken((Token)WSTrustElementFactory.newInstance(protocol).createSecurityContextToken(sctId, sctIns, wsuId));
/*      */     } 
/*      */     
/* 1489 */     if (ctx == null) {
/* 1490 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1365_UNABLETO_LOCATE_SECURE_CONVERSATION_SESSION());
/* 1491 */       throw new XWSSecurityException("Could not locate SecureConversation session for Id:" + scId);
/*      */     } 
/*      */     
/* 1494 */     byte[] proofKey = null;
/* 1495 */     SecurityContextToken scToken = (SecurityContextToken)ctx.getSecurityToken();
/* 1496 */     if (scToken.getInstance() != null) {
/* 1497 */       if (context.isExpired()) {
/* 1498 */         proofKey = ctx.getProofKey();
/*      */       } else {
/* 1500 */         SecurityContextTokenInfo sctInstanceInfo = ctx.getSecurityContextTokenInfo();
/* 1501 */         proofKey = sctInstanceInfo.getInstanceSecret(scToken.getInstance());
/*      */       } 
/*      */     } else {
/* 1504 */       proofKey = ctx.getProofKey();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1509 */     if (proofKey == null) {
/* 1510 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1365_UNABLETO_LOCATE_SECURE_CONVERSATION_SESSION());
/* 1511 */       throw new XWSSecurityException("Could not locate SecureConversation session for Id:" + scId);
/*      */     } 
/*      */     
/* 1514 */     String algo = "AES";
/* 1515 */     if (context.getAlgorithmSuite() != null) {
/* 1516 */       algo = SecurityUtil.getSecretKeyAlgorithm(context.getAlgorithmSuite().getEncryptionAlgorithm());
/*      */     }
/* 1518 */     SecretKeySpec key = new SecretKeySpec(proofKey, algo);
/* 1519 */     if (purpose == KeySelector.Purpose.VERIFY) {
/* 1520 */       Subject subj = ctx.getRequestorSubject();
/* 1521 */       if (subj != null)
/*      */       {
/* 1523 */         if (context.getExtraneousProperty("SCBOOTSTRAP_CRED_IN_SUBJ") == null) {
/*      */           
/* 1525 */           context.getSecurityEnvironment().updateOtherPartySubject(SecurityUtil.getSubject(context.getExtraneousProperties()), subj);
/*      */           
/* 1527 */           context.getExtraneousProperties().put("SCBOOTSTRAP_CRED_IN_SUBJ", "true");
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1532 */     return key;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Key resolveDKT(XMLCryptoContext context, DerivedKeyTokenHeaderBlock token) throws XWSSecurityException {
/* 1538 */     FilterProcessingContext wssContext = (FilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/* 1539 */     AlgorithmSuite algSuite = wssContext.getAlgorithmSuite();
/*      */     
/* 1541 */     String algorithm = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
/* 1542 */     if (algSuite != null) {
/* 1543 */       algorithm = algSuite.getEncryptionAlgorithm();
/*      */     }
/*      */     try {
/* 1546 */       SecurityTokenReference sectr = token.getDerivedKeyElement();
/* 1547 */       SOAPElement se = sectr.getAsSoapElement();
/*      */       
/* 1549 */       Key encKey = resolve(se, context, KeySelector.Purpose.SIGN);
/* 1550 */       byte[] secret = encKey.getEncoded();
/*      */       
/* 1552 */       byte[] nonce = token.getNonce();
/* 1553 */       long length = token.getLength();
/* 1554 */       long offset = token.getOffset();
/* 1555 */       String label = token.getLabel();
/* 1556 */       DerivedKeyTokenImpl derivedKeyTokenImpl = new DerivedKeyTokenImpl(offset, length, secret, nonce, label);
/* 1557 */       String jceAlgo = SecurityUtil.getSecretKeyAlgorithm(algorithm);
/* 1558 */       Key returnKey = derivedKeyTokenImpl.generateSymmetricKey(jceAlgo);
/* 1559 */       return returnKey;
/* 1560 */     } catch (Exception e) {
/* 1561 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1366_UNABLE_GENERATE_SYMMETRIC_KEY_DKT(), e);
/* 1562 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\dsig\KeySelectorImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */