/*     */ package com.sun.xml.ws.api.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FilterMessageImpl
/*     */   extends Message
/*     */ {
/*     */   private final Message delegate;
/*     */   
/*     */   protected FilterMessageImpl(Message delegate) {
/*  84 */     this.delegate = delegate;
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/*  88 */     return this.delegate.hasHeaders();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @NotNull
/*     */   public HeaderList getHeaders() {
/*  96 */     return this.delegate.getHeaders();
/*     */   }
/*     */   @NotNull
/*     */   public AttachmentSet getAttachments() {
/* 100 */     return this.delegate.getAttachments();
/*     */   }
/*     */   
/*     */   protected boolean hasAttachments() {
/* 104 */     return this.delegate.hasAttachments();
/*     */   }
/*     */   
/*     */   public boolean isOneWay(@NotNull WSDLPort port) {
/* 108 */     return this.delegate.isOneWay(port);
/*     */   }
/*     */   @Nullable
/*     */   public String getPayloadLocalPart() {
/* 112 */     return this.delegate.getPayloadLocalPart();
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 116 */     return this.delegate.getPayloadNamespaceURI();
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/* 120 */     return this.delegate.hasPayload();
/*     */   }
/*     */   
/*     */   public boolean isFault() {
/* 124 */     return this.delegate.isFault();
/*     */   }
/*     */   @Nullable
/*     */   public QName getFirstDetailEntryName() {
/* 128 */     return this.delegate.getFirstDetailEntryName();
/*     */   }
/*     */   
/*     */   public Source readEnvelopeAsSource() {
/* 132 */     return this.delegate.readEnvelopeAsSource();
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 136 */     return this.delegate.readPayloadAsSource();
/*     */   }
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 140 */     return this.delegate.readAsSOAPMessage();
/*     */   }
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage(Packet packet, boolean inbound) throws SOAPException {
/* 144 */     return this.delegate.readAsSOAPMessage(packet, inbound);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 148 */     return this.delegate.readPayloadAsJAXB(unmarshaller);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 152 */     return this.delegate.readPayloadAsJAXB(bridge);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/* 156 */     return this.delegate.readPayloadAsJAXB(bridge);
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/* 160 */     return this.delegate.readPayload();
/*     */   }
/*     */   
/*     */   public void consume() {
/* 164 */     this.delegate.consume();
/*     */   }
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 168 */     this.delegate.writePayloadTo(sw);
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/* 172 */     this.delegate.writeTo(sw);
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 176 */     this.delegate.writeTo(contentHandler, errorHandler);
/*     */   }
/*     */   
/*     */   public Message copy() {
/* 180 */     return this.delegate.copy();
/*     */   }
/*     */   @NotNull
/*     */   public String getID(@NotNull WSBinding binding) {
/* 184 */     return this.delegate.getID(binding);
/*     */   }
/*     */   @NotNull
/*     */   public String getID(AddressingVersion av, SOAPVersion sv) {
/* 188 */     return this.delegate.getID(av, sv);
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 192 */     return this.delegate.getSOAPVersion();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public MessageHeaders getMessageHeaders() {
/* 197 */     return this.delegate.getMessageHeaders();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\FilterMessageImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */