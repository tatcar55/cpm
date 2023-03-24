/*    */ package com.sun.xml.ws.addressing;
/*    */ 
/*    */ import com.sun.xml.stream.buffer.XMLStreamBuffer;
/*    */ import com.sun.xml.ws.api.addressing.WSEndpointReference;
/*    */ import javax.xml.namespace.QName;
/*    */ import javax.xml.stream.XMLStreamException;
/*    */ import javax.xml.stream.XMLStreamReader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WSEPRExtension
/*    */   extends WSEndpointReference.EPRExtension
/*    */ {
/*    */   XMLStreamBuffer xsb;
/*    */   final QName qname;
/*    */   
/*    */   public WSEPRExtension(XMLStreamBuffer xsb, QName qname) {
/* 60 */     this.xsb = xsb;
/* 61 */     this.qname = qname;
/*    */   }
/*    */ 
/*    */   
/*    */   public XMLStreamReader readAsXMLStreamReader() throws XMLStreamException {
/* 66 */     return (XMLStreamReader)this.xsb.readAsXMLStreamReader();
/*    */   }
/*    */   
/*    */   public QName getQName() {
/* 70 */     return this.qname;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\addressing\WSEPRExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */