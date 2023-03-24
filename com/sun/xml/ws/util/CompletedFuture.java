/*    */ package com.sun.xml.ws.util;
/*    */ 
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.Future;
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
/*    */ public class CompletedFuture<T>
/*    */   implements Future<T>
/*    */ {
/*    */   private final T v;
/*    */   private final Throwable re;
/*    */   
/*    */   public CompletedFuture(T v, Throwable re) {
/* 58 */     this.v = v;
/* 59 */     this.re = re;
/*    */   }
/*    */   
/*    */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 63 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isCancelled() {
/* 67 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isDone() {
/* 71 */     return true;
/*    */   }
/*    */   
/*    */   public T get() throws ExecutionException {
/* 75 */     if (this.re != null) {
/* 76 */       throw new ExecutionException(this.re);
/*    */     }
/* 78 */     return this.v;
/*    */   }
/*    */   
/*    */   public T get(long timeout, TimeUnit unit) throws ExecutionException {
/* 82 */     return get();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\CompletedFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */