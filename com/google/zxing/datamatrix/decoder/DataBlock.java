/*     */ package com.google.zxing.datamatrix.decoder;
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
/*     */   static DataBlock[] getDataBlocks(byte[] rawCodewords, Version version) {
/*  49 */     Version.ECBlocks ecBlocks = version.getECBlocks();
/*     */ 
/*     */     
/*  52 */     int totalBlocks = 0;
/*  53 */     Version.ECB[] ecBlockArray = ecBlocks.getECBlocks();
/*  54 */     for (Version.ECB ecBlock : ecBlockArray) {
/*  55 */       totalBlocks += ecBlock.getCount();
/*     */     }
/*     */ 
/*     */     
/*  59 */     DataBlock[] result = new DataBlock[totalBlocks];
/*  60 */     int numResultBlocks = 0;
/*  61 */     for (Version.ECB ecBlock : ecBlockArray) {
/*  62 */       for (int m = 0; m < ecBlock.getCount(); m++) {
/*  63 */         int numDataCodewords = ecBlock.getDataCodewords();
/*  64 */         int numBlockCodewords = ecBlocks.getECCodewords() + numDataCodewords;
/*  65 */         result[numResultBlocks++] = new DataBlock(numDataCodewords, new byte[numBlockCodewords]);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     int longerBlocksTotalCodewords = (result[0]).codewords.length;
/*     */ 
/*     */     
/*  75 */     int longerBlocksNumDataCodewords = longerBlocksTotalCodewords - ecBlocks.getECCodewords();
/*  76 */     int shorterBlocksNumDataCodewords = longerBlocksNumDataCodewords - 1;
/*     */ 
/*     */     
/*  79 */     int rawCodewordsOffset = 0;
/*  80 */     for (int i = 0; i < shorterBlocksNumDataCodewords; i++) {
/*  81 */       for (int m = 0; m < numResultBlocks; m++) {
/*  82 */         (result[m]).codewords[i] = rawCodewords[rawCodewordsOffset++];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  87 */     boolean specialVersion = (version.getVersionNumber() == 24);
/*  88 */     int numLongerBlocks = specialVersion ? 8 : numResultBlocks;
/*  89 */     for (int j = 0; j < numLongerBlocks; j++) {
/*  90 */       (result[j]).codewords[longerBlocksNumDataCodewords - 1] = rawCodewords[rawCodewordsOffset++];
/*     */     }
/*     */ 
/*     */     
/*  94 */     int max = (result[0]).codewords.length;
/*  95 */     for (int k = longerBlocksNumDataCodewords; k < max; k++) {
/*  96 */       for (int m = 0; m < numResultBlocks; m++) {
/*  97 */         int jOffset = specialVersion ? ((m + 8) % numResultBlocks) : m;
/*  98 */         int iOffset = (specialVersion && jOffset > 7) ? (k - 1) : k;
/*  99 */         (result[jOffset]).codewords[iOffset] = rawCodewords[rawCodewordsOffset++];
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     if (rawCodewordsOffset != rawCodewords.length) {
/* 104 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 107 */     return result;
/*     */   }
/*     */   
/*     */   int getNumDataCodewords() {
/* 111 */     return this.numDataCodewords;
/*     */   }
/*     */   
/*     */   byte[] getCodewords() {
/* 115 */     return this.codewords;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\datamatrix\decoder\DataBlock.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */