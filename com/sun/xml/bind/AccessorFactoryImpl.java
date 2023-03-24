/*     */ package com.sun.xml.bind;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AccessorFactoryImpl
/*     */   implements InternalAccessorFactory
/*     */ {
/*  52 */   private static AccessorFactoryImpl instance = new AccessorFactoryImpl();
/*     */ 
/*     */   
/*     */   public static AccessorFactoryImpl getInstance() {
/*  56 */     return instance;
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
/*     */ 
/*     */   
/*     */   public Accessor createFieldAccessor(Class bean, Field field, boolean readOnly) {
/*  70 */     return readOnly ? (Accessor)new Accessor.ReadOnlyFieldReflection(field) : (Accessor)new Accessor.FieldReflection(field);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Accessor createFieldAccessor(Class bean, Field field, boolean readOnly, boolean supressWarning) {
/*  87 */     return readOnly ? (Accessor)new Accessor.ReadOnlyFieldReflection(field, supressWarning) : (Accessor)new Accessor.FieldReflection(field, supressWarning);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Accessor createPropertyAccessor(Class bean, Method getter, Method setter) {
/* 103 */     if (getter == null) {
/* 104 */       return (Accessor)new Accessor.SetterOnlyReflection(setter);
/*     */     }
/* 106 */     if (setter == null) {
/* 107 */       return (Accessor)new Accessor.GetterOnlyReflection(getter);
/*     */     }
/* 109 */     return (Accessor)new Accessor.GetterSetterReflection(getter, setter);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\AccessorFactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */