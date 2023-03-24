/*     */ package com.sun.xml.ws.assembler;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.assembler.dev.ServerTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DefaultServerTubelineAssemblyContext
/*     */   extends TubelineAssemblyContextImpl
/*     */   implements ServerTubelineAssemblyContext
/*     */ {
/*     */   @NotNull
/*     */   private final ServerTubeAssemblerContext wrappedContext;
/*     */   private final PolicyMap policyMap;
/*     */   
/*     */   public DefaultServerTubelineAssemblyContext(@NotNull ServerTubeAssemblerContext context) {
/*  71 */     this.wrappedContext = context;
/*  72 */     this.policyMap = context.getEndpoint().getPolicyMap();
/*     */   }
/*     */   
/*     */   public PolicyMap getPolicyMap() {
/*  76 */     return this.policyMap;
/*     */   }
/*     */   
/*     */   public boolean isPolicyAvailable() {
/*  80 */     return (this.policyMap != null && !this.policyMap.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SEIModel getSEIModel() {
/*  90 */     return this.wrappedContext.getSEIModel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WSDLPort getWsdlPort() {
/* 100 */     return this.wrappedContext.getWsdlModel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSEndpoint getEndpoint() {
/* 111 */     return this.wrappedContext.getEndpoint();
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
/*     */   @NotNull
/*     */   public Tube getTerminalTube() {
/* 125 */     return this.wrappedContext.getTerminalTube();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSynchronous() {
/* 135 */     return this.wrappedContext.isSynchronous();
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
/*     */   @NotNull
/*     */   public Codec getCodec() {
/* 148 */     return this.wrappedContext.getCodec();
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
/*     */   public void setCodec(@NotNull Codec codec) {
/* 170 */     this.wrappedContext.setCodec(codec);
/*     */   }
/*     */   
/*     */   public ServerTubeAssemblerContext getWrappedContext() {
/* 174 */     return this.wrappedContext;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\DefaultServerTubelineAssemblyContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */