/*    */ package com.sun.xml.rpc.wsdl.framework;
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
/*    */ 
/*    */ public class ValidationException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public ValidationException(String key) {
/* 40 */     super(key);
/*    */   }
/*    */   
/*    */   public ValidationException(String key, String arg) {
/* 44 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public ValidationException(String key, Localizable localizable) {
/* 48 */     super(key, localizable);
/*    */   }
/*    */   
/*    */   public ValidationException(String key, Object[] args) {
/* 52 */     super(key, args);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 56 */     return "com.sun.xml.rpc.resources.wsdl";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\ValidationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */