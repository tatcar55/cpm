/*     */ package com.sun.xml.fastinfoset.sax;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import com.sun.xml.fastinfoset.Encoder;
/*     */ import com.sun.xml.fastinfoset.QualifiedName;
/*     */ import com.sun.xml.fastinfoset.util.LocalNameQualifiedNamesMap;
/*     */ import java.io.IOException;
/*     */ import org.jvnet.fastinfoset.FastInfosetException;
/*     */ import org.jvnet.fastinfoset.sax.EncodingAlgorithmAttributes;
/*     */ import org.jvnet.fastinfoset.sax.FastInfosetWriter;
/*     */ import org.xml.sax.Attributes;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAXDocumentSerializer
/*     */   extends Encoder
/*     */   implements FastInfosetWriter
/*     */ {
/*     */   protected boolean _elementHasNamespaces = false;
/*     */   protected boolean _charactersAsCDATA = false;
/*     */   
/*     */   protected SAXDocumentSerializer(boolean v) {
/*  56 */     super(v);
/*     */   }
/*     */ 
/*     */   
/*     */   public SAXDocumentSerializer() {}
/*     */ 
/*     */   
/*     */   public void reset() {
/*  64 */     super.reset();
/*     */     
/*  66 */     this._elementHasNamespaces = false;
/*  67 */     this._charactersAsCDATA = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void startDocument() throws SAXException {
/*     */     try {
/*  74 */       reset();
/*  75 */       encodeHeader(false);
/*  76 */       encodeInitialVocabulary();
/*  77 */     } catch (IOException e) {
/*  78 */       throw new SAXException("startDocument", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void endDocument() throws SAXException {
/*     */     try {
/*  84 */       encodeDocumentTermination();
/*  85 */     } catch (IOException e) {
/*  86 */       throw new SAXException("endDocument", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/*     */     try {
/*  92 */       if (!this._elementHasNamespaces) {
/*  93 */         encodeTermination();
/*     */ 
/*     */         
/*  96 */         mark();
/*  97 */         this._elementHasNamespaces = true;
/*     */ 
/*     */         
/* 100 */         write(56);
/*     */       } 
/*     */       
/* 103 */       encodeNamespaceAttribute(prefix, uri);
/* 104 */     } catch (IOException e) {
/* 105 */       throw new SAXException("startElement", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/* 111 */     int attributeCount = (atts != null && atts.getLength() > 0) ? countAttributes(atts) : 0;
/*     */     
/*     */     try {
/* 114 */       if (this._elementHasNamespaces) {
/* 115 */         this._elementHasNamespaces = false;
/*     */         
/* 117 */         if (attributeCount > 0)
/*     */         {
/* 119 */           this._octetBuffer[this._markIndex] = (byte)(this._octetBuffer[this._markIndex] | 0x40);
/*     */         }
/* 121 */         resetMark();
/*     */         
/* 123 */         write(240);
/*     */         
/* 125 */         this._b = 0;
/*     */       } else {
/* 127 */         encodeTermination();
/*     */         
/* 129 */         this._b = 0;
/* 130 */         if (attributeCount > 0) {
/* 131 */           this._b |= 0x40;
/*     */         }
/*     */       } 
/*     */       
/* 135 */       encodeElement(namespaceURI, qName, localName);
/*     */       
/* 137 */       if (attributeCount > 0) {
/* 138 */         encodeAttributes(atts);
/*     */       }
/* 140 */     } catch (IOException e) {
/* 141 */       throw new SAXException("startElement", e);
/* 142 */     } catch (FastInfosetException e) {
/* 143 */       throw new SAXException("startElement", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void endElement(String namespaceURI, String localName, String qName) throws SAXException {
/*     */     try {
/* 149 */       encodeElementTermination();
/* 150 */     } catch (IOException e) {
/* 151 */       throw new SAXException("endElement", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void characters(char[] ch, int start, int length) throws SAXException {
/* 156 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 160 */     if (getIgnoreWhiteSpaceTextContent() && isWhiteSpace(ch, start, length)) {
/*     */       return;
/*     */     }
/*     */     try {
/* 164 */       encodeTermination();
/*     */       
/* 166 */       if (!this._charactersAsCDATA) {
/* 167 */         encodeCharacters(ch, start, length);
/*     */       } else {
/* 169 */         encodeCIIBuiltInAlgorithmDataAsCDATA(ch, start, length);
/*     */       } 
/* 171 */     } catch (IOException e) {
/* 172 */       throw new SAXException(e);
/* 173 */     } catch (FastInfosetException e) {
/* 174 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 179 */     if (getIgnoreWhiteSpaceTextContent())
/*     */       return; 
/* 181 */     characters(ch, start, length);
/*     */   }
/*     */   
/*     */   public final void processingInstruction(String target, String data) throws SAXException {
/*     */     try {
/* 186 */       if (getIgnoreProcesingInstructions())
/*     */         return; 
/* 188 */       if (target.length() == 0) {
/* 189 */         throw new SAXException(CommonResourceBundle.getInstance().getString("message.processingInstructionTargetIsEmpty"));
/*     */       }
/*     */       
/* 192 */       encodeTermination();
/*     */       
/* 194 */       encodeProcessingInstruction(target, data);
/* 195 */     } catch (IOException e) {
/* 196 */       throw new SAXException("processingInstruction", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setDocumentLocator(Locator locator) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public final void skippedEntity(String name) throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public final void comment(char[] ch, int start, int length) throws SAXException {
/*     */     try {
/* 212 */       if (getIgnoreComments())
/*     */         return; 
/* 214 */       encodeTermination();
/*     */       
/* 216 */       encodeComment(ch, start, length);
/* 217 */     } catch (IOException e) {
/* 218 */       throw new SAXException("startElement", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void startCDATA() throws SAXException {
/* 223 */     this._charactersAsCDATA = true;
/*     */   }
/*     */   
/*     */   public final void endCDATA() throws SAXException {
/* 227 */     this._charactersAsCDATA = false;
/*     */   }
/*     */   
/*     */   public final void startDTD(String name, String publicId, String systemId) throws SAXException {
/* 231 */     if (getIgnoreDTD())
/*     */       return; 
/*     */     try {
/* 234 */       encodeTermination();
/*     */       
/* 236 */       encodeDocumentTypeDeclaration(publicId, systemId);
/* 237 */       encodeElementTermination();
/* 238 */     } catch (IOException e) {
/* 239 */       throw new SAXException("startDTD", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void endDTD() throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public final void startEntity(String name) throws SAXException {}
/*     */ 
/*     */   
/*     */   public final void endEntity(String name) throws SAXException {}
/*     */ 
/*     */   
/*     */   public final void octets(String URI, int id, byte[] b, int start, int length) throws SAXException {
/* 256 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 261 */       encodeTermination();
/*     */       
/* 263 */       encodeNonIdentifyingStringOnThirdBit(URI, id, b, start, length);
/* 264 */     } catch (IOException e) {
/* 265 */       throw new SAXException(e);
/* 266 */     } catch (FastInfosetException e) {
/* 267 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void object(String URI, int id, Object data) throws SAXException {
/*     */     try {
/* 273 */       encodeTermination();
/*     */       
/* 275 */       encodeNonIdentifyingStringOnThirdBit(URI, id, data);
/* 276 */     } catch (IOException e) {
/* 277 */       throw new SAXException(e);
/* 278 */     } catch (FastInfosetException e) {
/* 279 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void bytes(byte[] b, int start, int length) throws SAXException {
/* 287 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 292 */       encodeTermination();
/*     */       
/* 294 */       encodeCIIOctetAlgorithmData(1, b, start, length);
/* 295 */     } catch (IOException e) {
/* 296 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void shorts(short[] s, int start, int length) throws SAXException {
/* 301 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 306 */       encodeTermination();
/*     */       
/* 308 */       encodeCIIBuiltInAlgorithmData(2, s, start, length);
/* 309 */     } catch (IOException e) {
/* 310 */       throw new SAXException(e);
/* 311 */     } catch (FastInfosetException e) {
/* 312 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void ints(int[] i, int start, int length) throws SAXException {
/* 317 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 322 */       encodeTermination();
/*     */       
/* 324 */       encodeCIIBuiltInAlgorithmData(3, i, start, length);
/* 325 */     } catch (IOException e) {
/* 326 */       throw new SAXException(e);
/* 327 */     } catch (FastInfosetException e) {
/* 328 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void longs(long[] l, int start, int length) throws SAXException {
/* 333 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 338 */       encodeTermination();
/*     */       
/* 340 */       encodeCIIBuiltInAlgorithmData(4, l, start, length);
/* 341 */     } catch (IOException e) {
/* 342 */       throw new SAXException(e);
/* 343 */     } catch (FastInfosetException e) {
/* 344 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void booleans(boolean[] b, int start, int length) throws SAXException {
/* 349 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 354 */       encodeTermination();
/*     */       
/* 356 */       encodeCIIBuiltInAlgorithmData(5, b, start, length);
/* 357 */     } catch (IOException e) {
/* 358 */       throw new SAXException(e);
/* 359 */     } catch (FastInfosetException e) {
/* 360 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void floats(float[] f, int start, int length) throws SAXException {
/* 365 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 370 */       encodeTermination();
/*     */       
/* 372 */       encodeCIIBuiltInAlgorithmData(6, f, start, length);
/* 373 */     } catch (IOException e) {
/* 374 */       throw new SAXException(e);
/* 375 */     } catch (FastInfosetException e) {
/* 376 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void doubles(double[] d, int start, int length) throws SAXException {
/* 381 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 386 */       encodeTermination();
/*     */       
/* 388 */       encodeCIIBuiltInAlgorithmData(7, d, start, length);
/* 389 */     } catch (IOException e) {
/* 390 */       throw new SAXException(e);
/* 391 */     } catch (FastInfosetException e) {
/* 392 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void uuids(long[] msblsb, int start, int length) throws SAXException {
/* 397 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 402 */       encodeTermination();
/*     */       
/* 404 */       encodeCIIBuiltInAlgorithmData(8, msblsb, start, length);
/* 405 */     } catch (IOException e) {
/* 406 */       throw new SAXException(e);
/* 407 */     } catch (FastInfosetException e) {
/* 408 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void numericCharacters(char[] ch, int start, int length) throws SAXException {
/* 416 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 421 */       encodeTermination();
/*     */       
/* 423 */       boolean addToTable = isCharacterContentChunkLengthMatchesLimit(length);
/* 424 */       encodeNumericFourBitCharacters(ch, start, length, addToTable);
/* 425 */     } catch (IOException e) {
/* 426 */       throw new SAXException(e);
/* 427 */     } catch (FastInfosetException e) {
/* 428 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dateTimeCharacters(char[] ch, int start, int length) throws SAXException {
/* 433 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 438 */       encodeTermination();
/*     */       
/* 440 */       boolean addToTable = isCharacterContentChunkLengthMatchesLimit(length);
/* 441 */       encodeDateTimeFourBitCharacters(ch, start, length, addToTable);
/* 442 */     } catch (IOException e) {
/* 443 */       throw new SAXException(e);
/* 444 */     } catch (FastInfosetException e) {
/* 445 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void alphabetCharacters(String alphabet, char[] ch, int start, int length) throws SAXException {
/* 450 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 455 */       encodeTermination();
/*     */       
/* 457 */       boolean addToTable = isCharacterContentChunkLengthMatchesLimit(length);
/* 458 */       encodeAlphabetCharacters(alphabet, ch, start, length, addToTable);
/* 459 */     } catch (IOException e) {
/* 460 */       throw new SAXException(e);
/* 461 */     } catch (FastInfosetException e) {
/* 462 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void characters(char[] ch, int start, int length, boolean index) throws SAXException {
/* 469 */     if (length <= 0) {
/*     */       return;
/*     */     }
/*     */     
/* 473 */     if (getIgnoreWhiteSpaceTextContent() && isWhiteSpace(ch, start, length)) {
/*     */       return;
/*     */     }
/*     */     try {
/* 477 */       encodeTermination();
/*     */       
/* 479 */       if (!this._charactersAsCDATA) {
/* 480 */         encodeNonIdentifyingStringOnThirdBit(ch, start, length, this._v.characterContentChunk, index, true);
/*     */       } else {
/* 482 */         encodeCIIBuiltInAlgorithmDataAsCDATA(ch, start, length);
/*     */       } 
/* 484 */     } catch (IOException e) {
/* 485 */       throw new SAXException(e);
/* 486 */     } catch (FastInfosetException e) {
/* 487 */       throw new SAXException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final int countAttributes(Attributes atts) {
/* 496 */     int count = 0;
/* 497 */     for (int i = 0; i < atts.getLength(); i++) {
/* 498 */       String uri = atts.getURI(i);
/* 499 */       if (uri != "http://www.w3.org/2000/xmlns/" && !uri.equals("http://www.w3.org/2000/xmlns/"))
/*     */       {
/*     */         
/* 502 */         count++; } 
/*     */     } 
/* 504 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void encodeAttributes(Attributes atts) throws IOException, FastInfosetException {
/* 511 */     if (atts instanceof EncodingAlgorithmAttributes) {
/* 512 */       EncodingAlgorithmAttributes eAtts = (EncodingAlgorithmAttributes)atts;
/*     */ 
/*     */       
/* 515 */       for (int i = 0; i < eAtts.getLength(); i++) {
/* 516 */         if (encodeAttribute(atts.getURI(i), atts.getQName(i), atts.getLocalName(i))) {
/* 517 */           Object data = eAtts.getAlgorithmData(i);
/*     */           
/* 519 */           if (data == null) {
/* 520 */             String value = eAtts.getValue(i);
/* 521 */             boolean addToTable = isAttributeValueLengthMatchesLimit(value.length());
/* 522 */             boolean mustBeAddedToTable = eAtts.getToIndex(i);
/*     */             
/* 524 */             String alphabet = eAtts.getAlpababet(i);
/* 525 */             if (alphabet == null) {
/* 526 */               encodeNonIdentifyingStringOnFirstBit(value, this._v.attributeValue, addToTable, mustBeAddedToTable);
/* 527 */             } else if (alphabet == "0123456789-:TZ ") {
/* 528 */               encodeDateTimeNonIdentifyingStringOnFirstBit(value, addToTable, mustBeAddedToTable);
/*     */             }
/* 530 */             else if (alphabet == "0123456789-+.E ") {
/* 531 */               encodeNumericNonIdentifyingStringOnFirstBit(value, addToTable, mustBeAddedToTable);
/*     */             } else {
/*     */               
/* 534 */               encodeNonIdentifyingStringOnFirstBit(value, this._v.attributeValue, addToTable, mustBeAddedToTable);
/*     */             } 
/*     */           } else {
/* 537 */             encodeNonIdentifyingStringOnFirstBit(eAtts.getAlgorithmURI(i), eAtts.getAlgorithmIndex(i), data);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 543 */       for (int i = 0; i < atts.getLength(); i++) {
/* 544 */         if (encodeAttribute(atts.getURI(i), atts.getQName(i), atts.getLocalName(i))) {
/* 545 */           String value = atts.getValue(i);
/* 546 */           boolean addToTable = isAttributeValueLengthMatchesLimit(value.length());
/* 547 */           encodeNonIdentifyingStringOnFirstBit(value, this._v.attributeValue, addToTable, false);
/*     */         } 
/*     */       } 
/*     */     } 
/* 551 */     this._b = 240;
/* 552 */     this._terminate = true;
/*     */   }
/*     */   
/*     */   protected void encodeElement(String namespaceURI, String qName, String localName) throws IOException {
/* 556 */     LocalNameQualifiedNamesMap.Entry entry = this._v.elementName.obtainEntry(qName);
/* 557 */     if (entry._valueIndex > 0) {
/* 558 */       QualifiedName[] names = entry._value;
/* 559 */       for (int i = 0; i < entry._valueIndex; i++) {
/* 560 */         QualifiedName n = names[i];
/* 561 */         if (namespaceURI == n.namespaceName || namespaceURI.equals(n.namespaceName)) {
/* 562 */           encodeNonZeroIntegerOnThirdBit((names[i]).index);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 568 */     encodeLiteralElementQualifiedNameOnThirdBit(namespaceURI, getPrefixFromQualifiedName(qName), localName, entry);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean encodeAttribute(String namespaceURI, String qName, String localName) throws IOException {
/* 573 */     LocalNameQualifiedNamesMap.Entry entry = this._v.attributeName.obtainEntry(qName);
/* 574 */     if (entry._valueIndex > 0) {
/* 575 */       QualifiedName[] names = entry._value;
/* 576 */       for (int i = 0; i < entry._valueIndex; i++) {
/* 577 */         if (namespaceURI == (names[i]).namespaceName || namespaceURI.equals((names[i]).namespaceName)) {
/* 578 */           encodeNonZeroIntegerOnSecondBitFirstBitZero((names[i]).index);
/* 579 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 584 */     return encodeLiteralAttributeQualifiedNameOnSecondBit(namespaceURI, getPrefixFromQualifiedName(qName), localName, entry);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\sax\SAXDocumentSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */