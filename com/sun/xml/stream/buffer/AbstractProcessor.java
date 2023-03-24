/*     */ package com.sun.xml.stream.buffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractProcessor
/*     */   extends AbstractCreatorProcessor
/*     */ {
/*     */   protected static final int STATE_ILLEGAL = 0;
/*     */   protected static final int STATE_DOCUMENT = 1;
/*     */   protected static final int STATE_DOCUMENT_FRAGMENT = 2;
/*     */   protected static final int STATE_ELEMENT_U_LN_QN = 3;
/*     */   protected static final int STATE_ELEMENT_P_U_LN = 4;
/*     */   protected static final int STATE_ELEMENT_U_LN = 5;
/*     */   protected static final int STATE_ELEMENT_LN = 6;
/*     */   protected static final int STATE_TEXT_AS_CHAR_ARRAY_SMALL = 7;
/*     */   protected static final int STATE_TEXT_AS_CHAR_ARRAY_MEDIUM = 8;
/*     */   protected static final int STATE_TEXT_AS_CHAR_ARRAY_COPY = 9;
/*     */   protected static final int STATE_TEXT_AS_STRING = 10;
/*     */   protected static final int STATE_TEXT_AS_OBJECT = 11;
/*     */   protected static final int STATE_COMMENT_AS_CHAR_ARRAY_SMALL = 12;
/*     */   protected static final int STATE_COMMENT_AS_CHAR_ARRAY_MEDIUM = 13;
/*     */   protected static final int STATE_COMMENT_AS_CHAR_ARRAY_COPY = 14;
/*     */   protected static final int STATE_COMMENT_AS_STRING = 15;
/*     */   protected static final int STATE_PROCESSING_INSTRUCTION = 16;
/*     */   protected static final int STATE_END = 17;
/*  67 */   private static final int[] _eiiStateTable = new int[256];
/*     */   
/*     */   protected static final int STATE_NAMESPACE_ATTRIBUTE = 1;
/*     */   protected static final int STATE_NAMESPACE_ATTRIBUTE_P = 2;
/*     */   protected static final int STATE_NAMESPACE_ATTRIBUTE_P_U = 3;
/*     */   protected static final int STATE_NAMESPACE_ATTRIBUTE_U = 4;
/*  73 */   private static final int[] _niiStateTable = new int[256];
/*     */   
/*     */   protected static final int STATE_ATTRIBUTE_U_LN_QN = 1;
/*     */   protected static final int STATE_ATTRIBUTE_P_U_LN = 2;
/*     */   protected static final int STATE_ATTRIBUTE_U_LN = 3;
/*     */   protected static final int STATE_ATTRIBUTE_LN = 4;
/*     */   protected static final int STATE_ATTRIBUTE_U_LN_QN_OBJECT = 5;
/*     */   protected static final int STATE_ATTRIBUTE_P_U_LN_OBJECT = 6;
/*     */   protected static final int STATE_ATTRIBUTE_U_LN_OBJECT = 7;
/*     */   protected static final int STATE_ATTRIBUTE_LN_OBJECT = 8;
/*  83 */   private static final int[] _aiiStateTable = new int[256];
/*     */ 
/*     */   
/*     */   protected XMLStreamBuffer _buffer;
/*     */   
/*     */   protected boolean _fragmentMode;
/*     */ 
/*     */   
/*     */   static {
/*  92 */     _eiiStateTable[16] = 1;
/*  93 */     _eiiStateTable[17] = 2;
/*  94 */     _eiiStateTable[38] = 3;
/*  95 */     _eiiStateTable[35] = 4;
/*  96 */     _eiiStateTable[34] = 5;
/*  97 */     _eiiStateTable[32] = 6;
/*  98 */     _eiiStateTable[80] = 7;
/*  99 */     _eiiStateTable[81] = 8;
/* 100 */     _eiiStateTable[84] = 9;
/* 101 */     _eiiStateTable[88] = 10;
/* 102 */     _eiiStateTable[92] = 11;
/* 103 */     _eiiStateTable[96] = 12;
/* 104 */     _eiiStateTable[97] = 13;
/* 105 */     _eiiStateTable[100] = 14;
/* 106 */     _eiiStateTable[104] = 15;
/* 107 */     _eiiStateTable[112] = 16;
/* 108 */     _eiiStateTable[144] = 17;
/*     */     
/* 110 */     _niiStateTable[64] = 1;
/* 111 */     _niiStateTable[65] = 2;
/* 112 */     _niiStateTable[67] = 3;
/* 113 */     _niiStateTable[66] = 4;
/*     */     
/* 115 */     _aiiStateTable[54] = 1;
/* 116 */     _aiiStateTable[51] = 2;
/* 117 */     _aiiStateTable[50] = 3;
/* 118 */     _aiiStateTable[48] = 4;
/* 119 */     _aiiStateTable[62] = 5;
/* 120 */     _aiiStateTable[59] = 6;
/* 121 */     _aiiStateTable[58] = 7;
/* 122 */     _aiiStateTable[56] = 8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _stringInterningFeature = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _treeCount;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setBuffer(XMLStreamBuffer buffer) {
/* 145 */     setBuffer(buffer, buffer.isFragment());
/*     */   }
/*     */   protected final void setBuffer(XMLStreamBuffer buffer, boolean fragmentMode) {
/* 148 */     this._buffer = buffer;
/* 149 */     this._fragmentMode = fragmentMode;
/*     */     
/* 151 */     this._currentStructureFragment = this._buffer.getStructure();
/* 152 */     this._structure = this._currentStructureFragment.getArray();
/* 153 */     this._structurePtr = this._buffer.getStructurePtr();
/*     */     
/* 155 */     this._currentStructureStringFragment = this._buffer.getStructureStrings();
/* 156 */     this._structureStrings = this._currentStructureStringFragment.getArray();
/* 157 */     this._structureStringsPtr = this._buffer.getStructureStringsPtr();
/*     */     
/* 159 */     this._currentContentCharactersBufferFragment = this._buffer.getContentCharactersBuffer();
/* 160 */     this._contentCharactersBuffer = this._currentContentCharactersBufferFragment.getArray();
/* 161 */     this._contentCharactersBufferPtr = this._buffer.getContentCharactersBufferPtr();
/*     */     
/* 163 */     this._currentContentObjectFragment = this._buffer.getContentObjects();
/* 164 */     this._contentObjects = this._currentContentObjectFragment.getArray();
/* 165 */     this._contentObjectsPtr = this._buffer.getContentObjectsPtr();
/*     */     
/* 167 */     this._stringInterningFeature = this._buffer.hasInternedStrings();
/* 168 */     this._treeCount = this._buffer.treeCount;
/*     */   }
/*     */   
/*     */   protected final int peekStructure() {
/* 172 */     if (this._structurePtr < this._structure.length) {
/* 173 */       return this._structure[this._structurePtr] & 0xFF;
/*     */     }
/*     */     
/* 176 */     return readFromNextStructure(0);
/*     */   }
/*     */   
/*     */   protected final int readStructure() {
/* 180 */     if (this._structurePtr < this._structure.length) {
/* 181 */       return this._structure[this._structurePtr++] & 0xFF;
/*     */     }
/*     */     
/* 184 */     return readFromNextStructure(1);
/*     */   }
/*     */   
/*     */   protected final int readEiiState() {
/* 188 */     return _eiiStateTable[readStructure()];
/*     */   }
/*     */   
/*     */   protected static int getEIIState(int item) {
/* 192 */     return _eiiStateTable[item];
/*     */   }
/*     */   
/*     */   protected static int getNIIState(int item) {
/* 196 */     return _niiStateTable[item];
/*     */   }
/*     */   
/*     */   protected static int getAIIState(int item) {
/* 200 */     return _aiiStateTable[item];
/*     */   }
/*     */   
/*     */   protected final int readStructure16() {
/* 204 */     return readStructure() << 8 | readStructure();
/*     */   }
/*     */   
/*     */   private int readFromNextStructure(int v) {
/* 208 */     this._structurePtr = v;
/* 209 */     this._currentStructureFragment = (FragmentedArray)this._currentStructureFragment.getNext();
/* 210 */     this._structure = this._currentStructureFragment.getArray();
/* 211 */     return this._structure[0] & 0xFF;
/*     */   }
/*     */   
/*     */   protected final String readStructureString() {
/* 215 */     if (this._structureStringsPtr < this._structureStrings.length) {
/* 216 */       return this._structureStrings[this._structureStringsPtr++];
/*     */     }
/*     */     
/* 219 */     this._structureStringsPtr = 1;
/* 220 */     this._currentStructureStringFragment = (FragmentedArray)this._currentStructureStringFragment.getNext();
/* 221 */     this._structureStrings = this._currentStructureStringFragment.getArray();
/* 222 */     return this._structureStrings[0];
/*     */   }
/*     */   
/*     */   protected final String readContentString() {
/* 226 */     return (String)readContentObject();
/*     */   }
/*     */   
/*     */   protected final char[] readContentCharactersCopy() {
/* 230 */     return (char[])readContentObject();
/*     */   }
/*     */   
/*     */   protected final int readContentCharactersBuffer(int length) {
/* 234 */     if (this._contentCharactersBufferPtr + length < this._contentCharactersBuffer.length) {
/* 235 */       int start = this._contentCharactersBufferPtr;
/* 236 */       this._contentCharactersBufferPtr += length;
/* 237 */       return start;
/*     */     } 
/*     */     
/* 240 */     this._contentCharactersBufferPtr = length;
/* 241 */     this._currentContentCharactersBufferFragment = (FragmentedArray)this._currentContentCharactersBufferFragment.getNext();
/* 242 */     this._contentCharactersBuffer = this._currentContentCharactersBufferFragment.getArray();
/* 243 */     return 0;
/*     */   }
/*     */   
/*     */   protected final Object readContentObject() {
/* 247 */     if (this._contentObjectsPtr < this._contentObjects.length) {
/* 248 */       return this._contentObjects[this._contentObjectsPtr++];
/*     */     }
/*     */     
/* 251 */     this._contentObjectsPtr = 1;
/* 252 */     this._currentContentObjectFragment = this._currentContentObjectFragment.getNext();
/* 253 */     this._contentObjects = this._currentContentObjectFragment.getArray();
/* 254 */     return this._contentObjects[0];
/*     */   }
/*     */   
/* 257 */   protected final StringBuilder _qNameBuffer = new StringBuilder();
/*     */   
/*     */   protected final String getQName(String prefix, String localName) {
/* 260 */     this._qNameBuffer.append(prefix).append(':').append(localName);
/* 261 */     String qName = this._qNameBuffer.toString();
/* 262 */     this._qNameBuffer.setLength(0);
/* 263 */     return this._stringInterningFeature ? qName.intern() : qName;
/*     */   }
/*     */   
/*     */   protected final String getPrefixFromQName(String qName) {
/* 267 */     int pIndex = qName.indexOf(':');
/* 268 */     if (this._stringInterningFeature) {
/* 269 */       return (pIndex != -1) ? qName.substring(0, pIndex).intern() : "";
/*     */     }
/* 271 */     return (pIndex != -1) ? qName.substring(0, pIndex) : "";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\AbstractProcessor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */