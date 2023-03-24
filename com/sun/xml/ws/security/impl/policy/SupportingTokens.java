/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.policy.EncryptedElements;
/*     */ import com.sun.xml.ws.security.policy.EncryptedParts;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.SignedElements;
/*     */ import com.sun.xml.ws.security.policy.SignedParts;
/*     */ import com.sun.xml.ws.security.policy.SupportingTokens;
/*     */ import com.sun.xml.ws.security.policy.Token;
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
/*     */ public class SupportingTokens
/*     */   extends PolicyAssertion
/*     */   implements SupportingTokens
/*     */ {
/*     */   private AlgorithmSuite algSuite;
/*  66 */   private List<SignedParts> spList = new ArrayList<SignedParts>(1);
/*  67 */   private List<EncryptedParts> epList = new ArrayList<EncryptedParts>(1);
/*  68 */   private List<SignedElements> seList = new ArrayList<SignedElements>(1);
/*  69 */   private List<EncryptedElements> eeList = new ArrayList<EncryptedElements>(1);
/*     */   private boolean isServer = false;
/*     */   private List<Token> _tokenList;
/*     */   private boolean populated;
/*  73 */   private SecurityPolicyVersion spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */ 
/*     */ 
/*     */   
/*     */   public SupportingTokens() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public SupportingTokens(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  82 */     super(name, nestedAssertions, nestedAlternative);
/*  83 */     String nsUri = getName().getNamespaceURI();
/*  84 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*     */   }
/*     */   
/*     */   public void setAlgorithmSuite(AlgorithmSuite algSuite) {
/*  88 */     this.algSuite = algSuite;
/*     */   }
/*     */   
/*     */   public AlgorithmSuite getAlgorithmSuite() {
/*  92 */     populate();
/*  93 */     return this.algSuite;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addToken(Token token) {
/*  98 */     if (this._tokenList == null) {
/*  99 */       this._tokenList = new ArrayList<Token>();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 104 */     this._tokenList.add(token);
/*     */   }
/*     */   
/*     */   public Iterator getTokens() {
/* 108 */     populate();
/* 109 */     if (this._tokenList != null) {
/* 110 */       return this._tokenList.iterator();
/*     */     }
/* 112 */     return Collections.emptyList().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void populate() {
/* 117 */     if (!this.populated) {
/* 118 */       NestedPolicy policy = getNestedPolicy();
/* 119 */       if (policy == null) {
/* 120 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 121 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 123 */         this.populated = true;
/*     */         return;
/*     */       } 
/* 126 */       AssertionSet as = policy.getAssertionSet();
/* 127 */       Iterator<PolicyAssertion> ast = as.iterator();
/* 128 */       while (ast.hasNext()) {
/* 129 */         PolicyAssertion assertion = ast.next();
/* 130 */         if (PolicyUtil.isAlgorithmAssertion(assertion, this.spVersion)) {
/* 131 */           this.algSuite = (AlgorithmSuite)assertion;
/* 132 */           String sigAlgo = assertion.getAttributeValue(new QName("signatureAlgorithm"));
/* 133 */           this.algSuite.setSignatureAlgorithm(sigAlgo); continue;
/* 134 */         }  if (PolicyUtil.isToken(assertion, this.spVersion)) {
/* 135 */           addToken((Token)assertion); continue;
/*     */         } 
/* 137 */         if (PolicyUtil.isSignedParts(assertion, this.spVersion)) {
/* 138 */           this.spList.add((SignedParts)assertion); continue;
/* 139 */         }  if (PolicyUtil.isSignedElements(assertion, this.spVersion)) {
/* 140 */           this.seList.add((SignedElements)assertion); continue;
/* 141 */         }  if (PolicyUtil.isEncryptParts(assertion, this.spVersion)) {
/* 142 */           this.epList.add((EncryptedParts)assertion); continue;
/* 143 */         }  if (PolicyUtil.isEncryptedElements(assertion, this.spVersion)) {
/* 144 */           this.eeList.add((EncryptedElements)assertion); continue;
/*     */         } 
/* 146 */         if (!assertion.isOptional()) {
/* 147 */           if (Constants.logger.getLevel() == Level.SEVERE) {
/* 148 */             Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0100_INVALID_SECURITY_ASSERTION(assertion, "SecurityContextToken"));
/*     */           }
/* 150 */           if (this.isServer) {
/* 151 */             throw new UnsupportedPolicyAssertion("Policy assertion " + assertion + " is not supported under SupportingTokens assertion");
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 157 */       Iterator<PolicyAssertion> parameterAssertion = getParametersIterator();
/* 158 */       while (parameterAssertion.hasNext()) {
/* 159 */         PolicyAssertion assertion = parameterAssertion.next();
/* 160 */         if (PolicyUtil.isSignedParts(assertion, this.spVersion)) {
/* 161 */           this.spList.add((SignedParts)assertion); continue;
/* 162 */         }  if (PolicyUtil.isSignedElements(assertion, this.spVersion)) {
/* 163 */           this.seList.add((SignedElements)assertion); continue;
/* 164 */         }  if (PolicyUtil.isEncryptParts(assertion, this.spVersion)) {
/* 165 */           this.epList.add((EncryptedParts)assertion); continue;
/* 166 */         }  if (PolicyUtil.isEncryptedElements(assertion, this.spVersion)) {
/* 167 */           this.eeList.add((EncryptedElements)assertion); continue;
/*     */         } 
/* 169 */         if (!assertion.isOptional()) {
/* 170 */           if (Constants.logger.getLevel() == Level.SEVERE) {
/* 171 */             Constants.logger.log(Level.SEVERE, LogStringsMessages.SP_0100_INVALID_SECURITY_ASSERTION(assertion, "SecurityContextToken"));
/*     */           }
/* 173 */           if (this.isServer) {
/* 174 */             throw new UnsupportedPolicyAssertion("Policy assertion " + assertion + " is not supported under SupportingTokens assertion");
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 180 */       this.populated = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getIncludeToken() {
/* 185 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIncludeToken(String type) {}
/*     */   
/*     */   public String getTokenId() {
/* 192 */     return "";
/*     */   }
/*     */   
/*     */   public Iterator<SignedParts> getSignedParts() {
/* 196 */     populate();
/* 197 */     return this.spList.iterator();
/*     */   }
/*     */   
/*     */   public Iterator<SignedElements> getSignedElements() {
/* 201 */     populate();
/* 202 */     return this.seList.iterator();
/*     */   }
/*     */   
/*     */   public Iterator<EncryptedParts> getEncryptedParts() {
/* 206 */     populate();
/* 207 */     return this.epList.iterator();
/*     */   }
/*     */   
/*     */   public Iterator<EncryptedElements> getEncryptedElements() {
/* 211 */     populate();
/* 212 */     return this.eeList.iterator();
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 216 */     return this.spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\SupportingTokens.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */