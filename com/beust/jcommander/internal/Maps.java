/*    */ package com.beust.jcommander.internal;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
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
/*    */ public class Maps
/*    */ {
/*    */   public static <K, V> Map<K, V> newHashMap() {
/* 28 */     return new HashMap<K, V>();
/*    */   }
/*    */   
/*    */   public static <K, V> Map<K, V> newLinkedHashMap() {
/* 32 */     return new LinkedHashMap<K, V>();
/*    */   }
/*    */   
/*    */   public static <T> Map<T, T> newHashMap(T... parameters) {
/* 36 */     Map<T, T> result = newHashMap();
/* 37 */     for (int i = 0; i < parameters.length; i += 2) {
/* 38 */       result.put(parameters[i], parameters[i + 1]);
/*    */     }
/* 40 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\beust\jcommander\internal\Maps.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */