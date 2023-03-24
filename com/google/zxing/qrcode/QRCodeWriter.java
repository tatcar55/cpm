/*     */ package com.google.zxing.qrcode;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.EncodeHintType;
/*     */ import com.google.zxing.Writer;
/*     */ import com.google.zxing.WriterException;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/*     */ import com.google.zxing.qrcode.encoder.ByteMatrix;
/*     */ import com.google.zxing.qrcode.encoder.Encoder;
/*     */ import com.google.zxing.qrcode.encoder.QRCode;
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
/*     */ public final class QRCodeWriter
/*     */   implements Writer
/*     */ {
/*     */   private static final int QUIET_ZONE_SIZE = 4;
/*     */   
/*     */   public BitMatrix encode(String contents, BarcodeFormat format, int width, int height) throws WriterException {
/*  44 */     return encode(contents, format, width, height, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
/*  54 */     if (contents.isEmpty()) {
/*  55 */       throw new IllegalArgumentException("Found empty contents");
/*     */     }
/*     */     
/*  58 */     if (format != BarcodeFormat.QR_CODE) {
/*  59 */       throw new IllegalArgumentException("Can only encode QR_CODE, but got " + format);
/*     */     }
/*     */     
/*  62 */     if (width < 0 || height < 0) {
/*  63 */       throw new IllegalArgumentException("Requested dimensions are too small: " + width + 'x' + height);
/*     */     }
/*     */ 
/*     */     
/*  67 */     ErrorCorrectionLevel errorCorrectionLevel = ErrorCorrectionLevel.L;
/*  68 */     int quietZone = 4;
/*  69 */     if (hints != null) {
/*  70 */       ErrorCorrectionLevel requestedECLevel = (ErrorCorrectionLevel)hints.get(EncodeHintType.ERROR_CORRECTION);
/*  71 */       if (requestedECLevel != null) {
/*  72 */         errorCorrectionLevel = requestedECLevel;
/*     */       }
/*  74 */       Integer quietZoneInt = (Integer)hints.get(EncodeHintType.MARGIN);
/*  75 */       if (quietZoneInt != null) {
/*  76 */         quietZone = quietZoneInt.intValue();
/*     */       }
/*     */     } 
/*     */     
/*  80 */     QRCode code = Encoder.encode(contents, errorCorrectionLevel, hints);
/*  81 */     return renderResult(code, width, height, quietZone);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static BitMatrix renderResult(QRCode code, int width, int height, int quietZone) {
/*  87 */     ByteMatrix input = code.getMatrix();
/*  88 */     if (input == null) {
/*  89 */       throw new IllegalStateException();
/*     */     }
/*  91 */     int inputWidth = input.getWidth();
/*  92 */     int inputHeight = input.getHeight();
/*  93 */     int qrWidth = inputWidth + quietZone * 2;
/*  94 */     int qrHeight = inputHeight + quietZone * 2;
/*  95 */     int outputWidth = Math.max(width, qrWidth);
/*  96 */     int outputHeight = Math.max(height, qrHeight);
/*     */     
/*  98 */     int multiple = Math.min(outputWidth / qrWidth, outputHeight / qrHeight);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     int leftPadding = (outputWidth - inputWidth * multiple) / 2;
/* 104 */     int topPadding = (outputHeight - inputHeight * multiple) / 2;
/*     */     
/* 106 */     BitMatrix output = new BitMatrix(outputWidth, outputHeight);
/*     */     int outputY;
/* 108 */     for (int inputY = 0; inputY < inputHeight; inputY++, outputY += multiple) {
/*     */       int outputX;
/* 110 */       for (int inputX = 0; inputX < inputWidth; inputX++, outputX += multiple) {
/* 111 */         if (input.get(inputX, inputY) == 1) {
/* 112 */           output.setRegion(outputX, outputY, multiple, multiple);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     return output;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\qrcode\QRCodeWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */