/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.ProcessorAction;
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Service;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
/*     */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.util.VersionUtil;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
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
/*     */ public class ServiceInterfaceGenerator
/*     */   implements ProcessorAction
/*     */ {
/*     */   private File sourceDir;
/*     */   private ProcessorEnvironment env;
/*     */   private String JAXRPCVersion;
/*     */   private boolean donotOverride;
/*     */   private String sourceVersion;
/*     */   
/*     */   public void perform(Model model, Configuration config, Properties options) {
/*     */     try {
/*  70 */       this.env = (ProcessorEnvironment)config.getEnvironment();
/*  71 */       this.env.getNames().resetPrefixFactory();
/*  72 */       String key = "sourceDirectory";
/*  73 */       String dirPath = options.getProperty(key);
/*  74 */       key = "donotOverride";
/*  75 */       this.donotOverride = Boolean.valueOf(options.getProperty(key)).booleanValue();
/*     */       
/*  77 */       this.sourceDir = new File(dirPath);
/*  78 */       this.JAXRPCVersion = options.getProperty("JAXRPC Version");
/*     */       
/*  80 */       key = "sourceVersion";
/*  81 */       this.sourceVersion = options.getProperty(key);
/*     */       
/*  83 */       for (Iterator<Service> iter = model.getServices(); iter.hasNext(); ) {
/*  84 */         Service service = iter.next();
/*  85 */         process(service);
/*     */       } 
/*     */     } finally {
/*  88 */       this.sourceDir = null;
/*  89 */       this.env = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void process(Service service) {
/*     */     try {
/*  95 */       JavaInterface intf = service.getJavaInterface();
/*  96 */       String className = this.env.getNames().customJavaTypeClassName(intf);
/*  97 */       if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/*  98 */         log("Class " + className + " exists. Not overriding.");
/*     */         return;
/*     */       } 
/* 101 */       log("creating service interface: " + className);
/* 102 */       File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 111 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 112 */       fi.setFile(classFile);
/* 113 */       fi.setType("Service");
/* 114 */       this.env.addGeneratedFile(fi);
/*     */       
/* 116 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 119 */       GeneratorBase.writePackage(out, className, this.JAXRPCVersion, this.sourceVersion);
/* 120 */       out.pln("import javax.xml.rpc.*;");
/* 121 */       out.pln();
/* 122 */       out.plnI("public interface " + Names.stripQualifier(className) + " extends javax.xml.rpc.Service {");
/*     */ 
/*     */ 
/*     */       
/* 126 */       Iterator<Port> ports = service.getPorts();
/*     */ 
/*     */ 
/*     */       
/* 130 */       while (ports.hasNext()) {
/* 131 */         Port port = ports.next();
/* 132 */         String portClass = port.getJavaInterface().getName();
/* 133 */         String portName = Names.getPortName(port);
/*     */ 
/*     */         
/* 136 */         portName = this.env.getNames().validJavaClassName(portName);
/* 137 */         String getPortMethodStr = "public " + portClass + " get" + portName + "()";
/* 138 */         if (VersionUtil.isVersion101(this.sourceVersion) || VersionUtil.isVersion103(this.sourceVersion)) {
/*     */           
/* 140 */           getPortMethodStr = getPortMethodStr + ";";
/*     */         } else {
/* 142 */           getPortMethodStr = getPortMethodStr + " throws ServiceException;";
/*     */         } 
/* 144 */         out.pln(getPortMethodStr);
/*     */       } 
/* 146 */       out.pOln("}");
/* 147 */       out.close();
/*     */     }
/* 149 */     catch (Exception e) {
/* 150 */       throw new GeneratorException("generator.nestedGeneratorError", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void log(String msg) {
/* 157 */     if (this.env.verbose())
/* 158 */       System.out.println("[" + Names.stripQualifier(getClass().getName()) + ": " + msg + "]"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\ServiceInterfaceGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */