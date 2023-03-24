/*    */ package com.sun.xml.bind.v2.model.core;
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
/*    */ public enum WildcardMode
/*    */ {
/* 49 */   STRICT(false, true), SKIP(true, false), LAX(true, true);
/*    */   
/*    */   public final boolean allowTypedObject;
/*    */   public final boolean allowDom;
/*    */   
/*    */   WildcardMode(boolean allowDom, boolean allowTypedObject) {
/* 55 */     this.allowDom = allowDom;
/* 56 */     this.allowTypedObject = allowTypedObject;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\core\WildcardMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */