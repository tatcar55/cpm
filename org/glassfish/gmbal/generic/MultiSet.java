/*     */ package org.glassfish.gmbal.generic;
/*     */ 
/*     */ import java.util.HashMap;
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
/*     */ public class MultiSet<E>
/*     */ {
/*  51 */   private Map<E, Integer> contents = new HashMap<E, Integer>();
/*     */   
/*     */   public void add(E element) {
/*  54 */     Integer value = this.contents.get(element);
/*  55 */     if (value == null) {
/*  56 */       value = Integer.valueOf(0);
/*     */     }
/*     */     
/*  59 */     value = Integer.valueOf(value.intValue() + 1);
/*  60 */     this.contents.put(element, value);
/*     */   }
/*     */   
/*     */   public void remove(E element) {
/*  64 */     Integer value = this.contents.get(element);
/*  65 */     if (value == null) {
/*     */       return;
/*     */     }
/*     */     
/*  69 */     value = Integer.valueOf(value.intValue() - 1);
/*     */     
/*  71 */     if (value.intValue() == 0) {
/*  72 */       this.contents.remove(element);
/*     */     } else {
/*  74 */       this.contents.put(element, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean contains(E element) {
/*  79 */     Integer value = this.contents.get(element);
/*  80 */     if (value == null) {
/*  81 */       value = Integer.valueOf(0);
/*     */     }
/*     */     
/*  84 */     return (value.intValue() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/*  90 */     return this.contents.keySet().size();
/*     */   }
/*     */   
/*     */   private static void shouldBeTrue(boolean val, String msg) {
/*  94 */     if (!val)
/*  95 */       System.out.println(msg); 
/*     */   }
/*     */   
/*     */   private static void shouldBeFalse(boolean val, String msg) {
/*  99 */     if (val)
/* 100 */       System.out.println(msg); 
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 104 */     MultiSet<String> mset = new MultiSet<String>();
/* 105 */     String s1 = "first";
/* 106 */     String s2 = "second";
/*     */     
/* 108 */     mset.add(s1);
/* 109 */     shouldBeTrue(mset.contains(s1), "mset does not contain s1 (1)");
/*     */     
/* 111 */     mset.add(s2);
/* 112 */     mset.add(s1);
/* 113 */     mset.remove(s1);
/* 114 */     shouldBeTrue(mset.contains(s1), "mset does not contain s1 (2)");
/* 115 */     mset.remove(s1);
/* 116 */     shouldBeFalse(mset.contains(s1), "mset still contains s1 (3)");
/* 117 */     shouldBeTrue(mset.contains(s2), "mset does not contain s2 (4)");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\MultiSet.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */