/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.Claims;
/*     */ import com.sun.xml.ws.security.policy.HttpsToken;
/*     */ import com.sun.xml.ws.security.policy.Issuer;
/*     */ import com.sun.xml.ws.security.policy.IssuerName;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
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
/*     */ public class HttpsToken
/*     */   extends PolicyAssertion
/*     */   implements HttpsToken, SecurityAssertionValidator
/*     */ {
/*  65 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   private boolean populated = false;
/*     */   private boolean requireCC = false;
/*     */   private boolean httpBasicAuthentication = false;
/*     */   private boolean httpDigestAuthentication = false;
/*  70 */   private String id = "";
/*  71 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   private final QName rccQname;
/*  73 */   private Issuer issuer = null;
/*  74 */   private IssuerName issuerName = null;
/*  75 */   private Claims claims = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpsToken() {
/*  80 */     this.id = PolicyUtil.randomUUID();
/*  81 */     this.rccQname = new QName(this.spVersion.namespaceUri, "RequireClientCertificate");
/*     */   }
/*     */   
/*     */   public HttpsToken(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  85 */     super(name, nestedAssertions, nestedAlternative);
/*  86 */     this.id = PolicyUtil.randomUUID();
/*  87 */     String nsUri = getName().getNamespaceURI();
/*  88 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*  89 */     this.rccQname = new QName(this.spVersion.namespaceUri, "RequireClientCertificate");
/*     */   }
/*     */   
/*     */   public void setRequireClientCertificate(boolean value) {
/*  93 */     Map<QName, String> attrs = getAttributes();
/*  94 */     QName rccQname = new QName(this.spVersion.namespaceUri, "RequireClientCertificate");
/*  95 */     attrs.put(rccQname, Boolean.toString(value));
/*  96 */     this.requireCC = value;
/*     */   }
/*     */   
/*     */   public boolean isRequireClientCertificate() {
/* 100 */     populate();
/* 101 */     return this.requireCC;
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/* 105 */     throw new UnsupportedOperationException("This method is not supported for HttpsToken");
/*     */   }
/*     */   
/*     */   public void setIncludeToken(String type) {
/* 109 */     throw new UnsupportedOperationException("This method is not supported for HttpsToken");
/*     */   }
/*     */   
/*     */   public String getTokenId() {
/* 113 */     return this.id;
/*     */   }
/*     */   
/*     */   public Issuer getIssuer() {
/* 117 */     populate();
/* 118 */     return this.issuer;
/*     */   }
/*     */   
/*     */   public IssuerName getIssuerName() {
/* 122 */     populate();
/* 123 */     return this.issuerName;
/*     */   }
/*     */   
/*     */   public Claims getClaims() {
/* 127 */     populate();
/* 128 */     return this.claims;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 132 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 135 */     populate(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 140 */     if (!this.populated) {
/* 141 */       if (SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri.equals(this.spVersion.namespaceUri)) {
/*     */         
/* 143 */         String value = getAttributeValue(this.rccQname);
/* 144 */         this.requireCC = Boolean.valueOf(value).booleanValue();
/*     */       } 
/* 146 */       NestedPolicy policy = getNestedPolicy();
/* 147 */       if (policy == null) {
/* 148 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 149 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 151 */         this.populated = true;
/* 152 */         return this.fitness;
/*     */       } 
/* 154 */       AssertionSet assertionSet = policy.getAssertionSet();
/* 155 */       for (PolicyAssertion assertion : assertionSet) {
/* 156 */         if (PolicyUtil.isRequireClientCertificate(assertion, this.spVersion)) {
/* 157 */           this.requireCC = true; continue;
/* 158 */         }  if (PolicyUtil.isHttpBasicAuthentication(assertion, this.spVersion)) {
/* 159 */           this.httpBasicAuthentication = true; continue;
/* 160 */         }  if (PolicyUtil.isHttpDigestAuthentication(assertion, this.spVersion)) {
/* 161 */           this.httpDigestAuthentication = true; continue;
/*     */         } 
/* 163 */         if (!assertion.isOptional()) {
/* 164 */           Constants.log_invalid_assertion(assertion, isServer, "HttpsToken");
/* 165 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 169 */       if (hasParameters()) {
/* 170 */         Iterator<PolicyAssertion> it = getParametersIterator();
/* 171 */         while (it.hasNext()) {
/* 172 */           PolicyAssertion assertion = it.next();
/* 173 */           if (PolicyUtil.isIssuer(assertion, this.spVersion)) {
/* 174 */             this.issuer = (Issuer)assertion; continue;
/* 175 */           }  if (PolicyUtil.isIssuerName(assertion, this.spVersion)) {
/* 176 */             this.issuerName = (IssuerName)assertion; continue;
/* 177 */           }  if (PolicyUtil.isClaimsElement(assertion) && SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(this.spVersion.namespaceUri))
/*     */           {
/* 179 */             this.claims = (Claims)assertion;
/*     */           }
/*     */         } 
/*     */       } 
/* 183 */       if (this.issuer != null && this.issuerName != null) {
/* 184 */         Constants.log_invalid_assertion(this.issuerName, isServer, "SecureConversationToken");
/* 185 */         this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_INVALID_VALUE;
/*     */       } 
/* 187 */       this.populated = true;
/*     */     } 
/* 189 */     return this.fitness;
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 194 */     return this.spVersion;
/*     */   }
/*     */   
/*     */   public boolean isHttpBasicAuthentication() {
/* 198 */     populate();
/* 199 */     if (SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri.equals(this.spVersion.namespaceUri))
/*     */     {
/* 201 */       throw new UnsupportedOperationException("HttpBasicAuthentication is only supported forSecurityPolicy 1.2 and later");
/*     */     }
/*     */     
/* 204 */     return this.httpBasicAuthentication;
/*     */   }
/*     */   
/*     */   public boolean isHttpDigestAuthentication() {
/* 208 */     populate();
/* 209 */     if (SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri.equals(this.spVersion.namespaceUri))
/*     */     {
/* 211 */       throw new UnsupportedOperationException("HttpDigestAuthentication is only supported forSecurityPolicy 1.2 and later");
/*     */     }
/*     */     
/* 214 */     return this.httpDigestAuthentication;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\HttpsToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */