/*     */ package com.sun.xml.messaging.saaj.packaging.mime.util;
/*     */ 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BASE64DecoderStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   private byte[] buffer;
/*  63 */   private int bufsize = 0;
/*  64 */   public int read() throws IOException { if (this.index >= this.bufsize) { decode(); if (this.bufsize == 0) return -1;  this.index = 0; }  return this.buffer[this.index++] & 0xFF; } private int index = 0;
/*     */   public int read(byte[] buf, int off, int len) throws IOException { int i; for (i = 0; i < len; i++) {
/*     */       int c; if ((c = read()) == -1) {
/*     */         if (i == 0)
/*     */           i = -1;  break;
/*     */       }  buf[off + i] = (byte)c;
/*     */     } 
/*  71 */     return i; } public BASE64DecoderStream(InputStream in) { super(in);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     this.decode_buffer = new byte[4]; this.buffer = new byte[3]; } public boolean markSupported() { return false; }
/*     */   public int available() throws IOException { return this.in.available() * 3 / 4 + this.bufsize - this.index; }
/* 173 */   private void decode() throws IOException { this.bufsize = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 179 */     int got = 0;
/* 180 */     while (got < 4) {
/* 181 */       int i = this.in.read();
/* 182 */       if (i == -1) {
/* 183 */         if (got == 0)
/*     */           return; 
/* 185 */         throw new IOException("Error in encoded stream, got " + got);
/*     */       } 
/* 187 */       if ((i >= 0 && i < 256 && i == 61) || pem_convert_array[i] != -1) {
/* 188 */         this.decode_buffer[got++] = (byte)i;
/*     */       }
/*     */     } 
/*     */     
/* 192 */     byte a = pem_convert_array[this.decode_buffer[0] & 0xFF];
/* 193 */     byte b = pem_convert_array[this.decode_buffer[1] & 0xFF];
/*     */     
/* 195 */     this.buffer[this.bufsize++] = (byte)(a << 2 & 0xFC | b >>> 4 & 0x3);
/*     */     
/* 197 */     if (this.decode_buffer[2] == 61)
/*     */       return; 
/* 199 */     a = b;
/* 200 */     b = pem_convert_array[this.decode_buffer[2] & 0xFF];
/*     */     
/* 202 */     this.buffer[this.bufsize++] = (byte)(a << 4 & 0xF0 | b >>> 2 & 0xF);
/*     */     
/* 204 */     if (this.decode_buffer[3] == 61)
/*     */       return; 
/* 206 */     a = b;
/* 207 */     b = pem_convert_array[this.decode_buffer[3] & 0xFF];
/*     */     
/* 209 */     this.buffer[this.bufsize++] = (byte)(a << 6 & 0xC0 | b & 0x3F); } private static final char[] pem_array = new char[] { 
/*     */       'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
/*     */       'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
/*     */       'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
/*     */       'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
/*     */       'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
/*     */       'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', 
/*     */       '8', '9', '+', '/' }; private static final byte[] pem_convert_array = new byte[256]; private byte[] decode_buffer; static { int i;
/*     */     for (i = 0; i < 255; i++)
/*     */       pem_convert_array[i] = -1; 
/*     */     for (i = 0; i < pem_array.length; i++)
/*     */       pem_convert_array[pem_array[i]] = (byte)i;  }
/*     */   public static byte[] decode(byte[] inbuf) {
/* 222 */     int size = inbuf.length / 4 * 3;
/* 223 */     if (size == 0) {
/* 224 */       return inbuf;
/*     */     }
/* 226 */     if (inbuf[inbuf.length - 1] == 61) {
/* 227 */       size--;
/* 228 */       if (inbuf[inbuf.length - 2] == 61)
/* 229 */         size--; 
/*     */     } 
/* 231 */     byte[] outbuf = new byte[size];
/*     */     
/* 233 */     int inpos = 0, outpos = 0;
/* 234 */     size = inbuf.length;
/* 235 */     while (size > 0) {
/*     */       
/* 237 */       byte a = pem_convert_array[inbuf[inpos++] & 0xFF];
/* 238 */       byte b = pem_convert_array[inbuf[inpos++] & 0xFF];
/*     */       
/* 240 */       outbuf[outpos++] = (byte)(a << 2 & 0xFC | b >>> 4 & 0x3);
/*     */       
/* 242 */       if (inbuf[inpos] == 61)
/* 243 */         return outbuf; 
/* 244 */       a = b;
/* 245 */       b = pem_convert_array[inbuf[inpos++] & 0xFF];
/*     */       
/* 247 */       outbuf[outpos++] = (byte)(a << 4 & 0xF0 | b >>> 2 & 0xF);
/*     */       
/* 249 */       if (inbuf[inpos] == 61)
/* 250 */         return outbuf; 
/* 251 */       a = b;
/* 252 */       b = pem_convert_array[inbuf[inpos++] & 0xFF];
/*     */       
/* 254 */       outbuf[outpos++] = (byte)(a << 6 & 0xC0 | b & 0x3F);
/* 255 */       size -= 4;
/*     */     } 
/* 257 */     return outbuf;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mim\\util\BASE64DecoderStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */