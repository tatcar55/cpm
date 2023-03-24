/*    */ package com.sun.xml.messaging.saaj.soap.ver1_1;
/*    */ 
/*    */ import com.sun.xml.messaging.saaj.soap.SOAPDocument;
/*    */ import com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.impl.BodyImpl;
/*    */ import com.sun.xml.messaging.saaj.soap.name.NameImpl;
/*    */ import java.util.Locale;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.soap.Name;
/*    */ import javax.xml.soap.SOAPBodyElement;
/*    */ import javax.xml.soap.SOAPElement;
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
/*    */ public class Body1_1Impl
/*    */   extends BodyImpl
/*    */ {
/*    */   public Body1_1Impl(SOAPDocumentImpl ownerDocument, String prefix) {
/* 59 */     super(ownerDocument, NameImpl.createBody1_1Name(prefix));
/*    */   }
/*    */ 
/*    */   
/*    */   public SOAPFault addSOAP12Fault(QName faultCode, String faultReason, Locale locale) {
/* 64 */     throw new UnsupportedOperationException("Not supported in SOAP 1.1");
/*    */   }
/*    */ 
/*    */   
/*    */   protected NameImpl getFaultName(String name) {
/* 69 */     return NameImpl.createFault1_1Name(null);
/*    */   }
/*    */   
/*    */   protected SOAPBodyElement createBodyElement(Name name) {
/* 73 */     return (SOAPBodyElement)new BodyElement1_1Impl(((SOAPDocument)getOwnerDocument()).getDocument(), name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected SOAPBodyElement createBodyElement(QName name) {
/* 79 */     return (SOAPBodyElement)new BodyElement1_1Impl(((SOAPDocument)getOwnerDocument()).getDocument(), name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected QName getDefaultFaultCode() {
/* 85 */     return new QName("http://schemas.xmlsoap.org/soap/envelope/", "Server");
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isFault(SOAPElement child) {
/* 90 */     return child.getElementName().equals(getFaultName((String)null));
/*    */   }
/*    */   
/*    */   protected SOAPFault createFaultElement() {
/* 94 */     return (SOAPFault)new Fault1_1Impl(((SOAPDocument)getOwnerDocument()).getDocument(), getPrefix());
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\messaging\saaj\soap\ver1_1\Body1_1Impl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */