/*    */ package com.sun.xml.rpc.server.http.ea;
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
/*    */ public class WSDLPortInfo
/*    */ {
/*    */   private String targetNamespace;
/*    */   private String serviceName;
/*    */   private String portName;
/*    */   
/*    */   public WSDLPortInfo(String targetNamespace, String serviceName, String portName) {
/* 40 */     this.targetNamespace = targetNamespace;
/* 41 */     this.serviceName = serviceName;
/* 42 */     this.portName = portName;
/*    */   }
/*    */   
/*    */   public String getTargetNamespace() {
/* 46 */     return this.targetNamespace;
/*    */   }
/*    */   
/*    */   public String getServiceName() {
/* 50 */     return this.serviceName;
/*    */   }
/*    */   
/*    */   public String getPortName() {
/* 54 */     return this.portName;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\http\ea\WSDLPortInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */