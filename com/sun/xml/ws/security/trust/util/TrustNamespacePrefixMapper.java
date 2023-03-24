/*    */ package com.sun.xml.ws.security.trust.util;
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
/*    */ public class TrustNamespacePrefixMapper
/*    */   extends NamespacePrefixMapper
/*    */ {
/*    */   public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
/* 50 */     if ("http://www.w3.org/2001/XMLSchema-instance".equals(namespaceUri)) {
/* 51 */       return "xsi";
/*    */     }
/*    */ 
/*    */     
/* 55 */     if ("http://schemas.xmlsoap.org/ws/2005/02/trust".equals(namespaceUri)) {
/* 56 */       return "wst";
/*    */     }
/*    */     
/* 59 */     if ("http://docs.oasis-open.org/ws-sx/ws-trust/200512".equals(namespaceUri)) {
/* 60 */       return "trust";
/*    */     }
/*    */     
/* 63 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".equals(namespaceUri)) {
/* 64 */       return "wsu";
/*    */     }
/*    */     
/* 67 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(namespaceUri)) {
/* 68 */       return "wsse";
/*    */     }
/*    */     
/* 71 */     if ("http://schemas.xmlsoap.org/ws/2005/02/sc".equals(namespaceUri)) {
/* 72 */       return "wssc";
/*    */     }
/*    */     
/* 75 */     if ("http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512".equals(namespaceUri)) {
/* 76 */       return "sc";
/*    */     }
/*    */     
/* 79 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(namespaceUri)) {
/* 80 */       return "wsse";
/*    */     }
/*    */     
/* 83 */     if ("http://schemas.xmlsoap.org/ws/2004/09/policy".equals(namespaceUri)) {
/* 84 */       return "wsp";
/*    */     }
/*    */     
/* 87 */     if ("http://www.w3.org/2005/08/addressing".equals(namespaceUri)) {
/* 88 */       return "wsa";
/*    */     }
/*    */ 
/*    */     
/* 92 */     return suggestion;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\trus\\util\TrustNamespacePrefixMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */