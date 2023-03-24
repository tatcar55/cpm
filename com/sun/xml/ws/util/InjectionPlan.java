/*     */ package com.sun.xml.ws.util;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import javax.annotation.Resource;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class InjectionPlan<T, R>
/*     */ {
/*     */   public void inject(T instance, Callable<R> resource) {
/*     */     try {
/*  81 */       inject(instance, resource.call());
/*  82 */     } catch (Exception e) {
/*  83 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static class FieldInjectionPlan<T, R>
/*     */     extends InjectionPlan<T, R>
/*     */   {
/*     */     private final Field field;
/*     */ 
/*     */     
/*     */     public FieldInjectionPlan(Field field) {
/*  95 */       this.field = field;
/*     */     }
/*     */     
/*     */     public void inject(final T instance, final R resource) {
/*  99 */       AccessController.doPrivileged(new PrivilegedAction() {
/*     */             public Object run() {
/*     */               try {
/* 102 */                 if (!InjectionPlan.FieldInjectionPlan.this.field.isAccessible()) {
/* 103 */                   InjectionPlan.FieldInjectionPlan.this.field.setAccessible(true);
/*     */                 }
/* 105 */                 InjectionPlan.FieldInjectionPlan.this.field.set(instance, resource);
/* 106 */                 return null;
/* 107 */               } catch (IllegalAccessException e) {
/* 108 */                 throw new WebServiceException(e);
/*     */               } 
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class MethodInjectionPlan<T, R>
/*     */     extends InjectionPlan<T, R>
/*     */   {
/*     */     private final Method method;
/*     */ 
/*     */     
/*     */     public MethodInjectionPlan(Method method) {
/* 123 */       this.method = method;
/*     */     }
/*     */     
/*     */     public void inject(T instance, R resource) {
/* 127 */       InjectionPlan.invokeMethod(this.method, instance, new Object[] { resource });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void invokeMethod(final Method method, final Object instance, Object... args) {
/* 135 */     if (method == null)
/* 136 */       return;  AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/*     */             try {
/* 139 */               if (!method.isAccessible()) {
/* 140 */                 method.setAccessible(true);
/*     */               }
/* 142 */               method.invoke(instance, args);
/* 143 */             } catch (IllegalAccessException e) {
/* 144 */               throw new WebServiceException(e);
/* 145 */             } catch (InvocationTargetException e) {
/* 146 */               throw new WebServiceException(e);
/*     */             } 
/* 148 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Compositor<T, R>
/*     */     extends InjectionPlan<T, R>
/*     */   {
/*     */     private final Collection<InjectionPlan<T, R>> children;
/*     */     
/*     */     public Compositor(Collection<InjectionPlan<T, R>> children) {
/* 160 */       this.children = children;
/*     */     }
/*     */     
/*     */     public void inject(T instance, R res) {
/* 164 */       for (InjectionPlan<T, R> plan : this.children)
/* 165 */         plan.inject(instance, res); 
/*     */     }
/*     */     
/*     */     public void inject(T instance, Callable<R> resource) {
/* 169 */       if (!this.children.isEmpty()) {
/* 170 */         super.inject(instance, resource);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T, R> InjectionPlan<T, R> buildInjectionPlan(Class<? extends T> clazz, Class<R> resourceType, boolean isStatic) {
/* 184 */     List<InjectionPlan<T, R>> plan = new ArrayList<InjectionPlan<T, R>>();
/*     */     
/* 186 */     Class<?> cl = clazz;
/* 187 */     while (cl != Object.class) {
/* 188 */       for (Field field : cl.getDeclaredFields()) {
/* 189 */         Resource resource = field.<Resource>getAnnotation(Resource.class);
/* 190 */         if (resource != null && 
/* 191 */           isInjectionPoint(resource, field.getType(), "Incorrect type for field" + field.getName(), resourceType)) {
/*     */ 
/*     */ 
/*     */           
/* 195 */           if (isStatic && !Modifier.isStatic(field.getModifiers())) {
/* 196 */             throw new WebServiceException("Static resource " + resourceType + " cannot be injected to non-static " + field);
/*     */           }
/* 198 */           plan.add(new FieldInjectionPlan<T, R>(field));
/*     */         } 
/*     */       } 
/*     */       
/* 202 */       cl = cl.getSuperclass();
/*     */     } 
/*     */     
/* 205 */     cl = clazz;
/* 206 */     while (cl != Object.class) {
/* 207 */       for (Method method : cl.getDeclaredMethods()) {
/* 208 */         Resource resource = method.<Resource>getAnnotation(Resource.class);
/* 209 */         if (resource != null) {
/* 210 */           Class[] paramTypes = method.getParameterTypes();
/* 211 */           if (paramTypes.length != 1)
/* 212 */             throw new WebServiceException("Incorrect no of arguments for method " + method); 
/* 213 */           if (isInjectionPoint(resource, paramTypes[0], "Incorrect argument types for method" + method.getName(), resourceType)) {
/*     */ 
/*     */ 
/*     */             
/* 217 */             if (isStatic && !Modifier.isStatic(method.getModifiers())) {
/* 218 */               throw new WebServiceException("Static resource " + resourceType + " cannot be injected to non-static " + method);
/*     */             }
/* 220 */             plan.add(new MethodInjectionPlan<T, R>(method));
/*     */           } 
/*     */         } 
/*     */       } 
/* 224 */       cl = cl.getSuperclass();
/*     */     } 
/*     */     
/* 227 */     return new Compositor<T, R>(plan);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isInjectionPoint(Resource resource, Class fieldType, String errorMessage, Class<?> resourceType) {
/* 235 */     Class<?> t = resource.type();
/* 236 */     if (t.equals(Object.class))
/* 237 */       return fieldType.equals(resourceType); 
/* 238 */     if (t.equals(resourceType)) {
/* 239 */       if (fieldType.isAssignableFrom(resourceType)) {
/* 240 */         return true;
/*     */       }
/*     */       
/* 243 */       throw new WebServiceException(errorMessage);
/*     */     } 
/*     */     
/* 246 */     return false;
/*     */   }
/*     */   
/*     */   public abstract void inject(T paramT, R paramR);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\InjectionPlan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */