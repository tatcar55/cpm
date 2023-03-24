/*     */ package org.glassfish.external.statistics.impl;
/*     */ 
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Map;
/*     */ import org.glassfish.external.statistics.CountStatistic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CountStatisticImpl
/*     */   extends StatisticImpl
/*     */   implements CountStatistic, InvocationHandler
/*     */ {
/*  52 */   private long count = 0L;
/*     */   
/*     */   private final long initCount;
/*  55 */   private final CountStatistic cs = (CountStatistic)Proxy.newProxyInstance(CountStatistic.class.getClassLoader(), new Class[] { CountStatistic.class }, this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CountStatisticImpl(long countVal, String name, String unit, String desc, long sampleTime, long startTime) {
/*  63 */     super(name, unit, desc, startTime, sampleTime);
/*  64 */     this.count = countVal;
/*  65 */     this.initCount = countVal;
/*     */   }
/*     */   
/*     */   public CountStatisticImpl(String name, String unit, String desc) {
/*  69 */     this(0L, name, unit, desc, -1L, System.currentTimeMillis());
/*     */   }
/*     */   
/*     */   public synchronized CountStatistic getStatistic() {
/*  73 */     return this.cs;
/*     */   }
/*     */   
/*     */   public synchronized Map getStaticAsMap() {
/*  77 */     Map<String, Long> m = super.getStaticAsMap();
/*  78 */     m.put("count", Long.valueOf(getCount()));
/*  79 */     return m;
/*     */   }
/*     */   
/*     */   public synchronized String toString() {
/*  83 */     return super.toString() + NEWLINE + "Count: " + getCount();
/*     */   }
/*     */   
/*     */   public synchronized long getCount() {
/*  87 */     return this.count;
/*     */   }
/*     */   
/*     */   public synchronized void setCount(long countVal) {
/*  91 */     this.count = countVal;
/*  92 */     this.sampleTime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public synchronized void increment() {
/*  96 */     this.count++;
/*  97 */     this.sampleTime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public synchronized void increment(long delta) {
/* 101 */     this.count += delta;
/* 102 */     this.sampleTime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public synchronized void decrement() {
/* 106 */     this.count--;
/* 107 */     this.sampleTime = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void reset() {
/* 112 */     super.reset();
/* 113 */     this.count = this.initCount;
/* 114 */     this.sampleTime = -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
/*     */     Object result;
/*     */     try {
/* 121 */       result = m.invoke(this, args);
/* 122 */     } catch (InvocationTargetException e) {
/* 123 */       throw e.getTargetException();
/* 124 */     } catch (Exception e) {
/* 125 */       throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
/*     */     } finally {}
/*     */ 
/*     */     
/* 129 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\statistics\impl\CountStatisticImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */