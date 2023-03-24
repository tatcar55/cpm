/*    */ package com.sun.xml.ws.transport.tcp.servicechannel.stubs;
/*    */ 
/*    */ import com.sun.xml.ws.transport.tcp.servicechannel.ServiceChannelWSImpl;
/*    */ import java.net.URL;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.Service;
/*    */ import javax.xml.ws.WebEndpoint;
/*    */ import javax.xml.ws.WebServiceClient;
/*    */ import javax.xml.ws.WebServiceFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @WebServiceClient(name = "ServiceChannelWSImplService", targetNamespace = "http://servicechannel.tcp.transport.ws.xml.sun.com/")
/*    */ public class ServiceChannelWSImplService
/*    */   extends Service
/*    */ {
/* 65 */   private static final URL SERVICECHANNELWSIMPLSERVICE_WSDL_LOCATION = ServiceChannelWSImpl.class.getResource("ServiceChannelWSImplService.wsdl");
/*    */ 
/*    */   
/*    */   public ServiceChannelWSImplService(URL wsdlLocation, QName serviceName) {
/* 69 */     super(wsdlLocation, serviceName);
/*    */   }
/*    */   
/*    */   public ServiceChannelWSImplService() {
/* 73 */     super(SERVICECHANNELWSIMPLSERVICE_WSDL_LOCATION, new QName("http://servicechannel.tcp.transport.ws.xml.sun.com/", "ServiceChannelWSImplService"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @WebEndpoint(name = "ServiceChannelWSImplPort")
/*    */   public ServiceChannelWSImpl getServiceChannelWSImplPort() {
/* 83 */     return getPort(new QName("http://servicechannel.tcp.transport.ws.xml.sun.com/", "ServiceChannelWSImplPort"), ServiceChannelWSImpl.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @WebEndpoint(name = "ServiceChannelWSImplPort")
/*    */   public ServiceChannelWSImpl getServiceChannelWSImplPort(WebServiceFeature... features) {
/* 95 */     return getPort(new QName("http://servicechannel.tcp.transport.ws.xml.sun.com/", "ServiceChannelWSImplPort"), ServiceChannelWSImpl.class, features);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\servicechannel\stubs\ServiceChannelWSImplService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */