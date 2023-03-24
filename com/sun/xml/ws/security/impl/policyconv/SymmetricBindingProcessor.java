/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.security.addressing.policy.Address;
/*     */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*     */ import com.sun.xml.ws.security.impl.policy.UsernameToken;
/*     */ import com.sun.xml.ws.security.policy.Binding;
/*     */ import com.sun.xml.ws.security.policy.EncryptedElements;
/*     */ import com.sun.xml.ws.security.policy.EncryptedParts;
/*     */ import com.sun.xml.ws.security.policy.IssuedToken;
/*     */ import com.sun.xml.ws.security.policy.KerberosToken;
/*     */ import com.sun.xml.ws.security.policy.SamlToken;
/*     */ import com.sun.xml.ws.security.policy.SecureConversationToken;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.SignedElements;
/*     */ import com.sun.xml.ws.security.policy.SignedParts;
/*     */ import com.sun.xml.ws.security.policy.SymmetricBinding;
/*     */ import com.sun.xml.ws.security.policy.Token;
/*     */ import com.sun.xml.ws.security.policy.X509Token;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.KeyBindingBase;
/*     */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SymmetricKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.TimestampPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SymmetricBindingProcessor
/*     */   extends BindingProcessor
/*     */ {
/*  77 */   private SymmetricBinding binding = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SymmetricBindingProcessor(SymmetricBinding binding, XWSSPolicyContainer container, boolean isServer, boolean isIncoming, Vector<SignedParts> signedParts, Vector<EncryptedParts> encryptedParts, Vector<SignedElements> signedElements, Vector<EncryptedElements> encryptedElements) {
/*  83 */     this.binding = binding;
/*  84 */     this.container = container;
/*  85 */     this.isServer = isServer;
/*  86 */     this.isIncoming = isIncoming;
/*  87 */     this.protectionOrder = binding.getProtectionOrder();
/*  88 */     this.tokenProcessor = new TokenProcessor(isServer, isIncoming, this.pid);
/*  89 */     this.iAP = new IntegrityAssertionProcessor(binding.getAlgorithmSuite(), binding.isSignContent());
/*  90 */     this.eAP = new EncryptionAssertionProcessor(binding.getAlgorithmSuite(), false);
/*  91 */     this.signedParts = signedParts;
/*  92 */     this.signedElements = signedElements;
/*  93 */     this.encryptedElements = encryptedElements;
/*  94 */     this.encryptedParts = encryptedParts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void process() throws PolicyException {
/* 101 */     Token pt = this.binding.getProtectionToken();
/* 102 */     Token st = null;
/* 103 */     Token et = null;
/*     */     
/* 105 */     if (pt == null) {
/* 106 */       st = this.binding.getSignatureToken();
/* 107 */       et = this.binding.getEncryptionToken();
/*     */       
/* 109 */       if (et != null) {
/* 110 */         this.primaryEP = new EncryptionPolicy();
/* 111 */         this.primaryEP.setUUID(this.pid.generateID());
/* 112 */         addSymmetricKeyBinding((WSSPolicy)this.primaryEP, et);
/*     */       } 
/*     */       
/* 115 */       if (st != null) {
/* 116 */         this.primarySP = new SignaturePolicy();
/* 117 */         this.primarySP.setUUID(this.pid.generateID());
/*     */         
/* 119 */         SignaturePolicy.FeatureBinding spFB = (SignaturePolicy.FeatureBinding)this.primarySP.getFeatureBinding();
/*     */ 
/*     */         
/* 122 */         SecurityPolicyUtil.setCanonicalizationMethod(spFB, this.binding.getAlgorithmSuite());
/* 123 */         spFB.isPrimarySignature(true);
/* 124 */         addSymmetricKeyBinding((WSSPolicy)this.primarySP, st);
/*     */       } 
/*     */     } else {
/* 127 */       this.primarySP = new SignaturePolicy();
/* 128 */       this.primarySP.setUUID(this.pid.generateID());
/* 129 */       this.primaryEP = new EncryptionPolicy();
/* 130 */       this.primaryEP.setUUID(this.pid.generateID());
/* 131 */       PolicyAssertion tokenAssertion = (PolicyAssertion)pt;
/* 132 */       SecurityPolicyVersion spVersion = SecurityPolicyUtil.getSPVersion(tokenAssertion);
/* 133 */       addSymmetricKeyBinding((WSSPolicy)this.primarySP, pt);
/* 134 */       addSymmetricKeyBinding((WSSPolicy)this.primaryEP, pt);
/*     */       
/* 136 */       if (PolicyUtil.isUsernameToken(tokenAssertion, spVersion) && (!PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)this.primarySP.getKeyBinding()) || !PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)this.primaryEP.getKeyBinding()))) {
/* 137 */         this.primaryEP.setKeyBinding(this.primarySP.getKeyBinding());
/*     */       }
/* 139 */       SignaturePolicy.FeatureBinding spFB = (SignaturePolicy.FeatureBinding)this.primarySP.getFeatureBinding();
/*     */ 
/*     */       
/* 142 */       SecurityPolicyUtil.setCanonicalizationMethod(spFB, this.binding.getAlgorithmSuite());
/* 143 */       spFB.isPrimarySignature(true);
/*     */     } 
/*     */     
/* 146 */     if (this.protectionOrder == "SignBeforeEncrypting") {
/* 147 */       this.container.insert((SecurityPolicy)this.primarySP);
/*     */     } else {
/*     */       
/* 150 */       this.container.insert((SecurityPolicy)this.primaryEP);
/* 151 */       this.container.insert((SecurityPolicy)this.primarySP);
/* 152 */       if (this.primaryEP != null) {
/* 153 */         EncryptionPolicy.FeatureBinding efp = (EncryptionPolicy.FeatureBinding)this.primaryEP.getFeatureBinding();
/* 154 */         efp.setUseStandAloneRefList(true);
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     addPrimaryTargets();
/*     */ 
/*     */     
/* 161 */     if (this.foundEncryptTargets && this.binding.getSignatureProtection()) {
/* 162 */       protectPrimarySignature();
/*     */     }
/* 164 */     if (this.binding.isIncludeTimeStamp()) {
/* 165 */       TimestampPolicy tp = new TimestampPolicy();
/* 166 */       tp.setUUID(this.pid.generateID());
/* 167 */       this.container.insert((SecurityPolicy)tp);
/* 168 */       if (!this.binding.isDisableTimestampSigning()) {
/* 169 */         protectTimestamp(tp);
/*     */       }
/*     */     } 
/* 172 */     if (this.binding.getTokenProtection() && ((
/* 173 */       this.isServer && this.isIncoming) || (!this.isServer && !this.isIncoming))) {
/* 174 */       WSSPolicy policy = (WSSPolicy)this.primarySP.getKeyBinding();
/* 175 */       if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)policy)) {
/* 176 */         protectToken(policy, true);
/*     */       } else {
/* 178 */         protectToken((WSSPolicy)policy.getKeyBinding(), true);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addSymmetricKeyBinding(WSSPolicy policy, Token token) throws PolicyException {
/* 185 */     SymmetricKeyBinding skb = new SymmetricKeyBinding();
/*     */ 
/*     */ 
/*     */     
/* 189 */     PolicyAssertion tokenAssertion = (PolicyAssertion)token;
/* 190 */     SecurityPolicyVersion spVersion = SecurityPolicyUtil.getSPVersion(tokenAssertion);
/* 191 */     if (PolicyUtil.isX509Token(tokenAssertion, spVersion)) {
/* 192 */       AuthenticationTokenPolicy.X509CertificateBinding x509CB = new AuthenticationTokenPolicy.X509CertificateBinding();
/*     */       
/* 194 */       X509Token x509Token = (X509Token)tokenAssertion;
/* 195 */       x509CB.setUUID(token.getTokenId());
/* 196 */       this.tokenProcessor.setTokenValueType(x509CB, tokenAssertion);
/* 197 */       this.tokenProcessor.setTokenInclusion((KeyBindingBase)x509CB, (Token)tokenAssertion);
/*     */       
/* 199 */       this.tokenProcessor.setX509TokenRefType(x509CB, x509Token);
/*     */       
/* 201 */       if (x509Token.getIssuer() != null) {
/* 202 */         Address addr = x509Token.getIssuer().getAddress();
/* 203 */         if (addr != null)
/* 204 */           x509CB.setIssuer(addr.getURI().toString()); 
/* 205 */       } else if (x509Token.getIssuerName() != null) {
/* 206 */         x509CB.setIssuer(x509Token.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 209 */       if (x509Token.getClaims() != null) {
/* 210 */         x509CB.setClaims(x509Token.getClaims().getClaimsAsBytes());
/*     */       }
/*     */       
/* 213 */       if (x509Token.isRequireDerivedKeys()) {
/* 214 */         DerivedTokenKeyBinding dtKB = new DerivedTokenKeyBinding();
/* 215 */         skb.setKeyBinding((MLSPolicy)x509CB);
/* 216 */         policy.setKeyBinding((MLSPolicy)dtKB);
/* 217 */         dtKB.setOriginalKeyBinding((WSSPolicy)skb);
/* 218 */         dtKB.setUUID(this.pid.generateID());
/*     */       } else {
/* 220 */         skb.setKeyBinding((MLSPolicy)x509CB);
/* 221 */         policy.setKeyBinding((MLSPolicy)skb);
/*     */       } 
/* 223 */     } else if (PolicyUtil.isKerberosToken(tokenAssertion, spVersion)) {
/* 224 */       AuthenticationTokenPolicy.KerberosTokenBinding kerberosBinding = new AuthenticationTokenPolicy.KerberosTokenBinding();
/*     */       
/* 226 */       kerberosBinding.setUUID(token.getTokenId());
/* 227 */       KerberosToken kerberosToken = (KerberosToken)tokenAssertion;
/* 228 */       this.tokenProcessor.setTokenValueType(kerberosBinding, tokenAssertion);
/* 229 */       this.tokenProcessor.setTokenInclusion((KeyBindingBase)kerberosBinding, (Token)tokenAssertion);
/* 230 */       this.tokenProcessor.setKerberosTokenRefType(kerberosBinding, kerberosToken);
/*     */       
/* 232 */       if (kerberosToken.getIssuer() != null) {
/* 233 */         Address addr = kerberosToken.getIssuer().getAddress();
/* 234 */         if (addr != null)
/* 235 */           kerberosBinding.setIssuer(addr.getURI().toString()); 
/* 236 */       } else if (kerberosToken.getIssuerName() != null) {
/* 237 */         kerberosBinding.setIssuer(kerberosToken.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 240 */       if (kerberosToken.getClaims() != null) {
/* 241 */         kerberosBinding.setClaims(kerberosToken.getClaims().getClaimsAsBytes());
/*     */       }
/*     */       
/* 244 */       if (kerberosToken.isRequireDerivedKeys()) {
/* 245 */         DerivedTokenKeyBinding dtKB = new DerivedTokenKeyBinding();
/* 246 */         skb.setKeyBinding((MLSPolicy)kerberosBinding);
/* 247 */         policy.setKeyBinding((MLSPolicy)dtKB);
/* 248 */         dtKB.setOriginalKeyBinding((WSSPolicy)skb);
/* 249 */         dtKB.setUUID(this.pid.generateID());
/*     */       } else {
/* 251 */         skb.setKeyBinding((MLSPolicy)kerberosBinding);
/* 252 */         policy.setKeyBinding((MLSPolicy)skb);
/*     */       } 
/* 254 */     } else if (PolicyUtil.isSamlToken(tokenAssertion, spVersion)) {
/* 255 */       AuthenticationTokenPolicy.SAMLAssertionBinding sab = new AuthenticationTokenPolicy.SAMLAssertionBinding();
/* 256 */       SamlToken samlToken = (SamlToken)tokenAssertion;
/* 257 */       sab.setUUID(token.getTokenId());
/* 258 */       sab.setReferenceType("Direct");
/* 259 */       this.tokenProcessor.setTokenInclusion((KeyBindingBase)sab, (Token)tokenAssertion);
/*     */ 
/*     */       
/* 262 */       if (samlToken.getIssuer() != null) {
/* 263 */         Address addr = samlToken.getIssuer().getAddress();
/* 264 */         if (addr != null)
/* 265 */           sab.setIssuer(addr.getURI().toString()); 
/* 266 */       } else if (samlToken.getIssuerName() != null) {
/* 267 */         sab.setIssuer(samlToken.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 270 */       if (samlToken.getClaims() != null) {
/* 271 */         sab.setClaims(samlToken.getClaims().getClaimsAsBytes());
/*     */       }
/*     */       
/* 274 */       if (samlToken.isRequireDerivedKeys()) {
/* 275 */         DerivedTokenKeyBinding dtKB = new DerivedTokenKeyBinding();
/* 276 */         dtKB.setOriginalKeyBinding((WSSPolicy)sab);
/* 277 */         policy.setKeyBinding((MLSPolicy)dtKB);
/* 278 */         dtKB.setUUID(this.pid.generateID());
/*     */       } else {
/* 280 */         policy.setKeyBinding((MLSPolicy)sab);
/*     */       } 
/* 282 */     } else if (PolicyUtil.isIssuedToken(tokenAssertion, spVersion)) {
/* 283 */       IssuedTokenKeyBinding itkb = new IssuedTokenKeyBinding();
/* 284 */       this.tokenProcessor.setTokenInclusion((KeyBindingBase)itkb, (Token)tokenAssertion);
/*     */       
/* 286 */       itkb.setUUID(((Token)tokenAssertion).getTokenId());
/* 287 */       IssuedToken it = (IssuedToken)tokenAssertion;
/*     */       
/* 289 */       if (it.getIssuer() != null) {
/* 290 */         Address addr = it.getIssuer().getAddress();
/* 291 */         if (addr != null)
/* 292 */           itkb.setIssuer(addr.getURI().toString()); 
/* 293 */       } else if (it.getIssuerName() != null) {
/* 294 */         itkb.setIssuer(it.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 297 */       if (it.getClaims() != null) {
/* 298 */         itkb.setClaims(it.getClaims().getClaimsAsBytes());
/*     */       }
/*     */       
/* 301 */       if (it.isRequireDerivedKeys()) {
/* 302 */         DerivedTokenKeyBinding dtKB = new DerivedTokenKeyBinding();
/* 303 */         dtKB.setOriginalKeyBinding((WSSPolicy)itkb);
/* 304 */         policy.setKeyBinding((MLSPolicy)dtKB);
/* 305 */         dtKB.setUUID(this.pid.generateID());
/*     */       } else {
/* 307 */         policy.setKeyBinding((MLSPolicy)itkb);
/*     */       } 
/* 309 */     } else if (PolicyUtil.isSecureConversationToken(tokenAssertion, spVersion)) {
/* 310 */       SecureConversationTokenKeyBinding sct = new SecureConversationTokenKeyBinding();
/* 311 */       SecureConversationToken sctPolicy = (SecureConversationToken)tokenAssertion;
/*     */       
/* 313 */       if (sctPolicy.getIssuer() != null) {
/* 314 */         Address addr = sctPolicy.getIssuer().getAddress();
/* 315 */         if (addr != null)
/* 316 */           sct.setIssuer(addr.getURI().toString()); 
/* 317 */       } else if (sctPolicy.getIssuerName() != null) {
/* 318 */         sct.setIssuer(sctPolicy.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 321 */       if (sctPolicy.getClaims() != null) {
/* 322 */         sct.setClaims(sctPolicy.getClaims().getClaimsAsBytes());
/*     */       }
/*     */       
/* 325 */       if (sctPolicy.isRequireDerivedKeys()) {
/* 326 */         DerivedTokenKeyBinding dtKB = new DerivedTokenKeyBinding();
/* 327 */         dtKB.setOriginalKeyBinding((WSSPolicy)sct);
/* 328 */         policy.setKeyBinding((MLSPolicy)dtKB);
/* 329 */         dtKB.setUUID(this.pid.generateID());
/*     */       } else {
/* 331 */         policy.setKeyBinding((MLSPolicy)sct);
/*     */       } 
/* 333 */       this.tokenProcessor.setTokenInclusion((KeyBindingBase)sct, (Token)tokenAssertion);
/*     */       
/* 335 */       sct.setUUID(((Token)tokenAssertion).getTokenId());
/* 336 */     } else if (PolicyUtil.isUsernameToken(tokenAssertion, spVersion)) {
/* 337 */       AuthenticationTokenPolicy.UsernameTokenBinding utb = new AuthenticationTokenPolicy.UsernameTokenBinding();
/* 338 */       UsernameToken unt = (UsernameToken)tokenAssertion;
/* 339 */       utb.setUUID(token.getTokenId());
/* 340 */       utb.setReferenceType("Direct");
/* 341 */       this.tokenProcessor.setTokenValueType(utb, tokenAssertion);
/* 342 */       this.tokenProcessor.setTokenInclusion((KeyBindingBase)utb, (Token)tokenAssertion);
/* 343 */       this.tokenProcessor.setUsernameTokenRefType(utb, unt);
/* 344 */       if (unt.getIssuer() != null) {
/* 345 */         Address addr = unt.getIssuer().getAddress();
/* 346 */         if (addr != null)
/* 347 */           utb.setIssuer(addr.getURI().toString()); 
/* 348 */       } else if (unt.getIssuerName() != null) {
/* 349 */         utb.setIssuer(unt.getIssuerName().getIssuerName());
/*     */       } 
/*     */       
/* 352 */       if (unt.getClaims() != null) {
/* 353 */         utb.setClaims(unt.getClaims().getClaimsAsBytes());
/*     */       }
/* 355 */       utb.setUseCreated(unt.useCreated());
/* 356 */       utb.setUseNonce(unt.useNonce());
/*     */       
/* 358 */       if (unt.isRequireDerivedKeys()) {
/* 359 */         DerivedTokenKeyBinding dtKB = new DerivedTokenKeyBinding();
/* 360 */         skb.setKeyBinding((MLSPolicy)utb);
/* 361 */         policy.setKeyBinding((MLSPolicy)dtKB);
/* 362 */         dtKB.setOriginalKeyBinding((WSSPolicy)skb);
/* 363 */         dtKB.setUUID(this.pid.generateID());
/*     */       } else {
/* 365 */         skb.setKeyBinding((MLSPolicy)utb);
/* 366 */         policy.setKeyBinding((MLSPolicy)skb);
/*     */       } 
/*     */     } else {
/*     */       
/* 370 */       throw new UnsupportedOperationException("addKeyBinding for " + token + "is not supported");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Binding getBinding() {
/* 375 */     return (Binding)this.binding;
/*     */   }
/*     */   
/*     */   protected EncryptionPolicy getSecondaryEncryptionPolicy() throws PolicyException {
/* 379 */     if (this.sEncPolicy == null) {
/* 380 */       this.sEncPolicy = new EncryptionPolicy();
/* 381 */       this.sEncPolicy.setUUID(this.pid.generateID());
/* 382 */       Token token = null;
/* 383 */       token = this.binding.getProtectionToken();
/* 384 */       if (token == null) {
/* 385 */         token = this.binding.getEncryptionToken();
/*     */       }
/* 387 */       addSymmetricKeyBinding((WSSPolicy)this.sEncPolicy, token);
/* 388 */       this.container.insert((SecurityPolicy)this.sEncPolicy);
/*     */     } 
/* 390 */     return this.sEncPolicy;
/*     */   }
/*     */   
/*     */   protected void close() {
/* 394 */     if (this.protectionOrder == "SignBeforeEncrypting")
/* 395 */       this.container.insert((SecurityPolicy)this.primaryEP); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\SymmetricBindingProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */