/*    */ package com.sun.xml.rpc.tools.wsdeploy;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ public class WebServicesInfo
/*    */ {
/*    */   private String targetNamespaceBase;
/*    */   private String typeNamespaceBase;
/*    */   private String urlPatternBase;
/* 39 */   private Map endpoints = new HashMap<Object, Object>();
/* 40 */   private Map endpointMappings = new HashMap<Object, Object>();
/* 41 */   private Map endpointClients = new HashMap<Object, Object>();
/*    */ 
/*    */   
/*    */   public String getTargetNamespaceBase() {
/* 45 */     return this.targetNamespaceBase;
/*    */   }
/*    */   
/*    */   public void setTargetNamespaceBase(String s) {
/* 49 */     this.targetNamespaceBase = s;
/*    */   }
/*    */   
/*    */   public String getTypeNamespaceBase() {
/* 53 */     return this.typeNamespaceBase;
/*    */   }
/*    */   
/*    */   public void setTypeNamespaceBase(String s) {
/* 57 */     this.typeNamespaceBase = s;
/*    */   }
/*    */   
/*    */   public String getUrlPatternBase() {
/* 61 */     return this.urlPatternBase;
/*    */   }
/*    */   
/*    */   public void setUrlPatternBase(String s) {
/* 65 */     this.urlPatternBase = s;
/*    */   }
/*    */   
/*    */   public void add(EndpointClientInfo i) {
/* 69 */     this.endpointClients.put(i.getName(), i);
/*    */   }
/*    */   
/*    */   public void add(EndpointInfo i) {
/* 73 */     this.endpoints.put(i.getName(), i);
/*    */   }
/*    */   
/*    */   public Map getEndpoints() {
/* 77 */     return this.endpoints;
/*    */   }
/*    */   
/*    */   public void add(EndpointMappingInfo i) {
/* 81 */     this.endpointMappings.put(i.getName(), i);
/*    */   }
/*    */   
/*    */   public Map getEndpointMappings() {
/* 85 */     return this.endpointMappings;
/*    */   }
/*    */   
/*    */   public Map getEndpointClients() {
/* 89 */     return this.endpointClients;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\wsdeploy\WebServicesInfo.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */