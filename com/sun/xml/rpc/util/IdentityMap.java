/*      */ package com.sun.xml.rpc.util;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractCollection;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class IdentityMap
/*      */   extends MapBase
/*      */   implements Map, Serializable, Cloneable
/*      */ {
/*      */   private static final int DEFAULT_CAPACITY = 32;
/*      */   private static final int MINIMUM_CAPACITY = 4;
/*      */   private static final int MAXIMUM_CAPACITY = 536870912;
/*      */   private transient Object[] table;
/*      */   private int size;
/*      */   private volatile transient int modCount;
/*      */   private transient int threshold;
/*  196 */   private static final Object NULL_KEY = new Object();
/*      */ 
/*      */   
/*      */   private transient Set entrySet;
/*      */ 
/*      */   
/*      */   private static Object maskNull(Object key) {
/*  203 */     return (key == null) ? NULL_KEY : key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Object unmaskNull(Object key) {
/*  210 */     return (key == NULL_KEY) ? null : key;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IdentityMap()
/*      */   {
/*  912 */     this.entrySet = null; init(32); } public IdentityMap(int expectedMaxSize) { this.entrySet = null; if (expectedMaxSize < 0)
/*      */       throw new IllegalArgumentException("expectedMaxSize is negative: " + expectedMaxSize);  init(capacity(expectedMaxSize)); }
/*      */   private int capacity(int expectedMaxSize) { int result, minCapacity = 3 * expectedMaxSize / 2; if (minCapacity > 536870912 || minCapacity < 0) {
/*      */       result = 536870912;
/*      */     } else {
/*      */       result = 4; while (result < minCapacity)
/*      */         result <<= 1; 
/*      */     }  return result; }
/*      */   private void init(int initCapacity) { this.threshold = initCapacity * 2 / 3; this.table = new Object[2 * initCapacity]; }
/*      */   public IdentityMap(Map m) { this((int)((1 + m.size()) * 1.1D)); putAll(m); }
/*      */   public int size() { return this.size; }
/*      */   public boolean isEmpty() { return (this.size == 0); }
/*      */   private static int hash(Object x, int length) { int h = System.identityHashCode(x); return (h << 1) - (h << 8) & length - 1; }
/*      */   private static int nextKeyIndex(int i, int len) { return (i + 2 < len) ? (i + 2) : 0; }
/*      */   public Object get(Object key) { Object k = maskNull(key); Object[] tab = this.table; int len = tab.length; int i = hash(k, len); while (true) {
/*      */       Object item = tab[i]; if (item == k)
/*      */         return tab[i + 1];  if (item == null)
/*      */         return item;  i = nextKeyIndex(i, len);
/*      */     }  }
/*      */   public boolean containsKey(Object key) { Object k = maskNull(key); Object[] tab = this.table; int len = tab.length; int i = hash(k, len); while (true) {
/*      */       Object item = tab[i]; if (item == k)
/*      */         return true;  if (item == null)
/*      */         return false;  i = nextKeyIndex(i, len);
/*      */     }  }
/*      */   public boolean containsValue(Object value) { Object[] tab = this.table; for (int i = 1; i < tab.length; i += 2) {
/*      */       if (tab[i] == value)
/*      */         return true; 
/*      */     }  return false; }
/*      */   private boolean containsMapping(Object key, Object value) { Object k = maskNull(key); Object[] tab = this.table; int len = tab.length; int i = hash(k, len); while (true) {
/*      */       Object item = tab[i]; if (item == k)
/*      */         return (tab[i + 1] == value);  if (item == null)
/*      */         return false;  i = nextKeyIndex(i, len);
/*      */     }  }
/*      */   public Object put(Object key, Object value) { Object k = maskNull(key); Object[] tab = this.table; int len = tab.length; int i = hash(k, len); Object item; while ((item = tab[i]) != null) {
/*      */       if (item == k) {
/*      */         Object oldValue = tab[i + 1]; tab[i + 1] = value; return oldValue;
/*      */       }  i = nextKeyIndex(i, len);
/*      */     }  this.modCount++; tab[i] = k;
/*      */     tab[i + 1] = value;
/*      */     if (++this.size >= this.threshold)
/*      */       resize(len); 
/*  953 */     return null; } public Set keySet() { Set ks = this.keySet;
/*  954 */     if (ks != null) {
/*  955 */       return ks;
/*      */     }
/*  957 */     return this.keySet = new KeySet(); } private void resize(int newCapacity) { int newLength = newCapacity * 2; Object[] oldTable = this.table; int oldLength = oldTable.length; if (oldLength == 1073741824) { if (this.threshold == 536870911) throw new IllegalStateException("Capacity exhausted.");  this.threshold = 536870911; return; }  if (oldLength >= newLength) return;  Object[] newTable = new Object[newLength]; this.threshold = newLength / 3; for (int j = 0; j < oldLength; j += 2) { Object key = oldTable[j]; if (key != null) { Object value = oldTable[j + 1]; oldTable[j] = null; oldTable[j + 1] = null; int i = hash(key, newLength); while (newTable[i] != null) i = nextKeyIndex(i, newLength);  newTable[i] = key; newTable[i + 1] = value; }  }  this.table = newTable; } public void putAll(Map t) { int n = t.size(); if (n == 0) return;  if (n > this.threshold) resize(capacity(n));  for (Iterator<Map.Entry> it = t.entrySet().iterator(); it.hasNext(); ) { Map.Entry e = it.next(); put(e.getKey(), e.getValue()); }  } public Object remove(Object key) { Object k = maskNull(key); Object[] tab = this.table; int len = tab.length; int i = hash(k, len); while (true) { Object item = tab[i]; if (item == k) { this.modCount++; this.size--; Object oldValue = tab[i + 1]; tab[i + 1] = null; tab[i] = null; closeDeletion(i); return oldValue; }  if (item == null) return null;  i = nextKeyIndex(i, len); }  } private boolean removeMapping(Object key, Object value) { Object k = maskNull(key); Object[] tab = this.table; int len = tab.length; int i = hash(k, len); while (true) { Object item = tab[i]; if (item == k) { if (tab[i + 1] != value) return false;  this.modCount++; this.size--; tab[i] = null; tab[i + 1] = null; closeDeletion(i); return true; }  if (item == null) return false;  i = nextKeyIndex(i, len); }  } private void closeDeletion(int d) { Object[] tab = this.table; int len = tab.length; int i = nextKeyIndex(d, len); Object item; for (; (item = tab[i]) != null; i = nextKeyIndex(i, len)) { int r = hash(item, len); if ((i < r && (r <= d || d <= i)) || (r <= d && d <= i)) { tab[d] = item; tab[d + 1] = tab[i + 1]; tab[i] = null; tab[i + 1] = null; d = i; }  }  } public void clear() { this.modCount++; Object[] tab = this.table; for (int i = 0; i < tab.length; i++) tab[i] = null;  this.size = 0; } public boolean equals(Object o) { if (o == this) return true;  if (o instanceof IdentityMap) { IdentityMap m = (IdentityMap)o; if (m.size() != this.size) return false;  Object[] tab = m.table; for (int i = 0; i < tab.length; i += 2) { Object k = tab[i]; if (k != null && !containsMapping(k, tab[i + 1])) return false;  }  return true; }  if (o instanceof Map) { Map m = (Map)o; return entrySet().equals(m.entrySet()); }  return false; } public int hashCode() { int result = 0; Object[] tab = this.table; for (int i = 0; i < tab.length; i += 2) { Object key = tab[i]; if (key != null) { Object k = unmaskNull(key); result += System.identityHashCode(k) ^ System.identityHashCode(tab[i + 1]); }  }  return result; } public Object clone() { try { IdentityMap t = (IdentityMap)super.clone(); t.entrySet = null; t.table = (Object[])this.table.clone(); return t; } catch (CloneNotSupportedException e) { throw new InternalError(); }  } private abstract class IdentityMapIterator implements Iterator {
/*      */     int index = (IdentityMap.this.size != 0) ? 0 : IdentityMap.this.table.length; int expectedModCount = IdentityMap.this.modCount; int lastReturnedIndex = -1; boolean indexValid; Object[] traversalTable = IdentityMap.this.table; public boolean hasNext() { Object[] tab = this.traversalTable; for (int i = this.index; i < tab.length; i += 2) { Object key = tab[i]; if (key != null) { this.index = i; return this.indexValid = true; }  }  this.index = tab.length; return false; } protected int nextIndex() { if (IdentityMap.this.modCount != this.expectedModCount) throw new ConcurrentModificationException();  if (!this.indexValid && !hasNext()) throw new NoSuchElementException();  this.indexValid = false; this.lastReturnedIndex = this.index; this.index += 2; return this.lastReturnedIndex; } public void remove() { if (this.lastReturnedIndex == -1) throw new IllegalStateException();  if (IdentityMap.this.modCount != this.expectedModCount) throw new ConcurrentModificationException();  this.expectedModCount = ++IdentityMap.this.modCount; int deletedSlot = this.lastReturnedIndex; this.lastReturnedIndex = -1; IdentityMap.this.size--; this.index = deletedSlot; this.indexValid = false; Object[] tab = this.traversalTable; int len = tab.length; int d = deletedSlot; Object key = tab[d]; tab[d] = null; tab[d + 1] = null; if (tab != IdentityMap.this.table) { IdentityMap.this.remove(key); this.expectedModCount = IdentityMap.this.modCount; return; }  int i = IdentityMap.nextKeyIndex(d, len); Object item; for (; (item = tab[i]) != null; i = IdentityMap.nextKeyIndex(i, len)) { int r = IdentityMap.hash(item, len); if ((i < r && (r <= d || d <= i)) || (r <= d && d <= i)) { if (i < deletedSlot && d >= deletedSlot && this.traversalTable == IdentityMap.this.table) { int remaining = len - deletedSlot; Object[] newTable = new Object[remaining]; System.arraycopy(tab, deletedSlot, newTable, 0, remaining); this.traversalTable = newTable; this.index = 0; }  tab[d] = item; tab[d + 1] = tab[i + 1]; tab[i] = null; tab[i + 1] = null; d = i; }  }  } private IdentityMapIterator() {} } private class KeyIterator extends IdentityMapIterator {
/*      */     private KeyIterator() {} public Object next() { return IdentityMap.unmaskNull(this.traversalTable[nextIndex()]); } } private class ValueIterator extends IdentityMapIterator {
/*      */     private ValueIterator() {} public Object next() { return this.traversalTable[nextIndex() + 1]; } } private class EntryIterator extends IdentityMapIterator implements Map.Entry {
/*      */     private EntryIterator() {} public Object next() { nextIndex(); return this; } public Object getKey() { if (this.lastReturnedIndex < 0) throw new IllegalStateException("Entry was removed");  return IdentityMap.unmaskNull(this.traversalTable[this.lastReturnedIndex]); } public Object getValue() { if (this.lastReturnedIndex < 0) throw new IllegalStateException("Entry was removed");  return this.traversalTable[this.lastReturnedIndex + 1]; } public Object setValue(Object value) { if (this.lastReturnedIndex < 0) throw new IllegalStateException("Entry was removed");  Object oldValue = this.traversalTable[this.lastReturnedIndex + 1]; this.traversalTable[this.lastReturnedIndex + 1] = value; if (this.traversalTable != IdentityMap.this.table) IdentityMap.this.put(this.traversalTable[this.lastReturnedIndex], value);  return oldValue; } public boolean equals(Object o) { if (!(o instanceof Map.Entry)) return false;  Map.Entry e = (Map.Entry)o; return (e.getKey() == getKey() && e.getValue() == getValue()); } public int hashCode() { return System.identityHashCode(getKey()) ^ System.identityHashCode(getValue()); } public String toString() { return getKey() + "=" + getValue(); } } private class KeySet extends AbstractSet {
/*  962 */     private KeySet() {} public Iterator iterator() { return new IdentityMap.KeyIterator(); }
/*      */     
/*      */     public int size() {
/*  965 */       return IdentityMap.this.size;
/*      */     }
/*      */     public boolean contains(Object o) {
/*  968 */       return IdentityMap.this.containsKey(o);
/*      */     }
/*      */     public boolean remove(Object o) {
/*  971 */       int oldSize = IdentityMap.this.size;
/*  972 */       IdentityMap.this.remove(o);
/*  973 */       return (IdentityMap.this.size != oldSize);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection c) {
/*  981 */       boolean modified = false;
/*  982 */       for (Iterator i = iterator(); i.hasNext();) {
/*  983 */         if (c.contains(i.next())) {
/*  984 */           i.remove();
/*  985 */           modified = true;
/*      */         } 
/*      */       } 
/*  988 */       return modified;
/*      */     }
/*      */     public void clear() {
/*  991 */       IdentityMap.this.clear();
/*      */     }
/*      */     public int hashCode() {
/*  994 */       int result = 0;
/*  995 */       for (Iterator i = iterator(); i.hasNext();)
/*  996 */         result += System.identityHashCode(i.next()); 
/*  997 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Collection values() {
/* 1023 */     Collection vs = this.values;
/* 1024 */     if (vs != null) {
/* 1025 */       return vs;
/*      */     }
/* 1027 */     return this.values = new Values();
/*      */   }
/*      */   private class Values extends AbstractCollection { private Values() {}
/*      */     
/*      */     public Iterator iterator() {
/* 1032 */       return new IdentityMap.ValueIterator();
/*      */     }
/*      */     public int size() {
/* 1035 */       return IdentityMap.this.size;
/*      */     }
/*      */     public boolean contains(Object o) {
/* 1038 */       return IdentityMap.this.containsValue(o);
/*      */     }
/*      */     public boolean remove(Object o) {
/* 1041 */       for (Iterator i = iterator(); i.hasNext();) {
/* 1042 */         if (i.next() == o) {
/* 1043 */           i.remove();
/* 1044 */           return true;
/*      */         } 
/*      */       } 
/* 1047 */       return false;
/*      */     }
/*      */     public void clear() {
/* 1050 */       IdentityMap.this.clear();
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Set entrySet() {
/* 1092 */     Set es = this.entrySet;
/* 1093 */     if (es != null) {
/* 1094 */       return es;
/*      */     }
/* 1096 */     return this.entrySet = new EntrySet();
/*      */   }
/*      */   private class EntrySet extends AbstractSet { private EntrySet() {}
/*      */     
/*      */     public Iterator iterator() {
/* 1101 */       return new IdentityMap.EntryIterator();
/*      */     }
/*      */     public boolean contains(Object o) {
/* 1104 */       if (!(o instanceof Map.Entry))
/* 1105 */         return false; 
/* 1106 */       Map.Entry entry = (Map.Entry)o;
/* 1107 */       return IdentityMap.this.containsMapping(entry.getKey(), entry.getValue());
/*      */     }
/*      */     public boolean remove(Object o) {
/* 1110 */       if (!(o instanceof Map.Entry))
/* 1111 */         return false; 
/* 1112 */       Map.Entry entry = (Map.Entry)o;
/* 1113 */       return IdentityMap.this.removeMapping(entry.getKey(), entry.getValue());
/*      */     }
/*      */     public int size() {
/* 1116 */       return IdentityMap.this.size;
/*      */     }
/*      */     public void clear() {
/* 1119 */       IdentityMap.this.clear();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean removeAll(Collection c) {
/* 1127 */       boolean modified = false;
/* 1128 */       for (Iterator i = iterator(); i.hasNext();) {
/* 1129 */         if (c.contains(i.next())) {
/* 1130 */           i.remove();
/* 1131 */           modified = true;
/*      */         } 
/*      */       } 
/* 1134 */       return modified;
/*      */     }
/*      */     
/*      */     public Object[] toArray() {
/* 1138 */       Collection<MapBase.SimpleEntry> c = new ArrayList(size());
/* 1139 */       for (Iterator<Map.Entry> i = iterator(); i.hasNext();)
/* 1140 */         c.add(new MapBase.SimpleEntry(i.next())); 
/* 1141 */       return c.toArray();
/*      */     }
/*      */     public Object[] toArray(Object[] a) {
/* 1144 */       Collection<MapBase.SimpleEntry> c = new ArrayList(size());
/* 1145 */       for (Iterator<Map.Entry> i = iterator(); i.hasNext();)
/* 1146 */         c.add(new MapBase.SimpleEntry(i.next())); 
/* 1147 */       return c.toArray(a);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObject(ObjectOutputStream s) throws IOException {
/* 1165 */     s.defaultWriteObject();
/*      */ 
/*      */     
/* 1168 */     s.writeInt(this.size);
/*      */ 
/*      */     
/* 1171 */     Object[] tab = this.table;
/* 1172 */     for (int i = 0; i < tab.length; i += 2) {
/* 1173 */       Object key = tab[i];
/* 1174 */       if (key != null) {
/* 1175 */         s.writeObject(unmaskNull(key));
/* 1176 */         s.writeObject(tab[i + 1]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
/* 1188 */     s.defaultReadObject();
/*      */ 
/*      */     
/* 1191 */     int size = s.readInt();
/*      */ 
/*      */     
/* 1194 */     init(capacity(size * 4 / 3));
/*      */ 
/*      */     
/* 1197 */     for (int i = 0; i < size; i++) {
/* 1198 */       Object key = s.readObject();
/* 1199 */       Object value = s.readObject();
/* 1200 */       put(key, value);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\IdentityMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */