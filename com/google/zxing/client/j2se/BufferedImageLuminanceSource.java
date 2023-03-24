/*     */ package com.google.zxing.client.j2se;
/*     */ 
/*     */ import com.google.zxing.LuminanceSource;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.ImageObserver;
/*     */ import java.awt.image.WritableRaster;
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
/*     */ public final class BufferedImageLuminanceSource
/*     */   extends LuminanceSource
/*     */ {
/*     */   private static final double MINUS_45_IN_RADIANS = -0.7853981633974483D;
/*     */   private final BufferedImage image;
/*     */   private final int left;
/*     */   private final int top;
/*     */   
/*     */   public BufferedImageLuminanceSource(BufferedImage image) {
/*  42 */     this(image, 0, 0, image.getWidth(), image.getHeight());
/*     */   }
/*     */   
/*     */   public BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width, int height) {
/*  46 */     super(width, height);
/*     */     
/*  48 */     if (image.getType() == 10) {
/*  49 */       this.image = image;
/*     */     } else {
/*  51 */       int sourceWidth = image.getWidth();
/*  52 */       int sourceHeight = image.getHeight();
/*  53 */       if (left + width > sourceWidth || top + height > sourceHeight) {
/*  54 */         throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
/*     */       }
/*     */       
/*  57 */       this.image = new BufferedImage(sourceWidth, sourceHeight, 10);
/*     */       
/*  59 */       WritableRaster raster = this.image.getRaster();
/*  60 */       int[] buffer = new int[width];
/*  61 */       for (int y = top; y < top + height; y++) {
/*  62 */         image.getRGB(left, y, width, 1, buffer, 0, sourceWidth);
/*  63 */         for (int x = 0; x < width; x++) {
/*  64 */           int pixel = buffer[x];
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  69 */           if ((pixel & 0xFF000000) == 0) {
/*  70 */             pixel = -1;
/*     */           }
/*     */ 
/*     */           
/*  74 */           buffer[x] = 306 * (pixel >> 16 & 0xFF) + 601 * (pixel >> 8 & 0xFF) + 117 * (pixel & 0xFF) + 512 >> 10;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  80 */         raster.setPixels(left, y, width, 1, buffer);
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     this.left = left;
/*  85 */     this.top = top;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getRow(int y, byte[] row) {
/*  90 */     if (y < 0 || y >= getHeight()) {
/*  91 */       throw new IllegalArgumentException("Requested row is outside the image: " + y);
/*     */     }
/*  93 */     int width = getWidth();
/*  94 */     if (row == null || row.length < width) {
/*  95 */       row = new byte[width];
/*     */     }
/*     */     
/*  98 */     this.image.getRaster().getDataElements(this.left, this.top + y, width, 1, row);
/*  99 */     return row;
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getMatrix() {
/* 104 */     int width = getWidth();
/* 105 */     int height = getHeight();
/* 106 */     int area = width * height;
/* 107 */     byte[] matrix = new byte[area];
/*     */     
/* 109 */     this.image.getRaster().getDataElements(this.left, this.top, width, height, matrix);
/* 110 */     return matrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCropSupported() {
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public LuminanceSource crop(int left, int top, int width, int height) {
/* 120 */     return new BufferedImageLuminanceSource(this.image, this.left + left, this.top + top, width, height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRotateSupported() {
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public LuminanceSource rotateCounterClockwise() {
/* 135 */     int sourceWidth = this.image.getWidth();
/* 136 */     int sourceHeight = this.image.getHeight();
/*     */ 
/*     */     
/* 139 */     AffineTransform transform = new AffineTransform(0.0D, -1.0D, 1.0D, 0.0D, 0.0D, sourceWidth);
/*     */ 
/*     */     
/* 142 */     BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, 10);
/*     */ 
/*     */     
/* 145 */     Graphics2D g = rotatedImage.createGraphics();
/* 146 */     g.drawImage(this.image, transform, (ImageObserver)null);
/* 147 */     g.dispose();
/*     */ 
/*     */     
/* 150 */     int width = getWidth();
/* 151 */     return new BufferedImageLuminanceSource(rotatedImage, this.top, sourceWidth - this.left + width, getHeight(), width);
/*     */   }
/*     */ 
/*     */   
/*     */   public LuminanceSource rotateCounterClockwise45() {
/* 156 */     int width = getWidth();
/* 157 */     int height = getHeight();
/*     */     
/* 159 */     int oldCenterX = this.left + width / 2;
/* 160 */     int oldCenterY = this.top + height / 2;
/*     */ 
/*     */     
/* 163 */     AffineTransform transform = AffineTransform.getRotateInstance(-0.7853981633974483D, oldCenterX, oldCenterY);
/*     */     
/* 165 */     int sourceDimension = Math.max(this.image.getWidth(), this.image.getHeight());
/* 166 */     BufferedImage rotatedImage = new BufferedImage(sourceDimension, sourceDimension, 10);
/*     */ 
/*     */     
/* 169 */     Graphics2D g = rotatedImage.createGraphics();
/* 170 */     g.drawImage(this.image, transform, (ImageObserver)null);
/* 171 */     g.dispose();
/*     */     
/* 173 */     int halfDimension = Math.max(width, height) / 2;
/* 174 */     int newLeft = Math.max(0, oldCenterX - halfDimension);
/* 175 */     int newTop = Math.max(0, oldCenterY - halfDimension);
/* 176 */     int newRight = Math.min(sourceDimension - 1, oldCenterX + halfDimension);
/* 177 */     int newBottom = Math.min(sourceDimension - 1, oldCenterY + halfDimension);
/*     */     
/* 179 */     return new BufferedImageLuminanceSource(rotatedImage, newLeft, newTop, newRight - newLeft, newBottom - newTop);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\client\j2se\BufferedImageLuminanceSource.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */