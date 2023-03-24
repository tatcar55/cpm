/*     */ package com.sun.xml.ws.policy.spi;
/*     */ 
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractQNameValidator
/*     */   implements PolicyAssertionValidator
/*     */ {
/*  73 */   private final Set<String> supportedDomains = new HashSet<String>();
/*     */ 
/*     */ 
/*     */   
/*     */   private final Collection<QName> serverAssertions;
/*     */ 
/*     */ 
/*     */   
/*     */   private final Collection<QName> clientAssertions;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractQNameValidator(Collection<QName> serverSideAssertions, Collection<QName> clientSideAssertions) {
/*  87 */     if (serverSideAssertions != null) {
/*  88 */       this.serverAssertions = new HashSet<QName>(serverSideAssertions);
/*  89 */       for (QName assertion : this.serverAssertions) {
/*  90 */         this.supportedDomains.add(assertion.getNamespaceURI());
/*     */       }
/*     */     } else {
/*  93 */       this.serverAssertions = new HashSet<QName>(0);
/*     */     } 
/*     */     
/*  96 */     if (clientSideAssertions != null) {
/*  97 */       this.clientAssertions = new HashSet<QName>(clientSideAssertions);
/*  98 */       for (QName assertion : this.clientAssertions) {
/*  99 */         this.supportedDomains.add(assertion.getNamespaceURI());
/*     */       }
/*     */     } else {
/* 102 */       this.clientAssertions = new HashSet<QName>(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String[] declareSupportedDomains() {
/* 107 */     return this.supportedDomains.<String>toArray(new String[this.supportedDomains.size()]);
/*     */   }
/*     */   
/*     */   public PolicyAssertionValidator.Fitness validateClientSide(PolicyAssertion assertion) {
/* 111 */     return validateAssertion(assertion, this.clientAssertions, this.serverAssertions);
/*     */   }
/*     */   
/*     */   public PolicyAssertionValidator.Fitness validateServerSide(PolicyAssertion assertion) {
/* 115 */     return validateAssertion(assertion, this.serverAssertions, this.clientAssertions);
/*     */   }
/*     */   
/*     */   private PolicyAssertionValidator.Fitness validateAssertion(PolicyAssertion assertion, Collection<QName> thisSideAssertions, Collection<QName> otherSideAssertions) {
/* 119 */     QName assertionName = assertion.getName();
/* 120 */     if (thisSideAssertions.contains(assertionName))
/* 121 */       return PolicyAssertionValidator.Fitness.SUPPORTED; 
/* 122 */     if (otherSideAssertions.contains(assertionName)) {
/* 123 */       return PolicyAssertionValidator.Fitness.UNSUPPORTED;
/*     */     }
/* 125 */     return PolicyAssertionValidator.Fitness.UNKNOWN;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\spi\AbstractQNameValidator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */