/*     */ package com.sun.xml.ws.transport.httpspi.servlet;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ExchangeRequestHeaders
/*     */   extends Headers
/*     */ {
/*     */   private final HttpServletRequest request;
/*     */   private boolean useMap = false;
/*     */   
/*     */   ExchangeRequestHeaders(HttpServletRequest request) {
/*  58 */     this.request = request;
/*     */   }
/*     */   
/*     */   private void convertToMap() {
/*  62 */     if (!this.useMap) {
/*  63 */       Enumeration<String> e = this.request.getHeaderNames();
/*  64 */       while (e.hasMoreElements()) {
/*  65 */         String name = e.nextElement();
/*  66 */         Enumeration<String> ev = this.request.getHeaders(name);
/*  67 */         while (ev.hasMoreElements()) {
/*  68 */           String value = ev.nextElement();
/*  69 */           super.add(name, value);
/*     */         } 
/*     */       } 
/*  72 */       this.useMap = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/*  78 */     convertToMap();
/*  79 */     return super.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/*  84 */     convertToMap();
/*  85 */     return super.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/*  90 */     if (!(key instanceof String)) {
/*  91 */       return false;
/*     */     }
/*  93 */     return this.useMap ? super.containsKey(key) : ((this.request.getHeader((String)key) != null));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/*  98 */     convertToMap();
/*  99 */     return super.containsValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> get(Object key) {
/* 104 */     convertToMap();
/* 105 */     return super.get(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFirst(String key) {
/* 110 */     return this.useMap ? super.getFirst(key) : this.request.getHeader(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> put(String key, List<String> value) {
/* 115 */     convertToMap();
/* 116 */     return super.put(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(String key, String value) {
/* 121 */     convertToMap();
/* 122 */     super.add(key, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(String key, String value) {
/* 127 */     convertToMap();
/* 128 */     super.set(key, value);
/*     */   }
/*     */   
/*     */   public List<String> remove(Object key) {
/* 132 */     convertToMap();
/* 133 */     return super.remove(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends String, ? extends List<String>> t) {
/* 138 */     convertToMap();
/* 139 */     super.putAll(t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 144 */     convertToMap();
/* 145 */     super.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> keySet() {
/* 150 */     convertToMap();
/* 151 */     return super.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<List<String>> values() {
/* 156 */     convertToMap();
/* 157 */     return super.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<String, List<String>>> entrySet() {
/* 162 */     convertToMap();
/* 163 */     return super.entrySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 168 */     convertToMap();
/* 169 */     return super.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 174 */     return super.equals(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 179 */     return super.hashCode();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\httpspi\servlet\ExchangeRequestHeaders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */