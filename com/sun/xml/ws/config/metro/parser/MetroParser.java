/*     */ package com.sun.xml.ws.config.metro.parser;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.ResourceLoader;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.stream.XMLInputFactory;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import javax.xml.stream.XMLStreamReader;
/*     */ import javax.xml.ws.WebServiceException;
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
/*     */ public class MetroParser
/*     */ {
/*  63 */   private static final Logger LOGGER = Logger.getLogger(MetroParser.class);
/*     */   
/*     */   private static final String SERVLET_CONTEXT_CLASSNAME = "javax.servlet.ServletContext";
/*     */   
/*     */   private static final String JAR_PREFIX = "META-INF/";
/*     */   
/*     */   private static final String WAR_PREFIX = "/WEB-INF/";
/*     */   
/*     */   private static final String WEBSERVICES_NAME = "webservices.xml";
/*     */   private static final String METRO_WEBSERVICES_NAME = "metro-webservices.xml";
/*  73 */   private static final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void parse(Container container) {
/*  84 */     findConfigFile("metro-webservices.xml", container);
/*     */     
/*  86 */     findConfigFile("webservices.xml", container);
/*     */   }
/*     */ 
/*     */   
/*     */   private static URL findConfigFile(String configFileName, Container container) throws WebServiceException {
/*  91 */     URL configFileUrl = null;
/*     */     try {
/*  93 */       ResourceLoader resourceLoader = (container != null) ? (ResourceLoader)container.getSPI(ResourceLoader.class) : null;
/*  94 */       if (resourceLoader != null) {
/*  95 */         configFileUrl = resourceLoader.getResource(configFileName);
/*     */       }
/*     */       
/*  98 */       if (configFileUrl == null && container != null) {
/*     */         
/*     */         try {
/* 101 */           Class<?> contextClass = Class.forName("javax.servlet.ServletContext");
/* 102 */           Object context = container.getSPI(contextClass);
/* 103 */           if (context != null)
/*     */           {
/* 105 */             configFileUrl = PolicyUtils.ConfigFile.loadFromContext("/WEB-INF/" + configFileName, context);
/*     */           }
/* 107 */         } catch (ClassNotFoundException e) {
/* 108 */           if (LOGGER.isLoggable(Level.FINE))
/*     */           {
/* 110 */             LOGGER.fine("Cannot find servlet context");
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 116 */       if (configFileUrl == null) {
/*     */         
/* 118 */         StringBuilder examinedPath = (new StringBuilder("META-INF/")).append(configFileName);
/* 119 */         configFileUrl = PolicyUtils.ConfigFile.loadFromClasspath(examinedPath.toString());
/*     */       } 
/*     */       
/* 122 */       return configFileUrl;
/* 123 */     } catch (MalformedURLException e) {
/*     */       
/* 125 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Failed to load file", e));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static XMLStreamReader urlToReader(URL url) throws WebServiceException {
/*     */     try {
/* 131 */       return inputFactory.createXMLStreamReader(url.openStream());
/* 132 */     } catch (IOException e) {
/*     */       
/* 134 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Failed to load URL", e));
/* 135 */     } catch (XMLStreamException e) {
/*     */       
/* 137 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException("Failed to load URL", e));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\config\metro\parser\MetroParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */