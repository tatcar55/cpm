/*     */ package com.google.zxing.oned;
/*     */ 
/*     */ import com.google.zxing.BarcodeFormat;
/*     */ import com.google.zxing.EncodeHintType;
/*     */ import com.google.zxing.Writer;
/*     */ import com.google.zxing.WriterException;
/*     */ import com.google.zxing.common.BitMatrix;
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
/*     */ public abstract class OneDimensionalCodeWriter
/*     */   implements Writer
/*     */ {
/*     */   public final BitMatrix encode(String contents, BarcodeFormat format, int width, int height) throws WriterException {
/*  37 */     return encode(contents, format, width, height, null);
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
/*     */   public BitMatrix encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws WriterException {
/*  53 */     if (contents.isEmpty()) {
/*  54 */       throw new IllegalArgumentException("Found empty contents");
/*     */     }
/*     */     
/*  57 */     if (width < 0 || height < 0) {
/*  58 */       throw new IllegalArgumentException("Negative size is not allowed. Input: " + width + 'x' + height);
/*     */     }
/*     */ 
/*     */     
/*  62 */     int sidesMargin = getDefaultMargin();
/*  63 */     if (hints != null) {
/*  64 */       Integer sidesMarginInt = (Integer)hints.get(EncodeHintType.MARGIN);
/*  65 */       if (sidesMarginInt != null) {
/*  66 */         sidesMargin = sidesMarginInt.intValue();
/*     */       }
/*     */     } 
/*     */     
/*  70 */     boolean[] code = encode(contents);
/*  71 */     return renderResult(code, width, height, sidesMargin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static BitMatrix renderResult(boolean[] code, int width, int height, int sidesMargin) {
/*  78 */     int inputWidth = code.length;
/*     */     
/*  80 */     int fullWidth = inputWidth + sidesMargin;
/*  81 */     int outputWidth = Math.max(width, fullWidth);
/*  82 */     int outputHeight = Math.max(1, height);
/*     */     
/*  84 */     int multiple = outputWidth / fullWidth;
/*  85 */     int leftPadding = (outputWidth - inputWidth * multiple) / 2;
/*     */     
/*  87 */     BitMatrix output = new BitMatrix(outputWidth, outputHeight); int outputX;
/*  88 */     for (int inputX = 0; inputX < inputWidth; inputX++, outputX += multiple) {
/*  89 */       if (code[inputX]) {
/*  90 */         output.setRegion(outputX, 0, multiple, outputHeight);
/*     */       }
/*     */     } 
/*  93 */     return output;
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
/*     */   protected static int appendPattern(boolean[] target, int pos, int[] pattern, boolean startColor) {
/* 105 */     boolean color = startColor;
/* 106 */     int numAdded = 0;
/* 107 */     for (int len : pattern) {
/* 108 */       for (int j = 0; j < len; j++) {
/* 109 */         target[pos++] = color;
/*     */       }
/* 111 */       numAdded += len;
/* 112 */       color = !color;
/*     */     } 
/* 114 */     return numAdded;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDefaultMargin() {
/* 120 */     return 10;
/*     */   }
/*     */   
/*     */   public abstract boolean[] encode(String paramString);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\OneDimensionalCodeWriter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */