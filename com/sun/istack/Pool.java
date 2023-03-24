/*     */ package com.sun.istack;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Pool<T>
/*     */ {
/*     */   @NotNull
/*     */   T take();
/*     */   
/*     */   void recycle(@NotNull T paramT);
/*     */   
/*     */   public static abstract class Impl<T>
/*     */     implements Pool<T>
/*     */   {
/*     */     private volatile WeakReference<ConcurrentLinkedQueue<T>> queue;
/*     */     
/*     */     @NotNull
/*     */     public final T take() {
/*  89 */       T t = getQueue().poll();
/*  90 */       if (t == null) {
/*  91 */         return create();
/*     */       }
/*  93 */       return t;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final void recycle(T t) {
/* 100 */       getQueue().offer(t);
/*     */     }
/*     */     
/*     */     private ConcurrentLinkedQueue<T> getQueue() {
/* 104 */       WeakReference<ConcurrentLinkedQueue<T>> q = this.queue;
/* 105 */       if (q != null) {
/* 106 */         ConcurrentLinkedQueue<T> concurrentLinkedQueue = q.get();
/* 107 */         if (concurrentLinkedQueue != null) {
/* 108 */           return concurrentLinkedQueue;
/*     */         }
/*     */       } 
/*     */       
/* 112 */       ConcurrentLinkedQueue<T> d = new ConcurrentLinkedQueue<T>();
/* 113 */       this.queue = new WeakReference<ConcurrentLinkedQueue<T>>(d);
/*     */       
/* 115 */       return d;
/*     */     }
/*     */     
/*     */     @NotNull
/*     */     protected abstract T create();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\istack\Pool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */