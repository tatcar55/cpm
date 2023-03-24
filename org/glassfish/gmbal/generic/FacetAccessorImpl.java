/*     */ package org.glassfish.gmbal.generic;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.glassfish.gmbal.GmbalException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FacetAccessorImpl
/*     */   implements FacetAccessor
/*     */ {
/*     */   private Object delegate;
/*  58 */   private Map<Class<?>, Object> facetMap = new HashMap<Class<?>, Object>();
/*     */ 
/*     */ 
/*     */   
/*     */   private MethodMonitor mm;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FacetAccessorImpl(Object delegate) {
/*  68 */     this.delegate = delegate;
/*  69 */     this.mm = MethodMonitorFactory.makeStandard(getClass());
/*     */   }
/*     */   
/*     */   public <T> T facet(Class<T> cls, boolean debug) {
/*  73 */     Object result = null;
/*  74 */     if (debug) this.mm.enter(debug, "facet", new Object[] { cls });
/*     */     
/*     */     try {
/*  77 */       if (cls.isInstance(this.delegate)) {
/*  78 */         result = this.delegate;
/*  79 */         if (debug) this.mm.info(debug, new Object[] { "result is delegate" }); 
/*     */       } else {
/*  81 */         result = this.facetMap.get(cls);
/*  82 */         if (debug) this.mm.info(debug, new Object[] { "result=", result });
/*     */       
/*     */       } 
/*  85 */       if (result == null) {
/*  86 */         return null;
/*     */       }
/*  88 */       return cls.cast(result);
/*     */     } finally {
/*     */       
/*  91 */       if (debug) this.mm.exit(debug, result); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection<Object> facets() {
/*  96 */     Collection<Object> result = new ArrayList();
/*  97 */     result.addAll(this.facetMap.values());
/*  98 */     result.add(this);
/*  99 */     return result;
/*     */   }
/*     */   
/*     */   public <T> void addFacet(final T obj) {
/* 103 */     if (obj.getClass().isInstance(this.delegate)) {
/* 104 */       throw new IllegalArgumentException("Cannot add facet of supertype of this object");
/*     */     }
/*     */ 
/*     */     
/* 108 */     ClassAnalyzer ca = ClassAnalyzer.getClassAnalyzer(obj.getClass());
/* 109 */     ca.findClasses(new Predicate<Class>()
/*     */         {
/*     */           public boolean evaluate(Class<?> arg) {
/* 112 */             FacetAccessorImpl.this.facetMap.put(arg, obj);
/* 113 */             return false;
/*     */           }
/*     */         });
/*     */   }
/*     */   public Object invoke(Method method, boolean debug, Object... args) {
/* 118 */     if (debug) this.mm.enter(debug, "invoke", new Object[] { method, args });
/*     */     
/* 120 */     Object result = null;
/*     */     try {
/* 122 */       Object target = facet(method.getDeclaringClass(), debug);
/* 123 */       if (target == null) {
/* 124 */         throw new IllegalArgumentException("No facet available for method " + method);
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 129 */         result = method.invoke(target, args);
/* 130 */       } catch (IllegalAccessException ex) {
/* 131 */         throw new GmbalException("Exception on invocation", ex);
/* 132 */       } catch (IllegalArgumentException ex) {
/* 133 */         throw new GmbalException("Exception on invocation", ex);
/* 134 */       } catch (InvocationTargetException ex) {
/* 135 */         throw new GmbalException("Exception on invocation", ex);
/*     */       } 
/*     */     } finally {
/* 138 */       if (debug) this.mm.exit(debug, result);
/*     */     
/*     */     } 
/* 141 */     return result;
/*     */   }
/*     */   
/*     */   public Object get(Field field, boolean debug) {
/* 145 */     if (debug) this.mm.enter(debug, "get", new Object[] { field });
/*     */     
/* 147 */     Object result = null;
/*     */     
/*     */     try {
/* 150 */       Object target = facet(field.getDeclaringClass(), debug);
/*     */       
/*     */       try {
/* 153 */         result = field.get(target);
/* 154 */       } catch (IllegalArgumentException ex) {
/* 155 */         throw new GmbalException("Exception on field get", ex);
/* 156 */       } catch (IllegalAccessException ex) {
/* 157 */         throw new GmbalException("Exception on field get", ex);
/*     */       } 
/*     */     } finally {
/* 160 */       if (debug) this.mm.exit(debug, result);
/*     */     
/*     */     } 
/* 163 */     return result;
/*     */   }
/*     */   
/*     */   public void set(Field field, Object value, boolean debug) {
/* 167 */     if (debug) this.mm.enter(debug, "set", new Object[] { field, value });
/*     */     
/*     */     try {
/* 170 */       Object target = facet(field.getDeclaringClass(), debug);
/*     */       
/*     */       try {
/* 173 */         field.set(target, value);
/* 174 */       } catch (IllegalArgumentException ex) {
/* 175 */         throw new GmbalException("Exception on field get", ex);
/* 176 */       } catch (IllegalAccessException ex) {
/* 177 */         throw new GmbalException("Exception on field get", ex);
/*     */       } 
/*     */     } finally {
/* 180 */       if (debug) this.mm.exit(debug); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeFacet(Class<?> cls) {
/* 185 */     if (cls.isInstance(this.delegate)) {
/* 186 */       throw new IllegalArgumentException("Cannot add facet of supertype of this object");
/*     */     }
/*     */ 
/*     */     
/* 190 */     ClassAnalyzer ca = ClassAnalyzer.getClassAnalyzer(cls);
/* 191 */     ca.findClasses(new Predicate<Class>()
/*     */         {
/*     */           public boolean evaluate(Class arg) {
/* 194 */             FacetAccessorImpl.this.facetMap.remove(arg);
/* 195 */             return false;
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\FacetAccessorImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */