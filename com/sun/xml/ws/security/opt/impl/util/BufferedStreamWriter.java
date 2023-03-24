/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import javax.crypto.CipherOutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BufferedStreamWriter
/*     */   extends OutputStream
/*     */ {
/*  57 */   int size = 4096;
/*  58 */   byte[] buf = null;
/*  59 */   int pos = 0;
/*  60 */   CipherOutputStream cos = null;
/*     */   
/*     */   public BufferedStreamWriter(CipherOutputStream cos) {
/*  63 */     this.buf = new byte[this.size];
/*  64 */     this.cos = cos;
/*     */   }
/*     */   public BufferedStreamWriter(CipherOutputStream cos, int size) {
/*  67 */     this.buf = new byte[size];
/*  68 */     this.cos = cos;
/*     */   }
/*     */   public void write(byte[] arg0) throws IOException {
/*  71 */     int newPos = this.pos + arg0.length;
/*  72 */     if (newPos >= this.size) {
/*  73 */       flush();
/*  74 */       System.arraycopy(arg0, 0, this.buf, 0, arg0.length);
/*  75 */       this.pos = arg0.length;
/*     */     } else {
/*  77 */       System.arraycopy(arg0, 0, this.buf, this.pos, arg0.length);
/*  78 */       this.pos = newPos;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void write(byte[] arg0, int arg1, int arg2) throws IOException {
/*  83 */     int newPos = this.pos + arg2;
/*  84 */     if (newPos >= this.size) {
/*  85 */       flush();
/*  86 */       System.arraycopy(arg0, arg1, this.buf, 0, arg2);
/*  87 */       this.pos = arg2;
/*     */     } else {
/*  89 */       System.arraycopy(arg0, arg1, this.buf, this.pos, arg2);
/*  90 */       this.pos = newPos;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void write(int arg0) throws IOException {
/*  95 */     if (this.pos >= this.size) {
/*  96 */       flush();
/*     */     }
/*  98 */     this.buf[this.pos++] = (byte)arg0;
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/* 102 */     this.cos.write(this.buf, 0, this.pos);
/* 103 */     this.pos = 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\BufferedStreamWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */