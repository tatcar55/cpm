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
/*     */ public class StringIntMap
/*     */   extends KeyIntMap
/*     */ {
/*  23 */   protected static final Entry NULL_ENTRY = new Entry(null, 0, -1, null);
/*     */   protected StringIntMap _readOnlyMap;
/*     */   
/*     */   protected static class Entry
/*     */     extends KeyIntMap.BaseEntry {
/*     */     final String _key;
/*     */     Entry _next;
/*     */     
/*     */     public Entry(String key, int hash, int value, Entry next) {
/*  32 */       super(hash, value);
/*  33 */       this._key = key;
/*  34 */       this._next = next;
/*     */     }
/*     */   }
/*     */   
/*  38 */   protected Entry _lastEntry = NULL_ENTRY;
/*     */   
/*     */   protected Entry[] _table;
/*     */   
/*     */   protected int _index;
/*     */   
/*     */   protected int _totalCharacterCount;
/*     */ 
/*     */   
/*     */   public StringIntMap(int initialCapacity, float loadFactor) {
/*  48 */     super(initialCapacity, loadFactor);
/*     */     
/*  50 */     this._table = new Entry[this._capacity];
/*     */   }
/*     */   
/*     */   public StringIntMap(int initialCapacity) {
/*  54 */     this(initialCapacity, 0.75F);
/*     */   }
/*     */   
/*     */   public StringIntMap() {
/*  58 */     this(16, 0.75F);
/*     */   }
/*     */   
/*     */   public void clear() {
/*  62 */     for (int i = 0; i < this._table.length; i++) {
/*  63 */       this._table[i] = null;
/*     */     }
/*  65 */     this._lastEntry = NULL_ENTRY;
/*  66 */     this._size = 0;
/*  67 */     this._index = this._readOnlyMapSize;
/*  68 */     this._totalCharacterCount = 0;
/*     */   }
/*     */   
/*     */   public void setReadOnlyMap(KeyIntMap readOnlyMap, boolean clear) {
/*  72 */     if (!(readOnlyMap instanceof StringIntMap)) {
/*  73 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.illegalClass", new Object[] { readOnlyMap }));
/*     */     }
/*     */ 
/*     */     
/*  77 */     setReadOnlyMap((StringIntMap)readOnlyMap, clear);
/*     */   }
/*     */   
/*     */   public final void setReadOnlyMap(StringIntMap readOnlyMap, boolean clear) {
/*  81 */     this._readOnlyMap = readOnlyMap;
/*  82 */     if (this._readOnlyMap != null) {
/*  83 */       this._readOnlyMapSize = this._readOnlyMap.size();
/*  84 */       this._index = this._size + this._readOnlyMapSize;
/*     */       
/*  86 */       if (clear) {
/*  87 */         clear();
/*     */       }
/*     */     } else {
/*  90 */       this._readOnlyMapSize = 0;
/*  91 */       this._index = this._size;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final int getNextIndex() {
/*  96 */     return this._index++;
/*     */   }
/*     */   
/*     */   public final int getIndex() {
/* 100 */     return this._index;
/*     */   }
/*     */   
/*     */   public final int obtainIndex(String key) {
/* 104 */     int hash = hashHash(key.hashCode());
/*     */     
/* 106 */     if (this._readOnlyMap != null) {
/* 107 */       int index = this._readOnlyMap.get(key, hash);
/* 108 */       if (index != -1) {
/* 109 */         return index;
/*     */       }
/*     */     } 
/*     */     
/* 113 */     int tableIndex = indexFor(hash, this._table.length);
/* 114 */     for (Entry e = this._table[tableIndex]; e != null; e = e._next) {
/* 115 */       if (e._hash == hash && eq(key, e._key)) {
/* 116 */         return e._value;
/*     */       }
/*     */     } 
/*     */     
/* 120 */     addEntry(key, hash, tableIndex);
/* 121 */     return -1;
/*     */   }
/*     */   
/*     */   public final void add(String key) {
/* 125 */     int hash = hashHash(key.hashCode());
/* 126 */     int tableIndex = indexFor(hash, this._table.length);
/* 127 */     addEntry(key, hash, tableIndex);
/*     */   }
/*     */   
/*     */   public final int get(String key) {
/* 131 */     if (key == this._lastEntry._key) {
/* 132 */       return this._lastEntry._value;
/*     */     }
/* 134 */     return get(key, hashHash(key.hashCode()));
/*     */   }
/*     */   
/*     */   public final int getTotalCharacterCount() {
/* 138 */     return this._totalCharacterCount;
/*     */   }
/*     */   
/*     */   private final int get(String key, int hash) {
/* 142 */     if (this._readOnlyMap != null) {
/* 143 */       int i = this._readOnlyMap.get(key, hash);
/* 144 */       if (i != -1) {
/* 145 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 149 */     int tableIndex = indexFor(hash, this._table.length);
/* 150 */     for (Entry e = this._table[tableIndex]; e != null; e = e._next) {
/* 151 */       if (e._hash == hash && eq(key, e._key)) {
/* 152 */         this._lastEntry = e;
/* 153 */         return e._value;
/*     */       } 
/*     */     } 
/*     */     
/* 157 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private final void addEntry(String key, int hash, int bucketIndex) {
/* 162 */     Entry e = this._table[bucketIndex];
/* 163 */     this._table[bucketIndex] = new Entry(key, hash, this._index++, e);
/* 164 */     this._totalCharacterCount += key.length();
/* 165 */     if (this._size++ >= this._threshold) {
/* 166 */       resize(2 * this._table.length);
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void resize(int newCapacity) {
/* 171 */     this._capacity = newCapacity;
/* 172 */     Entry[] oldTable = this._table;
/* 173 */     int oldCapacity = oldTable.length;
/* 174 */     if (oldCapacity == 1048576) {
/* 175 */       this._threshold = Integer.MAX_VALUE;
/*     */       
/*     */       return;
/*     */     } 
/* 179 */     Entry[] newTable = new Entry[this._capacity];
/* 180 */     transfer(newTable);
/* 181 */     this._table = newTable;
/* 182 */     this._threshold = (int)(this._capacity * this._loadFactor);
/*     */   }
/*     */   
/*     */   private final void transfer(Entry[] newTable) {
/* 186 */     Entry[] src = this._table;
/* 187 */     int newCapacity = newTable.length;
/* 188 */     for (int j = 0; j < src.length; j++) {
/* 189 */       Entry e = src[j];
/* 190 */       if (e != null) {
/* 191 */         src[j] = null;
/*     */         do {
/* 193 */           Entry next = e._next;
/* 194 */           int i = indexFor(e._hash, newCapacity);
/* 195 */           e._next = newTable[i];
/* 196 */           newTable[i] = e;
/* 197 */           e = next;
/* 198 */         } while (e != null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private final boolean eq(String x, String y) {
/* 204 */     return (x == y || x.equals(y));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\StringIntMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */