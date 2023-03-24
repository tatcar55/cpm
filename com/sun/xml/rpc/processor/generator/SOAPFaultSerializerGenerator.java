/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.generator.writer.SerializerWriter;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.HeaderFault;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Operation;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SOAPFaultSerializerGenerator
/*     */   extends GeneratorBase
/*     */ {
/*     */   private Port port;
/*     */   private Set generatedFaultSerializers;
/*     */   private boolean writeFaultSerializerElse;
/*     */   
/*     */   public SOAPFaultSerializerGenerator() {}
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties) {
/*  75 */     return new SOAPFaultSerializerGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*  83 */     return new SOAPFaultSerializerGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPFaultSerializerGenerator(Model model, Configuration config, Properties properties) {
/*  90 */     super(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPFaultSerializerGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*  98 */     super(model, config, properties);
/*     */   }
/*     */   
/*     */   protected void preVisitModel(Model model) throws Exception {
/* 102 */     this.generatedFaultSerializers = new HashSet();
/*     */   }
/*     */   
/*     */   protected void postVisitModel(Model model) throws Exception {
/* 106 */     this.generatedFaultSerializers = null;
/*     */   }
/*     */   
/*     */   protected void preVisitPort(Port port) throws Exception {
/* 110 */     super.preVisitPort(port);
/* 111 */     this.port = port;
/*     */   }
/*     */   
/*     */   protected void postVisitPort(Port port) throws Exception {
/* 115 */     this.port = null;
/* 116 */     super.postVisitPort(port);
/*     */   }
/*     */   
/*     */   protected void postVisitOperation(Operation operation) throws Exception {
/* 120 */     if (needsFaultSerializer(operation)) {
/* 121 */       generateFaultSerializer(operation);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void visitFault(Fault fault) throws Exception {
/* 126 */     AbstractType type = fault.getBlock().getType();
/* 127 */     if (type.isSOAPType()) {
/* 128 */       ((SOAPType)type).accept(this);
/*     */     }
/* 130 */     if (type.isLiteralType()) {
/* 131 */       ((LiteralType)type).accept(this);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean needsFaultSerializer(Operation operation) {
/* 136 */     Iterator<Fault> faults = operation.getFaults();
/*     */     
/* 138 */     boolean needsFaultSerializer = false;
/* 139 */     String className = this.env.getNames().faultSerializerClassName(this.servicePackage, this.port, operation);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 144 */     if (faults != null && !this.generatedFaultSerializers.contains(className)) {
/* 145 */       while (!needsFaultSerializer && faults.hasNext()) {
/* 146 */         Fault fault = faults.next();
/* 147 */         needsFaultSerializer = true;
/*     */       } 
/* 149 */       this.generatedFaultSerializers.add(className);
/*     */     } 
/* 151 */     return needsFaultSerializer;
/*     */   }
/*     */   
/*     */   private void generateFaultSerializer(Operation operation) {
/* 155 */     log("generating FaultHandler for: " + operation.getUniqueName());
/*     */     try {
/* 157 */       String className = this.env.getNames().faultSerializerClassName(this.servicePackage, this.port, operation);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 162 */       if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/* 163 */         log("Class " + className + " exists. Not overriding.");
/*     */         return;
/*     */       } 
/* 166 */       File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 174 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 175 */       fi.setFile(classFile);
/* 176 */       fi.setType("SoapFaultSerializer");
/* 177 */       this.env.addGeneratedFile(fi);
/*     */       
/* 179 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 182 */       writePackage(out, className);
/* 183 */       out.pln();
/* 184 */       writeImports(out);
/* 185 */       out.pln();
/* 186 */       writeClassDecl(out, className);
/* 187 */       writeMembers(out, operation);
/* 188 */       out.pln();
/* 189 */       writeClassConstructor(out, className);
/* 190 */       out.pln();
/* 191 */       writeInitialize(out, operation);
/* 192 */       out.pln();
/* 193 */       writeDeserializeDetail(out, operation);
/* 194 */       out.pln();
/* 195 */       writeSerializeDetail(out, operation);
/* 196 */       out.pOln("}");
/* 197 */       out.close();
/* 198 */     } catch (Exception e) {
/* 199 */       fail(e);
/*     */     } 
/*     */   }
/*     */   private void writeImports(IndentingWriter p) throws IOException {
/* 203 */     p.pln("import com.sun.xml.rpc.encoding.*;");
/* 204 */     p.pln("import com.sun.xml.rpc.encoding.soap.SOAPConstants;");
/* 205 */     p.pln("import com.sun.xml.rpc.encoding.soap.SOAP12Constants;");
/* 206 */     p.pln("import com.sun.xml.rpc.soap.message.SOAPFaultInfo;");
/* 207 */     p.pln("import com.sun.xml.rpc.streaming.*;");
/* 208 */     p.pln("import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;");
/* 209 */     p.pln("import javax.xml.namespace.QName;");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeClassDecl(IndentingWriter p, String className) throws IOException {
/* 214 */     p.plnI("public class " + Names.stripQualifier(className) + " extends SOAPFaultInfoSerializer {");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeMembers(IndentingWriter p, Operation operation) throws IOException, GeneratorException {
/* 222 */     Set<String> processedTypes = new HashSet();
/* 223 */     Iterator<Fault> faults = operation.getFaults();
/*     */ 
/*     */     
/* 226 */     Set<QName> faultNames = new HashSet();
/* 227 */     while (faults.hasNext()) {
/* 228 */       Fault fault = faults.next();
/* 229 */       if (!faultNames.contains(fault.getBlock().getName())) {
/* 230 */         GeneratorUtil.writeQNameDeclaration(p, fault.getElementName(), this.env.getNames());
/*     */ 
/*     */ 
/*     */         
/* 234 */         faultNames.add(fault.getBlock().getName());
/*     */       } 
/* 236 */       String suffix = "_Serializer";
/* 237 */       if (fault.getBlock().getType().isSOAPType()) {
/* 238 */         SOAPEncoding.writeStaticSerializer(p, this.servicePackage, (SOAPType)fault.getBlock().getType(), processedTypes, this.writerFactory, this.env.getNames());
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/* 246 */         LiteralEncoding.writeStaticSerializer(p, this.servicePackage, (LiteralType)fault.getBlock().getType(), processedTypes, this.writerFactory, this.env.getNames());
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 254 */       SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, fault.getBlock().getType());
/*     */ 
/*     */ 
/*     */       
/* 258 */       if (!processedTypes.contains(fault.getBlock().getType().getName() + writer.serializerMemberName() + suffix)) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 263 */         p.pln("private CombinedSerializer " + writer.serializerMemberName() + suffix + ";");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 270 */         processedTypes.add(fault.getBlock().getType().getName() + writer.serializerMemberName() + suffix);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     faults = operation.getFaults();
/* 278 */     for (int i = 0; faults.hasNext(); i++) {
/* 279 */       Fault fault = faults.next();
/* 280 */       p.pln("private static final int " + fault.getJavaException().getRealName().toUpperCase().replace('.', '_') + "_INDEX = " + i + ";");
/*     */     } 
/*     */   }
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
/*     */   private void writeClassConstructor(IndentingWriter p, String className) throws IOException {
/* 297 */     p.plnI("public " + Names.stripQualifier(className) + "(boolean encodeType, " + "boolean isNullable) {");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 302 */     p.pln("super(encodeType, isNullable);");
/* 303 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeInitialize(IndentingWriter p, Operation operation) throws IOException {
/* 308 */     Iterator<Fault> faults = operation.getFaults();
/* 309 */     Set<AbstractType> processedTypes = new HashSet();
/*     */ 
/*     */     
/* 312 */     p.plnI("public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {");
/*     */     
/* 314 */     p.pln("super.initialize(registry);");
/* 315 */     while (faults.hasNext()) {
/* 316 */       Fault fault = faults.next();
/* 317 */       AbstractType type = fault.getBlock().getType();
/* 318 */       if (processedTypes.contains(type)) {
/*     */         continue;
/*     */       }
/* 321 */       String suffix = "_Serializer";
/* 322 */       SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, type);
/*     */       
/* 324 */       writer.initializeSerializer(p, this.env.getNames().getTypeQName(type.getName()), "registry");
/*     */ 
/*     */ 
/*     */       
/* 328 */       p.pln(writer.serializerMemberName() + suffix + " = " + writer.serializerMemberName() + ".getInnermostSerializer();");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 334 */       processedTypes.add(type);
/*     */     } 
/* 336 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeDeserializeDetail(IndentingWriter p, Operation operation) throws IOException {
/* 341 */     Set faultsSet = new TreeSet(new GeneratorUtil.FaultComparator(true));
/* 342 */     faultsSet.addAll(operation.getFaultsSet());
/* 343 */     Iterator<Fault> faults = faultsSet.iterator();
/* 344 */     String detailNames = "";
/*     */     
/* 346 */     p.plnI("protected java.lang.Object deserializeDetail(SOAPDeserializationState state, XMLReader reader,");
/*     */     
/* 348 */     p.pln("SOAPDeserializationContext context, SOAPFaultInfo instance) throws java.lang.Exception {");
/*     */     
/* 350 */     p.pln("boolean isComplete = true;");
/* 351 */     p.pln("javax.xml.namespace.QName elementName;");
/* 352 */     p.pln("javax.xml.namespace.QName elementType = null;");
/* 353 */     p.pln("SOAPInstanceBuilder builder = null;");
/* 354 */     p.pln("java.lang.Object detail = null;");
/* 355 */     p.pln("java.lang.Object obj = null;");
/* 356 */     p.pln();
/* 357 */     p.pln("reader.nextElementContent();");
/* 358 */     p.plnI("if (reader.getState() == XMLReader.END)");
/* 359 */     p.pln("return deserializeDetail(reader, context);");
/* 360 */     p.pO();
/* 361 */     p.pln("XMLReaderUtil.verifyReaderState(reader, XMLReader.START);");
/* 362 */     p.pln("elementName = reader.getName();");
/* 363 */     p.pln("elementType = getType(reader);");
/* 364 */     String faultName = "";
/* 365 */     String nextName = "";
/* 366 */     Fault nextFault = faults.hasNext() ? faults.next() : null;
/*     */     
/* 368 */     boolean wroteTypeCheck = false;
/* 369 */     boolean startName = false;
/* 370 */     for (int i = 0; nextFault != null; i++) {
/* 371 */       Fault fault = nextFault;
/* 372 */       boolean hasNext = false;
/* 373 */       nextFault = faults.hasNext() ? faults.next() : null;
/* 374 */       nextName = (nextFault != null) ? this.env.getNames().getQNameName(nextFault.getElementName()) : null;
/*     */ 
/*     */ 
/*     */       
/* 378 */       if (!faultName.equals(this.env.getNames().getQNameName(fault.getElementName()))) {
/*     */         
/* 380 */         faultName = this.env.getNames().getQNameName(fault.getElementName());
/* 381 */         boolean writeTypeCheck = false;
/* 382 */         startName = true;
/* 383 */         if (i > 0) {
/* 384 */           p.pln();
/* 385 */           p.pO("} else ");
/*     */         } 
/* 387 */         p.plnI("if (elementName.equals(" + faultName + ")) {");
/* 388 */         if (writeTypeCheck) {
/* 389 */           p.plnI("if (elementType != null) {");
/* 390 */           wroteTypeCheck = true;
/*     */         } 
/*     */       } 
/* 393 */       hasNext = (nextName != null) ? faultName.equals(nextName) : false;
/*     */       
/* 395 */       if (!hasNext && wroteTypeCheck) {
/* 396 */         p.pln();
/* 397 */         p.pO("} else ");
/* 398 */         wroteTypeCheck = false;
/* 399 */       } else if (!startName) {
/* 400 */         p.p("else ");
/*     */       } 
/* 402 */       startName = false;
/* 403 */       writeFaultDeserializer(p, fault, operation, "reader", hasNext);
/*     */     } 
/* 405 */     p.pln();
/* 406 */     p.pOln("}");
/* 407 */     writeCatchAllDetailDeserializer(p);
/*     */     
/* 409 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeCatchAllDetailDeserializer(IndentingWriter p) throws IOException {
/* 414 */     p.pln("return deserializeDetail(reader, context);");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeSerializeDetail(IndentingWriter p, Operation operation) throws IOException {
/* 419 */     Set faultsSet = new TreeSet(new GeneratorUtil.FaultComparator(false));
/* 420 */     faultsSet.addAll(operation.getFaultsSet());
/* 421 */     Iterator<Fault> faults = faultsSet.iterator();
/* 422 */     String detailNames = "";
/*     */ 
/*     */     
/* 425 */     p.plnI("protected void serializeDetail(java.lang.Object detail, XMLWriter writer, SOAPSerializationContext context)");
/*     */     
/* 427 */     p.pln("throws java.lang.Exception {");
/* 428 */     p.plnI("if (detail == null) {");
/* 429 */     p.pln("throw new SerializationException(\"soap.unexpectedNull\");");
/* 430 */     p.pOln("}");
/*     */     
/* 432 */     for (Iterator iter = faultsSet.iterator(); iter.hasNext(); ) {
/* 433 */       Object obj = iter.next();
/* 434 */       if (obj instanceof HeaderFault) {
/* 435 */         HeaderFault headerFault = (HeaderFault)obj;
/* 436 */         String faultExceptionName = this.env.getNames().customExceptionClassName((Fault)headerFault);
/*     */         
/* 438 */         p.plnI("if (detail instanceof " + faultExceptionName + ") {");
/* 439 */         p.pln("return;");
/* 440 */         p.pOln("}");
/*     */       } 
/*     */     } 
/* 443 */     p.pln("writer.startElement(DETAIL_QNAME);");
/* 444 */     p.pln();
/* 445 */     p.pln("boolean pushedEncodingStyle = false;");
/* 446 */     p.plnI("if (encodingStyle != null) {");
/* 447 */     p.pln("context.pushEncodingStyle(encodingStyle, writer);");
/* 448 */     p.pOln("}");
/*     */     
/* 450 */     this.writeFaultSerializerElse = false;
/* 451 */     while (faults.hasNext()) {
/* 452 */       Fault fault = faults.next();
/* 453 */       if (!(fault instanceof HeaderFault)) {
/* 454 */         writeFaultSerializer(p, fault, "writer");
/* 455 */         this.writeFaultSerializerElse = true;
/*     */       } 
/*     */     } 
/* 458 */     p.pln("writer.endElement();");
/* 459 */     p.plnI("if (pushedEncodingStyle) {");
/* 460 */     p.pln("context.popEncodingStyle();");
/* 461 */     p.pOln("}");
/* 462 */     p.pOln("}");
/*     */   }
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
/*     */   private void writeFaultDeserializer(IndentingWriter p, Fault fault, Operation operation, String reader, boolean hasNext) throws IOException {
/* 475 */     Block block = fault.getBlock();
/* 476 */     String memberConstName = "0";
/* 477 */     String memberQName = this.env.getNames().getQNameName(fault.getElementName());
/*     */     
/* 479 */     AbstractType type = block.getType();
/*     */     
/* 481 */     SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, type);
/*     */     
/* 483 */     String serializer = writer.deserializerMemberName();
/* 484 */     boolean referenceable = (type.isSOAPType() && ((SOAPType)type).isReferenceable());
/*     */     
/* 486 */     String suffix = type.isSOAPType() ? "_Serializer" : "";
/* 487 */     if (!hasNext) {
/* 488 */       if (fault.getSubfaults() == null) {
/* 489 */         p.plnI("if (elementType == null || ");
/* 490 */         p.pln("(elementType.equals(" + serializer + ".getXmlType()) ||");
/*     */         
/* 492 */         p.pln("(" + writer.serializerMemberName() + suffix + " instanceof ArraySerializerBase &&");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 497 */         p.pln("elementType.equals(SOAPConstants.QNAME_ENCODING_ARRAY)) ) ) {");
/*     */       } 
/*     */     } else {
/*     */       
/* 501 */       p.plnI("if (elementType.equals(" + serializer + ".getXmlType()) ||");
/*     */       
/* 503 */       p.pln("(" + writer.serializerMemberName() + suffix + " instanceof ArraySerializerBase &&");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 508 */       p.pln("elementType.equals(SOAPConstants.QNAME_ENCODING_ARRAY)) ) {");
/*     */     } 
/*     */     
/* 511 */     p.pln("obj = " + serializer + ".deserialize(" + memberQName + ", " + reader + ", context);");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 520 */     if (referenceable || type.isLiteralType()) {
/* 521 */       JavaException javaException = fault.getJavaException();
/* 522 */       if (referenceable) {
/* 523 */         p.plnI("if (obj instanceof SOAPDeserializationState) {");
/* 524 */         String index = javaException.getRealName().toUpperCase() + "_INDEX";
/*     */         
/* 526 */         p.pln("builder = new " + this.env.getNames().faultBuilderClassName(this.servicePackage, this.port, operation) + "();");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 533 */         p.plnI("state = registerWithMemberState(instance, state, obj,");
/* 534 */         p.pln(index.replace('.', '_') + ", builder);");
/* 535 */         p.pO();
/* 536 */         p.pln("isComplete = false;");
/* 537 */         p.pOlnI("} else {");
/*     */       } 
/* 539 */       if ((type instanceof SOAPStructureType || type instanceof com.sun.xml.rpc.processor.model.literal.LiteralStructuredType) && SOAPObjectSerializerGenerator.deserializeToDetail(type)) {
/*     */ 
/*     */         
/* 542 */         p.pln("detail = (javax.xml.soap.Detail)obj;");
/*     */       }
/* 544 */       else if (!(type instanceof SOAPStructureType) && !(type instanceof com.sun.xml.rpc.processor.model.literal.LiteralStructuredType) && javaException.getMembersCount() == 1 && fault.getSubfaults() == null) {
/*     */ 
/*     */ 
/*     */         
/* 548 */         Iterator<JavaStructureMember> members = fault.getJavaException().getMembers();
/*     */ 
/*     */         
/* 551 */         JavaStructureMember member = members.next();
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
/* 562 */         String valueStr = null;
/* 563 */         String javaName = type.getJavaType().getName();
/*     */         
/* 565 */         if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/* 566 */           String boxName = SimpleToBoxedUtil.getBoxedClassName(javaName);
/* 567 */           valueStr = SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")obj", javaName);
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 572 */           valueStr = "(" + javaName + ")obj";
/*     */         } 
/* 574 */         p.pln("detail = new " + this.env.getNames().customExceptionClassName(fault) + "(" + valueStr + ");");
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */ 
/*     */         
/* 582 */         p.pln("detail = obj;");
/*     */       } 
/*     */       
/* 585 */       if (referenceable) {
/* 586 */         p.pOln("}");
/*     */       }
/*     */       
/* 589 */       p.pln("reader.nextElementContent();");
/*     */ 
/*     */       
/* 592 */       p.pln("skipRemainingDetailEntries(reader);");
/* 593 */       p.pln("XMLReaderUtil.verifyReaderState(reader, XMLReader.END);");
/* 594 */       p.pln("return (isComplete ? (Object)detail : (Object)state);");
/*     */     } else {
/* 596 */       String valueStr = null;
/* 597 */       String javaName = type.getJavaType().getName();
/*     */       
/* 599 */       if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/* 600 */         String boxName = SimpleToBoxedUtil.getBoxedClassName(javaName);
/* 601 */         valueStr = SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")obj", javaName);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 606 */         valueStr = "(" + javaName + ")obj";
/*     */       } 
/* 608 */       p.pln("detail = new " + this.env.getNames().customExceptionClassName(fault) + "(" + valueStr + ");");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 614 */       p.pln("reader.nextElementContent();");
/*     */       
/* 616 */       p.pln("XMLReaderUtil.verifyReaderState(reader, XMLReader.END);");
/* 617 */       p.pln("return detail;");
/*     */     } 
/* 619 */     if (hasNext || fault.getSubfaults() == null)
/* 620 */       p.pO("} "); 
/*     */   }
/*     */   
/*     */   private static boolean deserializeToDetail(SOAPStructureType type) {
/* 624 */     boolean detail = SOAPObjectSerializerGenerator.deserializeToDetail((AbstractType)type);
/*     */     
/* 626 */     return detail;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeFaultSerializer(IndentingWriter p, Fault fault, String writer) throws IOException {
/* 636 */     Block block = fault.getBlock();
/* 637 */     String memberQName = this.env.getNames().getQNameName(fault.getElementName());
/*     */     
/* 639 */     AbstractType type = block.getType();
/* 640 */     String faultExceptionName = this.env.getNames().customExceptionClassName(fault);
/*     */     
/* 642 */     if (this.writeFaultSerializerElse) {
/* 643 */       p.p("else ");
/*     */     }
/* 645 */     p.plnI("if (detail instanceof " + faultExceptionName + ") {");
/* 646 */     SerializerWriter sWriter = this.writerFactory.createWriter(this.servicePackage, type);
/*     */     
/* 648 */     String serializer = sWriter.deserializerMemberName() + "_Serializer";
/* 649 */     String detailStr = "detail";
/*     */     
/* 651 */     JavaException exception = fault.getJavaException();
/* 652 */     if (!(type instanceof SOAPStructureType) && !(type instanceof com.sun.xml.rpc.processor.model.literal.LiteralStructuredType) && exception.getMembersCount() == 1 && fault.getSubfaults() == null) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 657 */       Iterator<JavaStructureMember> members = exception.getMembers();
/* 658 */       JavaStructureMember javaMember = members.next();
/*     */       
/* 660 */       detailStr = "((" + faultExceptionName + ")" + detailStr + ")." + javaMember.getReadMethod() + "()";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 668 */       String javaName = javaMember.getType().getName();
/* 669 */       if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/* 670 */         detailStr = SimpleToBoxedUtil.getBoxedExpressionOfType(detailStr, javaName);
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 676 */     p.pln(serializer + ".serialize(" + detailStr + ", " + memberQName + ", null, " + writer + ", context);");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 685 */     p.pOln("}");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\SOAPFaultSerializerGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */