/*     */ package com.sun.xml.bind.v2;
/*     */ 
/*     */ import com.sun.xml.bind.Util;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ClassFactory
/*     */ {
/*  66 */   private static final Class[] emptyClass = new Class[0];
/*  67 */   private static final Object[] emptyObject = new Object[0];
/*     */   
/*  69 */   private static final Logger logger = Util.getClassLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private static final ThreadLocal<Map<Class, WeakReference<Constructor>>> tls = new ThreadLocal<Map<Class, WeakReference<Constructor>>>()
/*     */     {
/*     */       public Map<Class, WeakReference<Constructor>> initialValue() {
/*  79 */         return (Map)new WeakHashMap<Class<?>, WeakReference<Constructor>>();
/*     */       }
/*     */     };
/*     */   
/*     */   public static void cleanCache() {
/*  84 */     if (tls != null) {
/*     */       try {
/*  86 */         tls.remove();
/*  87 */       } catch (Exception e) {
/*  88 */         logger.log(Level.WARNING, "Unable to clean Thread Local cache of classes used in Unmarshaller: {0}", e.getLocalizedMessage());
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T create0(Class<T> clazz) throws IllegalAccessException, InvocationTargetException, InstantiationException {
/*  97 */     Map<Class<?>, WeakReference<Constructor>> m = tls.get();
/*  98 */     Constructor<T> cons = null;
/*  99 */     WeakReference<Constructor> consRef = m.get(clazz);
/* 100 */     if (consRef != null)
/* 101 */       cons = consRef.get(); 
/* 102 */     if (cons == null) {
/*     */       try {
/* 104 */         cons = clazz.getDeclaredConstructor(emptyClass);
/* 105 */       } catch (NoSuchMethodException e) {
/* 106 */         NoSuchMethodError exp; logger.log(Level.INFO, "No default constructor found on " + clazz, e);
/*     */         
/* 108 */         if (clazz.getDeclaringClass() != null && !Modifier.isStatic(clazz.getModifiers())) {
/* 109 */           exp = new NoSuchMethodError(Messages.NO_DEFAULT_CONSTRUCTOR_IN_INNER_CLASS.format(new Object[] { clazz.getName() }));
/*     */         } else {
/* 111 */           exp = new NoSuchMethodError(e.getMessage());
/*     */         } 
/* 113 */         exp.initCause(e);
/* 114 */         throw exp;
/*     */       } 
/*     */       
/* 117 */       int classMod = clazz.getModifiers();
/*     */       
/* 119 */       if (!Modifier.isPublic(classMod) || !Modifier.isPublic(cons.getModifiers())) {
/*     */         
/*     */         try {
/* 122 */           cons.setAccessible(true);
/* 123 */         } catch (SecurityException e) {
/*     */           
/* 125 */           logger.log(Level.FINE, "Unable to make the constructor of " + clazz + " accessible", e);
/* 126 */           throw e;
/*     */         } 
/*     */       }
/*     */       
/* 130 */       m.put(clazz, new WeakReference<Constructor>(cons));
/*     */     } 
/*     */     
/* 133 */     return cons.newInstance(emptyObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T create(Class<T> clazz) {
/*     */     try {
/* 142 */       return create0(clazz);
/* 143 */     } catch (InstantiationException e) {
/* 144 */       logger.log(Level.INFO, "failed to create a new instance of " + clazz, e);
/* 145 */       throw new InstantiationError(e.toString());
/* 146 */     } catch (IllegalAccessException e) {
/* 147 */       logger.log(Level.INFO, "failed to create a new instance of " + clazz, e);
/* 148 */       throw new IllegalAccessError(e.toString());
/* 149 */     } catch (InvocationTargetException e) {
/* 150 */       Throwable target = e.getTargetException();
/*     */ 
/*     */ 
/*     */       
/* 154 */       if (target instanceof RuntimeException) {
/* 155 */         throw (RuntimeException)target;
/*     */       }
/*     */       
/* 158 */       if (target instanceof Error) {
/* 159 */         throw (Error)target;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 164 */       throw new IllegalStateException(target);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object create(Method method) {
/*     */     Throwable throwable;
/*     */     try {
/* 174 */       return method.invoke(null, emptyObject);
/* 175 */     } catch (InvocationTargetException ive) {
/* 176 */       Throwable target = ive.getTargetException();
/*     */       
/* 178 */       if (target instanceof RuntimeException) {
/* 179 */         throw (RuntimeException)target;
/*     */       }
/* 181 */       if (target instanceof Error) {
/* 182 */         throw (Error)target;
/*     */       }
/* 184 */       throw new IllegalStateException(target);
/* 185 */     } catch (IllegalAccessException e) {
/* 186 */       logger.log(Level.INFO, "failed to create a new instance of " + method.getReturnType().getName(), e);
/* 187 */       throw new IllegalAccessError(e.toString());
/* 188 */     } catch (IllegalArgumentException iae) {
/* 189 */       logger.log(Level.INFO, "failed to create a new instance of " + method.getReturnType().getName(), iae);
/* 190 */       throwable = iae;
/* 191 */     } catch (NullPointerException npe) {
/* 192 */       logger.log(Level.INFO, "failed to create a new instance of " + method.getReturnType().getName(), npe);
/* 193 */       throwable = npe;
/* 194 */     } catch (ExceptionInInitializerError eie) {
/* 195 */       logger.log(Level.INFO, "failed to create a new instance of " + method.getReturnType().getName(), eie);
/* 196 */       throwable = eie;
/*     */     } 
/*     */ 
/*     */     
/* 200 */     NoSuchMethodError exp = new NoSuchMethodError(throwable.getMessage());
/* 201 */     exp.initCause(throwable);
/* 202 */     throw exp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Class<? extends T> inferImplClass(Class<T> fieldType, Class[] knownImplClasses) {
/* 212 */     if (!fieldType.isInterface()) {
/* 213 */       return fieldType;
/*     */     }
/* 215 */     for (Class<?> impl : knownImplClasses) {
/* 216 */       if (fieldType.isAssignableFrom(impl)) {
/* 217 */         return impl.asSubclass(fieldType);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 223 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\ClassFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */