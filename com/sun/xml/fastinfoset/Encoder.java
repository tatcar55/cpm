/*      */ package com.sun.xml.fastinfoset;
/*      */ 
/*      */ import com.sun.xml.fastinfoset.algorithm.BuiltInEncodingAlgorithmFactory;
/*      */ import com.sun.xml.fastinfoset.org.apache.xerces.util.XMLChar;
/*      */ import com.sun.xml.fastinfoset.util.CharArrayIntMap;
/*      */ import com.sun.xml.fastinfoset.util.LocalNameQualifiedNamesMap;
/*      */ import com.sun.xml.fastinfoset.util.StringIntMap;
/*      */ import com.sun.xml.fastinfoset.vocab.SerializerVocabulary;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import org.jvnet.fastinfoset.EncodingAlgorithm;
/*      */ import org.jvnet.fastinfoset.EncodingAlgorithmException;
/*      */ import org.jvnet.fastinfoset.ExternalVocabulary;
/*      */ import org.jvnet.fastinfoset.FastInfosetException;
/*      */ import org.jvnet.fastinfoset.FastInfosetSerializer;
/*      */ import org.jvnet.fastinfoset.VocabularyApplicationData;
/*      */ import org.xml.sax.helpers.DefaultHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Encoder
/*      */   extends DefaultHandler
/*      */   implements FastInfosetSerializer
/*      */ {
/*      */   public static final String CHARACTER_ENCODING_SCHEME_SYSTEM_PROPERTY = "com.sun.xml.fastinfoset.serializer.character-encoding-scheme";
/*   77 */   protected static final String _characterEncodingSchemeSystemDefault = getDefaultEncodingScheme();
/*      */   
/*      */   private static String getDefaultEncodingScheme() {
/*   80 */     String p = System.getProperty("com.sun.xml.fastinfoset.serializer.character-encoding-scheme", "UTF-8");
/*      */     
/*   82 */     if (p.equals("UTF-16BE")) {
/*   83 */       return "UTF-16BE";
/*      */     }
/*   85 */     return "UTF-8";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   94 */   private static int[] NUMERIC_CHARACTERS_TABLE = new int[maxCharacter("0123456789-+.E ") + 1];
/*   95 */   private static int[] DATE_TIME_CHARACTERS_TABLE = new int[maxCharacter("0123456789-:TZ ") + 1]; private boolean _ignoreDTD; private boolean _ignoreComments; static {
/*      */     int i;
/*   97 */     for (i = 0; i < NUMERIC_CHARACTERS_TABLE.length; i++) {
/*   98 */       NUMERIC_CHARACTERS_TABLE[i] = -1;
/*      */     }
/*  100 */     for (i = 0; i < DATE_TIME_CHARACTERS_TABLE.length; i++) {
/*  101 */       DATE_TIME_CHARACTERS_TABLE[i] = -1;
/*      */     }
/*      */     
/*  104 */     for (i = 0; i < "0123456789-+.E ".length(); i++) {
/*  105 */       NUMERIC_CHARACTERS_TABLE["0123456789-+.E ".charAt(i)] = i;
/*      */     }
/*  107 */     for (i = 0; i < "0123456789-:TZ ".length(); i++)
/*  108 */       DATE_TIME_CHARACTERS_TABLE["0123456789-:TZ ".charAt(i)] = i; 
/*      */   }
/*      */   private boolean _ignoreProcessingInstructions; private boolean _ignoreWhiteSpaceTextContent; private boolean _useLocalNameAsKeyForQualifiedNameLookup;
/*      */   
/*      */   private static int maxCharacter(String alphabet) {
/*  113 */     int c = 0;
/*  114 */     for (int i = 0; i < alphabet.length(); i++) {
/*  115 */       if (c < alphabet.charAt(i)) {
/*  116 */         c = alphabet.charAt(i);
/*      */       }
/*      */     } 
/*      */     
/*  120 */     return c;
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
/*      */   private boolean _encodingStringsAsUtf8 = true;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int _nonIdentifyingStringOnThirdBitCES;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int _nonIdentifyingStringOnFirstBitCES;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  171 */   private Map _registeredEncodingAlgorithms = new HashMap<Object, Object>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected SerializerVocabulary _v;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected VocabularyApplicationData _vData;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean _vIsInternal;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean _terminate = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _b;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected OutputStream _s;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  208 */   protected char[] _charBuffer = new char[512];
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  213 */   protected byte[] _octetBuffer = new byte[1024];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int _octetBufferIndex;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  226 */   protected int _markIndex = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  232 */   protected int minAttributeValueSize = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  238 */   protected int maxAttributeValueSize = 32;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  244 */   protected int attributeValueMapTotalCharactersConstraint = 1073741823;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  251 */   protected int minCharacterContentChunkSize = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  258 */   protected int maxCharacterContentChunkSize = 32;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  264 */   protected int characterContentChunkMapTotalCharactersConstraint = 1073741823;
/*      */ 
/*      */ 
/*      */   
/*      */   private int _bitsLeftInOctet;
/*      */ 
/*      */ 
/*      */   
/*      */   private EncodingBufferOutputStream _encodingBufferOutputStream;
/*      */ 
/*      */ 
/*      */   
/*      */   private byte[] _encodingBuffer;
/*      */ 
/*      */ 
/*      */   
/*      */   private int _encodingBufferIndex;
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIgnoreDTD(boolean ignoreDTD) {
/*  285 */     this._ignoreDTD = ignoreDTD;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getIgnoreDTD() {
/*  292 */     return this._ignoreDTD;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIgnoreComments(boolean ignoreComments) {
/*  299 */     this._ignoreComments = ignoreComments;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getIgnoreComments() {
/*  306 */     return this._ignoreComments;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIgnoreProcesingInstructions(boolean ignoreProcesingInstructions) {
/*  314 */     this._ignoreProcessingInstructions = ignoreProcesingInstructions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getIgnoreProcesingInstructions() {
/*  321 */     return this._ignoreProcessingInstructions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIgnoreWhiteSpaceTextContent(boolean ignoreWhiteSpaceTextContent) {
/*  328 */     this._ignoreWhiteSpaceTextContent = ignoreWhiteSpaceTextContent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean getIgnoreWhiteSpaceTextContent() {
/*  335 */     return this._ignoreWhiteSpaceTextContent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCharacterEncodingScheme(String characterEncodingScheme) {
/*  342 */     if (characterEncodingScheme.equals("UTF-16BE")) {
/*  343 */       this._encodingStringsAsUtf8 = false;
/*  344 */       this._nonIdentifyingStringOnThirdBitCES = 132;
/*  345 */       this._nonIdentifyingStringOnFirstBitCES = 16;
/*      */     } else {
/*  347 */       this._encodingStringsAsUtf8 = true;
/*  348 */       this._nonIdentifyingStringOnThirdBitCES = 128;
/*  349 */       this._nonIdentifyingStringOnFirstBitCES = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCharacterEncodingScheme() {
/*  357 */     return this._encodingStringsAsUtf8 ? "UTF-8" : "UTF-16BE";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRegisteredEncodingAlgorithms(Map algorithms) {
/*  364 */     this._registeredEncodingAlgorithms = algorithms;
/*  365 */     if (this._registeredEncodingAlgorithms == null) {
/*  366 */       this._registeredEncodingAlgorithms = new HashMap<Object, Object>();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Map getRegisteredEncodingAlgorithms() {
/*  374 */     return this._registeredEncodingAlgorithms;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinCharacterContentChunkSize() {
/*  381 */     return this.minCharacterContentChunkSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinCharacterContentChunkSize(int size) {
/*  388 */     if (size < 0) {
/*  389 */       size = 0;
/*      */     }
/*      */     
/*  392 */     this.minCharacterContentChunkSize = size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxCharacterContentChunkSize() {
/*  399 */     return this.maxCharacterContentChunkSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxCharacterContentChunkSize(int size) {
/*  406 */     if (size < 0) {
/*  407 */       size = 0;
/*      */     }
/*      */     
/*  410 */     this.maxCharacterContentChunkSize = size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCharacterContentChunkMapMemoryLimit() {
/*  417 */     return this.characterContentChunkMapTotalCharactersConstraint * 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCharacterContentChunkMapMemoryLimit(int size) {
/*  424 */     if (size < 0) {
/*  425 */       size = 0;
/*      */     }
/*      */     
/*  428 */     this.characterContentChunkMapTotalCharactersConstraint = size / 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCharacterContentChunkLengthMatchesLimit(int length) {
/*  438 */     return (length >= this.minCharacterContentChunkSize && length < this.maxCharacterContentChunkSize);
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
/*      */   public boolean canAddCharacterContentToTable(int length, CharArrayIntMap map) {
/*  451 */     return (map.getTotalCharacterCount() + length < this.characterContentChunkMapTotalCharactersConstraint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinAttributeValueSize() {
/*  459 */     return this.minAttributeValueSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinAttributeValueSize(int size) {
/*  466 */     if (size < 0) {
/*  467 */       size = 0;
/*      */     }
/*      */     
/*  470 */     this.minAttributeValueSize = size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxAttributeValueSize() {
/*  477 */     return this.maxAttributeValueSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxAttributeValueSize(int size) {
/*  484 */     if (size < 0) {
/*  485 */       size = 0;
/*      */     }
/*      */     
/*  488 */     this.maxAttributeValueSize = size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAttributeValueMapMemoryLimit(int size) {
/*  495 */     if (size < 0) {
/*  496 */       size = 0;
/*      */     }
/*      */     
/*  499 */     this.attributeValueMapTotalCharactersConstraint = size / 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAttributeValueMapMemoryLimit() {
/*  507 */     return this.attributeValueMapTotalCharactersConstraint * 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAttributeValueLengthMatchesLimit(int length) {
/*  517 */     return (length >= this.minAttributeValueSize && length < this.maxAttributeValueSize);
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
/*      */   public boolean canAddAttributeToTable(int length) {
/*  529 */     return (this._v.attributeValue.getTotalCharacterCount() + length < this.attributeValueMapTotalCharactersConstraint);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExternalVocabulary(ExternalVocabulary v) {
/*  538 */     this._v = new SerializerVocabulary();
/*      */     
/*  540 */     SerializerVocabulary ev = new SerializerVocabulary(v.vocabulary, this._useLocalNameAsKeyForQualifiedNameLookup);
/*      */     
/*  542 */     this._v.setExternalVocabulary(v.URI, ev, false);
/*      */ 
/*      */     
/*  545 */     this._vIsInternal = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVocabularyApplicationData(VocabularyApplicationData data) {
/*  552 */     this._vData = data;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public VocabularyApplicationData getVocabularyApplicationData() {
/*  559 */     return this._vData;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reset() {
/*  568 */     this._terminate = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOutputStream(OutputStream s) {
/*  578 */     this._octetBufferIndex = 0;
/*  579 */     this._markIndex = -1;
/*  580 */     this._s = s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVocabulary(SerializerVocabulary vocabulary) {
/*  589 */     this._v = vocabulary;
/*  590 */     this._vIsInternal = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeHeader(boolean encodeXmlDecl) throws IOException {
/*  599 */     if (encodeXmlDecl) {
/*  600 */       this._s.write(EncodingConstants.XML_DECLARATION_VALUES[0]);
/*      */     }
/*  602 */     this._s.write(EncodingConstants.BINARY_HEADER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeInitialVocabulary() throws IOException {
/*  610 */     if (this._v == null) {
/*  611 */       this._v = new SerializerVocabulary();
/*  612 */       this._vIsInternal = true;
/*  613 */     } else if (this._vIsInternal) {
/*  614 */       this._v.clear();
/*  615 */       if (this._vData != null) {
/*  616 */         this._vData.clear();
/*      */       }
/*      */     } 
/*  619 */     if (!this._v.hasInitialVocabulary() && !this._v.hasExternalVocabulary()) {
/*  620 */       write(0);
/*  621 */     } else if (this._v.hasInitialVocabulary()) {
/*  622 */       this._b = 32;
/*  623 */       write(this._b);
/*      */       
/*  625 */       SerializerVocabulary initialVocabulary = this._v.getReadOnlyVocabulary();
/*      */ 
/*      */       
/*  628 */       if (initialVocabulary.hasExternalVocabulary()) {
/*  629 */         this._b = 16;
/*  630 */         write(this._b);
/*  631 */         write(0);
/*      */       } 
/*      */       
/*  634 */       if (initialVocabulary.hasExternalVocabulary()) {
/*  635 */         encodeNonEmptyOctetStringOnSecondBit(this._v.getExternalVocabularyURI());
/*      */       
/*      */       }
/*      */     }
/*  639 */     else if (this._v.hasExternalVocabulary()) {
/*  640 */       this._b = 32;
/*  641 */       write(this._b);
/*      */       
/*  643 */       this._b = 16;
/*  644 */       write(this._b);
/*  645 */       write(0);
/*      */       
/*  647 */       encodeNonEmptyOctetStringOnSecondBit(this._v.getExternalVocabularyURI());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeDocumentTermination() throws IOException {
/*  656 */     encodeElementTermination();
/*  657 */     encodeTermination();
/*  658 */     _flush();
/*  659 */     this._s.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeElementTermination() throws IOException {
/*  667 */     this._terminate = true;
/*  668 */     switch (this._b) {
/*      */       case 240:
/*  670 */         this._b = 255;
/*      */         return;
/*      */       case 255:
/*  673 */         write(255); break;
/*      */     } 
/*  675 */     this._b = 240;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeTermination() throws IOException {
/*  684 */     if (this._terminate) {
/*  685 */       write(this._b);
/*  686 */       this._b = 0;
/*  687 */       this._terminate = false;
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
/*      */   protected final void encodeNamespaceAttribute(String prefix, String uri) throws IOException {
/*  700 */     this._b = 204;
/*  701 */     if (prefix.length() > 0) {
/*  702 */       this._b |= 0x2;
/*      */     }
/*  704 */     if (uri.length() > 0) {
/*  705 */       this._b |= 0x1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  713 */     write(this._b);
/*      */     
/*  715 */     if (prefix.length() > 0) {
/*  716 */       encodeIdentifyingNonEmptyStringOnFirstBit(prefix, this._v.prefix);
/*      */     }
/*  718 */     if (uri.length() > 0) {
/*  719 */       encodeIdentifyingNonEmptyStringOnFirstBit(uri, this._v.namespaceName);
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
/*      */   protected final void encodeCharacters(char[] ch, int offset, int length) throws IOException {
/*  732 */     boolean addToTable = isCharacterContentChunkLengthMatchesLimit(length);
/*  733 */     encodeNonIdentifyingStringOnThirdBit(ch, offset, length, this._v.characterContentChunk, addToTable, true);
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
/*      */   protected final void encodeCharactersNoClone(char[] ch, int offset, int length) throws IOException {
/*  749 */     boolean addToTable = isCharacterContentChunkLengthMatchesLimit(length);
/*  750 */     encodeNonIdentifyingStringOnThirdBit(ch, offset, length, this._v.characterContentChunk, addToTable, false);
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
/*      */   protected final void encodeNumericFourBitCharacters(char[] ch, int offset, int length, boolean addToTable) throws FastInfosetException, IOException {
/*  768 */     encodeFourBitCharacters(0, NUMERIC_CHARACTERS_TABLE, ch, offset, length, addToTable);
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
/*      */   protected final void encodeDateTimeFourBitCharacters(char[] ch, int offset, int length, boolean addToTable) throws FastInfosetException, IOException {
/*  787 */     encodeFourBitCharacters(1, DATE_TIME_CHARACTERS_TABLE, ch, offset, length, addToTable);
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
/*      */   protected final void encodeFourBitCharacters(int id, int[] table, char[] ch, int offset, int length, boolean addToTable) throws FastInfosetException, IOException {
/*  806 */     if (addToTable) {
/*      */       
/*  808 */       boolean canAddCharacterContentToTable = canAddCharacterContentToTable(length, this._v.characterContentChunk);
/*      */ 
/*      */ 
/*      */       
/*  812 */       int index = canAddCharacterContentToTable ? this._v.characterContentChunk.obtainIndex(ch, offset, length, true) : this._v.characterContentChunk.get(ch, offset, length);
/*      */ 
/*      */ 
/*      */       
/*  816 */       if (index != -1) {
/*      */         
/*  818 */         this._b = 160;
/*  819 */         encodeNonZeroIntegerOnFourthBit(index); return;
/*      */       } 
/*  821 */       if (canAddCharacterContentToTable) {
/*      */         
/*  823 */         this._b = 152;
/*      */       } else {
/*      */         
/*  826 */         this._b = 136;
/*      */       } 
/*      */     } else {
/*  829 */       this._b = 136;
/*      */     } 
/*      */     
/*  832 */     write(this._b);
/*      */ 
/*      */     
/*  835 */     this._b = id << 2;
/*      */     
/*  837 */     encodeNonEmptyFourBitCharacterStringOnSeventhBit(table, ch, offset, length);
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
/*      */   protected final void encodeAlphabetCharacters(String alphabet, char[] ch, int offset, int length, boolean addToTable) throws FastInfosetException, IOException {
/*  856 */     if (addToTable) {
/*      */       
/*  858 */       boolean canAddCharacterContentToTable = canAddCharacterContentToTable(length, this._v.characterContentChunk);
/*      */ 
/*      */ 
/*      */       
/*  862 */       int index = canAddCharacterContentToTable ? this._v.characterContentChunk.obtainIndex(ch, offset, length, true) : this._v.characterContentChunk.get(ch, offset, length);
/*      */ 
/*      */ 
/*      */       
/*  866 */       if (index != -1) {
/*      */         
/*  868 */         this._b = 160;
/*  869 */         encodeNonZeroIntegerOnFourthBit(index); return;
/*      */       } 
/*  871 */       if (canAddCharacterContentToTable) {
/*      */         
/*  873 */         this._b = 152;
/*      */       } else {
/*      */         
/*  876 */         this._b = 136;
/*      */       } 
/*      */     } else {
/*  879 */       this._b = 136;
/*      */     } 
/*      */     
/*  882 */     int id = this._v.restrictedAlphabet.get(alphabet);
/*  883 */     if (id == -1) {
/*  884 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.restrictedAlphabetNotPresent"));
/*      */     }
/*  886 */     id += 32;
/*      */     
/*  888 */     this._b |= (id & 0xC0) >> 6;
/*  889 */     write(this._b);
/*      */ 
/*      */     
/*  892 */     this._b = (id & 0x3F) << 2;
/*      */     
/*  894 */     encodeNonEmptyNBitCharacterStringOnSeventhBit(alphabet, ch, offset, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeProcessingInstruction(String target, String data) throws IOException {
/*  904 */     write(225);
/*      */ 
/*      */     
/*  907 */     encodeIdentifyingNonEmptyStringOnFirstBit(target, this._v.otherNCName);
/*      */ 
/*      */     
/*  910 */     boolean addToTable = isCharacterContentChunkLengthMatchesLimit(data.length());
/*  911 */     encodeNonIdentifyingStringOnFirstBit(data, this._v.otherString, addToTable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeDocumentTypeDeclaration(String systemId, String publicId) throws IOException {
/*  921 */     this._b = 196;
/*  922 */     if (systemId != null && systemId.length() > 0) {
/*  923 */       this._b |= 0x2;
/*      */     }
/*  925 */     if (publicId != null && publicId.length() > 0) {
/*  926 */       this._b |= 0x1;
/*      */     }
/*  928 */     write(this._b);
/*      */     
/*  930 */     if (systemId != null && systemId.length() > 0) {
/*  931 */       encodeIdentifyingNonEmptyStringOnFirstBit(systemId, this._v.otherURI);
/*      */     }
/*  933 */     if (publicId != null && publicId.length() > 0) {
/*  934 */       encodeIdentifyingNonEmptyStringOnFirstBit(publicId, this._v.otherURI);
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
/*      */   protected final void encodeComment(char[] ch, int offset, int length) throws IOException {
/*  947 */     write(226);
/*      */     
/*  949 */     boolean addToTable = isCharacterContentChunkLengthMatchesLimit(length);
/*  950 */     encodeNonIdentifyingStringOnFirstBit(ch, offset, length, this._v.otherString, addToTable, true);
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
/*      */   protected final void encodeCommentNoClone(char[] ch, int offset, int length) throws IOException {
/*  966 */     write(226);
/*      */     
/*  968 */     boolean addToTable = isCharacterContentChunkLengthMatchesLimit(length);
/*  969 */     encodeNonIdentifyingStringOnFirstBit(ch, offset, length, this._v.otherString, addToTable, false);
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
/*      */   protected final void encodeElementQualifiedNameOnThirdBit(String namespaceURI, String prefix, String localName) throws IOException {
/*  987 */     LocalNameQualifiedNamesMap.Entry entry = this._v.elementName.obtainEntry(localName);
/*  988 */     if (entry._valueIndex > 0) {
/*  989 */       QualifiedName[] names = entry._value;
/*  990 */       for (int i = 0; i < entry._valueIndex; i++) {
/*  991 */         if ((prefix == (names[i]).prefix || prefix.equals((names[i]).prefix)) && (namespaceURI == (names[i]).namespaceName || namespaceURI.equals((names[i]).namespaceName))) {
/*      */           
/*  993 */           encodeNonZeroIntegerOnThirdBit((names[i]).index);
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*  999 */     encodeLiteralElementQualifiedNameOnThirdBit(namespaceURI, prefix, localName, entry);
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
/*      */   protected final void encodeLiteralElementQualifiedNameOnThirdBit(String namespaceURI, String prefix, String localName, LocalNameQualifiedNamesMap.Entry entry) throws IOException {
/* 1014 */     QualifiedName name = new QualifiedName(prefix, namespaceURI, localName, "", this._v.elementName.getNextIndex());
/* 1015 */     entry.addQualifiedName(name);
/*      */     
/* 1017 */     int namespaceURIIndex = -1;
/* 1018 */     int prefixIndex = -1;
/* 1019 */     if (namespaceURI.length() > 0) {
/* 1020 */       namespaceURIIndex = this._v.namespaceName.get(namespaceURI);
/* 1021 */       if (namespaceURIIndex == -1) {
/* 1022 */         throw new IOException(CommonResourceBundle.getInstance().getString("message.namespaceURINotIndexed", new Object[] { namespaceURI }));
/*      */       }
/*      */       
/* 1025 */       if (prefix.length() > 0) {
/* 1026 */         prefixIndex = this._v.prefix.get(prefix);
/* 1027 */         if (prefixIndex == -1) {
/* 1028 */           throw new IOException(CommonResourceBundle.getInstance().getString("message.prefixNotIndexed", new Object[] { prefix }));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1033 */     int localNameIndex = this._v.localName.obtainIndex(localName);
/*      */     
/* 1035 */     this._b |= 0x3C;
/* 1036 */     if (namespaceURIIndex >= 0) {
/* 1037 */       this._b |= 0x1;
/* 1038 */       if (prefixIndex >= 0) {
/* 1039 */         this._b |= 0x2;
/*      */       }
/*      */     } 
/* 1042 */     write(this._b);
/*      */     
/* 1044 */     if (namespaceURIIndex >= 0) {
/* 1045 */       if (prefixIndex >= 0) {
/* 1046 */         encodeNonZeroIntegerOnSecondBitFirstBitOne(prefixIndex);
/*      */       }
/* 1048 */       encodeNonZeroIntegerOnSecondBitFirstBitOne(namespaceURIIndex);
/*      */     } 
/*      */     
/* 1051 */     if (localNameIndex >= 0) {
/* 1052 */       encodeNonZeroIntegerOnSecondBitFirstBitOne(localNameIndex);
/*      */     } else {
/* 1054 */       encodeNonEmptyOctetStringOnSecondBit(localName);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeAttributeQualifiedNameOnSecondBit(String namespaceURI, String prefix, String localName) throws IOException {
/* 1073 */     LocalNameQualifiedNamesMap.Entry entry = this._v.attributeName.obtainEntry(localName);
/* 1074 */     if (entry._valueIndex > 0) {
/* 1075 */       QualifiedName[] names = entry._value;
/* 1076 */       for (int i = 0; i < entry._valueIndex; i++) {
/* 1077 */         if ((prefix == (names[i]).prefix || prefix.equals((names[i]).prefix)) && (namespaceURI == (names[i]).namespaceName || namespaceURI.equals((names[i]).namespaceName))) {
/*      */           
/* 1079 */           encodeNonZeroIntegerOnSecondBitFirstBitZero((names[i]).index);
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/* 1085 */     encodeLiteralAttributeQualifiedNameOnSecondBit(namespaceURI, prefix, localName, entry);
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
/*      */   protected final boolean encodeLiteralAttributeQualifiedNameOnSecondBit(String namespaceURI, String prefix, String localName, LocalNameQualifiedNamesMap.Entry entry) throws IOException {
/* 1100 */     int namespaceURIIndex = -1;
/* 1101 */     int prefixIndex = -1;
/* 1102 */     if (namespaceURI.length() > 0) {
/* 1103 */       namespaceURIIndex = this._v.namespaceName.get(namespaceURI);
/* 1104 */       if (namespaceURIIndex == -1) {
/* 1105 */         if (namespaceURI == "http://www.w3.org/2000/xmlns/" || namespaceURI.equals("http://www.w3.org/2000/xmlns/"))
/*      */         {
/* 1107 */           return false;
/*      */         }
/* 1109 */         throw new IOException(CommonResourceBundle.getInstance().getString("message.namespaceURINotIndexed", new Object[] { namespaceURI }));
/*      */       } 
/*      */ 
/*      */       
/* 1113 */       if (prefix.length() > 0) {
/* 1114 */         prefixIndex = this._v.prefix.get(prefix);
/* 1115 */         if (prefixIndex == -1) {
/* 1116 */           throw new IOException(CommonResourceBundle.getInstance().getString("message.prefixNotIndexed", new Object[] { prefix }));
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1121 */     int localNameIndex = this._v.localName.obtainIndex(localName);
/*      */     
/* 1123 */     QualifiedName name = new QualifiedName(prefix, namespaceURI, localName, "", this._v.attributeName.getNextIndex());
/* 1124 */     entry.addQualifiedName(name);
/*      */     
/* 1126 */     this._b = 120;
/* 1127 */     if (namespaceURI.length() > 0) {
/* 1128 */       this._b |= 0x1;
/* 1129 */       if (prefix.length() > 0) {
/* 1130 */         this._b |= 0x2;
/*      */       }
/*      */     } 
/*      */     
/* 1134 */     write(this._b);
/*      */     
/* 1136 */     if (namespaceURIIndex >= 0) {
/* 1137 */       if (prefixIndex >= 0) {
/* 1138 */         encodeNonZeroIntegerOnSecondBitFirstBitOne(prefixIndex);
/*      */       }
/* 1140 */       encodeNonZeroIntegerOnSecondBitFirstBitOne(namespaceURIIndex);
/* 1141 */     } else if (namespaceURI != "") {
/*      */       
/* 1143 */       encodeNonEmptyOctetStringOnSecondBit("xml");
/* 1144 */       encodeNonEmptyOctetStringOnSecondBit("http://www.w3.org/XML/1998/namespace");
/*      */     } 
/*      */     
/* 1147 */     if (localNameIndex >= 0) {
/* 1148 */       encodeNonZeroIntegerOnSecondBitFirstBitOne(localNameIndex);
/*      */     } else {
/* 1150 */       encodeNonEmptyOctetStringOnSecondBit(localName);
/*      */     } 
/*      */     
/* 1153 */     return true;
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
/*      */   protected final void encodeNonIdentifyingStringOnFirstBit(String s, StringIntMap map, boolean addToTable, boolean mustBeAddedToTable) throws IOException {
/* 1169 */     if (s == null || s.length() == 0) {
/*      */       
/* 1171 */       write(255);
/*      */     }
/* 1173 */     else if (addToTable || mustBeAddedToTable) {
/*      */       
/* 1175 */       boolean canAddAttributeToTable = (mustBeAddedToTable || canAddAttributeToTable(s.length()));
/*      */ 
/*      */ 
/*      */       
/* 1179 */       int index = canAddAttributeToTable ? map.obtainIndex(s) : map.get(s);
/*      */ 
/*      */ 
/*      */       
/* 1183 */       if (index != -1) {
/*      */         
/* 1185 */         encodeNonZeroIntegerOnSecondBitFirstBitOne(index);
/* 1186 */       } else if (canAddAttributeToTable) {
/*      */         
/* 1188 */         this._b = 0x40 | this._nonIdentifyingStringOnFirstBitCES;
/*      */         
/* 1190 */         encodeNonEmptyCharacterStringOnFifthBit(s);
/*      */       } else {
/*      */         
/* 1193 */         this._b = this._nonIdentifyingStringOnFirstBitCES;
/* 1194 */         encodeNonEmptyCharacterStringOnFifthBit(s);
/*      */       } 
/*      */     } else {
/* 1197 */       this._b = this._nonIdentifyingStringOnFirstBitCES;
/* 1198 */       encodeNonEmptyCharacterStringOnFifthBit(s);
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
/*      */ 
/*      */   
/*      */   protected final void encodeNonIdentifyingStringOnFirstBit(String s, CharArrayIntMap map, boolean addToTable) throws IOException {
/* 1213 */     if (s == null || s.length() == 0) {
/*      */       
/* 1215 */       write(255);
/*      */     }
/* 1217 */     else if (addToTable) {
/* 1218 */       char[] ch = s.toCharArray();
/* 1219 */       int length = s.length();
/*      */ 
/*      */       
/* 1222 */       boolean canAddCharacterContentToTable = canAddCharacterContentToTable(length, map);
/*      */ 
/*      */ 
/*      */       
/* 1226 */       int index = canAddCharacterContentToTable ? map.obtainIndex(ch, 0, length, false) : map.get(ch, 0, length);
/*      */ 
/*      */ 
/*      */       
/* 1230 */       if (index != -1) {
/*      */         
/* 1232 */         encodeNonZeroIntegerOnSecondBitFirstBitOne(index);
/* 1233 */       } else if (canAddCharacterContentToTable) {
/*      */         
/* 1235 */         this._b = 0x40 | this._nonIdentifyingStringOnFirstBitCES;
/*      */         
/* 1237 */         encodeNonEmptyCharacterStringOnFifthBit(ch, 0, length);
/*      */       } else {
/*      */         
/* 1240 */         this._b = this._nonIdentifyingStringOnFirstBitCES;
/* 1241 */         encodeNonEmptyCharacterStringOnFifthBit(s);
/*      */       } 
/*      */     } else {
/* 1244 */       this._b = this._nonIdentifyingStringOnFirstBitCES;
/* 1245 */       encodeNonEmptyCharacterStringOnFifthBit(s);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeNonIdentifyingStringOnFirstBit(char[] ch, int offset, int length, CharArrayIntMap map, boolean addToTable, boolean clone) throws IOException {
/* 1265 */     if (length == 0) {
/*      */       
/* 1267 */       write(255);
/*      */     }
/* 1269 */     else if (addToTable) {
/*      */       
/* 1271 */       boolean canAddCharacterContentToTable = canAddCharacterContentToTable(length, map);
/*      */ 
/*      */ 
/*      */       
/* 1275 */       int index = canAddCharacterContentToTable ? map.obtainIndex(ch, offset, length, clone) : map.get(ch, offset, length);
/*      */ 
/*      */ 
/*      */       
/* 1279 */       if (index != -1) {
/*      */         
/* 1281 */         encodeNonZeroIntegerOnSecondBitFirstBitOne(index);
/* 1282 */       } else if (canAddCharacterContentToTable) {
/*      */         
/* 1284 */         this._b = 0x40 | this._nonIdentifyingStringOnFirstBitCES;
/*      */         
/* 1286 */         encodeNonEmptyCharacterStringOnFifthBit(ch, offset, length);
/*      */       } else {
/*      */         
/* 1289 */         this._b = this._nonIdentifyingStringOnFirstBitCES;
/* 1290 */         encodeNonEmptyCharacterStringOnFifthBit(ch, offset, length);
/*      */       } 
/*      */     } else {
/* 1293 */       this._b = this._nonIdentifyingStringOnFirstBitCES;
/* 1294 */       encodeNonEmptyCharacterStringOnFifthBit(ch, offset, length);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeNumericNonIdentifyingStringOnFirstBit(String s, boolean addToTable, boolean mustBeAddedToTable) throws IOException, FastInfosetException {
/* 1302 */     encodeNonIdentifyingStringOnFirstBit(0, NUMERIC_CHARACTERS_TABLE, s, addToTable, mustBeAddedToTable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeDateTimeNonIdentifyingStringOnFirstBit(String s, boolean addToTable, boolean mustBeAddedToTable) throws IOException, FastInfosetException {
/* 1311 */     encodeNonIdentifyingStringOnFirstBit(1, DATE_TIME_CHARACTERS_TABLE, s, addToTable, mustBeAddedToTable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeNonIdentifyingStringOnFirstBit(int id, int[] table, String s, boolean addToTable, boolean mustBeAddedToTable) throws IOException, FastInfosetException {
/* 1320 */     if (s == null || s.length() == 0) {
/*      */       
/* 1322 */       write(255);
/*      */       
/*      */       return;
/*      */     } 
/* 1326 */     if (addToTable || mustBeAddedToTable) {
/*      */       
/* 1328 */       boolean canAddAttributeToTable = (mustBeAddedToTable || canAddAttributeToTable(s.length()));
/*      */ 
/*      */ 
/*      */       
/* 1332 */       int index = canAddAttributeToTable ? this._v.attributeValue.obtainIndex(s) : this._v.attributeValue.get(s);
/*      */ 
/*      */ 
/*      */       
/* 1336 */       if (index != -1) {
/*      */         
/* 1338 */         encodeNonZeroIntegerOnSecondBitFirstBitOne(index); return;
/*      */       } 
/* 1340 */       if (canAddAttributeToTable) {
/*      */         
/* 1342 */         this._b = 96;
/*      */       }
/*      */       else {
/*      */         
/* 1346 */         this._b = 32;
/*      */       } 
/*      */     } else {
/* 1349 */       this._b = 32;
/*      */     } 
/*      */ 
/*      */     
/* 1353 */     write(this._b | (id & 0xF0) >> 4);
/*      */     
/* 1355 */     this._b = (id & 0xF) << 4;
/*      */     
/* 1357 */     int length = s.length();
/* 1358 */     int octetPairLength = length / 2;
/* 1359 */     int octetSingleLength = length % 2;
/* 1360 */     encodeNonZeroOctetStringLengthOnFifthBit(octetPairLength + octetSingleLength);
/* 1361 */     encodeNonEmptyFourBitCharacterString(table, s.toCharArray(), 0, octetPairLength, octetSingleLength);
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
/*      */   protected final void encodeNonIdentifyingStringOnFirstBit(String URI, int id, Object data) throws FastInfosetException, IOException {
/* 1378 */     if (URI != null) {
/* 1379 */       id = this._v.encodingAlgorithm.get(URI);
/* 1380 */       if (id == -1) {
/* 1381 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.EncodingAlgorithmURI", new Object[] { URI }));
/*      */       }
/* 1383 */       id += 32;
/*      */       
/* 1385 */       EncodingAlgorithm ea = (EncodingAlgorithm)this._registeredEncodingAlgorithms.get(URI);
/* 1386 */       if (ea != null) {
/* 1387 */         encodeAIIObjectAlgorithmData(id, data, ea);
/*      */       }
/* 1389 */       else if (data instanceof byte[]) {
/* 1390 */         byte[] d = (byte[])data;
/* 1391 */         encodeAIIOctetAlgorithmData(id, d, 0, d.length);
/*      */       } else {
/* 1393 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.nullEncodingAlgorithmURI"));
/*      */       }
/*      */     
/* 1396 */     } else if (id <= 9) {
/* 1397 */       int length = 0;
/* 1398 */       switch (id) {
/*      */         case 0:
/*      */         case 1:
/* 1401 */           length = ((byte[])data).length;
/*      */           break;
/*      */         case 2:
/* 1404 */           length = ((short[])data).length;
/*      */           break;
/*      */         case 3:
/* 1407 */           length = ((int[])data).length;
/*      */           break;
/*      */         case 4:
/*      */         case 8:
/* 1411 */           length = ((long[])data).length;
/*      */           break;
/*      */         case 5:
/* 1414 */           length = ((boolean[])data).length;
/*      */           break;
/*      */         case 6:
/* 1417 */           length = ((float[])data).length;
/*      */           break;
/*      */         case 7:
/* 1420 */           length = ((double[])data).length;
/*      */           break;
/*      */         case 9:
/* 1423 */           throw new UnsupportedOperationException(CommonResourceBundle.getInstance().getString("message.CDATA"));
/*      */         default:
/* 1425 */           throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.UnsupportedBuiltInAlgorithm", new Object[] { Integer.valueOf(id) }));
/*      */       } 
/* 1427 */       encodeAIIBuiltInAlgorithmData(id, data, 0, length);
/* 1428 */     } else if (id >= 32) {
/* 1429 */       if (data instanceof byte[]) {
/* 1430 */         byte[] d = (byte[])data;
/* 1431 */         encodeAIIOctetAlgorithmData(id, d, 0, d.length);
/*      */       } else {
/* 1433 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.nullEncodingAlgorithmURI"));
/*      */       } 
/*      */     } else {
/* 1436 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.identifiers10to31Reserved"));
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeAIIOctetAlgorithmData(int id, byte[] d, int offset, int length) throws IOException {
/* 1452 */     write(0x30 | (id & 0xF0) >> 4);
/*      */ 
/*      */ 
/*      */     
/* 1456 */     this._b = (id & 0xF) << 4;
/*      */ 
/*      */     
/* 1459 */     encodeNonZeroOctetStringLengthOnFifthBit(length);
/*      */     
/* 1461 */     write(d, offset, length);
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
/*      */   protected final void encodeAIIObjectAlgorithmData(int id, Object data, EncodingAlgorithm ea) throws FastInfosetException, IOException {
/* 1476 */     write(0x30 | (id & 0xF0) >> 4);
/*      */ 
/*      */ 
/*      */     
/* 1480 */     this._b = (id & 0xF) << 4;
/*      */     
/* 1482 */     this._encodingBufferOutputStream.reset();
/* 1483 */     ea.encodeToOutputStream(data, this._encodingBufferOutputStream);
/* 1484 */     encodeNonZeroOctetStringLengthOnFifthBit(this._encodingBufferIndex);
/* 1485 */     write(this._encodingBuffer, this._encodingBufferIndex);
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
/*      */   protected final void encodeAIIBuiltInAlgorithmData(int id, Object data, int offset, int length) throws IOException {
/* 1502 */     write(0x30 | (id & 0xF0) >> 4);
/*      */ 
/*      */ 
/*      */     
/* 1506 */     this._b = (id & 0xF) << 4;
/*      */     
/* 1508 */     int octetLength = BuiltInEncodingAlgorithmFactory.getAlgorithm(id).getOctetLengthFromPrimitiveLength(length);
/*      */ 
/*      */     
/* 1511 */     encodeNonZeroOctetStringLengthOnFifthBit(octetLength);
/*      */     
/* 1513 */     ensureSize(octetLength);
/* 1514 */     BuiltInEncodingAlgorithmFactory.getAlgorithm(id).encodeToBytes(data, offset, length, this._octetBuffer, this._octetBufferIndex);
/*      */     
/* 1516 */     this._octetBufferIndex += octetLength;
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
/*      */   protected final void encodeNonIdentifyingStringOnThirdBit(char[] ch, int offset, int length, CharArrayIntMap map, boolean addToTable, boolean clone) throws IOException {
/* 1536 */     if (addToTable) {
/*      */       
/* 1538 */       boolean canAddCharacterContentToTable = canAddCharacterContentToTable(length, map);
/*      */ 
/*      */ 
/*      */       
/* 1542 */       int index = canAddCharacterContentToTable ? map.obtainIndex(ch, offset, length, clone) : map.get(ch, offset, length);
/*      */ 
/*      */ 
/*      */       
/* 1546 */       if (index != -1) {
/*      */         
/* 1548 */         this._b = 160;
/* 1549 */         encodeNonZeroIntegerOnFourthBit(index);
/* 1550 */       } else if (canAddCharacterContentToTable) {
/*      */         
/* 1552 */         this._b = 0x10 | this._nonIdentifyingStringOnThirdBitCES;
/*      */         
/* 1554 */         encodeNonEmptyCharacterStringOnSeventhBit(ch, offset, length);
/*      */       } else {
/*      */         
/* 1557 */         this._b = this._nonIdentifyingStringOnThirdBitCES;
/* 1558 */         encodeNonEmptyCharacterStringOnSeventhBit(ch, offset, length);
/*      */       } 
/*      */     } else {
/*      */       
/* 1562 */       this._b = this._nonIdentifyingStringOnThirdBitCES;
/* 1563 */       encodeNonEmptyCharacterStringOnSeventhBit(ch, offset, length);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeNonIdentifyingStringOnThirdBit(String URI, int id, Object data) throws FastInfosetException, IOException {
/* 1581 */     if (URI != null) {
/* 1582 */       id = this._v.encodingAlgorithm.get(URI);
/* 1583 */       if (id == -1) {
/* 1584 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.EncodingAlgorithmURI", new Object[] { URI }));
/*      */       }
/* 1586 */       id += 32;
/*      */       
/* 1588 */       EncodingAlgorithm ea = (EncodingAlgorithm)this._registeredEncodingAlgorithms.get(URI);
/* 1589 */       if (ea != null) {
/* 1590 */         encodeCIIObjectAlgorithmData(id, data, ea);
/*      */       }
/* 1592 */       else if (data instanceof byte[]) {
/* 1593 */         byte[] d = (byte[])data;
/* 1594 */         encodeCIIOctetAlgorithmData(id, d, 0, d.length);
/*      */       } else {
/* 1596 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.nullEncodingAlgorithmURI"));
/*      */       }
/*      */     
/* 1599 */     } else if (id <= 9) {
/* 1600 */       int length = 0;
/* 1601 */       switch (id) {
/*      */         case 0:
/*      */         case 1:
/* 1604 */           length = ((byte[])data).length;
/*      */           break;
/*      */         case 2:
/* 1607 */           length = ((short[])data).length;
/*      */           break;
/*      */         case 3:
/* 1610 */           length = ((int[])data).length;
/*      */           break;
/*      */         case 4:
/*      */         case 8:
/* 1614 */           length = ((long[])data).length;
/*      */           break;
/*      */         case 5:
/* 1617 */           length = ((boolean[])data).length;
/*      */           break;
/*      */         case 6:
/* 1620 */           length = ((float[])data).length;
/*      */           break;
/*      */         case 7:
/* 1623 */           length = ((double[])data).length;
/*      */           break;
/*      */         case 9:
/* 1626 */           throw new UnsupportedOperationException(CommonResourceBundle.getInstance().getString("message.CDATA"));
/*      */         default:
/* 1628 */           throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.UnsupportedBuiltInAlgorithm", new Object[] { Integer.valueOf(id) }));
/*      */       } 
/* 1630 */       encodeCIIBuiltInAlgorithmData(id, data, 0, length);
/* 1631 */     } else if (id >= 32) {
/* 1632 */       if (data instanceof byte[]) {
/* 1633 */         byte[] d = (byte[])data;
/* 1634 */         encodeCIIOctetAlgorithmData(id, d, 0, d.length);
/*      */       } else {
/* 1636 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.nullEncodingAlgorithmURI"));
/*      */       } 
/*      */     } else {
/* 1639 */       throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.identifiers10to31Reserved"));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeNonIdentifyingStringOnThirdBit(String URI, int id, byte[] d, int offset, int length) throws FastInfosetException, IOException {
/* 1658 */     if (URI != null) {
/* 1659 */       id = this._v.encodingAlgorithm.get(URI);
/* 1660 */       if (id == -1) {
/* 1661 */         throw new EncodingAlgorithmException(CommonResourceBundle.getInstance().getString("message.EncodingAlgorithmURI", new Object[] { URI }));
/*      */       }
/* 1663 */       id += 32;
/*      */     } 
/*      */     
/* 1666 */     encodeCIIOctetAlgorithmData(id, d, offset, length);
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
/*      */   protected final void encodeCIIOctetAlgorithmData(int id, byte[] d, int offset, int length) throws IOException {
/* 1681 */     write(0x8C | (id & 0xC0) >> 6);
/*      */ 
/*      */ 
/*      */     
/* 1685 */     this._b = (id & 0x3F) << 2;
/*      */ 
/*      */     
/* 1688 */     encodeNonZeroOctetStringLengthOnSenventhBit(length);
/*      */     
/* 1690 */     write(d, offset, length);
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
/*      */   protected final void encodeCIIObjectAlgorithmData(int id, Object data, EncodingAlgorithm ea) throws FastInfosetException, IOException {
/* 1705 */     write(0x8C | (id & 0xC0) >> 6);
/*      */ 
/*      */ 
/*      */     
/* 1709 */     this._b = (id & 0x3F) << 2;
/*      */     
/* 1711 */     this._encodingBufferOutputStream.reset();
/* 1712 */     ea.encodeToOutputStream(data, this._encodingBufferOutputStream);
/* 1713 */     encodeNonZeroOctetStringLengthOnSenventhBit(this._encodingBufferIndex);
/* 1714 */     write(this._encodingBuffer, this._encodingBufferIndex);
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
/*      */   protected final void encodeCIIBuiltInAlgorithmData(int id, Object data, int offset, int length) throws FastInfosetException, IOException {
/* 1731 */     write(0x8C | (id & 0xC0) >> 6);
/*      */ 
/*      */ 
/*      */     
/* 1735 */     this._b = (id & 0x3F) << 2;
/*      */     
/* 1737 */     int octetLength = BuiltInEncodingAlgorithmFactory.getAlgorithm(id).getOctetLengthFromPrimitiveLength(length);
/*      */ 
/*      */     
/* 1740 */     encodeNonZeroOctetStringLengthOnSenventhBit(octetLength);
/*      */     
/* 1742 */     ensureSize(octetLength);
/* 1743 */     BuiltInEncodingAlgorithmFactory.getAlgorithm(id).encodeToBytes(data, offset, length, this._octetBuffer, this._octetBufferIndex);
/*      */     
/* 1745 */     this._octetBufferIndex += octetLength;
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
/*      */   protected final void encodeCIIBuiltInAlgorithmDataAsCDATA(char[] ch, int offset, int length) throws FastInfosetException, IOException {
/* 1759 */     write(140);
/*      */ 
/*      */     
/* 1762 */     this._b = 36;
/*      */ 
/*      */     
/* 1765 */     length = encodeUTF8String(ch, offset, length);
/* 1766 */     encodeNonZeroOctetStringLengthOnSenventhBit(length);
/* 1767 */     write(this._encodingBuffer, length);
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
/*      */   protected final void encodeIdentifyingNonEmptyStringOnFirstBit(String s, StringIntMap map) throws IOException {
/* 1779 */     int index = map.obtainIndex(s);
/* 1780 */     if (index == -1) {
/*      */       
/* 1782 */       encodeNonEmptyOctetStringOnSecondBit(s);
/*      */     } else {
/*      */       
/* 1785 */       encodeNonZeroIntegerOnSecondBitFirstBitOne(index);
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
/*      */   protected final void encodeNonEmptyOctetStringOnSecondBit(String s) throws IOException {
/* 1797 */     int length = encodeUTF8String(s);
/* 1798 */     encodeNonZeroOctetStringLengthOnSecondBit(length);
/* 1799 */     write(this._encodingBuffer, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeNonZeroOctetStringLengthOnSecondBit(int length) throws IOException {
/* 1809 */     if (length < 65) {
/*      */       
/* 1811 */       write(length - 1);
/* 1812 */     } else if (length < 321) {
/*      */       
/* 1814 */       write(64);
/* 1815 */       write(length - 65);
/*      */     } else {
/*      */       
/* 1818 */       write(96);
/* 1819 */       length -= 321;
/* 1820 */       write(length >>> 24);
/* 1821 */       write(length >> 16 & 0xFF);
/* 1822 */       write(length >> 8 & 0xFF);
/* 1823 */       write(length & 0xFF);
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
/*      */   protected final void encodeNonEmptyCharacterStringOnFifthBit(String s) throws IOException {
/* 1835 */     int length = this._encodingStringsAsUtf8 ? encodeUTF8String(s) : encodeUtf16String(s);
/* 1836 */     encodeNonZeroOctetStringLengthOnFifthBit(length);
/* 1837 */     write(this._encodingBuffer, length);
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
/*      */   protected final void encodeNonEmptyCharacterStringOnFifthBit(char[] ch, int offset, int length) throws IOException {
/* 1850 */     length = this._encodingStringsAsUtf8 ? encodeUTF8String(ch, offset, length) : encodeUtf16String(ch, offset, length);
/* 1851 */     encodeNonZeroOctetStringLengthOnFifthBit(length);
/* 1852 */     write(this._encodingBuffer, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeNonZeroOctetStringLengthOnFifthBit(int length) throws IOException {
/* 1863 */     if (length < 9) {
/*      */       
/* 1865 */       write(this._b | length - 1);
/* 1866 */     } else if (length < 265) {
/*      */       
/* 1868 */       write(this._b | 0x8);
/* 1869 */       write(length - 9);
/*      */     } else {
/*      */       
/* 1872 */       write(this._b | 0xC);
/* 1873 */       length -= 265;
/* 1874 */       write(length >>> 24);
/* 1875 */       write(length >> 16 & 0xFF);
/* 1876 */       write(length >> 8 & 0xFF);
/* 1877 */       write(length & 0xFF);
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
/*      */   
/*      */   protected final void encodeNonEmptyCharacterStringOnSeventhBit(char[] ch, int offset, int length) throws IOException {
/* 1891 */     length = this._encodingStringsAsUtf8 ? encodeUTF8String(ch, offset, length) : encodeUtf16String(ch, offset, length);
/* 1892 */     encodeNonZeroOctetStringLengthOnSenventhBit(length);
/* 1893 */     write(this._encodingBuffer, length);
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
/*      */   protected final void encodeNonEmptyFourBitCharacterStringOnSeventhBit(int[] table, char[] ch, int offset, int length) throws FastInfosetException, IOException {
/* 1908 */     int octetPairLength = length / 2;
/* 1909 */     int octetSingleLength = length % 2;
/*      */ 
/*      */     
/* 1912 */     encodeNonZeroOctetStringLengthOnSenventhBit(octetPairLength + octetSingleLength);
/* 1913 */     encodeNonEmptyFourBitCharacterString(table, ch, offset, octetPairLength, octetSingleLength);
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void encodeNonEmptyFourBitCharacterString(int[] table, char[] ch, int offset, int octetPairLength, int octetSingleLength) throws FastInfosetException, IOException {
/* 1918 */     ensureSize(octetPairLength + octetSingleLength);
/*      */     
/* 1920 */     int v = 0;
/* 1921 */     for (int i = 0; i < octetPairLength; i++) {
/* 1922 */       v = table[ch[offset++]] << 4 | table[ch[offset++]];
/* 1923 */       if (v < 0) {
/* 1924 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.characterOutofAlphabetRange"));
/*      */       }
/* 1926 */       this._octetBuffer[this._octetBufferIndex++] = (byte)v;
/*      */     } 
/*      */     
/* 1929 */     if (octetSingleLength == 1) {
/* 1930 */       v = table[ch[offset]] << 4 | 0xF;
/* 1931 */       if (v < 0) {
/* 1932 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.characterOutofAlphabetRange"));
/*      */       }
/* 1934 */       this._octetBuffer[this._octetBufferIndex++] = (byte)v;
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
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeNonEmptyNBitCharacterStringOnSeventhBit(String alphabet, char[] ch, int offset, int length) throws FastInfosetException, IOException {
/* 1950 */     int bitsPerCharacter = 1;
/* 1951 */     while (1 << bitsPerCharacter <= alphabet.length()) {
/* 1952 */       bitsPerCharacter++;
/*      */     }
/*      */     
/* 1955 */     int bits = length * bitsPerCharacter;
/* 1956 */     int octets = bits / 8;
/* 1957 */     int bitsOfLastOctet = bits % 8;
/* 1958 */     int totalOctets = octets + ((bitsOfLastOctet > 0) ? 1 : 0);
/*      */ 
/*      */     
/* 1961 */     encodeNonZeroOctetStringLengthOnSenventhBit(totalOctets);
/*      */     
/* 1963 */     resetBits();
/* 1964 */     ensureSize(totalOctets);
/* 1965 */     int v = 0;
/* 1966 */     for (int i = 0; i < length; i++) {
/* 1967 */       char c = ch[offset + i];
/*      */       
/* 1969 */       for (v = 0; v < alphabet.length() && 
/* 1970 */         c != alphabet.charAt(v); v++);
/*      */ 
/*      */ 
/*      */       
/* 1974 */       if (v == alphabet.length()) {
/* 1975 */         throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.characterOutofAlphabetRange"));
/*      */       }
/* 1977 */       writeBits(bitsPerCharacter, v);
/*      */     } 
/*      */     
/* 1980 */     if (bitsOfLastOctet > 0) {
/* 1981 */       this._b |= (1 << 8 - bitsOfLastOctet) - 1;
/* 1982 */       write(this._b);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final void resetBits() {
/* 1989 */     this._bitsLeftInOctet = 8;
/* 1990 */     this._b = 0;
/*      */   }
/*      */   
/*      */   private final void writeBits(int bits, int v) throws IOException {
/* 1994 */     while (bits > 0) {
/* 1995 */       int bit = ((v & 1 << --bits) > 0) ? 1 : 0;
/* 1996 */       this._b |= bit << --this._bitsLeftInOctet;
/* 1997 */       if (this._bitsLeftInOctet == 0) {
/* 1998 */         write(this._b);
/* 1999 */         this._bitsLeftInOctet = 8;
/* 2000 */         this._b = 0;
/*      */       } 
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
/*      */   protected final void encodeNonZeroOctetStringLengthOnSenventhBit(int length) throws IOException {
/* 2013 */     if (length < 3) {
/*      */       
/* 2015 */       write(this._b | length - 1);
/* 2016 */     } else if (length < 259) {
/*      */       
/* 2018 */       write(this._b | 0x2);
/* 2019 */       write(length - 3);
/*      */     } else {
/*      */       
/* 2022 */       write(this._b | 0x3);
/* 2023 */       length -= 259;
/* 2024 */       write(length >>> 24);
/* 2025 */       write(length >> 16 & 0xFF);
/* 2026 */       write(length >> 8 & 0xFF);
/* 2027 */       write(length & 0xFF);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeNonZeroIntegerOnSecondBitFirstBitOne(int i) throws IOException {
/* 2045 */     if (i < 64) {
/*      */       
/* 2047 */       write(0x80 | i);
/* 2048 */     } else if (i < 8256) {
/*      */       
/* 2050 */       i -= 64;
/* 2051 */       this._b = 0xC0 | i >> 8;
/*      */       
/* 2053 */       write(this._b);
/* 2054 */       write(i & 0xFF);
/* 2055 */     } else if (i < 1048576) {
/*      */       
/* 2057 */       i -= 8256;
/* 2058 */       this._b = 0xE0 | i >> 16;
/*      */       
/* 2060 */       write(this._b);
/* 2061 */       write(i >> 8 & 0xFF);
/* 2062 */       write(i & 0xFF);
/*      */     } else {
/* 2064 */       throw new IOException(CommonResourceBundle.getInstance().getString("message.integerMaxSize", new Object[] { Integer.valueOf(1048576) }));
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void encodeNonZeroIntegerOnSecondBitFirstBitZero(int i) throws IOException {
/* 2084 */     if (i < 64) {
/*      */       
/* 2086 */       write(i);
/* 2087 */     } else if (i < 8256) {
/*      */       
/* 2089 */       i -= 64;
/* 2090 */       this._b = 0x40 | i >> 8;
/* 2091 */       write(this._b);
/* 2092 */       write(i & 0xFF);
/*      */     } else {
/*      */       
/* 2095 */       i -= 8256;
/* 2096 */       this._b = 0x60 | i >> 16;
/* 2097 */       write(this._b);
/* 2098 */       write(i >> 8 & 0xFF);
/* 2099 */       write(i & 0xFF);
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
/*      */   protected final void encodeNonZeroIntegerOnThirdBit(int i) throws IOException {
/* 2112 */     if (i < 32) {
/*      */       
/* 2114 */       write(this._b | i);
/* 2115 */     } else if (i < 2080) {
/*      */       
/* 2117 */       i -= 32;
/* 2118 */       this._b |= 0x20 | i >> 8;
/* 2119 */       write(this._b);
/* 2120 */       write(i & 0xFF);
/* 2121 */     } else if (i < 526368) {
/*      */       
/* 2123 */       i -= 2080;
/* 2124 */       this._b |= 0x28 | i >> 16;
/* 2125 */       write(this._b);
/* 2126 */       write(i >> 8 & 0xFF);
/* 2127 */       write(i & 0xFF);
/*      */     } else {
/*      */       
/* 2130 */       i -= 526368;
/* 2131 */       this._b |= 0x30;
/* 2132 */       write(this._b);
/* 2133 */       write(i >> 16);
/* 2134 */       write(i >> 8 & 0xFF);
/* 2135 */       write(i & 0xFF);
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
/*      */   protected final void encodeNonZeroIntegerOnFourthBit(int i) throws IOException {
/* 2148 */     if (i < 16) {
/*      */       
/* 2150 */       write(this._b | i);
/* 2151 */     } else if (i < 1040) {
/*      */       
/* 2153 */       i -= 16;
/* 2154 */       this._b |= 0x10 | i >> 8;
/* 2155 */       write(this._b);
/* 2156 */       write(i & 0xFF);
/* 2157 */     } else if (i < 263184) {
/*      */       
/* 2159 */       i -= 1040;
/* 2160 */       this._b |= 0x14 | i >> 16;
/* 2161 */       write(this._b);
/* 2162 */       write(i >> 8 & 0xFF);
/* 2163 */       write(i & 0xFF);
/*      */     } else {
/*      */       
/* 2166 */       i -= 263184;
/* 2167 */       this._b |= 0x18;
/* 2168 */       write(this._b);
/* 2169 */       write(i >> 16);
/* 2170 */       write(i >> 8 & 0xFF);
/* 2171 */       write(i & 0xFF);
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
/*      */   protected final void encodeNonEmptyUTF8StringAsOctetString(int b, String s, int[] constants) throws IOException {
/* 2184 */     char[] ch = s.toCharArray();
/* 2185 */     encodeNonEmptyUTF8StringAsOctetString(b, ch, 0, ch.length, constants);
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
/*      */   protected final void encodeNonEmptyUTF8StringAsOctetString(int b, char[] ch, int offset, int length, int[] constants) throws IOException {
/* 2200 */     length = encodeUTF8String(ch, offset, length);
/* 2201 */     encodeNonZeroOctetStringLength(b, length, constants);
/* 2202 */     write(this._encodingBuffer, length);
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
/*      */   protected final void encodeNonZeroOctetStringLength(int b, int length, int[] constants) throws IOException {
/* 2215 */     if (length < constants[0]) {
/* 2216 */       write(b | length - 1);
/* 2217 */     } else if (length < constants[1]) {
/* 2218 */       write(b | constants[2]);
/* 2219 */       write(length - constants[0]);
/*      */     } else {
/* 2221 */       write(b | constants[3]);
/* 2222 */       length -= constants[1];
/* 2223 */       write(length >>> 24);
/* 2224 */       write(length >> 16 & 0xFF);
/* 2225 */       write(length >> 8 & 0xFF);
/* 2226 */       write(length & 0xFF);
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
/*      */   protected final void encodeNonZeroInteger(int b, int i, int[] constants) throws IOException {
/* 2239 */     if (i < constants[0]) {
/* 2240 */       write(b | i);
/* 2241 */     } else if (i < constants[1]) {
/* 2242 */       i -= constants[0];
/* 2243 */       write(b | constants[3] | i >> 8);
/* 2244 */       write(i & 0xFF);
/* 2245 */     } else if (i < constants[2]) {
/* 2246 */       i -= constants[1];
/* 2247 */       write(b | constants[4] | i >> 16);
/* 2248 */       write(i >> 8 & 0xFF);
/* 2249 */       write(i & 0xFF);
/* 2250 */     } else if (i < 1048576) {
/* 2251 */       i -= constants[2];
/* 2252 */       write(b | constants[5]);
/* 2253 */       write(i >> 16);
/* 2254 */       write(i >> 8 & 0xFF);
/* 2255 */       write(i & 0xFF);
/*      */     } else {
/* 2257 */       throw new IOException(CommonResourceBundle.getInstance().getString("message.integerMaxSize", new Object[] { Integer.valueOf(1048576) }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void mark() {
/* 2265 */     this._markIndex = this._octetBufferIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void resetMark() {
/* 2272 */     this._markIndex = -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final boolean hasMark() {
/* 2280 */     return (this._markIndex != -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void write(int i) throws IOException {
/* 2287 */     if (this._octetBufferIndex < this._octetBuffer.length) {
/* 2288 */       this._octetBuffer[this._octetBufferIndex++] = (byte)i;
/*      */     }
/* 2290 */     else if (this._markIndex == -1) {
/* 2291 */       this._s.write(this._octetBuffer);
/* 2292 */       this._octetBufferIndex = 1;
/* 2293 */       this._octetBuffer[0] = (byte)i;
/*      */     } else {
/* 2295 */       resize(this._octetBuffer.length * 3 / 2);
/* 2296 */       this._octetBuffer[this._octetBufferIndex++] = (byte)i;
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
/*      */   protected final void write(byte[] b, int length) throws IOException {
/* 2308 */     write(b, 0, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final void write(byte[] b, int offset, int length) throws IOException {
/* 2319 */     if (this._octetBufferIndex + length < this._octetBuffer.length) {
/* 2320 */       System.arraycopy(b, offset, this._octetBuffer, this._octetBufferIndex, length);
/* 2321 */       this._octetBufferIndex += length;
/*      */     }
/* 2323 */     else if (this._markIndex == -1) {
/* 2324 */       this._s.write(this._octetBuffer, 0, this._octetBufferIndex);
/* 2325 */       this._s.write(b, offset, length);
/* 2326 */       this._octetBufferIndex = 0;
/*      */     } else {
/* 2328 */       resize((this._octetBuffer.length + length) * 3 / 2 + 1);
/* 2329 */       System.arraycopy(b, offset, this._octetBuffer, this._octetBufferIndex, length);
/* 2330 */       this._octetBufferIndex += length;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void ensureSize(int length) {
/* 2336 */     if (this._octetBufferIndex + length > this._octetBuffer.length) {
/* 2337 */       resize((this._octetBufferIndex + length) * 3 / 2 + 1);
/*      */     }
/*      */   }
/*      */   
/*      */   private void resize(int length) {
/* 2342 */     byte[] b = new byte[length];
/* 2343 */     System.arraycopy(this._octetBuffer, 0, b, 0, this._octetBufferIndex);
/* 2344 */     this._octetBuffer = b;
/*      */   }
/*      */   
/*      */   private void _flush() throws IOException {
/* 2348 */     if (this._octetBufferIndex > 0) {
/* 2349 */       this._s.write(this._octetBuffer, 0, this._octetBufferIndex);
/* 2350 */       this._octetBufferIndex = 0;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected Encoder() {
/* 2355 */     this._encodingBufferOutputStream = new EncodingBufferOutputStream();
/*      */     
/* 2357 */     this._encodingBuffer = new byte[512]; setCharacterEncodingScheme(_characterEncodingSchemeSystemDefault); } protected Encoder(boolean useLocalNameAsKeyForQualifiedNameLookup) { this._encodingBufferOutputStream = new EncodingBufferOutputStream(); this._encodingBuffer = new byte[512];
/*      */     setCharacterEncodingScheme(_characterEncodingSchemeSystemDefault);
/*      */     this._useLocalNameAsKeyForQualifiedNameLookup = useLocalNameAsKeyForQualifiedNameLookup; }
/*      */   
/*      */   private class EncodingBufferOutputStream extends OutputStream { private EncodingBufferOutputStream() {}
/*      */     
/*      */     public void write(int b) throws IOException {
/* 2364 */       if (Encoder.this._encodingBufferIndex < Encoder.this._encodingBuffer.length) {
/* 2365 */         Encoder.this._encodingBuffer[Encoder.this._encodingBufferIndex++] = (byte)b;
/*      */       } else {
/* 2367 */         byte[] newbuf = new byte[Math.max(Encoder.this._encodingBuffer.length << 1, Encoder.this._encodingBufferIndex)];
/* 2368 */         System.arraycopy(Encoder.this._encodingBuffer, 0, newbuf, 0, Encoder.this._encodingBufferIndex);
/* 2369 */         Encoder.this._encodingBuffer = newbuf;
/*      */         
/* 2371 */         Encoder.this._encodingBuffer[Encoder.this._encodingBufferIndex++] = (byte)b;
/*      */       } 
/*      */     }
/*      */     
/*      */     public void write(byte[] b, int off, int len) throws IOException {
/* 2376 */       if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0)
/*      */       {
/* 2378 */         throw new IndexOutOfBoundsException(); } 
/* 2379 */       if (len == 0) {
/*      */         return;
/*      */       }
/* 2382 */       int newoffset = Encoder.this._encodingBufferIndex + len;
/* 2383 */       if (newoffset > Encoder.this._encodingBuffer.length) {
/* 2384 */         byte[] newbuf = new byte[Math.max(Encoder.this._encodingBuffer.length << 1, newoffset)];
/* 2385 */         System.arraycopy(Encoder.this._encodingBuffer, 0, newbuf, 0, Encoder.this._encodingBufferIndex);
/* 2386 */         Encoder.this._encodingBuffer = newbuf;
/*      */       } 
/* 2388 */       System.arraycopy(b, off, Encoder.this._encodingBuffer, Encoder.this._encodingBufferIndex, len);
/* 2389 */       Encoder.this._encodingBufferIndex = newoffset;
/*      */     }
/*      */     
/*      */     public int getLength() {
/* 2393 */       return Encoder.this._encodingBufferIndex;
/*      */     }
/*      */     
/*      */     public void reset() {
/* 2397 */       Encoder.this._encodingBufferIndex = 0;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int encodeUTF8String(String s) throws IOException {
/* 2407 */     int length = s.length();
/* 2408 */     if (length < this._charBuffer.length) {
/* 2409 */       s.getChars(0, length, this._charBuffer, 0);
/* 2410 */       return encodeUTF8String(this._charBuffer, 0, length);
/*      */     } 
/* 2412 */     char[] ch = s.toCharArray();
/* 2413 */     return encodeUTF8String(ch, 0, length);
/*      */   }
/*      */ 
/*      */   
/*      */   private void ensureEncodingBufferSizeForUtf8String(int length) {
/* 2418 */     int newLength = 4 * length;
/* 2419 */     if (this._encodingBuffer.length < newLength) {
/* 2420 */       this._encodingBuffer = new byte[newLength];
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
/*      */   protected final int encodeUTF8String(char[] ch, int offset, int length) throws IOException {
/* 2432 */     int bpos = 0;
/*      */ 
/*      */     
/* 2435 */     ensureEncodingBufferSizeForUtf8String(length);
/*      */     
/* 2437 */     int end = offset + length;
/*      */     
/* 2439 */     while (end != offset) {
/* 2440 */       int c = ch[offset++];
/* 2441 */       if (c < 128) {
/*      */         
/* 2443 */         this._encodingBuffer[bpos++] = (byte)c; continue;
/* 2444 */       }  if (c < 2048) {
/*      */         
/* 2446 */         this._encodingBuffer[bpos++] = (byte)(0xC0 | c >> 6);
/*      */         
/* 2448 */         this._encodingBuffer[bpos++] = (byte)(0x80 | c & 0x3F); continue;
/*      */       } 
/* 2450 */       if (c <= 65535) {
/* 2451 */         if (!XMLChar.isHighSurrogate(c) && !XMLChar.isLowSurrogate(c)) {
/*      */           
/* 2453 */           this._encodingBuffer[bpos++] = (byte)(0xE0 | c >> 12);
/*      */           
/* 2455 */           this._encodingBuffer[bpos++] = (byte)(0x80 | c >> 6 & 0x3F);
/*      */           
/* 2457 */           this._encodingBuffer[bpos++] = (byte)(0x80 | c & 0x3F);
/*      */           
/*      */           continue;
/*      */         } 
/* 2461 */         encodeCharacterAsUtf8FourByte(c, ch, offset, end, bpos);
/* 2462 */         bpos += 4;
/* 2463 */         offset++;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2468 */     return bpos;
/*      */   }
/*      */   
/*      */   private void encodeCharacterAsUtf8FourByte(int c, char[] ch, int chpos, int chend, int bpos) throws IOException {
/* 2472 */     if (chpos == chend) {
/* 2473 */       throw new IOException("");
/*      */     }
/*      */     
/* 2476 */     char d = ch[chpos];
/* 2477 */     if (!XMLChar.isLowSurrogate(d)) {
/* 2478 */       throw new IOException("");
/*      */     }
/*      */     
/* 2481 */     int uc = ((c & 0x3FF) << 10 | d & 0x3FF) + 65536;
/* 2482 */     if (uc < 0 || uc >= 2097152) {
/* 2483 */       throw new IOException("");
/*      */     }
/*      */     
/* 2486 */     this._encodingBuffer[bpos++] = (byte)(0xF0 | uc >> 18);
/* 2487 */     this._encodingBuffer[bpos++] = (byte)(0x80 | uc >> 12 & 0x3F);
/* 2488 */     this._encodingBuffer[bpos++] = (byte)(0x80 | uc >> 6 & 0x3F);
/* 2489 */     this._encodingBuffer[bpos++] = (byte)(0x80 | uc & 0x3F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final int encodeUtf16String(String s) throws IOException {
/* 2498 */     int length = s.length();
/* 2499 */     if (length < this._charBuffer.length) {
/* 2500 */       s.getChars(0, length, this._charBuffer, 0);
/* 2501 */       return encodeUtf16String(this._charBuffer, 0, length);
/*      */     } 
/* 2503 */     char[] ch = s.toCharArray();
/* 2504 */     return encodeUtf16String(ch, 0, length);
/*      */   }
/*      */ 
/*      */   
/*      */   private void ensureEncodingBufferSizeForUtf16String(int length) {
/* 2509 */     int newLength = 2 * length;
/* 2510 */     if (this._encodingBuffer.length < newLength) {
/* 2511 */       this._encodingBuffer = new byte[newLength];
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
/*      */   protected final int encodeUtf16String(char[] ch, int offset, int length) throws IOException {
/* 2523 */     int byteLength = 0;
/*      */ 
/*      */     
/* 2526 */     ensureEncodingBufferSizeForUtf16String(length);
/*      */     
/* 2528 */     int n = offset + length;
/* 2529 */     for (int i = offset; i < n; i++) {
/* 2530 */       int c = ch[i];
/* 2531 */       this._encodingBuffer[byteLength++] = (byte)(c >> 8);
/* 2532 */       this._encodingBuffer[byteLength++] = (byte)(c & 0xFF);
/*      */     } 
/*      */     
/* 2535 */     return byteLength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getPrefixFromQualifiedName(String qName) {
/* 2545 */     int i = qName.indexOf(':');
/* 2546 */     String prefix = "";
/* 2547 */     if (i != -1) {
/* 2548 */       prefix = qName.substring(0, i);
/*      */     }
/* 2550 */     return prefix;
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
/*      */   public static boolean isWhiteSpace(char[] ch, int start, int length) {
/* 2562 */     if (!XMLChar.isSpace(ch[start])) return false;
/*      */     
/* 2564 */     int end = start + length;
/* 2565 */     while (++start < end && XMLChar.isSpace(ch[start]));
/*      */     
/* 2567 */     return (start == end);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isWhiteSpace(String s) {
/* 2577 */     if (!XMLChar.isSpace(s.charAt(0))) return false;
/*      */     
/* 2579 */     int end = s.length();
/* 2580 */     int start = 1;
/* 2581 */     while (start < end && XMLChar.isSpace(s.charAt(start++)));
/* 2582 */     return (start == end);
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\Encoder.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */