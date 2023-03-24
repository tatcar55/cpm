/*     */ package com.sun.xml.ws.api.pipe;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.helper.PipeAdapter;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
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
/*     */ public final class ServerPipeAssemblerContext
/*     */   extends ServerTubeAssemblerContext
/*     */ {
/*     */   public ServerPipeAssemblerContext(@Nullable SEIModel seiModel, @Nullable WSDLPort wsdlModel, @NotNull WSEndpoint endpoint, @NotNull Tube terminal, boolean isSynchronous) {
/*  63 */     super(seiModel, wsdlModel, endpoint, terminal, isSynchronous);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Pipe createServerMUPipe(@NotNull Pipe next) {
/*  71 */     return PipeAdapter.adapt(createServerMUTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pipe createDumpPipe(String name, PrintStream out, Pipe next) {
/*  78 */     return PipeAdapter.adapt(createDumpTube(name, out, PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Pipe createMonitoringPipe(@NotNull Pipe next) {
/*  86 */     return PipeAdapter.adapt(createMonitoringTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Pipe createSecurityPipe(@NotNull Pipe next) {
/*  93 */     return PipeAdapter.adapt(createSecurityTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Pipe createValidationPipe(@NotNull Pipe next) {
/* 100 */     return PipeAdapter.adapt(createValidationTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Pipe createHandlerPipe(@NotNull Pipe next) {
/* 107 */     return PipeAdapter.adapt(createHandlerTube(PipeAdapter.adapt(next)));
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
/*     */   public Pipe getTerminalPipe() {
/* 121 */     return PipeAdapter.adapt(getTerminalTube());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pipe createWsaPipe(Pipe next) {
/* 128 */     return PipeAdapter.adapt(createWsaTube(PipeAdapter.adapt(next)));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\ServerPipeAssemblerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */