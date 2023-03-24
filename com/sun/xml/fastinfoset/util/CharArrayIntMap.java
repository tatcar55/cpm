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
/*     */ public class CharArrayIntMap
/*     */   extends KeyIntMap
/*     */ {
/*     */   private CharArrayIntMap _readOnlyMap;
/*     */   protected int _totalCharacterCount;
/*     */   private Entry[] _table;
/*     */   
/*     */   static class Entry
/*     */     extends KeyIntMap.BaseEntry
/*     */   {
/*     */     final char[] _ch;
/*     */     final int _start;
/*     */     final int _length;
/*     */     Entry _next;
/*     */     
/*     */     public Entry(char[] ch, int start, int length, int hash, int value, Entry next) {
/*  36 */       super(hash, value);
/*  37 */       this._ch = ch;
/*  38 */       this._start = start;
/*  39 */       this._length = length;
/*  40 */       this._next = next;
/*     */     }
/*     */     
/*     */     public final boolean equalsCharArray(char[] ch, int start, int length) {
/*  44 */       if (this._length == length) {
/*  45 */         int n = this._length;
/*  46 */         int i = this._start;
/*  47 */         int j = start;
/*  48 */         while (n-- != 0) {
/*  49 */           if (this._ch[i++] != ch[j++])
/*  50 */             return false; 
/*     */         } 
/*  52 */         return true;
/*     */       } 
/*     */       
/*  55 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CharArrayIntMap(int initialCapacity, float loadFactor) {
/*  63 */     super(initialCapacity, loadFactor);
/*     */     
/*  65 */     this._table = new Entry[this._capacity];
/*     */   }
/*     */   
/*     */   public CharArrayIntMap(int initialCapacity) {
/*  69 */     this(initialCapacity, 0.75F);
/*     */   }
/*     */   
/*     */   public CharArrayIntMap() {
/*  73 */     this(16, 0.75F);
/*     */   }
/*     */   
/*     */   public final void clear() {
/*  77 */     for (int i = 0; i < this._table.length; i++) {
/*  78 */       this._table[i] = null;
/*     */     }
/*  80 */     this._size = 0;
/*  81 */     this._totalCharacterCount = 0;
/*     */   }
/*     */   
/*     */   public final void setReadOnlyMap(KeyIntMap readOnlyMap, boolean clear) {
/*  85 */     if (!(readOnlyMap instanceof CharArrayIntMap)) {
/*  86 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.illegalClass", new Object[] { readOnlyMap }));
/*     */     }
/*     */ 
/*     */     
/*  90 */     setReadOnlyMap((CharArrayIntMap)readOnlyMap, clear);
/*     */   }
/*     */   
/*     */   public final void setReadOnlyMap(CharArrayIntMap readOnlyMap, boolean clear) {
/*  94 */     this._readOnlyMap = readOnlyMap;
/*  95 */     if (this._readOnlyMap != null) {
/*  96 */       this._readOnlyMapSize = this._readOnlyMap.size();
/*     */       
/*  98 */       if (clear) {
/*  99 */         clear();
/*     */       }
/*     */     } else {
/* 102 */       this._readOnlyMapSize = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int get(char[] ch, int start, int length) {
/* 114 */     int hash = hashHash(CharArray.hashCode(ch, start, length));
/* 115 */     return get(ch, start, length, hash);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int obtainIndex(char[] ch, int start, int length, boolean clone) {
/* 127 */     int hash = hashHash(CharArray.hashCode(ch, start, length));
/*     */     
/* 129 */     if (this._readOnlyMap != null) {
/* 130 */       int index = this._readOnlyMap.get(ch, start, length, hash);
/* 131 */       if (index != -1) {
/* 132 */         return index;
/*     */       }
/*     */     } 
/*     */     
/* 136 */     int tableIndex = indexFor(hash, this._table.length);
/* 137 */     for (Entry e = this._table[tableIndex]; e != null; e = e._next) {
/* 138 */       if (e._hash == hash && e.equalsCharArray(ch, start, length)) {
/* 139 */         return e._value;
/*     */       }
/*     */     } 
/*     */     
/* 143 */     if (clone) {
/* 144 */       char[] chClone = new char[length];
/* 145 */       System.arraycopy(ch, start, chClone, 0, length);
/*     */       
/* 147 */       ch = chClone;
/* 148 */       start = 0;
/*     */     } 
/*     */     
/* 151 */     addEntry(ch, start, length, hash, this._size + this._readOnlyMapSize, tableIndex);
/* 152 */     return -1;
/*     */   }
/*     */   
/*     */   public final int getTotalCharacterCount() {
/* 156 */     return this._totalCharacterCount;
/*     */   }
/*     */   
/*     */   private final int get(char[] ch, int start, int length, int hash) {
/* 160 */     if (this._readOnlyMap != null) {
/* 161 */       int i = this._readOnlyMap.get(ch, start, length, hash);
/* 162 */       if (i != -1) {
/* 163 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 167 */     int tableIndex = indexFor(hash, this._table.length);
/* 168 */     for (Entry e = this._table[tableIndex]; e != null; e = e._next) {
/* 169 */       if (e._hash == hash && e.equalsCharArray(ch, start, length)) {
/* 170 */         return e._value;
/*     */       }
/*     */     } 
/*     */     
/* 174 */     return -1;
/*     */   }
/*     */   
/*     */   private final void addEntry(char[] ch, int start, int length, int hash, int value, int bucketIndex) {
/* 178 */     Entry e = this._table[bucketIndex];
/* 179 */     this._table[bucketIndex] = new Entry(ch, start, length, hash, value, e);
/* 180 */     this._totalCharacterCount += length;
/* 181 */     if (this._size++ >= this._threshold) {
/* 182 */       resize(2 * this._table.length);
/*     */     }
/*     */   }
/*     */   
/*     */   private final void resize(int newCapacity) {
/* 187 */     this._capacity = newCapacity;
/* 188 */     Entry[] oldTable = this._table;
/* 189 */     int oldCapacity = oldTable.length;
/* 190 */     if (oldCapacity == 1048576) {
/* 191 */       this._threshold = Integer.MAX_VALUE;
/*     */       
/*     */       return;
/*     */     } 
/* 195 */     Entry[] newTable = new Entry[this._capacity];
/* 196 */     transfer(newTable);
/* 197 */     this._table = newTable;
/* 198 */     this._threshold = (int)(this._capacity * this._loadFactor);
/*     */   }
/*     */   
/*     */   private final void transfer(Entry[] newTable) {
/* 202 */     Entry[] src = this._table;
/* 203 */     int newCapacity = newTable.length;
/* 204 */     for (int j = 0; j < src.length; j++) {
/* 205 */       Entry e = src[j];
/* 206 */       if (e != null) {
/* 207 */         src[j] = null;
/*     */         do {
/* 209 */           Entry next = e._next;
/* 210 */           int i = indexFor(e._hash, newCapacity);
/* 211 */           e._next = newTable[i];
/* 212 */           newTable[i] = e;
/* 213 */           e = next;
/* 214 */         } while (e != null);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\CharArrayIntMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */