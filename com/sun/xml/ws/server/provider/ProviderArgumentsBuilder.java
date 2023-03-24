/*     */ package com.sun.xml.ws.server.provider;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
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
/*     */ 
/*     */ 
/*     */ public abstract class ProviderArgumentsBuilder<T>
/*     */ {
/*     */   protected abstract Message getResponseMessage(Exception paramException);
/*     */   
/*     */   protected Packet getResponse(Packet request, Exception e, WSDLPort port, WSBinding binding) {
/*  69 */     Message message = getResponseMessage(e);
/*  70 */     Packet response = request.createServerResponse(message, port, null, binding);
/*  71 */     return response;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract T getParameter(Packet paramPacket);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Message getResponseMessage(T paramT);
/*     */ 
/*     */ 
/*     */   
/*     */   protected Packet getResponse(Packet request, @Nullable T returnValue, WSDLPort port, WSBinding binding) {
/*  86 */     Message message = null;
/*  87 */     if (returnValue != null) {
/*  88 */       message = getResponseMessage(returnValue);
/*     */     }
/*  90 */     Packet response = request.createServerResponse(message, port, null, binding);
/*  91 */     return response;
/*     */   }
/*     */   
/*     */   public static ProviderArgumentsBuilder<?> create(ProviderEndpointModel model, WSBinding binding) {
/*  95 */     if (model.datatype == Packet.class)
/*  96 */       return new PacketProviderArgumentsBuilder(binding.getSOAPVersion()); 
/*  97 */     return (binding instanceof javax.xml.ws.soap.SOAPBinding) ? SOAPProviderArgumentBuilder.create(model, binding.getSOAPVersion()) : XMLProviderArgumentBuilder.createBuilder(model, binding);
/*     */   }
/*     */   
/*     */   private static class PacketProviderArgumentsBuilder
/*     */     extends ProviderArgumentsBuilder<Packet> {
/*     */     private final SOAPVersion soapVersion;
/*     */     
/*     */     public PacketProviderArgumentsBuilder(SOAPVersion soapVersion) {
/* 105 */       this.soapVersion = soapVersion;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected Message getResponseMessage(Exception e) {
/* 111 */       return SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, null, e);
/*     */     }
/*     */ 
/*     */     
/*     */     public Packet getParameter(Packet packet) {
/* 116 */       return packet;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected Message getResponseMessage(Packet returnValue) {
/* 122 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/*     */     
/*     */     protected Packet getResponse(Packet request, @Nullable Packet returnValue, WSDLPort port, WSBinding binding) {
/* 127 */       return returnValue;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\provider\ProviderArgumentsBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */