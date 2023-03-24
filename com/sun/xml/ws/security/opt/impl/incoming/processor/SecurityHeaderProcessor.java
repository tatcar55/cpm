/*     */ package com.sun.xml.ws.security.opt.impl.incoming.processor;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferException;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferCreator;
/*     */ import com.sun.xml.ws.security.IssuedTokenContext;
/*     */ import com.sun.xml.ws.security.impl.IssuedTokenContextImpl;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.DerivedKeyToken;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.EncryptedData;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.EncryptedKey;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.GenericSecuredHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.KerberosBinarySecurityToken;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.SAMLAssertion;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.Signature;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.SignatureConfirmation;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.TimestampHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.UsernameTokenHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.incoming.X509BinarySecurityToken;
/*     */ import com.sun.xml.wss.BasicSecurityProfile;
/*     */ import com.sun.xml.wss.ProcessingContext;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.LogStringsMessages;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityHeaderProcessor
/*     */ {
/*  82 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt", "com.sun.xml.wss.logging.impl.opt.LogStrings");
/*     */   
/*     */   private static final int TIMESTAMP_ELEMENT = 1;
/*     */   
/*     */   private static final int USERNAMETOKEN_ELEMENT = 2;
/*     */   private static final int BINARYSECURITY_TOKEN_ELEMENT = 3;
/*     */   private static final int ENCRYPTED_DATA_ELEMENT = 4;
/*     */   private static final int ENCRYPTED_KEY_ELEMENT = 5;
/*     */   private static final int SIGNATURE_ELEMENT = 6;
/*     */   private static final int REFERENCE_LIST_ELEMENT = 7;
/*     */   private static final int DERIVED_KEY_ELEMENT = 8;
/*     */   private static final int SIGNATURE_CONFIRMATION_ELEMENT = 9;
/*     */   private static final int SECURITY_CONTEXT_TOKEN = 10;
/*     */   private static final int SAML_ASSERTION_ELEMEMENT = 11;
/*  96 */   private Map<String, String> currentParentNS = new HashMap<String, String>();
/*     */   private JAXBFilterProcessingContext context;
/*  98 */   private XMLInputFactory staxIF = null;
/*  99 */   private StreamReaderBufferCreator creator = null;
/* 100 */   private BasicSecurityProfile bspContext = null;
/*     */ 
/*     */   
/*     */   public SecurityHeaderProcessor(JAXBFilterProcessingContext context, Map<String, String> namespaceList, XMLInputFactory xi, StreamReaderBufferCreator sbc) {
/* 104 */     this.context = context;
/* 105 */     this.currentParentNS = namespaceList;
/* 106 */     this.staxIF = xi;
/* 107 */     this.context = context;
/* 108 */     this.creator = sbc;
/* 109 */     this.bspContext = context.getBSPContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityHeaderElement createHeader(XMLStreamReader message) throws XWSSecurityException {
/* 120 */     int eventType = getSecurityElementType(message);
/*     */     try {
/* 122 */       while (eventType != -1) {
/* 123 */         TimestampHeader timestamp; String valueType; EncryptedKey ek; EncryptedData ed; UsernameTokenHeader ut; DerivedKeyToken dkt; SignatureConfirmation signConfirm; SecurityContextToken sct; Signature sig; SAMLAssertion samlAssertion; X509BinarySecurityToken bst; switch (eventType) {
/*     */           case 1:
/* 125 */             if (this.context.isBSP() && this.bspContext.isTimeStampFound()) {
/* 126 */               BasicSecurityProfile.log_bsp_3203();
/*     */             }
/* 128 */             this.bspContext.setTimeStampFound(true);
/* 129 */             timestamp = new TimestampHeader(message, this.creator, (HashMap)this.currentParentNS, this.context);
/* 130 */             timestamp.validate((ProcessingContext)this.context);
/* 131 */             this.context.getSecurityContext().getProcessedSecurityHeaders().add(timestamp);
/* 132 */             this.context.getInferredSecurityPolicy().append((SecurityPolicy)timestamp.getPolicy());
/* 133 */             return (SecurityHeaderElement)timestamp;
/*     */ 
/*     */           
/*     */           case 3:
/* 137 */             valueType = message.getAttributeValue("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "ValueType");
/* 138 */             if (valueType == "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ1510") {
/* 139 */               KerberosBinarySecurityToken kbst = new KerberosBinarySecurityToken(message, this.creator, (HashMap)this.currentParentNS, this.staxIF);
/* 140 */               kbst.validate((ProcessingContext)this.context);
/* 141 */               this.context.getSecurityContext().getProcessedSecurityHeaders().add(kbst);
/* 142 */               this.context.getInferredSecurityPolicy().append((SecurityPolicy)kbst.getPolicy());
/* 143 */               if (this.context.isTrustMessage() && !this.context.isClient()) {
/* 144 */                 IssuedTokenContext ctx = null;
/* 145 */                 if (this.context.getTrustContext() == null) {
/* 146 */                   IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/* 147 */                   issuedTokenContextImpl.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:Kerberos");
/* 148 */                   this.context.setTrustContext((IssuedTokenContext)issuedTokenContextImpl);
/*     */                 } else {
/* 150 */                   ctx = this.context.getTrustContext();
/* 151 */                   if (ctx.getAuthnContextClass() != null) {
/* 152 */                     ctx.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:Kerberos");
/* 153 */                     this.context.setTrustContext(ctx);
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 157 */               return (SecurityHeaderElement)kbst;
/*     */             } 
/* 159 */             bst = new X509BinarySecurityToken(message, this.creator, (HashMap)this.currentParentNS, this.staxIF);
/* 160 */             bst.validate((ProcessingContext)this.context);
/* 161 */             this.context.getSecurityContext().getProcessedSecurityHeaders().add(bst);
/* 162 */             this.context.getInferredSecurityPolicy().append((SecurityPolicy)bst.getPolicy());
/* 163 */             if (this.context.isTrustMessage() && !this.context.isClient()) {
/* 164 */               IssuedTokenContext ctx = null;
/* 165 */               if (this.context.getTrustContext() == null) {
/* 166 */                 IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/* 167 */                 issuedTokenContextImpl.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:X509");
/* 168 */                 this.context.setTrustContext((IssuedTokenContext)issuedTokenContextImpl);
/*     */               } else {
/* 170 */                 ctx = this.context.getTrustContext();
/* 171 */                 if (ctx.getAuthnContextClass() != null) {
/* 172 */                   ctx.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:X509");
/* 173 */                   this.context.setTrustContext(ctx);
/*     */                 } 
/*     */               } 
/*     */             } 
/* 177 */             return (SecurityHeaderElement)bst;
/*     */ 
/*     */           
/*     */           case 5:
/* 181 */             ek = new EncryptedKey(message, this.context, (HashMap)this.currentParentNS);
/* 182 */             this.context.getSecurityContext().getProcessedSecurityHeaders().add(ek);
/* 183 */             return (SecurityHeaderElement)ek;
/*     */           
/*     */           case 4:
/* 186 */             ed = new EncryptedData(message, this.context, (HashMap)this.currentParentNS);
/* 187 */             this.context.getSecurityContext().getProcessedSecurityHeaders().add(ed);
/* 188 */             return (SecurityHeaderElement)ed;
/*     */           
/*     */           case 2:
/* 191 */             ut = new UsernameTokenHeader(message, this.creator, (HashMap)this.currentParentNS, this.staxIF);
/* 192 */             ut.validate((ProcessingContext)this.context);
/* 193 */             if (this.context.isTrustMessage() && !this.context.isClient()) {
/* 194 */               IssuedTokenContext ctx = null;
/* 195 */               if (this.context.getTrustContext() == null) {
/* 196 */                 IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/* 197 */                 if (this.context.isSecure()) {
/* 198 */                   issuedTokenContextImpl.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
/*     */                 } else {
/* 200 */                   issuedTokenContextImpl.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:Password");
/*     */                 } 
/* 202 */                 this.context.setTrustContext((IssuedTokenContext)issuedTokenContextImpl);
/*     */               } else {
/* 204 */                 ctx = this.context.getTrustContext();
/* 205 */                 if (ctx.getAuthnContextClass() != null) {
/* 206 */                   if (this.context.isSecure()) {
/* 207 */                     ctx.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:PasswordProtectedTransport");
/*     */                   } else {
/* 209 */                     ctx.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:Password");
/*     */                   } 
/* 211 */                   this.context.setTrustContext(ctx);
/*     */                 } 
/*     */               } 
/*     */             } 
/* 215 */             this.context.getSecurityContext().getProcessedSecurityHeaders().add(ut);
/* 216 */             this.context.getInferredSecurityPolicy().append((SecurityPolicy)ut.getPolicy());
/* 217 */             return (SecurityHeaderElement)ut;
/*     */           
/*     */           case 8:
/* 220 */             dkt = new DerivedKeyToken(message, this.context, (HashMap)this.currentParentNS);
/* 221 */             this.context.getSecurityContext().getProcessedSecurityHeaders().add(dkt);
/* 222 */             return (SecurityHeaderElement)dkt;
/*     */ 
/*     */           
/*     */           case 9:
/* 226 */             signConfirm = new SignatureConfirmation(message, this.creator, (HashMap)this.currentParentNS, this.staxIF);
/* 227 */             signConfirm.validate((ProcessingContext)this.context);
/* 228 */             this.context.getSecurityContext().getProcessedSecurityHeaders().add(signConfirm);
/* 229 */             return (SecurityHeaderElement)signConfirm;
/*     */ 
/*     */           
/*     */           case 10:
/* 233 */             sct = new SecurityContextToken(message, this.context, (HashMap)this.currentParentNS);
/* 234 */             this.context.getSecurityContext().getProcessedSecurityHeaders().add(sct);
/* 235 */             return (SecurityHeaderElement)sct;
/*     */           
/*     */           case 6:
/* 238 */             sig = new Signature(this.context, this.currentParentNS, this.creator, true);
/* 239 */             sig.process(message);
/* 240 */             if (sig.getReferences().size() == 0) {
/* 241 */               this.context.getSecurityContext().getProcessedSecurityHeaders().add(sig);
/*     */             }
/* 243 */             this.context.getInferredSecurityPolicy().append((SecurityPolicy)sig.getPolicy());
/* 244 */             return (SecurityHeaderElement)sig;
/*     */           
/*     */           case 11:
/* 247 */             samlAssertion = new SAMLAssertion(message, this.context, null, (HashMap)this.currentParentNS);
/* 248 */             this.context.getSecurityContext().getProcessedSecurityHeaders().add(samlAssertion);
/* 249 */             if (samlAssertion.isHOK()) {
/* 250 */               samlAssertion.validateSignature();
/*     */             }
/* 252 */             samlAssertion.validate((ProcessingContext)this.context);
/* 253 */             samlAssertion.getKey();
/*     */ 
/*     */             
/* 256 */             if (this.context.getExtraneousProperty("incoming_saml_assertion") == null && samlAssertion.isHOK()) {
/* 257 */               this.context.getExtraneousProperties().put("incoming_saml_assertion", samlAssertion);
/*     */             }
/* 259 */             if (this.context.isTrustMessage() && !this.context.isClient()) {
/* 260 */               IssuedTokenContext ctx = null;
/* 261 */               if (this.context.getTrustContext() == null) {
/* 262 */                 IssuedTokenContextImpl issuedTokenContextImpl = new IssuedTokenContextImpl();
/* 263 */                 issuedTokenContextImpl.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession");
/* 264 */                 this.context.setTrustContext((IssuedTokenContext)issuedTokenContextImpl);
/*     */               } else {
/* 266 */                 ctx = this.context.getTrustContext();
/* 267 */                 if (ctx.getAuthnContextClass() != null) {
/* 268 */                   ctx.setAuthnContextClass("urn:oasis:names:tc:SAML:2.0:ac:classes:PreviousSession");
/* 269 */                   this.context.setTrustContext(ctx);
/*     */                 } 
/*     */               } 
/* 272 */             } else if (!this.context.isTrustMessage()) {
/* 273 */               this.context.getInferredSecurityPolicy().append((SecurityPolicy)samlAssertion.getPolicy());
/*     */             } 
/* 275 */             return (SecurityHeaderElement)samlAssertion;
/*     */         } 
/*     */         
/* 278 */         GenericSecuredHeader gsh = new GenericSecuredHeader(message, null, this.creator, (HashMap)this.currentParentNS, this.staxIF, this.context.getEncHeaderContent());
/*     */ 
/*     */ 
/*     */         
/* 282 */         eventType = getSecurityElementType(message);
/*     */       }
/*     */     
/* 285 */     } catch (XMLStreamException xe) {
/* 286 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1608_ERROR_SECURITY_HEADER(), xe);
/* 287 */       throw new XWSSecurityException(LogStringsMessages.WSS_1608_ERROR_SECURITY_HEADER(), xe);
/* 288 */     } catch (XMLStreamBufferException xbe) {
/* 289 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1608_ERROR_SECURITY_HEADER(), (Throwable)xbe);
/* 290 */       throw new XWSSecurityException(LogStringsMessages.WSS_1608_ERROR_SECURITY_HEADER(), xbe);
/*     */     } 
/*     */     
/* 293 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isTimeStamp(XMLStreamReader reader) {
/* 303 */     if (reader.getLocalName() == "Timestamp" && reader.getNamespaceURI() == "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd") {
/* 304 */       return true;
/*     */     }
/* 306 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBST(XMLStreamReader reader) {
/* 315 */     if (reader.getLocalName() == "BinarySecurityToken" && reader.getNamespaceURI() == "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") {
/* 316 */       return true;
/*     */     }
/* 318 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSignature(XMLStreamReader reader) {
/* 326 */     if (reader.getLocalName() == "Signature" && reader.getNamespaceURI() == "http://www.w3.org/2000/09/xmldsig#") {
/* 327 */       return true;
/*     */     }
/* 329 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isEncryptedKey(XMLStreamReader reader) {
/* 337 */     if (reader.getLocalName() == "EncryptedKey" && reader.getNamespaceURI() == "http://www.w3.org/2001/04/xmlenc#") {
/* 338 */       return true;
/*     */     }
/* 340 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isEncryptedData(XMLStreamReader reader) {
/* 348 */     if (reader.getLocalName() == "EncryptedData" && reader.getNamespaceURI() == "http://www.w3.org/2001/04/xmlenc#") {
/* 349 */       return true;
/*     */     }
/* 351 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isUsernameToken(XMLStreamReader reader) {
/* 359 */     if (reader.getLocalName() == "UsernameToken" && reader.getNamespaceURI() == "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") {
/* 360 */       return true;
/*     */     }
/* 362 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isDerivedKey(XMLStreamReader reader) {
/* 370 */     if (reader.getLocalName() == "DerivedKeyToken" && reader.getNamespaceURI() == "http://schemas.xmlsoap.org/ws/2005/02/sc") {
/* 371 */       return true;
/*     */     }
/* 373 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSignatureConfirmation(XMLStreamReader reader) {
/* 381 */     if (reader.getLocalName() == "SignatureConfirmation" && reader.getNamespaceURI() == "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd") {
/* 382 */       return true;
/*     */     }
/* 384 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSCT(XMLStreamReader reader) {
/* 392 */     if (reader.getLocalName() == "SecurityContextToken" && reader.getNamespaceURI() == "http://schemas.xmlsoap.org/ws/2005/02/sc") {
/* 393 */       return true;
/*     */     }
/* 395 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSAML(XMLStreamReader message) {
/* 403 */     if (message.getLocalName() == "Assertion") {
/* 404 */       String uri = message.getNamespaceURI();
/* 405 */       if (uri == "urn:oasis:names:tc:SAML:2.0:assertion" || uri == "urn:oasis:names:tc:SAML:1.0:assertion" || uri == "urn:oasis:names:tc:SAML:1.0:assertion") {
/* 406 */         return true;
/*     */       }
/*     */     } 
/* 409 */     return false;
/*     */   }
/*     */   
/*     */   private void moveToNextElement(XMLStreamReader reader) throws XMLStreamException {
/* 413 */     reader.next();
/* 414 */     while (reader.getEventType() != 1) {
/* 415 */       reader.next();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSecurityElementType(XMLStreamReader reader) {
/* 424 */     if (isTimeStamp(reader)) {
/* 425 */       return 1;
/*     */     }
/*     */     
/* 428 */     if (isBST(reader)) {
/* 429 */       return 3;
/*     */     }
/*     */     
/* 432 */     if (isSignature(reader)) {
/* 433 */       return 6;
/*     */     }
/*     */     
/* 436 */     if (isEncryptedKey(reader)) {
/* 437 */       return 5;
/*     */     }
/*     */     
/* 440 */     if (isEncryptedData(reader)) {
/* 441 */       return 4;
/*     */     }
/*     */     
/* 444 */     if (isUsernameToken(reader)) {
/* 445 */       return 2;
/*     */     }
/*     */     
/* 448 */     if (isSignatureConfirmation(reader)) {
/* 449 */       return 9;
/*     */     }
/*     */     
/* 452 */     if (isDerivedKey(reader)) {
/* 453 */       this; return 8;
/*     */     } 
/*     */     
/* 456 */     if (isSCT(reader)) {
/* 457 */       this; return 10;
/*     */     } 
/* 459 */     if (isSAML(reader)) {
/* 460 */       this; return 11;
/*     */     } 
/* 462 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\processor\SecurityHeaderProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */