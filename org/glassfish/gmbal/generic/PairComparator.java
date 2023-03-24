/*    */ package org.glassfish.gmbal.generic;
/*    */ 
/*    */ import java.util.Comparator;
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
/*    */ public class PairComparator<S, T>
/*    */   implements Comparator<Pair<S, T>>
/*    */ {
/*    */   private Comparator<? super S> sc;
/*    */   private Comparator<? super T> tc;
/*    */   
/*    */   public PairComparator(Comparator<? super S> sc, Comparator<? super T> tc) {
/* 51 */     if (sc == null || tc == null) {
/* 52 */       throw new IllegalArgumentException();
/*    */     }
/* 54 */     this.sc = sc;
/* 55 */     this.tc = tc;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compare(Pair<S, T> o1, Pair<S, T> o2) {
/* 60 */     int res = this.sc.compare(o1.first(), o2.first());
/* 61 */     if (res == 0) {
/* 62 */       return this.tc.compare(o1.second(), o2.second());
/*    */     }
/* 64 */     return res;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 69 */     if (!(obj instanceof PairComparator)) {
/* 70 */       return false;
/*    */     }
/* 72 */     if (obj == this) {
/* 73 */       return true;
/*    */     }
/* 75 */     PairComparator<S, T> other = (PairComparator<S, T>)obj;
/* 76 */     return (other.sc.equals(this.sc) && other.tc.equals(this.tc));
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 80 */     return this.sc.hashCode() ^ this.tc.hashCode();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 84 */     return "PairComparator[" + this.sc + "," + this.tc + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\PairComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */