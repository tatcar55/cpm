/*     */ package com.sun.xml.ws.api.pipe;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.addressing.W3CWsaServerTube;
/*     */ import com.sun.xml.ws.addressing.v200408.MemberSubmissionWsaServerTube;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.helper.PipeAdapter;
/*     */ import com.sun.xml.ws.api.server.ServerPipelineHook;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.binding.BindingImpl;
/*     */ import com.sun.xml.ws.developer.SchemaValidationFeature;
/*     */ import com.sun.xml.ws.handler.HandlerTube;
/*     */ import com.sun.xml.ws.handler.ServerLogicalHandlerTube;
/*     */ import com.sun.xml.ws.handler.ServerMessageHandlerTube;
/*     */ import com.sun.xml.ws.handler.ServerSOAPHandlerTube;
/*     */ import com.sun.xml.ws.protocol.soap.ServerMUTube;
/*     */ import com.sun.xml.ws.server.ServerSchemaValidationTube;
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
/*     */ 
/*     */ 
/*     */ public class ServerTubeAssemblerContext
/*     */ {
/*     */   private final SEIModel seiModel;
/*     */   private final WSDLPort wsdlModel;
/*     */   private final WSEndpoint endpoint;
/*     */   private final BindingImpl binding;
/*     */   private final Tube terminal;
/*     */   private final boolean isSynchronous;
/*     */   @NotNull
/*     */   private Codec codec;
/*     */   
/*     */   public ServerTubeAssemblerContext(@Nullable SEIModel seiModel, @Nullable WSDLPort wsdlModel, @NotNull WSEndpoint endpoint, @NotNull Tube terminal, boolean isSynchronous) {
/*  86 */     this.seiModel = seiModel;
/*  87 */     this.wsdlModel = wsdlModel;
/*  88 */     this.endpoint = endpoint;
/*  89 */     this.terminal = terminal;
/*     */     
/*  91 */     this.binding = (BindingImpl)endpoint.getBinding();
/*  92 */     this.isSynchronous = isSynchronous;
/*  93 */     this.codec = this.binding.createCodec();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SEIModel getSEIModel() {
/* 103 */     return this.seiModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WSDLPort getWsdlModel() {
/* 113 */     return this.wsdlModel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public WSEndpoint<?> getEndpoint() {
/* 124 */     return this.endpoint;
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
/* 138 */     return this.terminal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSynchronous() {
/* 148 */     return this.isSynchronous;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Tube createServerMUTube(@NotNull Tube next) {
/* 156 */     if (this.binding instanceof javax.xml.ws.soap.SOAPBinding) {
/* 157 */       return (Tube)new ServerMUTube(this, next);
/*     */     }
/* 159 */     return next;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Tube createHandlerTube(@NotNull Tube next) {
/*     */     ServerMessageHandlerTube serverMessageHandlerTube;
/* 166 */     if (!this.binding.getHandlerChain().isEmpty()) {
/* 167 */       ServerLogicalHandlerTube serverLogicalHandlerTube2 = new ServerLogicalHandlerTube((WSBinding)this.binding, this.seiModel, this.wsdlModel, next);
/* 168 */       ServerLogicalHandlerTube serverLogicalHandlerTube1 = serverLogicalHandlerTube2;
/* 169 */       if (this.binding instanceof javax.xml.ws.soap.SOAPBinding) {
/*     */         
/* 171 */         ServerSOAPHandlerTube serverSOAPHandlerTube2 = new ServerSOAPHandlerTube((WSBinding)this.binding, (Tube)serverLogicalHandlerTube1, (HandlerTube)serverLogicalHandlerTube2), serverSOAPHandlerTube1 = serverSOAPHandlerTube2;
/*     */ 
/*     */         
/* 174 */         serverMessageHandlerTube = new ServerMessageHandlerTube(this.seiModel, (WSBinding)this.binding, (Tube)serverSOAPHandlerTube1, (HandlerTube)serverSOAPHandlerTube2);
/*     */       } 
/*     */     } 
/* 177 */     return (Tube)serverMessageHandlerTube;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Tube createMonitoringTube(@NotNull Tube next) {
/* 185 */     ServerPipelineHook hook = (ServerPipelineHook)this.endpoint.getContainer().getSPI(ServerPipelineHook.class);
/* 186 */     if (hook != null) {
/* 187 */       ServerPipeAssemblerContext ctxt = new ServerPipeAssemblerContext(this.seiModel, this.wsdlModel, this.endpoint, this.terminal, this.isSynchronous);
/* 188 */       return PipeAdapter.adapt(hook.createMonitoringPipe(ctxt, PipeAdapter.adapt(next)));
/*     */     } 
/* 190 */     return next;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public Tube createSecurityTube(@NotNull Tube next) {
/* 197 */     ServerPipelineHook hook = (ServerPipelineHook)this.endpoint.getContainer().getSPI(ServerPipelineHook.class);
/* 198 */     if (hook != null) {
/* 199 */       ServerPipeAssemblerContext ctxt = new ServerPipeAssemblerContext(this.seiModel, this.wsdlModel, this.endpoint, this.terminal, this.isSynchronous);
/* 200 */       return PipeAdapter.adapt(hook.createSecurityPipe(ctxt, PipeAdapter.adapt(next)));
/*     */     } 
/* 202 */     return next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createDumpTube(String name, PrintStream out, Tube next) {
/* 209 */     return (Tube)new DumpTube(name, out, next);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createValidationTube(Tube next) {
/* 216 */     if (this.binding instanceof javax.xml.ws.soap.SOAPBinding && this.binding.isFeatureEnabled(SchemaValidationFeature.class) && this.wsdlModel != null) {
/* 217 */       return (Tube)new ServerSchemaValidationTube(this.endpoint, (WSBinding)this.binding, this.seiModel, this.wsdlModel, next);
/*     */     }
/* 219 */     return next;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createWsaTube(Tube next) {
/* 226 */     if (this.binding instanceof javax.xml.ws.soap.SOAPBinding && AddressingVersion.isEnabled((WSBinding)this.binding)) {
/* 227 */       if (AddressingVersion.fromBinding((WSBinding)this.binding) == AddressingVersion.MEMBER) {
/* 228 */         return (Tube)new MemberSubmissionWsaServerTube(this.endpoint, this.wsdlModel, (WSBinding)this.binding, next);
/*     */       }
/* 230 */       return (Tube)new W3CWsaServerTube(this.endpoint, this.wsdlModel, (WSBinding)this.binding, next);
/*     */     } 
/*     */     
/* 233 */     return next;
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
/* 246 */     return this.codec;
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
/* 268 */     this.codec = codec;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\ServerTubeAssemblerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */