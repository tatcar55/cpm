/*     */ package org.jvnet.staxex;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class Base64Encoder
/*     */ {
/*  47 */   private static final char[] encodeMap = initEncodeMap();
/*     */   
/*     */   private static char[] initEncodeMap() {
/*  50 */     char[] map = new char[64];
/*     */     int i;
/*  52 */     for (i = 0; i < 26; ) { map[i] = (char)(65 + i); i++; }
/*  53 */      for (i = 26; i < 52; ) { map[i] = (char)(97 + i - 26); i++; }
/*  54 */      for (i = 52; i < 62; ) { map[i] = (char)(48 + i - 52); i++; }
/*  55 */      map[62] = '+';
/*  56 */     map[63] = '/';
/*     */     
/*  58 */     return map;
/*     */   }
/*     */   
/*     */   public static char encode(int i) {
/*  62 */     return encodeMap[i & 0x3F];
/*     */   }
/*     */   
/*     */   public static byte encodeByte(int i) {
/*  66 */     return (byte)encodeMap[i & 0x3F];
/*     */   }
/*     */   
/*     */   public static String print(byte[] input, int offset, int len) {
/*  70 */     char[] buf = new char[(len + 2) / 3 * 4];
/*  71 */     int ptr = print(input, offset, len, buf, 0);
/*  72 */     assert ptr == buf.length;
/*  73 */     return new String(buf);
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
/*     */   public static int print(byte[] input, int offset, int len, char[] buf, int ptr) {
/*  86 */     for (int i = offset; i < len; i += 3) {
/*  87 */       switch (len - i) {
/*     */         case 1:
/*  89 */           buf[ptr++] = encode(input[i] >> 2);
/*  90 */           buf[ptr++] = encode((input[i] & 0x3) << 4);
/*  91 */           buf[ptr++] = '=';
/*  92 */           buf[ptr++] = '=';
/*     */           break;
/*     */         case 2:
/*  95 */           buf[ptr++] = encode(input[i] >> 2);
/*  96 */           buf[ptr++] = encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*     */ 
/*     */           
/*  99 */           buf[ptr++] = encode((input[i + 1] & 0xF) << 2);
/* 100 */           buf[ptr++] = '=';
/*     */           break;
/*     */         default:
/* 103 */           buf[ptr++] = encode(input[i] >> 2);
/* 104 */           buf[ptr++] = encode((input[i] & 0x3) << 4 | input[i + 1] >> 4 & 0xF);
/*     */ 
/*     */           
/* 107 */           buf[ptr++] = encode((input[i + 1] & 0xF) << 2 | input[i + 2] >> 6 & 0x3);
/*     */ 
/*     */           
/* 110 */           buf[ptr++] = encode(input[i + 2] & 0x3F);
/*     */           break;
/*     */       } 
/*     */     } 
/* 114 */     return ptr;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\staxex\Base64Encoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */