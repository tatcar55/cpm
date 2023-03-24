/*     */ package com.sun.xml.ws.security.opt.impl.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.bind.api.BridgeContext;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElementWriter;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityHeaderElement;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.wss.impl.c14n.AttributeNS;
/*     */ import com.sun.xml.wss.impl.c14n.StAXAttr;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Header
/*     */   implements Header
/*     */ {
/*  81 */   private Header wrappedHeader = null;
/*  82 */   private SecurityHeaderElement she = null;
/*     */   
/*     */   private String localName;
/*     */   private String uri;
/*     */   private String prefix;
/*  87 */   private Vector attrList = new Vector();
/*  88 */   private Vector attrNSList = new Vector();
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean parsed = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public Header(Header header, SecurityHeaderElement she) {
/*  97 */     this.wrappedHeader = header;
/*  98 */     this.she = she;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIgnorable(@NotNull SOAPVersion soapVersion, @NotNull Set<String> roles) {
/* 143 */     return this.wrappedHeader.isIgnorable(soapVersion, roles);
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
/*     */   @NotNull
/*     */   public String getRole(@NotNull SOAPVersion soapVersion) {
/* 161 */     return this.wrappedHeader.getRole(soapVersion);
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
/*     */   public boolean isRelay() {
/* 182 */     return this.wrappedHeader.isRelay();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/* 192 */     if (!this.parsed) {
/*     */       try {
/* 194 */         parse();
/* 195 */       } catch (XMLStreamException ex) {
/* 196 */         ex.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 200 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getLocalPart() {
/* 210 */     if (!this.parsed) {
/*     */       try {
/* 212 */         parse();
/* 213 */       } catch (XMLStreamException ex) {
/* 214 */         ex.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 218 */     return this.localName;
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
/*     */   @Nullable
/*     */   public String getAttribute(@NotNull String nsUri, @NotNull String localName) {
/* 237 */     throw new UnsupportedOperationException();
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
/*     */   @Nullable
/*     */   public String getAttribute(@NotNull QName name) {
/* 252 */     throw new UnsupportedOperationException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 284 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T readAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 293 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T readAsJAXB(Bridge<T> bridge, BridgeContext context) throws JAXBException {
/* 301 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T readAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 308 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 319 */     if (!this.parsed) {
/* 320 */       parse();
/*     */     }
/* 322 */     writeStartElement(w);
/* 323 */     ((SecurityElementWriter)this.she).writeTo(w);
/* 324 */     writeEndElement(w);
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
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 340 */     throw new UnsupportedOperationException("use writeTo(XMLStreamWriter w) ");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 369 */     throw new UnsupportedOperationException("use writeTo(XMLStreamWriter w) ");
/*     */   }
/*     */   
/*     */   public String getStringContent() {
/* 373 */     throw new UnsupportedOperationException();
/*     */   } @NotNull
/*     */   public WSEndpointReference readAsEPR(AddressingVersion expected) throws XMLStreamException {
/* 376 */     throw new UnsupportedOperationException();
/*     */   }
/*     */   
/*     */   protected void parse() throws XMLStreamException {
/* 380 */     XMLStreamReader reader = this.wrappedHeader.readHeader();
/* 381 */     this.parsed = true;
/* 382 */     boolean stop = false;
/* 383 */     while (reader.hasNext()) {
/* 384 */       int count, i, eventType = reader.next();
/* 385 */       if (stop) {
/*     */         return;
/*     */       }
/* 388 */       switch (eventType) {
/*     */         case 1:
/* 390 */           this.localName = reader.getLocalName();
/* 391 */           this.uri = reader.getNamespaceURI();
/* 392 */           this.prefix = reader.getPrefix();
/* 393 */           if (this.prefix == null)
/* 394 */             this.prefix = ""; 
/* 395 */           count = reader.getAttributeCount();
/* 396 */           for (i = 0; i < count; i++) {
/* 397 */             String localName = reader.getAttributeLocalName(i);
/* 398 */             String uri = reader.getAttributeNamespace(i);
/* 399 */             String prefix = reader.getAttributePrefix(i);
/* 400 */             if (prefix == null)
/* 401 */               prefix = ""; 
/* 402 */             String value = reader.getAttributeValue(i);
/* 403 */             StAXAttr attr = new StAXAttr();
/* 404 */             attr.setLocalName(localName);
/* 405 */             attr.setValue(value);
/* 406 */             attr.setPrefix(prefix);
/* 407 */             attr.setUri(uri);
/* 408 */             this.attrList.add(attr);
/*     */           } 
/*     */           
/* 411 */           count = 0;
/* 412 */           count = reader.getNamespaceCount();
/* 413 */           for (i = 0; i < count; i++) {
/* 414 */             String prefix = reader.getNamespacePrefix(i);
/* 415 */             if (prefix == null)
/* 416 */               prefix = ""; 
/* 417 */             String uri = reader.getNamespaceURI(i);
/* 418 */             AttributeNS attrNS = new AttributeNS();
/* 419 */             attrNS.setPrefix(prefix);
/* 420 */             attrNS.setUri(uri);
/* 421 */             this.attrNSList.add(attrNS);
/*     */           } 
/* 423 */           stop = true;
/*     */ 
/*     */         
/*     */         case 2:
/* 427 */           stop = true;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeEndElement(XMLStreamWriter xsw) throws XMLStreamException {
/* 436 */     xsw.writeEndElement();
/*     */   }
/*     */   
/*     */   private void writeStartElement(XMLStreamWriter xsw) throws XMLStreamException {
/* 440 */     xsw.writeStartElement(this.prefix, this.localName, this.uri); int i;
/* 441 */     for (i = 0; i < this.attrNSList.size(); i++) {
/* 442 */       AttributeNS attrNs = this.attrNSList.get(i);
/* 443 */       xsw.writeNamespace(attrNs.getPrefix(), attrNs.getUri());
/*     */     } 
/* 445 */     for (i = 0; i < this.attrList.size(); i++) {
/* 446 */       StAXAttr attr = this.attrList.get(i);
/* 447 */       xsw.writeAttribute(attr.getPrefix(), attr.getUri(), attr.getLocalName(), attr.getValue());
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(XMLBridge<T> arg0) throws JAXBException {
/* 452 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\message\Header.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */