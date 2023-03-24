/*      */ package com.sun.xml.wss.impl.misc;
/*      */ 
/*      */ import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Base64
/*      */ {
/*      */   public static final int BASE64DEFAULTLENGTH = 76;
/*      */   private static final int BASELENGTH = 255;
/*      */   private static final int LOOKUPLENGTH = 64;
/*      */   private static final int TWENTYFOURBITGROUP = 24;
/*      */   private static final int EIGHTBIT = 8;
/*      */   private static final int SIXTEENBIT = 16;
/*      */   private static final int FOURBYTE = 4;
/*      */   private static final int SIGN = -128;
/*      */   private static final char PAD = '=';
/*      */   private static final boolean fDebug = false;
/*  104 */   private static final byte[] base64Alphabet = new byte[255];
/*  105 */   private static final char[] lookUpBase64Alphabet = new char[64];
/*      */   
/*      */   static {
/*      */     int i;
/*  109 */     for (i = 0; i < 255; i++) {
/*  110 */       base64Alphabet[i] = -1;
/*      */     }
/*  112 */     for (i = 90; i >= 65; i--) {
/*  113 */       base64Alphabet[i] = (byte)(i - 65);
/*      */     }
/*  115 */     for (i = 122; i >= 97; i--) {
/*  116 */       base64Alphabet[i] = (byte)(i - 97 + 26);
/*      */     }
/*      */     
/*  119 */     for (i = 57; i >= 48; i--) {
/*  120 */       base64Alphabet[i] = (byte)(i - 48 + 52);
/*      */     }
/*      */     
/*  123 */     base64Alphabet[43] = 62;
/*  124 */     base64Alphabet[47] = 63;
/*      */     
/*  126 */     for (i = 0; i <= 25; i++)
/*  127 */       lookUpBase64Alphabet[i] = (char)(65 + i); 
/*      */     int j;
/*  129 */     for (i = 26, j = 0; i <= 51; i++, j++) {
/*  130 */       lookUpBase64Alphabet[i] = (char)(97 + j);
/*      */     }
/*  132 */     for (i = 52, j = 0; i <= 61; i++, j++)
/*  133 */       lookUpBase64Alphabet[i] = (char)(48 + j); 
/*  134 */     lookUpBase64Alphabet[62] = '+';
/*  135 */     lookUpBase64Alphabet[63] = '/';
/*      */   }
/*      */ 
/*      */   
/*      */   protected static final boolean isWhiteSpace(byte octect) {
/*  140 */     return (octect == 32 || octect == 13 || octect == 10 || octect == 9);
/*      */   }
/*      */   
/*      */   protected static final boolean isPad(byte octect) {
/*  144 */     return (octect == 61);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] encodeData(byte[] binaryData) {
/*  156 */     return encodeData(binaryData, 2046);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char[] encodeData(byte[] binaryData, int length) {
/*  169 */     if (length < 4) {
/*  170 */       length = Integer.MAX_VALUE;
/*      */     }
/*      */     
/*  173 */     if (binaryData == null) {
/*  174 */       return null;
/*      */     }
/*  176 */     int lengthDataBits = binaryData.length * 8;
/*  177 */     if (lengthDataBits == 0) {
/*  178 */       return "".toCharArray();
/*      */     }
/*      */ 
/*      */     
/*  182 */     int fewerThan24bits = lengthDataBits % 24;
/*  183 */     int numberTriplets = lengthDataBits / 24;
/*  184 */     int numberQuartet = (fewerThan24bits != 0) ? (numberTriplets + 1) : numberTriplets;
/*  185 */     int quartesPerLine = length / 4;
/*  186 */     int numberLines = (numberQuartet - 1) / quartesPerLine;
/*  187 */     char[] encodedData = null;
/*      */     
/*  189 */     encodedData = new char[numberQuartet * 4 + numberLines];
/*      */     
/*  191 */     byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;
/*      */     
/*  193 */     int encodedIndex = 0;
/*  194 */     int dataIndex = 0;
/*  195 */     int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  200 */     for (int line = 0; line < numberLines; line++) {
/*  201 */       for (int quartet = 0; quartet < 19; quartet++) {
/*  202 */         b1 = binaryData[dataIndex++];
/*  203 */         b2 = binaryData[dataIndex++];
/*  204 */         b3 = binaryData[dataIndex++];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  210 */         l = (byte)(b2 & 0xF);
/*  211 */         k = (byte)(b1 & 0x3);
/*      */         
/*  213 */         byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*      */         
/*  215 */         byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*  216 */         byte val3 = ((b3 & Byte.MIN_VALUE) == 0) ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 0xFC);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  224 */         encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
/*  225 */         encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k << 4];
/*  226 */         encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2 | val3];
/*  227 */         encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 0x3F];
/*      */         
/*  229 */         i++;
/*      */       } 
/*  231 */       encodedData[encodedIndex++] = '\n';
/*      */     } 
/*      */     
/*  234 */     for (; i < numberTriplets; i++) {
/*  235 */       b1 = binaryData[dataIndex++];
/*  236 */       b2 = binaryData[dataIndex++];
/*  237 */       b3 = binaryData[dataIndex++];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  243 */       l = (byte)(b2 & 0xF);
/*  244 */       k = (byte)(b1 & 0x3);
/*      */       
/*  246 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*      */       
/*  248 */       byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*  249 */       byte val3 = ((b3 & Byte.MIN_VALUE) == 0) ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 0xFC);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  257 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
/*  258 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k << 4];
/*  259 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2 | val3];
/*  260 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 0x3F];
/*      */     } 
/*      */ 
/*      */     
/*  264 */     if (fewerThan24bits == 8) {
/*  265 */       b1 = binaryData[dataIndex];
/*  266 */       k = (byte)(b1 & 0x3);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  271 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*  272 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
/*  273 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[k << 4];
/*  274 */       encodedData[encodedIndex++] = '=';
/*  275 */       encodedData[encodedIndex++] = '=';
/*  276 */     } else if (fewerThan24bits == 16) {
/*  277 */       b1 = binaryData[dataIndex];
/*  278 */       b2 = binaryData[dataIndex + 1];
/*  279 */       l = (byte)(b2 & 0xF);
/*  280 */       k = (byte)(b1 & 0x3);
/*      */       
/*  282 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*  283 */       byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*      */       
/*  285 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
/*  286 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k << 4];
/*  287 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2];
/*  288 */       encodedData[encodedIndex++] = '=';
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  293 */     return encodedData;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String encode(byte[] binaryData, int length) {
/*  299 */     if (length < 4) {
/*  300 */       length = Integer.MAX_VALUE;
/*      */     }
/*      */     
/*  303 */     if (binaryData == null) {
/*  304 */       return null;
/*      */     }
/*  306 */     int lengthDataBits = binaryData.length * 8;
/*  307 */     if (lengthDataBits == 0) {
/*  308 */       return "";
/*      */     }
/*      */     
/*  311 */     int fewerThan24bits = lengthDataBits % 24;
/*  312 */     int numberTriplets = lengthDataBits / 24;
/*  313 */     int numberQuartet = (fewerThan24bits != 0) ? (numberTriplets + 1) : numberTriplets;
/*  314 */     int quartesPerLine = length / 4;
/*  315 */     int numberLines = (numberQuartet - 1) / quartesPerLine;
/*  316 */     char[] encodedData = null;
/*      */     
/*  318 */     encodedData = new char[numberQuartet * 4 + numberLines];
/*      */     
/*  320 */     byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;
/*      */     
/*  322 */     int encodedIndex = 0;
/*  323 */     int dataIndex = 0;
/*  324 */     int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  329 */     for (int line = 0; line < numberLines; line++) {
/*  330 */       for (int quartet = 0; quartet < 19; quartet++) {
/*  331 */         b1 = binaryData[dataIndex++];
/*  332 */         b2 = binaryData[dataIndex++];
/*  333 */         b3 = binaryData[dataIndex++];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  339 */         l = (byte)(b2 & 0xF);
/*  340 */         k = (byte)(b1 & 0x3);
/*      */         
/*  342 */         byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*      */         
/*  344 */         byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*  345 */         byte val3 = ((b3 & Byte.MIN_VALUE) == 0) ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 0xFC);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  353 */         encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
/*  354 */         encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k << 4];
/*  355 */         encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2 | val3];
/*  356 */         encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 0x3F];
/*      */         
/*  358 */         i++;
/*      */       } 
/*  360 */       encodedData[encodedIndex++] = '\n';
/*      */     } 
/*      */     
/*  363 */     for (; i < numberTriplets; i++) {
/*  364 */       b1 = binaryData[dataIndex++];
/*  365 */       b2 = binaryData[dataIndex++];
/*  366 */       b3 = binaryData[dataIndex++];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  372 */       l = (byte)(b2 & 0xF);
/*  373 */       k = (byte)(b1 & 0x3);
/*      */       
/*  375 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*      */       
/*  377 */       byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*  378 */       byte val3 = ((b3 & Byte.MIN_VALUE) == 0) ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 0xFC);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  386 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
/*  387 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k << 4];
/*  388 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2 | val3];
/*  389 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 0x3F];
/*      */     } 
/*      */ 
/*      */     
/*  393 */     if (fewerThan24bits == 8) {
/*  394 */       b1 = binaryData[dataIndex];
/*  395 */       k = (byte)(b1 & 0x3);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  400 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*  401 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
/*  402 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[k << 4];
/*  403 */       encodedData[encodedIndex++] = '=';
/*  404 */       encodedData[encodedIndex++] = '=';
/*  405 */     } else if (fewerThan24bits == 16) {
/*  406 */       b1 = binaryData[dataIndex];
/*  407 */       b2 = binaryData[dataIndex + 1];
/*  408 */       l = (byte)(b2 & 0xF);
/*  409 */       k = (byte)(b1 & 0x3);
/*      */       
/*  411 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*  412 */       byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*      */       
/*  414 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
/*  415 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | k << 4];
/*  416 */       encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2];
/*  417 */       encodedData[encodedIndex++] = '=';
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  422 */     return new String(encodedData);
/*      */   }
/*      */ 
/*      */   
/*      */   public static String encode(byte[] binaryData) {
/*  427 */     return encode(binaryData, 2048);
/*      */   }
/*      */   
/*      */   public static void encodeToStream(byte[] binaryData, int length, OutputStream stream) throws IOException {
/*  431 */     encodeToStream(binaryData, 0, length, stream);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void encodeToStream(byte[] binaryData, int offset, int length, OutputStream stream) throws IOException {
/*  436 */     if (length < 4) {
/*  437 */       length = Integer.MAX_VALUE;
/*      */     }
/*      */     
/*  440 */     if (binaryData == null) {
/*      */       return;
/*      */     }
/*  443 */     int lengthDataBits = length * 8;
/*  444 */     if (lengthDataBits == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  448 */     int fewerThan24bits = lengthDataBits % 24;
/*  449 */     int numberTriplets = lengthDataBits / 24;
/*  450 */     int numberQuartet = (fewerThan24bits != 0) ? (numberTriplets + 1) : numberTriplets;
/*  451 */     int quartesPerLine = length / 4;
/*  452 */     int numberLines = (numberQuartet - 1) / quartesPerLine;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  457 */     byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;
/*      */     
/*  459 */     int encodedIndex = 0;
/*  460 */     int dataIndex = offset;
/*  461 */     int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  466 */     for (int line = 0; line < numberLines; line++) {
/*  467 */       for (int quartet = 0; quartet < 19; quartet++) {
/*  468 */         b1 = binaryData[dataIndex++];
/*  469 */         b2 = binaryData[dataIndex++];
/*  470 */         b3 = binaryData[dataIndex++];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  476 */         l = (byte)(b2 & 0xF);
/*  477 */         k = (byte)(b1 & 0x3);
/*      */         
/*  479 */         byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*      */         
/*  481 */         byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*  482 */         byte val3 = ((b3 & Byte.MIN_VALUE) == 0) ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 0xFC);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  494 */         stream.write(lookUpBase64Alphabet[val1]);
/*  495 */         stream.write(lookUpBase64Alphabet[val2 | k << 4]);
/*  496 */         stream.write(lookUpBase64Alphabet[l << 2 | val3]);
/*  497 */         stream.write(lookUpBase64Alphabet[b3 & 0x3F]);
/*  498 */         i++;
/*      */       } 
/*      */       
/*  501 */       stream.write(10);
/*      */     } 
/*      */     
/*  504 */     for (; i < numberTriplets; i++) {
/*  505 */       b1 = binaryData[dataIndex++];
/*  506 */       b2 = binaryData[dataIndex++];
/*  507 */       b3 = binaryData[dataIndex++];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  513 */       l = (byte)(b2 & 0xF);
/*  514 */       k = (byte)(b1 & 0x3);
/*      */       
/*  516 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*      */       
/*  518 */       byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*  519 */       byte val3 = ((b3 & Byte.MIN_VALUE) == 0) ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 0xFC);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  526 */       stream.write(lookUpBase64Alphabet[val1]);
/*  527 */       stream.write(lookUpBase64Alphabet[val2 | k << 4]);
/*  528 */       stream.write(lookUpBase64Alphabet[l << 2 | val3]);
/*  529 */       stream.write(lookUpBase64Alphabet[b3 & 0x3F]);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  537 */     if (fewerThan24bits == 8) {
/*  538 */       b1 = binaryData[dataIndex];
/*  539 */       k = (byte)(b1 & 0x3);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  544 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  549 */       stream.write(lookUpBase64Alphabet[val1]);
/*  550 */       stream.write(lookUpBase64Alphabet[k << 4]);
/*  551 */       stream.write(61);
/*  552 */       stream.write(61);
/*  553 */     } else if (fewerThan24bits == 16) {
/*  554 */       b1 = binaryData[dataIndex];
/*  555 */       b2 = binaryData[dataIndex + 1];
/*  556 */       l = (byte)(b2 & 0xF);
/*  557 */       k = (byte)(b1 & 0x3);
/*      */       
/*  559 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*  560 */       byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  567 */       stream.write(lookUpBase64Alphabet[val1]);
/*  568 */       stream.write(lookUpBase64Alphabet[val2 | k << 4]);
/*  569 */       stream.write(lookUpBase64Alphabet[l << 2]);
/*  570 */       stream.write(61);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void encodeToStream(ByteArray binaryData, int length, OutputStream stream) throws IOException {
/*  580 */     if (length < 4) {
/*  581 */       length = Integer.MAX_VALUE;
/*      */     }
/*      */     
/*  584 */     if (binaryData == null) {
/*      */       return;
/*      */     }
/*  587 */     int lengthDataBits = binaryData.length * 8;
/*  588 */     if (lengthDataBits == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  592 */     int fewerThan24bits = lengthDataBits % 24;
/*  593 */     int numberTriplets = lengthDataBits / 24;
/*  594 */     int numberQuartet = (fewerThan24bits != 0) ? (numberTriplets + 1) : numberTriplets;
/*  595 */     int quartesPerLine = length / 4;
/*  596 */     int numberLines = (numberQuartet - 1) / quartesPerLine;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  601 */     byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;
/*      */     
/*  603 */     int encodedIndex = 0;
/*  604 */     int dataIndex = 0;
/*  605 */     int i = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  610 */     for (int line = 0; line < numberLines; line++) {
/*  611 */       for (int quartet = 0; quartet < 19; quartet++) {
/*  612 */         b1 = binaryData.byteAt(dataIndex++);
/*  613 */         b2 = binaryData.byteAt(dataIndex++);
/*  614 */         b3 = binaryData.byteAt(dataIndex++);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  620 */         l = (byte)(b2 & 0xF);
/*  621 */         k = (byte)(b1 & 0x3);
/*      */         
/*  623 */         byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*      */         
/*  625 */         byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*  626 */         byte val3 = ((b3 & Byte.MIN_VALUE) == 0) ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 0xFC);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  638 */         stream.write(lookUpBase64Alphabet[val1]);
/*  639 */         stream.write(lookUpBase64Alphabet[val2 | k << 4]);
/*  640 */         stream.write(lookUpBase64Alphabet[l << 2 | val3]);
/*  641 */         stream.write(lookUpBase64Alphabet[b3 & 0x3F]);
/*  642 */         i++;
/*      */       } 
/*      */       
/*  645 */       stream.write(10);
/*      */     } 
/*      */     
/*  648 */     for (; i < numberTriplets; i++) {
/*  649 */       b1 = binaryData.byteAt(dataIndex++);
/*  650 */       b2 = binaryData.byteAt(dataIndex++);
/*  651 */       b3 = binaryData.byteAt(dataIndex++);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  657 */       l = (byte)(b2 & 0xF);
/*  658 */       k = (byte)(b1 & 0x3);
/*      */       
/*  660 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*      */       
/*  662 */       byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*  663 */       byte val3 = ((b3 & Byte.MIN_VALUE) == 0) ? (byte)(b3 >> 6) : (byte)(b3 >> 6 ^ 0xFC);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  670 */       stream.write(lookUpBase64Alphabet[val1]);
/*  671 */       stream.write(lookUpBase64Alphabet[val2 | k << 4]);
/*  672 */       stream.write(lookUpBase64Alphabet[l << 2 | val3]);
/*  673 */       stream.write(lookUpBase64Alphabet[b3 & 0x3F]);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  681 */     if (fewerThan24bits == 8) {
/*  682 */       b1 = binaryData.byteAt(dataIndex);
/*  683 */       k = (byte)(b1 & 0x3);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  688 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  693 */       stream.write(lookUpBase64Alphabet[val1]);
/*  694 */       stream.write(lookUpBase64Alphabet[k << 4]);
/*  695 */       stream.write(61);
/*  696 */       stream.write(61);
/*  697 */     } else if (fewerThan24bits == 16) {
/*  698 */       b1 = binaryData.byteAt(dataIndex);
/*  699 */       b2 = binaryData.byteAt(dataIndex + 1);
/*  700 */       l = (byte)(b2 & 0xF);
/*  701 */       k = (byte)(b1 & 0x3);
/*      */       
/*  703 */       byte val1 = ((b1 & Byte.MIN_VALUE) == 0) ? (byte)(b1 >> 2) : (byte)(b1 >> 2 ^ 0xC0);
/*  704 */       byte val2 = ((b2 & Byte.MIN_VALUE) == 0) ? (byte)(b2 >> 4) : (byte)(b2 >> 4 ^ 0xF0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  711 */       stream.write(lookUpBase64Alphabet[val1]);
/*  712 */       stream.write(lookUpBase64Alphabet[val2 | k << 4]);
/*  713 */       stream.write(lookUpBase64Alphabet[l << 2]);
/*  714 */       stream.write(61);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final byte[] decode(String encoded) throws Base64DecodingException {
/*  731 */     if (encoded == null) {
/*  732 */       return null;
/*      */     }
/*  734 */     return decodeInternal(encoded.getBytes());
/*      */   }
/*      */   
/*      */   public static final byte[] decode(byte[] base64Data) throws Base64DecodingException {
/*  738 */     return decodeInternal(base64Data);
/*      */   }
/*      */ 
/*      */   
/*      */   protected static final byte[] decodeInternal(byte[] base64Data) throws Base64DecodingException {
/*  743 */     int len = removeWhiteSpace(base64Data);
/*      */     
/*  745 */     if (len % 4 != 0) {
/*  746 */       throw new Base64DecodingException("decoding.divisible.four");
/*      */     }
/*      */ 
/*      */     
/*  750 */     int numberQuadruple = len / 4;
/*      */     
/*  752 */     if (numberQuadruple == 0) {
/*  753 */       return new byte[0];
/*      */     }
/*  755 */     byte[] decodedData = null;
/*  756 */     byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
/*      */ 
/*      */     
/*  759 */     int i = 0;
/*  760 */     int encodedIndex = 0;
/*  761 */     int dataIndex = 0;
/*      */ 
/*      */     
/*  764 */     dataIndex = (numberQuadruple - 1) * 4;
/*  765 */     encodedIndex = (numberQuadruple - 1) * 3;
/*      */     
/*  767 */     b1 = base64Alphabet[base64Data[dataIndex++]];
/*  768 */     b2 = base64Alphabet[base64Data[dataIndex++]];
/*  769 */     if (b1 == -1 || b2 == -1) {
/*  770 */       throw new Base64DecodingException("decoding.general");
/*      */     }
/*      */     
/*      */     byte d3;
/*      */     
/*  775 */     b3 = base64Alphabet[d3 = base64Data[dataIndex++]]; byte d4;
/*  776 */     b4 = base64Alphabet[d4 = base64Data[dataIndex++]];
/*  777 */     if (b3 == -1 || b4 == -1) {
/*      */       
/*  779 */       if (isPad(d3) && isPad(d4)) {
/*  780 */         if ((b2 & 0xF) != 0)
/*  781 */           throw new Base64DecodingException("decoding.general"); 
/*  782 */         decodedData = new byte[encodedIndex + 1];
/*  783 */         decodedData[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
/*  784 */       } else if (!isPad(d3) && isPad(d4)) {
/*  785 */         if ((b3 & 0x3) != 0)
/*  786 */           throw new Base64DecodingException("decoding.general"); 
/*  787 */         decodedData = new byte[encodedIndex + 2];
/*  788 */         decodedData[encodedIndex++] = (byte)(b1 << 2 | b2 >> 4);
/*  789 */         decodedData[encodedIndex] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/*      */       } else {
/*  791 */         throw new Base64DecodingException("decoding.general");
/*      */       } 
/*      */     } else {
/*      */       
/*  795 */       decodedData = new byte[encodedIndex + 3];
/*  796 */       decodedData[encodedIndex++] = (byte)(b1 << 2 | b2 >> 4);
/*  797 */       decodedData[encodedIndex++] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/*  798 */       decodedData[encodedIndex++] = (byte)(b3 << 6 | b4);
/*      */     } 
/*  800 */     encodedIndex = 0;
/*  801 */     dataIndex = 0;
/*      */     
/*  803 */     for (i = numberQuadruple - 1; i > 0; i--) {
/*  804 */       b1 = base64Alphabet[base64Data[dataIndex++]];
/*  805 */       b2 = base64Alphabet[base64Data[dataIndex++]];
/*  806 */       b3 = base64Alphabet[base64Data[dataIndex++]];
/*  807 */       b4 = base64Alphabet[base64Data[dataIndex++]];
/*      */       
/*  809 */       if (b1 == -1 || b2 == -1 || b3 == -1 || b4 == -1)
/*      */       {
/*      */ 
/*      */         
/*  813 */         throw new Base64DecodingException("decoding.general");
/*      */       }
/*      */       
/*  816 */       decodedData[encodedIndex++] = (byte)(b1 << 2 | b2 >> 4);
/*  817 */       decodedData[encodedIndex++] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/*  818 */       decodedData[encodedIndex++] = (byte)(b3 << 6 | b4);
/*      */     } 
/*  820 */     return decodedData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void decode(byte[] base64Data, OutputStream os) throws Base64DecodingException, IOException {
/*  834 */     int len = removeWhiteSpace(base64Data);
/*      */     
/*  836 */     if (len % 4 != 0) {
/*  837 */       throw new Base64DecodingException("decoding.divisible.four");
/*      */     }
/*      */ 
/*      */     
/*  841 */     int numberQuadruple = len / 4;
/*      */     
/*  843 */     if (numberQuadruple == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  847 */     byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
/*      */     
/*  849 */     int i = 0;
/*      */     
/*  851 */     int dataIndex = 0;
/*      */ 
/*      */     
/*  854 */     for (i = numberQuadruple - 1; i > 0; i--) {
/*  855 */       b1 = base64Alphabet[base64Data[dataIndex++]];
/*  856 */       b2 = base64Alphabet[base64Data[dataIndex++]];
/*  857 */       b3 = base64Alphabet[base64Data[dataIndex++]];
/*  858 */       b4 = base64Alphabet[base64Data[dataIndex++]];
/*  859 */       if (b1 == -1 || b2 == -1 || b3 == -1 || b4 == -1)
/*      */       {
/*      */ 
/*      */         
/*  863 */         throw new Base64DecodingException("decoding.general");
/*      */       }
/*      */ 
/*      */       
/*  867 */       os.write((byte)(b1 << 2 | b2 >> 4));
/*  868 */       os.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
/*  869 */       os.write((byte)(b3 << 6 | b4));
/*      */     } 
/*  871 */     b1 = base64Alphabet[base64Data[dataIndex++]];
/*  872 */     b2 = base64Alphabet[base64Data[dataIndex++]];
/*      */ 
/*      */     
/*  875 */     if (b1 == -1 || b2 == -1)
/*      */     {
/*  877 */       throw new Base64DecodingException("decoding.general");
/*      */     }
/*      */     
/*      */     byte d3;
/*  881 */     b3 = base64Alphabet[d3 = base64Data[dataIndex++]]; byte d4;
/*  882 */     b4 = base64Alphabet[d4 = base64Data[dataIndex++]];
/*  883 */     if (b3 == -1 || b4 == -1) {
/*      */       
/*  885 */       if (isPad(d3) && isPad(d4)) {
/*  886 */         if ((b2 & 0xF) != 0)
/*  887 */           throw new Base64DecodingException("decoding.general"); 
/*  888 */         os.write((byte)(b1 << 2 | b2 >> 4));
/*  889 */       } else if (!isPad(d3) && isPad(d4)) {
/*  890 */         if ((b3 & 0x3) != 0)
/*  891 */           throw new Base64DecodingException("decoding.general"); 
/*  892 */         os.write((byte)(b1 << 2 | b2 >> 4));
/*  893 */         os.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
/*      */       } else {
/*  895 */         throw new Base64DecodingException("decoding.general");
/*      */       } 
/*      */     } else {
/*      */       
/*  899 */       os.write((byte)(b1 << 2 | b2 >> 4));
/*  900 */       os.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
/*  901 */       os.write((byte)(b3 << 6 | b4));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final void decode(InputStream is, OutputStream os) throws Base64DecodingException, IOException {
/*  917 */     byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
/*      */     
/*  919 */     int index = 0;
/*  920 */     byte[] data = new byte[4];
/*      */     
/*      */     int read;
/*  923 */     while ((read = is.read()) > 0) {
/*  924 */       byte readed = (byte)read;
/*  925 */       if (isWhiteSpace(readed)) {
/*      */         continue;
/*      */       }
/*  928 */       if (isPad(readed)) {
/*  929 */         data[index++] = readed;
/*  930 */         if (index == 3) {
/*  931 */           data[index++] = (byte)is.read();
/*      */         }
/*      */         
/*      */         break;
/*      */       } 
/*  936 */       data[index++] = readed; if (readed == -1) {
/*  937 */         throw new Base64DecodingException("decoding.general");
/*      */       }
/*      */       
/*  940 */       if (index != 4) {
/*      */         continue;
/*      */       }
/*  943 */       index = 0;
/*  944 */       b1 = base64Alphabet[data[0]];
/*  945 */       b2 = base64Alphabet[data[1]];
/*  946 */       b3 = base64Alphabet[data[2]];
/*  947 */       b4 = base64Alphabet[data[3]];
/*      */       
/*  949 */       os.write((byte)(b1 << 2 | b2 >> 4));
/*  950 */       os.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
/*  951 */       os.write((byte)(b3 << 6 | b4));
/*      */     } 
/*      */ 
/*      */     
/*  955 */     byte d1 = data[0], d2 = data[1], d3 = data[2], d4 = data[3];
/*  956 */     b1 = base64Alphabet[d1];
/*  957 */     b2 = base64Alphabet[d2];
/*  958 */     b3 = base64Alphabet[d3];
/*  959 */     b4 = base64Alphabet[d4];
/*  960 */     if (b3 == -1 || b4 == -1) {
/*      */       
/*  962 */       if (isPad(d3) && isPad(d4)) {
/*  963 */         if ((b2 & 0xF) != 0)
/*  964 */           throw new Base64DecodingException("decoding.general"); 
/*  965 */         os.write((byte)(b1 << 2 | b2 >> 4));
/*  966 */       } else if (!isPad(d3) && isPad(d4)) {
/*  967 */         b3 = base64Alphabet[d3];
/*  968 */         if ((b3 & 0x3) != 0)
/*  969 */           throw new Base64DecodingException("decoding.general"); 
/*  970 */         os.write((byte)(b1 << 2 | b2 >> 4));
/*  971 */         os.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
/*      */       } else {
/*  973 */         throw new Base64DecodingException("decoding.general");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  978 */       os.write((byte)(b1 << 2 | b2 >> 4));
/*  979 */       os.write((byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF));
/*  980 */       os.write((byte)(b3 << 6 | b4));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static int removeWhiteSpace(byte[] data) {
/*  992 */     if (data == null) {
/*  993 */       return 0;
/*      */     }
/*      */     
/*  996 */     int newSize = 0;
/*  997 */     int len = data.length;
/*  998 */     for (int i = 0; i < len; i++) {
/*  999 */       byte dataS = data[i];
/* 1000 */       if (!isWhiteSpace(dataS))
/* 1001 */         data[newSize++] = dataS; 
/*      */     } 
/* 1003 */     return newSize;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\Base64.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */