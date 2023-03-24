/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.ws.security.policy.Header;
/*     */ import com.sun.xml.ws.security.policy.SignedElements;
/*     */ import com.sun.xml.ws.security.policy.SignedParts;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignaturePolicy;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*     */ import com.sun.xml.wss.impl.policy.mls.Target;
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
/*     */ 
/*     */ public class IntegrityAssertionProcessor
/*     */ {
/*     */   private boolean contentOnly = false;
/*     */   private boolean seenBody = false;
/*     */   private boolean seenAttachments = false;
/*  63 */   private HashSet<Header> signParts = new HashSet<Header>();
/*     */   private boolean allHeaders = false;
/*  65 */   private SignatureTargetCreator targetCreator = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IntegrityAssertionProcessor(AlgorithmSuite algorithmSuite, boolean contentOnly) {
/*  71 */     this.contentOnly = contentOnly;
/*  72 */     this.targetCreator = new SignatureTargetCreator(false, algorithmSuite, contentOnly);
/*     */   }
/*     */   
/*     */   public SignatureTargetCreator getTargetCreator() {
/*  76 */     return this.targetCreator;
/*     */   }
/*     */   
/*     */   public void process(SignedParts signedParts, SignaturePolicy.FeatureBinding binding) {
/*  80 */     Iterator<Header> tv = signedParts.getHeaders();
/*  81 */     if (SecurityPolicyUtil.isSignedPartsEmpty(signedParts)) {
/*  82 */       if (!this.allHeaders) {
/*  83 */         SignatureTarget target = this.targetCreator.newURISignatureTarget("");
/*  84 */         this.targetCreator.addTransform(target);
/*  85 */         target.setValue("ALL_MESSAGE_HEADERS");
/*  86 */         target.isSOAPHeadersOnly(true);
/*  87 */         binding.addTargetBinding(target);
/*  88 */         target.setContentOnly(this.contentOnly);
/*  89 */         this.allHeaders = true;
/*     */       } 
/*  91 */       if (!this.seenBody) {
/*  92 */         SignatureTarget target = this.targetCreator.newQNameSignatureTarget(Target.BODY_QNAME);
/*  93 */         this.targetCreator.addTransform(target);
/*  94 */         binding.addTargetBinding(target);
/*  95 */         target.setContentOnly(this.contentOnly);
/*  96 */         this.seenBody = true;
/*     */       } 
/*     */     } else {
/*  99 */       while (tv.hasNext()) {
/* 100 */         Header ht = tv.next();
/* 101 */         if (!this.allHeaders && !seenSignTarget(ht)) {
/* 102 */           SignatureTarget target = this.targetCreator.newQNameSignatureTarget(new QName(ht.getURI(), ht.getLocalName()));
/* 103 */           this.targetCreator.addTransform(target);
/* 104 */           target.isSOAPHeadersOnly(true);
/* 105 */           target.setContentOnly(this.contentOnly);
/* 106 */           binding.addTargetBinding(target);
/*     */         } 
/*     */       } 
/* 109 */       if (signedParts.hasBody() && 
/* 110 */         !this.seenBody) {
/* 111 */         SignatureTarget target = this.targetCreator.newQNameSignatureTarget(Target.BODY_QNAME);
/* 112 */         this.targetCreator.addTransform(target);
/* 113 */         target.setContentOnly(this.contentOnly);
/* 114 */         binding.addTargetBinding(target);
/* 115 */         this.seenBody = true;
/*     */       } 
/*     */       
/* 118 */       if (signedParts.hasAttachments() && 
/* 119 */         !this.seenAttachments) {
/* 120 */         SignatureTarget target = this.targetCreator.newURISignatureTarget("");
/* 121 */         target.setValue("cid:*");
/* 122 */         this.targetCreator.addAttachmentTransform(target, signedParts.attachmentProtectionType());
/* 123 */         binding.addTargetBinding(target);
/* 124 */         this.seenAttachments = true;
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     this.signParts.clear();
/*     */   }
/*     */   
/*     */   public void process(SignedElements signedElements, SignaturePolicy.FeatureBinding binding) {
/* 132 */     Iterator<String> itr = signedElements.getTargets();
/* 133 */     while (itr.hasNext()) {
/* 134 */       String xpathTarget = itr.next();
/* 135 */       SignatureTarget target = this.targetCreator.newXpathSignatureTarget(xpathTarget);
/* 136 */       this.targetCreator.addTransform(target);
/* 137 */       target.setContentOnly(this.contentOnly);
/*     */       
/* 139 */       binding.addTargetBinding(target);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean seenSignTarget(Header name) {
/* 151 */     if (this.signParts.contains(name)) {
/* 152 */       return true;
/*     */     }
/* 154 */     this.signParts.add(name);
/* 155 */     return false;
/*     */   }
/*     */   
/*     */   public void process(QName targetName, SignaturePolicy.FeatureBinding binding) {
/* 159 */     SignatureTarget target = this.targetCreator.newQNameSignatureTarget(targetName);
/* 160 */     this.targetCreator.addTransform(target);
/* 161 */     binding.addTargetBinding(target);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\IntegrityAssertionProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */