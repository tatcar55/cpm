/*     */ package com.sun.xml.ws.api.config.management;
/*     */ 
/*     */ import com.sun.xml.ws.api.server.Invoker;
/*     */ import org.xml.sax.EntityResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EndpointCreationAttributes
/*     */ {
/*     */   private final boolean processHandlerAnnotation;
/*     */   private final Invoker invoker;
/*     */   private final EntityResolver entityResolver;
/*     */   private final boolean isTransportSynchronous;
/*     */   
/*     */   public EndpointCreationAttributes(boolean processHandlerAnnotation, Invoker invoker, EntityResolver resolver, boolean isTransportSynchronous) {
/*  73 */     this.processHandlerAnnotation = processHandlerAnnotation;
/*  74 */     this.invoker = invoker;
/*  75 */     this.entityResolver = resolver;
/*  76 */     this.isTransportSynchronous = isTransportSynchronous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isProcessHandlerAnnotation() {
/*  85 */     return this.processHandlerAnnotation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Invoker getInvoker() {
/*  94 */     return this.invoker;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityResolver getEntityResolver() {
/* 103 */     return this.entityResolver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isTransportSynchronous() {
/* 112 */     return this.isTransportSynchronous;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\config\management\EndpointCreationAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */