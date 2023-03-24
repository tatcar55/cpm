/*    */ package com.sun.xml.rpc.client.dii.webservice;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.ProcessorException;
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
/*    */ public class WebServicesClientException
/*    */   extends ProcessorException
/*    */ {
/*    */   public WebServicesClientException(String key) {
/* 33 */     super(key);
/*    */   }
/*    */   
/*    */   public WebServicesClientException(String key, String arg) {
/* 37 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public WebServicesClientException(String key, Object[] args) {
/* 41 */     super(key, args);
/*    */   }
/*    */   
/*    */   public WebServicesClientException(String key, Localizable arg) {
/* 45 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 49 */     return "com.sun.xml.rpc.resources.client.dii.webservice";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\webservice\WebServicesClientException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */