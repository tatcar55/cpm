/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.RelToken;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ public class RelToken
/*     */   extends PolicyAssertion
/*     */   implements RelToken, SecurityAssertionValidator
/*     */ {
/*     */   private String id;
/*     */   private List<String> tokenRefType;
/*     */   private boolean populated = false;
/*     */   private String tokenType;
/*  72 */   private PolicyAssertion rdKey = null;
/*  73 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */ 
/*     */   
/*  76 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   
/*     */   private final QName itQname;
/*     */   private String includeToken;
/*     */   
/*     */   public RelToken(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  82 */     super(name, nestedAssertions, nestedAlternative);
/*  83 */     this.id = PolicyUtil.randomUUID();
/*  84 */     String nsUri = getName().getNamespaceURI();
/*  85 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*  86 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  87 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTokenType() {
/*  92 */     populate();
/*  93 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public Iterator getTokenRefernceType() {
/*  97 */     if (this.tokenRefType != null) {
/*  98 */       return this.tokenRefType.iterator();
/*     */     }
/* 100 */     return Collections.emptyList().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRequireDerivedKeys() {
/* 105 */     populate();
/* 106 */     if (this.rdKey != null) {
/* 107 */       return true;
/*     */     }
/* 109 */     return false;
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/* 113 */     populate();
/* 114 */     return this.includeToken;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/* 119 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 124 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 127 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 131 */     if (!this.populated) {
/* 132 */       NestedPolicy policy = getNestedPolicy();
/* 133 */       this.includeToken = getAttributeValue(this.itQname);
/* 134 */       if (policy == null) {
/* 135 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 136 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 138 */         this.populated = true;
/* 139 */         return this.fitness;
/*     */       } 
/* 141 */       AssertionSet as = policy.getAssertionSet();
/* 142 */       Iterator<PolicyAssertion> paItr = as.iterator();
/*     */       
/* 144 */       while (paItr.hasNext()) {
/* 145 */         PolicyAssertion assertion = paItr.next();
/* 146 */         if (PolicyUtil.isRelTokenType(assertion, this.spVersion)) {
/* 147 */           this.tokenType = assertion.getName().getLocalPart().intern(); continue;
/* 148 */         }  if (PolicyUtil.isRequireDerivedKeys(assertion, this.spVersion)) {
/* 149 */           this.rdKey = assertion; continue;
/* 150 */         }  if (PolicyUtil.isRequireKeyIR(assertion, this.spVersion)) {
/* 151 */           if (this.tokenRefType == null) {
/* 152 */             this.tokenRefType = new ArrayList<String>();
/*     */           }
/* 154 */           this.tokenRefType.add(assertion.getName().getLocalPart().intern()); continue;
/*     */         } 
/* 156 */         if (!assertion.isOptional()) {
/*     */           
/* 158 */           Constants.log_invalid_assertion(assertion, isServer, "RelToken");
/* 159 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 163 */       this.populated = true;
/*     */     } 
/* 165 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 169 */     return this.spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\RelToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */