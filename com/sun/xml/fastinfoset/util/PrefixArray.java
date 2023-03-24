/*     */ package com.sun.xml.fastinfoset.util;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.jvnet.fastinfoset.FastInfosetException;
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
/*     */ public class PrefixArray
/*     */   extends ValueArray
/*     */ {
/*     */   public static final int PREFIX_MAP_SIZE = 64;
/*     */   private int _initialCapacity;
/*     */   public String[] _array;
/*     */   private PrefixArray _readOnlyArray;
/*     */   
/*     */   private static class PrefixEntry
/*     */   {
/*     */     private PrefixEntry next;
/*     */     private int prefixId;
/*     */     
/*     */     private PrefixEntry() {}
/*     */   }
/*  40 */   private PrefixEntry[] _prefixMap = new PrefixEntry[64];
/*     */   
/*     */   private PrefixEntry _prefixPool;
/*     */   
/*     */   private NamespaceEntry _namespacePool;
/*     */   
/*     */   private NamespaceEntry[] _inScopeNamespaces;
/*     */   public int[] _currentInScope;
/*     */   public int _declarationId;
/*     */   
/*     */   private static class NamespaceEntry
/*     */   {
/*     */     private NamespaceEntry next;
/*     */     private int declarationId;
/*     */     private int namespaceIndex;
/*     */     private String prefix;
/*     */     private String namespaceName;
/*     */     private int prefixEntryIndex;
/*     */     
/*     */     private NamespaceEntry() {}
/*     */   }
/*     */   
/*     */   public PrefixArray(int initialCapacity, int maximumCapacity) {
/*  63 */     this._initialCapacity = initialCapacity;
/*  64 */     this._maximumCapacity = maximumCapacity;
/*     */     
/*  66 */     this._array = new String[initialCapacity];
/*     */ 
/*     */ 
/*     */     
/*  70 */     this._inScopeNamespaces = new NamespaceEntry[initialCapacity + 2];
/*  71 */     this._currentInScope = new int[initialCapacity + 2];
/*     */     
/*  73 */     increaseNamespacePool(initialCapacity);
/*  74 */     increasePrefixPool(initialCapacity);
/*     */     
/*  76 */     initializeEntries();
/*     */   }
/*     */   
/*     */   public PrefixArray() {
/*  80 */     this(10, 2147483647);
/*     */   }
/*     */   
/*     */   private final void initializeEntries() {
/*  84 */     this._inScopeNamespaces[0] = this._namespacePool;
/*  85 */     this._namespacePool = this._namespacePool.next;
/*  86 */     (this._inScopeNamespaces[0]).next = null;
/*  87 */     (this._inScopeNamespaces[0]).prefix = "";
/*  88 */     (this._inScopeNamespaces[0]).namespaceName = "";
/*  89 */     (this._inScopeNamespaces[0]).namespaceIndex = this._currentInScope[0] = 0;
/*     */     
/*  91 */     int index = KeyIntMap.indexFor(KeyIntMap.hashHash((this._inScopeNamespaces[0]).prefix.hashCode()), this._prefixMap.length);
/*  92 */     this._prefixMap[index] = this._prefixPool;
/*  93 */     this._prefixPool = this._prefixPool.next;
/*  94 */     (this._prefixMap[index]).next = null;
/*  95 */     (this._prefixMap[index]).prefixId = 0;
/*     */ 
/*     */     
/*  98 */     this._inScopeNamespaces[1] = this._namespacePool;
/*  99 */     this._namespacePool = this._namespacePool.next;
/* 100 */     (this._inScopeNamespaces[1]).next = null;
/* 101 */     (this._inScopeNamespaces[1]).prefix = "xml";
/* 102 */     (this._inScopeNamespaces[1]).namespaceName = "http://www.w3.org/XML/1998/namespace";
/* 103 */     (this._inScopeNamespaces[1]).namespaceIndex = this._currentInScope[1] = 1;
/*     */     
/* 105 */     index = KeyIntMap.indexFor(KeyIntMap.hashHash((this._inScopeNamespaces[1]).prefix.hashCode()), this._prefixMap.length);
/* 106 */     if (this._prefixMap[index] == null) {
/* 107 */       this._prefixMap[index] = this._prefixPool;
/* 108 */       this._prefixPool = this._prefixPool.next;
/* 109 */       (this._prefixMap[index]).next = null;
/*     */     } else {
/* 111 */       PrefixEntry e = this._prefixMap[index];
/* 112 */       this._prefixMap[index] = this._prefixPool;
/* 113 */       this._prefixPool = this._prefixPool.next;
/* 114 */       (this._prefixMap[index]).next = e;
/*     */     } 
/* 116 */     (this._prefixMap[index]).prefixId = 1;
/*     */   }
/*     */   
/*     */   private final void increaseNamespacePool(int capacity) {
/* 120 */     if (this._namespacePool == null) {
/* 121 */       this._namespacePool = new NamespaceEntry();
/*     */     }
/*     */     
/* 124 */     for (int i = 0; i < capacity; i++) {
/* 125 */       NamespaceEntry ne = new NamespaceEntry();
/* 126 */       ne.next = this._namespacePool;
/* 127 */       this._namespacePool = ne;
/*     */     } 
/*     */   }
/*     */   
/*     */   private final void increasePrefixPool(int capacity) {
/* 132 */     if (this._prefixPool == null) {
/* 133 */       this._prefixPool = new PrefixEntry();
/*     */     }
/*     */     
/* 136 */     for (int i = 0; i < capacity; i++) {
/* 137 */       PrefixEntry pe = new PrefixEntry();
/* 138 */       pe.next = this._prefixPool;
/* 139 */       this._prefixPool = pe;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int countNamespacePool() {
/* 144 */     int i = 0;
/* 145 */     NamespaceEntry e = this._namespacePool;
/* 146 */     while (e != null) {
/* 147 */       i++;
/* 148 */       e = e.next;
/*     */     } 
/* 150 */     return i;
/*     */   }
/*     */   
/*     */   public int countPrefixPool() {
/* 154 */     int i = 0;
/* 155 */     PrefixEntry e = this._prefixPool;
/* 156 */     while (e != null) {
/* 157 */       i++;
/* 158 */       e = e.next;
/*     */     } 
/* 160 */     return i;
/*     */   }
/*     */   
/*     */   public final void clear() {
/* 164 */     for (int i = this._readOnlyArraySize; i < this._size; i++) {
/* 165 */       this._array[i] = null;
/*     */     }
/* 167 */     this._size = this._readOnlyArraySize;
/*     */   }
/*     */   
/*     */   public final void clearCompletely() {
/* 171 */     this._prefixPool = null;
/* 172 */     this._namespacePool = null;
/*     */     int i;
/* 174 */     for (i = 0; i < this._size + 2; i++) {
/* 175 */       this._currentInScope[i] = 0;
/* 176 */       this._inScopeNamespaces[i] = null;
/*     */     } 
/*     */     
/* 179 */     for (i = 0; i < this._prefixMap.length; i++) {
/* 180 */       this._prefixMap[i] = null;
/*     */     }
/*     */     
/* 183 */     increaseNamespacePool(this._initialCapacity);
/* 184 */     increasePrefixPool(this._initialCapacity);
/*     */     
/* 186 */     initializeEntries();
/*     */     
/* 188 */     this._declarationId = 0;
/*     */     
/* 190 */     clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String[] getArray() {
/* 198 */     if (this._array == null) return null;
/*     */     
/* 200 */     String[] clonedArray = new String[this._array.length];
/* 201 */     System.arraycopy(this._array, 0, clonedArray, 0, this._array.length);
/* 202 */     return clonedArray;
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(ValueArray readOnlyArray, boolean clear) {
/* 206 */     if (!(readOnlyArray instanceof PrefixArray)) {
/* 207 */       throw new IllegalArgumentException(CommonResourceBundle.getInstance().getString("message.illegalClass", new Object[] { readOnlyArray }));
/*     */     }
/*     */ 
/*     */     
/* 211 */     setReadOnlyArray((PrefixArray)readOnlyArray, clear);
/*     */   }
/*     */   
/*     */   public final void setReadOnlyArray(PrefixArray readOnlyArray, boolean clear) {
/* 215 */     if (readOnlyArray != null) {
/* 216 */       this._readOnlyArray = readOnlyArray;
/* 217 */       this._readOnlyArraySize = readOnlyArray.getSize();
/*     */       
/* 219 */       clearCompletely();
/*     */ 
/*     */       
/* 222 */       this._inScopeNamespaces = new NamespaceEntry[this._readOnlyArraySize + this._inScopeNamespaces.length];
/* 223 */       this._currentInScope = new int[this._readOnlyArraySize + this._currentInScope.length];
/*     */       
/* 225 */       initializeEntries();
/*     */       
/* 227 */       if (clear) {
/* 228 */         clear();
/*     */       }
/*     */       
/* 231 */       this._array = getCompleteArray();
/* 232 */       this._size = this._readOnlyArraySize;
/*     */     } 
/*     */   }
/*     */   
/*     */   public final String[] getCompleteArray() {
/* 237 */     if (this._readOnlyArray == null)
/*     */     {
/* 239 */       return getArray();
/*     */     }
/*     */     
/* 242 */     String[] ra = this._readOnlyArray.getCompleteArray();
/* 243 */     String[] a = new String[this._readOnlyArraySize + this._array.length];
/* 244 */     System.arraycopy(ra, 0, a, 0, this._readOnlyArraySize);
/* 245 */     return a;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String get(int i) {
/* 250 */     return this._array[i];
/*     */   }
/*     */   
/*     */   public final int add(String s) {
/* 254 */     if (this._size == this._array.length) {
/* 255 */       resize();
/*     */     }
/*     */     
/* 258 */     this._array[this._size++] = s;
/* 259 */     return this._size;
/*     */   }
/*     */   
/*     */   protected final void resize() {
/* 263 */     if (this._size == this._maximumCapacity) {
/* 264 */       throw new ValueArrayResourceException(CommonResourceBundle.getInstance().getString("message.arrayMaxCapacity"));
/*     */     }
/*     */     
/* 267 */     int newSize = this._size * 3 / 2 + 1;
/* 268 */     if (newSize > this._maximumCapacity) {
/* 269 */       newSize = this._maximumCapacity;
/*     */     }
/*     */     
/* 272 */     String[] newArray = new String[newSize];
/* 273 */     System.arraycopy(this._array, 0, newArray, 0, this._size);
/* 274 */     this._array = newArray;
/*     */     
/* 276 */     newSize += 2;
/* 277 */     NamespaceEntry[] newInScopeNamespaces = new NamespaceEntry[newSize];
/* 278 */     System.arraycopy(this._inScopeNamespaces, 0, newInScopeNamespaces, 0, this._inScopeNamespaces.length);
/* 279 */     this._inScopeNamespaces = newInScopeNamespaces;
/*     */     
/* 281 */     int[] newCurrentInScope = new int[newSize];
/* 282 */     System.arraycopy(this._currentInScope, 0, newCurrentInScope, 0, this._currentInScope.length);
/* 283 */     this._currentInScope = newCurrentInScope;
/*     */   }
/*     */   
/*     */   public final void clearDeclarationIds() {
/* 287 */     for (int i = 0; i < this._size; i++) {
/* 288 */       NamespaceEntry e = this._inScopeNamespaces[i];
/* 289 */       if (e != null) {
/* 290 */         e.declarationId = 0;
/*     */       }
/*     */     } 
/*     */     
/* 294 */     this._declarationId = 1;
/*     */   }
/*     */   
/*     */   public final void pushScope(int prefixIndex, int namespaceIndex) throws FastInfosetException {
/* 298 */     if (this._namespacePool == null) {
/* 299 */       increaseNamespacePool(16);
/*     */     }
/*     */     
/* 302 */     NamespaceEntry e = this._namespacePool;
/* 303 */     this._namespacePool = e.next;
/*     */     
/* 305 */     NamespaceEntry current = this._inScopeNamespaces[++prefixIndex];
/* 306 */     if (current == null) {
/* 307 */       e.declarationId = this._declarationId;
/* 308 */       e.namespaceIndex = this._currentInScope[prefixIndex] = ++namespaceIndex;
/* 309 */       e.next = null;
/*     */       
/* 311 */       this._inScopeNamespaces[prefixIndex] = e;
/* 312 */     } else if (current.declarationId < this._declarationId) {
/* 313 */       e.declarationId = this._declarationId;
/* 314 */       e.namespaceIndex = this._currentInScope[prefixIndex] = ++namespaceIndex;
/* 315 */       e.next = current;
/*     */       
/* 317 */       current.declarationId = 0;
/* 318 */       this._inScopeNamespaces[prefixIndex] = e;
/*     */     } else {
/* 320 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.duplicateNamespaceAttribute"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void pushScopeWithPrefixEntry(String prefix, String namespaceName, int prefixIndex, int namespaceIndex) throws FastInfosetException {
/* 326 */     if (this._namespacePool == null) {
/* 327 */       increaseNamespacePool(16);
/*     */     }
/* 329 */     if (this._prefixPool == null) {
/* 330 */       increasePrefixPool(16);
/*     */     }
/*     */     
/* 333 */     NamespaceEntry e = this._namespacePool;
/* 334 */     this._namespacePool = e.next;
/*     */     
/* 336 */     NamespaceEntry current = this._inScopeNamespaces[++prefixIndex];
/* 337 */     if (current == null) {
/* 338 */       e.declarationId = this._declarationId;
/* 339 */       e.namespaceIndex = this._currentInScope[prefixIndex] = ++namespaceIndex;
/* 340 */       e.next = null;
/*     */       
/* 342 */       this._inScopeNamespaces[prefixIndex] = e;
/* 343 */     } else if (current.declarationId < this._declarationId) {
/* 344 */       e.declarationId = this._declarationId;
/* 345 */       e.namespaceIndex = this._currentInScope[prefixIndex] = ++namespaceIndex;
/* 346 */       e.next = current;
/*     */       
/* 348 */       current.declarationId = 0;
/* 349 */       this._inScopeNamespaces[prefixIndex] = e;
/*     */     } else {
/* 351 */       throw new FastInfosetException(CommonResourceBundle.getInstance().getString("message.duplicateNamespaceAttribute"));
/*     */     } 
/*     */     
/* 354 */     PrefixEntry p = this._prefixPool;
/* 355 */     this._prefixPool = this._prefixPool.next;
/* 356 */     p.prefixId = prefixIndex;
/*     */     
/* 358 */     e.prefix = prefix;
/* 359 */     e.namespaceName = namespaceName;
/* 360 */     e.prefixEntryIndex = KeyIntMap.indexFor(KeyIntMap.hashHash(prefix.hashCode()), this._prefixMap.length);
/*     */     
/* 362 */     PrefixEntry pCurrent = this._prefixMap[e.prefixEntryIndex];
/* 363 */     p.next = pCurrent;
/* 364 */     this._prefixMap[e.prefixEntryIndex] = p;
/*     */   }
/*     */   
/*     */   public final void popScope(int prefixIndex) {
/* 368 */     NamespaceEntry e = this._inScopeNamespaces[++prefixIndex];
/* 369 */     this._inScopeNamespaces[prefixIndex] = e.next;
/* 370 */     this._currentInScope[prefixIndex] = (e.next != null) ? e.next.namespaceIndex : 0;
/*     */     
/* 372 */     e.next = this._namespacePool;
/* 373 */     this._namespacePool = e;
/*     */   }
/*     */   
/*     */   public final void popScopeWithPrefixEntry(int prefixIndex) {
/* 377 */     NamespaceEntry e = this._inScopeNamespaces[++prefixIndex];
/*     */     
/* 379 */     this._inScopeNamespaces[prefixIndex] = e.next;
/* 380 */     this._currentInScope[prefixIndex] = (e.next != null) ? e.next.namespaceIndex : 0;
/*     */     
/* 382 */     e.prefix = e.namespaceName = null;
/* 383 */     e.next = this._namespacePool;
/* 384 */     this._namespacePool = e;
/*     */     
/* 386 */     PrefixEntry current = this._prefixMap[e.prefixEntryIndex];
/* 387 */     if (current.prefixId == prefixIndex) {
/* 388 */       this._prefixMap[e.prefixEntryIndex] = current.next;
/* 389 */       current.next = this._prefixPool;
/* 390 */       this._prefixPool = current;
/*     */     } else {
/* 392 */       PrefixEntry prev = current;
/* 393 */       current = current.next;
/* 394 */       while (current != null) {
/* 395 */         if (current.prefixId == prefixIndex) {
/* 396 */           prev.next = current.next;
/* 397 */           current.next = this._prefixPool;
/* 398 */           this._prefixPool = current;
/*     */           break;
/*     */         } 
/* 401 */         prev = current;
/* 402 */         current = current.next;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public final String getNamespaceFromPrefix(String prefix) {
/* 408 */     int index = KeyIntMap.indexFor(KeyIntMap.hashHash(prefix.hashCode()), this._prefixMap.length);
/* 409 */     PrefixEntry pe = this._prefixMap[index];
/* 410 */     while (pe != null) {
/* 411 */       NamespaceEntry ne = this._inScopeNamespaces[pe.prefixId];
/* 412 */       if (prefix == ne.prefix || prefix.equals(ne.prefix)) {
/* 413 */         return ne.namespaceName;
/*     */       }
/* 415 */       pe = pe.next;
/*     */     } 
/*     */     
/* 418 */     return null;
/*     */   }
/*     */   
/*     */   public final String getPrefixFromNamespace(String namespaceName) {
/* 422 */     int position = 0;
/* 423 */     while (++position < this._size + 2) {
/* 424 */       NamespaceEntry ne = this._inScopeNamespaces[position];
/* 425 */       if (ne != null && namespaceName.equals(ne.namespaceName)) {
/* 426 */         return ne.prefix;
/*     */       }
/*     */     } 
/*     */     
/* 430 */     return null;
/*     */   }
/*     */   
/*     */   public final Iterator getPrefixes() {
/* 434 */     return new Iterator() {
/* 435 */         int _position = 1;
/* 436 */         PrefixArray.NamespaceEntry _ne = PrefixArray.this._inScopeNamespaces[this._position];
/*     */         
/*     */         public boolean hasNext() {
/* 439 */           return (this._ne != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 443 */           if (this._position == PrefixArray.this._size + 2) {
/* 444 */             throw new NoSuchElementException();
/*     */           }
/*     */           
/* 447 */           String prefix = this._ne.prefix;
/* 448 */           moveToNext();
/* 449 */           return prefix;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 453 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */         private final void moveToNext() {
/* 457 */           while (++this._position < PrefixArray.this._size + 2) {
/* 458 */             this._ne = PrefixArray.this._inScopeNamespaces[this._position];
/* 459 */             if (this._ne != null) {
/*     */               return;
/*     */             }
/*     */           } 
/* 463 */           this._ne = null;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public final Iterator getPrefixesFromNamespace(final String namespaceName) {
/* 470 */     return new Iterator()
/*     */       {
/*     */         String _namespaceName;
/*     */         
/*     */         int _position;
/*     */         
/*     */         PrefixArray.NamespaceEntry _ne;
/*     */ 
/*     */         
/*     */         public boolean hasNext() {
/* 480 */           return (this._ne != null);
/*     */         }
/*     */         
/*     */         public Object next() {
/* 484 */           if (this._position == PrefixArray.this._size + 2) {
/* 485 */             throw new NoSuchElementException();
/*     */           }
/*     */           
/* 488 */           String prefix = this._ne.prefix;
/* 489 */           moveToNext();
/* 490 */           return prefix;
/*     */         }
/*     */         
/*     */         public void remove() {
/* 494 */           throw new UnsupportedOperationException();
/*     */         }
/*     */         
/*     */         private final void moveToNext() {
/* 498 */           while (++this._position < PrefixArray.this._size + 2) {
/* 499 */             this._ne = PrefixArray.this._inScopeNamespaces[this._position];
/* 500 */             if (this._ne != null && this._namespaceName.equals(this._ne.namespaceName)) {
/*     */               return;
/*     */             }
/*     */           } 
/* 504 */           this._ne = null;
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfose\\util\PrefixArray.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */