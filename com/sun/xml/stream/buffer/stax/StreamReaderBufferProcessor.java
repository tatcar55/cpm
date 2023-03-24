/*      */ package com.sun.xml.stream.buffer.stax;
/*      */ 
/*      */ import com.sun.xml.stream.buffer.AbstractCreatorProcessor;
/*      */ import com.sun.xml.stream.buffer.AbstractProcessor;
/*      */ import com.sun.xml.stream.buffer.AttributesHolder;
/*      */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*      */ import com.sun.xml.stream.buffer.XMLStreamBufferMark;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.Location;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import org.jvnet.staxex.NamespaceContextEx;
/*      */ import org.jvnet.staxex.XMLStreamReaderEx;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StreamReaderBufferProcessor
/*      */   extends AbstractProcessor
/*      */   implements XMLStreamReaderEx
/*      */ {
/*      */   private static final int CACHE_SIZE = 16;
/*   78 */   protected ElementStackEntry[] _stack = new ElementStackEntry[16];
/*      */ 
/*      */ 
/*      */   
/*      */   protected ElementStackEntry _stackTop;
/*      */ 
/*      */   
/*      */   protected int _depth;
/*      */ 
/*      */   
/*   88 */   protected String[] _namespaceAIIsPrefix = new String[16];
/*   89 */   protected String[] _namespaceAIIsNamespaceName = new String[16];
/*      */   
/*      */   protected int _namespaceAIIsEnd;
/*      */   
/*   93 */   protected InternalNamespaceContext _nsCtx = new InternalNamespaceContext();
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _eventType;
/*      */ 
/*      */ 
/*      */   
/*      */   protected AttributesHolder _attributeCache;
/*      */ 
/*      */ 
/*      */   
/*      */   protected CharSequence _charSequence;
/*      */ 
/*      */ 
/*      */   
/*      */   protected char[] _characters;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _textOffset;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _textLen;
/*      */ 
/*      */ 
/*      */   
/*      */   protected String _piTarget;
/*      */ 
/*      */ 
/*      */   
/*      */   protected String _piData;
/*      */ 
/*      */   
/*      */   private static final int PARSING = 1;
/*      */ 
/*      */   
/*      */   private static final int PENDING_END_DOCUMENT = 2;
/*      */ 
/*      */   
/*      */   private static final int COMPLETED = 3;
/*      */ 
/*      */   
/*      */   private int _completionState;
/*      */ 
/*      */ 
/*      */   
/*      */   public StreamReaderBufferProcessor() {
/*  142 */     for (int i = 0; i < this._stack.length; i++) {
/*  143 */       this._stack[i] = new ElementStackEntry();
/*      */     }
/*      */     
/*  146 */     this._attributeCache = new AttributesHolder();
/*      */   }
/*      */   
/*      */   public StreamReaderBufferProcessor(XMLStreamBuffer buffer) throws XMLStreamException {
/*  150 */     this();
/*  151 */     setXMLStreamBuffer(buffer);
/*      */   }
/*      */   
/*      */   public void setXMLStreamBuffer(XMLStreamBuffer buffer) throws XMLStreamException {
/*  155 */     setBuffer(buffer, buffer.isFragment());
/*      */     
/*  157 */     this._completionState = 1;
/*  158 */     this._namespaceAIIsEnd = 0;
/*  159 */     this._characters = null;
/*  160 */     this._charSequence = null;
/*  161 */     this._eventType = 7;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public XMLStreamBuffer nextTagAndMark() throws XMLStreamException {
/*      */     while (true) {
/*  178 */       int s = peekStructure();
/*  179 */       if ((s & 0xF0) == 32) {
/*      */         
/*  181 */         Map<String, String> inscope = new HashMap<String, String>(this._namespaceAIIsEnd);
/*      */         
/*  183 */         for (int i = 0; i < this._namespaceAIIsEnd; i++) {
/*  184 */           inscope.put(this._namespaceAIIsPrefix[i], this._namespaceAIIsNamespaceName[i]);
/*      */         }
/*  186 */         XMLStreamBufferMark mark = new XMLStreamBufferMark(inscope, (AbstractCreatorProcessor)this);
/*  187 */         next();
/*  188 */         return (XMLStreamBuffer)mark;
/*  189 */       }  if ((s & 0xF0) == 16) {
/*      */         
/*  191 */         readStructure();
/*      */         
/*  193 */         XMLStreamBufferMark mark = new XMLStreamBufferMark(new HashMap<Object, Object>(this._namespaceAIIsEnd), (AbstractCreatorProcessor)this);
/*  194 */         next();
/*  195 */         return (XMLStreamBuffer)mark;
/*      */       } 
/*      */       
/*  198 */       if (next() == 2)
/*  199 */         return null; 
/*      */     } 
/*      */   }
/*      */   
/*      */   public Object getProperty(String name) {
/*  204 */     return null;
/*      */   }
/*      */   public int next() throws XMLStreamException {
/*      */     int eiiState;
/*  208 */     switch (this._completionState) {
/*      */       case 3:
/*  210 */         throw new XMLStreamException("Invalid State");
/*      */       case 2:
/*  212 */         this._namespaceAIIsEnd = 0;
/*  213 */         this._completionState = 3;
/*  214 */         return this._eventType = 8;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  222 */     switch (this._eventType) {
/*      */       case 2:
/*  224 */         if (this._depth > 1) {
/*  225 */           this._depth--;
/*      */ 
/*      */           
/*  228 */           popElementStack(this._depth); break;
/*  229 */         }  if (this._depth == 1) {
/*  230 */           this._depth--;
/*      */         }
/*      */         break;
/*      */     } 
/*  234 */     this._characters = null;
/*  235 */     this._charSequence = null; while (true) {
/*      */       String uri, localName, prefix;
/*  237 */       eiiState = readEiiState();
/*  238 */       switch (eiiState) {
/*      */         case 1:
/*      */           continue;
/*      */ 
/*      */         
/*      */         case 3:
/*  244 */           uri = readStructureString();
/*  245 */           localName = readStructureString();
/*  246 */           prefix = getPrefixFromQName(readStructureString());
/*      */           
/*  248 */           processElement(prefix, uri, localName, isInscope(this._depth));
/*  249 */           return this._eventType = 1;
/*      */         
/*      */         case 4:
/*  252 */           processElement(readStructureString(), readStructureString(), readStructureString(), isInscope(this._depth));
/*  253 */           return this._eventType = 1;
/*      */         case 5:
/*  255 */           processElement((String)null, readStructureString(), readStructureString(), isInscope(this._depth));
/*  256 */           return this._eventType = 1;
/*      */         case 6:
/*  258 */           processElement((String)null, (String)null, readStructureString(), isInscope(this._depth));
/*  259 */           return this._eventType = 1;
/*      */         case 7:
/*  261 */           this._textLen = readStructure();
/*  262 */           this._textOffset = readContentCharactersBuffer(this._textLen);
/*  263 */           this._characters = this._contentCharactersBuffer;
/*      */           
/*  265 */           return this._eventType = 4;
/*      */         case 8:
/*  267 */           this._textLen = readStructure16();
/*  268 */           this._textOffset = readContentCharactersBuffer(this._textLen);
/*  269 */           this._characters = this._contentCharactersBuffer;
/*      */           
/*  271 */           return this._eventType = 4;
/*      */         case 9:
/*  273 */           this._characters = readContentCharactersCopy();
/*  274 */           this._textLen = this._characters.length;
/*  275 */           this._textOffset = 0;
/*      */           
/*  277 */           return this._eventType = 4;
/*      */         case 10:
/*  279 */           this._eventType = 4;
/*  280 */           this._charSequence = readContentString();
/*      */           
/*  282 */           return this._eventType = 4;
/*      */         case 11:
/*  284 */           this._eventType = 4;
/*  285 */           this._charSequence = (CharSequence)readContentObject();
/*      */           
/*  287 */           return this._eventType = 4;
/*      */         case 12:
/*  289 */           this._textLen = readStructure();
/*  290 */           this._textOffset = readContentCharactersBuffer(this._textLen);
/*  291 */           this._characters = this._contentCharactersBuffer;
/*      */           
/*  293 */           return this._eventType = 5;
/*      */         case 13:
/*  295 */           this._textLen = readStructure16();
/*  296 */           this._textOffset = readContentCharactersBuffer(this._textLen);
/*  297 */           this._characters = this._contentCharactersBuffer;
/*      */           
/*  299 */           return this._eventType = 5;
/*      */         case 14:
/*  301 */           this._characters = readContentCharactersCopy();
/*  302 */           this._textLen = this._characters.length;
/*  303 */           this._textOffset = 0;
/*      */           
/*  305 */           return this._eventType = 5;
/*      */         case 15:
/*  307 */           this._charSequence = readContentString();
/*      */           
/*  309 */           return this._eventType = 5;
/*      */         case 16:
/*  311 */           this._piTarget = readStructureString();
/*  312 */           this._piData = readStructureString();
/*      */           
/*  314 */           return this._eventType = 3;
/*      */         case 17:
/*  316 */           if (this._depth > 1)
/*      */           {
/*  318 */             return this._eventType = 2; } 
/*  319 */           if (this._depth == 1) {
/*      */             
/*  321 */             if (this._fragmentMode && 
/*  322 */               --this._treeCount == 0) {
/*  323 */               this._completionState = 2;
/*      */             }
/*  325 */             return this._eventType = 2;
/*      */           } 
/*      */ 
/*      */           
/*  329 */           this._namespaceAIIsEnd = 0;
/*  330 */           this._completionState = 3;
/*  331 */           return this._eventType = 8;
/*      */       }  break;
/*      */     } 
/*  334 */     throw new XMLStreamException("Internal XSB error: Invalid State=" + eiiState);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void require(int type, String namespaceURI, String localName) throws XMLStreamException {
/*  341 */     if (type != this._eventType) {
/*  342 */       throw new XMLStreamException("");
/*      */     }
/*  344 */     if (namespaceURI != null && !namespaceURI.equals(getNamespaceURI())) {
/*  345 */       throw new XMLStreamException("");
/*      */     }
/*  347 */     if (localName != null && !localName.equals(getLocalName())) {
/*  348 */       throw new XMLStreamException("");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getElementTextTrim() throws XMLStreamException {
/*  354 */     return getElementText().trim();
/*      */   }
/*      */   
/*      */   public final String getElementText() throws XMLStreamException {
/*  358 */     if (this._eventType != 1) {
/*  359 */       throw new XMLStreamException("");
/*      */     }
/*      */     
/*  362 */     next();
/*  363 */     return getElementText(true);
/*      */   }
/*      */   
/*      */   public final String getElementText(boolean startElementRead) throws XMLStreamException {
/*  367 */     if (!startElementRead) {
/*  368 */       throw new XMLStreamException("");
/*      */     }
/*      */     
/*  371 */     int eventType = getEventType();
/*  372 */     StringBuilder content = new StringBuilder();
/*  373 */     while (eventType != 2) {
/*  374 */       if (eventType == 4 || eventType == 12 || eventType == 6 || eventType == 9) {
/*      */ 
/*      */ 
/*      */         
/*  378 */         content.append(getText());
/*  379 */       } else if (eventType != 3 && eventType != 5) {
/*      */ 
/*      */         
/*  382 */         if (eventType == 8)
/*  383 */           throw new XMLStreamException(""); 
/*  384 */         if (eventType == 1) {
/*  385 */           throw new XMLStreamException("");
/*      */         }
/*  387 */         throw new XMLStreamException("");
/*      */       } 
/*  389 */       eventType = next();
/*      */     } 
/*  391 */     return content.toString();
/*      */   }
/*      */   
/*      */   public final int nextTag() throws XMLStreamException {
/*  395 */     next();
/*  396 */     return nextTag(true);
/*      */   }
/*      */   
/*      */   public final int nextTag(boolean currentTagRead) throws XMLStreamException {
/*  400 */     int eventType = getEventType();
/*  401 */     if (!currentTagRead) {
/*  402 */       eventType = next();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  408 */     while ((eventType == 4 && isWhiteSpace()) || (eventType == 12 && isWhiteSpace()) || eventType == 6 || eventType == 3 || eventType == 5) {
/*  409 */       eventType = next();
/*      */     }
/*  411 */     if (eventType != 1 && eventType != 2) {
/*  412 */       throw new XMLStreamException("");
/*      */     }
/*  414 */     return eventType;
/*      */   }
/*      */   
/*      */   public final boolean hasNext() {
/*  418 */     return (this._eventType != 8);
/*      */   }
/*      */ 
/*      */   
/*      */   public void close() throws XMLStreamException {}
/*      */   
/*      */   public final boolean isStartElement() {
/*  425 */     return (this._eventType == 1);
/*      */   }
/*      */   
/*      */   public final boolean isEndElement() {
/*  429 */     return (this._eventType == 2);
/*      */   }
/*      */   
/*      */   public final boolean isCharacters() {
/*  433 */     return (this._eventType == 4);
/*      */   }
/*      */   
/*      */   public final boolean isWhiteSpace() {
/*  437 */     if (isCharacters() || this._eventType == 12) {
/*  438 */       char[] ch = getTextCharacters();
/*  439 */       int start = getTextStart();
/*  440 */       int length = getTextLength();
/*  441 */       for (int i = start; i < length; i++) {
/*  442 */         char c = ch[i];
/*  443 */         if (c != ' ' && c != '\t' && c != '\r' && c != '\n')
/*  444 */           return false; 
/*      */       } 
/*  446 */       return true;
/*      */     } 
/*  448 */     return false;
/*      */   }
/*      */   
/*      */   public final String getAttributeValue(String namespaceURI, String localName) {
/*  452 */     if (this._eventType != 1) {
/*  453 */       throw new IllegalStateException("");
/*      */     }
/*      */     
/*  456 */     if (namespaceURI == null)
/*      */     {
/*      */       
/*  459 */       namespaceURI = "";
/*      */     }
/*      */     
/*  462 */     return this._attributeCache.getValue(namespaceURI, localName);
/*      */   }
/*      */   
/*      */   public final int getAttributeCount() {
/*  466 */     if (this._eventType != 1) {
/*  467 */       throw new IllegalStateException("");
/*      */     }
/*      */     
/*  470 */     return this._attributeCache.getLength();
/*      */   }
/*      */   
/*      */   public final QName getAttributeName(int index) {
/*  474 */     if (this._eventType != 1) {
/*  475 */       throw new IllegalStateException("");
/*      */     }
/*      */     
/*  478 */     String prefix = this._attributeCache.getPrefix(index);
/*  479 */     String localName = this._attributeCache.getLocalName(index);
/*  480 */     String uri = this._attributeCache.getURI(index);
/*  481 */     return new QName(uri, localName, prefix);
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getAttributeNamespace(int index) {
/*  486 */     if (this._eventType != 1) {
/*  487 */       throw new IllegalStateException("");
/*      */     }
/*  489 */     return fixEmptyString(this._attributeCache.getURI(index));
/*      */   }
/*      */   
/*      */   public final String getAttributeLocalName(int index) {
/*  493 */     if (this._eventType != 1) {
/*  494 */       throw new IllegalStateException("");
/*      */     }
/*  496 */     return this._attributeCache.getLocalName(index);
/*      */   }
/*      */   
/*      */   public final String getAttributePrefix(int index) {
/*  500 */     if (this._eventType != 1) {
/*  501 */       throw new IllegalStateException("");
/*      */     }
/*  503 */     return fixEmptyString(this._attributeCache.getPrefix(index));
/*      */   }
/*      */   
/*      */   public final String getAttributeType(int index) {
/*  507 */     if (this._eventType != 1) {
/*  508 */       throw new IllegalStateException("");
/*      */     }
/*  510 */     return this._attributeCache.getType(index);
/*      */   }
/*      */   
/*      */   public final String getAttributeValue(int index) {
/*  514 */     if (this._eventType != 1) {
/*  515 */       throw new IllegalStateException("");
/*      */     }
/*      */     
/*  518 */     return this._attributeCache.getValue(index);
/*      */   }
/*      */   
/*      */   public final boolean isAttributeSpecified(int index) {
/*  522 */     return false;
/*      */   }
/*      */   
/*      */   public final int getNamespaceCount() {
/*  526 */     if (this._eventType == 1 || this._eventType == 2) {
/*  527 */       return this._stackTop.namespaceAIIsEnd - this._stackTop.namespaceAIIsStart;
/*      */     }
/*      */     
/*  530 */     throw new IllegalStateException("");
/*      */   }
/*      */   
/*      */   public final String getNamespacePrefix(int index) {
/*  534 */     if (this._eventType == 1 || this._eventType == 2) {
/*  535 */       return this._namespaceAIIsPrefix[this._stackTop.namespaceAIIsStart + index];
/*      */     }
/*      */     
/*  538 */     throw new IllegalStateException("");
/*      */   }
/*      */   
/*      */   public final String getNamespaceURI(int index) {
/*  542 */     if (this._eventType == 1 || this._eventType == 2) {
/*  543 */       return this._namespaceAIIsNamespaceName[this._stackTop.namespaceAIIsStart + index];
/*      */     }
/*      */     
/*  546 */     throw new IllegalStateException("");
/*      */   }
/*      */   
/*      */   public final String getNamespaceURI(String prefix) {
/*  550 */     return this._nsCtx.getNamespaceURI(prefix);
/*      */   }
/*      */   
/*      */   public final NamespaceContextEx getNamespaceContext() {
/*  554 */     return this._nsCtx;
/*      */   }
/*      */   
/*      */   public final int getEventType() {
/*  558 */     return this._eventType;
/*      */   }
/*      */   
/*      */   public final String getText() {
/*  562 */     if (this._characters != null) {
/*  563 */       String s = new String(this._characters, this._textOffset, this._textLen);
/*  564 */       this._charSequence = s;
/*  565 */       return s;
/*  566 */     }  if (this._charSequence != null) {
/*  567 */       return this._charSequence.toString();
/*      */     }
/*  569 */     throw new IllegalStateException();
/*      */   }
/*      */ 
/*      */   
/*      */   public final char[] getTextCharacters() {
/*  574 */     if (this._characters != null)
/*  575 */       return this._characters; 
/*  576 */     if (this._charSequence != null) {
/*      */ 
/*      */       
/*  579 */       this._characters = this._charSequence.toString().toCharArray();
/*  580 */       this._textLen = this._characters.length;
/*  581 */       this._textOffset = 0;
/*  582 */       return this._characters;
/*      */     } 
/*  584 */     throw new IllegalStateException();
/*      */   }
/*      */ 
/*      */   
/*      */   public final int getTextStart() {
/*  589 */     if (this._characters != null)
/*  590 */       return this._textOffset; 
/*  591 */     if (this._charSequence != null) {
/*  592 */       return 0;
/*      */     }
/*  594 */     throw new IllegalStateException();
/*      */   }
/*      */ 
/*      */   
/*      */   public final int getTextLength() {
/*  599 */     if (this._characters != null)
/*  600 */       return this._textLen; 
/*  601 */     if (this._charSequence != null) {
/*  602 */       return this._charSequence.length();
/*      */     }
/*  604 */     throw new IllegalStateException();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
/*  610 */     if (this._characters == null) {
/*  611 */       if (this._charSequence != null) {
/*  612 */         this._characters = this._charSequence.toString().toCharArray();
/*  613 */         this._textLen = this._characters.length;
/*  614 */         this._textOffset = 0;
/*      */       } else {
/*  616 */         throw new IllegalStateException("");
/*      */       } 
/*      */     }
/*      */     try {
/*  620 */       int remaining = this._textLen - sourceStart;
/*  621 */       int len = (remaining > length) ? length : remaining;
/*  622 */       sourceStart += this._textOffset;
/*  623 */       System.arraycopy(this._characters, sourceStart, target, targetStart, len);
/*  624 */       return len;
/*  625 */     } catch (IndexOutOfBoundsException e) {
/*  626 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   private class CharSequenceImpl implements CharSequence {
/*      */     private final int _offset;
/*      */     private final int _length;
/*      */     
/*      */     CharSequenceImpl(int offset, int length) {
/*  635 */       this._offset = offset;
/*  636 */       this._length = length;
/*      */     }
/*      */     
/*      */     public int length() {
/*  640 */       return this._length;
/*      */     }
/*      */     
/*      */     public char charAt(int index) {
/*  644 */       if (index >= 0 && index < StreamReaderBufferProcessor.this._textLen) {
/*  645 */         return StreamReaderBufferProcessor.this._characters[StreamReaderBufferProcessor.this._textOffset + index];
/*      */       }
/*  647 */       throw new IndexOutOfBoundsException();
/*      */     }
/*      */ 
/*      */     
/*      */     public CharSequence subSequence(int start, int end) {
/*  652 */       int length = end - start;
/*  653 */       if (end < 0 || start < 0 || end > length || start > end) {
/*  654 */         throw new IndexOutOfBoundsException();
/*      */       }
/*      */       
/*  657 */       return new CharSequenceImpl(this._offset + start, length);
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*  662 */       return new String(StreamReaderBufferProcessor.this._characters, this._offset, this._length);
/*      */     }
/*      */   }
/*      */   
/*      */   public final CharSequence getPCDATA() {
/*  667 */     if (this._characters != null)
/*  668 */       return new CharSequenceImpl(this._textOffset, this._textLen); 
/*  669 */     if (this._charSequence != null) {
/*  670 */       return this._charSequence;
/*      */     }
/*  672 */     throw new IllegalStateException();
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getEncoding() {
/*  677 */     return "UTF-8";
/*      */   }
/*      */   
/*      */   public final boolean hasText() {
/*  681 */     return (this._characters != null || this._charSequence != null);
/*      */   }
/*      */   
/*      */   public final Location getLocation() {
/*  685 */     return new DummyLocation();
/*      */   }
/*      */   
/*      */   public final boolean hasName() {
/*  689 */     return (this._eventType == 1 || this._eventType == 2);
/*      */   }
/*      */   
/*      */   public final QName getName() {
/*  693 */     return this._stackTop.getQName();
/*      */   }
/*      */   
/*      */   public final String getLocalName() {
/*  697 */     return this._stackTop.localName;
/*      */   }
/*      */   
/*      */   public final String getNamespaceURI() {
/*  701 */     return this._stackTop.uri;
/*      */   }
/*      */   
/*      */   public final String getPrefix() {
/*  705 */     return this._stackTop.prefix;
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getVersion() {
/*  710 */     return "1.0";
/*      */   }
/*      */   
/*      */   public final boolean isStandalone() {
/*  714 */     return false;
/*      */   }
/*      */   
/*      */   public final boolean standaloneSet() {
/*  718 */     return false;
/*      */   }
/*      */   
/*      */   public final String getCharacterEncodingScheme() {
/*  722 */     return "UTF-8";
/*      */   }
/*      */   
/*      */   public final String getPITarget() {
/*  726 */     if (this._eventType == 3) {
/*  727 */       return this._piTarget;
/*      */     }
/*  729 */     throw new IllegalStateException("");
/*      */   }
/*      */   
/*      */   public final String getPIData() {
/*  733 */     if (this._eventType == 3) {
/*  734 */       return this._piData;
/*      */     }
/*  736 */     throw new IllegalStateException("");
/*      */   }
/*      */   
/*      */   protected void processElement(String prefix, String uri, String localName, boolean inscope) {
/*  740 */     pushElementStack();
/*  741 */     this._stackTop.set(prefix, uri, localName);
/*      */     
/*  743 */     this._attributeCache.clear();
/*      */     
/*  745 */     int item = peekStructure();
/*  746 */     if ((item & 0xF0) == 64 || inscope)
/*      */     {
/*      */       
/*  749 */       item = processNamespaceAttributes(item, inscope);
/*      */     }
/*  751 */     if ((item & 0xF0) == 48) {
/*  752 */       processAttributes(item);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean isInscope(int depth) {
/*  757 */     return (this._buffer.getInscopeNamespaces().size() > 0 && depth == 0);
/*      */   }
/*      */   
/*      */   private void resizeNamespaceAttributes() {
/*  761 */     String[] namespaceAIIsPrefix = new String[this._namespaceAIIsEnd * 2];
/*  762 */     System.arraycopy(this._namespaceAIIsPrefix, 0, namespaceAIIsPrefix, 0, this._namespaceAIIsEnd);
/*  763 */     this._namespaceAIIsPrefix = namespaceAIIsPrefix;
/*      */     
/*  765 */     String[] namespaceAIIsNamespaceName = new String[this._namespaceAIIsEnd * 2];
/*  766 */     System.arraycopy(this._namespaceAIIsNamespaceName, 0, namespaceAIIsNamespaceName, 0, this._namespaceAIIsEnd);
/*  767 */     this._namespaceAIIsNamespaceName = namespaceAIIsNamespaceName;
/*      */   }
/*      */   
/*      */   private int processNamespaceAttributes(int item, boolean inscope) {
/*  771 */     this._stackTop.namespaceAIIsStart = this._namespaceAIIsEnd;
/*  772 */     Set<String> prefixSet = inscope ? new HashSet<String>() : Collections.<String>emptySet();
/*      */     
/*  774 */     while ((item & 0xF0) == 64) {
/*  775 */       if (this._namespaceAIIsEnd == this._namespaceAIIsPrefix.length) {
/*  776 */         resizeNamespaceAttributes();
/*      */       }
/*      */       
/*  779 */       switch (getNIIState(item)) {
/*      */         
/*      */         case 1:
/*  782 */           this._namespaceAIIsNamespaceName[this._namespaceAIIsEnd++] = ""; this._namespaceAIIsPrefix[this._namespaceAIIsEnd] = "";
/*      */           
/*  784 */           if (inscope) {
/*  785 */             prefixSet.add("");
/*      */           }
/*      */           break;
/*      */         
/*      */         case 2:
/*  790 */           this._namespaceAIIsPrefix[this._namespaceAIIsEnd] = readStructureString();
/*  791 */           if (inscope) {
/*  792 */             prefixSet.add(this._namespaceAIIsPrefix[this._namespaceAIIsEnd]);
/*      */           }
/*  794 */           this._namespaceAIIsNamespaceName[this._namespaceAIIsEnd++] = "";
/*      */           break;
/*      */         
/*      */         case 3:
/*  798 */           this._namespaceAIIsPrefix[this._namespaceAIIsEnd] = readStructureString();
/*  799 */           if (inscope) {
/*  800 */             prefixSet.add(this._namespaceAIIsPrefix[this._namespaceAIIsEnd]);
/*      */           }
/*  802 */           this._namespaceAIIsNamespaceName[this._namespaceAIIsEnd++] = readStructureString();
/*      */           break;
/*      */         
/*      */         case 4:
/*  806 */           this._namespaceAIIsPrefix[this._namespaceAIIsEnd] = "";
/*  807 */           if (inscope) {
/*  808 */             prefixSet.add("");
/*      */           }
/*  810 */           this._namespaceAIIsNamespaceName[this._namespaceAIIsEnd++] = readStructureString();
/*      */           break;
/*      */       } 
/*  813 */       readStructure();
/*      */       
/*  815 */       item = peekStructure();
/*      */     } 
/*      */     
/*  818 */     if (inscope) {
/*  819 */       for (Map.Entry<String, String> e : (Iterable<Map.Entry<String, String>>)this._buffer.getInscopeNamespaces().entrySet()) {
/*  820 */         String key = fixNull(e.getKey());
/*      */         
/*  822 */         if (!prefixSet.contains(key)) {
/*  823 */           if (this._namespaceAIIsEnd == this._namespaceAIIsPrefix.length) {
/*  824 */             resizeNamespaceAttributes();
/*      */           }
/*  826 */           this._namespaceAIIsPrefix[this._namespaceAIIsEnd] = key;
/*  827 */           this._namespaceAIIsNamespaceName[this._namespaceAIIsEnd++] = e.getValue();
/*      */         } 
/*      */       } 
/*      */     }
/*  831 */     this._stackTop.namespaceAIIsEnd = this._namespaceAIIsEnd;
/*      */     
/*  833 */     return item;
/*      */   }
/*      */   
/*      */   private static String fixNull(String s) {
/*  837 */     if (s == null) return ""; 
/*  838 */     return s;
/*      */   }
/*      */   private void processAttributes(int item) {
/*      */     do {
/*      */       String uri, localName, prefix;
/*  843 */       switch (getAIIState(item)) {
/*      */         case 1:
/*  845 */           uri = readStructureString();
/*  846 */           localName = readStructureString();
/*  847 */           prefix = getPrefixFromQName(readStructureString());
/*  848 */           this._attributeCache.addAttributeWithPrefix(prefix, uri, localName, readStructureString(), readContentString());
/*      */           break;
/*      */         
/*      */         case 2:
/*  852 */           this._attributeCache.addAttributeWithPrefix(readStructureString(), readStructureString(), readStructureString(), readStructureString(), readContentString());
/*      */           break;
/*      */         
/*      */         case 3:
/*  856 */           this._attributeCache.addAttributeWithPrefix("", readStructureString(), readStructureString(), readStructureString(), readContentString());
/*      */           break;
/*      */         case 4:
/*  859 */           this._attributeCache.addAttributeWithPrefix("", "", readStructureString(), readStructureString(), readContentString());
/*      */           break;
/*      */         
/*      */         default:
/*  863 */           assert false : "Internal XSB Error: wrong attribute state, Item=" + item; break;
/*      */       } 
/*  865 */       readStructure();
/*      */       
/*  867 */       item = peekStructure();
/*  868 */     } while ((item & 0xF0) == 48);
/*      */   }
/*      */   
/*      */   private void pushElementStack() {
/*  872 */     if (this._depth == this._stack.length) {
/*      */       
/*  874 */       ElementStackEntry[] tmp = this._stack;
/*  875 */       this._stack = new ElementStackEntry[this._stack.length * 3 / 2 + 1];
/*  876 */       System.arraycopy(tmp, 0, this._stack, 0, tmp.length);
/*  877 */       for (int i = tmp.length; i < this._stack.length; i++) {
/*  878 */         this._stack[i] = new ElementStackEntry();
/*      */       }
/*      */     } 
/*      */     
/*  882 */     this._stackTop = this._stack[this._depth++];
/*      */   }
/*      */ 
/*      */   
/*      */   private void popElementStack(int depth) {
/*  887 */     this._stackTop = this._stack[depth - 1];
/*      */     
/*  889 */     this._namespaceAIIsEnd = (this._stack[depth]).namespaceAIIsStart;
/*      */   }
/*      */ 
/*      */   
/*      */   private final class ElementStackEntry
/*      */   {
/*      */     String prefix;
/*      */     
/*      */     String uri;
/*      */     
/*      */     String localName;
/*      */     
/*      */     QName qname;
/*      */     
/*      */     int namespaceAIIsStart;
/*      */     
/*      */     int namespaceAIIsEnd;
/*      */ 
/*      */     
/*      */     private ElementStackEntry() {}
/*      */ 
/*      */     
/*      */     public void set(String prefix, String uri, String localName) {
/*  912 */       this.prefix = prefix;
/*  913 */       this.uri = uri;
/*  914 */       this.localName = localName;
/*  915 */       this.qname = null;
/*      */       
/*  917 */       this.namespaceAIIsStart = this.namespaceAIIsEnd = StreamReaderBufferProcessor.this._namespaceAIIsEnd;
/*      */     }
/*      */     
/*      */     public QName getQName() {
/*  921 */       if (this.qname == null) {
/*  922 */         this.qname = new QName(fixNull(this.uri), this.localName, fixNull(this.prefix));
/*      */       }
/*  924 */       return this.qname;
/*      */     }
/*      */     
/*      */     private String fixNull(String s) {
/*  928 */       return (s == null) ? "" : s;
/*      */     }
/*      */   }
/*      */   
/*      */   private final class InternalNamespaceContext implements NamespaceContextEx { private InternalNamespaceContext() {}
/*      */     
/*      */     public String getNamespaceURI(String prefix) {
/*  935 */       if (prefix == null) {
/*  936 */         throw new IllegalArgumentException("Prefix cannot be null");
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  944 */       if (StreamReaderBufferProcessor.this._stringInterningFeature) {
/*  945 */         prefix = prefix.intern();
/*      */ 
/*      */         
/*  948 */         for (int i = StreamReaderBufferProcessor.this._namespaceAIIsEnd - 1; i >= 0; i--) {
/*  949 */           if (prefix == StreamReaderBufferProcessor.this._namespaceAIIsPrefix[i]) {
/*  950 */             return StreamReaderBufferProcessor.this._namespaceAIIsNamespaceName[i];
/*      */           }
/*      */         } 
/*      */       } else {
/*      */         
/*  955 */         for (int i = StreamReaderBufferProcessor.this._namespaceAIIsEnd - 1; i >= 0; i--) {
/*  956 */           if (prefix.equals(StreamReaderBufferProcessor.this._namespaceAIIsPrefix[i])) {
/*  957 */             return StreamReaderBufferProcessor.this._namespaceAIIsNamespaceName[i];
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  963 */       if (prefix.equals("xml"))
/*  964 */         return "http://www.w3.org/XML/1998/namespace"; 
/*  965 */       if (prefix.equals("xmlns")) {
/*  966 */         return "http://www.w3.org/2000/xmlns/";
/*      */       }
/*      */       
/*  969 */       return null;
/*      */     }
/*      */     
/*      */     public String getPrefix(String namespaceURI) {
/*  973 */       Iterator<String> i = getPrefixes(namespaceURI);
/*  974 */       if (i.hasNext()) {
/*  975 */         return i.next();
/*      */       }
/*  977 */       return null;
/*      */     }
/*      */ 
/*      */     
/*      */     public Iterator getPrefixes(final String namespaceURI) {
/*  982 */       if (namespaceURI == null) {
/*  983 */         throw new IllegalArgumentException("NamespaceURI cannot be null");
/*      */       }
/*      */       
/*  986 */       if (namespaceURI.equals("http://www.w3.org/XML/1998/namespace"))
/*  987 */         return Collections.<String>singletonList("xml").iterator(); 
/*  988 */       if (namespaceURI.equals("http://www.w3.org/2000/xmlns/")) {
/*  989 */         return Collections.<String>singletonList("xmlns").iterator();
/*      */       }
/*      */       
/*  992 */       return new Iterator() {
/*  993 */           private int i = StreamReaderBufferProcessor.this._namespaceAIIsEnd - 1;
/*      */           private boolean requireFindNext = true;
/*      */           private String p;
/*      */           
/*      */           private String findNext() {
/*  998 */             while (this.i >= 0) {
/*      */               
/* 1000 */               if (namespaceURI.equals(StreamReaderBufferProcessor.this._namespaceAIIsNamespaceName[this.i]))
/*      */               {
/*      */                 
/* 1003 */                 if (StreamReaderBufferProcessor.InternalNamespaceContext.this.getNamespaceURI(StreamReaderBufferProcessor.this._namespaceAIIsPrefix[this.i]).equals(StreamReaderBufferProcessor.this._namespaceAIIsNamespaceName[this.i]))
/*      */                 {
/* 1005 */                   return this.p = StreamReaderBufferProcessor.this._namespaceAIIsPrefix[this.i];
/*      */                 }
/*      */               }
/* 1008 */               this.i--;
/*      */             } 
/* 1010 */             return this.p = null;
/*      */           }
/*      */           
/*      */           public boolean hasNext() {
/* 1014 */             if (this.requireFindNext) {
/* 1015 */               findNext();
/* 1016 */               this.requireFindNext = false;
/*      */             } 
/* 1018 */             return (this.p != null);
/*      */           }
/*      */           
/*      */           public Object next() {
/* 1022 */             if (this.requireFindNext) {
/* 1023 */               findNext();
/*      */             }
/* 1025 */             this.requireFindNext = true;
/*      */             
/* 1027 */             if (this.p == null) {
/* 1028 */               throw new NoSuchElementException();
/*      */             }
/*      */             
/* 1031 */             return this.p;
/*      */           }
/*      */           
/*      */           public void remove() {
/* 1035 */             throw new UnsupportedOperationException();
/*      */           }
/*      */         };
/*      */     }
/*      */     
/*      */     private class BindingImpl implements NamespaceContextEx.Binding {
/*      */       final String _prefix;
/*      */       final String _namespaceURI;
/*      */       
/*      */       BindingImpl(String prefix, String namespaceURI) {
/* 1045 */         this._prefix = prefix;
/* 1046 */         this._namespaceURI = namespaceURI;
/*      */       }
/*      */       
/*      */       public String getPrefix() {
/* 1050 */         return this._prefix;
/*      */       }
/*      */       
/*      */       public String getNamespaceURI() {
/* 1054 */         return this._namespaceURI;
/*      */       }
/*      */     }
/*      */     
/*      */     public Iterator<NamespaceContextEx.Binding> iterator() {
/* 1059 */       return new Iterator<NamespaceContextEx.Binding>() {
/* 1060 */           private final int end = StreamReaderBufferProcessor.this._namespaceAIIsEnd - 1;
/* 1061 */           private int current = this.end;
/*      */           private boolean requireFindNext = true;
/*      */           private NamespaceContextEx.Binding namespace;
/*      */           
/*      */           private NamespaceContextEx.Binding findNext() {
/* 1066 */             while (this.current >= 0) {
/* 1067 */               String prefix = StreamReaderBufferProcessor.this._namespaceAIIsPrefix[this.current];
/*      */ 
/*      */ 
/*      */               
/* 1071 */               int i = this.end;
/* 1072 */               for (; i > this.current && 
/* 1073 */                 !prefix.equals(StreamReaderBufferProcessor.this._namespaceAIIsPrefix[i]); i--);
/*      */ 
/*      */ 
/*      */               
/* 1077 */               if (i == this.current--)
/*      */               {
/* 1079 */                 return this.namespace = new StreamReaderBufferProcessor.InternalNamespaceContext.BindingImpl(prefix, StreamReaderBufferProcessor.this._namespaceAIIsNamespaceName[this.current]);
/*      */               }
/*      */             } 
/* 1082 */             return this.namespace = null;
/*      */           }
/*      */           
/*      */           public boolean hasNext() {
/* 1086 */             if (this.requireFindNext) {
/* 1087 */               findNext();
/* 1088 */               this.requireFindNext = false;
/*      */             } 
/* 1090 */             return (this.namespace != null);
/*      */           }
/*      */           
/*      */           public NamespaceContextEx.Binding next() {
/* 1094 */             if (this.requireFindNext) {
/* 1095 */               findNext();
/*      */             }
/* 1097 */             this.requireFindNext = true;
/*      */             
/* 1099 */             if (this.namespace == null) {
/* 1100 */               throw new NoSuchElementException();
/*      */             }
/*      */             
/* 1103 */             return this.namespace;
/*      */           }
/*      */           
/*      */           public void remove() {
/* 1107 */             throw new UnsupportedOperationException();
/*      */           }
/*      */         };
/*      */     } }
/*      */   
/*      */   private class DummyLocation implements Location { private DummyLocation() {}
/*      */     
/*      */     public int getLineNumber() {
/* 1115 */       return -1;
/*      */     }
/*      */     
/*      */     public int getColumnNumber() {
/* 1119 */       return -1;
/*      */     }
/*      */     
/*      */     public int getCharacterOffset() {
/* 1123 */       return -1;
/*      */     }
/*      */     
/*      */     public String getPublicId() {
/* 1127 */       return null;
/*      */     }
/*      */     
/*      */     public String getSystemId() {
/* 1131 */       return StreamReaderBufferProcessor.this._buffer.getSystemId();
/*      */     } }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String fixEmptyString(String s) {
/* 1137 */     if (s.length() == 0) return null; 
/* 1138 */     return s;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\stax\StreamReaderBufferProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */