/*     */ package com.sun.xml.ws.client.sei;
/*     */ 
/*     */ import com.oracle.webservices.api.databinding.JavaCallInfo;
/*     */ import com.oracle.webservices.api.message.MessageContext;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.client.RequestContext;
/*     */ import com.sun.xml.ws.client.ResponseContextReceiver;
/*     */ import com.sun.xml.ws.encoding.soap.DeserializationException;
/*     */ import java.lang.reflect.Method;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SyncMethodHandler
/*     */   extends MethodHandler
/*     */ {
/*     */   SyncMethodHandler(SEIStub owner, Method m) {
/*  81 */     super(owner, m);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object invoke(Object proxy, Object[] args) throws Throwable {
/*  91 */     return invoke(proxy, args, this.owner.requestContext, (ResponseContextReceiver)this.owner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Object invoke(Object proxy, Object[] args, RequestContext rc, ResponseContextReceiver receiver) throws Throwable {
/* 104 */     JavaCallInfo call = this.owner.databinding.createJavaCallInfo(this.method, args);
/*     */     
/* 106 */     Packet req = (Packet)this.owner.databinding.serializeRequest(call);
/*     */     
/* 108 */     Packet reply = this.owner.doProcess(req, rc, receiver);
/*     */     
/* 110 */     Message msg = reply.getMessage();
/* 111 */     if (msg == null)
/*     */     {
/* 113 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 117 */       call = this.owner.databinding.deserializeResponse((MessageContext)reply, call);
/* 118 */       if (call.getException() != null) {
/* 119 */         throw call.getException();
/*     */       }
/* 121 */       return call.getReturnValue();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 129 */     catch (JAXBException e) {
/* 130 */       throw new DeserializationException("failed.to.read.response", new Object[] { e });
/* 131 */     } catch (XMLStreamException e) {
/* 132 */       throw new DeserializationException("failed.to.read.response", new Object[] { e });
/*     */     } finally {
/* 134 */       if (reply.transportBackChannel != null)
/* 135 */         reply.transportBackChannel.close(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   ValueGetterFactory getValueGetterFactory() {
/* 140 */     return ValueGetterFactory.SYNC;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\sei\SyncMethodHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */