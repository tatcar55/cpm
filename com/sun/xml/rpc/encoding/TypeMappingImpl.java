/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.encoding.DeserializerFactory;
/*     */ import javax.xml.rpc.encoding.SerializerFactory;
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
/*     */ public class TypeMappingImpl
/*     */   implements ExtendedTypeMapping
/*     */ {
/*  45 */   protected static final String[] EMPTY_STRING_ARRAY = new String[0];
/*     */ 
/*     */   
/*     */   protected static final boolean UNIQUE_IS_REQUIRED = true;
/*     */   
/*     */   protected static final boolean UNIQUE_IS_OPTIONAL = false;
/*     */   
/*  52 */   protected static final Row NULL_ROW = new Row();
/*  53 */   protected static final Entry NULL_ENTRY = new Entry(null, 0, NULL_ROW); static {
/*  54 */     NULL_ENTRY.next = NULL_ENTRY;
/*     */   }
/*     */ 
/*     */   
/*     */   private Entry[] table;
/*     */   private int count;
/*     */   private int threshold;
/*     */   private float loadFactor;
/*  62 */   protected ExtendedTypeMapping parent = null;
/*  63 */   protected String[] encodingURIs = EMPTY_STRING_ARRAY;
/*  64 */   protected List tuples = new ArrayList();
/*     */ 
/*     */   
/*     */   public static class Row
/*     */     implements TypeMappingDescriptor
/*     */   {
/*     */     Class javaType;
/*     */     
/*     */     QName xmlType;
/*     */     SerializerFactory serializerFactory;
/*     */     DeserializerFactory deserializerFactory;
/*     */     
/*     */     Row(Class javaType, QName xmlType, SerializerFactory sf, DeserializerFactory dsf) {
/*  77 */       if (javaType == null) {
/*  78 */         throw new IllegalArgumentException("javaType may not be null");
/*     */       }
/*  80 */       if (xmlType == null) {
/*  81 */         throw new IllegalArgumentException("xmlType may not be null");
/*     */       }
/*  83 */       if (sf == null) {
/*  84 */         throw new IllegalArgumentException("serializerFactory may not be null");
/*     */       }
/*  86 */       if (dsf == null) {
/*  87 */         throw new IllegalArgumentException("deserializerFactory may not be null");
/*     */       }
/*     */       
/*  90 */       this.javaType = javaType;
/*  91 */       this.xmlType = xmlType;
/*  92 */       this.serializerFactory = sf;
/*  93 */       this.deserializerFactory = dsf;
/*     */     }
/*     */     Row() {
/*  96 */       this.javaType = null;
/*  97 */       this.xmlType = null;
/*  98 */       this.serializerFactory = null;
/*  99 */       this.deserializerFactory = null;
/*     */     }
/*     */     
/*     */     public Class getJavaType() {
/* 103 */       return this.javaType;
/*     */     }
/*     */     public QName getXMLType() {
/* 106 */       return this.xmlType;
/*     */     }
/*     */     public SerializerFactory getSerializer() {
/* 109 */       return this.serializerFactory;
/*     */     }
/*     */     public DeserializerFactory getDeserializer() {
/* 112 */       return this.deserializerFactory;
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class Entry {
/*     */     Entry next;
/*     */     int hash;
/*     */     TypeMappingImpl.Row row;
/*     */     
/*     */     Entry(Entry next, int hash, TypeMappingImpl.Row row) {
/* 122 */       if (row == null) {
/* 123 */         throw new IllegalArgumentException("row may not be null");
/*     */       }
/*     */       
/* 126 */       this.next = next;
/* 127 */       this.hash = hash;
/* 128 */       this.row = row;
/*     */     }
/*     */     
/*     */     Entry getEntryMatching(Class javaType) {
/* 132 */       Entry candidate = this;
/*     */       
/* 134 */       while (candidate != TypeMappingImpl.NULL_ENTRY && !candidate.row.javaType.equals(javaType)) {
/* 135 */         candidate = candidate.next;
/*     */       }
/*     */       
/* 138 */       return candidate;
/*     */     }
/*     */     Entry getEntryMatchingSuperclassOf(Class javaType) {
/* 141 */       Entry bestCandidate = TypeMappingImpl.NULL_ENTRY;
/* 142 */       Entry currentCandidate = this;
/*     */       
/* 144 */       while (currentCandidate != TypeMappingImpl.NULL_ENTRY) {
/* 145 */         if (currentCandidate.matchesSuperclassOf(javaType) && (
/* 146 */           bestCandidate == TypeMappingImpl.NULL_ENTRY || currentCandidate.matchesSubclassOf(bestCandidate.row.javaType)))
/*     */         {
/*     */           
/* 149 */           bestCandidate = currentCandidate;
/*     */         }
/*     */         
/* 152 */         currentCandidate = currentCandidate.next;
/*     */       } 
/*     */       
/* 155 */       return bestCandidate;
/*     */     }
/*     */     
/*     */     boolean matchesSubclassOf(Class<Object> javaType) {
/* 159 */       Class<?> currentJavaType = this.row.javaType;
/* 160 */       return (javaType.equals(currentJavaType) || (javaType.isAssignableFrom(currentJavaType) && javaType != Object.class));
/*     */     }
/*     */ 
/*     */     
/*     */     boolean matchesSuperclassOf(Class<?> javaType) {
/* 165 */       Class<Object> currentJavaType = this.row.javaType;
/* 166 */       return (currentJavaType.equals(javaType) || (currentJavaType.isAssignableFrom(javaType) && currentJavaType != Object.class));
/*     */     }
/*     */ 
/*     */     
/*     */     Entry getEntryMatching(QName xmlType) {
/* 171 */       Entry candidate = this;
/*     */       
/* 173 */       while (candidate != TypeMappingImpl.NULL_ENTRY && !candidate.row.xmlType.equals(xmlType)) {
/* 174 */         candidate = candidate.next;
/*     */       }
/* 176 */       return candidate;
/*     */     }
/*     */     Entry getNonPrimitiveEntryMatching(QName xmlType) {
/* 179 */       Entry candidate = this;
/*     */       
/* 181 */       while (candidate != TypeMappingImpl.NULL_ENTRY && (!candidate.row.xmlType.equals(xmlType) || candidate.row.javaType.isPrimitive()))
/*     */       {
/* 183 */         candidate = candidate.next;
/*     */       }
/* 185 */       return candidate;
/*     */     }
/*     */     Entry getEntryMatching(Class javaType, QName xmlType) {
/* 188 */       Entry candidate = this;
/*     */ 
/*     */       
/* 191 */       while (candidate != TypeMappingImpl.NULL_ENTRY && (!candidate.row.javaType.equals(javaType) || !candidate.row.xmlType.equals(xmlType)))
/*     */       {
/* 193 */         candidate = candidate.next;
/*     */       }
/*     */       
/* 196 */       return candidate;
/*     */     }
/*     */     Entry getEntryMatchingSuperclassOf(Class javaType, QName xmlType) {
/* 199 */       Entry bestCandidate = TypeMappingImpl.NULL_ENTRY;
/* 200 */       Entry currentCandidate = this;
/*     */       
/* 202 */       while (currentCandidate != TypeMappingImpl.NULL_ENTRY) {
/* 203 */         if (currentCandidate.matchesSuperclassOf(javaType) && currentCandidate.row.xmlType.equals(xmlType))
/*     */         {
/* 205 */           if (bestCandidate == TypeMappingImpl.NULL_ENTRY || currentCandidate.matchesSubclassOf(bestCandidate.row.javaType))
/*     */           {
/*     */             
/* 208 */             bestCandidate = currentCandidate;
/*     */           }
/*     */         }
/* 211 */         currentCandidate = currentCandidate.next;
/*     */       } 
/*     */       
/* 214 */       return bestCandidate;
/*     */     }
/*     */   }
/*     */   
/*     */   private int hashToIndex(int hash) {
/* 219 */     return (hash & Integer.MAX_VALUE) % this.table.length;
/*     */   }
/*     */   
/*     */   private Entry getHashBucket(int hash) {
/* 223 */     return this.table[hashToIndex(hash)];
/*     */   }
/*     */   
/*     */   private void put(int hash, Row row) {
/* 227 */     if (this.count >= this.threshold) {
/* 228 */       rehash();
/*     */     }
/* 230 */     int index = hashToIndex(hash);
/* 231 */     this.table[index] = new Entry(this.table[index], hash, row);
/* 232 */     this.count++;
/*     */   }
/*     */   
/*     */   private void rehash() {
/* 236 */     int oldCapacity = this.table.length;
/* 237 */     Entry[] oldMap = this.table;
/*     */     
/* 239 */     int newCapacity = oldCapacity * 2 + 1;
/* 240 */     Entry[] newMap = new Entry[newCapacity];
/* 241 */     Arrays.fill((Object[])newMap, NULL_ENTRY);
/*     */     
/* 243 */     this.threshold = (int)(newCapacity * this.loadFactor);
/* 244 */     this.table = newMap;
/*     */     
/* 246 */     for (int i = oldCapacity; i-- > 0;) {
/* 247 */       for (Entry old = oldMap[i]; old != NULL_ENTRY; ) {
/* 248 */         Entry e = old;
/* 249 */         old = old.next;
/*     */         
/* 251 */         int index = hashToIndex(e.hash);
/* 252 */         e.next = this.table[index];
/* 253 */         this.table[index] = e;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public TypeMappingImpl() {
/* 259 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/* 263 */     this.loadFactor = 0.75F;
/*     */     
/* 265 */     this.parent = null;
/* 266 */     this.encodingURIs = EMPTY_STRING_ARRAY;
/* 267 */     this.tuples = new ArrayList();
/* 268 */     int initialCapacity = 57;
/* 269 */     this.table = new Entry[initialCapacity];
/* 270 */     Arrays.fill((Object[])this.table, NULL_ENTRY);
/* 271 */     this.count = 0;
/* 272 */     this.threshold = (int)(initialCapacity * this.loadFactor);
/*     */   }
/*     */   
/*     */   public TypeMappingImpl(ExtendedTypeMapping parent) {
/* 276 */     this();
/* 277 */     this.parent = parent;
/*     */   }
/*     */   
/*     */   public String[] getSupportedEncodings() {
/* 281 */     return this.encodingURIs;
/*     */   }
/*     */   
/*     */   public void setSupportedEncodings(String[] encodingURIs) {
/* 285 */     if (encodingURIs != null) {
/* 286 */       this.encodingURIs = encodingURIs;
/*     */     } else {
/* 288 */       this.encodingURIs = EMPTY_STRING_ARRAY;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isRegistered(Class javaType, QName xmlType) {
/* 293 */     if (xmlType == null) {
/* 294 */       throw new IllegalArgumentException("XML type may not be null");
/*     */     }
/* 296 */     if (javaType == null) {
/* 297 */       throw new IllegalArgumentException("Java type may not be null");
/*     */     }
/* 299 */     int jTypeHash = javaType.hashCode();
/* 300 */     int xTypeHash = xmlType.hashCode();
/* 301 */     int combinedHash = jTypeHash ^ xTypeHash;
/*     */     
/* 303 */     Entry existingEntry = getHashBucket(combinedHash).getEntryMatching(javaType, xmlType);
/*     */ 
/*     */     
/* 306 */     boolean isRegistered = (existingEntry != NULL_ENTRY);
/* 307 */     if (!isRegistered && this.parent != null) {
/* 308 */       isRegistered = this.parent.isRegistered(javaType, xmlType);
/*     */     }
/*     */     
/* 311 */     return isRegistered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(Class javaType, QName xmlType, SerializerFactory sf, DeserializerFactory dsf) {
/* 320 */     if (xmlType == null) {
/* 321 */       throw new IllegalArgumentException("XML type may not be null");
/*     */     }
/* 323 */     if (javaType == null) {
/* 324 */       throw new IllegalArgumentException("Java type may not be null");
/*     */     }
/*     */     try {
/* 327 */       int jTypeHash = javaType.hashCode();
/* 328 */       int xTypeHash = xmlType.hashCode();
/* 329 */       int combinedHash = jTypeHash ^ xTypeHash;
/*     */       
/* 331 */       Row existingRow = (getHashBucket(combinedHash).getEntryMatching(javaType, xmlType)).row;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 336 */       if (existingRow != NULL_ROW) {
/*     */         
/* 338 */         existingRow.serializerFactory = sf;
/* 339 */         existingRow.deserializerFactory = dsf;
/*     */       } else {
/* 341 */         Row newRow = new Row(javaType, xmlType, sf, dsf);
/*     */         
/* 343 */         put(jTypeHash, newRow);
/* 344 */         put(xTypeHash, newRow);
/* 345 */         put(combinedHash, newRow);
/* 346 */         this.tuples.add(newRow);
/*     */       } 
/* 348 */     } catch (Exception e) {
/* 349 */       throw new TypeMappingException("typemapping.registration.failed.nested.exception", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Entry getEntryMatching(Class javaType) {
/* 356 */     return getHashBucket(javaType.hashCode()).getEntryMatching(javaType);
/*     */   }
/*     */   
/*     */   protected Entry getEntryMatching(QName xmlType) {
/* 360 */     return getHashBucket(xmlType.hashCode()).getEntryMatching(xmlType);
/*     */   }
/*     */   
/*     */   protected Entry getNonPrimitiveEntryMatching(QName xmlType) {
/* 364 */     return getHashBucket(xmlType.hashCode()).getNonPrimitiveEntryMatching(xmlType);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Entry getEntryMatching(Class javaType, QName xmlType) {
/* 369 */     return getHashBucket(javaType.hashCode() ^ xmlType.hashCode()).getEntryMatching(javaType, xmlType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Entry getEntryClosestTo(Class javaType, QName xmlType) {
/* 376 */     Entry entry = getEntryMatching(javaType, xmlType);
/* 377 */     if (entry == NULL_ENTRY) {
/* 378 */       entry = getEntryMatching(xmlType).getEntryMatchingSuperclassOf(javaType, xmlType);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 383 */     return entry;
/*     */   }
/*     */   
/*     */   protected Entry getEntryCloesestTo(Class javaType) {
/* 387 */     Entry matchingEntry = getEntryMatching(javaType);
/* 388 */     if (matchingEntry != NULL_ENTRY) {
/* 389 */       return matchingEntry;
/*     */     }
/*     */     
/* 392 */     List<Class<?>> superTypes = new ArrayList();
/* 393 */     Class<?> superClass = javaType.getSuperclass();
/* 394 */     if (superClass != null && !superClass.equals(Object.class)) {
/* 395 */       superTypes.add(superClass);
/*     */     }
/* 397 */     superTypes.addAll(Arrays.asList(javaType.getInterfaces()));
/*     */     
/* 399 */     for (int i = 0; i < superTypes.size(); i++) {
/* 400 */       Class<?> currentType = superTypes.get(i);
/* 401 */       if (currentType != null) {
/*     */ 
/*     */         
/* 404 */         matchingEntry = getEntryMatching(currentType);
/* 405 */         if (matchingEntry != NULL_ENTRY) {
/*     */           break;
/*     */         }
/*     */         
/* 409 */         superClass = currentType.getSuperclass();
/* 410 */         if (superClass != null && !superClass.equals(Object.class))
/* 411 */           superTypes.add(superClass); 
/*     */       } 
/*     */     } 
/* 414 */     return matchingEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SerializerFactory getSerializer(Class javaType, boolean uniqueRequired) {
/*     */     try {
/* 422 */       Entry matchingRowEntry = getEntryCloesestTo(javaType);
/* 423 */       SerializerFactory factory = matchingRowEntry.row.serializerFactory;
/*     */       
/* 425 */       return factory;
/* 426 */     } catch (Exception e) {
/* 427 */       throw new TypeMappingException("typemapping.retrieval.failed.nested.exception", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SerializerFactory getSerializer(QName xmlType, boolean uniqueRequired) {
/*     */     try {
/* 438 */       Entry matchingRowEntry = getNonPrimitiveEntryMatching(xmlType);
/* 439 */       SerializerFactory factory = matchingRowEntry.row.serializerFactory;
/*     */       
/* 441 */       if (uniqueRequired && matchingRowEntry.next.getNonPrimitiveEntryMatching(xmlType) != NULL_ENTRY)
/*     */       {
/*     */         
/* 444 */         return null;
/*     */       }
/*     */       
/* 447 */       return factory;
/* 448 */     } catch (Exception e) {
/* 449 */       throw new TypeMappingException("typemapping.retrieval.failed.nested.exception", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DeserializerFactory getDeserializer(Class javaType, boolean uniqueRequired) {
/*     */     try {
/* 460 */       Entry matchingRowEntry = getEntryMatching(javaType);
/* 461 */       DeserializerFactory factory = matchingRowEntry.row.deserializerFactory;
/*     */ 
/*     */       
/* 464 */       if (uniqueRequired && matchingRowEntry.next.getEntryMatching(javaType) != NULL_ENTRY)
/*     */       {
/*     */         
/* 467 */         return null;
/*     */       }
/*     */       
/* 470 */       return factory;
/* 471 */     } catch (Exception e) {
/* 472 */       throw new TypeMappingException("typemapping.retrieval.failed.nested.exception", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected DeserializerFactory getDeserializer(QName xmlType, boolean uniqueRequired) {
/*     */     try {
/* 483 */       Entry matchingRowEntry = getNonPrimitiveEntryMatching(xmlType);
/* 484 */       DeserializerFactory factory = matchingRowEntry.row.deserializerFactory;
/*     */ 
/*     */       
/* 487 */       if (uniqueRequired && matchingRowEntry.next.getNonPrimitiveEntryMatching(xmlType) != NULL_ENTRY)
/*     */       {
/*     */         
/* 490 */         return null;
/*     */       }
/*     */       
/* 493 */       return factory;
/* 494 */     } catch (Exception e) {
/* 495 */       throw new TypeMappingException("typemapping.retrieval.failed.nested.exception", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Class getJavaType(QName xmlType, boolean uniqueRequired) {
/*     */     try {
/* 502 */       Entry matchingRowEntry = getNonPrimitiveEntryMatching(xmlType);
/* 503 */       Class javaType = matchingRowEntry.row.javaType;
/*     */       
/* 505 */       if (uniqueRequired && matchingRowEntry.next.getNonPrimitiveEntryMatching(xmlType) != NULL_ENTRY)
/*     */       {
/*     */         
/* 508 */         return null;
/*     */       }
/*     */       
/* 511 */       return javaType;
/* 512 */     } catch (Exception e) {
/* 513 */       throw new TypeMappingException("typemapping.retrieval.failed.nested.exception", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected QName getXmlType(Class javaType, boolean uniqueRequired) {
/*     */     try {
/* 521 */       Entry matchingRowEntry = getEntryMatching(javaType);
/* 522 */       QName xmlType = matchingRowEntry.row.xmlType;
/*     */       
/* 524 */       if (uniqueRequired && matchingRowEntry.next.getEntryMatching(javaType) != NULL_ENTRY)
/*     */       {
/*     */         
/* 527 */         return null;
/*     */       }
/*     */       
/* 530 */       return xmlType;
/* 531 */     } catch (Exception e) {
/* 532 */       throw new TypeMappingException("typemapping.retrieval.failed.nested.exception", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SerializerFactory getSerializer(Class javaType, QName xmlType) {
/*     */     SerializerFactory factory;
/* 541 */     if (javaType == null) {
/* 542 */       if (xmlType == null) {
/* 543 */         throw new IllegalArgumentException("getSerializer requires a Java type and/or an XML type");
/*     */       }
/* 545 */       factory = getSerializer(xmlType, false);
/*     */     }
/* 547 */     else if (xmlType == null) {
/* 548 */       factory = getSerializer(javaType, false);
/*     */     } else {
/*     */       
/*     */       try {
/* 552 */         factory = (getEntryClosestTo(javaType, xmlType)).row.serializerFactory;
/*     */       }
/* 554 */       catch (Exception e) {
/* 555 */         throw new TypeMappingException("typemapping.retrieval.failed.nested.exception", new LocalizableExceptionAdapter(e));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 561 */     if (factory == null && this.parent != null) {
/* 562 */       factory = this.parent.getSerializer(javaType, xmlType);
/*     */     }
/*     */     
/* 565 */     return factory;
/*     */   }
/*     */   
/*     */   public DeserializerFactory getDeserializer(Class javaType, QName xmlType) {
/* 569 */     DeserializerFactory factory = null;
/* 570 */     if (javaType == null) {
/* 571 */       if (xmlType == null) {
/* 572 */         throw new IllegalArgumentException("getDeserializer requires a Java type and/or an XML type");
/*     */       }
/* 574 */       factory = getDeserializer(xmlType, false);
/* 575 */     } else if (xmlType == null) {
/* 576 */       factory = getDeserializer(javaType, false);
/*     */     } else {
/*     */       
/*     */       try {
/* 580 */         factory = (getEntryClosestTo(javaType, xmlType)).row.deserializerFactory;
/*     */ 
/*     */       
/*     */       }
/* 584 */       catch (Exception e) {
/* 585 */         throw new TypeMappingException("typemapping.retrieval.failed.nested.exception", new LocalizableExceptionAdapter(e));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 591 */     if (factory == null && this.parent != null) {
/* 592 */       factory = this.parent.getDeserializer(javaType, xmlType);
/*     */     }
/*     */     
/* 595 */     return factory;
/*     */   }
/*     */   
/*     */   public void removeSerializer(Class javaType, QName xmlType) {
/* 599 */     if (javaType == null || xmlType == null) {
/* 600 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*     */     try {
/* 604 */       (getEntryMatching(javaType, xmlType)).row.serializerFactory = null;
/* 605 */     } catch (Exception e) {
/* 606 */       throw new TypeMappingException("typemapping.retrieval.failed.nested.exception", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeDeserializer(Class javaType, QName xmlType) {
/* 613 */     if (javaType == null || xmlType == null) {
/* 614 */       throw new IllegalArgumentException();
/*     */     }
/*     */     
/*     */     try {
/* 618 */       (getEntryMatching(javaType, xmlType)).row.deserializerFactory = null;
/* 619 */     } catch (Exception e) {
/* 620 */       throw new TypeMappingException("typemapping.retrieval.failed.nested.exception", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class getJavaType(QName xmlType) {
/* 627 */     if (xmlType == null) {
/* 628 */       throw new IllegalArgumentException("non null xmlType required");
/*     */     }
/*     */     
/* 631 */     Class javaType = getJavaType(xmlType, false);
/*     */     
/* 633 */     if (javaType == null && this.parent != null) {
/* 634 */       javaType = this.parent.getJavaType(xmlType);
/*     */     }
/*     */     
/* 637 */     return javaType;
/*     */   }
/*     */   
/*     */   public QName getXmlType(Class javaType) {
/* 641 */     if (javaType == null) {
/* 642 */       throw new IllegalArgumentException("non null xmjavaType required");
/*     */     }
/*     */     
/* 645 */     QName xmlType = getXmlType(javaType, false);
/*     */     
/* 647 */     if (xmlType == null && this.parent != null) {
/* 648 */       xmlType = this.parent.getXmlType(javaType);
/*     */     }
/*     */     
/* 651 */     return xmlType;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\TypeMappingImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */