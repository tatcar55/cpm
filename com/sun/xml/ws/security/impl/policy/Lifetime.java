/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.Lifetime;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Lifetime
/*     */   extends PolicyAssertion
/*     */   implements Lifetime, SecurityAssertionValidator
/*     */ {
/*     */   private String created;
/*     */   private String expires;
/*  68 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   
/*     */   private boolean populated = false;
/*     */   
/*     */   public Lifetime(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  73 */     super(name, nestedAssertions, nestedAlternative);
/*     */   }
/*     */   
/*     */   public String getCreated() {
/*  77 */     populate();
/*  78 */     return this.created;
/*     */   }
/*     */   
/*     */   public void setCreated(String created) {
/*  82 */     this.created = created;
/*     */   }
/*     */   
/*     */   public String getExpires() {
/*  86 */     populate();
/*  87 */     return this.expires;
/*     */   }
/*     */   
/*     */   public void setExpires(String expires) {
/*  91 */     this.expires = expires;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/*  95 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/*  98 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 102 */     if (!this.populated) {
/* 103 */       NestedPolicy policy = getNestedPolicy();
/* 104 */       if (policy == null) {
/* 105 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 106 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 108 */         this.populated = true;
/* 109 */         return this.fitness;
/*     */       } 
/* 111 */       AssertionSet as = policy.getAssertionSet();
/* 112 */       for (PolicyAssertion pa : as) {
/* 113 */         if (PolicyUtil.isCreated(pa)) {
/* 114 */           this.created = pa.getValue(); continue;
/* 115 */         }  if (PolicyUtil.isExpires(pa)) {
/* 116 */           this.expires = pa.getValue();
/*     */         }
/*     */       } 
/* 119 */       this.populated = true;
/*     */     } 
/* 121 */     return this.fitness;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\Lifetime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */