/*    */ package com.sun.xml.rpc.processor.config.parser;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*    */ import com.sun.xml.rpc.processor.config.NoMetadataModelInfo;
/*    */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*    */ import com.sun.xml.rpc.spi.tools.NamespaceMappingRegistryInfo;
/*    */ import com.sun.xml.rpc.streaming.XMLReader;
/*    */ import javax.xml.namespace.QName;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoMetadataModelInfoParser
/*    */   extends ModelInfoParser
/*    */ {
/*    */   public NoMetadataModelInfoParser(ProcessorEnvironment env) {
/* 43 */     super(env);
/*    */   }
/*    */   
/*    */   public ModelInfo parse(XMLReader reader) {
/* 47 */     NoMetadataModelInfo modelInfo = new NoMetadataModelInfo();
/* 48 */     String location = ParserUtil.getMandatoryNonEmptyAttribute(reader, "location");
/*    */     
/* 50 */     modelInfo.setLocation(location);
/* 51 */     String interfaceName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "interfaceName");
/*    */     
/* 53 */     modelInfo.setInterfaceName(interfaceName);
/* 54 */     String servantName = ParserUtil.getNonEmptyAttribute(reader, "servantName");
/*    */     
/* 56 */     modelInfo.setServantName(servantName);
/* 57 */     String serviceInterfaceName = ParserUtil.getNonEmptyAttribute(reader, "serviceInterfaceName");
/*    */     
/* 59 */     modelInfo.setServiceInterfaceName(serviceInterfaceName);
/* 60 */     QName serviceName = ParserUtil.getQNameAttribute(reader, "serviceName");
/*    */     
/* 62 */     modelInfo.setServiceName(serviceName);
/* 63 */     QName portName = ParserUtil.getQNameAttribute(reader, "portName");
/*    */     
/* 65 */     modelInfo.setPortName(portName);
/*    */     
/* 67 */     boolean gotHandlerChains = false;
/* 68 */     boolean gotNamespaceMappingRegistry = false;
/* 69 */     while (reader.nextElementContent() != 2) {
/* 70 */       if (reader.getName().equals(Constants.QNAME_HANDLER_CHAINS)) {
/* 71 */         if (gotHandlerChains) {
/* 72 */           ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*    */           continue;
/*    */         } 
/* 75 */         HandlerChainInfoData data = parseHandlerChainInfoData(reader);
/*    */         
/* 77 */         modelInfo.setClientHandlerChainInfo(data.getClientHandlerChainInfo());
/*    */         
/* 79 */         modelInfo.setServerHandlerChainInfo(data.getServerHandlerChainInfo());
/*    */         
/* 81 */         gotHandlerChains = true; continue;
/*    */       } 
/* 83 */       if (reader.getName().equals(Constants.QNAME_NAMESPACE_MAPPING_REGISTRY)) {
/*    */ 
/*    */         
/* 86 */         if (gotNamespaceMappingRegistry) {
/* 87 */           ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*    */           continue;
/*    */         } 
/* 90 */         modelInfo.setNamespaceMappingRegistry((NamespaceMappingRegistryInfo)parseNamespaceMappingRegistryInfo(reader));
/*    */         
/* 92 */         gotNamespaceMappingRegistry = true;
/*    */         continue;
/*    */       } 
/* 95 */       ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*    */     } 
/*    */ 
/*    */     
/* 99 */     return (ModelInfo)modelInfo;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\parser\NoMetadataModelInfoParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */