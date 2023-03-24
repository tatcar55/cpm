/*     */ package org.glassfish.external.statistics.impl;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import org.glassfish.external.statistics.Statistic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StatisticImpl
/*     */   implements Statistic
/*     */ {
/*     */   private final String statisticName;
/*     */   private final String statisticUnit;
/*     */   private final String statisticDesc;
/*  55 */   protected long sampleTime = -1L;
/*     */   
/*     */   private long startTime;
/*     */   public static final String UNIT_COUNT = "count";
/*     */   public static final String UNIT_SECOND = "second";
/*     */   public static final String UNIT_MILLISECOND = "millisecond";
/*     */   public static final String UNIT_MICROSECOND = "microsecond";
/*     */   public static final String UNIT_NANOSECOND = "nanosecond";
/*     */   public static final String START_TIME = "starttime";
/*     */   public static final String LAST_SAMPLE_TIME = "lastsampletime";
/*  65 */   protected final Map<String, Object> statMap = new ConcurrentHashMap<String, Object>();
/*     */   
/*  67 */   protected static final String NEWLINE = System.getProperty("line.separator");
/*     */ 
/*     */ 
/*     */   
/*     */   protected StatisticImpl(String name, String unit, String desc, long start_time, long sample_time) {
/*  72 */     if (isValidString(name)) {
/*  73 */       this.statisticName = name;
/*     */     } else {
/*  75 */       this.statisticName = "name";
/*     */     } 
/*     */     
/*  78 */     if (isValidString(unit)) {
/*  79 */       this.statisticUnit = unit;
/*     */     } else {
/*  81 */       this.statisticUnit = "unit";
/*     */     } 
/*     */     
/*  84 */     if (isValidString(desc)) {
/*  85 */       this.statisticDesc = desc;
/*     */     } else {
/*  87 */       this.statisticDesc = "description";
/*     */     } 
/*     */     
/*  90 */     this.startTime = start_time;
/*  91 */     this.sampleTime = sample_time;
/*     */   }
/*     */   
/*     */   protected StatisticImpl(String name, String unit, String desc) {
/*  95 */     this(name, unit, desc, System.currentTimeMillis(), System.currentTimeMillis());
/*     */   }
/*     */   
/*     */   public synchronized Map getStaticAsMap() {
/*  99 */     if (isValidString(this.statisticName)) {
/* 100 */       this.statMap.put("name", this.statisticName);
/*     */     }
/* 102 */     if (isValidString(this.statisticUnit)) {
/* 103 */       this.statMap.put("unit", this.statisticUnit);
/*     */     }
/* 105 */     if (isValidString(this.statisticDesc)) {
/* 106 */       this.statMap.put("description", this.statisticDesc);
/*     */     }
/* 108 */     this.statMap.put("starttime", Long.valueOf(this.startTime));
/* 109 */     this.statMap.put("lastsampletime", Long.valueOf(this.sampleTime));
/* 110 */     return this.statMap;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 114 */     return this.statisticName;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 118 */     return this.statisticDesc;
/*     */   }
/*     */   
/*     */   public String getUnit() {
/* 122 */     return this.statisticUnit;
/*     */   }
/*     */   
/*     */   public synchronized long getLastSampleTime() {
/* 126 */     return this.sampleTime;
/*     */   }
/*     */   
/*     */   public synchronized long getStartTime() {
/* 130 */     return this.startTime;
/*     */   }
/*     */   
/*     */   public synchronized void reset() {
/* 134 */     this.startTime = System.currentTimeMillis();
/*     */   }
/*     */   
/*     */   public synchronized String toString() {
/* 138 */     return "Statistic " + getClass().getName() + NEWLINE + "Name: " + getName() + NEWLINE + "Description: " + getDescription() + NEWLINE + "Unit: " + getUnit() + NEWLINE + "LastSampleTime: " + getLastSampleTime() + NEWLINE + "StartTime: " + getStartTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isValidString(String str) {
/* 147 */     return (str != null && str.length() > 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\statistics\impl\StatisticImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */