/*     */ package com.sun.xml.ws.security.opt.impl.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.bind.api.Bridge;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.message.AttachmentSet;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.security.opt.api.SecurityElement;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPMessage;
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
/*     */ public class MessageWrapper
/*     */   extends Message
/*     */ {
/*     */   private boolean isOneWay = false;
/*     */   private SecuredMessage sm;
/*     */   private List headers;
/*  75 */   private HeaderList hl = new HeaderList();
/*  76 */   private MutableXMLStreamBuffer bufferedMsg = null;
/*     */   public MessageWrapper(SecuredMessage sm, boolean oneWay) {
/*  78 */     this.sm = sm;
/*  79 */     this.isOneWay = oneWay;
/*  80 */     this.headers = sm.getHeaders();
/*  81 */     for (int i = 0; i < this.headers.size(); i++) {
/*  82 */       Object obj = this.headers.get(i);
/*  83 */       if (obj instanceof Header) {
/*  84 */         this.hl.add((Header)obj);
/*     */       } else {
/*  86 */         this.hl.add(new HeaderWrapper((SecurityElement)obj));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public MessageWrapper(MutableXMLStreamBuffer msg, boolean oneWay, HeaderList hdrs, SecuredMessage sm) {
/*  92 */     this.bufferedMsg = msg;
/*  93 */     this.sm = sm;
/*  94 */     this.hl = hdrs;
/*  95 */     this.isOneWay = oneWay;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasHeaders() {
/* 105 */     return (this.hl.size() > 0);
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
/* 122 */     return this.hl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttachmentSet getAttachments() {
/* 130 */     return this.sm.getAttachments();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean hasAttachments() {
/* 138 */     return (this.sm.getAttachments() != null);
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
/*     */   public boolean isOneWay(@NotNull WSDLPort port) {
/* 165 */     return this.isOneWay;
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
/*     */   public String getPayloadLocalPart() {
/* 178 */     return this.sm.getPayloadLocalPart();
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
/*     */   public boolean isFault() {
/* 192 */     String localPart = getPayloadLocalPart();
/* 193 */     String action = null;
/* 194 */     if ("EncryptedData".equals(localPart)) {
/* 195 */       if (this.hl != null) {
/*     */         try {
/* 197 */           action = this.hl.getAction(AddressingVersion.W3C, this.sm.getSOAPVersion());
/* 198 */         } catch (Exception e) {}
/*     */       }
/*     */       
/* 201 */       if (action != null && action.endsWith("addressing/fault")) {
/* 202 */         localPart = "Fault";
/*     */       }
/*     */     } 
/* 205 */     if (localPart == null || !localPart.equals("Fault")) {
/* 206 */       return false;
/*     */     }
/*     */     
/* 209 */     String nsUri = getPayloadNamespaceURI();
/* 210 */     return (nsUri.equals(SOAPVersion.SOAP_11.nsUri) || nsUri.equals(SOAPVersion.SOAP_12.nsUri));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 220 */     return this.sm.getPayloadNamespaceURI();
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
/* 239 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Source readEnvelopeAsSource() {
/* 248 */     throw new UnsupportedOperationException();
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
/* 261 */     throw new UnsupportedOperationException();
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
/* 273 */     throw new UnsupportedOperationException();
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
/* 285 */     throw new UnsupportedOperationException();
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
/* 297 */     throw new UnsupportedOperationException();
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
/* 314 */     _check();
/* 315 */     return this.sm.readPayload();
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
/* 336 */     _check();
/* 337 */     this.sm.writePayloadTo(sw);
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
/* 351 */     if (this.bufferedMsg != null) {
/* 352 */       this.bufferedMsg.writeToXMLStreamWriter(sw);
/*     */       return;
/*     */     } 
/* 355 */     this.sm.writeTo(sw);
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
/* 374 */     throw new UnsupportedOperationException();
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
/*     */   public MessageWrapper copy() {
/* 440 */     return this;
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
/*     */   private void _check() {
/* 456 */     if (this.bufferedMsg != null) {
/* 457 */       throw new UnsupportedOperationException("Message is buffered , only writeTo method is supported");
/*     */     }
/*     */   }
/*     */   
/*     */   public <T> T readPayloadAsJAXB(XMLBridge<T> arg0) throws JAXBException {
/* 462 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\impl\message\MessageWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */