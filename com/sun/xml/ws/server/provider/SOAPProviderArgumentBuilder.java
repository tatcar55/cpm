/*     */ package com.sun.xml.ws.server.provider;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.ws.resources.ServerMessages;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.soap.MimeHeader;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ abstract class SOAPProviderArgumentBuilder<T>
/*     */   extends ProviderArgumentsBuilder<T>
/*     */ {
/*     */   protected final SOAPVersion soapVersion;
/*     */   
/*     */   private SOAPProviderArgumentBuilder(SOAPVersion soapVersion) {
/*  73 */     this.soapVersion = soapVersion;
/*     */   }
/*     */   
/*     */   static ProviderArgumentsBuilder create(ProviderEndpointModel model, SOAPVersion soapVersion) {
/*  77 */     if (model.mode == Service.Mode.PAYLOAD) {
/*  78 */       return new PayloadSource(soapVersion);
/*     */     }
/*  80 */     if (model.datatype == Source.class)
/*  81 */       return new MessageSource(soapVersion); 
/*  82 */     if (model.datatype == SOAPMessage.class)
/*  83 */       return new SOAPMessageParameter(soapVersion); 
/*  84 */     if (model.datatype == Message.class)
/*  85 */       return new MessageProviderArgumentBuilder(soapVersion); 
/*  86 */     throw new WebServiceException(ServerMessages.PROVIDER_INVALID_PARAMETER_TYPE(model.implClass, model.datatype));
/*     */   }
/*     */   
/*     */   private static final class PayloadSource
/*     */     extends SOAPProviderArgumentBuilder<Source> {
/*     */     PayloadSource(SOAPVersion soapVersion) {
/*  92 */       super(soapVersion);
/*     */     }
/*     */     
/*     */     public Source getParameter(Packet packet) {
/*  96 */       return packet.getMessage().readPayloadAsSource();
/*     */     }
/*     */     
/*     */     protected Message getResponseMessage(Source source) {
/* 100 */       return Messages.createUsingPayload(source, this.soapVersion);
/*     */     }
/*     */     
/*     */     protected Message getResponseMessage(Exception e) {
/* 104 */       return SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, null, e);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class MessageSource
/*     */     extends SOAPProviderArgumentBuilder<Source> {
/*     */     MessageSource(SOAPVersion soapVersion) {
/* 111 */       super(soapVersion);
/*     */     }
/*     */     
/*     */     public Source getParameter(Packet packet) {
/* 115 */       return packet.getMessage().readEnvelopeAsSource();
/*     */     }
/*     */     
/*     */     protected Message getResponseMessage(Source source) {
/* 119 */       return Messages.create(source, this.soapVersion);
/*     */     }
/*     */     
/*     */     protected Message getResponseMessage(Exception e) {
/* 123 */       return SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, null, e);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class SOAPMessageParameter extends SOAPProviderArgumentBuilder<SOAPMessage> {
/*     */     SOAPMessageParameter(SOAPVersion soapVersion) {
/* 129 */       super(soapVersion);
/*     */     }
/*     */     
/*     */     public SOAPMessage getParameter(Packet packet) {
/*     */       try {
/* 134 */         return packet.getMessage().readAsSOAPMessage(packet, true);
/* 135 */       } catch (SOAPException se) {
/* 136 */         throw new WebServiceException(se);
/*     */       } 
/*     */     }
/*     */     
/*     */     protected Message getResponseMessage(SOAPMessage soapMsg) {
/* 141 */       return Messages.create(soapMsg);
/*     */     }
/*     */     
/*     */     protected Message getResponseMessage(Exception e) {
/* 145 */       return SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, null, e);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Packet getResponse(Packet request, @Nullable SOAPMessage returnValue, WSDLPort port, WSBinding binding) {
/* 150 */       Packet response = super.getResponse(request, returnValue, port, binding);
/*     */       
/* 152 */       if (returnValue != null && response.supports("com.sun.xml.ws.api.message.packet.outbound.transport.headers")) {
/* 153 */         MimeHeaders hdrs = returnValue.getMimeHeaders();
/* 154 */         Map<String, List<String>> headers = new HashMap<String, List<String>>();
/* 155 */         Iterator<MimeHeader> i = hdrs.getAllHeaders();
/* 156 */         while (i.hasNext()) {
/* 157 */           MimeHeader header = i.next();
/* 158 */           if (header.getName().equalsIgnoreCase("SOAPAction")) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 163 */           List<String> list = headers.get(header.getName());
/* 164 */           if (list == null) {
/* 165 */             list = new ArrayList<String>();
/* 166 */             headers.put(header.getName(), list);
/*     */           } 
/* 168 */           list.add(header.getValue());
/*     */         } 
/* 170 */         response.put("com.sun.xml.ws.api.message.packet.outbound.transport.headers", headers);
/*     */       } 
/* 172 */       return response;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\provider\SOAPProviderArgumentBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */