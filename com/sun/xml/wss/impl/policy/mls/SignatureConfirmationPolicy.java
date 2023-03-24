/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignatureConfirmationPolicy
/*     */   extends WSSPolicy
/*     */ {
/*  64 */   private String signatureValue = MessageConstants._EMPTY;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureConfirmationPolicy() {
/*  70 */     setPolicyIdentifier("SignatureConfirmationPolicy");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSignatureValue(String signatureValue) {
/*  78 */     this.signatureValue = signatureValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSignatureValue() {
/*  85 */     return this.signatureValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(WSSPolicy policy) {
/*  94 */     boolean assrt = false;
/*     */     try {
/*  96 */       SignatureConfirmationPolicy scPolicy = (SignatureConfirmationPolicy)policy;
/*  97 */       assrt = this.signatureValue.equals(scPolicy.getSignatureValue());
/*  98 */     } catch (Exception e) {}
/*     */     
/* 100 */     return assrt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equalsIgnoreTargets(WSSPolicy policy) {
/* 109 */     return equals(policy);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 117 */     SignatureConfirmationPolicy scPolicy = new SignatureConfirmationPolicy();
/*     */     
/*     */     try {
/* 120 */       scPolicy.setUUID(getUUID());
/* 121 */       scPolicy.setSignatureValue(this.signatureValue);
/* 122 */     } catch (Exception e) {}
/*     */     
/* 124 */     return scPolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 131 */     return "SignatureConfirmationPolicy";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\SignatureConfirmationPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */