/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityContextToken;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
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
/*     */ public class SecurityContextToken
/*     */   extends PolicyAssertion
/*     */   implements SecurityContextToken, SecurityAssertionValidator
/*     */ {
/*     */   private String id;
/*     */   private boolean populated = false;
/*     */   private String tokenType;
/*  71 */   private PolicyAssertion rdKey = null;
/*  72 */   private Set<String> referenceType = null;
/*  73 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*  74 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   
/*     */   private final QName itQname;
/*     */   private String includeToken;
/*     */   
/*     */   public SecurityContextToken(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  80 */     super(name, nestedAssertions, nestedAlternative);
/*  81 */     this.id = PolicyUtil.randomUUID();
/*  82 */     String nsUri = getName().getNamespaceURI();
/*  83 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*  84 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  85 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */   
/*     */   public String getTokenType() {
/*  89 */     populate();
/*  90 */     return this.tokenType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getTokenRefernceType() {
/* 100 */     return Collections.emptyList().iterator();
/*     */   }
/*     */   
/*     */   public boolean isRequireDerivedKeys() {
/* 104 */     populate();
/* 105 */     if (this.rdKey != null) {
/* 106 */       return true;
/*     */     }
/* 108 */     return false;
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/* 112 */     populate();
/* 113 */     return this.includeToken;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/* 118 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 123 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 126 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 130 */     if (!this.populated) {
/* 131 */       NestedPolicy policy = getNestedPolicy();
/* 132 */       if (getAttributeValue(this.itQname) != null) {
/* 133 */         this.includeToken = getAttributeValue(this.itQname);
/*     */       }
/* 135 */       if (policy == null) {
/* 136 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 137 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 139 */         this.populated = true;
/* 140 */         return this.fitness;
/*     */       } 
/* 142 */       AssertionSet as = policy.getAssertionSet();
/* 143 */       Iterator<PolicyAssertion> paItr = as.iterator();
/*     */       
/* 145 */       while (paItr.hasNext()) {
/* 146 */         PolicyAssertion assertion = paItr.next();
/* 147 */         if (PolicyUtil.isSecurityContextTokenType(assertion, this.spVersion)) {
/* 148 */           this.tokenType = assertion.getName().getLocalPart().intern(); continue;
/* 149 */         }  if (PolicyUtil.isRequireDerivedKeys(assertion, this.spVersion)) {
/* 150 */           this.rdKey = assertion; continue;
/* 151 */         }  if (PolicyUtil.isRequireExternalUriReference(assertion, this.spVersion)) {
/* 152 */           if (this.referenceType == null) {
/* 153 */             this.referenceType = new HashSet<String>();
/*     */           }
/* 155 */           this.referenceType.add(assertion.getName().getLocalPart().intern()); continue;
/*     */         } 
/* 157 */         if (!assertion.isOptional()) {
/* 158 */           if (Constants.logger.getLevel() == Level.SEVERE) {
/* 159 */             Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0100_INVALID_SECURITY_ASSERTION(assertion, "SecurityContextToken"));
/*     */           }
/* 161 */           if (isServer) {
/* 162 */             throw new UnsupportedPolicyAssertion("Policy assertion " + assertion + " is not supported under SecurityContextToken assertion");
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 168 */       this.populated = true;
/*     */     } 
/* 170 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 174 */     return this.spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\SecurityContextToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */