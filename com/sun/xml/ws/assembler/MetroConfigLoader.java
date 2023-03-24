/*     */ package com.sun.xml.ws.assembler;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.api.ResourceLoader;
/*     */ import com.sun.xml.ws.api.server.Container;
/*     */ import com.sun.xml.ws.resources.TubelineassemblyMessages;
/*     */ import com.sun.xml.ws.runtime.config.MetroConfig;
/*     */ import com.sun.xml.ws.runtime.config.TubeFactoryList;
/*     */ import com.sun.xml.ws.runtime.config.TubelineDefinition;
/*     */ import com.sun.xml.ws.runtime.config.TubelineMapping;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.util.logging.Level;
/*     */ import javax.xml.bind.JAXBContext;
/*     */ import javax.xml.bind.JAXBElement;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.stream.XMLInputFactory;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MetroConfigLoader
/*     */ {
/*  79 */   private static final Logger LOGGER = Logger.getLogger(MetroConfigLoader.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private MetroConfigName defaultTubesConfigNames;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private static final TubeFactoryListResolver ENDPOINT_SIDE_RESOLVER = new TubeFactoryListResolver()
/*     */     {
/*     */       public TubeFactoryList getFactories(TubelineDefinition td) {
/*  91 */         return (td != null) ? td.getEndpointSide() : null;
/*     */       }
/*     */     };
/*  94 */   private static final TubeFactoryListResolver CLIENT_SIDE_RESOLVER = new TubeFactoryListResolver()
/*     */     {
/*     */       public TubeFactoryList getFactories(TubelineDefinition td) {
/*  97 */         return (td != null) ? td.getClientSide() : null;
/*     */       }
/*     */     };
/*     */   
/*     */   private MetroConfig defaultConfig;
/*     */   private URL defaultConfigUrl;
/*     */   private MetroConfig appConfig;
/*     */   private URL appConfigUrl;
/*     */   
/*     */   MetroConfigLoader(Container container, MetroConfigName defaultTubesConfigNames) {
/* 107 */     this.defaultTubesConfigNames = defaultTubesConfigNames;
/* 108 */     ResourceLoader spiResourceLoader = null;
/* 109 */     if (container != null) {
/* 110 */       spiResourceLoader = (ResourceLoader)container.getSPI(ResourceLoader.class);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 115 */     init(container, new ResourceLoader[] { spiResourceLoader, new MetroConfigUrlLoader(container) });
/*     */   }
/*     */ 
/*     */   
/*     */   private void init(Container container, ResourceLoader... loaders) {
/* 120 */     String appFileName = null;
/* 121 */     String defaultFileName = null;
/* 122 */     if (container != null) {
/* 123 */       MetroConfigName mcn = (MetroConfigName)container.getSPI(MetroConfigName.class);
/* 124 */       if (mcn != null) {
/* 125 */         appFileName = mcn.getAppFileName();
/* 126 */         defaultFileName = mcn.getDefaultFileName();
/*     */       } 
/*     */     } 
/* 129 */     if (appFileName == null) {
/* 130 */       appFileName = this.defaultTubesConfigNames.getAppFileName();
/*     */     }
/*     */     
/* 133 */     if (defaultFileName == null) {
/* 134 */       defaultFileName = this.defaultTubesConfigNames.getDefaultFileName();
/*     */     }
/* 136 */     this.defaultConfigUrl = locateResource(defaultFileName, loaders);
/* 137 */     if (this.defaultConfigUrl == null) {
/* 138 */       throw (IllegalStateException)LOGGER.logSevereException(new IllegalStateException(TubelineassemblyMessages.MASM_0001_DEFAULT_CFG_FILE_NOT_FOUND(defaultFileName)));
/*     */     }
/*     */     
/* 141 */     LOGGER.config(TubelineassemblyMessages.MASM_0002_DEFAULT_CFG_FILE_LOCATED(defaultFileName, this.defaultConfigUrl));
/* 142 */     this.defaultConfig = loadMetroConfig(this.defaultConfigUrl);
/* 143 */     if (this.defaultConfig == null) {
/* 144 */       throw (IllegalStateException)LOGGER.logSevereException(new IllegalStateException(TubelineassemblyMessages.MASM_0003_DEFAULT_CFG_FILE_NOT_LOADED(defaultFileName)));
/*     */     }
/* 146 */     if (this.defaultConfig.getTubelines() == null) {
/* 147 */       throw (IllegalStateException)LOGGER.logSevereException(new IllegalStateException(TubelineassemblyMessages.MASM_0004_NO_TUBELINES_SECTION_IN_DEFAULT_CFG_FILE(defaultFileName)));
/*     */     }
/* 149 */     if (this.defaultConfig.getTubelines().getDefault() == null) {
/* 150 */       throw (IllegalStateException)LOGGER.logSevereException(new IllegalStateException(TubelineassemblyMessages.MASM_0005_NO_DEFAULT_TUBELINE_IN_DEFAULT_CFG_FILE(defaultFileName)));
/*     */     }
/*     */     
/* 153 */     this.appConfigUrl = locateResource(appFileName, loaders);
/* 154 */     if (this.appConfigUrl != null) {
/* 155 */       LOGGER.config(TubelineassemblyMessages.MASM_0006_APP_CFG_FILE_LOCATED(this.appConfigUrl));
/* 156 */       this.appConfig = loadMetroConfig(this.appConfigUrl);
/*     */     } else {
/* 158 */       LOGGER.config(TubelineassemblyMessages.MASM_0007_APP_CFG_FILE_NOT_FOUND());
/* 159 */       this.appConfig = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   TubeFactoryList getEndpointSideTubeFactories(URI endpointReference) {
/* 164 */     return getTubeFactories(endpointReference, ENDPOINT_SIDE_RESOLVER);
/*     */   }
/*     */   
/*     */   TubeFactoryList getClientSideTubeFactories(URI endpointReference) {
/* 168 */     return getTubeFactories(endpointReference, CLIENT_SIDE_RESOLVER);
/*     */   }
/*     */   
/*     */   private TubeFactoryList getTubeFactories(URI endpointReference, TubeFactoryListResolver resolver) {
/* 172 */     if (this.appConfig != null && this.appConfig.getTubelines() != null) {
/* 173 */       for (TubelineMapping mapping : this.appConfig.getTubelines().getTubelineMappings()) {
/* 174 */         if (mapping.getEndpointRef().equals(endpointReference.toString())) {
/* 175 */           TubeFactoryList list = resolver.getFactories(getTubeline(this.appConfig, resolveReference(mapping.getTubelineRef())));
/* 176 */           if (list != null) {
/* 177 */             return list;
/*     */           }
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       
/* 184 */       if (this.appConfig.getTubelines().getDefault() != null) {
/* 185 */         TubeFactoryList list = resolver.getFactories(getTubeline(this.appConfig, resolveReference(this.appConfig.getTubelines().getDefault())));
/* 186 */         if (list != null) {
/* 187 */           return list;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     for (TubelineMapping mapping : this.defaultConfig.getTubelines().getTubelineMappings()) {
/* 193 */       if (mapping.getEndpointRef().equals(endpointReference.toString())) {
/* 194 */         TubeFactoryList list = resolver.getFactories(getTubeline(this.defaultConfig, resolveReference(mapping.getTubelineRef())));
/* 195 */         if (list != null) {
/* 196 */           return list;
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     return resolver.getFactories(getTubeline(this.defaultConfig, resolveReference(this.defaultConfig.getTubelines().getDefault())));
/*     */   }
/*     */   
/*     */   TubelineDefinition getTubeline(MetroConfig config, URI tubelineDefinitionUri) {
/* 207 */     if (config != null && config.getTubelines() != null) {
/* 208 */       for (TubelineDefinition td : config.getTubelines().getTubelineDefinitions()) {
/* 209 */         if (td.getName().equals(tubelineDefinitionUri.getFragment())) {
/* 210 */           return td;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 215 */     return null;
/*     */   }
/*     */   
/*     */   private static URI resolveReference(String reference) {
/*     */     try {
/* 220 */       return new URI(reference);
/* 221 */     } catch (URISyntaxException ex) {
/* 222 */       throw (WebServiceException)LOGGER.logSevereException(new WebServiceException(TubelineassemblyMessages.MASM_0008_INVALID_URI_REFERENCE(reference), ex));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static URL locateResource(String resource, ResourceLoader loader) {
/* 228 */     if (loader == null) return null;
/*     */     
/*     */     try {
/* 231 */       return loader.getResource(resource);
/* 232 */     } catch (MalformedURLException ex) {
/* 233 */       LOGGER.severe(TubelineassemblyMessages.MASM_0009_CANNOT_FORM_VALID_URL(resource), ex);
/*     */       
/* 235 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static URL locateResource(String resource, ResourceLoader[] loaders) {
/* 240 */     for (ResourceLoader loader : loaders) {
/* 241 */       URL url = locateResource(resource, loader);
/* 242 */       if (url != null) {
/* 243 */         return url;
/*     */       }
/*     */     } 
/* 246 */     return null;
/*     */   }
/*     */   
/*     */   private static MetroConfig loadMetroConfig(@NotNull URL resourceUrl) {
/* 250 */     MetroConfig result = null;
/*     */     try {
/* 252 */       JAXBContext jaxbContext = JAXBContext.newInstance(MetroConfig.class.getPackage().getName());
/* 253 */       Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
/* 254 */       JAXBElement<MetroConfig> configElement = unmarshaller.unmarshal(XMLInputFactory.newInstance().createXMLStreamReader(resourceUrl.openStream()), MetroConfig.class);
/* 255 */       result = configElement.getValue();
/* 256 */     } catch (Exception e) {
/* 257 */       LOGGER.warning(TubelineassemblyMessages.MASM_0010_ERROR_READING_CFG_FILE_FROM_LOCATION(resourceUrl.toString()), e);
/*     */     } 
/* 259 */     return result;
/*     */   }
/*     */   
/*     */   private static interface TubeFactoryListResolver {
/*     */     TubeFactoryList getFactories(TubelineDefinition param1TubelineDefinition); }
/*     */   
/*     */   private static class MetroConfigUrlLoader extends ResourceLoader { Container container;
/*     */     
/*     */     MetroConfigUrlLoader(ResourceLoader parentLoader) {
/* 268 */       this.parentLoader = parentLoader;
/*     */     }
/*     */     ResourceLoader parentLoader;
/*     */     MetroConfigUrlLoader(Container container) {
/* 272 */       this((container != null) ? (ResourceLoader)container.getSPI(ResourceLoader.class) : null);
/* 273 */       this.container = container;
/*     */     }
/*     */ 
/*     */     
/*     */     public URL getResource(String resource) throws MalformedURLException {
/* 278 */       MetroConfigLoader.LOGGER.entering(new Object[] { resource });
/* 279 */       URL resourceUrl = null;
/*     */       try {
/* 281 */         if (this.parentLoader != null) {
/* 282 */           if (MetroConfigLoader.LOGGER.isLoggable(Level.FINE)) {
/* 283 */             MetroConfigLoader.LOGGER.fine(TubelineassemblyMessages.MASM_0011_LOADING_RESOURCE(resource, this.parentLoader));
/*     */           }
/*     */           
/* 286 */           resourceUrl = this.parentLoader.getResource(resource);
/*     */         } 
/*     */         
/* 289 */         if (resourceUrl == null) {
/* 290 */           resourceUrl = loadViaClassLoaders("META-INF/" + resource);
/*     */         }
/*     */         
/* 293 */         if (resourceUrl == null && this.container != null)
/*     */         {
/* 295 */           resourceUrl = loadFromServletContext(resource);
/*     */         }
/*     */         
/* 298 */         return resourceUrl;
/*     */       } finally {
/* 300 */         MetroConfigLoader.LOGGER.exiting(resourceUrl);
/*     */       } 
/*     */     }
/*     */     
/*     */     private static URL loadViaClassLoaders(String resource) {
/* 305 */       URL resourceUrl = tryLoadFromClassLoader(resource, Thread.currentThread().getContextClassLoader());
/* 306 */       if (resourceUrl == null) {
/* 307 */         resourceUrl = tryLoadFromClassLoader(resource, MetroConfigLoader.class.getClassLoader());
/* 308 */         if (resourceUrl == null) {
/* 309 */           return ClassLoader.getSystemResource(resource);
/*     */         }
/*     */       } 
/*     */       
/* 313 */       return resourceUrl;
/*     */     }
/*     */     
/*     */     private static URL tryLoadFromClassLoader(String resource, ClassLoader loader) {
/* 317 */       return (loader != null) ? loader.getResource(resource) : null;
/*     */     }
/*     */     
/*     */     private URL loadFromServletContext(String resource) throws RuntimeException {
/* 321 */       Object context = null;
/*     */       try {
/* 323 */         Class<?> contextClass = Class.forName("javax.servlet.ServletContext");
/* 324 */         context = this.container.getSPI(contextClass);
/* 325 */         if (context != null) {
/* 326 */           if (MetroConfigLoader.LOGGER.isLoggable(Level.FINE)) {
/* 327 */             MetroConfigLoader.LOGGER.fine(TubelineassemblyMessages.MASM_0012_LOADING_VIA_SERVLET_CONTEXT(resource, context));
/*     */           }
/*     */           try {
/* 330 */             Method method = context.getClass().getMethod("getResource", new Class[] { String.class });
/* 331 */             Object result = method.invoke(context, new Object[] { "/WEB-INF/" + resource });
/* 332 */             return URL.class.cast(result);
/* 333 */           } catch (Exception e) {
/* 334 */             throw (RuntimeException)MetroConfigLoader.LOGGER.logSevereException(new RuntimeException(TubelineassemblyMessages.MASM_0013_ERROR_INVOKING_SERVLET_CONTEXT_METHOD("getResource()")), e);
/*     */           } 
/*     */         } 
/* 337 */       } catch (ClassNotFoundException e) {
/* 338 */         if (MetroConfigLoader.LOGGER.isLoggable(Level.FINE)) {
/* 339 */           MetroConfigLoader.LOGGER.fine(TubelineassemblyMessages.MASM_0014_UNABLE_TO_LOAD_CLASS("javax.servlet.ServletContext"));
/*     */         }
/*     */       } 
/* 342 */       return null;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\MetroConfigLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */