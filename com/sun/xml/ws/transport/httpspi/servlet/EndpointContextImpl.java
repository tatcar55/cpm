/*    */ package com.sun.xml.ws.transport.httpspi.servlet;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import javax.xml.ws.Endpoint;
/*    */ import javax.xml.ws.EndpointContext;
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
/*    */ public class EndpointContextImpl
/*    */   extends EndpointContext
/*    */ {
/* 53 */   private final Set<Endpoint> set = new HashSet<Endpoint>();
/*    */   
/*    */   void add(Endpoint endpoint) {
/* 56 */     this.set.add(endpoint);
/*    */   }
/*    */   
/*    */   public Set<Endpoint> getEndpoints() {
/* 60 */     return this.set;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\EndpointContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */