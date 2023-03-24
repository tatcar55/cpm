/*    */ package com.sun.xml.rpc.processor.model;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelException
/*    */   extends ProcessorException
/*    */ {
/*    */   public ModelException(String key) {
/* 43 */     super(key);
/*    */   }
/*    */   
/*    */   public ModelException(String key, String arg) {
/* 47 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public ModelException(String key, Object[] args) {
/* 51 */     super(key, args);
/*    */   }
/*    */   
/*    */   public ModelException(String key, Localizable arg) {
/* 55 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public ModelException(Localizable arg) {
/* 59 */     super("model.nestedModelError", arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 63 */     return "com.sun.xml.rpc.resources.model";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\model\ModelException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */