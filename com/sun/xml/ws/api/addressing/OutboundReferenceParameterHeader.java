/*     */ package com.sun.xml.ws.api.addressing;
/*     */ 
/*     */ import com.sun.istack.FinalArrayList;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferException;
/*     */ import com.sun.xml.ws.message.AbstractHeaderImpl;
/*     */ import com.sun.xml.ws.util.xml.XMLStreamWriterFilter;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.stream.util.StreamReaderDelegate;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Element;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ import org.xml.sax.helpers.XMLFilterImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class OutboundReferenceParameterHeader
/*     */   extends AbstractHeaderImpl
/*     */ {
/*     */   private final XMLStreamBuffer infoset;
/*     */   private final String nsUri;
/*     */   private final String localName;
/*     */   private FinalArrayList<Attribute> attributes;
/*     */   private static final String TRUE_VALUE = "1";
/*     */   private static final String IS_REFERENCE_PARAMETER = "IsReferenceParameter";
/*     */   
/*     */   OutboundReferenceParameterHeader(XMLStreamBuffer infoset, String nsUri, String localName) {
/*  93 */     this.infoset = infoset;
/*  94 */     this.nsUri = nsUri;
/*  95 */     this.localName = localName;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/* 100 */     return this.nsUri;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getLocalPart() {
/* 105 */     return this.localName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 110 */     if (this.attributes == null) {
/* 111 */       parseAttributes();
/*     */     }
/* 113 */     for (int i = this.attributes.size() - 1; i >= 0; i--) {
/* 114 */       Attribute a = (Attribute)this.attributes.get(i);
/* 115 */       if (a.localName.equals(localName) && a.nsUri.equals(nsUri)) {
/* 116 */         return a.value;
/*     */       }
/*     */     } 
/* 119 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parseAttributes() {
/*     */     try {
/* 130 */       XMLStreamReader reader = readHeader();
/* 131 */       reader.nextTag();
/*     */       
/* 133 */       this.attributes = new FinalArrayList();
/* 134 */       boolean refParamAttrWritten = false;
/* 135 */       for (int i = 0; i < reader.getAttributeCount(); i++) {
/* 136 */         String attrLocalName = reader.getAttributeLocalName(i);
/* 137 */         String namespaceURI = reader.getAttributeNamespace(i);
/* 138 */         String value = reader.getAttributeValue(i);
/* 139 */         if (namespaceURI.equals(AddressingVersion.W3C.nsUri) && attrLocalName.equals("IS_REFERENCE_PARAMETER")) {
/* 140 */           refParamAttrWritten = true;
/*     */         }
/* 142 */         this.attributes.add(new Attribute(namespaceURI, attrLocalName, value));
/*     */       } 
/*     */       
/* 145 */       if (!refParamAttrWritten) {
/* 146 */         this.attributes.add(new Attribute(AddressingVersion.W3C.nsUri, "IsReferenceParameter", "1"));
/*     */       }
/* 148 */     } catch (XMLStreamException e) {
/* 149 */       throw new WebServiceException("Unable to read the attributes for {" + this.nsUri + "}" + this.localName + " header", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 155 */     return new StreamReaderDelegate((XMLStreamReader)this.infoset.readAsXMLStreamReader()) {
/* 156 */         int state = 0;
/*     */         
/*     */         public int next() throws XMLStreamException {
/* 159 */           return check(super.next());
/*     */         }
/*     */ 
/*     */         
/*     */         public int nextTag() throws XMLStreamException {
/* 164 */           return check(super.nextTag());
/*     */         }
/*     */         
/*     */         private int check(int type) {
/* 168 */           switch (this.state) {
/*     */             case 0:
/* 170 */               if (type == 1) {
/* 171 */                 this.state = 1;
/*     */               }
/*     */               break;
/*     */             case 1:
/* 175 */               this.state = 2;
/*     */               break;
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 181 */           return type;
/*     */         }
/*     */ 
/*     */         
/*     */         public int getAttributeCount() {
/* 186 */           if (this.state == 1) {
/* 187 */             return super.getAttributeCount() + 1;
/*     */           }
/* 189 */           return super.getAttributeCount();
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public String getAttributeLocalName(int index) {
/* 195 */           if (this.state == 1 && index == super.getAttributeCount()) {
/* 196 */             return "IsReferenceParameter";
/*     */           }
/* 198 */           return super.getAttributeLocalName(index);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public String getAttributeNamespace(int index) {
/* 204 */           if (this.state == 1 && index == super.getAttributeCount()) {
/* 205 */             return AddressingVersion.W3C.nsUri;
/*     */           }
/*     */           
/* 208 */           return super.getAttributeNamespace(index);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public String getAttributePrefix(int index) {
/* 214 */           if (this.state == 1 && index == super.getAttributeCount()) {
/* 215 */             return "wsa";
/*     */           }
/* 217 */           return super.getAttributePrefix(index);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public String getAttributeType(int index) {
/* 223 */           if (this.state == 1 && index == super.getAttributeCount()) {
/* 224 */             return "CDATA";
/*     */           }
/* 226 */           return super.getAttributeType(index);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public String getAttributeValue(int index) {
/* 232 */           if (this.state == 1 && index == super.getAttributeCount()) {
/* 233 */             return "1";
/*     */           }
/* 235 */           return super.getAttributeValue(index);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public QName getAttributeName(int index) {
/* 241 */           if (this.state == 1 && index == super.getAttributeCount()) {
/* 242 */             return new QName(AddressingVersion.W3C.nsUri, "IsReferenceParameter", "wsa");
/*     */           }
/* 244 */           return super.getAttributeName(index);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public String getAttributeValue(String namespaceUri, String localName) {
/* 250 */           if (this.state == 1 && localName.equals("IsReferenceParameter") && namespaceUri.equals(AddressingVersion.W3C.nsUri)) {
/* 251 */             return "1";
/*     */           }
/* 253 */           return super.getAttributeValue(namespaceUri, localName);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 261 */     this.infoset.writeToXMLStreamWriter((XMLStreamWriter)new XMLStreamWriterFilter(w) {
/*     */           private boolean root = true;
/*     */           private boolean onRootEl = true;
/*     */           
/*     */           public void writeStartElement(String localName) throws XMLStreamException {
/* 266 */             super.writeStartElement(localName);
/* 267 */             writeAddedAttribute();
/*     */           }
/*     */           
/*     */           private void writeAddedAttribute() throws XMLStreamException {
/* 271 */             if (!this.root) {
/* 272 */               this.onRootEl = false;
/*     */               return;
/*     */             } 
/* 275 */             this.root = false;
/* 276 */             writeNamespace("wsa", AddressingVersion.W3C.nsUri);
/* 277 */             super.writeAttribute("wsa", AddressingVersion.W3C.nsUri, "IsReferenceParameter", "1");
/*     */           }
/*     */ 
/*     */           
/*     */           public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
/* 282 */             super.writeStartElement(namespaceURI, localName);
/* 283 */             writeAddedAttribute();
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
/* 290 */             boolean prefixDeclared = isPrefixDeclared(prefix, namespaceURI);
/* 291 */             super.writeStartElement(prefix, localName, namespaceURI);
/* 292 */             if (!prefixDeclared && !prefix.equals("")) {
/* 293 */               super.writeNamespace(prefix, namespaceURI);
/*     */             }
/* 295 */             writeAddedAttribute();
/*     */           }
/*     */           
/*     */           public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
/* 299 */             if (!isPrefixDeclared(prefix, namespaceURI)) {
/* 300 */               super.writeNamespace(prefix, namespaceURI);
/*     */             }
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
/* 307 */             if (this.onRootEl && namespaceURI.equals(AddressingVersion.W3C.nsUri) && localName.equals("IsReferenceParameter")) {
/*     */               return;
/*     */             }
/*     */             
/* 311 */             this.writer.writeAttribute(prefix, namespaceURI, localName, value);
/*     */           }
/*     */ 
/*     */           
/*     */           public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
/* 316 */             this.writer.writeAttribute(namespaceURI, localName, value);
/*     */           }
/*     */           private boolean isPrefixDeclared(String prefix, String namespaceURI) {
/* 319 */             return namespaceURI.equals(getNamespaceContext().getNamespaceURI(prefix));
/*     */           }
/*     */         }true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/*     */     try {
/* 330 */       SOAPHeader header = saaj.getSOAPHeader();
/* 331 */       if (header == null) {
/* 332 */         header = saaj.getSOAPPart().getEnvelope().addHeader();
/*     */       }
/* 334 */       Element node = (Element)this.infoset.writeTo(header);
/* 335 */       node.setAttributeNS(AddressingVersion.W3C.nsUri, AddressingVersion.W3C.getPrefix() + ":" + "IsReferenceParameter", "1");
/* 336 */     } catch (XMLStreamBufferException e) {
/* 337 */       throw new SOAPException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/*     */     class Filter extends XMLFilterImpl { private int depth;
/*     */       
/*     */       Filter(ContentHandler ch) {
/* 345 */         this.depth = 0;
/*     */         setContentHandler(ch);
/*     */       } public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
/* 348 */         if (this.depth++ == 0) {
/*     */           
/* 350 */           startPrefixMapping("wsa", AddressingVersion.W3C.nsUri);
/*     */ 
/*     */           
/* 353 */           if (atts.getIndex(AddressingVersion.W3C.nsUri, "IsReferenceParameter") == -1) {
/* 354 */             AttributesImpl atts2 = new AttributesImpl(atts);
/* 355 */             atts2.addAttribute(AddressingVersion.W3C.nsUri, "IsReferenceParameter", "wsa:IsReferenceParameter", "CDATA", "1");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 361 */             atts = atts2;
/*     */           } 
/*     */         } 
/*     */         
/* 365 */         super.startElement(uri, localName, qName, atts);
/*     */       }
/*     */ 
/*     */       
/*     */       public void endElement(String uri, String localName, String qName) throws SAXException {
/* 370 */         super.endElement(uri, localName, qName);
/* 371 */         if (--this.depth == 0) {
/* 372 */           endPrefixMapping("wsa");
/*     */         }
/*     */       } }
/*     */     ;
/*     */     
/* 377 */     this.infoset.writeTo(new Filter(contentHandler), errorHandler);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static final class Attribute
/*     */   {
/*     */     final String nsUri;
/*     */ 
/*     */     
/*     */     final String localName;
/*     */     
/*     */     final String value;
/*     */ 
/*     */     
/*     */     public Attribute(String nsUri, String localName, String value) {
/* 393 */       this.nsUri = fixNull(nsUri);
/* 394 */       this.localName = localName;
/* 395 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static String fixNull(String s) {
/* 402 */       return (s == null) ? "" : s;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\addressing\OutboundReferenceParameterHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */