/*    */ package com.sun.xml.ws.tx.coord.v11.endpoint;
/*    */ 
/*    */ import com.sun.xml.ws.tx.coord.common.types.BaseRegisterResponseType;
/*    */ import com.sun.xml.ws.tx.coord.v11.XmlTypeAdapter;
/*    */ import com.sun.xml.ws.tx.coord.v11.types.RegisterResponseType;
/*    */ import com.sun.xml.ws.tx.coord.v11.types.RegisterType;
/*    */ import com.sun.xml.ws.tx.coord.v11.types.RegistrationPortType;
/*    */ import javax.annotation.Resource;
/*    */ import javax.jws.WebService;
/*    */ import javax.xml.ws.BindingType;
/*    */ import javax.xml.ws.WebServiceContext;
/*    */ import javax.xml.ws.soap.Addressing;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @WebService(portName = "RegistrationPort", serviceName = "RegistrationService_V11", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", wsdlLocation = "/wsdls/wsc11/wstx-wscoor-1.1-wsdl-200702.wsdl", endpointInterface = "com.sun.xml.ws.tx.coord.v11.types.RegistrationPortType")
/*    */ @BindingType("http://schemas.xmlsoap.org/wsdl/soap/http")
/*    */ @Addressing
/*    */ public class RegistrationPortImpl
/*    */   implements RegistrationPortType
/*    */ {
/*    */   @Resource
/*    */   private WebServiceContext m_context;
/*    */   
/*    */   public RegisterResponseType registerOperation(RegisterType parameters) {
/* 76 */     this.m_context.getMessageContext().put("javax.xml.ws.soap.http.soapaction.use", Boolean.valueOf(true));
/* 77 */     this.m_context.getMessageContext().put("javax.xml.ws.soap.http.soapaction.uri", "http://docs.oasis-open.org/ws-tx/wscoor/2006/06/RegisterResponse");
/* 78 */     RegistrationProxyImpl proxy = getProxy();
/*    */     
/* 80 */     BaseRegisterResponseType<W3CEndpointReference, RegisterResponseType> response = proxy.registerOperation(XmlTypeAdapter.adapt(parameters));
/* 81 */     return (RegisterResponseType)response.getDelegate();
/*    */   }
/*    */   
/*    */   protected RegistrationProxyImpl getProxy() {
/* 85 */     return new RegistrationProxyImpl(this.m_context);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v11\endpoint\RegistrationPortImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */