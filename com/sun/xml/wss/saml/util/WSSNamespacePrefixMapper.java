/*    */ package com.sun.xml.wss.saml.util;
/*    */ 
/*    */ import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WSSNamespacePrefixMapper
/*    */   extends NamespacePrefixMapper
/*    */ {
/*    */   private boolean soap12;
/*    */   
/*    */   public WSSNamespacePrefixMapper() {}
/*    */   
/*    */   public WSSNamespacePrefixMapper(boolean soap12) {
/* 56 */     this.soap12 = soap12;
/*    */   }
/*    */   
/*    */   public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
/* 60 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(namespaceUri)) {
/* 61 */       return "wsse";
/*    */     }
/*    */     
/* 64 */     if ("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd".equals(namespaceUri)) {
/* 65 */       return "wsse11";
/*    */     }
/* 67 */     if ("http://www.w3.org/2001/04/xmlenc#".equals(namespaceUri)) {
/* 68 */       return "xenc";
/*    */     }
/* 70 */     if ("http://www.w3.org/2000/09/xmldsig#".equals(namespaceUri)) {
/* 71 */       return "ds";
/*    */     }
/* 73 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".equals(namespaceUri)) {
/* 74 */       return "wsu";
/*    */     }
/* 76 */     if ("http://schemas.xmlsoap.org/ws/2005/02/sc".equals(namespaceUri)) {
/* 77 */       return "wsc";
/*    */     }
/* 79 */     if ("urn:oasis:names:tc:SAML:1.0:assertion".equals(namespaceUri)) {
/* 80 */       return "saml";
/*    */     }
/* 82 */     if ("urn:oasis:names:tc:SAML:2.0:assertion".equals(namespaceUri)) {
/* 83 */       return "saml2";
/*    */     }
/* 85 */     if ("http://www.w3.org/2001/10/xml-exc-c14n#".equals(namespaceUri)) {
/* 86 */       return "exc14n";
/*    */     }
/*    */     
/* 89 */     return null;
/*    */   }
/*    */   
/*    */   public String[] getPreDeclaredNamespaceUris() {
/* 93 */     return new String[0];
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\sam\\util\WSSNamespacePrefixMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */