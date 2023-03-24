/*     */ package com.sun.xml.ws.commons;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ScheduledTaskManager
/*     */ {
/*  66 */   private static final Logger LOGGER = Logger.getLogger(ScheduledTaskManager.class);
/*  67 */   private static final AtomicInteger instanceNumber = new AtomicInteger(1);
/*     */   
/*     */   private static final long DELAY = 2000L;
/*     */   
/*     */   private static final long PERIOD = 100L;
/*     */   
/*     */   private final String name;
/*     */   
/*     */   private final ScheduledExecutorService executorService;
/*     */   private final Queue<ScheduledFuture<?>> scheduledTaskHandles;
/*     */   
/*     */   public ScheduledTaskManager(String name) {
/*  79 */     this.name = name.trim();
/*     */ 
/*     */     
/*  82 */     String threadNamePrefix = this.name.toLowerCase().replaceAll("\\s+", "-") + "-scheduler-" + instanceNumber.getAndIncrement();
/*     */     
/*  84 */     this.executorService = Executors.newScheduledThreadPool(1, new NamedThreadFactory(threadNamePrefix));
/*  85 */     this.scheduledTaskHandles = new ConcurrentLinkedQueue<ScheduledFuture<?>>();
/*     */   }
/*     */   
/*     */   public void stopAllTasks() {
/*     */     ScheduledFuture<?> handle;
/*  90 */     while ((handle = this.scheduledTaskHandles.poll()) != null) {
/*  91 */       handle.cancel(false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/*  99 */     stopAllTasks();
/*     */     
/* 101 */     this.executorService.shutdown();
/* 102 */     if (!this.executorService.isTerminated()) {
/*     */       try {
/* 104 */         Thread.sleep(2000L);
/* 105 */       } catch (InterruptedException ex) {
/* 106 */         LOGGER.fine("Interrupted while waiting for a scheduler to shut down.", ex);
/*     */       } 
/* 108 */       if (!this.executorService.isTerminated()) {
/* 109 */         this.executorService.shutdownNow();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> startTask(Runnable task, long initialDelay, long period) {
/* 122 */     ScheduledFuture<?> taskHandle = this.executorService.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.MILLISECONDS);
/* 123 */     if (!this.scheduledTaskHandles.offer(taskHandle))
/*     */     {
/* 125 */       LOGGER.warning(String.format("Unable to store handle for task of class [ %s ]", new Object[] { task.getClass().getName() }));
/*     */     }
/* 127 */     return taskHandle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> runOnce(Runnable task) {
/* 136 */     return startTask(task, 2000L, 100L);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\commons\ScheduledTaskManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */