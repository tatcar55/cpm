/*     */ package com.sun.xml.bind.v2.model.annotation;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
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
/*     */ public final class RuntimeInlineAnnotationReader
/*     */   extends AbstractInlineAnnotationReaderImpl<Type, Class, Field, Method>
/*     */   implements RuntimeAnnotationReader
/*     */ {
/*     */   public <A extends Annotation> A getFieldAnnotation(Class<A> annotation, Field field, Locatable srcPos) {
/*  61 */     return LocatableAnnotation.create(field.getAnnotation(annotation), srcPos);
/*     */   }
/*     */   
/*     */   public boolean hasFieldAnnotation(Class<? extends Annotation> annotationType, Field field) {
/*  65 */     return field.isAnnotationPresent(annotationType);
/*     */   }
/*     */   
/*     */   public boolean hasClassAnnotation(Class clazz, Class<? extends Annotation> annotationType) {
/*  69 */     return clazz.isAnnotationPresent(annotationType);
/*     */   }
/*     */   
/*     */   public Annotation[] getAllFieldAnnotations(Field field, Locatable srcPos) {
/*  73 */     Annotation[] r = field.getAnnotations();
/*  74 */     for (int i = 0; i < r.length; i++) {
/*  75 */       r[i] = LocatableAnnotation.create(r[i], srcPos);
/*     */     }
/*  77 */     return r;
/*     */   }
/*     */   
/*     */   public <A extends Annotation> A getMethodAnnotation(Class<A> annotation, Method method, Locatable srcPos) {
/*  81 */     return LocatableAnnotation.create(method.getAnnotation(annotation), srcPos);
/*     */   }
/*     */   
/*     */   public boolean hasMethodAnnotation(Class<? extends Annotation> annotation, Method method) {
/*  85 */     return method.isAnnotationPresent(annotation);
/*     */   }
/*     */   
/*     */   public Annotation[] getAllMethodAnnotations(Method method, Locatable srcPos) {
/*  89 */     Annotation[] r = method.getAnnotations();
/*  90 */     for (int i = 0; i < r.length; i++) {
/*  91 */       r[i] = LocatableAnnotation.create(r[i], srcPos);
/*     */     }
/*  93 */     return r;
/*     */   }
/*     */   
/*     */   public <A extends Annotation> A getMethodParameterAnnotation(Class<A> annotation, Method method, int paramIndex, Locatable srcPos) {
/*  97 */     Annotation[] pa = method.getParameterAnnotations()[paramIndex];
/*  98 */     for (Annotation a : pa) {
/*  99 */       if (a.annotationType() == annotation)
/* 100 */         return LocatableAnnotation.create((A)a, srcPos); 
/*     */     } 
/* 102 */     return null;
/*     */   }
/*     */   
/*     */   public <A extends Annotation> A getClassAnnotation(Class<A> a, Class clazz, Locatable srcPos) {
/* 106 */     return LocatableAnnotation.create((A)clazz.getAnnotation(a), srcPos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 113 */   private final Map<Class<? extends Annotation>, Map<Package, Annotation>> packageCache = new HashMap<Class<? extends Annotation>, Map<Package, Annotation>>();
/*     */ 
/*     */   
/*     */   public <A extends Annotation> A getPackageAnnotation(Class<A> a, Class clazz, Locatable srcPos) {
/* 117 */     Package p = clazz.getPackage();
/* 118 */     if (p == null) return null;
/*     */     
/* 120 */     Map<Package, Annotation> cache = this.packageCache.get(a);
/* 121 */     if (cache == null) {
/* 122 */       cache = new HashMap<Package, Annotation>();
/* 123 */       this.packageCache.put(a, cache);
/*     */     } 
/*     */     
/* 126 */     if (cache.containsKey(p)) {
/* 127 */       return (A)cache.get(p);
/*     */     }
/* 129 */     A ann = LocatableAnnotation.create(p.getAnnotation(a), srcPos);
/* 130 */     cache.put(p, (Annotation)ann);
/* 131 */     return ann;
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getClassValue(Annotation a, String name) {
/*     */     try {
/* 137 */       return (Class)a.annotationType().getMethod(name, new Class[0]).invoke(a, new Object[0]);
/* 138 */     } catch (IllegalAccessException e) {
/*     */       
/* 140 */       throw new IllegalAccessError(e.getMessage());
/* 141 */     } catch (InvocationTargetException e) {
/*     */       
/* 143 */       throw new InternalError(Messages.CLASS_NOT_FOUND.format(new Object[] { a.annotationType(), e.getMessage() }));
/* 144 */     } catch (NoSuchMethodException e) {
/* 145 */       throw new NoSuchMethodError(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public Class[] getClassArrayValue(Annotation a, String name) {
/*     */     try {
/* 151 */       return (Class[])a.annotationType().getMethod(name, new Class[0]).invoke(a, new Object[0]);
/* 152 */     } catch (IllegalAccessException e) {
/*     */       
/* 154 */       throw new IllegalAccessError(e.getMessage());
/* 155 */     } catch (InvocationTargetException e) {
/*     */       
/* 157 */       throw new InternalError(e.getMessage());
/* 158 */     } catch (NoSuchMethodException e) {
/* 159 */       throw new NoSuchMethodError(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String fullName(Method m) {
/* 164 */     return m.getDeclaringClass().getName() + '#' + m.getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\annotation\RuntimeInlineAnnotationReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */