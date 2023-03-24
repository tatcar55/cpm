/*     */ package com.sun.xml.messaging.saaj.soap;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.SOAPExceptionImpl;
/*     */ import com.sun.xml.messaging.saaj.packaging.mime.internet.MimeBodyPart;
/*     */ import com.sun.xml.messaging.saaj.soap.impl.ElementImpl;
/*     */ import com.sun.xml.messaging.saaj.util.ByteOutputStream;
/*     */ import com.sun.xml.messaging.saaj.util.FastInfosetReflection;
/*     */ import com.sun.xml.messaging.saaj.util.JAXMStreamSource;
/*     */ import com.sun.xml.messaging.saaj.util.MimeHeadersUtil;
/*     */ import com.sun.xml.messaging.saaj.util.SAAJUtil;
/*     */ import com.sun.xml.messaging.saaj.util.XMLDeclarationParser;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PushbackReader;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.activation.DataHandler;
/*     */ import javax.activation.DataSource;
/*     */ import javax.xml.soap.MimeHeaders;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPEnvelope;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPPart;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.CDATASection;
/*     */ import org.w3c.dom.Comment;
/*     */ import org.w3c.dom.DOMConfiguration;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.DOMImplementation;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.DocumentFragment;
/*     */ import org.w3c.dom.DocumentType;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.EntityReference;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.ProcessingInstruction;
/*     */ import org.w3c.dom.Text;
/*     */ import org.w3c.dom.UserDataHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SOAPPartImpl
/*     */   extends SOAPPart
/*     */   implements SOAPDocument
/*     */ {
/*  73 */   protected static final Logger log = Logger.getLogger("com.sun.xml.messaging.saaj.soap", "com.sun.xml.messaging.saaj.soap.LocalStrings");
/*     */ 
/*     */   
/*     */   protected MimeHeaders headers;
/*     */ 
/*     */   
/*     */   protected Envelope envelope;
/*     */   
/*     */   protected Source source;
/*     */   
/*     */   protected SOAPDocumentImpl document;
/*     */   
/*     */   private boolean sourceWasSet = false;
/*     */   
/*     */   protected boolean omitXmlDecl = true;
/*     */   
/*  89 */   protected String sourceCharsetEncoding = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MessageImpl message;
/*     */ 
/*     */ 
/*     */   
/*  98 */   static final boolean lazyContentLength = SAAJUtil.getSystemBoolean("saaj.lazy.contentlength");
/*     */ 
/*     */   
/*     */   protected SOAPPartImpl() {
/* 102 */     this(null);
/*     */   }
/*     */   
/*     */   protected SOAPPartImpl(MessageImpl message) {
/* 106 */     this.document = new SOAPDocumentImpl(this);
/* 107 */     this.headers = new MimeHeaders();
/* 108 */     this.message = message;
/* 109 */     this.headers.setHeader("Content-Type", getContentType());
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract String getContentType();
/*     */ 
/*     */   
/*     */   protected abstract Envelope createEnvelopeFromSource() throws SOAPException;
/*     */ 
/*     */   
/*     */   protected String getContentTypeString() {
/* 120 */     return getContentType();
/*     */   } protected abstract Envelope createEmptyEnvelope(String paramString) throws SOAPException;
/*     */   protected abstract SOAPPartImpl duplicateType();
/*     */   public boolean isFastInfoset() {
/* 124 */     return (this.message != null) ? this.message.isFastInfoset() : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPEnvelope getEnvelope() throws SOAPException {
/* 133 */     if (this.sourceWasSet) {
/* 134 */       this.sourceWasSet = false;
/*     */     }
/* 136 */     lookForEnvelope();
/* 137 */     if (this.envelope != null) {
/* 138 */       if (this.source != null) {
/* 139 */         this.document.removeChild(this.envelope);
/* 140 */         this.envelope = createEnvelopeFromSource();
/*     */       } 
/* 142 */     } else if (this.source != null) {
/* 143 */       this.envelope = createEnvelopeFromSource();
/*     */     } else {
/* 145 */       this.envelope = createEmptyEnvelope(null);
/* 146 */       this.document.insertBefore(this.envelope, (Node)null);
/*     */     } 
/* 148 */     return this.envelope;
/*     */   }
/*     */   
/*     */   protected void lookForEnvelope() throws SOAPException {
/* 152 */     Element envelopeChildElement = this.document.doGetDocumentElement();
/* 153 */     if (envelopeChildElement == null || envelopeChildElement instanceof Envelope)
/* 154 */     { this.envelope = (Envelope)envelopeChildElement; }
/* 155 */     else { if (!(envelopeChildElement instanceof ElementImpl)) {
/* 156 */         log.severe("SAAJ0512.soap.incorrect.factory.used");
/* 157 */         throw new SOAPExceptionImpl("Unable to create envelope: incorrect factory used during tree construction");
/*     */       } 
/* 159 */       ElementImpl soapElement = (ElementImpl)envelopeChildElement;
/* 160 */       if (soapElement.getLocalName().equalsIgnoreCase("Envelope")) {
/* 161 */         String prefix = soapElement.getPrefix();
/* 162 */         String uri = (prefix == null) ? soapElement.getNamespaceURI() : soapElement.getNamespaceURI(prefix);
/* 163 */         if (!uri.equals("http://schemas.xmlsoap.org/soap/envelope/") && !uri.equals("http://www.w3.org/2003/05/soap-envelope")) {
/* 164 */           log.severe("SAAJ0513.soap.unknown.ns");
/* 165 */           throw new SOAPVersionMismatchException("Unable to create envelope from given source because the namespace was not recognized");
/*     */         } 
/*     */       } else {
/* 168 */         log.severe("SAAJ0514.soap.root.elem.not.named.envelope");
/* 169 */         throw new SOAPExceptionImpl("Unable to create envelope from given source because the root element is not named \"Envelope\"");
/*     */       }  }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeAllMimeHeaders() {
/* 176 */     this.headers.removeAllHeaders();
/*     */   }
/*     */   
/*     */   public void removeMimeHeader(String header) {
/* 180 */     this.headers.removeHeader(header);
/*     */   }
/*     */   
/*     */   public String[] getMimeHeader(String name) {
/* 184 */     return this.headers.getHeader(name);
/*     */   }
/*     */   
/*     */   public void setMimeHeader(String name, String value) {
/* 188 */     this.headers.setHeader(name, value);
/*     */   }
/*     */   
/*     */   public void addMimeHeader(String name, String value) {
/* 192 */     this.headers.addHeader(name, value);
/*     */   }
/*     */   
/*     */   public Iterator getAllMimeHeaders() {
/* 196 */     return this.headers.getAllHeaders();
/*     */   }
/*     */   
/*     */   public Iterator getMatchingMimeHeaders(String[] names) {
/* 200 */     return this.headers.getMatchingHeaders(names);
/*     */   }
/*     */   
/*     */   public Iterator getNonMatchingMimeHeaders(String[] names) {
/* 204 */     return this.headers.getNonMatchingHeaders(names);
/*     */   }
/*     */   
/*     */   public Source getContent() throws SOAPException {
/* 208 */     if (this.source != null) {
/* 209 */       InputStream bis = null;
/* 210 */       if (this.source instanceof JAXMStreamSource) {
/* 211 */         StreamSource streamSource = (StreamSource)this.source;
/* 212 */         bis = streamSource.getInputStream();
/* 213 */       } else if (FastInfosetReflection.isFastInfosetSource(this.source)) {
/*     */         
/* 215 */         SAXSource saxSource = (SAXSource)this.source;
/* 216 */         bis = saxSource.getInputSource().getByteStream();
/*     */       } 
/*     */       
/* 219 */       if (bis != null) {
/*     */         try {
/* 221 */           bis.reset();
/* 222 */         } catch (IOException e) {}
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 232 */       return this.source;
/*     */     } 
/*     */     
/* 235 */     return ((Envelope)getEnvelope()).getContent();
/*     */   }
/*     */   
/*     */   public void setContent(Source source) throws SOAPException {
/*     */     try {
/* 240 */       if (source instanceof StreamSource) {
/* 241 */         InputStream is = ((StreamSource)source).getInputStream();
/* 242 */         Reader rdr = ((StreamSource)source).getReader();
/*     */         
/* 244 */         if (is != null) {
/* 245 */           this.source = (Source)new JAXMStreamSource(is);
/* 246 */         } else if (rdr != null) {
/* 247 */           this.source = (Source)new JAXMStreamSource(rdr);
/*     */         } else {
/* 249 */           log.severe("SAAJ0544.soap.no.valid.reader.for.src");
/* 250 */           throw new SOAPExceptionImpl("Source does not have a valid Reader or InputStream");
/*     */         }
/*     */       
/* 253 */       } else if (FastInfosetReflection.isFastInfosetSource(source)) {
/*     */         
/* 255 */         InputStream is = FastInfosetReflection.FastInfosetSource_getInputStream(source);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 262 */         if (!(is instanceof com.sun.xml.messaging.saaj.util.ByteInputStream)) {
/* 263 */           ByteOutputStream bout = new ByteOutputStream();
/* 264 */           bout.write(is);
/*     */ 
/*     */           
/* 267 */           FastInfosetReflection.FastInfosetSource_setInputStream(source, (InputStream)bout.newInputStream());
/*     */         } 
/*     */         
/* 270 */         this.source = source;
/*     */       } else {
/*     */         
/* 273 */         this.source = source;
/*     */       } 
/* 275 */       this.sourceWasSet = true;
/*     */     }
/* 277 */     catch (Exception ex) {
/* 278 */       ex.printStackTrace();
/*     */       
/* 280 */       log.severe("SAAJ0545.soap.cannot.set.src.for.part");
/* 281 */       throw new SOAPExceptionImpl("Error setting the source for SOAPPart: " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InputStream getContentAsStream() throws IOException {
/* 287 */     if (this.source != null) {
/* 288 */       InputStream is = null;
/*     */ 
/*     */       
/* 291 */       if (this.source instanceof StreamSource && !isFastInfoset()) {
/* 292 */         is = ((StreamSource)this.source).getInputStream();
/*     */       }
/* 294 */       else if (FastInfosetReflection.isFastInfosetSource(this.source) && isFastInfoset()) {
/*     */ 
/*     */         
/*     */         try {
/*     */           
/* 299 */           is = FastInfosetReflection.FastInfosetSource_getInputStream(this.source);
/*     */         }
/* 301 */         catch (Exception e) {
/* 302 */           throw new IOException(e.toString());
/*     */         } 
/*     */       } 
/*     */       
/* 306 */       if (is != null) {
/* 307 */         if (lazyContentLength) {
/* 308 */           return is;
/*     */         }
/* 310 */         if (!(is instanceof com.sun.xml.messaging.saaj.util.ByteInputStream)) {
/* 311 */           log.severe("SAAJ0546.soap.stream.incorrect.type");
/* 312 */           throw new IOException("Internal error: stream not of the right type");
/*     */         } 
/* 314 */         return is;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 320 */     ByteOutputStream b = new ByteOutputStream();
/*     */     
/* 322 */     Envelope env = null;
/*     */     
/*     */     try {
/* 325 */       env = (Envelope)getEnvelope();
/* 326 */       env.output((OutputStream)b, isFastInfoset());
/*     */     }
/* 328 */     catch (SOAPException soapException) {
/* 329 */       log.severe("SAAJ0547.soap.cannot.externalize");
/* 330 */       throw new SOAPIOException("SOAP exception while trying to externalize: ", soapException);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 335 */     return (InputStream)b.newInputStream();
/*     */   }
/*     */   
/*     */   MimeBodyPart getMimePart() throws SOAPException {
/*     */     try {
/* 340 */       MimeBodyPart headerEnvelope = new MimeBodyPart();
/*     */       
/* 342 */       headerEnvelope.setDataHandler(getDataHandler());
/* 343 */       AttachmentPartImpl.copyMimeHeaders(this.headers, headerEnvelope);
/*     */       
/* 345 */       return headerEnvelope;
/* 346 */     } catch (SOAPException ex) {
/* 347 */       throw ex;
/* 348 */     } catch (Exception ex) {
/* 349 */       log.severe("SAAJ0548.soap.cannot.externalize.hdr");
/* 350 */       throw new SOAPExceptionImpl("Unable to externalize header", ex);
/*     */     } 
/*     */   }
/*     */   
/*     */   MimeHeaders getMimeHeaders() {
/* 355 */     return this.headers;
/*     */   }
/*     */   
/*     */   DataHandler getDataHandler() {
/* 359 */     DataSource ds = new DataSource() {
/*     */         public OutputStream getOutputStream() throws IOException {
/* 361 */           throw new IOException("Illegal Operation");
/*     */         }
/*     */         
/*     */         public String getContentType() {
/* 365 */           return SOAPPartImpl.this.getContentTypeString();
/*     */         }
/*     */         
/*     */         public String getName() {
/* 369 */           return SOAPPartImpl.this.getContentId();
/*     */         }
/*     */         
/*     */         public InputStream getInputStream() throws IOException {
/* 373 */           return SOAPPartImpl.this.getContentAsStream();
/*     */         }
/*     */       };
/* 376 */     return new DataHandler(ds);
/*     */   }
/*     */   
/*     */   public SOAPDocumentImpl getDocument() {
/* 380 */     handleNewSource();
/* 381 */     return this.document;
/*     */   }
/*     */   
/*     */   public SOAPPartImpl getSOAPPart() {
/* 385 */     return this;
/*     */   }
/*     */   
/*     */   public DocumentType getDoctype() {
/* 389 */     return this.document.getDoctype();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DOMImplementation getImplementation() {
/* 397 */     return this.document.getImplementation();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Element getDocumentElement() {
/*     */     try {
/* 405 */       getEnvelope();
/* 406 */     } catch (SOAPException e) {}
/*     */     
/* 408 */     return this.document.getDocumentElement();
/*     */   }
/*     */   
/*     */   protected void doGetDocumentElement() {
/* 412 */     handleNewSource();
/*     */     try {
/* 414 */       lookForEnvelope();
/* 415 */     } catch (SOAPException e) {}
/*     */   }
/*     */ 
/*     */   
/*     */   public Element createElement(String tagName) throws DOMException {
/* 420 */     return this.document.createElement(tagName);
/*     */   }
/*     */   
/*     */   public DocumentFragment createDocumentFragment() {
/* 424 */     return this.document.createDocumentFragment();
/*     */   }
/*     */   
/*     */   public Text createTextNode(String data) {
/* 428 */     return this.document.createTextNode(data);
/*     */   }
/*     */   
/*     */   public Comment createComment(String data) {
/* 432 */     return this.document.createComment(data);
/*     */   }
/*     */   
/*     */   public CDATASection createCDATASection(String data) throws DOMException {
/* 436 */     return this.document.createCDATASection(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProcessingInstruction createProcessingInstruction(String target, String data) throws DOMException {
/* 443 */     return this.document.createProcessingInstruction(target, data);
/*     */   }
/*     */   
/*     */   public Attr createAttribute(String name) throws DOMException {
/* 447 */     return this.document.createAttribute(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityReference createEntityReference(String name) throws DOMException {
/* 452 */     return this.document.createEntityReference(name);
/*     */   }
/*     */   
/*     */   public NodeList getElementsByTagName(String tagname) {
/* 456 */     handleNewSource();
/* 457 */     return this.document.getElementsByTagName(tagname);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node importNode(Node importedNode, boolean deep) throws DOMException {
/* 464 */     handleNewSource();
/* 465 */     return this.document.importNode(importedNode, deep);
/*     */   }
/*     */ 
/*     */   
/*     */   public Element createElementNS(String namespaceURI, String qualifiedName) throws DOMException {
/* 470 */     return this.document.createElementNS(namespaceURI, qualifiedName);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException {
/* 475 */     return this.document.createAttributeNS(namespaceURI, qualifiedName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
/* 481 */     handleNewSource();
/* 482 */     return this.document.getElementsByTagNameNS(namespaceURI, localName);
/*     */   }
/*     */   
/*     */   public Element getElementById(String elementId) {
/* 486 */     handleNewSource();
/* 487 */     return this.document.getElementById(elementId);
/*     */   }
/*     */   
/*     */   public Node appendChild(Node newChild) throws DOMException {
/* 491 */     handleNewSource();
/* 492 */     return this.document.appendChild(newChild);
/*     */   }
/*     */   
/*     */   public Node cloneNode(boolean deep) {
/* 496 */     handleNewSource();
/* 497 */     return this.document.cloneNode(deep);
/*     */   }
/*     */   
/*     */   protected SOAPPartImpl doCloneNode() {
/* 501 */     handleNewSource();
/* 502 */     SOAPPartImpl newSoapPart = duplicateType();
/*     */     
/* 504 */     newSoapPart.headers = MimeHeadersUtil.copy(this.headers);
/* 505 */     newSoapPart.source = this.source;
/* 506 */     return newSoapPart;
/*     */   }
/*     */   
/*     */   public NamedNodeMap getAttributes() {
/* 510 */     return this.document.getAttributes();
/*     */   }
/*     */   
/*     */   public NodeList getChildNodes() {
/* 514 */     handleNewSource();
/* 515 */     return this.document.getChildNodes();
/*     */   }
/*     */   
/*     */   public Node getFirstChild() {
/* 519 */     handleNewSource();
/* 520 */     return this.document.getFirstChild();
/*     */   }
/*     */   
/*     */   public Node getLastChild() {
/* 524 */     handleNewSource();
/* 525 */     return this.document.getLastChild();
/*     */   }
/*     */   
/*     */   public String getLocalName() {
/* 529 */     return this.document.getLocalName();
/*     */   }
/*     */   
/*     */   public String getNamespaceURI() {
/* 533 */     return this.document.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public Node getNextSibling() {
/* 537 */     handleNewSource();
/* 538 */     return this.document.getNextSibling();
/*     */   }
/*     */   
/*     */   public String getNodeName() {
/* 542 */     return this.document.getNodeName();
/*     */   }
/*     */   
/*     */   public short getNodeType() {
/* 546 */     return this.document.getNodeType();
/*     */   }
/*     */   
/*     */   public String getNodeValue() throws DOMException {
/* 550 */     return this.document.getNodeValue();
/*     */   }
/*     */   
/*     */   public Document getOwnerDocument() {
/* 554 */     return this.document.getOwnerDocument();
/*     */   }
/*     */   
/*     */   public Node getParentNode() {
/* 558 */     return this.document.getParentNode();
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/* 562 */     return this.document.getPrefix();
/*     */   }
/*     */   
/*     */   public Node getPreviousSibling() {
/* 566 */     return this.document.getPreviousSibling();
/*     */   }
/*     */   
/*     */   public boolean hasAttributes() {
/* 570 */     return this.document.hasAttributes();
/*     */   }
/*     */   
/*     */   public boolean hasChildNodes() {
/* 574 */     handleNewSource();
/* 575 */     return this.document.hasChildNodes();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node insertBefore(Node arg0, Node arg1) throws DOMException {
/* 582 */     handleNewSource();
/* 583 */     return this.document.insertBefore(arg0, arg1);
/*     */   }
/*     */   
/*     */   public boolean isSupported(String arg0, String arg1) {
/* 587 */     return this.document.isSupported(arg0, arg1);
/*     */   }
/*     */   
/*     */   public void normalize() {
/* 591 */     handleNewSource();
/* 592 */     this.document.normalize();
/*     */   }
/*     */ 
/*     */   
/*     */   public Node removeChild(Node arg0) throws DOMException {
/* 597 */     handleNewSource();
/* 598 */     return this.document.removeChild(arg0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Node replaceChild(Node arg0, Node arg1) throws DOMException {
/* 605 */     handleNewSource();
/* 606 */     return this.document.replaceChild(arg0, arg1);
/*     */   }
/*     */   
/*     */   public void setNodeValue(String arg0) throws DOMException {
/* 610 */     this.document.setNodeValue(arg0);
/*     */   }
/*     */   
/*     */   public void setPrefix(String arg0) throws DOMException {
/* 614 */     this.document.setPrefix(arg0);
/*     */   }
/*     */   
/*     */   private void handleNewSource() {
/* 618 */     if (this.sourceWasSet) {
/*     */       
/*     */       try {
/* 621 */         getEnvelope();
/* 622 */       } catch (SOAPException e) {}
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected XMLDeclarationParser lookForXmlDecl() throws SOAPException {
/* 628 */     if (this.source != null && this.source instanceof StreamSource) {
/*     */       
/* 630 */       Reader reader = null;
/*     */       
/* 632 */       InputStream inputStream = ((StreamSource)this.source).getInputStream();
/* 633 */       if (inputStream != null) {
/* 634 */         if (getSourceCharsetEncoding() == null) {
/* 635 */           reader = new InputStreamReader(inputStream);
/*     */         } else {
/*     */           try {
/* 638 */             reader = new InputStreamReader(inputStream, getSourceCharsetEncoding());
/*     */           
/*     */           }
/* 641 */           catch (UnsupportedEncodingException uee) {
/* 642 */             log.log(Level.SEVERE, "SAAJ0551.soap.unsupported.encoding", new Object[] { getSourceCharsetEncoding() });
/*     */ 
/*     */ 
/*     */             
/* 646 */             throw new SOAPExceptionImpl("Unsupported encoding " + getSourceCharsetEncoding(), uee);
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/* 652 */         reader = ((StreamSource)this.source).getReader();
/*     */       } 
/* 654 */       if (reader != null) {
/* 655 */         PushbackReader pushbackReader = new PushbackReader(reader, 4096);
/*     */         
/* 657 */         XMLDeclarationParser ev = new XMLDeclarationParser(pushbackReader);
/*     */         
/*     */         try {
/* 660 */           ev.parse();
/* 661 */         } catch (Exception e) {
/* 662 */           log.log(Level.SEVERE, "SAAJ0552.soap.xml.decl.parsing.failed");
/*     */ 
/*     */           
/* 665 */           throw new SOAPExceptionImpl("XML declaration parsing failed", e);
/*     */         } 
/*     */         
/* 668 */         String xmlDecl = ev.getXmlDeclaration();
/* 669 */         if (xmlDecl != null && xmlDecl.length() > 0) {
/* 670 */           this.omitXmlDecl = false;
/*     */         }
/* 672 */         if (lazyContentLength) {
/* 673 */           this.source = new StreamSource(pushbackReader);
/*     */         }
/* 675 */         return ev;
/*     */       } 
/* 677 */     } else if (this.source == null || this.source instanceof javax.xml.transform.dom.DOMSource) {
/*     */     
/*     */     } 
/* 680 */     return null;
/*     */   }
/*     */   
/*     */   public void setSourceCharsetEncoding(String charset) {
/* 684 */     this.sourceCharsetEncoding = charset;
/*     */   }
/*     */ 
/*     */   
/*     */   public Node renameNode(Node n, String namespaceURI, String qualifiedName) throws DOMException {
/* 689 */     handleNewSource();
/* 690 */     return this.document.renameNode(n, namespaceURI, qualifiedName);
/*     */   }
/*     */   
/*     */   public void normalizeDocument() {
/* 694 */     this.document.normalizeDocument();
/*     */   }
/*     */   
/*     */   public DOMConfiguration getDomConfig() {
/* 698 */     return this.document.getDomConfig();
/*     */   }
/*     */   
/*     */   public Node adoptNode(Node source) throws DOMException {
/* 702 */     handleNewSource();
/* 703 */     return this.document.adoptNode(source);
/*     */   }
/*     */   
/*     */   public void setDocumentURI(String documentURI) {
/* 707 */     this.document.setDocumentURI(documentURI);
/*     */   }
/*     */   
/*     */   public String getDocumentURI() {
/* 711 */     return this.document.getDocumentURI();
/*     */   }
/*     */   
/*     */   public void setStrictErrorChecking(boolean strictErrorChecking) {
/* 715 */     this.document.setStrictErrorChecking(strictErrorChecking);
/*     */   }
/*     */   
/*     */   public String getInputEncoding() {
/* 719 */     return this.document.getInputEncoding();
/*     */   }
/*     */   
/*     */   public String getXmlEncoding() {
/* 723 */     return this.document.getXmlEncoding();
/*     */   }
/*     */   
/*     */   public boolean getXmlStandalone() {
/* 727 */     return this.document.getXmlStandalone();
/*     */   }
/*     */   
/*     */   public void setXmlStandalone(boolean xmlStandalone) throws DOMException {
/* 731 */     this.document.setXmlStandalone(xmlStandalone);
/*     */   }
/*     */   
/*     */   public String getXmlVersion() {
/* 735 */     return this.document.getXmlVersion();
/*     */   }
/*     */   
/*     */   public void setXmlVersion(String xmlVersion) throws DOMException {
/* 739 */     this.document.setXmlVersion(xmlVersion);
/*     */   }
/*     */   
/*     */   public boolean getStrictErrorChecking() {
/* 743 */     return this.document.getStrictErrorChecking();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBaseURI() {
/* 748 */     return this.document.getBaseURI();
/*     */   }
/*     */ 
/*     */   
/*     */   public short compareDocumentPosition(Node other) throws DOMException {
/* 753 */     return this.document.compareDocumentPosition(other);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTextContent() throws DOMException {
/* 758 */     return this.document.getTextContent();
/*     */   }
/*     */   
/*     */   public void setTextContent(String textContent) throws DOMException {
/* 762 */     this.document.setTextContent(textContent);
/*     */   }
/*     */   
/*     */   public boolean isSameNode(Node other) {
/* 766 */     return this.document.isSameNode(other);
/*     */   }
/*     */   
/*     */   public String lookupPrefix(String namespaceURI) {
/* 770 */     return this.document.lookupPrefix(namespaceURI);
/*     */   }
/*     */   
/*     */   public boolean isDefaultNamespace(String namespaceURI) {
/* 774 */     return this.document.isDefaultNamespace(namespaceURI);
/*     */   }
/*     */   
/*     */   public String lookupNamespaceURI(String prefix) {
/* 778 */     return this.document.lookupNamespaceURI(prefix);
/*     */   }
/*     */   
/*     */   public boolean isEqualNode(Node arg) {
/* 782 */     return this.document.isEqualNode(arg);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getFeature(String feature, String version) {
/* 787 */     return this.document.getFeature(feature, version);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object setUserData(String key, Object data, UserDataHandler handler) {
/* 793 */     return this.document.setUserData(key, data, handler);
/*     */   }
/*     */   
/*     */   public Object getUserData(String key) {
/* 797 */     return this.document.getUserData(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public void recycleNode() {}
/*     */ 
/*     */   
/*     */   public String getValue() {
/* 805 */     return null;
/*     */   }
/*     */   
/*     */   public void setValue(String value) {
/* 809 */     log.severe("SAAJ0571.soappart.setValue.not.defined");
/* 810 */     throw new IllegalStateException("Setting value of a soap part is not defined");
/*     */   }
/*     */   
/*     */   public void setParentElement(SOAPElement parent) throws SOAPException {
/* 814 */     log.severe("SAAJ0570.soappart.parent.element.not.defined");
/* 815 */     throw new SOAPExceptionImpl("The parent element of a soap part is not defined");
/*     */   }
/*     */   
/*     */   public SOAPElement getParentElement() {
/* 819 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void detachNode() {}
/*     */ 
/*     */   
/*     */   public String getSourceCharsetEncoding() {
/* 827 */     return this.sourceCharsetEncoding;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\SOAPPartImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */