/*     */ package com.sun.xml.ws.tx.coord.v11.client;
/*     */ 
/*     */ import com.sun.xml.ws.tx.at.WSATHelper;
/*     */ import com.sun.xml.ws.tx.coord.v11.types.RegistrationCoordinatorPortType;
/*     */ import com.sun.xml.ws.tx.coord.v11.types.RegistrationPortType;
/*     */ import com.sun.xml.ws.tx.coord.v11.types.RegistrationRequesterPortType;
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
/*     */ @WebServiceClient(name = "RegistrationService_V11", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", wsdlLocation = "wstx-wscoor-1.1-wsdl-200702.wsdl")
/*     */ public class RegistrationServiceV11
/*     */   extends Service
/*     */ {
/*     */   private static URL REGISTRATIONSERVICEV11_WSDL_LOCATION;
/*     */   
/*     */   static {
/*     */     try {
/*  61 */       REGISTRATIONSERVICEV11_WSDL_LOCATION = new URL(WSATHelper.V11.getRegistrationCoordinatorAddress() + "?wsdl");
/*  62 */     } catch (MalformedURLException e) {
/*  63 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public RegistrationServiceV11(URL wsdlLocation, QName serviceName) {
/*  68 */     super(wsdlLocation, serviceName);
/*     */   }
/*     */   
/*     */   public RegistrationServiceV11() {
/*  72 */     super(REGISTRATIONSERVICEV11_WSDL_LOCATION, new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "RegistrationService_V11"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "RegistrationPort")
/*     */   public RegistrationPortType getRegistrationPort(EndpointReference epr, WebServiceFeature... features) {
/*  84 */     return getPort(epr, RegistrationPortType.class, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "RegistrationRequesterPort")
/*     */   public RegistrationRequesterPortType getRegistrationRequesterPort(EndpointReference epr, WebServiceFeature... features) {
/*  96 */     return getPort(epr, RegistrationRequesterPortType.class, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "RegistrationCoordinatorPort")
/*     */   public RegistrationCoordinatorPortType getRegistrationCoordinatorPort(EndpointReference epr, WebServiceFeature... features) {
/* 108 */     return getPort(epr, RegistrationCoordinatorPortType.class, features);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v11\client\RegistrationServiceV11.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */