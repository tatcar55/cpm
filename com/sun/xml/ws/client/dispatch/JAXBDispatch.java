/*     */ package com.sun.xml.ws.client.dispatch;
/*     */ 
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.Headers;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.client.WSServiceDelegate;
/*     */ import com.sun.xml.ws.message.jaxb.JAXBDispatchMessage;
/*     */ import com.sun.xml.ws.spi.db.BindingContextFactory;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public class JAXBDispatch
/*     */   extends DispatchImpl<Object>
/*     */ {
/*     */   private final JAXBContext jaxbcontext;
/*     */   private final boolean isContextSupported;
/*     */   
/*     */   @Deprecated
/*     */   public JAXBDispatch(QName port, JAXBContext jc, Service.Mode mode, WSServiceDelegate service, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/*  86 */     super(port, mode, service, pipe, binding, epr);
/*  87 */     this.jaxbcontext = jc;
/*  88 */     this.isContextSupported = BindingContextFactory.isContextSupported(jc);
/*     */   }
/*     */   
/*     */   public JAXBDispatch(WSPortInfo portInfo, JAXBContext jc, Service.Mode mode, BindingImpl binding, WSEndpointReference epr) {
/*  92 */     super(portInfo, mode, binding, epr);
/*  93 */     this.jaxbcontext = jc;
/*  94 */     this.isContextSupported = BindingContextFactory.isContextSupported(jc);
/*     */   }
/*     */   Object toReturnValue(Packet response) {
/*     */     try {
/*     */       Source result;
/*  99 */       Unmarshaller unmarshaller = this.jaxbcontext.createUnmarshaller();
/* 100 */       Message msg = response.getMessage();
/* 101 */       switch (this.mode) {
/*     */         case PAYLOAD:
/* 103 */           return msg.readPayloadAsJAXB(unmarshaller);
/*     */         case MESSAGE:
/* 105 */           result = msg.readEnvelopeAsSource();
/* 106 */           return unmarshaller.unmarshal(result);
/*     */       } 
/* 108 */       throw new WebServiceException("Unrecognized dispatch mode");
/*     */     }
/* 110 */     catch (JAXBException e) {
/* 111 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   Packet createPacket(Object msg) {
/*     */     Message message;
/* 117 */     assert this.jaxbcontext != null;
/*     */ 
/*     */     
/* 120 */     if (this.mode == Service.Mode.MESSAGE) {
/* 121 */       JAXBDispatchMessage jAXBDispatchMessage = this.isContextSupported ? new JAXBDispatchMessage(BindingContextFactory.create(this.jaxbcontext), msg, this.soapVersion) : new JAXBDispatchMessage(this.jaxbcontext, msg, this.soapVersion);
/*     */ 
/*     */     
/*     */     }
/* 125 */     else if (msg == null) {
/* 126 */       message = Messages.createEmpty(this.soapVersion);
/*     */     } else {
/* 128 */       message = this.isContextSupported ? Messages.create(this.jaxbcontext, msg, this.soapVersion) : Messages.createRaw(this.jaxbcontext, msg, this.soapVersion);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     return new Packet(message);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOutboundHeaders(Object... headers) {
/* 139 */     if (headers == null)
/* 140 */       throw new IllegalArgumentException(); 
/* 141 */     Header[] hl = new Header[headers.length];
/* 142 */     for (int i = 0; i < hl.length; i++) {
/* 143 */       if (headers[i] == null) {
/* 144 */         throw new IllegalArgumentException();
/*     */       }
/* 146 */       hl[i] = Headers.create(this.jaxbcontext, headers[i]);
/*     */     } 
/* 148 */     setOutboundHeaders(hl);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\dispatch\JAXBDispatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */