/*     */ package com.sun.xml.ws.util.pipe;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.pipe.ClientPipeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.Pipe;
/*     */ import com.sun.xml.ws.api.pipe.PipelineAssembler;
/*     */ import com.sun.xml.ws.api.pipe.ServerPipeAssemblerContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandalonePipeAssembler
/*     */   implements PipelineAssembler
/*     */ {
/*     */   private static final boolean dump;
/*     */   
/*     */   @NotNull
/*     */   public Pipe createClient(ClientPipeAssemblerContext context) {
/*  61 */     Pipe head = context.createTransportPipe();
/*  62 */     head = context.createSecurityPipe(head);
/*     */     
/*  64 */     if (dump)
/*     */     {
/*     */       
/*  67 */       head = context.createDumpPipe("client", System.out, head);
/*     */     }
/*  69 */     head = context.createWsaPipe(head);
/*  70 */     head = context.createClientMUPipe(head);
/*  71 */     return context.createHandlerPipe(head);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pipe createServer(ServerPipeAssemblerContext context) {
/*  80 */     Pipe head = context.getTerminalPipe();
/*  81 */     head = context.createHandlerPipe(head);
/*  82 */     head = context.createMonitoringPipe(head);
/*  83 */     head = context.createServerMUPipe(head);
/*  84 */     head = context.createWsaPipe(head);
/*  85 */     head = context.createSecurityPipe(head);
/*  86 */     return head;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  95 */     boolean b = false;
/*     */     try {
/*  97 */       b = Boolean.getBoolean(StandalonePipeAssembler.class.getName() + ".dump");
/*  98 */     } catch (Throwable t) {}
/*     */ 
/*     */     
/* 101 */     dump = b;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\pipe\StandalonePipeAssembler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */