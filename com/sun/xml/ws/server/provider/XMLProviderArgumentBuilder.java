/*     */ package com.sun.xml.ws.server.provider;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.encoding.xml.XMLMessage;
/*     */ import com.sun.xml.ws.resources.ServerMessages;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.http.HTTPException;
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
/*     */ abstract class XMLProviderArgumentBuilder<T>
/*     */   extends ProviderArgumentsBuilder<T>
/*     */ {
/*     */   protected Packet getResponse(Packet request, Exception e, WSDLPort port, WSBinding binding) {
/*  66 */     Packet response = super.getResponse(request, e, port, binding);
/*  67 */     if (e instanceof HTTPException && 
/*  68 */       response.supports("javax.xml.ws.http.response.code")) {
/*  69 */       response.put("javax.xml.ws.http.response.code", Integer.valueOf(((HTTPException)e).getStatusCode()));
/*     */     }
/*     */     
/*  72 */     return response;
/*     */   }
/*     */   
/*     */   static XMLProviderArgumentBuilder createBuilder(ProviderEndpointModel model, WSBinding binding) {
/*  76 */     if (model.mode == Service.Mode.PAYLOAD) {
/*  77 */       return new PayloadSource();
/*     */     }
/*  79 */     if (model.datatype == Source.class)
/*  80 */       return new PayloadSource(); 
/*  81 */     if (model.datatype == DataSource.class)
/*  82 */       return new DataSourceParameter(binding); 
/*  83 */     throw new WebServiceException(ServerMessages.PROVIDER_INVALID_PARAMETER_TYPE(model.implClass, model.datatype));
/*     */   }
/*     */   
/*     */   private static final class PayloadSource
/*     */     extends XMLProviderArgumentBuilder<Source> {
/*     */     public Source getParameter(Packet packet) {
/*  89 */       return packet.getMessage().readPayloadAsSource();
/*     */     }
/*     */     private PayloadSource() {}
/*     */     public Message getResponseMessage(Source source) {
/*  93 */       return Messages.createUsingPayload(source, SOAPVersion.SOAP_11);
/*     */     }
/*     */     
/*     */     protected Message getResponseMessage(Exception e) {
/*  97 */       return XMLMessage.create(e);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DataSourceParameter extends XMLProviderArgumentBuilder<DataSource> {
/*     */     private final WSBinding binding;
/*     */     
/*     */     DataSourceParameter(WSBinding binding) {
/* 105 */       this.binding = binding;
/*     */     }
/*     */     public DataSource getParameter(Packet packet) {
/* 108 */       Message msg = packet.getInternalMessage();
/* 109 */       return (msg instanceof XMLMessage.MessageDataSource) ? ((XMLMessage.MessageDataSource)msg).getDataSource() : XMLMessage.getDataSource(msg, this.binding.getFeatures());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Message getResponseMessage(DataSource ds) {
/* 115 */       return XMLMessage.create(ds, this.binding.getFeatures());
/*     */     }
/*     */     
/*     */     protected Message getResponseMessage(Exception e) {
/* 119 */       return XMLMessage.create(e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\provider\XMLProviderArgumentBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */