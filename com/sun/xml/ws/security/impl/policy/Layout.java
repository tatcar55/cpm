/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.MessageLayout;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Layout
/*     */   extends PolicyAssertion
/*     */   implements SecurityAssertionValidator
/*     */ {
/*     */   MessageLayout ml;
/*  61 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   
/*     */   private boolean populated = false;
/*     */   
/*     */   private SecurityPolicyVersion spVersion;
/*     */   
/*     */   public Layout() {
/*  68 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   }
/*     */   
/*     */   public Layout(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  72 */     super(name, nestedAssertions, nestedAlternative);
/*  73 */     String nsUri = getName().getNamespaceURI();
/*  74 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*     */   }
/*     */   public MessageLayout getMessageLayout() {
/*  77 */     populate();
/*  78 */     return this.ml;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/*  82 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/*  85 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/*  89 */     if (!this.populated) {
/*  90 */       NestedPolicy policy = getNestedPolicy();
/*  91 */       AssertionSet assertionSet = policy.getAssertionSet();
/*  92 */       for (PolicyAssertion assertion : assertionSet) {
/*  93 */         if (PolicyUtil.isLax(assertion, this.spVersion)) {
/*  94 */           this.ml = MessageLayout.Lax; continue;
/*  95 */         }  if (PolicyUtil.isLaxTsFirst(assertion, this.spVersion)) {
/*  96 */           this.ml = MessageLayout.LaxTsFirst; continue;
/*  97 */         }  if (PolicyUtil.isLaxTsLast(assertion, this.spVersion)) {
/*  98 */           this.ml = MessageLayout.LaxTsLast; continue;
/*  99 */         }  if (PolicyUtil.isStrict(assertion, this.spVersion)) {
/* 100 */           this.ml = MessageLayout.Strict; continue;
/*     */         } 
/* 102 */         if (!assertion.isOptional()) {
/* 103 */           Constants.log_invalid_assertion(assertion, isServer, "Layout");
/* 104 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 108 */       this.populated = true;
/*     */     } 
/* 110 */     return this.fitness;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\Layout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */