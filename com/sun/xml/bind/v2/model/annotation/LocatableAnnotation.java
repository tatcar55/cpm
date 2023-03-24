/*     */ package com.sun.xml.bind.v2.model.annotation;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.Location;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocatableAnnotation
/*     */   implements InvocationHandler, Locatable, Location
/*     */ {
/*     */   private final Annotation core;
/*     */   private final Locatable upstream;
/*     */   
/*     */   public static <A extends Annotation> A create(A annotation, Locatable parentSourcePos) {
/*  69 */     if (annotation == null) return null; 
/*  70 */     Class<? extends Annotation> type = annotation.annotationType();
/*  71 */     if (quicks.containsKey(type))
/*     */     {
/*  73 */       return (A)((Quick)quicks.get(type)).newInstance(parentSourcePos, (Annotation)annotation);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  78 */     ClassLoader cl = SecureLoader.getClassClassLoader(LocatableAnnotation.class);
/*     */     
/*     */     try {
/*  81 */       Class<?> loadableT = Class.forName(type.getName(), false, cl);
/*  82 */       if (loadableT != type) {
/*  83 */         return annotation;
/*     */       }
/*  85 */       return (A)Proxy.newProxyInstance(cl, new Class[] { type, Locatable.class }, new LocatableAnnotation((Annotation)annotation, parentSourcePos));
/*     */     
/*     */     }
/*  88 */     catch (ClassNotFoundException e) {
/*     */       
/*  90 */       return annotation;
/*  91 */     } catch (IllegalArgumentException e) {
/*     */ 
/*     */       
/*  94 */       return annotation;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   LocatableAnnotation(Annotation core, Locatable upstream) {
/* 100 */     this.core = core;
/* 101 */     this.upstream = upstream;
/*     */   }
/*     */   
/*     */   public Locatable getUpstream() {
/* 105 */     return this.upstream;
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 109 */     return this;
/*     */   }
/*     */   
/*     */   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
/*     */     try {
/* 114 */       if (method.getDeclaringClass() == Locatable.class)
/* 115 */         return method.invoke(this, args); 
/* 116 */       if (Modifier.isStatic(method.getModifiers()))
/*     */       {
/*     */ 
/*     */         
/* 120 */         throw new IllegalArgumentException();
/*     */       }
/* 122 */       return method.invoke(this.core, args);
/* 123 */     } catch (InvocationTargetException e) {
/* 124 */       if (e.getTargetException() != null)
/* 125 */         throw e.getTargetException(); 
/* 126 */       throw e;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String toString() {
/* 131 */     return this.core.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   private static final Map<Class, Quick> quicks = (Map)new HashMap<Class<?>, Quick>();
/*     */   
/*     */   static {
/* 141 */     for (Quick q : Init.getAll())
/* 142 */       quicks.put(q.annotationType(), q); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\LocatableAnnotation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */