/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.EncryptedElements;
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
/*     */ public class EncryptedElements
/*     */   extends PolicyAssertion
/*     */   implements EncryptedElements, SecurityAssertionValidator
/*     */ {
/*     */   private String xpathVersion;
/*     */   private ArrayList<String> targetList;
/*  74 */   private static List<String> emptyList = Collections.emptyList();
/*     */   private boolean populated = false;
/*  76 */   private static QName XPathVersion = new QName("XPathVersion");
/*  77 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */ 
/*     */   
/*     */   private SecurityPolicyVersion spVersion;
/*     */ 
/*     */   
/*     */   public EncryptedElements() {
/*  84 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   }
/*     */   
/*     */   public EncryptedElements(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  88 */     super(name, nestedAssertions, nestedAlternative);
/*  89 */     String nsUri = getName().getNamespaceURI();
/*  90 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*     */   }
/*     */   
/*     */   public String getXPathVersion() {
/*  94 */     return this.xpathVersion;
/*     */   }
/*     */   
/*     */   public void setXPathVersion(String version) {
/*  98 */     this.xpathVersion = version;
/*     */   }
/*     */   
/*     */   public void addTarget(String target) {
/* 102 */     if (this.targetList == null) {
/* 103 */       this.targetList = new ArrayList<String>();
/*     */     }
/* 105 */     this.targetList.add(target);
/*     */   }
/*     */   
/*     */   public void removeTarget(String target) {
/* 109 */     if (this.targetList != null) {
/* 110 */       this.targetList.remove(target);
/*     */     }
/*     */   }
/*     */   
/*     */   public Iterator<String> getTargets() {
/* 115 */     populate();
/* 116 */     if (this.targetList != null) {
/* 117 */       return this.targetList.iterator();
/*     */     }
/* 119 */     return emptyList.iterator();
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 123 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 126 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 130 */     if (!this.populated) {
/* 131 */       this.xpathVersion = getAttributeValue(XPathVersion);
/* 132 */       if (hasNestedAssertions()) {
/* 133 */         Iterator<PolicyAssertion> it = getNestedAssertionsIterator();
/* 134 */         if (it.hasNext()) {
/* 135 */           PolicyAssertion assertion = it.next();
/* 136 */           if (PolicyUtil.isXPath(assertion, this.spVersion)) {
/* 137 */             addTarget(assertion.getValue());
/*     */           }
/* 139 */           else if (!assertion.isOptional()) {
/* 140 */             Constants.log_invalid_assertion(assertion, isServer, "EncryptedElements");
/* 141 */             this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 146 */       this.populated = true;
/*     */     } 
/* 148 */     return this.fitness;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\EncryptedElements.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */