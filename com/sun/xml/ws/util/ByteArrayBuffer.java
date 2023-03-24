/*     */ package com.sun.xml.ws.util;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteArrayBuffer
/*     */   extends OutputStream
/*     */ {
/*     */   protected byte[] buf;
/*     */   private int count;
/*     */   private static final int CHUNK_SIZE = 4096;
/*     */   
/*     */   public ByteArrayBuffer() {
/*  85 */     this(32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteArrayBuffer(int size) {
/*  96 */     if (size <= 0)
/*  97 */       throw new IllegalArgumentException(); 
/*  98 */     this.buf = new byte[size];
/*     */   }
/*     */   
/*     */   public ByteArrayBuffer(byte[] data) {
/* 102 */     this(data, data.length);
/*     */   }
/*     */   
/*     */   public ByteArrayBuffer(byte[] data, int length) {
/* 106 */     this.buf = data;
/* 107 */     this.count = length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void write(InputStream in) throws IOException {
/*     */     while (true) {
/* 119 */       int cap = this.buf.length - this.count;
/* 120 */       int sz = in.read(this.buf, this.count, cap);
/* 121 */       if (sz < 0)
/* 122 */         return;  this.count += sz;
/*     */ 
/*     */       
/* 125 */       if (cap == sz)
/* 126 */         ensureCapacity(this.buf.length * 2); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void write(int b) {
/* 131 */     int newcount = this.count + 1;
/* 132 */     ensureCapacity(newcount);
/* 133 */     this.buf[this.count] = (byte)b;
/* 134 */     this.count = newcount;
/*     */   }
/*     */   
/*     */   public final void write(byte[] b, int off, int len) {
/* 138 */     int newcount = this.count + len;
/* 139 */     ensureCapacity(newcount);
/* 140 */     System.arraycopy(b, off, this.buf, this.count, len);
/* 141 */     this.count = newcount;
/*     */   }
/*     */   
/*     */   private void ensureCapacity(int newcount) {
/* 145 */     if (newcount > this.buf.length) {
/* 146 */       byte[] newbuf = new byte[Math.max(this.buf.length << 1, newcount)];
/* 147 */       System.arraycopy(this.buf, 0, newbuf, 0, this.count);
/* 148 */       this.buf = newbuf;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void writeTo(OutputStream out) throws IOException {
/* 157 */     int remaining = this.count;
/* 158 */     int off = 0;
/* 159 */     while (remaining > 0) {
/* 160 */       int chunk = (remaining > 4096) ? 4096 : remaining;
/* 161 */       out.write(this.buf, off, chunk);
/* 162 */       remaining -= chunk;
/* 163 */       off += chunk;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void reset() {
/* 168 */     this.count = 0;
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
/*     */   public final byte[] toByteArray() {
/* 182 */     byte[] newbuf = new byte[this.count];
/* 183 */     System.arraycopy(this.buf, 0, newbuf, 0, this.count);
/* 184 */     return newbuf;
/*     */   }
/*     */   
/*     */   public final int size() {
/* 188 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte[] getRawData() {
/* 198 */     return this.buf;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public final InputStream newInputStream() {
/* 208 */     return new ByteArrayInputStream(this.buf, 0, this.count);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final InputStream newInputStream(int start, int length) {
/* 215 */     return new ByteArrayInputStream(this.buf, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 226 */     return new String(this.buf, 0, this.count);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\ByteArrayBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */