/*     */ package com.sun.xml.ws.transport;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Comparator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   extends TreeMap<String, List<String>>
/*     */ {
/*     */   public Headers() {
/*  87 */     super(INSTANCE);
/*     */   }
/*     */   
/*  90 */   private static final InsensitiveComparator INSTANCE = new InsensitiveComparator();
/*     */   
/*     */   private static final class InsensitiveComparator
/*     */     implements Comparator<String>, Serializable {
/*     */     public int compare(String o1, String o2) {
/*  95 */       if (o1 == null && o2 == null)
/*  96 */         return 0; 
/*  97 */       if (o1 == null)
/*  98 */         return -1; 
/*  99 */       if (o2 == null)
/* 100 */         return 1; 
/* 101 */       return o1.compareToIgnoreCase(o2);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private InsensitiveComparator() {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String key, String value) {
/* 113 */     List<String> list = get(key);
/* 114 */     if (list == null) {
/* 115 */       list = new LinkedList<String>();
/* 116 */       put(key, list);
/*     */     } 
/* 118 */     list.add(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFirst(String key) {
/* 129 */     List<String> l = get(key);
/* 130 */     return (l == null) ? null : l.get(0);
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
/* 141 */     LinkedList<String> l = new LinkedList<String>();
/* 142 */     l.add(value);
/* 143 */     put(key, l);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\Headers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */