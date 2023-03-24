/*     */ package com.sun.xml.ws.transport.httpspi.servlet;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletResponse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ExchangeResponseHeaders
/*     */   extends Headers
/*     */ {
/*     */   private final HttpServletResponse response;
/*     */   
/*     */   ExchangeResponseHeaders(HttpServletResponse response) {
/*  56 */     this.response = response;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  61 */     return super.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  66 */     return super.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/*  71 */     return super.containsKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/*  76 */     return super.containsValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> get(Object key) {
/*  81 */     return super.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFirst(String key) {
/*  86 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> put(String key, List<String> value) {
/*  91 */     for (String val : value) {
/*  92 */       this.response.addHeader(key, val);
/*     */     }
/*  94 */     return super.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(String key, String value) {
/*  99 */     this.response.addHeader(key, value);
/* 100 */     super.add(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(String key, String value) {
/* 105 */     this.response.addHeader(key, value);
/* 106 */     super.set(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> remove(Object key) {
/* 112 */     return super.remove(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends String, ? extends List<String>> t) {
/* 118 */     super.putAll(t);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 124 */     super.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> keySet() {
/* 129 */     return super.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<List<String>> values() {
/* 134 */     return super.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<String, List<String>>> entrySet() {
/* 139 */     return super.entrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 144 */     return super.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 149 */     return super.equals(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 154 */     return super.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\ExchangeResponseHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */