/*    */ package com.sun.xml.ws.addressing.v200408;
/*    */ 
/*    */ import com.sun.xml.ws.addressing.WsaClientTube;
/*    */ import com.sun.xml.ws.addressing.model.MissingAddressingHeaderException;
/*    */ import com.sun.xml.ws.api.WSBinding;
/*    */ import com.sun.xml.ws.api.message.AddressingUtils;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*    */ import com.sun.xml.ws.api.pipe.Tube;
/*    */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*    */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*    */ import com.sun.xml.ws.developer.MemberSubmissionAddressing;
/*    */ import com.sun.xml.ws.developer.MemberSubmissionAddressingFeature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MemberSubmissionWsaClientTube
/*    */   extends WsaClientTube
/*    */ {
/*    */   private final MemberSubmissionAddressing.Validation validation;
/*    */   
/*    */   public MemberSubmissionWsaClientTube(WSDLPort wsdlPort, WSBinding binding, Tube next) {
/* 61 */     super(wsdlPort, binding, next);
/* 62 */     this.validation = ((MemberSubmissionAddressingFeature)binding.getFeature(MemberSubmissionAddressingFeature.class)).getValidation();
/*    */   }
/*    */ 
/*    */   
/*    */   public MemberSubmissionWsaClientTube(MemberSubmissionWsaClientTube that, TubeCloner cloner) {
/* 67 */     super(that, cloner);
/* 68 */     this.validation = that.validation;
/*    */   }
/*    */   
/*    */   public MemberSubmissionWsaClientTube copy(TubeCloner cloner) {
/* 72 */     return new MemberSubmissionWsaClientTube(this, cloner);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void checkMandatoryHeaders(Packet packet, boolean foundAction, boolean foundTo, boolean foundReplyTo, boolean foundFaultTo, boolean foundMessageID, boolean foundRelatesTo) {
/* 78 */     super.checkMandatoryHeaders(packet, foundAction, foundTo, foundReplyTo, foundFaultTo, foundMessageID, foundRelatesTo);
/*    */ 
/*    */     
/* 81 */     if (!foundTo) {
/* 82 */       throw new MissingAddressingHeaderException(this.addressingVersion.toTag, packet);
/*    */     }
/*    */     
/* 85 */     if (!this.validation.equals(MemberSubmissionAddressing.Validation.LAX))
/*    */     {
/*    */ 
/*    */ 
/*    */       
/* 90 */       if (this.expectReply && packet.getMessage() != null && !foundRelatesTo) {
/* 91 */         String action = AddressingUtils.getAction(packet.getMessage().getMessageHeaders(), this.addressingVersion, this.soapVersion);
/*    */ 
/*    */         
/* 94 */         if (!packet.getMessage().isFault() || !action.equals(this.addressingVersion.getDefaultFaultAction()))
/* 95 */           throw new MissingAddressingHeaderException(this.addressingVersion.relatesToTag, packet); 
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\v200408\MemberSubmissionWsaClientTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */