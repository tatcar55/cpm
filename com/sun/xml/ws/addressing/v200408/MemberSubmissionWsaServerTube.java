/*     */ package com.sun.xml.ws.addressing.v200408;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.addressing.WsaServerTube;
/*     */ import com.sun.xml.ws.addressing.model.MissingAddressingHeaderException;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.api.server.WSEndpoint;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionAddressing;
/*     */ import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MemberSubmissionWsaServerTube
/*     */   extends WsaServerTube
/*     */ {
/*     */   private final MemberSubmissionAddressing.Validation validation;
/*     */   
/*     */   public MemberSubmissionWsaServerTube(WSEndpoint endpoint, @NotNull WSDLPort wsdlPort, WSBinding binding, Tube next) {
/*  62 */     super(endpoint, wsdlPort, binding, next);
/*  63 */     this.validation = ((MemberSubmissionAddressingFeature)binding.getFeature(MemberSubmissionAddressingFeature.class)).getValidation();
/*     */   }
/*     */   
/*     */   public MemberSubmissionWsaServerTube(MemberSubmissionWsaServerTube that, TubeCloner cloner) {
/*  67 */     super(that, cloner);
/*  68 */     this.validation = that.validation;
/*     */   }
/*     */ 
/*     */   
/*     */   public MemberSubmissionWsaServerTube copy(TubeCloner cloner) {
/*  73 */     return new MemberSubmissionWsaServerTube(this, cloner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void checkMandatoryHeaders(Packet packet, boolean foundAction, boolean foundTo, boolean foundReplyTo, boolean foundFaultTo, boolean foundMessageId, boolean foundRelatesTo) {
/*  80 */     super.checkMandatoryHeaders(packet, foundAction, foundTo, foundReplyTo, foundFaultTo, foundMessageId, foundRelatesTo);
/*     */ 
/*     */ 
/*     */     
/*  84 */     if (!foundTo) {
/*  85 */       throw new MissingAddressingHeaderException(this.addressingVersion.toTag, packet);
/*     */     }
/*     */     
/*  88 */     if (this.wsdlPort != null) {
/*  89 */       WSDLBoundOperation wbo = getWSDLBoundOperation(packet);
/*     */ 
/*     */ 
/*     */       
/*  93 */       if (wbo != null && !wbo.getOperation().isOneWay() && !foundReplyTo) {
/*  94 */         throw new MissingAddressingHeaderException(this.addressingVersion.replyToTag, packet);
/*     */       }
/*     */     } 
/*  97 */     if (!this.validation.equals(MemberSubmissionAddressing.Validation.LAX))
/*     */     {
/*  99 */       if ((foundReplyTo || foundFaultTo) && !foundMessageId)
/* 100 */         throw new MissingAddressingHeaderException(this.addressingVersion.messageIDTag, packet); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\v200408\MemberSubmissionWsaServerTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */