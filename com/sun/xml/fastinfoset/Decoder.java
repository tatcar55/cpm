/*      */ package com.sun.xml.fastinfoset;
/*      */ 
/*      */ import com.sun.xml.fastinfoset.alphabet.BuiltInRestrictedAlphabets;
/*      */ import com.sun.xml.fastinfoset.org.apache.xerces.util.XMLChar;
/*      */ import com.sun.xml.fastinfoset.util.CharArray;
/*      */ import com.sun.xml.fastinfoset.util.CharArrayArray;
/*      */ import com.sun.xml.fastinfoset.util.CharArrayString;
/*      */ import com.sun.xml.fastinfoset.util.ContiguousCharArrayArray;
/*      */ import com.sun.xml.fastinfoset.util.DuplicateAttributeVerifier;
/*      */ import com.sun.xml.fastinfoset.util.PrefixArray;
/*      */ import com.sun.xml.fastinfoset.util.QualifiedNameArray;
/*      */ import com.sun.xml.fastinfoset.util.StringArray;
/*      */ import com.sun.xml.fastinfoset.vocab.ParserVocabulary;
/*      */ import java.io.EOFException;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.jvnet.fastinfoset.ExternalVocabulary;
/*      */ import org.jvnet.fastinfoset.FastInfosetException;
/*      */ import org.jvnet.fastinfoset.FastInfosetParser;
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
/*      */ public abstract class Decoder
/*      */   implements FastInfosetParser
/*      */ {
/*   66 */   private static final char[] XML_NAMESPACE_NAME_CHARS = "http://www.w3.org/XML/1998/namespace".toCharArray();
/*      */ 
/*      */   
/*   69 */   private static final char[] XMLNS_NAMESPACE_PREFIX_CHARS = "xmlns".toCharArray();
/*      */ 
/*      */   
/*   72 */   private static final char[] XMLNS_NAMESPACE_NAME_CHARS = "http://www.w3.org/2000/xmlns/".toCharArray();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String STRING_INTERNING_SYSTEM_PROPERTY = "com.sun.xml.fastinfoset.parser.string-interning";
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String BUFFER_SIZE_SYSTEM_PROPERTY = "com.sun.xml.fastinfoset.parser.buffer-size";
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean _stringInterningSystemDefault = false;
/*      */ 
/*      */ 
/*      */   
/*   89 */   private static int _bufferSizeSystemDefault = 1024;
/*      */   
/*      */   static {
/*   92 */     String p = System.getProperty("com.sun.xml.fastinfoset.parser.string-interning", Boolean.toString(_stringInterningSystemDefault));
/*      */     
/*   94 */     _stringInterningSystemDefault = Boolean.valueOf(p).booleanValue();
/*      */     
/*   96 */     p = System.getProperty("com.sun.xml.fastinfoset.parser.buffer-size", Integer.toString(_bufferSizeSystemDefault));
/*      */     
/*      */     try {
/*   99 */       int i = Integer.valueOf(p).intValue();
/*  100 */       if (i > 0) {
/*  101 */         _bufferSizeSystemDefault = i;
/*      */       }
/*  103 */     } catch (NumberFormatException e) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  110 */   private boolean _stringInterning = _stringInterningSystemDefault;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private InputStream _s;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map _externalVocabularies;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _parseFragments;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _needForceStreamClose;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _vIsInternal;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List _notations;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List _unparsedEntities;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  152 */   protected Map _registeredEncodingAlgorithms = new HashMap<Object, Object>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ParserVocabulary _v;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected PrefixArray _prefixTable;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected QualifiedNameArray _elementNameTable;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected QualifiedNameArray _attributeNameTable;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected ContiguousCharArrayArray _characterContentChunkTable;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected StringArray _attributeValueTable;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _b;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _terminate;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _doubleTerminate;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _addToTable;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _integer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _identifier;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  218 */   protected int _bufferSize = _bufferSizeSystemDefault;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  223 */   protected byte[] _octetBuffer = new byte[_bufferSizeSystemDefault];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _octetBufferStart;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _octetBufferOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _octetBufferEnd;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _octetBufferLength;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  249 */   protected char[] _charBuffer = new char[512];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _charBufferLength;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  259 */   protected DuplicateAttributeVerifier _duplicateAttributeVerifier = new DuplicateAttributeVerifier(); protected static final int NISTRING_STRING = 0;
/*      */   protected static final int NISTRING_INDEX = 1;
/*      */   protected static final int NISTRING_ENCODING_ALGORITHM = 2;
/*      */   protected static final int NISTRING_EMPTY_STRING = 3;
/*      */   
/*      */   protected Decoder() {
/*  265 */     this._v = new ParserVocabulary();
/*  266 */     this._prefixTable = this._v.prefix;
/*  267 */     this._elementNameTable = this._v.elementName;
/*  268 */     this._attributeNameTable = this._v.attributeName;
/*  269 */     this._characterContentChunkTable = this._v.characterContentChunk;
/*  270 */     this._attributeValueTable = this._v.attributeValue;
/*  271 */     this._vIsInternal = true;
/*      */   }
/*      */   
/*      */   protected int _prefixIndex;
/*      */   protected int _namespaceNameIndex;
/*      */   private int _bitsLeftInOctet;
/*      */   private char _utf8_highSurrogate;
/*      */   private char _utf8_lowSurrogate;
/*      */   
/*      */   public void setStringInterning(boolean stringInterning) {
/*  281 */     this._stringInterning = stringInterning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getStringInterning() {
/*  288 */     return this._stringInterning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBufferSize(int bufferSize) {
/*  295 */     if (this._bufferSize > this._octetBuffer.length) {
/*  296 */       this._bufferSize = bufferSize;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBufferSize() {
/*  304 */     return this._bufferSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRegisteredEncodingAlgorithms(Map algorithms) {
/*  311 */     this._registeredEncodingAlgorithms = algorithms;
/*  312 */     if (this._registeredEncodingAlgorithms == null) {
/*  313 */       this._registeredEncodingAlgorithms = new HashMap<Object, Object>();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map getRegisteredEncodingAlgorithms() {
/*  321 */     return this._registeredEncodingAlgorithms;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExternalVocabularies(Map referencedVocabualries) {
/*  328 */     if (referencedVocabualries != null) {
/*      */       
/*  330 */       this._externalVocabularies = new HashMap<Object, Object>();
/*  331 */       this._externalVocabularies.putAll(referencedVocabualries);
/*      */     } else {
/*  333 */       this._externalVocabularies = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map getExternalVocabularies() {
/*  341 */     return this._externalVocabularies;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setParseFragments(boolean parseFragments) {
/*  348 */     this._parseFragments = parseFragments;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getParseFragments() {
/*  355 */     return this._parseFragments;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setForceStreamClose(boolean needForceStreamClose) {
/*  362 */     this._needForceStreamClose = needForceStreamClose;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getForceStreamClose() {
/*  369 */     return this._needForceStreamClose;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/*  378 */     this._terminate = this._doubleTerminate = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVocabulary(ParserVocabulary v) {
/*  387 */     this._v = v;
/*  388 */     this._prefixTable = this._v.prefix;
/*  389 */     this._elementNameTable = this._v.elementName;
/*  390 */     this._attributeNameTable = this._v.attributeName;
/*  391 */     this._characterContentChunkTable = this._v.characterContentChunk;
/*  392 */     this._attributeValueTable = this._v.attributeValue;
/*  393 */     this._vIsInternal = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInputStream(InputStream s) {
/*  402 */     this._s = s;
/*  403 */     this._octetBufferOffset = 0;
/*  404 */     this._octetBufferEnd = 0;
/*  405 */     if (this._vIsInternal == true) {
/*  406 */       this._v.clear();
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void decodeDII() throws FastInfosetException, IOException {
/*  411 */     int b = read();
/*  412 */     if (b == 32) {
/*  413 */       decodeInitialVocabulary();
/*  414 */     } else if (b != 0) {
/*  415 */       throw new IOException(CommonResourceBundle.getInstance().getString("message.optinalValues"));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void decodeAdditionalData() throws FastInfosetException, IOException {
/*  421 */     int noOfItems = decodeNumberOfItemsOfSequence();
/*      */     
/*  423 */     for (int i = 0; i < noOfItems; i++) {
/*  424 */       String URI = decodeNonEmptyOctetStringOnSecondBitAsUtf8String();
/*      */       
/*  426 */       decodeNonEmptyOctetStringLengthOnSecondBit();
/*  427 */       ensureOctetBufferSize();
/*  428 */       this._octetBufferStart = this._octetBufferOffset;
/*  429 */       this._octetBufferOffset += this._octetBufferLength;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void decodeInitialVocabulary() throws FastInfosetException, IOException {
/*  435 */     int b = read();
/*      */     
/*  437 */     int b2 = read();
/*      */ 
/*      */     
/*  440 */     if (b == 16 && b2 == 0) {
/*  441 */       decodeExternalVocabularyURI();
/*      */       
/*      */       return;
/*      */     } 
/*  445 */     if ((b & 0x10) > 0) {
/*  446 */       decodeExternalVocabularyURI();
/*      */     }
/*      */     
/*  449 */     if ((b & 0x8) > 0) {
/*  450 */       decodeTableItems(this._v.restrictedAlphabet);
/*      */     }
/*      */     
/*  453 */     if ((b & 0x4) > 0) {
/*  454 */       decodeTableItems(this._v.encodingAlgorithm);
/*      */     }
/*      */     
/*  457 */     if ((b & 0x2) > 0) {
/*  458 */       decodeTableItems(this._v.prefix);
/*      */     }
/*      */     
/*  461 */     if ((b & 0x1) > 0) {
/*  462 */       decodeTableItems(this._v.namespaceName);
/*      */     }
/*      */     
/*  465 */     if ((b2 & 0x80) > 0) {
/*  466 */       decodeTableItems(this._v.localName);
/*      */     }
/*      */     
/*  469 */     if ((b2 & 0x40) > 0) {
/*  470 */       decodeTableItems(this._v.otherNCName);
/*      */     }
/*      */     
/*  473 */     if ((b2 & 0x20) > 0) {
/*  474 */       decodeTableItems(this._v.otherURI);
/*      */     }
/*      */     
/*  477 */     if ((b2 & 0x10) > 0) {
/*  478 */       decodeTableItems(this._v.attributeValue);
/*      */     }
/*      */     
/*  481 */     if ((b2 & 0x8) > 0) {
/*  482 */       decodeTableItems(this._v.characterContentChunk);
/*      */     }
/*      */     
/*  485 */     if ((b2 & 0x4) > 0) {
/*  486 */       decodeTableItems(this._v.otherString);
/*      */     }
/*      */     
/*  489 */     if ((b2 & 0x2) > 0) {
/*  490 */       decodeTableItems(this._v.elementName, false);
/*      */     }
/*      */     
/*  493 */     if ((b2 & 0x1) > 0) {
/*  494 */       decodeTableItems(this._v.attributeName, true);
/*      */     }
/*      */   }
/*      */   
/*      */   private void decodeExternalVocabularyURI() throws FastInfosetException, IOException {
/*  499 */     if (this._externalVocabularies == null) {
/*  500 */       throw new IOException(CommonResourceBundle.getInstance().getString("message.noExternalVocabularies"));
/*      */     }
/*      */ 
/*      */     
/*  504 */     String externalVocabularyURI = decodeNonEmptyOctetStringOnSecondBitAsUtf8String();
/*      */ 
/*      */     
/*  507 */     Object o = this._externalVocabularies.get(externalVocabularyURI);
/*  508 */     if (o instanceof ParserVocabulary) {
/*  509 */       this._v.setReferencedVocabulary(externalVocabularyURI, (ParserVocabulary)o, false);
/*      */     }
/*  511 */     else if (o instanceof ExternalVocabulary) {
/*  512 */       ExternalVocabulary v = (ExternalVocabulary)o;
/*      */       
/*  514 */       ParserVocabulary pv = new ParserVocabulary(v.vocabulary);
/*      */       
/*  516 */       this._externalVocabularies.put(externalVocabularyURI, pv);
/*  517 */       this._v.setReferencedVocabulary(externalVocabularyURI, pv, false);
/*      */     } else {
/*      */       
/*  520 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.externalVocabularyNotRegistered", new Object[] { externalVocabularyURI }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void decodeTableItems(StringArray array) throws FastInfosetException, IOException {
/*  527 */     int noOfItems = decodeNumberOfItemsOfSequence();
/*      */     
/*  529 */     for (int i = 0; i < noOfItems; i++) {
/*  530 */       array.add(decodeNonEmptyOctetStringOnSecondBitAsUtf8String());
/*      */     }
/*      */   }
/*      */   
/*      */   private void decodeTableItems(PrefixArray array) throws FastInfosetException, IOException {
/*  535 */     int noOfItems = decodeNumberOfItemsOfSequence();
/*      */     
/*  537 */     for (int i = 0; i < noOfItems; i++) {
/*  538 */       array.add(decodeNonEmptyOctetStringOnSecondBitAsUtf8String());
/*      */     }
/*      */   }
/*      */   
/*      */   private void decodeTableItems(ContiguousCharArrayArray array) throws FastInfosetException, IOException {
/*  543 */     int noOfItems = decodeNumberOfItemsOfSequence();
/*      */     
/*  545 */     for (int i = 0; i < noOfItems; i++) {
/*  546 */       switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */         case 0:
/*  548 */           array.add(this._charBuffer, this._charBufferLength);
/*      */           break;
/*      */         default:
/*  551 */           throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.illegalState"));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void decodeTableItems(CharArrayArray array) throws FastInfosetException, IOException {
/*  557 */     int noOfItems = decodeNumberOfItemsOfSequence();
/*      */     
/*  559 */     for (int i = 0; i < noOfItems; i++) {
/*  560 */       switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */         case 0:
/*  562 */           array.add(new CharArray(this._charBuffer, 0, this._charBufferLength, true));
/*      */           break;
/*      */         default:
/*  565 */           throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.illegalState"));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void decodeTableItems(QualifiedNameArray array, boolean isAttribute) throws FastInfosetException, IOException {
/*  571 */     int noOfItems = decodeNumberOfItemsOfSequence();
/*      */     
/*  573 */     for (int i = 0; i < noOfItems; i++) {
/*  574 */       int b = read();
/*      */       
/*  576 */       String prefix = "";
/*  577 */       int prefixIndex = -1;
/*  578 */       if ((b & 0x2) > 0) {
/*  579 */         prefixIndex = decodeIntegerIndexOnSecondBit();
/*  580 */         prefix = this._v.prefix.get(prefixIndex);
/*      */       } 
/*      */       
/*  583 */       String namespaceName = "";
/*  584 */       int namespaceNameIndex = -1;
/*  585 */       if ((b & 0x1) > 0) {
/*  586 */         namespaceNameIndex = decodeIntegerIndexOnSecondBit();
/*  587 */         namespaceName = this._v.namespaceName.get(namespaceNameIndex);
/*      */       } 
/*      */       
/*  590 */       if (namespaceName == "" && prefix != "") {
/*  591 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.missingNamespace"));
/*      */       }
/*      */       
/*  594 */       int localNameIndex = decodeIntegerIndexOnSecondBit();
/*  595 */       String localName = this._v.localName.get(localNameIndex);
/*      */       
/*  597 */       QualifiedName qualifiedName = new QualifiedName(prefix, namespaceName, localName, prefixIndex, namespaceNameIndex, localNameIndex, this._charBuffer);
/*      */ 
/*      */       
/*  600 */       if (isAttribute) {
/*  601 */         qualifiedName.createAttributeValues(256);
/*      */       }
/*  603 */       array.add(qualifiedName);
/*      */     } 
/*      */   }
/*      */   
/*      */   private int decodeNumberOfItemsOfSequence() throws IOException {
/*  608 */     int b = read();
/*  609 */     if (b < 128) {
/*  610 */       return b + 1;
/*      */     }
/*  612 */     return ((b & 0xF) << 16 | read() << 8 | read()) + 129;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void decodeNotations() throws FastInfosetException, IOException {
/*  617 */     if (this._notations == null) {
/*  618 */       this._notations = new ArrayList();
/*      */     } else {
/*  620 */       this._notations.clear();
/*      */     } 
/*      */     
/*  623 */     int b = read();
/*  624 */     while ((b & 0xFC) == 192) {
/*  625 */       String name = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */       
/*  627 */       String system_identifier = ((this._b & 0x2) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */       
/*  629 */       String public_identifier = ((this._b & 0x1) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */ 
/*      */       
/*  632 */       Notation notation = new Notation(name, system_identifier, public_identifier);
/*  633 */       this._notations.add(notation);
/*      */       
/*  635 */       b = read();
/*      */     } 
/*  637 */     if (b != 240) {
/*  638 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.IIsNotTerminatedCorrectly"));
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void decodeUnparsedEntities() throws FastInfosetException, IOException {
/*  643 */     if (this._unparsedEntities == null) {
/*  644 */       this._unparsedEntities = new ArrayList();
/*      */     } else {
/*  646 */       this._unparsedEntities.clear();
/*      */     } 
/*      */     
/*  649 */     int b = read();
/*  650 */     while ((b & 0xFE) == 208) {
/*  651 */       String name = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*  652 */       String system_identifier = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI);
/*      */       
/*  654 */       String public_identifier = ((this._b & 0x1) > 0) ? decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherURI) : "";
/*      */ 
/*      */       
/*  657 */       String notation_name = decodeIdentifyingNonEmptyStringOnFirstBit(this._v.otherNCName);
/*      */       
/*  659 */       UnparsedEntity unparsedEntity = new UnparsedEntity(name, system_identifier, public_identifier, notation_name);
/*  660 */       this._unparsedEntities.add(unparsedEntity);
/*      */       
/*  662 */       b = read();
/*      */     } 
/*  664 */     if (b != 240) {
/*  665 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.unparsedEntities"));
/*      */     }
/*      */   }
/*      */   
/*      */   protected final String decodeCharacterEncodingScheme() throws FastInfosetException, IOException {
/*  670 */     return decodeNonEmptyOctetStringOnSecondBitAsUtf8String();
/*      */   }
/*      */   protected final String decodeVersion() throws FastInfosetException, IOException {
/*      */     String data;
/*  674 */     switch (decodeNonIdentifyingStringOnFirstBit()) {
/*      */       case 0:
/*  676 */         data = new String(this._charBuffer, 0, this._charBufferLength);
/*  677 */         if (this._addToTable) {
/*  678 */           this._v.otherString.add((CharArray)new CharArrayString(data));
/*      */         }
/*  680 */         return data;
/*      */       case 2:
/*  682 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingNotSupported"));
/*      */       case 1:
/*  684 */         return this._v.otherString.get(this._integer).toString();
/*      */     } 
/*      */     
/*  687 */     return "";
/*      */   }
/*      */ 
/*      */   
/*      */   protected final QualifiedName decodeEIIIndexMedium() throws FastInfosetException, IOException {
/*  692 */     int i = ((this._b & 0x7) << 8 | read()) + 32;
/*      */     
/*  694 */     return this._v.elementName._array[i];
/*      */   }
/*      */   
/*      */   protected final QualifiedName decodeEIIIndexLarge() throws FastInfosetException, IOException {
/*      */     int i;
/*  699 */     if ((this._b & 0x30) == 32) {
/*      */       
/*  701 */       i = ((this._b & 0x7) << 16 | read() << 8 | read()) + 2080;
/*      */     }
/*      */     else {
/*      */       
/*  705 */       i = ((read() & 0xF) << 16 | read() << 8 | read()) + 526368;
/*      */     } 
/*      */     
/*  708 */     return this._v.elementName._array[i];
/*      */   }
/*      */ 
/*      */   
/*      */   protected final QualifiedName decodeLiteralQualifiedName(int state, QualifiedName q) throws FastInfosetException, IOException {
/*  713 */     if (q == null) q = new QualifiedName(); 
/*  714 */     switch (state) {
/*      */       
/*      */       case 0:
/*  717 */         return q.set("", "", decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), -1, -1, this._identifier, (char[])null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  727 */         return q.set("", decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(false), decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), -1, this._namespaceNameIndex, this._identifier, (char[])null);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  737 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.qNameMissingNamespaceName"));
/*      */       
/*      */       case 3:
/*  740 */         return q.set(decodeIdentifyingNonEmptyStringIndexOnFirstBitAsPrefix(true), decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(true), decodeIdentifyingNonEmptyStringOnFirstBit(this._v.localName), this._prefixIndex, this._namespaceNameIndex, this._identifier, this._charBuffer);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  749 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingEII"));
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
/*      */   protected final int decodeNonIdentifyingStringOnFirstBit() throws FastInfosetException, IOException {
/*  763 */     int length, b2, b = read();
/*  764 */     switch (DecoderStateTables.NISTRING(b)) {
/*      */       case 0:
/*  766 */         this._addToTable = ((b & 0x40) > 0);
/*  767 */         this._octetBufferLength = (b & 0x7) + 1;
/*  768 */         decodeUtf8StringAsCharBuffer();
/*  769 */         return 0;
/*      */       case 1:
/*  771 */         this._addToTable = ((b & 0x40) > 0);
/*  772 */         this._octetBufferLength = read() + 9;
/*  773 */         decodeUtf8StringAsCharBuffer();
/*  774 */         return 0;
/*      */       
/*      */       case 2:
/*  777 */         this._addToTable = ((b & 0x40) > 0);
/*  778 */         length = read() << 24 | read() << 16 | read() << 8 | read();
/*      */ 
/*      */ 
/*      */         
/*  782 */         this._octetBufferLength = length + 265;
/*  783 */         decodeUtf8StringAsCharBuffer();
/*  784 */         return 0;
/*      */       
/*      */       case 3:
/*  787 */         this._addToTable = ((b & 0x40) > 0);
/*  788 */         this._octetBufferLength = (b & 0x7) + 1;
/*  789 */         decodeUtf16StringAsCharBuffer();
/*  790 */         return 0;
/*      */       case 4:
/*  792 */         this._addToTable = ((b & 0x40) > 0);
/*  793 */         this._octetBufferLength = read() + 9;
/*  794 */         decodeUtf16StringAsCharBuffer();
/*  795 */         return 0;
/*      */       
/*      */       case 5:
/*  798 */         this._addToTable = ((b & 0x40) > 0);
/*  799 */         length = read() << 24 | read() << 16 | read() << 8 | read();
/*      */ 
/*      */ 
/*      */         
/*  803 */         this._octetBufferLength = length + 265;
/*  804 */         decodeUtf16StringAsCharBuffer();
/*  805 */         return 0;
/*      */ 
/*      */       
/*      */       case 6:
/*  809 */         this._addToTable = ((b & 0x40) > 0);
/*      */         
/*  811 */         this._identifier = (b & 0xF) << 4;
/*  812 */         b2 = read();
/*  813 */         this._identifier |= (b2 & 0xF0) >> 4;
/*      */         
/*  815 */         decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit(b2);
/*      */         
/*  817 */         decodeRestrictedAlphabetAsCharBuffer();
/*  818 */         return 0;
/*      */ 
/*      */       
/*      */       case 7:
/*  822 */         this._addToTable = ((b & 0x40) > 0);
/*      */         
/*  824 */         this._identifier = (b & 0xF) << 4;
/*  825 */         b2 = read();
/*  826 */         this._identifier |= (b2 & 0xF0) >> 4;
/*      */         
/*  828 */         decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit(b2);
/*  829 */         return 2;
/*      */       
/*      */       case 8:
/*  832 */         this._integer = b & 0x3F;
/*  833 */         return 1;
/*      */       case 9:
/*  835 */         this._integer = ((b & 0x1F) << 8 | read()) + 64;
/*      */         
/*  837 */         return 1;
/*      */       case 10:
/*  839 */         this._integer = ((b & 0xF) << 16 | read() << 8 | read()) + 8256;
/*      */         
/*  841 */         return 1;
/*      */       case 11:
/*  843 */         return 3;
/*      */     } 
/*  845 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingNonIdentifyingString"));
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void decodeOctetsOnFifthBitOfNonIdentifyingStringOnFirstBit(int b) throws FastInfosetException, IOException {
/*      */     int length;
/*  851 */     b &= 0xF;
/*      */     
/*  853 */     switch (DecoderStateTables.NISTRING(b)) {
/*      */       case 0:
/*  855 */         this._octetBufferLength = b + 1;
/*      */         break;
/*      */       case 1:
/*  858 */         this._octetBufferLength = read() + 9;
/*      */         break;
/*      */       case 2:
/*  861 */         length = read() << 24 | read() << 16 | read() << 8 | read();
/*      */ 
/*      */ 
/*      */         
/*  865 */         this._octetBufferLength = length + 265;
/*      */         break;
/*      */       default:
/*  868 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingOctets"));
/*      */     } 
/*  870 */     ensureOctetBufferSize();
/*  871 */     this._octetBufferStart = this._octetBufferOffset;
/*  872 */     this._octetBufferOffset += this._octetBufferLength;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void decodeOctetsOnSeventhBitOfNonIdentifyingStringOnThirdBit(int b) throws FastInfosetException, IOException {
/*  877 */     switch (b & 0x3) {
/*      */       
/*      */       case 0:
/*  880 */         this._octetBufferLength = 1;
/*      */         break;
/*      */       
/*      */       case 1:
/*  884 */         this._octetBufferLength = 2;
/*      */         break;
/*      */       
/*      */       case 2:
/*  888 */         this._octetBufferLength = read() + 3;
/*      */         break;
/*      */       
/*      */       case 3:
/*  892 */         this._octetBufferLength = read() << 24 | read() << 16 | read() << 8 | read();
/*      */ 
/*      */ 
/*      */         
/*  896 */         this._octetBufferLength += 259;
/*      */         break;
/*      */     } 
/*      */     
/*  900 */     ensureOctetBufferSize();
/*  901 */     this._octetBufferStart = this._octetBufferOffset;
/*  902 */     this._octetBufferOffset += this._octetBufferLength;
/*      */   }
/*      */   
/*      */   protected final String decodeIdentifyingNonEmptyStringOnFirstBit(StringArray table) throws FastInfosetException, IOException {
/*      */     String s;
/*      */     int length;
/*      */     String str1;
/*  909 */     int b = read();
/*  910 */     switch (DecoderStateTables.ISTRING(b)) {
/*      */       
/*      */       case 0:
/*  913 */         this._octetBufferLength = b + 1;
/*  914 */         s = this._stringInterning ? decodeUtf8StringAsString().intern() : decodeUtf8StringAsString();
/*  915 */         this._identifier = table.add(s) - 1;
/*  916 */         return s;
/*      */ 
/*      */       
/*      */       case 1:
/*  920 */         this._octetBufferLength = read() + 65;
/*  921 */         s = this._stringInterning ? decodeUtf8StringAsString().intern() : decodeUtf8StringAsString();
/*  922 */         this._identifier = table.add(s) - 1;
/*  923 */         return s;
/*      */ 
/*      */       
/*      */       case 2:
/*  927 */         length = read() << 24 | read() << 16 | read() << 8 | read();
/*      */ 
/*      */ 
/*      */         
/*  931 */         this._octetBufferLength = length + 321;
/*  932 */         str1 = this._stringInterning ? decodeUtf8StringAsString().intern() : decodeUtf8StringAsString();
/*  933 */         this._identifier = table.add(str1) - 1;
/*  934 */         return str1;
/*      */       
/*      */       case 3:
/*  937 */         this._identifier = b & 0x3F;
/*  938 */         return table._array[this._identifier];
/*      */       case 4:
/*  940 */         this._identifier = ((b & 0x1F) << 8 | read()) + 64;
/*      */         
/*  942 */         return table._array[this._identifier];
/*      */       case 5:
/*  944 */         this._identifier = ((b & 0xF) << 16 | read() << 8 | read()) + 8256;
/*      */         
/*  946 */         return table._array[this._identifier];
/*      */     } 
/*  948 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingIdentifyingString"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String decodeIdentifyingNonEmptyStringOnFirstBitAsPrefix(boolean namespaceNamePresent) throws FastInfosetException, IOException {
/*      */     String s;
/*      */     int length;
/*      */     String str1;
/*  958 */     int b = read();
/*  959 */     switch (DecoderStateTables.ISTRING_PREFIX_NAMESPACE(b)) {
/*      */       
/*      */       case 6:
/*  962 */         this._octetBufferLength = EncodingConstants.XML_NAMESPACE_PREFIX_LENGTH;
/*  963 */         decodeUtf8StringAsCharBuffer();
/*      */         
/*  965 */         if (this._charBuffer[0] == 'x' && this._charBuffer[1] == 'm' && this._charBuffer[2] == 'l')
/*      */         {
/*      */           
/*  968 */           throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.prefixIllegal"));
/*      */         }
/*      */         
/*  971 */         s = this._stringInterning ? (new String(this._charBuffer, 0, this._charBufferLength)).intern() : new String(this._charBuffer, 0, this._charBufferLength);
/*      */         
/*  973 */         this._prefixIndex = this._v.prefix.add(s);
/*  974 */         return s;
/*      */ 
/*      */       
/*      */       case 7:
/*  978 */         this._octetBufferLength = EncodingConstants.XMLNS_NAMESPACE_PREFIX_LENGTH;
/*  979 */         decodeUtf8StringAsCharBuffer();
/*      */         
/*  981 */         if (this._charBuffer[0] == 'x' && this._charBuffer[1] == 'm' && this._charBuffer[2] == 'l' && this._charBuffer[3] == 'n' && this._charBuffer[4] == 's')
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  986 */           throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.xmlns"));
/*      */         }
/*      */         
/*  989 */         s = this._stringInterning ? (new String(this._charBuffer, 0, this._charBufferLength)).intern() : new String(this._charBuffer, 0, this._charBufferLength);
/*      */         
/*  991 */         this._prefixIndex = this._v.prefix.add(s);
/*  992 */         return s;
/*      */ 
/*      */       
/*      */       case 0:
/*      */       case 8:
/*      */       case 9:
/*  998 */         this._octetBufferLength = b + 1;
/*  999 */         s = this._stringInterning ? decodeUtf8StringAsString().intern() : decodeUtf8StringAsString();
/* 1000 */         this._prefixIndex = this._v.prefix.add(s);
/* 1001 */         return s;
/*      */ 
/*      */       
/*      */       case 1:
/* 1005 */         this._octetBufferLength = read() + 65;
/* 1006 */         s = this._stringInterning ? decodeUtf8StringAsString().intern() : decodeUtf8StringAsString();
/* 1007 */         this._prefixIndex = this._v.prefix.add(s);
/* 1008 */         return s;
/*      */ 
/*      */       
/*      */       case 2:
/* 1012 */         length = read() << 24 | read() << 16 | read() << 8 | read();
/*      */ 
/*      */ 
/*      */         
/* 1016 */         this._octetBufferLength = length + 321;
/* 1017 */         str1 = this._stringInterning ? decodeUtf8StringAsString().intern() : decodeUtf8StringAsString();
/* 1018 */         this._prefixIndex = this._v.prefix.add(str1);
/* 1019 */         return str1;
/*      */       
/*      */       case 10:
/* 1022 */         if (namespaceNamePresent) {
/* 1023 */           this._prefixIndex = 0;
/*      */           
/* 1025 */           if (DecoderStateTables.ISTRING_PREFIX_NAMESPACE(peek()) != 10)
/*      */           {
/* 1027 */             throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.wrongNamespaceName"));
/*      */           }
/* 1029 */           return "xml";
/*      */         } 
/* 1031 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.missingNamespaceName"));
/*      */       
/*      */       case 3:
/* 1034 */         this._prefixIndex = b & 0x3F;
/* 1035 */         return this._v.prefix._array[this._prefixIndex - 1];
/*      */       case 4:
/* 1037 */         this._prefixIndex = ((b & 0x1F) << 8 | read()) + 64;
/*      */         
/* 1039 */         return this._v.prefix._array[this._prefixIndex - 1];
/*      */       case 5:
/* 1041 */         this._prefixIndex = ((b & 0xF) << 16 | read() << 8 | read()) + 8256;
/*      */         
/* 1043 */         return this._v.prefix._array[this._prefixIndex - 1];
/*      */     } 
/* 1045 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingIdentifyingStringForPrefix"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String decodeIdentifyingNonEmptyStringIndexOnFirstBitAsPrefix(boolean namespaceNamePresent) throws FastInfosetException, IOException {
/* 1053 */     int b = read();
/* 1054 */     switch (DecoderStateTables.ISTRING_PREFIX_NAMESPACE(b)) {
/*      */       case 10:
/* 1056 */         if (namespaceNamePresent) {
/* 1057 */           this._prefixIndex = 0;
/*      */           
/* 1059 */           if (DecoderStateTables.ISTRING_PREFIX_NAMESPACE(peek()) != 10)
/*      */           {
/* 1061 */             throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.wrongNamespaceName"));
/*      */           }
/* 1063 */           return "xml";
/*      */         } 
/* 1065 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.missingNamespaceName"));
/*      */       
/*      */       case 3:
/* 1068 */         this._prefixIndex = b & 0x3F;
/* 1069 */         return this._v.prefix._array[this._prefixIndex - 1];
/*      */       case 4:
/* 1071 */         this._prefixIndex = ((b & 0x1F) << 8 | read()) + 64;
/*      */         
/* 1073 */         return this._v.prefix._array[this._prefixIndex - 1];
/*      */       case 5:
/* 1075 */         this._prefixIndex = ((b & 0xF) << 16 | read() << 8 | read()) + 8256;
/*      */         
/* 1077 */         return this._v.prefix._array[this._prefixIndex - 1];
/*      */     } 
/* 1079 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingIdentifyingStringForPrefix"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String decodeIdentifyingNonEmptyStringOnFirstBitAsNamespaceName(boolean prefixPresent) throws FastInfosetException, IOException {
/*      */     String s;
/*      */     int length;
/*      */     String str1;
/* 1089 */     int b = read();
/* 1090 */     switch (DecoderStateTables.ISTRING_PREFIX_NAMESPACE(b)) {
/*      */       
/*      */       case 0:
/*      */       case 6:
/*      */       case 7:
/* 1095 */         this._octetBufferLength = b + 1;
/* 1096 */         s = this._stringInterning ? decodeUtf8StringAsString().intern() : decodeUtf8StringAsString();
/* 1097 */         this._namespaceNameIndex = this._v.namespaceName.add(s);
/* 1098 */         return s;
/*      */ 
/*      */       
/*      */       case 8:
/* 1102 */         this._octetBufferLength = EncodingConstants.XMLNS_NAMESPACE_NAME_LENGTH;
/* 1103 */         decodeUtf8StringAsCharBuffer();
/*      */         
/* 1105 */         if (compareCharsWithCharBufferFromEndToStart(XMLNS_NAMESPACE_NAME_CHARS)) {
/* 1106 */           throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.xmlnsConnotBeBoundToPrefix"));
/*      */         }
/*      */         
/* 1109 */         s = this._stringInterning ? (new String(this._charBuffer, 0, this._charBufferLength)).intern() : new String(this._charBuffer, 0, this._charBufferLength);
/*      */         
/* 1111 */         this._namespaceNameIndex = this._v.namespaceName.add(s);
/* 1112 */         return s;
/*      */ 
/*      */       
/*      */       case 9:
/* 1116 */         this._octetBufferLength = EncodingConstants.XML_NAMESPACE_NAME_LENGTH;
/* 1117 */         decodeUtf8StringAsCharBuffer();
/*      */         
/* 1119 */         if (compareCharsWithCharBufferFromEndToStart(XML_NAMESPACE_NAME_CHARS)) {
/* 1120 */           throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.illegalNamespaceName"));
/*      */         }
/*      */         
/* 1123 */         s = this._stringInterning ? (new String(this._charBuffer, 0, this._charBufferLength)).intern() : new String(this._charBuffer, 0, this._charBufferLength);
/*      */         
/* 1125 */         this._namespaceNameIndex = this._v.namespaceName.add(s);
/* 1126 */         return s;
/*      */ 
/*      */       
/*      */       case 1:
/* 1130 */         this._octetBufferLength = read() + 65;
/* 1131 */         s = this._stringInterning ? decodeUtf8StringAsString().intern() : decodeUtf8StringAsString();
/* 1132 */         this._namespaceNameIndex = this._v.namespaceName.add(s);
/* 1133 */         return s;
/*      */ 
/*      */       
/*      */       case 2:
/* 1137 */         length = read() << 24 | read() << 16 | read() << 8 | read();
/*      */ 
/*      */ 
/*      */         
/* 1141 */         this._octetBufferLength = length + 321;
/* 1142 */         str1 = this._stringInterning ? decodeUtf8StringAsString().intern() : decodeUtf8StringAsString();
/* 1143 */         this._namespaceNameIndex = this._v.namespaceName.add(str1);
/* 1144 */         return str1;
/*      */       
/*      */       case 10:
/* 1147 */         if (prefixPresent) {
/* 1148 */           this._namespaceNameIndex = 0;
/* 1149 */           return "http://www.w3.org/XML/1998/namespace";
/*      */         } 
/* 1151 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.namespaceWithoutPrefix"));
/*      */       
/*      */       case 3:
/* 1154 */         this._namespaceNameIndex = b & 0x3F;
/* 1155 */         return this._v.namespaceName._array[this._namespaceNameIndex - 1];
/*      */       case 4:
/* 1157 */         this._namespaceNameIndex = ((b & 0x1F) << 8 | read()) + 64;
/*      */         
/* 1159 */         return this._v.namespaceName._array[this._namespaceNameIndex - 1];
/*      */       case 5:
/* 1161 */         this._namespaceNameIndex = ((b & 0xF) << 16 | read() << 8 | read()) + 8256;
/*      */         
/* 1163 */         return this._v.namespaceName._array[this._namespaceNameIndex - 1];
/*      */     } 
/* 1165 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingForNamespaceName"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String decodeIdentifyingNonEmptyStringIndexOnFirstBitAsNamespaceName(boolean prefixPresent) throws FastInfosetException, IOException {
/* 1173 */     int b = read();
/* 1174 */     switch (DecoderStateTables.ISTRING_PREFIX_NAMESPACE(b)) {
/*      */       case 10:
/* 1176 */         if (prefixPresent) {
/* 1177 */           this._namespaceNameIndex = 0;
/* 1178 */           return "http://www.w3.org/XML/1998/namespace";
/*      */         } 
/* 1180 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.namespaceWithoutPrefix"));
/*      */       
/*      */       case 3:
/* 1183 */         this._namespaceNameIndex = b & 0x3F;
/* 1184 */         return this._v.namespaceName._array[this._namespaceNameIndex - 1];
/*      */       case 4:
/* 1186 */         this._namespaceNameIndex = ((b & 0x1F) << 8 | read()) + 64;
/*      */         
/* 1188 */         return this._v.namespaceName._array[this._namespaceNameIndex - 1];
/*      */       case 5:
/* 1190 */         this._namespaceNameIndex = ((b & 0xF) << 16 | read() << 8 | read()) + 8256;
/*      */         
/* 1192 */         return this._v.namespaceName._array[this._namespaceNameIndex - 1];
/*      */     } 
/* 1194 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingForNamespaceName"));
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean compareCharsWithCharBufferFromEndToStart(char[] c) {
/* 1199 */     int i = this._charBufferLength;
/* 1200 */     while (--i >= 0) {
/* 1201 */       if (c[i] != this._charBuffer[i]) {
/* 1202 */         return false;
/*      */       }
/*      */     } 
/* 1205 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String decodeNonEmptyOctetStringOnSecondBitAsUtf8String() throws FastInfosetException, IOException {
/* 1212 */     decodeNonEmptyOctetStringOnSecondBitAsUtf8CharArray();
/* 1213 */     return new String(this._charBuffer, 0, this._charBufferLength);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void decodeNonEmptyOctetStringOnSecondBitAsUtf8CharArray() throws FastInfosetException, IOException {
/* 1220 */     decodeNonEmptyOctetStringLengthOnSecondBit();
/* 1221 */     decodeUtf8StringAsCharBuffer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void decodeNonEmptyOctetStringLengthOnSecondBit() throws FastInfosetException, IOException {
/* 1228 */     int length, b = read();
/* 1229 */     switch (DecoderStateTables.ISTRING(b)) {
/*      */       case 0:
/* 1231 */         this._octetBufferLength = b + 1;
/*      */         return;
/*      */       case 1:
/* 1234 */         this._octetBufferLength = read() + 65;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1238 */         length = read() << 24 | read() << 16 | read() << 8 | read();
/*      */ 
/*      */ 
/*      */         
/* 1242 */         this._octetBufferLength = length + 321;
/*      */         return;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1249 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingNonEmptyOctet"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int decodeIntegerIndexOnSecondBit() throws FastInfosetException, IOException {
/* 1257 */     int b = read() | 0x80;
/* 1258 */     switch (DecoderStateTables.ISTRING(b)) {
/*      */       case 3:
/* 1260 */         return b & 0x3F;
/*      */       case 4:
/* 1262 */         return ((b & 0x1F) << 8 | read()) + 64;
/*      */       
/*      */       case 5:
/* 1265 */         return ((b & 0xF) << 16 | read() << 8 | read()) + 8256;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1271 */     throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.decodingIndexOnSecondBit"));
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void decodeHeader() throws FastInfosetException, IOException {
/* 1276 */     if (!_isFastInfosetDocument()) {
/* 1277 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.notFIDocument"));
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void decodeRestrictedAlphabetAsCharBuffer() throws FastInfosetException, IOException {
/* 1282 */     if (this._identifier <= 1) {
/* 1283 */       decodeFourBitAlphabetOctetsAsCharBuffer(BuiltInRestrictedAlphabets.table[this._identifier]);
/*      */     }
/* 1285 */     else if (this._identifier >= 32) {
/* 1286 */       CharArray ca = this._v.restrictedAlphabet.get(this._identifier - 32);
/* 1287 */       if (ca == null) {
/* 1288 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.alphabetNotPresent", new Object[] { Integer.valueOf(this._identifier) }));
/*      */       }
/* 1290 */       decodeAlphabetOctetsAsCharBuffer(ca.ch);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1295 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.alphabetIdentifiersReserved"));
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final String decodeRestrictedAlphabetAsString() throws FastInfosetException, IOException {
/* 1300 */     decodeRestrictedAlphabetAsCharBuffer();
/* 1301 */     return new String(this._charBuffer, 0, this._charBufferLength);
/*      */   }
/*      */   
/*      */   protected final String decodeRAOctetsAsString(char[] restrictedAlphabet) throws FastInfosetException, IOException {
/* 1305 */     decodeAlphabetOctetsAsCharBuffer(restrictedAlphabet);
/* 1306 */     return new String(this._charBuffer, 0, this._charBufferLength);
/*      */   }
/*      */   
/*      */   protected final void decodeFourBitAlphabetOctetsAsCharBuffer(char[] restrictedAlphabet) throws FastInfosetException, IOException {
/* 1310 */     this._charBufferLength = 0;
/* 1311 */     int characters = this._octetBufferLength * 2;
/* 1312 */     if (this._charBuffer.length < characters) {
/* 1313 */       this._charBuffer = new char[characters];
/*      */     }
/*      */     
/* 1316 */     int v = 0;
/* 1317 */     for (int i = 0; i < this._octetBufferLength - 1; i++) {
/* 1318 */       v = this._octetBuffer[this._octetBufferStart++] & 0xFF;
/* 1319 */       this._charBuffer[this._charBufferLength++] = restrictedAlphabet[v >> 4];
/* 1320 */       this._charBuffer[this._charBufferLength++] = restrictedAlphabet[v & 0xF];
/*      */     } 
/* 1322 */     v = this._octetBuffer[this._octetBufferStart++] & 0xFF;
/* 1323 */     this._charBuffer[this._charBufferLength++] = restrictedAlphabet[v >> 4];
/* 1324 */     v &= 0xF;
/* 1325 */     if (v != 15) {
/* 1326 */       this._charBuffer[this._charBufferLength++] = restrictedAlphabet[v & 0xF];
/*      */     }
/*      */   }
/*      */   
/*      */   protected final void decodeAlphabetOctetsAsCharBuffer(char[] restrictedAlphabet) throws FastInfosetException, IOException {
/* 1331 */     if (restrictedAlphabet.length < 2) {
/* 1332 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.alphabetMustContain2orMoreChars"));
/*      */     }
/*      */     
/* 1335 */     int bitsPerCharacter = 1;
/* 1336 */     while (1 << bitsPerCharacter <= restrictedAlphabet.length) {
/* 1337 */       bitsPerCharacter++;
/*      */     }
/* 1339 */     int terminatingValue = (1 << bitsPerCharacter) - 1;
/*      */     
/* 1341 */     int characters = (this._octetBufferLength << 3) / bitsPerCharacter;
/* 1342 */     if (characters == 0) {
/* 1343 */       throw new IOException("");
/*      */     }
/*      */     
/* 1346 */     this._charBufferLength = 0;
/* 1347 */     if (this._charBuffer.length < characters) {
/* 1348 */       this._charBuffer = new char[characters];
/*      */     }
/*      */     
/* 1351 */     resetBits();
/* 1352 */     for (int i = 0; i < characters; i++) {
/* 1353 */       int value = readBits(bitsPerCharacter);
/* 1354 */       if (bitsPerCharacter < 8 && value == terminatingValue) {
/* 1355 */         int octetPosition = i * bitsPerCharacter >>> 3;
/* 1356 */         if (octetPosition != this._octetBufferLength - 1) {
/* 1357 */           throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.alphabetIncorrectlyTerminated"));
/*      */         }
/*      */         break;
/*      */       } 
/* 1361 */       this._charBuffer[this._charBufferLength++] = restrictedAlphabet[value];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetBits() {
/* 1368 */     this._bitsLeftInOctet = 0;
/*      */   }
/*      */   
/*      */   private int readBits(int bits) throws IOException {
/* 1372 */     int value = 0;
/* 1373 */     while (bits > 0) {
/* 1374 */       if (this._bitsLeftInOctet == 0) {
/* 1375 */         this._b = this._octetBuffer[this._octetBufferStart++] & 0xFF;
/* 1376 */         this._bitsLeftInOctet = 8;
/*      */       } 
/* 1378 */       int bit = ((this._b & 1 << --this._bitsLeftInOctet) > 0) ? 1 : 0;
/* 1379 */       value |= bit << --bits;
/*      */     } 
/*      */     
/* 1382 */     return value;
/*      */   }
/*      */   
/*      */   protected final void decodeUtf8StringAsCharBuffer() throws IOException {
/* 1386 */     ensureOctetBufferSize();
/* 1387 */     decodeUtf8StringIntoCharBuffer();
/*      */   }
/*      */   
/*      */   protected final void decodeUtf8StringAsCharBuffer(char[] ch, int offset) throws IOException {
/* 1391 */     ensureOctetBufferSize();
/* 1392 */     decodeUtf8StringIntoCharBuffer(ch, offset);
/*      */   }
/*      */   
/*      */   protected final String decodeUtf8StringAsString() throws IOException {
/* 1396 */     decodeUtf8StringAsCharBuffer();
/* 1397 */     return new String(this._charBuffer, 0, this._charBufferLength);
/*      */   }
/*      */   
/*      */   protected final void decodeUtf16StringAsCharBuffer() throws IOException {
/* 1401 */     ensureOctetBufferSize();
/* 1402 */     decodeUtf16StringIntoCharBuffer();
/*      */   }
/*      */   
/*      */   protected final String decodeUtf16StringAsString() throws IOException {
/* 1406 */     decodeUtf16StringAsCharBuffer();
/* 1407 */     return new String(this._charBuffer, 0, this._charBufferLength);
/*      */   }
/*      */   
/*      */   private void ensureOctetBufferSize() throws IOException {
/* 1411 */     if (this._octetBufferEnd < this._octetBufferOffset + this._octetBufferLength) {
/* 1412 */       int octetsInBuffer = this._octetBufferEnd - this._octetBufferOffset;
/*      */       
/* 1414 */       if (this._octetBuffer.length < this._octetBufferLength) {
/*      */         
/* 1416 */         byte[] newOctetBuffer = new byte[this._octetBufferLength];
/*      */         
/* 1418 */         System.arraycopy(this._octetBuffer, this._octetBufferOffset, newOctetBuffer, 0, octetsInBuffer);
/* 1419 */         this._octetBuffer = newOctetBuffer;
/*      */       } else {
/*      */         
/* 1422 */         System.arraycopy(this._octetBuffer, this._octetBufferOffset, this._octetBuffer, 0, octetsInBuffer);
/*      */       } 
/* 1424 */       this._octetBufferOffset = 0;
/*      */ 
/*      */       
/* 1427 */       int octetsRead = this._s.read(this._octetBuffer, octetsInBuffer, this._octetBuffer.length - octetsInBuffer);
/* 1428 */       if (octetsRead < 0) {
/* 1429 */         throw new EOFException("Unexpeceted EOF");
/*      */       }
/* 1431 */       this._octetBufferEnd = octetsInBuffer + octetsRead;
/*      */ 
/*      */ 
/*      */       
/* 1435 */       if (this._octetBufferEnd < this._octetBufferLength) {
/* 1436 */         repeatedRead();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void repeatedRead() throws IOException {
/* 1443 */     while (this._octetBufferEnd < this._octetBufferLength) {
/*      */       
/* 1445 */       int octetsRead = this._s.read(this._octetBuffer, this._octetBufferEnd, this._octetBuffer.length - this._octetBufferEnd);
/* 1446 */       if (octetsRead < 0) {
/* 1447 */         throw new EOFException("Unexpeceted EOF");
/*      */       }
/* 1449 */       this._octetBufferEnd += octetsRead;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void decodeUtf8StringIntoCharBuffer() throws IOException {
/* 1454 */     if (this._charBuffer.length < this._octetBufferLength) {
/* 1455 */       this._charBuffer = new char[this._octetBufferLength];
/*      */     }
/*      */     
/* 1458 */     this._charBufferLength = 0;
/* 1459 */     int end = this._octetBufferLength + this._octetBufferOffset;
/*      */     
/* 1461 */     while (end != this._octetBufferOffset) {
/* 1462 */       int b1 = this._octetBuffer[this._octetBufferOffset++] & 0xFF;
/* 1463 */       if (DecoderStateTables.UTF8(b1) == 1) {
/* 1464 */         this._charBuffer[this._charBufferLength++] = (char)b1; continue;
/*      */       } 
/* 1466 */       decodeTwoToFourByteUtf8Character(b1, end);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected final void decodeUtf8StringIntoCharBuffer(char[] ch, int offset) throws IOException
/*      */   {
/* 1472 */     this._charBufferLength = offset;
/* 1473 */     int end = this._octetBufferLength + this._octetBufferOffset;
/*      */     
/* 1475 */     while (end != this._octetBufferOffset) {
/* 1476 */       int b1 = this._octetBuffer[this._octetBufferOffset++] & 0xFF;
/* 1477 */       if (DecoderStateTables.UTF8(b1) == 1) {
/* 1478 */         ch[this._charBufferLength++] = (char)b1; continue;
/*      */       } 
/* 1480 */       decodeTwoToFourByteUtf8Character(ch, b1, end);
/*      */     } 
/*      */     
/* 1483 */     this._charBufferLength -= offset; } private void decodeTwoToFourByteUtf8Character(int b1, int end) throws IOException {
/*      */     int b2;
/*      */     char c;
/*      */     int supplemental;
/* 1487 */     switch (DecoderStateTables.UTF8(b1)) {
/*      */ 
/*      */       
/*      */       case 2:
/* 1491 */         if (end == this._octetBufferOffset) {
/* 1492 */           decodeUtf8StringLengthTooSmall();
/*      */         }
/* 1494 */         b2 = this._octetBuffer[this._octetBufferOffset++] & 0xFF;
/* 1495 */         if ((b2 & 0xC0) != 128) {
/* 1496 */           decodeUtf8StringIllegalState();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1502 */         this._charBuffer[this._charBufferLength++] = (char)((b1 & 0x1F) << 6 | b2 & 0x3F);
/*      */         return;
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/* 1508 */         c = decodeUtf8ThreeByteChar(end, b1);
/* 1509 */         if (XMLChar.isContent(c)) {
/* 1510 */           this._charBuffer[this._charBufferLength++] = c;
/*      */         } else {
/* 1512 */           decodeUtf8StringIllegalState();
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 4:
/* 1517 */         supplemental = decodeUtf8FourByteChar(end, b1);
/* 1518 */         if (XMLChar.isContent(supplemental)) {
/* 1519 */           this._charBuffer[this._charBufferLength++] = this._utf8_highSurrogate;
/* 1520 */           this._charBuffer[this._charBufferLength++] = this._utf8_lowSurrogate;
/*      */         } else {
/* 1522 */           decodeUtf8StringIllegalState();
/*      */         } 
/*      */         return;
/*      */     } 
/*      */     
/* 1527 */     decodeUtf8StringIllegalState();
/*      */   } private void decodeTwoToFourByteUtf8Character(char[] ch, int b1, int end) throws IOException {
/*      */     int b2;
/*      */     char c;
/*      */     int supplemental;
/* 1532 */     switch (DecoderStateTables.UTF8(b1)) {
/*      */ 
/*      */       
/*      */       case 2:
/* 1536 */         if (end == this._octetBufferOffset) {
/* 1537 */           decodeUtf8StringLengthTooSmall();
/*      */         }
/* 1539 */         b2 = this._octetBuffer[this._octetBufferOffset++] & 0xFF;
/* 1540 */         if ((b2 & 0xC0) != 128) {
/* 1541 */           decodeUtf8StringIllegalState();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1547 */         ch[this._charBufferLength++] = (char)((b1 & 0x1F) << 6 | b2 & 0x3F);
/*      */         return;
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/* 1553 */         c = decodeUtf8ThreeByteChar(end, b1);
/* 1554 */         if (XMLChar.isContent(c)) {
/* 1555 */           ch[this._charBufferLength++] = c;
/*      */         } else {
/* 1557 */           decodeUtf8StringIllegalState();
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 4:
/* 1562 */         supplemental = decodeUtf8FourByteChar(end, b1);
/* 1563 */         if (XMLChar.isContent(supplemental)) {
/* 1564 */           ch[this._charBufferLength++] = this._utf8_highSurrogate;
/* 1565 */           ch[this._charBufferLength++] = this._utf8_lowSurrogate;
/*      */         } else {
/* 1567 */           decodeUtf8StringIllegalState();
/*      */         } 
/*      */         return;
/*      */     } 
/*      */     
/* 1572 */     decodeUtf8StringIllegalState();
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void decodeUtf8NCNameIntoCharBuffer() throws IOException {
/* 1577 */     this._charBufferLength = 0;
/* 1578 */     if (this._charBuffer.length < this._octetBufferLength) {
/* 1579 */       this._charBuffer = new char[this._octetBufferLength];
/*      */     }
/*      */     
/* 1582 */     int end = this._octetBufferLength + this._octetBufferOffset;
/*      */     
/* 1584 */     int b1 = this._octetBuffer[this._octetBufferOffset++] & 0xFF;
/* 1585 */     if (DecoderStateTables.UTF8_NCNAME(b1) == 0) {
/* 1586 */       this._charBuffer[this._charBufferLength++] = (char)b1;
/*      */     } else {
/* 1588 */       decodeUtf8NCNameStartTwoToFourByteCharacters(b1, end);
/*      */     } 
/*      */     
/* 1591 */     while (end != this._octetBufferOffset) {
/* 1592 */       b1 = this._octetBuffer[this._octetBufferOffset++] & 0xFF;
/* 1593 */       if (DecoderStateTables.UTF8_NCNAME(b1) < 2) {
/* 1594 */         this._charBuffer[this._charBufferLength++] = (char)b1; continue;
/*      */       } 
/* 1596 */       decodeUtf8NCNameTwoToFourByteCharacters(b1, end);
/*      */     } 
/*      */   } private void decodeUtf8NCNameStartTwoToFourByteCharacters(int b1, int end) throws IOException {
/*      */     int b2;
/*      */     char c, c1;
/*      */     int supplemental;
/* 1602 */     switch (DecoderStateTables.UTF8_NCNAME(b1)) {
/*      */ 
/*      */       
/*      */       case 2:
/* 1606 */         if (end == this._octetBufferOffset) {
/* 1607 */           decodeUtf8StringLengthTooSmall();
/*      */         }
/* 1609 */         b2 = this._octetBuffer[this._octetBufferOffset++] & 0xFF;
/* 1610 */         if ((b2 & 0xC0) != 128) {
/* 1611 */           decodeUtf8StringIllegalState();
/*      */         }
/*      */         
/* 1614 */         c1 = (char)((b1 & 0x1F) << 6 | b2 & 0x3F);
/*      */ 
/*      */         
/* 1617 */         if (XMLChar.isNCNameStart(c1)) {
/* 1618 */           this._charBuffer[this._charBufferLength++] = c1;
/*      */         } else {
/* 1620 */           decodeUtf8NCNameIllegalState();
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 3:
/* 1625 */         c = decodeUtf8ThreeByteChar(end, b1);
/* 1626 */         if (XMLChar.isNCNameStart(c)) {
/* 1627 */           this._charBuffer[this._charBufferLength++] = c;
/*      */         } else {
/* 1629 */           decodeUtf8NCNameIllegalState();
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 4:
/* 1634 */         supplemental = decodeUtf8FourByteChar(end, b1);
/* 1635 */         if (XMLChar.isNCNameStart(supplemental)) {
/* 1636 */           this._charBuffer[this._charBufferLength++] = this._utf8_highSurrogate;
/* 1637 */           this._charBuffer[this._charBufferLength++] = this._utf8_lowSurrogate;
/*      */         } else {
/* 1639 */           decodeUtf8NCNameIllegalState();
/*      */         } 
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/* 1645 */     decodeUtf8NCNameIllegalState();
/*      */   }
/*      */   private void decodeUtf8NCNameTwoToFourByteCharacters(int b1, int end) throws IOException {
/*      */     int b2;
/*      */     char c, c1;
/*      */     int supplemental;
/* 1651 */     switch (DecoderStateTables.UTF8_NCNAME(b1)) {
/*      */ 
/*      */       
/*      */       case 2:
/* 1655 */         if (end == this._octetBufferOffset) {
/* 1656 */           decodeUtf8StringLengthTooSmall();
/*      */         }
/* 1658 */         b2 = this._octetBuffer[this._octetBufferOffset++] & 0xFF;
/* 1659 */         if ((b2 & 0xC0) != 128) {
/* 1660 */           decodeUtf8StringIllegalState();
/*      */         }
/*      */         
/* 1663 */         c1 = (char)((b1 & 0x1F) << 6 | b2 & 0x3F);
/*      */ 
/*      */         
/* 1666 */         if (XMLChar.isNCName(c1)) {
/* 1667 */           this._charBuffer[this._charBufferLength++] = c1;
/*      */         } else {
/* 1669 */           decodeUtf8NCNameIllegalState();
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 3:
/* 1674 */         c = decodeUtf8ThreeByteChar(end, b1);
/* 1675 */         if (XMLChar.isNCName(c)) {
/* 1676 */           this._charBuffer[this._charBufferLength++] = c;
/*      */         } else {
/* 1678 */           decodeUtf8NCNameIllegalState();
/*      */         } 
/*      */         return;
/*      */       
/*      */       case 4:
/* 1683 */         supplemental = decodeUtf8FourByteChar(end, b1);
/* 1684 */         if (XMLChar.isNCName(supplemental)) {
/* 1685 */           this._charBuffer[this._charBufferLength++] = this._utf8_highSurrogate;
/* 1686 */           this._charBuffer[this._charBufferLength++] = this._utf8_lowSurrogate;
/*      */         } else {
/* 1688 */           decodeUtf8NCNameIllegalState();
/*      */         } 
/*      */         return;
/*      */     } 
/*      */     
/* 1693 */     decodeUtf8NCNameIllegalState();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private char decodeUtf8ThreeByteChar(int end, int b1) throws IOException {
/* 1699 */     if (end == this._octetBufferOffset) {
/* 1700 */       decodeUtf8StringLengthTooSmall();
/*      */     }
/* 1702 */     int b2 = this._octetBuffer[this._octetBufferOffset++] & 0xFF;
/* 1703 */     if ((b2 & 0xC0) != 128 || (b1 == 237 && b2 >= 160) || ((b1 & 0xF) == 0 && (b2 & 0x20) == 0))
/*      */     {
/*      */       
/* 1706 */       decodeUtf8StringIllegalState();
/*      */     }
/*      */ 
/*      */     
/* 1710 */     if (end == this._octetBufferOffset) {
/* 1711 */       decodeUtf8StringLengthTooSmall();
/*      */     }
/* 1713 */     int b3 = this._octetBuffer[this._octetBufferOffset++] & 0xFF;
/* 1714 */     if ((b3 & 0xC0) != 128) {
/* 1715 */       decodeUtf8StringIllegalState();
/*      */     }
/*      */     
/* 1718 */     return (char)((b1 & 0xF) << 12 | (b2 & 0x3F) << 6 | b3 & 0x3F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int decodeUtf8FourByteChar(int end, int b1) throws IOException {
/* 1729 */     if (end == this._octetBufferOffset) {
/* 1730 */       decodeUtf8StringLengthTooSmall();
/*      */     }
/* 1732 */     int b2 = this._octetBuffer[this._octetBufferOffset++] & 0xFF;
/* 1733 */     if ((b2 & 0xC0) != 128 || ((b2 & 0x30) == 0 && (b1 & 0x7) == 0))
/*      */     {
/* 1735 */       decodeUtf8StringIllegalState();
/*      */     }
/*      */ 
/*      */     
/* 1739 */     if (end == this._octetBufferOffset) {
/* 1740 */       decodeUtf8StringLengthTooSmall();
/*      */     }
/* 1742 */     int b3 = this._octetBuffer[this._octetBufferOffset++] & 0xFF;
/* 1743 */     if ((b3 & 0xC0) != 128) {
/* 1744 */       decodeUtf8StringIllegalState();
/*      */     }
/*      */ 
/*      */     
/* 1748 */     if (end == this._octetBufferOffset) {
/* 1749 */       decodeUtf8StringLengthTooSmall();
/*      */     }
/* 1751 */     int b4 = this._octetBuffer[this._octetBufferOffset++] & 0xFF;
/* 1752 */     if ((b4 & 0xC0) != 128) {
/* 1753 */       decodeUtf8StringIllegalState();
/*      */     }
/*      */     
/* 1756 */     int uuuuu = b1 << 2 & 0x1C | b2 >> 4 & 0x3;
/* 1757 */     if (uuuuu > 16) {
/* 1758 */       decodeUtf8StringIllegalState();
/*      */     }
/* 1760 */     int wwww = uuuuu - 1;
/*      */     
/* 1762 */     this._utf8_highSurrogate = (char)(0xD800 | wwww << 6 & 0x3C0 | b2 << 2 & 0x3C | b3 >> 4 & 0x3);
/*      */ 
/*      */     
/* 1765 */     this._utf8_lowSurrogate = (char)(0xDC00 | b3 << 6 & 0x3C0 | b4 & 0x3F);
/*      */     
/* 1767 */     return XMLChar.supplemental(this._utf8_highSurrogate, this._utf8_lowSurrogate);
/*      */   }
/*      */   
/*      */   private void decodeUtf8StringLengthTooSmall() throws IOException {
/* 1771 */     throw new IOException(CommonResourceBundle.getInstance().getString("message.deliminatorTooSmall"));
/*      */   }
/*      */   
/*      */   private void decodeUtf8StringIllegalState() throws IOException {
/* 1775 */     throw new IOException(CommonResourceBundle.getInstance().getString("message.UTF8Encoded"));
/*      */   }
/*      */   
/*      */   private void decodeUtf8NCNameIllegalState() throws IOException {
/* 1779 */     throw new IOException(CommonResourceBundle.getInstance().getString("message.UTF8EncodedNCName"));
/*      */   }
/*      */   
/*      */   private void decodeUtf16StringIntoCharBuffer() throws IOException {
/* 1783 */     this._charBufferLength = this._octetBufferLength / 2;
/* 1784 */     if (this._charBuffer.length < this._charBufferLength) {
/* 1785 */       this._charBuffer = new char[this._charBufferLength];
/*      */     }
/*      */     
/* 1788 */     for (int i = 0; i < this._charBufferLength; i++) {
/* 1789 */       char c = (char)(read() << 8 | read());
/*      */       
/* 1791 */       this._charBuffer[i] = c;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected String createQualifiedNameString(String second) {
/* 1797 */     return createQualifiedNameString(XMLNS_NAMESPACE_PREFIX_CHARS, second);
/*      */   }
/*      */   
/*      */   protected String createQualifiedNameString(char[] first, String second) {
/* 1801 */     int l1 = first.length;
/* 1802 */     int l2 = second.length();
/* 1803 */     int total = l1 + l2 + 1;
/* 1804 */     if (total < this._charBuffer.length) {
/* 1805 */       System.arraycopy(first, 0, this._charBuffer, 0, l1);
/* 1806 */       this._charBuffer[l1] = ':';
/* 1807 */       second.getChars(0, l2, this._charBuffer, l1 + 1);
/* 1808 */       return new String(this._charBuffer, 0, total);
/*      */     } 
/* 1810 */     StringBuffer b = new StringBuffer(new String(first));
/* 1811 */     b.append(':');
/* 1812 */     b.append(second);
/* 1813 */     return b.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   protected final int read() throws IOException {
/* 1818 */     if (this._octetBufferOffset < this._octetBufferEnd) {
/* 1819 */       return this._octetBuffer[this._octetBufferOffset++] & 0xFF;
/*      */     }
/* 1821 */     this._octetBufferEnd = this._s.read(this._octetBuffer);
/* 1822 */     if (this._octetBufferEnd < 0) {
/* 1823 */       throw new EOFException(CommonResourceBundle.getInstance().getString("message.EOF"));
/*      */     }
/*      */     
/* 1826 */     this._octetBufferOffset = 1;
/* 1827 */     return this._octetBuffer[0] & 0xFF;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void closeIfRequired() throws IOException {
/* 1832 */     if (this._s != null && this._needForceStreamClose) {
/* 1833 */       this._s.close();
/*      */     }
/*      */   }
/*      */   
/*      */   protected final int peek() throws IOException {
/* 1838 */     return peek(null);
/*      */   }
/*      */   
/*      */   protected final int peek(OctetBufferListener octetBufferListener) throws IOException {
/* 1842 */     if (this._octetBufferOffset < this._octetBufferEnd) {
/* 1843 */       return this._octetBuffer[this._octetBufferOffset] & 0xFF;
/*      */     }
/* 1845 */     if (octetBufferListener != null) {
/* 1846 */       octetBufferListener.onBeforeOctetBufferOverwrite();
/*      */     }
/*      */     
/* 1849 */     this._octetBufferEnd = this._s.read(this._octetBuffer);
/* 1850 */     if (this._octetBufferEnd < 0) {
/* 1851 */       throw new EOFException(CommonResourceBundle.getInstance().getString("message.EOF"));
/*      */     }
/*      */     
/* 1854 */     this._octetBufferOffset = 0;
/* 1855 */     return this._octetBuffer[0] & 0xFF;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final int peek2(OctetBufferListener octetBufferListener) throws IOException {
/* 1860 */     if (this._octetBufferOffset + 1 < this._octetBufferEnd) {
/* 1861 */       return this._octetBuffer[this._octetBufferOffset + 1] & 0xFF;
/*      */     }
/* 1863 */     if (octetBufferListener != null) {
/* 1864 */       octetBufferListener.onBeforeOctetBufferOverwrite();
/*      */     }
/*      */     
/* 1867 */     int offset = 0;
/* 1868 */     if (this._octetBufferOffset < this._octetBufferEnd) {
/* 1869 */       this._octetBuffer[0] = this._octetBuffer[this._octetBufferOffset];
/* 1870 */       offset = 1;
/*      */     } 
/* 1872 */     this._octetBufferEnd = this._s.read(this._octetBuffer, offset, this._octetBuffer.length - offset);
/*      */     
/* 1874 */     if (this._octetBufferEnd < 0) {
/* 1875 */       throw new EOFException(CommonResourceBundle.getInstance().getString("message.EOF"));
/*      */     }
/*      */     
/* 1878 */     this._octetBufferOffset = 0;
/* 1879 */     return this._octetBuffer[1] & 0xFF;
/*      */   }
/*      */   
/*      */   protected class EncodingAlgorithmInputStream
/*      */     extends InputStream
/*      */   {
/*      */     public int read() throws IOException {
/* 1886 */       if (Decoder.this._octetBufferStart < Decoder.this._octetBufferOffset) {
/* 1887 */         return Decoder.this._octetBuffer[Decoder.this._octetBufferStart++] & 0xFF;
/*      */       }
/* 1889 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int read(byte[] b) throws IOException {
/* 1895 */       return read(b, 0, b.length);
/*      */     }
/*      */ 
/*      */     
/*      */     public int read(byte[] b, int off, int len) throws IOException {
/* 1900 */       if (b == null)
/* 1901 */         throw new NullPointerException(); 
/* 1902 */       if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0)
/*      */       {
/* 1904 */         throw new IndexOutOfBoundsException(); } 
/* 1905 */       if (len == 0) {
/* 1906 */         return 0;
/*      */       }
/*      */       
/* 1909 */       int newOctetBufferStart = Decoder.this._octetBufferStart + len;
/* 1910 */       if (newOctetBufferStart < Decoder.this._octetBufferOffset) {
/* 1911 */         System.arraycopy(Decoder.this._octetBuffer, Decoder.this._octetBufferStart, b, off, len);
/* 1912 */         Decoder.this._octetBufferStart = newOctetBufferStart;
/* 1913 */         return len;
/* 1914 */       }  if (Decoder.this._octetBufferStart < Decoder.this._octetBufferOffset) {
/* 1915 */         int bytesToRead = Decoder.this._octetBufferOffset - Decoder.this._octetBufferStart;
/* 1916 */         System.arraycopy(Decoder.this._octetBuffer, Decoder.this._octetBufferStart, b, off, bytesToRead);
/* 1917 */         Decoder.this._octetBufferStart += bytesToRead;
/* 1918 */         return bytesToRead;
/*      */       } 
/* 1920 */       return -1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean _isFastInfosetDocument() throws IOException {
/* 1927 */     peek();
/*      */     
/* 1929 */     this._octetBufferLength = EncodingConstants.BINARY_HEADER.length;
/* 1930 */     ensureOctetBufferSize();
/* 1931 */     this._octetBufferOffset += this._octetBufferLength;
/*      */ 
/*      */     
/* 1934 */     if (this._octetBuffer[0] != EncodingConstants.BINARY_HEADER[0] || this._octetBuffer[1] != EncodingConstants.BINARY_HEADER[1] || this._octetBuffer[2] != EncodingConstants.BINARY_HEADER[2] || this._octetBuffer[3] != EncodingConstants.BINARY_HEADER[3]) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1940 */       for (int i = 0; i < EncodingConstants.XML_DECLARATION_VALUES.length; i++) {
/* 1941 */         this._octetBufferLength = (EncodingConstants.XML_DECLARATION_VALUES[i]).length - this._octetBufferOffset;
/* 1942 */         ensureOctetBufferSize();
/* 1943 */         this._octetBufferOffset += this._octetBufferLength;
/*      */ 
/*      */         
/* 1946 */         if (arrayEquals(this._octetBuffer, 0, EncodingConstants.XML_DECLARATION_VALUES[i], (EncodingConstants.XML_DECLARATION_VALUES[i]).length)) {
/*      */ 
/*      */           
/* 1949 */           this._octetBufferLength = EncodingConstants.BINARY_HEADER.length;
/* 1950 */           ensureOctetBufferSize();
/*      */ 
/*      */           
/* 1953 */           if (this._octetBuffer[this._octetBufferOffset++] != EncodingConstants.BINARY_HEADER[0] || this._octetBuffer[this._octetBufferOffset++] != EncodingConstants.BINARY_HEADER[1] || this._octetBuffer[this._octetBufferOffset++] != EncodingConstants.BINARY_HEADER[2] || this._octetBuffer[this._octetBufferOffset++] != EncodingConstants.BINARY_HEADER[3])
/*      */           {
/*      */ 
/*      */             
/* 1957 */             return false;
/*      */           }
/*      */           
/* 1960 */           return true;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1965 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1969 */     return true;
/*      */   }
/*      */   
/*      */   private boolean arrayEquals(byte[] b1, int offset, byte[] b2, int length) {
/* 1973 */     for (int i = 0; i < length; i++) {
/* 1974 */       if (b1[offset + i] != b2[i]) {
/* 1975 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1979 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isFastInfosetDocument(InputStream s) throws IOException {
/* 1986 */     byte[] header = new byte[4];
/* 1987 */     s.read(header);
/* 1988 */     if (header[0] != EncodingConstants.BINARY_HEADER[0] || header[1] != EncodingConstants.BINARY_HEADER[1] || header[2] != EncodingConstants.BINARY_HEADER[2] || header[3] != EncodingConstants.BINARY_HEADER[3])
/*      */     {
/*      */ 
/*      */       
/* 1992 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1996 */     return true;
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\Decoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */