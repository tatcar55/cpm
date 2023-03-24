/*     */ package com.sun.xml.txw2.output;
/*     */ 
/*     */ import com.sun.xml.txw2.TxwException;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StaxSerializer
/*     */   implements XmlSerializer
/*     */ {
/*     */   private final XMLStreamWriter out;
/*     */   
/*     */   public StaxSerializer(XMLStreamWriter writer) {
/*  60 */     this(writer, true);
/*     */   }
/*     */   
/*     */   public StaxSerializer(XMLStreamWriter writer, boolean indenting) {
/*  64 */     if (indenting)
/*  65 */       writer = new IndentingXMLStreamWriter(writer); 
/*  66 */     this.out = writer;
/*     */   }
/*     */   
/*     */   public void startDocument() {
/*     */     try {
/*  71 */       this.out.writeStartDocument();
/*  72 */     } catch (XMLStreamException e) {
/*  73 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void beginStartTag(String uri, String localName, String prefix) {
/*     */     try {
/*  79 */       this.out.writeStartElement(prefix, localName, uri);
/*  80 */     } catch (XMLStreamException e) {
/*  81 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeAttribute(String uri, String localName, String prefix, StringBuilder value) {
/*     */     try {
/*  87 */       this.out.writeAttribute(prefix, uri, localName, value.toString());
/*  88 */     } catch (XMLStreamException e) {
/*  89 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeXmlns(String prefix, String uri) {
/*     */     try {
/*  95 */       if (prefix.length() == 0) {
/*  96 */         this.out.setDefaultNamespace(uri);
/*     */       } else {
/*  98 */         this.out.setPrefix(prefix, uri);
/*     */       } 
/*     */ 
/*     */       
/* 102 */       this.out.writeNamespace(prefix, uri);
/* 103 */     } catch (XMLStreamException e) {
/* 104 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void endStartTag(String uri, String localName, String prefix) {}
/*     */ 
/*     */   
/*     */   public void endTag() {
/*     */     try {
/* 114 */       this.out.writeEndElement();
/* 115 */     } catch (XMLStreamException e) {
/* 116 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void text(StringBuilder text) {
/*     */     try {
/* 122 */       this.out.writeCharacters(text.toString());
/* 123 */     } catch (XMLStreamException e) {
/* 124 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cdata(StringBuilder text) {
/*     */     try {
/* 130 */       this.out.writeCData(text.toString());
/* 131 */     } catch (XMLStreamException e) {
/* 132 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void comment(StringBuilder comment) {
/*     */     try {
/* 138 */       this.out.writeComment(comment.toString());
/* 139 */     } catch (XMLStreamException e) {
/* 140 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endDocument() {
/*     */     try {
/* 146 */       this.out.writeEndDocument();
/* 147 */       this.out.flush();
/* 148 */     } catch (XMLStreamException e) {
/* 149 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void flush() {
/*     */     try {
/* 155 */       this.out.flush();
/* 156 */     } catch (XMLStreamException e) {
/* 157 */       throw new TxwException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\txw2\output\StaxSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */