/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MandatoryTargetPolicy
/*     */   extends WSSPolicy
/*     */ {
/*     */   public Object clone() {
/*  63 */     MandatoryTargetPolicy mp = new MandatoryTargetPolicy();
/*  64 */     WSSPolicy wp = (WSSPolicy)getFeatureBinding();
/*  65 */     if (wp != null) {
/*  66 */       WSSPolicy nwp = (WSSPolicy)wp.clone();
/*  67 */       mp.setFeatureBinding(nwp);
/*     */     } 
/*  69 */     return mp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(WSSPolicy policy) {
/*  78 */     if (policy.getType() == "MandatoryTargetPolicy") {
/*  79 */       WSSPolicy p1 = (WSSPolicy)policy.getFeatureBinding();
/*  80 */       if (p1 == null || getFeatureBinding() == null) {
/*  81 */         return false;
/*     */       }
/*  83 */       return p1.equals(getFeatureBinding());
/*     */     } 
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equalsIgnoreTargets(WSSPolicy policy) {
/*  94 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getType() {
/* 102 */     return "MandatoryTargetPolicy";
/*     */   }
/*     */   
/*     */   public static class FeatureBinding
/*     */     extends WSSPolicy {
/* 107 */     private List<Target> targets = new ArrayList<Target>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void addTargetBinding(Target target) {
/* 116 */       this.targets.add(target);
/* 117 */       target.setEnforce(true);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public List<Target> getTargetBindings() {
/* 125 */       return this.targets;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object clone() {
/* 133 */       FeatureBinding binding = new FeatureBinding();
/* 134 */       for (Target t : this.targets) {
/* 135 */         binding.addTargetBinding(t);
/*     */       }
/* 137 */       return binding;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(WSSPolicy policy) {
/* 146 */       boolean retVal = false;
/* 147 */       if (policy.getType() == "MandatoryTargetPolicy.FeatureBinding") {
/* 148 */         List<Target> tList = ((FeatureBinding)policy).getTargetBindings();
/* 149 */         for (Target t : tList) {
/* 150 */           if (!this.targets.contains(t)) {
/*     */             break;
/*     */           }
/*     */         } 
/* 154 */         retVal = true;
/*     */       } 
/* 156 */       return retVal;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equalsIgnoreTargets(WSSPolicy policy) {
/* 165 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getType() {
/* 173 */       return "MandatoryTargetPolicy.FeatureBinding";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\MandatoryTargetPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */