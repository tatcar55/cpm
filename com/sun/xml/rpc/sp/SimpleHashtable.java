/*     */ package com.sun.xml.rpc.sp;
/*     */ 
/*     */ import java.util.Iterator;
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
/*     */ final class SimpleHashtable
/*     */   implements Iterator
/*     */ {
/*     */   private Entry[] table;
/*  65 */   private Entry current = null;
/*  66 */   private int currentBucket = 0;
/*     */ 
/*     */   
/*     */   private int count;
/*     */ 
/*     */   
/*     */   private int threshold;
/*     */ 
/*     */   
/*     */   private static final float loadFactor = 0.75F;
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleHashtable(int initialCapacity) {
/*  80 */     if (initialCapacity < 0) {
/*  81 */       throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
/*     */     }
/*  83 */     if (initialCapacity == 0)
/*  84 */       initialCapacity = 1; 
/*  85 */     this.table = new Entry[initialCapacity];
/*  86 */     this.threshold = (int)(initialCapacity * 0.75F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SimpleHashtable() {
/*  93 */     this(11);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  99 */     this.count = 0;
/* 100 */     this.currentBucket = 0;
/* 101 */     this.current = null;
/* 102 */     for (int i = 0; i < this.table.length; i++) {
/* 103 */       this.table[i] = null;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 112 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator keys() {
/* 122 */     this.currentBucket = 0;
/* 123 */     this.current = null;
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() {
/* 132 */     if (this.current != null)
/* 133 */       return true; 
/* 134 */     while (this.currentBucket < this.table.length) {
/* 135 */       this.current = this.table[this.currentBucket++];
/* 136 */       if (this.current != null)
/* 137 */         return true; 
/*     */     } 
/* 139 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object next() {
/* 149 */     if (this.current == null)
/* 150 */       throw new IllegalStateException(); 
/* 151 */     Object retval = this.current.key;
/* 152 */     this.current = this.current.next;
/* 153 */     return retval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove() {
/* 160 */     if (this.current == null)
/* 161 */       throw new IllegalStateException(); 
/* 162 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object get(String key) {
/* 169 */     Entry[] tab = this.table;
/* 170 */     int hash = key.hashCode();
/* 171 */     int index = (hash & Integer.MAX_VALUE) % tab.length;
/* 172 */     for (Entry e = tab[index]; e != null; e = e.next) {
/* 173 */       if (e.hash == hash && e.key == key)
/* 174 */         return e.value; 
/*     */     } 
/* 176 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getNonInterned(String key) {
/* 184 */     Entry[] tab = this.table;
/* 185 */     int hash = key.hashCode();
/* 186 */     int index = (hash & Integer.MAX_VALUE) % tab.length;
/* 187 */     for (Entry e = tab[index]; e != null; e = e.next) {
/* 188 */       if (e.hash == hash && e.key.equals(key))
/* 189 */         return e.value; 
/*     */     } 
/* 191 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void rehash() {
/* 202 */     int oldCapacity = this.table.length;
/* 203 */     Entry[] oldMap = this.table;
/*     */     
/* 205 */     int newCapacity = oldCapacity * 2 + 1;
/* 206 */     Entry[] newMap = new Entry[newCapacity];
/*     */     
/* 208 */     this.threshold = (int)(newCapacity * 0.75F);
/* 209 */     this.table = newMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     for (int i = oldCapacity; i-- > 0;) {
/* 219 */       for (Entry old = oldMap[i]; old != null; ) {
/* 220 */         Entry e = old;
/* 221 */         old = old.next;
/*     */         
/* 223 */         int index = (e.hash & Integer.MAX_VALUE) % newCapacity;
/* 224 */         e.next = newMap[index];
/* 225 */         newMap[index] = e;
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
/*     */ 
/*     */   
/*     */   public Object put(Object key, Object value) {
/* 240 */     if (value == null) {
/* 241 */       throw new NullPointerException();
/*     */     }
/*     */ 
/*     */     
/* 245 */     Entry[] tab = this.table;
/* 246 */     int hash = key.hashCode();
/* 247 */     int index = (hash & Integer.MAX_VALUE) % tab.length; Entry e;
/* 248 */     for (e = tab[index]; e != null; e = e.next) {
/*     */       
/* 250 */       if (e.hash == hash && e.key == key) {
/* 251 */         Object old = e.value;
/* 252 */         e.value = value;
/* 253 */         return old;
/*     */       } 
/*     */     } 
/*     */     
/* 257 */     if (this.count >= this.threshold) {
/*     */       
/* 259 */       rehash();
/*     */       
/* 261 */       tab = this.table;
/* 262 */       index = (hash & Integer.MAX_VALUE) % tab.length;
/*     */     } 
/*     */ 
/*     */     
/* 266 */     e = new Entry(hash, key, value, tab[index]);
/* 267 */     tab[index] = e;
/* 268 */     this.count++;
/* 269 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private static class Entry
/*     */   {
/*     */     int hash;
/*     */     
/*     */     Object key;
/*     */     Object value;
/*     */     Entry next;
/*     */     
/*     */     protected Entry(int hash, Object key, Object value, Entry next) {
/* 282 */       this.hash = hash;
/* 283 */       this.key = key;
/* 284 */       this.value = value;
/* 285 */       this.next = next;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\sp\SimpleHashtable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */