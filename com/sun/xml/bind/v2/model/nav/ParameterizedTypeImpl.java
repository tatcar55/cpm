/*     */ package com.sun.xml.bind.v2.model.nav;
/*     */ 
/*     */ import java.lang.reflect.MalformedParameterizedTypeException;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.Arrays;
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
/*     */ class ParameterizedTypeImpl
/*     */   implements ParameterizedType
/*     */ {
/*     */   private Type[] actualTypeArguments;
/*     */   private Class<?> rawType;
/*     */   private Type ownerType;
/*     */   
/*     */   ParameterizedTypeImpl(Class<?> rawType, Type[] actualTypeArguments, Type ownerType) {
/*  61 */     this.actualTypeArguments = actualTypeArguments;
/*  62 */     this.rawType = rawType;
/*  63 */     if (ownerType != null) {
/*  64 */       this.ownerType = ownerType;
/*     */     } else {
/*  66 */       this.ownerType = rawType.getDeclaringClass();
/*     */     } 
/*  68 */     validateConstructorArguments();
/*     */   }
/*     */   
/*     */   private void validateConstructorArguments() {
/*  72 */     TypeVariable[] formals = (TypeVariable[])this.rawType.getTypeParameters();
/*     */     
/*  74 */     if (formals.length != this.actualTypeArguments.length) {
/*  75 */       throw new MalformedParameterizedTypeException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type[] getActualTypeArguments() {
/*  85 */     return (Type[])this.actualTypeArguments.clone();
/*     */   }
/*     */   
/*     */   public Class<?> getRawType() {
/*  89 */     return this.rawType;
/*     */   }
/*     */   
/*     */   public Type getOwnerType() {
/*  93 */     return this.ownerType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 105 */     if (o instanceof ParameterizedType) {
/*     */       
/* 107 */       ParameterizedType that = (ParameterizedType)o;
/*     */       
/* 109 */       if (this == that) {
/* 110 */         return true;
/*     */       }
/* 112 */       Type thatOwner = that.getOwnerType();
/* 113 */       Type thatRawType = that.getRawType();
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
/* 137 */       if ((this.ownerType == null) ? (thatOwner == null) : this.ownerType.equals(thatOwner)) if (((this.rawType == null) ? (thatRawType == null) : this.rawType.equals(thatRawType)) && Arrays.equals((Object[])this.actualTypeArguments, (Object[])that.getActualTypeArguments()));  return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 152 */     return Arrays.hashCode((Object[])this.actualTypeArguments) ^ ((this.ownerType == null) ? 0 : this.ownerType.hashCode()) ^ ((this.rawType == null) ? 0 : this.rawType.hashCode());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 158 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 160 */     if (this.ownerType != null)
/* 161 */     { if (this.ownerType instanceof Class) {
/* 162 */         sb.append(((Class)this.ownerType).getName());
/*     */       } else {
/* 164 */         sb.append(this.ownerType.toString());
/*     */       } 
/* 166 */       sb.append(".");
/*     */       
/* 168 */       if (this.ownerType instanceof ParameterizedTypeImpl) {
/*     */ 
/*     */         
/* 171 */         sb.append(this.rawType.getName().replace(((ParameterizedTypeImpl)this.ownerType).rawType.getName() + "$", ""));
/*     */       } else {
/*     */         
/* 174 */         sb.append(this.rawType.getName());
/*     */       }  }
/* 176 */     else { sb.append(this.rawType.getName()); }
/*     */     
/* 178 */     if (this.actualTypeArguments != null && this.actualTypeArguments.length > 0) {
/*     */       
/* 180 */       sb.append("<");
/* 181 */       boolean first = true;
/* 182 */       for (Type t : this.actualTypeArguments) {
/* 183 */         if (!first)
/* 184 */           sb.append(", "); 
/* 185 */         if (t instanceof Class) {
/* 186 */           sb.append(((Class)t).getName());
/*     */         } else {
/* 188 */           sb.append(t.toString());
/* 189 */         }  first = false;
/*     */       } 
/* 191 */       sb.append(">");
/*     */     } 
/*     */     
/* 194 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\nav\ParameterizedTypeImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */