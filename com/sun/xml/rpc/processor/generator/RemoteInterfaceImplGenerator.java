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
/*     */ import com.sun.xml.rpc.spi.model.JavaInterface;
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
/*     */ public class RemoteInterfaceImplGenerator
/*     */   implements ProcessorAction
/*     */ {
/*     */   private Port port;
/*     */   private boolean donotOverride;
/*     */   private File sourceDir;
/*     */   private ProcessorEnvironment env;
/*     */   
/*     */   public void perform(Model model, Configuration config, Properties options) {
/*     */     try {
/*  67 */       this.env = (ProcessorEnvironment)config.getEnvironment();
/*  68 */       this.env.getNames().resetPrefixFactory();
/*  69 */       String key = "sourceDirectory";
/*  70 */       String dirPath = options.getProperty(key);
/*  71 */       this.sourceDir = new File(dirPath);
/*  72 */       key = "donotOverride";
/*  73 */       this.donotOverride = Boolean.valueOf(options.getProperty(key)).booleanValue();
/*     */       
/*  75 */       Set<String> interfaceNames = new HashSet();
/*     */       
/*  77 */       for (Iterator<Service> iter = model.getServices(); iter.hasNext(); ) {
/*  78 */         Service service = iter.next();
/*     */         
/*  80 */         for (Iterator<Port> iter2 = service.getPorts(); iter2.hasNext(); ) {
/*  81 */           Port port = iter2.next();
/*  82 */           JavaInterface intf = port.getJavaInterface();
/*  83 */           if (!interfaceNames.contains(intf.getName())) {
/*  84 */             process(port);
/*  85 */             interfaceNames.add(intf.getName());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } finally {
/*  90 */       this.sourceDir = null;
/*  91 */       this.env = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void process(Port port) {
/*  96 */     JavaInterface intf = port.getJavaInterface();
/*  97 */     if (intf.getImpl() == null) {
/*  98 */       String className = this.env.getNames().interfaceImplClassName((JavaInterface)intf);
/*  99 */       if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/* 100 */         log("Class " + className + " exists. Not overriding.");
/*     */         return;
/*     */       } 
/* 103 */       generateClassFor(className, port);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void generateClassFor(String className, Port port) {
/* 108 */     JavaInterface intf = port.getJavaInterface();
/*     */     try {
/* 110 */       File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 118 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 119 */       fi.setFile(classFile);
/* 120 */       fi.setType("RemoteInterface");
/* 121 */       this.env.addGeneratedFile(fi);
/*     */       
/* 123 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 126 */       GeneratorBase.writePackageOnly(out, className);
/* 127 */       out.plnI("public class " + Names.stripQualifier(className) + " implements " + this.env.getNames().customJavaTypeClassName(intf) + ", java.rmi.Remote {");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 134 */       for (Iterator<JavaMethod> iter = intf.getMethods(); iter.hasNext(); ) {
/* 135 */         JavaMethod method = iter.next();
/* 136 */         out.p("public ");
/* 137 */         if (method.getReturnType() == null) {
/* 138 */           out.p("void");
/*     */         } else {
/* 140 */           out.p(method.getReturnType().getName());
/*     */         } 
/* 142 */         out.p(" ");
/* 143 */         out.p(method.getName());
/* 144 */         out.p("(");
/* 145 */         boolean first = true;
/*     */         
/* 147 */         Iterator<JavaParameter> iter2 = method.getParameters();
/* 148 */         while (iter2.hasNext()) {
/*     */           
/* 150 */           JavaParameter parameter = iter2.next();
/* 151 */           if (!first) {
/* 152 */             out.p(", ");
/*     */           }
/* 154 */           if (parameter.isHolder()) {
/* 155 */             out.p(this.env.getNames().holderClassName(port, parameter.getType()));
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 160 */             out.p(this.env.getNames().typeClassName(parameter.getType()));
/*     */           } 
/*     */           
/* 163 */           out.p(" ");
/* 164 */           out.p(parameter.getName());
/* 165 */           first = false;
/*     */         } 
/* 167 */         out.plnI(") throws ");
/* 168 */         Iterator<String> exceptions = method.getExceptions();
/*     */         
/* 170 */         while (exceptions.hasNext()) {
/* 171 */           String exception = exceptions.next();
/* 172 */           out.p(exception + ", ");
/*     */         } 
/* 174 */         out.pln(" java.rmi.RemoteException {");
/* 175 */         if (method.getReturnType() != null && !method.getReturnType().getName().equals("void")) {
/*     */           
/* 177 */           out.pln();
/* 178 */           out.pln(method.getReturnType().getName() + " _retVal = " + method.getReturnType().getInitString() + ";");
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 183 */           out.pln("return _retVal;");
/*     */         } 
/* 185 */         out.pOln("}");
/*     */       } 
/*     */       
/* 188 */       out.pOln("}");
/* 189 */       out.close();
/*     */     }
/* 191 */     catch (Exception e) {
/* 192 */       throw new GeneratorException("generator.nestedGeneratorError", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void log(String msg) {
/* 199 */     if (this.env.verbose())
/* 200 */       System.out.println("[" + Names.stripQualifier(getClass().getName()) + ": " + msg + "]"); 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\RemoteInterfaceImplGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */