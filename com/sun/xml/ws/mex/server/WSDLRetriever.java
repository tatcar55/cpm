/*     */ package com.sun.xml.ws.mex.server;
/*     */ 
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.server.DocumentAddressResolver;
/*     */ import com.sun.xml.ws.api.server.SDDocument;
/*     */ import com.sun.xml.ws.api.server.ServiceDefinition;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.mex.MessagesMessages;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSDLRetriever
/*     */ {
/*     */   private final WSEndpoint endpoint;
/*  75 */   private static final Logger logger = Logger.getLogger(WSDLRetriever.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   private static final DocumentAddressResolver dar = new DocumentAddressResolver()
/*     */     {
/*     */       
/*     */       public String getRelativeAddressFor(SDDocument doc1, SDDocument doc2)
/*     */       {
/*  90 */         return null;
/*     */       }
/*     */     };
/*     */   
/*     */   public WSDLRetriever(WSEndpoint endpoint) {
/*  95 */     this.endpoint = endpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addDocuments(XMLStreamWriter writer, Packet request, String address) throws XMLStreamException {
/* 105 */     ServiceDefinition sDef = this.endpoint.getServiceDefinition();
/* 106 */     if (sDef == null) {
/*     */       return;
/*     */     }
/* 109 */     Iterator<SDDocument> docs = sDef.iterator();
/* 110 */     while (docs.hasNext()) {
/* 111 */       writeDoc(writer, docs.next(), address);
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
/*     */   
/*     */   private void writeDoc(XMLStreamWriter writer, SDDocument doc, String address) throws XMLStreamException {
/*     */     try {
/* 126 */       writer.writeStartElement("mex", "MetadataSection", "http://schemas.xmlsoap.org/ws/2004/09/mex");
/*     */       
/* 128 */       if (doc.isWSDL()) {
/* 129 */         writer.writeAttribute("Dialect", "http://schemas.xmlsoap.org/wsdl/");
/* 130 */         writer.writeAttribute("Identifier", ((SDDocument.WSDL)doc).getTargetNamespace());
/*     */       }
/* 132 */       else if (doc.isSchema()) {
/* 133 */         writer.writeAttribute("Dialect", "http://www.w3.org/2001/XMLSchema");
/* 134 */         writer.writeAttribute("Identifier", ((SDDocument.Schema)doc).getTargetNamespace());
/*     */       } 
/*     */       
/* 137 */       doc.writeTo(new MEXAddressResolver(this.endpoint.getServiceName(), this.endpoint.getPortName(), address), dar, writer);
/* 138 */       writer.writeEndElement();
/* 139 */     } catch (IOException ioe) {
/*     */       
/* 141 */       String exceptionMessage = MessagesMessages.MEX_0015_IOEXCEPTION_WHILE_WRITING_RESPONSE(address);
/*     */       
/* 143 */       logger.log(Level.SEVERE, exceptionMessage, ioe);
/* 144 */       throw new WebServiceException(exceptionMessage, ioe);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\mex\server\WSDLRetriever.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */