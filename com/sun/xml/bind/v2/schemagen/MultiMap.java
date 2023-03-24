/*    */ package com.sun.xml.bind.v2.schemagen;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.TreeMap;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ final class MultiMap<K extends Comparable<K>, V>
/*    */   extends TreeMap<K, V>
/*    */ {
/*    */   private final V many;
/*    */   
/*    */   public MultiMap(V many) {
/* 60 */     this.many = many;
/*    */   }
/*    */ 
/*    */   
/*    */   public V put(K key, V value) {
/* 65 */     V old = super.put(key, value);
/* 66 */     if (old != null && !old.equals(value))
/*    */     {
/* 68 */       super.put(key, this.many);
/*    */     }
/* 70 */     return old;
/*    */   }
/*    */ 
/*    */   
/*    */   public void putAll(Map<? extends K, ? extends V> map) {
/* 75 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\schemagen\MultiMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */