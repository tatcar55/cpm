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
/*     */ 
/*     */ public class Wss10
/*     */   extends PolicyAssertion
/*     */   implements WSSAssertion, SecurityAssertionValidator
/*     */ {
/*     */   Set<String> requiredPropSet;
/*  61 */   String version = "1.0";
/*     */   boolean populated = false;
/*  63 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */ 
/*     */   
/*     */   private SecurityPolicyVersion spVersion;
/*     */ 
/*     */   
/*     */   public Wss10() {
/*  70 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   }
/*     */   
/*     */   public Wss10(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  74 */     super(name, nestedAssertions, nestedAlternative);
/*  75 */     String nsUri = getName().getNamespaceURI();
/*  76 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRequiredProperty(String requirement) {
/*  82 */     if (this.requiredPropSet == null) {
/*  83 */       this.requiredPropSet = new HashSet<String>();
/*     */     }
/*  85 */     this.requiredPropSet.add(requirement);
/*     */   }
/*     */   
/*     */   public Set<String> getRequiredProperties() {
/*  89 */     populate();
/*  90 */     return this.requiredPropSet;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  94 */     return this.version;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/*  98 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 101 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 105 */     if (!this.populated) {
/* 106 */       NestedPolicy policy = getNestedPolicy();
/* 107 */       if (policy == null) {
/* 108 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 109 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 111 */         this.populated = true;
/* 112 */         return this.fitness;
/*     */       } 
/* 114 */       AssertionSet as = policy.getAssertionSet();
/* 115 */       for (PolicyAssertion pa : as) {
/* 116 */         if (PolicyUtil.isWSS10PolicyContent(pa, this.spVersion)) {
/* 117 */           addRequiredProperty(pa.getName().getLocalPart().intern()); continue;
/*     */         } 
/* 119 */         if (!pa.isOptional()) {
/* 120 */           Constants.log_invalid_assertion(pa, isServer, "Wss10");
/* 121 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 125 */       this.populated = true;
/*     */     } 
/* 127 */     return this.fitness;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\Wss10.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */