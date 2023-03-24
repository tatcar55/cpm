/*    */ package com.sun.xml.ws.protocol.soap;
/*    */ 
/*    */ import com.sun.xml.ws.api.SOAPVersion;
/*    */ import com.sun.xml.ws.api.message.ExceptionHasMessage;
/*    */ import com.sun.xml.ws.api.message.Message;
/*    */ import com.sun.xml.ws.fault.SOAPFaultBuilder;
/*    */ import javax.xml.namespace.QName;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageCreationException
/*    */   extends ExceptionHasMessage
/*    */ {
/*    */   private final SOAPVersion soapVersion;
/*    */   
/*    */   public MessageCreationException(SOAPVersion soapVersion, Object... args) {
/* 61 */     super("soap.msg.create.err", args);
/* 62 */     this.soapVersion = soapVersion;
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 66 */     return "com.sun.xml.ws.resources.soap";
/*    */   }
/*    */   
/*    */   public Message getFaultMessage() {
/* 70 */     QName faultCode = this.soapVersion.faultCodeClient;
/* 71 */     return SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, getLocalizedMessage(), faultCode);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\protocol\soap\MessageCreationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */