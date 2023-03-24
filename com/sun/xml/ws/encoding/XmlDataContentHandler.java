/*     */ package com.sun.xml.ws.encoding;
/*     */ 
/*     */ import com.sun.xml.ws.util.xml.XmlUtil;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.Arrays;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XmlDataContentHandler
/*     */   implements DataContentHandler
/*     */ {
/*     */   private final DataFlavor[] flavors;
/*     */   
/*     */   public XmlDataContentHandler() throws ClassNotFoundException {
/*  70 */     this.flavors = new DataFlavor[3];
/*  71 */     this.flavors[0] = new ActivationDataFlavor(StreamSource.class, "text/xml", "XML");
/*  72 */     this.flavors[1] = new ActivationDataFlavor(StreamSource.class, "application/xml", "XML");
/*  73 */     this.flavors[2] = new ActivationDataFlavor(String.class, "text/xml", "XML String");
/*     */   }
/*     */   
/*     */   public DataFlavor[] getTransferDataFlavors() {
/*  77 */     return Arrays.<DataFlavor>copyOf(this.flavors, this.flavors.length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getTransferData(DataFlavor df, DataSource ds) throws IOException {
/*  83 */     for (DataFlavor aFlavor : this.flavors) {
/*  84 */       if (aFlavor.equals(df)) {
/*  85 */         return getContent(ds);
/*     */       }
/*     */     } 
/*  88 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getContent(DataSource ds) throws IOException {
/*  95 */     String ctStr = ds.getContentType();
/*  96 */     String charset = null;
/*  97 */     if (ctStr != null) {
/*  98 */       ContentType ct = new ContentType(ctStr);
/*  99 */       if (!isXml(ct)) {
/* 100 */         throw new IOException("Cannot convert DataSource with content type \"" + ctStr + "\" to object in XmlDataContentHandler");
/*     */       }
/*     */ 
/*     */       
/* 104 */       charset = ct.getParameter("charset");
/*     */     } 
/* 106 */     return (charset != null) ? new StreamSource(new InputStreamReader(ds.getInputStream()), charset) : new StreamSource(ds.getInputStream());
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
/* 117 */     if (!(obj instanceof DataSource) && !(obj instanceof Source) && !(obj instanceof String)) {
/* 118 */       throw new IOException("Invalid Object type = " + obj.getClass() + ". XmlDataContentHandler can only convert DataSource|Source|String to XML.");
/*     */     }
/*     */ 
/*     */     
/* 122 */     ContentType ct = new ContentType(mimeType);
/* 123 */     if (!isXml(ct)) {
/* 124 */       throw new IOException("Invalid content type \"" + mimeType + "\" for XmlDataContentHandler");
/*     */     }
/*     */ 
/*     */     
/* 128 */     String charset = ct.getParameter("charset");
/* 129 */     if (obj instanceof String) {
/* 130 */       String s = (String)obj;
/* 131 */       if (charset == null) {
/* 132 */         charset = "utf-8";
/*     */       }
/* 134 */       OutputStreamWriter osw = new OutputStreamWriter(os, charset);
/* 135 */       osw.write(s, 0, s.length());
/* 136 */       osw.flush();
/*     */       
/*     */       return;
/*     */     } 
/* 140 */     Source source = (obj instanceof DataSource) ? (Source)getContent((DataSource)obj) : (Source)obj;
/*     */     
/*     */     try {
/* 143 */       Transformer transformer = XmlUtil.newTransformer();
/* 144 */       if (charset != null) {
/* 145 */         transformer.setOutputProperty("encoding", charset);
/*     */       }
/* 147 */       StreamResult result = new StreamResult(os);
/* 148 */       transformer.transform(source, result);
/* 149 */     } catch (Exception ex) {
/* 150 */       throw new IOException("Unable to run the JAXP transformer in XmlDataContentHandler " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isXml(ContentType ct) {
/* 157 */     return (ct.getSubType().equals("xml") && (ct.getPrimaryType().equals("text") || ct.getPrimaryType().equals("application")));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\XmlDataContentHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */