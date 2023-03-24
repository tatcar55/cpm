/*     */ package com.sun.xml.ws.tx.at.v10.client;
/*     */ 
/*     */ import com.sun.xml.ws.tx.at.WSATHelper;
/*     */ import com.sun.xml.ws.tx.at.v10.types.CoordinatorPortType;
/*     */ import com.sun.xml.ws.tx.at.v10.types.ParticipantPortType;
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
/*     */ 
/*     */ @WebServiceClient(name = "WSAT10Service", targetNamespace = "http://schemas.xmlsoap.org/ws/2004/10/wsat", wsdlLocation = "wsat.wsdl")
/*     */ public class WSAT10Service
/*     */   extends Service
/*     */ {
/*     */   private static URL WSAT10SERVICE_WSDL_LOCATION;
/*     */   
/*     */   static {
/*     */     try {
/*  65 */       WSAT10SERVICE_WSDL_LOCATION = new URL(WSATHelper.V10.getCoordinatorAddress() + "?wsdl");
/*  66 */     } catch (MalformedURLException e) {
/*  67 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public WSAT10Service(URL wsdlLocation, QName serviceName) {
/*  72 */     super(wsdlLocation, serviceName);
/*     */   }
/*     */   
/*     */   public WSAT10Service() {
/*  76 */     super(WSAT10SERVICE_WSDL_LOCATION, new QName("http://schemas.xmlsoap.org/ws/2004/10/wsat", "WSATCoordinator"));
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
/*     */   @WebEndpoint(name = "CoordinatorPortTypePort")
/*     */   public CoordinatorPortType getCoordinatorPortTypePort(EndpointReference epr, WebServiceFeature... features) {
/*  89 */     return getPort(epr, CoordinatorPortType.class, features);
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
/*     */   @WebEndpoint(name = "ParticipantPortTypePort")
/*     */   public ParticipantPortType getParticipantPortTypePort(EndpointReference epr, WebServiceFeature... features) {
/* 102 */     return getPort(epr, ParticipantPortType.class, features);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v10\client\WSAT10Service.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */