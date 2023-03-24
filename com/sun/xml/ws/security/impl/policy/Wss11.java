/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.WSSAssertion;
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
/*     */ public class Wss11
/*     */   extends PolicyAssertion
/*     */   implements WSSAssertion, SecurityAssertionValidator
/*     */ {
/*  59 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   Set<String> requiredPropSet;
/*  61 */   String version = "1.1";
/*     */   
/*     */   boolean populated = false;
/*     */   
/*     */   private SecurityPolicyVersion spVersion;
/*     */ 
/*     */   
/*     */   public Wss11() {
/*  69 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   }
/*     */   
/*     */   public Wss11(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  73 */     super(name, nestedAssertions, nestedAlternative);
/*  74 */     String nsUri = getName().getNamespaceURI();
/*  75 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*     */   }
/*     */   
/*     */   public void addRequiredProperty(String requirement) {
/*  79 */     if (this.requiredPropSet == null) {
/*  80 */       this.requiredPropSet = new HashSet<String>();
/*     */     }
/*  82 */     this.requiredPropSet.add(requirement);
/*     */   }
/*     */   
/*     */   public Set<String> getRequiredProperties() {
/*  86 */     populate();
/*  87 */     return this.requiredPropSet;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  91 */     return this.version;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/*  95 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/*  98 */     populate(false);
/*     */   }
/*     */ 
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
/*     */       
/* 114 */       for (PolicyAssertion pa : as) {
/* 115 */         if (PolicyUtil.isWSS11PolicyContent(pa, this.spVersion)) {
/* 116 */           addRequiredProperty(pa.getName().getLocalPart().intern()); continue;
/*     */         } 
/* 118 */         if (!pa.isOptional()) {
/* 119 */           Constants.log_invalid_assertion(pa, isServer, "Wss11");
/* 120 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 126 */       this.populated = true;
/*     */     } 
/* 128 */     return this.fitness;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\Wss11.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */