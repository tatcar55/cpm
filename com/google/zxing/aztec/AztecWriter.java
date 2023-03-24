/*    */ package com.google.zxing.aztec;
/*    */ 
/*    */ import com.google.zxing.BarcodeFormat;
/*    */ import com.google.zxing.EncodeHintType;
/*    */ import com.google.zxing.Writer;
/*    */ import com.google.zxing.aztec.encoder.AztecCode;
/*    */ import com.google.zxing.aztec.encoder.Encoder;
/*    */ import com.google.zxing.common.BitMatrix;
/*    */ import java.nio.charset.Charset;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class AztecWriter
/*    */   implements Writer
/*    */ {
/* 31 */   private static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");
/*    */ 
/*    */   
/*    */   public BitMatrix encode(String contents, BarcodeFormat format, int width, int height) {
/* 35 */     return encode(contents, format, width, height, null);
/*    */   }
/*    */ 
/*    */   
/*    */   public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
/* 40 */     String charset = (hints == null) ? null : (String)hints.get(EncodeHintType.CHARACTER_SET);
/* 41 */     Number eccPercent = (hints == null) ? null : (Number)hints.get(EncodeHintType.ERROR_CORRECTION);
/* 42 */     Number layers = (hints == null) ? null : (Number)hints.get(EncodeHintType.AZTEC_LAYERS);
/* 43 */     return encode(contents, format, width, height, (charset == null) ? DEFAULT_CHARSET : 
/*    */ 
/*    */ 
/*    */         
/* 47 */         Charset.forName(charset), (eccPercent == null) ? 33 : eccPercent
/* 48 */         .intValue(), (layers == null) ? 0 : layers
/* 49 */         .intValue());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Charset charset, int eccPercent, int layers) {
/* 55 */     if (format != BarcodeFormat.AZTEC) {
/* 56 */       throw new IllegalArgumentException("Can only encode AZTEC, but got " + format);
/*    */     }
/* 58 */     AztecCode aztec = Encoder.encode(contents.getBytes(charset), eccPercent, layers);
/* 59 */     return renderResult(aztec, width, height);
/*    */   }
/*    */   
/*    */   private static BitMatrix renderResult(AztecCode code, int width, int height) {
/* 63 */     BitMatrix input = code.getMatrix();
/* 64 */     if (input == null) {
/* 65 */       throw new IllegalStateException();
/*    */     }
/* 67 */     int inputWidth = input.getWidth();
/* 68 */     int inputHeight = input.getHeight();
/* 69 */     int outputWidth = Math.max(width, inputWidth);
/* 70 */     int outputHeight = Math.max(height, inputHeight);
/*    */     
/* 72 */     int multiple = Math.min(outputWidth / inputWidth, outputHeight / inputHeight);
/* 73 */     int leftPadding = (outputWidth - inputWidth * multiple) / 2;
/* 74 */     int topPadding = (outputHeight - inputHeight * multiple) / 2;
/*    */     
/* 76 */     BitMatrix output = new BitMatrix(outputWidth, outputHeight);
/*    */     int outputY;
/* 78 */     for (int inputY = 0; inputY < inputHeight; inputY++, outputY += multiple) {
/*    */       int outputX;
/* 80 */       for (int inputX = 0; inputX < inputWidth; inputX++, outputX += multiple) {
/* 81 */         if (input.get(inputX, inputY)) {
/* 82 */           output.setRegion(outputX, outputY, multiple, multiple);
/*    */         }
/*    */       } 
/*    */     } 
/* 86 */     return output;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\aztec\AztecWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */