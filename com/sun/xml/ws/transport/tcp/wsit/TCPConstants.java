/*    */ package com.sun.xml.ws.transport.tcp.wsit;
/*    */ 
/*    */ import javax.xml.namespace.QName;
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
/*    */ public final class TCPConstants
/*    */ {
/*    */   public static final String TCPTRANSPORT_POLICY_NAMESPACE_URI = "http://java.sun.com/xml/ns/wsit/2006/09/policy/soaptcp/service";
/* 54 */   public static final QName TCPTRANSPORT_POLICY_ASSERTION = new QName("http://java.sun.com/xml/ns/wsit/2006/09/policy/soaptcp/service", "OptimizedTCPTransport");
/* 55 */   public static final QName TCPTRANSPORT_PORT_ATTRIBUTE = new QName("port");
/*    */   
/*    */   public static final String CLIENT_TRANSPORT_NS = "http://java.sun.com/xml/ns/wsit/2006/09/policy/transport/client";
/* 58 */   public static final QName SELECT_OPTIMAL_TRANSPORT_ASSERTION = new QName("http://java.sun.com/xml/ns/wsit/2006/09/policy/transport/client", "AutomaticallySelectOptimalTransport");
/*    */   
/*    */   public static final String TCPTRANSPORT_CONNECTION_MANAGEMENT_NAMESPACE_URI = "http://java.sun.com/xml/ns/wsit/2006/09/policy/soaptcp";
/* 61 */   public static final QName TCPTRANSPORT_CONNECTION_MANAGEMENT_ASSERTION = new QName("http://java.sun.com/xml/ns/wsit/2006/09/policy/soaptcp", "ConnectionManagement");
/*    */   public static final String TCPTRANSPORT_CONNECTION_MANAGEMENT_HIGH_WATERMARK_ATTR = "HighWatermark";
/*    */   public static final String TCPTRANSPORT_CONNECTION_MANAGEMENT_MAX_PARALLEL_CONNECTIONS_ATTR = "MaxParallelConnections";
/*    */   public static final String TCPTRANSPORT_CONNECTION_MANAGEMENT_NUMBER_TO_RECLAIM_ATTR = "NumberToReclaim";
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\wsit\TCPConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */