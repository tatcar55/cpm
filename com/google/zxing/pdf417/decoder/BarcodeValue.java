/*    */ package com.google.zxing.pdf417.decoder;
/*    */ 
/*    */ import com.google.zxing.pdf417.PDF417Common;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ final class BarcodeValue
/*    */ {
/* 31 */   private final Map<Integer, Integer> values = new HashMap<>();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void setValue(int value) {
/* 37 */     Integer confidence = this.values.get(Integer.valueOf(value));
/* 38 */     if (confidence == null) {
/* 39 */       confidence = Integer.valueOf(0);
/*    */     }
/* 41 */     Integer integer1 = confidence, integer2 = confidence = Integer.valueOf(confidence.intValue() + 1);
/* 42 */     this.values.put(Integer.valueOf(value), confidence);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   int[] getValue() {
/* 50 */     int maxConfidence = -1;
/* 51 */     Collection<Integer> result = new ArrayList<>();
/* 52 */     for (Map.Entry<Integer, Integer> entry : this.values.entrySet()) {
/* 53 */       if (((Integer)entry.getValue()).intValue() > maxConfidence) {
/* 54 */         maxConfidence = ((Integer)entry.getValue()).intValue();
/* 55 */         result.clear();
/* 56 */         result.add(entry.getKey()); continue;
/* 57 */       }  if (((Integer)entry.getValue()).intValue() == maxConfidence) {
/* 58 */         result.add(entry.getKey());
/*    */       }
/*    */     } 
/* 61 */     return PDF417Common.toIntArray(result);
/*    */   }
/*    */   
/*    */   public Integer getConfidence(int value) {
/* 65 */     return this.values.get(Integer.valueOf(value));
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\pdf417\decoder\BarcodeValue.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */