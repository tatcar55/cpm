/*    */ package com.sun.xml.ws.commons;
/*    */ 
/*    */ import com.sun.istack.NotNull;
/*    */ import java.util.concurrent.TimeUnit;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum MaintenanceTaskExecutor
/*    */ {
/* 52 */   INSTANCE;
/*    */   
/*    */   private DelayedTaskManager delayedTaskManager;
/*    */   
/*    */   MaintenanceTaskExecutor() {
/* 57 */     this.delayedTaskManager = DelayedTaskManager.createManager("maintenance-task-executor", 5);
/*    */   }
/*    */   
/*    */   public boolean register(@NotNull DelayedTaskManager.DelayedTask task, long delay, TimeUnit timeUnit) {
/* 61 */     return this.delayedTaskManager.register(task, delay, timeUnit);
/*    */   }
/*    */   
/*    */   public boolean isClosed() {
/* 65 */     return this.delayedTaskManager.isClosed();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\commons\MaintenanceTaskExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */