/*     */ package com.sun.xml.ws.client.dispatch;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.client.WSServiceDelegate;
/*     */ import com.sun.xml.ws.message.source.PayloadSourceMessage;
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
/*     */ final class SOAPSourceDispatch
/*     */   extends DispatchImpl<Source>
/*     */ {
/*     */   @Deprecated
/*     */   public SOAPSourceDispatch(QName port, Service.Mode mode, WSServiceDelegate owner, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/*  74 */     super(port, mode, owner, pipe, binding, epr);
/*  75 */     assert !isXMLHttp((WSBinding)binding);
/*     */   }
/*     */   
/*     */   public SOAPSourceDispatch(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, WSEndpointReference epr) {
/*  79 */     super(portInfo, mode, binding, epr);
/*  80 */     assert !isXMLHttp((WSBinding)binding);
/*     */   }
/*     */ 
/*     */   
/*     */   Source toReturnValue(Packet response) {
/*  85 */     Message msg = response.getMessage();
/*     */     
/*  87 */     switch (this.mode) {
/*     */       case PAYLOAD:
/*  89 */         return msg.readPayloadAsSource();
/*     */       case MESSAGE:
/*  91 */         return msg.readEnvelopeAsSource();
/*     */     } 
/*  93 */     throw new WebServiceException("Unrecognized dispatch mode");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Packet createPacket(Source msg) {
/*     */     Message message;
/* 102 */     if (msg == null)
/* 103 */     { message = Messages.createEmpty(this.soapVersion); }
/*     */     else
/* 105 */     { PayloadSourceMessage payloadSourceMessage; switch (this.mode)
/*     */       { case PAYLOAD:
/* 107 */           payloadSourceMessage = new PayloadSourceMessage(null, msg, setOutboundAttachments(), this.soapVersion);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 117 */           return new Packet((Message)payloadSourceMessage);case MESSAGE: message = Messages.create(msg, this.soapVersion); return new Packet(message); }  throw new WebServiceException("Unrecognized message mode"); }  return new Packet(message);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\dispatch\SOAPSourceDispatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */