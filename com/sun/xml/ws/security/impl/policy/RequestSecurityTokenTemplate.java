/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.Claims;
/*     */ import com.sun.xml.ws.security.policy.Lifetime;
/*     */ import com.sun.xml.ws.security.policy.RequestSecurityTokenTemplate;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RequestSecurityTokenTemplate
/*     */   extends PolicyAssertion
/*     */   implements RequestSecurityTokenTemplate, SecurityAssertionValidator
/*     */ {
/*     */   private boolean populated = false;
/*  58 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   String tokenType;
/*     */   String requestType;
/*     */   Lifetime lifeTime;
/*     */   String authenticationType;
/*     */   private String keyType;
/*     */   private int keySize;
/*     */   private String sigAlgo;
/*     */   private String encAlgo;
/*     */   private String canonAlgo;
/*     */   private boolean isProofEncRequired = false;
/*     */   private String computedKeyAlgo;
/*     */   private boolean isEncRequired = false;
/*     */   private String signWith;
/*     */   private String encryptWith;
/*     */   private String keyWrapAlgo;
/*     */   private String wstVer;
/*  75 */   private Claims claims = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RequestSecurityTokenTemplate(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  84 */     super(name, nestedAssertions, nestedAlternative);
/*     */   }
/*     */   
/*     */   public String getTokenType() {
/*  88 */     populate();
/*  89 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public String getRequestType() {
/*  93 */     populate();
/*  94 */     return this.requestType;
/*     */   }
/*     */   
/*     */   public Lifetime getLifetime() {
/*  98 */     populate();
/*  99 */     return this.lifeTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAuthenticationType() {
/* 104 */     populate();
/* 105 */     return this.authenticationType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKeyType() {
/* 110 */     populate();
/* 111 */     return this.keyType;
/*     */   }
/*     */   
/*     */   public int getKeySize() {
/* 115 */     populate();
/* 116 */     return this.keySize;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSignatureAlgorithm() {
/* 122 */     populate();
/* 123 */     return this.sigAlgo;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEncryptionAlgorithm() {
/* 128 */     populate();
/* 129 */     return this.encAlgo;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCanonicalizationAlgorithm() {
/* 134 */     populate();
/* 135 */     return this.canonAlgo;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getProofEncryptionRequired() {
/* 140 */     populate();
/* 141 */     return this.isProofEncRequired;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getComputedKeyAlgorithm() {
/* 147 */     populate();
/* 148 */     return this.computedKeyAlgo;
/*     */   }
/*     */   
/*     */   public String getKeyWrapAlgorithm() {
/* 152 */     populate();
/* 153 */     return this.keyWrapAlgo;
/*     */   }
/*     */   
/*     */   public boolean getEncryptionRequired() {
/* 157 */     populate();
/* 158 */     return this.isEncRequired;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSignWith() {
/* 164 */     populate();
/* 165 */     return this.signWith;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEncryptWith() {
/* 170 */     populate();
/* 171 */     return this.encryptWith;
/*     */   }
/*     */   
/*     */   public Claims getClaims() {
/* 175 */     populate();
/* 176 */     return this.claims;
/*     */   }
/*     */   
/*     */   public String getTrustVersion() {
/* 180 */     populate();
/* 181 */     return this.wstVer;
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 186 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 189 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 193 */     if (!this.populated) {
/* 194 */       if (hasNestedAssertions()) {
/*     */         
/* 196 */         Iterator<PolicyAssertion> it = getNestedAssertionsIterator();
/* 197 */         while (it.hasNext()) {
/* 198 */           PolicyAssertion assertion = it.next();
/* 199 */           if (this.wstVer == null) {
/* 200 */             this.wstVer = assertion.getName().getNamespaceURI();
/*     */           }
/*     */           
/* 203 */           if (PolicyUtil.isKeyType(assertion)) {
/* 204 */             this.keyType = assertion.getValue(); continue;
/* 205 */           }  if (PolicyUtil.isKeySize(assertion)) {
/* 206 */             this.keySize = Integer.valueOf(assertion.getValue()).intValue(); continue;
/* 207 */           }  if (PolicyUtil.isEncryption(assertion)) {
/* 208 */             this.isEncRequired = true; continue;
/* 209 */           }  if (PolicyUtil.isProofEncryption(assertion)) {
/* 210 */             this.isProofEncRequired = true; continue;
/* 211 */           }  if (PolicyUtil.isLifeTime(assertion)) {
/* 212 */             this.lifeTime = (Lifetime)assertion; continue;
/* 213 */           }  if (PolicyUtil.isSignWith(assertion)) {
/* 214 */             this.signWith = assertion.getValue(); continue;
/* 215 */           }  if (PolicyUtil.isEncryptWith(assertion)) {
/* 216 */             this.encryptWith = assertion.getValue(); continue;
/* 217 */           }  if (PolicyUtil.isTrustTokenType(assertion)) {
/* 218 */             this.tokenType = assertion.getValue(); continue;
/* 219 */           }  if (PolicyUtil.isRequestType(assertion)) {
/* 220 */             this.requestType = assertion.getValue(); continue;
/* 221 */           }  if (PolicyUtil.isAuthenticationType(assertion)) {
/* 222 */             this.authenticationType = assertion.getValue(); continue;
/* 223 */           }  if (PolicyUtil.isSignatureAlgorithm(assertion)) {
/* 224 */             this.sigAlgo = assertion.getValue(); continue;
/* 225 */           }  if (PolicyUtil.isEncryptionAlgorithm(assertion)) {
/* 226 */             this.encAlgo = assertion.getValue(); continue;
/* 227 */           }  if (PolicyUtil.isCanonicalizationAlgorithm(assertion)) {
/* 228 */             this.canonAlgo = assertion.getValue(); continue;
/* 229 */           }  if (PolicyUtil.isComputedKeyAlgorithm(assertion)) {
/* 230 */             this.computedKeyAlgo = assertion.getValue(); continue;
/* 231 */           }  if (PolicyUtil.isKeyWrapAlgorithm(assertion)) {
/* 232 */             this.keyWrapAlgo = assertion.getValue(); continue;
/* 233 */           }  if (PolicyUtil.isEncryption(assertion)) {
/* 234 */             this.isEncRequired = true; continue;
/* 235 */           }  if (PolicyUtil.isClaimsElement(assertion)) {
/* 236 */             this.claims = (Claims)assertion; continue;
/* 237 */           }  if (PolicyUtil.isEntropyElement(assertion)) {
/*     */             continue;
/*     */           }
/* 240 */           if (!assertion.isOptional()) {
/* 241 */             Constants.log_invalid_assertion(assertion, isServer, "RequestSecurityTokenTemplate");
/* 242 */             this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 248 */       this.populated = true;
/*     */     } 
/* 250 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public RequestSecurityTokenTemplate() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\RequestSecurityTokenTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */