/*     */ package com.sun.xml.ws.message;
/*     */ 
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.ws.api.message.MessageWritable;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.message.saaj.SAAJFactory;
/*     */ import com.sun.xml.ws.message.saaj.SAAJMessage;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import org.xml.sax.ContentHandler;
/*     */ import org.xml.sax.ErrorHandler;
/*     */ import org.xml.sax.SAXException;
/*     */ import org.xml.sax.helpers.AttributesImpl;
/*     */ import org.xml.sax.helpers.LocatorImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractMessageImpl
/*     */   extends Message
/*     */ {
/*     */   protected final SOAPVersion soapVersion;
/*     */   
/*     */   protected AbstractMessageImpl(SOAPVersion soapVersion) {
/*  98 */     this.soapVersion = soapVersion;
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 102 */     return this.soapVersion;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractMessageImpl(AbstractMessageImpl that) {
/* 108 */     this.soapVersion = that.soapVersion;
/*     */   }
/*     */   
/*     */   public Source readEnvelopeAsSource() {
/* 112 */     return new SAXSource(new XMLReaderImpl(this), XMLReaderImpl.THE_SOURCE);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 116 */     if (hasAttachments())
/* 117 */       unmarshaller.setAttachmentUnmarshaller(new AttachmentUnmarshallerImpl(getAttachments())); 
/*     */     try {
/* 119 */       return (T)unmarshaller.unmarshal(readPayloadAsSource());
/*     */     } finally {
/* 121 */       unmarshaller.setAttachmentUnmarshaller(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 126 */     return (T)bridge.unmarshal(readPayloadAsSource(), hasAttachments() ? new AttachmentUnmarshallerImpl(getAttachments()) : null);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/* 131 */     return (T)bridge.unmarshal(readPayloadAsSource(), hasAttachments() ? new AttachmentUnmarshallerImpl(getAttachments()) : null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter w) throws XMLStreamException {
/* 139 */     String soapNsUri = this.soapVersion.nsUri;
/* 140 */     w.writeStartDocument();
/* 141 */     w.writeStartElement("S", "Envelope", soapNsUri);
/* 142 */     w.writeNamespace("S", soapNsUri);
/* 143 */     if (hasHeaders()) {
/* 144 */       w.writeStartElement("S", "Header", soapNsUri);
/* 145 */       HeaderList headers = getHeaders();
/* 146 */       int len = headers.size();
/* 147 */       for (int i = 0; i < len; i++) {
/* 148 */         headers.get(i).writeTo(w);
/*     */       }
/* 150 */       w.writeEndElement();
/*     */     } 
/*     */     
/* 153 */     w.writeStartElement("S", "Body", soapNsUri);
/*     */     
/* 155 */     writePayloadTo(w);
/*     */     
/* 157 */     w.writeEndElement();
/* 158 */     w.writeEndElement();
/* 159 */     w.writeEndDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 166 */     String soapNsUri = this.soapVersion.nsUri;
/*     */     
/* 168 */     contentHandler.setDocumentLocator(NULL_LOCATOR);
/* 169 */     contentHandler.startDocument();
/* 170 */     contentHandler.startPrefixMapping("S", soapNsUri);
/* 171 */     contentHandler.startElement(soapNsUri, "Envelope", "S:Envelope", EMPTY_ATTS);
/* 172 */     if (hasHeaders()) {
/* 173 */       contentHandler.startElement(soapNsUri, "Header", "S:Header", EMPTY_ATTS);
/* 174 */       HeaderList headers = getHeaders();
/* 175 */       int len = headers.size();
/* 176 */       for (int i = 0; i < len; i++)
/*     */       {
/* 178 */         headers.get(i).writeTo(contentHandler, errorHandler);
/*     */       }
/* 180 */       contentHandler.endElement(soapNsUri, "Header", "S:Header");
/*     */     } 
/*     */     
/* 183 */     contentHandler.startElement(soapNsUri, "Body", "S:Body", EMPTY_ATTS);
/* 184 */     writePayloadTo(contentHandler, errorHandler, true);
/* 185 */     contentHandler.endElement(soapNsUri, "Body", "S:Body");
/* 186 */     contentHandler.endElement(soapNsUri, "Envelope", "S:Envelope");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void writePayloadTo(ContentHandler paramContentHandler, ErrorHandler paramErrorHandler, boolean paramBoolean) throws SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message toSAAJ(Packet p, Boolean inbound) throws SOAPException {
/* 200 */     SAAJMessage message = SAAJFactory.read(p);
/* 201 */     if (message instanceof MessageWritable) {
/* 202 */       ((MessageWritable)message).setMTOMConfiguration(p.getMtomFeature());
/*     */     }
/* 204 */     if (inbound != null) transportHeaders(p, inbound.booleanValue(), message.readAsSOAPMessage()); 
/* 205 */     return (Message)message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 212 */     return SAAJFactory.read(this.soapVersion, this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage(Packet packet, boolean inbound) throws SOAPException {
/* 219 */     SOAPMessage msg = SAAJFactory.read(this.soapVersion, this, packet);
/* 220 */     transportHeaders(packet, inbound, msg);
/* 221 */     return msg;
/*     */   }
/*     */   
/*     */   private void transportHeaders(Packet packet, boolean inbound, SOAPMessage msg) throws SOAPException {
/* 225 */     Map<String, List<String>> headers = getTransportHeaders(packet, inbound);
/* 226 */     if (headers != null) {
/* 227 */       addSOAPMimeHeaders(msg.getMimeHeaders(), headers);
/*     */     }
/* 229 */     if (msg.saveRequired()) msg.saveChanges();
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageHeaders getMessageHeaders() {
/* 237 */     return (MessageHeaders)getHeaders();
/*     */   }
/* 239 */   protected static final AttributesImpl EMPTY_ATTS = new AttributesImpl();
/* 240 */   protected static final LocatorImpl NULL_LOCATOR = new LocatorImpl();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\AbstractMessageImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */