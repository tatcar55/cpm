/*    */ package com.google.zxing.oned.rss.expanded.decoders;
/*    */ 
/*    */ import com.google.zxing.common.BitArray;
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
/*    */ final class AI01320xDecoder
/*    */   extends AI013x0xDecoder
/*    */ {
/*    */   AI01320xDecoder(BitArray information) {
/* 37 */     super(information);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void addWeightCode(StringBuilder buf, int weight) {
/* 42 */     if (weight < 10000) {
/* 43 */       buf.append("(3202)");
/*    */     } else {
/* 45 */       buf.append("(3203)");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected int checkWeight(int weight) {
/* 51 */     if (weight < 10000) {
/* 52 */       return weight;
/*    */     }
/* 54 */     return weight - 10000;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\rss\expanded\decoders\AI01320xDecoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */