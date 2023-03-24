/*     */ package com.sun.xml.ws.security.trust.util;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.encryption.EncryptedKey;
/*     */ import com.sun.org.apache.xml.internal.security.encryption.XMLCipher;
/*     */ import com.sun.org.apache.xml.internal.security.keys.KeyInfo;
/*     */ import com.sun.org.apache.xml.internal.security.keys.content.X509Data;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.policy.impl.bindings.AppliesTo;
/*     */ import com.sun.xml.ws.policy.impl.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.secconv.WSSCElementFactory;
/*     */ import com.sun.xml.ws.security.secconv.WSSCElementFactory13;
/*     */ import com.sun.xml.ws.security.secconv.WSSecureConversationException;
/*     */ import com.sun.xml.ws.security.trust.WSTrustElementFactory;
/*     */ import com.sun.xml.ws.security.trust.WSTrustSOAPFaultException;
/*     */ import com.sun.xml.ws.security.trust.WSTrustVersion;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSRequest;
/*     */ import com.sun.xml.ws.security.trust.elements.BaseSTSResponse;
/*     */ import com.sun.xml.ws.security.trust.elements.Lifetime;
/*     */ import com.sun.xml.ws.security.trust.elements.str.KeyIdentifier;
/*     */ import com.sun.xml.ws.security.trust.elements.str.Reference;
/*     */ import com.sun.xml.ws.security.trust.elements.str.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.AttributedURI;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.EndpointReference;
/*     */ import com.sun.xml.ws.security.trust.impl.bindings.ObjectFactory;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.str.KeyIdentifierImpl;
/*     */ import com.sun.xml.ws.security.trust.impl.elements.str.SecurityTokenReferenceImpl;
/*     */ import com.sun.xml.ws.security.wsu10.AttributedDateTime;
/*     */ import com.sun.xml.wss.WSITXMLFactory;
/*     */ import com.sun.xml.wss.core.reference.X509SubjectKeyIdentifier;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.saml.Assertion;
/*     */ import com.sun.xml.wss.saml.SAMLAssertionFactory;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.StringWriter;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.security.PublicKey;
/*     */ import java.security.SecureRandom;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
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
/*     */ public class WSTrustUtil
/*     */ {
/*     */   public static SOAPFault createSOAP11Fault(WSTrustSOAPFaultException sfex) {
/* 140 */     throw new UnsupportedOperationException("To Do");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SOAPFault createSOAP12Fault(WSTrustSOAPFaultException sfex) {
/* 148 */     throw new UnsupportedOperationException("To Do");
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] generateRandomSecret(int keySize) {
/* 153 */     SecureRandom random = new SecureRandom();
/* 154 */     byte[] secret = new byte[keySize];
/* 155 */     random.nextBytes(secret);
/* 156 */     return secret;
/*     */   }
/*     */   public static SecurityContextToken createSecurityContextToken(WSTrustElementFactory wsscEleFac) throws WSSecureConversationException {
/*     */     URI idURI;
/* 160 */     String identifier = "urn:uuid:" + UUID.randomUUID().toString();
/*     */     
/*     */     try {
/* 163 */       idURI = new URI(identifier);
/* 164 */     } catch (URISyntaxException ex) {
/* 165 */       throw new WSSecureConversationException(ex.getMessage(), ex);
/*     */     } 
/* 167 */     String wsuId = "uuid-" + UUID.randomUUID().toString();
/* 168 */     if (wsscEleFac instanceof WSSCElementFactory)
/* 169 */       return ((WSSCElementFactory)wsscEleFac).createSecurityContextToken(idURI, null, wsuId); 
/* 170 */     if (wsscEleFac instanceof WSSCElementFactory13) {
/* 171 */       return ((WSSCElementFactory13)wsscEleFac).createSecurityContextToken(idURI, null, wsuId);
/*     */     }
/* 173 */     return null;
/*     */   }
/*     */   public static SecurityContextToken createSecurityContextToken(WSSCElementFactory eleFac) throws WSSecureConversationException {
/*     */     URI idURI;
/* 177 */     String identifier = "urn:uuid:" + UUID.randomUUID().toString();
/*     */     
/*     */     try {
/* 180 */       idURI = new URI(identifier);
/* 181 */     } catch (URISyntaxException ex) {
/* 182 */       throw new WSSecureConversationException(ex.getMessage(), ex);
/*     */     } 
/* 184 */     String wsuId = "uuid-" + UUID.randomUUID().toString();
/*     */     
/* 186 */     return eleFac.createSecurityContextToken(idURI, null, wsuId);
/*     */   }
/*     */   
/*     */   public static SecurityContextToken createSecurityContextToken(WSTrustElementFactory wsscEleFac, String identifier) throws WSSecureConversationException {
/*     */     URI idURI;
/*     */     try {
/* 192 */       idURI = new URI(identifier);
/* 193 */     } catch (URISyntaxException ex) {
/* 194 */       throw new WSSecureConversationException(ex.getMessage(), ex);
/*     */     } 
/* 196 */     String wsuId = "uuid-" + UUID.randomUUID().toString();
/* 197 */     String wsuInstance = "uuid-" + UUID.randomUUID().toString();
/*     */     
/* 199 */     if (wsscEleFac instanceof WSSCElementFactory)
/* 200 */       return ((WSSCElementFactory)wsscEleFac).createSecurityContextToken(idURI, wsuInstance, wsuId); 
/* 201 */     if (wsscEleFac instanceof WSSCElementFactory13) {
/* 202 */       return ((WSSCElementFactory13)wsscEleFac).createSecurityContextToken(idURI, wsuInstance, wsuId);
/*     */     }
/* 204 */     return null;
/*     */   }
/*     */   
/*     */   public static SecurityContextToken createSecurityContextToken(WSSCElementFactory eleFac, String identifier) throws WSSecureConversationException {
/*     */     URI idURI;
/*     */     try {
/* 210 */       idURI = new URI(identifier);
/* 211 */     } catch (URISyntaxException ex) {
/* 212 */       throw new WSSecureConversationException(ex.getMessage(), ex);
/*     */     } 
/* 214 */     String wsuId = "uuid-" + UUID.randomUUID().toString();
/* 215 */     String wsuInstance = "uuid-" + UUID.randomUUID().toString();
/*     */     
/* 217 */     return eleFac.createSecurityContextToken(idURI, wsuInstance, wsuId);
/*     */   }
/*     */   
/*     */   public static SecurityTokenReference createSecurityTokenReference(String id, String valueType) {
/* 221 */     WSTrustElementFactory eleFac = WSTrustElementFactory.newInstance();
/* 222 */     KeyIdentifier ref = eleFac.createKeyIdentifier(valueType, null);
/* 223 */     ref.setValue(id);
/* 224 */     return eleFac.createSecurityTokenReference((Reference)ref);
/*     */   }
/*     */   
/*     */   public static AppliesTo createAppliesTo(String appliesTo) {
/* 228 */     AttributedURI uri = new AttributedURI();
/* 229 */     uri.setValue(appliesTo);
/* 230 */     EndpointReference epr = new EndpointReference();
/* 231 */     epr.setAddress(uri);
/* 232 */     AppliesTo applTo = (new ObjectFactory()).createAppliesTo();
/* 233 */     applTo.getAny().add((new ObjectFactory()).createEndpointReference(epr));
/*     */     
/* 235 */     return applTo;
/*     */   }
/*     */   
/*     */   public static List<Object> parseAppliesTo(AppliesTo appliesTo) {
/* 239 */     List<Object> list = appliesTo.getAny();
/* 240 */     EndpointReference epr = null;
/* 241 */     List<Object> result = new ArrayList();
/* 242 */     if (!list.isEmpty()) {
/* 243 */       for (Object obj : list) {
/* 244 */         if (obj instanceof EndpointReference) {
/* 245 */           epr = (EndpointReference)obj;
/* 246 */         } else if (obj instanceof JAXBElement) {
/* 247 */           JAXBElement<EndpointReference> ele = (JAXBElement)obj;
/* 248 */           String local = ele.getName().getLocalPart();
/* 249 */           if (local.equalsIgnoreCase("EndpointReference")) {
/* 250 */             epr = ele.getValue();
/*     */           }
/*     */         } 
/*     */         
/* 254 */         if (epr != null) {
/* 255 */           AttributedURI uri = epr.getAddress();
/* 256 */           if (uri != null) {
/* 257 */             result.add(uri.getValue());
/*     */           }
/* 259 */           for (Object obj2 : epr.getAny()) {
/*     */             try {
/* 261 */               Element ele = WSTrustElementFactory.newInstance().toElement(obj2);
/* 262 */               if (ele != null) {
/* 263 */                 NodeList nodeList = ele.getElementsByTagNameNS("*", "Identity");
/* 264 */                 if (nodeList.getLength() > 0) {
/* 265 */                   Element identity = (Element)nodeList.item(0);
/* 266 */                   result.add(identity);
/* 267 */                   NodeList clist = identity.getChildNodes();
/* 268 */                   for (int i = 0; i < clist.getLength(); i++) {
/* 269 */                     if (clist.item(i).getNodeType() == 3) {
/* 270 */                       String data = ((Text)clist.item(i)).getData();
/* 271 */                       X509Certificate cert = (X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(Base64.decode(data)));
/* 272 */                       result.add(cert);
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 277 */             } catch (Exception ex) {
/* 278 */               ex.printStackTrace();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/* 284 */     return result;
/*     */   }
/*     */   
/*     */   public static String getAppliesToURI(AppliesTo appliesTo) {
/* 288 */     List list = appliesTo.getAny();
/* 289 */     EndpointReference epr = null;
/* 290 */     if (!list.isEmpty()) {
/* 291 */       for (int i = 0; i < list.size(); i++) {
/* 292 */         Object obj = list.get(i);
/* 293 */         if (obj instanceof EndpointReference) {
/* 294 */           epr = (EndpointReference)obj;
/* 295 */         } else if (obj instanceof JAXBElement) {
/* 296 */           JAXBElement<EndpointReference> ele = (JAXBElement)obj;
/* 297 */           String local = ele.getName().getLocalPart();
/* 298 */           if (local.equalsIgnoreCase("EndpointReference")) {
/* 299 */             epr = ele.getValue();
/*     */           }
/*     */         } 
/*     */         
/* 303 */         if (epr != null) {
/* 304 */           AttributedURI uri = epr.getAddress();
/* 305 */           if (uri != null) {
/* 306 */             return uri.getValue();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/* 311 */     return null;
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
/*     */   public static String createFriendlyPPID(String displayValue) {
/* 324 */     return displayValue;
/*     */   }
/*     */   
/*     */   public static String elemToString(BaseSTSResponse rstr, WSTrustVersion wstVer) {
/* 328 */     StringWriter writer = new StringWriter();
/*     */     try {
/* 330 */       Transformer trans = WSITXMLFactory.createTransformerFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING).newTransformer();
/* 331 */       trans.transform(WSTrustElementFactory.newInstance(wstVer).toSource(rstr), new StreamResult(writer));
/* 332 */     } catch (Exception ex) {
/* 333 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 336 */     return writer.toString();
/*     */   }
/*     */   
/*     */   public static String elemToString(BaseSTSRequest rst, WSTrustVersion wstVer) {
/* 340 */     StringWriter writer = new StringWriter();
/*     */     try {
/* 342 */       Transformer trans = WSITXMLFactory.createTransformerFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING).newTransformer();
/* 343 */       trans.transform(WSTrustElementFactory.newInstance(wstVer).toSource(rst), new StreamResult(writer));
/* 344 */     } catch (Exception ex) {
/* 345 */       ex.printStackTrace();
/*     */     } 
/*     */     
/* 348 */     return writer.toString();
/*     */   }
/*     */   
/*     */   public static long getCurrentTimeWithOffset() {
/* 352 */     Calendar cal = new GregorianCalendar();
/* 353 */     int offset = cal.get(15);
/* 354 */     if (cal.getTimeZone().inDaylightTime(cal.getTime())) {
/* 355 */       offset += cal.getTimeZone().getDSTSavings();
/*     */     }
/*     */ 
/*     */     
/* 359 */     long beforeTime = cal.getTimeInMillis();
/*     */     
/* 361 */     return beforeTime - offset;
/*     */   }
/*     */   
/*     */   public static Lifetime createLifetime(long currentTime, long lifespan, WSTrustVersion wstVer) {
/* 365 */     SimpleDateFormat calendarFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'", Locale.getDefault());
/*     */     
/* 367 */     Calendar cal = new GregorianCalendar();
/* 368 */     synchronized (calendarFormatter) {
/* 369 */       calendarFormatter.setTimeZone(cal.getTimeZone());
/* 370 */       cal.setTimeInMillis(currentTime);
/*     */       
/* 372 */       AttributedDateTime created = new AttributedDateTime();
/* 373 */       created.setValue(calendarFormatter.format(cal.getTime()));
/*     */       
/* 375 */       AttributedDateTime expires = new AttributedDateTime();
/* 376 */       cal.setTimeInMillis(currentTime + lifespan);
/* 377 */       expires.setValue(calendarFormatter.format(cal.getTime()));
/*     */       
/* 379 */       Lifetime lifetime = WSTrustElementFactory.newInstance(wstVer).createLifetime(created, expires);
/*     */       
/* 381 */       return lifetime;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static long getLifeSpan(Lifetime lifetime) {
/* 386 */     AttributedDateTime created = lifetime.getCreated();
/* 387 */     AttributedDateTime expires = lifetime.getExpires();
/*     */     
/* 389 */     return parseAttributedDateTime(expires).getTime() - parseAttributedDateTime(created).getTime();
/*     */   }
/*     */   
/*     */   public static Date parseAttributedDateTime(AttributedDateTime time) {
/* 393 */     SimpleDateFormat calendarFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SSS'Z'", Locale.getDefault());
/*     */ 
/*     */     
/* 396 */     Date date = null;
/* 397 */     synchronized (calendarFormatter) {
/*     */       try {
/* 399 */         date = calendarFormatter.parse(time.getValue());
/* 400 */       } catch (Exception ex) {
/*     */         
/*     */         try {
/* 403 */           SimpleDateFormat calendarFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
/*     */ 
/*     */           
/* 406 */           date = calendarFormatter1.parse(time.getValue());
/* 407 */         } catch (ParseException pex) {
/* 408 */           throw new RuntimeException(pex);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 413 */     return date;
/*     */   }
/*     */   public static EncryptedKey encryptKey(Document doc, byte[] encryptedKey, X509Certificate cert, String keyWrapAlgorithm) throws Exception {
/*     */     XMLCipher cipher;
/* 417 */     PublicKey pubKey = cert.getPublicKey();
/*     */     
/* 419 */     if (keyWrapAlgorithm != null) {
/* 420 */       cipher = XMLCipher.getInstance(keyWrapAlgorithm);
/*     */     } else {
/* 422 */       cipher = XMLCipher.getInstance("http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p");
/*     */     } 
/* 424 */     cipher.init(3, pubKey);
/*     */     
/* 426 */     EncryptedKey encKey = cipher.encryptKey(doc, new SecretKeySpec(encryptedKey, "AES"));
/* 427 */     KeyInfo keyinfo = new KeyInfo(doc);
/*     */     
/* 429 */     byte[] skid = X509SubjectKeyIdentifier.getSubjectKeyIdentifier(cert);
/* 430 */     if (skid != null && skid.length > 0) {
/* 431 */       KeyIdentifierImpl keyIdentifierImpl = new KeyIdentifierImpl("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509SubjectKeyIdentifier", null);
/* 432 */       keyIdentifierImpl.setValue(Base64.encode(skid));
/* 433 */       SecurityTokenReferenceImpl securityTokenReferenceImpl = new SecurityTokenReferenceImpl((Reference)keyIdentifierImpl);
/* 434 */       keyinfo.addUnknownElement((Element)doc.importNode(WSTrustElementFactory.newInstance().toElement((SecurityTokenReference)securityTokenReferenceImpl, null), true));
/*     */     } else {
/* 436 */       X509Data x509data = new X509Data(doc);
/* 437 */       x509data.addCertificate(cert);
/* 438 */       keyinfo.add(x509data);
/*     */     } 
/* 440 */     encKey.setKeyInfo(keyinfo);
/*     */     
/* 442 */     return encKey;
/*     */   }
/*     */   
/*     */   public static Assertion addSamlAttributes(Assertion assertion, Map<QName, List<String>> claimedAttrs) throws WSTrustException {
/*     */     try {
/* 447 */       String version = assertion.getVersion();
/* 448 */       SAMLAssertionFactory samlFac = null;
/*     */       
/* 450 */       if ("2.0".equals(version)) {
/* 451 */         samlFac = SAMLAssertionFactory.newInstance("Saml2.0");
/*     */       } else {
/* 453 */         samlFac = SAMLAssertionFactory.newInstance("Saml1.1");
/*     */       } 
/* 455 */       Element assertionEle = assertion.toElement(null);
/* 456 */       String samlNS = assertionEle.getNamespaceURI();
/* 457 */       String samlPrefix = assertionEle.getPrefix();
/* 458 */       NodeList asList = assertionEle.getElementsByTagNameNS(samlNS, "AttributeStatement");
/* 459 */       Node as = null;
/* 460 */       if (asList.getLength() > 0) {
/* 461 */         as = asList.item(0);
/*     */       }
/* 463 */       createAttributeStatement(as, claimedAttrs, samlNS, samlPrefix);
/*     */       
/* 465 */       return samlFac.createAssertion(assertionEle);
/* 466 */     } catch (Exception ex) {
/* 467 */       throw new WSTrustException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Node createAttributeStatement(Node as, Map<QName, List<String>> claimedAttrs, String samlNS, String samlPrefix) throws WSTrustException {
/*     */     try {
/* 473 */       Document doc = null;
/* 474 */       if (as != null) {
/* 475 */         doc = as.getOwnerDocument();
/*     */       } else {
/* 477 */         doc = newDocument();
/* 478 */         as = doc.createElementNS(samlNS, samlPrefix + ":AttributeStatement");
/* 479 */         doc.appendChild(as);
/*     */       } 
/* 481 */       Set<Map.Entry<QName, List<String>>> entries = claimedAttrs.entrySet();
/* 482 */       for (Map.Entry<QName, List<String>> entry : entries) {
/* 483 */         QName attrKey = entry.getKey();
/* 484 */         List<String> values = entry.getValue();
/* 485 */         if (values.size() > 0) {
/* 486 */           Element attrEle = null;
/* 487 */           if ("NameID".equals(attrKey.getLocalPart())) {
/*     */             
/* 489 */             attrEle = createActorAttribute(doc, samlNS, samlPrefix, values.get(0));
/*     */           } else {
/*     */             
/* 492 */             attrEle = createAttribute(doc, samlNS, samlPrefix, attrKey);
/* 493 */             Iterator<String> valueIt = values.iterator();
/* 494 */             while (valueIt.hasNext()) {
/* 495 */               Element attrValueEle = doc.createElementNS(samlNS, samlPrefix + ":AttributeValue");
/* 496 */               Text text = doc.createTextNode(valueIt.next());
/* 497 */               attrValueEle.appendChild(text);
/* 498 */               attrEle.appendChild(attrValueEle);
/*     */             } 
/*     */           } 
/* 501 */           as.appendChild(attrEle);
/*     */         } 
/*     */       } 
/*     */       
/* 505 */       return as;
/* 506 */     } catch (Exception ex) {
/* 507 */       throw new WSTrustException(ex.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Element createAttribute(Document doc, String samlNS, String samlPrefix, QName attrKey) throws Exception {
/* 512 */     Element attrEle = doc.createElementNS(samlNS, samlPrefix + ":Attribute");
/* 513 */     attrEle.setAttribute("AttributeName", attrKey.getLocalPart());
/* 514 */     attrEle.setAttribute("AttributeNamespace", attrKey.getNamespaceURI());
/* 515 */     if ("urn:oasis:names:tc:SAML:2.0:assertion".equals(samlNS)) {
/* 516 */       attrEle.setAttribute("Name", attrKey.getLocalPart());
/* 517 */       attrEle.setAttribute("NameFormat", attrKey.getNamespaceURI());
/*     */     } 
/* 519 */     return attrEle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Element createActorAttribute(Document doc, String samlNS, String samlPrefix, String name) throws Exception {
/* 528 */     Element actorEle = createAttribute(doc, samlNS, samlPrefix, new QName("actor", "http://schemas.xmlsoap.com/ws/2009/09/identity/claims"));
/* 529 */     Element attrValueEle = doc.createElementNS(samlNS, samlPrefix + ":AttributeValue");
/* 530 */     actorEle.appendChild(attrValueEle);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 537 */     Element nameEle = createAttribute(doc, samlNS, samlPrefix, new QName("name", "http://schemas.xmlsoap.com/ws/2005/05/identity/claims"));
/* 538 */     attrValueEle.appendChild(nameEle);
/* 539 */     Element nameAttrValueEle = doc.createElementNS(samlNS, samlPrefix + ":AttributeValue");
/* 540 */     nameEle.appendChild(nameAttrValueEle);
/* 541 */     Text text = doc.createTextNode(name);
/* 542 */     nameAttrValueEle.appendChild(text);
/*     */     
/* 544 */     return actorEle;
/*     */   }
/*     */   
/*     */   public static Document newDocument() {
/*     */     Document doc;
/*     */     try {
/* 550 */       DocumentBuilderFactory dbf = WSITXMLFactory.createDocumentBuilderFactory(WSITXMLFactory.DISABLE_SECURE_PROCESSING);
/* 551 */       dbf.setNamespaceAware(true);
/* 552 */       DocumentBuilder db = dbf.newDocumentBuilder();
/* 553 */       doc = db.newDocument();
/* 554 */     } catch (Exception ex) {
/* 555 */       throw new RuntimeException(ex.getMessage(), ex);
/*     */     } 
/*     */     
/* 558 */     return doc;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trus\\util\WSTrustUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */