/*     */ package com.sun.xml.ws.message.jaxb;
/*     */ 
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.message.AbstractMessageImpl;
/*     */ import com.sun.xml.ws.message.PayloadElementSniffer;
/*     */ import com.sun.xml.ws.spi.db.BindingContext;
/*     */ import com.sun.xml.ws.spi.db.XMLBridge;
/*     */ import com.sun.xml.ws.streaming.MtomStreamWriter;
/*     */ import com.sun.xml.ws.streaming.XMLStreamWriterUtil;
/*     */ import java.io.OutputStream;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.bind.attachment.AttachmentMarshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.stream.XMLStreamWriter;
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
/*     */ public class JAXBDispatchMessage
/*     */   extends AbstractMessageImpl
/*     */ {
/*     */   private final Object jaxbObject;
/*     */   private final XMLBridge bridge;
/*     */   private final JAXBContext rawContext;
/*     */   private QName payloadQName;
/*     */   
/*     */   private JAXBDispatchMessage(JAXBDispatchMessage that) {
/* 101 */     super(that);
/* 102 */     this.jaxbObject = that.jaxbObject;
/* 103 */     this.rawContext = that.rawContext;
/* 104 */     this.bridge = that.bridge;
/*     */   }
/*     */   
/*     */   public JAXBDispatchMessage(JAXBContext rawContext, Object jaxbObject, SOAPVersion soapVersion) {
/* 108 */     super(soapVersion);
/* 109 */     this.bridge = null;
/* 110 */     this.rawContext = rawContext;
/* 111 */     this.jaxbObject = jaxbObject;
/*     */   }
/*     */   
/*     */   public JAXBDispatchMessage(BindingContext context, Object jaxbObject, SOAPVersion soapVersion) {
/* 115 */     super(soapVersion);
/* 116 */     this.bridge = context.createFragmentBridge();
/* 117 */     this.rawContext = null;
/* 118 */     this.jaxbObject = jaxbObject;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writePayloadTo(ContentHandler contentHandler, ErrorHandler errorHandler, boolean fragment) throws SAXException {
/* 123 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasHeaders() {
/* 128 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public HeaderList getHeaders() {
/* 133 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPayloadLocalPart() {
/* 138 */     if (this.payloadQName == null) {
/* 139 */       readPayloadElement();
/*     */     }
/* 141 */     return this.payloadQName.getLocalPart();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPayloadNamespaceURI() {
/* 146 */     if (this.payloadQName == null) {
/* 147 */       readPayloadElement();
/*     */     }
/* 149 */     return this.payloadQName.getNamespaceURI();
/*     */   }
/*     */   
/*     */   private void readPayloadElement() {
/* 153 */     PayloadElementSniffer sniffer = new PayloadElementSniffer();
/*     */     try {
/* 155 */       if (this.rawContext != null) {
/* 156 */         Marshaller m = this.rawContext.createMarshaller();
/* 157 */         m.setProperty("jaxb.fragment", Boolean.FALSE);
/* 158 */         m.marshal(this.jaxbObject, (ContentHandler)sniffer);
/*     */       } else {
/* 160 */         this.bridge.marshal(this.jaxbObject, (ContentHandler)sniffer, null);
/*     */       }
/*     */     
/* 163 */     } catch (JAXBException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 170 */       this.payloadQName = sniffer.getPayloadQName();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPayload() {
/* 176 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Source readPayloadAsSource() {
/* 181 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public XMLStreamReader readPayload() throws XMLStreamException {
/* 186 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public void writePayloadTo(XMLStreamWriter sw) throws XMLStreamException {
/* 191 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Message copy() {
/* 196 */     return (Message)new JAXBDispatchMessage(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeTo(XMLStreamWriter sw) throws XMLStreamException {
/*     */     try {
/* 204 */       AttachmentMarshaller am = (sw instanceof MtomStreamWriter) ? ((MtomStreamWriter)sw).getAttachmentMarshaller() : new AttachmentMarshallerImpl(this.attachmentSet);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 209 */       String encoding = XMLStreamWriterUtil.getEncoding(sw);
/*     */ 
/*     */       
/* 212 */       OutputStream os = this.bridge.supportOutputStream() ? XMLStreamWriterUtil.getOutputStream(sw) : null;
/* 213 */       if (this.rawContext != null) {
/* 214 */         Marshaller m = this.rawContext.createMarshaller();
/* 215 */         m.setProperty("jaxb.fragment", Boolean.FALSE);
/* 216 */         m.setAttachmentMarshaller(am);
/* 217 */         if (os != null) {
/* 218 */           m.marshal(this.jaxbObject, os);
/*     */         } else {
/* 220 */           m.marshal(this.jaxbObject, sw);
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 225 */       else if (os != null && encoding != null && encoding.equalsIgnoreCase("utf-8")) {
/* 226 */         this.bridge.marshal(this.jaxbObject, os, sw.getNamespaceContext(), am);
/*     */       } else {
/* 228 */         this.bridge.marshal(this.jaxbObject, sw, am);
/*     */       }
/*     */     
/*     */     }
/* 232 */     catch (JAXBException e) {
/*     */       
/* 234 */       throw new WebServiceException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\jaxb\JAXBDispatchMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */