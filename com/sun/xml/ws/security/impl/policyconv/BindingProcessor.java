/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.security.policy.Binding;
/*     */ import com.sun.xml.ws.security.policy.EncryptedElements;
/*     */ import com.sun.xml.ws.security.policy.EncryptedParts;
/*     */ import com.sun.xml.ws.security.policy.EncryptedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.EndorsingEncryptedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.EndorsingSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SignedElements;
/*     */ import com.sun.xml.ws.security.policy.SignedEncryptedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SignedEndorsingEncryptedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SignedEndorsingSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SignedParts;
/*     */ import com.sun.xml.ws.security.policy.SignedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.WSSAssertion;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.DerivedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.KeyBindingBase;
/*     */ import com.sun.xml.wss.impl.policy.mls.SecureConversationTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import com.sun.xml.wss.impl.policy.mls.TimestampPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import java.util.Vector;
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
/*     */ public abstract class BindingProcessor
/*     */ {
/*  82 */   protected String protectionOrder = "SignBeforeEncrypting";
/*     */   protected boolean isServer = false;
/*     */   protected boolean isIncoming = false;
/*  85 */   protected SignaturePolicy primarySP = null;
/*  86 */   protected EncryptionPolicy primaryEP = null;
/*     */   
/*  88 */   protected EncryptionPolicy sEncPolicy = null;
/*  89 */   protected SignaturePolicy sSigPolicy = null;
/*  90 */   protected XWSSPolicyContainer container = null;
/*  91 */   protected Vector<SignedParts> signedParts = null;
/*  92 */   protected Vector<EncryptedParts> encryptedParts = null;
/*  93 */   protected Vector<SignedElements> signedElements = null;
/*  94 */   protected Vector<EncryptedElements> encryptedElements = null;
/*  95 */   protected PolicyID pid = null;
/*  96 */   protected TokenProcessor tokenProcessor = null;
/*  97 */   protected IntegrityAssertionProcessor iAP = null;
/*  98 */   protected EncryptionAssertionProcessor eAP = null;
/*  99 */   private WSSAssertion wss11 = null;
/*     */   
/*     */   private boolean isIssuedTokenAsEncryptedSupportingToken = false;
/*     */   protected boolean foundEncryptTargets = false;
/*     */   
/*     */   public BindingProcessor() {
/* 105 */     this.pid = new PolicyID();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void protectPrimarySignature() throws PolicyException {
/* 113 */     if (this.primarySP == null) {
/*     */       return;
/*     */     }
/* 116 */     boolean encryptSignConfirm = ((this.isServer && !this.isIncoming) || (!this.isServer && this.isIncoming));
/* 117 */     if ("EncryptBeforeSigning".equals(this.protectionOrder)) {
/* 118 */       EncryptionPolicy ep = getSecondaryEncryptionPolicy();
/* 119 */       EncryptionPolicy.FeatureBinding epFB = (EncryptionPolicy.FeatureBinding)ep.getFeatureBinding();
/* 120 */       EncryptionTarget et = this.eAP.getTargetCreator().newURIEncryptionTarget(this.primarySP.getUUID());
/* 121 */       SecurityPolicyUtil.setName((Target)et, (WSSPolicy)this.primarySP);
/* 122 */       epFB.addTargetBinding(et);
/* 123 */       if (this.foundEncryptTargets && isWSS11() && requireSC() && encryptSignConfirm && getBinding().getSignatureProtection()) {
/* 124 */         this.eAP.process(Target.SIGNATURE_CONFIRMATION, epFB);
/*     */       }
/*     */     } else {
/* 127 */       EncryptionPolicy.FeatureBinding epFB = (EncryptionPolicy.FeatureBinding)this.primaryEP.getFeatureBinding();
/* 128 */       EncryptionTarget et = this.eAP.getTargetCreator().newURIEncryptionTarget(this.primarySP.getUUID());
/* 129 */       SecurityPolicyUtil.setName((Target)et, (WSSPolicy)this.primarySP);
/* 130 */       epFB.addTargetBinding(et);
/* 131 */       if (this.foundEncryptTargets && isWSS11() && requireSC() && encryptSignConfirm && getBinding().getSignatureProtection()) {
/* 132 */         this.eAP.process(Target.SIGNATURE_CONFIRMATION, epFB);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void protectTimestamp(TimestampPolicy tp) {
/* 138 */     if (this.primarySP != null) {
/* 139 */       SignatureTarget target = this.iAP.getTargetCreator().newURISignatureTarget(tp.getUUID());
/* 140 */       this.iAP.getTargetCreator().addTransform(target);
/* 141 */       SecurityPolicyUtil.setName((Target)target, (WSSPolicy)tp);
/* 142 */       SignaturePolicy.FeatureBinding spFB = (SignaturePolicy.FeatureBinding)this.primarySP.getFeatureBinding();
/* 143 */       spFB.addTargetBinding(target);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void protectToken(WSSPolicy token) {
/* 149 */     if (this.primarySP == null) {
/*     */       return;
/*     */     }
/* 152 */     if ((this.isServer && this.isIncoming) || (!this.isServer && !this.isIncoming)) {
/* 153 */       protectToken(token, false);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void protectToken(WSSPolicy token, boolean ignoreSTR) {
/* 158 */     String uuid = (token != null) ? token.getUUID() : null;
/* 159 */     String uid = null;
/* 160 */     String includeToken = ((KeyBindingBase)token).getIncludeToken();
/* 161 */     boolean strIgnore = false;
/* 162 */     QName qName = null;
/*     */ 
/*     */     
/* 165 */     if (includeToken.endsWith("Always") || includeToken.endsWith("AlwaysToRecipient") || includeToken.endsWith("Once")) {
/* 166 */       strIgnore = true;
/*     */     }
/* 168 */     if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)token)) {
/* 169 */       uid = ((AuthenticationTokenPolicy.UsernameTokenBinding)token).getUUID();
/* 170 */       if (uid == null) {
/* 171 */         uid = this.pid.generateID();
/* 172 */         ((AuthenticationTokenPolicy.UsernameTokenBinding)token).setSTRID(uid);
/*     */       } 
/*     */       
/* 175 */       strIgnore = true;
/* 176 */       qName = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken");
/* 177 */     } else if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)token)) {
/* 178 */       uid = ((AuthenticationTokenPolicy.X509CertificateBinding)token).getSTRID();
/* 179 */       if (uid == null) {
/* 180 */         uid = this.pid.generateID();
/* 181 */         ((AuthenticationTokenPolicy.X509CertificateBinding)token).setSTRID(uid);
/*     */       } 
/* 183 */       qName = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "BinarySecurityToken");
/* 184 */     } else if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)token)) {
/*     */       
/* 186 */       uid = generateSAMLSTRID();
/*     */ 
/*     */       
/* 189 */       ((AuthenticationTokenPolicy.SAMLAssertionBinding)token).setSTRID(uid);
/*     */       
/* 191 */       qName = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Assertion");
/* 192 */     } else if (PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)token)) {
/* 193 */       IssuedTokenKeyBinding itb = (IssuedTokenKeyBinding)token;
/* 194 */       uid = itb.getSTRID();
/* 195 */       if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1".equals(itb.getTokenType()) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0".equals(itb.getTokenType())) {
/*     */         
/* 197 */         uid = generateSAMLSTRID();
/* 198 */         itb.setSTRID(uid);
/* 199 */         uuid = uid;
/*     */       } 
/* 201 */       if (uid == null) {
/* 202 */         uid = this.pid.generateID();
/* 203 */         itb.setSTRID(uid);
/*     */       } 
/* 205 */     } else if (PolicyTypeUtil.secureConversationTokenKeyBinding((SecurityPolicy)token)) {
/* 206 */       SecureConversationTokenKeyBinding sctBinding = (SecureConversationTokenKeyBinding)token;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 212 */     if (includeToken.endsWith("Never") || PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)token) || PolicyTypeUtil.issuedTokenKeyBinding((SecurityPolicy)token)) {
/* 213 */       uuid = uid;
/*     */     }
/*     */     
/* 216 */     if (!ignoreSTR) {
/* 217 */       if (uuid != null) {
/* 218 */         SignatureTargetCreator stc = this.iAP.getTargetCreator();
/* 219 */         SignatureTarget st = stc.newURISignatureTarget(uuid);
/* 220 */         if (strIgnore != true) {
/* 221 */           stc.addSTRTransform(st);
/* 222 */           st.setPolicyQName(qName);
/*     */         } else {
/* 224 */           stc.addTransform(st);
/*     */         } 
/* 226 */         SignaturePolicy.FeatureBinding fb = (SignaturePolicy.FeatureBinding)this.primarySP.getFeatureBinding();
/* 227 */         fb.addTargetBinding(st);
/*     */       } 
/*     */     } else {
/* 230 */       SignatureTargetCreator stc = this.iAP.getTargetCreator();
/* 231 */       SignatureTarget st = null;
/* 232 */       if (PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)token)) {
/* 233 */         WSSPolicy kbd = ((DerivedTokenKeyBinding)token).getOriginalKeyBinding();
/* 234 */         if (PolicyTypeUtil.symmetricKeyBinding((SecurityPolicy)kbd)) {
/* 235 */           st = stc.newURISignatureTarget(uuid);
/*     */         } else {
/* 237 */           st = stc.newURISignatureTarget(uuid);
/*     */         } 
/*     */       } else {
/* 240 */         st = stc.newURISignatureTarget(uuid);
/*     */       } 
/* 242 */       if (st != null) {
/* 243 */         if (strIgnore != true) {
/* 244 */           stc.addSTRTransform(st);
/* 245 */           st.setPolicyQName(qName);
/*     */         } else {
/* 247 */           stc.addTransform(st);
/*     */         } 
/* 249 */         SignaturePolicy.FeatureBinding fb = (SignaturePolicy.FeatureBinding)this.primarySP.getFeatureBinding();
/* 250 */         fb.addTargetBinding(st);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract EncryptionPolicy getSecondaryEncryptionPolicy() throws PolicyException;
/*     */   
/*     */   private String generateSAMLSTRID() {
/* 258 */     StringBuilder sb = new StringBuilder();
/* 259 */     sb.append("SAML");
/* 260 */     sb.append(this.pid.generateID());
/* 261 */     return sb.toString();
/*     */   }
/*     */   
/*     */   protected void addPrimaryTargets() throws PolicyException {
/* 265 */     SignaturePolicy.FeatureBinding spFB = null;
/* 266 */     if (this.primarySP != null) {
/* 267 */       spFB = (SignaturePolicy.FeatureBinding)this.primarySP.getFeatureBinding();
/*     */     }
/* 269 */     EncryptionPolicy.FeatureBinding epFB = null;
/* 270 */     if (this.primaryEP != null) {
/* 271 */       epFB = (EncryptionPolicy.FeatureBinding)this.primaryEP.getFeatureBinding();
/*     */     }
/*     */     
/* 274 */     if (spFB != null) {
/* 275 */       if (spFB.getCanonicalizationAlgorithm() == null || spFB.getCanonicalizationAlgorithm().equals("")) {
/* 276 */         spFB.setCanonicalizationAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 281 */       for (SignedElements se : this.signedElements) {
/* 282 */         this.iAP.process(se, spFB);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 289 */       for (SignedParts sp : this.signedParts) {
/* 290 */         if (SecurityPolicyUtil.isSignedPartsEmpty(sp)) {
/* 291 */           this.signedParts.removeAllElements();
/* 292 */           this.signedParts.add(sp);
/*     */           break;
/*     */         } 
/*     */       } 
/* 296 */       for (SignedParts sp : this.signedParts) {
/* 297 */         this.iAP.process(sp, spFB);
/*     */       }
/*     */       
/* 300 */       if (isWSS11() && requireSC()) {
/* 301 */         this.iAP.process(Target.SIGNATURE_CONFIRMATION, spFB);
/*     */       }
/*     */     } 
/*     */     
/* 305 */     if (epFB != null) {
/* 306 */       for (EncryptedParts ep : this.encryptedParts) {
/* 307 */         this.foundEncryptTargets = true;
/* 308 */         this.eAP.process(ep, epFB);
/*     */       } 
/*     */       
/* 311 */       for (EncryptedElements encEl : this.encryptedElements) {
/* 312 */         this.foundEncryptTargets = true;
/* 313 */         this.eAP.process(encEl, epFB);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean requireSC() {
/* 319 */     if (this.wss11 != null && this.wss11.getRequiredProperties() != null && 
/* 320 */       this.wss11.getRequiredProperties().contains("RequireSignatureConfirmation")) {
/* 321 */       return true;
/*     */     }
/*     */     
/* 324 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract Binding getBinding();
/*     */   
/*     */   public void processSupportingTokens(SupportingTokens st) throws PolicyException {
/* 331 */     SupportingTokensProcessor stp = new SupportingTokensProcessor(st, this.tokenProcessor, getBinding(), this.container, this.primarySP, getEncryptionPolicy(), this.pid);
/*     */     
/* 333 */     stp.process();
/*     */   }
/*     */ 
/*     */   
/*     */   public void processSupportingTokens(SignedSupportingTokens st) throws PolicyException {
/* 338 */     SignedSupportingTokensProcessor stp = new SignedSupportingTokensProcessor(st, this.tokenProcessor, getBinding(), this.container, this.primarySP, getEncryptionPolicy(), this.pid);
/*     */     
/* 340 */     stp.process();
/*     */   }
/*     */ 
/*     */   
/*     */   public void processSupportingTokens(EndorsingSupportingTokens est) throws PolicyException {
/* 345 */     EndorsingSupportingTokensProcessor stp = new EndorsingSupportingTokensProcessor((SupportingTokens)est, this.tokenProcessor, getBinding(), this.container, this.primarySP, getEncryptionPolicy(), this.pid);
/*     */     
/* 347 */     stp.process();
/*     */   }
/*     */   
/*     */   public void processSupportingTokens(SignedEndorsingSupportingTokens est) throws PolicyException {
/* 351 */     SignedEndorsingSupportingTokensProcessor stp = new SignedEndorsingSupportingTokensProcessor((SupportingTokens)est, this.tokenProcessor, getBinding(), this.container, this.primarySP, getEncryptionPolicy(), this.pid);
/*     */     
/* 353 */     stp.process();
/*     */   }
/*     */ 
/*     */   
/*     */   public void processSupportingTokens(SignedEncryptedSupportingTokens sest) throws PolicyException {
/* 358 */     SignedEncryptedSupportingTokensProcessor setp = new SignedEncryptedSupportingTokensProcessor(sest, this.tokenProcessor, getBinding(), this.container, this.primarySP, getEncryptionPolicy(), this.pid);
/*     */     
/* 360 */     setp.process();
/* 361 */     isIssuedTokenAsEncryptedSupportingToken(setp.isIssuedTokenAsEncryptedSupportingToken());
/*     */   }
/*     */   
/*     */   public void processSupportingTokens(EncryptedSupportingTokens est) throws PolicyException {
/* 365 */     EncryptedSupportingTokensProcessor etp = new EncryptedSupportingTokensProcessor(est, this.tokenProcessor, getBinding(), this.container, this.primarySP, getEncryptionPolicy(), this.pid);
/*     */     
/* 367 */     etp.process();
/* 368 */     isIssuedTokenAsEncryptedSupportingToken(etp.isIssuedTokenAsEncryptedSupportingToken());
/*     */   }
/*     */   
/*     */   public void processSupportingTokens(EndorsingEncryptedSupportingTokens est) throws PolicyException {
/* 372 */     EndorsingEncryptedSupportingTokensProcessor etp = new EndorsingEncryptedSupportingTokensProcessor(est, this.tokenProcessor, getBinding(), this.container, this.primarySP, getEncryptionPolicy(), this.pid);
/*     */     
/* 374 */     etp.process();
/* 375 */     isIssuedTokenAsEncryptedSupportingToken(etp.isIssuedTokenAsEncryptedSupportingToken());
/*     */   }
/*     */   
/*     */   public void processSupportingTokens(SignedEndorsingEncryptedSupportingTokens est) throws PolicyException {
/* 379 */     SignedEndorsingEncryptedSupportingTokensProcessor etp = new SignedEndorsingEncryptedSupportingTokensProcessor(est, this.tokenProcessor, getBinding(), this.container, this.primarySP, getEncryptionPolicy(), this.pid);
/*     */     
/* 381 */     etp.process();
/* 382 */     isIssuedTokenAsEncryptedSupportingToken(etp.isIssuedTokenAsEncryptedSupportingToken());
/*     */   }
/*     */   
/*     */   protected SignaturePolicy getSignaturePolicy() {
/* 386 */     if ("SignBeforeEncrypting".equals(getBinding().getProtectionOrder())) {
/* 387 */       return this.primarySP;
/*     */     }
/* 389 */     return this.sSigPolicy;
/*     */   }
/*     */ 
/*     */   
/*     */   private EncryptionPolicy getEncryptionPolicy() throws PolicyException {
/* 394 */     if ("SignBeforeEncrypting".equals(getBinding().getProtectionOrder())) {
/* 395 */       return this.primaryEP;
/*     */     }
/* 397 */     return getSecondaryEncryptionPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void close();
/*     */   
/*     */   public boolean isWSS11() {
/* 404 */     if (this.wss11 != null) {
/* 405 */       return true;
/*     */     }
/* 407 */     return false;
/*     */   }
/*     */   
/*     */   public void setWSS11(WSSAssertion wss11) {
/* 411 */     this.wss11 = wss11;
/*     */   }
/*     */   
/*     */   public boolean isIssuedTokenAsEncryptedSupportingToken() {
/* 415 */     return this.isIssuedTokenAsEncryptedSupportingToken;
/*     */   }
/*     */   
/*     */   private void isIssuedTokenAsEncryptedSupportingToken(boolean value) {
/* 419 */     this.isIssuedTokenAsEncryptedSupportingToken = value;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\BindingProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */