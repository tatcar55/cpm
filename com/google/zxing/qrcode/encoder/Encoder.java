/*     */ package com.google.zxing.qrcode.encoder;
/*     */ 
/*     */ import com.google.zxing.EncodeHintType;
/*     */ import com.google.zxing.WriterException;
/*     */ import com.google.zxing.common.BitArray;
/*     */ import com.google.zxing.common.CharacterSetECI;
/*     */ import com.google.zxing.common.reedsolomon.GenericGF;
/*     */ import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
/*     */ import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/*     */ import com.google.zxing.qrcode.decoder.Mode;
/*     */ import com.google.zxing.qrcode.decoder.Version;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
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
/*     */ public final class Encoder
/*     */ {
/*  41 */   private static final int[] ALPHANUMERIC_TABLE = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final String DEFAULT_BYTE_MODE_ENCODING = "ISO-8859-1";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int calculateMaskPenalty(ByteMatrix matrix) {
/*  61 */     return MaskUtil.applyMaskPenaltyRule1(matrix) + MaskUtil.applyMaskPenaltyRule2(matrix) + MaskUtil.applyMaskPenaltyRule3(matrix) + MaskUtil.applyMaskPenaltyRule4(matrix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static QRCode encode(String content, ErrorCorrectionLevel ecLevel) throws WriterException {
/*  72 */     return encode(content, ecLevel, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static QRCode encode(String content, ErrorCorrectionLevel ecLevel, Map<EncodeHintType, ?> hints) throws WriterException {
/*  80 */     String encoding = (hints == null) ? null : (String)hints.get(EncodeHintType.CHARACTER_SET);
/*  81 */     if (encoding == null) {
/*  82 */       encoding = "ISO-8859-1";
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  87 */     Mode mode = chooseMode(content, encoding);
/*     */ 
/*     */ 
/*     */     
/*  91 */     BitArray headerBits = new BitArray();
/*     */ 
/*     */     
/*  94 */     if (mode == Mode.BYTE && !"ISO-8859-1".equals(encoding)) {
/*  95 */       CharacterSetECI eci = CharacterSetECI.getCharacterSetECIByName(encoding);
/*  96 */       if (eci != null) {
/*  97 */         appendECI(eci, headerBits);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 102 */     appendModeInfo(mode, headerBits);
/*     */ 
/*     */ 
/*     */     
/* 106 */     BitArray dataBits = new BitArray();
/* 107 */     appendBytes(content, mode, dataBits, encoding);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     int provisionalBitsNeeded = headerBits.getSize() + mode.getCharacterCountBits(Version.getVersionForNumber(1)) + dataBits.getSize();
/* 116 */     Version provisionalVersion = chooseVersion(provisionalBitsNeeded, ecLevel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     int bitsNeeded = headerBits.getSize() + mode.getCharacterCountBits(provisionalVersion) + dataBits.getSize();
/* 123 */     Version version = chooseVersion(bitsNeeded, ecLevel);
/*     */     
/* 125 */     BitArray headerAndDataBits = new BitArray();
/* 126 */     headerAndDataBits.appendBitArray(headerBits);
/*     */     
/* 128 */     int numLetters = (mode == Mode.BYTE) ? dataBits.getSizeInBytes() : content.length();
/* 129 */     appendLengthInfo(numLetters, version, mode, headerAndDataBits);
/*     */     
/* 131 */     headerAndDataBits.appendBitArray(dataBits);
/*     */     
/* 133 */     Version.ECBlocks ecBlocks = version.getECBlocksForLevel(ecLevel);
/* 134 */     int numDataBytes = version.getTotalCodewords() - ecBlocks.getTotalECCodewords();
/*     */ 
/*     */     
/* 137 */     terminateBits(numDataBytes, headerAndDataBits);
/*     */ 
/*     */     
/* 140 */     BitArray finalBits = interleaveWithECBytes(headerAndDataBits, version
/* 141 */         .getTotalCodewords(), numDataBytes, ecBlocks
/*     */         
/* 143 */         .getNumBlocks());
/*     */     
/* 145 */     QRCode qrCode = new QRCode();
/*     */     
/* 147 */     qrCode.setECLevel(ecLevel);
/* 148 */     qrCode.setMode(mode);
/* 149 */     qrCode.setVersion(version);
/*     */ 
/*     */     
/* 152 */     int dimension = version.getDimensionForVersion();
/* 153 */     ByteMatrix matrix = new ByteMatrix(dimension, dimension);
/* 154 */     int maskPattern = chooseMaskPattern(finalBits, ecLevel, version, matrix);
/* 155 */     qrCode.setMaskPattern(maskPattern);
/*     */ 
/*     */     
/* 158 */     MatrixUtil.buildMatrix(finalBits, ecLevel, version, maskPattern, matrix);
/* 159 */     qrCode.setMatrix(matrix);
/*     */     
/* 161 */     return qrCode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int getAlphanumericCode(int code) {
/* 169 */     if (code < ALPHANUMERIC_TABLE.length) {
/* 170 */       return ALPHANUMERIC_TABLE[code];
/*     */     }
/* 172 */     return -1;
/*     */   }
/*     */   
/*     */   public static Mode chooseMode(String content) {
/* 176 */     return chooseMode(content, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Mode chooseMode(String content, String encoding) {
/* 184 */     if ("Shift_JIS".equals(encoding))
/*     */     {
/* 186 */       return isOnlyDoubleByteKanji(content) ? Mode.KANJI : Mode.BYTE;
/*     */     }
/* 188 */     boolean hasNumeric = false;
/* 189 */     boolean hasAlphanumeric = false;
/* 190 */     for (int i = 0; i < content.length(); i++) {
/* 191 */       char c = content.charAt(i);
/* 192 */       if (c >= '0' && c <= '9') {
/* 193 */         hasNumeric = true;
/* 194 */       } else if (getAlphanumericCode(c) != -1) {
/* 195 */         hasAlphanumeric = true;
/*     */       } else {
/* 197 */         return Mode.BYTE;
/*     */       } 
/*     */     } 
/* 200 */     if (hasAlphanumeric) {
/* 201 */       return Mode.ALPHANUMERIC;
/*     */     }
/* 203 */     if (hasNumeric) {
/* 204 */       return Mode.NUMERIC;
/*     */     }
/* 206 */     return Mode.BYTE;
/*     */   }
/*     */   
/*     */   private static boolean isOnlyDoubleByteKanji(String content) {
/*     */     byte[] bytes;
/*     */     try {
/* 212 */       bytes = content.getBytes("Shift_JIS");
/* 213 */     } catch (UnsupportedEncodingException ignored) {
/* 214 */       return false;
/*     */     } 
/* 216 */     int length = bytes.length;
/* 217 */     if (length % 2 != 0) {
/* 218 */       return false;
/*     */     }
/* 220 */     for (int i = 0; i < length; i += 2) {
/* 221 */       int byte1 = bytes[i] & 0xFF;
/* 222 */       if ((byte1 < 129 || byte1 > 159) && (byte1 < 224 || byte1 > 235)) {
/* 223 */         return false;
/*     */       }
/*     */     } 
/* 226 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int chooseMaskPattern(BitArray bits, ErrorCorrectionLevel ecLevel, Version version, ByteMatrix matrix) throws WriterException {
/* 234 */     int minPenalty = Integer.MAX_VALUE;
/* 235 */     int bestMaskPattern = -1;
/*     */     
/* 237 */     for (int maskPattern = 0; maskPattern < 8; maskPattern++) {
/* 238 */       MatrixUtil.buildMatrix(bits, ecLevel, version, maskPattern, matrix);
/* 239 */       int penalty = calculateMaskPenalty(matrix);
/* 240 */       if (penalty < minPenalty) {
/* 241 */         minPenalty = penalty;
/* 242 */         bestMaskPattern = maskPattern;
/*     */       } 
/*     */     } 
/* 245 */     return bestMaskPattern;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Version chooseVersion(int numInputBits, ErrorCorrectionLevel ecLevel) throws WriterException {
/* 250 */     for (int versionNum = 1; versionNum <= 40; versionNum++) {
/* 251 */       Version version = Version.getVersionForNumber(versionNum);
/*     */       
/* 253 */       int numBytes = version.getTotalCodewords();
/*     */       
/* 255 */       Version.ECBlocks ecBlocks = version.getECBlocksForLevel(ecLevel);
/* 256 */       int numEcBytes = ecBlocks.getTotalECCodewords();
/*     */       
/* 258 */       int numDataBytes = numBytes - numEcBytes;
/* 259 */       int totalInputBytes = (numInputBits + 7) / 8;
/* 260 */       if (numDataBytes >= totalInputBytes) {
/* 261 */         return version;
/*     */       }
/*     */     } 
/* 264 */     throw new WriterException("Data too big");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void terminateBits(int numDataBytes, BitArray bits) throws WriterException {
/* 271 */     int capacity = numDataBytes * 8;
/* 272 */     if (bits.getSize() > capacity) {
/* 273 */       throw new WriterException("data bits cannot fit in the QR Code" + bits.getSize() + " > " + capacity);
/*     */     }
/*     */     
/* 276 */     for (int i = 0; i < 4 && bits.getSize() < capacity; i++) {
/* 277 */       bits.appendBit(false);
/*     */     }
/*     */ 
/*     */     
/* 281 */     int numBitsInLastByte = bits.getSize() & 0x7;
/* 282 */     if (numBitsInLastByte > 0) {
/* 283 */       for (int k = numBitsInLastByte; k < 8; k++) {
/* 284 */         bits.appendBit(false);
/*     */       }
/*     */     }
/*     */     
/* 288 */     int numPaddingBytes = numDataBytes - bits.getSizeInBytes();
/* 289 */     for (int j = 0; j < numPaddingBytes; j++) {
/* 290 */       bits.appendBits(((j & 0x1) == 0) ? 236 : 17, 8);
/*     */     }
/* 292 */     if (bits.getSize() != capacity) {
/* 293 */       throw new WriterException("Bits size does not equal capacity");
/*     */     }
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
/*     */ 
/*     */   
/*     */   static void getNumDataBytesAndNumECBytesForBlockID(int numTotalBytes, int numDataBytes, int numRSBlocks, int blockID, int[] numDataBytesInBlock, int[] numECBytesInBlock) throws WriterException {
/* 308 */     if (blockID >= numRSBlocks) {
/* 309 */       throw new WriterException("Block ID too large");
/*     */     }
/*     */     
/* 312 */     int numRsBlocksInGroup2 = numTotalBytes % numRSBlocks;
/*     */     
/* 314 */     int numRsBlocksInGroup1 = numRSBlocks - numRsBlocksInGroup2;
/*     */     
/* 316 */     int numTotalBytesInGroup1 = numTotalBytes / numRSBlocks;
/*     */     
/* 318 */     int numTotalBytesInGroup2 = numTotalBytesInGroup1 + 1;
/*     */     
/* 320 */     int numDataBytesInGroup1 = numDataBytes / numRSBlocks;
/*     */     
/* 322 */     int numDataBytesInGroup2 = numDataBytesInGroup1 + 1;
/*     */     
/* 324 */     int numEcBytesInGroup1 = numTotalBytesInGroup1 - numDataBytesInGroup1;
/*     */     
/* 326 */     int numEcBytesInGroup2 = numTotalBytesInGroup2 - numDataBytesInGroup2;
/*     */ 
/*     */     
/* 329 */     if (numEcBytesInGroup1 != numEcBytesInGroup2) {
/* 330 */       throw new WriterException("EC bytes mismatch");
/*     */     }
/*     */     
/* 333 */     if (numRSBlocks != numRsBlocksInGroup1 + numRsBlocksInGroup2) {
/* 334 */       throw new WriterException("RS blocks mismatch");
/*     */     }
/*     */     
/* 337 */     if (numTotalBytes != (numDataBytesInGroup1 + numEcBytesInGroup1) * numRsBlocksInGroup1 + (numDataBytesInGroup2 + numEcBytesInGroup2) * numRsBlocksInGroup2)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 342 */       throw new WriterException("Total bytes mismatch");
/*     */     }
/*     */     
/* 345 */     if (blockID < numRsBlocksInGroup1) {
/* 346 */       numDataBytesInBlock[0] = numDataBytesInGroup1;
/* 347 */       numECBytesInBlock[0] = numEcBytesInGroup1;
/*     */     } else {
/* 349 */       numDataBytesInBlock[0] = numDataBytesInGroup2;
/* 350 */       numECBytesInBlock[0] = numEcBytesInGroup2;
/*     */     } 
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
/*     */   
/*     */   static BitArray interleaveWithECBytes(BitArray bits, int numTotalBytes, int numDataBytes, int numRSBlocks) throws WriterException {
/* 364 */     if (bits.getSizeInBytes() != numDataBytes) {
/* 365 */       throw new WriterException("Number of bits and data bytes does not match");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 370 */     int dataBytesOffset = 0;
/* 371 */     int maxNumDataBytes = 0;
/* 372 */     int maxNumEcBytes = 0;
/*     */ 
/*     */     
/* 375 */     Collection<BlockPair> blocks = new ArrayList<>(numRSBlocks);
/*     */     
/* 377 */     for (int i = 0; i < numRSBlocks; i++) {
/* 378 */       int[] numDataBytesInBlock = new int[1];
/* 379 */       int[] numEcBytesInBlock = new int[1];
/* 380 */       getNumDataBytesAndNumECBytesForBlockID(numTotalBytes, numDataBytes, numRSBlocks, i, numDataBytesInBlock, numEcBytesInBlock);
/*     */ 
/*     */ 
/*     */       
/* 384 */       int size = numDataBytesInBlock[0];
/* 385 */       byte[] dataBytes = new byte[size];
/* 386 */       bits.toBytes(8 * dataBytesOffset, dataBytes, 0, size);
/* 387 */       byte[] ecBytes = generateECBytes(dataBytes, numEcBytesInBlock[0]);
/* 388 */       blocks.add(new BlockPair(dataBytes, ecBytes));
/*     */       
/* 390 */       maxNumDataBytes = Math.max(maxNumDataBytes, size);
/* 391 */       maxNumEcBytes = Math.max(maxNumEcBytes, ecBytes.length);
/* 392 */       dataBytesOffset += numDataBytesInBlock[0];
/*     */     } 
/* 394 */     if (numDataBytes != dataBytesOffset) {
/* 395 */       throw new WriterException("Data bytes does not match offset");
/*     */     }
/*     */     
/* 398 */     BitArray result = new BitArray();
/*     */     
/*     */     int j;
/* 401 */     for (j = 0; j < maxNumDataBytes; j++) {
/* 402 */       for (BlockPair block : blocks) {
/* 403 */         byte[] dataBytes = block.getDataBytes();
/* 404 */         if (j < dataBytes.length) {
/* 405 */           result.appendBits(dataBytes[j], 8);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 410 */     for (j = 0; j < maxNumEcBytes; j++) {
/* 411 */       for (BlockPair block : blocks) {
/* 412 */         byte[] ecBytes = block.getErrorCorrectionBytes();
/* 413 */         if (j < ecBytes.length) {
/* 414 */           result.appendBits(ecBytes[j], 8);
/*     */         }
/*     */       } 
/*     */     } 
/* 418 */     if (numTotalBytes != result.getSizeInBytes()) {
/* 419 */       throw new WriterException("Interleaving error: " + numTotalBytes + " and " + result
/* 420 */           .getSizeInBytes() + " differ.");
/*     */     }
/*     */     
/* 423 */     return result;
/*     */   }
/*     */   
/*     */   static byte[] generateECBytes(byte[] dataBytes, int numEcBytesInBlock) {
/* 427 */     int numDataBytes = dataBytes.length;
/* 428 */     int[] toEncode = new int[numDataBytes + numEcBytesInBlock];
/* 429 */     for (int i = 0; i < numDataBytes; i++) {
/* 430 */       toEncode[i] = dataBytes[i] & 0xFF;
/*     */     }
/* 432 */     (new ReedSolomonEncoder(GenericGF.QR_CODE_FIELD_256)).encode(toEncode, numEcBytesInBlock);
/*     */     
/* 434 */     byte[] ecBytes = new byte[numEcBytesInBlock];
/* 435 */     for (int j = 0; j < numEcBytesInBlock; j++) {
/* 436 */       ecBytes[j] = (byte)toEncode[numDataBytes + j];
/*     */     }
/* 438 */     return ecBytes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void appendModeInfo(Mode mode, BitArray bits) {
/* 445 */     bits.appendBits(mode.getBits(), 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void appendLengthInfo(int numLetters, Version version, Mode mode, BitArray bits) throws WriterException {
/* 453 */     int numBits = mode.getCharacterCountBits(version);
/* 454 */     if (numLetters >= 1 << numBits) {
/* 455 */       throw new WriterException(numLetters + " is bigger than " + ((1 << numBits) - 1));
/*     */     }
/* 457 */     bits.appendBits(numLetters, numBits);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void appendBytes(String content, Mode mode, BitArray bits, String encoding) throws WriterException {
/* 467 */     switch (mode) {
/*     */       case NUMERIC:
/* 469 */         appendNumericBytes(content, bits);
/*     */         return;
/*     */       case ALPHANUMERIC:
/* 472 */         appendAlphanumericBytes(content, bits);
/*     */         return;
/*     */       case BYTE:
/* 475 */         append8BitBytes(content, bits, encoding);
/*     */         return;
/*     */       case KANJI:
/* 478 */         appendKanjiBytes(content, bits);
/*     */         return;
/*     */     } 
/* 481 */     throw new WriterException("Invalid mode: " + mode);
/*     */   }
/*     */ 
/*     */   
/*     */   static void appendNumericBytes(CharSequence content, BitArray bits) {
/* 486 */     int length = content.length();
/* 487 */     int i = 0;
/* 488 */     while (i < length) {
/* 489 */       int num1 = content.charAt(i) - 48;
/* 490 */       if (i + 2 < length) {
/*     */         
/* 492 */         int num2 = content.charAt(i + 1) - 48;
/* 493 */         int num3 = content.charAt(i + 2) - 48;
/* 494 */         bits.appendBits(num1 * 100 + num2 * 10 + num3, 10);
/* 495 */         i += 3; continue;
/* 496 */       }  if (i + 1 < length) {
/*     */         
/* 498 */         int num2 = content.charAt(i + 1) - 48;
/* 499 */         bits.appendBits(num1 * 10 + num2, 7);
/* 500 */         i += 2;
/*     */         continue;
/*     */       } 
/* 503 */       bits.appendBits(num1, 4);
/* 504 */       i++;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void appendAlphanumericBytes(CharSequence content, BitArray bits) throws WriterException {
/* 510 */     int length = content.length();
/* 511 */     int i = 0;
/* 512 */     while (i < length) {
/* 513 */       int code1 = getAlphanumericCode(content.charAt(i));
/* 514 */       if (code1 == -1) {
/* 515 */         throw new WriterException();
/*     */       }
/* 517 */       if (i + 1 < length) {
/* 518 */         int code2 = getAlphanumericCode(content.charAt(i + 1));
/* 519 */         if (code2 == -1) {
/* 520 */           throw new WriterException();
/*     */         }
/*     */         
/* 523 */         bits.appendBits(code1 * 45 + code2, 11);
/* 524 */         i += 2;
/*     */         continue;
/*     */       } 
/* 527 */       bits.appendBits(code1, 6);
/* 528 */       i++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void append8BitBytes(String content, BitArray bits, String encoding) throws WriterException {
/*     */     byte[] bytes;
/*     */     try {
/* 537 */       bytes = content.getBytes(encoding);
/* 538 */     } catch (UnsupportedEncodingException uee) {
/* 539 */       throw new WriterException(uee);
/*     */     } 
/* 541 */     for (byte b : bytes) {
/* 542 */       bits.appendBits(b, 8);
/*     */     }
/*     */   }
/*     */   
/*     */   static void appendKanjiBytes(String content, BitArray bits) throws WriterException {
/*     */     byte[] bytes;
/*     */     try {
/* 549 */       bytes = content.getBytes("Shift_JIS");
/* 550 */     } catch (UnsupportedEncodingException uee) {
/* 551 */       throw new WriterException(uee);
/*     */     } 
/* 553 */     int length = bytes.length;
/* 554 */     for (int i = 0; i < length; i += 2) {
/* 555 */       int byte1 = bytes[i] & 0xFF;
/* 556 */       int byte2 = bytes[i + 1] & 0xFF;
/* 557 */       int code = byte1 << 8 | byte2;
/* 558 */       int subtracted = -1;
/* 559 */       if (code >= 33088 && code <= 40956) {
/* 560 */         subtracted = code - 33088;
/* 561 */       } else if (code >= 57408 && code <= 60351) {
/* 562 */         subtracted = code - 49472;
/*     */       } 
/* 564 */       if (subtracted == -1) {
/* 565 */         throw new WriterException("Invalid byte sequence");
/*     */       }
/* 567 */       int encoded = (subtracted >> 8) * 192 + (subtracted & 0xFF);
/* 568 */       bits.appendBits(encoded, 13);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void appendECI(CharacterSetECI eci, BitArray bits) {
/* 573 */     bits.appendBits(Mode.ECI.getBits(), 4);
/*     */     
/* 575 */     bits.appendBits(eci.getValue(), 8);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\qrcode\encoder\Encoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */