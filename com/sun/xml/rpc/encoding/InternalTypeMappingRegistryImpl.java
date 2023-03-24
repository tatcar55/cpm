/*     */ package com.sun.xml.rpc.encoding;
/*     */ 
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import java.util.Arrays;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.encoding.Deserializer;
/*     */ import javax.xml.rpc.encoding.Serializer;
/*     */ import javax.xml.rpc.encoding.TypeMapping;
/*     */ import javax.xml.rpc.encoding.TypeMappingRegistry;
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
/*     */ public class InternalTypeMappingRegistryImpl
/*     */   implements InternalTypeMappingRegistry, SerializerConstants
/*     */ {
/*  52 */   protected static final Row NULL_ROW = Row.createNull();
/*  53 */   protected static final Entry NULL_ENTRY = Entry.createNull(NULL_ROW);
/*     */   
/*     */   private Entry[] table;
/*     */   
/*     */   private int count;
/*     */   
/*     */   private int threshold;
/*     */   private float loadFactor;
/*  61 */   protected TypeMappingRegistry registry = null;
/*     */   
/*     */   protected static class Row {
/*     */     String encoding;
/*     */     Class javaType;
/*     */     QName xmlType;
/*     */     Serializer serializer;
/*     */     Deserializer deserializer;
/*     */     
/*     */     Row(String encoding, Class javaType, QName xmlType) {
/*  71 */       this(encoding, javaType, xmlType, null, null);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Row(String encoding, Class javaType, QName xmlType, Serializer serializer, Deserializer deserializer) {
/*  79 */       if (encoding == null) {
/*  80 */         throw new IllegalArgumentException("encoding may not be null");
/*     */       }
/*  82 */       if (javaType == null && xmlType == null) {
/*  83 */         throw new IllegalArgumentException("javaType and xmlType may not both be null");
/*     */       }
/*     */       
/*  86 */       this.encoding = encoding;
/*  87 */       this.javaType = javaType;
/*  88 */       this.xmlType = xmlType;
/*  89 */       this.serializer = serializer;
/*  90 */       this.deserializer = deserializer;
/*     */     }
/*     */     
/*     */     static Row createNull() {
/*  94 */       return new Row();
/*     */     }
/*     */     
/*     */     private Row() {
/*  98 */       this.encoding = null;
/*  99 */       this.javaType = null;
/* 100 */       this.xmlType = null;
/* 101 */       this.serializer = null;
/* 102 */       this.deserializer = null;
/*     */     }
/*     */     
/*     */     public String getEncoding() {
/* 106 */       return this.encoding;
/*     */     }
/*     */     
/*     */     public Class getJavaType() {
/* 110 */       return this.javaType;
/*     */     }
/*     */     
/*     */     public QName getXMLType() {
/* 114 */       return this.xmlType;
/*     */     }
/*     */     
/*     */     public Serializer getSerializer() {
/* 118 */       return this.serializer;
/*     */     }
/*     */     
/*     */     public Deserializer getDeserializer() {
/* 122 */       return this.deserializer;
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class Entry {
/*     */     Entry next;
/*     */     int hash;
/*     */     InternalTypeMappingRegistryImpl.Row row;
/*     */     
/*     */     static Entry createNull(InternalTypeMappingRegistryImpl.Row nullRow) {
/* 132 */       Entry nullEntry = new Entry(0, nullRow);
/* 133 */       nullEntry.next = nullEntry;
/* 134 */       return nullEntry;
/*     */     }
/*     */     
/*     */     private Entry(int hash, InternalTypeMappingRegistryImpl.Row row) {
/* 138 */       if (row == null) {
/* 139 */         throw new IllegalArgumentException("row may not be null");
/*     */       }
/*     */       
/* 142 */       this.next = null;
/* 143 */       this.hash = hash;
/* 144 */       this.row = row;
/*     */     }
/*     */     Entry(Entry next, int hash, InternalTypeMappingRegistryImpl.Row row) {
/* 147 */       this(hash, row);
/* 148 */       if (next == null) {
/* 149 */         throw new IllegalArgumentException("next may not be null");
/*     */       }
/* 151 */       this.next = next;
/*     */     }
/*     */     
/*     */     boolean matches(String encoding, Class javaType) {
/* 155 */       return (this.row.encoding.equals(encoding) && this.row.javaType != null) ? this.row.javaType.equals(javaType) : false;
/*     */     }
/*     */ 
/*     */     
/*     */     boolean matches(String encoding, QName xmlType) {
/* 160 */       return (this.row.encoding.equals(encoding) && this.row.xmlType != null) ? this.row.xmlType.equals(xmlType) : false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean matches(String encoding, Class javaType, QName xmlType) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: getfield row : Lcom/sun/xml/rpc/encoding/InternalTypeMappingRegistryImpl$Row;
/*     */       //   4: getfield xmlType : Ljavax/xml/namespace/QName;
/*     */       //   7: ifnull -> 27
/*     */       //   10: aload_0
/*     */       //   11: getfield row : Lcom/sun/xml/rpc/encoding/InternalTypeMappingRegistryImpl$Row;
/*     */       //   14: getfield xmlType : Ljavax/xml/namespace/QName;
/*     */       //   17: aload_3
/*     */       //   18: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */       //   21: ifeq -> 80
/*     */       //   24: goto -> 31
/*     */       //   27: aload_3
/*     */       //   28: ifnonnull -> 80
/*     */       //   31: aload_0
/*     */       //   32: getfield row : Lcom/sun/xml/rpc/encoding/InternalTypeMappingRegistryImpl$Row;
/*     */       //   35: getfield javaType : Ljava/lang/Class;
/*     */       //   38: ifnull -> 58
/*     */       //   41: aload_0
/*     */       //   42: getfield row : Lcom/sun/xml/rpc/encoding/InternalTypeMappingRegistryImpl$Row;
/*     */       //   45: getfield javaType : Ljava/lang/Class;
/*     */       //   48: aload_2
/*     */       //   49: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */       //   52: ifeq -> 80
/*     */       //   55: goto -> 62
/*     */       //   58: aload_2
/*     */       //   59: ifnonnull -> 80
/*     */       //   62: aload_0
/*     */       //   63: getfield row : Lcom/sun/xml/rpc/encoding/InternalTypeMappingRegistryImpl$Row;
/*     */       //   66: getfield encoding : Ljava/lang/String;
/*     */       //   69: aload_1
/*     */       //   70: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */       //   73: ifeq -> 80
/*     */       //   76: iconst_1
/*     */       //   77: goto -> 81
/*     */       //   80: iconst_0
/*     */       //   81: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #165	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	82	0	this	Lcom/sun/xml/rpc/encoding/InternalTypeMappingRegistryImpl$Entry;
/*     */       //   0	82	1	encoding	Ljava/lang/String;
/*     */       //   0	82	2	javaType	Ljava/lang/Class;
/*     */       //   0	82	3	xmlType	Ljavax/xml/namespace/QName;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Entry getEntryMatching(String encoding, Class javaType) {
/* 175 */       Entry candidate = this;
/*     */       
/* 177 */       while (candidate != InternalTypeMappingRegistryImpl.NULL_ENTRY && !candidate.matches(encoding, javaType)) {
/* 178 */         candidate = candidate.next;
/*     */       }
/*     */       
/* 181 */       return candidate;
/*     */     }
/*     */     Entry getEntryMatching(String encoding, QName xmlType) {
/* 184 */       Entry candidate = this;
/*     */       
/* 186 */       while (candidate != InternalTypeMappingRegistryImpl.NULL_ENTRY && !candidate.matches(encoding, xmlType)) {
/* 187 */         candidate = candidate.next;
/*     */       }
/* 189 */       return candidate;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Entry getEntryMatching(String encoding, Class javaType, QName xmlType) {
/* 195 */       Entry candidate = this;
/*     */ 
/*     */       
/* 198 */       while (candidate != InternalTypeMappingRegistryImpl.NULL_ENTRY && !candidate.matches(encoding, javaType, xmlType)) {
/* 199 */         candidate = candidate.next;
/*     */       }
/*     */       
/* 202 */       return candidate;
/*     */     }
/*     */   }
/*     */   
/*     */   private int hashToIndex(int hash) {
/* 207 */     return (hash & Integer.MAX_VALUE) % this.table.length;
/*     */   }
/*     */   
/*     */   private Entry get(int hash) {
/* 211 */     return this.table[hashToIndex(hash)];
/*     */   }
/*     */   
/*     */   private Entry put(int hash, Row row) {
/* 215 */     if (this.count >= this.threshold) {
/* 216 */       rehash();
/*     */     }
/* 218 */     int index = hashToIndex(hash);
/* 219 */     this.table[index] = new Entry(this.table[index], hash, row);
/* 220 */     this.count++;
/* 221 */     return this.table[index];
/*     */   }
/*     */   
/*     */   private void rehash() {
/* 225 */     int oldCapacity = this.table.length;
/* 226 */     Entry[] oldMap = this.table;
/*     */     
/* 228 */     int newCapacity = oldCapacity * 2 + 1;
/* 229 */     Entry[] newMap = new Entry[newCapacity];
/* 230 */     Arrays.fill((Object[])newMap, NULL_ENTRY);
/*     */     
/* 232 */     this.threshold = (int)(newCapacity * this.loadFactor);
/* 233 */     this.table = newMap;
/*     */     
/* 235 */     for (int i = oldCapacity; i-- > 0;) {
/* 236 */       for (Entry old = oldMap[i]; old != NULL_ENTRY; ) {
/* 237 */         Entry e = old;
/* 238 */         old = old.next;
/*     */         
/* 240 */         int index = hashToIndex(e.hash);
/* 241 */         e.next = this.table[index];
/* 242 */         this.table[index] = e;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/* 247 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*     */ 
/*     */   
/*     */   private void init(SOAPVersion ver) {
/* 251 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */   }
/*     */ 
/*     */   
/*     */   public InternalTypeMappingRegistryImpl(TypeMappingRegistry registry) {
/* 256 */     init();
/* 257 */     this.registry = registry;
/* 258 */     setupDynamicSerializers(SOAPVersion.SOAP_11);
/* 259 */     setupDynamicSerializers(SOAPVersion.SOAP_12);
/*     */   }
/*     */   
/*     */   protected void init() {
/* 263 */     int initialCapacity = 57;
/* 264 */     this.table = new Entry[initialCapacity];
/* 265 */     Arrays.fill((Object[])this.table, NULL_ENTRY);
/* 266 */     this.count = 0;
/* 267 */     this.loadFactor = 0.75F;
/* 268 */     this.threshold = (int)(initialCapacity * this.loadFactor);
/*     */   }
/*     */   
/*     */   protected void setupDynamicSerializers(SOAPVersion ver) {
/* 272 */     init(ver);
/*     */     try {
/* 274 */       ExtendedTypeMapping soapMappings = (ExtendedTypeMapping)this.registry.getTypeMapping(this.soapEncodingConstants.getURIEncoding());
/*     */ 
/*     */       
/* 277 */       if (soapMappings != null) {
/* 278 */         CombinedSerializer anyTypeSerializer = new DynamicSerializer(SchemaConstants.QNAME_TYPE_URTYPE, true, true, this.soapEncodingConstants.getSOAPEncodingNamespace(), ver);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 285 */         anyTypeSerializer = new ReferenceableSerializerImpl(false, anyTypeSerializer, ver);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 290 */         ((Initializable)anyTypeSerializer).initialize(this);
/*     */         
/* 292 */         soapMappings.register(Object.class, SchemaConstants.QNAME_TYPE_URTYPE, new SingletonSerializerFactory(anyTypeSerializer), new SingletonDeserializerFactory(anyTypeSerializer));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 301 */         QName ELEMENT_NAME = null;
/* 302 */         CombinedSerializer polymorphicArraySerializer = new PolymorphicArraySerializer(this.soapEncodingConstants.getQNameEncodingArray(), false, true, this.soapEncodingConstants.getURIEncoding(), ELEMENT_NAME, ver);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 310 */         polymorphicArraySerializer = new ReferenceableSerializerImpl(false, polymorphicArraySerializer, ver);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 315 */         ((Initializable)polymorphicArraySerializer).initialize(this);
/*     */         
/* 317 */         soapMappings.register(Object[].class, this.soapEncodingConstants.getQNameEncodingArray(), new SingletonSerializerFactory(polymorphicArraySerializer), new SingletonDeserializerFactory(polymorphicArraySerializer));
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 324 */     catch (Exception e) {
/* 325 */       throw new EncodingException("nestedEncodingError", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Row getRowMatching(String encoding, Class javaType, QName xmlType) {
/* 336 */     int hash = encoding.hashCode() ^ javaType.hashCode() ^ xmlType.hashCode();
/*     */     
/* 338 */     Entry matchingRowEntry = get(hash).getEntryMatching(encoding, javaType, xmlType);
/*     */     
/* 340 */     if (matchingRowEntry == NULL_ENTRY) {
/* 341 */       Row row = new Row(encoding, javaType, xmlType);
/*     */       
/* 343 */       put(encoding.hashCode() ^ javaType.hashCode(), row);
/* 344 */       put(encoding.hashCode() ^ xmlType.hashCode(), row);
/* 345 */       matchingRowEntry = put(hash, row);
/*     */     } 
/* 347 */     return matchingRowEntry.row;
/*     */   }
/*     */   
/*     */   protected Row getRowMatching(String encoding, QName xmlType) {
/* 351 */     int hash = encoding.hashCode() ^ xmlType.hashCode();
/* 352 */     Entry matchingRowEntry = get(hash).getEntryMatching(encoding, xmlType);
/* 353 */     if (matchingRowEntry == NULL_ENTRY) {
/* 354 */       Row row = new Row(encoding, null, xmlType);
/*     */       
/* 356 */       matchingRowEntry = put(hash, row);
/*     */     } 
/* 358 */     return matchingRowEntry.row;
/*     */   }
/*     */   
/*     */   protected Row getRowMatching(String encoding, Class javaType) {
/* 362 */     int hash = encoding.hashCode() ^ javaType.hashCode();
/* 363 */     Entry matchingRowEntry = get(hash).getEntryMatching(encoding, javaType);
/* 364 */     if (matchingRowEntry == NULL_ENTRY) {
/* 365 */       Row row = new Row(encoding, javaType, null);
/*     */       
/* 367 */       matchingRowEntry = put(hash, row);
/*     */     } 
/* 369 */     return matchingRowEntry.row;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializer getSerializer(String encoding, Class javaType, QName xmlType) throws Exception {
/*     */     Row row;
/* 379 */     if (javaType == null) {
/* 380 */       if (xmlType == null) {
/* 381 */         throw new IllegalArgumentException("getSerializer requires a Java type and/or an XML type");
/*     */       }
/* 383 */       row = getRowMatching(encoding, xmlType);
/* 384 */     } else if (xmlType == null) {
/* 385 */       row = getRowMatching(encoding, javaType);
/*     */     } else {
/* 387 */       row = getRowMatching(encoding, javaType, xmlType);
/*     */     } 
/*     */     
/* 390 */     if (row.serializer == null) {
/* 391 */       TypeMapping mapping = TypeMappingUtil.getTypeMapping(this.registry, encoding);
/*     */       
/* 393 */       Serializer serializer = TypeMappingUtil.getSerializer(mapping, javaType, xmlType);
/*     */       
/* 395 */       row.serializer = serializer;
/*     */       
/* 397 */       if (serializer instanceof Initializable) {
/* 398 */         ((Initializable)serializer).initialize(this);
/*     */       }
/*     */     } 
/* 401 */     return row.serializer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializer getSerializer(String encoding, Class javaType) throws Exception {
/* 407 */     return getSerializer(encoding, javaType, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Serializer getSerializer(String encoding, QName xmlType) throws Exception {
/* 413 */     return getSerializer(encoding, null, xmlType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Deserializer getDeserializer(String encoding, Class javaType, QName xmlType) throws Exception {
/*     */     Row row;
/* 423 */     if (javaType == null) {
/* 424 */       if (xmlType == null) {
/* 425 */         throw new IllegalArgumentException("getSerializer requires a Java type and/or an XML type");
/*     */       }
/* 427 */       row = getRowMatching(encoding, xmlType);
/* 428 */     } else if (xmlType == null) {
/* 429 */       row = getRowMatching(encoding, javaType);
/*     */     } else {
/* 431 */       row = getRowMatching(encoding, javaType, xmlType);
/*     */     } 
/*     */     
/* 434 */     if (row.deserializer == null) {
/* 435 */       TypeMapping mapping = TypeMappingUtil.getTypeMapping(this.registry, encoding);
/*     */       
/* 437 */       Deserializer deserializer = TypeMappingUtil.getDeserializer(mapping, javaType, xmlType);
/*     */       
/* 439 */       row.deserializer = deserializer;
/*     */       
/* 441 */       if (deserializer instanceof Initializable) {
/* 442 */         ((Initializable)deserializer).initialize(this);
/*     */       }
/*     */     } 
/* 445 */     return row.deserializer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Deserializer getDeserializer(String encoding, Class javaType) throws Exception {
/* 451 */     return getDeserializer(encoding, javaType, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Deserializer getDeserializer(String encoding, QName xmlType) throws Exception {
/* 457 */     return getDeserializer(encoding, null, xmlType);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class getJavaType(String encoding, QName xmlType) throws Exception {
/* 462 */     if (xmlType == null) {
/* 463 */       throw new IllegalArgumentException("getJavaType requires an XML type");
/*     */     }
/*     */     
/* 466 */     Row row = getRowMatching(encoding, xmlType);
/*     */     
/* 468 */     if (row.javaType == null) {
/* 469 */       ExtendedTypeMapping mapping = (ExtendedTypeMapping)TypeMappingUtil.getTypeMapping(this.registry, encoding);
/*     */ 
/*     */ 
/*     */       
/* 473 */       if (mapping != null) {
/* 474 */         return mapping.getJavaType(xmlType);
/*     */       }
/* 476 */       return null;
/*     */     } 
/* 478 */     return row.javaType;
/*     */   }
/*     */   
/*     */   public QName getXmlType(String encoding, Class javaType) throws Exception {
/* 482 */     if (javaType == null) {
/* 483 */       throw new IllegalArgumentException("getXmlType requires a Java type");
/*     */     }
/*     */     
/* 486 */     Row row = getRowMatching(encoding, javaType);
/*     */     
/* 488 */     if (row.xmlType == null) {
/* 489 */       ExtendedTypeMapping mapping = (ExtendedTypeMapping)TypeMappingUtil.getTypeMapping(this.registry, encoding);
/*     */ 
/*     */ 
/*     */       
/* 493 */       if (mapping != null) {
/* 494 */         return mapping.getXmlType(javaType);
/*     */       }
/* 496 */       return null;
/*     */     } 
/* 498 */     return row.xmlType;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\encoding\InternalTypeMappingRegistryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */