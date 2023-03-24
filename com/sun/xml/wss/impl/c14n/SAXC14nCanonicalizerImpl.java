/*     */ package com.sun.xml.wss.impl.c14n;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.helpers.NamespaceSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAXC14nCanonicalizerImpl
/*     */   extends BaseCanonicalizer
/*     */   implements ContentHandler
/*     */ {
/*  69 */   NamespaceSupport nsContext = new NamespaceSupport();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXC14nCanonicalizerImpl() {
/*  78 */     this._attrResult = new ArrayList();
/*  79 */     for (int i = 0; i < 4; i++) {
/*  80 */       this._attrs.add(new Attribute());
/*     */     }
/*     */   }
/*     */   
/*     */   public NamespaceSupport getNSContext() {
/*  85 */     return this.nsContext;
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
/*     */   
/*     */   public void warning(SAXParseException e) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void error(SAXParseException e) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fatalError(SAXParseException e) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 169 */       outputTextToWriter(ch, start, length, this._stream);
/* 170 */     } catch (IOException ex) {
/* 171 */       throw new RuntimeException(ex);
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
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEntity(String name) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startEntity(String name) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 286 */     String dURI = this.nsContext.getURI(prefix);
/* 287 */     boolean add = false;
/* 288 */     if (dURI == null || !uri.equals(dURI)) {
/* 289 */       add = true;
/*     */     }
/*     */     
/* 292 */     if (add && !this._ncContextState[this._depth]) {
/* 293 */       this.nsContext.pushContext();
/*     */       
/* 295 */       this._ncContextState[this._depth] = true;
/*     */     } 
/* 297 */     if (add) {
/* 298 */       if (prefix.length() == 0) {
/* 299 */         this._defURI = uri;
/*     */       } else {
/* 301 */         this.nsContext.declarePrefix(prefix, uri);
/* 302 */         AttributeNS attrNS = getAttributeNS();
/* 303 */         attrNS.setPrefix(prefix);
/* 304 */         attrNS.setUri(uri);
/* 305 */         this._nsResult.add(attrNS);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void reset() {
/* 311 */     super.reset();
/* 312 */     this.nsContext.reset();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/*     */     try {
/* 341 */       this._depth++;
/* 342 */       resize();
/* 343 */       this._ncContextState[this._depth] = false;
/* 344 */       this._stream.write(60);
/* 345 */       if (qName.length() > 0) {
/* 346 */         writeStringToUtf8(qName, this._stream);
/*     */       } else {
/* 348 */         writeStringToUtf8(localName, this._stream);
/*     */       } 
/* 350 */       if (attributes.getLength() > 0 || this._nsResult.size() > 0) {
/* 351 */         handleAttributes(attributes);
/*     */       }
/* 353 */       this._stream.write(62);
/*     */       
/* 355 */       this._attrNSPos = 0;
/* 356 */       this._attrPos = 0;
/*     */       
/* 358 */       this._defURI = null;
/*     */       
/* 360 */       this._nsResult.clear();
/* 361 */       this._attrResult.clear();
/* 362 */     } catch (IOException ex) {
/* 363 */       throw new RuntimeException(ex);
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
/*     */   public void startDocument() throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDTD(String name, String publicId, String systemId) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startCDATA() throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId) throws SAXException, IOException {
/* 404 */     throw new UnsupportedOperationException("Not yet implemented");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
/* 414 */     return null;
/*     */   }
/*     */   
/*     */   public void internalEntityDecl(String name, String value) throws SAXException {
/* 418 */     throw new UnsupportedOperationException("Not yet implemented");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputSource getExternalSubset(String name, String baseURI) throws SAXException, IOException {
/* 427 */     throw new UnsupportedOperationException("Not yet implemented");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void externalEntityDecl(String name, String publicId, String systemId) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDTD() throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endCDATA() throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void elementDecl(String name, String model) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attributeDecl(String eName, String aName, String type, String mode, String value) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endElement(String uri, String localName, String qName) throws SAXException {
/* 483 */     this._depth--;
/* 484 */     if (this._ncContextState[this._depth]) {
/* 485 */       this.nsContext.popContext();
/* 486 */       this._ncContextState[this._depth] = false;
/*     */     }  try {
/* 488 */       this._stream.write(_END_TAG);
/* 489 */       if (qName.length() > 0) {
/* 490 */         writeStringToUtf8(qName, this._stream);
/*     */       } else {
/* 492 */         writeStringToUtf8(localName, this._stream);
/*     */       } 
/* 494 */       this._stream.write(62);
/* 495 */     } catch (IOException io) {
/* 496 */       throw new RuntimeException(io);
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
/*     */   public void notationDecl(String name, String publicId, String systemId) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleAttributes(Attributes attributes) {
/* 539 */     int length = attributes.getLength();
/* 540 */     String localName = null;
/* 541 */     boolean contextPushed = false;
/*     */     
/*     */     try {
/* 544 */       for (int i = 0; i < length; i++) {
/* 545 */         Attribute attr = getAttribute();
/* 546 */         attr.setPosition(i);
/* 547 */         attr.setAttributes(attributes);
/* 548 */         this._attrResult.add(attr);
/*     */       } 
/*     */       
/* 551 */       if (this._defURI != null) {
/* 552 */         outputAttrToWriter("xmlns", this._defURI, this._stream);
/*     */       }
/* 554 */       Iterator itr = this._nsResult.iterator();
/* 555 */       writeAttributesNS(itr);
/* 556 */       BaseCanonicalizer.sort(this._attrResult);
/* 557 */       writeAttributes(attributes, this._attrResult.iterator());
/* 558 */       this._nsResult.clear();
/* 559 */       this._attrResult.clear();
/* 560 */     } catch (IOException io) {
/* 561 */       throw new RuntimeException(io);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Attribute getAttribute() {
/* 566 */     if (this._attrPos < this._attrs.size()) {
/* 567 */       return this._attrs.get(this._attrPos++);
/*     */     }
/* 569 */     for (int i = 0; i < 4; i++) {
/* 570 */       this._attrs.add(new Attribute());
/*     */     }
/* 572 */     return this._attrs.get(this._attrPos++);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\SAXC14nCanonicalizerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */