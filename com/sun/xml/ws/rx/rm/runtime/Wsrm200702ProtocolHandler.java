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
/*     */ import com.sun.xml.ws.rx.mc.dev.AdditionalResponses;
/*     */ import com.sun.xml.ws.rx.rm.protocol.AcknowledgementData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CloseSequenceData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CloseSequenceResponseData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CreateSequenceData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CreateSequenceResponseData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.TerminateSequenceData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.TerminateSequenceResponseData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.AckRequestedElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.CloseSequenceElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.CloseSequenceResponseElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.CreateSequenceElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.CreateSequenceResponseElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.SequenceAcknowledgementElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.SequenceElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.SequenceFaultElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.TerminateSequenceElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.TerminateSequenceResponseElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.UsesSequenceSTR;
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
/*     */ 
/*     */ final class Wsrm200702ProtocolHandler
/*     */   extends WsrmProtocolHandler
/*     */ {
/*  87 */   private static final Logger LOGGER = Logger.getLogger(Wsrm200702ProtocolHandler.class);
/*     */   private final RuntimeContext rc;
/*     */   
/*     */   Wsrm200702ProtocolHandler(RmConfiguration configuration, RuntimeContext rc, Communicator communicator) {
/*  91 */     super(RmRuntimeVersion.WSRM200702, configuration, communicator);
/*     */     
/*  93 */     assert rc != null;
/*     */     
/*  95 */     this.rc = rc;
/*     */   }
/*     */   
/*     */   public CreateSequenceData toCreateSequenceData(@NotNull Packet packet) throws RxRuntimeException {
/*  99 */     assert packet != null;
/* 100 */     assert packet.getMessage() != null;
/* 101 */     assert !packet.getMessage().isFault();
/*     */     
/* 103 */     Message message = packet.getMessage();
/* 104 */     CreateSequenceElement csElement = unmarshallMessage(message);
/*     */ 
/*     */ 
/*     */     
/* 108 */     return csElement.toDataBuilder().build();
/*     */   }
/*     */   
/*     */   public Packet toPacket(CreateSequenceData data, @Nullable Packet requestPacket) throws RxRuntimeException {
/* 112 */     Packet packet = this.communicator.createRequestPacket(requestPacket, new CreateSequenceElement(data), this.rmVersion.protocolVersion.createSequenceAction, true);
/*     */     
/* 114 */     if (data.getStrType() != null) {
/* 115 */       UsesSequenceSTR usesSequenceSTR = new UsesSequenceSTR();
/* 116 */       usesSequenceSTR.getOtherAttributes().put(this.communicator.soapMustUnderstandAttributeName, "true");
/* 117 */       packet.getMessage().getHeaders().add(Headers.create((JAXBContext)getJaxbContext(), usesSequenceSTR));
/*     */     } 
/*     */     
/* 120 */     return packet;
/*     */   }
/*     */   
/*     */   public CreateSequenceResponseData toCreateSequenceResponseData(Packet packet) throws RxRuntimeException {
/* 124 */     assert packet != null;
/* 125 */     assert packet.getMessage() != null;
/* 126 */     assert !packet.getMessage().isFault();
/*     */     
/* 128 */     Message message = packet.getMessage();
/*     */     
/* 130 */     CreateSequenceResponseElement csrElement = unmarshallMessage(message);
/*     */     
/* 132 */     return csrElement.toDataBuilder().build();
/*     */   }
/*     */   
/*     */   public Packet toPacket(CreateSequenceResponseData data, @NotNull Packet requestPacket, boolean clientSideResponse) throws RxRuntimeException {
/* 136 */     return this.communicator.createResponsePacket(requestPacket, new CreateSequenceResponseElement(data), this.rmVersion.protocolVersion.createSequenceResponseAction, clientSideResponse);
/*     */   }
/*     */   
/*     */   public CloseSequenceData toCloseSequenceData(Packet packet) throws RxRuntimeException {
/* 140 */     assert packet != null;
/* 141 */     assert packet.getMessage() != null;
/* 142 */     assert !packet.getMessage().isFault();
/*     */     
/* 144 */     Message message = packet.getMessage();
/* 145 */     CloseSequenceElement csElement = unmarshallMessage(message);
/* 146 */     CloseSequenceData.Builder dataBuilder = csElement.toDataBuilder();
/*     */     
/* 148 */     dataBuilder.acknowledgementData(getAcknowledgementData(message));
/*     */     
/* 150 */     return dataBuilder.build();
/*     */   }
/*     */   
/*     */   public Packet toPacket(CloseSequenceData data, @Nullable Packet requestPacket) throws RxRuntimeException {
/* 154 */     Packet packet = this.communicator.createRequestPacket(requestPacket, new CloseSequenceElement(data), this.rmVersion.protocolVersion.closeSequenceAction, true);
/*     */     
/* 156 */     if (data.getAcknowledgementData() != null) {
/* 157 */       appendAcknowledgementHeaders(packet, data.getAcknowledgementData());
/*     */     }
/*     */     
/* 160 */     return packet;
/*     */   }
/*     */   
/*     */   public CloseSequenceResponseData toCloseSequenceResponseData(Packet packet) throws RxRuntimeException {
/* 164 */     assert packet != null;
/* 165 */     assert packet.getMessage() != null;
/* 166 */     assert !packet.getMessage().isFault();
/*     */     
/* 168 */     Message message = packet.getMessage();
/* 169 */     CloseSequenceResponseElement csrElement = unmarshallMessage(message);
/* 170 */     CloseSequenceResponseData.Builder dataBuilder = csrElement.toDataBuilder();
/*     */     
/* 172 */     dataBuilder.acknowledgementData(getAcknowledgementData(message));
/*     */     
/* 174 */     return dataBuilder.build();
/*     */   }
/*     */   
/*     */   public Packet toPacket(CloseSequenceResponseData data, @NotNull Packet requestPacket, boolean clientSideResponse) throws RxRuntimeException {
/* 178 */     Packet packet = this.communicator.createResponsePacket(requestPacket, new CloseSequenceResponseElement(data), this.rmVersion.protocolVersion.closeSequenceResponseAction, clientSideResponse);
/*     */     
/* 180 */     if (data.getAcknowledgementData() != null) {
/* 181 */       appendAcknowledgementHeaders(packet, data.getAcknowledgementData());
/*     */     }
/*     */     
/* 184 */     return packet;
/*     */   }
/*     */   
/*     */   public TerminateSequenceData toTerminateSequenceData(Packet packet) throws RxRuntimeException {
/* 188 */     assert packet != null;
/* 189 */     assert packet.getMessage() != null;
/* 190 */     assert !packet.getMessage().isFault();
/*     */     
/* 192 */     Message message = packet.getMessage();
/* 193 */     TerminateSequenceElement tsElement = unmarshallMessage(message);
/* 194 */     TerminateSequenceData.Builder dataBuilder = tsElement.toDataBuilder();
/*     */     
/* 196 */     dataBuilder.acknowledgementData(getAcknowledgementData(message));
/*     */     
/* 198 */     return dataBuilder.build();
/*     */   }
/*     */   
/*     */   public Packet toPacket(TerminateSequenceData data, @Nullable Packet requestPacket) throws RxRuntimeException {
/* 202 */     Packet packet = this.communicator.createRequestPacket(requestPacket, new TerminateSequenceElement(data), this.rmVersion.protocolVersion.terminateSequenceAction, true);
/*     */     
/* 204 */     if (data.getAcknowledgementData() != null) {
/* 205 */       appendAcknowledgementHeaders(packet, data.getAcknowledgementData());
/*     */     }
/*     */     
/* 208 */     return packet;
/*     */   }
/*     */   
/*     */   public TerminateSequenceResponseData toTerminateSequenceResponseData(Packet packet) throws RxRuntimeException {
/* 212 */     assert packet != null;
/* 213 */     assert packet.getMessage() != null;
/* 214 */     assert !packet.getMessage().isFault();
/*     */     
/* 216 */     Message message = packet.getMessage();
/*     */ 
/*     */     
/* 219 */     TerminateSequenceResponseElement tsrElement = unmarshallMessage(message);
/* 220 */     TerminateSequenceResponseData.Builder dataBuilder = tsrElement.toDataBuilder();
/*     */     
/* 222 */     dataBuilder.acknowledgementData(getAcknowledgementData(message));
/*     */     
/* 224 */     return dataBuilder.build();
/*     */   }
/*     */   
/*     */   public Packet toPacket(TerminateSequenceResponseData data, @NotNull Packet requestPacket, boolean clientSideResponse) throws RxRuntimeException {
/* 228 */     Packet packet = this.communicator.createResponsePacket(requestPacket, new TerminateSequenceResponseElement(data), this.rmVersion.protocolVersion.terminateSequenceResponseAction, clientSideResponse);
/*     */     
/* 230 */     if (data.getAcknowledgementData() != null) {
/* 231 */       appendAcknowledgementHeaders(packet, data.getAcknowledgementData());
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 239 */     if (data.getBoundSequenceId() != null) {
/* 240 */       AdditionalResponses ar = (AdditionalResponses)packet.getSatellite(AdditionalResponses.class);
/* 241 */       if (ar != null) {
/* 242 */         TerminateSequenceData tsData = TerminateSequenceData.getBuilder(data.getBoundSequenceId(), data.getBoundSequenceLastMessageId()).build();
/* 243 */         ar.getAdditionalResponsePacketQueue().offer(toPacket(tsData, requestPacket));
/*     */       } 
/*     */     } 
/*     */     
/* 247 */     return packet;
/*     */   }
/*     */   
/*     */   public void appendSequenceHeader(@NotNull Message jaxwsMessage, @NotNull ApplicationMessage message) throws RxRuntimeException {
/* 251 */     assert message != null;
/* 252 */     assert message.getSequenceId() != null;
/* 253 */     assert jaxwsMessage != null;
/*     */     
/* 255 */     SequenceElement sequenceHeaderElement = new SequenceElement();
/* 256 */     sequenceHeaderElement.setId(message.getSequenceId());
/* 257 */     sequenceHeaderElement.setMessageNumber(Long.valueOf(message.getMessageNumber()));
/*     */     
/* 259 */     sequenceHeaderElement.getOtherAttributes().put(this.communicator.soapMustUnderstandAttributeName, "true");
/* 260 */     jaxwsMessage.getHeaders().add(createHeader(sequenceHeaderElement));
/*     */   }
/*     */   
/*     */   public void appendAcknowledgementHeaders(@NotNull Packet packet, @NotNull AcknowledgementData ackData) {
/* 264 */     assert packet != null;
/* 265 */     assert packet.getMessage() != null;
/* 266 */     assert ackData != null;
/*     */ 
/*     */     
/* 269 */     Message jaxwsMessage = packet.getMessage();
/*     */     
/* 271 */     if (ackData.getAckReqestedSequenceId() != null) {
/* 272 */       AckRequestedElement ackRequestedElement = new AckRequestedElement();
/* 273 */       ackRequestedElement.setId(ackData.getAckReqestedSequenceId());
/*     */ 
/*     */ 
/*     */       
/* 277 */       jaxwsMessage.getHeaders().add(createHeader(ackRequestedElement));
/*     */       
/* 279 */       packet.invocationProperties.put(RmConfiguration.ACK_REQUESTED_HEADER_SET, Boolean.TRUE);
/*     */     } 
/*     */ 
/*     */     
/* 283 */     if (ackData.getAcknowledgedSequenceId() != null) {
/* 284 */       SequenceAcknowledgementElement ackElement = new SequenceAcknowledgementElement();
/* 285 */       ackElement.setId(ackData.getAcknowledgedSequenceId());
/*     */       
/* 287 */       List<Sequence.AckRange> ackedRanges = ackData.getAcknowledgedRanges();
/* 288 */       if (ackedRanges.isEmpty()) {
/* 289 */         ackElement.setNone(new SequenceAcknowledgementElement.None());
/*     */       } else {
/* 291 */         for (Sequence.AckRange range : ackedRanges) {
/* 292 */           ackElement.addAckRange(range.lower, range.upper);
/*     */         }
/*     */       } 
/*     */       
/* 296 */       if (ackData.isFinalAcknowledgement()) {
/* 297 */         ackElement.setFinal(new SequenceAcknowledgementElement.Final());
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 307 */       jaxwsMessage.getHeaders().add(createHeader(ackElement));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loadSequenceHeaderData(@NotNull ApplicationMessage message, @NotNull Message jaxwsMessage) throws RxRuntimeException {
/* 312 */     assert message != null;
/* 313 */     assert message.getSequenceId() == null;
/*     */     
/* 315 */     SequenceElement sequenceElement = readHeaderAsUnderstood(this.rmVersion.protocolVersion.protocolNamespaceUri, "Sequence", jaxwsMessage);
/* 316 */     if (sequenceElement != null) {
/* 317 */       message.setSequenceData(sequenceElement.getId(), sequenceElement.getMessageNumber().longValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadAcknowledgementData(@NotNull ApplicationMessage message, @NotNull Message jaxwsMessage) throws RxRuntimeException {
/* 322 */     assert message != null;
/* 323 */     assert message.getAcknowledgementData() == null;
/*     */     
/* 325 */     message.setAcknowledgementData(getAcknowledgementData(jaxwsMessage));
/*     */   }
/*     */   
/*     */   public AcknowledgementData getAcknowledgementData(Message jaxwsMessage) throws UnknownSequenceException, RxRuntimeException {
/* 329 */     assert jaxwsMessage != null;
/*     */     
/* 331 */     AcknowledgementData.Builder ackDataBuilder = AcknowledgementData.getBuilder();
/* 332 */     AckRequestedElement ackRequestedElement = readHeaderAsUnderstood(this.rmVersion.protocolVersion.protocolNamespaceUri, "AckRequested", jaxwsMessage);
/* 333 */     if (ackRequestedElement != null) {
/* 334 */       ackDataBuilder.ackReqestedSequenceId(ackRequestedElement.getId());
/*     */     }
/* 336 */     SequenceAcknowledgementElement ackElement = readHeaderAsUnderstood(this.rmVersion.protocolVersion.protocolNamespaceUri, "SequenceAcknowledgement", jaxwsMessage);
/* 337 */     if (ackElement != null) {
/* 338 */       List<Sequence.AckRange> ranges = new LinkedList<Sequence.AckRange>();
/* 339 */       if (ackElement.getNone() == null) {
/* 340 */         if (!ackElement.getNack().isEmpty()) {
/* 341 */           List<BigInteger> nacks = new ArrayList<BigInteger>(ackElement.getNack());
/* 342 */           Collections.sort(nacks);
/* 343 */           long lastLowerBound = 1L;
/* 344 */           for (BigInteger nackId : nacks) {
/* 345 */             if (lastLowerBound == nackId.longValue()) {
/* 346 */               lastLowerBound++; continue;
/*     */             } 
/* 348 */             ranges.add(new Sequence.AckRange(lastLowerBound, nackId.longValue() - 1L));
/* 349 */             lastLowerBound = nackId.longValue() + 1L;
/*     */           } 
/*     */           
/* 352 */           long lastMessageId = this.rc.sequenceManager().getSequence(ackElement.getId()).getLastMessageNumber();
/* 353 */           if (lastLowerBound <= lastMessageId) {
/* 354 */             ranges.add(new Sequence.AckRange(lastLowerBound, lastMessageId));
/*     */           }
/* 356 */         } else if (ackElement.getAcknowledgementRange() != null && !ackElement.getAcknowledgementRange().isEmpty()) {
/* 357 */           for (SequenceAcknowledgementElement.AcknowledgementRange rangeElement : ackElement.getAcknowledgementRange()) {
/* 358 */             ranges.add(new Sequence.AckRange(rangeElement.getLower().longValue(), rangeElement.getUpper().longValue()));
/*     */           }
/*     */         } 
/*     */       }
/* 362 */       ackDataBuilder.acknowledgements(ackElement.getId(), ranges, (ackElement.getFinal() != null));
/*     */     } 
/*     */ 
/*     */     
/* 366 */     return ackDataBuilder.build();
/*     */   }
/*     */ 
/*     */   
/*     */   public Header createSequenceFaultElementHeader(QName subcode, Detail detail) {
/* 371 */     return Headers.create((JAXBContext)this.rmVersion.getJaxbContext(this.addressingVersion), new SequenceFaultElement(subcode, detail));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet createEmptyAcknowledgementResponse(AcknowledgementData ackData, Packet requestPacket) throws RxRuntimeException {
/* 377 */     if (ackData.getAckReqestedSequenceId() != null || ackData.getAcknowledgedSequenceId() != null) {
/*     */       
/* 379 */       Packet response = this.rc.communicator.createEmptyResponsePacket(requestPacket, this.rc.rmVersion.protocolVersion.sequenceAcknowledgementAction);
/* 380 */       response = this.rc.communicator.setEmptyResponseMessage(response, requestPacket, this.rc.rmVersion.protocolVersion.sequenceAcknowledgementAction);
/* 381 */       appendAcknowledgementHeaders(response, ackData);
/* 382 */       return response;
/*     */     } 
/* 384 */     return this.rc.communicator.createNullResponsePacket(requestPacket);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\Wsrm200702ProtocolHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */