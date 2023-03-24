/*    */ package com.sun.xml.bind.v2.model.nav;
/*    */ 
/*    */ import java.lang.reflect.GenericArrayType;
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
/*    */ final class GenericArrayTypeImpl
/*    */   implements GenericArrayType
/*    */ {
/*    */   private Type genericComponentType;
/*    */   
/*    */   GenericArrayTypeImpl(Type ct) {
/* 53 */     assert ct != null;
/* 54 */     this.genericComponentType = ct;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Type getGenericComponentType() {
/* 66 */     return this.genericComponentType;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 70 */     Type componentType = getGenericComponentType();
/* 71 */     StringBuilder sb = new StringBuilder();
/*    */     
/* 73 */     if (componentType instanceof Class) {
/* 74 */       sb.append(((Class)componentType).getName());
/*    */     } else {
/* 76 */       sb.append(componentType.toString());
/* 77 */     }  sb.append("[]");
/* 78 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object o) {
/* 83 */     if (o instanceof GenericArrayType) {
/* 84 */       GenericArrayType that = (GenericArrayType)o;
/*    */       
/* 86 */       Type thatComponentType = that.getGenericComponentType();
/* 87 */       return this.genericComponentType.equals(thatComponentType);
/*    */     } 
/* 89 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 94 */     return this.genericComponentType.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\nav\GenericArrayTypeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */