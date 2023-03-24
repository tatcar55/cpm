/*     */ package com.sun.xml.ws.api.pipe;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.WSService;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.helper.PipeAdapter;
/*     */ import com.sun.xml.ws.api.server.Container;
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
/*     */ 
/*     */ public final class ClientPipeAssemblerContext
/*     */   extends ClientTubeAssemblerContext
/*     */ {
/*     */   public ClientPipeAssemblerContext(@NotNull EndpointAddress address, @NotNull WSDLPort wsdlModel, @NotNull WSService rootOwner, @NotNull WSBinding binding) {
/*  64 */     this(address, wsdlModel, rootOwner, binding, Container.NONE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientPipeAssemblerContext(@NotNull EndpointAddress address, @NotNull WSDLPort wsdlModel, @NotNull WSService rootOwner, @NotNull WSBinding binding, @NotNull Container container) {
/*  70 */     super(address, wsdlModel, rootOwner, binding, container);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pipe createDumpPipe(String name, PrintStream out, Pipe next) {
/*  77 */     return PipeAdapter.adapt(createDumpTube(name, out, PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pipe createWsaPipe(Pipe next) {
/*  85 */     return PipeAdapter.adapt(createWsaTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pipe createClientMUPipe(Pipe next) {
/*  93 */     return PipeAdapter.adapt(createClientMUTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pipe createValidationPipe(Pipe next) {
/* 100 */     return PipeAdapter.adapt(createValidationTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pipe createHandlerPipe(Pipe next) {
/* 107 */     return PipeAdapter.adapt(createHandlerTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Pipe createSecurityPipe(@NotNull Pipe next) {
/* 114 */     return PipeAdapter.adapt(createSecurityTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pipe createTransportPipe() {
/* 121 */     return PipeAdapter.adapt(createTransportTube());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\ClientPipeAssemblerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */