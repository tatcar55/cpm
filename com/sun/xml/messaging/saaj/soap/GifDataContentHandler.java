/*     */ package com.sun.xml.messaging.saaj.soap;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Image;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
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
/*     */ public class GifDataContentHandler
/*     */   extends Component
/*     */   implements DataContentHandler
/*     */ {
/*  55 */   private static ActivationDataFlavor myDF = new ActivationDataFlavor(Image.class, "image/gif", "GIF Image");
/*     */ 
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
/*  92 */     InputStream is = ds.getInputStream();
/*  93 */     int pos = 0;
/*     */     
/*  95 */     byte[] buf = new byte[1024];
/*     */     int count;
/*  97 */     while ((count = is.read(buf, pos, buf.length - pos)) != -1) {
/*  98 */       pos += count;
/*  99 */       if (pos >= buf.length) {
/* 100 */         int size = buf.length;
/* 101 */         if (size < 262144) {
/* 102 */           size += size;
/*     */         } else {
/* 104 */           size += 262144;
/* 105 */         }  byte[] tbuf = new byte[size];
/* 106 */         System.arraycopy(buf, 0, tbuf, 0, pos);
/* 107 */         buf = tbuf;
/*     */       } 
/*     */     } 
/* 110 */     Toolkit tk = Toolkit.getDefaultToolkit();
/* 111 */     return tk.createImage(buf, 0, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(Object obj, String type, OutputStream os) throws IOException {
/* 119 */     if (obj != null && !(obj instanceof Image)) {
/* 120 */       throw new IOException("\"" + getDF().getMimeType() + "\" DataContentHandler requires Image object, " + "was given object of type " + obj.getClass().toString());
/*     */     }
/*     */ 
/*     */     
/* 124 */     throw new IOException(getDF().getMimeType() + " encoding not supported");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\GifDataContentHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */