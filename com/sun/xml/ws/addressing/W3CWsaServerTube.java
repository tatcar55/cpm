/*     */ package com.sun.xml.ws.addressing;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.addressing.model.InvalidAddressingHeaderException;
/*     */ import com.sun.xml.ws.addressing.model.MissingAddressingHeaderException;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import javax.xml.ws.soap.AddressingFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class W3CWsaServerTube
/*     */   extends WsaServerTube
/*     */ {
/*     */   private final AddressingFeature af;
/*     */   
/*     */   public W3CWsaServerTube(WSEndpoint endpoint, @NotNull WSDLPort wsdlPort, WSBinding binding, Tube next) {
/*  67 */     super(endpoint, wsdlPort, binding, next);
/*  68 */     this.af = (AddressingFeature)binding.getFeature(AddressingFeature.class);
/*     */   }
/*     */   
/*     */   public W3CWsaServerTube(W3CWsaServerTube that, TubeCloner cloner) {
/*  72 */     super(that, cloner);
/*  73 */     this.af = that.af;
/*     */   }
/*     */ 
/*     */   
/*     */   public W3CWsaServerTube copy(TubeCloner cloner) {
/*  78 */     return new W3CWsaServerTube(this, cloner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkMandatoryHeaders(Packet packet, boolean foundAction, boolean foundTo, boolean foundReplyTo, boolean foundFaultTo, boolean foundMessageId, boolean foundRelatesTo) {
/*  85 */     super.checkMandatoryHeaders(packet, foundAction, foundTo, foundReplyTo, foundFaultTo, foundMessageId, foundRelatesTo);
/*     */ 
/*     */ 
/*     */     
/*  89 */     WSDLBoundOperation wbo = getWSDLBoundOperation(packet);
/*     */     
/*  91 */     if (wbo != null)
/*     */     {
/*  93 */       if (!wbo.getOperation().isOneWay() && !foundMessageId) {
/*  94 */         throw new MissingAddressingHeaderException(this.addressingVersion.messageIDTag, packet);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isAnonymousRequired(@Nullable WSDLBoundOperation wbo) {
/* 102 */     return (getResponseRequirement(wbo) == WSDLBoundOperation.ANONYMOUS.required);
/*     */   }
/*     */   
/*     */   private WSDLBoundOperation.ANONYMOUS getResponseRequirement(@Nullable WSDLBoundOperation wbo) {
/*     */     try {
/* 107 */       if (this.af.getResponses() == AddressingFeature.Responses.ANONYMOUS)
/* 108 */         return WSDLBoundOperation.ANONYMOUS.required; 
/* 109 */       if (this.af.getResponses() == AddressingFeature.Responses.NON_ANONYMOUS) {
/* 110 */         return WSDLBoundOperation.ANONYMOUS.prohibited;
/*     */       }
/* 112 */     } catch (NoSuchMethodError e) {}
/*     */ 
/*     */ 
/*     */     
/* 116 */     return (wbo != null) ? wbo.getAnonymous() : WSDLBoundOperation.ANONYMOUS.optional;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void checkAnonymousSemantics(WSDLBoundOperation wbo, WSEndpointReference replyTo, WSEndpointReference faultTo) {
/* 121 */     String replyToValue = null;
/* 122 */     String faultToValue = null;
/*     */     
/* 124 */     if (replyTo != null) {
/* 125 */       replyToValue = replyTo.getAddress();
/*     */     }
/* 127 */     if (faultTo != null)
/* 128 */       faultToValue = faultTo.getAddress(); 
/* 129 */     WSDLBoundOperation.ANONYMOUS responseRequirement = getResponseRequirement(wbo);
/*     */     
/* 131 */     switch (responseRequirement) {
/*     */       case prohibited:
/* 133 */         if (replyToValue != null && replyToValue.equals(this.addressingVersion.anonymousUri)) {
/* 134 */           throw new InvalidAddressingHeaderException(this.addressingVersion.replyToTag, W3CAddressingConstants.ONLY_NON_ANONYMOUS_ADDRESS_SUPPORTED);
/*     */         }
/* 136 */         if (faultToValue != null && faultToValue.equals(this.addressingVersion.anonymousUri))
/* 137 */           throw new InvalidAddressingHeaderException(this.addressingVersion.faultToTag, W3CAddressingConstants.ONLY_NON_ANONYMOUS_ADDRESS_SUPPORTED); 
/*     */         break;
/*     */       case required:
/* 140 */         if (replyToValue != null && !replyToValue.equals(this.addressingVersion.anonymousUri)) {
/* 141 */           throw new InvalidAddressingHeaderException(this.addressingVersion.replyToTag, W3CAddressingConstants.ONLY_ANONYMOUS_ADDRESS_SUPPORTED);
/*     */         }
/* 143 */         if (faultToValue != null && !faultToValue.equals(this.addressingVersion.anonymousUri))
/* 144 */           throw new InvalidAddressingHeaderException(this.addressingVersion.faultToTag, W3CAddressingConstants.ONLY_ANONYMOUS_ADDRESS_SUPPORTED); 
/*     */         break;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\W3CWsaServerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */