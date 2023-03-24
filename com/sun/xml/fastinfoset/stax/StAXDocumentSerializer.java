/*     */ package com.sun.xml.fastinfoset.stax;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import com.sun.xml.fastinfoset.Encoder;
/*     */ import com.sun.xml.fastinfoset.QualifiedName;
/*     */ import com.sun.xml.fastinfoset.util.LocalNameQualifiedNamesMap;
/*     */ import com.sun.xml.fastinfoset.util.NamespaceContextImplementation;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.EmptyStackException;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.fastinfoset.stax.LowLevelFastInfosetStreamWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXDocumentSerializer
/*     */   extends Encoder
/*     */   implements XMLStreamWriter, LowLevelFastInfosetStreamWriter
/*     */ {
/*     */   protected StAXManager _manager;
/*     */   protected String _encoding;
/*     */   protected String _currentLocalName;
/*     */   protected String _currentUri;
/*     */   protected String _currentPrefix;
/*     */   protected boolean _inStartElement = false;
/*     */   protected boolean _isEmptyElement = false;
/*  78 */   protected String[] _attributesArray = new String[64];
/*  79 */   protected int _attributesArrayIndex = 0;
/*     */   
/*  81 */   protected boolean[] _nsSupportContextStack = new boolean[32];
/*  82 */   protected int _stackCount = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  87 */   protected NamespaceContextImplementation _nsContext = new NamespaceContextImplementation();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  93 */   protected String[] _namespacesArray = new String[16];
/*  94 */   protected int _namespacesArrayIndex = 0;
/*     */   
/*     */   public StAXDocumentSerializer() {
/*  97 */     super(true);
/*  98 */     this._manager = new StAXManager(2);
/*     */   }
/*     */   
/*     */   public StAXDocumentSerializer(OutputStream outputStream) {
/* 102 */     super(true);
/* 103 */     setOutputStream(outputStream);
/* 104 */     this._manager = new StAXManager(2);
/*     */   }
/*     */   
/*     */   public StAXDocumentSerializer(OutputStream outputStream, StAXManager manager) {
/* 108 */     super(true);
/* 109 */     setOutputStream(outputStream);
/* 110 */     this._manager = manager;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 114 */     super.reset();
/*     */     
/* 116 */     this._attributesArrayIndex = 0;
/* 117 */     this._namespacesArrayIndex = 0;
/*     */     
/* 119 */     this._nsContext.reset();
/* 120 */     this._stackCount = -1;
/*     */     
/* 122 */     this._currentUri = this._currentPrefix = null;
/* 123 */     this._currentLocalName = null;
/*     */     
/* 125 */     this._inStartElement = this._isEmptyElement = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {
/* 131 */     writeStartDocument("finf", "1.0");
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String version) throws XMLStreamException {
/* 135 */     writeStartDocument("finf", version);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
/* 141 */     reset();
/*     */     
/*     */     try {
/* 144 */       encodeHeader(false);
/* 145 */       encodeInitialVocabulary();
/* 146 */     } catch (IOException e) {
/* 147 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {
/*     */     try {
/* 156 */       for (; this._stackCount >= 0; this._stackCount--) {
/* 157 */         writeEndElement();
/*     */       }
/*     */       
/* 160 */       encodeDocumentTermination();
/*     */     }
/* 162 */     catch (IOException e) {
/* 163 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() throws XMLStreamException {
/* 168 */     reset();
/*     */   }
/*     */   
/*     */   public void flush() throws XMLStreamException {
/*     */     try {
/* 173 */       this._s.flush();
/*     */     }
/* 175 */     catch (IOException e) {
/* 176 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartElement(String localName) throws XMLStreamException {
/* 184 */     writeStartElement("", localName, "");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 190 */     writeStartElement("", localName, namespaceURI);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 196 */     encodeTerminationAndCurrentElement(false);
/*     */     
/* 198 */     this._inStartElement = true;
/* 199 */     this._isEmptyElement = false;
/*     */     
/* 201 */     this._currentLocalName = localName;
/* 202 */     this._currentPrefix = prefix;
/* 203 */     this._currentUri = namespaceURI;
/*     */     
/* 205 */     this._stackCount++;
/* 206 */     if (this._stackCount == this._nsSupportContextStack.length) {
/* 207 */       boolean[] nsSupportContextStack = new boolean[this._stackCount * 2];
/* 208 */       System.arraycopy(this._nsSupportContextStack, 0, nsSupportContextStack, 0, this._nsSupportContextStack.length);
/* 209 */       this._nsSupportContextStack = nsSupportContextStack;
/*     */     } 
/*     */     
/* 212 */     this._nsSupportContextStack[this._stackCount] = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String localName) throws XMLStreamException {
/* 218 */     writeEmptyElement("", localName, "");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
/* 224 */     writeEmptyElement("", localName, namespaceURI);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 230 */     encodeTerminationAndCurrentElement(false);
/*     */     
/* 232 */     this._isEmptyElement = this._inStartElement = true;
/*     */     
/* 234 */     this._currentLocalName = localName;
/* 235 */     this._currentPrefix = prefix;
/* 236 */     this._currentUri = namespaceURI;
/*     */     
/* 238 */     this._stackCount++;
/* 239 */     if (this._stackCount == this._nsSupportContextStack.length) {
/* 240 */       boolean[] nsSupportContextStack = new boolean[this._stackCount * 2];
/* 241 */       System.arraycopy(this._nsSupportContextStack, 0, nsSupportContextStack, 0, this._nsSupportContextStack.length);
/* 242 */       this._nsSupportContextStack = nsSupportContextStack;
/*     */     } 
/*     */     
/* 245 */     this._nsSupportContextStack[this._stackCount] = false;
/*     */   }
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/* 249 */     if (this._inStartElement) {
/* 250 */       encodeTerminationAndCurrentElement(false);
/*     */     }
/*     */     
/*     */     try {
/* 254 */       encodeElementTermination();
/* 255 */       if (this._nsSupportContextStack[this._stackCount--] == true) {
/* 256 */         this._nsContext.popContext();
/*     */       }
/*     */     }
/* 259 */     catch (IOException e) {
/* 260 */       throw new XMLStreamException(e);
/*     */     }
/* 262 */     catch (EmptyStackException e) {
/* 263 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttribute(String localName, String value) throws XMLStreamException {
/* 271 */     writeAttribute("", "", localName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
/* 277 */     String prefix = "";
/*     */ 
/*     */     
/* 280 */     if (namespaceURI.length() > 0) {
/* 281 */       prefix = this._nsContext.getNonDefaultPrefix(namespaceURI);
/*     */ 
/*     */       
/* 284 */       if (prefix == null || prefix.length() == 0) {
/*     */ 
/*     */         
/* 287 */         if (namespaceURI == "http://www.w3.org/2000/xmlns/" || namespaceURI.equals("http://www.w3.org/2000/xmlns/")) {
/*     */           return;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 296 */         throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.URIUnbound", new Object[] { namespaceURI }));
/*     */       } 
/*     */     } 
/* 299 */     writeAttribute(prefix, namespaceURI, localName, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
/* 305 */     if (!this._inStartElement) {
/* 306 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.attributeWritingNotAllowed"));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 314 */     if (namespaceURI == "http://www.w3.org/2000/xmlns/" || namespaceURI.equals("http://www.w3.org/2000/xmlns/")) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 319 */     if (this._attributesArrayIndex == this._attributesArray.length) {
/* 320 */       String[] attributesArray = new String[this._attributesArrayIndex * 2];
/* 321 */       System.arraycopy(this._attributesArray, 0, attributesArray, 0, this._attributesArrayIndex);
/* 322 */       this._attributesArray = attributesArray;
/*     */     } 
/*     */     
/* 325 */     this._attributesArray[this._attributesArrayIndex++] = namespaceURI;
/* 326 */     this._attributesArray[this._attributesArrayIndex++] = prefix;
/* 327 */     this._attributesArray[this._attributesArrayIndex++] = localName;
/* 328 */     this._attributesArray[this._attributesArrayIndex++] = value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
/* 334 */     if (prefix == null || prefix.length() == 0 || prefix.equals("xmlns")) {
/* 335 */       writeDefaultNamespace(namespaceURI);
/*     */     } else {
/*     */       
/* 338 */       if (!this._inStartElement) {
/* 339 */         throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.attributeWritingNotAllowed"));
/*     */       }
/*     */       
/* 342 */       if (this._namespacesArrayIndex == this._namespacesArray.length) {
/* 343 */         String[] namespacesArray = new String[this._namespacesArrayIndex * 2];
/* 344 */         System.arraycopy(this._namespacesArray, 0, namespacesArray, 0, this._namespacesArrayIndex);
/* 345 */         this._namespacesArray = namespacesArray;
/*     */       } 
/*     */       
/* 348 */       this._namespacesArray[this._namespacesArrayIndex++] = prefix;
/* 349 */       this._namespacesArray[this._namespacesArrayIndex++] = namespaceURI;
/* 350 */       setPrefix(prefix, namespaceURI);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
/* 357 */     if (!this._inStartElement) {
/* 358 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.attributeWritingNotAllowed"));
/*     */     }
/*     */     
/* 361 */     if (this._namespacesArrayIndex == this._namespacesArray.length) {
/* 362 */       String[] namespacesArray = new String[this._namespacesArrayIndex * 2];
/* 363 */       System.arraycopy(this._namespacesArray, 0, namespacesArray, 0, this._namespacesArrayIndex);
/* 364 */       this._namespacesArray = namespacesArray;
/*     */     } 
/*     */     
/* 367 */     this._namespacesArray[this._namespacesArrayIndex++] = "";
/* 368 */     this._namespacesArray[this._namespacesArrayIndex++] = namespaceURI;
/* 369 */     setPrefix("", namespaceURI);
/*     */   }
/*     */   
/*     */   public void writeComment(String data) throws XMLStreamException {
/*     */     try {
/* 374 */       if (getIgnoreComments())
/*     */         return; 
/* 376 */       encodeTerminationAndCurrentElement(true);
/*     */ 
/*     */       
/* 379 */       encodeComment(data.toCharArray(), 0, data.length());
/*     */     }
/* 381 */     catch (IOException e) {
/* 382 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeProcessingInstruction(String target) throws XMLStreamException {
/* 389 */     writeProcessingInstruction(target, "");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
/*     */     try {
/* 396 */       if (getIgnoreProcesingInstructions())
/*     */         return; 
/* 398 */       encodeTerminationAndCurrentElement(true);
/*     */       
/* 400 */       encodeProcessingInstruction(target, data);
/*     */     }
/* 402 */     catch (IOException e) {
/* 403 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeCData(String text) throws XMLStreamException {
/*     */     try {
/* 409 */       int length = text.length();
/* 410 */       if (length == 0)
/*     */         return; 
/* 412 */       if (length < this._charBuffer.length) {
/* 413 */         if (getIgnoreWhiteSpaceTextContent() && isWhiteSpace(text)) {
/*     */           return;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 420 */         encodeTerminationAndCurrentElement(true);
/*     */         
/* 422 */         text.getChars(0, length, this._charBuffer, 0);
/* 423 */         encodeCIIBuiltInAlgorithmDataAsCDATA(this._charBuffer, 0, length);
/*     */       } else {
/* 425 */         char[] ch = text.toCharArray();
/* 426 */         if (getIgnoreWhiteSpaceTextContent() && isWhiteSpace(ch, 0, length)) {
/*     */           return;
/*     */         }
/* 429 */         encodeTerminationAndCurrentElement(true);
/*     */         
/* 431 */         encodeCIIBuiltInAlgorithmDataAsCDATA(ch, 0, length);
/*     */       } 
/* 433 */     } catch (Exception e) {
/* 434 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeDTD(String dtd) throws XMLStreamException {
/* 439 */     throw new UnsupportedOperationException(CommonResourceBundle.getInstance().getString("message.notImplemented"));
/*     */   }
/*     */   
/*     */   public void writeEntityRef(String name) throws XMLStreamException {
/* 443 */     throw new UnsupportedOperationException(CommonResourceBundle.getInstance().getString("message.notImplemented"));
/*     */   }
/*     */   
/*     */   public void writeCharacters(String text) throws XMLStreamException {
/*     */     try {
/* 448 */       int length = text.length();
/* 449 */       if (length == 0)
/*     */         return; 
/* 451 */       if (length < this._charBuffer.length) {
/* 452 */         if (getIgnoreWhiteSpaceTextContent() && isWhiteSpace(text)) {
/*     */           return;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 459 */         encodeTerminationAndCurrentElement(true);
/*     */         
/* 461 */         text.getChars(0, length, this._charBuffer, 0);
/* 462 */         encodeCharacters(this._charBuffer, 0, length);
/*     */       } else {
/* 464 */         char[] ch = text.toCharArray();
/* 465 */         if (getIgnoreWhiteSpaceTextContent() && isWhiteSpace(ch, 0, length)) {
/*     */           return;
/*     */         }
/* 468 */         encodeTerminationAndCurrentElement(true);
/*     */         
/* 470 */         encodeCharactersNoClone(ch, 0, length);
/*     */       }
/*     */     
/* 473 */     } catch (IOException e) {
/* 474 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
/*     */     try {
/* 482 */       if (len <= 0) {
/*     */         return;
/*     */       }
/*     */       
/* 486 */       if (getIgnoreWhiteSpaceTextContent() && isWhiteSpace(text, start, len)) {
/*     */         return;
/*     */       }
/* 489 */       encodeTerminationAndCurrentElement(true);
/*     */       
/* 491 */       encodeCharacters(text, start, len);
/*     */     }
/* 493 */     catch (IOException e) {
/* 494 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getPrefix(String uri) throws XMLStreamException {
/* 499 */     return this._nsContext.getPrefix(uri);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrefix(String prefix, String uri) throws XMLStreamException {
/* 505 */     if (this._stackCount > -1 && !this._nsSupportContextStack[this._stackCount]) {
/* 506 */       this._nsSupportContextStack[this._stackCount] = true;
/* 507 */       this._nsContext.pushContext();
/*     */     } 
/*     */     
/* 510 */     this._nsContext.declarePrefix(prefix, uri);
/*     */   }
/*     */   
/*     */   public void setDefaultNamespace(String uri) throws XMLStreamException {
/* 514 */     setPrefix("", uri);
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
/*     */   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
/* 534 */     throw new UnsupportedOperationException("setNamespaceContext");
/*     */   }
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 538 */     return (NamespaceContext)this._nsContext;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 544 */     if (this._manager != null) {
/* 545 */       return this._manager.getProperty(name);
/*     */     }
/* 547 */     return null;
/*     */   }
/*     */   
/*     */   public void setManager(StAXManager manager) {
/* 551 */     this._manager = manager;
/*     */   }
/*     */   
/*     */   public void setEncoding(String encoding) {
/* 555 */     this._encoding = encoding;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeOctets(byte[] b, int start, int len) throws XMLStreamException {
/*     */     try {
/* 563 */       if (len == 0) {
/*     */         return;
/*     */       }
/*     */       
/* 567 */       encodeTerminationAndCurrentElement(true);
/*     */       
/* 569 */       encodeCIIOctetAlgorithmData(1, b, start, len);
/*     */     }
/* 571 */     catch (IOException e) {
/* 572 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void encodeTerminationAndCurrentElement(boolean terminateAfter) throws XMLStreamException {
/*     */     try {
/* 578 */       encodeTermination();
/*     */       
/* 580 */       if (this._inStartElement) {
/*     */         
/* 582 */         this._b = 0;
/* 583 */         if (this._attributesArrayIndex > 0) {
/* 584 */           this._b |= 0x40;
/*     */         }
/*     */ 
/*     */         
/* 588 */         if (this._namespacesArrayIndex > 0) {
/* 589 */           write(this._b | 0x38);
/* 590 */           for (int j = 0; j < this._namespacesArrayIndex;) {
/* 591 */             encodeNamespaceAttribute(this._namespacesArray[j++], this._namespacesArray[j++]);
/*     */           }
/* 593 */           this._namespacesArrayIndex = 0;
/*     */           
/* 595 */           write(240);
/*     */           
/* 597 */           this._b = 0;
/*     */         } 
/*     */ 
/*     */         
/* 601 */         if (this._currentPrefix.length() == 0) {
/* 602 */           if (this._currentUri.length() == 0) {
/* 603 */             this._currentUri = this._nsContext.getNamespaceURI("");
/*     */           } else {
/* 605 */             String tmpPrefix = getPrefix(this._currentUri);
/* 606 */             if (tmpPrefix != null) {
/* 607 */               this._currentPrefix = tmpPrefix;
/*     */             }
/*     */           } 
/*     */         }
/*     */         
/* 612 */         encodeElementQualifiedNameOnThirdBit(this._currentUri, this._currentPrefix, this._currentLocalName);
/*     */         
/* 614 */         for (int i = 0; i < this._attributesArrayIndex; ) {
/* 615 */           encodeAttributeQualifiedNameOnSecondBit(this._attributesArray[i++], this._attributesArray[i++], this._attributesArray[i++]);
/*     */ 
/*     */           
/* 618 */           String value = this._attributesArray[i];
/* 619 */           this._attributesArray[i++] = null;
/* 620 */           boolean addToTable = isAttributeValueLengthMatchesLimit(value.length());
/* 621 */           encodeNonIdentifyingStringOnFirstBit(value, this._v.attributeValue, addToTable, false);
/*     */           
/* 623 */           this._b = 240;
/* 624 */           this._terminate = true;
/*     */         } 
/* 626 */         this._attributesArrayIndex = 0;
/* 627 */         this._inStartElement = false;
/*     */         
/* 629 */         if (this._isEmptyElement) {
/* 630 */           encodeElementTermination();
/* 631 */           if (this._nsSupportContextStack[this._stackCount--] == true) {
/* 632 */             this._nsContext.popContext();
/*     */           }
/*     */           
/* 635 */           this._isEmptyElement = false;
/*     */         } 
/*     */         
/* 638 */         if (terminateAfter) {
/* 639 */           encodeTermination();
/*     */         }
/*     */       } 
/* 642 */     } catch (IOException e) {
/* 643 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void initiateLowLevelWriting() throws XMLStreamException {
/* 651 */     encodeTerminationAndCurrentElement(false);
/*     */   }
/*     */   
/*     */   public final int getNextElementIndex() {
/* 655 */     return this._v.elementName.getNextIndex();
/*     */   }
/*     */   
/*     */   public final int getNextAttributeIndex() {
/* 659 */     return this._v.attributeName.getNextIndex();
/*     */   }
/*     */   
/*     */   public final int getLocalNameIndex() {
/* 663 */     return this._v.localName.getIndex();
/*     */   }
/*     */   
/*     */   public final int getNextLocalNameIndex() {
/* 667 */     return this._v.localName.getNextIndex();
/*     */   }
/*     */   
/*     */   public final void writeLowLevelTerminationAndMark() throws IOException {
/* 671 */     encodeTermination();
/* 672 */     mark();
/*     */   }
/*     */   
/*     */   public final void writeLowLevelStartElementIndexed(int type, int index) throws IOException {
/* 676 */     this._b = type;
/* 677 */     encodeNonZeroIntegerOnThirdBit(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean writeLowLevelStartElement(int type, String prefix, String localName, String namespaceURI) throws IOException {
/* 682 */     boolean isIndexed = encodeElement(type, namespaceURI, prefix, localName);
/*     */     
/* 684 */     if (!isIndexed) {
/* 685 */       encodeLiteral(type | 0x3C, namespaceURI, prefix, localName);
/*     */     }
/*     */     
/* 688 */     return isIndexed;
/*     */   }
/*     */   
/*     */   public final void writeLowLevelStartNamespaces() throws IOException {
/* 692 */     write(56);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeLowLevelNamespace(String prefix, String namespaceName) throws IOException {
/* 697 */     encodeNamespaceAttribute(prefix, namespaceName);
/*     */   }
/*     */   
/*     */   public final void writeLowLevelEndNamespaces() throws IOException {
/* 701 */     write(240);
/*     */   }
/*     */   
/*     */   public final void writeLowLevelStartAttributes() throws IOException {
/* 705 */     if (hasMark()) {
/* 706 */       this._octetBuffer[this._markIndex] = (byte)(this._octetBuffer[this._markIndex] | 0x40);
/* 707 */       resetMark();
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void writeLowLevelAttributeIndexed(int index) throws IOException {
/* 712 */     encodeNonZeroIntegerOnSecondBitFirstBitZero(index);
/*     */   }
/*     */   
/*     */   public final boolean writeLowLevelAttribute(String prefix, String namespaceURI, String localName) throws IOException {
/* 716 */     boolean isIndexed = encodeAttribute(namespaceURI, prefix, localName);
/*     */     
/* 718 */     if (!isIndexed) {
/* 719 */       encodeLiteral(120, namespaceURI, prefix, localName);
/*     */     }
/*     */     
/* 722 */     return isIndexed;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeLowLevelAttributeValue(String value) throws IOException {
/* 727 */     boolean addToTable = isAttributeValueLengthMatchesLimit(value.length());
/* 728 */     encodeNonIdentifyingStringOnFirstBit(value, this._v.attributeValue, addToTable, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeLowLevelStartNameLiteral(int type, String prefix, byte[] utf8LocalName, String namespaceURI) throws IOException {
/* 733 */     encodeLiteralHeader(type, namespaceURI, prefix);
/* 734 */     encodeNonZeroOctetStringLengthOnSecondBit(utf8LocalName.length);
/* 735 */     write(utf8LocalName, 0, utf8LocalName.length);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void writeLowLevelStartNameLiteral(int type, String prefix, int localNameIndex, String namespaceURI) throws IOException {
/* 740 */     encodeLiteralHeader(type, namespaceURI, prefix);
/* 741 */     encodeNonZeroIntegerOnSecondBitFirstBitOne(localNameIndex);
/*     */   }
/*     */   
/*     */   public final void writeLowLevelEndStartElement() throws IOException {
/* 745 */     if (hasMark()) {
/* 746 */       resetMark();
/*     */     } else {
/*     */       
/* 749 */       this._b = 240;
/* 750 */       this._terminate = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void writeLowLevelEndElement() throws IOException {
/* 755 */     encodeElementTermination();
/*     */   }
/*     */   
/*     */   public final void writeLowLevelText(char[] text, int length) throws IOException {
/* 759 */     if (length == 0) {
/*     */       return;
/*     */     }
/* 762 */     encodeTermination();
/*     */     
/* 764 */     encodeCharacters(text, 0, length);
/*     */   }
/*     */   
/*     */   public final void writeLowLevelText(String text) throws IOException {
/* 768 */     int length = text.length();
/* 769 */     if (length == 0) {
/*     */       return;
/*     */     }
/* 772 */     encodeTermination();
/*     */     
/* 774 */     if (length < this._charBuffer.length) {
/* 775 */       text.getChars(0, length, this._charBuffer, 0);
/* 776 */       encodeCharacters(this._charBuffer, 0, length);
/*     */     } else {
/* 778 */       char[] ch = text.toCharArray();
/* 779 */       encodeCharactersNoClone(ch, 0, length);
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void writeLowLevelOctets(byte[] octets, int length) throws IOException {
/* 784 */     if (length == 0) {
/*     */       return;
/*     */     }
/* 787 */     encodeTermination();
/*     */     
/* 789 */     encodeCIIOctetAlgorithmData(1, octets, 0, length);
/*     */   }
/*     */   
/*     */   private boolean encodeElement(int type, String namespaceURI, String prefix, String localName) throws IOException {
/* 793 */     LocalNameQualifiedNamesMap.Entry entry = this._v.elementName.obtainEntry(localName);
/* 794 */     for (int i = 0; i < entry._valueIndex; i++) {
/* 795 */       QualifiedName name = entry._value[i];
/* 796 */       if ((prefix == name.prefix || prefix.equals(name.prefix)) && (namespaceURI == name.namespaceName || namespaceURI.equals(name.namespaceName))) {
/*     */         
/* 798 */         this._b = type;
/* 799 */         encodeNonZeroIntegerOnThirdBit(name.index);
/* 800 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 804 */     entry.addQualifiedName(new QualifiedName(prefix, namespaceURI, localName, "", this._v.elementName.getNextIndex()));
/* 805 */     return false;
/*     */   }
/*     */   
/*     */   private boolean encodeAttribute(String namespaceURI, String prefix, String localName) throws IOException {
/* 809 */     LocalNameQualifiedNamesMap.Entry entry = this._v.attributeName.obtainEntry(localName);
/* 810 */     for (int i = 0; i < entry._valueIndex; i++) {
/* 811 */       QualifiedName name = entry._value[i];
/* 812 */       if ((prefix == name.prefix || prefix.equals(name.prefix)) && (namespaceURI == name.namespaceName || namespaceURI.equals(name.namespaceName))) {
/*     */         
/* 814 */         encodeNonZeroIntegerOnSecondBitFirstBitZero(name.index);
/* 815 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 819 */     entry.addQualifiedName(new QualifiedName(prefix, namespaceURI, localName, "", this._v.attributeName.getNextIndex()));
/* 820 */     return false;
/*     */   }
/*     */   
/*     */   private void encodeLiteralHeader(int type, String namespaceURI, String prefix) throws IOException {
/* 824 */     if (namespaceURI != "") {
/* 825 */       type |= 0x1;
/* 826 */       if (prefix != "") {
/* 827 */         type |= 0x2;
/*     */       }
/* 829 */       write(type);
/* 830 */       if (prefix != "")
/* 831 */         encodeNonZeroIntegerOnSecondBitFirstBitOne(this._v.prefix.get(prefix)); 
/* 832 */       encodeNonZeroIntegerOnSecondBitFirstBitOne(this._v.namespaceName.get(namespaceURI));
/*     */     } else {
/* 834 */       write(type);
/*     */     } 
/*     */   }
/*     */   private void encodeLiteral(int type, String namespaceURI, String prefix, String localName) throws IOException {
/* 838 */     encodeLiteralHeader(type, namespaceURI, prefix);
/*     */     
/* 840 */     int localNameIndex = this._v.localName.obtainIndex(localName);
/* 841 */     if (localNameIndex == -1) {
/* 842 */       encodeNonEmptyOctetStringOnSecondBit(localName);
/*     */     } else {
/* 844 */       encodeNonZeroIntegerOnSecondBitFirstBitOne(localNameIndex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\StAXDocumentSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */