/*     */ package com.sun.xml.ws.util.xml;
/*     */ 
/*     */ import java.util.Stack;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContentHandlerToXMLStreamWriter
/*     */   extends DefaultHandler
/*     */ {
/*     */   private final XMLStreamWriter staxWriter;
/*     */   private final Stack prefixBindings;
/*     */   
/*     */   public ContentHandlerToXMLStreamWriter(XMLStreamWriter staxCore) {
/*  72 */     this.staxWriter = staxCore;
/*  73 */     this.prefixBindings = new Stack();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {
/*     */     try {
/*  83 */       this.staxWriter.writeEndDocument();
/*  84 */       this.staxWriter.flush();
/*  85 */     } catch (XMLStreamException e) {
/*  86 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/*     */     try {
/*  97 */       this.staxWriter.writeStartDocument();
/*  98 */     } catch (XMLStreamException e) {
/*  99 */       throw new SAXException(e);
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
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/* 112 */       this.staxWriter.writeCharacters(ch, start, length);
/* 113 */     } catch (XMLStreamException e) {
/* 114 */       throw new SAXException(e);
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
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 127 */     characters(ch, start, length);
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
/*     */   public void endPrefixMapping(String prefix) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) throws SAXException {
/*     */     try {
/* 149 */       this.staxWriter.writeEntityRef(name);
/* 150 */     } catch (XMLStreamException e) {
/* 151 */       throw new SAXException(e);
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
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/*     */     try {
/* 177 */       this.staxWriter.writeProcessingInstruction(target, data);
/* 178 */     } catch (XMLStreamException e) {
/* 179 */       throw new SAXException(e);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 194 */     if (prefix == null) {
/* 195 */       prefix = "";
/*     */     }
/*     */     
/* 198 */     if (prefix.equals("xml")) {
/*     */       return;
/*     */     }
/*     */     
/* 202 */     this.prefixBindings.add(prefix);
/* 203 */     this.prefixBindings.add(uri);
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
/*     */   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
/*     */     try {
/* 217 */       this.staxWriter.writeEndElement();
/* 218 */     } catch (XMLStreamException e) {
/* 219 */       throw new SAXException(e);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/*     */     try {
/* 237 */       this.staxWriter.writeStartElement(getPrefix(qName), localName, namespaceURI);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 243 */       while (this.prefixBindings.size() != 0) {
/* 244 */         String uri = this.prefixBindings.pop();
/* 245 */         String prefix = this.prefixBindings.pop();
/* 246 */         if (prefix.length() == 0) {
/* 247 */           this.staxWriter.setDefaultNamespace(uri);
/*     */         } else {
/* 249 */           this.staxWriter.setPrefix(prefix, uri);
/*     */         } 
/*     */ 
/*     */         
/* 253 */         this.staxWriter.writeNamespace(prefix, uri);
/*     */       } 
/*     */       
/* 256 */       writeAttributes(atts);
/* 257 */     } catch (XMLStreamException e) {
/* 258 */       throw new SAXException(e);
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
/*     */   private void writeAttributes(Attributes atts) throws XMLStreamException {
/* 270 */     for (int i = 0; i < atts.getLength(); i++) {
/* 271 */       String prefix = getPrefix(atts.getQName(i));
/* 272 */       if (!prefix.equals("xmlns")) {
/* 273 */         this.staxWriter.writeAttribute(prefix, atts.getURI(i), atts.getLocalName(i), atts.getValue(i));
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getPrefix(String qName) {
/* 290 */     int idx = qName.indexOf(':');
/* 291 */     if (idx == -1) {
/* 292 */       return "";
/*     */     }
/* 294 */     return qName.substring(0, idx);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\xml\ContentHandlerToXMLStreamWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */