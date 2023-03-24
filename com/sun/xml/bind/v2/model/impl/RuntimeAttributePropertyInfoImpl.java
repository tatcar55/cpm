/*    */ package com.sun.xml.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.bind.v2.model.core.NonElement;
/*    */ import com.sun.xml.bind.v2.model.core.PropertyInfo;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimeAttributePropertyInfo;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimeNonElement;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimePropertyInfo;
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
/*    */ class RuntimeAttributePropertyInfoImpl
/*    */   extends AttributePropertyInfoImpl<Type, Class, Field, Method>
/*    */   implements RuntimeAttributePropertyInfo
/*    */ {
/*    */   RuntimeAttributePropertyInfoImpl(RuntimeClassInfoImpl classInfo, PropertySeed<Type, Class<?>, Field, Method> seed) {
/* 58 */     super(classInfo, seed);
/*    */   }
/*    */   
/*    */   public boolean elementOnlyContent() {
/* 62 */     return true;
/*    */   }
/*    */   
/*    */   public RuntimeNonElement getTarget() {
/* 66 */     return (RuntimeNonElement)super.getTarget();
/*    */   }
/*    */   
/*    */   public List<? extends RuntimeNonElement> ref() {
/* 70 */     return (List)super.ref();
/*    */   }
/*    */   
/*    */   public RuntimePropertyInfo getSource() {
/* 74 */     return (RuntimePropertyInfo)this;
/*    */   }
/*    */   
/*    */   public void link() {
/* 78 */     getTransducer();
/* 79 */     super.link();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\RuntimeAttributePropertyInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */