/*     */ package com.sun.xml.ws.wsdl.parser;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.Nullable;
/*     */ import com.sun.xml.ws.util.xml.XmlUtil;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.stream.XMLStreamReader;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ParserUtil
/*     */ {
/*     */   public static String getAttribute(XMLStreamReader reader, String name) {
/*  65 */     return reader.getAttributeValue(null, name);
/*     */   }
/*     */   
/*     */   public static String getAttribute(XMLStreamReader reader, String nsUri, String name) {
/*  69 */     return reader.getAttributeValue(nsUri, name);
/*     */   }
/*     */   
/*     */   public static String getAttribute(XMLStreamReader reader, QName name) {
/*  73 */     return reader.getAttributeValue(name.getNamespaceURI(), name.getLocalPart());
/*     */   }
/*     */   
/*     */   public static QName getQName(XMLStreamReader reader, String tag) {
/*  77 */     String localName = XmlUtil.getLocalPart(tag);
/*  78 */     String pfix = XmlUtil.getPrefix(tag);
/*  79 */     String uri = reader.getNamespaceURI(fixNull(pfix));
/*  80 */     return new QName(uri, localName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMandatoryNonEmptyAttribute(XMLStreamReader reader, String name) {
/*  86 */     String value = reader.getAttributeValue(null, name);
/*     */     
/*  88 */     if (value == null) {
/*  89 */       failWithLocalName("client.missing.attribute", reader, name);
/*  90 */     } else if (value.equals("")) {
/*  91 */       failWithLocalName("client.invalidAttributeValue", reader, name);
/*     */     } 
/*     */     
/*  94 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void failWithFullName(String key, XMLStreamReader reader) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void failWithLocalName(String key, XMLStreamReader reader) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void failWithLocalName(String key, XMLStreamReader reader, String arg) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static String fixNull(@Nullable String s) {
/* 121 */     if (s == null) return ""; 
/* 122 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\wsdl\parser\ParserUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */