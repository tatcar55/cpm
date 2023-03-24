/*    */ package com.sun.xml.rpc.client.dii;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DynamicInvocationException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public DynamicInvocationException(String key) {
/* 43 */     super(key);
/*    */   }
/*    */   
/*    */   public DynamicInvocationException(String key, String arg) {
/* 47 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public DynamicInvocationException(String key, Object[] args) {
/* 51 */     super(key, args);
/*    */   }
/*    */   
/*    */   public DynamicInvocationException(String key, Localizable arg) {
/* 55 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public DynamicInvocationException(Localizable arg) {
/* 59 */     super("dii.exception.nested", arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 63 */     return "com.sun.xml.rpc.resources.dii";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\DynamicInvocationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */