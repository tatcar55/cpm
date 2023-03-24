/*    */ package com.sun.xml.ws.tx.coord.v10.endpoint;
/*    */ 
/*    */ import com.sun.xml.ws.developer.MemberSubmissionAddressing;
/*    */ import com.sun.xml.ws.tx.coord.common.endpoint.RegistrationRequester;
/*    */ import com.sun.xml.ws.tx.coord.v10.XmlTypeAdapter;
/*    */ import com.sun.xml.ws.tx.coord.v10.types.RegisterResponseType;
/*    */ import com.sun.xml.ws.tx.coord.v10.types.RegistrationRequesterPortType;
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
/*    */ @WebService(portName = "RegistrationRequesterPortTypePort", serviceName = "RegistrationService_V10", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wscoor", wsdlLocation = "/wsdls/wsc10/wscoor.wsdl", endpointInterface = "com.sun.xml.ws.tx.coord.v10.types.RegistrationRequesterPortType")
/*    */ @BindingType("http://schemas.xmlsoap.org/wsdl/soap/http")
/*    */ @MemberSubmissionAddressing
/*    */ public class RegistrationRequesterPortTypePortImpl
/*    */   implements RegistrationRequesterPortType
/*    */ {
/*    */   @Resource
/*    */   private WebServiceContext m_context;
/*    */   
/*    */   public void registerResponse(RegisterResponseType parameters) {
/* 71 */     RegistrationRequester proxy = new RegistrationRequesterImpl(this.m_context);
/* 72 */     proxy.registerResponse(XmlTypeAdapter.adapt(parameters));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v10\endpoint\RegistrationRequesterPortTypePortImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */