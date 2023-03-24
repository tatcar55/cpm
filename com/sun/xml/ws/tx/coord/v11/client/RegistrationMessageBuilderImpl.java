/*    */ package com.sun.xml.ws.tx.coord.v11.client;
/*    */ 
/*    */ import com.sun.xml.ws.tx.at.WSATHelper;
/*    */ import com.sun.xml.ws.tx.coord.common.EndpointReferenceBuilder;
/*    */ import com.sun.xml.ws.tx.coord.common.client.RegistrationMessageBuilder;
/*    */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
/*    */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterType;
/*    */ import com.sun.xml.ws.tx.coord.v11.XmlTypeAdapter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
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
/* 60 */     super.durable(durable);
/* 61 */     if (this.protocolIdentifier == null) {
/* 62 */       protocolIdentifier(durable ? "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Durable2PC" : "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Volatile2PC");
/*    */     }
/*    */ 
/*    */     
/* 66 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   protected BaseRegisterType newRegistrationRequest() {
/* 71 */     return XmlTypeAdapter.newRegisterType();
/*    */   }
/*    */   
/*    */   protected String getDefaultParticipantAddress() {
/* 75 */     return WSATHelper.V11.getParticipantAddress();
/*    */   }
/*    */ 
/*    */   
/*    */   protected BaseRegisterResponseType buildRegistrationResponse() {
/* 80 */     return XmlTypeAdapter.newRegisterResponseType();
/*    */   }
/*    */ 
/*    */   
/*    */   protected EndpointReferenceBuilder getEndpointReferenceBuilder() {
/* 85 */     return EndpointReferenceBuilder.W3C();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v11\client\RegistrationMessageBuilderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */