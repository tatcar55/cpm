/*     */ package com.sun.xml.ws.security.opt.impl.util;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamFilter
/*     */   implements XMLStreamWriter
/*     */ {
/*  53 */   protected XMLStreamWriter writer = null;
/*  54 */   protected NamespaceContextEx nsContext = null;
/*     */   protected boolean seenFirstElement = false;
/*  56 */   protected int count = 0;
/*     */   
/*     */   public XMLStreamFilter(XMLStreamWriter writer, NamespaceContextEx nce) throws XMLStreamException {
/*  59 */     this.writer = writer;
/*  60 */     this.nsContext = nce;
/*  61 */     if (this.nsContext == null) {
/*  62 */       throw new XMLStreamException("NamespaceContext cannot be null");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/*  68 */     if (this.count == 0) {
/*  69 */       return (NamespaceContext)this.nsContext;
/*     */     }
/*  71 */     return this.writer.getNamespaceContext();
/*     */   }
/*     */   
/*     */   public void close() throws XMLStreamException {
/*  75 */     this.writer.close();
/*     */   }
/*     */   
/*     */   public void flush() throws XMLStreamException {
/*  79 */     this.writer.flush();
/*     */   }
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {
/*  83 */     this.writer.writeEndDocument();
/*     */   }
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/*  87 */     if (this.count == 0) {
/*     */       return;
/*     */     }
/*  90 */     this.count--;
/*  91 */     this.writer.writeEndElement();
/*     */   }
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {
/*  95 */     this.writer.writeStartDocument();
/*     */   }
/*     */   
/*     */   public void writeCharacters(char[] c, int index, int len) throws XMLStreamException {
/*  99 */     this.writer.writeCharacters(c, index, len);
/*     */   }
/*     */   
/*     */   public void setDefaultNamespace(String string) throws XMLStreamException {
/* 103 */     if (this.count == 0) {
/* 104 */       this.nsContext.add("", string);
/*     */       return;
/*     */     } 
/* 107 */     this.writer.writeCharacters(string);
/*     */   }
/*     */   
/*     */   public void writeCData(String string) throws XMLStreamException {
/* 111 */     this.writer.writeCData(string);
/*     */   }
/*     */   
/*     */   public void writeCharacters(String string) throws XMLStreamException {
/* 115 */     this.writer.writeCharacters(string);
/*     */   }
/*     */   
/*     */   public void writeComment(String string) throws XMLStreamException {
/* 119 */     this.writer.writeComment(string);
/*     */   }
/*     */   
/*     */   public void writeDTD(String string) throws XMLStreamException {
/* 123 */     this.writer.writeDTD(string);
/*     */   }
/*     */   
/*     */   public void writeDefaultNamespace(String string) throws XMLStreamException {
/* 127 */     this.writer.writeDefaultNamespace(string);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String string) throws XMLStreamException {
/* 131 */     if (this.count == 0) {
/* 132 */       this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */     }
/* 134 */     this.writer.writeEmptyElement(string);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEntityRef(String string) throws XMLStreamException {
/* 139 */     this.writer.writeEntityRef(string);
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String string) throws XMLStreamException {
/* 143 */     this.writer.writeProcessingInstruction(string);
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String string) throws XMLStreamException {
/* 147 */     this.writer.writeStartDocument(string);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String string) throws XMLStreamException {
/* 151 */     if (!this.seenFirstElement) {
/* 152 */       this.seenFirstElement = true;
/*     */       return;
/*     */     } 
/* 155 */     this.count++;
/* 156 */     if (this.count == 1) {
/* 157 */       this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */     }
/* 159 */     this.writer.writeStartElement(string);
/*     */   }
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext namespaceContext) throws XMLStreamException {
/* 163 */     this.writer.setNamespaceContext(namespaceContext);
/*     */   }
/*     */   
/*     */   public Object getProperty(String value) throws IllegalArgumentException {
/* 167 */     if ("com.ctc.wstx.outputUnderlyingStream".equals(value) || "http://java.sun.com/xml/stream/properties/outputstream".equals(value))
/*     */     {
/* 169 */       return null;
/*     */     }
/* 171 */     return this.writer.getProperty(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix(String string) throws XMLStreamException {
/* 176 */     return this.writer.getPrefix(string);
/*     */   }
/*     */   
/*     */   public void setPrefix(String string, String string0) throws XMLStreamException {
/* 180 */     this.writer.setPrefix(string, string0);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String string, String string0) throws XMLStreamException {
/* 184 */     if (this.count == 0) {
/*     */       return;
/*     */     }
/* 187 */     this.writer.writeAttribute(string, string0);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String string, String string0) throws XMLStreamException {
/* 191 */     if (this.count == 0) {
/* 192 */       this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */     }
/* 194 */     this.writer.writeEmptyElement(string, string0);
/*     */   }
/*     */   
/*     */   public void writeNamespace(String string, String string0) throws XMLStreamException {
/* 198 */     if (this.count == 0) {
/* 199 */       this.nsContext.add(string, string0);
/*     */       return;
/*     */     } 
/* 202 */     this.writer.writeNamespace(string, string0);
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String string, String string0) throws XMLStreamException {
/* 206 */     this.writer.writeProcessingInstruction(string, string0);
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String string, String string0) throws XMLStreamException {
/* 210 */     this.writer.writeStartDocument(string, string0);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String string, String string0) throws XMLStreamException {
/* 214 */     if (!this.seenFirstElement) {
/* 215 */       this.seenFirstElement = true;
/*     */       return;
/*     */     } 
/* 218 */     this.count++;
/* 219 */     if (this.count == 1) {
/* 220 */       this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */     }
/* 222 */     this.writer.writeStartElement(string, string0);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String string, String string0, String string1) throws XMLStreamException {
/* 226 */     if (this.count == 0) {
/*     */       return;
/*     */     }
/* 229 */     this.writer.writeAttribute(string, string0, string1);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String string, String string0, String string1) throws XMLStreamException {
/* 233 */     if (this.count == 0) {
/* 234 */       this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */     }
/* 236 */     this.writer.writeEmptyElement(string, string0, string1);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String string, String string0, String string1) throws XMLStreamException {
/* 240 */     if (!this.seenFirstElement) {
/* 241 */       this.seenFirstElement = true;
/*     */       return;
/*     */     } 
/* 244 */     this.count++;
/* 245 */     if (this.count == 1) {
/* 246 */       this.writer.setNamespaceContext((NamespaceContext)this.nsContext);
/*     */     }
/* 248 */     this.writer.writeStartElement(string, string0, string1);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String string, String string0, String string1, String string2) throws XMLStreamException {
/* 252 */     if (this.count == 0) {
/*     */       return;
/*     */     }
/* 255 */     this.writer.writeAttribute(string, string0, string1, string2);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\XMLStreamFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */