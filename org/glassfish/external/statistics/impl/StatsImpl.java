/*    */ package org.glassfish.external.statistics.impl;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import org.glassfish.external.statistics.Statistic;
/*    */ import org.glassfish.external.statistics.Stats;
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
/*    */ public final class StatsImpl
/*    */   implements Stats
/*    */ {
/*    */   private final StatisticImpl[] statArray;
/*    */   
/*    */   protected StatsImpl(StatisticImpl[] statisticArray) {
/* 55 */     this.statArray = statisticArray;
/*    */   }
/*    */   
/*    */   public synchronized Statistic getStatistic(String statisticName) {
/* 59 */     Statistic stat = null;
/* 60 */     for (Statistic s : this.statArray) {
/* 61 */       if (s.getName().equals(statisticName)) {
/* 62 */         stat = s;
/*    */         break;
/*    */       } 
/*    */     } 
/* 66 */     return stat;
/*    */   }
/*    */   
/*    */   public synchronized String[] getStatisticNames() {
/* 70 */     ArrayList<String> list = new ArrayList();
/* 71 */     for (Statistic s : this.statArray) {
/* 72 */       list.add(s.getName());
/*    */     }
/* 74 */     String[] strArray = new String[list.size()];
/* 75 */     return list.<String>toArray(strArray);
/*    */   }
/*    */   
/*    */   public synchronized Statistic[] getStatistics() {
/* 79 */     return (Statistic[])this.statArray;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void reset() {
/* 86 */     for (StatisticImpl s : this.statArray)
/* 87 */       s.reset(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\external\statistics\impl\StatsImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */