/*    */ package com.sun.xml.rpc.util;
/*    */ 
/*    */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*    */ import com.sun.xml.rpc.util.localization.Localizable;
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
/*    */ public class HolderException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public HolderException(String key) {
/* 39 */     super(key);
/*    */   }
/*    */   
/*    */   public HolderException(String key, String arg) {
/* 43 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public HolderException(String key, Localizable localizable) {
/* 47 */     super(key, localizable);
/*    */   }
/*    */   
/*    */   public HolderException(String key, Object[] args) {
/* 51 */     super(key, args);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 55 */     return "com.sun.xml.rpc.resources.util";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\HolderException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */