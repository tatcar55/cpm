/*     */ package com.sun.xml.stream.buffer;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.sax.SAXBufferCreator;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferCreator;
/*     */ import com.sun.xml.stream.buffer.stax.StreamWriterBufferCreator;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.XMLReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MutableXMLStreamBuffer
/*     */   extends XMLStreamBuffer
/*     */ {
/*     */   public static final int DEFAULT_ARRAY_SIZE = 512;
/*     */   
/*     */   public MutableXMLStreamBuffer() {
/*  87 */     this(512);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSystemId(String systemId) {
/*  95 */     this.systemId = systemId;
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
/*     */   public MutableXMLStreamBuffer(int size) {
/* 108 */     this._structure = (FragmentedArray)new FragmentedArray<byte>(new byte[size]);
/* 109 */     this._structureStrings = (FragmentedArray)new FragmentedArray<String>(new String[size]);
/* 110 */     this._contentCharactersBuffer = (FragmentedArray)new FragmentedArray<char>(new char[4096]);
/* 111 */     this._contentObjects = new FragmentedArray(new Object[size]);
/*     */ 
/*     */ 
/*     */     
/* 115 */     ((byte[])this._structure.getArray())[0] = -112;
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
/*     */   public void createFromXMLStreamReader(XMLStreamReader reader) throws XMLStreamException {
/* 132 */     reset();
/* 133 */     StreamReaderBufferCreator c = new StreamReaderBufferCreator(this);
/* 134 */     c.create(reader);
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
/*     */   public XMLStreamWriter createFromXMLStreamWriter() {
/* 148 */     reset();
/* 149 */     return (XMLStreamWriter)new StreamWriterBufferCreator(this);
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
/*     */   public SAXBufferCreator createFromSAXBufferCreator() {
/* 165 */     reset();
/* 166 */     SAXBufferCreator c = new SAXBufferCreator();
/* 167 */     c.setBuffer(this);
/* 168 */     return c;
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
/*     */   public void createFromXMLReader(XMLReader reader, InputStream in) throws SAXException, IOException {
/* 187 */     createFromXMLReader(reader, in, (String)null);
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
/*     */   public void createFromXMLReader(XMLReader reader, InputStream in, String systemId) throws SAXException, IOException {
/* 208 */     reset();
/* 209 */     SAXBufferCreator c = new SAXBufferCreator(this);
/*     */     
/* 211 */     reader.setContentHandler((ContentHandler)c);
/* 212 */     reader.setDTDHandler((DTDHandler)c);
/* 213 */     reader.setProperty("http://xml.org/sax/properties/lexical-handler", c);
/*     */     
/* 215 */     c.create(reader, in, systemId);
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
/*     */   public void reset() {
/* 230 */     this._structurePtr = this._structureStringsPtr = this._contentCharactersBufferPtr = this._contentObjectsPtr = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     ((byte[])this._structure.getArray())[0] = -112;
/*     */ 
/*     */     
/* 240 */     this._contentObjects.setNext(null);
/* 241 */     Object[] o = this._contentObjects.getArray();
/* 242 */     for (int i = 0; i < o.length && 
/* 243 */       o[i] != null; i++) {
/* 244 */       o[i] = null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 250 */     this.treeCount = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setHasInternedStrings(boolean hasInternedStrings) {
/* 260 */     this._hasInternedStrings = hasInternedStrings;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\MutableXMLStreamBuffer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */