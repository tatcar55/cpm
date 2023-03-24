/*      */ package com.sun.xml.wss.impl.apachecrypto;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
/*      */ import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
/*      */ import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
/*      */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*      */ import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
/*      */ import com.sun.xml.ws.security.IssuedTokenContext;
/*      */ import com.sun.xml.ws.security.SecurityContextToken;
/*      */ import com.sun.xml.ws.security.SecurityTokenReference;
/*      */ import com.sun.xml.ws.security.impl.DerivedKeyTokenImpl;
/*      */ import com.sun.xml.ws.security.trust.GenericToken;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.core.DerivedKeyTokenHeaderBlock;
/*      */ import com.sun.xml.wss.core.EncryptedDataHeaderBlock;
/*      */ import com.sun.xml.wss.core.EncryptedHeaderBlock;
/*      */ import com.sun.xml.wss.core.KeyInfoHeaderBlock;
/*      */ import com.sun.xml.wss.core.ReferenceElement;
/*      */ import com.sun.xml.wss.core.ReferenceListHeaderBlock;
/*      */ import com.sun.xml.wss.core.SecurityContextTokenImpl;
/*      */ import com.sun.xml.wss.core.SecurityHeader;
/*      */ import com.sun.xml.wss.core.SecurityHeaderBlock;
/*      */ import com.sun.xml.wss.core.SecurityTokenReference;
/*      */ import com.sun.xml.wss.core.X509SecurityToken;
/*      */ import com.sun.xml.wss.core.reference.DirectReference;
/*      */ import com.sun.xml.wss.core.reference.EncryptedKeySHA1Identifier;
/*      */ import com.sun.xml.wss.impl.AlgorithmSuite;
/*      */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*      */ import com.sun.xml.wss.impl.MessageConstants;
/*      */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*      */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*      */ import com.sun.xml.wss.impl.XMLUtil;
/*      */ import com.sun.xml.wss.impl.keyinfo.KeyIdentifierStrategy;
/*      */ import com.sun.xml.wss.impl.keyinfo.KeyInfoStrategy;
/*      */ import com.sun.xml.wss.impl.keyinfo.KeyNameStrategy;
/*      */ import com.sun.xml.wss.impl.misc.Base64;
/*      */ import com.sun.xml.wss.impl.misc.KeyResolver;
/*      */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*      */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*      */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.Target;
/*      */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*      */ import com.sun.xml.wss.impl.resolver.AttachmentSignatureInput;
/*      */ import com.sun.xml.wss.saml.Assertion;
/*      */ import com.sun.xml.wss.saml.SAMLException;
/*      */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.Assertion;
/*      */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.Assertion;
/*      */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.Assertion;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.security.Key;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.cert.X509Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Vector;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.activation.DataHandler;
/*      */ import javax.activation.DataSource;
/*      */ import javax.crypto.Cipher;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.crypto.spec.SecretKeySpec;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.soap.AttachmentPart;
/*      */ import javax.xml.soap.MimeHeader;
/*      */ import javax.xml.soap.SOAPElement;
/*      */ import javax.xml.soap.SOAPPart;
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
/*      */ public class EncryptionProcessor
/*      */ {
/*  148 */   private static byte[] crlf = null;
/*  149 */   protected static final Logger log = Logger.getLogger("com.sun.xml.wss.logging.impl.crypto", "com.sun.xml.wss.logging.impl.crypto.LogStrings");
/*      */   
/*      */   static {
/*      */     try {
/*  153 */       crlf = "\r\n".getBytes("US-ASCII");
/*  154 */     } catch (UnsupportedEncodingException ue) {
/*      */       
/*  156 */       if (log != null) {
/*  157 */         log.log(Level.SEVERE, "WSS1204.crlf.init.failed", ue);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void encrypt(FilterProcessingContext context) throws XWSSecurityException {
/*      */     DerivedTokenKeyBinding derivedTokenKeyBinding;
/*      */     X509SecurityToken x509SecurityToken;
/*  168 */     SecurableSoapMessage secureMsg = context.getSecurableSoapMessage();
/*  169 */     SecurityHeader _secHeader = secureMsg.findOrCreateSecurityHeader();
/*      */     
/*  171 */     boolean _exportCertificate = false;
/*  172 */     SecretKey _symmetricKey = null;
/*  173 */     SecretKey keyEncSK = null;
/*      */     
/*  175 */     X509Certificate _x509Cert = null;
/*  176 */     Key samlkey = null;
/*  177 */     KeyInfoStrategy keyInfoStrategy = null;
/*      */     
/*  179 */     String referenceType = null;
/*  180 */     String x509TokenId = null;
/*  181 */     String keyEncAlgo = "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
/*  182 */     String dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*  183 */     String symmetricKeyName = null;
/*      */     
/*  185 */     AuthenticationTokenPolicy.X509CertificateBinding certificateBinding = null;
/*      */     
/*  187 */     WSSPolicy wssPolicy = (WSSPolicy)context.getSecurityPolicy();
/*  188 */     EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)wssPolicy.getFeatureBinding();
/*  189 */     WSSPolicy keyBinding = (WSSPolicy)wssPolicy.getKeyBinding();
/*      */     
/*  191 */     AlgorithmSuite algSuite = context.getAlgorithmSuite();
/*      */     
/*  193 */     SecurityTokenReference samlTokenRef = null;
/*  194 */     SecurityTokenReference secConvRef = null;
/*  195 */     SecurityTokenReference ekTokenRef = null;
/*  196 */     SecurityTokenReference dktSctTokenRef = null;
/*  197 */     SecurityTokenReference issuedTokenRef = null;
/*      */     
/*  199 */     SecurityTokenReference ekDirectRef = null;
/*      */     
/*  201 */     DerivedKeyTokenHeaderBlock dktHeadrBlock = null;
/*      */     
/*  203 */     SecurityContextTokenImpl sct = null;
/*  204 */     boolean sctTokenInserted = false;
/*  205 */     SOAPElement sctElement = null;
/*  206 */     boolean sctWithDKT = false;
/*  207 */     boolean includeSCT = true;
/*      */     
/*  209 */     boolean issuedWithDKT = false;
/*  210 */     SecurityTokenReference dktIssuedTokenRef = null;
/*  211 */     SOAPElement issuedTokenElement = null;
/*  212 */     Element issuedTokenElementFromMsg = null;
/*  213 */     boolean issuedTokenInserted = false;
/*  214 */     boolean includeIST = true;
/*      */     
/*  216 */     boolean dktSender = false;
/*      */ 
/*      */     
/*  219 */     Key originalKey = null;
/*  220 */     String ekId = context.getSecurableSoapMessage().generateId();
/*  221 */     String insertedEkId = null;
/*      */     
/*  223 */     boolean skbX509TokenInserted = false;
/*      */     
/*  225 */     boolean useStandaloneRefList = false;
/*      */     
/*  227 */     HashMap<String, String> ekCache = context.getEncryptedKeyCache();
/*      */     
/*  229 */     SOAPElement x509TokenElement = null;
/*      */     
/*  231 */     SecurableSoapMessage secureMessage = context.getSecurableSoapMessage();
/*      */     
/*  233 */     if (log.isLoggable(Level.FINEST)) {
/*  234 */       log.log(Level.FINEST, "KeyBinding in Encryption is " + keyBinding);
/*      */     }
/*      */     
/*  237 */     boolean wss11Receiver = "true".equals(context.getExtraneousProperty("EnableWSS11PolicyReceiver"));
/*  238 */     boolean wss11Sender = "true".equals(context.getExtraneousProperty("EnableWSS11PolicySender"));
/*  239 */     boolean sendEKSHA1 = (wss11Receiver && wss11Sender && getEKSHA1Ref(context) != null);
/*  240 */     boolean wss10 = !wss11Sender;
/*      */     
/*  242 */     String tmp = featureBinding.getDataEncryptionAlgorithm();
/*  243 */     if ((tmp == null || "".equals(tmp)) && 
/*  244 */       context.getAlgorithmSuite() != null) {
/*  245 */       tmp = context.getAlgorithmSuite().getEncryptionAlgorithm();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  251 */     if (tmp != null && !"".equals(tmp)) {
/*  252 */       dataEncAlgo = tmp;
/*      */     }
/*      */     
/*  255 */     if (context.getAlgorithmSuite() != null) {
/*  256 */       keyEncAlgo = context.getAlgorithmSuite().getAsymmetricKeyAlgorithm();
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  261 */     if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)keyBinding)) {
/*  262 */       DerivedTokenKeyBinding dtk = (DerivedTokenKeyBinding)keyBinding.clone();
/*  263 */       WSSPolicy originalKeyBinding = dtk.getOriginalKeyBinding();
/*      */       
/*  265 */       if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)originalKeyBinding)) {
/*  266 */         AuthenticationTokenPolicy.X509CertificateBinding ckBindingClone = (AuthenticationTokenPolicy.X509CertificateBinding)originalKeyBinding.clone();
/*      */ 
/*      */         
/*  269 */         SymmetricKeyBinding skb = new SymmetricKeyBinding();
/*  270 */         skb.setKeyBinding((MLSPolicy)ckBindingClone);
/*      */         
/*  272 */         dtk.setOriginalKeyBinding((WSSPolicy)skb);
/*  273 */         derivedTokenKeyBinding = dtk;
/*      */       } 
/*      */     } 
/*      */     
/*  277 */     if (PolicyTypeUtil.usernameTokenPolicy((SecurityPolicy)derivedTokenKeyBinding)) {
/*  278 */       log.log(Level.SEVERE, "WSS1210.unsupported.UsernameToken.AsKeyBinding.EncryptionPolicy");
/*  279 */       throw new XWSSecurityException("UsernameToken as KeyBinding for EncryptionPolicy is Not Yet Supported");
/*  280 */     }  if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)derivedTokenKeyBinding)) {
/*      */       
/*  282 */       useStandaloneRefList = true;
/*  283 */       if (context.getX509CertificateBinding() != null) {
/*  284 */         certificateBinding = context.getX509CertificateBinding();
/*  285 */         context.setX509CertificateBinding(null);
/*      */       } else {
/*  287 */         certificateBinding = (AuthenticationTokenPolicy.X509CertificateBinding)derivedTokenKeyBinding;
/*      */       } 
/*      */       
/*  290 */       x509TokenId = certificateBinding.getUUID();
/*  291 */       if (x509TokenId == null || x509TokenId.equals("")) {
/*  292 */         x509TokenId = secureMsg.generateId();
/*      */       }
/*  294 */       if (log.isLoggable(Level.FINEST)) {
/*  295 */         log.log(Level.FINEST, "Certificate was " + _x509Cert);
/*  296 */         log.log(Level.FINEST, "BinaryToken ID " + x509TokenId);
/*      */       } 
/*      */       
/*  299 */       HashMap tokenCache = context.getTokenCache();
/*  300 */       HashMap<String, X509SecurityToken> insertedX509Cache = context.getInsertedX509Cache();
/*      */       
/*  302 */       SecurityUtil.checkIncludeTokenPolicy(context, certificateBinding, x509TokenId);
/*      */       
/*  304 */       _x509Cert = certificateBinding.getX509Certificate();
/*  305 */       referenceType = certificateBinding.getReferenceType();
/*  306 */       if (referenceType.equals("Identifier") && certificateBinding.getValueType().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1")) {
/*  307 */         log.log(Level.SEVERE, "WSS1211.unsupported.KeyIdentifierStrategy.X509v1");
/*  308 */         throw new XWSSecurityException("Key Identifier strategy with X509v1 certificate is not allowed");
/*      */       } 
/*  310 */       keyInfoStrategy = KeyInfoStrategy.getInstance(referenceType);
/*  311 */       _exportCertificate = true;
/*  312 */       keyInfoStrategy.setCertificate(_x509Cert);
/*      */       
/*  314 */       if ("Direct".equals(referenceType)) {
/*      */         
/*  316 */         X509SecurityToken token = (X509SecurityToken)tokenCache.get(x509TokenId);
/*  317 */         if (token == null) {
/*  318 */           String valueType = certificateBinding.getValueType();
/*  319 */           if (valueType == null || valueType.equals(""))
/*      */           {
/*  321 */             valueType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
/*      */           }
/*  323 */           token = new X509SecurityToken(secureMsg.getSOAPPart(), _x509Cert, x509TokenId, valueType);
/*      */         } 
/*  325 */         if (insertedX509Cache.get(x509TokenId) == null) {
/*  326 */           secureMsg.findOrCreateSecurityHeader().insertHeaderBlock((SecurityHeaderBlock)token);
/*  327 */           insertedX509Cache.put(x509TokenId, token);
/*  328 */           x509TokenElement = secureMsg.findOrCreateSecurityHeader().getNextSiblingOfTimestamp();
/*      */         } else {
/*  330 */           x509TokenElement = secureMsg.getElementByWsuId(x509TokenId);
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  338 */       tmp = null;
/*  339 */       tmp = certificateBinding.getKeyAlgorithm();
/*  340 */       if (tmp != null && !tmp.equals("")) {
/*  341 */         keyEncAlgo = tmp;
/*      */       }
/*  343 */       _symmetricKey = SecurityUtil.generateSymmetricKey(dataEncAlgo);
/*  344 */     } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)derivedTokenKeyBinding)) {
/*  345 */       SymmetricKeyBinding skb = null;
/*  346 */       if (context.getSymmetricKeyBinding() != null) {
/*  347 */         skb = context.getSymmetricKeyBinding();
/*  348 */         context.setSymmetricKeyBinding(null);
/*      */       } else {
/*  350 */         skb = (SymmetricKeyBinding)derivedTokenKeyBinding;
/*      */       } 
/*      */       
/*  353 */       KeyInfoHeaderBlock keyInfoBlock = null;
/*      */       
/*  355 */       if (!skb.getKeyIdentifier().equals(MessageConstants._EMPTY)) {
/*  356 */         keyEncAlgo = skb.getKeyAlgorithm();
/*  357 */         if (keyEncAlgo != null && !"".equals(keyEncAlgo)) {
/*  358 */           _symmetricKey = SecurityUtil.generateSymmetricKey(dataEncAlgo);
/*      */         }
/*  360 */         keyInfoStrategy = KeyInfoStrategy.getInstance("KeyName");
/*  361 */         keyEncSK = skb.getSecretKey();
/*  362 */         symmetricKeyName = skb.getKeyIdentifier();
/*  363 */         String secKeyAlgo = keyEncSK.getAlgorithm();
/*      */         
/*  365 */         if (_symmetricKey == null) {
/*  366 */           ((KeyNameStrategy)keyInfoStrategy).setKeyName(symmetricKeyName);
/*  367 */           _symmetricKey = keyEncSK;
/*  368 */           keyEncSK = null;
/*      */         } 
/*  370 */       } else if (sendEKSHA1) {
/*      */         
/*  372 */         String ekSha1Ref = getEKSHA1Ref(context);
/*  373 */         _symmetricKey = skb.getSecretKey();
/*      */         
/*  375 */         keyInfoBlock = new KeyInfoHeaderBlock(secureMessage.getSOAPPart());
/*  376 */         ekTokenRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/*  377 */         EncryptedKeySHA1Identifier refElem = new EncryptedKeySHA1Identifier(secureMessage.getSOAPPart());
/*  378 */         refElem.setReferenceValue(ekSha1Ref);
/*  379 */         ekTokenRef.setReference((ReferenceElement)refElem);
/*      */ 
/*      */ 
/*      */         
/*  383 */         referenceType = "EncryptedKeySHA1";
/*  384 */         keyInfoStrategy = KeyInfoStrategy.getInstance(referenceType);
/*      */ 
/*      */       
/*      */       }
/*  388 */       else if (wss11Sender || wss10) {
/*  389 */         _symmetricKey = skb.getSecretKey();
/*  390 */         useStandaloneRefList = true;
/*      */         
/*  392 */         if (!skb.getCertAlias().equals(MessageConstants._EMPTY)) {
/*  393 */           certificateBinding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*      */           
/*  395 */           certificateBinding.setCertificateIdentifier(skb.getCertAlias());
/*  396 */           _x509Cert = context.getSecurityEnvironment().getCertificate(context.getExtraneousProperties(), certificateBinding.getCertificateIdentifier(), false);
/*  397 */           certificateBinding.setX509Certificate(_x509Cert);
/*  398 */           certificateBinding.setReferenceType("Direct");
/*  399 */         } else if (context.getX509CertificateBinding() != null) {
/*  400 */           certificateBinding = context.getX509CertificateBinding();
/*  401 */           context.setX509CertificateBinding(null);
/*      */         } 
/*      */         
/*  404 */         _x509Cert = certificateBinding.getX509Certificate();
/*  405 */         x509TokenId = certificateBinding.getUUID();
/*  406 */         if (x509TokenId == null || x509TokenId.equals("")) {
/*  407 */           x509TokenId = secureMsg.generateId();
/*      */         }
/*      */         
/*  410 */         if (log.isLoggable(Level.FINEST)) {
/*  411 */           log.log(Level.FINEST, "Certificate was " + _x509Cert);
/*  412 */           log.log(Level.FINEST, "BinaryToken ID " + x509TokenId);
/*      */         } 
/*      */         
/*  415 */         HashMap<String, X509SecurityToken> tokenCache = context.getTokenCache();
/*  416 */         HashMap<String, X509SecurityToken> insertedX509Cache = context.getInsertedX509Cache();
/*      */         
/*  418 */         SecurityUtil.checkIncludeTokenPolicy(context, certificateBinding, x509TokenId);
/*      */         
/*  420 */         X509SecurityToken token = (X509SecurityToken)tokenCache.get(x509TokenId);
/*  421 */         if (token == null) {
/*  422 */           String valueType = certificateBinding.getValueType();
/*  423 */           if (valueType == null || valueType.equals(""))
/*      */           {
/*  425 */             valueType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
/*      */           }
/*  427 */           token = new X509SecurityToken(secureMsg.getSOAPPart(), _x509Cert, x509TokenId, valueType);
/*  428 */           tokenCache.put(x509TokenId, token);
/*  429 */           context.setCurrentSecret(_symmetricKey);
/*      */         } else {
/*  431 */           skbX509TokenInserted = true;
/*  432 */           _symmetricKey = context.getCurrentSecret();
/*      */         } 
/*      */         
/*  435 */         ekTokenRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/*  436 */         DirectReference reference = new DirectReference();
/*  437 */         insertedEkId = (String)ekCache.get(x509TokenId);
/*  438 */         if (insertedEkId == null)
/*  439 */           insertedEkId = ekId; 
/*  440 */         reference.setURI("#" + insertedEkId);
/*  441 */         reference.setValueType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
/*  442 */         ekTokenRef.setReference((ReferenceElement)reference);
/*      */         
/*  444 */         if (!skbX509TokenInserted) {
/*      */           
/*  446 */           referenceType = certificateBinding.getReferenceType();
/*  447 */           if (referenceType.equals("Identifier") && certificateBinding.getValueType().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v1")) {
/*  448 */             log.log(Level.SEVERE, "WSS1211.unsupported.KeyIdentifierStrategy.X509v1");
/*  449 */             throw new XWSSecurityException("Key Identifier strategy with X509v1 is not allowed");
/*      */           } 
/*  451 */           keyInfoStrategy = KeyInfoStrategy.getInstance(referenceType);
/*  452 */           _exportCertificate = true;
/*  453 */           keyInfoStrategy.setCertificate(_x509Cert);
/*      */           
/*  455 */           context.setExtraneousProperty("SecretKey", _symmetricKey);
/*      */         } 
/*  457 */         if ("Direct".equals(referenceType)) {
/*  458 */           if (insertedX509Cache.get(x509TokenId) == null) {
/*  459 */             secureMsg.findOrCreateSecurityHeader().insertHeaderBlock((SecurityHeaderBlock)token);
/*  460 */             insertedX509Cache.put(x509TokenId, token);
/*  461 */             x509TokenElement = secureMsg.findOrCreateSecurityHeader().getNextSiblingOfTimestamp();
/*      */           } else {
/*      */             
/*  464 */             x509TokenElement = secureMsg.getElementByWsuId(x509TokenId);
/*      */           }
/*      */         
/*      */         }
/*      */       }
/*      */     
/*  470 */     } else if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)derivedTokenKeyBinding)) {
/*      */       Assertion assertion;
/*      */       Assertion assertion3;
/*  473 */       AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)derivedTokenKeyBinding;
/*      */ 
/*      */       
/*  476 */       Assertion assertion1 = null;
/*  477 */       Assertion assertion2 = null;
/*      */       
/*      */       try {
/*  480 */         if (System.getProperty("com.sun.xml.wss.saml.binding.jaxb") == null) {
/*  481 */           if (samlBinding.getAssertion().getAttributeNode("ID") != null) {
/*  482 */             Assertion assertion4 = Assertion.fromElement(samlBinding.getAssertion());
/*      */           } else {
/*  484 */             assertion = Assertion.fromElement(samlBinding.getAssertion());
/*      */           } 
/*      */         } else {
/*  487 */           assertion3 = Assertion.fromElement(samlBinding.getAssertion());
/*      */         } 
/*  489 */       } catch (SAMLException ex) {
/*  490 */         log.log(Level.SEVERE, "WSS1212.error.SAMLAssertionException");
/*  491 */         throw new XWSSecurityException(ex);
/*      */       } 
/*      */       
/*  494 */       String assertionID = null;
/*  495 */       if (assertion != null) {
/*  496 */         HashMap<String, Assertion> tokenCache = context.getTokenCache();
/*      */         
/*  498 */         assertionID = assertion.getAssertionID();
/*  499 */         tokenCache.put(assertionID, assertion);
/*  500 */       } else if (assertion3 != null) {
/*  501 */         HashMap<String, Assertion> tokenCache = context.getTokenCache();
/*      */         
/*  503 */         assertionID = assertion3.getAssertionID();
/*  504 */         tokenCache.put(assertionID, assertion3);
/*      */       } else {
/*  506 */         log.log(Level.SEVERE, "WSS1213.null.SAMLAssertion");
/*  507 */         throw new XWSSecurityException("SAML Assertion is NULL");
/*      */       } 
/*      */ 
/*      */       
/*  511 */       samlkey = KeyResolver.resolveSamlAssertion(context.getSecurableSoapMessage(), samlBinding.getAssertion(), true, context, assertionID);
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
/*  522 */       if (!"".equals(samlBinding.getKeyAlgorithm())) {
/*  523 */         keyEncAlgo = samlBinding.getKeyAlgorithm();
/*      */       }
/*      */       
/*  526 */       _symmetricKey = SecurityUtil.generateSymmetricKey(dataEncAlgo);
/*      */       
/*  528 */       referenceType = samlBinding.getReferenceType();
/*  529 */       if (referenceType.equals("Embedded")) {
/*  530 */         log.log(Level.SEVERE, "WSS1215.unsupported.EmbeddedReference.SAMLAssertion");
/*  531 */         throw new XWSSecurityException("Embedded Reference Type for SAML Assertions not supported yet");
/*      */       } 
/*      */       
/*  534 */       String assertionId = null;
/*  535 */       if (assertion != null) {
/*  536 */         assertionId = assertion.getAssertionID();
/*  537 */       } else if (assertion3 != null) {
/*  538 */         assertionId = assertion3.getAssertionID();
/*      */       } 
/*  540 */       Element binding = samlBinding.getAuthorityBinding();
/*  541 */       samlTokenRef = new SecurityTokenReference(secureMsg.getSOAPPart());
/*  542 */       String strId = samlBinding.getSTRID();
/*  543 */       if (strId == null) {
/*  544 */         strId = secureMsg.generateId();
/*      */       }
/*  546 */       samlTokenRef.setWsuId(strId);
/*      */       
/*  548 */       if (binding != null) {
/*  549 */         samlTokenRef.setSamlAuthorityBinding(binding, secureMsg.getSOAPPart());
/*      */       }
/*  551 */       KeyIdentifierStrategy keyIdentifierStrategy = new KeyIdentifierStrategy(assertionId);
/*  552 */       keyIdentifierStrategy.insertKey(samlTokenRef, secureMsg);
/*      */     }
/*  554 */     else if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)derivedTokenKeyBinding)) {
/*      */       
/*  556 */       IssuedTokenContext trustContext = context.getTrustContext();
/*      */ 
/*      */       
/*      */       try {
/*  560 */         _symmetricKey = new SecretKeySpec(trustContext.getProofKey(), SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo));
/*  561 */       } catch (Exception e) {
/*  562 */         log.log(Level.SEVERE, "WSS1216.unableto.get.symmetrickey.Encryption");
/*  563 */         throw new XWSSecurityException(e);
/*      */       } 
/*      */ 
/*      */       
/*  567 */       GenericToken issuedToken = (GenericToken)trustContext.getSecurityToken();
/*      */ 
/*      */       
/*  570 */       IssuedTokenKeyBinding ikb = (IssuedTokenKeyBinding)derivedTokenKeyBinding;
/*      */       
/*  572 */       String ikbPolicyId = ikb.getUUID();
/*      */ 
/*      */       
/*  575 */       HashMap<String, SOAPElement> tokCache = context.getTokenCache();
/*  576 */       Object tok = tokCache.get(ikbPolicyId);
/*      */       
/*  578 */       SecurityTokenReference str = null;
/*  579 */       Element strElem = null;
/*  580 */       String tokenVersion = ikb.getIncludeToken();
/*  581 */       includeIST = (IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT.equals(tokenVersion) || IssuedTokenKeyBinding.INCLUDE_ALWAYS.equals(tokenVersion) || IssuedTokenKeyBinding.INCLUDE_ALWAYS_VER2.equals(tokenVersion) || IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2.equals(tokenVersion));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  587 */       if (includeIST && issuedToken == null) {
/*  588 */         log.log(Level.SEVERE, "WSS1217.null.IssueToken");
/*  589 */         throw new XWSSecurityException("Issued Token to be inserted into the Message was Null");
/*      */       } 
/*      */ 
/*      */       
/*  593 */       if (issuedToken != null) {
/*      */         
/*  595 */         Element elem = (Element)issuedToken.getTokenValue();
/*      */         
/*  597 */         if (tok == null) {
/*  598 */           issuedTokenElement = XMLUtil.convertToSoapElement(secureMessage.getSOAPPart(), elem);
/*      */           
/*  600 */           String tokId = issuedTokenElement.getAttribute("Id");
/*  601 */           if ("".equals(tokId) && "EncryptedData".equals(issuedTokenElement.getLocalName()))
/*      */           {
/*  603 */             issuedTokenElement.setAttribute("Id", secureMessage.generateId());
/*      */           }
/*  605 */           tokCache.put(ikbPolicyId, issuedTokenElement);
/*      */         } else {
/*  607 */           issuedTokenInserted = true;
/*      */           
/*  609 */           String wsuId = SecurityUtil.getWsuIdOrId((Element)tok);
/*  610 */           issuedTokenElementFromMsg = secureMessage.getElementById(wsuId);
/*  611 */           if (issuedTokenElementFromMsg == null) {
/*  612 */             log.log(Level.SEVERE, "WSS1218.unableto.locate.IssueToken.Message");
/*  613 */             throw new XWSSecurityException("Could not locate Issued Token in Message");
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  618 */       if (includeIST) {
/*  619 */         if (trustContext.getAttachedSecurityTokenReference() != null) {
/*  620 */           strElem = SecurityUtil.convertSTRToElement(trustContext.getAttachedSecurityTokenReference().getTokenValue(), secureMessage.getSOAPPart());
/*      */         } else {
/*  622 */           log.log(Level.SEVERE, "WSS1219.unableto.refer.Attached.IssueToken");
/*  623 */           throw new XWSSecurityException("Cannot determine how to reference the Attached Issued Token in the Message");
/*      */         }
/*      */       
/*      */       }
/*  627 */       else if (trustContext.getUnAttachedSecurityTokenReference() != null) {
/*  628 */         strElem = SecurityUtil.convertSTRToElement(trustContext.getUnAttachedSecurityTokenReference().getTokenValue(), secureMessage.getSOAPPart());
/*      */       } else {
/*  630 */         log.log(Level.SEVERE, "WSS1220.unableto.refer.Un-Attached.IssueToken");
/*  631 */         throw new XWSSecurityException("Cannot determine how to reference the Un-Attached Issued Token in the Message");
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  636 */       Element imported = (Element)secureMessage.getSOAPPart().importNode(strElem, true);
/*  637 */       issuedTokenRef = new SecurityTokenReference(XMLUtil.convertToSoapElement(secureMessage.getSOAPPart(), imported), false);
/*  638 */       SecurityUtil.updateSamlVsKeyCache((SecurityTokenReference)issuedTokenRef, context, _symmetricKey);
/*      */     }
/*  640 */     else if (PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)derivedTokenKeyBinding)) {
/*      */       
/*  642 */       SecureConversationTokenKeyBinding sctBinding = (SecureConversationTokenKeyBinding)derivedTokenKeyBinding;
/*      */ 
/*      */       
/*  645 */       String sctPolicyId = sctBinding.getUUID();
/*      */       
/*  647 */       HashMap<String, SecurityContextTokenImpl> tokCache = context.getTokenCache();
/*  648 */       sct = (SecurityContextTokenImpl)tokCache.get(sctPolicyId);
/*      */       
/*  650 */       IssuedTokenContext ictx = context.getSecureConversationContext();
/*      */       
/*  652 */       if (sct == null) {
/*  653 */         SecurityContextToken sct1 = (SecurityContextToken)ictx.getSecurityToken();
/*  654 */         if (sct1 == null) {
/*  655 */           log.log(Level.SEVERE, "WSS1221.null.SecureConversationToken");
/*  656 */           throw new XWSSecurityException("SecureConversation Token not Found");
/*      */         } 
/*      */         
/*  659 */         sct = new SecurityContextTokenImpl(secureMessage.getSOAPPart(), sct1.getIdentifier().toString(), sct1.getInstance(), sct1.getWsuId(), sct1.getExtElements());
/*      */ 
/*      */         
/*  662 */         tokCache.put(sctPolicyId, sct);
/*      */       } else {
/*  664 */         sctTokenInserted = true;
/*      */         
/*  666 */         sctElement = secureMessage.getElementByWsuId(sct.getWsuId());
/*      */       } 
/*      */       
/*  669 */       String sctWsuId = sct.getWsuId();
/*  670 */       if (sctWsuId == null) {
/*  671 */         sct.setId(secureMessage.generateId());
/*      */       }
/*  673 */       sctWsuId = sct.getWsuId();
/*      */       
/*  675 */       secConvRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/*  676 */       DirectReference reference = new DirectReference();
/*  677 */       if (SecureConversationTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT.equals(sctBinding.getIncludeToken()) || SecureConversationTokenKeyBinding.INCLUDE_ALWAYS.equals(sctBinding.getIncludeToken())) {
/*      */ 
/*      */         
/*  680 */         reference.setURI("#" + sctWsuId);
/*      */       } else {
/*  682 */         includeSCT = false;
/*  683 */         reference.setSCTURI(sct.getIdentifier().toString(), sct.getInstance());
/*      */       } 
/*      */       
/*  686 */       secConvRef.setReference((ReferenceElement)reference);
/*  687 */       referenceType = "Direct";
/*  688 */       keyInfoStrategy = KeyInfoStrategy.getInstance(referenceType);
/*      */       
/*  690 */       String jceAlgo = SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo);
/*  691 */       _symmetricKey = new SecretKeySpec(ictx.getProofKey(), jceAlgo);
/*      */     
/*      */     }
/*  694 */     else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)derivedTokenKeyBinding)) {
/*  695 */       DerivedTokenKeyBinding dtk = (DerivedTokenKeyBinding)derivedTokenKeyBinding.clone();
/*  696 */       WSSPolicy originalKeyBinding = dtk.getOriginalKeyBinding();
/*      */       
/*  698 */       String algorithm = null;
/*  699 */       if (algSuite != null) {
/*  700 */         algorithm = algSuite.getEncryptionAlgorithm();
/*      */       }
/*      */       
/*  703 */       long offset = 0L;
/*  704 */       long length = SecurityUtil.getLengthFromAlgorithm(algorithm);
/*      */       
/*  706 */       if (!PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)originalKeyBinding))
/*      */       {
/*  708 */         if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)originalKeyBinding)) {
/*  709 */           SymmetricKeyBinding skb = null;
/*  710 */           if (context.getSymmetricKeyBinding() != null) {
/*  711 */             skb = context.getSymmetricKeyBinding();
/*  712 */             context.setSymmetricKeyBinding(null);
/*      */           } else {
/*  714 */             skb = (SymmetricKeyBinding)originalKeyBinding;
/*      */           } 
/*      */           
/*  717 */           if (sendEKSHA1) {
/*  718 */             String ekSha1Ref = getEKSHA1Ref(context);
/*      */             
/*  720 */             originalKey = skb.getSecretKey();
/*  721 */             byte[] secret = originalKey.getEncoded();
/*  722 */             DerivedKeyTokenImpl derivedKeyTokenImpl = new DerivedKeyTokenImpl(offset, length, secret);
/*  723 */             String dktId = secureMessage.generateId();
/*  724 */             String nonce = Base64.encode(derivedKeyTokenImpl.getNonce());
/*      */             
/*      */             try {
/*  727 */               String jceAlgo = SecurityUtil.getSecretKeyAlgorithm(algorithm);
/*  728 */               _symmetricKey = derivedKeyTokenImpl.generateSymmetricKey(jceAlgo);
/*  729 */             } catch (Exception e) {
/*  730 */               log.log(Level.SEVERE, "WSS1216.unableto.get.symmetrickey.Encryption");
/*  731 */               throw new XWSSecurityException(e);
/*      */             } 
/*      */             
/*  734 */             SecurityTokenReference tokenRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/*  735 */             EncryptedKeySHA1Identifier refElem = new EncryptedKeySHA1Identifier(secureMessage.getSOAPPart());
/*  736 */             refElem.setReferenceValue(ekSha1Ref);
/*  737 */             tokenRef.setReference((ReferenceElement)refElem);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  743 */             dktHeadrBlock = new DerivedKeyTokenHeaderBlock(_secHeader.getOwnerDocument(), tokenRef, nonce, derivedKeyTokenImpl.getOffset(), derivedKeyTokenImpl.getLength(), dktId);
/*      */ 
/*      */ 
/*      */             
/*  747 */             DirectReference reference = new DirectReference();
/*  748 */             reference.setURI("#" + dktId);
/*  749 */             ekTokenRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/*  750 */             ekTokenRef.setReference((ReferenceElement)reference);
/*  751 */           } else if (wss11Sender || wss10) {
/*  752 */             dktSender = true;
/*  753 */             originalKey = skb.getSecretKey();
/*  754 */             if (context.getX509CertificateBinding() != null) {
/*  755 */               certificateBinding = context.getX509CertificateBinding();
/*  756 */               context.setX509CertificateBinding(null);
/*  757 */               _x509Cert = certificateBinding.getX509Certificate();
/*      */             } 
/*  759 */             _x509Cert = certificateBinding.getX509Certificate();
/*  760 */             referenceType = certificateBinding.getReferenceType();
/*  761 */             keyInfoStrategy = KeyInfoStrategy.getInstance(referenceType);
/*  762 */             _exportCertificate = true;
/*  763 */             keyInfoStrategy.setCertificate(_x509Cert);
/*  764 */             x509TokenId = certificateBinding.getUUID();
/*  765 */             if (x509TokenId == null || x509TokenId.equals("")) {
/*  766 */               x509TokenId = secureMsg.generateId();
/*      */             }
/*      */             
/*  769 */             if (log.isLoggable(Level.FINEST)) {
/*  770 */               log.log(Level.FINEST, "Certificate was " + _x509Cert);
/*  771 */               log.log(Level.FINEST, "BinaryToken ID " + x509TokenId);
/*      */             } 
/*      */ 
/*      */             
/*  775 */             HashMap<String, X509SecurityToken> tokenCache = context.getTokenCache();
/*  776 */             HashMap<String, X509SecurityToken> insertedX509Cache = context.getInsertedX509Cache();
/*      */             
/*  778 */             SecurityUtil.checkIncludeTokenPolicy(context, certificateBinding, x509TokenId);
/*      */ 
/*      */ 
/*      */             
/*  782 */             X509SecurityToken insertedx509 = (X509SecurityToken)context.getInsertedX509Cache().get(x509TokenId);
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  787 */             X509SecurityToken token = (X509SecurityToken)tokenCache.get(x509TokenId);
/*  788 */             if (token == null) {
/*  789 */               if (insertedx509 != null) {
/*  790 */                 token = insertedx509;
/*  791 */                 tokenCache.put(x509TokenId, insertedx509);
/*      */               } else {
/*  793 */                 String valueType = certificateBinding.getValueType();
/*  794 */                 if (valueType == null || valueType.equals(""))
/*      */                 {
/*  796 */                   valueType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3";
/*      */                 }
/*  798 */                 token = new X509SecurityToken(secureMsg.getSOAPPart(), _x509Cert, x509TokenId, valueType);
/*  799 */                 tokenCache.put(x509TokenId, token);
/*      */               } 
/*  801 */               context.setCurrentSecret(originalKey);
/*      */               
/*  803 */               context.setExtraneousProperty("SecretKey", originalKey);
/*      */             } else {
/*  805 */               skbX509TokenInserted = true;
/*  806 */               originalKey = context.getCurrentSecret();
/*      */             } 
/*      */             
/*  809 */             if (insertedx509 == null) {
/*  810 */               if ("Direct".equals(referenceType)) {
/*  811 */                 secureMsg.findOrCreateSecurityHeader().insertHeaderBlock((SecurityHeaderBlock)token);
/*  812 */                 insertedX509Cache.put(x509TokenId, token);
/*  813 */                 x509TokenElement = secureMsg.findOrCreateSecurityHeader().getNextSiblingOfTimestamp();
/*      */               } 
/*      */             } else {
/*      */               
/*  817 */               x509SecurityToken = insertedx509;
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/*  822 */             byte[] secret = originalKey.getEncoded();
/*  823 */             DerivedKeyTokenImpl derivedKeyTokenImpl = new DerivedKeyTokenImpl(offset, length, secret);
/*  824 */             String dktId = secureMessage.generateId();
/*  825 */             String nonce = Base64.encode(derivedKeyTokenImpl.getNonce());
/*      */             
/*      */             try {
/*  828 */               String jceAlgo = SecurityUtil.getSecretKeyAlgorithm(algorithm);
/*  829 */               _symmetricKey = derivedKeyTokenImpl.generateSymmetricKey(jceAlgo);
/*  830 */             } catch (Exception e) {
/*  831 */               log.log(Level.SEVERE, "WSS1216.unableto.get.symmetrickey.Encryption");
/*  832 */               throw new XWSSecurityException(e);
/*      */             } 
/*      */ 
/*      */             
/*  836 */             SecurityTokenReference tokenRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/*  837 */             DirectReference reference = new DirectReference();
/*      */ 
/*      */ 
/*      */             
/*  841 */             insertedEkId = (String)ekCache.get(x509TokenId);
/*  842 */             if (insertedEkId == null)
/*  843 */               insertedEkId = ekId; 
/*  844 */             reference.setURI("#" + insertedEkId);
/*  845 */             reference.setValueType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
/*  846 */             tokenRef.setReference((ReferenceElement)reference);
/*  847 */             dktHeadrBlock = new DerivedKeyTokenHeaderBlock(_secHeader.getOwnerDocument(), tokenRef, nonce, derivedKeyTokenImpl.getOffset(), derivedKeyTokenImpl.getLength(), dktId);
/*      */ 
/*      */ 
/*      */             
/*  851 */             DirectReference refEnc = new DirectReference();
/*  852 */             refEnc.setURI("#" + dktId);
/*  853 */             ekTokenRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/*  854 */             ekTokenRef.setReference((ReferenceElement)refEnc);
/*      */           } 
/*  856 */         } else if (PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)originalKeyBinding)) {
/*      */           
/*  858 */           sctWithDKT = true;
/*      */           
/*  860 */           SecureConversationTokenKeyBinding sctBinding = (SecureConversationTokenKeyBinding)originalKeyBinding;
/*      */           
/*  862 */           String sctPolicyId = sctBinding.getUUID();
/*      */           
/*  864 */           HashMap<String, SecurityContextTokenImpl> tokCache = context.getTokenCache();
/*  865 */           sct = (SecurityContextTokenImpl)tokCache.get(sctPolicyId);
/*      */           
/*  867 */           IssuedTokenContext ictx = context.getSecureConversationContext();
/*      */           
/*  869 */           if (sct == null) {
/*  870 */             SecurityContextToken sct1 = (SecurityContextToken)ictx.getSecurityToken();
/*  871 */             if (sct1 == null) {
/*  872 */               log.log(Level.SEVERE, "WSS1221.null.SecureConversationToken");
/*  873 */               throw new XWSSecurityException("SecureConversation Token not Found");
/*      */             } 
/*      */             
/*  876 */             sct = new SecurityContextTokenImpl(secureMessage.getSOAPPart(), sct1.getIdentifier().toString(), sct1.getInstance(), sct1.getWsuId(), sct1.getExtElements());
/*      */ 
/*      */             
/*  879 */             tokCache.put(sctPolicyId, sct);
/*      */           } else {
/*  881 */             sctTokenInserted = true;
/*      */             
/*  883 */             sctElement = secureMessage.getElementByWsuId(sct.getWsuId());
/*      */           } 
/*      */           
/*  886 */           String sctWsuId = sct.getWsuId();
/*  887 */           if (sctWsuId == null) {
/*  888 */             sct.setId(secureMessage.generateId());
/*      */           }
/*  890 */           sctWsuId = sct.getWsuId();
/*      */           
/*  892 */           byte[] secret = context.getSecureConversationContext().getProofKey();
/*  893 */           DerivedKeyTokenImpl derivedKeyTokenImpl = new DerivedKeyTokenImpl(offset, length, secret);
/*  894 */           String dktId = secureMessage.generateId();
/*  895 */           String nonce = Base64.encode(derivedKeyTokenImpl.getNonce());
/*      */           
/*      */           try {
/*  898 */             _symmetricKey = derivedKeyTokenImpl.generateSymmetricKey(SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo));
/*  899 */           } catch (Exception e) {
/*  900 */             log.log(Level.SEVERE, "WSS1216.unableto.get.symmetrickey.Encryption");
/*  901 */             throw new XWSSecurityException(e);
/*      */           } 
/*      */           
/*  904 */           SecurityTokenReference secRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/*  905 */           DirectReference reference = new DirectReference();
/*  906 */           if (SecureConversationTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT.equals(sctBinding.getIncludeToken()) || SecureConversationTokenKeyBinding.INCLUDE_ALWAYS.equals(sctBinding.getIncludeToken())) {
/*      */ 
/*      */             
/*  909 */             reference.setURI("#" + sctWsuId);
/*      */           } else {
/*  911 */             includeSCT = false;
/*  912 */             reference.setSCTURI(sct.getIdentifier().toString(), sct.getInstance());
/*      */           } 
/*  914 */           secRef.setReference((ReferenceElement)reference);
/*  915 */           dktHeadrBlock = new DerivedKeyTokenHeaderBlock(_secHeader.getOwnerDocument(), secRef, nonce, derivedKeyTokenImpl.getOffset(), derivedKeyTokenImpl.getLength(), dktId);
/*      */ 
/*      */ 
/*      */           
/*  919 */           DirectReference refEnc = new DirectReference();
/*  920 */           refEnc.setURI("#" + dktId);
/*  921 */           dktSctTokenRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/*  922 */           dktSctTokenRef.setReference((ReferenceElement)refEnc);
/*      */         }
/*  924 */         else if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)originalKeyBinding)) {
/*      */           
/*  926 */           issuedWithDKT = true;
/*      */           
/*  928 */           IssuedTokenContext trustContext = context.getTrustContext();
/*  929 */           DerivedKeyTokenImpl derivedKeyTokenImpl = new DerivedKeyTokenImpl(offset, length, trustContext.getProofKey());
/*  930 */           String dktId = secureMessage.generateId();
/*  931 */           String nonce = Base64.encode(derivedKeyTokenImpl.getNonce());
/*      */ 
/*      */           
/*  934 */           Key origKey = null;
/*      */           try {
/*  936 */             origKey = new SecretKeySpec(trustContext.getProofKey(), SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo));
/*  937 */           } catch (Exception e) {
/*  938 */             log.log(Level.SEVERE, "WSS1216.unableto.get.symmetrickey.Encryption");
/*  939 */             throw new XWSSecurityException(e);
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*      */           try {
/*  945 */             _symmetricKey = derivedKeyTokenImpl.generateSymmetricKey(SecurityUtil.getSecretKeyAlgorithm(dataEncAlgo));
/*  946 */           } catch (Exception e) {
/*  947 */             log.log(Level.SEVERE, "WSS1216.unableto.get.symmetrickey.Encryption");
/*  948 */             throw new XWSSecurityException(e);
/*      */           } 
/*      */ 
/*      */           
/*  952 */           GenericToken issuedToken = (GenericToken)trustContext.getSecurityToken();
/*      */ 
/*      */           
/*  955 */           IssuedTokenKeyBinding ikb = (IssuedTokenKeyBinding)originalKeyBinding;
/*      */           
/*  957 */           String ikbPolicyId = ikb.getUUID();
/*      */           
/*  959 */           HashMap<String, SOAPElement> tokCache = context.getTokenCache();
/*  960 */           Object tok = tokCache.get(ikbPolicyId);
/*      */           
/*  962 */           SecurityTokenReference str = null;
/*  963 */           Element strElem = null;
/*  964 */           String tokenVersion = ikb.getIncludeToken();
/*  965 */           includeIST = (IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT.equals(tokenVersion) || IssuedTokenKeyBinding.INCLUDE_ALWAYS.equals(tokenVersion) || IssuedTokenKeyBinding.INCLUDE_ALWAYS_VER2.equals(tokenVersion) || IssuedTokenKeyBinding.INCLUDE_ALWAYS_TO_RECIPIENT_VER2.equals(tokenVersion));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  971 */           if (includeIST && issuedToken == null) {
/*  972 */             log.log(Level.SEVERE, "WSS1217.null.IssueToken");
/*  973 */             throw new XWSSecurityException("Issued Token to be inserted into the Message was Null");
/*      */           } 
/*      */           
/*  976 */           if (issuedToken != null) {
/*      */             
/*  978 */             Element elem = (Element)issuedToken.getTokenValue();
/*      */             
/*  980 */             if (tok == null) {
/*  981 */               issuedTokenElement = XMLUtil.convertToSoapElement(secureMessage.getSOAPPart(), elem);
/*      */               
/*  983 */               String tokId = issuedTokenElement.getAttribute("Id");
/*  984 */               if ("".equals(tokId) && "EncryptedData".equals(issuedTokenElement.getLocalName()))
/*      */               {
/*  986 */                 issuedTokenElement.setAttribute("Id", secureMessage.generateId());
/*      */               }
/*  988 */               tokCache.put(ikbPolicyId, issuedTokenElement);
/*      */             } else {
/*  990 */               issuedTokenInserted = true;
/*      */               
/*  992 */               String wsuId = SecurityUtil.getWsuIdOrId((Element)tok);
/*  993 */               issuedTokenElementFromMsg = secureMessage.getElementById(wsuId);
/*  994 */               if (issuedTokenElementFromMsg == null) {
/*  995 */                 log.log(Level.SEVERE, "WSS1218.unableto.locate.IssueToken.Message");
/*  996 */                 throw new XWSSecurityException("Could not locate Issued Token in Message");
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/* 1001 */           if (includeIST) {
/* 1002 */             if (trustContext.getAttachedSecurityTokenReference() != null) {
/* 1003 */               strElem = (Element)trustContext.getAttachedSecurityTokenReference().getTokenValue();
/*      */             } else {
/* 1005 */               log.log(Level.SEVERE, "WSS1219.unableto.refer.Attached.IssueToken");
/* 1006 */               throw new XWSSecurityException("Cannot determine how to reference the Attached Issued Token in the Message");
/*      */             }
/*      */           
/*      */           }
/* 1010 */           else if (trustContext.getUnAttachedSecurityTokenReference() != null) {
/* 1011 */             strElem = (Element)trustContext.getUnAttachedSecurityTokenReference().getTokenValue();
/*      */           } else {
/* 1013 */             log.log(Level.SEVERE, "WSS1220.unableto.refer.Un-Attached.IssueToken");
/* 1014 */             throw new XWSSecurityException("Cannot determine how to reference the Un-Attached Issued Token in the Message");
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 1019 */           Element imported = (Element)secureMessage.getSOAPPart().importNode(strElem, true);
/* 1020 */           str = new SecurityTokenReference(XMLUtil.convertToSoapElement(secureMessage.getSOAPPart(), (Element)imported.cloneNode(true)), false);
/*      */ 
/*      */           
/* 1023 */           if (origKey != null) {
/* 1024 */             SecurityUtil.updateSamlVsKeyCache((SecurityTokenReference)str, context, origKey);
/*      */           }
/*      */           
/* 1027 */           dktHeadrBlock = new DerivedKeyTokenHeaderBlock(_secHeader.getOwnerDocument(), str, nonce, derivedKeyTokenImpl.getOffset(), derivedKeyTokenImpl.getLength(), dktId);
/*      */ 
/*      */           
/* 1030 */           DirectReference refEnc = new DirectReference();
/* 1031 */           refEnc.setURI("#" + dktId);
/* 1032 */           dktIssuedTokenRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/* 1033 */           dktIssuedTokenRef.setReference((ReferenceElement)refEnc);
/*      */         } 
/*      */       }
/*      */     } else {
/* 1037 */       log.log(Level.SEVERE, "WSS1222.unsupported.KeyBinding.EncryptionPolicy");
/* 1038 */       throw new XWSSecurityException("Unsupported Key Binding for EncryptionPolicy");
/*      */     } 
/*      */ 
/*      */     
/* 1042 */     XMLCipher _keyEncryptor = null;
/* 1043 */     XMLCipher _dataEncryptor = null;
/* 1044 */     Cipher _attachmentEncryptor = null;
/* 1045 */     Cipher _dataCipher = null;
/*      */ 
/*      */     
/*      */     try {
/* 1049 */       if (log.isLoggable(Level.FINEST)) {
/* 1050 */         log.log(Level.FINEST, "KeyEncryption algorithm is " + keyEncAlgo);
/*      */       }
/*      */       
/* 1053 */       if (_x509Cert != null) {
/*      */         
/* 1055 */         _keyEncryptor = XMLCipher.getInstance(keyEncAlgo);
/* 1056 */         _keyEncryptor.init(3, _x509Cert.getPublicKey());
/* 1057 */       } else if (samlkey != null) {
/*      */         
/* 1059 */         _keyEncryptor = XMLCipher.getInstance(keyEncAlgo);
/* 1060 */         _keyEncryptor.init(3, samlkey);
/* 1061 */       } else if (keyEncSK != null) {
/*      */         
/* 1063 */         _keyEncryptor = XMLCipher.getInstance(keyEncAlgo);
/* 1064 */         _keyEncryptor.init(3, keyEncSK);
/*      */       } 
/*      */       
/* 1067 */       if (log.isLoggable(Level.FINEST)) {
/* 1068 */         log.log(Level.FINEST, "Data encryption algorithm is " + dataEncAlgo);
/*      */       }
/*      */       
/* 1071 */       String dataAlgorithm = JCEMapper.translateURItoJCEID(dataEncAlgo);
/* 1072 */       _dataCipher = Cipher.getInstance(dataAlgorithm);
/* 1073 */       _dataEncryptor = XMLCipher.getInstance(dataEncAlgo, _dataCipher);
/* 1074 */       _dataCipher.init(1, _symmetricKey);
/* 1075 */       _dataEncryptor.init(1, _symmetricKey);
/*      */     }
/* 1077 */     catch (Exception xee) {
/* 1078 */       log.log(Level.SEVERE, "WSS1205.unableto.initialize.xml.cipher", xee);
/* 1079 */       throw new XWSSecurityException("Unable to initialize XML Cipher", xee);
/*      */     } 
/*      */ 
/*      */     
/* 1083 */     ArrayList targets = featureBinding.getTargetBindings();
/*      */     
/* 1085 */     ArrayList<Object[]> _aparts = new ArrayList();
/* 1086 */     ArrayList<Object[]> _dnodes = new ArrayList();
/*      */     
/* 1088 */     Iterator<EncryptionTarget> i = targets.iterator();
/*      */ 
/*      */     
/* 1091 */     while (i.hasNext()) {
/* 1092 */       EncryptionTarget target = i.next();
/* 1093 */       boolean contentOnly = target.getContentOnly();
/* 1094 */       Boolean cOnly = Boolean.valueOf(contentOnly);
/* 1095 */       if ("cid:*".equals(target.getValue())) {
/* 1096 */         Iterator<AttachmentPart> itr = secureMsg.getAttachments();
/* 1097 */         while (itr.hasNext()) {
/* 1098 */           AttachmentPart ap = itr.next();
/* 1099 */           Object[] s = new Object[2];
/* 1100 */           s[0] = ap;
/* 1101 */           s[1] = cOnly;
/* 1102 */           _aparts.add(s);
/*      */         } 
/*      */         continue;
/*      */       } 
/* 1106 */       Object mgpart = secureMsg.getMessageParts((Target)target);
/*      */ 
/*      */       
/* 1109 */       ArrayList transforms = target.getCipherReferenceTransforms();
/* 1110 */       if (mgpart == null)
/*      */         continue; 
/* 1112 */       if (mgpart instanceof AttachmentPart) {
/* 1113 */         Object[] s = new Object[2];
/* 1114 */         s[0] = mgpart;
/* 1115 */         s[1] = cOnly;
/* 1116 */         _aparts.add(s); continue;
/*      */       } 
/* 1118 */       if (mgpart instanceof Node) {
/* 1119 */         Object[] s = new Object[2];
/* 1120 */         s[0] = mgpart;
/* 1121 */         s[1] = cOnly;
/* 1122 */         _dnodes.add(s); continue;
/* 1123 */       }  if (mgpart instanceof NodeList) {
/* 1124 */         for (int j = 0; j < ((NodeList)mgpart).getLength(); j++) {
/* 1125 */           Object[] s = new Object[2];
/* 1126 */           Node n = ((NodeList)mgpart).item(j);
/* 1127 */           s[0] = n;
/* 1128 */           s[1] = cOnly;
/* 1129 */           _dnodes.add(s);
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1135 */     if (_dnodes.isEmpty() && _aparts.isEmpty() && 
/* 1136 */       log.isLoggable(Level.WARNING)) {
/* 1137 */       log.log(Level.WARNING, "None of the specified Encryption Parts found in the Message");
/*      */     }
/*      */ 
/*      */     
/* 1141 */     EncryptedKey _encryptedKey = null;
/* 1142 */     ReferenceListHeaderBlock _ekReferenceList = null;
/* 1143 */     ReferenceListHeaderBlock _standaloneReferenceList = null;
/*      */     
/* 1145 */     if (_keyEncryptor != null && !skbX509TokenInserted) {
/*      */       try {
/* 1147 */         if (!dktSender) {
/* 1148 */           _encryptedKey = _keyEncryptor.encryptKey(secureMsg.getSOAPPart(), _symmetricKey);
/*      */         } else {
/* 1150 */           _encryptedKey = _keyEncryptor.encryptKey(secureMsg.getSOAPPart(), originalKey);
/*      */         } 
/* 1152 */         _encryptedKey.setId(ekId);
/* 1153 */         ekCache.put(x509TokenId, ekId);
/* 1154 */         KeyInfoHeaderBlock keyInfoBlock = new KeyInfoHeaderBlock(secureMsg.getSOAPPart());
/*      */         
/* 1156 */         if (samlTokenRef != null) {
/* 1157 */           keyInfoBlock.addSecurityTokenReference(samlTokenRef);
/* 1158 */         } else if (_x509Cert != null) {
/* 1159 */           keyInfoStrategy.insertKey(keyInfoBlock, secureMsg, x509TokenId);
/* 1160 */         } else if (keyEncSK != null) {
/*      */           
/* 1162 */           keyInfoBlock.addKeyName(symmetricKeyName);
/*      */         } 
/* 1164 */         KeyInfo keyInfo = keyInfoBlock.getKeyInfo();
/* 1165 */         _encryptedKey.setKeyInfo(keyInfo);
/*      */       }
/* 1167 */       catch (Exception xe) {
/* 1168 */         log.log(Level.SEVERE, "WSS1223.unableto.set.KeyInfo.EncryptedKey", xe);
/*      */         
/* 1170 */         throw new XWSSecurityException(xe);
/*      */       } 
/*      */     }
/*      */     
/* 1174 */     if (_encryptedKey != null && !dktSender && !useStandaloneRefList) {
/* 1175 */       _ekReferenceList = new ReferenceListHeaderBlock(secureMsg.getSOAPPart());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1181 */     SOAPElement x509Sibling = null;
/*      */     
/* 1183 */     if (x509SecurityToken != null) {
/* 1184 */       x509Sibling = (SOAPElement)x509SecurityToken.getNextSibling();
/*      */     }
/* 1186 */     Iterator<Object[]> _apartsI = _aparts.iterator();
/* 1187 */     if (_apartsI.hasNext()) {
/*      */       
/*      */       try {
/*      */ 
/*      */         
/* 1192 */         String dataAlgorithm = JCEMapper.translateURItoJCEID(dataEncAlgo);
/* 1193 */         _attachmentEncryptor = Cipher.getInstance(dataAlgorithm);
/* 1194 */         _attachmentEncryptor.init(1, _symmetricKey);
/* 1195 */       } catch (Exception xee) {
/* 1196 */         log.log(Level.SEVERE, "WSS1205.unableto.initialize.xml.cipher", xee);
/* 1197 */         throw new XWSSecurityException("Unable to initialize XML Cipher", xee);
/*      */       } 
/*      */     }
/*      */     
/* 1201 */     while (_apartsI.hasNext()) {
/* 1202 */       Object[] s = _apartsI.next();
/* 1203 */       AttachmentPart p = (AttachmentPart)s[0];
/* 1204 */       boolean b = ((Boolean)s[1]).booleanValue();
/*      */ 
/*      */ 
/*      */       
/* 1208 */       EncryptedDataHeaderBlock edhb = new EncryptedDataHeaderBlock();
/*      */       
/* 1210 */       String id = secureMsg.generateId();
/*      */       
/* 1212 */       edhb.setId(id);
/* 1213 */       edhb.setType(b ? "http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Content-Only" : "http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Complete");
/* 1214 */       edhb.setMimeType(p.getContentType());
/*      */       
/* 1216 */       String uri = p.getContentId();
/* 1217 */       if (uri != null) {
/* 1218 */         if (uri.charAt(0) == '<' && uri.charAt(uri.length() - 1) == '>') {
/* 1219 */           uri = "cid:" + uri.substring(1, uri.length() - 1);
/*      */         } else {
/* 1221 */           uri = "cid:" + uri;
/*      */         } 
/*      */       } else {
/* 1224 */         uri = p.getContentLocation();
/*      */       } 
/*      */       
/* 1227 */       edhb.getCipherReference(true, uri);
/* 1228 */       edhb.setEncryptionMethod(dataEncAlgo);
/* 1229 */       edhb.addTransform("http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Content-Only-Transform");
/*      */       
/* 1231 */       encryptAttachment(p, b, _attachmentEncryptor);
/*      */       
/* 1233 */       if (_ekReferenceList != null) {
/* 1234 */         _ekReferenceList.addReference("#" + id);
/*      */       }
/* 1236 */       if (x509Sibling == null && x509SecurityToken == null) {
/* 1237 */         _secHeader.insertHeaderBlock((SecurityHeaderBlock)edhb); continue;
/*      */       } 
/* 1239 */       if (x509Sibling != null) {
/* 1240 */         _secHeader.insertBefore((SecurityHeaderBlock)edhb, x509Sibling); continue;
/*      */       } 
/* 1242 */       _secHeader.appendChild((SecurityHeaderBlock)edhb);
/*      */     } 
/*      */ 
/*      */     
/* 1246 */     int optType = -1;
/* 1247 */     Iterator<Object[]> _dnodeI = _dnodes.iterator();
/* 1248 */     while (_dnodeI.hasNext()) {
/* 1249 */       Object[] s = _dnodeI.next();
/* 1250 */       Node n = (Node)s[0];
/* 1251 */       boolean b = ((Boolean)s[1]).booleanValue();
/*      */       
/* 1253 */       Element ed = null;
/* 1254 */       boolean _fi = false;
/* 1255 */       if (context.getConfigType() == 2) {
/* 1256 */         if (_fi) {
/* 1257 */           ed = encryptBodyContent(secureMsg, context.getCanonicalizedData(), _dataEncryptor);
/*      */         } else {
/* 1259 */           signEncrypt(context, _dataCipher, _ekReferenceList, _standaloneReferenceList, keyInfoStrategy, dataEncAlgo);
/*      */           
/*      */           continue;
/*      */         } 
/* 1263 */       } else if (n.getNodeType() == 3) {
/* 1264 */         ed = encryptElement(secureMsg, (SOAPElement)n.getParentNode(), true, _dataEncryptor);
/*      */       } else {
/* 1266 */         ed = encryptElement(secureMsg, (SOAPElement)n, b, _dataEncryptor);
/*      */       } 
/*      */       
/* 1269 */       EncryptedHeaderBlock ehb = null;
/* 1270 */       boolean isEhb = false;
/* 1271 */       EncryptedDataHeaderBlock xencEncryptedData = new EncryptedDataHeaderBlock(XMLUtil.convertToSoapElement(secureMsg.getSOAPPart(), ed));
/*      */ 
/*      */       
/* 1274 */       String xencEncryptedDataId = secureMsg.generateId();
/* 1275 */       String xencEncryptedDataRef = "#" + xencEncryptedDataId;
/* 1276 */       if (ed.getParentNode() instanceof javax.xml.soap.SOAPHeader && wss11Sender) {
/* 1277 */         isEhb = true;
/* 1278 */         ehb = new EncryptedHeaderBlock(secureMsg.getSOAPPart());
/* 1279 */         ehb.setId(xencEncryptedDataId);
/* 1280 */         ehb.copyAttributes(secureMsg, _secHeader);
/*      */       } else {
/* 1282 */         xencEncryptedData.setId(xencEncryptedDataId);
/*      */       } 
/*      */       
/* 1285 */       if (_ekReferenceList != null) {
/* 1286 */         _ekReferenceList.addReference(xencEncryptedDataRef);
/*      */       } else {
/* 1288 */         if (_standaloneReferenceList == null) {
/* 1289 */           _standaloneReferenceList = new ReferenceListHeaderBlock(secureMsg.getSOAPPart());
/*      */         }
/* 1291 */         _standaloneReferenceList.addReference(xencEncryptedDataRef);
/*      */         
/* 1293 */         KeyInfoHeaderBlock keyInfoBlock = new KeyInfoHeaderBlock(secureMsg.getSOAPPart());
/* 1294 */         SecurityTokenReference cloned = null;
/* 1295 */         if (dktSctTokenRef != null) {
/* 1296 */           cloned = new SecurityTokenReference((SOAPElement)dktSctTokenRef.cloneNode(true));
/* 1297 */           keyInfoBlock.addSecurityTokenReference(cloned);
/* 1298 */         } else if (secConvRef != null) {
/* 1299 */           cloned = new SecurityTokenReference((SOAPElement)secConvRef.cloneNode(true));
/* 1300 */           keyInfoBlock.addSecurityTokenReference(cloned);
/* 1301 */         } else if (ekTokenRef != null) {
/* 1302 */           cloned = new SecurityTokenReference((SOAPElement)ekTokenRef.cloneNode(true));
/* 1303 */           keyInfoBlock.addSecurityTokenReference(cloned);
/* 1304 */         } else if (dktIssuedTokenRef != null) {
/* 1305 */           cloned = new SecurityTokenReference((SOAPElement)dktIssuedTokenRef.cloneNode(true));
/* 1306 */           keyInfoBlock.addSecurityTokenReference(cloned);
/* 1307 */         } else if (issuedTokenRef != null) {
/* 1308 */           cloned = new SecurityTokenReference((SOAPElement)issuedTokenRef.cloneNode(true));
/* 1309 */           keyInfoBlock.addSecurityTokenReference(cloned);
/*      */         
/*      */         }
/* 1312 */         else if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)derivedTokenKeyBinding)) {
/*      */           
/* 1314 */           DirectReference dRef = new DirectReference();
/* 1315 */           dRef.setURI("#" + ekId);
/* 1316 */           ekDirectRef = new SecurityTokenReference(secureMessage.getSOAPPart());
/* 1317 */           ekDirectRef.setReference((ReferenceElement)dRef);
/* 1318 */           keyInfoBlock.addSecurityTokenReference(ekDirectRef);
/*      */         }
/*      */         else {
/*      */           
/* 1322 */           keyInfoStrategy.insertKey(keyInfoBlock, secureMsg, null);
/*      */         } 
/*      */ 
/*      */         
/* 1326 */         xencEncryptedData.setKeyInfo(keyInfoBlock);
/*      */       } 
/*      */       
/* 1329 */       if (isEhb) {
/*      */         
/* 1331 */         try { ed.getParentNode().replaceChild(ehb.getAsSoapElement(), ed);
/* 1332 */           ehb.addChildElement(xencEncryptedData.getAsSoapElement()); }
/* 1333 */         catch (Exception se) { se.printStackTrace(); }
/*      */          continue;
/* 1335 */       }  ed.getParentNode().replaceChild(xencEncryptedData.getAsSoapElement(), ed);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/* 1340 */       x509Sibling = null;
/*      */       
/* 1342 */       if (x509SecurityToken != null) {
/* 1343 */         x509Sibling = (SOAPElement)x509SecurityToken.getNextSibling();
/*      */       }
/*      */       
/* 1346 */       if (_encryptedKey != null) {
/* 1347 */         SOAPElement se = (SOAPElement)_keyEncryptor.martial(_encryptedKey);
/* 1348 */         se = _secHeader.makeUsable(se);
/* 1349 */         if (_ekReferenceList != null) {
/* 1350 */           se.appendChild(_ekReferenceList.getAsSoapElement());
/*      */         }
/*      */         
/* 1353 */         Element cipherData = se.getChildElements(new QName("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc")).next();
/* 1354 */         String cipherValue = cipherData.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue").item(0).getTextContent();
/* 1355 */         byte[] decodedCipher = Base64.decode(cipherValue);
/* 1356 */         byte[] ekSha1 = MessageDigest.getInstance("SHA-1").digest(decodedCipher);
/* 1357 */         String encEkSha1 = Base64.encode(ekSha1);
/* 1358 */         context.setExtraneousProperty("EncryptedKeySHA1", encEkSha1);
/*      */         
/* 1360 */         if (x509Sibling == null) {
/* 1361 */           if (x509SecurityToken == null) {
/* 1362 */             _secHeader.insertHeaderBlockElement(se);
/*      */           } else {
/* 1364 */             _secHeader.appendChild(se);
/*      */           } 
/*      */         } else {
/* 1367 */           _secHeader.insertBefore(se, x509Sibling);
/*      */         } 
/*      */         
/* 1370 */         if (_standaloneReferenceList != null) {
/* 1371 */           _secHeader.insertBefore((SecurityHeaderBlock)_standaloneReferenceList, se.getNextSibling());
/* 1372 */           context.setCurrentReferenceList(se.getNextSibling());
/*      */         }
/*      */       
/* 1375 */       } else if (_standaloneReferenceList != null) {
/*      */         
/* 1377 */         if (sctElement == null && issuedTokenElementFromMsg == null) {
/* 1378 */           if (insertedEkId != null) {
/*      */             
/* 1380 */             Element ekElem = secureMessage.getElementById(insertedEkId);
/* 1381 */             _secHeader.insertBefore((SecurityHeaderBlock)_standaloneReferenceList, ekElem.getNextSibling());
/*      */           } else {
/*      */             
/* 1384 */             _secHeader.insertHeaderBlock((SecurityHeaderBlock)_standaloneReferenceList);
/* 1385 */             context.setCurrentReferenceList(_standaloneReferenceList.getAsSoapElement());
/*      */           }
/*      */         
/*      */         }
/* 1389 */         else if (sctElement != null) {
/* 1390 */           _secHeader.insertBefore((SecurityHeaderBlock)_standaloneReferenceList, sctElement.getNextSibling());
/* 1391 */         } else if (issuedTokenElementFromMsg != null) {
/* 1392 */           _secHeader.insertBefore((SecurityHeaderBlock)_standaloneReferenceList, issuedTokenElementFromMsg.getNextSibling());
/*      */         } else {
/* 1394 */           _secHeader.insertHeaderBlock((SecurityHeaderBlock)_standaloneReferenceList);
/* 1395 */           context.setCurrentReferenceList(_standaloneReferenceList.getAsSoapElement());
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1401 */       if (sctWithDKT || issuedWithDKT) {
/*      */         
/* 1403 */         if (sctElement == null && sct != null) {
/* 1404 */           _secHeader.insertHeaderBlock((SecurityHeaderBlock)dktHeadrBlock);
/* 1405 */           if (includeSCT) {
/* 1406 */             _secHeader.insertHeaderBlock((SecurityHeaderBlock)sct);
/*      */           }
/* 1408 */         } else if (issuedTokenElementFromMsg == null && issuedTokenElement != null) {
/* 1409 */           _secHeader.insertHeaderBlock((SecurityHeaderBlock)dktHeadrBlock);
/* 1410 */           if (includeIST) {
/* 1411 */             _secHeader.insertHeaderBlockElement(issuedTokenElement);
/*      */           }
/*      */ 
/*      */           
/* 1415 */           context.setIssuedSAMLToken(issuedTokenElement);
/*      */         
/*      */         }
/* 1418 */         else if (sctElement != null) {
/* 1419 */           _secHeader.insertBefore((SecurityHeaderBlock)dktHeadrBlock, sctElement.getNextSibling());
/* 1420 */         } else if (issuedTokenElementFromMsg != null) {
/* 1421 */           _secHeader.insertBefore((SecurityHeaderBlock)dktHeadrBlock, issuedTokenElementFromMsg.getNextSibling());
/*      */         } else {
/* 1423 */           _secHeader.insertHeaderBlock((SecurityHeaderBlock)dktHeadrBlock);
/*      */         }
/*      */       
/*      */       }
/*      */       else {
/*      */         
/* 1429 */         if (dktHeadrBlock != null) {
/* 1430 */           if (insertedEkId != null) {
/* 1431 */             Element ekElem = secureMessage.getElementById(insertedEkId);
/* 1432 */             _secHeader.insertBefore((SecurityHeaderBlock)dktHeadrBlock, ekElem.getNextSibling());
/*      */           } else {
/* 1434 */             _secHeader.insertHeaderBlock((SecurityHeaderBlock)dktHeadrBlock);
/*      */           } 
/*      */         }
/*      */         
/* 1438 */         if (!sctTokenInserted && sct != null && includeSCT) {
/* 1439 */           _secHeader.insertHeaderBlock((SecurityHeaderBlock)sct);
/*      */         }
/*      */ 
/*      */         
/* 1443 */         if (!issuedTokenInserted && issuedTokenElement != null && includeIST) {
/* 1444 */           _secHeader.insertHeaderBlockElement(issuedTokenElement);
/*      */ 
/*      */           
/* 1447 */           context.setIssuedSAMLToken(issuedTokenElement);
/*      */         }
/*      */       
/*      */       } 
/* 1451 */     } catch (Base64DecodingException e) {
/* 1452 */       log.log(Level.SEVERE, "WSS1224.error.insertion.HeaderBlock.SecurityHeader", e);
/* 1453 */       throw new XWSSecurityException(e);
/* 1454 */     } catch (NoSuchAlgorithmException e) {
/* 1455 */       log.log(Level.SEVERE, "WSS1224.error.insertion.HeaderBlock.SecurityHeader", e);
/* 1456 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Element encryptElement(SecurableSoapMessage secureMsg, SOAPElement encryptElm, boolean contentOnly, XMLCipher xmlCipher) throws XWSSecurityException {
/*      */     Node contextNode;
/*      */     Element xencEncryptedData;
/* 1466 */     String localName = encryptElm.getLocalName();
/*      */ 
/*      */     
/* 1469 */     if (!contentOnly && ("http://schemas.xmlsoap.org/soap/envelope/".equalsIgnoreCase(encryptElm.getNamespaceURI()) || "http://www.w3.org/2003/05/soap-envelope".equalsIgnoreCase(encryptElm.getNamespaceURI())) && ("Header".equalsIgnoreCase(localName) || "Envelope".equalsIgnoreCase(localName) || "Body".equalsIgnoreCase(localName))) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1475 */       log.log(Level.SEVERE, "WSS1206.illegal.target", encryptElm.getElementName().getQualifiedName());
/*      */ 
/*      */       
/* 1478 */       throw new XWSSecurityException("Encryption of SOAP " + localName + " is not allowed");
/*      */     } 
/*      */ 
/*      */     
/* 1482 */     SOAPPart soapPart = secureMsg.getSOAPPart();
/*      */ 
/*      */     
/* 1485 */     Node refNode = null;
/*      */     
/* 1487 */     if (contentOnly) {
/* 1488 */       contextNode = encryptElm;
/*      */     } else {
/* 1490 */       contextNode = encryptElm.getParentNode();
/* 1491 */       refNode = encryptElm.getNextSibling();
/*      */     } 
/*      */     
/*      */     try {
/* 1495 */       xmlCipher.doFinal(soapPart, encryptElm, contentOnly);
/* 1496 */     } catch (Exception e) {
/* 1497 */       log.log(Level.SEVERE, "WSS1207.unableto.encrypt.message");
/* 1498 */       throw new XWSSecurityException("Unable to encrypt element", e);
/*      */     } 
/*      */ 
/*      */     
/* 1502 */     if (contentOnly) {
/* 1503 */       xencEncryptedData = (Element)contextNode.getFirstChild();
/*      */     }
/* 1505 */     else if (refNode == null) {
/* 1506 */       xencEncryptedData = (Element)contextNode.getLastChild();
/*      */     } else {
/* 1508 */       xencEncryptedData = (Element)refNode.getPreviousSibling();
/*      */     } 
/*      */ 
/*      */     
/* 1512 */     return xencEncryptedData;
/*      */   }
/*      */ 
/*      */   
/*      */   private static Element encryptBodyContent(SecurableSoapMessage contextNode, byte[] canonData, XMLCipher xmlCipher) throws XWSSecurityException {
/* 1517 */     throw new UnsupportedOperationException("Old optimizations disabled in WSIT");
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
/*      */   
/*      */   private static void signEncrypt(FilterProcessingContext fpc, Cipher cipher, ReferenceListHeaderBlock _ekReferenceList, ReferenceListHeaderBlock _standaloneReferenceList, KeyInfoStrategy keyInfoStrategy, String encAlgo) throws XWSSecurityException {
/* 1533 */     throw new UnsupportedOperationException("Not supported in WSIT");
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
/*      */   private static void encryptAttachment(AttachmentPart part, boolean contentOnly, Cipher cipher) throws XWSSecurityException {
/*      */     try {
/* 1573 */       byte[] cipherInput = null;
/*      */       
/* 1575 */       if (contentOnly) {
/* 1576 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 1577 */         part.getDataHandler().writeTo(baos);
/* 1578 */         cipherInput = baos.toByteArray();
/*      */       } else {
/* 1580 */         Object[] obj = AttachmentSignatureInput._getSignatureInput(part);
/*      */         
/* 1582 */         byte[] headers = serializeHeaders((Vector)obj[0]);
/* 1583 */         byte[] content = (byte[])obj[1];
/*      */         
/* 1585 */         cipherInput = new byte[headers.length + content.length];
/*      */         
/* 1587 */         System.arraycopy(headers, 0, cipherInput, 0, headers.length);
/* 1588 */         System.arraycopy(content, 0, cipherInput, headers.length, content.length);
/*      */       } 
/*      */       
/* 1591 */       byte[] cipherOutput = cipher.doFinal(cipherInput);
/*      */       
/* 1593 */       byte[] iv = cipher.getIV();
/* 1594 */       byte[] encryptedBytes = new byte[iv.length + cipherOutput.length];
/*      */       
/* 1596 */       System.arraycopy(iv, 0, encryptedBytes, 0, iv.length);
/* 1597 */       System.arraycopy(cipherOutput, 0, encryptedBytes, iv.length, cipherOutput.length);
/*      */       
/* 1599 */       int cLength = encryptedBytes.length;
/* 1600 */       String cType = "application/octet-stream";
/* 1601 */       String uri = part.getContentId();
/*      */       
/* 1603 */       if (!contentOnly) {
/* 1604 */         part.removeAllMimeHeaders();
/*      */       }
/*      */       
/* 1607 */       if (uri != null) {
/* 1608 */         part.setMimeHeader("Content-ID", uri);
/*      */       } else {
/* 1610 */         uri = part.getContentLocation();
/* 1611 */         if (uri != null) {
/* 1612 */           part.setMimeHeader("Content-Location", uri);
/*      */         }
/*      */       } 
/* 1615 */       part.setContentType(cType);
/* 1616 */       part.setMimeHeader("Content-Length", Integer.toString(cLength));
/* 1617 */       part.setMimeHeader("Content-Transfer-Encoding", "base64");
/*      */       
/* 1619 */       EncryptedAttachmentDataHandler dh = new EncryptedAttachmentDataHandler(new EncryptedAttachmentDataSource(encryptedBytes));
/* 1620 */       part.setDataHandler(dh);
/*      */     }
/* 1622 */     catch (Exception e) {
/* 1623 */       log.log(Level.SEVERE, "WSS1225.error.encrypting.Attachment", e);
/* 1624 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private static String getEKSHA1Ref(FilterProcessingContext context) {
/* 1629 */     String ekSha1Ref = null;
/* 1630 */     ekSha1Ref = (String)context.getExtraneousProperty("EKSHA1Value");
/* 1631 */     return ekSha1Ref;
/*      */   }
/*      */   
/*      */   private static byte[] serializeHeaders(Vector<MimeHeader> mimeHeaders) throws XWSSecurityException {
/* 1635 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*      */     
/*      */     try {
/* 1638 */       for (int i = 0; i < mimeHeaders.size(); i++) {
/* 1639 */         MimeHeader mh = mimeHeaders.elementAt(i);
/*      */         
/* 1641 */         String name = mh.getName();
/* 1642 */         String vlue = mh.getValue();
/*      */         
/* 1644 */         String line = name + ":" + vlue + "\r\n";
/*      */         
/* 1646 */         byte[] b = line.getBytes("US-ASCII");
/* 1647 */         baos.write(b, 0, b.length);
/*      */       } 
/*      */       
/* 1650 */       baos.write(crlf, 0, crlf.length);
/* 1651 */     } catch (Exception e) {
/* 1652 */       log.log(Level.SEVERE, "WSS1226.error.serialize.headers", e);
/* 1653 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/* 1656 */     return baos.toByteArray();
/*      */   }
/*      */   
/*      */   private static class EncryptedAttachmentDataSource implements DataSource {
/*      */     byte[] datasource;
/*      */     
/*      */     EncryptedAttachmentDataSource(byte[] ds) {
/* 1663 */       this.datasource = ds;
/*      */     }
/*      */     
/*      */     public String getContentType() {
/* 1667 */       return "application/octet-stream";
/*      */     }
/*      */     
/*      */     public InputStream getInputStream() throws IOException {
/* 1671 */       return new ByteArrayInputStream(this.datasource);
/*      */     }
/*      */     
/*      */     public String getName() {
/* 1675 */       return "Encrypted Attachment DataSource";
/*      */     }
/*      */     
/*      */     public OutputStream getOutputStream() throws IOException {
/* 1679 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 1680 */       baos.write(this.datasource, 0, this.datasource.length);
/* 1681 */       return baos;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class EncryptedAttachmentDataHandler
/*      */     extends DataHandler {
/*      */     EncryptedAttachmentDataHandler(DataSource ds) {
/* 1688 */       super(ds);
/*      */     }
/*      */ 
/*      */     
/*      */     public void writeTo(OutputStream os) throws IOException {
/* 1693 */       ((ByteArrayOutputStream)getDataSource().getOutputStream()).writeTo(os);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\apachecrypto\EncryptionProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */