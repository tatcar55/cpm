/*    */ package com.sun.xml.ws.tx.at.v10.endpoint;
/*    */ 
/*    */ import com.sun.xml.ws.developer.MemberSubmissionAddressing;
/*    */ import com.sun.xml.ws.tx.at.common.WSATVersion;
/*    */ import com.sun.xml.ws.tx.at.common.endpoint.Participant;
/*    */ import com.sun.xml.ws.tx.at.v10.types.Notification;
/*    */ import com.sun.xml.ws.tx.at.v10.types.ParticipantPortType;
/*    */ import javax.annotation.Resource;
/*    */ import javax.jws.WebService;
/*    */ import javax.xml.ws.BindingType;
/*    */ import javax.xml.ws.WebServiceContext;
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
/*    */ 
/*    */ @WebService(portName = "ParticipantPortTypePort", serviceName = "WSAT10Service", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", wsdlLocation = "/wsdls/wsat10/wsat.wsdl", endpointInterface = "com.sun.xml.ws.tx.at.v10.types.ParticipantPortType")
/*    */ @BindingType("http://schemas.xmlsoap.org/wsdl/soap/http")
/*    */ @MemberSubmissionAddressing
/*    */ public class ParticipantPortTypePortImpl
/*    */   implements ParticipantPortType
/*    */ {
/*    */   @Resource
/*    */   private WebServiceContext m_context;
/*    */   
/*    */   public void prepare(Notification parameters) {
/* 72 */     Participant<Notification> proxy = getProxy();
/* 73 */     proxy.prepare(parameters);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void commit(Notification parameters) {
/* 81 */     Participant<Notification> proxy = getProxy();
/* 82 */     proxy.commit(parameters);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void rollback(Notification parameters) {
/* 90 */     Participant<Notification> proxy = getProxy();
/* 91 */     proxy.rollback(parameters);
/*    */   }
/*    */   
/*    */   protected Participant<Notification> getProxy() {
/* 95 */     return new Participant(this.m_context, WSATVersion.v10);
/*    */   } public String toString() {
/* 97 */     return "v10ParticipantPortTypePortImpl hashcode:" + hashCode() + " getProxy():" + getProxy() + "m_context:" + this.m_context + "m_context.getMessageContext:" + this.m_context.getMessageContext();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v10\endpoint\ParticipantPortTypePortImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */