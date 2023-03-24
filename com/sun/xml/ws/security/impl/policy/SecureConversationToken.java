/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.Claims;
/*     */ import com.sun.xml.ws.security.policy.Issuer;
/*     */ import com.sun.xml.ws.security.policy.IssuerName;
/*     */ import com.sun.xml.ws.security.policy.SecureConversationToken;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
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
/*     */ public class SecureConversationToken
/*     */   extends PolicyAssertion
/*     */   implements SecureConversationToken, SecurityAssertionValidator
/*     */ {
/*  64 */   private NestedPolicy bootstrapPolicy = null;
/*  65 */   private String id = null;
/*     */   private boolean populated = false;
/*  67 */   private PolicyAssertion rdKey = null;
/*  68 */   private Set<String> referenceType = null;
/*  69 */   private Issuer issuer = null;
/*  70 */   private IssuerName issuerName = null;
/*  71 */   private String tokenType = null;
/*  72 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*  73 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   private final QName itQname;
/*     */   private String includeToken;
/*  76 */   private PolicyAssertion mustNotSendCancel = null;
/*  77 */   private PolicyAssertion mustNotSendRenew = null;
/*  78 */   private Claims claims = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecureConversationToken() {
/*  84 */     this.id = PolicyUtil.randomUUID();
/*  85 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  86 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */   
/*     */   public SecureConversationToken(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  90 */     super(name, nestedAssertions, nestedAlternative);
/*  91 */     this.id = PolicyUtil.randomUUID();
/*  92 */     String nsUri = getName().getNamespaceURI();
/*  93 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*  94 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  95 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set getTokenRefernceTypes() {
/* 100 */     populate();
/* 101 */     if (this.referenceType == null) {
/* 102 */       return Collections.emptySet();
/*     */     }
/* 104 */     return this.referenceType;
/*     */   }
/*     */   
/*     */   public boolean isRequireDerivedKeys() {
/* 108 */     populate();
/* 109 */     if (this.rdKey != null) {
/* 110 */       return true;
/*     */     }
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMustNotSendCancel() {
/* 117 */     populate();
/* 118 */     if (this.mustNotSendCancel != null) {
/* 119 */       return true;
/*     */     }
/* 121 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isMustNotSendRenew() {
/* 125 */     populate();
/* 126 */     if (this.mustNotSendRenew != null) {
/* 127 */       return true;
/*     */     }
/* 129 */     return false;
/*     */   }
/*     */   
/*     */   public String getTokenType() {
/* 133 */     populate();
/* 134 */     return this.tokenType;
/*     */   }
/*     */   
/*     */   public Issuer getIssuer() {
/* 138 */     populate();
/* 139 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public IssuerName getIssuerName() {
/* 143 */     populate();
/* 144 */     return this.issuerName;
/*     */   }
/*     */   
/*     */   public Claims getClaims() {
/* 148 */     populate();
/* 149 */     return this.claims;
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/* 153 */     populate();
/* 154 */     return this.includeToken;
/*     */   }
/*     */   
/*     */   public void setIncludeToken(String type) {
/* 158 */     Map<QName, String> attrs = getAttributes();
/* 159 */     QName tokenName = new QName(this.spVersion.namespaceUri, "IncludeToken");
/* 160 */     attrs.put(tokenName, type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NestedPolicy getBootstrapPolicy() {
/* 166 */     populate();
/* 167 */     return this.bootstrapPolicy;
/*     */   }
/*     */   
/*     */   public String getTokenId() {
/* 171 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 176 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 179 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 183 */     if (!this.populated) {
/* 184 */       String tmp = getAttributeValue(this.itQname);
/* 185 */       if (tmp != null)
/* 186 */         this.includeToken = tmp; 
/* 187 */       NestedPolicy policy = getNestedPolicy();
/* 188 */       if (policy == null) {
/* 189 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 190 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 192 */         this.populated = true;
/* 193 */         return this.fitness;
/*     */       } 
/* 195 */       AssertionSet as = policy.getAssertionSet();
/* 196 */       Iterator<PolicyAssertion> paItr = as.iterator();
/* 197 */       while (paItr.hasNext()) {
/* 198 */         PolicyAssertion assertion = paItr.next();
/* 199 */         if (PolicyUtil.isBootstrapPolicy(assertion, this.spVersion)) {
/* 200 */           this.bootstrapPolicy = assertion.getNestedPolicy(); continue;
/* 201 */         }  if (PolicyUtil.isRequireDerivedKeys(assertion, this.spVersion)) {
/* 202 */           this.rdKey = assertion; continue;
/* 203 */         }  if (PolicyUtil.isRequireExternalUriReference(assertion, this.spVersion)) {
/* 204 */           if (this.referenceType == null) {
/* 205 */             this.referenceType = new HashSet<String>();
/*     */           }
/* 207 */           this.referenceType.add(assertion.getName().getLocalPart().intern()); continue;
/* 208 */         }  if (PolicyUtil.isSC10SecurityContextToken(assertion, this.spVersion)) {
/* 209 */           this.tokenType = assertion.getName().getLocalPart(); continue;
/* 210 */         }  if (PolicyUtil.isMustNotSendCancel(assertion, this.spVersion)) {
/* 211 */           this.mustNotSendCancel = assertion; continue;
/* 212 */         }  if (PolicyUtil.isMustNotSendRenew(assertion, this.spVersion)) {
/* 213 */           this.mustNotSendRenew = assertion; continue;
/*     */         } 
/* 215 */         if (!assertion.isOptional()) {
/* 216 */           Constants.log_invalid_assertion(assertion, isServer, "SecureConversationToken");
/* 217 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 222 */       if (hasNestedAssertions()) {
/* 223 */         Iterator<PolicyAssertion> it = getNestedAssertionsIterator();
/* 224 */         while (it.hasNext()) {
/* 225 */           PolicyAssertion assertion = it.next();
/* 226 */           if (PolicyUtil.isIssuer(assertion, this.spVersion)) {
/* 227 */             this.issuer = (Issuer)assertion; continue;
/* 228 */           }  if (PolicyUtil.isIssuerName(assertion, this.spVersion)) {
/* 229 */             this.issuerName = (IssuerName)assertion; continue;
/* 230 */           }  if (PolicyUtil.isClaimsElement(assertion) && SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(this.spVersion.namespaceUri))
/*     */           {
/* 232 */             this.claims = (Claims)assertion;
/*     */           }
/*     */         } 
/*     */       } 
/* 236 */       if (this.issuer != null && this.issuerName != null) {
/* 237 */         Constants.log_invalid_assertion(this.issuerName, isServer, "SecureConversationToken");
/* 238 */         this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_INVALID_VALUE;
/*     */       } 
/* 240 */       this.populated = true;
/*     */     } 
/* 242 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 246 */     return this.spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\SecureConversationToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */