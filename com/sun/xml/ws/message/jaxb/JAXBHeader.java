/*     */ package com.sun.xml.ws.message.jaxb;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.XMLStreamException2;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.ws.message.AbstractHeaderImpl;
/*     */ import com.sun.xml.ws.message.RootElementSniffer;
/*     */ import com.sun.xml.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.ws.streaming.XMLStreamWriterUtil;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.util.JAXBResult;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
/*     */ import org.xml.sax.Attributes;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.SAXParseException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JAXBHeader
/*     */   extends AbstractHeaderImpl
/*     */ {
/*     */   private final Object jaxbObject;
/*     */   private final XMLBridge bridge;
/*     */   private String nsUri;
/*     */   private String localName;
/*     */   private Attributes atts;
/*     */   private XMLStreamBuffer infoset;
/*     */   
/*     */   public JAXBHeader(BindingContext context, Object jaxbObject) {
/* 100 */     this.jaxbObject = jaxbObject;
/*     */     
/* 102 */     this.bridge = context.createFragmentBridge();
/*     */     
/* 104 */     if (jaxbObject instanceof JAXBElement) {
/* 105 */       JAXBElement e = (JAXBElement)jaxbObject;
/* 106 */       this.nsUri = e.getName().getNamespaceURI();
/* 107 */       this.localName = e.getName().getLocalPart();
/*     */     } 
/*     */   }
/*     */   
/*     */   public JAXBHeader(XMLBridge bridge, Object jaxbObject) {
/* 112 */     this.jaxbObject = jaxbObject;
/* 113 */     this.bridge = bridge;
/*     */     
/* 115 */     QName tagName = (bridge.getTypeInfo()).tagName;
/* 116 */     this.nsUri = tagName.getNamespaceURI();
/* 117 */     this.localName = tagName.getLocalPart();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void parse() {
/* 124 */     RootElementSniffer sniffer = new RootElementSniffer();
/*     */     try {
/* 126 */       this.bridge.marshal(this.jaxbObject, (ContentHandler)sniffer, null);
/* 127 */     } catch (JAXBException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 134 */       this.nsUri = sniffer.getNsUri();
/* 135 */       this.localName = sniffer.getLocalName();
/* 136 */       this.atts = sniffer.getAttributes();
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public String getNamespaceURI() {
/* 142 */     if (this.nsUri == null)
/* 143 */       parse(); 
/* 144 */     return this.nsUri;
/*     */   }
/*     */   @NotNull
/*     */   public String getLocalPart() {
/* 148 */     if (this.localName == null)
/* 149 */       parse(); 
/* 150 */     return this.localName;
/*     */   }
/*     */   
/*     */   public String getAttribute(String nsUri, String localName) {
/* 154 */     if (this.atts == null)
/* 155 */       parse(); 
/* 156 */     return this.atts.getValue(nsUri, localName);
/*     */   }
/*     */   
/*     */   public XMLStreamReader readHeader() throws XMLStreamException {
/*     */     try {
/* 161 */       if (this.infoset == null) {
/* 162 */         XMLStreamBufferResult sbr = new XMLStreamBufferResult();
/* 163 */         this.bridge.marshal(this.jaxbObject, (Result)sbr);
/* 164 */         this.infoset = (XMLStreamBuffer)sbr.getXMLStreamBuffer();
/*     */       } 
/* 166 */       return (XMLStreamReader)this.infoset.readAsXMLStreamReader();
/* 167 */     } catch (JAXBException e) {
/* 168 */       throw new XMLStreamException2(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/*     */     try {
/* 174 */       JAXBResult r = new JAXBResult(unmarshaller);
/*     */       
/* 176 */       r.getHandler().startDocument();
/* 177 */       this.bridge.marshal(this.jaxbObject, r);
/* 178 */       r.getHandler().endDocument();
/* 179 */       return (T)r.getResult();
/* 180 */     } catch (SAXException e) {
/* 181 */       throw new JAXBException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 186 */     return (T)bridge.unmarshal(new JAXBBridgeSource(this.bridge, this.jaxbObject));
/*     */   }
/*     */   
/*     */   public <T> T readAsJAXB(XMLBridge<T> bond) throws JAXBException {
/* 190 */     return (T)bond.unmarshal(new JAXBBridgeSource(this.bridge, this.jaxbObject), null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/*     */     try {
/* 196 */       String encoding = XMLStreamWriterUtil.getEncoding(sw);
/*     */ 
/*     */       
/* 199 */       OutputStream os = this.bridge.supportOutputStream() ? XMLStreamWriterUtil.getOutputStream(sw) : null;
/* 200 */       if (os != null && encoding != null && encoding.equalsIgnoreCase("utf-8")) {
/* 201 */         this.bridge.marshal(this.jaxbObject, os, sw.getNamespaceContext(), null);
/*     */       } else {
/* 203 */         this.bridge.marshal(this.jaxbObject, sw, null);
/*     */       } 
/* 205 */     } catch (JAXBException e) {
/* 206 */       throw new XMLStreamException2(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(SOAPMessage saaj) throws SOAPException {
/*     */     try {
/* 212 */       SOAPHeader header = saaj.getSOAPHeader();
/* 213 */       if (header == null)
/* 214 */         header = saaj.getSOAPPart().getEnvelope().addHeader(); 
/* 215 */       this.bridge.marshal(this.jaxbObject, header);
/* 216 */     } catch (JAXBException e) {
/* 217 */       throw new SOAPException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/*     */     try {
/* 223 */       this.bridge.marshal(this.jaxbObject, contentHandler, null);
/* 224 */     } catch (JAXBException e) {
/* 225 */       SAXParseException x = new SAXParseException(e.getMessage(), null, null, -1, -1, e);
/* 226 */       errorHandler.fatalError(x);
/* 227 */       throw x;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\jaxb\JAXBHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */