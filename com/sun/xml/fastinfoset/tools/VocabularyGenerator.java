/*     */ package com.sun.xml.fastinfoset.tools;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.CommonResourceBundle;
/*     */ import com.sun.xml.fastinfoset.QualifiedName;
/*     */ import com.sun.xml.fastinfoset.util.CharArray;
/*     */ import com.sun.xml.fastinfoset.util.LocalNameQualifiedNamesMap;
/*     */ import com.sun.xml.fastinfoset.util.PrefixArray;
/*     */ import com.sun.xml.fastinfoset.util.QualifiedNameArray;
/*     */ import com.sun.xml.fastinfoset.util.StringArray;
/*     */ import com.sun.xml.fastinfoset.util.StringIntMap;
/*     */ import com.sun.xml.fastinfoset.vocab.ParserVocabulary;
/*     */ import com.sun.xml.fastinfoset.vocab.SerializerVocabulary;
/*     */ import java.util.Set;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.jvnet.fastinfoset.Vocabulary;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.ext.LexicalHandler;
/*     */ import org.xml.sax.helpers.DefaultHandler;
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
/*     */ public class VocabularyGenerator
/*     */   extends DefaultHandler
/*     */   implements LexicalHandler
/*     */ {
/*     */   protected SerializerVocabulary _serializerVocabulary;
/*     */   protected ParserVocabulary _parserVocabulary;
/*     */   protected Vocabulary _v;
/*  46 */   protected int attributeValueSizeConstraint = 32;
/*     */   
/*  48 */   protected int characterContentChunkSizeContraint = 32;
/*     */ 
/*     */   
/*     */   public VocabularyGenerator() {
/*  52 */     this._serializerVocabulary = new SerializerVocabulary();
/*  53 */     this._parserVocabulary = new ParserVocabulary();
/*     */     
/*  55 */     this._v = new Vocabulary();
/*     */   }
/*     */   
/*     */   public VocabularyGenerator(SerializerVocabulary serializerVocabulary) {
/*  59 */     this._serializerVocabulary = serializerVocabulary;
/*  60 */     this._parserVocabulary = new ParserVocabulary();
/*     */     
/*  62 */     this._v = new Vocabulary();
/*     */   }
/*     */   
/*     */   public VocabularyGenerator(ParserVocabulary parserVocabulary) {
/*  66 */     this._serializerVocabulary = new SerializerVocabulary();
/*  67 */     this._parserVocabulary = parserVocabulary;
/*     */     
/*  69 */     this._v = new Vocabulary();
/*     */   }
/*     */ 
/*     */   
/*     */   public VocabularyGenerator(SerializerVocabulary serializerVocabulary, ParserVocabulary parserVocabulary) {
/*  74 */     this._serializerVocabulary = serializerVocabulary;
/*  75 */     this._parserVocabulary = parserVocabulary;
/*     */     
/*  77 */     this._v = new Vocabulary();
/*     */   }
/*     */   
/*     */   public Vocabulary getVocabulary() {
/*  81 */     return this._v;
/*     */   }
/*     */   
/*     */   public void setCharacterContentChunkSizeLimit(int size) {
/*  85 */     if (size < 0) {
/*  86 */       size = 0;
/*     */     }
/*     */     
/*  89 */     this.characterContentChunkSizeContraint = size;
/*     */   }
/*     */   
/*     */   public int getCharacterContentChunkSizeLimit() {
/*  93 */     return this.characterContentChunkSizeContraint;
/*     */   }
/*     */   
/*     */   public void setAttributeValueSizeLimit(int size) {
/*  97 */     if (size < 0) {
/*  98 */       size = 0;
/*     */     }
/*     */     
/* 101 */     this.attributeValueSizeConstraint = size;
/*     */   }
/*     */   
/*     */   public int getAttributeValueSizeLimit() {
/* 105 */     return this.attributeValueSizeConstraint;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument() throws SAXException {}
/*     */ 
/*     */   
/*     */   public void endDocument() throws SAXException {}
/*     */ 
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 117 */     addToTable(prefix, this._v.prefixes, this._serializerVocabulary.prefix, this._parserVocabulary.prefix);
/* 118 */     addToTable(uri, this._v.namespaceNames, this._serializerVocabulary.namespaceName, this._parserVocabulary.namespaceName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {}
/*     */   
/*     */   public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
/* 125 */     addToNameTable(namespaceURI, qName, localName, this._v.elements, this._serializerVocabulary.elementName, this._parserVocabulary.elementName, false);
/*     */ 
/*     */     
/* 128 */     for (int a = 0; a < atts.getLength(); a++) {
/* 129 */       addToNameTable(atts.getURI(a), atts.getQName(a), atts.getLocalName(a), this._v.attributes, this._serializerVocabulary.attributeName, this._parserVocabulary.attributeName, true);
/*     */ 
/*     */       
/* 132 */       String value = atts.getValue(a);
/* 133 */       if (value.length() < this.attributeValueSizeConstraint) {
/* 134 */         addToTable(value, this._v.attributeValues, this._serializerVocabulary.attributeValue, this._parserVocabulary.attributeValue);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void endElement(String namespaceURI, String localName, String qName) throws SAXException {}
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 143 */     if (length < this.characterContentChunkSizeContraint) {
/* 144 */       addToCharArrayTable(new CharArray(ch, start, length, true));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void startCDATA() throws SAXException {}
/*     */ 
/*     */   
/*     */   public void endCDATA() throws SAXException {}
/*     */ 
/*     */   
/*     */   public void startDTD(String name, String publicId, String systemId) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void endDTD() throws SAXException {}
/*     */ 
/*     */   
/*     */   public void startEntity(String name) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void endEntity(String name) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void addToTable(String s, Set<String> v, StringIntMap m, StringArray a) {
/* 187 */     if (s.length() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 191 */     if (m.obtainIndex(s) == -1) {
/* 192 */       a.add(s);
/*     */     }
/*     */     
/* 195 */     v.add(s);
/*     */   }
/*     */   
/*     */   public void addToTable(String s, Set<String> v, StringIntMap m, PrefixArray a) {
/* 199 */     if (s.length() == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 203 */     if (m.obtainIndex(s) == -1) {
/* 204 */       a.add(s);
/*     */     }
/*     */     
/* 207 */     v.add(s);
/*     */   }
/*     */   
/*     */   public void addToCharArrayTable(CharArray c) {
/* 211 */     if (this._serializerVocabulary.characterContentChunk.obtainIndex(c.ch, c.start, c.length, false) == -1) {
/* 212 */       this._parserVocabulary.characterContentChunk.add(c.ch, c.length);
/*     */     }
/*     */     
/* 215 */     this._v.characterContentChunks.add(c.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addToNameTable(String namespaceURI, String qName, String localName, Set<QName> v, LocalNameQualifiedNamesMap m, QualifiedNameArray a, boolean isAttribute) throws SAXException {
/* 221 */     LocalNameQualifiedNamesMap.Entry entry = m.obtainEntry(qName);
/* 222 */     if (entry._valueIndex > 0) {
/* 223 */       QualifiedName[] names = entry._value;
/* 224 */       for (int i = 0; i < entry._valueIndex; i++) {
/* 225 */         if (namespaceURI == (names[i]).namespaceName || namespaceURI.equals((names[i]).namespaceName)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 231 */     String prefix = getPrefixFromQualifiedName(qName);
/*     */     
/* 233 */     int namespaceURIIndex = -1;
/* 234 */     int prefixIndex = -1;
/* 235 */     int localNameIndex = -1;
/* 236 */     if (namespaceURI.length() > 0) {
/* 237 */       namespaceURIIndex = this._serializerVocabulary.namespaceName.get(namespaceURI);
/* 238 */       if (namespaceURIIndex == -1) {
/* 239 */         throw new SAXException(CommonResourceBundle.getInstance().getString("message.namespaceURINotIndexed", new Object[] { Integer.valueOf(namespaceURIIndex) }));
/*     */       }
/*     */ 
/*     */       
/* 243 */       if (prefix.length() > 0) {
/* 244 */         prefixIndex = this._serializerVocabulary.prefix.get(prefix);
/* 245 */         if (prefixIndex == -1) {
/* 246 */           throw new SAXException(CommonResourceBundle.getInstance().getString("message.prefixNotIndexed", new Object[] { Integer.valueOf(prefixIndex) }));
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 252 */     localNameIndex = this._serializerVocabulary.localName.obtainIndex(localName);
/* 253 */     if (localNameIndex == -1) {
/* 254 */       this._parserVocabulary.localName.add(localName);
/* 255 */       localNameIndex = this._parserVocabulary.localName.getSize() - 1;
/*     */     } 
/* 257 */     QualifiedName name = new QualifiedName(prefix, namespaceURI, localName, m.getNextIndex(), prefixIndex, namespaceURIIndex, localNameIndex);
/*     */     
/* 259 */     if (isAttribute) {
/* 260 */       name.createAttributeValues(256);
/*     */     }
/* 262 */     entry.addQualifiedName(name);
/* 263 */     a.add(name);
/*     */     
/* 265 */     v.add(name.getQName());
/*     */   }
/*     */   
/*     */   public static String getPrefixFromQualifiedName(String qName) {
/* 269 */     int i = qName.indexOf(':');
/* 270 */     String prefix = "";
/* 271 */     if (i != -1) {
/* 272 */       prefix = qName.substring(0, i);
/*     */     }
/* 274 */     return prefix;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\tools\VocabularyGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */