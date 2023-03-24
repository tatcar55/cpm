/*    */ package com.ctc.wstx.dtd;
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
/*    */ public abstract class ContentSpec
/*    */ {
/*    */   protected char mArity;
/*    */   
/*    */   public ContentSpec(char arity) {
/* 36 */     this.mArity = arity;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final char getArity() {
/* 45 */     return this.mArity;
/*    */   } public final void setArity(char c) {
/* 47 */     this.mArity = c;
/*    */   } public boolean isLeaf() {
/* 49 */     return false;
/*    */   }
/*    */   
/*    */   public abstract StructValidator getSimpleValidator();
/*    */   
/*    */   public abstract ModelNode rewrite();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\ctc\wstx\dtd\ContentSpec.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */