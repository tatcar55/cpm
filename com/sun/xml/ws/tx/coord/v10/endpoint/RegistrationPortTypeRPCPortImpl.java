/*    */ package com.sun.xml.ws.tx.coord.v10.endpoint;
/*    */ 
/*    */ import com.sun.xml.ws.developer.MemberSubmissionAddressing;
/*    */ import com.sun.xml.ws.developer.MemberSubmissionEndpointReference;
/*    */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
/*    */ import com.sun.xml.ws.tx.coord.v10.XmlTypeAdapter;
/*    */ import com.sun.xml.ws.tx.coord.v10.types.RegisterResponseType;
/*    */ import com.sun.xml.ws.tx.coord.v10.types.RegisterType;
/*    */ import com.sun.xml.ws.tx.coord.v10.types.RegistrationPortTypeRPC;
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
/*    */ @WebService(portName = "RegistrationPortTypeRPCPort", serviceName = "RegistrationService_V10", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wscoor", wsdlLocation = "/wsdls/wsc10/wscoor.wsdl", endpointInterface = "com.sun.xml.ws.tx.coord.v10.types.RegistrationPortTypeRPC")
/*    */ @BindingType("http://schemas.xmlsoap.org/wsdl/soap/http")
/*    */ @MemberSubmissionAddressing
/*    */ public class RegistrationPortTypeRPCPortImpl
/*    */   implements RegistrationPortTypeRPC
/*    */ {
/*    */   @Resource
/*    */   private WebServiceContext m_context;
/*    */   
/*    */   public RegisterResponseType registerOperation(RegisterType parameters) {
/* 72 */     RegistrationProxyImpl proxy = getProxy();
/*    */     
/* 74 */     BaseRegisterResponseType<MemberSubmissionEndpointReference, RegisterResponseType> response = proxy.registerOperation(XmlTypeAdapter.adapt(parameters));
/* 75 */     return (RegisterResponseType)response.getDelegate();
/*    */   }
/*    */   
/*    */   protected RegistrationProxyImpl getProxy() {
/* 79 */     return new RegistrationProxyImpl(this.m_context);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v10\endpoint\RegistrationPortTypeRPCPortImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */