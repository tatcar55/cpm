/*     */ package com.sun.xml.ws.util.xml;
/*     */ 
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
/*     */ import javax.xml.namespace.NamespaceContext;
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
/*     */ 
/*     */ 
/*     */ public class XMLStreamWriterFilter
/*     */   implements XMLStreamWriter, XMLStreamWriterFactory.RecycleAware
/*     */ {
/*     */   protected XMLStreamWriter writer;
/*     */   
/*     */   public XMLStreamWriterFilter(XMLStreamWriter writer) {
/*  63 */     this.writer = writer;
/*     */   }
/*     */   
/*     */   public void close() throws XMLStreamException {
/*  67 */     this.writer.close();
/*     */   }
/*     */   
/*     */   public void flush() throws XMLStreamException {
/*  71 */     this.writer.flush();
/*     */   }
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {
/*  75 */     this.writer.writeEndDocument();
/*     */   }
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/*  79 */     this.writer.writeEndElement();
/*     */   }
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {
/*  83 */     this.writer.writeStartDocument();
/*     */   }
/*     */   
/*     */   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
/*  87 */     this.writer.writeCharacters(text, start, len);
/*     */   }
/*     */   
/*     */   public void setDefaultNamespace(String uri) throws XMLStreamException {
/*  91 */     this.writer.setDefaultNamespace(uri);
/*     */   }
/*     */   
/*     */   public void writeCData(String data) throws XMLStreamException {
/*  95 */     this.writer.writeCData(data);
/*     */   }
/*     */   
/*     */   public void writeCharacters(String text) throws XMLStreamException {
/*  99 */     this.writer.writeCharacters(text);
/*     */   }
/*     */   
/*     */   public void writeComment(String data) throws XMLStreamException {
/* 103 */     this.writer.writeComment(data);
/*     */   }
/*     */   
/*     */   public void writeDTD(String dtd) throws XMLStreamException {
/* 107 */     this.writer.writeDTD(dtd);
/*     */   }
/*     */   
/*     */   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
/* 111 */     this.writer.writeDefaultNamespace(namespaceURI);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String localName) throws XMLStreamException {
/* 115 */     this.writer.writeEmptyElement(localName);
/*     */   }
/*     */   
/*     */   public void writeEntityRef(String name) throws XMLStreamException {
/* 119 */     this.writer.writeEntityRef(name);
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String target) throws XMLStreamException {
/* 123 */     this.writer.writeProcessingInstruction(target);
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String version) throws XMLStreamException {
/* 127 */     this.writer.writeStartDocument(version);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String localName) throws XMLStreamException {
/* 131 */     this.writer.writeStartElement(localName);
/*     */   }
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 135 */     return this.writer.getNamespaceContext();
/*     */   }
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
/* 139 */     this.writer.setNamespaceContext(context);
/*     */   }
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 143 */     return this.writer.getProperty(name);
/*     */   }
/*     */   
/*     */   public String getPrefix(String uri) throws XMLStreamException {
/* 147 */     return this.writer.getPrefix(uri);
/*     */   }
/*     */   
/*     */   public void setPrefix(String prefix, String uri) throws XMLStreamException {
/* 151 */     this.writer.setPrefix(prefix, uri);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String localName, String value) throws XMLStreamException {
/* 155 */     this.writer.writeAttribute(localName, value);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
/* 159 */     this.writer.writeEmptyElement(namespaceURI, localName);
/*     */   }
/*     */   
/*     */   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
/* 163 */     this.writer.writeNamespace(prefix, namespaceURI);
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
/* 167 */     this.writer.writeProcessingInstruction(target, data);
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
/* 171 */     this.writer.writeStartDocument(encoding, version);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 175 */     this.writer.writeStartElement(namespaceURI, localName);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
/* 179 */     this.writer.writeAttribute(namespaceURI, localName, value);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 183 */     this.writer.writeEmptyElement(prefix, localName, namespaceURI);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 187 */     this.writer.writeStartElement(prefix, localName, namespaceURI);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
/* 191 */     this.writer.writeAttribute(prefix, namespaceURI, localName, value);
/*     */   }
/*     */   
/*     */   public void onRecycled() {
/* 195 */     XMLStreamWriterFactory.recycle(this.writer);
/* 196 */     this.writer = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\xml\XMLStreamWriterFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */