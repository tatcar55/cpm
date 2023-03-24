/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.handler.MessageHandler;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientMessageHandlerTube
/*     */   extends HandlerTube
/*     */ {
/*     */   private SEIModel seiModel;
/*     */   private Set<String> roles;
/*     */   
/*     */   public ClientMessageHandlerTube(@Nullable SEIModel seiModel, WSBinding binding, WSDLPort port, Tube next) {
/*  76 */     super(next, port, binding);
/*  77 */     this.seiModel = seiModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientMessageHandlerTube(ClientMessageHandlerTube that, TubeCloner cloner) {
/*  84 */     super(that, cloner);
/*  85 */     this.seiModel = that.seiModel;
/*     */   }
/*     */   
/*     */   public AbstractFilterTubeImpl copy(TubeCloner cloner) {
/*  89 */     return new ClientMessageHandlerTube(this, cloner);
/*     */   }
/*     */ 
/*     */   
/*     */   void callHandlersOnResponse(MessageUpdatableContext context, boolean handleFault) {
/*     */     try {
/*  95 */       this.processor.callHandlersResponse(HandlerProcessor.Direction.INBOUND, context, handleFault);
/*     */     }
/*  97 */     catch (WebServiceException wse) {
/*     */       
/*  99 */       throw wse;
/* 100 */     } catch (RuntimeException re) {
/* 101 */       throw new WebServiceException(re);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {
/*     */     boolean handlerResult;
/* 109 */     Map<String, DataHandler> atts = (Map<String, DataHandler>)context.get("javax.xml.ws.binding.attachments.outbound");
/* 110 */     AttachmentSet attSet = context.packet.getMessage().getAttachments();
/* 111 */     for (Map.Entry<String, DataHandler> entry : atts.entrySet()) {
/* 112 */       String cid = entry.getKey();
/* 113 */       if (attSet.get(cid) == null) {
/* 114 */         DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(cid, atts.get(cid));
/* 115 */         attSet.add((Attachment)dataHandlerAttachment);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 121 */       handlerResult = this.processor.callHandlersRequest(HandlerProcessor.Direction.OUTBOUND, context, !isOneWay);
/* 122 */     } catch (WebServiceException wse) {
/* 123 */       this.remedyActionTaken = true;
/*     */       
/* 125 */       throw wse;
/* 126 */     } catch (RuntimeException re) {
/* 127 */       this.remedyActionTaken = true;
/*     */       
/* 129 */       throw new WebServiceException(re);
/*     */     } 
/*     */     
/* 132 */     if (!handlerResult) {
/* 133 */       this.remedyActionTaken = true;
/*     */     }
/* 135 */     return handlerResult;
/*     */   }
/*     */   
/*     */   void closeHandlers(MessageContext mc) {
/* 139 */     closeClientsideHandlers(mc);
/*     */   }
/*     */ 
/*     */   
/*     */   void setUpProcessor() {
/* 144 */     if (this.handlers == null) {
/*     */ 
/*     */       
/* 147 */       this.handlers = new ArrayList<Handler>();
/* 148 */       HandlerConfiguration handlerConfig = ((BindingImpl)getBinding()).getHandlerConfig();
/* 149 */       List<MessageHandler> msgHandlersSnapShot = handlerConfig.getMessageHandlers();
/* 150 */       if (!msgHandlersSnapShot.isEmpty()) {
/* 151 */         this.handlers.addAll(msgHandlersSnapShot);
/* 152 */         this.roles = new HashSet<String>();
/* 153 */         this.roles.addAll(handlerConfig.getRoles());
/* 154 */         this.processor = new SOAPHandlerProcessor<MessageUpdatableContext>(true, this, getBinding(), this.handlers);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   MessageUpdatableContext getContext(Packet p) {
/* 162 */     MessageHandlerContextImpl context = new MessageHandlerContextImpl(this.seiModel, getBinding(), this.port, p, this.roles);
/* 163 */     return context;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\ClientMessageHandlerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */