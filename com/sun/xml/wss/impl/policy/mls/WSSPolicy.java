/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WSSPolicy
/*     */   extends MLSPolicy
/*     */   implements Cloneable
/*     */ {
/*     */   protected String UUID;
/*     */   protected String _policyIdentifier;
/*  70 */   protected MLSPolicy _keyBinding = null;
/*  71 */   protected MLSPolicy _featureBinding = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _isOptional = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean bsp = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MLSPolicy getFeatureBinding() {
/*  92 */     return this._featureBinding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MLSPolicy getKeyBinding() {
/* 103 */     return this._keyBinding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeatureBinding(MLSPolicy policy) {
/* 111 */     if (isReadOnly()) {
/* 112 */       throw new RuntimeException("Can not set FeatureBinding : Policy is Readonly");
/*     */     }
/*     */     
/* 115 */     this._featureBinding = policy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKeyBinding(MLSPolicy policy) {
/* 123 */     if (isReadOnly()) {
/* 124 */       throw new RuntimeException("Can not set KeyBinding : Policy is Readonly");
/*     */     }
/*     */     
/* 127 */     this._keyBinding = policy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPolicyIdentifier(String pi) {
/* 134 */     if (isReadOnly()) {
/* 135 */       throw new RuntimeException("Can not set PolicyIdentifier : Policy is Readonly");
/*     */     }
/*     */     
/* 138 */     this._policyIdentifier = pi;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPolicyIdentifier() {
/* 145 */     return this._policyIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUUID() {
/* 152 */     return this.UUID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUUID(String uuid) {
/* 160 */     if (isReadOnly()) {
/* 161 */       throw new RuntimeException("Can not set UUID : Policy is Readonly");
/*     */     }
/*     */     
/* 164 */     this.UUID = uuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOptional() {
/* 171 */     return this._isOptional;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isOptional(boolean isOptional) {
/* 178 */     if (isReadOnly()) {
/* 179 */       throw new RuntimeException("Can not set Optional Requirement flag : Policy is Readonly");
/*     */     }
/*     */     
/* 182 */     this._isOptional = isOptional;
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
/*     */   
/*     */   public abstract Object clone();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean equals(WSSPolicy paramWSSPolicy);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean equalsIgnoreTargets(WSSPolicy paramWSSPolicy);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isBSP(boolean flag) {
/* 226 */     this.bsp = flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBSP() {
/* 233 */     return this.bsp;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\WSSPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */