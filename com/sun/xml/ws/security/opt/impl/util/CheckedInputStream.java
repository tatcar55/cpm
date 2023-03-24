/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.FilterInputStream;
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
/*     */ public class CheckedInputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   int read;
/*     */   boolean isEmpty = false;
/*     */   boolean xmlDecl = false;
/*  57 */   byte[] tmpBytes = new byte[4];
/*  58 */   ByteArrayInputStream tmpIs = null;
/*     */ 
/*     */   
/*     */   public CheckedInputStream(InputStream cin) throws IOException {
/*  62 */     super(cin);
/*  63 */     this.read = cin.read();
/*  64 */     if (this.read == -1) {
/*  65 */       this.isEmpty = true;
/*     */     } else {
/*  67 */       cin.read(this.tmpBytes, 0, 4);
/*  68 */       this.tmpIs = new ByteArrayInputStream(this.tmpBytes);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int read() throws IOException {
/*  73 */     if (this.read != -1) {
/*  74 */       int tmp = this.read;
/*  75 */       this.read = -1;
/*     */       
/*  77 */       if (tmp == 60 && "?xml".equals(new String(this.tmpBytes))) {
/*  78 */         this.xmlDecl = true;
/*  79 */         int c = super.read();
/*  80 */         while (c != 62)
/*     */         {
/*  82 */           c = super.read();
/*     */         }
/*     */       } 
/*     */       
/*  86 */       if (!this.xmlDecl) {
/*  87 */         return tmp;
/*     */       }
/*     */     } 
/*     */     
/*  91 */     if (!this.xmlDecl) {
/*  92 */       int c = this.tmpIs.read();
/*  93 */       if (c != -1) {
/*  94 */         return c;
/*     */       }
/*     */     } 
/*     */     
/*  98 */     return super.read();
/*     */   }
/*     */   
/*     */   public int read(byte[] b) throws IOException {
/* 102 */     return read(b, 0, b.length);
/*     */   }
/*     */   
/*     */   public int read(byte[] b, int off, int len) throws IOException {
/* 106 */     if (this.read != -1) {
/*     */       
/* 108 */       if (this.read == 60 && "?xml".equals(new String(this.tmpBytes))) {
/* 109 */         this.xmlDecl = true;
/* 110 */         int c = super.read();
/* 111 */         while (c != 62)
/*     */         {
/* 113 */           c = super.read();
/*     */         }
/*     */       } 
/*     */       
/* 117 */       int i = 0;
/* 118 */       b[off + i] = (byte)this.read;
/* 119 */       i++;
/* 120 */       len--;
/* 121 */       this.read = -1;
/*     */       
/* 123 */       if (!this.xmlDecl) {
/*     */         
/* 125 */         int c = this.tmpIs.read();
/* 126 */         while (c != -1 && len > 0) {
/* 127 */           b[off + i] = (byte)c;
/* 128 */           i++;
/* 129 */           c = this.tmpIs.read();
/* 130 */           len--;
/*     */         } 
/*     */       } 
/*     */       
/* 134 */       int rb = 0;
/* 135 */       if (len > 0) {
/* 136 */         rb = super.read(b, off + i, len);
/*     */       }
/*     */       
/* 139 */       return rb + i;
/*     */     } 
/* 141 */     return super.read(b, off, len);
/*     */   }
/*     */   public long skip(long n) throws IOException {
/* 144 */     if (this.read != -1) {
/* 145 */       this.read = -1;
/* 146 */       return super.skip(n - 1L) + 1L;
/*     */     } 
/* 148 */     return super.skip(n);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 152 */     return this.isEmpty;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\CheckedInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */