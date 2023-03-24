/*    */ package com.sun.xml.ws.tx.coord.v11.endpoint;
/*    */ 
/*    */ import com.sun.xml.ws.tx.coord.common.endpoint.RegistrationRequester;
/*    */ import com.sun.xml.ws.tx.coord.v11.XmlTypeAdapter;
/*    */ import com.sun.xml.ws.tx.coord.v11.types.RegisterResponseType;
/*    */ import com.sun.xml.ws.tx.coord.v11.types.RegistrationRequesterPortType;
/*    */ import javax.annotation.Resource;
/*    */ import javax.jws.WebService;
/*    */ import javax.xml.ws.BindingType;
/*    */ import javax.xml.ws.WebServiceContext;
/*    */ import javax.xml.ws.soap.Addressing;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @WebService(portName = "RegistrationRequesterPort", serviceName = "RegistrationService_V11", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", wsdlLocation = "/wsdls/wsc11/wstx-wscoor-1.1-wsdl-200702.wsdl", endpointInterface = "com.sun.xml.ws.tx.coord.v11.types.RegistrationRequesterPortType")
/*    */ @BindingType("http://schemas.xmlsoap.org/wsdl/soap/http")
/*    */ @Addressing
/*    */ public class RegistrationRequesterPortImpl
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v11\endpoint\RegistrationRequesterPortImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */