/*     */ package com.sun.xml.messaging.saaj.util;
/*     */ 
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
/*     */ public final class ByteOutputStream
/*     */   extends OutputStream
/*     */ {
/*     */   protected byte[] buf;
/*  70 */   protected int count = 0;
/*     */   
/*     */   public ByteOutputStream() {
/*  73 */     this(1024);
/*     */   }
/*     */   
/*     */   public ByteOutputStream(int size) {
/*  77 */     this.buf = new byte[size];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(InputStream in) throws IOException {
/*  84 */     if (in instanceof java.io.ByteArrayInputStream) {
/*  85 */       int size = in.available();
/*  86 */       ensureCapacity(size);
/*  87 */       this.count += in.read(this.buf, this.count, size);
/*     */       return;
/*     */     } 
/*     */     while (true) {
/*  91 */       int cap = this.buf.length - this.count;
/*  92 */       int sz = in.read(this.buf, this.count, cap);
/*  93 */       if (sz < 0)
/*     */         return; 
/*  95 */       this.count += sz;
/*  96 */       if (cap == sz)
/*     */       {
/*  98 */         ensureCapacity(this.count); } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void write(int b) {
/* 103 */     ensureCapacity(1);
/* 104 */     this.buf[this.count] = (byte)b;
/* 105 */     this.count++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void ensureCapacity(int space) {
/* 112 */     int newcount = space + this.count;
/* 113 */     if (newcount > this.buf.length) {
/* 114 */       byte[] newbuf = new byte[Math.max(this.buf.length << 1, newcount)];
/* 115 */       System.arraycopy(this.buf, 0, newbuf, 0, this.count);
/* 116 */       this.buf = newbuf;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void write(byte[] b, int off, int len) {
/* 121 */     ensureCapacity(len);
/* 122 */     System.arraycopy(b, off, this.buf, this.count, len);
/* 123 */     this.count += len;
/*     */   }
/*     */   
/*     */   public void write(byte[] b) {
/* 127 */     write(b, 0, b.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAsAscii(String s) {
/* 134 */     int len = s.length();
/*     */     
/* 136 */     ensureCapacity(len);
/*     */     
/* 138 */     int ptr = this.count;
/* 139 */     for (int i = 0; i < len; i++)
/* 140 */       this.buf[ptr++] = (byte)s.charAt(i); 
/* 141 */     this.count = ptr;
/*     */   }
/*     */   
/*     */   public void writeTo(OutputStream out) throws IOException {
/* 145 */     out.write(this.buf, 0, this.count);
/*     */   }
/*     */   
/*     */   public void reset() {
/* 149 */     this.count = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] toByteArray() {
/* 160 */     byte[] newbuf = new byte[this.count];
/* 161 */     System.arraycopy(this.buf, 0, newbuf, 0, this.count);
/* 162 */     return newbuf;
/*     */   }
/*     */   
/*     */   public int size() {
/* 166 */     return this.count;
/*     */   }
/*     */   
/*     */   public ByteInputStream newInputStream() {
/* 170 */     return new ByteInputStream(this.buf, this.count);
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
/* 181 */     return new String(this.buf, 0, this.count);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */   
/*     */   public byte[] getBytes() {
/* 188 */     return this.buf;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCount() {
/* 193 */     return this.count;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\ByteOutputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */