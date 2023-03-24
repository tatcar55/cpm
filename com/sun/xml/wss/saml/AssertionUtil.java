/*     */ package com.sun.xml.wss.saml;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
/*     */ import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
/*     */ import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
/*     */ import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.core.KeyInfoHeaderBlock;
/*     */ import com.sun.xml.wss.core.ReferenceElement;
/*     */ import com.sun.xml.wss.core.SecurityTokenReference;
/*     */ import com.sun.xml.wss.core.reference.KeyIdentifier;
/*     */ import com.sun.xml.wss.core.reference.X509IssuerSerial;
/*     */ import com.sun.xml.wss.impl.callback.SignatureVerificationKeyCallback;
/*     */ import com.sun.xml.wss.impl.misc.DefaultSecurityEnvironmentImpl;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb10.Assertion;
/*     */ import com.sun.xml.wss.saml.assertion.saml11.jaxb20.Assertion;
/*     */ import com.sun.xml.wss.saml.assertion.saml20.jaxb20.Assertion;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.AssertionImpl;
/*     */ import com.sun.xml.wss.saml.internal.saml11.jaxb20.AssertionType;
/*     */ import com.sun.xml.wss.saml.internal.saml20.jaxb20.AssertionType;
/*     */ import com.sun.xml.wss.saml.util.SAML20JAXBUtil;
/*     */ import com.sun.xml.wss.saml.util.SAMLJAXBUtil;
/*     */ import java.math.BigInteger;
/*     */ import java.security.Key;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.security.auth.callback.Callback;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import org.w3c.dom.DOMException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AssertionUtil
/*     */ {
/*  97 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AssertionUtil(CallbackHandler callbackHandler) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Key getSubjectConfirmationKey(Element assertion, CallbackHandler callbackHandler) throws XWSSecurityException {
/* 117 */     NodeList nl1 = assertion.getElementsByTagName("SubjectConfirmation");
/*     */     
/* 119 */     if (nl1.getLength() == 0) {
/* 120 */       throw new XWSSecurityException("SAML Assertion does not contain a key");
/*     */     }
/*     */     
/* 123 */     NodeList nl = ((Element)nl1.item(0)).getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "KeyInfo");
/*     */     
/* 125 */     if (nl.getLength() == 0) {
/* 126 */       throw new XWSSecurityException("SAML Assertion does not contain a key");
/*     */     }
/*     */     
/*     */     try {
/* 130 */       Element keyInfoElem = (Element)nl.item(0);
/*     */       
/* 132 */       KeyInfo keyInfo = new KeyInfo(keyInfoElem, null);
/*     */       
/* 134 */       if (keyInfo.containsKeyValue())
/* 135 */         return keyInfo.itemKeyValue(0).getPublicKey(); 
/* 136 */       if (keyInfo.containsX509Data())
/* 137 */         return resolveX509Data(keyInfo.itemX509Data(0), callbackHandler); 
/* 138 */       if (keyInfo.length("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey") > 0) {
/* 139 */         return resolveEncryptedKey(keyInfo.itemEncryptedKey(0), callbackHandler);
/*     */       }
/*     */       
/* 142 */       throw new XWSSecurityException("Unsupported Key Information");
/*     */     }
/* 144 */     catch (Exception e) {
/* 145 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Key resolveX509Data(X509Data x509Data, CallbackHandler callbackHandler) throws XWSSecurityException {
/* 150 */     x509Data.getElement().normalize();
/* 151 */     X509Certificate cert = null;
/*     */     
/*     */     try {
/* 154 */       if (x509Data.containsCertificate()) {
/* 155 */         cert = x509Data.itemCertificate(0).getX509Certificate();
/* 156 */       } else if (x509Data.containsSKI()) {
/* 157 */         byte[] keyIdentifier = x509Data.itemSKI(0).getSKIBytes();
/* 158 */         SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest x509SubjectKeyIdentifierBasedRequest = new SignatureVerificationKeyCallback.X509SubjectKeyIdentifierBasedRequest(keyIdentifier);
/*     */         
/* 160 */         SignatureVerificationKeyCallback verifyKeyCallback = new SignatureVerificationKeyCallback((SignatureVerificationKeyCallback.Request)x509SubjectKeyIdentifierBasedRequest);
/*     */         
/* 162 */         Callback[] callbacks = { (Callback)verifyKeyCallback };
/*     */         try {
/* 164 */           callbackHandler.handle(callbacks);
/* 165 */         } catch (Exception e) {
/* 166 */           throw new XWSSecurityException(e);
/*     */         } 
/*     */         
/* 169 */         cert = x509SubjectKeyIdentifierBasedRequest.getX509Certificate();
/*     */         
/* 171 */         if (cert == null) {
/* 172 */           throw new XWSSecurityException("No Matching public key for " + Base64.encode(keyIdentifier) + " subject key identifier found");
/*     */         }
/* 174 */       } else if (x509Data.containsIssuerSerial()) {
/*     */         
/* 176 */         String issuerName = x509Data.itemIssuerSerial(0).getIssuerName();
/* 177 */         BigInteger serialNumber = x509Data.itemIssuerSerial(0).getSerialNumber();
/*     */         
/* 179 */         SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest x509IssuerSerialBasedRequest = new SignatureVerificationKeyCallback.X509IssuerSerialBasedRequest(issuerName, serialNumber);
/*     */ 
/*     */ 
/*     */         
/* 183 */         SignatureVerificationKeyCallback verifyKeyCallback = new SignatureVerificationKeyCallback((SignatureVerificationKeyCallback.Request)x509IssuerSerialBasedRequest);
/*     */ 
/*     */         
/* 186 */         Callback[] callbacks = { (Callback)verifyKeyCallback };
/*     */         
/*     */         try {
/* 189 */           callbackHandler.handle(callbacks);
/* 190 */         } catch (Exception e) {
/* 191 */           throw new XWSSecurityException(e);
/*     */         } 
/*     */         
/* 194 */         cert = x509IssuerSerialBasedRequest.getX509Certificate();
/*     */         
/* 196 */         if (cert == null) {
/* 197 */           throw new XWSSecurityException("No Matching public key for serial number " + serialNumber + " and issuer name " + issuerName + " found");
/*     */         }
/*     */       } else {
/*     */         
/* 201 */         throw new XWSSecurityException("Unsupported child element of X509Data encountered");
/*     */       } 
/*     */ 
/*     */       
/* 205 */       return cert.getPublicKey();
/* 206 */     } catch (Exception e) {
/* 207 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Key resolveEncryptedKey(EncryptedKey encryptedKey, CallbackHandler callbackHandler) throws XWSSecurityException {
/* 214 */     KeyInfo keyInfo = encryptedKey.getKeyInfo();
/* 215 */     KeyInfoHeaderBlock keyInfoHb = new KeyInfoHeaderBlock(keyInfo);
/* 216 */     Key kek = null;
/*     */     try {
/* 218 */       if (keyInfoHb.containsSecurityTokenReference()) {
/* 219 */         kek = processSecurityTokenReference(keyInfoHb, callbackHandler);
/* 220 */       } else if (keyInfoHb.containsKeyValue()) {
/*     */         
/* 222 */         DefaultSecurityEnvironmentImpl defaultSecurityEnvironmentImpl = new DefaultSecurityEnvironmentImpl(callbackHandler);
/* 223 */         KeyValue keyValue = keyInfoHb.getKeyValue(0);
/* 224 */         keyValue.getElement().normalize();
/* 225 */         kek = defaultSecurityEnvironmentImpl.getPrivateKey(null, keyValue.getPublicKey(), false);
/*     */       }
/* 227 */       else if (keyInfoHb.containsX509Data()) {
/* 228 */         kek = processX509Data(keyInfoHb, callbackHandler);
/*     */       } else {
/*     */         
/* 231 */         throw new XWSSecurityException("Unsupported Key Information");
/*     */       } 
/*     */       
/* 234 */       String algorithmURI = encryptedKey.getEncryptionMethod().getAlgorithm();
/* 235 */       XMLCipher xmlCipher = XMLCipher.getInstance();
/* 236 */       xmlCipher.init(4, null);
/* 237 */       xmlCipher.setKEK(kek);
/*     */       
/* 239 */       return xmlCipher.decryptKey(encryptedKey, algorithmURI);
/* 240 */     } catch (Exception e) {
/* 241 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Key processSecurityTokenReference(KeyInfoHeaderBlock keyInfo, CallbackHandler callbackHandler) throws XWSSecurityException {
/* 247 */     Key returnKey = null;
/*     */     
/* 249 */     DefaultSecurityEnvironmentImpl defaultSecurityEnvironmentImpl = new DefaultSecurityEnvironmentImpl(callbackHandler);
/*     */     
/* 251 */     SecurityTokenReference str = keyInfo.getSecurityTokenReference(0);
/* 252 */     ReferenceElement refElement = str.getReference();
/*     */     
/* 254 */     if (refElement instanceof KeyIdentifier) {
/* 255 */       KeyIdentifier keyId = (KeyIdentifier)refElement;
/* 256 */       byte[] decodedValue = keyId.getDecodedReferenceValue().getBytes();
/* 257 */       if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier".equals(keyId.getValueType()) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3SubjectKeyIdentifier".equals(keyId.getValueType())) {
/*     */         
/* 259 */         returnKey = defaultSecurityEnvironmentImpl.getPrivateKey(null, decodedValue);
/* 260 */       } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#ThumbprintSHA1".equals(keyId.getValueType())) {
/* 261 */         throw new XWSSecurityException("Unsupported KeyValueType :" + keyId.getValueType());
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 268 */     else if (refElement instanceof X509IssuerSerial) {
/* 269 */       BigInteger serialNumber = ((X509IssuerSerial)refElement).getSerialNumber();
/* 270 */       String issuerName = ((X509IssuerSerial)refElement).getIssuerName();
/*     */       
/* 272 */       returnKey = defaultSecurityEnvironmentImpl.getPrivateKey(null, serialNumber, issuerName);
/*     */     } else {
/* 274 */       log.log(Level.SEVERE, "WSS0338.unsupported.reference.mechanism");
/*     */       
/* 276 */       throw new XWSSecurityException("Key reference mechanism not supported");
/*     */     } 
/*     */     
/* 279 */     return returnKey;
/*     */   }
/*     */   
/*     */   private static Key processX509Data(KeyInfoHeaderBlock keyInfo, CallbackHandler callbackHandler) throws XWSSecurityException {
/* 283 */     DefaultSecurityEnvironmentImpl defaultSecurityEnvironmentImpl = new DefaultSecurityEnvironmentImpl(callbackHandler);
/* 284 */     X509Data x509Data = keyInfo.getX509Data(0);
/* 285 */     X509Certificate cert = null;
/*     */     try {
/* 287 */       if (x509Data.containsCertificate())
/* 288 */       { cert = x509Data.itemCertificate(0).getX509Certificate(); }
/* 289 */       else { if (x509Data.containsSKI())
/* 290 */           return defaultSecurityEnvironmentImpl.getPrivateKey(null, x509Data.itemSKI(0).getSKIBytes()); 
/* 291 */         if (x509Data.containsIssuerSerial()) {
/* 292 */           return defaultSecurityEnvironmentImpl.getPrivateKey(null, x509Data.itemIssuerSerial(0).getSerialNumber(), x509Data.itemIssuerSerial(0).getIssuerName());
/*     */         }
/*     */ 
/*     */         
/* 296 */         log.log(Level.SEVERE, "WSS0339.unsupported.keyinfo");
/* 297 */         throw new XWSSecurityException("Unsupported child element of X509Data encountered"); }
/*     */ 
/*     */       
/* 300 */       return defaultSecurityEnvironmentImpl.getPrivateKey(null, cert);
/*     */     }
/* 302 */     catch (Exception e) {
/* 303 */       log.log(Level.SEVERE, "WSS0602.illegal.x509.data", e.getMessage());
/* 304 */       throw new XWSSecurityException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Assertion fromElement(Element element) throws SAMLException {
/*     */     try {
/* 311 */       if (System.getProperty("com.sun.xml.wss.saml.binding.jaxb") != null) {
/* 312 */         JAXBContext jAXBContext = SAMLJAXBUtil.getJAXBContext();
/*     */         
/* 314 */         Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
/* 315 */         return (Assertion)new Assertion((AssertionImpl)unmarshaller.unmarshal(element));
/*     */       } 
/*     */       
/* 318 */       if (element.getAttributeNode("ID") != null) {
/* 319 */         JAXBContext jAXBContext = SAML20JAXBUtil.getJAXBContext();
/*     */         
/* 321 */         Unmarshaller unmarshaller = jAXBContext.createUnmarshaller();
/* 322 */         Object object = unmarshaller.unmarshal(element);
/* 323 */         return (Assertion)new Assertion(((JAXBElement<AssertionType>)object).getValue());
/*     */       } 
/* 325 */       JAXBContext jc = SAMLJAXBUtil.getJAXBContext();
/*     */       
/* 327 */       Unmarshaller u = jc.createUnmarshaller();
/* 328 */       Object el = u.unmarshal(element);
/* 329 */       return (Assertion)new Assertion(((JAXBElement<AssertionType>)el).getValue());
/*     */     
/*     */     }
/* 332 */     catch (Exception ex) {
/*     */       
/* 334 */       throw new SAMLException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getConfirmationMethod(Element assertion) {
/* 340 */     NodeList nl = null;
/*     */     
/* 342 */     if (assertion.getAttributeNode("ID") != null) {
/* 343 */       nl = assertion.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmation");
/*     */     } else {
/* 345 */       nl = assertion.getElementsByTagNameNS("urn:oasis:names:tc:SAML:1.0:assertion", "ConfirmationMethod");
/*     */     } 
/* 347 */     if (nl.getLength() == 0) {
/* 348 */       return null;
/*     */     }
/* 350 */     Element confirmationMethod = (Element)nl.item(0);
/*     */     try {
/* 352 */       if (assertion.getAttributeNode("ID") != null) {
/* 353 */         return confirmationMethod.getAttribute("Method");
/*     */       }
/* 355 */       return confirmationMethod.getTextContent();
/*     */     }
/* 357 */     catch (DOMException ex) {
/*     */       
/* 359 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static NodeList skipAdviceValidation(Element assertion, NodeList nodeList) {
/* 364 */     boolean keyPresent = false;
/* 365 */     int returnNodeIndex = 0;
/* 366 */     for (int i = 0; i < nodeList.getLength(); ) {
/* 367 */       if (nodeList.item(i).getParentNode().getParentNode().getParentNode().getParentNode().getLocalName().equals("Advice")) {
/*     */         i++; continue;
/*     */       } 
/* 370 */       keyPresent = true;
/* 371 */       returnNodeIndex = i;
/*     */     } 
/*     */ 
/*     */     
/* 375 */     if (keyPresent) {
/* 376 */       return ((Element)nodeList.item(returnNodeIndex)).getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "KeyInfo");
/*     */     }
/* 378 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Element getSubjectConfirmationKeyInfo(Element assertion) throws XWSSecurityException {
/*     */     try {
/* 386 */       NodeList nl = null;
/* 387 */       NodeList nl1 = null;
/*     */       
/* 389 */       if (assertion.getAttributeNode("ID") != null) {
/* 390 */         nl1 = assertion.getElementsByTagNameNS("urn:oasis:names:tc:SAML:2.0:assertion", "SubjectConfirmationData");
/*     */       } else {
/* 392 */         nl1 = assertion.getElementsByTagNameNS("urn:oasis:names:tc:SAML:1.0:assertion", "SubjectConfirmation");
/*     */       } 
/*     */       
/* 395 */       if (nl1.getLength() == 0) {
/* 396 */         throw new XWSSecurityException("SAML Assertion does not contain a key");
/*     */       }
/*     */       
/* 399 */       nl = skipAdviceValidation(assertion, nl1);
/*     */       
/* 401 */       if (nl == null || nl.getLength() == 0) {
/* 402 */         throw new XWSSecurityException("SAML Assertion does not contain a key");
/*     */       }
/*     */ 
/*     */       
/* 406 */       if (nl.getLength() != 0) {
/* 407 */         Element keyInfo = (Element)nl.item(0);
/* 408 */         if (keyInfo != null) {
/* 409 */           return keyInfo;
/*     */         }
/*     */       } 
/* 412 */     } catch (Exception e) {
/* 413 */       throw new XWSSecurityException(e);
/*     */     } 
/* 415 */     throw new XWSSecurityException("Unable to locate KeyInfo inside SubjectConfirmation of SAML Assertion");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\AssertionUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */