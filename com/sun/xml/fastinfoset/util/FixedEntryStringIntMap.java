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
/*     */ public class FixedEntryStringIntMap
/*     */   extends StringIntMap
/*     */ {
/*     */   private StringIntMap.Entry _fixedEntry;
/*     */   
/*     */   public FixedEntryStringIntMap(String fixedEntry, int initialCapacity, float loadFactor) {
/*  27 */     super(initialCapacity, loadFactor);
/*     */ 
/*     */     
/*  30 */     int hash = hashHash(fixedEntry.hashCode());
/*  31 */     int tableIndex = indexFor(hash, this._table.length);
/*  32 */     this._table[tableIndex] = this._fixedEntry = new StringIntMap.Entry(fixedEntry, hash, this._index++, null);
/*  33 */     if (this._size++ >= this._threshold) {
/*  34 */       resize(2 * this._table.length);
/*     */     }
/*     */   }
/*     */   
/*     */   public FixedEntryStringIntMap(String fixedEntry, int initialCapacity) {
/*  39 */     this(fixedEntry, initialCapacity, 0.75F);
/*     */   }
/*     */   
/*     */   public FixedEntryStringIntMap(String fixedEntry) {
/*  43 */     this(fixedEntry, 16, 0.75F);
/*     */   }
/*     */   
/*     */   public final void clear() {
/*  47 */     for (int i = 0; i < this._table.length; i++) {
/*  48 */       this._table[i] = null;
/*     */     }
/*  50 */     this._lastEntry = NULL_ENTRY;
/*     */     
/*  52 */     if (this._fixedEntry != null) {
/*  53 */       int tableIndex = indexFor(this._fixedEntry._hash, this._table.length);
/*  54 */       this._table[tableIndex] = this._fixedEntry;
/*  55 */       this._fixedEntry._next = null;
/*  56 */       this._size = 1;
/*  57 */       this._index = this._readOnlyMapSize + 1;
/*     */     } else {
/*  59 */       this._size = 0;
/*  60 */       this._index = this._readOnlyMapSize;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void setReadOnlyMap(KeyIntMap readOnlyMap, boolean clear) {
/*  65 */     if (!(readOnlyMap instanceof FixedEntryStringIntMap)) {
/*  66 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.illegalClass", new Object[] { readOnlyMap }));
/*     */     }
/*     */ 
/*     */     
/*  70 */     setReadOnlyMap((FixedEntryStringIntMap)readOnlyMap, clear);
/*     */   }
/*     */   
/*     */   public final void setReadOnlyMap(FixedEntryStringIntMap readOnlyMap, boolean clear) {
/*  74 */     this._readOnlyMap = readOnlyMap;
/*  75 */     if (this._readOnlyMap != null) {
/*  76 */       readOnlyMap.removeFixedEntry();
/*  77 */       this._readOnlyMapSize = readOnlyMap.size();
/*  78 */       this._index = this._readOnlyMapSize + this._size;
/*  79 */       if (clear) {
/*  80 */         clear();
/*     */       }
/*     */     } else {
/*  83 */       this._readOnlyMapSize = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private final void removeFixedEntry() {
/*  88 */     if (this._fixedEntry != null) {
/*  89 */       int tableIndex = indexFor(this._fixedEntry._hash, this._table.length);
/*  90 */       StringIntMap.Entry firstEntry = this._table[tableIndex];
/*  91 */       if (firstEntry == this._fixedEntry) {
/*  92 */         this._table[tableIndex] = this._fixedEntry._next;
/*     */       } else {
/*  94 */         StringIntMap.Entry previousEntry = firstEntry;
/*  95 */         while (previousEntry._next != this._fixedEntry) {
/*  96 */           previousEntry = previousEntry._next;
/*     */         }
/*  98 */         previousEntry._next = this._fixedEntry._next;
/*     */       } 
/*     */       
/* 101 */       this._fixedEntry = null;
/* 102 */       this._size--;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\FixedEntryStringIntMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */