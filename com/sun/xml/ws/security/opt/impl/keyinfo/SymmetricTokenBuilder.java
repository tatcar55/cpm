/*     */ package com.sun.xml.ws.security.opt.impl.keyinfo;
/*     */ 
/*     */ import com.sun.xml.ws.security.impl.PasswordDerivedKey;
/*     */ import com.sun.xml.ws.security.opt.api.EncryptedKey;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BuilderResult;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.enc.JAXBEncryptedKey;
/*     */ import com.sun.xml.ws.security.opt.impl.tokens.UsernameToken;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.misc.Base64;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.token.LogStringsMessages;
/*     */ import java.security.Key;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SymmetricTokenBuilder
/*     */   extends TokenBuilder
/*     */ {
/*  74 */   private Key dataProtectionKey = null;
/*  75 */   private Key keyProtectionKey = null;
/*  76 */   private SymmetricKeyBinding binding = null;
/*     */   
/*     */   private String dataProtectionAlg;
/*     */   private String keyProtectionAlg;
/*     */   private BuilderResult result;
/*     */   
/*     */   public SymmetricTokenBuilder(SymmetricKeyBinding binding, JAXBFilterProcessingContext context, String dpAlgo, String kpAlgo) {
/*  83 */     super(context);
/*  84 */     this.binding = binding;
/*  85 */     this.dataProtectionAlg = dpAlgo;
/*  86 */     this.keyProtectionAlg = kpAlgo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BuilderResult process() throws XWSSecurityException {
/*  97 */     boolean wss11Receiver = "true".equals(this.context.getExtraneousProperty("EnableWSS11PolicyReceiver"));
/*  98 */     boolean wss11Sender = "true".equals(this.context.getExtraneousProperty("EnableWSS11PolicySender"));
/*  99 */     boolean sendEKSHA1 = (wss11Receiver && wss11Sender);
/* 100 */     boolean wss10 = !wss11Sender;
/* 101 */     ((NamespaceContextEx)this.context.getNamespaceContext()).addEncryptionNS();
/* 102 */     if (sendEKSHA1 && 
/* 103 */       this.context.getExtraneousProperty("SecretKeyValue") == null) {
/* 104 */       sendEKSHA1 = false;
/*     */     }
/*     */     
/* 107 */     BuilderResult stbResult = new BuilderResult();
/* 108 */     WSSPolicy ckBinding = (WSSPolicy)this.binding.getKeyBinding();
/* 109 */     if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)ckBinding)) {
/* 110 */       if (sendEKSHA1) {
/* 111 */         String ekSha1Ref = (String)this.context.getExtraneousProperty("EKSHA1Value");
/* 112 */         buildKeyInfoWithEKSHA1(ekSha1Ref);
/* 113 */         this.dataProtectionKey = this.binding.getSecretKey();
/* 114 */         if (this.dataProtectionKey == null) {
/* 115 */           throw new XWSSecurityException("DataProtectionKey got from the  UsernameToken Binding is NULL");
/*     */         }
/* 117 */         stbResult.setKeyInfo(this.keyInfo);
/* 118 */         stbResult.setDataProtectionKey(this.dataProtectionKey);
/* 119 */       } else if (wss11Sender || wss10) {
/* 120 */         AuthenticationTokenPolicy.UsernameTokenBinding untBinding = null;
/* 121 */         this.dataProtectionKey = this.binding.getSecretKey();
/* 122 */         if (this.dataProtectionKey == null) {
/* 123 */           throw new XWSSecurityException("DataProtectionKey got from the  UsernameToken Binding is NULL");
/*     */         }
/* 125 */         if (this.context.getusernameTokenBinding() != null) {
/* 126 */           untBinding = this.context.getusernameTokenBinding();
/* 127 */           untBinding.setReferenceType("Direct");
/*     */         } else {
/* 129 */           throw new XWSSecurityException("Internal error: UsernameToken Binding not set on context");
/*     */         } 
/* 131 */         UsernameToken unt = untBinding.getUsernameToken();
/* 132 */         String unTokenId = untBinding.getUUID();
/* 133 */         if (unTokenId == null || unTokenId.equals("")) {
/* 134 */           unTokenId = this.context.generateID();
/*     */         }
/* 136 */         if (logger.isLoggable(Level.FINEST)) {
/* 137 */           logger.log(Level.FINEST, "UsernameToken for SymmetricBinding is: " + unt);
/* 138 */           logger.log(Level.FINEST, "Token ID for SymmetricBinding is: " + unTokenId);
/*     */         } 
/* 140 */         SecurityHeaderElement ek = null;
/* 141 */         HashMap<String, String> ekCache = this.context.getEncryptedKeyCache();
/* 142 */         String ekId = (String)ekCache.get(unTokenId);
/* 143 */         this.keyProtectionKey = untBinding.getSecretKey();
/* 144 */         if (ekId == null) {
/* 145 */           TokenBuilder builder = new UsernameTokenBuilder(this.context, untBinding);
/* 146 */           this.result = builder.process();
/* 147 */           KeyInfo ekKI = this.result.getKeyInfo();
/* 148 */           this.context.setExtraneousProperty("SecretKey", this.dataProtectionKey);
/*     */           
/* 150 */           byte[] secretKey = untBinding.getSecretKey().getEncoded();
/* 151 */           PasswordDerivedKey pdk = new PasswordDerivedKey();
/* 152 */           Key dpKey = pdk.generate16ByteKeyforEncryption(secretKey);
/* 153 */           ek = (SecurityHeaderElement)this.elementFactory.createEncryptedKey(this.context.generateID(), this.context.getAlgorithmSuite().getSymmetricKeyAlgorithm(), ekKI, dpKey, this.dataProtectionKey);
/* 154 */           this.context.getSecurityHeader().add(ek);
/* 155 */           ekId = ek.getId();
/* 156 */           ekCache.put(unTokenId, ekId);
/* 157 */           this.context.addToCurrentSecretMap(ekId, this.dataProtectionKey);
/*     */           try {
/* 159 */             byte[] cipherVal = ((JAXBEncryptedKey)ek).getCipherValue();
/* 160 */             byte[] ekSha1 = MessageDigest.getInstance("SHA-1").digest(cipherVal);
/*     */             
/* 162 */             String encEkSha1 = Base64.encode(ekSha1);
/* 163 */             this.context.setExtraneousProperty("EncryptedKeySHA1", encEkSha1);
/* 164 */           } catch (NoSuchAlgorithmException nsa) {
/* 165 */             throw new XWSSecurityException(nsa);
/*     */           } 
/*     */         } else {
/* 168 */           if (ekId == null || ekId.length() == 0) {
/* 169 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1804_WRONG_ENCRYPTED_KEY());
/* 170 */             throw new XWSSecurityException("Invalid EncryptedKey Id ");
/*     */           } 
/* 172 */           this.dataProtectionKey = this.context.getCurrentSecretFromMap(ekId);
/*     */         } 
/* 174 */         String valType = null;
/* 175 */         if (wss11Sender) {
/* 176 */           valType = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey";
/*     */         }
/* 178 */         SecurityTokenReference str = buildSTR(untBinding.getUUID(), (Reference)buildDirectReference(ekId, valType));
/*     */         
/* 180 */         buildKeyInfo((SecurityTokenReference)str);
/* 181 */         stbResult.setDataProtectionKey(this.dataProtectionKey);
/* 182 */         stbResult.setKeyInfo(this.keyInfo);
/* 183 */         stbResult.setEncryptedKey((EncryptedKey)ek);
/*     */       } 
/* 185 */     } else if (!PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)ckBinding)) {
/* 186 */       if (!this.binding.getKeyIdentifier().equals(MessageConstants._EMPTY)) {
/* 187 */         if (this.keyProtectionAlg != null && !"".equals(this.keyProtectionAlg)) {
/* 188 */           this.dataProtectionKey = SecurityUtil.generateSymmetricKey(this.dataProtectionAlg);
/*     */         }
/*     */         
/* 191 */         this.keyProtectionKey = this.binding.getSecretKey();
/* 192 */         if (this.dataProtectionKey == null) {
/* 193 */           this.dataProtectionKey = this.keyProtectionKey;
/* 194 */           this.keyProtectionKey = null;
/* 195 */           buildKIWithKeyName(this.binding.getKeyIdentifier());
/*     */         } 
/* 197 */         stbResult.setKeyInfo(this.keyInfo);
/* 198 */         stbResult.setDataProtectionKey(this.dataProtectionKey);
/* 199 */       } else if (sendEKSHA1) {
/*     */         
/* 201 */         String ekSha1Ref = (String)this.context.getExtraneousProperty("EKSHA1Value");
/* 202 */         buildKeyInfoWithEKSHA1(ekSha1Ref);
/* 203 */         this.dataProtectionKey = this.binding.getSecretKey();
/* 204 */         stbResult.setKeyInfo(this.keyInfo);
/* 205 */         stbResult.setDataProtectionKey(this.dataProtectionKey);
/* 206 */       } else if (wss11Sender || wss10) {
/* 207 */         this.dataProtectionKey = this.binding.getSecretKey();
/*     */         
/* 209 */         AuthenticationTokenPolicy.X509CertificateBinding certificateBinding = null;
/* 210 */         if (!this.binding.getCertAlias().equals(MessageConstants._EMPTY)) {
/* 211 */           certificateBinding = new AuthenticationTokenPolicy.X509CertificateBinding();
/*     */           
/* 213 */           certificateBinding.setCertificateIdentifier(this.binding.getCertAlias());
/* 214 */           X509Certificate x509Certificate = this.context.getSecurityEnvironment().getCertificate(this.context.getExtraneousProperties(), certificateBinding.getCertificateIdentifier(), false);
/* 215 */           certificateBinding.setX509Certificate(x509Certificate);
/* 216 */           certificateBinding.setReferenceType("Direct");
/* 217 */         } else if (this.context.getX509CertificateBinding() != null) {
/* 218 */           certificateBinding = this.context.getX509CertificateBinding();
/* 219 */           this.context.setX509CertificateBinding(null);
/*     */         } else {
/* 221 */           throw new XWSSecurityException("Internal Error: X509CertificateBinding not set on context");
/*     */         } 
/*     */         
/* 224 */         X509Certificate x509Cert = certificateBinding.getX509Certificate();
/* 225 */         String x509TokenId = certificateBinding.getUUID();
/* 226 */         if (x509TokenId == null || x509TokenId.equals("")) {
/* 227 */           x509TokenId = this.context.generateID();
/*     */         }
/*     */         
/* 230 */         SecurityUtil.checkIncludeTokenPolicyOpt(this.context, certificateBinding, x509TokenId);
/*     */         
/* 232 */         if (logger.isLoggable(Level.FINEST)) {
/* 233 */           logger.log(Level.FINEST, "Certificate for SymmetricBinding is: " + x509Cert);
/* 234 */           logger.log(Level.FINEST, "BinaryToken ID for SymmetricBinding is: " + x509TokenId);
/*     */         } 
/* 236 */         BinarySecurityToken bst = null;
/* 237 */         SecurityHeaderElement ek = null;
/*     */         
/* 239 */         HashMap<String, String> ekCache = this.context.getEncryptedKeyCache();
/* 240 */         String ekId = (String)ekCache.get(x509TokenId);
/*     */         
/* 242 */         this.keyProtectionKey = x509Cert.getPublicKey();
/* 243 */         if (ekId == null) {
/*     */           
/* 245 */           TokenBuilder builder = new X509TokenBuilder(this.context, certificateBinding);
/* 246 */           BuilderResult bResult = builder.process();
/* 247 */           KeyInfo ekKI = bResult.getKeyInfo();
/* 248 */           this.context.setExtraneousProperty("SecretKey", this.dataProtectionKey);
/* 249 */           ek = (SecurityHeaderElement)this.elementFactory.createEncryptedKey(this.context.generateID(), this.keyProtectionAlg, ekKI, this.keyProtectionKey, this.dataProtectionKey);
/* 250 */           this.context.getSecurityHeader().add(ek);
/* 251 */           ekId = ek.getId();
/* 252 */           ekCache.put(x509TokenId, ekId);
/* 253 */           this.context.addToCurrentSecretMap(ekId, this.dataProtectionKey);
/* 254 */           stbResult.setEncryptedKey((EncryptedKey)ek);
/*     */           
/*     */           try {
/* 257 */             byte[] cipherVal = ((JAXBEncryptedKey)ek).getCipherValue();
/* 258 */             byte[] ekSha1 = MessageDigest.getInstance("SHA-1").digest(cipherVal);
/* 259 */             String encEkSha1 = Base64.encode(ekSha1);
/* 260 */             this.context.setExtraneousProperty("EncryptedKeySHA1", encEkSha1);
/* 261 */           } catch (NoSuchAlgorithmException nsa) {
/* 262 */             throw new XWSSecurityException(nsa);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 267 */           if (ekId == null || ekId.length() == 0) {
/* 268 */             logger.log(Level.SEVERE, LogStringsMessages.WSS_1804_WRONG_ENCRYPTED_KEY());
/* 269 */             throw new XWSSecurityException("Invalid EncryptedKey Id ");
/*     */           } 
/* 271 */           this.dataProtectionKey = this.context.getCurrentSecretFromMap(ekId);
/*     */         } 
/* 273 */         String valType = null;
/* 274 */         if (wss11Sender) {
/* 275 */           valType = "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey";
/*     */         }
/* 277 */         SecurityTokenReference str = buildSTR(certificateBinding.getUUID(), (Reference)buildDirectReference(ekId, valType));
/*     */         
/* 279 */         buildKeyInfo((SecurityTokenReference)str);
/* 280 */         stbResult.setDataProtectionKey(this.dataProtectionKey);
/* 281 */         stbResult.setKeyInfo(this.keyInfo);
/* 282 */         stbResult.setEncryptedKey((EncryptedKey)ek);
/*     */       } 
/*     */     } else {
/* 285 */       AuthenticationTokenPolicy.KerberosTokenBinding krbBinding = null;
/* 286 */       if (this.context.getKerberosTokenBinding() != null) {
/* 287 */         krbBinding = this.context.getKerberosTokenBinding();
/* 288 */         this.context.setKerberosTokenBinding(null);
/*     */         
/* 290 */         this.dataProtectionKey = krbBinding.getSecretKey();
/* 291 */         TokenBuilder builder = new KerberosTokenBuilder(this.context, krbBinding);
/* 292 */         stbResult = builder.process();
/* 293 */         stbResult.setDataProtectionKey(this.dataProtectionKey);
/*     */       } else {
/* 295 */         throw new XWSSecurityException("Internal error: Kerberos Binding not set on context");
/*     */       } 
/*     */     } 
/* 298 */     return stbResult;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\keyinfo\SymmetricTokenBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */