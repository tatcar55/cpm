/*     */ package com.sun.xml.ws.tx.at.v11.types;
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
/*     */ @WebServiceClient(name = "WSAT11Service", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", wsdlLocation = "file:wsdls/wsat11/wstx-wsat-1.1-wsdl-200702.wsdl")
/*     */ public class WSAT11Service
/*     */   extends Service
/*     */ {
/*     */   private static final URL WSAT11SERVICE_WSDL_LOCATION;
/*  60 */   private static final Logger LOGGER = Logger.getLogger(WSAT11Service.class);
/*     */   
/*     */   static {
/*  63 */     URL url = null;
/*     */     
/*     */     try {
/*  66 */       URL baseUrl = WSAT11Service.class.getResource(".");
/*  67 */       url = new URL(baseUrl, "file:wsdls/wsat11/wstx-wsat-1.1-wsdl-200702.wsdl");
/*  68 */     } catch (MalformedURLException e) {
/*  69 */       LOGGER.warning(LocalizationMessages.WSAT_4619_FAILED_TO_CREATE_URL_FOR_WSDL());
/*  70 */       LOGGER.warning(e.getMessage());
/*     */     } 
/*  72 */     WSAT11SERVICE_WSDL_LOCATION = url;
/*     */   }
/*     */   
/*     */   public WSAT11Service(URL wsdlLocation, QName serviceName) {
/*  76 */     super(wsdlLocation, serviceName);
/*     */   }
/*     */   
/*     */   public WSAT11Service() {
/*  80 */     super(WSAT11SERVICE_WSDL_LOCATION, new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "WSAT11Service"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "CoordinatorPort")
/*     */   public CoordinatorPortType getCoordinatorPort() {
/*  90 */     return getPort(new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "CoordinatorPort"), CoordinatorPortType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "CoordinatorPort")
/*     */   public CoordinatorPortType getCoordinatorPort(WebServiceFeature... features) {
/* 102 */     return getPort(new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "CoordinatorPort"), CoordinatorPortType.class, features);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "ParticipantPort")
/*     */   public ParticipantPortType getParticipantPort() {
/* 112 */     return getPort(new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "ParticipantPort"), ParticipantPortType.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @WebEndpoint(name = "ParticipantPort")
/*     */   public ParticipantPortType getParticipantPort(WebServiceFeature... features) {
/* 124 */     return getPort(new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "ParticipantPort"), ParticipantPortType.class, features);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v11\types\WSAT11Service.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */