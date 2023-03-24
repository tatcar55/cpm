/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.ws.ProtocolException;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class SOAPHandlerProcessor<C extends MessageUpdatableContext>
/*     */   extends HandlerProcessor<C>
/*     */ {
/*     */   public SOAPHandlerProcessor(boolean isClient, HandlerTube owner, WSBinding binding, List<? extends Handler> chain) {
/*  73 */     super(owner, binding, chain);
/*  74 */     this.isClient = isClient;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void insertFaultMessage(C context, ProtocolException exception) {
/*     */     try {
/*  94 */       if (!context.getPacketMessage().isFault()) {
/*  95 */         Message faultMessage = Messages.create(this.binding.getSOAPVersion(), exception, determineFaultCode(this.binding.getSOAPVersion()));
/*     */         
/*  97 */         context.setPacketMessage(faultMessage);
/*     */       } 
/*  99 */     } catch (Exception e) {
/*     */       
/* 101 */       logger.log(Level.SEVERE, "exception while creating fault message in handler chain", e);
/*     */       
/* 103 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private QName determineFaultCode(SOAPVersion soapVersion) {
/* 113 */     return this.isClient ? soapVersion.faultCodeClient : soapVersion.faultCodeServer;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\SOAPHandlerProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */