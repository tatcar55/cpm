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
/*     */ 
/*     */ public class ClientSOAPHandlerTube
/*     */   extends HandlerTube
/*     */ {
/*     */   private Set<String> roles;
/*     */   
/*     */   public ClientSOAPHandlerTube(WSBinding binding, WSDLPort port, Tube next) {
/*  75 */     super(next, port, binding);
/*  76 */     if (binding.getSOAPVersion() != null);
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
/*     */   public ClientSOAPHandlerTube(WSBinding binding, Tube next, HandlerTube cousinTube) {
/*  91 */     super(next, cousinTube, binding);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientSOAPHandlerTube(ClientSOAPHandlerTube that, TubeCloner cloner) {
/*  98 */     super(that, cloner);
/*     */   }
/*     */   
/*     */   public AbstractFilterTubeImpl copy(TubeCloner cloner) {
/* 102 */     return new ClientSOAPHandlerTube(this, cloner);
/*     */   }
/*     */   
/*     */   void setUpProcessor() {
/* 106 */     if (this.handlers == null) {
/*     */ 
/*     */       
/* 109 */       this.handlers = new ArrayList<Handler>();
/* 110 */       HandlerConfiguration handlerConfig = ((BindingImpl)getBinding()).getHandlerConfig();
/* 111 */       List<SOAPHandler> soapSnapShot = handlerConfig.getSoapHandlers();
/* 112 */       if (!soapSnapShot.isEmpty()) {
/* 113 */         this.handlers.addAll((Collection)soapSnapShot);
/* 114 */         this.roles = new HashSet<String>();
/* 115 */         this.roles.addAll(handlerConfig.getRoles());
/* 116 */         this.processor = new SOAPHandlerProcessor<MessageUpdatableContext>(true, this, getBinding(), this.handlers);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   MessageUpdatableContext getContext(Packet packet) {
/* 122 */     SOAPMessageContextImpl context = new SOAPMessageContextImpl(getBinding(), packet, this.roles);
/* 123 */     return context;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   boolean callHandlersOnRequest(MessageUpdatableContext context, boolean isOneWay) {
/*     */     boolean handlerResult;
/* 130 */     Map<String, DataHandler> atts = (Map<String, DataHandler>)context.get("javax.xml.ws.binding.attachments.outbound");
/* 131 */     AttachmentSet attSet = context.packet.getMessage().getAttachments();
/* 132 */     for (Map.Entry<String, DataHandler> entry : atts.entrySet()) {
/* 133 */       String cid = entry.getKey();
/* 134 */       if (attSet.get(cid) == null) {
/* 135 */         DataHandlerAttachment dataHandlerAttachment = new DataHandlerAttachment(cid, atts.get(cid));
/* 136 */         attSet.add((Attachment)dataHandlerAttachment);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 142 */       handlerResult = this.processor.callHandlersRequest(HandlerProcessor.Direction.OUTBOUND, context, !isOneWay);
/* 143 */     } catch (WebServiceException wse) {
/* 144 */       this.remedyActionTaken = true;
/*     */       
/* 146 */       throw wse;
/* 147 */     } catch (RuntimeException re) {
/* 148 */       this.remedyActionTaken = true;
/*     */       
/* 150 */       throw new WebServiceException(re);
/*     */     } 
/*     */     
/* 153 */     if (!handlerResult) {
/* 154 */       this.remedyActionTaken = true;
/*     */     }
/* 156 */     return handlerResult;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void callHandlersOnResponse(MessageUpdatableContext context, boolean handleFault) {
/*     */     try {
/* 163 */       this.processor.callHandlersResponse(HandlerProcessor.Direction.INBOUND, context, handleFault);
/*     */     }
/* 165 */     catch (WebServiceException wse) {
/*     */       
/* 167 */       throw wse;
/* 168 */     } catch (RuntimeException re) {
/* 169 */       throw new WebServiceException(re);
/*     */     } 
/*     */   }
/*     */   
/*     */   void closeHandlers(MessageContext mc) {
/* 174 */     closeClientsideHandlers(mc);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\ClientSOAPHandlerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */