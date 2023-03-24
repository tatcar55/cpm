/*     */ package com.sun.xml.ws.util.pipe;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.pipe.ClientTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.ServerTubeAssemblerContext;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubelineAssembler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandaloneTubeAssembler
/*     */   implements TubelineAssembler
/*     */ {
/*     */   public static final boolean dump;
/*     */   
/*     */   @NotNull
/*     */   public Tube createClient(ClientTubeAssemblerContext context) {
/*  60 */     Tube head = context.createTransportTube();
/*  61 */     head = context.createSecurityTube(head);
/*  62 */     if (dump)
/*     */     {
/*     */       
/*  65 */       head = context.createDumpTube("client", System.out, head);
/*     */     }
/*  67 */     head = context.createWsaTube(head);
/*  68 */     head = context.createClientMUTube(head);
/*  69 */     head = context.createValidationTube(head);
/*  70 */     return context.createHandlerTube(head);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tube createServer(ServerTubeAssemblerContext context) {
/*  79 */     Tube head = context.getTerminalTube();
/*  80 */     head = context.createValidationTube(head);
/*  81 */     head = context.createHandlerTube(head);
/*  82 */     head = context.createMonitoringTube(head);
/*  83 */     head = context.createServerMUTube(head);
/*  84 */     head = context.createWsaTube(head);
/*  85 */     if (dump)
/*     */     {
/*     */       
/*  88 */       head = context.createDumpTube("server", System.out, head);
/*     */     }
/*  90 */     head = context.createSecurityTube(head);
/*  91 */     return head;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 100 */     boolean b = false;
/*     */     try {
/* 102 */       b = Boolean.getBoolean(StandaloneTubeAssembler.class.getName() + ".dump");
/* 103 */     } catch (Throwable t) {}
/*     */ 
/*     */     
/* 106 */     dump = b;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\pipe\StandaloneTubeAssembler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */