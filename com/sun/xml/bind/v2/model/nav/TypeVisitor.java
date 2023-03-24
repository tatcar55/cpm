/*    */ package com.sun.xml.bind.v2.model.nav;
/*    */ 
/*    */ import java.lang.reflect.GenericArrayType;
/*    */ import java.lang.reflect.ParameterizedType;
/*    */ import java.lang.reflect.Type;
/*    */ import java.lang.reflect.TypeVariable;
/*    */ import java.lang.reflect.WildcardType;
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
/*    */ abstract class TypeVisitor<T, P>
/*    */ {
/*    */   public final T visit(Type t, P param) {
/* 54 */     assert t != null;
/*    */     
/* 56 */     if (t instanceof Class)
/* 57 */       return onClass((Class)t, param); 
/* 58 */     if (t instanceof ParameterizedType)
/* 59 */       return onParameterizdType((ParameterizedType)t, param); 
/* 60 */     if (t instanceof GenericArrayType)
/* 61 */       return onGenericArray((GenericArrayType)t, param); 
/* 62 */     if (t instanceof WildcardType)
/* 63 */       return onWildcard((WildcardType)t, param); 
/* 64 */     if (t instanceof TypeVariable) {
/* 65 */       return onVariable((TypeVariable)t, param);
/*    */     }
/*    */     
/*    */     assert false;
/* 69 */     throw new IllegalArgumentException();
/*    */   }
/*    */   
/*    */   protected abstract T onClass(Class paramClass, P paramP);
/*    */   
/*    */   protected abstract T onParameterizdType(ParameterizedType paramParameterizedType, P paramP);
/*    */   
/*    */   protected abstract T onGenericArray(GenericArrayType paramGenericArrayType, P paramP);
/*    */   
/*    */   protected abstract T onVariable(TypeVariable paramTypeVariable, P paramP);
/*    */   
/*    */   protected abstract T onWildcard(WildcardType paramWildcardType, P paramP);
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\nav\TypeVisitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */