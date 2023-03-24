/*     */ package com.sun.xml.fastinfoset.sax;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.QualifiedName;
/*     */ import com.sun.xml.fastinfoset.util.LocalNameQualifiedNamesMap;
/*     */ import com.sun.xml.fastinfoset.util.StringIntMap;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.jvnet.fastinfoset.FastInfosetException;
/*     */ import org.jvnet.fastinfoset.sax.EncodingAlgorithmAttributes;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SAXDocumentSerializerWithPrefixMapping
/*     */   extends SAXDocumentSerializer
/*     */ {
/*     */   protected Map _namespaceToPrefixMapping;
/*     */   protected Map _prefixToPrefixMapping;
/*     */   protected String _lastCheckedNamespace;
/*     */   protected String _lastCheckedPrefix;
/*     */   protected StringIntMap _declaredNamespaces;
/*     */   
/*     */   public SAXDocumentSerializerWithPrefixMapping(Map<?, ?> namespaceToPrefixMapping) {
/*  60 */     super(true);
/*  61 */     this._namespaceToPrefixMapping = new HashMap<Object, Object>(namespaceToPrefixMapping);
/*  62 */     this._prefixToPrefixMapping = new HashMap<Object, Object>();
/*     */ 
/*     */     
/*  65 */     this._namespaceToPrefixMapping.put("", "");
/*     */     
/*  67 */     this._namespaceToPrefixMapping.put("http://www.w3.org/XML/1998/namespace", "xml");
/*     */     
/*  69 */     this._declaredNamespaces = new StringIntMap(4);
/*     */   }
/*     */   
/*     */   public final void startPrefixMapping(String prefix, String uri) throws SAXException {
/*     */     try {
/*  74 */       if (!this._elementHasNamespaces) {
/*  75 */         encodeTermination();
/*     */ 
/*     */         
/*  78 */         mark();
/*  79 */         this._elementHasNamespaces = true;
/*     */ 
/*     */         
/*  82 */         write(56);
/*     */         
/*  84 */         this._declaredNamespaces.clear();
/*  85 */         this._declaredNamespaces.obtainIndex(uri);
/*     */       }
/*  87 */       else if (this._declaredNamespaces.obtainIndex(uri) != -1) {
/*  88 */         String str = getPrefix(uri);
/*  89 */         if (str != null) {
/*  90 */           this._prefixToPrefixMapping.put(prefix, str);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  96 */       String p = getPrefix(uri);
/*  97 */       if (p != null) {
/*  98 */         encodeNamespaceAttribute(p, uri);
/*  99 */         this._prefixToPrefixMapping.put(prefix, p);
/*     */       } else {
/* 101 */         putPrefix(uri, prefix);
/* 102 */         encodeNamespaceAttribute(prefix, uri);
/*     */       }
/*     */     
/* 105 */     } catch (IOException e) {
/* 106 */       throw new SAXException("startElement", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void encodeElement(String namespaceURI, String qName, String localName) throws IOException {
/* 111 */     LocalNameQualifiedNamesMap.Entry entry = this._v.elementName.obtainEntry(localName);
/* 112 */     if (entry._valueIndex > 0) {
/* 113 */       if (encodeElementMapEntry(entry, namespaceURI))
/*     */         return; 
/* 115 */       if (this._v.elementName.isQNameFromReadOnlyMap(entry._value[0])) {
/* 116 */         entry = this._v.elementName.obtainDynamicEntry(localName);
/* 117 */         if (entry._valueIndex > 0 && 
/* 118 */           encodeElementMapEntry(entry, namespaceURI)) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */     } 
/* 123 */     encodeLiteralElementQualifiedNameOnThirdBit(namespaceURI, getPrefix(namespaceURI), localName, entry);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean encodeElementMapEntry(LocalNameQualifiedNamesMap.Entry entry, String namespaceURI) throws IOException {
/* 128 */     QualifiedName[] names = entry._value;
/* 129 */     for (int i = 0; i < entry._valueIndex; i++) {
/* 130 */       if (namespaceURI == (names[i]).namespaceName || namespaceURI.equals((names[i]).namespaceName)) {
/* 131 */         encodeNonZeroIntegerOnThirdBit((names[i]).index);
/* 132 */         return true;
/*     */       } 
/*     */     } 
/* 135 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void encodeAttributes(Attributes atts) throws IOException, FastInfosetException {
/* 143 */     if (atts instanceof EncodingAlgorithmAttributes) {
/* 144 */       EncodingAlgorithmAttributes eAtts = (EncodingAlgorithmAttributes)atts;
/*     */ 
/*     */       
/* 147 */       for (int i = 0; i < eAtts.getLength(); i++) {
/* 148 */         String uri = atts.getURI(i);
/* 149 */         if (encodeAttribute(uri, atts.getQName(i), atts.getLocalName(i))) {
/* 150 */           Object data = eAtts.getAlgorithmData(i);
/*     */           
/* 152 */           if (data == null) {
/* 153 */             String value = eAtts.getValue(i);
/* 154 */             boolean addToTable = isAttributeValueLengthMatchesLimit(value.length());
/* 155 */             boolean mustToBeAddedToTable = eAtts.getToIndex(i);
/* 156 */             String alphabet = eAtts.getAlpababet(i);
/* 157 */             if (alphabet == null) {
/* 158 */               if (uri == "http://www.w3.org/2001/XMLSchema-instance" || uri.equals("http://www.w3.org/2001/XMLSchema-instance"))
/*     */               {
/* 160 */                 value = convertQName(value);
/*     */               }
/* 162 */               encodeNonIdentifyingStringOnFirstBit(value, this._v.attributeValue, addToTable, mustToBeAddedToTable);
/* 163 */             } else if (alphabet == "0123456789-:TZ ") {
/* 164 */               encodeDateTimeNonIdentifyingStringOnFirstBit(value, addToTable, mustToBeAddedToTable);
/*     */             }
/* 166 */             else if (alphabet == "0123456789-+.E ") {
/* 167 */               encodeNumericNonIdentifyingStringOnFirstBit(value, addToTable, mustToBeAddedToTable);
/*     */             } else {
/*     */               
/* 170 */               encodeNonIdentifyingStringOnFirstBit(value, this._v.attributeValue, addToTable, mustToBeAddedToTable);
/*     */             } 
/*     */           } else {
/* 173 */             encodeNonIdentifyingStringOnFirstBit(eAtts.getAlgorithmURI(i), eAtts.getAlgorithmIndex(i), data);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 179 */       for (int i = 0; i < atts.getLength(); i++) {
/* 180 */         String uri = atts.getURI(i);
/* 181 */         if (encodeAttribute(atts.getURI(i), atts.getQName(i), atts.getLocalName(i))) {
/* 182 */           String value = atts.getValue(i);
/* 183 */           boolean addToTable = isAttributeValueLengthMatchesLimit(value.length());
/*     */           
/* 185 */           if (uri == "http://www.w3.org/2001/XMLSchema-instance" || uri.equals("http://www.w3.org/2001/XMLSchema-instance"))
/*     */           {
/* 187 */             value = convertQName(value);
/*     */           }
/* 189 */           encodeNonIdentifyingStringOnFirstBit(value, this._v.attributeValue, addToTable, false);
/*     */         } 
/*     */       } 
/*     */     } 
/* 193 */     this._b = 240;
/* 194 */     this._terminate = true;
/*     */   }
/*     */   
/*     */   private String convertQName(String qName) {
/* 198 */     int i = qName.indexOf(':');
/* 199 */     String prefix = "";
/* 200 */     String localName = qName;
/* 201 */     if (i != -1) {
/* 202 */       prefix = qName.substring(0, i);
/* 203 */       localName = qName.substring(i + 1);
/*     */     } 
/*     */     
/* 206 */     String p = (String)this._prefixToPrefixMapping.get(prefix);
/* 207 */     if (p != null) {
/* 208 */       if (p.length() == 0) {
/* 209 */         return localName;
/*     */       }
/* 211 */       return p + ":" + localName;
/*     */     } 
/* 213 */     return qName;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final boolean encodeAttribute(String namespaceURI, String qName, String localName) throws IOException {
/* 218 */     LocalNameQualifiedNamesMap.Entry entry = this._v.attributeName.obtainEntry(localName);
/* 219 */     if (entry._valueIndex > 0) {
/* 220 */       if (encodeAttributeMapEntry(entry, namespaceURI)) return true;
/*     */       
/* 222 */       if (this._v.attributeName.isQNameFromReadOnlyMap(entry._value[0])) {
/* 223 */         entry = this._v.attributeName.obtainDynamicEntry(localName);
/* 224 */         if (entry._valueIndex > 0 && 
/* 225 */           encodeAttributeMapEntry(entry, namespaceURI)) return true;
/*     */       
/*     */       } 
/*     */     } 
/*     */     
/* 230 */     return encodeLiteralAttributeQualifiedNameOnSecondBit(namespaceURI, getPrefix(namespaceURI), localName, entry);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean encodeAttributeMapEntry(LocalNameQualifiedNamesMap.Entry entry, String namespaceURI) throws IOException {
/* 235 */     QualifiedName[] names = entry._value;
/* 236 */     for (int i = 0; i < entry._valueIndex; i++) {
/* 237 */       if (namespaceURI == (names[i]).namespaceName || namespaceURI.equals((names[i]).namespaceName)) {
/* 238 */         encodeNonZeroIntegerOnSecondBitFirstBitZero((names[i]).index);
/* 239 */         return true;
/*     */       } 
/*     */     } 
/* 242 */     return false;
/*     */   }
/*     */   
/*     */   protected final String getPrefix(String namespaceURI) {
/* 246 */     if (this._lastCheckedNamespace == namespaceURI) return this._lastCheckedPrefix;
/*     */     
/* 248 */     this._lastCheckedNamespace = namespaceURI;
/* 249 */     return this._lastCheckedPrefix = (String)this._namespaceToPrefixMapping.get(namespaceURI);
/*     */   }
/*     */   
/*     */   protected final void putPrefix(String namespaceURI, String prefix) {
/* 253 */     this._namespaceToPrefixMapping.put(namespaceURI, prefix);
/*     */     
/* 255 */     this._lastCheckedNamespace = namespaceURI;
/* 256 */     this._lastCheckedPrefix = prefix;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\sax\SAXDocumentSerializerWithPrefixMapping.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */