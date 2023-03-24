/*     */ package org.glassfish.external.statistics.impl;
/*     */ 
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Map;
/*     */ import org.glassfish.external.statistics.StringStatistic;
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
/*     */ public final class StringStatisticImpl
/*     */   extends StatisticImpl
/*     */   implements StringStatistic, InvocationHandler
/*     */ {
/*  52 */   private volatile String str = null;
/*     */   
/*     */   private final String initStr;
/*  55 */   private final StringStatistic ss = (StringStatistic)Proxy.newProxyInstance(StringStatistic.class.getClassLoader(), new Class[] { StringStatistic.class }, this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StringStatisticImpl(String str, String name, String unit, String desc, long sampleTime, long startTime) {
/*  63 */     super(name, unit, desc, startTime, sampleTime);
/*  64 */     this.str = str;
/*  65 */     this.initStr = str;
/*     */   }
/*     */   
/*     */   public StringStatisticImpl(String name, String unit, String desc) {
/*  69 */     this("", name, unit, desc, System.currentTimeMillis(), System.currentTimeMillis());
/*     */   }
/*     */   
/*     */   public synchronized StringStatistic getStatistic() {
/*  73 */     return this.ss;
/*     */   }
/*     */   
/*     */   public synchronized Map getStaticAsMap() {
/*  77 */     Map<String, String> m = super.getStaticAsMap();
/*  78 */     if (getCurrent() != null) {
/*  79 */       m.put("current", getCurrent());
/*     */     }
/*  81 */     return m;
/*     */   }
/*     */   
/*     */   public synchronized String toString() {
/*  85 */     return super.toString() + NEWLINE + "Current-value: " + getCurrent();
/*     */   }
/*     */   
/*     */   public String getCurrent() {
/*  89 */     return this.str;
/*     */   }
/*     */   
/*     */   public void setCurrent(String str) {
/*  93 */     this.str = str;
/*  94 */     this.sampleTime = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void reset() {
/*  99 */     super.reset();
/* 100 */     this.str = this.initStr;
/* 101 */     this.sampleTime = -1L;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
/*     */     Object result;
/*     */     try {
/* 108 */       result = m.invoke(this, args);
/* 109 */     } catch (InvocationTargetException e) {
/* 110 */       throw e.getTargetException();
/* 111 */     } catch (Exception e) {
/* 112 */       throw new RuntimeException("unexpected invocation exception: " + e.getMessage());
/*     */     } finally {}
/*     */ 
/*     */     
/* 116 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\statistics\impl\StringStatisticImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */