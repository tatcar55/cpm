/*     */ package com.sun.xml.rpc.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StructMap
/*     */   implements Map
/*     */ {
/*  46 */   protected HashMap map = new HashMap<Object, Object>();
/*  47 */   protected ArrayList keys = new ArrayList();
/*  48 */   protected ArrayList values = new ArrayList();
/*     */   
/*     */   public int size() {
/*  51 */     return this.map.size();
/*     */   }
/*     */   public boolean isEmpty() {
/*  54 */     return this.map.isEmpty();
/*     */   }
/*     */   public boolean containsKey(Object key) {
/*  57 */     return this.map.containsKey(key);
/*     */   }
/*     */   public boolean containsValue(Object value) {
/*  60 */     return this.map.containsValue(value);
/*     */   }
/*     */   public Object get(Object key) {
/*  63 */     return this.map.get(key);
/*     */   }
/*     */   public Object put(Object key, Object value) {
/*  66 */     this.keys.add(key);
/*  67 */     this.values.add(value);
/*  68 */     return this.map.put(key, value);
/*     */   }
/*     */   public Object remove(Object key) {
/*  71 */     Object value = this.map.get(key);
/*  72 */     this.keys.remove(key);
/*  73 */     this.values.remove(value);
/*  74 */     return this.map.remove(key);
/*     */   }
/*     */   public void putAll(Map t) {
/*  77 */     if (!(t instanceof StructMap))
/*  78 */       throw new IllegalArgumentException("Cannot putAll members of anything other than a StructMap"); 
/*  79 */     StructMap that = (StructMap)t;
/*  80 */     for (int i = 0; i < that.keys.size(); i++)
/*  81 */       put(that.keys.get(i), that.values.get(i)); 
/*     */   }
/*     */   
/*     */   public void clear() {
/*  85 */     this.keys.clear();
/*  86 */     this.values.clear();
/*  87 */     this.map.clear();
/*     */   }
/*     */   public Set keySet() {
/*  90 */     return this.map.keySet();
/*     */   }
/*     */   public Collection values() {
/*  93 */     return Collections.unmodifiableList(this.values);
/*     */   }
/*     */   public Set entrySet() {
/*  96 */     return this.map.entrySet();
/*     */   }
/*     */   public boolean equals(Object o) {
/*  99 */     return this.map.equals(o);
/*     */   }
/*     */   public int hashCode() {
/* 102 */     return this.map.hashCode() ^ this.keys.hashCode() ^ this.values.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection keys() {
/* 107 */     return Collections.unmodifiableList(this.keys);
/*     */   }
/*     */   public void set(int index, Object key, Object value) {
/* 110 */     this.keys.set(index, key);
/* 111 */     this.values.set(index, value);
/* 112 */     this.map.put(key, value);
/*     */   }
/*     */   public void set(int index, Object value) {
/* 115 */     Object key = this.keys.get(index);
/* 116 */     this.values.set(index, value);
/* 117 */     this.map.put(key, value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\StructMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */