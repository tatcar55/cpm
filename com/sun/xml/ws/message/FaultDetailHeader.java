/*     */ package com.sun.xml.ws.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FaultDetailHeader
/*     */   extends AbstractHeaderImpl
/*     */ {
/*     */   private AddressingVersion av;
/*     */   private String wrapper;
/*  67 */   private String problemValue = null;
/*     */   
/*     */   public FaultDetailHeader(AddressingVersion av, String wrapper, QName problemHeader) {
/*  70 */     this.av = av;
/*  71 */     this.wrapper = wrapper;
/*  72 */     this.problemValue = problemHeader.toString();
/*     */   }
/*     */   
/*     */   public FaultDetailHeader(AddressingVersion av, String wrapper, String problemValue) {
/*  76 */     this.av = av;
/*  77 */     this.wrapper = wrapper;
/*  78 */     this.problemValue = problemValue;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/*  84 */     return this.av.nsUri;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getLocalPart() {
/*  90 */     return this.av.faultDetailTag.getLocalPart();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getAttribute(@NotNull String nsUri, @NotNull String localName) {
/*  95 */     return null;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/*  99 */     MutableXMLStreamBuffer buf = new MutableXMLStreamBuffer();
/* 100 */     XMLStreamWriter w = buf.createFromXMLStreamWriter();
/* 101 */     writeTo(w);
/* 102 */     return (XMLStreamReader)buf.readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 106 */     w.writeStartElement("", this.av.faultDetailTag.getLocalPart(), this.av.faultDetailTag.getNamespaceURI());
/* 107 */     w.writeDefaultNamespace(this.av.nsUri);
/* 108 */     w.writeStartElement("", this.wrapper, this.av.nsUri);
/* 109 */     w.writeCharacters(this.problemValue);
/* 110 */     w.writeEndElement();
/* 111 */     w.writeEndElement();
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 115 */     SOAPHeader header = saaj.getSOAPHeader();
/* 116 */     if (header == null)
/* 117 */       header = saaj.getSOAPPart().getEnvelope().addHeader(); 
/* 118 */     SOAPHeaderElement she = header.addHeaderElement(this.av.faultDetailTag);
/* 119 */     she = header.addHeaderElement(new QName(this.av.nsUri, this.wrapper));
/* 120 */     she.addTextNode(this.problemValue);
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler h, ErrorHandler errorHandler) throws SAXException {
/* 124 */     String nsUri = this.av.nsUri;
/* 125 */     String ln = this.av.faultDetailTag.getLocalPart();
/*     */     
/* 127 */     h.startPrefixMapping("", nsUri);
/* 128 */     h.startElement(nsUri, ln, ln, EMPTY_ATTS);
/* 129 */     h.startElement(nsUri, this.wrapper, this.wrapper, EMPTY_ATTS);
/* 130 */     h.characters(this.problemValue.toCharArray(), 0, this.problemValue.length());
/* 131 */     h.endElement(nsUri, this.wrapper, this.wrapper);
/* 132 */     h.endElement(nsUri, ln, ln);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\FaultDetailHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */