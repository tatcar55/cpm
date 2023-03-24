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
/*     */ import com.sun.xml.ws.security.policy.UserNameToken;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public class UsernameToken
/*     */   extends PolicyAssertion
/*     */   implements UserNameToken, Cloneable, SecurityAssertionValidator
/*     */ {
/*     */   private String tokenType;
/*     */   private String id;
/*     */   private boolean populated;
/*  66 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   private boolean hasPassword = true;
/*     */   private boolean useHashPassword = false;
/*  69 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   private final QName itQname;
/*     */   private String includeToken;
/*  72 */   private Issuer issuer = null;
/*  73 */   private IssuerName issuerName = null;
/*  74 */   private Claims claims = null;
/*     */   
/*     */   private boolean reqDK = false;
/*     */   
/*     */   private boolean useNonce = false;
/*     */   
/*     */   private boolean useCreated = false;
/*     */   
/*     */   public UsernameToken() {
/*  83 */     this.id = PolicyUtil.randomUUID();
/*  84 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  85 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */   
/*     */   public UsernameToken(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  89 */     super(name, nestedAssertions, nestedAlternative);
/*  90 */     this.id = PolicyUtil.randomUUID();
/*  91 */     String nsUri = getName().getNamespaceURI();
/*  92 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*  93 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  94 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */   
/*     */   public void setType(String type) {
/*  98 */     this.tokenType = type;
/*     */   }
/*     */   
/*     */   public String getType() {
/* 102 */     populate();
/* 103 */     return this.tokenType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTokenId() {
/* 108 */     return this.id;
/*     */   }
/*     */   
/*     */   public void setTokenId(String _id) {
/* 112 */     this.id = _id;
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/* 116 */     populate();
/* 117 */     return this.includeToken;
/*     */   }
/*     */   
/*     */   public void setIncludeToken(String type) {
/* 121 */     Map<QName, String> attrs = getAttributes();
/* 122 */     QName itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/* 123 */     attrs.put(itQname, type);
/*     */   }
/*     */   
/*     */   public Issuer getIssuer() {
/* 127 */     populate();
/* 128 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public IssuerName getIssuerName() {
/* 132 */     populate();
/* 133 */     return this.issuerName;
/*     */   }
/*     */   
/*     */   public Claims getClaims() {
/* 137 */     populate();
/* 138 */     return this.claims;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 142 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 145 */     populate(false);
/*     */   }
/*     */   public boolean hasPassword() {
/* 148 */     return this.hasPassword;
/*     */   }
/*     */   
/*     */   public boolean useHashPassword() {
/* 152 */     return this.useHashPassword;
/*     */   }
/*     */   
/*     */   public boolean isRequireDerivedKeys() {
/* 156 */     populate();
/* 157 */     return this.reqDK;
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 161 */     if (!this.populated) {
/* 162 */       if (getAttributeValue(this.itQname) != null) {
/* 163 */         this.includeToken = getAttributeValue(this.itQname);
/*     */       }
/* 165 */       NestedPolicy policy = getNestedPolicy();
/* 166 */       if (policy == null) {
/* 167 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 168 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 170 */         this.populated = true;
/* 171 */         return this.fitness;
/*     */       } 
/* 173 */       AssertionSet assertionSet = policy.getAssertionSet();
/* 174 */       for (PolicyAssertion assertion : assertionSet) {
/* 175 */         if (PolicyUtil.isUsernameTokenType(assertion, this.spVersion)) {
/* 176 */           this.tokenType = assertion.getName().getLocalPart(); continue;
/* 177 */         }  if (PolicyUtil.hasPassword(assertion, this.spVersion)) {
/* 178 */           this.hasPassword = false; continue;
/* 179 */         }  if (PolicyUtil.isHashPassword(assertion, this.spVersion)) {
/* 180 */           this.useHashPassword = true; continue;
/* 181 */         }  if (PolicyUtil.isRequireDerivedKeys(assertion, this.spVersion)) {
/* 182 */           this.reqDK = true; continue;
/* 183 */         }  if (PolicyUtil.useCreated(assertion, this.spVersion)) {
/* 184 */           this.useCreated = true; continue;
/* 185 */         }  if (PolicyUtil.useNonce(assertion, this.spVersion)) {
/* 186 */           this.useNonce = true;
/* 187 */           this.useCreated = true; continue;
/*     */         } 
/* 189 */         if (!assertion.isOptional()) {
/* 190 */           Constants.log_invalid_assertion(assertion, isServer, "UsernameToken");
/* 191 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 195 */       if (hasParameters()) {
/* 196 */         Iterator<PolicyAssertion> it = getParametersIterator();
/* 197 */         while (it.hasNext()) {
/* 198 */           PolicyAssertion assertion = it.next();
/* 199 */           if (PolicyUtil.isIssuer(assertion, this.spVersion)) {
/* 200 */             this.issuer = (Issuer)assertion; continue;
/* 201 */           }  if (PolicyUtil.isIssuerName(assertion, this.spVersion)) {
/* 202 */             this.issuerName = (IssuerName)assertion; continue;
/* 203 */           }  if (PolicyUtil.isClaimsElement(assertion) && SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(this.spVersion.namespaceUri))
/*     */           {
/* 205 */             this.claims = (Claims)assertion;
/*     */           }
/*     */         } 
/*     */       } 
/* 209 */       if (this.issuer != null && this.issuerName != null) {
/* 210 */         Constants.log_invalid_assertion(this.issuerName, isServer, "SecureConversationToken");
/* 211 */         this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_INVALID_VALUE;
/*     */       } 
/* 213 */       this.populated = true;
/*     */     } 
/* 215 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public Object clone() throws CloneNotSupportedException {
/* 219 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 223 */     return this.spVersion;
/*     */   }
/*     */   
/*     */   public Set getTokenRefernceType() {
/* 227 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */   
/*     */   public boolean useNonce() {
/* 231 */     return this.useNonce;
/*     */   }
/*     */   
/*     */   public boolean useCreated() {
/* 235 */     return this.useCreated;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\UsernameToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */