/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.EncryptedParts;
/*     */ import com.sun.xml.ws.security.policy.Header;
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
/*     */ public class EncryptedParts
/*     */   extends PolicyAssertion
/*     */   implements EncryptedParts, SecurityAssertionValidator
/*     */ {
/*     */   private boolean _body;
/*     */   private boolean _attachments;
/*     */   private List<Header> header;
/*     */   private boolean populated = false;
/*  68 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   
/*     */   private SecurityPolicyVersion spVersion;
/*     */   
/*     */   public EncryptedParts() {
/*  73 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   }
/*     */   public EncryptedParts(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  76 */     super(name, nestedAssertions, nestedAlternative);
/*  77 */     String nsUri = getName().getNamespaceURI();
/*  78 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*     */   }
/*     */   
/*     */   public void addBody() {
/*  82 */     this._body = true;
/*     */   }
/*     */   
/*     */   public boolean hasBody() {
/*  86 */     populate();
/*  87 */     return this._body;
/*     */   }
/*     */   
/*     */   public boolean hasAttachments() {
/*  91 */     populate();
/*  92 */     return this._attachments;
/*     */   }
/*     */   
/*     */   public void addTarget(QName targetName) {
/*  96 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Iterator getTargets() {
/* 100 */     populate();
/* 101 */     if (this.header == null) {
/* 102 */       return Collections.emptyList().iterator();
/*     */     }
/* 104 */     return this.header.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 112 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 115 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 119 */     if (!this.populated) {
/* 120 */       if (hasNestedAssertions()) {
/*     */         
/* 122 */         Iterator<PolicyAssertion> it = getNestedAssertionsIterator();
/* 123 */         while (it.hasNext()) {
/* 124 */           PolicyAssertion assertion = it.next();
/* 125 */           if (PolicyUtil.isBody(assertion, this.spVersion)) {
/* 126 */             this._body = true; continue;
/* 127 */           }  if (PolicyUtil.isAttachments(assertion, this.spVersion)) {
/* 128 */             this._attachments = true; continue;
/*     */           } 
/* 130 */           if (this.header == null) {
/* 131 */             this.header = new ArrayList<Header>();
/*     */           }
/* 133 */           if (PolicyUtil.isHeader(assertion, this.spVersion)) {
/* 134 */             this.header.add((Header)assertion); continue;
/*     */           } 
/* 136 */           if (!assertion.isOptional()) {
/* 137 */             Constants.log_invalid_assertion(assertion, isServer, "EncryptedParts");
/* 138 */             this.fitness = SecurityAssertionValidator.AssertionFitness.HAS_UNKNOWN_ASSERTION;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 144 */       this.populated = true;
/*     */     } 
/* 146 */     return this.fitness;
/*     */   }
/*     */   
/*     */   public void removeTarget(QName targetName) {
/* 150 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void removeBody() {
/* 154 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\EncryptedParts.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */