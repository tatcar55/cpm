/*     */ package com.sun.xml.fastinfoset.vocab;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.QualifiedName;
/*     */ import com.sun.xml.fastinfoset.util.CharArray;
/*     */ import com.sun.xml.fastinfoset.util.CharArrayArray;
/*     */ import com.sun.xml.fastinfoset.util.ContiguousCharArrayArray;
/*     */ import com.sun.xml.fastinfoset.util.FixedEntryStringIntMap;
/*     */ import com.sun.xml.fastinfoset.util.PrefixArray;
/*     */ import com.sun.xml.fastinfoset.util.QualifiedNameArray;
/*     */ import com.sun.xml.fastinfoset.util.StringArray;
/*     */ import com.sun.xml.fastinfoset.util.StringIntMap;
/*     */ import com.sun.xml.fastinfoset.util.ValueArray;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.jvnet.fastinfoset.Vocabulary;
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
/*     */ public class ParserVocabulary
/*     */   extends Vocabulary
/*     */ {
/*     */   public static final String IDENTIFYING_STRING_TABLE_MAXIMUM_ITEMS_PEOPERTY = "com.sun.xml.fastinfoset.vocab.ParserVocabulary.IdentifyingStringTable.maximumItems";
/*     */   public static final String NON_IDENTIFYING_STRING_TABLE_MAXIMUM_ITEMS_PEOPERTY = "com.sun.xml.fastinfoset.vocab.ParserVocabulary.NonIdentifyingStringTable.maximumItems";
/*     */   public static final String NON_IDENTIFYING_STRING_TABLE_MAXIMUM_CHARACTERS_PEOPERTY = "com.sun.xml.fastinfoset.vocab.ParserVocabulary.NonIdentifyingStringTable.maximumCharacters";
/*  44 */   protected static final int IDENTIFYING_STRING_TABLE_MAXIMUM_ITEMS = getIntegerValueFromProperty("com.sun.xml.fastinfoset.vocab.ParserVocabulary.IdentifyingStringTable.maximumItems");
/*     */   
/*  46 */   protected static final int NON_IDENTIFYING_STRING_TABLE_MAXIMUM_ITEMS = getIntegerValueFromProperty("com.sun.xml.fastinfoset.vocab.ParserVocabulary.NonIdentifyingStringTable.maximumItems");
/*     */   
/*  48 */   protected static final int NON_IDENTIFYING_STRING_TABLE_MAXIMUM_CHARACTERS = getIntegerValueFromProperty("com.sun.xml.fastinfoset.vocab.ParserVocabulary.NonIdentifyingStringTable.maximumCharacters");
/*     */ 
/*     */   
/*     */   private static int getIntegerValueFromProperty(String property) {
/*  52 */     String value = System.getProperty(property);
/*  53 */     if (value == null) {
/*  54 */       return Integer.MAX_VALUE;
/*     */     }
/*     */     
/*     */     try {
/*  58 */       return Math.max(Integer.parseInt(value), 10);
/*  59 */     } catch (NumberFormatException e) {
/*  60 */       return Integer.MAX_VALUE;
/*     */     } 
/*     */   }
/*     */   
/*  64 */   public final CharArrayArray restrictedAlphabet = new CharArrayArray(10, 256);
/*  65 */   public final StringArray encodingAlgorithm = new StringArray(10, 256, true);
/*     */   
/*     */   public final StringArray namespaceName;
/*     */   
/*     */   public final PrefixArray prefix;
/*     */   
/*     */   public final StringArray localName;
/*     */   
/*     */   public final StringArray otherNCName;
/*     */   public final StringArray otherURI;
/*     */   public final StringArray attributeValue;
/*     */   public final CharArrayArray otherString;
/*     */   public final ContiguousCharArrayArray characterContentChunk;
/*     */   public final QualifiedNameArray elementName;
/*     */   public final QualifiedNameArray attributeName;
/*  80 */   public final ValueArray[] tables = new ValueArray[12];
/*     */   
/*     */   protected SerializerVocabulary _readOnlyVocabulary;
/*     */ 
/*     */   
/*     */   public ParserVocabulary() {
/*  86 */     this.namespaceName = new StringArray(10, IDENTIFYING_STRING_TABLE_MAXIMUM_ITEMS, false);
/*  87 */     this.prefix = new PrefixArray(10, IDENTIFYING_STRING_TABLE_MAXIMUM_ITEMS);
/*  88 */     this.localName = new StringArray(10, IDENTIFYING_STRING_TABLE_MAXIMUM_ITEMS, false);
/*  89 */     this.otherNCName = new StringArray(10, IDENTIFYING_STRING_TABLE_MAXIMUM_ITEMS, false);
/*  90 */     this.otherURI = new StringArray(10, IDENTIFYING_STRING_TABLE_MAXIMUM_ITEMS, true);
/*  91 */     this.attributeValue = new StringArray(10, NON_IDENTIFYING_STRING_TABLE_MAXIMUM_ITEMS, true);
/*  92 */     this.otherString = new CharArrayArray(10, NON_IDENTIFYING_STRING_TABLE_MAXIMUM_ITEMS);
/*     */     
/*  94 */     this.characterContentChunk = new ContiguousCharArrayArray(10, NON_IDENTIFYING_STRING_TABLE_MAXIMUM_ITEMS, 512, NON_IDENTIFYING_STRING_TABLE_MAXIMUM_CHARACTERS);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     this.elementName = new QualifiedNameArray(10, IDENTIFYING_STRING_TABLE_MAXIMUM_ITEMS);
/* 100 */     this.attributeName = new QualifiedNameArray(10, IDENTIFYING_STRING_TABLE_MAXIMUM_ITEMS);
/*     */     
/* 102 */     this.tables[0] = (ValueArray)this.restrictedAlphabet;
/* 103 */     this.tables[1] = (ValueArray)this.encodingAlgorithm;
/* 104 */     this.tables[2] = (ValueArray)this.prefix;
/* 105 */     this.tables[3] = (ValueArray)this.namespaceName;
/* 106 */     this.tables[4] = (ValueArray)this.localName;
/* 107 */     this.tables[5] = (ValueArray)this.otherNCName;
/* 108 */     this.tables[6] = (ValueArray)this.otherURI;
/* 109 */     this.tables[7] = (ValueArray)this.attributeValue;
/* 110 */     this.tables[8] = (ValueArray)this.otherString;
/* 111 */     this.tables[9] = (ValueArray)this.characterContentChunk;
/* 112 */     this.tables[10] = (ValueArray)this.elementName;
/* 113 */     this.tables[11] = (ValueArray)this.attributeName;
/*     */   }
/*     */ 
/*     */   
/*     */   public ParserVocabulary(Vocabulary v) {
/* 118 */     this();
/*     */     
/* 120 */     convertVocabulary(v);
/*     */   }
/*     */   
/*     */   void setReadOnlyVocabulary(ParserVocabulary readOnlyVocabulary, boolean clear) {
/* 124 */     for (int i = 0; i < this.tables.length; i++) {
/* 125 */       this.tables[i].setReadOnlyArray(readOnlyVocabulary.tables[i], clear);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setInitialVocabulary(ParserVocabulary initialVocabulary, boolean clear) {
/* 130 */     setExternalVocabularyURI(null);
/* 131 */     setInitialReadOnlyVocabulary(true);
/* 132 */     setReadOnlyVocabulary(initialVocabulary, clear);
/*     */   }
/*     */   
/*     */   public void setReferencedVocabulary(String referencedVocabularyURI, ParserVocabulary referencedVocabulary, boolean clear) {
/* 136 */     if (!referencedVocabularyURI.equals(getExternalVocabularyURI())) {
/* 137 */       setInitialReadOnlyVocabulary(false);
/* 138 */       setExternalVocabularyURI(referencedVocabularyURI);
/* 139 */       setReadOnlyVocabulary(referencedVocabulary, clear);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clear() {
/* 144 */     for (int i = 0; i < this.tables.length; i++) {
/* 145 */       this.tables[i].clear();
/*     */     }
/*     */   }
/*     */   
/*     */   private void convertVocabulary(Vocabulary v) {
/* 150 */     FixedEntryStringIntMap fixedEntryStringIntMap1 = new FixedEntryStringIntMap("xml", 8);
/*     */     
/* 152 */     FixedEntryStringIntMap fixedEntryStringIntMap2 = new FixedEntryStringIntMap("http://www.w3.org/XML/1998/namespace", 8);
/*     */     
/* 154 */     StringIntMap localNameMap = new StringIntMap();
/*     */     
/* 156 */     addToTable(v.restrictedAlphabets.iterator(), this.restrictedAlphabet);
/* 157 */     addToTable(v.encodingAlgorithms.iterator(), this.encodingAlgorithm);
/* 158 */     addToTable(v.prefixes.iterator(), this.prefix, (StringIntMap)fixedEntryStringIntMap1);
/* 159 */     addToTable(v.namespaceNames.iterator(), this.namespaceName, (StringIntMap)fixedEntryStringIntMap2);
/* 160 */     addToTable(v.localNames.iterator(), this.localName, localNameMap);
/* 161 */     addToTable(v.otherNCNames.iterator(), this.otherNCName);
/* 162 */     addToTable(v.otherURIs.iterator(), this.otherURI);
/* 163 */     addToTable(v.attributeValues.iterator(), this.attributeValue);
/* 164 */     addToTable(v.otherStrings.iterator(), this.otherString);
/* 165 */     addToTable(v.characterContentChunks.iterator(), this.characterContentChunk);
/* 166 */     addToTable(v.elements.iterator(), this.elementName, false, (StringIntMap)fixedEntryStringIntMap1, (StringIntMap)fixedEntryStringIntMap2, localNameMap);
/*     */     
/* 168 */     addToTable(v.attributes.iterator(), this.attributeName, true, (StringIntMap)fixedEntryStringIntMap1, (StringIntMap)fixedEntryStringIntMap2, localNameMap);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addToTable(Iterator<String> i, StringArray a) {
/* 173 */     while (i.hasNext()) {
/* 174 */       addToTable(i.next(), a, (StringIntMap)null);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addToTable(Iterator<String> i, StringArray a, StringIntMap m) {
/* 179 */     while (i.hasNext()) {
/* 180 */       addToTable(i.next(), a, m);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addToTable(String s, StringArray a, StringIntMap m) {
/* 185 */     if (s.length() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 189 */     if (m != null) m.obtainIndex(s); 
/* 190 */     a.add(s);
/*     */   }
/*     */   
/*     */   private void addToTable(Iterator<String> i, PrefixArray a, StringIntMap m) {
/* 194 */     while (i.hasNext()) {
/* 195 */       addToTable(i.next(), a, m);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addToTable(String s, PrefixArray a, StringIntMap m) {
/* 200 */     if (s.length() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 204 */     if (m != null) m.obtainIndex(s); 
/* 205 */     a.add(s);
/*     */   }
/*     */   
/*     */   private void addToTable(Iterator<String> i, ContiguousCharArrayArray a) {
/* 209 */     while (i.hasNext()) {
/* 210 */       addToTable(i.next(), a);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addToTable(String s, ContiguousCharArrayArray a) {
/* 215 */     if (s.length() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 219 */     char[] c = s.toCharArray();
/* 220 */     a.add(c, c.length);
/*     */   }
/*     */   
/*     */   private void addToTable(Iterator<String> i, CharArrayArray a) {
/* 224 */     while (i.hasNext()) {
/* 225 */       addToTable(i.next(), a);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addToTable(String s, CharArrayArray a) {
/* 230 */     if (s.length() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 234 */     char[] c = s.toCharArray();
/* 235 */     a.add(new CharArray(c, 0, c.length, false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addToTable(Iterator<QName> i, QualifiedNameArray a, boolean isAttribute, StringIntMap prefixMap, StringIntMap namespaceNameMap, StringIntMap localNameMap) {
/* 242 */     while (i.hasNext()) {
/* 243 */       addToNameTable(i.next(), a, isAttribute, prefixMap, namespaceNameMap, localNameMap);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addToNameTable(QName n, QualifiedNameArray a, boolean isAttribute, StringIntMap prefixMap, StringIntMap namespaceNameMap, StringIntMap localNameMap) {
/* 252 */     int namespaceURIIndex = -1;
/* 253 */     int prefixIndex = -1;
/* 254 */     if (n.getNamespaceURI().length() > 0) {
/* 255 */       namespaceURIIndex = namespaceNameMap.obtainIndex(n.getNamespaceURI());
/* 256 */       if (namespaceURIIndex == -1) {
/* 257 */         namespaceURIIndex = this.namespaceName.getSize();
/* 258 */         this.namespaceName.add(n.getNamespaceURI());
/*     */       } 
/*     */       
/* 261 */       if (n.getPrefix().length() > 0) {
/* 262 */         prefixIndex = prefixMap.obtainIndex(n.getPrefix());
/* 263 */         if (prefixIndex == -1) {
/* 264 */           prefixIndex = this.prefix.getSize();
/* 265 */           this.prefix.add(n.getPrefix());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 270 */     int localNameIndex = localNameMap.obtainIndex(n.getLocalPart());
/* 271 */     if (localNameIndex == -1) {
/* 272 */       localNameIndex = this.localName.getSize();
/* 273 */       this.localName.add(n.getLocalPart());
/*     */     } 
/*     */     
/* 276 */     QualifiedName name = new QualifiedName(n.getPrefix(), n.getNamespaceURI(), n.getLocalPart(), a.getSize(), prefixIndex, namespaceURIIndex, localNameIndex);
/*     */ 
/*     */     
/* 279 */     if (isAttribute) {
/* 280 */       name.createAttributeValues(256);
/*     */     }
/* 282 */     a.add(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\vocab\ParserVocabulary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */