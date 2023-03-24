/*     */ package com.sun.xml.stream.buffer.stax;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import org.jvnet.staxex.Base64Data;
/*     */ import org.jvnet.staxex.XMLStreamReaderEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamReaderBufferCreator
/*     */   extends StreamBufferCreator
/*     */ {
/*     */   private int _eventType;
/*     */   private boolean _storeInScopeNamespacesOnElementFragment;
/*     */   private Map<String, Integer> _inScopePrefixes;
/*     */   
/*     */   public StreamReaderBufferCreator() {}
/*     */   
/*     */   public StreamReaderBufferCreator(MutableXMLStreamBuffer buffer) {
/*  78 */     setBuffer(buffer);
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
/*     */   public MutableXMLStreamBuffer create(XMLStreamReader reader) throws XMLStreamException {
/* 100 */     if (this._buffer == null) {
/* 101 */       createBuffer();
/*     */     }
/* 103 */     store(reader);
/*     */     
/* 105 */     return getXMLStreamBuffer();
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
/*     */   public MutableXMLStreamBuffer createElementFragment(XMLStreamReader reader, boolean storeInScopeNamespaces) throws XMLStreamException {
/* 126 */     if (this._buffer == null) {
/* 127 */       createBuffer();
/*     */     }
/*     */     
/* 130 */     if (!reader.hasNext()) {
/* 131 */       return this._buffer;
/*     */     }
/*     */     
/* 134 */     this._storeInScopeNamespacesOnElementFragment = storeInScopeNamespaces;
/*     */     
/* 136 */     this._eventType = reader.getEventType();
/* 137 */     if (this._eventType != 1) {
/*     */       do {
/* 139 */         this._eventType = reader.next();
/* 140 */       } while (this._eventType != 1 && this._eventType != 8);
/*     */     }
/*     */     
/* 143 */     if (storeInScopeNamespaces) {
/* 144 */       this._inScopePrefixes = new HashMap<String, Integer>();
/*     */     }
/*     */     
/* 147 */     storeElementAndChildren(reader);
/*     */     
/* 149 */     return getXMLStreamBuffer();
/*     */   }
/*     */   
/*     */   private void store(XMLStreamReader reader) throws XMLStreamException {
/* 153 */     if (!reader.hasNext()) {
/*     */       return;
/*     */     }
/*     */     
/* 157 */     this._eventType = reader.getEventType();
/* 158 */     switch (this._eventType) {
/*     */       case 7:
/* 160 */         storeDocumentAndChildren(reader);
/*     */         break;
/*     */       case 1:
/* 163 */         storeElementAndChildren(reader);
/*     */         break;
/*     */       default:
/* 166 */         throw new XMLStreamException("XMLStreamReader not positioned at a document or element");
/*     */     } 
/*     */     
/* 169 */     increaseTreeCount();
/*     */   }
/*     */   
/*     */   private void storeDocumentAndChildren(XMLStreamReader reader) throws XMLStreamException {
/* 173 */     storeStructure(16);
/*     */     
/* 175 */     this._eventType = reader.next();
/* 176 */     while (this._eventType != 8) {
/* 177 */       switch (this._eventType) {
/*     */         case 1:
/* 179 */           storeElementAndChildren(reader);
/*     */           continue;
/*     */         case 5:
/* 182 */           storeComment(reader);
/*     */           break;
/*     */         case 3:
/* 185 */           storeProcessingInstruction(reader);
/*     */           break;
/*     */       } 
/* 188 */       this._eventType = reader.next();
/*     */     } 
/*     */     
/* 191 */     storeStructure(144);
/*     */   }
/*     */   
/*     */   private void storeElementAndChildren(XMLStreamReader reader) throws XMLStreamException {
/* 195 */     if (reader instanceof XMLStreamReaderEx) {
/* 196 */       storeElementAndChildrenEx((XMLStreamReaderEx)reader);
/*     */     } else {
/* 198 */       storeElementAndChildrenNoEx(reader);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void storeElementAndChildrenEx(XMLStreamReaderEx reader) throws XMLStreamException {
/* 203 */     int depth = 1;
/* 204 */     if (this._storeInScopeNamespacesOnElementFragment) {
/* 205 */       storeElementWithInScopeNamespaces((XMLStreamReader)reader);
/*     */     } else {
/* 207 */       storeElement((XMLStreamReader)reader);
/*     */     } 
/*     */     
/* 210 */     while (depth > 0) {
/* 211 */       CharSequence c; this._eventType = reader.next();
/* 212 */       switch (this._eventType) {
/*     */         case 1:
/* 214 */           depth++;
/* 215 */           storeElement((XMLStreamReader)reader);
/*     */         
/*     */         case 2:
/* 218 */           depth--;
/* 219 */           storeStructure(144);
/*     */         
/*     */         case 13:
/* 222 */           storeNamespaceAttributes((XMLStreamReader)reader);
/*     */         
/*     */         case 10:
/* 225 */           storeAttributes((XMLStreamReader)reader);
/*     */         
/*     */         case 4:
/*     */         case 6:
/*     */         case 12:
/* 230 */           c = reader.getPCDATA();
/* 231 */           if (c instanceof Base64Data) {
/* 232 */             storeStructure(92);
/* 233 */             storeContentObject(((Base64Data)c).clone()); continue;
/*     */           } 
/* 235 */           storeContentCharacters(80, reader.getTextCharacters(), reader.getTextStart(), reader.getTextLength());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 5:
/* 242 */           storeComment((XMLStreamReader)reader);
/*     */         
/*     */         case 3:
/* 245 */           storeProcessingInstruction((XMLStreamReader)reader);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 254 */     this._eventType = reader.next();
/*     */   }
/*     */   
/*     */   private void storeElementAndChildrenNoEx(XMLStreamReader reader) throws XMLStreamException {
/* 258 */     int depth = 1;
/* 259 */     if (this._storeInScopeNamespacesOnElementFragment) {
/* 260 */       storeElementWithInScopeNamespaces(reader);
/*     */     } else {
/* 262 */       storeElement(reader);
/*     */     } 
/*     */     
/* 265 */     while (depth > 0) {
/* 266 */       this._eventType = reader.next();
/* 267 */       switch (this._eventType) {
/*     */         case 1:
/* 269 */           depth++;
/* 270 */           storeElement(reader);
/*     */         
/*     */         case 2:
/* 273 */           depth--;
/* 274 */           storeStructure(144);
/*     */         
/*     */         case 13:
/* 277 */           storeNamespaceAttributes(reader);
/*     */         
/*     */         case 10:
/* 280 */           storeAttributes(reader);
/*     */         
/*     */         case 4:
/*     */         case 6:
/*     */         case 12:
/* 285 */           storeContentCharacters(80, reader.getTextCharacters(), reader.getTextStart(), reader.getTextLength());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 5:
/* 291 */           storeComment(reader);
/*     */         
/*     */         case 3:
/* 294 */           storeProcessingInstruction(reader);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 303 */     this._eventType = reader.next();
/*     */   }
/*     */   
/*     */   private void storeElementWithInScopeNamespaces(XMLStreamReader reader) {
/* 307 */     storeQualifiedName(32, reader.getPrefix(), reader.getNamespaceURI(), reader.getLocalName());
/*     */ 
/*     */     
/* 310 */     if (reader.getNamespaceCount() > 0) {
/* 311 */       storeNamespaceAttributes(reader);
/*     */     }
/*     */     
/* 314 */     if (reader.getAttributeCount() > 0) {
/* 315 */       storeAttributes(reader);
/*     */     }
/*     */   }
/*     */   
/*     */   private void storeElement(XMLStreamReader reader) {
/* 320 */     storeQualifiedName(32, reader.getPrefix(), reader.getNamespaceURI(), reader.getLocalName());
/*     */ 
/*     */     
/* 323 */     if (reader.getNamespaceCount() > 0) {
/* 324 */       storeNamespaceAttributes(reader);
/*     */     }
/*     */     
/* 327 */     if (reader.getAttributeCount() > 0) {
/* 328 */       storeAttributes(reader);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeElement(String nsURI, String localName, String prefix, String[] ns) {
/* 350 */     storeQualifiedName(32, prefix, nsURI, localName);
/* 351 */     storeNamespaceAttributes(ns);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeEndElement() {
/* 362 */     storeStructure(144);
/*     */   }
/*     */   
/*     */   private void storeNamespaceAttributes(XMLStreamReader reader) {
/* 366 */     int count = reader.getNamespaceCount();
/* 367 */     for (int i = 0; i < count; i++) {
/* 368 */       storeNamespaceAttribute(reader.getNamespacePrefix(i), reader.getNamespaceURI(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void storeNamespaceAttributes(String[] ns) {
/* 376 */     for (int i = 0; i < ns.length; i += 2) {
/* 377 */       storeNamespaceAttribute(ns[i], ns[i + 1]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void storeAttributes(XMLStreamReader reader) {
/* 382 */     int count = reader.getAttributeCount();
/* 383 */     for (int i = 0; i < count; i++) {
/* 384 */       storeAttribute(reader.getAttributePrefix(i), reader.getAttributeNamespace(i), reader.getAttributeLocalName(i), reader.getAttributeType(i), reader.getAttributeValue(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void storeComment(XMLStreamReader reader) {
/* 390 */     storeContentCharacters(96, reader.getTextCharacters(), reader.getTextStart(), reader.getTextLength());
/*     */   }
/*     */ 
/*     */   
/*     */   private void storeProcessingInstruction(XMLStreamReader reader) {
/* 395 */     storeProcessingInstruction(reader.getPITarget(), reader.getPIData());
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\stream\buffer\stax\StreamReaderBufferCreator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */