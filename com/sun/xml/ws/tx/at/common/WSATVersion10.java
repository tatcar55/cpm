/*    */ package com.sun.xml.ws.tx.at.common;
/*    */ 
/*    */ import com.sun.xml.ws.api.SOAPVersion;
/*    */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*    */ import com.sun.xml.ws.api.tx.at.Transactional;
/*    */ import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
/*    */ import com.sun.xml.ws.tx.at.WSATHelper;
/*    */ import com.sun.xml.ws.tx.at.common.client.CoordinatorProxyBuilder;
/*    */ import com.sun.xml.ws.tx.at.common.client.ParticipantProxyBuilder;
/*    */ import com.sun.xml.ws.tx.at.v10.NotificationBuilderImpl;
/*    */ import com.sun.xml.ws.tx.at.v10.client.CoordinatorProxyBuilderImpl;
/*    */ import com.sun.xml.ws.tx.at.v10.client.ParticipantProxyBuilderImpl;
/*    */ import com.sun.xml.ws.tx.at.v10.types.Notification;
/*    */ import com.sun.xml.ws.tx.coord.common.EndpointReferenceBuilder;
/*    */ import javax.xml.ws.WebServiceFeature;
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
/*    */ class WSATVersion10
/*    */   extends WSATVersion<Notification>
/*    */ {
/*    */   WSATVersion10() {
/* 61 */     super(Transactional.Version.WSAT10);
/* 62 */     this.addressingVersion = AddressingVersion.MEMBER;
/* 63 */     this.soapVersion = SOAPVersion.SOAP_11;
/*    */   }
/*    */ 
/*    */   
/*    */   public WSATHelper getWSATHelper() {
/* 68 */     return WSATHelper.V10;
/*    */   }
/*    */ 
/*    */   
/*    */   public CoordinatorProxyBuilder<Notification> newCoordinatorProxyBuilder() {
/* 73 */     return (CoordinatorProxyBuilder<Notification>)new CoordinatorProxyBuilderImpl();
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticipantProxyBuilder<Notification> newParticipantProxyBuilder() {
/* 78 */     return (ParticipantProxyBuilder<Notification>)new ParticipantProxyBuilderImpl();
/*    */   }
/*    */ 
/*    */   
/*    */   public EndpointReferenceBuilder newEndpointReferenceBuilder() {
/* 83 */     return EndpointReferenceBuilder.MemberSubmission();
/*    */   }
/*    */ 
/*    */   
/*    */   public WebServiceFeature newAddressingFeature() {
/* 88 */     return (WebServiceFeature)new MemberSubmissionAddressingFeature();
/*    */   }
/*    */ 
/*    */   
/*    */   public NotificationBuilder newNotificationBuilder() {
/* 93 */     return (NotificationBuilder)new NotificationBuilderImpl();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\common\WSATVersion10.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */