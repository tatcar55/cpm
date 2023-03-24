/*     */ package com.google.zxing.datamatrix.decoder;
/*     */ 
/*     */ import com.google.zxing.FormatException;
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
/*     */ public final class Version
/*     */ {
/*  29 */   private static final Version[] VERSIONS = buildVersions();
/*     */   
/*     */   private final int versionNumber;
/*     */   
/*     */   private final int symbolSizeRows;
/*     */   
/*     */   private final int symbolSizeColumns;
/*     */   
/*     */   private final int dataRegionSizeRows;
/*     */   
/*     */   private final int dataRegionSizeColumns;
/*     */   
/*     */   private final ECBlocks ecBlocks;
/*     */   private final int totalCodewords;
/*     */   
/*     */   private Version(int versionNumber, int symbolSizeRows, int symbolSizeColumns, int dataRegionSizeRows, int dataRegionSizeColumns, ECBlocks ecBlocks) {
/*  45 */     this.versionNumber = versionNumber;
/*  46 */     this.symbolSizeRows = symbolSizeRows;
/*  47 */     this.symbolSizeColumns = symbolSizeColumns;
/*  48 */     this.dataRegionSizeRows = dataRegionSizeRows;
/*  49 */     this.dataRegionSizeColumns = dataRegionSizeColumns;
/*  50 */     this.ecBlocks = ecBlocks;
/*     */ 
/*     */     
/*  53 */     int total = 0;
/*  54 */     int ecCodewords = ecBlocks.getECCodewords();
/*  55 */     ECB[] ecbArray = ecBlocks.getECBlocks();
/*  56 */     for (ECB ecBlock : ecbArray) {
/*  57 */       total += ecBlock.getCount() * (ecBlock.getDataCodewords() + ecCodewords);
/*     */     }
/*  59 */     this.totalCodewords = total;
/*     */   }
/*     */   
/*     */   public int getVersionNumber() {
/*  63 */     return this.versionNumber;
/*     */   }
/*     */   
/*     */   public int getSymbolSizeRows() {
/*  67 */     return this.symbolSizeRows;
/*     */   }
/*     */   
/*     */   public int getSymbolSizeColumns() {
/*  71 */     return this.symbolSizeColumns;
/*     */   }
/*     */   
/*     */   public int getDataRegionSizeRows() {
/*  75 */     return this.dataRegionSizeRows;
/*     */   }
/*     */   
/*     */   public int getDataRegionSizeColumns() {
/*  79 */     return this.dataRegionSizeColumns;
/*     */   }
/*     */   
/*     */   public int getTotalCodewords() {
/*  83 */     return this.totalCodewords;
/*     */   }
/*     */   
/*     */   ECBlocks getECBlocks() {
/*  87 */     return this.ecBlocks;
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
/*     */   public static Version getVersionForDimensions(int numRows, int numColumns) throws FormatException {
/*  99 */     if ((numRows & 0x1) != 0 || (numColumns & 0x1) != 0) {
/* 100 */       throw FormatException.getFormatInstance();
/*     */     }
/*     */     
/* 103 */     for (Version version : VERSIONS) {
/* 104 */       if (version.symbolSizeRows == numRows && version.symbolSizeColumns == numColumns) {
/* 105 */         return version;
/*     */       }
/*     */     } 
/*     */     
/* 109 */     throw FormatException.getFormatInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class ECBlocks
/*     */   {
/*     */     private final int ecCodewords;
/*     */ 
/*     */     
/*     */     private final Version.ECB[] ecBlocks;
/*     */ 
/*     */     
/*     */     private ECBlocks(int ecCodewords, Version.ECB ecBlocks) {
/* 123 */       this.ecCodewords = ecCodewords;
/* 124 */       this.ecBlocks = new Version.ECB[] { ecBlocks };
/*     */     }
/*     */     
/*     */     private ECBlocks(int ecCodewords, Version.ECB ecBlocks1, Version.ECB ecBlocks2) {
/* 128 */       this.ecCodewords = ecCodewords;
/* 129 */       this.ecBlocks = new Version.ECB[] { ecBlocks1, ecBlocks2 };
/*     */     }
/*     */     
/*     */     int getECCodewords() {
/* 133 */       return this.ecCodewords;
/*     */     }
/*     */     
/*     */     Version.ECB[] getECBlocks() {
/* 137 */       return this.ecBlocks;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class ECB
/*     */   {
/*     */     private final int count;
/*     */     
/*     */     private final int dataCodewords;
/*     */ 
/*     */     
/*     */     private ECB(int count, int dataCodewords) {
/* 151 */       this.count = count;
/* 152 */       this.dataCodewords = dataCodewords;
/*     */     }
/*     */     
/*     */     int getCount() {
/* 156 */       return this.count;
/*     */     }
/*     */     
/*     */     int getDataCodewords() {
/* 160 */       return this.dataCodewords;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 166 */     return String.valueOf(this.versionNumber);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Version[] buildVersions() {
/* 173 */     return new Version[] { new Version(1, 10, 10, 8, 8, new ECBlocks(5, new ECB(1, 3))), new Version(2, 12, 12, 10, 10, new ECBlocks(7, new ECB(1, 5))), new Version(3, 14, 14, 12, 12, new ECBlocks(10, new ECB(1, 8))), new Version(4, 16, 16, 14, 14, new ECBlocks(12, new ECB(1, 12))), new Version(5, 18, 18, 16, 16, new ECBlocks(14, new ECB(1, 18))), new Version(6, 20, 20, 18, 18, new ECBlocks(18, new ECB(1, 22))), new Version(7, 22, 22, 20, 20, new ECBlocks(20, new ECB(1, 30))), new Version(8, 24, 24, 22, 22, new ECBlocks(24, new ECB(1, 36))), new Version(9, 26, 26, 24, 24, new ECBlocks(28, new ECB(1, 44))), new Version(10, 32, 32, 14, 14, new ECBlocks(36, new ECB(1, 62))), new Version(11, 36, 36, 16, 16, new ECBlocks(42, new ECB(1, 86))), new Version(12, 40, 40, 18, 18, new ECBlocks(48, new ECB(1, 114))), new Version(13, 44, 44, 20, 20, new ECBlocks(56, new ECB(1, 144))), new Version(14, 48, 48, 22, 22, new ECBlocks(68, new ECB(1, 174))), new Version(15, 52, 52, 24, 24, new ECBlocks(42, new ECB(2, 102))), new Version(16, 64, 64, 14, 14, new ECBlocks(56, new ECB(2, 140))), new Version(17, 72, 72, 16, 16, new ECBlocks(36, new ECB(4, 92))), new Version(18, 80, 80, 18, 18, new ECBlocks(48, new ECB(4, 114))), new Version(19, 88, 88, 20, 20, new ECBlocks(56, new ECB(4, 144))), new Version(20, 96, 96, 22, 22, new ECBlocks(68, new ECB(4, 174))), new Version(21, 104, 104, 24, 24, new ECBlocks(56, new ECB(6, 136))), new Version(22, 120, 120, 18, 18, new ECBlocks(68, new ECB(6, 175))), new Version(23, 132, 132, 20, 20, new ECBlocks(62, new ECB(8, 163))), new Version(24, 144, 144, 22, 22, new ECBlocks(62, new ECB(8, 156), new ECB(2, 155))), new Version(25, 8, 18, 6, 16, new ECBlocks(7, new ECB(1, 5))), new Version(26, 8, 32, 6, 14, new ECBlocks(11, new ECB(1, 10))), new Version(27, 12, 26, 10, 24, new ECBlocks(14, new ECB(1, 16))), new Version(28, 12, 36, 10, 16, new ECBlocks(18, new ECB(1, 22))), new Version(29, 16, 36, 14, 16, new ECBlocks(24, new ECB(1, 32))), new Version(30, 16, 48, 14, 22, new ECBlocks(28, new ECB(1, 49))) };
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\datamatrix\decoder\Version.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */