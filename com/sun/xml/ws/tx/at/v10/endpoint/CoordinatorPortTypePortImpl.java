/*     */ package com.sun.xml.ws.tx.at.v10.endpoint;
/*     */ 
/*     */ import com.sun.xml.ws.developer.MemberSubmissionAddressing;
/*     */ import com.sun.xml.ws.tx.at.common.WSATVersion;
/*     */ import com.sun.xml.ws.tx.at.common.endpoint.Coordinator;
/*     */ import com.sun.xml.ws.tx.at.v10.types.CoordinatorPortType;
/*     */ import com.sun.xml.ws.tx.at.v10.types.Notification;
/*     */ import javax.annotation.Resource;
/*     */ import javax.jws.WebService;
/*     */ import javax.xml.ws.BindingType;
/*     */ import javax.xml.ws.WebServiceContext;
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
/*     */ @WebService(portName = "CoordinatorPortTypePort", serviceName = "WSAT10Service", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", wsdlLocation = "/wsdls/wsat10/wsat.wsdl", endpointInterface = "com.sun.xml.ws.tx.at.v10.types.CoordinatorPortType")
/*     */ @BindingType("http://schemas.xmlsoap.org/wsdl/soap/http")
/*     */ @MemberSubmissionAddressing
/*     */ public class CoordinatorPortTypePortImpl
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
/*     */   public void abortedOperation(Notification parameters) {
/*  81 */     Coordinator<Notification> proxy = getProxy();
/*  82 */     proxy.abortedOperation(parameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readOnlyOperation(Notification parameters) {
/*  90 */     Coordinator<Notification> proxy = getProxy();
/*  91 */     proxy.readOnlyOperation(parameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void committedOperation(Notification parameters) {
/*  99 */     Coordinator<Notification> proxy = getProxy();
/* 100 */     proxy.committedOperation(parameters);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void replayOperation(Notification parameters) {
/* 108 */     Coordinator<Notification> proxy = getProxy();
/* 109 */     proxy.replayOperation(parameters);
/*     */   }
/*     */   
/*     */   protected Coordinator<Notification> getProxy() {
/* 113 */     Coordinator<Notification> proxy = new Coordinator(this.m_context, WSATVersion.v10);
/* 114 */     return proxy;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v10\endpoint\CoordinatorPortTypePortImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */