/*     */ package com.google.zxing.datamatrix.decoder;
/*     */ 
/*     */ import com.google.zxing.ChecksumException;
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.common.DecoderResult;
/*     */ import com.google.zxing.common.reedsolomon.GenericGF;
/*     */ import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
/*     */ import com.google.zxing.common.reedsolomon.ReedSolomonException;
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
/*     */ public final class Decoder
/*     */ {
/*  38 */   private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.DATA_MATRIX_FIELD_256);
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
/*     */   public DecoderResult decode(boolean[][] image) throws FormatException, ChecksumException {
/*  51 */     int dimension = image.length;
/*  52 */     BitMatrix bits = new BitMatrix(dimension);
/*  53 */     for (int i = 0; i < dimension; i++) {
/*  54 */       for (int j = 0; j < dimension; j++) {
/*  55 */         if (image[i][j]) {
/*  56 */           bits.set(j, i);
/*     */         }
/*     */       } 
/*     */     } 
/*  60 */     return decode(bits);
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
/*     */   public DecoderResult decode(BitMatrix bits) throws FormatException, ChecksumException {
/*  75 */     BitMatrixParser parser = new BitMatrixParser(bits);
/*  76 */     Version version = parser.getVersion();
/*     */ 
/*     */     
/*  79 */     byte[] codewords = parser.readCodewords();
/*     */     
/*  81 */     DataBlock[] dataBlocks = DataBlock.getDataBlocks(codewords, version);
/*     */     
/*  83 */     int dataBlocksCount = dataBlocks.length;
/*     */ 
/*     */     
/*  86 */     int totalBytes = 0;
/*  87 */     for (DataBlock db : dataBlocks) {
/*  88 */       totalBytes += db.getNumDataCodewords();
/*     */     }
/*  90 */     byte[] resultBytes = new byte[totalBytes];
/*     */ 
/*     */     
/*  93 */     for (int j = 0; j < dataBlocksCount; j++) {
/*  94 */       DataBlock dataBlock = dataBlocks[j];
/*  95 */       byte[] codewordBytes = dataBlock.getCodewords();
/*  96 */       int numDataCodewords = dataBlock.getNumDataCodewords();
/*  97 */       correctErrors(codewordBytes, numDataCodewords);
/*  98 */       for (int i = 0; i < numDataCodewords; i++)
/*     */       {
/* 100 */         resultBytes[i * dataBlocksCount + j] = codewordBytes[i];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 105 */     return DecodedBitStreamParser.decode(resultBytes);
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
/*     */   private void correctErrors(byte[] codewordBytes, int numDataCodewords) throws ChecksumException {
/* 117 */     int numCodewords = codewordBytes.length;
/*     */     
/* 119 */     int[] codewordsInts = new int[numCodewords];
/* 120 */     for (int i = 0; i < numCodewords; i++) {
/* 121 */       codewordsInts[i] = codewordBytes[i] & 0xFF;
/*     */     }
/* 123 */     int numECCodewords = codewordBytes.length - numDataCodewords;
/*     */     try {
/* 125 */       this.rsDecoder.decode(codewordsInts, numECCodewords);
/* 126 */     } catch (ReedSolomonException ignored) {
/* 127 */       throw ChecksumException.getChecksumInstance();
/*     */     } 
/*     */ 
/*     */     
/* 131 */     for (int j = 0; j < numDataCodewords; j++)
/* 132 */       codewordBytes[j] = (byte)codewordsInts[j]; 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\datamatrix\decoder\Decoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */