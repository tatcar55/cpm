/*     */ package org.glassfish.gmbal.generic;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Formatter;
/*     */ import java.util.List;
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
/*     */ public class OperationTracer
/*     */ {
/*     */   private static boolean enabled = true;
/*     */   
/*     */   public static void enable() {
/*  51 */     enabled = true;
/*     */   }
/*     */   
/*     */   public static void disable() {
/*  55 */     enabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  60 */   private static ThreadLocal<List<Pair<String, Object[]>>> state = new ThreadLocal<List<Pair<String, Object[]>>>()
/*     */     {
/*     */       public List<Pair<String, Object[]>> initialValue() {
/*  63 */         return new ArrayList<Pair<String, Object[]>>();
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private static String format(Pair<String, Object[]> arg) {
/*  69 */     String name = arg.first();
/*  70 */     Object[] args = arg.second();
/*  71 */     StringBuilder sb = new StringBuilder();
/*  72 */     if (name == null) {
/*  73 */       sb.append("!NULL_NAME!");
/*     */     } else {
/*  75 */       sb.append(name);
/*     */     } 
/*     */     
/*  78 */     sb.append('(');
/*  79 */     boolean first = true;
/*  80 */     for (Object obj : args) {
/*  81 */       if (first) {
/*  82 */         first = false;
/*     */       } else {
/*  84 */         sb.append(',');
/*     */       } 
/*     */       
/*  87 */       sb.append(Algorithms.convertToString(obj));
/*     */     } 
/*  89 */     sb.append(')');
/*  90 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getAsString() {
/*  97 */     StringBuilder sb = new StringBuilder();
/*  98 */     Formatter fmt = new Formatter(sb);
/*  99 */     List<Pair<String, Object[]>> elements = state.get();
/* 100 */     int ctr = 0;
/* 101 */     for (Pair<String, Object[]> elem : elements) {
/* 102 */       fmt.format("\n\t(%3d): %s", new Object[] { Integer.valueOf(ctr++), format(elem) });
/*     */     } 
/*     */     
/* 105 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static void clear() {
/* 109 */     if (enabled) {
/* 110 */       ((List)state.get()).clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void enter(String name, Object... args) {
/* 115 */     if (enabled) {
/* 116 */       ((List)state.get()).add(new Pair<String, Object>(name, args));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void exit() {
/* 121 */     if (enabled) {
/* 122 */       List<Pair<String, Object[]>> elements = state.get();
/* 123 */       int size = elements.size();
/* 124 */       if (size > 0)
/* 125 */         elements.remove(size - 1); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\OperationTracer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */