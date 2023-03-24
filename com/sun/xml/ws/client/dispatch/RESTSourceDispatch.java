/*     */ package com.sun.xml.ws.client.dispatch;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.client.WSServiceDelegate;
/*     */ import com.sun.xml.ws.encoding.xml.XMLMessage;
/*     */ import com.sun.xml.ws.message.source.PayloadSourceMessage;
/*     */ import java.io.IOException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.ws.Service;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class RESTSourceDispatch
/*     */   extends DispatchImpl<Source>
/*     */ {
/*     */   @Deprecated
/*     */   public RESTSourceDispatch(QName port, Service.Mode mode, WSServiceDelegate owner, Tube pipe, BindingImpl binding, WSEndpointReference epr) {
/*  73 */     super(port, mode, owner, pipe, binding, epr);
/*  74 */     assert isXMLHttp((WSBinding)binding);
/*     */   }
/*     */   
/*     */   public RESTSourceDispatch(WSPortInfo portInfo, Service.Mode mode, BindingImpl binding, WSEndpointReference epr) {
/*  78 */     super(portInfo, mode, binding, epr);
/*  79 */     assert isXMLHttp((WSBinding)binding);
/*     */   }
/*     */ 
/*     */   
/*     */   Source toReturnValue(Packet response) {
/*  84 */     Message msg = response.getMessage();
/*     */     try {
/*  86 */       return new StreamSource(XMLMessage.getDataSource(msg, (WSFeatureList)this.binding.getFeatures()).getInputStream());
/*  87 */     } catch (IOException e) {
/*  88 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   Packet createPacket(Source msg) {
/*     */     PayloadSourceMessage payloadSourceMessage;
/*  96 */     if (msg == null) {
/*  97 */       Message message = Messages.createEmpty(this.soapVersion);
/*     */     } else {
/*  99 */       payloadSourceMessage = new PayloadSourceMessage(null, msg, setOutboundAttachments(), this.soapVersion);
/*     */     } 
/* 101 */     return new Packet((Message)payloadSourceMessage);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\dispatch\RESTSourceDispatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */