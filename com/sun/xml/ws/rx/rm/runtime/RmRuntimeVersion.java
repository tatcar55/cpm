/*     */ package com.sun.xml.ws.rx.rm.runtime;
/*     */ 
/*     */ import com.sun.xml.bind.api.JAXBRIContext;
/*     */ import com.sun.xml.ws.api.addressing.AddressingVersion;
/*     */ import com.sun.xml.ws.rx.RxRuntimeException;
/*     */ import com.sun.xml.ws.rx.rm.api.RmProtocolVersion;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.AcceptType;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.AckRequestedElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.CreateSequenceElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.CreateSequenceResponseElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.Expires;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.Identifier;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.OfferType;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.SequenceAcknowledgementElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.SequenceElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.SequenceFaultElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200502.TerminateSequenceElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.AcceptType;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.AckRequestedElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.Address;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.CloseSequenceElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.CloseSequenceResponseElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.CreateSequenceElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.CreateSequenceResponseElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.DetailType;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.Expires;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.Identifier;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.IncompleteSequenceBehaviorType;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.OfferType;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.SequenceAcknowledgementElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.SequenceElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.SequenceFaultElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.TerminateSequenceElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.TerminateSequenceResponseElement;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.UsesSequenceSSL;
/*     */ import com.sun.xml.ws.rx.rm.protocol.wsrm200702.UsesSequenceSTR;
/*     */ import com.sun.xml.ws.rx.util.JaxbContextRepository;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum RmRuntimeVersion
/*     */ {
/*  76 */   WSRM200502(RmProtocolVersion.WSRM200502, new Class[] { AcceptType.class, AckRequestedElement.class, CreateSequenceElement.class, CreateSequenceResponseElement.class, Expires.class, Identifier.class, OfferType.class, SequenceAcknowledgementElement.class, SequenceElement.class, SequenceFaultElement.class, TerminateSequenceElement.class
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }),
/* 101 */   WSRM200702(RmProtocolVersion.WSRM200702, new Class[] { AcceptType.class, AckRequestedElement.class, Address.class, CloseSequenceElement.class, CloseSequenceResponseElement.class, CreateSequenceElement.class, CreateSequenceResponseElement.class, DetailType.class, Expires.class, Identifier.class, IncompleteSequenceBehaviorType.class, OfferType.class, SequenceAcknowledgementElement.class, SequenceElement.class, SequenceFaultElement.class, TerminateSequenceElement.class, TerminateSequenceResponseElement.class, UsesSequenceSSL.class, UsesSequenceSTR.class });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final RmProtocolVersion protocolVersion;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final JaxbContextRepository jaxbContextRepository;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RmRuntimeVersion forProtocolVersion(RmProtocolVersion protocolVersion) {
/* 124 */     for (RmRuntimeVersion version : values()) {
/* 125 */       if (version.protocolVersion == protocolVersion) {
/* 126 */         return version;
/*     */       }
/*     */     } 
/*     */     
/* 130 */     assert false : "Unsupported WS-ReliableMessaging protocol version definition detected";
/*     */     
/* 132 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RmRuntimeVersion getDefault() {
/* 143 */     return forProtocolVersion(RmProtocolVersion.getDefault());
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
/*     */   RmRuntimeVersion(RmProtocolVersion protocolVersion, Class<?>... rmProtocolClasses) {
/* 158 */     this.protocolVersion = protocolVersion;
/* 159 */     this.jaxbContextRepository = new JaxbContextRepository(rmProtocolClasses);
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
/*     */   public Unmarshaller createUnmarshaller(AddressingVersion av) throws RxRuntimeException {
/* 177 */     return this.jaxbContextRepository.getUnmarshaller(av);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JAXBRIContext getJaxbContext(AddressingVersion av) {
/* 188 */     return this.jaxbContextRepository.getJaxbContext(av);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 193 */     return "RmRuntimeVersion{protocolVersion=" + this.protocolVersion + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\rx\rm\runtime\RmRuntimeVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */