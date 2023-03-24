/*    */ package com.google.zxing.oned.rss;
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
/*    */ public class DataCharacter
/*    */ {
/*    */   private final int value;
/*    */   private final int checksumPortion;
/*    */   
/*    */   public DataCharacter(int value, int checksumPortion) {
/* 25 */     this.value = value;
/* 26 */     this.checksumPortion = checksumPortion;
/*    */   }
/*    */   
/*    */   public final int getValue() {
/* 30 */     return this.value;
/*    */   }
/*    */   
/*    */   public final int getChecksumPortion() {
/* 34 */     return this.checksumPortion;
/*    */   }
/*    */ 
/*    */   
/*    */   public final String toString() {
/* 39 */     return this.value + "(" + this.checksumPortion + ')';
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean equals(Object o) {
/* 44 */     if (!(o instanceof DataCharacter)) {
/* 45 */       return false;
/*    */     }
/* 47 */     DataCharacter that = (DataCharacter)o;
/* 48 */     return (this.value == that.value && this.checksumPortion == that.checksumPortion);
/*    */   }
/*    */ 
/*    */   
/*    */   public final int hashCode() {
/* 53 */     return this.value ^ this.checksumPortion;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\rss\DataCharacter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */