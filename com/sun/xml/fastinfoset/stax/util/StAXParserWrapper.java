/*     */ package com.sun.xml.fastinfoset.stax.util;
/*     */ 
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
/*     */ public class StAXParserWrapper
/*     */   implements XMLStreamReader
/*     */ {
/*     */   private XMLStreamReader _reader;
/*     */   
/*     */   public StAXParserWrapper() {}
/*     */   
/*     */   public StAXParserWrapper(XMLStreamReader reader) {
/*  36 */     this._reader = reader;
/*     */   }
/*     */   public void setReader(XMLStreamReader reader) {
/*  39 */     this._reader = reader;
/*     */   }
/*     */   public XMLStreamReader getReader() {
/*  42 */     return this._reader;
/*     */   }
/*     */ 
/*     */   
/*     */   public int next() throws XMLStreamException {
/*  47 */     return this._reader.next();
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextTag() throws XMLStreamException {
/*  52 */     return this._reader.nextTag();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getElementText() throws XMLStreamException {
/*  57 */     return this._reader.getElementText();
/*     */   }
/*     */ 
/*     */   
/*     */   public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
/*  62 */     this._reader.require(type, namespaceURI, localName);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNext() throws XMLStreamException {
/*  67 */     return this._reader.hasNext();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws XMLStreamException {
/*  72 */     this._reader.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNamespaceURI(String prefix) {
/*  77 */     return this._reader.getNamespaceURI(prefix);
/*     */   }
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/*  81 */     return this._reader.getNamespaceContext();
/*     */   }
/*     */   
/*     */   public boolean isStartElement() {
/*  85 */     return this._reader.isStartElement();
/*     */   }
/*     */   
/*     */   public boolean isEndElement() {
/*  89 */     return this._reader.isEndElement();
/*     */   }
/*     */   
/*     */   public boolean isCharacters() {
/*  93 */     return this._reader.isCharacters();
/*     */   }
/*     */   
/*     */   public boolean isWhiteSpace() {
/*  97 */     return this._reader.isWhiteSpace();
/*     */   }
/*     */   
/*     */   public QName getAttributeName(int index) {
/* 101 */     return this._reader.getAttributeName(index);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
/* 107 */     return this._reader.getTextCharacters(sourceStart, target, targetStart, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttributeValue(String namespaceUri, String localName) {
/* 113 */     return this._reader.getAttributeValue(namespaceUri, localName);
/*     */   }
/*     */   public int getAttributeCount() {
/* 116 */     return this._reader.getAttributeCount();
/*     */   }
/*     */   public String getAttributePrefix(int index) {
/* 119 */     return this._reader.getAttributePrefix(index);
/*     */   }
/*     */   public String getAttributeNamespace(int index) {
/* 122 */     return this._reader.getAttributeNamespace(index);
/*     */   }
/*     */   public String getAttributeLocalName(int index) {
/* 125 */     return this._reader.getAttributeLocalName(index);
/*     */   }
/*     */   public String getAttributeType(int index) {
/* 128 */     return this._reader.getAttributeType(index);
/*     */   }
/*     */   public String getAttributeValue(int index) {
/* 131 */     return this._reader.getAttributeValue(index);
/*     */   }
/*     */   public boolean isAttributeSpecified(int index) {
/* 134 */     return this._reader.isAttributeSpecified(index);
/*     */   }
/*     */   
/*     */   public int getNamespaceCount() {
/* 138 */     return this._reader.getNamespaceCount();
/*     */   }
/*     */   public String getNamespacePrefix(int index) {
/* 141 */     return this._reader.getNamespacePrefix(index);
/*     */   }
/*     */   public String getNamespaceURI(int index) {
/* 144 */     return this._reader.getNamespaceURI(index);
/*     */   }
/*     */   
/*     */   public int getEventType() {
/* 148 */     return this._reader.getEventType();
/*     */   }
/*     */   
/*     */   public String getText() {
/* 152 */     return this._reader.getText();
/*     */   }
/*     */   
/*     */   public char[] getTextCharacters() {
/* 156 */     return this._reader.getTextCharacters();
/*     */   }
/*     */   
/*     */   public int getTextStart() {
/* 160 */     return this._reader.getTextStart();
/*     */   }
/*     */   
/*     */   public int getTextLength() {
/* 164 */     return this._reader.getTextLength();
/*     */   }
/*     */   
/*     */   public String getEncoding() {
/* 168 */     return this._reader.getEncoding();
/*     */   }
/*     */   
/*     */   public boolean hasText() {
/* 172 */     return this._reader.hasText();
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 176 */     return this._reader.getLocation();
/*     */   }
/*     */   
/*     */   public QName getName() {
/* 180 */     return this._reader.getName();
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/* 184 */     return this._reader.getLocalName();
/*     */   }
/*     */   
/*     */   public boolean hasName() {
/* 188 */     return this._reader.hasName();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 192 */     return this._reader.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 196 */     return this._reader.getPrefix();
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 200 */     return this._reader.getVersion();
/*     */   }
/*     */   
/*     */   public boolean isStandalone() {
/* 204 */     return this._reader.isStandalone();
/*     */   }
/*     */   
/*     */   public boolean standaloneSet() {
/* 208 */     return this._reader.standaloneSet();
/*     */   }
/*     */   
/*     */   public String getCharacterEncodingScheme() {
/* 212 */     return this._reader.getCharacterEncodingScheme();
/*     */   }
/*     */   
/*     */   public String getPITarget() {
/* 216 */     return this._reader.getPITarget();
/*     */   }
/*     */   
/*     */   public String getPIData() {
/* 220 */     return this._reader.getPIData();
/*     */   }
/*     */   
/*     */   public Object getProperty(String name) {
/* 224 */     return this._reader.getProperty(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\sta\\util\StAXParserWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */