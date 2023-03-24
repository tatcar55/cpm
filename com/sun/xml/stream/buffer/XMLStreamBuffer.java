/*     */ package com.sun.xml.stream.buffer;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.sax.SAXBufferProcessor;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferProcessor;
/*     */ import com.sun.xml.stream.buffer.stax.StreamWriterBufferProcessor;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMResult;
/*     */ import org.w3c.dom.Node;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.XMLReader;
/*     */ import org.xml.sax.ext.LexicalHandler;
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
/*     */ public abstract class XMLStreamBuffer
/*     */ {
/* 102 */   protected Map<String, String> _inscopeNamespaces = Collections.emptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _hasInternedStrings;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FragmentedArray<byte[]> _structure;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _structurePtr;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FragmentedArray<String[]> _structureStrings;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _structureStringsPtr;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FragmentedArray<char[]> _contentCharactersBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _contentCharactersBufferPtr;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FragmentedArray<Object[]> _contentObjects;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int _contentObjectsPtr;
/*     */ 
/*     */ 
/*     */   
/*     */   protected int treeCount;
/*     */ 
/*     */ 
/*     */   
/*     */   protected String systemId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isCreated() {
/* 160 */     return (((byte[])this._structure.getArray())[0] != 144);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isFragment() {
/* 171 */     return (isCreated() && (((byte[])this._structure.getArray())[this._structurePtr] & 0xF0) != 16);
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
/*     */   public final boolean isElementFragment() {
/* 184 */     return (isCreated() && (((byte[])this._structure.getArray())[this._structurePtr] & 0xF0) == 32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isForest() {
/* 193 */     return (isCreated() && this.treeCount > 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getSystemId() {
/* 201 */     return this.systemId;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Map<String, String> getInscopeNamespaces() {
/* 223 */     return this._inscopeNamespaces;
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
/*     */   public final boolean hasInternedStrings() {
/* 246 */     return this._hasInternedStrings;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final StreamReaderBufferProcessor readAsXMLStreamReader() throws XMLStreamException {
/* 256 */     return new StreamReaderBufferProcessor(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void writeToXMLStreamWriter(XMLStreamWriter writer, boolean writeAsFragment) throws XMLStreamException {
/* 274 */     StreamWriterBufferProcessor p = new StreamWriterBufferProcessor(this, writeAsFragment);
/* 275 */     p.process(writer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void writeToXMLStreamWriter(XMLStreamWriter writer) throws XMLStreamException {
/* 283 */     writeToXMLStreamWriter(writer, isFragment());
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
/*     */   public final SAXBufferProcessor readAsXMLReader() {
/* 295 */     return new SAXBufferProcessor(this, isFragment());
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
/*     */   public final SAXBufferProcessor readAsXMLReader(boolean produceFragmentEvent) {
/* 308 */     return new SAXBufferProcessor(this, produceFragmentEvent);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void writeTo(ContentHandler handler, boolean produceFragmentEvent) throws SAXException {
/* 329 */     SAXBufferProcessor p = readAsXMLReader(produceFragmentEvent);
/* 330 */     p.setContentHandler(handler);
/* 331 */     if (p instanceof LexicalHandler) {
/* 332 */       p.setLexicalHandler((LexicalHandler)handler);
/*     */     }
/* 334 */     if (p instanceof DTDHandler) {
/* 335 */       p.setDTDHandler((DTDHandler)handler);
/*     */     }
/* 337 */     if (p instanceof ErrorHandler) {
/* 338 */       p.setErrorHandler((ErrorHandler)handler);
/*     */     }
/* 340 */     p.process();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void writeTo(ContentHandler handler) throws SAXException {
/* 348 */     writeTo(handler, isFragment());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void writeTo(ContentHandler handler, ErrorHandler errorHandler, boolean produceFragmentEvent) throws SAXException {
/* 370 */     SAXBufferProcessor p = readAsXMLReader(produceFragmentEvent);
/* 371 */     p.setContentHandler(handler);
/* 372 */     if (p instanceof LexicalHandler) {
/* 373 */       p.setLexicalHandler((LexicalHandler)handler);
/*     */     }
/* 375 */     if (p instanceof DTDHandler) {
/* 376 */       p.setDTDHandler((DTDHandler)handler);
/*     */     }
/*     */     
/* 379 */     p.setErrorHandler(errorHandler);
/*     */     
/* 381 */     p.process();
/*     */   }
/*     */   
/*     */   public final void writeTo(ContentHandler handler, ErrorHandler errorHandler) throws SAXException {
/* 385 */     writeTo(handler, errorHandler, isFragment());
/*     */   }
/*     */   
/* 388 */   private static final TransformerFactory trnsformerFactory = TransformerFactory.newInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Node writeTo(Node n) throws XMLStreamBufferException {
/*     */     try {
/* 400 */       Transformer t = trnsformerFactory.newTransformer();
/* 401 */       t.transform(new XMLStreamBufferSource(this), new DOMResult(n));
/* 402 */       return n.getLastChild();
/* 403 */     } catch (TransformerException e) {
/* 404 */       throw new XMLStreamBufferException(e);
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
/*     */   public static XMLStreamBuffer createNewBufferFromXMLStreamReader(XMLStreamReader reader) throws XMLStreamException {
/* 418 */     MutableXMLStreamBuffer b = new MutableXMLStreamBuffer();
/* 419 */     b.createFromXMLStreamReader(reader);
/* 420 */     return b;
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
/*     */   
/*     */   public static XMLStreamBuffer createNewBufferFromXMLReader(XMLReader reader, InputStream in) throws SAXException, IOException {
/* 434 */     MutableXMLStreamBuffer b = new MutableXMLStreamBuffer();
/* 435 */     b.createFromXMLReader(reader, in);
/* 436 */     return b;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XMLStreamBuffer createNewBufferFromXMLReader(XMLReader reader, InputStream in, String systemId) throws SAXException, IOException {
/* 453 */     MutableXMLStreamBuffer b = new MutableXMLStreamBuffer();
/* 454 */     b.createFromXMLReader(reader, in, systemId);
/* 455 */     return b;
/*     */   }
/*     */   
/*     */   protected final FragmentedArray<byte[]> getStructure() {
/* 459 */     return this._structure;
/*     */   }
/*     */   
/*     */   protected final int getStructurePtr() {
/* 463 */     return this._structurePtr;
/*     */   }
/*     */   
/*     */   protected final FragmentedArray<String[]> getStructureStrings() {
/* 467 */     return this._structureStrings;
/*     */   }
/*     */   
/*     */   protected final int getStructureStringsPtr() {
/* 471 */     return this._structureStringsPtr;
/*     */   }
/*     */   
/*     */   protected final FragmentedArray<char[]> getContentCharactersBuffer() {
/* 475 */     return this._contentCharactersBuffer;
/*     */   }
/*     */   
/*     */   protected final int getContentCharactersBufferPtr() {
/* 479 */     return this._contentCharactersBufferPtr;
/*     */   }
/*     */   
/*     */   protected final FragmentedArray<Object[]> getContentObjects() {
/* 483 */     return this._contentObjects;
/*     */   }
/*     */   
/*     */   protected final int getContentObjectsPtr() {
/* 487 */     return this._contentObjectsPtr;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\XMLStreamBuffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */