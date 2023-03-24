/*     */ package com.google.zxing.qrcode.decoder;
/*     */ 
/*     */ import com.google.zxing.common.BitMatrix;
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
/*     */ abstract class DataMask
/*     */ {
/*  37 */   private static final DataMask[] DATA_MASKS = new DataMask[] { new DataMask000(), new DataMask001(), new DataMask010(), new DataMask011(), new DataMask100(), new DataMask101(), new DataMask110(), new DataMask111() };
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
/*     */   private DataMask() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void unmaskBitMatrix(BitMatrix bits, int dimension) {
/*  59 */     for (int i = 0; i < dimension; i++) {
/*  60 */       for (int j = 0; j < dimension; j++) {
/*  61 */         if (isMasked(i, j)) {
/*  62 */           bits.flip(j, i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static DataMask forReference(int reference) {
/*  76 */     if (reference < 0 || reference > 7) {
/*  77 */       throw new IllegalArgumentException();
/*     */     }
/*  79 */     return DATA_MASKS[reference];
/*     */   }
/*     */   
/*     */   abstract boolean isMasked(int paramInt1, int paramInt2);
/*     */   
/*     */   private static final class DataMask000 extends DataMask {
/*     */     private DataMask000() {}
/*     */     
/*     */     boolean isMasked(int i, int j) {
/*  88 */       return ((i + j & 0x1) == 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DataMask001
/*     */     extends DataMask
/*     */   {
/*     */     private DataMask001() {}
/*     */     
/*     */     boolean isMasked(int i, int j) {
/*  98 */       return ((i & 0x1) == 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DataMask010
/*     */     extends DataMask
/*     */   {
/*     */     private DataMask010() {}
/*     */     
/*     */     boolean isMasked(int i, int j) {
/* 108 */       return (j % 3 == 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DataMask011
/*     */     extends DataMask
/*     */   {
/*     */     private DataMask011() {}
/*     */     
/*     */     boolean isMasked(int i, int j) {
/* 118 */       return ((i + j) % 3 == 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DataMask100
/*     */     extends DataMask
/*     */   {
/*     */     private DataMask100() {}
/*     */     
/*     */     boolean isMasked(int i, int j) {
/* 128 */       return ((i / 2 + j / 3 & 0x1) == 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DataMask101
/*     */     extends DataMask
/*     */   {
/*     */     private DataMask101() {}
/*     */     
/*     */     boolean isMasked(int i, int j) {
/* 138 */       int temp = i * j;
/* 139 */       return ((temp & 0x1) + temp % 3 == 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DataMask110
/*     */     extends DataMask
/*     */   {
/*     */     private DataMask110() {}
/*     */     
/*     */     boolean isMasked(int i, int j) {
/* 149 */       int temp = i * j;
/* 150 */       return (((temp & 0x1) + temp % 3 & 0x1) == 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class DataMask111
/*     */     extends DataMask
/*     */   {
/*     */     private DataMask111() {}
/*     */     
/*     */     boolean isMasked(int i, int j) {
/* 160 */       return (((i + j & 0x1) + i * j % 3 & 0x1) == 0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\qrcode\decoder\DataMask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */