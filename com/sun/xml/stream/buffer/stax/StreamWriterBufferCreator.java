/*     */ package com.sun.xml.stream.buffer.stax;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.jvnet.staxex.Base64Data;
/*     */ import org.jvnet.staxex.NamespaceContextEx;
/*     */ import org.jvnet.staxex.XMLStreamWriterEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamWriterBufferCreator
/*     */   extends StreamBufferCreator
/*     */   implements XMLStreamWriterEx
/*     */ {
/*  64 */   private final NamespaceContexHelper namespaceContext = new NamespaceContexHelper();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private int depth = 0;
/*     */   
/*     */   public StreamWriterBufferCreator() {
/*  74 */     setXMLStreamBuffer(new MutableXMLStreamBuffer());
/*     */   }
/*     */   
/*     */   public StreamWriterBufferCreator(MutableXMLStreamBuffer buffer) {
/*  78 */     setXMLStreamBuffer(buffer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String str) throws IllegalArgumentException {
/*  84 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws XMLStreamException {}
/*     */ 
/*     */   
/*     */   public void flush() throws XMLStreamException {}
/*     */ 
/*     */   
/*     */   public NamespaceContextEx getNamespaceContext() {
/*  95 */     return this.namespaceContext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext namespaceContext) throws XMLStreamException {
/* 102 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   public void setDefaultNamespace(String namespaceURI) throws XMLStreamException {
/* 106 */     setPrefix("", namespaceURI);
/*     */   }
/*     */   
/*     */   public void setPrefix(String prefix, String namespaceURI) throws XMLStreamException {
/* 110 */     this.namespaceContext.declareNamespace(prefix, namespaceURI);
/*     */   }
/*     */   
/*     */   public String getPrefix(String namespaceURI) throws XMLStreamException {
/* 114 */     return this.namespaceContext.getPrefix(namespaceURI);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {
/* 119 */     writeStartDocument("", "");
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String version) throws XMLStreamException {
/* 123 */     writeStartDocument("", "");
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
/* 127 */     this.namespaceContext.resetContexts();
/*     */     
/* 129 */     storeStructure(16);
/*     */   }
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {
/* 133 */     storeStructure(144);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String localName) throws XMLStreamException {
/* 137 */     this.namespaceContext.pushContext();
/* 138 */     this.depth++;
/*     */     
/* 140 */     String defaultNamespaceURI = this.namespaceContext.getNamespaceURI("");
/*     */     
/* 142 */     if (defaultNamespaceURI == null) {
/* 143 */       storeQualifiedName(32, (String)null, (String)null, localName);
/*     */     } else {
/* 145 */       storeQualifiedName(32, (String)null, defaultNamespaceURI, localName);
/*     */     } 
/*     */   }
/*     */   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 149 */     this.namespaceContext.pushContext();
/* 150 */     this.depth++;
/*     */     
/* 152 */     String prefix = this.namespaceContext.getPrefix(namespaceURI);
/* 153 */     if (prefix == null) {
/* 154 */       throw new XMLStreamException();
/*     */     }
/*     */     
/* 157 */     this.namespaceContext.pushContext();
/* 158 */     storeQualifiedName(32, prefix, namespaceURI, localName);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 162 */     this.namespaceContext.pushContext();
/* 163 */     this.depth++;
/*     */     
/* 165 */     storeQualifiedName(32, prefix, namespaceURI, localName);
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String localName) throws XMLStreamException {
/* 169 */     writeStartElement(localName);
/* 170 */     writeEndElement();
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
/* 174 */     writeStartElement(namespaceURI, localName);
/* 175 */     writeEndElement();
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 179 */     writeStartElement(prefix, localName, namespaceURI);
/* 180 */     writeEndElement();
/*     */   }
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/* 184 */     this.namespaceContext.popContext();
/*     */     
/* 186 */     storeStructure(144);
/* 187 */     if (--this.depth == 0)
/* 188 */       increaseTreeCount(); 
/*     */   }
/*     */   
/*     */   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
/* 192 */     storeNamespaceAttribute((String)null, namespaceURI);
/*     */   }
/*     */   
/*     */   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
/* 196 */     if ("xmlns".equals(prefix))
/* 197 */       prefix = null; 
/* 198 */     storeNamespaceAttribute(prefix, namespaceURI);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeAttribute(String localName, String value) throws XMLStreamException {
/* 203 */     storeAttribute((String)null, (String)null, localName, "CDATA", value);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
/* 207 */     String prefix = this.namespaceContext.getPrefix(namespaceURI);
/* 208 */     if (prefix == null)
/*     */     {
/* 210 */       throw new XMLStreamException();
/*     */     }
/*     */     
/* 213 */     writeAttribute(prefix, namespaceURI, localName, value);
/*     */   }
/*     */   
/*     */   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
/* 217 */     storeAttribute(prefix, namespaceURI, localName, "CDATA", value);
/*     */   }
/*     */   
/*     */   public void writeCData(String data) throws XMLStreamException {
/* 221 */     storeStructure(88);
/* 222 */     storeContentString(data);
/*     */   }
/*     */   
/*     */   public void writeCharacters(String charData) throws XMLStreamException {
/* 226 */     storeStructure(88);
/* 227 */     storeContentString(charData);
/*     */   }
/*     */   
/*     */   public void writeCharacters(char[] buf, int start, int len) throws XMLStreamException {
/* 231 */     storeContentCharacters(80, buf, start, len);
/*     */   }
/*     */   
/*     */   public void writeComment(String str) throws XMLStreamException {
/* 235 */     storeStructure(104);
/* 236 */     storeContentString(str);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDTD(String str) throws XMLStreamException {}
/*     */ 
/*     */   
/*     */   public void writeEntityRef(String str) throws XMLStreamException {
/* 244 */     storeStructure(128);
/* 245 */     storeContentString(str);
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String target) throws XMLStreamException {
/* 249 */     writeProcessingInstruction(target, "");
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
/* 253 */     storeProcessingInstruction(target, data);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePCDATA(CharSequence charSequence) throws XMLStreamException {
/* 259 */     if (charSequence instanceof Base64Data) {
/* 260 */       storeStructure(92);
/* 261 */       storeContentObject(((Base64Data)charSequence).clone());
/*     */     } else {
/* 263 */       writeCharacters(charSequence.toString());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeBinary(byte[] bytes, int offset, int length, String endpointURL) throws XMLStreamException {
/* 268 */     Base64Data d = new Base64Data();
/* 269 */     byte[] b = new byte[length];
/* 270 */     System.arraycopy(bytes, offset, b, 0, length);
/* 271 */     d.set(b, length, null, true);
/* 272 */     storeStructure(92);
/* 273 */     storeContentObject(d);
/*     */   }
/*     */   
/*     */   public void writeBinary(DataHandler dataHandler) throws XMLStreamException {
/* 277 */     Base64Data d = new Base64Data();
/* 278 */     d.set(dataHandler);
/* 279 */     storeStructure(92);
/* 280 */     storeContentObject(d);
/*     */   }
/*     */ 
/*     */   
/*     */   public OutputStream writeBinary(String endpointURL) throws XMLStreamException {
/* 285 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\stax\StreamWriterBufferCreator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */