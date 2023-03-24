/*     */ package com.sun.xml.ws.spi.db;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MethodGetter
/*     */   extends PropertyGetterBase
/*     */ {
/*     */   private Method method;
/*     */   
/*     */   public MethodGetter(Method m) {
/*  59 */     this.method = m;
/*  60 */     this.type = m.getReturnType();
/*     */   }
/*     */   
/*     */   public Method getMethod() {
/*  64 */     return this.method;
/*     */   }
/*     */   
/*     */   public <A> A getAnnotation(Class<A> annotationType) {
/*  68 */     Class<A> c = annotationType;
/*  69 */     return this.method.getAnnotation(c);
/*     */   }
/*     */   
/*     */   static class PrivilegedGetter
/*     */     implements PrivilegedExceptionAction {
/*     */     private Object value;
/*     */     private Method method;
/*     */     private Object instance;
/*     */     
/*     */     public PrivilegedGetter(Method m, Object instance) {
/*  79 */       this.method = m;
/*  80 */       this.instance = instance;
/*     */     }
/*     */     public Object run() throws IllegalAccessException {
/*  83 */       if (!this.method.isAccessible()) {
/*  84 */         this.method.setAccessible(true);
/*     */       }
/*     */       try {
/*  87 */         this.value = this.method.invoke(this.instance, new Object[0]);
/*  88 */       } catch (Exception e) {
/*     */         
/*  90 */         e.printStackTrace();
/*     */       } 
/*  92 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   public Object get(Object instance) {
/*  97 */     Object[] args = new Object[0];
/*     */     try {
/*  99 */       if (this.method.isAccessible()) {
/* 100 */         return this.method.invoke(instance, args);
/*     */       }
/* 102 */       PrivilegedGetter privilegedGetter = new PrivilegedGetter(this.method, instance);
/*     */       try {
/* 104 */         AccessController.doPrivileged(privilegedGetter);
/* 105 */       } catch (PrivilegedActionException e) {
/*     */         
/* 107 */         e.printStackTrace();
/*     */       } 
/* 109 */       return privilegedGetter.value;
/*     */     }
/* 111 */     catch (Exception e) {
/*     */       
/* 113 */       e.printStackTrace();
/*     */       
/* 115 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\MethodGetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */