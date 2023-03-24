/*     */ package com.sun.xml.bind.v2.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.activation.MimeType;
/*     */ import javax.activation.MimeTypeParseException;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DataSourceSource
/*     */   extends StreamSource
/*     */ {
/*     */   private final DataSource source;
/*     */   private final String charset;
/*     */   private Reader r;
/*     */   private InputStream is;
/*     */   
/*     */   public DataSourceSource(DataHandler dh) throws MimeTypeParseException {
/*  84 */     this(dh.getDataSource());
/*     */   }
/*     */   
/*     */   public DataSourceSource(DataSource source) throws MimeTypeParseException {
/*  88 */     this.source = source;
/*     */     
/*  90 */     String ct = source.getContentType();
/*  91 */     if (ct == null) {
/*  92 */       this.charset = null;
/*     */     } else {
/*  94 */       MimeType mimeType = new MimeType(ct);
/*  95 */       this.charset = mimeType.getParameter("charset");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setReader(Reader reader) {
/* 101 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInputStream(InputStream inputStream) {
/* 106 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Reader getReader() {
/*     */     try {
/* 112 */       if (this.charset == null) return null; 
/* 113 */       if (this.r == null)
/* 114 */         this.r = new InputStreamReader(this.source.getInputStream(), this.charset); 
/* 115 */       return this.r;
/* 116 */     } catch (IOException e) {
/*     */       
/* 118 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getInputStream() {
/*     */     try {
/* 125 */       if (this.charset != null) return null; 
/* 126 */       if (this.is == null)
/* 127 */         this.is = this.source.getInputStream(); 
/* 128 */       return this.is;
/* 129 */     } catch (IOException e) {
/*     */       
/* 131 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public DataSource getDataSource() {
/* 136 */     return this.source;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v\\util\DataSourceSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */