/*     */ package com.sun.xml.ws.security.opt.impl.enc;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.api.EncryptedKey;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.BuilderResult;
/*     */ import com.sun.xml.ws.security.opt.api.keyinfo.TokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.api.reference.Reference;
/*     */ import com.sun.xml.ws.security.opt.crypto.dsig.keyinfo.KeyInfo;
/*     */ import com.sun.xml.ws.security.opt.impl.JAXBFilterProcessingContext;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.DerivedKeyTokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.IssuedTokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.KerberosTokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.SCTBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.SamlTokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.SecurityTokenReference;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.SymmetricTokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.UsernameTokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.keyinfo.X509TokenBuilder;
/*     */ import com.sun.xml.ws.security.opt.impl.reference.DirectReference;
/*     */ import com.sun.xml.ws.security.opt.impl.util.NamespaceContextEx;
/*     */ import com.sun.xml.ws.security.opt.impl.util.WSSElementFactory;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.misc.SecurityUtil;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import com.sun.xml.wss.logging.impl.opt.crypto.LogStringsMessages;
/*     */ import java.security.Key;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TokenProcessor
/*     */ {
/*  84 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.crypto", "com.sun.xml.wss.logging.impl.opt.crypto.LogStrings");
/*     */ 
/*     */   
/*  87 */   private Key dataEncKey = null;
/*  88 */   private Key dkEncKey = null;
/*  89 */   private WSSElementFactory elementFactory = null;
/*  90 */   private WSSPolicy keyBinding = null;
/*  91 */   private JAXBFilterProcessingContext context = null;
/*  92 */   private EncryptionPolicy ep = null;
/*  93 */   private TokenBuilder builder = null;
/*     */   
/*  95 */   private EncryptedKey ek = null;
/*  96 */   private KeyInfo keyInfo = null;
/*     */   
/*     */   public TokenProcessor(EncryptionPolicy ep, JAXBFilterProcessingContext context) {
/*  99 */     this.context = context;
/* 100 */     this.ep = ep;
/* 101 */     this.keyBinding = (WSSPolicy)ep.getKeyBinding();
/* 102 */     this.elementFactory = new WSSElementFactory(context.getSOAPVersion());
/*     */   }
/*     */ 
/*     */   
/*     */   public Key getDataEncKey() {
/* 107 */     return this.dataEncKey;
/*     */   }
/*     */   
/*     */   public Key getKeyEncKey() {
/* 111 */     return this.dkEncKey;
/*     */   }
/*     */   
/*     */   public EncryptedKey getEncryptedKey() {
/* 115 */     return this.ek;
/*     */   }
/*     */   
/*     */   public KeyInfo getKeyInfo() {
/* 119 */     return this.keyInfo;
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
/*     */   public BuilderResult process() throws XWSSecurityException {
/* 131 */     String keyEncAlgo = "http://www.w3.org/2001/04/xmlenc#kw-aes256";
/*     */     
/* 133 */     String dataEncAlgo = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
/* 134 */     EncryptionPolicy.FeatureBinding featureBinding = (EncryptionPolicy.FeatureBinding)this.ep.getFeatureBinding();
/* 135 */     String tmp = featureBinding.getDataEncryptionAlgorithm();
/* 136 */     if (tmp == null || "".equals(tmp)) {
/* 137 */       if (this.context.getAlgorithmSuite() != null) {
/* 138 */         tmp = this.context.getAlgorithmSuite().getEncryptionAlgorithm();
/*     */       
/*     */       }
/* 141 */       else if (logger.isLoggable(Level.FINEST)) {
/* 142 */         logger.log(Level.FINEST, LogStringsMessages.WSS_1950_DATAENCRYPTION_ALGORITHM_NOTSET());
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 147 */     if (tmp != null && !"".equals(tmp)) {
/* 148 */       dataEncAlgo = tmp;
/*     */     }
/*     */     
/* 151 */     if (this.context.getAlgorithmSuite() != null) {
/* 152 */       keyEncAlgo = this.context.getAlgorithmSuite().getAsymmetricKeyAlgorithm();
/*     */     }
/* 154 */     if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)this.keyBinding)) {
/* 155 */       AuthenticationTokenPolicy.UsernameTokenBinding untBinding = null;
/* 156 */       if (this.context.getusernameTokenBinding() != null) {
/* 157 */         untBinding = this.context.getusernameTokenBinding();
/* 158 */         this.context.setUsernameTokenBinding(null);
/*     */       } else {
/* 160 */         untBinding = (AuthenticationTokenPolicy.UsernameTokenBinding)this.keyBinding;
/*     */       } 
/* 162 */       this.dataEncKey = untBinding.getSecretKey();
/* 163 */       this.builder = (TokenBuilder)new UsernameTokenBuilder(this.context, untBinding);
/* 164 */       BuilderResult untResult = this.builder.process();
/* 165 */       untResult.setDataProtectionKey(this.dataEncKey);
/* 166 */       return untResult;
/*     */     } 
/* 168 */     if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)this.keyBinding)) {
/* 169 */       AuthenticationTokenPolicy.X509CertificateBinding certificateBinding = null;
/* 170 */       if (this.context.getX509CertificateBinding() != null) {
/* 171 */         certificateBinding = this.context.getX509CertificateBinding();
/* 172 */         this.context.setX509CertificateBinding(null);
/*     */       } else {
/* 174 */         certificateBinding = (AuthenticationTokenPolicy.X509CertificateBinding)this.keyBinding;
/*     */       } 
/*     */       
/* 177 */       String x509TokenId = certificateBinding.getUUID();
/* 178 */       if (x509TokenId == null || x509TokenId.equals("")) {
/* 179 */         x509TokenId = this.context.generateID();
/*     */       }
/*     */       
/* 182 */       this.builder = (TokenBuilder)new X509TokenBuilder(this.context, certificateBinding);
/* 183 */       BuilderResult xtbResult = this.builder.process();
/* 184 */       KeyInfo ekKI = xtbResult.getKeyInfo();
/*     */       
/* 186 */       tmp = null;
/* 187 */       tmp = certificateBinding.getKeyAlgorithm();
/* 188 */       if (tmp != null && !tmp.equals("")) {
/* 189 */         keyEncAlgo = tmp;
/*     */       }
/*     */       
/* 192 */       this.dataEncKey = SecurityUtil.generateSymmetricKey(dataEncAlgo);
/*     */       
/* 194 */       this.dkEncKey = certificateBinding.getX509Certificate().getPublicKey();
/* 195 */       String ekId = this.context.generateID();
/* 196 */       this.ek = this.elementFactory.createEncryptedKey(ekId, keyEncAlgo, ekKI, this.dkEncKey, this.dataEncKey);
/* 197 */       this.context.getSecurityHeader().add((SecurityHeaderElement)this.ek);
/* 198 */       xtbResult.setKeyInfo(null);
/*     */       
/* 200 */       DirectReference dr = this.elementFactory.createDirectReference();
/* 201 */       dr.setURI("#" + ekId);
/* 202 */       boolean wss11Sender = "true".equals(this.context.getExtraneousProperty("EnableWSS11PolicySender"));
/* 203 */       if (wss11Sender) {
/* 204 */         dr.setValueType("http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey");
/*     */       }
/* 206 */       SecurityTokenReference str = this.elementFactory.createSecurityTokenReference((Reference)dr);
/* 207 */       this.keyInfo = this.elementFactory.createKeyInfo(str);
/*     */       
/* 209 */       xtbResult.setKeyInfo(this.keyInfo);
/* 210 */       xtbResult.setEncryptedKey(this.ek);
/* 211 */       xtbResult.setDataProtectionKey(this.dataEncKey);
/* 212 */       xtbResult.setKeyProtectionKey(this.dkEncKey);
/* 213 */       return xtbResult;
/*     */     } 
/* 215 */     if (PolicyTypeUtil.kerberosTokenBinding((SecurityPolicy)this.keyBinding)) {
/* 216 */       AuthenticationTokenPolicy.KerberosTokenBinding krbBinding = null;
/* 217 */       if (this.context.getKerberosTokenBinding() != null) {
/* 218 */         krbBinding = this.context.getKerberosTokenBinding();
/* 219 */         this.context.setKerberosTokenBinding(null);
/*     */       } else {
/* 221 */         krbBinding = (AuthenticationTokenPolicy.KerberosTokenBinding)this.keyBinding;
/*     */       } 
/* 223 */       this.dataEncKey = krbBinding.getSecretKey();
/* 224 */       this.builder = (TokenBuilder)new KerberosTokenBuilder(this.context, krbBinding);
/* 225 */       BuilderResult ktbResult = this.builder.process();
/* 226 */       ktbResult.setDataProtectionKey(this.dataEncKey);
/* 227 */       return ktbResult;
/*     */     } 
/* 229 */     if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)this.keyBinding)) {
/* 230 */       SymmetricKeyBinding skb = null;
/* 231 */       if (this.context.getSymmetricKeyBinding() != null) {
/* 232 */         skb = this.context.getSymmetricKeyBinding();
/* 233 */         this.context.setSymmetricKeyBinding(null);
/*     */       } else {
/* 235 */         skb = (SymmetricKeyBinding)this.keyBinding;
/*     */       } 
/* 237 */       this.builder = (TokenBuilder)new SymmetricTokenBuilder(skb, this.context, dataEncAlgo, keyEncAlgo);
/* 238 */       BuilderResult skbResult = this.builder.process();
/* 239 */       this.dataEncKey = skbResult.getDataProtectionKey();
/* 240 */       this.keyInfo = skbResult.getKeyInfo();
/* 241 */       return skbResult;
/*     */     } 
/* 243 */     if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)this.keyBinding)) {
/* 244 */       DerivedTokenKeyBinding dtk = (DerivedTokenKeyBinding)this.keyBinding;
/* 245 */       ((NamespaceContextEx)this.context.getNamespaceContext()).addSCNS();
/* 246 */       this.builder = (TokenBuilder)new DerivedKeyTokenBuilder(this.context, dtk);
/* 247 */       BuilderResult dtkResult = this.builder.process();
/*     */       
/* 249 */       return dtkResult;
/* 250 */     }  if (PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)this.keyBinding)) {
/* 251 */       ((NamespaceContextEx)this.context.getNamespaceContext()).addSCNS();
/* 252 */       SCTBuilder sctBuilder = new SCTBuilder(this.context, (SecureConversationTokenKeyBinding)this.keyBinding);
/* 253 */       BuilderResult sctResult = sctBuilder.process();
/* 254 */       return sctResult;
/* 255 */     }  if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)this.keyBinding)) {
/* 256 */       IssuedTokenBuilder itb = new IssuedTokenBuilder(this.context, (IssuedTokenKeyBinding)this.keyBinding);
/* 257 */       BuilderResult itbResult = itb.process();
/* 258 */       return itbResult;
/* 259 */     }  if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)this.keyBinding)) {
/* 260 */       ((NamespaceContextEx)this.context.getNamespaceContext()).addSAMLNS();
/* 261 */       SamlTokenBuilder stb = new SamlTokenBuilder(this.context, (AuthenticationTokenPolicy.SAMLAssertionBinding)this.keyBinding, false);
/* 262 */       return stb.process();
/*     */     } 
/* 264 */     logger.log(Level.SEVERE, LogStringsMessages.WSS_1903_UNSUPPORTED_KEYBINDING_ENCRYPTIONPOLICY(this.keyBinding));
/* 265 */     throw new UnsupportedOperationException("Unsupported Key Binding" + this.keyBinding);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\enc\TokenProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */