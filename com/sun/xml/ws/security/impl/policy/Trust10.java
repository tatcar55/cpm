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
/*     */ public class Trust10
/*     */   extends PolicyAssertion
/*     */   implements TrustAssertion, SecurityAssertionValidator
/*     */ {
/*     */   Set<String> requiredProps;
/*  61 */   String version = "1.0";
/*     */   private boolean populated = false;
/*  63 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */ 
/*     */   
/*     */   private SecurityPolicyVersion spVersion;
/*     */ 
/*     */   
/*     */   public Trust10() {
/*  70 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   }
/*     */ 
/*     */   
/*     */   public Trust10(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  75 */     super(name, nestedAssertions, nestedAlternative);
/*  76 */     String nsUri = getName().getNamespaceURI();
/*  77 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*     */   }
/*     */   
/*     */   public void addRequiredProperty(String requirement) {
/*  81 */     if (this.requiredProps == null) {
/*  82 */       this.requiredProps = new HashSet<String>();
/*     */     }
/*  84 */     this.requiredProps.add(requirement);
/*     */   }
/*     */   
/*     */   public Set getRequiredProperties() {
/*  88 */     populate();
/*  89 */     return this.requiredProps;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  93 */     return this.version;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/*  97 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 100 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 104 */     if (!this.populated) {
/* 105 */       NestedPolicy policy = getNestedPolicy();
/* 106 */       if (policy == null) {
/* 107 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 108 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 110 */         this.populated = true;
/* 111 */         return this.fitness;
/*     */       } 
/* 113 */       AssertionSet as = policy.getAssertionSet();
/* 114 */       for (PolicyAssertion assertion : as) {
/* 115 */         if (PolicyUtil.isSupportClientChallenge(assertion, this.spVersion)) {
/* 116 */           addRequiredProperty("MustSupportClientChallenge"); continue;
/* 117 */         }  if (PolicyUtil.isSupportServerChallenge(assertion, this.spVersion)) {
/* 118 */           addRequiredProperty("MustSupportServerChallenge"); continue;
/* 119 */         }  if (PolicyUtil.isRequireClientEntropy(assertion, this.spVersion)) {
/* 120 */           addRequiredProperty("RequireClientEntropy"); continue;
/* 121 */         }  if (PolicyUtil.isRequireServerEntropy(assertion, this.spVersion)) {
/* 122 */           addRequiredProperty("RequireServerEntropy"); continue;
/* 123 */         }  if (PolicyUtil.isSupportIssuedTokens(assertion, this.spVersion)) {
/* 124 */           addRequiredProperty("MustSupportIssuedTokens"); continue;
/*     */         } 
/* 126 */         if (!assertion.isOptional()) {
/* 127 */           Constants.log_invalid_assertion(assertion, isServer, "Trust10");
/* 128 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 133 */       this.populated = true;
/*     */     } 
/* 135 */     return this.fitness;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\Trust10.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */