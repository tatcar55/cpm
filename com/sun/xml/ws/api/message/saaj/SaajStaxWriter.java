/*     */ package com.sun.xml.ws.api.message.saaj;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.xml.namespace.NamespaceContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.w3c.dom.Comment;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SaajStaxWriter
/*     */   implements XMLStreamWriter
/*     */ {
/*     */   protected SOAPMessage soap;
/*     */   protected String envURI;
/*     */   protected SOAPElement currentElement;
/*     */   protected static final String Envelope = "Envelope";
/*     */   protected static final String Header = "Header";
/*     */   protected static final String Body = "Body";
/*     */   protected static final String xmlns = "xmlns";
/*     */   
/*     */   public SaajStaxWriter(SOAPMessage msg) throws SOAPException {
/*  74 */     this.soap = msg;
/*  75 */     this.currentElement = this.soap.getSOAPPart().getEnvelope();
/*  76 */     this.envURI = this.currentElement.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public SOAPMessage getSOAPMessage() {
/*  80 */     return this.soap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartElement(String localName) throws XMLStreamException {
/*     */     try {
/*  86 */       this.currentElement = this.currentElement.addChildElement(localName);
/*  87 */     } catch (SOAPException e) {
/*  88 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartElement(String ns, String ln) throws XMLStreamException {
/*  94 */     writeStartElement(null, ln, ns);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeStartElement(String prefix, String ln, String ns) throws XMLStreamException {
/*     */     try {
/* 100 */       if (this.envURI.equals(ns)) {
/* 101 */         if ("Envelope".equals(ln)) {
/* 102 */           this.currentElement = this.soap.getSOAPPart().getEnvelope();
/* 103 */           fixPrefix(prefix); return;
/*     */         } 
/* 105 */         if ("Header".equals(ln)) {
/* 106 */           this.currentElement = this.soap.getSOAPHeader();
/* 107 */           fixPrefix(prefix); return;
/*     */         } 
/* 109 */         if ("Body".equals(ln)) {
/* 110 */           this.currentElement = this.soap.getSOAPBody();
/* 111 */           fixPrefix(prefix);
/*     */           return;
/*     */         } 
/*     */       } 
/* 115 */       this.currentElement = (prefix == null) ? this.currentElement.addChildElement(new QName(ns, ln)) : this.currentElement.addChildElement(ln, prefix, ns);
/*     */     
/*     */     }
/* 118 */     catch (SOAPException e) {
/* 119 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fixPrefix(String prfx) throws XMLStreamException {
/* 124 */     String oldPrfx = this.currentElement.getPrefix();
/* 125 */     if (prfx != null && !prfx.equals(oldPrfx)) {
/* 126 */       this.currentElement.setPrefix(prfx);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String uri, String ln) throws XMLStreamException {
/* 132 */     writeStartElement(null, ln, uri);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String prefix, String ln, String uri) throws XMLStreamException {
/* 137 */     writeStartElement(prefix, ln, uri);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEmptyElement(String ln) throws XMLStreamException {
/* 142 */     writeStartElement(null, ln, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEndElement() throws XMLStreamException {
/* 147 */     if (this.currentElement != null) this.currentElement = this.currentElement.getParentElement();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEndDocument() throws XMLStreamException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws XMLStreamException {}
/*     */ 
/*     */   
/*     */   public void flush() throws XMLStreamException {}
/*     */ 
/*     */   
/*     */   public void writeAttribute(String ln, String val) throws XMLStreamException {
/* 164 */     writeAttribute(null, null, ln, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeAttribute(String prefix, String ns, String ln, String value) throws XMLStreamException {
/*     */     try {
/* 170 */       if (ns == null) {
/* 171 */         if (prefix == null && "xmlns".equals(ln)) {
/* 172 */           this.currentElement.addNamespaceDeclaration("", value);
/*     */         } else {
/* 174 */           this.currentElement.setAttributeNS("", ln, value);
/*     */         } 
/*     */       } else {
/* 177 */         QName name = (prefix == null) ? new QName(ns, ln) : new QName(ns, ln, prefix);
/* 178 */         this.currentElement.addAttribute(name, value);
/*     */       } 
/* 180 */     } catch (SOAPException e) {
/* 181 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeAttribute(String ns, String ln, String val) throws XMLStreamException {
/* 187 */     writeAttribute(null, ns, ln, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeNamespace(String prefix, String uri) throws XMLStreamException {
/*     */     try {
/* 193 */       this.currentElement.addNamespaceDeclaration(prefix, uri);
/* 194 */     } catch (SOAPException e) {
/* 195 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeDefaultNamespace(String uri) throws XMLStreamException {
/* 201 */     writeNamespace("", uri);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeComment(String data) throws XMLStreamException {
/* 206 */     Comment c = this.soap.getSOAPPart().createComment(data);
/* 207 */     this.currentElement.appendChild(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeProcessingInstruction(String target) throws XMLStreamException {
/* 212 */     Node n = this.soap.getSOAPPart().createProcessingInstruction(target, "");
/* 213 */     this.currentElement.appendChild(n);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
/* 218 */     Node n = this.soap.getSOAPPart().createProcessingInstruction(target, data);
/* 219 */     this.currentElement.appendChild(n);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCData(String data) throws XMLStreamException {
/* 224 */     Node n = this.soap.getSOAPPart().createCDATASection(data);
/* 225 */     this.currentElement.appendChild(n);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeDTD(String dtd) throws XMLStreamException {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityRef(String name) throws XMLStreamException {
/* 235 */     Node n = this.soap.getSOAPPart().createEntityReference(name);
/* 236 */     this.currentElement.appendChild(n);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeStartDocument() throws XMLStreamException {}
/*     */ 
/*     */   
/*     */   public void writeStartDocument(String version) throws XMLStreamException {
/* 245 */     if (version != null) this.soap.getSOAPPart().setXmlVersion(version);
/*     */   
/*     */   }
/*     */   
/*     */   public void writeStartDocument(String encoding, String version) throws XMLStreamException {
/* 250 */     if (version != null) this.soap.getSOAPPart().setXmlVersion(version); 
/* 251 */     if (encoding != null) {
/*     */       try {
/* 253 */         this.soap.setProperty("javax.xml.soap.character-set-encoding", encoding);
/* 254 */       } catch (SOAPException e) {
/* 255 */         throw new XMLStreamException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCharacters(String text) throws XMLStreamException {
/*     */     try {
/* 263 */       this.currentElement.addTextNode(text);
/* 264 */     } catch (SOAPException e) {
/* 265 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
/* 271 */     char[] chr = (start == 0 && len == text.length) ? text : Arrays.copyOfRange(text, start, start + len);
/*     */     try {
/* 273 */       this.currentElement.addTextNode(new String(chr));
/* 274 */     } catch (SOAPException e) {
/* 275 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix(String uri) throws XMLStreamException {
/* 281 */     return this.currentElement.lookupPrefix(uri);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPrefix(String prefix, String uri) throws XMLStreamException {
/*     */     try {
/* 287 */       this.currentElement.addNamespaceDeclaration(prefix, uri);
/* 288 */     } catch (SOAPException e) {
/* 289 */       throw new XMLStreamException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDefaultNamespace(String uri) throws XMLStreamException {
/* 295 */     setPrefix("", uri);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
/* 300 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getProperty(String name) throws IllegalArgumentException {
/* 306 */     if ("javax.xml.stream.isRepairingNamespaces".equals(name)) return Boolean.FALSE; 
/* 307 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public NamespaceContext getNamespaceContext() {
/* 312 */     return new NamespaceContext() {
/*     */         public String getNamespaceURI(String prefix) {
/* 314 */           return SaajStaxWriter.this.currentElement.getNamespaceURI(prefix);
/*     */         }
/*     */         public String getPrefix(String namespaceURI) {
/* 317 */           return SaajStaxWriter.this.currentElement.lookupPrefix(namespaceURI);
/*     */         }
/*     */         public Iterator getPrefixes(final String namespaceURI) {
/* 320 */           return new Iterator() {
/* 321 */               String prefix = SaajStaxWriter.null.this.getPrefix(namespaceURI);
/*     */               public boolean hasNext() {
/* 323 */                 return (this.prefix != null);
/*     */               }
/*     */               public Object next() {
/* 326 */                 if (!hasNext()) throw new NoSuchElementException(); 
/* 327 */                 String next = this.prefix;
/* 328 */                 this.prefix = null;
/* 329 */                 return next;
/*     */               }
/*     */               
/*     */               public void remove() {}
/*     */             };
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\saaj\SaajStaxWriter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */