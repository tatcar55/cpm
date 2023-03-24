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
/*     */ public class TeeInputStream
/*     */   extends InputStream
/*     */ {
/*     */   protected InputStream source;
/*     */   protected OutputStream copySink;
/*     */   
/*     */   public TeeInputStream(InputStream source, OutputStream sink) {
/*  60 */     this.copySink = sink;
/*  61 */     this.source = source;
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/*  65 */     int result = this.source.read();
/*  66 */     this.copySink.write(result);
/*  67 */     return result;
/*     */   }
/*     */   
/*     */   public int available() throws IOException {
/*  71 */     return this.source.available();
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/*  75 */     this.source.close();
/*     */   }
/*     */   
/*     */   public synchronized void mark(int readlimit) {
/*  79 */     this.source.mark(readlimit);
/*     */   }
/*     */   
/*     */   public boolean markSupported() {
/*  83 */     return this.source.markSupported();
/*     */   }
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/*  87 */     int result = this.source.read(b, off, len);
/*  88 */     this.copySink.write(b, off, len);
/*  89 */     return result;
/*     */   }
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/*  93 */     int result = this.source.read(b);
/*  94 */     this.copySink.write(b);
/*  95 */     return result;
/*     */   }
/*     */   
/*     */   public synchronized void reset() throws IOException {
/*  99 */     this.source.reset();
/*     */   }
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 103 */     return this.source.skip(n);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saa\\util\TeeInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */