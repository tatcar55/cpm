/*    */ package com.sun.xml.bind.v2.model.impl;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Type;
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
/*    */ final class RuntimeEnumConstantImpl
/*    */   extends EnumConstantImpl<Type, Class, Field, Method>
/*    */ {
/*    */   public RuntimeEnumConstantImpl(RuntimeEnumLeafInfoImpl<Type, Class, Field, Method> owner, String name, String lexical, EnumConstantImpl<Type, Class<?>, Field, Method> next) {
/* 54 */     super(owner, name, lexical, next);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\impl\RuntimeEnumConstantImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */