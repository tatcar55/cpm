/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.policy.Binding;
/*     */ import com.sun.xml.ws.security.policy.EncryptedElements;
/*     */ import com.sun.xml.ws.security.policy.EncryptedParts;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.SignedElements;
/*     */ import com.sun.xml.ws.security.policy.SignedParts;
/*     */ import com.sun.xml.ws.security.policy.SupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.Target;
/*     */ import com.sun.xml.ws.security.policy.Token;
/*     */ import com.sun.xml.ws.security.policy.UserNameToken;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.AuthenticationTokenPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.IssuedTokenKeyBinding;
/*     */ import com.sun.xml.wss.impl.policy.mls.KeyBindingBase;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
/*     */ import com.sun.xml.wss.impl.policy.mls.WSSPolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class SupportingTokensProcessor
/*     */ {
/*  77 */   protected TokenProcessor tokenProcessor = null;
/*  78 */   protected SignatureTargetCreator stc = null;
/*  79 */   protected EncryptionTargetCreator etc = null;
/*  80 */   protected Binding binding = null;
/*  81 */   protected XWSSPolicyContainer policyContainer = null;
/*     */   
/*  83 */   protected SignaturePolicy signaturePolicy = null;
/*  84 */   protected EncryptionPolicy encryptionPolicy = null;
/*  85 */   protected SupportingTokens st = null;
/*  86 */   protected IntegrityAssertionProcessor iAP = null;
/*  87 */   protected EncryptionAssertionProcessor eAP = null;
/*     */   
/*  89 */   protected ArrayList<SignaturePolicy> spList = null;
/*  90 */   protected ArrayList<EncryptionPolicy> epList = null;
/*  91 */   protected SignedParts emptySP = null;
/*     */   protected boolean buildSP = false;
/*     */   protected boolean buildEP = false;
/*  94 */   protected PolicyID pid = null;
/*     */ 
/*     */ 
/*     */   
/*     */   protected SupportingTokensProcessor() {}
/*     */ 
/*     */   
/*     */   public SupportingTokensProcessor(SupportingTokens st, TokenProcessor tokenProcessor, Binding binding, XWSSPolicyContainer container, SignaturePolicy sp, EncryptionPolicy ep, PolicyID pid) {
/* 102 */     this.st = st;
/* 103 */     this.tokenProcessor = tokenProcessor;
/* 104 */     this.binding = binding;
/* 105 */     this.pid = pid;
/* 106 */     this.policyContainer = container;
/* 107 */     this.encryptionPolicy = ep;
/* 108 */     this.signaturePolicy = sp;
/* 109 */     AlgorithmSuite as = null;
/* 110 */     as = st.getAlgorithmSuite();
/* 111 */     if (as == null && binding != null) {
/* 112 */       as = binding.getAlgorithmSuite();
/*     */     }
/* 114 */     boolean signContent = false;
/* 115 */     if (binding != null)
/* 116 */       signContent = binding.isSignContent(); 
/* 117 */     this.iAP = new IntegrityAssertionProcessor(as, signContent);
/* 118 */     this.eAP = new EncryptionAssertionProcessor(as, false);
/* 119 */     this.stc = this.iAP.getTargetCreator();
/* 120 */     this.etc = this.eAP.getTargetCreator();
/* 121 */     this.emptySP = getEmptySignedParts(st.getSignedParts());
/*     */   }
/*     */   
/*     */   public void process() throws PolicyException {
/* 125 */     Iterator<Token> tokens = this.st.getTokens();
/*     */     
/* 127 */     if (this.st.getEncryptedParts().hasNext() || this.st.getEncryptedElements().hasNext()) {
/* 128 */       this.buildEP = true;
/*     */     }
/* 130 */     if (this.st.getSignedElements().hasNext() || this.st.getSignedParts().hasNext()) {
/* 131 */       this.buildSP = true;
/*     */     }
/*     */     
/* 134 */     while (tokens.hasNext()) {
/* 135 */       Token token = tokens.next();
/* 136 */       SecurityPolicyVersion spVersion = SecurityPolicyUtil.getSPVersion((PolicyAssertion)token);
/* 137 */       WSSPolicy policy = this.tokenProcessor.getWSSToken(token);
/* 138 */       if (this instanceof EndorsingSupportingTokensProcessor && 
/* 139 */         PolicyUtil.isUsernameToken((PolicyAssertion)token, spVersion)) {
/* 140 */         AuthenticationTokenPolicy.UsernameTokenBinding utb = (AuthenticationTokenPolicy.UsernameTokenBinding)policy;
/*     */         
/* 142 */         utb.isEndorsing(true);
/*     */       } 
/*     */       
/* 145 */       if (PolicyUtil.isIssuedToken((PolicyAssertion)token, spVersion) && this instanceof EndorsingSupportingTokensProcessor)
/*     */       {
/* 147 */         ((IssuedTokenKeyBinding)policy).setSTRID(null);
/*     */       }
/* 149 */       if (policy.getUUID() != null) {
/*     */         
/* 151 */         addToPrimarySignature(policy, token);
/*     */         
/* 153 */         encryptToken(token, spVersion);
/*     */         
/* 155 */         if (PolicyUtil.isSamlToken((PolicyAssertion)token, spVersion)) {
/* 156 */           correctSAMLBinding(policy);
/*     */         }
/*     */         
/* 159 */         collectSignaturePolicies(token);
/* 160 */         if (this.buildEP) {
/* 161 */           EncryptionPolicy ep = new EncryptionPolicy();
/* 162 */           ep.setKeyBinding((MLSPolicy)policy);
/* 163 */           getEPList().add(ep);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 168 */       if (!(this instanceof EndorsingSupportingTokensProcessor) || (this instanceof EndorsingSupportingTokensProcessor && token instanceof com.sun.xml.ws.security.impl.policy.X509Token && token.getIncludeToken().endsWith("Never"))) {
/*     */         
/* 170 */         AuthenticationTokenPolicy atp = new AuthenticationTokenPolicy();
/* 171 */         atp.setFeatureBinding((MLSPolicy)policy);
/* 172 */         this.policyContainer.insert((SecurityPolicy)atp);
/*     */       } 
/*     */       
/* 175 */       addTargets();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void collectSignaturePolicies(Token token) throws PolicyException {
/* 180 */     if (this.buildSP) {
/* 181 */       createSupportingSignature(token);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void createSupportingSignature(Token token) throws PolicyException {
/* 186 */     SignaturePolicy sp = new SignaturePolicy();
/* 187 */     sp.setUUID(this.pid.generateID());
/* 188 */     this.tokenProcessor.addKeyBinding(this.binding, (WSSPolicy)sp, token, true);
/* 189 */     if (this.binding != null && this.binding.getTokenProtection()) {
/* 190 */       protectToken((WSSPolicy)sp.getKeyBinding(), sp);
/*     */     }
/* 192 */     SignaturePolicy.FeatureBinding spFB = (SignaturePolicy.FeatureBinding)sp.getFeatureBinding();
/*     */     
/* 194 */     AlgorithmSuite as = null;
/* 195 */     as = this.st.getAlgorithmSuite();
/* 196 */     if (as == null && this.binding != null) {
/* 197 */       as = this.binding.getAlgorithmSuite();
/*     */     }
/* 199 */     SecurityPolicyUtil.setCanonicalizationMethod(spFB, as);
/*     */     
/* 201 */     getSPList().add(sp);
/* 202 */     endorseSignature(sp);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addToPrimarySignature(WSSPolicy policy, Token token) throws PolicyException {}
/*     */ 
/*     */   
/*     */   protected void endorseSignature(SignaturePolicy sp) {}
/*     */ 
/*     */   
/*     */   protected ArrayList<SignaturePolicy> getSPList() {
/* 213 */     if (this.spList == null) {
/* 214 */       this.spList = new ArrayList<SignaturePolicy>();
/*     */     }
/* 216 */     return this.spList;
/*     */   }
/*     */   
/*     */   protected ArrayList<EncryptionPolicy> getEPList() {
/* 220 */     if (this.epList == null) {
/* 221 */       this.epList = new ArrayList<EncryptionPolicy>();
/*     */     }
/* 223 */     return this.epList;
/*     */   }
/*     */   
/*     */   protected void encryptToken(Token token, SecurityPolicyVersion spVersion) throws PolicyException {
/* 227 */     if (PolicyUtil.isUsernameToken((PolicyAssertion)token, spVersion) && ((UserNameToken)token).hasPassword() && !((UserNameToken)token).useHashPassword())
/*     */     {
/*     */       
/* 230 */       if (this.binding != null && token.getTokenId() != null) {
/* 231 */         EncryptionPolicy.FeatureBinding fb = (EncryptionPolicy.FeatureBinding)this.encryptionPolicy.getFeatureBinding();
/* 232 */         EncryptionTarget et = this.etc.newURIEncryptionTarget(token.getTokenId());
/* 233 */         fb.addTargetBinding(et);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected SignedParts getEmptySignedParts(Iterator<Target> itr) {
/* 240 */     while (itr.hasNext()) {
/* 241 */       Target target = itr.next();
/* 242 */       SecurityPolicyVersion spVersion = SecurityPolicyUtil.getSPVersion((PolicyAssertion)target);
/* 243 */       if (PolicyUtil.isSignedParts((PolicyAssertion)target, spVersion) && 
/* 244 */         SecurityPolicyUtil.isSignedPartsEmpty((SignedParts)target)) {
/* 245 */         return (SignedParts)target;
/*     */       }
/*     */     } 
/*     */     
/* 249 */     return null;
/*     */   }
/*     */   
/*     */   protected void addTargets() {
/* 253 */     if (this.binding != null && "SignBeforeEncrypting".equals(this.binding.getProtectionOrder())) {
/* 254 */       if (this.spList != null) {
/* 255 */         populateSignaturePolicy();
/*     */       }
/* 257 */       if (this.epList != null) {
/* 258 */         populateEncryptionPolicy();
/*     */       }
/*     */     } else {
/* 261 */       if (this.epList != null) {
/* 262 */         populateEncryptionPolicy();
/*     */       }
/* 264 */       if (this.spList != null) {
/* 265 */         populateSignaturePolicy();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void populateSignaturePolicy() {
/* 271 */     for (SignaturePolicy sp : this.spList) {
/* 272 */       SignaturePolicy.FeatureBinding spFB = (SignaturePolicy.FeatureBinding)sp.getFeatureBinding();
/* 273 */       if (this.emptySP != null) {
/* 274 */         this.iAP.process(this.emptySP, spFB);
/*     */       } else {
/* 276 */         Iterator<SignedParts> iterator = this.st.getSignedParts();
/* 277 */         while (iterator.hasNext()) {
/* 278 */           SignedParts target = iterator.next();
/* 279 */           this.iAP.process(target, spFB);
/*     */         } 
/*     */       } 
/* 282 */       Iterator<SignedElements> itr = this.st.getSignedElements();
/* 283 */       while (itr.hasNext()) {
/* 284 */         SignedElements target = itr.next();
/* 285 */         this.iAP.process(target, spFB);
/*     */       } 
/* 287 */       this.policyContainer.insert((SecurityPolicy)sp);
/*     */     } 
/* 289 */     this.spList.clear();
/*     */   }
/*     */   
/*     */   protected void populateEncryptionPolicy() {
/* 293 */     for (EncryptionPolicy ep : this.epList) {
/* 294 */       EncryptionPolicy.FeatureBinding epFB = (EncryptionPolicy.FeatureBinding)ep.getFeatureBinding();
/* 295 */       Iterator<EncryptedElements> itr = this.st.getEncryptedElements();
/* 296 */       while (itr.hasNext()) {
/* 297 */         EncryptedElements target = itr.next();
/* 298 */         this.eAP.process(target, epFB);
/*     */       } 
/* 300 */       Iterator<EncryptedParts> epr = this.st.getEncryptedParts();
/* 301 */       while (epr.hasNext()) {
/* 302 */         EncryptedParts target = epr.next();
/* 303 */         this.eAP.process(target, epFB);
/*     */       } 
/* 305 */       this.policyContainer.insert((SecurityPolicy)ep);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void protectToken(WSSPolicy token, SignaturePolicy sp) {
/* 310 */     String uid = token.getUUID();
/* 311 */     boolean strIgnore = false;
/* 312 */     String includeToken = ((KeyBindingBase)token).getIncludeToken();
/* 313 */     if (includeToken.endsWith("AlwaysToRecipient") || includeToken.endsWith("Always")) {
/* 314 */       strIgnore = true;
/*     */     }
/* 316 */     if (uid != null) {
/* 317 */       SignatureTargetCreator stcr = this.iAP.getTargetCreator();
/* 318 */       SignatureTarget stg = stcr.newURISignatureTarget(uid);
/* 319 */       SecurityPolicyUtil.setName((Target)stg, token);
/* 320 */       if (!strIgnore) {
/* 321 */         stcr.addSTRTransform(stg);
/* 322 */         stg.setPolicyQName(getQName(token));
/*     */       } else {
/* 324 */         stcr.addTransform(stg);
/*     */       } 
/* 326 */       SignaturePolicy.FeatureBinding fb = (SignaturePolicy.FeatureBinding)sp.getFeatureBinding();
/* 327 */       fb.addTargetBinding(stg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void correctSAMLBinding(WSSPolicy policy) {}
/*     */ 
/*     */   
/*     */   protected QName getQName(WSSPolicy token) {
/* 336 */     QName qName = null;
/* 337 */     if (PolicyTypeUtil.usernameTokenBinding((SecurityPolicy)token)) {
/* 338 */       qName = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "UsernameToken");
/* 339 */     } else if (PolicyTypeUtil.x509CertificateBinding((SecurityPolicy)token)) {
/* 340 */       qName = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "BinarySecurityToken");
/* 341 */     } else if (PolicyTypeUtil.samlTokenPolicy((SecurityPolicy)token)) {
/* 342 */       qName = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "SAMLToken");
/*     */     } 
/* 344 */     return qName;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\SupportingTokensProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */