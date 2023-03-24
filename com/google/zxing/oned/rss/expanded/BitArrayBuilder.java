/*    */ package com.google.zxing.oned.rss.expanded;
/*    */ 
/*    */ import com.google.zxing.common.BitArray;
/*    */ import java.util.List;
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
/*    */ final class BitArrayBuilder
/*    */ {
/*    */   static BitArray buildBitArray(List<ExpandedPair> pairs) {
/* 43 */     int charNumber = pairs.size() * 2 - 1;
/* 44 */     if (((ExpandedPair)pairs.get(pairs.size() - 1)).getRightChar() == null) {
/* 45 */       charNumber--;
/*    */     }
/*    */     
/* 48 */     int size = 12 * charNumber;
/*    */     
/* 50 */     BitArray binary = new BitArray(size);
/* 51 */     int accPos = 0;
/*    */     
/* 53 */     ExpandedPair firstPair = pairs.get(0);
/* 54 */     int firstValue = firstPair.getRightChar().getValue(); int i;
/* 55 */     for (i = 11; i >= 0; i--) {
/* 56 */       if ((firstValue & 1 << i) != 0) {
/* 57 */         binary.set(accPos);
/*    */       }
/* 59 */       accPos++;
/*    */     } 
/*    */     
/* 62 */     for (i = 1; i < pairs.size(); i++) {
/* 63 */       ExpandedPair currentPair = pairs.get(i);
/*    */       
/* 65 */       int leftValue = currentPair.getLeftChar().getValue();
/* 66 */       for (int j = 11; j >= 0; j--) {
/* 67 */         if ((leftValue & 1 << j) != 0) {
/* 68 */           binary.set(accPos);
/*    */         }
/* 70 */         accPos++;
/*    */       } 
/*    */       
/* 73 */       if (currentPair.getRightChar() != null) {
/* 74 */         int rightValue = currentPair.getRightChar().getValue();
/* 75 */         for (int k = 11; k >= 0; k--) {
/* 76 */           if ((rightValue & 1 << k) != 0) {
/* 77 */             binary.set(accPos);
/*    */           }
/* 79 */           accPos++;
/*    */         } 
/*    */       } 
/*    */     } 
/* 83 */     return binary;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\rss\expanded\BitArrayBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */