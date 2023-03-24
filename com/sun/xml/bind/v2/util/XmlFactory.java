/*     */ package com.sun.xml.bind.v2.util;
/*     */ 
/*     */ import com.sun.xml.bind.Util;
/*     */ import com.sun.xml.bind.v2.Messages;
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
/*     */ public class XmlFactory
/*     */ {
/*  66 */   private static final Logger LOGGER = Logger.getLogger(XmlFactory.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String DISABLE_XML_SECURITY = "com.sun.xml.bind.disableXmlSecurity";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  77 */   public static final boolean DISABLE_SECURE_PROCESSING = Boolean.parseBoolean(Util.getSystemProperty("com.sun.xml.bind.disableXmlSecurity"));
/*     */ 
/*     */   
/*     */   private static boolean xmlFeatureValue(boolean runtimeSetting) {
/*  81 */     return (!DISABLE_SECURE_PROCESSING && !runtimeSetting);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SchemaFactory createSchemaFactory(String language, boolean disableSecureProcessing) throws IllegalStateException {
/*     */     try {
/*  91 */       SchemaFactory factory = SchemaFactory.newInstance(language);
/*  92 */       if (LOGGER.isLoggable(Level.FINE)) {
/*  93 */         LOGGER.log(Level.FINE, "SchemaFactory instance: {0}", factory);
/*     */       }
/*  95 */       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", xmlFeatureValue(disableSecureProcessing));
/*  96 */       return factory;
/*  97 */     } catch (SAXNotRecognizedException ex) {
/*  98 */       LOGGER.log(Level.SEVERE, (String)null, ex);
/*  99 */       throw new IllegalStateException(ex);
/* 100 */     } catch (SAXNotSupportedException ex) {
/* 101 */       LOGGER.log(Level.SEVERE, (String)null, ex);
/* 102 */       throw new IllegalStateException(ex);
/* 103 */     } catch (AbstractMethodError er) {
/* 104 */       LOGGER.log(Level.SEVERE, (String)null, er);
/* 105 */       throw new IllegalStateException(Messages.INVALID_JAXP_IMPLEMENTATION.format(new Object[0]), er);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SAXParserFactory createParserFactory(boolean disableSecureProcessing) throws IllegalStateException {
/*     */     try {
/* 116 */       SAXParserFactory factory = SAXParserFactory.newInstance();
/* 117 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 118 */         LOGGER.log(Level.FINE, "SAXParserFactory instance: {0}", factory);
/*     */       }
/* 120 */       factory.setNamespaceAware(true);
/* 121 */       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", xmlFeatureValue(disableSecureProcessing));
/* 122 */       return factory;
/* 123 */     } catch (ParserConfigurationException ex) {
/* 124 */       LOGGER.log(Level.SEVERE, (String)null, ex);
/* 125 */       throw new IllegalStateException(ex);
/* 126 */     } catch (SAXNotRecognizedException ex) {
/* 127 */       LOGGER.log(Level.SEVERE, (String)null, ex);
/* 128 */       throw new IllegalStateException(ex);
/* 129 */     } catch (SAXNotSupportedException ex) {
/* 130 */       LOGGER.log(Level.SEVERE, (String)null, ex);
/* 131 */       throw new IllegalStateException(ex);
/* 132 */     } catch (AbstractMethodError er) {
/* 133 */       LOGGER.log(Level.SEVERE, (String)null, er);
/* 134 */       throw new IllegalStateException(Messages.INVALID_JAXP_IMPLEMENTATION.format(new Object[0]), er);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static XPathFactory createXPathFactory(boolean disableSecureProcessing) throws IllegalStateException {
/*     */     try {
/* 144 */       XPathFactory factory = XPathFactory.newInstance();
/* 145 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 146 */         LOGGER.log(Level.FINE, "XPathFactory instance: {0}", factory);
/*     */       }
/* 148 */       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", xmlFeatureValue(disableSecureProcessing));
/* 149 */       return factory;
/* 150 */     } catch (XPathFactoryConfigurationException ex) {
/* 151 */       LOGGER.log(Level.SEVERE, (String)null, ex);
/* 152 */       throw new IllegalStateException(ex);
/* 153 */     } catch (AbstractMethodError er) {
/* 154 */       LOGGER.log(Level.SEVERE, (String)null, er);
/* 155 */       throw new IllegalStateException(Messages.INVALID_JAXP_IMPLEMENTATION.format(new Object[0]), er);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TransformerFactory createTransformerFactory(boolean disableSecureProcessing) throws IllegalStateException {
/*     */     try {
/* 165 */       TransformerFactory factory = TransformerFactory.newInstance();
/* 166 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 167 */         LOGGER.log(Level.FINE, "TransformerFactory instance: {0}", factory);
/*     */       }
/* 169 */       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", xmlFeatureValue(disableSecureProcessing));
/* 170 */       return factory;
/* 171 */     } catch (TransformerConfigurationException ex) {
/* 172 */       LOGGER.log(Level.SEVERE, (String)null, ex);
/* 173 */       throw new IllegalStateException(ex);
/* 174 */     } catch (AbstractMethodError er) {
/* 175 */       LOGGER.log(Level.SEVERE, (String)null, er);
/* 176 */       throw new IllegalStateException(Messages.INVALID_JAXP_IMPLEMENTATION.format(new Object[0]), er);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DocumentBuilderFactory createDocumentBuilderFactory(boolean disableSecureProcessing) throws IllegalStateException {
/*     */     try {
/* 187 */       DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
/* 188 */       if (LOGGER.isLoggable(Level.FINE)) {
/* 189 */         LOGGER.log(Level.FINE, "DocumentBuilderFactory instance: {0}", factory);
/*     */       }
/* 191 */       factory.setNamespaceAware(true);
/* 192 */       factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", xmlFeatureValue(disableSecureProcessing));
/* 193 */       return factory;
/* 194 */     } catch (ParserConfigurationException ex) {
/* 195 */       LOGGER.log(Level.SEVERE, (String)null, ex);
/* 196 */       throw new IllegalStateException(ex);
/* 197 */     } catch (AbstractMethodError er) {
/* 198 */       LOGGER.log(Level.SEVERE, (String)null, er);
/* 199 */       throw new IllegalStateException(Messages.INVALID_JAXP_IMPLEMENTATION.format(new Object[0]), er);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v\\util\XmlFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */