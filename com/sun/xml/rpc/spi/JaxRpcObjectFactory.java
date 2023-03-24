/*     */ package com.sun.xml.rpc.spi;
/*     */ 
/*     */ import com.sun.xml.rpc.spi.runtime.ClientTransportFactory;
/*     */ import com.sun.xml.rpc.spi.runtime.Implementor;
/*     */ import com.sun.xml.rpc.spi.runtime.ImplementorCache;
/*     */ import com.sun.xml.rpc.spi.runtime.RuntimeEndpointInfo;
/*     */ import com.sun.xml.rpc.spi.runtime.SOAPMessageContext;
/*     */ import com.sun.xml.rpc.spi.runtime.ServletDelegate;
/*     */ import com.sun.xml.rpc.spi.runtime.Tie;
/*     */ import com.sun.xml.rpc.spi.tools.CompileTool;
/*     */ import com.sun.xml.rpc.spi.tools.Configuration;
/*     */ import com.sun.xml.rpc.spi.tools.HandlerInfo;
/*     */ import com.sun.xml.rpc.spi.tools.J2EEModelInfo;
/*     */ import com.sun.xml.rpc.spi.tools.ModelFileModelInfo;
/*     */ import com.sun.xml.rpc.spi.tools.Names;
/*     */ import com.sun.xml.rpc.spi.tools.NamespaceMappingInfo;
/*     */ import com.sun.xml.rpc.spi.tools.NamespaceMappingRegistryInfo;
/*     */ import com.sun.xml.rpc.spi.tools.NoMetadataModelInfo;
/*     */ import com.sun.xml.rpc.spi.tools.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.spi.tools.WSDLParser;
/*     */ import com.sun.xml.rpc.spi.tools.WSDLUtil;
/*     */ import com.sun.xml.rpc.spi.tools.XMLModelFileFilter;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import javax.servlet.ServletConfig;
/*     */ import javax.servlet.ServletContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JaxRpcObjectFactory
/*     */ {
/*     */   private static JaxRpcObjectFactory factory;
/*  61 */   private static String DEFAULT_JAXRPC_OBJECT_FACTORY = "com.sun.xml.rpc.util.JaxRpcObjectFactoryImpl";
/*     */   
/*  63 */   private static String JAXRPC_FACTORY_PROPERTY = "javax.xml.rpc.spi.JaxRpcObjectFactory";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object newInstance(String className, ClassLoader classLoader) {
/*     */     try {
/*     */       Class<?> spiClass;
/*  79 */       if (classLoader == null) {
/*  80 */         spiClass = Class.forName(className);
/*     */       } else {
/*  82 */         spiClass = classLoader.loadClass(className);
/*     */       } 
/*  84 */       return spiClass.newInstance();
/*  85 */     } catch (Exception e) {
/*  86 */       throw new IllegalArgumentException(e.getMessage());
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
/*     */   public static JaxRpcObjectFactory newInstance() {
/* 111 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*     */ 
/*     */     
/*     */     try {
/* 115 */       String systemProp = System.getProperty(JAXRPC_FACTORY_PROPERTY);
/*     */       
/* 117 */       if (systemProp != null) {
/* 118 */         return (JaxRpcObjectFactory)newInstance(systemProp, classLoader);
/*     */       }
/* 120 */     } catch (Exception e) {}
/*     */ 
/*     */ 
/*     */     
/* 124 */     String serviceId = "META-INF/services/" + JAXRPC_FACTORY_PROPERTY;
/*     */     
/*     */     try {
/* 127 */       InputStream is = null;
/* 128 */       if (classLoader == null) {
/* 129 */         is = ClassLoader.getSystemResourceAsStream(serviceId);
/*     */       } else {
/* 131 */         is = classLoader.getResourceAsStream(serviceId);
/*     */       } 
/*     */       
/* 134 */       if (is != null) {
/* 135 */         BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
/*     */ 
/*     */         
/* 138 */         String factoryClassName = rd.readLine();
/* 139 */         rd.close();
/*     */         
/* 141 */         if (factoryClassName != null && !"".equals(factoryClassName))
/*     */         {
/* 143 */           return (JaxRpcObjectFactory)newInstance(factoryClassName, classLoader);
/*     */         }
/*     */       } 
/* 146 */     } catch (Exception ex) {}
/*     */ 
/*     */     
/* 149 */     return (JaxRpcObjectFactory)newInstance(DEFAULT_JAXRPC_OBJECT_FACTORY, classLoader);
/*     */   }
/*     */   
/*     */   public abstract ModelFileModelInfo createModelFileModelInfo();
/*     */   
/*     */   public abstract NoMetadataModelInfo createNoMetadataModelInfo();
/*     */   
/*     */   public abstract J2EEModelInfo createJ2EEModelInfo(URL paramURL) throws Exception;
/*     */   
/*     */   public abstract HandlerInfo createHandlerInfo();
/*     */   
/*     */   public abstract NamespaceMappingRegistryInfo createNamespaceMappingRegistryInfo();
/*     */   
/*     */   public abstract NamespaceMappingInfo createNamespaceMappingInfo(String paramString1, String paramString2);
/*     */   
/*     */   public abstract Configuration createConfiguration(ProcessorEnvironment paramProcessorEnvironment);
/*     */   
/*     */   public abstract SOAPMessageContext createSOAPMessageContext();
/*     */   
/*     */   public abstract Implementor createImplementor(ServletContext paramServletContext, Tie paramTie);
/*     */   
/*     */   public abstract RuntimeEndpointInfo createRuntimeEndpointInfo();
/*     */   
/*     */   public abstract ClientTransportFactory createClientTransportFactory(int paramInt, OutputStream paramOutputStream);
/*     */   
/*     */   public abstract CompileTool createCompileTool(OutputStream paramOutputStream, String paramString);
/*     */   
/*     */   public abstract XMLModelFileFilter createXMLModelFileFilter();
/*     */   
/*     */   public abstract ImplementorCache createImplementorCache(ServletConfig paramServletConfig);
/*     */   
/*     */   public abstract ServletDelegate createServletDelegate();
/*     */   
/*     */   public abstract Names createNames();
/*     */   
/*     */   public abstract WSDLUtil createWSDLUtil();
/*     */   
/*     */   public abstract WSDLParser createWSDLParser();
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\spi\JaxRpcObjectFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */