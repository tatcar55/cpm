/*     */ package com.sun.xml.ws.assembler;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.Codec;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
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
/*     */ class DefaultClientTubelineAssemblyContext
/*     */   extends TubelineAssemblyContextImpl
/*     */   implements ClientTubelineAssemblyContext
/*     */ {
/*     */   @NotNull
/*     */   private final ClientTubeAssemblerContext wrappedContext;
/*     */   private final PolicyMap policyMap;
/*     */   private final WSPortInfo portInfo;
/*     */   private final WSDLPort wsdlPort;
/*     */   
/*     */   public DefaultClientTubelineAssemblyContext(@NotNull ClientTubeAssemblerContext context) {
/*  75 */     this.wrappedContext = context;
/*  76 */     this.wsdlPort = context.getWsdlModel();
/*  77 */     this.portInfo = context.getPortInfo();
/*  78 */     this.policyMap = context.getPortInfo().getPolicyMap();
/*     */   }
/*     */   
/*     */   public PolicyMap getPolicyMap() {
/*  82 */     return this.policyMap;
/*     */   }
/*     */   
/*     */   public boolean isPolicyAvailable() {
/*  86 */     return (this.policyMap != null && !this.policyMap.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WSDLPort getWsdlPort() {
/*  97 */     return this.wsdlPort;
/*     */   }
/*     */   
/*     */   public WSPortInfo getPortInfo() {
/* 101 */     return this.portInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public EndpointAddress getAddress() {
/* 110 */     return this.wrappedContext.getAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSService getService() {
/* 119 */     return this.wrappedContext.getService();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSBinding getBinding() {
/* 126 */     return this.wrappedContext.getBinding();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SEIModel getSEIModel() {
/* 136 */     return this.wrappedContext.getSEIModel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Container getContainer() {
/* 145 */     return this.wrappedContext.getContainer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Codec getCodec() {
/* 155 */     return this.wrappedContext.getCodec();
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
/*     */   public void setCodec(@NotNull Codec codec) {
/* 170 */     this.wrappedContext.setCodec(codec);
/*     */   }
/*     */   
/*     */   public ClientTubeAssemblerContext getWrappedContext() {
/* 174 */     return this.wrappedContext;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\DefaultClientTubelineAssemblyContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */