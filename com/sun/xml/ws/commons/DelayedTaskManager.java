/*     */ package com.sun.xml.ws.commons;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.logging.Level;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DelayedTaskManager
/*     */ {
/*  58 */   private static final Logger LOGGER = Logger.getLogger(DelayedTaskManager.class);
/*     */ 
/*     */   
/*     */   private final ScheduledExecutorService executorService;
/*     */   
/*     */   private final AtomicBoolean isClosed;
/*     */ 
/*     */   
/*     */   public static DelayedTaskManager createSingleThreadedManager(String name) {
/*  67 */     return new DelayedTaskManager(Executors.newSingleThreadScheduledExecutor(createThreadFactory(name)));
/*     */   }
/*     */   
/*     */   public static DelayedTaskManager createManager(String name, int coreThreadPoolSize) {
/*  71 */     return new DelayedTaskManager(Executors.newScheduledThreadPool(coreThreadPoolSize, createThreadFactory(name)));
/*     */   }
/*     */   
/*     */   private static final ThreadFactory createThreadFactory(String name) {
/*  75 */     return new NamedThreadFactory(name);
/*     */   }
/*     */   
/*     */   private class Worker
/*     */     implements Runnable {
/*     */     public final DelayedTaskManager.DelayedTask task;
/*     */     
/*     */     public Worker(DelayedTaskManager.DelayedTask handler) {
/*  83 */       this.task = handler;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*  90 */       DelayedTaskManager.LOGGER.entering();
/*     */       
/*  92 */       if (DelayedTaskManager.LOGGER.isLoggable(Level.FINER)) {
/*  93 */         DelayedTaskManager.LOGGER.finer(String.format("Starting delayed execution of [ %s ]", new Object[] { this.task.getName() }));
/*     */       }
/*     */       try {
/*  96 */         this.task.run(DelayedTaskManager.this);
/*  97 */       } catch (Exception ex) {
/*  98 */         DelayedTaskManager.LOGGER.warning(String.format("An exception occured during execution of [ %s ]", new Object[] { this.task.getName() }), ex);
/*     */       } finally {
/* 100 */         if (DelayedTaskManager.LOGGER.isLoggable(Level.FINER)) {
/* 101 */           DelayedTaskManager.LOGGER.finer(String.format("Delayed execution of [ %s ] finished", new Object[] { this.task.getName() }));
/*     */         }
/*     */         
/* 104 */         DelayedTaskManager.LOGGER.exiting();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private DelayedTaskManager(ScheduledExecutorService executorService) {
/* 113 */     this.executorService = executorService;
/* 114 */     this.isClosed = new AtomicBoolean(false);
/*     */   }
/*     */   
/*     */   public boolean register(@NotNull DelayedTask task, long delay, TimeUnit timeUnit) {
/* 118 */     if (this.isClosed.get()) {
/* 119 */       LOGGER.finer(String.format("Attempt to register a new task has failed. This '%s' instance has already been closed", new Object[] { getClass().getName() }));
/* 120 */       return false;
/*     */     } 
/*     */     
/* 123 */     assert task != null;
/* 124 */     this.executorService.schedule(new Worker(task), delay, timeUnit);
/*     */     
/* 126 */     return true;
/*     */   }
/*     */   
/*     */   public void close() {
/* 130 */     if (this.isClosed.compareAndSet(false, true)) {
/* 131 */       this.executorService.shutdown();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/* 136 */     return this.isClosed.get();
/*     */   }
/*     */   
/*     */   public static interface DelayedTask {
/*     */     String getName();
/*     */     
/*     */     void run(DelayedTaskManager param1DelayedTaskManager);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\commons\DelayedTaskManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */