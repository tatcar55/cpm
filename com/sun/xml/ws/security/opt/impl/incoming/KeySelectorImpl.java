/*      */ package com.sun.xml.ws.security.opt.impl.incoming;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*      */ import com.sun.org.apache.xml.internal.security.utils.RFC2253Parser;
/*      */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*      */ import com.sun.xml.ws.api.security.trust.client.IssuedTokenConfiguration;
/*      */ import com.sun.xml.ws.api.security.trust.client.IssuedTokenManager;
/*      */ import com.sun.xml.ws.runtime.dev.SessionManager;
/*      */ import com.sun.xml.ws.security.IssuedTokenContext;
/*      */ import com.sun.xml.ws.security.SecurityContextToken;
/*      */ import com.sun.xml.ws.security.SecurityContextTokenInfo;
/*      */ import com.sun.xml.ws.security.Token;
/*      */ import com.sun.xml.ws.security.impl.PasswordDerivedKey;
/*      */ import com.sun.xml.ws.security.impl.kerberos.KerberosContext;
/*      */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*      */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*      */ import com.sun.xml.ws.security.opt.api.keyinfo.BinarySecurityToken;
/*      */ import com.sun.xml.ws.security.opt.api.reference.DirectReference;
/*      */ import com.sun.xml.ws.security.opt.api.reference.KeyIdentifier;
/*      */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*      */ import com.sun.xml.ws.security.opt.crypto.jaxb.JAXBStructure;
/*      */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*      */ import com.sun.xml.ws.security.opt.impl.crypto.SSEData;
/*      */ import com.sun.xml.ws.security.opt.impl.keyinfo.BinarySecurityToken;
/*      */ import com.sun.xml.ws.security.opt.impl.keyinfo.SecurityTokenReference;
/*      */ import com.sun.xml.ws.security.opt.impl.reference.KeyIdentifier;
/*      */ import com.sun.xml.ws.security.opt.impl.reference.X509IssuerSerial;
/*      */ import com.sun.xml.ws.security.opt.impl.tokens.UsernameToken;
/*      */ import com.sun.xml.ws.security.opt.impl.util.SOAPUtil;
/*      */ import com.sun.xml.ws.security.opt.impl.util.WSSElementFactory;
/*      */ import com.sun.xml.ws.security.secconv.impl.client.DefaultSCTokenConfiguration;
/*      */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*      */ import com.sun.xml.wss.impl.MessageConstants;
/*      */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*      */ import com.sun.xml.wss.impl.XMLUtil;
/*      */ import com.sun.xml.wss.impl.misc.Base64;
/*      */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*      */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*      */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*      */ import com.sun.xml.wss.logging.LogStringsMessages;
/*      */ import com.sun.xml.wss.logging.impl.dsig.LogStringsMessages;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.math.BigInteger;
/*      */ import java.net.URI;
/*      */ import java.security.Key;
/*      */ import java.security.KeyException;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.PublicKey;
/*      */ import java.security.cert.CertificateEncodingException;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.crypto.spec.SecretKeySpec;
/*      */ import javax.security.auth.Subject;
/*      */ import javax.xml.bind.JAXBElement;
/*      */ import javax.xml.crypto.AlgorithmMethod;
/*      */ import javax.xml.crypto.KeySelector;
/*      */ import javax.xml.crypto.KeySelectorException;
/*      */ import javax.xml.crypto.KeySelectorResult;
/*      */ import javax.xml.crypto.URIDereferencer;
/*      */ import javax.xml.crypto.URIReference;
/*      */ import javax.xml.crypto.URIReferenceException;
/*      */ import javax.xml.crypto.XMLCryptoContext;
/*      */ import javax.xml.crypto.XMLStructure;
/*      */ import javax.xml.crypto.dsig.SignatureMethod;
/*      */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*      */ import javax.xml.crypto.dsig.keyinfo.KeyName;
/*      */ import javax.xml.crypto.dsig.keyinfo.KeyValue;
/*      */ import javax.xml.crypto.dsig.keyinfo.X509Data;
/*      */ import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
/*      */ import org.ietf.jgss.GSSException;
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
/*  138 */   private static KeySelectorImpl keyResolver = null;
/*  139 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.dsig", "com.sun.xml.wss.logging.impl.dsig.LogStrings");
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  145 */     keyResolver = new KeySelectorImpl();
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
/*      */   public static KeySelector getInstance() {
/*  157 */     return keyResolver;
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
/*  170 */     if (keyInfo == null) {
/*  171 */       if (logger.getLevel() == Level.SEVERE) {
/*  172 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1317_KEYINFO_NULL());
/*      */       }
/*  174 */       throw new KeySelectorException("Null KeyInfo object!");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  184 */       SignatureMethod sm = (SignatureMethod)method;
/*  185 */       List<XMLStructure> list = keyInfo.getContent();
/*  186 */       JAXBFilterProcessingContext wssContext = (JAXBFilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/*      */       
/*  188 */       SecurityPolicy securityPolicy = wssContext.getSecurityPolicy();
/*  189 */       boolean isBSP = false;
/*  190 */       if (securityPolicy != null) {
/*  191 */         if (PolicyTypeUtil.messagePolicy(securityPolicy)) {
/*  192 */           isBSP = ((MessagePolicy)securityPolicy).isBSP();
/*      */         } else {
/*  194 */           isBSP = ((WSSPolicy)securityPolicy).isBSP();
/*      */         } 
/*      */       }
/*      */       
/*  198 */       if (isBSP && list.size() > 1) {
/*  199 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1350_ILLEGAL_BSP_VIOLATION_KEY_INFO());
/*  200 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "BSP Violation of R5402: KeyInfo MUST have exactly one child", null);
/*      */       } 
/*      */ 
/*      */       
/*  204 */       boolean isStr = false;
/*      */       
/*  206 */       for (int i = 0; i < list.size(); i++) {
/*  207 */         XMLStructure xmlStructure = list.get(i);
/*  208 */         if (xmlStructure instanceof KeyValue) {
/*  209 */           PublicKey pk = null;
/*      */           try {
/*  211 */             pk = ((KeyValue)xmlStructure).getPublicKey();
/*  212 */           } catch (KeyException ke) {
/*  213 */             throw new KeySelectorException(ke);
/*      */           } 
/*      */ 
/*      */           
/*  217 */           if (purpose == KeySelector.Purpose.VERIFY) {
/*  218 */             X509Certificate cert = wssContext.getSecurityEnvironment().getCertificate(wssContext.getExtraneousProperties(), pk, false);
/*  219 */             wssContext.getSecurityEnvironment().validateCertificate(cert, wssContext.getExtraneousProperties());
/*      */           } 
/*      */           
/*  222 */           if (algEquals(sm.getAlgorithm(), pk.getAlgorithm())) {
/*  223 */             return new SimpleKeySelectorResult(pk);
/*      */           }
/*  225 */         } else if (xmlStructure instanceof JAXBStructure) {
/*  226 */           JAXBElement reference = ((JAXBStructure)xmlStructure).getJAXBElement();
/*  227 */           if (isSecurityTokenReference(reference)) {
/*  228 */             isStr = true;
/*  229 */             final Key key = resolve(reference, context, purpose);
/*  230 */             return new KeySelectorResult()
/*      */               {
/*      */                 public Key getKey() {
/*  233 */                   return key;
/*      */                 }
/*      */               };
/*      */           } 
/*  237 */         } else if (xmlStructure instanceof KeyName) {
/*  238 */           KeyName keyName = (KeyName)xmlStructure;
/*  239 */           Key returnKey = wssContext.getSecurityEnvironment().getSecretKey(wssContext.getExtraneousProperties(), keyName.getName(), false);
/*      */           
/*  241 */           if (returnKey == null) {
/*  242 */             X509Certificate cert = wssContext.getSecurityEnvironment().getCertificate(wssContext.getExtraneousProperties(), keyName.getName(), false);
/*      */             
/*  244 */             if (cert != null && algEquals(sm.getAlgorithm(), cert.getPublicKey().getAlgorithm())) {
/*  245 */               return new SimpleKeySelectorResult(cert.getPublicKey());
/*      */             }
/*      */           } else {
/*  248 */             return new SimpleKeySelectorResult(returnKey);
/*      */           } 
/*  250 */         } else if (xmlStructure instanceof X509Data) {
/*  251 */           final Key key = resolveX509Data(wssContext, (X509Data)xmlStructure, purpose);
/*  252 */           return new SimpleKeySelectorResult(key);
/*      */         }
/*      */       
/*      */       } 
/*  256 */     } catch (KeySelectorException kse) {
/*  257 */       throw kse;
/*  258 */     } catch (Exception ex) {
/*  259 */       logger.log(Level.FINEST, "Error occurred while resolving keyinformation" + ex.getMessage());
/*      */       
/*  261 */       throw new KeySelectorException(ex);
/*      */     } 
/*  263 */     throw new KeySelectorException("No KeyValue element found!");
/*      */   }
/*      */   
/*      */   private static class SimpleKeySelectorResult
/*      */     implements KeySelectorResult {
/*      */     private Key pk;
/*      */     
/*      */     SimpleKeySelectorResult(Key pk) {
/*  271 */       this.pk = pk;
/*      */     }
/*      */     
/*      */     public Key getKey() {
/*  275 */       return this.pk;
/*      */     }
/*      */   }
/*      */   
/*      */   private static Key resolve(JAXBElement<SecurityTokenReference> securityTokenReference, XMLCryptoContext context, KeySelector.Purpose purpose) throws KeySelectorException {
/*      */     try {
/*  281 */       JAXBFilterProcessingContext wssContext = (JAXBFilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/*  282 */       boolean isPolicyRecipient = (wssContext.getMode() == 3);
/*      */       
/*  284 */       SecurityPolicy securityPolicy = wssContext.getSecurityPolicy();
/*  285 */       boolean isBSP = false;
/*  286 */       if (securityPolicy != null) {
/*  287 */         if (PolicyTypeUtil.messagePolicy(securityPolicy)) {
/*  288 */           isBSP = ((MessagePolicy)securityPolicy).isBSP();
/*      */         } else {
/*  290 */           isBSP = ((WSSPolicy)securityPolicy).isBSP();
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  296 */       SecurityTokenReference str = securityTokenReference.getValue();
/*  297 */       Reference reference = str.getReference();
/*      */ 
/*      */ 
/*      */       
/*  301 */       Key returnKey = null;
/*  302 */       if (reference instanceof KeyIdentifier) {
/*  303 */         KeyIdentifier keyId = (KeyIdentifier)reference;
/*      */         
/*  305 */         returnKey = resolveKeyIdentifier(context, keyId.getValueType(), keyId.getReferenceValue(), null, purpose);
/*      */ 
/*      */       
/*      */       }
/*  309 */       else if (reference instanceof DirectReference) {
/*      */ 
/*      */ 
/*      */         
/*  313 */         DirectReference dReference = (DirectReference)reference;
/*  314 */         String uri = dReference.getURI();
/*  315 */         if (isBSP && !uri.startsWith("#")) {
/*  316 */           throw new XWSSecurityException("Violation of BSP R5204 : When a SECURITY_TOKEN_REFERENCE uses a Direct Reference to an INTERNAL_SECURITY_TOKEN, it MUST use a Shorthand XPointer Reference");
/*      */         }
/*      */         
/*  319 */         String valueType = dReference.getValueType();
/*  320 */         if ("http://schemas.xmlsoap.org/ws/2005/02/sc/dk".equals(valueType) || "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/dk".equals(valueType))
/*      */         {
/*      */           
/*  323 */           valueType = null;
/*      */         }
/*      */         
/*  326 */         returnKey = resolveDirectReference(context, valueType, uri, purpose);
/*      */       
/*      */       }
/*  329 */       else if (reference instanceof X509IssuerSerial) {
/*  330 */         X509IssuerSerial xis = (X509IssuerSerial)reference;
/*      */         
/*  332 */         BigInteger serialNumber = xis.getX509SerialNumber();
/*  333 */         String issuerName = xis.getX509IssuerName();
/*      */         
/*  335 */         resolveIssuerSerial(context, issuerName, serialNumber, xis.getId(), purpose);
/*      */       } else {
/*  337 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1308_UNSUPPORTED_REFERENCE_MECHANISM());
/*  338 */         KeySelectorException xwsse = new KeySelectorException("Key reference mechanism not supported");
/*      */ 
/*      */         
/*  341 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_UNSUPPORTED_SECURITY_TOKEN, xwsse.getMessage(), xwsse);
/*      */       } 
/*      */       
/*  344 */       return returnKey;
/*  345 */     } catch (XWSSecurityException xwsExp) {
/*  346 */       logger.log(Level.FINEST, "Error occurred while resolvingkey information", (Throwable)xwsExp);
/*      */       
/*  348 */       throw new KeySelectorException(xwsExp);
/*  349 */     } catch (Exception ex) {
/*  350 */       logger.log(Level.FINEST, "Error occurred while resolvingkey information", ex);
/*      */       
/*  352 */       throw new KeySelectorException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Key resolveIssuerSerial(XMLCryptoContext context, String issuerName, BigInteger serialNumber, String strId, KeySelector.Purpose purpose) throws KeySelectorException {
/*  360 */     Key returnKey = null;
/*  361 */     String normalizedIssuerName = RFC2253Parser.normalize(issuerName);
/*      */     try {
/*  363 */       JAXBFilterProcessingContext wssContext = (JAXBFilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/*  364 */       MLSPolicy inferredKB = wssContext.getSecurityContext().getInferredKB();
/*      */ 
/*      */       
/*  367 */       AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  368 */       x509Binding.setReferenceType("IssuerSerialNumber");
/*  369 */       if (inferredKB == null) {
/*  370 */         wssContext.getSecurityContext().setInferredKB((MLSPolicy)x509Binding);
/*  371 */       } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  372 */         ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  373 */       } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  374 */         DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  375 */         if (dktBind.getOriginalKeyBinding() == null) {
/*  376 */           dktBind.setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  377 */         } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  378 */           dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*      */         } 
/*      */       } 
/*      */       
/*  382 */       if (purpose == KeySelector.Purpose.VERIFY) {
/*  383 */         wssContext.setExtraneousProperty("requester.serial", serialNumber);
/*  384 */         wssContext.setExtraneousProperty("requester.issuername", normalizedIssuerName);
/*      */ 
/*      */ 
/*      */         
/*  388 */         X509Certificate cert = wssContext.getSecurityEnvironment().getCertificate(wssContext.getExtraneousProperties(), serialNumber, normalizedIssuerName);
/*      */         
/*  390 */         returnKey = cert.getPublicKey();
/*  391 */       } else if (purpose == KeySelector.Purpose.SIGN || purpose == KeySelector.Purpose.DECRYPT) {
/*  392 */         returnKey = wssContext.getSecurityEnvironment().getPrivateKey(wssContext.getExtraneousProperties(), serialNumber, normalizedIssuerName);
/*      */       } 
/*      */       
/*  395 */       if (strId != null) {
/*      */         
/*  397 */         try { X509Certificate cert = wssContext.getSecurityEnvironment().getCertificate(wssContext.getExtraneousProperties(), serialNumber, normalizedIssuerName);
/*      */           
/*  399 */           WSSElementFactory elementFactory = new WSSElementFactory(wssContext.getSOAPVersion());
/*  400 */           BinarySecurityToken binarySecurityToken = elementFactory.createBinarySecurityToken(null, cert.getEncoded());
/*  401 */           SSEData data = new SSEData((SecurityElement)binarySecurityToken, false, wssContext.getNamespaceContext());
/*  402 */           wssContext.getSTRTransformCache().put(strId, data); }
/*  403 */         catch (XWSSecurityException ex) {  }
/*  404 */         catch (CertificateEncodingException ex) {  }
/*  405 */         catch (Exception ex) {}
/*      */       
/*      */       }
/*      */     }
/*  409 */     catch (Exception ex) {
/*  410 */       logger.log(Level.FINEST, "Error occurred while resolvingkey information", ex);
/*      */       
/*  412 */       throw new KeySelectorException(ex);
/*      */     } 
/*  414 */     return returnKey;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Key resolveDirectReference(XMLCryptoContext context, String valueType, String uri, KeySelector.Purpose purpose) throws KeySelectorException {
/*  420 */     Key returnKey = null;
/*      */     try {
/*  422 */       JAXBFilterProcessingContext wssContext = (JAXBFilterProcessingContext)context.get("http://wss.sun.com#processingContext");
/*  423 */       MLSPolicy inferredKB = wssContext.getSecurityContext().getInferredKB();
/*  424 */       String wsuId = SOAPUtil.getIdFromFragmentRef(uri);
/*  425 */       boolean isSymmetric = false;
/*  426 */       if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0".equals(valueType) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken".equals(valueType))
/*  427 */       { UsernameTokenHeader token = null;
/*  428 */         token = (UsernameTokenHeader)resolveToken(wsuId, context);
/*  429 */         if (token == null) {
/*  430 */           throw new KeySelectorException("Token with Id " + wsuId + " not found");
/*      */         }
/*  432 */         AuthenticationTokenPolicy.UsernameTokenBinding untBinding = new AuthenticationTokenPolicy.UsernameTokenBinding();
/*  433 */         untBinding.setReferenceType("Direct");
/*  434 */         untBinding.setValueType(valueType);
/*  435 */         untBinding.setUseNonce(((AuthenticationTokenPolicy.UsernameTokenBinding)token.getPolicy()).getUseNonce());
/*  436 */         untBinding.setUseCreated(((AuthenticationTokenPolicy.UsernameTokenBinding)token.getPolicy()).getUseCreated());
/*      */         
/*  438 */         if (inferredKB == null) {
/*  439 */           wssContext.getSecurityContext().setInferredKB((MLSPolicy)untBinding);
/*  440 */           if (wssContext.getExtraneousProperty("EncryptedKey") != null) {
/*  441 */             isSymmetric = true;
/*      */           }
/*  443 */         } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  444 */           ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)untBinding);
/*  445 */           isSymmetric = true;
/*  446 */         } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  447 */           DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  448 */           if (dktBind.getOriginalKeyBinding() == null) {
/*  449 */             dktBind.setOriginalKeyBinding((WSSPolicy)untBinding);
/*  450 */           } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  451 */             dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)untBinding);
/*  452 */             isSymmetric = true;
/*      */           } 
/*      */         } 
/*  455 */         returnKey = resolveUsernameToken(wssContext, token, purpose, isSymmetric); }
/*      */       
/*  457 */       else if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3".equals(valueType) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1".equals(valueType))
/*      */       
/*  459 */       { X509BinarySecurityToken token = null;
/*  460 */         token = (X509BinarySecurityToken)resolveToken(wsuId, context);
/*  461 */         if (token == null) {
/*  462 */           throw new KeySelectorException("Token with Id " + wsuId + "not found");
/*      */         }
/*      */         
/*  465 */         AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  466 */         x509Binding.setReferenceType("Direct");
/*  467 */         x509Binding.setValueType(valueType);
/*  468 */         if (inferredKB == null) {
/*  469 */           wssContext.getSecurityContext().setInferredKB((MLSPolicy)x509Binding);
/*  470 */         } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  471 */           ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  472 */           isSymmetric = true;
/*  473 */         } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  474 */           DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  475 */           if (dktBind.getOriginalKeyBinding() == null) {
/*  476 */             dktBind.setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  477 */           } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  478 */             dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*  479 */             isSymmetric = true;
/*      */           } 
/*      */         } 
/*      */         
/*  483 */         returnKey = resolveX509Token(wssContext, token, purpose, isSymmetric); }
/*  484 */       else if ("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ1510".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ".equals(valueType))
/*      */       
/*  486 */       { KerberosBinarySecurityToken token = (KerberosBinarySecurityToken)resolveToken(wsuId, context);
/*  487 */         if (token == null) {
/*  488 */           throw new KeySelectorException("Token with Id " + wsuId + "not found");
/*      */         }
/*      */         
/*  491 */         SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/*  492 */         AuthenticationTokenPolicy.KerberosTokenBinding ktBinding = new AuthenticationTokenPolicy.KerberosTokenBinding();
/*  493 */         ktBinding.setReferenceType("Direct");
/*  494 */         ktBinding.setValueType(valueType);
/*  495 */         skBinding.setKeyBinding((MLSPolicy)ktBinding);
/*  496 */         if (inferredKB == null) {
/*  497 */           wssContext.getSecurityContext().setInferredKB((MLSPolicy)skBinding);
/*  498 */         } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  499 */           DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  500 */           if (dktBind.getOriginalKeyBinding() == null) {
/*  501 */             dktBind.setOriginalKeyBinding((WSSPolicy)skBinding);
/*  502 */           } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  503 */             dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)ktBinding);
/*  504 */             isSymmetric = true;
/*      */           } 
/*      */         } 
/*      */         
/*  508 */         returnKey = resolveKerberosToken(wssContext, token); }
/*  509 */       else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(valueType))
/*  510 */       { SymmetricKeyBinding symmetricKeyBinding; EncryptedKey token = (EncryptedKey)resolveToken(wsuId, context);
/*  511 */         if (token == null) {
/*  512 */           throw new KeySelectorException("Token with Id " + wsuId + "not found");
/*      */         }
/*      */         
/*  515 */         WSSPolicy skBinding = null;
/*  516 */         boolean saml = wssContext.getSecurityContext().getIsSAMLKeyBinding();
/*  517 */         if (saml) {
/*  518 */           AuthenticationTokenPolicy.SAMLAssertionBinding sAMLAssertionBinding = new AuthenticationTokenPolicy.SAMLAssertionBinding();
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  524 */           SymmetricKeyBinding symkBinding = new SymmetricKeyBinding();
/*      */ 
/*      */           
/*  527 */           symmetricKeyBinding = symkBinding;
/*      */         } 
/*      */         
/*  530 */         if (inferredKB == null) {
/*  531 */           wssContext.getSecurityContext().setInferredKB((MLSPolicy)symmetricKeyBinding);
/*  532 */         } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  533 */           (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  534 */           ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)symmetricKeyBinding);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  539 */         String algo = wssContext.getAlgorithmSuite().getEncryptionAlgorithm();
/*  540 */         returnKey = token.getKey(algo);
/*  541 */         symmetricKeyBinding.setKeyBinding((MLSPolicy)token.getInferredKB()); }
/*  542 */       else { if ("http://schemas.xmlsoap.org/ws/2005/02/sc/sct".equals(valueType) || "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct".equals(valueType)) {
/*      */           
/*  544 */           if (wssContext.isClient()) {
/*  545 */             returnKey = resolveSCT(wssContext, wsuId, purpose);
/*      */           }
/*  547 */           if (returnKey == null) {
/*  548 */             SecurityContextToken scToken = (SecurityContextToken)resolveToken(wsuId, context);
/*      */             
/*  550 */             if (scToken == null) {
/*  551 */               if (!wssContext.isClient()) {
/*      */                 
/*  553 */                 returnKey = resolveSCT(wssContext, wsuId, purpose);
/*      */               } else {
/*  555 */                 throw new KeySelectorException("Token with Id " + wsuId + "not found");
/*      */               } 
/*      */             } else {
/*  558 */               returnKey = resolveSCT(wssContext, scToken.getSCId(), purpose);
/*      */             } 
/*      */           } 
/*      */           
/*  562 */           SecureConversationTokenKeyBinding sctBinding = new SecureConversationTokenKeyBinding();
/*  563 */           if (inferredKB == null) {
/*  564 */             wssContext.getSecurityContext().setInferredKB((MLSPolicy)sctBinding);
/*  565 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  566 */             ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)sctBinding);
/*      */           } 
/*  568 */           return returnKey;
/*  569 */         }  if ("http://schemas.xmlsoap.org/ws/2005/02/sc/dk".equals(valueType) || "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/dk".equals(valueType)) {
/*      */           
/*  571 */           DerivedKeyToken token = (DerivedKeyToken)resolveToken(wsuId, context);
/*  572 */           if (token == null) {
/*  573 */             throw new KeySelectorException("Token with Id " + wsuId + "not found");
/*      */           }
/*  575 */           returnKey = token.getKey();
/*  576 */           DerivedTokenKeyBinding dtkBinding = new DerivedTokenKeyBinding();
/*  577 */           dtkBinding.setOriginalKeyBinding(token.getInferredKB());
/*  578 */           if (inferredKB == null) {
/*  579 */             wssContext.getSecurityContext().setInferredKB((MLSPolicy)dtkBinding);
/*  580 */           } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */         
/*      */         }
/*  587 */         else if (null == valueType) {
/*      */           
/*  589 */           SecurityHeaderElement token = resolveToken(wsuId, context);
/*  590 */           if (token == null) {
/*  591 */             throw new KeySelectorException("Token with Id " + wsuId + " not found");
/*      */           }
/*  593 */           if (token instanceof X509BinarySecurityToken) {
/*      */             
/*  595 */             AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  596 */             x509Binding.setReferenceType("Direct");
/*  597 */             if (inferredKB == null) {
/*  598 */               wssContext.getSecurityContext().setInferredKB((MLSPolicy)x509Binding);
/*  599 */             } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  600 */               ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  601 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  602 */               DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  603 */               if (dktBind.getOriginalKeyBinding() == null) {
/*  604 */                 dktBind.setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  605 */               } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  606 */                 dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*      */               } 
/*      */             } 
/*      */ 
/*      */             
/*  611 */             returnKey = resolveX509Token(wssContext, (X509BinarySecurityToken)token, purpose, isSymmetric);
/*  612 */           } else if (token instanceof EncryptedKey) {
/*      */             
/*  614 */             SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/*  615 */             AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  616 */             skBinding.setKeyBinding((MLSPolicy)x509Binding);
/*      */             
/*  618 */             if (inferredKB == null) {
/*  619 */               wssContext.getSecurityContext().setInferredKB((MLSPolicy)skBinding);
/*  620 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  621 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  622 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)skBinding);
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  627 */             String algo = wssContext.getAlgorithmSuite().getEncryptionAlgorithm();
/*  628 */             returnKey = ((EncryptedKey)token).getKey(algo);
/*  629 */           } else if (token instanceof DerivedKeyToken) {
/*      */             
/*  631 */             returnKey = ((DerivedKeyToken)token).getKey();
/*  632 */             inferredKB = wssContext.getSecurityContext().getInferredKB();
/*  633 */             DerivedTokenKeyBinding dtkBinding = new DerivedTokenKeyBinding();
/*  634 */             dtkBinding.setOriginalKeyBinding(((DerivedKeyToken)token).getInferredKB());
/*  635 */             if (inferredKB == null) {
/*  636 */               wssContext.getSecurityContext().setInferredKB((MLSPolicy)dtkBinding);
/*  637 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*      */ 
/*      */ 
/*      */             
/*      */             }
/*      */           
/*      */           }
/*  644 */           else if (token instanceof SecurityContextToken) {
/*      */             
/*  646 */             SecureConversationTokenKeyBinding sctBinding = new SecureConversationTokenKeyBinding();
/*  647 */             if (inferredKB == null) {
/*  648 */               wssContext.getSecurityContext().setInferredKB((MLSPolicy)sctBinding);
/*  649 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  650 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)sctBinding);
/*      */             } 
/*      */             
/*  653 */             returnKey = resolveSCT(wssContext, ((SecurityContextToken)token).getSCId(), purpose);
/*  654 */           } else if (token instanceof UsernameToken) {
/*  655 */             AuthenticationTokenPolicy.UsernameTokenBinding untBinding = new AuthenticationTokenPolicy.UsernameTokenBinding();
/*  656 */             untBinding.setReferenceType("Direct");
/*      */             
/*  658 */             if (((UsernameToken)token).getCreatedValue() != null) {
/*  659 */               untBinding.setUseCreated(true);
/*      */             }
/*  661 */             if (((UsernameToken)token).getNonceValue() != null) {
/*  662 */               untBinding.setUseNonce(true);
/*      */             }
/*  664 */             if (inferredKB == null) {
/*  665 */               wssContext.getSecurityContext().setInferredKB((MLSPolicy)untBinding);
/*  666 */             } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  667 */               (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  668 */               ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)untBinding);
/*      */             } 
/*      */ 
/*      */             
/*  672 */             returnKey = resolveUsernameToken(wssContext, (UsernameTokenHeader)token, purpose, isSymmetric);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  677 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1307_UNSUPPORTED_DIRECTREF_MECHANISM(new Object[] { valueType }));
/*  678 */           throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_INVALID_SECURITY_TOKEN, "unsupported directreference ValueType " + valueType, null);
/*      */         }  }
/*      */     
/*  681 */     } catch (XWSSecurityException ex) {
/*  682 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1377_ERROR_IN_RESOLVING_KEYINFO(), (Throwable)ex);
/*  683 */       throw new KeySelectorException(ex);
/*  684 */     } catch (URIReferenceException ex) {
/*  685 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1377_ERROR_IN_RESOLVING_KEYINFO(), ex);
/*  686 */       throw new KeySelectorException(ex);
/*      */     } 
/*      */     
/*  689 */     return returnKey;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Key resolveKeyIdentifier(XMLCryptoContext xc, String valueType, String referenceValue, String strId, KeySelector.Purpose purpose) throws KeySelectorException {
/*  695 */     JAXBFilterProcessingContext context = (JAXBFilterProcessingContext)xc.get("http://wss.sun.com#processingContext");
/*  696 */     Key returnKey = null;
/*  697 */     MLSPolicy inferredKB = context.getSecurityContext().getInferredKB();
/*  698 */     boolean isSymmetric = false;
/*      */     try {
/*  700 */       if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier".equals(valueType) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3SubjectKeyIdentifier".equals(valueType)) {
/*      */ 
/*      */         
/*  703 */         AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  704 */         x509Binding.setValueType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier");
/*  705 */         x509Binding.setReferenceType("Identifier");
/*  706 */         if (inferredKB == null) {
/*  707 */           context.getSecurityContext().setInferredKB((MLSPolicy)x509Binding);
/*  708 */         } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  709 */           ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  710 */           isSymmetric = true;
/*  711 */         } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  712 */           DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  713 */           if (dktBind.getOriginalKeyBinding() == null) {
/*  714 */             ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  715 */           } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  716 */             dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*  717 */             isSymmetric = true;
/*      */           } 
/*      */         } 
/*      */         
/*  721 */         byte[] keyIdBytes = XMLUtil.getDecodedBase64EncodedData(referenceValue);
/*  722 */         if (purpose == KeySelector.Purpose.VERIFY || purpose == KeySelector.Purpose.ENCRYPT) {
/*  723 */           context.setExtraneousProperty("requester.keyid", new String(keyIdBytes));
/*      */ 
/*      */           
/*  726 */           X509Certificate cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), keyIdBytes);
/*      */ 
/*      */           
/*  729 */           if (!isSymmetric && !context.isSamlSignatureKey()) {
/*  730 */             context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject((FilterProcessingContext)context), cert);
/*      */           }
/*      */           
/*  733 */           returnKey = cert.getPublicKey();
/*  734 */         } else if (purpose == KeySelector.Purpose.SIGN || purpose == KeySelector.Purpose.DECRYPT) {
/*  735 */           returnKey = context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), keyIdBytes);
/*      */         } 
/*      */ 
/*      */         
/*  739 */         if (strId != null) {
/*      */           
/*  741 */           try { X509Certificate cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), keyIdBytes, "Identifier");
/*      */             
/*  743 */             WSSElementFactory elementFactory = new WSSElementFactory(context.getSOAPVersion());
/*  744 */             BinarySecurityToken binarySecurityToken = elementFactory.createBinarySecurityToken(null, cert.getEncoded());
/*  745 */             SSEData data = new SSEData((SecurityElement)binarySecurityToken, false, context.getNamespaceContext());
/*  746 */             context.getSTRTransformCache().put(strId, data); }
/*  747 */           catch (XWSSecurityException ex) {  }
/*  748 */           catch (CertificateEncodingException ex) {  }
/*  749 */           catch (Exception ex) {}
/*      */         
/*      */         }
/*      */       }
/*  753 */       else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1".equals(valueType)) {
/*      */         
/*  755 */         AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  756 */         x509Binding.setValueType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1");
/*  757 */         x509Binding.setReferenceType("Identifier");
/*  758 */         if (inferredKB == null) {
/*  759 */           context.getSecurityContext().setInferredKB((MLSPolicy)x509Binding);
/*  760 */         } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)inferredKB)) {
/*  761 */           ((SymmetricKeyBinding)inferredKB).setKeyBinding((MLSPolicy)x509Binding);
/*  762 */           isSymmetric = true;
/*  763 */         } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB)) {
/*  764 */           DerivedTokenKeyBinding dktBind = (DerivedTokenKeyBinding)inferredKB;
/*  765 */           if (dktBind.getOriginalKeyBinding() == null) {
/*  766 */             ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)x509Binding);
/*  767 */           } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)dktBind.getOriginalKeyBinding())) {
/*  768 */             dktBind.getOriginalKeyBinding().setKeyBinding((MLSPolicy)x509Binding);
/*  769 */             isSymmetric = true;
/*      */           } 
/*      */         } 
/*      */         
/*  773 */         byte[] keyIdBytes = XMLUtil.getDecodedBase64EncodedData(referenceValue);
/*  774 */         if (purpose == KeySelector.Purpose.VERIFY || purpose == KeySelector.Purpose.ENCRYPT) {
/*  775 */           context.setExtraneousProperty("requester.keyid", new String(keyIdBytes));
/*  776 */           X509Certificate cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), keyIdBytes, "Thumbprint");
/*      */           
/*  778 */           if (!isSymmetric) {
/*  779 */             context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject((FilterProcessingContext)context), cert);
/*      */           }
/*      */           
/*  782 */           returnKey = cert.getPublicKey();
/*      */         }
/*  784 */         else if (purpose == KeySelector.Purpose.SIGN || purpose == KeySelector.Purpose.DECRYPT) {
/*  785 */           returnKey = context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), keyIdBytes, "Thumbprint");
/*      */         } 
/*      */ 
/*      */         
/*  789 */         if (strId != null) {
/*      */           
/*  791 */           try { X509Certificate cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), keyIdBytes, "Thumbprint");
/*      */             
/*  793 */             WSSElementFactory elementFactory = new WSSElementFactory(context.getSOAPVersion());
/*  794 */             BinarySecurityToken binarySecurityToken = elementFactory.createBinarySecurityToken(null, cert.getEncoded());
/*  795 */             SSEData data = new SSEData((SecurityElement)binarySecurityToken, false, context.getNamespaceContext());
/*  796 */             context.getSTRTransformCache().put(strId, data); }
/*  797 */           catch (XWSSecurityException ex) {  }
/*  798 */           catch (CertificateEncodingException ex) {  }
/*  799 */           catch (Exception ex) {}
/*      */         
/*      */         }
/*      */       }
/*  803 */       else if ("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5APREQSHA1".equals(valueType)) {
/*      */         
/*  805 */         SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/*  806 */         AuthenticationTokenPolicy.KerberosTokenBinding ktBinding = new AuthenticationTokenPolicy.KerberosTokenBinding();
/*  807 */         ktBinding.setReferenceType("Identifier");
/*  808 */         skBinding.setKeyBinding((MLSPolicy)ktBinding);
/*  809 */         if (inferredKB == null) {
/*  810 */           context.getSecurityContext().setInferredKB((MLSPolicy)skBinding);
/*  811 */         } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  812 */           (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  813 */           ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)skBinding);
/*      */         } 
/*      */ 
/*      */         
/*  817 */         String algo = SecurityUtil.getSecretKeyAlgorithm(context.getAlgorithmSuite().getEncryptionAlgorithm());
/*  818 */         KerberosContext krbContext = context.getKerberosContext();
/*  819 */         if (krbContext != null) {
/*  820 */           String encodedRef = (String)context.getExtraneousProperty("KerbSHA1Value");
/*  821 */           if (!referenceValue.equals(encodedRef)) {
/*  822 */             throw new XWSSecurityException("SecretKey could not be obtained, Incorrect Kerberos Context found");
/*      */           }
/*  824 */           returnKey = krbContext.getSecretKey(algo);
/*      */         } else {
/*  826 */           throw new XWSSecurityException("SecretKey could not be obtained, Kerberos Context not set");
/*      */         } 
/*  828 */       } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1".equals(valueType)) {
/*      */         
/*  830 */         SymmetricKeyBinding skBinding = new SymmetricKeyBinding();
/*  831 */         AuthenticationTokenPolicy.X509CertificateBinding x509Binding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*  832 */         x509Binding.setReferenceType("Identifier");
/*  833 */         skBinding.setKeyBinding((MLSPolicy)x509Binding);
/*      */         
/*  835 */         if (inferredKB == null) {
/*  836 */           context.getSecurityContext().setInferredKB((MLSPolicy)skBinding);
/*  837 */         } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  838 */           (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  839 */           ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)skBinding);
/*      */         } 
/*      */ 
/*      */         
/*  843 */         String ekSha1RefValue = (String)context.getExtraneousProperty("EncryptedKeySHA1");
/*  844 */         Key secretKey = (Key)context.getExtraneousProperty("SecretKey");
/*  845 */         String keyRefValue = referenceValue;
/*  846 */         if (ekSha1RefValue != null && secretKey != null) {
/*  847 */           if (ekSha1RefValue.equals(keyRefValue)) {
/*  848 */             returnKey = secretKey;
/*      */             
/*  850 */             skBinding.usesEKSHA1KeyBinding(true);
/*      */           } 
/*      */         } else {
/*  853 */           String message = "EncryptedKeySHA1 reference not correct";
/*  854 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1306_UNSUPPORTED_KEY_IDENTIFIER_REFERENCE_TYPE(), new Object[] { message });
/*  855 */           throw new KeySelectorException(message);
/*      */         } 
/*  857 */       } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(valueType) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(valueType)) {
/*      */ 
/*      */         
/*  860 */         IssuedTokenKeyBinding itkBinding = new IssuedTokenKeyBinding();
/*  861 */         if (inferredKB == null) {
/*  862 */           if (context.hasIssuedToken()) {
/*  863 */             context.getSecurityContext().setInferredKB((MLSPolicy)itkBinding);
/*      */           } else {
/*  865 */             context.getSecurityContext().setInferredKB((MLSPolicy)new AuthenticationTokenPolicy.SAMLAssertionBinding());
/*      */           } 
/*  867 */         } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)inferredKB) && (
/*  868 */           (DerivedTokenKeyBinding)inferredKB).getOriginalKeyBinding() == null) {
/*  869 */           ((DerivedTokenKeyBinding)inferredKB).setOriginalKeyBinding((WSSPolicy)itkBinding);
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  874 */         SecurityHeaderElement she = resolveToken(referenceValue, xc);
/*  875 */         if (she != null && she instanceof SAMLAssertion) {
/*  876 */           SAMLAssertion samlAssertion = (SAMLAssertion)she;
/*  877 */           returnKey = samlAssertion.getKey();
/*  878 */           if (strId != null && strId.length() > 0) {
/*  879 */             SSEData sSEData = new SSEData((SecurityElement)samlAssertion, false, context.getNamespaceContext());
/*  880 */             context.getElementCache().put(strId, sSEData);
/*      */           } 
/*      */         } else {
/*  883 */           HashMap sentSamlKeys = (HashMap)context.getExtraneousProperty("stored_saml_keys");
/*  884 */           if (sentSamlKeys != null) {
/*      */             
/*  886 */             context.getSecurityContext().setIsSAMLKeyBinding(true);
/*  887 */             returnKey = (Key)sentSamlKeys.get(referenceValue);
/*      */           } 
/*      */         } 
/*      */         
/*  891 */         if (context.hasIssuedToken() && returnKey != null) {
/*  892 */           SecurityTokenReference str = new SecurityTokenReference(context.getSOAPVersion());
/*  893 */           KeyIdentifier ki = new KeyIdentifier(context.getSOAPVersion());
/*  894 */           ki.setValueType(valueType);
/*  895 */           ki.setReferenceValue(referenceValue);
/*  896 */           str.setReference((Reference)ki);
/*  897 */           SecurityUtil.initInferredIssuedTokenContext((FilterProcessingContext)context, (Token)str, returnKey);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  902 */         returnKey = null;
/*      */       } 
/*  904 */     } catch (XWSSecurityException ex) {
/*  905 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1377_ERROR_IN_RESOLVING_KEYINFO(), (Throwable)ex);
/*  906 */       throw new KeySelectorException(ex);
/*  907 */     } catch (URIReferenceException ex) {
/*  908 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1377_ERROR_IN_RESOLVING_KEYINFO(), ex);
/*  909 */       throw new KeySelectorException(ex);
/*      */     } 
/*  911 */     return returnKey;
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
/*      */   private static boolean algEquals(String algURI, String algName) {
/*  923 */     if (algName.equalsIgnoreCase("DSA") && algURI.equalsIgnoreCase("http://www.w3.org/2000/09/xmldsig#dsa-sha1"))
/*      */     {
/*  925 */       return true; } 
/*  926 */     if (algName.equalsIgnoreCase("RSA") && algURI.equalsIgnoreCase("http://www.w3.org/2000/09/xmldsig#rsa-sha1"))
/*      */     {
/*  928 */       return true;
/*      */     }
/*  930 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Key resolveUsernameToken(JAXBFilterProcessingContext wssContext, UsernameTokenHeader token, KeySelector.Purpose purpose, boolean isSymmetric) throws XWSSecurityException {
/*  936 */     String algo = wssContext.getAlgorithmSuite().getSymmetricKeyAlgorithm();
/*  937 */     AuthenticationTokenPolicy.UsernameTokenBinding untBinding = new AuthenticationTokenPolicy.UsernameTokenBinding();
/*  938 */     String decodedSalt = token.getSalt();
/*  939 */     if (decodedSalt == null) {
/*  940 */       throw new XWSSecurityException("Salt retrieved from UsernameToken is null");
/*      */     }
/*  942 */     byte[] salt = null;
/*      */     try {
/*  944 */       salt = Base64.decode(decodedSalt);
/*  945 */     } catch (Base64DecodingException ex) {
/*  946 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_0144_UNABLETO_DECODE_BASE_64_DATA(ex), ex);
/*  947 */       throw new XWSSecurityException("exception during decoding the salt ");
/*      */     } 
/*  949 */     String password = null;
/*      */     try {
/*  951 */       password = wssContext.getSecurityEnvironment().authenticateUser(wssContext.getExtraneousProperties(), token.getUsernameValue());
/*  952 */     } catch (XWSSecurityException ex) {
/*  953 */       throw new XWSSecurityException("exception during retrieving the password using the username");
/*      */     } 
/*  955 */     if (password == null) {
/*  956 */       throw new XWSSecurityException("Password retrieved from UsernameToken is null");
/*      */     }
/*  958 */     String iterate = token.getIterations();
/*  959 */     if (iterate == null) {
/*  960 */       throw new XWSSecurityException("Value of Iterations  retrieved from UsernameToken is null");
/*      */     }
/*  962 */     int iterations = Integer.parseInt(iterate);
/*  963 */     PasswordDerivedKey pdk = new PasswordDerivedKey();
/*  964 */     SecretKey sKey = null;
/*  965 */     byte[] verifySignature = null;
/*  966 */     if (purpose == KeySelector.Purpose.DECRYPT) {
/*  967 */       salt[0] = 2;
/*  968 */       if (isSymmetric) {
/*      */         try {
/*  970 */           verifySignature = pdk.generate160BitKey(password, iterations, salt);
/*  971 */         } catch (UnsupportedEncodingException ex) {
/*  972 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1381_ERROR_GENERATING_160_BITKEY(), ex);
/*  973 */           throw new XWSSecurityException("error during generating 160 bit key ");
/*      */         } 
/*  975 */         untBinding.setSecretKey(verifySignature);
/*  976 */         sKey = untBinding.getSecretKey(SecurityUtil.getSecretKeyAlgorithm(algo));
/*  977 */         untBinding.setSecretKey(sKey);
/*  978 */         wssContext.setUsernameTokenBinding(untBinding);
/*  979 */         byte[] secretKey = untBinding.getSecretKey().getEncoded();
/*  980 */         SecretKey key = pdk.generate16ByteKeyforEncryption(secretKey);
/*  981 */         sKey = key;
/*      */       } else {
/*  983 */         byte[] decSignature = null;
/*      */         try {
/*  985 */           decSignature = pdk.generate160BitKey(password, iterations, salt);
/*  986 */         } catch (UnsupportedEncodingException ex) {
/*  987 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1381_ERROR_GENERATING_160_BITKEY(), ex);
/*  988 */           throw new XWSSecurityException("error during generating 160 bit key ");
/*      */         } 
/*  990 */         byte[] keyof128Bits = new byte[16];
/*  991 */         for (int i = 0; i < 16; i++) {
/*  992 */           keyof128Bits[i] = decSignature[i];
/*      */         }
/*  994 */         untBinding.setSecretKey(keyof128Bits);
/*  995 */         sKey = untBinding.getSecretKey(SecurityUtil.getSecretKeyAlgorithm(algo));
/*  996 */         untBinding.setSecretKey(sKey);
/*      */       } 
/*  998 */     } else if (purpose == KeySelector.Purpose.VERIFY) {
/*  999 */       salt[0] = 1;
/*      */       try {
/* 1001 */         verifySignature = pdk.generate160BitKey(password, iterations, salt);
/* 1002 */       } catch (UnsupportedEncodingException ex) {
/* 1003 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1381_ERROR_GENERATING_160_BITKEY(), ex);
/* 1004 */         throw new XWSSecurityException("error during generating 160 bit key ");
/*      */       } 
/* 1006 */       untBinding.setSecretKey(verifySignature);
/* 1007 */       sKey = untBinding.getSecretKey(SecurityUtil.getSecretKeyAlgorithm(algo));
/* 1008 */       untBinding.setSecretKey(sKey);
/*      */     }
/*      */     else {
/*      */       
/* 1012 */       salt[0] = 2;
/* 1013 */       byte[] key = null;
/*      */       try {
/* 1015 */         key = pdk.generate160BitKey(password, iterations, salt);
/* 1016 */       } catch (UnsupportedEncodingException ex) {
/* 1017 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1381_ERROR_GENERATING_160_BITKEY(), ex);
/* 1018 */         throw new XWSSecurityException("error during generating 160 bit key ");
/*      */       } 
/* 1020 */       byte[] sKeyof16ByteLength = new byte[16];
/* 1021 */       for (int i = 0; i < 16; i++) {
/* 1022 */         sKeyof16ByteLength[i] = key[i];
/*      */       }
/* 1024 */       untBinding.setSecretKey(sKeyof16ByteLength);
/* 1025 */       sKey = untBinding.getSecretKey(SecurityUtil.getSecretKeyAlgorithm(algo));
/*      */     } 
/* 1027 */     return sKey;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Key resolveX509Token(JAXBFilterProcessingContext context, X509BinarySecurityToken token, KeySelector.Purpose purpose, boolean isSymmetric) throws XWSSecurityException {
/* 1032 */     X509Certificate cert = token.getCertificate();
/* 1033 */     if (cert == null) {
/* 1034 */       cert = SOAPUtil.getCertificateFromToken((BinarySecurityToken)token);
/*      */     }
/* 1036 */     if (purpose == KeySelector.Purpose.VERIFY) {
/* 1037 */       if (!isSymmetric) {
/* 1038 */         context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject((FilterProcessingContext)context), cert);
/*      */       }
/*      */       
/* 1041 */       return cert.getPublicKey();
/* 1042 */     }  if (purpose == KeySelector.Purpose.SIGN || purpose == KeySelector.Purpose.DECRYPT) {
/* 1043 */       return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), cert);
/*      */     }
/*      */     
/* 1046 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Key resolveX509Data(JAXBFilterProcessingContext context, X509Data x509Data, KeySelector.Purpose purpose) throws KeySelectorException {
/* 1051 */     X509Certificate cert = null;
/*      */     try {
/* 1053 */       List data = x509Data.getContent();
/* 1054 */       Iterator iterator = data.iterator();
/* 1055 */       while (iterator.hasNext()) {
/* 1056 */         Object content = iterator.next();
/* 1057 */         if (content instanceof X509Certificate) {
/* 1058 */           cert = (X509Certificate)content;
/* 1059 */         } else if (content instanceof byte[]) {
/* 1060 */           byte[] ski = (byte[])content;
/* 1061 */           if (purpose == KeySelector.Purpose.VERIFY) {
/*      */ 
/*      */             
/* 1064 */             cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), ski);
/*      */ 
/*      */             
/* 1067 */             context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject((FilterProcessingContext)context), cert);
/*      */             
/* 1069 */             return cert.getPublicKey();
/* 1070 */           }  if (purpose == KeySelector.Purpose.SIGN) {
/* 1071 */             return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), ski);
/*      */           }
/*      */         } else {
/* 1074 */           if (content instanceof String) {
/* 1075 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1312_UNSUPPORTED_KEYINFO());
/* 1076 */             throw new KeySelectorException("X509SubjectName child element of X509Data is not yet supported by our implementation");
/*      */           } 
/* 1078 */           if (content instanceof X509IssuerSerial) {
/* 1079 */             X509IssuerSerial xis = (X509IssuerSerial)content;
/* 1080 */             if (purpose == KeySelector.Purpose.VERIFY) {
/*      */ 
/*      */               
/* 1083 */               cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), xis.getSerialNumber(), xis.getIssuerName());
/*      */               
/* 1085 */               context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject((FilterProcessingContext)context), cert);
/*      */               
/* 1087 */               return cert.getPublicKey();
/* 1088 */             }  if (purpose == KeySelector.Purpose.SIGN) {
/* 1089 */               return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), xis.getSerialNumber(), xis.getIssuerName());
/*      */             }
/*      */           }
/*      */           else {
/*      */             
/* 1094 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1312_UNSUPPORTED_KEYINFO());
/* 1095 */             throw new KeySelectorException("Unsupported child element of X509Data encountered");
/*      */           } 
/*      */         } 
/*      */         
/* 1099 */         if (purpose == KeySelector.Purpose.VERIFY) {
/* 1100 */           context.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject((FilterProcessingContext)context), cert);
/*      */           
/* 1102 */           return cert.getPublicKey();
/* 1103 */         }  if (purpose == KeySelector.Purpose.SIGN) {
/* 1104 */           return context.getSecurityEnvironment().getPrivateKey(context.getExtraneousProperties(), cert);
/*      */         }
/*      */       }
/*      */     
/* 1108 */     } catch (Exception e) {
/* 1109 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1314_ILLEGAL_X_509_DATA(e.getMessage()), e.getMessage());
/* 1110 */       throw new KeySelectorException(e);
/*      */     } 
/* 1112 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected static SecurityHeaderElement resolveToken(final String uri, XMLCryptoContext context) throws URIReferenceException, XWSSecurityException {
/* 1117 */     URIDereferencer resolver = context.getURIDereferencer();
/*      */     
/* 1119 */     URIReference uriRef = new URIReference()
/*      */       {
/*      */         public String getURI() {
/* 1122 */           return uri;
/*      */         }
/*      */         
/*      */         public String getType() {
/* 1126 */           return null;
/*      */         }
/*      */       };
/*      */     try {
/* 1130 */       StreamWriterData data = (StreamWriterData)resolver.dereference(uriRef, context);
/*      */       
/* 1132 */       if (data == null) {
/* 1133 */         return null;
/*      */       }
/* 1135 */       Object derefData = data.getDereferencedObject();
/* 1136 */       SecurityHeaderElement she = null;
/* 1137 */       if (derefData instanceof SecurityHeaderElement) {
/* 1138 */         she = (SecurityHeaderElement)derefData;
/*      */       }
/*      */       
/* 1141 */       if (she == null) {
/* 1142 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1304_FC_SECURITY_TOKEN_UNAVAILABLE());
/* 1143 */         throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_SECURITY_TOKEN_UNAVAILABLE, "Referenced Security Token could not be retrieved", null);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1149 */       if ("BinarySecurityToken".equals(she.getLocalPart())) {
/* 1150 */         BinarySecurityToken token = (BinarySecurityToken)she;
/* 1151 */         if ("http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ1510".equals(token.getValueType()) || "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ".equals(token.getValueType()))
/*      */         {
/* 1153 */           return (KerberosBinarySecurityToken)token;
/*      */         }
/* 1155 */         X509BinarySecurityToken x509bst = (X509BinarySecurityToken)token;
/* 1156 */         return x509bst;
/*      */       } 
/* 1158 */       if ("EncryptedKey".equals(she.getLocalPart()))
/* 1159 */         return she; 
/* 1160 */       if ("SecurityContextToken".equals(she.getLocalPart()))
/* 1161 */         return she; 
/* 1162 */       if ("DerivedKeyToken".equals(she.getLocalPart()))
/* 1163 */         return she; 
/* 1164 */       if ("Assertion".equals(she.getLocalPart()))
/*      */       {
/* 1166 */         return she; } 
/* 1167 */       if ("UsernameToken".equals(she.getLocalPart())) {
/* 1168 */         return she;
/*      */       }
/* 1170 */     } catch (URIReferenceException ure) {
/* 1171 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1304_FC_SECURITY_TOKEN_UNAVAILABLE(), ure);
/* 1172 */       throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_SECURITY_TOKEN_UNAVAILABLE, "Referenced Security Token could not be retrieved", ure);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1178 */     if (logger.isLoggable(Level.SEVERE)) {
/* 1179 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1305_UN_SUPPORTED_SECURITY_TOKEN());
/*      */     }
/* 1181 */     throw SOAPUtil.newSOAPFaultException(MessageConstants.WSSE_UNSUPPORTED_SECURITY_TOKEN, "A Unsupported token was provided ", null);
/*      */   }
/*      */   
/*      */   private static boolean isSecurityTokenReference(JAXBElement reference) {
/* 1185 */     String local = reference.getName().getLocalPart();
/* 1186 */     String uri = reference.getName().getNamespaceURI();
/* 1187 */     if ("SecurityTokenReference".equals(local) && "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(uri))
/*      */     {
/* 1189 */       return true;
/*      */     }
/* 1191 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Key resolveSCT(JAXBFilterProcessingContext wssContext, String scId, KeySelector.Purpose purpose) throws XWSSecurityException {
/* 1197 */     IssuedTokenContext ctx = null;
/*      */     
/* 1199 */     String protocol = wssContext.getWSSCVersion(wssContext.getSecurityPolicyVersion());
/* 1200 */     if (wssContext.isClient()) {
/* 1201 */       DefaultSCTokenConfiguration defaultSCTokenConfiguration = new DefaultSCTokenConfiguration(protocol, scId, !wssContext.isExpired(), !wssContext.isInboundMessage());
/* 1202 */       ctx = IssuedTokenManager.getInstance().createIssuedTokenContext((IssuedTokenConfiguration)defaultSCTokenConfiguration, null);
/*      */       try {
/* 1204 */         IssuedTokenManager.getInstance().getIssuedToken(ctx);
/* 1205 */       } catch (WSTrustException e) {
/* 1206 */         throw new XWSSecurityException(e);
/*      */       } 
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
/* 1224 */       if (ctx == null || ctx.getSecurityPolicy().isEmpty())
/*      */       {
/* 1226 */         return null;
/*      */       }
/*      */     } else {
/*      */       
/* 1230 */       System.out.println("context.isExpired >>> " + wssContext.isExpired());
/* 1231 */       ctx = ((SessionManager)wssContext.getExtraneousProperty("SessionManager")).getSecurityContext(scId, !wssContext.isExpired());
/* 1232 */       URI sctId = null;
/* 1233 */       String sctIns = null;
/* 1234 */       String wsuId = null;
/* 1235 */       SecurityContextToken sct = (SecurityContextToken)ctx.getSecurityToken();
/* 1236 */       if (sct != null) {
/* 1237 */         sctId = sct.getIdentifier();
/* 1238 */         sctIns = sct.getInstance();
/* 1239 */         wsuId = sct.getWsuId();
/*      */       } else {
/* 1241 */         SecurityContextTokenInfo sctInfo = ctx.getSecurityContextTokenInfo();
/* 1242 */         sctId = URI.create(sctInfo.getIdentifier());
/* 1243 */         sctIns = sctInfo.getInstance();
/* 1244 */         wsuId = sctInfo.getExternalId();
/*      */       } 
/* 1246 */       ctx.setSecurityToken((Token)WSTrustElementFactory.newInstance(protocol).createSecurityContextToken(sctId, sctIns, wsuId));
/*      */     } 
/*      */ 
/*      */     
/* 1250 */     Subject subj = ctx.getRequestorSubject();
/* 1251 */     if (subj != null)
/*      */     {
/* 1253 */       if (wssContext.getExtraneousProperty("SCBOOTSTRAP_CRED_IN_SUBJ") == null) {
/*      */         
/* 1255 */         wssContext.getSecurityEnvironment().updateOtherPartySubject(SecurityUtil.getSubject(wssContext.getExtraneousProperties()), subj);
/*      */         
/* 1257 */         wssContext.getExtraneousProperties().put("SCBOOTSTRAP_CRED_IN_SUBJ", "true");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1262 */     byte[] proofKey = null;
/*      */     
/* 1264 */     if (wssContext.getWSCInstance() != null) {
/* 1265 */       if (wssContext.isExpired()) {
/* 1266 */         proofKey = ctx.getProofKey();
/*      */       } else {
/* 1268 */         SecurityContextTokenInfo sctInstanceInfo = ctx.getSecurityContextTokenInfo();
/* 1269 */         proofKey = sctInstanceInfo.getInstanceSecret(wssContext.getWSCInstance());
/*      */       } 
/*      */     } else {
/* 1272 */       proofKey = ctx.getProofKey();
/*      */     } 
/* 1274 */     wssContext.setExtraneousProperty("Incoming_SCT", ctx.getSecurityToken());
/*      */ 
/*      */     
/* 1277 */     if (proofKey == null) {
/* 1278 */       throw new XWSSecurityException("Could not locate SecureConversation session for Id:" + scId);
/*      */     }
/*      */     
/* 1281 */     String algo = "AES";
/* 1282 */     if (wssContext.getAlgorithmSuite() != null) {
/* 1283 */       algo = SecurityUtil.getSecretKeyAlgorithm(wssContext.getAlgorithmSuite().getEncryptionAlgorithm());
/*      */     }
/* 1285 */     SecretKeySpec key = new SecretKeySpec(proofKey, algo);
/* 1286 */     return key;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Key resolveKerberosToken(JAXBFilterProcessingContext wssContext, KerberosBinarySecurityToken token) throws XWSSecurityException {
/* 1291 */     String encodedRef = (String)wssContext.getExtraneousProperty("KerbSHA1Value");
/*      */     
/* 1293 */     if (encodedRef == null) {
/*      */       try {
/* 1295 */         byte[] krbSha1 = MessageDigest.getInstance("SHA-1").digest(token.getTokenValue());
/* 1296 */         encodedRef = Base64.encode(krbSha1);
/* 1297 */       } catch (NoSuchAlgorithmException nsae) {
/* 1298 */         throw new XWSSecurityException(nsae);
/*      */       } 
/*      */     }
/* 1301 */     String algo = SecurityUtil.getSecretKeyAlgorithm(wssContext.getAlgorithmSuite().getEncryptionAlgorithm());
/* 1302 */     KerberosContext krbContext = wssContext.getKerberosContext();
/*      */     
/* 1304 */     if (krbContext == null) {
/* 1305 */       krbContext = wssContext.getSecurityEnvironment().doKerberosLogin(token.getTokenValue());
/* 1306 */       wssContext.setKerberosContext(krbContext);
/*      */       try {
/* 1308 */         wssContext.getSecurityEnvironment().updateOtherPartySubject(DefaultSecurityEnvironmentImpl.getSubject((FilterProcessingContext)wssContext), krbContext.getGSSContext().getSrcName(), krbContext.getDelegatedCredentials());
/*      */       }
/* 1310 */       catch (GSSException gsse) {
/* 1311 */         throw new XWSSecurityException(gsse);
/*      */       } 
/*      */     } 
/* 1314 */     wssContext.setExtraneousProperty("KerbSHA1Value", encodedRef);
/* 1315 */     return krbContext.getSecretKey(algo);
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\KeySelectorImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */