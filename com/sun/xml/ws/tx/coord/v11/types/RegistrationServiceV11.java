/*     */ package com.sun.xml.ws.tx.coord.v11.types;
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
/*     */ 
/*     */ @WebServiceClient(name = "RegistrationService_V11", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", wsdlLocation = "file:wsdls/wsc11/wstx-wscoor-1.1-wsdl-200702.wsdl")
/*     */ public class RegistrationServiceV11
/*     */   extends Service
/*     */ {
/*     */   private static final URL REGISTRATIONSERVICEV11_WSDL_LOCATION;
/*  61 */   private static final Logger LOGGER = Logger.getLogger(RegistrationServiceV11.class);
/*     */   
/*     */   static {
/*  64 */     URL url = null;
/*     */     
/*     */     try {
/*  67 */       URL baseUrl = RegistrationServiceV11.class.getResource(".");
/*  68 */       url = new URL(baseUrl, "wsdls/wsc11/wstx-wscoor-1.1-wsdl-200702.wsdl");
/*  69 */     } catch (MalformedURLException e) {
/*  70 */       LOGGER.warning(LocalizationMessages.WSAT_4623_FAILED_TO_CREATE_URL_FOR_WSDL());
/*  71 */       LOGGER.warning(e.getMessage());
/*     */     } 
/*  73 */     REGISTRATIONSERVICEV11_WSDL_LOCATION = url;
/*     */   }
/*     */   
/*     */   public RegistrationServiceV11(URL wsdlLocation, QName serviceName) {
/*  77 */     super(wsdlLocation, serviceName);
/*     */   }
/*     */   
/*     */   public RegistrationServiceV11() {
/*  81 */     super(REGISTRATIONSERVICEV11_WSDL_LOCATION, new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "RegistrationService_V11"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "RegistrationPort")
/*     */   public RegistrationPortType getRegistrationPort() {
/*  91 */     return getPort(new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "RegistrationPort"), RegistrationPortType.class);
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
/*     */   public RegistrationPortType getRegistrationPort(WebServiceFeature... features) {
/* 103 */     return getPort(new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "RegistrationPort"), RegistrationPortType.class, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "RegistrationCoordinatorPort")
/*     */   public RegistrationCoordinatorPortType getRegistrationCoordinatorPort() {
/* 113 */     return getPort(new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "RegistrationCoordinatorPort"), RegistrationCoordinatorPortType.class);
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
/*     */   public RegistrationCoordinatorPortType getRegistrationCoordinatorPort(WebServiceFeature... features) {
/* 125 */     return getPort(new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "RegistrationCoordinatorPort"), RegistrationCoordinatorPortType.class, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "RegistrationRequesterPort")
/*     */   public RegistrationRequesterPortType getRegistrationRequesterPort() {
/* 135 */     return getPort(new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "RegistrationRequesterPort"), RegistrationRequesterPortType.class);
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
/*     */   public RegistrationRequesterPortType getRegistrationRequesterPort(WebServiceFeature... features) {
/* 147 */     return getPort(new QName("http://docs.oasis-open.org/ws-tx/wscoor/2006/06", "RegistrationRequesterPort"), RegistrationRequesterPortType.class, features);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\coord\v11\types\RegistrationServiceV11.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */