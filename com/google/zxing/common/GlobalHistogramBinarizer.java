/*     */ package com.google.zxing.common;
/*     */ 
/*     */ import com.google.zxing.Binarizer;
/*     */ import com.google.zxing.LuminanceSource;
/*     */ import com.google.zxing.NotFoundException;
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
/*     */ public class GlobalHistogramBinarizer
/*     */   extends Binarizer
/*     */ {
/*     */   private static final int LUMINANCE_BITS = 5;
/*     */   private static final int LUMINANCE_SHIFT = 3;
/*     */   private static final int LUMINANCE_BUCKETS = 32;
/*  39 */   private static final byte[] EMPTY = new byte[0];
/*     */   
/*     */   private byte[] luminances;
/*     */   private final int[] buckets;
/*     */   
/*     */   public GlobalHistogramBinarizer(LuminanceSource source) {
/*  45 */     super(source);
/*  46 */     this.luminances = EMPTY;
/*  47 */     this.buckets = new int[32];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BitArray getBlackRow(int y, BitArray row) throws NotFoundException {
/*  53 */     LuminanceSource source = getLuminanceSource();
/*  54 */     int width = source.getWidth();
/*  55 */     if (row == null || row.getSize() < width) {
/*  56 */       row = new BitArray(width);
/*     */     } else {
/*  58 */       row.clear();
/*     */     } 
/*     */     
/*  61 */     initArrays(width);
/*  62 */     byte[] localLuminances = source.getRow(y, this.luminances);
/*  63 */     int[] localBuckets = this.buckets;
/*  64 */     for (int x = 0; x < width; x++) {
/*  65 */       int pixel = localLuminances[x] & 0xFF;
/*  66 */       localBuckets[pixel >> 3] = localBuckets[pixel >> 3] + 1;
/*     */     } 
/*  68 */     int blackPoint = estimateBlackPoint(localBuckets);
/*     */     
/*  70 */     int left = localLuminances[0] & 0xFF;
/*  71 */     int center = localLuminances[1] & 0xFF;
/*  72 */     for (int i = 1; i < width - 1; i++) {
/*  73 */       int right = localLuminances[i + 1] & 0xFF;
/*     */       
/*  75 */       int luminance = (center * 4 - left - right) / 2;
/*  76 */       if (luminance < blackPoint) {
/*  77 */         row.set(i);
/*     */       }
/*  79 */       left = center;
/*  80 */       center = right;
/*     */     } 
/*  82 */     return row;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BitMatrix getBlackMatrix() throws NotFoundException {
/*  88 */     LuminanceSource source = getLuminanceSource();
/*  89 */     int width = source.getWidth();
/*  90 */     int height = source.getHeight();
/*  91 */     BitMatrix matrix = new BitMatrix(width, height);
/*     */ 
/*     */ 
/*     */     
/*  95 */     initArrays(width);
/*  96 */     int[] localBuckets = this.buckets;
/*  97 */     for (int y = 1; y < 5; y++) {
/*  98 */       int row = height * y / 5;
/*  99 */       byte[] arrayOfByte = source.getRow(row, this.luminances);
/* 100 */       int right = width * 4 / 5;
/* 101 */       for (int x = width / 5; x < right; x++) {
/* 102 */         int pixel = arrayOfByte[x] & 0xFF;
/* 103 */         localBuckets[pixel >> 3] = localBuckets[pixel >> 3] + 1;
/*     */       } 
/*     */     } 
/* 106 */     int blackPoint = estimateBlackPoint(localBuckets);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     byte[] localLuminances = source.getMatrix();
/* 112 */     for (int i = 0; i < height; i++) {
/* 113 */       int offset = i * width;
/* 114 */       for (int x = 0; x < width; x++) {
/* 115 */         int pixel = localLuminances[offset + x] & 0xFF;
/* 116 */         if (pixel < blackPoint) {
/* 117 */           matrix.set(x, i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     return matrix;
/*     */   }
/*     */ 
/*     */   
/*     */   public Binarizer createBinarizer(LuminanceSource source) {
/* 127 */     return new GlobalHistogramBinarizer(source);
/*     */   }
/*     */   
/*     */   private void initArrays(int luminanceSize) {
/* 131 */     if (this.luminances.length < luminanceSize) {
/* 132 */       this.luminances = new byte[luminanceSize];
/*     */     }
/* 134 */     for (int x = 0; x < 32; x++) {
/* 135 */       this.buckets[x] = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static int estimateBlackPoint(int[] buckets) throws NotFoundException {
/* 141 */     int numBuckets = buckets.length;
/* 142 */     int maxBucketCount = 0;
/* 143 */     int firstPeak = 0;
/* 144 */     int firstPeakSize = 0;
/* 145 */     for (int x = 0; x < numBuckets; x++) {
/* 146 */       if (buckets[x] > firstPeakSize) {
/* 147 */         firstPeak = x;
/* 148 */         firstPeakSize = buckets[x];
/*     */       } 
/* 150 */       if (buckets[x] > maxBucketCount) {
/* 151 */         maxBucketCount = buckets[x];
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 156 */     int secondPeak = 0;
/* 157 */     int secondPeakScore = 0;
/* 158 */     for (int i = 0; i < numBuckets; i++) {
/* 159 */       int distanceToBiggest = i - firstPeak;
/*     */       
/* 161 */       int score = buckets[i] * distanceToBiggest * distanceToBiggest;
/* 162 */       if (score > secondPeakScore) {
/* 163 */         secondPeak = i;
/* 164 */         secondPeakScore = score;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 169 */     if (firstPeak > secondPeak) {
/* 170 */       int temp = firstPeak;
/* 171 */       firstPeak = secondPeak;
/* 172 */       secondPeak = temp;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 177 */     if (secondPeak - firstPeak <= numBuckets / 16) {
/* 178 */       throw NotFoundException.getNotFoundInstance();
/*     */     }
/*     */ 
/*     */     
/* 182 */     int bestValley = secondPeak - 1;
/* 183 */     int bestValleyScore = -1;
/* 184 */     for (int j = secondPeak - 1; j > firstPeak; j--) {
/* 185 */       int fromFirst = j - firstPeak;
/* 186 */       int score = fromFirst * fromFirst * (secondPeak - j) * (maxBucketCount - buckets[j]);
/* 187 */       if (score > bestValleyScore) {
/* 188 */         bestValley = j;
/* 189 */         bestValleyScore = score;
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     return bestValley << 3;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\common\GlobalHistogramBinarizer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */