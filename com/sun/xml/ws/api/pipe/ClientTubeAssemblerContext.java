/*     */ package com.sun.xml.ws.api.pipe;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.addressing.W3CWsaClientTube;
/*     */ import com.sun.xml.ws.addressing.v200408.MemberSubmissionWsaClientTube;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.client.ClientPipelineHook;
/*     */ import com.sun.xml.ws.api.client.WSPortInfo;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.helper.PipeAdapter;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.client.ClientSchemaValidationTube;
/*     */ import com.sun.xml.ws.developer.SchemaValidationFeature;
/*     */ import com.sun.xml.ws.developer.WSBindingProvider;
/*     */ import com.sun.xml.ws.handler.ClientLogicalHandlerTube;
/*     */ import com.sun.xml.ws.handler.ClientMessageHandlerTube;
/*     */ import com.sun.xml.ws.handler.ClientSOAPHandlerTube;
/*     */ import com.sun.xml.ws.handler.HandlerTube;
/*     */ import com.sun.xml.ws.protocol.soap.ClientMUTube;
/*     */ import com.sun.xml.ws.transport.DeferredTransportPipe;
/*     */ import com.sun.xml.ws.util.pipe.DumpTube;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientTubeAssemblerContext
/*     */ {
/*     */   @NotNull
/*     */   private final EndpointAddress address;
/*     */   @Nullable
/*     */   private final WSDLPort wsdlModel;
/*     */   @Nullable
/*     */   private final SEIModel seiModel;
/*     */   @Nullable
/*     */   private final Class sei;
/*     */   @NotNull
/*     */   private final WSService rootOwner;
/*     */   @NotNull
/*     */   private final WSBinding binding;
/*     */   @NotNull
/*     */   private final Container container;
/*     */   @NotNull
/*     */   private Codec codec;
/*     */   @Nullable
/*     */   private final WSBindingProvider bindingProvider;
/*     */   
/*     */   public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel, @NotNull WSService rootOwner, @NotNull WSBinding binding) {
/*  99 */     this(address, wsdlModel, rootOwner, binding, Container.NONE);
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
/*     */   public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel, @NotNull WSService rootOwner, @NotNull WSBinding binding, @NotNull Container container) {
/* 111 */     this(address, wsdlModel, rootOwner, binding, container, ((BindingImpl)binding).createCodec());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel, @NotNull WSService rootOwner, @NotNull WSBinding binding, @NotNull Container container, Codec codec) {
/* 122 */     this(address, wsdlModel, rootOwner, binding, container, codec, (SEIModel)null, (Class)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel, @NotNull WSService rootOwner, @NotNull WSBinding binding, @NotNull Container container, Codec codec, SEIModel seiModel, Class sei) {
/* 133 */     this(address, wsdlModel, rootOwner, null, binding, container, codec, seiModel, sei);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel, @NotNull WSBindingProvider bindingProvider, @NotNull WSBinding binding, @NotNull Container container, Codec codec, SEIModel seiModel, Class sei) {
/* 144 */     this(address, wsdlModel, (bindingProvider == null) ? null : bindingProvider.getPortInfo().getOwner(), bindingProvider, binding, container, codec, seiModel, sei);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClientTubeAssemblerContext(@NotNull EndpointAddress address, @Nullable WSDLPort wsdlModel, @Nullable WSService rootOwner, @Nullable WSBindingProvider bindingProvider, @NotNull WSBinding binding, @NotNull Container container, Codec codec, SEIModel seiModel, Class sei) {
/* 153 */     this.address = address;
/* 154 */     this.wsdlModel = wsdlModel;
/* 155 */     this.rootOwner = rootOwner;
/* 156 */     this.bindingProvider = bindingProvider;
/* 157 */     this.binding = binding;
/* 158 */     this.container = container;
/* 159 */     this.codec = codec;
/* 160 */     this.seiModel = seiModel;
/* 161 */     this.sei = sei;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public EndpointAddress getAddress() {
/* 170 */     return this.address;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WSDLPort getWsdlModel() {
/* 179 */     return this.wsdlModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSService getService() {
/* 188 */     return this.rootOwner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WSPortInfo getPortInfo() {
/* 196 */     return (this.bindingProvider == null) ? null : this.bindingProvider.getPortInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WSBindingProvider getBindingProvider() {
/* 205 */     return this.bindingProvider;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSBinding getBinding() {
/* 212 */     return this.binding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SEIModel getSEIModel() {
/* 222 */     return this.seiModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Class getSEI() {
/* 232 */     return this.sei;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Container getContainer() {
/* 241 */     return this.container;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createDumpTube(String name, PrintStream out, Tube next) {
/* 248 */     return (Tube)new DumpTube(name, out, next);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Tube createSecurityTube(@NotNull Tube next) {
/* 255 */     ClientPipelineHook hook = (ClientPipelineHook)this.container.getSPI(ClientPipelineHook.class);
/* 256 */     if (hook != null) {
/* 257 */       ClientPipeAssemblerContext ctxt = new ClientPipeAssemblerContext(this.address, this.wsdlModel, this.rootOwner, this.binding, this.container);
/*     */       
/* 259 */       return PipeAdapter.adapt(hook.createSecurityPipe(ctxt, PipeAdapter.adapt(next)));
/*     */     } 
/* 261 */     return next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createWsaTube(Tube next) {
/* 268 */     if (this.binding instanceof javax.xml.ws.soap.SOAPBinding && AddressingVersion.isEnabled(this.binding) && this.wsdlModel != null) {
/* 269 */       if (AddressingVersion.fromBinding(this.binding) == AddressingVersion.MEMBER) {
/* 270 */         return (Tube)new MemberSubmissionWsaClientTube(this.wsdlModel, this.binding, next);
/*     */       }
/* 272 */       return (Tube)new W3CWsaClientTube(this.wsdlModel, this.binding, next);
/*     */     } 
/*     */     
/* 275 */     return next;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createHandlerTube(Tube next) {
/*     */     ClientSOAPHandlerTube clientSOAPHandlerTube1, clientSOAPHandlerTube2;
/* 282 */     HandlerTube cousinHandlerTube = null;
/*     */     
/* 284 */     if (this.binding instanceof javax.xml.ws.soap.SOAPBinding) {
/*     */       
/* 286 */       ClientMessageHandlerTube clientMessageHandlerTube3 = new ClientMessageHandlerTube(this.seiModel, this.binding, this.wsdlModel, next);
/* 287 */       ClientMessageHandlerTube clientMessageHandlerTube2 = clientMessageHandlerTube3, clientMessageHandlerTube1 = clientMessageHandlerTube2;
/*     */ 
/*     */       
/* 290 */       ClientSOAPHandlerTube clientSOAPHandlerTube = new ClientSOAPHandlerTube(this.binding, (Tube)clientMessageHandlerTube1, (HandlerTube)clientMessageHandlerTube2);
/* 291 */       clientSOAPHandlerTube1 = clientSOAPHandlerTube2 = clientSOAPHandlerTube;
/*     */     } 
/* 293 */     return (Tube)new ClientLogicalHandlerTube(this.binding, this.seiModel, (Tube)clientSOAPHandlerTube1, (HandlerTube)clientSOAPHandlerTube2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createClientMUTube(Tube next) {
/* 301 */     if (this.binding instanceof javax.xml.ws.soap.SOAPBinding) {
/* 302 */       return (Tube)new ClientMUTube(this.binding, next);
/*     */     }
/* 304 */     return next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createValidationTube(Tube next) {
/* 311 */     if (this.binding instanceof javax.xml.ws.soap.SOAPBinding && this.binding.isFeatureEnabled(SchemaValidationFeature.class) && this.wsdlModel != null) {
/* 312 */       return (Tube)new ClientSchemaValidationTube(this.binding, this.wsdlModel, next);
/*     */     }
/* 314 */     return next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createTransportTube() {
/* 321 */     ClassLoader cl = Thread.currentThread().getContextClassLoader();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 327 */     return (Tube)new DeferredTransportPipe(cl, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Codec getCodec() {
/* 337 */     return this.codec;
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
/* 352 */     this.codec = codec;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\ClientTubeAssemblerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */