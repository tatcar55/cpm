/*     */ package com.sun.xml.ws.spi.db;
/*     */ 
/*     */ import java.lang.reflect.Field;
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
/*     */ 
/*     */ public class FieldGetter
/*     */   extends PropertyGetterBase
/*     */ {
/*     */   protected Field field;
/*     */   
/*     */   public FieldGetter(Field f) {
/*  60 */     this.field = f;
/*  61 */     this.type = f.getType();
/*     */   }
/*     */   
/*     */   public Field getField() {
/*  65 */     return this.field;
/*     */   }
/*     */   
/*     */   static class PrivilegedGetter implements PrivilegedExceptionAction {
/*     */     private Object value;
/*     */     private Field field;
/*     */     private Object instance;
/*     */     
/*     */     public PrivilegedGetter(Field field, Object instance) {
/*  74 */       this.field = field;
/*  75 */       this.instance = instance;
/*     */     }
/*     */     public Object run() throws IllegalAccessException {
/*  78 */       if (!this.field.isAccessible()) {
/*  79 */         this.field.setAccessible(true);
/*     */       }
/*  81 */       this.value = this.field.get(this.instance);
/*  82 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   public Object get(Object instance) {
/*  87 */     if (this.field.isAccessible())
/*     */       try {
/*  89 */         return this.field.get(instance);
/*  90 */       } catch (Exception e) {
/*     */         
/*  92 */         e.printStackTrace();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 104 */         return null;
/*     */       }   PrivilegedGetter privilegedGetter = new PrivilegedGetter(this.field, instance); try { AccessController.doPrivileged(privilegedGetter); }
/*     */     catch (PrivilegedActionException e)
/*     */     { e.printStackTrace(); }
/* 108 */      return privilegedGetter.value; } public <A> A getAnnotation(Class<A> annotationType) { Class<A> c = annotationType;
/* 109 */     return this.field.getAnnotation(c); }
/*     */ 
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\spi\db\FieldGetter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */