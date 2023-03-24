/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.nio.charset.Charset;
/*     */ import javax.activation.ActivationDataFlavor;
/*     */ import javax.activation.DataContentHandler;
/*     */ import javax.activation.DataSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringDataContentHandler
/*     */   implements DataContentHandler
/*     */ {
/*  58 */   private static final ActivationDataFlavor myDF = new ActivationDataFlavor(String.class, "text/plain", "Text String");
/*     */ 
/*     */   
/*     */   protected ActivationDataFlavor getDF() {
/*  62 */     return myDF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/*  71 */     return new DataFlavor[] { getDF() };
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
/*     */   public Object getTransferData(DataFlavor df, DataSource ds) throws IOException {
/*  85 */     if (getDF().equals(df)) {
/*  86 */       return getContent(ds);
/*     */     }
/*  88 */     return null;
/*     */   }
/*     */   public Object getContent(DataSource ds) throws IOException {
/*     */     InputStreamReader is;
/*  92 */     String enc = null;
/*     */ 
/*     */     
/*     */     try {
/*  96 */       enc = getCharset(ds.getContentType());
/*  97 */       is = new InputStreamReader(ds.getInputStream(), enc);
/*  98 */     } catch (IllegalArgumentException iex) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 107 */       throw new UnsupportedEncodingException(enc);
/*     */     } 
/*     */     
/*     */     try {
/* 111 */       int pos = 0;
/*     */       
/* 113 */       char[] buf = new char[1024];
/*     */       int count;
/* 115 */       while ((count = is.read(buf, pos, buf.length - pos)) != -1) {
/* 116 */         pos += count;
/* 117 */         if (pos >= buf.length) {
/* 118 */           int size = buf.length;
/* 119 */           if (size < 262144) {
/* 120 */             size += size;
/*     */           } else {
/* 122 */             size += 262144;
/* 123 */           }  char[] tbuf = new char[size];
/* 124 */           System.arraycopy(buf, 0, tbuf, 0, pos);
/* 125 */           buf = tbuf;
/*     */         } 
/*     */       } 
/* 128 */       return new String(buf, 0, pos);
/*     */     } finally {
/*     */       try {
/* 131 */         is.close();
/* 132 */       } catch (IOException ex) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(Object obj, String type, OutputStream os) throws IOException {
/*     */     OutputStreamWriter osw;
/* 143 */     if (!(obj instanceof String)) {
/* 144 */       throw new IOException("\"" + getDF().getMimeType() + "\" DataContentHandler requires String object, " + "was given object of type " + obj.getClass().toString());
/*     */     }
/*     */ 
/*     */     
/* 148 */     String enc = null;
/*     */ 
/*     */     
/*     */     try {
/* 152 */       enc = getCharset(type);
/* 153 */       osw = new OutputStreamWriter(os, enc);
/* 154 */     } catch (IllegalArgumentException iex) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 163 */       throw new UnsupportedEncodingException(enc);
/*     */     } 
/*     */     
/* 166 */     String s = (String)obj;
/* 167 */     osw.write(s, 0, s.length());
/* 168 */     osw.flush();
/*     */   }
/*     */   
/*     */   private String getCharset(String type) {
/*     */     try {
/* 173 */       ContentType ct = new ContentType(type);
/* 174 */       String charset = ct.getParameter("charset");
/* 175 */       if (charset == null)
/*     */       {
/* 177 */         charset = "us-ascii";
/*     */       }
/* 179 */       return Charset.forName(charset).name();
/*     */     }
/* 181 */     catch (Exception ex) {
/* 182 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\StringDataContentHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */