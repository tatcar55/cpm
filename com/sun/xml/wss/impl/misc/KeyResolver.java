/*      */ package com.sun.xml.wss.impl.misc;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;
/*      */ import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
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
/*      */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*      */ import com.sun.xml.wss.impl.MessageConstants;
/*      */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*      */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*      */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*      */ import com.sun.xml.wss.impl.XMLUtil;
/*      */ import com.sun.xml.wss.impl.dsig.SignatureProcessor;
/*      */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*      */ import com.sun.xml.wss.logging.LogStringsMessages;
/*      */ import com.sun.xml.wss.logging.impl.dsig.LogStringsMessages;
/*      */ import com.sun.xml.wss.saml.AssertionUtil;
/*      */ import com.sun.xml.wss.saml.util.SAMLUtil;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.math.BigInteger;
/*      */ import java.net.URI;
/*      */ import java.security.InvalidKeyException;
/*      */ import java.security.Key;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.HashMap;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.crypto.spec.SecretKeySpec;
/*      */ import javax.security.auth.Subject;
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
/*      */ public class KeyResolver
/*      */ {
/*  120 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
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
/*      */   public static Key getKey(KeyInfoHeaderBlock keyInfo, boolean sig, FilterProcessingContext context) throws XWSSecurityException {
/*      */     Key returnKey;
/*      */     try {
/*  147 */       SecurableSoapMessage secureMsg = context.getSecurableSoapMessage();
/*  148 */       if (keyInfo.containsSecurityTokenReference())
/*  149 */         return processSecurityTokenReference(keyInfo, sig, context); 
/*  150 */       if (keyInfo.containsKeyName()) {
/*  151 */         EncryptionPolicy policy = (EncryptionPolicy)context.getInferredPolicy();
/*      */         
/*  153 */         String keynameString = keyInfo.getKeyNameString(0);
/*  154 */         if (policy != null) {
/*  155 */           SymmetricKeyBinding keyBinding = null;
/*  156 */           keyBinding = (SymmetricKeyBinding)policy.newSymmetricKeyBinding();
/*  157 */           keyBinding.setKeyIdentifier(keynameString);
/*      */         } 
/*      */         
/*  160 */         returnKey = context.getSecurityEnvironment().getSecretKey(context.getExtraneousProperties(), keynameString, false);
/*      */ 
/*      */       
/*      */       }
/*  164 */       else if (keyInfo.containsKeyValue()) {
/*      */         
/*  166 */         returnKey = resolveKeyValue(secureMsg, keyInfo.getKeyValue(0), sig, context);
/*      */       }
/*  168 */       else if (keyInfo.containsX509Data()) {
/*      */         
/*  170 */         returnKey = resolveX509Data(secureMsg, keyInfo.getX509Data(0), sig, context);
/*  171 */       } else if (keyInfo.containsEncryptedKeyToken()) {
/*  172 */         EncryptedKeyToken token = keyInfo.getEncryptedKey(0);
/*  173 */         KeyInfoHeaderBlock kiHB = token.getKeyInfo();
/*  174 */         if (kiHB.containsSecurityTokenReference()) {
/*  175 */           SecurityTokenReference sectr = kiHB.getSecurityTokenReference(0);
/*      */         } else {
/*  177 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0335_UNSUPPORTED_REFERENCETYPE());
/*  178 */           throw new XWSSecurityException("Unsupported reference type under EncryptedKey");
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  183 */         String dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*  184 */         if (context.getAlgorithmSuite() != null) {
/*  185 */           dataEncAlgo = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*      */         }
/*  187 */         else if (context.getDataEncryptionAlgorithm() != null) {
/*  188 */           dataEncAlgo = context.getDataEncryptionAlgorithm();
/*      */         } 
/*      */         
/*  191 */         returnKey = token.getSecretKey(getKey(kiHB, false, context), dataEncAlgo);
/*  192 */       } else if (keyInfo.containsBinarySecret()) {
/*  193 */         BinarySecret bs = keyInfo.getBinarySecret(0);
/*      */         
/*  195 */         if (bs.getType() == null || bs.getType().equals("http://schemas.xmlsoap.org/ws/2005/02/trust/SymmetricKey")) {
/*  196 */           String algo = "AES";
/*  197 */           if (context.getAlgorithmSuite() != null) {
/*  198 */             algo = SecurityUtil.getSecretKeyAlgorithm(context.getAlgorithmSuite().getEncryptionAlgorithm());
/*      */           }
/*  200 */           returnKey = new SecretKeySpec(bs.getRawValue(), algo);
/*      */         } else {
/*  202 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0339_UNSUPPORTED_KEYINFO());
/*  203 */           throw new XWSSecurityException("Unsupported wst:BinarySecret Type");
/*      */         } 
/*      */       } else {
/*      */         
/*  207 */         XWSSecurityException xwsse = new XWSSecurityException("Support for processing information in the given ds:KeyInfo is not present");
/*      */ 
/*      */         
/*  210 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0339_UNSUPPORTED_KEYINFO(), (Throwable)xwsse);
/*  211 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, xwsse.getMessage(), xwsse);
/*      */       
/*      */       }
/*      */     
/*      */     }
/*  216 */     catch (WssSoapFaultException wsse) {
/*  217 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0284_WSS_SOAP_FAULT_EXCEPTION(), (Throwable)wsse);
/*  218 */       throw wsse;
/*  219 */     } catch (XWSSecurityException xwsse) {
/*  220 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0284_WSS_SOAP_FAULT_EXCEPTION(), (Throwable)xwsse);
/*  221 */       throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_SECURITY_TOKEN_UNAVAILABLE, xwsse.getMessage(), xwsse);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  227 */     if (returnKey == null) {
/*  228 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0600_ILLEGAL_TOKEN_REFERENCE());
/*      */       
/*  230 */       XWSSecurityException xwsse = new XWSSecurityException("Referenced security token could not be retrieved");
/*      */ 
/*      */       
/*  233 */       throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_SECURITY_TOKEN_UNAVAILABLE, xwsse.getMessage(), xwsse);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  239 */     return returnKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Key resolveSamlAssertion(SecurableSoapMessage secureMsg, Element samlAssertion, boolean sig, FilterProcessingContext context, String assertionID) throws XWSSecurityException {
/*      */     try {
/*  247 */       Key key = (Key)context.getSamlIdVSKeyCache().get(assertionID);
/*  248 */       String samlSignatureResolved = (String)context.getExtraneousProperty("Saml_Signature_resolved");
/*  249 */       if (key != null) {
/*  250 */         return key;
/*      */       }
/*      */ 
/*      */       
/*  254 */       if (samlAssertion == null) {
/*  255 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0235_FAILED_LOCATE_SAML_ASSERTION());
/*  256 */         throw new XWSSecurityException("Cannot Locate SAML Assertion");
/*      */       } 
/*      */       
/*  259 */       if ("false".equals(samlSignatureResolved)) {
/*  260 */         NodeList nl = samlAssertion.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
/*      */         
/*  262 */         if (nl.getLength() == 0) {
/*  263 */           XWSSecurityException e = new XWSSecurityException("Unsigned SAML Assertion encountered");
/*  264 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1309_SAML_SIGNATURE_VERIFY_FAILED(), (Throwable)e);
/*  265 */           throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY, "Exception during Signature verfication in SAML Assertion", e);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  270 */         SignaturePolicy policy = (SignaturePolicy)context.getInferredPolicy();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  278 */         Element elem = (Element)nl.item(0);
/*      */         
/*      */         try {
/*  281 */           if (!SignatureProcessor.verifySignature(elem, context)) {
/*  282 */             log.log(Level.SEVERE, LogStringsMessages.WSS_1310_SAML_SIGNATURE_INVALID());
/*  283 */             throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "SAML Assertion has invalid Signature", new Exception("SAML Assertion has invalid Signature"));
/*      */           
/*      */           }
/*      */ 
/*      */         
/*      */         }
/*  289 */         catch (XWSSecurityException ex) {
/*  290 */           log.log(Level.SEVERE, LogStringsMessages.WSS_1310_SAML_SIGNATURE_INVALID());
/*  291 */           throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_FAILED_AUTHENTICATION, "SAML Assertion has invalid Signature", ex);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  298 */       if ("false".equals(samlSignatureResolved)) {
/*  299 */         context.setExtraneousProperty("Saml_Signature_resolved", "true");
/*      */       }
/*      */       
/*  302 */       Element keyInfoElem = AssertionUtil.getSubjectConfirmationKeyInfo(samlAssertion);
/*      */       
/*  304 */       KeyInfoHeaderBlock keyInfo = new KeyInfoHeaderBlock(XMLUtil.convertToSoapElement(secureMsg.getSOAPPart(), keyInfoElem));
/*      */       
/*  306 */       key = getKey(keyInfo, sig, context);
/*  307 */       context.getSamlIdVSKeyCache().put(assertionID, key);
/*  308 */       return key;
/*  309 */     } catch (Exception e) {
/*  310 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0238_FAILED_RESOLVE_SAML_ASSERTION());
/*  311 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Key processSecurityTokenReference(KeyInfoHeaderBlock keyInfo, boolean sig, FilterProcessingContext context) throws XWSSecurityException {
/*  318 */     Key returnKey = null;
/*  319 */     HashMap<String, SecurityToken> tokenCache = context.getTokenCache();
/*  320 */     SecurableSoapMessage secureMsg = context.getSecurableSoapMessage();
/*  321 */     SecurityTokenReference str = keyInfo.getSecurityTokenReference(0);
/*  322 */     ReferenceElement refElement = str.getReference();
/*  323 */     EncryptionPolicy policy = (EncryptionPolicy)context.getInferredPolicy();
/*  324 */     EncryptionPolicy inferredEncryptionPolicy = null;
/*  325 */     boolean isWSITRecipient = (context.getMode() == 3);
/*      */     try {
/*  327 */       if (isWSITRecipient) {
/*  328 */         int i = context.getInferredSecurityPolicy().size() - 1;
/*  329 */         inferredEncryptionPolicy = (EncryptionPolicy)context.getInferredSecurityPolicy().get(i);
/*      */       } 
/*  331 */     } catch (Exception e) {
/*  332 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0239_FAILED_PROCESS_SECURITY_TOKEN_REFERENCE(), e);
/*  333 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  338 */     if (refElement instanceof KeyIdentifier) {
/*  339 */       KeyIdentifier keyId = (KeyIdentifier)refElement;
/*      */       
/*  341 */       if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier".equals(keyId.getValueType()) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3SubjectKeyIdentifier".equals(keyId.getValueType())) {
/*      */         
/*  343 */         if (policy != null) {
/*  344 */           AuthenticationTokenPolicy.X509CertificateBinding keyBinding = null;
/*  345 */           keyBinding = (AuthenticationTokenPolicy.X509CertificateBinding)policy.newX509CertificateKeyBinding();
/*  346 */           keyBinding.setReferenceType("Identifier");
/*      */         } 
/*  348 */         if (isWSITRecipient) {
/*  349 */           MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/*  350 */           AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  351 */           x509Binding.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier");
/*  352 */           x509Binding.setReferenceType("Identifier");
/*  353 */           if (inferredKB == null) {
/*  354 */             inferredEncryptionPolicy.setKeyBinding((MLSPolicy)x509Binding);
/*  355 */           } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  356 */             ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  357 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  358 */             DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  359 */             if (dktBind.getOriginalKeyBinding() == null) {
/*  360 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  361 */             } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  362 */               dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  367 */         if (sig) {
/*  368 */           returnKey = context.getSecurityEnvironment().getPublicKey(context.getExtraneousProperties(), getDecodedBase64EncodedData(keyId.getReferenceValue()));
/*      */         }
/*      */         else {
/*      */           
/*  372 */           returnKey = context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), getDecodedBase64EncodedData(keyId.getReferenceValue()));
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  377 */       else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1".equals(keyId.getValueType())) {
/*  378 */         if (policy != null) {
/*  379 */           AuthenticationTokenPolicy.X509CertificateBinding keyBinding = null;
/*  380 */           keyBinding = (AuthenticationTokenPolicy.X509CertificateBinding)policy.newX509CertificateKeyBinding();
/*  381 */           keyBinding.setReferenceType("Thumbprint");
/*      */         } 
/*  383 */         if (isWSITRecipient) {
/*  384 */           MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/*  385 */           AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  386 */           x509Binding.setValueType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1");
/*  387 */           x509Binding.setReferenceType("Identifier");
/*  388 */           if (inferredKB == null) {
/*  389 */             inferredEncryptionPolicy.setKeyBinding((MLSPolicy)x509Binding);
/*  390 */           } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  391 */             ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  392 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  393 */             DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  394 */             if (dktBind.getOriginalKeyBinding() == null) {
/*  395 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  396 */             } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  397 */               dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  402 */         if (sig) {
/*  403 */           returnKey = context.getSecurityEnvironment().getPublicKey(context.getExtraneousProperties(), getDecodedBase64EncodedData(keyId.getReferenceValue()), "Thumbprint");
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  410 */           returnKey = context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), getDecodedBase64EncodedData(keyId.getReferenceValue()), "Thumbprint");
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  418 */       else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1".equals(keyId.getValueType())) {
/*  419 */         if (isWSITRecipient) {
/*  420 */           MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/*  421 */           SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/*  422 */           AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  423 */           x509Binding.setReferenceType("Identifier");
/*  424 */           skBinding.setKeyBinding((MLSPolicy)x509Binding);
/*      */           
/*  426 */           if (inferredKB == null) {
/*  427 */             inferredEncryptionPolicy.setKeyBinding((MLSPolicy)skBinding);
/*  428 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  429 */             (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  430 */             ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)skBinding);
/*      */           } 
/*      */         } 
/*  433 */         String ekSha1RefValue = (String)context.getExtraneousProperty("EncryptedKeySHA1");
/*  434 */         Key secretKey = (Key)context.getExtraneousProperty("SecretKey");
/*  435 */         String keyRefValue = keyId.getReferenceValue();
/*  436 */         if (ekSha1RefValue != null && secretKey != null) {
/*  437 */           if (ekSha1RefValue.equals(keyRefValue))
/*  438 */             returnKey = secretKey; 
/*      */         } else {
/*  440 */           String message = "EncryptedKeySHA1 reference not correct";
/*  441 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0240_INVALID_ENCRYPTED_KEY_SHA_1_REFERENCE());
/*  442 */           throw new XWSSecurityException(message);
/*      */         }
/*      */       
/*  445 */       } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(keyId.getValueType()) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(keyId.getValueType())) {
/*      */ 
/*      */         
/*  448 */         if (policy != null) {
/*  449 */           AuthenticationTokenPolicy.SAMLAssertionBinding keyBinding = null;
/*  450 */           keyBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)policy.newSAMLAssertionKeyBinding();
/*  451 */           keyBinding.setReferenceType(keyId.getValueType());
/*      */         } 
/*  453 */         String assertionID = keyId.getDecodedReferenceValue();
/*  454 */         Element samlAssertion = resolveSAMLToken(str, assertionID, context);
/*  455 */         if (isWSITRecipient) {
/*  456 */           MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/*  457 */           IssuedTokenKeyBinding itkBinding = new IssuedTokenKeyBinding();
/*  458 */           if (inferredKB == null) {
/*  459 */             if (context.hasIssuedToken()) {
/*  460 */               inferredEncryptionPolicy.setKeyBinding((MLSPolicy)itkBinding);
/*      */             } else {
/*  462 */               inferredEncryptionPolicy.setKeyBinding((MLSPolicy)new AuthenticationTokenPolicy.SAMLAssertionBinding());
/*      */             } 
/*  464 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  465 */             (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  466 */             ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)itkBinding);
/*      */           } 
/*      */         } 
/*  469 */         returnKey = resolveSamlAssertion(secureMsg, samlAssertion, sig, context, assertionID);
/*  470 */         if (context.hasIssuedToken() && returnKey != null) {
/*  471 */           SecurityUtil.initInferredIssuedTokenContext(context, (Token)str, returnKey);
/*      */         }
/*      */       } else {
/*      */         
/*  475 */         if (policy != null) {
/*  476 */           AuthenticationTokenPolicy.SAMLAssertionBinding keyBinding = null;
/*  477 */           keyBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)policy.newSAMLAssertionKeyBinding();
/*      */         } 
/*  479 */         Element samlAssertion = null;
/*  480 */         String assertionID = keyId.getDecodedReferenceValue();
/*      */         try {
/*  482 */           samlAssertion = resolveSAMLToken(str, assertionID, context);
/*  483 */         } catch (Exception ex) {}
/*      */ 
/*      */         
/*  486 */         if (samlAssertion != null) {
/*  487 */           if (isWSITRecipient) {
/*  488 */             MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/*  489 */             IssuedTokenKeyBinding itkBinding = new IssuedTokenKeyBinding();
/*  490 */             if (inferredKB == null) {
/*  491 */               inferredEncryptionPolicy.setKeyBinding((MLSPolicy)itkBinding);
/*  492 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  493 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  494 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)itkBinding);
/*      */             } 
/*      */           } 
/*  497 */           returnKey = resolveSamlAssertion(secureMsg, samlAssertion, sig, context, assertionID);
/*  498 */           if (context.hasIssuedToken() && returnKey != null) {
/*  499 */             SecurityUtil.initInferredIssuedTokenContext(context, (Token)str, returnKey);
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  504 */           if (isWSITRecipient) {
/*  505 */             MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/*  506 */             AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  507 */             x509Binding.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier");
/*  508 */             x509Binding.setReferenceType("Identifier");
/*  509 */             if (inferredKB == null) {
/*  510 */               inferredEncryptionPolicy.setKeyBinding((MLSPolicy)x509Binding);
/*  511 */             } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  512 */               ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  513 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  514 */               DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  515 */               if (dktBind.getOriginalKeyBinding() == null) {
/*  516 */                 ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  517 */               } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  518 */                 dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/*  523 */           if (sig) {
/*  524 */             returnKey = context.getSecurityEnvironment().getPublicKey(context.getExtraneousProperties(), getDecodedBase64EncodedData(keyId.getReferenceValue()));
/*      */           }
/*      */           else {
/*      */             
/*  528 */             returnKey = context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), getDecodedBase64EncodedData(keyId.getReferenceValue()));
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       } 
/*  534 */     } else if (refElement instanceof DirectReference) {
/*  535 */       String uri = ((DirectReference)refElement).getURI();
/*      */ 
/*      */       
/*  538 */       AuthenticationTokenPolicy.X509CertificateBinding keyBinding = null;
/*  539 */       String valueType = ((DirectReference)refElement).getValueType();
/*  540 */       if ("http://schemas.xmlsoap.org/ws/2005/02/sc/dk".equals(valueType) || "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/dk".equals(valueType))
/*      */       {
/*      */         
/*  543 */         valueType = null;
/*      */       }
/*      */       
/*  546 */       if (policy != null) {
/*  547 */         keyBinding = (AuthenticationTokenPolicy.X509CertificateBinding)policy.newX509CertificateKeyBinding();
/*  548 */         keyBinding.setReferenceType("Direct");
/*  549 */         keyBinding.setValueType(valueType);
/*      */       } 
/*      */       
/*  552 */       if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(valueType) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1".equals(valueType)) {
/*      */         
/*  554 */         HashMap insertedX509Cache = context.getInsertedX509Cache();
/*  555 */         String wsuId = SecurableSoapMessage.getIdFromFragmentRef(uri);
/*  556 */         X509SecurityToken token = (X509SecurityToken)insertedX509Cache.get(wsuId);
/*  557 */         if (token == null)
/*  558 */           token = (X509SecurityToken)resolveToken(wsuId, context, secureMsg); 
/*  559 */         if (isWSITRecipient) {
/*  560 */           MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/*  561 */           AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  562 */           x509Binding.setReferenceType("Direct");
/*  563 */           x509Binding.setValueType(valueType);
/*  564 */           if (inferredKB == null) {
/*  565 */             inferredEncryptionPolicy.setKeyBinding((MLSPolicy)x509Binding);
/*  566 */           } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  567 */             ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  568 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  569 */             DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  570 */             if (dktBind.getOriginalKeyBinding() == null) {
/*  571 */               dktBind.setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  572 */             } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  573 */               dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*      */             } 
/*      */           } 
/*      */         } 
/*  577 */         returnKey = resolveX509Token(secureMsg, token, sig, context);
/*      */       }
/*  579 */       else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(valueType)) {
/*      */         
/*  581 */         String wsuId = SecurableSoapMessage.getIdFromFragmentRef(uri);
/*  582 */         SecurityToken token = resolveToken(wsuId, context, secureMsg);
/*      */         
/*  584 */         KeyInfoHeaderBlock kiHB = ((EncryptedKeyToken)token).getKeyInfo();
/*  585 */         SecurityTokenReference sectr = kiHB.getSecurityTokenReference(0);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  590 */         String dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*  591 */         if (context.getAlgorithmSuite() != null) {
/*  592 */           dataEncAlgo = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*      */         }
/*  594 */         else if (context.getDataEncryptionAlgorithm() != null) {
/*  595 */           dataEncAlgo = context.getDataEncryptionAlgorithm();
/*      */         } 
/*      */         
/*      */         try {
/*  599 */           Element cipherData = ((EncryptedKeyToken)token).getAsSoapElement().getChildElements(new QName("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc")).next();
/*  600 */           String cipherValue = cipherData.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue").item(0).getTextContent();
/*  601 */           byte[] decodedCipher = Base64.decode(cipherValue);
/*  602 */           byte[] ekSha1 = MessageDigest.getInstance("SHA-1").digest(decodedCipher);
/*  603 */           String encEkSha1 = Base64.encode(ekSha1);
/*  604 */           context.setExtraneousProperty("EKSHA1Value", encEkSha1);
/*      */         }
/*  606 */         catch (Exception e) {
/*  607 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0241_UNABLETO_SET_EKSHA_1_ON_CONTEXT(), e);
/*  608 */           throw new XWSSecurityException(e);
/*      */         } 
/*  610 */         if (isWSITRecipient) {
/*  611 */           MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/*  612 */           SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/*  613 */           AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  614 */           skBinding.setKeyBinding((MLSPolicy)x509Binding);
/*      */           
/*  616 */           if (inferredKB == null) {
/*  617 */             inferredEncryptionPolicy.setKeyBinding((MLSPolicy)skBinding);
/*  618 */           } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  619 */             ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  620 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  621 */             DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  622 */             if (dktBind.getOriginalKeyBinding() == null) {
/*  623 */               dktBind.setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  624 */             } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  625 */               dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*      */             } 
/*      */           } 
/*      */         } 
/*  629 */         returnKey = ((EncryptedKeyToken)token).getSecretKey(getKey(kiHB, sig, context), dataEncAlgo);
/*  630 */         context.setExtraneousProperty("SecretKeyValue", returnKey);
/*      */       }
/*  632 */       else if ("http://schemas.xmlsoap.org/ws/2005/02/sc/sct".equals(valueType) || "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct".equals(valueType)) {
/*      */         
/*  634 */         String sctId = SecurableSoapMessage.getIdFromFragmentRef(uri);
/*  635 */         SecurityToken securityToken = (SecurityToken)tokenCache.get(sctId);
/*      */         
/*  637 */         if (securityToken == null) {
/*  638 */           SecurityContextTokenImpl securityContextTokenImpl = SecurityUtil.locateBySCTId(context, uri);
/*  639 */           if (securityContextTokenImpl == null) {
/*  640 */             securityToken = resolveToken(sctId, context, secureMsg);
/*      */           }
/*  642 */           if (securityToken == null) {
/*  643 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0242_UNABLETO_LOCATE_SCT());
/*  644 */             throw new XWSSecurityException("SCT Token with Id " + sctId + "not found");
/*      */           } 
/*  646 */           tokenCache.put(sctId, securityToken);
/*      */         } 
/*      */ 
/*      */         
/*  650 */         if (securityToken instanceof SecurityContextToken) {
/*      */           
/*  652 */           byte[] proofKey = resolveSCT(context, (SecurityContextTokenImpl)securityToken, sig);
/*  653 */           String encAlgo = "AES";
/*  654 */           if (context.getAlgorithmSuite() != null) {
/*  655 */             encAlgo = SecurityUtil.getSecretKeyAlgorithm(context.getAlgorithmSuite().getEncryptionAlgorithm());
/*      */           }
/*  657 */           if (isWSITRecipient) {
/*  658 */             MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/*  659 */             SecureConversationTokenKeyBinding sctBinding = new SecureConversationTokenKeyBinding();
/*  660 */             if (inferredKB == null) {
/*  661 */               inferredEncryptionPolicy.setKeyBinding((MLSPolicy)sctBinding);
/*  662 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  663 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  664 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)sctBinding);
/*      */             } 
/*      */           } 
/*  667 */           returnKey = new SecretKeySpec(proofKey, encAlgo);
/*      */         } else {
/*      */           
/*  670 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0243_INVALID_VALUE_TYPE_NON_SCT_TOKEN());
/*  671 */           throw new XWSSecurityException("Incorrect ValueType: http://schemas.xmlsoap.org/ws/2005/02/sc/sct, specified for a Non SCT Token");
/*      */         }
/*      */       
/*  674 */       } else if (null == valueType) {
/*      */         SecurityToken securityToken;
/*  676 */         String wsuId = SecurableSoapMessage.getIdFromFragmentRef(uri);
/*  677 */         SecurityContextTokenImpl securityContextTokenImpl = SecurityUtil.locateBySCTId(context, wsuId);
/*  678 */         if (securityContextTokenImpl == null) {
/*  679 */           securityToken = resolveToken(wsuId, context, secureMsg);
/*      */         }
/*  681 */         if (securityToken instanceof X509SecurityToken) {
/*  682 */           if (isWSITRecipient) {
/*  683 */             MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/*  684 */             AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  685 */             x509Binding.setReferenceType("Direct");
/*  686 */             if (inferredKB == null) {
/*  687 */               inferredEncryptionPolicy.setKeyBinding((MLSPolicy)x509Binding);
/*  688 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  689 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  690 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*      */             } 
/*      */           } 
/*  693 */           returnKey = resolveX509Token(secureMsg, (X509SecurityToken)securityToken, sig, context);
/*  694 */         } else if (securityToken instanceof EncryptedKeyToken) {
/*      */           
/*  696 */           KeyInfoHeaderBlock kiHB = ((EncryptedKeyToken)securityToken).getKeyInfo();
/*  697 */           SecurityTokenReference sectr = kiHB.getSecurityTokenReference(0);
/*  698 */           ReferenceElement refElem = sectr.getReference();
/*  699 */           String dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*      */ 
/*      */ 
/*      */           
/*  703 */           if (context.getAlgorithmSuite() != null) {
/*  704 */             dataEncAlgo = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*      */           }
/*  706 */           else if (context.getDataEncryptionAlgorithm() != null) {
/*  707 */             dataEncAlgo = context.getDataEncryptionAlgorithm();
/*      */           } 
/*      */           
/*      */           try {
/*  711 */             Element cipherData = ((EncryptedKeyToken)securityToken).getAsSoapElement().getChildElements(new QName("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc")).next();
/*  712 */             String cipherValue = cipherData.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue").item(0).getTextContent();
/*  713 */             byte[] decodedCipher = Base64.decode(cipherValue);
/*  714 */             byte[] ekSha1 = MessageDigest.getInstance("SHA-1").digest(decodedCipher);
/*  715 */             String encEkSha1 = Base64.encode(ekSha1);
/*  716 */             context.setExtraneousProperty("EKSHA1Value", encEkSha1);
/*  717 */           } catch (Exception e) {
/*  718 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0241_UNABLETO_SET_EKSHA_1_ON_CONTEXT(), e);
/*  719 */             throw new XWSSecurityException(e);
/*      */           } 
/*  721 */           if (isWSITRecipient) {
/*  722 */             MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/*  723 */             SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/*  724 */             AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  725 */             skBinding.setKeyBinding((MLSPolicy)x509Binding);
/*      */             
/*  727 */             if (inferredKB == null) {
/*  728 */               inferredEncryptionPolicy.setKeyBinding((MLSPolicy)skBinding);
/*  729 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  730 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  731 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)skBinding);
/*      */             } 
/*      */           } 
/*  734 */           returnKey = ((EncryptedKeyToken)securityToken).getSecretKey(getKey(kiHB, sig, context), dataEncAlgo);
/*  735 */           context.setExtraneousProperty("SecretKeyValue", returnKey);
/*      */         }
/*  737 */         else if (securityToken instanceof SecurityContextToken) {
/*      */           
/*  739 */           byte[] proofKey = resolveSCT(context, (SecurityContextTokenImpl)securityToken, sig);
/*  740 */           String encAlgo = "AES";
/*  741 */           if (context.getAlgorithmSuite() != null) {
/*  742 */             encAlgo = SecurityUtil.getSecretKeyAlgorithm(context.getAlgorithmSuite().getEncryptionAlgorithm());
/*      */           }
/*  744 */           if (isWSITRecipient) {
/*  745 */             MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/*  746 */             SecureConversationTokenKeyBinding sctBinding = new SecureConversationTokenKeyBinding();
/*  747 */             if (inferredKB == null) {
/*  748 */               inferredEncryptionPolicy.setKeyBinding((MLSPolicy)sctBinding);
/*  749 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  750 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  751 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)sctBinding);
/*      */             } 
/*      */           } 
/*  754 */           returnKey = new SecretKeySpec(proofKey, encAlgo);
/*      */         }
/*  756 */         else if (securityToken instanceof DerivedKeyTokenHeaderBlock) {
/*  757 */           if (isWSITRecipient) {
/*  758 */             MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/*  759 */             DerivedTokenKeyBinding dtkBinding = new DerivedTokenKeyBinding();
/*  760 */             if (inferredKB == null) {
/*  761 */               inferredEncryptionPolicy.setKeyBinding((MLSPolicy)dtkBinding);
/*  762 */             } else if (!PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*      */ 
/*      */               
/*  765 */               log.log(Level.SEVERE, LogStringsMessages.WSS_0244_INVALID_LEVEL_DKT());
/*  766 */               throw new XWSSecurityException("A derived Key Token should be a top level key binding");
/*      */             } 
/*      */           } 
/*  769 */           returnKey = resolveDKT(context, (DerivedKeyTokenHeaderBlock)securityToken);
/*      */         } else {
/*  771 */           String message = " Cannot Resolve URI " + uri;
/*  772 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0337_UNSUPPORTED_DIRECTREF_MECHANISM(message), new Object[] { message });
/*      */           
/*  774 */           XWSSecurityException xwsse = new XWSSecurityException(message);
/*  775 */           throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_SECURITY_TOKEN_UNAVAILABLE, xwsse.getMessage(), xwsse);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  780 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0337_UNSUPPORTED_DIRECTREF_MECHANISM(((DirectReference)refElement).getValueType()), new Object[] { ((DirectReference)refElement).getValueType() });
/*      */ 
/*      */ 
/*      */         
/*  784 */         XWSSecurityException xwsse = new XWSSecurityException("unsupported directreference ValueType " + ((DirectReference)refElement).getValueType());
/*      */ 
/*      */ 
/*      */         
/*  788 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, xwsse.getMessage(), xwsse);
/*      */       }
/*      */     
/*      */     }
/*  792 */     else if (refElement instanceof X509IssuerSerial) {
/*  793 */       BigInteger serialNumber = ((X509IssuerSerial)refElement).getSerialNumber();
/*  794 */       String issuerName = ((X509IssuerSerial)refElement).getIssuerName();
/*      */       
/*  796 */       if (isWSITRecipient) {
/*  797 */         MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/*  798 */         AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  799 */         x509Binding.setReferenceType("IssuerSerialNumber");
/*  800 */         if (inferredKB == null) {
/*  801 */           inferredEncryptionPolicy.setKeyBinding((MLSPolicy)x509Binding);
/*  802 */         } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  803 */           ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  804 */         } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  805 */           DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  806 */           if (dktBind.getOriginalKeyBinding() == null) {
/*  807 */             dktBind.setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  808 */           } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  809 */             dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*      */           } 
/*      */         } 
/*      */       } 
/*  813 */       if (sig) {
/*  814 */         returnKey = context.getSecurityEnvironment().getPublicKey(context.getExtraneousProperties(), serialNumber, issuerName);
/*      */       } else {
/*      */         
/*  817 */         returnKey = context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), serialNumber, issuerName);
/*      */       } 
/*      */     } else {
/*      */       
/*  821 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0338_UNSUPPORTED_REFERENCE_MECHANISM());
/*      */       
/*  823 */       XWSSecurityException xwsse = new XWSSecurityException("Key reference mechanism not supported");
/*      */ 
/*      */       
/*  826 */       throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_UNSUPPORTED_SECURITY_TOKEN, xwsse.getMessage(), xwsse);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  831 */     return returnKey;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Key resolveX509Token(SecurableSoapMessage secureMsg, X509SecurityToken token, boolean sig, FilterProcessingContext context) throws XWSSecurityException {
/*  837 */     if (sig) {
/*      */       
/*  839 */       X509Certificate cert = token.getCertificate();
/*  840 */       context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject(context), cert);
/*      */       
/*  842 */       if (context.getTrustCredentialHolder() != null) {
/*  843 */         context.getTrustCredentialHolder().setRequestorCertificate(cert);
/*      */       }
/*  845 */       return cert.getPublicKey();
/*      */     } 
/*  847 */     return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), token.getCertificate());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Key resolveKeyValue(SecurableSoapMessage secureMsg, KeyValue keyValue, boolean sig, FilterProcessingContext context) throws XWSSecurityException {
/*  854 */     keyValue.getElement().normalize();
/*      */     try {
/*  856 */       if (sig) {
/*  857 */         return keyValue.getPublicKey();
/*      */       }
/*  859 */       return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), keyValue.getPublicKey(), false);
/*      */     
/*      */     }
/*  862 */     catch (Exception e) {
/*  863 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0601_UNSUPPORTED_KEYINFO_WSS_0601_ILLEGAL_KEY_VALUE(e.getMessage()), e.getMessage());
/*      */ 
/*      */       
/*  866 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Key resolveX509Data(SecurableSoapMessage secureMsg, X509Data x509Data, boolean sig, FilterProcessingContext context) throws XWSSecurityException {
/*  877 */     x509Data.getElement().normalize();
/*  878 */     X509Certificate cert = null;
/*      */     
/*      */     try {
/*  881 */       if (x509Data.containsCertificate())
/*  882 */       { cert = x509Data.itemCertificate(0).getX509Certificate(); }
/*  883 */       else { if (x509Data.containsSKI()) {
/*  884 */           if (sig) {
/*  885 */             return context.getSecurityEnvironment().getPublicKey(context.getExtraneousProperties(), x509Data.itemSKI(0).getSKIBytes());
/*      */           }
/*      */           
/*  888 */           return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), x509Data.itemSKI(0).getSKIBytes());
/*      */         } 
/*      */ 
/*      */         
/*  892 */         if (x509Data.containsSubjectName()) {
/*  893 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0339_UNSUPPORTED_KEYINFO());
/*  894 */           throw new XWSSecurityException("X509SubjectName child element of X509Data is not yet supported by our implementation");
/*      */         } 
/*  896 */         if (x509Data.containsIssuerSerial()) {
/*  897 */           if (sig) {
/*  898 */             return context.getSecurityEnvironment().getPublicKey(context.getExtraneousProperties(), x509Data.itemIssuerSerial(0).getSerialNumber(), x509Data.itemIssuerSerial(0).getIssuerName());
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  904 */           return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), x509Data.itemIssuerSerial(0).getSerialNumber(), x509Data.itemIssuerSerial(0).getIssuerName());
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  912 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0339_UNSUPPORTED_KEYINFO());
/*  913 */         throw new XWSSecurityException("Unsupported child element of X509Data encountered"); }
/*      */ 
/*      */ 
/*      */       
/*  917 */       if (sig) {
/*  918 */         return cert.getPublicKey();
/*      */       }
/*  920 */       return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), cert);
/*      */     
/*      */     }
/*  923 */     catch (Exception e) {
/*  924 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0602_ILLEGAL_X_509_DATA(e.getMessage()), e.getMessage());
/*      */ 
/*      */       
/*  927 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static byte[] getDecodedBase64EncodedData(String encodedData) throws XWSSecurityException {
/*      */     try {
/*  934 */       return Base64.decode(encodedData);
/*  935 */     } catch (Base64DecodingException e) {
/*  936 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0144_UNABLETO_DECODE_BASE_64_DATA(e.getMessage()), e);
/*  937 */       throw new XWSSecurityException("Unable to decode Base64 encoded data", e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static SecurityToken resolveToken(String uri, FilterProcessingContext context, SecurableSoapMessage secureMsg) throws XWSSecurityException {
/*      */     try {
/*      */       DerivedKeyTokenHeaderBlock derivedKeyTokenHeaderBlock;
/*  947 */       HashMap<String, DerivedKeyTokenHeaderBlock> cache = context.getTokenCache();
/*  948 */       SecurityToken token = (SecurityToken)cache.get(uri);
/*  949 */       if (token != null) {
/*  950 */         return token;
/*      */       }
/*  952 */       if (token == null) {
/*  953 */         Node tokenNode = secureMsg.getElementById(uri);
/*  954 */         tokenNode.normalize();
/*  955 */         if ("BinarySecurityToken".equals(tokenNode.getLocalName())) {
/*  956 */           X509SecurityToken x509SecurityToken = new X509SecurityToken((SOAPElement)tokenNode);
/*  957 */         } else if ("EncryptedKey".equals(tokenNode.getLocalName())) {
/*  958 */           EncryptedKeyToken encryptedKeyToken = new EncryptedKeyToken((SOAPElement)tokenNode);
/*  959 */         } else if ("SecurityContextToken".equals(tokenNode.getLocalName())) {
/*  960 */           SecurityContextTokenImpl securityContextTokenImpl = new SecurityContextTokenImpl((SOAPElement)tokenNode);
/*  961 */         } else if ("DerivedKeyToken".equals(tokenNode.getLocalName())) {
/*  962 */           derivedKeyTokenHeaderBlock = new DerivedKeyTokenHeaderBlock((SOAPElement)tokenNode);
/*      */         } 
/*      */       } 
/*  965 */       cache.put(uri, derivedKeyTokenHeaderBlock);
/*  966 */       return (SecurityToken)derivedKeyTokenHeaderBlock;
/*  967 */     } catch (Exception ex) {
/*  968 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0245_FAILED_RESOLVE_SECURITY_TOKEN(), ex);
/*  969 */       throw new XWSSecurityException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Element resolveSAMLToken(SecurityTokenReference tokenRef, String assertionId, FilterProcessingContext context) throws XWSSecurityException {
/*  976 */     Element clientSAMLAssertionCache = (Element)context.getExtraneousProperties().get("Saml_Assertion_Client_Cache");
/*      */     
/*  978 */     if (clientSAMLAssertionCache != null) {
/*  979 */       return clientSAMLAssertionCache;
/*      */     }
/*      */     
/*  982 */     Element tokenElement = context.getIssuedSAMLToken();
/*  983 */     if (tokenElement != null) {
/*  984 */       context.setExtraneousProperty("Saml_Signature_resolved", "false");
/*      */     }
/*  986 */     if (tokenElement == null) {
/*  987 */       if (tokenRef.getSamlAuthorityBinding() != null) {
/*  988 */         tokenElement = context.getSecurityEnvironment().locateSAMLAssertion(context.getExtraneousProperties(), tokenRef.getSamlAuthorityBinding(), assertionId, context.getSOAPMessage().getSOAPPart());
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  995 */         tokenElement = SAMLUtil.locateSamlAssertion(assertionId, context.getSOAPMessage().getSOAPPart());
/*      */         
/*  997 */         if (!"true".equals(context.getExtraneousProperty("Saml_Signature_resolved")) || "false".equals(context.getExtraneousProperty("Saml_Signature_resolved")))
/*      */         {
/*  999 */           context.setExtraneousProperty("Saml_Signature_resolved", "false");
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1004 */     addAuthorityId(tokenElement, context);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1010 */       if ("EncryptedData".equals(tokenElement.getLocalName())) {
/* 1011 */         return null;
/*      */       }
/* 1013 */     } catch (Exception e) {
/* 1014 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0238_FAILED_RESOLVE_SAML_ASSERTION(), e);
/* 1015 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/* 1018 */     return tokenElement;
/*      */   }
/*      */   
/*      */   private static void addAuthorityId(Element assertion, FilterProcessingContext fp) {
/* 1022 */     EncryptionPolicy ep = (EncryptionPolicy)fp.getInferredPolicy();
/* 1023 */     if (ep != null) {
/* 1024 */       AuthenticationTokenPolicy.SAMLAssertionBinding kb = (AuthenticationTokenPolicy.SAMLAssertionBinding)ep.newSAMLAssertionKeyBinding();
/* 1025 */       String issuer = assertion.getAttribute("Issuer");
/* 1026 */       kb.setAuthorityIdentifier(issuer);
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
/*      */   private static byte[] resolveSCT(FilterProcessingContext context, SecurityContextTokenImpl token, boolean sig) throws XWSSecurityException {
/* 1039 */     context.setExtraneousProperty("Incoming_SCT", token);
/*      */     
/* 1041 */     String scId = token.getSCId();
/* 1042 */     IssuedTokenContext ctx = null;
/* 1043 */     String protocol = context.getWSSCVersion(context.getSecurityPolicyVersion());
/* 1044 */     if (context.isClient()) {
/* 1045 */       DefaultSCTokenConfiguration defaultSCTokenConfiguration = new DefaultSCTokenConfiguration(protocol, scId, !context.isExpired(), !context.isInboundMessage());
/* 1046 */       ctx = IssuedTokenManager.getInstance().createIssuedTokenContext((IssuedTokenConfiguration)defaultSCTokenConfiguration, null);
/*      */       try {
/* 1048 */         IssuedTokenManager.getInstance().getIssuedToken(ctx);
/* 1049 */       } catch (WSTrustException e) {
/* 1050 */         throw new XWSSecurityException(e);
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
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1070 */       ctx = ((SessionManager)context.getExtraneousProperty("SessionManager")).getSecurityContext(scId, !context.isExpired());
/* 1071 */       URI sctId = null;
/* 1072 */       String sctIns = null;
/* 1073 */       String wsuId = null;
/* 1074 */       SecurityContextToken sct = (SecurityContextToken)ctx.getSecurityToken();
/* 1075 */       if (sct != null) {
/* 1076 */         sctId = sct.getIdentifier();
/* 1077 */         sctIns = sct.getInstance();
/* 1078 */         wsuId = sct.getWsuId();
/*      */       } else {
/* 1080 */         SecurityContextTokenInfo sctInfo = ctx.getSecurityContextTokenInfo();
/* 1081 */         sctId = URI.create(sctInfo.getIdentifier());
/* 1082 */         sctIns = sctInfo.getInstance();
/* 1083 */         wsuId = sctInfo.getExternalId();
/*      */       } 
/* 1085 */       ctx.setSecurityToken((Token)WSTrustElementFactory.newInstance(protocol).createSecurityContextToken(sctId, sctIns, wsuId));
/*      */     } 
/*      */     
/* 1088 */     if (ctx == null) {
/* 1089 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0246_UNABLETO_LOCATE_SECURE_CONVERSATION_SESSION());
/* 1090 */       throw new XWSSecurityException("Could not locate SecureConversation session for Id:" + scId);
/*      */     } 
/*      */     
/* 1093 */     Subject subj = ctx.getRequestorSubject();
/* 1094 */     if (subj != null)
/*      */     {
/* 1096 */       if (context.getExtraneousProperty("SCBOOTSTRAP_CRED_IN_SUBJ") == null) {
/*      */         
/* 1098 */         context.getSecurityEnvironment().updateOtherPartySubject(SecurityUtil.getSubject(context.getExtraneousProperties()), subj);
/*      */         
/* 1100 */         context.getExtraneousProperties().put("SCBOOTSTRAP_CRED_IN_SUBJ", "true");
/*      */       } 
/*      */     }
/*      */     
/* 1104 */     byte[] proofKey = null;
/* 1105 */     String instance = null;
/* 1106 */     SecurityContextToken scToken = (SecurityContextToken)ctx.getSecurityToken();
/* 1107 */     if (scToken != null) {
/* 1108 */       instance = scToken.getInstance();
/*      */     } else {
/* 1110 */       instance = ctx.getSecurityContextTokenInfo().getInstance();
/*      */     } 
/* 1112 */     if (instance != null) {
/* 1113 */       if (context.isExpired()) {
/* 1114 */         proofKey = ctx.getProofKey();
/*      */       } else {
/* 1116 */         SecurityContextTokenInfo sctInstanceInfo = ctx.getSecurityContextTokenInfo();
/* 1117 */         proofKey = sctInstanceInfo.getInstanceSecret(scToken.getInstance());
/*      */       } 
/*      */     } else {
/* 1120 */       proofKey = ctx.getProofKey();
/*      */     } 
/* 1122 */     return proofKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Key resolveDKT(FilterProcessingContext context, DerivedKeyTokenHeaderBlock token) throws XWSSecurityException {
/* 1129 */     String dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
/* 1130 */     if (context.getAlgorithmSuite() != null) {
/* 1131 */       dataEncAlgo = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*      */     }
/*      */     
/* 1134 */     SecurableSoapMessage secureMsg = context.getSecurableSoapMessage();
/*      */     
/* 1136 */     EncryptionPolicy inferredEncryptionPolicy = null;
/* 1137 */     boolean isWSITRecipient = (context.getMode() == 3);
/*      */     try {
/* 1139 */       if (isWSITRecipient) {
/* 1140 */         int i = context.getInferredSecurityPolicy().size() - 1;
/* 1141 */         inferredEncryptionPolicy = (EncryptionPolicy)context.getInferredSecurityPolicy().get(i);
/*      */       } 
/* 1143 */     } catch (Exception e) {
/* 1144 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0247_FAILED_RESOLVE_DERIVED_KEY_TOKEN());
/* 1145 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */ 
/*      */     
/* 1149 */     SecurityTokenReference sectr = token.getDerivedKeyElement();
/* 1150 */     if (sectr == null) {
/* 1151 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0248_NULL_STR());
/* 1152 */       throw new XWSSecurityException("Invalid DerivedKey Token encountered, no STR found");
/*      */     } 
/* 1154 */     ReferenceElement refElement = sectr.getReference();
/* 1155 */     Key encKey = null;
/* 1156 */     byte[] secret = null;
/* 1157 */     if (refElement instanceof DirectReference) {
/* 1158 */       SecurityToken securityToken; String uri = ((DirectReference)refElement).getURI();
/* 1159 */       String valueType = ((DirectReference)refElement).getValueType();
/* 1160 */       String wsuId = SecurableSoapMessage.getIdFromFragmentRef(uri);
/* 1161 */       SecurityContextTokenImpl securityContextTokenImpl = SecurityUtil.locateBySCTId(context, wsuId);
/* 1162 */       if (securityContextTokenImpl == null) {
/* 1163 */         securityToken = resolveToken(wsuId, context, secureMsg);
/*      */         
/* 1165 */         if (valueType == null && securityToken instanceof EncryptedKeyToken) {
/* 1166 */           valueType = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey";
/*      */         }
/*      */       } 
/*      */       
/* 1170 */       if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(valueType)) {
/*      */         try {
/* 1172 */           Element cipherData = ((EncryptedKeyToken)securityToken).getAsSoapElement().getChildElements(new QName("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc")).next();
/* 1173 */           String cipherValue = cipherData.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue").item(0).getTextContent();
/* 1174 */           byte[] decodedCipher = Base64.decode(cipherValue);
/* 1175 */           byte[] ekSha1 = MessageDigest.getInstance("SHA-1").digest(decodedCipher);
/* 1176 */           String encEkSha1 = Base64.encode(ekSha1);
/* 1177 */           context.setExtraneousProperty("EKSHA1Value", encEkSha1);
/* 1178 */         } catch (Exception e) {
/* 1179 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0241_UNABLETO_SET_EKSHA_1_ON_CONTEXT(), e);
/* 1180 */           throw new XWSSecurityException(e);
/*      */         } 
/*      */         
/* 1183 */         if (isWSITRecipient) {
/* 1184 */           MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/* 1185 */           SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/* 1186 */           AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 1187 */           skBinding.setKeyBinding((MLSPolicy)x509Binding);
/*      */           
/* 1189 */           if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1190 */             (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1191 */             ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)skBinding);
/*      */           }
/*      */         } 
/*      */         
/* 1195 */         KeyInfoHeaderBlock kiHB = ((EncryptedKeyToken)securityToken).getKeyInfo();
/* 1196 */         encKey = ((EncryptedKeyToken)securityToken).getSecretKey(getKey(kiHB, false, context), dataEncAlgo);
/* 1197 */         secret = encKey.getEncoded();
/* 1198 */         context.setExtraneousProperty("SecretKeyValue", encKey);
/* 1199 */       } else if ("http://schemas.xmlsoap.org/ws/2005/02/sc/sct".equals(valueType) || "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct".equals(valueType)) {
/* 1200 */         if (securityToken instanceof SecurityContextToken) {
/* 1201 */           if (isWSITRecipient) {
/* 1202 */             MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/* 1203 */             SecureConversationTokenKeyBinding sctBinding = new SecureConversationTokenKeyBinding();
/* 1204 */             if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1205 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1206 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)sctBinding);
/*      */             }
/*      */           } 
/*      */           
/* 1210 */           secret = resolveSCT(context, (SecurityContextTokenImpl)securityToken, false);
/*      */         } else {
/* 1212 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0243_INVALID_VALUE_TYPE_NON_SCT_TOKEN());
/* 1213 */           throw new XWSSecurityException("Incorrect ValueType: http://schemas.xmlsoap.org/ws/2005/02/sc/sct, specified for a Non SCT Token");
/*      */         }
/*      */       
/* 1216 */       } else if (null == valueType) {
/* 1217 */         if (securityToken instanceof SecurityContextToken) {
/* 1218 */           if (isWSITRecipient) {
/* 1219 */             MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/* 1220 */             SecureConversationTokenKeyBinding sctBinding = new SecureConversationTokenKeyBinding();
/* 1221 */             if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/* 1222 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)sctBinding);
/*      */             }
/*      */           } 
/*      */           
/* 1226 */           secret = resolveSCT(context, (SecurityContextTokenImpl)securityToken, false);
/*      */         } else {
/* 1228 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0249_UNSUPPORTED_TOKEN_TYPE_DKT());
/* 1229 */           throw new XWSSecurityException("Unsupported TokenType " + securityToken + " under DerivedKeyToken");
/*      */         } 
/*      */       } else {
/* 1232 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0249_UNSUPPORTED_TOKEN_TYPE_DKT());
/* 1233 */         throw new XWSSecurityException("Unsupported TokenType " + securityToken + " under DerivedKeyToken");
/*      */       } 
/* 1235 */     } else if (refElement instanceof KeyIdentifier) {
/* 1236 */       KeyIdentifier keyId = (KeyIdentifier)refElement;
/* 1237 */       if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1".equals(keyId.getValueType())) {
/* 1238 */         if (isWSITRecipient) {
/* 1239 */           MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/* 1240 */           SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/* 1241 */           AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 1242 */           x509Binding.setReferenceType("Identifier");
/* 1243 */           skBinding.setKeyBinding((MLSPolicy)x509Binding);
/*      */           
/* 1245 */           if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1246 */             (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1247 */             ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)skBinding);
/*      */           }
/*      */         } 
/*      */         
/* 1251 */         String ekSha1RefValue = (String)context.getExtraneousProperty("EncryptedKeySHA1");
/* 1252 */         Key secretKey = (Key)context.getExtraneousProperty("SecretKey");
/* 1253 */         String keyRefValue = keyId.getReferenceValue();
/* 1254 */         if (ekSha1RefValue != null && secretKey != null) {
/* 1255 */           if (ekSha1RefValue.equals(keyRefValue)) {
/* 1256 */             encKey = secretKey;
/* 1257 */             secret = encKey.getEncoded();
/*      */           } else {
/* 1259 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0240_INVALID_ENCRYPTED_KEY_SHA_1_REFERENCE());
/* 1260 */             throw new XWSSecurityException("EncryptedKeySHA1 reference not correct");
/*      */           } 
/*      */         } else {
/* 1263 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0240_INVALID_ENCRYPTED_KEY_SHA_1_REFERENCE());
/* 1264 */           String message = "EncryptedKeySHA1 reference not correct";
/* 1265 */           throw new XWSSecurityException(message);
/*      */         } 
/* 1267 */       } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(keyId.getValueType()) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(keyId.getValueType())) {
/*      */         
/* 1269 */         if (isWSITRecipient) {
/* 1270 */           MLSPolicy inferredKB = inferredEncryptionPolicy.getKeyBinding();
/* 1271 */           IssuedTokenKeyBinding itkBinding = new IssuedTokenKeyBinding();
/* 1272 */           if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1273 */             (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1274 */             ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)itkBinding);
/*      */           }
/*      */         } 
/*      */         
/* 1278 */         String asId = keyId.getReferenceValue();
/* 1279 */         Element assertion = resolveSAMLToken(sectr, asId, context);
/* 1280 */         encKey = resolveSamlAssertion(secureMsg, assertion, true, context, asId);
/* 1281 */         if (context.hasIssuedToken() && encKey != null) {
/* 1282 */           SecurityUtil.initInferredIssuedTokenContext(context, (Token)sectr, encKey);
/*      */         }
/* 1284 */         secret = encKey.getEncoded();
/*      */       } else {
/* 1286 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0282_UNSUPPORTED_KEY_IDENTIFIER_REFERENCE_DKT());
/* 1287 */         throw new XWSSecurityException("Unsupported KeyIdentifier Reference " + keyId + " under DerivedKeyToken");
/*      */       } 
/*      */     } else {
/* 1290 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0283_UNSUPPORTED_REFERENCE_TYPE_DKT());
/* 1291 */       throw new XWSSecurityException("Unsupported ReferenceType " + refElement + " under DerivedKeyToken");
/*      */     } 
/* 1293 */     long length = token.getLength();
/* 1294 */     long offset = token.getOffset();
/* 1295 */     byte[] nonce = token.getNonce();
/* 1296 */     String label = token.getLabel();
/* 1297 */     DerivedKeyTokenImpl derivedKeyTokenImpl = new DerivedKeyTokenImpl(offset, length, secret, nonce, label);
/* 1298 */     String jceAlgo = SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo);
/*      */     
/* 1300 */     Key returnKey = null;
/*      */     try {
/* 1302 */       returnKey = derivedKeyTokenImpl.generateSymmetricKey(jceAlgo);
/* 1303 */     } catch (InvalidKeyException ex) {
/* 1304 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0247_FAILED_RESOLVE_DERIVED_KEY_TOKEN());
/* 1305 */       throw new XWSSecurityException(ex);
/* 1306 */     } catch (UnsupportedEncodingException ex) {
/* 1307 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0247_FAILED_RESOLVE_DERIVED_KEY_TOKEN());
/* 1308 */       throw new XWSSecurityException(ex);
/* 1309 */     } catch (NoSuchAlgorithmException ex) {
/* 1310 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0247_FAILED_RESOLVE_DERIVED_KEY_TOKEN());
/* 1311 */       throw new XWSSecurityException(ex);
/*      */     } 
/* 1313 */     return returnKey;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Key processSTR(KeyInfoHeaderBlock keyInfo, boolean sig, FilterProcessingContext context) throws XWSSecurityException {
/* 1322 */     Key returnKey = null;
/* 1323 */     HashMap<String, SecurityToken> tokenCache = context.getTokenCache();
/* 1324 */     SecurableSoapMessage secureMsg = context.getSecurableSoapMessage();
/* 1325 */     SecurityTokenReference str = keyInfo.getSecurityTokenReference(0);
/* 1326 */     ReferenceElement refElement = str.getReference();
/* 1327 */     SignaturePolicy policy = (SignaturePolicy)context.getInferredPolicy();
/* 1328 */     SignaturePolicy inferredSignaturePolicy = null;
/* 1329 */     boolean isWSITRecipient = (context.getMode() == 3);
/*      */     try {
/* 1331 */       if (isWSITRecipient) {
/* 1332 */         int i = context.getInferredSecurityPolicy().size() - 1;
/* 1333 */         inferredSignaturePolicy = (SignaturePolicy)context.getInferredSecurityPolicy().get(i);
/*      */       } 
/* 1335 */     } catch (Exception e) {
/* 1336 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0250_FAILED_PROCESS_STR(), e);
/* 1337 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1342 */     if (refElement instanceof KeyIdentifier) {
/* 1343 */       KeyIdentifier keyId = (KeyIdentifier)refElement;
/*      */       
/* 1345 */       if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier".equals(keyId.getValueType()) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3SubjectKeyIdentifier".equals(keyId.getValueType())) {
/*      */         
/* 1347 */         if (policy != null) {
/* 1348 */           AuthenticationTokenPolicy.X509CertificateBinding keyBinding = null;
/* 1349 */           keyBinding = (AuthenticationTokenPolicy.X509CertificateBinding)policy.newX509CertificateKeyBinding();
/* 1350 */           keyBinding.setReferenceType("Identifier");
/*      */         } 
/* 1352 */         if (isWSITRecipient) {
/* 1353 */           MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/* 1354 */           AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 1355 */           x509Binding.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier");
/* 1356 */           x509Binding.setReferenceType("Identifier");
/* 1357 */           if (inferredKB == null) {
/* 1358 */             inferredSignaturePolicy.setKeyBinding((MLSPolicy)x509Binding);
/* 1359 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1360 */             (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1361 */             ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*      */           } 
/*      */         } 
/* 1364 */         if (sig) {
/* 1365 */           returnKey = context.getSecurityEnvironment().getPublicKey(context.getExtraneousProperties(), getDecodedBase64EncodedData(keyId.getReferenceValue()));
/*      */         }
/*      */         else {
/*      */           
/* 1369 */           returnKey = context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), getDecodedBase64EncodedData(keyId.getReferenceValue()));
/*      */         
/*      */         }
/*      */       
/*      */       }
/* 1374 */       else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1".equals(keyId.getValueType())) {
/* 1375 */         if (policy != null) {
/* 1376 */           AuthenticationTokenPolicy.X509CertificateBinding keyBinding = null;
/* 1377 */           keyBinding = (AuthenticationTokenPolicy.X509CertificateBinding)policy.newX509CertificateKeyBinding();
/* 1378 */           keyBinding.setReferenceType("Thumbprint");
/*      */         } 
/* 1380 */         if (isWSITRecipient) {
/* 1381 */           MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/* 1382 */           AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 1383 */           x509Binding.setValueType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1");
/* 1384 */           x509Binding.setReferenceType("Identifier");
/* 1385 */           if (inferredKB == null) {
/* 1386 */             inferredSignaturePolicy.setKeyBinding((MLSPolicy)x509Binding);
/* 1387 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1388 */             (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1389 */             ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*      */           } 
/*      */         } 
/* 1392 */         if (sig) {
/* 1393 */           returnKey = context.getSecurityEnvironment().getPublicKey(context.getExtraneousProperties(), getDecodedBase64EncodedData(keyId.getReferenceValue()), "Thumbprint");
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1400 */           returnKey = context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), getDecodedBase64EncodedData(keyId.getReferenceValue()), "Thumbprint");
/*      */ 
/*      */         
/*      */         }
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1408 */       else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1".equals(keyId.getValueType())) {
/* 1409 */         if (isWSITRecipient) {
/* 1410 */           MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/* 1411 */           SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/* 1412 */           AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 1413 */           skBinding.setKeyBinding((MLSPolicy)x509Binding);
/*      */           
/* 1415 */           if (inferredKB == null) {
/* 1416 */             inferredSignaturePolicy.setKeyBinding((MLSPolicy)skBinding);
/* 1417 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1418 */             (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1419 */             ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)skBinding);
/*      */           } 
/*      */         } 
/* 1422 */         String ekSha1RefValue = (String)context.getExtraneousProperty("EncryptedKeySHA1");
/* 1423 */         Key secretKey = (Key)context.getExtraneousProperty("SecretKey");
/* 1424 */         String keyRefValue = keyId.getReferenceValue();
/* 1425 */         if (ekSha1RefValue != null && secretKey != null) {
/* 1426 */           if (ekSha1RefValue.equals(keyRefValue))
/* 1427 */             returnKey = secretKey; 
/*      */         } else {
/* 1429 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0240_INVALID_ENCRYPTED_KEY_SHA_1_REFERENCE());
/* 1430 */           String message = "EncryptedKeySHA1 reference not correct";
/* 1431 */           throw new XWSSecurityException(message);
/*      */         }
/*      */       
/* 1434 */       } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(keyId.getValueType()) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(keyId.getValueType())) {
/*      */ 
/*      */         
/* 1437 */         if (policy != null) {
/* 1438 */           AuthenticationTokenPolicy.SAMLAssertionBinding keyBinding = null;
/* 1439 */           keyBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)policy.newSAMLAssertionKeyBinding();
/* 1440 */           keyBinding.setReferenceType(keyId.getValueType());
/*      */         } 
/*      */         
/* 1443 */         String assertionID = keyId.getDecodedReferenceValue();
/* 1444 */         Element samlAssertion = resolveSAMLToken(str, assertionID, context);
/* 1445 */         if (isWSITRecipient) {
/* 1446 */           MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/* 1447 */           IssuedTokenKeyBinding itkBinding = new IssuedTokenKeyBinding();
/* 1448 */           if (inferredKB == null) {
/* 1449 */             if (context.hasIssuedToken()) {
/* 1450 */               inferredSignaturePolicy.setKeyBinding((MLSPolicy)itkBinding);
/*      */             } else {
/* 1452 */               inferredSignaturePolicy.setKeyBinding((MLSPolicy)new AuthenticationTokenPolicy.SAMLAssertionBinding());
/*      */             } 
/* 1454 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1455 */             (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1456 */             ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)itkBinding);
/*      */           } 
/*      */         } 
/* 1459 */         returnKey = resolveSamlAssertion(secureMsg, samlAssertion, sig, context, assertionID);
/* 1460 */         if (context.hasIssuedToken() && returnKey != null) {
/* 1461 */           SecurityUtil.initInferredIssuedTokenContext(context, (Token)str, returnKey);
/*      */         }
/*      */       } else {
/*      */         
/* 1465 */         if (policy != null) {
/* 1466 */           AuthenticationTokenPolicy.SAMLAssertionBinding keyBinding = null;
/* 1467 */           keyBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)policy.newSAMLAssertionKeyBinding();
/*      */         } 
/* 1469 */         Element samlAssertion = null;
/* 1470 */         String assertionID = keyId.getDecodedReferenceValue();
/*      */         try {
/* 1472 */           samlAssertion = resolveSAMLToken(str, assertionID, context);
/* 1473 */         } catch (Exception ex) {}
/*      */ 
/*      */         
/* 1476 */         if (samlAssertion != null) {
/* 1477 */           if (isWSITRecipient) {
/* 1478 */             MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/* 1479 */             IssuedTokenKeyBinding itkBinding = new IssuedTokenKeyBinding();
/* 1480 */             if (inferredKB == null) {
/* 1481 */               if (context.hasIssuedToken()) {
/* 1482 */                 inferredSignaturePolicy.setKeyBinding((MLSPolicy)itkBinding);
/*      */               } else {
/* 1484 */                 inferredSignaturePolicy.setKeyBinding((MLSPolicy)new AuthenticationTokenPolicy.SAMLAssertionBinding());
/*      */               } 
/* 1486 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1487 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1488 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)itkBinding);
/*      */             } 
/*      */           } 
/* 1491 */           returnKey = resolveSamlAssertion(secureMsg, samlAssertion, sig, context, assertionID);
/* 1492 */           if (context.hasIssuedToken() && returnKey != null) {
/* 1493 */             SecurityUtil.initInferredIssuedTokenContext(context, (Token)str, returnKey);
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/* 1498 */           if (isWSITRecipient) {
/* 1499 */             MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/* 1500 */             AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 1501 */             x509Binding.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier");
/* 1502 */             x509Binding.setReferenceType("Identifier");
/* 1503 */             if (inferredKB == null) {
/* 1504 */               inferredSignaturePolicy.setKeyBinding((MLSPolicy)x509Binding);
/* 1505 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1506 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1507 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*      */             } 
/*      */           } 
/* 1510 */           if (sig) {
/* 1511 */             returnKey = context.getSecurityEnvironment().getPublicKey(context.getExtraneousProperties(), getDecodedBase64EncodedData(keyId.getReferenceValue()));
/*      */           }
/*      */           else {
/*      */             
/* 1515 */             returnKey = context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), getDecodedBase64EncodedData(keyId.getReferenceValue()));
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       } 
/* 1521 */     } else if (refElement instanceof DirectReference) {
/* 1522 */       String uri = ((DirectReference)refElement).getURI();
/*      */ 
/*      */       
/* 1525 */       AuthenticationTokenPolicy.X509CertificateBinding keyBinding = null;
/* 1526 */       String valueType = ((DirectReference)refElement).getValueType();
/* 1527 */       if ("http://schemas.xmlsoap.org/ws/2005/02/sc/dk".equals(valueType) || "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/dk".equals(valueType))
/*      */       {
/*      */         
/* 1530 */         valueType = null;
/*      */       }
/*      */       
/* 1533 */       if (policy != null) {
/* 1534 */         keyBinding = (AuthenticationTokenPolicy.X509CertificateBinding)policy.newX509CertificateKeyBinding();
/* 1535 */         keyBinding.setReferenceType("Direct");
/* 1536 */         keyBinding.setValueType(valueType);
/*      */       } 
/*      */       
/* 1539 */       if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(valueType) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1".equals(valueType)) {
/*      */         
/* 1541 */         HashMap insertedX509Cache = context.getInsertedX509Cache();
/* 1542 */         String wsuId = SecurableSoapMessage.getIdFromFragmentRef(uri);
/* 1543 */         X509SecurityToken token = (X509SecurityToken)insertedX509Cache.get(wsuId);
/* 1544 */         if (token == null)
/* 1545 */           token = (X509SecurityToken)resolveToken(wsuId, context, secureMsg); 
/* 1546 */         if (isWSITRecipient) {
/* 1547 */           MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/* 1548 */           AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 1549 */           x509Binding.setReferenceType("Direct");
/* 1550 */           x509Binding.setValueType(valueType);
/* 1551 */           if (inferredKB == null) {
/* 1552 */             inferredSignaturePolicy.setKeyBinding((MLSPolicy)x509Binding);
/* 1553 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1554 */             (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1555 */             ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*      */           } 
/*      */         } 
/* 1558 */         returnKey = resolveX509Token(secureMsg, token, sig, context);
/*      */       }
/* 1560 */       else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(valueType)) {
/*      */         
/* 1562 */         String wsuId = SecurableSoapMessage.getIdFromFragmentRef(uri);
/* 1563 */         SecurityToken token = resolveToken(wsuId, context, secureMsg);
/*      */         
/* 1565 */         KeyInfoHeaderBlock kiHB = ((EncryptedKeyToken)token).getKeyInfo();
/* 1566 */         SecurityTokenReference sectr = kiHB.getSecurityTokenReference(0);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1571 */         String dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/* 1572 */         if (context.getAlgorithmSuite() != null) {
/* 1573 */           dataEncAlgo = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*      */         }
/* 1575 */         else if (context.getDataEncryptionAlgorithm() != null) {
/* 1576 */           dataEncAlgo = context.getDataEncryptionAlgorithm();
/*      */         } 
/*      */         
/*      */         try {
/* 1580 */           Element cipherData = ((EncryptedKeyToken)token).getAsSoapElement().getChildElements(new QName("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc")).next();
/* 1581 */           String cipherValue = cipherData.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue").item(0).getTextContent();
/* 1582 */           byte[] decodedCipher = Base64.decode(cipherValue);
/* 1583 */           byte[] ekSha1 = MessageDigest.getInstance("SHA-1").digest(decodedCipher);
/* 1584 */           String encEkSha1 = Base64.encode(ekSha1);
/* 1585 */           context.setExtraneousProperty("EKSHA1Value", encEkSha1);
/* 1586 */         } catch (Exception e) {
/* 1587 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0241_UNABLETO_SET_EKSHA_1_ON_CONTEXT(), e);
/* 1588 */           throw new XWSSecurityException(e);
/*      */         } 
/* 1590 */         if (isWSITRecipient) {
/* 1591 */           MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/* 1592 */           SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/* 1593 */           AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 1594 */           skBinding.setKeyBinding((MLSPolicy)x509Binding);
/*      */           
/* 1596 */           if (inferredKB == null) {
/* 1597 */             inferredSignaturePolicy.setKeyBinding((MLSPolicy)skBinding);
/* 1598 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1599 */             (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1600 */             ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)skBinding);
/*      */           } 
/*      */         } 
/* 1603 */         returnKey = ((EncryptedKeyToken)token).getSecretKey(getKey(kiHB, sig, context), dataEncAlgo);
/* 1604 */         context.setExtraneousProperty("SecretKeyValue", returnKey);
/*      */       }
/* 1606 */       else if ("http://schemas.xmlsoap.org/ws/2005/02/sc/sct".equals(valueType) || "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct".equals(valueType)) {
/*      */         
/* 1608 */         String sctId = SecurableSoapMessage.getIdFromFragmentRef(uri);
/* 1609 */         SecurityToken securityToken = (SecurityToken)tokenCache.get(sctId);
/*      */         
/* 1611 */         if (securityToken == null) {
/* 1612 */           SecurityContextTokenImpl securityContextTokenImpl = SecurityUtil.locateBySCTId(context, uri);
/* 1613 */           if (securityContextTokenImpl == null) {
/* 1614 */             securityToken = resolveToken(sctId, context, secureMsg);
/*      */           }
/* 1616 */           if (securityToken == null) {
/* 1617 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0242_UNABLETO_LOCATE_SCT());
/* 1618 */             throw new XWSSecurityException("SCT Token with Id " + sctId + "not found");
/*      */           } 
/* 1620 */           tokenCache.put(sctId, securityToken);
/*      */         } 
/*      */ 
/*      */         
/* 1624 */         if (securityToken instanceof SecurityContextToken) {
/*      */           
/* 1626 */           byte[] proofKey = resolveSCT(context, (SecurityContextTokenImpl)securityToken, sig);
/* 1627 */           String encAlgo = "AES";
/* 1628 */           if (context.getAlgorithmSuite() != null) {
/* 1629 */             encAlgo = SecurityUtil.getSecretKeyAlgorithm(context.getAlgorithmSuite().getEncryptionAlgorithm());
/*      */           }
/* 1631 */           if (isWSITRecipient) {
/* 1632 */             MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/* 1633 */             SecureConversationTokenKeyBinding sctBinding = new SecureConversationTokenKeyBinding();
/* 1634 */             if (inferredKB == null) {
/* 1635 */               inferredSignaturePolicy.setKeyBinding((MLSPolicy)sctBinding);
/* 1636 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1637 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1638 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)sctBinding);
/*      */             } 
/*      */           } 
/* 1641 */           returnKey = new SecretKeySpec(proofKey, encAlgo);
/*      */         } else {
/*      */           
/* 1644 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0243_INVALID_VALUE_TYPE_NON_SCT_TOKEN());
/* 1645 */           throw new XWSSecurityException("Incorrect ValueType: http://schemas.xmlsoap.org/ws/2005/02/sc/sct, specified for a Non SCT Token");
/*      */         }
/*      */       
/* 1648 */       } else if (null == valueType) {
/*      */         SecurityToken securityToken;
/* 1650 */         String wsuId = SecurableSoapMessage.getIdFromFragmentRef(uri);
/* 1651 */         SecurityContextTokenImpl securityContextTokenImpl = SecurityUtil.locateBySCTId(context, wsuId);
/* 1652 */         if (securityContextTokenImpl == null) {
/* 1653 */           securityToken = resolveToken(wsuId, context, secureMsg);
/*      */         }
/* 1655 */         if (securityToken instanceof X509SecurityToken) {
/* 1656 */           if (isWSITRecipient) {
/* 1657 */             MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/* 1658 */             AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 1659 */             x509Binding.setReferenceType("Direct");
/* 1660 */             if (inferredKB == null) {
/* 1661 */               inferredSignaturePolicy.setKeyBinding((MLSPolicy)x509Binding);
/* 1662 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1663 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1664 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*      */             } 
/*      */           } 
/* 1667 */           returnKey = resolveX509Token(secureMsg, (X509SecurityToken)securityToken, sig, context);
/* 1668 */         } else if (securityToken instanceof EncryptedKeyToken) {
/*      */           
/* 1670 */           KeyInfoHeaderBlock kiHB = ((EncryptedKeyToken)securityToken).getKeyInfo();
/* 1671 */           SecurityTokenReference sectr = kiHB.getSecurityTokenReference(0);
/* 1672 */           ReferenceElement refElem = sectr.getReference();
/* 1673 */           String dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*      */ 
/*      */           
/* 1676 */           if (context.getAlgorithmSuite() != null) {
/* 1677 */             dataEncAlgo = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*      */           }
/* 1679 */           else if (context.getDataEncryptionAlgorithm() != null) {
/* 1680 */             dataEncAlgo = context.getDataEncryptionAlgorithm();
/*      */           } 
/*      */           
/*      */           try {
/* 1684 */             Element cipherData = ((EncryptedKeyToken)securityToken).getAsSoapElement().getChildElements(new QName("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc")).next();
/* 1685 */             String cipherValue = cipherData.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue").item(0).getTextContent();
/* 1686 */             byte[] decodedCipher = Base64.decode(cipherValue);
/* 1687 */             byte[] ekSha1 = MessageDigest.getInstance("SHA-1").digest(decodedCipher);
/* 1688 */             String encEkSha1 = Base64.encode(ekSha1);
/* 1689 */             context.setExtraneousProperty("EKSHA1Value", encEkSha1);
/* 1690 */           } catch (Exception e) {
/* 1691 */             log.log(Level.SEVERE, LogStringsMessages.WSS_0241_UNABLETO_SET_EKSHA_1_ON_CONTEXT(), e);
/* 1692 */             throw new XWSSecurityException(e);
/*      */           } 
/* 1694 */           if (isWSITRecipient) {
/* 1695 */             MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/* 1696 */             SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/* 1697 */             AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 1698 */             skBinding.setKeyBinding((MLSPolicy)x509Binding);
/*      */             
/* 1700 */             if (inferredKB == null) {
/* 1701 */               inferredSignaturePolicy.setKeyBinding((MLSPolicy)skBinding);
/* 1702 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1703 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1704 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)skBinding);
/*      */             } 
/*      */           } 
/* 1707 */           returnKey = ((EncryptedKeyToken)securityToken).getSecretKey(getKey(kiHB, sig, context), dataEncAlgo);
/* 1708 */           context.setExtraneousProperty("SecretKeyValue", returnKey);
/*      */         }
/* 1710 */         else if (securityToken instanceof SecurityContextToken) {
/*      */           
/* 1712 */           byte[] proofKey = resolveSCT(context, (SecurityContextTokenImpl)securityToken, sig);
/* 1713 */           String encAlgo = "AES";
/* 1714 */           if (context.getAlgorithmSuite() != null) {
/* 1715 */             encAlgo = SecurityUtil.getSecretKeyAlgorithm(context.getAlgorithmSuite().getEncryptionAlgorithm());
/*      */           }
/* 1717 */           if (isWSITRecipient) {
/* 1718 */             MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/* 1719 */             SecureConversationTokenKeyBinding sctBinding = new SecureConversationTokenKeyBinding();
/* 1720 */             if (inferredKB == null) {
/* 1721 */               inferredSignaturePolicy.setKeyBinding((MLSPolicy)sctBinding);
/* 1722 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1723 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1724 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)sctBinding);
/*      */             } 
/*      */           } 
/* 1727 */           returnKey = new SecretKeySpec(proofKey, encAlgo);
/*      */         }
/* 1729 */         else if (securityToken instanceof DerivedKeyTokenHeaderBlock) {
/* 1730 */           if (isWSITRecipient) {
/* 1731 */             MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/* 1732 */             DerivedTokenKeyBinding dtkBinding = new DerivedTokenKeyBinding();
/* 1733 */             if (inferredKB == null) {
/* 1734 */               inferredSignaturePolicy.setKeyBinding((MLSPolicy)dtkBinding);
/*      */             } else {
/* 1736 */               log.log(Level.SEVERE, LogStringsMessages.WSS_0244_INVALID_LEVEL_DKT());
/* 1737 */               throw new XWSSecurityException("A derived Key Token should be a top level key binding");
/*      */             } 
/*      */           } 
/* 1740 */           returnKey = resolveDKT(context, (DerivedKeyTokenHeaderBlock)securityToken);
/*      */         } else {
/* 1742 */           String message = " Cannot Resolve URI " + uri;
/* 1743 */           log.log(Level.SEVERE, LogStringsMessages.WSS_0337_UNSUPPORTED_DIRECTREF_MECHANISM(message), new Object[] { message });
/*      */           
/* 1745 */           XWSSecurityException xwsse = new XWSSecurityException(message);
/* 1746 */           throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_SECURITY_TOKEN_UNAVAILABLE, xwsse.getMessage(), xwsse);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1751 */         log.log(Level.SEVERE, LogStringsMessages.WSS_0337_UNSUPPORTED_DIRECTREF_MECHANISM(((DirectReference)refElement).getValueType()), new Object[] { ((DirectReference)refElement).getValueType() });
/*      */ 
/*      */ 
/*      */         
/* 1755 */         XWSSecurityException xwsse = new XWSSecurityException("unsupported directreference ValueType " + ((DirectReference)refElement).getValueType());
/*      */ 
/*      */ 
/*      */         
/* 1759 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, xwsse.getMessage(), xwsse);
/*      */       }
/*      */     
/*      */     }
/* 1763 */     else if (refElement instanceof X509IssuerSerial) {
/* 1764 */       BigInteger serialNumber = ((X509IssuerSerial)refElement).getSerialNumber();
/* 1765 */       String issuerName = ((X509IssuerSerial)refElement).getIssuerName();
/*      */       
/* 1767 */       if (isWSITRecipient) {
/* 1768 */         MLSPolicy inferredKB = inferredSignaturePolicy.getKeyBinding();
/* 1769 */         AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/* 1770 */         x509Binding.setReferenceType("IssuerSerialNumber");
/* 1771 */         if (inferredKB == null) {
/* 1772 */           inferredSignaturePolicy.setKeyBinding((MLSPolicy)x509Binding);
/* 1773 */         } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/* 1774 */           (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/* 1775 */           ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*      */         } 
/*      */       } 
/* 1778 */       if (sig) {
/* 1779 */         returnKey = context.getSecurityEnvironment().getPublicKey(context.getExtraneousProperties(), serialNumber, issuerName);
/*      */       } else {
/*      */         
/* 1782 */         returnKey = context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), serialNumber, issuerName);
/*      */       } 
/*      */     } else {
/*      */       
/* 1786 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0338_UNSUPPORTED_REFERENCE_MECHANISM());
/*      */       
/* 1788 */       XWSSecurityException xwsse = new XWSSecurityException("Key reference mechanism not supported");
/*      */ 
/*      */       
/* 1791 */       throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_UNSUPPORTED_SECURITY_TOKEN, xwsse.getMessage(), xwsse);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1796 */     return returnKey;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\KeyResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */