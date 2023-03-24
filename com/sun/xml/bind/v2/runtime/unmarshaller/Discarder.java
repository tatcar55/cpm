/*    */ package com.sun.xml.bind.v2.runtime.unmarshaller;
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
/*    */ public final class Discarder
/*    */   extends Loader
/*    */ {
/* 54 */   public static final Loader INSTANCE = new Discarder();
/*    */   
/*    */   private Discarder() {
/* 57 */     super(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void childElement(UnmarshallingContext.State state, TagName ea) {
/* 62 */     state.target = null;
/*    */     
/* 64 */     state.loader = this;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\Discarder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */