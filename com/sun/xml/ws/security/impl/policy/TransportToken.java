/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.HttpsToken;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.TransportToken;
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
/*     */ public class TransportToken
/*     */   extends Token
/*     */   implements TransportToken, SecurityAssertionValidator
/*     */ {
/*     */   private String id;
/*  57 */   private HttpsToken token = null;
/*     */   private boolean populated;
/*  59 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   
/*     */   private QName itQname;
/*     */   
/*     */   private String includeToken;
/*     */   
/*     */   public TransportToken() {
/*  66 */     this.id = PolicyUtil.randomUUID();
/*  67 */     this.itQname = new QName((getSecurityPolicyVersion()).namespaceUri, "IncludeToken");
/*  68 */     this.includeToken = "";
/*     */   }
/*     */   
/*     */   public TransportToken(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  72 */     super(name, nestedAssertions, nestedAlternative);
/*  73 */     this.id = PolicyUtil.randomUUID();
/*  74 */     this.itQname = new QName((getSecurityPolicyVersion()).namespaceUri, "IncludeToken");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/*  79 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/*  83 */     throw new UnsupportedOperationException("This method is not supported for TransportToken");
/*     */   }
/*     */   
/*     */   public void setIncludeToken(String type) {
/*  87 */     throw new UnsupportedOperationException("This method is not supported for TransportToken");
/*     */   }
/*     */   
/*     */   public HttpsToken getHttpsToken() {
/*  91 */     populate();
/*  92 */     return this.token;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHttpsToken(HttpsToken token) {}
/*     */ 
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 100 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 103 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 107 */     if (!this.populated) {
/* 108 */       this.includeToken = getAttributeValue(this.itQname);
/* 109 */       NestedPolicy policy = getNestedPolicy();
/* 110 */       AssertionSet assertionSet = policy.getAssertionSet();
/* 111 */       for (PolicyAssertion assertion : assertionSet) {
/* 112 */         if (PolicyUtil.isHttpsToken(assertion, getSecurityPolicyVersion())) {
/* 113 */           this.token = (HttpsToken)assertion; continue;
/*     */         } 
/* 115 */         if (!assertion.isOptional()) {
/* 116 */           Constants.log_invalid_assertion(assertion, isServer, "TransportToken");
/* 117 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 121 */       this.populated = true;
/*     */     } 
/* 123 */     return this.fitness;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\TransportToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */