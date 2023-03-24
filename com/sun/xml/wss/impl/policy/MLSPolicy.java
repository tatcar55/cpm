/*    */ package com.sun.xml.wss.impl.policy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class MLSPolicy
/*    */   implements SecurityPolicy
/*    */ {
/*    */   protected boolean readonly = false;
/*    */   
/*    */   public abstract MLSPolicy getFeatureBinding() throws PolicyGenerationException;
/*    */   
/*    */   public abstract MLSPolicy getKeyBinding() throws PolicyGenerationException;
/*    */   
/*    */   public void isReadOnly(boolean readonly) {
/* 79 */     this.readonly = readonly;
/*    */     try {
/* 81 */       MLSPolicy featureBinding = getFeatureBinding();
/* 82 */       if (featureBinding != null) {
/* 83 */         featureBinding.isReadOnly(readonly);
/*    */       }
/*    */       
/* 86 */       MLSPolicy keybinding = getKeyBinding();
/* 87 */       if (keybinding != null) {
/* 88 */         keybinding.isReadOnly(readonly);
/*    */       }
/* 90 */     } catch (PolicyGenerationException e) {}
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isReadOnly() {
/* 99 */     return this.readonly;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\policy\MLSPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */