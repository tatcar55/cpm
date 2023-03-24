/*     */ package com.sun.xml.ws.client;
/*     */ 
/*     */ import com.oracle.webservices.api.message.BaseDistributedPropertySet;
/*     */ import com.oracle.webservices.api.message.BasePropertySet;
/*     */ import com.oracle.webservices.api.message.DistributedPropertySet;
/*     */ import com.oracle.webservices.api.message.MessageContext;
/*     */ import com.oracle.webservices.api.message.PropertySet;
/*     */ import com.oracle.webservices.api.message.PropertySet.Property;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.PropertySet;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.transport.Headers;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class RequestContext
/*     */   extends BaseDistributedPropertySet
/*     */ {
/* 103 */   private static final Logger LOGGER = Logger.getLogger(RequestContext.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 112 */   private static ContentNegotiation defaultContentNegotiation = ContentNegotiation.obtainFromSystemProperty();
/*     */   
/*     */   @NotNull
/*     */   private EndpointAddress endpointAddress;
/*     */ 
/*     */   
/*     */   public void addSatellite(@NotNull PropertySet satellite) {
/* 119 */     addSatellite((PropertySet)satellite);
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
/*     */   @Property({"javax.xml.ws.service.endpoint.address"})
/*     */   public String getEndPointAddressString() {
/* 139 */     return (this.endpointAddress != null) ? this.endpointAddress.toString() : null;
/*     */   }
/*     */   
/*     */   public void setEndPointAddressString(String s) {
/* 143 */     if (s == null) {
/* 144 */       throw new IllegalArgumentException();
/*     */     }
/* 146 */     this.endpointAddress = EndpointAddress.create(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEndpointAddress(@NotNull EndpointAddress epa) {
/* 151 */     this.endpointAddress = epa;
/*     */   }
/*     */   @NotNull
/*     */   public EndpointAddress getEndpointAddress() {
/* 155 */     return this.endpointAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 162 */   public ContentNegotiation contentNegotiation = defaultContentNegotiation; private String soapAction;
/*     */   
/*     */   @Property({"com.sun.xml.ws.client.ContentNegotiation"})
/*     */   public String getContentNegotiationString() {
/* 166 */     return this.contentNegotiation.toString();
/*     */   }
/*     */   private Boolean soapActionUse;
/*     */   public void setContentNegotiationString(String s) {
/* 170 */     if (s == null) {
/* 171 */       this.contentNegotiation = ContentNegotiation.none;
/*     */     } else {
/*     */       try {
/* 174 */         this.contentNegotiation = ContentNegotiation.valueOf(s);
/* 175 */       } catch (IllegalArgumentException e) {
/*     */         
/* 177 */         this.contentNegotiation = ContentNegotiation.none;
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Property({"javax.xml.ws.soap.http.soapaction.uri"})
/*     */   public String getSoapAction() {
/* 211 */     return this.soapAction;
/*     */   }
/*     */   
/*     */   public void setSoapAction(String sAction) {
/* 215 */     this.soapAction = sAction;
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
/*     */   @Property({"javax.xml.ws.soap.http.soapaction.use"})
/*     */   public Boolean getSoapActionUse() {
/* 229 */     return this.soapActionUse;
/*     */   }
/*     */   
/*     */   public void setSoapActionUse(Boolean sActionUse) {
/* 233 */     this.soapActionUse = sActionUse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   RequestContext() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RequestContext(RequestContext that) {
/* 246 */     for (Map.Entry<String, Object> entry : (Iterable<Map.Entry<String, Object>>)that.asMapLocal().entrySet()) {
/* 247 */       if (!propMap.containsKey(entry.getKey())) {
/* 248 */         asMap().put(entry.getKey(), entry.getValue());
/*     */       }
/*     */     } 
/* 251 */     this.endpointAddress = that.endpointAddress;
/* 252 */     this.soapAction = that.soapAction;
/* 253 */     this.soapActionUse = that.soapActionUse;
/* 254 */     this.contentNegotiation = that.contentNegotiation;
/* 255 */     that.copySatelliteInto((DistributedPropertySet)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(Object key) {
/* 263 */     if (supports(key)) {
/* 264 */       return super.get(key);
/*     */     }
/*     */     
/* 267 */     return asMap().get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object put(String key, Object value) {
/* 277 */     if (supports(key)) {
/* 278 */       return super.put(key, value);
/*     */     }
/*     */     
/* 281 */     return asMap().put(key, value);
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
/*     */   public void fill(Packet packet, boolean isAddressingEnabled) {
/* 296 */     if (this.endpointAddress != null) {
/* 297 */       packet.endpointAddress = this.endpointAddress;
/*     */     }
/* 299 */     packet.contentNegotiation = this.contentNegotiation;
/* 300 */     fillSOAPAction(packet, isAddressingEnabled);
/* 301 */     mergeRequestHeaders(packet);
/*     */     
/* 303 */     Set<String> handlerScopeNames = new HashSet<String>();
/*     */     
/* 305 */     copySatelliteInto((MessageContext)packet);
/*     */ 
/*     */     
/* 308 */     for (String key : asMapLocal().keySet()) {
/*     */ 
/*     */       
/* 311 */       if (!supportsLocal(key)) {
/* 312 */         handlerScopeNames.add(key);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 317 */       if (!propMap.containsKey(key)) {
/* 318 */         Object value = asMapLocal().get(key);
/* 319 */         if (packet.supports(key)) {
/*     */           
/* 321 */           packet.put(key, value); continue;
/*     */         } 
/* 323 */         packet.invocationProperties.put(key, value);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 328 */     if (!handlerScopeNames.isEmpty()) {
/* 329 */       packet.getHandlerScopePropertyNames(false).addAll(handlerScopeNames);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void mergeRequestHeaders(Packet packet) {
/* 337 */     Headers packetHeaders = (Headers)packet.invocationProperties.get("javax.xml.ws.http.request.headers");
/*     */     
/* 339 */     Map<String, List<String>> myHeaders = (Map<String, List<String>>)asMap().get("javax.xml.ws.http.request.headers");
/* 340 */     if (packetHeaders != null && myHeaders != null) {
/*     */       
/* 342 */       for (Map.Entry<String, List<String>> entry : myHeaders.entrySet()) {
/* 343 */         String key = entry.getKey();
/* 344 */         if (key != null && key.trim().length() != 0) {
/* 345 */           List<String> listFromPacket = (List<String>)packetHeaders.get(key);
/*     */           
/* 347 */           if (listFromPacket != null) {
/* 348 */             listFromPacket.addAll(entry.getValue());
/*     */             continue;
/*     */           } 
/* 351 */           packetHeaders.put(key, myHeaders.get(key));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 356 */       asMap().put("javax.xml.ws.http.request.headers", packetHeaders);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fillSOAPAction(Packet packet, boolean isAddressingEnabled) {
/* 361 */     boolean p = packet.packetTakesPriorityOverRequestContext;
/* 362 */     String localSoapAction = p ? packet.soapAction : this.soapAction;
/* 363 */     Boolean localSoapActionUse = p ? (Boolean)packet.invocationProperties.get("javax.xml.ws.soap.http.soapaction.use") : this.soapActionUse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 370 */     if (((localSoapActionUse != null && localSoapActionUse.booleanValue()) || (localSoapActionUse == null && isAddressingEnabled)) && 
/* 371 */       localSoapAction != null) {
/* 372 */       packet.soapAction = localSoapAction;
/*     */     }
/*     */ 
/*     */     
/* 376 */     if (!isAddressingEnabled && (localSoapActionUse == null || !localSoapActionUse.booleanValue()) && localSoapAction != null) {
/* 377 */       LOGGER.warning("BindingProvider.SOAPACTION_URI_PROPERTY is set in the RequestContext but is ineffective, Either set BindingProvider.SOAPACTION_USE_PROPERTY to true or enable AddressingFeature");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public RequestContext copy() {
/* 383 */     return new RequestContext(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BasePropertySet.PropertyMap getPropertyMap() {
/* 388 */     return propMap;
/*     */   }
/*     */   
/* 391 */   private static final BasePropertySet.PropertyMap propMap = parse(RequestContext.class);
/*     */ 
/*     */   
/*     */   protected boolean mapAllowsAdditionalProperties() {
/* 395 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\client\RequestContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */