/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.ProcessorAction;
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Service;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaMethod;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaParameter;
/*     */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
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
/*     */ public class RemoteInterfaceGenerator
/*     */   implements ProcessorAction
/*     */ {
/*     */   private boolean donotOverride;
/*     */   private File sourceDir;
/*     */   private ProcessorEnvironment env;
/*     */   private String JAXRPCVersion;
/*     */   private String sourceVersion;
/*     */   
/*     */   public void perform(Model model, Configuration config, Properties options) {
/*     */     try {
/*  69 */       this.env = (ProcessorEnvironment)config.getEnvironment();
/*  70 */       this.env.getNames().resetPrefixFactory();
/*  71 */       String key = "sourceDirectory";
/*  72 */       String dirPath = options.getProperty(key);
/*  73 */       key = "donotOverride";
/*  74 */       this.donotOverride = Boolean.valueOf(options.getProperty(key)).booleanValue();
/*     */       
/*  76 */       this.sourceDir = new File(dirPath);
/*  77 */       Set<String> interfaceNames = new HashSet();
/*  78 */       this.JAXRPCVersion = options.getProperty("JAXRPC Version");
/*     */       
/*  80 */       this.sourceVersion = options.getProperty("sourceVersion");
/*     */ 
/*     */       
/*  83 */       String modelerName = (String)model.getProperty("com.sun.xml.rpc.processor.model.ModelerName");
/*     */ 
/*     */       
/*  86 */       if (modelerName != null && modelerName.equals("com.sun.xml.rpc.processor.modeler.rmi.RmiModeler")) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  93 */       for (Iterator<Service> iter = model.getServices(); iter.hasNext(); ) {
/*  94 */         Service service = iter.next();
/*     */         
/*  96 */         for (Iterator<Port> iter2 = service.getPorts(); iter2.hasNext(); ) {
/*  97 */           Port port = iter2.next();
/*  98 */           JavaInterface intf = port.getJavaInterface();
/*  99 */           if (!interfaceNames.contains(intf.getName())) {
/* 100 */             generateClassFor(port);
/* 101 */             interfaceNames.add(intf.getName());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 106 */       this.sourceDir = null;
/* 107 */       this.env = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void generateClassFor(Port port) {
/* 112 */     JavaInterface intf = port.getJavaInterface();
/*     */     try {
/* 114 */       String className = this.env.getNames().customJavaTypeClassName(intf);
/* 115 */       if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/* 116 */         log("Class " + className + " exists. Not overriding.");
/*     */         return;
/*     */       } 
/* 119 */       File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 127 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 128 */       fi.setFile(classFile);
/* 129 */       fi.setType("RemoteInterface");
/* 130 */       this.env.addGeneratedFile(fi);
/*     */       
/* 132 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 135 */       GeneratorBase.writePackage(out, className, this.JAXRPCVersion, this.sourceVersion);
/* 136 */       out.plnI("public interface " + Names.stripQualifier(className) + " extends java.rmi.Remote {");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 141 */       for (Iterator<JavaMethod> iter = intf.getMethods(); iter.hasNext(); ) {
/* 142 */         JavaMethod method = iter.next();
/* 143 */         out.p("public ");
/* 144 */         if (method.getReturnType() == null) {
/* 145 */           out.p("void");
/*     */         } else {
/* 147 */           out.p(method.getReturnType().getName());
/*     */         } 
/* 149 */         out.p(" ");
/* 150 */         out.p(method.getName());
/* 151 */         out.p("(");
/* 152 */         boolean first = true;
/*     */         
/* 154 */         Iterator<JavaParameter> iter2 = method.getParameters();
/* 155 */         while (iter2.hasNext()) {
/*     */           
/* 157 */           JavaParameter parameter = iter2.next();
/* 158 */           if (!first) {
/* 159 */             out.p(", ");
/*     */           }
/* 161 */           if (parameter.isHolder()) {
/* 162 */             out.p(this.env.getNames().holderClassName(port, parameter.getType()));
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 167 */             out.p(this.env.getNames().typeClassName(parameter.getType()));
/*     */           } 
/*     */           
/* 170 */           out.p(" ");
/* 171 */           out.p(parameter.getName());
/* 172 */           first = false;
/*     */         } 
/* 174 */         out.plnI(") throws ");
/* 175 */         Iterator<String> exceptions = method.getExceptions();
/*     */         
/* 177 */         while (exceptions.hasNext()) {
/* 178 */           String exception = exceptions.next();
/* 179 */           out.p(exception + ", ");
/*     */         } 
/* 181 */         out.pln(" java.rmi.RemoteException;");
/* 182 */         out.pO();
/*     */       } 
/*     */       
/* 185 */       out.pOln("}");
/* 186 */       out.close();
/*     */     }
/* 188 */     catch (Exception e) {
/* 189 */       throw new GeneratorException("generator.nestedGeneratorError", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void log(String msg) {
/* 196 */     if (this.env.verbose())
/* 197 */       System.out.println("[" + Names.stripQualifier(getClass().getName()) + ": " + msg + "]"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\RemoteInterfaceGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */