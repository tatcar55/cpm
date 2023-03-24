/*     */ package com.sun.xml.ws.util.xml;
/*     */ 
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamReaderFilter
/*     */   implements XMLStreamReaderFactory.RecycleAware, XMLStreamReader
/*     */ {
/*     */   protected XMLStreamReader reader;
/*     */   
/*     */   public XMLStreamReaderFilter(XMLStreamReader core) {
/*  67 */     this.reader = core;
/*     */   }
/*     */   
/*     */   public void onRecycled() {
/*  71 */     XMLStreamReaderFactory.recycle(this.reader);
/*  72 */     this.reader = null;
/*     */   }
/*     */   
/*     */   public int getAttributeCount() {
/*  76 */     return this.reader.getAttributeCount();
/*     */   }
/*     */   
/*     */   public int getEventType() {
/*  80 */     return this.reader.getEventType();
/*     */   }
/*     */   
/*     */   public int getNamespaceCount() {
/*  84 */     return this.reader.getNamespaceCount();
/*     */   }
/*     */   
/*     */   public int getTextLength() {
/*  88 */     return this.reader.getTextLength();
/*     */   }
/*     */   
/*     */   public int getTextStart() {
/*  92 */     return this.reader.getTextStart();
/*     */   }
/*     */   
/*     */   public int next() throws XMLStreamException {
/*  96 */     return this.reader.next();
/*     */   }
/*     */   
/*     */   public int nextTag() throws XMLStreamException {
/* 100 */     return this.reader.nextTag();
/*     */   }
/*     */   
/*     */   public void close() throws XMLStreamException {
/* 104 */     this.reader.close();
/*     */   }
/*     */   
/*     */   public boolean hasName() {
/* 108 */     return this.reader.hasName();
/*     */   }
/*     */   
/*     */   public boolean hasNext() throws XMLStreamException {
/* 112 */     return this.reader.hasNext();
/*     */   }
/*     */   
/*     */   public boolean hasText() {
/* 116 */     return this.reader.hasText();
/*     */   }
/*     */   
/*     */   public boolean isCharacters() {
/* 120 */     return this.reader.isCharacters();
/*     */   }
/*     */   
/*     */   public boolean isEndElement() {
/* 124 */     return this.reader.isEndElement();
/*     */   }
/*     */   
/*     */   public boolean isStandalone() {
/* 128 */     return this.reader.isStandalone();
/*     */   }
/*     */   
/*     */   public boolean isStartElement() {
/* 132 */     return this.reader.isStartElement();
/*     */   }
/*     */   
/*     */   public boolean isWhiteSpace() {
/* 136 */     return this.reader.isWhiteSpace();
/*     */   }
/*     */   
/*     */   public boolean standaloneSet() {
/* 140 */     return this.reader.standaloneSet();
/*     */   }
/*     */   
/*     */   public char[] getTextCharacters() {
/* 144 */     return this.reader.getTextCharacters();
/*     */   }
/*     */   
/*     */   public boolean isAttributeSpecified(int index) {
/* 148 */     return this.reader.isAttributeSpecified(index);
/*     */   }
/*     */   
/*     */   public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
/* 152 */     return this.reader.getTextCharacters(sourceStart, target, targetStart, length);
/*     */   }
/*     */   
/*     */   public String getCharacterEncodingScheme() {
/* 156 */     return this.reader.getCharacterEncodingScheme();
/*     */   }
/*     */   
/*     */   public String getElementText() throws XMLStreamException {
/* 160 */     return this.reader.getElementText();
/*     */   }
/*     */   
/*     */   public String getEncoding() {
/* 164 */     return this.reader.getEncoding();
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/* 168 */     return this.reader.getLocalName();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 172 */     return this.reader.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public String getPIData() {
/* 176 */     return this.reader.getPIData();
/*     */   }
/*     */   
/*     */   public String getPITarget() {
/* 180 */     return this.reader.getPITarget();
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 184 */     return this.reader.getPrefix();
/*     */   }
/*     */   
/*     */   public String getText() {
/* 188 */     return this.reader.getText();
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 192 */     return this.reader.getVersion();
/*     */   }
/*     */   
/*     */   public String getAttributeLocalName(int index) {
/* 196 */     return this.reader.getAttributeLocalName(index);
/*     */   }
/*     */   
/*     */   public String getAttributeNamespace(int index) {
/* 200 */     return this.reader.getAttributeNamespace(index);
/*     */   }
/*     */   
/*     */   public String getAttributePrefix(int index) {
/* 204 */     return this.reader.getAttributePrefix(index);
/*     */   }
/*     */   
/*     */   public String getAttributeType(int index) {
/* 208 */     return this.reader.getAttributeType(index);
/*     */   }
/*     */   
/*     */   public String getAttributeValue(int index) {
/* 212 */     return this.reader.getAttributeValue(index);
/*     */   }
/*     */   
/*     */   public String getNamespacePrefix(int index) {
/* 216 */     return this.reader.getNamespacePrefix(index);
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(int index) {
/* 220 */     return this.reader.getNamespaceURI(index);
/*     */   }
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 224 */     return this.reader.getNamespaceContext();
/*     */   }
/*     */   
/*     */   public QName getName() {
/* 228 */     return this.reader.getName();
/*     */   }
/*     */   
/*     */   public QName getAttributeName(int index) {
/* 232 */     return this.reader.getAttributeName(index);
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 236 */     return this.reader.getLocation();
/*     */   }
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 240 */     return this.reader.getProperty(name);
/*     */   }
/*     */   
/*     */   public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
/* 244 */     this.reader.require(type, namespaceURI, localName);
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/* 248 */     return this.reader.getNamespaceURI(prefix);
/*     */   }
/*     */   
/*     */   public String getAttributeValue(String namespaceURI, String localName) {
/* 252 */     return this.reader.getAttributeValue(namespaceURI, localName);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\xml\XMLStreamReaderFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */