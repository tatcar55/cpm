/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.client.HandlerConfiguration;
/*     */ import java.util.List;
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
/*     */ 
/*     */ public abstract class HandlerTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*     */   HandlerTube cousinTube;
/*     */   protected List<Handler> handlers;
/*     */   HandlerProcessor processor;
/*     */   boolean remedyActionTaken = false;
/*     */   @Nullable
/*     */   protected final WSDLPort port;
/*     */   boolean requestProcessingSucessful = false;
/*     */   private WSBinding binding;
/*     */   private HandlerConfiguration hc;
/*     */   private HandlerTubeExchange exchange;
/*     */   
/*     */   public HandlerTube(Tube next, WSDLPort port, WSBinding binding) {
/*  75 */     super(next);
/*  76 */     this.port = port;
/*  77 */     this.binding = binding;
/*     */   }
/*     */   
/*     */   public HandlerTube(Tube next, HandlerTube cousinTube, WSBinding binding) {
/*  81 */     super(next);
/*  82 */     this.cousinTube = cousinTube;
/*  83 */     this.binding = binding;
/*  84 */     if (cousinTube != null) {
/*  85 */       this.port = cousinTube.port;
/*     */     } else {
/*  87 */       this.port = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected HandlerTube(HandlerTube that, TubeCloner cloner) {
/*  95 */     super(that, cloner);
/*  96 */     if (that.cousinTube != null) {
/*  97 */       this.cousinTube = (HandlerTube)cloner.copy((Tube)that.cousinTube);
/*     */     }
/*  99 */     this.port = that.port;
/* 100 */     this.binding = that.binding;
/*     */   }
/*     */   
/*     */   protected WSBinding getBinding() {
/* 104 */     return this.binding;
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/* 109 */     setupExchange();
/*     */     
/* 111 */     if (isHandleFalse()) {
/*     */ 
/*     */       
/* 114 */       this.remedyActionTaken = true;
/* 115 */       return doInvoke(this.next, request);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 120 */     setUpProcessorInternal();
/*     */     
/* 122 */     MessageUpdatableContext context = getContext(request);
/* 123 */     boolean isOneWay = checkOneWay(request);
/*     */     try {
/* 125 */       if (!isHandlerChainEmpty()) {
/*     */         
/* 127 */         boolean handlerResult = callHandlersOnRequest(context, isOneWay);
/*     */         
/* 129 */         context.updatePacket();
/*     */         
/* 131 */         if (!isOneWay && !handlerResult) {
/* 132 */           return doReturnWith(request);
/*     */         }
/*     */       } 
/* 135 */       this.requestProcessingSucessful = true;
/*     */       
/* 137 */       return doInvoke(this.next, request);
/* 138 */     } catch (RuntimeException re) {
/* 139 */       if (isOneWay) {
/*     */         
/* 141 */         if (request.transportBackChannel != null) {
/* 142 */           request.transportBackChannel.close();
/*     */         }
/* 144 */         request.setMessage(null);
/* 145 */         return doReturnWith(request);
/*     */       } 
/* 147 */       throw re;
/*     */     } finally {
/* 149 */       if (!this.requestProcessingSucessful) {
/* 150 */         initiateClosing(context.getMessageContext());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/* 158 */     setupExchange();
/* 159 */     MessageUpdatableContext context = getContext(response);
/*     */     try {
/* 161 */       if (isHandleFalse() || response.getMessage() == null)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/* 166 */         return doReturnWith(response);
/*     */       }
/*     */       
/* 169 */       setUpProcessorInternal();
/*     */       
/* 171 */       boolean isFault = isHandleFault(response);
/* 172 */       if (!isHandlerChainEmpty())
/*     */       {
/* 174 */         callHandlersOnResponse(context, isFault);
/*     */       }
/*     */     } finally {
/* 177 */       initiateClosing(context.getMessageContext());
/*     */     } 
/*     */     
/* 180 */     context.updatePacket();
/*     */     
/* 182 */     return doReturnWith(response);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NextAction processException(Throwable t) {
/*     */     try {
/* 189 */       return doThrow(t);
/*     */     } finally {
/* 191 */       Packet packet = Fiber.current().getPacket();
/* 192 */       MessageUpdatableContext context = getContext(packet);
/* 193 */       initiateClosing(context.getMessageContext());
/*     */     } 
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
/*     */   
/*     */   protected void initiateClosing(MessageContext mc) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void close(MessageContext msgContext) {
/* 231 */     if (this.requestProcessingSucessful && 
/* 232 */       this.cousinTube != null) {
/* 233 */       this.cousinTube.close(msgContext);
/*     */     }
/*     */ 
/*     */     
/* 237 */     if (this.processor != null) {
/* 238 */       closeHandlers(msgContext);
/*     */     }
/*     */     
/* 241 */     this.exchange = null;
/* 242 */     this.requestProcessingSucessful = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void closeHandlers(MessageContext paramMessageContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void closeClientsideHandlers(MessageContext msgContext) {
/* 258 */     if (this.processor == null)
/*     */       return; 
/* 260 */     if (this.remedyActionTaken) {
/*     */ 
/*     */ 
/*     */       
/* 264 */       this.processor.closeHandlers(msgContext, this.processor.getIndex(), 0);
/* 265 */       this.processor.setIndex(-1);
/*     */       
/* 267 */       this.remedyActionTaken = false;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 272 */       this.processor.closeHandlers(msgContext, this.handlers.size() - 1, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void closeServersideHandlers(MessageContext msgContext) {
/* 281 */     if (this.processor == null)
/*     */       return; 
/* 283 */     if (this.remedyActionTaken) {
/*     */ 
/*     */ 
/*     */       
/* 287 */       this.processor.closeHandlers(msgContext, this.processor.getIndex(), this.handlers.size() - 1);
/* 288 */       this.processor.setIndex(-1);
/*     */       
/* 290 */       this.remedyActionTaken = false;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 295 */       this.processor.closeHandlers(msgContext, 0, this.handlers.size() - 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   abstract void callHandlersOnResponse(MessageUpdatableContext paramMessageUpdatableContext, boolean paramBoolean);
/*     */   
/*     */   abstract boolean callHandlersOnRequest(MessageUpdatableContext paramMessageUpdatableContext, boolean paramBoolean);
/*     */   
/*     */   private boolean checkOneWay(Packet packet) {
/* 305 */     if (this.port != null)
/*     */     {
/* 307 */       return packet.getMessage().isOneWay(this.port);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 313 */     return (packet.expectReply == null || !packet.expectReply.booleanValue());
/*     */   }
/*     */ 
/*     */   
/*     */   private void setUpProcessorInternal() {
/* 318 */     HandlerConfiguration hc = ((BindingImpl)this.binding).getHandlerConfig();
/* 319 */     if (hc != this.hc)
/* 320 */       resetProcessor(); 
/* 321 */     this.hc = hc;
/*     */     
/* 323 */     setUpProcessor();
/*     */   }
/*     */   
/*     */   abstract void setUpProcessor();
/*     */   
/*     */   protected void resetProcessor() {
/* 329 */     this.handlers = null;
/*     */   }
/*     */   
/*     */   public final boolean isHandlerChainEmpty() {
/* 333 */     return this.handlers.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isHandleFault(Packet packet) {
/* 338 */     if (this.cousinTube != null) {
/* 339 */       return this.exchange.isHandleFault();
/*     */     }
/* 341 */     boolean isFault = packet.getMessage().isFault();
/* 342 */     this.exchange.setHandleFault(isFault);
/* 343 */     return isFault;
/*     */   }
/*     */   abstract MessageUpdatableContext getContext(Packet paramPacket);
/*     */   
/*     */   final void setHandleFault() {
/* 348 */     this.exchange.setHandleFault(true);
/*     */   }
/*     */   
/*     */   private boolean isHandleFalse() {
/* 352 */     return this.exchange.isHandleFalse();
/*     */   }
/*     */   
/*     */   final void setHandleFalse() {
/* 356 */     this.exchange.setHandleFalse();
/*     */   }
/*     */   
/*     */   private void setupExchange() {
/* 360 */     if (this.exchange == null) {
/* 361 */       this.exchange = new HandlerTubeExchange();
/* 362 */       if (this.cousinTube != null) {
/* 363 */         this.cousinTube.exchange = this.exchange;
/*     */       }
/*     */     }
/* 366 */     else if (this.cousinTube != null) {
/* 367 */       this.cousinTube.exchange = this.exchange;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class HandlerTubeExchange
/*     */   {
/*     */     private boolean handleFalse;
/*     */ 
/*     */     
/*     */     private boolean handleFault;
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isHandleFault() {
/* 383 */       return this.handleFault;
/*     */     }
/*     */     
/*     */     void setHandleFault(boolean isFault) {
/* 387 */       this.handleFault = isFault;
/*     */     }
/*     */     
/*     */     public boolean isHandleFalse() {
/* 391 */       return this.handleFalse;
/*     */     }
/*     */     
/*     */     void setHandleFalse() {
/* 395 */       this.handleFalse = true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\HandlerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */