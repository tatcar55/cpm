/*     */ package com.sun.xml.ws.tx.at.v11.endpoint;
/*     */ 
/*     */ import com.sun.xml.ws.tx.at.common.WSATVersion;
/*     */ import com.sun.xml.ws.tx.at.common.endpoint.Coordinator;
/*     */ import com.sun.xml.ws.tx.at.v11.types.CoordinatorPortType;
/*     */ import com.sun.xml.ws.tx.at.v11.types.Notification;
/*     */ import javax.annotation.Resource;
/*     */ import javax.jws.WebService;
/*     */ import javax.xml.ws.BindingType;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import javax.xml.ws.soap.Addressing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @WebService(portName = "CoordinatorPort", serviceName = "WSAT11Service", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", wsdlLocation = "/wsdls/wsat11/wstx-wsat-1.1-wsdl-200702.wsdl", endpointInterface = "com.sun.xml.ws.tx.at.v11.types.CoordinatorPortType")
/*     */ @BindingType("http://schemas.xmlsoap.org/wsdl/soap/http")
/*     */ @Addressing
/*     */ public class CoordinatorPortImpl
/*     */   implements CoordinatorPortType
/*     */ {
/*     */   @Resource
/*     */   private WebServiceContext m_context;
/*     */   
/*     */   public void preparedOperation(Notification parameters) {
/*  72 */     Coordinator<Notification> proxy = getProxy();
/*  73 */     proxy.preparedOperation(parameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void abortedOperation(Notification parameters) {
/*  82 */     Coordinator<Notification> proxy = getProxy();
/*  83 */     proxy.abortedOperation(parameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readOnlyOperation(Notification parameters) {
/*  91 */     Coordinator<Notification> proxy = getProxy();
/*  92 */     proxy.readOnlyOperation(parameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void committedOperation(Notification parameters) {
/* 100 */     Coordinator<Notification> proxy = getProxy();
/* 101 */     proxy.committedOperation(parameters);
/*     */   }
/*     */   
/*     */   protected Coordinator<Notification> getProxy() {
/* 105 */     return new Coordinator(this.m_context, WSATVersion.v11);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v11\endpoint\CoordinatorPortImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */