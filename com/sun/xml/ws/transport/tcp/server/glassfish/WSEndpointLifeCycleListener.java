/*    */ package com.sun.xml.ws.transport.tcp.server.glassfish;
/*    */ 
/*    */ import com.sun.enterprise.webservice.monitoring.Endpoint;
/*    */ import com.sun.enterprise.webservice.monitoring.EndpointLifecycleListener;
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
/*    */ public final class WSEndpointLifeCycleListener
/*    */   implements EndpointLifecycleListener
/*    */ {
/*    */   public void endpointAdded(Endpoint endpoint) {
/* 51 */     AppServWSRegistry.getInstance().registerEndpoint(endpoint);
/*    */   }
/*    */   
/*    */   public void endpointRemoved(Endpoint endpoint) {
/* 55 */     AppServWSRegistry.getInstance().deregisterEndpoint(endpoint);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\server\glassfish\WSEndpointLifeCycleListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */