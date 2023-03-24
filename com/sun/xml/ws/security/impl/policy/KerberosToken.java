/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.Claims;
/*     */ import com.sun.xml.ws.security.policy.Issuer;
/*     */ import com.sun.xml.ws.security.policy.IssuerName;
/*     */ import com.sun.xml.ws.security.policy.KerberosToken;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
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
/*     */ 
/*     */ 
/*     */ public class KerberosToken
/*     */   extends PolicyAssertion
/*     */   implements KerberosToken, SecurityAssertionValidator
/*     */ {
/*  76 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   private boolean populated = false;
/*  78 */   private String tokenType = null;
/*  79 */   private String id = null;
/*     */   private boolean reqDK = false;
/*     */   private boolean isServer = false;
/*  82 */   private HashSet<String> referenceType = null;
/*  83 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   private final QName itQname;
/*     */   private String includeToken;
/*  86 */   private Issuer issuer = null;
/*  87 */   private IssuerName issuerName = null;
/*  88 */   private Claims claims = null;
/*     */ 
/*     */   
/*     */   public KerberosToken(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  92 */     super(name, nestedAssertions, nestedAlternative);
/*  93 */     this.id = PolicyUtil.randomUUID();
/*  94 */     this.referenceType = new HashSet<String>();
/*  95 */     String nsUri = getName().getNamespaceURI();
/*  96 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*  97 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  98 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTokenType() {
/* 103 */     populate();
/* 104 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public void setTokenType(String tokenType) {
/* 108 */     this.tokenType = tokenType;
/*     */   }
/*     */   
/*     */   public Set getTokenRefernceType() {
/* 112 */     populate();
/* 113 */     return this.referenceType;
/*     */   }
/*     */   
/*     */   public void addTokenReferenceType(String tokenRefType) {
/* 117 */     this.referenceType.add(tokenRefType);
/*     */   }
/*     */   
/*     */   public boolean isRequireDerivedKeys() {
/* 121 */     populate();
/* 122 */     return this.reqDK;
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/* 126 */     populate();
/* 127 */     return this.includeToken;
/*     */   }
/*     */   
/*     */   public void setIncludeToken(String type) {
/* 131 */     this.includeToken = type;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/* 136 */     return this.id;
/*     */   }
/*     */   
/*     */   public Issuer getIssuer() {
/* 140 */     populate();
/* 141 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public IssuerName getIssuerName() {
/* 145 */     populate();
/* 146 */     return this.issuerName;
/*     */   }
/*     */   
/*     */   public Claims getClaims() {
/* 150 */     populate();
/* 151 */     return this.claims;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 155 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 158 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 162 */     if (!this.populated) {
/* 163 */       if (getAttributeValue(this.itQname) != null) {
/* 164 */         this.includeToken = getAttributeValue(this.itQname);
/*     */       }
/* 166 */       NestedPolicy policy = getNestedPolicy();
/* 167 */       if (policy == null) {
/* 168 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 169 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 171 */         this.populated = true;
/* 172 */         return this.fitness;
/*     */       } 
/* 174 */       AssertionSet as = policy.getAssertionSet();
/*     */       
/* 176 */       for (PolicyAssertion assertion : as) {
/* 177 */         if (PolicyUtil.isTokenReferenceType(assertion, this.spVersion)) {
/* 178 */           this.referenceType.add(assertion.getName().getLocalPart().intern()); continue;
/* 179 */         }  if (PolicyUtil.isKerberosTokenType(assertion, this.spVersion)) {
/* 180 */           this.tokenType = assertion.getName().getLocalPart().intern(); continue;
/* 181 */         }  if (PolicyUtil.isRequireDerivedKeys(assertion, this.spVersion)) {
/* 182 */           this.reqDK = true; continue;
/*     */         } 
/* 184 */         if (!assertion.isOptional()) {
/* 185 */           Constants.log_invalid_assertion(assertion, isServer, "KerberosToken");
/* 186 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 190 */       if (hasParameters()) {
/* 191 */         Iterator<PolicyAssertion> it = getParametersIterator();
/* 192 */         while (it.hasNext()) {
/* 193 */           PolicyAssertion assertion = it.next();
/* 194 */           if (PolicyUtil.isIssuer(assertion, this.spVersion)) {
/* 195 */             this.issuer = (Issuer)assertion; continue;
/* 196 */           }  if (PolicyUtil.isIssuerName(assertion, this.spVersion)) {
/* 197 */             this.issuerName = (IssuerName)assertion; continue;
/* 198 */           }  if (PolicyUtil.isClaimsElement(assertion) && SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(this.spVersion.namespaceUri))
/*     */           {
/* 200 */             this.claims = (Claims)assertion;
/*     */           }
/*     */         } 
/*     */       } 
/* 204 */       if (this.issuer != null && this.issuerName != null) {
/* 205 */         Constants.log_invalid_assertion(this.issuerName, isServer, "SecureConversationToken");
/* 206 */         this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_INVALID_VALUE;
/*     */       } 
/* 208 */       this.populated = true;
/*     */     } 
/* 210 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 214 */     return this.spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\KerberosToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */