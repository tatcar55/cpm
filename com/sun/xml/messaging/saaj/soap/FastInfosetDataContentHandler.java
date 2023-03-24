/*     */ package com.sun.xml.messaging.saaj.soap;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.util.FastInfosetReflection;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.ActivationDataFlavor;
/*     */ import javax.activation.DataContentHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.transform.Source;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FastInfosetDataContentHandler
/*     */   implements DataContentHandler
/*     */ {
/*     */   public static final String STR_SRC = "org.jvnet.fastinfoset.FastInfosetSource";
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/*  69 */     DataFlavor[] flavors = new DataFlavor[1];
/*  70 */     flavors[0] = new ActivationDataFlavor(FastInfosetReflection.getFastInfosetSource_class(), "application/fastinfoset", "Fast Infoset");
/*     */ 
/*     */     
/*  73 */     return flavors;
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
/*     */   public Object getTransferData(DataFlavor flavor, DataSource dataSource) throws IOException {
/*  85 */     if (flavor.getMimeType().startsWith("application/fastinfoset")) {
/*     */       try {
/*  87 */         if (flavor.getRepresentationClass().getName().equals("org.jvnet.fastinfoset.FastInfosetSource")) {
/*  88 */           return FastInfosetReflection.FastInfosetSource_new(dataSource.getInputStream());
/*     */         
/*     */         }
/*     */       }
/*  92 */       catch (Exception e) {
/*  93 */         throw new IOException(e.getMessage());
/*     */       } 
/*     */     }
/*  96 */     return null;
/*     */   }
/*     */   
/*     */   public Object getContent(DataSource dataSource) throws IOException {
/*     */     try {
/* 101 */       return FastInfosetReflection.FastInfosetSource_new(dataSource.getInputStream());
/*     */     
/*     */     }
/* 104 */     catch (Exception e) {
/* 105 */       throw new IOException(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(Object obj, String mimeType, OutputStream os) throws IOException {
/* 117 */     if (!mimeType.equals("application/fastinfoset")) {
/* 118 */       throw new IOException("Invalid content type \"" + mimeType + "\" for FastInfosetDCH");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 123 */       InputStream is = FastInfosetReflection.FastInfosetSource_getInputStream((Source)obj);
/*     */ 
/*     */       
/* 126 */       byte[] buffer = new byte[4096]; int n;
/* 127 */       while ((n = is.read(buffer)) != -1) {
/* 128 */         os.write(buffer, 0, n);
/*     */       }
/*     */     }
/* 131 */     catch (Exception ex) {
/* 132 */       throw new IOException("Error copying FI source to output stream " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\FastInfosetDataContentHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */