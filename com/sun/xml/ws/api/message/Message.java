/*     */ package com.sun.xml.ws.api.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.model.JavaMethod;
/*     */ import com.sun.xml.ws.api.model.SEIModel;
/*     */ import com.sun.xml.ws.api.model.WSDLOperationMapping;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.ws.message.AttachmentSetImpl;
/*     */ import com.sun.xml.ws.message.StringHeader;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Message
/*     */ {
/*     */   protected AttachmentSet attachmentSet;
/*     */   
/*     */   public abstract boolean hasHeaders();
/*     */   
/*     */   @Deprecated
/*     */   @NotNull
/*     */   public abstract HeaderList getHeaders();
/*     */   
/*     */   @NotNull
/*     */   public MessageHeaders getMessageHeaders() {
/* 246 */     return getHeaders();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public AttachmentSet getAttachments() {
/* 254 */     if (this.attachmentSet == null) {
/* 255 */       this.attachmentSet = (AttachmentSet)new AttachmentSetImpl();
/*     */     }
/* 257 */     return this.attachmentSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasAttachments() {
/* 265 */     return (this.attachmentSet != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 270 */   private WSDLBoundOperation operation = null;
/*     */   
/* 272 */   private WSDLOperationMapping wsdlOperationMapping = null;
/*     */   
/* 274 */   private MessageMetadata messageMetadata = null;
/*     */   
/*     */   public void setMessageMedadata(MessageMetadata metadata) {
/* 277 */     this.messageMetadata = metadata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Boolean isOneWay;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @Nullable
/*     */   public final WSDLBoundOperation getOperation(@NotNull WSDLBoundPortType boundPortType) {
/* 310 */     if (this.operation == null && this.messageMetadata != null) {
/* 311 */       if (this.wsdlOperationMapping == null) this.wsdlOperationMapping = this.messageMetadata.getWSDLOperationMapping(); 
/* 312 */       if (this.wsdlOperationMapping != null) this.operation = this.wsdlOperationMapping.getWSDLBoundOperation(); 
/*     */     } 
/* 314 */     if (this.operation == null)
/* 315 */       this.operation = boundPortType.getOperation(getPayloadNamespaceURI(), getPayloadLocalPart()); 
/* 316 */     return this.operation;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @Nullable
/*     */   public final WSDLBoundOperation getOperation(@NotNull WSDLPort port) {
/* 329 */     return getOperation(port.getBinding());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @Nullable
/*     */   public final JavaMethod getMethod(@NotNull SEIModel seiModel) {
/*     */     String nsUri;
/* 359 */     if (this.wsdlOperationMapping == null && this.messageMetadata != null) {
/* 360 */       this.wsdlOperationMapping = this.messageMetadata.getWSDLOperationMapping();
/*     */     }
/* 362 */     if (this.wsdlOperationMapping != null) {
/* 363 */       return this.wsdlOperationMapping.getJavaMethod();
/*     */     }
/*     */     
/* 366 */     String localPart = getPayloadLocalPart();
/*     */     
/* 368 */     if (localPart == null) {
/* 369 */       localPart = "";
/* 370 */       nsUri = "";
/*     */     } else {
/* 372 */       nsUri = getPayloadNamespaceURI();
/*     */     } 
/* 374 */     QName name = new QName(nsUri, localPart);
/* 375 */     return seiModel.getJavaMethod(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOneWay(@NotNull WSDLPort port) {
/* 404 */     if (this.isOneWay == null) {
/*     */       
/* 406 */       WSDLBoundOperation op = getOperation(port);
/* 407 */       if (op != null) {
/* 408 */         this.isOneWay = Boolean.valueOf(op.getOperation().isOneWay());
/*     */       } else {
/*     */         
/* 411 */         this.isOneWay = Boolean.valueOf(false);
/*     */       } 
/* 413 */     }  return this.isOneWay.booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void assertOneWay(boolean value) {
/* 442 */     assert this.isOneWay == null || this.isOneWay.booleanValue() == value;
/*     */     
/* 444 */     this.isOneWay = Boolean.valueOf(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public abstract String getPayloadLocalPart();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getPayloadNamespaceURI();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean hasPayload();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFault() {
/* 492 */     String localPart = getPayloadLocalPart();
/* 493 */     if (localPart == null || !localPart.equals("Fault")) {
/* 494 */       return false;
/*     */     }
/* 496 */     String nsUri = getPayloadNamespaceURI();
/* 497 */     return (nsUri.equals(SOAPVersion.SOAP_11.nsUri) || nsUri.equals(SOAPVersion.SOAP_12.nsUri));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public QName getFirstDetailEntryName() {
/* 511 */     assert isFault();
/* 512 */     Message msg = copy();
/*     */     try {
/* 514 */       SOAPFaultBuilder fault = SOAPFaultBuilder.create(msg);
/* 515 */       return fault.getFirstDetailEntryName();
/* 516 */     } catch (JAXBException e) {
/* 517 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Source readEnvelopeAsSource();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Source readPayloadAsSource();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract SOAPMessage readAsSOAPMessage() throws SOAPException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage(Packet packet, boolean inbound) throws SOAPException {
/* 560 */     return readAsSOAPMessage();
/*     */   }
/*     */   
/*     */   public static Map<String, List<String>> getTransportHeaders(Packet packet) {
/* 564 */     return getTransportHeaders(packet, packet.getState().isInbound());
/*     */   }
/*     */   
/*     */   public static Map<String, List<String>> getTransportHeaders(Packet packet, boolean inbound) {
/* 568 */     Map<String, List<String>> headers = null;
/* 569 */     String key = inbound ? "com.sun.xml.ws.api.message.packet.inbound.transport.headers" : "com.sun.xml.ws.api.message.packet.outbound.transport.headers";
/* 570 */     if (packet.supports(key)) {
/* 571 */       headers = (Map<String, List<String>>)packet.get(key);
/*     */     }
/* 573 */     return headers;
/*     */   }
/*     */   
/*     */   public static void addSOAPMimeHeaders(MimeHeaders mh, Map<String, List<String>> headers) {
/* 577 */     for (Map.Entry<String, List<String>> e : headers.entrySet()) {
/* 578 */       if (!((String)e.getKey()).equalsIgnoreCase("Content-Type")) {
/* 579 */         for (String value : e.getValue()) {
/* 580 */           mh.addHeader(e.getKey(), value);
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract <T> T readPayloadAsJAXB(Unmarshaller paramUnmarshaller) throws JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract <T> T readPayloadAsJAXB(Bridge<T> paramBridge) throws JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract <T> T readPayloadAsJAXB(XMLBridge<T> paramXMLBridge) throws JAXBException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract XMLStreamReader readPayload() throws XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void consume() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void writePayloadTo(XMLStreamWriter paramXMLStreamWriter) throws XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void writeTo(XMLStreamWriter paramXMLStreamWriter) throws XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void writeTo(ContentHandler paramContentHandler, ErrorHandler paramErrorHandler) throws SAXException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Message copy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getID(@NotNull WSBinding binding) {
/* 781 */     return getID(binding.getAddressingVersion(), binding.getSOAPVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public String getID(AddressingVersion av, SOAPVersion sv) {
/* 794 */     String uuid = null;
/* 795 */     if (av != null) {
/* 796 */       uuid = AddressingUtils.getMessageID(getMessageHeaders(), av, sv);
/*     */     }
/* 798 */     if (uuid == null) {
/* 799 */       uuid = generateMessageID();
/* 800 */       getMessageHeaders().add((Header)new StringHeader(av.messageIDTag, uuid));
/*     */     } 
/* 802 */     return uuid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String generateMessageID() {
/* 810 */     return "uuid:" + UUID.randomUUID().toString();
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 814 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\Message.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */