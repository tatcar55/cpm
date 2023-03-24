/*     */ package com.google.zxing.common;
/*     */ 
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
/*     */ public final class BitArray
/*     */   implements Cloneable
/*     */ {
/*     */   private int[] bits;
/*     */   private int size;
/*     */   
/*     */   public BitArray() {
/*  32 */     this.size = 0;
/*  33 */     this.bits = new int[1];
/*     */   }
/*     */   
/*     */   public BitArray(int size) {
/*  37 */     this.size = size;
/*  38 */     this.bits = makeArray(size);
/*     */   }
/*     */ 
/*     */   
/*     */   BitArray(int[] bits, int size) {
/*  43 */     this.bits = bits;
/*  44 */     this.size = size;
/*     */   }
/*     */   
/*     */   public int getSize() {
/*  48 */     return this.size;
/*     */   }
/*     */   
/*     */   public int getSizeInBytes() {
/*  52 */     return (this.size + 7) / 8;
/*     */   }
/*     */   
/*     */   private void ensureCapacity(int size) {
/*  56 */     if (size > this.bits.length * 32) {
/*  57 */       int[] newBits = makeArray(size);
/*  58 */       System.arraycopy(this.bits, 0, newBits, 0, this.bits.length);
/*  59 */       this.bits = newBits;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean get(int i) {
/*  68 */     return ((this.bits[i / 32] & 1 << (i & 0x1F)) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int i) {
/*  77 */     this.bits[i / 32] = this.bits[i / 32] | 1 << (i & 0x1F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flip(int i) {
/*  86 */     this.bits[i / 32] = this.bits[i / 32] ^ 1 << (i & 0x1F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNextSet(int from) {
/*  96 */     if (from >= this.size) {
/*  97 */       return this.size;
/*     */     }
/*  99 */     int bitsOffset = from / 32;
/* 100 */     int currentBits = this.bits[bitsOffset];
/*     */     
/* 102 */     currentBits &= (1 << (from & 0x1F)) - 1 ^ 0xFFFFFFFF;
/* 103 */     while (currentBits == 0) {
/* 104 */       if (++bitsOffset == this.bits.length) {
/* 105 */         return this.size;
/*     */       }
/* 107 */       currentBits = this.bits[bitsOffset];
/*     */     } 
/* 109 */     int result = bitsOffset * 32 + Integer.numberOfTrailingZeros(currentBits);
/* 110 */     return (result > this.size) ? this.size : result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNextUnset(int from) {
/* 119 */     if (from >= this.size) {
/* 120 */       return this.size;
/*     */     }
/* 122 */     int bitsOffset = from / 32;
/* 123 */     int currentBits = this.bits[bitsOffset] ^ 0xFFFFFFFF;
/*     */     
/* 125 */     currentBits &= (1 << (from & 0x1F)) - 1 ^ 0xFFFFFFFF;
/* 126 */     while (currentBits == 0) {
/* 127 */       if (++bitsOffset == this.bits.length) {
/* 128 */         return this.size;
/*     */       }
/* 130 */       currentBits = this.bits[bitsOffset] ^ 0xFFFFFFFF;
/*     */     } 
/* 132 */     int result = bitsOffset * 32 + Integer.numberOfTrailingZeros(currentBits);
/* 133 */     return (result > this.size) ? this.size : result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBulk(int i, int newBits) {
/* 144 */     this.bits[i / 32] = newBits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRange(int start, int end) {
/* 154 */     if (end < start) {
/* 155 */       throw new IllegalArgumentException();
/*     */     }
/* 157 */     if (end == start) {
/*     */       return;
/*     */     }
/* 160 */     end--;
/* 161 */     int firstInt = start / 32;
/* 162 */     int lastInt = end / 32;
/* 163 */     for (int i = firstInt; i <= lastInt; i++) {
/* 164 */       int mask, firstBit = (i > firstInt) ? 0 : (start & 0x1F);
/* 165 */       int lastBit = (i < lastInt) ? 31 : (end & 0x1F);
/*     */       
/* 167 */       if (firstBit == 0 && lastBit == 31) {
/* 168 */         mask = -1;
/*     */       } else {
/* 170 */         mask = 0;
/* 171 */         for (int j = firstBit; j <= lastBit; j++) {
/* 172 */           mask |= 1 << j;
/*     */         }
/*     */       } 
/* 175 */       this.bits[i] = this.bits[i] | mask;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 183 */     int max = this.bits.length;
/* 184 */     for (int i = 0; i < max; i++) {
/* 185 */       this.bits[i] = 0;
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
/*     */   public boolean isRange(int start, int end, boolean value) {
/* 199 */     if (end < start) {
/* 200 */       throw new IllegalArgumentException();
/*     */     }
/* 202 */     if (end == start) {
/* 203 */       return true;
/*     */     }
/* 205 */     end--;
/* 206 */     int firstInt = start / 32;
/* 207 */     int lastInt = end / 32;
/* 208 */     for (int i = firstInt; i <= lastInt; i++) {
/* 209 */       int mask, firstBit = (i > firstInt) ? 0 : (start & 0x1F);
/* 210 */       int lastBit = (i < lastInt) ? 31 : (end & 0x1F);
/*     */       
/* 212 */       if (firstBit == 0 && lastBit == 31) {
/* 213 */         mask = -1;
/*     */       } else {
/* 215 */         mask = 0;
/* 216 */         for (int j = firstBit; j <= lastBit; j++) {
/* 217 */           mask |= 1 << j;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 223 */       if ((this.bits[i] & mask) != (value ? mask : 0)) {
/* 224 */         return false;
/*     */       }
/*     */     } 
/* 227 */     return true;
/*     */   }
/*     */   
/*     */   public void appendBit(boolean bit) {
/* 231 */     ensureCapacity(this.size + 1);
/* 232 */     if (bit) {
/* 233 */       this.bits[this.size / 32] = this.bits[this.size / 32] | 1 << (this.size & 0x1F);
/*     */     }
/* 235 */     this.size++;
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
/*     */   public void appendBits(int value, int numBits) {
/* 247 */     if (numBits < 0 || numBits > 32) {
/* 248 */       throw new IllegalArgumentException("Num bits must be between 0 and 32");
/*     */     }
/* 250 */     ensureCapacity(this.size + numBits);
/* 251 */     for (int numBitsLeft = numBits; numBitsLeft > 0; numBitsLeft--) {
/* 252 */       appendBit(((value >> numBitsLeft - 1 & 0x1) == 1));
/*     */     }
/*     */   }
/*     */   
/*     */   public void appendBitArray(BitArray other) {
/* 257 */     int otherSize = other.size;
/* 258 */     ensureCapacity(this.size + otherSize);
/* 259 */     for (int i = 0; i < otherSize; i++) {
/* 260 */       appendBit(other.get(i));
/*     */     }
/*     */   }
/*     */   
/*     */   public void xor(BitArray other) {
/* 265 */     if (this.bits.length != other.bits.length) {
/* 266 */       throw new IllegalArgumentException("Sizes don't match");
/*     */     }
/* 268 */     for (int i = 0; i < this.bits.length; i++)
/*     */     {
/*     */       
/* 271 */       this.bits[i] = this.bits[i] ^ other.bits[i];
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
/*     */   public void toBytes(int bitOffset, byte[] array, int offset, int numBytes) {
/* 284 */     for (int i = 0; i < numBytes; i++) {
/* 285 */       int theByte = 0;
/* 286 */       for (int j = 0; j < 8; j++) {
/* 287 */         if (get(bitOffset)) {
/* 288 */           theByte |= 1 << 7 - j;
/*     */         }
/* 290 */         bitOffset++;
/*     */       } 
/* 292 */       array[offset + i] = (byte)theByte;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getBitArray() {
/* 301 */     return this.bits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reverse() {
/* 308 */     int[] newBits = new int[this.bits.length];
/*     */     
/* 310 */     int len = (this.size - 1) / 32;
/* 311 */     int oldBitsLen = len + 1;
/* 312 */     for (int i = 0; i < oldBitsLen; i++) {
/* 313 */       long x = this.bits[i];
/* 314 */       x = x >> 1L & 0x55555555L | (x & 0x55555555L) << 1L;
/* 315 */       x = x >> 2L & 0x33333333L | (x & 0x33333333L) << 2L;
/* 316 */       x = x >> 4L & 0xF0F0F0FL | (x & 0xF0F0F0FL) << 4L;
/* 317 */       x = x >> 8L & 0xFF00FFL | (x & 0xFF00FFL) << 8L;
/* 318 */       x = x >> 16L & 0xFFFFL | (x & 0xFFFFL) << 16L;
/* 319 */       newBits[len - i] = (int)x;
/*     */     } 
/*     */     
/* 322 */     if (this.size != oldBitsLen * 32) {
/* 323 */       int leftOffset = oldBitsLen * 32 - this.size;
/* 324 */       int mask = 1;
/* 325 */       for (int j = 0; j < 31 - leftOffset; j++) {
/* 326 */         mask = mask << 1 | 0x1;
/*     */       }
/* 328 */       int currentInt = newBits[0] >> leftOffset & mask;
/* 329 */       for (int k = 1; k < oldBitsLen; k++) {
/* 330 */         int nextInt = newBits[k];
/* 331 */         currentInt |= nextInt << 32 - leftOffset;
/* 332 */         newBits[k - 1] = currentInt;
/* 333 */         currentInt = nextInt >> leftOffset & mask;
/*     */       } 
/* 335 */       newBits[oldBitsLen - 1] = currentInt;
/*     */     } 
/* 337 */     this.bits = newBits;
/*     */   }
/*     */   
/*     */   private static int[] makeArray(int size) {
/* 341 */     return new int[(size + 31) / 32];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 346 */     if (!(o instanceof BitArray)) {
/* 347 */       return false;
/*     */     }
/* 349 */     BitArray other = (BitArray)o;
/* 350 */     return (this.size == other.size && Arrays.equals(this.bits, other.bits));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 355 */     return 31 * this.size + Arrays.hashCode(this.bits);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 360 */     StringBuilder result = new StringBuilder(this.size);
/* 361 */     for (int i = 0; i < this.size; i++) {
/* 362 */       if ((i & 0x7) == 0) {
/* 363 */         result.append(' ');
/*     */       }
/* 365 */       result.append(get(i) ? 88 : 46);
/*     */     } 
/* 367 */     return result.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public BitArray clone() {
/* 372 */     return new BitArray((int[])this.bits.clone(), this.size);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\google\zxing\common\BitArray.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */