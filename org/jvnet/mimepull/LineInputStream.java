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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class LineInputStream
/*     */   extends FilterInputStream
/*     */ {
/*  61 */   private char[] lineBuffer = null;
/*  62 */   private static int MAX_INCR = 1048576;
/*     */   
/*     */   public LineInputStream(InputStream in) {
/*  65 */     super(in);
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
/*     */   public String readLine() throws IOException {
/*  81 */     char[] buf = this.lineBuffer;
/*     */     
/*  83 */     if (buf == null) {
/*  84 */       buf = this.lineBuffer = new char[128];
/*     */     }
/*     */ 
/*     */     
/*  88 */     int room = buf.length;
/*  89 */     int offset = 0;
/*     */     int c1;
/*  91 */     while ((c1 = this.in.read()) != -1 && 
/*  92 */       c1 != 10) {
/*     */       
/*  94 */       if (c1 == 13) {
/*     */         
/*  96 */         boolean twoCRs = false;
/*  97 */         if (this.in.markSupported()) {
/*  98 */           this.in.mark(2);
/*     */         }
/* 100 */         int c2 = this.in.read();
/* 101 */         if (c2 == 13) {
/* 102 */           twoCRs = true;
/* 103 */           c2 = this.in.read();
/*     */         } 
/* 105 */         if (c2 != 10) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 117 */           if (this.in.markSupported()) {
/* 118 */             this.in.reset(); break;
/*     */           } 
/* 120 */           if (!(this.in instanceof PushbackInputStream)) {
/* 121 */             this.in = new PushbackInputStream(this.in, 2);
/*     */           }
/* 123 */           if (c2 != -1) {
/* 124 */             ((PushbackInputStream)this.in).unread(c2);
/*     */           }
/* 126 */           if (twoCRs) {
/* 127 */             ((PushbackInputStream)this.in).unread(13);
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */ 
/*     */       
/* 136 */       if (--room < 0) {
/* 137 */         if (buf.length < MAX_INCR) {
/* 138 */           buf = new char[buf.length * 2];
/*     */         } else {
/* 140 */           buf = new char[buf.length + MAX_INCR];
/*     */         } 
/* 142 */         room = buf.length - offset - 1;
/* 143 */         System.arraycopy(this.lineBuffer, 0, buf, 0, offset);
/* 144 */         this.lineBuffer = buf;
/*     */       } 
/* 146 */       buf[offset++] = (char)c1;
/*     */     } 
/*     */     
/* 149 */     if (c1 == -1 && offset == 0) {
/* 150 */       return null;
/*     */     }
/*     */     
/* 153 */     return String.copyValueOf(buf, 0, offset);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\LineInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */