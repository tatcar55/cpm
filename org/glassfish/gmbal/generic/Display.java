/*     */ package org.glassfish.gmbal.generic;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Display<K, V>
/*     */ {
/*  63 */   private List<Map<K, V>> display = new ArrayList<Map<K, V>>();
/*     */   
/*     */   public void enterScope() {
/*  66 */     this.display.add(new HashMap<K, V>());
/*     */   }
/*     */   
/*     */   public void exitScope() {
/*  70 */     if (this.display.isEmpty()) {
/*  71 */       throw new IllegalStateException("Display is empty");
/*     */     }
/*     */     
/*  74 */     this.display.remove(this.display.size() - 1);
/*     */   }
/*     */   
/*     */   public void bind(K key, V value) {
/*  78 */     if (this.display.isEmpty()) {
/*  79 */       throw new IllegalStateException("Display is empty");
/*     */     }
/*     */     
/*  82 */     ((Map<K, V>)this.display.get(this.display.size() - 1)).put(key, value);
/*     */   }
/*     */   
/*     */   public void bind(Map<K, V> bindings) {
/*  86 */     if (this.display.isEmpty()) {
/*  87 */       throw new IllegalStateException("Display is empty");
/*     */     }
/*     */     
/*  90 */     ((Map<K, V>)this.display.get(this.display.size() - 1)).putAll(bindings);
/*     */   }
/*     */   
/*     */   public V lookup(K key) {
/*  94 */     V result = null;
/*  95 */     for (int ctr = this.display.size() - 1; ctr >= 0; ctr--) {
/*  96 */       result = (V)((Map)this.display.get(ctr)).get(key);
/*  97 */       if (result != null) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 102 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\Display.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */