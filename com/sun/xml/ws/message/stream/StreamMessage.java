/*     */ package com.sun.xml.ws.message.stream;
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
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.ws.util.xml.DummyLocation;
/*     */ import com.sun.xml.ws.util.xml.StAXSource;
/*     */ import com.sun.xml.ws.util.xml.XMLStreamReaderToXMLStreamWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
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
/*     */ import org.xml.sax.helpers.NamespaceSupport;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StreamMessage
/*     */   extends AbstractMessageImpl
/*     */ {
/*     */   @NotNull
/*     */   private XMLStreamReader reader;
/*     */   @Nullable
/*     */   private HeaderList headers;
/* 103 */   private String bodyPrologue = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 108 */   private String bodyEpilogue = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String payloadLocalName;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String payloadNamespaceURI;
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private TagInfoset envelopeTag;
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private TagInfoset headerTag;
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private TagInfoset bodyTag;
/*     */ 
/*     */   
/*     */   private Throwable consumedAt;
/*     */ 
/*     */   
/* 136 */   private static final TagInfoset[] DEFAULT_TAGS = new TagInfoset[6]; static {
/* 137 */     create(SOAPVersion.SOAP_11);
/* 138 */     create(SOAPVersion.SOAP_12);
/*     */   }
/*     */   
/*     */   public StreamMessage(SOAPVersion v) {
/* 142 */     super(v);
/* 143 */     this.payloadLocalName = null;
/* 144 */     this.payloadNamespaceURI = null;
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
/*     */   public StreamMessage(@Nullable HeaderList headers, @NotNull AttachmentSet attachmentSet, @NotNull XMLStreamReader reader, @NotNull SOAPVersion soapVersion) {
/* 161 */     super(soapVersion);
/* 162 */     this.headers = headers;
/* 163 */     this.attachmentSet = attachmentSet;
/* 164 */     this.reader = reader;
/*     */     
/* 166 */     if (reader.getEventType() == 7) {
/* 167 */       XMLStreamReaderUtil.nextElementContent(reader);
/*     */     }
/*     */ 
/*     */     
/* 171 */     if (reader.getEventType() == 2) {
/* 172 */       String body = reader.getLocalName();
/* 173 */       String nsUri = reader.getNamespaceURI();
/* 174 */       assert body != null;
/* 175 */       assert nsUri != null;
/*     */       
/* 177 */       if (body.equals("Body") && nsUri.equals(soapVersion.nsUri)) {
/* 178 */         this.payloadLocalName = null;
/* 179 */         this.payloadNamespaceURI = null;
/*     */       } else {
/* 181 */         throw new WebServiceException("Malformed stream: {" + nsUri + "}" + body);
/*     */       } 
/*     */     } else {
/* 184 */       this.payloadLocalName = reader.getLocalName();
/* 185 */       this.payloadNamespaceURI = reader.getNamespaceURI();
/*     */     } 
/*     */ 
/*     */     
/* 189 */     int base = soapVersion.ordinal() * 3;
/* 190 */     this.envelopeTag = DEFAULT_TAGS[base];
/* 191 */     this.headerTag = DEFAULT_TAGS[base + 1];
/* 192 */     this.bodyTag = DEFAULT_TAGS[base + 2];
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
/*     */   public StreamMessage(@NotNull TagInfoset envelopeTag, @Nullable TagInfoset headerTag, @NotNull AttachmentSet attachmentSet, @Nullable HeaderList headers, @NotNull TagInfoset bodyTag, @NotNull XMLStreamReader reader, @NotNull SOAPVersion soapVersion) {
/* 208 */     this(envelopeTag, headerTag, attachmentSet, headers, (String)null, bodyTag, (String)null, reader, soapVersion);
/*     */   }
/*     */   
/*     */   public StreamMessage(@NotNull TagInfoset envelopeTag, @Nullable TagInfoset headerTag, @NotNull AttachmentSet attachmentSet, @Nullable HeaderList headers, @Nullable String bodyPrologue, @NotNull TagInfoset bodyTag, @Nullable String bodyEpilogue, @NotNull XMLStreamReader reader, @NotNull SOAPVersion soapVersion) {
/* 212 */     this(headers, attachmentSet, reader, soapVersion);
/* 213 */     if (envelopeTag == null) {
/* 214 */       throw new IllegalArgumentException("EnvelopeTag TagInfoset cannot be null");
/*     */     }
/* 216 */     if (bodyTag == null) {
/* 217 */       throw new IllegalArgumentException("BodyTag TagInfoset cannot be null");
/*     */     }
/* 219 */     this.envelopeTag = envelopeTag;
/* 220 */     this.headerTag = (headerTag != null) ? headerTag : new TagInfoset(envelopeTag.nsUri, "Header", envelopeTag.prefix, EMPTY_ATTS, new String[0]);
/*     */     
/* 222 */     this.bodyTag = bodyTag;
/* 223 */     this.bodyPrologue = bodyPrologue;
/* 224 */     this.bodyEpilogue = bodyEpilogue;
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/* 228 */     return (this.headers != null && !this.headers.isEmpty());
/*     */   }
/*     */   
/*     */   public HeaderList getHeaders() {
/* 232 */     if (this.headers == null) {
/* 233 */       this.headers = new HeaderList(getSOAPVersion());
/*     */     }
/* 235 */     return this.headers;
/*     */   }
/*     */   
/*     */   public String getPayloadLocalPart() {
/* 239 */     return this.payloadLocalName;
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 243 */     return this.payloadNamespaceURI;
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/* 247 */     return (this.payloadLocalName != null);
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 251 */     if (hasPayload()) {
/* 252 */       assert unconsumed();
/* 253 */       return (Source)new StAXSource(this.reader, true, getInscopeNamespaces());
/*     */     } 
/* 255 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] getInscopeNamespaces() {
/* 266 */     NamespaceSupport nss = new NamespaceSupport();
/*     */     
/* 268 */     nss.pushContext(); int i;
/* 269 */     for (i = 0; i < this.envelopeTag.ns.length; i += 2) {
/* 270 */       nss.declarePrefix(this.envelopeTag.ns[i], this.envelopeTag.ns[i + 1]);
/*     */     }
/*     */     
/* 273 */     nss.pushContext();
/* 274 */     for (i = 0; i < this.bodyTag.ns.length; i += 2) {
/* 275 */       nss.declarePrefix(this.bodyTag.ns[i], this.bodyTag.ns[i + 1]);
/*     */     }
/*     */     
/* 278 */     List<String> inscope = new ArrayList<String>();
/* 279 */     for (Enumeration<String> en = nss.getPrefixes(); en.hasMoreElements(); ) {
/* 280 */       String prefix = en.nextElement();
/* 281 */       inscope.add(prefix);
/* 282 */       inscope.add(nss.getURI(prefix));
/*     */     } 
/* 284 */     return inscope.<String>toArray(new String[inscope.size()]);
/*     */   }
/*     */   
/*     */   public Object readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 288 */     if (!hasPayload())
/* 289 */       return null; 
/* 290 */     assert unconsumed();
/*     */     
/* 292 */     if (hasAttachments())
/* 293 */       unmarshaller.setAttachmentUnmarshaller((AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(getAttachments())); 
/*     */     try {
/* 295 */       return unmarshaller.unmarshal(this.reader);
/*     */     } finally {
/* 297 */       unmarshaller.setAttachmentUnmarshaller(null);
/* 298 */       XMLStreamReaderUtil.readRest(this.reader);
/* 299 */       XMLStreamReaderUtil.close(this.reader);
/* 300 */       XMLStreamReaderFactory.recycle(this.reader);
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 305 */     if (!hasPayload())
/* 306 */       return null; 
/* 307 */     assert unconsumed();
/* 308 */     T r = (T)bridge.unmarshal(this.reader, hasAttachments() ? (AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(getAttachments()) : null);
/*     */     
/* 310 */     XMLStreamReaderUtil.readRest(this.reader);
/* 311 */     XMLStreamReaderUtil.close(this.reader);
/* 312 */     XMLStreamReaderFactory.recycle(this.reader);
/* 313 */     return r;
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/* 317 */     if (!hasPayload())
/* 318 */       return null; 
/* 319 */     assert unconsumed();
/* 320 */     T r = (T)bridge.unmarshal(this.reader, hasAttachments() ? (AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(getAttachments()) : null);
/*     */     
/* 322 */     XMLStreamReaderUtil.readRest(this.reader);
/* 323 */     XMLStreamReaderUtil.close(this.reader);
/* 324 */     XMLStreamReaderFactory.recycle(this.reader);
/* 325 */     return r;
/*     */   }
/*     */ 
/*     */   
/*     */   public void consume() {
/* 330 */     assert unconsumed();
/* 331 */     XMLStreamReaderUtil.readRest(this.reader);
/* 332 */     XMLStreamReaderUtil.close(this.reader);
/* 333 */     XMLStreamReaderFactory.recycle(this.reader);
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() {
/* 337 */     if (!hasPayload()) {
/* 338 */       return null;
/*     */     }
/* 340 */     assert unconsumed();
/* 341 */     return this.reader;
/*     */   }
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter writer) throws XMLStreamException {
/* 345 */     assert unconsumed();
/*     */     
/* 347 */     if (this.payloadLocalName == null) {
/*     */       return;
/*     */     }
/*     */     
/* 351 */     if (this.bodyPrologue != null) {
/* 352 */       writer.writeCharacters(this.bodyPrologue);
/*     */     }
/*     */     
/* 355 */     XMLStreamReaderToXMLStreamWriter conv = new XMLStreamReaderToXMLStreamWriter();
/*     */     
/* 357 */     while (this.reader.getEventType() != 8) {
/* 358 */       String name = this.reader.getLocalName();
/* 359 */       String nsUri = this.reader.getNamespaceURI();
/*     */ 
/*     */ 
/*     */       
/* 363 */       if (this.reader.getEventType() == 2) {
/*     */         
/* 365 */         if (!isBodyElement(name, nsUri)) {
/*     */ 
/*     */           
/* 368 */           String whiteSpaces = XMLStreamReaderUtil.nextWhiteSpaceContent(this.reader);
/* 369 */           if (whiteSpaces != null) {
/* 370 */             this.bodyEpilogue = whiteSpaces;
/*     */             
/* 372 */             writer.writeCharacters(whiteSpaces);
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 381 */       conv.bridge(this.reader, writer);
/*     */     } 
/*     */ 
/*     */     
/* 385 */     XMLStreamReaderUtil.readRest(this.reader);
/* 386 */     XMLStreamReaderUtil.close(this.reader);
/* 387 */     XMLStreamReaderFactory.recycle(this.reader);
/*     */   }
/*     */   
/*     */   private boolean isBodyElement(String name, String nsUri) {
/* 391 */     return (name.equals("Body") && nsUri.equals(this.soapVersion.nsUri));
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/* 395 */     writeEnvelope(sw);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeEnvelope(XMLStreamWriter writer) throws XMLStreamException {
/* 403 */     writer.writeStartDocument();
/* 404 */     this.envelopeTag.writeStart(writer);
/*     */ 
/*     */     
/* 407 */     HeaderList hl = getHeaders();
/* 408 */     if (hl.size() > 0) {
/* 409 */       this.headerTag.writeStart(writer);
/* 410 */       for (Header h : hl) {
/* 411 */         h.writeTo(writer);
/*     */       }
/* 413 */       writer.writeEndElement();
/*     */     } 
/* 415 */     this.bodyTag.writeStart(writer);
/* 416 */     if (hasPayload())
/* 417 */       writePayloadTo(writer); 
/* 418 */     writer.writeEndElement();
/* 419 */     writer.writeEndElement();
/* 420 */     writer.writeEndDocument();
/*     */   }
/*     */   
/*     */   public void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/* 424 */     assert unconsumed();
/*     */     
/*     */     try {
/* 427 */       if (this.payloadLocalName == null) {
/*     */         return;
/*     */       }
/* 430 */       if (this.bodyPrologue != null) {
/* 431 */         char[] chars = this.bodyPrologue.toCharArray();
/* 432 */         contentHandler.characters(chars, 0, chars.length);
/*     */       } 
/*     */       
/* 435 */       XMLStreamReaderToContentHandler conv = new XMLStreamReaderToContentHandler(this.reader, contentHandler, true, fragment, getInscopeNamespaces());
/*     */       
/* 437 */       while (this.reader.getEventType() != 8) {
/* 438 */         String name = this.reader.getLocalName();
/* 439 */         String nsUri = this.reader.getNamespaceURI();
/*     */ 
/*     */ 
/*     */         
/* 443 */         if (this.reader.getEventType() == 2) {
/*     */           
/* 445 */           if (!isBodyElement(name, nsUri)) {
/*     */ 
/*     */             
/* 448 */             String whiteSpaces = XMLStreamReaderUtil.nextWhiteSpaceContent(this.reader);
/* 449 */             if (whiteSpaces != null) {
/* 450 */               this.bodyEpilogue = whiteSpaces;
/*     */               
/* 452 */               char[] chars = whiteSpaces.toCharArray();
/* 453 */               contentHandler.characters(chars, 0, chars.length);
/*     */             } 
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 462 */         conv.bridge();
/*     */       } 
/*     */       
/* 465 */       XMLStreamReaderUtil.readRest(this.reader);
/* 466 */       XMLStreamReaderUtil.close(this.reader);
/* 467 */       XMLStreamReaderFactory.recycle(this.reader);
/* 468 */     } catch (XMLStreamException e) {
/* 469 */       Location loc = e.getLocation();
/* 470 */       if (loc == null) loc = DummyLocation.INSTANCE;
/*     */       
/* 472 */       SAXParseException x = new SAXParseException(e.getMessage(), loc.getPublicId(), loc.getSystemId(), loc.getLineNumber(), loc.getColumnNumber(), e);
/*     */       
/* 474 */       errorHandler.error(x);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Message copy() {
/*     */     try {
/* 481 */       assert unconsumed();
/* 482 */       this.consumedAt = null;
/* 483 */       MutableXMLStreamBuffer xsb = new MutableXMLStreamBuffer();
/* 484 */       StreamReaderBufferCreator c = new StreamReaderBufferCreator(xsb);
/*     */ 
/*     */ 
/*     */       
/* 488 */       c.storeElement(this.envelopeTag.nsUri, this.envelopeTag.localName, this.envelopeTag.prefix, this.envelopeTag.ns);
/* 489 */       c.storeElement(this.bodyTag.nsUri, this.bodyTag.localName, this.bodyTag.prefix, this.bodyTag.ns);
/*     */       
/* 491 */       if (hasPayload())
/*     */       {
/* 493 */         while (this.reader.getEventType() != 8) {
/* 494 */           String name = this.reader.getLocalName();
/* 495 */           String nsUri = this.reader.getNamespaceURI();
/* 496 */           if (isBodyElement(name, nsUri) || this.reader.getEventType() == 8)
/*     */             break; 
/* 498 */           c.create(this.reader);
/*     */ 
/*     */ 
/*     */           
/* 502 */           if (this.reader.isWhiteSpace()) {
/* 503 */             this.bodyEpilogue = XMLStreamReaderUtil.currentWhiteSpaceContent(this.reader);
/*     */             
/*     */             continue;
/*     */           } 
/* 507 */           this.bodyEpilogue = null;
/*     */         } 
/*     */       }
/*     */       
/* 511 */       c.storeEndElement();
/* 512 */       c.storeEndElement();
/* 513 */       c.storeEndElement();
/*     */       
/* 515 */       XMLStreamReaderUtil.readRest(this.reader);
/* 516 */       XMLStreamReaderUtil.close(this.reader);
/* 517 */       XMLStreamReaderFactory.recycle(this.reader);
/*     */       
/* 519 */       this.reader = (XMLStreamReader)xsb.readAsXMLStreamReader();
/* 520 */       StreamReaderBufferProcessor streamReaderBufferProcessor = xsb.readAsXMLStreamReader();
/*     */ 
/*     */       
/* 523 */       proceedToRootElement(this.reader);
/* 524 */       proceedToRootElement((XMLStreamReader)streamReaderBufferProcessor);
/*     */       
/* 526 */       return (Message)new StreamMessage(this.envelopeTag, this.headerTag, this.attachmentSet, HeaderList.copy(this.headers), this.bodyPrologue, this.bodyTag, this.bodyEpilogue, (XMLStreamReader)streamReaderBufferProcessor, this.soapVersion);
/* 527 */     } catch (XMLStreamException e) {
/* 528 */       throw new WebServiceException("Failed to copy a message", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void proceedToRootElement(XMLStreamReader xsr) throws XMLStreamException {
/* 533 */     assert xsr.getEventType() == 7;
/* 534 */     xsr.nextTag();
/* 535 */     xsr.nextTag();
/* 536 */     xsr.nextTag();
/* 537 */     assert xsr.getEventType() == 1 || xsr.getEventType() == 2;
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 541 */     contentHandler.setDocumentLocator(NULL_LOCATOR);
/* 542 */     contentHandler.startDocument();
/* 543 */     this.envelopeTag.writeStart(contentHandler);
/* 544 */     this.headerTag.writeStart(contentHandler);
/* 545 */     if (hasHeaders()) {
/* 546 */       HeaderList headers = getHeaders();
/* 547 */       int len = headers.size();
/* 548 */       for (int i = 0; i < len; i++)
/*     */       {
/* 550 */         headers.get(i).writeTo(contentHandler, errorHandler);
/*     */       }
/*     */     } 
/* 553 */     this.headerTag.writeEnd(contentHandler);
/* 554 */     this.bodyTag.writeStart(contentHandler);
/* 555 */     writePayloadTo(contentHandler, errorHandler, true);
/* 556 */     this.bodyTag.writeEnd(contentHandler);
/* 557 */     this.envelopeTag.writeEnd(contentHandler);
/* 558 */     contentHandler.endDocument();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean unconsumed() {
/* 569 */     if (this.payloadLocalName == null) {
/* 570 */       return true;
/*     */     }
/* 572 */     if (this.reader.getEventType() != 1) {
/* 573 */       AssertionError error = new AssertionError("StreamMessage has been already consumed. See the nested exception for where it's consumed");
/* 574 */       error.initCause(this.consumedAt);
/* 575 */       throw error;
/*     */     } 
/* 577 */     this.consumedAt = (new Exception()).fillInStackTrace();
/* 578 */     return true;
/*     */   }
/*     */   
/*     */   private static void create(SOAPVersion v) {
/* 582 */     int base = v.ordinal() * 3;
/* 583 */     DEFAULT_TAGS[base] = new TagInfoset(v.nsUri, "Envelope", "S", EMPTY_ATTS, new String[] { "S", v.nsUri });
/* 584 */     DEFAULT_TAGS[base + 1] = new TagInfoset(v.nsUri, "Header", "S", EMPTY_ATTS, new String[0]);
/* 585 */     DEFAULT_TAGS[base + 2] = new TagInfoset(v.nsUri, "Body", "S", EMPTY_ATTS, new String[0]);
/*     */   }
/*     */   
/*     */   public String getBodyPrologue() {
/* 589 */     return this.bodyPrologue;
/*     */   }
/*     */   
/*     */   public String getBodyEpilogue() {
/* 593 */     return this.bodyEpilogue;
/*     */   }
/*     */   
/*     */   public XMLStreamReader getReader() {
/* 597 */     assert unconsumed();
/* 598 */     return this.reader;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\stream\StreamMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */