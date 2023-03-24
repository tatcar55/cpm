/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.TrustAssertion;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ public class Trust13
/*     */   extends PolicyAssertion
/*     */   implements TrustAssertion, SecurityAssertionValidator
/*     */ {
/*     */   Set<String> requiredProps;
/*  62 */   String version = "1.3";
/*     */   private boolean populated = false;
/*  64 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   
/*     */   private SecurityPolicyVersion spVersion;
/*     */   
/*     */   public Trust13() {
/*  69 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY12NS;
/*     */   }
/*     */   
/*     */   public Trust13(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  73 */     super(name, nestedAssertions, nestedAlternative);
/*  74 */     String nsUri = getName().getNamespaceURI();
/*  75 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*     */   }
/*     */   
/*     */   public void addRequiredProperty(String requirement) {
/*  79 */     if (this.requiredProps == null) {
/*  80 */       this.requiredProps = new HashSet<String>();
/*     */     }
/*  82 */     this.requiredProps.add(requirement);
/*     */   }
/*     */   
/*     */   public Set getRequiredProperties() {
/*  86 */     populate();
/*  87 */     return this.requiredProps;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  91 */     return this.version;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/*  95 */     return populate(isServer);
/*     */   }
/*     */   
/*     */   private void populate() {
/*  99 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 103 */     if (!this.populated) {
/* 104 */       NestedPolicy policy = getNestedPolicy();
/* 105 */       if (policy == null) {
/* 106 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 107 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 109 */         this.populated = true;
/* 110 */         return this.fitness;
/*     */       } 
/* 112 */       AssertionSet as = policy.getAssertionSet();
/* 113 */       for (PolicyAssertion assertion : as) {
/* 114 */         if (PolicyUtil.isSupportClientChallenge(assertion, this.spVersion)) {
/* 115 */           addRequiredProperty("MustSupportClientChallenge"); continue;
/* 116 */         }  if (PolicyUtil.isSupportServerChallenge(assertion, this.spVersion)) {
/* 117 */           addRequiredProperty("MustSupportServerChallenge"); continue;
/* 118 */         }  if (PolicyUtil.isRequireClientEntropy(assertion, this.spVersion)) {
/* 119 */           addRequiredProperty("RequireClientEntropy"); continue;
/* 120 */         }  if (PolicyUtil.isRequireServerEntropy(assertion, this.spVersion)) {
/* 121 */           addRequiredProperty("RequireServerEntropy"); continue;
/* 122 */         }  if (PolicyUtil.isSupportIssuedTokens(assertion, this.spVersion)) {
/* 123 */           addRequiredProperty("MustSupportIssuedTokens"); continue;
/* 124 */         }  if (PolicyUtil.isRequestSecurityTokenCollection(assertion, this.spVersion)) {
/* 125 */           addRequiredProperty("RequireRequestSecurityTokenCollection"); continue;
/* 126 */         }  if (PolicyUtil.isAppliesTo(assertion, this.spVersion)) {
/* 127 */           addRequiredProperty("RequireAppliesTo"); continue;
/*     */         } 
/* 129 */         if (!assertion.isOptional()) {
/* 130 */           Constants.log_invalid_assertion(assertion, isServer, "Trust13");
/* 131 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 135 */       this.populated = true;
/*     */     } 
/* 137 */     return this.fitness;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\Trust13.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */