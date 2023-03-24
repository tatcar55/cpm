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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignaturePolicy
/*     */   extends WSSKeyBindingExtension
/*     */ {
/*     */   public SignaturePolicy() {
/*  89 */     setPolicyIdentifier("SignaturePolicy");
/*  90 */     this._featureBinding = new FeatureBinding();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object clone() {
/*  98 */     SignaturePolicy policy = new SignaturePolicy();
/*     */     
/*     */     try {
/* 101 */       WSSPolicy fBinding = (WSSPolicy)getFeatureBinding();
/* 102 */       WSSPolicy kBinding = (WSSPolicy)getKeyBinding();
/*     */       
/* 104 */       if (fBinding != null) {
/* 105 */         policy.setFeatureBinding((MLSPolicy)fBinding.clone());
/*     */       }
/* 107 */       if (kBinding != null)
/* 108 */         policy.setKeyBinding((MLSPolicy)kBinding.clone()); 
/* 109 */     } catch (Exception e) {}
/*     */     
/* 111 */     return policy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(WSSPolicy policy) {
/* 120 */     boolean _assert = false;
/*     */     
/*     */     try {
/* 123 */       return equalsIgnoreTargets(policy);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 130 */     catch (Exception cce) {
/*     */       
/* 132 */       return _assert;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equalsIgnoreTargets(WSSPolicy policy) {
/* 141 */     boolean _assert = false;
/*     */     
/*     */     try {
/* 144 */       if (!PolicyTypeUtil.signaturePolicy((SecurityPolicy)policy)) return false; 
/* 145 */       SignaturePolicy sPolicy = (SignaturePolicy)policy;
/* 146 */       _assert = ((WSSPolicy)getFeatureBinding()).equalsIgnoreTargets((WSSPolicy)sPolicy.getFeatureBinding());
/*     */ 
/*     */     
/*     */     }
/* 150 */     catch (Exception cce) {}
/*     */     
/* 152 */     return _assert;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 159 */     return "SignaturePolicy";
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
/*     */ 
/*     */   
/*     */   public static class FeatureBinding
/*     */     extends WSSPolicy
/*     */   {
/* 185 */     String _canonicalizationAlgorithm = "";
/*     */     
/* 187 */     private SignatureTarget timestamp = null;
/*     */     
/* 189 */     final ArrayList _targets = new ArrayList();
/*     */ 
/*     */     
/*     */     private boolean isEndorsingSignature = false;
/*     */ 
/*     */     
/*     */     private boolean isPrimarySignature = false;
/*     */     
/*     */     private boolean disableInclusivePrefix = false;
/*     */ 
/*     */     
/*     */     public FeatureBinding() {
/* 201 */       setPolicyIdentifier("SignaturePolicy.FeatureBinding");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FeatureBinding(String canonicalization) {
/* 209 */       this();
/*     */       
/* 211 */       this._canonicalizationAlgorithm = canonicalization;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getCanonicalizationAlgorithm() {
/* 218 */       return this._canonicalizationAlgorithm;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setCanonicalizationAlgorithm(String canonicalization) {
/* 226 */       if (isReadOnly()) {
/* 227 */         throw new RuntimeException("Can not set CanonicalizationAlgorithm : Policy is ReadOnly");
/*     */       }
/*     */       
/* 230 */       if (isBSP() && canonicalization != "http://www.w3.org/2001/10/xml-exc-c14n#")
/*     */       {
/* 232 */         throw new RuntimeException("Does not meet BSP requirement: 5404. C14n algorithm must be exc-c14n");
/*     */       }
/* 234 */       this._canonicalizationAlgorithm = canonicalization;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean includeTimestamp() {
/* 241 */       return (this.timestamp != null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void includeTimestamp(boolean include) {
/* 250 */       if (isReadOnly()) {
/* 251 */         throw new RuntimeException("Can not set includeTimestamp Flag : Policy is ReadOnly");
/*     */       }
/*     */       
/* 254 */       if (include) {
/* 255 */         if (this.timestamp == null) {
/* 256 */           this.timestamp = new SignatureTarget();
/* 257 */           this.timestamp.setType("qname");
/* 258 */           this.timestamp.setValue("{http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd}Timestamp");
/* 259 */           this.timestamp.setDigestAlgorithm("http://www.w3.org/2000/09/xmldsig#sha1");
/* 260 */           this._targets.add(0, this.timestamp);
/*     */         }
/*     */       
/* 263 */       } else if (this.timestamp != null) {
/* 264 */         int idx = this._targets.indexOf(this.timestamp);
/* 265 */         this._targets.remove(idx);
/* 266 */         this.timestamp = null;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void isEndorsingSignature(boolean isEndorsingSignature) {
/* 272 */       this.isEndorsingSignature = isEndorsingSignature;
/*     */     }
/*     */     
/*     */     public boolean isEndorsingSignature() {
/* 276 */       return this.isEndorsingSignature;
/*     */     }
/*     */     
/*     */     public void isPrimarySignature(boolean isPrimarySignature) {
/* 280 */       this.isPrimarySignature = isPrimarySignature;
/*     */     }
/*     */     
/*     */     public boolean isPrimarySignature() {
/* 284 */       return this.isPrimarySignature;
/*     */     }
/*     */     
/*     */     public boolean getDisableInclusivePrefix() {
/* 288 */       return this.disableInclusivePrefix;
/*     */     }
/*     */     
/*     */     public void setDisbaleInclusivePrefix(boolean disableInclusivePrefix) {
/* 292 */       this.disableInclusivePrefix = disableInclusivePrefix;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ArrayList getTargetBindings() {
/* 299 */       return this._targets;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addTargetBinding(SignatureTarget target) {
/* 309 */       if (isReadOnly()) {
/* 310 */         throw new RuntimeException("Can not add Target : Policy is ReadOnly");
/*     */       }
/* 312 */       this._targets.add(target);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addTargetBinding(Target target) {
/* 320 */       addTargetBinding(new SignatureTarget(target));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void removeTargetBindings(ArrayList<?> targets) {
/* 333 */       if (isReadOnly()) {
/* 334 */         throw new RuntimeException("Can not remove Target : Policy is ReadOnly");
/*     */       }
/*     */       
/* 337 */       this._targets.removeAll(targets);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(WSSPolicy binding) {
/*     */       try {
/* 348 */         if (!PolicyTypeUtil.signaturePolicyFeatureBinding((SecurityPolicy)binding))
/* 349 */           return false; 
/* 350 */         FeatureBinding policy = (FeatureBinding)binding;
/*     */         
/* 352 */         boolean b1 = this._canonicalizationAlgorithm.equals("") ? true : this._canonicalizationAlgorithm.equals(policy.getCanonicalizationAlgorithm());
/*     */         
/* 354 */         if (!b1) return false;
/*     */         
/* 356 */         boolean b2 = this._targets.equals(policy.getTargetBindings());
/* 357 */         if (!b2) return false;
/*     */       
/* 359 */       } catch (Exception e) {}
/*     */       
/* 361 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equalsIgnoreTargets(WSSPolicy binding) {
/* 371 */       boolean assrt = false;
/*     */       
/* 373 */       if (!PolicyTypeUtil.signaturePolicyFeatureBinding((SecurityPolicy)binding)) {
/* 374 */         return false;
/*     */       }
/*     */       
/*     */       try {
/* 378 */         FeatureBinding policy = (FeatureBinding)binding;
/* 379 */         assrt = this._canonicalizationAlgorithm.equals(policy.getCanonicalizationAlgorithm());
/* 380 */       } catch (Exception e) {}
/*     */       
/* 382 */       return assrt;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 389 */       FeatureBinding binding = new FeatureBinding();
/*     */       
/*     */       try {
/* 392 */         WSSPolicy kBinding = (WSSPolicy)getKeyBinding();
/* 393 */         WSSPolicy fBinding = (WSSPolicy)getFeatureBinding();
/*     */         
/* 395 */         if (fBinding != null) {
/* 396 */           binding.setFeatureBinding((MLSPolicy)fBinding.clone());
/*     */         }
/* 398 */         if (kBinding != null) {
/* 399 */           binding.setKeyBinding((MLSPolicy)kBinding.clone());
/*     */         }
/* 401 */         binding.setCanonicalizationAlgorithm(getCanonicalizationAlgorithm());
/*     */         
/* 403 */         Iterator<SignatureTarget> i = getTargetBindings().iterator();
/* 404 */         while (i.hasNext()) {
/* 405 */           SignatureTarget target = i.next();
/* 406 */           binding.addTargetBinding((SignatureTarget)target.clone());
/*     */         } 
/* 408 */       } catch (Exception e) {}
/*     */       
/* 410 */       return binding;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getType() {
/* 417 */       return "SignaturePolicy.FeatureBinding";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\SignaturePolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */