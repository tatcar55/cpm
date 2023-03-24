/*     */ package com.sun.xml.ws.transport.tcp.io;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DataInOutUtils
/*     */ {
/*     */   public static int readInt4(InputStream is) throws IOException {
/*  54 */     int value = 0;
/*     */     
/*  56 */     for (int shVal = 0, neeble = 8; (neeble & 0x8) != 0; shVal += 6) {
/*  57 */       int octet = is.read();
/*  58 */       if (octet == -1) {
/*  59 */         throw new EOFException();
/*     */       }
/*  61 */       neeble = octet >> 4;
/*     */       
/*  63 */       value |= (neeble & 0x7) << shVal;
/*  64 */       if ((neeble & 0x8) != 0) {
/*  65 */         neeble = octet & 0xF;
/*  66 */         value |= (neeble & 0x7) << shVal + 3;
/*     */       } 
/*     */     } 
/*     */     
/*  70 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void readInts4(InputStream is, int[] array, int count) throws IOException {
/*  75 */     readInts4(is, array, count, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int readInts4(InputStream is, int[] array, int count, int lowValue) throws IOException {
/*  80 */     int value = 0;
/*  81 */     int octet = 0;
/*  82 */     int readInts = 0;
/*  83 */     int shVal = 0;
/*  84 */     int neeble = 0;
/*  85 */     int neebleNum = 0;
/*     */     
/*  87 */     if (lowValue > 0) {
/*  88 */       octet = lowValue & 0xF;
/*  89 */       neebleNum = 1;
/*     */     } 
/*     */     
/*  92 */     for (; readInts < count; neebleNum++) {
/*  93 */       if (neebleNum % 2 == 0) {
/*  94 */         octet = is.read();
/*  95 */         if (octet == -1) {
/*  96 */           throw new EOFException();
/*     */         }
/*  98 */         neeble = octet >> 4;
/*     */       } else {
/* 100 */         neeble = octet & 0xF;
/*     */       } 
/*     */       
/* 103 */       value |= (neeble & 0x7) << shVal;
/* 104 */       if ((neeble & 0x8) == 0) {
/* 105 */         array[readInts++] = value;
/* 106 */         shVal = 0;
/* 107 */         value = 0;
/*     */       } else {
/* 109 */         shVal += 3;
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     if (neebleNum % 2 != 0) {
/* 114 */       return 0x80 | octet & 0xF;
/*     */     }
/*     */     
/* 117 */     return 0;
/*     */   }
/*     */   
/*     */   public static void readInts4(ByteBuffer buffer, int[] array, int count) throws IOException {
/* 121 */     readInts4(buffer, array, count, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int readInts4(ByteBuffer buffer, int[] array, int count, int lowValue) throws IOException {
/* 126 */     int value = 0;
/* 127 */     int octet = 0;
/* 128 */     int readInts = 0;
/* 129 */     int shVal = 0;
/* 130 */     int neeble = 0;
/* 131 */     int neebleNum = 0;
/*     */     
/* 133 */     if (lowValue > 0) {
/* 134 */       octet = lowValue & 0xF;
/* 135 */       neebleNum = 1;
/*     */     } 
/*     */     
/* 138 */     for (; readInts < count; neebleNum++) {
/* 139 */       if (neebleNum % 2 == 0) {
/* 140 */         if (!buffer.hasRemaining()) {
/* 141 */           throw new EOFException();
/*     */         }
/* 143 */         octet = buffer.get();
/*     */         
/* 145 */         neeble = octet >> 4;
/*     */       } else {
/* 147 */         neeble = octet & 0xF;
/*     */       } 
/*     */       
/* 150 */       value |= (neeble & 0x7) << shVal;
/* 151 */       if ((neeble & 0x8) == 0) {
/* 152 */         array[readInts++] = value;
/* 153 */         shVal = 0;
/* 154 */         value = 0;
/*     */       } else {
/* 156 */         shVal += 3;
/*     */       } 
/*     */     } 
/*     */     
/* 160 */     if (neebleNum % 2 != 0) {
/* 161 */       return 0x80 | octet & 0xF;
/*     */     }
/*     */     
/* 164 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeInt4(OutputStream os, int value) throws IOException {
/*     */     do {
/* 171 */       int nibbleL, nibbleH = value & 0x7;
/* 172 */       value >>>= 3;
/*     */       
/* 174 */       if (value != 0) {
/* 175 */         nibbleH |= 0x8;
/* 176 */         nibbleL = value & 0x7;
/* 177 */         value >>>= 3;
/* 178 */         if (value != 0) {
/* 179 */           nibbleL |= 0x8;
/*     */         }
/*     */       } else {
/* 182 */         nibbleL = 0;
/*     */       } 
/*     */       
/* 185 */       os.write(nibbleL | nibbleH << 4);
/* 186 */     } while (value != 0);
/*     */   }
/*     */   
/*     */   public static int readInt8(InputStream is) throws IOException {
/* 190 */     int value = 0;
/* 191 */     for (int shVal = 0, octet = 128; (octet & 0x80) != 0; shVal += 7) {
/* 192 */       octet = is.read();
/* 193 */       if (octet == -1) {
/* 194 */         throw new EOFException();
/*     */       }
/*     */       
/* 197 */       value |= (octet & 0x7F) << shVal;
/*     */     } 
/*     */     
/* 200 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeInt8(OutputStream os, int value) throws IOException {
/*     */     do {
/* 206 */       int octet = value & 0x7F;
/* 207 */       value >>>= 7;
/*     */       
/* 209 */       if (value != 0) {
/* 210 */         octet |= 0x80;
/*     */       }
/*     */       
/* 213 */       os.write(octet);
/* 214 */     } while (value != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeInt8(ByteBuffer bb, int value) throws IOException {
/*     */     do {
/* 220 */       int octet = value & 0x7F;
/* 221 */       value >>>= 7;
/*     */       
/* 223 */       if (value != 0) {
/* 224 */         octet |= 0x80;
/*     */       }
/*     */       
/* 227 */       bb.put((byte)octet);
/* 228 */     } while (value != 0);
/*     */   }
/*     */   
/*     */   public static int readInt4(ByteBuffer buffer) throws IOException {
/* 232 */     int value = 0;
/*     */     
/* 234 */     for (int shVal = 0, neeble = 8; (neeble & 0x8) != 0; shVal += 6) {
/* 235 */       if (!buffer.hasRemaining()) {
/* 236 */         throw new EOFException();
/*     */       }
/*     */       
/* 239 */       int octet = buffer.get();
/* 240 */       neeble = octet >> 4;
/*     */       
/* 242 */       value |= (neeble & 0x7) << shVal;
/* 243 */       if ((neeble & 0x8) != 0) {
/* 244 */         neeble = octet & 0xF;
/* 245 */         value |= (neeble & 0x7) << shVal + 3;
/*     */       } 
/*     */     } 
/*     */     
/* 249 */     return value;
/*     */   }
/*     */   
/*     */   public static void writeInts4(ByteBuffer bb, int... values) throws IOException {
/* 253 */     writeInts4(bb, values, 0, values.length);
/*     */   }
/*     */   
/*     */   public static void writeInts4(ByteBuffer bb, int[] array, int offset, int count) throws IOException {
/* 257 */     int shiftValue = 0;
/* 258 */     for (int i = 0; i < count - 1; i++) {
/* 259 */       int value = array[offset + i];
/* 260 */       shiftValue = writeInt4(bb, value, shiftValue, false);
/*     */     } 
/*     */     
/* 263 */     if (count > 0) {
/* 264 */       writeInt4(bb, array[offset + count - 1], shiftValue, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeInts4(OutputStream out, int... values) throws IOException {
/* 270 */     writeInts4(out, values, 0, values.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeInts4(OutputStream out, int[] array, int offset, int count) throws IOException {
/* 275 */     int shiftValue = 0;
/* 276 */     for (int i = 0; i < count - 1; i++) {
/* 277 */       int value = array[offset + i];
/* 278 */       shiftValue = writeInt4(out, value, shiftValue, false);
/*     */     } 
/*     */     
/* 281 */     if (count > 0) {
/* 282 */       writeInt4(out, array[offset + count - 1], shiftValue, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int writeInt4(OutputStream out, int value, int highValue, boolean flush) throws IOException {
/* 290 */     if (highValue > 0) {
/* 291 */       highValue &= 0x70;
/* 292 */       int nibbleL = value & 0x7;
/* 293 */       value >>>= 3;
/* 294 */       if (value != 0) {
/* 295 */         nibbleL |= 0x8;
/*     */       }
/*     */       
/* 298 */       out.write(highValue | nibbleL);
/*     */       
/* 300 */       if (value == 0) {
/* 301 */         return 0;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*     */     do {
/* 307 */       int nibbleL, nibbleH = (value & 0x7) << 4;
/* 308 */       value >>>= 3;
/*     */       
/* 310 */       if (value != 0) {
/* 311 */         nibbleH |= 0x80;
/* 312 */         nibbleL = value & 0x7;
/* 313 */         value >>>= 3;
/* 314 */         if (value != 0) {
/* 315 */           nibbleL |= 0x8;
/*     */         }
/*     */       } else {
/* 318 */         if (!flush) {
/* 319 */           return nibbleH | 0x80;
/*     */         }
/*     */         
/* 322 */         nibbleL = 0;
/*     */       } 
/*     */       
/* 325 */       out.write(nibbleH | nibbleL);
/* 326 */     } while (value != 0);
/*     */     
/* 328 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int writeInt4(ByteBuffer bb, int value, int highValue, boolean flush) throws IOException {
/* 335 */     if (highValue > 0) {
/* 336 */       highValue &= 0x70;
/* 337 */       int nibbleL = value & 0x7;
/* 338 */       value >>>= 3;
/* 339 */       if (value != 0) {
/* 340 */         nibbleL |= 0x8;
/*     */       }
/*     */       
/* 343 */       bb.put((byte)(highValue | nibbleL));
/*     */       
/* 345 */       if (value == 0) {
/* 346 */         return 0;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*     */     do {
/* 352 */       int nibbleL, nibbleH = (value & 0x7) << 4;
/* 353 */       value >>>= 3;
/*     */       
/* 355 */       if (value != 0) {
/* 356 */         nibbleH |= 0x80;
/* 357 */         nibbleL = value & 0x7;
/* 358 */         value >>>= 3;
/* 359 */         if (value != 0) {
/* 360 */           nibbleL |= 0x8;
/*     */         }
/*     */       } else {
/* 363 */         if (!flush) {
/* 364 */           return nibbleH | 0x80;
/*     */         }
/*     */         
/* 367 */         nibbleL = 0;
/*     */       } 
/*     */       
/* 370 */       bb.put((byte)(nibbleH | nibbleL));
/* 371 */     } while (value != 0);
/*     */     
/* 373 */     return 0;
/*     */   }
/*     */   
/*     */   public static int readInt8(ByteBuffer buffer) throws IOException {
/* 377 */     int value = 0;
/* 378 */     for (int shVal = 0, octet = 128; (octet & 0x80) != 0; shVal += 7) {
/* 379 */       if (!buffer.hasRemaining()) {
/* 380 */         throw new EOFException();
/*     */       }
/*     */       
/* 383 */       octet = buffer.get();
/*     */       
/* 385 */       value |= (octet & 0x7F) << shVal;
/*     */     } 
/*     */     
/* 388 */     return value;
/*     */   }
/*     */   
/*     */   public static void readFully(InputStream inputStream, byte[] buffer) throws IOException {
/* 392 */     readFully(inputStream, buffer, 0, buffer.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void readFully(InputStream inputStream, byte[] buffer, int offset, int length) throws IOException {
/* 397 */     int bytesRead = 0;
/* 398 */     while (bytesRead < length) {
/* 399 */       int count = inputStream.read(buffer, offset + bytesRead, length - bytesRead);
/* 400 */       if (count < 0) {
/* 401 */         throw new EOFException();
/*     */       }
/* 403 */       bytesRead += count;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\io\DataInOutUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */