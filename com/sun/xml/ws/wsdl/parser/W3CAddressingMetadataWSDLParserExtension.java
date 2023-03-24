/*    */ package com.sun.xml.ws.wsdl.parser;
/*    */ 
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundOperation;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLBoundPortType;
/*    */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*    */ import com.sun.xml.ws.model.wsdl.WSDLBoundPortTypeImpl;
/*    */ import javax.xml.namespace.QName;
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
/*    */ 
/*    */ public class W3CAddressingMetadataWSDLParserExtension
/*    */   extends W3CAddressingWSDLParserExtension
/*    */ {
/* 58 */   String METADATA_WSDL_EXTN_NS = "http://www.w3.org/2007/05/addressing/metadata";
/* 59 */   QName METADATA_WSDL_ACTION_TAG = new QName(this.METADATA_WSDL_EXTN_NS, "Action", "wsam");
/*    */   
/*    */   public boolean bindingElements(WSDLBoundPortType binding, XMLStreamReader reader) {
/* 62 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean portElements(WSDLPort port, XMLStreamReader reader) {
/* 67 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean bindingOperationElements(WSDLBoundOperation operation, XMLStreamReader reader) {
/* 72 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void patchAnonymousDefault(WSDLBoundPortTypeImpl binding) {}
/*    */ 
/*    */   
/*    */   protected String getNamespaceURI() {
/* 81 */     return this.METADATA_WSDL_EXTN_NS;
/*    */   }
/*    */ 
/*    */   
/*    */   protected QName getWsdlActionTag() {
/* 86 */     return this.METADATA_WSDL_ACTION_TAG;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\parser\W3CAddressingMetadataWSDLParserExtension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */