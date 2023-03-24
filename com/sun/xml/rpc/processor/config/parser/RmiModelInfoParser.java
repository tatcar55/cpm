/*     */ package com.sun.xml.rpc.processor.config.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.ConfigurationException;
/*     */ import com.sun.xml.rpc.processor.config.ImportedDocumentInfo;
/*     */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*     */ import com.sun.xml.rpc.processor.config.RmiInterfaceInfo;
/*     */ import com.sun.xml.rpc.processor.config.RmiModelInfo;
/*     */ import com.sun.xml.rpc.processor.config.TypeMappingRegistryInfo;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.spi.tools.NamespaceMappingRegistryInfo;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RmiModelInfoParser
/*     */   extends ModelInfoParser
/*     */ {
/*     */   public RmiModelInfoParser(ProcessorEnvironment env) {
/*  46 */     super(env);
/*     */   }
/*     */   
/*     */   public ModelInfo parse(XMLReader reader) {
/*  50 */     RmiModelInfo modelInfo = new RmiModelInfo();
/*  51 */     String name = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*     */     
/*  53 */     modelInfo.setName(name);
/*  54 */     String targetNamespaceURI = ParserUtil.getMandatoryNonEmptyAttribute(reader, "targetNamespace");
/*     */     
/*  56 */     modelInfo.setTargetNamespaceURI(targetNamespaceURI);
/*  57 */     String typeNamespaceURI = ParserUtil.getMandatoryNonEmptyAttribute(reader, "typeNamespace");
/*     */     
/*  59 */     modelInfo.setTypeNamespaceURI(typeNamespaceURI);
/*  60 */     String packageName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "packageName");
/*     */     
/*  62 */     modelInfo.setJavaPackageName(packageName);
/*     */     
/*  64 */     boolean gotTypeMappingRegistry = false;
/*  65 */     boolean gotHandlerChains = false;
/*  66 */     boolean gotNamespaceMappingRegistry = false;
/*  67 */     while (reader.nextElementContent() != 2) {
/*  68 */       if (reader.getName().equals(Constants.QNAME_INTERFACE)) {
/*  69 */         if (gotTypeMappingRegistry) {
/*  70 */           ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */           continue;
/*     */         } 
/*  73 */         modelInfo.add(parseInterfaceInfo(reader)); continue;
/*     */       } 
/*  75 */       if (reader.getName().equals(Constants.QNAME_TYPE_MAPPING_REGISTRY)) {
/*     */ 
/*     */         
/*  78 */         if (gotTypeMappingRegistry) {
/*  79 */           ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */           continue;
/*     */         } 
/*  82 */         modelInfo.setTypeMappingRegistry(parseTypeMappingRegistryInfo(reader));
/*     */         
/*  84 */         gotTypeMappingRegistry = true; continue;
/*     */       } 
/*  86 */       if (reader.getName().equals(Constants.QNAME_HANDLER_CHAINS)) {
/*     */ 
/*     */         
/*  89 */         if (gotHandlerChains) {
/*  90 */           ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */           continue;
/*     */         } 
/*  93 */         HandlerChainInfoData data = parseHandlerChainInfoData(reader);
/*     */         
/*  95 */         modelInfo.setClientHandlerChainInfo(data.getClientHandlerChainInfo());
/*     */         
/*  97 */         modelInfo.setServerHandlerChainInfo(data.getServerHandlerChainInfo());
/*     */         
/*  99 */         gotHandlerChains = true; continue;
/*     */       } 
/* 101 */       if (reader.getName().equals(Constants.QNAME_NAMESPACE_MAPPING_REGISTRY)) {
/*     */ 
/*     */         
/* 104 */         if (gotNamespaceMappingRegistry) {
/* 105 */           ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */           continue;
/*     */         } 
/* 108 */         modelInfo.setNamespaceMappingRegistry((NamespaceMappingRegistryInfo)parseNamespaceMappingRegistryInfo(reader));
/*     */         
/* 110 */         gotNamespaceMappingRegistry = true;
/*     */         continue;
/*     */       } 
/* 113 */       ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     TypeMappingRegistryInfo registryInfo = modelInfo.getTypeMappingRegistry();
/*     */     
/* 121 */     if (registryInfo != null) {
/* 122 */       String tns = modelInfo.getTargetNamespaceURI();
/* 123 */       if (tns != null) {
/* 124 */         ImportedDocumentInfo docInfo = registryInfo.getImportedDocument(tns);
/*     */         
/* 126 */         if (docInfo != null) {
/* 127 */           throw new ConfigurationException("configuration.invalidImport.targetNamespace", tns);
/*     */         }
/*     */       } 
/*     */       
/* 131 */       String ttns = modelInfo.getTypeNamespaceURI();
/* 132 */       if (ttns != null) {
/* 133 */         ImportedDocumentInfo docInfo = registryInfo.getImportedDocument(ttns);
/* 134 */         if (docInfo != null) {
/* 135 */           throw new ConfigurationException("configuration.invalidImport.targetTypeNamespace", ttns);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 141 */     return (ModelInfo)modelInfo;
/*     */   }
/*     */   
/*     */   private RmiInterfaceInfo parseInterfaceInfo(XMLReader reader) {
/* 145 */     RmiInterfaceInfo interfaceInfo = new RmiInterfaceInfo();
/* 146 */     String name = ParserUtil.getMandatoryNonEmptyAttribute(reader, "name");
/*     */     
/* 148 */     interfaceInfo.setName(name);
/* 149 */     String servantName = ParserUtil.getAttribute(reader, "servantName");
/*     */     
/* 151 */     interfaceInfo.setServantName(servantName);
/* 152 */     String soapAction = ParserUtil.getAttribute(reader, "soapAction");
/*     */     
/* 154 */     interfaceInfo.setSOAPAction(soapAction);
/* 155 */     String soapActionBase = ParserUtil.getAttribute(reader, "soapActionBase");
/*     */     
/* 157 */     interfaceInfo.setSOAPActionBase(soapActionBase);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     interfaceInfo.setSOAPVersion(SOAPVersion.SOAP_11);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     boolean gotHandlerChains = false;
/* 170 */     while (reader.nextElementContent() != 2) {
/* 171 */       if (reader.getName().equals(Constants.QNAME_HANDLER_CHAINS)) {
/* 172 */         if (gotHandlerChains) {
/* 173 */           ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */           continue;
/*     */         } 
/* 176 */         HandlerChainInfoData data = parseHandlerChainInfoData(reader);
/*     */         
/* 178 */         interfaceInfo.setClientHandlerChainInfo(data.getClientHandlerChainInfo());
/*     */         
/* 180 */         interfaceInfo.setServerHandlerChainInfo(data.getServerHandlerChainInfo());
/*     */         
/* 182 */         gotHandlerChains = true;
/*     */         continue;
/*     */       } 
/* 185 */       ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*     */     } 
/*     */ 
/*     */     
/* 189 */     return interfaceInfo;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\parser\RmiModelInfoParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */