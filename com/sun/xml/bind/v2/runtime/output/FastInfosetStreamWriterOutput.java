/*     */ package com.sun.xml.bind.v2.runtime.output;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.JAXBContextImpl;
/*     */ import com.sun.xml.bind.v2.runtime.Name;
/*     */ import com.sun.xml.bind.v2.runtime.XMLSerializer;
/*     */ import com.sun.xml.bind.v2.runtime.unmarshaller.Base64Data;
/*     */ import com.sun.xml.fastinfoset.stax.StAXDocumentSerializer;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.fastinfoset.VocabularyApplicationData;
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
/*     */ public final class FastInfosetStreamWriterOutput
/*     */   extends XMLStreamWriterOutput
/*     */ {
/*     */   private final StAXDocumentSerializer fiout;
/*     */   private final Encoded[] localNames;
/*     */   private final TablesPerJAXBContext tables;
/*     */   
/*     */   static final class TablesPerJAXBContext
/*     */   {
/*     */     final int[] elementIndexes;
/*     */     final int[] elementIndexPrefixes;
/*     */     final int[] attributeIndexes;
/*     */     final int[] localNameIndexes;
/*     */     int indexOffset;
/*     */     int maxIndex;
/*     */     boolean requiresClear;
/*     */     
/*     */     TablesPerJAXBContext(JAXBContextImpl context, int initialIndexOffset) {
/* 123 */       this.elementIndexes = new int[context.getNumberOfElementNames()];
/* 124 */       this.elementIndexPrefixes = new int[context.getNumberOfElementNames()];
/* 125 */       this.attributeIndexes = new int[context.getNumberOfAttributeNames()];
/* 126 */       this.localNameIndexes = new int[context.getNumberOfLocalNames()];
/*     */       
/* 128 */       this.indexOffset = 1;
/* 129 */       this.maxIndex = initialIndexOffset + this.elementIndexes.length + this.attributeIndexes.length;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void requireClearTables() {
/* 136 */       this.requiresClear = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clearOrResetTables(int intialIndexOffset) {
/* 146 */       if (this.requiresClear) {
/* 147 */         this.requiresClear = false;
/*     */ 
/*     */         
/* 150 */         this.indexOffset += this.maxIndex;
/*     */         
/* 152 */         this.maxIndex = intialIndexOffset + this.elementIndexes.length + this.attributeIndexes.length;
/*     */ 
/*     */         
/* 155 */         if (this.indexOffset + this.maxIndex < 0) {
/* 156 */           clearAll();
/*     */         }
/*     */       } else {
/*     */         
/* 160 */         this.maxIndex = intialIndexOffset + this.elementIndexes.length + this.attributeIndexes.length;
/*     */ 
/*     */         
/* 163 */         if (this.indexOffset + this.maxIndex < 0) {
/* 164 */           resetAll();
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     private void clearAll() {
/* 170 */       clear(this.elementIndexes);
/* 171 */       clear(this.attributeIndexes);
/* 172 */       clear(this.localNameIndexes);
/* 173 */       this.indexOffset = 1;
/*     */     }
/*     */     
/*     */     private void clear(int[] array) {
/* 177 */       for (int i = 0; i < array.length; i++) {
/* 178 */         array[i] = 0;
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void incrementMaxIndexValue() {
/* 189 */       this.maxIndex++;
/*     */ 
/*     */       
/* 192 */       if (this.indexOffset + this.maxIndex < 0) {
/* 193 */         resetAll();
/*     */       }
/*     */     }
/*     */     
/*     */     private void resetAll() {
/* 198 */       clear(this.elementIndexes);
/* 199 */       clear(this.attributeIndexes);
/* 200 */       clear(this.localNameIndexes);
/* 201 */       this.indexOffset = 1;
/*     */     }
/*     */     
/*     */     private void reset(int[] array) {
/* 205 */       for (int i = 0; i < array.length; i++) {
/* 206 */         if (array[i] > this.indexOffset) {
/* 207 */           array[i] = array[i] - this.indexOffset + 1;
/*     */         } else {
/* 209 */           array[i] = 0;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class AppData
/*     */     implements VocabularyApplicationData
/*     */   {
/* 223 */     final Map<JAXBContext, FastInfosetStreamWriterOutput.TablesPerJAXBContext> contexts = new WeakHashMap<JAXBContext, FastInfosetStreamWriterOutput.TablesPerJAXBContext>();
/*     */     
/* 225 */     final Collection<FastInfosetStreamWriterOutput.TablesPerJAXBContext> collectionOfContexts = this.contexts.values();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {
/* 231 */       for (FastInfosetStreamWriterOutput.TablesPerJAXBContext c : this.collectionOfContexts) {
/* 232 */         c.requireClearTables();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public FastInfosetStreamWriterOutput(StAXDocumentSerializer out, JAXBContextImpl context) {
/* 238 */     super((XMLStreamWriter)out);
/*     */     
/* 240 */     this.fiout = out;
/* 241 */     this.localNames = context.getUTF8NameTable();
/*     */     
/* 243 */     VocabularyApplicationData vocabAppData = this.fiout.getVocabularyApplicationData();
/* 244 */     AppData appData = null;
/* 245 */     if (vocabAppData == null || !(vocabAppData instanceof AppData)) {
/* 246 */       appData = new AppData();
/* 247 */       this.fiout.setVocabularyApplicationData(appData);
/*     */     } else {
/* 249 */       appData = (AppData)vocabAppData;
/*     */     } 
/*     */     
/* 252 */     TablesPerJAXBContext tablesPerContext = appData.contexts.get(context);
/* 253 */     if (tablesPerContext != null) {
/* 254 */       this.tables = tablesPerContext;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 259 */       this.tables.clearOrResetTables(out.getLocalNameIndex());
/*     */     } else {
/* 261 */       this.tables = new TablesPerJAXBContext(context, out.getLocalNameIndex());
/* 262 */       appData.contexts.put(context, this.tables);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startDocument(XMLSerializer serializer, boolean fragment, int[] nsUriIndex2prefixIndex, NamespaceContextImpl nsContext) throws IOException, SAXException, XMLStreamException {
/* 270 */     super.startDocument(serializer, fragment, nsUriIndex2prefixIndex, nsContext);
/*     */     
/* 272 */     if (fragment) {
/* 273 */       this.fiout.initiateLowLevelWriting();
/*     */     }
/*     */   }
/*     */   
/*     */   public void endDocument(boolean fragment) throws IOException, SAXException, XMLStreamException {
/* 278 */     super.endDocument(fragment);
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginStartTag(Name name) throws IOException {
/* 283 */     this.fiout.writeLowLevelTerminationAndMark();
/*     */     
/* 285 */     if (this.nsContext.getCurrent().count() == 0) {
/* 286 */       int qNameIndex = this.tables.elementIndexes[name.qNameIndex] - this.tables.indexOffset;
/* 287 */       int prefixIndex = this.nsUriIndex2prefixIndex[name.nsUriIndex];
/*     */       
/* 289 */       if (qNameIndex >= 0 && this.tables.elementIndexPrefixes[name.qNameIndex] == prefixIndex) {
/*     */         
/* 291 */         this.fiout.writeLowLevelStartElementIndexed(0, qNameIndex);
/*     */       } else {
/* 293 */         this.tables.elementIndexes[name.qNameIndex] = this.fiout.getNextElementIndex() + this.tables.indexOffset;
/* 294 */         this.tables.elementIndexPrefixes[name.qNameIndex] = prefixIndex;
/* 295 */         writeLiteral(60, name, this.nsContext.getPrefix(prefixIndex), this.nsContext.getNamespaceURI(prefixIndex));
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 301 */       beginStartTagWithNamespaces(name);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void beginStartTagWithNamespaces(Name name) throws IOException {
/* 306 */     NamespaceContextImpl.Element nse = this.nsContext.getCurrent();
/*     */     
/* 308 */     this.fiout.writeLowLevelStartNamespaces();
/* 309 */     for (int i = nse.count() - 1; i >= 0; i--) {
/* 310 */       String uri = nse.getNsUri(i);
/* 311 */       if (uri.length() != 0 || nse.getBase() != 1)
/*     */       {
/* 313 */         this.fiout.writeLowLevelNamespace(nse.getPrefix(i), uri); } 
/*     */     } 
/* 315 */     this.fiout.writeLowLevelEndNamespaces();
/*     */     
/* 317 */     int qNameIndex = this.tables.elementIndexes[name.qNameIndex] - this.tables.indexOffset;
/* 318 */     int prefixIndex = this.nsUriIndex2prefixIndex[name.nsUriIndex];
/*     */     
/* 320 */     if (qNameIndex >= 0 && this.tables.elementIndexPrefixes[name.qNameIndex] == prefixIndex) {
/*     */       
/* 322 */       this.fiout.writeLowLevelStartElementIndexed(0, qNameIndex);
/*     */     } else {
/* 324 */       this.tables.elementIndexes[name.qNameIndex] = this.fiout.getNextElementIndex() + this.tables.indexOffset;
/* 325 */       this.tables.elementIndexPrefixes[name.qNameIndex] = prefixIndex;
/* 326 */       writeLiteral(60, name, this.nsContext.getPrefix(prefixIndex), this.nsContext.getNamespaceURI(prefixIndex));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attribute(Name name, String value) throws IOException {
/* 335 */     this.fiout.writeLowLevelStartAttributes();
/*     */     
/* 337 */     int qNameIndex = this.tables.attributeIndexes[name.qNameIndex] - this.tables.indexOffset;
/* 338 */     if (qNameIndex >= 0) {
/* 339 */       this.fiout.writeLowLevelAttributeIndexed(qNameIndex);
/*     */     } else {
/* 341 */       this.tables.attributeIndexes[name.qNameIndex] = this.fiout.getNextAttributeIndex() + this.tables.indexOffset;
/*     */       
/* 343 */       int namespaceURIId = name.nsUriIndex;
/* 344 */       if (namespaceURIId == -1) {
/* 345 */         writeLiteral(120, name, "", "");
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 350 */         int prefix = this.nsUriIndex2prefixIndex[namespaceURIId];
/* 351 */         writeLiteral(120, name, this.nsContext.getPrefix(prefix), this.nsContext.getNamespaceURI(prefix));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 358 */     this.fiout.writeLowLevelAttributeValue(value);
/*     */   }
/*     */   
/*     */   private void writeLiteral(int type, Name name, String prefix, String namespaceURI) throws IOException {
/* 362 */     int localNameIndex = this.tables.localNameIndexes[name.localNameIndex] - this.tables.indexOffset;
/*     */     
/* 364 */     if (localNameIndex < 0) {
/* 365 */       this.tables.localNameIndexes[name.localNameIndex] = this.fiout.getNextLocalNameIndex() + this.tables.indexOffset;
/*     */       
/* 367 */       this.fiout.writeLowLevelStartNameLiteral(type, prefix, (this.localNames[name.localNameIndex]).buf, namespaceURI);
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 373 */       this.fiout.writeLowLevelStartNameLiteral(type, prefix, localNameIndex, namespaceURI);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endStartTag() throws IOException {
/* 383 */     this.fiout.writeLowLevelEndStartElement();
/*     */   }
/*     */ 
/*     */   
/*     */   public void endTag(Name name) throws IOException {
/* 388 */     this.fiout.writeLowLevelEndElement();
/*     */   }
/*     */ 
/*     */   
/*     */   public void endTag(int prefix, String localName) throws IOException {
/* 393 */     this.fiout.writeLowLevelEndElement();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void text(Pcdata value, boolean needsSeparatingWhitespace) throws IOException {
/* 399 */     if (needsSeparatingWhitespace) {
/* 400 */       this.fiout.writeLowLevelText(" ");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 405 */     if (!(value instanceof Base64Data)) {
/* 406 */       int len = value.length();
/* 407 */       if (len < this.buf.length) {
/* 408 */         value.writeTo(this.buf, 0);
/* 409 */         this.fiout.writeLowLevelText(this.buf, len);
/*     */       } else {
/* 411 */         this.fiout.writeLowLevelText(value.toString());
/*     */       } 
/*     */     } else {
/* 414 */       Base64Data dataValue = (Base64Data)value;
/*     */       
/* 416 */       this.fiout.writeLowLevelOctets(dataValue.get(), dataValue.getDataLen());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void text(String value, boolean needsSeparatingWhitespace) throws IOException {
/* 423 */     if (needsSeparatingWhitespace) {
/* 424 */       this.fiout.writeLowLevelText(" ");
/*     */     }
/* 426 */     this.fiout.writeLowLevelText(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void beginStartTag(int prefix, String localName) throws IOException {
/* 432 */     this.fiout.writeLowLevelTerminationAndMark();
/*     */     
/* 434 */     int type = 0;
/* 435 */     if (this.nsContext.getCurrent().count() > 0) {
/* 436 */       NamespaceContextImpl.Element nse = this.nsContext.getCurrent();
/*     */       
/* 438 */       this.fiout.writeLowLevelStartNamespaces();
/* 439 */       for (int i = nse.count() - 1; i >= 0; i--) {
/* 440 */         String uri = nse.getNsUri(i);
/* 441 */         if (uri.length() != 0 || nse.getBase() != 1)
/*     */         {
/* 443 */           this.fiout.writeLowLevelNamespace(nse.getPrefix(i), uri); } 
/*     */       } 
/* 445 */       this.fiout.writeLowLevelEndNamespaces();
/*     */       
/* 447 */       type = 0;
/*     */     } 
/*     */     
/* 450 */     boolean isIndexed = this.fiout.writeLowLevelStartElement(type, this.nsContext.getPrefix(prefix), localName, this.nsContext.getNamespaceURI(prefix));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 456 */     if (!isIndexed)
/* 457 */       this.tables.incrementMaxIndexValue(); 
/*     */   }
/*     */   
/*     */   public void attribute(int prefix, String localName, String value) throws IOException {
/*     */     boolean isIndexed;
/* 462 */     this.fiout.writeLowLevelStartAttributes();
/*     */ 
/*     */     
/* 465 */     if (prefix == -1) {
/* 466 */       isIndexed = this.fiout.writeLowLevelAttribute("", "", localName);
/*     */     } else {
/* 468 */       isIndexed = this.fiout.writeLowLevelAttribute(this.nsContext.getPrefix(prefix), this.nsContext.getNamespaceURI(prefix), localName);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 473 */     if (!isIndexed) {
/* 474 */       this.tables.incrementMaxIndexValue();
/*     */     }
/* 476 */     this.fiout.writeLowLevelAttributeValue(value);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\output\FastInfosetStreamWriterOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */