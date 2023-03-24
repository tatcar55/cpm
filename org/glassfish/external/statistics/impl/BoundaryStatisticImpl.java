/*     */ package org.glassfish.external.statistics.impl;
/*     */ 
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Map;
/*     */ import org.glassfish.external.statistics.BoundaryStatistic;
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
/*     */ public final class BoundaryStatisticImpl
/*     */   extends StatisticImpl
/*     */   implements BoundaryStatistic, InvocationHandler
/*     */ {
/*     */   private final long lowerBound;
/*     */   private final long upperBound;
/*  56 */   private final BoundaryStatistic bs = (BoundaryStatistic)Proxy.newProxyInstance(BoundaryStatistic.class.getClassLoader(), new Class[] { BoundaryStatistic.class }, this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BoundaryStatisticImpl(long lower, long upper, String name, String unit, String desc, long startTime, long sampleTime) {
/*  65 */     super(name, unit, desc, startTime, sampleTime);
/*  66 */     this.upperBound = upper;
/*  67 */     this.lowerBound = lower;
/*     */   }
/*     */   
/*     */   public synchronized BoundaryStatistic getStatistic() {
/*  71 */     return this.bs;
/*     */   }
/*     */   
/*     */   public synchronized Map getStaticAsMap() {
/*  75 */     Map<String, Long> m = super.getStaticAsMap();
/*  76 */     m.put("lowerbound", Long.valueOf(getLowerBound()));
/*  77 */     m.put("upperbound", Long.valueOf(getUpperBound()));
/*  78 */     return m;
/*     */   }
/*     */   
/*     */   public synchronized long getLowerBound() {
/*  82 */     return this.lowerBound;
/*     */   }
/*     */   
/*     */   public synchronized long getUpperBound() {
/*  86 */     return this.upperBound;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void reset() {
/*  91 */     super.reset();
/*  92 */     this.sampleTime = -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
/*     */     Object result;
/*     */     try {
/*  99 */       result = m.invoke(this, args);
/* 100 */     } catch (InvocationTargetException e) {
/* 101 */       throw e.getTargetException();
/* 102 */     } catch (Exception e) {
/* 103 */       throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
/*     */     } finally {}
/*     */ 
/*     */     
/* 107 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\statistics\impl\BoundaryStatisticImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */