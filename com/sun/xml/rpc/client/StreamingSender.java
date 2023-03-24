/*     */ package com.sun.xml.rpc.client;
/*     */ 
/*     */ import com.sun.xml.messaging.saaj.util.ByteInputStream;
/*     */ import com.sun.xml.rpc.encoding.JAXRPCSerializer;
/*     */ import com.sun.xml.rpc.encoding.ReferenceableSerializer;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*     */ import com.sun.xml.rpc.encoding.SOAPDeserializationState;
/*     */ import com.sun.xml.rpc.encoding.SOAPFaultInfoSerializer;
/*     */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*     */ import com.sun.xml.rpc.encoding.soap.SOAPConstants;
/*     */ import com.sun.xml.rpc.soap.message.SOAPBlockInfo;
/*     */ import com.sun.xml.rpc.soap.message.SOAPFaultInfo;
/*     */ import com.sun.xml.rpc.soap.message.SOAPHeaderBlockInfo;
/*     */ import com.sun.xml.rpc.soap.message.SOAPMessageContext;
/*     */ import com.sun.xml.rpc.soap.streaming.SOAPProtocolViolationException;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.FastInfosetReaderFactoryImpl;
/*     */ import com.sun.xml.rpc.streaming.PrefixFactory;
/*     */ import com.sun.xml.rpc.streaming.PrefixFactoryImpl;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderFactory;
/*     */ import com.sun.xml.rpc.streaming.XMLWriter;
/*     */ import com.sun.xml.rpc.streaming.XMLWriterFactory;
/*     */ import com.sun.xml.rpc.streaming.XmlTreeReader;
/*     */ import com.sun.xml.rpc.streaming.XmlTreeWriter;
/*     */ import com.sun.xml.rpc.util.HeaderFaultException;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.InputStream;
/*     */ import java.rmi.MarshalException;
/*     */ import java.rmi.RemoteException;
/*     */ import java.rmi.ServerException;
/*     */ import java.util.Iterator;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.rpc.handler.HandlerChain;
/*     */ import javax.xml.rpc.handler.MessageContext;
/*     */ import javax.xml.rpc.soap.SOAPFaultException;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.transform.stream.StreamSource;
/*     */ import org.jvnet.fastinfoset.FastInfosetSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class StreamingSender
/*     */ {
/*     */   protected StreamingSenderState _start(HandlerChain handlerChain) {
/*  83 */     SOAPMessageContext messageContext = new SOAPMessageContext();
/*  84 */     ((HandlerChainImpl)handlerChain).addUnderstoodHeaders(_getUnderstoodHeaders());
/*     */ 
/*     */ 
/*     */     
/*  88 */     return new StreamingSenderState(messageContext, handlerChain, false, true);
/*     */   }
/*     */   
/*     */   protected String _getActor() {
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _send(String endpoint, StreamingSenderState state) throws Exception {
/*     */     XmlTreeReader xmlTreeReader;
/* 100 */     _preSendingHook(state);
/*     */     
/* 102 */     _preRequestWritingHook(state);
/*     */     
/* 104 */     _writeRequest(state);
/*     */     
/* 106 */     _postRequestWritingHook(state);
/*     */     
/* 108 */     boolean invoke = _preRequestSendingHook(state);
/*     */ 
/*     */ 
/*     */     
/* 112 */     if (invoke == true) {
/* 113 */       _getTransport().invoke(endpoint, state.getMessageContext());
/*     */     }
/* 115 */     _postRequestSendingHook(state);
/*     */     
/* 117 */     XMLReader reader = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 122 */       SOAPFaultInfo fault = null;
/*     */       
/* 124 */       _preHandlingHook(state);
/*     */       
/* 126 */       Source source = state.getResponse().getMessage().getSOAPPart().getContent();
/*     */ 
/*     */       
/* 129 */       if (source instanceof StreamSource && ((StreamSource)source).getInputStream() != null) {
/*     */ 
/*     */         
/* 132 */         InputStream istream = ((StreamSource)source).getInputStream();
/* 133 */         reader = _getXMLReaderFactory().createXMLReader(istream, true);
/*     */       }
/* 135 */       else if (source instanceof FastInfosetSource) {
/*     */         
/* 137 */         InputStream istream = ((FastInfosetSource)source).getInputSource().getByteStream();
/*     */         
/* 139 */         reader = FastInfosetReaderFactoryImpl.newInstance().createXMLReader(istream, true);
/*     */       } else {
/*     */         
/* 142 */         xmlTreeReader = new XmlTreeReader(state.getResponse().getMessage().getSOAPPart().getEnvelope());
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 151 */       _preEnvelopeReadingHook(state);
/*     */       
/* 153 */       xmlTreeReader.nextElementContent();
/*     */       
/* 155 */       SOAPDeserializationContext deserializationContext = new SOAPDeserializationContext();
/*     */       
/* 157 */       deserializationContext.setMessage(state.getResponse().getMessage());
/*     */ 
/*     */       
/* 160 */       if (xmlTreeReader.getState() == 1 && "http://schemas.xmlsoap.org/soap/envelope/".equals(xmlTreeReader.getURI()) && "Envelope".equals(xmlTreeReader.getLocalName())) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 165 */         boolean envelopePushedEncodingStyle = deserializationContext.processEncodingStyle((XMLReader)xmlTreeReader);
/*     */ 
/*     */         
/* 168 */         _preHeaderReadingHook(state);
/*     */         
/* 170 */         if (state.isFailure()) {
/*     */           return;
/*     */         }
/*     */         
/* 174 */         xmlTreeReader.nextElementContent();
/*     */         
/* 176 */         if (xmlTreeReader.getState() == 1 && "http://schemas.xmlsoap.org/soap/envelope/".equals(xmlTreeReader.getURI())) {
/*     */ 
/*     */           
/* 179 */           if ("Header".equals(xmlTreeReader.getLocalName())) {
/*     */ 
/*     */ 
/*     */             
/* 183 */             boolean headerPushedEncodingStyle = deserializationContext.processEncodingStyle((XMLReader)xmlTreeReader);
/*     */ 
/*     */             
/* 186 */             _processHeaders((XMLReader)xmlTreeReader, deserializationContext, state);
/*     */ 
/*     */ 
/*     */             
/* 190 */             _postHeaderReadingHook(state);
/*     */ 
/*     */ 
/*     */             
/* 194 */             if (headerPushedEncodingStyle) {
/* 195 */               deserializationContext.popEncodingStyle();
/*     */             }
/*     */             
/* 198 */             xmlTreeReader.nextElementContent();
/*     */           } 
/*     */ 
/*     */           
/* 202 */           if (xmlTreeReader.getState() == 1 && "http://schemas.xmlsoap.org/soap/envelope/".equals(xmlTreeReader.getURI()) && "Body".equals(xmlTreeReader.getLocalName())) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 207 */             boolean bodyPushedEncodingStyle = deserializationContext.processEncodingStyle((XMLReader)xmlTreeReader);
/*     */             
/* 209 */             Object faultOrFaultState = null;
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 214 */             if (xmlTreeReader.nextElementContent() == 2) {
/* 215 */               _handleEmptyBody((XMLReader)xmlTreeReader, deserializationContext, state);
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 220 */               if (state.isFailure()) {
/*     */                 return;
/*     */               }
/*     */               
/* 224 */               _preBodyReadingHook(state);
/*     */               
/* 226 */               if (state.isFailure()) {
/*     */                 return;
/*     */               }
/*     */             } else {
/* 230 */               _preBodyReadingHook(state);
/*     */               
/* 232 */               if (state.isFailure()) {
/*     */                 return;
/*     */               }
/*     */ 
/*     */               
/* 237 */               if (xmlTreeReader.getName().equals(QNAME_SOAP_FAULT)) {
/* 238 */                 faultOrFaultState = _readBodyFaultElement((XMLReader)xmlTreeReader, deserializationContext, state);
/*     */ 
/*     */               
/*     */               }
/*     */               else {
/*     */ 
/*     */                 
/* 245 */                 _readFirstBodyElement((XMLReader)xmlTreeReader, deserializationContext, state);
/*     */               } 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 251 */               if (state.isFailure()) {
/*     */                 return;
/*     */               }
/*     */ 
/*     */               
/* 256 */               deserializationContext.deserializeMultiRefObjects((XMLReader)xmlTreeReader);
/*     */ 
/*     */               
/* 259 */               deserializationContext.runPostDeserializationAction();
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 264 */             _postBodyReadingHook(state);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 269 */             while (xmlTreeReader.nextElementContent() == 1) {
/* 270 */               xmlTreeReader.skipElement();
/*     */             }
/*     */             
/* 273 */             deserializationContext.doneDeserializing();
/*     */             
/* 275 */             if (bodyPushedEncodingStyle) {
/* 276 */               deserializationContext.popEncodingStyle();
/*     */             }
/*     */             
/* 279 */             if (faultOrFaultState != null) {
/* 280 */               if (faultOrFaultState instanceof SOAPFaultInfo) {
/* 281 */                 fault = (SOAPFaultInfo)faultOrFaultState;
/* 282 */               } else if (faultOrFaultState instanceof SOAPDeserializationState) {
/*     */ 
/*     */                 
/* 285 */                 fault = (SOAPFaultInfo)((SOAPDeserializationState)faultOrFaultState).getInstance();
/*     */               
/*     */               }
/*     */               else {
/*     */ 
/*     */                 
/* 291 */                 throw new SenderException("sender.response.unrecognizedFault");
/*     */               } 
/*     */             }
/*     */           } else {
/* 295 */             throw new SOAPProtocolViolationException("soap.protocol.missingBody");
/*     */           } 
/*     */         } else {
/* 298 */           throw new SOAPProtocolViolationException("soap.protocol.invalidEnvelopeContent");
/*     */         } 
/*     */         
/* 301 */         if (envelopePushedEncodingStyle)
/* 302 */           deserializationContext.popEncodingStyle(); 
/*     */       } else {
/* 304 */         if (xmlTreeReader.getState() == 1 && "Envelope".equals(xmlTreeReader.getLocalName()))
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 309 */           throw new SOAPProtocolViolationException("soap.protocol.envelopeVersionMismatch");
/*     */         }
/*     */         
/* 312 */         throw new SOAPProtocolViolationException("soap.protocol.notAnEnvelope");
/*     */       } 
/*     */       
/* 315 */       if (state.isFailure()) {
/*     */         return;
/*     */       }
/*     */       
/* 319 */       _postEnvelopeReadingHook(state);
/*     */ 
/*     */       
/* 322 */       if (fault != null)
/*     */       {
/* 324 */         _raiseFault(fault, state);
/*     */       }
/*     */     }
/* 327 */     catch (SOAPFaultException e) {
/* 328 */       throw e;
/* 329 */     } catch (RuntimeException rex) {
/* 330 */       _handleRuntimeExceptionInSend(rex);
/* 331 */     } catch (Exception e) {
/* 332 */       throw e;
/*     */     } finally {
/* 334 */       if (xmlTreeReader != null) {
/* 335 */         xmlTreeReader.close();
/*     */       }
/*     */       
/*     */       try {
/* 339 */         _postHandlingHook(state);
/*     */       } finally {
/* 341 */         _postSendingHook(state);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _handleRuntimeExceptionInSend(RuntimeException rex) throws Exception {
/* 348 */     throw new RemoteException("Runtime exception", rex);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _sendOneWay(String endpoint, StreamingSenderState state) throws Exception {
/* 354 */     _preSendingHook(state);
/*     */     
/* 356 */     _preRequestWritingHook(state);
/*     */     
/* 358 */     _writeRequest(state);
/*     */     
/* 360 */     _postRequestWritingHook(state);
/*     */     
/* 362 */     boolean invoke = _preRequestSendingHook(state);
/*     */     
/* 364 */     if (invoke == true) {
/* 365 */       _getTransport().invokeOneWay(endpoint, state.getMessageContext());
/*     */     }
/* 367 */     _postRequestSendingHook(state);
/*     */     
/* 369 */     _postSendingHook(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _processHeaders(XMLReader reader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
/* 380 */     while (reader.nextElementContent() != 2) {
/* 381 */       _processHeaderElement(reader, deserializationContext, state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _processHeaderElement(XMLReader reader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
/* 390 */     Attributes attributes = reader.getAttributes();
/* 391 */     String actorAttr = attributes.getValue("http://schemas.xmlsoap.org/soap/envelope/", "actor");
/*     */ 
/*     */ 
/*     */     
/* 395 */     String mustUnderstandAttr = attributes.getValue("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand");
/*     */ 
/*     */ 
/*     */     
/* 399 */     boolean mustUnderstand = false;
/* 400 */     if (mustUnderstandAttr != null)
/*     */     {
/* 402 */       if (mustUnderstandAttr.equals("1") || mustUnderstandAttr.equals("true")) {
/*     */         
/* 404 */         mustUnderstand = true;
/* 405 */       } else if (!mustUnderstandAttr.equals("0") && !mustUnderstandAttr.equals("false")) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 410 */         throw new SenderException("sender.response.illegalValueOfMustUnderstandAttribute", mustUnderstandAttr);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 416 */     if ((_getActor() == null && (actorAttr == null || actorAttr.equals("http://schemas.xmlsoap.org/soap/actor/next"))) || (_getActor() != null && _getActor().equals(actorAttr))) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 421 */       SOAPHeaderBlockInfo headerInfo = new SOAPHeaderBlockInfo(reader.getName(), actorAttr, mustUnderstand);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 426 */       boolean succeeded = _readHeaderElement(headerInfo, reader, deserializationContext, state);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 432 */       if (!succeeded && mustUnderstand) {
/* 433 */         throw new SOAPFaultException(SOAPConstants.FAULT_CODE_MUST_UNDERSTAND, "SOAP must understand error", _getActor(), null);
/*     */ 
/*     */       
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 442 */       reader.skipElement();
/*     */       return;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean _readHeaderElement(SOAPHeaderBlockInfo headerInfo, XMLReader reader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
/* 468 */     reader.skipElement();
/* 469 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Object _readBodyFaultElement(XMLReader reader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
/* 477 */     return faultInfoSerializer.deserialize(null, reader, deserializationContext);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _readFirstBodyElement(XMLReader reader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
/* 488 */     reader.skipElement();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _handleEmptyBody(XMLReader reader, SOAPDeserializationContext deserializationContext, StreamingSenderState state) throws Exception {
/* 496 */     throw new SOAPProtocolViolationException("soap.protocol.emptyBody");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _raiseFault(SOAPFaultInfo fault, StreamingSenderState state) throws Exception {
/* 501 */     if (fault.getDetail() != null && fault.getDetail() instanceof Exception)
/*     */     {
/* 503 */       throw (Exception)fault.getDetail();
/*     */     }
/*     */     
/* 506 */     Object detail = fault.getDetail();
/* 507 */     if (detail != null && detail instanceof Detail) {
/* 508 */       throw new SOAPFaultException(fault.getCode(), fault.getString(), fault.getActor(), (Detail)detail);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 513 */     if (detail == null) {
/*     */       
/* 515 */       Object object = null;
/* 516 */       if (state.getResponse().headers().hasNext()) {
/* 517 */         object = state.getResponse().headers().next();
/* 518 */         throw new RemoteException(fault.getString(), new HeaderFaultException(fault.getString(), object));
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 526 */     if (fault.getCode().equals(SOAPConstants.FAULT_CODE_SERVER))
/* 527 */       throw new ServerException(fault.getString()); 
/* 528 */     if (fault.getCode().equals(SOAPConstants.FAULT_CODE_DATA_ENCODING_UNKNOWN))
/*     */     {
/*     */       
/* 531 */       throw new MarshalException(fault.getString()); } 
/* 532 */     if (fault.getCode().equals(SOAPConstants.FAULT_CODE_PROCEDURE_NOT_PRESENT) || fault.getCode().equals(SOAPConstants.FAULT_CODE_BAD_ARGUMENTS))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 537 */       throw new RemoteException(fault.getString());
/*     */     }
/* 539 */     Object obj = null;
/*     */ 
/*     */     
/* 542 */     if (state.getResponse().headers().hasNext()) {
/* 543 */       obj = state.getResponse().headers().next();
/*     */     }
/* 545 */     throw new SOAPFaultException(fault.getCode(), fault.getString(), fault.getActor(), (Detail)detail);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _writeRequest(StreamingSenderState state) throws Exception {
/*     */     XmlTreeWriter xmlTreeWriter;
/* 557 */     SOAPBlockInfo bodyInfo = state.getRequest().getBody();
/* 558 */     boolean pushedEncodingStyle = false;
/*     */     
/* 560 */     if (bodyInfo == null || bodyInfo.getSerializer() == null) {
/* 561 */       throw new SenderException("sender.request.missingBodyInfo");
/*     */     }
/*     */ 
/*     */     
/* 565 */     XMLWriter writer = null;
/* 566 */     ByteArrayOutputStream bufferedStream = null;
/* 567 */     HandlerChain handlerChain = state.getHandlerChain();
/* 568 */     if (handlerChain == null || handlerChain.size() == 0) {
/*     */       
/* 570 */       bufferedStream = new ByteArrayOutputStream();
/* 571 */       writer = _getXMLWriterFactory().createXMLWriter(bufferedStream, _getPreferredCharacterEncoding());
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 576 */       xmlTreeWriter = new XmlTreeWriter(state.getRequest().getMessage().getSOAPPart());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 581 */     xmlTreeWriter.setPrefixFactory((PrefixFactory)new PrefixFactoryImpl("ans"));
/*     */     
/* 583 */     SOAPSerializationContext serializationContext = new SOAPSerializationContext("ID");
/*     */     
/* 585 */     serializationContext.setMessage(state.getRequest().getMessage());
/*     */ 
/*     */     
/* 588 */     xmlTreeWriter.startElement("Envelope", "http://schemas.xmlsoap.org/soap/envelope/", "env");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 594 */     xmlTreeWriter.writeNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
/* 595 */     xmlTreeWriter.writeNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
/* 596 */     xmlTreeWriter.writeNamespaceDeclaration("enc", "http://schemas.xmlsoap.org/soap/encoding/");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 601 */     String[] namespaceDeclarations = _getNamespaceDeclarations();
/* 602 */     if (namespaceDeclarations != null) {
/* 603 */       for (int i = 0; i < namespaceDeclarations.length; i += 2) {
/* 604 */         xmlTreeWriter.writeNamespaceDeclaration(namespaceDeclarations[i], namespaceDeclarations[i + 1]);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 610 */     if (_getDefaultEnvelopeEncodingStyle() != null) {
/* 611 */       pushedEncodingStyle = serializationContext.pushEncodingStyle(_getDefaultEnvelopeEncodingStyle(), (XMLWriter)xmlTreeWriter);
/*     */ 
/*     */     
/*     */     }
/* 615 */     else if (_getImplicitEnvelopeEncodingStyle() != null) {
/* 616 */       pushedEncodingStyle = serializationContext.setImplicitEncodingStyle(_getImplicitEnvelopeEncodingStyle());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 622 */     boolean wroteHeader = false;
/* 623 */     for (Iterator<SOAPHeaderBlockInfo> iter = state.getRequest().headers(); iter.hasNext(); ) {
/* 624 */       SOAPHeaderBlockInfo headerInfo = iter.next();
/* 625 */       if (headerInfo.getValue() != null && headerInfo.getSerializer() != null) {
/*     */         
/* 627 */         if (!wroteHeader) {
/* 628 */           xmlTreeWriter.startElement("Header", "http://schemas.xmlsoap.org/soap/envelope/");
/*     */ 
/*     */           
/* 631 */           wroteHeader = true;
/*     */         } 
/*     */         
/* 634 */         serializationContext.beginFragment();
/* 635 */         JAXRPCSerializer serializer = headerInfo.getSerializer();
/* 636 */         if (serializer instanceof ReferenceableSerializer) {
/* 637 */           ((ReferenceableSerializer)serializer).serializeInstance(headerInfo.getValue(), headerInfo.getName(), false, (XMLWriter)xmlTreeWriter, serializationContext);
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 644 */           serializer.serialize(headerInfo.getValue(), headerInfo.getName(), null, (XMLWriter)xmlTreeWriter, serializationContext);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 651 */         serializationContext.endFragment();
/*     */       } 
/*     */     } 
/*     */     
/* 655 */     if (wroteHeader) {
/* 656 */       xmlTreeWriter.endElement();
/*     */     }
/*     */ 
/*     */     
/* 660 */     xmlTreeWriter.startElement("Body", "http://schemas.xmlsoap.org/soap/envelope/", "env");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 665 */     serializationContext.beginFragment();
/*     */     
/* 667 */     bodyInfo.getSerializer().serialize(bodyInfo.getValue(), bodyInfo.getName(), null, (XMLWriter)xmlTreeWriter, serializationContext);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 673 */     serializationContext.serializeMultiRefObjects((XMLWriter)xmlTreeWriter);
/* 674 */     serializationContext.endFragment();
/*     */     
/* 676 */     xmlTreeWriter.endElement();
/* 677 */     xmlTreeWriter.endElement();
/* 678 */     if (pushedEncodingStyle) {
/* 679 */       serializationContext.popEncodingStyle();
/*     */     }
/* 681 */     xmlTreeWriter.close();
/*     */     
/* 683 */     if (handlerChain == null || handlerChain.size() == 0) {
/*     */       
/* 685 */       byte[] data = bufferedStream.toByteArray();
/*     */ 
/*     */       
/* 688 */       ByteInputStream bis = new ByteInputStream(data, data.length);
/* 689 */       state.getRequest().getMessage().getSOAPPart().setContent((xmlTreeWriter instanceof com.sun.xml.rpc.streaming.FastInfosetWriter) ? (Source)new FastInfosetSource((InputStream)bis) : new StreamSource((InputStream)bis));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String[] _getNamespaceDeclarations() {
/* 699 */     return null;
/*     */   }
/*     */   
/*     */   public QName[] _getUnderstoodHeaders() {
/* 703 */     return new QName[0];
/*     */   }
/*     */ 
/*     */   
/*     */   protected String _getDefaultEnvelopeEncodingStyle() {
/* 708 */     return "http://schemas.xmlsoap.org/soap/encoding/";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String _getImplicitEnvelopeEncodingStyle() {
/* 713 */     return null;
/*     */   }
/*     */   
/*     */   protected String _getPreferredCharacterEncoding() {
/* 717 */     return "UTF-8";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _preSendingHook(StreamingSenderState state) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _postSendingHook(StreamingSenderState state) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _preHandlingHook(StreamingSenderState state) throws Exception {
/* 732 */     _callResponseHandlers(state);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _postHandlingHook(StreamingSenderState state) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _preRequestWritingHook(StreamingSenderState state) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _postRequestWritingHook(StreamingSenderState state) throws Exception {}
/*     */ 
/*     */   
/*     */   protected boolean _preRequestSendingHook(StreamingSenderState state) throws Exception {
/* 749 */     return _callRequestHandlers(state);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _postRequestSendingHook(StreamingSenderState state) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _preEnvelopeReadingHook(StreamingSenderState state) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _preHeaderReadingHook(StreamingSenderState state) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _postHeaderReadingHook(StreamingSenderState state) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _preBodyReadingHook(StreamingSenderState state) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _postBodyReadingHook(StreamingSenderState state) throws Exception {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void _postEnvelopeReadingHook(StreamingSenderState state) throws Exception {}
/*     */ 
/*     */   
/*     */   protected boolean _callRequestHandlers(StreamingSenderState state) throws Exception {
/* 782 */     HandlerChain handlerChain = state.getHandlerChain();
/*     */     
/* 784 */     if (handlerChain != null) {
/*     */       try {
/* 786 */         return handlerChain.handleRequest((MessageContext)state.getMessageContext());
/* 787 */       } catch (RuntimeException e) {
/* 788 */         throw new RemoteException("request handler error: ", e);
/*     */       } 
/*     */     }
/*     */     
/* 792 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void _callResponseHandlers(StreamingSenderState state) throws Exception {
/* 797 */     HandlerChainImpl handlerChain = (HandlerChainImpl)state.getHandlerChain();
/*     */     
/* 799 */     if (hasNonEmptyHandlerChain(handlerChain)) {
/* 800 */       boolean allUnderstood = handlerChain.checkMustUnderstand((MessageContext)state.getMessageContext());
/*     */       
/* 802 */       if (!allUnderstood) {
/* 803 */         throw new SOAPFaultException(SOAPConstants.FAULT_CODE_MUST_UNDERSTAND, "SOAP must understand error", _getActor(), null);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 810 */         handlerChain.handleResponse((MessageContext)state.getMessageContext());
/* 811 */       } catch (RuntimeException e) {
/* 812 */         throw new RemoteException("response handler error: ", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasNonEmptyHandlerChain(HandlerChain chain) {
/* 818 */     return (chain != null && !chain.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract ClientTransport _getTransport();
/*     */ 
/*     */   
/*     */   protected XMLReaderFactory _getXMLReaderFactory() {
/* 826 */     return XMLReaderFactory.newInstance();
/*     */   }
/*     */   
/*     */   protected XMLWriterFactory _getXMLWriterFactory() {
/* 830 */     return XMLWriterFactory.newInstance();
/*     */   }
/*     */   
/* 833 */   private static final SOAPFaultInfoSerializer faultInfoSerializer = new SOAPFaultInfoSerializer(true, false);
/*     */ 
/*     */   
/* 836 */   private static final QName QNAME_SOAP_FAULT = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Fault");
/*     */   private static final String MUST_UNDERSTAND_FAULT_MESSAGE_STRING = "SOAP must understand error";
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\StreamingSender.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */