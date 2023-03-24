/*     */ package com.sun.xml.ws.addressing;
/*     */ 
/*     */ import com.oracle.webservices.api.message.PropertySet;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.addressing.model.ActionNotSupportedException;
/*     */ import com.sun.xml.ws.addressing.model.InvalidAddressingHeaderException;
/*     */ import com.sun.xml.ws.api.EndpointAddress;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.NonAnonymousResponseProcessor;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.MessageHeaders;
/*     */ import com.sun.xml.ws.api.message.Messages;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Fiber;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.ThrowableContainerPropertySet;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.client.Stub;
/*     */ import com.sun.xml.ws.message.FaultDetailHeader;
/*     */ import com.sun.xml.ws.resources.AddressingMessages;
/*     */ import java.net.URI;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.soap.SOAPFault;
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
/*     */ public class WsaServerTube
/*     */   extends WsaTube
/*     */ {
/*     */   private WSEndpoint endpoint;
/*     */   private WSEndpointReference replyTo;
/*     */   private WSEndpointReference faultTo;
/*     */   private boolean isAnonymousRequired = false;
/*     */   protected boolean isEarlyBackchannelCloseAllowed = true;
/*     */   private WSDLBoundOperation wbo;
/*     */   public static final String REQUEST_MESSAGE_ID = "com.sun.xml.ws.addressing.request.messageID";
/*     */   
/*     */   public WsaServerTube(WSEndpoint endpoint, @NotNull WSDLPort wsdlPort, WSBinding binding, Tube next) {
/*  99 */     super(wsdlPort, binding, next);
/* 100 */     this.endpoint = endpoint;
/*     */   }
/*     */   
/*     */   public WsaServerTube(WsaServerTube that, TubeCloner cloner) {
/* 104 */     super(that, cloner);
/* 105 */     this.endpoint = that.endpoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public WsaServerTube copy(TubeCloner cloner) {
/* 110 */     return new WsaServerTube(this, cloner);
/*     */   }
/*     */   @NotNull
/*     */   public NextAction processRequest(Packet request) {
/*     */     String msgId;
/* 115 */     Message msg = request.getMessage();
/* 116 */     if (msg == null) {
/* 117 */       return doInvoke(this.next, request);
/*     */     }
/*     */ 
/*     */     
/* 121 */     request.addSatellite((PropertySet)new WsaPropertyBag(this.addressingVersion, this.soapVersion, request));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     MessageHeaders hl = request.getMessage().getMessageHeaders();
/*     */     
/*     */     try {
/* 130 */       this.replyTo = AddressingUtils.getReplyTo(hl, this.addressingVersion, this.soapVersion);
/* 131 */       this.faultTo = AddressingUtils.getFaultTo(hl, this.addressingVersion, this.soapVersion);
/* 132 */       msgId = AddressingUtils.getMessageID(hl, this.addressingVersion, this.soapVersion);
/* 133 */     } catch (InvalidAddressingHeaderException e) {
/*     */       
/* 135 */       LOGGER.log(Level.WARNING, this.addressingVersion.getInvalidMapText() + ", Problem header:" + e.getProblemHeader() + ", Reason: " + e.getSubsubcode(), (Throwable)e);
/*     */ 
/*     */       
/* 138 */       hl.remove(e.getProblemHeader());
/*     */       
/* 140 */       SOAPFault soapFault = this.helper.createInvalidAddressingHeaderFault(e, this.addressingVersion);
/*     */       
/* 142 */       if (this.wsdlPort != null && request.getMessage().isOneWay(this.wsdlPort)) {
/* 143 */         Packet packet = request.createServerResponse(null, this.wsdlPort, null, this.binding);
/* 144 */         return doReturnWith(packet);
/*     */       } 
/*     */       
/* 147 */       Message m = Messages.create(soapFault);
/* 148 */       if (this.soapVersion == SOAPVersion.SOAP_11) {
/* 149 */         FaultDetailHeader s11FaultDetailHeader = new FaultDetailHeader(this.addressingVersion, this.addressingVersion.problemHeaderQNameTag.getLocalPart(), e.getProblemHeader());
/* 150 */         m.getMessageHeaders().add((Header)s11FaultDetailHeader);
/*     */       } 
/*     */       
/* 153 */       Packet response = request.createServerResponse(m, this.wsdlPort, null, this.binding);
/* 154 */       return doReturnWith(response);
/*     */     } 
/*     */ 
/*     */     
/* 158 */     if (this.replyTo == null) {
/* 159 */       this.replyTo = this.addressingVersion.anonymousEpr;
/*     */     }
/* 161 */     if (this.faultTo == null) {
/* 162 */       this.faultTo = this.replyTo;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     request.put("com.sun.xml.ws.addressing.WsaPropertyBag.ReplyToFromRequest", this.replyTo);
/* 169 */     request.put("com.sun.xml.ws.addressing.WsaPropertyBag.FaultToFromRequest", this.faultTo);
/* 170 */     request.put("com.sun.xml.ws.addressing.WsaPropertyBag.MessageIdFromRequest", msgId);
/*     */     
/* 172 */     this.wbo = getWSDLBoundOperation(request);
/* 173 */     this.isAnonymousRequired = isAnonymousRequired(this.wbo);
/*     */     
/* 175 */     Packet p = validateInboundHeaders(request);
/*     */ 
/*     */     
/* 178 */     if (p.getMessage() == null) {
/* 179 */       return doReturnWith(p);
/*     */     }
/*     */ 
/*     */     
/* 183 */     if (p.getMessage().isFault()) {
/*     */ 
/*     */       
/* 186 */       if (this.isEarlyBackchannelCloseAllowed && !this.isAnonymousRequired && !this.faultTo.isAnonymous() && request.transportBackChannel != null)
/*     */       {
/*     */         
/* 189 */         request.transportBackChannel.close();
/*     */       }
/* 191 */       return processResponse(p);
/*     */     } 
/*     */ 
/*     */     
/* 195 */     if (this.isEarlyBackchannelCloseAllowed && !this.isAnonymousRequired && !this.replyTo.isAnonymous() && !this.faultTo.isAnonymous() && request.transportBackChannel != null)
/*     */     {
/*     */ 
/*     */       
/* 199 */       request.transportBackChannel.close();
/*     */     }
/* 201 */     return doInvoke(this.next, p);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isAnonymousRequired(@Nullable WSDLBoundOperation wbo) {
/* 206 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkAnonymousSemantics(WSDLBoundOperation wbo, WSEndpointReference replyTo, WSEndpointReference faultTo) {}
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public NextAction processException(Throwable t) {
/* 215 */     Packet response = Fiber.current().getPacket();
/* 216 */     ThrowableContainerPropertySet tc = (ThrowableContainerPropertySet)response.getSatellite(ThrowableContainerPropertySet.class);
/* 217 */     if (tc == null) {
/* 218 */       tc = new ThrowableContainerPropertySet(t);
/* 219 */       response.addSatellite((PropertySet)tc);
/* 220 */     } else if (t != tc.getThrowable()) {
/*     */ 
/*     */       
/* 223 */       tc.setThrowable(t);
/*     */     } 
/* 225 */     return processResponse(response.endpoint.createServiceResponseForException(tc, response, this.soapVersion, this.wsdlPort, response.endpoint.getSEIModel(), this.binding));
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public NextAction processResponse(Packet response) {
/*     */     EndpointAddress adrs;
/* 232 */     Message msg = response.getMessage();
/* 233 */     if (msg == null) {
/* 234 */       return doReturnWith(response);
/*     */     }
/*     */     
/* 237 */     String to = AddressingUtils.getTo(msg.getMessageHeaders(), this.addressingVersion, this.soapVersion);
/*     */     
/* 239 */     if (to != null) {
/* 240 */       this.replyTo = this.faultTo = new WSEndpointReference(to, this.addressingVersion);
/*     */     }
/*     */     
/* 243 */     if (this.replyTo == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 249 */       this.replyTo = (WSEndpointReference)response.get("com.sun.xml.ws.addressing.WsaPropertyBag.ReplyToFromRequest");
/*     */     }
/*     */ 
/*     */     
/* 253 */     if (this.faultTo == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 259 */       this.faultTo = (WSEndpointReference)response.get("com.sun.xml.ws.addressing.WsaPropertyBag.FaultToFromRequest");
/*     */     }
/*     */ 
/*     */     
/* 263 */     WSEndpointReference target = msg.isFault() ? this.faultTo : this.replyTo;
/* 264 */     if (target == null && response.proxy instanceof Stub) {
/* 265 */       target = ((Stub)response.proxy).getWSEndpointReference();
/*     */     }
/* 267 */     if (target == null || target.isAnonymous() || this.isAnonymousRequired) {
/* 268 */       return doReturnWith(response);
/*     */     }
/* 270 */     if (target.isNone()) {
/*     */       
/* 272 */       response.setMessage(null);
/* 273 */       return doReturnWith(response);
/*     */     } 
/*     */     
/* 276 */     if (this.wsdlPort != null && response.getMessage().isOneWay(this.wsdlPort)) {
/*     */       
/* 278 */       LOGGER.fine(AddressingMessages.NON_ANONYMOUS_RESPONSE_ONEWAY());
/* 279 */       return doReturnWith(response);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 286 */     if (this.wbo != null || response.soapAction == null) {
/* 287 */       String action = response.getMessage().isFault() ? this.helper.getFaultAction(this.wbo, response) : this.helper.getOutputAction(this.wbo);
/*     */ 
/*     */ 
/*     */       
/* 291 */       if (response.soapAction == null || (action != null && !action.equals("http://jax-ws.dev.java.net/addressing/output-action-not-set")))
/*     */       {
/*     */         
/* 294 */         response.soapAction = action;
/*     */       }
/*     */     } 
/* 297 */     response.expectReply = Boolean.valueOf(false);
/*     */ 
/*     */     
/*     */     try {
/* 301 */       adrs = new EndpointAddress(URI.create(target.getAddress()));
/* 302 */     } catch (NullPointerException e) {
/* 303 */       throw new WebServiceException(e);
/* 304 */     } catch (IllegalArgumentException e) {
/* 305 */       throw new WebServiceException(e);
/*     */     } 
/*     */     
/* 308 */     response.endpointAddress = adrs;
/*     */     
/* 310 */     if (response.isAdapterDeliversNonAnonymousResponse) {
/* 311 */       return doReturnWith(response);
/*     */     }
/*     */     
/* 314 */     return doReturnWith(NonAnonymousResponseProcessor.getDefault().process(response));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validateAction(Packet packet) {
/* 321 */     WSDLBoundOperation wsdlBoundOperation = getWSDLBoundOperation(packet);
/*     */     
/* 323 */     if (wsdlBoundOperation == null) {
/*     */       return;
/*     */     }
/*     */     
/* 327 */     String gotA = AddressingUtils.getAction(packet.getMessage().getMessageHeaders(), this.addressingVersion, this.soapVersion);
/*     */ 
/*     */ 
/*     */     
/* 331 */     if (gotA == null) {
/* 332 */       throw new WebServiceException(AddressingMessages.VALIDATION_SERVER_NULL_ACTION());
/*     */     }
/*     */     
/* 335 */     String expected = this.helper.getInputAction(packet);
/* 336 */     String soapAction = this.helper.getSOAPAction(packet);
/* 337 */     if (this.helper.isInputActionDefault(packet) && soapAction != null && !soapAction.equals("")) {
/* 338 */       expected = soapAction;
/*     */     }
/*     */     
/* 341 */     if (expected != null && !gotA.equals(expected)) {
/* 342 */       throw new ActionNotSupportedException(gotA);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkMessageAddressingProperties(Packet packet) {
/* 348 */     super.checkMessageAddressingProperties(packet);
/*     */ 
/*     */     
/* 351 */     WSDLBoundOperation wsdlBoundOperation = getWSDLBoundOperation(packet);
/* 352 */     checkAnonymousSemantics(wsdlBoundOperation, this.replyTo, this.faultTo);
/*     */     
/* 354 */     checkNonAnonymousAddresses(this.replyTo, this.faultTo);
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkNonAnonymousAddresses(WSEndpointReference replyTo, WSEndpointReference faultTo) {
/* 359 */     if (!replyTo.isAnonymous()) {
/*     */       try {
/* 361 */         new EndpointAddress(URI.create(replyTo.getAddress()));
/* 362 */       } catch (Exception e) {
/* 363 */         throw new InvalidAddressingHeaderException(this.addressingVersion.replyToTag, this.addressingVersion.invalidAddressTag);
/*     */       } 
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
/* 385 */   private static final Logger LOGGER = Logger.getLogger(WsaServerTube.class.getName());
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\WsaServerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */