/*     */ package com.sun.xml.ws.security.opt.impl.dsig;
/*     */ 
/*     */ import com.sun.xml.ws.security.opt.crypto.JAXBData;
/*     */ import com.sun.xml.ws.security.opt.crypto.StreamWriterData;
/*     */ import com.sun.xml.ws.security.opt.impl.crypto.OctectStreamData;
/*     */ import com.sun.xml.wss.XWSSecurityException;
/*     */ import com.sun.xml.wss.impl.c14n.StAXEXC14nCanonicalizerImpl;
/*     */ import com.sun.xml.wss.logging.impl.opt.signature.LogStringsMessages;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.crypto.Data;
/*     */ import javax.xml.crypto.URIDereferencer;
/*     */ import javax.xml.crypto.URIReference;
/*     */ import javax.xml.crypto.URIReferenceException;
/*     */ import javax.xml.crypto.XMLCryptoContext;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.jvnet.staxex.NamespaceContextEx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StAXSTRTransformWriter
/*     */   implements XMLStreamWriter, StreamWriterData
/*     */ {
/*  70 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt.signature", "com.sun.xml.wss.logging.impl.opt.signature.LogStrings");
/*     */ 
/*     */   
/*  73 */   private XMLStreamWriter nextWriter = null;
/*     */   
/*     */   private boolean ignore = false;
/*     */   private boolean derefSAMLKeyIdentifier = false;
/*  77 */   private Data data = null;
/*  78 */   private int index = 0;
/*  79 */   private NamespaceContextEx ns = null;
/*     */   private boolean directReference = false;
/*     */   private boolean first = true;
/*  82 */   private String directReferenceValue = "";
/*     */   private XMLCryptoContext xMLCryptoContext;
/*  84 */   private String strId = "";
/*     */ 
/*     */   
/*     */   public StAXSTRTransformWriter(XMLStreamWriter writer, Data data, XMLCryptoContext xMLCryptoContext) {
/*  88 */     this.nextWriter = writer;
/*  89 */     this.data = data;
/*  90 */     if (data instanceof JAXBData) {
/*  91 */       this.ns = ((JAXBData)data).getNamespaceContext();
/*  92 */     } else if (data instanceof StreamWriterData) {
/*  93 */       this.ns = ((StreamWriterData)data).getNamespaceContext();
/*     */     } 
/*  95 */     this.xMLCryptoContext = xMLCryptoContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public StAXSTRTransformWriter(Data data, XMLCryptoContext xMLCryptoContext, String refId) {
/* 100 */     this.data = data;
/* 101 */     if (data instanceof JAXBData) {
/* 102 */       this.ns = ((JAXBData)data).getNamespaceContext();
/* 103 */     } else if (data instanceof StreamWriterData) {
/* 104 */       this.ns = ((StreamWriterData)data).getNamespaceContext();
/*     */     } 
/* 106 */     this.xMLCryptoContext = xMLCryptoContext;
/* 107 */     this.strId = refId;
/*     */   }
/*     */ 
/*     */   
/*     */   public NamespaceContextEx getNamespaceContext() {
/* 112 */     return this.ns;
/*     */   }
/*     */   
/*     */   public void close() throws XMLStreamException {
/* 116 */     this.nextWriter.close();
/*     */   }
/*     */   
/*     */   public void flush() throws XMLStreamException {
/* 120 */     this.nextWriter.flush();
/*     */   }
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {
/* 124 */     if (this.index > 0) {
/* 125 */       int size = this.index;
/* 126 */       for (int i = 0; i < size; i++) {
/* 127 */         writeEndElement();
/*     */       }
/*     */     } 
/* 130 */     this.nextWriter.writeEndDocument();
/*     */   }
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/* 134 */     if (this.index == 1 && !this.ignore) {
/* 135 */       this.nextWriter.writeEndElement();
/*     */     }
/* 137 */     if (this.index > 0) {
/* 138 */       this.index--;
/*     */     }
/* 140 */     if (this.index == 0) {
/* 141 */       if (this.ignore) {
/* 142 */         this.ignore = false;
/* 143 */         derefernceSTR();
/*     */       } 
/*     */       
/* 146 */       this.nextWriter.writeEndElement();
/* 147 */       if (this.derefSAMLKeyIdentifier) {
/* 148 */         this.derefSAMLKeyIdentifier = false;
/*     */       }
/* 150 */       if (this.directReference) {
/* 151 */         this.directReference = false;
/*     */       }
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {
/* 159 */     if (!this.ignore) {
/* 160 */       this.nextWriter.writeStartDocument();
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeCharacters(char[] c, int index, int len) throws XMLStreamException {
/* 165 */     if (!this.ignore) {
/* 166 */       this.nextWriter.writeCharacters(c, index, len);
/*     */     }
/* 168 */     else if (this.derefSAMLKeyIdentifier) {
/* 169 */       this.strId = String.valueOf(c, index, len);
/* 170 */       if (this.strId == null) {
/* 171 */         throw new XMLStreamException("SAML Key Identifier is empty in SecurityTokenReference");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultNamespace(String string) throws XMLStreamException {
/* 178 */     if (!this.ignore) {
/* 179 */       this.nextWriter.setDefaultNamespace(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeCData(String string) throws XMLStreamException {
/* 184 */     if (!this.ignore) {
/* 185 */       this.nextWriter.writeCData(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeCharacters(String string) throws XMLStreamException {
/* 190 */     if (!this.ignore) {
/* 191 */       this.nextWriter.writeCharacters(string);
/*     */     }
/* 193 */     else if (this.derefSAMLKeyIdentifier) {
/* 194 */       this.strId = string;
/* 195 */       if (this.strId == null) {
/* 196 */         throw new XMLStreamException("SAML Key Identifier is empty in SecurityTokenReference");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeComment(String string) throws XMLStreamException {
/* 203 */     if (!this.ignore) {
/* 204 */       this.nextWriter.writeComment(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeDTD(String string) throws XMLStreamException {
/* 209 */     if (!this.ignore) {
/* 210 */       this.nextWriter.writeDTD(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeDefaultNamespace(String string) throws XMLStreamException {
/* 215 */     if (!this.ignore) {
/* 216 */       this.nextWriter.writeDefaultNamespace(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeEmptyElement(String string) throws XMLStreamException {
/* 221 */     if (!this.ignore) {
/* 222 */       this.nextWriter.writeEmptyElement(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeEntityRef(String string) throws XMLStreamException {
/* 227 */     if (!this.ignore) {
/* 228 */       this.nextWriter.writeEntityRef(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String string) throws XMLStreamException {
/* 233 */     if (!this.ignore) {
/* 234 */       this.nextWriter.writeProcessingInstruction(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String string) throws XMLStreamException {
/* 239 */     if (!this.ignore) {
/* 240 */       this.nextWriter.writeStartDocument(string);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeStartElement(String string) throws XMLStreamException {
/* 245 */     if (!this.ignore) {
/* 246 */       this.nextWriter.writeStartElement(string);
/*     */     }
/* 248 */     this.first = false;
/*     */   }
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext namespaceContext) throws XMLStreamException {
/* 252 */     if (!this.ignore) {
/* 253 */       this.nextWriter.setNamespaceContext(namespaceContext);
/*     */     }
/*     */   }
/*     */   
/*     */   public Object getProperty(String string) throws IllegalArgumentException {
/* 258 */     return this.nextWriter.getProperty(string);
/*     */   }
/*     */   
/*     */   public String getPrefix(String string) throws XMLStreamException {
/* 262 */     return this.nextWriter.getPrefix(string);
/*     */   }
/*     */   
/*     */   public void setPrefix(String string, String string0) throws XMLStreamException {
/* 266 */     if (!this.ignore) {
/* 267 */       this.nextWriter.setPrefix(string, string0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeAttribute(String localname, String value) throws XMLStreamException {
/* 272 */     if (!this.ignore) {
/* 273 */       this.nextWriter.writeAttribute(localname, value);
/*     */     }
/* 275 */     else if (this.directReference) {
/* 276 */       if (localname == "URI") {
/* 277 */         this.directReferenceValue = value;
/*     */       }
/* 279 */     } else if ("http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.0#SAMLAssertionID".equals(value) || "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLID".equals(value)) {
/*     */       
/* 281 */       this.derefSAMLKeyIdentifier = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String string, String string0) throws XMLStreamException {
/* 287 */     if (!this.ignore) {
/* 288 */       this.nextWriter.writeEmptyElement(string, string0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeNamespace(String string, String string0) throws XMLStreamException {
/* 293 */     if (!this.ignore) {
/* 294 */       this.nextWriter.writeNamespace(string, string0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeProcessingInstruction(String string, String string0) throws XMLStreamException {
/* 299 */     if (!this.ignore) {
/* 300 */       this.nextWriter.writeProcessingInstruction(string, string0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String string, String string0) throws XMLStreamException {
/* 305 */     if (!this.ignore) {
/* 306 */       this.nextWriter.writeStartDocument(string, string0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 311 */     if (!this.ignore) {
/* 312 */       if (this.first && localName == "SecurityTokenReference" && namespaceURI == "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") {
/* 313 */         this.ignore = true;
/* 314 */         this.index++;
/*     */         return;
/*     */       } 
/* 317 */       this.nextWriter.writeStartElement(namespaceURI, localName);
/*     */     } else {
/* 319 */       this.index++;
/*     */     } 
/* 321 */     this.first = false;
/*     */   }
/*     */   
/*     */   public void writeAttribute(String uri, String localname, String value) throws XMLStreamException {
/* 325 */     if (!this.ignore) {
/* 326 */       this.nextWriter.writeAttribute(uri, localname, value);
/*     */     }
/* 328 */     else if (this.directReference && 
/* 329 */       localname == "URI") {
/* 330 */       this.directReferenceValue = value;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String string, String string0, String string1) throws XMLStreamException {
/* 337 */     if (!this.ignore) {
/* 338 */       this.nextWriter.writeEmptyElement(string, string0, string1);
/*     */     }
/*     */   }
/*     */   
/*     */   public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 343 */     if (!this.ignore) {
/*     */       
/* 345 */       if (this.first && localName == "SecurityTokenReference" && namespaceURI == "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") {
/* 346 */         this.ignore = true;
/* 347 */         this.index++; return;
/*     */       } 
/* 349 */       if (this.first && localName == "Reference" && namespaceURI == "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") {
/* 350 */         this.ignore = true;
/* 351 */         this.index++;
/* 352 */         this.directReference = true;
/* 353 */         this.nextWriter.writeNamespace(prefix, namespaceURI); return;
/*     */       } 
/* 355 */       if (this.first && localName == "KeyIdentifier" && namespaceURI == "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") {
/* 356 */         this.ignore = true;
/* 357 */         this.index++;
/* 358 */         this.nextWriter.writeNamespace(prefix, namespaceURI);
/*     */       } else {
/* 360 */         this.nextWriter.writeStartElement(prefix, localName, namespaceURI);
/*     */       }
/*     */     
/* 363 */     } else if (localName == "Reference" && namespaceURI == "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") {
/* 364 */       this.index++;
/* 365 */       this.directReference = true;
/* 366 */     } else if (localName == "KeyIdentifier" && namespaceURI == "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd") {
/* 367 */       this.index++;
/* 368 */       this.nextWriter.writeNamespace(prefix, namespaceURI);
/*     */     } else {
/* 370 */       this.nextWriter.writeStartElement(prefix, localName, namespaceURI);
/*     */     } 
/*     */     
/* 373 */     this.first = false;
/*     */   }
/*     */   
/*     */   public void writeAttribute(String prefix, String uri, String localName, String value) throws XMLStreamException {
/* 377 */     if (!this.ignore) {
/* 378 */       this.nextWriter.writeNamespace(prefix, uri);
/* 379 */       this.nextWriter.writeAttribute(prefix, uri, localName, value);
/*     */     }
/* 381 */     else if (this.directReference && 
/* 382 */       localName == "URI") {
/* 383 */       this.directReferenceValue = value;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write(XMLStreamWriter writer) throws XMLStreamException {
/* 394 */     this.nextWriter = writer;
/* 395 */     if (this.nextWriter instanceof StAXEXC14nCanonicalizerImpl) {
/* 396 */       ((StAXEXC14nCanonicalizerImpl)this.nextWriter).forceDefaultNS(true);
/*     */     }
/* 398 */     if (this.data instanceof JAXBData) {
/*     */       try {
/* 400 */         ((JAXBData)this.data).writeTo(this);
/* 401 */         NamespaceContextEx nc = ((JAXBData)this.data).getNamespaceContext();
/* 402 */         Iterator<NamespaceContextEx.Binding> itr = nc.iterator();
/* 403 */         while (itr.hasNext()) {
/* 404 */           NamespaceContextEx.Binding nd = itr.next();
/* 405 */           this.nextWriter.writeNamespace(nd.getPrefix(), nd.getNamespaceURI());
/*     */         } 
/* 407 */       } catch (XWSSecurityException ex) {
/* 408 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1706_ERROR_ENVELOPED_SIGNATURE(), (Throwable)ex);
/* 409 */         throw new XMLStreamException("Error occurred while performing Enveloped Signature");
/*     */       } 
/* 411 */     } else if (this.data instanceof StreamWriterData) {
/* 412 */       StreamWriterData swd = (StreamWriterData)this.data;
/* 413 */       NamespaceContextEx nc = swd.getNamespaceContext();
/* 414 */       Iterator<NamespaceContextEx.Binding> itr = nc.iterator();
/*     */       
/* 416 */       while (itr.hasNext()) {
/* 417 */         NamespaceContextEx.Binding nd = itr.next();
/* 418 */         this.nextWriter.writeNamespace(nd.getPrefix(), nd.getNamespaceURI());
/*     */       } 
/* 420 */       ((StreamWriterData)this.data).write(this);
/* 421 */     } else if (this.data instanceof OctectStreamData) {
/* 422 */       ((OctectStreamData)this.data).write(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void derefernceSTR() throws XMLStreamException {
/*     */     final String uri;
/* 430 */     Data token = null;
/* 431 */     URIDereferencer deRef = this.xMLCryptoContext.getURIDereferencer();
/*     */     
/* 433 */     if (this.directReference) {
/* 434 */       uri = this.directReferenceValue;
/* 435 */     } else if (this.strId != null && this.strId.length() > 0) {
/* 436 */       uri = this.strId;
/*     */     } else {
/* 438 */       uri = "";
/*     */     } 
/*     */     
/* 441 */     URIReference ref = new URIReference() {
/*     */         public String getType() {
/* 443 */           return "";
/*     */         }
/*     */         public String getURI() {
/* 446 */           return uri;
/*     */         }
/*     */       };
/*     */     
/*     */     try {
/* 451 */       token = deRef.dereference(ref, this.xMLCryptoContext);
/* 452 */     } catch (URIReferenceException ue) {
/* 453 */       logger.log(Level.SEVERE, LogStringsMessages.WSS_1716_ERROR_DEREFERENCE_STR_TRANSFORM(), ue);
/* 454 */       throw new XMLStreamException("Error occurred while dereferencing STR-Transform's Reference Element", ue);
/*     */     } 
/*     */     
/* 457 */     if (token != null)
/* 458 */       if (token instanceof JAXBData) {
/*     */         try {
/* 460 */           ((JAXBData)token).writeTo(this);
/* 461 */         } catch (XWSSecurityException ex) {
/* 462 */           logger.log(Level.SEVERE, LogStringsMessages.WSS_1706_ERROR_ENVELOPED_SIGNATURE(), (Throwable)ex);
/* 463 */           throw new XMLStreamException("Error occurred while performing Enveloped Signature");
/*     */         } 
/* 465 */       } else if (token instanceof StreamWriterData) {
/* 466 */         ((StreamWriterData)token).write(this);
/* 467 */       } else if (token instanceof OctectStreamData) {
/* 468 */         ((OctectStreamData)token).write(this);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\dsig\StAXSTRTransformWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */