/*    */ package com.sun.xml.messaging.saaj.soap.ver1_2;
/*    */ 
/*    */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.SOAPFactoryImpl;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.Detail;
/*    */ import javax.xml.soap.SOAPException;
/*    */ import javax.xml.soap.SOAPFault;
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
/*    */ public class SOAPFactory1_2Impl
/*    */   extends SOAPFactoryImpl
/*    */ {
/*    */   protected SOAPDocumentImpl createDocument() {
/* 58 */     return (new SOAPPart1_2Impl()).getDocument();
/*    */   }
/*    */   
/*    */   public Detail createDetail() throws SOAPException {
/* 62 */     return (Detail)new Detail1_2Impl(createDocument());
/*    */   }
/*    */ 
/*    */   
/*    */   public SOAPFault createFault(String reasonText, QName faultCode) throws SOAPException {
/* 67 */     if (faultCode == null) {
/* 68 */       throw new IllegalArgumentException("faultCode argument for createFault was passed NULL");
/*    */     }
/* 70 */     if (reasonText == null) {
/* 71 */       throw new IllegalArgumentException("reasonText argument for createFault was passed NULL");
/*    */     }
/* 73 */     Fault1_2Impl fault = new Fault1_2Impl(createDocument(), null);
/* 74 */     fault.setFaultCode(faultCode);
/* 75 */     fault.setFaultString(reasonText);
/* 76 */     return (SOAPFault)fault;
/*    */   }
/*    */   
/*    */   public SOAPFault createFault() throws SOAPException {
/* 80 */     Fault1_2Impl fault = new Fault1_2Impl(createDocument(), null);
/* 81 */     fault.setFaultCode(fault.getDefaultFaultCode());
/* 82 */     fault.setFaultString("Fault string, and possibly fault code, not set");
/* 83 */     return (SOAPFault)fault;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_2\SOAPFactory1_2Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */