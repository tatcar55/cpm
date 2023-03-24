/*    */ package com.sun.xml.ws.commons;
/*    */ 
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class NamedThreadFactory
/*    */   implements ThreadFactory
/*    */ {
/*    */   private final ThreadGroup group;
/* 49 */   private final AtomicInteger threadNumber = new AtomicInteger(1);
/*    */   private final String namePrefix;
/*    */   private final boolean createDeamenoThreads;
/*    */   
/*    */   public NamedThreadFactory(String namePrefix) {
/* 54 */     this(namePrefix, true);
/*    */   }
/*    */   
/*    */   public NamedThreadFactory(String namePrefix, boolean createDaemonThreads) {
/* 58 */     SecurityManager securityManager = System.getSecurityManager();
/* 59 */     this.group = (securityManager != null) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
/* 60 */     this.namePrefix = namePrefix + "-thread-";
/* 61 */     this.createDeamenoThreads = createDaemonThreads;
/*    */   }
/*    */   
/*    */   public Thread newThread(Runnable task) {
/* 65 */     Thread newThread = new Thread(this.group, task, this.namePrefix + this.threadNumber.getAndIncrement());
/*    */     
/* 67 */     if (newThread.getPriority() != 5) {
/* 68 */       newThread.setPriority(5);
/*    */     }
/*    */     
/* 71 */     if (this.createDeamenoThreads && !newThread.isDaemon()) {
/* 72 */       newThread.setDaemon(true);
/*    */     }
/*    */     
/* 75 */     return newThread;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\commons\NamedThreadFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */