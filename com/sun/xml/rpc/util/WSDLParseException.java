/*    */ package com.sun.xml.rpc.util;
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
/*    */ public class WSDLParseException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public WSDLParseException(String key) {
/* 38 */     super(key);
/*    */   }
/*    */   
/*    */   public WSDLParseException(String key, String arg) {
/* 42 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public WSDLParseException(String key, Localizable localizable) {
/* 46 */     super(key, localizable);
/*    */   }
/*    */   
/*    */   public WSDLParseException(String key, Object[] args) {
/* 50 */     super(key, args);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 54 */     return "com.sun.xml.rpc.resources.util";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\WSDLParseException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */