/*     */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.security.core.dsig.ObjectFactory;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BinarySecurityToken;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.Token;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.TokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyName;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyValue;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.RSAKeyValue;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.SSEData;
/*     */ import com.sun.xml.ws.security.opt.impl.outgoing.SecurityHeader;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.DirectReference;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.KeyIdentifier;
/*     */ import com.sun.xml.ws.security.opt.impl.tokens.UsernameToken;
/*     */ import com.sun.xml.ws.security.opt.impl.util.WSSElementFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.token.LogStringsMessages;
/*     */ import java.security.PublicKey;
/*     */ import java.security.cert.CertificateEncodingException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.crypto.dsig.keyinfo.KeyInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TokenBuilder
/*     */   implements TokenBuilder
/*     */ {
/*  83 */   protected static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.token", "com.sun.xml.wss.logging.impl.opt.token.LogStrings");
/*     */   
/*  85 */   protected JAXBFilterProcessingContext context = null;
/*  86 */   protected SecurityHeader securityHeader = null;
/*  87 */   protected WSSElementFactory elementFactory = null;
/*  88 */   protected KeyInfo keyInfo = null;
/*     */ 
/*     */   
/*     */   public TokenBuilder(JAXBFilterProcessingContext context) {
/*  92 */     this.context = context;
/*  93 */     this.securityHeader = context.getSecurityHeader();
/*  94 */     this.elementFactory = new WSSElementFactory(context.getSOAPVersion());
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
/*     */   protected BinarySecurityToken createBinarySecurityToken(AuthenticationTokenPolicy.X509CertificateBinding binding, X509Certificate x509Cert) throws XWSSecurityException {
/* 108 */     if (!AuthenticationTokenPolicy.X509CertificateBinding.INCLUDE_NEVER.equals(binding.getIncludeToken())) if (!AuthenticationTokenPolicy.X509CertificateBinding.INCLUDE_NEVER_VER2.equals(binding.getIncludeToken())) {
/*     */         BinarySecurityToken bst;
/*     */ 
/*     */         
/* 112 */         String id = getID((WSSPolicy)binding);
/*     */         
/* 114 */         if (logger.isLoggable(Level.FINEST)) {
/* 115 */           logger.log(Level.FINEST, "X509 Token id: " + id);
/*     */         }
/*     */         
/* 118 */         Token token = (Token)this.securityHeader.getChildElement(id);
/* 119 */         if (token != null) {
/* 120 */           if (token instanceof BinarySecurityToken) {
/* 121 */             return (BinarySecurityToken)token;
/*     */           }
/* 123 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1854_TWO_TOKENS_WITH_SAME_ID());
/* 124 */           throw new XWSSecurityException("Found two tokens with same Id attribute");
/*     */         } 
/*     */         
/*     */         try {
/* 128 */           bst = this.elementFactory.createBinarySecurityToken(id, x509Cert.getEncoded());
/* 129 */         } catch (CertificateEncodingException ex) {
/* 130 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1801_BST_CREATION_FAILED());
/* 131 */           throw new XWSSecurityException("Error occured while constructing BinarySecurityToken", ex);
/*     */         } 
/* 133 */         this.context.getSecurityHeader().add((SecurityHeaderElement)bst);
/* 134 */         return bst;
/*     */       } 
/*     */     
/*     */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected UsernameToken createUsernameToken(AuthenticationTokenPolicy.UsernameTokenBinding binding, UsernameToken unToken) throws XWSSecurityException {
/* 148 */     String id = getID((WSSPolicy)binding);
/* 149 */     if (logger.isLoggable(Level.FINEST)) {
/* 150 */       logger.log(Level.FINEST, "Username Token id: " + id);
/*     */     }
/* 152 */     SecurityHeaderElement token = this.securityHeader.getChildElement(id);
/* 153 */     if (token != null) {
/* 154 */       if (token instanceof UsernameToken) {
/* 155 */         return (UsernameToken)token;
/*     */       }
/* 157 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1854_TWO_TOKENS_WITH_SAME_ID());
/* 158 */       throw new XWSSecurityException("Found two tokens with same Id attribute");
/*     */     } 
/* 160 */     unToken.setId(id);
/* 161 */     this.context.getSecurityHeader().add((SecurityHeaderElement)unToken);
/* 162 */     return unToken;
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
/*     */   protected BinarySecurityToken createKerberosBST(AuthenticationTokenPolicy.KerberosTokenBinding binding, byte[] kerbToken) throws XWSSecurityException {
/* 177 */     if (!AuthenticationTokenPolicy.KerberosTokenBinding.INCLUDE_NEVER.equals(binding.getIncludeToken())) if (!AuthenticationTokenPolicy.KerberosTokenBinding.INCLUDE_NEVER_VER2.equals(binding.getIncludeToken())) {
/*     */ 
/*     */ 
/*     */         
/* 181 */         String id = getID((WSSPolicy)binding);
/*     */         
/* 183 */         if (logger.isLoggable(Level.FINEST)) {
/* 184 */           logger.log(Level.FINEST, "Kerberos Token id: " + id);
/*     */         }
/*     */         
/* 187 */         Token token = (Token)this.securityHeader.getChildElement(id);
/* 188 */         if (token != null) {
/* 189 */           if (token instanceof BinarySecurityToken) {
/* 190 */             return (BinarySecurityToken)token;
/*     */           }
/* 192 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1854_TWO_TOKENS_WITH_SAME_ID());
/* 193 */           throw new XWSSecurityException("Found two tokens with same Id attribute");
/*     */         } 
/*     */         
/* 196 */         BinarySecurityToken bst = this.elementFactory.createKerberosBinarySecurityToken(id, kerbToken);
/* 197 */         this.context.getSecurityHeader().add((SecurityHeaderElement)bst);
/* 198 */         return bst;
/*     */       } 
/*     */     
/*     */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SecurityTokenReference buildSTR(String strId, Reference ref) {
/* 210 */     SecurityTokenReference str = this.elementFactory.createSecurityTokenReference(ref);
/* 211 */     SecurityPolicy sp = this.context.getSecurityPolicy();
/* 212 */     if (sp instanceof com.sun.xml.wss.impl.policy.mls.SignaturePolicy || sp instanceof AuthenticationTokenPolicy) {
/* 213 */       str.setId(strId);
/*     */     }
/* 215 */     if (this.context.getWSSAssertion() != null && 
/* 216 */       ref instanceof DirectReference && this.context.getWSSAssertion().getType().equals("1.1")) {
/* 217 */       if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken".equals(((DirectReference)ref).getValueType())) {
/* 218 */         str.setTokenType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#UsernameToken");
/* 219 */       } else if ("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey".equals(((DirectReference)ref).getValueType())) {
/* 220 */         str.setTokenType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
/*     */       } 
/*     */     }
/*     */     
/* 224 */     SSEData sSEData = new SSEData((SecurityElement)str, false, this.context.getNamespaceContext());
/* 225 */     if (strId != null) {
/* 226 */       this.context.getElementCache().put(strId, sSEData);
/*     */     }
/* 228 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SecurityTokenReference buildSTR(Reference ref) {
/* 237 */     SecurityTokenReference str = this.elementFactory.createSecurityTokenReference(ref);
/* 238 */     return str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected KeyInfo buildKeyInfo(Reference ref, String strId) {
/* 249 */     this.keyInfo = this.elementFactory.createKeyInfo(buildSTR(strId, ref));
/* 250 */     return this.keyInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected KeyInfo buildKeyInfo(SecurityTokenReference str) {
/* 259 */     this.keyInfo = this.elementFactory.createKeyInfo(str);
/* 260 */     return this.keyInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected KeyInfo buildKeyInfo(PublicKey pubKey) {
/* 270 */     this.keyInfo = this.elementFactory.createKeyInfo(buildKeyValue(pubKey));
/* 271 */     return this.keyInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected KeyValue buildKeyValue(PublicKey pubKey) {
/* 281 */     KeyValue kv = new KeyValue();
/* 282 */     RSAKeyValue rsaKV = new RSAKeyValue(pubKey);
/* 283 */     JAXBElement je = (new ObjectFactory()).createRSAKeyValue(rsaKV);
/* 284 */     List<JAXBElement> strList = Collections.singletonList(je);
/* 285 */     kv.setContent(strList);
/* 286 */     return kv;
/*     */   }
/*     */   
/*     */   protected KeyInfo buildKIWithKeyName(String name) {
/* 290 */     KeyName kn = new KeyName();
/* 291 */     kn.setKeyName(name);
/* 292 */     this.keyInfo = this.elementFactory.createKeyInfo(kn);
/* 293 */     return this.keyInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DirectReference buildDirectReference(String id, String valueType) {
/* 303 */     DirectReference dr = this.elementFactory.createDirectReference();
/* 304 */     dr.setURI("#" + id);
/* 305 */     if (valueType != null) {
/* 306 */       dr.setValueType(valueType);
/*     */     }
/* 308 */     return dr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected KeyIdentifier buildKeyInfoWithKI(AuthenticationTokenPolicy.X509CertificateBinding binding, String refType) throws XWSSecurityException {
/* 319 */     KeyIdentifier keyIdentifier = this.elementFactory.createKeyIdentifier();
/*     */     
/* 321 */     keyIdentifier.setValueType(refType);
/* 322 */     keyIdentifier.updateReferenceValue(binding.getX509Certificate());
/* 323 */     keyIdentifier.setEncodingType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
/* 324 */     if (keyIdentifier.getValue() == null || keyIdentifier.getValue().length() == 0) {
/* 325 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1852_KEY_IDENTIFIER_EMPTY());
/* 326 */       throw new XWSSecurityException(LogStringsMessages.WSS_1852_KEY_IDENTIFIER_EMPTY());
/*     */     } 
/* 328 */     buildKeyInfo((Reference)keyIdentifier, binding.getSTRID());
/* 329 */     return keyIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected KeyIdentifier buildKeyInfoWithKIKerberos(AuthenticationTokenPolicy.KerberosTokenBinding binding, String refType) throws XWSSecurityException {
/* 340 */     KeyIdentifier keyIdentifier = this.elementFactory.createKeyIdentifier();
/* 341 */     keyIdentifier.setValueType(refType);
/* 342 */     keyIdentifier.updateReferenceValue(binding.getTokenValue());
/* 343 */     keyIdentifier.setEncodingType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
/* 344 */     if (keyIdentifier.getValue() == null || keyIdentifier.getValue().length() == 0) {
/* 345 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1852_KEY_IDENTIFIER_EMPTY());
/* 346 */       throw new XWSSecurityException(LogStringsMessages.WSS_1852_KEY_IDENTIFIER_EMPTY());
/*     */     } 
/* 348 */     buildKeyInfo((Reference)keyIdentifier, binding.getSTRID());
/* 349 */     return keyIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected KeyIdentifier buildKeyInfoWithEKSHA1(String ekSHA1Ref) {
/* 358 */     KeyIdentifier keyIdentifier = this.elementFactory.createKeyIdentifier();
/* 359 */     keyIdentifier.setValueType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKeySHA1");
/* 360 */     keyIdentifier.setEncodingType("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
/* 361 */     keyIdentifier.setReferenceValue(ekSHA1Ref);
/* 362 */     buildKeyInfo((Reference)keyIdentifier, null);
/* 363 */     return keyIdentifier;
/*     */   }
/*     */   
/*     */   protected String getID(WSSPolicy policy) {
/* 367 */     String id = policy.getUUID();
/* 368 */     if (id == null || id.length() == 0) {
/* 369 */       return this.context.generateID();
/*     */     }
/* 371 */     return id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public KeyInfo getKeyInfo() {
/* 379 */     return (KeyInfo)this.keyInfo;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\TokenBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */