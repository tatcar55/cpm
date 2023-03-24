/*    */ package com.sun.xml.rpc.encoding;
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
/*    */ public class TypeMappingException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public TypeMappingException(String key) {
/* 42 */     super(key);
/*    */   }
/*    */   
/*    */   public TypeMappingException(String key, String arg) {
/* 46 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public TypeMappingException(String key, Object[] args) {
/* 50 */     super(key, args);
/*    */   }
/*    */   
/*    */   public TypeMappingException(String key, Localizable arg) {
/* 54 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public TypeMappingException(Localizable arg) {
/* 58 */     super("typemapping.nested.exception", arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 62 */     return "com.sun.xml.rpc.resources.encoding";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\TypeMappingException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */