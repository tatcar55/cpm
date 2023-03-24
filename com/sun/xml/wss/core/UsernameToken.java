/*     */ package com.sun.xml.wss.core;
/*     */ 
/*     */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*     */ import com.sun.xml.ws.security.Token;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.SecurityTokenException;
/*     */ import com.sun.xml.wss.impl.XMLUtil;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.misc.SecurityHeaderBlockImpl;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Node;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UsernameToken
/*     */   extends SecurityHeaderBlockImpl
/*     */   implements SecurityToken, Token
/*     */ {
/*     */   public static final long MAX_NONCE_AGE = 900000L;
/*     */   private String username;
/* 105 */   private String password = null;
/*     */ 
/*     */   
/* 108 */   private String passwordType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";
/*     */ 
/*     */   
/* 111 */   private String passwordDigest = null;
/*     */   
/* 113 */   private byte[] decodedNonce = null;
/*     */ 
/*     */   
/* 116 */   private String nonce = null;
/*     */ 
/*     */   
/* 119 */   private String nonceEncodingType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";
/*     */ 
/*     */   
/* 122 */   private String created = null;
/*     */ 
/*     */   
/*     */   private boolean bsp = false;
/*     */   
/*     */   private Document soapDoc;
/*     */   
/* 129 */   private static Logger log = Logger.getLogger("javax.enterprise.resource.xml.webservices.security", "com.sun.xml.wss.logging.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecurityHeaderBlock fromSoapElement(SOAPElement element) throws XWSSecurityException {
/* 135 */     return SecurityHeaderBlockImpl.fromSoapElement(element, UsernameToken.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public UsernameToken(Document document, String username) throws SecurityTokenException {
/* 140 */     this.soapDoc = document;
/* 141 */     this.username = username;
/*     */     
/* 143 */     setPasswordType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
/*     */   }
/*     */   
/*     */   public UsernameToken(Document document, String username, String password, boolean digestPassword) throws SecurityTokenException {
/* 147 */     this(document, username);
/* 148 */     this.password = password;
/* 149 */     if (digestPassword) {
/* 150 */       setPasswordType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
/*     */     }
/*     */   }
/*     */   
/*     */   public UsernameToken(Document document, String username, String password) throws SecurityTokenException {
/* 155 */     this(document, username, password, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UsernameToken(Document document, String username, String password, boolean setNonce, boolean digestPassword) throws SecurityTokenException {
/* 163 */     this(document, username, password, digestPassword);
/* 164 */     if (setNonce) {
/* 165 */       createNonce();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UsernameToken(Document document, String username, String password, boolean setNonce, boolean setCreatedTimestamp, boolean digestPassword) throws SecurityTokenException {
/* 175 */     this(document, username, password, setNonce, digestPassword);
/* 176 */     if (setCreatedTimestamp) {
/*     */       try {
/* 178 */         this.created = getCreatedFromTimestamp();
/* 179 */       } catch (Exception e) {
/* 180 */         log.log(Level.SEVERE, "WSS0280.failed.create.UsernameToken", e);
/* 181 */         throw new SecurityTokenException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public UsernameToken(SOAPElement usernameTokenSoapElement, boolean bspFlag) throws XWSSecurityException {
/* 187 */     this(usernameTokenSoapElement);
/* 188 */     isBSP(bspFlag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UsernameToken(SOAPElement usernameTokenSoapElement) throws XWSSecurityException {
/* 198 */     setSOAPElement(usernameTokenSoapElement);
/* 199 */     this.soapDoc = getOwnerDocument();
/*     */     
/* 201 */     if (!"UsernameToken".equals(getLocalName()) || !XMLUtil.inWsseNS(this)) {
/*     */       
/* 203 */       log.log(Level.SEVERE, "WSS0329.usernametoken.expected", new Object[] { getLocalName() });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 208 */       throw new SecurityTokenException("Expected UsernameToken Element, but Found " + getLocalName());
/*     */     } 
/*     */ 
/*     */     
/* 212 */     boolean invalidToken = false;
/*     */     
/* 214 */     Iterator<Node> children = getChildElements();
/*     */ 
/*     */ 
/*     */     
/* 218 */     Node object = null;
/* 219 */     while (children.hasNext() && !(object instanceof SOAPElement)) {
/* 220 */       object = children.next();
/*     */     }
/*     */     
/* 223 */     if (object != null && object.getNodeType() == 1) {
/* 224 */       SOAPElement element = (SOAPElement)object;
/* 225 */       if ("Username".equals(element.getLocalName()) && XMLUtil.inWsseNS(element)) {
/*     */         
/* 227 */         this.username = element.getValue();
/*     */       } else {
/* 229 */         log.log(Level.SEVERE, "WSS0330.usernametoken.firstchild.mustbe.username");
/* 230 */         throw new SecurityTokenException("The first child of a UsernameToken Element, should be a Username ");
/*     */       } 
/*     */     } else {
/*     */       
/* 234 */       invalidToken = true;
/*     */     } 
/*     */     
/* 237 */     while (children.hasNext()) {
/*     */       
/* 239 */       object = children.next();
/*     */       
/* 241 */       if (object.getNodeType() == 1) {
/*     */         
/* 243 */         SOAPElement element = (SOAPElement)object;
/* 244 */         if ("Password".equals(element.getLocalName()) && XMLUtil.inWsseNS(element)) {
/*     */           
/* 246 */           String passwordType = element.getAttribute("Type");
/*     */           
/* 248 */           if (isBSP() && passwordType.length() < 1) {
/*     */             
/* 250 */             log.log(Level.SEVERE, "BSP4201.PasswordType.Username");
/* 251 */             throw new XWSSecurityException(" A wsse:UsernameToken/wsse:Password element in a SECURITY_HEADER MUST specify a Type attribute.");
/*     */           } 
/*     */           
/* 254 */           if (!"".equals(passwordType)) {
/* 255 */             setPasswordType(passwordType);
/*     */           }
/* 257 */           if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText" == this.passwordType) {
/* 258 */             this.password = element.getValue(); continue;
/*     */           } 
/* 260 */           this.passwordDigest = element.getValue(); continue;
/*     */         } 
/* 262 */         if ("Nonce".equals(element.getLocalName()) && XMLUtil.inWsseNS(element)) {
/* 263 */           this.nonce = element.getValue();
/* 264 */           String encodingType = element.getAttribute("EncodingType");
/*     */           
/* 266 */           if (!"".equals(encodingType))
/* 267 */             setNonceEncodingType(encodingType); 
/*     */           try {
/* 269 */             this.decodedNonce = Base64.decode(this.nonce);
/* 270 */           } catch (Base64DecodingException bde) {
/* 271 */             log.log(Level.SEVERE, "WSS0309.couldnot.decode.base64.nonce", bde);
/* 272 */             throw new XWSSecurityException(bde);
/*     */           }  continue;
/*     */         } 
/* 275 */         if ("Created".equals(element.getLocalName()) && XMLUtil.inWsuNS(element)) {
/*     */           
/* 277 */           this.created = element.getValue(); continue;
/*     */         } 
/* 279 */         invalidToken = true;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 284 */     if (invalidToken) {
/* 285 */       log.log(Level.SEVERE, "WSS0331.invalid.usernametoken");
/* 286 */       throw new SecurityTokenException("Element passed was not a SOAPElement or is not a proper UsernameToken");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 291 */     if (null == this.username) {
/* 292 */       log.log(Level.SEVERE, "WSS0332.usernametoken.null.username");
/* 293 */       throw new SecurityTokenException("Username token does not contain the username");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUsername() {
/* 302 */     return this.username;
/*     */   }
/*     */   
/*     */   public void setUsername(String username) {
/* 306 */     this.username = username;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPassword() {
/* 313 */     return this.password;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPasswordType() {
/* 320 */     return this.passwordType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setPasswordType(String passwordType) throws SecurityTokenException {
/* 326 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText".equals(passwordType)) {
/* 327 */       this.passwordType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";
/* 328 */     } else if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest".equals(passwordType)) {
/* 329 */       this.passwordType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest";
/*     */     } else {
/* 331 */       log.log(Level.SEVERE, "WSS0306.invalid.passwd.type", new Object[] { "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest" });
/*     */ 
/*     */ 
/*     */       
/* 335 */       throw new SecurityTokenException("Invalid password type. Must be one of   http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText or http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
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
/* 346 */     return this.nonceEncodingType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setNonceEncodingType(String nonceEncodingType) {
/* 356 */     if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary".equals(nonceEncodingType)) {
/* 357 */       log.log(Level.SEVERE, "WSS0307.nonce.enctype.invalid");
/* 358 */       throw new RuntimeException("Nonce encoding type invalid");
/*     */     } 
/* 360 */     this.nonceEncodingType = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNonce() throws SecurityTokenException {
/* 368 */     return this.nonce;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCreated() {
/* 375 */     return this.created;
/*     */   }
/*     */   
/*     */   public String getPasswordDigest() {
/* 379 */     return this.passwordDigest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPassword(String passwd) {
/* 387 */     this.password = passwd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNonce(String nonceValue) {
/* 395 */     if (nonceValue == null || "".equals(nonceValue)) {
/* 396 */       createNonce();
/*     */     } else {
/* 398 */       this.nonce = nonceValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreationTime(String time) throws XWSSecurityException {
/* 406 */     if (time == null || "".equals(time)) {
/* 407 */       this.created = getCreatedFromTimestamp();
/*     */     } else {
/* 409 */       this.created = time;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDigestOn() throws SecurityTokenException {
/* 414 */     setPasswordType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest");
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPElement getAsSoapElement() throws SecurityTokenException {
/* 419 */     if (null != this.delegateElement)
/* 420 */       return this.delegateElement; 
/*     */     try {
/* 422 */       setSOAPElement((SOAPElement)this.soapDoc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:UsernameToken"));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 427 */       addNamespaceDeclaration("wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
/*     */ 
/*     */ 
/*     */       
/* 431 */       if (null == this.username || MessageConstants._EMPTY.equals(this.username)) {
/* 432 */         log.log(Level.SEVERE, "WSS0387.error.creating.usernametoken");
/* 433 */         throw new SecurityTokenException("username was not set");
/*     */       } 
/* 435 */       addChildElement("Username", "wsse").addTextNode(this.username);
/*     */ 
/*     */ 
/*     */       
/* 439 */       if (this.password != null && !MessageConstants._EMPTY.equals(this.password)) {
/* 440 */         SOAPElement wssePassword = addChildElement("Password", "wsse");
/*     */ 
/*     */         
/* 443 */         if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordDigest" == this.passwordType) {
/* 444 */           createDigest();
/* 445 */           wssePassword.addTextNode(this.passwordDigest);
/*     */         } else {
/* 447 */           wssePassword.addTextNode(this.password);
/*     */         } 
/* 449 */         wssePassword.setAttribute("Type", this.passwordType);
/*     */       } 
/*     */       
/* 452 */       if (this.nonce != null) {
/* 453 */         SOAPElement wsseNonce = addChildElement("Nonce", "wsse");
/*     */         
/* 455 */         wsseNonce.addTextNode(this.nonce);
/*     */         
/* 457 */         if (this.nonceEncodingType != null) {
/* 458 */           wsseNonce.setAttribute("EncodingType", this.nonceEncodingType);
/*     */         }
/*     */       } 
/*     */       
/* 462 */       if (this.created != null) {
/* 463 */         SOAPElement wsuCreated = addChildElement("Created", "wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 468 */         wsuCreated.addTextNode(this.created);
/*     */       }
/*     */     
/* 471 */     } catch (SOAPException se) {
/* 472 */       log.log(Level.SEVERE, "WSS0388.error.creating.usernametoken", se.getMessage());
/* 473 */       throw new SecurityTokenException("There was an error creating Username Token " + se.getMessage());
/*     */     } 
/*     */ 
/*     */     
/* 477 */     return this.delegateElement;
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
/* 488 */     this.decodedNonce = new byte[18];
/*     */     try {
/* 490 */       SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
/* 491 */       random.nextBytes(this.decodedNonce);
/* 492 */     } catch (NoSuchAlgorithmException e) {
/* 493 */       log.log(Level.SEVERE, "WSS0310.no.such.algorithm", new Object[] { e.getMessage() });
/*     */       
/* 495 */       throw new RuntimeException("No such algorithm found" + e.getMessage());
/*     */     } 
/*     */     
/* 498 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary" == this.nonceEncodingType) {
/* 499 */       this.nonce = Base64.encode(this.decodedNonce);
/*     */     } else {
/* 501 */       log.log(Level.SEVERE, "WSS0389.unrecognized.nonce.encoding", this.nonceEncodingType);
/* 502 */       throw new RuntimeException("Unrecognized encoding: " + this.nonceEncodingType);
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
/*     */   private void createDigest() throws SecurityTokenException {
/*     */     byte[] utf8Bytes, bytesToHash, hash;
/* 518 */     String utf8String = "";
/* 519 */     if (this.created != null) {
/* 520 */       utf8String = utf8String + this.created;
/*     */     }
/*     */ 
/*     */     
/* 524 */     if (this.password != null) {
/* 525 */       utf8String = utf8String + this.password;
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 530 */       utf8Bytes = utf8String.getBytes("utf-8");
/* 531 */     } catch (UnsupportedEncodingException uee) {
/* 532 */       log.log(Level.SEVERE, "WSS0390.unsupported.charset.exception");
/* 533 */       throw new SecurityTokenException(uee);
/*     */     } 
/*     */ 
/*     */     
/* 537 */     if (this.decodedNonce != null) {
/* 538 */       bytesToHash = new byte[utf8Bytes.length + 18]; int i;
/* 539 */       for (i = 0; i < 18; i++)
/* 540 */         bytesToHash[i] = this.decodedNonce[i]; 
/* 541 */       for (i = 18; i < utf8Bytes.length + 18; i++)
/* 542 */         bytesToHash[i] = utf8Bytes[i - 18]; 
/*     */     } else {
/* 544 */       bytesToHash = utf8Bytes;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 549 */       MessageDigest sha = MessageDigest.getInstance("SHA-1");
/* 550 */       hash = sha.digest(bytesToHash);
/* 551 */     } catch (Exception e) {
/* 552 */       log.log(Level.SEVERE, "WSS0311.passwd.digest.couldnot.be.created", new Object[] { e.getMessage() });
/*     */       
/* 554 */       throw new SecurityTokenException("Password Digest could not be created. " + e.getMessage());
/*     */     } 
/*     */     
/* 557 */     this.passwordDigest = Base64.encode(hash);
/*     */   }
/*     */   
/*     */   private String getCreatedFromTimestamp() throws XWSSecurityException {
/* 561 */     Timestamp ts = new Timestamp();
/* 562 */     ts.createDateTime();
/* 563 */     return ts.getCreated();
/*     */   }
/*     */   
/*     */   public void isBSP(boolean flag) {
/* 567 */     this.bsp = flag;
/*     */   }
/*     */   
/*     */   public boolean isBSP() {
/* 571 */     return this.bsp;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 575 */     return "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0";
/*     */   }
/*     */   
/*     */   public Object getTokenValue() {
/* 579 */     log.log(Level.SEVERE, "WSS0281.unsupported.operation");
/* 580 */     throw new UnsupportedOperationException("Not yet implemented");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\core\UsernameToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */