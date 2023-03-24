/*    */ package com.sun.xml.ws.model.wsdl;
/*    */ 
/*    */ import com.sun.xml.ws.api.model.SEIModel;
/*    */ import javax.xml.namespace.QName;
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
/*    */ 
/*    */ 
/*    */ public final class WSDLDirectProperties
/*    */   extends WSDLProperties
/*    */ {
/*    */   private final QName serviceName;
/*    */   private final QName portName;
/*    */   
/*    */   public WSDLDirectProperties(QName serviceName, QName portName) {
/* 57 */     this(serviceName, portName, null);
/*    */   }
/*    */   
/*    */   public WSDLDirectProperties(QName serviceName, QName portName, SEIModel seiModel) {
/* 61 */     super(seiModel);
/* 62 */     this.serviceName = serviceName;
/* 63 */     this.portName = portName;
/*    */   }
/*    */   
/*    */   public QName getWSDLService() {
/* 67 */     return this.serviceName;
/*    */   }
/*    */   
/*    */   public QName getWSDLPort() {
/* 71 */     return this.portName;
/*    */   }
/*    */   
/*    */   public QName getWSDLPortType() {
/* 75 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\wsdl\WSDLDirectProperties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */