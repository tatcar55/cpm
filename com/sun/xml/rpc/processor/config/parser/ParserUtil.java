/*     */ package com.sun.xml.rpc.processor.config.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.ConfigurationException;
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
/*     */ 
/*     */ public class ParserUtil
/*     */ {
/*     */   public static String getAttribute(XMLReader reader, String name) {
/*  43 */     Attributes attributes = reader.getAttributes();
/*  44 */     return attributes.getValue(name);
/*     */   }
/*     */   
/*     */   public static String getNonEmptyAttribute(XMLReader reader, String name) {
/*  48 */     String value = getAttribute(reader, name);
/*  49 */     if (value != null && value.equals("")) {
/*  50 */       failWithLocalName("configuration.invalidAttributeValue", reader, name);
/*     */     }
/*     */     
/*  53 */     return value;
/*     */   }
/*     */   
/*     */   public static String getMandatoryAttribute(XMLReader reader, String name) {
/*  57 */     String value = getAttribute(reader, name);
/*  58 */     if (value == null) {
/*  59 */       failWithLocalName("configuration.missing.attribute", reader, name);
/*     */     }
/*  61 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMandatoryNonEmptyAttribute(XMLReader reader, String name) {
/*  67 */     String value = getAttribute(reader, name);
/*  68 */     if (value == null) {
/*  69 */       failWithLocalName("configuration.missing.attribute", reader, name);
/*     */     }
/*  71 */     else if (value.equals("")) {
/*  72 */       failWithLocalName("configuration.invalidAttributeValue", reader, name);
/*     */     } 
/*     */     
/*  75 */     return value;
/*     */   }
/*     */   
/*     */   public static QName getQNameAttribute(XMLReader reader, String name) {
/*  79 */     String value = getAttribute(reader, name);
/*     */     
/*  81 */     if (value == null) {
/*  82 */       return null;
/*     */     }
/*     */     
/*  85 */     String prefix = XmlUtil.getPrefix(value);
/*  86 */     String uri = "";
/*  87 */     if (prefix != null) {
/*  88 */       uri = reader.getURI(prefix);
/*  89 */       if (uri == null) {
/*  90 */         failWithLocalName("configuration.invalidAttributeValue", reader, name);
/*     */       }
/*     */     } 
/*     */     
/*  94 */     String localPart = XmlUtil.getLocalPart(value);
/*  95 */     return new QName(uri, localPart);
/*     */   }
/*     */   
/*     */   public static void ensureNoContent(XMLReader reader) {
/*  99 */     if (reader.nextElementContent() != 2) {
/* 100 */       fail("configuration.unexpectedContent", reader);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void fail(String key, XMLReader reader) {
/* 105 */     throw new ConfigurationException(key, Integer.toString(reader.getLineNumber()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void failWithFullName(String key, XMLReader reader) {
/* 110 */     throw new ConfigurationException(key, new Object[] { Integer.toString(reader.getLineNumber()), reader.getName().toString() });
/*     */   }
/*     */ 
/*     */   
/*     */   public static void failWithLocalName(String key, XMLReader reader) {
/* 115 */     throw new ConfigurationException(key, new Object[] { Integer.toString(reader.getLineNumber()), reader.getLocalName() });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void failWithLocalName(String key, XMLReader reader, String arg) {
/* 122 */     throw new ConfigurationException(key, new Object[] { Integer.toString(reader.getLineNumber()), reader.getLocalName(), arg });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\parser\ParserUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */