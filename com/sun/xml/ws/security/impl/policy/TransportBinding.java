/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.policy.HttpsToken;
/*     */ import com.sun.xml.ws.security.policy.MessageLayout;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.Token;
/*     */ import com.sun.xml.ws.security.policy.TransportBinding;
/*     */ import java.util.Collection;
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
/*     */ public class TransportBinding
/*     */   extends PolicyAssertion
/*     */   implements TransportBinding, SecurityAssertionValidator
/*     */ {
/*     */   HttpsToken transportToken;
/*     */   private AlgorithmSuite algSuite;
/*     */   boolean includeTimeStamp = false;
/*  66 */   MessageLayout layout = MessageLayout.Lax;
/*     */   boolean populated = false;
/*  68 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   
/*     */   private SecurityPolicyVersion spVersion;
/*     */ 
/*     */   
/*     */   public TransportBinding() {
/*  74 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   }
/*     */   
/*     */   public TransportBinding(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  78 */     super(name, nestedAssertions, nestedAlternative);
/*  79 */     String nsUri = getName().getNamespaceURI();
/*  80 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*     */   }
/*     */   
/*     */   public void addTransportToken(Token token) {
/*  84 */     this.transportToken = (HttpsToken)token;
/*     */   }
/*     */   
/*     */   public Token getTransportToken() {
/*  88 */     populate();
/*  89 */     return (Token)this.transportToken;
/*     */   }
/*     */   
/*     */   public void setAlgorithmSuite(AlgorithmSuite algSuite) {
/*  93 */     this.algSuite = algSuite;
/*     */   }
/*     */   
/*     */   public AlgorithmSuite getAlgorithmSuite() {
/*  97 */     populate();
/*  98 */     return this.algSuite;
/*     */   }
/*     */   
/*     */   public void includeTimeStamp(boolean value) {
/* 102 */     this.includeTimeStamp = value;
/*     */   }
/*     */   
/*     */   public boolean isIncludeTimeStamp() {
/* 106 */     populate();
/* 107 */     return this.includeTimeStamp;
/*     */   }
/*     */   
/*     */   public void setLayout(MessageLayout layout) {
/* 111 */     this.layout = layout;
/*     */   }
/*     */   
/*     */   public MessageLayout getLayout() {
/* 115 */     populate();
/* 116 */     return this.layout;
/*     */   }
/*     */   
/*     */   public boolean isSignContent() {
/* 120 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */   
/*     */   public void setSignContent(boolean contentOnly) {
/* 124 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */   
/*     */   public void setProtectionOrder(String order) {
/* 128 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */   
/*     */   public String getProtectionOrder() {
/* 132 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */   
/*     */   public void setTokenProtection(boolean token) {
/* 136 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */   
/*     */   public void setSignatureProtection(boolean token) {
/* 140 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */   
/*     */   public boolean getTokenProtection() {
/* 144 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */   
/*     */   public boolean getSignatureProtection() {
/* 148 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 152 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 155 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 159 */     if (!this.populated) {
/* 160 */       NestedPolicy policy = getNestedPolicy();
/* 161 */       AssertionSet assertions = policy.getAssertionSet();
/* 162 */       if (assertions == null) {
/* 163 */         if (Constants.logger.getLevel() == Level.FINE) {
/* 164 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 166 */         this.populated = true;
/* 167 */         return this.fitness;
/*     */       } 
/* 169 */       for (PolicyAssertion assertion : assertions) {
/* 170 */         if (PolicyUtil.isAlgorithmAssertion(assertion, this.spVersion)) {
/* 171 */           this.algSuite = (AlgorithmSuite)assertion;
/* 172 */           String sigAlgo = assertion.getAttributeValue(new QName("signatureAlgorithm"));
/* 173 */           this.algSuite.setSignatureAlgorithm(sigAlgo); continue;
/* 174 */         }  if (PolicyUtil.isToken(assertion, this.spVersion)) {
/* 175 */           this.transportToken = (HttpsToken)((Token)assertion).getToken(); continue;
/* 176 */         }  if (PolicyUtil.isMessageLayout(assertion, this.spVersion)) {
/* 177 */           this.layout = ((Layout)assertion).getMessageLayout(); continue;
/* 178 */         }  if (PolicyUtil.isIncludeTimestamp(assertion, this.spVersion)) {
/* 179 */           this.includeTimeStamp = true; continue;
/*     */         } 
/* 181 */         if (!assertion.isOptional()) {
/* 182 */           Constants.log_invalid_assertion(assertion, isServer, "TransportBinding");
/* 183 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 187 */       this.populated = true;
/*     */     } 
/* 189 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public boolean isDisableTimestampSigning() {
/* 193 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public SecurityPolicyVersion getSecurityPolicyVersion() {
/* 197 */     return this.spVersion;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\TransportBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */