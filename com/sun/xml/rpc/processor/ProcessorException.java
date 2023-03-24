/*    */ package com.sun.xml.rpc.processor;
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
/*    */ public class ProcessorException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public ProcessorException(String key) {
/* 43 */     super(key);
/*    */   }
/*    */   
/*    */   public ProcessorException(String key, String arg) {
/* 47 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public ProcessorException(String key, Object[] args) {
/* 51 */     super(key, args);
/*    */   }
/*    */   
/*    */   public ProcessorException(String key, Localizable arg) {
/* 55 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 59 */     return "com.sun.xml.rpc.resources.processor";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\ProcessorException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */