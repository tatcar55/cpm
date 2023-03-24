/*    */ package com.sun.xml.ws.config.management.server;
/*    */ 
/*    */ import com.sun.istack.logging.Logger;
/*    */ import com.sun.xml.ws.api.config.management.EndpointCreationAttributes;
/*    */ import com.sun.xml.ws.api.config.management.ManagedEndpointFactory;
/*    */ import com.sun.xml.ws.api.config.management.policy.ManagedServiceAssertion;
/*    */ import com.sun.xml.ws.api.server.WSEndpoint;
/*    */ import com.sun.xml.ws.config.management.ManagementMessages;
/*    */ import com.sun.xml.ws.metro.api.config.management.ManagedEndpoint;
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
/*    */ public class EndpointFactoryImpl
/*    */   implements ManagedEndpointFactory
/*    */ {
/* 59 */   private static final Logger LOGGER = Logger.getLogger(EndpointFactoryImpl.class);
/*    */   
/*    */   public <T> WSEndpoint<T> createEndpoint(WSEndpoint<T> endpoint, EndpointCreationAttributes attributes) {
/* 62 */     ManagedServiceAssertion assertion = ManagedServiceAssertion.getAssertion(endpoint);
/* 63 */     if (assertion != null && !assertion.isManagementEnabled()) {
/* 64 */       LOGGER.config(ManagementMessages.WSM_5002_ENDPOINT_NOT_CREATED());
/* 65 */       return endpoint;
/*    */     } 
/* 67 */     return (WSEndpoint<T>)new ManagedEndpoint(endpoint, attributes);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\management\server\EndpointFactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */