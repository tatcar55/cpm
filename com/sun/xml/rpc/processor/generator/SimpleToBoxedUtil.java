/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
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
/*     */ public final class SimpleToBoxedUtil
/*     */ {
/*     */   public static String getBoxedExpressionOfType(String s, String c) {
/*  39 */     if (isPrimitive(c)) {
/*  40 */       StringBuffer sb = new StringBuffer();
/*  41 */       sb.append("new ");
/*  42 */       sb.append(getBoxedClassName(c));
/*  43 */       sb.append('(');
/*  44 */       sb.append(s);
/*  45 */       sb.append(')');
/*  46 */       return sb.toString();
/*     */     } 
/*  48 */     return s;
/*     */   }
/*     */   
/*     */   public static String getUnboxedExpressionOfType(String s, String c) {
/*  52 */     if (isPrimitive(c)) {
/*  53 */       StringBuffer sb = new StringBuffer();
/*  54 */       sb.append('(');
/*  55 */       sb.append(s);
/*  56 */       sb.append(").");
/*  57 */       sb.append(c);
/*  58 */       sb.append("Value()");
/*  59 */       return sb.toString();
/*     */     } 
/*  61 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String convertExpressionFromTypeToType(String s, String from, String to) throws Exception {
/*  69 */     if (from.equals(to)) {
/*  70 */       return s;
/*     */     }
/*  72 */     if (!isPrimitive(to) && isPrimitive(from))
/*  73 */       return getBoxedExpressionOfType(s, from); 
/*  74 */     if (isPrimitive(to) && isPrimitive(from)) {
/*  75 */       return getUnboxedExpressionOfType(s, to);
/*     */     }
/*  77 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getBoxedClassName(String className) {
/*  82 */     if (isPrimitive(className)) {
/*  83 */       StringBuffer sb = new StringBuffer();
/*  84 */       if (className.equals(int.class.getName())) {
/*  85 */         sb.append("java.lang.Integer");
/*  86 */       } else if (className.equals(char.class.getName())) {
/*  87 */         sb.append("java.lang.Character");
/*     */       } else {
/*  89 */         sb.append(Character.toUpperCase(className.charAt(0)));
/*  90 */         sb.append(className.substring(1));
/*     */       } 
/*  92 */       return sb.toString();
/*     */     } 
/*  94 */     return className;
/*     */   }
/*     */   
/*     */   public static boolean isPrimitive(String className) {
/*  98 */     return primitiveSet.contains(className);
/*     */   }
/*     */   
/* 101 */   static Set primitiveSet = null;
/*     */   
/*     */   static {
/* 104 */     primitiveSet = new HashSet();
/* 105 */     primitiveSet.add("boolean");
/* 106 */     primitiveSet.add("byte");
/* 107 */     primitiveSet.add("double");
/* 108 */     primitiveSet.add("float");
/* 109 */     primitiveSet.add("int");
/* 110 */     primitiveSet.add("long");
/* 111 */     primitiveSet.add("short");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\SimpleToBoxedUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */