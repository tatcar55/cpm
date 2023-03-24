/*     */ package com.sun.xml.ws.transport.httpspi.servlet;
/*     */ 
/*     */ import com.sun.xml.ws.util.InjectionPlan;
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import javax.annotation.PostConstruct;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.spi.Invoker;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class InvokerImpl
/*     */   extends Invoker
/*     */ {
/*     */   private final Class implType;
/*     */   private final Object impl;
/*     */   private final Method postConstructMethod;
/*     */   
/*     */   InvokerImpl(Class implType) {
/*  67 */     this.implType = implType;
/*  68 */     this.postConstructMethod = findAnnotatedMethod(implType, (Class)PostConstruct.class);
/*     */     
/*     */     try {
/*  71 */       this.impl = implType.newInstance();
/*  72 */     } catch (InstantiationException e) {
/*  73 */       throw new WebServiceException(e);
/*  74 */     } catch (IllegalAccessException e) {
/*  75 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void invokeMethod(final Method method, final Object instance, Object... args) {
/*  83 */     if (method == null)
/*  84 */       return;  AccessController.doPrivileged(new PrivilegedAction<Void>() {
/*     */           public Void run() {
/*     */             try {
/*  87 */               if (!method.isAccessible()) {
/*  88 */                 method.setAccessible(true);
/*     */               }
/*  90 */               method.invoke(instance, args);
/*  91 */             } catch (IllegalAccessException e) {
/*  92 */               throw new WebServiceException(e);
/*  93 */             } catch (InvocationTargetException e) {
/*  94 */               throw new WebServiceException(e);
/*     */             } 
/*  96 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void inject(WebServiceContext webServiceContext) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
/* 102 */     InjectionPlan.buildInjectionPlan(this.implType, WebServiceContext.class, false).inject(this.impl, webServiceContext);
/*     */     
/* 104 */     invokeMethod(this.postConstructMethod, this.impl, new Object[0]);
/*     */   }
/*     */   
/*     */   public Object invoke(Method m, Object... args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
/* 108 */     return m.invoke(this.impl, args);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Method findAnnotatedMethod(Class clazz, Class<? extends Annotation> annType) {
/* 116 */     boolean once = false;
/* 117 */     Method r = null;
/* 118 */     for (Method method : clazz.getDeclaredMethods()) {
/* 119 */       if (method.getAnnotation(annType) != null) {
/* 120 */         if (once)
/* 121 */           throw new WebServiceException("Only one method should have the annotation" + annType); 
/* 122 */         if ((method.getParameterTypes()).length != 0)
/* 123 */           throw new WebServiceException("Method" + method + "shouldn't have any arguments"); 
/* 124 */         r = method;
/* 125 */         once = true;
/*     */       } 
/*     */     } 
/* 128 */     return r;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\InvokerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */