/*    */ package com.sun.xml.rpc.client;
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
/*    */ public class HandlerException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public HandlerException(String key) {
/* 38 */     super(key);
/*    */   }
/*    */   
/*    */   public HandlerException(String key, String arg) {
/* 42 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public HandlerException(String key, Object[] args) {
/* 46 */     super(key, args);
/*    */   }
/*    */   
/*    */   public HandlerException(String key, Localizable arg) {
/* 50 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public HandlerException(Localizable arg) {
/* 54 */     super("handler.nestedError", arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 58 */     return "com.sun.xml.rpc.resources.handler";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\HandlerException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */