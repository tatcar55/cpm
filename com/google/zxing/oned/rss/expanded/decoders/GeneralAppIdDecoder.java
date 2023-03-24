/*     */ package com.google.zxing.oned.rss.expanded.decoders;
/*     */ 
/*     */ import com.google.zxing.FormatException;
/*     */ import com.google.zxing.NotFoundException;
/*     */ import com.google.zxing.common.BitArray;
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
/*     */ final class GeneralAppIdDecoder
/*     */ {
/*     */   private final BitArray information;
/*  40 */   private final CurrentParsingState current = new CurrentParsingState();
/*  41 */   private final StringBuilder buffer = new StringBuilder();
/*     */   
/*     */   GeneralAppIdDecoder(BitArray information) {
/*  44 */     this.information = information;
/*     */   }
/*     */   
/*     */   String decodeAllCodes(StringBuilder buff, int initialPosition) throws NotFoundException, FormatException {
/*  48 */     int currentPosition = initialPosition;
/*  49 */     String remaining = null;
/*     */     while (true) {
/*  51 */       DecodedInformation info = decodeGeneralPurposeField(currentPosition, remaining);
/*  52 */       String parsedFields = FieldParser.parseFieldsInGeneralPurpose(info.getNewString());
/*  53 */       if (parsedFields != null) {
/*  54 */         buff.append(parsedFields);
/*     */       }
/*  56 */       if (info.isRemaining()) {
/*  57 */         remaining = String.valueOf(info.getRemainingValue());
/*     */       } else {
/*  59 */         remaining = null;
/*     */       } 
/*     */       
/*  62 */       if (currentPosition == info.getNewPosition()) {
/*     */         break;
/*     */       }
/*  65 */       currentPosition = info.getNewPosition();
/*     */     } 
/*     */     
/*  68 */     return buff.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isStillNumeric(int pos) {
/*  74 */     if (pos + 7 > this.information.getSize()) {
/*  75 */       return (pos + 4 <= this.information.getSize());
/*     */     }
/*     */     
/*  78 */     for (int i = pos; i < pos + 3; i++) {
/*  79 */       if (this.information.get(i)) {
/*  80 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  84 */     return this.information.get(pos + 3);
/*     */   }
/*     */   
/*     */   private DecodedNumeric decodeNumeric(int pos) throws FormatException {
/*  88 */     if (pos + 7 > this.information.getSize()) {
/*  89 */       int i = extractNumericValueFromBitArray(pos, 4);
/*  90 */       if (i == 0) {
/*  91 */         return new DecodedNumeric(this.information.getSize(), 10, 10);
/*     */       }
/*  93 */       return new DecodedNumeric(this.information.getSize(), i - 1, 10);
/*     */     } 
/*  95 */     int numeric = extractNumericValueFromBitArray(pos, 7);
/*     */     
/*  97 */     int digit1 = (numeric - 8) / 11;
/*  98 */     int digit2 = (numeric - 8) % 11;
/*     */     
/* 100 */     return new DecodedNumeric(pos + 7, digit1, digit2);
/*     */   }
/*     */   
/*     */   int extractNumericValueFromBitArray(int pos, int bits) {
/* 104 */     return extractNumericValueFromBitArray(this.information, pos, bits);
/*     */   }
/*     */   
/*     */   static int extractNumericValueFromBitArray(BitArray information, int pos, int bits) {
/* 108 */     int value = 0;
/* 109 */     for (int i = 0; i < bits; i++) {
/* 110 */       if (information.get(pos + i)) {
/* 111 */         value |= 1 << bits - i - 1;
/*     */       }
/*     */     } 
/*     */     
/* 115 */     return value;
/*     */   }
/*     */   
/*     */   DecodedInformation decodeGeneralPurposeField(int pos, String remaining) throws FormatException {
/* 119 */     this.buffer.setLength(0);
/*     */     
/* 121 */     if (remaining != null) {
/* 122 */       this.buffer.append(remaining);
/*     */     }
/*     */     
/* 125 */     this.current.setPosition(pos);
/*     */     
/* 127 */     DecodedInformation lastDecoded = parseBlocks();
/* 128 */     if (lastDecoded != null && lastDecoded.isRemaining()) {
/* 129 */       return new DecodedInformation(this.current.getPosition(), this.buffer.toString(), lastDecoded.getRemainingValue());
/*     */     }
/* 131 */     return new DecodedInformation(this.current.getPosition(), this.buffer.toString());
/*     */   }
/*     */   
/*     */   private DecodedInformation parseBlocks() throws FormatException {
/*     */     boolean isFinished;
/*     */     BlockParsedResult result;
/*     */     do {
/* 138 */       int initialPosition = this.current.getPosition();
/*     */       
/* 140 */       if (this.current.isAlpha()) {
/* 141 */         result = parseAlphaBlock();
/* 142 */         isFinished = result.isFinished();
/* 143 */       } else if (this.current.isIsoIec646()) {
/* 144 */         result = parseIsoIec646Block();
/* 145 */         isFinished = result.isFinished();
/*     */       } else {
/* 147 */         result = parseNumericBlock();
/* 148 */         isFinished = result.isFinished();
/*     */       } 
/*     */       
/* 151 */       boolean positionChanged = (initialPosition != this.current.getPosition());
/* 152 */       if (!positionChanged && !isFinished) {
/*     */         break;
/*     */       }
/* 155 */     } while (!isFinished);
/*     */     
/* 157 */     return result.getDecodedInformation();
/*     */   }
/*     */   
/*     */   private BlockParsedResult parseNumericBlock() throws FormatException {
/* 161 */     while (isStillNumeric(this.current.getPosition())) {
/* 162 */       DecodedNumeric numeric = decodeNumeric(this.current.getPosition());
/* 163 */       this.current.setPosition(numeric.getNewPosition());
/*     */       
/* 165 */       if (numeric.isFirstDigitFNC1()) {
/*     */         DecodedInformation information;
/* 167 */         if (numeric.isSecondDigitFNC1()) {
/* 168 */           information = new DecodedInformation(this.current.getPosition(), this.buffer.toString());
/*     */         } else {
/* 170 */           information = new DecodedInformation(this.current.getPosition(), this.buffer.toString(), numeric.getSecondDigit());
/*     */         } 
/* 172 */         return new BlockParsedResult(information, true);
/*     */       } 
/* 174 */       this.buffer.append(numeric.getFirstDigit());
/*     */       
/* 176 */       if (numeric.isSecondDigitFNC1()) {
/* 177 */         DecodedInformation information = new DecodedInformation(this.current.getPosition(), this.buffer.toString());
/* 178 */         return new BlockParsedResult(information, true);
/*     */       } 
/* 180 */       this.buffer.append(numeric.getSecondDigit());
/*     */     } 
/*     */     
/* 183 */     if (isNumericToAlphaNumericLatch(this.current.getPosition())) {
/* 184 */       this.current.setAlpha();
/* 185 */       this.current.incrementPosition(4);
/*     */     } 
/* 187 */     return new BlockParsedResult(false);
/*     */   }
/*     */   
/*     */   private BlockParsedResult parseIsoIec646Block() throws FormatException {
/* 191 */     while (isStillIsoIec646(this.current.getPosition())) {
/* 192 */       DecodedChar iso = decodeIsoIec646(this.current.getPosition());
/* 193 */       this.current.setPosition(iso.getNewPosition());
/*     */       
/* 195 */       if (iso.isFNC1()) {
/* 196 */         DecodedInformation information = new DecodedInformation(this.current.getPosition(), this.buffer.toString());
/* 197 */         return new BlockParsedResult(information, true);
/*     */       } 
/* 199 */       this.buffer.append(iso.getValue());
/*     */     } 
/*     */     
/* 202 */     if (isAlphaOr646ToNumericLatch(this.current.getPosition())) {
/* 203 */       this.current.incrementPosition(3);
/* 204 */       this.current.setNumeric();
/* 205 */     } else if (isAlphaTo646ToAlphaLatch(this.current.getPosition())) {
/* 206 */       if (this.current.getPosition() + 5 < this.information.getSize()) {
/* 207 */         this.current.incrementPosition(5);
/*     */       } else {
/* 209 */         this.current.setPosition(this.information.getSize());
/*     */       } 
/*     */       
/* 212 */       this.current.setAlpha();
/*     */     } 
/* 214 */     return new BlockParsedResult(false);
/*     */   }
/*     */   
/*     */   private BlockParsedResult parseAlphaBlock() {
/* 218 */     while (isStillAlpha(this.current.getPosition())) {
/* 219 */       DecodedChar alpha = decodeAlphanumeric(this.current.getPosition());
/* 220 */       this.current.setPosition(alpha.getNewPosition());
/*     */       
/* 222 */       if (alpha.isFNC1()) {
/* 223 */         DecodedInformation information = new DecodedInformation(this.current.getPosition(), this.buffer.toString());
/* 224 */         return new BlockParsedResult(information, true);
/*     */       } 
/*     */       
/* 227 */       this.buffer.append(alpha.getValue());
/*     */     } 
/*     */     
/* 230 */     if (isAlphaOr646ToNumericLatch(this.current.getPosition())) {
/* 231 */       this.current.incrementPosition(3);
/* 232 */       this.current.setNumeric();
/* 233 */     } else if (isAlphaTo646ToAlphaLatch(this.current.getPosition())) {
/* 234 */       if (this.current.getPosition() + 5 < this.information.getSize()) {
/* 235 */         this.current.incrementPosition(5);
/*     */       } else {
/* 237 */         this.current.setPosition(this.information.getSize());
/*     */       } 
/*     */       
/* 240 */       this.current.setIsoIec646();
/*     */     } 
/* 242 */     return new BlockParsedResult(false);
/*     */   }
/*     */   
/*     */   private boolean isStillIsoIec646(int pos) {
/* 246 */     if (pos + 5 > this.information.getSize()) {
/* 247 */       return false;
/*     */     }
/*     */     
/* 250 */     int fiveBitValue = extractNumericValueFromBitArray(pos, 5);
/* 251 */     if (fiveBitValue >= 5 && fiveBitValue < 16) {
/* 252 */       return true;
/*     */     }
/*     */     
/* 255 */     if (pos + 7 > this.information.getSize()) {
/* 256 */       return false;
/*     */     }
/*     */     
/* 259 */     int sevenBitValue = extractNumericValueFromBitArray(pos, 7);
/* 260 */     if (sevenBitValue >= 64 && sevenBitValue < 116) {
/* 261 */       return true;
/*     */     }
/*     */     
/* 264 */     if (pos + 8 > this.information.getSize()) {
/* 265 */       return false;
/*     */     }
/*     */     
/* 268 */     int eightBitValue = extractNumericValueFromBitArray(pos, 8);
/* 269 */     return (eightBitValue >= 232 && eightBitValue < 253);
/*     */   }
/*     */   
/*     */   private DecodedChar decodeIsoIec646(int pos) throws FormatException {
/*     */     char c;
/* 274 */     int fiveBitValue = extractNumericValueFromBitArray(pos, 5);
/* 275 */     if (fiveBitValue == 15) {
/* 276 */       return new DecodedChar(pos + 5, '$');
/*     */     }
/*     */     
/* 279 */     if (fiveBitValue >= 5 && fiveBitValue < 15) {
/* 280 */       return new DecodedChar(pos + 5, (char)(48 + fiveBitValue - 5));
/*     */     }
/*     */     
/* 283 */     int sevenBitValue = extractNumericValueFromBitArray(pos, 7);
/*     */     
/* 285 */     if (sevenBitValue >= 64 && sevenBitValue < 90) {
/* 286 */       return new DecodedChar(pos + 7, (char)(sevenBitValue + 1));
/*     */     }
/*     */     
/* 289 */     if (sevenBitValue >= 90 && sevenBitValue < 116) {
/* 290 */       return new DecodedChar(pos + 7, (char)(sevenBitValue + 7));
/*     */     }
/*     */     
/* 293 */     int eightBitValue = extractNumericValueFromBitArray(pos, 8);
/*     */     
/* 295 */     switch (eightBitValue) {
/*     */       case 232:
/* 297 */         c = '!';
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
/* 362 */         return new DecodedChar(pos + 8, c);case 233: c = '"'; return new DecodedChar(pos + 8, c);case 234: c = '%'; return new DecodedChar(pos + 8, c);case 235: c = '&'; return new DecodedChar(pos + 8, c);case 236: c = '\''; return new DecodedChar(pos + 8, c);case 237: c = '('; return new DecodedChar(pos + 8, c);case 238: c = ')'; return new DecodedChar(pos + 8, c);case 239: c = '*'; return new DecodedChar(pos + 8, c);case 240: c = '+'; return new DecodedChar(pos + 8, c);case 241: c = ','; return new DecodedChar(pos + 8, c);case 242: c = '-'; return new DecodedChar(pos + 8, c);case 243: c = '.'; return new DecodedChar(pos + 8, c);case 244: c = '/'; return new DecodedChar(pos + 8, c);case 245: c = ':'; return new DecodedChar(pos + 8, c);case 246: c = ';'; return new DecodedChar(pos + 8, c);case 247: c = '<'; return new DecodedChar(pos + 8, c);case 248: c = '='; return new DecodedChar(pos + 8, c);case 249: c = '>'; return new DecodedChar(pos + 8, c);case 250: c = '?'; return new DecodedChar(pos + 8, c);case 251: c = '_'; return new DecodedChar(pos + 8, c);case 252: c = ' '; return new DecodedChar(pos + 8, c);
/*     */     } 
/*     */     throw FormatException.getFormatInstance();
/*     */   } private boolean isStillAlpha(int pos) {
/* 366 */     if (pos + 5 > this.information.getSize()) {
/* 367 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 371 */     int fiveBitValue = extractNumericValueFromBitArray(pos, 5);
/* 372 */     if (fiveBitValue >= 5 && fiveBitValue < 16) {
/* 373 */       return true;
/*     */     }
/*     */     
/* 376 */     if (pos + 6 > this.information.getSize()) {
/* 377 */       return false;
/*     */     }
/*     */     
/* 380 */     int sixBitValue = extractNumericValueFromBitArray(pos, 6);
/* 381 */     return (sixBitValue >= 16 && sixBitValue < 63);
/*     */   }
/*     */   private DecodedChar decodeAlphanumeric(int pos) {
/*     */     char c;
/* 385 */     int fiveBitValue = extractNumericValueFromBitArray(pos, 5);
/* 386 */     if (fiveBitValue == 15) {
/* 387 */       return new DecodedChar(pos + 5, '$');
/*     */     }
/*     */     
/* 390 */     if (fiveBitValue >= 5 && fiveBitValue < 15) {
/* 391 */       return new DecodedChar(pos + 5, (char)(48 + fiveBitValue - 5));
/*     */     }
/*     */     
/* 394 */     int sixBitValue = extractNumericValueFromBitArray(pos, 6);
/*     */     
/* 396 */     if (sixBitValue >= 32 && sixBitValue < 58) {
/* 397 */       return new DecodedChar(pos + 6, (char)(sixBitValue + 33));
/*     */     }
/*     */ 
/*     */     
/* 401 */     switch (sixBitValue) {
/*     */       case 58:
/* 403 */         c = '*';
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
/* 420 */         return new DecodedChar(pos + 6, c);case 59: c = ','; return new DecodedChar(pos + 6, c);case 60: c = '-'; return new DecodedChar(pos + 6, c);case 61: c = '.'; return new DecodedChar(pos + 6, c);case 62: c = '/'; return new DecodedChar(pos + 6, c);
/*     */     } 
/*     */     throw new IllegalStateException("Decoding invalid alphanumeric value: " + sixBitValue);
/*     */   } private boolean isAlphaTo646ToAlphaLatch(int pos) {
/* 424 */     if (pos + 1 > this.information.getSize()) {
/* 425 */       return false;
/*     */     }
/*     */     
/* 428 */     for (int i = 0; i < 5 && i + pos < this.information.getSize(); i++) {
/* 429 */       if (i == 2) {
/* 430 */         if (!this.information.get(pos + 2)) {
/* 431 */           return false;
/*     */         }
/* 433 */       } else if (this.information.get(pos + i)) {
/* 434 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 438 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isAlphaOr646ToNumericLatch(int pos) {
/* 443 */     if (pos + 3 > this.information.getSize()) {
/* 444 */       return false;
/*     */     }
/*     */     
/* 447 */     for (int i = pos; i < pos + 3; i++) {
/* 448 */       if (this.information.get(i)) {
/* 449 */         return false;
/*     */       }
/*     */     } 
/* 452 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isNumericToAlphaNumericLatch(int pos) {
/* 458 */     if (pos + 1 > this.information.getSize()) {
/* 459 */       return false;
/*     */     }
/*     */     
/* 462 */     for (int i = 0; i < 4 && i + pos < this.information.getSize(); i++) {
/* 463 */       if (this.information.get(pos + i)) {
/* 464 */         return false;
/*     */       }
/*     */     } 
/* 467 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\oned\rss\expanded\decoders\GeneralAppIdDecoder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */