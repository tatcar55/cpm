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
/*    */ public class SenderException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public SenderException(String key) {
/* 38 */     super(key);
/*    */   }
/*    */   
/*    */   public SenderException(String key, String arg) {
/* 42 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public SenderException(String key, Object[] args) {
/* 46 */     super(key, args);
/*    */   }
/*    */   
/*    */   public SenderException(String key, Localizable arg) {
/* 50 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public SenderException(Localizable arg) {
/* 54 */     super("sender.nestedError", arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 58 */     return "com.sun.xml.rpc.resources.sender";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\SenderException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */