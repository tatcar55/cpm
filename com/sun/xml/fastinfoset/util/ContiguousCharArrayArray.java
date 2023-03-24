/*     */ package com.sun.xml.fastinfoset.util;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContiguousCharArrayArray
/*     */   extends ValueArray
/*     */ {
/*     */   public static final int INITIAL_CHARACTER_SIZE = 512;
/*     */   public static final int MAXIMUM_CHARACTER_SIZE = 2147483647;
/*     */   protected int _maximumCharacterSize;
/*     */   public int[] _offset;
/*     */   public int[] _length;
/*     */   public char[] _array;
/*     */   public int _arrayIndex;
/*     */   public int _readOnlyArrayIndex;
/*     */   private String[] _cachedStrings;
/*     */   public int _cachedIndex;
/*     */   private ContiguousCharArrayArray _readOnlyArray;
/*     */   
/*     */   public ContiguousCharArrayArray(int initialCapacity, int maximumCapacity, int initialCharacterSize, int maximumCharacterSize) {
/*  42 */     this._offset = new int[initialCapacity];
/*  43 */     this._length = new int[initialCapacity];
/*  44 */     this._array = new char[initialCharacterSize];
/*  45 */     this._maximumCapacity = maximumCapacity;
/*  46 */     this._maximumCharacterSize = maximumCharacterSize;
/*     */   }
/*     */   
/*     */   public ContiguousCharArrayArray() {
/*  50 */     this(10, 2147483647, 512, 2147483647);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void clear() {
/*  55 */     this._arrayIndex = this._readOnlyArrayIndex;
/*  56 */     this._size = this._readOnlyArraySize;
/*     */     
/*  58 */     if (this._cachedStrings != null) {
/*  59 */       for (int i = this._readOnlyArraySize; i < this._cachedStrings.length; i++) {
/*  60 */         this._cachedStrings[i] = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public final int getArrayIndex() {
/*  66 */     return this._arrayIndex;
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(ValueArray readOnlyArray, boolean clear) {
/*  70 */     if (!(readOnlyArray instanceof ContiguousCharArrayArray)) {
/*  71 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.illegalClass", new Object[] { readOnlyArray }));
/*     */     }
/*     */     
/*  74 */     setReadOnlyArray((ContiguousCharArrayArray)readOnlyArray, clear);
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(ContiguousCharArrayArray readOnlyArray, boolean clear) {
/*  78 */     if (readOnlyArray != null) {
/*  79 */       this._readOnlyArray = readOnlyArray;
/*  80 */       this._readOnlyArraySize = readOnlyArray.getSize();
/*  81 */       this._readOnlyArrayIndex = readOnlyArray.getArrayIndex();
/*     */       
/*  83 */       if (clear) {
/*  84 */         clear();
/*     */       }
/*     */       
/*  87 */       this._array = getCompleteCharArray();
/*  88 */       this._offset = getCompleteOffsetArray();
/*  89 */       this._length = getCompleteLengthArray();
/*  90 */       this._size = this._readOnlyArraySize;
/*  91 */       this._arrayIndex = this._readOnlyArrayIndex;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final char[] getCompleteCharArray() {
/*  96 */     if (this._readOnlyArray == null) {
/*  97 */       if (this._array == null) return null;
/*     */ 
/*     */       
/* 100 */       char[] clonedArray = new char[this._array.length];
/* 101 */       System.arraycopy(this._array, 0, clonedArray, 0, this._array.length);
/* 102 */       return clonedArray;
/*     */     } 
/*     */     
/* 105 */     char[] ra = this._readOnlyArray.getCompleteCharArray();
/* 106 */     char[] a = new char[this._readOnlyArrayIndex + this._array.length];
/* 107 */     System.arraycopy(ra, 0, a, 0, this._readOnlyArrayIndex);
/* 108 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int[] getCompleteOffsetArray() {
/* 113 */     if (this._readOnlyArray == null) {
/* 114 */       if (this._offset == null) return null;
/*     */ 
/*     */       
/* 117 */       int[] clonedArray = new int[this._offset.length];
/* 118 */       System.arraycopy(this._offset, 0, clonedArray, 0, this._offset.length);
/* 119 */       return clonedArray;
/*     */     } 
/*     */     
/* 122 */     int[] ra = this._readOnlyArray.getCompleteOffsetArray();
/* 123 */     int[] a = new int[this._readOnlyArraySize + this._offset.length];
/* 124 */     System.arraycopy(ra, 0, a, 0, this._readOnlyArraySize);
/* 125 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int[] getCompleteLengthArray() {
/* 130 */     if (this._readOnlyArray == null) {
/* 131 */       if (this._length == null) return null;
/*     */ 
/*     */       
/* 134 */       int[] clonedArray = new int[this._length.length];
/* 135 */       System.arraycopy(this._length, 0, clonedArray, 0, this._length.length);
/* 136 */       return clonedArray;
/*     */     } 
/*     */     
/* 139 */     int[] ra = this._readOnlyArray.getCompleteLengthArray();
/* 140 */     int[] a = new int[this._readOnlyArraySize + this._length.length];
/* 141 */     System.arraycopy(ra, 0, a, 0, this._readOnlyArraySize);
/* 142 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getString(int i) {
/* 147 */     if (this._cachedStrings != null && i < this._cachedStrings.length) {
/* 148 */       String s = this._cachedStrings[i];
/* 149 */       return (s != null) ? s : (this._cachedStrings[i] = new String(this._array, this._offset[i], this._length[i]));
/*     */     } 
/*     */     
/* 152 */     String[] newCachedStrings = new String[this._offset.length];
/* 153 */     if (this._cachedStrings != null && i >= this._cachedStrings.length) {
/* 154 */       System.arraycopy(this._cachedStrings, 0, newCachedStrings, 0, this._cachedStrings.length);
/*     */     }
/* 156 */     this._cachedStrings = newCachedStrings;
/*     */     
/* 158 */     this._cachedStrings[i] = new String(this._array, this._offset[i], this._length[i]); return new String(this._array, this._offset[i], this._length[i]);
/*     */   }
/*     */   
/*     */   public final void ensureSize(int l) {
/* 162 */     if (this._arrayIndex + l >= this._array.length) {
/* 163 */       resizeArray(this._arrayIndex + l);
/*     */     }
/*     */   }
/*     */   
/*     */   public final void add(int l) {
/* 168 */     if (this._size == this._offset.length) {
/* 169 */       resize();
/*     */     }
/*     */     
/* 172 */     this._cachedIndex = this._size;
/* 173 */     this._offset[this._size] = this._arrayIndex;
/* 174 */     this._length[this._size++] = l;
/*     */     
/* 176 */     this._arrayIndex += l;
/*     */   }
/*     */   
/*     */   public final int add(char[] c, int l) {
/* 180 */     if (this._size == this._offset.length) {
/* 181 */       resize();
/*     */     }
/*     */     
/* 184 */     int oldArrayIndex = this._arrayIndex;
/* 185 */     int arrayIndex = oldArrayIndex + l;
/*     */     
/* 187 */     this._cachedIndex = this._size;
/* 188 */     this._offset[this._size] = oldArrayIndex;
/* 189 */     this._length[this._size++] = l;
/*     */     
/* 191 */     if (arrayIndex >= this._array.length) {
/* 192 */       resizeArray(arrayIndex);
/*     */     }
/*     */     
/* 195 */     System.arraycopy(c, 0, this._array, oldArrayIndex, l);
/*     */     
/* 197 */     this._arrayIndex = arrayIndex;
/* 198 */     return oldArrayIndex;
/*     */   }
/*     */   
/*     */   protected final void resize() {
/* 202 */     if (this._size == this._maximumCapacity) {
/* 203 */       throw new ValueArrayResourceException(CommonResourceBundle.getInstance().getString("message.arrayMaxCapacity"));
/*     */     }
/*     */     
/* 206 */     int newSize = this._size * 3 / 2 + 1;
/* 207 */     if (newSize > this._maximumCapacity) {
/* 208 */       newSize = this._maximumCapacity;
/*     */     }
/*     */     
/* 211 */     int[] offset = new int[newSize];
/* 212 */     System.arraycopy(this._offset, 0, offset, 0, this._size);
/* 213 */     this._offset = offset;
/*     */     
/* 215 */     int[] length = new int[newSize];
/* 216 */     System.arraycopy(this._length, 0, length, 0, this._size);
/* 217 */     this._length = length;
/*     */   }
/*     */   
/*     */   protected final void resizeArray(int requestedSize) {
/* 221 */     if (this._arrayIndex == this._maximumCharacterSize) {
/* 222 */       throw new ValueArrayResourceException(CommonResourceBundle.getInstance().getString("message.maxNumberOfCharacters"));
/*     */     }
/*     */     
/* 225 */     int newSize = requestedSize * 3 / 2 + 1;
/* 226 */     if (newSize > this._maximumCharacterSize) {
/* 227 */       newSize = this._maximumCharacterSize;
/*     */     }
/*     */     
/* 230 */     char[] array = new char[newSize];
/* 231 */     System.arraycopy(this._array, 0, array, 0, this._arrayIndex);
/* 232 */     this._array = array;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\ContiguousCharArrayArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */