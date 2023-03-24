/*     */ package com.sun.xml.rpc.tools.wscompile;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.Processor;
/*     */ import com.sun.xml.rpc.processor.ProcessorAction;
/*     */ import com.sun.xml.rpc.processor.config.J2EEModelInfo;
/*     */ import com.sun.xml.rpc.processor.config.parser.Constants;
/*     */ import com.sun.xml.rpc.processor.config.parser.J2EEModelInfoParser;
/*     */ import com.sun.xml.rpc.processor.config.parser.ModelInfoParser;
/*     */ import com.sun.xml.rpc.processor.config.parser.ModelInfoPlugin;
/*     */ import com.sun.xml.rpc.processor.generator.JaxRpcMappingGenerator;
/*     */ import com.sun.xml.rpc.processor.modeler.j2ee.JaxRpcMappingXml;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.spi.tools.ModelInfo;
/*     */ import com.sun.xml.rpc.tools.plugin.ToolPlugin;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import com.sun.xml.rpc.util.localization.LocalizableMessageFactory;
/*     */ import com.sun.xml.rpc.util.localization.Localizer;
/*     */ import java.io.File;
/*     */ import java.net.URL;
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
/*     */ public class J2EEToolPlugin
/*     */   extends ToolPlugin
/*     */   implements UsageIf, ModelInfoPlugin, ProcessorActionsIf
/*     */ {
/*     */   private LocalizableMessageFactory messageFactory;
/*  57 */   protected Localizer localizer = new Localizer();
/*  58 */   protected File mappingFile = null;
/*     */   
/*     */   public J2EEToolPlugin() {
/*  61 */     this.messageFactory = new LocalizableMessageFactory("com.sun.xml.rpc.resources.j2ee");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public QName getModelInfoName() {
/*  69 */     return Constants.QNAME_J2EE_MAPPING_FILE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelInfoParser createModelInfoParser(ProcessorEnvironment env) {
/*  76 */     return (ModelInfoParser)new J2EEModelInfoParser(env);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelInfo createModelInfo(URL mappingFile) throws Exception {
/*  83 */     JaxRpcMappingXml mapping = new JaxRpcMappingXml(mappingFile.toExternalForm());
/*     */     
/*  85 */     J2EEModelInfo modelInfo = new J2EEModelInfo(mapping);
/*  86 */     return (ModelInfo)modelInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelInfo createModelInfo() {
/*  93 */     return (ModelInfo)new J2EEModelInfo();
/*     */   }
/*     */ 
/*     */   
/*     */   public Localizable getOptionsUsage() {
/*  98 */     return this.messageFactory.getMessage("j2ee.usage.options", (Object[])null);
/*     */   }
/*     */   
/*     */   public Localizable getFeaturesUsage() {
/* 102 */     return null;
/*     */   }
/*     */   
/*     */   public Localizable getInternalUsage() {
/* 106 */     return null;
/*     */   }
/*     */   
/*     */   public Localizable getExamplesUsage() {
/* 110 */     return null;
/*     */   }
/*     */   
/*     */   public boolean parseArguments(String[] args, UsageIf.UsageError err) {
/* 114 */     this.mappingFile = null;
/*     */     
/* 116 */     for (int i = 0; i < args.length; i++) {
/* 117 */       if (args[i] != null && args[i].equals("-mapping")) {
/* 118 */         if (i + 1 < args.length) {
/* 119 */           if (this.mappingFile != null) {
/* 120 */             err.msg = this.messageFactory.getMessage("j2ee.duplicateOption", new Object[] { "-mapping" });
/*     */ 
/*     */ 
/*     */             
/* 124 */             return false;
/*     */           } 
/* 126 */           args[i] = null;
/* 127 */           this.mappingFile = new File(args[++i]);
/* 128 */           args[i] = null;
/*     */         } else {
/* 130 */           err.msg = this.messageFactory.getMessage("j2ee.missingOptionArgument", new Object[] { "-mapping" });
/*     */ 
/*     */ 
/*     */           
/* 134 */           return false;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 139 */     return true;
/*     */   }
/*     */   
/*     */   public void registerActions(Processor processor) {
/* 143 */     if (this.mappingFile != null)
/* 144 */       processor.add((ProcessorAction)new JaxRpcMappingGenerator(this.mappingFile)); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\tools\wscompile\J2EEToolPlugin.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */