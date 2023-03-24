/*     */ package com.sun.xml.ws.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.server.SDDocument;
/*     */ import com.sun.xml.ws.api.server.SDDocumentFilter;
/*     */ import com.sun.xml.ws.api.server.ServiceDefinition;
/*     */ import com.sun.xml.ws.wsdl.SDDocumentResolver;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ServiceDefinitionImpl
/*     */   implements ServiceDefinition, SDDocumentResolver
/*     */ {
/*     */   private final List<SDDocumentImpl> docs;
/*     */   private final Map<String, SDDocumentImpl> bySystemId;
/*     */   @NotNull
/*     */   private final SDDocumentImpl primaryWsdl;
/*     */   WSEndpointImpl<?> owner;
/*  75 */   final List<SDDocumentFilter> filters = new ArrayList<SDDocumentFilter>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServiceDefinitionImpl(List<SDDocumentImpl> docs, @NotNull SDDocumentImpl primaryWsdl) {
/*  84 */     assert docs.contains(primaryWsdl);
/*  85 */     this.docs = docs;
/*  86 */     this.primaryWsdl = primaryWsdl;
/*     */     
/*  88 */     this.bySystemId = new HashMap<String, SDDocumentImpl>(docs.size());
/*  89 */     for (SDDocumentImpl doc : docs) {
/*  90 */       this.bySystemId.put(doc.getURL().toExternalForm(), doc);
/*  91 */       doc.setFilters(this.filters);
/*  92 */       doc.setResolver(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setOwner(WSEndpointImpl<?> owner) {
/* 100 */     assert owner != null && this.owner == null;
/* 101 */     this.owner = owner;
/*     */   }
/*     */   @NotNull
/*     */   public SDDocument getPrimary() {
/* 105 */     return this.primaryWsdl;
/*     */   }
/*     */   
/*     */   public void addFilter(SDDocumentFilter filter) {
/* 109 */     this.filters.add(filter);
/*     */   }
/*     */   
/*     */   public Iterator<SDDocument> iterator() {
/* 113 */     return this.docs.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SDDocument resolve(String systemId) {
/* 124 */     return this.bySystemId.get(systemId);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\ServiceDefinitionImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */