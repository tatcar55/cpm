/*     */ package com.sun.xml.ws.policy.parser;
/*     */ 
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.ResourceLoader;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLModel;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.policy.PolicyException;
/*     */ import com.sun.xml.ws.policy.PolicyMap;
/*     */ import com.sun.xml.ws.policy.PolicyMapMutator;
/*     */ import com.sun.xml.ws.policy.localization.LocalizationMessages;
/*     */ import com.sun.xml.ws.policy.privateutil.PolicyUtils;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.stream.XMLStreamException;
/*     */ import org.xml.sax.SAXException;
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
/*     */ public final class PolicyConfigParser
/*     */ {
/*  70 */   private static final Logger LOGGER = Logger.getLogger(PolicyConfigParser.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String SERVLET_CONTEXT_CLASSNAME = "javax.servlet.ServletContext";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String JAR_PREFIX = "META-INF/";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String WAR_PREFIX = "/WEB-INF/";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PolicyMap parse(String configFileIdentifier, Container container, PolicyMapMutator... mutators) throws PolicyException {
/* 105 */     LOGGER.entering(new Object[] { configFileIdentifier, container, mutators });
/* 106 */     PolicyMap map = null;
/*     */     try {
/* 108 */       WSDLModel configModel = parseModel(configFileIdentifier, container, mutators);
/*     */       
/* 110 */       if (configModel != null)
/* 111 */         map = configModel.getPolicyMap(); 
/* 112 */       return map;
/*     */     } finally {
/* 114 */       LOGGER.exiting(map);
/*     */     } 
/*     */   }
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
/*     */   public static PolicyMap parse(URL configFileUrl, boolean isClient, PolicyMapMutator... mutators) throws PolicyException, IllegalArgumentException {
/* 137 */     LOGGER.entering(new Object[] { configFileUrl, Boolean.valueOf(isClient), mutators });
/* 138 */     PolicyMap map = null;
/*     */     try {
/* 140 */       return map = parseModel(configFileUrl, isClient, mutators).getPolicyMap();
/*     */     } finally {
/* 142 */       LOGGER.exiting(map);
/*     */     } 
/*     */   }
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
/*     */   public static WSDLModel parseModel(String configFileIdentifier, Container container, PolicyMapMutator... mutators) throws PolicyException {
/*     */     WSDLModel model;
/* 186 */     LOGGER.entering(new Object[] { configFileIdentifier, container, mutators });
/* 187 */     URL configFileUrl = findConfigFile(configFileIdentifier, container);
/*     */     
/* 189 */     if (configFileUrl != null) {
/* 190 */       model = parseModel(configFileUrl, "client".equals(configFileIdentifier), mutators);
/* 191 */       LOGGER.info(LocalizationMessages.WSP_5018_LOADED_WSIT_CFG_FILE(configFileUrl.toExternalForm()));
/*     */     } else {
/*     */       
/* 194 */       model = null;
/*     */     } 
/* 196 */     LOGGER.exiting(model);
/* 197 */     return model;
/*     */   }
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
/*     */   public static WSDLModel parseModel(URL configFileUrl, boolean isClient, PolicyMapMutator... mutators) throws PolicyException, IllegalArgumentException {
/* 220 */     LOGGER.entering(new Object[] { configFileUrl, Boolean.valueOf(isClient), mutators });
/* 221 */     WSDLModel model = null;
/*     */     try {
/* 223 */       if (null == configFileUrl) {
/* 224 */         throw (IllegalArgumentException)LOGGER.logSevereException(new IllegalArgumentException(LocalizationMessages.WSP_5007_FAILED_TO_READ_NULL_WSIT_CFG()));
/*     */       }
/*     */ 
/*     */       
/* 228 */       return PolicyResourceLoader.getWsdlModel(configFileUrl, isClient);
/* 229 */     } catch (XMLStreamException ex) {
/* 230 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_5001_WSIT_CFG_FILE_PROCESSING_FAILED(configFileUrl.toString()), ex));
/*     */     }
/* 232 */     catch (IOException ex) {
/* 233 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_5001_WSIT_CFG_FILE_PROCESSING_FAILED(configFileUrl.toString()), ex));
/*     */     }
/* 235 */     catch (SAXException ex) {
/* 236 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_5001_WSIT_CFG_FILE_PROCESSING_FAILED(configFileUrl.toString()), ex));
/*     */     } finally {
/*     */       
/* 239 */       LOGGER.exiting(model);
/*     */     } 
/*     */   }
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
/*     */   public static URL findConfigFile(String configFileIdentifier, Container container) throws PolicyException {
/*     */     URL configFileUrl;
/* 275 */     String configFileName = PolicyUtils.ConfigFile.generateFullName(configFileIdentifier);
/* 276 */     if (LOGGER.isLoggable(Level.FINEST)) {
/* 277 */       LOGGER.finest(LocalizationMessages.WSP_5011_CONFIG_FILE_IS(configFileName));
/*     */     }
/*     */     
/* 280 */     WsitConfigResourceLoader loader = new WsitConfigResourceLoader(container);
/*     */     try {
/* 282 */       configFileUrl = loader.getResource(configFileName);
/* 283 */     } catch (MalformedURLException e) {
/* 284 */       throw (PolicyException)LOGGER.logSevereException(new PolicyException(LocalizationMessages.WSP_5021_FAILED_RESOURCE_FROM_LOADER(configFileName, loader), e));
/*     */     } 
/*     */     
/* 287 */     return configFileUrl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class WsitConfigResourceLoader
/*     */     extends ResourceLoader
/*     */   {
/*     */     Container container;
/*     */ 
/*     */     
/*     */     ResourceLoader parentLoader;
/*     */ 
/*     */ 
/*     */     
/*     */     WsitConfigResourceLoader(ResourceLoader parentLoader) {
/* 304 */       this.parentLoader = parentLoader;
/*     */     }
/*     */     
/*     */     WsitConfigResourceLoader(Container container) {
/* 308 */       this((container != null) ? (ResourceLoader)container.getSPI(ResourceLoader.class) : null);
/* 309 */       this.container = container;
/*     */     }
/*     */ 
/*     */     
/*     */     public URL getResource(String resource) throws MalformedURLException {
/* 314 */       PolicyConfigParser.LOGGER.entering(new Object[] { resource });
/* 315 */       URL resourceUrl = null;
/*     */       
/*     */       try {
/* 318 */         if (this.parentLoader != null) {
/* 319 */           if (PolicyConfigParser.LOGGER.isLoggable(Level.FINE)) {
/* 320 */             PolicyConfigParser.LOGGER.fine(LocalizationMessages.WSP_5020_RESOURCE_FROM_LOADER(resource, this.parentLoader));
/*     */           }
/*     */           
/* 323 */           resourceUrl = this.parentLoader.getResource(resource);
/*     */         } 
/*     */         
/* 326 */         if (resourceUrl == null && this.container != null) {
/*     */           
/* 328 */           Object context = null;
/*     */           try {
/* 330 */             Class<?> contextClass = Class.forName("javax.servlet.ServletContext");
/* 331 */             context = this.container.getSPI(contextClass);
/* 332 */             if (context != null) {
/* 333 */               if (PolicyConfigParser.LOGGER.isLoggable(Level.FINE)) {
/* 334 */                 PolicyConfigParser.LOGGER.fine(LocalizationMessages.WSP_5022_RESOURCE_FROM_CONTEXT(resource, context));
/*     */               }
/* 336 */               resourceUrl = PolicyUtils.ConfigFile.loadFromContext("/WEB-INF/" + resource, context);
/*     */             } 
/* 338 */           } catch (ClassNotFoundException e) {
/* 339 */             if (PolicyConfigParser.LOGGER.isLoggable(Level.FINE)) {
/* 340 */               PolicyConfigParser.LOGGER.fine(LocalizationMessages.WSP_5016_CAN_NOT_FIND_CLASS("javax.servlet.ServletContext"));
/*     */             }
/*     */           } 
/* 343 */           if (PolicyConfigParser.LOGGER.isLoggable(Level.FINEST)) {
/* 344 */             PolicyConfigParser.LOGGER.finest(LocalizationMessages.WSP_5010_CONTEXT_IS(context));
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 349 */         if (resourceUrl == null) {
/*     */           
/* 351 */           StringBuilder examinedPath = (new StringBuilder("META-INF/")).append(resource);
/* 352 */           resourceUrl = PolicyUtils.ConfigFile.loadFromClasspath(examinedPath.toString());
/*     */ 
/*     */           
/* 355 */           if (resourceUrl == null && isClientConfig(resource)) {
/* 356 */             examinedPath.append(File.pathSeparator).append(resource);
/* 357 */             resourceUrl = PolicyUtils.ConfigFile.loadFromClasspath(resource);
/*     */           } 
/* 359 */           if (resourceUrl == null && PolicyConfigParser.LOGGER.isLoggable(Level.CONFIG)) {
/* 360 */             PolicyConfigParser.LOGGER.config(LocalizationMessages.WSP_5009_COULD_NOT_LOCATE_WSIT_CFG_FILE(resource, examinedPath));
/*     */           }
/*     */         } 
/*     */         
/* 364 */         return resourceUrl;
/*     */       } finally {
/* 366 */         PolicyConfigParser.LOGGER.exiting(resourceUrl);
/*     */       } 
/*     */     }
/*     */     
/*     */     private boolean isClientConfig(String resource) {
/* 371 */       return "wsit-client.xml".equals(resource);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\policy\parser\PolicyConfigParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */