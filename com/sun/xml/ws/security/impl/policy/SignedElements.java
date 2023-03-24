/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.SignedElements;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ public class SignedElements
/*     */   extends PolicyAssertion
/*     */   implements SignedElements, SecurityAssertionValidator
/*     */ {
/*     */   private String xpathVersion;
/*     */   private List<String> targetList;
/*     */   private boolean populated = false;
/*  73 */   private static QName XPathVersion = new QName("XPathVersion");
/*  74 */   private static List<String> emptyList = Collections.emptyList();
/*  75 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   
/*     */   private SecurityPolicyVersion spVersion;
/*     */   
/*     */   public SignedElements() {
/*  80 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   }
/*     */   
/*     */   public SignedElements(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  84 */     super(name, nestedAssertions, nestedAlternative);
/*  85 */     String nsUri = getName().getNamespaceURI();
/*  86 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*     */   }
/*     */   
/*     */   public String getXPathVersion() {
/*  90 */     populate();
/*  91 */     return this.xpathVersion;
/*     */   }
/*     */   
/*     */   public void setXPathVersion(String version) {
/*  95 */     this.xpathVersion = version;
/*     */   }
/*     */   
/*     */   public void addTarget(String target) {
/*  99 */     if (this.targetList == null) {
/* 100 */       this.targetList = new ArrayList<String>();
/*     */     }
/* 102 */     this.targetList.add(target);
/*     */   }
/*     */   
/*     */   public void removeTarget(String target) {
/* 106 */     if (this.targetList != null) {
/* 107 */       this.targetList.remove(target);
/*     */     }
/*     */   }
/*     */   
/*     */   public Iterator<String> getTargets() {
/* 112 */     populate();
/* 113 */     if (this.targetList != null) {
/* 114 */       return this.targetList.iterator();
/*     */     }
/* 116 */     return emptyList.iterator();
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 120 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 123 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 127 */     if (!this.populated) {
/* 128 */       this.xpathVersion = getAttributeValue(XPathVersion);
/*     */       
/* 130 */       if (hasNestedAssertions()) {
/*     */         
/* 132 */         Iterator<PolicyAssertion> it = getNestedAssertionsIterator();
/* 133 */         while (it.hasNext()) {
/* 134 */           PolicyAssertion assertion = it.next();
/* 135 */           if (PolicyUtil.isXPath(assertion, this.spVersion)) {
/* 136 */             addTarget(assertion.getValue()); continue;
/*     */           } 
/* 138 */           if (!assertion.isOptional()) {
/* 139 */             Constants.log_invalid_assertion(assertion, isServer, "SignedElements");
/* 140 */             this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 145 */       this.populated = true;
/*     */     } 
/* 147 */     return this.fitness;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\SignedElements.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */