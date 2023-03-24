/*     */ package com.google.zxing.qrcode.decoder;
/*     */ 
/*     */ import com.google.zxing.ChecksumException;
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.ReaderException;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.common.DecoderResult;
/*     */ import com.google.zxing.common.reedsolomon.GenericGF;
/*     */ import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
/*     */ import com.google.zxing.common.reedsolomon.ReedSolomonException;
/*     */ import java.util.Map;
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
/*  41 */   private final ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);
/*     */ 
/*     */   
/*     */   public DecoderResult decode(boolean[][] image) throws ChecksumException, FormatException {
/*  45 */     return decode(image, (Map<DecodeHintType, ?>)null);
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
/*     */   public DecoderResult decode(boolean[][] image, Map<DecodeHintType, ?> hints) throws ChecksumException, FormatException {
/*  60 */     int dimension = image.length;
/*  61 */     BitMatrix bits = new BitMatrix(dimension);
/*  62 */     for (int i = 0; i < dimension; i++) {
/*  63 */       for (int j = 0; j < dimension; j++) {
/*  64 */         if (image[i][j]) {
/*  65 */           bits.set(j, i);
/*     */         }
/*     */       } 
/*     */     } 
/*  69 */     return decode(bits, hints);
/*     */   }
/*     */   
/*     */   public DecoderResult decode(BitMatrix bits) throws ChecksumException, FormatException {
/*  73 */     return decode(bits, (Map<DecodeHintType, ?>)null);
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
/*     */   public DecoderResult decode(BitMatrix bits, Map<DecodeHintType, ?> hints) throws FormatException, ChecksumException {
/*  89 */     BitMatrixParser parser = new BitMatrixParser(bits);
/*  90 */     FormatException fe = null;
/*  91 */     ChecksumException ce = null;
/*     */     try {
/*  93 */       return decode(parser, hints);
/*  94 */     } catch (FormatException e) {
/*  95 */       fe = e;
/*  96 */     } catch (ChecksumException e) {
/*  97 */       ce = e;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 103 */       parser.remask();
/*     */ 
/*     */       
/* 106 */       parser.setMirror(true);
/*     */ 
/*     */       
/* 109 */       parser.readVersion();
/*     */ 
/*     */       
/* 112 */       parser.readFormatInformation();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       parser.mirror();
/*     */       
/* 123 */       DecoderResult result = decode(parser, hints);
/*     */ 
/*     */       
/* 126 */       result.setOther(new QRCodeDecoderMetaData(true));
/*     */       
/* 128 */       return result;
/*     */     }
/* 130 */     catch (FormatException|ChecksumException e) {
/*     */       
/* 132 */       if (fe != null) {
/* 133 */         throw fe;
/*     */       }
/* 135 */       if (ce != null) {
/* 136 */         throw ce;
/*     */       }
/* 138 */       throw e;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private DecoderResult decode(BitMatrixParser parser, Map<DecodeHintType, ?> hints) throws FormatException, ChecksumException {
/* 145 */     Version version = parser.readVersion();
/* 146 */     ErrorCorrectionLevel ecLevel = parser.readFormatInformation().getErrorCorrectionLevel();
/*     */ 
/*     */     
/* 149 */     byte[] codewords = parser.readCodewords();
/*     */     
/* 151 */     DataBlock[] dataBlocks = DataBlock.getDataBlocks(codewords, version, ecLevel);
/*     */ 
/*     */     
/* 154 */     int totalBytes = 0;
/* 155 */     for (DataBlock dataBlock : dataBlocks) {
/* 156 */       totalBytes += dataBlock.getNumDataCodewords();
/*     */     }
/* 158 */     byte[] resultBytes = new byte[totalBytes];
/* 159 */     int resultOffset = 0;
/*     */ 
/*     */     
/* 162 */     for (DataBlock dataBlock : dataBlocks) {
/* 163 */       byte[] codewordBytes = dataBlock.getCodewords();
/* 164 */       int numDataCodewords = dataBlock.getNumDataCodewords();
/* 165 */       correctErrors(codewordBytes, numDataCodewords);
/* 166 */       for (int i = 0; i < numDataCodewords; i++) {
/* 167 */         resultBytes[resultOffset++] = codewordBytes[i];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 172 */     return DecodedBitStreamParser.decode(resultBytes, version, ecLevel, hints);
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
/* 184 */     int numCodewords = codewordBytes.length;
/*     */     
/* 186 */     int[] codewordsInts = new int[numCodewords];
/* 187 */     for (int i = 0; i < numCodewords; i++) {
/* 188 */       codewordsInts[i] = codewordBytes[i] & 0xFF;
/*     */     }
/* 190 */     int numECCodewords = codewordBytes.length - numDataCodewords;
/*     */     try {
/* 192 */       this.rsDecoder.decode(codewordsInts, numECCodewords);
/* 193 */     } catch (ReedSolomonException ignored) {
/* 194 */       throw ChecksumException.getChecksumInstance();
/*     */     } 
/*     */ 
/*     */     
/* 198 */     for (int j = 0; j < numDataCodewords; j++)
/* 199 */       codewordBytes[j] = (byte)codewordsInts[j]; 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\qrcode\decoder\Decoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */