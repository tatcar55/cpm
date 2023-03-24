/*     */ package com.sun.xml.messaging.saaj.packaging.mime.util;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BASE64EncoderStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   private byte[] buffer;
/*  63 */   private int bufsize = 0;
/*  64 */   private int count = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private int bytesPerLine;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BASE64EncoderStream(OutputStream out, int bytesPerLine) {
/*  74 */     super(out);
/*  75 */     this.buffer = new byte[3];
/*  76 */     this.bytesPerLine = bytesPerLine;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BASE64EncoderStream(OutputStream out) {
/*  85 */     this(out, 76);
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
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/*  99 */     for (int i = 0; i < len; i++) {
/* 100 */       write(b[off + i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/* 109 */     write(b, 0, b.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int c) throws IOException {
/* 118 */     this.buffer[this.bufsize++] = (byte)c;
/* 119 */     if (this.bufsize == 3) {
/* 120 */       encode();
/* 121 */       this.bufsize = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 131 */     if (this.bufsize > 0) {
/* 132 */       encode();
/* 133 */       this.bufsize = 0;
/*     */     } 
/* 135 */     this.out.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 143 */     flush();
/* 144 */     this.out.close();
/*     */   }
/*     */ 
/*     */   
/* 148 */   private static final char[] pem_array = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void encode() throws IOException {
/* 162 */     if (this.count + 4 > this.bytesPerLine) {
/* 163 */       this.out.write(13);
/* 164 */       this.out.write(10);
/* 165 */       this.count = 0;
/*     */     } 
/*     */ 
/*     */     
/* 169 */     if (this.bufsize == 1) {
/* 170 */       byte a = this.buffer[0];
/* 171 */       byte b = 0;
/* 172 */       byte c = 0;
/* 173 */       this.out.write(pem_array[a >>> 2 & 0x3F]);
/* 174 */       this.out.write(pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xF)]);
/* 175 */       this.out.write(61);
/* 176 */       this.out.write(61);
/* 177 */     } else if (this.bufsize == 2) {
/* 178 */       byte a = this.buffer[0];
/* 179 */       byte b = this.buffer[1];
/* 180 */       byte c = 0;
/* 181 */       this.out.write(pem_array[a >>> 2 & 0x3F]);
/* 182 */       this.out.write(pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xF)]);
/* 183 */       this.out.write(pem_array[(b << 2 & 0x3C) + (c >>> 6 & 0x3)]);
/* 184 */       this.out.write(61);
/*     */     } else {
/* 186 */       byte a = this.buffer[0];
/* 187 */       byte b = this.buffer[1];
/* 188 */       byte c = this.buffer[2];
/* 189 */       this.out.write(pem_array[a >>> 2 & 0x3F]);
/* 190 */       this.out.write(pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xF)]);
/* 191 */       this.out.write(pem_array[(b << 2 & 0x3C) + (c >>> 6 & 0x3)]);
/* 192 */       this.out.write(pem_array[c & 0x3F]);
/*     */     } 
/*     */ 
/*     */     
/* 196 */     this.count += 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] encode(byte[] inbuf) {
/* 206 */     if (inbuf.length == 0)
/* 207 */       return inbuf; 
/* 208 */     byte[] outbuf = new byte[(inbuf.length + 2) / 3 * 4];
/* 209 */     int inpos = 0, outpos = 0;
/* 210 */     int size = inbuf.length;
/* 211 */     while (size > 0) {
/*     */       
/* 213 */       if (size == 1) {
/* 214 */         byte a = inbuf[inpos++];
/* 215 */         byte b = 0;
/* 216 */         byte c = 0;
/* 217 */         outbuf[outpos++] = (byte)pem_array[a >>> 2 & 0x3F];
/* 218 */         outbuf[outpos++] = (byte)pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xF)];
/*     */         
/* 220 */         outbuf[outpos++] = 61;
/* 221 */         outbuf[outpos++] = 61;
/* 222 */       } else if (size == 2) {
/* 223 */         byte a = inbuf[inpos++];
/* 224 */         byte b = inbuf[inpos++];
/* 225 */         byte c = 0;
/* 226 */         outbuf[outpos++] = (byte)pem_array[a >>> 2 & 0x3F];
/* 227 */         outbuf[outpos++] = (byte)pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xF)];
/*     */         
/* 229 */         outbuf[outpos++] = (byte)pem_array[(b << 2 & 0x3C) + (c >>> 6 & 0x3)];
/*     */         
/* 231 */         outbuf[outpos++] = 61;
/*     */       } else {
/* 233 */         byte a = inbuf[inpos++];
/* 234 */         byte b = inbuf[inpos++];
/* 235 */         byte c = inbuf[inpos++];
/* 236 */         outbuf[outpos++] = (byte)pem_array[a >>> 2 & 0x3F];
/* 237 */         outbuf[outpos++] = (byte)pem_array[(a << 4 & 0x30) + (b >>> 4 & 0xF)];
/*     */         
/* 239 */         outbuf[outpos++] = (byte)pem_array[(b << 2 & 0x3C) + (c >>> 6 & 0x3)];
/*     */         
/* 241 */         outbuf[outpos++] = (byte)pem_array[c & 0x3F];
/*     */       } 
/* 243 */       size -= 3;
/*     */     } 
/* 245 */     return outbuf;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mim\\util\BASE64EncoderStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */