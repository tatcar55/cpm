/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class EndpointMessageContextImpl
/*     */   extends AbstractMap<String, Object>
/*     */   implements MessageContext
/*     */ {
/*     */   private Set<Map.Entry<String, Object>> entrySet;
/*     */   private final Packet packet;
/*     */   
/*     */   public EndpointMessageContextImpl(Packet packet) {
/*  82 */     this.packet = packet;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object key) {
/*  88 */     if (this.packet.supports(key)) {
/*  89 */       return this.packet.get(key);
/*     */     }
/*  91 */     if (this.packet.getHandlerScopePropertyNames(true).contains(key)) {
/*  92 */       return null;
/*     */     }
/*  94 */     Object value = this.packet.invocationProperties.get(key);
/*     */ 
/*     */     
/*  97 */     if (key.equals("javax.xml.ws.binding.attachments.outbound") || key.equals("javax.xml.ws.binding.attachments.inbound")) {
/*     */       
/*  99 */       Map<String, DataHandler> atts = (Map<String, DataHandler>)value;
/* 100 */       if (atts == null)
/* 101 */         atts = new HashMap<String, DataHandler>(); 
/* 102 */       AttachmentSet attSet = this.packet.getMessage().getAttachments();
/* 103 */       for (Attachment att : attSet) {
/* 104 */         atts.put(att.getContentId(), att.asDataHandler());
/*     */       }
/* 106 */       return atts;
/*     */     } 
/* 108 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object put(String key, Object value) {
/* 113 */     if (this.packet.supports(key)) {
/* 114 */       return this.packet.put(key, value);
/*     */     }
/* 116 */     Object old = this.packet.invocationProperties.get(key);
/* 117 */     if (old != null) {
/* 118 */       if (this.packet.getHandlerScopePropertyNames(true).contains(key)) {
/* 119 */         throw new IllegalArgumentException("Cannot overwrite property in HANDLER scope");
/*     */       }
/*     */       
/* 122 */       this.packet.invocationProperties.put(key, value);
/* 123 */       return old;
/*     */     } 
/*     */     
/* 126 */     this.packet.invocationProperties.put(key, value);
/* 127 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object remove(Object key) {
/* 133 */     if (this.packet.supports(key)) {
/* 134 */       return this.packet.remove(key);
/*     */     }
/* 136 */     Object old = this.packet.invocationProperties.get(key);
/* 137 */     if (old != null) {
/* 138 */       if (this.packet.getHandlerScopePropertyNames(true).contains(key)) {
/* 139 */         throw new IllegalArgumentException("Cannot remove property in HANDLER scope");
/*     */       }
/*     */       
/* 142 */       this.packet.invocationProperties.remove(key);
/* 143 */       return old;
/*     */     } 
/*     */     
/* 146 */     return null;
/*     */   }
/*     */   
/*     */   public Set<Map.Entry<String, Object>> entrySet() {
/* 150 */     if (this.entrySet == null) {
/* 151 */       this.entrySet = new EntrySet();
/*     */     }
/* 153 */     return this.entrySet;
/*     */   }
/*     */   
/*     */   public void setScope(String name, MessageContext.Scope scope) {
/* 157 */     throw new UnsupportedOperationException("All the properties in this context are in APPLICATION scope. Cannot do setScope().");
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageContext.Scope getScope(String name) {
/* 162 */     throw new UnsupportedOperationException("All the properties in this context are in APPLICATION scope. Cannot do getScope().");
/*     */   }
/*     */   
/*     */   private class EntrySet extends AbstractSet<Map.Entry<String, Object>> {
/*     */     private EntrySet() {}
/*     */     
/*     */     public Iterator<Map.Entry<String, Object>> iterator() {
/* 169 */       final Iterator<Map.Entry<String, Object>> it = EndpointMessageContextImpl.this.createBackupMap().entrySet().iterator();
/*     */       
/* 171 */       return new Iterator<Map.Entry<String, Object>>() {
/*     */           Map.Entry<String, Object> cur;
/*     */           
/*     */           public boolean hasNext() {
/* 175 */             return it.hasNext();
/*     */           }
/*     */           
/*     */           public Map.Entry<String, Object> next() {
/* 179 */             this.cur = it.next();
/* 180 */             return this.cur;
/*     */           }
/*     */           
/*     */           public void remove() {
/* 184 */             it.remove();
/* 185 */             EndpointMessageContextImpl.this.remove(this.cur.getKey());
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*     */     public int size() {
/* 191 */       return EndpointMessageContextImpl.this.createBackupMap().size();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<String, Object> createBackupMap() {
/* 197 */     Map<String, Object> backupMap = new HashMap<String, Object>();
/* 198 */     backupMap.putAll(this.packet.createMapView());
/* 199 */     Set<String> handlerProps = this.packet.getHandlerScopePropertyNames(true);
/* 200 */     for (Map.Entry<String, Object> e : (Iterable<Map.Entry<String, Object>>)this.packet.invocationProperties.entrySet()) {
/* 201 */       if (!handlerProps.contains(e.getKey())) {
/* 202 */         backupMap.put(e.getKey(), e.getValue());
/*     */       }
/*     */     } 
/* 205 */     return backupMap;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\EndpointMessageContextImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */