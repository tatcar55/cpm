/*    */ package com.sun.xml.rpc.soap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SOAPConstantsFactory
/*    */ {
/* 34 */   private static final SOAPConstantsFactory factory = new SOAPConstantsFactory(); private static SOAPNamespaceConstants namespaceConstants11; private static SOAPWSDLConstants wsdlConstants11;
/*    */   private static SOAPEncodingConstants encodingConstants11;
/*    */   
/*    */   private SOAPConstantsFactory() {
/* 38 */     namespaceConstants11 = new SOAPNamespaceConstantsImpl(SOAPVersion.SOAP_11);
/*    */     
/* 40 */     wsdlConstants11 = new SOAPWSDLConstantsImpl(SOAPVersion.SOAP_11);
/* 41 */     encodingConstants11 = new SOAPEncodingConstantsImpl(SOAPVersion.SOAP_11);
/*    */ 
/*    */     
/* 44 */     namespaceConstants12 = new SOAPNamespaceConstantsImpl(SOAPVersion.SOAP_12);
/*    */     
/* 46 */     wsdlConstants12 = new SOAPWSDLConstantsImpl(SOAPVersion.SOAP_12);
/* 47 */     encodingConstants12 = new SOAPEncodingConstantsImpl(SOAPVersion.SOAP_12);
/*    */   }
/*    */   private static SOAPNamespaceConstants namespaceConstants12; private static SOAPWSDLConstants wsdlConstants12; private static SOAPEncodingConstants encodingConstants12;
/*    */   
/*    */   public static SOAPNamespaceConstants getSOAPNamespaceConstants(SOAPVersion ver) {
/* 52 */     if (ver == SOAPVersion.SOAP_11)
/* 53 */       return namespaceConstants11; 
/* 54 */     if (ver == SOAPVersion.SOAP_12)
/* 55 */       return namespaceConstants12; 
/* 56 */     return null;
/*    */   }
/*    */   
/*    */   public static SOAPWSDLConstants getSOAPWSDLConstants(SOAPVersion ver) {
/* 60 */     if (ver == SOAPVersion.SOAP_11)
/* 61 */       return wsdlConstants11; 
/* 62 */     if (ver == SOAPVersion.SOAP_12)
/* 63 */       return wsdlConstants12; 
/* 64 */     return null;
/*    */   }
/*    */   
/*    */   public static SOAPEncodingConstants getSOAPEncodingConstants(SOAPVersion ver) {
/* 68 */     if (ver == SOAPVersion.SOAP_11)
/* 69 */       return encodingConstants11; 
/* 70 */     if (ver == SOAPVersion.SOAP_12)
/* 71 */       return encodingConstants12; 
/* 72 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\soap\SOAPConstantsFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */