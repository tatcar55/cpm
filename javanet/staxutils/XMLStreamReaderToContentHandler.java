/*     */ package javanet.staxutils;
/*     */ 
/*     */ import javanet.staxutils.helpers.XMLFilterImplEx;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.xml.sax.Attributes;
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
/*     */ public class XMLStreamReaderToContentHandler
/*     */   implements StAXReaderToContentHandler
/*     */ {
/*     */   private final XMLStreamReader staxStreamReader;
/*     */   private XMLFilterImplEx filter;
/*     */   
/*     */   public XMLStreamReaderToContentHandler(XMLStreamReader staxCore, XMLFilterImplEx filter) {
/*  78 */     this.staxStreamReader = staxCore;
/*     */     
/*  80 */     this.filter = filter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void bridge() throws XMLStreamException {
/*     */     try {
/*  90 */       int depth = 0;
/*  91 */       boolean isDocument = false;
/*     */       
/*  93 */       handleStartDocument();
/*     */ 
/*     */       
/*  96 */       int event = this.staxStreamReader.getEventType();
/*  97 */       if (event == 7) {
/*  98 */         isDocument = true;
/*  99 */         event = this.staxStreamReader.next();
/* 100 */         while (event != 1) {
/* 101 */           switch (event) {
/*     */             case 5:
/* 103 */               handleComment();
/*     */               break;
/*     */             case 3:
/* 106 */               handlePI();
/*     */               break;
/*     */           } 
/* 109 */           event = this.staxStreamReader.next();
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 114 */       if (event != 1) {
/* 115 */         throw new IllegalStateException("The current event is not START_ELEMENT\n but" + event);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 122 */         switch (event) {
/*     */           case 1:
/* 124 */             depth++;
/* 125 */             handleStartElement();
/*     */             break;
/*     */           case 2:
/* 128 */             handleEndElement();
/* 129 */             depth--;
/*     */             break;
/*     */           case 4:
/* 132 */             handleCharacters();
/*     */             break;
/*     */           case 9:
/* 135 */             handleEntityReference();
/*     */             break;
/*     */           case 3:
/* 138 */             handlePI();
/*     */             break;
/*     */           case 5:
/* 141 */             handleComment();
/*     */             break;
/*     */           case 11:
/* 144 */             handleDTD();
/*     */             break;
/*     */           case 10:
/* 147 */             handleAttribute();
/*     */             break;
/*     */           case 13:
/* 150 */             handleNamespace();
/*     */             break;
/*     */           case 12:
/* 153 */             handleCDATA();
/*     */             break;
/*     */           case 15:
/* 156 */             handleEntityDecl();
/*     */             break;
/*     */           case 14:
/* 159 */             handleNotationDecl();
/*     */             break;
/*     */           case 6:
/* 162 */             handleSpace();
/*     */             break;
/*     */           default:
/* 165 */             throw new InternalError("processing event: " + event);
/*     */         } 
/*     */         
/* 168 */         event = this.staxStreamReader.next();
/* 169 */       } while (depth != 0);
/*     */ 
/*     */       
/* 172 */       if (isDocument) {
/* 173 */         while (event != 8) {
/* 174 */           switch (event) {
/*     */             case 5:
/* 176 */               handleComment();
/*     */               break;
/*     */             case 3:
/* 179 */               handlePI();
/*     */               break;
/*     */           } 
/* 182 */           event = this.staxStreamReader.next();
/*     */         } 
/*     */       }
/*     */       
/* 186 */       handleEndDocument();
/* 187 */     } catch (SAXException e) {
/* 188 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void handleEndDocument() throws SAXException {
/* 193 */     this.filter.endDocument();
/*     */   }
/*     */   
/*     */   private void handleStartDocument() throws SAXException {
/* 197 */     Location location = this.staxStreamReader.getLocation();
/* 198 */     if (location != null) {
/* 199 */       this.filter.setDocumentLocator(new Locator(this, location) { private final Location val$location;
/*     */             public int getColumnNumber() {
/* 201 */               return this.val$location.getColumnNumber();
/*     */             } private final XMLStreamReaderToContentHandler this$0;
/*     */             public int getLineNumber() {
/* 204 */               return this.val$location.getLineNumber();
/*     */             }
/*     */             public String getPublicId() {
/* 207 */               return this.val$location.getPublicId();
/*     */             }
/*     */             public String getSystemId() {
/* 210 */               return this.val$location.getSystemId();
/*     */             } }
/*     */         );
/*     */     } else {
/* 214 */       this.filter.setDocumentLocator(new DummyLocator());
/*     */     } 
/* 216 */     this.filter.startDocument();
/*     */   }
/*     */   
/*     */   private void handlePI() throws XMLStreamException {
/*     */     try {
/* 221 */       this.filter.processingInstruction(this.staxStreamReader.getPITarget(), this.staxStreamReader.getPIData());
/*     */     
/*     */     }
/* 224 */     catch (SAXException e) {
/* 225 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleCharacters() throws XMLStreamException {
/* 233 */     int textLength = this.staxStreamReader.getTextLength();
/* 234 */     int textStart = this.staxStreamReader.getTextStart();
/* 235 */     char[] chars = new char[textLength];
/*     */     
/* 237 */     this.staxStreamReader.getTextCharacters(textStart, chars, 0, textLength);
/*     */     
/*     */     try {
/* 240 */       this.filter.characters(chars, 0, chars.length);
/* 241 */     } catch (SAXException e) {
/* 242 */       throw new XMLStreamException(e);
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
/*     */   
/*     */   private void handleEndElement() throws XMLStreamException {
/* 260 */     QName qName = this.staxStreamReader.getName();
/*     */ 
/*     */     
/*     */     try {
/* 264 */       String rawname, prefix = qName.getPrefix();
/*     */       
/* 266 */       if (prefix == null || prefix.length() == 0) {
/* 267 */         rawname = qName.getLocalPart();
/*     */       } else {
/* 269 */         rawname = prefix + ':' + qName.getLocalPart();
/*     */       } 
/* 271 */       this.filter.endElement(qName.getNamespaceURI(), qName.getLocalPart(), rawname);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 277 */       int nsCount = this.staxStreamReader.getNamespaceCount();
/* 278 */       for (int i = nsCount - 1; i >= 0; i--) {
/* 279 */         String nsprefix = this.staxStreamReader.getNamespacePrefix(i);
/* 280 */         if (nsprefix == null) {
/* 281 */           nsprefix = "";
/*     */         }
/* 283 */         this.filter.endPrefixMapping(nsprefix);
/*     */       } 
/* 285 */     } catch (SAXException e) {
/* 286 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleStartElement() throws XMLStreamException {
/*     */     try {
/*     */       String rawname;
/* 294 */       int nsCount = this.staxStreamReader.getNamespaceCount();
/* 295 */       for (int i = 0; i < nsCount; i++) {
/* 296 */         String uri = this.staxStreamReader.getNamespaceURI(i);
/* 297 */         if (uri == null) {
/* 298 */           uri = "";
/*     */         }
/* 300 */         String str1 = this.staxStreamReader.getNamespacePrefix(i);
/* 301 */         if (str1 == null) {
/* 302 */           str1 = "";
/*     */         }
/* 304 */         this.filter.startPrefixMapping(str1, uri);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 310 */       QName qName = this.staxStreamReader.getName();
/* 311 */       String prefix = qName.getPrefix();
/*     */       
/* 313 */       if (prefix == null || prefix.length() == 0) {
/* 314 */         rawname = qName.getLocalPart();
/*     */       } else {
/* 316 */         rawname = prefix + ':' + qName.getLocalPart();
/* 317 */       }  Attributes attrs = getAttributes();
/* 318 */       this.filter.startElement(qName.getNamespaceURI(), qName.getLocalPart(), rawname, attrs);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 323 */     catch (SAXException e) {
/* 324 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Attributes getAttributes() {
/* 335 */     AttributesImpl attrs = new AttributesImpl();
/*     */     
/* 337 */     int eventType = this.staxStreamReader.getEventType();
/* 338 */     if (eventType != 10 && eventType != 1)
/*     */     {
/* 340 */       throw new InternalError("getAttributes() attempting to process: " + eventType);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 345 */     if (this.filter.getNamespacePrefixes()) {
/* 346 */       for (int j = 0; j < this.staxStreamReader.getNamespaceCount(); j++) {
/* 347 */         String uri = this.staxStreamReader.getNamespaceURI(j);
/* 348 */         if (uri == null) uri = "";
/*     */         
/* 350 */         String prefix = this.staxStreamReader.getNamespacePrefix(j);
/* 351 */         if (prefix == null) prefix = "";
/*     */         
/* 353 */         String qName = "xmlns";
/* 354 */         if (prefix.length() == 0) {
/* 355 */           prefix = qName;
/*     */         } else {
/* 357 */           qName = qName + ':' + prefix;
/*     */         } 
/* 359 */         attrs.addAttribute("http://www.w3.org/2000/xmlns/", prefix, qName, "CDATA", uri);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 364 */     for (int i = 0; i < this.staxStreamReader.getAttributeCount(); i++) {
/* 365 */       String qName, uri = this.staxStreamReader.getAttributeNamespace(i);
/* 366 */       if (uri == null) uri = ""; 
/* 367 */       String localName = this.staxStreamReader.getAttributeLocalName(i);
/* 368 */       String prefix = this.staxStreamReader.getAttributePrefix(i);
/*     */       
/* 370 */       if (prefix == null || prefix.length() == 0) {
/* 371 */         qName = localName;
/*     */       } else {
/* 373 */         qName = prefix + ':' + localName;
/* 374 */       }  String type = this.staxStreamReader.getAttributeType(i);
/* 375 */       String value = this.staxStreamReader.getAttributeValue(i);
/*     */       
/* 377 */       attrs.addAttribute(uri, localName, qName, type, value);
/*     */     } 
/*     */     
/* 380 */     return attrs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleNamespace() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleAttribute() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleDTD() {}
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleComment() throws XMLStreamException {
/* 401 */     int textLength = this.staxStreamReader.getTextLength();
/* 402 */     int textStart = this.staxStreamReader.getTextStart();
/* 403 */     char[] chars = new char[textLength];
/*     */     
/* 405 */     this.staxStreamReader.getTextCharacters(textStart, chars, 0, textLength);
/*     */     
/*     */     try {
/* 408 */       this.filter.comment(chars, 0, textLength);
/* 409 */     } catch (SAXException e) {
/* 410 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\javanet\staxutils\XMLStreamReaderToContentHandler.class
 * Java compiler version: 3 (47.0)
 * JD-Core Version:       1.1.3
 */