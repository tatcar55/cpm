/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.KeyValueToken;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.util.Collection;
/*     */ import java.util.logging.Level;
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
/*     */ public class KeyValueToken
/*     */   extends PolicyAssertion
/*     */   implements KeyValueToken, Cloneable, SecurityAssertionValidator
/*     */ {
/*  60 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   private boolean populated = false;
/*  62 */   private String tokenType = null;
/*  63 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY12NS;
/*     */   private final QName itQname;
/*     */   private String includeToken;
/*  66 */   private String id = null;
/*     */   
/*     */   private boolean isServer = false;
/*     */   
/*     */   public KeyValueToken() {
/*  71 */     this.id = PolicyUtil.randomUUID();
/*  72 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  73 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */   
/*     */   public KeyValueToken(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  77 */     super(name, nestedAssertions, nestedAlternative);
/*     */     
/*  79 */     this.id = PolicyUtil.randomUUID();
/*     */     
/*  81 */     String nsUri = getName().getNamespaceURI();
/*  82 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*  83 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  84 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */   
/*     */   public void setTokenType(String tokenType) {
/*  88 */     this.tokenType = tokenType;
/*     */   }
/*     */   
/*     */   public String getTokenType() {
/*  92 */     populate();
/*  93 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/*  97 */     populate();
/*  98 */     return this.includeToken;
/*     */   }
/*     */   
/*     */   public void setIncludeToken(String type) {
/* 102 */     this.includeToken = type;
/*     */   }
/*     */   
/*     */   public String getTokenId() {
/* 106 */     return this.id;
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 110 */     return this.spVersion;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 114 */     return populate(isServer);
/*     */   }
/*     */   
/*     */   private void populate() {
/* 118 */     populate(false);
/*     */   }
/*     */   
/*     */   private SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 122 */     if (!this.populated) {
/* 123 */       if (getAttributeValue(this.itQname) != null) {
/* 124 */         this.includeToken = getAttributeValue(this.itQname);
/*     */       }
/* 126 */       NestedPolicy policy = getNestedPolicy();
/* 127 */       if (policy == null) {
/* 128 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 129 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 131 */         this.populated = true;
/* 132 */         return this.fitness;
/*     */       } 
/* 134 */       AssertionSet assertionSet = policy.getAssertionSet();
/* 135 */       for (PolicyAssertion assertion : assertionSet) {
/* 136 */         if (PolicyUtil.isKeyValueTokenType(assertion, this.spVersion)) {
/* 137 */           this.tokenType = assertion.getName().getLocalPart(); continue;
/*     */         } 
/* 139 */         if (!assertion.isOptional()) {
/* 140 */           Constants.log_invalid_assertion(assertion, isServer, "KeyValueToken");
/* 141 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 145 */       this.populated = true;
/*     */     } 
/* 147 */     return this.fitness;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\KeyValueToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */