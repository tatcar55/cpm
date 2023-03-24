/*     */ package com.sun.xml.ws.util;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
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
/*     */ public final class QNameMap<V>
/*     */ {
/*     */   private static final int DEFAULT_INITIAL_CAPACITY = 16;
/*     */   private static final int MAXIMUM_CAPACITY = 1073741824;
/*  79 */   transient Entry<V>[] table = (Entry<V>[])new Entry[16];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   transient int size;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int threshold;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final float DEFAULT_LOAD_FACTOR = 0.75F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   private Set<Entry<V>> entrySet = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private transient Iterable<V> views;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(String namespaceUri, String localname, V value) {
/* 123 */     assert localname != null;
/* 124 */     assert namespaceUri != null;
/*     */     
/* 126 */     int hash = hash(localname);
/* 127 */     int i = indexFor(hash, this.table.length);
/*     */     
/* 129 */     for (Entry<V> e = this.table[i]; e != null; e = e.next) {
/* 130 */       if (e.hash == hash && localname.equals(e.localName) && namespaceUri.equals(e.nsUri)) {
/* 131 */         e.value = value;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 136 */     addEntry(hash, namespaceUri, localname, value, i);
/*     */   }
/*     */ 
/*     */   
/*     */   public void put(QName name, V value) {
/* 141 */     put(name.getNamespaceURI(), name.getLocalPart(), value);
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
/*     */ 
/*     */   
/*     */   public V get(@NotNull String nsUri, String localPart) {
/* 155 */     Entry<V> e = getEntry(nsUri, localPart);
/* 156 */     if (e == null) return null; 
/* 157 */     return e.value;
/*     */   }
/*     */   
/*     */   public V get(QName name) {
/* 161 */     return get(name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 170 */     return this.size;
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
/*     */   public QNameMap<V> putAll(QNameMap<? extends V> map) {
/* 182 */     int numKeysToBeAdded = map.size();
/* 183 */     if (numKeysToBeAdded == 0) {
/* 184 */       return this;
/*     */     }
/*     */     
/* 187 */     if (numKeysToBeAdded > this.threshold) {
/* 188 */       int targetCapacity = numKeysToBeAdded;
/* 189 */       if (targetCapacity > 1073741824)
/* 190 */         targetCapacity = 1073741824; 
/* 191 */       int newCapacity = this.table.length;
/* 192 */       while (newCapacity < targetCapacity)
/* 193 */         newCapacity <<= 1; 
/* 194 */       if (newCapacity > this.table.length) {
/* 195 */         resize(newCapacity);
/*     */       }
/*     */     } 
/* 198 */     for (Entry<? extends V> e : map.entrySet())
/* 199 */       put(e.nsUri, e.localName, e.getValue()); 
/* 200 */     return this;
/*     */   }
/*     */   
/*     */   public QNameMap<V> putAll(Map<QName, ? extends V> map) {
/* 204 */     for (Map.Entry<QName, ? extends V> e : map.entrySet()) {
/* 205 */       QName qn = e.getKey();
/* 206 */       put(qn.getNamespaceURI(), qn.getLocalPart(), e.getValue());
/*     */     } 
/* 208 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int hash(String x) {
/* 217 */     int h = x.hashCode();
/*     */     
/* 219 */     h += h << 9 ^ 0xFFFFFFFF;
/* 220 */     h ^= h >>> 14;
/* 221 */     h += h << 4;
/* 222 */     h ^= h >>> 10;
/* 223 */     return h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexFor(int h, int length) {
/* 230 */     return h & length - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addEntry(int hash, String nsUri, String localName, V value, int bucketIndex) {
/* 240 */     Entry<V> e = this.table[bucketIndex];
/* 241 */     this.table[bucketIndex] = new Entry<V>(hash, nsUri, localName, value, e);
/* 242 */     if (this.size++ >= this.threshold) {
/* 243 */       resize(2 * this.table.length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resize(int newCapacity) {
/* 253 */     Entry<V>[] arrayOfEntry = this.table;
/* 254 */     int oldCapacity = arrayOfEntry.length;
/* 255 */     if (oldCapacity == 1073741824) {
/* 256 */       this.threshold = Integer.MAX_VALUE;
/*     */       
/*     */       return;
/*     */     } 
/* 260 */     Entry[] newTable = new Entry[newCapacity];
/* 261 */     transfer((Entry<V>[])newTable);
/* 262 */     this.table = (Entry<V>[])newTable;
/* 263 */     this.threshold = newCapacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transfer(Entry<V>[] newTable) {
/* 270 */     Entry<V>[] src = this.table;
/* 271 */     int newCapacity = newTable.length;
/* 272 */     for (int j = 0; j < src.length; j++) {
/* 273 */       Entry<V> e = src[j];
/* 274 */       if (e != null) {
/* 275 */         src[j] = null;
/*     */         do {
/* 277 */           Entry<V> next = e.next;
/* 278 */           int i = indexFor(e.hash, newCapacity);
/* 279 */           e.next = newTable[i];
/* 280 */           newTable[i] = e;
/* 281 */           e = next;
/* 282 */         } while (e != null);
/*     */       } 
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
/*     */   public Entry<V> getOne() {
/* 295 */     for (Entry<V> e : this.table) {
/* 296 */       if (e != null)
/* 297 */         return e; 
/*     */     } 
/* 299 */     return null;
/*     */   }
/*     */   
/*     */   public Collection<QName> keySet() {
/* 303 */     Set<QName> r = new HashSet<QName>();
/* 304 */     for (Entry<V> e : entrySet()) {
/* 305 */       r.add(e.createQName());
/*     */     }
/* 307 */     return r;
/*     */   }
/*     */   
/*     */   public Iterable<V> values() {
/* 311 */     return this.views;
/*     */   }
/*     */   public QNameMap() {
/* 314 */     this.views = new Iterable<V>() {
/*     */         public Iterator<V> iterator() {
/* 316 */           return new QNameMap.ValueIterator();
/*     */         }
/*     */       };
/*     */     this.threshold = 12;
/*     */     this.table = (Entry<V>[])new Entry[16];
/*     */   }
/*     */   private abstract class HashIterator<E> implements Iterator<E> { QNameMap.Entry<V> next;
/*     */     
/*     */     HashIterator() {
/* 325 */       QNameMap.Entry[] arrayOfEntry = QNameMap.this.table;
/* 326 */       int i = arrayOfEntry.length;
/* 327 */       QNameMap.Entry<V> n = null;
/* 328 */       if (QNameMap.this.size != 0) {
/* 329 */         while (i > 0 && (n = arrayOfEntry[--i]) == null);
/*     */       }
/*     */       
/* 332 */       this.next = n;
/* 333 */       this.index = i;
/*     */     }
/*     */     int index;
/*     */     public boolean hasNext() {
/* 337 */       return (this.next != null);
/*     */     }
/*     */     
/*     */     QNameMap.Entry<V> nextEntry() {
/* 341 */       QNameMap.Entry<V> e = this.next;
/* 342 */       if (e == null) {
/* 343 */         throw new NoSuchElementException();
/*     */       }
/* 345 */       QNameMap.Entry<V> n = e.next;
/* 346 */       QNameMap.Entry[] arrayOfEntry = QNameMap.this.table;
/* 347 */       int i = this.index;
/* 348 */       while (n == null && i > 0)
/* 349 */         n = arrayOfEntry[--i]; 
/* 350 */       this.index = i;
/* 351 */       this.next = n;
/* 352 */       return e;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 356 */       throw new UnsupportedOperationException();
/*     */     } }
/*     */   
/*     */   private class ValueIterator extends HashIterator<V> { private ValueIterator() {}
/*     */     
/*     */     public V next() {
/* 362 */       return (nextEntry()).value;
/*     */     } }
/*     */ 
/*     */   
/*     */   public boolean containsKey(@NotNull String nsUri, String localName) {
/* 367 */     return (getEntry(nsUri, localName) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 375 */     return (this.size == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class Entry<V>
/*     */   {
/*     */     public final String nsUri;
/*     */     
/*     */     public final String localName;
/*     */     
/*     */     V value;
/*     */     
/*     */     final int hash;
/*     */     
/*     */     Entry<V> next;
/*     */ 
/*     */     
/*     */     Entry(int h, String nsUri, String localName, V v, Entry<V> n) {
/* 394 */       this.value = v;
/* 395 */       this.next = n;
/* 396 */       this.nsUri = nsUri;
/* 397 */       this.localName = localName;
/* 398 */       this.hash = h;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public QName createQName() {
/* 405 */       return new QName(this.nsUri, this.localName);
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 409 */       return this.value;
/*     */     }
/*     */     
/*     */     public V setValue(V newValue) {
/* 413 */       V oldValue = this.value;
/* 414 */       this.value = newValue;
/* 415 */       return oldValue;
/*     */     }
/*     */     
/*     */     public boolean equals(Object o) {
/* 419 */       if (!(o instanceof Entry))
/* 420 */         return false; 
/* 421 */       Entry e = (Entry)o;
/* 422 */       String k1 = this.nsUri;
/* 423 */       String k2 = e.nsUri;
/* 424 */       String k3 = this.localName;
/* 425 */       String k4 = e.localName;
/* 426 */       if (k1.equals(k2) && k3.equals(k4)) {
/* 427 */         Object v1 = getValue();
/* 428 */         Object v2 = e.getValue();
/* 429 */         if (v1 == v2 || (v1 != null && v1.equals(v2)))
/* 430 */           return true; 
/*     */       } 
/* 432 */       return false;
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 436 */       return this.localName.hashCode() ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 441 */       return '"' + this.nsUri + "\",\"" + this.localName + "\"=" + getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<Entry<V>> entrySet() {
/* 446 */     Set<Entry<V>> es = this.entrySet;
/* 447 */     return (es != null) ? es : (this.entrySet = new EntrySet());
/*     */   }
/*     */   
/*     */   private Iterator<Entry<V>> newEntryIterator() {
/* 451 */     return new EntryIterator();
/*     */   }
/*     */   private class EntryIterator extends HashIterator<Entry<V>> { private EntryIterator() {}
/*     */     
/*     */     public QNameMap.Entry<V> next() {
/* 456 */       return nextEntry();
/*     */     } }
/*     */   private class EntrySet extends AbstractSet<Entry<V>> { private EntrySet() {}
/*     */     
/*     */     public Iterator<QNameMap.Entry<V>> iterator() {
/* 461 */       return QNameMap.this.newEntryIterator();
/*     */     }
/*     */     public boolean contains(Object o) {
/* 464 */       if (!(o instanceof QNameMap.Entry))
/* 465 */         return false; 
/* 466 */       QNameMap.Entry<V> e = (QNameMap.Entry<V>)o;
/* 467 */       QNameMap.Entry<V> candidate = QNameMap.this.getEntry(e.nsUri, e.localName);
/* 468 */       return (candidate != null && candidate.equals(e));
/*     */     }
/*     */     public boolean remove(Object o) {
/* 471 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     public int size() {
/* 474 */       return QNameMap.this.size;
/*     */     } }
/*     */ 
/*     */   
/*     */   private Entry<V> getEntry(@NotNull String nsUri, String localName) {
/* 479 */     int hash = hash(localName);
/* 480 */     int i = indexFor(hash, this.table.length);
/* 481 */     Entry<V> e = this.table[i];
/* 482 */     while (e != null && (!localName.equals(e.localName) || !nsUri.equals(e.nsUri)))
/* 483 */       e = e.next; 
/* 484 */     return e;
/*     */   }
/*     */   
/*     */   public String toString() {
/* 488 */     StringBuilder buf = new StringBuilder();
/* 489 */     buf.append('{');
/*     */     
/* 491 */     for (Entry<V> e : entrySet()) {
/* 492 */       if (buf.length() > 1)
/* 493 */         buf.append(','); 
/* 494 */       buf.append('[');
/* 495 */       buf.append(e);
/* 496 */       buf.append(']');
/*     */     } 
/*     */     
/* 499 */     buf.append('}');
/* 500 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\w\\util\QNameMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */