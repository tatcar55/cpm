/*     */ package org.glassfish.external.statistics.impl;
/*     */ 
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Map;
/*     */ import org.glassfish.external.statistics.TimeStatistic;
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
/*     */ public final class TimeStatisticImpl
/*     */   extends StatisticImpl
/*     */   implements TimeStatistic, InvocationHandler
/*     */ {
/*  54 */   private long count = 0L;
/*  55 */   private long maxTime = 0L;
/*  56 */   private long minTime = 0L;
/*  57 */   private long totTime = 0L;
/*     */   
/*     */   private final long initCount;
/*     */   private final long initMaxTime;
/*     */   private final long initMinTime;
/*     */   private final long initTotTime;
/*  63 */   private final TimeStatistic ts = (TimeStatistic)Proxy.newProxyInstance(TimeStatistic.class.getClassLoader(), new Class[] { TimeStatistic.class }, this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized String toString() {
/*  70 */     return super.toString() + NEWLINE + "Count: " + getCount() + NEWLINE + "MinTime: " + getMinTime() + NEWLINE + "MaxTime: " + getMaxTime() + NEWLINE + "TotalTime: " + getTotalTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeStatisticImpl(long counter, long maximumTime, long minimumTime, long totalTime, String name, String unit, String desc, long startTime, long sampleTime) {
/*  80 */     super(name, unit, desc, startTime, sampleTime);
/*  81 */     this.count = counter;
/*  82 */     this.initCount = counter;
/*  83 */     this.maxTime = maximumTime;
/*  84 */     this.initMaxTime = maximumTime;
/*  85 */     this.minTime = minimumTime;
/*  86 */     this.initMinTime = minimumTime;
/*  87 */     this.totTime = totalTime;
/*  88 */     this.initTotTime = totalTime;
/*     */   }
/*     */   
/*     */   public synchronized TimeStatistic getStatistic() {
/*  92 */     return this.ts;
/*     */   }
/*     */   
/*     */   public synchronized Map getStaticAsMap() {
/*  96 */     Map<String, Long> m = super.getStaticAsMap();
/*  97 */     m.put("count", Long.valueOf(getCount()));
/*  98 */     m.put("maxtime", Long.valueOf(getMaxTime()));
/*  99 */     m.put("mintime", Long.valueOf(getMinTime()));
/* 100 */     m.put("totaltime", Long.valueOf(getTotalTime()));
/* 101 */     return m;
/*     */   }
/*     */   
/*     */   public synchronized void incrementCount(long current) {
/* 105 */     if (this.count == 0L) {
/* 106 */       this.totTime = current;
/* 107 */       this.maxTime = current;
/* 108 */       this.minTime = current;
/*     */     } else {
/* 110 */       this.totTime += current;
/* 111 */       this.maxTime = (current >= this.maxTime) ? current : this.maxTime;
/* 112 */       this.minTime = (current >= this.minTime) ? this.minTime : current;
/*     */     } 
/* 114 */     this.count++;
/* 115 */     this.sampleTime = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long getCount() {
/* 122 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long getMaxTime() {
/* 130 */     return this.maxTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long getMinTime() {
/* 138 */     return this.minTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long getTotalTime() {
/* 146 */     return this.totTime;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void reset() {
/* 151 */     super.reset();
/* 152 */     this.count = this.initCount;
/* 153 */     this.maxTime = this.initMaxTime;
/* 154 */     this.minTime = this.initMinTime;
/* 155 */     this.totTime = this.initTotTime;
/* 156 */     this.sampleTime = -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
/*     */     Object result;
/*     */     try {
/* 163 */       result = m.invoke(this, args);
/* 164 */     } catch (InvocationTargetException e) {
/* 165 */       throw e.getTargetException();
/* 166 */     } catch (Exception e) {
/* 167 */       throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
/*     */     } finally {}
/*     */ 
/*     */     
/* 171 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\statistics\impl\TimeStatisticImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */