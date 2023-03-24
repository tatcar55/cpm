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
/*    */ 
/*    */ public class EncodingException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public EncodingException(String key) {
/* 43 */     super(key);
/*    */   }
/*    */   
/*    */   public EncodingException(String key, String arg) {
/* 47 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public EncodingException(String key, Object[] args) {
/* 51 */     super(key, args);
/*    */   }
/*    */   
/*    */   public EncodingException(String key, Localizable arg) {
/* 55 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public EncodingException(Localizable arg) {
/* 59 */     super("nestedEncodingError", arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 63 */     return "com.sun.xml.rpc.resources.encoding";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\EncodingException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */