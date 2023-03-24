/*     */ package com.sun.xml.wss;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.parsers.SAXParserFactory;
/*     */ import javax.xml.transform.TransformerConfigurationException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.validation.SchemaFactory;
/*     */ import javax.xml.xpath.XPathFactory;
/*     */ import javax.xml.xpath.XPathFactoryConfigurationException;
/*     */ import org.xml.sax.SAXNotRecognizedException;
/*     */ import org.xml.sax.SAXNotSupportedException;
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
/*     */ public class WSITXMLFactory
/*     */ {
/*  67 */   public static final boolean DISABLE_SECURE_PROCESSING = Boolean.parseBoolean(System.getProperty("com.sun.xml.ws.disableXmlSecurity"));
/*     */ 
/*     */   
/*     */   private static boolean xmlFeatureValue(boolean runtimeSetting) {
/*  71 */     return (!DISABLE_SECURE_PROCESSING && (DISABLE_SECURE_PROCESSING || !runtimeSetting));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final SchemaFactory createSchemaFactory(String language, boolean disableSecureProcessing) throws IllegalArgumentException {
/*     */     try {
/*  81 */       SchemaFactory factory = SchemaFactory.newInstance(language);
/*  82 */       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", xmlFeatureValue(disableSecureProcessing));
/*  83 */       return factory;
/*  84 */     } catch (SAXNotRecognizedException ex) {
/*  85 */       Logger.getLogger(WSITXMLFactory.class.getName()).log(Level.SEVERE, (String)null, ex);
/*  86 */       throw new IllegalStateException(ex);
/*  87 */     } catch (SAXNotSupportedException ex) {
/*  88 */       Logger.getLogger(WSITXMLFactory.class.getName()).log(Level.SEVERE, (String)null, ex);
/*  89 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final SAXParserFactory createParserFactory(boolean disableSecureProcessing) throws IllegalArgumentException {
/*     */     try {
/* 100 */       SAXParserFactory factory = SAXParserFactory.newInstance();
/* 101 */       factory.setNamespaceAware(true);
/* 102 */       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", xmlFeatureValue(disableSecureProcessing));
/* 103 */       return factory;
/* 104 */     } catch (ParserConfigurationException ex) {
/* 105 */       Logger.getLogger(WSITXMLFactory.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 106 */       throw new IllegalStateException(ex);
/* 107 */     } catch (SAXNotRecognizedException ex) {
/* 108 */       Logger.getLogger(WSITXMLFactory.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 109 */       throw new IllegalStateException(ex);
/* 110 */     } catch (SAXNotSupportedException ex) {
/* 111 */       Logger.getLogger(WSITXMLFactory.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 112 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final XPathFactory createXPathFactory(boolean disableSecureProcessing) throws IllegalArgumentException {
/*     */     try {
/* 122 */       XPathFactory factory = XPathFactory.newInstance();
/* 123 */       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", xmlFeatureValue(disableSecureProcessing));
/* 124 */       return factory;
/* 125 */     } catch (XPathFactoryConfigurationException ex) {
/* 126 */       Logger.getLogger(WSITXMLFactory.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 127 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final TransformerFactory createTransformerFactory(boolean disableSecureProcessing) throws IllegalArgumentException {
/*     */     try {
/* 137 */       TransformerFactory factory = TransformerFactory.newInstance();
/* 138 */       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", xmlFeatureValue(disableSecureProcessing));
/* 139 */       return factory;
/* 140 */     } catch (TransformerConfigurationException ex) {
/* 141 */       Logger.getLogger(WSITXMLFactory.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 142 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final DocumentBuilderFactory createDocumentBuilderFactory(boolean disableSecureProcessing) throws IllegalStateException {
/*     */     try {
/* 153 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 154 */       factory.setNamespaceAware(true);
/* 155 */       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", xmlFeatureValue(disableSecureProcessing));
/* 156 */       return factory;
/* 157 */     } catch (ParserConfigurationException ex) {
/* 158 */       Logger.getLogger(WSITXMLFactory.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 159 */       throw new IllegalStateException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\WSITXMLFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */