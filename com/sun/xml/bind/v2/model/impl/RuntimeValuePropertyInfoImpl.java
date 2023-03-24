/*    */ package com.sun.xml.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.bind.v2.model.core.NonElement;
/*    */ import com.sun.xml.bind.v2.model.core.PropertyInfo;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElement;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimeValuePropertyInfo;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
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
/*    */ final class RuntimeValuePropertyInfoImpl
/*    */   extends ValuePropertyInfoImpl<Type, Class, Field, Method>
/*    */   implements RuntimeValuePropertyInfo
/*    */ {
/*    */   RuntimeValuePropertyInfoImpl(RuntimeClassInfoImpl classInfo, PropertySeed<Type, Class<?>, Field, Method> seed) {
/* 58 */     super(classInfo, seed);
/*    */   }
/*    */   
/*    */   public boolean elementOnlyContent() {
/* 62 */     return false;
/*    */   }
/*    */   
/*    */   public RuntimePropertyInfo getSource() {
/* 66 */     return (RuntimePropertyInfo)super.getSource();
/*    */   }
/*    */   
/*    */   public RuntimeNonElement getTarget() {
/* 70 */     return (RuntimeNonElement)super.getTarget();
/*    */   }
/*    */   
/*    */   public List<? extends RuntimeNonElement> ref() {
/* 74 */     return (List)super.ref();
/*    */   }
/*    */   
/*    */   public void link() {
/* 78 */     getTransducer();
/* 79 */     super.link();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\RuntimeValuePropertyInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */