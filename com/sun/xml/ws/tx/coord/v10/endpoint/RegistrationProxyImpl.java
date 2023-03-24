/*    */ package com.sun.xml.ws.tx.coord.v10.endpoint;
/*    */ 
/*    */ import com.sun.xml.ws.api.tx.at.Transactional;
/*    */ import com.sun.xml.ws.developer.MemberSubmissionEndpointReference;
/*    */ import com.sun.xml.ws.tx.at.WSATHelper;
/*    */ import com.sun.xml.ws.tx.coord.common.EndpointReferenceBuilder;
/*    */ import com.sun.xml.ws.tx.coord.common.endpoint.BaseRegistration;
/*    */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
/*    */ import com.sun.xml.ws.tx.coord.v10.XmlTypeAdapter;
/*    */ import com.sun.xml.ws.tx.coord.v10.types.RegisterResponseType;
/*    */ import com.sun.xml.ws.tx.coord.v10.types.RegisterType;
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
/*    */ public class RegistrationProxyImpl
/*    */   extends BaseRegistration<MemberSubmissionEndpointReference, RegisterType, RegisterResponseType>
/*    */ {
/*    */   public RegistrationProxyImpl(WebServiceContext context) {
/* 59 */     super(context, Transactional.Version.WSAT10);
/*    */   }
/*    */ 
/*    */   
/*    */   protected EndpointReferenceBuilder<MemberSubmissionEndpointReference> getEndpointReferenceBuilder() {
/* 64 */     return EndpointReferenceBuilder.MemberSubmission();
/*    */   }
/*    */ 
/*    */   
/*    */   protected BaseRegisterResponseType<MemberSubmissionEndpointReference, RegisterResponseType> newRegisterResponseType() {
/* 69 */     return XmlTypeAdapter.adapt(new RegisterResponseType());
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getCoordinatorAddress() {
/* 74 */     return WSATHelper.V10.getCoordinatorAddress();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v10\endpoint\RegistrationProxyImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */