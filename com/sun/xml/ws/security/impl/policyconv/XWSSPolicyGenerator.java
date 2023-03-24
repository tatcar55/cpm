/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.security.impl.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.impl.policy.Constants;
/*     */ import com.sun.xml.ws.security.impl.policy.LogStringsMessages;
/*     */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*     */ import com.sun.xml.ws.security.impl.policy.Trust10;
/*     */ import com.sun.xml.ws.security.impl.policy.Trust13;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.policy.AsymmetricBinding;
/*     */ import com.sun.xml.ws.security.policy.Binding;
/*     */ import com.sun.xml.ws.security.policy.EncryptedElements;
/*     */ import com.sun.xml.ws.security.policy.EncryptedParts;
/*     */ import com.sun.xml.ws.security.policy.EncryptedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.EndorsingEncryptedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.EndorsingSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.MessageLayout;
/*     */ import com.sun.xml.ws.security.policy.RequiredElements;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.SignedElements;
/*     */ import com.sun.xml.ws.security.policy.SignedEncryptedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SignedEndorsingEncryptedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SignedEndorsingSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SignedParts;
/*     */ import com.sun.xml.ws.security.policy.SignedSupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SymmetricBinding;
/*     */ import com.sun.xml.ws.security.policy.TransportBinding;
/*     */ import com.sun.xml.ws.security.policy.WSSAssertion;
/*     */ import com.sun.xml.wss.impl.AlgorithmSuite;
/*     */ import com.sun.xml.wss.impl.MessageLayout;
/*     */ import com.sun.xml.wss.impl.WSSAssertion;
/*     */ import com.sun.xml.wss.impl.policy.PolicyGenerationException;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Vector;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XWSSPolicyGenerator
/*     */ {
/*  93 */   String _protectionOrder = "";
/*     */   
/*  95 */   SignaturePolicy _primarySP = null;
/*  96 */   EncryptionPolicy _primaryEP = null;
/*     */   
/*  98 */   EncryptionPolicy _sEncPolicy = null;
/*  99 */   SignaturePolicy _csSP = null;
/* 100 */   XWSSPolicyContainer _policyContainer = null;
/*     */   Binding _binding;
/* 102 */   Policy effectivePolicy = null;
/*     */   boolean isServer = false;
/*     */   boolean isIncoming = false;
/* 105 */   private PolicyAssertion wssAssertion = null;
/* 106 */   private WSSAssertion wss11 = null;
/* 107 */   private Trust10 trust10 = null;
/* 108 */   private Trust13 trust13 = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean encryptBody = false;
/*     */ 
/*     */ 
/*     */   
/* 116 */   private Vector<SignedParts> signedParts = new Vector<SignedParts>();
/* 117 */   private Vector<EncryptedParts> encryptedParts = new Vector<EncryptedParts>();
/* 118 */   private Vector<SignedElements> signedElements = new Vector<SignedElements>();
/* 119 */   private Vector<EncryptedElements> encryptedElements = new Vector<EncryptedElements>();
/*     */   private boolean ignoreST = false;
/*     */   private boolean transportBinding = false;
/* 122 */   private IntegrityAssertionProcessor iAP = null;
/* 123 */   private EncryptionAssertionProcessor eAP = null;
/* 124 */   private Binding policyBinding = null;
/* 125 */   private List<RequiredElements> reqElements = new ArrayList<RequiredElements>();
/*     */ 
/*     */   
/*     */   private SecurityPolicyVersion spVersion;
/*     */   
/*     */   private boolean isIssuedTokenAsEncryptedSupportingToken = false;
/*     */ 
/*     */   
/*     */   public XWSSPolicyGenerator(Policy effectivePolicy, boolean isServer, boolean isIncoming, SecurityPolicyVersion spVersion) {
/* 134 */     this.effectivePolicy = effectivePolicy;
/* 135 */     this._policyContainer = new XWSSPolicyContainer(isServer, isIncoming);
/* 136 */     this.isServer = isServer;
/* 137 */     this.isIncoming = isIncoming;
/* 138 */     this.spVersion = spVersion;
/*     */   }
/*     */   
/*     */   public XWSSPolicyGenerator(Policy effectivePolicy, boolean isServer, boolean isIncoming) {
/* 142 */     this.effectivePolicy = effectivePolicy;
/* 143 */     this._policyContainer = new XWSSPolicyContainer(isServer, isIncoming);
/* 144 */     this.isServer = isServer;
/* 145 */     this.isIncoming = isIncoming;
/* 146 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   }
/*     */   
/*     */   public AlgorithmSuite getBindingLevelAlgSuite() {
/* 150 */     if (this._binding != null) {
/* 151 */       return this._binding.getAlgorithmSuite();
/*     */     }
/*     */     
/* 154 */     return (AlgorithmSuite)new AlgorithmSuite();
/*     */   }
/*     */ 
/*     */   
/*     */   public void process(boolean ignoreST) throws PolicyException {
/* 159 */     this.ignoreST = ignoreST;
/* 160 */     process();
/*     */   }
/*     */ 
/*     */   
/*     */   public void process() throws PolicyException {
/* 165 */     collectPolicies();
/* 166 */     PolicyAssertion binding = (PolicyAssertion)getBinding();
/* 167 */     this.policyBinding = (Binding)binding;
/* 168 */     if (binding == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 173 */       NilBindingProcessor nbp = new NilBindingProcessor(this.isServer, this.isIncoming, this._policyContainer);
/* 174 */       nbp.process();
/* 175 */       processNonBindingAssertions(nbp);
/*     */       return;
/*     */     } 
/* 178 */     if (PolicyUtil.isTransportBinding(binding, this.spVersion)) {
/* 179 */       if (Constants.logger.isLoggable(Level.FINE)) {
/* 180 */         Constants.logger.log(Level.FINE, "TransportBinding was configured in the policy");
/*     */       }
/* 182 */       TransportBindingProcessor tbp = new TransportBindingProcessor((TransportBinding)binding, this.isServer, this.isIncoming, this._policyContainer);
/* 183 */       tbp.process();
/* 184 */       processNonBindingAssertions(tbp);
/* 185 */       this.transportBinding = true;
/*     */     } else {
/*     */       
/* 188 */       this.iAP = new IntegrityAssertionProcessor(this._binding.getAlgorithmSuite(), this._binding.isSignContent());
/* 189 */       this.eAP = new EncryptionAssertionProcessor(this._binding.getAlgorithmSuite(), false);
/*     */       
/* 191 */       this._policyContainer.setPolicyContainerMode(this._binding.getLayout());
/* 192 */       if (PolicyUtil.isSymmetricBinding(binding.getName(), this.spVersion)) {
/*     */         
/* 194 */         if (Constants.logger.isLoggable(Level.FINE)) {
/* 195 */           Constants.logger.log(Level.FINE, "SymmetricBinding was configured in the policy");
/*     */         }
/* 197 */         SymmetricBindingProcessor sbp = new SymmetricBindingProcessor((SymmetricBinding)this._binding, this._policyContainer, this.isServer, this.isIncoming, this.signedParts, this.encryptedParts, this.signedElements, this.encryptedElements);
/*     */ 
/*     */         
/* 200 */         if (this.wssAssertion != null && PolicyUtil.isWSS11(this.wssAssertion, this.spVersion)) {
/* 201 */           sbp.setWSS11((WSSAssertion)this.wssAssertion);
/*     */         }
/* 203 */         sbp.process();
/* 204 */         processNonBindingAssertions(sbp);
/* 205 */         sbp.close();
/*     */       }
/* 207 */       else if (PolicyUtil.isAsymmetricBinding(binding.getName(), this.spVersion)) {
/*     */         
/* 209 */         if (Constants.logger.isLoggable(Level.FINE)) {
/* 210 */           Constants.logger.log(Level.FINE, "AsymmetricBinding was configured in the policy");
/*     */         }
/* 212 */         AsymmetricBindingProcessor abp = new AsymmetricBindingProcessor((AsymmetricBinding)this._binding, this._policyContainer, this.isServer, this.isIncoming, this.signedParts, this.encryptedParts, this.signedElements, this.encryptedElements);
/*     */ 
/*     */         
/* 215 */         if (this.wssAssertion != null && PolicyUtil.isWSS11(this.wssAssertion, this.spVersion)) {
/* 216 */           abp.setWSS11((WSSAssertion)this.wssAssertion);
/*     */         }
/* 218 */         abp.process();
/* 219 */         processNonBindingAssertions(abp);
/* 220 */         abp.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public MessagePolicy getXWSSPolicy() throws PolicyException {
/* 226 */     MessagePolicy mp = null;
/*     */     try {
/* 228 */       if (this.wssAssertion != null) {
/* 229 */         mp = this._policyContainer.getMessagePolicy(PolicyUtil.isWSS11(this.wssAssertion, this.spVersion));
/*     */       } else {
/* 231 */         mp = this._policyContainer.getMessagePolicy(false);
/*     */       } 
/* 233 */     } catch (PolicyGenerationException ex) {
/* 234 */       Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0113_UNABLE_TO_DIGEST_POLICY(this.effectivePolicy), (Throwable)ex);
/* 235 */       throw new PolicyException("Unable to digest SecurityPolicy ");
/*     */     } 
/*     */     
/* 238 */     if (this.wssAssertion != null) {
/*     */       try {
/* 240 */         mp.setWSSAssertion(getWssAssertion((WSSAssertion)this.wssAssertion));
/* 241 */       } catch (PolicyGenerationException ex) {
/* 242 */         Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0104_ERROR_SIGNATURE_CONFIRMATION_ELEMENT(ex.getMessage()), (Throwable)ex);
/* 243 */         throw new PolicyException(LogStringsMessages.SP_0104_ERROR_SIGNATURE_CONFIRMATION_ELEMENT(ex.getMessage()));
/*     */       } 
/*     */     }
/* 246 */     if (this.policyBinding != null && this.policyBinding.getAlgorithmSuite() != null) {
/* 247 */       mp.setAlgorithmSuite(getAlgoSuite(this.policyBinding.getAlgorithmSuite()));
/*     */     }
/* 249 */     if (this.policyBinding != null && this.policyBinding.getLayout() != null) {
/* 250 */       mp.setLayout(getLayout(this.policyBinding.getLayout()));
/*     */     }
/* 252 */     if (this.isIncoming && this.reqElements.size() > 0) {
/*     */       try {
/* 254 */         RequiredElementsProcessor rep = new RequiredElementsProcessor(this.reqElements, mp);
/*     */         
/* 256 */         rep.process();
/* 257 */       } catch (PolicyGenerationException ex) {
/* 258 */         Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0103_ERROR_REQUIRED_ELEMENTS(ex.getMessage()), (Throwable)ex);
/* 259 */         throw new PolicyException(LogStringsMessages.SP_0103_ERROR_REQUIRED_ELEMENTS(ex.getMessage()));
/*     */       } 
/*     */     }
/* 262 */     if (this.transportBinding) {
/* 263 */       mp.setSSL(this.transportBinding);
/*     */     }
/* 265 */     return mp;
/*     */   }
/*     */   
/*     */   private void processNonBindingAssertions(BindingProcessor bindingProcessor) throws PolicyException {
/* 269 */     for (AssertionSet assertionSet : this.effectivePolicy) {
/* 270 */       for (PolicyAssertion assertion : assertionSet) {
/* 271 */         if (PolicyUtil.isBinding(assertion, this.spVersion))
/*     */           continue; 
/* 273 */         if (!this.ignoreST && shouldAddST() && PolicyUtil.isSupportingToken(assertion, this.spVersion)) {
/* 274 */           bindingProcessor.processSupportingTokens((SupportingTokens)assertion); continue;
/* 275 */         }  if (!this.ignoreST && shouldAddST() && PolicyUtil.isSignedSupportingToken(assertion, this.spVersion)) {
/* 276 */           bindingProcessor.processSupportingTokens((SignedSupportingTokens)assertion); continue;
/* 277 */         }  if (!this.ignoreST && shouldAddST() && PolicyUtil.isEndorsedSupportingToken(assertion, this.spVersion)) {
/* 278 */           bindingProcessor.processSupportingTokens((EndorsingSupportingTokens)assertion); continue;
/* 279 */         }  if (!this.ignoreST && shouldAddST() && PolicyUtil.isSignedEndorsingSupportingToken(assertion, this.spVersion)) {
/* 280 */           bindingProcessor.processSupportingTokens((SignedEndorsingSupportingTokens)assertion); continue;
/* 281 */         }  if (!this.ignoreST && shouldAddST() && PolicyUtil.isSignedEncryptedSupportingToken(assertion, this.spVersion)) {
/* 282 */           bindingProcessor.processSupportingTokens((SignedEncryptedSupportingTokens)assertion);
/* 283 */           isIssuedTokenAsEncryptedSupportingToken(bindingProcessor.isIssuedTokenAsEncryptedSupportingToken()); continue;
/* 284 */         }  if (!this.ignoreST && shouldAddST() && PolicyUtil.isEncryptedSupportingToken(assertion, this.spVersion)) {
/* 285 */           bindingProcessor.processSupportingTokens((EncryptedSupportingTokens)assertion);
/* 286 */           isIssuedTokenAsEncryptedSupportingToken(bindingProcessor.isIssuedTokenAsEncryptedSupportingToken()); continue;
/* 287 */         }  if (!this.ignoreST && shouldAddST() && PolicyUtil.isEndorsingEncryptedSupportingToken(assertion, this.spVersion)) {
/* 288 */           bindingProcessor.processSupportingTokens((EndorsingEncryptedSupportingTokens)assertion);
/* 289 */           isIssuedTokenAsEncryptedSupportingToken(bindingProcessor.isIssuedTokenAsEncryptedSupportingToken()); continue;
/* 290 */         }  if (!this.ignoreST && shouldAddST() && PolicyUtil.isSignedEndorsingEncryptedSupportingToken(assertion, this.spVersion)) {
/* 291 */           bindingProcessor.processSupportingTokens((SignedEndorsingEncryptedSupportingTokens)assertion);
/* 292 */           isIssuedTokenAsEncryptedSupportingToken(bindingProcessor.isIssuedTokenAsEncryptedSupportingToken()); continue;
/* 293 */         }  if (PolicyUtil.isWSS10(assertion, this.spVersion)) {
/* 294 */           this.wssAssertion = assertion; continue;
/* 295 */         }  if (PolicyUtil.isWSS11(assertion, this.spVersion)) {
/* 296 */           this.wssAssertion = assertion; continue;
/* 297 */         }  if (PolicyUtil.isTrust10(assertion, this.spVersion)) {
/* 298 */           this.trust10 = (Trust10)assertion; continue;
/* 299 */         }  if (PolicyUtil.isTrust13(assertion, this.spVersion)) {
/* 300 */           this.trust13 = (Trust13)assertion;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Binding getBinding() {
/* 307 */     return this._binding;
/*     */   }
/*     */   
/*     */   private void collectPolicies() {
/* 311 */     for (AssertionSet assertionSet : this.effectivePolicy) {
/* 312 */       for (PolicyAssertion assertion : assertionSet) {
/* 313 */         if (PolicyUtil.isSignedParts(assertion, this.spVersion)) {
/* 314 */           this.signedParts.add((SignedParts)assertion); continue;
/* 315 */         }  if (PolicyUtil.isEncryptParts(assertion, this.spVersion)) {
/* 316 */           this.encryptedParts.add((EncryptedParts)assertion); continue;
/* 317 */         }  if (PolicyUtil.isSignedElements(assertion, this.spVersion)) {
/* 318 */           this.signedElements.add((SignedElements)assertion); continue;
/* 319 */         }  if (PolicyUtil.isEncryptedElements(assertion, this.spVersion)) {
/* 320 */           this.encryptedElements.add((EncryptedElements)assertion); continue;
/* 321 */         }  if (PolicyUtil.isWSS10(assertion, this.spVersion)) {
/* 322 */           this.wssAssertion = assertion; continue;
/* 323 */         }  if (PolicyUtil.isWSS11(assertion, this.spVersion)) {
/* 324 */           this.wssAssertion = assertion; continue;
/* 325 */         }  if (PolicyUtil.isTrust10(assertion, this.spVersion)) {
/* 326 */           this.trust10 = (Trust10)assertion; continue;
/* 327 */         }  if (PolicyUtil.isTrust13(assertion, this.spVersion)) {
/* 328 */           this.trust13 = (Trust13)assertion; continue;
/* 329 */         }  if (PolicyUtil.isBinding(assertion, this.spVersion)) {
/* 330 */           this._binding = (Binding)assertion; continue;
/* 331 */         }  if (PolicyUtil.isRequiredElements(assertion, this.spVersion)) {
/* 332 */           this.reqElements.add((RequiredElements)assertion);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean shouldAddST() {
/* 339 */     if (this.isServer && !this.isIncoming) {
/* 340 */       return false;
/*     */     }
/*     */     
/* 343 */     if (!this.isServer && this.isIncoming) {
/* 344 */       return false;
/*     */     }
/* 346 */     return true;
/*     */   }
/*     */   
/*     */   protected AlgorithmSuite getAlgoSuite(AlgorithmSuite suite) {
/* 350 */     AlgorithmSuite als = new AlgorithmSuite(suite.getDigestAlgorithm(), suite.getEncryptionAlgorithm(), suite.getSymmetricKeyAlgorithm(), suite.getAsymmetricKeyAlgorithm());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 355 */     als.setSignatureAlgorithm(suite.getSignatureAlgorithm());
/* 356 */     return als;
/*     */   }
/*     */   
/*     */   protected WSSAssertion getWssAssertion(WSSAssertion asser) {
/* 360 */     WSSAssertion assertion = new WSSAssertion(asser.getRequiredProperties(), asser.getType());
/*     */ 
/*     */     
/* 363 */     return assertion;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected MessageLayout getLayout(MessageLayout layout) {
/* 369 */     switch (layout) {
/*     */       case Strict:
/* 371 */         if (Constants.logger.isLoggable(Level.FINE)) {
/* 372 */           Constants.logger.log(Level.FINE, "MessageLayout has been configured to be  STRICT ");
/*     */         }
/* 374 */         return MessageLayout.Strict;
/*     */       
/*     */       case Lax:
/* 377 */         if (Constants.logger.isLoggable(Level.FINE)) {
/* 378 */           Constants.logger.log(Level.FINE, "MessageLayout has been configured to be LAX ");
/*     */         }
/* 380 */         return MessageLayout.Lax;
/*     */       
/*     */       case LaxTsFirst:
/* 383 */         if (Constants.logger.isLoggable(Level.FINE)) {
/* 384 */           Constants.logger.log(Level.FINE, "MessageLayout has been configured to be LaxTimestampFirst ");
/*     */         }
/* 386 */         return MessageLayout.LaxTsFirst;
/*     */       
/*     */       case LaxTsLast:
/* 389 */         if (Constants.logger.isLoggable(Level.FINE)) {
/* 390 */           Constants.logger.log(Level.FINE, "MessageLayout has been configured tp be LaxTimestampLast ");
/*     */         }
/* 392 */         return MessageLayout.LaxTsLast;
/*     */     } 
/* 394 */     if (Constants.logger.isLoggable(Level.SEVERE)) {
/* 395 */       Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0106_UNKNOWN_MESSAGE_LAYOUT(layout));
/*     */     }
/* 397 */     throw new RuntimeException(LogStringsMessages.SP_0106_UNKNOWN_MESSAGE_LAYOUT(layout));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIssuedTokenAsEncryptedSupportingToken() {
/* 403 */     return this.isIssuedTokenAsEncryptedSupportingToken;
/*     */   }
/*     */   
/*     */   private void isIssuedTokenAsEncryptedSupportingToken(boolean value) {
/* 407 */     this.isIssuedTokenAsEncryptedSupportingToken = value;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\XWSSPolicyGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */