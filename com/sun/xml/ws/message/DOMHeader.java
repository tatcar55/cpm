/*     */ package com.sun.xml.ws.message;
/*     */ 
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.bind.unmarshaller.DOMScanner;
/*     */ import com.sun.xml.ws.streaming.DOMStreamReader;
/*     */ import com.sun.xml.ws.util.DOMUtil;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class DOMHeader<N extends Element>
/*     */   extends AbstractHeaderImpl
/*     */ {
/*     */   protected final N node;
/*     */   private final String nsUri;
/*     */   private final String localName;
/*     */   
/*     */   public DOMHeader(N node) {
/*  74 */     assert node != null;
/*  75 */     this.node = node;
/*     */     
/*  77 */     this.nsUri = fixNull(node.getNamespaceURI());
/*  78 */     this.localName = node.getLocalName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNamespaceURI() {
/*  83 */     return this.nsUri;
/*     */   }
/*     */   
/*     */   public String getLocalPart() {
/*  87 */     return this.localName;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/*  91 */     DOMStreamReader r = new DOMStreamReader((Node)this.node);
/*  92 */     r.nextTag();
/*  93 */     return (XMLStreamReader)r;
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/*  97 */     return (T)unmarshaller.unmarshal((Node)this.node);
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 101 */     return (T)bridge.unmarshal((Node)this.node);
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 105 */     DOMUtil.serializeNode((Element)this.node, w);
/*     */   }
/*     */   
/*     */   private static String fixNull(String s) {
/* 109 */     if (s != null) return s; 
/* 110 */     return "";
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 114 */     DOMScanner ds = new DOMScanner();
/* 115 */     ds.setContentHandler(contentHandler);
/* 116 */     ds.scan((Element)this.node);
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 120 */     if (nsUri.length() == 0) nsUri = null; 
/* 121 */     return this.node.getAttributeNS(nsUri, localName);
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/* 125 */     SOAPHeader header = saaj.getSOAPHeader();
/* 126 */     if (header == null)
/* 127 */       header = saaj.getSOAPPart().getEnvelope().addHeader(); 
/* 128 */     Node clone = header.getOwnerDocument().importNode((Node)this.node, true);
/* 129 */     header.appendChild(clone);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStringContent() {
/* 134 */     return this.node.getTextContent();
/*     */   }
/*     */   
/*     */   public N getWrappedNode() {
/* 138 */     return this.node;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 144 */     return getWrappedNode().hashCode();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 150 */     if (obj instanceof DOMHeader) {
/* 151 */       return getWrappedNode().equals(((DOMHeader)obj).getWrappedNode());
/*     */     }
/* 153 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\DOMHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */