/*     */ package com.sun.xml.ws.security.opt.impl.dsig;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.InclusiveNamespacesType;
/*     */ import com.sun.xml.security.core.dsig.ObjectFactory;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.CanonicalizationMethod;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.Transform;
/*     */ import com.sun.xml.ws.security.opt.crypto.jaxb.JAXBSignatureFactory;
/*     */ import com.sun.xml.ws.security.opt.crypto.jaxb.JAXBStructure;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.SSEData;
/*     */ import com.sun.xml.ws.security.opt.impl.message.SOAPBody;
/*     */ import com.sun.xml.ws.security.opt.impl.message.SecuredMessage;
/*     */ import com.sun.xml.ws.security.opt.impl.outgoing.SecurityHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.TransformationParametersType;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.LazyKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.Parameter;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.crypto.XMLStructure;
/*     */ import javax.xml.crypto.dsig.CanonicalizationMethod;
/*     */ import javax.xml.crypto.dsig.DigestMethod;
/*     */ import javax.xml.crypto.dsig.Reference;
/*     */ import javax.xml.crypto.dsig.SignatureMethod;
/*     */ import javax.xml.crypto.dsig.SignedInfo;
/*     */ import javax.xml.crypto.dsig.Transform;
/*     */ import javax.xml.crypto.dsig.XMLSignature;
/*     */ import javax.xml.crypto.dsig.XMLSignatureFactory;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*     */ import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
/*     */ import javax.xml.crypto.dsig.spec.TransformParameterSpec;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignatureElementFactory
/*     */ {
/* 112 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignature constructSignature(SignedInfo signInfo, KeyInfo keyInfo, String id) {
/* 128 */     return getSignatureFactory().newXMLSignature(signInfo, keyInfo, null, id, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLSignature constructSignature(SignedInfo signInfo, KeyInfo keyInfo) {
/* 138 */     return getSignatureFactory().newXMLSignature(signInfo, keyInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   public SignedInfo constructSignedInfo(JAXBFilterProcessingContext fpContext) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, XWSSecurityException {
/* 143 */     if (PolicyTypeUtil.signaturePolicy(fpContext.getSecurityPolicy())) {
/* 144 */       return generateSignedInfo(fpContext);
/*     */     }
/* 146 */     return null;
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
/*     */   private SignedInfo generateSignedInfo(JAXBFilterProcessingContext fpContext) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, XWSSecurityException {
/* 159 */     SignaturePolicy signaturePolicy = (SignaturePolicy)fpContext.getSecurityPolicy();
/* 160 */     SignaturePolicy.FeatureBinding featureBinding = (SignaturePolicy.FeatureBinding)signaturePolicy.getFeatureBinding();
/* 161 */     MLSPolicy keyBinding = signaturePolicy.getKeyBinding();
/*     */     
/* 163 */     XMLSignatureFactory signatureFactory = getSignatureFactory();
/* 164 */     String canonicalAlgo = featureBinding.getCanonicalizationAlgorithm();
/* 165 */     ArrayList targetList = featureBinding.getTargetBindings();
/* 166 */     ArrayList cloneList = targetList;
/* 167 */     if (signaturePolicy.getKeyBinding() instanceof LazyKeyBinding) {
/* 168 */       LazyKeyBinding lkb = (LazyKeyBinding)signaturePolicy.getKeyBinding();
/* 169 */       if (lkb.getRealId() != null) {
/* 170 */         cloneList = (ArrayList)targetList.clone();
/* 171 */         Iterator<SignatureTarget> it = cloneList.iterator();
/* 172 */         while (it.hasNext()) {
/* 173 */           SignatureTarget o = it.next();
/* 174 */           if (o.getValue().equals("#" + lkb.getSTRID())) {
/* 175 */             o.setValue("#" + lkb.getRealId());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 180 */     String keyAlgo = null;
/* 181 */     String algo = fpContext.getAlgorithmSuite().getSignatureAlgorithm();
/*     */     
/* 183 */     keyAlgo = SecurityUtil.getKeyAlgo(algo);
/*     */     
/* 185 */     if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)keyBinding)) {
/* 186 */       AuthenticationTokenPolicy.UsernameTokenBinding untBinding = (AuthenticationTokenPolicy.UsernameTokenBinding)keyBinding;
/*     */       
/* 188 */       if (!"".equals(untBinding.getKeyAlgorithm())) {
/* 189 */         keyAlgo = untBinding.getKeyAlgorithm();
/*     */       } else {
/* 191 */         keyAlgo = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
/*     */       } 
/* 193 */     } else if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)keyBinding)) {
/* 194 */       AuthenticationTokenPolicy.X509CertificateBinding certificateBinding = (AuthenticationTokenPolicy.X509CertificateBinding)keyBinding;
/*     */       
/* 196 */       if (!"".equals(certificateBinding.getKeyAlgorithm())) {
/* 197 */         keyAlgo = certificateBinding.getKeyAlgorithm();
/*     */       }
/* 199 */     } else if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)keyBinding)) {
/* 200 */       AuthenticationTokenPolicy.SAMLAssertionBinding samlBinding = (AuthenticationTokenPolicy.SAMLAssertionBinding)keyBinding;
/*     */       
/* 202 */       if (!"".equals(samlBinding.getKeyAlgorithm())) {
/* 203 */         keyAlgo = samlBinding.getKeyAlgorithm();
/*     */       }
/* 205 */     } else if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)keyBinding)) {
/* 206 */       SymmetricKeyBinding symmetricKeybinding = (SymmetricKeyBinding)keyBinding;
/* 207 */       if (!"".equals(symmetricKeybinding.getKeyAlgorithm())) {
/* 208 */         keyAlgo = symmetricKeybinding.getKeyAlgorithm();
/*     */       } else {
/* 210 */         keyAlgo = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
/*     */       } 
/* 212 */     } else if (PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)keyBinding)) {
/* 213 */       keyAlgo = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
/* 214 */     } else if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)keyBinding)) {
/* 215 */       keyAlgo = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
/* 216 */       DerivedTokenKeyBinding dtkBinding = (DerivedTokenKeyBinding)keyBinding;
/*     */       
/* 218 */       if (fpContext.getTrustContext() != null && fpContext.getTrustContext().getProofKey() == null && PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)dtkBinding.getOriginalKeyBinding()))
/*     */       {
/* 220 */         keyAlgo = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/*     */       }
/*     */     }
/* 223 */     else if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)keyBinding)) {
/*     */       
/* 225 */       if (fpContext.getTrustContext() != null) {
/* 226 */         keyAlgo = fpContext.getTrustContext().getSignWith();
/*     */       }
/* 228 */       if (keyAlgo == null) {
/* 229 */         keyAlgo = "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
/*     */       }
/*     */       
/* 232 */       if (fpContext.getTrustContext() != null && fpContext.getTrustContext().getProofKey() == null)
/*     */       {
/* 234 */         if (fpContext.getTrustContext().getSignWith() == null) {
/* 235 */           keyAlgo = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/*     */         }
/*     */       }
/* 238 */     } else if (PolicyTypeUtil.keyValueTokenBinding((SecurityPolicy)keyBinding)) {
/* 239 */       keyAlgo = "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/*     */     } else {
/* 241 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1703_UNSUPPORTED_KEYBINDING_SIGNATUREPOLICY(keyBinding));
/* 242 */       throw new XWSSecurityException("Unsupported KeyBinding for SignaturePolicy");
/*     */     } 
/* 244 */     C14NMethodParameterSpec spec = null;
/* 245 */     if ("http://www.w3.org/2001/10/xml-exc-c14n#".equalsIgnoreCase(canonicalAlgo)) {
/* 246 */       if (!fpContext.getDisableIncPrefix()) {
/* 247 */         List<String> inc = new ArrayList();
/* 248 */         inc.add("wsse"); inc.add("S");
/* 249 */         spec = new ExcC14NParameterSpec(inc);
/*     */       } 
/* 251 */       ((NamespaceContextEx)fpContext.getNamespaceContext()).addExc14NS();
/*     */     } 
/* 253 */     CanonicalizationMethod canonicalMethod = signatureFactory.newCanonicalizationMethod(canonicalAlgo, spec);
/*     */     
/* 255 */     if (!fpContext.getDisableIncPrefix()) {
/* 256 */       List contentList = setInclusiveNamespaces((ExcC14NParameterSpec)spec);
/* 257 */       ((CanonicalizationMethod)canonicalMethod).setContent(contentList);
/*     */     } 
/*     */     
/* 260 */     SignatureMethod signatureMethod = signatureFactory.newSignatureMethod(keyAlgo, null);
/*     */     
/* 262 */     SignedInfo signedInfo = signatureFactory.newSignedInfo(canonicalMethod, signatureMethod, generateReferenceList(cloneList, signatureFactory, fpContext, false), null);
/*     */ 
/*     */     
/* 265 */     return signedInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLSignatureFactory getSignatureFactory() {
/*     */     try {
/* 274 */       return (XMLSignatureFactory)JAXBSignatureFactory.newInstance();
/* 275 */     } catch (Exception ex) {
/* 276 */       throw new RuntimeException(ex);
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
/*     */   private List generateReferenceList(List targetList, XMLSignatureFactory signatureFactory, JAXBFilterProcessingContext fpContext, boolean verify) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, XWSSecurityException {
/* 294 */     ListIterator<SignatureTarget> iterator = targetList.listIterator();
/* 295 */     ArrayList<Reference> references = new ArrayList();
/* 296 */     if (logger.isLoggable(Level.FINEST)) {
/* 297 */       logger.log(Level.FINEST, LogStringsMessages.WSS_1751_NUMBER_TARGETS_SIGNATURE(Integer.valueOf(targetList.size())));
/*     */     }
/*     */     
/* 300 */     while (iterator.hasNext()) {
/* 301 */       SignatureTarget signatureTarget = iterator.next();
/* 302 */       String digestAlgo = signatureTarget.getDigestAlgorithm();
/* 303 */       if (logger.isLoggable(Level.FINEST)) {
/* 304 */         logger.log(Level.FINEST, LogStringsMessages.WSS_1752_SIGNATURE_TARGET_VALUE(signatureTarget.getValue()));
/* 305 */         logger.log(Level.FINEST, LogStringsMessages.WSS_1753_TARGET_DIGEST_ALGORITHM(digestAlgo));
/*     */       } 
/* 307 */       DigestMethod digestMethod = null;
/*     */       try {
/* 309 */         digestMethod = signatureFactory.newDigestMethod(digestAlgo, null);
/* 310 */       } catch (Exception ex) {
/* 311 */         logger.log(Level.SEVERE, "WSS1301.invalid.digest.algo", digestAlgo);
/* 312 */         throw new XWSSecurityException(ex.getMessage());
/*     */       } 
/*     */       
/* 315 */       boolean exclTransformToBeAdded = false;
/* 316 */       ArrayList transforms = signatureTarget.getTransforms();
/* 317 */       ListIterator<SignatureTarget.Transform> transformIterator = transforms.listIterator();
/* 318 */       ArrayList<Transform> transformList = new ArrayList(2);
/* 319 */       while (transformIterator.hasNext()) {
/* 320 */         SignatureTarget.Transform transformInfo = transformIterator.next();
/* 321 */         String transformAlgo = transformInfo.getTransform();
/* 322 */         Transform transform = null;
/*     */         
/* 324 */         if (logger.isLoggable(Level.FINEST))
/* 325 */           logger.log(Level.FINEST, "Transform Algorithm is " + transformAlgo); 
/* 326 */         if ("http://www.w3.org/TR/1999/REC-xpath-19991116".equals(transformAlgo))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 334 */           throw new UnsupportedOperationException("XPATH not supported"); } 
/* 335 */         if ("http://www.w3.org/2002/06/xmldsig-filter2".equals(transformAlgo))
/*     */         {
/*     */           
/* 338 */           throw new UnsupportedOperationException("XPATH not supported"); } 
/* 339 */         if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform".equals(transformAlgo)) {
/* 340 */           Parameter transformParams = (Parameter)transformInfo.getAlgorithmParameters();
/* 341 */           String algo = null;
/* 342 */           if (transformParams.getParamName().equals("CanonicalizationMethod")) {
/* 343 */             algo = transformParams.getParamValue();
/*     */           }
/* 345 */           if (algo == null) {
/* 346 */             throw new XWSSecurityException("STR Transform must have acanonicalization method specified");
/*     */           }
/*     */           
/* 349 */           if (logger.isLoggable(Level.FINEST)) {
/* 350 */             logger.log(Level.FINEST, "CanonicalizationMethod is " + algo);
/*     */           }
/*     */           
/* 353 */           C14NMethodParameterSpec spec = null;
/*     */           try {
/* 355 */             TransformationParametersType tp = (new ObjectFactory()).createTransformationParametersType();
/*     */             
/* 357 */             CanonicalizationMethod cm = new CanonicalizationMethod();
/*     */             
/* 359 */             cm.setAlgorithm(algo);
/* 360 */             tp.getAny().add(cm);
/* 361 */             JAXBElement<TransformationParametersType> tpElement = (new ObjectFactory()).createTransformationParameters(tp);
/*     */             
/* 363 */             JAXBStructure jAXBStructure = new JAXBStructure(tpElement);
/* 364 */             transform = signatureFactory.newTransform(transformAlgo, (XMLStructure)jAXBStructure);
/* 365 */             if ("uri".equals(signatureTarget.getType())) {
/* 366 */               String str = signatureTarget.getValue();
/* 367 */               ((Transform)transform).setReferenceId(str);
/*     */             }
/*     */           
/* 370 */           } catch (Exception ex) {
/* 371 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1767_ERROR_CREATE_TRANSFORM_OBJECT(), ex);
/* 372 */             throw new XWSSecurityException(ex.getMessage());
/*     */           } 
/* 374 */         } else if ("http://www.w3.org/2001/10/xml-exc-c14n#".equalsIgnoreCase(transformAlgo)) {
/*     */ 
/*     */           
/* 377 */           exclTransformToBeAdded = true;
/*     */         } else {
/* 379 */           transform = signatureFactory.newTransform(transformAlgo, (TransformParameterSpec)null);
/*     */         } 
/*     */ 
/*     */         
/* 383 */         if (!"http://www.w3.org/2001/10/xml-exc-c14n#".equalsIgnoreCase(transformAlgo))
/*     */         {
/* 385 */           transformList.add(transform);
/*     */         }
/*     */       } 
/* 388 */       String targetURI = "";
/* 389 */       String signatureType = signatureTarget.getType();
/* 390 */       if (signatureTarget.isITNever()) {
/* 391 */         String uri = signatureTarget.getValue();
/* 392 */         uri = uri.startsWith("#") ? uri.substring(1) : uri;
/* 393 */         SSEData data = (SSEData)fpContext.getElementCache().get(uri);
/* 394 */         SecurityHeaderElement se = (SecurityHeaderElement)data.getSecurityElement();
/* 395 */         fpContext.getSecurityHeader().add(se);
/*     */       } 
/* 397 */       SecuredMessage secMessage = fpContext.getSecuredMessage();
/*     */ 
/*     */ 
/*     */       
/* 401 */       if (signatureType.equals("qname")) {
/*     */         
/* 403 */         String expr = null;
/* 404 */         List<SignedMessagePart> targets = new ArrayList<SignedMessagePart>();
/*     */         
/* 406 */         String targetValue = signatureTarget.getValue();
/* 407 */         boolean optimized = false;
/* 408 */         if (fpContext.getConfigType() == 1 || fpContext.getConfigType() == 2)
/*     */         {
/* 410 */           optimized = true;
/*     */         }
/*     */         
/* 413 */         if (targetValue.equals("{http://schemas.xmlsoap.org/soap/envelope/}Body")) {
/* 414 */           Object body = secMessage.getBody();
/* 415 */           if (body instanceof SignedMessagePart) {
/* 416 */             targets.add((SignedMessagePart)body);
/* 417 */           } else if (body instanceof SecurityElement) {
/* 418 */             SignedMessagePart smp = new SignedMessagePart((SecurityElement)body);
/* 419 */             targets.add(smp);
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 424 */             boolean contentOnly = signatureTarget.getContentOnly();
/* 425 */             SOAPBody soapBody = (SOAPBody)body;
/* 426 */             if (!contentOnly) {
/* 427 */               if (soapBody.getId() == null || "".equals(soapBody.getId()))
/* 428 */                 soapBody.setId(fpContext.generateID()); 
/* 429 */               SignedMessagePart smp = new SignedMessagePart(soapBody, contentOnly);
/* 430 */               secMessage.replaceBody(smp);
/* 431 */               targets.add(smp);
/*     */             } else {
/* 433 */               String id = null;
/* 434 */               if (soapBody.getBodyContentId() == null || "".equals(soapBody.getBodyContentId())) {
/* 435 */                 id = fpContext.generateID();
/* 436 */                 soapBody.setBodyContentId(id);
/*     */               } 
/*     */               
/* 439 */               SignedMessagePart smp = new SignedMessagePart(soapBody, contentOnly);
/* 440 */               SOAPBody newBody = new SOAPBody(smp, fpContext.getSOAPVersion());
/* 441 */               newBody.setId(soapBody.getId());
/* 442 */               secMessage.replaceBody(newBody);
/* 443 */               targets.add(smp);
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 450 */           QName name = null;
/* 451 */           if (targetValue.endsWith("}")) {
/* 452 */             String nsURI = targetValue.substring(1, targetValue.length() - 1);
/* 453 */             name = new QName(nsURI, "");
/*     */           } else {
/* 455 */             name = QName.valueOf(targetValue);
/*     */           } 
/*     */           
/* 458 */           Iterator<SecurityHeaderElement> headers = null;
/* 459 */           if (name.getNamespaceURI().equals("http://schemas.xmlsoap.org/ws/2004/08/addressing") || name.getNamespaceURI().equals("http://www.w3.org/2005/08/addressing")) {
/*     */             
/* 461 */             if (!"".equals(name.getLocalPart())) {
/* 462 */               headers = secMessage.getHeaders(name.getLocalPart(), null);
/*     */             } else {
/* 464 */               headers = secMessage.getHeaders("http://schemas.xmlsoap.org/ws/2004/08/addressing");
/* 465 */               if (!headers.hasNext()) {
/* 466 */                 headers = secMessage.getHeaders("http://www.w3.org/2005/08/addressing");
/*     */               }
/*     */             } 
/* 469 */           } else if (!"".equals(name.getLocalPart())) {
/* 470 */             headers = secMessage.getHeaders(name.getLocalPart(), name.getNamespaceURI());
/*     */           } else {
/* 472 */             headers = secMessage.getHeaders(name.getNamespaceURI());
/*     */           } 
/*     */           
/* 475 */           while (headers.hasNext()) {
/* 476 */             Object next = headers.next();
/* 477 */             if (next instanceof SignedMessageHeader) {
/* 478 */               targets.add((SignedMessageHeader)next); continue;
/* 479 */             }  if (next instanceof SecurityHeaderElement) {
/* 480 */               SecurityHeaderElement she = (SecurityHeaderElement)next;
/* 481 */               SignedMessageHeader smh = new SignedMessageHeader(she);
/* 482 */               secMessage.replaceHeader(she, smh);
/* 483 */               targets.add(smh); continue;
/* 484 */             }  if (next instanceof Header) {
/* 485 */               Header header = (Header)next;
/* 486 */               SignedMessageHeader smh = toSignedMessageHeader(header, fpContext);
/* 487 */               secMessage.replaceHeader(header, smh);
/* 488 */               targets.add(smh);
/*     */             } 
/*     */           } 
/*     */           
/* 492 */           SecurityHeader sh = fpContext.getSecurityHeader();
/* 493 */           headers = sh.getHeaders(name.getLocalPart(), name.getNamespaceURI());
/* 494 */           while (headers.hasNext()) {
/* 495 */             SecurityHeaderElement she = headers.next();
/* 496 */             if (she instanceof SignedMessageHeader) {
/* 497 */               targets.add((SignedMessageHeader)she); continue;
/*     */             } 
/* 499 */             if (she.getId() == null) {
/* 500 */               she.setId(fpContext.generateID());
/*     */             }
/* 502 */             SignedMessageHeader smh = new SignedMessageHeader(she);
/* 503 */             targets.add(smh);
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 508 */         if (targets.size() <= 0) {
/* 509 */           if (signatureTarget.getEnforce()) {
/* 510 */             throw new XWSSecurityException("SignatureTarget with URI " + signatureTarget.getValue() + " is not in the message");
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/* 516 */         if (logger.isLoggable(Level.FINEST)) {
/* 517 */           logger.log(Level.FINEST, "Number of nodes " + targets.size());
/* 518 */           logger.log(Level.FINEST, "+++++++++++++++END+++++++++++++++");
/*     */         } 
/*     */         
/* 521 */         HashMap<String, SignedMessagePart> elementCache = null;
/* 522 */         if (fpContext != null) {
/* 523 */           elementCache = fpContext.getElementCache();
/*     */         }
/*     */         
/* 526 */         for (int i = 0; i < targets.size(); i++) {
/* 527 */           SignedMessagePart targetRef = targets.get(i);
/* 528 */           ArrayList<Transform> clonedTransformList = (ArrayList)transformList.clone();
/* 529 */           if (exclTransformToBeAdded) {
/*     */             
/* 531 */             String transformAlgo = "http://www.w3.org/2001/10/xml-exc-c14n#";
/* 532 */             ((NamespaceContextEx)fpContext.getNamespaceContext()).addExc14NS();
/* 533 */             ExcC14NParameterSpec spec = null;
/* 534 */             if (!fpContext.getDisableIncPrefix()) {
/* 535 */               ArrayList<String> list = new ArrayList();
/* 536 */               list.add("S");
/* 537 */               spec = new ExcC14NParameterSpec(list);
/*     */             } 
/* 539 */             Transform transform = signatureFactory.newTransform(transformAlgo, spec);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 546 */             clonedTransformList.add(transform);
/*     */           } 
/*     */           
/* 549 */           String id = targetRef.getId();
/* 550 */           if (id == null || id.equals("")) {
/* 551 */             id = fpContext.generateID();
/* 552 */             if (!verify) {
/* 553 */               targetRef.setId(id);
/*     */             } else {
/*     */               
/* 556 */               elementCache.put(id, targetRef);
/*     */             } 
/*     */           } 
/*     */           
/* 560 */           if (logger.isLoggable(Level.FINEST)) {
/* 561 */             logger.log(Level.FINEST, "SignedInfo val id " + id);
/*     */           }
/* 563 */           targetURI = "#" + id;
/*     */           
/* 565 */           Reference reference1 = null;
/* 566 */           reference1 = signatureFactory.newReference(targetURI, digestMethod, clonedTransformList, null, null);
/* 567 */           references.add(reference1);
/*     */         }  continue;
/*     */       } 
/* 570 */       if ("uri".equals(signatureType)) {
/* 571 */         targetURI = signatureTarget.getValue();
/*     */         
/* 573 */         if (targetURI == null) {
/* 574 */           targetURI = "";
/*     */         }
/* 576 */         QName policyName = signatureTarget.getPolicyQName();
/* 577 */         if (policyName != null && policyName == MessageConstants.SCT_NAME) {
/* 578 */           String _uri = targetURI;
/* 579 */           if (targetURI.length() > 0 && targetURI.charAt(0) == '#') {
/* 580 */             _uri = targetURI.substring(1);
/*     */           }
/* 582 */           IssuedTokenContext ictx = fpContext.getIssuedTokenContext(_uri);
/* 583 */           SecurityContextToken sct1 = (SecurityContextToken)ictx.getSecurityToken();
/* 584 */           targetURI = sct1.getWsuId();
/*     */         } 
/* 586 */         if ("cid:*".equals(targetURI)) {
/* 587 */           AttachmentSet as = secMessage.getAttachments();
/* 588 */           if (as != null && as.isEmpty()) {
/* 589 */             logger.log(Level.WARNING, LogStringsMessages.WSS_1766_NO_ATTACHMENT_PARTS_TOBE_SECURED());
/*     */             continue;
/*     */           } 
/* 592 */           for (Attachment attachment : as) {
/* 593 */             String cid = "cid:" + attachment.getContentId();
/*     */             
/* 595 */             Reference reference1 = signatureFactory.newReference(cid, digestMethod, transformList, null, null);
/* 596 */             references.add(reference1);
/*     */           } 
/*     */           continue;
/*     */         } 
/* 600 */         if (exclTransformToBeAdded) {
/* 601 */           String _uri = targetURI;
/* 602 */           if (targetURI.length() > 0 && targetURI.charAt(0) == '#') {
/* 603 */             _uri = targetURI.substring(1);
/*     */           }
/* 605 */           Object reqdPart = getPartFromId(fpContext, _uri);
/* 606 */           if (reqdPart != null) {
/* 607 */             String transformAlgo = "http://www.w3.org/2001/10/xml-exc-c14n#";
/* 608 */             ExcC14NParameterSpec spec = null;
/* 609 */             if (!fpContext.getDisableIncPrefix()) {
/* 610 */               ArrayList<String> list = new ArrayList();
/* 611 */               list.add("wsu"); list.add("wsse"); list.add("S");
/* 612 */               spec = new ExcC14NParameterSpec(list);
/*     */             } 
/* 614 */             Transform transform = signatureFactory.newTransform(transformAlgo, spec);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 621 */             transformList.add(transform);
/*     */           } 
/*     */         } 
/* 624 */         if (targetURI.equals("ALL_MESSAGE_HEADERS")) {
/*     */ 
/*     */           
/* 627 */           ArrayList headers = secMessage.getHeaders();
/* 628 */           for (int i = 0; i < headers.size(); i++) {
/* 629 */             Object header = headers.get(i);
/* 630 */             String tmpUri = null;
/* 631 */             if (header instanceof SignedMessageHeader) {
/* 632 */               tmpUri = "#" + ((SignedMessageHeader)header).getId();
/*     */             }
/* 634 */             else if (header instanceof SecurityHeaderElement) {
/* 635 */               SecurityHeaderElement she = (SecurityHeaderElement)header;
/* 636 */               SignedMessageHeader smh = new SignedMessageHeader(she);
/* 637 */               String id = smh.getId();
/* 638 */               if (id == null) {
/* 639 */                 id = fpContext.generateID();
/* 640 */                 smh.setId(id);
/*     */               } 
/* 642 */               secMessage.replaceHeader(she, smh);
/* 643 */               tmpUri = "#" + id;
/* 644 */             } else if (header instanceof Header) {
/* 645 */               Header jwHeader = (Header)header;
/* 646 */               tmpUri = fpContext.generateID();
/* 647 */               SignedMessageHeader smh = createSignedMessageHeader(jwHeader, tmpUri, fpContext);
/* 648 */               secMessage.replaceHeader(jwHeader, smh);
/* 649 */               if (!tmpUri.startsWith("#")) {
/* 650 */                 tmpUri = "#" + tmpUri;
/*     */               }
/*     */             } 
/* 653 */             if (tmpUri != null) {
/* 654 */               Reference reference1 = signatureFactory.newReference(tmpUri, digestMethod, transformList, null, null);
/* 655 */               references.add(reference1);
/*     */             } 
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/*     */       } 
/*     */       
/* 663 */       Reference reference = null;
/* 664 */       reference = signatureFactory.newReference(targetURI, digestMethod, transformList, null, null);
/* 665 */       references.add(reference);
/*     */     } 
/*     */     
/* 668 */     if (references.isEmpty()) {
/* 669 */       logger.log(Level.WARNING, LogStringsMessages.WSS_1768_NO_SIGNEDPARTS());
/*     */     }
/* 671 */     return references;
/*     */   }
/*     */   
/*     */   private List setInclusiveNamespaces(ExcC14NParameterSpec spec) {
/* 675 */     if (spec == null) {
/* 676 */       return null;
/*     */     }
/* 678 */     ObjectFactory objFac = new ObjectFactory();
/* 679 */     InclusiveNamespacesType incList = objFac.createInclusiveNamespaces();
/* 680 */     List<String> prefixList = spec.getPrefixList();
/* 681 */     for (int j = 0; j < prefixList.size(); j++) {
/* 682 */       String prefix = prefixList.get(j);
/* 683 */       incList.addToPrefixList(prefix);
/*     */     } 
/* 685 */     JAXBElement<InclusiveNamespacesType> je = objFac.createInclusiveNamespaces(incList);
/* 686 */     List<JAXBElement<InclusiveNamespacesType>> contentList = new ArrayList();
/* 687 */     contentList.add(je);
/* 688 */     return contentList;
/*     */   }
/*     */   
/*     */   private SignedMessageHeader toSignedMessageHeader(Header header, JAXBFilterProcessingContext context) {
/* 692 */     return createSignedMessageHeader(header, context.generateID(), context);
/*     */   }
/*     */   
/*     */   private SignedMessageHeader createSignedMessageHeader(Header header, String string, JAXBFilterProcessingContext context) {
/* 696 */     return new SignedMessageHeader(header, string, context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object getPartFromId(JAXBFilterProcessingContext fpContext, String id) throws XWSSecurityException {
/* 707 */     SecuredMessage secMessage = fpContext.getSecuredMessage();
/* 708 */     Object reqdHeader = secMessage.getHeader(id);
/* 709 */     if (reqdHeader != null) {
/* 710 */       return reqdHeader;
/*     */     }
/* 712 */     SecurityHeader secHeader = fpContext.getSecurityHeader();
/* 713 */     SecurityHeaderElement she = secHeader.getChildElement(id);
/* 714 */     if (she != null)
/* 715 */       return she; 
/* 716 */     Object body = secMessage.getBody();
/* 717 */     if (body instanceof SOAPBody) {
/* 718 */       SOAPBody soapBody = (SOAPBody)body;
/* 719 */       if (id.equals(soapBody.getId()))
/* 720 */         return soapBody; 
/* 721 */     } else if (body instanceof SecurityElement) {
/* 722 */       SecurityElement se = (SecurityElement)body;
/* 723 */       if (id.equals(se.getId()))
/* 724 */         return se; 
/*     */     } 
/* 726 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\dsig\SignatureElementFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */