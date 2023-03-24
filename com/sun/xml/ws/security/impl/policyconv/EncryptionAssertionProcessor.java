/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.policy.EncryptedElements;
/*     */ import com.sun.xml.ws.security.policy.EncryptedParts;
/*     */ import com.sun.xml.ws.security.policy.Header;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionPolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
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
/*     */ public class EncryptionAssertionProcessor
/*     */ {
/*     */   private boolean bodyEncrypted = false;
/*     */   private boolean encryptAttachments = false;
/*  60 */   private HashSet<Header> encryptedParts = new HashSet<Header>();
/*     */   
/*  62 */   private EncryptionTargetCreator etCreator = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public EncryptionAssertionProcessor(AlgorithmSuite algorithmSuite, boolean enforce) {
/*  67 */     this.etCreator = new EncryptionTargetCreator(algorithmSuite, enforce);
/*     */   }
/*     */   
/*     */   public EncryptionTargetCreator getTargetCreator() {
/*  71 */     return this.etCreator;
/*     */   }
/*     */   
/*     */   public void process(EncryptedParts encryptParts, EncryptionPolicy.FeatureBinding binding) {
/*  75 */     if (SecurityPolicyUtil.isEncryptedPartsEmpty(encryptParts) && 
/*  76 */       !this.bodyEncrypted) {
/*  77 */       EncryptionTarget target = this.etCreator.newQNameEncryptionTarget(EncryptionTarget.BODY_QNAME);
/*  78 */       target.setContentOnly(true);
/*  79 */       binding.addTargetBinding(target);
/*  80 */       this.bodyEncrypted = true;
/*     */     } 
/*     */     
/*  83 */     Iterator<Header> tv = encryptParts.getTargets();
/*  84 */     while (tv.hasNext()) {
/*  85 */       Header ht = tv.next();
/*  86 */       if (!seenEncryptedParts(ht)) {
/*  87 */         EncryptionTarget target = this.etCreator.newQNameEncryptionTarget(new QName(ht.getURI(), ht.getLocalName()));
/*  88 */         target.isSOAPHeadersOnly(true);
/*  89 */         binding.addTargetBinding(target);
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     if (encryptParts.hasBody() && !this.bodyEncrypted) {
/*  94 */       EncryptionTarget target = this.etCreator.newQNameEncryptionTarget(EncryptionTarget.BODY_QNAME);
/*  95 */       target.setContentOnly(true);
/*  96 */       binding.addTargetBinding(target);
/*  97 */       this.bodyEncrypted = true;
/*     */     } 
/*     */     
/* 100 */     if (encryptParts.hasAttachments() && !this.encryptAttachments) {
/* 101 */       EncryptionTarget target = this.etCreator.newURIEncryptionTarget("cid:*");
/* 102 */       target.setContentOnly(true);
/* 103 */       this.etCreator.addAttachmentTransform(target, "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Ciphertext-Transform");
/* 104 */       binding.addTargetBinding(target);
/* 105 */       this.encryptAttachments = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void process(EncryptedElements encryptedElements, EncryptionPolicy.FeatureBinding binding) {
/* 111 */     Iterator<String> eeItr = encryptedElements.getTargets();
/* 112 */     while (eeItr.hasNext()) {
/* 113 */       String xpathTarget = eeItr.next();
/* 114 */       EncryptionTarget target = this.etCreator.newXpathEncryptionTarget(xpathTarget);
/* 115 */       binding.addTargetBinding(target);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean seenEncryptedParts(Header header) {
/* 120 */     if (this.encryptedParts.contains(header)) {
/* 121 */       return true;
/*     */     }
/* 123 */     this.encryptedParts.add(header);
/* 124 */     return false;
/*     */   }
/*     */   
/*     */   public void process(QName targetName, EncryptionPolicy.FeatureBinding binding) {
/* 128 */     EncryptionTarget target = this.etCreator.newQNameEncryptionTarget(targetName);
/* 129 */     binding.addTargetBinding(target);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\EncryptionAssertionProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */