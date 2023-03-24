/*     */ package com.sun.xml.fastinfoset.vocab;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.QualifiedName;
/*     */ import com.sun.xml.fastinfoset.util.CharArrayIntMap;
/*     */ import com.sun.xml.fastinfoset.util.FixedEntryStringIntMap;
/*     */ import com.sun.xml.fastinfoset.util.KeyIntMap;
/*     */ import com.sun.xml.fastinfoset.util.LocalNameQualifiedNamesMap;
/*     */ import com.sun.xml.fastinfoset.util.StringIntMap;
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
/*     */ public class SerializerVocabulary
/*     */   extends Vocabulary
/*     */ {
/*     */   public final StringIntMap restrictedAlphabet;
/*     */   public final StringIntMap encodingAlgorithm;
/*     */   public final StringIntMap namespaceName;
/*     */   public final StringIntMap prefix;
/*     */   public final StringIntMap localName;
/*     */   public final StringIntMap otherNCName;
/*     */   public final StringIntMap otherURI;
/*     */   public final StringIntMap attributeValue;
/*     */   public final CharArrayIntMap otherString;
/*     */   public final CharArrayIntMap characterContentChunk;
/*     */   public final LocalNameQualifiedNamesMap elementName;
/*     */   public final LocalNameQualifiedNamesMap attributeName;
/*  47 */   public final KeyIntMap[] tables = new KeyIntMap[12];
/*     */   
/*     */   protected boolean _useLocalNameAsKey;
/*     */   
/*     */   protected SerializerVocabulary _readOnlyVocabulary;
/*     */   
/*     */   public SerializerVocabulary() {
/*  54 */     this.tables[0] = (KeyIntMap)(this.restrictedAlphabet = new StringIntMap(4));
/*  55 */     this.tables[1] = (KeyIntMap)(this.encodingAlgorithm = new StringIntMap(4));
/*  56 */     this.tables[2] = (KeyIntMap)(this.prefix = (StringIntMap)new FixedEntryStringIntMap("xml", 8));
/*  57 */     this.tables[3] = (KeyIntMap)(this.namespaceName = (StringIntMap)new FixedEntryStringIntMap("http://www.w3.org/XML/1998/namespace", 8));
/*  58 */     this.tables[4] = (KeyIntMap)(this.localName = new StringIntMap());
/*  59 */     this.tables[5] = (KeyIntMap)(this.otherNCName = new StringIntMap(4));
/*  60 */     this.tables[6] = (KeyIntMap)(this.otherURI = new StringIntMap(4));
/*  61 */     this.tables[7] = (KeyIntMap)(this.attributeValue = new StringIntMap());
/*  62 */     this.tables[8] = (KeyIntMap)(this.otherString = new CharArrayIntMap(4));
/*  63 */     this.tables[9] = (KeyIntMap)(this.characterContentChunk = new CharArrayIntMap());
/*  64 */     this.tables[10] = (KeyIntMap)(this.elementName = new LocalNameQualifiedNamesMap());
/*  65 */     this.tables[11] = (KeyIntMap)(this.attributeName = new LocalNameQualifiedNamesMap());
/*     */   }
/*     */ 
/*     */   
/*     */   public SerializerVocabulary(Vocabulary v, boolean useLocalNameAsKey) {
/*  70 */     this();
/*     */     
/*  72 */     this._useLocalNameAsKey = useLocalNameAsKey;
/*  73 */     convertVocabulary(v);
/*     */   }
/*     */   
/*     */   public SerializerVocabulary getReadOnlyVocabulary() {
/*  77 */     return this._readOnlyVocabulary;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setReadOnlyVocabulary(SerializerVocabulary readOnlyVocabulary, boolean clear) {
/*  82 */     for (int i = 0; i < this.tables.length; i++) {
/*  83 */       this.tables[i].setReadOnlyMap(readOnlyVocabulary.tables[i], clear);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInitialVocabulary(SerializerVocabulary initialVocabulary, boolean clear) {
/*  89 */     setExternalVocabularyURI(null);
/*  90 */     setInitialReadOnlyVocabulary(true);
/*  91 */     setReadOnlyVocabulary(initialVocabulary, clear);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExternalVocabulary(String externalVocabularyURI, SerializerVocabulary externalVocabulary, boolean clear) {
/*  96 */     setInitialReadOnlyVocabulary(false);
/*  97 */     setExternalVocabularyURI(externalVocabularyURI);
/*  98 */     setReadOnlyVocabulary(externalVocabulary, clear);
/*     */   }
/*     */   
/*     */   public void clear() {
/* 102 */     for (int i = 0; i < this.tables.length; i++) {
/* 103 */       this.tables[i].clear();
/*     */     }
/*     */   }
/*     */   
/*     */   private void convertVocabulary(Vocabulary v) {
/* 108 */     addToTable(v.restrictedAlphabets.iterator(), this.restrictedAlphabet);
/* 109 */     addToTable(v.encodingAlgorithms.iterator(), this.encodingAlgorithm);
/* 110 */     addToTable(v.prefixes.iterator(), this.prefix);
/* 111 */     addToTable(v.namespaceNames.iterator(), this.namespaceName);
/* 112 */     addToTable(v.localNames.iterator(), this.localName);
/* 113 */     addToTable(v.otherNCNames.iterator(), this.otherNCName);
/* 114 */     addToTable(v.otherURIs.iterator(), this.otherURI);
/* 115 */     addToTable(v.attributeValues.iterator(), this.attributeValue);
/* 116 */     addToTable(v.otherStrings.iterator(), this.otherString);
/* 117 */     addToTable(v.characterContentChunks.iterator(), this.characterContentChunk);
/* 118 */     addToTable(v.elements.iterator(), this.elementName);
/* 119 */     addToTable(v.attributes.iterator(), this.attributeName);
/*     */   }
/*     */   
/*     */   private void addToTable(Iterator<String> i, StringIntMap m) {
/* 123 */     while (i.hasNext()) {
/* 124 */       addToTable(i.next(), m);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addToTable(String s, StringIntMap m) {
/* 129 */     if (s.length() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 133 */     m.obtainIndex(s);
/*     */   }
/*     */   
/*     */   private void addToTable(Iterator<String> i, CharArrayIntMap m) {
/* 137 */     while (i.hasNext()) {
/* 138 */       addToTable(i.next(), m);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addToTable(String s, CharArrayIntMap m) {
/* 143 */     if (s.length() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 147 */     char[] c = s.toCharArray();
/* 148 */     m.obtainIndex(c, 0, c.length, false);
/*     */   }
/*     */   
/*     */   private void addToTable(Iterator<QName> i, LocalNameQualifiedNamesMap m) {
/* 152 */     while (i.hasNext()) {
/* 153 */       addToNameTable(i.next(), m);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addToNameTable(QName n, LocalNameQualifiedNamesMap m) {
/* 158 */     int namespaceURIIndex = -1;
/* 159 */     int prefixIndex = -1;
/* 160 */     if (n.getNamespaceURI().length() > 0) {
/* 161 */       namespaceURIIndex = this.namespaceName.obtainIndex(n.getNamespaceURI());
/* 162 */       if (namespaceURIIndex == -1) {
/* 163 */         namespaceURIIndex = this.namespaceName.get(n.getNamespaceURI());
/*     */       }
/*     */       
/* 166 */       if (n.getPrefix().length() > 0) {
/* 167 */         prefixIndex = this.prefix.obtainIndex(n.getPrefix());
/* 168 */         if (prefixIndex == -1) {
/* 169 */           prefixIndex = this.prefix.get(n.getPrefix());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     int localNameIndex = this.localName.obtainIndex(n.getLocalPart());
/* 175 */     if (localNameIndex == -1) {
/* 176 */       localNameIndex = this.localName.get(n.getLocalPart());
/*     */     }
/*     */     
/* 179 */     QualifiedName name = new QualifiedName(n.getPrefix(), n.getNamespaceURI(), n.getLocalPart(), m.getNextIndex(), prefixIndex, namespaceURIIndex, localNameIndex);
/*     */ 
/*     */ 
/*     */     
/* 183 */     LocalNameQualifiedNamesMap.Entry entry = null;
/* 184 */     if (this._useLocalNameAsKey) {
/* 185 */       entry = m.obtainEntry(n.getLocalPart());
/*     */     } else {
/* 187 */       String qName = (prefixIndex == -1) ? n.getLocalPart() : (n.getPrefix() + ":" + n.getLocalPart());
/*     */ 
/*     */       
/* 190 */       entry = m.obtainEntry(qName);
/*     */     } 
/*     */     
/* 193 */     entry.addQualifiedName(name);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\vocab\SerializerVocabulary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */