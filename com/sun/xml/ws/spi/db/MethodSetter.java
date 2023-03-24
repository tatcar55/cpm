/*    */ package com.sun.xml.ws.spi.db;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedActionException;
/*    */ import java.security.PrivilegedExceptionAction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MethodSetter
/*    */   extends PropertySetterBase
/*    */ {
/*    */   private Method method;
/*    */   
/*    */   public MethodSetter(Method m) {
/* 59 */     this.method = m;
/* 60 */     this.type = m.getParameterTypes()[0];
/*    */   }
/*    */   
/*    */   public Method getMethod() {
/* 64 */     return this.method;
/*    */   }
/*    */   
/*    */   public <A> A getAnnotation(Class<A> annotationType) {
/* 68 */     Class<A> c = annotationType;
/* 69 */     return this.method.getAnnotation(c);
/*    */   }
/*    */   
/*    */   public void set(final Object instance, Object resource) {
/* 73 */     final Object[] args = { resource };
/* 74 */     if (this.method.isAccessible()) {
/*    */       try {
/* 76 */         this.method.invoke(instance, args);
/* 77 */       } catch (Exception e) {
/* 78 */         e.printStackTrace();
/*    */       } 
/*    */     } else {
/*    */       try {
/* 82 */         AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*    */               public Object run() throws IllegalAccessException {
/* 84 */                 if (!MethodSetter.this.method.isAccessible()) {
/* 85 */                   MethodSetter.this.method.setAccessible(true);
/*    */                 }
/*    */                 try {
/* 88 */                   MethodSetter.this.method.invoke(instance, args);
/* 89 */                 } catch (Exception e) {
/*    */                   
/* 91 */                   e.printStackTrace();
/*    */                 } 
/* 93 */                 return null;
/*    */               }
/*    */             });
/* 96 */       } catch (PrivilegedActionException e) {
/*    */         
/* 98 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\MethodSetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */