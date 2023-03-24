/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.Issuer;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.SpnegoContextToken;
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
/*     */ public class SpnegoContextToken
/*     */   extends PolicyAssertion
/*     */   implements SpnegoContextToken, SecurityAssertionValidator
/*     */ {
/*     */   private boolean populated = false;
/*  64 */   private PolicyAssertion rdKey = null;
/*     */   
/*     */   private String id;
/*  67 */   private Issuer issuer = null;
/*  68 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*  69 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   
/*     */   private final QName itQname;
/*     */   
/*     */   private String includeToken;
/*     */ 
/*     */   
/*     */   public SpnegoContextToken() {
/*  77 */     this.id = PolicyUtil.randomUUID();
/*  78 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  79 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */   
/*     */   public SpnegoContextToken(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  83 */     super(name, nestedAssertions, nestedAlternative);
/*  84 */     this.id = PolicyUtil.randomUUID();
/*  85 */     String nsUri = getName().getNamespaceURI();
/*  86 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*  87 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  88 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */ 
/*     */   
/*     */   public Issuer getIssuer() {
/*  93 */     populate();
/*  94 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public boolean isRequireDerivedKeys() {
/*  98 */     populate();
/*  99 */     if (this.rdKey != null) {
/* 100 */       return true;
/*     */     }
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIncludeToken() {
/* 107 */     populate();
/* 108 */     return this.includeToken;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/* 113 */     return this.id;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 117 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 120 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 124 */     if (!this.populated) {
/* 125 */       NestedPolicy policy = getNestedPolicy();
/* 126 */       if (getAttributeValue(this.itQname) != null) {
/* 127 */         this.includeToken = getAttributeValue(this.itQname);
/*     */       }
/* 129 */       if (policy == null) {
/* 130 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 131 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 133 */         this.populated = true;
/* 134 */         return this.fitness;
/*     */       } 
/* 136 */       AssertionSet as = policy.getAssertionSet();
/* 137 */       Iterator<PolicyAssertion> paItr = as.iterator();
/*     */       
/* 139 */       while (paItr.hasNext()) {
/* 140 */         PolicyAssertion assertion = paItr.next();
/* 141 */         if (PolicyUtil.isRequireDerivedKeys(assertion, this.spVersion)) {
/* 142 */           this.rdKey = assertion; continue;
/*     */         } 
/* 144 */         if (!assertion.isOptional()) {
/* 145 */           Constants.log_invalid_assertion(assertion, isServer, "SpnegoContextToken");
/* 146 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 151 */       if (hasNestedAssertions()) {
/* 152 */         Iterator<PolicyAssertion> it = getNestedAssertionsIterator();
/* 153 */         while (it.hasNext()) {
/* 154 */           PolicyAssertion assertion = it.next();
/* 155 */           if (PolicyUtil.isIssuer(assertion, this.spVersion)) {
/* 156 */             this.issuer = (Issuer)assertion;
/*     */           }
/*     */         } 
/*     */       } 
/* 160 */       this.populated = true;
/*     */     } 
/* 162 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 166 */     return this.spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\SpnegoContextToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */