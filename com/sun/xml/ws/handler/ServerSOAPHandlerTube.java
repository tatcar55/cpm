/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.client.HandlerConfiguration;
/*     */ import com.sun.xml.ws.message.DataHandlerAttachment;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.handler.MessageContext;
/*     */ import javax.xml.ws.handler.soap.SOAPHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerSOAPHandlerTube
/*     */   extends HandlerTube
/*     */ {
/*     */   private Set<String> roles;
/*     */   
/*     */   public ServerSOAPHandlerTube(WSBinding binding, WSDLPort port, Tube next) {
/*  74 */     super(next, port, binding);
/*  75 */     if (binding.getSOAPVersion() != null);
/*     */ 
/*     */ 
/*     */     
/*  79 */     setUpHandlersOnce();
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
/*     */   public ServerSOAPHandlerTube(WSBinding binding, Tube next, HandlerTube cousinTube) {
/*  91 */     super(next, cousinTube, binding);
/*  92 */     setUpHandlersOnce();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerSOAPHandlerTube(ServerSOAPHandlerTube that, TubeCloner cloner) {
/*  99 */     super(that, cloner);
/* 100 */     this.handlers = that.handlers;
/* 101 */     this.roles = that.roles;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractFilterTubeImpl copy(TubeCloner cloner) {
/* 106 */     return new ServerSOAPHandlerTube(this, cloner);
/*     */   }
/*     */   
/*     */   private void setUpHandlersOnce() {
/* 110 */     this.handlers = new ArrayList<Handler>();
/* 111 */     HandlerConfiguration handlerConfig = ((BindingImpl)getBinding()).getHandlerConfig();
/* 112 */     List<SOAPHandler> soapSnapShot = handlerConfig.getSoapHandlers();
/* 113 */     if (!soapSnapShot.isEmpty()) {
/* 114 */       this.handlers.addAll((Collection)soapSnapShot);
/* 115 */       this.roles = new HashSet<String>();
/* 116 */       this.roles.addAll(handlerConfig.getRoles());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void resetProcessor() {
/* 121 */     this.processor = null;
/*     */   }
/*     */   
/*     */   void setUpProcessor() {
/* 125 */     if (!this.handlers.isEmpty() && this.processor == null)
/* 126 */       this.processor = new SOAPHandlerProcessor<MessageUpdatableContext>(false, this, getBinding(), this.handlers); 
/*     */   }
/*     */   MessageUpdatableContext getContext(Packet packet) {
/* 129 */     SOAPMessageContextImpl context = new SOAPMessageContextImpl(getBinding(), packet, this.roles);
/* 130 */     return context;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {
/*     */     boolean handlerResult;
/*     */     try {
/* 138 */       handlerResult = this.processor.callHandlersRequest(HandlerProcessor.Direction.INBOUND, context, !isOneWay);
/*     */     }
/* 140 */     catch (RuntimeException re) {
/* 141 */       this.remedyActionTaken = true;
/* 142 */       throw re;
/*     */     } 
/*     */     
/* 145 */     if (!handlerResult) {
/* 146 */       this.remedyActionTaken = true;
/*     */     }
/* 148 */     return handlerResult;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void callHandlersOnResponse(MessageUpdatableContext context, boolean handleFault) {
/* 154 */     Map<String, DataHandler> atts = (Map<String, DataHandler>)context.get("javax.xml.ws.binding.attachments.outbound");
/* 155 */     AttachmentSet attSet = context.packet.getMessage().getAttachments();
/* 156 */     for (Map.Entry<String, DataHandler> entry : atts.entrySet()) {
/* 157 */       String cid = entry.getKey();
/* 158 */       if (attSet.get(cid) == null) {
/* 159 */         DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(cid, atts.get(cid));
/* 160 */         attSet.add((Attachment)dataHandlerAttachment);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 166 */       this.processor.callHandlersResponse(HandlerProcessor.Direction.OUTBOUND, context, handleFault);
/*     */     }
/* 168 */     catch (WebServiceException wse) {
/*     */       
/* 170 */       throw wse;
/* 171 */     } catch (RuntimeException re) {
/* 172 */       throw re;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void closeHandlers(MessageContext mc) {
/* 178 */     closeServersideHandlers(mc);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\ServerSOAPHandlerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */