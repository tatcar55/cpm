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
/*     */ public class ProblemActionHeader
/*     */   extends AbstractHeaderImpl
/*     */ {
/*     */   @NotNull
/*     */   protected String action;
/*     */   protected String soapAction;
/*     */   @NotNull
/*     */   protected AddressingVersion av;
/*     */   private static final String actionLocalName = "Action";
/*     */   private static final String soapActionLocalName = "SoapAction";
/*     */   
/*     */   public ProblemActionHeader(@NotNull String action, @NotNull AddressingVersion av) {
/*  74 */     this(action, null, av);
/*     */   }
/*     */   
/*     */   public ProblemActionHeader(@NotNull String action, String soapAction, @NotNull AddressingVersion av) {
/*  78 */     assert action != null;
/*  79 */     assert av != null;
/*  80 */     this.action = action;
/*  81 */     this.soapAction = soapAction;
/*  82 */     this.av = av;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/*  88 */     return this.av.nsUri;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getLocalPart() {
/*  94 */     return "ProblemAction";
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getAttribute(@NotNull String nsUri, @NotNull String localName) {
/*  99 */     return null;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/* 103 */     MutableXMLStreamBuffer buf = new MutableXMLStreamBuffer();
/* 104 */     XMLStreamWriter w = buf.createFromXMLStreamWriter();
/* 105 */     writeTo(w);
/* 106 */     return (XMLStreamReader)buf.readAsXMLStreamReader();
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 110 */     w.writeStartElement("", getLocalPart(), getNamespaceURI());
/* 111 */     w.writeDefaultNamespace(getNamespaceURI());
/* 112 */     w.writeStartElement("Action");
/* 113 */     w.writeCharacters(this.action);
/* 114 */     w.writeEndElement();
/* 115 */     if (this.soapAction != null) {
/* 116 */       w.writeStartElement("SoapAction");
/* 117 */       w.writeCharacters(this.soapAction);
/* 118 */       w.writeEndElement();
/*     */     } 
/* 120 */     w.writeEndElement();
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 124 */     SOAPHeader header = saaj.getSOAPHeader();
/* 125 */     if (header == null)
/* 126 */       header = saaj.getSOAPPart().getEnvelope().addHeader(); 
/* 127 */     SOAPHeaderElement she = header.addHeaderElement(new QName(getNamespaceURI(), getLocalPart()));
/* 128 */     she.addChildElement("Action");
/* 129 */     she.addTextNode(this.action);
/* 130 */     if (this.soapAction != null) {
/* 131 */       she.addChildElement("SoapAction");
/* 132 */       she.addTextNode(this.soapAction);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler h, ErrorHandler errorHandler) throws SAXException {
/* 137 */     String nsUri = getNamespaceURI();
/* 138 */     String ln = getLocalPart();
/*     */     
/* 140 */     h.startPrefixMapping("", nsUri);
/* 141 */     h.startElement(nsUri, ln, ln, EMPTY_ATTS);
/* 142 */     h.startElement(nsUri, "Action", "Action", EMPTY_ATTS);
/* 143 */     h.characters(this.action.toCharArray(), 0, this.action.length());
/* 144 */     h.endElement(nsUri, "Action", "Action");
/* 145 */     if (this.soapAction != null) {
/* 146 */       h.startElement(nsUri, "SoapAction", "SoapAction", EMPTY_ATTS);
/* 147 */       h.characters(this.soapAction.toCharArray(), 0, this.soapAction.length());
/* 148 */       h.endElement(nsUri, "SoapAction", "SoapAction");
/*     */     } 
/* 150 */     h.endElement(nsUri, ln, ln);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\ProblemActionHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */