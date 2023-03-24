/*     */ package com.sun.xml.messaging.saaj.soap;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.internet.ContentType;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeUtility;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
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
/*     */ public class StringDataContentHandler
/*     */   implements DataContentHandler
/*     */ {
/*  56 */   private static ActivationDataFlavor myDF = new ActivationDataFlavor(String.class, "text/plain", "Text String");
/*     */ 
/*     */ 
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
/*     */   
/*     */   public Object getContent(DataSource ds) throws IOException {
/*  92 */     String enc = null;
/*  93 */     InputStreamReader is = null;
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
/*     */   public void writeTo(Object obj, String type, OutputStream os) throws IOException {
/* 141 */     if (!(obj instanceof String)) {
/* 142 */       throw new IOException("\"" + getDF().getMimeType() + "\" DataContentHandler requires String object, " + "was given object of type " + obj.getClass().toString());
/*     */     }
/*     */ 
/*     */     
/* 146 */     String enc = null;
/* 147 */     OutputStreamWriter osw = null;
/*     */     
/*     */     try {
/* 150 */       enc = getCharset(type);
/* 151 */       osw = new OutputStreamWriter(os, enc);
/* 152 */     } catch (IllegalArgumentException iex) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 161 */       throw new UnsupportedEncodingException(enc);
/*     */     } 
/*     */     
/* 164 */     String s = (String)obj;
/* 165 */     osw.write(s, 0, s.length());
/* 166 */     osw.flush();
/*     */   }
/*     */   
/*     */   private String getCharset(String type) {
/*     */     try {
/* 171 */       ContentType ct = new ContentType(type);
/* 172 */       String charset = ct.getParameter("charset");
/* 173 */       if (charset == null)
/*     */       {
/* 175 */         charset = "us-ascii"; } 
/* 176 */       return MimeUtility.javaCharset(charset);
/* 177 */     } catch (Exception ex) {
/* 178 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\StringDataContentHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */