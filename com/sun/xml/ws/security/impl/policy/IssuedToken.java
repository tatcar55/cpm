/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.Claims;
/*     */ import com.sun.xml.ws.security.policy.IssuedToken;
/*     */ import com.sun.xml.ws.security.policy.Issuer;
/*     */ import com.sun.xml.ws.security.policy.IssuerName;
/*     */ import com.sun.xml.ws.security.policy.RequestSecurityTokenTemplate;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.util.ArrayList;
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
/*     */ public class IssuedToken
/*     */   extends PolicyAssertion
/*     */   implements IssuedToken, SecurityAssertionValidator
/*     */ {
/*     */   private boolean populated = false;
/*     */   private RequestSecurityTokenTemplate rstTemplate;
/*  68 */   private Issuer issuer = null;
/*  69 */   private IssuerName issuerName = null;
/*     */   private ArrayList<String> referenceType;
/*     */   private String id;
/*  72 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   private boolean reqDK = false;
/*  74 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   private final QName itQname;
/*     */   private String includeToken;
/*  77 */   private Claims claims = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IssuedToken() {
/*  83 */     this.id = PolicyUtil.randomUUID();
/*  84 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  85 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */   
/*     */   public IssuedToken(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  89 */     super(name, nestedAssertions, nestedAlternative);
/*  90 */     this.id = PolicyUtil.randomUUID();
/*  91 */     String nsUri = getName().getNamespaceURI();
/*  92 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*  93 */     this.itQname = new QName(this.spVersion.namespaceUri, "IncludeToken");
/*  94 */     this.includeToken = this.spVersion.includeTokenAlways;
/*     */   }
/*     */   
/*     */   public RequestSecurityTokenTemplate getRequestSecurityTokenTemplate() {
/*  98 */     populate();
/*  99 */     return this.rstTemplate;
/*     */   }
/*     */   
/*     */   public Iterator getTokenRefernceType() {
/* 103 */     populate();
/* 104 */     return this.referenceType.iterator();
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/* 108 */     populate();
/* 109 */     return this.includeToken;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIncludeToken(String type) {
/* 114 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public String getTokenId() {
/* 118 */     return this.id;
/*     */   }
/*     */   
/*     */   public Issuer getIssuer() {
/* 122 */     populate();
/* 123 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public IssuerName getIssuerName() {
/* 127 */     populate();
/* 128 */     return this.issuerName;
/*     */   }
/*     */   
/*     */   public Claims getClaims() {
/* 132 */     populate();
/* 133 */     return this.claims;
/*     */   }
/*     */   
/*     */   public boolean isRequireDerivedKeys() {
/* 137 */     populate();
/* 138 */     return this.reqDK;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 142 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 145 */     populate(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 150 */     if (!this.populated) {
/* 151 */       if (getAttributeValue(this.itQname) != null) {
/* 152 */         this.includeToken = getAttributeValue(this.itQname);
/*     */       }
/* 154 */       if (hasNestedAssertions()) {
/* 155 */         Iterator<PolicyAssertion> it = getNestedAssertionsIterator();
/* 156 */         while (it.hasNext()) {
/* 157 */           PolicyAssertion assertion = it.next();
/* 158 */           if (PolicyUtil.isIssuer(assertion, this.spVersion)) {
/* 159 */             this.issuer = (Issuer)assertion; continue;
/* 160 */           }  if (PolicyUtil.isRequestSecurityTokenTemplate(assertion, this.spVersion)) {
/* 161 */             this.rstTemplate = (RequestSecurityTokenTemplate)assertion; continue;
/* 162 */           }  if (PolicyUtil.isIssuerName(assertion, this.spVersion)) {
/* 163 */             this.issuerName = (IssuerName)assertion; continue;
/* 164 */           }  if (PolicyUtil.isClaimsElement(assertion) && SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(this.spVersion.namespaceUri)) {
/*     */             
/* 166 */             this.claims = (Claims)assertion; continue;
/*     */           } 
/* 168 */           if (!assertion.isOptional()) {
/* 169 */             Constants.log_invalid_assertion(assertion, isServer, "IssuedToken");
/* 170 */             this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 175 */       if (this.issuer != null && this.issuerName != null) {
/* 176 */         Constants.log_invalid_assertion(this.issuerName, isServer, "SecureConversationToken");
/* 177 */         this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_INVALID_VALUE;
/*     */       } 
/* 179 */       NestedPolicy policy = getNestedPolicy();
/* 180 */       if (policy == null) {
/* 181 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 182 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 184 */         this.populated = true;
/* 185 */         return this.fitness;
/*     */       } 
/* 187 */       AssertionSet as = policy.getAssertionSet();
/* 188 */       if (as == null) {
/* 189 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 190 */           Constants.logger.log(Level.FINE, " Nested Policy is empty");
/*     */         }
/* 192 */         this.populated = true;
/* 193 */         return this.fitness;
/*     */       } 
/* 195 */       Iterator<PolicyAssertion> ast = as.iterator();
/*     */       
/* 197 */       while (ast.hasNext()) {
/* 198 */         PolicyAssertion assertion = ast.next();
/* 199 */         if (this.referenceType == null) {
/* 200 */           this.referenceType = new ArrayList<String>();
/*     */         }
/* 202 */         if (PolicyUtil.isRequireDerivedKeys(assertion, this.spVersion)) {
/* 203 */           this.reqDK = true; continue;
/* 204 */         }  if (PolicyUtil.isRequireExternalReference(assertion, this.spVersion)) {
/* 205 */           this.referenceType.add(assertion.getName().getLocalPart().intern()); continue;
/* 206 */         }  if (PolicyUtil.isRequireInternalReference(assertion, this.spVersion)) {
/* 207 */           this.referenceType.add(assertion.getName().getLocalPart().intern()); continue;
/*     */         } 
/* 209 */         if (!assertion.isOptional()) {
/* 210 */           Constants.log_invalid_assertion(assertion, isServer, "IssuedToken");
/* 211 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 215 */       this.populated = true;
/*     */     } 
/* 217 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 221 */     return this.spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\IssuedToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */