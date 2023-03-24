/*    */ package com.sun.xml.ws.tx.coord.v10.client;
/*    */ 
/*    */ import com.sun.xml.ws.tx.at.WSATHelper;
/*    */ import com.sun.xml.ws.tx.coord.common.EndpointReferenceBuilder;
/*    */ import com.sun.xml.ws.tx.coord.common.client.RegistrationMessageBuilder;
/*    */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
/*    */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterType;
/*    */ import com.sun.xml.ws.tx.coord.v10.XmlTypeAdapter;
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
/*    */ public class RegistrationMessageBuilderImpl
/*    */   extends RegistrationMessageBuilder
/*    */ {
/*    */   public RegistrationMessageBuilder durable(boolean durable) {
/* 59 */     super.durable(durable);
/* 60 */     if (this.protocolIdentifier == null) {
/* 61 */       protocolIdentifier(durable ? "http://schemas.xmlsoap.org/ws/2004/10/wsat/Durable2PC" : "http://schemas.xmlsoap.org/ws/2004/10/wsat/Volatile2PC");
/*    */     }
/*    */ 
/*    */     
/* 65 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BaseRegisterType newRegistrationRequest() {
/* 70 */     return XmlTypeAdapter.newRegisterType();
/*    */   }
/*    */   
/*    */   protected String getDefaultParticipantAddress() {
/* 74 */     return WSATHelper.V10.getParticipantAddress();
/*    */   }
/*    */ 
/*    */   
/*    */   protected BaseRegisterResponseType buildRegistrationResponse() {
/* 79 */     return XmlTypeAdapter.newRegisterResponseType();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected EndpointReferenceBuilder getEndpointReferenceBuilder() {
/* 85 */     return EndpointReferenceBuilder.MemberSubmission();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v10\client\RegistrationMessageBuilderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */