/*     */ package com.google.zxing.datamatrix.encoder;
/*     */ 
/*     */ import com.google.zxing.Dimension;
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
/*     */ public class SymbolInfo
/*     */ {
/*  28 */   static final SymbolInfo[] PROD_SYMBOLS = new SymbolInfo[] { new SymbolInfo(false, 3, 5, 8, 8, 1), new SymbolInfo(false, 5, 7, 10, 10, 1), new SymbolInfo(true, 5, 7, 16, 6, 1), new SymbolInfo(false, 8, 10, 12, 12, 1), new SymbolInfo(true, 10, 11, 14, 6, 2), new SymbolInfo(false, 12, 12, 14, 14, 1), new SymbolInfo(true, 16, 14, 24, 10, 1), new SymbolInfo(false, 18, 14, 16, 16, 1), new SymbolInfo(false, 22, 18, 18, 18, 1), new SymbolInfo(true, 22, 18, 16, 10, 2), new SymbolInfo(false, 30, 20, 20, 20, 1), new SymbolInfo(true, 32, 24, 16, 14, 2), new SymbolInfo(false, 36, 24, 22, 22, 1), new SymbolInfo(false, 44, 28, 24, 24, 1), new SymbolInfo(true, 49, 28, 22, 14, 2), new SymbolInfo(false, 62, 36, 14, 14, 4), new SymbolInfo(false, 86, 42, 16, 16, 4), new SymbolInfo(false, 114, 48, 18, 18, 4), new SymbolInfo(false, 144, 56, 20, 20, 4), new SymbolInfo(false, 174, 68, 22, 22, 4), new SymbolInfo(false, 204, 84, 24, 24, 4, 102, 42), new SymbolInfo(false, 280, 112, 14, 14, 16, 140, 56), new SymbolInfo(false, 368, 144, 16, 16, 16, 92, 36), new SymbolInfo(false, 456, 192, 18, 18, 16, 114, 48), new SymbolInfo(false, 576, 224, 20, 20, 16, 144, 56), new SymbolInfo(false, 696, 272, 22, 22, 16, 174, 68), new SymbolInfo(false, 816, 336, 24, 24, 16, 136, 56), new SymbolInfo(false, 1050, 408, 18, 18, 36, 175, 68), new SymbolInfo(false, 1304, 496, 20, 20, 36, 163, 62), new DataMatrixSymbolInfo144() };
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
/*     */   
/*  64 */   private static SymbolInfo[] symbols = PROD_SYMBOLS;
/*     */   
/*     */   private final boolean rectangular;
/*     */   private final int dataCapacity;
/*     */   private final int errorCodewords;
/*     */   public final int matrixWidth;
/*     */   
/*     */   public static void overrideSymbolSet(SymbolInfo[] override) {
/*  72 */     symbols = override;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int matrixHeight;
/*     */   
/*     */   private final int dataRegions;
/*     */   
/*     */   private final int rsBlockData;
/*     */   
/*     */   private final int rsBlockError;
/*     */ 
/*     */   
/*     */   public SymbolInfo(boolean rectangular, int dataCapacity, int errorCodewords, int matrixWidth, int matrixHeight, int dataRegions) {
/*  86 */     this(rectangular, dataCapacity, errorCodewords, matrixWidth, matrixHeight, dataRegions, dataCapacity, errorCodewords);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SymbolInfo(boolean rectangular, int dataCapacity, int errorCodewords, int matrixWidth, int matrixHeight, int dataRegions, int rsBlockData, int rsBlockError) {
/*  93 */     this.rectangular = rectangular;
/*  94 */     this.dataCapacity = dataCapacity;
/*  95 */     this.errorCodewords = errorCodewords;
/*  96 */     this.matrixWidth = matrixWidth;
/*  97 */     this.matrixHeight = matrixHeight;
/*  98 */     this.dataRegions = dataRegions;
/*  99 */     this.rsBlockData = rsBlockData;
/* 100 */     this.rsBlockError = rsBlockError;
/*     */   }
/*     */   
/*     */   public static SymbolInfo lookup(int dataCodewords) {
/* 104 */     return lookup(dataCodewords, SymbolShapeHint.FORCE_NONE, true);
/*     */   }
/*     */   
/*     */   public static SymbolInfo lookup(int dataCodewords, SymbolShapeHint shape) {
/* 108 */     return lookup(dataCodewords, shape, true);
/*     */   }
/*     */   
/*     */   public static SymbolInfo lookup(int dataCodewords, boolean allowRectangular, boolean fail) {
/* 112 */     SymbolShapeHint shape = allowRectangular ? SymbolShapeHint.FORCE_NONE : SymbolShapeHint.FORCE_SQUARE;
/*     */     
/* 114 */     return lookup(dataCodewords, shape, fail);
/*     */   }
/*     */   
/*     */   private static SymbolInfo lookup(int dataCodewords, SymbolShapeHint shape, boolean fail) {
/* 118 */     return lookup(dataCodewords, shape, null, null, fail);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SymbolInfo lookup(int dataCodewords, SymbolShapeHint shape, Dimension minSize, Dimension maxSize, boolean fail) {
/* 126 */     for (SymbolInfo symbol : symbols) {
/* 127 */       if (shape != SymbolShapeHint.FORCE_SQUARE || !symbol.rectangular)
/*     */       {
/*     */         
/* 130 */         if (shape != SymbolShapeHint.FORCE_RECTANGLE || symbol.rectangular)
/*     */         {
/*     */           
/* 133 */           if (minSize == null || (symbol
/* 134 */             .getSymbolWidth() >= minSize.getWidth() && symbol
/* 135 */             .getSymbolHeight() >= minSize.getHeight()))
/*     */           {
/*     */             
/* 138 */             if (maxSize == null || (symbol
/* 139 */               .getSymbolWidth() <= maxSize.getWidth() && symbol
/* 140 */               .getSymbolHeight() <= maxSize.getHeight()))
/*     */             {
/*     */               
/* 143 */               if (dataCodewords <= symbol.dataCapacity)
/* 144 */                 return symbol;  }  }  } 
/*     */       }
/*     */     } 
/* 147 */     if (fail) {
/* 148 */       throw new IllegalArgumentException("Can't find a symbol arrangement that matches the message. Data codewords: " + dataCodewords);
/*     */     }
/*     */ 
/*     */     
/* 152 */     return null;
/*     */   }
/*     */   
/*     */   final int getHorizontalDataRegions() {
/* 156 */     switch (this.dataRegions) {
/*     */       case 1:
/* 158 */         return 1;
/*     */       case 2:
/* 160 */         return 2;
/*     */       case 4:
/* 162 */         return 2;
/*     */       case 16:
/* 164 */         return 4;
/*     */       case 36:
/* 166 */         return 6;
/*     */     } 
/* 168 */     throw new IllegalStateException("Cannot handle this number of data regions");
/*     */   }
/*     */ 
/*     */   
/*     */   final int getVerticalDataRegions() {
/* 173 */     switch (this.dataRegions) {
/*     */       case 1:
/* 175 */         return 1;
/*     */       case 2:
/* 177 */         return 1;
/*     */       case 4:
/* 179 */         return 2;
/*     */       case 16:
/* 181 */         return 4;
/*     */       case 36:
/* 183 */         return 6;
/*     */     } 
/* 185 */     throw new IllegalStateException("Cannot handle this number of data regions");
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getSymbolDataWidth() {
/* 190 */     return getHorizontalDataRegions() * this.matrixWidth;
/*     */   }
/*     */   
/*     */   public final int getSymbolDataHeight() {
/* 194 */     return getVerticalDataRegions() * this.matrixHeight;
/*     */   }
/*     */   
/*     */   public final int getSymbolWidth() {
/* 198 */     return getSymbolDataWidth() + getHorizontalDataRegions() * 2;
/*     */   }
/*     */   
/*     */   public final int getSymbolHeight() {
/* 202 */     return getSymbolDataHeight() + getVerticalDataRegions() * 2;
/*     */   }
/*     */   
/*     */   public int getCodewordCount() {
/* 206 */     return this.dataCapacity + this.errorCodewords;
/*     */   }
/*     */   
/*     */   public int getInterleavedBlockCount() {
/* 210 */     return this.dataCapacity / this.rsBlockData;
/*     */   }
/*     */   
/*     */   public final int getDataCapacity() {
/* 214 */     return this.dataCapacity;
/*     */   }
/*     */   
/*     */   public final int getErrorCodewords() {
/* 218 */     return this.errorCodewords;
/*     */   }
/*     */   
/*     */   public int getDataLengthForInterleavedBlock(int index) {
/* 222 */     return this.rsBlockData;
/*     */   }
/*     */   
/*     */   public final int getErrorLengthForInterleavedBlock(int index) {
/* 226 */     return this.rsBlockError;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 231 */     StringBuilder sb = new StringBuilder();
/* 232 */     sb.append(this.rectangular ? "Rectangular Symbol:" : "Square Symbol:");
/* 233 */     sb.append(" data region ").append(this.matrixWidth).append('x').append(this.matrixHeight);
/* 234 */     sb.append(", symbol size ").append(getSymbolWidth()).append('x').append(getSymbolHeight());
/* 235 */     sb.append(", symbol data size ").append(getSymbolDataWidth()).append('x').append(getSymbolDataHeight());
/* 236 */     sb.append(", codewords ").append(this.dataCapacity).append('+').append(this.errorCodewords);
/* 237 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\datamatrix\encoder\SymbolInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */