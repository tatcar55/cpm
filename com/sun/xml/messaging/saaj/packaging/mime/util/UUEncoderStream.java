/*     */ package com.sun.xml.messaging.saaj.packaging.mime.util;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UUEncoderStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   private byte[] buffer;
/*  62 */   private int bufsize = 0;
/*     */ 
/*     */   
/*     */   private boolean wrotePrefix = false;
/*     */   
/*     */   protected String name;
/*     */   
/*     */   protected int mode;
/*     */ 
/*     */   
/*     */   public UUEncoderStream(OutputStream out) {
/*  73 */     this(out, "encoder.buf", 644);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUEncoderStream(OutputStream out, String name) {
/*  82 */     this(out, name, 644);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUEncoderStream(OutputStream out, String name, int mode) {
/*  92 */     super(out);
/*  93 */     this.name = name;
/*  94 */     this.mode = mode;
/*  95 */     this.buffer = new byte[45];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNameMode(String name, int mode) {
/* 104 */     this.name = name;
/* 105 */     this.mode = mode;
/*     */   }
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/* 109 */     for (int i = 0; i < len; i++)
/* 110 */       write(b[off + i]); 
/*     */   }
/*     */   
/*     */   public void write(byte[] data) throws IOException {
/* 114 */     write(data, 0, data.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int c) throws IOException {
/* 122 */     this.buffer[this.bufsize++] = (byte)c;
/* 123 */     if (this.bufsize == 45) {
/* 124 */       writePrefix();
/* 125 */       encode();
/* 126 */       this.bufsize = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void flush() throws IOException {
/* 131 */     if (this.bufsize > 0) {
/* 132 */       writePrefix();
/* 133 */       encode();
/*     */     } 
/* 135 */     writeSuffix();
/* 136 */     this.out.flush();
/*     */   }
/*     */   
/*     */   public void close() throws IOException {
/* 140 */     flush();
/* 141 */     this.out.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writePrefix() throws IOException {
/* 148 */     if (!this.wrotePrefix) {
/* 149 */       PrintStream ps = new PrintStream(this.out);
/* 150 */       ps.println("begin " + this.mode + " " + this.name);
/* 151 */       ps.flush();
/* 152 */       this.wrotePrefix = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeSuffix() throws IOException {
/* 161 */     PrintStream ps = new PrintStream(this.out);
/* 162 */     ps.println(" \nend");
/* 163 */     ps.flush();
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
/*     */   private void encode() throws IOException {
/* 178 */     int i = 0;
/*     */ 
/*     */     
/* 181 */     this.out.write((this.bufsize & 0x3F) + 32);
/*     */     
/* 183 */     while (i < this.bufsize) {
/* 184 */       byte b, c, a = this.buffer[i++];
/* 185 */       if (i < this.bufsize) {
/* 186 */         b = this.buffer[i++];
/* 187 */         if (i < this.bufsize) {
/* 188 */           c = this.buffer[i++];
/*     */         } else {
/* 190 */           c = 1;
/*     */         } 
/*     */       } else {
/* 193 */         b = 1;
/* 194 */         c = 1;
/*     */       } 
/*     */       
/* 197 */       int c1 = a >>> 2 & 0x3F;
/* 198 */       int c2 = a << 4 & 0x30 | b >>> 4 & 0xF;
/* 199 */       int c3 = b << 2 & 0x3C | c >>> 6 & 0x3;
/* 200 */       int c4 = c & 0x3F;
/* 201 */       this.out.write(c1 + 32);
/* 202 */       this.out.write(c2 + 32);
/* 203 */       this.out.write(c3 + 32);
/* 204 */       this.out.write(c4 + 32);
/*     */     } 
/*     */     
/* 207 */     this.out.write(10);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mim\\util\UUEncoderStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */