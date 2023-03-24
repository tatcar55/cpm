/*     */ package com.sun.xml.wss.impl.dsig;
/*     */ 
/*     */ import com.sun.xml.wss.impl.policy.mls.SignatureTarget;
/*     */ import javax.xml.crypto.Data;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataWrapper
/*     */ {
/*  63 */   private Data data = null;
/*  64 */   private int type = -1;
/*  65 */   private SignatureTarget signatureTarget = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DataWrapper(Data data) {
/*  72 */     this.data = data;
/*  73 */     if (data instanceof AttachmentData) {
/*  74 */       this.type = 3;
/*  75 */     } else if (data instanceof javax.xml.crypto.NodeSetData) {
/*  76 */       this.type = 2;
/*  77 */     } else if (data instanceof javax.xml.crypto.OctetStreamData) {
/*  78 */       this.type = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Data getData() {
/*  88 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() {
/*  96 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAttachmentData() {
/* 104 */     if (this.type == 3) {
/* 105 */       return true;
/*     */     }
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNodesetData() {
/* 116 */     if (this.type == 2) {
/* 117 */       return true;
/*     */     }
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOctectData() {
/* 128 */     if (this.type == 1) {
/* 129 */       return true;
/*     */     }
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureTarget getTarget() {
/* 140 */     return this.signatureTarget;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTarget(SignatureTarget target) {
/* 148 */     this.signatureTarget = target;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\dsig\DataWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */