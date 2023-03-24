/*     */ package com.sun.xml.wss.saml.internal.saml11.jaxb10.impl.runtime;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContentHandlerAdaptor
/*     */   implements ContentHandler
/*     */ {
/*  27 */   private final ArrayList prefixMap = new ArrayList();
/*     */ 
/*     */   
/*     */   private final XMLSerializer serializer;
/*     */   
/*  32 */   private final StringBuffer text = new StringBuffer();
/*     */ 
/*     */   
/*     */   public ContentHandlerAdaptor(XMLSerializer _serializer) {
/*  36 */     this.serializer = _serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {
/*  42 */     this.prefixMap.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {}
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/*  49 */     this.prefixMap.add(prefix);
/*  50 */     this.prefixMap.add(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/*  59 */     flushText();
/*     */     
/*  61 */     int len = atts.getLength();
/*     */     
/*  63 */     this.serializer.startElement(namespaceURI, localName);
/*     */     int i;
/*  65 */     for (i = 0; i < len; i++) {
/*  66 */       String qname = atts.getQName(i);
/*  67 */       int idx = qname.indexOf(':');
/*  68 */       String prefix = (idx == -1) ? qname : qname.substring(0, idx);
/*     */       
/*  70 */       this.serializer.getNamespaceContext().declareNamespace(atts.getURI(i), prefix, true);
/*     */     } 
/*     */     
/*  73 */     for (i = 0; i < this.prefixMap.size(); i += 2) {
/*  74 */       String prefix = this.prefixMap.get(i);
/*  75 */       this.serializer.getNamespaceContext().declareNamespace(this.prefixMap.get(i + 1), prefix, (prefix.length() != 0));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     this.serializer.endNamespaceDecls();
/*     */     
/*  83 */     for (i = 0; i < len; i++) {
/*  84 */       this.serializer.startAttribute(atts.getURI(i), atts.getLocalName(i));
/*  85 */       this.serializer.text(atts.getValue(i), null);
/*  86 */       this.serializer.endAttribute();
/*     */     } 
/*  88 */     this.prefixMap.clear();
/*  89 */     this.serializer.endAttributes();
/*     */   }
/*     */   
/*     */   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
/*  93 */     flushText();
/*  94 */     this.serializer.endElement();
/*     */   }
/*     */   
/*     */   private void flushText() throws SAXException {
/*  98 */     if (this.text.length() != 0) {
/*  99 */       this.serializer.text(this.text.toString(), null);
/* 100 */       this.text.setLength(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 105 */     this.text.append(ch, start, length);
/*     */   }
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 109 */     this.text.append(ch, start, length);
/*     */   }
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {}
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {}
/*     */   
/*     */   public void skippedEntity(String name) throws SAXException {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\saml\internal\saml11\jaxb10\impl\runtime\ContentHandlerAdaptor.class
 * Java compiler version: 4 (48.0)
 * JD-Core Version:       1.1.3
 */