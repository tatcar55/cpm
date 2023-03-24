/*     */ package com.sun.xml.ws.addressing;
/*     */ 
/*     */ import com.oracle.webservices.api.message.PropertySet;
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.xml.ws.addressing.model.ActionNotSupportedException;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.message.AddressingUtils;
/*     */ import com.sun.xml.ws.api.message.Packet;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.api.pipe.NextAction;
/*     */ import com.sun.xml.ws.api.pipe.Tube;
/*     */ import com.sun.xml.ws.api.pipe.TubeCloner;
/*     */ import com.sun.xml.ws.api.pipe.helper.AbstractTubeImpl;
/*     */ import com.sun.xml.ws.resources.AddressingMessages;
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
/*     */ public class WsaClientTube
/*     */   extends WsaTube
/*     */ {
/*     */   protected boolean expectReply = true;
/*     */   
/*     */   public WsaClientTube(WSDLPort wsdlPort, WSBinding binding, Tube next) {
/*  69 */     super(wsdlPort, binding, next);
/*     */   }
/*     */   
/*     */   public WsaClientTube(WsaClientTube that, TubeCloner cloner) {
/*  73 */     super(that, cloner);
/*     */   }
/*     */   
/*     */   public WsaClientTube copy(TubeCloner cloner) {
/*  77 */     return new WsaClientTube(this, cloner);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public NextAction processRequest(Packet request) {
/*  82 */     this.expectReply = request.expectReply.booleanValue();
/*  83 */     return doInvoke(this.next, request);
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public NextAction processResponse(Packet response) {
/*  89 */     if (response.getMessage() != null) {
/*  90 */       response = validateInboundHeaders(response);
/*  91 */       response.addSatellite((PropertySet)new WsaPropertyBag(this.addressingVersion, this.soapVersion, response));
/*  92 */       String msgId = AddressingUtils.getMessageID(response.getMessage().getMessageHeaders(), this.addressingVersion, this.soapVersion);
/*     */ 
/*     */       
/*  95 */       response.put("com.sun.xml.ws.addressing.WsaPropertyBag.MessageIdFromRequest", msgId);
/*     */     } 
/*     */     
/*  98 */     return doReturnWith(response);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void validateAction(Packet packet) {
/* 106 */     WSDLBoundOperation wbo = getWSDLBoundOperation(packet);
/*     */     
/* 108 */     if (wbo == null)
/*     */       return; 
/* 110 */     String gotA = AddressingUtils.getAction(packet.getMessage().getMessageHeaders(), this.addressingVersion, this.soapVersion);
/*     */ 
/*     */     
/* 113 */     if (gotA == null) {
/* 114 */       throw new WebServiceException(AddressingMessages.VALIDATION_CLIENT_NULL_ACTION());
/*     */     }
/* 116 */     String expected = this.helper.getOutputAction(packet);
/*     */     
/* 118 */     if (expected != null && !gotA.equals(expected))
/* 119 */       throw new ActionNotSupportedException(gotA); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\WsaClientTube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */