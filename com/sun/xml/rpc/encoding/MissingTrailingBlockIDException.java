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
/*    */ public class MissingTrailingBlockIDException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public MissingTrailingBlockIDException(String key) {
/* 39 */     super(key);
/*    */   }
/*    */   
/*    */   public MissingTrailingBlockIDException(String key, String arg) {
/* 43 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public MissingTrailingBlockIDException(String key, Object[] args) {
/* 47 */     super(key, args);
/*    */   }
/*    */   
/*    */   public MissingTrailingBlockIDException(String key, Localizable arg) {
/* 51 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public MissingTrailingBlockIDException(Localizable arg) {
/* 55 */     super("nestedDeserializationError", arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 59 */     return "com.sun.xml.rpc.resources.encoding";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\MissingTrailingBlockIDException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */