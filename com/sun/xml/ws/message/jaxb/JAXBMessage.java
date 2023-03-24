/*     */ package com.sun.xml.ws.message.jaxb;
/*     */ 
/*     */ import com.sun.istack.FragmentContentHandler;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBufferResult;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferProcessor;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.message.AbstractMessageImpl;
/*     */ import com.sun.xml.ws.message.AttachmentSetImpl;
/*     */ import com.sun.xml.ws.message.RootElementSniffer;
/*     */ import com.sun.xml.ws.message.stream.StreamMessage;
/*     */ import com.sun.xml.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.ws.spi.db.BindingContextFactory;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.ws.streaming.MtomStreamWriter;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.ws.streaming.XMLStreamWriterUtil;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.bind.util.JAXBResult;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Result;
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
/*     */ public final class JAXBMessage
/*     */   extends AbstractMessageImpl
/*     */ {
/*     */   private HeaderList headers;
/*     */   private final Object jaxbObject;
/*     */   private final XMLBridge bridge;
/*     */   private final JAXBContext rawContext;
/*     */   private String nsUri;
/*     */   private String localName;
/*     */   private XMLStreamBuffer infoset;
/*     */   
/*     */   public static Message create(BindingContext context, Object jaxbObject, SOAPVersion soapVersion, HeaderList headers, AttachmentSet attachments) {
/* 118 */     if (!context.hasSwaRef()) {
/* 119 */       return (Message)new JAXBMessage(context, jaxbObject, soapVersion, headers, attachments);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 127 */       MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/*     */       
/* 129 */       Marshaller m = context.createMarshaller();
/* 130 */       AttachmentMarshallerImpl am = new AttachmentMarshallerImpl(attachments);
/* 131 */       m.setAttachmentMarshaller(am);
/* 132 */       am.cleanup();
/* 133 */       m.marshal(jaxbObject, xsb.createFromXMLStreamWriter());
/*     */ 
/*     */       
/* 136 */       return (Message)new StreamMessage(headers, attachments, (XMLStreamReader)xsb.readAsXMLStreamReader(), soapVersion);
/* 137 */     } catch (JAXBException e) {
/* 138 */       throw new WebServiceException(e);
/* 139 */     } catch (XMLStreamException e) {
/* 140 */       throw new WebServiceException(e);
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
/*     */   public static Message create(BindingContext context, Object jaxbObject, SOAPVersion soapVersion) {
/* 156 */     return create(context, jaxbObject, soapVersion, (HeaderList)null, (AttachmentSet)null);
/*     */   }
/*     */   
/*     */   public static Message create(JAXBContext context, Object jaxbObject, SOAPVersion soapVersion) {
/* 160 */     return create(BindingContextFactory.create(context), jaxbObject, soapVersion, (HeaderList)null, (AttachmentSet)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message createRaw(JAXBContext context, Object jaxbObject, SOAPVersion soapVersion) {
/* 170 */     return (Message)new JAXBMessage(context, jaxbObject, soapVersion, null, null);
/*     */   }
/*     */   
/*     */   private JAXBMessage(BindingContext context, Object jaxbObject, SOAPVersion soapVer, HeaderList headers, AttachmentSet attachments) {
/* 174 */     super(soapVer);
/*     */     
/* 176 */     this.bridge = context.createFragmentBridge();
/* 177 */     this.rawContext = null;
/* 178 */     this.jaxbObject = jaxbObject;
/* 179 */     this.headers = headers;
/* 180 */     this.attachmentSet = attachments;
/*     */   }
/*     */   
/*     */   private JAXBMessage(JAXBContext rawContext, Object jaxbObject, SOAPVersion soapVer, HeaderList headers, AttachmentSet attachments) {
/* 184 */     super(soapVer);
/*     */     
/* 186 */     this.rawContext = rawContext;
/* 187 */     this.bridge = null;
/* 188 */     this.jaxbObject = jaxbObject;
/* 189 */     this.headers = headers;
/* 190 */     this.attachmentSet = attachments;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message create(XMLBridge bridge, Object jaxbObject, SOAPVersion soapVer) {
/* 201 */     if (!bridge.context().hasSwaRef()) {
/* 202 */       return (Message)new JAXBMessage(bridge, jaxbObject, soapVer);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 210 */       MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/*     */       
/* 212 */       AttachmentSetImpl attachments = new AttachmentSetImpl();
/* 213 */       AttachmentMarshallerImpl am = new AttachmentMarshallerImpl((AttachmentSet)attachments);
/* 214 */       bridge.marshal(jaxbObject, xsb.createFromXMLStreamWriter(), am);
/* 215 */       am.cleanup();
/*     */ 
/*     */       
/* 218 */       return (Message)new StreamMessage(null, (AttachmentSet)attachments, (XMLStreamReader)xsb.readAsXMLStreamReader(), soapVer);
/* 219 */     } catch (JAXBException e) {
/* 220 */       throw new WebServiceException(e);
/* 221 */     } catch (XMLStreamException e) {
/* 222 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private JAXBMessage(XMLBridge bridge, Object jaxbObject, SOAPVersion soapVer) {
/* 227 */     super(soapVer);
/*     */     
/* 229 */     this.bridge = bridge;
/* 230 */     this.rawContext = null;
/* 231 */     this.jaxbObject = jaxbObject;
/* 232 */     QName tagName = (bridge.getTypeInfo()).tagName;
/* 233 */     this.nsUri = tagName.getNamespaceURI();
/* 234 */     this.localName = tagName.getLocalPart();
/* 235 */     this.attachmentSet = (AttachmentSet)new AttachmentSetImpl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBMessage(JAXBMessage that) {
/* 242 */     super(that);
/* 243 */     this.headers = that.headers;
/* 244 */     if (this.headers != null)
/* 245 */       this.headers = new HeaderList(this.headers); 
/* 246 */     this.attachmentSet = that.attachmentSet;
/*     */     
/* 248 */     this.jaxbObject = that.jaxbObject;
/* 249 */     this.bridge = that.bridge;
/* 250 */     this.rawContext = that.rawContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasHeaders() {
/* 255 */     return (this.headers != null && !this.headers.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public HeaderList getHeaders() {
/* 260 */     if (this.headers == null)
/* 261 */       this.headers = new HeaderList(getSOAPVersion()); 
/* 262 */     return this.headers;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPayloadLocalPart() {
/* 267 */     if (this.localName == null)
/* 268 */       sniff(); 
/* 269 */     return this.localName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 274 */     if (this.nsUri == null)
/* 275 */       sniff(); 
/* 276 */     return this.nsUri;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPayload() {
/* 281 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sniff() {
/* 288 */     RootElementSniffer sniffer = new RootElementSniffer(false);
/*     */     try {
/* 290 */       if (this.rawContext != null)
/* 291 */       { Marshaller m = this.rawContext.createMarshaller();
/* 292 */         m.setProperty("jaxb.fragment", Boolean.TRUE);
/* 293 */         m.marshal(this.jaxbObject, (ContentHandler)sniffer); }
/*     */       else
/* 295 */       { this.bridge.marshal(this.jaxbObject, (ContentHandler)sniffer, null); } 
/* 296 */     } catch (JAXBException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 303 */       this.nsUri = sniffer.getNsUri();
/* 304 */       this.localName = sniffer.getLocalName();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 310 */     return new JAXBBridgeSource(this.bridge, this.jaxbObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 315 */     JAXBResult out = new JAXBResult(unmarshaller);
/*     */     
/*     */     try {
/* 318 */       out.getHandler().startDocument();
/* 319 */       if (this.rawContext != null) {
/* 320 */         Marshaller m = this.rawContext.createMarshaller();
/* 321 */         m.setProperty("jaxb.fragment", Boolean.TRUE);
/* 322 */         m.marshal(this.jaxbObject, out);
/*     */       } else {
/* 324 */         this.bridge.marshal(this.jaxbObject, out);
/* 325 */       }  out.getHandler().endDocument();
/* 326 */     } catch (SAXException e) {
/* 327 */       throw new JAXBException(e);
/*     */     } 
/* 329 */     return (T)out.getResult();
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/*     */     try {
/* 335 */       if (this.infoset == null) {
/* 336 */         XMLStreamBufferResult sbr = new XMLStreamBufferResult();
/* 337 */         if (this.rawContext != null) {
/* 338 */           Marshaller m = this.rawContext.createMarshaller();
/* 339 */           m.setProperty("jaxb.fragment", Boolean.TRUE);
/* 340 */           m.marshal(this.jaxbObject, (Result)sbr);
/*     */         } else {
/* 342 */           this.bridge.marshal(this.jaxbObject, (Result)sbr);
/* 343 */         }  this.infoset = (XMLStreamBuffer)sbr.getXMLStreamBuffer();
/*     */       } 
/* 345 */       StreamReaderBufferProcessor streamReaderBufferProcessor = this.infoset.readAsXMLStreamReader();
/* 346 */       if (streamReaderBufferProcessor.getEventType() == 7)
/* 347 */         XMLStreamReaderUtil.nextElementContent((XMLStreamReader)streamReaderBufferProcessor); 
/* 348 */       return (XMLStreamReader)streamReaderBufferProcessor;
/* 349 */     } catch (JAXBException e) {
/*     */       
/* 351 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/*     */     try {
/*     */       FragmentContentHandler fragmentContentHandler;
/* 361 */       if (fragment)
/* 362 */         fragmentContentHandler = new FragmentContentHandler(contentHandler); 
/* 363 */       AttachmentMarshallerImpl am = new AttachmentMarshallerImpl(this.attachmentSet);
/* 364 */       if (this.rawContext != null) {
/* 365 */         Marshaller m = this.rawContext.createMarshaller();
/* 366 */         m.setProperty("jaxb.fragment", Boolean.TRUE);
/* 367 */         m.setAttachmentMarshaller(am);
/* 368 */         m.marshal(this.jaxbObject, (ContentHandler)fragmentContentHandler);
/*     */       } else {
/* 370 */         this.bridge.marshal(this.jaxbObject, (ContentHandler)fragmentContentHandler, am);
/* 371 */       }  am.cleanup();
/* 372 */     } catch (JAXBException e) {
/*     */ 
/*     */ 
/*     */       
/* 376 */       throw new WebServiceException(e.getMessage(), e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/*     */     try {
/* 384 */       AttachmentMarshaller am = (sw instanceof MtomStreamWriter) ? ((MtomStreamWriter)sw).getAttachmentMarshaller() : new AttachmentMarshallerImpl(this.attachmentSet);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 389 */       String encoding = XMLStreamWriterUtil.getEncoding(sw);
/*     */ 
/*     */       
/* 392 */       OutputStream os = this.bridge.supportOutputStream() ? XMLStreamWriterUtil.getOutputStream(sw) : null;
/* 393 */       if (this.rawContext != null) {
/* 394 */         Marshaller m = this.rawContext.createMarshaller();
/* 395 */         m.setProperty("jaxb.fragment", Boolean.TRUE);
/* 396 */         m.setAttachmentMarshaller(am);
/* 397 */         if (os != null) {
/* 398 */           m.marshal(this.jaxbObject, os);
/*     */         } else {
/* 400 */           m.marshal(this.jaxbObject, sw);
/*     */         } 
/* 402 */       } else if (os != null && encoding != null && encoding.equalsIgnoreCase("utf-8")) {
/* 403 */         this.bridge.marshal(this.jaxbObject, os, sw.getNamespaceContext(), am);
/*     */       } else {
/* 405 */         this.bridge.marshal(this.jaxbObject, sw, am);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 410 */     catch (JAXBException e) {
/*     */       
/* 412 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Message copy() {
/* 418 */     return (Message)new JAXBMessage(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\jaxb\JAXBMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */