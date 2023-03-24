/*     */ package com.sun.xml.wss.impl.c14n;
/*     */ 
/*     */ import com.sun.xml.wss.impl.misc.UnsyncByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.xml.sax.helpers.NamespaceSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXC14nCanonicalizerImpl
/*     */   extends BaseCanonicalizer
/*     */   implements XMLStreamWriter
/*     */ {
/*     */   boolean closeStartTag = false;
/*  75 */   NamespaceSupport nsContext = new NamespaceSupport();
/*  76 */   private NamespaceContext namespaceContext = null;
/*  77 */   BaseCanonicalizer.ElementName[] elementNames = new BaseCanonicalizer.ElementName[10];
/*     */   
/*  79 */   protected UnsyncByteArrayOutputStream elemBuffer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StAXC14nCanonicalizerImpl() {
/*  85 */     this._attrResult = new ArrayList(); int i;
/*  86 */     for (i = 0; i < 4; i++) {
/*  87 */       this._attrs.add(new StAXAttr());
/*     */     }
/*  89 */     for (i = 0; i < 10; i++) {
/*  90 */       this.elementNames[i] = new BaseCanonicalizer.ElementName();
/*     */     }
/*  92 */     this.elemBuffer = new UnsyncByteArrayOutputStream();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws XMLStreamException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() throws XMLStreamException {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 111 */     return this.namespaceContext;
/*     */   }
/*     */   
/*     */   public NamespaceSupport getNSContext() {
/* 115 */     return this.nsContext;
/*     */   }
/*     */   
/*     */   public String getPrefix(String namespaceURI) throws XMLStreamException {
/* 119 */     return this.nsContext.getPrefix(namespaceURI);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String str) throws IllegalArgumentException {
/* 125 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultNamespace(String str) throws XMLStreamException {
/* 130 */     this.nsContext.declarePrefix("", str);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext namespaceContext) throws XMLStreamException {
/* 135 */     this.namespaceContext = namespaceContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPrefix(String str, String str1) throws XMLStreamException {
/* 140 */     this.nsContext.declarePrefix(str, str1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeAttribute(String localName, String value) throws XMLStreamException {
/* 151 */     writeAttribute("", "", localName, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
/* 156 */     String prefix = "";
/* 157 */     prefix = this.nsContext.getPrefix(namespaceURI);
/* 158 */     writeAttribute(prefix, "", localName, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
/* 163 */     StAXAttr attr = getAttribute();
/* 164 */     attr.setLocalName(localName);
/* 165 */     attr.setValue(value);
/* 166 */     attr.setPrefix(prefix);
/* 167 */     attr.setUri(namespaceURI);
/* 168 */     this._attrResult.add(attr);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCData(String data) throws XMLStreamException {
/*     */     try {
/* 174 */       closeStartTag();
/* 175 */       outputTextToWriter(data, this._stream);
/* 176 */     } catch (IOException ex) {
/* 177 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCharacters(String charData) throws XMLStreamException {
/*     */     try {
/* 184 */       closeStartTag();
/* 185 */       outputTextToWriter(charData, this._stream);
/* 186 */     } catch (IOException ex) {
/* 187 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCharacters(char[] values, int param, int param2) throws XMLStreamException {
/*     */     try {
/* 194 */       closeStartTag();
/* 195 */       outputTextToWriter(values, param, param2, this._stream);
/* 196 */     } catch (IOException ex) {
/* 197 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeComment(String str) throws XMLStreamException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDTD(String str) throws XMLStreamException {
/* 208 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
/* 213 */     this._defURI = namespaceURI;
/* 214 */     writeNamespace("", namespaceURI);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String localName) throws XMLStreamException {
/* 220 */     writeEmptyElement("", localName, "");
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
/* 225 */     String prefix = this.nsContext.getPrefix(namespaceURI);
/* 226 */     writeEmptyElement(prefix, localName, namespaceURI);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 231 */     closeStartTag();
/*     */ 
/*     */     
/*     */     try {
/* 235 */       this._stream.write(60);
/* 236 */       if ((prefix != null && prefix.length() == 0) || prefix == null) {
/* 237 */         writeStringToUtf8(localName, this._stream);
/*     */       } else {
/* 239 */         writeStringToUtf8(prefix, this._stream);
/* 240 */         writeStringToUtf8(":", this._stream);
/* 241 */         writeStringToUtf8(localName, this._stream);
/*     */       } 
/*     */ 
/*     */       
/* 245 */       this._stream.write(62);
/* 246 */       this._stream.write(_END_TAG);
/* 247 */       if ((prefix != null && prefix.length() == 0) || prefix == null) {
/* 248 */         writeStringToUtf8(localName, this._stream);
/*     */       } else {
/* 250 */         writeStringToUtf8(prefix, this._stream);
/* 251 */         writeStringToUtf8(":", this._stream);
/* 252 */         writeStringToUtf8(localName, this._stream);
/*     */       } 
/*     */       
/* 255 */       this._stream.write(62);
/* 256 */     } catch (IOException ex) {
/* 257 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {
/* 264 */     while (this._depth > 0) {
/* 265 */       writeEndElement();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/* 272 */     closeStartTag();
/* 273 */     if (this._depth == 0) {
/*     */       return;
/*     */     }
/* 276 */     if (this._ncContextState[this._depth]) {
/* 277 */       this.nsContext.popContext();
/*     */     }
/*     */     try {
/* 280 */       this._stream.write(_END_TAG);
/*     */       
/* 282 */       BaseCanonicalizer.ElementName en = this.elementNames[--this._depth];
/* 283 */       this._stream.write(en.getUtf8Data().getBytes(), 0, en.getUtf8Data().getLength());
/* 284 */       en.getUtf8Data().reset();
/* 285 */       this._stream.write(62);
/* 286 */     } catch (IOException ex) {
/* 287 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEntityRef(String str) throws XMLStreamException {
/* 293 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
/* 298 */     String duri = this.nsContext.getURI(prefix);
/* 299 */     boolean add = false;
/* 300 */     if (duri == null || !duri.equals(namespaceURI)) {
/* 301 */       add = true;
/*     */     }
/* 303 */     if (add && !this._ncContextState[this._depth - 1]) {
/* 304 */       this.nsContext.pushContext();
/* 305 */       this._ncContextState[this._depth - 1] = true;
/*     */     } 
/* 307 */     if (add) {
/* 308 */       this.nsContext.declarePrefix(prefix, namespaceURI);
/* 309 */       AttributeNS attrNS = getAttributeNS();
/* 310 */       attrNS.setPrefix(prefix);
/* 311 */       attrNS.setUri(namespaceURI);
/* 312 */       this._nsResult.add(attrNS);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeProcessingInstruction(String target) throws XMLStreamException {
/*     */     try {
/* 319 */       outputPItoWriter(target, "", this._stream);
/* 320 */     } catch (IOException ex) {
/* 321 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
/*     */     try {
/* 328 */       outputPItoWriter(target, data, this._stream);
/* 329 */     } catch (IOException ex) {
/* 330 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartDocument(String version) throws XMLStreamException {}
/*     */ 
/*     */   
/*     */   public void writeStartDocument(String encoding, String version) throws XMLStreamException {}
/*     */ 
/*     */   
/*     */   public void writeStartElement(String localName) throws XMLStreamException {
/* 347 */     writeStartElement("", localName, "");
/*     */   }
/*     */   
/*     */   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 351 */     String prefix = this.nsContext.getPrefix(namespaceURI);
/* 352 */     writeStartElement(prefix, localName, namespaceURI);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 358 */     closeStartTag();
/* 359 */     this.elemBuffer.reset();
/* 360 */     UnsyncByteArrayOutputStream buffWriter = null;
/*     */     try {
/* 362 */       if (prefix != null && prefix.length() > 0) {
/* 363 */         buffWriter = this.elementNames[this._depth].getUtf8Data();
/* 364 */         writeStringToUtf8(prefix, (OutputStream)buffWriter);
/* 365 */         writeStringToUtf8(":", (OutputStream)buffWriter);
/* 366 */         writeStringToUtf8(localName, (OutputStream)buffWriter);
/* 367 */         this._elementPrefix = prefix;
/*     */       } else {
/* 369 */         buffWriter = this.elementNames[this._depth].getUtf8Data();
/* 370 */         writeStringToUtf8(localName, (OutputStream)buffWriter);
/*     */       }
/*     */     
/* 373 */     } catch (Exception ex) {
/* 374 */       throw new RuntimeException(ex);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 379 */     this._ncContextState[this._depth] = false;
/*     */ 
/*     */     
/* 382 */     this._depth++; resize();
/* 383 */     resizeElementStack();
/*     */     try {
/* 385 */       this._stream.write(60);
/*     */       
/* 387 */       this._stream.write(buffWriter.getBytes(), 0, buffWriter.getLength());
/* 388 */       this.closeStartTag = true;
/* 389 */     } catch (IOException ex) {
/* 390 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void closeStartTag() throws XMLStreamException {
/*     */     try {
/* 396 */       if (this.closeStartTag) {
/*     */         
/* 398 */         if (this._defURI != null) {
/* 399 */           outputAttrToWriter("xmlns", this._defURI, this._stream);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 404 */         if (this._nsResult.size() > 0) {
/* 405 */           writeAttributesNS(this._nsResult.iterator());
/*     */         }
/*     */         
/* 408 */         if (this._attrResult.size() > 0) {
/* 409 */           writeAttributes(this._attrResult);
/*     */         }
/*     */         
/* 412 */         this._nsResult.clear();
/* 413 */         this._attrResult.clear();
/* 414 */         this._stream.write(62);
/* 415 */         this.closeStartTag = false;
/* 416 */         this._defURI = null;
/*     */       } 
/* 418 */     } catch (IOException ex) {
/* 419 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected StAXAttr getAttribute() {
/* 425 */     if (this._attrPos < this._attrs.size()) {
/* 426 */       return this._attrs.get(this._attrPos++);
/*     */     }
/* 428 */     for (int i = 0; i < 4; i++) {
/* 429 */       this._attrs.add(new StAXAttr());
/*     */     }
/* 431 */     return this._attrs.get(this._attrPos++);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void resizeElementStack() {
/* 436 */     if (this._depth >= this.elementNames.length) {
/* 437 */       BaseCanonicalizer.ElementName[] tmp = new BaseCanonicalizer.ElementName[this.elementNames.length + 10];
/* 438 */       System.arraycopy(this.elementNames, 0, tmp, 0, this.elementNames.length);
/* 439 */       for (int i = this.elementNames.length; i < this.elementNames.length + 10; i++) {
/* 440 */         tmp[i] = new BaseCanonicalizer.ElementName();
/*     */       }
/* 442 */       this.elementNames = tmp;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeAttributes(List<StAXAttr> itr) throws IOException {
/* 448 */     int size = itr.size();
/* 449 */     for (int i = 0; i < size; i++) {
/* 450 */       StAXAttr attr = itr.get(i);
/* 451 */       String prefix = attr.getPrefix();
/* 452 */       if (prefix != null && prefix.length() != 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 458 */         outputAttrToWriter(prefix, attr.getLocalName(), attr.getValue(), this._stream);
/*     */       } else {
/* 460 */         prefix = attr.getLocalName();
/* 461 */         outputAttrToWriter(prefix, attr.getValue(), this._stream);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\StAXC14nCanonicalizerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */