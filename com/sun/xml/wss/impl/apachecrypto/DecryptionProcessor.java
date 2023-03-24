/*      */ package com.sun.xml.wss.impl.apachecrypto;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;
/*      */ import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
/*      */ import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
/*      */ import com.sun.org.apache.xml.internal.security.encryption.XMLEncryptionException;
/*      */ import com.sun.xml.messaging.saaj.soap.AttachmentPartImpl;
/*      */ import com.sun.xml.wss.XWSSecurityException;
/*      */ import com.sun.xml.wss.core.EncryptedDataHeaderBlock;
/*      */ import com.sun.xml.wss.core.EncryptedKeyHeaderBlock;
/*      */ import com.sun.xml.wss.core.KeyInfoHeaderBlock;
/*      */ import com.sun.xml.wss.core.ReferenceListHeaderBlock;
/*      */ import com.sun.xml.wss.core.SecurityHeader;
/*      */ import com.sun.xml.wss.impl.FilterProcessingContext;
/*      */ import com.sun.xml.wss.impl.MessageConstants;
/*      */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*      */ import com.sun.xml.wss.impl.PolicyViolationException;
/*      */ import com.sun.xml.wss.impl.SecurableSoapMessage;
/*      */ import com.sun.xml.wss.impl.WssSoapFaultException;
/*      */ import com.sun.xml.wss.impl.misc.Base64;
/*      */ import com.sun.xml.wss.impl.misc.KeyResolver;
/*      */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*      */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*      */ import com.sun.xml.wss.impl.policy.mls.Target;
/*      */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*      */ import com.sun.xml.wss.impl.policy.verifier.EncryptionPolicyVerifier;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.security.Key;
/*      */ import java.security.MessageDigest;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Enumeration;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.activation.DataHandler;
/*      */ import javax.activation.DataSource;
/*      */ import javax.crypto.Cipher;
/*      */ import javax.crypto.SecretKey;
/*      */ import javax.crypto.spec.IvParameterSpec;
/*      */ import javax.mail.Header;
/*      */ import javax.mail.MessagingException;
/*      */ import javax.mail.internet.MimeBodyPart;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.soap.AttachmentPart;
/*      */ import javax.xml.soap.MimeHeader;
/*      */ import javax.xml.soap.SOAPElement;
/*      */ import javax.xml.soap.SOAPException;
/*      */ import javax.xml.soap.SOAPFactory;
/*      */ import org.w3c.dom.DOMException;
/*      */ import org.w3c.dom.Document;
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
/*      */ public class DecryptionProcessor
/*      */ {
/*  113 */   protected static final Logger log = Logger.getLogger("com.sun.xml.wss.logging.impl.crypto", "com.sun.xml.wss.logging.impl.crypto.LogStrings");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void decrypt(FilterProcessingContext context) throws XWSSecurityException {
/*  123 */     SecurableSoapMessage secureMessage = context.getSecurableSoapMessage();
/*  124 */     SecurityHeader wsseSecurity = secureMessage.findSecurityHeader();
/*  125 */     SOAPElement headerElement = wsseSecurity.getCurrentHeaderElement();
/*      */     
/*  127 */     String localName = headerElement.getLocalName();
/*  128 */     if (log.isLoggable(Level.FINEST)) {
/*  129 */       log.log(Level.FINEST, "EncryptionProcessor:decrypt : LocalName is " + localName);
/*      */     }
/*  131 */     if (localName == null) {
/*  132 */       context.setPVE((Throwable)new PolicyViolationException("Expected one of EncryptedKey,EncryptedData,ReferenceList as per receiverrequirements, found none"));
/*      */ 
/*      */       
/*  135 */       context.isPrimaryPolicyViolation(true);
/*      */       return;
/*      */     } 
/*  138 */     EncryptionPolicy inferredPolicy = null;
/*  139 */     if (context.getMode() == 0 || context.getMode() == 1) {
/*  140 */       inferredPolicy = new EncryptionPolicy();
/*  141 */       context.setInferredPolicy((WSSPolicy)inferredPolicy);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  147 */     SecretKey key = null;
/*  148 */     if ("EncryptedData".equals(localName)) {
/*  149 */       processEncryptedData(headerElement, key, context);
/*  150 */     } else if ("EncryptedKey".equals(localName)) {
/*  151 */       if (context.getMode() == 3) {
/*  152 */         inferredPolicy = new EncryptionPolicy();
/*  153 */         context.getInferredSecurityPolicy().append((SecurityPolicy)inferredPolicy);
/*      */       } 
/*  155 */       processEncryptedKey(context, headerElement);
/*  156 */     } else if ("ReferenceList".equals(localName)) {
/*  157 */       if (context.getMode() == 3) {
/*  158 */         inferredPolicy = new EncryptionPolicy();
/*  159 */         context.getInferredSecurityPolicy().append((SecurityPolicy)inferredPolicy);
/*      */       } 
/*  161 */       decryptReferenceList(headerElement, key, null, context);
/*      */     } else {
/*  163 */       context.setPVE((Throwable)new PolicyViolationException("Expected one of EncryptedKey,EncryptedData,ReferenceList as per receiverrequirements, found " + localName));
/*      */ 
/*      */       
/*  166 */       context.isPrimaryPolicyViolation(true);
/*      */       
/*      */       return;
/*      */     } 
/*  170 */     if (context.getMode() == 0) {
/*  171 */       (new EncryptionPolicyVerifier(context)).verifyPolicy(context.getSecurityPolicy(), (SecurityPolicy)context.getInferredPolicy());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void processEncryptedKey(FilterProcessingContext context, SOAPElement xencEncryptedKey) throws XWSSecurityException {
/*  178 */     boolean isBSP = false;
/*      */     try {
/*      */       SecretKey symmetricKey;
/*  181 */       xencEncryptedKey.normalize();
/*      */ 
/*      */       
/*  184 */       Element cipherData = xencEncryptedKey.getChildElements(new QName("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc")).next();
/*  185 */       String cipherValue = cipherData.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "CipherValue").item(0).getTextContent();
/*  186 */       byte[] decodedCipher = Base64.decode(cipherValue);
/*  187 */       byte[] ekSha1 = MessageDigest.getInstance("SHA-1").digest(decodedCipher);
/*  188 */       String encEkSha1 = Base64.encode(ekSha1);
/*  189 */       context.setExtraneousProperty("EKSHA1Value", encEkSha1);
/*      */       
/*  191 */       EncryptedKeyHeaderBlock encKeyHB = new EncryptedKeyHeaderBlock(xencEncryptedKey);
/*  192 */       String encryptionAlgorithm = encKeyHB.getEncryptionMethodURI();
/*  193 */       SecurityPolicy securityPolicy = context.getSecurityPolicy();
/*      */       
/*  195 */       if (securityPolicy != null && PolicyTypeUtil.encryptionPolicy(securityPolicy)) {
/*  196 */         isBSP = ((EncryptionPolicy)securityPolicy).isBSP();
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  201 */       EncryptionPolicy infPolicy = null;
/*      */       
/*  203 */       if (context.getMode() != 2) {
/*  204 */         infPolicy = (EncryptionPolicy)context.getInferredPolicy();
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  211 */       if (isBSP && 
/*  212 */         !"http://www.w3.org/2001/04/xmlenc#rsa-1_5".equals(encryptionAlgorithm) && !"http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p".equals(encryptionAlgorithm) && !"http://www.w3.org/2001/04/xmlenc#kw-tripledes".equals(encryptionAlgorithm) && !"http://www.w3.org/2001/04/xmlenc#kw-aes128".equals(encryptionAlgorithm) && !"http://www.w3.org/2001/04/xmlenc#kw-aes256".equals(encryptionAlgorithm)) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  217 */         log.log(Level.SEVERE, "WSS1227.keyEncryptionAlg.Violation");
/*  218 */         throw new XWSSecurityException("Violation of BSP5621.  KeyEncryption algorithmMUST be one of #rsa-1_5,#rsa-oaep-mgf1p,#kw-tripledes,#kw-aes256,#kw-aes128");
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/*  223 */       XMLCipher xmlCipher = XMLCipher.getInstance(encryptionAlgorithm);
/*  224 */       EncryptedKey encryptedKey = xmlCipher.loadEncryptedKey(xencEncryptedKey);
/*      */       
/*  226 */       KeyInfoHeaderBlock keyInfo = new KeyInfoHeaderBlock(encryptedKey.getKeyInfo());
/*  227 */       SOAPElement refListSoapElement = null;
/*  228 */       String commonDataEncAlgo = null;
/*      */       
/*  230 */       refListSoapElement = xencEncryptedKey.getChildElements(SOAPFactory.newInstance().createName("ReferenceList", "xenc", "http://www.w3.org/2001/04/xmlenc#")).next();
/*      */ 
/*      */       
/*  233 */       commonDataEncAlgo = getDataEncryptionAlgorithm(refListSoapElement, context.getSecurableSoapMessage());
/*      */       
/*  235 */       if (isBSP && 
/*  236 */         !"http://www.w3.org/2001/04/xmlenc#tripledes-cbc".equalsIgnoreCase(commonDataEncAlgo) && !"http://www.w3.org/2001/04/xmlenc#aes128-cbc".equalsIgnoreCase(commonDataEncAlgo) && !"http://www.w3.org/2001/04/xmlenc#aes256-cbc".equalsIgnoreCase(commonDataEncAlgo)) {
/*      */ 
/*      */         
/*  239 */         log.log(Level.SEVERE, "WSS1228.DataEncryptionAlg.Violation");
/*  240 */         throw new XWSSecurityException("Violation of BSP5620 for DataEncryption Algo permitted values");
/*      */       } 
/*      */ 
/*      */       
/*  244 */       Key key = KeyResolver.getKey(keyInfo, false, context);
/*  245 */       xmlCipher.init(4, key);
/*  246 */       if (infPolicy != null) {
/*  247 */         WSSPolicy keyBinding = (WSSPolicy)infPolicy.getKeyBinding();
/*      */         
/*  249 */         if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)keyBinding)) {
/*  250 */           ((AuthenticationTokenPolicy.X509CertificateBinding)keyBinding).setKeyAlgorithm(encryptionAlgorithm);
/*  251 */         } else if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)keyBinding)) {
/*  252 */           ((AuthenticationTokenPolicy.SAMLAssertionBinding)keyBinding).setKeyAlgorithm(encryptionAlgorithm);
/*      */         } 
/*      */       } 
/*  255 */       XMLCipher dataCipher = null;
/*      */ 
/*      */       
/*      */       try {
/*  259 */         symmetricKey = (SecretKey)xmlCipher.decryptKey(encryptedKey, commonDataEncAlgo);
/*  260 */         dataCipher = initXMLCipher(symmetricKey, commonDataEncAlgo);
/*  261 */       } catch (XMLEncryptionException xmlee) {
/*  262 */         log.log(Level.SEVERE, "WSS1200.error.decrypting.key");
/*      */         
/*  264 */         throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, "Decryption of key encryption key failed", xmlee);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  270 */       context.setExtraneousProperty("SecretKeyValue", symmetricKey);
/*  271 */       if (refListSoapElement != null) {
/*  272 */         decryptReferenceList(refListSoapElement, symmetricKey, dataCipher, context);
/*      */       }
/*  274 */     } catch (WssSoapFaultException wssSfe) {
/*  275 */       log.log(Level.SEVERE, "WSS1229.Error.Processing.EncrpytedKey");
/*  276 */       throw wssSfe;
/*  277 */     } catch (Exception e) {
/*  278 */       log.log(Level.SEVERE, "WSS1229.Error.Processing.EncrpytedKey");
/*  279 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void decryptReferenceList(SOAPElement refListSoapElement, SecretKey key, XMLCipher dataCipher, FilterProcessingContext context) throws XWSSecurityException {
/*  288 */     SecurableSoapMessage secureMessage = context.getSecurableSoapMessage();
/*  289 */     ReferenceListHeaderBlock refList = new ReferenceListHeaderBlock(refListSoapElement);
/*      */     
/*  291 */     NodeList dataRefElements = refList.getDataRefElements();
/*  292 */     int numberOfEncryptedElems = refList.size();
/*  293 */     EncryptionPolicy policy = null;
/*      */     
/*  295 */     ArrayList<EncryptionTarget> targets = null;
/*      */ 
/*      */     
/*  298 */     boolean partialReqsMet = false;
/*      */     
/*  300 */     ArrayList<EncryptionTarget> optionalTargets = null;
/*  301 */     ArrayList<EncryptionTarget> requiredTargets = null;
/*  302 */     ArrayList<EncryptedData> attachmentTargets = null;
/*  303 */     boolean skipAttachments = false;
/*  304 */     EncryptionTarget allCT = null;
/*  305 */     boolean verifyReq = false;
/*  306 */     if (context.getMode() == 0) {
/*  307 */       policy = (EncryptionPolicy)context.getSecurityPolicy();
/*  308 */       targets = ((EncryptionPolicy.FeatureBinding)policy.getFeatureBinding()).getTargetBindings();
/*  309 */       optionalTargets = new ArrayList();
/*  310 */       requiredTargets = new ArrayList();
/*      */       
/*  312 */       int j = 0;
/*  313 */       while (j < targets.size()) {
/*  314 */         EncryptionTarget et = targets.get(j++);
/*  315 */         if (et.getEnforce()) {
/*  316 */           String value = et.getValue();
/*  317 */           if (value == "cid:*") {
/*  318 */             if (attachmentTargets == null) {
/*  319 */               attachmentTargets = new ArrayList();
/*      */             }
/*  321 */             allCT = et;
/*  322 */             skipAttachments = true; continue;
/*      */           } 
/*  324 */           requiredTargets.add(et);
/*      */           continue;
/*      */         } 
/*  327 */         optionalTargets.add(et);
/*      */       } 
/*      */       
/*  330 */       if (requiredTargets.size() > 0 || skipAttachments) {
/*  331 */         verifyReq = true;
/*      */       }
/*  333 */     } else if (context.getMode() == 1) {
/*  334 */       policy = new EncryptionPolicy();
/*  335 */       MessagePolicy messagePolicy = (MessagePolicy)context.getSecurityPolicy();
/*  336 */       messagePolicy.append((SecurityPolicy)policy);
/*      */     } 
/*      */ 
/*      */     
/*  340 */     for (int i = 0; i < numberOfEncryptedElems; i++) {
/*  341 */       String refURI = ((SOAPElement)dataRefElements.item(i)).getAttribute("URI");
/*  342 */       SOAPElement encDataElement = null;
/*  343 */       EncryptedData ed = null;
/*      */       
/*  345 */       encDataElement = (SOAPElement)secureMessage.getElementById(refURI.substring(1));
/*  346 */       if (encDataElement.getLocalName() == "EncryptedHeader" || encDataElement.getLocalName().equals("EncryptedHeader")) {
/*  347 */         Iterator<SOAPElement> itr = encDataElement.getChildElements();
/*  348 */         SOAPElement encDataElementChild = null;
/*  349 */         while (itr.hasNext()) {
/*  350 */           encDataElementChild = itr.next();
/*      */         }
/*  352 */         if (encDataElementChild == null) {
/*  353 */           throw new XWSSecurityException("No EncryptedData child element found in EncryptedHeader");
/*      */         }
/*  355 */         ed = processEncryptedData(encDataElementChild, key, dataCipher, context, requiredTargets, optionalTargets, policy, false);
/*      */       } else {
/*  357 */         ed = processEncryptedData(encDataElement, key, dataCipher, context, requiredTargets, optionalTargets, policy, false);
/*      */       } 
/*      */       
/*  360 */       if (context.getMode() == 0 && verifyReq) {
/*  361 */         if (ed.isAttachmentData() && skipAttachments) {
/*  362 */           attachmentTargets.add(ed);
/*      */ 
/*      */         
/*      */         }
/*  366 */         else if (!verifyTargets(secureMessage, requiredTargets, ed, true)) {
/*  367 */           if (optionalTargets.size() == 0) {
/*  368 */             log.log(Level.SEVERE, "WSS1230.failed.receiverReq");
/*  369 */             throw new XWSSecurityException("Receiver requirement for URI" + refURI + " is not met");
/*      */           } 
/*      */           
/*  372 */           if (!verifyTargets(secureMessage, optionalTargets, ed, false)) {
/*  373 */             log.log(Level.SEVERE, "WSS1230.failed.receiverReq");
/*  374 */             throw new XWSSecurityException("Receiver requirement for URI" + refURI + " is not met");
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  381 */     if (skipAttachments) {
/*  382 */       int count = secureMessage.countAttachments();
/*  383 */       if (count > attachmentTargets.size()) {
/*  384 */         log.log(Level.SEVERE, "WSS1238.failed.receiverReq.attachments");
/*  385 */         throw new XWSSecurityException("Receiver requirement cid:* is not met,only " + attachmentTargets.size() + " attachments out of " + count + " were encrypted");
/*      */       } 
/*      */     } 
/*      */     
/*  389 */     if (context.getMode() == 0 && requiredTargets.size() > 0) {
/*  390 */       log.log(Level.SEVERE, "WSS1239.failed.receiverReq.more");
/*  391 */       throw new XWSSecurityException("More receiver requirements specified than present in the message");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void processEncryptedData(SOAPElement encDataElement, SecretKey key, FilterProcessingContext context) throws XWSSecurityException {
/*  399 */     EncryptionPolicy policy = null;
/*  400 */     ArrayList<EncryptionTarget> optionalTargets = null;
/*  401 */     ArrayList<EncryptionTarget> requiredTargets = null;
/*  402 */     ArrayList attachmentTargets = null;
/*  403 */     boolean verifyReq = false;
/*  404 */     boolean isBSP = false;
/*  405 */     if (context.getMode() == 1) {
/*  406 */       policy = new EncryptionPolicy();
/*  407 */       MessagePolicy messagePolicy = (MessagePolicy)context.getSecurityPolicy();
/*  408 */       isBSP = messagePolicy.isBSP();
/*  409 */       policy.isBSP(isBSP);
/*  410 */       messagePolicy.append((SecurityPolicy)policy);
/*  411 */     } else if (context.getMode() == 0) {
/*  412 */       policy = (EncryptionPolicy)context.getSecurityPolicy();
/*      */       
/*  414 */       ArrayList<EncryptionTarget> targets = ((EncryptionPolicy.FeatureBinding)policy.getFeatureBinding()).getTargetBindings();
/*  415 */       optionalTargets = new ArrayList();
/*  416 */       requiredTargets = new ArrayList();
/*  417 */       int i = 0;
/*  418 */       while (i < targets.size()) {
/*  419 */         EncryptionTarget et = targets.get(i++);
/*  420 */         if (et.getEnforce()) {
/*  421 */           String value = et.getValue();
/*  422 */           if (value == "cid:*") {
/*      */ 
/*      */             
/*  425 */             log.log(Level.SEVERE, "WSS1201.cid_encrypt_all_notsupported"); continue;
/*      */           } 
/*  427 */           requiredTargets.add(et);
/*      */           continue;
/*      */         } 
/*  430 */         optionalTargets.add(et);
/*      */       } 
/*      */       
/*  433 */       if (requiredTargets.size() > 0) {
/*  434 */         verifyReq = true;
/*      */       }
/*  436 */       String id = encDataElement.getAttribute("Id");
/*  437 */       EncryptedElement ed = (EncryptedElement)processEncryptedData(encDataElement, key, null, context, requiredTargets, optionalTargets, policy, true);
/*      */ 
/*      */       
/*  440 */       if (requiredTargets.size() > 1) {
/*  441 */         log.log(Level.SEVERE, "WSS1240.failed.receiverReq.moretargets");
/*  442 */         throw new XWSSecurityException("Receiver requirement has more targets specified");
/*      */       } 
/*      */       
/*  445 */       SecurableSoapMessage secureMsg = context.getSecurableSoapMessage();
/*  446 */       if (verifyReq && !verifyTargets(secureMsg, requiredTargets, ed, true)) {
/*  447 */         if (optionalTargets.size() == 0) {
/*  448 */           log.log(Level.SEVERE, "WSS1241.failed.receiverReq.encryptedData");
/*  449 */           throw new XWSSecurityException("Receiver requirement for EncryptedData with ID " + id + " is not met");
/*      */         } 
/*      */         
/*  452 */         if (!verifyTargets(secureMsg, optionalTargets, ed, false)) {
/*  453 */           log.log(Level.SEVERE, "WSS1241.failed.receiverReq.encryptedData");
/*  454 */           throw new XWSSecurityException("Receiver requirement for EncryptedData ID " + id + " is not met");
/*      */         }
/*      */       
/*      */       }
/*      */     
/*  459 */     } else if (context.getMode() == 2) {
/*      */       
/*  461 */       EncryptedElement ed = (EncryptedElement)processEncryptedData(encDataElement, key, null, context, requiredTargets, optionalTargets, policy, true);
/*      */     
/*      */     }
/*  464 */     else if (context.getMode() == 3) {
/*  465 */       context.getInferredSecurityPolicy().append((SecurityPolicy)new EncryptionPolicy());
/*  466 */       EncryptedElement ed = (EncryptedElement)processEncryptedData(encDataElement, key, null, context, requiredTargets, optionalTargets, policy, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static EncryptedData processEncryptedData(SOAPElement encDataElement, SecretKey key, XMLCipher dataCipher, FilterProcessingContext context, ArrayList requiredTargets, ArrayList optionalTargets, EncryptionPolicy encryptionPolicy, boolean updateSH) throws XWSSecurityException {
/*  477 */     EncryptedDataHeaderBlock xencEncryptedData = new EncryptedDataHeaderBlock(encDataElement);
/*  478 */     SecurableSoapMessage secureMessage = context.getSecurableSoapMessage();
/*  479 */     KeyInfoHeaderBlock keyInfo = xencEncryptedData.getKeyInfo();
/*  480 */     String algorithm = null;
/*  481 */     algorithm = xencEncryptedData.getEncryptionMethodURI();
/*      */     
/*  483 */     EncryptionPolicy inferredPolicy = (EncryptionPolicy)context.getInferredPolicy();
/*  484 */     EncryptionPolicy.FeatureBinding fb = null;
/*      */ 
/*      */     
/*  487 */     EncryptionPolicy inferredWsdlEncPolicy = null;
/*  488 */     if (context.getMode() == 3) {
/*      */       try {
/*  490 */         int i = context.getInferredSecurityPolicy().size() - 1;
/*  491 */         inferredWsdlEncPolicy = (EncryptionPolicy)context.getInferredSecurityPolicy().get(i);
/*  492 */       } catch (Exception e) {
/*  493 */         log.log(Level.SEVERE, "WSS1237.Error.Processing.EncrpytedData", e);
/*  494 */         throw new XWSSecurityException(e);
/*      */       } 
/*      */     }
/*      */     
/*  498 */     if (inferredPolicy != null) {
/*  499 */       fb = (EncryptionPolicy.FeatureBinding)inferredPolicy.getFeatureBinding();
/*  500 */       fb.setDataEncryptionAlgorithm(algorithm);
/*      */     } 
/*      */     
/*  503 */     SecretKey symmetricKey = null;
/*  504 */     if (keyInfo == null) {
/*  505 */       if (key == null) {
/*  506 */         log.log(Level.SEVERE, "WSS1231.null.SymmetricKey");
/*  507 */         throw new XWSSecurityException("Symmetric Key is null");
/*      */       } 
/*  509 */       symmetricKey = key;
/*      */     } else {
/*  511 */       context.setDataEncryptionAlgorithm(algorithm);
/*  512 */       symmetricKey = (SecretKey)KeyResolver.getKey(keyInfo, false, context);
/*  513 */       context.setDataEncryptionAlgorithm(null);
/*      */     } 
/*      */     
/*  516 */     if (symmetricKey == null) {
/*  517 */       log.log(Level.SEVERE, "WSS1202.couldnot.locate.symmetrickey");
/*  518 */       throw new XWSSecurityException("Couldn't locate symmetricKey for decryption");
/*      */     } 
/*      */     
/*  521 */     boolean isAttachment = false;
/*  522 */     String type = xencEncryptedData.getType();
/*  523 */     if (type.equals("http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Content-Only") || type.equals("http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Complete"))
/*      */     {
/*  525 */       isAttachment = true;
/*      */     }
/*      */     
/*  528 */     Node parent = null;
/*  529 */     Node prevSibling = null;
/*  530 */     boolean contentOnly = false;
/*      */     
/*  532 */     Element actualEncrypted = null;
/*      */     
/*  534 */     AttachmentPart encryptedAttachment = null;
/*  535 */     AttachmentPartImpl _attachmentBuffer = new AttachmentPartImpl();
/*      */     
/*  537 */     if (isAttachment) {
/*      */       
/*  539 */       String uri = xencEncryptedData.getCipherReference(false, null).getAttribute("URI");
/*  540 */       contentOnly = type.equals("http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Content-Only");
/*      */       
/*      */       try {
/*  543 */         AttachmentPart p = secureMessage.getAttachmentPart(uri);
/*  544 */         Iterator<MimeHeader> j = p.getAllMimeHeaders();
/*  545 */         while (j.hasNext()) {
/*  546 */           MimeHeader mh = j.next();
/*  547 */           _attachmentBuffer.setMimeHeader(mh.getName(), mh.getValue());
/*      */         } 
/*  549 */         _attachmentBuffer.setDataHandler(p.getDataHandler());
/*  550 */         encryptedAttachment = decryptAttachment(secureMessage, xencEncryptedData, symmetricKey);
/*      */       }
/*  552 */       catch (IOException ioe) {
/*  553 */         log.log(Level.SEVERE, "WSS1232.failedto.decrypt.attachment", ioe);
/*  554 */         throw new XWSSecurityException(ioe);
/*  555 */       } catch (SOAPException se) {
/*  556 */         log.log(Level.SEVERE, "WSS1232.failedto.decrypt.attachment", se);
/*  557 */         throw new XWSSecurityException(se);
/*  558 */       } catch (MessagingException me) {
/*  559 */         log.log(Level.SEVERE, "WSS1232.failedto.decrypt.attachment", (Throwable)me);
/*  560 */         throw new XWSSecurityException(me);
/*      */       } 
/*  562 */       encDataElement.detachNode();
/*      */     } else {
/*  564 */       parent = encDataElement.getParentNode();
/*  565 */       prevSibling = encDataElement.getPreviousSibling();
/*  566 */       if (dataCipher == null) {
/*  567 */         dataCipher = initXMLCipher(symmetricKey, algorithm);
/*      */       }
/*  569 */       if (parent.getLocalName() == "EncryptedHeader" || parent.getLocalName().equals("EncryptedHeader")) {
/*      */         try {
/*  571 */           encDataElement.getParentNode().getParentNode().replaceChild(encDataElement, parent);
/*  572 */           parent = encDataElement.getParentNode();
/*  573 */           prevSibling = encDataElement.getPreviousSibling();
/*  574 */         } catch (DOMException e) {
/*  575 */           log.log(Level.SEVERE, "WSS1242.exception.dom", e);
/*  576 */           throw new XWSSecurityException(e);
/*      */         } 
/*      */       }
/*  579 */       decryptElementWithCipher(dataCipher, encDataElement, secureMessage);
/*      */       
/*  581 */       SOAPElement currentNode = null;
/*  582 */       if (updateSH && secureMessage.findSecurityHeader().getCurrentHeaderBlockElement() == encDataElement) {
/*      */         
/*  584 */         if (prevSibling == null) {
/*  585 */           currentNode = (SOAPElement)parent.getFirstChild();
/*      */         } else {
/*  587 */           currentNode = (SOAPElement)prevSibling.getNextSibling();
/*      */         } 
/*  589 */         secureMessage.findSecurityHeader().setCurrentHeaderElement(currentNode);
/*      */       } 
/*      */       
/*  592 */       if (xencEncryptedData.getType().equals("http://www.w3.org/2001/04/xmlenc#Content")) {
/*  593 */         actualEncrypted = (Element)resolveEncryptedNode(parent, prevSibling, true);
/*  594 */         contentOnly = true;
/*      */       }
/*  596 */       else if (xencEncryptedData.getType().equals("http://www.w3.org/2001/04/xmlenc#Element")) {
/*  597 */         actualEncrypted = (Element)resolveEncryptedNode(parent, prevSibling, false);
/*  598 */         contentOnly = false;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  603 */     if (context.getMode() == 1) {
/*      */       
/*  605 */       if (encryptionPolicy == null) {
/*  606 */         encryptionPolicy = new EncryptionPolicy();
/*      */       }
/*  608 */       EncryptionPolicy.FeatureBinding eFB = (EncryptionPolicy.FeatureBinding)encryptionPolicy.getFeatureBinding();
/*      */       
/*  610 */       EncryptionTarget encTarget = new EncryptionTarget();
/*      */       
/*  612 */       encTarget.setDataEncryptionAlgorithm(algorithm);
/*  613 */       encTarget.setContentOnly(contentOnly);
/*  614 */       if (isAttachment) {
/*  615 */         encTarget.addCipherReferenceTransform(type);
/*      */       }
/*  617 */       if (encryptedAttachment != null) {
/*  618 */         encTarget.setValue(encryptedAttachment.getContentId());
/*      */       } else {
/*  620 */         String id = actualEncrypted.getAttribute("Id");
/*      */         
/*  622 */         if ("".equals(id)) {
/*  623 */           id = actualEncrypted.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/*      */         }
/*  625 */         encTarget.setValue(id);
/*      */       } 
/*  627 */       encTarget.setType("uri");
/*  628 */       encTarget.setElementData(actualEncrypted);
/*  629 */       Iterator<String> transformItr = xencEncryptedData.getTransforms();
/*  630 */       if (transformItr != null) {
/*  631 */         while (transformItr.hasNext()) {
/*  632 */           encTarget.addCipherReferenceTransform(transformItr.next());
/*      */         }
/*      */       }
/*  635 */       eFB.addTargetBinding(encTarget);
/*  636 */       return null;
/*  637 */     }  if (context.getMode() == 0 || context.getMode() == 2) {
/*      */       
/*  639 */       if (isAttachment) {
/*  640 */         return new AttachmentData(encryptedAttachment.getContentId(), contentOnly);
/*      */       }
/*  642 */       EncryptedElement encryptedElement = new EncryptedElement(actualEncrypted, contentOnly);
/*  643 */       return encryptedElement;
/*  644 */     }  if (context.getMode() == 3) {
/*  645 */       QName qname = new QName(actualEncrypted.getNamespaceURI(), actualEncrypted.getLocalName());
/*  646 */       EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)inferredWsdlEncPolicy.getFeatureBinding();
/*      */       
/*  648 */       EncryptionTarget target = new EncryptionTarget();
/*  649 */       if (actualEncrypted.getNamespaceURI() != null && (actualEncrypted.getNamespaceURI().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") || actualEncrypted.getNamespaceURI().equals("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd") || actualEncrypted.getNamespaceURI().equals("http://schemas.xmlsoap.org/ws/2005/02/sc") || actualEncrypted.getNamespaceURI().equals("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd"))) {
/*      */ 
/*      */ 
/*      */         
/*  653 */         String id = actualEncrypted.getAttribute("Id");
/*  654 */         if ("".equals(id)) {
/*  655 */           id = actualEncrypted.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
/*      */         }
/*  657 */         target.setValue(id);
/*  658 */         target.setType("uri");
/*      */       } else {
/*  660 */         target.setQName(qname);
/*  661 */         target.setType("qname");
/*      */       } 
/*      */       
/*  664 */       target.setDataEncryptionAlgorithm(algorithm);
/*  665 */       target.setContentOnly(contentOnly);
/*  666 */       featureBinding.addTargetBinding(target);
/*  667 */       if (qname.getLocalPart().equals("Assertion"))
/*      */       {
/*  669 */         featureBinding.encryptsIssuedToken(true);
/*      */       }
/*      */     } 
/*  672 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getDataEncryptionAlgorithm(SOAPElement referenceList, SecurableSoapMessage secureMsg) throws XWSSecurityException {
/*      */     try {
/*  678 */       ReferenceListHeaderBlock refList = new ReferenceListHeaderBlock(referenceList);
/*  679 */       NodeList dataRefElements = refList.getDataRefElements();
/*  680 */       Element dataRef = (Element)dataRefElements.item(0);
/*  681 */       String refURI = dataRef.getAttribute("URI");
/*      */       
/*  683 */       SOAPElement encDataElement = null;
/*  684 */       encDataElement = (SOAPElement)secureMsg.getElementById(refURI.substring(1));
/*  685 */       NodeList nodeList = encDataElement.getElementsByTagNameNS("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod");
/*  686 */       if (nodeList.getLength() <= 0) {
/*  687 */         return "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*      */       }
/*  689 */       Element em = (Element)nodeList.item(0);
/*  690 */       if (em != null) {
/*  691 */         String algo = em.getAttribute("Algorithm");
/*  692 */         if ("".equals(algo)) {
/*  693 */           return "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*      */         }
/*  695 */         return algo;
/*      */       } 
/*  697 */     } catch (XWSSecurityException xe) {
/*  698 */       log.log(Level.SEVERE, "WSS1233.failed.get.DataEncryptionAlgorithm", (Throwable)xe);
/*  699 */       throw xe;
/*  700 */     } catch (Exception ex) {
/*  701 */       log.log(Level.SEVERE, "WSS1233.failed.get.DataEncryptionAlgorithm", ex);
/*  702 */       throw new XWSSecurityException(ex);
/*      */     } 
/*  704 */     return "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static AttachmentPart decryptAttachment(SecurableSoapMessage ssm, EncryptedDataHeaderBlock edhb, SecretKey symmetricKey) throws IOException, SOAPException, MessagingException, XWSSecurityException {
/*  712 */     String uri = edhb.getCipherReference(false, null).getAttribute("URI");
/*  713 */     boolean contentOnly = edhb.getType().equals("http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Content-Only");
/*      */     
/*  715 */     String mimeType = edhb.getMimeType();
/*  716 */     Element dsTransform = edhb.getTransforms().next();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  725 */     if (!dsTransform.getAttribute("Algorithm").equals("http://docs.oasis-open.org/wss/2004/XX/oasis-2004XX-wss-swa-profile-1.0#Attachment-Content-Only-Transform")) {
/*      */       
/*  727 */       log.log(Level.SEVERE, "WSS1234.invalid.transform=");
/*  728 */       throw new XWSSecurityException("Unexpected ds:Transform, " + dsTransform.getAttribute("Algorithm"));
/*      */     } 
/*      */     
/*  731 */     AttachmentPart part = ssm.getAttachmentPart(uri);
/*  732 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  733 */     part.getDataHandler().writeTo(baos);
/*      */     
/*  735 */     byte[] cipherInput = baos.toByteArray();
/*  736 */     String tmp = edhb.getEncryptionMethodURI();
/*      */ 
/*      */     
/*  739 */     Cipher decryptor = null;
/*  740 */     byte[] cipherOutput = null;
/*      */     try {
/*  742 */       String dataAlgorithm = JCEMapper.translateURItoJCEID(tmp);
/*  743 */       decryptor = Cipher.getInstance(dataAlgorithm);
/*      */ 
/*      */ 
/*      */       
/*  747 */       int ivLen = decryptor.getBlockSize();
/*  748 */       byte[] ivBytes = new byte[ivLen];
/*      */       
/*  750 */       System.arraycopy(cipherInput, 0, ivBytes, 0, ivLen);
/*  751 */       IvParameterSpec iv = new IvParameterSpec(ivBytes);
/*      */       
/*  753 */       decryptor.init(2, symmetricKey, iv);
/*      */       
/*  755 */       cipherOutput = decryptor.doFinal(cipherInput, ivLen, cipherInput.length - ivLen);
/*  756 */     } catch (Exception e) {
/*  757 */       log.log(Level.SEVERE, "WSS1232.failedto.decrypt.attachment", e);
/*  758 */       throw new XWSSecurityException(e);
/*      */     } 
/*      */     
/*  761 */     InputStream is = new ByteArrayInputStream(cipherOutput);
/*  762 */     if (contentOnly) {
/*      */       
/*  764 */       part.setContentType(mimeType);
/*      */ 
/*      */       
/*  767 */       String[] cLength = part.getMimeHeader("Content-Length");
/*  768 */       if (cLength != null && !cLength[0].equals("")) {
/*  769 */         part.setMimeHeader("Content-Length", Integer.toString(cipherOutput.length));
/*      */       }
/*      */       
/*  772 */       part.clearContent();
/*  773 */       part.setDataHandler(new DataHandler(new _DS(cipherOutput, mimeType)));
/*      */     } else {
/*      */       
/*  776 */       MimeBodyPart decryptedAttachment = new MimeBodyPart(is);
/*      */       
/*  778 */       String dcId = decryptedAttachment.getContentID();
/*  779 */       if (dcId == null || !uri.substring(4).equals(dcId.substring(1, dcId.length() - 1))) {
/*  780 */         log.log(Level.SEVERE, "WSS1234.unmatched.content-id");
/*  781 */         throw new XWSSecurityException("Content-Ids in encrypted and decrypted attachments donot match");
/*      */       } 
/*      */       
/*  784 */       part.removeAllMimeHeaders();
/*      */ 
/*      */       
/*  787 */       Enumeration<Header> h_enum = decryptedAttachment.getAllHeaders();
/*  788 */       while (h_enum.hasMoreElements()) {
/*  789 */         Header hdr = h_enum.nextElement();
/*  790 */         String hname = hdr.getName();
/*  791 */         String hvale = hdr.getValue();
/*  792 */         part.setMimeHeader(hname, hvale);
/*      */       } 
/*      */ 
/*      */       
/*  796 */       part.clearContent();
/*  797 */       part.setDataHandler(decryptedAttachment.getDataHandler());
/*      */     } 
/*      */     
/*  800 */     return part;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean verifyTargets(SecurableSoapMessage ssm, ArrayList<EncryptionTarget> reqTargets, EncryptedData encData, boolean requiredTarget) throws XWSSecurityException {
/*  805 */     boolean found = false;
/*  806 */     for (int et = 0; et < reqTargets.size(); et++) {
/*  807 */       EncryptionTarget encTarget = reqTargets.get(et);
/*      */       
/*  809 */       if (encData.isElementData()) {
/*  810 */         EncryptedElement elementData = (EncryptedElement)encData;
/*  811 */         if (encTarget.getType() == "uri") {
/*  812 */           if (!encTarget.isAttachment()) {
/*      */ 
/*      */             
/*  815 */             Element element = ssm.getElementById(encTarget.getValue());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  820 */             EncryptedElement ee = new EncryptedElement(element, encTarget.getContentOnly());
/*  821 */             if (ee.equals((EncryptedElement)encData)) {
/*  822 */               found = true;
/*  823 */               reqTargets.remove(et); break;
/*      */             } 
/*      */           } 
/*  826 */         } else if (encTarget.getType() == "qname") {
/*  827 */           QName qname = encTarget.getQName();
/*  828 */           String localPart = qname.getLocalPart();
/*  829 */           if (localPart.equals(elementData.getElement().getLocalName())) {
/*  830 */             ArrayList list = getAllTargetElements(ssm, encTarget, requiredTarget);
/*  831 */             if (contains(list, (EncryptedElement)encData)) {
/*  832 */               reqTargets.remove(et);
/*  833 */               found = true;
/*      */               break;
/*      */             } 
/*      */           } 
/*  837 */         } else if (encTarget.getType() == "xpath") {
/*  838 */           ArrayList list = getAllTargetElements(ssm, encTarget, requiredTarget);
/*  839 */           if (contains(list, (EncryptedElement)encData)) {
/*  840 */             reqTargets.remove(et);
/*  841 */             found = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*  846 */       } else if (encTarget.getType() == "uri" && encTarget.isAttachment()) {
/*  847 */         if (encTarget.getValue().startsWith("cid:") && encTarget.getEnforce()) {
/*  848 */           AttachmentPart ap = ssm.getAttachmentPart(encTarget.getValue());
/*  849 */           AttachmentData ad = (AttachmentData)encData;
/*  850 */           if (ap != null && ad.getCID() == ap.getContentId() && ad.isContentOnly() == encTarget.getContentOnly()) {
/*      */             
/*  852 */             found = true;
/*  853 */             reqTargets.remove(et);
/*      */             break;
/*      */           } 
/*      */         } else {
/*  857 */           reqTargets.remove(et);
/*  858 */           found = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  864 */     return found;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean contains(List<EncryptedElement> targetList, EncryptedElement ee) {
/*  869 */     for (int i = 0; i < targetList.size(); i++) {
/*  870 */       EncryptedElement ed = targetList.get(i);
/*  871 */       if (ed.equals(ee))
/*  872 */         return true; 
/*      */     } 
/*  874 */     return false;
/*      */   }
/*      */   private static boolean isEquals(EncryptedData msgEd, EncryptedData reqEd) {
/*  877 */     if (msgEd.isElementData() && reqEd.isElementData()) {
/*  878 */       ((EncryptedElement)msgEd).equals((EncryptedElement)reqEd);
/*  879 */     } else if (msgEd.isAttachmentData() && reqEd.isAttachmentData()) {
/*  880 */       ((AttachmentData)msgEd).equals((AttachmentData)reqEd);
/*      */     } 
/*  882 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ArrayList getAllTargetElements(SecurableSoapMessage ssm, EncryptionTarget target, boolean reqElements) throws XWSSecurityException {
/*  890 */     ArrayList result = new ArrayList();
/*      */     
/*  892 */     boolean contentOnly = target.getContentOnly();
/*      */     
/*      */     try {
/*  895 */       Object obj = ssm.getMessageParts((Target)target);
/*      */       
/*  897 */       if (obj instanceof SOAPElement) {
/*  898 */         contribute((Node)obj, result, contentOnly);
/*  899 */       } else if (obj instanceof NodeList) {
/*  900 */         contribute((NodeList)obj, result, contentOnly);
/*  901 */       } else if (obj instanceof Node) {
/*  902 */         contribute((Node)obj, result, contentOnly);
/*      */       } 
/*  904 */     } catch (XWSSecurityException xwse) {
/*  905 */       if (reqElements) {
/*  906 */         log.log(Level.SEVERE, "WSS1235.failedto.get.targetElements", (Throwable)xwse);
/*  907 */         throw xwse;
/*      */       } 
/*      */     } 
/*      */     
/*  911 */     return result;
/*      */   }
/*      */   
/*      */   private static void contribute(NodeList targetElements, ArrayList result, boolean contentOnly) {
/*  915 */     for (int i = 0; i < targetElements.getLength(); i++)
/*  916 */       contribute(targetElements.item(i), result, contentOnly); 
/*      */   }
/*      */   
/*      */   private static void contribute(Node element, ArrayList<EncryptedElement> result, boolean contentOnly) {
/*  920 */     EncryptedElement targetElement = new EncryptedElement((Element)element, contentOnly);
/*  921 */     result.add(targetElement);
/*      */   }
/*      */   
/*      */   private static void contribute(AttachmentPart element, ArrayList<AttachmentData> result, boolean contentOnly) {
/*  925 */     AttachmentData targetElement = new AttachmentData(element.getContentId(), contentOnly);
/*  926 */     result.add(targetElement);
/*      */   }
/*      */   
/*      */   private static Node resolveEncryptedNode(Node parent, Node prevSibling, boolean contentOnly) {
/*  930 */     Node actualEncrypted = null;
/*  931 */     if (!contentOnly)
/*  932 */     { if (prevSibling == null) {
/*  933 */         actualEncrypted = parent.getFirstChild();
/*      */       } else {
/*  935 */         actualEncrypted = prevSibling.getNextSibling();
/*      */       }  }
/*  937 */     else { actualEncrypted = parent; }
/*  938 */      return actualEncrypted;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static XMLCipher initXMLCipher(Key key, String algorithm) throws XWSSecurityException {
/*      */     XMLCipher xmlCipher;
/*      */     try {
/*  946 */       xmlCipher = XMLCipher.getInstance(algorithm);
/*  947 */       xmlCipher.init(2, key);
/*  948 */     } catch (XMLEncryptionException xee) {
/*      */       
/*  950 */       log.log(Level.SEVERE, "WSS1203.unableto.decrypt.message", new Object[] { xee.getMessage() });
/*      */ 
/*      */ 
/*      */       
/*  954 */       throw new XWSSecurityException("Unable to decrypt message", xee);
/*      */     } 
/*  956 */     return xmlCipher;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Document decryptElementWithCipher(XMLCipher xmlCipher, SOAPElement element, SecurableSoapMessage secureMessage) throws XWSSecurityException {
/*  963 */     Document document = null;
/*      */ 
/*      */ 
/*      */     
/*  967 */     element.normalize();
/*      */     try {
/*  969 */       document = xmlCipher.doFinal(secureMessage.getSOAPPart(), element);
/*  970 */     } catch (Exception e) {
/*  971 */       log.log(Level.SEVERE, "WSS1203.unableto.decrypt.message", new Object[] { e.getMessage() });
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  976 */       XWSSecurityException xse = new XWSSecurityException("Unable to decrypt message", e);
/*      */       
/*  978 */       throw SecurableSoapMessage.newSOAPFaultException(MessageConstants.WSSE_FAILED_CHECK, "Unable to decrypt message", xse);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  983 */     return document;
/*      */   }
/*      */   
/*      */   private static interface EncryptedData {
/*      */     boolean isElementData();
/*      */     
/*      */     boolean isAttachmentData(); }
/*      */   
/*      */   private static class AttachmentData implements EncryptedData {
/*  992 */     private String cid = null; private boolean contentOnly = false;
/*      */     
/*      */     public AttachmentData(String cid, boolean co) {
/*  995 */       this.cid = cid;
/*  996 */       this.contentOnly = co;
/*      */     }
/*      */     public String getCID() {
/*  999 */       return this.cid;
/*      */     }
/*      */     public boolean isContentOnly() {
/* 1002 */       return this.contentOnly;
/*      */     }
/*      */     
/*      */     public boolean equals(AttachmentData data) {
/* 1006 */       if (this.cid != null && this.cid.equals(data.getCID()) && this.contentOnly == data.isContentOnly())
/*      */       {
/* 1008 */         return true;
/*      */       }
/* 1010 */       return false;
/*      */     }
/*      */     
/*      */     public boolean isElementData() {
/* 1014 */       return false;
/*      */     }
/*      */     
/*      */     public boolean isAttachmentData() {
/* 1018 */       return true;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class EncryptedElement implements EncryptedData {
/*      */     private Element element;
/*      */     private boolean contentOnly;
/* 1025 */     private EncryptionPolicy policy = null;
/*      */     
/*      */     public EncryptedElement(Element element, boolean contentOnly) {
/* 1028 */       this.element = element;
/* 1029 */       this.contentOnly = contentOnly;
/*      */     }
/*      */     
/*      */     public Element getElement() {
/* 1033 */       return this.element;
/*      */     }
/*      */     
/*      */     public boolean getContentOnly() {
/* 1037 */       return this.contentOnly;
/*      */     }
/*      */     
/*      */     public boolean equals(EncryptedElement element) {
/* 1041 */       EncryptedElement encryptedElement = element;
/* 1042 */       return (encryptedElement.getElement() == this.element && encryptedElement.getContentOnly() == this.contentOnly);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setpolicy(EncryptionPolicy policy) {
/* 1049 */       this.policy = policy;
/*      */     }
/*      */     
/*      */     public EncryptionPolicy getPolicy() {
/* 1053 */       return this.policy;
/*      */     }
/*      */     
/*      */     public boolean isElementData() {
/* 1057 */       return true;
/*      */     }
/*      */     
/*      */     public boolean isAttachmentData() {
/* 1061 */       return false;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class _DS implements DataSource {
/* 1066 */     byte[] _b = null;
/* 1067 */     String _mt = null;
/*      */     _DS(byte[] b, String mt) {
/* 1069 */       this._b = b; this._mt = mt;
/*      */     }
/*      */     public InputStream getInputStream() throws IOException {
/* 1072 */       return new ByteArrayInputStream(this._b);
/*      */     }
/*      */     
/*      */     public OutputStream getOutputStream() throws IOException {
/* 1076 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 1077 */       baos.write(this._b, 0, this._b.length);
/* 1078 */       return baos;
/*      */     }
/*      */     public String getName() {
/* 1081 */       return "_DS";
/*      */     } public String getContentType() {
/* 1083 */       return this._mt;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\apachecrypto\DecryptionProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */