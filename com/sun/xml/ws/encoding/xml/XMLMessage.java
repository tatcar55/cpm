/*     */ package com.sun.xml.ws.encoding.xml;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSFeatureList;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.ContentType;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamWriterFactory;
/*     */ import com.sun.xml.ws.developer.StreamingAttachmentFeature;
/*     */ import com.sun.xml.ws.encoding.ContentType;
/*     */ import com.sun.xml.ws.encoding.MimeMultipartParser;
/*     */ import com.sun.xml.ws.encoding.XMLHTTPBindingCodec;
/*     */ import com.sun.xml.ws.message.AbstractMessageImpl;
/*     */ import com.sun.xml.ws.message.EmptyMessageImpl;
/*     */ import com.sun.xml.ws.message.MimeAttachmentSet;
/*     */ import com.sun.xml.ws.message.source.PayloadSourceMessage;
/*     */ import com.sun.xml.ws.util.ByteArrayBuffer;
/*     */ import com.sun.xml.ws.util.StreamUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
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
/*     */ public final class XMLMessage
/*     */ {
/*     */   private static final int PLAIN_XML_FLAG = 1;
/*     */   private static final int MIME_MULTIPART_FLAG = 2;
/*     */   private static final int FI_ENCODED_FLAG = 16;
/*     */   private WebServiceFeature[] features;
/*     */   
/*     */   public static Message create(String ct, InputStream in, WSFeatureList f) {
/*     */     UnknownContent unknownContent;
/*     */     try {
/* 101 */       in = StreamUtils.hasSomeData(in);
/* 102 */       if (in == null) {
/* 103 */         return Messages.createEmpty(SOAPVersion.SOAP_11);
/*     */       }
/*     */       
/* 106 */       if (ct != null) {
/* 107 */         ContentType contentType = new ContentType(ct);
/* 108 */         int contentTypeId = identifyContentType(contentType);
/* 109 */         if ((contentTypeId & 0x2) != 0) {
/* 110 */           XMLMultiPart xMLMultiPart = new XMLMultiPart(ct, in, f);
/* 111 */         } else if ((contentTypeId & 0x1) != 0) {
/* 112 */           XmlContent xmlContent = new XmlContent(ct, in, f);
/*     */         } else {
/* 114 */           unknownContent = new UnknownContent(ct, in);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 119 */         unknownContent = new UnknownContent("application/octet-stream", in);
/*     */       } 
/* 121 */     } catch (Exception ex) {
/* 122 */       throw new WebServiceException(ex);
/*     */     } 
/* 124 */     return (Message)unknownContent;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Message create(Source source) {
/* 129 */     return (source == null) ? Messages.createEmpty(SOAPVersion.SOAP_11) : Messages.createUsingPayload(source, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message create(DataSource ds, WSFeatureList f) {
/*     */     try {
/* 136 */       return (ds == null) ? Messages.createEmpty(SOAPVersion.SOAP_11) : create(ds.getContentType(), ds.getInputStream(), f);
/*     */     
/*     */     }
/* 139 */     catch (IOException ioe) {
/* 140 */       throw new WebServiceException(ioe);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Message create(Exception e) {
/* 145 */     return (Message)new FaultMessage(SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getContentId(String ct) {
/*     */     try {
/* 153 */       ContentType contentType = new ContentType(ct);
/* 154 */       return identifyContentType(contentType);
/* 155 */     } catch (Exception ex) {
/* 156 */       throw new WebServiceException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isFastInfoset(String ct) {
/* 164 */     return ((getContentId(ct) & 0x10) != 0);
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
/*     */   public static int identifyContentType(ContentType contentType) {
/* 178 */     String primary = contentType.getPrimaryType();
/* 179 */     String sub = contentType.getSubType();
/*     */     
/* 181 */     if (primary.equalsIgnoreCase("multipart") && sub.equalsIgnoreCase("related")) {
/* 182 */       String type = contentType.getParameter("type");
/* 183 */       if (type != null) {
/* 184 */         if (isXMLType(type))
/* 185 */           return 3; 
/* 186 */         if (isFastInfosetType(type)) {
/* 187 */           return 18;
/*     */         }
/*     */       } 
/* 190 */       return 0;
/* 191 */     }  if (isXMLType(primary, sub))
/* 192 */       return 1; 
/* 193 */     if (isFastInfosetType(primary, sub)) {
/* 194 */       return 16;
/*     */     }
/* 196 */     return 0;
/*     */   }
/*     */   
/*     */   protected static boolean isXMLType(@NotNull String primary, @NotNull String sub) {
/* 200 */     return ((primary.equalsIgnoreCase("text") && sub.equalsIgnoreCase("xml")) || (primary.equalsIgnoreCase("application") && sub.equalsIgnoreCase("xml")) || (primary.equalsIgnoreCase("application") && sub.toLowerCase().endsWith("+xml")));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isXMLType(String type) {
/* 206 */     String lowerType = type.toLowerCase();
/* 207 */     return (lowerType.startsWith("text/xml") || lowerType.startsWith("application/xml") || (lowerType.startsWith("application/") && lowerType.indexOf("+xml") != -1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isFastInfosetType(String primary, String sub) {
/* 213 */     return (primary.equalsIgnoreCase("application") && sub.equalsIgnoreCase("fastinfoset"));
/*     */   }
/*     */   
/*     */   protected static boolean isFastInfosetType(String type) {
/* 217 */     return type.toLowerCase().startsWith("application/fastinfoset");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface MessageDataSource
/*     */   {
/*     */     boolean hasUnconsumedDataSource();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     DataSource getDataSource();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class XmlContent
/*     */     extends AbstractMessageImpl
/*     */     implements MessageDataSource
/*     */   {
/*     */     private final XMLMessage.XmlDataSource dataSource;
/*     */ 
/*     */     
/*     */     private boolean consumed;
/*     */ 
/*     */     
/*     */     private Message delegate;
/*     */ 
/*     */     
/*     */     private final HeaderList headerList;
/*     */ 
/*     */     
/*     */     private WSFeatureList features;
/*     */ 
/*     */ 
/*     */     
/*     */     public XmlContent(String ct, InputStream in, WSFeatureList f) {
/* 256 */       super(SOAPVersion.SOAP_11);
/* 257 */       this.dataSource = new XMLMessage.XmlDataSource(ct, in);
/* 258 */       this.headerList = new HeaderList(SOAPVersion.SOAP_11);
/*     */       
/* 260 */       this.features = f;
/*     */     }
/*     */     
/*     */     private Message getMessage() {
/* 264 */       if (this.delegate == null) {
/* 265 */         InputStream in = this.dataSource.getInputStream();
/* 266 */         assert in != null;
/* 267 */         this.delegate = Messages.createUsingPayload(new StreamSource(in), SOAPVersion.SOAP_11);
/* 268 */         this.consumed = true;
/*     */       } 
/* 270 */       return this.delegate;
/*     */     }
/*     */     
/*     */     public boolean hasUnconsumedDataSource() {
/* 274 */       return (!this.dataSource.consumed() && !this.consumed);
/*     */     }
/*     */     
/*     */     public DataSource getDataSource() {
/* 278 */       return hasUnconsumedDataSource() ? this.dataSource : XMLMessage.getDataSource(getMessage(), this.features);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasHeaders() {
/* 283 */       return false;
/*     */     }
/*     */     @NotNull
/*     */     public HeaderList getHeaders() {
/* 287 */       return this.headerList;
/*     */     }
/*     */     
/*     */     public String getPayloadLocalPart() {
/* 291 */       return getMessage().getPayloadLocalPart();
/*     */     }
/*     */     
/*     */     public String getPayloadNamespaceURI() {
/* 295 */       return getMessage().getPayloadNamespaceURI();
/*     */     }
/*     */     
/*     */     public boolean hasPayload() {
/* 299 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isFault() {
/* 303 */       return false;
/*     */     }
/*     */     
/*     */     public Source readEnvelopeAsSource() {
/* 307 */       return getMessage().readEnvelopeAsSource();
/*     */     }
/*     */     
/*     */     public Source readPayloadAsSource() {
/* 311 */       return getMessage().readPayloadAsSource();
/*     */     }
/*     */     
/*     */     public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 315 */       return getMessage().readAsSOAPMessage();
/*     */     }
/*     */     
/*     */     public SOAPMessage readAsSOAPMessage(Packet packet, boolean inbound) throws SOAPException {
/* 319 */       return getMessage().readAsSOAPMessage(packet, inbound);
/*     */     }
/*     */     
/*     */     public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 323 */       return (T)getMessage().readPayloadAsJAXB(unmarshaller);
/*     */     }
/*     */     
/*     */     public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 327 */       return (T)getMessage().readPayloadAsJAXB(bridge);
/*     */     }
/*     */     
/*     */     public XMLStreamReader readPayload() throws XMLStreamException {
/* 331 */       return getMessage().readPayload();
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 336 */       getMessage().writePayloadTo(sw);
/*     */     }
/*     */     
/*     */     public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/* 340 */       getMessage().writeTo(sw);
/*     */     }
/*     */     
/*     */     public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 344 */       getMessage().writeTo(contentHandler, errorHandler);
/*     */     }
/*     */     
/*     */     public Message copy() {
/* 348 */       return getMessage().copy();
/*     */     }
/*     */     
/*     */     protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/* 352 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class XMLMultiPart
/*     */     extends AbstractMessageImpl
/*     */     implements MessageDataSource
/*     */   {
/*     */     private final DataSource dataSource;
/*     */     
/*     */     private final StreamingAttachmentFeature feature;
/*     */     
/*     */     private Message delegate;
/*     */     
/*     */     private HeaderList headerList;
/*     */     
/*     */     private final WSFeatureList features;
/*     */ 
/*     */     
/*     */     public XMLMultiPart(String contentType, InputStream is, WSFeatureList f) {
/* 374 */       super(SOAPVersion.SOAP_11);
/* 375 */       this.headerList = new HeaderList(SOAPVersion.SOAP_11);
/* 376 */       this.dataSource = XMLMessage.createDataSource(contentType, is);
/* 377 */       this.feature = (StreamingAttachmentFeature)f.get(StreamingAttachmentFeature.class);
/* 378 */       this.features = f;
/*     */     }
/*     */     
/*     */     private Message getMessage() {
/* 382 */       if (this.delegate == null) {
/*     */         MimeMultipartParser mpp;
/*     */         try {
/* 385 */           mpp = new MimeMultipartParser(this.dataSource.getInputStream(), this.dataSource.getContentType(), this.feature);
/*     */         }
/* 387 */         catch (IOException ioe) {
/* 388 */           throw new WebServiceException(ioe);
/*     */         } 
/* 390 */         InputStream in = mpp.getRootPart().asInputStream();
/* 391 */         assert in != null;
/* 392 */         this.delegate = (Message)new PayloadSourceMessage(this.headerList, new StreamSource(in), (AttachmentSet)new MimeAttachmentSet(mpp), SOAPVersion.SOAP_11);
/*     */       } 
/* 394 */       return this.delegate;
/*     */     }
/*     */     
/*     */     public boolean hasUnconsumedDataSource() {
/* 398 */       return (this.delegate == null);
/*     */     }
/*     */     
/*     */     public DataSource getDataSource() {
/* 402 */       return hasUnconsumedDataSource() ? this.dataSource : XMLMessage.getDataSource(getMessage(), this.features);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasHeaders() {
/* 407 */       return false;
/*     */     }
/*     */     @NotNull
/*     */     public HeaderList getHeaders() {
/* 411 */       return this.headerList;
/*     */     }
/*     */     
/*     */     public String getPayloadLocalPart() {
/* 415 */       return getMessage().getPayloadLocalPart();
/*     */     }
/*     */     
/*     */     public String getPayloadNamespaceURI() {
/* 419 */       return getMessage().getPayloadNamespaceURI();
/*     */     }
/*     */     
/*     */     public boolean hasPayload() {
/* 423 */       return true;
/*     */     }
/*     */     
/*     */     public boolean isFault() {
/* 427 */       return false;
/*     */     }
/*     */     
/*     */     public Source readEnvelopeAsSource() {
/* 431 */       return getMessage().readEnvelopeAsSource();
/*     */     }
/*     */     
/*     */     public Source readPayloadAsSource() {
/* 435 */       return getMessage().readPayloadAsSource();
/*     */     }
/*     */     
/*     */     public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 439 */       return getMessage().readAsSOAPMessage();
/*     */     }
/*     */     
/*     */     public SOAPMessage readAsSOAPMessage(Packet packet, boolean inbound) throws SOAPException {
/* 443 */       return getMessage().readAsSOAPMessage(packet, inbound);
/*     */     }
/*     */     
/*     */     public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 447 */       return (T)getMessage().readPayloadAsJAXB(unmarshaller);
/*     */     }
/*     */     
/*     */     public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 451 */       return (T)getMessage().readPayloadAsJAXB(bridge);
/*     */     }
/*     */     
/*     */     public XMLStreamReader readPayload() throws XMLStreamException {
/* 455 */       return getMessage().readPayload();
/*     */     }
/*     */     
/*     */     public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 459 */       getMessage().writePayloadTo(sw);
/*     */     }
/*     */     
/*     */     public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/* 463 */       getMessage().writeTo(sw);
/*     */     }
/*     */     
/*     */     public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 467 */       getMessage().writeTo(contentHandler, errorHandler);
/*     */     }
/*     */     
/*     */     public Message copy() {
/* 471 */       return getMessage().copy();
/*     */     }
/*     */     
/*     */     protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/* 475 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isOneWay(@NotNull WSDLPort port) {
/* 480 */       return false;
/*     */     }
/*     */     @NotNull
/*     */     public AttachmentSet getAttachments() {
/* 484 */       return getMessage().getAttachments();
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FaultMessage
/*     */     extends EmptyMessageImpl
/*     */   {
/*     */     public FaultMessage(SOAPVersion version) {
/* 492 */       super(version);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isFault() {
/* 497 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class UnknownContent
/*     */     extends AbstractMessageImpl
/*     */     implements MessageDataSource
/*     */   {
/*     */     private final DataSource ds;
/*     */     
/*     */     private final HeaderList headerList;
/*     */ 
/*     */     
/*     */     public UnknownContent(String ct, InputStream in) {
/* 513 */       this(XMLMessage.createDataSource(ct, in));
/*     */     }
/*     */     
/*     */     public UnknownContent(DataSource ds) {
/* 517 */       super(SOAPVersion.SOAP_11);
/* 518 */       this.ds = ds;
/* 519 */       this.headerList = new HeaderList(SOAPVersion.SOAP_11);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private UnknownContent(UnknownContent that) {
/* 526 */       super(that.soapVersion);
/* 527 */       this.ds = that.ds;
/* 528 */       this.headerList = HeaderList.copy(that.headerList);
/*     */     }
/*     */     
/*     */     public boolean hasUnconsumedDataSource() {
/* 532 */       return true;
/*     */     }
/*     */     
/*     */     public DataSource getDataSource() {
/* 536 */       assert this.ds != null;
/* 537 */       return this.ds;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/* 542 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean hasHeaders() {
/* 546 */       return false;
/*     */     }
/*     */     
/*     */     public boolean isFault() {
/* 550 */       return false;
/*     */     }
/*     */     
/*     */     public HeaderList getHeaders() {
/* 554 */       return this.headerList;
/*     */     }
/*     */     
/*     */     public String getPayloadLocalPart() {
/* 558 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public String getPayloadNamespaceURI() {
/* 562 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     public boolean hasPayload() {
/* 566 */       return false;
/*     */     }
/*     */     
/*     */     public Source readPayloadAsSource() {
/* 570 */       return null;
/*     */     }
/*     */     
/*     */     public XMLStreamReader readPayload() throws XMLStreamException {
/* 574 */       throw new WebServiceException("There isn't XML payload. Shouldn't come here.");
/*     */     }
/*     */ 
/*     */     
/*     */     public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {}
/*     */ 
/*     */     
/*     */     public Message copy() {
/* 582 */       return (Message)new UnknownContent(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static DataSource getDataSource(Message msg, WSFeatureList f) {
/* 588 */     if (msg == null)
/* 589 */       return null; 
/* 590 */     if (msg instanceof MessageDataSource) {
/* 591 */       return ((MessageDataSource)msg).getDataSource();
/*     */     }
/* 593 */     AttachmentSet atts = msg.getAttachments();
/* 594 */     if (atts != null && !atts.isEmpty()) {
/* 595 */       ByteArrayBuffer byteArrayBuffer = new ByteArrayBuffer();
/*     */       try {
/* 597 */         XMLHTTPBindingCodec xMLHTTPBindingCodec = new XMLHTTPBindingCodec(f);
/* 598 */         Packet packet = new Packet(msg);
/* 599 */         ContentType ct = xMLHTTPBindingCodec.getStaticContentType(packet);
/* 600 */         xMLHTTPBindingCodec.encode(packet, (OutputStream)byteArrayBuffer);
/* 601 */         return createDataSource(ct.getContentType(), byteArrayBuffer.newInputStream());
/* 602 */       } catch (IOException ioe) {
/* 603 */         throw new WebServiceException(ioe);
/*     */       } 
/*     */     } 
/*     */     
/* 607 */     ByteArrayBuffer bos = new ByteArrayBuffer();
/* 608 */     XMLStreamWriter writer = XMLStreamWriterFactory.create((OutputStream)bos);
/*     */     try {
/* 610 */       msg.writePayloadTo(writer);
/* 611 */       writer.flush();
/* 612 */     } catch (XMLStreamException e) {
/* 613 */       throw new WebServiceException(e);
/*     */     } 
/* 615 */     return createDataSource("text/xml", bos.newInputStream());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static DataSource createDataSource(String contentType, InputStream is) {
/* 621 */     return new XmlDataSource(contentType, is);
/*     */   }
/*     */   
/*     */   private static class XmlDataSource implements DataSource {
/*     */     private final String contentType;
/*     */     private final InputStream is;
/*     */     private boolean consumed;
/*     */     
/*     */     XmlDataSource(String contentType, InputStream is) {
/* 630 */       this.contentType = contentType;
/* 631 */       this.is = is;
/*     */     }
/*     */     
/*     */     public boolean consumed() {
/* 635 */       return this.consumed;
/*     */     }
/*     */     
/*     */     public InputStream getInputStream() {
/* 639 */       this.consumed = !this.consumed;
/* 640 */       return this.is;
/*     */     }
/*     */     
/*     */     public OutputStream getOutputStream() {
/* 644 */       return null;
/*     */     }
/*     */     
/*     */     public String getContentType() {
/* 648 */       return this.contentType;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 652 */       return "";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\encoding\xml\XMLMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */