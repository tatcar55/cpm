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
/*     */ public class CharArrayArray
/*     */   extends ValueArray
/*     */ {
/*     */   private CharArray[] _array;
/*     */   private CharArrayArray _readOnlyArray;
/*     */   
/*     */   public CharArrayArray(int initialCapacity, int maximumCapacity) {
/*  29 */     this._array = new CharArray[initialCapacity];
/*  30 */     this._maximumCapacity = maximumCapacity;
/*     */   }
/*     */   
/*     */   public CharArrayArray() {
/*  34 */     this(10, 2147483647);
/*     */   }
/*     */   
/*     */   public final void clear() {
/*  38 */     for (int i = 0; i < this._size; i++) {
/*  39 */       this._array[i] = null;
/*     */     }
/*  41 */     this._size = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final CharArray[] getArray() {
/*  49 */     if (this._array == null) return null;
/*     */     
/*  51 */     CharArray[] clonedArray = new CharArray[this._array.length];
/*  52 */     System.arraycopy(this._array, 0, clonedArray, 0, this._array.length);
/*  53 */     return clonedArray;
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(ValueArray readOnlyArray, boolean clear) {
/*  57 */     if (!(readOnlyArray instanceof CharArrayArray)) {
/*  58 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.illegalClass", new Object[] { readOnlyArray }));
/*     */     }
/*     */     
/*  61 */     setReadOnlyArray((CharArrayArray)readOnlyArray, clear);
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(CharArrayArray readOnlyArray, boolean clear) {
/*  65 */     if (readOnlyArray != null) {
/*  66 */       this._readOnlyArray = readOnlyArray;
/*  67 */       this._readOnlyArraySize = readOnlyArray.getSize();
/*     */       
/*  69 */       if (clear) {
/*  70 */         clear();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public final CharArray get(int i) {
/*  76 */     if (this._readOnlyArray == null) {
/*  77 */       return this._array[i];
/*     */     }
/*  79 */     if (i < this._readOnlyArraySize) {
/*  80 */       return this._readOnlyArray.get(i);
/*     */     }
/*  82 */     return this._array[i - this._readOnlyArraySize];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(CharArray s) {
/*  88 */     if (this._size == this._array.length) {
/*  89 */       resize();
/*     */     }
/*     */     
/*  92 */     this._array[this._size++] = s;
/*     */   }
/*     */   
/*     */   protected final void resize() {
/*  96 */     if (this._size == this._maximumCapacity) {
/*  97 */       throw new ValueArrayResourceException(CommonResourceBundle.getInstance().getString("message.arrayMaxCapacity"));
/*     */     }
/*     */     
/* 100 */     int newSize = this._size * 3 / 2 + 1;
/* 101 */     if (newSize > this._maximumCapacity) {
/* 102 */       newSize = this._maximumCapacity;
/*     */     }
/*     */     
/* 105 */     CharArray[] newArray = new CharArray[newSize];
/* 106 */     System.arraycopy(this._array, 0, newArray, 0, this._size);
/* 107 */     this._array = newArray;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\CharArrayArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */