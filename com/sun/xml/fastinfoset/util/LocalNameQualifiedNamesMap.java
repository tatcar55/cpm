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
/*     */ public class LocalNameQualifiedNamesMap
/*     */   extends KeyIntMap
/*     */ {
/*     */   private LocalNameQualifiedNamesMap _readOnlyMap;
/*     */   private int _index;
/*     */   private Entry[] _table;
/*     */   
/*     */   public static class Entry
/*     */   {
/*     */     final String _key;
/*     */     final int _hash;
/*     */     public QualifiedName[] _value;
/*     */     public int _valueIndex;
/*     */     Entry _next;
/*     */     
/*     */     public Entry(String key, int hash, Entry next) {
/*  37 */       this._key = key;
/*  38 */       this._hash = hash;
/*  39 */       this._next = next;
/*  40 */       this._value = new QualifiedName[1];
/*     */     }
/*     */     
/*     */     public void addQualifiedName(QualifiedName name) {
/*  44 */       if (this._valueIndex < this._value.length) {
/*  45 */         this._value[this._valueIndex++] = name;
/*  46 */       } else if (this._valueIndex == this._value.length) {
/*  47 */         QualifiedName[] newValue = new QualifiedName[this._valueIndex * 3 / 2 + 1];
/*  48 */         System.arraycopy(this._value, 0, newValue, 0, this._valueIndex);
/*  49 */         this._value = newValue;
/*  50 */         this._value[this._valueIndex++] = name;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public LocalNameQualifiedNamesMap(int initialCapacity, float loadFactor) {
/*  58 */     super(initialCapacity, loadFactor);
/*     */     
/*  60 */     this._table = new Entry[this._capacity];
/*     */   }
/*     */   
/*     */   public LocalNameQualifiedNamesMap(int initialCapacity) {
/*  64 */     this(initialCapacity, 0.75F);
/*     */   }
/*     */   
/*     */   public LocalNameQualifiedNamesMap() {
/*  68 */     this(16, 0.75F);
/*     */   }
/*     */   
/*     */   public final void clear() {
/*  72 */     for (int i = 0; i < this._table.length; i++) {
/*  73 */       this._table[i] = null;
/*     */     }
/*  75 */     this._size = 0;
/*     */     
/*  77 */     if (this._readOnlyMap != null) {
/*  78 */       this._index = this._readOnlyMap.getIndex();
/*     */     } else {
/*  80 */       this._index = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final void setReadOnlyMap(KeyIntMap readOnlyMap, boolean clear) {
/*  85 */     if (!(readOnlyMap instanceof LocalNameQualifiedNamesMap)) {
/*  86 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.illegalClass", new Object[] { readOnlyMap }));
/*     */     }
/*     */ 
/*     */     
/*  90 */     setReadOnlyMap((LocalNameQualifiedNamesMap)readOnlyMap, clear);
/*     */   }
/*     */   
/*     */   public final void setReadOnlyMap(LocalNameQualifiedNamesMap readOnlyMap, boolean clear) {
/*  94 */     this._readOnlyMap = readOnlyMap;
/*  95 */     if (this._readOnlyMap != null) {
/*  96 */       this._readOnlyMapSize = this._readOnlyMap.size();
/*  97 */       this._index = this._readOnlyMap.getIndex();
/*  98 */       if (clear) {
/*  99 */         clear();
/*     */       }
/*     */     } else {
/* 102 */       this._readOnlyMapSize = 0;
/* 103 */       this._index = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final boolean isQNameFromReadOnlyMap(QualifiedName name) {
/* 108 */     return (this._readOnlyMap != null && name.index <= this._readOnlyMap.getIndex());
/*     */   }
/*     */   
/*     */   public final int getNextIndex() {
/* 112 */     return this._index++;
/*     */   }
/*     */   
/*     */   public final int getIndex() {
/* 116 */     return this._index;
/*     */   }
/*     */   
/*     */   public final Entry obtainEntry(String key) {
/* 120 */     int hash = hashHash(key.hashCode());
/*     */     
/* 122 */     if (this._readOnlyMap != null) {
/* 123 */       Entry entry = this._readOnlyMap.getEntry(key, hash);
/* 124 */       if (entry != null) {
/* 125 */         return entry;
/*     */       }
/*     */     } 
/*     */     
/* 129 */     int tableIndex = indexFor(hash, this._table.length);
/* 130 */     for (Entry e = this._table[tableIndex]; e != null; e = e._next) {
/* 131 */       if (e._hash == hash && eq(key, e._key)) {
/* 132 */         return e;
/*     */       }
/*     */     } 
/*     */     
/* 136 */     return addEntry(key, hash, tableIndex);
/*     */   }
/*     */   
/*     */   public final Entry obtainDynamicEntry(String key) {
/* 140 */     int hash = hashHash(key.hashCode());
/*     */     
/* 142 */     int tableIndex = indexFor(hash, this._table.length);
/* 143 */     for (Entry e = this._table[tableIndex]; e != null; e = e._next) {
/* 144 */       if (e._hash == hash && eq(key, e._key)) {
/* 145 */         return e;
/*     */       }
/*     */     } 
/*     */     
/* 149 */     return addEntry(key, hash, tableIndex);
/*     */   }
/*     */   
/*     */   private final Entry getEntry(String key, int hash) {
/* 153 */     if (this._readOnlyMap != null) {
/* 154 */       Entry entry = this._readOnlyMap.getEntry(key, hash);
/* 155 */       if (entry != null) {
/* 156 */         return entry;
/*     */       }
/*     */     } 
/*     */     
/* 160 */     int tableIndex = indexFor(hash, this._table.length);
/* 161 */     for (Entry e = this._table[tableIndex]; e != null; e = e._next) {
/* 162 */       if (e._hash == hash && eq(key, e._key)) {
/* 163 */         return e;
/*     */       }
/*     */     } 
/*     */     
/* 167 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private final Entry addEntry(String key, int hash, int bucketIndex) {
/* 172 */     Entry e = this._table[bucketIndex];
/* 173 */     this._table[bucketIndex] = new Entry(key, hash, e);
/* 174 */     e = this._table[bucketIndex];
/* 175 */     if (this._size++ >= this._threshold) {
/* 176 */       resize(2 * this._table.length);
/*     */     }
/*     */     
/* 179 */     return e;
/*     */   }
/*     */   
/*     */   private final void resize(int newCapacity) {
/* 183 */     this._capacity = newCapacity;
/* 184 */     Entry[] oldTable = this._table;
/* 185 */     int oldCapacity = oldTable.length;
/* 186 */     if (oldCapacity == 1048576) {
/* 187 */       this._threshold = Integer.MAX_VALUE;
/*     */       
/*     */       return;
/*     */     } 
/* 191 */     Entry[] newTable = new Entry[this._capacity];
/* 192 */     transfer(newTable);
/* 193 */     this._table = newTable;
/* 194 */     this._threshold = (int)(this._capacity * this._loadFactor);
/*     */   }
/*     */   
/*     */   private final void transfer(Entry[] newTable) {
/* 198 */     Entry[] src = this._table;
/* 199 */     int newCapacity = newTable.length;
/* 200 */     for (int j = 0; j < src.length; j++) {
/* 201 */       Entry e = src[j];
/* 202 */       if (e != null) {
/* 203 */         src[j] = null;
/*     */         do {
/* 205 */           Entry next = e._next;
/* 206 */           int i = indexFor(e._hash, newCapacity);
/* 207 */           e._next = newTable[i];
/* 208 */           newTable[i] = e;
/* 209 */           e = next;
/* 210 */         } while (e != null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private final boolean eq(String x, String y) {
/* 216 */     return (x == y || x.equals(y));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\LocalNameQualifiedNamesMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */