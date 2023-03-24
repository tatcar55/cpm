/*     */ package com.sun.xml.ws.client.dispatch;
/*     */ 
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.message.saaj.SAAJFactory;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.client.WSServiceDelegate;
/*     */ import com.sun.xml.ws.resources.DispatchMessages;
/*     */ import com.sun.xml.ws.transport.Headers;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.MimeHeader;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
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
/*     */ public class SOAPMessageDispatch
/*     */   extends DispatchImpl<SOAPMessage>
/*     */ {
/*     */   @Deprecated
/*     */   public SOAPMessageDispatch(QName port, Service.Mode mode, WSServiceDelegate owner, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/*  78 */     super(port, mode, owner, pipe, binding, epr);
/*     */   }
/*     */   
/*     */   public SOAPMessageDispatch(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, WSEndpointReference epr) {
/*  82 */     super(portInfo, mode, binding, epr);
/*     */   }
/*     */   
/*     */   Packet createPacket(SOAPMessage arg) {
/*  86 */     Iterator<MimeHeader> iter = arg.getMimeHeaders().getAllHeaders();
/*  87 */     Headers ch = new Headers();
/*  88 */     while (iter.hasNext()) {
/*  89 */       MimeHeader mh = iter.next();
/*  90 */       ch.add(mh.getName(), mh.getValue());
/*     */     } 
/*  92 */     Packet packet = new Packet(SAAJFactory.create(arg));
/*  93 */     packet.invocationProperties.put("javax.xml.ws.http.request.headers", ch);
/*  94 */     return packet;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   SOAPMessage toReturnValue(Packet response) {
/*     */     try {
/* 101 */       if (response == null || response.getMessage() == null) {
/* 102 */         throw new WebServiceException(DispatchMessages.INVALID_RESPONSE());
/*     */       }
/* 104 */       return response.getMessage().readAsSOAPMessage();
/* 105 */     } catch (SOAPException e) {
/* 106 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\dispatch\SOAPMessageDispatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */