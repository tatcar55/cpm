/*     */ package com.sun.xml.ws.tx.coord.v10.types;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.tx.at.localization.LocalizationMessages;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import javax.xml.namespace.QName;
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
/*     */ 
/*     */ @WebServiceClient(name = "RegistrationService_V10", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wscoor", wsdlLocation = "file:wsdls/wsc10/wscoor.wsdl")
/*     */ public class RegistrationServiceV10
/*     */   extends Service
/*     */ {
/*     */   private static final URL REGISTRATIONSERVICEV10_WSDL_LOCATION;
/*  60 */   private static final Logger LOGGER = Logger.getLogger(RegistrationServiceV10.class);
/*     */   
/*     */   static {
/*  63 */     URL url = null;
/*     */     
/*     */     try {
/*  66 */       URL baseUrl = RegistrationServiceV10.class.getResource(".");
/*  67 */       url = new URL(baseUrl, "file:wsdls/wsc10/wscoor.wsdl");
/*  68 */     } catch (MalformedURLException e) {
/*  69 */       LOGGER.warning(LocalizationMessages.WSAT_4622_FAILED_TO_CREATE_URL_FOR_WSDL());
/*  70 */       LOGGER.warning(e.getMessage());
/*     */     } 
/*  72 */     REGISTRATIONSERVICEV10_WSDL_LOCATION = url;
/*     */   }
/*     */   
/*     */   public RegistrationServiceV10(URL wsdlLocation, QName serviceName) {
/*  76 */     super(wsdlLocation, serviceName);
/*     */   }
/*     */   
/*     */   public RegistrationServiceV10() {
/*  80 */     super(REGISTRATIONSERVICEV10_WSDL_LOCATION, new QName("http://schemas.xmlsoap.org/ws/2004/10/wscoor", "RegistrationService_V10"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "RegistrationRequesterPortTypePort")
/*     */   public RegistrationRequesterPortType getRegistrationRequesterPortTypePort() {
/*  90 */     return getPort(new QName("http://schemas.xmlsoap.org/ws/2004/10/wscoor", "RegistrationRequesterPortTypePort"), RegistrationRequesterPortType.class);
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
/*     */   public RegistrationRequesterPortType getRegistrationRequesterPortTypePort(WebServiceFeature... features) {
/* 102 */     return getPort(new QName("http://schemas.xmlsoap.org/ws/2004/10/wscoor", "RegistrationRequesterPortTypePort"), RegistrationRequesterPortType.class, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "RegistrationPortTypeRPCPort")
/*     */   public RegistrationPortTypeRPC getRegistrationPortTypeRPCPort() {
/* 112 */     return getPort(new QName("http://schemas.xmlsoap.org/ws/2004/10/wscoor", "RegistrationPortTypeRPCPort"), RegistrationPortTypeRPC.class);
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
/*     */   public RegistrationPortTypeRPC getRegistrationPortTypeRPCPort(WebServiceFeature... features) {
/* 124 */     return getPort(new QName("http://schemas.xmlsoap.org/ws/2004/10/wscoor", "RegistrationPortTypeRPCPort"), RegistrationPortTypeRPC.class, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "RegistrationCoordinatorPortTypePort")
/*     */   public RegistrationCoordinatorPortType getRegistrationCoordinatorPortTypePort() {
/* 134 */     return getPort(new QName("http://schemas.xmlsoap.org/ws/2004/10/wscoor", "RegistrationCoordinatorPortTypePort"), RegistrationCoordinatorPortType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "RegistrationCoordinatorPortTypePort")
/*     */   public RegistrationCoordinatorPortType getRegistrationCoordinatorPortTypePort(WebServiceFeature... features) {
/* 146 */     return getPort(new QName("http://schemas.xmlsoap.org/ws/2004/10/wscoor", "RegistrationCoordinatorPortTypePort"), RegistrationCoordinatorPortType.class, features);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v10\types\RegistrationServiceV10.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */