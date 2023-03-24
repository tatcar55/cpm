/*     */ package org.glassfish.gmbal.generic;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public class ClassAnalyzer
/*     */ {
/*  95 */   private static final Graph.Finder<Class<?>> finder = new Graph.Finder<Class<?>>() {
/*     */       public List<Class<?>> evaluate(Class<?> arg) {
/*  97 */         List<Class<?>> result = new ArrayList<Class<?>>();
/*  98 */         Class<?> sclass = arg.getSuperclass();
/*  99 */         if (sclass != null) {
/* 100 */           result.add(sclass);
/*     */         }
/* 102 */         for (Class<?> cls : arg.getInterfaces()) {
/* 103 */           result.add(cls);
/*     */         }
/* 105 */         return result;
/*     */       }
/*     */     };
/*     */   
/* 109 */   private static Map<Class<?>, ClassAnalyzer> caMap = new WeakHashMap<Class<?>, ClassAnalyzer>();
/*     */   private List<Class<?>> classInheritance;
/*     */   
/*     */   public static synchronized ClassAnalyzer getClassAnalyzer(Class<?> cls) {
/* 113 */     ClassAnalyzer result = caMap.get(cls);
/* 114 */     if (result == null) {
/* 115 */       result = new ClassAnalyzer(cls);
/* 116 */       caMap.put(cls, result);
/*     */     } 
/*     */     
/* 119 */     return result;
/*     */   }
/*     */ 
/*     */   
/* 123 */   private String contents = null;
/*     */   
/*     */   private ClassAnalyzer(Graph<Class<?>> gr) {
/* 126 */     List<Class<?>> result = new ArrayList<Class<?>>(gr.getPostorderList());
/*     */     
/* 128 */     Collections.reverse(result);
/* 129 */     this.classInheritance = result;
/*     */   }
/*     */   
/*     */   private ClassAnalyzer(Class<?> cls) {
/* 133 */     this(new Graph<Class<?>>(cls, finder));
/*     */   }
/*     */   
/*     */   public List<Class<?>> findClasses(Predicate<Class<?>> pred) {
/* 137 */     List<Class<?>> result = new ArrayList<Class<?>>();
/* 138 */     for (Class<?> c : this.classInheritance) {
/* 139 */       if (pred.evaluate(c)) {
/* 140 */         result.add(c);
/*     */       }
/*     */     } 
/*     */     
/* 144 */     return result;
/*     */   }
/*     */   
/*     */   private static List<Method> getDeclaredMethods(final Class<?> cls) {
/* 148 */     SecurityManager sman = System.getSecurityManager();
/* 149 */     if (sman == null) {
/* 150 */       return Arrays.asList(cls.getDeclaredMethods());
/*     */     }
/* 152 */     return AccessController.<List<Method>>doPrivileged(new PrivilegedAction<List<Method>>()
/*     */         {
/*     */           public List<Method> run() {
/* 155 */             return Arrays.asList(cls.getDeclaredMethods());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Method> findMethods(Predicate<Method> pred) {
/* 166 */     List<Method> result = new ArrayList<Method>();
/* 167 */     for (Class<?> c : this.classInheritance) {
/* 168 */       for (Method m : getDeclaredMethods(c)) {
/* 169 */         if (pred.evaluate(m)) {
/* 170 */           result.add(m);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized String toString() {
/* 180 */     if (this.contents == null) {
/* 181 */       StringBuilder sb = new StringBuilder();
/*     */       
/* 183 */       boolean first = true;
/* 184 */       sb.append("ClassAnalyzer[");
/* 185 */       for (Class<?> cls : this.classInheritance) {
/* 186 */         if (first) {
/* 187 */           first = false;
/*     */         } else {
/* 189 */           sb.append(" ");
/*     */         } 
/* 191 */         sb.append(cls.getName());
/*     */       } 
/* 193 */       sb.append("]");
/* 194 */       this.contents = sb.toString();
/*     */     } 
/*     */     
/* 197 */     return this.contents;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\ClassAnalyzer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */