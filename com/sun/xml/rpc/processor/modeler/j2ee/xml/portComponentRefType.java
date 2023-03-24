/*    */ package com.sun.xml.rpc.processor.modeler.j2ee.xml;
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
/*    */ public class portComponentRefType
/*    */   extends ComplexType
/*    */ {
/*    */   public void setServiceEndpointInterface(String serviceEndpointInterface) {
/* 43 */     setElementValue("service-endpoint-interface", serviceEndpointInterface);
/*    */   }
/*    */   
/*    */   public String getServiceEndpointInterface() {
/* 47 */     return getElementValue("service-endpoint-interface");
/*    */   }
/*    */   
/*    */   public boolean removeServiceEndpointInterface() {
/* 51 */     return removeElement("service-endpoint-interface");
/*    */   }
/*    */   
/*    */   public void setPortComponentLink(String portComponentLink) {
/* 55 */     setElementValue("port-component-link", portComponentLink);
/*    */   }
/*    */   
/*    */   public String getPortComponentLink() {
/* 59 */     return getElementValue("port-component-link");
/*    */   }
/*    */   
/*    */   public boolean removePortComponentLink() {
/* 63 */     return removeElement("port-component-link");
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 67 */     setAttributeValue("id", id);
/*    */   }
/*    */   
/*    */   public String getId() {
/* 71 */     return getAttributeValue("id");
/*    */   }
/*    */   
/*    */   public boolean removeId() {
/* 75 */     return removeAttribute("id");
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\j2ee\xml\portComponentRefType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */