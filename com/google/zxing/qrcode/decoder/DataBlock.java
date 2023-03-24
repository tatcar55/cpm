/*     */ package com.google.zxing.qrcode.decoder;
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
/*     */ final class DataBlock
/*     */ {
/*     */   private final int numDataCodewords;
/*     */   private final byte[] codewords;
/*     */   
/*     */   private DataBlock(int numDataCodewords, byte[] codewords) {
/*  32 */     this.numDataCodewords = numDataCodewords;
/*  33 */     this.codewords = codewords;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static DataBlock[] getDataBlocks(byte[] rawCodewords, Version version, ErrorCorrectionLevel ecLevel) {
/*  51 */     if (rawCodewords.length != version.getTotalCodewords()) {
/*  52 */       throw new IllegalArgumentException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  57 */     Version.ECBlocks ecBlocks = version.getECBlocksForLevel(ecLevel);
/*     */ 
/*     */     
/*  60 */     int totalBlocks = 0;
/*  61 */     Version.ECB[] ecBlockArray = ecBlocks.getECBlocks();
/*  62 */     for (Version.ECB ecBlock : ecBlockArray) {
/*  63 */       totalBlocks += ecBlock.getCount();
/*     */     }
/*     */ 
/*     */     
/*  67 */     DataBlock[] result = new DataBlock[totalBlocks];
/*  68 */     int numResultBlocks = 0;
/*  69 */     for (Version.ECB ecBlock : ecBlockArray) {
/*  70 */       for (int m = 0; m < ecBlock.getCount(); m++) {
/*  71 */         int numDataCodewords = ecBlock.getDataCodewords();
/*  72 */         int numBlockCodewords = ecBlocks.getECCodewordsPerBlock() + numDataCodewords;
/*  73 */         result[numResultBlocks++] = new DataBlock(numDataCodewords, new byte[numBlockCodewords]);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  79 */     int shorterBlocksTotalCodewords = (result[0]).codewords.length;
/*  80 */     int longerBlocksStartAt = result.length - 1;
/*  81 */     while (longerBlocksStartAt >= 0) {
/*  82 */       int numCodewords = (result[longerBlocksStartAt]).codewords.length;
/*  83 */       if (numCodewords == shorterBlocksTotalCodewords) {
/*     */         break;
/*     */       }
/*  86 */       longerBlocksStartAt--;
/*     */     } 
/*  88 */     longerBlocksStartAt++;
/*     */     
/*  90 */     int shorterBlocksNumDataCodewords = shorterBlocksTotalCodewords - ecBlocks.getECCodewordsPerBlock();
/*     */ 
/*     */     
/*  93 */     int rawCodewordsOffset = 0;
/*  94 */     for (int i = 0; i < shorterBlocksNumDataCodewords; i++) {
/*  95 */       for (int m = 0; m < numResultBlocks; m++) {
/*  96 */         (result[m]).codewords[i] = rawCodewords[rawCodewordsOffset++];
/*     */       }
/*     */     } 
/*     */     
/* 100 */     for (int j = longerBlocksStartAt; j < numResultBlocks; j++) {
/* 101 */       (result[j]).codewords[shorterBlocksNumDataCodewords] = rawCodewords[rawCodewordsOffset++];
/*     */     }
/*     */     
/* 104 */     int max = (result[0]).codewords.length;
/* 105 */     for (int k = shorterBlocksNumDataCodewords; k < max; k++) {
/* 106 */       for (int m = 0; m < numResultBlocks; m++) {
/* 107 */         int iOffset = (m < longerBlocksStartAt) ? k : (k + 1);
/* 108 */         (result[m]).codewords[iOffset] = rawCodewords[rawCodewordsOffset++];
/*     */       } 
/*     */     } 
/* 111 */     return result;
/*     */   }
/*     */   
/*     */   int getNumDataCodewords() {
/* 115 */     return this.numDataCodewords;
/*     */   }
/*     */   
/*     */   byte[] getCodewords() {
/* 119 */     return this.codewords;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\qrcode\decoder\DataBlock.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */