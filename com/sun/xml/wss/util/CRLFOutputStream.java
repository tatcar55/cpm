/*     */ package com.sun.xml.wss.util;
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
/*     */ public class CRLFOutputStream
/*     */   extends FilterOutputStream
/*     */ {
/*  57 */   protected int lastb = -1;
/*     */   protected boolean atBOL = true;
/*  59 */   private static final byte[] newline = new byte[] { 13, 10 };
/*     */   
/*     */   public CRLFOutputStream(OutputStream os) {
/*  62 */     super(os);
/*     */   }
/*     */   
/*     */   public void write(int b) throws IOException {
/*  66 */     if (b == 13) {
/*  67 */       writeln();
/*  68 */     } else if (b == 10) {
/*  69 */       if (this.lastb != 13)
/*  70 */         writeln(); 
/*     */     } else {
/*  72 */       this.out.write(b);
/*  73 */       this.atBOL = false;
/*     */     } 
/*  75 */     this.lastb = b;
/*     */   }
/*     */   
/*     */   public void write(byte[] b) throws IOException {
/*  79 */     write(b, 0, b.length);
/*     */   }
/*     */   
/*     */   public void write(byte[] b, int off, int len) throws IOException {
/*  83 */     int start = off;
/*     */     
/*  85 */     len += off;
/*  86 */     for (int i = start; i < len; i++) {
/*  87 */       if (b[i] == 13) {
/*  88 */         this.out.write(b, start, i - start);
/*  89 */         writeln();
/*  90 */         start = i + 1;
/*  91 */       } else if (b[i] == 10) {
/*  92 */         if (this.lastb != 13) {
/*  93 */           this.out.write(b, start, i - start);
/*  94 */           writeln();
/*     */         } 
/*  96 */         start = i + 1;
/*     */       } 
/*  98 */       this.lastb = b[i];
/*     */     } 
/* 100 */     if (len - start > 0) {
/* 101 */       this.out.write(b, start, len - start);
/* 102 */       this.atBOL = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeln() throws IOException {
/* 110 */     this.out.write(newline);
/* 111 */     this.atBOL = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\\util\CRLFOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */