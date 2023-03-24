/*     */ package com.google.zxing.qrcode;
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
/*     */ import com.google.zxing.common.DetectorResult;
/*     */ import com.google.zxing.qrcode.decoder.Decoder;
/*     */ import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
/*     */ import com.google.zxing.qrcode.detector.Detector;
/*     */ import java.util.List;
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
/*     */ public class QRCodeReader
/*     */   implements Reader
/*     */ {
/*  46 */   private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
/*     */   
/*  48 */   private final Decoder decoder = new Decoder();
/*     */   
/*     */   protected final Decoder getDecoder() {
/*  51 */     return this.decoder;
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
/*     */   public Result decode(BinaryBitmap image) throws NotFoundException, ChecksumException, FormatException {
/*  64 */     return decode(image, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException, ChecksumException, FormatException {
/*     */     DecoderResult decoderResult;
/*     */     ResultPoint[] points;
/*  72 */     if (hints != null && hints.containsKey(DecodeHintType.PURE_BARCODE)) {
/*  73 */       BitMatrix bits = extractPureBits(image.getBlackMatrix());
/*  74 */       decoderResult = this.decoder.decode(bits, hints);
/*  75 */       points = NO_POINTS;
/*     */     } else {
/*  77 */       DetectorResult detectorResult = (new Detector(image.getBlackMatrix())).detect(hints);
/*  78 */       decoderResult = this.decoder.decode(detectorResult.getBits(), hints);
/*  79 */       points = detectorResult.getPoints();
/*     */     } 
/*     */ 
/*     */     
/*  83 */     if (decoderResult.getOther() instanceof QRCodeDecoderMetaData) {
/*  84 */       ((QRCodeDecoderMetaData)decoderResult.getOther()).applyMirroredCorrection(points);
/*     */     }
/*     */     
/*  87 */     Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), points, BarcodeFormat.QR_CODE);
/*  88 */     List<byte[]> byteSegments = decoderResult.getByteSegments();
/*  89 */     if (byteSegments != null) {
/*  90 */       result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
/*     */     }
/*  92 */     String ecLevel = decoderResult.getECLevel();
/*  93 */     if (ecLevel != null) {
/*  94 */       result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ecLevel);
/*     */     }
/*  96 */     if (decoderResult.hasStructuredAppend()) {
/*  97 */       result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE, 
/*  98 */           Integer.valueOf(decoderResult.getStructuredAppendSequenceNumber()));
/*  99 */       result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_PARITY, 
/* 100 */           Integer.valueOf(decoderResult.getStructuredAppendParity()));
/*     */     } 
/* 102 */     return result;
/*     */   }
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
/* 120 */     int[] leftTopBlack = image.getTopLeftOnBit();
/* 121 */     int[] rightBottomBlack = image.getBottomRightOnBit();
/* 122 */     if (leftTopBlack == null || rightBottomBlack == null) {
/* 123 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */     
/* 126 */     float moduleSize = moduleSize(leftTopBlack, image);
/*     */     
/* 128 */     int top = leftTopBlack[1];
/* 129 */     int bottom = rightBottomBlack[1];
/* 130 */     int left = leftTopBlack[0];
/* 131 */     int right = rightBottomBlack[0];
/*     */ 
/*     */     
/* 134 */     if (left >= right || top >= bottom) {
/* 135 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */     
/* 138 */     if (bottom - top != right - left)
/*     */     {
/*     */       
/* 141 */       right = left + bottom - top;
/*     */     }
/*     */     
/* 144 */     int matrixWidth = Math.round((right - left + 1) / moduleSize);
/* 145 */     int matrixHeight = Math.round((bottom - top + 1) / moduleSize);
/* 146 */     if (matrixWidth <= 0 || matrixHeight <= 0) {
/* 147 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/* 149 */     if (matrixHeight != matrixWidth)
/*     */     {
/* 151 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     int nudge = (int)(moduleSize / 2.0F);
/* 158 */     top += nudge;
/* 159 */     left += nudge;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     int nudgedTooFarRight = left + (int)((matrixWidth - 1) * moduleSize) - right;
/* 165 */     if (nudgedTooFarRight > 0) {
/* 166 */       if (nudgedTooFarRight > nudge)
/*     */       {
/* 168 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/* 170 */       left -= nudgedTooFarRight;
/*     */     } 
/*     */     
/* 173 */     int nudgedTooFarDown = top + (int)((matrixHeight - 1) * moduleSize) - bottom;
/* 174 */     if (nudgedTooFarDown > 0) {
/* 175 */       if (nudgedTooFarDown > nudge)
/*     */       {
/* 177 */         throw NotFoundException.getNotFoundInstance();
/*     */       }
/* 179 */       top -= nudgedTooFarDown;
/*     */     } 
/*     */ 
/*     */     
/* 183 */     BitMatrix bits = new BitMatrix(matrixWidth, matrixHeight);
/* 184 */     for (int y = 0; y < matrixHeight; y++) {
/* 185 */       int iOffset = top + (int)(y * moduleSize);
/* 186 */       for (int x = 0; x < matrixWidth; x++) {
/* 187 */         if (image.get(left + (int)(x * moduleSize), iOffset)) {
/* 188 */           bits.set(x, y);
/*     */         }
/*     */       } 
/*     */     } 
/* 192 */     return bits;
/*     */   }
/*     */   
/*     */   private static float moduleSize(int[] leftTopBlack, BitMatrix image) throws NotFoundException {
/* 196 */     int height = image.getHeight();
/* 197 */     int width = image.getWidth();
/* 198 */     int x = leftTopBlack[0];
/* 199 */     int y = leftTopBlack[1];
/* 200 */     boolean inBlack = true;
/* 201 */     int transitions = 0;
/* 202 */     while (x < width && y < height) {
/* 203 */       if (inBlack != image.get(x, y)) {
/* 204 */         if (++transitions == 5) {
/*     */           break;
/*     */         }
/* 207 */         inBlack = !inBlack;
/*     */       } 
/* 209 */       x++;
/* 210 */       y++;
/*     */     } 
/* 212 */     if (x == width || y == height) {
/* 213 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/* 215 */     return (x - leftTopBlack[0]) / 7.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\qrcode\QRCodeReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */