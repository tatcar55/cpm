/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.api.server.WSWebServiceContext;
/*     */ import java.security.Principal;
/*     */ import javax.xml.ws.EndpointReference;
/*     */ import javax.xml.ws.handler.MessageContext;
/*     */ import javax.xml.ws.wsaddressing.W3CEndpointReference;
/*     */ import org.w3c.dom.Element;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractWebServiceContext
/*     */   implements WSWebServiceContext
/*     */ {
/*     */   private final WSEndpoint endpoint;
/*     */   
/*     */   public AbstractWebServiceContext(@NotNull WSEndpoint endpoint) {
/*  70 */     this.endpoint = endpoint;
/*     */   }
/*     */   
/*     */   public MessageContext getMessageContext() {
/*  74 */     Packet packet = getRequestPacket();
/*  75 */     if (packet == null) {
/*  76 */       throw new IllegalStateException("getMessageContext() can only be called while servicing a request");
/*     */     }
/*  78 */     return new EndpointMessageContextImpl(packet);
/*     */   }
/*     */   
/*     */   public Principal getUserPrincipal() {
/*  82 */     Packet packet = getRequestPacket();
/*  83 */     if (packet == null) {
/*  84 */       throw new IllegalStateException("getUserPrincipal() can only be called while servicing a request");
/*     */     }
/*  86 */     return packet.webServiceContextDelegate.getUserPrincipal(packet);
/*     */   }
/*     */   
/*     */   public boolean isUserInRole(String role) {
/*  90 */     Packet packet = getRequestPacket();
/*  91 */     if (packet == null) {
/*  92 */       throw new IllegalStateException("isUserInRole() can only be called while servicing a request");
/*     */     }
/*  94 */     return packet.webServiceContextDelegate.isUserInRole(packet, role);
/*     */   }
/*     */   
/*     */   public EndpointReference getEndpointReference(Element... referenceParameters) {
/*  98 */     return getEndpointReference((Class)W3CEndpointReference.class, referenceParameters);
/*     */   }
/*     */   
/*     */   public <T extends EndpointReference> T getEndpointReference(Class<T> clazz, Element... referenceParameters) {
/* 102 */     Packet packet = getRequestPacket();
/* 103 */     if (packet == null) {
/* 104 */       throw new IllegalStateException("getEndpointReference() can only be called while servicing a request");
/*     */     }
/* 106 */     String address = packet.webServiceContextDelegate.getEPRAddress(packet, this.endpoint);
/* 107 */     String wsdlAddress = null;
/* 108 */     if (this.endpoint.getServiceDefinition() != null) {
/* 109 */       wsdlAddress = packet.webServiceContextDelegate.getWSDLAddress(packet, this.endpoint);
/*     */     }
/* 111 */     return clazz.cast(this.endpoint.getEndpointReference(clazz, address, wsdlAddress, referenceParameters));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\AbstractWebServiceContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */