/*    */ package com.sun.xml.ws.tx.coord.common.client;
/*    */ 
/*    */ import com.sun.xml.ws.tx.coord.common.EndpointReferenceBuilder;
/*    */ import com.sun.xml.ws.tx.coord.common.WSCUtil;
/*    */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
/*    */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterType;
/*    */ import javax.xml.ws.EndpointReference;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class RegistrationMessageBuilder
/*    */ {
/*    */   protected boolean durable = true;
/*    */   protected Element txIdElement;
/*    */   protected Element routingElement;
/*    */   protected String participantAddress;
/*    */   protected String protocolIdentifier;
/*    */   
/*    */   public RegistrationMessageBuilder durable(boolean durable) {
/* 61 */     this.durable = durable;
/* 62 */     return this;
/*    */   }
/*    */   
/*    */   public RegistrationMessageBuilder txId(String txId) {
/* 66 */     this.txIdElement = WSCUtil.referenceElementTxId(txId);
/* 67 */     return this;
/*    */   }
/*    */   
/*    */   public RegistrationMessageBuilder routing() {
/* 71 */     this.routingElement = WSCUtil.referenceElementRoutingInfo();
/* 72 */     return this;
/*    */   }
/*    */   
/*    */   public RegistrationMessageBuilder participantAddress(String address) {
/* 76 */     this.participantAddress = address;
/* 77 */     return this;
/*    */   }
/*    */   
/*    */   public RegistrationMessageBuilder protocolIdentifier(String protocolIdentifier) {
/* 81 */     this.protocolIdentifier = protocolIdentifier;
/* 82 */     return this;
/*    */   }
/*    */   
/*    */   public BaseRegisterType build() {
/* 86 */     if (this.participantAddress == null)
/* 87 */       this.participantAddress = getDefaultParticipantAddress(); 
/* 88 */     BaseRegisterType registerType = newRegistrationRequest();
/* 89 */     registerType.setParticipantProtocolService(getParticipantProtocolService());
/* 90 */     registerType.setProtocolIdentifier(this.protocolIdentifier);
/* 91 */     return registerType;
/*    */   }
/*    */   
/*    */   protected EndpointReference getParticipantProtocolService() {
/* 95 */     EndpointReferenceBuilder eprBuilder = getEndpointReferenceBuilder();
/* 96 */     return eprBuilder.address(this.participantAddress).referenceParameter(new Element[] { this.txIdElement }).referenceParameter(new Element[] { this.routingElement }).build();
/*    */   }
/*    */   
/*    */   protected abstract BaseRegisterType newRegistrationRequest();
/*    */   
/*    */   protected abstract String getDefaultParticipantAddress();
/*    */   
/*    */   protected abstract BaseRegisterResponseType buildRegistrationResponse();
/*    */   
/*    */   protected abstract EndpointReferenceBuilder getEndpointReferenceBuilder();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\common\client\RegistrationMessageBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */