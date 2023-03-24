/*     */ package com.sun.xml.stream.buffer.sax;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.AbstractProcessor;
/*     */ import com.sun.xml.stream.buffer.AttributesHolder;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.LocatorImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAXBufferProcessor
/*     */   extends AbstractProcessor
/*     */   implements XMLReader
/*     */ {
/*  74 */   protected EntityResolver _entityResolver = DEFAULT_LEXICAL_HANDLER;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   protected DTDHandler _dtdHandler = DEFAULT_LEXICAL_HANDLER;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  84 */   protected ContentHandler _contentHandler = DEFAULT_LEXICAL_HANDLER;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  89 */   protected ErrorHandler _errorHandler = DEFAULT_LEXICAL_HANDLER;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  94 */   protected LexicalHandler _lexicalHandler = DEFAULT_LEXICAL_HANDLER;
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _namespacePrefixesFeature = false;
/*     */ 
/*     */   
/* 101 */   protected AttributesHolder _attributes = new AttributesHolder();
/*     */   
/* 103 */   protected String[] _namespacePrefixes = new String[16];
/*     */   
/*     */   protected int _namespacePrefixesIndex;
/* 106 */   protected int[] _namespaceAttributesStartingStack = new int[16];
/* 107 */   protected int[] _namespaceAttributesStack = new int[16];
/*     */ 
/*     */   
/*     */   protected int _namespaceAttributesStackIndex;
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXBufferProcessor() {}
/*     */ 
/*     */   
/*     */   public SAXBufferProcessor(XMLStreamBuffer buffer) {
/* 118 */     setXMLStreamBuffer(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SAXBufferProcessor(XMLStreamBuffer buffer, boolean produceFragmentEvent) {
/* 127 */     setXMLStreamBuffer(buffer, produceFragmentEvent);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 132 */     if (name.equals("http://xml.org/sax/features/namespaces"))
/* 133 */       return true; 
/* 134 */     if (name.equals("http://xml.org/sax/features/namespace-prefixes"))
/* 135 */       return this._namespacePrefixesFeature; 
/* 136 */     if (name.equals("http://xml.org/sax/features/external-general-entities"))
/* 137 */       return true; 
/* 138 */     if (name.equals("http://xml.org/sax/features/external-parameter-entities"))
/* 139 */       return true; 
/* 140 */     if (name.equals("http://xml.org/sax/features/string-interning")) {
/* 141 */       return this._stringInterningFeature;
/*     */     }
/* 143 */     throw new SAXNotRecognizedException("Feature not supported: " + name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 150 */     if (name.equals("http://xml.org/sax/features/namespaces")) {
/* 151 */       if (!value) {
/* 152 */         throw new SAXNotSupportedException(name + ":" + value);
/*     */       }
/* 154 */     } else if (name.equals("http://xml.org/sax/features/namespace-prefixes")) {
/* 155 */       this._namespacePrefixesFeature = value;
/* 156 */     } else if (!name.equals("http://xml.org/sax/features/external-general-entities")) {
/*     */       
/* 158 */       if (!name.equals("http://xml.org/sax/features/external-parameter-entities"))
/*     */       {
/* 160 */         if (name.equals("http://xml.org/sax/features/string-interning")) {
/* 161 */           if (value != this._stringInterningFeature) {
/* 162 */             throw new SAXNotSupportedException(name + ":" + value);
/*     */           }
/*     */         } else {
/* 165 */           throw new SAXNotRecognizedException("Feature not supported: " + name);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 172 */     if (name.equals("http://xml.org/sax/properties/lexical-handler")) {
/* 173 */       return getLexicalHandler();
/*     */     }
/* 175 */     throw new SAXNotRecognizedException("Property not recognized: " + name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
/* 181 */     if (name.equals("http://xml.org/sax/properties/lexical-handler")) {
/* 182 */       if (value instanceof LexicalHandler) {
/* 183 */         setLexicalHandler((LexicalHandler)value);
/*     */       } else {
/* 185 */         throw new SAXNotSupportedException("http://xml.org/sax/properties/lexical-handler");
/*     */       } 
/*     */     } else {
/* 188 */       throw new SAXNotRecognizedException("Property not recognized: " + name);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setEntityResolver(EntityResolver resolver) {
/* 193 */     this._entityResolver = resolver;
/*     */   }
/*     */   
/*     */   public EntityResolver getEntityResolver() {
/* 197 */     return this._entityResolver;
/*     */   }
/*     */   
/*     */   public void setDTDHandler(DTDHandler handler) {
/* 201 */     this._dtdHandler = handler;
/*     */   }
/*     */   
/*     */   public DTDHandler getDTDHandler() {
/* 205 */     return this._dtdHandler;
/*     */   }
/*     */   
/*     */   public void setContentHandler(ContentHandler handler) {
/* 209 */     this._contentHandler = handler;
/*     */   }
/*     */   
/*     */   public ContentHandler getContentHandler() {
/* 213 */     return this._contentHandler;
/*     */   }
/*     */   
/*     */   public void setErrorHandler(ErrorHandler handler) {
/* 217 */     this._errorHandler = handler;
/*     */   }
/*     */   
/*     */   public ErrorHandler getErrorHandler() {
/* 221 */     return this._errorHandler;
/*     */   }
/*     */   
/*     */   public void setLexicalHandler(LexicalHandler handler) {
/* 225 */     this._lexicalHandler = handler;
/*     */   }
/*     */   
/*     */   public LexicalHandler getLexicalHandler() {
/* 229 */     return this._lexicalHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public void parse(InputSource input) throws IOException, SAXException {
/* 234 */     process();
/*     */   }
/*     */ 
/*     */   
/*     */   public void parse(String systemId) throws IOException, SAXException {
/* 239 */     process();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void process(XMLStreamBuffer buffer) throws SAXException {
/* 249 */     setXMLStreamBuffer(buffer);
/* 250 */     process();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void process(XMLStreamBuffer buffer, boolean produceFragmentEvent) throws SAXException {
/* 261 */     setXMLStreamBuffer(buffer);
/* 262 */     process();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMLStreamBuffer(XMLStreamBuffer buffer) {
/* 272 */     setBuffer(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setXMLStreamBuffer(XMLStreamBuffer buffer, boolean produceFragmentEvent) {
/* 283 */     if (!produceFragmentEvent && this._treeCount > 1)
/* 284 */       throw new IllegalStateException("Can't write a forest to a full XML infoset"); 
/* 285 */     setBuffer(buffer, produceFragmentEvent);
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
/*     */   public final void process() throws SAXException {
/* 304 */     if (!this._fragmentMode) {
/* 305 */       LocatorImpl nullLocator = new LocatorImpl();
/* 306 */       nullLocator.setSystemId(this._buffer.getSystemId());
/* 307 */       nullLocator.setLineNumber(-1);
/* 308 */       nullLocator.setColumnNumber(-1);
/* 309 */       this._contentHandler.setDocumentLocator(nullLocator);
/*     */       
/* 311 */       this._contentHandler.startDocument();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 316 */     while (this._treeCount > 0) {
/* 317 */       String prefix, uri, localName, str2, str1, str3; int item = readEiiState();
/* 318 */       switch (item) {
/*     */         case 1:
/* 320 */           processDocument();
/* 321 */           this._treeCount--;
/*     */           continue;
/*     */         
/*     */         case 17:
/*     */           return;
/*     */         case 3:
/* 327 */           processElement(readStructureString(), readStructureString(), readStructureString(), isInscope());
/* 328 */           this._treeCount--;
/*     */           continue;
/*     */         
/*     */         case 4:
/* 332 */           prefix = readStructureString();
/* 333 */           str2 = readStructureString();
/* 334 */           str3 = readStructureString();
/* 335 */           processElement(str2, str3, getQName(prefix, str3), isInscope());
/* 336 */           this._treeCount--;
/*     */           continue;
/*     */         
/*     */         case 5:
/* 340 */           uri = readStructureString();
/* 341 */           str1 = readStructureString();
/* 342 */           processElement(uri, str1, str1, isInscope());
/* 343 */           this._treeCount--;
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 6:
/* 348 */           localName = readStructureString();
/* 349 */           processElement("", localName, localName, isInscope());
/* 350 */           this._treeCount--;
/*     */           continue;
/*     */         
/*     */         case 12:
/* 354 */           processCommentAsCharArraySmall();
/*     */           continue;
/*     */         case 13:
/* 357 */           processCommentAsCharArrayMedium();
/*     */           continue;
/*     */         case 14:
/* 360 */           processCommentAsCharArrayCopy();
/*     */           continue;
/*     */         case 15:
/* 363 */           processComment(readContentString());
/*     */           continue;
/*     */         case 16:
/* 366 */           processProcessingInstruction(readStructureString(), readStructureString());
/*     */           continue;
/*     */       } 
/* 369 */       throw reportFatalError("Illegal state for DIIs: " + item);
/*     */     } 
/*     */ 
/*     */     
/* 373 */     if (!this._fragmentMode)
/* 374 */       this._contentHandler.endDocument(); 
/*     */   }
/*     */   
/*     */   private void processCommentAsCharArraySmall() throws SAXException {
/* 378 */     int length = readStructure();
/* 379 */     int start = readContentCharactersBuffer(length);
/* 380 */     processComment(this._contentCharactersBuffer, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SAXParseException reportFatalError(String msg) throws SAXException {
/* 389 */     SAXParseException spe = new SAXParseException(msg, null);
/* 390 */     if (this._errorHandler != null)
/* 391 */       this._errorHandler.fatalError(spe); 
/* 392 */     return spe;
/*     */   }
/*     */   
/*     */   private boolean isInscope() {
/* 396 */     return (this._buffer.getInscopeNamespaces().size() > 0);
/*     */   } private void processDocument() throws SAXException {
/*     */     int item;
/*     */     while (true) {
/*     */       String prefix, uri, localName, str2, str1, str3;
/* 401 */       item = readEiiState();
/* 402 */       switch (item) {
/*     */         case 3:
/* 404 */           processElement(readStructureString(), readStructureString(), readStructureString(), isInscope());
/*     */           continue;
/*     */         
/*     */         case 4:
/* 408 */           prefix = readStructureString();
/* 409 */           str2 = readStructureString();
/* 410 */           str3 = readStructureString();
/* 411 */           processElement(str2, str3, getQName(prefix, str3), isInscope());
/*     */           continue;
/*     */         
/*     */         case 5:
/* 415 */           uri = readStructureString();
/* 416 */           str1 = readStructureString();
/* 417 */           processElement(uri, str1, str1, isInscope());
/*     */           continue;
/*     */ 
/*     */         
/*     */         case 6:
/* 422 */           localName = readStructureString();
/* 423 */           processElement("", localName, localName, isInscope());
/*     */           continue;
/*     */         
/*     */         case 12:
/* 427 */           processCommentAsCharArraySmall();
/*     */           continue;
/*     */         case 13:
/* 430 */           processCommentAsCharArrayMedium();
/*     */           continue;
/*     */         case 14:
/* 433 */           processCommentAsCharArrayCopy();
/*     */           continue;
/*     */         case 15:
/* 436 */           processComment(readContentString());
/*     */           continue;
/*     */         case 16:
/* 439 */           processProcessingInstruction(readStructureString(), readStructureString()); continue;
/*     */         case 17:
/*     */           return;
/*     */       }  break;
/*     */     } 
/* 444 */     throw reportFatalError("Illegal state for child of DII: " + item);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void processElement(String uri, String localName, String qName, boolean inscope) throws SAXException {
/* 450 */     boolean hasAttributes = false;
/* 451 */     boolean hasNamespaceAttributes = false;
/* 452 */     int item = peekStructure();
/* 453 */     Set<String> prefixSet = inscope ? new HashSet<String>() : Collections.<String>emptySet();
/* 454 */     if ((item & 0xF0) == 64) {
/* 455 */       cacheNamespacePrefixStartingIndex();
/* 456 */       hasNamespaceAttributes = true;
/* 457 */       item = processNamespaceAttributes(item, inscope, prefixSet);
/*     */     } 
/* 459 */     if (inscope) {
/* 460 */       readInscopeNamespaces(prefixSet);
/*     */     }
/*     */     
/* 463 */     if ((item & 0xF0) == 48) {
/* 464 */       hasAttributes = true;
/* 465 */       processAttributes(item);
/*     */     } 
/*     */     
/* 468 */     this._contentHandler.startElement(uri, localName, qName, (Attributes)this._attributes);
/*     */     
/* 470 */     if (hasAttributes)
/* 471 */       this._attributes.clear();  do {
/*     */       String p, u, ln; int length; char[] ch; String s; CharSequence c; String str3, str2;
/*     */       int start;
/*     */       String str1, str4;
/* 475 */       item = readEiiState();
/* 476 */       switch (item) {
/*     */         case 3:
/* 478 */           processElement(readStructureString(), readStructureString(), readStructureString(), false);
/*     */           break;
/*     */         
/*     */         case 4:
/* 482 */           p = readStructureString();
/* 483 */           str3 = readStructureString();
/* 484 */           str4 = readStructureString();
/* 485 */           processElement(str3, str4, getQName(p, str4), false);
/*     */           break;
/*     */         
/*     */         case 5:
/* 489 */           u = readStructureString();
/* 490 */           str2 = readStructureString();
/* 491 */           processElement(u, str2, str2, false);
/*     */           break;
/*     */         
/*     */         case 6:
/* 495 */           ln = readStructureString();
/* 496 */           processElement("", ln, ln, false);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 7:
/* 501 */           length = readStructure();
/* 502 */           start = readContentCharactersBuffer(length);
/* 503 */           this._contentHandler.characters(this._contentCharactersBuffer, start, length);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 8:
/* 508 */           length = readStructure16();
/* 509 */           start = readContentCharactersBuffer(length);
/* 510 */           this._contentHandler.characters(this._contentCharactersBuffer, start, length);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 9:
/* 515 */           ch = readContentCharactersCopy();
/*     */           
/* 517 */           this._contentHandler.characters(ch, 0, ch.length);
/*     */           break;
/*     */ 
/*     */         
/*     */         case 10:
/* 522 */           s = readContentString();
/* 523 */           this._contentHandler.characters(s.toCharArray(), 0, s.length());
/*     */           break;
/*     */ 
/*     */         
/*     */         case 11:
/* 528 */           c = (CharSequence)readContentObject();
/* 529 */           str1 = c.toString();
/* 530 */           this._contentHandler.characters(str1.toCharArray(), 0, str1.length());
/*     */           break;
/*     */         
/*     */         case 12:
/* 534 */           processCommentAsCharArraySmall();
/*     */           break;
/*     */         case 13:
/* 537 */           processCommentAsCharArrayMedium();
/*     */           break;
/*     */         case 14:
/* 540 */           processCommentAsCharArrayCopy();
/*     */           break;
/*     */         case 104:
/* 543 */           processComment(readContentString());
/*     */           break;
/*     */         case 16:
/* 546 */           processProcessingInstruction(readStructureString(), readStructureString());
/*     */           break;
/*     */         case 17:
/*     */           break;
/*     */         default:
/* 551 */           throw reportFatalError("Illegal state for child of EII: " + item);
/*     */       } 
/* 553 */     } while (item != 17);
/*     */     
/* 555 */     this._contentHandler.endElement(uri, localName, qName);
/*     */     
/* 557 */     if (hasNamespaceAttributes) {
/* 558 */       processEndPrefixMapping();
/*     */     }
/*     */   }
/*     */   
/*     */   private void readInscopeNamespaces(Set<String> prefixSet) throws SAXException {
/* 563 */     for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)this._buffer.getInscopeNamespaces().entrySet()) {
/* 564 */       String key = fixNull(e.getKey());
/*     */       
/* 566 */       if (!prefixSet.contains(key)) {
/* 567 */         processNamespaceAttribute(key, e.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String fixNull(String s) {
/* 574 */     if (s == null) return ""; 
/* 575 */     return s;
/*     */   }
/*     */   private void processCommentAsCharArrayCopy() throws SAXException {
/* 578 */     char[] ch = readContentCharactersCopy();
/* 579 */     processComment(ch, 0, ch.length);
/*     */   }
/*     */   
/*     */   private void processCommentAsCharArrayMedium() throws SAXException {
/* 583 */     int length = readStructure16();
/* 584 */     int start = readContentCharactersBuffer(length);
/* 585 */     processComment(this._contentCharactersBuffer, start, length);
/*     */   }
/*     */   
/*     */   private void processEndPrefixMapping() throws SAXException {
/* 589 */     int end = this._namespaceAttributesStack[--this._namespaceAttributesStackIndex];
/*     */     
/* 591 */     int start = (this._namespaceAttributesStackIndex >= 0) ? this._namespaceAttributesStartingStack[this._namespaceAttributesStackIndex] : 0;
/*     */     
/* 593 */     for (int i = end - 1; i >= start; i--) {
/* 594 */       this._contentHandler.endPrefixMapping(this._namespacePrefixes[i]);
/*     */     }
/* 596 */     this._namespacePrefixesIndex = start;
/*     */   }
/*     */   
/*     */   private int processNamespaceAttributes(int item, boolean collectPrefixes, Set<String> prefixSet) throws SAXException {
/*     */     do {
/*     */       String prefix;
/* 602 */       switch (getNIIState(item)) {
/*     */         
/*     */         case 1:
/* 605 */           processNamespaceAttribute("", "");
/* 606 */           if (collectPrefixes) {
/* 607 */             prefixSet.add("");
/*     */           }
/*     */           break;
/*     */         
/*     */         case 2:
/* 612 */           prefix = readStructureString();
/* 613 */           processNamespaceAttribute(prefix, "");
/* 614 */           if (collectPrefixes) {
/* 615 */             prefixSet.add(prefix);
/*     */           }
/*     */           break;
/*     */         
/*     */         case 3:
/* 620 */           prefix = readStructureString();
/* 621 */           processNamespaceAttribute(prefix, readStructureString());
/* 622 */           if (collectPrefixes) {
/* 623 */             prefixSet.add(prefix);
/*     */           }
/*     */           break;
/*     */         
/*     */         case 4:
/* 628 */           processNamespaceAttribute("", readStructureString());
/* 629 */           if (collectPrefixes) {
/* 630 */             prefixSet.add("");
/*     */           }
/*     */           break;
/*     */         default:
/* 634 */           throw reportFatalError("Illegal state: " + item);
/*     */       } 
/* 636 */       readStructure();
/*     */       
/* 638 */       item = peekStructure();
/* 639 */     } while ((item & 0xF0) == 64);
/*     */ 
/*     */     
/* 642 */     cacheNamespacePrefixIndex();
/*     */     
/* 644 */     return item;
/*     */   }
/*     */   private void processAttributes(int item) throws SAXException {
/*     */     do {
/*     */       String p, u, ln, str2, str1, str3;
/* 649 */       switch (getAIIState(item)) {
/*     */         case 1:
/* 651 */           this._attributes.addAttributeWithQName(readStructureString(), readStructureString(), readStructureString(), readStructureString(), readContentString());
/*     */           break;
/*     */         
/*     */         case 2:
/* 655 */           p = readStructureString();
/* 656 */           str2 = readStructureString();
/* 657 */           str3 = readStructureString();
/* 658 */           this._attributes.addAttributeWithQName(str2, str3, getQName(p, str3), readStructureString(), readContentString());
/*     */           break;
/*     */         
/*     */         case 3:
/* 662 */           u = readStructureString();
/* 663 */           str1 = readStructureString();
/* 664 */           this._attributes.addAttributeWithQName(u, str1, str1, readStructureString(), readContentString());
/*     */           break;
/*     */         
/*     */         case 4:
/* 668 */           ln = readStructureString();
/* 669 */           this._attributes.addAttributeWithQName("", ln, ln, readStructureString(), readContentString());
/*     */           break;
/*     */         
/*     */         default:
/* 673 */           throw reportFatalError("Illegal state: " + item);
/*     */       } 
/* 675 */       readStructure();
/*     */       
/* 677 */       item = peekStructure();
/* 678 */     } while ((item & 0xF0) == 48);
/*     */   }
/*     */   
/*     */   private void processNamespaceAttribute(String prefix, String uri) throws SAXException {
/* 682 */     this._contentHandler.startPrefixMapping(prefix, uri);
/*     */     
/* 684 */     if (this._namespacePrefixesFeature)
/*     */     {
/* 686 */       if (prefix != "") {
/* 687 */         this._attributes.addAttributeWithQName("http://www.w3.org/2000/xmlns/", prefix, getQName("xmlns", prefix), "CDATA", uri);
/*     */       }
/*     */       else {
/*     */         
/* 691 */         this._attributes.addAttributeWithQName("http://www.w3.org/2000/xmlns/", "xmlns", "xmlns", "CDATA", uri);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 697 */     cacheNamespacePrefix(prefix);
/*     */   }
/*     */   
/*     */   private void cacheNamespacePrefix(String prefix) {
/* 701 */     if (this._namespacePrefixesIndex == this._namespacePrefixes.length) {
/* 702 */       String[] namespaceAttributes = new String[this._namespacePrefixesIndex * 3 / 2 + 1];
/* 703 */       System.arraycopy(this._namespacePrefixes, 0, namespaceAttributes, 0, this._namespacePrefixesIndex);
/* 704 */       this._namespacePrefixes = namespaceAttributes;
/*     */     } 
/*     */     
/* 707 */     this._namespacePrefixes[this._namespacePrefixesIndex++] = prefix;
/*     */   }
/*     */   
/*     */   private void cacheNamespacePrefixIndex() {
/* 711 */     if (this._namespaceAttributesStackIndex == this._namespaceAttributesStack.length) {
/* 712 */       int[] namespaceAttributesStack = new int[this._namespaceAttributesStackIndex * 3 / 2 + 1];
/* 713 */       System.arraycopy(this._namespaceAttributesStack, 0, namespaceAttributesStack, 0, this._namespaceAttributesStackIndex);
/* 714 */       this._namespaceAttributesStack = namespaceAttributesStack;
/*     */     } 
/*     */     
/* 717 */     this._namespaceAttributesStack[this._namespaceAttributesStackIndex++] = this._namespacePrefixesIndex;
/*     */   }
/*     */   
/*     */   private void cacheNamespacePrefixStartingIndex() {
/* 721 */     if (this._namespaceAttributesStackIndex == this._namespaceAttributesStartingStack.length) {
/* 722 */       int[] namespaceAttributesStart = new int[this._namespaceAttributesStackIndex * 3 / 2 + 1];
/* 723 */       System.arraycopy(this._namespaceAttributesStartingStack, 0, namespaceAttributesStart, 0, this._namespaceAttributesStackIndex);
/* 724 */       this._namespaceAttributesStartingStack = namespaceAttributesStart;
/*     */     } 
/* 726 */     this._namespaceAttributesStartingStack[this._namespaceAttributesStackIndex] = this._namespacePrefixesIndex;
/*     */   }
/*     */   
/*     */   private void processComment(String s) throws SAXException {
/* 730 */     processComment(s.toCharArray(), 0, s.length());
/*     */   }
/*     */   
/*     */   private void processComment(char[] ch, int start, int length) throws SAXException {
/* 734 */     this._lexicalHandler.comment(ch, start, length);
/*     */   }
/*     */   
/*     */   private void processProcessingInstruction(String target, String data) throws SAXException {
/* 738 */     this._contentHandler.processingInstruction(target, data);
/*     */   }
/*     */   
/* 741 */   private static final DefaultWithLexicalHandler DEFAULT_LEXICAL_HANDLER = new DefaultWithLexicalHandler();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\sax\SAXBufferProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */