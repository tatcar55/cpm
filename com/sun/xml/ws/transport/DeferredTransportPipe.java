/*     */ package com.sun.xml.ws.transport;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.TransportTubeFactory;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.developer.HttpConfigFeature;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DeferredTransportPipe
/*     */   extends AbstractTubeImpl
/*     */ {
/*     */   private Tube transport;
/*     */   private EndpointAddress address;
/*     */   private final ClassLoader classLoader;
/*     */   private final ClientTubeAssemblerContext context;
/*     */   
/*     */   public DeferredTransportPipe(ClassLoader classLoader, ClientTubeAssemblerContext context) {
/*  79 */     this.classLoader = classLoader;
/*  80 */     this.context = context;
/*  81 */     if (context.getBinding().getFeature(HttpConfigFeature.class) == null) {
/*  82 */       context.getBinding().getFeatures().mergeFeatures(new WebServiceFeature[] { (WebServiceFeature)new HttpConfigFeature() }, false);
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  87 */       this.transport = TransportTubeFactory.create(classLoader, context);
/*  88 */       this.address = context.getAddress();
/*  89 */     } catch (Exception e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DeferredTransportPipe(DeferredTransportPipe that, TubeCloner cloner) {
/*  95 */     super(that, cloner);
/*  96 */     this.classLoader = that.classLoader;
/*  97 */     this.context = that.context;
/*  98 */     if (that.transport != null) {
/*  99 */       this.transport = cloner.copy(that.transport);
/* 100 */       this.address = that.address;
/*     */     } 
/*     */   }
/*     */   public NextAction processException(@NotNull Throwable t) {
/* 104 */     return this.transport.processException(t);
/*     */   }
/*     */   
/*     */   public NextAction processRequest(@NotNull Packet request) {
/* 108 */     if (request.endpointAddress == this.address)
/*     */     {
/* 110 */       return this.transport.processRequest(request);
/*     */     }
/*     */ 
/*     */     
/* 114 */     if (this.transport != null) {
/*     */       
/* 116 */       this.transport.preDestroy();
/* 117 */       this.transport = null;
/* 118 */       this.address = null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 123 */     ClientTubeAssemblerContext newContext = new ClientTubeAssemblerContext(request.endpointAddress, this.context.getWsdlModel(), this.context.getBindingProvider(), this.context.getBinding(), this.context.getContainer(), this.context.getCodec().copy(), this.context.getSEIModel(), this.context.getSEI());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 134 */     this.address = request.endpointAddress;
/* 135 */     this.transport = TransportTubeFactory.create(this.classLoader, newContext);
/*     */     
/* 137 */     assert this.transport != null;
/*     */     
/* 139 */     return this.transport.processRequest(request);
/*     */   }
/*     */   
/*     */   public NextAction processResponse(@NotNull Packet response) {
/* 143 */     if (this.transport != null)
/* 144 */       return this.transport.processResponse(response); 
/* 145 */     return doReturnWith(response);
/*     */   }
/*     */   
/*     */   public void preDestroy() {
/* 149 */     if (this.transport != null) {
/* 150 */       this.transport.preDestroy();
/* 151 */       this.transport = null;
/* 152 */       this.address = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public DeferredTransportPipe copy(TubeCloner cloner) {
/* 157 */     return new DeferredTransportPipe(this, cloner);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\DeferredTransportPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */