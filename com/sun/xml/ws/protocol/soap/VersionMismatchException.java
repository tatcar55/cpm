/*    */ package com.sun.xml.ws.protocol.soap;
/*    */ 
/*    */ import com.sun.xml.ws.api.SOAPVersion;
/*    */ import com.sun.xml.ws.api.message.ExceptionHasMessage;
/*    */ import com.sun.xml.ws.api.message.Message;
/*    */ import com.sun.xml.ws.encoding.soap.SOAP12Constants;
/*    */ import com.sun.xml.ws.encoding.soap.SOAPConstants;
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
/*    */ 
/*    */ public class VersionMismatchException
/*    */   extends ExceptionHasMessage
/*    */ {
/*    */   private final SOAPVersion soapVersion;
/*    */   
/*    */   public VersionMismatchException(SOAPVersion soapVersion, Object... args) {
/* 64 */     super("soap.version.mismatch.err", args);
/* 65 */     this.soapVersion = soapVersion;
/*    */   }
/*    */   
/*    */   public String getDefaultResourceBundleName() {
/* 69 */     return "com.sun.xml.ws.resources.soap";
/*    */   }
/*    */   
/*    */   public Message getFaultMessage() {
/* 73 */     QName faultCode = (this.soapVersion == SOAPVersion.SOAP_11) ? SOAPConstants.FAULT_CODE_VERSION_MISMATCH : SOAP12Constants.FAULT_CODE_VERSION_MISMATCH;
/*    */ 
/*    */     
/* 76 */     return SOAPFaultBuilder.createSOAPFaultMessage(this.soapVersion, getLocalizedMessage(), faultCode);
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\protocol\soap\VersionMismatchException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */