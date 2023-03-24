/*    */ package com.sun.xml.ws.transport.tcp.servicechannel;
/*    */ 
/*    */ import com.sun.xml.ws.api.server.WSEndpoint;
/*    */ import com.sun.xml.ws.transport.tcp.server.WSTCPModule;
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
/*    */ public class ServiceChannelCreator
/*    */ {
/* 51 */   private static final WSEndpoint<ServiceChannelWSImpl> endpoint = WSTCPModule.getInstance().createServiceChannelEndpoint();
/*    */ 
/*    */   
/*    */   public static WSEndpoint<ServiceChannelWSImpl> getServiceChannelEndpointInstance() {
/* 55 */     return endpoint;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\servicechannel\ServiceChannelCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */