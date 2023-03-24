/*     */ package com.sun.xml.ws.tx.at.v10.types;
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
/*     */ @WebServiceClient(name = "WSAT10Service", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", wsdlLocation = "file:wsdls/wsat10/wsat.wsdl")
/*     */ public class WSAT10Service
/*     */   extends Service
/*     */ {
/*     */   private static final URL WSAT10SERVICE_WSDL_LOCATION;
/*  60 */   private static final Logger LOGGER = Logger.getLogger(WSAT10Service.class);
/*     */   
/*     */   static {
/*  63 */     URL url = null;
/*     */     
/*     */     try {
/*  66 */       URL baseUrl = WSAT10Service.class.getResource(".");
/*  67 */       url = new URL(baseUrl, "file:wsdls/wsat10/wsat.wsdl");
/*  68 */     } catch (MalformedURLException e) {
/*  69 */       LOGGER.warning(LocalizationMessages.WSAT_4618_FAILED_TO_CREATE_URL_FOR_WSDL());
/*  70 */       LOGGER.warning(e.getMessage());
/*     */     } 
/*  72 */     WSAT10SERVICE_WSDL_LOCATION = url;
/*     */   }
/*     */   
/*     */   public WSAT10Service(URL wsdlLocation, QName serviceName) {
/*  76 */     super(wsdlLocation, serviceName);
/*     */   }
/*     */   
/*     */   public WSAT10Service() {
/*  80 */     super(WSAT10SERVICE_WSDL_LOCATION, new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "WSAT10Service"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "CoordinatorPortTypePort")
/*     */   public CoordinatorPortType getCoordinatorPortTypePort() {
/*  90 */     return getPort(new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "CoordinatorPortTypePort"), CoordinatorPortType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "CoordinatorPortTypePort")
/*     */   public CoordinatorPortType getCoordinatorPortTypePort(WebServiceFeature... features) {
/* 102 */     return getPort(new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "CoordinatorPortTypePort"), CoordinatorPortType.class, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "ParticipantPortTypePort")
/*     */   public ParticipantPortType getParticipantPortTypePort() {
/* 112 */     return getPort(new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "ParticipantPortTypePort"), ParticipantPortType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "ParticipantPortTypePort")
/*     */   public ParticipantPortType getParticipantPortTypePort(WebServiceFeature... features) {
/* 124 */     return getPort(new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "ParticipantPortTypePort"), ParticipantPortType.class, features);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v10\types\WSAT10Service.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */