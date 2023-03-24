/*     */ package com.sun.xml.ws.message;
/*     */ 
/*     */ import com.sun.istack.FragmentContentHandler;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.bind.unmarshaller.DOMScanner;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.streaming.DOMStreamReader;
/*     */ import com.sun.xml.ws.util.DOMUtil;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Element;
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
/*     */ public final class DOMMessage
/*     */   extends AbstractMessageImpl
/*     */ {
/*     */   private HeaderList headers;
/*     */   private final Element payload;
/*     */   
/*     */   public DOMMessage(SOAPVersion ver, Element payload) {
/*  77 */     this(ver, (HeaderList)null, payload);
/*     */   }
/*     */   
/*     */   public DOMMessage(SOAPVersion ver, HeaderList headers, Element payload) {
/*  81 */     this(ver, headers, payload, (AttachmentSet)null);
/*     */   }
/*     */   
/*     */   public DOMMessage(SOAPVersion ver, HeaderList headers, Element payload, AttachmentSet attachments) {
/*  85 */     super(ver);
/*  86 */     this.headers = headers;
/*  87 */     this.payload = payload;
/*  88 */     this.attachmentSet = attachments;
/*  89 */     assert payload != null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private DOMMessage(DOMMessage that) {
/*  95 */     super(that);
/*  96 */     this.headers = HeaderList.copy(that.headers);
/*  97 */     this.payload = that.payload;
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/* 101 */     return (getHeaders().size() > 0);
/*     */   }
/*     */   
/*     */   public HeaderList getHeaders() {
/* 105 */     if (this.headers == null) {
/* 106 */       this.headers = new HeaderList(getSOAPVersion());
/*     */     }
/* 108 */     return this.headers;
/*     */   }
/*     */   
/*     */   public String getPayloadLocalPart() {
/* 112 */     return this.payload.getLocalName();
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 116 */     return this.payload.getNamespaceURI();
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/* 120 */     return true;
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 124 */     return new DOMSource(this.payload);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 128 */     if (hasAttachments())
/* 129 */       unmarshaller.setAttachmentUnmarshaller(new AttachmentUnmarshallerImpl(getAttachments())); 
/*     */     try {
/* 131 */       return (T)unmarshaller.unmarshal(this.payload);
/*     */     } finally {
/* 133 */       unmarshaller.setAttachmentUnmarshaller(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 138 */     return (T)bridge.unmarshal(this.payload, hasAttachments() ? new AttachmentUnmarshallerImpl(getAttachments()) : null);
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/* 143 */     DOMStreamReader dss = new DOMStreamReader();
/* 144 */     dss.setCurrentNode(this.payload);
/* 145 */     dss.nextTag();
/* 146 */     assert dss.getEventType() == 1;
/* 147 */     return (XMLStreamReader)dss;
/*     */   }
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) {
/*     */     try {
/* 152 */       if (this.payload != null)
/* 153 */         DOMUtil.serializeNode(this.payload, sw); 
/* 154 */     } catch (XMLStreamException e) {
/* 155 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */   protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/*     */     FragmentContentHandler fragmentContentHandler;
/* 160 */     if (fragment)
/* 161 */       fragmentContentHandler = new FragmentContentHandler(contentHandler); 
/* 162 */     DOMScanner ds = new DOMScanner();
/* 163 */     ds.setContentHandler((ContentHandler)fragmentContentHandler);
/* 164 */     ds.scan(this.payload);
/*     */   }
/*     */   
/*     */   public Message copy() {
/* 168 */     return new DOMMessage(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\DOMMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */