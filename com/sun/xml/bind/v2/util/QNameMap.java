/*     */ package com.sun.xml.bind.v2.util;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
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
/*     */   public QNameMap() {
/* 106 */     this.threshold = 12;
/* 107 */     this.table = (Entry<V>[])new Entry[16];
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
/*     */ 
/*     */   
/*     */   public void put(String namespaceUri, String localname, V value) {
/* 123 */     assert localname != null;
/* 124 */     assert namespaceUri != null;
/*     */     
/* 126 */     assert localname == localname.intern();
/* 127 */     assert namespaceUri == namespaceUri.intern();
/*     */     
/* 129 */     int hash = hash(localname);
/* 130 */     int i = indexFor(hash, this.table.length);
/*     */     
/* 132 */     for (Entry<V> e = this.table[i]; e != null; e = e.next) {
/* 133 */       if (e.hash == hash && localname == e.localName && namespaceUri == e.nsUri) {
/* 134 */         e.value = value;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 139 */     addEntry(hash, namespaceUri, localname, value, i);
/*     */   }
/*     */ 
/*     */   
/*     */   public void put(QName name, V value) {
/* 144 */     put(name.getNamespaceURI(), name.getLocalPart(), value);
/*     */   }
/*     */   
/*     */   public void put(Name name, V value) {
/* 148 */     put(name.nsUri, name.localName, value);
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
/*     */   public V get(String nsUri, String localPart) {
/* 162 */     Entry<V> e = getEntry(nsUri, localPart);
/* 163 */     if (e == null) return null; 
/* 164 */     return e.value;
/*     */   }
/*     */   
/*     */   public V get(QName name) {
/* 168 */     return get(name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 177 */     return this.size;
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
/* 189 */     int numKeysToBeAdded = map.size();
/* 190 */     if (numKeysToBeAdded == 0) {
/* 191 */       return this;
/*     */     }
/*     */     
/* 194 */     if (numKeysToBeAdded > this.threshold) {
/* 195 */       int targetCapacity = numKeysToBeAdded;
/* 196 */       if (targetCapacity > 1073741824)
/* 197 */         targetCapacity = 1073741824; 
/* 198 */       int newCapacity = this.table.length;
/* 199 */       while (newCapacity < targetCapacity)
/* 200 */         newCapacity <<= 1; 
/* 201 */       if (newCapacity > this.table.length) {
/* 202 */         resize(newCapacity);
/*     */       }
/*     */     } 
/* 205 */     for (Entry<? extends V> e : map.entrySet())
/* 206 */       put(e.nsUri, e.localName, e.getValue()); 
/* 207 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int hash(String x) {
/* 216 */     int h = x.hashCode();
/*     */     
/* 218 */     h += h << 9 ^ 0xFFFFFFFF;
/* 219 */     h ^= h >>> 14;
/* 220 */     h += h << 4;
/* 221 */     h ^= h >>> 10;
/* 222 */     return h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int indexFor(int h, int length) {
/* 229 */     return h & length - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addEntry(int hash, String nsUri, String localName, V value, int bucketIndex) {
/* 239 */     Entry<V> e = this.table[bucketIndex];
/* 240 */     this.table[bucketIndex] = new Entry<V>(hash, nsUri, localName, value, e);
/* 241 */     if (this.size++ >= this.threshold) {
/* 242 */       resize(2 * this.table.length);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void resize(int newCapacity) {
/* 252 */     Entry<V>[] arrayOfEntry = this.table;
/* 253 */     int oldCapacity = arrayOfEntry.length;
/* 254 */     if (oldCapacity == 1073741824) {
/* 255 */       this.threshold = Integer.MAX_VALUE;
/*     */       
/*     */       return;
/*     */     } 
/* 259 */     Entry[] newTable = new Entry[newCapacity];
/* 260 */     transfer((Entry<V>[])newTable);
/* 261 */     this.table = (Entry<V>[])newTable;
/* 262 */     this.threshold = newCapacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transfer(Entry<V>[] newTable) {
/* 269 */     Entry<V>[] src = this.table;
/* 270 */     int newCapacity = newTable.length;
/* 271 */     for (int j = 0; j < src.length; j++) {
/* 272 */       Entry<V> e = src[j];
/* 273 */       if (e != null) {
/* 274 */         src[j] = null;
/*     */         do {
/* 276 */           Entry<V> next = e.next;
/* 277 */           int i = indexFor(e.hash, newCapacity);
/* 278 */           e.next = newTable[i];
/* 279 */           newTable[i] = e;
/* 280 */           e = next;
/* 281 */         } while (e != null);
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
/* 294 */     for (Entry<V> e : this.table) {
/* 295 */       if (e != null)
/* 296 */         return e; 
/*     */     } 
/* 298 */     return null;
/*     */   }
/*     */   
/*     */   public Collection<QName> keySet() {
/* 302 */     Set<QName> r = new HashSet<QName>();
/* 303 */     for (Entry<V> e : entrySet()) {
/* 304 */       r.add(e.createQName());
/*     */     }
/* 306 */     return r;
/*     */   }
/*     */   
/*     */   private abstract class HashIterator<E> implements Iterator<E> {
/*     */     QNameMap.Entry<V> next;
/*     */     int index;
/*     */     
/*     */     HashIterator() {
/* 314 */       QNameMap.Entry[] arrayOfEntry = QNameMap.this.table;
/* 315 */       int i = arrayOfEntry.length;
/* 316 */       QNameMap.Entry<V> n = null;
/* 317 */       if (QNameMap.this.size != 0) {
/* 318 */         while (i > 0 && (n = arrayOfEntry[--i]) == null);
/*     */       }
/* 320 */       this.next = n;
/* 321 */       this.index = i;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 325 */       return (this.next != null);
/*     */     }
/*     */     
/*     */     QNameMap.Entry<V> nextEntry() {
/* 329 */       QNameMap.Entry<V> e = this.next;
/* 330 */       if (e == null) {
/* 331 */         throw new NoSuchElementException();
/*     */       }
/* 333 */       QNameMap.Entry<V> n = e.next;
/* 334 */       QNameMap.Entry[] arrayOfEntry = QNameMap.this.table;
/* 335 */       int i = this.index;
/* 336 */       while (n == null && i > 0)
/* 337 */         n = arrayOfEntry[--i]; 
/* 338 */       this.index = i;
/* 339 */       this.next = n;
/* 340 */       return e;
/*     */     }
/*     */     
/*     */     public void remove() {
/* 344 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean containsKey(String nsUri, String localName) {
/* 349 */     return (getEntry(nsUri, localName) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 357 */     return (this.size == 0);
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
/* 376 */       this.value = v;
/* 377 */       this.next = n;
/* 378 */       this.nsUri = nsUri;
/* 379 */       this.localName = localName;
/* 380 */       this.hash = h;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public QName createQName() {
/* 387 */       return new QName(this.nsUri, this.localName);
/*     */     }
/*     */     
/*     */     public V getValue() {
/* 391 */       return this.value;
/*     */     }
/*     */     
/*     */     public V setValue(V newValue) {
/* 395 */       V oldValue = this.value;
/* 396 */       this.value = newValue;
/* 397 */       return oldValue;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 402 */       if (!(o instanceof Entry))
/* 403 */         return false; 
/* 404 */       Entry e = (Entry)o;
/* 405 */       String k1 = this.nsUri;
/* 406 */       String k2 = e.nsUri;
/* 407 */       String k3 = this.localName;
/* 408 */       String k4 = e.localName;
/* 409 */       if (k1 == k2 || (k1 != null && k1.equals(k2) && (k3 == k4 || (k3 != null && k3.equals(k4))))) {
/*     */         
/* 411 */         Object v1 = getValue();
/* 412 */         Object v2 = e.getValue();
/* 413 */         if (v1 == v2 || (v1 != null && v1.equals(v2)))
/* 414 */           return true; 
/*     */       } 
/* 416 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 421 */       return this.localName.hashCode() ^ ((this.value == null) ? 0 : this.value.hashCode());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 427 */       return '"' + this.nsUri + "\",\"" + this.localName + "\"=" + getValue();
/*     */     }
/*     */   }
/*     */   
/*     */   public Set<Entry<V>> entrySet() {
/* 432 */     Set<Entry<V>> es = this.entrySet;
/* 433 */     return (es != null) ? es : (this.entrySet = new EntrySet());
/*     */   }
/*     */   
/*     */   private Iterator<Entry<V>> newEntryIterator() {
/* 437 */     return new EntryIterator();
/*     */   }
/*     */   private class EntryIterator extends HashIterator<Entry<V>> { private EntryIterator() {}
/*     */     
/*     */     public QNameMap.Entry<V> next() {
/* 442 */       return nextEntry();
/*     */     } }
/*     */   
/*     */   private class EntrySet extends AbstractSet<Entry<V>> {
/*     */     public Iterator<QNameMap.Entry<V>> iterator() {
/* 447 */       return QNameMap.this.newEntryIterator();
/*     */     }
/*     */     private EntrySet() {}
/*     */     public boolean contains(Object o) {
/* 451 */       if (!(o instanceof QNameMap.Entry))
/* 452 */         return false; 
/* 453 */       QNameMap.Entry<V> e = (QNameMap.Entry<V>)o;
/* 454 */       QNameMap.Entry<V> candidate = QNameMap.this.getEntry(e.nsUri, e.localName);
/* 455 */       return (candidate != null && candidate.equals(e));
/*     */     }
/*     */     
/*     */     public boolean remove(Object o) {
/* 459 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     public int size() {
/* 462 */       return QNameMap.this.size;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private Entry<V> getEntry(String nsUri, String localName) {
/* 468 */     assert nsUri == nsUri.intern();
/* 469 */     assert localName == localName.intern();
/*     */     
/* 471 */     int hash = hash(localName);
/* 472 */     int i = indexFor(hash, this.table.length);
/* 473 */     Entry<V> e = this.table[i];
/* 474 */     while (e != null && (localName != e.localName || nsUri != e.nsUri))
/* 475 */       e = e.next; 
/* 476 */     return e;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 481 */     StringBuilder buf = new StringBuilder();
/* 482 */     buf.append('{');
/*     */     
/* 484 */     for (Entry<V> e : entrySet()) {
/* 485 */       if (buf.length() > 1)
/* 486 */         buf.append(','); 
/* 487 */       buf.append('[');
/* 488 */       buf.append(e);
/* 489 */       buf.append(']');
/*     */     } 
/*     */     
/* 492 */     buf.append('}');
/* 493 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v\\util\QNameMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */