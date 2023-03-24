/*     */ package com.sun.xml.rpc.util;
/*     */ 
/*     */ import com.sun.xml.rpc.client.ClientTransportFactory;
/*     */ import com.sun.xml.rpc.client.http.HttpClientTransportFactory;
/*     */ import com.sun.xml.rpc.client.local.LocalClientTransportFactory;
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.config.HandlerInfo;
/*     */ import com.sun.xml.rpc.processor.config.ModelFileModelInfo;
/*     */ import com.sun.xml.rpc.processor.config.NamespaceMappingInfo;
/*     */ import com.sun.xml.rpc.processor.config.NamespaceMappingRegistryInfo;
/*     */ import com.sun.xml.rpc.processor.config.NoMetadataModelInfo;
/*     */ import com.sun.xml.rpc.processor.config.parser.ModelInfoPlugin;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.util.XMLModelFileFilter;
/*     */ import com.sun.xml.rpc.server.http.Implementor;
/*     */ import com.sun.xml.rpc.server.http.ImplementorCache;
/*     */ import com.sun.xml.rpc.server.http.JAXRPCServletDelegate;
/*     */ import com.sun.xml.rpc.server.http.RuntimeEndpointInfo;
/*     */ import com.sun.xml.rpc.soap.message.SOAPMessageContext;
/*     */ import com.sun.xml.rpc.spi.JaxRpcObjectFactory;
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
/*     */ import com.sun.xml.rpc.tools.plugin.ToolPluginFactory;
/*     */ import com.sun.xml.rpc.tools.wscompile.CompileTool;
/*     */ import com.sun.xml.rpc.wsdl.parser.WSDLParser;
/*     */ import com.sun.xml.rpc.wsdl.parser.WSDLUtil;
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
/*     */ public class JaxRpcObjectFactoryImpl
/*     */   extends JaxRpcObjectFactory
/*     */ {
/*     */   public ModelFileModelInfo createModelFileModelInfo() {
/*  74 */     return (ModelFileModelInfo)new ModelFileModelInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public J2EEModelInfo createJ2EEModelInfo(URL mappingFile) throws Exception {
/*  81 */     ModelInfoPlugin plugin = (ModelInfoPlugin)ToolPluginFactory.getInstance().getPlugin("com.sun.xml.rpc.tools.j2ee");
/*     */ 
/*     */     
/*  84 */     return (J2EEModelInfo)plugin.createModelInfo(mappingFile);
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
/*     */   public ClientTransportFactory createClientTransportFactory(int type, OutputStream outputStream) {
/*  97 */     ClientTransportFactory clientFactory = null;
/*  98 */     switch (type) {
/*     */       case 0:
/* 100 */         return (ClientTransportFactory)new HttpClientTransportFactory(outputStream);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 110 */         return (ClientTransportFactory)new LocalClientTransportFactory(null, outputStream);
/*     */     } 
/* 112 */     return (ClientTransportFactory)clientFactory;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CompileTool createCompileTool(OutputStream outputStream, String str) {
/* 118 */     return (CompileTool)new CompileTool(outputStream, str);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Implementor createImplementor(ServletContext servletContext, Tie tie) {
/* 124 */     return (Implementor)new Implementor(servletContext, tie);
/*     */   }
/*     */ 
/*     */   
/*     */   public ImplementorCache createImplementorCache(ServletConfig servletConfig) {
/* 129 */     return (ImplementorCache)new ImplementorCache(servletConfig);
/*     */   }
/*     */ 
/*     */   
/*     */   public Configuration createConfiguration(ProcessorEnvironment processorEnvironment) {
/* 134 */     return (Configuration)new Configuration(processorEnvironment);
/*     */   }
/*     */   
/*     */   public HandlerInfo createHandlerInfo() {
/* 138 */     return (HandlerInfo)new HandlerInfo();
/*     */   }
/*     */   
/*     */   public Names createNames() {
/* 142 */     return (Names)new Names();
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
/*     */   public NamespaceMappingInfo createNamespaceMappingInfo(String namespaceURI, String javaPackageName) {
/* 154 */     return (NamespaceMappingInfo)new NamespaceMappingInfo(namespaceURI, javaPackageName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NamespaceMappingRegistryInfo createNamespaceMappingRegistryInfo() {
/* 164 */     return (NamespaceMappingRegistryInfo)new NamespaceMappingRegistryInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NoMetadataModelInfo createNoMetadataModelInfo() {
/* 174 */     return (NoMetadataModelInfo)new NoMetadataModelInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RuntimeEndpointInfo createRuntimeEndpointInfo() {
/* 184 */     return (RuntimeEndpointInfo)new RuntimeEndpointInfo();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SOAPMessageContext createSOAPMessageContext() {
/* 194 */     return (SOAPMessageContext)new SOAPMessageContext();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServletDelegate createServletDelegate() {
/* 204 */     return (ServletDelegate)new JAXRPCServletDelegate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public XMLModelFileFilter createXMLModelFileFilter() {
/* 214 */     return (XMLModelFileFilter)new XMLModelFileFilter();
/*     */   }
/*     */   
/*     */   public WSDLUtil createWSDLUtil() {
/* 218 */     return (WSDLUtil)new WSDLUtil();
/*     */   }
/*     */   
/*     */   public WSDLParser createWSDLParser() {
/* 222 */     return (WSDLParser)new WSDLParser();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rp\\util\JaxRpcObjectFactoryImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */