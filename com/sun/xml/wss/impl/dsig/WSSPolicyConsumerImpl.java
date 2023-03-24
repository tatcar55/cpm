/*      */ package com.sun.xml.wss.impl.dsig;
/*      */ 
/*      */ import com.sun.xml.wss.WSITXMLFactory;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.core.SecurityTokenReference;
/*      */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*      */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*      */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*      */ import com.sun.xml.wss.impl.XMLUtil;
/*      */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*      */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*      */ import com.sun.xml.wss.impl.policy.PolicyGenerationException;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*      */ import com.sun.xml.wss.impl.policy.mls.Parameter;
/*      */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*      */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*      */ import com.sun.xml.wss.logging.impl.dsig.LogStringsMessages;
/*      */ import com.sun.xml.wss.util.NodeListImpl;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.security.AccessController;
/*      */ import java.security.InvalidAlgorithmParameterException;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.Provider;
/*      */ import java.security.Security;
/*      */ import java.security.spec.AlgorithmParameterSpec;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Map;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.crypto.URIDereferencer;
/*      */ import javax.xml.crypto.XMLStructure;
/*      */ import javax.xml.crypto.dom.DOMStructure;
/*      */ import javax.xml.crypto.dsig.CanonicalizationMethod;
/*      */ import javax.xml.crypto.dsig.DigestMethod;
/*      */ import javax.xml.crypto.dsig.Reference;
/*      */ import javax.xml.crypto.dsig.SignatureMethod;
/*      */ import javax.xml.crypto.dsig.SignedInfo;
/*      */ import javax.xml.crypto.dsig.Transform;
/*      */ import javax.xml.crypto.dsig.XMLSignature;
/*      */ import javax.xml.crypto.dsig.XMLSignatureFactory;
/*      */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*      */ import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
/*      */ import javax.xml.crypto.dsig.keyinfo.KeyName;
/*      */ import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
/*      */ import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
/*      */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*      */ import javax.xml.crypto.dsig.spec.XPathFilter2ParameterSpec;
/*      */ import javax.xml.crypto.dsig.spec.XPathFilterParameterSpec;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.soap.AttachmentPart;
/*      */ import javax.xml.soap.SOAPElement;
/*      */ import javax.xml.soap.SOAPException;
/*      */ import javax.xml.soap.SOAPHeader;
/*      */ import javax.xml.soap.SOAPMessage;
/*      */ import javax.xml.transform.Transformer;
/*      */ import javax.xml.transform.dom.DOMSource;
/*      */ import javax.xml.transform.stream.StreamResult;
/*      */ import javax.xml.xpath.XPath;
/*      */ import javax.xml.xpath.XPathConstants;
/*      */ import javax.xml.xpath.XPathExpression;
/*      */ import javax.xml.xpath.XPathExpressionException;
/*      */ import javax.xml.xpath.XPathFactory;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NamedNodeMap;
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
/*      */ public class WSSPolicyConsumerImpl
/*      */ {
/*  138 */   private static Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.dsig.LogStrings", "com.sun.xml.wss.logging.impl.dsig.LogStrings");
/*      */ 
/*      */   
/*      */   public static final String defaultJSR105Provider = "org.jcp.xml.dsig.internal.dom.XMLDSigRI";
/*      */   
/*  143 */   private String providerName = null;
/*  144 */   private String pMT = null;
/*  145 */   private static volatile WSSPolicyConsumerImpl wpcInstance = null;
/*  146 */   private URIDereferencer externalURIResolver = null;
/*  147 */   private Provider provider = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private WSSPolicyConsumerImpl() {
/*  158 */     this.providerName = System.getProperty("jsr105Provider", "org.jcp.xml.dsig.internal.dom.XMLDSigRI");
/*      */     
/*  160 */     this.pMT = System.getProperty("jsr105MechanismType", "DOM");
/*  161 */     final Provider prov = Security.getProvider("XMLDSig");
/*      */ 
/*      */     
/*      */     try {
/*  165 */       ClassLoader loader = getClass().getClassLoader();
/*  166 */       Class<?> providerClass = Class.forName(this.providerName, true, loader);
/*  167 */       this.provider = (Provider)providerClass.newInstance();
/*  168 */     } catch (Exception ex1) {
/*      */       try {
/*  170 */         ClassLoader tccl = Thread.currentThread().getContextClassLoader();
/*  171 */         Class<?> providerClass = Class.forName(this.providerName, true, tccl);
/*  172 */         this.provider = (Provider)providerClass.newInstance();
/*  173 */       } catch (Exception ex) {
/*  174 */         logger.log(Level.WARNING, LogStringsMessages.WSS_1324_DSIG_FACTORY(), ex);
/*      */       } 
/*      */     } 
/*      */     
/*  178 */     if (logger.isLoggable(Level.FINEST)) {
/*  179 */       logger.log(Level.FINEST, "JSR 105 provider is : " + this.providerName);
/*  180 */       logger.log(Level.FINEST, "JSR 105 provider mechanism is : " + this.pMT);
/*      */     } 
/*      */     
/*  183 */     AccessController.doPrivileged(new PrivilegedAction() {
/*      */           public Object run() {
/*      */             try {
/*  186 */               if (prov == null && WSSPolicyConsumerImpl.this.provider != null) {
/*  187 */                 Security.insertProviderAt(WSSPolicyConsumerImpl.this.provider, 5);
/*      */               }
/*  189 */               Security.insertProviderAt(new WSSPolicyConsumerImpl.WSSProvider(), 6);
/*  190 */             } catch (SecurityException ex) {
/*  191 */               if (prov == null && WSSPolicyConsumerImpl.this.provider != null) {
/*  192 */                 Security.addProvider(WSSPolicyConsumerImpl.this.provider);
/*      */               }
/*  194 */               Security.addProvider(new WSSPolicyConsumerImpl.WSSProvider());
/*      */             } 
/*  196 */             return null;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static WSSPolicyConsumerImpl getInstance() {
/*  205 */     if (wpcInstance == null) {
/*  206 */       synchronized (WSSPolicyConsumerImpl.class) {
/*  207 */         if (wpcInstance == null) {
/*  208 */           wpcInstance = new WSSPolicyConsumerImpl();
/*      */         }
/*      */       } 
/*      */     }
/*  212 */     return wpcInstance;
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
/*      */   public SignedInfo constructSignedInfo(FilterProcessingContext fpContext) throws PolicyGenerationException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, XWSSecurityException {
/*  226 */     if (PolicyTypeUtil.signaturePolicy(fpContext.getSecurityPolicy())) {
/*  227 */       SignedInfo signInfo = generateSignedInfo(fpContext);
/*  228 */       return signInfo;
/*      */     } 
/*  230 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLSignature constructSignature(SignedInfo signInfo, KeyInfo keyInfo) {
/*  240 */     return getSignatureFactory().newXMLSignature(signInfo, keyInfo);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLSignature constructSignature(SignedInfo signInfo, KeyInfo keyInfo, String id) {
/*  251 */     return getSignatureFactory().newXMLSignature(signInfo, keyInfo, null, id, null);
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
/*      */   public KeyInfo constructKeyInfo(MLSPolicy signaturePolicy, SecurityTokenReference reference) throws PolicyGenerationException, SOAPException, XWSSecurityException {
/*  265 */     if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)signaturePolicy)) {
/*      */ 
/*      */       
/*  268 */       KeyInfoFactory keyFactory = getKeyInfoFactory();
/*      */       
/*  270 */       DOMStructure domKeyInfo = new DOMStructure(reference.getAsSoapElement());
/*      */       
/*  272 */       KeyInfo keyInfo = keyFactory.newKeyInfo(Collections.singletonList(domKeyInfo));
/*  273 */       return keyInfo;
/*      */     } 
/*      */ 
/*      */     
/*  277 */     return null;
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
/*      */   public KeyInfo constructKeyInfo(MLSPolicy signaturePolicy, String KeyName) throws PolicyGenerationException, SOAPException, XWSSecurityException {
/*  293 */     if (PolicyTypeUtil.signaturePolicy((SecurityPolicy)signaturePolicy)) {
/*      */ 
/*      */       
/*  296 */       KeyInfoFactory keyFactory = getKeyInfoFactory();
/*  297 */       KeyName keyName = keyFactory.newKeyName(KeyName);
/*  298 */       List<KeyName> list = new ArrayList();
/*  299 */       list.add(keyName);
/*      */       
/*  301 */       KeyInfo keyInfo = keyFactory.newKeyInfo(list);
/*      */       
/*  303 */       return keyInfo;
/*      */     } 
/*      */ 
/*      */     
/*  307 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLSignatureFactory getSignatureFactory() {
/*      */     try {
/*  318 */       return XMLSignatureFactory.getInstance("DOM", this.provider);
/*      */     }
/*  320 */     catch (Exception ex) {
/*  321 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1324_DSIG_FACTORY(), ex);
/*  322 */       throw new RuntimeException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyInfoFactory getKeyInfoFactory() {
/*      */     try {
/*  332 */       return getSignatureFactory().getKeyInfoFactory();
/*  333 */     } catch (Exception ex) {
/*  334 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1323_DSIG_KEYINFO_FACTORY(), ex);
/*  335 */       throw new RuntimeException(ex);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignaturePolicy constructSignaturePolicy(SignedInfo signedInfo, boolean isBSP) {
/*  344 */     SignaturePolicy policy = new SignaturePolicy();
/*  345 */     constructSignaturePolicy(signedInfo, isBSP, policy);
/*  346 */     return policy;
/*      */   }
/*      */   
/*      */   public void constructSignaturePolicy(SignedInfo signedInfo, boolean isBSP, SignaturePolicy policy) {
/*  350 */     List referencesList = signedInfo.getReferences();
/*      */     
/*  352 */     CanonicalizationMethod cm = signedInfo.getCanonicalizationMethod();
/*      */     
/*  354 */     policy.isBSP(isBSP);
/*  355 */     SignaturePolicy.FeatureBinding featureBinding = (SignaturePolicy.FeatureBinding)policy.getFeatureBinding();
/*  356 */     featureBinding.setCanonicalizationAlgorithm(cm.getAlgorithm());
/*  357 */     Iterator<Reference> itr = referencesList.iterator();
/*  358 */     while (itr.hasNext()) {
/*  359 */       Reference ref = itr.next();
/*  360 */       SignatureTarget.Transform transform = getSignatureTransform(ref);
/*  361 */       SignatureTarget target = new SignatureTarget();
/*  362 */       target.isBSP(isBSP);
/*  363 */       if (transform != null) {
/*  364 */         target.addTransform(transform);
/*      */       }
/*  366 */       target.setDigestAlgorithm(ref.getDigestMethod().getAlgorithm());
/*  367 */       if (ref.getURI().length() > 0) {
/*  368 */         target.setValue(SecurableSoapMessage.getIdFromFragmentRef(ref.getURI()));
/*      */       } else {
/*  370 */         target.setValue(ref.getURI());
/*      */       } 
/*  372 */       target.setType("uri");
/*  373 */       featureBinding.addTargetBinding(target);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void constructSignaturePolicy(SignedInfo signedInfo, SignaturePolicy policy, SecurableSoapMessage secMsg) throws XWSSecurityException {
/*  379 */     List referencesList = signedInfo.getReferences();
/*      */     
/*  381 */     CanonicalizationMethod cm = signedInfo.getCanonicalizationMethod();
/*      */ 
/*      */     
/*  384 */     SignaturePolicy.FeatureBinding featureBinding = (SignaturePolicy.FeatureBinding)policy.getFeatureBinding();
/*  385 */     featureBinding.setCanonicalizationAlgorithm(cm.getAlgorithm());
/*  386 */     Iterator<Reference> itr = referencesList.iterator();
/*  387 */     while (itr.hasNext()) {
/*  388 */       Reference ref = itr.next();
/*  389 */       SignatureTarget.Transform transform = getSignatureTransform(ref);
/*  390 */       SignatureTarget target = new SignatureTarget();
/*      */       
/*  392 */       if (transform != null) {
/*  393 */         target.addTransform(transform);
/*      */       }
/*  395 */       target.setDigestAlgorithm(ref.getDigestMethod().getAlgorithm());
/*  396 */       if (ref.getURI().length() > 0) {
/*  397 */         String Id = SecurableSoapMessage.getIdFromFragmentRef(ref.getURI());
/*      */         
/*  399 */         SOAPElement se = (SOAPElement)secMsg.getElementById(Id);
/*  400 */         if (se != null) {
/*  401 */           if (se.getNamespaceURI().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") || se.getNamespaceURI().equals("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd") || se.getNamespaceURI().equals("http://schemas.xmlsoap.org/ws/2005/02/sc") || se.getNamespaceURI().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd")) {
/*      */ 
/*      */ 
/*      */             
/*  405 */             target.setValue("#" + Id);
/*  406 */             target.setType("uri");
/*      */           } else {
/*      */             
/*  409 */             QName qname = new QName(se.getNamespaceURI(), se.getLocalName());
/*  410 */             target.setQName(qname);
/*  411 */             target.setType("qname");
/*      */           } 
/*      */         } else {
/*  414 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1376_FAILED_VERIFY_POLICY_NO_ELEMENTBY_ID());
/*  415 */           throw new XWSSecurityException("Policy verification for Signature failed: Element with Id: " + Id + "not found in message");
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  420 */       featureBinding.addTargetBinding(target);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignatureTarget.Transform getSignatureTransform(Reference reference) {
/*  430 */     List transformList = reference.getTransforms();
/*  431 */     Iterator<Transform> transformItr = transformList.iterator();
/*  432 */     SignatureTarget.Transform transform = null;
/*  433 */     while (transformItr.hasNext()) {
/*  434 */       Transform trObj = transformItr.next();
/*  435 */       String algorithm = trObj.getAlgorithm();
/*  436 */       transform = new SignatureTarget.Transform();
/*  437 */       transform.setTransform(algorithm);
/*  438 */       AlgorithmParameterSpec paramSpec = trObj.getParameterSpec();
/*      */ 
/*      */       
/*  441 */       transform.setAlgorithmParameters(paramSpec);
/*      */     } 
/*  443 */     return transform;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addCanonicalizationParams(AlgorithmParameterSpec algoSpec, HashMap<String, String> paramList) {
/*  454 */     if (algoSpec instanceof XPathFilterParameterSpec) {
/*  455 */       XPathFilterParameterSpec spec = (XPathFilterParameterSpec)algoSpec;
/*  456 */       paramList.put("XPATH", spec.getXPath());
/*  457 */     } else if (algoSpec instanceof XPathFilter2ParameterSpec) {
/*  458 */       XPathFilter2ParameterSpec spec = (XPathFilter2ParameterSpec)algoSpec;
/*  459 */       paramList.put("XPATH2", spec.getXPathList());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private SignedInfo generateSignedInfo(FilterProcessingContext fpContext) throws PolicyGenerationException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, XWSSecurityException {
/*  465 */     SignaturePolicy signaturePolicy = (SignaturePolicy)fpContext.getSecurityPolicy();
/*  466 */     SignaturePolicy.FeatureBinding featureBinding = (SignaturePolicy.FeatureBinding)signaturePolicy.getFeatureBinding();
/*  467 */     MLSPolicy keyBinding = signaturePolicy.getKeyBinding();
/*  468 */     XMLSignatureFactory signatureFactory = getSignatureFactory();
/*  469 */     SecurableSoapMessage secureMessage = fpContext.getSecurableSoapMessage();
/*  470 */     String canonicalAlgo = featureBinding.getCanonicalizationAlgorithm();
/*  471 */     boolean disableInclusivePrefix = featureBinding.getDisableInclusivePrefix();
/*      */     
/*  473 */     ArrayList targetList = featureBinding.getTargetBindings();
/*  474 */     String keyAlgo = null;
/*  475 */     String algo = "SHA1withRSA";
/*  476 */     if (fpContext.getAlgorithmSuite() != null) {
/*  477 */       algo = fpContext.getAlgorithmSuite().getSignatureAlgorithm();
/*      */     }
/*      */     
/*  480 */     keyAlgo = SecurityUtil.getKeyAlgo(algo);
/*      */     
/*  482 */     if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)keyBinding)) {
/*  483 */       AuthenticationTokenPolicy.X509CertificateBinding certificateBinding = (AuthenticationTokenPolicy.X509CertificateBinding)keyBinding;
/*      */       
/*  485 */       if (!"".equals(certificateBinding.getKeyAlgorithm())) {
/*  486 */         keyAlgo = certificateBinding.getKeyAlgorithm();
/*      */       }
/*  488 */     } else if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)keyBinding)) {
/*  489 */       AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)keyBinding;
/*      */       
/*  491 */       if (!"".equals(samlBinding.getKeyAlgorithm())) {
/*  492 */         keyAlgo = samlBinding.getKeyAlgorithm();
/*      */       }
/*  494 */     } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)keyBinding)) {
/*  495 */       SymmetricKeyBinding symmetricKeybinding = (SymmetricKeyBinding)keyBinding;
/*  496 */       if (!"".equals(symmetricKeybinding.getKeyAlgorithm())) {
/*  497 */         keyAlgo = symmetricKeybinding.getKeyAlgorithm();
/*      */       } else {
/*  499 */         keyAlgo = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
/*      */       } 
/*  501 */     } else if (PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)keyBinding)) {
/*  502 */       keyAlgo = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
/*  503 */     } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)keyBinding)) {
/*  504 */       keyAlgo = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
/*  505 */       if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)((DerivedTokenKeyBinding)keyBinding).getOriginalKeyBinding()) && 
/*  506 */         fpContext.getTrustContext().getProofKey() == null) {
/*  507 */         keyAlgo = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/*      */       }
/*      */     }
/*  510 */     else if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)keyBinding)) {
/*      */       
/*  512 */       keyAlgo = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
/*  513 */       if (fpContext.getTrustContext().getProofKey() == null) {
/*  514 */         keyAlgo = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/*      */       }
/*      */     } else {
/*  517 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1335_UNSUPPORTED_KEYBINDING_SIGNATUREPOLICY());
/*  518 */       throw new XWSSecurityException("Unsupported KeyBinding for SignaturePolicy");
/*      */     } 
/*      */     
/*  521 */     C14NMethodParameterSpec spec = null;
/*  522 */     if ("http://www.w3.org/2001/10/xml-exc-c14n#".equalsIgnoreCase(canonicalAlgo))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  528 */       if (featureBinding.isBSP() || !disableInclusivePrefix) {
/*  529 */         List inc = getInclusiveNamespacePrefixes((Element)secureMessage.findSecurityHeader(), false);
/*  530 */         spec = new ExcC14NParameterSpec(inc);
/*      */       } else {
/*  532 */         spec = null;
/*      */       } 
/*      */     }
/*      */     
/*  536 */     CanonicalizationMethod canonicalMethod = signatureFactory.newCanonicalizationMethod(canonicalAlgo, spec);
/*      */ 
/*      */     
/*  539 */     SignatureMethod signatureMethod = signatureFactory.newSignatureMethod(keyAlgo, null);
/*      */     
/*  541 */     SignedInfo signedInfo = signatureFactory.newSignedInfo(canonicalMethod, signatureMethod, generateReferenceList(targetList, signatureFactory, secureMessage, fpContext, false, featureBinding.isEndorsingSignature()), null);
/*      */ 
/*      */     
/*  544 */     return signedInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List getInclusiveNamespacePrefixes(Element target, boolean excludeVisiblePrefixes) {
/*  554 */     ArrayList<String> result = new ArrayList();
/*      */ 
/*      */     
/*  557 */     Node parent = target;
/*      */     
/*  559 */     while (!(parent instanceof Document)) {
/*  560 */       NamedNodeMap attributes = parent.getAttributes();
/*  561 */       for (int i = 0; i < attributes.getLength(); i++) {
/*  562 */         Node attribute = attributes.item(i);
/*  563 */         if (attribute.getNamespaceURI() != null && attribute.getNamespaceURI().equals("http://www.w3.org/2000/xmlns/"))
/*      */         {
/*  565 */           result.add(attribute.getLocalName());
/*      */         }
/*      */       } 
/*  568 */       parent = parent.getParentNode();
/*      */     } 
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
/*  591 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List getReferenceNamespacePrefixes(Node target) {
/*  600 */     ArrayList result = new ArrayList();
/*      */     
/*  602 */     traverseSubtree(target, result);
/*      */     
/*  604 */     return result;
/*      */   }
/*      */   
/*      */   private static void traverseSubtree(Node node, List<String> result) {
/*  608 */     SOAPElement element = (SOAPElement)node;
/*  609 */     Iterator<String> visible = element.getVisibleNamespacePrefixes();
/*      */     
/*  611 */     while (visible.hasNext()) {
/*  612 */       String prefix = visible.next();
/*  613 */       if (!result.contains(prefix)) {
/*  614 */         result.add(prefix);
/*      */       }
/*      */     } 
/*  617 */     Iterator<Node> children = element.getChildElements();
/*      */     
/*  619 */     while (children.hasNext()) {
/*  620 */       Node child = children.next();
/*  621 */       if (!(child instanceof javax.xml.soap.Text)) {
/*  622 */         traverseSubtree(child, result);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List generateReferenceList(List targetList, SecurableSoapMessage secureMessage, FilterProcessingContext fpContext, boolean verify, boolean isEndorsing) throws PolicyGenerationException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, XWSSecurityException {
/*  630 */     XMLSignatureFactory factory = getSignatureFactory();
/*  631 */     return generateReferenceList(targetList, factory, secureMessage, fpContext, verify, isEndorsing);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List generateReferenceList(List targetList, XMLSignatureFactory signatureFactory, SecurableSoapMessage secureMessage, FilterProcessingContext fpContext, boolean verify, boolean isEndorsing) throws PolicyGenerationException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, XWSSecurityException {
/*  642 */     SignaturePolicy signaturePolicy = (SignaturePolicy)fpContext.getSecurityPolicy();
/*  643 */     SignaturePolicy.FeatureBinding featureBinding = (SignaturePolicy.FeatureBinding)signaturePolicy.getFeatureBinding();
/*  644 */     ListIterator<SignatureTarget> iterator = targetList.listIterator();
/*  645 */     ArrayList<Reference> references = new ArrayList();
/*  646 */     if (logger.isLoggable(Level.FINEST)) {
/*  647 */       logger.log(Level.FINEST, "Number of Targets is" + targetList.size());
/*      */     }
/*      */     
/*  650 */     while (iterator.hasNext()) {
/*      */       
/*  652 */       SignatureTarget signatureTarget = iterator.next();
/*  653 */       String digestAlgo = signatureTarget.getDigestAlgorithm();
/*  654 */       if (logger.isLoggable(Level.FINEST)) {
/*  655 */         logger.log(Level.FINEST, "Digest Algorithm is " + digestAlgo);
/*  656 */         logger.log(Level.FINEST, "Targets is" + signatureTarget.getValue());
/*      */       } 
/*  658 */       DigestMethod digestMethod = null;
/*      */       try {
/*  660 */         digestMethod = signatureFactory.newDigestMethod(digestAlgo, null);
/*  661 */       } catch (Exception ex) {
/*  662 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1301_INVALID_DIGEST_ALGO(digestAlgo), ex);
/*  663 */         throw new XWSSecurityException(ex.getMessage());
/*      */       } 
/*      */       
/*  666 */       boolean exclTransformToBeAdded = false;
/*  667 */       ArrayList transforms = signatureTarget.getTransforms();
/*  668 */       ListIterator<SignatureTarget.Transform> transformIterator = transforms.listIterator();
/*  669 */       ArrayList<Transform> transformList = new ArrayList(2);
/*  670 */       boolean disableInclusivePrefix = false;
/*  671 */       while (transformIterator.hasNext()) {
/*  672 */         SignatureTarget.Transform transformInfo = transformIterator.next();
/*  673 */         String transformAlgo = transformInfo.getTransform();
/*  674 */         Transform transform = null;
/*      */         
/*  676 */         if (logger.isLoggable(Level.FINEST))
/*  677 */           logger.log(Level.FINEST, "Transform Algorithm is " + transformAlgo); 
/*  678 */         if (transformAlgo == "http://www.w3.org/TR/1999/REC-xpath-19991116" || transformAlgo.equals("http://www.w3.org/TR/1999/REC-xpath-19991116")) {
/*  679 */           TransformParameterSpec spec = (TransformParameterSpec)transformInfo.getAlgorithmParameters();
/*      */ 
/*      */           
/*  682 */           if (spec == null) {
/*  683 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1367_ILLEGAL_XPATH());
/*  684 */             throw new XWSSecurityException("XPATH parameters cannot be null");
/*      */           } 
/*      */ 
/*      */           
/*  688 */           transform = signatureFactory.newTransform(transformAlgo, spec);
/*      */         }
/*  690 */         else if (transformAlgo == "http://www.w3.org/2002/06/xmldsig-filter2" || transformAlgo.equals("http://www.w3.org/2002/06/xmldsig-filter2")) {
/*  691 */           TransformParameterSpec transformParams = (TransformParameterSpec)transformInfo.getAlgorithmParameters();
/*  692 */           transform = signatureFactory.newTransform(transformAlgo, transformParams);
/*  693 */         } else if (transformAlgo == "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform" || transformAlgo.equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform")) {
/*  694 */           Parameter transformParams = (Parameter)transformInfo.getAlgorithmParameters();
/*  695 */           String algo = null;
/*  696 */           if (transformParams.getParamName().equals("CanonicalizationMethod")) {
/*  697 */             algo = transformParams.getParamValue();
/*      */           }
/*  699 */           if (algo == null) {
/*  700 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1368_ILLEGAL_STR_CANONCALIZATION());
/*  701 */             throw new XWSSecurityException("STR Transform must have acanonicalization method specified");
/*      */           } 
/*      */           
/*  704 */           if (logger.isLoggable(Level.FINEST)) {
/*  705 */             logger.log(Level.FINEST, "CanonicalizationMethod is " + algo);
/*      */           }
/*  707 */           CanonicalizationMethod cm = null;
/*  708 */           C14NMethodParameterSpec spec = null;
/*      */           try {
/*  710 */             Document doc = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING).newDocumentBuilder().newDocument();
/*  711 */             Element tp = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:TransformationParameters");
/*  712 */             Element cem = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:CanonicalizationMethod");
/*  713 */             tp.appendChild(cem);
/*  714 */             cem.setAttribute("Algorithm", algo);
/*  715 */             doc.appendChild(tp);
/*  716 */             XMLStructure transformSpec = new DOMStructure(tp);
/*  717 */             transform = signatureFactory.newTransform(transformAlgo, transformSpec);
/*  718 */           } catch (Exception ex) {
/*  719 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1300_DSIG_TRANSFORM_PARAM_ERROR(), ex);
/*  720 */             throw new XWSSecurityException(ex.getMessage());
/*      */           } 
/*  722 */         } else if ("http://www.w3.org/2001/10/xml-exc-c14n#".equalsIgnoreCase(transformAlgo)) {
/*      */ 
/*      */           
/*  725 */           exclTransformToBeAdded = true;
/*  726 */           disableInclusivePrefix = transformInfo.getDisableInclusivePrefix();
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  731 */           transform = signatureFactory.newTransform(transformAlgo, (TransformParameterSpec)null);
/*      */         } 
/*  733 */         if (!"http://www.w3.org/2001/10/xml-exc-c14n#".equalsIgnoreCase(transformAlgo))
/*      */         {
/*  735 */           transformList.add(transform);
/*      */         }
/*      */       } 
/*  738 */       String targetURI = "";
/*  739 */       String signatureType = signatureTarget.getType();
/*  740 */       SOAPMessage msg = secureMessage.getSOAPMessage();
/*  741 */       boolean headersOnly = signatureTarget.isSOAPHeadersOnly();
/*  742 */       if (signatureType.equals("qname") || signatureType.equals("xpath")) {
/*      */         
/*  744 */         String expr = null;
/*  745 */         NodeList nodes = null;
/*  746 */         if (signatureType == "qname") {
/*  747 */           String targetValue = signatureTarget.getValue();
/*  748 */           boolean optimized = false;
/*  749 */           if (fpContext.getConfigType() == 1 || fpContext.getConfigType() == 2) {
/*  750 */             optimized = true;
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  759 */           if (targetValue.equals("{http://schemas.xmlsoap.org/soap/envelope/}Body")) {
/*      */             
/*      */             try {
/*  762 */               final SOAPElement se = msg.getSOAPBody();
/*      */               
/*  764 */               nodes = new NodeList() {
/*  765 */                   Node node = se;
/*      */                   public int getLength() {
/*  767 */                     if (this.node == null) {
/*  768 */                       return 0;
/*      */                     }
/*  770 */                     return 1;
/*      */                   }
/*      */                   
/*      */                   public Node item(int num) {
/*  774 */                     if (num == 0) {
/*  775 */                       return this.node;
/*      */                     }
/*  777 */                     return null;
/*      */                   }
/*      */                 };
/*      */             }
/*  781 */             catch (SOAPException se) {
/*  782 */               logger.log(Level.SEVERE, LogStringsMessages.WSS_1369_UNABLE_GET_SIGNATURE_TARGET_BY_URI());
/*  783 */               throw new XWSSecurityException("SignatureTarget with URI " + targetValue + " is not in the message");
/*      */             
/*      */             }
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  791 */             QName name = QName.valueOf(targetValue);
/*  792 */             if (!headersOnly) {
/*  793 */               if ("".equals(name.getNamespaceURI())) {
/*  794 */                 nodes = msg.getSOAPPart().getElementsByTagName(name.getLocalPart());
/*      */               }
/*  796 */               else if (!"".equals(name.getLocalPart())) {
/*  797 */                 nodes = msg.getSOAPPart().getElementsByTagNameNS(name.getNamespaceURI(), name.getLocalPart());
/*      */               } else {
/*  799 */                 nodes = msg.getSOAPPart().getElementsByTagNameNS(name.getNamespaceURI(), "*");
/*      */               } 
/*      */             } else {
/*      */               
/*      */               try {
/*  804 */                 NodeListImpl nodeListImpl = new NodeListImpl();
/*  805 */                 NodeList hdrChilds = msg.getSOAPHeader().getChildNodes();
/*  806 */                 for (int j = 0; j < hdrChilds.getLength(); j++) {
/*  807 */                   Node child = hdrChilds.item(j);
/*  808 */                   if (child.getNodeType() == 1) {
/*  809 */                     if ("".equals(name.getNamespaceURI())) {
/*  810 */                       if (name.getLocalPart().equals(child.getLocalName())) {
/*  811 */                         nodeListImpl.add(child);
/*      */                       }
/*      */                     }
/*  814 */                     else if (name.getNamespaceURI().equals("http://schemas.xmlsoap.org/ws/2004/08/addressing") || name.getNamespaceURI().equals("http://www.w3.org/2005/08/addressing")) {
/*      */                       
/*  816 */                       if (child.getNamespaceURI().equals("http://schemas.xmlsoap.org/ws/2004/08/addressing") || child.getNamespaceURI().equals("http://www.w3.org/2005/08/addressing"))
/*      */                       {
/*  818 */                         if (!"".equals(name.getLocalPart())) {
/*  819 */                           if (name.getLocalPart().equals(child.getLocalName()))
/*  820 */                             nodeListImpl.add(child); 
/*      */                         } else {
/*  822 */                           nodeListImpl.add(child);
/*      */                         }
/*      */                       
/*      */                       }
/*  826 */                     } else if (!"".equals(name.getLocalPart())) {
/*  827 */                       if (name.getNamespaceURI().equals(child.getNamespaceURI()) && name.getLocalPart().equals(child.getLocalName()))
/*      */                       {
/*  829 */                         nodeListImpl.add(child);
/*      */                       }
/*  831 */                     } else if (name.getNamespaceURI().equals(child.getNamespaceURI())) {
/*  832 */                       nodeListImpl.add(child);
/*      */                     }
/*      */                   
/*      */                   }
/*      */                 }
/*      */               
/*  838 */               } catch (SOAPException se) {
/*  839 */                 logger.log(Level.SEVERE, LogStringsMessages.WSS_1370_FAILED_PROCESS_HEADER());
/*  840 */                 throw new XWSSecurityException(se);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } else {
/*      */           
/*  846 */           expr = signatureTarget.getValue();
/*      */           
/*      */           try {
/*  849 */             XPathFactory xpathFactory = WSITXMLFactory.createXPathFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/*  850 */             XPath xpath = xpathFactory.newXPath();
/*  851 */             xpath.setNamespaceContext(secureMessage.getNamespaceContext());
/*      */ 
/*      */             
/*  854 */             XPathExpression xpathExpr = xpath.compile(expr);
/*  855 */             if (logger.isLoggable(Level.FINEST)) {
/*  856 */               logger.log(Level.FINEST, "++++++++++++++++++++++++++++++");
/*  857 */               logger.log(Level.FINEST, "Expr is " + expr);
/*  858 */               printDocument(secureMessage.getSOAPPart());
/*      */             } 
/*  860 */             nodes = (NodeList)xpathExpr.evaluate(secureMessage.getSOAPPart(), XPathConstants.NODESET);
/*  861 */           } catch (XPathExpressionException xpe) {
/*  862 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1371_FAILED_RESOLVE_X_PATH() + expr, xpe);
/*  863 */             throw new XWSSecurityException(xpe);
/*      */           } 
/*      */         } 
/*  866 */         int i = 0;
/*  867 */         if (nodes == null || nodes.getLength() <= 0) {
/*  868 */           if (signatureTarget.getEnforce()) {
/*  869 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1369_UNABLE_GET_SIGNATURE_TARGET_BY_URI());
/*  870 */             throw new XWSSecurityException("SignatureTarget with URI " + signatureTarget.getValue() + " is not in the message");
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  881 */         if (logger.isLoggable(Level.FINEST)) {
/*  882 */           logger.log(Level.FINEST, "Number of nodes " + nodes.getLength());
/*  883 */           logger.log(Level.FINEST, "+++++++++++++++END+++++++++++++++");
/*      */         } 
/*  885 */         HashMap<String, Node> elementCache = null;
/*  886 */         if (fpContext != null) {
/*  887 */           elementCache = fpContext.getElementCache();
/*      */         }
/*  889 */         while (i < nodes.getLength()) {
/*  890 */           if (logger.isLoggable(Level.FINEST))
/*  891 */             logger.log(Level.FINEST, "Nodes is " + nodes.item(i)); 
/*  892 */           Node nodeRef = nodes.item(i++);
/*  893 */           if (nodeRef.getNodeType() != 1) {
/*  894 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1371_FAILED_RESOLVE_X_PATH());
/*  895 */             throw new XWSSecurityException("XPath does not correspond to a DOM Element");
/*      */           } 
/*      */           
/*  898 */           ArrayList<Transform> clonedTransformList = (ArrayList)transformList.clone();
/*  899 */           if (exclTransformToBeAdded) {
/*      */             
/*  901 */             String transformAlgo = "http://www.w3.org/2001/10/xml-exc-c14n#";
/*  902 */             ExcC14NParameterSpec spec = null;
/*  903 */             if ((featureBinding != null && featureBinding.isBSP()) || !disableInclusivePrefix) {
/*  904 */               spec = new ExcC14NParameterSpec(getReferenceNamespacePrefixes(nodeRef));
/*      */             }
/*  906 */             Transform transform = signatureFactory.newTransform(transformAlgo, spec);
/*  907 */             clonedTransformList.add(transform);
/*      */           } 
/*  909 */           boolean w3cElem = false;
/*      */           
/*  911 */           String id = ((Element)nodeRef).getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/*  912 */           if ((id == null || id.equals("")) && (
/*  913 */             nodeRef.getNamespaceURI() == "http://www.w3.org/2000/09/xmldsig#" || nodeRef.getNamespaceURI() == "http://www.w3.org/2001/04/xmlenc#")) {
/*      */             
/*  915 */             w3cElem = true;
/*  916 */             id = ((Element)nodeRef).getAttribute("Id");
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/*  921 */           if (id == null || id.equals("")) {
/*  922 */             id = secureMessage.generateId();
/*  923 */             if (!verify) {
/*  924 */               if (w3cElem) {
/*  925 */                 XMLUtil.setIdAttr((Element)nodeRef, id);
/*      */               } else {
/*  927 */                 XMLUtil.setWsuIdAttr((Element)nodeRef, id);
/*      */               } 
/*      */             } else {
/*      */               
/*  931 */               elementCache.put(id, nodeRef);
/*      */             } 
/*      */           } 
/*      */           
/*  935 */           if (logger.isLoggable(Level.FINEST)) {
/*  936 */             logger.log(Level.FINEST, "SignedInfo val id " + id);
/*      */           }
/*  938 */           targetURI = "#" + id;
/*      */           
/*  940 */           byte[] arrayOfByte = fpContext.getDigestValue();
/*  941 */           Reference reference1 = null;
/*  942 */           if (!verify && arrayOfByte != null) {
/*  943 */             reference1 = signatureFactory.newReference(targetURI, digestMethod, clonedTransformList, null, null, arrayOfByte);
/*      */           } else {
/*  945 */             reference1 = signatureFactory.newReference(targetURI, digestMethod, clonedTransformList, null, null);
/*      */           } 
/*  947 */           references.add(reference1);
/*      */         }  continue;
/*      */       } 
/*  950 */       if (signatureType == "uri") {
/*  951 */         targetURI = signatureTarget.getValue();
/*      */         
/*  953 */         if (targetURI == null) {
/*  954 */           targetURI = "";
/*      */         }
/*  956 */         if (targetURI == "cid:*") {
/*  957 */           Iterator<AttachmentPart> itr = secureMessage.getAttachments();
/*  958 */           if (!itr.hasNext()) {
/*  959 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1372_NO_ATTACHMENT_FOUND());
/*  960 */             throw new XWSSecurityException("No attachment present in the message");
/*      */           } 
/*      */ 
/*      */           
/*  964 */           while (itr.hasNext()) {
/*  965 */             String cid = null;
/*  966 */             AttachmentPart ap = itr.next();
/*  967 */             String _cid = ap.getContentId();
/*  968 */             if (_cid.charAt(0) == '<' && _cid.charAt(_cid.length() - 1) == '>') {
/*  969 */               int lindex = _cid.lastIndexOf('>');
/*  970 */               int sindex = _cid.indexOf('<');
/*  971 */               if (lindex < sindex || lindex == sindex)
/*      */               {
/*  973 */                 logger.log(Level.SEVERE, LogStringsMessages.WSS_1303_CID_ERROR());
/*      */               }
/*  975 */               cid = "cid:" + _cid.substring(sindex + 1, lindex);
/*      */             } else {
/*  977 */               cid = "cid:" + _cid;
/*      */             } 
/*  979 */             Reference reference1 = signatureFactory.newReference(cid, digestMethod, transformList, null, null);
/*  980 */             references.add(reference1);
/*      */           } 
/*      */           continue;
/*      */         } 
/*  984 */         if (exclTransformToBeAdded) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  990 */           SOAPElement dataElement = null;
/*  991 */           if (featureBinding != null && featureBinding.isBSP()) {
/*      */ 
/*      */             
/*  994 */             String _uri = targetURI;
/*  995 */             if (targetURI.length() > 0 && targetURI.charAt(0) == '#') {
/*  996 */               _uri = targetURI.substring(1);
/*      */             }
/*  998 */             dataElement = (SOAPElement)secureMessage.getElementById(_uri);
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1004 */           String transformAlgo = "http://www.w3.org/2001/10/xml-exc-c14n#";
/* 1005 */           ExcC14NParameterSpec spec = null;
/* 1006 */           if (dataElement != null && !disableInclusivePrefix) {
/* 1007 */             spec = new ExcC14NParameterSpec(getReferenceNamespacePrefixes(dataElement));
/*      */           }
/* 1009 */           Transform transform = signatureFactory.newTransform(transformAlgo, spec);
/* 1010 */           transformList.add(transform);
/*      */         } 
/* 1012 */         if (targetURI.equals("ALL_MESSAGE_HEADERS")) {
/* 1013 */           SOAPHeader soapHeader = null;
/*      */           try {
/* 1015 */             soapHeader = secureMessage.getSOAPHeader();
/* 1016 */           } catch (SOAPException se) {
/* 1017 */             se.printStackTrace();
/*      */           } 
/* 1019 */           NodeList headers = soapHeader.getChildNodes();
/* 1020 */           Reference reference1 = null;
/* 1021 */           for (int i = 0; i < headers.getLength(); i++) {
/* 1022 */             if (headers.item(i).getNodeType() == 1) {
/* 1023 */               Element element = (Element)headers.item(i);
/* 1024 */               if (!"Security".equals(element.getLocalName()) || !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(element.getNamespaceURI())) {
/*      */                 
/* 1026 */                 reference1 = signatureFactory.newReference("#" + generateReferenceID(element, secureMessage), digestMethod, transformList, null, null);
/* 1027 */                 references.add(reference1);
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*      */       } 
/*      */       
/* 1036 */       byte[] digestValue = fpContext.getDigestValue();
/* 1037 */       Reference reference = null;
/* 1038 */       if (!verify && digestValue != null) {
/* 1039 */         reference = signatureFactory.newReference(targetURI, digestMethod, transformList, null, null, digestValue);
/*      */       } else {
/* 1041 */         reference = signatureFactory.newReference(targetURI, digestMethod, transformList, null, null);
/*      */       } 
/*      */ 
/*      */       
/* 1045 */       references.add(reference);
/*      */     } 
/*      */     
/* 1048 */     if (references.isEmpty() && 
/* 1049 */       logger.isLoggable(Level.WARNING)) {
/* 1050 */       logger.log(Level.WARNING, LogStringsMessages.WSS_1375_NO_SIGNEDPARTS());
/*      */     }
/*      */ 
/*      */     
/* 1054 */     return references;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String generateReferenceID(Element secElement, SecurableSoapMessage securableSoapMessage) {
/* 1064 */     String id = secElement.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/*      */     
/* 1066 */     if (id == null || id.equals("")) {
/*      */       try {
/* 1068 */         id = securableSoapMessage.generateId();
/* 1069 */       } catch (XWSSecurityException xse) {
/* 1070 */         xse.printStackTrace();
/*      */       } 
/* 1072 */       XMLUtil.setWsuIdAttr(secElement, id);
/*      */     } 
/* 1074 */     if (logger.isLoggable(Level.FINE)) {
/* 1075 */       logger.log(Level.FINE, "Element wsu:id attribute is: " + id);
/*      */     }
/* 1077 */     return id;
/*      */   }
/*      */   
/*      */   public URIDereferencer getDefaultResolver() {
/* 1081 */     if (this.externalURIResolver == null) {
/* 1082 */       this.externalURIResolver = getSignatureFactory().getURIDereferencer();
/*      */     }
/* 1084 */     return this.externalURIResolver;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void printDocument(Node node) {
/*      */     try {
/* 1092 */       if (logger.isLoggable(Level.FINEST)) {
/* 1093 */         logger.log(Level.FINEST, "\n");
/*      */       }
/* 1095 */       Transformer transformer = WSITXMLFactory.createTransformerFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING).newTransformer();
/* 1096 */       transformer.setOutputProperty("indent", "yes");
/* 1097 */       OutputStream baos = new ByteArrayOutputStream();
/* 1098 */       transformer.transform(new DOMSource(node), new StreamResult(baos));
/* 1099 */       byte[] bytes = ((ByteArrayOutputStream)baos).toByteArray();
/* 1100 */       if (logger.isLoggable(Level.FINEST)) {
/* 1101 */         logger.log(Level.FINEST, new String(bytes));
/* 1102 */         logger.log(Level.FINEST, "\n");
/*      */       } 
/* 1104 */     } catch (Exception ex) {
/* 1105 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1374_FAILEDTO_PRINT_DOCUMENT(), ex);
/* 1106 */       throw new RuntimeException(ex);
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
/*      */   public static final class WSSProvider
/*      */     extends Provider
/*      */   {
/*      */     private static final String INFO = "WSS_TRANSFORM (DOM WSS_TRANSFORM_PROVIDER)";
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
/*      */     public WSSProvider() {
/* 1302 */       super("WSS_TRANSFORM", 1.0D, "WSS_TRANSFORM (DOM WSS_TRANSFORM_PROVIDER)");
/* 1303 */       Map<Object, Object> map = new HashMap<Object, Object>();
/* 1304 */       map.put("TransformService.http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Complete-Transform", "com.sun.xml.wss.impl.transform.ACTransform");
/* 1305 */       map.put("TransformService.http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Complete-Transform MechanismType", "DOM");
/*      */       
/* 1307 */       map.put("TransformService.http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform", "com.sun.xml.wss.impl.transform.DOMSTRTransform");
/* 1308 */       map.put("TransformService.http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform MechanismType", "DOM");
/* 1309 */       map.put("TransformService.http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Content-Only-Transform", "com.sun.xml.wss.impl.transform.ACOTransform");
/* 1310 */       map.put("TransformService.http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Content-Only-Transform MechanismType", "DOM");
/*      */       
/* 1312 */       putAll(map);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\dsig\WSSPolicyConsumerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */