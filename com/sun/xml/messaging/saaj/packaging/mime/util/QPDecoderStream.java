/*     */ package com.sun.xml.messaging.saaj.packaging.mime.util;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PushbackInputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QPDecoderStream
/*     */   extends FilterInputStream
/*     */ {
/*  61 */   protected byte[] ba = new byte[2];
/*  62 */   protected int spaces = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QPDecoderStream(InputStream in) {
/*  70 */     super(new PushbackInputStream(in, 2));
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
/*     */   
/*     */   public int read() throws IOException {
/*  86 */     if (this.spaces > 0) {
/*     */       
/*  88 */       this.spaces--;
/*  89 */       return 32;
/*     */     } 
/*     */     
/*  92 */     int c = this.in.read();
/*     */     
/*  94 */     if (c == 32) {
/*     */       
/*  96 */       while ((c = this.in.read()) == 32) {
/*  97 */         this.spaces++;
/*     */       }
/*  99 */       if (c == 13 || c == 10 || c == -1) {
/*     */ 
/*     */         
/* 102 */         this.spaces = 0;
/*     */       } else {
/*     */         
/* 105 */         ((PushbackInputStream)this.in).unread(c);
/* 106 */         c = 32;
/*     */       } 
/* 108 */       return c;
/*     */     } 
/* 110 */     if (c == 61) {
/*     */       
/* 112 */       int a = this.in.read();
/*     */       
/* 114 */       if (a == 10)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 120 */         return read(); } 
/* 121 */       if (a == 13) {
/*     */         
/* 123 */         int b = this.in.read();
/* 124 */         if (b != 10)
/*     */         {
/*     */ 
/*     */           
/* 128 */           ((PushbackInputStream)this.in).unread(b); } 
/* 129 */         return read();
/* 130 */       }  if (a == -1)
/*     */       {
/* 132 */         return -1;
/*     */       }
/* 134 */       this.ba[0] = (byte)a;
/* 135 */       this.ba[1] = (byte)this.in.read();
/*     */       try {
/* 137 */         return ASCIIUtility.parseInt(this.ba, 0, 2, 16);
/* 138 */       } catch (NumberFormatException nex) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 146 */         ((PushbackInputStream)this.in).unread(this.ba);
/* 147 */         return c;
/*     */       } 
/*     */     } 
/*     */     
/* 151 */     return c;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public int read(byte[] buf, int off, int len) throws IOException {
/*     */     int i;
/* 170 */     for (i = 0; i < len; i++) {
/* 171 */       int c; if ((c = read()) == -1) {
/* 172 */         if (i == 0)
/* 173 */           i = -1; 
/*     */         break;
/*     */       } 
/* 176 */       buf[off + i] = (byte)c;
/*     */     } 
/* 178 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 186 */     return false;
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
/*     */   public int available() throws IOException {
/* 199 */     return this.in.available();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mim\\util\QPDecoderStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */