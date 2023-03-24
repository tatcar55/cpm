/*    */ package com.sun.xml.rpc.processor.modeler;
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
/*    */ public class ModelerException
/*    */   extends ProcessorException
/*    */ {
/*    */   public ModelerException(String key) {
/* 43 */     super(key);
/*    */   }
/*    */   
/*    */   public ModelerException(String key, String arg) {
/* 47 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public ModelerException(String key, Object[] args) {
/* 51 */     super(key, args);
/*    */   }
/*    */   
/*    */   public ModelerException(String key, Localizable arg) {
/* 55 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public ModelerException(Localizable arg) {
/* 59 */     super("modeler.nestedModelError", arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 63 */     return "com.sun.xml.rpc.resources.modeler";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\ModelerException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */