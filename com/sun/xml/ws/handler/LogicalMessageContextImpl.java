/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.spi.db.BindingContext;
/*     */ import javax.xml.ws.LogicalMessage;
/*     */ import javax.xml.ws.handler.LogicalMessageContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LogicalMessageContextImpl
/*     */   extends MessageUpdatableContext
/*     */   implements LogicalMessageContext
/*     */ {
/*     */   private LogicalMessageImpl lm;
/*     */   private WSBinding binding;
/*     */   private BindingContext defaultJaxbContext;
/*     */   
/*     */   public LogicalMessageContextImpl(WSBinding binding, BindingContext defaultJAXBContext, Packet packet) {
/*  75 */     super(packet);
/*  76 */     this.binding = binding;
/*  77 */     this.defaultJaxbContext = defaultJAXBContext;
/*     */   }
/*     */   
/*     */   public LogicalMessage getMessage() {
/*  81 */     if (this.lm == null)
/*  82 */       this.lm = new LogicalMessageImpl(this.defaultJaxbContext, this.packet); 
/*  83 */     return this.lm;
/*     */   }
/*     */   
/*     */   void setPacketMessage(Message newMessage) {
/*  87 */     if (newMessage != null) {
/*  88 */       this.packet.setMessage(newMessage);
/*  89 */       this.lm = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateMessage() {
/*  96 */     if (this.lm != null) {
/*     */ 
/*     */       
/*  99 */       if (this.lm.isPayloadModifed()) {
/* 100 */         Message msg = this.packet.getMessage();
/* 101 */         Message updatedMsg = this.lm.getMessage(msg.getHeaders(), msg.getAttachments(), this.binding);
/* 102 */         this.packet.setMessage(updatedMsg);
/*     */       } 
/* 104 */       this.lm = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\LogicalMessageContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */