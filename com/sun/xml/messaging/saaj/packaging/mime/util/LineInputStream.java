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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class LineInputStream
/*     */   extends FilterInputStream
/*     */ {
/*  66 */   private char[] lineBuffer = null;
/*     */   
/*     */   public LineInputStream(InputStream in) {
/*  69 */     super(in);
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
/*     */   public String readLine() throws IOException {
/*  84 */     InputStream in = this.in;
/*  85 */     char[] buf = this.lineBuffer;
/*     */     
/*  87 */     if (buf == null) {
/*  88 */       buf = this.lineBuffer = new char[128];
/*     */     }
/*     */     
/*  91 */     int room = buf.length;
/*  92 */     int offset = 0;
/*     */     int c1;
/*  94 */     while ((c1 = in.read()) != -1 && 
/*  95 */       c1 != 10) {
/*     */       
/*  97 */       if (c1 == 13) {
/*     */         
/*  99 */         int c2 = in.read();
/* 100 */         if (c2 == 13)
/* 101 */           c2 = in.read(); 
/* 102 */         if (c2 != 10) {
/*     */           
/* 104 */           if (!(in instanceof PushbackInputStream))
/* 105 */             in = this.in = new PushbackInputStream(in); 
/* 106 */           ((PushbackInputStream)in).unread(c2);
/*     */         } 
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 113 */       if (--room < 0) {
/* 114 */         buf = new char[offset + 128];
/* 115 */         room = buf.length - offset - 1;
/* 116 */         System.arraycopy(this.lineBuffer, 0, buf, 0, offset);
/* 117 */         this.lineBuffer = buf;
/*     */       } 
/* 119 */       buf[offset++] = (char)c1;
/*     */     } 
/*     */     
/* 122 */     if (c1 == -1 && offset == 0) {
/* 123 */       return null;
/*     */     }
/* 125 */     return String.copyValueOf(buf, 0, offset);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\packaging\mim\\util\LineInputStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */