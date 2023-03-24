/*     */ package org.glassfish.external.statistics.impl;
/*     */ 
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Map;
/*     */ import org.glassfish.external.statistics.BoundedRangeStatistic;
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
/*     */ public final class BoundedRangeStatisticImpl
/*     */   extends StatisticImpl
/*     */   implements BoundedRangeStatistic, InvocationHandler
/*     */ {
/*  54 */   private long lowerBound = 0L;
/*  55 */   private long upperBound = 0L;
/*  56 */   private long currentVal = 0L;
/*  57 */   private long highWaterMark = Long.MIN_VALUE;
/*  58 */   private long lowWaterMark = Long.MAX_VALUE;
/*     */   
/*     */   private final long initLowerBound;
/*     */   
/*     */   private final long initUpperBound;
/*     */   private final long initCurrentVal;
/*     */   private final long initHighWaterMark;
/*     */   private final long initLowWaterMark;
/*  66 */   private final BoundedRangeStatistic bs = (BoundedRangeStatistic)Proxy.newProxyInstance(BoundedRangeStatistic.class.getClassLoader(), new Class[] { BoundedRangeStatistic.class }, this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized String toString() {
/*  73 */     return super.toString() + NEWLINE + "Current: " + getCurrent() + NEWLINE + "LowWaterMark: " + getLowWaterMark() + NEWLINE + "HighWaterMark: " + getHighWaterMark() + NEWLINE + "LowerBound: " + getLowerBound() + NEWLINE + "UpperBound: " + getUpperBound();
/*     */   }
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
/*     */   public BoundedRangeStatisticImpl(long curVal, long highMark, long lowMark, long upper, long lower, String name, String unit, String desc, long startTime, long sampleTime) {
/*  86 */     super(name, unit, desc, startTime, sampleTime);
/*  87 */     this.currentVal = curVal;
/*  88 */     this.initCurrentVal = curVal;
/*  89 */     this.highWaterMark = highMark;
/*  90 */     this.initHighWaterMark = highMark;
/*  91 */     this.lowWaterMark = lowMark;
/*  92 */     this.initLowWaterMark = lowMark;
/*  93 */     this.upperBound = upper;
/*  94 */     this.initUpperBound = upper;
/*  95 */     this.lowerBound = lower;
/*  96 */     this.initLowerBound = lower;
/*     */   }
/*     */   
/*     */   public synchronized BoundedRangeStatistic getStatistic() {
/* 100 */     return this.bs;
/*     */   }
/*     */   
/*     */   public synchronized Map getStaticAsMap() {
/* 104 */     Map<String, Long> m = super.getStaticAsMap();
/* 105 */     m.put("current", Long.valueOf(getCurrent()));
/* 106 */     m.put("lowerbound", Long.valueOf(getLowerBound()));
/* 107 */     m.put("upperbound", Long.valueOf(getUpperBound()));
/* 108 */     m.put("lowwatermark", Long.valueOf(getLowWaterMark()));
/* 109 */     m.put("highwatermark", Long.valueOf(getHighWaterMark()));
/* 110 */     return m;
/*     */   }
/*     */   
/*     */   public synchronized long getCurrent() {
/* 114 */     return this.currentVal;
/*     */   }
/*     */   
/*     */   public synchronized void setCurrent(long curVal) {
/* 118 */     this.currentVal = curVal;
/* 119 */     this.lowWaterMark = (curVal >= this.lowWaterMark) ? this.lowWaterMark : curVal;
/* 120 */     this.highWaterMark = (curVal >= this.highWaterMark) ? curVal : this.highWaterMark;
/* 121 */     this.sampleTime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public synchronized long getHighWaterMark() {
/* 125 */     return this.highWaterMark;
/*     */   }
/*     */   
/*     */   public synchronized void setHighWaterMark(long hwm) {
/* 129 */     this.highWaterMark = hwm;
/*     */   }
/*     */   
/*     */   public synchronized long getLowWaterMark() {
/* 133 */     return this.lowWaterMark;
/*     */   }
/*     */   
/*     */   public synchronized void setLowWaterMark(long lwm) {
/* 137 */     this.lowWaterMark = lwm;
/*     */   }
/*     */   
/*     */   public synchronized long getLowerBound() {
/* 141 */     return this.lowerBound;
/*     */   }
/*     */   
/*     */   public synchronized long getUpperBound() {
/* 145 */     return this.upperBound;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void reset() {
/* 150 */     super.reset();
/* 151 */     this.lowerBound = this.initLowerBound;
/* 152 */     this.upperBound = this.initUpperBound;
/* 153 */     this.currentVal = this.initCurrentVal;
/* 154 */     this.highWaterMark = this.initHighWaterMark;
/* 155 */     this.lowWaterMark = this.initLowWaterMark;
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\statistics\impl\BoundedRangeStatisticImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */