/*     */ package com.google.zxing.pdf417.decoder;
/*     */ 
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.common.CharacterSetECI;
/*     */ import com.google.zxing.common.DecoderResult;
/*     */ import com.google.zxing.pdf417.PDF417ResultMetadata;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ final class DecodedBitStreamParser
/*     */ {
/*     */   private static final int TEXT_COMPACTION_MODE_LATCH = 900;
/*     */   private static final int BYTE_COMPACTION_MODE_LATCH = 901;
/*     */   private static final int NUMERIC_COMPACTION_MODE_LATCH = 902;
/*     */   private static final int BYTE_COMPACTION_MODE_LATCH_6 = 924;
/*     */   private static final int ECI_USER_DEFINED = 925;
/*     */   private static final int ECI_GENERAL_PURPOSE = 926;
/*     */   private static final int ECI_CHARSET = 927;
/*     */   private static final int BEGIN_MACRO_PDF417_CONTROL_BLOCK = 928;
/*     */   private static final int BEGIN_MACRO_PDF417_OPTIONAL_FIELD = 923;
/*     */   private static final int MACRO_PDF417_TERMINATOR = 922;
/*     */   private static final int MODE_SHIFT_TO_BYTE_COMPACTION_MODE = 913;
/*     */   private static final int MAX_NUMERIC_CODEWORDS = 15;
/*     */   private static final int PL = 25;
/*     */   private static final int LL = 27;
/*     */   private static final int AS = 27;
/*     */   private static final int ML = 28;
/*     */   private static final int AL = 28;
/*     */   private static final int PS = 29;
/*     */   private static final int PAL = 29;
/*     */   
/*     */   private enum Mode
/*     */   {
/*  38 */     ALPHA,
/*  39 */     LOWER,
/*  40 */     MIXED,
/*  41 */     PUNCT,
/*  42 */     ALPHA_SHIFT,
/*  43 */     PUNCT_SHIFT;
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
/*  67 */   private static final char[] PUNCT_CHARS = new char[] { ';', '<', '>', '@', '[', '\\', ']', '_', '`', '~', '!', '\r', '\t', ',', ':', '\n', '-', '.', '$', '/', '"', '|', '*', '(', ')', '?', '{', '}', '\'' };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  72 */   private static final char[] MIXED_CHARS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '&', '\r', '\t', ',', ':', '#', '-', '.', '$', '/', '+', '%', '*', '=', '^' };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   private static final Charset DEFAULT_ENCODING = Charset.forName("ISO-8859-1");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   private static final BigInteger[] EXP900 = new BigInteger[16]; static {
/*  86 */     EXP900[0] = BigInteger.ONE;
/*  87 */     BigInteger nineHundred = BigInteger.valueOf(900L);
/*  88 */     EXP900[1] = nineHundred;
/*  89 */     for (int i = 2; i < EXP900.length; i++) {
/*  90 */       EXP900[i] = EXP900[i - 1].multiply(nineHundred);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int NUMBER_OF_SEQUENCE_CODEWORDS = 2;
/*     */ 
/*     */   
/*     */   static DecoderResult decode(int[] codewords, String ecLevel) throws FormatException {
/* 100 */     StringBuilder result = new StringBuilder(codewords.length * 2);
/* 101 */     Charset encoding = DEFAULT_ENCODING;
/*     */     
/* 103 */     int codeIndex = 1;
/* 104 */     int code = codewords[codeIndex++];
/* 105 */     PDF417ResultMetadata resultMetadata = new PDF417ResultMetadata();
/* 106 */     while (codeIndex < codewords[0]) {
/* 107 */       CharacterSetECI charsetECI; switch (code) {
/*     */         case 900:
/* 109 */           codeIndex = textCompaction(codewords, codeIndex, result);
/*     */           break;
/*     */         case 901:
/*     */         case 924:
/* 113 */           codeIndex = byteCompaction(code, codewords, encoding, codeIndex, result);
/*     */           break;
/*     */         case 913:
/* 116 */           result.append((char)codewords[codeIndex++]);
/*     */           break;
/*     */         case 902:
/* 119 */           codeIndex = numericCompaction(codewords, codeIndex, result);
/*     */           break;
/*     */         
/*     */         case 927:
/* 123 */           charsetECI = CharacterSetECI.getCharacterSetECIByValue(codewords[codeIndex++]);
/* 124 */           encoding = Charset.forName(charsetECI.name());
/*     */           break;
/*     */         
/*     */         case 926:
/* 128 */           codeIndex += 2;
/*     */           break;
/*     */         
/*     */         case 925:
/* 132 */           codeIndex++;
/*     */           break;
/*     */         case 928:
/* 135 */           codeIndex = decodeMacroBlock(codewords, codeIndex, resultMetadata);
/*     */           break;
/*     */         
/*     */         case 922:
/*     */         case 923:
/* 140 */           throw FormatException.getFormatInstance();
/*     */ 
/*     */ 
/*     */         
/*     */         default:
/* 145 */           codeIndex--;
/* 146 */           codeIndex = textCompaction(codewords, codeIndex, result);
/*     */           break;
/*     */       } 
/* 149 */       if (codeIndex < codewords.length) {
/* 150 */         code = codewords[codeIndex++]; continue;
/*     */       } 
/* 152 */       throw FormatException.getFormatInstance();
/*     */     } 
/*     */     
/* 155 */     if (result.length() == 0) {
/* 156 */       throw FormatException.getFormatInstance();
/*     */     }
/* 158 */     DecoderResult decoderResult = new DecoderResult(null, result.toString(), null, ecLevel);
/* 159 */     decoderResult.setOther(resultMetadata);
/* 160 */     return decoderResult;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int decodeMacroBlock(int[] codewords, int codeIndex, PDF417ResultMetadata resultMetadata) throws FormatException {
/* 165 */     if (codeIndex + 2 > codewords[0])
/*     */     {
/* 167 */       throw FormatException.getFormatInstance();
/*     */     }
/* 169 */     int[] segmentIndexArray = new int[2];
/* 170 */     for (int i = 0; i < 2; i++, codeIndex++) {
/* 171 */       segmentIndexArray[i] = codewords[codeIndex];
/*     */     }
/* 173 */     resultMetadata.setSegmentIndex(Integer.parseInt(decodeBase900toBase10(segmentIndexArray, 2)));
/*     */ 
/*     */     
/* 176 */     StringBuilder fileId = new StringBuilder();
/* 177 */     codeIndex = textCompaction(codewords, codeIndex, fileId);
/* 178 */     resultMetadata.setFileId(fileId.toString());
/*     */     
/* 180 */     if (codewords[codeIndex] == 923) {
/* 181 */       codeIndex++;
/* 182 */       int[] additionalOptionCodeWords = new int[codewords[0] - codeIndex];
/* 183 */       int additionalOptionCodeWordsIndex = 0;
/*     */       
/* 185 */       boolean end = false;
/* 186 */       while (codeIndex < codewords[0] && !end) {
/* 187 */         int code = codewords[codeIndex++];
/* 188 */         if (code < 900) {
/* 189 */           additionalOptionCodeWords[additionalOptionCodeWordsIndex++] = code; continue;
/*     */         } 
/* 191 */         switch (code) {
/*     */           case 922:
/* 193 */             resultMetadata.setLastSegment(true);
/* 194 */             codeIndex++;
/* 195 */             end = true;
/*     */             continue;
/*     */         } 
/* 198 */         throw FormatException.getFormatInstance();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 203 */       resultMetadata.setOptionalData(Arrays.copyOf(additionalOptionCodeWords, additionalOptionCodeWordsIndex));
/* 204 */     } else if (codewords[codeIndex] == 922) {
/* 205 */       resultMetadata.setLastSegment(true);
/* 206 */       codeIndex++;
/*     */     } 
/*     */     
/* 209 */     return codeIndex;
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
/*     */   
/*     */   private static int textCompaction(int[] codewords, int codeIndex, StringBuilder result) {
/* 224 */     int[] textCompactionData = new int[(codewords[0] - codeIndex) * 2];
/*     */     
/* 226 */     int[] byteCompactionData = new int[(codewords[0] - codeIndex) * 2];
/*     */     
/* 228 */     int index = 0;
/* 229 */     boolean end = false;
/* 230 */     while (codeIndex < codewords[0] && !end) {
/* 231 */       int code = codewords[codeIndex++];
/* 232 */       if (code < 900) {
/* 233 */         textCompactionData[index] = code / 30;
/* 234 */         textCompactionData[index + 1] = code % 30;
/* 235 */         index += 2; continue;
/*     */       } 
/* 237 */       switch (code) {
/*     */         
/*     */         case 900:
/* 240 */           textCompactionData[index++] = 900;
/*     */         
/*     */         case 901:
/*     */         case 902:
/*     */         case 922:
/*     */         case 923:
/*     */         case 924:
/*     */         case 928:
/* 248 */           codeIndex--;
/* 249 */           end = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 913:
/* 258 */           textCompactionData[index] = 913;
/* 259 */           code = codewords[codeIndex++];
/* 260 */           byteCompactionData[index] = code;
/* 261 */           index++;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 266 */     decodeTextCompaction(textCompactionData, byteCompactionData, index, result);
/* 267 */     return codeIndex;
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
/*     */   private static void decodeTextCompaction(int[] textCompactionData, int[] byteCompactionData, int length, StringBuilder result) {
/* 294 */     Mode subMode = Mode.ALPHA;
/* 295 */     Mode priorToShiftMode = Mode.ALPHA;
/* 296 */     int i = 0;
/* 297 */     while (i < length) {
/* 298 */       int subModeCh = textCompactionData[i];
/* 299 */       char ch = Character.MIN_VALUE;
/* 300 */       switch (subMode) {
/*     */         
/*     */         case ALPHA:
/* 303 */           if (subModeCh < 26) {
/*     */             
/* 305 */             ch = (char)(65 + subModeCh); break;
/*     */           } 
/* 307 */           if (subModeCh == 26) {
/* 308 */             ch = ' '; break;
/* 309 */           }  if (subModeCh == 27) {
/* 310 */             subMode = Mode.LOWER; break;
/* 311 */           }  if (subModeCh == 28) {
/* 312 */             subMode = Mode.MIXED; break;
/* 313 */           }  if (subModeCh == 29) {
/*     */             
/* 315 */             priorToShiftMode = subMode;
/* 316 */             subMode = Mode.PUNCT_SHIFT; break;
/* 317 */           }  if (subModeCh == 913) {
/* 318 */             result.append((char)byteCompactionData[i]); break;
/* 319 */           }  if (subModeCh == 900) {
/* 320 */             subMode = Mode.ALPHA;
/*     */           }
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case LOWER:
/* 327 */           if (subModeCh < 26) {
/* 328 */             ch = (char)(97 + subModeCh); break;
/*     */           } 
/* 330 */           if (subModeCh == 26) {
/* 331 */             ch = ' '; break;
/* 332 */           }  if (subModeCh == 27) {
/*     */             
/* 334 */             priorToShiftMode = subMode;
/* 335 */             subMode = Mode.ALPHA_SHIFT; break;
/* 336 */           }  if (subModeCh == 28) {
/* 337 */             subMode = Mode.MIXED; break;
/* 338 */           }  if (subModeCh == 29) {
/*     */             
/* 340 */             priorToShiftMode = subMode;
/* 341 */             subMode = Mode.PUNCT_SHIFT; break;
/* 342 */           }  if (subModeCh == 913) {
/*     */             
/* 344 */             result.append((char)byteCompactionData[i]); break;
/* 345 */           }  if (subModeCh == 900) {
/* 346 */             subMode = Mode.ALPHA;
/*     */           }
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case MIXED:
/* 353 */           if (subModeCh < 25) {
/* 354 */             ch = MIXED_CHARS[subModeCh]; break;
/*     */           } 
/* 356 */           if (subModeCh == 25) {
/* 357 */             subMode = Mode.PUNCT; break;
/* 358 */           }  if (subModeCh == 26) {
/* 359 */             ch = ' '; break;
/* 360 */           }  if (subModeCh == 27) {
/* 361 */             subMode = Mode.LOWER; break;
/* 362 */           }  if (subModeCh == 28) {
/* 363 */             subMode = Mode.ALPHA; break;
/* 364 */           }  if (subModeCh == 29) {
/*     */             
/* 366 */             priorToShiftMode = subMode;
/* 367 */             subMode = Mode.PUNCT_SHIFT; break;
/* 368 */           }  if (subModeCh == 913) {
/* 369 */             result.append((char)byteCompactionData[i]); break;
/* 370 */           }  if (subModeCh == 900) {
/* 371 */             subMode = Mode.ALPHA;
/*     */           }
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case PUNCT:
/* 378 */           if (subModeCh < 29) {
/* 379 */             ch = PUNCT_CHARS[subModeCh]; break;
/*     */           } 
/* 381 */           if (subModeCh == 29) {
/* 382 */             subMode = Mode.ALPHA; break;
/* 383 */           }  if (subModeCh == 913) {
/* 384 */             result.append((char)byteCompactionData[i]); break;
/* 385 */           }  if (subModeCh == 900) {
/* 386 */             subMode = Mode.ALPHA;
/*     */           }
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case ALPHA_SHIFT:
/* 393 */           subMode = priorToShiftMode;
/* 394 */           if (subModeCh < 26) {
/* 395 */             ch = (char)(65 + subModeCh); break;
/*     */           } 
/* 397 */           if (subModeCh == 26) {
/* 398 */             ch = ' '; break;
/* 399 */           }  if (subModeCh == 900) {
/* 400 */             subMode = Mode.ALPHA;
/*     */           }
/*     */           break;
/*     */ 
/*     */ 
/*     */         
/*     */         case PUNCT_SHIFT:
/* 407 */           subMode = priorToShiftMode;
/* 408 */           if (subModeCh < 29) {
/* 409 */             ch = PUNCT_CHARS[subModeCh]; break;
/*     */           } 
/* 411 */           if (subModeCh == 29) {
/* 412 */             subMode = Mode.ALPHA; break;
/* 413 */           }  if (subModeCh == 913) {
/*     */ 
/*     */             
/* 416 */             result.append((char)byteCompactionData[i]); break;
/* 417 */           }  if (subModeCh == 900) {
/* 418 */             subMode = Mode.ALPHA;
/*     */           }
/*     */           break;
/*     */       } 
/*     */       
/* 423 */       if (ch != '\000')
/*     */       {
/* 425 */         result.append(ch);
/*     */       }
/* 427 */       i++;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int byteCompaction(int mode, int[] codewords, Charset encoding, int codeIndex, StringBuilder result) {
/* 448 */     ByteArrayOutputStream decodedBytes = new ByteArrayOutputStream();
/* 449 */     if (mode == 901) {
/*     */ 
/*     */       
/* 452 */       int count = 0;
/* 453 */       long value = 0L;
/* 454 */       int[] byteCompactedCodewords = new int[6];
/* 455 */       boolean end = false;
/* 456 */       int nextCode = codewords[codeIndex++];
/* 457 */       while (codeIndex < codewords[0] && !end) {
/* 458 */         byteCompactedCodewords[count++] = nextCode;
/*     */         
/* 460 */         value = 900L * value + nextCode;
/* 461 */         nextCode = codewords[codeIndex++];
/*     */         
/* 463 */         if (nextCode == 900 || nextCode == 901 || nextCode == 902 || nextCode == 924 || nextCode == 928 || nextCode == 923 || nextCode == 922) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 470 */           codeIndex--;
/* 471 */           end = true; continue;
/*     */         } 
/* 473 */         if (count % 5 == 0 && count > 0) {
/*     */ 
/*     */           
/* 476 */           for (int j = 0; j < 6; j++) {
/* 477 */             decodedBytes.write((byte)(int)(value >> 8 * (5 - j)));
/*     */           }
/* 479 */           value = 0L;
/* 480 */           count = 0;
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 486 */       if (codeIndex == codewords[0] && nextCode < 900) {
/* 487 */         byteCompactedCodewords[count++] = nextCode;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 493 */       for (int i = 0; i < count; i++) {
/* 494 */         decodedBytes.write((byte)byteCompactedCodewords[i]);
/*     */       }
/*     */     }
/* 497 */     else if (mode == 924) {
/*     */ 
/*     */       
/* 500 */       int count = 0;
/* 501 */       long value = 0L;
/* 502 */       boolean end = false;
/* 503 */       while (codeIndex < codewords[0] && !end) {
/* 504 */         int code = codewords[codeIndex++];
/* 505 */         if (code < 900) {
/* 506 */           count++;
/*     */           
/* 508 */           value = 900L * value + code;
/*     */         }
/* 510 */         else if (code == 900 || code == 901 || code == 902 || code == 924 || code == 928 || code == 923 || code == 922) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 517 */           codeIndex--;
/* 518 */           end = true;
/*     */         } 
/*     */         
/* 521 */         if (count % 5 == 0 && count > 0) {
/*     */ 
/*     */           
/* 524 */           for (int j = 0; j < 6; j++) {
/* 525 */             decodedBytes.write((byte)(int)(value >> 8 * (5 - j)));
/*     */           }
/* 527 */           value = 0L;
/* 528 */           count = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/* 532 */     result.append(new String(decodedBytes.toByteArray(), encoding));
/* 533 */     return codeIndex;
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
/*     */   private static int numericCompaction(int[] codewords, int codeIndex, StringBuilder result) throws FormatException {
/* 545 */     int count = 0;
/* 546 */     boolean end = false;
/*     */     
/* 548 */     int[] numericCodewords = new int[15];
/*     */     
/* 550 */     while (codeIndex < codewords[0] && !end) {
/* 551 */       int code = codewords[codeIndex++];
/* 552 */       if (codeIndex == codewords[0]) {
/* 553 */         end = true;
/*     */       }
/* 555 */       if (code < 900) {
/* 556 */         numericCodewords[count] = code;
/* 557 */         count++;
/*     */       }
/* 559 */       else if (code == 900 || code == 901 || code == 924 || code == 928 || code == 923 || code == 922) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 565 */         codeIndex--;
/* 566 */         end = true;
/*     */       } 
/*     */       
/* 569 */       if (count % 15 == 0 || code == 902 || end)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 576 */         if (count > 0) {
/* 577 */           String s = decodeBase900toBase10(numericCodewords, count);
/* 578 */           result.append(s);
/* 579 */           count = 0;
/*     */         } 
/*     */       }
/*     */     } 
/* 583 */     return codeIndex;
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
/*     */   private static String decodeBase900toBase10(int[] codewords, int count) throws FormatException {
/* 630 */     BigInteger result = BigInteger.ZERO;
/* 631 */     for (int i = 0; i < count; i++) {
/* 632 */       result = result.add(EXP900[count - i - 1].multiply(BigInteger.valueOf(codewords[i])));
/*     */     }
/* 634 */     String resultString = result.toString();
/* 635 */     if (resultString.charAt(0) != '1') {
/* 636 */       throw FormatException.getFormatInstance();
/*     */     }
/* 638 */     return resultString.substring(1);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\pdf417\decoder\DecodedBitStreamParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */