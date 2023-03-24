/*    */ package com.sun.xml.rpc.processor.config.parser;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*    */ import com.sun.xml.rpc.processor.config.WSDLModelInfo;
/*    */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*    */ import com.sun.xml.rpc.spi.tools.NamespaceMappingRegistryInfo;
/*    */ import com.sun.xml.rpc.streaming.XMLReader;
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
/*    */ public class WSDLModelInfoParser
/*    */   extends ModelInfoParser
/*    */ {
/*    */   public WSDLModelInfoParser(ProcessorEnvironment env) {
/* 41 */     super(env);
/*    */   }
/*    */   
/*    */   public ModelInfo parse(XMLReader reader) {
/* 45 */     WSDLModelInfo modelInfo = new WSDLModelInfo();
/* 46 */     String location = ParserUtil.getMandatoryNonEmptyAttribute(reader, "location");
/*    */     
/* 48 */     modelInfo.setLocation(location);
/* 49 */     String packageName = ParserUtil.getMandatoryNonEmptyAttribute(reader, "packageName");
/*    */     
/* 51 */     modelInfo.setJavaPackageName(packageName);
/*    */     
/* 53 */     boolean gotTypeMappingRegistry = false;
/* 54 */     boolean gotHandlerChains = false;
/* 55 */     boolean gotNamespaceMappingRegistry = false;
/* 56 */     while (reader.nextElementContent() != 2) {
/* 57 */       if (reader.getName().equals(Constants.QNAME_TYPE_MAPPING_REGISTRY)) {
/*    */ 
/*    */         
/* 60 */         if (gotTypeMappingRegistry) {
/* 61 */           ParserUtil.failWithLocalName("configuration.invalidElement", reader);
/*    */           continue;
/*    */         } 
/* 64 */         modelInfo.setTypeMappingRegistry(parseTypeMappingRegistryInfo(reader));
/*    */         
/* 66 */         gotTypeMappingRegistry = true; continue;
/*    */       } 
/* 68 */       if (reader.getName().equals(Constants.QNAME_HANDLER_CHAINS)) {
/*    */ 
/*    */         
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


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\parser\WSDLModelInfoParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */