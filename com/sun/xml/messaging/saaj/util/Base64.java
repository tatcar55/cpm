/*     */ package com.sun.xml.messaging.saaj.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Base64
/*     */ {
/*     */   private static final int BASELENGTH = 255;
/*     */   private static final int LOOKUPLENGTH = 63;
/*     */   private static final int TWENTYFOURBITGROUP = 24;
/*     */   private static final int EIGHTBIT = 8;
/*     */   private static final int SIXTEENBIT = 16;
/*     */   private static final int SIXBIT = 6;
/*     */   private static final int FOURBYTE = 4;
/*     */   private static final byte PAD = 61;
/*  70 */   private static byte[] base64Alphabet = new byte[255];
/*  71 */   private static byte[] lookUpBase64Alphabet = new byte[63];
/*     */   
/*     */   static {
/*     */     int i;
/*  75 */     for (i = 0; i < 255; i++) {
/*  76 */       base64Alphabet[i] = -1;
/*     */     }
/*  78 */     for (i = 90; i >= 65; i--) {
/*  79 */       base64Alphabet[i] = (byte)(i - 65);
/*     */     }
/*  81 */     for (i = 122; i >= 97; i--) {
/*  82 */       base64Alphabet[i] = (byte)(i - 97 + 26);
/*     */     }
/*     */     
/*  85 */     for (i = 57; i >= 48; i--) {
/*  86 */       base64Alphabet[i] = (byte)(i - 48 + 52);
/*     */     }
/*     */     
/*  89 */     base64Alphabet[43] = 62;
/*  90 */     base64Alphabet[47] = 63;
/*     */     
/*  92 */     for (i = 0; i <= 25; i++)
/*  93 */       lookUpBase64Alphabet[i] = (byte)(65 + i); 
/*     */     int j;
/*  95 */     for (i = 26, j = 0; i <= 51; i++, j++) {
/*  96 */       lookUpBase64Alphabet[i] = (byte)(97 + j);
/*     */     }
/*  98 */     for (i = 52, j = 0; i <= 61; i++, j++) {
/*  99 */       lookUpBase64Alphabet[i] = (byte)(48 + j);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static boolean isBase64(byte octect) {
/* 106 */     return (octect == 61 || base64Alphabet[octect] != -1);
/*     */   }
/*     */ 
/*     */   
/*     */   static boolean isArrayByteBase64(byte[] arrayOctect) {
/* 111 */     int length = arrayOctect.length;
/* 112 */     if (length == 0)
/* 113 */       return false; 
/* 114 */     for (int i = 0; i < length; i++) {
/* 115 */       if (!isBase64(arrayOctect[i]))
/* 116 */         return false; 
/*     */     } 
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] encode(byte[] binaryData) {
/* 128 */     int lengthDataBits = binaryData.length * 8;
/* 129 */     int fewerThan24bits = lengthDataBits % 24;
/* 130 */     int numberTriplets = lengthDataBits / 24;
/* 131 */     byte[] encodedData = null;
/*     */ 
/*     */     
/* 134 */     if (fewerThan24bits != 0) {
/* 135 */       encodedData = new byte[(numberTriplets + 1) * 4];
/*     */     } else {
/* 137 */       encodedData = new byte[numberTriplets * 4];
/*     */     } 
/* 139 */     byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;
/*     */     
/* 141 */     int encodedIndex = 0;
/* 142 */     int dataIndex = 0;
/* 143 */     int i = 0;
/* 144 */     for (i = 0; i < numberTriplets; i++) {
/*     */       
/* 146 */       dataIndex = i * 3;
/* 147 */       b1 = binaryData[dataIndex];
/* 148 */       b2 = binaryData[dataIndex + 1];
/* 149 */       b3 = binaryData[dataIndex + 2];
/*     */       
/* 151 */       l = (byte)(b2 & 0xF);
/* 152 */       k = (byte)(b1 & 0x3);
/*     */       
/* 154 */       encodedIndex = i * 4;
/* 155 */       encodedData[encodedIndex] = lookUpBase64Alphabet[b1 >> 2];
/* 156 */       encodedData[encodedIndex + 1] = lookUpBase64Alphabet[b2 >> 4 | k << 4];
/*     */       
/* 158 */       encodedData[encodedIndex + 2] = lookUpBase64Alphabet[l << 2 | b3 >> 6];
/*     */       
/* 160 */       encodedData[encodedIndex + 3] = lookUpBase64Alphabet[b3 & 0x3F];
/*     */     } 
/*     */ 
/*     */     
/* 164 */     dataIndex = i * 3;
/* 165 */     encodedIndex = i * 4;
/* 166 */     if (fewerThan24bits == 8) {
/* 167 */       b1 = binaryData[dataIndex];
/* 168 */       k = (byte)(b1 & 0x3);
/* 169 */       encodedData[encodedIndex] = lookUpBase64Alphabet[b1 >> 2];
/* 170 */       encodedData[encodedIndex + 1] = lookUpBase64Alphabet[k << 4];
/* 171 */       encodedData[encodedIndex + 2] = 61;
/* 172 */       encodedData[encodedIndex + 3] = 61;
/* 173 */     } else if (fewerThan24bits == 16) {
/*     */       
/* 175 */       b1 = binaryData[dataIndex];
/* 176 */       b2 = binaryData[dataIndex + 1];
/* 177 */       l = (byte)(b2 & 0xF);
/* 178 */       k = (byte)(b1 & 0x3);
/* 179 */       encodedData[encodedIndex] = lookUpBase64Alphabet[b1 >> 2];
/* 180 */       encodedData[encodedIndex + 1] = lookUpBase64Alphabet[b2 >> 4 | k << 4];
/*     */       
/* 182 */       encodedData[encodedIndex + 2] = lookUpBase64Alphabet[l << 2];
/* 183 */       encodedData[encodedIndex + 3] = 61;
/*     */     } 
/* 185 */     return encodedData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decode(byte[] base64Data) {
/* 196 */     int numberQuadruple = base64Data.length / 4;
/* 197 */     byte[] decodedData = null;
/* 198 */     byte b1 = 0, b2 = 0, b3 = 0, b4 = 0, marker0 = 0, marker1 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     int encodedIndex = 0;
/* 204 */     int dataIndex = 0;
/* 205 */     decodedData = new byte[numberQuadruple * 3 + 1];
/*     */     
/* 207 */     for (int i = 0; i < numberQuadruple; i++) {
/* 208 */       dataIndex = i * 4;
/* 209 */       marker0 = base64Data[dataIndex + 2];
/* 210 */       marker1 = base64Data[dataIndex + 3];
/*     */       
/* 212 */       b1 = base64Alphabet[base64Data[dataIndex]];
/* 213 */       b2 = base64Alphabet[base64Data[dataIndex + 1]];
/*     */       
/* 215 */       if (marker0 != 61 && marker1 != 61) {
/* 216 */         b3 = base64Alphabet[marker0];
/* 217 */         b4 = base64Alphabet[marker1];
/*     */         
/* 219 */         decodedData[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
/* 220 */         decodedData[encodedIndex + 1] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/*     */         
/* 222 */         decodedData[encodedIndex + 2] = (byte)(b3 << 6 | b4);
/* 223 */       } else if (marker0 == 61) {
/* 224 */         decodedData[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
/* 225 */         decodedData[encodedIndex + 1] = (byte)((b2 & 0xF) << 4);
/* 226 */         decodedData[encodedIndex + 2] = 0;
/* 227 */       } else if (marker1 == 61) {
/* 228 */         b3 = base64Alphabet[marker0];
/*     */         
/* 230 */         decodedData[encodedIndex] = (byte)(b1 << 2 | b2 >> 4);
/* 231 */         decodedData[encodedIndex + 1] = (byte)((b2 & 0xF) << 4 | b3 >> 2 & 0xF);
/*     */         
/* 233 */         decodedData[encodedIndex + 2] = (byte)(b3 << 6);
/*     */       } 
/* 235 */       encodedIndex += 3;
/*     */     } 
/* 237 */     return decodedData;
/*     */   }
/*     */ 
/*     */   
/* 241 */   static final int[] base64 = new int[] { 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 62, 64, 64, 64, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 64, 64, 64, 64, 64, 64, 64, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 64, 64, 64, 64, 64, 64, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64, 64 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String base64Decode(String orig) {
/* 261 */     char[] chars = orig.toCharArray();
/* 262 */     StringBuffer sb = new StringBuffer();
/* 263 */     int i = 0;
/*     */     
/* 265 */     int shift = 0;
/* 266 */     int acc = 0;
/*     */     
/* 268 */     for (i = 0; i < chars.length; i++) {
/* 269 */       int v = base64[chars[i] & 0xFF];
/*     */       
/* 271 */       if (v >= 64) {
/* 272 */         if (chars[i] != '=')
/* 273 */           System.out.println("Wrong char in base64: " + chars[i]); 
/*     */       } else {
/* 275 */         acc = acc << 6 | v;
/* 276 */         shift += 6;
/* 277 */         if (shift >= 8) {
/* 278 */           shift -= 8;
/* 279 */           sb.append((char)(acc >> shift & 0xFF));
/*     */         } 
/*     */       } 
/*     */     } 
/* 283 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\Base64.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */