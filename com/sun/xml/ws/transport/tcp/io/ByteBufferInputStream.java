/*     */ package com.sun.xml.ws.transport.tcp.io;
/*     */ 
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ByteBufferInputStream
/*     */   extends InputStream
/*     */ {
/*     */   private ByteBuffer byteBuffer;
/*     */   
/*     */   public ByteBufferInputStream(ByteBuffer byteBuffer) {
/*  60 */     this.byteBuffer = byteBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByteBuffer(ByteBuffer byteBuffer) {
/*  68 */     this.byteBuffer = byteBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int available() {
/*  77 */     return this.byteBuffer.remaining();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read() {
/* 100 */     if (!this.byteBuffer.hasRemaining()) {
/* 101 */       return -1;
/*     */     }
/*     */     
/* 104 */     return this.byteBuffer.get() & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] b) {
/* 112 */     return read(b, 0, b.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] b, int offset, int length) {
/* 120 */     if (!this.byteBuffer.hasRemaining()) {
/* 121 */       return -1;
/*     */     }
/*     */     
/* 124 */     if (length > available()) {
/* 125 */       length = available();
/*     */     }
/*     */     
/* 128 */     this.byteBuffer.get(b, offset, length);
/*     */     
/* 130 */     return length;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\io\ByteBufferInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */