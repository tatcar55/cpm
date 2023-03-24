/*     */ package com.sun.xml.ws.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
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
/*     */ public class EmptyMessageImpl
/*     */   extends AbstractMessageImpl
/*     */ {
/*     */   private final HeaderList headers;
/*     */   private final AttachmentSet attachmentSet;
/*     */   
/*     */   public EmptyMessageImpl(SOAPVersion version) {
/*  77 */     super(version);
/*  78 */     this.headers = new HeaderList(version);
/*  79 */     this.attachmentSet = new AttachmentSetImpl();
/*     */   }
/*     */   
/*     */   public EmptyMessageImpl(HeaderList headers, @NotNull AttachmentSet attachmentSet, SOAPVersion version) {
/*  83 */     super(version);
/*  84 */     if (headers == null)
/*  85 */       headers = new HeaderList(version); 
/*  86 */     this.attachmentSet = attachmentSet;
/*  87 */     this.headers = headers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EmptyMessageImpl(EmptyMessageImpl that) {
/*  94 */     super(that);
/*  95 */     this.headers = new HeaderList(that.headers);
/*  96 */     this.attachmentSet = that.attachmentSet;
/*     */   }
/*     */   
/*     */   public boolean hasHeaders() {
/* 100 */     return !this.headers.isEmpty();
/*     */   }
/*     */   
/*     */   public HeaderList getHeaders() {
/* 104 */     return this.headers;
/*     */   }
/*     */   
/*     */   public String getPayloadLocalPart() {
/* 108 */     return null;
/*     */   }
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 112 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasPayload() {
/* 116 */     return false;
/*     */   }
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 120 */     return null;
/*     */   }
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {}
/*     */ 
/*     */   
/*     */   public void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {}
/*     */ 
/*     */   
/*     */   public Message copy() {
/* 136 */     return new EmptyMessageImpl(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\EmptyMessageImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */