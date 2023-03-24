/*     */ package com.sun.xml.ws.transport.tcp.io;
/*     */ 
/*     */ import com.sun.xml.ws.util.ByteArrayBuffer;
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
/*     */ public class BufferedMessageInputStream
/*     */   extends InputStream
/*     */ {
/*     */   private InputStream inputStream;
/*     */   private boolean isClosed;
/*     */   private boolean isBuffered;
/*     */   private int bufferedSize;
/*     */   
/*     */   public BufferedMessageInputStream(InputStream inputStream) {
/*  59 */     this.inputStream = inputStream;
/*  60 */     this.isBuffered = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int read() throws IOException {
/*  65 */     return this.inputStream.read();
/*     */   }
/*     */   
/*     */   public int read(byte[] b, int offset, int length) throws IOException {
/*  69 */     return this.inputStream.read(b, offset, length);
/*     */   }
/*     */   
/*     */   public void bufferMessage() throws IOException {
/*  73 */     if (!this.isBuffered) {
/*  74 */       ByteArrayBuffer baBuffer = new ByteArrayBuffer();
/*     */       try {
/*  76 */         baBuffer.write(this.inputStream);
/*  77 */         this.inputStream = baBuffer.newInputStream();
/*  78 */         this.bufferedSize = baBuffer.size();
/*  79 */         this.isBuffered = true;
/*     */       } finally {
/*  81 */         baBuffer.close();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public InputStream getSourceInputStream() {
/*  87 */     return this.inputStream;
/*     */   }
/*     */   
/*     */   public int getBufferedSize() {
/*  91 */     if (this.isBuffered) {
/*  92 */       return this.bufferedSize;
/*     */     }
/*     */     
/*  95 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/*  99 */     return this.isClosed;
/*     */   }
/*     */   
/*     */   public boolean isBuffered() {
/* 103 */     return this.isBuffered;
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 107 */     this.isClosed = true;
/* 108 */     this.inputStream.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\transport\tcp\io\BufferedMessageInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */