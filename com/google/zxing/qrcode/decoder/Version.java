/*     */ package com.google.zxing.qrcode.decoder;
/*     */ 
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.common.BitMatrix;
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
/*     */ public final class Version
/*     */ {
/*  33 */   private static final int[] VERSION_DECODE_INFO = new int[] { 31892, 34236, 39577, 42195, 48118, 51042, 55367, 58893, 63784, 68472, 70749, 76311, 79154, 84390, 87683, 92361, 96236, 102084, 102881, 110507, 110734, 117786, 119615, 126325, 127568, 133589, 136944, 141498, 145311, 150283, 152622, 158308, 161089, 167017 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   private static final Version[] VERSIONS = buildVersions();
/*     */   
/*     */   private final int versionNumber;
/*     */   
/*     */   private final int[] alignmentPatternCenters;
/*     */   
/*     */   private final ECBlocks[] ecBlocks;
/*     */   private final int totalCodewords;
/*     */   
/*     */   private Version(int versionNumber, int[] alignmentPatternCenters, ECBlocks... ecBlocks) {
/*  53 */     this.versionNumber = versionNumber;
/*  54 */     this.alignmentPatternCenters = alignmentPatternCenters;
/*  55 */     this.ecBlocks = ecBlocks;
/*  56 */     int total = 0;
/*  57 */     int ecCodewords = ecBlocks[0].getECCodewordsPerBlock();
/*  58 */     ECB[] ecbArray = ecBlocks[0].getECBlocks();
/*  59 */     for (ECB ecBlock : ecbArray) {
/*  60 */       total += ecBlock.getCount() * (ecBlock.getDataCodewords() + ecCodewords);
/*     */     }
/*  62 */     this.totalCodewords = total;
/*     */   }
/*     */   
/*     */   public int getVersionNumber() {
/*  66 */     return this.versionNumber;
/*     */   }
/*     */   
/*     */   public int[] getAlignmentPatternCenters() {
/*  70 */     return this.alignmentPatternCenters;
/*     */   }
/*     */   
/*     */   public int getTotalCodewords() {
/*  74 */     return this.totalCodewords;
/*     */   }
/*     */   
/*     */   public int getDimensionForVersion() {
/*  78 */     return 17 + 4 * this.versionNumber;
/*     */   }
/*     */   
/*     */   public ECBlocks getECBlocksForLevel(ErrorCorrectionLevel ecLevel) {
/*  82 */     return this.ecBlocks[ecLevel.ordinal()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Version getProvisionalVersionForDimension(int dimension) throws FormatException {
/*  93 */     if (dimension % 4 != 1) {
/*  94 */       throw FormatException.getFormatInstance();
/*     */     }
/*     */     try {
/*  97 */       return getVersionForNumber((dimension - 17) / 4);
/*  98 */     } catch (IllegalArgumentException ignored) {
/*  99 */       throw FormatException.getFormatInstance();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Version getVersionForNumber(int versionNumber) {
/* 104 */     if (versionNumber < 1 || versionNumber > 40) {
/* 105 */       throw new IllegalArgumentException();
/*     */     }
/* 107 */     return VERSIONS[versionNumber - 1];
/*     */   }
/*     */   
/*     */   static Version decodeVersionInformation(int versionBits) {
/* 111 */     int bestDifference = Integer.MAX_VALUE;
/* 112 */     int bestVersion = 0;
/* 113 */     for (int i = 0; i < VERSION_DECODE_INFO.length; i++) {
/* 114 */       int targetVersion = VERSION_DECODE_INFO[i];
/*     */       
/* 116 */       if (targetVersion == versionBits) {
/* 117 */         return getVersionForNumber(i + 7);
/*     */       }
/*     */ 
/*     */       
/* 121 */       int bitsDifference = FormatInformation.numBitsDiffering(versionBits, targetVersion);
/* 122 */       if (bitsDifference < bestDifference) {
/* 123 */         bestVersion = i + 7;
/* 124 */         bestDifference = bitsDifference;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 129 */     if (bestDifference <= 3) {
/* 130 */       return getVersionForNumber(bestVersion);
/*     */     }
/*     */     
/* 133 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   BitMatrix buildFunctionPattern() {
/* 140 */     int dimension = getDimensionForVersion();
/* 141 */     BitMatrix bitMatrix = new BitMatrix(dimension);
/*     */ 
/*     */     
/* 144 */     bitMatrix.setRegion(0, 0, 9, 9);
/*     */     
/* 146 */     bitMatrix.setRegion(dimension - 8, 0, 8, 9);
/*     */     
/* 148 */     bitMatrix.setRegion(0, dimension - 8, 9, 8);
/*     */ 
/*     */     
/* 151 */     int max = this.alignmentPatternCenters.length;
/* 152 */     for (int x = 0; x < max; x++) {
/* 153 */       int i = this.alignmentPatternCenters[x] - 2;
/* 154 */       for (int y = 0; y < max; y++) {
/* 155 */         if ((x != 0 || (y != 0 && y != max - 1)) && (x != max - 1 || y != 0))
/*     */         {
/*     */ 
/*     */           
/* 159 */           bitMatrix.setRegion(this.alignmentPatternCenters[y] - 2, i, 5, 5);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     bitMatrix.setRegion(6, 9, 1, dimension - 17);
/*     */     
/* 166 */     bitMatrix.setRegion(9, 6, dimension - 17, 1);
/*     */     
/* 168 */     if (this.versionNumber > 6) {
/*     */       
/* 170 */       bitMatrix.setRegion(dimension - 11, 0, 3, 6);
/*     */       
/* 172 */       bitMatrix.setRegion(0, dimension - 11, 6, 3);
/*     */     } 
/*     */     
/* 175 */     return bitMatrix;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class ECBlocks
/*     */   {
/*     */     private final int ecCodewordsPerBlock;
/*     */ 
/*     */     
/*     */     private final Version.ECB[] ecBlocks;
/*     */ 
/*     */     
/*     */     ECBlocks(int ecCodewordsPerBlock, Version.ECB... ecBlocks) {
/* 189 */       this.ecCodewordsPerBlock = ecCodewordsPerBlock;
/* 190 */       this.ecBlocks = ecBlocks;
/*     */     }
/*     */     
/*     */     public int getECCodewordsPerBlock() {
/* 194 */       return this.ecCodewordsPerBlock;
/*     */     }
/*     */     
/*     */     public int getNumBlocks() {
/* 198 */       int total = 0;
/* 199 */       for (Version.ECB ecBlock : this.ecBlocks) {
/* 200 */         total += ecBlock.getCount();
/*     */       }
/* 202 */       return total;
/*     */     }
/*     */     
/*     */     public int getTotalECCodewords() {
/* 206 */       return this.ecCodewordsPerBlock * getNumBlocks();
/*     */     }
/*     */     
/*     */     public Version.ECB[] getECBlocks() {
/* 210 */       return this.ecBlocks;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class ECB
/*     */   {
/*     */     private final int count;
/*     */     
/*     */     private final int dataCodewords;
/*     */ 
/*     */     
/*     */     ECB(int count, int dataCodewords) {
/* 224 */       this.count = count;
/* 225 */       this.dataCodewords = dataCodewords;
/*     */     }
/*     */     
/*     */     public int getCount() {
/* 229 */       return this.count;
/*     */     }
/*     */     
/*     */     public int getDataCodewords() {
/* 233 */       return this.dataCodewords;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 239 */     return String.valueOf(this.versionNumber);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Version[] buildVersions() {
/* 246 */     return new Version[] { new Version(1, new int[0], new ECBlocks[] { new ECBlocks(7, new ECB[] { new ECB(1, 19) }), new ECBlocks(10, new ECB[] { new ECB(1, 16) }), new ECBlocks(13, new ECB[] { new ECB(1, 13) }), new ECBlocks(17, new ECB[] { new ECB(1, 9) }) }), new Version(2, new int[] { 6, 18 }, new ECBlocks[] { new ECBlocks(10, new ECB[] { new ECB(1, 34) }), new ECBlocks(16, new ECB[] { new ECB(1, 28) }), new ECBlocks(22, new ECB[] { new ECB(1, 22) }), new ECBlocks(28, new ECB[] { new ECB(1, 16) }) }), new Version(3, new int[] { 6, 22 }, new ECBlocks[] { new ECBlocks(15, new ECB[] { new ECB(1, 55) }), new ECBlocks(26, new ECB[] { new ECB(1, 44) }), new ECBlocks(18, new ECB[] { new ECB(2, 17) }), new ECBlocks(22, new ECB[] { new ECB(2, 13) }) }), new Version(4, new int[] { 6, 26 }, new ECBlocks[] { new ECBlocks(20, new ECB[] { new ECB(1, 80) }), new ECBlocks(18, new ECB[] { new ECB(2, 32) }), new ECBlocks(26, new ECB[] { new ECB(2, 24) }), new ECBlocks(16, new ECB[] { new ECB(4, 9) }) }), new Version(5, new int[] { 6, 30 }, new ECBlocks[] { new ECBlocks(26, new ECB[] { new ECB(1, 108) }), new ECBlocks(24, new ECB[] { new ECB(2, 43) }), new ECBlocks(18, new ECB[] { new ECB(2, 15), new ECB(2, 16) }), new ECBlocks(22, new ECB[] { new ECB(2, 11), new ECB(2, 12) }) }), new Version(6, new int[] { 6, 34 }, new ECBlocks[] { new ECBlocks(18, new ECB[] { new ECB(2, 68) }), new ECBlocks(16, new ECB[] { new ECB(4, 27) }), new ECBlocks(24, new ECB[] { new ECB(4, 19) }), new ECBlocks(28, new ECB[] { new ECB(4, 15) }) }), new Version(7, new int[] { 6, 22, 38 }, new ECBlocks[] { new ECBlocks(20, new ECB[] { new ECB(2, 78) }), new ECBlocks(18, new ECB[] { new ECB(4, 31) }), new ECBlocks(18, new ECB[] { new ECB(2, 14), new ECB(4, 15) }), new ECBlocks(26, new ECB[] { new ECB(4, 13), new ECB(1, 14) }) }), new Version(8, new int[] { 6, 24, 42 }, new ECBlocks[] { new ECBlocks(24, new ECB[] { new ECB(2, 97) }), new ECBlocks(22, new ECB[] { new ECB(2, 38), new ECB(2, 39) }), new ECBlocks(22, new ECB[] { new ECB(4, 18), new ECB(2, 19) }), new ECBlocks(26, new ECB[] { new ECB(4, 14), new ECB(2, 15) }) }), new Version(9, new int[] { 6, 26, 46 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(2, 116) }), new ECBlocks(22, new ECB[] { new ECB(3, 36), new ECB(2, 37) }), new ECBlocks(20, new ECB[] { new ECB(4, 16), new ECB(4, 17) }), new ECBlocks(24, new ECB[] { new ECB(4, 12), new ECB(4, 13) }) }), new Version(10, new int[] { 6, 28, 50 }, new ECBlocks[] { new ECBlocks(18, new ECB[] { new ECB(2, 68), new ECB(2, 69) }), new ECBlocks(26, new ECB[] { new ECB(4, 43), new ECB(1, 44) }), new ECBlocks(24, new ECB[] { new ECB(6, 19), new ECB(2, 20) }), new ECBlocks(28, new ECB[] { new ECB(6, 15), new ECB(2, 16) }) }), new Version(11, new int[] { 6, 30, 54 }, new ECBlocks[] { new ECBlocks(20, new ECB[] { new ECB(4, 81) }), new ECBlocks(30, new ECB[] { new ECB(1, 50), new ECB(4, 51) }), new ECBlocks(28, new ECB[] { new ECB(4, 22), new ECB(4, 23) }), new ECBlocks(24, new ECB[] { new ECB(3, 12), new ECB(8, 13) }) }), new Version(12, new int[] { 6, 32, 58 }, new ECBlocks[] { new ECBlocks(24, new ECB[] { new ECB(2, 92), new ECB(2, 93) }), new ECBlocks(22, new ECB[] { new ECB(6, 36), new ECB(2, 37) }), new ECBlocks(26, new ECB[] { new ECB(4, 20), new ECB(6, 21) }), new ECBlocks(28, new ECB[] { new ECB(7, 14), new ECB(4, 15) }) }), new Version(13, new int[] { 6, 34, 62 }, new ECBlocks[] { new ECBlocks(26, new ECB[] { new ECB(4, 107) }), new ECBlocks(22, new ECB[] { new ECB(8, 37), new ECB(1, 38) }), new ECBlocks(24, new ECB[] { new ECB(8, 20), new ECB(4, 21) }), new ECBlocks(22, new ECB[] { new ECB(12, 11), new ECB(4, 12) }) }), new Version(14, new int[] { 6, 26, 46, 66 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(3, 115), new ECB(1, 116) }), new ECBlocks(24, new ECB[] { new ECB(4, 40), new ECB(5, 41) }), new ECBlocks(20, new ECB[] { new ECB(11, 16), new ECB(5, 17) }), new ECBlocks(24, new ECB[] { new ECB(11, 12), new ECB(5, 13) }) }), new Version(15, new int[] { 6, 26, 48, 70 }, new ECBlocks[] { new ECBlocks(22, new ECB[] { new ECB(5, 87), new ECB(1, 88) }), new ECBlocks(24, new ECB[] { new ECB(5, 41), new ECB(5, 42) }), new ECBlocks(30, new ECB[] { new ECB(5, 24), new ECB(7, 25) }), new ECBlocks(24, new ECB[] { new ECB(11, 12), new ECB(7, 13) }) }), new Version(16, new int[] { 6, 26, 50, 74 }, new ECBlocks[] { new ECBlocks(24, new ECB[] { new ECB(5, 98), new ECB(1, 99) }), new ECBlocks(28, new ECB[] { new ECB(7, 45), new ECB(3, 46) }), new ECBlocks(24, new ECB[] { new ECB(15, 19), new ECB(2, 20) }), new ECBlocks(30, new ECB[] { new ECB(3, 15), new ECB(13, 16) }) }), new Version(17, new int[] { 6, 30, 54, 78 }, new ECBlocks[] { new ECBlocks(28, new ECB[] { new ECB(1, 107), new ECB(5, 108) }), new ECBlocks(28, new ECB[] { new ECB(10, 46), new ECB(1, 47) }), new ECBlocks(28, new ECB[] { new ECB(1, 22), new ECB(15, 23) }), new ECBlocks(28, new ECB[] { new ECB(2, 14), new ECB(17, 15) }) }), new Version(18, new int[] { 6, 30, 56, 82 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(5, 120), new ECB(1, 121) }), new ECBlocks(26, new ECB[] { new ECB(9, 43), new ECB(4, 44) }), new ECBlocks(28, new ECB[] { new ECB(17, 22), new ECB(1, 23) }), new ECBlocks(28, new ECB[] { new ECB(2, 14), new ECB(19, 15) }) }), new Version(19, new int[] { 6, 30, 58, 86 }, new ECBlocks[] { new ECBlocks(28, new ECB[] { new ECB(3, 113), new ECB(4, 114) }), new ECBlocks(26, new ECB[] { new ECB(3, 44), new ECB(11, 45) }), new ECBlocks(26, new ECB[] { new ECB(17, 21), new ECB(4, 22) }), new ECBlocks(26, new ECB[] { new ECB(9, 13), new ECB(16, 14) }) }), new Version(20, new int[] { 6, 34, 62, 90 }, new ECBlocks[] { new ECBlocks(28, new ECB[] { new ECB(3, 107), new ECB(5, 108) }), new ECBlocks(26, new ECB[] { new ECB(3, 41), new ECB(13, 42) }), new ECBlocks(30, new ECB[] { new ECB(15, 24), new ECB(5, 25) }), new ECBlocks(28, new ECB[] { new ECB(15, 15), new ECB(10, 16) }) }), new Version(21, new int[] { 6, 28, 50, 72, 94 }, new ECBlocks[] { new ECBlocks(28, new ECB[] { new ECB(4, 116), new ECB(4, 117) }), new ECBlocks(26, new ECB[] { new ECB(17, 42) }), new ECBlocks(28, new ECB[] { new ECB(17, 22), new ECB(6, 23) }), new ECBlocks(30, new ECB[] { new ECB(19, 16), new ECB(6, 17) }) }), new Version(22, new int[] { 6, 26, 50, 74, 98 }, new ECBlocks[] { new ECBlocks(28, new ECB[] { new ECB(2, 111), new ECB(7, 112) }), new ECBlocks(28, new ECB[] { new ECB(17, 46) }), new ECBlocks(30, new ECB[] { new ECB(7, 24), new ECB(16, 25) }), new ECBlocks(24, new ECB[] { new ECB(34, 13) }) }), new Version(23, new int[] { 6, 30, 54, 78, 102 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(4, 121), new ECB(5, 122) }), new ECBlocks(28, new ECB[] { new ECB(4, 47), new ECB(14, 48) }), new ECBlocks(30, new ECB[] { new ECB(11, 24), new ECB(14, 25) }), new ECBlocks(30, new ECB[] { new ECB(16, 15), new ECB(14, 16) }) }), new Version(24, new int[] { 6, 28, 54, 80, 106 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(6, 117), new ECB(4, 118) }), new ECBlocks(28, new ECB[] { new ECB(6, 45), new ECB(14, 46) }), new ECBlocks(30, new ECB[] { new ECB(11, 24), new ECB(16, 25) }), new ECBlocks(30, new ECB[] { new ECB(30, 16), new ECB(2, 17) }) }), new Version(25, new int[] { 6, 32, 58, 84, 110 }, new ECBlocks[] { new ECBlocks(26, new ECB[] { new ECB(8, 106), new ECB(4, 107) }), new ECBlocks(28, new ECB[] { new ECB(8, 47), new ECB(13, 48) }), new ECBlocks(30, new ECB[] { new ECB(7, 24), new ECB(22, 25) }), new ECBlocks(30, new ECB[] { new ECB(22, 15), new ECB(13, 16) }) }), new Version(26, new int[] { 6, 30, 58, 86, 114 }, new ECBlocks[] { new ECBlocks(28, new ECB[] { new ECB(10, 114), new ECB(2, 115) }), new ECBlocks(28, new ECB[] { new ECB(19, 46), new ECB(4, 47) }), new ECBlocks(28, new ECB[] { new ECB(28, 22), new ECB(6, 23) }), new ECBlocks(30, new ECB[] { new ECB(33, 16), new ECB(4, 17) }) }), new Version(27, new int[] { 6, 34, 62, 90, 118 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(8, 122), new ECB(4, 123) }), new ECBlocks(28, new ECB[] { new ECB(22, 45), new ECB(3, 46) }), new ECBlocks(30, new ECB[] { new ECB(8, 23), new ECB(26, 24) }), new ECBlocks(30, new ECB[] { new ECB(12, 15), new ECB(28, 16) }) }), new Version(28, new int[] { 6, 26, 50, 74, 98, 122 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(3, 117), new ECB(10, 118) }), new ECBlocks(28, new ECB[] { new ECB(3, 45), new ECB(23, 46) }), new ECBlocks(30, new ECB[] { new ECB(4, 24), new ECB(31, 25) }), new ECBlocks(30, new ECB[] { new ECB(11, 15), new ECB(31, 16) }) }), new Version(29, new int[] { 6, 30, 54, 78, 102, 126 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(7, 116), new ECB(7, 117) }), new ECBlocks(28, new ECB[] { new ECB(21, 45), new ECB(7, 46) }), new ECBlocks(30, new ECB[] { new ECB(1, 23), new ECB(37, 24) }), new ECBlocks(30, new ECB[] { new ECB(19, 15), new ECB(26, 16) }) }), new Version(30, new int[] { 6, 26, 52, 78, 104, 130 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(5, 115), new ECB(10, 116) }), new ECBlocks(28, new ECB[] { new ECB(19, 47), new ECB(10, 48) }), new ECBlocks(30, new ECB[] { new ECB(15, 24), new ECB(25, 25) }), new ECBlocks(30, new ECB[] { new ECB(23, 15), new ECB(25, 16) }) }), new Version(31, new int[] { 6, 30, 56, 82, 108, 134 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(13, 115), new ECB(3, 116) }), new ECBlocks(28, new ECB[] { new ECB(2, 46), new ECB(29, 47) }), new ECBlocks(30, new ECB[] { new ECB(42, 24), new ECB(1, 25) }), new ECBlocks(30, new ECB[] { new ECB(23, 15), new ECB(28, 16) }) }), new Version(32, new int[] { 6, 34, 60, 86, 112, 138 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(17, 115) }), new ECBlocks(28, new ECB[] { new ECB(10, 46), new ECB(23, 47) }), new ECBlocks(30, new ECB[] { new ECB(10, 24), new ECB(35, 25) }), new ECBlocks(30, new ECB[] { new ECB(19, 15), new ECB(35, 16) }) }), new Version(33, new int[] { 6, 30, 58, 86, 114, 142 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(17, 115), new ECB(1, 116) }), new ECBlocks(28, new ECB[] { new ECB(14, 46), new ECB(21, 47) }), new ECBlocks(30, new ECB[] { new ECB(29, 24), new ECB(19, 25) }), new ECBlocks(30, new ECB[] { new ECB(11, 15), new ECB(46, 16) }) }), new Version(34, new int[] { 6, 34, 62, 90, 118, 146 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(13, 115), new ECB(6, 116) }), new ECBlocks(28, new ECB[] { new ECB(14, 46), new ECB(23, 47) }), new ECBlocks(30, new ECB[] { new ECB(44, 24), new ECB(7, 25) }), new ECBlocks(30, new ECB[] { new ECB(59, 16), new ECB(1, 17) }) }), new Version(35, new int[] { 6, 30, 54, 78, 102, 126, 150 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(12, 121), new ECB(7, 122) }), new ECBlocks(28, new ECB[] { new ECB(12, 47), new ECB(26, 48) }), new ECBlocks(30, new ECB[] { new ECB(39, 24), new ECB(14, 25) }), new ECBlocks(30, new ECB[] { new ECB(22, 15), new ECB(41, 16) }) }), new Version(36, new int[] { 6, 24, 50, 76, 102, 128, 154 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(6, 121), new ECB(14, 122) }), new ECBlocks(28, new ECB[] { new ECB(6, 47), new ECB(34, 48) }), new ECBlocks(30, new ECB[] { new ECB(46, 24), new ECB(10, 25) }), new ECBlocks(30, new ECB[] { new ECB(2, 15), new ECB(64, 16) }) }), new Version(37, new int[] { 6, 28, 54, 80, 106, 132, 158 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(17, 122), new ECB(4, 123) }), new ECBlocks(28, new ECB[] { new ECB(29, 46), new ECB(14, 47) }), new ECBlocks(30, new ECB[] { new ECB(49, 24), new ECB(10, 25) }), new ECBlocks(30, new ECB[] { new ECB(24, 15), new ECB(46, 16) }) }), new Version(38, new int[] { 6, 32, 58, 84, 110, 136, 162 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(4, 122), new ECB(18, 123) }), new ECBlocks(28, new ECB[] { new ECB(13, 46), new ECB(32, 47) }), new ECBlocks(30, new ECB[] { new ECB(48, 24), new ECB(14, 25) }), new ECBlocks(30, new ECB[] { new ECB(42, 15), new ECB(32, 16) }) }), new Version(39, new int[] { 6, 26, 54, 82, 110, 138, 166 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(20, 117), new ECB(4, 118) }), new ECBlocks(28, new ECB[] { new ECB(40, 47), new ECB(7, 48) }), new ECBlocks(30, new ECB[] { new ECB(43, 24), new ECB(22, 25) }), new ECBlocks(30, new ECB[] { new ECB(10, 15), new ECB(67, 16) }) }), new Version(40, new int[] { 6, 30, 58, 86, 114, 142, 170 }, new ECBlocks[] { new ECBlocks(30, new ECB[] { new ECB(19, 118), new ECB(6, 119) }), new ECBlocks(28, new ECB[] { new ECB(18, 47), new ECB(31, 48) }), new ECBlocks(30, new ECB[] { new ECB(34, 24), new ECB(34, 25) }), new ECBlocks(30, new ECB[] { new ECB(20, 15), new ECB(61, 16) }) }) };
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\qrcode\decoder\Version.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */