/*     */ package org.glassfish.gmbal.generic;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Pair<S, T>
/*     */ {
/*     */   protected S _first;
/*     */   protected T _second;
/*     */   
/*     */   public Pair(S first, T second) {
/*  51 */     this._first = first;
/*  52 */     this._second = second;
/*     */   }
/*     */   
/*     */   public Pair(S first) {
/*  56 */     this(first, null);
/*     */   }
/*     */   
/*     */   public Pair() {
/*  60 */     this(null);
/*     */   }
/*     */   
/*     */   public S first() {
/*  64 */     return this._first;
/*     */   }
/*     */   
/*     */   public T second() {
/*  68 */     return this._second;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/*  73 */     if (obj == this) {
/*  74 */       return true;
/*     */     }
/*     */     
/*  77 */     if (!(obj instanceof Pair)) {
/*  78 */       return false;
/*     */     }
/*     */     
/*  81 */     Pair pair = Pair.class.cast(obj);
/*     */     
/*  83 */     if ((first() == null) ? (pair.first() == null) : first().equals(pair.first()))
/*     */     {
/*  85 */       return (second() == null) ? ((pair.second() == null)) : second().equals(pair.second());
/*     */     }
/*     */     
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  94 */     int result = 0;
/*  95 */     if (this._first != null)
/*  96 */       result ^= this._first.hashCode(); 
/*  97 */     if (this._second != null) {
/*  98 */       result ^= this._second.hashCode();
/*     */     }
/* 100 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 105 */     return "Pair[" + this._first + "," + this._second + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\Pair.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */