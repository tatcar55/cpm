/*    */ package com.sun.xml.bind.v2.model.impl;
/*    */ 
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimeElementPropertyInfo;
/*    */ import com.sun.xml.bind.v2.model.runtime.RuntimeTypeInfo;
/*    */ import com.sun.xml.bind.v2.runtime.reflect.Accessor;
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import javax.xml.namespace.QName;
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
/*    */ class RuntimeElementPropertyInfoImpl
/*    */   extends ElementPropertyInfoImpl<Type, Class, Field, Method>
/*    */   implements RuntimeElementPropertyInfo
/*    */ {
/*    */   private final Accessor acc;
/*    */   
/*    */   RuntimeElementPropertyInfoImpl(RuntimeClassInfoImpl classInfo, PropertySeed<Type, Class<?>, Field, Method> seed) {
/* 63 */     super(classInfo, seed);
/* 64 */     Accessor rawAcc = ((RuntimeClassInfoImpl.RuntimePropertySeed)seed).getAccessor();
/* 65 */     if (getAdapter() != null && !isCollection())
/*    */     {
/*    */       
/* 68 */       rawAcc = rawAcc.adapt(getAdapter()); } 
/* 69 */     this.acc = rawAcc;
/*    */   }
/*    */   
/*    */   public Accessor getAccessor() {
/* 73 */     return this.acc;
/*    */   }
/*    */   
/*    */   public boolean elementOnlyContent() {
/* 77 */     return true;
/*    */   }
/*    */   
/*    */   public List<? extends RuntimeTypeInfo> ref() {
/* 81 */     return (List)super.ref();
/*    */   }
/*    */ 
/*    */   
/*    */   protected RuntimeTypeRefImpl createTypeRef(QName name, Type type, boolean isNillable, String defaultValue) {
/* 86 */     return new RuntimeTypeRefImpl(this, name, type, isNillable, defaultValue);
/*    */   }
/*    */   
/*    */   public List<RuntimeTypeRefImpl> getTypes() {
/* 90 */     return (List)super.getTypes();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\RuntimeElementPropertyInfoImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */