/*     */ package com.sun.xml.ws.db;
/*     */ 
/*     */ import com.oracle.webservices.api.databinding.Databinding;
/*     */ import com.oracle.webservices.api.databinding.DatabindingModeFeature;
/*     */ import com.oracle.webservices.api.databinding.WSDLGenerator;
/*     */ import com.sun.xml.ws.api.BindingID;
/*     */ import com.sun.xml.ws.api.WSBinding;
/*     */ import com.sun.xml.ws.api.databinding.DatabindingConfig;
/*     */ import com.sun.xml.ws.api.databinding.DatabindingFactory;
/*     */ import com.sun.xml.ws.api.databinding.MetadataReader;
/*     */ import com.sun.xml.ws.api.model.wsdl.WSDLPort;
/*     */ import com.sun.xml.ws.spi.db.DatabindingProvider;
/*     */ import com.sun.xml.ws.util.ServiceFinder;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.transform.Source;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ import javax.xml.ws.WebServiceFeature;
/*     */ import org.xml.sax.EntityResolver;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabindingFactoryImpl
/*     */   extends DatabindingFactory
/*     */ {
/*     */   static final String WsRuntimeFactoryDefaultImpl = "com.sun.xml.ws.db.DatabindingProviderImpl";
/*  79 */   protected Map<String, Object> properties = new HashMap<String, Object>();
/*     */   
/*     */   protected DatabindingProvider defaultRuntimeFactory;
/*     */   
/*     */   protected List<DatabindingProvider> providers;
/*     */   
/*     */   private static List<DatabindingProvider> providers() {
/*  86 */     List<DatabindingProvider> factories = new ArrayList<DatabindingProvider>();
/*  87 */     for (DatabindingProvider p : ServiceFinder.find(DatabindingProvider.class)) {
/*  88 */       factories.add(p);
/*     */     }
/*  90 */     return factories;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, Object> properties() {
/*  97 */     return this.properties;
/*     */   }
/*     */   
/*     */   <T> T property(Class<T> propType, String propName) {
/* 101 */     if (propName == null) propName = propType.getName(); 
/* 102 */     return propType.cast(this.properties.get(propName));
/*     */   }
/*     */   
/*     */   public DatabindingProvider provider(DatabindingConfig config) {
/* 106 */     String mode = databindingMode(config);
/* 107 */     if (this.providers == null)
/* 108 */       this.providers = providers(); 
/* 109 */     DatabindingProvider provider = null;
/* 110 */     if (this.providers != null)
/* 111 */       for (DatabindingProvider p : this.providers) {
/* 112 */         if (p.isFor(mode))
/* 113 */           provider = p; 
/* 114 */       }   if (provider == null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 120 */       provider = new DatabindingProviderImpl();
/*     */     }
/* 122 */     return provider;
/*     */   }
/*     */   
/*     */   public Databinding createRuntime(DatabindingConfig config) {
/* 126 */     DatabindingProvider provider = provider(config);
/* 127 */     return provider.create(config);
/*     */   }
/*     */   
/*     */   public WSDLGenerator createWsdlGen(DatabindingConfig config) {
/* 131 */     DatabindingProvider provider = provider(config);
/* 132 */     return provider.wsdlGen(config);
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
/*     */   String databindingMode(DatabindingConfig config) {
/* 156 */     if (config.getMappingInfo() != null && config.getMappingInfo().getDatabindingMode() != null)
/*     */     {
/* 158 */       return config.getMappingInfo().getDatabindingMode(); } 
/* 159 */     if (config.getFeatures() != null) for (WebServiceFeature f : config.getFeatures()) {
/* 160 */         if (f instanceof DatabindingModeFeature) {
/* 161 */           DatabindingModeFeature dmf = (DatabindingModeFeature)f;
/* 162 */           return dmf.getMode();
/*     */         } 
/*     */       }  
/* 165 */     return null;
/*     */   }
/*     */   
/*     */   ClassLoader classLoader() {
/* 169 */     ClassLoader classLoader = property(ClassLoader.class, null);
/* 170 */     if (classLoader == null) classLoader = Thread.currentThread().getContextClassLoader(); 
/* 171 */     return classLoader;
/*     */   }
/*     */   
/*     */   Properties loadPropertiesFile(String fileName) {
/* 175 */     ClassLoader classLoader = classLoader();
/* 176 */     Properties p = new Properties();
/*     */     try {
/* 178 */       InputStream is = null;
/* 179 */       if (classLoader == null) {
/* 180 */         is = ClassLoader.getSystemResourceAsStream(fileName);
/*     */       } else {
/* 182 */         is = classLoader.getResourceAsStream(fileName);
/*     */       } 
/* 184 */       if (is != null) {
/* 185 */         p.load(is);
/*     */       }
/* 187 */     } catch (Exception e) {
/* 188 */       throw new WebServiceException(e);
/*     */     } 
/* 190 */     return p;
/*     */   }
/*     */   
/*     */   public Databinding.Builder createBuilder(Class<?> contractClass, Class<?> endpointClass) {
/* 194 */     return new ConfigBuilder(this, contractClass, endpointClass);
/*     */   }
/*     */   
/*     */   static class ConfigBuilder implements Databinding.Builder {
/*     */     DatabindingConfig config;
/*     */     DatabindingFactoryImpl factory;
/*     */     
/*     */     ConfigBuilder(DatabindingFactoryImpl f, Class<?> contractClass, Class<?> implBeanClass) {
/* 202 */       this.factory = f;
/* 203 */       this.config = new DatabindingConfig();
/* 204 */       this.config.setContractClass(contractClass);
/* 205 */       this.config.setEndpointClass(implBeanClass);
/*     */     }
/*     */     public Databinding.Builder targetNamespace(String targetNamespace) {
/* 208 */       this.config.getMappingInfo().setTargetNamespace(targetNamespace);
/* 209 */       return this;
/*     */     }
/*     */     public Databinding.Builder serviceName(QName serviceName) {
/* 212 */       this.config.getMappingInfo().setServiceName(serviceName);
/* 213 */       return this;
/*     */     }
/*     */     public Databinding.Builder portName(QName portName) {
/* 216 */       this.config.getMappingInfo().setPortName(portName);
/* 217 */       return this;
/*     */     }
/*     */     public Databinding.Builder wsdlURL(URL wsdlURL) {
/* 220 */       this.config.setWsdlURL(wsdlURL);
/* 221 */       return this;
/*     */     }
/*     */     public Databinding.Builder wsdlSource(Source wsdlSource) {
/* 224 */       this.config.setWsdlSource(wsdlSource);
/* 225 */       return this;
/*     */     }
/*     */     public Databinding.Builder entityResolver(EntityResolver entityResolver) {
/* 228 */       this.config.setEntityResolver(entityResolver);
/* 229 */       return this;
/*     */     }
/*     */     public Databinding.Builder classLoader(ClassLoader classLoader) {
/* 232 */       this.config.setClassLoader(classLoader);
/* 233 */       return this;
/*     */     }
/*     */     public Databinding.Builder feature(WebServiceFeature... f) {
/* 236 */       this.config.setFeatures(f);
/* 237 */       return this;
/*     */     }
/*     */     public Databinding.Builder property(String name, Object value) {
/* 240 */       this.config.properties().put(name, value);
/* 241 */       if (isfor(BindingID.class, name, value)) {
/* 242 */         this.config.getMappingInfo().setBindingID((BindingID)value);
/*     */       }
/* 244 */       if (isfor(WSBinding.class, name, value)) {
/* 245 */         this.config.setWSBinding((WSBinding)value);
/*     */       }
/* 247 */       if (isfor(WSDLPort.class, name, value)) {
/* 248 */         this.config.setWsdlPort((WSDLPort)value);
/*     */       }
/* 250 */       if (isfor(MetadataReader.class, name, value)) {
/* 251 */         this.config.setMetadataReader((MetadataReader)value);
/*     */       }
/* 253 */       return this;
/*     */     }
/*     */     boolean isfor(Class<?> type, String name, Object value) {
/* 256 */       return (type.getName().equals(name) && type.isInstance(value));
/*     */     }
/*     */     
/*     */     public Databinding build() {
/* 260 */       return this.factory.createRuntime(this.config);
/*     */     }
/*     */     public WSDLGenerator createWSDLGenerator() {
/* 263 */       return this.factory.createWsdlGen(this.config);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\db\DatabindingFactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */