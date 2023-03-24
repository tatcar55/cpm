/*      */ package com.sun.xml.fastinfoset.stax;
/*      */ 
/*      */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*      */ import com.sun.xml.fastinfoset.Decoder;
/*      */ import com.sun.xml.fastinfoset.DecoderStateTables;
/*      */ import com.sun.xml.fastinfoset.OctetBufferListener;
/*      */ import com.sun.xml.fastinfoset.QualifiedName;
/*      */ import com.sun.xml.fastinfoset.algorithm.BuiltInEncodingAlgorithm;
/*      */ import com.sun.xml.fastinfoset.algorithm.BuiltInEncodingAlgorithmFactory;
/*      */ import com.sun.xml.fastinfoset.org.apache.xerces.util.XMLChar;
/*      */ import com.sun.xml.fastinfoset.sax.AttributesHolder;
/*      */ import com.sun.xml.fastinfoset.util.CharArray;
/*      */ import com.sun.xml.fastinfoset.util.CharArrayString;
/*      */ import com.sun.xml.fastinfoset.util.PrefixArray;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.Iterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.namespace.NamespaceContext;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.stream.Location;
/*      */ import javax.xml.stream.XMLStreamException;
/*      */ import javax.xml.stream.XMLStreamReader;
/*      */ import org.jvnet.fastinfoset.EncodingAlgorithm;
/*      */ import org.jvnet.fastinfoset.EncodingAlgorithmException;
/*      */ import org.jvnet.fastinfoset.FastInfosetException;
/*      */ import org.jvnet.fastinfoset.stax.FastInfosetStreamReader;
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
/*      */ public class StAXDocumentParser
/*      */   extends Decoder
/*      */   implements XMLStreamReader, FastInfosetStreamReader, OctetBufferListener
/*      */ {
/*   61 */   private static final Logger logger = Logger.getLogger(StAXDocumentParser.class.getName());
/*      */ 
/*      */   
/*      */   protected static final int INTERNAL_STATE_START_DOCUMENT = 0;
/*      */   
/*      */   protected static final int INTERNAL_STATE_START_ELEMENT_TERMINATE = 1;
/*      */   
/*      */   protected static final int INTERNAL_STATE_SINGLE_TERMINATE_ELEMENT_WITH_NAMESPACES = 2;
/*      */   
/*      */   protected static final int INTERNAL_STATE_DOUBLE_TERMINATE_ELEMENT = 3;
/*      */   
/*      */   protected static final int INTERNAL_STATE_END_DOCUMENT = 4;
/*      */   
/*      */   protected static final int INTERNAL_STATE_VOID = -1;
/*      */   
/*      */   protected int _internalState;
/*      */   
/*      */   protected int _eventType;
/*      */   
/*   80 */   protected QualifiedName[] _qNameStack = new QualifiedName[32];
/*   81 */   protected int[] _namespaceAIIsStartStack = new int[32];
/*   82 */   protected int[] _namespaceAIIsEndStack = new int[32];
/*   83 */   protected int _stackCount = -1;
/*      */   
/*   85 */   protected String[] _namespaceAIIsPrefix = new String[32];
/*   86 */   protected String[] _namespaceAIIsNamespaceName = new String[32];
/*   87 */   protected int[] _namespaceAIIsPrefixIndex = new int[32];
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _namespaceAIIsIndex;
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _currentNamespaceAIIsStart;
/*      */ 
/*      */   
/*      */   protected int _currentNamespaceAIIsEnd;
/*      */ 
/*      */   
/*      */   protected QualifiedName _qualifiedName;
/*      */ 
/*      */   
/*  104 */   protected AttributesHolder _attributes = new AttributesHolder();
/*      */   
/*      */   protected boolean _clearAttributes = false;
/*      */   
/*      */   protected char[] _characters;
/*      */   
/*      */   protected int _charactersOffset;
/*      */   
/*      */   protected String _algorithmURI;
/*      */   
/*      */   protected int _algorithmId;
/*      */   
/*      */   protected boolean _isAlgorithmDataCloned;
/*      */   
/*      */   protected byte[] _algorithmData;
/*      */   
/*      */   protected int _algorithmDataOffset;
/*      */   
/*      */   protected int _algorithmDataLength;
/*      */   
/*      */   protected String _piTarget;
/*      */   
/*      */   protected String _piData;
/*  127 */   protected NamespaceContextImpl _nsContext = new NamespaceContextImpl();
/*      */   
/*      */   protected String _characterEncodingScheme;
/*      */   
/*      */   protected StAXManager _manager;
/*      */   
/*      */   private byte[] base64TaleBytes;
/*      */   
/*      */   private int base64TaleLength;
/*      */ 
/*      */   
/*      */   public StAXDocumentParser(InputStream s) {
/*  139 */     this();
/*  140 */     setInputStream(s);
/*  141 */     this._manager = new StAXManager(1);
/*      */   }
/*      */   
/*      */   public StAXDocumentParser(InputStream s, StAXManager manager) {
/*  145 */     this(s);
/*  146 */     this._manager = manager;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInputStream(InputStream s) {
/*  151 */     super.setInputStream(s);
/*  152 */     reset();
/*      */   }
/*      */ 
/*      */   
/*      */   public void reset() {
/*  157 */     super.reset();
/*  158 */     if (this._internalState != 0 && this._internalState != 4) {
/*      */ 
/*      */       
/*  161 */       for (int i = this._namespaceAIIsIndex - 1; i >= 0; i--) {
/*  162 */         this._prefixTable.popScopeWithPrefixEntry(this._namespaceAIIsPrefixIndex[i]);
/*      */       }
/*      */       
/*  165 */       this._stackCount = -1;
/*      */       
/*  167 */       this._namespaceAIIsIndex = 0;
/*  168 */       this._characters = null;
/*  169 */       this._algorithmData = null;
/*      */     } 
/*      */     
/*  172 */     this._characterEncodingScheme = "UTF-8";
/*  173 */     this._eventType = 7;
/*  174 */     this._internalState = 0;
/*      */   }
/*      */   
/*      */   protected void resetOnError() {
/*  178 */     super.reset();
/*      */     
/*  180 */     if (this._v != null) {
/*  181 */       this._prefixTable.clearCompletely();
/*      */     }
/*  183 */     this._duplicateAttributeVerifier.clear();
/*      */     
/*  185 */     this._stackCount = -1;
/*      */     
/*  187 */     this._namespaceAIIsIndex = 0;
/*  188 */     this._characters = null;
/*  189 */     this._algorithmData = null;
/*      */     
/*  191 */     this._eventType = 7;
/*  192 */     this._internalState = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getProperty(String name) throws IllegalArgumentException {
/*  199 */     if (this._manager != null) {
/*  200 */       return this._manager.getProperty(name);
/*      */     }
/*  202 */     return null; } public int next() throws XMLStreamException {
/*      */     try {
/*      */       QualifiedName qn;
/*      */       boolean addToTable;
/*      */       int index, b2;
/*  207 */       if (this._internalState != -1) {
/*  208 */         int i; switch (this._internalState) {
/*      */           case 0:
/*  210 */             decodeHeader();
/*  211 */             processDII();
/*      */             
/*  213 */             this._internalState = -1;
/*      */             break;
/*      */           case 1:
/*  216 */             if (this._currentNamespaceAIIsEnd > 0) {
/*  217 */               for (int j = this._currentNamespaceAIIsEnd - 1; j >= this._currentNamespaceAIIsStart; j--) {
/*  218 */                 this._prefixTable.popScopeWithPrefixEntry(this._namespaceAIIsPrefixIndex[j]);
/*      */               }
/*  220 */               this._namespaceAIIsIndex = this._currentNamespaceAIIsStart;
/*      */             } 
/*      */ 
/*      */             
/*  224 */             popStack();
/*      */             
/*  226 */             this._internalState = -1;
/*  227 */             return this._eventType = 2;
/*      */           
/*      */           case 2:
/*  230 */             for (i = this._currentNamespaceAIIsEnd - 1; i >= this._currentNamespaceAIIsStart; i--) {
/*  231 */               this._prefixTable.popScopeWithPrefixEntry(this._namespaceAIIsPrefixIndex[i]);
/*      */             }
/*  233 */             this._namespaceAIIsIndex = this._currentNamespaceAIIsStart;
/*  234 */             this._internalState = -1;
/*      */             break;
/*      */           
/*      */           case 3:
/*  238 */             if (this._currentNamespaceAIIsEnd > 0) {
/*  239 */               for (i = this._currentNamespaceAIIsEnd - 1; i >= this._currentNamespaceAIIsStart; i--) {
/*  240 */                 this._prefixTable.popScopeWithPrefixEntry(this._namespaceAIIsPrefixIndex[i]);
/*      */               }
/*  242 */               this._namespaceAIIsIndex = this._currentNamespaceAIIsStart;
/*      */             } 
/*      */             
/*  245 */             if (this._stackCount == -1) {
/*  246 */               this._internalState = 4;
/*  247 */               return this._eventType = 8;
/*      */             } 
/*      */ 
/*      */             
/*  251 */             popStack();
/*      */             
/*  253 */             this._internalState = (this._currentNamespaceAIIsEnd > 0) ? 2 : -1;
/*      */ 
/*      */             
/*  256 */             return this._eventType = 2;
/*      */           case 4:
/*  258 */             throw new NoSuchElementException(CommonResourceBundle.getInstance().getString("message.noMoreEvents"));
/*      */         } 
/*      */ 
/*      */       
/*      */       } 
/*  263 */       this._characters = null;
/*  264 */       this._algorithmData = null;
/*  265 */       this._currentNamespaceAIIsEnd = 0;
/*      */ 
/*      */       
/*  268 */       int b = read();
/*  269 */       switch (DecoderStateTables.EII(b)) {
/*      */         case 0:
/*  271 */           processEII(this._elementNameTable._array[b], false);
/*  272 */           return this._eventType;
/*      */         case 1:
/*  274 */           processEII(this._elementNameTable._array[b & 0x1F], true);
/*  275 */           return this._eventType;
/*      */         case 2:
/*  277 */           processEII(processEIIIndexMedium(b), ((b & 0x40) > 0));
/*  278 */           return this._eventType;
/*      */         case 3:
/*  280 */           processEII(processEIIIndexLarge(b), ((b & 0x40) > 0));
/*  281 */           return this._eventType;
/*      */         
/*      */         case 5:
/*  284 */           qn = processLiteralQualifiedName(b & 0x3, this._elementNameTable.getNext());
/*      */ 
/*      */           
/*  287 */           this._elementNameTable.add(qn);
/*  288 */           processEII(qn, ((b & 0x40) > 0));
/*  289 */           return this._eventType;
/*      */         
/*      */         case 4:
/*  292 */           processEIIWithNamespaces(((b & 0x40) > 0));
/*  293 */           return this._eventType;
/*      */         case 6:
/*  295 */           this._octetBufferLength = (b & 0x1) + 1;
/*      */           
/*  297 */           processUtf8CharacterString(b);
/*  298 */           return this._eventType = 4;
/*      */         case 7:
/*  300 */           this._octetBufferLength = read() + 3;
/*  301 */           processUtf8CharacterString(b);
/*  302 */           return this._eventType = 4;
/*      */         case 8:
/*  304 */           this._octetBufferLength = (read() << 24 | read() << 16 | read() << 8 | read()) + 259;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  309 */           processUtf8CharacterString(b);
/*  310 */           return this._eventType = 4;
/*      */         case 9:
/*  312 */           this._octetBufferLength = (b & 0x1) + 1;
/*      */           
/*  314 */           processUtf16CharacterString(b);
/*  315 */           return this._eventType = 4;
/*      */         case 10:
/*  317 */           this._octetBufferLength = read() + 3;
/*  318 */           processUtf16CharacterString(b);
/*  319 */           return this._eventType = 4;
/*      */         case 11:
/*  321 */           this._octetBufferLength = (read() << 24 | read() << 16 | read() << 8 | read()) + 259;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  326 */           processUtf16CharacterString(b);
/*  327 */           return this._eventType = 4;
/*      */         
/*      */         case 12:
/*  330 */           addToTable = ((b & 0x10) > 0);
/*      */           
/*  332 */           this._identifier = (b & 0x2) << 6;
/*  333 */           b2 = read();
/*  334 */           this._identifier |= (b2 & 0xFC) >> 2;
/*      */           
/*  336 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(b2);
/*      */           
/*  338 */           decodeRestrictedAlphabetAsCharBuffer();
/*      */           
/*  340 */           if (addToTable) {
/*  341 */             this._charactersOffset = this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*  342 */             this._characters = this._characterContentChunkTable._array;
/*      */           } else {
/*  344 */             this._characters = this._charBuffer;
/*  345 */             this._charactersOffset = 0;
/*      */           } 
/*  347 */           return this._eventType = 4;
/*      */ 
/*      */         
/*      */         case 13:
/*  351 */           addToTable = ((b & 0x10) > 0);
/*      */           
/*  353 */           this._algorithmId = (b & 0x2) << 6;
/*  354 */           b2 = read();
/*  355 */           this._algorithmId |= (b2 & 0xFC) >> 2;
/*      */           
/*  357 */           decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(b2);
/*  358 */           processCIIEncodingAlgorithm(addToTable);
/*      */           
/*  360 */           if (this._algorithmId == 9) {
/*  361 */             return this._eventType = 12;
/*      */           }
/*      */           
/*  364 */           return this._eventType = 4;
/*      */ 
/*      */         
/*      */         case 14:
/*  368 */           index = b & 0xF;
/*  369 */           this._characterContentChunkTable._cachedIndex = index;
/*      */           
/*  371 */           this._characters = this._characterContentChunkTable._array;
/*  372 */           this._charactersOffset = this._characterContentChunkTable._offset[index];
/*  373 */           this._charBufferLength = this._characterContentChunkTable._length[index];
/*  374 */           return this._eventType = 4;
/*      */ 
/*      */         
/*      */         case 15:
/*  378 */           index = ((b & 0x3) << 8 | read()) + 16;
/*      */           
/*  380 */           this._characterContentChunkTable._cachedIndex = index;
/*      */           
/*  382 */           this._characters = this._characterContentChunkTable._array;
/*  383 */           this._charactersOffset = this._characterContentChunkTable._offset[index];
/*  384 */           this._charBufferLength = this._characterContentChunkTable._length[index];
/*  385 */           return this._eventType = 4;
/*      */ 
/*      */         
/*      */         case 16:
/*  389 */           index = ((b & 0x3) << 16 | read() << 8 | read()) + 1040;
/*      */ 
/*      */ 
/*      */           
/*  393 */           this._characterContentChunkTable._cachedIndex = index;
/*      */           
/*  395 */           this._characters = this._characterContentChunkTable._array;
/*  396 */           this._charactersOffset = this._characterContentChunkTable._offset[index];
/*  397 */           this._charBufferLength = this._characterContentChunkTable._length[index];
/*  398 */           return this._eventType = 4;
/*      */ 
/*      */         
/*      */         case 17:
/*  402 */           index = (read() << 16 | read() << 8 | read()) + 263184;
/*      */ 
/*      */ 
/*      */           
/*  406 */           this._characterContentChunkTable._cachedIndex = index;
/*      */           
/*  408 */           this._characters = this._characterContentChunkTable._array;
/*  409 */           this._charactersOffset = this._characterContentChunkTable._offset[index];
/*  410 */           this._charBufferLength = this._characterContentChunkTable._length[index];
/*  411 */           return this._eventType = 4;
/*      */         
/*      */         case 18:
/*  414 */           processCommentII();
/*  415 */           return this._eventType;
/*      */         case 19:
/*  417 */           processProcessingII();
/*  418 */           return this._eventType;
/*      */         
/*      */         case 21:
/*  421 */           processUnexpandedEntityReference(b);
/*      */           
/*  423 */           return next();
/*      */         
/*      */         case 23:
/*  426 */           if (this._stackCount != -1) {
/*      */             
/*  428 */             popStack();
/*      */             
/*  430 */             this._internalState = 3;
/*  431 */             return this._eventType = 2;
/*      */           } 
/*      */           
/*  434 */           this._internalState = 4;
/*  435 */           return this._eventType = 8;
/*      */         case 22:
/*  437 */           if (this._stackCount != -1) {
/*      */             
/*  439 */             popStack();
/*      */             
/*  441 */             if (this._currentNamespaceAIIsEnd > 0) {
/*  442 */               this._internalState = 2;
/*      */             }
/*  444 */             return this._eventType = 2;
/*      */           } 
/*      */           
/*  447 */           this._internalState = 4;
/*  448 */           return this._eventType = 8;
/*      */       } 
/*  450 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEII"));
/*      */     }
/*  452 */     catch (IOException e) {
/*  453 */       resetOnError();
/*  454 */       logger.log(Level.FINE, "next() exception", e);
/*  455 */       throw new XMLStreamException(e);
/*  456 */     } catch (FastInfosetException e) {
/*  457 */       resetOnError();
/*  458 */       logger.log(Level.FINE, "next() exception", (Throwable)e);
/*  459 */       throw new XMLStreamException(e);
/*  460 */     } catch (RuntimeException e) {
/*  461 */       resetOnError();
/*  462 */       logger.log(Level.FINE, "next() exception", e);
/*  463 */       throw e;
/*      */     } 
/*      */   }
/*      */   
/*      */   private final void processUtf8CharacterString(int b) throws IOException {
/*  468 */     if ((b & 0x10) > 0) {
/*  469 */       this._characterContentChunkTable.ensureSize(this._octetBufferLength);
/*  470 */       this._characters = this._characterContentChunkTable._array;
/*  471 */       this._charactersOffset = this._characterContentChunkTable._arrayIndex;
/*  472 */       decodeUtf8StringAsCharBuffer(this._characterContentChunkTable._array, this._charactersOffset);
/*  473 */       this._characterContentChunkTable.add(this._charBufferLength);
/*      */     } else {
/*  475 */       decodeUtf8StringAsCharBuffer();
/*  476 */       this._characters = this._charBuffer;
/*  477 */       this._charactersOffset = 0;
/*      */     } 
/*      */   }
/*      */   
/*      */   private final void processUtf16CharacterString(int b) throws IOException {
/*  482 */     decodeUtf16StringAsCharBuffer();
/*  483 */     if ((b & 0x10) > 0) {
/*  484 */       this._charactersOffset = this._characterContentChunkTable.add(this._charBuffer, this._charBufferLength);
/*  485 */       this._characters = this._characterContentChunkTable._array;
/*      */     } else {
/*  487 */       this._characters = this._charBuffer;
/*  488 */       this._charactersOffset = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private final void popStack() {
/*  494 */     this._qualifiedName = this._qNameStack[this._stackCount];
/*  495 */     this._currentNamespaceAIIsStart = this._namespaceAIIsStartStack[this._stackCount];
/*  496 */     this._currentNamespaceAIIsEnd = this._namespaceAIIsEndStack[this._stackCount];
/*  497 */     this._qNameStack[this._stackCount--] = null;
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
/*      */   public final void require(int type, String namespaceURI, String localName) throws XMLStreamException {
/*  509 */     if (type != this._eventType)
/*  510 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.eventTypeNotMatch", new Object[] { getEventTypeString(type) })); 
/*  511 */     if (namespaceURI != null && !namespaceURI.equals(getNamespaceURI()))
/*  512 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.namespaceURINotMatch", new Object[] { namespaceURI })); 
/*  513 */     if (localName != null && !localName.equals(getLocalName())) {
/*  514 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.localNameNotMatch", new Object[] { localName }));
/*      */     }
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
/*      */   public final String getElementText() throws XMLStreamException {
/*  527 */     if (getEventType() != 1) {
/*  528 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.mustBeOnSTARTELEMENT"), getLocation());
/*      */     }
/*      */ 
/*      */     
/*  532 */     next();
/*  533 */     return getElementText(true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getElementText(boolean startElementRead) throws XMLStreamException {
/*  539 */     if (!startElementRead) {
/*  540 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.mustBeOnSTARTELEMENT"), getLocation());
/*      */     }
/*      */     
/*  543 */     int eventType = getEventType();
/*  544 */     StringBuffer content = new StringBuffer();
/*  545 */     while (eventType != 2) {
/*  546 */       if (eventType == 4 || eventType == 12 || eventType == 6 || eventType == 9) {
/*      */ 
/*      */ 
/*      */         
/*  550 */         content.append(getText());
/*  551 */       } else if (eventType != 3 && eventType != 5) {
/*      */ 
/*      */         
/*  554 */         if (eventType == 8)
/*  555 */           throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.unexpectedEOF")); 
/*  556 */         if (eventType == 1) {
/*  557 */           throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.getElementTextExpectTextOnly"), getLocation());
/*      */         }
/*      */         
/*  560 */         throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.unexpectedEventType") + getEventTypeString(eventType), getLocation());
/*      */       } 
/*      */       
/*  563 */       eventType = next();
/*      */     } 
/*  565 */     return content.toString();
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
/*      */   
/*      */   public final int nextTag() throws XMLStreamException {
/*  582 */     next();
/*  583 */     return nextTag(true);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final int nextTag(boolean currentTagRead) throws XMLStreamException {
/*  589 */     int eventType = getEventType();
/*  590 */     if (!currentTagRead) {
/*  591 */       eventType = next();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  597 */     while ((eventType == 4 && isWhiteSpace()) || (eventType == 12 && isWhiteSpace()) || eventType == 6 || eventType == 3 || eventType == 5) {
/*  598 */       eventType = next();
/*      */     }
/*  600 */     if (eventType != 1 && eventType != 2) {
/*  601 */       throw new XMLStreamException(CommonResourceBundle.getInstance().getString("message.expectedStartOrEnd"), getLocation());
/*      */     }
/*  603 */     return eventType;
/*      */   }
/*      */   
/*      */   public final boolean hasNext() throws XMLStreamException {
/*  607 */     return (this._eventType != 8);
/*      */   }
/*      */   
/*      */   public void close() throws XMLStreamException {
/*      */     try {
/*  612 */       closeIfRequired();
/*  613 */     } catch (IOException ex) {}
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getNamespaceURI(String prefix) {
/*  618 */     String namespace = getNamespaceDecl(prefix);
/*  619 */     if (namespace == null) {
/*  620 */       if (prefix == null) {
/*  621 */         throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.nullPrefix"));
/*      */       }
/*  623 */       return null;
/*      */     } 
/*  625 */     return namespace;
/*      */   }
/*      */   
/*      */   public final boolean isStartElement() {
/*  629 */     return (this._eventType == 1);
/*      */   }
/*      */   
/*      */   public final boolean isEndElement() {
/*  633 */     return (this._eventType == 2);
/*      */   }
/*      */   
/*      */   public final boolean isCharacters() {
/*  637 */     return (this._eventType == 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isWhiteSpace() {
/*  647 */     if (isCharacters() || this._eventType == 12) {
/*  648 */       char[] ch = getTextCharacters();
/*  649 */       int start = getTextStart();
/*  650 */       int length = getTextLength();
/*  651 */       for (int i = start; i < start + length; i++) {
/*  652 */         if (!XMLChar.isSpace(ch[i])) {
/*  653 */           return false;
/*      */         }
/*      */       } 
/*  656 */       return true;
/*      */     } 
/*  658 */     return false;
/*      */   }
/*      */   
/*      */   public final String getAttributeValue(String namespaceURI, String localName) {
/*  662 */     if (this._eventType != 1) {
/*  663 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*      */     
/*  666 */     if (localName == null) {
/*  667 */       throw new IllegalArgumentException();
/*      */     }
/*      */     
/*  670 */     if (namespaceURI != null) {
/*  671 */       for (int i = 0; i < this._attributes.getLength(); i++) {
/*  672 */         if (this._attributes.getLocalName(i).equals(localName) && this._attributes.getURI(i).equals(namespaceURI))
/*      */         {
/*  674 */           return this._attributes.getValue(i);
/*      */         }
/*      */       } 
/*      */     } else {
/*  678 */       for (int i = 0; i < this._attributes.getLength(); i++) {
/*  679 */         if (this._attributes.getLocalName(i).equals(localName)) {
/*  680 */           return this._attributes.getValue(i);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  685 */     return null;
/*      */   }
/*      */   
/*      */   public final int getAttributeCount() {
/*  689 */     if (this._eventType != 1) {
/*  690 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*      */     
/*  693 */     return this._attributes.getLength();
/*      */   }
/*      */   
/*      */   public final QName getAttributeName(int index) {
/*  697 */     if (this._eventType != 1) {
/*  698 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*  700 */     return this._attributes.getQualifiedName(index).getQName();
/*      */   }
/*      */   
/*      */   public final String getAttributeNamespace(int index) {
/*  704 */     if (this._eventType != 1) {
/*  705 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*      */     
/*  708 */     return this._attributes.getURI(index);
/*      */   }
/*      */   
/*      */   public final String getAttributeLocalName(int index) {
/*  712 */     if (this._eventType != 1) {
/*  713 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*  715 */     return this._attributes.getLocalName(index);
/*      */   }
/*      */   
/*      */   public final String getAttributePrefix(int index) {
/*  719 */     if (this._eventType != 1) {
/*  720 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*  722 */     return this._attributes.getPrefix(index);
/*      */   }
/*      */   
/*      */   public final String getAttributeType(int index) {
/*  726 */     if (this._eventType != 1) {
/*  727 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*  729 */     return this._attributes.getType(index);
/*      */   }
/*      */   
/*      */   public final String getAttributeValue(int index) {
/*  733 */     if (this._eventType != 1) {
/*  734 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*  736 */     return this._attributes.getValue(index);
/*      */   }
/*      */   
/*      */   public final boolean isAttributeSpecified(int index) {
/*  740 */     return false;
/*      */   }
/*      */   
/*      */   public final int getNamespaceCount() {
/*  744 */     if (this._eventType == 1 || this._eventType == 2) {
/*  745 */       return (this._currentNamespaceAIIsEnd > 0) ? (this._currentNamespaceAIIsEnd - this._currentNamespaceAIIsStart) : 0;
/*      */     }
/*  747 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetNamespaceCount"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getNamespacePrefix(int index) {
/*  752 */     if (this._eventType == 1 || this._eventType == 2) {
/*  753 */       return this._namespaceAIIsPrefix[this._currentNamespaceAIIsStart + index];
/*      */     }
/*  755 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetNamespacePrefix"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getNamespaceURI(int index) {
/*  760 */     if (this._eventType == 1 || this._eventType == 2) {
/*  761 */       return this._namespaceAIIsNamespaceName[this._currentNamespaceAIIsStart + index];
/*      */     }
/*  763 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetNamespacePrefix"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final NamespaceContext getNamespaceContext() {
/*  768 */     return this._nsContext;
/*      */   }
/*      */   
/*      */   public final int getEventType() {
/*  772 */     return this._eventType;
/*      */   }
/*      */   
/*      */   public final String getText() {
/*  776 */     if (this._characters == null) {
/*  777 */       checkTextState();
/*      */     }
/*      */     
/*  780 */     if (this._characters == this._characterContentChunkTable._array) {
/*  781 */       return this._characterContentChunkTable.getString(this._characterContentChunkTable._cachedIndex);
/*      */     }
/*  783 */     return new String(this._characters, this._charactersOffset, this._charBufferLength);
/*      */   }
/*      */ 
/*      */   
/*      */   public final char[] getTextCharacters() {
/*  788 */     if (this._characters == null) {
/*  789 */       checkTextState();
/*      */     }
/*      */     
/*  792 */     return this._characters;
/*      */   }
/*      */   
/*      */   public final int getTextStart() {
/*  796 */     if (this._characters == null) {
/*  797 */       checkTextState();
/*      */     }
/*      */     
/*  800 */     return this._charactersOffset;
/*      */   }
/*      */   
/*      */   public final int getTextLength() {
/*  804 */     if (this._characters == null) {
/*  805 */       checkTextState();
/*      */     }
/*      */     
/*  808 */     return this._charBufferLength;
/*      */   }
/*      */ 
/*      */   
/*      */   public final int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
/*  813 */     if (this._characters == null) {
/*  814 */       checkTextState();
/*      */     }
/*      */     
/*      */     try {
/*  818 */       int bytesToCopy = Math.min(this._charBufferLength, length);
/*  819 */       System.arraycopy(this._characters, this._charactersOffset + sourceStart, target, targetStart, bytesToCopy);
/*      */       
/*  821 */       return bytesToCopy;
/*  822 */     } catch (IndexOutOfBoundsException e) {
/*  823 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void checkTextState() {
/*  828 */     if (this._algorithmData == null) {
/*  829 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.InvalidStateForText"));
/*      */     }
/*      */     
/*      */     try {
/*  833 */       convertEncodingAlgorithmDataToCharacters();
/*  834 */     } catch (Exception e) {
/*  835 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.InvalidStateForText"));
/*      */     } 
/*      */   }
/*      */   
/*      */   public final String getEncoding() {
/*  840 */     return this._characterEncodingScheme;
/*      */   }
/*      */   
/*      */   public final boolean hasText() {
/*  844 */     return (this._characters != null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final Location getLocation() {
/*  850 */     return EventLocation.getNilLocation();
/*      */   }
/*      */   
/*      */   public final QName getName() {
/*  854 */     if (this._eventType == 1 || this._eventType == 2) {
/*  855 */       return this._qualifiedName.getQName();
/*      */     }
/*  857 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetName"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getLocalName() {
/*  862 */     if (this._eventType == 1 || this._eventType == 2) {
/*  863 */       return this._qualifiedName.localName;
/*      */     }
/*  865 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetLocalName"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean hasName() {
/*  870 */     return (this._eventType == 1 || this._eventType == 2);
/*      */   }
/*      */   
/*      */   public final String getNamespaceURI() {
/*  874 */     if (this._eventType == 1 || this._eventType == 2) {
/*  875 */       return this._qualifiedName.namespaceName;
/*      */     }
/*  877 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetNamespaceURI"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getPrefix() {
/*  882 */     if (this._eventType == 1 || this._eventType == 2) {
/*  883 */       return this._qualifiedName.prefix;
/*      */     }
/*  885 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetPrefix"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getVersion() {
/*  890 */     return null;
/*      */   }
/*      */   
/*      */   public final boolean isStandalone() {
/*  894 */     return false;
/*      */   }
/*      */   
/*      */   public final boolean standaloneSet() {
/*  898 */     return false;
/*      */   }
/*      */   
/*      */   public final String getCharacterEncodingScheme() {
/*  902 */     return null;
/*      */   }
/*      */   
/*      */   public final String getPITarget() {
/*  906 */     if (this._eventType != 3) {
/*  907 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetPITarget"));
/*      */     }
/*      */     
/*  910 */     return this._piTarget;
/*      */   }
/*      */   
/*      */   public final String getPIData() {
/*  914 */     if (this._eventType != 3) {
/*  915 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetPIData"));
/*      */     }
/*      */     
/*  918 */     return this._piData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final String getNameString() {
/*  925 */     if (this._eventType == 1 || this._eventType == 2) {
/*  926 */       return this._qualifiedName.getQNameString();
/*      */     }
/*  928 */     throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetName"));
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getAttributeNameString(int index) {
/*  933 */     if (this._eventType != 1) {
/*  934 */       throw new IllegalStateException(CommonResourceBundle.getInstance().getString("message.invalidCallingGetAttributeValue"));
/*      */     }
/*  936 */     return this._attributes.getQualifiedName(index).getQNameString();
/*      */   }
/*      */ 
/*      */   
/*      */   public final String getTextAlgorithmURI() {
/*  941 */     return this._algorithmURI;
/*      */   }
/*      */   
/*      */   public final int getTextAlgorithmIndex() {
/*  945 */     return this._algorithmId;
/*      */   }
/*      */   
/*      */   public final boolean hasTextAlgorithmBytes() {
/*  949 */     return (this._algorithmData != null);
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
/*      */   public final byte[] getTextAlgorithmBytes() {
/*  962 */     if (this._algorithmData == null) {
/*  963 */       return null;
/*      */     }
/*      */     
/*  966 */     byte[] algorithmData = new byte[this._algorithmData.length];
/*  967 */     System.arraycopy(this._algorithmData, 0, algorithmData, 0, this._algorithmData.length);
/*  968 */     return algorithmData;
/*      */   }
/*      */   
/*      */   public final byte[] getTextAlgorithmBytesClone() {
/*  972 */     if (this._algorithmData == null) {
/*  973 */       return null;
/*      */     }
/*      */     
/*  976 */     byte[] algorithmData = new byte[this._algorithmDataLength];
/*  977 */     System.arraycopy(this._algorithmData, this._algorithmDataOffset, algorithmData, 0, this._algorithmDataLength);
/*  978 */     return algorithmData;
/*      */   }
/*      */   
/*      */   public final int getTextAlgorithmStart() {
/*  982 */     return this._algorithmDataOffset;
/*      */   }
/*      */   
/*      */   public final int getTextAlgorithmLength() {
/*  986 */     return this._algorithmDataLength;
/*      */   }
/*      */ 
/*      */   
/*      */   public final int getTextAlgorithmBytes(int sourceStart, byte[] target, int targetStart, int length) throws XMLStreamException {
/*      */     try {
/*  992 */       System.arraycopy(this._algorithmData, sourceStart, target, targetStart, length);
/*      */       
/*  994 */       return length;
/*  995 */     } catch (IndexOutOfBoundsException e) {
/*  996 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final int peekNext() throws XMLStreamException {
/*      */     try {
/* 1004 */       switch (DecoderStateTables.EII(peek(this))) {
/*      */         case 0:
/*      */         case 1:
/*      */         case 2:
/*      */         case 3:
/*      */         case 4:
/*      */         case 5:
/* 1011 */           return 1;
/*      */         case 6:
/*      */         case 7:
/*      */         case 8:
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*      */         case 12:
/*      */         case 13:
/*      */         case 14:
/*      */         case 15:
/*      */         case 16:
/*      */         case 17:
/* 1024 */           return 4;
/*      */         case 18:
/* 1026 */           return 5;
/*      */         case 19:
/* 1028 */           return 3;
/*      */         case 21:
/* 1030 */           return 9;
/*      */         case 22:
/*      */         case 23:
/* 1033 */           return (this._stackCount != -1) ? 2 : 8;
/*      */       } 
/* 1035 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEII"));
/*      */     
/*      */     }
/* 1038 */     catch (IOException e) {
/* 1039 */       throw new XMLStreamException(e);
/* 1040 */     } catch (FastInfosetException e) {
/* 1041 */       throw new XMLStreamException(e);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void onBeforeOctetBufferOverwrite() {
/* 1046 */     if (this._algorithmData != null) {
/* 1047 */       this._algorithmData = getTextAlgorithmBytesClone();
/* 1048 */       this._algorithmDataOffset = 0;
/* 1049 */       this._isAlgorithmDataCloned = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final int accessNamespaceCount() {
/* 1056 */     return (this._currentNamespaceAIIsEnd > 0) ? (this._currentNamespaceAIIsEnd - this._currentNamespaceAIIsStart) : 0;
/*      */   }
/*      */   
/*      */   public final String accessLocalName() {
/* 1060 */     return this._qualifiedName.localName;
/*      */   }
/*      */   
/*      */   public final String accessNamespaceURI() {
/* 1064 */     return this._qualifiedName.namespaceName;
/*      */   }
/*      */   
/*      */   public final String accessPrefix() {
/* 1068 */     return this._qualifiedName.prefix;
/*      */   }
/*      */   
/*      */   public final char[] accessTextCharacters() {
/* 1072 */     if (this._characters == null) return null;
/*      */ 
/*      */     
/* 1075 */     char[] clonedCharacters = new char[this._characters.length];
/* 1076 */     System.arraycopy(this._characters, 0, clonedCharacters, 0, this._characters.length);
/* 1077 */     return clonedCharacters;
/*      */   }
/*      */   
/*      */   public final int accessTextStart() {
/* 1081 */     return this._charactersOffset;
/*      */   }
/*      */   
/*      */   public final int accessTextLength() {
/* 1085 */     return this._charBufferLength;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void processDII() throws FastInfosetException, IOException {
/* 1091 */     int b = read();
/* 1092 */     if (b > 0) {
/* 1093 */       processDIIOptionalProperties(b);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void processDIIOptionalProperties(int b) throws FastInfosetException, IOException {
/* 1099 */     if (b == 32) {
/* 1100 */       decodeInitialVocabulary();
/*      */       
/*      */       return;
/*      */     } 
/* 1104 */     if ((b & 0x40) > 0) {
/* 1105 */       decodeAdditionalData();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1112 */     if ((b & 0x20) > 0) {
/* 1113 */       decodeInitialVocabulary();
/*      */     }
/*      */     
/* 1116 */     if ((b & 0x10) > 0) {
/* 1117 */       decodeNotations();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1127 */     if ((b & 0x8) > 0) {
/* 1128 */       decodeUnparsedEntities();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1138 */     if ((b & 0x4) > 0) {
/* 1139 */       this._characterEncodingScheme = decodeCharacterEncodingScheme();
/*      */     }
/*      */     
/* 1142 */     if ((b & 0x2) > 0) {
/* 1143 */       boolean standalone = (read() > 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1150 */     if ((b & 0x1) > 0) {
/* 1151 */       decodeVersion();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void resizeNamespaceAIIs() {
/* 1161 */     String[] namespaceAIIsPrefix = new String[this._namespaceAIIsIndex * 2];
/* 1162 */     System.arraycopy(this._namespaceAIIsPrefix, 0, namespaceAIIsPrefix, 0, this._namespaceAIIsIndex);
/* 1163 */     this._namespaceAIIsPrefix = namespaceAIIsPrefix;
/*      */     
/* 1165 */     String[] namespaceAIIsNamespaceName = new String[this._namespaceAIIsIndex * 2];
/* 1166 */     System.arraycopy(this._namespaceAIIsNamespaceName, 0, namespaceAIIsNamespaceName, 0, this._namespaceAIIsIndex);
/* 1167 */     this._namespaceAIIsNamespaceName = namespaceAIIsNamespaceName;
/*      */     
/* 1169 */     int[] namespaceAIIsPrefixIndex = new int[this._namespaceAIIsIndex * 2];
/* 1170 */     System.arraycopy(this._namespaceAIIsPrefixIndex, 0, namespaceAIIsPrefixIndex, 0, this._namespaceAIIsIndex);
/* 1171 */     this._namespaceAIIsPrefixIndex = namespaceAIIsPrefixIndex;
/*      */   }
/*      */   protected final void processEIIWithNamespaces(boolean hasAttributes) throws FastInfosetException, IOException {
/*      */     QualifiedName qn;
/* 1175 */     if (++this._prefixTable._declarationId == Integer.MAX_VALUE) {
/* 1176 */       this._prefixTable.clearDeclarationIds();
/*      */     }
/*      */     
/* 1179 */     this._currentNamespaceAIIsStart = this._namespaceAIIsIndex;
/* 1180 */     String prefix = "", namespaceName = "";
/* 1181 */     int b = read();
/* 1182 */     while ((b & 0xFC) == 204) {
/* 1183 */       if (this._namespaceAIIsIndex == this._namespaceAIIsPrefix.length) {
/* 1184 */         resizeNamespaceAIIs();
/*      */       }
/*      */       
/* 1187 */       switch (b & 0x3) {
/*      */ 
/*      */         
/*      */         case 0:
/* 1191 */           this._namespaceAIIsNamespaceName[this._namespaceAIIsIndex] = ""; this._namespaceAIIsPrefix[this._namespaceAIIsIndex] = ""; prefix = namespaceName = "";
/*      */ 
/*      */ 
/*      */           
/* 1195 */           this._namespaceAIIsPrefixIndex[this._namespaceAIIsIndex++] = -1; this._namespaceNameIndex = this._prefixIndex = -1;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 1:
/* 1200 */           prefix = this._namespaceAIIsPrefix[this._namespaceAIIsIndex] = "";
/* 1201 */           namespaceName = this._namespaceAIIsNamespaceName[this._namespaceAIIsIndex] = decodeIdentifyingNonEmptyStringOnFirstBitAsNamespaceName(false);
/*      */ 
/*      */           
/* 1204 */           this._prefixIndex = this._namespaceAIIsPrefixIndex[this._namespaceAIIsIndex++] = -1;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 2:
/* 1209 */           prefix = this._namespaceAIIsPrefix[this._namespaceAIIsIndex] = decodeIdentifyingNonEmptyStringOnFirstBitAsPrefix(false);
/*      */           
/* 1211 */           namespaceName = this._namespaceAIIsNamespaceName[this._namespaceAIIsIndex] = "";
/*      */           
/* 1213 */           this._namespaceNameIndex = -1;
/* 1214 */           this._namespaceAIIsPrefixIndex[this._namespaceAIIsIndex++] = this._prefixIndex;
/*      */           break;
/*      */ 
/*      */         
/*      */         case 3:
/* 1219 */           prefix = this._namespaceAIIsPrefix[this._namespaceAIIsIndex] = decodeIdentifyingNonEmptyStringOnFirstBitAsPrefix(true);
/*      */           
/* 1221 */           namespaceName = this._namespaceAIIsNamespaceName[this._namespaceAIIsIndex] = decodeIdentifyingNonEmptyStringOnFirstBitAsNamespaceName(true);
/*      */ 
/*      */           
/* 1224 */           this._namespaceAIIsPrefixIndex[this._namespaceAIIsIndex++] = this._prefixIndex;
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1229 */       this._prefixTable.pushScopeWithPrefixEntry(prefix, namespaceName, this._prefixIndex, this._namespaceNameIndex);
/*      */       
/* 1231 */       b = read();
/*      */     } 
/* 1233 */     if (b != 240) {
/* 1234 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.EIInamespaceNameNotTerminatedCorrectly"));
/*      */     }
/* 1236 */     this._currentNamespaceAIIsEnd = this._namespaceAIIsIndex;
/*      */     
/* 1238 */     b = read();
/* 1239 */     switch (DecoderStateTables.EII(b)) {
/*      */       case 0:
/* 1241 */         processEII(this._elementNameTable._array[b], hasAttributes);
/*      */         return;
/*      */       case 2:
/* 1244 */         processEII(processEIIIndexMedium(b), hasAttributes);
/*      */         return;
/*      */       case 3:
/* 1247 */         processEII(processEIIIndexLarge(b), hasAttributes);
/*      */         return;
/*      */       
/*      */       case 5:
/* 1251 */         qn = processLiteralQualifiedName(b & 0x3, this._elementNameTable.getNext());
/*      */ 
/*      */         
/* 1254 */         this._elementNameTable.add(qn);
/* 1255 */         processEII(qn, hasAttributes);
/*      */         return;
/*      */     } 
/*      */     
/* 1259 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IllegalStateDecodingEIIAfterAIIs"));
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void processEII(QualifiedName name, boolean hasAttributes) throws FastInfosetException, IOException {
/* 1264 */     if (this._prefixTable._currentInScope[name.prefixIndex] != name.namespaceNameIndex) {
/* 1265 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.qnameOfEIINotInScope"));
/*      */     }
/*      */     
/* 1268 */     this._eventType = 1;
/* 1269 */     this._qualifiedName = name;
/*      */     
/* 1271 */     if (this._clearAttributes) {
/* 1272 */       this._attributes.clear();
/* 1273 */       this._clearAttributes = false;
/*      */     } 
/*      */     
/* 1276 */     if (hasAttributes) {
/* 1277 */       processAIIs();
/*      */     }
/*      */ 
/*      */     
/* 1281 */     this._stackCount++;
/* 1282 */     if (this._stackCount == this._qNameStack.length) {
/* 1283 */       QualifiedName[] qNameStack = new QualifiedName[this._qNameStack.length * 2];
/* 1284 */       System.arraycopy(this._qNameStack, 0, qNameStack, 0, this._qNameStack.length);
/* 1285 */       this._qNameStack = qNameStack;
/*      */       
/* 1287 */       int[] namespaceAIIsStartStack = new int[this._namespaceAIIsStartStack.length * 2];
/* 1288 */       System.arraycopy(this._namespaceAIIsStartStack, 0, namespaceAIIsStartStack, 0, this._namespaceAIIsStartStack.length);
/* 1289 */       this._namespaceAIIsStartStack = namespaceAIIsStartStack;
/*      */       
/* 1291 */       int[] namespaceAIIsEndStack = new int[this._namespaceAIIsEndStack.length * 2];
/* 1292 */       System.arraycopy(this._namespaceAIIsEndStack, 0, namespaceAIIsEndStack, 0, this._namespaceAIIsEndStack.length);
/* 1293 */       this._namespaceAIIsEndStack = namespaceAIIsEndStack;
/*      */     } 
/* 1295 */     this._qNameStack[this._stackCount] = this._qualifiedName;
/* 1296 */     this._namespaceAIIsStartStack[this._stackCount] = this._currentNamespaceAIIsStart;
/* 1297 */     this._namespaceAIIsEndStack[this._stackCount] = this._currentNamespaceAIIsEnd;
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
/*      */   protected final void processAIIs() throws FastInfosetException, IOException {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   4: dup
/*      */     //   5: getfield _currentIteration : I
/*      */     //   8: iconst_1
/*      */     //   9: iadd
/*      */     //   10: dup_x1
/*      */     //   11: putfield _currentIteration : I
/*      */     //   14: ldc 2147483647
/*      */     //   16: if_icmpne -> 26
/*      */     //   19: aload_0
/*      */     //   20: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   23: invokevirtual clear : ()V
/*      */     //   26: aload_0
/*      */     //   27: iconst_1
/*      */     //   28: putfield _clearAttributes : Z
/*      */     //   31: iconst_0
/*      */     //   32: istore #4
/*      */     //   34: aload_0
/*      */     //   35: invokevirtual read : ()I
/*      */     //   38: istore_2
/*      */     //   39: iload_2
/*      */     //   40: invokestatic AII : (I)I
/*      */     //   43: tableswitch default -> 208, 0 -> 80, 1 -> 93, 2 -> 124, 3 -> 164, 4 -> 202, 5 -> 197
/*      */     //   80: aload_0
/*      */     //   81: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   84: getfield _array : [Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   87: iload_2
/*      */     //   88: aaload
/*      */     //   89: astore_1
/*      */     //   90: goto -> 224
/*      */     //   93: iload_2
/*      */     //   94: bipush #31
/*      */     //   96: iand
/*      */     //   97: bipush #8
/*      */     //   99: ishl
/*      */     //   100: aload_0
/*      */     //   101: invokevirtual read : ()I
/*      */     //   104: ior
/*      */     //   105: bipush #64
/*      */     //   107: iadd
/*      */     //   108: istore #5
/*      */     //   110: aload_0
/*      */     //   111: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   114: getfield _array : [Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   117: iload #5
/*      */     //   119: aaload
/*      */     //   120: astore_1
/*      */     //   121: goto -> 224
/*      */     //   124: iload_2
/*      */     //   125: bipush #15
/*      */     //   127: iand
/*      */     //   128: bipush #16
/*      */     //   130: ishl
/*      */     //   131: aload_0
/*      */     //   132: invokevirtual read : ()I
/*      */     //   135: bipush #8
/*      */     //   137: ishl
/*      */     //   138: ior
/*      */     //   139: aload_0
/*      */     //   140: invokevirtual read : ()I
/*      */     //   143: ior
/*      */     //   144: sipush #8256
/*      */     //   147: iadd
/*      */     //   148: istore #5
/*      */     //   150: aload_0
/*      */     //   151: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   154: getfield _array : [Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   157: iload #5
/*      */     //   159: aaload
/*      */     //   160: astore_1
/*      */     //   161: goto -> 224
/*      */     //   164: aload_0
/*      */     //   165: iload_2
/*      */     //   166: iconst_3
/*      */     //   167: iand
/*      */     //   168: aload_0
/*      */     //   169: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   172: invokevirtual getNext : ()Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   175: invokevirtual processLiteralQualifiedName : (ILcom/sun/xml/fastinfoset/QualifiedName;)Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   178: astore_1
/*      */     //   179: aload_1
/*      */     //   180: sipush #256
/*      */     //   183: invokevirtual createAttributeValues : (I)V
/*      */     //   186: aload_0
/*      */     //   187: getfield _attributeNameTable : Lcom/sun/xml/fastinfoset/util/QualifiedNameArray;
/*      */     //   190: aload_1
/*      */     //   191: invokevirtual add : (Lcom/sun/xml/fastinfoset/QualifiedName;)V
/*      */     //   194: goto -> 224
/*      */     //   197: aload_0
/*      */     //   198: iconst_1
/*      */     //   199: putfield _internalState : I
/*      */     //   202: iconst_1
/*      */     //   203: istore #4
/*      */     //   205: goto -> 942
/*      */     //   208: new org/jvnet/fastinfoset/FastInfosetException
/*      */     //   211: dup
/*      */     //   212: invokestatic getInstance : ()Lcom/sun/xml/fastinfoset/CommonResourceBundle;
/*      */     //   215: ldc 'message.decodingAIIs'
/*      */     //   217: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   220: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   223: athrow
/*      */     //   224: aload_1
/*      */     //   225: getfield prefixIndex : I
/*      */     //   228: ifle -> 266
/*      */     //   231: aload_0
/*      */     //   232: getfield _prefixTable : Lcom/sun/xml/fastinfoset/util/PrefixArray;
/*      */     //   235: getfield _currentInScope : [I
/*      */     //   238: aload_1
/*      */     //   239: getfield prefixIndex : I
/*      */     //   242: iaload
/*      */     //   243: aload_1
/*      */     //   244: getfield namespaceNameIndex : I
/*      */     //   247: if_icmpeq -> 266
/*      */     //   250: new org/jvnet/fastinfoset/FastInfosetException
/*      */     //   253: dup
/*      */     //   254: invokestatic getInstance : ()Lcom/sun/xml/fastinfoset/CommonResourceBundle;
/*      */     //   257: ldc 'message.AIIqNameNotInScope'
/*      */     //   259: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   262: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   265: athrow
/*      */     //   266: aload_0
/*      */     //   267: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   270: aload_1
/*      */     //   271: getfield attributeHash : I
/*      */     //   274: aload_1
/*      */     //   275: getfield attributeId : I
/*      */     //   278: invokevirtual checkForDuplicateAttribute : (II)V
/*      */     //   281: aload_0
/*      */     //   282: invokevirtual read : ()I
/*      */     //   285: istore_2
/*      */     //   286: iload_2
/*      */     //   287: invokestatic NISTRING : (I)I
/*      */     //   290: tableswitch default -> 926, 0 -> 352, 1 -> 395, 2 -> 439, 3 -> 508, 4 -> 551, 5 -> 595, 6 -> 664, 7 -> 745, 8 -> 805, 9 -> 828, 10 -> 866, 11 -> 913
/*      */     //   352: aload_0
/*      */     //   353: iload_2
/*      */     //   354: bipush #7
/*      */     //   356: iand
/*      */     //   357: iconst_1
/*      */     //   358: iadd
/*      */     //   359: putfield _octetBufferLength : I
/*      */     //   362: aload_0
/*      */     //   363: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   366: astore_3
/*      */     //   367: iload_2
/*      */     //   368: bipush #64
/*      */     //   370: iand
/*      */     //   371: ifle -> 383
/*      */     //   374: aload_0
/*      */     //   375: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   378: aload_3
/*      */     //   379: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   382: pop
/*      */     //   383: aload_0
/*      */     //   384: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   387: aload_1
/*      */     //   388: aload_3
/*      */     //   389: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   392: goto -> 942
/*      */     //   395: aload_0
/*      */     //   396: aload_0
/*      */     //   397: invokevirtual read : ()I
/*      */     //   400: bipush #9
/*      */     //   402: iadd
/*      */     //   403: putfield _octetBufferLength : I
/*      */     //   406: aload_0
/*      */     //   407: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   410: astore_3
/*      */     //   411: iload_2
/*      */     //   412: bipush #64
/*      */     //   414: iand
/*      */     //   415: ifle -> 427
/*      */     //   418: aload_0
/*      */     //   419: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   422: aload_3
/*      */     //   423: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   426: pop
/*      */     //   427: aload_0
/*      */     //   428: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   431: aload_1
/*      */     //   432: aload_3
/*      */     //   433: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   436: goto -> 942
/*      */     //   439: aload_0
/*      */     //   440: aload_0
/*      */     //   441: invokevirtual read : ()I
/*      */     //   444: bipush #24
/*      */     //   446: ishl
/*      */     //   447: aload_0
/*      */     //   448: invokevirtual read : ()I
/*      */     //   451: bipush #16
/*      */     //   453: ishl
/*      */     //   454: ior
/*      */     //   455: aload_0
/*      */     //   456: invokevirtual read : ()I
/*      */     //   459: bipush #8
/*      */     //   461: ishl
/*      */     //   462: ior
/*      */     //   463: aload_0
/*      */     //   464: invokevirtual read : ()I
/*      */     //   467: ior
/*      */     //   468: sipush #265
/*      */     //   471: iadd
/*      */     //   472: putfield _octetBufferLength : I
/*      */     //   475: aload_0
/*      */     //   476: invokevirtual decodeUtf8StringAsString : ()Ljava/lang/String;
/*      */     //   479: astore_3
/*      */     //   480: iload_2
/*      */     //   481: bipush #64
/*      */     //   483: iand
/*      */     //   484: ifle -> 496
/*      */     //   487: aload_0
/*      */     //   488: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   491: aload_3
/*      */     //   492: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   495: pop
/*      */     //   496: aload_0
/*      */     //   497: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   500: aload_1
/*      */     //   501: aload_3
/*      */     //   502: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   505: goto -> 942
/*      */     //   508: aload_0
/*      */     //   509: iload_2
/*      */     //   510: bipush #7
/*      */     //   512: iand
/*      */     //   513: iconst_1
/*      */     //   514: iadd
/*      */     //   515: putfield _octetBufferLength : I
/*      */     //   518: aload_0
/*      */     //   519: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   522: astore_3
/*      */     //   523: iload_2
/*      */     //   524: bipush #64
/*      */     //   526: iand
/*      */     //   527: ifle -> 539
/*      */     //   530: aload_0
/*      */     //   531: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   534: aload_3
/*      */     //   535: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   538: pop
/*      */     //   539: aload_0
/*      */     //   540: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   543: aload_1
/*      */     //   544: aload_3
/*      */     //   545: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   548: goto -> 942
/*      */     //   551: aload_0
/*      */     //   552: aload_0
/*      */     //   553: invokevirtual read : ()I
/*      */     //   556: bipush #9
/*      */     //   558: iadd
/*      */     //   559: putfield _octetBufferLength : I
/*      */     //   562: aload_0
/*      */     //   563: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   566: astore_3
/*      */     //   567: iload_2
/*      */     //   568: bipush #64
/*      */     //   570: iand
/*      */     //   571: ifle -> 583
/*      */     //   574: aload_0
/*      */     //   575: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   578: aload_3
/*      */     //   579: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   582: pop
/*      */     //   583: aload_0
/*      */     //   584: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   587: aload_1
/*      */     //   588: aload_3
/*      */     //   589: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   592: goto -> 942
/*      */     //   595: aload_0
/*      */     //   596: aload_0
/*      */     //   597: invokevirtual read : ()I
/*      */     //   600: bipush #24
/*      */     //   602: ishl
/*      */     //   603: aload_0
/*      */     //   604: invokevirtual read : ()I
/*      */     //   607: bipush #16
/*      */     //   609: ishl
/*      */     //   610: ior
/*      */     //   611: aload_0
/*      */     //   612: invokevirtual read : ()I
/*      */     //   615: bipush #8
/*      */     //   617: ishl
/*      */     //   618: ior
/*      */     //   619: aload_0
/*      */     //   620: invokevirtual read : ()I
/*      */     //   623: ior
/*      */     //   624: sipush #265
/*      */     //   627: iadd
/*      */     //   628: putfield _octetBufferLength : I
/*      */     //   631: aload_0
/*      */     //   632: invokevirtual decodeUtf16StringAsString : ()Ljava/lang/String;
/*      */     //   635: astore_3
/*      */     //   636: iload_2
/*      */     //   637: bipush #64
/*      */     //   639: iand
/*      */     //   640: ifle -> 652
/*      */     //   643: aload_0
/*      */     //   644: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   647: aload_3
/*      */     //   648: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   651: pop
/*      */     //   652: aload_0
/*      */     //   653: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   656: aload_1
/*      */     //   657: aload_3
/*      */     //   658: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   661: goto -> 942
/*      */     //   664: iload_2
/*      */     //   665: bipush #64
/*      */     //   667: iand
/*      */     //   668: ifle -> 675
/*      */     //   671: iconst_1
/*      */     //   672: goto -> 676
/*      */     //   675: iconst_0
/*      */     //   676: istore #5
/*      */     //   678: aload_0
/*      */     //   679: iload_2
/*      */     //   680: bipush #15
/*      */     //   682: iand
/*      */     //   683: iconst_4
/*      */     //   684: ishl
/*      */     //   685: putfield _identifier : I
/*      */     //   688: aload_0
/*      */     //   689: invokevirtual read : ()I
/*      */     //   692: istore_2
/*      */     //   693: aload_0
/*      */     //   694: dup
/*      */     //   695: getfield _identifier : I
/*      */     //   698: iload_2
/*      */     //   699: sipush #240
/*      */     //   702: iand
/*      */     //   703: iconst_4
/*      */     //   704: ishr
/*      */     //   705: ior
/*      */     //   706: putfield _identifier : I
/*      */     //   709: aload_0
/*      */     //   710: iload_2
/*      */     //   711: invokevirtual decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit : (I)V
/*      */     //   714: aload_0
/*      */     //   715: invokevirtual decodeRestrictedAlphabetAsString : ()Ljava/lang/String;
/*      */     //   718: astore_3
/*      */     //   719: iload #5
/*      */     //   721: ifeq -> 733
/*      */     //   724: aload_0
/*      */     //   725: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   728: aload_3
/*      */     //   729: invokevirtual add : (Ljava/lang/String;)I
/*      */     //   732: pop
/*      */     //   733: aload_0
/*      */     //   734: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   737: aload_1
/*      */     //   738: aload_3
/*      */     //   739: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   742: goto -> 942
/*      */     //   745: iload_2
/*      */     //   746: bipush #64
/*      */     //   748: iand
/*      */     //   749: ifle -> 756
/*      */     //   752: iconst_1
/*      */     //   753: goto -> 757
/*      */     //   756: iconst_0
/*      */     //   757: istore #5
/*      */     //   759: aload_0
/*      */     //   760: iload_2
/*      */     //   761: bipush #15
/*      */     //   763: iand
/*      */     //   764: iconst_4
/*      */     //   765: ishl
/*      */     //   766: putfield _identifier : I
/*      */     //   769: aload_0
/*      */     //   770: invokevirtual read : ()I
/*      */     //   773: istore_2
/*      */     //   774: aload_0
/*      */     //   775: dup
/*      */     //   776: getfield _identifier : I
/*      */     //   779: iload_2
/*      */     //   780: sipush #240
/*      */     //   783: iand
/*      */     //   784: iconst_4
/*      */     //   785: ishr
/*      */     //   786: ior
/*      */     //   787: putfield _identifier : I
/*      */     //   790: aload_0
/*      */     //   791: iload_2
/*      */     //   792: invokevirtual decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit : (I)V
/*      */     //   795: aload_0
/*      */     //   796: aload_1
/*      */     //   797: iload #5
/*      */     //   799: invokevirtual processAIIEncodingAlgorithm : (Lcom/sun/xml/fastinfoset/QualifiedName;Z)V
/*      */     //   802: goto -> 942
/*      */     //   805: aload_0
/*      */     //   806: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   809: aload_1
/*      */     //   810: aload_0
/*      */     //   811: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   814: getfield _array : [Ljava/lang/String;
/*      */     //   817: iload_2
/*      */     //   818: bipush #63
/*      */     //   820: iand
/*      */     //   821: aaload
/*      */     //   822: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   825: goto -> 942
/*      */     //   828: iload_2
/*      */     //   829: bipush #31
/*      */     //   831: iand
/*      */     //   832: bipush #8
/*      */     //   834: ishl
/*      */     //   835: aload_0
/*      */     //   836: invokevirtual read : ()I
/*      */     //   839: ior
/*      */     //   840: bipush #64
/*      */     //   842: iadd
/*      */     //   843: istore #5
/*      */     //   845: aload_0
/*      */     //   846: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   849: aload_1
/*      */     //   850: aload_0
/*      */     //   851: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   854: getfield _array : [Ljava/lang/String;
/*      */     //   857: iload #5
/*      */     //   859: aaload
/*      */     //   860: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   863: goto -> 942
/*      */     //   866: iload_2
/*      */     //   867: bipush #15
/*      */     //   869: iand
/*      */     //   870: bipush #16
/*      */     //   872: ishl
/*      */     //   873: aload_0
/*      */     //   874: invokevirtual read : ()I
/*      */     //   877: bipush #8
/*      */     //   879: ishl
/*      */     //   880: ior
/*      */     //   881: aload_0
/*      */     //   882: invokevirtual read : ()I
/*      */     //   885: ior
/*      */     //   886: sipush #8256
/*      */     //   889: iadd
/*      */     //   890: istore #5
/*      */     //   892: aload_0
/*      */     //   893: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   896: aload_1
/*      */     //   897: aload_0
/*      */     //   898: getfield _attributeValueTable : Lcom/sun/xml/fastinfoset/util/StringArray;
/*      */     //   901: getfield _array : [Ljava/lang/String;
/*      */     //   904: iload #5
/*      */     //   906: aaload
/*      */     //   907: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   910: goto -> 942
/*      */     //   913: aload_0
/*      */     //   914: getfield _attributes : Lcom/sun/xml/fastinfoset/sax/AttributesHolder;
/*      */     //   917: aload_1
/*      */     //   918: ldc ''
/*      */     //   920: invokevirtual addAttribute : (Lcom/sun/xml/fastinfoset/QualifiedName;Ljava/lang/String;)V
/*      */     //   923: goto -> 942
/*      */     //   926: new org/jvnet/fastinfoset/FastInfosetException
/*      */     //   929: dup
/*      */     //   930: invokestatic getInstance : ()Lcom/sun/xml/fastinfoset/CommonResourceBundle;
/*      */     //   933: ldc 'message.decodingAIIValue'
/*      */     //   935: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
/*      */     //   938: invokespecial <init> : (Ljava/lang/String;)V
/*      */     //   941: athrow
/*      */     //   942: iload #4
/*      */     //   944: ifeq -> 34
/*      */     //   947: aload_0
/*      */     //   948: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   951: aload_0
/*      */     //   952: getfield _duplicateAttributeVerifier : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier;
/*      */     //   955: getfield _poolHead : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier$Entry;
/*      */     //   958: putfield _poolCurrent : Lcom/sun/xml/fastinfoset/util/DuplicateAttributeVerifier$Entry;
/*      */     //   961: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #1305	-> 0
/*      */     //   #1306	-> 19
/*      */     //   #1309	-> 26
/*      */     //   #1310	-> 31
/*      */     //   #1313	-> 34
/*      */     //   #1314	-> 39
/*      */     //   #1316	-> 80
/*      */     //   #1317	-> 90
/*      */     //   #1320	-> 93
/*      */     //   #1322	-> 110
/*      */     //   #1323	-> 121
/*      */     //   #1327	-> 124
/*      */     //   #1329	-> 150
/*      */     //   #1330	-> 161
/*      */     //   #1333	-> 164
/*      */     //   #1336	-> 179
/*      */     //   #1337	-> 186
/*      */     //   #1338	-> 194
/*      */     //   #1340	-> 197
/*      */     //   #1342	-> 202
/*      */     //   #1344	-> 205
/*      */     //   #1346	-> 208
/*      */     //   #1351	-> 224
/*      */     //   #1352	-> 250
/*      */     //   #1355	-> 266
/*      */     //   #1357	-> 281
/*      */     //   #1358	-> 286
/*      */     //   #1360	-> 352
/*      */     //   #1361	-> 362
/*      */     //   #1362	-> 367
/*      */     //   #1363	-> 374
/*      */     //   #1366	-> 383
/*      */     //   #1367	-> 392
/*      */     //   #1369	-> 395
/*      */     //   #1370	-> 406
/*      */     //   #1371	-> 411
/*      */     //   #1372	-> 418
/*      */     //   #1375	-> 427
/*      */     //   #1376	-> 436
/*      */     //   #1378	-> 439
/*      */     //   #1383	-> 475
/*      */     //   #1384	-> 480
/*      */     //   #1385	-> 487
/*      */     //   #1388	-> 496
/*      */     //   #1389	-> 505
/*      */     //   #1391	-> 508
/*      */     //   #1392	-> 518
/*      */     //   #1393	-> 523
/*      */     //   #1394	-> 530
/*      */     //   #1397	-> 539
/*      */     //   #1398	-> 548
/*      */     //   #1400	-> 551
/*      */     //   #1401	-> 562
/*      */     //   #1402	-> 567
/*      */     //   #1403	-> 574
/*      */     //   #1406	-> 583
/*      */     //   #1407	-> 592
/*      */     //   #1409	-> 595
/*      */     //   #1414	-> 631
/*      */     //   #1415	-> 636
/*      */     //   #1416	-> 643
/*      */     //   #1419	-> 652
/*      */     //   #1420	-> 661
/*      */     //   #1423	-> 664
/*      */     //   #1425	-> 678
/*      */     //   #1426	-> 688
/*      */     //   #1427	-> 693
/*      */     //   #1429	-> 709
/*      */     //   #1431	-> 714
/*      */     //   #1432	-> 719
/*      */     //   #1433	-> 724
/*      */     //   #1436	-> 733
/*      */     //   #1437	-> 742
/*      */     //   #1441	-> 745
/*      */     //   #1443	-> 759
/*      */     //   #1444	-> 769
/*      */     //   #1445	-> 774
/*      */     //   #1447	-> 790
/*      */     //   #1448	-> 795
/*      */     //   #1449	-> 802
/*      */     //   #1452	-> 805
/*      */     //   #1454	-> 825
/*      */     //   #1457	-> 828
/*      */     //   #1460	-> 845
/*      */     //   #1462	-> 863
/*      */     //   #1466	-> 866
/*      */     //   #1469	-> 892
/*      */     //   #1471	-> 910
/*      */     //   #1474	-> 913
/*      */     //   #1475	-> 923
/*      */     //   #1477	-> 926
/*      */     //   #1480	-> 942
/*      */     //   #1483	-> 947
/*      */     //   #1484	-> 961
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   110	14	5	i	I
/*      */     //   150	14	5	i	I
/*      */     //   678	67	5	addToTable	Z
/*      */     //   759	46	5	addToTable	Z
/*      */     //   845	21	5	index	I
/*      */     //   892	21	5	index	I
/*      */     //   90	852	1	name	Lcom/sun/xml/fastinfoset/QualifiedName;
/*      */     //   0	962	0	this	Lcom/sun/xml/fastinfoset/stax/StAXDocumentParser;
/*      */     //   39	923	2	b	I
/*      */     //   367	595	3	value	Ljava/lang/String;
/*      */     //   34	928	4	terminate	Z
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
/*      */   protected final QualifiedName processEIIIndexMedium(int b) throws FastInfosetException, IOException {
/* 1487 */     int i = ((b & 0x7) << 8 | read()) + 32;
/*      */     
/* 1489 */     return this._elementNameTable._array[i];
/*      */   }
/*      */   
/*      */   protected final QualifiedName processEIIIndexLarge(int b) throws FastInfosetException, IOException {
/*      */     int i;
/* 1494 */     if ((b & 0x30) == 32) {
/*      */       
/* 1496 */       i = ((b & 0x7) << 16 | read() << 8 | read()) + 2080;
/*      */     }
/*      */     else {
/*      */       
/* 1500 */       i = ((read() & 0xF) << 16 | read() << 8 | read()) + 526368;
/*      */     } 
/*      */     
/* 1503 */     return this._elementNameTable._array[i];
/*      */   }
/*      */ 
/*      */   
/*      */   protected final QualifiedName processLiteralQualifiedName(int state, QualifiedName q) throws FastInfosetException, IOException {
/* 1508 */     if (q == null) q = new QualifiedName();
/*      */     
/* 1510 */     switch (state) {
/*      */       
/*      */       case 0:
/* 1513 */         return q.set("", "", decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), "", 0, -1, -1, this._identifier);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 1524 */         return q.set("", decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(false), decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), "", 0, -1, this._namespaceNameIndex, this._identifier);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 1535 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.qNameMissingNamespaceName"));
/*      */       
/*      */       case 3:
/* 1538 */         return q.set(decodeIdentifyingNonEmptyStringIndexOnFirstBitAsPrefix(true), decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(true), decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), "", 0, this._prefixIndex, this._namespaceNameIndex, this._identifier);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1548 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingEII"));
/*      */   }
/*      */   
/*      */   protected final void processCommentII() throws FastInfosetException, IOException {
/*      */     CharArray ca;
/* 1553 */     this._eventType = 5;
/*      */     
/* 1555 */     switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */       case 0:
/* 1557 */         if (this._addToTable) {
/* 1558 */           this._v.otherString.add(new CharArray(this._charBuffer, 0, this._charBufferLength, true));
/*      */         }
/*      */         
/* 1561 */         this._characters = this._charBuffer;
/* 1562 */         this._charactersOffset = 0;
/*      */         break;
/*      */       case 2:
/* 1565 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.commentIIAlgorithmNotSupported"));
/*      */       case 1:
/* 1567 */         ca = this._v.otherString.get(this._integer);
/*      */         
/* 1569 */         this._characters = ca.ch;
/* 1570 */         this._charactersOffset = ca.start;
/* 1571 */         this._charBufferLength = ca.length;
/*      */         break;
/*      */       case 3:
/* 1574 */         this._characters = this._charBuffer;
/* 1575 */         this._charactersOffset = 0;
/* 1576 */         this._charBufferLength = 0;
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processProcessingII() throws FastInfosetException, IOException {
/* 1582 */     this._eventType = 3;
/*      */     
/* 1584 */     this._piTarget = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */     
/* 1586 */     switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */       case 0:
/* 1588 */         this._piData = new String(this._charBuffer, 0, this._charBufferLength);
/* 1589 */         if (this._addToTable) {
/* 1590 */           this._v.otherString.add((CharArray)new CharArrayString(this._piData));
/*      */         }
/*      */         break;
/*      */       case 2:
/* 1594 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.processingIIWithEncodingAlgorithm"));
/*      */       case 1:
/* 1596 */         this._piData = this._v.otherString.get(this._integer).toString();
/*      */         break;
/*      */       case 3:
/* 1599 */         this._piData = "";
/*      */         break;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void processUnexpandedEntityReference(int b) throws FastInfosetException, IOException {
/* 1605 */     this._eventType = 9;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1611 */     String entity_reference_name = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */     
/* 1613 */     String system_identifier = ((b & 0x2) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */     
/* 1615 */     String public_identifier = ((b & 0x1) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void processCIIEncodingAlgorithm(boolean addToTable) throws FastInfosetException, IOException {
/* 1620 */     this._algorithmData = this._octetBuffer;
/* 1621 */     this._algorithmDataOffset = this._octetBufferStart;
/* 1622 */     this._algorithmDataLength = this._octetBufferLength;
/* 1623 */     this._isAlgorithmDataCloned = false;
/*      */     
/* 1625 */     if (this._algorithmId >= 32) {
/* 1626 */       this._algorithmURI = this._v.encodingAlgorithm.get(this._algorithmId - 32);
/* 1627 */       if (this._algorithmURI == null) {
/* 1628 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.URINotPresent", new Object[] { Integer.valueOf(this._identifier) }));
/*      */       }
/* 1630 */     } else if (this._algorithmId > 9) {
/*      */ 
/*      */ 
/*      */       
/* 1634 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.identifiers10to31Reserved"));
/*      */     } 
/*      */     
/* 1637 */     if (addToTable) {
/* 1638 */       convertEncodingAlgorithmDataToCharacters();
/* 1639 */       this._characterContentChunkTable.add(this._characters, this._characters.length);
/*      */     } 
/*      */   } protected final void processAIIEncodingAlgorithm(QualifiedName name, boolean addToTable) throws FastInfosetException, IOException {
/*      */     BuiltInEncodingAlgorithm builtInEncodingAlgorithm;
/*      */     Object algorithmData;
/* 1644 */     EncodingAlgorithm ea = null;
/* 1645 */     String URI = null;
/* 1646 */     if (this._identifier >= 32) {
/* 1647 */       URI = this._v.encodingAlgorithm.get(this._identifier - 32);
/* 1648 */       if (URI == null)
/* 1649 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.URINotPresent", new Object[] { Integer.valueOf(this._identifier) })); 
/* 1650 */       if (this._registeredEncodingAlgorithms != null)
/* 1651 */         ea = (EncodingAlgorithm)this._registeredEncodingAlgorithms.get(URI); 
/*      */     } else {
/* 1653 */       if (this._identifier >= 9) {
/* 1654 */         if (this._identifier == 9) {
/* 1655 */           throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.CDATAAlgorithmNotSupported"));
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1661 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.identifiers10to31Reserved"));
/*      */       } 
/* 1663 */       builtInEncodingAlgorithm = BuiltInEncodingAlgorithmFactory.getAlgorithm(this._identifier);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1668 */     if (builtInEncodingAlgorithm != null) {
/* 1669 */       algorithmData = builtInEncodingAlgorithm.decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/*      */     } else {
/*      */       
/* 1672 */       byte[] data = new byte[this._octetBufferLength];
/* 1673 */       System.arraycopy(this._octetBuffer, this._octetBufferStart, data, 0, this._octetBufferLength);
/*      */       
/* 1675 */       algorithmData = data;
/*      */     } 
/*      */     
/* 1678 */     this._attributes.addAttributeWithAlgorithmData(name, URI, this._identifier, algorithmData);
/*      */     
/* 1680 */     if (addToTable) {
/* 1681 */       this._attributeValueTable.add(this._attributes.getValue(this._attributes.getIndex(name.qName)));
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void convertEncodingAlgorithmDataToCharacters() throws FastInfosetException, IOException {
/* 1686 */     StringBuffer buffer = new StringBuffer();
/* 1687 */     if (this._algorithmId == 1)
/* 1688 */     { convertBase64AlorithmDataToCharacters(buffer); }
/* 1689 */     else if (this._algorithmId < 9)
/* 1690 */     { Object array = BuiltInEncodingAlgorithmFactory.getAlgorithm(this._algorithmId).decodeFromBytes(this._algorithmData, this._algorithmDataOffset, this._algorithmDataLength);
/*      */       
/* 1692 */       BuiltInEncodingAlgorithmFactory.getAlgorithm(this._algorithmId).convertToCharacters(array, buffer); }
/* 1693 */     else { if (this._algorithmId == 9) {
/* 1694 */         this._octetBufferOffset -= this._octetBufferLength;
/* 1695 */         decodeUtf8StringIntoCharBuffer();
/*      */         
/* 1697 */         this._characters = this._charBuffer;
/* 1698 */         this._charactersOffset = 0; return;
/*      */       } 
/* 1700 */       if (this._algorithmId >= 32) {
/* 1701 */         EncodingAlgorithm ea = (EncodingAlgorithm)this._registeredEncodingAlgorithms.get(this._algorithmURI);
/* 1702 */         if (ea != null) {
/* 1703 */           Object data = ea.decodeFromBytes(this._octetBuffer, this._octetBufferStart, this._octetBufferLength);
/* 1704 */           ea.convertToCharacters(data, buffer);
/*      */         } else {
/* 1706 */           throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.algorithmDataCannotBeReported"));
/*      */         } 
/*      */       }  }
/*      */ 
/*      */     
/* 1711 */     this._characters = new char[buffer.length()];
/* 1712 */     buffer.getChars(0, buffer.length(), this._characters, 0);
/* 1713 */     this._charactersOffset = 0;
/* 1714 */     this._charBufferLength = this._characters.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public StAXDocumentParser() {
/* 1719 */     this.base64TaleBytes = new byte[3];
/*      */     reset();
/*      */     this._manager = new StAXManager(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void convertBase64AlorithmDataToCharacters(StringBuffer buffer) throws EncodingAlgorithmException, IOException {
/* 1728 */     int afterTaleOffset = 0;
/*      */     
/* 1730 */     if (this.base64TaleLength > 0) {
/*      */       
/* 1732 */       int bytesToCopy = Math.min(3 - this.base64TaleLength, this._algorithmDataLength);
/* 1733 */       System.arraycopy(this._algorithmData, this._algorithmDataOffset, this.base64TaleBytes, this.base64TaleLength, bytesToCopy);
/* 1734 */       if (this.base64TaleLength + bytesToCopy == 3)
/* 1735 */       { base64DecodeWithCloning(buffer, this.base64TaleBytes, 0, 3); }
/* 1736 */       else { if (!isBase64Follows()) {
/*      */           
/* 1738 */           base64DecodeWithCloning(buffer, this.base64TaleBytes, 0, this.base64TaleLength + bytesToCopy);
/*      */           
/*      */           return;
/*      */         } 
/* 1742 */         this.base64TaleLength += bytesToCopy;
/*      */         
/*      */         return; }
/*      */       
/* 1746 */       afterTaleOffset = bytesToCopy;
/* 1747 */       this.base64TaleLength = 0;
/*      */     } 
/*      */     
/* 1750 */     int taleBytesRemaining = isBase64Follows() ? ((this._algorithmDataLength - afterTaleOffset) % 3) : 0;
/*      */     
/* 1752 */     if (this._isAlgorithmDataCloned) {
/* 1753 */       base64DecodeWithoutCloning(buffer, this._algorithmData, this._algorithmDataOffset + afterTaleOffset, this._algorithmDataLength - afterTaleOffset - taleBytesRemaining);
/*      */     } else {
/*      */       
/* 1756 */       base64DecodeWithCloning(buffer, this._algorithmData, this._algorithmDataOffset + afterTaleOffset, this._algorithmDataLength - afterTaleOffset - taleBytesRemaining);
/*      */     } 
/*      */ 
/*      */     
/* 1760 */     if (taleBytesRemaining > 0) {
/* 1761 */       System.arraycopy(this._algorithmData, this._algorithmDataOffset + this._algorithmDataLength - taleBytesRemaining, this.base64TaleBytes, 0, taleBytesRemaining);
/*      */       
/* 1763 */       this.base64TaleLength = taleBytesRemaining;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void base64DecodeWithCloning(StringBuffer dstBuffer, byte[] data, int offset, int length) throws EncodingAlgorithmException {
/* 1772 */     Object array = BuiltInEncodingAlgorithmFactory.base64EncodingAlgorithm.decodeFromBytes(data, offset, length);
/*      */     
/* 1774 */     BuiltInEncodingAlgorithmFactory.base64EncodingAlgorithm.convertToCharacters(array, dstBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void base64DecodeWithoutCloning(StringBuffer dstBuffer, byte[] data, int offset, int length) throws EncodingAlgorithmException {
/* 1782 */     BuiltInEncodingAlgorithmFactory.base64EncodingAlgorithm.convertToCharacters(data, offset, length, dstBuffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBase64Follows() throws IOException {
/* 1791 */     int algorithmId, b2, b = peek(this);
/* 1792 */     switch (DecoderStateTables.EII(b)) {
/*      */       case 13:
/* 1794 */         algorithmId = (b & 0x2) << 6;
/* 1795 */         b2 = peek2(this);
/* 1796 */         algorithmId |= (b2 & 0xFC) >> 2;
/*      */         
/* 1798 */         return (algorithmId == 1);
/*      */     } 
/* 1800 */     return false;
/*      */   }
/*      */   
/*      */   protected class NamespaceContextImpl
/*      */     implements NamespaceContext {
/*      */     public final String getNamespaceURI(String prefix) {
/* 1806 */       return StAXDocumentParser.this._prefixTable.getNamespaceFromPrefix(prefix);
/*      */     }
/*      */     
/*      */     public final String getPrefix(String namespaceURI) {
/* 1810 */       return StAXDocumentParser.this._prefixTable.getPrefixFromNamespace(namespaceURI);
/*      */     }
/*      */     
/*      */     public final Iterator getPrefixes(String namespaceURI) {
/* 1814 */       return StAXDocumentParser.this._prefixTable.getPrefixesFromNamespace(namespaceURI);
/*      */     }
/*      */   }
/*      */   
/*      */   public final String getNamespaceDecl(String prefix) {
/* 1819 */     return this._prefixTable.getNamespaceFromPrefix(prefix);
/*      */   }
/*      */   
/*      */   public final String getURI(String prefix) {
/* 1823 */     return getNamespaceDecl(prefix);
/*      */   }
/*      */   
/*      */   public final Iterator getPrefixes() {
/* 1827 */     return this._prefixTable.getPrefixes();
/*      */   }
/*      */   
/*      */   public final AttributesHolder getAttributesHolder() {
/* 1831 */     return this._attributes;
/*      */   }
/*      */   
/*      */   public final void setManager(StAXManager manager) {
/* 1835 */     this._manager = manager;
/*      */   }
/*      */   
/*      */   static final String getEventTypeString(int eventType) {
/* 1839 */     switch (eventType) {
/*      */       case 1:
/* 1841 */         return "START_ELEMENT";
/*      */       case 2:
/* 1843 */         return "END_ELEMENT";
/*      */       case 3:
/* 1845 */         return "PROCESSING_INSTRUCTION";
/*      */       case 4:
/* 1847 */         return "CHARACTERS";
/*      */       case 5:
/* 1849 */         return "COMMENT";
/*      */       case 7:
/* 1851 */         return "START_DOCUMENT";
/*      */       case 8:
/* 1853 */         return "END_DOCUMENT";
/*      */       case 9:
/* 1855 */         return "ENTITY_REFERENCE";
/*      */       case 10:
/* 1857 */         return "ATTRIBUTE";
/*      */       case 11:
/* 1859 */         return "DTD";
/*      */       case 12:
/* 1861 */         return "CDATA";
/*      */     } 
/* 1863 */     return "UNKNOWN_EVENT_TYPE";
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\stax\StAXDocumentParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */