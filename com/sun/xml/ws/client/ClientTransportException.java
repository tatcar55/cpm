/*    */ package com.sun.xml.ws.client;
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
/*    */ public class ClientTransportException
/*    */   extends JAXWSExceptionBase
/*    */ {
/*    */   public ClientTransportException(Localizable msg) {
/* 52 */     super(msg);
/*    */   }
/*    */   
/*    */   public ClientTransportException(Localizable msg, Throwable cause) {
/* 56 */     super(msg, cause);
/*    */   }
/*    */   
/*    */   public ClientTransportException(Throwable throwable) {
/* 60 */     super(throwable);
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 64 */     return "com.sun.xml.ws.resources.client";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\ClientTransportException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */