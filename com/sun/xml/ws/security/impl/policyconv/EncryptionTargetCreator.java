/*     */ package com.sun.xml.ws.security.impl.policyconv;
/*     */ 
/*     */ import com.sun.xml.ws.security.impl.policy.Constants;
/*     */ import com.sun.xml.ws.security.policy.AlgorithmSuite;
/*     */ import com.sun.xml.wss.impl.policy.mls.EncryptionTarget;
/*     */ import java.util.logging.Level;
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
/*     */ public class EncryptionTargetCreator
/*     */ {
/*     */   public AlgorithmSuite algorithmSuite;
/*     */   public boolean enforce = false;
/*     */   
/*     */   public EncryptionTargetCreator(AlgorithmSuite algorithmSuite, boolean enforce) {
/*  58 */     this.algorithmSuite = algorithmSuite;
/*  59 */     this.enforce = enforce;
/*     */   }
/*     */   
/*     */   public EncryptionTarget newQNameEncryptionTarget(QName targetValue) {
/*  63 */     EncryptionTarget target = new EncryptionTarget();
/*  64 */     target.setEnforce(this.enforce);
/*  65 */     target.setDataEncryptionAlgorithm(this.algorithmSuite.getEncryptionAlgorithm());
/*  66 */     target.setType("qname");
/*  67 */     target.setQName(targetValue);
/*     */     
/*  69 */     target.setValue("{" + targetValue.getNamespaceURI() + "}" + targetValue.getLocalPart());
/*  70 */     target.setContentOnly(false);
/*  71 */     if (Constants.logger.isLoggable(Level.FINE)) {
/*  72 */       Constants.logger.log(Level.FINE, "QName Encryption Target with value " + target.getValue() + " has been added");
/*     */     }
/*  74 */     return target;
/*     */   }
/*     */   
/*     */   public EncryptionTarget newXpathEncryptionTarget(String xpathTarget) {
/*  78 */     EncryptionTarget target = new EncryptionTarget();
/*  79 */     target.setType("xpath");
/*  80 */     target.setValue(xpathTarget);
/*  81 */     target.setEnforce(this.enforce);
/*  82 */     target.setDataEncryptionAlgorithm(this.algorithmSuite.getEncryptionAlgorithm());
/*  83 */     target.setContentOnly(false);
/*  84 */     if (Constants.logger.isLoggable(Level.FINE)) {
/*  85 */       Constants.logger.log(Level.FINE, "XPath Encryption Target with value " + target.getValue() + " has been added");
/*     */     }
/*  87 */     return target;
/*     */   }
/*     */   
/*     */   public EncryptionTarget newURIEncryptionTarget(String uri) {
/*  91 */     EncryptionTarget target = new EncryptionTarget();
/*  92 */     target.setEnforce(this.enforce);
/*  93 */     target.setDataEncryptionAlgorithm(this.algorithmSuite.getEncryptionAlgorithm());
/*  94 */     target.setType("uri");
/*  95 */     target.setValue(uri);
/*  96 */     target.setContentOnly(false);
/*  97 */     if (Constants.logger.isLoggable(Level.FINE)) {
/*  98 */       Constants.logger.log(Level.FINE, "URI Encryption Target with value " + target.getValue() + " has been added");
/*     */     }
/* 100 */     return target;
/*     */   }
/*     */   
/*     */   public void addAttachmentTransform(EncryptionTarget target, String transformURI) {
/* 104 */     EncryptionTarget.Transform tr = target.newEncryptionTransform();
/* 105 */     tr.setTransform(transformURI);
/* 106 */     target.addCipherReferenceTransform(tr);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\impl\policyconv\EncryptionTargetCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */