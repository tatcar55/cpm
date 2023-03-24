/*     */ package com.sun.xml.fastinfoset.tools;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.QualifiedName;
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.DefaultHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAX2StAXWriter
/*     */   extends DefaultHandler
/*     */   implements LexicalHandler
/*     */ {
/*  33 */   private static final Logger logger = Logger.getLogger(SAX2StAXWriter.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   XMLStreamWriter _writer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   ArrayList _namespaces = new ArrayList();
/*     */   
/*     */   public SAX2StAXWriter(XMLStreamWriter writer) {
/*  46 */     this._writer = writer;
/*     */   }
/*     */   
/*     */   public XMLStreamWriter getWriter() {
/*  50 */     return this._writer;
/*     */   }
/*     */   
/*     */   public void startDocument() throws SAXException {
/*     */     try {
/*  55 */       this._writer.writeStartDocument();
/*     */     }
/*  57 */     catch (XMLStreamException e) {
/*  58 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endDocument() throws SAXException {
/*     */     try {
/*  64 */       this._writer.writeEndDocument();
/*  65 */       this._writer.flush();
/*     */     }
/*  67 */     catch (XMLStreamException e) {
/*  68 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/*  76 */       this._writer.writeCharacters(ch, start, length);
/*     */     }
/*  78 */     catch (XMLStreamException e) {
/*  79 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/*     */     try {
/*  87 */       int k = qName.indexOf(':');
/*  88 */       String prefix = (k > 0) ? qName.substring(0, k) : "";
/*  89 */       this._writer.writeStartElement(prefix, localName, namespaceURI);
/*     */       
/*  91 */       int length = this._namespaces.size(); int i;
/*  92 */       for (i = 0; i < length; i++) {
/*  93 */         QualifiedName nsh = this._namespaces.get(i);
/*  94 */         this._writer.writeNamespace(nsh.prefix, nsh.namespaceName);
/*     */       } 
/*  96 */       this._namespaces.clear();
/*     */       
/*  98 */       length = atts.getLength();
/*  99 */       for (i = 0; i < length; i++) {
/* 100 */         this._writer.writeAttribute(atts.getURI(i), atts.getLocalName(i), atts.getValue(i));
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 105 */     catch (XMLStreamException e) {
/* 106 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
/*     */     try {
/* 114 */       this._writer.writeEndElement();
/*     */     }
/* 116 */     catch (XMLStreamException e) {
/* 117 */       logger.log(Level.FINE, "Exception on endElement", e);
/* 118 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 129 */     this._namespaces.add(new QualifiedName(prefix, uri));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 142 */     characters(ch, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/*     */     try {
/* 149 */       this._writer.writeProcessingInstruction(target, data);
/*     */     }
/* 151 */     catch (XMLStreamException e) {
/* 152 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {}
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/* 166 */       this._writer.writeComment(new String(ch, start, length));
/*     */     }
/* 168 */     catch (XMLStreamException e) {
/* 169 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endCDATA() throws SAXException {}
/*     */   
/*     */   public void endDTD() throws SAXException {}
/*     */   
/*     */   public void endEntity(String name) throws SAXException {}
/*     */   
/*     */   public void startCDATA() throws SAXException {}
/*     */   
/*     */   public void startDTD(String name, String publicId, String systemId) throws SAXException {}
/*     */   
/*     */   public void startEntity(String name) throws SAXException {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\SAX2StAXWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */