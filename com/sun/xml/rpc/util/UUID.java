/*     */ package com.sun.xml.rpc.util;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.SecureRandom;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class UUID
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4856846361193249489L;
/*     */   private long mostSigBits;
/*     */   private long leastSigBits;
/* 112 */   private transient int version = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 117 */   private transient int variant = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   private transient long timestamp = -1L;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   private transient int sequence = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 132 */   private transient long node = -1L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 138 */   private static SecureRandom numberGenerator = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UUID(byte[] data) {
/* 147 */     if (data.length != 16);
/*     */     
/*     */     int i;
/* 150 */     for (i = 0; i < 8; i++)
/* 151 */       this.mostSigBits = this.mostSigBits << 8L | (data[i] & 0xFF); 
/* 152 */     for (i = 8; i < 16; i++) {
/* 153 */       this.leastSigBits = this.leastSigBits << 8L | (data[i] & 0xFF);
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
/*     */   private UUID(long mostSigBits, long leastSigBits) {
/* 166 */     this.mostSigBits = mostSigBits;
/* 167 */     this.leastSigBits = leastSigBits;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UUID(DataInput in) throws IOException {
/* 178 */     this.mostSigBits = in.readLong();
/* 179 */     this.leastSigBits = in.readLong();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static UUID randomUUID() {
/* 188 */     if (numberGenerator == null) {
/* 189 */       numberGenerator = new SecureRandom();
/*     */     }
/* 191 */     byte[] randomBytes = new byte[16];
/* 192 */     numberGenerator.nextBytes(randomBytes);
/* 193 */     randomBytes[6] = (byte)(randomBytes[6] & 0xF);
/* 194 */     randomBytes[6] = (byte)(randomBytes[6] | 0x40);
/* 195 */     randomBytes[8] = (byte)(randomBytes[8] & 0x3F);
/* 196 */     randomBytes[8] = (byte)(randomBytes[8] | 0x80);
/* 197 */     UUID result = new UUID(randomBytes);
/* 198 */     return new UUID(randomBytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static UUID nameUUIDFromString(String name) {
/*     */     MessageDigest md;
/*     */     byte[] md5Bytes;
/*     */     try {
/* 211 */       md = MessageDigest.getInstance("MD5");
/* 212 */     } catch (NoSuchAlgorithmException nsae) {
/* 213 */       throw new InternalError("MD5 not supported");
/*     */     } 
/*     */     
/*     */     try {
/* 217 */       md5Bytes = md.digest(name.getBytes("8859_1"));
/* 218 */     } catch (UnsupportedEncodingException uee) {
/* 219 */       throw new InternalError("8859_1 not supported");
/*     */     } 
/* 221 */     md5Bytes[6] = (byte)(md5Bytes[6] & 0xF);
/* 222 */     md5Bytes[6] = (byte)(md5Bytes[6] | 0x30);
/* 223 */     md5Bytes[8] = (byte)(md5Bytes[8] & 0x3F);
/* 224 */     md5Bytes[8] = (byte)(md5Bytes[8] | 0x80);
/* 225 */     return new UUID(md5Bytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static UUID nameUUIDFromBytes(byte[] name) {
/*     */     MessageDigest md;
/*     */     try {
/* 238 */       md = MessageDigest.getInstance("MD5");
/* 239 */     } catch (NoSuchAlgorithmException nsae) {
/* 240 */       throw new InternalError("MD5 not supported");
/*     */     } 
/* 242 */     byte[] md5Bytes = md.digest(name);
/* 243 */     md5Bytes[6] = (byte)(md5Bytes[6] & 0xF);
/* 244 */     md5Bytes[6] = (byte)(md5Bytes[6] | 0x30);
/* 245 */     md5Bytes[8] = (byte)(md5Bytes[8] & 0x3F);
/* 246 */     md5Bytes[8] = (byte)(md5Bytes[8] | 0x80);
/* 247 */     return new UUID(md5Bytes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static UUID fromString(String name) {
/* 258 */     String[] components = name.split("-");
/* 259 */     if (components.length != 5)
/* 260 */       throw new IllegalArgumentException("Invalid UUID string: " + name); 
/* 261 */     for (int i = 0; i < 5; i++) {
/* 262 */       components[i] = "0x" + components[i];
/*     */     }
/* 264 */     long mostSigBits = Long.decode(components[0]).longValue();
/* 265 */     mostSigBits <<= 16L;
/* 266 */     mostSigBits |= Long.decode(components[1]).longValue();
/* 267 */     mostSigBits <<= 16L;
/* 268 */     mostSigBits |= Long.decode(components[2]).longValue();
/*     */     
/* 270 */     long leastSigBits = Long.decode(components[3]).longValue();
/* 271 */     leastSigBits <<= 48L;
/* 272 */     leastSigBits |= Long.decode(components[4]).longValue();
/*     */     
/* 274 */     return new UUID(mostSigBits, leastSigBits);
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
/*     */   private int version() {
/* 294 */     if (this.version < 0)
/*     */     {
/* 296 */       this.version = (int)(this.mostSigBits >> 12L & 0xFL);
/*     */     }
/* 298 */     return this.version;
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
/*     */   private int variant() {
/* 316 */     if (this.variant < 0)
/*     */     {
/* 318 */       if (this.leastSigBits >>> 63L == 0L) {
/* 319 */         this.variant = 0;
/* 320 */       } else if (this.leastSigBits >>> 62L == 2L) {
/* 321 */         this.variant = 2;
/*     */       } else {
/* 323 */         this.variant = (int)(this.leastSigBits >>> 61L);
/*     */       } 
/*     */     }
/* 326 */     return this.variant;
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
/*     */   private long timestamp() {
/* 345 */     if (version() != 1) {
/* 346 */       throw new UnsupportedOperationException("Not a time-based UUID");
/*     */     }
/* 348 */     this.timestamp = (this.mostSigBits & 0xFFFL) << 48L;
/* 349 */     this.timestamp |= (this.mostSigBits >> 16L & 0xFFFFL) << 32L;
/* 350 */     this.timestamp |= this.mostSigBits >>> 32L;
/* 351 */     return this.timestamp;
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
/*     */   private int clockSequence() {
/* 370 */     if (version() != 1) {
/* 371 */       throw new UnsupportedOperationException("Not a time-based UUID");
/*     */     }
/* 373 */     this.sequence = (int)((this.leastSigBits & 0x3FFF000000000000L) >>> 48L);
/* 374 */     return this.sequence;
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
/*     */   private long node() {
/* 394 */     if (version() != 1) {
/* 395 */       throw new UnsupportedOperationException("Not a time-based UUID");
/*     */     }
/* 397 */     this.node = this.leastSigBits & 0xFFFFFFFFFFFFL;
/* 398 */     return this.node;
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
/*     */   public String toString() {
/* 428 */     return digits(this.mostSigBits >> 32L, 8) + "-" + digits(this.mostSigBits >> 16L, 4) + "-" + digits(this.mostSigBits, 4) + "-" + digits(this.leastSigBits >> 48L, 4) + "-" + digits(this.leastSigBits, 12);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String digits(long val, int digits) {
/* 437 */     long hi = 1L << digits * 4;
/* 438 */     return Long.toHexString(hi | val & hi - 1L).substring(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 447 */     return (int)(this.mostSigBits >> 32L ^ this.mostSigBits ^ this.leastSigBits >> 32L ^ this.leastSigBits);
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
/*     */   public boolean equals(Object obj) {
/* 464 */     if (!(obj instanceof UUID))
/* 465 */       return false; 
/* 466 */     if (((UUID)obj).variant() != variant())
/* 467 */       return false; 
/* 468 */     UUID id = (UUID)obj;
/* 469 */     return (this.mostSigBits == id.mostSigBits && this.leastSigBits == id.leastSigBits);
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
/*     */   private int compareTo(UUID val) {
/* 491 */     if (val.variant() != variant()) {
/* 492 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/* 495 */     return (this.mostSigBits < val.mostSigBits) ? -1 : ((this.mostSigBits > val.mostSigBits) ? 1 : ((this.leastSigBits < val.leastSigBits) ? -1 : ((this.leastSigBits > val.leastSigBits) ? 1 : 0)));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\UUID.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */