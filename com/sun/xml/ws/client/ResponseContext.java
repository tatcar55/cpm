/*     */ package com.sun.xml.ws.client;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.activation.DataHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResponseContext
/*     */   extends AbstractMap<String, Object>
/*     */ {
/*     */   private final Packet packet;
/*     */   private Set<Map.Entry<String, Object>> entrySet;
/*     */   
/*     */   public ResponseContext(Packet packet) {
/*  98 */     this.packet = packet;
/*     */   }
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 102 */     if (this.packet.supports(key)) {
/* 103 */       return this.packet.containsKey(key);
/*     */     }
/* 105 */     if (this.packet.invocationProperties.containsKey(key))
/*     */     {
/* 107 */       return !this.packet.getHandlerScopePropertyNames(true).contains(key);
/*     */     }
/* 109 */     return false;
/*     */   }
/*     */   
/*     */   public Object get(Object key) {
/* 113 */     if (this.packet.supports(key)) {
/* 114 */       return this.packet.get(key);
/*     */     }
/* 116 */     if (this.packet.getHandlerScopePropertyNames(true).contains(key)) {
/* 117 */       return null;
/*     */     }
/* 119 */     Object value = this.packet.invocationProperties.get(key);
/*     */ 
/*     */     
/* 122 */     if (key.equals("javax.xml.ws.binding.attachments.inbound")) {
/* 123 */       Map<String, DataHandler> atts = (Map<String, DataHandler>)value;
/* 124 */       if (atts == null)
/* 125 */         atts = new HashMap<String, DataHandler>(); 
/* 126 */       AttachmentSet attSet = this.packet.getMessage().getAttachments();
/* 127 */       for (Attachment att : attSet) {
/* 128 */         atts.put(att.getContentId(), att.asDataHandler());
/*     */       }
/* 130 */       return atts;
/*     */     } 
/* 132 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object put(String key, Object value) {
/* 137 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object remove(Object key) {
/* 142 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends String, ? extends Object> t) {
/* 147 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 152 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public Set<Map.Entry<String, Object>> entrySet() {
/* 156 */     if (this.entrySet == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 161 */       Map<String, Object> r = new HashMap<String, Object>();
/*     */ 
/*     */       
/* 164 */       r.putAll(this.packet.invocationProperties);
/*     */ 
/*     */       
/* 167 */       r.keySet().removeAll(this.packet.getHandlerScopePropertyNames(true));
/*     */ 
/*     */       
/* 170 */       r.putAll(this.packet.createMapView());
/*     */       
/* 172 */       this.entrySet = Collections.unmodifiableSet(r.entrySet());
/*     */     } 
/*     */     
/* 175 */     return this.entrySet;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\ResponseContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */