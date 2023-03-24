/*     */ package com.sun.xml.bind.v2.model.impl;
/*     */ 
/*     */ import com.sun.xml.bind.v2.model.annotation.AnnotationReader;
/*     */ import com.sun.xml.bind.v2.model.core.ElementInfo;
/*     */ import com.sun.xml.bind.v2.model.core.NonElement;
/*     */ import com.sun.xml.bind.v2.model.nav.Navigator;
/*     */ import com.sun.xml.bind.v2.model.nav.ReflectionNavigator;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeElementInfo;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElement;
/*     */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfoSet;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class RuntimeTypeInfoSetImpl
/*     */   extends TypeInfoSetImpl<Type, Class, Field, Method>
/*     */   implements RuntimeTypeInfoSet
/*     */ {
/*     */   public RuntimeTypeInfoSetImpl(AnnotationReader<Type, Class<?>, Field, Method> reader) {
/*  64 */     super((Navigator<Type, Class, Field, Method>)Navigator.REFLECTION, reader, (Map)RuntimeBuiltinLeafInfoImpl.LEAVES);
/*     */   }
/*     */ 
/*     */   
/*     */   protected RuntimeNonElement createAnyType() {
/*  69 */     return RuntimeAnyTypeImpl.theInstance;
/*     */   }
/*     */   
/*     */   public ReflectionNavigator getNavigator() {
/*  73 */     return (ReflectionNavigator)super.getNavigator();
/*     */   }
/*     */   
/*     */   public RuntimeNonElement getTypeInfo(Type type) {
/*  77 */     return (RuntimeNonElement)super.getTypeInfo(type);
/*     */   }
/*     */   
/*     */   public RuntimeNonElement getAnyTypeInfo() {
/*  81 */     return (RuntimeNonElement)super.getAnyTypeInfo();
/*     */   }
/*     */   
/*     */   public RuntimeNonElement getClassInfo(Class clazz) {
/*  85 */     return (RuntimeNonElement)super.getClassInfo(clazz);
/*     */   }
/*     */   
/*     */   public Map<Class, RuntimeClassInfoImpl> beans() {
/*  89 */     return (Map)super.beans();
/*     */   }
/*     */   
/*     */   public Map<Type, RuntimeBuiltinLeafInfoImpl<?>> builtins() {
/*  93 */     return (Map)super.builtins();
/*     */   }
/*     */   
/*     */   public Map<Class, RuntimeEnumLeafInfoImpl<?, ?>> enums() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokespecial enums : ()Ljava/util/Map;
/*     */     //   4: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #97	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	5	0	this	Lcom/sun/xml/bind/v2/model/impl/RuntimeTypeInfoSetImpl;
/*     */   }
/*     */   
/*     */   public Map<Class, RuntimeArrayInfoImpl> arrays() {
/* 101 */     return (Map)super.arrays();
/*     */   }
/*     */   
/*     */   public RuntimeElementInfoImpl getElementInfo(Class scope, QName name) {
/* 105 */     return (RuntimeElementInfoImpl)super.getElementInfo(scope, name);
/*     */   }
/*     */   
/*     */   public Map<QName, RuntimeElementInfoImpl> getElementMappings(Class scope) {
/* 109 */     return (Map)super.getElementMappings(scope);
/*     */   }
/*     */   
/*     */   public Iterable<RuntimeElementInfoImpl> getAllElements() {
/* 113 */     return (Iterable)super.getAllElements();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\RuntimeTypeInfoSetImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */