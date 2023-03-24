/*     */ package com.sun.xml.rpc.streaming;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.sax.AttributesHolder;
/*     */ import com.sun.xml.fastinfoset.stax.StAXDocumentParser;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import java.io.InputStream;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class FastInfosetReader
/*     */   extends StAXDocumentParser
/*     */   implements XMLReader
/*     */ {
/*     */   int _state;
/*     */   ElementIdStack _elementIds;
/*     */   int _elementId;
/*     */   AttributesAdapter _attrsAdapter;
/*     */   
/*     */   public FastInfosetReader(InputStream is) {
/* 109 */     this._attrsAdapter = new AttributesAdapter();
/* 110 */     setInputStream(is);
/*     */   }
/*     */   
/*     */   public void reset() {
/* 114 */     super.reset();
/* 115 */     this._state = 0;
/* 116 */     if (this._elementIds == null) {
/* 117 */       this._elementIds = new ElementIdStack();
/*     */     } else {
/* 119 */       this._elementIds.reset();
/*     */     } 
/* 121 */     this._elementId = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int next() {
/* 130 */     if (this._state == 5) {
/* 131 */       return 5;
/*     */     }
/*     */     
/*     */     try {
/* 135 */       int readerEvent = super.next();
/*     */       
/* 137 */       while (readerEvent != 8) {
/* 138 */         switch (readerEvent) {
/*     */           case 1:
/* 140 */             this._elementId = this._elementIds.pushNext();
/* 141 */             return this._state = 1;
/*     */           case 2:
/* 143 */             this._elementId = this._elementIds.pop();
/* 144 */             return this._state = 2;
/*     */           case 4:
/*     */           case 12:
/* 147 */             return this._state = 3;
/*     */           case 3:
/* 149 */             return this._state = 4;
/*     */         } 
/*     */ 
/*     */         
/* 153 */         readerEvent = super.next();
/*     */       }
/*     */     
/* 156 */     } catch (XMLStreamException e) {
/* 157 */       throw wrapException(e);
/*     */     } 
/*     */     
/* 160 */     return this._state = 5;
/*     */   }
/*     */   
/*     */   public int nextElementContent() {
/* 164 */     int state = nextContent();
/* 165 */     if (state == 3) {
/* 166 */       throw new XMLReaderException("xmlreader.unexpectedCharacterContent", getValue());
/*     */     }
/*     */     
/* 169 */     return state;
/*     */   }
/*     */   
/*     */   public int nextContent() {
/*     */     while (true) {
/* 174 */       int state = next();
/* 175 */       switch (state) {
/*     */         case 1:
/*     */         case 2:
/*     */         case 5:
/* 179 */           return state;
/*     */         case 3:
/* 181 */           if (this._characters != null && !isWhiteSpaceCharacters()) {
/* 182 */             return 3;
/*     */           }
/*     */       } 
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
/*     */   public int getState() {
/* 196 */     return this._state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getURI() {
/* 205 */     return getNamespaceURI();
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
/*     */   public Attributes getAttributes() {
/* 218 */     return this._attrsAdapter.setTarget(this._attributes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 227 */     return (this._state == 4) ? getPIData() : getText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getElementId() {
/* 234 */     return this._elementId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineNumber() {
/* 243 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLReader recordElement() {
/* 254 */     throw new UnsupportedOperationException("recordElement()");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skipElement() {
/* 261 */     skipElement(getElementId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void skipElement(int elementId) {
/* 268 */     while (this._state != 5 && (this._state != 2 || this._elementId != elementId))
/*     */     {
/* 270 */       next();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/*     */     try {
/* 281 */       this._state = 5;
/* 282 */       super.close();
/*     */     }
/* 284 */     catch (XMLStreamException e) {
/* 285 */       throw wrapException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private XMLReaderException wrapException(XMLStreamException e) {
/* 292 */     return new XMLReaderException("xmlreader.ioException", (Localizable)new LocalizableExceptionAdapter(e));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isWhiteSpaceCharacters() {
/* 298 */     int i = this._charactersOffset;
/* 299 */     int end = i + this._charactersLength;
/* 300 */     while (i < end) {
/* 301 */       if (this._characters[i++] > ' ') {
/* 302 */         return false;
/*     */       }
/*     */     } 
/* 305 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class AttributesAdapter
/*     */     implements Attributes
/*     */   {
/*     */     AttributesHolder _attr;
/*     */ 
/*     */ 
/*     */     
/*     */     public final AttributesAdapter setTarget(AttributesHolder attr) {
/* 318 */       this._attr = attr;
/* 319 */       return this;
/*     */     }
/*     */     
/*     */     public final int getLength() {
/* 323 */       return this._attr.getLength();
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean isNamespaceDeclaration(int index) {
/* 328 */       return false;
/*     */     }
/*     */     
/*     */     public final QName getName(int index) {
/* 332 */       return this._attr.getQualifiedName(index).getQName();
/*     */     }
/*     */     
/*     */     public final String getURI(int index) {
/* 336 */       return this._attr.getURI(index);
/*     */     }
/*     */     
/*     */     public final String getLocalName(int index) {
/* 340 */       return this._attr.getLocalName(index);
/*     */     }
/*     */     
/*     */     public final String getPrefix(int index) {
/* 344 */       return this._attr.getPrefix(index);
/*     */     }
/*     */     
/*     */     public final String getValue(int index) {
/* 348 */       return this._attr.getValue(index);
/*     */     }
/*     */     
/*     */     public final int getIndex(QName name) {
/* 352 */       return this._attr.getIndex(name.getNamespaceURI(), name.getLocalPart());
/*     */     }
/*     */     
/*     */     public final int getIndex(String uri, String localName) {
/* 356 */       return this._attr.getIndex(uri, localName);
/*     */     }
/*     */     
/*     */     public final int getIndex(String localName) {
/* 360 */       return this._attr.getIndex(localName);
/*     */     }
/*     */     
/*     */     public final String getValue(QName name) {
/* 364 */       return this._attr.getValue(name.getNamespaceURI(), name.getLocalPart());
/*     */     }
/*     */     
/*     */     public final String getValue(String uri, String localName) {
/* 368 */       return this._attr.getValue(uri, localName);
/*     */     }
/*     */     
/*     */     public final String getValue(String localName) {
/* 372 */       return this._attr.getValue(localName);
/*     */     }
/*     */     
/*     */     public final String toString() {
/* 376 */       StringBuffer attributes = new StringBuffer();
/* 377 */       for (int i = 0; i < getLength(); i++) {
/* 378 */         if (i != 0) {
/* 379 */           attributes.append("\n");
/*     */         }
/* 381 */         attributes.append(getURI(i) + ":" + getLocalName(i) + " = " + getValue(i));
/*     */       } 
/* 383 */       return attributes.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\streaming\FastInfosetReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */