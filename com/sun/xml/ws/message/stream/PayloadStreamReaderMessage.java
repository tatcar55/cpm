/*     */ package com.sun.xml.ws.message.stream;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.ws.message.AbstractMessageImpl;
/*     */ import com.sun.xml.ws.message.AttachmentSetImpl;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ public class PayloadStreamReaderMessage
/*     */   extends AbstractMessageImpl
/*     */ {
/*     */   private final StreamMessage message;
/*     */   
/*     */   public PayloadStreamReaderMessage(XMLStreamReader reader, SOAPVersion soapVer) {
/*  72 */     this(null, reader, (AttachmentSet)new AttachmentSetImpl(), soapVer);
/*     */   }
/*     */ 
/*     */   
/*     */   public PayloadStreamReaderMessage(@Nullable HeaderList headers, @NotNull XMLStreamReader reader, @NotNull AttachmentSet attSet, @NotNull SOAPVersion soapVersion) {
/*  77 */     super(soapVersion);
/*  78 */     this.message = new StreamMessage(headers, attSet, reader, soapVersion);
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/*  82 */     return this.message.hasHeaders();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HeaderList getHeaders() {
/*  89 */     return this.message.getHeaders();
/*     */   }
/*     */   
/*     */   public AttachmentSet getAttachments() {
/*  93 */     return this.message.getAttachments();
/*     */   }
/*     */   
/*     */   public String getPayloadLocalPart() {
/*  97 */     return this.message.getPayloadLocalPart();
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 101 */     return this.message.getPayloadNamespaceURI();
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/* 105 */     return true;
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 109 */     return this.message.readPayloadAsSource();
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/* 113 */     return this.message.readPayload();
/*     */   }
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 117 */     this.message.writePayloadTo(sw);
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 121 */     return (T)this.message.readPayloadAsJAXB(unmarshaller);
/*     */   }
/*     */   
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 125 */     this.message.writeTo(contentHandler, errorHandler);
/*     */   }
/*     */   
/*     */   protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/* 129 */     this.message.writePayloadTo(contentHandler, errorHandler, fragment);
/*     */   }
/*     */   
/*     */   public Message copy() {
/* 133 */     return this.message.copy();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public MessageHeaders getMessageHeaders() {
/* 138 */     return this.message.getMessageHeaders();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\stream\PayloadStreamReaderMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */