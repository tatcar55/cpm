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
/*    */ public class ParseException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public ParseException(String key) {
/* 40 */     super(key);
/*    */   }
/*    */   
/*    */   public ParseException(String key, String arg) {
/* 44 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public ParseException(String key, Localizable localizable) {
/* 48 */     super(key, localizable);
/*    */   }
/*    */   
/*    */   public ParseException(String key, String arg, Localizable localizable) {
/* 52 */     this(key, new Object[] { arg, localizable });
/*    */   }
/*    */   
/*    */   public ParseException(String key, Object[] args) {
/* 56 */     super(key, args);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 60 */     return "com.sun.xml.rpc.resources.wsdl";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\wsdl\framework\ParseException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */