/*     */ package com.sun.xml.txw2.output;
/*     */ 
/*     */ import com.sun.xml.txw2.TxwException;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.io.Writer;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamSerializer
/*     */   implements XmlSerializer
/*     */ {
/*     */   private final SaxSerializer serializer;
/*     */   private final XMLWriter writer;
/*     */   
/*     */   public StreamSerializer(OutputStream out) {
/*  67 */     this(createWriter(out));
/*     */   }
/*     */   
/*     */   public StreamSerializer(OutputStream out, String encoding) throws UnsupportedEncodingException {
/*  71 */     this(createWriter(out, encoding));
/*     */   }
/*     */   
/*     */   public StreamSerializer(Writer out) {
/*  75 */     this(new StreamResult(out));
/*     */   }
/*     */ 
/*     */   
/*     */   public StreamSerializer(StreamResult streamResult) {
/*  80 */     final OutputStream[] autoClose = new OutputStream[1];
/*     */     
/*  82 */     if (streamResult.getWriter() != null) {
/*  83 */       this.writer = createWriter(streamResult.getWriter());
/*  84 */     } else if (streamResult.getOutputStream() != null) {
/*  85 */       this.writer = createWriter(streamResult.getOutputStream());
/*  86 */     } else if (streamResult.getSystemId() != null) {
/*  87 */       String fileURL = streamResult.getSystemId();
/*     */       
/*  89 */       fileURL = convertURL(fileURL);
/*     */       
/*     */       try {
/*  92 */         FileOutputStream fos = new FileOutputStream(fileURL);
/*  93 */         autoClose[0] = fos;
/*  94 */         this.writer = createWriter(fos);
/*  95 */       } catch (IOException e) {
/*  96 */         throw new TxwException(e);
/*     */       } 
/*     */     } else {
/*  99 */       throw new IllegalArgumentException();
/*     */     } 
/*     */     
/* 102 */     this.serializer = new SaxSerializer(this.writer, this.writer, false) {
/*     */         public void endDocument() {
/* 104 */           super.endDocument();
/* 105 */           if (autoClose[0] != null) {
/*     */             try {
/* 107 */               autoClose[0].close();
/* 108 */             } catch (IOException e) {
/* 109 */               throw new TxwException(e);
/*     */             } 
/* 111 */             autoClose[0] = null;
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   private StreamSerializer(XMLWriter writer) {
/* 118 */     this.writer = writer;
/*     */     
/* 120 */     this.serializer = new SaxSerializer(writer, writer, false);
/*     */   }
/*     */   
/*     */   private String convertURL(String url) {
/* 124 */     url = url.replace('\\', '/');
/* 125 */     url = url.replaceAll("//", "/");
/* 126 */     url = url.replaceAll("//", "/");
/* 127 */     if (url.startsWith("file:/"))
/* 128 */       if (url.substring(6).indexOf(":") > 0) {
/* 129 */         url = url.substring(6);
/*     */       } else {
/* 131 */         url = url.substring(5);
/*     */       }  
/* 133 */     return url;
/*     */   }
/*     */ 
/*     */   
/*     */   public void startDocument() {
/* 138 */     this.serializer.startDocument();
/*     */   }
/*     */   
/*     */   public void beginStartTag(String uri, String localName, String prefix) {
/* 142 */     this.serializer.beginStartTag(uri, localName, prefix);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String uri, String localName, String prefix, StringBuilder value) {
/* 146 */     this.serializer.writeAttribute(uri, localName, prefix, value);
/*     */   }
/*     */   
/*     */   public void writeXmlns(String prefix, String uri) {
/* 150 */     this.serializer.writeXmlns(prefix, uri);
/*     */   }
/*     */   
/*     */   public void endStartTag(String uri, String localName, String prefix) {
/* 154 */     this.serializer.endStartTag(uri, localName, prefix);
/*     */   }
/*     */   
/*     */   public void endTag() {
/* 158 */     this.serializer.endTag();
/*     */   }
/*     */   
/*     */   public void text(StringBuilder text) {
/* 162 */     this.serializer.text(text);
/*     */   }
/*     */   
/*     */   public void cdata(StringBuilder text) {
/* 166 */     this.serializer.cdata(text);
/*     */   }
/*     */   
/*     */   public void comment(StringBuilder comment) {
/* 170 */     this.serializer.comment(comment);
/*     */   }
/*     */   
/*     */   public void endDocument() {
/* 174 */     this.serializer.endDocument();
/*     */   }
/*     */   
/*     */   public void flush() {
/* 178 */     this.serializer.flush();
/*     */     try {
/* 180 */       this.writer.flush();
/* 181 */     } catch (IOException e) {
/* 182 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static XMLWriter createWriter(Writer w) {
/* 189 */     DataWriter dw = new DataWriter(new BufferedWriter(w));
/* 190 */     dw.setIndentStep("  ");
/* 191 */     return dw;
/*     */   }
/*     */   
/*     */   private static XMLWriter createWriter(OutputStream os, String encoding) throws UnsupportedEncodingException {
/* 195 */     XMLWriter writer = createWriter(new OutputStreamWriter(os, encoding));
/* 196 */     writer.setEncoding(encoding);
/* 197 */     return writer;
/*     */   }
/*     */   
/*     */   private static XMLWriter createWriter(OutputStream os) {
/*     */     try {
/* 202 */       return createWriter(os, "UTF-8");
/* 203 */     } catch (UnsupportedEncodingException e) {
/*     */       
/* 205 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\output\StreamSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */