/*     */ package com.sun.xml.rpc.processor.config.parser;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.config.ConfigurationException;
/*     */ import com.sun.xml.rpc.processor.config.ModelInfo;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.spi.tools.Configuration;
/*     */ import com.sun.xml.rpc.spi.tools.ModelInfo;
/*     */ import com.sun.xml.rpc.spi.tools.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.streaming.XMLReader;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderException;
/*     */ import com.sun.xml.rpc.streaming.XMLReaderFactory;
/*     */ import com.sun.xml.rpc.tools.plugin.ToolPluginFactory;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigurationParser
/*     */ {
/*     */   private ProcessorEnvironment _env;
/*     */   private Map _modelInfoParsers;
/*     */   
/*     */   public ConfigurationParser(ProcessorEnvironment env) {
/*  51 */     this._env = env;
/*  52 */     this._modelInfoParsers = new HashMap<Object, Object>();
/*  53 */     this._modelInfoParsers.put(Constants.QNAME_SERVICE, new RmiModelInfoParser(env));
/*     */     
/*  55 */     this._modelInfoParsers.put(Constants.QNAME_WSDL, new WSDLModelInfoParser(env));
/*     */     
/*  57 */     this._modelInfoParsers.put(Constants.QNAME_MODELFILE, new ModelFileModelInfoParser(env));
/*     */     
/*  59 */     this._modelInfoParsers.put(Constants.QNAME_NO_METADATA, new NoMetadataModelInfoParser(env));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     Iterator<ModelInfoPlugin> i = ToolPluginFactory.getInstance().getExtensions("com.sun.xml.rpc.tools.wscompile", "com.sun.xml.rpc.tools.wscompile.modelInfo");
/*     */ 
/*     */     
/*  69 */     while (i != null && i.hasNext()) {
/*  70 */       ModelInfoPlugin plugin = i.next();
/*  71 */       this._modelInfoParsers.put(plugin.getModelInfoName(), plugin.createModelInfoParser(env));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Configuration parse(InputStream is) {
/*     */     try {
/*  79 */       XMLReader reader = XMLReaderFactory.newInstance().createXMLReader(is);
/*     */       
/*  81 */       reader.next();
/*  82 */       return parseConfiguration(reader);
/*  83 */     } catch (XMLReaderException e) {
/*  84 */       throw new ConfigurationException("configuration.xmlReader", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Configuration parseConfiguration(XMLReader reader) {
/*  91 */     if (!reader.getName().equals(Constants.QNAME_CONFIGURATION)) {
/*  92 */       ParserUtil.failWithFullName("configuration.invalidElement", reader);
/*     */     }
/*     */     
/*  95 */     String version = ParserUtil.getAttribute(reader, "version");
/*     */     
/*  97 */     if (version != null && !version.equals("1.0"))
/*     */     {
/*  99 */       ParserUtil.failWithLocalName("configuration.invalidVersionNumber", reader, version);
/*     */     }
/*     */ 
/*     */     
/* 103 */     Configuration configuration = new Configuration((ProcessorEnvironment)this._env);
/* 104 */     if (reader.nextElementContent() == 1) {
/* 105 */       configuration.setModelInfo((ModelInfo)parseModelInfo(reader));
/*     */     } else {
/* 107 */       ParserUtil.fail("configuration.missing.model", reader);
/*     */     } 
/*     */     
/* 110 */     if (reader.nextElementContent() != 2) {
/* 111 */       ParserUtil.fail("configuration.unexpectedContent", reader);
/*     */     }
/*     */     
/* 114 */     reader.close();
/* 115 */     return (Configuration)configuration;
/*     */   }
/*     */   
/*     */   protected ModelInfo parseModelInfo(XMLReader reader) {
/* 119 */     ModelInfoParser miParser = (ModelInfoParser)this._modelInfoParsers.get(reader.getName());
/*     */     
/* 121 */     if (miParser != null) {
/* 122 */       return miParser.parse(reader);
/*     */     }
/* 124 */     ParserUtil.fail("configuration.unknown.modelInfo", reader);
/* 125 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\config\parser\ConfigurationParser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */