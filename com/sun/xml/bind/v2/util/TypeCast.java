/*    */ package com.sun.xml.bind.v2.util;
/*    */ 
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
/*    */ public class TypeCast
/*    */ {
/*    */   public static <K, V> Map<K, V> checkedCast(Map<?, ?> m, Class<K> keyType, Class<V> valueType) {
/* 53 */     if (m == null)
/* 54 */       return null; 
/* 55 */     for (Map.Entry<?, ?> e : m.entrySet()) {
/* 56 */       if (!keyType.isInstance(e.getKey()))
/* 57 */         throw new ClassCastException(e.getKey().getClass().toString()); 
/* 58 */       if (!valueType.isInstance(e.getValue()))
/* 59 */         throw new ClassCastException(e.getValue().getClass().toString()); 
/*    */     } 
/* 61 */     return (Map)m;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v\\util\TypeCast.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */