/*     */ package com.google.zxing.multi.qrcode;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.BinaryBitmap;
/*     */ import com.google.zxing.DecodeHintType;
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.ReaderException;
/*     */ import com.google.zxing.Result;
/*     */ import com.google.zxing.ResultMetadataType;
/*     */ import com.google.zxing.ResultPoint;
/*     */ import com.google.zxing.common.DecoderResult;
/*     */ import com.google.zxing.common.DetectorResult;
/*     */ import com.google.zxing.multi.MultipleBarcodeReader;
/*     */ import com.google.zxing.multi.qrcode.detector.MultiDetector;
/*     */ import com.google.zxing.qrcode.QRCodeReader;
/*     */ import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
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
/*     */ public final class QRCodeMultiReader
/*     */   extends QRCodeReader
/*     */   implements MultipleBarcodeReader
/*     */ {
/*  50 */   private static final Result[] EMPTY_RESULT_ARRAY = new Result[0];
/*  51 */   private static final ResultPoint[] NO_POINTS = new ResultPoint[0];
/*     */ 
/*     */   
/*     */   public Result[] decodeMultiple(BinaryBitmap image) throws NotFoundException {
/*  55 */     return decodeMultiple(image, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Result[] decodeMultiple(BinaryBitmap image, Map<DecodeHintType, ?> hints) throws NotFoundException {
/*  60 */     List<Result> results = new ArrayList<>();
/*  61 */     DetectorResult[] detectorResults = (new MultiDetector(image.getBlackMatrix())).detectMulti(hints);
/*  62 */     for (DetectorResult detectorResult : detectorResults) {
/*     */       try {
/*  64 */         DecoderResult decoderResult = getDecoder().decode(detectorResult.getBits(), hints);
/*  65 */         ResultPoint[] points = detectorResult.getPoints();
/*     */         
/*  67 */         if (decoderResult.getOther() instanceof QRCodeDecoderMetaData) {
/*  68 */           ((QRCodeDecoderMetaData)decoderResult.getOther()).applyMirroredCorrection(points);
/*     */         }
/*  70 */         Result result = new Result(decoderResult.getText(), decoderResult.getRawBytes(), points, BarcodeFormat.QR_CODE);
/*     */         
/*  72 */         List<byte[]> byteSegments = decoderResult.getByteSegments();
/*  73 */         if (byteSegments != null) {
/*  74 */           result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
/*     */         }
/*  76 */         String ecLevel = decoderResult.getECLevel();
/*  77 */         if (ecLevel != null) {
/*  78 */           result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, ecLevel);
/*     */         }
/*  80 */         if (decoderResult.hasStructuredAppend()) {
/*  81 */           result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE, 
/*  82 */               Integer.valueOf(decoderResult.getStructuredAppendSequenceNumber()));
/*  83 */           result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_PARITY, 
/*  84 */               Integer.valueOf(decoderResult.getStructuredAppendParity()));
/*     */         } 
/*  86 */         results.add(result);
/*  87 */       } catch (ReaderException readerException) {}
/*     */     } 
/*     */ 
/*     */     
/*  91 */     if (results.isEmpty()) {
/*  92 */       return EMPTY_RESULT_ARRAY;
/*     */     }
/*  94 */     results = processStructuredAppend(results);
/*  95 */     return results.<Result>toArray(new Result[results.size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Result> processStructuredAppend(List<Result> results) {
/* 100 */     boolean hasSA = false;
/*     */ 
/*     */     
/* 103 */     for (Result result : results) {
/* 104 */       if (result.getResultMetadata().containsKey(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)) {
/* 105 */         hasSA = true;
/*     */         break;
/*     */       } 
/*     */     } 
/* 109 */     if (!hasSA) {
/* 110 */       return results;
/*     */     }
/*     */ 
/*     */     
/* 114 */     List<Result> newResults = new ArrayList<>();
/* 115 */     List<Result> saResults = new ArrayList<>();
/* 116 */     for (Result result : results) {
/* 117 */       newResults.add(result);
/* 118 */       if (result.getResultMetadata().containsKey(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)) {
/* 119 */         saResults.add(result);
/*     */       }
/*     */     } 
/*     */     
/* 123 */     Collections.sort(saResults, new SAComparator());
/* 124 */     StringBuilder concatedText = new StringBuilder();
/* 125 */     int rawBytesLen = 0;
/* 126 */     int byteSegmentLength = 0;
/* 127 */     for (Result saResult : saResults) {
/* 128 */       concatedText.append(saResult.getText());
/* 129 */       rawBytesLen += (saResult.getRawBytes()).length;
/* 130 */       if (saResult.getResultMetadata().containsKey(ResultMetadataType.BYTE_SEGMENTS)) {
/*     */ 
/*     */         
/* 133 */         Iterable<byte[]> byteSegments = (Iterable<byte[]>)saResult.getResultMetadata().get(ResultMetadataType.BYTE_SEGMENTS);
/* 134 */         for (byte[] segment : byteSegments) {
/* 135 */           byteSegmentLength += segment.length;
/*     */         }
/*     */       } 
/*     */     } 
/* 139 */     byte[] newRawBytes = new byte[rawBytesLen];
/* 140 */     byte[] newByteSegment = new byte[byteSegmentLength];
/* 141 */     int newRawBytesIndex = 0;
/* 142 */     int byteSegmentIndex = 0;
/* 143 */     for (Result saResult : saResults) {
/* 144 */       System.arraycopy(saResult.getRawBytes(), 0, newRawBytes, newRawBytesIndex, (saResult.getRawBytes()).length);
/* 145 */       newRawBytesIndex += (saResult.getRawBytes()).length;
/* 146 */       if (saResult.getResultMetadata().containsKey(ResultMetadataType.BYTE_SEGMENTS)) {
/*     */ 
/*     */         
/* 149 */         Iterable<byte[]> byteSegments = (Iterable<byte[]>)saResult.getResultMetadata().get(ResultMetadataType.BYTE_SEGMENTS);
/* 150 */         for (byte[] segment : byteSegments) {
/* 151 */           System.arraycopy(segment, 0, newByteSegment, byteSegmentIndex, segment.length);
/* 152 */           byteSegmentIndex += segment.length;
/*     */         } 
/*     */       } 
/*     */     } 
/* 156 */     Result newResult = new Result(concatedText.toString(), newRawBytes, NO_POINTS, BarcodeFormat.QR_CODE);
/* 157 */     if (byteSegmentLength > 0) {
/* 158 */       Collection<byte[]> byteSegmentList = (Collection)new ArrayList<>();
/* 159 */       byteSegmentList.add(newByteSegment);
/* 160 */       newResult.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegmentList);
/*     */     } 
/* 162 */     newResults.add(newResult);
/* 163 */     return newResults;
/*     */   }
/*     */   
/*     */   private static final class SAComparator
/*     */     implements Comparator<Result>, Serializable {
/*     */     public int compare(Result a, Result b) {
/* 169 */       int aNumber = ((Integer)a.getResultMetadata().get(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)).intValue();
/* 170 */       int bNumber = ((Integer)b.getResultMetadata().get(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)).intValue();
/* 171 */       if (aNumber < bNumber) {
/* 172 */         return -1;
/*     */       }
/* 174 */       if (aNumber > bNumber) {
/* 175 */         return 1;
/*     */       }
/* 177 */       return 0;
/*     */     }
/*     */     
/*     */     private SAComparator() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\multi\qrcode\QRCodeMultiReader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */