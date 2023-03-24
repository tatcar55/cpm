/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuiteValue;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AlgorithmSuite
/*     */   extends PolicyAssertion
/*     */   implements AlgorithmSuite, SecurityAssertionValidator
/*     */ {
/*  65 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   private AlgorithmSuiteValue value;
/*  67 */   private HashSet<String> props = new HashSet<String>();
/*     */   private boolean populated = false;
/*     */   private SecurityPolicyVersion spVersion;
/*  70 */   private String signatureAlgo = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public AlgorithmSuite() {
/*  75 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   }
/*     */   
/*     */   public AlgorithmSuite(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  79 */     super(name, nestedAssertions, nestedAlternative);
/*  80 */     String nsUri = getName().getNamespaceURI();
/*  81 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*     */   }
/*     */   
/*     */   public Set getAdditionalProps() {
/*  85 */     return this.props;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAdditionalProps(Set properties) {}
/*     */   
/*     */   public void setType(AlgorithmSuiteValue value) {
/*  92 */     this.value = value;
/*  93 */     this.populated = true;
/*     */   }
/*     */   
/*     */   public AlgorithmSuiteValue getType() {
/*  97 */     populate();
/*  98 */     return this.value;
/*     */   }
/*     */   
/*     */   public String getDigestAlgorithm() {
/* 102 */     populate();
/* 103 */     return this.value.getDigAlgorithm();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEncryptionAlgorithm() {
/* 108 */     populate();
/* 109 */     return this.value.getEncAlgorithm();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSymmetricKeyAlgorithm() {
/* 114 */     populate();
/* 115 */     return this.value.getSymKWAlgorithm();
/*     */   }
/*     */   
/*     */   public String getAsymmetricKeyAlgorithm() {
/* 119 */     populate();
/* 120 */     return this.value.getAsymKWAlgorithm();
/*     */   }
/*     */   
/*     */   public String getSignatureKDAlogrithm() {
/* 124 */     populate();
/* 125 */     return this.value.getSigKDAlgorithm();
/*     */   }
/*     */   
/*     */   public String getEncryptionKDAlogrithm() {
/* 129 */     populate();
/* 130 */     return this.value.getEncKDAlgorithm();
/*     */   }
/*     */   
/*     */   public int getMinSKLAlgorithm() {
/* 134 */     populate();
/* 135 */     return this.value.getMinSKLAlgorithm();
/*     */   }
/*     */   
/*     */   public String getSymmetricKeySignatureAlgorithm() {
/* 139 */     return "http://www.w3.org/2000/09/xmldsig#hmac-sha1";
/*     */   }
/*     */   
/*     */   public String getAsymmetricKeySignatureAlgorithm() {
/* 143 */     return "http://www.w3.org/2000/09/xmldsig#rsa-sha1";
/*     */   }
/*     */   
/*     */   private void populate() {
/* 147 */     populate(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 152 */     if (!this.populated) {
/* 153 */       NestedPolicy policy = getNestedPolicy();
/* 154 */       if (policy == null) {
/* 155 */         if (Constants.logger.isLoggable(Level.FINE)) {
/* 156 */           Constants.logger.log(Level.FINE, "NestedPolicy is null");
/*     */         }
/* 158 */         if (this.value == null) {
/* 159 */           this.value = AlgorithmSuiteValue.Basic128;
/*     */         }
/* 161 */         return this.fitness;
/*     */       } 
/* 163 */       AssertionSet as = policy.getAssertionSet();
/*     */       
/* 165 */       Iterator<PolicyAssertion> ast = as.iterator();
/* 166 */       while (ast.hasNext()) {
/* 167 */         PolicyAssertion assertion = ast.next();
/* 168 */         if (this.value == null) {
/* 169 */           AlgorithmSuiteValue av = PolicyUtil.isValidAlgorithmSuiteValue(assertion, this.spVersion);
/* 170 */           if (av != null) {
/* 171 */             this.value = av;
/*     */             continue;
/*     */           } 
/*     */         } 
/* 175 */         if (PolicyUtil.isInclusiveC14N(assertion, this.spVersion)) {
/* 176 */           this.props.add("InclusiveC14N"); continue;
/* 177 */         }  if (PolicyUtil.isXPath(assertion, this.spVersion)) {
/* 178 */           this.props.add("XPath"); continue;
/* 179 */         }  if (PolicyUtil.isXPathFilter20(assertion)) {
/* 180 */           this.props.add("XPathFilter20"); continue;
/* 181 */         }  if (PolicyUtil.isSTRTransform10(assertion, this.spVersion)) {
/* 182 */           this.props.add("STRTransform10"); continue;
/* 183 */         }  if (PolicyUtil.isInclusiveC14NWithComments(assertion)) {
/* 184 */           if (PolicyUtil.isInclusiveC14NWithCommentsForTransforms(assertion)) {
/* 185 */             this.props.add("InclusiveC14NWithCommentsForTransforms");
/*     */           }
/* 187 */           if (PolicyUtil.isInclusiveC14NWithCommentsForCm(assertion))
/* 188 */             this.props.add("InclusiveC14NWithCommentsForCm");  continue;
/*     */         } 
/* 190 */         if (PolicyUtil.isExclusiveC14NWithComments(assertion)) {
/* 191 */           if (PolicyUtil.isExclusiveC14NWithCommentsForTransforms(assertion)) {
/* 192 */             this.props.add("ExclusiveC14NWithCommentsForTransforms");
/*     */           }
/* 194 */           if (PolicyUtil.isExclusiveC14NWithCommentsForCm(assertion))
/* 195 */             this.props.add("ExclusiveC14NWithCommentsForCm"); 
/*     */           continue;
/*     */         } 
/* 198 */         if (!assertion.isOptional()) {
/* 199 */           Constants.log_invalid_assertion(assertion, isServer, "AlgorithmSuite");
/* 200 */           this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */         } 
/*     */       } 
/*     */       
/* 204 */       if (this.value == null) {
/* 205 */         this.value = AlgorithmSuiteValue.Basic128;
/*     */       }
/* 207 */       this.populated = true;
/*     */     } 
/* 209 */     return this.fitness;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getComputedKeyAlgorithm() {
/* 214 */     return "http://schemas.xmlsoap.org/ws/2005/02/sc/dk/p_sha1";
/*     */   }
/*     */   
/*     */   public int getMaxSymmetricKeyLength() {
/* 218 */     return 256;
/*     */   }
/*     */   
/*     */   public int getMinAsymmetricKeyLength() {
/* 222 */     return 1024;
/*     */   }
/*     */   
/*     */   public int getMaxAsymmetricKeyLength() {
/* 226 */     return 4096;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 230 */     return populate(isServer);
/*     */   }
/*     */   
/*     */   public void setSignatureAlgorithm(String sigAlgo) {
/* 234 */     this.signatureAlgo = sigAlgo;
/*     */   }
/*     */   public String getSignatureAlgorithm() {
/* 237 */     return this.signatureAlgo;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\AlgorithmSuite.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */