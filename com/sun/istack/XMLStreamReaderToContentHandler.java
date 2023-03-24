/*     */ package com.sun.istack;
/*     */ 
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XMLStreamReaderToContentHandler
/*     */ {
/*     */   private final XMLStreamReader staxStreamReader;
/*     */   private final ContentHandler saxHandler;
/*     */   private final boolean eagerQuit;
/*     */   private final boolean fragment;
/*     */   private final String[] inscopeNamespaces;
/*     */   
/*     */   public XMLStreamReaderToContentHandler(XMLStreamReader staxCore, ContentHandler saxCore, boolean eagerQuit, boolean fragment) {
/*  87 */     this(staxCore, saxCore, eagerQuit, fragment, new String[0]);
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
/*     */ 
/*     */   
/*     */   public XMLStreamReaderToContentHandler(XMLStreamReader staxCore, ContentHandler saxCore, boolean eagerQuit, boolean fragment, String[] inscopeNamespaces) {
/* 105 */     this.staxStreamReader = staxCore;
/* 106 */     this.saxHandler = saxCore;
/* 107 */     this.eagerQuit = eagerQuit;
/* 108 */     this.fragment = fragment;
/* 109 */     this.inscopeNamespaces = inscopeNamespaces;
/* 110 */     assert inscopeNamespaces.length % 2 == 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bridge() throws XMLStreamException {
/*     */     try {
/* 121 */       int depth = 0;
/*     */ 
/*     */       
/* 124 */       int event = this.staxStreamReader.getEventType();
/* 125 */       if (event == 7)
/*     */       {
/* 127 */         while (!this.staxStreamReader.isStartElement()) {
/* 128 */           event = this.staxStreamReader.next();
/*     */         }
/*     */       }
/*     */       
/* 132 */       if (event != 1) {
/* 133 */         throw new IllegalStateException("The current event is not START_ELEMENT\n but " + event);
/*     */       }
/* 135 */       handleStartDocument();
/*     */       int i;
/* 137 */       for (i = 0; i < this.inscopeNamespaces.length; i += 2) {
/* 138 */         this.saxHandler.startPrefixMapping(this.inscopeNamespaces[i], this.inscopeNamespaces[i + 1]);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 146 */         switch (event) {
/*     */           case 1:
/* 148 */             depth++;
/* 149 */             handleStartElement();
/*     */             break;
/*     */           case 2:
/* 152 */             handleEndElement();
/* 153 */             depth--;
/* 154 */             if (depth == 0 && this.eagerQuit)
/*     */               break; 
/*     */             break;
/*     */           case 4:
/* 158 */             handleCharacters();
/*     */             break;
/*     */           case 9:
/* 161 */             handleEntityReference();
/*     */             break;
/*     */           case 3:
/* 164 */             handlePI();
/*     */             break;
/*     */           case 5:
/* 167 */             handleComment();
/*     */             break;
/*     */           case 11:
/* 170 */             handleDTD();
/*     */             break;
/*     */           case 10:
/* 173 */             handleAttribute();
/*     */             break;
/*     */           case 13:
/* 176 */             handleNamespace();
/*     */             break;
/*     */           case 12:
/* 179 */             handleCDATA();
/*     */             break;
/*     */           case 15:
/* 182 */             handleEntityDecl();
/*     */             break;
/*     */           case 14:
/* 185 */             handleNotationDecl();
/*     */             break;
/*     */           case 6:
/* 188 */             handleSpace();
/*     */             break;
/*     */           default:
/* 191 */             throw new InternalError("processing event: " + event);
/*     */         } 
/*     */         
/* 194 */         event = this.staxStreamReader.next();
/* 195 */       } while (depth != 0);
/*     */       
/* 197 */       for (i = 0; i < this.inscopeNamespaces.length; i += 2) {
/* 198 */         this.saxHandler.endPrefixMapping(this.inscopeNamespaces[i]);
/*     */       }
/*     */       
/* 201 */       handleEndDocument();
/* 202 */     } catch (SAXException e) {
/* 203 */       throw new XMLStreamException2(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleEndDocument() throws SAXException {
/* 208 */     if (this.fragment) {
/*     */       return;
/*     */     }
/* 211 */     this.saxHandler.endDocument();
/*     */   }
/*     */   
/*     */   private void handleStartDocument() throws SAXException {
/* 215 */     if (this.fragment) {
/*     */       return;
/*     */     }
/* 218 */     this.saxHandler.setDocumentLocator(new Locator() {
/*     */           public int getColumnNumber() {
/* 220 */             return XMLStreamReaderToContentHandler.this.staxStreamReader.getLocation().getColumnNumber();
/*     */           }
/*     */           public int getLineNumber() {
/* 223 */             return XMLStreamReaderToContentHandler.this.staxStreamReader.getLocation().getLineNumber();
/*     */           }
/*     */           public String getPublicId() {
/* 226 */             return XMLStreamReaderToContentHandler.this.staxStreamReader.getLocation().getPublicId();
/*     */           }
/*     */           public String getSystemId() {
/* 229 */             return XMLStreamReaderToContentHandler.this.staxStreamReader.getLocation().getSystemId();
/*     */           }
/*     */         });
/* 232 */     this.saxHandler.startDocument();
/*     */   }
/*     */   
/*     */   private void handlePI() throws XMLStreamException {
/*     */     try {
/* 237 */       this.saxHandler.processingInstruction(this.staxStreamReader.getPITarget(), this.staxStreamReader.getPIData());
/*     */     
/*     */     }
/* 240 */     catch (SAXException e) {
/* 241 */       throw new XMLStreamException2(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleCharacters() throws XMLStreamException {
/*     */     try {
/* 247 */       this.saxHandler.characters(this.staxStreamReader.getTextCharacters(), this.staxStreamReader.getTextStart(), this.staxStreamReader.getTextLength());
/*     */ 
/*     */     
/*     */     }
/* 251 */     catch (SAXException e) {
/* 252 */       throw new XMLStreamException2(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleEndElement() throws XMLStreamException {
/* 257 */     QName qName = this.staxStreamReader.getName();
/*     */     
/*     */     try {
/* 260 */       String pfix = qName.getPrefix();
/* 261 */       String rawname = (pfix == null || pfix.length() == 0) ? qName.getLocalPart() : (pfix + ':' + qName.getLocalPart());
/*     */ 
/*     */ 
/*     */       
/* 265 */       this.saxHandler.endElement(qName.getNamespaceURI(), qName.getLocalPart(), rawname);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 271 */       int nsCount = this.staxStreamReader.getNamespaceCount();
/* 272 */       for (int i = nsCount - 1; i >= 0; i--) {
/* 273 */         String prefix = this.staxStreamReader.getNamespacePrefix(i);
/* 274 */         if (prefix == null) {
/* 275 */           prefix = "";
/*     */         }
/* 277 */         this.saxHandler.endPrefixMapping(prefix);
/*     */       } 
/* 279 */     } catch (SAXException e) {
/* 280 */       throw new XMLStreamException2(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleStartElement() throws XMLStreamException {
/*     */     try {
/*     */       String rawname;
/* 288 */       int nsCount = this.staxStreamReader.getNamespaceCount();
/* 289 */       for (int i = 0; i < nsCount; i++) {
/* 290 */         this.saxHandler.startPrefixMapping(fixNull(this.staxStreamReader.getNamespacePrefix(i)), fixNull(this.staxStreamReader.getNamespaceURI(i)));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 296 */       QName qName = this.staxStreamReader.getName();
/* 297 */       String prefix = qName.getPrefix();
/*     */       
/* 299 */       if (prefix == null || prefix.length() == 0) {
/* 300 */         rawname = qName.getLocalPart();
/*     */       } else {
/* 302 */         rawname = prefix + ':' + qName.getLocalPart();
/* 303 */       }  Attributes attrs = getAttributes();
/* 304 */       this.saxHandler.startElement(qName.getNamespaceURI(), qName.getLocalPart(), rawname, attrs);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 309 */     catch (SAXException e) {
/* 310 */       throw new XMLStreamException2(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String fixNull(String s) {
/* 315 */     if (s == null) return ""; 
/* 316 */     return s;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Attributes getAttributes() {
/* 326 */     AttributesImpl attrs = new AttributesImpl();
/*     */     
/* 328 */     int eventType = this.staxStreamReader.getEventType();
/* 329 */     if (eventType != 10 && eventType != 1)
/*     */     {
/* 331 */       throw new InternalError("getAttributes() attempting to process: " + eventType);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 340 */     for (int i = 0; i < this.staxStreamReader.getAttributeCount(); i++) {
/* 341 */       String qName, uri = this.staxStreamReader.getAttributeNamespace(i);
/* 342 */       if (uri == null) uri = ""; 
/* 343 */       String localName = this.staxStreamReader.getAttributeLocalName(i);
/* 344 */       String prefix = this.staxStreamReader.getAttributePrefix(i);
/*     */       
/* 346 */       if (prefix == null || prefix.length() == 0) {
/* 347 */         qName = localName;
/*     */       } else {
/* 349 */         qName = prefix + ':' + localName;
/* 350 */       }  String type = this.staxStreamReader.getAttributeType(i);
/* 351 */       String value = this.staxStreamReader.getAttributeValue(i);
/*     */       
/* 353 */       attrs.addAttribute(uri, localName, qName, type, value);
/*     */     } 
/*     */     
/* 356 */     return attrs;
/*     */   }
/*     */   
/*     */   private void handleNamespace() {}
/*     */   
/*     */   private void handleAttribute() {}
/*     */   
/*     */   private void handleDTD() {}
/*     */   
/*     */   private void handleComment() {}
/*     */   
/*     */   private void handleEntityReference() {}
/*     */   
/*     */   private void handleSpace() {}
/*     */   
/*     */   private void handleNotationDecl() {}
/*     */   
/*     */   private void handleEntityDecl() {}
/*     */   
/*     */   private void handleCDATA() {}
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\istack\XMLStreamReaderToContentHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */