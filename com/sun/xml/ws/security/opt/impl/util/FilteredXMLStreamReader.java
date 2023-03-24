/*     */ package com.sun.xml.ws.security.opt.impl.util;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FilteredXMLStreamReader
/*     */   implements XMLStreamReader
/*     */ {
/*  56 */   private XMLStreamReader reader = null;
/*  57 */   private int startElemCounter = 0;
/*     */ 
/*     */   
/*     */   public FilteredXMLStreamReader(XMLStreamReader reader) {
/*  61 */     this.reader = reader;
/*     */   }
/*     */   
/*     */   public int getAttributeCount() {
/*  65 */     return this.reader.getAttributeCount();
/*     */   }
/*     */   
/*     */   public int getEventType() {
/*  69 */     return this.reader.getEventType();
/*     */   }
/*     */   
/*     */   public int getNamespaceCount() {
/*  73 */     return this.reader.getNamespaceCount();
/*     */   }
/*     */   
/*     */   public int getTextLength() {
/*  77 */     return this.reader.getTextLength();
/*     */   }
/*     */   
/*     */   public int getTextStart() {
/*  81 */     return this.reader.getTextStart();
/*     */   }
/*     */   
/*     */   public int next() throws XMLStreamException {
/*  85 */     int nextEvent = this.reader.next();
/*  86 */     if (nextEvent == 1) {
/*  87 */       this.startElemCounter++;
/*  88 */       if (this.startElemCounter == 1) {
/*  89 */         return next();
/*     */       }
/*     */     } 
/*  92 */     if (nextEvent == 2) {
/*  93 */       this.startElemCounter--;
/*  94 */       if (this.startElemCounter == 1) {
/*  95 */         return next();
/*     */       }
/*     */     } 
/*  98 */     return nextEvent;
/*     */   }
/*     */   
/*     */   public int nextTag() throws XMLStreamException {
/* 102 */     int eventType = next();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 107 */     while ((eventType == 4 && isWhiteSpace()) || (eventType == 12 && isWhiteSpace()) || eventType == 6 || eventType == 3 || eventType == 5) {
/* 108 */       eventType = next();
/*     */     }
/* 110 */     if (eventType != 1 && eventType != 2) {
/* 111 */       throw new XMLStreamException("expected start or end tag", getLocation());
/*     */     }
/* 113 */     return eventType;
/*     */   }
/*     */   
/*     */   public void close() throws XMLStreamException {
/* 117 */     this.reader.close();
/*     */   }
/*     */   
/*     */   public boolean hasName() {
/* 121 */     return this.reader.hasName();
/*     */   }
/*     */   
/*     */   public boolean hasNext() throws XMLStreamException {
/* 125 */     return this.reader.hasNext();
/*     */   }
/*     */   
/*     */   public boolean hasText() {
/* 129 */     return this.reader.hasText();
/*     */   }
/*     */   
/*     */   public boolean isCharacters() {
/* 133 */     return this.reader.isCharacters();
/*     */   }
/*     */   
/*     */   public boolean isEndElement() {
/* 137 */     return this.reader.isEndElement();
/*     */   }
/*     */   
/*     */   public boolean isStandalone() {
/* 141 */     return this.reader.isStandalone();
/*     */   }
/*     */   
/*     */   public boolean isStartElement() {
/* 145 */     return this.reader.isStartElement();
/*     */   }
/*     */   
/*     */   public boolean isWhiteSpace() {
/* 149 */     return this.reader.isWhiteSpace();
/*     */   }
/*     */   
/*     */   public boolean standaloneSet() {
/* 153 */     return this.reader.standaloneSet();
/*     */   }
/*     */   
/*     */   public char[] getTextCharacters() {
/* 157 */     return this.reader.getTextCharacters();
/*     */   }
/*     */   
/*     */   public boolean isAttributeSpecified(int i) {
/* 161 */     return this.reader.isAttributeSpecified(i);
/*     */   }
/*     */   
/*     */   public int getTextCharacters(int i, char[] c, int i0, int i1) throws XMLStreamException {
/* 165 */     return this.reader.getTextCharacters(i, c, i0, i1);
/*     */   }
/*     */   
/*     */   public String getCharacterEncodingScheme() {
/* 169 */     return this.reader.getCharacterEncodingScheme();
/*     */   }
/*     */   
/*     */   public String getElementText() throws XMLStreamException {
/* 173 */     return this.reader.getElementText();
/*     */   }
/*     */   
/*     */   public String getEncoding() {
/* 177 */     return this.reader.getEncoding();
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/* 181 */     return this.reader.getLocalName();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 185 */     return this.reader.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public String getPIData() {
/* 189 */     return this.reader.getPIData();
/*     */   }
/*     */   
/*     */   public String getPITarget() {
/* 193 */     return this.reader.getPITarget();
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 197 */     return this.reader.getPrefix();
/*     */   }
/*     */   
/*     */   public String getText() {
/* 201 */     return this.reader.getText();
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 205 */     return this.reader.getVersion();
/*     */   }
/*     */   
/*     */   public String getAttributeLocalName(int i) {
/* 209 */     return this.reader.getAttributeLocalName(i);
/*     */   }
/*     */   
/*     */   public String getAttributeNamespace(int i) {
/* 213 */     return this.reader.getAttributeNamespace(i);
/*     */   }
/*     */   
/*     */   public String getAttributePrefix(int i) {
/* 217 */     return this.reader.getAttributePrefix(i);
/*     */   }
/*     */   
/*     */   public String getAttributeType(int i) {
/* 221 */     return this.reader.getAttributeType(i);
/*     */   }
/*     */   
/*     */   public String getAttributeValue(int i) {
/* 225 */     return this.reader.getAttributeValue(i);
/*     */   }
/*     */   
/*     */   public String getNamespacePrefix(int i) {
/* 229 */     return this.reader.getNamespacePrefix(i);
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(int i) {
/* 233 */     return this.reader.getNamespaceURI(i);
/*     */   }
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 237 */     return this.reader.getNamespaceContext();
/*     */   }
/*     */   
/*     */   public QName getName() {
/* 241 */     return this.reader.getName();
/*     */   }
/*     */   
/*     */   public QName getAttributeName(int i) {
/* 245 */     return this.reader.getAttributeName(i);
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/* 249 */     return this.reader.getLocation();
/*     */   }
/*     */   
/*     */   public Object getProperty(String string) throws IllegalArgumentException {
/* 253 */     return this.reader.getProperty(string);
/*     */   }
/*     */   
/*     */   public void require(int i, String string, String string0) throws XMLStreamException {
/* 257 */     this.reader.require(i, string, string0);
/*     */   }
/*     */   
/*     */   public String getNamespaceURI(String string) {
/* 261 */     return this.reader.getNamespaceURI(string);
/*     */   }
/*     */   
/*     */   public String getAttributeValue(String string, String string0) {
/* 265 */     return this.reader.getAttributeValue(string, string0);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\FilteredXMLStreamReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */