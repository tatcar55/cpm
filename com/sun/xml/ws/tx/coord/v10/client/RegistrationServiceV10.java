/*     */ package com.sun.xml.ws.tx.coord.v10.client;
/*     */ 
/*     */ import com.sun.xml.ws.tx.at.WSATHelper;
/*     */ import com.sun.xml.ws.tx.coord.v10.types.RegistrationCoordinatorPortType;
/*     */ import com.sun.xml.ws.tx.coord.v10.types.RegistrationPortTypeRPC;
/*     */ import com.sun.xml.ws.tx.coord.v10.types.RegistrationRequesterPortType;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebEndpoint;
/*     */ import javax.xml.ws.WebServiceClient;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @WebServiceClient(name = "RegistrationService_V10", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wscoor", wsdlLocation = "wscoor.wsdl")
/*     */ public class RegistrationServiceV10
/*     */   extends Service
/*     */ {
/*     */   private static URL REGISTRATIONSERVICEV10_WSDL_LOCATION;
/*     */   
/*     */   static {
/*     */     try {
/*  65 */       REGISTRATIONSERVICEV10_WSDL_LOCATION = new URL(WSATHelper.V10.getRegistrationCoordinatorAddress() + "?wsdl");
/*  66 */     } catch (MalformedURLException e) {
/*  67 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public RegistrationServiceV10(URL wsdlLocation, QName serviceName) {
/*  72 */     super(wsdlLocation, serviceName);
/*     */   }
/*     */   
/*     */   public RegistrationServiceV10() {
/*  76 */     super(REGISTRATIONSERVICEV10_WSDL_LOCATION, new QName("http://schemas.xmlsoap.org/ws/2004/10/wscoor", "Coordinator"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "RegistrationRequesterPortTypePort")
/*     */   public RegistrationRequesterPortType getRegistrationRequesterPortTypePort(EndpointReference epr, WebServiceFeature... features) {
/*  88 */     return getPort(epr, RegistrationRequesterPortType.class, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "RegistrationPortTypeRPCPort")
/*     */   public RegistrationPortTypeRPC getRegistrationPortTypeRPCPort(EndpointReference epr, WebServiceFeature... features) {
/* 100 */     return getPort(epr, RegistrationPortTypeRPC.class, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "RegistrationCoordinatorPortTypePort")
/*     */   public RegistrationCoordinatorPortType getRegistrationCoordinatorPortTypePort(EndpointReference epr, WebServiceFeature... features) {
/* 113 */     return getPort(epr, RegistrationCoordinatorPortType.class, features);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v10\client\RegistrationServiceV10.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */