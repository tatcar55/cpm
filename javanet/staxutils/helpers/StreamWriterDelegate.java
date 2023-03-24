/*     */ package javanet.staxutils.helpers;
/*     */ 
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
/*     */ public abstract class StreamWriterDelegate
/*     */   implements XMLStreamWriter
/*     */ {
/*     */   protected XMLStreamWriter out;
/*     */   
/*     */   protected StreamWriterDelegate(XMLStreamWriter out) {
/*  48 */     this.out = out;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/*  54 */     return this.out.getProperty(name);
/*     */   }
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/*  58 */     return this.out.getNamespaceContext();
/*     */   }
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
/*  62 */     this.out.setNamespaceContext(context);
/*     */   }
/*     */   
/*     */   public void setDefaultNamespace(String uri) throws XMLStreamException {
/*  66 */     this.out.setDefaultNamespace(uri);
/*     */   }
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {
/*  70 */     this.out.writeStartDocument();
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String version) throws XMLStreamException {
/*  74 */     this.out.writeStartDocument(version);
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
/*  78 */     this.out.writeStartDocument(encoding, version);
/*     */   }
/*     */   
/*     */   public void writeDTD(String dtd) throws XMLStreamException {
/*  82 */     this.out.writeDTD(dtd);
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String target) throws XMLStreamException {
/*  86 */     this.out.writeProcessingInstruction(target);
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
/*  90 */     this.out.writeProcessingInstruction(target, data);
/*     */   }
/*     */   
/*     */   public void writeComment(String data) throws XMLStreamException {
/*  94 */     this.out.writeComment(data);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String localName) throws XMLStreamException {
/*  98 */     this.out.writeEmptyElement(localName);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
/* 102 */     this.out.writeEmptyElement(namespaceURI, localName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 107 */     this.out.writeEmptyElement(prefix, localName, namespaceURI);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String localName) throws XMLStreamException {
/* 111 */     this.out.writeStartElement(localName);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 115 */     this.out.writeStartElement(namespaceURI, localName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 120 */     this.out.writeStartElement(prefix, localName, namespaceURI);
/*     */   }
/*     */   
/*     */   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
/* 124 */     this.out.writeDefaultNamespace(namespaceURI);
/*     */   }
/*     */   
/*     */   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
/* 128 */     this.out.writeNamespace(prefix, namespaceURI);
/*     */   }
/*     */   
/*     */   public String getPrefix(String uri) throws XMLStreamException {
/* 132 */     return this.out.getPrefix(uri);
/*     */   }
/*     */   
/*     */   public void setPrefix(String prefix, String uri) throws XMLStreamException {
/* 136 */     this.out.setPrefix(prefix, uri);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String localName, String value) throws XMLStreamException {
/* 140 */     this.out.writeAttribute(localName, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
/* 145 */     this.out.writeAttribute(namespaceURI, localName, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
/* 150 */     this.out.writeAttribute(prefix, namespaceURI, localName, value);
/*     */   }
/*     */   
/*     */   public void writeCharacters(String text) throws XMLStreamException {
/* 154 */     this.out.writeCharacters(text);
/*     */   }
/*     */   
/*     */   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
/* 158 */     this.out.writeCharacters(text, start, len);
/*     */   }
/*     */   
/*     */   public void writeCData(String data) throws XMLStreamException {
/* 162 */     this.out.writeCData(data);
/*     */   }
/*     */   
/*     */   public void writeEntityRef(String name) throws XMLStreamException {
/* 166 */     this.out.writeEntityRef(name);
/*     */   }
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/* 170 */     this.out.writeEndElement();
/*     */   }
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {
/* 174 */     this.out.writeEndDocument();
/*     */   }
/*     */   
/*     */   public void flush() throws XMLStreamException {
/* 178 */     this.out.flush();
/*     */   }
/*     */   
/*     */   public void close() throws XMLStreamException {
/* 182 */     this.out.close();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\helpers\StreamWriterDelegate.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */