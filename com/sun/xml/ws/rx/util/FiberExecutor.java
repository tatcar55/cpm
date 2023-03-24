/*     */ package com.sun.xml.ws.rx.util;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Engine;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.commons.NamedThreadFactory;
/*     */ import com.sun.xml.ws.util.Pool;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FiberExecutor
/*     */ {
/*     */   private Pool<Tube> tubelinePool;
/*     */   private volatile Engine engine;
/*     */   
/*     */   private static class Schedule
/*     */   {
/*     */     private final Packet request;
/*     */     private final Fiber.CompletionCallback completionCallback;
/*     */     
/*     */     public Schedule(Packet request, Fiber.CompletionCallback completionCallback) {
/*  76 */       this.request = request;
/*  77 */       this.completionCallback = completionCallback;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*  82 */   private final List<Schedule> schedules = new LinkedList<Schedule>();
/*     */   private ExecutorService fiberExecutorService;
/*     */   
/*     */   public FiberExecutor(String id, Tube masterTubeline) {
/*  86 */     this.tubelinePool = (Pool<Tube>)new Pool.TubePool(masterTubeline);
/*  87 */     this.fiberExecutorService = Executors.newCachedThreadPool((ThreadFactory)new NamedThreadFactory(id + "-fiber-executor"));
/*  88 */     this.engine = new Engine(id, this.fiberExecutorService);
/*     */   }
/*     */   
/*     */   public Packet runSync(Packet request) {
/*  92 */     Tube tubeline = (Tube)this.tubelinePool.take();
/*     */     try {
/*  94 */       return this.engine.createFiber().runSync(tubeline, request);
/*     */     } finally {
/*  96 */       this.tubelinePool.recycle(tubeline);
/*     */     } 
/*     */   }
/*     */   
/*     */   public synchronized void schedule(Packet request, @NotNull Fiber.CompletionCallback callback) {
/* 101 */     this.schedules.add(new Schedule(request, callback));
/*     */   }
/*     */   
/*     */   public synchronized void startScheduledFibers() {
/* 105 */     Iterator<Schedule> iterator = this.schedules.iterator();
/* 106 */     while (iterator.hasNext()) {
/* 107 */       Schedule schedule = iterator.next();
/* 108 */       iterator.remove();
/*     */       
/* 110 */       start(schedule.request, schedule.completionCallback);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void start(Packet request, @NotNull final Fiber.CompletionCallback callback) {
/* 115 */     Fiber fiber = this.engine.createFiber();
/*     */ 
/*     */ 
/*     */     
/* 119 */     final Tube tube = (Tube)this.tubelinePool.take();
/* 120 */     fiber.start(tube, request, new Fiber.CompletionCallback()
/*     */         {
/*     */           public void onCompletion(@NotNull Packet response) {
/* 123 */             FiberExecutor.this.tubelinePool.recycle(tube);
/* 124 */             callback.onCompletion(response);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void onCompletion(@NotNull Throwable error) {
/* 130 */             callback.onCompletion(error);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void close() {
/* 136 */     Pool<Tube> tp = this.tubelinePool;
/* 137 */     if (tp != null) {
/*     */ 
/*     */ 
/*     */       
/* 141 */       Tube p = (Tube)tp.take();
/* 142 */       p.preDestroy();
/* 143 */       this.tubelinePool = null;
/* 144 */       this.engine = null;
/* 145 */       this.schedules.clear();
/*     */     } 
/*     */ 
/*     */     
/* 149 */     ExecutorService fes = this.fiberExecutorService;
/* 150 */     if (fes != null) {
/* 151 */       fes.shutdownNow();
/* 152 */       this.fiberExecutorService = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\r\\util\FiberExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */