/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.handler.MessageHandler;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.client.HandlerConfiguration;
/*     */ import com.sun.xml.ws.message.DataHandlerAttachment;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.handler.MessageContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerMessageHandlerTube
/*     */   extends HandlerTube
/*     */ {
/*     */   private SEIModel seiModel;
/*     */   private Set<String> roles;
/*     */   
/*     */   public ServerMessageHandlerTube(SEIModel seiModel, WSBinding binding, Tube next, HandlerTube cousinTube) {
/*  71 */     super(next, cousinTube, binding);
/*  72 */     this.seiModel = seiModel;
/*  73 */     setUpHandlersOnce();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerMessageHandlerTube(ServerMessageHandlerTube that, TubeCloner cloner) {
/*  80 */     super(that, cloner);
/*  81 */     this.seiModel = that.seiModel;
/*  82 */     this.handlers = that.handlers;
/*  83 */     this.roles = that.roles;
/*     */   }
/*     */   
/*     */   private void setUpHandlersOnce() {
/*  87 */     this.handlers = new ArrayList<Handler>();
/*  88 */     HandlerConfiguration handlerConfig = ((BindingImpl)getBinding()).getHandlerConfig();
/*  89 */     List<MessageHandler> msgHandlersSnapShot = handlerConfig.getMessageHandlers();
/*  90 */     if (!msgHandlersSnapShot.isEmpty()) {
/*  91 */       this.handlers.addAll(msgHandlersSnapShot);
/*  92 */       this.roles = new HashSet<String>();
/*  93 */       this.roles.addAll(handlerConfig.getRoles());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void callHandlersOnResponse(MessageUpdatableContext context, boolean handleFault) {
/*  99 */     Map<String, DataHandler> atts = (Map<String, DataHandler>)context.get("javax.xml.ws.binding.attachments.outbound");
/* 100 */     AttachmentSet attSet = context.packet.getMessage().getAttachments();
/* 101 */     for (Map.Entry<String, DataHandler> entry : atts.entrySet()) {
/* 102 */       String cid = entry.getKey();
/* 103 */       if (attSet.get(cid) == null) {
/* 104 */         DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(cid, atts.get(cid));
/* 105 */         attSet.add((Attachment)dataHandlerAttachment);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 111 */       this.processor.callHandlersResponse(HandlerProcessor.Direction.OUTBOUND, context, handleFault);
/*     */     }
/* 113 */     catch (WebServiceException wse) {
/*     */       
/* 115 */       throw wse;
/* 116 */     } catch (RuntimeException re) {
/* 117 */       throw re;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {
/*     */     boolean handlerResult;
/*     */     try {
/* 126 */       handlerResult = this.processor.callHandlersRequest(HandlerProcessor.Direction.INBOUND, context, !isOneWay);
/*     */     }
/* 128 */     catch (RuntimeException re) {
/* 129 */       this.remedyActionTaken = true;
/* 130 */       throw re;
/*     */     } 
/*     */     
/* 133 */     if (!handlerResult) {
/* 134 */       this.remedyActionTaken = true;
/*     */     }
/* 136 */     return handlerResult;
/*     */   }
/*     */   
/*     */   protected void resetProcessor() {
/* 140 */     this.processor = null;
/*     */   }
/*     */   
/*     */   void setUpProcessor() {
/* 144 */     if (!this.handlers.isEmpty() && this.processor == null) {
/* 145 */       this.processor = new SOAPHandlerProcessor<MessageUpdatableContext>(false, this, getBinding(), this.handlers);
/*     */     }
/*     */   }
/*     */   
/*     */   void closeHandlers(MessageContext mc) {
/* 150 */     closeServersideHandlers(mc);
/*     */   }
/*     */   
/*     */   MessageUpdatableContext getContext(Packet packet) {
/* 154 */     MessageHandlerContextImpl context = new MessageHandlerContextImpl(this.seiModel, getBinding(), this.port, packet, this.roles);
/* 155 */     return context;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initiateClosing(MessageContext mc) {
/* 161 */     close(mc);
/* 162 */     super.initiateClosing(mc);
/*     */   }
/*     */   
/*     */   public AbstractFilterTubeImpl copy(TubeCloner cloner) {
/* 166 */     return new ServerMessageHandlerTube(this, cloner);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\ServerMessageHandlerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */