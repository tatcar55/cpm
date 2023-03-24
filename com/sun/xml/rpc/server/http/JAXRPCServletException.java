/*    */ package com.sun.xml.rpc.server.http;
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
/*    */ public class JAXRPCServletException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public JAXRPCServletException(String key) {
/* 39 */     super(key);
/*    */   }
/*    */   
/*    */   public JAXRPCServletException(String key, String arg) {
/* 43 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public JAXRPCServletException(String key, Object[] args) {
/* 47 */     super(key, args);
/*    */   }
/*    */   
/*    */   public JAXRPCServletException(String key, Localizable arg) {
/* 51 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 55 */     return "com.sun.xml.rpc.resources.jaxrpcservlet";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\JAXRPCServletException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */