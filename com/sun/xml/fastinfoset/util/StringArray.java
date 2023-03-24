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
/*     */ public class StringArray
/*     */   extends ValueArray
/*     */ {
/*     */   public String[] _array;
/*     */   private StringArray _readOnlyArray;
/*     */   private boolean _clear;
/*     */   
/*     */   public StringArray(int initialCapacity, int maximumCapacity, boolean clear) {
/*  30 */     this._array = new String[initialCapacity];
/*  31 */     this._maximumCapacity = maximumCapacity;
/*  32 */     this._clear = clear;
/*     */   }
/*     */   
/*     */   public StringArray() {
/*  36 */     this(10, 2147483647, false);
/*     */   }
/*     */   
/*     */   public final void clear() {
/*  40 */     if (this._clear) for (int i = this._readOnlyArraySize; i < this._size; i++) {
/*  41 */         this._array[i] = null;
/*     */       } 
/*  43 */     this._size = this._readOnlyArraySize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String[] getArray() {
/*  51 */     if (this._array == null) return null;
/*     */     
/*  53 */     String[] clonedArray = new String[this._array.length];
/*  54 */     System.arraycopy(this._array, 0, clonedArray, 0, this._array.length);
/*  55 */     return clonedArray;
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(ValueArray readOnlyArray, boolean clear) {
/*  59 */     if (!(readOnlyArray instanceof StringArray)) {
/*  60 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.illegalClass", new Object[] { readOnlyArray }));
/*     */     }
/*     */ 
/*     */     
/*  64 */     setReadOnlyArray((StringArray)readOnlyArray, clear);
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(StringArray readOnlyArray, boolean clear) {
/*  68 */     if (readOnlyArray != null) {
/*  69 */       this._readOnlyArray = readOnlyArray;
/*  70 */       this._readOnlyArraySize = readOnlyArray.getSize();
/*     */       
/*  72 */       if (clear) {
/*  73 */         clear();
/*     */       }
/*     */       
/*  76 */       this._array = getCompleteArray();
/*  77 */       this._size = this._readOnlyArraySize;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final String[] getCompleteArray() {
/*  82 */     if (this._readOnlyArray == null)
/*     */     {
/*  84 */       return getArray();
/*     */     }
/*     */     
/*  87 */     String[] ra = this._readOnlyArray.getCompleteArray();
/*  88 */     String[] a = new String[this._readOnlyArraySize + this._array.length];
/*  89 */     System.arraycopy(ra, 0, a, 0, this._readOnlyArraySize);
/*  90 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String get(int i) {
/*  95 */     return this._array[i];
/*     */   }
/*     */   
/*     */   public final int add(String s) {
/*  99 */     if (this._size == this._array.length) {
/* 100 */       resize();
/*     */     }
/*     */     
/* 103 */     this._array[this._size++] = s;
/* 104 */     return this._size;
/*     */   }
/*     */   
/*     */   protected final void resize() {
/* 108 */     if (this._size == this._maximumCapacity) {
/* 109 */       throw new ValueArrayResourceException(CommonResourceBundle.getInstance().getString("message.arrayMaxCapacity"));
/*     */     }
/*     */     
/* 112 */     int newSize = this._size * 3 / 2 + 1;
/* 113 */     if (newSize > this._maximumCapacity) {
/* 114 */       newSize = this._maximumCapacity;
/*     */     }
/*     */     
/* 117 */     String[] newArray = new String[newSize];
/* 118 */     System.arraycopy(this._array, 0, newArray, 0, this._size);
/* 119 */     this._array = newArray;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\StringArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */