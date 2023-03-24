/*     */ package com.google.zxing.datamatrix;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.Dimension;
/*     */ import com.google.zxing.EncodeHintType;
/*     */ import com.google.zxing.Writer;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.datamatrix.encoder.DefaultPlacement;
/*     */ import com.google.zxing.datamatrix.encoder.ErrorCorrection;
/*     */ import com.google.zxing.datamatrix.encoder.HighLevelEncoder;
/*     */ import com.google.zxing.datamatrix.encoder.SymbolInfo;
/*     */ import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
/*     */ import com.google.zxing.qrcode.encoder.ByteMatrix;
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
/*     */ public final class DataMatrixWriter
/*     */   implements Writer
/*     */ {
/*     */   public BitMatrix encode(String contents, BarcodeFormat format, int width, int height) {
/*  43 */     return encode(contents, format, width, height, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
/*  49 */     if (contents.isEmpty()) {
/*  50 */       throw new IllegalArgumentException("Found empty contents");
/*     */     }
/*     */     
/*  53 */     if (format != BarcodeFormat.DATA_MATRIX) {
/*  54 */       throw new IllegalArgumentException("Can only encode DATA_MATRIX, but got " + format);
/*     */     }
/*     */     
/*  57 */     if (width < 0 || height < 0) {
/*  58 */       throw new IllegalArgumentException("Requested dimensions are too small: " + width + 'x' + height);
/*     */     }
/*     */ 
/*     */     
/*  62 */     SymbolShapeHint shape = SymbolShapeHint.FORCE_NONE;
/*  63 */     Dimension minSize = null;
/*  64 */     Dimension maxSize = null;
/*  65 */     if (hints != null) {
/*  66 */       SymbolShapeHint requestedShape = (SymbolShapeHint)hints.get(EncodeHintType.DATA_MATRIX_SHAPE);
/*  67 */       if (requestedShape != null) {
/*  68 */         shape = requestedShape;
/*     */       }
/*     */       
/*  71 */       Dimension requestedMinSize = (Dimension)hints.get(EncodeHintType.MIN_SIZE);
/*  72 */       if (requestedMinSize != null) {
/*  73 */         minSize = requestedMinSize;
/*     */       }
/*     */       
/*  76 */       Dimension requestedMaxSize = (Dimension)hints.get(EncodeHintType.MAX_SIZE);
/*  77 */       if (requestedMaxSize != null) {
/*  78 */         maxSize = requestedMaxSize;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  84 */     String encoded = HighLevelEncoder.encodeHighLevel(contents, shape, minSize, maxSize);
/*     */     
/*  86 */     SymbolInfo symbolInfo = SymbolInfo.lookup(encoded.length(), shape, minSize, maxSize, true);
/*     */ 
/*     */     
/*  89 */     String codewords = ErrorCorrection.encodeECC200(encoded, symbolInfo);
/*     */ 
/*     */ 
/*     */     
/*  93 */     DefaultPlacement placement = new DefaultPlacement(codewords, symbolInfo.getSymbolDataWidth(), symbolInfo.getSymbolDataHeight());
/*  94 */     placement.place();
/*     */ 
/*     */     
/*  97 */     return encodeLowLevel(placement, symbolInfo);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BitMatrix encodeLowLevel(DefaultPlacement placement, SymbolInfo symbolInfo) {
/* 108 */     int symbolWidth = symbolInfo.getSymbolDataWidth();
/* 109 */     int symbolHeight = symbolInfo.getSymbolDataHeight();
/*     */     
/* 111 */     ByteMatrix matrix = new ByteMatrix(symbolInfo.getSymbolWidth(), symbolInfo.getSymbolHeight());
/*     */     
/* 113 */     int matrixY = 0;
/*     */     
/* 115 */     for (int y = 0; y < symbolHeight; y++) {
/*     */ 
/*     */       
/* 118 */       if (y % symbolInfo.matrixHeight == 0) {
/* 119 */         int i = 0;
/* 120 */         for (int j = 0; j < symbolInfo.getSymbolWidth(); j++) {
/* 121 */           matrix.set(i, matrixY, (j % 2 == 0));
/* 122 */           i++;
/*     */         } 
/* 124 */         matrixY++;
/*     */       } 
/* 126 */       int matrixX = 0; int x;
/* 127 */       for (x = 0; x < symbolWidth; x++) {
/*     */         
/* 129 */         if (x % symbolInfo.matrixWidth == 0) {
/* 130 */           matrix.set(matrixX, matrixY, true);
/* 131 */           matrixX++;
/*     */         } 
/* 133 */         matrix.set(matrixX, matrixY, placement.getBit(x, y));
/* 134 */         matrixX++;
/*     */         
/* 136 */         if (x % symbolInfo.matrixWidth == symbolInfo.matrixWidth - 1) {
/* 137 */           matrix.set(matrixX, matrixY, (y % 2 == 0));
/* 138 */           matrixX++;
/*     */         } 
/*     */       } 
/* 141 */       matrixY++;
/*     */       
/* 143 */       if (y % symbolInfo.matrixHeight == symbolInfo.matrixHeight - 1) {
/* 144 */         matrixX = 0;
/* 145 */         for (x = 0; x < symbolInfo.getSymbolWidth(); x++) {
/* 146 */           matrix.set(matrixX, matrixY, true);
/* 147 */           matrixX++;
/*     */         } 
/* 149 */         matrixY++;
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     return convertByteMatrixToBitMatrix(matrix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BitMatrix convertByteMatrixToBitMatrix(ByteMatrix matrix) {
/* 163 */     int matrixWidgth = matrix.getWidth();
/* 164 */     int matrixHeight = matrix.getHeight();
/*     */     
/* 166 */     BitMatrix output = new BitMatrix(matrixWidgth, matrixHeight);
/* 167 */     output.clear();
/* 168 */     for (int i = 0; i < matrixWidgth; i++) {
/* 169 */       for (int j = 0; j < matrixHeight; j++) {
/*     */         
/* 171 */         if (matrix.get(i, j) == 1) {
/* 172 */           output.set(i, j);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 177 */     return output;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\datamatrix\DataMatrixWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */