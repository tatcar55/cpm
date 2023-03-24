/*     */ package com.sun.xml.ws.rx.mc.runtime;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractFilterTubeImpl;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.mc.dev.ProtocolMessageHandler;
/*     */ import com.sun.xml.ws.rx.mc.dev.WsmcRuntimeProvider;
/*     */ import com.sun.xml.ws.rx.mc.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.util.Communicator;
/*     */ import com.sun.xml.ws.rx.util.SuspendedFiberStorage;
/*     */ import java.util.UUID;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class McClientTube
/*     */   extends AbstractFilterTubeImpl
/*     */   implements WsmcRuntimeProvider
/*     */ {
/*  72 */   private static final Logger LOGGER = Logger.getLogger(McClientTube.class);
/*     */   
/*     */   private final McConfiguration configuration;
/*     */   private final Header wsmcAnnonymousReplyToHeader;
/*     */   private final Header wsmcAnnonymousFaultToHeader;
/*     */   private final Communicator communicator;
/*     */   private final SuspendedFiberStorage suspendedFiberStorage;
/*     */   private final MakeConnectionSenderTask mcSenderTask;
/*     */   private final WSEndpointReference wsmcAnonymousEndpointReference;
/*     */   
/*     */   McClientTube(McConfiguration configuration, Tube tubelineHead) throws RxRuntimeException {
/*  83 */     super(tubelineHead);
/*     */     
/*  85 */     this.configuration = configuration;
/*     */     
/*  87 */     this.communicator = Communicator.builder("mc-client-tube-communicator").tubelineHead(this.next).addressingVersion(configuration.getAddressingVersion()).soapVersion(configuration.getSoapVersion()).jaxbContext(configuration.getRuntimeVersion().getJaxbContext(configuration.getAddressingVersion())).build();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     String wsmcAnonymousAddress = configuration.getRuntimeVersion().getAnonymousAddress(UUID.randomUUID().toString());
/*  95 */     this.wsmcAnonymousEndpointReference = new WSEndpointReference(wsmcAnonymousAddress, configuration.getAddressingVersion());
/*  96 */     this.wsmcAnnonymousReplyToHeader = this.wsmcAnonymousEndpointReference.createHeader((configuration.getAddressingVersion()).replyToTag);
/*  97 */     this.wsmcAnnonymousFaultToHeader = this.wsmcAnonymousEndpointReference.createHeader((configuration.getAddressingVersion()).faultToTag);
/*     */     
/*  99 */     this.suspendedFiberStorage = new SuspendedFiberStorage();
/* 100 */     this.mcSenderTask = new MakeConnectionSenderTask(this.communicator, this.suspendedFiberStorage, wsmcAnonymousAddress, this.wsmcAnnonymousReplyToHeader, this.wsmcAnnonymousFaultToHeader, configuration);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   McClientTube(McClientTube original, TubeCloner cloner) {
/* 110 */     super(original, cloner);
/*     */     
/* 112 */     this.configuration = original.configuration;
/*     */     
/* 114 */     this.wsmcAnnonymousReplyToHeader = original.wsmcAnnonymousReplyToHeader;
/* 115 */     this.wsmcAnnonymousFaultToHeader = original.wsmcAnnonymousFaultToHeader;
/* 116 */     this.communicator = original.communicator;
/* 117 */     this.suspendedFiberStorage = original.suspendedFiberStorage;
/* 118 */     this.mcSenderTask = original.mcSenderTask;
/*     */     
/* 120 */     this.wsmcAnonymousEndpointReference = original.wsmcAnonymousEndpointReference;
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractTubeImpl copy(TubeCloner cloner) {
/* 125 */     LOGGER.entering();
/*     */     try {
/* 127 */       return (AbstractTubeImpl)new McClientTube(this, cloner);
/*     */     } finally {
/* 129 */       LOGGER.exiting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processRequest(Packet request) {
/* 135 */     if (!this.mcSenderTask.isRunning() && !this.mcSenderTask.wasShutdown()) {
/* 136 */       this.communicator.setDestinationAddressFrom(request);
/* 137 */       this.mcSenderTask.start();
/*     */     } 
/*     */     
/* 140 */     Message message = request.getMessage();
/* 141 */     if (!message.hasHeaders()) {
/* 142 */       throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSMC_0102_NO_SOAP_HEADERS()));
/*     */     }
/*     */ 
/*     */     
/* 146 */     if (needToSetWsmcAnnonymousHeaders(request)) {
/* 147 */       Fiber.CompletionCallback responseHandler; setMcAnnonymousHeaders(message.getHeaders(), this.configuration.getAddressingVersion(), this.wsmcAnnonymousReplyToHeader, this.wsmcAnnonymousFaultToHeader);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 153 */       String correlationId = message.getID(this.configuration.getAddressingVersion(), this.configuration.getSoapVersion());
/*     */       
/* 155 */       if (request.expectReply != null && request.expectReply.booleanValue()) {
/* 156 */         responseHandler = new RequestResponseMepHandler(this.configuration, this.mcSenderTask, this.suspendedFiberStorage, correlationId);
/*     */       } else {
/* 158 */         responseHandler = new OneWayMepHandler(this.configuration, this.mcSenderTask, this.suspendedFiberStorage, correlationId);
/*     */       } 
/*     */       
/* 161 */       this.suspendedFiberStorage.register(correlationId, Fiber.current());
/* 162 */       this.communicator.sendAsync(request, responseHandler);
/*     */       
/* 164 */       return doSuspend();
/*     */     } 
/* 166 */     return super.processRequest(request);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NextAction processResponse(Packet response) {
/* 172 */     return super.processResponse(response);
/*     */   }
/*     */ 
/*     */   
/*     */   public NextAction processException(Throwable t) {
/* 177 */     return super.processException(t);
/*     */   }
/*     */ 
/*     */   
/*     */   public void preDestroy() {
/* 182 */     this.mcSenderTask.shutdown();
/* 183 */     this.communicator.close();
/*     */     
/* 185 */     super.preDestroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final WSEndpointReference getWsmcAnonymousEndpointReference() {
/* 192 */     return this.wsmcAnonymousEndpointReference;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void registerProtocolMessageHandler(ProtocolMessageHandler handler) {
/* 199 */     this.mcSenderTask.register(handler);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean needToSetWsmcAnnonymousHeaders(Packet request) {
/* 207 */     Header replyToHeader = request.getMessage().getHeaders().get((this.configuration.getAddressingVersion()).replyToTag, false);
/* 208 */     if (replyToHeader != null) {
/*     */       try {
/* 210 */         return replyToHeader.readAsEPR(this.configuration.getAddressingVersion()).isAnonymous();
/* 211 */       } catch (XMLStreamException ex) {
/* 212 */         throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSMC_0103_ERROR_RETRIEVING_WSA_REPLYTO_CONTENT(), ex));
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 218 */     return isBooleanFlagSet(request, McConfiguration.ACK_REQUESTED_HEADER_SET).booleanValue();
/*     */   }
/*     */   
/*     */   static void setMcAnnonymousHeaders(HeaderList headers, AddressingVersion av, Header wsmcReplyToHeader, Header wsmcFaultToHeader) {
/* 222 */     headers.remove(av.replyToTag);
/* 223 */     headers.add(wsmcReplyToHeader);
/*     */     
/* 225 */     if (headers.remove(av.faultToTag) != null) {
/* 226 */       headers.add(wsmcFaultToHeader);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private Boolean isBooleanFlagSet(Packet packet, String flag) {
/* 232 */     Boolean value = Boolean.class.cast(packet.invocationProperties.get(flag));
/* 233 */     return Boolean.valueOf((value != null && value.booleanValue()));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\mc\runtime\McClientTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */