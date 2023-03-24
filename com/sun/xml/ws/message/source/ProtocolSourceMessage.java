/*     */ package com.sun.xml.ws.message.source;
/*     */ 
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Codecs;
/*     */ import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.ws.streaming.SourceReaderFactory;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
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
/*     */ public class ProtocolSourceMessage
/*     */   extends Message
/*     */ {
/*     */   private final Message sm;
/*     */   
/*     */   public ProtocolSourceMessage(Source source, SOAPVersion soapVersion) {
/*  79 */     XMLStreamReader reader = SourceReaderFactory.createSourceReader(source, true);
/*  80 */     StreamSOAPCodec codec = Codecs.createSOAPEnvelopeXmlCodec(soapVersion);
/*  81 */     this.sm = codec.decode(reader);
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/*  85 */     return this.sm.hasHeaders();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderList getHeaders() {
/*  92 */     return this.sm.getHeaders();
/*     */   }
/*     */   
/*     */   public String getPayloadLocalPart() {
/*  96 */     return this.sm.getPayloadLocalPart();
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 100 */     return this.sm.getPayloadNamespaceURI();
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/* 104 */     return this.sm.hasPayload();
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 108 */     return this.sm.readPayloadAsSource();
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/* 112 */     return this.sm.readPayload();
/*     */   }
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 116 */     this.sm.writePayloadTo(sw);
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/* 120 */     this.sm.writeTo(sw);
/*     */   }
/*     */   
/*     */   public Message copy() {
/* 124 */     return this.sm.copy();
/*     */   }
/*     */   
/*     */   public Source readEnvelopeAsSource() {
/* 128 */     return this.sm.readEnvelopeAsSource();
/*     */   }
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 132 */     return this.sm.readAsSOAPMessage();
/*     */   }
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage(Packet packet, boolean inbound) throws SOAPException {
/* 136 */     return this.sm.readAsSOAPMessage(packet, inbound);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 140 */     return (T)this.sm.readPayloadAsJAXB(unmarshaller);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 144 */     return (T)this.sm.readPayloadAsJAXB(bridge);
/*     */   }
/*     */   public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/* 147 */     return (T)this.sm.readPayloadAsJAXB(bridge);
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 151 */     this.sm.writeTo(contentHandler, errorHandler);
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 155 */     return this.sm.getSOAPVersion();
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageHeaders getMessageHeaders() {
/* 160 */     return this.sm.getMessageHeaders();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\source\ProtocolSourceMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */