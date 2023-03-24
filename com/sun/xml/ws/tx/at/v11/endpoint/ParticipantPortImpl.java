/*    */ package com.sun.xml.ws.tx.at.v11.endpoint;
/*    */ 
/*    */ import com.sun.xml.ws.tx.at.common.WSATVersion;
/*    */ import com.sun.xml.ws.tx.at.common.endpoint.Participant;
/*    */ import com.sun.xml.ws.tx.at.v11.types.Notification;
/*    */ import com.sun.xml.ws.tx.at.v11.types.ParticipantPortType;
/*    */ import javax.annotation.Resource;
/*    */ import javax.jws.WebService;
/*    */ import javax.xml.ws.BindingType;
/*    */ import javax.xml.ws.WebServiceContext;
/*    */ import javax.xml.ws.soap.Addressing;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @WebService(portName = "ParticipantPort", serviceName = "WSAT11Service", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", wsdlLocation = "/wsdls/wsat11/wstx-wsat-1.1-wsdl-200702.wsdl", endpointInterface = "com.sun.xml.ws.tx.at.v11.types.ParticipantPortType")
/*    */ @BindingType("http://schemas.xmlsoap.org/wsdl/soap/http")
/*    */ @Addressing
/*    */ public class ParticipantPortImpl
/*    */   implements ParticipantPortType
/*    */ {
/*    */   @Resource
/*    */   private WebServiceContext m_context;
/*    */   
/*    */   public void prepareOperation(Notification parameters) {
/* 71 */     Participant<Notification> proxy = getPoxy();
/* 72 */     proxy.prepare(parameters);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void commitOperation(Notification parameters) {
/* 80 */     Participant<Notification> proxy = getPoxy();
/* 81 */     proxy.commit(parameters);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void rollbackOperation(Notification parameters) {
/* 89 */     Participant<Notification> proxy = getPoxy();
/* 90 */     proxy.rollback(parameters);
/*    */   }
/*    */   
/*    */   protected Participant<Notification> getPoxy() {
/* 94 */     return new Participant(this.m_context, WSATVersion.v11);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v11\endpoint\ParticipantPortImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */