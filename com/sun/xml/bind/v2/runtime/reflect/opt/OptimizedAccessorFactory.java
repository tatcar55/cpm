/*     */ package com.sun.xml.bind.v2.runtime.reflect.opt;
/*     */ 
/*     */ import com.sun.xml.bind.Util;
/*     */ import com.sun.xml.bind.v2.bytecode.ClassTailor;
/*     */ import com.sun.xml.bind.v2.runtime.RuntimeUtil;
/*     */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
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
/*     */ public abstract class OptimizedAccessorFactory
/*     */ {
/*  62 */   private static final Logger logger = Util.getClassLogger();
/*     */   
/*     */   private static final String fieldTemplateName;
/*     */   
/*     */   private static final String methodTemplateName;
/*     */   
/*     */   static {
/*  69 */     String s = FieldAccessor_Byte.class.getName();
/*  70 */     fieldTemplateName = s.substring(0, s.length() - "Byte".length()).replace('.', '/');
/*     */     
/*  72 */     s = MethodAccessor_Byte.class.getName();
/*  73 */     methodTemplateName = s.substring(0, s.length() - "Byte".length()).replace('.', '/');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final <B, V> Accessor<B, V> get(Method getter, Method setter) {
/*     */     Class<?> opt;
/*  84 */     if ((getter.getParameterTypes()).length != 0)
/*  85 */       return null; 
/*  86 */     Class<?>[] sparams = setter.getParameterTypes();
/*  87 */     if (sparams.length != 1)
/*  88 */       return null; 
/*  89 */     if (sparams[0] != getter.getReturnType())
/*  90 */       return null; 
/*  91 */     if (setter.getReturnType() != void.class)
/*  92 */       return null; 
/*  93 */     if (getter.getDeclaringClass() != setter.getDeclaringClass())
/*  94 */       return null; 
/*  95 */     if (Modifier.isPrivate(getter.getModifiers()) || Modifier.isPrivate(setter.getModifiers()))
/*     */     {
/*  97 */       return null;
/*     */     }
/*  99 */     Class<?> t = sparams[0];
/* 100 */     String typeName = t.getName().replace('.', '_');
/* 101 */     if (t.isArray()) {
/* 102 */       typeName = "AOf_";
/* 103 */       String compName = t.getComponentType().getName().replace('.', '_');
/* 104 */       while (compName.startsWith("[L")) {
/* 105 */         compName = compName.substring(2);
/* 106 */         typeName = typeName + "AOf_";
/*     */       } 
/* 108 */       typeName = typeName + compName;
/*     */     } 
/*     */     
/* 111 */     String newClassName = ClassTailor.toVMClassName(getter.getDeclaringClass()) + "$JaxbAccessorM_" + getter.getName() + '_' + setter.getName() + '_' + typeName;
/*     */ 
/*     */     
/* 114 */     if (t.isPrimitive()) {
/* 115 */       opt = AccessorInjector.prepare(getter.getDeclaringClass(), methodTemplateName + ((Class)RuntimeUtil.primitiveToBox.get(t)).getSimpleName(), newClassName, new String[] { ClassTailor.toVMClassName(Bean.class), ClassTailor.toVMClassName(getter.getDeclaringClass()), "get_" + t.getName(), getter.getName(), "set_" + t.getName(), setter.getName() });
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 125 */       opt = AccessorInjector.prepare(getter.getDeclaringClass(), methodTemplateName + "Ref", newClassName, new String[] { ClassTailor.toVMClassName(Bean.class), ClassTailor.toVMClassName(getter.getDeclaringClass()), ClassTailor.toVMClassName(Ref.class), ClassTailor.toVMClassName(t), "()" + ClassTailor.toVMTypeName(Ref.class), "()" + ClassTailor.toVMTypeName(t), '(' + ClassTailor.toVMTypeName(Ref.class) + ")V", '(' + ClassTailor.toVMTypeName(t) + ")V", "get_ref", getter.getName(), "set_ref", setter.getName() });
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     if (opt == null) {
/* 142 */       return null;
/*     */     }
/* 144 */     Accessor<B, V> acc = instanciate(opt);
/* 145 */     if (acc != null)
/* 146 */       logger.log(Level.FINE, "Using optimized Accessor for " + getter + " and " + setter); 
/* 147 */     return acc;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final <B, V> Accessor<B, V> get(Field field) {
/*     */     Class<?> opt;
/* 158 */     int mods = field.getModifiers();
/* 159 */     if (Modifier.isPrivate(mods) || Modifier.isFinal(mods))
/*     */     {
/* 161 */       return null;
/*     */     }
/* 163 */     String newClassName = ClassTailor.toVMClassName(field.getDeclaringClass()) + "$JaxbAccessorF_" + field.getName();
/*     */ 
/*     */ 
/*     */     
/* 167 */     if (field.getType().isPrimitive()) {
/* 168 */       opt = AccessorInjector.prepare(field.getDeclaringClass(), fieldTemplateName + ((Class)RuntimeUtil.primitiveToBox.get(field.getType())).getSimpleName(), newClassName, new String[] { ClassTailor.toVMClassName(Bean.class), ClassTailor.toVMClassName(field.getDeclaringClass()), "f_" + field.getType().getName(), field.getName() });
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */       
/* 176 */       opt = AccessorInjector.prepare(field.getDeclaringClass(), fieldTemplateName + "Ref", newClassName, new String[] { ClassTailor.toVMClassName(Bean.class), ClassTailor.toVMClassName(field.getDeclaringClass()), ClassTailor.toVMClassName(Ref.class), ClassTailor.toVMClassName(field.getType()), ClassTailor.toVMTypeName(Ref.class), ClassTailor.toVMTypeName(field.getType()), "f_ref", field.getName() });
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     if (opt == null) {
/* 189 */       return null;
/*     */     }
/* 191 */     Accessor<B, V> acc = instanciate(opt);
/* 192 */     if (acc != null)
/* 193 */       logger.log(Level.FINE, "Using optimized Accessor for " + field); 
/* 194 */     return acc;
/*     */   }
/*     */   
/*     */   private static <B, V> Accessor<B, V> instanciate(Class<Accessor<B, V>> opt) {
/*     */     try {
/* 199 */       return opt.newInstance();
/* 200 */     } catch (InstantiationException e) {
/* 201 */       logger.log(Level.INFO, "failed to load an optimized Accessor", e);
/* 202 */     } catch (IllegalAccessException e) {
/* 203 */       logger.log(Level.INFO, "failed to load an optimized Accessor", e);
/* 204 */     } catch (SecurityException e) {
/* 205 */       logger.log(Level.INFO, "failed to load an optimized Accessor", e);
/*     */     } 
/* 207 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\opt\OptimizedAccessorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */