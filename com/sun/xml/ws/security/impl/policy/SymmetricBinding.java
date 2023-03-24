/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.policy.MessageLayout;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.SymmetricBinding;
/*     */ import com.sun.xml.ws.security.policy.Token;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SymmetricBinding
/*     */   extends PolicyAssertion
/*     */   implements SymmetricBinding, SecurityAssertionValidator
/*     */ {
/*  67 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   boolean populated = false;
/*     */   Token protectionToken;
/*     */   Token signatureToken;
/*     */   Token encryptionToken;
/*  72 */   MessageLayout layout = MessageLayout.Lax;
/*     */   AlgorithmSuite algSuite;
/*     */   boolean includeTimestamp = false;
/*     */   boolean disableTimestampSigning = false;
/*     */   boolean contentOnly = true;
/*  77 */   String protectionOrder = "SignBeforeEncrypting";
/*     */ 
/*     */   
/*     */   boolean protectToken = false;
/*     */ 
/*     */   
/*     */   boolean protectSignature = false;
/*     */   
/*     */   private SecurityPolicyVersion spVersion;
/*     */ 
/*     */   
/*     */   public SymmetricBinding() {
/*  89 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   }
/*     */ 
/*     */   
/*     */   public SymmetricBinding(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  94 */     super(name, nestedAssertions, nestedAlternative);
/*  95 */     String nsUri = getName().getNamespaceURI();
/*  96 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*     */   }
/*     */   
/*     */   public Token getEncryptionToken() {
/* 100 */     populate();
/* 101 */     return this.encryptionToken;
/*     */   }
/*     */   
/*     */   public Token getSignatureToken() {
/* 105 */     populate();
/* 106 */     return this.signatureToken;
/*     */   }
/*     */   
/*     */   public Token getProtectionToken() {
/* 110 */     populate();
/* 111 */     return this.protectionToken;
/*     */   }
/*     */   
/*     */   public void setAlgorithmSuite(AlgorithmSuite algSuite) {
/* 115 */     this.algSuite = algSuite;
/*     */   }
/*     */   
/*     */   public AlgorithmSuite getAlgorithmSuite() {
/* 119 */     populate();
/* 120 */     if (this.algSuite == null) {
/* 121 */       this.algSuite = new AlgorithmSuite();
/* 122 */       Constants.logger.log(Level.FINE, "Using Default Algorithm Suite Basic128");
/*     */     } 
/*     */     
/* 125 */     return this.algSuite;
/*     */   }
/*     */   
/*     */   public void includeTimeStamp(boolean value) {
/* 129 */     this.includeTimestamp = value;
/*     */   }
/*     */   
/*     */   public boolean isIncludeTimeStamp() {
/* 133 */     populate();
/* 134 */     return this.includeTimestamp;
/*     */   }
/*     */   
/*     */   public boolean isDisableTimestampSigning() {
/* 138 */     populate();
/* 139 */     return this.disableTimestampSigning;
/*     */   }
/*     */   
/*     */   public void setLayout(MessageLayout layout) {
/* 143 */     this.layout = layout;
/*     */   }
/*     */   
/*     */   public MessageLayout getLayout() {
/* 147 */     populate();
/* 148 */     return this.layout;
/*     */   }
/*     */   
/*     */   public void setEncryptionToken(Token token) {
/* 152 */     this.encryptionToken = token;
/*     */   }
/*     */   
/*     */   public void setSignatureToken(Token token) {
/* 156 */     this.signatureToken = token;
/*     */   }
/*     */   
/*     */   public void setProtectionToken(Token token) {
/* 160 */     this.protectionToken = token;
/*     */   }
/*     */   
/*     */   public boolean isSignContent() {
/* 164 */     populate();
/* 165 */     return this.contentOnly;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSignContent(boolean contentOnly) {
/* 170 */     this.contentOnly = contentOnly;
/*     */   }
/*     */   
/*     */   public void setProtectionOrder(String order) {
/* 174 */     this.protectionOrder = order;
/*     */   }
/*     */   
/*     */   public String getProtectionOrder() {
/* 178 */     populate();
/* 179 */     return this.protectionOrder;
/*     */   }
/*     */   
/*     */   public void setTokenProtection(boolean value) {
/* 183 */     this.protectToken = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSignatureProtection(boolean value) {
/* 189 */     this.protectSignature = value;
/*     */   }
/*     */   
/*     */   public boolean getTokenProtection() {
/* 193 */     populate();
/* 194 */     return this.protectToken;
/*     */   }
/*     */   
/*     */   public boolean getSignatureProtection() {
/* 198 */     populate();
/* 199 */     return this.protectSignature;
/*     */   }
/*     */   
/*     */   private void populate() {
/* 203 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 207 */     if (!this.populated) {
/* 208 */       NestedPolicy policy = getNestedPolicy();
/* 209 */       if (policy == null) {
/* 210 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 211 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 213 */         this.populated = true;
/* 214 */         return this.fitness;
/*     */       } 
/* 216 */       AssertionSet as = policy.getAssertionSet();
/* 217 */       Iterator<PolicyAssertion> ast = as.iterator();
/* 218 */       while (ast.hasNext()) {
/* 219 */         PolicyAssertion assertion = ast.next();
/* 220 */         if (PolicyUtil.isSignatureToken(assertion, this.spVersion)) {
/* 221 */           this.signatureToken = ((Token)assertion).getToken(); continue;
/* 222 */         }  if (PolicyUtil.isEncryptionToken(assertion, this.spVersion)) {
/* 223 */           this.encryptionToken = ((Token)assertion).getToken(); continue;
/* 224 */         }  if (PolicyUtil.isProtectionToken(assertion, this.spVersion)) {
/* 225 */           this.protectionToken = ((Token)assertion).getToken(); continue;
/* 226 */         }  if (PolicyUtil.isAlgorithmAssertion(assertion, this.spVersion)) {
/* 227 */           this.algSuite = (AlgorithmSuite)assertion;
/* 228 */           String sigAlgo = assertion.getAttributeValue(new QName("signatureAlgorithm"));
/* 229 */           this.algSuite.setSignatureAlgorithm(sigAlgo); continue;
/* 230 */         }  if (PolicyUtil.isIncludeTimestamp(assertion, this.spVersion)) {
/* 231 */           this.includeTimestamp = true; continue;
/* 232 */         }  if (PolicyUtil.isEncryptBeforeSign(assertion, this.spVersion)) {
/* 233 */           this.protectionOrder = "EncryptBeforeSigning"; continue;
/* 234 */         }  if (PolicyUtil.isSignBeforeEncrypt(assertion, this.spVersion)) {
/* 235 */           this.protectionOrder = "SignBeforeEncrypting"; continue;
/* 236 */         }  if (PolicyUtil.isContentOnlyAssertion(assertion, this.spVersion)) {
/* 237 */           this.contentOnly = false; continue;
/* 238 */         }  if (PolicyUtil.isMessageLayout(assertion, this.spVersion)) {
/* 239 */           this.layout = ((Layout)assertion).getMessageLayout(); continue;
/* 240 */         }  if (PolicyUtil.isProtectTokens(assertion, this.spVersion)) {
/* 241 */           this.protectToken = true; continue;
/* 242 */         }  if (PolicyUtil.isEncryptSignature(assertion, this.spVersion)) {
/* 243 */           this.protectSignature = true; continue;
/* 244 */         }  if (PolicyUtil.disableTimestampSigning(assertion)) {
/* 245 */           this.disableTimestampSigning = true; continue;
/*     */         } 
/* 247 */         if (!assertion.isOptional()) {
/* 248 */           Constants.log_invalid_assertion(assertion, isServer, "SymmetricBinding");
/* 249 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 253 */       this.populated = true;
/*     */     } 
/* 255 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 259 */     return populate(isServer);
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 263 */     return this.spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\SymmetricBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */