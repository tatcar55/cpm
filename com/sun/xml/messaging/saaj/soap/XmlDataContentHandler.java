/*     */ package com.sun.xml.messaging.saaj.soap;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.util.transform.EfficientStreamingTransformer;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringReader;
/*     */ import javax.activation.ActivationDataFlavor;
/*     */ import javax.activation.DataContentHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.stream.StreamResult;
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
/*     */ public class XmlDataContentHandler
/*     */   implements DataContentHandler
/*     */ {
/*     */   public static final String STR_SRC = "javax.xml.transform.stream.StreamSource";
/*  62 */   private static Class streamSourceClass = null;
/*     */   
/*     */   public XmlDataContentHandler() throws ClassNotFoundException {
/*  65 */     if (streamSourceClass == null) {
/*  66 */       streamSourceClass = Class.forName("javax.xml.transform.stream.StreamSource");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/*  75 */     DataFlavor[] flavors = new DataFlavor[2];
/*     */     
/*  77 */     flavors[0] = new ActivationDataFlavor(streamSourceClass, "text/xml", "XML");
/*     */     
/*  79 */     flavors[1] = new ActivationDataFlavor(streamSourceClass, "application/xml", "XML");
/*     */ 
/*     */     
/*  82 */     return flavors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getTransferData(DataFlavor flavor, DataSource dataSource) throws IOException {
/*  93 */     if (flavor.getMimeType().startsWith("text/xml") || flavor.getMimeType().startsWith("application/xml"))
/*     */     {
/*  95 */       if (flavor.getRepresentationClass().getName().equals("javax.xml.transform.stream.StreamSource")) {
/*  96 */         return new StreamSource(dataSource.getInputStream());
/*     */       }
/*     */     }
/*  99 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getContent(DataSource dataSource) throws IOException {
/* 106 */     return new StreamSource(dataSource.getInputStream());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(Object obj, String mimeType, OutputStream os) throws IOException {
/* 116 */     if (!mimeType.startsWith("text/xml") && !mimeType.startsWith("application/xml")) {
/* 117 */       throw new IOException("Invalid content type \"" + mimeType + "\" for XmlDCH");
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 122 */       Transformer transformer = EfficientStreamingTransformer.newTransformer();
/* 123 */       StreamResult result = new StreamResult(os);
/* 124 */       if (obj instanceof DataSource) {
/*     */         
/* 126 */         transformer.transform((Source)getContent((DataSource)obj), result);
/*     */       } else {
/* 128 */         Source src = null;
/* 129 */         if (obj instanceof String) {
/* 130 */           src = new StreamSource(new StringReader((String)obj));
/*     */         } else {
/* 132 */           src = (Source)obj;
/*     */         } 
/* 134 */         transformer.transform(src, result);
/*     */       } 
/* 136 */     } catch (Exception ex) {
/* 137 */       throw new IOException("Unable to run the JAXP transformer on a stream " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\XmlDataContentHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */