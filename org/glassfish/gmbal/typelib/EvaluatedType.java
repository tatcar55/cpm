/*     */ package org.glassfish.gmbal.typelib;
/*     */ 
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Date;
/*     */ import javax.management.ObjectName;
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
/*     */ public interface EvaluatedType
/*     */ {
/*  56 */   public static final EvaluatedClassDeclaration EVOID = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(void.class);
/*     */   
/*  58 */   public static final EvaluatedClassDeclaration EINT = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(int.class);
/*     */   
/*  60 */   public static final EvaluatedClassDeclaration EINTW = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(Integer.class);
/*     */   
/*  62 */   public static final EvaluatedClassDeclaration EBYTE = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(byte.class);
/*     */   
/*  64 */   public static final EvaluatedClassDeclaration EBYTEW = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(Byte.class);
/*     */   
/*  66 */   public static final EvaluatedClassDeclaration ECHAR = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(char.class);
/*     */   
/*  68 */   public static final EvaluatedClassDeclaration ECHARW = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(Character.class);
/*     */   
/*  70 */   public static final EvaluatedClassDeclaration ESHORT = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(short.class);
/*     */   
/*  72 */   public static final EvaluatedClassDeclaration ESHORTW = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(Short.class);
/*     */   
/*  74 */   public static final EvaluatedClassDeclaration EBOOLEAN = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(boolean.class);
/*     */   
/*  76 */   public static final EvaluatedClassDeclaration EBOOLEANW = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(Boolean.class);
/*     */   
/*  78 */   public static final EvaluatedClassDeclaration EFLOAT = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(float.class);
/*     */   
/*  80 */   public static final EvaluatedClassDeclaration EFLOATW = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(Float.class);
/*     */   
/*  82 */   public static final EvaluatedClassDeclaration EDOUBLE = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(double.class);
/*     */   
/*  84 */   public static final EvaluatedClassDeclaration EDOUBLEW = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(Double.class);
/*     */   
/*  86 */   public static final EvaluatedClassDeclaration ELONG = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(long.class);
/*     */   
/*  88 */   public static final EvaluatedClassDeclaration ELONGW = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(Long.class);
/*     */   
/*  90 */   public static final EvaluatedClassDeclaration EBIG_DECIMAL = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(BigDecimal.class);
/*     */   
/*  92 */   public static final EvaluatedClassDeclaration EBIG_INTEGER = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(BigInteger.class);
/*     */   
/*  94 */   public static final EvaluatedClassDeclaration EDATE = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(Date.class);
/*     */   
/*  96 */   public static final EvaluatedClassDeclaration EOBJECT_NAME = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(ObjectName.class);
/*     */   
/*  98 */   public static final EvaluatedClassDeclaration ESTRING = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(String.class);
/*     */   
/* 100 */   public static final EvaluatedClassDeclaration EOBJECT = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(Object.class);
/*     */   
/* 102 */   public static final EvaluatedClassDeclaration ENUMBER = (EvaluatedClassDeclaration)TypeEvaluator.getEvaluatedType(Number.class);
/*     */   
/*     */   <R> R accept(Visitor<R> paramVisitor);
/*     */   
/*     */   boolean isImmutable();
/*     */   
/*     */   String name();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\EvaluatedType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */