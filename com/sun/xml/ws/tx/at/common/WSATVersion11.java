/*    */ package com.sun.xml.ws.tx.at.common;
/*    */ 
/*    */ import com.sun.xml.ws.api.SOAPVersion;
/*    */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*    */ import com.sun.xml.ws.api.tx.at.Transactional;
/*    */ import com.sun.xml.ws.tx.at.WSATHelper;
/*    */ import com.sun.xml.ws.tx.at.common.client.CoordinatorProxyBuilder;
/*    */ import com.sun.xml.ws.tx.at.common.client.ParticipantProxyBuilder;
/*    */ import com.sun.xml.ws.tx.at.v11.NotificationBuilderImpl;
/*    */ import com.sun.xml.ws.tx.at.v11.client.CoordinatorProxyBuilderImpl;
/*    */ import com.sun.xml.ws.tx.at.v11.client.ParticipantProxyBuilderImpl;
/*    */ import com.sun.xml.ws.tx.at.v11.types.Notification;
/*    */ import com.sun.xml.ws.tx.coord.common.EndpointReferenceBuilder;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ import javax.xml.ws.soap.AddressingFeature;
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
/*    */ class WSATVersion11
/*    */   extends WSATVersion<Notification>
/*    */ {
/*    */   WSATVersion11() {
/* 62 */     super(Transactional.Version.WSAT11);
/* 63 */     this.addressingVersion = AddressingVersion.W3C;
/* 64 */     this.soapVersion = SOAPVersion.SOAP_11;
/*    */   }
/*    */ 
/*    */   
/*    */   public WSATHelper getWSATHelper() {
/* 69 */     return WSATHelper.V11;
/*    */   }
/*    */ 
/*    */   
/*    */   public CoordinatorProxyBuilder<Notification> newCoordinatorProxyBuilder() {
/* 74 */     return (CoordinatorProxyBuilder<Notification>)new CoordinatorProxyBuilderImpl();
/*    */   }
/*    */ 
/*    */   
/*    */   public ParticipantProxyBuilder<Notification> newParticipantProxyBuilder() {
/* 79 */     return (ParticipantProxyBuilder<Notification>)new ParticipantProxyBuilderImpl();
/*    */   }
/*    */ 
/*    */   
/*    */   public NotificationBuilder<Notification> newNotificationBuilder() {
/* 84 */     return (NotificationBuilder<Notification>)new NotificationBuilderImpl();
/*    */   }
/*    */ 
/*    */   
/*    */   public EndpointReferenceBuilder newEndpointReferenceBuilder() {
/* 89 */     return EndpointReferenceBuilder.W3C();
/*    */   }
/*    */ 
/*    */   
/*    */   public WebServiceFeature newAddressingFeature() {
/* 94 */     return new AddressingFeature();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\common\WSATVersion11.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */