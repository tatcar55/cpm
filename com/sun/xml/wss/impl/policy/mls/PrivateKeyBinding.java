/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import com.sun.xml.wss.impl.MessageConstants;
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.PrivateKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrivateKeyBinding
/*     */   extends WSSPolicy
/*     */ {
/*  68 */   String _keyAlgorithm = MessageConstants._EMPTY;
/*  69 */   String _keyIdentifier = MessageConstants._EMPTY;
/*     */   
/*  71 */   PrivateKey _privateKey = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrivateKeyBinding() {
/*  77 */     setPolicyIdentifier("PrivateKeyBinding");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrivateKeyBinding(String keyIdentifier, String keyAlgorithm) {
/*  86 */     this();
/*     */     
/*  88 */     this._keyIdentifier = keyIdentifier;
/*  89 */     this._keyAlgorithm = keyAlgorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeyIdentifier(String keyIdentifier) {
/*  97 */     this._keyIdentifier = keyIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKeyIdentifier() {
/* 104 */     return this._keyIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeyAlgorithm(String keyAlgorithm) {
/* 116 */     this._keyAlgorithm = keyAlgorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKeyAlgorithm() {
/* 123 */     return this._keyAlgorithm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrivateKey(PrivateKey privateKey) {
/* 131 */     this._privateKey = privateKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PrivateKey getPrivateKey() {
/* 138 */     return this._privateKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(WSSPolicy binding) {
/*     */     try {
/* 149 */       if (!PolicyTypeUtil.privateKeyBinding((SecurityPolicy)binding)) {
/* 150 */         return false;
/*     */       }
/* 152 */       PrivateKeyBinding policy = (PrivateKeyBinding)binding;
/*     */       
/* 154 */       boolean b1 = this._keyIdentifier.equals("") ? true : this._keyIdentifier.equals(policy.getKeyIdentifier());
/* 155 */       if (!b1) return false; 
/* 156 */       boolean b2 = this._keyAlgorithm.equals("") ? true : this._keyAlgorithm.equals(policy.getKeyAlgorithm());
/* 157 */       if (!b2) return false; 
/* 158 */     } catch (Exception e) {}
/*     */     
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equalsIgnoreTargets(WSSPolicy binding) {
/* 167 */     return equals(binding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 175 */     PrivateKeyBinding pkBinding = new PrivateKeyBinding();
/*     */     
/*     */     try {
/* 178 */       pkBinding.setKeyAlgorithm(this._keyAlgorithm);
/* 179 */       pkBinding.setKeyIdentifier(this._keyIdentifier);
/*     */       
/* 181 */       KeyFactory factory = KeyFactory.getInstance(this._privateKey.getAlgorithm());
/* 182 */       pkBinding.setPrivateKey((PrivateKey)factory.translateKey(this._privateKey));
/* 183 */     } catch (Exception e) {
/* 184 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 187 */     return pkBinding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 194 */     return "PrivateKeyBinding";
/*     */   }
/*     */   
/*     */   public String toString() {
/* 198 */     return "PrivateKeyBinding::" + getKeyAlgorithm() + "::" + this._keyIdentifier;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\PrivateKeyBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */