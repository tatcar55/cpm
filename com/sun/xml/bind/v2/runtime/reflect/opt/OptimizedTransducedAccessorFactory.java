/*     */ package com.sun.xml.bind.v2.runtime.reflect.opt;
/*     */ 
/*     */ import com.sun.xml.bind.Util;
/*     */ import com.sun.xml.bind.v2.bytecode.ClassTailor;
/*     */ import com.sun.xml.bind.v2.model.core.TypeInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeClassInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.TransducedAccessor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ public abstract class OptimizedTransducedAccessorFactory
/*     */ {
/*  71 */   private static final Logger logger = Util.getClassLogger();
/*     */   
/*     */   private static final String fieldTemplateName;
/*     */   private static final String methodTemplateName;
/*     */   
/*     */   static {
/*  77 */     String s = TransducedAccessor_field_Byte.class.getName();
/*  78 */     fieldTemplateName = s.substring(0, s.length() - "Byte".length()).replace('.', '/');
/*     */     
/*  80 */     s = TransducedAccessor_method_Byte.class.getName();
/*  81 */     methodTemplateName = s.substring(0, s.length() - "Byte".length()).replace('.', '/');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final TransducedAccessor get(RuntimePropertyInfo prop) {
/*  91 */     Accessor acc = prop.getAccessor();
/*     */ 
/*     */     
/*  94 */     Class<?> opt = null;
/*     */     
/*  96 */     TypeInfo<Type, Class<?>> parent = prop.parent();
/*  97 */     if (!(parent instanceof RuntimeClassInfo)) {
/*  98 */       return null;
/*     */     }
/* 100 */     Class dc = (Class)((RuntimeClassInfo)parent).getClazz();
/* 101 */     String newClassName = ClassTailor.toVMClassName(dc) + "_JaxbXducedAccessor_" + prop.getName();
/*     */ 
/*     */     
/* 104 */     if (acc instanceof Accessor.FieldReflection) {
/*     */       
/* 106 */       Accessor.FieldReflection racc = (Accessor.FieldReflection)acc;
/* 107 */       Field field = racc.f;
/*     */       
/* 109 */       int mods = field.getModifiers();
/* 110 */       if (Modifier.isPrivate(mods) || Modifier.isFinal(mods))
/*     */       {
/*     */         
/* 113 */         return null;
/*     */       }
/* 115 */       Class<?> t = field.getType();
/* 116 */       if (t.isPrimitive()) {
/* 117 */         opt = AccessorInjector.prepare(dc, fieldTemplateName + (String)suffixMap.get(t), newClassName, new String[] { ClassTailor.toVMClassName(Bean.class), ClassTailor.toVMClassName(dc), "f_" + t.getName(), field.getName() });
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     if (acc.getClass() == Accessor.GetterSetterReflection.class) {
/* 127 */       Accessor.GetterSetterReflection gacc = (Accessor.GetterSetterReflection)acc;
/*     */       
/* 129 */       if (gacc.getter == null || gacc.setter == null) {
/* 130 */         return null;
/*     */       }
/* 132 */       Class<?> t = gacc.getter.getReturnType();
/*     */       
/* 134 */       if (Modifier.isPrivate(gacc.getter.getModifiers()) || Modifier.isPrivate(gacc.setter.getModifiers()))
/*     */       {
/*     */         
/* 137 */         return null;
/*     */       }
/*     */       
/* 140 */       if (t.isPrimitive()) {
/* 141 */         opt = AccessorInjector.prepare(dc, methodTemplateName + (String)suffixMap.get(t), newClassName, new String[] { ClassTailor.toVMClassName(Bean.class), ClassTailor.toVMClassName(dc), "get_" + t.getName(), gacc.getter.getName(), "set_" + t.getName(), gacc.setter.getName() });
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     if (opt == null) {
/* 153 */       return null;
/*     */     }
/* 155 */     logger.log(Level.FINE, "Using optimized TransducedAccessor for " + prop.displayName());
/*     */ 
/*     */     
/*     */     try {
/* 159 */       return (TransducedAccessor)opt.newInstance();
/* 160 */     } catch (InstantiationException e) {
/* 161 */       logger.log(Level.INFO, "failed to load an optimized TransducedAccessor", e);
/* 162 */     } catch (IllegalAccessException e) {
/* 163 */       logger.log(Level.INFO, "failed to load an optimized TransducedAccessor", e);
/* 164 */     } catch (SecurityException e) {
/* 165 */       logger.log(Level.INFO, "failed to load an optimized TransducedAccessor", e);
/*     */     } 
/* 167 */     return null;
/*     */   }
/*     */   
/* 170 */   private static final Map<Class, String> suffixMap = (Map)new HashMap<Class<?>, String>();
/*     */   
/*     */   static {
/* 173 */     suffixMap.put(byte.class, "Byte");
/* 174 */     suffixMap.put(short.class, "Short");
/* 175 */     suffixMap.put(int.class, "Integer");
/* 176 */     suffixMap.put(long.class, "Long");
/* 177 */     suffixMap.put(boolean.class, "Boolean");
/* 178 */     suffixMap.put(float.class, "Float");
/* 179 */     suffixMap.put(double.class, "Double");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\opt\OptimizedTransducedAccessorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */