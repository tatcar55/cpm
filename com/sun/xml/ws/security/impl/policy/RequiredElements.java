/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.RequiredElements;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
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
/*     */ 
/*     */ 
/*     */ public class RequiredElements
/*     */   extends PolicyAssertion
/*     */   implements RequiredElements, SecurityAssertionValidator
/*     */ {
/*     */   private String xpathVersion;
/*     */   private List<String> targetList;
/*     */   private boolean populated = false;
/*  75 */   private static QName XPathVersion = new QName("XPathVersion");
/*  76 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   private SecurityPolicyVersion spVersion;
/*     */   
/*     */   public RequiredElements() {
/*  80 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   }
/*     */   
/*     */   public RequiredElements(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  84 */     super(name, nestedAssertions, nestedAlternative);
/*  85 */     String nsUri = getName().getNamespaceURI();
/*  86 */     if (SecurityPolicyVersion.SECURITYPOLICY200507.namespaceUri.equals(nsUri)) {
/*  87 */       this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*  88 */     } else if (SecurityPolicyVersion.SECURITYPOLICY12NS.namespaceUri.equals(nsUri)) {
/*  89 */       this.spVersion = SecurityPolicyVersion.SECURITYPOLICY12NS;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getXPathVersion() {
/*  94 */     populate();
/*  95 */     return this.xpathVersion;
/*     */   }
/*     */   
/*     */   public void setXPathVersion(String version) {
/*  99 */     this.xpathVersion = version;
/*     */   }
/*     */   
/*     */   public void addTarget(String target) {
/* 103 */     if (this.targetList == null) {
/* 104 */       this.targetList = new ArrayList<String>();
/*     */     }
/* 106 */     this.targetList.add(target);
/*     */   }
/*     */   
/*     */   public void removeTarget(String target) {
/* 110 */     if (this.targetList != null) {
/* 111 */       this.targetList.remove(target);
/*     */     }
/*     */   }
/*     */   
/*     */   public Iterator getTargets() {
/* 116 */     populate();
/* 117 */     if (this.targetList != null) {
/* 118 */       return this.targetList.iterator();
/*     */     }
/* 120 */     return Collections.emptyList().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 125 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 128 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 132 */     if (!this.populated) {
/* 133 */       this.xpathVersion = getAttributeValue(XPathVersion);
/* 134 */       if (hasNestedAssertions()) {
/* 135 */         Iterator<PolicyAssertion> it = getNestedAssertionsIterator();
/* 136 */         if (it.hasNext()) {
/* 137 */           PolicyAssertion assertion = it.next();
/* 138 */           if (PolicyUtil.isXPath(assertion, this.spVersion)) {
/* 139 */             addTarget(assertion.getValue());
/*     */           }
/* 141 */           else if (!assertion.isOptional()) {
/* 142 */             Constants.log_invalid_assertion(assertion, isServer, "RequiredElements");
/* 143 */             this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 148 */       this.populated = true;
/*     */     } 
/* 150 */     return this.fitness;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\RequiredElements.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */