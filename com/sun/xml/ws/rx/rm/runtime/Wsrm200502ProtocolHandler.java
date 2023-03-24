/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.Headers;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.message.RxMessage;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.rm.protocol.AcknowledgementData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CloseSequenceData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CloseSequenceResponseData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CreateSequenceData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CreateSequenceResponseData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.TerminateSequenceData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.TerminateSequenceResponseData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.AckRequestedElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.CreateSequenceElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.CreateSequenceResponseElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.SequenceAcknowledgementElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.SequenceElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.SequenceFaultElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.TerminateSequenceElement;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.DuplicateMessageRegistrationException;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.Sequence;
/*     */ import com.sun.xml.ws.rx.rm.runtime.sequence.UnknownSequenceException;
/*     */ import com.sun.xml.ws.rx.util.Communicator;
/*     */ import java.math.BigInteger;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.soap.Detail;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Wsrm200502ProtocolHandler
/*     */   extends WsrmProtocolHandler
/*     */ {
/*  84 */   private static final Logger LOGGER = Logger.getLogger(Wsrm200502ProtocolHandler.class);
/*     */   private final RuntimeContext rc;
/*     */   
/*     */   Wsrm200502ProtocolHandler(RmConfiguration configuration, RuntimeContext rc, Communicator communicator) {
/*  88 */     super(RmRuntimeVersion.WSRM200502, configuration, communicator);
/*     */     
/*  90 */     assert rc != null;
/*     */     
/*  92 */     this.rc = rc;
/*     */   }
/*     */   
/*     */   public CreateSequenceData toCreateSequenceData(@NotNull Packet packet) throws RxRuntimeException {
/*  96 */     assert packet != null;
/*  97 */     assert packet.getMessage() != null;
/*  98 */     assert !packet.getMessage().isFault();
/*     */     
/* 100 */     Message message = packet.getMessage();
/* 101 */     CreateSequenceElement csElement = unmarshallMessage(message);
/*     */ 
/*     */ 
/*     */     
/* 105 */     return csElement.toDataBuilder().build();
/*     */   }
/*     */   
/*     */   public Packet toPacket(CreateSequenceData data, @Nullable Packet requestPacket) throws RxRuntimeException {
/* 109 */     Packet packet = this.communicator.createRequestPacket(requestPacket, new CreateSequenceElement(data), this.rmVersion.protocolVersion.createSequenceAction, true);
/*     */     
/* 111 */     return packet;
/*     */   }
/*     */   
/*     */   public CreateSequenceResponseData toCreateSequenceResponseData(Packet packet) throws RxRuntimeException {
/* 115 */     assert packet != null;
/* 116 */     assert packet.getMessage() != null;
/* 117 */     assert !packet.getMessage().isFault();
/*     */     
/* 119 */     Message message = packet.getMessage();
/* 120 */     CreateSequenceResponseElement csrElement = unmarshallMessage(message);
/*     */     
/* 122 */     return csrElement.toDataBuilder().build();
/*     */   }
/*     */   
/*     */   public Packet toPacket(CreateSequenceResponseData data, @NotNull Packet requestPacket, boolean clientSideResponse) throws RxRuntimeException {
/* 126 */     return this.communicator.createResponsePacket(requestPacket, new CreateSequenceResponseElement(data), this.rmVersion.protocolVersion.createSequenceResponseAction, clientSideResponse);
/*     */   }
/*     */   
/*     */   public CloseSequenceData toCloseSequenceData(Packet packet) throws RxRuntimeException {
/* 130 */     assert packet != null;
/* 131 */     assert packet.getMessage() != null;
/* 132 */     assert !packet.getMessage().isFault();
/*     */     
/* 134 */     Message message = packet.getMessage();
/*     */     
/*     */     try {
/* 137 */       ApplicationMessage lastAppMessage = new ApplicationMessageBase("")
/*     */         {
/*     */           public RxMessage.State getState() {
/* 140 */             throw new UnsupportedOperationException("Not supported yet.");
/*     */           }
/*     */         };
/* 143 */       loadSequenceHeaderData(lastAppMessage, message);
/* 144 */       loadAcknowledgementData(lastAppMessage, message);
/*     */ 
/*     */       
/* 147 */       Sequence inboundSequence = this.rc.sequenceManager().getInboundSequence(lastAppMessage.getSequenceId());
/*     */       try {
/* 149 */         inboundSequence.registerMessage(lastAppMessage, false);
/* 150 */       } catch (Exception ex) {
/* 151 */         throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSRM_1146_UNEXPECTED_ERROR_WHILE_REGISTERING_MESSAGE(), ex));
/*     */       } 
/* 153 */       inboundSequence.acknowledgeMessageNumber(lastAppMessage.getMessageNumber());
/* 154 */       inboundSequence.setAckRequestedFlag();
/*     */       
/* 156 */       CloseSequenceData.Builder dataBuilder = CloseSequenceData.getBuilder(lastAppMessage.getSequenceId(), lastAppMessage.getMessageNumber());
/* 157 */       dataBuilder.acknowledgementData(lastAppMessage.getAcknowledgementData());
/* 158 */       return dataBuilder.build();
/*     */     } finally {
/* 160 */       message.consume();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Packet toPacket(CloseSequenceData data, @Nullable Packet requestPacket) throws RxRuntimeException {
/*     */     Packet packet;
/* 166 */     if (requestPacket != null) {
/* 167 */       packet = this.communicator.createEmptyResponsePacket(requestPacket, this.rmVersion.protocolVersion.closeSequenceAction);
/*     */     } else {
/* 169 */       packet = this.communicator.createEmptyRequestPacket(this.rmVersion.protocolVersion.closeSequenceAction, false);
/*     */     } 
/* 171 */     Message message = packet.getMessage();
/*     */     
/* 173 */     ApplicationMessage lastAppMessage = new ApplicationMessageBase("")
/*     */       {
/*     */         public RxMessage.State getState() {
/* 176 */           throw new UnsupportedOperationException("Not supported yet.");
/*     */         }
/*     */       };
/*     */     try {
/* 180 */       this.rc.sequenceManager().getOutboundSequence(data.getSequenceId()).registerMessage(lastAppMessage, false);
/* 181 */     } catch (DuplicateMessageRegistrationException ex) {
/* 182 */       LOGGER.logSevereException((Throwable)ex);
/* 183 */     } catch (IllegalStateException ex) {
/* 184 */       LOGGER.logSevereException(ex);
/*     */     } 
/*     */     
/* 187 */     SequenceElement sequenceElement = new SequenceElement();
/* 188 */     sequenceElement.setId(lastAppMessage.getSequenceId());
/* 189 */     sequenceElement.setMessageNumber(Long.valueOf(lastAppMessage.getMessageNumber()));
/* 190 */     sequenceElement.setLastMessage(new SequenceElement.LastMessage());
/*     */     
/* 192 */     message.getHeaders().add(createHeader(sequenceElement));
/*     */     
/* 194 */     appendAcknowledgementHeaders(packet, data.getAcknowledgementData());
/*     */     
/* 196 */     return packet;
/*     */   }
/*     */   
/*     */   public CloseSequenceResponseData toCloseSequenceResponseData(Packet packet) throws RxRuntimeException {
/* 200 */     assert packet != null;
/* 201 */     assert packet.getMessage() != null;
/* 202 */     assert !packet.getMessage().isFault();
/*     */     
/* 204 */     Message message = packet.getMessage();
/*     */     try {
/* 206 */       AcknowledgementData ackData = getAcknowledgementData(message);
/*     */       
/* 208 */       CloseSequenceResponseData.Builder dataBuilder = CloseSequenceResponseData.getBuilder(ackData.getAcknowledgedSequenceId());
/*     */       
/* 210 */       dataBuilder.acknowledgementData(ackData);
/*     */       
/* 212 */       return dataBuilder.build();
/*     */     } finally {
/* 214 */       message.consume();
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
/*     */   public Packet toPacket(CloseSequenceResponseData data, @NotNull Packet requestPacket, boolean clientSideResponse) throws RxRuntimeException {
/* 230 */     Sequence boundSequence = this.rc.sequenceManager().getBoundSequence(data.getSequenceId());
/* 231 */     if (boundSequence != null) {
/*     */       
/* 233 */       CloseSequenceData closeSequenceData = CloseSequenceData.getBuilder(boundSequence.getId(), boundSequence.getLastMessageNumber()).acknowledgementData(data.getAcknowledgementData()).build();
/* 234 */       return toPacket(closeSequenceData, requestPacket);
/*     */     } 
/*     */     
/* 237 */     return createEmptyAcknowledgementResponse(data.getAcknowledgementData(), requestPacket);
/*     */   }
/*     */ 
/*     */   
/*     */   public TerminateSequenceData toTerminateSequenceData(Packet packet) throws RxRuntimeException {
/* 242 */     assert packet != null;
/* 243 */     assert packet.getMessage() != null;
/* 244 */     assert !packet.getMessage().isFault();
/*     */     
/* 246 */     Message message = packet.getMessage();
/* 247 */     TerminateSequenceElement tsElement = unmarshallMessage(message);
/* 248 */     TerminateSequenceData.Builder dataBuilder = tsElement.toDataBuilder();
/*     */     
/* 250 */     dataBuilder.acknowledgementData(getAcknowledgementData(message));
/*     */     
/* 252 */     return dataBuilder.build();
/*     */   }
/*     */   
/*     */   public Packet toPacket(TerminateSequenceData data, @Nullable Packet requestPacket) throws RxRuntimeException {
/* 256 */     Packet packet = this.communicator.createRequestPacket(requestPacket, new TerminateSequenceElement(data), this.rmVersion.protocolVersion.terminateSequenceAction, true);
/*     */     
/* 258 */     if (data.getAcknowledgementData() != null) {
/* 259 */       appendAcknowledgementHeaders(packet, data.getAcknowledgementData());
/*     */     }
/*     */     
/* 262 */     return packet;
/*     */   }
/*     */   
/*     */   public TerminateSequenceResponseData toTerminateSequenceResponseData(Packet packet) throws RxRuntimeException {
/* 266 */     assert packet != null;
/* 267 */     assert packet.getMessage() != null;
/* 268 */     assert !packet.getMessage().isFault();
/*     */     
/* 270 */     Message message = packet.getMessage();
/*     */     try {
/* 272 */       TerminateSequenceResponseData.Builder dataBuilder = TerminateSequenceResponseData.getBuilder("");
/* 273 */       dataBuilder.acknowledgementData(getAcknowledgementData(message));
/*     */       
/* 275 */       return dataBuilder.build();
/*     */     } finally {
/* 277 */       message.consume();
/*     */     } 
/*     */   }
/*     */   
/*     */   public Packet toPacket(TerminateSequenceResponseData data, @NotNull Packet requestPacket, boolean clientSideResponse) throws RxRuntimeException {
/* 282 */     if (data.getBoundSequenceId() != null) {
/* 283 */       TerminateSequenceData tsData = TerminateSequenceData.getBuilder(data.getBoundSequenceId(), data.getBoundSequenceLastMessageId()).acknowledgementData(data.getAcknowledgementData()).build();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 288 */       return toPacket(tsData, requestPacket);
/*     */     } 
/* 290 */     requestPacket.transportBackChannel.close();
/* 291 */     return this.communicator.createNullResponsePacket(requestPacket);
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendSequenceHeader(@NotNull Message jaxwsMessage, @NotNull ApplicationMessage message) throws RxRuntimeException {
/* 296 */     assert message != null;
/* 297 */     assert message.getSequenceId() != null;
/*     */     
/* 299 */     SequenceElement sequenceHeaderElement = new SequenceElement();
/* 300 */     sequenceHeaderElement.setId(message.getSequenceId());
/* 301 */     sequenceHeaderElement.setMessageNumber(Long.valueOf(message.getMessageNumber()));
/*     */     
/* 303 */     sequenceHeaderElement.getOtherAttributes().put(this.communicator.soapMustUnderstandAttributeName, "true");
/* 304 */     jaxwsMessage.getHeaders().add(createHeader(sequenceHeaderElement));
/*     */   }
/*     */   
/*     */   public void appendAcknowledgementHeaders(@NotNull Packet packet, @NotNull AcknowledgementData ackData) {
/* 308 */     assert packet != null;
/* 309 */     assert packet.getMessage() != null;
/* 310 */     assert ackData != null;
/*     */ 
/*     */     
/* 313 */     Message jaxwsMessage = packet.getMessage();
/*     */     
/* 315 */     if (ackData.getAckReqestedSequenceId() != null) {
/* 316 */       AckRequestedElement ackRequestedElement = new AckRequestedElement();
/* 317 */       ackRequestedElement.setId(ackData.getAckReqestedSequenceId());
/*     */ 
/*     */ 
/*     */       
/* 321 */       jaxwsMessage.getHeaders().add(createHeader(ackRequestedElement));
/*     */     } 
/*     */ 
/*     */     
/* 325 */     if (ackData.containsSequenceAcknowledgementData()) {
/* 326 */       SequenceAcknowledgementElement ackElement = new SequenceAcknowledgementElement();
/* 327 */       ackElement.setId(ackData.getAcknowledgedSequenceId());
/*     */       
/* 329 */       for (Sequence.AckRange range : ackData.getAcknowledgedRanges()) {
/* 330 */         ackElement.addAckRange(range.lower, range.upper);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 340 */       jaxwsMessage.getHeaders().add(createHeader(ackElement));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loadSequenceHeaderData(@NotNull ApplicationMessage message, @NotNull Message jaxwsMessage) throws RxRuntimeException {
/* 345 */     assert message != null;
/* 346 */     assert message.getSequenceId() == null;
/*     */     
/* 348 */     SequenceElement sequenceElement = readHeaderAsUnderstood(RmRuntimeVersion.WSRM200502.protocolVersion.protocolNamespaceUri, "Sequence", jaxwsMessage);
/* 349 */     if (sequenceElement != null) {
/* 350 */       message.setSequenceData(sequenceElement.getId(), sequenceElement.getMessageNumber().longValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadAcknowledgementData(@NotNull ApplicationMessage message, @NotNull Message jaxwsMessage) throws RxRuntimeException {
/* 355 */     assert message != null;
/* 356 */     assert message.getAcknowledgementData() == null;
/*     */     
/* 358 */     message.setAcknowledgementData(getAcknowledgementData(jaxwsMessage));
/*     */   }
/*     */   
/*     */   public AcknowledgementData getAcknowledgementData(Message jaxwsMessage) throws UnknownSequenceException, RxRuntimeException {
/* 362 */     assert jaxwsMessage != null;
/*     */     
/* 364 */     AcknowledgementData.Builder ackDataBuilder = AcknowledgementData.getBuilder();
/* 365 */     AckRequestedElement ackRequestedElement = readHeaderAsUnderstood(this.rmVersion.protocolVersion.protocolNamespaceUri, "AckRequested", jaxwsMessage);
/* 366 */     if (ackRequestedElement != null) {
/* 367 */       ackDataBuilder.ackReqestedSequenceId(ackRequestedElement.getId());
/*     */     }
/* 369 */     SequenceAcknowledgementElement ackElement = readHeaderAsUnderstood(this.rmVersion.protocolVersion.protocolNamespaceUri, "SequenceAcknowledgement", jaxwsMessage);
/* 370 */     if (ackElement != null) {
/* 371 */       List<Sequence.AckRange> ranges = new LinkedList<Sequence.AckRange>();
/* 372 */       if (!ackElement.getNack().isEmpty()) {
/* 373 */         List<BigInteger> nacks = new ArrayList<BigInteger>(ackElement.getNack());
/* 374 */         Collections.sort(nacks);
/* 375 */         long lastLowerBound = 1L;
/* 376 */         for (BigInteger nackId : nacks) {
/* 377 */           if (lastLowerBound == nackId.longValue()) {
/* 378 */             lastLowerBound++; continue;
/*     */           } 
/* 380 */           ranges.add(new Sequence.AckRange(lastLowerBound, nackId.longValue() - 1L));
/* 381 */           lastLowerBound = nackId.longValue() + 1L;
/*     */         } 
/*     */ 
/*     */         
/* 385 */         long lastMessageId = this.rc.sequenceManager().getSequence(ackElement.getId()).getLastMessageNumber();
/* 386 */         if (lastLowerBound <= lastMessageId) {
/* 387 */           ranges.add(new Sequence.AckRange(lastLowerBound, lastMessageId));
/*     */         }
/*     */       }
/* 390 */       else if (ackElement.getAcknowledgementRange() != null && !ackElement.getAcknowledgementRange().isEmpty()) {
/* 391 */         for (SequenceAcknowledgementElement.AcknowledgementRange rangeElement : ackElement.getAcknowledgementRange()) {
/* 392 */           ranges.add(new Sequence.AckRange(rangeElement.getLower().longValue(), rangeElement.getUpper().longValue()));
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 397 */       ackDataBuilder.acknowledgements(ackElement.getId(), ranges, false);
/*     */     } 
/* 399 */     return ackDataBuilder.build();
/*     */   }
/*     */ 
/*     */   
/*     */   public Header createSequenceFaultElementHeader(QName subcode, Detail detail) {
/* 404 */     return Headers.create((JAXBContext)this.rmVersion.getJaxbContext(this.addressingVersion), new SequenceFaultElement(subcode));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet createEmptyAcknowledgementResponse(AcknowledgementData ackData, Packet requestPacket) throws RxRuntimeException {
/* 410 */     if (ackData.getAckReqestedSequenceId() != null || ackData.containsSequenceAcknowledgementData()) {
/*     */       
/* 412 */       Packet response = this.rc.communicator.createEmptyResponsePacket(requestPacket, this.rc.rmVersion.protocolVersion.sequenceAcknowledgementAction);
/* 413 */       response = this.rc.communicator.setEmptyResponseMessage(response, requestPacket, this.rc.rmVersion.protocolVersion.sequenceAcknowledgementAction);
/* 414 */       appendAcknowledgementHeaders(response, ackData);
/* 415 */       return response;
/*     */     } 
/* 417 */     return this.rc.communicator.createNullResponsePacket(requestPacket);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\Wsrm200502ProtocolHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */