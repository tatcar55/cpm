/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.Claims;
/*     */ import com.sun.xml.ws.security.policy.Issuer;
/*     */ import com.sun.xml.ws.security.policy.IssuerName;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.X509Token;
/*     */ import java.util.Collection;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class X509Token
/*     */   extends PolicyAssertion
/*     */   implements X509Token, Cloneable, SecurityAssertionValidator
/*     */ {
/*  74 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   private boolean populated = false;
/*  76 */   private String tokenType = null;
/*  77 */   private HashSet<String> referenceType = null;
/*  78 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   private final QName itQname;
/*     */   private String includeToken;
/*  81 */   private Issuer issuer = null;
/*  82 */   private IssuerName issuerName = null;
/*  83 */   private Claims claims = null;
/*  84 */   private String id = null;
/*     */ 
/*     */   
/*     */   private boolean reqDK = false;
/*     */ 
/*     */   
/*     */   public X509Token() {
/*  91 */     this.id = PolicyUtil.randomUUID();
/*  92 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  93 */     this.includeToken = this.spVersion.includeTokenAlways;
/*  94 */     this.referenceType = new HashSet<String>();
/*     */   }
/*     */   
/*     */   public X509Token(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  98 */     super(name, nestedAssertions, nestedAlternative);
/*     */     
/* 100 */     this.id = PolicyUtil.randomUUID();
/* 101 */     this.referenceType = new HashSet<String>();
/*     */     
/* 103 */     String nsUri = getName().getNamespaceURI();
/* 104 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/* 105 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/* 106 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTokenReferenceType(String tokenRefType) {
/* 112 */     this.referenceType.add(tokenRefType);
/*     */   }
/*     */   
/*     */   public void setTokenType(String tokenType) {
/* 116 */     this.tokenType = tokenType;
/*     */   }
/*     */   
/*     */   public String getTokenType() {
/* 120 */     populate();
/* 121 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public Set getTokenRefernceType() {
/* 125 */     populate();
/* 126 */     return this.referenceType;
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/* 130 */     populate();
/* 131 */     return this.includeToken;
/*     */   }
/*     */   
/*     */   public void setIncludeToken(String type) {
/* 135 */     this.includeToken = type;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/* 140 */     return this.id;
/*     */   }
/*     */   
/*     */   public boolean isRequireDerivedKeys() {
/* 144 */     populate();
/* 145 */     return this.reqDK;
/*     */   }
/*     */   
/*     */   public Issuer getIssuer() {
/* 149 */     populate();
/* 150 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public IssuerName getIssuerName() {
/* 154 */     populate();
/* 155 */     return this.issuerName;
/*     */   }
/*     */   
/*     */   public Claims getClaims() {
/* 159 */     populate();
/* 160 */     return this.claims;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 164 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 167 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 171 */     if (!this.populated) {
/* 172 */       if (getAttributeValue(this.itQname) != null) {
/* 173 */         this.includeToken = getAttributeValue(this.itQname);
/*     */       }
/* 175 */       NestedPolicy policy = getNestedPolicy();
/* 176 */       if (policy == null) {
/* 177 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 178 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 180 */         this.populated = true;
/* 181 */         return this.fitness;
/*     */       } 
/* 183 */       AssertionSet assertionSet = policy.getAssertionSet();
/* 184 */       for (PolicyAssertion assertion : assertionSet) {
/* 185 */         if (PolicyUtil.isTokenReferenceType(assertion, this.spVersion)) {
/* 186 */           this.referenceType.add(assertion.getName().getLocalPart().intern()); continue;
/* 187 */         }  if (PolicyUtil.isTokenType(assertion, this.spVersion)) {
/* 188 */           this.tokenType = assertion.getName().getLocalPart(); continue;
/* 189 */         }  if (PolicyUtil.isRequireDerivedKeys(assertion, this.spVersion)) {
/* 190 */           this.reqDK = true; continue;
/*     */         } 
/* 192 */         if (!assertion.isOptional()) {
/* 193 */           Constants.log_invalid_assertion(assertion, isServer, "X509Token");
/* 194 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 198 */       if (hasParameters()) {
/* 199 */         Iterator<PolicyAssertion> it = getParametersIterator();
/* 200 */         while (it.hasNext()) {
/* 201 */           PolicyAssertion assertion = it.next();
/* 202 */           if (PolicyUtil.isIssuer(assertion, this.spVersion)) {
/* 203 */             this.issuer = (Issuer)assertion; continue;
/* 204 */           }  if (PolicyUtil.isIssuerName(assertion, this.spVersion)) {
/* 205 */             this.issuerName = (IssuerName)assertion; continue;
/* 206 */           }  if (PolicyUtil.isClaimsElement(assertion) && SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(this.spVersion.namespaceUri))
/*     */           {
/* 208 */             this.claims = (Claims)assertion;
/*     */           }
/*     */         } 
/*     */       } 
/* 212 */       if (this.issuer != null && this.issuerName != null) {
/* 213 */         Constants.log_invalid_assertion(this.issuerName, isServer, "SecureConversationToken");
/* 214 */         this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_INVALID_VALUE;
/*     */       } 
/* 216 */       this.populated = true;
/*     */     } 
/* 218 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 222 */     throw new UnsupportedOperationException("Fix me");
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 227 */     return this.spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\X509Token.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */