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
/*     */ public class AbstractCreator
/*     */   extends AbstractCreatorProcessor
/*     */ {
/*     */   protected MutableXMLStreamBuffer _buffer;
/*     */   
/*     */   public void setXMLStreamBuffer(MutableXMLStreamBuffer buffer) {
/*  52 */     if (buffer == null) {
/*  53 */       throw new NullPointerException("buffer cannot be null");
/*     */     }
/*  55 */     setBuffer(buffer);
/*     */   }
/*     */   
/*     */   public MutableXMLStreamBuffer getXMLStreamBuffer() {
/*  59 */     return this._buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void createBuffer() {
/*  64 */     setBuffer(new MutableXMLStreamBuffer());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void increaseTreeCount() {
/*  71 */     this._buffer.treeCount++;
/*     */   }
/*     */   
/*     */   protected final void setBuffer(MutableXMLStreamBuffer buffer) {
/*  75 */     this._buffer = buffer;
/*     */     
/*  77 */     this._currentStructureFragment = this._buffer.getStructure();
/*  78 */     this._structure = this._currentStructureFragment.getArray();
/*  79 */     this._structurePtr = 0;
/*     */     
/*  81 */     this._currentStructureStringFragment = this._buffer.getStructureStrings();
/*  82 */     this._structureStrings = this._currentStructureStringFragment.getArray();
/*  83 */     this._structureStringsPtr = 0;
/*     */     
/*  85 */     this._currentContentCharactersBufferFragment = this._buffer.getContentCharactersBuffer();
/*  86 */     this._contentCharactersBuffer = this._currentContentCharactersBufferFragment.getArray();
/*  87 */     this._contentCharactersBufferPtr = 0;
/*     */     
/*  89 */     this._currentContentObjectFragment = this._buffer.getContentObjects();
/*  90 */     this._contentObjects = this._currentContentObjectFragment.getArray();
/*  91 */     this._contentObjectsPtr = 0;
/*     */   }
/*     */   
/*     */   protected final void setHasInternedStrings(boolean hasInternedStrings) {
/*  95 */     this._buffer.setHasInternedStrings(hasInternedStrings);
/*     */   }
/*     */   
/*     */   protected final void storeStructure(int b) {
/*  99 */     this._structure[this._structurePtr++] = (byte)b;
/* 100 */     if (this._structurePtr == this._structure.length) {
/* 101 */       resizeStructure();
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void resizeStructure() {
/* 106 */     this._structurePtr = 0;
/* 107 */     if (this._currentStructureFragment.getNext() != null) {
/* 108 */       this._currentStructureFragment = (FragmentedArray)this._currentStructureFragment.getNext();
/* 109 */       this._structure = this._currentStructureFragment.getArray();
/*     */     } else {
/* 111 */       this._structure = new byte[this._structure.length];
/* 112 */       this._currentStructureFragment = (FragmentedArray)new FragmentedArray<byte>(this._structure, (FragmentedArray)this._currentStructureFragment);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void storeStructureString(String s) {
/* 117 */     this._structureStrings[this._structureStringsPtr++] = s;
/* 118 */     if (this._structureStringsPtr == this._structureStrings.length) {
/* 119 */       resizeStructureStrings();
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void resizeStructureStrings() {
/* 124 */     this._structureStringsPtr = 0;
/* 125 */     if (this._currentStructureStringFragment.getNext() != null) {
/* 126 */       this._currentStructureStringFragment = (FragmentedArray)this._currentStructureStringFragment.getNext();
/* 127 */       this._structureStrings = this._currentStructureStringFragment.getArray();
/*     */     } else {
/* 129 */       this._structureStrings = new String[this._structureStrings.length];
/* 130 */       this._currentStructureStringFragment = (FragmentedArray)new FragmentedArray<String>(this._structureStrings, (FragmentedArray)this._currentStructureStringFragment);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void storeContentString(String s) {
/* 135 */     storeContentObject(s);
/*     */   }
/*     */   
/*     */   protected final void storeContentCharacters(int type, char[] ch, int start, int length) {
/* 139 */     if (this._contentCharactersBufferPtr + length >= this._contentCharactersBuffer.length) {
/* 140 */       if (length >= 512) {
/* 141 */         storeStructure(type | 0x4);
/* 142 */         storeContentCharactersCopy(ch, start, length);
/*     */         return;
/*     */       } 
/* 145 */       resizeContentCharacters();
/*     */     } 
/*     */     
/* 148 */     if (length < 256) {
/* 149 */       storeStructure(type);
/* 150 */       storeStructure(length);
/* 151 */       System.arraycopy(ch, start, this._contentCharactersBuffer, this._contentCharactersBufferPtr, length);
/* 152 */       this._contentCharactersBufferPtr += length;
/* 153 */     } else if (length < 65536) {
/* 154 */       storeStructure(type | 0x1);
/* 155 */       storeStructure(length >> 8);
/* 156 */       storeStructure(length & 0xFF);
/* 157 */       System.arraycopy(ch, start, this._contentCharactersBuffer, this._contentCharactersBufferPtr, length);
/* 158 */       this._contentCharactersBufferPtr += length;
/*     */     } else {
/* 160 */       storeStructure(type | 0x4);
/* 161 */       storeContentCharactersCopy(ch, start, length);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void resizeContentCharacters() {
/* 166 */     this._contentCharactersBufferPtr = 0;
/* 167 */     if (this._currentContentCharactersBufferFragment.getNext() != null) {
/* 168 */       this._currentContentCharactersBufferFragment = (FragmentedArray)this._currentContentCharactersBufferFragment.getNext();
/* 169 */       this._contentCharactersBuffer = this._currentContentCharactersBufferFragment.getArray();
/*     */     } else {
/* 171 */       this._contentCharactersBuffer = new char[this._contentCharactersBuffer.length];
/* 172 */       this._currentContentCharactersBufferFragment = (FragmentedArray)new FragmentedArray<char>(this._contentCharactersBuffer, (FragmentedArray)this._currentContentCharactersBufferFragment);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void storeContentCharactersCopy(char[] ch, int start, int length) {
/* 178 */     char[] copyOfCh = new char[length];
/* 179 */     System.arraycopy(ch, start, copyOfCh, 0, length);
/* 180 */     storeContentObject(copyOfCh);
/*     */   }
/*     */   
/*     */   protected final Object peekAtContentObject() {
/* 184 */     return this._contentObjects[this._contentObjectsPtr];
/*     */   }
/*     */   
/*     */   protected final void storeContentObject(Object s) {
/* 188 */     this._contentObjects[this._contentObjectsPtr++] = s;
/* 189 */     if (this._contentObjectsPtr == this._contentObjects.length) {
/* 190 */       resizeContentObjects();
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void resizeContentObjects() {
/* 195 */     this._contentObjectsPtr = 0;
/* 196 */     if (this._currentContentObjectFragment.getNext() != null) {
/* 197 */       this._currentContentObjectFragment = this._currentContentObjectFragment.getNext();
/* 198 */       this._contentObjects = this._currentContentObjectFragment.getArray();
/*     */     } else {
/* 200 */       this._contentObjects = new Object[this._contentObjects.length];
/* 201 */       this._currentContentObjectFragment = new FragmentedArray(this._contentObjects, this._currentContentObjectFragment);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\AbstractCreator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */