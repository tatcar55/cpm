/*     */ package org.jvnet.mimepull;
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
/*     */ final class QPDecoderStream
/*     */   extends FilterInputStream
/*     */ {
/*  56 */   private byte[] ba = new byte[2];
/*  57 */   private int spaces = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QPDecoderStream(InputStream in) {
/*  65 */     super(new PushbackInputStream(in, 2));
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
/*     */   public int read() throws IOException {
/*  82 */     if (this.spaces > 0) {
/*     */       
/*  84 */       this.spaces--;
/*  85 */       return 32;
/*     */     } 
/*     */     
/*  88 */     int c = this.in.read();
/*     */     
/*  90 */     if (c == 32) {
/*     */       
/*  92 */       while ((c = this.in.read()) == 32) {
/*  93 */         this.spaces++;
/*     */       }
/*     */       
/*  96 */       if (c == 13 || c == 10 || c == -1) {
/*  97 */         this.spaces = 0;
/*     */       } else {
/*     */         
/* 100 */         ((PushbackInputStream)this.in).unread(c);
/* 101 */         c = 32;
/*     */       } 
/* 103 */       return c;
/*     */     } 
/* 105 */     if (c == 61) {
/*     */       
/* 107 */       int a = this.in.read();
/*     */       
/* 109 */       if (a == 10)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 115 */         return read(); } 
/* 116 */       if (a == 13) {
/*     */         
/* 118 */         int b = this.in.read();
/* 119 */         if (b != 10) {
/* 120 */           ((PushbackInputStream)this.in).unread(b);
/*     */         }
/* 122 */         return read();
/* 123 */       }  if (a == -1)
/*     */       {
/* 125 */         return -1;
/*     */       }
/* 127 */       this.ba[0] = (byte)a;
/* 128 */       this.ba[1] = (byte)this.in.read();
/*     */       try {
/* 130 */         return ASCIIUtility.parseInt(this.ba, 0, 2, 16);
/* 131 */       } catch (NumberFormatException nex) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 139 */         ((PushbackInputStream)this.in).unread(this.ba);
/* 140 */         return c;
/*     */       } 
/*     */     } 
/*     */     
/* 144 */     return c;
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
/*     */   
/*     */   public int read(byte[] buf, int off, int len) throws IOException {
/*     */     int i;
/* 164 */     for (i = 0; i < len; i++) {
/* 165 */       int c; if ((c = read()) == -1) {
/* 166 */         if (i == 0) {
/* 167 */           i = -1;
/*     */         }
/*     */         break;
/*     */       } 
/* 171 */       buf[off + i] = (byte)c;
/*     */     } 
/* 173 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long skip(long n) throws IOException {
/* 181 */     long skipped = 0L;
/* 182 */     while (n-- > 0L && read() >= 0) {
/* 183 */       skipped++;
/*     */     }
/* 185 */     return skipped;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean markSupported() {
/* 194 */     return false;
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
/*     */   public int available() throws IOException {
/* 208 */     return this.in.available();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\QPDecoderStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */