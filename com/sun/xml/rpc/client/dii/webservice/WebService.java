/*    */ package com.sun.xml.rpc.client.dii.webservice;
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
/*    */ public class WebService
/*    */ {
/*    */   private String wsdlLocation;
/*    */   private String model;
/*    */   
/*    */   public WebService() {}
/*    */   
/*    */   public WebService(String wsdlLocation, String model) {
/* 37 */     this.wsdlLocation = wsdlLocation;
/* 38 */     this.model = model;
/*    */   }
/*    */   
/*    */   public String getWsdlLocation() {
/* 42 */     return this.wsdlLocation;
/*    */   }
/*    */   
/*    */   public void setWsdlLocation(String wsdlLocation) {
/* 46 */     this.wsdlLocation = wsdlLocation;
/*    */   }
/*    */   
/*    */   public String getModel() {
/* 50 */     return this.model;
/*    */   }
/*    */   
/*    */   public void setModel(String model) {
/* 54 */     this.model = model;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 58 */     return "wsdlLocation = " + this.wsdlLocation + " model = " + this.model + ".";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\webservice\WebService.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */