/*     */ package com.sun.xml.ws.security.impl.policy;
/*     */ 
/*     */ import com.sun.xml.ws.policy.AssertionSet;
/*     */ import com.sun.xml.ws.policy.PolicyAssertion;
/*     */ import com.sun.xml.ws.policy.sourcemodel.AssertionData;
/*     */ import com.sun.xml.ws.security.policy.Header;
/*     */ import com.sun.xml.ws.security.policy.SecurityAssertionValidator;
/*     */ import com.sun.xml.ws.security.policy.SecurityPolicyVersion;
/*     */ import com.sun.xml.ws.security.policy.SignedParts;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignedParts
/*     */   extends PolicyAssertion
/*     */   implements SignedParts, SecurityAssertionValidator
/*     */ {
/*  64 */   private SecurityAssertionValidator.AssertionFitness fitness = SecurityAssertionValidator.AssertionFitness.IS_VALID;
/*     */   private boolean body;
/*     */   private boolean attachments;
/*  67 */   private String attachmentProtectionType = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform";
/*     */   private boolean populated = false;
/*  69 */   private Set<PolicyAssertion> targets = new HashSet<PolicyAssertion>();
/*     */ 
/*     */   
/*     */   private SecurityPolicyVersion spVersion;
/*     */ 
/*     */   
/*     */   public SignedParts() {
/*  76 */     this.spVersion = SecurityPolicyVersion.SECURITYPOLICY200507;
/*     */   }
/*     */   
/*     */   public SignedParts(AssertionData name, Collection<PolicyAssertion> nestedAssertions, AssertionSet nestedAlternative) {
/*  80 */     super(name, nestedAssertions, nestedAlternative);
/*  81 */     String nsUri = getName().getNamespaceURI();
/*  82 */     this.spVersion = PolicyUtil.getSecurityPolicyVersion(nsUri);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBody() {}
/*     */ 
/*     */   
/*     */   public boolean hasBody() {
/*  90 */     populate();
/*  91 */     return this.body;
/*     */   }
/*     */   
/*     */   public boolean hasAttachments() {
/*  95 */     populate();
/*  96 */     return this.attachments;
/*     */   }
/*     */   
/*     */   public String attachmentProtectionType() {
/* 100 */     populate();
/* 101 */     return this.attachmentProtectionType;
/*     */   }
/*     */   
/*     */   public SecurityAssertionValidator.AssertionFitness validate(boolean isServer) {
/* 105 */     return populate(isServer);
/*     */   }
/*     */   private void populate() {
/* 108 */     populate(false);
/*     */   }
/*     */   
/*     */   private synchronized SecurityAssertionValidator.AssertionFitness populate(boolean isServer) {
/* 112 */     if (!this.populated) {
/* 113 */       if (hasNestedAssertions()) {
/* 114 */         Iterator<PolicyAssertion> it = getNestedAssertionsIterator();
/* 115 */         while (it.hasNext()) {
/* 116 */           PolicyAssertion as = it.next();
/* 117 */           if (PolicyUtil.isBody(as, this.spVersion)) {
/*     */             
/* 119 */             this.body = true; continue;
/*     */           } 
/* 121 */           if (PolicyUtil.isAttachments(as, this.spVersion)) {
/* 122 */             this.attachments = true;
/* 123 */             if (as.hasParameters()) {
/* 124 */               Iterator<PolicyAssertion> attachIter = as.getParametersIterator();
/* 125 */               while (attachIter.hasNext()) {
/* 126 */                 PolicyAssertion attachType = attachIter.next();
/* 127 */                 if (PolicyUtil.isAttachmentCompleteTransform(attachType, this.spVersion)) {
/* 128 */                   this.attachmentProtectionType = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform"; continue;
/* 129 */                 }  if (PolicyUtil.isAttachmentContentTransform(attachType, this.spVersion))
/* 130 */                   this.attachmentProtectionType = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform"; 
/*     */               } 
/*     */             } 
/*     */             continue;
/*     */           } 
/* 135 */           this.targets.add(as);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 140 */       this.populated = true;
/*     */     } 
/* 142 */     return this.fitness;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addHeader(Header header) {}
/*     */ 
/*     */   
/*     */   public Iterator getHeaders() {
/* 150 */     populate();
/* 151 */     if (this.targets == null) {
/* 152 */       return Collections.emptyList().iterator();
/*     */     }
/* 154 */     return this.targets.iterator();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policy\SignedParts.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */