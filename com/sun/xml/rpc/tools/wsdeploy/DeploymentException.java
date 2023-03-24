/*    */ package com.sun.xml.rpc.tools.wsdeploy;
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
/*    */ public class DeploymentException
/*    */   extends JAXRPCExceptionBase
/*    */ {
/*    */   public DeploymentException(String key) {
/* 43 */     super(key);
/*    */   }
/*    */   
/*    */   public DeploymentException(String key, String arg) {
/* 47 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public DeploymentException(String key, Object[] args) {
/* 51 */     super(key, args);
/*    */   }
/*    */   
/*    */   public DeploymentException(String key, Localizable arg) {
/* 55 */     super(key, arg);
/*    */   }
/*    */   
/*    */   public String getResourceBundleName() {
/* 59 */     return "com.sun.xml.rpc.resources.deployment";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\wsdeploy\DeploymentException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */