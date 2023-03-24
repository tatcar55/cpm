/*     */ package com.sun.xml.ws.mex.server;
/*     */ 
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Pipe;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.PipeAdapter;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.mex.MessagesMessages;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MetadataServerPipe
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*     */   private final WSDLRetriever wsdlRetriever;
/*     */   private final SOAPVersion soapVersion;
/*  89 */   private static final Logger logger = Logger.getLogger(MetadataServerPipe.class.getName());
/*     */ 
/*     */   
/*     */   public MetadataServerPipe(WSEndpoint endpoint, Pipe next) {
/*  93 */     super(PipeAdapter.adapt(next));
/*  94 */     this.wsdlRetriever = new WSDLRetriever(endpoint);
/*  95 */     this.soapVersion = endpoint.getBinding().getSOAPVersion();
/*     */   }
/*     */   
/*     */   protected MetadataServerPipe(MetadataServerPipe that, TubeCloner cloner) {
/*  99 */     super(that, cloner);
/* 100 */     this.soapVersion = that.soapVersion;
/* 101 */     this.wsdlRetriever = that.wsdlRetriever;
/*     */   }
/*     */   
/*     */   public MetadataServerPipe copy(TubeCloner cloner) {
/* 105 */     return new MetadataServerPipe(this, cloner);
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
/*     */   public NextAction processRequest(Packet request) {
/* 118 */     if (request.getMessage() == null || !request.getMessage().hasHeaders()) {
/* 119 */       return super.processRequest(request);
/*     */     }
/*     */ 
/*     */     
/* 123 */     HeaderList headers = request.getMessage().getHeaders();
/* 124 */     String action = headers.getAction(AddressingVersion.W3C, this.soapVersion);
/* 125 */     AddressingVersion adVersion = AddressingVersion.W3C;
/* 126 */     if (action == null) {
/* 127 */       action = headers.getAction(AddressingVersion.MEMBER, this.soapVersion);
/* 128 */       adVersion = AddressingVersion.MEMBER;
/*     */     } 
/*     */     
/* 131 */     if (action != null) {
/* 132 */       if (action.equals("http://schemas.xmlsoap.org/ws/2004/09/transfer/Get")) {
/* 133 */         String toAddress = headers.getTo(adVersion, this.soapVersion);
/* 134 */         return doReturnWith(processGetRequest(request, toAddress, adVersion));
/* 135 */       }  if (action.equals("http://schemas.xmlsoap.org/ws/2004/09/mex/GetMetadata/Request")) {
/* 136 */         Message faultMessage = Messages.create("http://schemas.xmlsoap.org/ws/2004/09/mex/GetMetadata/Request", adVersion, this.soapVersion);
/*     */         
/* 138 */         return doReturnWith(request.createServerResponse(faultMessage, adVersion, this.soapVersion, adVersion.getDefaultFaultAction()));
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 143 */     return super.processRequest(request);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Packet processGetRequest(Packet request, String address, AddressingVersion adVersion) {
/*     */     try {
/* 154 */       MutableXMLStreamBuffer buffer = new MutableXMLStreamBuffer();
/* 155 */       XMLStreamWriter writer = buffer.createFromXMLStreamWriter();
/*     */       
/* 157 */       writeStartEnvelope(writer, adVersion);
/* 158 */       this.wsdlRetriever.addDocuments(writer, request, address);
/*     */       
/* 160 */       writeEndEnvelope(writer);
/* 161 */       writer.flush();
/*     */       
/* 163 */       Message responseMessage = Messages.create((XMLStreamBuffer)buffer);
/* 164 */       Packet response = request.createServerResponse(responseMessage, adVersion, this.soapVersion, "http://schemas.xmlsoap.org/ws/2004/09/transfer/GetResponse");
/*     */       
/* 166 */       return response;
/* 167 */     } catch (XMLStreamException streamE) {
/* 168 */       String exceptionMessage = MessagesMessages.MEX_0001_RESPONSE_WRITING_FAILURE(address);
/*     */       
/* 170 */       logger.log(Level.SEVERE, exceptionMessage, streamE);
/* 171 */       throw new WebServiceException(exceptionMessage, streamE);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeStartEnvelope(XMLStreamWriter writer, AddressingVersion adVersion) throws XMLStreamException {
/* 178 */     String soapPrefix = "soapenv";
/*     */     
/* 180 */     writer.writeStartDocument();
/* 181 */     writer.writeStartElement("soapenv", "Envelope", this.soapVersion.nsUri);
/*     */ 
/*     */     
/* 184 */     writer.writeNamespace("soapenv", this.soapVersion.nsUri);
/*     */     
/* 186 */     writer.writeNamespace("wsa", adVersion.nsUri);
/* 187 */     writer.writeNamespace("mex", "http://schemas.xmlsoap.org/ws/2004/09/mex");
/*     */     
/* 189 */     writer.writeStartElement("soapenv", "Body", this.soapVersion.nsUri);
/* 190 */     writer.writeStartElement("mex", "Metadata", "http://schemas.xmlsoap.org/ws/2004/09/mex");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeEndEnvelope(XMLStreamWriter writer) throws XMLStreamException {
/* 195 */     writer.writeEndElement();
/* 196 */     writer.writeEndElement();
/* 197 */     writer.writeEndElement();
/* 198 */     writer.writeEndDocument();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\mex\server\MetadataServerPipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */