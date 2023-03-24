/*     */ package com.sun.xml.wss.impl.misc;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UnsyncByteArrayOutputStream
/*     */   extends ByteArrayOutputStream
/*     */ {
/*  70 */   int size = 4096;
/*     */ 
/*     */   
/*  73 */   byte[] buf = null;
/*  74 */   int pos = 0;
/*     */   
/*     */   public UnsyncByteArrayOutputStream() {
/*  77 */     this.buf = new byte[this.size];
/*     */   }
/*     */   
/*     */   public UnsyncByteArrayOutputStream(int size) {
/*  81 */     this.size = size;
/*  82 */     this.buf = new byte[size];
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] arg0) {
/*  87 */     int newPos = this.pos + arg0.length;
/*  88 */     if (newPos > this.size) {
/*  89 */       expandSize();
/*     */     }
/*  91 */     System.arraycopy(arg0, 0, this.buf, this.pos, arg0.length);
/*  92 */     this.pos = newPos;
/*     */   }
/*     */   
/*     */   public void write(byte[] arg0, int arg1, int arg2) {
/*  96 */     int newPos = this.pos + arg2;
/*  97 */     if (newPos > this.size) {
/*  98 */       expandSize();
/*     */     }
/* 100 */     System.arraycopy(arg0, arg1, this.buf, this.pos, arg2);
/* 101 */     this.pos = newPos;
/*     */   }
/*     */   
/*     */   public void write(int arg0) {
/* 105 */     if (this.pos >= this.size) {
/* 106 */       expandSize();
/*     */     }
/* 108 */     this.buf[this.pos++] = (byte)arg0;
/*     */   }
/*     */   
/*     */   public byte[] toByteArray() {
/* 112 */     byte[] result = new byte[this.pos];
/* 113 */     System.arraycopy(this.buf, 0, result, 0, this.pos);
/* 114 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 119 */     this.pos = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   void expandSize() {
/* 124 */     int newSize = this.size << 2;
/* 125 */     byte[] newBuf = new byte[newSize];
/* 126 */     System.arraycopy(this.buf, 0, newBuf, 0, this.pos);
/* 127 */     this.buf = newBuf;
/* 128 */     this.size = newSize;
/*     */   }
/*     */   
/*     */   public int getLength() {
/* 132 */     return this.pos;
/*     */   }
/*     */   
/*     */   public byte[] getBytes() {
/* 136 */     return this.buf;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\misc\UnsyncByteArrayOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */