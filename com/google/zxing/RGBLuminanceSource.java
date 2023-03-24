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
/*     */ public final class RGBLuminanceSource
/*     */   extends LuminanceSource
/*     */ {
/*     */   private final byte[] luminances;
/*     */   private final int dataWidth;
/*     */   private final int dataHeight;
/*     */   private final int left;
/*     */   private final int top;
/*     */   
/*     */   public RGBLuminanceSource(int width, int height, int[] pixels) {
/*  35 */     super(width, height);
/*     */     
/*  37 */     this.dataWidth = width;
/*  38 */     this.dataHeight = height;
/*  39 */     this.left = 0;
/*  40 */     this.top = 0;
/*     */ 
/*     */ 
/*     */     
/*  44 */     this.luminances = new byte[width * height];
/*  45 */     for (int y = 0; y < height; y++) {
/*  46 */       int offset = y * width;
/*  47 */       for (int x = 0; x < width; x++) {
/*  48 */         int pixel = pixels[offset + x];
/*  49 */         int r = pixel >> 16 & 0xFF;
/*  50 */         int g = pixel >> 8 & 0xFF;
/*  51 */         int b = pixel & 0xFF;
/*  52 */         if (r == g && g == b) {
/*     */           
/*  54 */           this.luminances[offset + x] = (byte)r;
/*     */         } else {
/*     */           
/*  57 */           this.luminances[offset + x] = (byte)((r + 2 * g + b) / 4);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RGBLuminanceSource(byte[] pixels, int dataWidth, int dataHeight, int left, int top, int width, int height) {
/*  70 */     super(width, height);
/*  71 */     if (left + width > dataWidth || top + height > dataHeight) {
/*  72 */       throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
/*     */     }
/*  74 */     this.luminances = pixels;
/*  75 */     this.dataWidth = dataWidth;
/*  76 */     this.dataHeight = dataHeight;
/*  77 */     this.left = left;
/*  78 */     this.top = top;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getRow(int y, byte[] row) {
/*  83 */     if (y < 0 || y >= getHeight()) {
/*  84 */       throw new IllegalArgumentException("Requested row is outside the image: " + y);
/*     */     }
/*  86 */     int width = getWidth();
/*  87 */     if (row == null || row.length < width) {
/*  88 */       row = new byte[width];
/*     */     }
/*  90 */     int offset = (y + this.top) * this.dataWidth + this.left;
/*  91 */     System.arraycopy(this.luminances, offset, row, 0, width);
/*  92 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getMatrix() {
/*  97 */     int width = getWidth();
/*  98 */     int height = getHeight();
/*     */ 
/*     */ 
/*     */     
/* 102 */     if (width == this.dataWidth && height == this.dataHeight) {
/* 103 */       return this.luminances;
/*     */     }
/*     */     
/* 106 */     int area = width * height;
/* 107 */     byte[] matrix = new byte[area];
/* 108 */     int inputOffset = this.top * this.dataWidth + this.left;
/*     */ 
/*     */     
/* 111 */     if (width == this.dataWidth) {
/* 112 */       System.arraycopy(this.luminances, inputOffset, matrix, 0, area);
/* 113 */       return matrix;
/*     */     } 
/*     */ 
/*     */     
/* 117 */     byte[] rgb = this.luminances;
/* 118 */     for (int y = 0; y < height; y++) {
/* 119 */       int outputOffset = y * width;
/* 120 */       System.arraycopy(rgb, inputOffset, matrix, outputOffset, width);
/* 121 */       inputOffset += this.dataWidth;
/*     */     } 
/* 123 */     return matrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCropSupported() {
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public LuminanceSource crop(int left, int top, int width, int height) {
/* 133 */     return new RGBLuminanceSource(this.luminances, this.dataWidth, this.dataHeight, this.left + left, this.top + top, width, height);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\RGBLuminanceSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */