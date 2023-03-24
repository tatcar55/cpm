/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.model.AbstractSEIModelImpl;
/*     */ import com.sun.xml.ws.spi.db.BindingContext;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
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
/*     */ public class ClientLogicalHandlerTube
/*     */   extends HandlerTube
/*     */ {
/*     */   private SEIModel seiModel;
/*     */   
/*     */   public ClientLogicalHandlerTube(WSBinding binding, SEIModel seiModel, WSDLPort port, Tube next) {
/*  73 */     super(next, port, binding);
/*  74 */     this.seiModel = seiModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientLogicalHandlerTube(WSBinding binding, SEIModel seiModel, Tube next, HandlerTube cousinTube) {
/*  85 */     super(next, cousinTube, binding);
/*  86 */     this.seiModel = seiModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientLogicalHandlerTube(ClientLogicalHandlerTube that, TubeCloner cloner) {
/*  94 */     super(that, cloner);
/*  95 */     this.seiModel = that.seiModel;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initiateClosing(MessageContext mc) {
/* 101 */     close(mc);
/* 102 */     super.initiateClosing(mc);
/*     */   }
/*     */   
/*     */   public AbstractFilterTubeImpl copy(TubeCloner cloner) {
/* 106 */     return new ClientLogicalHandlerTube(this, cloner);
/*     */   }
/*     */   
/*     */   void setUpProcessor() {
/* 110 */     if (this.handlers == null) {
/*     */ 
/*     */       
/* 113 */       this.handlers = new ArrayList<Handler>();
/* 114 */       WSBinding binding = getBinding();
/* 115 */       List<LogicalHandler> logicalSnapShot = ((BindingImpl)binding).getHandlerConfig().getLogicalHandlers();
/* 116 */       if (!logicalSnapShot.isEmpty()) {
/* 117 */         this.handlers.addAll((Collection)logicalSnapShot);
/* 118 */         if (binding.getSOAPVersion() == null) {
/* 119 */           this.processor = new XMLHandlerProcessor<MessageUpdatableContext>(this, binding, this.handlers);
/*     */         } else {
/*     */           
/* 122 */           this.processor = new SOAPHandlerProcessor<MessageUpdatableContext>(true, this, binding, this.handlers);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   MessageUpdatableContext getContext(Packet packet) {
/* 131 */     return new LogicalMessageContextImpl(getBinding(), getBindingContext(), packet);
/*     */   }
/*     */   
/*     */   private BindingContext getBindingContext() {
/* 135 */     return (this.seiModel != null && this.seiModel instanceof AbstractSEIModelImpl) ? ((AbstractSEIModelImpl)this.seiModel).getBindingContext() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {
/*     */     boolean handlerResult;
/*     */     try {
/* 145 */       handlerResult = this.processor.callHandlersRequest(HandlerProcessor.Direction.OUTBOUND, context, !isOneWay);
/* 146 */     } catch (WebServiceException wse) {
/* 147 */       this.remedyActionTaken = true;
/*     */       
/* 149 */       throw wse;
/* 150 */     } catch (RuntimeException re) {
/* 151 */       this.remedyActionTaken = true;
/*     */       
/* 153 */       throw new WebServiceException(re);
/*     */     } 
/*     */     
/* 156 */     if (!handlerResult) {
/* 157 */       this.remedyActionTaken = true;
/*     */     }
/* 159 */     return handlerResult;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void callHandlersOnResponse(MessageUpdatableContext context, boolean handleFault) {
/*     */     try {
/* 166 */       this.processor.callHandlersResponse(HandlerProcessor.Direction.INBOUND, context, handleFault);
/*     */     }
/* 168 */     catch (WebServiceException wse) {
/*     */       
/* 170 */       throw wse;
/* 171 */     } catch (RuntimeException re) {
/*     */       
/* 173 */       throw new WebServiceException(re);
/*     */     } 
/*     */   }
/*     */   
/*     */   void closeHandlers(MessageContext mc) {
/* 178 */     closeClientsideHandlers(mc);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\ClientLogicalHandlerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */