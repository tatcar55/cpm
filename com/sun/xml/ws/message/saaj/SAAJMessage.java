/*     */ package com.sun.xml.ws.message.saaj;
/*     */ 
/*     */ import com.sun.istack.FragmentContentHandler;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.istack.XMLStreamException2;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.bind.unmarshaller.DOMScanner;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.Attachment;
/*     */ import com.sun.xml.ws.api.message.AttachmentEx;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.ws.message.AttachmentUnmarshallerImpl;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.ws.streaming.DOMStreamReader;
/*     */ import com.sun.xml.ws.util.ASCIIUtility;
/*     */ import com.sun.xml.ws.util.DOMUtil;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.attachment.AttachmentUnmarshaller;
/*     */ import javax.xml.soap.AttachmentPart;
/*     */ import javax.xml.soap.MimeHeader;
/*     */ import javax.xml.soap.SOAPBody;
/*     */ import javax.xml.soap.SOAPEnvelope;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPHeader;
/*     */ import javax.xml.soap.SOAPHeaderElement;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
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
/*     */ public class SAAJMessage
/*     */   extends Message
/*     */ {
/*     */   private boolean parsedMessage;
/*     */   private boolean accessedMessage;
/*     */   private final SOAPMessage sm;
/*     */   private HeaderList headers;
/*     */   private List<Element> bodyParts;
/*     */   private Element payload;
/*     */   private String payloadLocalName;
/*     */   private String payloadNamespace;
/*     */   private SOAPVersion soapVersion;
/*     */   private NamedNodeMap bodyAttrs;
/*     */   private NamedNodeMap headerAttrs;
/*     */   private NamedNodeMap envelopeAttrs;
/*     */   
/*     */   public SAAJMessage(SOAPMessage sm) {
/* 111 */     this.sm = sm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SAAJMessage(HeaderList headers, AttachmentSet as, SOAPMessage sm, SOAPVersion version) {
/* 121 */     this.sm = sm;
/* 122 */     parse();
/* 123 */     if (headers == null)
/* 124 */       headers = new HeaderList(version); 
/* 125 */     this.headers = headers;
/* 126 */     this.attachmentSet = as;
/*     */   }
/*     */   
/*     */   private void parse() {
/* 130 */     if (!this.parsedMessage) {
/*     */       try {
/* 132 */         access();
/* 133 */         if (this.headers == null)
/* 134 */           this.headers = new HeaderList(getSOAPVersion()); 
/* 135 */         SOAPHeader header = this.sm.getSOAPHeader();
/* 136 */         if (header != null) {
/* 137 */           this.headerAttrs = header.getAttributes();
/* 138 */           Iterator<SOAPHeaderElement> iter = header.examineAllHeaderElements();
/* 139 */           while (iter.hasNext()) {
/* 140 */             this.headers.add((Header)new SAAJHeader(iter.next()));
/*     */           }
/*     */         } 
/* 143 */         this.attachmentSet = new SAAJAttachmentSet(this.sm);
/*     */         
/* 145 */         this.parsedMessage = true;
/* 146 */       } catch (SOAPException e) {
/* 147 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected void access() {
/* 153 */     if (!this.accessedMessage) {
/*     */       try {
/* 155 */         this.envelopeAttrs = this.sm.getSOAPPart().getEnvelope().getAttributes();
/* 156 */         Node body = this.sm.getSOAPBody();
/* 157 */         this.bodyAttrs = body.getAttributes();
/* 158 */         this.soapVersion = SOAPVersion.fromNsUri(body.getNamespaceURI());
/*     */         
/* 160 */         this.bodyParts = DOMUtil.getChildElements(body);
/*     */         
/* 162 */         this.payload = (this.bodyParts.size() > 0) ? this.bodyParts.get(0) : null;
/*     */ 
/*     */ 
/*     */         
/* 166 */         if (this.payload != null) {
/* 167 */           this.payloadLocalName = this.payload.getLocalName();
/* 168 */           this.payloadNamespace = this.payload.getNamespaceURI();
/*     */         } 
/* 170 */         this.accessedMessage = true;
/* 171 */       } catch (SOAPException e) {
/* 172 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/* 178 */     parse();
/* 179 */     return (this.headers.size() > 0);
/*     */   }
/*     */   @NotNull
/*     */   public HeaderList getHeaders() {
/* 183 */     parse();
/* 184 */     return this.headers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public AttachmentSet getAttachments() {
/* 192 */     parse();
/* 193 */     return this.attachmentSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasAttachments() {
/* 202 */     parse();
/* 203 */     return (this.attachmentSet != null);
/*     */   }
/*     */   @Nullable
/*     */   public String getPayloadLocalPart() {
/* 207 */     access();
/* 208 */     return this.payloadLocalName;
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 212 */     access();
/* 213 */     return this.payloadNamespace;
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/* 217 */     access();
/* 218 */     return (this.payloadNamespace != null);
/*     */   }
/*     */   
/*     */   private void addAttributes(Element e, NamedNodeMap attrs) {
/* 222 */     if (attrs == null)
/*     */       return; 
/* 224 */     String elPrefix = e.getPrefix();
/* 225 */     for (int i = 0; i < attrs.getLength(); i++) {
/* 226 */       Attr a = (Attr)attrs.item(i);
/*     */       
/* 228 */       if ("xmlns".equals(a.getPrefix()) || "xmlns".equals(a.getLocalName())) {
/* 229 */         if (elPrefix != null || !a.getLocalName().equals("xmlns"))
/*     */         {
/*     */           
/* 232 */           if (elPrefix == null || !"xmlns".equals(a.getPrefix()) || !elPrefix.equals(a.getLocalName()))
/*     */           {
/*     */ 
/*     */             
/* 236 */             e.setAttributeNS(a.getNamespaceURI(), a.getName(), a.getValue()); } 
/*     */         }
/*     */       } else {
/* 239 */         e.setAttributeNS(a.getNamespaceURI(), a.getName(), a.getValue());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public Source readEnvelopeAsSource() {
/*     */     try {
/* 245 */       if (!this.parsedMessage) {
/* 246 */         SOAPEnvelope sOAPEnvelope = this.sm.getSOAPPart().getEnvelope();
/* 247 */         return new DOMSource(sOAPEnvelope);
/*     */       } 
/*     */       
/* 250 */       SOAPMessage msg = this.soapVersion.getMessageFactory().createMessage();
/* 251 */       addAttributes(msg.getSOAPPart().getEnvelope(), this.envelopeAttrs);
/*     */       
/* 253 */       SOAPBody newBody = msg.getSOAPPart().getEnvelope().getBody();
/* 254 */       addAttributes(newBody, this.bodyAttrs);
/* 255 */       for (Element part : this.bodyParts) {
/* 256 */         Node n = newBody.getOwnerDocument().importNode(part, true);
/* 257 */         newBody.appendChild(n);
/*     */       } 
/* 259 */       addAttributes(msg.getSOAPHeader(), this.headerAttrs);
/* 260 */       for (Header header : this.headers) {
/* 261 */         header.writeTo(msg);
/*     */       }
/* 263 */       SOAPEnvelope se = msg.getSOAPPart().getEnvelope();
/* 264 */       return new DOMSource(se);
/*     */     }
/* 266 */     catch (SOAPException e) {
/* 267 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 272 */     if (!this.parsedMessage) {
/* 273 */       return this.sm;
/*     */     }
/* 275 */     SOAPMessage msg = this.soapVersion.getMessageFactory().createMessage();
/* 276 */     addAttributes(msg.getSOAPPart().getEnvelope(), this.envelopeAttrs);
/* 277 */     SOAPBody newBody = msg.getSOAPPart().getEnvelope().getBody();
/* 278 */     addAttributes(newBody, this.bodyAttrs);
/* 279 */     for (Element part : this.bodyParts) {
/* 280 */       Node n = newBody.getOwnerDocument().importNode(part, true);
/* 281 */       newBody.appendChild(n);
/*     */     } 
/* 283 */     addAttributes(msg.getSOAPHeader(), this.headerAttrs);
/* 284 */     for (Header header : this.headers) {
/* 285 */       header.writeTo(msg);
/*     */     }
/* 287 */     for (Attachment att : getAttachments()) {
/* 288 */       AttachmentPart part = msg.createAttachmentPart();
/* 289 */       part.setDataHandler(att.asDataHandler());
/* 290 */       part.setContentId('<' + att.getContentId() + '>');
/* 291 */       addCustomMimeHeaders(att, part);
/* 292 */       msg.addAttachmentPart(part);
/*     */     } 
/* 294 */     msg.saveChanges();
/* 295 */     return msg;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addCustomMimeHeaders(Attachment att, AttachmentPart part) {
/* 300 */     if (att instanceof AttachmentEx) {
/* 301 */       Iterator<AttachmentEx.MimeHeader> allMimeHeaders = ((AttachmentEx)att).getMimeHeaders();
/* 302 */       while (allMimeHeaders.hasNext()) {
/* 303 */         AttachmentEx.MimeHeader mh = allMimeHeaders.next();
/* 304 */         String name = mh.getName();
/* 305 */         if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Id".equalsIgnoreCase(name))
/*     */         {
/* 307 */           part.addMimeHeader(name, mh.getValue());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 314 */     access();
/* 315 */     return (this.payload != null) ? new DOMSource(this.payload) : null;
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 319 */     access();
/* 320 */     if (this.payload != null) {
/* 321 */       if (hasAttachments())
/* 322 */         unmarshaller.setAttachmentUnmarshaller((AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(getAttachments())); 
/* 323 */       return (T)unmarshaller.unmarshal(this.payload);
/*     */     } 
/*     */     
/* 326 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 331 */     access();
/* 332 */     if (this.payload != null)
/* 333 */       return (T)bridge.unmarshal(this.payload, hasAttachments() ? (AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(getAttachments()) : null); 
/* 334 */     return null;
/*     */   }
/*     */   public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/* 337 */     access();
/* 338 */     if (this.payload != null)
/* 339 */       return (T)bridge.unmarshal(this.payload, hasAttachments() ? (AttachmentUnmarshaller)new AttachmentUnmarshallerImpl(getAttachments()) : null); 
/* 340 */     return null;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/* 344 */     access();
/* 345 */     if (this.payload != null) {
/* 346 */       DOMStreamReader dss = new DOMStreamReader();
/* 347 */       dss.setCurrentNode(this.payload);
/* 348 */       dss.nextTag();
/* 349 */       assert dss.getEventType() == 1;
/* 350 */       return (XMLStreamReader)dss;
/*     */     } 
/* 352 */     return null;
/*     */   }
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 356 */     access();
/*     */     try {
/* 358 */       for (Element part : this.bodyParts)
/* 359 */         DOMUtil.serializeNode(part, sw); 
/* 360 */     } catch (XMLStreamException e) {
/* 361 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeTo(XMLStreamWriter writer) throws XMLStreamException {
/*     */     try {
/* 367 */       writer.writeStartDocument();
/* 368 */       if (!this.parsedMessage) {
/* 369 */         DOMUtil.serializeNode(this.sm.getSOAPPart().getEnvelope(), writer);
/*     */       } else {
/* 371 */         SOAPEnvelope env = this.sm.getSOAPPart().getEnvelope();
/* 372 */         DOMUtil.writeTagWithAttributes(env, writer);
/* 373 */         if (hasHeaders()) {
/* 374 */           if (env.getHeader() != null) {
/* 375 */             DOMUtil.writeTagWithAttributes(env.getHeader(), writer);
/*     */           } else {
/* 377 */             writer.writeStartElement(env.getPrefix(), "Header", env.getNamespaceURI());
/*     */           } 
/* 379 */           int len = this.headers.size();
/* 380 */           for (int i = 0; i < len; i++) {
/* 381 */             this.headers.get(i).writeTo(writer);
/*     */           }
/* 383 */           writer.writeEndElement();
/*     */         } 
/*     */         
/* 386 */         DOMUtil.serializeNode(this.sm.getSOAPBody(), writer);
/* 387 */         writer.writeEndElement();
/*     */       } 
/* 389 */       writer.writeEndDocument();
/* 390 */       writer.flush();
/* 391 */     } catch (SOAPException ex) {
/* 392 */       throw new XMLStreamException2(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 398 */     String soapNsUri = this.soapVersion.nsUri;
/* 399 */     if (!this.parsedMessage) {
/* 400 */       DOMScanner ds = new DOMScanner();
/* 401 */       ds.setContentHandler(contentHandler);
/* 402 */       ds.scan(this.sm.getSOAPPart());
/*     */     } else {
/* 404 */       contentHandler.setDocumentLocator(NULL_LOCATOR);
/* 405 */       contentHandler.startDocument();
/* 406 */       contentHandler.startPrefixMapping("S", soapNsUri);
/* 407 */       startPrefixMapping(contentHandler, this.envelopeAttrs, "S");
/* 408 */       contentHandler.startElement(soapNsUri, "Envelope", "S:Envelope", getAttributes(this.envelopeAttrs));
/* 409 */       if (hasHeaders()) {
/* 410 */         startPrefixMapping(contentHandler, this.headerAttrs, "S");
/* 411 */         contentHandler.startElement(soapNsUri, "Header", "S:Header", getAttributes(this.headerAttrs));
/* 412 */         HeaderList headers = getHeaders();
/* 413 */         int len = headers.size();
/* 414 */         for (int i = 0; i < len; i++)
/*     */         {
/* 416 */           headers.get(i).writeTo(contentHandler, errorHandler);
/*     */         }
/* 418 */         endPrefixMapping(contentHandler, this.headerAttrs, "S");
/* 419 */         contentHandler.endElement(soapNsUri, "Header", "S:Header");
/*     */       } 
/*     */       
/* 422 */       startPrefixMapping(contentHandler, this.bodyAttrs, "S");
/*     */       
/* 424 */       contentHandler.startElement(soapNsUri, "Body", "S:Body", getAttributes(this.bodyAttrs));
/* 425 */       writePayloadTo(contentHandler, errorHandler, true);
/* 426 */       endPrefixMapping(contentHandler, this.bodyAttrs, "S");
/* 427 */       contentHandler.endElement(soapNsUri, "Body", "S:Body");
/* 428 */       endPrefixMapping(contentHandler, this.envelopeAttrs, "S");
/* 429 */       contentHandler.endElement(soapNsUri, "Envelope", "S:Envelope");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AttributesImpl getAttributes(NamedNodeMap attrs) {
/* 438 */     AttributesImpl atts = new AttributesImpl();
/* 439 */     if (attrs == null)
/* 440 */       return EMPTY_ATTS; 
/* 441 */     for (int i = 0; i < attrs.getLength(); i++) {
/* 442 */       Attr a = (Attr)attrs.item(i);
/*     */       
/* 444 */       if (!"xmlns".equals(a.getPrefix()) && !"xmlns".equals(a.getLocalName()))
/*     */       {
/*     */         
/* 447 */         atts.addAttribute(fixNull(a.getNamespaceURI()), a.getLocalName(), a.getName(), a.getSchemaTypeInfo().getTypeName(), a.getValue()); } 
/*     */     } 
/* 449 */     return atts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startPrefixMapping(ContentHandler contentHandler, NamedNodeMap attrs, String excludePrefix) throws SAXException {
/* 460 */     if (attrs == null)
/*     */       return; 
/* 462 */     for (int i = 0; i < attrs.getLength(); i++) {
/* 463 */       Attr a = (Attr)attrs.item(i);
/*     */       
/* 465 */       if (("xmlns".equals(a.getPrefix()) || "xmlns".equals(a.getLocalName())) && 
/* 466 */         !fixNull(a.getPrefix()).equals(excludePrefix)) {
/* 467 */         contentHandler.startPrefixMapping(fixNull(a.getPrefix()), a.getNamespaceURI());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void endPrefixMapping(ContentHandler contentHandler, NamedNodeMap attrs, String excludePrefix) throws SAXException {
/* 474 */     if (attrs == null)
/*     */       return; 
/* 476 */     for (int i = 0; i < attrs.getLength(); i++) {
/* 477 */       Attr a = (Attr)attrs.item(i);
/*     */       
/* 479 */       if (("xmlns".equals(a.getPrefix()) || "xmlns".equals(a.getLocalName())) && 
/* 480 */         !fixNull(a.getPrefix()).equals(excludePrefix)) {
/* 481 */         contentHandler.endPrefixMapping(fixNull(a.getPrefix()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static String fixNull(String s) {
/* 488 */     if (s == null) return ""; 
/* 489 */     return s;
/*     */   }
/*     */   private void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/*     */     FragmentContentHandler fragmentContentHandler;
/* 493 */     if (fragment)
/* 494 */       fragmentContentHandler = new FragmentContentHandler(contentHandler); 
/* 495 */     DOMScanner ds = new DOMScanner();
/* 496 */     ds.setContentHandler((ContentHandler)fragmentContentHandler);
/* 497 */     ds.scan(this.payload);
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
/*     */   public Message copy() {
/*     */     try {
/* 523 */       if (!this.parsedMessage) {
/* 524 */         return new SAAJMessage(readAsSOAPMessage());
/*     */       }
/* 526 */       SOAPMessage msg = this.soapVersion.getMessageFactory().createMessage();
/* 527 */       SOAPBody newBody = msg.getSOAPPart().getEnvelope().getBody();
/* 528 */       for (Element part : this.bodyParts) {
/* 529 */         Node n = newBody.getOwnerDocument().importNode(part, true);
/* 530 */         newBody.appendChild(n);
/*     */       } 
/* 532 */       addAttributes(newBody, this.bodyAttrs);
/* 533 */       return new SAAJMessage(getHeaders(), getAttachments(), msg, this.soapVersion);
/*     */     }
/* 535 */     catch (SOAPException e) {
/* 536 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/* 539 */   private static final AttributesImpl EMPTY_ATTS = new AttributesImpl();
/* 540 */   private static final LocatorImpl NULL_LOCATOR = new LocatorImpl();
/*     */   
/*     */   private static class SAAJAttachment
/*     */     implements AttachmentEx
/*     */   {
/*     */     final AttachmentPart ap;
/*     */     String contentIdNoAngleBracket;
/*     */     
/*     */     public SAAJAttachment(AttachmentPart part) {
/* 549 */       this.ap = part;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getContentId() {
/* 556 */       if (this.contentIdNoAngleBracket == null) {
/* 557 */         this.contentIdNoAngleBracket = this.ap.getContentId();
/* 558 */         if (this.contentIdNoAngleBracket != null && this.contentIdNoAngleBracket.charAt(0) == '<')
/* 559 */           this.contentIdNoAngleBracket = this.contentIdNoAngleBracket.substring(1, this.contentIdNoAngleBracket.length() - 1); 
/*     */       } 
/* 561 */       return this.contentIdNoAngleBracket;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getContentType() {
/* 568 */       return this.ap.getContentType();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] asByteArray() {
/*     */       try {
/* 576 */         return this.ap.getRawContentBytes();
/* 577 */       } catch (SOAPException e) {
/* 578 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DataHandler asDataHandler() {
/*     */       try {
/* 587 */         return this.ap.getDataHandler();
/* 588 */       } catch (SOAPException e) {
/* 589 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Source asSource() {
/*     */       try {
/* 599 */         return new StreamSource(this.ap.getRawContent());
/* 600 */       } catch (SOAPException e) {
/* 601 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public InputStream asInputStream() {
/*     */       try {
/* 610 */         return this.ap.getRawContent();
/* 611 */       } catch (SOAPException e) {
/* 612 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeTo(OutputStream os) throws IOException {
/*     */       try {
/* 621 */         ASCIIUtility.copyStream(this.ap.getRawContent(), os);
/* 622 */       } catch (SOAPException e) {
/* 623 */         throw new WebServiceException(e);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void writeTo(SOAPMessage saaj) {
/* 631 */       saaj.addAttachmentPart(this.ap);
/*     */     }
/*     */     
/*     */     AttachmentPart asAttachmentPart() {
/* 635 */       return this.ap;
/*     */     }
/*     */     
/*     */     public Iterator<AttachmentEx.MimeHeader> getMimeHeaders() {
/* 639 */       final Iterator it = this.ap.getAllMimeHeaders();
/* 640 */       return new Iterator<AttachmentEx.MimeHeader>() {
/*     */           public boolean hasNext() {
/* 642 */             return it.hasNext();
/*     */           }
/*     */           
/*     */           public AttachmentEx.MimeHeader next() {
/* 646 */             final MimeHeader mh = it.next();
/* 647 */             return new AttachmentEx.MimeHeader() {
/*     */                 public String getName() {
/* 649 */                   return mh.getName();
/*     */                 }
/*     */                 
/*     */                 public String getValue() {
/* 653 */                   return mh.getValue();
/*     */                 }
/*     */               };
/*     */           }
/*     */           
/*     */           public void remove() {
/* 659 */             throw new UnsupportedOperationException();
/*     */           }
/*     */         };
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SAAJAttachmentSet
/*     */     implements AttachmentSet
/*     */   {
/*     */     private Map<String, Attachment> attMap;
/*     */ 
/*     */     
/*     */     private Iterator attIter;
/*     */ 
/*     */     
/*     */     public SAAJAttachmentSet(SOAPMessage sm) {
/* 677 */       this.attIter = sm.getAttachments();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Attachment get(String contentId) {
/* 688 */       if (this.attMap == null) {
/* 689 */         if (!this.attIter.hasNext())
/* 690 */           return null; 
/* 691 */         this.attMap = createAttachmentMap();
/*     */       } 
/* 693 */       if (contentId.charAt(0) != '<') {
/* 694 */         return this.attMap.get('<' + contentId + '>');
/*     */       }
/* 696 */       return this.attMap.get(contentId);
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 700 */       if (this.attMap != null) {
/* 701 */         return this.attMap.isEmpty();
/*     */       }
/* 703 */       return !this.attIter.hasNext();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Iterator<Attachment> iterator() {
/* 712 */       if (this.attMap == null) {
/* 713 */         this.attMap = createAttachmentMap();
/*     */       }
/* 715 */       return this.attMap.values().iterator();
/*     */     }
/*     */     
/*     */     private Map<String, Attachment> createAttachmentMap() {
/* 719 */       HashMap<String, Attachment> map = new HashMap<String, Attachment>();
/* 720 */       while (this.attIter.hasNext()) {
/* 721 */         AttachmentPart ap = this.attIter.next();
/* 722 */         map.put(ap.getContentId(), new SAAJMessage.SAAJAttachment(ap));
/*     */       } 
/* 724 */       return map;
/*     */     }
/*     */     
/*     */     public void add(Attachment att) {
/* 728 */       this.attMap.put('<' + att.getContentId() + '>', att);
/*     */     }
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 733 */     return this.soapVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public MessageHeaders getMessageHeaders() {
/* 738 */     return (MessageHeaders)getHeaders();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\saaj\SAAJMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */