/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.impl.policy.PolicyUtil;
/*     */ import com.sun.xml.ws.security.policy.AsymmetricBinding;
/*     */ import com.sun.xml.ws.security.policy.Claims;
/*     */ import com.sun.xml.ws.security.policy.Issuer;
/*     */ import com.sun.xml.ws.security.policy.IssuerName;
/*     */ import com.sun.xml.ws.security.policy.SecureConversationToken;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.SupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.SymmetricBinding;
/*     */ import com.sun.xml.ws.security.policy.Token;
/*     */ import com.sun.xml.wss.impl.policy.mls.MessagePolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SCTokenWrapper
/*     */   extends PolicyAssertion
/*     */   implements SecureConversationToken
/*     */ {
/*  71 */   private SecureConversationToken scToken = null;
/*  72 */   private MessagePolicy messagePolicy = null;
/*  73 */   private List<PolicyAssertion> issuedTokenList = null;
/*  74 */   private List<PolicyAssertion> kerberosTokenList = null;
/*     */   private boolean cached = false;
/*  76 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */ 
/*     */   
/*     */   public SCTokenWrapper(PolicyAssertion scToken, MessagePolicy mp) {
/*  80 */     super(AssertionData.createAssertionData(scToken.getName(), scToken.getValue(), scToken.getAttributes(), scToken.isOptional(), scToken.isIgnorable()), getAssertionParameters(scToken), (scToken.getNestedPolicy() == null) ? null : scToken.getNestedPolicy().getAssertionSet());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     this.scToken = (SecureConversationToken)scToken;
/*  90 */     this.messagePolicy = mp;
/*     */     
/*  92 */     String nsUri = scToken.getName().getNamespaceURI();
/*  93 */     if (SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri.equals(nsUri)) {
/*  94 */       this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*  95 */     } else if (SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(nsUri)) {
/*  96 */       this.spVersion = SecurityPolicyVersion.SECURITYPOLICY12NS;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Collection<PolicyAssertion> getAssertionParameters(PolicyAssertion scToken) {
/* 101 */     if (scToken.hasParameters()) {
/* 102 */       Iterator<PolicyAssertion> itr = scToken.getParametersIterator();
/* 103 */       if (itr.hasNext()) {
/* 104 */         return Collections.singletonList(itr.next());
/*     */       }
/*     */     } 
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public SecureConversationToken getSecureConversationToken() {
/* 112 */     return this.scToken;
/*     */   }
/*     */   
/*     */   public void setSecureConversationToken(SecureConversationToken scToken) {
/* 116 */     this.scToken = scToken;
/*     */   }
/*     */   
/*     */   public MessagePolicy getMessagePolicy() {
/* 120 */     return this.messagePolicy;
/*     */   }
/*     */   
/*     */   public void setMessagePolicyp(MessagePolicy mp) {
/* 124 */     this.messagePolicy = mp;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRequireDerivedKeys() {
/* 129 */     return this.scToken.isRequireDerivedKeys();
/*     */   }
/*     */   
/*     */   public boolean isMustNotSendCancel() {
/* 133 */     return this.scToken.isMustNotSendCancel();
/*     */   }
/*     */   
/*     */   public boolean isMustNotSendRenew() {
/* 137 */     return this.scToken.isMustNotSendRenew();
/*     */   }
/*     */   
/*     */   public String getTokenType() {
/* 141 */     return this.scToken.getTokenType();
/*     */   }
/*     */   
/*     */   public Issuer getIssuer() {
/* 145 */     return this.scToken.getIssuer();
/*     */   }
/*     */   
/*     */   public IssuerName getIssuerName() {
/* 149 */     return this.scToken.getIssuerName();
/*     */   }
/*     */   
/*     */   public Claims getClaims() {
/* 153 */     return this.scToken.getClaims();
/*     */   }
/*     */   
/*     */   public NestedPolicy getBootstrapPolicy() {
/* 157 */     return this.scToken.getBootstrapPolicy();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIncludeToken() {
/* 162 */     return this.scToken.getIncludeToken();
/*     */   }
/*     */   
/*     */   public String getTokenId() {
/* 166 */     return this.scToken.getTokenId();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<PolicyAssertion> getIssuedTokens() {
/* 171 */     if (!this.cached && 
/* 172 */       hasNestedPolicy()) {
/* 173 */       getTokens(getNestedPolicy());
/* 174 */       this.cached = true;
/*     */     } 
/*     */     
/* 177 */     return this.issuedTokenList;
/*     */   }
/*     */   
/*     */   public List<PolicyAssertion> getKerberosTokens() {
/* 181 */     if (!this.cached && 
/* 182 */       hasNestedPolicy()) {
/* 183 */       getTokens(getNestedPolicy());
/* 184 */       this.cached = true;
/*     */     } 
/*     */     
/* 187 */     return this.kerberosTokenList;
/*     */   }
/*     */   
/*     */   private void getTokens(NestedPolicy policy) {
/* 191 */     this.issuedTokenList = new ArrayList<PolicyAssertion>();
/* 192 */     this.kerberosTokenList = new ArrayList<PolicyAssertion>();
/* 193 */     AssertionSet assertionSet = policy.getAssertionSet();
/* 194 */     for (PolicyAssertion pa : assertionSet) {
/* 195 */       if (PolicyUtil.isBootstrapPolicy(pa, this.spVersion)) {
/* 196 */         NestedPolicy np = pa.getNestedPolicy();
/* 197 */         AssertionSet bpSet = np.getAssertionSet();
/* 198 */         for (PolicyAssertion assertion : bpSet) {
/* 199 */           if (PolicyUtil.isAsymmetricBinding(assertion, this.spVersion)) {
/* 200 */             AsymmetricBinding sb = (AsymmetricBinding)assertion;
/* 201 */             Token iToken = sb.getInitiatorToken();
/* 202 */             if (iToken != null) {
/* 203 */               addToken(iToken);
/*     */             } else {
/* 205 */               addToken(sb.getInitiatorSignatureToken());
/* 206 */               addToken(sb.getInitiatorEncryptionToken());
/*     */             } 
/*     */             
/* 209 */             Token rToken = sb.getRecipientToken();
/* 210 */             if (rToken != null) {
/* 211 */               addToken(rToken); continue;
/*     */             } 
/* 213 */             addToken(sb.getRecipientSignatureToken());
/* 214 */             addToken(sb.getRecipientEncryptionToken()); continue;
/*     */           } 
/* 216 */           if (PolicyUtil.isSymmetricBinding(assertion, this.spVersion)) {
/* 217 */             SymmetricBinding sb = (SymmetricBinding)assertion;
/* 218 */             Token token = sb.getProtectionToken();
/* 219 */             if (token != null) {
/* 220 */               addToken(token); continue;
/*     */             } 
/* 222 */             addToken(sb.getEncryptionToken());
/* 223 */             addToken(sb.getSignatureToken()); continue;
/*     */           } 
/* 225 */           if (PolicyUtil.isSupportingTokens(assertion, this.spVersion)) {
/* 226 */             SupportingTokens st = (SupportingTokens)assertion;
/* 227 */             Iterator<Token> itr = st.getTokens();
/* 228 */             while (itr.hasNext()) {
/* 229 */               addToken(itr.next());
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addToken(Token token) {
/* 239 */     if (token != null) {
/* 240 */       if (PolicyUtil.isIssuedToken((PolicyAssertion)token, this.spVersion)) {
/* 241 */         this.issuedTokenList.add((PolicyAssertion)token);
/* 242 */       } else if (PolicyUtil.isKerberosToken((PolicyAssertion)token, this.spVersion)) {
/* 243 */         this.kerberosTokenList.add((PolicyAssertion)token);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public Set getTokenRefernceTypes() {
/* 249 */     return this.scToken.getTokenRefernceTypes();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBootstrapPolicy(NestedPolicy policy) {}
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 256 */     return this.spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\SCTokenWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */