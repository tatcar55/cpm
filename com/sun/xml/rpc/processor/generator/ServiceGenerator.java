/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.ProcessorAction;
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.config.HandlerChainInfo;
/*     */ import com.sun.xml.rpc.processor.config.HandlerInfo;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Service;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
/*     */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.processor.util.StringUtils;
/*     */ import com.sun.xml.rpc.spi.model.JavaInterface;
/*     */ import com.sun.xml.rpc.spi.model.Port;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
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
/*     */ public class ServiceGenerator
/*     */   implements ProcessorAction
/*     */ {
/*     */   private File sourceDir;
/*     */   private ProcessorEnvironment env;
/*     */   private Model model;
/*     */   private Set portClassNames;
/*     */   private String JAXRPCVersion;
/*     */   private String sourceVersion;
/*     */   private boolean donotOverride;
/*     */   
/*     */   public ServiceGenerator() {
/*  73 */     this.sourceDir = null;
/*  74 */     this.env = null;
/*  75 */     this.model = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void perform(Model model, Configuration config, Properties properties) {
/*  82 */     ProcessorEnvironment env = (ProcessorEnvironment)config.getEnvironment();
/*     */     
/*  84 */     ServiceGenerator generator = new ServiceGenerator(env, model, properties);
/*     */     
/*  86 */     generator.doGeneration();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServiceGenerator(ProcessorEnvironment env, Model model, Properties properties) {
/*  93 */     this.env = env;
/*  94 */     this.model = model;
/*  95 */     String key = "sourceDirectory";
/*  96 */     String dirPath = properties.getProperty(key);
/*  97 */     this.sourceDir = new File(dirPath);
/*  98 */     key = "donotOverride";
/*  99 */     this.donotOverride = Boolean.valueOf(properties.getProperty(key)).booleanValue();
/*     */     
/* 101 */     this.JAXRPCVersion = properties.getProperty("JAXRPC Version");
/*     */     
/* 103 */     this.sourceVersion = properties.getProperty("sourceVersion");
/*     */   }
/*     */ 
/*     */   
/*     */   private void doGeneration() {
/* 108 */     this.env.getNames().resetPrefixFactory();
/* 109 */     Service service = null;
/*     */     try {
/* 111 */       for (Iterator<Service> iter = this.model.getServices(); iter.hasNext(); ) {
/* 112 */         service = iter.next();
/* 113 */         generateService(service);
/*     */       } 
/* 115 */     } catch (IOException e) {
/* 116 */       fail("generator.cant.write", service.getName().getLocalPart());
/*     */     } finally {
/* 118 */       this.sourceDir = null;
/* 119 */       this.env = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void generateService(Service service) throws IOException {
/*     */     try {
/* 125 */       JavaInterface intf = service.getJavaInterface();
/* 126 */       String className = this.env.getNames().interfaceImplClassName((JavaInterface)intf);
/* 127 */       String serializerRegistryName = this.env.getNames().serializerRegistryClassName(intf);
/*     */       
/* 129 */       int location = serializerRegistryName.lastIndexOf(".");
/* 130 */       if (service.getName().getLocalPart().equals(serializerRegistryName.substring(0, location)))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 136 */         serializerRegistryName = serializerRegistryName.substring(location + 1, serializerRegistryName.length());
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 141 */       if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/* 142 */         log("Class " + className + " exists. Not overriding.");
/*     */         return;
/*     */       } 
/* 145 */       log("creating service: " + className);
/* 146 */       String interfaceName = this.env.getNames().customJavaTypeClassName(intf);
/* 147 */       File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 155 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 156 */       fi.setFile(classFile);
/* 157 */       fi.setType("ServiceImpl");
/* 158 */       this.env.addGeneratedFile(fi);
/*     */       
/* 160 */       this.portClassNames = new HashSet();
/*     */       
/* 162 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 165 */       GeneratorBase.writePackage(out, className, this.JAXRPCVersion, this.sourceVersion);
/* 166 */       writeImports(out);
/* 167 */       out.pln();
/* 168 */       writeClassDecl(out, className, interfaceName);
/* 169 */       writeStaticMembers(out, service);
/* 170 */       out.pln();
/* 171 */       writeConstructor(out, className, service, serializerRegistryName);
/* 172 */       out.pln();
/* 173 */       writeGenericGetPortMethods(out, service);
/* 174 */       out.pln();
/* 175 */       writeIndividualGetPorts(out, service.getPorts());
/* 176 */       out.pOln("}");
/* 177 */       out.close();
/*     */     }
/* 179 */     catch (Exception e) {
/* 180 */       throw new GeneratorException("generator.nestedGeneratorError", new LocalizableExceptionAdapter(e));
/*     */     }
/*     */     finally {
/*     */       
/* 184 */       this.portClassNames = null;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeImports(IndentingWriter p) throws IOException {
/* 189 */     p.pln("import com.sun.xml.rpc.encoding.*;");
/* 190 */     p.pln("import com.sun.xml.rpc.client.ServiceExceptionImpl;");
/* 191 */     p.pln("import com.sun.xml.rpc.util.exception.*;");
/* 192 */     p.pln("import com.sun.xml.rpc.soap.SOAPVersion;");
/* 193 */     p.pln("import com.sun.xml.rpc.client.HandlerChainImpl;");
/* 194 */     p.pln("import javax.xml.rpc.*;");
/* 195 */     p.pln("import javax.xml.rpc.encoding.*;");
/* 196 */     p.pln("import javax.xml.rpc.handler.HandlerChain;");
/* 197 */     p.pln("import javax.xml.rpc.handler.HandlerInfo;");
/* 198 */     p.pln("import javax.xml.namespace.QName;");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeClassDecl(IndentingWriter p, String className, String interfaceName) throws IOException {
/* 206 */     p.plnI("public class " + Names.stripQualifier(className) + " extends com.sun.xml.rpc.client.BasicService" + " implements " + Names.stripQualifier(interfaceName) + " {");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeStaticMembers(IndentingWriter p, Service service) throws IOException {
/* 217 */     p.p("private static final QName serviceName = ");
/* 218 */     GeneratorUtil.writeNewQName(p, service.getName());
/* 219 */     p.pln(";");
/* 220 */     Iterator<Port> ports = service.getPorts();
/*     */ 
/*     */     
/* 223 */     while (ports.hasNext()) {
/* 224 */       Port port = ports.next();
/* 225 */       String portClass = port.getJavaInterface().getName();
/* 226 */       QName portName = port.getName();
/* 227 */       p.p("private static final QName " + this.env.getNames().getQNameName(portName) + " = ");
/*     */ 
/*     */ 
/*     */       
/* 231 */       GeneratorUtil.writeNewQName(p, portName);
/* 232 */       p.pln(";");
/* 233 */       QName portWsdlName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLPortName");
/*     */ 
/*     */       
/* 236 */       if (!portWsdlName.equals(portName)) {
/* 237 */         p.p("private static final QName " + this.env.getNames().getQNameName(portWsdlName) + " = ");
/*     */ 
/*     */ 
/*     */         
/* 241 */         GeneratorUtil.writeNewQName(p, portWsdlName);
/* 242 */         p.pln(";");
/*     */       } 
/* 244 */       if (!this.portClassNames.contains(portClass)) {
/* 245 */         p.pln("private static final Class " + StringUtils.decapitalize(Names.stripQualifier(portClass)) + "_PortClass = " + portClass + ".class;");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 252 */         this.portClassNames.add(portClass);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeConstructor(IndentingWriter p, String className, Service service, String serializerRegistryName) throws IOException {
/* 263 */     p.plnI("public " + Names.stripQualifier(className) + "() {");
/* 264 */     p.plnI("super(serviceName, new QName[] {");
/* 265 */     p.pI(3);
/* 266 */     Iterator<Port> eachPort = service.getPorts();
/*     */     
/* 268 */     for (int i = 0; eachPort.hasNext(); i++) {
/* 269 */       Port port = eachPort.next();
/* 270 */       if (i > 0)
/* 271 */         p.pln(","); 
/* 272 */       p.p(this.env.getNames().getQNameName(port.getName()));
/*     */     } 
/* 274 */     p.pln();
/* 275 */     p.pOln("},");
/* 276 */     p.pO(2);
/* 277 */     p.pln("new " + serializerRegistryName + "().getRegistry());");
/*     */     
/* 279 */     p.pO();
/* 280 */     eachPort = service.getPorts();
/* 281 */     if (eachPort.hasNext()) {
/* 282 */       p.pln();
/* 283 */       while (eachPort.hasNext()) {
/* 284 */         Port port = eachPort.next();
/* 285 */         HandlerChainInfo portClientHandlers = port.getClientHandlerChainInfo();
/*     */         
/* 287 */         Iterator<HandlerInfo> eachHandler = portClientHandlers.getHandlers();
/* 288 */         if (eachHandler.hasNext()) {
/* 289 */           p.plnI("{");
/* 290 */           p.pln("java.util.List handlerInfos = new java.util.Vector();");
/*     */           
/* 292 */           while (eachHandler.hasNext()) {
/* 293 */             HandlerInfo currentHandler = eachHandler.next();
/*     */             
/* 295 */             Map properties = currentHandler.getProperties();
/* 296 */             String propertiesName = "props";
/* 297 */             p.plnI("{");
/* 298 */             p.pln("java.util.Map " + propertiesName + " = new java.util.HashMap();");
/*     */ 
/*     */ 
/*     */             
/* 302 */             Iterator<Map.Entry> entries = properties.entrySet().iterator();
/*     */             
/* 304 */             while (entries.hasNext()) {
/*     */               
/* 306 */               Map.Entry entry = entries.next();
/* 307 */               p.pln(propertiesName + ".put(\"" + (String)entry.getKey() + "\", \"" + (String)entry.getValue() + "\");");
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 316 */             Object[] headers = currentHandler.getHeaderNames().toArray();
/*     */ 
/*     */             
/* 319 */             if (headers != null && headers.length > 0) {
/* 320 */               p.plnI("javax.xml.namespace.QName[] headers = {");
/* 321 */               for (int j = 0; j < headers.length; j++) {
/* 322 */                 QName hdr = (QName)headers[j];
/*     */                 
/* 324 */                 p.pln("new javax.xml.namespace.QName(\"" + hdr.getNamespaceURI() + "\"" + ", " + "\"" + hdr.getLocalPart() + "\"" + ")" + ((j != headers.length - 1) ? "," : ""));
/*     */               } 
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
/* 336 */               p.pOln("};");
/*     */             } else {
/* 338 */               p.pln("javax.xml.namespace.QName[] headers = null;");
/*     */             } 
/* 340 */             p.pln("HandlerInfo handlerInfo = new HandlerInfo(" + currentHandler.getHandlerClassName() + ".class" + ", " + propertiesName + ", headers);");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 347 */             p.pln("handlerInfos.add(handlerInfo);");
/* 348 */             p.pOln("}");
/*     */           } 
/* 350 */           p.pln("getHandlerRegistry().setHandlerChain(" + this.env.getNames().getQNameName((QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLPortName")) + ", handlerInfos);");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 357 */           p.pOln("}");
/*     */         } 
/*     */       } 
/*     */     } 
/* 361 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeGenericGetPortMethods(IndentingWriter p, Service service) throws IOException {
/* 366 */     Iterator<Port> ports = service.getPorts();
/*     */ 
/*     */ 
/*     */     
/* 370 */     p.plnI("public java.rmi.Remote getPort(javax.xml.namespace.QName portName, java.lang.Class serviceDefInterface) throws javax.xml.rpc.ServiceException {");
/*     */     
/* 372 */     p.plnI("try {");
/* 373 */     while (ports.hasNext()) {
/* 374 */       Port port = ports.next();
/* 375 */       String portClass = port.getJavaInterface().getName();
/* 376 */       String portName = Names.getPortName(port);
/* 377 */       p.plnI("if (portName.equals(" + this.env.getNames().getQNameName(port.getName()) + ") &&");
/*     */ 
/*     */ 
/*     */       
/* 381 */       p.pln("serviceDefInterface.equals(" + StringUtils.decapitalize(Names.stripQualifier(portClass)) + "_PortClass)) {");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 387 */       portName = this.env.getNames().validJavaClassName(portName);
/* 388 */       p.pln("return get" + portName + "();");
/* 389 */       p.pOln("}");
/*     */     } 
/* 391 */     p.pOlnI("} catch (Exception e) {");
/* 392 */     p.pln("throw new ServiceExceptionImpl(new LocalizableExceptionAdapter(e));");
/*     */     
/* 394 */     p.pOln("}");
/* 395 */     p.pln("return super.getPort(portName, serviceDefInterface);");
/* 396 */     p.pOln("}");
/* 397 */     p.pln();
/*     */     
/* 399 */     ports = service.getPorts();
/* 400 */     p.plnI("public java.rmi.Remote getPort(java.lang.Class serviceDefInterface) throws javax.xml.rpc.ServiceException {");
/*     */     
/* 402 */     p.plnI("try {");
/* 403 */     while (ports.hasNext()) {
/* 404 */       Port port = ports.next();
/* 405 */       String portClass = port.getJavaInterface().getName();
/* 406 */       String portName = Names.getPortName(port);
/*     */ 
/*     */       
/* 409 */       portName = this.env.getNames().validJavaClassName(portName);
/* 410 */       p.plnI("if (serviceDefInterface.equals(" + StringUtils.decapitalize(Names.stripQualifier(portClass)) + "_PortClass)) {");
/*     */ 
/*     */ 
/*     */       
/* 414 */       p.pln("return get" + portName + "();");
/* 415 */       p.pOln("}");
/*     */     } 
/* 417 */     p.pOlnI("} catch (Exception e) {");
/* 418 */     p.pln("throw new ServiceExceptionImpl(new LocalizableExceptionAdapter(e));");
/*     */     
/* 420 */     p.pOln("}");
/* 421 */     p.pln("return super.getPort(serviceDefInterface);");
/* 422 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeIndividualGetPorts(IndentingWriter p, Iterator<Port> ports) throws IOException {
/* 430 */     while (ports.hasNext()) {
/* 431 */       Port port = ports.next();
/* 432 */       String portClass = port.getJavaInterface().getName();
/* 433 */       String portName = Names.getPortName(port);
/* 434 */       QName portWsdlName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLPortName");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 439 */       portName = this.env.getNames().validJavaClassName(portName);
/* 440 */       p.plnI("public " + portClass + " get" + portName + "() {");
/* 441 */       Set roles = port.getClientHandlerChainInfo().getRoles();
/* 442 */       p.p("java.lang.String[] roles = new java.lang.String[] {");
/*     */       
/* 444 */       boolean first = true;
/* 445 */       Iterator<String> i = roles.iterator();
/* 446 */       while (i.hasNext()) {
/* 447 */         if (!first) {
/* 448 */           p.p(", ");
/*     */         } else {
/* 450 */           first = false;
/*     */         } 
/* 452 */         p.p("\"" + i.next() + "\"");
/*     */       } 
/*     */       
/* 455 */       p.pln("};");
/*     */       
/* 457 */       p.pln("HandlerChainImpl handlerChain = new HandlerChainImpl(getHandlerRegistry().getHandlerChain(" + this.env.getNames().getQNameName(portWsdlName) + "));");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 462 */       p.pln("handlerChain.setRoles(roles);");
/*     */       
/* 464 */       p.pln(this.env.getNames().stubFor((Port)port) + " stub = new " + this.env.getNames().stubFor((Port)port) + "(handlerChain);");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 470 */       p.plnI("try {");
/* 471 */       p.pln("stub._initialize(super.internalTypeRegistry);");
/* 472 */       p.pOlnI("} catch (JAXRPCException e) {");
/* 473 */       p.pln("throw e;");
/* 474 */       p.pOlnI("} catch (Exception e) {");
/* 475 */       p.pln("throw new JAXRPCException(e.getMessage(), e);");
/* 476 */       p.pOln("}");
/* 477 */       p.pln("return stub;");
/* 478 */       p.pOln("}");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void log(String msg) {
/* 483 */     if (this.env.verbose()) {
/* 484 */       System.out.println("[" + Names.stripQualifier(getClass().getName()) + ": " + msg + "]");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fail(String key) {
/* 494 */     throw new GeneratorException(key);
/*     */   }
/*     */   
/*     */   protected void fail(String key, String arg) {
/* 498 */     throw new GeneratorException(key, arg);
/*     */   }
/*     */   
/*     */   protected void fail(String key, String arg1, String arg2) {
/* 502 */     throw new GeneratorException(key, new Object[] { arg1, arg2 });
/*     */   }
/*     */   
/*     */   protected void fail(Localizable arg) {
/* 506 */     throw new GeneratorException("generator.nestedGeneratorError", arg);
/*     */   }
/*     */   
/*     */   protected void fail(Throwable arg) {
/* 510 */     throw new GeneratorException("generator.nestedGeneratorError", new LocalizableExceptionAdapter(arg));
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\ServiceGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */