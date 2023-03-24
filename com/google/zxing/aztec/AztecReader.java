/*     */ package com.google.zxing.aztec;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.BinaryBitmap;
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.Reader;
/*     */ import com.google.zxing.ReaderException;
/*     */ import com.google.zxing.Result;
/*     */ import com.google.zxing.ResultMetadataType;
/*     */ import com.google.zxing.ResultPoint;
/*     */ import com.google.zxing.ResultPointCallback;
/*     */ import com.google.zxing.aztec.decoder.Decoder;
/*     */ import com.google.zxing.aztec.detector.Detector;
/*     */ import com.google.zxing.common.DecoderResult;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AztecReader
/*     */   implements Reader
/*     */ {
/*     */   public Result decode(BinaryBitmap image) throws NotFoundException, FormatException {
/*  52 */     return decode(image, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result decode(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException, FormatException {
/*  59 */     NotFoundException notFoundException = null;
/*  60 */     FormatException formatException = null;
/*  61 */     Detector detector = new Detector(image.getBlackMatrix());
/*  62 */     ResultPoint[] points = null;
/*  63 */     DecoderResult decoderResult = null;
/*     */     try {
/*  65 */       AztecDetectorResult detectorResult = detector.detect(false);
/*  66 */       points = detectorResult.getPoints();
/*  67 */       decoderResult = (new Decoder()).decode(detectorResult);
/*  68 */     } catch (NotFoundException e) {
/*  69 */       notFoundException = e;
/*  70 */     } catch (FormatException e) {
/*  71 */       formatException = e;
/*     */     } 
/*  73 */     if (decoderResult == null) {
/*     */       try {
/*  75 */         AztecDetectorResult detectorResult = detector.detect(true);
/*  76 */         points = detectorResult.getPoints();
/*  77 */         decoderResult = (new Decoder()).decode(detectorResult);
/*  78 */       } catch (NotFoundException|FormatException e) {
/*  79 */         if (notFoundException != null) {
/*  80 */           throw notFoundException;
/*     */         }
/*  82 */         if (formatException != null) {
/*  83 */           throw formatException;
/*     */         }
/*  85 */         throw e;
/*     */       } 
/*     */     }
/*     */     
/*  89 */     if (hints != null) {
/*  90 */       ResultPointCallback rpcb = (ResultPointCallback)hints.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
/*  91 */       if (rpcb != null) {
/*  92 */         for (ResultPoint point : points) {
/*  93 */           rpcb.foundPossibleResultPoint(point);
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/*  98 */     Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), points, BarcodeFormat.AZTEC);
/*     */     
/* 100 */     List<byte[]> byteSegments = decoderResult.getByteSegments();
/* 101 */     if (byteSegments != null) {
/* 102 */       result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
/*     */     }
/* 104 */     String ecLevel = decoderResult.getECLevel();
/* 105 */     if (ecLevel != null) {
/* 106 */       result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ecLevel);
/*     */     }
/*     */     
/* 109 */     return result;
/*     */   }
/*     */   
/*     */   public void reset() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\aztec\AztecReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */