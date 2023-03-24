/*    */ package org.jvnet.mimepull;
/*    */ 
/*    */ import java.util.concurrent.Executor;
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
/*    */ public abstract class CleanUpExecutorFactory
/*    */ {
/* 46 */   private static final String DEFAULT_PROPERTY_NAME = CleanUpExecutorFactory.class.getName();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CleanUpExecutorFactory newInstance() {
/*    */     try {
/* 54 */       return (CleanUpExecutorFactory)FactoryFinder.find(DEFAULT_PROPERTY_NAME);
/* 55 */     } catch (Exception e) {
/* 56 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public abstract Executor getExecutor();
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\CleanUpExecutorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */