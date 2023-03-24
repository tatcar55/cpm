/*     */ package com.sun.xml.ws.rx.testing;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.rx.rm.runtime.RmConfiguration;
/*     */ import com.sun.xml.ws.rx.rm.runtime.RuntimeContext;
/*     */ import com.sun.xml.ws.rx.util.Communicator;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PacketFilteringTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*  65 */   private static final Logger LOGGER = Logger.getLogger(PacketFilteringTube.class);
/*     */   private final boolean isClientSide;
/*     */   private final RuntimeContext rc;
/*     */   private final List<PacketFilter> filters;
/*     */   
/*     */   public PacketFilteringTube(PacketFilteringTube original, TubeCloner cloner) {
/*  71 */     super(original, cloner);
/*  72 */     this.isClientSide = original.isClientSide;
/*  73 */     this.rc = original.rc;
/*  74 */     this.filters = original.filters;
/*     */   }
/*     */   public PacketFilteringTube(RmConfiguration configuration, Tube tubelineHead, ClientTubelineAssemblyContext context) {
/*  77 */     super(tubelineHead);
/*  78 */     this.isClientSide = true;
/*     */     
/*  80 */     RuntimeContext.Builder rcBuilder = RuntimeContext.builder(configuration, Communicator.builder("packet-filtering-client-tube-communicator").tubelineHead(this.next).addressingVersion(configuration.getAddressingVersion()).soapVersion(configuration.getSoapVersion()).jaxbContext(configuration.getRuntimeVersion().getJaxbContext(configuration.getAddressingVersion())).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  89 */     this.rc = rcBuilder.build();
/*     */     
/*  91 */     this.filters = getConfiguredFilters(context.getBinding(), this.rc);
/*     */   }
/*     */   
/*     */   public PacketFilteringTube(RmConfiguration configuration, Tube tubelineHead, ServerTubelineAssemblyContext context) {
/*  95 */     super(tubelineHead);
/*  96 */     this.isClientSide = false;
/*     */     
/*  98 */     RuntimeContext.Builder rcBuilder = RuntimeContext.builder(configuration, Communicator.builder("packet-filtering-server-tube-communicator").tubelineHead(this.next).addressingVersion(configuration.getAddressingVersion()).soapVersion(configuration.getSoapVersion()).jaxbContext(configuration.getRuntimeVersion().getJaxbContext(configuration.getAddressingVersion())).build());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     this.rc = rcBuilder.build();
/*     */     
/* 109 */     this.filters = getConfiguredFilters(context.getEndpoint().getBinding(), this.rc);
/*     */   }
/*     */ 
/*     */   
/*     */   public PacketFilteringTube copy(TubeCloner cloner) {
/* 114 */     LOGGER.entering();
/*     */     try {
/* 116 */       return new PacketFilteringTube(this, cloner);
/*     */     } finally {
/* 118 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void preDestroy() {
/* 124 */     this.rc.close();
/*     */     
/* 126 */     super.preDestroy();
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/* 131 */     if (this.isClientSide) {
/*     */       try {
/* 133 */         for (PacketFilter filter : this.filters) {
/* 134 */           if (request != null) {
/* 135 */             request = filter.filterClientRequest(request);
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 140 */       catch (Exception ex) {
/* 141 */         LOGGER.logSevereException(ex);
/* 142 */         if (ex instanceof RuntimeException) {
/* 143 */           return doThrow(ex);
/*     */         }
/* 145 */         return doThrow(new WebServiceException(ex));
/*     */       } 
/*     */ 
/*     */       
/* 149 */       if (request == null)
/*     */       {
/* 151 */         return doThrow(new WebServiceException(new IOException("Simulated IO error while sending a request")));
/*     */       }
/*     */     } 
/* 154 */     return super.processRequest(request);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/* 159 */     if (!this.isClientSide) {
/*     */       try {
/* 161 */         for (PacketFilter filter : this.filters) {
/* 162 */           if (response != null) {
/* 163 */             response = filter.filterServerResponse(response);
/*     */           }
/*     */         }
/*     */       
/*     */       }
/* 168 */       catch (Exception ex) {
/* 169 */         LOGGER.logSevereException(ex);
/* 170 */         if (ex instanceof RuntimeException) {
/* 171 */           return doThrow(ex);
/*     */         }
/* 173 */         return doThrow(new WebServiceException(ex));
/*     */       } 
/*     */     }
/*     */     
/* 177 */     return super.processResponse(response);
/*     */   }
/*     */   
/*     */   private List<PacketFilter> getConfiguredFilters(WSBinding binding, RuntimeContext context) {
/* 181 */     PacketFilteringFeature pfFeature = (PacketFilteringFeature)binding.getFeature(PacketFilteringFeature.class);
/* 182 */     return pfFeature.createFilters(context);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\testing\PacketFilteringTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */