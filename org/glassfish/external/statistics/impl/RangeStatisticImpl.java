/*     */ package org.glassfish.external.statistics.impl;
/*     */ 
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Map;
/*     */ import org.glassfish.external.statistics.RangeStatistic;
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
/*     */ public final class RangeStatisticImpl
/*     */   extends StatisticImpl
/*     */   implements RangeStatistic, InvocationHandler
/*     */ {
/*  53 */   private long currentVal = 0L;
/*  54 */   private long highWaterMark = Long.MIN_VALUE;
/*  55 */   private long lowWaterMark = Long.MAX_VALUE;
/*     */   
/*     */   private final long initCurrentVal;
/*     */   private final long initHighWaterMark;
/*     */   private final long initLowWaterMark;
/*  60 */   private final RangeStatistic rs = (RangeStatistic)Proxy.newProxyInstance(RangeStatistic.class.getClassLoader(), new Class[] { RangeStatistic.class }, this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RangeStatisticImpl(long curVal, long highMark, long lowMark, String name, String unit, String desc, long startTime, long sampleTime) {
/*  69 */     super(name, unit, desc, startTime, sampleTime);
/*  70 */     this.currentVal = curVal;
/*  71 */     this.initCurrentVal = curVal;
/*  72 */     this.highWaterMark = highMark;
/*  73 */     this.initHighWaterMark = highMark;
/*  74 */     this.lowWaterMark = lowMark;
/*  75 */     this.initLowWaterMark = lowMark;
/*     */   }
/*     */   
/*     */   public synchronized RangeStatistic getStatistic() {
/*  79 */     return this.rs;
/*     */   }
/*     */   
/*     */   public synchronized Map getStaticAsMap() {
/*  83 */     Map<String, Long> m = super.getStaticAsMap();
/*  84 */     m.put("current", Long.valueOf(getCurrent()));
/*  85 */     m.put("lowwatermark", Long.valueOf(getLowWaterMark()));
/*  86 */     m.put("highwatermark", Long.valueOf(getHighWaterMark()));
/*  87 */     return m;
/*     */   }
/*     */   
/*     */   public synchronized long getCurrent() {
/*  91 */     return this.currentVal;
/*     */   }
/*     */   
/*     */   public synchronized void setCurrent(long curVal) {
/*  95 */     this.currentVal = curVal;
/*  96 */     this.lowWaterMark = (curVal >= this.lowWaterMark) ? this.lowWaterMark : curVal;
/*  97 */     this.highWaterMark = (curVal >= this.highWaterMark) ? curVal : this.highWaterMark;
/*  98 */     this.sampleTime = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long getHighWaterMark() {
/* 105 */     return this.highWaterMark;
/*     */   }
/*     */   
/*     */   public synchronized void setHighWaterMark(long hwm) {
/* 109 */     this.highWaterMark = hwm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized long getLowWaterMark() {
/* 116 */     return this.lowWaterMark;
/*     */   }
/*     */   
/*     */   public synchronized void setLowWaterMark(long lwm) {
/* 120 */     this.lowWaterMark = lwm;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void reset() {
/* 125 */     super.reset();
/* 126 */     this.currentVal = this.initCurrentVal;
/* 127 */     this.highWaterMark = this.initHighWaterMark;
/* 128 */     this.lowWaterMark = this.initLowWaterMark;
/* 129 */     this.sampleTime = -1L;
/*     */   }
/*     */   
/*     */   public synchronized String toString() {
/* 133 */     return super.toString() + NEWLINE + "Current: " + getCurrent() + NEWLINE + "LowWaterMark: " + getLowWaterMark() + NEWLINE + "HighWaterMark: " + getHighWaterMark();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
/*     */     Object result;
/*     */     try {
/* 143 */       result = m.invoke(this, args);
/* 144 */     } catch (InvocationTargetException e) {
/* 145 */       throw e.getTargetException();
/* 146 */     } catch (Exception e) {
/* 147 */       throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
/*     */     } finally {}
/*     */ 
/*     */     
/* 151 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\statistics\impl\RangeStatisticImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */