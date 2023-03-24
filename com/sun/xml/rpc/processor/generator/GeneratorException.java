/*    */ package com.sun.xml.rpc.processor.generator;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.ProcessorException;
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
/*    */ public class GeneratorException
/*    */   extends ProcessorException
/*    */ {
/*    */   public GeneratorException(String key) {
/* 39 */     super(key);
/*    */   }
/*    */   
/*    */   public GeneratorException(String key, String arg) {
/* 43 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public GeneratorException(String key, Object[] args) {
/* 47 */     super(key, args);
/*    */   }
/*    */   
/*    */   public GeneratorException(String key, Localizable arg) {
/* 51 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 55 */     return "com.sun.xml.rpc.resources.generator";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\GeneratorException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */