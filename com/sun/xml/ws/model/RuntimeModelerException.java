/*    */ package com.sun.xml.ws.model;
/*    */ 
/*    */ import com.sun.istack.localization.Localizable;
/*    */ import com.sun.xml.ws.util.exception.JAXWSExceptionBase;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RuntimeModelerException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public RuntimeModelerException(String key, Object... args) {
/* 56 */     super(key, args);
/*    */   }
/*    */   
/*    */   public RuntimeModelerException(Throwable throwable) {
/* 60 */     super(throwable);
/*    */   }
/*    */   
/*    */   public RuntimeModelerException(Localizable arg) {
/* 64 */     super("nestedModelerError", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 68 */     return "com.sun.xml.ws.resources.modeler";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\RuntimeModelerException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */