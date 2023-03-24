/*     */ package com.sun.xml.ws.security.message.stream;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.StreamSOAPCodec;
/*     */ import com.sun.xml.ws.message.stream.StreamMessage;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import java.io.IOException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLOutputFactory;
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
/*     */ public class LazyStreamBasedMessage
/*     */   extends Message
/*     */ {
/*  74 */   protected static final Logger logger = Logger.getLogger("com.sun.xml.wss.jaxws.impl", "com.sun.xml.wss.jaxws.impl.logging.LogStrings");
/*     */ 
/*     */ 
/*     */   
/*  78 */   private StreamSOAPCodec codec = null;
/*     */   private boolean readMessage = false;
/*  80 */   private XMLStreamReader reader = null;
/*  81 */   private Message message = null;
/*  82 */   AttachmentSet as = null;
/*  83 */   private MutableXMLStreamBuffer buffer = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private static final boolean MTOM_LARGEDATA = Boolean.getBoolean("MTOM_LARGEDATA");
/*     */ 
/*     */   
/*     */   public LazyStreamBasedMessage(XMLStreamReader message, StreamSOAPCodec codec) {
/*  92 */     this.reader = message;
/*  93 */     this.codec = codec;
/*     */   }
/*     */   
/*     */   public LazyStreamBasedMessage(XMLStreamReader message, StreamSOAPCodec codec, AttachmentSet as) {
/*  97 */     this.reader = message;
/*  98 */     this.codec = codec;
/*  99 */     this.as = as;
/*     */   }
/*     */   
/*     */   public StreamSOAPCodec getCodec() {
/* 103 */     return this.codec;
/*     */   }
/*     */   
/*     */   private synchronized void cacheMessage() {
/* 107 */     if (!this.readMessage) {
/* 108 */       if (this.as == null) {
/* 109 */         this.message = this.codec.decode(this.reader);
/*     */       } else {
/* 111 */         this.message = this.codec.decode(this.reader, this.as);
/*     */       } 
/* 113 */       this.readMessage = true;
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
/*     */   public boolean hasHeaders() {
/* 126 */     if (!this.readMessage) {
/* 127 */       cacheMessage();
/*     */     }
/* 129 */     return this.message.hasHeaders();
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
/*     */   public HeaderList getHeaders() {
/* 146 */     if (!this.readMessage) {
/* 147 */       cacheMessage();
/*     */     }
/* 149 */     return this.message.getHeaders();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public AttachmentSet getAttachments() {
/* 157 */     if (!this.readMessage) {
/* 158 */       cacheMessage();
/*     */     }
/* 160 */     return this.message.getAttachments();
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
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOneWay(@NotNull WSDLPort port) {
/* 192 */     if (!this.readMessage) {
/* 193 */       cacheMessage();
/*     */     }
/* 195 */     return this.message.isOneWay(port);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getPayloadLocalPart() {
/* 205 */     if (!this.readMessage) {
/* 206 */       cacheMessage();
/*     */     }
/* 208 */     return this.message.getPayloadLocalPart();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 218 */     if (!this.readMessage) {
/* 219 */       cacheMessage();
/*     */     }
/* 221 */     return this.message.getPayloadNamespaceURI();
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
/*     */   public boolean hasPayload() {
/* 240 */     if (!this.readMessage) {
/* 241 */       cacheMessage();
/*     */     }
/* 243 */     return this.message.hasPayload();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Source readEnvelopeAsSource() {
/* 251 */     if (!this.readMessage) {
/* 252 */       cacheMessage();
/*     */     }
/* 254 */     return this.message.readEnvelopeAsSource();
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
/*     */   public Source readPayloadAsSource() {
/* 267 */     if (!this.readMessage) {
/* 268 */       cacheMessage();
/*     */     }
/* 270 */     return this.message.readPayloadAsSource();
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
/*     */   public SOAPMessage readAsSOAPMessage() throws SOAPException {
/* 282 */     if (!this.readMessage) {
/* 283 */       cacheMessage();
/*     */     }
/* 285 */     return this.message.readAsSOAPMessage();
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
/*     */   public <T> T readPayloadAsJAXB(Unmarshaller unmarshaller) throws JAXBException {
/* 297 */     if (!this.readMessage) {
/* 298 */       cacheMessage();
/*     */     }
/* 300 */     throw new UnsupportedOperationException();
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
/*     */   public <T> T readPayloadAsJAXB(Bridge<T> bridge) throws JAXBException {
/* 312 */     if (!this.readMessage) {
/* 313 */       cacheMessage();
/*     */     }
/* 315 */     return (T)this.message.readPayloadAsJAXB(bridge);
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
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/* 332 */     if (!this.readMessage) {
/* 333 */       cacheMessage();
/*     */     }
/* 335 */     return this.message.readPayload();
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
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 356 */     if (!this.readMessage) {
/* 357 */       cacheMessage();
/*     */     }
/* 359 */     this.message.writePayloadTo(sw);
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
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/* 373 */     if (!this.readMessage) {
/* 374 */       cacheMessage();
/*     */     }
/* 376 */     this.message.writeTo(sw);
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
/*     */   public void writeTo(ContentHandler contentHandler, ErrorHandler errorHandler) throws SAXException {
/* 395 */     if (!this.readMessage) {
/* 396 */       cacheMessage();
/*     */     }
/* 398 */     this.message.writeTo(contentHandler, errorHandler);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 464 */     if (!this.readMessage) {
/* 465 */       cacheMessage();
/*     */     }
/* 467 */     return this.message.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamReader readMessage() {
/* 472 */     if (!this.readMessage) {
/* 473 */       return this.reader;
/*     */     }
/*     */     
/* 476 */     if (this.buffer == null) {
/*     */       try {
/* 478 */         this.buffer = new MutableXMLStreamBuffer();
/* 479 */         XMLStreamWriter writer = this.buffer.createFromXMLStreamWriter();
/*     */         
/* 481 */         this.message.writeTo(writer);
/* 482 */       } catch (XMLStreamException ex) {
/* 483 */         logger.log(Level.SEVERE, LogStringsMessages.WSSMSG_0001_PROBLEM_CACHING(), ex);
/*     */       } 
/*     */     }
/*     */     try {
/* 487 */       this.reader = (XMLStreamReader)this.buffer.readAsXMLStreamReader();
/* 488 */       return this.reader;
/* 489 */     } catch (XMLStreamException ex) {
/* 490 */       logger.log(Level.SEVERE, LogStringsMessages.WSSMSG_0002_ERROR_READING_BUFFER(), ex);
/*     */       
/* 492 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void print() throws XMLStreamException {
/* 497 */     if (this.readMessage) {
/*     */       try {
/* 499 */         this.message.readAsSOAPMessage().writeTo(System.out);
/*     */         return;
/* 501 */       } catch (SOAPException ex) {
/* 502 */         logger.log(Level.SEVERE, LogStringsMessages.WSSMSG_0003_ERROR_PRINT(), ex);
/*     */       }
/* 504 */       catch (IOException ex) {
/* 505 */         logger.log(Level.SEVERE, LogStringsMessages.WSSMSG_0003_ERROR_PRINT(), ex);
/*     */       } 
/*     */     }
/*     */     
/* 509 */     if (this.buffer == null) {
/* 510 */       this.buffer = new MutableXMLStreamBuffer();
/* 511 */       this.buffer.createFromXMLStreamReader(this.reader);
/* 512 */       this.reader = (XMLStreamReader)this.buffer.readAsXMLStreamReader();
/*     */     } 
/* 514 */     XMLOutputFactory xof = XMLOutputFactory.newInstance();
/* 515 */     this.buffer.writeToXMLStreamWriter(xof.createXMLStreamWriter(System.out));
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(XMLBridge<T> bridge) throws JAXBException {
/* 519 */     if (!this.readMessage) {
/* 520 */       cacheMessage();
/*     */     }
/* 522 */     return (T)this.message.readPayloadAsJAXB(bridge);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBodyEpilogue() {
/* 531 */     return (this.message instanceof StreamMessage) ? ((StreamMessage)this.message).getBodyEpilogue() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBodyPrologue() {
/* 541 */     return (this.message instanceof StreamMessage) ? ((StreamMessage)this.message).getBodyPrologue() : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean mtomLargeData() {
/* 546 */     return MTOM_LARGEDATA;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\message\stream\LazyStreamBasedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */