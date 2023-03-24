/*     */ package com.google.zxing;
/*     */ 
/*     */ import java.util.EnumMap;
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
/*     */ public final class Result
/*     */ {
/*     */   private final String text;
/*     */   private final byte[] rawBytes;
/*     */   private ResultPoint[] resultPoints;
/*     */   private final BarcodeFormat format;
/*     */   private Map<ResultMetadataType, Object> resultMetadata;
/*     */   private final long timestamp;
/*     */   
/*     */   public Result(String text, byte[] rawBytes, ResultPoint[] resultPoints, BarcodeFormat format) {
/*  40 */     this(text, rawBytes, resultPoints, format, System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result(String text, byte[] rawBytes, ResultPoint[] resultPoints, BarcodeFormat format, long timestamp) {
/*  48 */     this.text = text;
/*  49 */     this.rawBytes = rawBytes;
/*  50 */     this.resultPoints = resultPoints;
/*  51 */     this.format = format;
/*  52 */     this.resultMetadata = null;
/*  53 */     this.timestamp = timestamp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/*  60 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getRawBytes() {
/*  67 */     return this.rawBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultPoint[] getResultPoints() {
/*  76 */     return this.resultPoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BarcodeFormat getBarcodeFormat() {
/*  83 */     return this.format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<ResultMetadataType, Object> getResultMetadata() {
/*  92 */     return this.resultMetadata;
/*     */   }
/*     */   
/*     */   public void putMetadata(ResultMetadataType type, Object value) {
/*  96 */     if (this.resultMetadata == null) {
/*  97 */       this.resultMetadata = new EnumMap<>(ResultMetadataType.class);
/*     */     }
/*  99 */     this.resultMetadata.put(type, value);
/*     */   }
/*     */   
/*     */   public void putAllMetadata(Map<ResultMetadataType, Object> metadata) {
/* 103 */     if (metadata != null) {
/* 104 */       if (this.resultMetadata == null) {
/* 105 */         this.resultMetadata = metadata;
/*     */       } else {
/* 107 */         this.resultMetadata.putAll(metadata);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void addResultPoints(ResultPoint[] newPoints) {
/* 113 */     ResultPoint[] oldPoints = this.resultPoints;
/* 114 */     if (oldPoints == null) {
/* 115 */       this.resultPoints = newPoints;
/* 116 */     } else if (newPoints != null && newPoints.length > 0) {
/* 117 */       ResultPoint[] allPoints = new ResultPoint[oldPoints.length + newPoints.length];
/* 118 */       System.arraycopy(oldPoints, 0, allPoints, 0, oldPoints.length);
/* 119 */       System.arraycopy(newPoints, 0, allPoints, oldPoints.length, newPoints.length);
/* 120 */       this.resultPoints = allPoints;
/*     */     } 
/*     */   }
/*     */   
/*     */   public long getTimestamp() {
/* 125 */     return this.timestamp;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 130 */     return this.text;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\Result.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */