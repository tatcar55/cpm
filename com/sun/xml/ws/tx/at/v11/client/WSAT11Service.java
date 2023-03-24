/*    */ package com.sun.xml.ws.tx.at.v11.client;
/*    */ 
/*    */ import com.sun.xml.ws.tx.at.WSATHelper;
/*    */ import com.sun.xml.ws.tx.at.v11.types.CoordinatorPortType;
/*    */ import com.sun.xml.ws.tx.at.v11.types.ParticipantPortType;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.ws.EndpointReference;
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
/*    */ @WebServiceClient(name = "WSAT11Service", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", wsdlLocation = "wstx-wsat-1.1-wsdl-200702.wsdl")
/*    */ public class WSAT11Service
/*    */   extends Service
/*    */ {
/*    */   private static URL WSAT11SERVICE_WSDL_LOCATION;
/*    */   
/*    */   static {
/*    */     try {
/* 64 */       WSAT11SERVICE_WSDL_LOCATION = new URL(WSATHelper.V11.getCoordinatorAddress() + "?wsdl");
/* 65 */     } catch (MalformedURLException e) {
/* 66 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   public WSAT11Service(URL wsdlLocation, QName serviceName) {
/* 71 */     super(wsdlLocation, serviceName);
/*    */   }
/*    */   
/*    */   public WSAT11Service() {
/* 75 */     super(WSAT11SERVICE_WSDL_LOCATION, new QName("http://docs.oasis-open.org/ws-tx/wsat/2006/06", "WSAT11Service"));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @WebEndpoint(name = "CoordinatorPort")
/*    */   public CoordinatorPortType getCoordinatorPort(EndpointReference epr, WebServiceFeature... features) {
/* 87 */     return getPort(epr, CoordinatorPortType.class, features);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @WebEndpoint(name = "ParticipantPort")
/*    */   public ParticipantPortType getParticipantPort(EndpointReference epr, WebServiceFeature... features) {
/* 99 */     return getPort(epr, ParticipantPortType.class, features);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\tx\at\v11\client\WSAT11Service.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */