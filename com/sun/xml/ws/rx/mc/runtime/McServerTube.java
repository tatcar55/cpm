/*     */ package com.sun.xml.ws.rx.mc.runtime;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.PropertySet;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.ha.HighAvailabilityProvider;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Headers;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.commons.ha.HaContext;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.mc.dev.AdditionalResponses;
/*     */ import com.sun.xml.ws.rx.mc.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.mc.protocol.wsmc200702.MakeConnectionElement;
/*     */ import com.sun.xml.ws.rx.mc.protocol.wsmc200702.MessagePendingElement;
/*     */ import com.sun.xml.ws.rx.message.jaxws.JaxwsMessage;
/*     */ import com.sun.xml.ws.rx.util.Communicator;
/*     */ import com.sun.xml.ws.rx.util.FiberExecutor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ import javax.xml.soap.SOAPException;
/*     */ import javax.xml.soap.SOAPFault;
/*     */ import javax.xml.stream.XMLStreamException;
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
/*     */ public class McServerTube
/*     */   extends AbstractFilterTubeImpl
/*     */ {
/*     */   private static final class AppRequestProcessingCallback
/*     */     implements Fiber.CompletionCallback
/*     */   {
/*  86 */     private static final Logger LOGGER = Logger.getLogger(AppRequestProcessingCallback.class);
/*     */     private final ResponseStorage responseStorage;
/*     */     private final String clientUID;
/*     */     private final McConfiguration configuration;
/*     */     
/*     */     public AppRequestProcessingCallback(@NotNull ResponseStorage responseStorage, @NotNull String clientUID, @NotNull McConfiguration configuration) {
/*  92 */       this.responseStorage = responseStorage;
/*  93 */       this.clientUID = clientUID;
/*  94 */       this.configuration = configuration;
/*     */     }
/*     */     
/*     */     public void onCompletion(Packet response) {
/*     */       try {
/*  99 */         LOGGER.finer(LocalizationMessages.WSMC_0105_STORING_RESPONSE(this.clientUID));
/* 100 */         HaContext.initFrom(response);
/*     */         
/* 102 */         storeResponse(response);
/* 103 */         AdditionalResponses additionalResponses = (AdditionalResponses)response.getSatellite(AdditionalResponses.class);
/*     */         
/* 105 */         if (additionalResponses != null) {
/* 106 */           for (Packet additionalResponse : additionalResponses.getAdditionalResponsePacketQueue()) {
/* 107 */             storeResponse(additionalResponse);
/*     */           }
/*     */         } else {
/* 110 */           LOGGER.fine("Response packet did not contain any AdditionalResponses property set.");
/*     */         } 
/*     */       } finally {
/* 113 */         HaContext.clear();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void onCompletion(Throwable error) {
/* 118 */       LOGGER.severe(LocalizationMessages.WSMC_0106_EXCEPTION_IN_REQUEST_PROCESSING(this.clientUID), error);
/*     */     }
/*     */     
/*     */     private void storeResponse(Packet response) {
/* 122 */       if (response.getMessage() != null) {
/* 123 */         HeaderList headers = response.getMessage().getHeaders();
/* 124 */         headers.remove((this.configuration.getAddressingVersion()).toTag);
/* 125 */         headers.add(Headers.create((this.configuration.getAddressingVersion()).toTag, this.configuration.getRuntimeVersion().getAnonymousAddress(this.clientUID)));
/*     */         
/* 127 */         JaxwsMessage responseMessage = new JaxwsMessage(response, headers.getMessageID(this.configuration.getAddressingVersion(), this.configuration.getSoapVersion()));
/* 128 */         this.responseStorage.store(responseMessage, this.clientUID);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/* 133 */   private static final Logger LOGGER = Logger.getLogger(McServerTube.class);
/*     */   
/*     */   private final McConfiguration configuration;
/*     */   private final FiberExecutor fiberExecutor;
/*     */   private final ResponseStorage responseStorage;
/*     */   private final Communicator communicator;
/*     */   
/*     */   McServerTube(McConfiguration configuration, Tube tubelineHead) {
/* 141 */     super(tubelineHead);
/*     */     
/* 143 */     this.configuration = configuration;
/* 144 */     this.fiberExecutor = new FiberExecutor("McServerTubeCommunicator", tubelineHead);
/* 145 */     this.responseStorage = new ResponseStorage(configuration.getUniqueEndpointId());
/* 146 */     this.communicator = Communicator.builder("mc-server-tube-communincator").soapVersion(configuration.getSoapVersion()).addressingVersion(configuration.getAddressingVersion()).tubelineHead(this.next).jaxbContext(configuration.getRuntimeVersion().getJaxbContext(configuration.getAddressingVersion())).build();
/*     */   }
/*     */   
/*     */   McServerTube(McServerTube original, TubeCloner cloner) {
/* 150 */     super(original, cloner);
/*     */     
/* 152 */     this.configuration = original.configuration;
/* 153 */     this.fiberExecutor = original.fiberExecutor;
/* 154 */     this.responseStorage = original.responseStorage;
/* 155 */     this.communicator = original.communicator;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 160 */     LOGGER.entering();
/*     */     try {
/* 162 */       return (AbstractTubeImpl)new McServerTube(this, cloner);
/*     */     } finally {
/* 164 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/*     */     try {
/* 172 */       LOGGER.entering();
/* 173 */       HaContext.initFrom(request);
/* 174 */       if (HaContext.failoverDetected()) {
/* 175 */         this.responseStorage.invalidateLocalCache();
/*     */       }
/*     */       
/* 178 */       assert request.getMessage() != null : "Unexpected [null] message in the server-side Tube.processRequest()";
/*     */       
/* 180 */       String clientUID = getClientUID(request);
/* 181 */       if (isMakeConnectionRequest(request)) {
/* 182 */         return handleMakeConnectionRequest(request, clientUID);
/*     */       }
/*     */       
/* 185 */       if (clientUID == null)
/*     */       {
/* 187 */         return super.processRequest(request);
/*     */       }
/*     */ 
/*     */       
/* 191 */       request.getMessage().getHeaders().remove((this.configuration.getAddressingVersion()).replyToTag);
/* 192 */       request.getMessage().getHeaders().remove((this.configuration.getAddressingVersion()).faultToTag);
/*     */ 
/*     */       
/* 195 */       Packet requestCopy = request.copy(true);
/*     */       
/* 197 */       request.addSatellite((PropertySet)new AdditionalResponses());
/* 198 */       this.fiberExecutor.start(request, new AppRequestProcessingCallback(this.responseStorage, clientUID, this.configuration));
/*     */       
/* 200 */       return doReturnWith(createEmptyResponse(requestCopy));
/*     */     } finally {
/* 202 */       HaContext.clear();
/* 203 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/*     */     try {
/* 210 */       LOGGER.entering();
/*     */ 
/*     */       
/* 213 */       return super.processResponse(response);
/*     */     } finally {
/* 215 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */   private NextAction handleMakeConnectionRequest(Packet request, String clientUID) {
/*     */     try {
/*     */       MakeConnectionElement mcElement;
/* 221 */       LOGGER.entering();
/*     */ 
/*     */       
/*     */       try {
/* 225 */         mcElement = (MakeConnectionElement)request.getMessage().readPayloadAsJAXB(this.configuration.getRuntimeVersion().getUnmarshaller(this.configuration.getAddressingVersion()));
/* 226 */       } catch (JAXBException ex) {
/* 227 */         throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSMC_0107_ERROR_UNMARSHALLING_PROTOCOL_MESSAGE(), ex));
/*     */       } 
/*     */       
/* 230 */       if (mcElement.getAddress() == null)
/*     */       {
/*     */         
/* 233 */         return doReturnWith(createSoapFaultResponse(request, this.configuration.getSoapVersion(), this.configuration.getAddressingVersion(), (this.configuration.getRuntimeVersion()).protocolVersion.wsmcFaultAction, (this.configuration.getSoapVersion()).faultCodeServer, (this.configuration.getRuntimeVersion()).protocolVersion.missingSelectionFaultCode, "The MakeConnection element did not contain any selection criteria.", null));
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 244 */       if (!mcElement.getAny().isEmpty()) {
/*     */ 
/*     */         
/* 247 */         List<SoapFaultDetailEntry> unsupportedSelections = new ArrayList<SoapFaultDetailEntry>(mcElement.getAny().size());
/* 248 */         for (Object element : mcElement.getAny()) {
/* 249 */           if (element instanceof Node) {
/* 250 */             Node selectionNode = (Node)element;
/* 251 */             unsupportedSelections.add(new SoapFaultDetailEntry((this.configuration.getRuntimeVersion()).protocolVersion.unsupportedSelectionFaultCode, (new QName(selectionNode.getNamespaceURI(), selectionNode.getLocalName())).toString()));
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 257 */         return doReturnWith(createSoapFaultResponse(request, this.configuration.getSoapVersion(), this.configuration.getAddressingVersion(), (this.configuration.getRuntimeVersion()).protocolVersion.wsmcFaultAction, (this.configuration.getSoapVersion()).faultCodeServer, (this.configuration.getRuntimeVersion()).protocolVersion.unsupportedSelectionFaultCode, "The extension element used in the message selection is not supported by the MakeConnection receiver.", unsupportedSelections));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 268 */       String selectionUID = this.configuration.getRuntimeVersion().getClientId(mcElement.getAddress().getValue());
/*     */       
/* 270 */       if (selectionUID == null)
/*     */       {
/* 272 */         throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSMC_0108_NULL_SELECTION_ADDRESS()));
/*     */       }
/*     */       
/* 275 */       if (clientUID != null && !selectionUID.equals(clientUID))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 292 */         LOGGER.warning(LocalizationMessages.WSMC_0109_SELECTION_ADDRESS_NOT_MATCHING_WSA_REPLYTO(selectionUID, clientUID));
/*     */       }
/*     */       
/* 295 */       Packet response = null;
/*     */       
/* 297 */       JaxwsMessage pendingMessage = (selectionUID != null) ? this.responseStorage.getPendingResponse(selectionUID) : null;
/* 298 */       if (pendingMessage != null) {
/* 299 */         LOGGER.finer(LocalizationMessages.WSMC_0110_PENDING_MESSAGE_FOUND_FOR_SELECTION_UUID(selectionUID));
/*     */         
/* 301 */         if (HighAvailabilityProvider.INSTANCE.isHaEnvironmentConfigured() && 
/* 302 */           pendingMessage.getPacket() == null)
/*     */         {
/*     */           
/* 305 */           pendingMessage.setPacket(this.communicator.createEmptyResponsePacket(request, pendingMessage.getWsaAction()));
/*     */         }
/*     */         
/* 308 */         response = pendingMessage.getPacket();
/*     */       } 
/*     */       
/* 311 */       if (response == null) {
/* 312 */         LOGGER.finer(LocalizationMessages.WSMC_0111_NO_PENDING_MESSAGE_FOUND_FOR_SELECTION_UUID(selectionUID));
/* 313 */         response = createEmptyResponse(request);
/*     */       } else {
/* 315 */         Message message = response.getMessage();
/* 316 */         if (message != null) {
/* 317 */           HeaderList headers = message.getHeaders();
/* 318 */           headers.add(Headers.create((JAXBContext)this.configuration.getRuntimeVersion().getJaxbContext(this.configuration.getAddressingVersion()), new MessagePendingElement(Boolean.valueOf((selectionUID != null && this.responseStorage.hasPendingResponse(selectionUID))))));
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 324 */       return doReturnWith(response);
/*     */     } finally {
/* 326 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processException(Throwable t) {
/*     */     try {
/* 333 */       LOGGER.entering();
/*     */       
/* 335 */       return super.processException(t);
/*     */     } finally {
/* 337 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void preDestroy() {
/* 343 */     this.responseStorage.dispose();
/*     */     
/* 345 */     super.preDestroy();
/*     */   }
/*     */   
/*     */   private String getClientUID(Packet request) {
/* 349 */     Header replyToHeader = request.getMessage().getHeaders().get((this.configuration.getAddressingVersion()).replyToTag, false);
/* 350 */     if (replyToHeader != null) {
/*     */       try {
/* 352 */         String replyToAddress = replyToHeader.readAsEPR(this.configuration.getAddressingVersion()).getAddress();
/* 353 */         return this.configuration.getRuntimeVersion().getClientId(replyToAddress);
/* 354 */       } catch (XMLStreamException ex) {
/* 355 */         throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSMC_0103_ERROR_RETRIEVING_WSA_REPLYTO_CONTENT(), ex));
/*     */       } 
/*     */     }
/*     */     
/* 359 */     return null;
/*     */   }
/*     */   
/*     */   private boolean isMakeConnectionRequest(Packet request) {
/* 363 */     return (this.configuration.getRuntimeVersion()).protocolVersion.wsmcAction.equals(request.getMessage().getHeaders().getAction(this.configuration.getAddressingVersion(), this.configuration.getSoapVersion()));
/*     */   }
/*     */   
/*     */   private Packet createEmptyResponse(Packet request) {
/* 367 */     return request.createServerResponse(null, null, null, "");
/*     */   }
/*     */   private Packet createSoapFaultResponse(Packet request, SOAPVersion soapVersion, AddressingVersion av, String action, QName code, QName subcode, String faultReasonText, List<SoapFaultDetailEntry> detailEntries) {
/*     */     try {
/*     */       Message soapFaultMessage;
/* 372 */       SOAPFault soapFault = soapVersion.saajSoapFactory.createFault();
/*     */ 
/*     */       
/* 375 */       if (faultReasonText != null) {
/* 376 */         soapFault.setFaultString(faultReasonText, Locale.ENGLISH);
/*     */       }
/*     */ 
/*     */       
/* 380 */       switch (soapVersion) {
/*     */         case SOAP_11:
/* 382 */           soapFault.setFaultCode(subcode);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 399 */           soapFaultMessage = Messages.create(soapFault);
/*     */           
/* 401 */           return request.createServerResponse(soapFaultMessage, av, soapVersion, action);case SOAP_12: soapFault.setFaultCode(code); soapFault.appendFaultSubcode(subcode); if (detailEntries != null && !detailEntries.isEmpty()) { Detail detail = soapFault.addDetail(); for (SoapFaultDetailEntry entry : detailEntries) detail.addDetailEntry(entry.name).setValue(entry.value);  }  soapFaultMessage = Messages.create(soapFault); return request.createServerResponse(soapFaultMessage, av, soapVersion, action);
/*     */       }  throw new RxRuntimeException("Unsupported SOAP version: '" + soapVersion.toString() + "'");
/* 403 */     } catch (SOAPException ex) {
/* 404 */       throw new RxRuntimeException("Error creating a SOAP fault", ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\runtime\McServerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */