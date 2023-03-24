/*     */ package com.sun.xml.fastinfoset.util;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import com.sun.xml.fastinfoset.QualifiedName;
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
/*     */ public class QualifiedNameArray
/*     */   extends ValueArray
/*     */ {
/*     */   public QualifiedName[] _array;
/*     */   private QualifiedNameArray _readOnlyArray;
/*     */   
/*     */   public QualifiedNameArray(int initialCapacity, int maximumCapacity) {
/*  30 */     this._array = new QualifiedName[initialCapacity];
/*  31 */     this._maximumCapacity = maximumCapacity;
/*     */   }
/*     */   
/*     */   public QualifiedNameArray() {
/*  35 */     this(10, 2147483647);
/*     */   }
/*     */   
/*     */   public final void clear() {
/*  39 */     this._size = this._readOnlyArraySize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final QualifiedName[] getArray() {
/*  47 */     if (this._array == null) return null;
/*     */     
/*  49 */     QualifiedName[] clonedArray = new QualifiedName[this._array.length];
/*  50 */     System.arraycopy(this._array, 0, clonedArray, 0, this._array.length);
/*  51 */     return clonedArray;
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(ValueArray readOnlyArray, boolean clear) {
/*  55 */     if (!(readOnlyArray instanceof QualifiedNameArray)) {
/*  56 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.illegalClass", new Object[] { readOnlyArray }));
/*     */     }
/*     */ 
/*     */     
/*  60 */     setReadOnlyArray((QualifiedNameArray)readOnlyArray, clear);
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(QualifiedNameArray readOnlyArray, boolean clear) {
/*  64 */     if (readOnlyArray != null) {
/*  65 */       this._readOnlyArray = readOnlyArray;
/*  66 */       this._readOnlyArraySize = readOnlyArray.getSize();
/*     */       
/*  68 */       if (clear) {
/*  69 */         clear();
/*     */       }
/*     */       
/*  72 */       this._array = getCompleteArray();
/*  73 */       this._size = this._readOnlyArraySize;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final QualifiedName[] getCompleteArray() {
/*  78 */     if (this._readOnlyArray == null)
/*     */     {
/*  80 */       return getArray();
/*     */     }
/*     */     
/*  83 */     QualifiedName[] ra = this._readOnlyArray.getCompleteArray();
/*  84 */     QualifiedName[] a = new QualifiedName[this._readOnlyArraySize + this._array.length];
/*  85 */     System.arraycopy(ra, 0, a, 0, this._readOnlyArraySize);
/*  86 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public final QualifiedName getNext() {
/*  91 */     return (this._size == this._array.length) ? null : this._array[this._size];
/*     */   }
/*     */   
/*     */   public final void add(QualifiedName s) {
/*  95 */     if (this._size == this._array.length) {
/*  96 */       resize();
/*     */     }
/*     */     
/*  99 */     this._array[this._size++] = s;
/*     */   }
/*     */   
/*     */   protected final void resize() {
/* 103 */     if (this._size == this._maximumCapacity) {
/* 104 */       throw new ValueArrayResourceException(CommonResourceBundle.getInstance().getString("message.arrayMaxCapacity"));
/*     */     }
/*     */     
/* 107 */     int newSize = this._size * 3 / 2 + 1;
/* 108 */     if (newSize > this._maximumCapacity) {
/* 109 */       newSize = this._maximumCapacity;
/*     */     }
/*     */     
/* 112 */     QualifiedName[] newArray = new QualifiedName[newSize];
/* 113 */     System.arraycopy(this._array, 0, newArray, 0, this._size);
/* 114 */     this._array = newArray;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\QualifiedNameArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */