/*     */ package com.sun.xml.wss.impl.c14n;
/*     */ 
/*     */ import com.sun.xml.wss.impl.misc.UnsyncByteArrayOutputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.NamespaceContext;
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
/*     */ public class StAXEXC14nCanonicalizerImpl
/*     */   extends StAXC14nCanonicalizerImpl
/*     */ {
/*  68 */   private List inclusivePrefixList = null;
/*  69 */   private HashSet visiblyUtilized = new HashSet();
/*     */   
/*  71 */   private UnsyncByteArrayOutputStream tmpBuffer = null;
/*     */   
/*  73 */   BaseCanonicalizer.NamespaceContextImpl exC14NContext = new BaseCanonicalizer.NamespaceContextImpl();
/*     */ 
/*     */   
/*     */   private boolean bodyPrologueTime = false;
/*     */ 
/*     */   
/*     */   private String bodyPrologue;
/*     */   
/*     */   private String bodyEpilogue;
/*     */   
/*     */   private boolean forceDefNS = false;
/*     */ 
/*     */   
/*     */   public StAXEXC14nCanonicalizerImpl() {
/*  87 */     this.tmpBuffer = new UnsyncByteArrayOutputStream();
/*     */   }
/*     */   
/*     */   public void setBodyPrologueTime(boolean bodyPrologueTime) {
/*  91 */     this.bodyPrologueTime = bodyPrologueTime;
/*     */   }
/*     */   
/*     */   public void setBodyEpilogue(String bodyEpilogue) {
/*  95 */     this.bodyEpilogue = bodyEpilogue;
/*     */   }
/*     */   
/*     */   public void setBodyPrologue(String bodyPrologue) {
/*  99 */     this.bodyPrologue = bodyPrologue;
/*     */   }
/*     */   
/*     */   public boolean isParentToParentAdvice() {
/* 103 */     if (this._depth > 2) {
/* 104 */       BaseCanonicalizer.ElementName qname = this.elementNames[this._depth - 2];
/* 105 */       if (qname.getUtf8Data().getLength() == 11 || qname.getUtf8Data().getLength() == 12) {
/* 106 */         String str = new String(qname.getUtf8Data().getBytes(), qname.getUtf8Data().getLength() - 6, 6);
/* 107 */         if (str.equals("Advice")) {
/* 108 */           return true;
/*     */         }
/*     */       } else {
/* 111 */         return false;
/*     */       } 
/*     */     } 
/* 114 */     return false;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 118 */     super.reset();
/* 119 */     this.exC14NContext.reset();
/*     */   }
/*     */   
/*     */   public void setInclusivePrefixList(List values) {
/* 123 */     this.inclusivePrefixList = values;
/*     */   }
/*     */   
/*     */   public List getInclusivePrefixList() {
/* 127 */     return this.inclusivePrefixList;
/*     */   }
/*     */   
/*     */   public void forceDefaultNS(boolean isForce) {
/* 131 */     this.forceDefNS = isForce;
/*     */   }
/*     */   public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
/* 134 */     this.exC14NContext.declareNamespace(prefix, namespaceURI);
/*     */   }
/*     */   
/*     */   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 138 */     String pf = prefix;
/* 139 */     if (prefix == null) {
/* 140 */       pf = "";
/*     */     }
/* 142 */     super.writeStartElement(pf, localName, namespaceURI);
/* 143 */     this._elementPrefix = pf;
/* 144 */     this.exC14NContext.push();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void closeStartTag() throws XMLStreamException {
/*     */     try {
/* 150 */       if (this.closeStartTag) {
/* 151 */         if (this._attrResult.size() > 0) {
/* 152 */           collectVisiblePrefixes(this._attrResult.iterator());
/*     */         }
/* 154 */         if (this._elementPrefix != null)
/* 155 */           this.visiblyUtilized.add(this._elementPrefix); 
/* 156 */         AttributeNS nsDecl = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 167 */         if (this.visiblyUtilized.size() > 0) {
/* 168 */           Iterator prefixItr = this.visiblyUtilized.iterator();
/* 169 */           populateNamespaceDecl(prefixItr);
/*     */         } 
/* 171 */         if (this.inclusivePrefixList != null) {
/* 172 */           populateNamespaceDecl(this.inclusivePrefixList.iterator());
/*     */         }
/*     */         
/* 175 */         if (this.forceDefNS) {
/* 176 */           this.forceDefNS = false;
/* 177 */           if (this.exC14NContext.getNamespaceDeclaration("") == null && "".equals(this.exC14NContext.getNamespaceURI(""))) {
/*     */             
/* 179 */             AttributeNS ns = new AttributeNS();
/* 180 */             ns.setPrefix("");
/* 181 */             ns.setUri("");
/* 182 */             if (!this._nsResult.contains(ns)) {
/* 183 */               this._nsResult.add(ns);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 188 */         if (this._nsResult.size() > 0) {
/* 189 */           BaseCanonicalizer.sort(this._nsResult);
/* 190 */           writeAttributesNS(this._nsResult);
/*     */         } 
/*     */         
/* 193 */         if (this._attrResult.size() > 0) {
/* 194 */           BaseCanonicalizer.sort(this._attrResult);
/* 195 */           writeAttributes(this._attrResult);
/*     */         } 
/* 197 */         this.visiblyUtilized.clear();
/* 198 */         this._nsResult.clear();
/* 199 */         this._attrResult.clear();
/* 200 */         this._stream.write(62);
/*     */ 
/*     */         
/* 203 */         if (this.bodyPrologueTime) {
/* 204 */           if (this.bodyPrologue != null) {
/* 205 */             this._stream.write(this.bodyPrologue.getBytes());
/*     */           }
/* 207 */           this.bodyPrologueTime = false;
/*     */         } 
/*     */         
/* 210 */         this.closeStartTag = false;
/* 211 */         this._elementPrefix = null;
/* 212 */         this._defURI = null;
/*     */       } 
/* 214 */     } catch (IOException ex) {
/* 215 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
/* 223 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 229 */     closeStartTag();
/* 230 */     this.exC14NContext.push();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 235 */       this._stream.write(60);
/* 236 */       this.elemBuffer.reset();
/* 237 */       if (prefix.length() == 0) {
/* 238 */         writeStringToUtf8(localName, (OutputStream)this.elemBuffer);
/*     */       } else {
/* 240 */         writeStringToUtf8(prefix, (OutputStream)this.elemBuffer);
/* 241 */         writeStringToUtf8(":", (OutputStream)this.elemBuffer);
/* 242 */         writeStringToUtf8(localName, (OutputStream)this.elemBuffer);
/*     */       } 
/*     */       
/* 245 */       byte[] endElem = this.elemBuffer.getBytes();
/* 246 */       int len = this.elemBuffer.getLength();
/* 247 */       this.visiblyUtilized.add(prefix);
/* 248 */       AttributeNS nsDecl = null;
/* 249 */       this._stream.write(endElem, 0, len);
/*     */       
/* 251 */       if (this.visiblyUtilized.size() > 0) {
/* 252 */         Iterator prefixItr = this.visiblyUtilized.iterator();
/* 253 */         populateNamespaceDecl(prefixItr);
/*     */       } 
/* 255 */       if (this.inclusivePrefixList != null) {
/* 256 */         populateNamespaceDecl(this.inclusivePrefixList.iterator());
/*     */       }
/*     */       
/* 259 */       if (this._nsResult.size() > 0) {
/* 260 */         BaseCanonicalizer.sort(this._nsResult);
/* 261 */         writeAttributesNS(this._nsResult);
/*     */       } 
/*     */       
/* 264 */       if (this._attrResult.size() > 0) {
/* 265 */         BaseCanonicalizer.sort(this._attrResult);
/* 266 */         writeAttributes(this._attrResult);
/*     */       } 
/*     */ 
/*     */       
/* 270 */       this.visiblyUtilized.clear();
/* 271 */       this._nsResult.clear();
/* 272 */       this._attrResult.clear();
/*     */       
/* 274 */       this.closeStartTag = false;
/* 275 */       this._elementPrefix = "";
/* 276 */       this._defURI = null;
/*     */       
/* 278 */       this._stream.write(62);
/* 279 */       this._stream.write(_END_TAG);
/*     */       
/* 281 */       this._stream.write(endElem, 0, len);
/* 282 */       this._stream.write(62);
/* 283 */       this.exC14NContext.pop();
/* 284 */     } catch (IOException ex) {
/* 285 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {
/* 290 */     while (this._depth > 0) {
/* 291 */       writeEndElement();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/* 297 */     closeStartTag();
/* 298 */     if (this._depth == 0) {
/*     */       return;
/*     */     }
/* 301 */     BaseCanonicalizer.ElementName qname = this.elementNames[--this._depth];
/*     */     
/* 303 */     this.exC14NContext.pop();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 309 */       if (this._depth == 0) {
/* 310 */         String toBeClosed = new String(qname.getUtf8Data().getBytes());
/*     */         
/* 312 */         if (toBeClosed.contains(":Body") && this.bodyEpilogue != null) {
/* 313 */           this._stream.write(this.bodyEpilogue.getBytes());
/*     */         }
/*     */       } 
/*     */       
/* 317 */       this._stream.write(_END_TAG);
/*     */       
/* 319 */       this._stream.write(qname.getUtf8Data().getBytes(), 0, qname.getUtf8Data().getLength());
/* 320 */       qname.getUtf8Data().reset();
/* 321 */       this._stream.write(62);
/* 322 */     } catch (IOException ex) {
/* 323 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void collectVisiblePrefixes(Iterator<StAXAttr> itr) throws IOException {
/* 329 */     while (itr.hasNext()) {
/* 330 */       StAXAttr attr = itr.next();
/* 331 */       String prefix = attr.getPrefix();
/* 332 */       if (prefix.length() > 0) {
/* 333 */         this.visiblyUtilized.add(prefix);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void populateNamespaceDecl(Iterator<String> prefixItr) {
/* 341 */     AttributeNS nsDecl = null;
/* 342 */     while (prefixItr.hasNext()) {
/* 343 */       String prefix = prefixItr.next();
/* 344 */       if (prefix.equals("xml")) {
/*     */         continue;
/*     */       }
/* 347 */       nsDecl = this.exC14NContext.getNamespaceDeclaration(prefix);
/*     */       
/* 349 */       if (nsDecl != null && !nsDecl.isWritten()) {
/* 350 */         nsDecl.setWritten(true);
/* 351 */         this._nsResult.add(nsDecl);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void writeAttributesNS(List<AttributeNS> itr) throws IOException {
/* 357 */     AttributeNS attr = null;
/* 358 */     int size = itr.size();
/* 359 */     for (int i = 0; i < size; i++) {
/* 360 */       attr = itr.get(i);
/* 361 */       this.tmpBuffer.reset();
/* 362 */       this._stream.write(attr.getUTF8Data((ByteArrayOutputStream)this.tmpBuffer));
/*     */     } 
/*     */   }
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 367 */     return this.exC14NContext;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\impl\c14n\StAXEXC14nCanonicalizerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */