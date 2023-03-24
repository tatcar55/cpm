/*     */ package com.sun.xml.ws.mex.server;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.stream.buffer.MutableXMLStreamBuffer;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Headers;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.server.BoundEndpoint;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.ws.message.ProblemActionHeader;
/*     */ import com.sun.xml.ws.mex.MessagesMessages;
/*     */ import com.sun.xml.ws.transport.http.servlet.ServletModule;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.Resource;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.SOAPConstants;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamWriter;
/*     */ import javax.xml.ws.Provider;
/*     */ import javax.xml.ws.Service;
/*     */ import javax.xml.ws.ServiceMode;
/*     */ import javax.xml.ws.WebServiceContext;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceProvider;
/*     */ import javax.xml.ws.soap.Addressing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @ServiceMode(Service.Mode.MESSAGE)
/*     */ @WebServiceProvider
/*     */ @Addressing(enabled = true, required = true)
/*     */ public class MEXEndpoint
/*     */   implements Provider<Message>
/*     */ {
/*     */   @Resource
/*     */   protected WebServiceContext wsContext;
/*  95 */   private static final Logger logger = Logger.getLogger(MEXEndpoint.class.getName());
/*     */ 
/*     */   
/*     */   public Message invoke(Message requestMsg) {
/*  99 */     if (requestMsg == null || !requestMsg.hasHeaders())
/*     */     {
/* 101 */       throw new WebServiceException("Malformed MEX Request");
/*     */     }
/*     */     
/* 104 */     WSEndpoint wsEndpoint = (WSEndpoint)this.wsContext.getMessageContext().get("com.sun.xml.ws.api.server.WSEndpoint");
/* 105 */     SOAPVersion soapVersion = wsEndpoint.getBinding().getSOAPVersion();
/*     */ 
/*     */     
/* 108 */     HeaderList headers = requestMsg.getHeaders();
/*     */     
/* 110 */     String action = headers.getAction(AddressingVersion.W3C, soapVersion);
/* 111 */     AddressingVersion wsaVersion = AddressingVersion.W3C;
/* 112 */     if (action == null) {
/* 113 */       action = headers.getAction(AddressingVersion.MEMBER, soapVersion);
/* 114 */       wsaVersion = AddressingVersion.MEMBER;
/*     */     } 
/*     */     
/* 117 */     if (action == null)
/*     */     {
/* 119 */       throw new WebServiceException("No wsa:Action specified");
/*     */     }
/* 121 */     if (action.equals("http://schemas.xmlsoap.org/ws/2004/09/transfer/Get")) {
/* 122 */       String toAddress = headers.getTo(wsaVersion, soapVersion);
/* 123 */       return processGetRequest(requestMsg, toAddress, wsaVersion, soapVersion);
/*     */     } 
/* 125 */     if (action.equals("http://schemas.xmlsoap.org/ws/2004/09/mex/GetMetadata/Request")) {
/* 126 */       String faultText = MessagesMessages.MEX_0017_GET_METADATA_NOT_IMPLEMENTED("http://schemas.xmlsoap.org/ws/2004/09/mex/GetMetadata/Request", "http://schemas.xmlsoap.org/ws/2004/09/transfer/Get");
/* 127 */       logger.warning(faultText);
/* 128 */       Message faultMessage = createFaultMessage(faultText, "http://schemas.xmlsoap.org/ws/2004/09/mex/GetMetadata/Request", wsaVersion, soapVersion);
/*     */       
/* 130 */       this.wsContext.getMessageContext().put("javax.xml.ws.soap.http.soapaction.uri", wsaVersion.getDefaultFaultAction());
/* 131 */       return faultMessage;
/*     */     } 
/*     */ 
/*     */     
/* 135 */     throw new UnsupportedOperationException(action);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Message processGetRequest(Message request, String address, AddressingVersion wsaVersion, SOAPVersion soapVersion) {
/*     */     try {
/* 147 */       WSEndpoint ownerEndpoint = findEndpoint();
/*     */ 
/*     */ 
/*     */       
/* 151 */       if (ownerEndpoint != null) {
/* 152 */         MutableXMLStreamBuffer buffer = new MutableXMLStreamBuffer();
/* 153 */         XMLStreamWriter writer = buffer.createFromXMLStreamWriter();
/*     */         
/* 155 */         address = getAddressFromMexAddress(address, soapVersion);
/* 156 */         writeStartEnvelope(writer, wsaVersion, soapVersion);
/* 157 */         WSDLRetriever wsdlRetriever = new WSDLRetriever(ownerEndpoint);
/* 158 */         wsdlRetriever.addDocuments(writer, null, address);
/* 159 */         writeEndEnvelope(writer);
/* 160 */         writer.flush();
/* 161 */         Message responseMessage = Messages.create((XMLStreamBuffer)buffer);
/*     */         
/* 163 */         HeaderList headers = responseMessage.getHeaders();
/*     */         
/* 165 */         headers.add(Headers.create(new QName(wsaVersion.nsUri, "Action"), "http://schemas.xmlsoap.org/ws/2004/09/transfer/GetResponse"));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 170 */         return responseMessage;
/*     */       } 
/*     */ 
/*     */       
/* 174 */       WebServiceException exception = new WebServiceException(MessagesMessages.MEX_0016_NO_METADATA());
/* 175 */       Message faultMessage = Messages.create(exception, soapVersion);
/* 176 */       this.wsContext.getMessageContext().put("javax.xml.ws.soap.http.soapaction.uri", wsaVersion.getDefaultFaultAction());
/* 177 */       return faultMessage;
/* 178 */     } catch (XMLStreamException streamE) {
/* 179 */       String exceptionMessage = MessagesMessages.MEX_0001_RESPONSE_WRITING_FAILURE(address);
/*     */       
/* 181 */       logger.log(Level.SEVERE, exceptionMessage, streamE);
/* 182 */       throw new WebServiceException(exceptionMessage, streamE);
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
/*     */   
/*     */   private WSEndpoint findEndpoint() {
/* 197 */     WSEndpoint wsEndpoint = (WSEndpoint)this.wsContext.getMessageContext().get("com.sun.xml.ws.api.server.WSEndpoint");
/* 198 */     HttpServletRequest servletRequest = (HttpServletRequest)this.wsContext.getMessageContext().get("javax.xml.ws.servlet.request");
/* 199 */     if (servletRequest == null)
/*     */     {
/* 201 */       throw new WebServiceException("MEX: no ServletRequest can be found");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 206 */     WSEndpoint ownerEndpoint = null;
/* 207 */     ServletModule module = (ServletModule)wsEndpoint.getContainer().getSPI(ServletModule.class);
/* 208 */     String baseAddress = module.getContextPath(servletRequest);
/* 209 */     String ownerEndpointAddress = null;
/* 210 */     List<BoundEndpoint> boundEndpoints = module.getBoundEndpoints();
/* 211 */     for (BoundEndpoint endpoint : boundEndpoints) {
/* 212 */       if (endpoint.getEndpoint().equalsProxiedInstance(wsEndpoint)) {
/* 213 */         ownerEndpointAddress = endpoint.getAddress(baseAddress).toString();
/*     */         break;
/*     */       } 
/*     */     } 
/* 217 */     if (ownerEndpointAddress != null) {
/* 218 */       ownerEndpointAddress = getAddressFromMexAddress(ownerEndpointAddress, wsEndpoint.getBinding().getSOAPVersion());
/*     */       
/* 220 */       boundEndpoints = module.getBoundEndpoints();
/* 221 */       for (BoundEndpoint endpoint : boundEndpoints) {
/*     */ 
/*     */         
/* 224 */         String endpointAddress = endpoint.getAddress(baseAddress).toString();
/* 225 */         if (endpointAddress.equals(ownerEndpointAddress)) {
/* 226 */           ownerEndpoint = endpoint.getEndpoint();
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 232 */     return ownerEndpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeStartEnvelope(XMLStreamWriter writer, AddressingVersion wsaVersion, SOAPVersion soapVersion) throws XMLStreamException {
/* 239 */     String soapPrefix = "soapenv";
/*     */     
/* 241 */     writer.writeStartDocument();
/* 242 */     writer.writeStartElement("soapenv", "Envelope", soapVersion.nsUri);
/*     */ 
/*     */     
/* 245 */     writer.writeNamespace("soapenv", soapVersion.nsUri);
/*     */     
/* 247 */     writer.writeNamespace("wsa", wsaVersion.nsUri);
/* 248 */     writer.writeNamespace("mex", "http://schemas.xmlsoap.org/ws/2004/09/mex");
/*     */     
/* 250 */     writer.writeStartElement("soapenv", "Body", soapVersion.nsUri);
/* 251 */     writer.writeStartElement("mex", "Metadata", "http://schemas.xmlsoap.org/ws/2004/09/mex");
/*     */   }
/*     */   
/*     */   private Message createFaultMessage(@NotNull String faultText, @NotNull String unsupportedAction, @NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/*     */     Message faultMessage;
/* 256 */     QName subcode = av.actionNotSupportedTag;
/*     */     
/*     */     try {
/*     */       SOAPFault fault;
/* 260 */       if (sv == SOAPVersion.SOAP_12) {
/* 261 */         fault = SOAPVersion.SOAP_12.saajSoapFactory.createFault();
/* 262 */         fault.setFaultCode(SOAPConstants.SOAP_SENDER_FAULT);
/* 263 */         fault.appendFaultSubcode(subcode);
/* 264 */         Detail detail = fault.addDetail();
/* 265 */         SOAPElement se = detail.addChildElement(av.problemActionTag);
/* 266 */         se = se.addChildElement(av.actionTag);
/* 267 */         se.addTextNode(unsupportedAction);
/*     */       } else {
/* 269 */         fault = SOAPVersion.SOAP_11.saajSoapFactory.createFault();
/* 270 */         fault.setFaultCode(subcode);
/*     */       } 
/* 272 */       fault.setFaultString(faultText);
/*     */       
/* 274 */       faultMessage = SOAPFaultBuilder.createSOAPFaultMessage(sv, fault);
/* 275 */       if (sv == SOAPVersion.SOAP_11) {
/* 276 */         faultMessage.getHeaders().add((Header)new ProblemActionHeader(unsupportedAction, av));
/*     */       }
/* 278 */     } catch (SOAPException e) {
/* 279 */       throw new WebServiceException(e);
/*     */     } 
/*     */     
/* 282 */     return faultMessage;
/*     */   }
/*     */   
/*     */   private String getAddressFromMexAddress(String mexAddress, SOAPVersion soapVersion) {
/* 286 */     if (mexAddress.endsWith("mex")) {
/* 287 */       return mexAddress.substring(0, mexAddress.length() - "/mex".length());
/*     */     }
/*     */     
/* 290 */     if (soapVersion.equals(SOAPVersion.SOAP_11))
/* 291 */       return mexAddress.substring(0, mexAddress.length() - "/mex/soap11".length()); 
/* 292 */     if (soapVersion.equals(SOAPVersion.SOAP_12)) {
/* 293 */       return mexAddress.substring(0, mexAddress.length() - "/mex/soap12".length());
/*     */     }
/*     */     
/* 296 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeEndEnvelope(XMLStreamWriter writer) throws XMLStreamException {
/* 302 */     writer.writeEndElement();
/* 303 */     writer.writeEndElement();
/* 304 */     writer.writeEndElement();
/* 305 */     writer.writeEndDocument();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\mex\server\MEXEndpoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */