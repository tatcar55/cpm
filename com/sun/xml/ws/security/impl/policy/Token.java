/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.Token;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ public class Token
/*     */   extends PolicyAssertion
/*     */   implements Token, SecurityAssertionValidator
/*     */ {
/*     */   private String _id;
/*     */   private boolean populated = false;
/*     */   private Token _token;
/*  66 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*  67 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   
/*     */   private final QName itQname;
/*     */   
/*     */   private String _includeToken;
/*     */ 
/*     */   
/*     */   public Token() {
/*  75 */     this._id = PolicyUtil.randomUUID();
/*  76 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  77 */     this._includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */   
/*     */   public Token(QName name) {
/*  81 */     this._id = PolicyUtil.randomUUID();
/*  82 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  83 */     this._includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */   
/*     */   public Token(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  87 */     super(name, nestedAssertions, nestedAlternative);
/*  88 */     String nsUri = getName().getNamespaceURI();
/*  89 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*  90 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  91 */     this._includeToken = this.spVersion.includeTokenAlways;
/*  92 */     this._id = PolicyUtil.randomUUID();
/*     */   }
/*     */   
/*     */   public Token getToken() {
/*  96 */     populate();
/*  97 */     return this._token;
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/* 101 */     populate();
/* 102 */     return this._includeToken;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIncludeToken(String type) {}
/*     */ 
/*     */   
/*     */   public void setToken(Token token) {}
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/* 114 */     return this._id;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 118 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 121 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 125 */     if (!this.populated) {
/* 126 */       String tValue = getAttributeValue(this.itQname);
/* 127 */       if (tValue != null) {
/* 128 */         this._includeToken = tValue;
/*     */       }
/* 130 */       NestedPolicy policy = getNestedPolicy();
/* 131 */       if (policy == null) {
/* 132 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 133 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 135 */         this.populated = true;
/* 136 */         return this.fitness;
/*     */       } 
/* 138 */       AssertionSet as = policy.getAssertionSet();
/* 139 */       Iterator<PolicyAssertion> ast = as.iterator();
/* 140 */       while (ast.hasNext()) {
/* 141 */         PolicyAssertion assertion = ast.next();
/* 142 */         if (PolicyUtil.isToken(assertion, this.spVersion)) {
/* 143 */           this._token = (Token)assertion; continue;
/*     */         } 
/* 145 */         if (!assertion.isOptional()) {
/* 146 */           Constants.log_invalid_assertion(assertion, isServer, "Token");
/* 147 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 152 */       this.populated = true;
/*     */     } 
/* 154 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 158 */     return this.spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\Token.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */