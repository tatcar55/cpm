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
/*     */ public class QPEncoderStream
/*     */   extends FilterOutputStream
/*     */ {
/*  61 */   private int count = 0;
/*     */ 
/*     */   
/*     */   private int bytesPerLine;
/*     */ 
/*     */   
/*     */   private boolean gotSpace = false;
/*     */ 
/*     */   
/*     */   private boolean gotCR = false;
/*     */ 
/*     */   
/*     */   public QPEncoderStream(OutputStream out, int bytesPerLine) {
/*  74 */     super(out);
/*     */ 
/*     */     
/*  77 */     this.bytesPerLine = bytesPerLine - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QPEncoderStream(OutputStream out) {
/*  86 */     this(out, 76);
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
/* 100 */     for (int i = 0; i < len; i++) {
/* 101 */       write(b[off + i]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/* 110 */     write(b, 0, b.length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(int c) throws IOException {
/* 119 */     c &= 0xFF;
/* 120 */     if (this.gotSpace) {
/* 121 */       if (c == 13 || c == 10) {
/*     */         
/* 123 */         output(32, true);
/*     */       } else {
/* 125 */         output(32, false);
/* 126 */       }  this.gotSpace = false;
/*     */     } 
/*     */     
/* 129 */     if (c == 13) {
/* 130 */       this.gotCR = true;
/* 131 */       outputCRLF();
/*     */     } else {
/* 133 */       if (c == 10) {
/* 134 */         if (!this.gotCR)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 139 */           outputCRLF(); } 
/* 140 */       } else if (c == 32) {
/* 141 */         this.gotSpace = true;
/* 142 */       } else if (c < 32 || c >= 127 || c == 61) {
/*     */         
/* 144 */         output(c, true);
/*     */       } else {
/* 146 */         output(c, false);
/*     */       } 
/* 148 */       this.gotCR = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws IOException {
/* 158 */     this.out.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 166 */     this.out.close();
/*     */   }
/*     */   
/*     */   private void outputCRLF() throws IOException {
/* 170 */     this.out.write(13);
/* 171 */     this.out.write(10);
/* 172 */     this.count = 0;
/*     */   }
/*     */ 
/*     */   
/* 176 */   private static final char[] hex = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void output(int c, boolean encode) throws IOException {
/* 182 */     if (encode) {
/* 183 */       if ((this.count += 3) > this.bytesPerLine) {
/* 184 */         this.out.write(61);
/* 185 */         this.out.write(13);
/* 186 */         this.out.write(10);
/* 187 */         this.count = 3;
/*     */       } 
/* 189 */       this.out.write(61);
/* 190 */       this.out.write(hex[c >> 4]);
/* 191 */       this.out.write(hex[c & 0xF]);
/*     */     } else {
/* 193 */       if (++this.count > this.bytesPerLine) {
/* 194 */         this.out.write(61);
/* 195 */         this.out.write(13);
/* 196 */         this.out.write(10);
/* 197 */         this.count = 1;
/*     */       } 
/* 199 */       this.out.write(c);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mim\\util\QPEncoderStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */