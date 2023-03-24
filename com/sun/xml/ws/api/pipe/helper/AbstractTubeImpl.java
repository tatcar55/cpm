/*     */ package com.sun.xml.ws.api.pipe.helper;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Pipe;
/*     */ import com.sun.xml.ws.api.pipe.PipeCloner;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractTubeImpl
/*     */   implements Tube, Pipe
/*     */ {
/*     */   protected AbstractTubeImpl() {}
/*     */   
/*     */   protected AbstractTubeImpl(AbstractTubeImpl that, TubeCloner cloner) {
/*  72 */     cloner.add(that, this);
/*     */   }
/*     */   
/*     */   protected final NextAction doInvoke(Tube next, Packet packet) {
/*  76 */     NextAction na = new NextAction();
/*  77 */     na.invoke(next, packet);
/*  78 */     return na;
/*     */   }
/*     */   
/*     */   protected final NextAction doInvokeAndForget(Tube next, Packet packet) {
/*  82 */     NextAction na = new NextAction();
/*  83 */     na.invokeAndForget(next, packet);
/*  84 */     return na;
/*     */   }
/*     */   
/*     */   protected final NextAction doReturnWith(Packet response) {
/*  88 */     NextAction na = new NextAction();
/*  89 */     na.returnWith(response);
/*  90 */     return na;
/*     */   }
/*     */   
/*     */   protected final NextAction doThrow(Packet response, Throwable t) {
/*  94 */     NextAction na = new NextAction();
/*  95 */     na.throwException(response, t);
/*  96 */     return na;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected final NextAction doSuspend() {
/* 101 */     NextAction na = new NextAction();
/* 102 */     na.suspend();
/* 103 */     return na;
/*     */   }
/*     */   
/*     */   protected final NextAction doSuspend(Runnable onExitRunnable) {
/* 107 */     NextAction na = new NextAction();
/* 108 */     na.suspend(onExitRunnable);
/* 109 */     return na;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   protected final NextAction doSuspend(Tube next) {
/* 114 */     NextAction na = new NextAction();
/* 115 */     na.suspend(next);
/* 116 */     return na;
/*     */   }
/*     */   
/*     */   protected final NextAction doSuspend(Tube next, Runnable onExitRunnable) {
/* 120 */     NextAction na = new NextAction();
/* 121 */     na.suspend(next, onExitRunnable);
/* 122 */     return na;
/*     */   }
/*     */   
/*     */   protected final NextAction doThrow(Throwable t) {
/* 126 */     NextAction na = new NextAction();
/* 127 */     na.throwException(t);
/* 128 */     return na;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet process(Packet p) {
/* 136 */     return Fiber.current().runSync(this, p);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final AbstractTubeImpl copy(PipeCloner cloner) {
/* 144 */     return copy((TubeCloner)cloner);
/*     */   }
/*     */   
/*     */   public abstract AbstractTubeImpl copy(TubeCloner paramTubeCloner);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\helper\AbstractTubeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */