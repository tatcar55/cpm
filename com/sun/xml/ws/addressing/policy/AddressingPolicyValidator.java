/*     */ package com.sun.xml.ws.addressing.policy;
/*     */ 
/*     */ import com.sun.xml.ws.addressing.W3CAddressingMetadataConstants;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.policy.NestedPolicy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyLogger;
/*     */ import com.sun.xml.ws.policy.spi.PolicyAssertionValidator;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AddressingPolicyValidator
/*     */   implements PolicyAssertionValidator
/*     */ {
/*  63 */   private static final ArrayList<QName> supportedAssertions = new ArrayList<QName>();
/*     */   
/*     */   static {
/*  66 */     supportedAssertions.add(new QName(AddressingVersion.MEMBER.policyNsUri, "UsingAddressing"));
/*  67 */     supportedAssertions.add(W3CAddressingMetadataConstants.WSAM_ADDRESSING_ASSERTION);
/*  68 */     supportedAssertions.add(W3CAddressingMetadataConstants.WSAM_ANONYMOUS_NESTED_ASSERTION);
/*  69 */     supportedAssertions.add(W3CAddressingMetadataConstants.WSAM_NONANONYMOUS_NESTED_ASSERTION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PolicyAssertionValidator.Fitness validateClientSide(PolicyAssertion assertion) {
/*  79 */     return supportedAssertions.contains(assertion.getName()) ? PolicyAssertionValidator.Fitness.SUPPORTED : PolicyAssertionValidator.Fitness.UNKNOWN;
/*     */   }
/*     */   
/*     */   public PolicyAssertionValidator.Fitness validateServerSide(PolicyAssertion assertion) {
/*  83 */     if (!supportedAssertions.contains(assertion.getName())) {
/*  84 */       return PolicyAssertionValidator.Fitness.UNKNOWN;
/*     */     }
/*     */     
/*  87 */     if (assertion.getName().equals(W3CAddressingMetadataConstants.WSAM_ADDRESSING_ASSERTION)) {
/*  88 */       NestedPolicy nestedPolicy = assertion.getNestedPolicy();
/*  89 */       if (nestedPolicy != null) {
/*  90 */         boolean requiresAnonymousResponses = false;
/*  91 */         boolean requiresNonAnonymousResponses = false;
/*  92 */         for (PolicyAssertion nestedAsser : nestedPolicy.getAssertionSet()) {
/*  93 */           if (nestedAsser.getName().equals(W3CAddressingMetadataConstants.WSAM_ANONYMOUS_NESTED_ASSERTION)) {
/*  94 */             requiresAnonymousResponses = true; continue;
/*  95 */           }  if (nestedAsser.getName().equals(W3CAddressingMetadataConstants.WSAM_NONANONYMOUS_NESTED_ASSERTION)) {
/*  96 */             requiresNonAnonymousResponses = true; continue;
/*     */           } 
/*  98 */           LOGGER.warning("Found unsupported assertion:\n" + nestedAsser + "\nnested into assertion:\n" + assertion);
/*  99 */           return PolicyAssertionValidator.Fitness.UNSUPPORTED;
/*     */         } 
/*     */ 
/*     */         
/* 103 */         if (requiresAnonymousResponses && requiresNonAnonymousResponses) {
/* 104 */           LOGGER.warning("Only one among AnonymousResponses and NonAnonymousResponses can be nested in an Addressing assertion");
/* 105 */           return PolicyAssertionValidator.Fitness.INVALID;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     return PolicyAssertionValidator.Fitness.SUPPORTED;
/*     */   }
/*     */   
/*     */   public String[] declareSupportedDomains() {
/* 114 */     return new String[] { AddressingVersion.MEMBER.policyNsUri, AddressingVersion.W3C.policyNsUri, "http://www.w3.org/2007/05/addressing/metadata" };
/*     */   }
/*     */   
/* 117 */   private static final PolicyLogger LOGGER = PolicyLogger.getLogger(AddressingPolicyValidator.class);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\policy\AddressingPolicyValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */