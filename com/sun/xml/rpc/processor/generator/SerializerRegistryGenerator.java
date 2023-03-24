/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.client.BasicService;
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.generator.writer.SerializerWriter;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Parameter;
/*     */ import com.sun.xml.rpc.processor.model.Request;
/*     */ import com.sun.xml.rpc.processor.model.Response;
/*     */ import com.sun.xml.rpc.processor.model.Service;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAllType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralFragmentType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralListType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPAnyType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPCustomType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPListType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPNamespaceConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.soap.SOAPWSDLConstants;
/*     */ import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import javax.xml.rpc.encoding.SerializerFactory;
/*     */ import javax.xml.rpc.encoding.TypeMapping;
/*     */ import javax.xml.rpc.encoding.TypeMappingRegistry;
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
/*     */ public class SerializerRegistryGenerator
/*     */   extends GeneratorBase
/*     */ {
/*     */   private static final String SERIALIZER_FACTORY = "serializerFactory";
/*     */   private static final String DESERIALIZER_FACTORY = "deserializerFactory";
/*     */   private static final String MAPPING = "mapping";
/*     */   private static final String LITERAL_MAPPING = "mapping2";
/*  94 */   private SOAPVersion soapVer = SOAPVersion.SOAP_11;
/*  95 */   private SOAPEncodingConstants soapEncodingConstants = null;
/*  96 */   private SOAPNamespaceConstants soapNamespaceConstants = null;
/*  97 */   private SOAPWSDLConstants soapWSDLConstants = null;
/*     */ 
/*     */   
/*     */   private boolean haveCustom = false;
/*     */ 
/*     */   
/*     */   private Model model;
/*     */   
/*     */   private Set visitedTypes;
/*     */ 
/*     */   
/*     */   public SerializerRegistryGenerator() {
/* 109 */     this(SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */   
/*     */   public SerializerRegistryGenerator(SOAPVersion ver) {
/* 114 */     init(ver);
/*     */   }
/*     */   
/*     */   private void init(SOAPVersion ver) {
/* 118 */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(ver);
/*     */     
/* 120 */     this.soapNamespaceConstants = SOAPConstantsFactory.getSOAPNamespaceConstants(ver);
/*     */     
/* 122 */     this.soapWSDLConstants = SOAPConstantsFactory.getSOAPWSDLConstants(ver);
/* 123 */     this.soapVer = ver;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties) {
/* 130 */     return new SerializerRegistryGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/* 137 */     return new SerializerRegistryGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SerializerRegistryGenerator(Model model, Configuration config, Properties properties) {
/* 144 */     this(model, config, properties, SOAPVersion.SOAP_11);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SerializerRegistryGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/* 152 */     super(model, config, properties);
/* 153 */     init(ver);
/*     */   }
/*     */   
/*     */   protected void preVisitModel(Model model) throws Exception {
/* 157 */     this.model = model;
/*     */   }
/*     */   
/*     */   protected void preVisitService(Service service) throws Exception {
/* 161 */     super.preVisitService(service);
/* 162 */     this.visitedTypes = new HashSet();
/* 163 */     Iterator<AbstractType> types = this.model.getExtraTypes();
/*     */     
/* 165 */     while (types.hasNext()) {
/* 166 */       AbstractType type = types.next();
/* 167 */       if (type.isSOAPType()) {
/* 168 */         ((SOAPType)type).accept(this); continue;
/*     */       } 
/* 170 */       ((LiteralType)type).accept(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void postVisitService(Service service) throws Exception {
/*     */     try {
/* 176 */       generateSerializerRegistry(service);
/* 177 */     } catch (IOException e) {
/* 178 */       fail("generator.cant.write", service.getName().getLocalPart());
/*     */     } 
/* 180 */     this.visitedTypes = null;
/* 181 */     this.servicePackage = null;
/*     */   }
/*     */   
/*     */   protected void visitParameter(Parameter param) throws Exception {
/* 185 */     AbstractType type = param.getType();
/* 186 */     if (type.isSOAPType()) {
/* 187 */       ((SOAPType)type).accept(this);
/*     */     } else {
/* 189 */       ((LiteralType)type).accept(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void preVisitResponse(Response response) throws Exception {
/* 194 */     Iterator<Parameter> iter = response.getParameters();
/*     */     
/* 196 */     while (iter.hasNext()) {
/* 197 */       ((Parameter)iter.next()).accept(this);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void preVisitRequest(Request request) throws Exception {
/* 202 */     Iterator<Parameter> iter = request.getParameters();
/*     */     
/* 204 */     while (iter.hasNext()) {
/* 205 */       ((Parameter)iter.next()).accept(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void visit(Fault fault) throws Exception {
/* 210 */     if (fault.getBlock().getType().isSOAPType()) {
/* 211 */       ((SOAPType)fault.getBlock().getType()).accept(this);
/* 212 */     } else if (fault.getBlock().getType().isLiteralType()) {
/* 213 */       ((LiteralType)fault.getBlock().getType()).accept(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(SOAPCustomType type) throws Exception {
/* 219 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 222 */     typeVisited((AbstractType)type);
/* 223 */     this.haveCustom = true;
/*     */   }
/*     */   
/*     */   public void visit(SOAPSimpleType type) throws Exception {
/* 227 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 230 */     typeVisited((AbstractType)type);
/*     */   }
/*     */   
/*     */   public void visit(SOAPAnyType type) throws Exception {
/* 234 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 237 */     typeVisited((AbstractType)type);
/* 238 */     this.haveCustom = true;
/*     */   }
/*     */   
/*     */   public void visit(SOAPEnumerationType type) throws Exception {
/* 242 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 245 */     typeVisited((AbstractType)type);
/*     */   }
/*     */   
/*     */   public void preVisitSOAPArrayType(SOAPArrayType type) throws Exception {
/* 249 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 252 */     typeVisited((AbstractType)type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void preVisitSOAPStructureType(SOAPStructureType type) throws Exception {
/* 257 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 260 */     typeVisited((AbstractType)type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(LiteralSimpleType type) throws Exception {
/* 265 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 268 */     typeVisited((AbstractType)type);
/*     */   }
/*     */   
/*     */   public void visit(LiteralFragmentType type) throws Exception {
/* 272 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 275 */     typeVisited((AbstractType)type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void preVisitLiteralArrayWrapperType(LiteralArrayWrapperType type) throws Exception {
/* 280 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 283 */     typeVisited((AbstractType)type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void preVisitLiteralSequenceType(LiteralSequenceType type) throws Exception {
/* 288 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 291 */     typeVisited((AbstractType)type);
/*     */   }
/*     */   
/*     */   public void preVisitLiteralAllType(LiteralAllType type) throws Exception {
/* 295 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 298 */     typeVisited((AbstractType)type);
/*     */   }
/*     */   
/*     */   public void visit(LiteralEnumerationType type) throws Exception {
/* 302 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 305 */     typeVisited((AbstractType)type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(LiteralListType type) throws Exception {
/* 310 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 313 */     typeVisited((AbstractType)type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(SOAPListType type) throws Exception {
/* 318 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 321 */     typeVisited((AbstractType)type);
/*     */   }
/*     */   
/*     */   private boolean haveVisited(AbstractType type) {
/* 325 */     return this.visitedTypes.contains(type);
/*     */   }
/*     */   
/*     */   private void typeVisited(AbstractType type) {
/* 329 */     this.visitedTypes.add(type);
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateSerializerRegistry(Service service) throws IOException {
/*     */     try {
/* 335 */       JavaInterface intf = service.getJavaInterface();
/* 336 */       String className = this.env.getNames().serializerRegistryClassName(intf);
/* 337 */       if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/* 338 */         log("Class " + className + " exists. Not overriding.");
/*     */         return;
/*     */       } 
/* 341 */       log("creating serializer registry: " + className);
/* 342 */       File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 350 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 351 */       fi.setFile(classFile);
/* 352 */       fi.setType("SerializerRegistry");
/* 353 */       this.env.addGeneratedFile(fi);
/*     */       
/* 355 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 358 */       writePackage(out, className);
/* 359 */       writeImports(out);
/* 360 */       out.pln();
/* 361 */       writeClassDecl(out, className);
/* 362 */       writeConstructor(out, className);
/* 363 */       out.pln();
/* 364 */       writeGetRegistry(out);
/* 365 */       out.pln();
/* 366 */       writeStatics(out);
/* 367 */       out.pOln("}");
/* 368 */       out.close();
/* 369 */     } catch (Exception e) {
/* 370 */       throw new GeneratorException("generator.nestedGeneratorError", new LocalizableExceptionAdapter(e));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeImports(IndentingWriter p) throws IOException {
/* 377 */     p.pln("import com.sun.xml.rpc.client.BasicService;");
/* 378 */     p.pln("import com.sun.xml.rpc.encoding.*;");
/* 379 */     p.pln("import com.sun.xml.rpc.encoding.simpletype.*;");
/* 380 */     p.pln("import com.sun.xml.rpc.encoding.soap.*;");
/* 381 */     p.pln("import com.sun.xml.rpc.encoding.literal.*;");
/* 382 */     p.pln("import com.sun.xml.rpc.soap.SOAPVersion;");
/* 383 */     p.pln("import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;");
/* 384 */     p.pln("import javax.xml.rpc.*;");
/* 385 */     p.pln("import javax.xml.rpc.encoding.*;");
/* 386 */     p.pln("import javax.xml.namespace.QName;");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeClassDecl(IndentingWriter p, String className) throws IOException {
/* 391 */     p.plnI("public class " + Names.stripQualifier(className) + " implements SerializerConstants {");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeStatics(IndentingWriter p) throws IOException, Exception {
/* 399 */     p.plnI("private static void registerSerializer(TypeMapping mapping, java.lang.Class javaType, javax.xml.namespace.QName xmlType,");
/*     */     
/* 401 */     p.pln("Serializer ser) {");
/* 402 */     p.plnI("mapping.register(javaType, xmlType, new SingletonSerializerFactory(ser),");
/*     */     
/* 404 */     p.pln("new SingletonDeserializerFactory((Deserializer)ser));");
/* 405 */     p.pO();
/* 406 */     p.pOln("}");
/* 407 */     p.pln();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean mappingExistsForType(TypeMapping mapping, AbstractType type) {
/*     */     try {
/*     */       Class<short> clazz;
/* 414 */       Class<boolean> cls = null;
/* 415 */       String javaName = type.getJavaType().getName();
/* 416 */       if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/* 417 */         if (javaName.equals(boolean.class.toString())) {
/* 418 */           cls = boolean.class;
/* 419 */         } else if (javaName.equals("byte")) {
/* 420 */           Class<byte> clazz1 = byte.class;
/* 421 */         } else if (javaName.equals("double")) {
/* 422 */           Class<double> clazz1 = double.class;
/* 423 */         } else if (javaName.equals("int")) {
/* 424 */           Class<int> clazz1 = int.class;
/* 425 */         } else if (javaName.equals("float")) {
/* 426 */           Class<float> clazz1 = float.class;
/* 427 */         } else if (javaName.equals("long")) {
/* 428 */           Class<long> clazz1 = long.class;
/* 429 */         } else if (javaName.equals("short")) {
/* 430 */           clazz = short.class;
/*     */         } 
/*     */       }
/* 433 */       if (clazz == null) {
/* 434 */         if (javaName.equals("byte[]")) {
/* 435 */           Class<byte[]> clazz1 = byte[].class;
/*     */         }
/*     */         
/* 438 */         if (javaName.equals("java.lang.String[]")) {
/* 439 */           Class<String[]> clazz1 = String[].class;
/*     */         } else {
/* 441 */           clazz = (Class)Class.forName(javaName);
/*     */         } 
/*     */       } 
/* 444 */       SerializerFactory factory = mapping.getSerializer(clazz, type.getName());
/*     */       
/* 446 */       if (factory != null) {
/* 447 */         return true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 459 */     catch (Exception e) {}
/*     */     
/* 461 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeConstructor(IndentingWriter p, String className) throws IOException {
/* 466 */     p.plnI("public " + Names.stripQualifier(className) + "() {");
/* 467 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeGetRegistry(IndentingWriter p) throws IOException, ClassNotFoundException {
/* 472 */     p.plnI("public TypeMappingRegistry getRegistry() {");
/*     */     
/* 474 */     Set<String> processedTypes = new HashSet();
/*     */ 
/*     */     
/* 477 */     p.pln();
/* 478 */     p.pln("TypeMappingRegistry registry = BasicService.createStandardTypeMappingRegistry();");
/*     */     
/* 480 */     p.pln("TypeMapping mapping12 = registry.getTypeMapping(SOAP12Constants.NS_SOAP_ENCODING);");
/*     */     
/* 482 */     p.pln("TypeMapping mapping = registry.getTypeMapping(SOAPConstants.NS_SOAP_ENCODING);");
/*     */     
/* 484 */     p.pln("TypeMapping mapping2 = registry.getTypeMapping(\"\");");
/* 485 */     TypeMappingRegistry registry = BasicService.createStandardTypeMappingRegistry();
/*     */     
/* 487 */     TypeMapping mapping = registry.getTypeMapping("http://schemas.xmlsoap.org/soap/encoding/");
/*     */ 
/*     */ 
/*     */     
/* 491 */     TypeMapping literalMapping = registry.getTypeMapping("");
/*     */ 
/*     */     
/* 494 */     TypeMapping mapping12 = registry.getTypeMapping("http://www.w3.org/2002/06/soap-encoding");
/*     */     
/* 496 */     Iterator<AbstractType> types = this.visitedTypes.iterator();
/* 497 */     while (types.hasNext()) {
/* 498 */       AbstractType type = types.next();
/* 499 */       if (type.getJavaType().getName().equals("void")) {
/*     */         continue;
/*     */       }
/* 502 */       String key = genKey(type);
/* 503 */       if (processedTypes.contains(key)) {
/*     */         continue;
/*     */       }
/* 506 */       processedTypes.add(key);
/* 507 */       SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, type);
/*     */       
/* 509 */       if (writer instanceof com.sun.xml.rpc.processor.generator.writer.SimpleTypeSerializerWriter || writer instanceof com.sun.xml.rpc.processor.generator.writer.CollectionSerializerWriter || writer instanceof com.sun.xml.rpc.processor.generator.writer.LiteralSimpleSerializerWriter || writer instanceof com.sun.xml.rpc.processor.generator.writer.DynamicSerializerWriter) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 514 */         if (mappingExistsForType(literalMapping, type) && type.isLiteralType()) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 520 */         if ((mappingExistsForType(mapping, type) || mappingExistsForType(mapping12, type)) && type.isSOAPType()) {
/*     */           continue;
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 531 */       p.plnI("{");
/* 532 */       if (type.isSOAPType()) {
/*     */         
/* 534 */         if (type.getVersion().equals(SOAPVersion.SOAP_12.toString())) {
/* 535 */           writer.registerSerializer(p, this.encodeTypes, this.multiRefEncoding, "mapping12");
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 541 */           writer.registerSerializer(p, this.encodeTypes, this.multiRefEncoding, "mapping");
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 546 */       else if (type.isLiteralType()) {
/* 547 */         writer.registerSerializer(p, this.encodeTypes, this.multiRefEncoding, "mapping2");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 553 */       p.pOln("}");
/*     */     } 
/*     */     
/* 556 */     p.pln("return registry;");
/* 557 */     p.pOln("}");
/*     */   }
/*     */   
/*     */   protected static String genKey(AbstractType type) {
/* 561 */     String typeType, schemaType = type.getName().toString();
/* 562 */     String javaType = type.getJavaType().getName();
/*     */     
/* 564 */     if (type instanceof LiteralListType || type instanceof SOAPListType) {
/*     */       
/* 566 */       typeType = type.toString();
/*     */     } else {
/* 568 */       typeType = type.getClass().getName();
/* 569 */     }  return schemaType + ";" + javaType + ";" + typeType;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\SerializerRegistryGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */