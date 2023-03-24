/*    */ package org.glassfish.gmbal.logex;
/*    */ 
/*    */ import java.util.logging.Level;
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
/*    */ public enum LogLevel
/*    */ {
/* 54 */   CONFIG {
/*    */     public Level getLevel() {
/* 56 */       return Level.CONFIG;
/*    */     }
/*    */   },
/* 59 */   FINE {
/*    */     public Level getLevel() {
/* 61 */       return Level.FINE;
/*    */     }
/*    */   },
/* 64 */   FINER {
/*    */     public Level getLevel() {
/* 66 */       return Level.FINER;
/*    */     }
/*    */   },
/* 69 */   FINEST {
/*    */     public Level getLevel() {
/* 71 */       return Level.FINEST;
/*    */     }
/*    */   },
/* 74 */   INFO {
/*    */     public Level getLevel() {
/* 76 */       return Level.INFO;
/*    */     }
/*    */   },
/* 79 */   SEVERE {
/*    */     public Level getLevel() {
/* 81 */       return Level.SEVERE;
/*    */     }
/*    */   },
/* 84 */   WARNING { public Level getLevel() {
/* 85 */       return Level.WARNING;
/*    */     } }
/*    */   ;
/*    */   
/*    */   public abstract Level getLevel();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\logex\LogLevel.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */