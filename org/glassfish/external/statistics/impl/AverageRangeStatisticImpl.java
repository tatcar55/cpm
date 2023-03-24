/*     */ package org.glassfish.external.statistics.impl;
/*     */ 
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Map;
/*     */ import org.glassfish.external.statistics.AverageRangeStatistic;
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
/*     */ public final class AverageRangeStatisticImpl
/*     */   extends StatisticImpl
/*     */   implements AverageRangeStatistic, InvocationHandler
/*     */ {
/*  59 */   private long currentVal = 0L;
/*  60 */   private long highWaterMark = Long.MIN_VALUE;
/*  61 */   private long lowWaterMark = Long.MAX_VALUE;
/*  62 */   private long numberOfSamples = 0L;
/*  63 */   private long runningTotal = 0L;
/*     */   
/*     */   private final long initCurrentVal;
/*     */   
/*     */   private final long initHighWaterMark;
/*     */   private final long initLowWaterMark;
/*     */   private final long initNumberOfSamples;
/*     */   private final long initRunningTotal;
/*  71 */   private final AverageRangeStatistic as = (AverageRangeStatistic)Proxy.newProxyInstance(AverageRangeStatistic.class.getClassLoader(), new Class[] { AverageRangeStatistic.class }, this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AverageRangeStatisticImpl(long curVal, long highMark, long lowMark, String name, String unit, String desc, long startTime, long sampleTime) {
/*  80 */     super(name, unit, desc, startTime, sampleTime);
/*  81 */     this.currentVal = curVal;
/*  82 */     this.initCurrentVal = curVal;
/*  83 */     this.highWaterMark = highMark;
/*  84 */     this.initHighWaterMark = highMark;
/*  85 */     this.lowWaterMark = lowMark;
/*  86 */     this.initLowWaterMark = lowMark;
/*  87 */     this.numberOfSamples = 0L;
/*  88 */     this.initNumberOfSamples = this.numberOfSamples;
/*  89 */     this.runningTotal = 0L;
/*  90 */     this.initRunningTotal = this.runningTotal;
/*     */   }
/*     */   
/*     */   public synchronized AverageRangeStatistic getStatistic() {
/*  94 */     return this.as;
/*     */   }
/*     */   
/*     */   public synchronized String toString() {
/*  98 */     return super.toString() + NEWLINE + "Current: " + getCurrent() + NEWLINE + "LowWaterMark: " + getLowWaterMark() + NEWLINE + "HighWaterMark: " + getHighWaterMark() + NEWLINE + "Average:" + getAverage();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Map getStaticAsMap() {
/* 106 */     Map<String, Long> m = super.getStaticAsMap();
/* 107 */     m.put("current", Long.valueOf(getCurrent()));
/* 108 */     m.put("lowwatermark", Long.valueOf(getLowWaterMark()));
/* 109 */     m.put("highwatermark", Long.valueOf(getHighWaterMark()));
/* 110 */     m.put("average", Long.valueOf(getAverage()));
/* 111 */     return m;
/*     */   }
/*     */   
/*     */   public synchronized void reset() {
/* 115 */     super.reset();
/* 116 */     this.currentVal = this.initCurrentVal;
/* 117 */     this.highWaterMark = this.initHighWaterMark;
/* 118 */     this.lowWaterMark = this.initLowWaterMark;
/* 119 */     this.numberOfSamples = this.initNumberOfSamples;
/* 120 */     this.runningTotal = this.initRunningTotal;
/* 121 */     this.sampleTime = -1L;
/*     */   }
/*     */   
/*     */   public synchronized long getAverage() {
/* 125 */     if (this.numberOfSamples == 0L) {
/* 126 */       return -1L;
/*     */     }
/* 128 */     return this.runningTotal / this.numberOfSamples;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized long getCurrent() {
/* 133 */     return this.currentVal;
/*     */   }
/*     */   
/*     */   public synchronized void setCurrent(long curVal) {
/* 137 */     this.currentVal = curVal;
/* 138 */     this.lowWaterMark = (curVal >= this.lowWaterMark) ? this.lowWaterMark : curVal;
/* 139 */     this.highWaterMark = (curVal >= this.highWaterMark) ? curVal : this.highWaterMark;
/* 140 */     this.numberOfSamples++;
/* 141 */     this.runningTotal += curVal;
/* 142 */     this.sampleTime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public synchronized long getHighWaterMark() {
/* 146 */     return this.highWaterMark;
/*     */   }
/*     */   
/*     */   public synchronized long getLowWaterMark() {
/* 150 */     return this.lowWaterMark;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/*     */     Object result;
/*     */     try {
/* 157 */       result = method.invoke(this, args);
/* 158 */     } catch (InvocationTargetException e) {
/* 159 */       throw e.getTargetException();
/* 160 */     } catch (Exception e) {
/* 161 */       throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
/*     */     } finally {}
/*     */ 
/*     */     
/* 165 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\statistics\impl\AverageRangeStatisticImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */