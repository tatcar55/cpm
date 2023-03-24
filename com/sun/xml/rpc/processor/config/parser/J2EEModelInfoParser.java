/*    */ package com.sun.xml.rpc.processor.config.parser;
/*    */ 
/*    */ import com.sun.xml.rpc.processor.config.ConfigurationException;
/*    */ import com.sun.xml.rpc.processor.config.J2EEModelInfo;
/*    */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*    */ import com.sun.xml.rpc.processor.modeler.j2ee.JaxRpcMappingXml;
/*    */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*    */ import com.sun.xml.rpc.streaming.XMLReader;
/*    */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*    */ import java.io.IOException;
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
/*    */ 
/*    */ public class J2EEModelInfoParser
/*    */   extends ModelInfoParser
/*    */ {
/*    */   public J2EEModelInfoParser(ProcessorEnvironment env) {
/* 46 */     super(env);
/*    */   }
/*    */   
/*    */   public ModelInfo parse(XMLReader reader) {
/* 50 */     J2EEModelInfo modelInfo = new J2EEModelInfo();
/* 51 */     modelInfo.setJavaPackageName("package_ignored");
/* 52 */     String location = ParserUtil.getMandatoryNonEmptyAttribute(reader, "wsdlLocation");
/*    */     
/* 54 */     modelInfo.setLocation(location);
/* 55 */     String mapping = ParserUtil.getMandatoryNonEmptyAttribute(reader, "location");
/*    */     
/*    */     try {
/* 58 */       modelInfo.setJaxRcpMappingXml(new JaxRpcMappingXml(mapping));
/* 59 */     } catch (IOException e) {
/* 60 */       throw new ConfigurationException("configuration.nestedConfigurationError", new LocalizableExceptionAdapter(e));
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 65 */     return (ModelInfo)modelInfo;
/*    */   }
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\parser\J2EEModelInfoParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */