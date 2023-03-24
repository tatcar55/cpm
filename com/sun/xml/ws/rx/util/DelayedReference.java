/*    */ package com.sun.xml.ws.rx.util;
/*    */ 
/*    */ import java.util.concurrent.Delayed;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DelayedReference<V>
/*    */   implements Delayed
/*    */ {
/*    */   private final V data;
/*    */   private final long resumeTimeInMilliseconds;
/*    */   
/*    */   private DelayedReference(V data, long resumeTimeInMilliseconds) {
/* 65 */     this.data = data;
/* 66 */     this.resumeTimeInMilliseconds = resumeTimeInMilliseconds;
/*    */   }
/*    */   
/*    */   public DelayedReference(V data, long delay, TimeUnit timeUnit) {
/* 70 */     this(data, timeUnit.toMillis(delay) + System.currentTimeMillis());
/*    */   }
/*    */   
/*    */   public V getValue() {
/* 74 */     return this.data;
/*    */   }
/*    */   
/*    */   public long getDelay(TimeUnit unit) {
/* 78 */     return unit.convert(this.resumeTimeInMilliseconds - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
/*    */   }
/*    */   
/*    */   public int compareTo(Delayed other) {
/* 82 */     long thisDelay = this.resumeTimeInMilliseconds - System.currentTimeMillis();
/* 83 */     long thatDelay = other.getDelay(TimeUnit.MILLISECONDS);
/*    */     
/* 85 */     return (thisDelay < thatDelay) ? -1 : ((thisDelay == thatDelay) ? 0 : 1);
/*    */   }
/*    */   
/*    */   public DelayedReference<V> updateData(V data) {
/* 89 */     return new DelayedReference(data, this.resumeTimeInMilliseconds);
/*    */   }
/*    */   
/*    */   public DelayedReference<V> updateDelay(long newDelay, TimeUnit timeUnit) {
/* 93 */     return new DelayedReference(this.data, timeUnit.toMillis(newDelay) + System.currentTimeMillis());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\r\\util\DelayedReference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */