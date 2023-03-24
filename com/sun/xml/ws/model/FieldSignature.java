/*     */ package com.sun.xml.ws.model;
/*     */ 
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.WildcardType;
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
/*     */ 
/*     */ 
/*     */ final class FieldSignature
/*     */ {
/*     */   static String vms(Type t) {
/*  60 */     if (t instanceof Class && ((Class)t).isPrimitive()) {
/*  61 */       Class<int> c = (Class)t;
/*  62 */       if (c == int.class)
/*  63 */         return "I"; 
/*  64 */       if (c == void.class)
/*  65 */         return "V"; 
/*  66 */       if (c == boolean.class)
/*  67 */         return "Z"; 
/*  68 */       if (c == byte.class)
/*  69 */         return "B"; 
/*  70 */       if (c == char.class)
/*  71 */         return "C"; 
/*  72 */       if (c == short.class)
/*  73 */         return "S"; 
/*  74 */       if (c == double.class)
/*  75 */         return "D"; 
/*  76 */       if (c == float.class)
/*  77 */         return "F"; 
/*  78 */       if (c == long.class)
/*  79 */         return "J"; 
/*     */     } else {
/*  81 */       if (t instanceof Class && ((Class)t).isArray())
/*  82 */         return "[" + vms(((Class)t).getComponentType()); 
/*  83 */       if (t instanceof Class || t instanceof ParameterizedType)
/*  84 */         return "L" + fqcn(t) + ";"; 
/*  85 */       if (t instanceof GenericArrayType)
/*  86 */         return "[" + vms(((GenericArrayType)t).getGenericComponentType()); 
/*  87 */       if (t instanceof java.lang.reflect.TypeVariable)
/*     */       {
/*     */ 
/*     */         
/*  91 */         return "Ljava/lang/Object;"; } 
/*  92 */       if (t instanceof WildcardType) {
/*  93 */         WildcardType w = (WildcardType)t;
/*  94 */         if ((w.getLowerBounds()).length > 0)
/*  95 */           return "-" + vms(w.getLowerBounds()[0]); 
/*  96 */         if ((w.getUpperBounds()).length > 0) {
/*  97 */           Type wt = w.getUpperBounds()[0];
/*  98 */           if (wt.equals(Object.class)) {
/*  99 */             return "*";
/*     */           }
/* 101 */           return "+" + vms(wt);
/*     */         } 
/*     */       } 
/*     */     } 
/* 105 */     throw new IllegalArgumentException("Illegal vms arg " + t);
/*     */   }
/*     */   
/*     */   private static String fqcn(Type t) {
/* 109 */     if (t instanceof Class) {
/* 110 */       Class c = (Class)t;
/* 111 */       if (c.getDeclaringClass() == null) {
/* 112 */         return c.getName().replace('.', '/');
/*     */       }
/* 114 */       return fqcn(c.getDeclaringClass()) + "$" + c.getSimpleName();
/*     */     } 
/* 116 */     if (t instanceof ParameterizedType) {
/* 117 */       ParameterizedType p = (ParameterizedType)t;
/* 118 */       if (p.getOwnerType() == null) {
/* 119 */         return fqcn(p.getRawType()) + args(p);
/*     */       }
/* 121 */       assert p.getRawType() instanceof Class;
/* 122 */       return fqcn(p.getOwnerType()) + "." + ((Class)p.getRawType()).getSimpleName() + args(p);
/*     */     } 
/*     */ 
/*     */     
/* 126 */     throw new IllegalArgumentException("Illegal fqcn arg = " + t);
/*     */   }
/*     */   
/*     */   private static String args(ParameterizedType p) {
/* 130 */     StringBuilder sig = new StringBuilder("<");
/* 131 */     for (Type t : p.getActualTypeArguments()) {
/* 132 */       sig.append(vms(t));
/*     */     }
/* 134 */     return sig.append(">").toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\FieldSignature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */