/*    */ package com.sun.istack.localization;
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
/*    */ public final class NullLocalizable
/*    */   implements Localizable
/*    */ {
/*    */   private final String msg;
/*    */   
/*    */   public NullLocalizable(String msg) {
/* 52 */     if (msg == null)
/* 53 */       throw new IllegalArgumentException(); 
/* 54 */     this.msg = msg;
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 58 */     return "\000";
/*    */   }
/*    */   public Object[] getArguments() {
/* 61 */     return new Object[] { this.msg };
/*    */   }
/*    */   public String getResourceBundleName() {
/* 64 */     return "";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\istack\localization\NullLocalizable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */