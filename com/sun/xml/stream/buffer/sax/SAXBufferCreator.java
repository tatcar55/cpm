/*     */ package com.sun.xml.stream.buffer.sax;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.AbstractCreator;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.DTDHandler;
/*     */ import org.xml.sax.EntityResolver;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.InputSource;
/*     */ import org.xml.sax.Locator;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
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
/*     */ public class SAXBufferCreator
/*     */   extends AbstractCreator
/*     */   implements EntityResolver, DTDHandler, ContentHandler, ErrorHandler, LexicalHandler
/*     */ {
/*     */   protected String[] _namespaceAttributes;
/*     */   protected int _namespaceAttributesPtr;
/*  72 */   private int depth = 0;
/*     */   
/*     */   public SAXBufferCreator() {
/*  75 */     this._namespaceAttributes = new String[32];
/*     */   }
/*     */   
/*     */   public SAXBufferCreator(MutableXMLStreamBuffer buffer) {
/*  79 */     this();
/*  80 */     setBuffer(buffer);
/*     */   }
/*     */   
/*     */   public MutableXMLStreamBuffer create(XMLReader reader, InputStream in) throws IOException, SAXException {
/*  84 */     return create(reader, in, (String)null);
/*     */   }
/*     */   
/*     */   public MutableXMLStreamBuffer create(XMLReader reader, InputStream in, String systemId) throws IOException, SAXException {
/*  88 */     if (this._buffer == null) {
/*  89 */       createBuffer();
/*     */     }
/*  91 */     this._buffer.setSystemId(systemId);
/*  92 */     reader.setContentHandler(this);
/*  93 */     reader.setProperty("http://xml.org/sax/properties/lexical-handler", this);
/*     */     
/*     */     try {
/*  96 */       setHasInternedStrings(reader.getFeature("http://xml.org/sax/features/string-interning"));
/*  97 */     } catch (SAXException e) {}
/*     */ 
/*     */ 
/*     */     
/* 101 */     if (systemId != null) {
/* 102 */       InputSource s = new InputSource(systemId);
/* 103 */       s.setByteStream(in);
/* 104 */       reader.parse(s);
/*     */     } else {
/* 106 */       reader.parse(new InputSource(in));
/*     */     } 
/*     */     
/* 109 */     return getXMLStreamBuffer();
/*     */   }
/*     */   
/*     */   public void reset() {
/* 113 */     this._buffer = null;
/* 114 */     this._namespaceAttributesPtr = 0;
/* 115 */     this.depth = 0;
/*     */   }
/*     */   
/*     */   public void startDocument() throws SAXException {
/* 119 */     storeStructure(16);
/*     */   }
/*     */   
/*     */   public void endDocument() throws SAXException {
/* 123 */     storeStructure(144);
/*     */   }
/*     */   
/*     */   public void startPrefixMapping(String prefix, String uri) throws SAXException {
/* 127 */     cacheNamespaceAttribute(prefix, uri);
/*     */   }
/*     */   
/*     */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/* 131 */     storeQualifiedName(32, uri, localName, qName);
/*     */ 
/*     */ 
/*     */     
/* 135 */     if (this._namespaceAttributesPtr > 0) {
/* 136 */       storeNamespaceAttributes();
/*     */     }
/*     */ 
/*     */     
/* 140 */     if (attributes.getLength() > 0) {
/* 141 */       storeAttributes(attributes);
/*     */     }
/* 143 */     this.depth++;
/*     */   }
/*     */   
/*     */   public void endElement(String uri, String localName, String qName) throws SAXException {
/* 147 */     storeStructure(144);
/* 148 */     if (--this.depth == 0)
/* 149 */       increaseTreeCount(); 
/*     */   }
/*     */   
/*     */   public void characters(char[] ch, int start, int length) throws SAXException {
/* 153 */     storeContentCharacters(80, ch, start, length);
/*     */   }
/*     */   
/*     */   public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
/* 157 */     characters(ch, start, length);
/*     */   }
/*     */   
/*     */   public void processingInstruction(String target, String data) throws SAXException {
/* 161 */     storeStructure(112);
/* 162 */     storeStructureString(target);
/* 163 */     storeStructureString(data);
/*     */   }
/*     */   
/*     */   public void comment(char[] ch, int start, int length) throws SAXException {
/* 167 */     storeContentCharacters(96, ch, start, length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void cacheNamespaceAttribute(String prefix, String uri) {
/* 173 */     this._namespaceAttributes[this._namespaceAttributesPtr++] = prefix;
/* 174 */     this._namespaceAttributes[this._namespaceAttributesPtr++] = uri;
/*     */     
/* 176 */     if (this._namespaceAttributesPtr == this._namespaceAttributes.length) {
/* 177 */       String[] namespaceAttributes = new String[this._namespaceAttributesPtr * 2];
/* 178 */       System.arraycopy(this._namespaceAttributes, 0, namespaceAttributes, 0, this._namespaceAttributesPtr);
/* 179 */       this._namespaceAttributes = namespaceAttributes;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void storeNamespaceAttributes() {
/* 184 */     for (int i = 0; i < this._namespaceAttributesPtr; i += 2) {
/* 185 */       int item = 64;
/* 186 */       if (this._namespaceAttributes[i].length() > 0) {
/* 187 */         item |= 0x1;
/* 188 */         storeStructureString(this._namespaceAttributes[i]);
/*     */       } 
/* 190 */       if (this._namespaceAttributes[i + 1].length() > 0) {
/* 191 */         item |= 0x2;
/* 192 */         storeStructureString(this._namespaceAttributes[i + 1]);
/*     */       } 
/* 194 */       storeStructure(item);
/*     */     } 
/* 196 */     this._namespaceAttributesPtr = 0;
/*     */   }
/*     */   
/*     */   private void storeAttributes(Attributes attributes) {
/* 200 */     for (int i = 0; i < attributes.getLength(); i++) {
/*     */ 
/*     */       
/* 203 */       if (!attributes.getQName(i).startsWith("xmlns")) {
/*     */         
/* 205 */         storeQualifiedName(48, attributes.getURI(i), attributes.getLocalName(i), attributes.getQName(i));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 210 */         storeStructureString(attributes.getType(i));
/* 211 */         storeContentString(attributes.getValue(i));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void storeQualifiedName(int item, String uri, String localName, String qName) {
/* 216 */     if (uri.length() > 0) {
/* 217 */       item |= 0x2;
/* 218 */       storeStructureString(uri);
/*     */     } 
/*     */     
/* 221 */     storeStructureString(localName);
/*     */     
/* 223 */     if (qName.indexOf(':') >= 0) {
/* 224 */       item |= 0x4;
/* 225 */       storeStructureString(qName);
/*     */     } 
/*     */     
/* 228 */     storeStructure(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
/* 239 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void notationDecl(String name, String publicId, String systemId) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void setDocumentLocator(Locator locator) {}
/*     */ 
/*     */   
/*     */   public void endPrefixMapping(String prefix) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void skippedEntity(String name) throws SAXException {}
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
/*     */   public void startCDATA() throws SAXException {}
/*     */ 
/*     */   
/*     */   public void endCDATA() throws SAXException {}
/*     */ 
/*     */   
/*     */   public void warning(SAXParseException e) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void error(SAXParseException e) throws SAXException {}
/*     */ 
/*     */   
/*     */   public void fatalError(SAXParseException e) throws SAXException {
/* 283 */     throw e;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\sax\SAXBufferCreator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */