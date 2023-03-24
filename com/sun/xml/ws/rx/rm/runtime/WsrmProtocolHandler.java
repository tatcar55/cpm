/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.ws.api.SOAPVersion;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.api.message.Header;
/*     */ import com.sun.xml.ws.api.message.Headers;
/*     */ import com.sun.xml.ws.api.message.Message;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.rm.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.rx.rm.protocol.AcknowledgementData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CloseSequenceData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CloseSequenceResponseData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CreateSequenceData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.CreateSequenceResponseData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.TerminateSequenceData;
/*     */ import com.sun.xml.ws.rx.rm.protocol.TerminateSequenceResponseData;
/*     */ import com.sun.xml.ws.rx.util.Communicator;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBException;
/*     */ import javax.xml.bind.Unmarshaller;
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
/*     */ 
/*     */ public abstract class WsrmProtocolHandler
/*     */ {
/*  74 */   private static final Logger LOGGER = Logger.getLogger(WsrmProtocolHandler.class);
/*     */   
/*     */   public static WsrmProtocolHandler getInstance(RmConfiguration configuration, Communicator communicator, RuntimeContext rc) {
/*  77 */     switch (configuration.getRuntimeVersion()) {
/*     */       case WSRM200502:
/*  79 */         return new Wsrm200502ProtocolHandler(configuration, rc, communicator);
/*     */       case WSRM200702:
/*  81 */         return new Wsrm200702ProtocolHandler(configuration, rc, communicator);
/*     */     } 
/*  83 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected final RmRuntimeVersion rmVersion;
/*     */   
/*     */   protected final Communicator communicator;
/*     */   protected final AddressingVersion addressingVersion;
/*     */   protected final SOAPVersion soapVersion;
/*     */   
/*     */   public abstract void appendSequenceHeader(@NotNull Message paramMessage, @NotNull ApplicationMessage paramApplicationMessage) throws RxRuntimeException;
/*     */   
/*     */   public abstract void appendAcknowledgementHeaders(@NotNull Packet paramPacket, @NotNull AcknowledgementData paramAcknowledgementData) throws RxRuntimeException;
/*     */   
/*     */   public abstract AcknowledgementData getAcknowledgementData(Message paramMessage) throws RxRuntimeException;
/*     */   
/*     */   public abstract void loadAcknowledgementData(@NotNull ApplicationMessage paramApplicationMessage, @NotNull Message paramMessage) throws RxRuntimeException;
/*     */   
/*     */   public abstract void loadSequenceHeaderData(@NotNull ApplicationMessage paramApplicationMessage, @NotNull Message paramMessage) throws RxRuntimeException;
/*     */   
/*     */   public abstract CreateSequenceData toCreateSequenceData(@NotNull Packet paramPacket) throws RxRuntimeException;
/*     */   
/*     */   public abstract Packet toPacket(@NotNull CreateSequenceData paramCreateSequenceData, @Nullable Packet paramPacket) throws RxRuntimeException;
/*     */   
/*     */   public abstract CreateSequenceResponseData toCreateSequenceResponseData(@NotNull Packet paramPacket) throws RxRuntimeException;
/*     */   
/*     */   public abstract Packet toPacket(@NotNull CreateSequenceResponseData paramCreateSequenceResponseData, @NotNull Packet paramPacket, boolean paramBoolean) throws RxRuntimeException;
/*     */   
/*     */   public abstract CloseSequenceData toCloseSequenceData(@NotNull Packet paramPacket) throws RxRuntimeException;
/*     */   
/*     */   public abstract Packet toPacket(@NotNull CloseSequenceData paramCloseSequenceData, @Nullable Packet paramPacket) throws RxRuntimeException;
/*     */   
/*     */   public abstract CloseSequenceResponseData toCloseSequenceResponseData(@NotNull Packet paramPacket) throws RxRuntimeException;
/*     */   
/*     */   public abstract Packet toPacket(@NotNull CloseSequenceResponseData paramCloseSequenceResponseData, @NotNull Packet paramPacket, boolean paramBoolean) throws RxRuntimeException;
/*     */   
/*     */   public abstract TerminateSequenceData toTerminateSequenceData(@NotNull Packet paramPacket) throws RxRuntimeException;
/*     */   
/*     */   public abstract Packet toPacket(@NotNull TerminateSequenceData paramTerminateSequenceData, @Nullable Packet paramPacket) throws RxRuntimeException;
/*     */   
/*     */   public abstract TerminateSequenceResponseData toTerminateSequenceResponseData(@NotNull Packet paramPacket) throws RxRuntimeException;
/*     */   
/*     */   public abstract Packet toPacket(@NotNull TerminateSequenceResponseData paramTerminateSequenceResponseData, @NotNull Packet paramPacket, boolean paramBoolean) throws RxRuntimeException;
/*     */   
/*     */   public abstract Header createSequenceFaultElementHeader(QName paramQName, Detail paramDetail);
/*     */   
/*     */   public abstract Packet createEmptyAcknowledgementResponse(AcknowledgementData paramAcknowledgementData, Packet paramPacket) throws RxRuntimeException;
/*     */   
/*     */   public final boolean containsProtocolMessage(@NotNull Packet packet) {
/* 132 */     assert packet != null;
/*     */     
/* 134 */     return (packet.getMessage() == null) ? false : this.rmVersion.protocolVersion.isProtocolAction(getWsaAction(packet.getMessage()));
/*     */   }
/*     */   
/*     */   public final boolean containsProtocolRequest(@NotNull Packet packet) {
/* 138 */     assert packet != null;
/*     */     
/* 140 */     return (packet.getMessage() == null) ? false : this.rmVersion.protocolVersion.isProtocolRequest(getWsaAction(packet.getMessage()));
/*     */   }
/*     */   
/*     */   public final boolean containsProtocolResponse(@NotNull Packet packet) {
/* 144 */     assert packet != null;
/*     */     
/* 146 */     return (packet.getMessage() == null) ? false : this.rmVersion.protocolVersion.isProtocolResponse(getWsaAction(packet.getMessage()));
/*     */   }
/*     */   
/*     */   protected WsrmProtocolHandler(@NotNull RmRuntimeVersion rmVersion, @NotNull RmConfiguration configuration, @NotNull Communicator communicator) {
/* 150 */     assert rmVersion != null;
/* 151 */     assert rmVersion == configuration.getRuntimeVersion();
/* 152 */     assert configuration != null;
/* 153 */     assert communicator != null;
/*     */     
/* 155 */     this.rmVersion = rmVersion;
/* 156 */     this.communicator = communicator;
/*     */     
/* 158 */     this.addressingVersion = configuration.getAddressingVersion();
/* 159 */     this.soapVersion = configuration.getSoapVersion();
/*     */   }
/*     */   
/*     */   protected final Header createHeader(Object jaxbHeaderContent) {
/* 163 */     return Headers.create((JAXBContext)this.rmVersion.getJaxbContext(this.addressingVersion), jaxbHeaderContent);
/*     */   }
/*     */   
/*     */   protected final <T> T readHeaderAsUnderstood(@NotNull String nsUri, @NotNull String name, @NotNull Message message) throws RxRuntimeException {
/* 167 */     assert nsUri != null;
/* 168 */     assert name != null;
/* 169 */     assert message != null;
/*     */     
/* 171 */     Header header = message.getHeaders().get(nsUri, name, true);
/* 172 */     if (header == null) {
/* 173 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 177 */       T result = (T)header.readAsJAXB(getJaxbUnmarshaller());
/* 178 */       return result;
/* 179 */     } catch (JAXBException ex) {
/* 180 */       throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSRM_1122_ERROR_MARSHALLING_RM_HEADER(nsUri + "#" + name), ex));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected final String getWsaAction(@NotNull Message message) {
/* 185 */     return message.getHeaders().getAction(this.addressingVersion, this.soapVersion);
/*     */   }
/*     */   
/*     */   protected final JAXBRIContext getJaxbContext() {
/* 189 */     return this.rmVersion.getJaxbContext(this.addressingVersion);
/*     */   }
/*     */   
/*     */   protected final Unmarshaller getJaxbUnmarshaller() throws RxRuntimeException {
/* 193 */     return this.rmVersion.createUnmarshaller(this.addressingVersion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final <T> T unmarshallMessage(@NotNull Message message) throws RxRuntimeException {
/* 204 */     assert message != null;
/*     */     
/*     */     try {
/* 207 */       T result = (T)message.readPayloadAsJAXB(getJaxbUnmarshaller());
/* 208 */       return result;
/* 209 */     } catch (JAXBException e) {
/* 210 */       throw (RxRuntimeException)LOGGER.logSevereException(new RxRuntimeException(LocalizationMessages.WSRM_1123_ERROR_UNMARSHALLING_MESSAGE(), e));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\WsrmProtocolHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */