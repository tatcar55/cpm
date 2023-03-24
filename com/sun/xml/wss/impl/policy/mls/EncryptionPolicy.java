/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.policy.MLSPolicy;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EncryptionPolicy
/*     */   extends WSSKeyBindingExtension
/*     */ {
/*     */   public EncryptionPolicy() {
/*  85 */     setPolicyIdentifier("EncryptionPolicy");
/*  86 */     this._featureBinding = new FeatureBinding();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(WSSPolicy policy) {
/*  95 */     boolean _assert = false;
/*     */     
/*     */     try {
/*  98 */       return equalsIgnoreTargets(policy);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 105 */     catch (Exception cce) {
/*     */       
/* 107 */       return _assert;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equalsIgnoreTargets(WSSPolicy policy) {
/* 116 */     boolean _assert = false;
/*     */     
/*     */     try {
/* 119 */       if (PolicyTypeUtil.encryptionPolicy((SecurityPolicy)policy)) {
/* 120 */         return true;
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 125 */     catch (Exception cce) {}
/*     */     
/* 127 */     return _assert;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/* 135 */     EncryptionPolicy ePolicy = new EncryptionPolicy();
/*     */     
/*     */     try {
/* 138 */       WSSPolicy fBinding = (WSSPolicy)getFeatureBinding();
/* 139 */       WSSPolicy kBinding = (WSSPolicy)getKeyBinding();
/*     */       
/* 141 */       if (fBinding != null) {
/* 142 */         ePolicy.setFeatureBinding((MLSPolicy)fBinding.clone());
/*     */       }
/* 144 */       if (kBinding != null)
/* 145 */         ePolicy.setKeyBinding((MLSPolicy)kBinding.clone()); 
/* 146 */     } catch (Exception e) {}
/*     */     
/* 148 */     return ePolicy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 155 */     return "EncryptionPolicy";
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class FeatureBinding
/*     */     extends WSSPolicy
/*     */   {
/* 179 */     String _dataEncryptionAlgorithm = "";
/* 180 */     ArrayList _targets = new ArrayList();
/*     */     
/*     */     boolean standAloneRefList = false;
/*     */     
/*     */     boolean targetIsIssuedToken = false;
/*     */     
/*     */     boolean targetIsSignature = false;
/*     */     
/*     */     public FeatureBinding() {
/* 189 */       setPolicyIdentifier("EncryptionPolicy.FeatureBinding");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getDataEncryptionAlgorithm() {
/* 196 */       return this._dataEncryptionAlgorithm;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setDataEncryptionAlgorithm(String algorithm) {
/* 204 */       if (isReadOnly()) {
/* 205 */         throw new RuntimeException("Can not set DateEncryptionAlgorithm : Policy is ReadOnly");
/*     */       }
/* 207 */       this._dataEncryptionAlgorithm = algorithm;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ArrayList getTargetBindings() {
/* 214 */       return this._targets;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addTargetBinding(EncryptionTarget target) {
/* 222 */       if (isReadOnly()) {
/* 223 */         throw new RuntimeException("Can not add Target : Policy is ReadOnly");
/*     */       }
/* 225 */       this._targets.add(target);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addTargetBinding(Target target) {
/* 233 */       if (isReadOnly()) {
/* 234 */         throw new RuntimeException("Can not add Target : Policy is ReadOnly");
/*     */       }
/* 236 */       this._targets.add(new EncryptionTarget(target));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void removeTargetBindings(ArrayList<?> targets) {
/* 244 */       if (isReadOnly()) {
/* 245 */         throw new RuntimeException("Can not remove Target : Policy is ReadOnly");
/*     */       }
/* 247 */       this._targets.removeAll(targets);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(WSSPolicy policy) {
/*     */       try {
/* 257 */         FeatureBinding fBinding = (FeatureBinding)policy;
/* 258 */         boolean b1 = this._targets.equals(fBinding.getTargetBindings());
/* 259 */         if (!b1) return false; 
/* 260 */       } catch (Exception e) {}
/*     */       
/* 262 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equalsIgnoreTargets(WSSPolicy policy) {
/* 271 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 280 */       FeatureBinding fBinding = new FeatureBinding();
/*     */       
/*     */       try {
/* 283 */         ArrayList<Object> list = new ArrayList();
/*     */         
/* 285 */         Iterator<EncryptionTarget> i = getTargetBindings().iterator();
/* 286 */         for (; i.hasNext(); list.add(((EncryptionTarget)i.next()).clone()));
/*     */         
/* 288 */         fBinding.getTargetBindings().addAll(list);
/*     */         
/* 290 */         WSSPolicy kBinding = (WSSPolicy)getKeyBinding();
/* 291 */         fBinding.setDataEncryptionAlgorithm(getDataEncryptionAlgorithm());
/* 292 */         if (kBinding != null)
/* 293 */           fBinding.setKeyBinding((MLSPolicy)kBinding.clone()); 
/* 294 */       } catch (Exception e) {}
/*     */       
/* 296 */       fBinding.encryptsIssuedToken(encryptsIssuedToken());
/* 297 */       fBinding.encryptsSignature(encryptsSignature());
/* 298 */       return fBinding;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getType() {
/* 305 */       return "EncryptionPolicy.FeatureBinding";
/*     */     }
/*     */     
/*     */     public boolean encryptsIssuedToken() {
/* 309 */       return this.targetIsIssuedToken;
/*     */     }
/*     */     
/*     */     public void encryptsIssuedToken(boolean flag) {
/* 313 */       this.targetIsIssuedToken = flag;
/*     */     }
/*     */     
/*     */     public boolean encryptsSignature() {
/* 317 */       return this.targetIsSignature;
/*     */     }
/*     */     public void encryptsSignature(boolean flag) {
/* 320 */       this.targetIsSignature = flag;
/*     */     }
/*     */     public boolean getUseStandAloneRefList() {
/* 323 */       return this.standAloneRefList;
/*     */     }
/*     */     
/*     */     public void setUseStandAloneRefList(boolean value) {
/* 327 */       this.standAloneRefList = value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\EncryptionPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */