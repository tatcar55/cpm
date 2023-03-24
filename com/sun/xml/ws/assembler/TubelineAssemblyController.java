/*     */ package com.sun.xml.ws.assembler;
/*     */ 
/*     */ import com.sun.istack.NotNull;
/*     */ import com.sun.istack.logging.Logger;
/*     */ import com.sun.xml.ws.assembler.dev.ClientTubelineAssemblyContext;
/*     */ import com.sun.xml.ws.resources.TubelineassemblyMessages;
/*     */ import com.sun.xml.ws.runtime.config.TubeFactoryConfig;
/*     */ import com.sun.xml.ws.runtime.config.TubeFactoryList;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class TubelineAssemblyController
/*     */ {
/*     */   private final MetroConfigName metroConfigName;
/*     */   
/*     */   TubelineAssemblyController(MetroConfigName metroConfigName) {
/*  65 */     this.metroConfigName = metroConfigName;
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
/*     */   Collection<TubeCreator> getTubeCreators(ClientTubelineAssemblyContext context) {
/*     */     URI endpointUri;
/*  87 */     if (context.getPortInfo() != null) {
/*  88 */       endpointUri = createEndpointComponentUri(context.getPortInfo().getServiceName(), context.getPortInfo().getPortName());
/*     */     } else {
/*  90 */       endpointUri = null;
/*     */     } 
/*     */     
/*  93 */     MetroConfigLoader configLoader = new MetroConfigLoader(context.getContainer(), this.metroConfigName);
/*  94 */     return initializeTubeCreators(configLoader.getClientSideTubeFactories(endpointUri));
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
/*     */   Collection<TubeCreator> getTubeCreators(DefaultServerTubelineAssemblyContext context) {
/*     */     URI endpointUri;
/* 116 */     if (context.getEndpoint() != null) {
/* 117 */       endpointUri = createEndpointComponentUri(context.getEndpoint().getServiceName(), context.getEndpoint().getPortName());
/*     */     } else {
/* 119 */       endpointUri = null;
/*     */     } 
/*     */     
/* 122 */     MetroConfigLoader configLoader = new MetroConfigLoader(context.getEndpoint().getContainer(), this.metroConfigName);
/* 123 */     return initializeTubeCreators(configLoader.getEndpointSideTubeFactories(endpointUri));
/*     */   }
/*     */   
/*     */   private Collection<TubeCreator> initializeTubeCreators(TubeFactoryList tfl) {
/* 127 */     ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
/*     */     
/* 129 */     LinkedList<TubeCreator> tubeCreators = new LinkedList<TubeCreator>();
/* 130 */     for (TubeFactoryConfig tubeFactoryConfig : tfl.getTubeFactoryConfigs()) {
/* 131 */       tubeCreators.addFirst(new TubeCreator(tubeFactoryConfig, contextClassLoader));
/*     */     }
/* 133 */     return tubeCreators;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private URI createEndpointComponentUri(@NotNull QName serviceName, @NotNull QName portName) {
/* 140 */     StringBuilder sb = (new StringBuilder(serviceName.getNamespaceURI())).append("#wsdl11.port(").append(serviceName.getLocalPart()).append('/').append(portName.getLocalPart()).append(')');
/*     */     try {
/* 142 */       return new URI(sb.toString());
/* 143 */     } catch (URISyntaxException ex) {
/* 144 */       Logger.getLogger(TubelineAssemblyController.class).warning(TubelineassemblyMessages.MASM_0020_ERROR_CREATING_URI_FROM_GENERATED_STRING(sb.toString()), ex);
/*     */ 
/*     */       
/* 147 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\assembler\TubelineAssemblyController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */