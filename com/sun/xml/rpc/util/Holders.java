/*     */ package com.sun.xml.rpc.util;
/*     */ 
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.rpc.holders.Holder;
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
/*     */ public class Holders
/*     */ {
/*  42 */   private static final Map boxedTypes = new HashMap<Object, Object>();
/*     */   
/*     */   static {
/*  45 */     boxedTypes.put(boolean.class, Boolean.class);
/*  46 */     boxedTypes.put(byte.class, Byte.class);
/*  47 */     boxedTypes.put(char.class, Character.class);
/*  48 */     boxedTypes.put(short.class, Short.class);
/*  49 */     boxedTypes.put(int.class, Integer.class);
/*  50 */     boxedTypes.put(long.class, Long.class);
/*  51 */     boxedTypes.put(float.class, Float.class);
/*  52 */     boxedTypes.put(double.class, Double.class);
/*     */   }
/*     */   
/*     */   public static Object getValue(Holder holder) {
/*  56 */     Class<?> holderClass = holder.getClass();
/*     */     try {
/*  58 */       Field valueField = holderClass.getField("value");
/*  59 */       return valueField.get(holder);
/*  60 */     } catch (Exception e) {
/*  61 */       throw fieldExtractionException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setValue(Holder holder, Object value) {
/*  66 */     Class<?> holderClass = holder.getClass();
/*     */     try {
/*  68 */       Field valueField = holderClass.getField("value");
/*  69 */       valueField.set(holder, value);
/*  70 */     } catch (Exception e) {
/*  71 */       throw fieldExtractionException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Class stripHolderClass(Class<?> aClass) {
/*  76 */     if (aClass == null || !Holder.class.isAssignableFrom(aClass)) {
/*  77 */       return aClass;
/*     */     }
/*     */     
/*     */     try {
/*  81 */       Field valueField = aClass.getField("value");
/*  82 */       Class<?> valueClass = valueField.getType();
/*     */       
/*  84 */       return boxClassIfPrimitive(valueClass);
/*  85 */     } catch (Exception e) {
/*  86 */       throw fieldExtractionException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Class boxClassIfPrimitive(Class aClass) {
/*  91 */     Class boxedType = (Class)boxedTypes.get(aClass);
/*     */     
/*  93 */     if (boxedType != null) {
/*  94 */       return boxedType;
/*     */     }
/*  96 */     return aClass;
/*     */   }
/*     */ 
/*     */   
/*     */   private static HolderException fieldExtractionException(Exception e) {
/* 101 */     return new HolderException("holder.valuefield.not.found", (Localizable)new LocalizableExceptionAdapter(e));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\Holders.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */