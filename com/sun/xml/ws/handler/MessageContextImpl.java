/*     */ package com.sun.xml.ws.handler;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.activation.DataHandler;
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
/*     */ class MessageContextImpl
/*     */   implements MessageContext
/*     */ {
/*     */   private final Set<String> handlerScopeProps;
/*     */   private final Packet packet;
/*     */   private final Map<String, Object> asMapIncludingInvocationProperties;
/*     */   
/*     */   public MessageContextImpl(Packet packet) {
/*  67 */     this.packet = packet;
/*  68 */     this.asMapIncludingInvocationProperties = packet.asMapIncludingInvocationProperties();
/*  69 */     this.handlerScopeProps = packet.getHandlerScopePropertyNames(false);
/*     */   }
/*     */   
/*     */   protected void updatePacket() {
/*  73 */     throw new UnsupportedOperationException("wrong call");
/*     */   }
/*     */   
/*     */   public void setScope(String name, MessageContext.Scope scope) {
/*  77 */     if (!containsKey(name))
/*  78 */       throw new IllegalArgumentException("Property " + name + " does not exist."); 
/*  79 */     if (scope == MessageContext.Scope.APPLICATION) {
/*  80 */       this.handlerScopeProps.remove(name);
/*     */     } else {
/*  82 */       this.handlerScopeProps.add(name);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageContext.Scope getScope(String name) {
/*  88 */     if (!containsKey(name))
/*  89 */       throw new IllegalArgumentException("Property " + name + " does not exist."); 
/*  90 */     if (this.handlerScopeProps.contains(name)) {
/*  91 */       return MessageContext.Scope.HANDLER;
/*     */     }
/*  93 */     return MessageContext.Scope.APPLICATION;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  98 */     return this.asMapIncludingInvocationProperties.size();
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 102 */     return this.asMapIncludingInvocationProperties.isEmpty();
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 106 */     return this.asMapIncludingInvocationProperties.containsKey(key);
/*     */   }
/*     */   
/*     */   public boolean containsValue(Object value) {
/* 110 */     return this.asMapIncludingInvocationProperties.containsValue(value);
/*     */   }
/*     */   
/*     */   public Object put(String key, Object value) {
/* 114 */     if (!this.asMapIncludingInvocationProperties.containsKey(key))
/*     */     {
/* 116 */       this.handlerScopeProps.add(key);
/*     */     }
/* 118 */     return this.asMapIncludingInvocationProperties.put(key, value);
/*     */   }
/*     */   public Object get(Object key) {
/* 121 */     if (key == null)
/* 122 */       return null; 
/* 123 */     Object value = this.asMapIncludingInvocationProperties.get(key);
/*     */     
/* 125 */     if (key.equals("javax.xml.ws.binding.attachments.outbound") || key.equals("javax.xml.ws.binding.attachments.inbound")) {
/*     */       
/* 127 */       Map<String, DataHandler> atts = (Map<String, DataHandler>)value;
/* 128 */       if (atts == null)
/* 129 */         atts = new HashMap<String, DataHandler>(); 
/* 130 */       AttachmentSet attSet = this.packet.getMessage().getAttachments();
/* 131 */       for (Attachment att : attSet) {
/* 132 */         String cid = att.getContentId();
/* 133 */         if (cid.indexOf("@jaxws.sun.com") == -1) {
/* 134 */           Object a = atts.get(cid);
/* 135 */           if (a == null) {
/* 136 */             a = atts.get("<" + cid + ">");
/* 137 */             if (a == null) atts.put(att.getContentId(), att.asDataHandler()); 
/*     */           }  continue;
/*     */         } 
/* 140 */         atts.put(att.getContentId(), att.asDataHandler());
/*     */       } 
/*     */       
/* 143 */       return atts;
/*     */     } 
/* 145 */     return value;
/*     */   }
/*     */   
/*     */   public void putAll(Map<? extends String, ? extends Object> t) {
/* 149 */     for (String key : t.keySet()) {
/* 150 */       if (!this.asMapIncludingInvocationProperties.containsKey(key))
/*     */       {
/* 152 */         this.handlerScopeProps.add(key);
/*     */       }
/*     */     } 
/* 155 */     this.asMapIncludingInvocationProperties.putAll(t);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 159 */     this.asMapIncludingInvocationProperties.clear();
/*     */   }
/*     */   public Object remove(Object key) {
/* 162 */     this.handlerScopeProps.remove(key);
/* 163 */     return this.asMapIncludingInvocationProperties.remove(key);
/*     */   }
/*     */   public Set<String> keySet() {
/* 166 */     return this.asMapIncludingInvocationProperties.keySet();
/*     */   }
/*     */   public Set<Map.Entry<String, Object>> entrySet() {
/* 169 */     return this.asMapIncludingInvocationProperties.entrySet();
/*     */   }
/*     */   public Collection<Object> values() {
/* 172 */     return this.asMapIncludingInvocationProperties.values();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\handler\MessageContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */