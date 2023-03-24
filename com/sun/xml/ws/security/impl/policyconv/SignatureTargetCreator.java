/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.wss.impl.policy.mls.Parameter;
/*     */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*     */ import java.security.spec.AlgorithmParameterSpec;
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
/*     */ public class SignatureTargetCreator
/*     */ {
/*     */   private boolean enforce = false;
/*  57 */   private AlgorithmSuite algorithmSuite = null;
/*     */   
/*     */   private boolean contentOnly = false;
/*     */ 
/*     */   
/*     */   public SignatureTargetCreator(boolean enforce, AlgorithmSuite algorithmSuite, boolean contentOnly) {
/*  63 */     this.enforce = enforce;
/*  64 */     this.algorithmSuite = algorithmSuite;
/*     */   }
/*     */   
/*     */   public SignatureTarget newURISignatureTarget(String uid) {
/*  68 */     if (uid != null) {
/*  69 */       SignatureTarget target = new SignatureTarget();
/*  70 */       target.setType("uri");
/*  71 */       target.setDigestAlgorithm(this.algorithmSuite.getDigestAlgorithm());
/*  72 */       target.setValue("#" + uid);
/*  73 */       addTransform(target);
/*  74 */       target.setEnforce(this.enforce);
/*  75 */       return target;
/*     */     } 
/*  77 */     return null;
/*     */   }
/*     */   
/*     */   public SignatureTarget newXpathSignatureTarget(String xpathTarget) {
/*  81 */     SignatureTarget target = new SignatureTarget();
/*  82 */     target.setType("xpath");
/*  83 */     target.setDigestAlgorithm(this.algorithmSuite.getDigestAlgorithm());
/*  84 */     target.setValue(xpathTarget);
/*  85 */     target.setContentOnly(this.contentOnly);
/*  86 */     target.setEnforce(this.enforce);
/*  87 */     return target;
/*     */   }
/*     */   
/*     */   public SignatureTarget newQNameSignatureTarget(QName name) {
/*  91 */     SignatureTarget target = new SignatureTarget();
/*  92 */     target.setType("qname");
/*  93 */     target.setDigestAlgorithm(this.algorithmSuite.getDigestAlgorithm());
/*  94 */     target.setContentOnly(this.contentOnly);
/*  95 */     target.setEnforce(this.enforce);
/*  96 */     target.setQName(name);
/*  97 */     return target;
/*     */   }
/*     */   
/*     */   public void addTransform(SignatureTarget target) {
/* 101 */     SignatureTarget.Transform tr = target.newSignatureTransform();
/* 102 */     if (this.algorithmSuite != null && this.algorithmSuite.getAdditionalProps().contains("InclusiveC14N")) {
/* 103 */       tr.setTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315");
/*     */     } else {
/* 105 */       tr.setTransform("http://www.w3.org/2001/10/xml-exc-c14n#");
/*     */     } 
/*     */     
/* 108 */     if (this.algorithmSuite != null && this.algorithmSuite.getAdditionalProps().contains("InclusiveC14NWithCommentsForTransforms")) {
/* 109 */       tr.setTransform("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments");
/* 110 */     } else if (this.algorithmSuite != null && this.algorithmSuite.getAdditionalProps().contains("ExclusiveC14NWithCommentsForTransforms")) {
/* 111 */       tr.setTransform("http://www.w3.org/2001/10/xml-exc-c14n#WithComments");
/*     */     } 
/* 113 */     target.addTransform(tr);
/*     */   }
/*     */   
/*     */   public void addSTRTransform(SignatureTarget target) {
/* 117 */     SignatureTarget.Transform tr = target.newSignatureTransform();
/* 118 */     tr.setTransform("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform");
/* 119 */     target.addTransform(tr);
/* 120 */     tr.setAlgorithmParameters((AlgorithmParameterSpec)new Parameter("CanonicalizationMethod", "http://www.w3.org/2001/10/xml-exc-c14n#"));
/*     */   }
/*     */   
/*     */   void addAttachmentTransform(SignatureTarget target, String transformURI) {
/* 124 */     SignatureTarget.Transform tr = target.newSignatureTransform();
/* 125 */     tr.setTransform(transformURI);
/* 126 */     target.addTransform(tr);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureTarget newURISignatureTargetForSSToken(String uid) {
/* 132 */     if (uid != null) {
/* 133 */       SignatureTarget target = new SignatureTarget();
/* 134 */       target.setType("uri");
/* 135 */       target.setDigestAlgorithm(this.algorithmSuite.getDigestAlgorithm());
/* 136 */       target.setValue("#" + uid);
/* 137 */       target.setEnforce(this.enforce);
/* 138 */       return target;
/*     */     } 
/* 140 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\SignatureTargetCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */