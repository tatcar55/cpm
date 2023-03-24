/*    */ package com.sun.xml.ws.tx.coord.v11.client;
/*    */ 
/*    */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*    */ import com.sun.xml.ws.tx.at.WSATHelper;
/*    */ import com.sun.xml.ws.tx.coord.common.EndpointReferenceBuilder;
/*    */ import com.sun.xml.ws.tx.coord.common.RegistrationIF;
/*    */ import com.sun.xml.ws.tx.coord.common.client.RegistrationProxyBuilder;
/*    */ import com.sun.xml.ws.tx.coord.v11.types.RegisterResponseType;
/*    */ import com.sun.xml.ws.tx.coord.v11.types.RegisterType;
/*    */ import com.sun.xml.ws.tx.coord.v11.types.RegistrationCoordinatorPortType;
/*    */ import javax.xml.ws.EndpointReference;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ import javax.xml.ws.soap.AddressingFeature;
/*    */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistrationProxyBuilderImpl
/*    */   extends RegistrationProxyBuilder
/*    */ {
/*    */   public RegistrationProxyBuilderImpl() {
/* 60 */     feature(new AddressingFeature());
/*    */   }
/*    */   
/*    */   public RegistrationIF<W3CEndpointReference, RegisterType, RegisterResponseType> build() {
/* 64 */     super.build();
/* 65 */     return (RegistrationIF<W3CEndpointReference, RegisterType, RegisterResponseType>)new RegistrationProxyImpl();
/*    */   }
/*    */   
/*    */   protected String getDefaultCallbackAddress() {
/* 69 */     return WSATHelper.V11.getRegistrationRequesterAddress();
/*    */   }
/*    */   
/*    */   protected EndpointReferenceBuilder getEndpointReferenceBuilder() {
/* 73 */     return EndpointReferenceBuilder.W3C();
/*    */   }
/*    */   
/*    */   public class RegistrationProxyImpl
/*    */     extends RegistrationProxyBuilder.RegistrationProxyF<W3CEndpointReference, RegisterType, RegisterResponseType, RegistrationCoordinatorPortType>
/*    */   {
/* 79 */     private RegistrationServiceV11 service = new RegistrationServiceV11();
/*    */ 
/*    */     
/*    */     private RegistrationCoordinatorPortType port;
/*    */ 
/*    */ 
/*    */     
/*    */     public RegistrationCoordinatorPortType getDelegate() {
/* 87 */       return this.port;
/*    */     }
/*    */     
/*    */     public void asyncRegister(RegisterType parameters) {
/* 91 */       this.port.registerOperation(parameters);
/*    */     }
/*    */     
/*    */     public AddressingVersion getAddressingVersion() {
/* 95 */       return AddressingVersion.W3C;
/*    */     }
/*    */     
/*    */     RegistrationProxyImpl() {
/*    */       super(RegistrationProxyBuilderImpl.this);
/*    */       this.port = this.service.getRegistrationCoordinatorPort(RegistrationProxyBuilderImpl.this.to, RegistrationProxyBuilderImpl.this.getEnabledFeatures());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v11\client\RegistrationProxyBuilderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */