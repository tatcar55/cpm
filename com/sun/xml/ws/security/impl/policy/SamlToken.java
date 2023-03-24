/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.Claims;
/*     */ import com.sun.xml.ws.security.policy.Issuer;
/*     */ import com.sun.xml.ws.security.policy.IssuerName;
/*     */ import com.sun.xml.ws.security.policy.SamlToken;
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
/*     */ 
/*     */ public class SamlToken
/*     */   extends PolicyAssertion
/*     */   implements SamlToken, SecurityAssertionValidator
/*     */ {
/*     */   private String id;
/*     */   private List<String> tokenRefType;
/*     */   private String tokenType;
/*  75 */   private PolicyAssertion rdKey = null;
/*     */   private boolean populated = false;
/*  77 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*  78 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   private final QName itQname;
/*     */   private String includeTokenType;
/*  81 */   private Issuer issuer = null;
/*  82 */   private IssuerName issuerName = null;
/*  83 */   private Claims claims = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public SamlToken(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  88 */     super(name, nestedAssertions, nestedAlternative);
/*  89 */     this.id = PolicyUtil.randomUUID();
/*  90 */     String nsUri = getName().getNamespaceURI();
/*  91 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*  92 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  93 */     this.includeTokenType = this.spVersion.includeTokenAlways;
/*     */   }
/*     */   
/*     */   public String getTokenType() {
/*  97 */     populate();
/*  98 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public Iterator getTokenRefernceType() {
/* 102 */     if (this.tokenRefType != null) {
/* 103 */       return this.tokenRefType.iterator();
/*     */     }
/* 105 */     return Collections.emptyList().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRequireDerivedKeys() {
/* 110 */     populate();
/* 111 */     if (this.rdKey != null) {
/* 112 */       return true;
/*     */     }
/* 114 */     return false;
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/* 118 */     populate();
/* 119 */     return this.includeTokenType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/* 124 */     return this.id;
/*     */   }
/*     */   
/*     */   public Issuer getIssuer() {
/* 128 */     populate();
/* 129 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public IssuerName getIssuerName() {
/* 133 */     populate();
/* 134 */     return this.issuerName;
/*     */   }
/*     */   
/*     */   public Claims getClaims() {
/* 138 */     populate();
/* 139 */     return this.claims;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 143 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 146 */     populate(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 151 */     if (!this.populated) {
/* 152 */       NestedPolicy policy = getNestedPolicy();
/* 153 */       if (getAttributeValue(this.itQname) != null) {
/* 154 */         this.includeTokenType = getAttributeValue(this.itQname);
/*     */       }
/* 156 */       if (policy == null) {
/* 157 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 158 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 160 */         this.populated = true;
/* 161 */         return this.fitness;
/*     */       } 
/* 163 */       AssertionSet as = policy.getAssertionSet();
/* 164 */       Iterator<PolicyAssertion> paItr = as.iterator();
/*     */       
/* 166 */       while (paItr.hasNext()) {
/* 167 */         PolicyAssertion assertion = paItr.next();
/* 168 */         if (PolicyUtil.isSamlTokenType(assertion, this.spVersion)) {
/* 169 */           this.tokenType = assertion.getName().getLocalPart().intern(); continue;
/* 170 */         }  if (PolicyUtil.isRequireDerivedKeys(assertion, this.spVersion)) {
/* 171 */           this.rdKey = assertion; continue;
/* 172 */         }  if (PolicyUtil.isRequireKeyIR(assertion, this.spVersion)) {
/* 173 */           if (this.tokenRefType == null) {
/* 174 */             this.tokenRefType = new ArrayList<String>();
/*     */           }
/* 176 */           this.tokenRefType.add(assertion.getName().getLocalPart().intern()); continue;
/*     */         } 
/* 178 */         if (!assertion.isOptional()) {
/* 179 */           Constants.log_invalid_assertion(assertion, isServer, "SamlToken");
/* 180 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 184 */       if (hasParameters()) {
/* 185 */         Iterator<PolicyAssertion> it = getParametersIterator();
/* 186 */         while (it.hasNext()) {
/* 187 */           PolicyAssertion assertion = it.next();
/* 188 */           if (PolicyUtil.isIssuer(assertion, this.spVersion)) {
/* 189 */             this.issuer = (Issuer)assertion; continue;
/* 190 */           }  if (PolicyUtil.isIssuerName(assertion, this.spVersion)) {
/* 191 */             this.issuerName = (IssuerName)assertion; continue;
/* 192 */           }  if (PolicyUtil.isClaimsElement(assertion) && SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(this.spVersion.namespaceUri))
/*     */           {
/* 194 */             this.claims = (Claims)assertion;
/*     */           }
/*     */         } 
/*     */       } 
/* 198 */       if (this.issuer != null && this.issuerName != null) {
/* 199 */         Constants.log_invalid_assertion(this.issuerName, isServer, "SecureConversationToken");
/* 200 */         this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_INVALID_VALUE;
/*     */       } 
/* 202 */       this.populated = true;
/*     */     } 
/* 204 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 208 */     return this.spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\SamlToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */