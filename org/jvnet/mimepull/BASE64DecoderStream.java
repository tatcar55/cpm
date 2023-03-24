/*     */ package org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class BASE64DecoderStream
/*     */   extends FilterInputStream
/*     */ {
/*  58 */   private byte[] buffer = new byte[3];
/*  59 */   private int bufsize = 0;
/*  60 */   private int index = 0;
/*     */ 
/*     */ 
/*     */   
/*  64 */   private byte[] input_buffer = new byte[8190];
/*  65 */   private int input_pos = 0;
/*  66 */   private int input_len = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean ignoreErrors = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BASE64DecoderStream(InputStream in) {
/*  79 */     super(in);
/*     */     
/*  81 */     this.ignoreErrors = PropUtil.getBooleanSystemProperty("mail.mime.base64.ignoreerrors", false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BASE64DecoderStream(InputStream in, boolean ignoreErrors) {
/*  92 */     super(in);
/*  93 */     this.ignoreErrors = ignoreErrors;
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
/*     */   public int read() throws IOException {
/* 111 */     if (this.index >= this.bufsize) {
/* 112 */       this.bufsize = decode(this.buffer, 0, this.buffer.length);
/* 113 */       if (this.bufsize <= 0) {
/* 114 */         return -1;
/*     */       }
/* 116 */       this.index = 0;
/*     */     } 
/* 118 */     return this.buffer[this.index++] & 0xFF;
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
/*     */   public int read(byte[] buf, int off, int len) throws IOException {
/* 138 */     int off0 = off;
/* 139 */     while (this.index < this.bufsize && len > 0) {
/* 140 */       buf[off++] = this.buffer[this.index++];
/* 141 */       len--;
/*     */     } 
/* 143 */     if (this.index >= this.bufsize) {
/* 144 */       this.bufsize = this.index = 0;
/*     */     }
/*     */     
/* 147 */     int bsize = len / 3 * 3;
/* 148 */     if (bsize > 0) {
/* 149 */       int size = decode(buf, off, bsize);
/* 150 */       off += size;
/* 151 */       len -= size;
/*     */       
/* 153 */       if (size != bsize) {
/* 154 */         if (off == off0) {
/* 155 */           return -1;
/*     */         }
/* 157 */         return off - off0;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 163 */     for (; len > 0; len--) {
/* 164 */       int c = read();
/* 165 */       if (c == -1) {
/*     */         break;
/*     */       }
/* 168 */       buf[off++] = (byte)c;
/*     */     } 
/*     */     
/* 171 */     if (off == off0) {
/* 172 */       return -1;
/*     */     }
/* 174 */     return off - off0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 183 */     long skipped = 0L;
/* 184 */     while (n-- > 0L && read() >= 0) {
/* 185 */       skipped++;
/*     */     }
/* 187 */     return skipped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 196 */     return false;
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
/*     */   public int available() throws IOException {
/* 209 */     return this.in.available() * 3 / 4 + this.bufsize - this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 216 */   private static final char[] pem_array = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 227 */   private static final byte[] pem_convert_array = new byte[256];
/*     */   static {
/*     */     int i;
/* 230 */     for (i = 0; i < 255; i++) {
/* 231 */       pem_convert_array[i] = -1;
/*     */     }
/* 233 */     for (i = 0; i < pem_array.length; i++) {
/* 234 */       pem_convert_array[pem_array[i]] = (byte)i;
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
/*     */   private int decode(byte[] outbuf, int pos, int len) throws IOException {
/* 253 */     int pos0 = pos;
/* 254 */     while (len >= 3) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 260 */       int got = 0;
/* 261 */       int val = 0;
/* 262 */       while (got < 4) {
/* 263 */         int i = getByte();
/* 264 */         if (i == -1 || i == -2) {
/*     */           boolean atEOF;
/* 266 */           if (i == -1) {
/* 267 */             if (got == 0) {
/* 268 */               return pos - pos0;
/*     */             }
/* 270 */             if (!this.ignoreErrors) {
/* 271 */               throw new DecodingException("BASE64Decoder: Error in encoded stream: needed 4 valid base64 characters but only got " + got + " before EOF" + recentChars());
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 277 */             atEOF = true;
/*     */           }
/*     */           else {
/*     */             
/* 281 */             if (got < 2 && !this.ignoreErrors) {
/* 282 */               throw new DecodingException("BASE64Decoder: Error in encoded stream: needed at least 2 valid base64 characters, but only got " + got + " before padding character (=)" + recentChars());
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 291 */             if (got == 0) {
/* 292 */               return pos - pos0;
/*     */             }
/* 294 */             atEOF = false;
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 301 */           int size = got - 1;
/* 302 */           if (size == 0) {
/* 303 */             size = 1;
/*     */           }
/*     */ 
/*     */           
/* 307 */           got++;
/* 308 */           val <<= 6;
/*     */           
/* 310 */           while (got < 4) {
/* 311 */             if (!atEOF) {
/*     */ 
/*     */               
/* 314 */               i = getByte();
/* 315 */               if (i == -1) {
/* 316 */                 if (!this.ignoreErrors) {
/* 317 */                   throw new DecodingException("BASE64Decoder: Error in encoded stream: hit EOF while looking for padding characters (=)" + recentChars());
/*     */ 
/*     */                 
/*     */                 }
/*     */               
/*     */               }
/* 323 */               else if (i != -2 && 
/* 324 */                 !this.ignoreErrors) {
/* 325 */                 throw new DecodingException("BASE64Decoder: Error in encoded stream: found valid base64 character after a padding character (=)" + recentChars());
/*     */               } 
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 333 */             val <<= 6;
/* 334 */             got++;
/*     */           } 
/*     */ 
/*     */           
/* 338 */           val >>= 8;
/* 339 */           if (size == 2) {
/* 340 */             outbuf[pos + 1] = (byte)(val & 0xFF);
/*     */           }
/* 342 */           val >>= 8;
/* 343 */           outbuf[pos] = (byte)(val & 0xFF);
/*     */           
/* 345 */           pos += size;
/* 346 */           return pos - pos0;
/*     */         } 
/*     */         
/* 349 */         val <<= 6;
/* 350 */         got++;
/* 351 */         val |= i;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 356 */       outbuf[pos + 2] = (byte)(val & 0xFF);
/* 357 */       val >>= 8;
/* 358 */       outbuf[pos + 1] = (byte)(val & 0xFF);
/* 359 */       val >>= 8;
/* 360 */       outbuf[pos] = (byte)(val & 0xFF);
/* 361 */       len -= 3;
/* 362 */       pos += 3;
/*     */     } 
/* 364 */     return pos - pos0;
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
/*     */   private int getByte() throws IOException {
/*     */     while (true) {
/* 378 */       if (this.input_pos >= this.input_len) {
/*     */         try {
/* 380 */           this.input_len = this.in.read(this.input_buffer);
/* 381 */         } catch (EOFException ex) {
/* 382 */           return -1;
/*     */         } 
/* 384 */         if (this.input_len <= 0) {
/* 385 */           return -1;
/*     */         }
/* 387 */         this.input_pos = 0;
/*     */       } 
/*     */       
/* 390 */       int c = this.input_buffer[this.input_pos++] & 0xFF;
/*     */       
/* 392 */       if (c == 61) {
/* 393 */         return -2;
/*     */       }
/*     */       
/* 396 */       c = pem_convert_array[c];
/*     */       
/* 398 */       if (c != -1) {
/* 399 */         return c;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String recentChars() {
/* 408 */     StringBuilder errstr = new StringBuilder();
/* 409 */     int nc = (this.input_pos > 10) ? 10 : this.input_pos;
/* 410 */     if (nc > 0) {
/* 411 */       errstr.append(", the ").append(nc).append(" most recent characters were: \"");
/* 412 */       for (int k = this.input_pos - nc; k < this.input_pos; k++) {
/* 413 */         char c = (char)(this.input_buffer[k] & 0xFF);
/* 414 */         switch (c) { case '\r':
/* 415 */             errstr.append("\\r"); break;
/* 416 */           case '\n': errstr.append("\\n"); break;
/* 417 */           case '\t': errstr.append("\\t"); break;
/*     */           default:
/* 419 */             if (c >= ' ' && c < '') {
/* 420 */               errstr.append(c); break;
/*     */             } 
/* 422 */             errstr.append("\\").append(c);
/*     */             break; }
/*     */       
/*     */       } 
/* 426 */       errstr.append("\"");
/*     */     } 
/* 428 */     return errstr.toString();
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
/*     */   public static byte[] decode(byte[] inbuf) {
/* 441 */     int size = inbuf.length / 4 * 3;
/* 442 */     if (size == 0) {
/* 443 */       return inbuf;
/*     */     }
/*     */     
/* 446 */     if (inbuf[inbuf.length - 1] == 61) {
/* 447 */       size--;
/* 448 */       if (inbuf[inbuf.length - 2] == 61) {
/* 449 */         size--;
/*     */       }
/*     */     } 
/* 452 */     byte[] outbuf = new byte[size];
/*     */     
/* 454 */     int inpos = 0, outpos = 0;
/* 455 */     size = inbuf.length;
/* 456 */     while (size > 0) {
/*     */       
/* 458 */       int osize = 3;
/* 459 */       int val = pem_convert_array[inbuf[inpos++] & 0xFF];
/* 460 */       val <<= 6;
/* 461 */       val |= pem_convert_array[inbuf[inpos++] & 0xFF];
/* 462 */       val <<= 6;
/* 463 */       if (inbuf[inpos] != 61) {
/* 464 */         val |= pem_convert_array[inbuf[inpos++] & 0xFF];
/*     */       } else {
/* 466 */         osize--;
/*     */       } 
/* 468 */       val <<= 6;
/* 469 */       if (inbuf[inpos] != 61) {
/* 470 */         val |= pem_convert_array[inbuf[inpos++] & 0xFF];
/*     */       } else {
/* 472 */         osize--;
/*     */       } 
/* 474 */       if (osize > 2) {
/* 475 */         outbuf[outpos + 2] = (byte)(val & 0xFF);
/*     */       }
/* 477 */       val >>= 8;
/* 478 */       if (osize > 1) {
/* 479 */         outbuf[outpos + 1] = (byte)(val & 0xFF);
/*     */       }
/* 481 */       val >>= 8;
/* 482 */       outbuf[outpos] = (byte)(val & 0xFF);
/* 483 */       outpos += osize;
/* 484 */       size -= 4;
/*     */     } 
/* 486 */     return outbuf;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\BASE64DecoderStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */