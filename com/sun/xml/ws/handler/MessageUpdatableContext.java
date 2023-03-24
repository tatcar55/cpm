/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public abstract class MessageUpdatableContext
/*     */   implements MessageContext
/*     */ {
/*     */   final Packet packet;
/*     */   private MessageContextImpl ctxt;
/*     */   
/*     */   public MessageUpdatableContext(Packet packet) {
/*  61 */     this.ctxt = new MessageContextImpl(packet);
/*  62 */     this.packet = packet;
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
/*     */   Message getPacketMessage() {
/*  75 */     updateMessage();
/*  76 */     return this.packet.getMessage();
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
/*     */   public final void updatePacket() {
/*  90 */     updateMessage();
/*     */   }
/*     */   
/*     */   MessageContextImpl getMessageContext() {
/*  94 */     return this.ctxt;
/*     */   }
/*     */   
/*     */   public void setScope(String name, MessageContext.Scope scope) {
/*  98 */     this.ctxt.setScope(name, scope);
/*     */   }
/*     */   
/*     */   public MessageContext.Scope getScope(String name) {
/* 102 */     return this.ctxt.getScope(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 108 */     this.ctxt.clear();
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object obj) {
/* 112 */     return this.ctxt.containsKey(obj);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object obj) {
/* 116 */     return this.ctxt.containsValue(obj);
/*     */   }
/*     */   
/*     */   public Set<Map.Entry<String, Object>> entrySet() {
/* 120 */     return this.ctxt.entrySet();
/*     */   }
/*     */   
/*     */   public Object get(Object obj) {
/* 124 */     return this.ctxt.get(obj);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 128 */     return this.ctxt.isEmpty();
/*     */   }
/*     */   
/*     */   public Set<String> keySet() {
/* 132 */     return this.ctxt.keySet();
/*     */   }
/*     */   
/*     */   public Object put(String str, Object obj) {
/* 136 */     return this.ctxt.put(str, obj);
/*     */   }
/*     */   
/*     */   public void putAll(Map<? extends String, ? extends Object> map) {
/* 140 */     this.ctxt.putAll(map);
/*     */   }
/*     */   
/*     */   public Object remove(Object obj) {
/* 144 */     return this.ctxt.remove(obj);
/*     */   }
/*     */   
/*     */   public int size() {
/* 148 */     return this.ctxt.size();
/*     */   }
/*     */   
/*     */   public Collection<Object> values() {
/* 152 */     return this.ctxt.values();
/*     */   }
/*     */   
/*     */   abstract void updateMessage();
/*     */   
/*     */   abstract void setPacketMessage(Message paramMessage);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\MessageUpdatableContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */