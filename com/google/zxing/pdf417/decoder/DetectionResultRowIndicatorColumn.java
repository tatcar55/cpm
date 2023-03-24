/*     */ package com.google.zxing.pdf417.decoder;
/*     */ 
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.ResultPoint;
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
/*     */ final class DetectionResultRowIndicatorColumn
/*     */   extends DetectionResultColumn
/*     */ {
/*     */   private final boolean isLeft;
/*     */   
/*     */   DetectionResultRowIndicatorColumn(BoundingBox boundingBox, boolean isLeft) {
/*  31 */     super(boundingBox);
/*  32 */     this.isLeft = isLeft;
/*     */   }
/*     */   
/*     */   void setRowNumbers() {
/*  36 */     for (Codeword codeword : getCodewords()) {
/*  37 */       if (codeword != null) {
/*  38 */         codeword.setRowNumberAsRowIndicatorColumn();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int adjustCompleteIndicatorColumnRowNumbers(BarcodeMetadata barcodeMetadata) {
/*  48 */     Codeword[] codewords = getCodewords();
/*  49 */     setRowNumbers();
/*  50 */     removeIncorrectCodewords(codewords, barcodeMetadata);
/*  51 */     BoundingBox boundingBox = getBoundingBox();
/*  52 */     ResultPoint top = this.isLeft ? boundingBox.getTopLeft() : boundingBox.getTopRight();
/*  53 */     ResultPoint bottom = this.isLeft ? boundingBox.getBottomLeft() : boundingBox.getBottomRight();
/*  54 */     int firstRow = imageRowToCodewordIndex((int)top.getY());
/*  55 */     int lastRow = imageRowToCodewordIndex((int)bottom.getY());
/*     */ 
/*     */     
/*  58 */     float averageRowHeight = (lastRow - firstRow) / barcodeMetadata.getRowCount();
/*  59 */     int barcodeRow = -1;
/*  60 */     int maxRowHeight = 1;
/*  61 */     int currentRowHeight = 0;
/*  62 */     for (int codewordsRow = firstRow; codewordsRow < lastRow; codewordsRow++) {
/*  63 */       if (codewords[codewordsRow] != null) {
/*     */ 
/*     */         
/*  66 */         Codeword codeword = codewords[codewordsRow];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  76 */         int rowDifference = codeword.getRowNumber() - barcodeRow;
/*     */ 
/*     */ 
/*     */         
/*  80 */         if (rowDifference == 0) {
/*  81 */           currentRowHeight++;
/*  82 */         } else if (rowDifference == 1) {
/*  83 */           maxRowHeight = Math.max(maxRowHeight, currentRowHeight);
/*  84 */           currentRowHeight = 1;
/*  85 */           barcodeRow = codeword.getRowNumber();
/*  86 */         } else if (rowDifference < 0 || codeword
/*  87 */           .getRowNumber() >= barcodeMetadata.getRowCount() || rowDifference > codewordsRow) {
/*     */           
/*  89 */           codewords[codewordsRow] = null;
/*     */         } else {
/*     */           int checkedRows;
/*  92 */           if (maxRowHeight > 2) {
/*  93 */             checkedRows = (maxRowHeight - 2) * rowDifference;
/*     */           } else {
/*  95 */             checkedRows = rowDifference;
/*     */           } 
/*  97 */           boolean closePreviousCodewordFound = (checkedRows >= codewordsRow);
/*  98 */           for (int i = 1; i <= checkedRows && !closePreviousCodewordFound; i++)
/*     */           {
/*     */             
/* 101 */             closePreviousCodewordFound = (codewords[codewordsRow - i] != null);
/*     */           }
/* 103 */           if (closePreviousCodewordFound) {
/* 104 */             codewords[codewordsRow] = null;
/*     */           } else {
/* 106 */             barcodeRow = codeword.getRowNumber();
/* 107 */             currentRowHeight = 1;
/*     */           } 
/*     */         } 
/*     */       } 
/* 111 */     }  return (int)(averageRowHeight + 0.5D);
/*     */   }
/*     */   
/*     */   int[] getRowHeights() throws FormatException {
/* 115 */     BarcodeMetadata barcodeMetadata = getBarcodeMetadata();
/* 116 */     if (barcodeMetadata == null) {
/* 117 */       return null;
/*     */     }
/* 119 */     adjustIncompleteIndicatorColumnRowNumbers(barcodeMetadata);
/* 120 */     int[] result = new int[barcodeMetadata.getRowCount()];
/* 121 */     for (Codeword codeword : getCodewords()) {
/* 122 */       if (codeword != null) {
/* 123 */         int rowNumber = codeword.getRowNumber();
/* 124 */         if (rowNumber >= result.length) {
/* 125 */           throw FormatException.getFormatInstance();
/*     */         }
/* 127 */         result[rowNumber] = result[rowNumber] + 1;
/*     */       } 
/*     */     } 
/* 130 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int adjustIncompleteIndicatorColumnRowNumbers(BarcodeMetadata barcodeMetadata) {
/* 137 */     BoundingBox boundingBox = getBoundingBox();
/* 138 */     ResultPoint top = this.isLeft ? boundingBox.getTopLeft() : boundingBox.getTopRight();
/* 139 */     ResultPoint bottom = this.isLeft ? boundingBox.getBottomLeft() : boundingBox.getBottomRight();
/* 140 */     int firstRow = imageRowToCodewordIndex((int)top.getY());
/* 141 */     int lastRow = imageRowToCodewordIndex((int)bottom.getY());
/* 142 */     float averageRowHeight = (lastRow - firstRow) / barcodeMetadata.getRowCount();
/* 143 */     Codeword[] codewords = getCodewords();
/* 144 */     int barcodeRow = -1;
/* 145 */     int maxRowHeight = 1;
/* 146 */     int currentRowHeight = 0;
/* 147 */     for (int codewordsRow = firstRow; codewordsRow < lastRow; codewordsRow++) {
/* 148 */       if (codewords[codewordsRow] != null) {
/*     */ 
/*     */         
/* 151 */         Codeword codeword = codewords[codewordsRow];
/*     */         
/* 153 */         codeword.setRowNumberAsRowIndicatorColumn();
/*     */         
/* 155 */         int rowDifference = codeword.getRowNumber() - barcodeRow;
/*     */ 
/*     */ 
/*     */         
/* 159 */         if (rowDifference == 0) {
/* 160 */           currentRowHeight++;
/* 161 */         } else if (rowDifference == 1) {
/* 162 */           maxRowHeight = Math.max(maxRowHeight, currentRowHeight);
/* 163 */           currentRowHeight = 1;
/* 164 */           barcodeRow = codeword.getRowNumber();
/* 165 */         } else if (codeword.getRowNumber() >= barcodeMetadata.getRowCount()) {
/* 166 */           codewords[codewordsRow] = null;
/*     */         } else {
/* 168 */           barcodeRow = codeword.getRowNumber();
/* 169 */           currentRowHeight = 1;
/*     */         } 
/*     */       } 
/* 172 */     }  return (int)(averageRowHeight + 0.5D);
/*     */   }
/*     */   
/*     */   BarcodeMetadata getBarcodeMetadata() {
/* 176 */     Codeword[] codewords = getCodewords();
/* 177 */     BarcodeValue barcodeColumnCount = new BarcodeValue();
/* 178 */     BarcodeValue barcodeRowCountUpperPart = new BarcodeValue();
/* 179 */     BarcodeValue barcodeRowCountLowerPart = new BarcodeValue();
/* 180 */     BarcodeValue barcodeECLevel = new BarcodeValue();
/* 181 */     for (Codeword codeword : codewords) {
/* 182 */       if (codeword != null) {
/*     */ 
/*     */         
/* 185 */         codeword.setRowNumberAsRowIndicatorColumn();
/* 186 */         int rowIndicatorValue = codeword.getValue() % 30;
/* 187 */         int codewordRowNumber = codeword.getRowNumber();
/* 188 */         if (!this.isLeft) {
/* 189 */           codewordRowNumber += 2;
/*     */         }
/* 191 */         switch (codewordRowNumber % 3) {
/*     */           case 0:
/* 193 */             barcodeRowCountUpperPart.setValue(rowIndicatorValue * 3 + 1);
/*     */             break;
/*     */           case 1:
/* 196 */             barcodeECLevel.setValue(rowIndicatorValue / 3);
/* 197 */             barcodeRowCountLowerPart.setValue(rowIndicatorValue % 3);
/*     */             break;
/*     */           case 2:
/* 200 */             barcodeColumnCount.setValue(rowIndicatorValue + 1);
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 205 */     if ((barcodeColumnCount.getValue()).length == 0 || (barcodeRowCountUpperPart
/* 206 */       .getValue()).length == 0 || (barcodeRowCountLowerPart
/* 207 */       .getValue()).length == 0 || (barcodeECLevel
/* 208 */       .getValue()).length == 0 || barcodeColumnCount
/* 209 */       .getValue()[0] < 1 || barcodeRowCountUpperPart
/* 210 */       .getValue()[0] + barcodeRowCountLowerPart.getValue()[0] < 3 || barcodeRowCountUpperPart
/* 211 */       .getValue()[0] + barcodeRowCountLowerPart.getValue()[0] > 90) {
/* 212 */       return null;
/*     */     }
/*     */     
/* 215 */     BarcodeMetadata barcodeMetadata = new BarcodeMetadata(barcodeColumnCount.getValue()[0], barcodeRowCountUpperPart.getValue()[0], barcodeRowCountLowerPart.getValue()[0], barcodeECLevel.getValue()[0]);
/* 216 */     removeIncorrectCodewords(codewords, barcodeMetadata);
/* 217 */     return barcodeMetadata;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeIncorrectCodewords(Codeword[] codewords, BarcodeMetadata barcodeMetadata) {
/* 223 */     for (int codewordRow = 0; codewordRow < codewords.length; codewordRow++) {
/* 224 */       Codeword codeword = codewords[codewordRow];
/* 225 */       if (codewords[codewordRow] != null) {
/*     */ 
/*     */         
/* 228 */         int rowIndicatorValue = codeword.getValue() % 30;
/* 229 */         int codewordRowNumber = codeword.getRowNumber();
/* 230 */         if (codewordRowNumber > barcodeMetadata.getRowCount()) {
/* 231 */           codewords[codewordRow] = null;
/*     */         } else {
/*     */           
/* 234 */           if (!this.isLeft) {
/* 235 */             codewordRowNumber += 2;
/*     */           }
/* 237 */           switch (codewordRowNumber % 3) {
/*     */             case 0:
/* 239 */               if (rowIndicatorValue * 3 + 1 != barcodeMetadata.getRowCountUpperPart()) {
/* 240 */                 codewords[codewordRow] = null;
/*     */               }
/*     */               break;
/*     */             case 1:
/* 244 */               if (rowIndicatorValue / 3 != barcodeMetadata.getErrorCorrectionLevel() || rowIndicatorValue % 3 != barcodeMetadata
/* 245 */                 .getRowCountLowerPart()) {
/* 246 */                 codewords[codewordRow] = null;
/*     */               }
/*     */               break;
/*     */             case 2:
/* 250 */               if (rowIndicatorValue + 1 != barcodeMetadata.getColumnCount())
/* 251 */                 codewords[codewordRow] = null; 
/*     */               break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   boolean isLeft() {
/* 259 */     return this.isLeft;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 264 */     return "IsLeft: " + this.isLeft + '\n' + super.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\pdf417\decoder\DetectionResultRowIndicatorColumn.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */