/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.policy.AsymmetricBinding;
/*     */ import com.sun.xml.ws.security.policy.MessageLayout;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
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
/*     */ public class AsymmetricBinding
/*     */   extends PolicyAssertion
/*     */   implements AsymmetricBinding, SecurityAssertionValidator
/*     */ {
/*  65 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   private Token initiatorToken;
/*     */   private Token recipientToken;
/*     */   private Token initiatorSignatureToken;
/*     */   private Token recipientSignatureToken;
/*     */   private Token initiatorEncryptionToken;
/*     */   private Token recipientEncryptionToken;
/*     */   private AlgorithmSuite algSuite;
/*     */   private boolean includeTimestamp = false;
/*     */   private boolean disableTimestampSigning = false;
/*     */   private boolean contentOnly = true;
/*  76 */   private MessageLayout layout = MessageLayout.Lax;
/*  77 */   private String protectionOrder = "SignBeforeEncrypting";
/*     */   
/*     */   private boolean protectToken = false;
/*     */   
/*     */   private boolean protectSignature = false;
/*     */   
/*     */   private boolean populated = false;
/*     */   private SecurityPolicyVersion spVersion;
/*     */   
/*     */   public AsymmetricBinding() {
/*  87 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   }
/*     */   public AsymmetricBinding(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  90 */     super(name, nestedAssertions, nestedAlternative);
/*  91 */     String nsUri = getName().getNamespaceURI();
/*  92 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*     */   }
/*     */   
/*     */   public Token getRecipientToken() {
/*  96 */     populate();
/*  97 */     return this.recipientToken;
/*     */   }
/*     */   
/*     */   public Token getInitiatorToken() {
/* 101 */     populate();
/* 102 */     return this.initiatorToken;
/*     */   }
/*     */   
/*     */   public Token getRecipientSignatureToken() {
/* 106 */     populate();
/* 107 */     return this.recipientSignatureToken;
/*     */   }
/*     */   
/*     */   public Token getInitiatorSignatureToken() {
/* 111 */     populate();
/* 112 */     return this.initiatorSignatureToken;
/*     */   }
/*     */   public Token getRecipientEncryptionToken() {
/* 115 */     populate();
/* 116 */     return this.recipientEncryptionToken;
/*     */   }
/*     */   
/*     */   public Token getInitiatorEncryptionToken() {
/* 120 */     populate();
/* 121 */     return this.initiatorEncryptionToken;
/*     */   }
/*     */   
/*     */   public void setAlgorithmSuite(AlgorithmSuite algSuite) {
/* 125 */     this.algSuite = algSuite;
/*     */   }
/*     */   
/*     */   public AlgorithmSuite getAlgorithmSuite() {
/* 129 */     populate();
/* 130 */     if (this.algSuite == null) {
/* 131 */       this.algSuite = new AlgorithmSuite();
/* 132 */       Constants.logger.log(Level.FINE, "Using Default Algorithm Suite Basic128");
/*     */     } 
/* 134 */     return this.algSuite;
/*     */   }
/*     */   
/*     */   public void includeTimeStamp(boolean value) {
/* 138 */     populate();
/* 139 */     this.includeTimestamp = value;
/*     */   }
/*     */   
/*     */   public boolean isIncludeTimeStamp() {
/* 143 */     populate();
/* 144 */     return this.includeTimestamp;
/*     */   }
/*     */   
/*     */   public boolean isDisableTimestampSigning() {
/* 148 */     populate();
/* 149 */     return this.disableTimestampSigning;
/*     */   }
/*     */   
/*     */   public void setLayout(MessageLayout layout) {
/* 153 */     this.layout = layout;
/*     */   }
/*     */   
/*     */   public MessageLayout getLayout() {
/* 157 */     populate();
/* 158 */     return this.layout;
/*     */   }
/*     */   
/*     */   public void setInitiatorToken(Token token) {
/* 162 */     this.initiatorToken = token;
/*     */   }
/*     */   
/*     */   public void setRecipientToken(Token token) {
/* 166 */     this.recipientToken = token;
/*     */   }
/*     */   
/*     */   public void setInitiatorSignatureToken(Token token) {
/* 170 */     this.initiatorSignatureToken = token;
/*     */   }
/*     */   
/*     */   public void setRecipientSignatureToken(Token token) {
/* 174 */     this.recipientSignatureToken = token;
/*     */   }
/*     */   
/*     */   public void setInitiatorEncryptionToken(Token token) {
/* 178 */     this.initiatorEncryptionToken = token;
/*     */   }
/*     */   
/*     */   public void setRecipientEncryptionToken(Token token) {
/* 182 */     this.recipientEncryptionToken = token;
/*     */   }
/*     */   
/*     */   public boolean isSignContent() {
/* 186 */     populate();
/* 187 */     return this.contentOnly;
/*     */   }
/*     */   
/*     */   public void setSignContent(boolean contentOnly) {
/* 191 */     this.contentOnly = contentOnly;
/*     */   }
/*     */   
/*     */   public void setProtectionOrder(String order) {
/* 195 */     this.protectionOrder = order;
/*     */   }
/*     */   
/*     */   public String getProtectionOrder() {
/* 199 */     populate();
/* 200 */     return this.protectionOrder;
/*     */   }
/*     */   
/*     */   public void setTokenProtection(boolean value) {
/* 204 */     this.protectToken = value;
/*     */   }
/*     */   
/*     */   public void setSignatureProtection(boolean value) {
/* 208 */     this.protectSignature = value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getTokenProtection() {
/* 213 */     populate();
/* 214 */     return this.protectToken;
/*     */   }
/*     */   
/*     */   public boolean getSignatureProtection() {
/* 218 */     populate();
/* 219 */     return this.protectSignature;
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 224 */     return populate(isServer);
/*     */   }
/*     */ 
/*     */   
/*     */   private void populate() {
/* 229 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 233 */     if (!this.populated) {
/* 234 */       NestedPolicy policy = getNestedPolicy();
/* 235 */       if (policy == null) {
/* 236 */         if (Constants.logger.isLoggable(Level.FINE)) {
/* 237 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 239 */         this.populated = true;
/* 240 */         return this.fitness;
/*     */       } 
/* 242 */       AssertionSet as = policy.getAssertionSet();
/* 243 */       Iterator<PolicyAssertion> ast = as.iterator();
/* 244 */       while (ast.hasNext()) {
/* 245 */         PolicyAssertion assertion = ast.next();
/* 246 */         if (PolicyUtil.isInitiatorToken(assertion, this.spVersion)) {
/* 247 */           this.initiatorToken = ((Token)assertion).getToken(); continue;
/* 248 */         }  if (PolicyUtil.isRecipientToken(assertion, this.spVersion)) {
/* 249 */           this.recipientToken = ((Token)assertion).getToken(); continue;
/* 250 */         }  if (PolicyUtil.isRecipientSignatureToken(assertion, this.spVersion)) {
/* 251 */           this.recipientSignatureToken = ((Token)assertion).getToken(); continue;
/* 252 */         }  if (PolicyUtil.isRecipientEncryptionToken(assertion, this.spVersion)) {
/* 253 */           this.recipientEncryptionToken = ((Token)assertion).getToken(); continue;
/* 254 */         }  if (PolicyUtil.isInitiatorSignatureToken(assertion, this.spVersion)) {
/* 255 */           this.initiatorSignatureToken = ((Token)assertion).getToken(); continue;
/* 256 */         }  if (PolicyUtil.isInitiatorEncryptionToken(assertion, this.spVersion)) {
/* 257 */           this.initiatorEncryptionToken = ((Token)assertion).getToken(); continue;
/* 258 */         }  if (PolicyUtil.isAlgorithmAssertion(assertion, this.spVersion)) {
/* 259 */           this.algSuite = (AlgorithmSuite)assertion;
/* 260 */           String sigAlgo = assertion.getAttributeValue(new QName("signatureAlgorithm"));
/* 261 */           this.algSuite.setSignatureAlgorithm(sigAlgo); continue;
/* 262 */         }  if (PolicyUtil.isIncludeTimestamp(assertion, this.spVersion)) {
/* 263 */           this.includeTimestamp = true; continue;
/* 264 */         }  if (PolicyUtil.isEncryptBeforeSign(assertion, this.spVersion)) {
/* 265 */           this.protectionOrder = "EncryptBeforeSigning"; continue;
/* 266 */         }  if (PolicyUtil.isSignBeforeEncrypt(assertion, this.spVersion)) {
/* 267 */           this.protectionOrder = "SignBeforeEncrypting"; continue;
/* 268 */         }  if (PolicyUtil.isContentOnlyAssertion(assertion, this.spVersion)) {
/* 269 */           this.contentOnly = false; continue;
/* 270 */         }  if (PolicyUtil.isMessageLayout(assertion, this.spVersion)) {
/* 271 */           this.layout = ((Layout)assertion).getMessageLayout(); continue;
/* 272 */         }  if (PolicyUtil.isProtectTokens(assertion, this.spVersion)) {
/* 273 */           this.protectToken = true; continue;
/* 274 */         }  if (PolicyUtil.isEncryptSignature(assertion, this.spVersion)) {
/* 275 */           this.protectSignature = true; continue;
/* 276 */         }  if (PolicyUtil.disableTimestampSigning(assertion)) {
/* 277 */           this.disableTimestampSigning = true; continue;
/*     */         } 
/* 279 */         if (!assertion.isOptional()) {
/* 280 */           Constants.log_invalid_assertion(assertion, isServer, "AsymmetricBinding");
/* 281 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 285 */       this.populated = true;
/*     */     } 
/* 287 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 291 */     return this.spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\AsymmetricBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */