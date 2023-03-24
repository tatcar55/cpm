/*     */ package com.sun.xml.wss.impl.c14n;
/*     */ 
/*     */ import com.sun.xml.wss.impl.misc.UnsyncByteArrayOutputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EXC14nStAXReaderBasedCanonicalizer
/*     */   extends BaseCanonicalizer
/*     */ {
/*  59 */   private BaseCanonicalizer.NamespaceContextImpl _exC14NContext = new BaseCanonicalizer.NamespaceContextImpl();
/*  60 */   private List _inclusivePrefixList = null;
/*  61 */   private UnsyncByteArrayOutputStream _tmpBuffer = null;
/*  62 */   private HashSet _visiblyUtilized = new HashSet();
/*  63 */   private int _index = 0;
/*     */ 
/*     */   
/*     */   public EXC14nStAXReaderBasedCanonicalizer() {
/*  67 */     this._attrResult = new ArrayList();
/*  68 */     for (int i = 0; i < 4; i++) {
/*  69 */       this._attrs.add(new StAXAttr());
/*     */     }
/*  71 */     this._tmpBuffer = new UnsyncByteArrayOutputStream();
/*     */   }
/*     */ 
/*     */   
/*     */   public void canonicalize(XMLStreamReader reader, OutputStream stream, List inclusiveList) throws XMLStreamException, IOException {
/*  76 */     if (reader.hasNext() && reader.getEventType() != 1) {
/*  77 */       throw new XMLStreamException("Reader should point to START_ELEMENT EVENT");
/*     */     }
/*     */     
/*  80 */     updatedNamespaceContext(reader);
/*  81 */     this._stream = stream;
/*  82 */     this._inclusivePrefixList = inclusiveList;
/*  83 */     int eventType = reader.getEventType();
/*     */     do {
/*  85 */       switch (eventType) {
/*     */         case 1:
/*  87 */           this._exC14NContext.push();
/*  88 */           this._index++;
/*  89 */           writeStartElement(reader);
/*     */           break;
/*     */         
/*     */         case 2:
/*  93 */           this._exC14NContext.pop();
/*  94 */           this._index--;
/*  95 */           writeEndElement(reader);
/*     */           break;
/*     */         
/*     */         case 12:
/*  99 */           outputTextToWriter(reader.getTextCharacters(), reader.getTextStart(), reader.getTextLength(), this._stream);
/*     */           break;
/*     */         
/*     */         case 4:
/* 103 */           if (!reader.isWhiteSpace()) {
/* 104 */             outputTextToWriter(reader.getText(), this._stream);
/*     */           }
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 142 */       eventType = reader.next();
/* 143 */     } while (reader.hasNext() && this._index > 0);
/*     */   }
/*     */   
/*     */   private void writeStartElement(XMLStreamReader reader) throws IOException {
/* 147 */     String localName = reader.getLocalName();
/* 148 */     String prefix = reader.getPrefix();
/* 149 */     String uri = reader.getNamespaceURI();
/* 150 */     writeCharToUtf8('<', this._stream);
/* 151 */     if (prefix == null && uri == null) {
/* 152 */       writeStringToUtf8(localName, this._stream);
/* 153 */     } else if (prefix != null && prefix.length() > 0) {
/* 154 */       writeStringToUtf8(prefix, this._stream);
/* 155 */       writeStringToUtf8(":", this._stream);
/* 156 */       writeStringToUtf8(localName, this._stream);
/* 157 */     } else if (prefix == null) {
/* 158 */       writeStringToUtf8(localName, this._stream);
/*     */     } 
/* 160 */     updatedNamespaceContext(reader);
/* 161 */     updateAttributes(reader);
/*     */     
/* 163 */     if (prefix != null) {
/* 164 */       this._visiblyUtilized.add(prefix);
/*     */     }
/* 166 */     if (this._elementPrefix.length() > 0) {
/* 167 */       AttributeNS eDecl = this._exC14NContext.getNamespaceDeclaration(this._elementPrefix);
/*     */       
/* 169 */       if (eDecl != null && !eDecl.isWritten()) {
/* 170 */         eDecl.setWritten(true);
/* 171 */         this._nsResult.add(eDecl);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 176 */     if (this._visiblyUtilized.size() > 0) {
/* 177 */       Iterator prefixItr = this._visiblyUtilized.iterator();
/* 178 */       populateNamespaceDecl(prefixItr);
/*     */     } 
/* 180 */     if (this._inclusivePrefixList != null) {
/* 181 */       populateNamespaceDecl(this._inclusivePrefixList.iterator());
/*     */     }
/*     */     
/* 184 */     if (this._nsResult.size() > 0) {
/* 185 */       BaseCanonicalizer.sort(this._nsResult);
/* 186 */       writeAttributesNS(this._nsResult);
/*     */     } 
/* 188 */     if (this._attrResult.size() > 0) {
/* 189 */       BaseCanonicalizer.sort(this._attrResult);
/* 190 */       writeAttributes(this._attrResult);
/*     */     } 
/* 192 */     writeCharToUtf8('>', this._stream);
/* 193 */     this._nsResult.clear();
/* 194 */     this._attrResult.clear();
/* 195 */     this._visiblyUtilized.clear();
/*     */   }
/*     */   
/*     */   private void populateNamespaceDecl(Iterator<String> prefixItr) {
/* 199 */     AttributeNS nsDecl = null;
/* 200 */     while (prefixItr.hasNext()) {
/* 201 */       String prefix = prefixItr.next();
/* 202 */       nsDecl = this._exC14NContext.getNamespaceDeclaration(prefix);
/*     */       
/* 204 */       if (nsDecl != null && !nsDecl.isWritten()) {
/* 205 */         nsDecl.setWritten(true);
/* 206 */         this._nsResult.add(nsDecl);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updatedNamespaceContext(XMLStreamReader reader) {
/* 212 */     if (reader.getEventType() != 1) {
/*     */       return;
/*     */     }
/* 215 */     int count = reader.getNamespaceCount();
/* 216 */     for (int i = 0; i < count; i++) {
/* 217 */       String prefix = reader.getNamespacePrefix(i);
/* 218 */       String uri = reader.getNamespaceURI(i);
/* 219 */       this._exC14NContext.declareNamespace(prefix, uri);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateAttributes(XMLStreamReader reader) throws IOException {
/* 224 */     int count = reader.getAttributeCount();
/* 225 */     for (int i = 0; i < count; i++) {
/* 226 */       String localName = reader.getAttributeLocalName(i);
/* 227 */       String uri = reader.getAttributeNamespace(i);
/* 228 */       String prefix = reader.getAttributePrefix(i);
/* 229 */       String value = reader.getAttributeValue(i);
/* 230 */       StAXAttr attr = getAttribute();
/* 231 */       attr.setLocalName(localName);
/* 232 */       attr.setValue(value);
/* 233 */       attr.setPrefix(prefix);
/* 234 */       attr.setUri(uri);
/* 235 */       this._attrResult.add(attr);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeEndElement(XMLStreamReader reader) throws IOException {
/* 240 */     String localName = reader.getLocalName();
/* 241 */     String prefix = reader.getPrefix();
/* 242 */     String uri = reader.getNamespaceURI();
/* 243 */     writeStringToUtf8("</", this._stream);
/* 244 */     if (prefix == null && uri == null) {
/* 245 */       writeStringToUtf8(localName, this._stream);
/* 246 */     } else if (prefix != null && prefix.length() > 0) {
/* 247 */       writeStringToUtf8(prefix, this._stream);
/* 248 */       writeStringToUtf8(":", this._stream);
/* 249 */       writeStringToUtf8(localName, this._stream);
/* 250 */     } else if (prefix == null) {
/* 251 */       writeStringToUtf8(localName, this._stream);
/*     */     } 
/* 253 */     writeCharToUtf8('>', this._stream);
/*     */   }
/*     */ 
/*     */   
/*     */   protected StAXAttr getAttribute() {
/* 258 */     if (this._attrPos < this._attrs.size()) {
/* 259 */       return this._attrs.get(this._attrPos++);
/*     */     }
/* 261 */     for (int i = 0; i < 4; i++) {
/* 262 */       this._attrs.add(new StAXAttr());
/*     */     }
/* 264 */     return this._attrs.get(this._attrPos++);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeAttributesNS(List<AttributeNS> itr) throws IOException {
/* 270 */     AttributeNS attr = null;
/* 271 */     int size = itr.size();
/* 272 */     for (int i = 0; i < size; i++) {
/* 273 */       attr = itr.get(i);
/* 274 */       this._tmpBuffer.reset();
/* 275 */       this._stream.write(attr.getUTF8Data((ByteArrayOutputStream)this._tmpBuffer));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeAttributes(List<StAXAttr> itr) throws IOException {
/* 282 */     int size = itr.size();
/* 283 */     for (int i = 0; i < size; i++) {
/* 284 */       StAXAttr attr = itr.get(i);
/* 285 */       String prefix = attr.getPrefix();
/* 286 */       if (prefix.length() != 0) {
/* 287 */         outputAttrToWriter(prefix, attr.getLocalName(), attr.getValue(), this._stream);
/*     */       } else {
/* 289 */         prefix = attr.getLocalName();
/* 290 */         outputAttrToWriter(prefix, attr.getValue(), this._stream);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\EXC14nStAXReaderBasedCanonicalizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */