/*    */ package com.sun.xml.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimeElement;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimeReferencePropertyInfo;
/*    */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
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
/*    */ class RuntimeReferencePropertyInfoImpl
/*    */   extends ReferencePropertyInfoImpl<Type, Class, Field, Method>
/*    */   implements RuntimeReferencePropertyInfo
/*    */ {
/*    */   private final Accessor acc;
/*    */   
/*    */   public RuntimeReferencePropertyInfoImpl(RuntimeClassInfoImpl classInfo, PropertySeed<Type, Class<?>, Field, Method> seed) {
/* 61 */     super(classInfo, seed);
/* 62 */     Accessor rawAcc = ((RuntimeClassInfoImpl.RuntimePropertySeed)seed).getAccessor();
/* 63 */     if (getAdapter() != null && !isCollection())
/*    */     {
/*    */       
/* 66 */       rawAcc = rawAcc.adapt(getAdapter()); } 
/* 67 */     this.acc = rawAcc;
/*    */   }
/*    */   
/*    */   public Set<? extends RuntimeElement> getElements() {
/* 71 */     return (Set)super.getElements();
/*    */   }
/*    */   
/*    */   public Set<? extends RuntimeElement> ref() {
/* 75 */     return (Set)super.ref();
/*    */   }
/*    */   
/*    */   public Accessor getAccessor() {
/* 79 */     return this.acc;
/*    */   }
/*    */   
/*    */   public boolean elementOnlyContent() {
/* 83 */     return !isMixed();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\RuntimeReferencePropertyInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */