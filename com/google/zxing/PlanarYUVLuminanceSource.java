/*     */ package com.google.zxing;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PlanarYUVLuminanceSource
/*     */   extends LuminanceSource
/*     */ {
/*     */   private static final int THUMBNAIL_SCALE_FACTOR = 2;
/*     */   private final byte[] yuvData;
/*     */   private final int dataWidth;
/*     */   private final int dataHeight;
/*     */   private final int left;
/*     */   private final int top;
/*     */   
/*     */   public PlanarYUVLuminanceSource(byte[] yuvData, int dataWidth, int dataHeight, int left, int top, int width, int height, boolean reverseHorizontal) {
/*  47 */     super(width, height);
/*     */     
/*  49 */     if (left + width > dataWidth || top + height > dataHeight) {
/*  50 */       throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
/*     */     }
/*     */     
/*  53 */     this.yuvData = yuvData;
/*  54 */     this.dataWidth = dataWidth;
/*  55 */     this.dataHeight = dataHeight;
/*  56 */     this.left = left;
/*  57 */     this.top = top;
/*  58 */     if (reverseHorizontal) {
/*  59 */       reverseHorizontal(width, height);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getRow(int y, byte[] row) {
/*  65 */     if (y < 0 || y >= getHeight()) {
/*  66 */       throw new IllegalArgumentException("Requested row is outside the image: " + y);
/*     */     }
/*  68 */     int width = getWidth();
/*  69 */     if (row == null || row.length < width) {
/*  70 */       row = new byte[width];
/*     */     }
/*  72 */     int offset = (y + this.top) * this.dataWidth + this.left;
/*  73 */     System.arraycopy(this.yuvData, offset, row, 0, width);
/*  74 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getMatrix() {
/*  79 */     int width = getWidth();
/*  80 */     int height = getHeight();
/*     */ 
/*     */ 
/*     */     
/*  84 */     if (width == this.dataWidth && height == this.dataHeight) {
/*  85 */       return this.yuvData;
/*     */     }
/*     */     
/*  88 */     int area = width * height;
/*  89 */     byte[] matrix = new byte[area];
/*  90 */     int inputOffset = this.top * this.dataWidth + this.left;
/*     */ 
/*     */     
/*  93 */     if (width == this.dataWidth) {
/*  94 */       System.arraycopy(this.yuvData, inputOffset, matrix, 0, area);
/*  95 */       return matrix;
/*     */     } 
/*     */ 
/*     */     
/*  99 */     byte[] yuv = this.yuvData;
/* 100 */     for (int y = 0; y < height; y++) {
/* 101 */       int outputOffset = y * width;
/* 102 */       System.arraycopy(yuv, inputOffset, matrix, outputOffset, width);
/* 103 */       inputOffset += this.dataWidth;
/*     */     } 
/* 105 */     return matrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCropSupported() {
/* 110 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public LuminanceSource crop(int left, int top, int width, int height) {
/* 115 */     return new PlanarYUVLuminanceSource(this.yuvData, this.dataWidth, this.dataHeight, this.left + left, this.top + top, width, height, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] renderThumbnail() {
/* 126 */     int width = getWidth() / 2;
/* 127 */     int height = getHeight() / 2;
/* 128 */     int[] pixels = new int[width * height];
/* 129 */     byte[] yuv = this.yuvData;
/* 130 */     int inputOffset = this.top * this.dataWidth + this.left;
/*     */     
/* 132 */     for (int y = 0; y < height; y++) {
/* 133 */       int outputOffset = y * width;
/* 134 */       for (int x = 0; x < width; x++) {
/* 135 */         int grey = yuv[inputOffset + x * 2] & 0xFF;
/* 136 */         pixels[outputOffset + x] = 0xFF000000 | grey * 65793;
/*     */       } 
/* 138 */       inputOffset += this.dataWidth * 2;
/*     */     } 
/* 140 */     return pixels;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getThumbnailWidth() {
/* 147 */     return getWidth() / 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getThumbnailHeight() {
/* 154 */     return getHeight() / 2;
/*     */   }
/*     */   
/*     */   private void reverseHorizontal(int width, int height) {
/* 158 */     byte[] yuvData = this.yuvData; int rowStart;
/* 159 */     for (int y = 0; y < height; y++, rowStart += this.dataWidth) {
/* 160 */       int middle = rowStart + width / 2;
/* 161 */       for (int x1 = rowStart, x2 = rowStart + width - 1; x1 < middle; x1++, x2--) {
/* 162 */         byte temp = yuvData[x1];
/* 163 */         yuvData[x1] = yuvData[x2];
/* 164 */         yuvData[x2] = temp;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\PlanarYUVLuminanceSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */