/*     */ package org.glassfish.gmbal.generic;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DprintUtil
/*     */ {
/*     */   private static final boolean USE_LOGGER = false;
/*     */   private final String sourceClassName;
/*     */   private final String loggerName;
/*     */   
/*  58 */   private final ThreadLocal<Stack<String>> currentMethod = new ThreadLocal<Stack<String>>()
/*     */     {
/*     */       public Stack<String> initialValue()
/*     */       {
/*  62 */         return new Stack<String>();
/*     */       }
/*     */     };
/*     */   
/*  66 */   private static Map<String, DprintUtil> dpuMap = new WeakHashMap<String, DprintUtil>();
/*     */ 
/*     */   
/*     */   public static DprintUtil getDprintUtil(Class<?> cls) {
/*  70 */     String cname = cls.getName();
/*  71 */     DprintUtil result = dpuMap.get(cname);
/*  72 */     if (result == null) {
/*  73 */       result = new DprintUtil(cls);
/*  74 */       dpuMap.put(cname, result);
/*     */     } 
/*     */     
/*  77 */     return result;
/*     */   }
/*     */   
/*     */   private DprintUtil(Class selfClass) {
/*  81 */     this.sourceClassName = compressClassName(selfClass.getName());
/*     */ 
/*     */ 
/*     */     
/*  85 */     this.loggerName = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static String compressClassName(String name) {
/*  91 */     String prefix = "org.glassfish.gmbal.";
/*  92 */     if (name.startsWith(prefix)) {
/*  93 */       return "(GMBAL)." + name.substring(prefix.length());
/*     */     }
/*  95 */     return name;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void dprint(String msg) {
/* 100 */     String prefix = "(" + Thread.currentThread().getName() + "): ";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     System.out.println(prefix + this.sourceClassName + msg);
/*     */   }
/*     */ 
/*     */   
/*     */   private String makeString(Object... args) {
/* 112 */     if (args.length == 0) {
/* 113 */       return "";
/*     */     }
/*     */     
/* 116 */     StringBuilder sb = new StringBuilder();
/* 117 */     sb.append('(');
/* 118 */     boolean first = true;
/* 119 */     for (Object obj : args) {
/* 120 */       if (first) {
/* 121 */         first = false;
/*     */       } else {
/* 123 */         sb.append(' ');
/*     */       } 
/*     */       
/* 126 */       sb.append(Algorithms.convertToString(obj));
/*     */     } 
/* 128 */     sb.append(')');
/*     */     
/* 130 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public void enter(String mname, Object... args) {
/* 134 */     ((Stack<String>)this.currentMethod.get()).push(mname);
/* 135 */     String str = makeString(args);
/* 136 */     dprint("." + mname + "->" + str);
/*     */   }
/*     */   
/*     */   public void info(Object... args) {
/* 140 */     String mname = ((Stack<String>)this.currentMethod.get()).peek();
/* 141 */     String str = makeString(args);
/* 142 */     dprint("." + mname + "::" + str);
/*     */   }
/*     */   
/*     */   public void exit() {
/* 146 */     String mname = ((Stack<String>)this.currentMethod.get()).peek();
/* 147 */     dprint("." + mname + "<-");
/* 148 */     ((Stack)this.currentMethod.get()).pop();
/*     */   }
/*     */   
/*     */   public void exit(Object retVal) {
/* 152 */     String mname = ((Stack<String>)this.currentMethod.get()).peek();
/* 153 */     dprint("." + mname + "<-(" + retVal + ")");
/* 154 */     ((Stack)this.currentMethod.get()).pop();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\DprintUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */