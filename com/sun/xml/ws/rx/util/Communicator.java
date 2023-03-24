/*     */ package com.sun.xml.ws.rx.util;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.HeaderList;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.security.trust.WSTrustException;
/*     */ import com.sun.xml.ws.message.RelatesToHeader;
/*     */ import com.sun.xml.ws.security.secconv.SecureConversationInitiator;
/*     */ import com.sun.xml.ws.security.secext10.SecurityTokenReferenceType;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.namespace.QName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Communicator
/*     */ {
/*     */   public static final class Builder
/*     */   {
/*     */     private final String name;
/*     */     private Tube tubelineHead;
/*     */     private SecureConversationInitiator scInitiator;
/*  85 */     private AddressingVersion addressingVersion = AddressingVersion.W3C;
/*  86 */     private SOAPVersion soapVersion = SOAPVersion.SOAP_12;
/*     */     private JAXBRIContext jaxbContext;
/*     */     
/*     */     private Builder(String name) {
/*  90 */       this.name = name;
/*     */     }
/*     */     
/*     */     public Builder addressingVersion(AddressingVersion value) {
/*  94 */       this.addressingVersion = value;
/*     */       
/*  96 */       return this;
/*     */     }
/*     */     
/*     */     public Builder jaxbContext(JAXBRIContext value) {
/* 100 */       this.jaxbContext = value;
/*     */       
/* 102 */       return this;
/*     */     }
/*     */     
/*     */     public Builder secureConversationInitiator(SecureConversationInitiator value) {
/* 106 */       this.scInitiator = value;
/*     */       
/* 108 */       return this;
/*     */     }
/*     */     
/*     */     public Builder soapVersion(SOAPVersion value) {
/* 112 */       this.soapVersion = value;
/*     */       
/* 114 */       return this;
/*     */     }
/*     */     
/*     */     public Builder tubelineHead(Tube value) {
/* 118 */       this.tubelineHead = value;
/*     */       
/* 120 */       return this;
/*     */     }
/*     */     
/*     */     public Communicator build() {
/* 124 */       if (this.tubelineHead == null) {
/* 125 */         throw new IllegalStateException("Cannot create communicator instance: tubeline head has not been set.");
/*     */       }
/* 127 */       if (this.jaxbContext == null) {
/* 128 */         throw new IllegalStateException("Cannot create communicator instance: JAXB context has not been set.");
/*     */       }
/*     */       
/* 131 */       return new Communicator(this.name, this.tubelineHead, this.scInitiator, this.addressingVersion, this.soapVersion, this.jaxbContext);
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder builder(String name) {
/* 136 */     return new Builder(name);
/*     */   }
/*     */   
/* 139 */   private static final Logger LOGGER = Logger.getLogger(Communicator.class);
/*     */ 
/*     */   
/*     */   public final QName soapMustUnderstandAttributeName;
/*     */   
/*     */   private final SecureConversationInitiator scInitiator;
/*     */   
/*     */   private final AddressingVersion addressingVersion;
/*     */   
/*     */   private final SOAPVersion soapVersion;
/*     */   
/*     */   private final JAXBRIContext jaxbContext;
/*     */   
/*     */   private FiberExecutor fiberExecutor;
/*     */   
/*     */   private volatile EndpointAddress destinationAddress;
/*     */ 
/*     */   
/*     */   private Communicator(@NotNull String name, @NotNull Tube tubeline, @Nullable SecureConversationInitiator scInitiator, @NotNull AddressingVersion addressingVersion, @NotNull SOAPVersion soapVersion, @NotNull JAXBRIContext jaxbContext) {
/* 158 */     this.destinationAddress = null;
/* 159 */     this.fiberExecutor = new FiberExecutor(name, tubeline);
/* 160 */     this.scInitiator = scInitiator;
/* 161 */     this.addressingVersion = addressingVersion;
/* 162 */     this.soapVersion = soapVersion;
/* 163 */     this.soapMustUnderstandAttributeName = new QName(soapVersion.nsUri, "mustUnderstand");
/* 164 */     this.jaxbContext = jaxbContext;
/*     */   }
/*     */   
/*     */   public final Packet createRequestPacket(Object jaxbElement, String wsaAction, boolean expectReply) {
/* 168 */     Message message = Messages.create((JAXBContext)this.jaxbContext, jaxbElement, this.soapVersion);
/*     */     
/* 170 */     return createRequestPacket(message, wsaAction, expectReply);
/*     */   }
/*     */   
/*     */   public final Packet createRequestPacket(Message message, String wsaAction, boolean expectReply) {
/* 174 */     if (this.destinationAddress == null) {
/* 175 */       throw new IllegalStateException("Destination address is not defined in this communicator instance");
/*     */     }
/*     */     
/* 178 */     Packet packet = new Packet(message);
/* 179 */     packet.endpointAddress = this.destinationAddress;
/* 180 */     packet.expectReply = Boolean.valueOf(expectReply);
/* 181 */     message.getHeaders().fillRequestAddressingHeaders(packet, this.addressingVersion, this.soapVersion, !expectReply, wsaAction);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 188 */     return packet;
/*     */   }
/*     */   
/*     */   public final Packet createRequestPacket(Packet originalRequestPacket, Object jaxbElement, String wsaAction, boolean expectReply) {
/* 192 */     if (originalRequestPacket != null) {
/* 193 */       Packet request = createResponsePacket(originalRequestPacket, jaxbElement, wsaAction, false);
/*     */       
/* 195 */       HeaderList requestHeaders = request.getMessage().getHeaders();
/* 196 */       if (expectReply) {
/* 197 */         String endpointAddress = originalRequestPacket.getMessage().getHeaders().getTo(this.addressingVersion, this.soapVersion);
/* 198 */         requestHeaders.add(createReplyToHeader(endpointAddress));
/*     */       } 
/* 200 */       requestHeaders.remove(this.addressingVersion.relatesToTag);
/*     */       
/* 202 */       return request;
/*     */     } 
/* 204 */     Message message = Messages.create((JAXBContext)this.jaxbContext, jaxbElement, this.soapVersion);
/* 205 */     return createRequestPacket(message, wsaAction, expectReply);
/*     */   }
/*     */ 
/*     */   
/*     */   private Header createReplyToHeader(String address) {
/* 210 */     WSEndpointReference wsepr = new WSEndpointReference(address, this.addressingVersion);
/* 211 */     return wsepr.createHeader(this.addressingVersion.replyToTag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet createEmptyRequestPacket(boolean expectReply) {
/* 220 */     if (this.destinationAddress == null) {
/* 221 */       throw new IllegalStateException("Destination address is not defined in this communicator instance");
/*     */     }
/*     */     
/* 224 */     Packet packet = new Packet();
/* 225 */     packet.endpointAddress = this.destinationAddress;
/* 226 */     packet.expectReply = Boolean.valueOf(expectReply);
/*     */     
/* 228 */     return packet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet createEmptyRequestPacket(String requestWsaAction, boolean expectReply) {
/* 237 */     return createRequestPacket(Messages.createEmpty(this.soapVersion), requestWsaAction, expectReply);
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
/*     */   public Packet createResponsePacket(@NotNull Packet requestPacket, Object jaxbElement, String responseWsaAction, boolean isClientResponse) {
/* 251 */     assert requestPacket != null : "Request packet must not be 'null' when creating a response";
/* 252 */     if (!isClientResponse) {
/* 253 */       return requestPacket.createServerResponse(Messages.create((JAXBContext)this.jaxbContext, jaxbElement, this.soapVersion), this.addressingVersion, this.soapVersion, responseWsaAction);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     Packet response = createRequestPacket(jaxbElement, responseWsaAction, false);
/* 260 */     response.getMessage().getHeaders().add((Header)new RelatesToHeader(this.addressingVersion.relatesToTag, requestPacket.getMessage().getHeaders().getMessageID(this.addressingVersion, this.soapVersion)));
/*     */ 
/*     */     
/* 263 */     return response;
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
/*     */   public Packet createResponsePacket(Packet requestPacket, Message message, String responseWsaAction) {
/* 277 */     if (requestPacket != null) {
/* 278 */       return requestPacket.createServerResponse(message, this.addressingVersion, this.soapVersion, responseWsaAction);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 284 */     return createRequestPacket(message, responseWsaAction, false);
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
/*     */   public Packet createEmptyResponsePacket(Packet requestPacket, String responseWsaAction) {
/* 298 */     if (requestPacket != null) {
/* 299 */       return requestPacket.createServerResponse(Messages.createEmpty(this.soapVersion), this.addressingVersion, this.soapVersion, responseWsaAction);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 305 */     return createEmptyRequestPacket(responseWsaAction, false);
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
/*     */   public Packet createNullResponsePacket(Packet requestPacket) {
/* 317 */     if (requestPacket.transportBackChannel != null) {
/* 318 */       requestPacket.transportBackChannel.close();
/*     */     }
/*     */     
/* 321 */     Packet emptyReturnPacket = new Packet();
/* 322 */     emptyReturnPacket.invocationProperties.putAll(requestPacket.invocationProperties);
/* 323 */     return emptyReturnPacket;
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
/*     */   public final Packet setEmptyRequestMessage(Packet request, String wsaAction) {
/* 335 */     Message message = Messages.createEmpty(this.soapVersion);
/* 336 */     request.setMessage(message);
/* 337 */     message.getHeaders().fillRequestAddressingHeaders(request, this.addressingVersion, this.soapVersion, false, wsaAction);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 345 */     return request;
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
/*     */   public final Packet setEmptyResponseMessage(Packet response, Packet request, String wsaAction) {
/* 358 */     Message message = Messages.createEmpty(this.soapVersion);
/* 359 */     response.setResponseMessage(request, message, this.addressingVersion, this.soapVersion, wsaAction);
/* 360 */     return response;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWsaAction(Packet packet) {
/* 371 */     if (packet == null || packet.getMessage() == null) {
/* 372 */       return null;
/*     */     }
/*     */     
/* 375 */     return packet.getMessage().getHeaders().getAction(this.addressingVersion, this.soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWsaTo(Packet packet) {
/* 386 */     if (packet == null || packet.getMessage() == null) {
/* 387 */       return null;
/*     */     }
/*     */     
/* 390 */     return packet.getMessage().getHeaders().getTo(this.addressingVersion, this.soapVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityTokenReferenceType tryStartSecureConversation(Packet request) throws WSTrustException {
/* 399 */     if (this.scInitiator == null) {
/* 400 */       return null;
/*     */     }
/*     */     
/* 403 */     Packet emptyPacket = createEmptyRequestPacket(false);
/* 404 */     emptyPacket.invocationProperties.putAll(request.invocationProperties);
/*     */     
/* 406 */     JAXBElement<SecurityTokenReferenceType> strElement = this.scInitiator.startSecureConversation(emptyPacket);
/*     */     
/* 408 */     return (strElement != null) ? strElement.getValue() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet send(@NotNull Packet request) {
/* 419 */     if (this.fiberExecutor == null) {
/* 420 */       LOGGER.fine("Cannot send messages: this Communicator instance has been closed");
/*     */     }
/*     */     
/* 423 */     return this.fiberExecutor.runSync(request);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAsync(@NotNull Packet request, @Nullable Fiber.CompletionCallback completionCallbackHandler) {
/* 434 */     if (this.fiberExecutor == null) {
/* 435 */       LOGGER.fine("Cannot send messages: this Communicator instance has been closed");
/*     */     }
/*     */     
/* 438 */     if (completionCallbackHandler != null) {
/* 439 */       this.fiberExecutor.start(request, completionCallbackHandler);
/*     */     } else {
/* 441 */       this.fiberExecutor.start(request, new Fiber.CompletionCallback()
/*     */           {
/*     */             public void onCompletion(Packet response) {}
/*     */ 
/*     */ 
/*     */             
/*     */             public void onCompletion(Throwable error) {
/* 448 */               Communicator.LOGGER.warning("Unexpected exception occured", error);
/*     */             }
/*     */           });
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
/*     */   @Nullable
/*     */   public EndpointAddress getDestinationAddress() {
/* 464 */     return this.destinationAddress;
/*     */   }
/*     */   
/*     */   public void setDestinationAddress(EndpointAddress newValue) {
/* 468 */     this.destinationAddress = newValue;
/*     */   }
/*     */   
/*     */   public void setDestinationAddressFrom(Packet packet) {
/* 472 */     this.destinationAddress = packet.endpointAddress;
/*     */   }
/*     */   
/*     */   public AddressingVersion getAddressingVersion() {
/* 476 */     return this.addressingVersion;
/*     */   }
/*     */   
/*     */   public SOAPVersion getSoapVersion() {
/* 480 */     return this.soapVersion;
/*     */   }
/*     */   
/*     */   public void close() {
/* 484 */     FiberExecutor fe = this.fiberExecutor;
/* 485 */     if (fe != null) {
/* 486 */       fe.close();
/* 487 */       this.fiberExecutor = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isClosed() {
/* 492 */     return (this.fiberExecutor == null);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\r\\util\Communicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */