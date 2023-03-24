/*    */ package com.sun.xml.ws.server.provider;
/*    */ 
/*    */ import com.sun.xml.ws.api.SOAPVersion;
/*    */ import com.sun.xml.ws.api.message.Message;
/*    */ import com.sun.xml.ws.api.message.Packet;
/*    */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
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
/*    */ 
/*    */ final class MessageProviderArgumentBuilder
/*    */   extends ProviderArgumentsBuilder<Message>
/*    */ {
/*    */   private final SOAPVersion soapVersion;
/*    */   
/*    */   public MessageProviderArgumentBuilder(SOAPVersion soapVersion) {
/* 55 */     this.soapVersion = soapVersion;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message getParameter(Packet packet) {
/* 60 */     return packet.getMessage();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Message getResponseMessage(Message returnValue) {
/* 65 */     return returnValue;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Message getResponseMessage(Exception e) {
/* 70 */     return SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, null, e);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\server\provider\MessageProviderArgumentBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */