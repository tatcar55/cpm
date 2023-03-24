/*     */ package com.sun.xml.ws.transport.httpspi.servlet;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Headers
/*     */   implements Map<String, List<String>>
/*     */ {
/*  86 */   HashMap<String, List<String>> map = new HashMap<String, List<String>>(32);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String normalize(String key) {
/*  94 */     if (key == null) {
/*  95 */       return null;
/*     */     }
/*  97 */     int len = key.length();
/*  98 */     if (len == 0) {
/*  99 */       return key;
/*     */     }
/*     */ 
/*     */     
/* 103 */     char[] b = key.toCharArray();
/* 104 */     if (b[0] >= 'a' && b[0] <= 'z') {
/* 105 */       b[0] = (char)(b[0] - 32);
/*     */     }
/* 107 */     for (int i = 1; i < len; i++) {
/* 108 */       if (b[i] >= 'A' && b[i] <= 'Z') {
/* 109 */         b[i] = (char)(b[i] + 32);
/*     */       }
/*     */     } 
/* 112 */     String s = new String(b);
/* 113 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 118 */     return this.map.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 123 */     return this.map.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/* 128 */     if (key == null) {
/* 129 */       return false;
/*     */     }
/* 131 */     if (!(key instanceof String)) {
/* 132 */       return false;
/*     */     }
/* 134 */     return this.map.containsKey(normalize((String)key));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/* 139 */     return this.map.containsValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> get(Object key) {
/* 144 */     return this.map.get(normalize((String)key));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFirst(String key) {
/* 154 */     List<String> l = this.map.get(normalize(key));
/* 155 */     if (l == null) {
/* 156 */       return null;
/*     */     }
/* 158 */     return l.get(0);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> put(String key, List<String> value) {
/* 163 */     return this.map.put(normalize(key), value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String key, String value) {
/* 174 */     String k = normalize(key);
/* 175 */     List<String> l = this.map.get(k);
/* 176 */     if (l == null) {
/* 177 */       l = new LinkedList<String>();
/* 178 */       this.map.put(k, l);
/*     */     } 
/* 180 */     l.add(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(String key, String value) {
/* 191 */     LinkedList<String> l = new LinkedList<String>();
/* 192 */     l.add(value);
/* 193 */     put(key, l);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> remove(Object key) {
/* 199 */     return this.map.remove(normalize((String)key));
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends String, ? extends List<String>> t) {
/* 204 */     for (Map.Entry<? extends String, ? extends List<String>> entry : t.entrySet()) {
/* 205 */       put(entry.getKey(), entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 211 */     this.map.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> keySet() {
/* 216 */     return this.map.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<List<String>> values() {
/* 221 */     return this.map.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<String, List<String>>> entrySet() {
/* 226 */     return this.map.entrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 231 */     return this.map.equals(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 236 */     return this.map.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 241 */     return this.map.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\Headers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */