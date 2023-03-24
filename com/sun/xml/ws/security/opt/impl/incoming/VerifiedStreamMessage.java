/*     */ package com.sun.xml.ws.security.opt.impl.incoming;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.istack.XMLStreamReaderToContentHandler;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferCreator;
/*     */ import com.sun.xml.stream.buffer.stax.StreamReaderBufferProcessor;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.streaming.XMLStreamReaderFactory;
/*     */ import com.sun.xml.ws.encoding.TagInfoset;
/*     */ import com.sun.xml.ws.message.AbstractMessageImpl;
/*     */ import com.sun.xml.ws.message.AttachmentUnmarshallerImpl;
/*     */ import com.sun.xml.ws.security.message.stream.LazyStreamBasedMessage;
/*     */ import com.sun.xml.ws.security.opt.impl.util.VerifiedMessageXMLStreamReader;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.ws.util.xml.DummyLocation;
/*     */ import com.sun.xml.ws.util.xml.StAXSource;
/*     */ import com.sun.xml.ws.util.xml.XMLStreamReaderToXMLStreamWriter;
/*     */ import com.sun.xml.wss.impl.XWSSecurityRuntimeException;
/*     */ import com.sun.xml.wss.logging.impl.opt.LogStringsMessages;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.Location;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public final class VerifiedStreamMessage
/*     */   extends AbstractMessageImpl
/*     */ {
/*     */   @NotNull
/*     */   private XMLStreamReader reader;
/* 107 */   private MutableXMLStreamBuffer buffer = null;
/*     */   
/*     */   @Nullable
/*     */   private HeaderList headers;
/*     */   
/*     */   private final String payloadLocalName;
/*     */   private final String payloadNamespaceURI;
/* 114 */   private static final Logger logger = Logger.getLogger("com.sun.xml.wss.logging.impl.opt", "com.sun.xml.wss.logging.impl.opt.LogStrings");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<String, String> bodyEnvNs;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private TagInfoset envelopeTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private TagInfoset headerTag;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private TagInfoset bodyTag;
/*     */ 
/*     */ 
/*     */   
/*     */   private Throwable consumedAt;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VerifiedStreamMessage(@Nullable HeaderList headers, @NotNull AttachmentSet attachmentSet, @NotNull XMLStreamReader reader, @NotNull SOAPVersion soapVersion, Map<String, String> bodyEnvNs) {
/* 147 */     super(soapVersion);
/* 148 */     this.headers = headers;
/* 149 */     this.attachmentSet = attachmentSet;
/* 150 */     this.reader = reader;
/* 151 */     this.bodyEnvNs = bodyEnvNs;
/*     */     
/* 153 */     if (reader.getEventType() == 7) {
/* 154 */       XMLStreamReaderUtil.nextElementContent(reader);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 159 */     if (reader.getEventType() == 2) {
/* 160 */       String body = reader.getLocalName();
/* 161 */       String nsUri = reader.getNamespaceURI();
/* 162 */       assert body != null;
/* 163 */       assert nsUri != null;
/*     */       
/* 165 */       if (body.equals("Body") && nsUri.equals(soapVersion.nsUri)) {
/* 166 */         this.payloadLocalName = null;
/* 167 */         this.payloadNamespaceURI = null;
/*     */       } else {
/* 169 */         throw new WebServiceException("Malformed stream: {" + nsUri + "}" + body);
/*     */       } 
/*     */     } else {
/* 172 */       this.payloadLocalName = reader.getLocalName();
/* 173 */       this.payloadNamespaceURI = reader.getNamespaceURI();
/*     */     } 
/*     */ 
/*     */     
/* 177 */     int base = soapVersion.ordinal() * 3;
/* 178 */     this.envelopeTag = DEFAULT_TAGS[base];
/* 179 */     this.headerTag = DEFAULT_TAGS[base + 1];
/* 180 */     this.bodyTag = DEFAULT_TAGS[base + 2];
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
/*     */   public VerifiedStreamMessage(@NotNull TagInfoset envelopeTag, @Nullable TagInfoset headerTag, @NotNull AttachmentSet attachmentSet, @Nullable HeaderList headers, @NotNull TagInfoset bodyTag, @NotNull XMLStreamReader reader, @NotNull SOAPVersion soapVersion, Map<String, String> bodyEnvNs) {
/* 203 */     this(headers, attachmentSet, reader, soapVersion, bodyEnvNs);
/* 204 */     assert envelopeTag != null && bodyTag != null;
/* 205 */     this.envelopeTag = envelopeTag;
/* 206 */     this.headerTag = (headerTag != null) ? headerTag : new TagInfoset(envelopeTag.nsUri, "Header", envelopeTag.prefix, EMPTY_ATTS, new String[0]);
/* 207 */     this.bodyTag = bodyTag;
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/* 211 */     return (this.headers != null && !this.headers.isEmpty());
/*     */   }
/*     */   
/*     */   public HeaderList getHeaders() {
/* 215 */     if (this.headers == null) {
/* 216 */       this.headers = new HeaderList();
/*     */     }
/* 218 */     return this.headers;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public AttachmentSet getAttachments() {
/* 225 */     return this.attachmentSet;
/*     */   }
/*     */   
/*     */   public String getPayloadLocalPart() {
/* 229 */     return this.payloadLocalName;
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 233 */     return this.payloadNamespaceURI;
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/* 237 */     return (this.payloadLocalName != null);
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 241 */     cacheMessage();
/* 242 */     if (hasPayload()) {
/* 243 */       assert unconsumed();
/* 244 */       return (Source)new StAXSource(this.reader, true);
/*     */     } 
/* 246 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T readPayloadAsJAXB(final Unmarshaller unmarshaller) throws JAXBException {
/*     */     try {
/* 254 */       cacheMessage();
/* 255 */       if (!hasPayload()) {
/* 256 */         return null;
/*     */       }
/* 258 */       assert unconsumed();
/*     */       
/* 260 */       Object ret = AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*     */             public Object run() throws Exception {
/* 262 */               if (VerifiedStreamMessage.this.hasAttachments()) {
/* 263 */                 unmarshaller.setAttachmentUnmarshaller((AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(VerifiedStreamMessage.this.getAttachments()));
/*     */               }
/*     */               try {
/* 266 */                 VerifiedStreamMessage.this.reader; if (8 == VerifiedStreamMessage.this.reader.getEventType() && VerifiedStreamMessage.this.buffer != null) {
/*     */                   try {
/* 268 */                     VerifiedStreamMessage.this.reader = (XMLStreamReader)VerifiedStreamMessage.this.buffer.readAsXMLStreamReader();
/* 269 */                     VerifiedStreamMessage.this.reader = (XMLStreamReader)new VerifiedMessageXMLStreamReader(VerifiedStreamMessage.this.reader, VerifiedStreamMessage.this.bodyEnvNs);
/* 270 */                     VerifiedStreamMessage.this.reader.next();
/* 271 */                   } catch (XMLStreamException ex) {
/* 272 */                     VerifiedStreamMessage.logger.log(Level.SEVERE, LogStringsMessages.WSS_1612_ERROR_READING_BUFFER(), ex);
/* 273 */                     throw new XWSSecurityRuntimeException(ex);
/*     */                   } 
/*     */                 }
/* 276 */                 return unmarshaller.unmarshal(VerifiedStreamMessage.this.reader);
/*     */               } finally {
/* 278 */                 unmarshaller.setAttachmentUnmarshaller(null);
/* 279 */                 XMLStreamReaderUtil.close(VerifiedStreamMessage.this.reader);
/* 280 */                 XMLStreamReaderFactory.recycle(VerifiedStreamMessage.this.reader);
/*     */               } 
/*     */             }
/*     */           });
/* 284 */       return (T)ret;
/* 285 */     } catch (PrivilegedActionException ex) {
/* 286 */       throw new XWSSecurityRuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 293 */     cacheMessage();
/* 294 */     if (!hasPayload()) {
/* 295 */       return null;
/*     */     }
/* 297 */     assert unconsumed();
/* 298 */     T r = (T)bridge.unmarshal(this.reader, hasAttachments() ? (AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(getAttachments()) : null);
/*     */     
/* 300 */     XMLStreamReaderUtil.close(this.reader);
/* 301 */     XMLStreamReaderFactory.recycle(this.reader);
/* 302 */     return r;
/*     */   }
/*     */ 
/*     */   
/*     */   public void consume() {
/* 307 */     assert unconsumed();
/* 308 */     XMLStreamReaderFactory.recycle(this.reader);
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() {
/* 312 */     cacheMessage();
/*     */     
/* 314 */     assert unconsumed();
/* 315 */     return this.reader;
/*     */   }
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter writer) throws XMLStreamException {
/* 319 */     if (this.payloadLocalName == null) {
/*     */       return;
/*     */     }
/* 322 */     assert unconsumed();
/* 323 */     XMLStreamReaderToXMLStreamWriter conv = new XMLStreamReaderToXMLStreamWriter();
/* 324 */     while (this.reader.getEventType() != 8) {
/* 325 */       String name = this.reader.getLocalName();
/* 326 */       String nsUri = this.reader.getNamespaceURI();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 331 */       if (this.reader.getEventType() == 2 && (
/* 332 */         !name.equals("Body") || !nsUri.equals(this.soapVersion.nsUri))) {
/* 333 */         XMLStreamReaderUtil.nextElementContent(this.reader);
/* 334 */         if (this.reader.getEventType() == 8) {
/*     */           break;
/*     */         }
/* 337 */         name = this.reader.getLocalName();
/* 338 */         nsUri = this.reader.getNamespaceURI();
/*     */       } 
/*     */       
/* 341 */       if ((name.equals("Body") && nsUri.equals(this.soapVersion.nsUri)) || this.reader.getEventType() == 8) {
/*     */         break;
/*     */       }
/* 344 */       conv.bridge(this.reader, writer);
/*     */     } 
/* 346 */     this.reader.close();
/* 347 */     XMLStreamReaderFactory.recycle(this.reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/* 352 */     writeEnvelope(sw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeEnvelope(XMLStreamWriter writer) throws XMLStreamException {
/* 360 */     writer.writeStartDocument();
/* 361 */     this.envelopeTag.writeStart(writer);
/*     */ 
/*     */     
/* 364 */     HeaderList hl = getHeaders();
/* 365 */     if (hl.size() > 0) {
/* 366 */       this.headerTag.writeStart(writer);
/* 367 */       for (Header h : hl) {
/* 368 */         h.writeTo(writer);
/*     */       }
/* 370 */       writer.writeEndElement();
/*     */     } 
/* 372 */     this.bodyTag.writeStart(writer);
/* 373 */     if (hasPayload()) {
/* 374 */       writePayloadTo(writer);
/*     */     }
/* 376 */     writer.writeEndElement();
/* 377 */     writer.writeEndElement();
/* 378 */     writer.writeEndDocument();
/*     */   }
/*     */   
/*     */   public void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/* 382 */     assert unconsumed();
/*     */     try {
/* 384 */       if (this.payloadLocalName == null) {
/*     */         return;
/*     */       }
/*     */       
/* 388 */       XMLStreamReaderToContentHandler conv = new XMLStreamReaderToContentHandler(this.reader, contentHandler, true, fragment);
/*     */ 
/*     */       
/* 391 */       while (this.reader.getEventType() != 8) {
/* 392 */         String name = this.reader.getLocalName();
/* 393 */         String nsUri = this.reader.getNamespaceURI();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 398 */         if (this.reader.getEventType() == 2 && (
/* 399 */           !name.equals("Body") || !nsUri.equals(this.soapVersion.nsUri))) {
/* 400 */           XMLStreamReaderUtil.nextElementContent(this.reader);
/* 401 */           if (this.reader.getEventType() == 8) {
/*     */             break;
/*     */           }
/* 404 */           name = this.reader.getLocalName();
/* 405 */           nsUri = this.reader.getNamespaceURI();
/*     */         } 
/*     */         
/* 408 */         if ((name.equals("Body") && nsUri.equals(this.soapVersion.nsUri)) || this.reader.getEventType() == 8) {
/*     */           break;
/*     */         }
/*     */         
/* 412 */         conv.bridge();
/*     */       } 
/* 414 */       this.reader.close();
/* 415 */       XMLStreamReaderFactory.recycle(this.reader);
/* 416 */     } catch (XMLStreamException e) {
/* 417 */       Location loc = e.getLocation();
/* 418 */       if (loc == null) {
/* 419 */         loc = DummyLocation.INSTANCE;
/*     */       }
/*     */       
/* 422 */       SAXParseException x = new SAXParseException(e.getMessage(), loc.getPublicId(), loc.getSystemId(), loc.getLineNumber(), loc.getColumnNumber(), e);
/*     */       
/* 424 */       errorHandler.error(x);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Message copy() {
/*     */     try {
/*     */       XMLStreamReader clone;
/* 434 */       if (hasPayload()) {
/* 435 */         assert unconsumed();
/* 436 */         this.consumedAt = null;
/* 437 */         MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/*     */ 
/*     */ 
/*     */         
/* 441 */         StreamReaderBufferCreator c = new StreamReaderBufferCreator(xsb);
/* 442 */         while (this.reader.getEventType() != 8) {
/* 443 */           String name = this.reader.getLocalName();
/* 444 */           String nsUri = this.reader.getNamespaceURI();
/* 445 */           if ((name.equals("Body") && nsUri.equals(this.soapVersion.nsUri)) || this.reader.getEventType() == 8) {
/*     */             break;
/*     */           }
/* 448 */           c.create(this.reader);
/*     */         } 
/* 450 */         XMLStreamReaderFactory.recycle(this.reader);
/*     */         
/* 452 */         this.reader = (XMLStreamReader)xsb.readAsXMLStreamReader();
/* 453 */         this.reader = (XMLStreamReader)new VerifiedMessageXMLStreamReader(this.reader, this.bodyEnvNs);
/* 454 */         StreamReaderBufferProcessor streamReaderBufferProcessor = xsb.readAsXMLStreamReader();
/* 455 */         VerifiedMessageXMLStreamReader verifiedMessageXMLStreamReader = new VerifiedMessageXMLStreamReader((XMLStreamReader)streamReaderBufferProcessor, this.bodyEnvNs);
/*     */         
/* 457 */         proceedToRootElement(this.reader);
/* 458 */         proceedToRootElement((XMLStreamReader)verifiedMessageXMLStreamReader);
/*     */       }
/*     */       else {
/*     */         
/* 462 */         clone = this.reader;
/* 463 */         XMLStreamReader clonedReader = this.reader;
/*     */       } 
/*     */       
/* 466 */       return (Message)new VerifiedStreamMessage(this.envelopeTag, this.headerTag, this.attachmentSet, HeaderList.copy(this.headers), this.bodyTag, clone, this.soapVersion, this.bodyEnvNs);
/* 467 */     } catch (XMLStreamException e) {
/* 468 */       throw new WebServiceException("Failed to copy a message", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void proceedToRootElement(XMLStreamReader xsr) throws XMLStreamException {
/* 473 */     assert xsr.getEventType() == 7;
/* 474 */     xsr.nextTag();
/* 475 */     assert xsr.getEventType() == 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 480 */     cacheMessage();
/* 481 */     return super.readAsSOAPMessage();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 486 */     contentHandler.setDocumentLocator(NULL_LOCATOR);
/* 487 */     contentHandler.startDocument();
/* 488 */     this.envelopeTag.writeStart(contentHandler);
/* 489 */     this.headerTag.writeStart(contentHandler);
/* 490 */     if (hasHeaders()) {
/* 491 */       HeaderList headerList = getHeaders();
/* 492 */       int len = headerList.size();
/* 493 */       for (int i = 0; i < len; i++)
/*     */       {
/* 495 */         headerList.get(i).writeTo(contentHandler, errorHandler);
/*     */       }
/*     */     } 
/* 498 */     this.headerTag.writeEnd(contentHandler);
/* 499 */     this.bodyTag.writeStart(contentHandler);
/* 500 */     writePayloadTo(contentHandler, errorHandler, true);
/* 501 */     this.bodyTag.writeEnd(contentHandler);
/* 502 */     this.envelopeTag.writeEnd(contentHandler);
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
/*     */   private boolean unconsumed() {
/* 514 */     if (this.payloadLocalName == null) {
/* 515 */       return true;
/*     */     }
/*     */     
/* 518 */     if (this.reader.getEventType() != 1) {
/* 519 */       System.out.append("Event Type=" + this.reader.getEventType() + " name=" + this.reader.getLocalName());
/* 520 */       System.out.append("START 1");
/* 521 */       System.out.append("END =2");
/* 522 */       AssertionError error = new AssertionError("StreamMessage has been already consumed. See the nested exception for where it's consumed");
/* 523 */       error.initCause(this.consumedAt);
/* 524 */       throw error;
/*     */     } 
/* 526 */     this.consumedAt = (new Exception()).fillInStackTrace();
/* 527 */     return true;
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
/* 541 */   private static final TagInfoset[] DEFAULT_TAGS = new TagInfoset[6]; static {
/* 542 */     create(SOAPVersion.SOAP_11);
/* 543 */     create(SOAPVersion.SOAP_12);
/*     */   }
/*     */   
/*     */   private static void create(SOAPVersion v) {
/* 547 */     int base = v.ordinal() * 3;
/* 548 */     DEFAULT_TAGS[base] = new TagInfoset(v.nsUri, "Envelope", "S", EMPTY_ATTS, new String[] { "S", v.nsUri });
/* 549 */     DEFAULT_TAGS[base + 1] = new TagInfoset(v.nsUri, "Header", "S", EMPTY_ATTS, new String[0]);
/* 550 */     DEFAULT_TAGS[base + 2] = new TagInfoset(v.nsUri, "Body", "S", EMPTY_ATTS, new String[0]);
/*     */   }
/*     */ 
/*     */   
/*     */   private void cacheMessage() {
/* 555 */     if (LazyStreamBasedMessage.mtomLargeData()) {
/*     */       return;
/*     */     }
/* 558 */     if (this.buffer == null) {
/*     */       try {
/* 560 */         this.buffer = new MutableXMLStreamBuffer();
/* 561 */         this.buffer.createFromXMLStreamReader(this.reader);
/* 562 */       } catch (XMLStreamException ex) {
/* 563 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1611_PROBLEM_CACHING(), ex);
/* 564 */         throw new XWSSecurityRuntimeException(ex);
/*     */       } 
/*     */       
/*     */       try {
/* 568 */         this.reader = (XMLStreamReader)this.buffer.readAsXMLStreamReader();
/* 569 */         this.reader = (XMLStreamReader)new VerifiedMessageXMLStreamReader(this.reader, this.bodyEnvNs);
/* 570 */         this.reader.next();
/* 571 */       } catch (XMLStreamException ex) {
/* 572 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1612_ERROR_READING_BUFFER(), ex);
/* 573 */         throw new XWSSecurityRuntimeException(ex);
/*     */       } 
/*     */     } 
/*     */     
/* 577 */     if (this.reader.getEventType() == 8 && 
/* 578 */       this.buffer != null)
/*     */       try {
/* 580 */         this.reader = (XMLStreamReader)this.buffer.readAsXMLStreamReader();
/* 581 */         this.reader = (XMLStreamReader)new VerifiedMessageXMLStreamReader(this.reader, this.bodyEnvNs);
/* 582 */         this.reader.next();
/* 583 */       } catch (XMLStreamException ex) {
/* 584 */         logger.log(Level.SEVERE, LogStringsMessages.WSS_1612_ERROR_READING_BUFFER(), ex);
/* 585 */         throw new XWSSecurityRuntimeException(ex);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\incoming\VerifiedStreamMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */