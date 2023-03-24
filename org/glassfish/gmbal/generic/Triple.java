/*    */ package org.glassfish.gmbal.generic;
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
/*    */ 
/*    */ 
/*    */ public class Triple<S, T, U>
/*    */ {
/*    */   private final Pair<S, Pair<T, U>> delegate;
/*    */   
/*    */   public Triple(S first, T second, U third) {
/* 49 */     this.delegate = new Pair<S, Pair<T, U>>(first, new Pair<T, U>(second, third));
/*    */   }
/*    */ 
/*    */   
/*    */   public S first() {
/* 54 */     return this.delegate.first();
/*    */   }
/*    */   
/*    */   public T second() {
/* 58 */     return (T)((Pair)this.delegate.second()).first();
/*    */   }
/*    */   
/*    */   public U third() {
/* 62 */     return (U)((Pair)this.delegate.second()).second();
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 67 */     return this.delegate.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 72 */     return this.delegate.equals(obj);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\Triple.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */