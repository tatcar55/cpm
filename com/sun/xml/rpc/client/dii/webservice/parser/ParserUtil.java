/*     */ package com.sun.xml.rpc.client.dii.webservice.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.client.dii.webservice.WebServicesClientException;
/*     */ import com.sun.xml.rpc.streaming.Attributes;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.util.xml.XmlUtil;
/*     */ import javax.xml.namespace.QName;
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
/*     */   public static String getAttribute(XMLReader reader, String name) {
/*  42 */     Attributes attributes = reader.getAttributes();
/*  43 */     return attributes.getValue(name);
/*     */   }
/*     */   
/*     */   public static String getNonEmptyAttribute(XMLReader reader, String name) {
/*  47 */     String value = getAttribute(reader, name);
/*  48 */     if (value != null && value.equals("")) {
/*  49 */       failWithLocalName("client.invalidAttributeValue", reader, name);
/*     */     }
/*  51 */     return value;
/*     */   }
/*     */   
/*     */   public static String getMandatoryAttribute(XMLReader reader, String name) {
/*  55 */     String value = getAttribute(reader, name);
/*  56 */     if (value == null) {
/*  57 */       failWithLocalName("client.missing.attribute", reader, name);
/*     */     }
/*  59 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMandatoryNonEmptyAttribute(XMLReader reader, String name) {
/*  65 */     String value = getAttribute(reader, name);
/*  66 */     if (value == null) {
/*  67 */       failWithLocalName("client.missing.attribute", reader, name);
/*  68 */     } else if (value.equals("")) {
/*  69 */       failWithLocalName("client.invalidAttributeValue", reader, name);
/*     */     } 
/*  71 */     return value;
/*     */   }
/*     */   
/*     */   public static QName getQNameAttribute(XMLReader reader, String name) {
/*  75 */     String value = getAttribute(reader, name);
/*     */     
/*  77 */     if (value == null) {
/*  78 */       return null;
/*     */     }
/*     */     
/*  81 */     String prefix = XmlUtil.getPrefix(value);
/*  82 */     String uri = "";
/*  83 */     if (prefix != null) {
/*  84 */       uri = reader.getURI(prefix);
/*  85 */       if (uri == null) {
/*  86 */         failWithLocalName("client.invalidAttributeValue", reader, name);
/*     */       }
/*     */     } 
/*  89 */     String localPart = XmlUtil.getLocalPart(value);
/*  90 */     return new QName(uri, localPart);
/*     */   }
/*     */   
/*     */   public static void ensureNoContent(XMLReader reader) {
/*  94 */     if (reader.nextElementContent() != 2) {
/*  95 */       fail("client.unexpectedContent", reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void fail(String key, XMLReader reader) {
/* 100 */     throw new WebServicesClientException(key, Integer.toString(reader.getLineNumber()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void failWithFullName(String key, XMLReader reader) {
/* 106 */     throw new WebServicesClientException(key, new Object[] { Integer.toString(reader.getLineNumber()), reader.getName().toString() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void failWithLocalName(String key, XMLReader reader) {
/* 114 */     throw new WebServicesClientException(key, new Object[] { Integer.toString(reader.getLineNumber()), reader.getLocalName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void failWithLocalName(String key, XMLReader reader, String arg) {
/* 125 */     throw new WebServicesClientException(key, new Object[] { Integer.toString(reader.getLineNumber()), reader.getLocalName(), arg });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\client\dii\webservice\parser\ParserUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */