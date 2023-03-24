/*     */ package com.sun.xml.ws.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPHeaderElement;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StringHeader
/*     */   extends AbstractHeaderImpl
/*     */ {
/*     */   protected final QName name;
/*     */   protected final String value;
/*     */   protected boolean mustUnderstand = false;
/*     */   protected SOAPVersion soapVersion;
/*     */   protected static final String MUST_UNDERSTAND = "mustUnderstand";
/*     */   protected static final String S12_MUST_UNDERSTAND_TRUE = "true";
/*     */   protected static final String S11_MUST_UNDERSTAND_TRUE = "1";
/*     */   
/*     */   public StringHeader(@NotNull QName name, @NotNull String value) {
/*  83 */     assert name != null;
/*  84 */     assert value != null;
/*  85 */     this.name = name;
/*  86 */     this.value = value;
/*     */   }
/*     */   
/*     */   public StringHeader(@NotNull QName name, @NotNull String value, @NotNull SOAPVersion soapVersion, boolean mustUnderstand) {
/*  90 */     this.name = name;
/*  91 */     this.value = value;
/*  92 */     this.soapVersion = soapVersion;
/*  93 */     this.mustUnderstand = mustUnderstand;
/*     */   }
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/*  97 */     return this.name.getNamespaceURI();
/*     */   }
/*     */   @NotNull
/*     */   public String getLocalPart() {
/* 101 */     return this.name.getLocalPart();
/*     */   }
/*     */   @Nullable
/*     */   public String getAttribute(@NotNull String nsUri, @NotNull String localName) {
/* 105 */     if (this.mustUnderstand && this.soapVersion.nsUri.equals(nsUri) && "mustUnderstand".equals(localName)) {
/* 106 */       return getMustUnderstandLiteral(this.soapVersion);
/*     */     }
/* 108 */     return null;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 112 */     MutableXMLStreamBuffer buf = new MutableXMLStreamBuffer();
/* 113 */     XMLStreamWriter w = buf.createFromXMLStreamWriter();
/* 114 */     writeTo(w);
/* 115 */     return (XMLStreamReader)buf.readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 119 */     w.writeStartElement("", this.name.getLocalPart(), this.name.getNamespaceURI());
/* 120 */     w.writeDefaultNamespace(this.name.getNamespaceURI());
/* 121 */     if (this.mustUnderstand) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 127 */       w.writeNamespace("S", this.soapVersion.nsUri);
/* 128 */       w.writeAttribute("S", this.soapVersion.nsUri, "mustUnderstand", getMustUnderstandLiteral(this.soapVersion));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 133 */     w.writeCharacters(this.value);
/* 134 */     w.writeEndElement();
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 138 */     SOAPHeader header = saaj.getSOAPHeader();
/* 139 */     if (header == null)
/* 140 */       header = saaj.getSOAPPart().getEnvelope().addHeader(); 
/* 141 */     SOAPHeaderElement she = header.addHeaderElement(this.name);
/* 142 */     if (this.mustUnderstand) {
/* 143 */       she.setMustUnderstand(true);
/*     */     }
/* 145 */     she.addTextNode(this.value);
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler h, ErrorHandler errorHandler) throws SAXException {
/* 149 */     String nsUri = this.name.getNamespaceURI();
/* 150 */     String ln = this.name.getLocalPart();
/*     */     
/* 152 */     h.startPrefixMapping("", nsUri);
/* 153 */     if (this.mustUnderstand) {
/* 154 */       AttributesImpl attributes = new AttributesImpl();
/* 155 */       attributes.addAttribute(this.soapVersion.nsUri, "mustUnderstand", "S:mustUnderstand", "CDATA", getMustUnderstandLiteral(this.soapVersion));
/* 156 */       h.startElement(nsUri, ln, ln, attributes);
/*     */     } else {
/* 158 */       h.startElement(nsUri, ln, ln, EMPTY_ATTS);
/*     */     } 
/* 160 */     h.characters(this.value.toCharArray(), 0, this.value.length());
/* 161 */     h.endElement(nsUri, ln, ln);
/*     */   }
/*     */   
/*     */   private static String getMustUnderstandLiteral(SOAPVersion sv) {
/* 165 */     if (sv == SOAPVersion.SOAP_12) {
/* 166 */       return "true";
/*     */     }
/* 168 */     return "1";
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\StringHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */