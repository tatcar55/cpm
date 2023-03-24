/*     */ package com.sun.xml.ws.api.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.message.stream.StreamMessage;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
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
/*     */ class MessageWrapper
/*     */   extends StreamMessage
/*     */ {
/*     */   Packet packet;
/*     */   Message delegate;
/*     */   StreamMessage streamDelegate;
/*     */   
/*     */   public void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/*  80 */     this.streamDelegate.writePayloadTo(contentHandler, errorHandler, fragment);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBodyPrologue() {
/*  85 */     return this.streamDelegate.getBodyPrologue();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBodyEpilogue() {
/*  90 */     return this.streamDelegate.getBodyEpilogue();
/*     */   }
/*     */   
/*     */   MessageWrapper(Packet p, Message m) {
/*  94 */     super(m.getSOAPVersion());
/*  95 */     this.packet = p;
/*  96 */     this.delegate = m;
/*  97 */     this.streamDelegate = (m instanceof StreamMessage) ? (StreamMessage)m : null;
/*  98 */     setMessageMedadata(p);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 103 */     return this.delegate.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 108 */     return this.delegate.equals(obj);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasHeaders() {
/* 113 */     return this.delegate.hasHeaders();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public HeaderList getHeaders() {
/* 122 */     return this.delegate.getHeaders();
/*     */   }
/*     */ 
/*     */   
/*     */   public AttachmentSet getAttachments() {
/* 127 */     return this.delegate.getAttachments();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 132 */     return this.delegate.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOneWay(WSDLPort port) {
/* 137 */     return this.delegate.isOneWay(port);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPayloadLocalPart() {
/* 142 */     return this.delegate.getPayloadLocalPart();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 147 */     return this.delegate.getPayloadNamespaceURI();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPayload() {
/* 152 */     return this.delegate.hasPayload();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFault() {
/* 157 */     return this.delegate.isFault();
/*     */   }
/*     */ 
/*     */   
/*     */   public QName getFirstDetailEntryName() {
/* 162 */     return this.delegate.getFirstDetailEntryName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Source readEnvelopeAsSource() {
/* 168 */     return this.delegate.readEnvelopeAsSource();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 174 */     return this.delegate.readPayloadAsSource();
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 179 */     if (!(this.delegate instanceof com.sun.xml.ws.message.saaj.SAAJMessage)) {
/* 180 */       this.delegate = toSAAJ(this.packet, null);
/*     */     }
/* 182 */     return this.delegate.readAsSOAPMessage();
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage(Packet p, boolean inbound) throws SOAPException {
/* 187 */     if (!(this.delegate instanceof com.sun.xml.ws.message.saaj.SAAJMessage)) {
/* 188 */       this.delegate = toSAAJ(p, Boolean.valueOf(inbound));
/*     */     }
/* 190 */     return this.delegate.readAsSOAPMessage();
/*     */   }
/*     */ 
/*     */   
/*     */   public Object readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 195 */     return this.delegate.readPayloadAsJAXB(unmarshaller);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 200 */     return this.delegate.readPayloadAsJAXB(bridge);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/* 205 */     return this.delegate.readPayloadAsJAXB(bridge);
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamReader readPayload() {
/*     */     try {
/* 211 */       return this.delegate.readPayload();
/* 212 */     } catch (XMLStreamException e) {
/* 213 */       e.printStackTrace();
/*     */       
/* 215 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void consume() {
/* 220 */     this.delegate.consume();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 225 */     this.delegate.writePayloadTo(sw);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/* 230 */     this.delegate.writeTo(sw);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 236 */     this.delegate.writeTo(contentHandler, errorHandler);
/*     */   }
/*     */ 
/*     */   
/*     */   public Message copy() {
/* 241 */     return this.delegate.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getID(WSBinding binding) {
/* 246 */     return this.delegate.getID(binding);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getID(AddressingVersion av, SOAPVersion sv) {
/* 251 */     return this.delegate.getID(av, sv);
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 256 */     return this.delegate.getSOAPVersion();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public MessageHeaders getMessageHeaders() {
/* 261 */     return this.delegate.getMessageHeaders();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMessageMedadata(MessageMetadata metadata) {
/* 266 */     super.setMessageMedadata(metadata);
/* 267 */     this.delegate.setMessageMedadata(metadata);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\MessageWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */