/*     */ package com.sun.xml.wss.impl.policy.mls;
/*     */ 
/*     */ import com.sun.xml.wss.impl.PolicyTypeUtil;
/*     */ import com.sun.xml.wss.impl.policy.SecurityPolicy;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DerivedTokenKeyBinding
/*     */   extends KeyBindingBase
/*     */ {
/*  60 */   private WSSPolicy originalKeyBinding = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public DerivedTokenKeyBinding() {
/*  65 */     setPolicyIdentifier("DerivedTokenKeyBinding");
/*     */   }
/*     */   
/*     */   public Object clone() {
/*  69 */     DerivedTokenKeyBinding dkt = new DerivedTokenKeyBinding();
/*  70 */     dkt.setOriginalKeyBinding((WSSPolicy)getOriginalKeyBinding().clone());
/*  71 */     dkt.setUUID(getUUID());
/*  72 */     return dkt;
/*     */   }
/*     */   
/*     */   public boolean equals(WSSPolicy policy) {
/*  76 */     if (!PolicyTypeUtil.derivedTokenKeyBinding((SecurityPolicy)policy)) {
/*  77 */       return false;
/*     */     }
/*     */     
/*  80 */     WSSPolicy dkt = ((DerivedTokenKeyBinding)policy).getOriginalKeyBinding();
/*  81 */     if (dkt.getType().intern() != getOriginalKeyBinding().getType().intern()) {
/*  82 */       return false;
/*     */     }
/*  84 */     return true;
/*     */   }
/*     */   
/*     */   public boolean equalsIgnoreTargets(WSSPolicy policy) {
/*  88 */     return equals(policy);
/*     */   }
/*     */   
/*     */   public String getType() {
/*  92 */     return "DerivedTokenKeyBinding";
/*     */   }
/*     */   
/*     */   public WSSPolicy getOriginalKeyBinding() {
/*  96 */     return this.originalKeyBinding;
/*     */   }
/*     */   
/*     */   public void setOriginalKeyBinding(WSSPolicy originalKeyBinding) {
/* 100 */     this.originalKeyBinding = originalKeyBinding;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\mls\DerivedTokenKeyBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */