/*    */ package com.sun.xml.rpc.soap.streaming;
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
/*    */ public class SOAPProtocolViolationException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public SOAPProtocolViolationException(String key) {
/* 37 */     super(key);
/*    */   }
/*    */   
/*    */   public SOAPProtocolViolationException(String key, String argument) {
/* 41 */     super(key, argument);
/*    */   }
/*    */   
/*    */   public SOAPProtocolViolationException(String key, Object[] arguments) {
/* 45 */     super(key, arguments);
/*    */   }
/*    */   
/*    */   public SOAPProtocolViolationException(String key, Localizable argument) {
/* 49 */     super(key, argument);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 53 */     return "com.sun.xml.rpc.resources.soap";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\soap\streaming\SOAPProtocolViolationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */