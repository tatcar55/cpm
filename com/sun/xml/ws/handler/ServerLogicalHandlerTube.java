/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
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
/*     */ import com.sun.xml.ws.message.DataHandlerAttachment;
/*     */ import com.sun.xml.ws.model.AbstractSEIModelImpl;
/*     */ import com.sun.xml.ws.spi.db.BindingContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.handler.Handler;
/*     */ import javax.xml.ws.handler.LogicalHandler;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerLogicalHandlerTube
/*     */   extends HandlerTube
/*     */ {
/*     */   private SEIModel seiModel;
/*     */   
/*     */   public ServerLogicalHandlerTube(WSBinding binding, SEIModel seiModel, WSDLPort port, Tube next) {
/*  79 */     super(next, port, binding);
/*  80 */     this.seiModel = seiModel;
/*  81 */     setUpHandlersOnce();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerLogicalHandlerTube(WSBinding binding, SEIModel seiModel, Tube next, HandlerTube cousinTube) {
/*  92 */     super(next, cousinTube, binding);
/*  93 */     this.seiModel = seiModel;
/*  94 */     setUpHandlersOnce();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerLogicalHandlerTube(ServerLogicalHandlerTube that, TubeCloner cloner) {
/* 102 */     super(that, cloner);
/* 103 */     this.seiModel = that.seiModel;
/* 104 */     this.handlers = that.handlers;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initiateClosing(MessageContext mc) {
/* 110 */     if (getBinding().getSOAPVersion() != null) {
/* 111 */       super.initiateClosing(mc);
/*     */     } else {
/* 113 */       close(mc);
/* 114 */       super.initiateClosing(mc);
/*     */     } 
/*     */   }
/*     */   
/*     */   public AbstractFilterTubeImpl copy(TubeCloner cloner) {
/* 119 */     return new ServerLogicalHandlerTube(this, cloner);
/*     */   }
/*     */   
/*     */   private void setUpHandlersOnce() {
/* 123 */     this.handlers = new ArrayList<Handler>();
/* 124 */     List<LogicalHandler> logicalSnapShot = ((BindingImpl)getBinding()).getHandlerConfig().getLogicalHandlers();
/* 125 */     if (!logicalSnapShot.isEmpty()) {
/* 126 */       this.handlers.addAll((Collection)logicalSnapShot);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void resetProcessor() {
/* 131 */     this.processor = null;
/*     */   }
/*     */   
/*     */   void setUpProcessor() {
/* 135 */     if (!this.handlers.isEmpty() && this.processor == null) {
/* 136 */       if (getBinding().getSOAPVersion() == null) {
/* 137 */         this.processor = new XMLHandlerProcessor<MessageUpdatableContext>(this, getBinding(), this.handlers);
/*     */       } else {
/*     */         
/* 140 */         this.processor = new SOAPHandlerProcessor<MessageUpdatableContext>(false, this, getBinding(), this.handlers);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   MessageUpdatableContext getContext(Packet packet) {
/* 146 */     return new LogicalMessageContextImpl(getBinding(), getBindingContext(), packet);
/*     */   }
/*     */   
/*     */   private BindingContext getBindingContext() {
/* 150 */     return (this.seiModel != null && this.seiModel instanceof AbstractSEIModelImpl) ? ((AbstractSEIModelImpl)this.seiModel).getBindingContext() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {
/*     */     boolean handlerResult;
/*     */     try {
/* 159 */       handlerResult = this.processor.callHandlersRequest(HandlerProcessor.Direction.INBOUND, context, !isOneWay);
/*     */     }
/* 161 */     catch (RuntimeException re) {
/* 162 */       this.remedyActionTaken = true;
/* 163 */       throw re;
/*     */     } 
/* 165 */     if (!handlerResult) {
/* 166 */       this.remedyActionTaken = true;
/*     */     }
/* 168 */     return handlerResult;
/*     */   }
/*     */ 
/*     */   
/*     */   void callHandlersOnResponse(MessageUpdatableContext context, boolean handleFault) {
/* 173 */     Map<String, DataHandler> atts = (Map<String, DataHandler>)context.get("javax.xml.ws.binding.attachments.outbound");
/* 174 */     AttachmentSet attSet = context.packet.getMessage().getAttachments();
/* 175 */     for (Map.Entry<String, DataHandler> entry : atts.entrySet()) {
/* 176 */       String cid = entry.getKey();
/* 177 */       DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(cid, atts.get(cid));
/* 178 */       attSet.add((Attachment)dataHandlerAttachment);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 183 */       this.processor.callHandlersResponse(HandlerProcessor.Direction.OUTBOUND, context, handleFault);
/*     */     }
/* 185 */     catch (WebServiceException wse) {
/*     */       
/* 187 */       throw wse;
/* 188 */     } catch (RuntimeException re) {
/* 189 */       throw re;
/*     */     } 
/*     */   }
/*     */   
/*     */   void closeHandlers(MessageContext mc) {
/* 194 */     closeServersideHandlers(mc);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\ServerLogicalHandlerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */