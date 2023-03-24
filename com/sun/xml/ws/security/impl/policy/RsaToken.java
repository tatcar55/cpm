/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.RsaToken;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.util.Collection;
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
/*     */ public class RsaToken
/*     */   extends PolicyAssertion
/*     */   implements RsaToken, Cloneable, SecurityAssertionValidator
/*     */ {
/*  60 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   private boolean populated = false;
/*  62 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   private final QName itQname;
/*     */   private String includeToken;
/*  65 */   private String id = null;
/*     */   
/*     */   public RsaToken() {
/*  68 */     this.id = PolicyUtil.randomUUID();
/*  69 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  70 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */   
/*     */   public RsaToken(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  74 */     super(name, nestedAssertions, nestedAlternative);
/*  75 */     this.id = PolicyUtil.randomUUID();
/*     */     
/*  77 */     String nsUri = getName().getNamespaceURI();
/*  78 */     if ("http://schemas.microsoft.com/ws/2005/07/securitypolicy".equals(nsUri)) {
/*  79 */       this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */     }
/*  81 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  82 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/*  86 */     return populate(isServer);
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/*  90 */     populate();
/*  91 */     return this.includeToken;
/*     */   }
/*     */   
/*     */   public void setIncludeToken(String type) {
/*  95 */     this.includeToken = type;
/*     */   }
/*     */   
/*     */   public String getTokenId() {
/*  99 */     return this.id;
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 103 */     return this.spVersion;
/*     */   }
/*     */   
/*     */   private void populate() {
/* 107 */     populate(false);
/*     */   }
/*     */   
/*     */   private SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 111 */     if (!this.populated) {
/* 112 */       if (getAttributeValue(this.itQname) != null) {
/* 113 */         this.includeToken = getAttributeValue(this.itQname);
/*     */       }
/*     */       
/* 116 */       this.populated = true;
/*     */     } 
/* 118 */     return this.fitness;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\RsaToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */