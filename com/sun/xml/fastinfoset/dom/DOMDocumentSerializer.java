/*     */ package com.sun.xml.fastinfoset.dom;
/*     */ 
/*     */ import com.sun.xml.fastinfoset.Encoder;
/*     */ import com.sun.xml.fastinfoset.QualifiedName;
/*     */ import com.sun.xml.fastinfoset.util.LocalNameQualifiedNamesMap;
/*     */ import com.sun.xml.fastinfoset.util.NamespaceContextImplementation;
/*     */ import java.io.IOException;
/*     */ import org.jvnet.fastinfoset.FastInfosetException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DOMDocumentSerializer
/*     */   extends Encoder
/*     */ {
/*     */   public final void serialize(Node n) throws IOException {
/*  47 */     switch (n.getNodeType()) {
/*     */       case 9:
/*  49 */         serialize((Document)n);
/*     */         break;
/*     */       case 1:
/*  52 */         serializeElementAsDocument(n);
/*     */         break;
/*     */       case 8:
/*  55 */         serializeComment(n);
/*     */         break;
/*     */       case 7:
/*  58 */         serializeProcessingInstruction(n);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void serialize(Document d) throws IOException {
/*  69 */     reset();
/*  70 */     encodeHeader(false);
/*  71 */     encodeInitialVocabulary();
/*     */     
/*  73 */     NodeList nl = d.getChildNodes();
/*  74 */     for (int i = 0; i < nl.getLength(); i++) {
/*  75 */       Node n = nl.item(i);
/*  76 */       switch (n.getNodeType()) {
/*     */         case 1:
/*  78 */           serializeElement(n);
/*     */           break;
/*     */         case 8:
/*  81 */           serializeComment(n);
/*     */           break;
/*     */         case 7:
/*  84 */           serializeProcessingInstruction(n);
/*     */           break;
/*     */       } 
/*     */     } 
/*  88 */     encodeDocumentTermination();
/*     */   }
/*     */   
/*     */   protected final void serializeElementAsDocument(Node e) throws IOException {
/*  92 */     reset();
/*  93 */     encodeHeader(false);
/*  94 */     encodeInitialVocabulary();
/*     */     
/*  96 */     serializeElement(e);
/*     */     
/*  98 */     encodeDocumentTermination();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   protected NamespaceContextImplementation _namespaceScopeContext = new NamespaceContextImplementation();
/* 106 */   protected Node[] _attributes = new Node[32];
/*     */   
/*     */   protected final void serializeElement(Node e) throws IOException {
/* 109 */     encodeTermination();
/*     */     
/* 111 */     int attributesSize = 0;
/*     */     
/* 113 */     this._namespaceScopeContext.pushContext();
/*     */     
/* 115 */     if (e.hasAttributes()) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 120 */       NamedNodeMap nnm = e.getAttributes();
/* 121 */       for (int i = 0; i < nnm.getLength(); i++) {
/* 122 */         Node a = nnm.item(i);
/* 123 */         String str = a.getNamespaceURI();
/* 124 */         if (str != null && str.equals("http://www.w3.org/2000/xmlns/")) {
/* 125 */           String attrPrefix = a.getLocalName();
/* 126 */           String attrNamespace = a.getNodeValue();
/* 127 */           if (attrPrefix == "xmlns" || attrPrefix.equals("xmlns")) {
/* 128 */             attrPrefix = "";
/*     */           }
/* 130 */           this._namespaceScopeContext.declarePrefix(attrPrefix, attrNamespace);
/*     */         } else {
/* 132 */           if (attributesSize == this._attributes.length) {
/* 133 */             Node[] attributes = new Node[attributesSize * 3 / 2 + 1];
/* 134 */             System.arraycopy(this._attributes, 0, attributes, 0, attributesSize);
/* 135 */             this._attributes = attributes;
/*     */           } 
/* 137 */           this._attributes[attributesSize++] = a;
/*     */           
/* 139 */           String attrNamespaceURI = a.getNamespaceURI();
/* 140 */           String attrPrefix = a.getPrefix();
/* 141 */           if (attrPrefix != null && !this._namespaceScopeContext.getNamespaceURI(attrPrefix).equals(attrNamespaceURI)) {
/* 142 */             this._namespaceScopeContext.declarePrefix(attrPrefix, attrNamespaceURI);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 148 */     String elementNamespaceURI = e.getNamespaceURI();
/* 149 */     String elementPrefix = e.getPrefix();
/* 150 */     if (elementPrefix == null) elementPrefix = ""; 
/* 151 */     if (elementNamespaceURI != null && !this._namespaceScopeContext.getNamespaceURI(elementPrefix).equals(elementNamespaceURI))
/*     */     {
/* 153 */       this._namespaceScopeContext.declarePrefix(elementPrefix, elementNamespaceURI);
/*     */     }
/*     */     
/* 156 */     if (!this._namespaceScopeContext.isCurrentContextEmpty()) {
/* 157 */       if (attributesSize > 0) {
/* 158 */         write(120);
/*     */       } else {
/*     */         
/* 161 */         write(56);
/*     */       } 
/*     */       
/* 164 */       int i = this._namespaceScopeContext.getCurrentContextStartIndex();
/* 165 */       for (; i < this._namespaceScopeContext.getCurrentContextEndIndex(); i++) {
/*     */         
/* 167 */         String prefix = this._namespaceScopeContext.getPrefix(i);
/* 168 */         String uri = this._namespaceScopeContext.getNamespaceURI(i);
/* 169 */         encodeNamespaceAttribute(prefix, uri);
/*     */       } 
/* 171 */       write(240);
/* 172 */       this._b = 0;
/*     */     } else {
/* 174 */       this._b = (attributesSize > 0) ? 64 : 0;
/*     */     } 
/*     */ 
/*     */     
/* 178 */     String namespaceURI = elementNamespaceURI;
/*     */     
/* 180 */     namespaceURI = (namespaceURI == null) ? "" : namespaceURI;
/* 181 */     encodeElement(namespaceURI, e.getNodeName(), e.getLocalName());
/*     */     
/* 183 */     if (attributesSize > 0) {
/*     */       
/* 185 */       for (int i = 0; i < attributesSize; i++) {
/* 186 */         Node a = this._attributes[i];
/* 187 */         this._attributes[i] = null;
/* 188 */         namespaceURI = a.getNamespaceURI();
/* 189 */         namespaceURI = (namespaceURI == null) ? "" : namespaceURI;
/* 190 */         encodeAttribute(namespaceURI, a.getNodeName(), a.getLocalName());
/*     */         
/* 192 */         String value = a.getNodeValue();
/* 193 */         boolean addToTable = isAttributeValueLengthMatchesLimit(value.length());
/* 194 */         encodeNonIdentifyingStringOnFirstBit(value, this._v.attributeValue, addToTable, false);
/*     */       } 
/*     */       
/* 197 */       this._b = 240;
/* 198 */       this._terminate = true;
/*     */     } 
/*     */     
/* 201 */     if (e.hasChildNodes()) {
/*     */       
/* 203 */       NodeList nl = e.getChildNodes();
/* 204 */       for (int i = 0; i < nl.getLength(); i++) {
/* 205 */         Node n = nl.item(i);
/* 206 */         switch (n.getNodeType()) {
/*     */           case 1:
/* 208 */             serializeElement(n);
/*     */             break;
/*     */           case 3:
/* 211 */             serializeText(n);
/*     */             break;
/*     */           case 4:
/* 214 */             serializeCDATA(n);
/*     */             break;
/*     */           case 8:
/* 217 */             serializeComment(n);
/*     */             break;
/*     */           case 7:
/* 220 */             serializeProcessingInstruction(n);
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 225 */     encodeElementTermination();
/* 226 */     this._namespaceScopeContext.popContext();
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void serializeText(Node t) throws IOException {
/* 231 */     String text = t.getNodeValue();
/*     */     
/* 233 */     int length = (text != null) ? text.length() : 0;
/* 234 */     if (length == 0)
/*     */       return; 
/* 236 */     if (length < this._charBuffer.length) {
/* 237 */       text.getChars(0, length, this._charBuffer, 0);
/* 238 */       if (getIgnoreWhiteSpaceTextContent() && isWhiteSpace(this._charBuffer, 0, length)) {
/*     */         return;
/*     */       }
/* 241 */       encodeTermination();
/* 242 */       encodeCharacters(this._charBuffer, 0, length);
/*     */     } else {
/* 244 */       char[] ch = text.toCharArray();
/* 245 */       if (getIgnoreWhiteSpaceTextContent() && isWhiteSpace(ch, 0, length)) {
/*     */         return;
/*     */       }
/* 248 */       encodeTermination();
/* 249 */       encodeCharactersNoClone(ch, 0, length);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void serializeCDATA(Node t) throws IOException {
/* 254 */     String text = t.getNodeValue();
/*     */     
/* 256 */     int length = (text != null) ? text.length() : 0;
/* 257 */     if (length == 0) {
/*     */       return;
/*     */     }
/* 260 */     char[] ch = text.toCharArray();
/* 261 */     if (getIgnoreWhiteSpaceTextContent() && isWhiteSpace(ch, 0, length)) {
/*     */       return;
/*     */     }
/* 264 */     encodeTermination();
/*     */     try {
/* 266 */       encodeCIIBuiltInAlgorithmDataAsCDATA(ch, 0, length);
/* 267 */     } catch (FastInfosetException e) {
/* 268 */       throw new IOException("");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected final void serializeComment(Node c) throws IOException {
/* 274 */     if (getIgnoreComments())
/*     */       return; 
/* 276 */     encodeTermination();
/*     */     
/* 278 */     String comment = c.getNodeValue();
/*     */     
/* 280 */     int length = (comment != null) ? comment.length() : 0;
/* 281 */     if (length == 0) {
/* 282 */       encodeComment(this._charBuffer, 0, 0);
/* 283 */     } else if (length < this._charBuffer.length) {
/* 284 */       comment.getChars(0, length, this._charBuffer, 0);
/* 285 */       encodeComment(this._charBuffer, 0, length);
/*     */     } else {
/* 287 */       char[] ch = comment.toCharArray();
/* 288 */       encodeCommentNoClone(ch, 0, length);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void serializeProcessingInstruction(Node pi) throws IOException {
/* 293 */     if (getIgnoreProcesingInstructions())
/*     */       return; 
/* 295 */     encodeTermination();
/*     */     
/* 297 */     String target = pi.getNodeName();
/* 298 */     String data = pi.getNodeValue();
/* 299 */     encodeProcessingInstruction(target, data);
/*     */   }
/*     */   
/*     */   protected final void encodeElement(String namespaceURI, String qName, String localName) throws IOException {
/* 303 */     LocalNameQualifiedNamesMap.Entry entry = this._v.elementName.obtainEntry(qName);
/* 304 */     if (entry._valueIndex > 0) {
/* 305 */       QualifiedName[] names = entry._value;
/* 306 */       for (int i = 0; i < entry._valueIndex; i++) {
/* 307 */         if (namespaceURI == (names[i]).namespaceName || namespaceURI.equals((names[i]).namespaceName)) {
/* 308 */           encodeNonZeroIntegerOnThirdBit((names[i]).index);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 315 */     if (localName != null) {
/* 316 */       encodeLiteralElementQualifiedNameOnThirdBit(namespaceURI, getPrefixFromQualifiedName(qName), localName, entry);
/*     */     } else {
/*     */       
/* 319 */       encodeLiteralElementQualifiedNameOnThirdBit(namespaceURI, "", qName, entry);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final void encodeAttribute(String namespaceURI, String qName, String localName) throws IOException {
/* 324 */     LocalNameQualifiedNamesMap.Entry entry = this._v.attributeName.obtainEntry(qName);
/* 325 */     if (entry._valueIndex > 0) {
/* 326 */       QualifiedName[] names = entry._value;
/* 327 */       for (int i = 0; i < entry._valueIndex; i++) {
/* 328 */         if (namespaceURI == (names[i]).namespaceName || namespaceURI.equals((names[i]).namespaceName)) {
/* 329 */           encodeNonZeroIntegerOnSecondBitFirstBitZero((names[i]).index);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 336 */     if (localName != null) {
/* 337 */       encodeLiteralAttributeQualifiedNameOnSecondBit(namespaceURI, getPrefixFromQualifiedName(qName), localName, entry);
/*     */     } else {
/*     */       
/* 340 */       encodeLiteralAttributeQualifiedNameOnSecondBit(namespaceURI, "", qName, entry);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\fastinfoset\dom\DOMDocumentSerializer.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */