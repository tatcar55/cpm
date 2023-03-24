/*    */ package com.sun.xml.ws.spi.db;
/*    */ 
/*    */ import java.lang.reflect.Field;
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
/*    */ public class FieldSetter
/*    */   extends PropertySetterBase
/*    */ {
/*    */   protected Field field;
/*    */   
/*    */   public FieldSetter(Field f) {
/* 59 */     this.field = f;
/* 60 */     this.type = f.getType();
/*    */   }
/*    */   
/*    */   public Field getField() {
/* 64 */     return this.field;
/*    */   }
/*    */   
/*    */   public void set(final Object instance, final Object resource) {
/* 68 */     if (this.field.isAccessible()) {
/*    */       try {
/* 70 */         this.field.set(instance, resource);
/* 71 */       } catch (Exception e) {
/*    */         
/* 73 */         e.printStackTrace();
/*    */       } 
/*    */     } else {
/*    */       try {
/* 77 */         AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*    */               public Object run() throws IllegalAccessException {
/* 79 */                 if (!FieldSetter.this.field.isAccessible()) {
/* 80 */                   FieldSetter.this.field.setAccessible(true);
/*    */                 }
/* 82 */                 FieldSetter.this.field.set(instance, resource);
/* 83 */                 return null;
/*    */               }
/*    */             });
/* 86 */       } catch (PrivilegedActionException e) {
/*    */         
/* 88 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public <A> A getAnnotation(Class<A> annotationType) {
/* 94 */     Class<A> c = annotationType;
/* 95 */     return this.field.getAnnotation(c);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\FieldSetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */