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
/*     */ public abstract class KeyIntMap
/*     */ {
/*     */   public static final int NOT_PRESENT = -1;
/*     */   static final int DEFAULT_INITIAL_CAPACITY = 16;
/*     */   static final int MAXIMUM_CAPACITY = 1048576;
/*     */   static final float DEFAULT_LOAD_FACTOR = 0.75F;
/*     */   int _readOnlyMapSize;
/*     */   int _size;
/*     */   int _capacity;
/*     */   int _threshold;
/*     */   final float _loadFactor;
/*     */   
/*     */   static class BaseEntry
/*     */   {
/*     */     final int _hash;
/*     */     final int _value;
/*     */     
/*     */     public BaseEntry(int hash, int value) {
/*  65 */       this._hash = hash;
/*  66 */       this._value = value;
/*     */     }
/*     */   }
/*     */   
/*     */   public KeyIntMap(int initialCapacity, float loadFactor) {
/*  71 */     if (initialCapacity < 0) {
/*  72 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.illegalInitialCapacity", new Object[] { Integer.valueOf(initialCapacity) }));
/*     */     }
/*  74 */     if (initialCapacity > 1048576)
/*  75 */       initialCapacity = 1048576; 
/*  76 */     if (loadFactor <= 0.0F || Float.isNaN(loadFactor)) {
/*  77 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.illegalLoadFactor", new Object[] { Float.valueOf(loadFactor) }));
/*     */     }
/*     */ 
/*     */     
/*  81 */     if (initialCapacity != 16) {
/*  82 */       this._capacity = 1;
/*  83 */       while (this._capacity < initialCapacity) {
/*  84 */         this._capacity <<= 1;
/*     */       }
/*  86 */       this._loadFactor = loadFactor;
/*  87 */       this._threshold = (int)(this._capacity * this._loadFactor);
/*     */     } else {
/*  89 */       this._capacity = 16;
/*  90 */       this._loadFactor = 0.75F;
/*  91 */       this._threshold = 12;
/*     */     } 
/*     */   }
/*     */   
/*     */   public KeyIntMap(int initialCapacity) {
/*  96 */     this(initialCapacity, 0.75F);
/*     */   }
/*     */   
/*     */   public KeyIntMap() {
/* 100 */     this._capacity = 16;
/* 101 */     this._loadFactor = 0.75F;
/* 102 */     this._threshold = 12;
/*     */   }
/*     */   
/*     */   public final int size() {
/* 106 */     return this._size + this._readOnlyMapSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void clear();
/*     */   
/*     */   public abstract void setReadOnlyMap(KeyIntMap paramKeyIntMap, boolean paramBoolean);
/*     */   
/*     */   public static final int hashHash(int h) {
/* 115 */     h += h << 9 ^ 0xFFFFFFFF;
/* 116 */     h ^= h >>> 14;
/* 117 */     h += h << 4;
/* 118 */     h ^= h >>> 10;
/* 119 */     return h;
/*     */   }
/*     */   
/*     */   public static final int indexFor(int h, int length) {
/* 123 */     return h & length - 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\KeyIntMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */