/*    */ package com.sun.xml.ws.addressing;
/*    */ 
/*    */ import com.sun.xml.ws.addressing.model.MissingAddressingHeaderException;
/*    */ import com.sun.xml.ws.api.WSBinding;
/*    */ import com.sun.xml.ws.api.message.AddressingUtils;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*    */ import com.sun.xml.ws.api.pipe.Tube;
/*    */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*    */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
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
/*    */ public class W3CWsaClientTube
/*    */   extends WsaClientTube
/*    */ {
/*    */   public W3CWsaClientTube(WSDLPort wsdlPort, WSBinding binding, Tube next) {
/* 56 */     super(wsdlPort, binding, next);
/*    */   }
/*    */   
/*    */   public W3CWsaClientTube(WsaClientTube that, TubeCloner cloner) {
/* 60 */     super(that, cloner);
/*    */   }
/*    */ 
/*    */   
/*    */   public W3CWsaClientTube copy(TubeCloner cloner) {
/* 65 */     return new W3CWsaClientTube(this, cloner);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void checkMandatoryHeaders(Packet packet, boolean foundAction, boolean foundTo, boolean foundReplyTo, boolean foundFaultTo, boolean foundMessageID, boolean foundRelatesTo) {
/* 71 */     super.checkMandatoryHeaders(packet, foundAction, foundTo, foundReplyTo, foundFaultTo, foundMessageID, foundRelatesTo);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 76 */     if (this.expectReply && packet.getMessage() != null && !foundRelatesTo) {
/* 77 */       String action = AddressingUtils.getAction(packet.getMessage().getMessageHeaders(), this.addressingVersion, this.soapVersion);
/*    */ 
/*    */       
/* 80 */       if (!packet.getMessage().isFault() || !action.equals(this.addressingVersion.getDefaultFaultAction()))
/* 81 */         throw new MissingAddressingHeaderException(this.addressingVersion.relatesToTag, packet); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\W3CWsaClientTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */