/*     */ package org.glassfish.gmbal.generic;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
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
/*     */ public class MethodMonitorFactory
/*     */ {
/*     */   private static abstract class MethodMonitorBase
/*     */     implements MethodMonitor
/*     */   {
/*     */     private String name;
/*     */     
/*     */     MethodMonitorBase(String name) {
/*  56 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  61 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public abstract void enter(boolean param1Boolean, String param1String, Object... param1VarArgs);
/*     */ 
/*     */     
/*     */     public void info(boolean enabled, Object... args) {}
/*     */ 
/*     */     
/*     */     public abstract void exit(boolean param1Boolean, Object param1Object);
/*     */     
/*     */     public void exit(boolean enabled) {
/*  74 */       exit(enabled, null);
/*     */     }
/*     */ 
/*     */     
/*     */     public void clear() {}
/*     */   }
/*     */ 
/*     */   
/*  82 */   private static final MethodMonitor noopImpl = new MethodMonitorBase("NoOpImpl")
/*     */     {
/*     */       public void enter(boolean enabled, String name, Object... args) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public void exit(boolean enabled, Object result) {}
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static MethodMonitor getNoOp() {
/*  97 */     return noopImpl;
/*     */   }
/*     */   
/*     */   public static MethodMonitor compose(MethodMonitor... mms) {
/* 101 */     return new MethodMonitorBase("ComposeImpl")
/*     */       {
/*     */         public void enter(boolean enabled, String name, Object... args)
/*     */         {
/* 105 */           for (MethodMonitor mm : mms) {
/* 106 */             mm.enter(enabled, name, args);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void info(boolean enabled, Object... args) {
/* 112 */           for (MethodMonitor mm : mms) {
/* 113 */             mm.info(enabled, args);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void exit(boolean enabled, Object result) {
/* 119 */           for (MethodMonitor mm : mms) {
/* 120 */             mm.exit(enabled, result);
/*     */           }
/*     */         }
/*     */       };
/*     */   }
/*     */   
/* 126 */   public static final MethodMonitor operationTracer = new MethodMonitorBase("OperationTracer")
/*     */     {
/*     */       
/*     */       public void enter(boolean enabled, String name, Object... args)
/*     */       {
/* 131 */         OperationTracer.enter(name, args);
/*     */       }
/*     */ 
/*     */       
/*     */       public void exit(boolean enabled, Object result) {
/* 136 */         OperationTracer.exit();
/*     */       }
/*     */ 
/*     */       
/*     */       public void clear() {
/* 141 */         OperationTracer.clear();
/*     */       }
/*     */     };
/*     */   
/*     */   public static MethodMonitor dprintUtil(Class<?> cls) {
/* 146 */     final DprintUtil dputil = DprintUtil.getDprintUtil(cls);
/*     */     
/* 148 */     return new MethodMonitorBase("DprintUtil")
/*     */       {
/*     */         public void enter(boolean enabled, String name, Object... args) {
/* 151 */           if (enabled) {
/* 152 */             dputil.enter(name, args);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void info(boolean enabled, Object... args) {
/* 158 */           if (enabled) {
/* 159 */             dputil.info(args);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void exit(boolean enabled, Object result) {
/* 165 */           if (enabled) {
/* 166 */             dputil.exit(result);
/*     */           }
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static MethodMonitor standardImpl(Class<?> cls) {
/* 175 */     final DprintUtil dputil = DprintUtil.getDprintUtil(cls);
/*     */     
/* 177 */     return new MethodMonitorBase("StandardImpl")
/*     */       {
/*     */         public void enter(boolean enabled, String name, Object... args) {
/* 180 */           OperationTracer.enter(name, args);
/* 181 */           if (enabled) {
/* 182 */             dputil.enter(name, args);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void info(boolean enabled, Object... args) {
/* 188 */           if (enabled) {
/* 189 */             dputil.info(args);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void exit(boolean enabled, Object result) {
/* 195 */           OperationTracer.exit();
/* 196 */           if (enabled) {
/* 197 */             dputil.exit(result);
/*     */           }
/*     */         }
/*     */ 
/*     */         
/*     */         public void clear() {
/* 203 */           OperationTracer.clear();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/* 208 */   private static Map<Class<?>, MethodMonitor> stdMap = new WeakHashMap<Class<?>, MethodMonitor>();
/*     */ 
/*     */   
/*     */   public static MethodMonitor makeStandard(Class<?> cls) {
/* 212 */     MethodMonitor result = stdMap.get(cls);
/* 213 */     if (result == null) {
/* 214 */       result = standardImpl(cls);
/* 215 */       stdMap.put(cls, result);
/*     */     } 
/*     */     
/* 218 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\MethodMonitorFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */