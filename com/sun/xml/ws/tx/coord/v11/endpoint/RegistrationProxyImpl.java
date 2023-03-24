/*    */ package com.sun.xml.ws.tx.coord.v11.endpoint;
/*    */ 
/*    */ import com.sun.xml.ws.api.tx.at.Transactional;
/*    */ import com.sun.xml.ws.tx.at.WSATHelper;
/*    */ import com.sun.xml.ws.tx.coord.common.EndpointReferenceBuilder;
/*    */ import com.sun.xml.ws.tx.coord.common.endpoint.BaseRegistration;
/*    */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
/*    */ import com.sun.xml.ws.tx.coord.v11.XmlTypeAdapter;
/*    */ import com.sun.xml.ws.tx.coord.v11.types.RegisterResponseType;
/*    */ import com.sun.xml.ws.tx.coord.v11.types.RegisterType;
/*    */ import javax.xml.ws.WebServiceContext;
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
/*    */ 
/*    */ public class RegistrationProxyImpl
/*    */   extends BaseRegistration<W3CEndpointReference, RegisterType, RegisterResponseType>
/*    */ {
/*    */   public RegistrationProxyImpl(WebServiceContext context) {
/* 59 */     super(context, Transactional.Version.WSAT11);
/*    */   }
/*    */ 
/*    */   
/*    */   protected EndpointReferenceBuilder<W3CEndpointReference> getEndpointReferenceBuilder() {
/* 64 */     return EndpointReferenceBuilder.W3C();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected BaseRegisterResponseType<W3CEndpointReference, RegisterResponseType> newRegisterResponseType() {
/* 70 */     return XmlTypeAdapter.adapt(new RegisterResponseType());
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getCoordinatorAddress() {
/* 75 */     return WSATHelper.V11.getCoordinatorAddress();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v11\endpoint\RegistrationProxyImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */