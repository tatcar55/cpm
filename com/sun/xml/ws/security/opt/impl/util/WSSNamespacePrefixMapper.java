/*     */ package com.sun.xml.ws.security.opt.impl.util;
/*     */ 
/*     */ import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WSSNamespacePrefixMapper
/*     */   extends NamespacePrefixMapper
/*     */ {
/*     */   private boolean soap12 = false;
/*     */   
/*     */   public WSSNamespacePrefixMapper() {}
/*     */   
/*     */   public WSSNamespacePrefixMapper(boolean soap12) {
/*  58 */     this.soap12 = soap12;
/*     */   }
/*     */   
/*     */   public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
/*  62 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(namespaceUri)) {
/*  63 */       return "wsse";
/*     */     }
/*     */     
/*  66 */     if ("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd".equals(namespaceUri)) {
/*  67 */       return "wsse11";
/*     */     }
/*  69 */     if ("http://www.w3.org/2001/04/xmlenc#".equals(namespaceUri)) {
/*  70 */       return "xenc";
/*     */     }
/*  72 */     if ("http://www.w3.org/2000/09/xmldsig#".equals(namespaceUri)) {
/*  73 */       return "ds";
/*     */     }
/*  75 */     if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd".equals(namespaceUri)) {
/*  76 */       return "wsu";
/*     */     }
/*  78 */     if ("http://schemas.xmlsoap.org/ws/2005/02/sc".equals(namespaceUri)) {
/*  79 */       return "wsc";
/*     */     }
/*  81 */     if ("http://www.w3.org/2001/10/xml-exc-c14n#".equals(namespaceUri)) {
/*  82 */       return "exc14n";
/*     */     }
/*  84 */     if ("http://schemas.xmlsoap.org/soap/envelope/".equals(namespaceUri)) {
/*  85 */       return "S";
/*     */     }
/*     */     
/*  88 */     if ("http://www.w3.org/2003/05/soap-envelope".equals(namespaceUri)) {
/*  89 */       return "S";
/*     */     }
/*  91 */     if ("http://www.w3.org/2001/XMLSchema-instance".equals(namespaceUri)) {
/*  92 */       return "xsi";
/*     */     }
/*  94 */     return null;
/*     */   }
/*     */   
/*     */   public String[] getPreDeclaredNamespaceUris() {
/*  98 */     return new String[0];
/*     */   }
/*     */   
/*     */   public String[] getContextualNamespaceDecls() {
/* 102 */     if (!this.soap12) {
/* 103 */       return new String[] { "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse11", "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "xenc", "http://www.w3.org/2001/04/xmlenc#", "ds", "http://www.w3.org/2000/09/xmldsig#", "wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsc", "http://schemas.xmlsoap.org/ws/2005/02/sc", "exc14n", "http://www.w3.org/2001/10/xml-exc-c14n#", "S", "http://schemas.xmlsoap.org/soap/envelope/" };
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 108 */     return new String[] { "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse11", "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "xenc", "http://www.w3.org/2001/04/xmlenc#", "ds", "http://www.w3.org/2000/09/xmldsig#", "wsu", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsc", "http://schemas.xmlsoap.org/ws/2005/02/sc", "exc14n", "http://www.w3.org/2001/10/xml-exc-c14n#", "S", "http://www.w3.org/2003/05/soap-envelope" };
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\security\opt\imp\\util\WSSNamespacePrefixMapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */