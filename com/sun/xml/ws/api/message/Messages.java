/*     */ package com.sun.xml.ws.api.message;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*     */ import com.sun.xml.ws.addressing.WsaTubeHelper;
/*     */ import com.sun.xml.ws.addressing.model.MissingAddressingHeaderException;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.message.saaj.SAAJFactory;
/*     */ import com.sun.xml.ws.api.pipe.Codecs;
/*     */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
/*     */ import com.sun.xml.ws.message.AttachmentSetImpl;
/*     */ import com.sun.xml.ws.message.DOMMessage;
/*     */ import com.sun.xml.ws.message.EmptyMessageImpl;
/*     */ import com.sun.xml.ws.message.ProblemActionHeader;
/*     */ import com.sun.xml.ws.message.jaxb.JAXBMessage;
/*     */ import com.sun.xml.ws.message.source.PayloadSourceMessage;
/*     */ import com.sun.xml.ws.message.source.ProtocolSourceMessage;
/*     */ import com.sun.xml.ws.message.stream.PayloadStreamReaderMessage;
/*     */ import com.sun.xml.ws.resources.AddressingMessages;
/*     */ import com.sun.xml.ws.spi.db.BindingContextFactory;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderException;
/*     */ import com.sun.xml.ws.streaming.XMLStreamReaderUtil;
/*     */ import com.sun.xml.ws.util.DOMUtil;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.Marshaller;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.SOAPConstants;
/*     */ import javax.xml.soap.SOAPElement;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.soap.SOAPMessage;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.sax.SAXSource;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import javax.xml.ws.ProtocolException;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Messages
/*     */ {
/*     */   public static Message create(JAXBContext context, Object jaxbObject, SOAPVersion soapVersion) {
/* 123 */     return JAXBMessage.create(context, jaxbObject, soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message createRaw(JAXBContext context, Object jaxbObject, SOAPVersion soapVersion) {
/* 133 */     return JAXBMessage.createRaw(context, jaxbObject, soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message create(Marshaller marshaller, Object jaxbObject, SOAPVersion soapVersion) {
/* 141 */     return create(BindingContextFactory.getBindingContext(marshaller).getJAXBContext(), jaxbObject, soapVersion);
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
/*     */   public static Message create(SOAPMessage saaj) {
/* 158 */     return SAAJFactory.create(saaj);
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
/*     */   public static Message createUsingPayload(Source payload, SOAPVersion ver) {
/* 174 */     if (payload instanceof DOMSource) {
/* 175 */       if (((DOMSource)payload).getNode() == null) {
/* 176 */         return (Message)new EmptyMessageImpl(ver);
/*     */       }
/* 178 */     } else if (payload instanceof StreamSource) {
/* 179 */       StreamSource ss = (StreamSource)payload;
/* 180 */       if (ss.getInputStream() == null && ss.getReader() == null && ss.getSystemId() == null) {
/* 181 */         return (Message)new EmptyMessageImpl(ver);
/*     */       }
/* 183 */     } else if (payload instanceof SAXSource) {
/* 184 */       SAXSource ss = (SAXSource)payload;
/* 185 */       if (ss.getInputSource() == null && ss.getXMLReader() == null) {
/* 186 */         return (Message)new EmptyMessageImpl(ver);
/*     */       }
/*     */     } 
/* 189 */     return (Message)new PayloadSourceMessage(payload, ver);
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
/*     */   public static Message createUsingPayload(XMLStreamReader payload, SOAPVersion ver) {
/* 205 */     return (Message)new PayloadStreamReaderMessage(payload, ver);
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
/*     */   public static Message createUsingPayload(Element payload, SOAPVersion ver) {
/* 220 */     return (Message)new DOMMessage(ver, payload);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message create(Element soapEnvelope) {
/* 231 */     SOAPVersion ver = SOAPVersion.fromNsUri(soapEnvelope.getNamespaceURI());
/*     */     
/* 233 */     Element header = DOMUtil.getFirstChild(soapEnvelope, ver.nsUri, "Header");
/* 234 */     HeaderList headers = null;
/* 235 */     if (header != null) {
/* 236 */       for (Node n = header.getFirstChild(); n != null; n = n.getNextSibling()) {
/* 237 */         if (n.getNodeType() == 1) {
/* 238 */           if (headers == null)
/* 239 */             headers = new HeaderList(ver); 
/* 240 */           headers.add(Headers.create((Element)n));
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 246 */     Element body = DOMUtil.getFirstChild(soapEnvelope, ver.nsUri, "Body");
/* 247 */     if (body == null)
/* 248 */       throw new WebServiceException("Message doesn't have <S:Body> " + soapEnvelope); 
/* 249 */     Element payload = DOMUtil.getFirstChild(soapEnvelope, ver.nsUri, "Body");
/*     */     
/* 251 */     if (payload == null) {
/* 252 */       return (Message)new EmptyMessageImpl(headers, (AttachmentSet)new AttachmentSetImpl(), ver);
/*     */     }
/* 254 */     return (Message)new DOMMessage(ver, headers, payload);
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
/*     */   public static Message create(Source envelope, SOAPVersion soapVersion) {
/* 269 */     return (Message)new ProtocolSourceMessage(envelope, soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message createEmpty(SOAPVersion soapVersion) {
/* 277 */     return (Message)new EmptyMessageImpl(soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static Message create(@NotNull XMLStreamReader reader) {
/* 289 */     if (reader.getEventType() != 1)
/* 290 */       XMLStreamReaderUtil.nextElementContent(reader); 
/* 291 */     assert reader.getEventType() == 1 : reader.getEventType();
/*     */     
/* 293 */     SOAPVersion ver = SOAPVersion.fromNsUri(reader.getNamespaceURI());
/*     */     
/* 295 */     return Codecs.createSOAPEnvelopeXmlCodec(ver).decode(reader);
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
/*     */   @NotNull
/*     */   public static Message create(@NotNull XMLStreamBuffer xsb) {
/*     */     try {
/* 311 */       return create((XMLStreamReader)xsb.readAsXMLStreamReader());
/* 312 */     } catch (XMLStreamException e) {
/* 313 */       throw new XMLStreamReaderException(e);
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
/*     */   
/*     */   public static Message create(Throwable t, SOAPVersion soapVersion) {
/* 329 */     return SOAPFaultBuilder.createSOAPFaultMessage(soapVersion, null, t);
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
/*     */   public static Message create(SOAPFault fault) {
/* 347 */     SOAPVersion ver = SOAPVersion.fromNsUri(fault.getNamespaceURI());
/* 348 */     return (Message)new DOMMessage(ver, fault);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Message createAddressingFaultMessage(WSBinding binding, QName missingHeader) {
/* 356 */     return createAddressingFaultMessage(binding, null, missingHeader);
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
/*     */   public static Message createAddressingFaultMessage(WSBinding binding, Packet p, QName missingHeader) {
/* 372 */     AddressingVersion av = binding.getAddressingVersion();
/* 373 */     if (av == null)
/*     */     {
/* 375 */       throw new WebServiceException(AddressingMessages.ADDRESSING_SHOULD_BE_ENABLED());
/*     */     }
/* 377 */     WsaTubeHelper helper = av.getWsaHelper(null, null, binding);
/* 378 */     return create(helper.newMapRequiredFault(new MissingAddressingHeaderException(missingHeader, p)));
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
/*     */   public static Message create(@NotNull String unsupportedAction, @NotNull AddressingVersion av, @NotNull SOAPVersion sv) {
/*     */     Message faultMessage;
/* 392 */     QName subcode = av.actionNotSupportedTag;
/* 393 */     String faultstring = String.format(av.actionNotSupportedText, new Object[] { unsupportedAction });
/*     */     
/*     */     try {
/*     */       SOAPFault fault;
/*     */       
/* 398 */       if (sv == SOAPVersion.SOAP_12) {
/* 399 */         fault = SOAPVersion.SOAP_12.getSOAPFactory().createFault();
/* 400 */         fault.setFaultCode(SOAPConstants.SOAP_SENDER_FAULT);
/* 401 */         fault.appendFaultSubcode(subcode);
/* 402 */         Detail detail = fault.addDetail();
/* 403 */         SOAPElement se = detail.addChildElement(av.problemActionTag);
/* 404 */         se = se.addChildElement(av.actionTag);
/* 405 */         se.addTextNode(unsupportedAction);
/*     */       } else {
/* 407 */         fault = SOAPVersion.SOAP_11.getSOAPFactory().createFault();
/* 408 */         fault.setFaultCode(subcode);
/*     */       } 
/* 410 */       fault.setFaultString(faultstring);
/*     */       
/* 412 */       faultMessage = SOAPFaultBuilder.createSOAPFaultMessage(sv, fault);
/* 413 */       if (sv == SOAPVersion.SOAP_11) {
/* 414 */         faultMessage.getMessageHeaders().add((Header)new ProblemActionHeader(unsupportedAction, av));
/*     */       }
/* 416 */     } catch (SOAPException e) {
/* 417 */       throw new WebServiceException(e);
/*     */     } 
/*     */     
/* 420 */     return faultMessage;
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
/*     */   @NotNull
/*     */   public static Message create(@NotNull SOAPVersion soapVersion, @NotNull ProtocolException pex, @Nullable QName faultcode) {
/* 433 */     return SOAPFaultBuilder.createSOAPFaultMessage(soapVersion, pex, faultcode);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\api\message\Messages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */