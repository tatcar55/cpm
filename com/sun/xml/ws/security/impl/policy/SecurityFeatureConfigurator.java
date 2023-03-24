/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.api.ha.StickyFeature;
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.Policy;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapKey;
/*     */ import com.sun.xml.ws.policy.jaxws.spi.PolicyFeatureConfigurator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class SecurityFeatureConfigurator
/*     */   implements PolicyFeatureConfigurator
/*     */ {
/*     */   private static final String SC_LOCAL_NAME = "SecureConversationToken";
/*     */   private static final String DIGEST_PASSWORD_LOCAL_NAME = "HashPassword";
/*     */   private static final String NONCE_LOCAL_NAME = "Nonce";
/*     */   
/*     */   public static final class SecurityStickyFeature
/*     */     extends WebServiceFeature
/*     */     implements StickyFeature
/*     */   {
/*  77 */     public static final String ID = SecurityStickyFeature.class.getName();
/*     */     
/*     */     private boolean nonceManagerUsed;
/*     */     
/*     */     private boolean scUsed;
/*     */     
/*     */     public String getID() {
/*  84 */       return ID;
/*     */     }
/*     */     
/*     */     public boolean isNonceManagerUsed() {
/*  88 */       return this.nonceManagerUsed;
/*     */     }
/*     */     
/*     */     public void nonceManagerUsed() {
/*  92 */       this.nonceManagerUsed = true;
/*     */     }
/*     */     
/*     */     public boolean isScUsed() {
/*  96 */       return this.scUsed;
/*     */     }
/*     */     
/*     */     public void scUsed() {
/* 100 */       this.scUsed = true;
/*     */     }
/*     */   }
/*     */   
/*     */   public Collection<WebServiceFeature> getFeatures(PolicyMapKey key, PolicyMap policyMap) throws PolicyException {
/* 105 */     SecurityStickyFeature stickyFeature = null;
/* 106 */     Collection<WebServiceFeature> features = new LinkedList<WebServiceFeature>();
/* 107 */     if (key != null && policyMap != null) {
/* 108 */       Policy policy = policyMap.getEndpointEffectivePolicy(key);
/* 109 */       if (policy != null) {
/* 110 */         for (AssertionSet alternative : policy) {
/* 111 */           stickyFeature = resolveStickiness(alternative.iterator(), stickyFeature);
/*     */ 
/*     */           
/* 114 */           List<WebServiceFeature> singleAlternativeFeatures = getFeatures(alternative);
/* 115 */           if (!singleAlternativeFeatures.isEmpty()) {
/* 116 */             features.addAll(singleAlternativeFeatures);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 122 */     if (stickyFeature != null) {
/* 123 */       features.add(stickyFeature);
/*     */     }
/*     */     
/* 126 */     return features;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 136 */   private static final Set<QName> STICKINESS_ENABLERS = Collections.unmodifiableSet(new HashSet<QName>(Arrays.asList(new QName[] { new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "SecureConversationToken"), new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "SecureConversationToken"), new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "HashPassword"), new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "HashPassword"), new QName(SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri, "Nonce"), new QName(SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri, "Nonce") })));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SecurityStickyFeature resolveStickiness(Iterator<PolicyAssertion> assertions, SecurityStickyFeature currentFeature) {
/* 149 */     while (assertions.hasNext()) {
/* 150 */       PolicyAssertion assertion = assertions.next();
/* 151 */       if (STICKINESS_ENABLERS.contains(assertion.getName())) {
/* 152 */         if (currentFeature == null) {
/* 153 */           currentFeature = new SecurityStickyFeature();
/*     */         }
/*     */         
/* 156 */         if ("SecureConversationToken".equals(assertion.getName().getLocalPart())) {
/* 157 */           currentFeature.scUsed();
/*     */         }
/*     */         
/* 160 */         if ("Nonce".equals(assertion.getName().getLocalPart()) || "HashPassword".equals(assertion.getName().getLocalPart()))
/*     */         {
/* 162 */           currentFeature.nonceManagerUsed();
/*     */         }
/*     */       } 
/*     */       
/* 166 */       if (assertion.hasParameters()) {
/* 167 */         currentFeature = resolveStickiness(assertion.getParametersIterator(), currentFeature);
/*     */       }
/*     */       
/* 170 */       if (assertion.hasNestedPolicy()) {
/* 171 */         currentFeature = resolveStickiness(assertion.getNestedPolicy().getAssertionSet().iterator(), currentFeature);
/*     */       }
/*     */     } 
/*     */     
/* 175 */     return currentFeature;
/*     */   }
/*     */ 
/*     */   
/*     */   private List<WebServiceFeature> getFeatures(AssertionSet alternative) {
/* 180 */     return Collections.emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\SecurityFeatureConfigurator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */