/*    */ package com.sun.xml.ws.message;
/*    */ 
/*    */ import com.sun.xml.ws.encoding.soap.SOAP12Constants;
/*    */ import com.sun.xml.ws.encoding.soap.SOAPConstants;
/*    */ import javax.xml.namespace.QName;
/*    */ import org.xml.sax.Attributes;
/*    */ import org.xml.sax.SAXException;
/*    */ import org.xml.sax.helpers.DefaultHandler;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PayloadElementSniffer
/*    */   extends DefaultHandler
/*    */ {
/*    */   private boolean bodyStarted;
/*    */   private QName payloadQName;
/*    */   
/*    */   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
/* 68 */     if (this.bodyStarted) {
/* 69 */       this.payloadQName = new QName(uri, localName);
/*    */       
/* 71 */       throw new SAXException("Payload element found, interrupting the parsing process.");
/*    */     } 
/*    */ 
/*    */     
/* 75 */     if (equalsQName(uri, localName, SOAPConstants.QNAME_SOAP_BODY) || equalsQName(uri, localName, SOAP12Constants.QNAME_SOAP_BODY))
/*    */     {
/* 77 */       this.bodyStarted = true;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean equalsQName(String uri, String localName, QName qname) {
/* 83 */     return (qname.getLocalPart().equals(localName) && qname.getNamespaceURI().equals(uri));
/*    */   }
/*    */ 
/*    */   
/*    */   public QName getPayloadQName() {
/* 88 */     return this.payloadQName;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\message\PayloadElementSniffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */