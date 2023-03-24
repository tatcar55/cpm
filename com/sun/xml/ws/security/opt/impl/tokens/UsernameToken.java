/*     */ package com.sun.xml.ws.security.opt.impl.tokens;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.tokens.UsernameToken;
/*     */ import com.sun.xml.ws.security.opt.impl.util.JAXBUtil;
/*     */ import com.sun.xml.ws.security.secext10.AttributedString;
/*     */ import com.sun.xml.ws.security.secext10.ObjectFactory;
/*     */ import com.sun.xml.ws.security.secext10.UsernameTokenType;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.SecurityTokenException;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.logging.LogStringsMessages;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
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
/*     */ public class UsernameToken
/*     */   extends UsernameTokenType
/*     */   implements UsernameToken, SecurityHeaderElement, SecurityElementWriter
/*     */ {
/*     */   public static final long MAX_NONCE_AGE = 900000L;
/*  90 */   private String passwordType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";
/*     */   
/*  92 */   private String usernameValue = null;
/*     */   
/*  94 */   private String passwordValue = null;
/*     */ 
/*     */   
/*  97 */   private String passwordDigestValue = null;
/*     */   
/*  99 */   private byte[] decodedNonce = null;
/*     */ 
/*     */   
/* 102 */   private String nonceValue = null;
/*     */ 
/*     */   
/* 105 */   private String nonceEncodingType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";
/*     */ 
/*     */   
/* 108 */   private String createdValue = null;
/*     */   
/*     */   private boolean bsp = false;
/*     */   
/*     */   private boolean valuesSet = false;
/*     */   
/* 114 */   private SOAPVersion soapVersion = SOAPVersion.SOAP_11;
/* 115 */   private ObjectFactory objFac = new ObjectFactory();
/*     */   
/* 117 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UsernameToken(SOAPVersion sv) {
/* 124 */     this.soapVersion = sv;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsernameValue() {
/* 132 */     return this.usernameValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUsernameValue(String username) {
/* 139 */     this.usernameValue = username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPasswordValue() {
/* 146 */     return this.passwordValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPasswordValue(String passwd) {
/* 157 */     this.passwordValue = passwd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPasswordType() {
/* 164 */     return this.passwordType;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setPasswordType(String passwordType) throws SecurityTokenException {
/* 169 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText".equals(passwordType)) {
/* 170 */       this.passwordType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";
/* 171 */     } else if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest".equals(passwordType)) {
/* 172 */       this.passwordType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest";
/*     */     } else {
/* 174 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0306_INVALID_PASSWD_TYPE("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest"), new Object[] { "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest" });
/*     */ 
/*     */ 
/*     */       
/* 178 */       throw new SecurityTokenException("Invalid password type. Must be one of   http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText or http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNonceEncodingType() {
/* 189 */     return this.nonceEncodingType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setNonceEncodingType(String nonceEncodingType) {
/* 199 */     if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary".equals(nonceEncodingType)) {
/* 200 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0307_NONCE_ENCTYPE_INVALID());
/* 201 */       throw new RuntimeException("Nonce encoding type invalid");
/*     */     } 
/* 203 */     this.nonceEncodingType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNonceValue() throws SecurityTokenException {
/* 210 */     return this.nonceValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCreatedValue() {
/* 217 */     return this.createdValue;
/*     */   }
/*     */   
/*     */   public String getPasswordDigestValue() {
/* 221 */     return this.passwordDigestValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNonce(String nonceValue) {
/* 229 */     if (nonceValue == null || "".equals(nonceValue)) {
/* 230 */       createNonce();
/*     */     } else {
/* 232 */       this.nonceValue = nonceValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreationTime(String time) throws XWSSecurityException {
/* 241 */     if (time == null || "".equals(time)) {
/* 242 */       this.createdValue = getCreatedFromTimestamp();
/*     */     } else {
/* 244 */       this.createdValue = time;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDigestOn() throws SecurityTokenException {
/* 249 */     setPasswordType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
/*     */   }
/*     */   
/*     */   public void isBSP(boolean flag) {
/* 253 */     this.bsp = flag;
/*     */   }
/*     */   
/*     */   public boolean isBSP() {
/* 257 */     return this.bsp;
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 261 */     return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/* 265 */     return "UsernameToken";
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 269 */     QName qname = new QName(nsUri, localName);
/* 270 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 271 */     return otherAttributes.get(qname);
/*     */   }
/*     */   
/*     */   public String getAttribute(QName name) {
/* 275 */     Map<QName, String> otherAttributes = getOtherAttributes();
/* 276 */     return otherAttributes.get(name);
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 280 */     if (!this.valuesSet)
/* 281 */       setValues(); 
/* 282 */     XMLStreamBufferResult xbr = new XMLStreamBufferResult();
/* 283 */     JAXBElement<UsernameTokenType> utElem = this.objFac.createUsernameToken(this);
/*     */     try {
/* 285 */       getMarshaller().marshal(utElem, (Result)xbr);
/*     */     }
/* 287 */     catch (JAXBException je) {
/* 288 */       throw new XMLStreamException(je);
/*     */     } 
/* 290 */     return (XMLStreamReader)xbr.getXMLStreamBuffer().readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream os) {
/* 294 */     if (!this.valuesSet)
/* 295 */       setValues(); 
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter) throws XMLStreamException {
/* 299 */     if (!this.valuesSet)
/* 300 */       setValues(); 
/* 301 */     JAXBElement<UsernameTokenType> utElem = this.objFac.createUsernameToken(this);
/*     */     
/*     */     try {
/* 304 */       if (streamWriter instanceof Map) {
/* 305 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 306 */         if (os != null) {
/* 307 */           streamWriter.writeCharacters("");
/* 308 */           getMarshaller().marshal(utElem, os);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 313 */       getMarshaller().marshal(utElem, streamWriter);
/* 314 */     } catch (JAXBException e) {
/* 315 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Marshaller getMarshaller() throws JAXBException {
/* 320 */     return JAXBUtil.createMarshaller(this.soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createNonce() {
/* 331 */     this.decodedNonce = new byte[18];
/*     */     try {
/* 333 */       SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
/* 334 */       random.nextBytes(this.decodedNonce);
/* 335 */     } catch (NoSuchAlgorithmException e) {
/* 336 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0310_NO_SUCH_ALGORITHM(e.getMessage()), new Object[] { e.getMessage() });
/* 337 */       throw new RuntimeException("No such algorithm found" + e.getMessage());
/*     */     } 
/*     */     
/* 340 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary" == this.nonceEncodingType) {
/* 341 */       this.nonceValue = Base64.encode(this.decodedNonce);
/*     */     } else {
/* 343 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0389_UNRECOGNIZED_NONCE_ENCODING(this.nonceEncodingType), this.nonceEncodingType);
/* 344 */       throw new RuntimeException("Unrecognized encoding: " + this.nonceEncodingType);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getCreatedFromTimestamp() throws XWSSecurityException {
/* 350 */     Timestamp ts = new Timestamp(this.soapVersion);
/* 351 */     ts.createDateTime();
/* 352 */     return ts.getCreated().getValue();
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
/*     */   private void createDigest() throws SecurityTokenException {
/*     */     byte[] utf8Bytes, bytesToHash, hash;
/* 366 */     String utf8String = "";
/* 367 */     if (this.createdValue != null) {
/* 368 */       utf8String = utf8String + this.createdValue;
/*     */     }
/*     */ 
/*     */     
/* 372 */     if (this.passwordValue != null) {
/* 373 */       utf8String = utf8String + this.passwordValue;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 378 */       utf8Bytes = utf8String.getBytes("utf-8");
/* 379 */     } catch (UnsupportedEncodingException uee) {
/* 380 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0390_UNSUPPORTED_CHARSET_EXCEPTION());
/* 381 */       throw new SecurityTokenException(uee);
/*     */     } 
/*     */ 
/*     */     
/* 385 */     if (this.decodedNonce != null) {
/* 386 */       bytesToHash = new byte[utf8Bytes.length + this.decodedNonce.length]; int i;
/* 387 */       for (i = 0; i < this.decodedNonce.length; i++)
/* 388 */         bytesToHash[i] = this.decodedNonce[i]; 
/* 389 */       for (i = this.decodedNonce.length; i < utf8Bytes.length + this.decodedNonce.length; i++)
/* 390 */         bytesToHash[i] = utf8Bytes[i - this.decodedNonce.length]; 
/*     */     } else {
/* 392 */       bytesToHash = utf8Bytes;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 397 */       MessageDigest sha = MessageDigest.getInstance("SHA-1");
/* 398 */       hash = sha.digest(bytesToHash);
/* 399 */     } catch (Exception e) {
/* 400 */       log.log(Level.SEVERE, LogStringsMessages.WSS_0311_PASSWD_DIGEST_COULDNOT_BE_CREATED(e.getMessage()), new Object[] { e.getMessage() });
/* 401 */       throw new SecurityTokenException("Password Digest could not be created. " + e.getMessage());
/*     */     } 
/*     */     
/* 404 */     this.passwordDigestValue = Base64.encode(hash);
/*     */   }
/*     */   
/*     */   private void setValues() {
/* 408 */     if (this.usernameValue != null) {
/* 409 */       AttributedString ut = this.objFac.createAttributedString();
/* 410 */       ut.setValue(this.usernameValue);
/* 411 */       setUsername(ut);
/*     */     } 
/*     */     
/* 414 */     if (this.passwordValue != null && !MessageConstants._EMPTY.equals(this.passwordValue)) {
/* 415 */       AttributedString pw = this.objFac.createAttributedString();
/* 416 */       if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest" == this.passwordType) {
/*     */         try {
/* 418 */           createDigest();
/* 419 */         } catch (SecurityTokenException ex) {
/* 420 */           ex.printStackTrace();
/*     */         } 
/* 422 */         pw.setValue(this.passwordDigestValue);
/* 423 */         setPassword(pw);
/*     */       } else {
/* 425 */         pw.setValue(this.passwordValue);
/* 426 */         setPassword(pw);
/*     */       } 
/* 428 */       QName qname = new QName("Type");
/* 429 */       pw.getOtherAttributes().put(qname, this.passwordType);
/*     */     } 
/*     */ 
/*     */     
/* 433 */     if (this.nonceValue != null) {
/* 434 */       AttributedString non = this.objFac.createAttributedString();
/* 435 */       non.setValue(this.nonceValue);
/* 436 */       setNonce(non);
/* 437 */       if (this.nonceEncodingType != null) {
/* 438 */         QName qname = new QName("EncodingType");
/* 439 */         non.getOtherAttributes().put(qname, this.nonceEncodingType);
/*     */       } 
/*     */     } 
/*     */     
/* 443 */     if (this.createdValue != null) {
/* 444 */       AttributedString cr = this.objFac.createAttributedString();
/* 445 */       cr.setValue(this.createdValue);
/* 446 */       setCreated(cr);
/*     */     } 
/*     */     
/* 449 */     this.valuesSet = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean refersToSecHdrWithId(String id) {
/* 458 */     return false;
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter streamWriter, HashMap<Object, Object> props) throws XMLStreamException {
/*     */     try {
/* 463 */       if (!this.valuesSet)
/* 464 */         setValues(); 
/* 465 */       Marshaller marshaller = getMarshaller();
/* 466 */       Iterator<Map.Entry<Object, Object>> itr = props.entrySet().iterator();
/* 467 */       while (itr.hasNext()) {
/* 468 */         Map.Entry<Object, Object> entry = itr.next();
/* 469 */         marshaller.setProperty((String)entry.getKey(), entry.getValue());
/*     */       } 
/* 471 */       JAXBElement<UsernameTokenType> utElem = this.objFac.createUsernameToken(this);
/* 472 */       if (streamWriter instanceof Map) {
/* 473 */         OutputStream os = (OutputStream)((Map)streamWriter).get("sjsxp-outputstream");
/* 474 */         if (os != null) {
/* 475 */           streamWriter.writeCharacters("");
/* 476 */           marshaller.marshal(utElem, os);
/*     */           return;
/*     */         } 
/*     */       } 
/* 480 */       marshaller.marshal(utElem, streamWriter);
/* 481 */     } catch (JAXBException jbe) {
/* 482 */       throw new XMLStreamException(jbe);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\tokens\UsernameToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */