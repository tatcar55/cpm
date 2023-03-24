/*     */ package com.sun.xml.ws.api.pipe;
/*     */ 
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.api.server.ContainerResolver;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Engine
/*     */ {
/*     */   private volatile Executor threadPool;
/*     */   public final String id;
/*     */   private final Container container;
/*     */   
/*     */   String getId() {
/*  64 */     return this.id;
/*  65 */   } Container getContainer() { return this.container; } Executor getExecutor() {
/*  66 */     return this.threadPool;
/*     */   }
/*     */   public Engine(String id, Executor threadPool) {
/*  69 */     this(id, ContainerResolver.getDefault().getContainer(), threadPool);
/*     */   }
/*     */   
/*     */   public Engine(String id, Container container, Executor threadPool) {
/*  73 */     this(id, container);
/*  74 */     this.threadPool = (threadPool != null) ? wrap(threadPool) : null;
/*     */   }
/*     */   
/*     */   public Engine(String id) {
/*  78 */     this(id, ContainerResolver.getDefault().getContainer());
/*     */   }
/*     */   
/*     */   public Engine(String id, Container container) {
/*  82 */     this.id = id;
/*  83 */     this.container = container;
/*     */   }
/*     */   
/*     */   public void setExecutor(Executor threadPool) {
/*  87 */     this.threadPool = (threadPool != null) ? wrap(threadPool) : null;
/*     */   }
/*     */   
/*     */   void addRunnable(Fiber fiber) {
/*  91 */     if (this.threadPool == null) {
/*  92 */       synchronized (this) {
/*  93 */         this.threadPool = wrap(Executors.newCachedThreadPool(new DaemonThreadFactory()));
/*     */       } 
/*     */     }
/*  96 */     this.threadPool.execute(fiber);
/*     */   }
/*     */   
/*     */   private Executor wrap(Executor ex) {
/* 100 */     return ContainerResolver.getDefault().wrapExecutor(this.container, ex);
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
/*     */   public Fiber createFiber() {
/* 113 */     return new Fiber(this);
/*     */   }
/*     */   
/*     */   private static class DaemonThreadFactory implements ThreadFactory {
/* 117 */     static final AtomicInteger poolNumber = new AtomicInteger(1);
/* 118 */     final AtomicInteger threadNumber = new AtomicInteger(1);
/*     */     final String namePrefix;
/*     */     
/*     */     DaemonThreadFactory() {
/* 122 */       this.namePrefix = "jaxws-engine-" + poolNumber.getAndIncrement() + "-thread-";
/*     */     }
/*     */     
/*     */     public Thread newThread(Runnable r) {
/* 126 */       Thread t = new Thread(null, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
/* 127 */       if (!t.isDaemon()) {
/* 128 */         t.setDaemon(true);
/*     */       }
/* 130 */       if (t.getPriority() != 5) {
/* 131 */         t.setPriority(5);
/*     */       }
/* 133 */       return t;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\pipe\Engine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */