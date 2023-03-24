/*     */ package com.sun.xml.bind.v2.runtime;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.annotation.adapters.XmlAdapter;
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
/*     */ public class RuntimeUtil
/*     */ {
/*     */   public static final Map<Class, Class> boxToPrimitive;
/*     */   public static final Map<Class, Class> primitiveToBox;
/*     */   
/*     */   public static final class ToStringAdapter
/*     */     extends XmlAdapter<String, Object>
/*     */   {
/*     */     public Object unmarshal(String s) {
/*  58 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public String marshal(Object o) {
/*  62 */       if (o == null) return null; 
/*  63 */       return o.toString();
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  81 */     Map<Class<?>, Class<?>> b = new HashMap<Class<?>, Class<?>>();
/*  82 */     b.put(byte.class, Byte.class);
/*  83 */     b.put(short.class, Short.class);
/*  84 */     b.put(int.class, Integer.class);
/*  85 */     b.put(long.class, Long.class);
/*  86 */     b.put(char.class, Character.class);
/*  87 */     b.put(boolean.class, Boolean.class);
/*  88 */     b.put(float.class, Float.class);
/*  89 */     b.put(double.class, Double.class);
/*  90 */     b.put(void.class, Void.class);
/*     */     
/*  92 */     primitiveToBox = Collections.unmodifiableMap(b);
/*     */     
/*  94 */     Map<Class<?>, Class<?>> p = new HashMap<Class<?>, Class<?>>();
/*  95 */     for (Map.Entry<Class<?>, Class<?>> e : b.entrySet()) {
/*  96 */       p.put(e.getValue(), e.getKey());
/*     */     }
/*  98 */     boxToPrimitive = Collections.unmodifiableMap(p);
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
/*     */   private static String getTypeName(Object o) {
/* 141 */     return o.getClass().getName();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\RuntimeUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */