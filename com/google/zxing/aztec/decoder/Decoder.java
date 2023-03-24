/*     */ package com.google.zxing.aztec.decoder;
/*     */ 
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.aztec.AztecDetectorResult;
/*     */ import com.google.zxing.common.BitMatrix;
/*     */ import com.google.zxing.common.DecoderResult;
/*     */ import com.google.zxing.common.reedsolomon.GenericGF;
/*     */ import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
/*     */ import com.google.zxing.common.reedsolomon.ReedSolomonException;
/*     */ import java.util.Arrays;
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
/*     */ public final class Decoder
/*     */ {
/*     */   private enum Table
/*     */   {
/*  38 */     UPPER,
/*  39 */     LOWER,
/*  40 */     MIXED,
/*  41 */     DIGIT,
/*  42 */     PUNCT,
/*  43 */     BINARY;
/*     */   }
/*     */   
/*  46 */   private static final String[] UPPER_TABLE = new String[] { "CTRL_PS", " ", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "CTRL_LL", "CTRL_ML", "CTRL_DL", "CTRL_BS" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   private static final String[] LOWER_TABLE = new String[] { "CTRL_PS", " ", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "CTRL_US", "CTRL_ML", "CTRL_DL", "CTRL_BS" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   private static final String[] MIXED_TABLE = new String[] { "CTRL_PS", " ", "\001", "\002", "\003", "\004", "\005", "\006", "\007", "\b", "\t", "\n", "\013", "\f", "\r", "\033", "\034", "\035", "\036", "\037", "@", "\\", "^", "_", "`", "|", "~", "", "CTRL_LL", "CTRL_UL", "CTRL_PL", "CTRL_BS" };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private static final String[] PUNCT_TABLE = new String[] { "", "\r", "\r\n", ". ", ", ", ": ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">", "?", "[", "]", "{", "}", "CTRL_UL" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   private static final String[] DIGIT_TABLE = new String[] { "CTRL_PS", " ", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ",", ".", "CTRL_UL", "CTRL_US" };
/*     */ 
/*     */   
/*     */   private AztecDetectorResult ddata;
/*     */ 
/*     */   
/*     */   public DecoderResult decode(AztecDetectorResult detectorResult) throws FormatException {
/*  74 */     this.ddata = detectorResult;
/*  75 */     BitMatrix matrix = detectorResult.getBits();
/*  76 */     boolean[] rawbits = extractBits(matrix);
/*  77 */     boolean[] correctedBits = correctBits(rawbits);
/*  78 */     String result = getEncodedData(correctedBits);
/*  79 */     return new DecoderResult(null, result, null, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String highLevelDecode(boolean[] correctedBits) {
/*  84 */     return getEncodedData(correctedBits);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getEncodedData(boolean[] correctedBits) {
/*  93 */     int endIndex = correctedBits.length;
/*  94 */     Table latchTable = Table.UPPER;
/*  95 */     Table shiftTable = Table.UPPER;
/*  96 */     StringBuilder result = new StringBuilder(20);
/*  97 */     int index = 0;
/*  98 */     while (index < endIndex) {
/*  99 */       if (shiftTable == Table.BINARY) {
/* 100 */         if (endIndex - index < 5) {
/*     */           break;
/*     */         }
/* 103 */         int length = readCode(correctedBits, index, 5);
/* 104 */         index += 5;
/* 105 */         if (length == 0) {
/* 106 */           if (endIndex - index < 11) {
/*     */             break;
/*     */           }
/* 109 */           length = readCode(correctedBits, index, 11) + 31;
/* 110 */           index += 11;
/*     */         } 
/* 112 */         for (int charCount = 0; charCount < length; charCount++) {
/* 113 */           if (endIndex - index < 8) {
/* 114 */             index = endIndex;
/*     */             break;
/*     */           } 
/* 117 */           int i = readCode(correctedBits, index, 8);
/* 118 */           result.append((char)i);
/* 119 */           index += 8;
/*     */         } 
/*     */         
/* 122 */         shiftTable = latchTable; continue;
/*     */       } 
/* 124 */       int size = (shiftTable == Table.DIGIT) ? 4 : 5;
/* 125 */       if (endIndex - index < size) {
/*     */         break;
/*     */       }
/* 128 */       int code = readCode(correctedBits, index, size);
/* 129 */       index += size;
/* 130 */       String str = getCharacter(shiftTable, code);
/* 131 */       if (str.startsWith("CTRL_")) {
/*     */         
/* 133 */         shiftTable = getTable(str.charAt(5));
/* 134 */         if (str.charAt(6) == 'L')
/* 135 */           latchTable = shiftTable; 
/*     */         continue;
/*     */       } 
/* 138 */       result.append(str);
/*     */       
/* 140 */       shiftTable = latchTable;
/*     */     } 
/*     */ 
/*     */     
/* 144 */     return result.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Table getTable(char t) {
/* 151 */     switch (t) {
/*     */       case 'L':
/* 153 */         return Table.LOWER;
/*     */       case 'P':
/* 155 */         return Table.PUNCT;
/*     */       case 'M':
/* 157 */         return Table.MIXED;
/*     */       case 'D':
/* 159 */         return Table.DIGIT;
/*     */       case 'B':
/* 161 */         return Table.BINARY;
/*     */     } 
/*     */     
/* 164 */     return Table.UPPER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String getCharacter(Table table, int code) {
/* 175 */     switch (table) {
/*     */       case UPPER:
/* 177 */         return UPPER_TABLE[code];
/*     */       case LOWER:
/* 179 */         return LOWER_TABLE[code];
/*     */       case MIXED:
/* 181 */         return MIXED_TABLE[code];
/*     */       case PUNCT:
/* 183 */         return PUNCT_TABLE[code];
/*     */       case DIGIT:
/* 185 */         return DIGIT_TABLE[code];
/*     */     } 
/*     */     
/* 188 */     throw new IllegalStateException("Bad table");
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
/*     */   private boolean[] correctBits(boolean[] rawbits) throws FormatException {
/*     */     GenericGF gf;
/*     */     int codewordSize;
/* 202 */     if (this.ddata.getNbLayers() <= 2) {
/* 203 */       codewordSize = 6;
/* 204 */       gf = GenericGF.AZTEC_DATA_6;
/* 205 */     } else if (this.ddata.getNbLayers() <= 8) {
/* 206 */       codewordSize = 8;
/* 207 */       gf = GenericGF.AZTEC_DATA_8;
/* 208 */     } else if (this.ddata.getNbLayers() <= 22) {
/* 209 */       codewordSize = 10;
/* 210 */       gf = GenericGF.AZTEC_DATA_10;
/*     */     } else {
/* 212 */       codewordSize = 12;
/* 213 */       gf = GenericGF.AZTEC_DATA_12;
/*     */     } 
/*     */     
/* 216 */     int numDataCodewords = this.ddata.getNbDatablocks();
/* 217 */     int numCodewords = rawbits.length / codewordSize;
/* 218 */     if (numCodewords < numDataCodewords) {
/* 219 */       throw FormatException.getFormatInstance();
/*     */     }
/* 221 */     int offset = rawbits.length % codewordSize;
/* 222 */     int numECCodewords = numCodewords - numDataCodewords;
/*     */     
/* 224 */     int[] dataWords = new int[numCodewords];
/* 225 */     for (int i = 0; i < numCodewords; i++, offset += codewordSize) {
/* 226 */       dataWords[i] = readCode(rawbits, offset, codewordSize);
/*     */     }
/*     */     
/*     */     try {
/* 230 */       ReedSolomonDecoder rsDecoder = new ReedSolomonDecoder(gf);
/* 231 */       rsDecoder.decode(dataWords, numECCodewords);
/* 232 */     } catch (ReedSolomonException ex) {
/* 233 */       throw FormatException.getFormatInstance(ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 238 */     int mask = (1 << codewordSize) - 1;
/* 239 */     int stuffedBits = 0;
/* 240 */     for (int j = 0; j < numDataCodewords; j++) {
/* 241 */       int dataWord = dataWords[j];
/* 242 */       if (dataWord == 0 || dataWord == mask)
/* 243 */         throw FormatException.getFormatInstance(); 
/* 244 */       if (dataWord == 1 || dataWord == mask - 1) {
/* 245 */         stuffedBits++;
/*     */       }
/*     */     } 
/*     */     
/* 249 */     boolean[] correctedBits = new boolean[numDataCodewords * codewordSize - stuffedBits];
/* 250 */     int index = 0;
/* 251 */     for (int k = 0; k < numDataCodewords; k++) {
/* 252 */       int dataWord = dataWords[k];
/* 253 */       if (dataWord == 1 || dataWord == mask - 1) {
/*     */         
/* 255 */         Arrays.fill(correctedBits, index, index + codewordSize - 1, (dataWord > 1));
/* 256 */         index += codewordSize - 1;
/*     */       } else {
/* 258 */         for (int bit = codewordSize - 1; bit >= 0; bit--) {
/* 259 */           correctedBits[index++] = ((dataWord & 1 << bit) != 0);
/*     */         }
/*     */       } 
/*     */     } 
/* 263 */     return correctedBits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean[] extractBits(BitMatrix matrix) {
/* 272 */     boolean compact = this.ddata.isCompact();
/* 273 */     int layers = this.ddata.getNbLayers();
/* 274 */     int baseMatrixSize = compact ? (11 + layers * 4) : (14 + layers * 4);
/* 275 */     int[] alignmentMap = new int[baseMatrixSize];
/* 276 */     boolean[] rawbits = new boolean[totalBitsInLayer(layers, compact)];
/*     */     
/* 278 */     if (compact) {
/* 279 */       for (int j = 0; j < alignmentMap.length; j++) {
/* 280 */         alignmentMap[j] = j;
/*     */       }
/*     */     } else {
/* 283 */       int matrixSize = baseMatrixSize + 1 + 2 * (baseMatrixSize / 2 - 1) / 15;
/* 284 */       int origCenter = baseMatrixSize / 2;
/* 285 */       int center = matrixSize / 2;
/* 286 */       for (int j = 0; j < origCenter; j++) {
/* 287 */         int newOffset = j + j / 15;
/* 288 */         alignmentMap[origCenter - j - 1] = center - newOffset - 1;
/* 289 */         alignmentMap[origCenter + j] = center + newOffset + 1;
/*     */       } 
/*     */     } 
/* 292 */     for (int i = 0, rowOffset = 0; i < layers; i++) {
/* 293 */       int rowSize = compact ? ((layers - i) * 4 + 9) : ((layers - i) * 4 + 12);
/*     */       
/* 295 */       int low = i * 2;
/*     */       
/* 297 */       int high = baseMatrixSize - 1 - low;
/*     */       
/* 299 */       for (int j = 0; j < rowSize; j++) {
/* 300 */         int columnOffset = j * 2;
/* 301 */         for (int k = 0; k < 2; k++) {
/*     */           
/* 303 */           rawbits[rowOffset + columnOffset + k] = matrix
/* 304 */             .get(alignmentMap[low + k], alignmentMap[low + j]);
/*     */           
/* 306 */           rawbits[rowOffset + 2 * rowSize + columnOffset + k] = matrix
/* 307 */             .get(alignmentMap[low + j], alignmentMap[high - k]);
/*     */           
/* 309 */           rawbits[rowOffset + 4 * rowSize + columnOffset + k] = matrix
/* 310 */             .get(alignmentMap[high - k], alignmentMap[high - j]);
/*     */           
/* 312 */           rawbits[rowOffset + 6 * rowSize + columnOffset + k] = matrix
/* 313 */             .get(alignmentMap[high - j], alignmentMap[low + k]);
/*     */         } 
/*     */       } 
/* 316 */       rowOffset += rowSize * 8;
/*     */     } 
/* 318 */     return rawbits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int readCode(boolean[] rawbits, int startIndex, int length) {
/* 325 */     int res = 0;
/* 326 */     for (int i = startIndex; i < startIndex + length; i++) {
/* 327 */       res <<= 1;
/* 328 */       if (rawbits[i]) {
/* 329 */         res |= 0x1;
/*     */       }
/*     */     } 
/* 332 */     return res;
/*     */   }
/*     */   
/*     */   private static int totalBitsInLayer(int layers, boolean compact) {
/* 336 */     return ((compact ? 88 : 112) + 16 * layers) * layers;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\aztec\decoder\Decoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */