/*     */ package com.sun.xml.bind.v2.runtime.unmarshaller;
/*     */ 
/*     */ import com.sun.xml.bind.WhiteSpaceProcessor;
/*     */ import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.Attributes;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class FastInfosetConnector
/*     */   extends StAXConnector
/*     */ {
/*     */   private final StAXDocumentParser fastInfosetStreamReader;
/*     */   private boolean textReported;
/*  69 */   private final Base64Data base64Data = new Base64Data();
/*     */ 
/*     */   
/*  72 */   private final StringBuilder buffer = new StringBuilder();
/*     */   private final CharSequenceImpl charArray;
/*     */   
/*     */   public FastInfosetConnector(StAXDocumentParser fastInfosetStreamReader, XmlVisitor visitor) {
/*  76 */     super(visitor);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 253 */     this.charArray = new CharSequenceImpl(); fastInfosetStreamReader.setStringInterning(true); this.fastInfosetStreamReader = fastInfosetStreamReader;
/*     */   }
/*     */   public void bridge() throws XMLStreamException { try { int depth = 0; int event = this.fastInfosetStreamReader.getEventType(); if (event == 7) while (!this.fastInfosetStreamReader.isStartElement()) event = this.fastInfosetStreamReader.next();   if (event != 1) throw new IllegalStateException("The current event is not START_ELEMENT\n but " + event);  handleStartDocument(this.fastInfosetStreamReader.getNamespaceContext()); while (true) { switch (event) { case 1: handleStartElement(); depth++; break;case 2: depth--; handleEndElement(); if (depth == 0) break;  break;case 4: case 6: case 12: if (this.predictor.expectText()) { event = this.fastInfosetStreamReader.peekNext(); if (event == 2) { processNonIgnorableText(); break; }  if (event == 1) { processIgnorableText(); break; }  handleFragmentedCharacters(); }  break; }  event = this.fastInfosetStreamReader.next(); }  this.fastInfosetStreamReader.next(); handleEndDocument(); } catch (SAXException e) { throw new XMLStreamException(e); }  }
/* 256 */   protected Location getCurrentLocation() { return this.fastInfosetStreamReader.getLocation(); } protected String getCurrentQName() { return this.fastInfosetStreamReader.getNameString(); } private void processNonIgnorableText() throws SAXException { this.textReported = true;
/* 257 */     boolean isTextAlgorithmAplied = (this.fastInfosetStreamReader.getTextAlgorithmBytes() != null);
/*     */ 
/*     */     
/* 260 */     if (isTextAlgorithmAplied && this.fastInfosetStreamReader.getTextAlgorithmIndex() == 1)
/*     */     
/* 262 */     { this.base64Data.set(this.fastInfosetStreamReader.getTextAlgorithmBytesClone(), null);
/* 263 */       this.visitor.text((CharSequence)this.base64Data); }
/*     */     else
/* 265 */     { if (isTextAlgorithmAplied) {
/* 266 */         this.fastInfosetStreamReader.getText();
/*     */       }
/*     */       
/* 269 */       this.charArray.set();
/* 270 */       this.visitor.text(this.charArray); }  }
/*     */   private void handleStartElement() throws SAXException { processUnreportedText(); for (int i = 0; i < this.fastInfosetStreamReader.accessNamespaceCount(); i++) this.visitor.startPrefixMapping(this.fastInfosetStreamReader.getNamespacePrefix(i), this.fastInfosetStreamReader.getNamespaceURI(i));  this.tagName.uri = this.fastInfosetStreamReader.accessNamespaceURI(); this.tagName.local = this.fastInfosetStreamReader.accessLocalName(); this.tagName.atts = (Attributes)this.fastInfosetStreamReader.getAttributesHolder(); this.visitor.startElement(this.tagName); }
/*     */   private void handleFragmentedCharacters() throws XMLStreamException, SAXException { this.buffer.setLength(0); this.buffer.append(this.fastInfosetStreamReader.getTextCharacters(), this.fastInfosetStreamReader.getTextStart(), this.fastInfosetStreamReader.getTextLength()); while (true) { switch (this.fastInfosetStreamReader.peekNext()) { case 1: processBufferedText(true); return;case 2: processBufferedText(false); return;case 4: case 6: case 12: this.fastInfosetStreamReader.next(); this.buffer.append(this.fastInfosetStreamReader.getTextCharacters(), this.fastInfosetStreamReader.getTextStart(), this.fastInfosetStreamReader.getTextLength()); continue; }  this.fastInfosetStreamReader.next(); }  }
/*     */   private void handleEndElement() throws SAXException { processUnreportedText(); this.tagName.uri = this.fastInfosetStreamReader.accessNamespaceURI(); this.tagName.local = this.fastInfosetStreamReader.accessLocalName(); this.visitor.endElement(this.tagName); for (int i = this.fastInfosetStreamReader.accessNamespaceCount() - 1; i >= 0; i--) this.visitor.endPrefixMapping(this.fastInfosetStreamReader.getNamespacePrefix(i));  }
/*     */   private final class CharSequenceImpl implements CharSequence {
/* 275 */     char[] ch; int start; int length; CharSequenceImpl() {} CharSequenceImpl(char[] ch, int start, int length) { this.ch = ch; this.start = start; this.length = length; } public void set() { this.ch = FastInfosetConnector.this.fastInfosetStreamReader.getTextCharacters(); this.start = FastInfosetConnector.this.fastInfosetStreamReader.getTextStart(); this.length = FastInfosetConnector.this.fastInfosetStreamReader.getTextLength(); } public final int length() { return this.length; } public final char charAt(int index) { return this.ch[this.start + index]; } public final CharSequence subSequence(int start, int end) { return new CharSequenceImpl(this.ch, this.start + start, end - start); } public String toString() { return new String(this.ch, this.start, this.length); } } private void processIgnorableText() throws SAXException { boolean isTextAlgorithmAplied = (this.fastInfosetStreamReader.getTextAlgorithmBytes() != null);
/*     */ 
/*     */     
/* 278 */     if (isTextAlgorithmAplied && this.fastInfosetStreamReader.getTextAlgorithmIndex() == 1) {
/*     */       
/* 280 */       this.base64Data.set(this.fastInfosetStreamReader.getTextAlgorithmBytesClone(), null);
/* 281 */       this.visitor.text((CharSequence)this.base64Data);
/* 282 */       this.textReported = true;
/*     */     } else {
/* 284 */       if (isTextAlgorithmAplied) {
/* 285 */         this.fastInfosetStreamReader.getText();
/*     */       }
/*     */       
/* 288 */       this.charArray.set();
/* 289 */       if (!WhiteSpaceProcessor.isWhiteSpace(this.charArray)) {
/* 290 */         this.visitor.text(this.charArray);
/* 291 */         this.textReported = true;
/*     */       } 
/*     */     }  }
/*     */ 
/*     */   
/*     */   private void processBufferedText(boolean ignorable) throws SAXException {
/* 297 */     if (!ignorable || !WhiteSpaceProcessor.isWhiteSpace(this.buffer)) {
/* 298 */       this.visitor.text(this.buffer);
/* 299 */       this.textReported = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void processUnreportedText() throws SAXException {
/* 304 */     if (!this.textReported && this.predictor.expectText()) {
/* 305 */       this.visitor.text("");
/*     */     }
/* 307 */     this.textReported = false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtim\\unmarshaller\FastInfosetConnector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */