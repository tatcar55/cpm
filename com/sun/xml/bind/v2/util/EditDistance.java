/*     */ package com.sun.xml.bind.v2.util;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EditDistance
/*     */ {
/*     */   private int[] cost;
/*     */   private int[] back;
/*     */   private final String a;
/*     */   private final String b;
/*     */   
/*     */   public static int editDistance(String a, String b) {
/*  65 */     return (new EditDistance(a, b)).calc();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String findNearest(String key, String[] group) {
/*  75 */     return findNearest(key, Arrays.asList(group));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String findNearest(String key, Collection<String> group) {
/*  85 */     int c = Integer.MAX_VALUE;
/*  86 */     String r = null;
/*     */     
/*  88 */     for (String s : group) {
/*  89 */       int ed = editDistance(key, s);
/*  90 */       if (c > ed) {
/*  91 */         c = ed;
/*  92 */         r = s;
/*     */       } 
/*     */     } 
/*  95 */     return r;
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
/*     */   private EditDistance(String a, String b) {
/* 107 */     this.a = a;
/* 108 */     this.b = b;
/* 109 */     this.cost = new int[a.length() + 1];
/* 110 */     this.back = new int[a.length() + 1];
/*     */     
/* 112 */     for (int i = 0; i <= a.length(); i++) {
/* 113 */       this.cost[i] = i;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void flip() {
/* 120 */     int[] t = this.cost;
/* 121 */     this.cost = this.back;
/* 122 */     this.back = t;
/*     */   }
/*     */   
/*     */   private int min(int a, int b, int c) {
/* 126 */     return Math.min(a, Math.min(b, c));
/*     */   }
/*     */   
/*     */   private int calc() {
/* 130 */     for (int j = 0; j < this.b.length(); j++) {
/* 131 */       flip();
/* 132 */       this.cost[0] = j + 1;
/* 133 */       for (int i = 0; i < this.a.length(); i++) {
/* 134 */         int match = (this.a.charAt(i) == this.b.charAt(j)) ? 0 : 1;
/* 135 */         this.cost[i + 1] = min(this.back[i] + match, this.cost[i] + 1, this.back[i + 1] + 1);
/*     */       } 
/*     */     } 
/* 138 */     return this.cost[this.a.length()];
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v\\util\EditDistance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */