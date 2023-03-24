/*      */ package com.sun.xml.rpc.server;
/*      */ 
/*      */ import com.sun.xml.messaging.saaj.soap.MessageImpl;
/*      */ import com.sun.xml.messaging.saaj.soap.SOAPVersionMismatchException;
/*      */ import com.sun.xml.messaging.saaj.util.ByteInputStream;
/*      */ import com.sun.xml.rpc.client.HandlerChainImpl;
/*      */ import com.sun.xml.rpc.encoding.JAXRPCSerializer;
/*      */ import com.sun.xml.rpc.encoding.ReferenceableSerializer;
/*      */ import com.sun.xml.rpc.encoding.SOAPDeserializationContext;
/*      */ import com.sun.xml.rpc.encoding.SOAPFaultInfoSerializer;
/*      */ import com.sun.xml.rpc.encoding.SOAPSerializationContext;
/*      */ import com.sun.xml.rpc.encoding.soap.SOAPConstants;
/*      */ import com.sun.xml.rpc.soap.message.Handler;
/*      */ import com.sun.xml.rpc.soap.message.InternalSOAPMessage;
/*      */ import com.sun.xml.rpc.soap.message.SOAPBlockInfo;
/*      */ import com.sun.xml.rpc.soap.message.SOAPFaultInfo;
/*      */ import com.sun.xml.rpc.soap.message.SOAPHeaderBlockInfo;
/*      */ import com.sun.xml.rpc.soap.message.SOAPMessageContext;
/*      */ import com.sun.xml.rpc.spi.runtime.SOAPMessageContext;
/*      */ import com.sun.xml.rpc.spi.runtime.StreamingHandler;
/*      */ import com.sun.xml.rpc.streaming.Attributes;
/*      */ import com.sun.xml.rpc.streaming.FastInfosetReaderFactoryImpl;
/*      */ import com.sun.xml.rpc.streaming.FastInfosetWriterFactoryImpl;
/*      */ import com.sun.xml.rpc.streaming.PrefixFactory;
/*      */ import com.sun.xml.rpc.streaming.PrefixFactoryImpl;
/*      */ import com.sun.xml.rpc.streaming.XMLReader;
/*      */ import com.sun.xml.rpc.streaming.XMLReaderFactory;
/*      */ import com.sun.xml.rpc.streaming.XMLWriter;
/*      */ import com.sun.xml.rpc.streaming.XMLWriterFactory;
/*      */ import com.sun.xml.rpc.streaming.XmlTreeReader;
/*      */ import com.sun.xml.rpc.streaming.XmlTreeWriter;
/*      */ import com.sun.xml.rpc.util.exception.JAXRPCExceptionBase;
/*      */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*      */ import com.sun.xml.rpc.util.localization.Localizable;
/*      */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*      */ import com.sun.xml.rpc.util.localization.Localizer;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.InputStream;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.Iterator;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.xml.namespace.QName;
/*      */ import javax.xml.rpc.JAXRPCException;
/*      */ import javax.xml.rpc.handler.HandlerChain;
/*      */ import javax.xml.rpc.handler.MessageContext;
/*      */ import javax.xml.rpc.soap.SOAPFaultException;
/*      */ import javax.xml.soap.Name;
/*      */ import javax.xml.soap.SOAPBody;
/*      */ import javax.xml.soap.SOAPElement;
/*      */ import javax.xml.soap.SOAPException;
/*      */ import javax.xml.soap.SOAPMessage;
/*      */ import javax.xml.transform.Source;
/*      */ import javax.xml.transform.stream.StreamSource;
/*      */ import org.jvnet.fastinfoset.FastInfosetSource;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class StreamingHandler
/*      */   implements Handler, StreamingHandler
/*      */ {
/*  101 */   private Localizer localizer = new Localizer();
/*  102 */   private LocalizableMessageFactory messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.tie");
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getActor() {
/*  107 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void handle(SOAPMessageContext spiContext) {
/*  112 */     SOAPMessageContext context = (SOAPMessageContext)spiContext;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  119 */     StreamingHandlerState state = new StreamingHandlerState(context);
/*      */     
/*      */     try {
/*      */       XmlTreeReader xmlTreeReader;
/*  123 */       XMLReader reader = null;
/*      */ 
/*      */       
/*      */       try {
/*  127 */         boolean invoke = preHandlingHook(state);
/*  128 */         if (!invoke) {
/*      */           return;
/*      */         }
/*      */         
/*  132 */         if (context.isFailure() || state.isFailure()) {
/*      */           return;
/*      */         }
/*      */         
/*  136 */         Source source = state.getRequest().getMessage().getSOAPPart().getContent();
/*  137 */         if (source instanceof StreamSource && ((StreamSource)source).getInputStream() != null) {
/*      */           
/*  139 */           InputStream istream = ((StreamSource)source).getInputStream();
/*      */           
/*  141 */           reader = getXMLReaderFactory(state).createXMLReader(istream, true);
/*      */         
/*      */         }
/*  144 */         else if (source instanceof FastInfosetSource) {
/*      */           
/*  146 */           InputStream istream = ((FastInfosetSource)source).getInputSource().getByteStream();
/*      */           
/*  148 */           reader = FastInfosetReaderFactoryImpl.newInstance().createXMLReader(istream, true);
/*      */         } else {
/*      */           
/*  151 */           xmlTreeReader = new XmlTreeReader(state.getRequest().getMessage().getSOAPPart().getEnvelope());
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  160 */         preEnvelopeReadingHook(state);
/*      */         
/*  162 */         xmlTreeReader.nextElementContent();
/*      */         
/*  164 */         SOAPDeserializationContext deserializationContext = new SOAPDeserializationContext();
/*      */         
/*  166 */         deserializationContext.setMessage(state.getRequest().getMessage());
/*      */ 
/*      */         
/*  169 */         if (xmlTreeReader.getState() == 1 && "http://schemas.xmlsoap.org/soap/envelope/".equals(xmlTreeReader.getURI()) && "Envelope".equals(xmlTreeReader.getLocalName())) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  174 */           boolean envelopePushedEncodingStyle = deserializationContext.processEncodingStyle((XMLReader)xmlTreeReader);
/*      */ 
/*      */           
/*  177 */           preHeaderReadingHook(state);
/*      */           
/*  179 */           if (state.isFailure()) {
/*      */             return;
/*      */           }
/*      */           
/*  183 */           xmlTreeReader.nextElementContent();
/*      */           
/*  185 */           if (xmlTreeReader.getState() == 1 && "http://schemas.xmlsoap.org/soap/envelope/".equals(xmlTreeReader.getURI())) {
/*      */ 
/*      */ 
/*      */             
/*  189 */             if ("Header".equals(xmlTreeReader.getLocalName())) {
/*      */ 
/*      */ 
/*      */               
/*  193 */               boolean headerPushedEncodingStyle = deserializationContext.processEncodingStyle((XMLReader)xmlTreeReader);
/*      */ 
/*      */ 
/*      */               
/*  197 */               processHeaders((XMLReader)xmlTreeReader, deserializationContext, state);
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  202 */               if (state.isFailure()) {
/*      */                 return;
/*      */               }
/*      */               
/*  206 */               postHeaderReadingHook(state);
/*      */               
/*  208 */               if (state.isFailure()) {
/*      */                 return;
/*      */               }
/*      */               
/*  212 */               if (headerPushedEncodingStyle) {
/*  213 */                 deserializationContext.popEncodingStyle();
/*      */               }
/*      */               
/*  216 */               xmlTreeReader.nextElementContent();
/*      */             } 
/*      */             
/*  219 */             if (xmlTreeReader.getState() == 1 && "http://schemas.xmlsoap.org/soap/envelope/".equals(xmlTreeReader.getURI()) && "Body".equals(xmlTreeReader.getLocalName())) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  225 */               boolean bodyPushedEncodingStyle = deserializationContext.processEncodingStyle((XMLReader)xmlTreeReader);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  231 */               if (xmlTreeReader.nextElementContent() == 2) {
/*  232 */                 handleEmptyBody((XMLReader)xmlTreeReader, deserializationContext, state);
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  237 */                 if (state.isFailure()) {
/*      */                   return;
/*      */                 }
/*      */                 
/*  241 */                 preBodyReadingHook(state);
/*      */                 
/*  243 */                 if (state.isFailure()) {
/*      */                   return;
/*      */                 }
/*      */               } else {
/*  247 */                 peekFirstBodyElement((XMLReader)xmlTreeReader, deserializationContext, state);
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  252 */                 if (state.isFailure()) {
/*      */                   return;
/*      */                 }
/*      */                 
/*  256 */                 preBodyReadingHook(state);
/*      */                 
/*  258 */                 if (state.isFailure()) {
/*      */                   return;
/*      */                 }
/*      */                 
/*  262 */                 readFirstBodyElement((XMLReader)xmlTreeReader, deserializationContext, state);
/*      */ 
/*      */ 
/*      */ 
/*      */                 
/*  267 */                 if (state.isFailure()) {
/*      */                   return;
/*      */                 }
/*      */                 
/*  271 */                 deserializationContext.deserializeMultiRefObjects((XMLReader)xmlTreeReader);
/*      */ 
/*      */ 
/*      */                 
/*  275 */                 deserializationContext.runPostDeserializationAction();
/*      */               } 
/*      */ 
/*      */               
/*  279 */               postBodyReadingHook(state);
/*      */ 
/*      */ 
/*      */               
/*  283 */               while (xmlTreeReader.nextElementContent() == 1) {
/*  284 */                 xmlTreeReader.skipElement();
/*      */               }
/*      */               
/*  287 */               if (bodyPushedEncodingStyle) {
/*  288 */                 deserializationContext.popEncodingStyle();
/*      */               }
/*      */               
/*  291 */               deserializationContext.doneDeserializing();
/*      */             } else {
/*      */               
/*  294 */               setBadRequestProp(state);
/*  295 */               SOAPFaultInfo fault = new SOAPFaultInfo(SOAPConstants.FAULT_CODE_CLIENT, "SOAP body expected", getActor());
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  300 */               reportFault(fault, state);
/*      */             } 
/*      */           } else {
/*  303 */             setBadRequestProp(state);
/*  304 */             SOAPFaultInfo fault = new SOAPFaultInfo(SOAPConstants.FAULT_CODE_CLIENT, "Invalid content in SOAP envelope", getActor());
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  309 */             reportFault(fault, state);
/*      */           } 
/*      */           
/*  312 */           if (envelopePushedEncodingStyle) {
/*  313 */             deserializationContext.popEncodingStyle();
/*      */           }
/*      */         }
/*  316 */         else if (xmlTreeReader.getState() == 1 && "Envelope".equals(xmlTreeReader.getLocalName())) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  321 */           setBadRequestProp(state);
/*  322 */           SOAPFaultInfo fault = new SOAPFaultInfo(SOAPConstants.FAULT_CODE_VERSION_MISMATCH, "Invalid SOAP envelope version", getActor());
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  327 */           reportFault(fault, state);
/*      */         }
/*      */         else {
/*      */           
/*  331 */           setBadRequestProp(state);
/*  332 */           SOAPFaultInfo fault = new SOAPFaultInfo(SOAPConstants.FAULT_CODE_CLIENT, "Invalid SOAP envelope", getActor());
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  337 */           reportFault(fault, state);
/*      */         } 
/*      */         
/*  340 */         if (state.isFailure()) {
/*      */           return;
/*      */         }
/*      */         
/*  344 */         postEnvelopeReadingHook(state);
/*      */         
/*  346 */         if (state.isFailure()) {
/*      */           return;
/*      */         }
/*      */         
/*  350 */         processingHook(state);
/*      */       }
/*  352 */       catch (Exception e) {
/*      */         
/*  354 */         logger.log(Level.SEVERE, e.getMessage(), e);
/*  355 */         String message = null;
/*  356 */         if (e instanceof Localizable) {
/*  357 */           message = this.localizer.localize((Localizable)e);
/*      */         } else {
/*  359 */           message = this.localizer.localize(this.messageFactory.getMessage("error.caughtExceptionWhileHandlingRequest", new Object[] { e.toString() }));
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  366 */         SOAPFaultInfo fault = null;
/*      */ 
/*      */ 
/*      */         
/*  370 */         if (e instanceof com.sun.xml.rpc.soap.streaming.SOAPProtocolViolationException || e instanceof com.sun.xml.rpc.encoding.DeserializationException) {
/*      */           
/*  372 */           logger.log(Level.SEVERE, message, e);
/*  373 */           String faultMessage = this.localizer.localize(this.messageFactory.getMessage("error.caughtExceptionWhileHandlingRequest", message));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  378 */           fault = new SOAPFaultInfo(SOAPConstants.FAULT_CODE_CLIENT, faultMessage, getActor());
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  383 */           reportFault(fault, state);
/*  384 */         } else if (e instanceof SOAPVersionMismatchException) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  393 */           logger.log(Level.SEVERE, message, e);
/*  394 */           String faultMessage = this.localizer.localize(this.messageFactory.getMessage("error.caughtExceptionWhileHandlingRequest", message));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  399 */           fault = new SOAPFaultInfo(SOAPConstants.FAULT_CODE_VERSION_MISMATCH, faultMessage, getActor());
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  404 */           reportFault(fault, state);
/*  405 */         } else if (isMalformedXML(e)) {
/*  406 */           setBadRequestProp(state);
/*  407 */           logger.log(Level.SEVERE, message, e);
/*  408 */           state.getResponse().setFailure(true);
/*  409 */           state.getMessageContext().setFailure(true);
/*      */         } else {
/*  411 */           logger.log(Level.SEVERE, message, e);
/*  412 */           String faultMessage = this.localizer.localize(this.messageFactory.getMessage("message.faultMessageForException", message));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  417 */           fault = new SOAPFaultInfo(SOAPConstants.FAULT_CODE_SERVER, faultMessage, getActor());
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  422 */           reportFault(fault, state);
/*      */         }
/*      */       
/*      */       } finally {
/*      */         
/*  427 */         if (xmlTreeReader != null) {
/*  428 */           xmlTreeReader.close();
/*      */         }
/*      */ 
/*      */         
/*      */         try {
/*  433 */           preResponseWritingHook(state);
/*      */           
/*  435 */           writeResponse(state);
/*      */           
/*  437 */           context.setFailure(state.getResponse().isFailure());
/*      */           
/*  439 */           context.setMessage(state.getResponse().getMessage());
/*      */           
/*  441 */           postResponseWritingHook(state);
/*      */         }
/*  443 */         catch (Exception e) {
/*      */           
/*  445 */           String message = null;
/*  446 */           if (e instanceof Localizable) {
/*  447 */             message = this.localizer.localize((Localizable)e);
/*      */           } else {
/*  449 */             message = this.localizer.localize(this.messageFactory.getMessage("error.caughtExceptionWhileHandlingRequest", new Object[] { e.toString() }));
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  455 */           logger.log(Level.SEVERE, message, e);
/*  456 */           String faultMessage = this.localizer.localize(this.messageFactory.getMessage("message.faultMessageForException", message));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  461 */           SOAPFaultInfo fault = new SOAPFaultInfo(SOAPConstants.FAULT_CODE_SERVER, faultMessage, getActor());
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  466 */           reportFault(fault, state);
/*  467 */           writeResponse(state);
/*  468 */           context.setFailure(state.getResponse().isFailure());
/*  469 */           context.setMessage(state.getResponse().getMessage());
/*      */         }
/*      */       
/*      */       }
/*      */     
/*  474 */     } catch (Exception e) {
/*      */       
/*  476 */       String message = null;
/*  477 */       if (e instanceof Localizable) {
/*  478 */         message = this.localizer.localize((Localizable)e);
/*      */       } else {
/*  480 */         message = this.localizer.localize(this.messageFactory.getMessage("error.caughtExceptionWhilePreparingResponse", new Object[] { e.toString() }));
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  486 */       logger.log(Level.SEVERE, message, e);
/*  487 */       context.writeInternalServerErrorResponse();
/*      */     } finally {
/*      */ 
/*      */       
/*      */       try {
/*  492 */         postHandlingHook(state);
/*  493 */       } catch (Exception e) {
/*      */         
/*  495 */         String message = null;
/*  496 */         if (e instanceof Localizable) {
/*  497 */           logger.log(Level.SEVERE, this.localizer.localize((Localizable)e), e);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  502 */           logger.log(Level.SEVERE, this.localizer.localize(this.messageFactory.getMessage("error.caughtExceptionPostHandlingRequest", new Object[] { e })));
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  509 */         logger.log(Level.SEVERE, message, e);
/*  510 */         context.writeInternalServerErrorResponse();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processHeaders(XMLReader reader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
/*  524 */     while (reader.nextElementContent() != 2) {
/*  525 */       if (!processHeaderElement(reader, deserializationContext, state)) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean processHeaderElement(XMLReader reader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
/*  536 */     Attributes attributes = reader.getAttributes();
/*  537 */     String actorAttr = attributes.getValue("http://schemas.xmlsoap.org/soap/envelope/", "actor");
/*      */ 
/*      */ 
/*      */     
/*  541 */     String mustUnderstandAttr = attributes.getValue("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  546 */     boolean mustUnderstand = false;
/*  547 */     if (mustUnderstandAttr != null) {
/*  548 */       if (mustUnderstandAttr.equals("1") || mustUnderstandAttr.equals("true")) {
/*      */         
/*  550 */         mustUnderstand = true;
/*  551 */       } else if (!mustUnderstandAttr.equals("0") && !mustUnderstandAttr.equals("false")) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  556 */         setBadRequestProp(state);
/*  557 */         SOAPFaultInfo fault = new SOAPFaultInfo(SOAPConstants.FAULT_CODE_CLIENT, "Illegal value of SOAP mustUnderstand attribute", getActor());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  562 */         reportFault(fault, state);
/*  563 */         return false;
/*      */       } 
/*      */     }
/*      */     
/*  567 */     if ((getActor() == null && (actorAttr == null || actorAttr.equals("http://schemas.xmlsoap.org/soap/actor/next"))) || (getActor() != null && getActor().equals(actorAttr))) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  572 */       SOAPHeaderBlockInfo headerInfo = new SOAPHeaderBlockInfo(reader.getName(), actorAttr, mustUnderstand);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  577 */       boolean succeeded = readHeaderElement(headerInfo, reader, deserializationContext, state);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  583 */       if (!succeeded && mustUnderstand) {
/*  584 */         SOAPFaultInfo fault = new SOAPFaultInfo(SOAPConstants.FAULT_CODE_MUST_UNDERSTAND, "SOAP must understand error", getActor());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  589 */         reportFault(fault, state);
/*  590 */         state.getRequest().setHeaderNotUnderstood(true);
/*  591 */         return false;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  596 */       reader.skipElement();
/*      */     } 
/*      */     
/*  599 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean readHeaderElement(SOAPHeaderBlockInfo headerInfo, XMLReader reader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
/*  609 */     reader.skipElement();
/*  610 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleEmptyBody(XMLReader reader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
/*  618 */     setBadRequestProp(state);
/*  619 */     SOAPFaultInfo fault = new SOAPFaultInfo(SOAPConstants.FAULT_CODE_CLIENT, "Missing body information", getActor());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  624 */     reportFault(fault, state);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void peekFirstBodyElement(XMLReader reader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void readFirstBodyElement(XMLReader reader, SOAPDeserializationContext deserializationContext, StreamingHandlerState state) throws Exception {
/*  640 */     reader.skipElement();
/*      */   }
/*      */   
/*      */   public int getOpcodeForRequestMessage(SOAPMessage request) {
/*  644 */     if (usesSOAPActionForDispatching()) {
/*  645 */       String[] soapactionheaders = request.getMimeHeaders().getHeader("SOAPAction");
/*      */       
/*  647 */       if (soapactionheaders.length > 0) {
/*  648 */         String soapaction = soapactionheaders[0];
/*  649 */         return getOpcodeForSOAPAction(soapaction);
/*      */       } 
/*      */     } else {
/*      */       try {
/*  653 */         SOAPBody body = request.getSOAPPart().getEnvelope().getBody();
/*  654 */         if (body != null) {
/*  655 */           Iterator<SOAPElement> iter = body.getChildElements();
/*  656 */           if (iter.hasNext()) {
/*  657 */             SOAPElement firstBodyElement = iter.next();
/*      */             
/*  659 */             Name firstBodyElementName = firstBodyElement.getElementName();
/*      */             
/*  661 */             return getOpcodeForFirstBodyElementName(new QName(firstBodyElementName.getURI(), firstBodyElementName.getLocalName()));
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  667 */           return getOpcodeForFirstBodyElementName(null);
/*      */         }
/*      */       
/*  670 */       } catch (SOAPException e) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  675 */     return -1;
/*      */   }
/*      */   
/*      */   public boolean usesSOAPActionForDispatching() {
/*  679 */     return false;
/*      */   }
/*      */   
/*      */   public int getOpcodeForFirstBodyElementName(QName name) {
/*  683 */     return -1;
/*      */   }
/*      */   
/*      */   public int getOpcodeForSOAPAction(String s) {
/*  687 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public Method getMethodForOpcode(int opcode) throws ClassNotFoundException, NoSuchMethodException {
/*  692 */     return null;
/*      */   }
/*      */   
/*      */   protected String[] getNamespaceDeclarations() {
/*  696 */     return null;
/*      */   }
/*      */   
/*      */   public QName[] getUnderstoodHeaders() {
/*  700 */     return new QName[0];
/*      */   }
/*      */   
/*      */   protected String getDefaultEnvelopeEncodingStyle() {
/*  704 */     return "http://schemas.xmlsoap.org/soap/encoding/";
/*      */   }
/*      */   
/*      */   protected String getImplicitEnvelopeEncodingStyle() {
/*  708 */     return null;
/*      */   }
/*      */   
/*      */   protected String getPreferredCharacterEncoding() {
/*  712 */     return "UTF-8";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeResponse(StreamingHandlerState state) throws Exception {
/*      */     XmlTreeWriter xmlTreeWriter;
/*  719 */     SOAPBlockInfo bodyInfo = state.getResponse().getBody();
/*  720 */     boolean pushedEncodingStyle = false;
/*      */     
/*  722 */     if (bodyInfo == null || bodyInfo.getSerializer() == null) {
/*  723 */       if (state.getHandlerFlag() == -1) {
/*      */         
/*  725 */         SOAPFaultInfo fault = new SOAPFaultInfo(SOAPConstants.FAULT_CODE_SERVER, "Missing body information", getActor());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  730 */         reportFault(fault, state);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*  735 */     ByteArrayOutputStream bufferedStream = null;
/*  736 */     XMLWriter writer = null;
/*  737 */     HandlerChainImpl handlerChainImpl = getHandlerChain();
/*  738 */     if (handlerChainImpl == null || handlerChainImpl.size() == 0) {
/*      */       
/*  740 */       bufferedStream = new ByteArrayOutputStream();
/*  741 */       writer = getXMLWriterFactory(state).createXMLWriter(bufferedStream, getPreferredCharacterEncoding());
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  747 */       MessageImpl response = (MessageImpl)state.getResponse().getMessage();
/*  748 */       MessageImpl request = (MessageImpl)state.getRequest().getMessage();
/*  749 */       if (request.acceptFastInfoset()) {
/*  750 */         response.setIsFastInfoset(true);
/*      */       }
/*      */       
/*  753 */       xmlTreeWriter = new XmlTreeWriter(response.getSOAPPart());
/*      */     } 
/*  755 */     xmlTreeWriter.setPrefixFactory((PrefixFactory)new PrefixFactoryImpl("ans"));
/*      */     
/*  757 */     SOAPSerializationContext serializationContext = new SOAPSerializationContext("ID");
/*      */     
/*  759 */     serializationContext.setMessage(state.getResponse().getMessage());
/*      */     
/*  761 */     xmlTreeWriter.startElement("Envelope", "http://schemas.xmlsoap.org/soap/envelope/", "env");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  766 */     xmlTreeWriter.writeNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema");
/*  767 */     xmlTreeWriter.writeNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
/*  768 */     xmlTreeWriter.writeNamespaceDeclaration("enc", "http://schemas.xmlsoap.org/soap/encoding/");
/*      */ 
/*      */ 
/*      */     
/*  772 */     String[] namespaceDeclarations = getNamespaceDeclarations();
/*  773 */     if (namespaceDeclarations != null) {
/*  774 */       for (int i = 0; i < namespaceDeclarations.length; i += 2) {
/*  775 */         xmlTreeWriter.writeNamespaceDeclaration(namespaceDeclarations[i], namespaceDeclarations[i + 1]);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  781 */     if (getDefaultEnvelopeEncodingStyle() != null) {
/*  782 */       pushedEncodingStyle = serializationContext.pushEncodingStyle(getDefaultEnvelopeEncodingStyle(), (XMLWriter)xmlTreeWriter);
/*      */ 
/*      */     
/*      */     }
/*  786 */     else if (getImplicitEnvelopeEncodingStyle() != null) {
/*  787 */       pushedEncodingStyle = serializationContext.setImplicitEncodingStyle(getImplicitEnvelopeEncodingStyle());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  792 */     boolean wroteHeader = false;
/*  793 */     for (Iterator<SOAPHeaderBlockInfo> iter = state.getResponse().headers(); iter.hasNext(); ) {
/*  794 */       SOAPHeaderBlockInfo headerInfo = iter.next();
/*  795 */       if (headerInfo.getValue() != null && headerInfo.getSerializer() != null) {
/*      */         
/*  797 */         if (!wroteHeader) {
/*  798 */           xmlTreeWriter.startElement("Header", "http://schemas.xmlsoap.org/soap/envelope/");
/*      */ 
/*      */           
/*  801 */           wroteHeader = true;
/*      */         } 
/*      */         
/*  804 */         serializationContext.beginFragment();
/*  805 */         JAXRPCSerializer serializer = headerInfo.getSerializer();
/*  806 */         if (serializer instanceof ReferenceableSerializer) {
/*  807 */           ((ReferenceableSerializer)serializer).serializeInstance(headerInfo.getValue(), headerInfo.getName(), false, (XMLWriter)xmlTreeWriter, serializationContext);
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/*  814 */           serializer.serialize(headerInfo.getValue(), headerInfo.getName(), null, (XMLWriter)xmlTreeWriter, serializationContext);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  821 */         serializationContext.endFragment();
/*      */       } 
/*      */     } 
/*      */     
/*  825 */     if (wroteHeader) {
/*  826 */       xmlTreeWriter.endElement();
/*      */     }
/*      */     
/*  829 */     xmlTreeWriter.startElement("Body", "http://schemas.xmlsoap.org/soap/envelope/", "env");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  834 */     serializationContext.beginFragment();
/*  835 */     bodyInfo.getSerializer().serialize(bodyInfo.getValue(), bodyInfo.getName(), null, (XMLWriter)xmlTreeWriter, serializationContext);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  842 */     serializationContext.serializeMultiRefObjects((XMLWriter)xmlTreeWriter);
/*  843 */     serializationContext.endFragment();
/*  844 */     xmlTreeWriter.endElement();
/*  845 */     xmlTreeWriter.endElement();
/*  846 */     if (pushedEncodingStyle) {
/*  847 */       serializationContext.popEncodingStyle();
/*      */     }
/*  849 */     xmlTreeWriter.close();
/*      */     
/*  851 */     if (handlerChainImpl == null || handlerChainImpl.size() == 0) {
/*      */       
/*  853 */       byte[] data = bufferedStream.toByteArray();
/*      */ 
/*      */       
/*  856 */       ByteInputStream bis = new ByteInputStream(data, data.length);
/*  857 */       state.getResponse().getMessage().getSOAPPart().setContent((xmlTreeWriter instanceof com.sun.xml.rpc.streaming.FastInfosetWriter) ? (Source)new FastInfosetSource((InputStream)bis) : new StreamSource((InputStream)bis));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean preHandlingHook(StreamingHandlerState state) throws Exception {
/*  868 */     return callRequestHandlers(state);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void postHandlingHook(StreamingHandlerState state) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preEnvelopeReadingHook(StreamingHandlerState state) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preHeaderReadingHook(StreamingHandlerState state) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void postHeaderReadingHook(StreamingHandlerState state) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preBodyReadingHook(StreamingHandlerState state) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void postBodyReadingHook(StreamingHandlerState state) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void postEnvelopeReadingHook(StreamingHandlerState state) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void processingHook(StreamingHandlerState state) throws Exception {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preResponseWritingHook(StreamingHandlerState state) throws Exception {}
/*      */ 
/*      */   
/*      */   protected void postResponseWritingHook(StreamingHandlerState state) throws Exception {
/*  909 */     String oneWay = (String)state.getMessageContext().getProperty("com.sun.xml.rpc.server.OneWayOperation");
/*      */ 
/*      */     
/*  912 */     if (oneWay == null || !oneWay.equalsIgnoreCase("true"))
/*      */     {
/*  914 */       if (state.getHandlerFlag() == 1) {
/*      */         
/*  916 */         callResponseHandlers(state);
/*  917 */       } else if (state.getHandlerFlag() == 0) {
/*      */ 
/*      */         
/*  920 */         callFaultHandlers(state);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void callFaultHandlers(StreamingHandlerState state) throws Exception {
/*  927 */     HandlerChainImpl handlerChainImpl = getHandlerChain();
/*      */     
/*  929 */     if (handlerChainImpl != null) {
/*  930 */       handlerChainImpl.handleFault((MessageContext)state.getMessageContext());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean callRequestHandlers(StreamingHandlerState state) throws Exception {
/*  941 */     HandlerChainImpl handlerChain = getHandlerChain();
/*  942 */     if (hasNonEmptyHandlerChain((HandlerChain)handlerChain)) {
/*      */       try {
/*  944 */         boolean allUnderstood = handlerChain.checkMustUnderstand((MessageContext)state.getMessageContext());
/*      */         
/*  946 */         if (!allUnderstood) {
/*  947 */           SOAPFaultInfo fault = new SOAPFaultInfo(SOAPConstants.FAULT_CODE_MUST_UNDERSTAND, "SOAP must understand error", getActor());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  953 */           reportFault(fault, state);
/*  954 */           state.getRequest().setHeaderNotUnderstood(true);
/*  955 */           state.setHandlerFlag(-1);
/*      */           
/*  957 */           return false;
/*      */         } 
/*      */         
/*  960 */         state.setHandlerFlag(1);
/*      */         
/*  962 */         boolean r = handlerChain.handleRequest((MessageContext)state.getMessageContext());
/*      */         
/*  964 */         if (!r) {
/*      */           
/*  966 */           MessageImpl request = (MessageImpl)state.getMessageContext().getMessage();
/*  967 */           if (request.acceptFastInfoset()) {
/*  968 */             request.setIsFastInfoset(true);
/*      */           }
/*  970 */           state.setResponse(new InternalSOAPMessage((SOAPMessage)request));
/*      */         } 
/*  972 */         return r;
/*  973 */       } catch (SOAPFaultException sfe) {
/*      */         
/*  975 */         MessageImpl request = (MessageImpl)state.getMessageContext().getMessage();
/*  976 */         if (request.acceptFastInfoset()) {
/*  977 */           request.setIsFastInfoset(true);
/*      */         }
/*  979 */         state.setResponse(new InternalSOAPMessage((SOAPMessage)request));
/*  980 */         state.setHandlerFlag(0);
/*  981 */         return false;
/*  982 */       } catch (SOAPVersionMismatchException svme) {
/*  983 */         SOAPFaultInfo fault = new SOAPFaultInfo(SOAPConstants.FAULT_CODE_VERSION_MISMATCH, "Invalid SOAP envelope version", getActor());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  989 */         reportFault(fault, state);
/*  990 */         state.setHandlerFlag(-1);
/*  991 */         return false;
/*  992 */       } catch (JAXRPCException jre) {
/*  993 */         state.setHandlerFlag(-1);
/*  994 */         throw jre;
/*  995 */       } catch (RuntimeException rex) {
/*  996 */         state.setHandlerFlag(-1);
/*  997 */         throw rex;
/*      */       } 
/*      */     }
/* 1000 */     return true;
/*      */   }
/*      */   
/*      */   private boolean hasNonEmptyHandlerChain(HandlerChain chain) {
/* 1004 */     return (chain != null && !chain.isEmpty());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void callResponseHandlers(StreamingHandlerState state) throws Exception {
/* 1009 */     HandlerChainImpl handlerChainImpl = getHandlerChain();
/*      */     
/* 1011 */     if (handlerChainImpl != null)
/* 1012 */       handlerChainImpl.handleResponse((MessageContext)state.getMessageContext()); 
/*      */   }
/*      */   
/*      */   protected HandlerChainImpl getHandlerChain() {
/* 1016 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLReaderFactory getXMLReaderFactory(StreamingHandlerState state) {
/* 1023 */     return state.isFastInfoset() ? FastInfosetReaderFactoryImpl.newInstance() : XMLReaderFactory.newInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected XMLWriterFactory getXMLWriterFactory(StreamingHandlerState state) {
/* 1031 */     return (state.isFastInfoset() || state.acceptFastInfoset()) ? FastInfosetWriterFactoryImpl.newInstance() : XMLWriterFactory.newInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void reportFault(SOAPFaultInfo fault, StreamingHandlerState state) {
/* 1043 */     if (state.getRequest().isHeaderNotUnderstood()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1048 */     state.resetResponse();
/* 1049 */     SOAPBlockInfo faultBlock = new SOAPBlockInfo(SOAPConstants.QNAME_SOAP_FAULT);
/*      */     
/* 1051 */     faultBlock.setValue(fault);
/* 1052 */     faultBlock.setSerializer((JAXRPCSerializer)soapFaultInfoSerializer);
/* 1053 */     state.getResponse().setBody(faultBlock);
/* 1054 */     state.getResponse().setFailure(true);
/* 1055 */     state.getMessageContext().setFailure(true);
/*      */   }
/*      */ 
/*      */   
/*      */   boolean isMalformedXML(Exception e) {
/* 1060 */     if (e instanceof JAXRPCExceptionBase) {
/* 1061 */       if (e instanceof com.sun.xml.rpc.encoding.DeserializationException || e instanceof com.sun.xml.rpc.encoding.MissingTrailingBlockIDException)
/*      */       {
/* 1063 */         return true;
/*      */       }
/* 1065 */       Throwable cause = ((JAXRPCExceptionBase)e).getLinkedException();
/* 1066 */       if (cause != null && cause instanceof LocalizableExceptionAdapter) {
/*      */         
/* 1068 */         Throwable ex = ((LocalizableExceptionAdapter)cause).getNestedException();
/*      */         
/* 1070 */         if (ex != null) {
/* 1071 */           if (ex instanceof com.sun.xml.rpc.sp.ParseException)
/* 1072 */             return true; 
/* 1073 */           if (ex instanceof com.sun.xml.rpc.encoding.DeserializationException)
/* 1074 */             return true; 
/*      */         } 
/*      */       } 
/* 1077 */     } else if (e instanceof com.sun.xml.rpc.sp.ParseException) {
/* 1078 */       return true;
/*      */     } 
/*      */     
/* 1081 */     return false;
/*      */   }
/*      */   
/*      */   void setBadRequestProp(StreamingHandlerState state) {
/* 1085 */     state.getMessageContext().setProperty("com.sun.xml.rpc.server.http.ClientBadRequest", "true");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1090 */   private static final SOAPFaultInfoSerializer soapFaultInfoSerializer = new SOAPFaultInfoSerializer(false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1111 */   private static final Logger logger = Logger.getLogger("com.sun.xml.rpc.server");
/*      */   private static final String MUST_UNDERSTAND_FAULT_MESSAGE_STRING = "SOAP must understand error";
/*      */   private static final String NO_BODY_INFO_MESSAGE_STRING = "Missing body information";
/*      */   private static final String BODY_EXPECTED_MESSAGE_STRING = "SOAP body expected";
/*      */   private static final String INVALID_ENVELOPE_CONTENT_MESSAGE_STRING = "Invalid content in SOAP envelope";
/*      */   private static final String INVALID_ENVELOPE_MESSAGE_STRING = "Invalid SOAP envelope";
/*      */   private static final String ENVELOPE_VERSION_MISMATCH_MESSAGE_STRING = "Invalid SOAP envelope version";
/*      */   private static final String ILLEGAL_VALUE_OF_MUST_UNDERSTAND_ATTRIBUTE_FAULT_MESSAGE_STRING = "Illegal value of SOAP mustUnderstand attribute";
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\server\StreamingHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */