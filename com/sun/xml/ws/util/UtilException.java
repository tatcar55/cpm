/*    */ package com.sun.xml.ws.util;
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
/*    */ public class UtilException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public UtilException(String key, Object... args) {
/* 56 */     super(key, args);
/*    */   }
/*    */   
/*    */   public UtilException(Throwable throwable) {
/* 60 */     super(throwable);
/*    */   }
/*    */   
/*    */   public UtilException(Localizable arg) {
/* 64 */     super("nestedUtilError", new Object[] { arg });
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 68 */     return "com.sun.xml.ws.resources.util";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\UtilException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */