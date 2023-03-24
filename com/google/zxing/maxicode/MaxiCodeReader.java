/*     */ package com.google.zxing.maxicode;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.BinaryBitmap;
/*     */ import com.google.zxing.ChecksumException;
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.Reader;
/*     */ import com.google.zxing.Result;
/*     */ import com.google.zxing.ResultMetadataType;
/*     */ import com.google.zxing.ResultPoint;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.common.DecoderResult;
/*     */ import com.google.zxing.maxicode.decoder.Decoder;
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
/*     */ public final class MaxiCodeReader
/*     */   implements Reader
/*     */ {
/*  40 */   private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
/*     */   
/*     */   private static final int MATRIX_WIDTH = 30;
/*     */   private static final int MATRIX_HEIGHT = 33;
/*  44 */   private final Decoder decoder = new Decoder();
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
/*     */   public Result decode(BinaryBitmap image) throws NotFoundException, ChecksumException, FormatException {
/*  62 */     return decode(image, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException, ChecksumException, FormatException {
/*     */     DecoderResult decoderResult;
/*  69 */     if (hints != null && hints.containsKey(DecodeHintType.PURE_BARCODE)) {
/*  70 */       BitMatrix bits = extractPureBits(image.getBlackMatrix());
/*  71 */       decoderResult = this.decoder.decode(bits, hints);
/*     */     } else {
/*  73 */       throw NotFoundException.getNotFoundInstance();
/*     */     } 
/*     */     
/*  76 */     ResultPoint[] points = NO_POINTS;
/*  77 */     Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), points, BarcodeFormat.MAXICODE);
/*     */     
/*  79 */     String ecLevel = decoderResult.getECLevel();
/*  80 */     if (ecLevel != null) {
/*  81 */       result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ecLevel);
/*     */     }
/*  83 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BitMatrix extractPureBits(BitMatrix image) throws NotFoundException {
/* 102 */     int[] enclosingRectangle = image.getEnclosingRectangle();
/* 103 */     if (enclosingRectangle == null) {
/* 104 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */     
/* 107 */     int left = enclosingRectangle[0];
/* 108 */     int top = enclosingRectangle[1];
/* 109 */     int width = enclosingRectangle[2];
/* 110 */     int height = enclosingRectangle[3];
/*     */ 
/*     */     
/* 113 */     BitMatrix bits = new BitMatrix(30, 33);
/* 114 */     for (int y = 0; y < 33; y++) {
/* 115 */       int iy = top + (y * height + height / 2) / 33;
/* 116 */       for (int x = 0; x < 30; x++) {
/* 117 */         int ix = left + (x * width + width / 2 + (y & 0x1) * width / 2) / 30;
/* 118 */         if (image.get(ix, iy)) {
/* 119 */           bits.set(x, y);
/*     */         }
/*     */       } 
/*     */     } 
/* 123 */     return bits;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\maxicode\MaxiCodeReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */