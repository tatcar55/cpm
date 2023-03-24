/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.generator.writer.LiteralSequenceSerializerWriter;
/*     */ import com.sun.xml.rpc.processor.generator.writer.SOAPObjectSerializerWriter;
/*     */ import com.sun.xml.rpc.processor.generator.writer.SerializerWriter;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAllType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPAnyType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
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
/*     */ public class InterfaceSerializerGenerator
/*     */   extends GeneratorBase
/*     */ {
/*     */   private Set visitedTypes;
/*  78 */   private Fault currentFault = null;
/*     */ 
/*     */   
/*     */   private static final String OBJECT_SERIALIZER_BASE = "ObjectSerializerBase";
/*     */ 
/*     */   
/*     */   private static final String INTERFACE_SERIALIZER_BASE = "InterfaceSerializerBase";
/*     */ 
/*     */   
/*     */   public InterfaceSerializerGenerator() {}
/*     */ 
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties) {
/*  91 */     return new InterfaceSerializerGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*  99 */     return new InterfaceSerializerGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private InterfaceSerializerGenerator(Model model, Configuration config, Properties properties) {
/* 106 */     super(model, config, properties);
/*     */   }
/*     */   
/*     */   protected void preVisitModel(Model model) throws Exception {
/* 110 */     this.visitedTypes = new HashSet();
/*     */   }
/*     */   
/*     */   protected void postVisitModel(Model model) throws Exception {
/* 114 */     Iterator<AbstractType> types = model.getExtraTypes();
/*     */     
/* 116 */     while (types.hasNext()) {
/* 117 */       AbstractType type = types.next();
/* 118 */       if (type.isSOAPType())
/* 119 */         ((SOAPType)type).accept(this); 
/*     */     } 
/* 121 */     this.visitedTypes = null;
/*     */   }
/*     */   
/*     */   protected void preVisitFault(Fault fault) throws Exception {
/* 125 */     if (fault.getBlock().getType().isSOAPType()) {
/* 126 */       this.currentFault = fault;
/* 127 */       ((SOAPType)fault.getBlock().getType()).accept(this);
/* 128 */       this.currentFault = null;
/*     */     } 
/* 130 */     if (fault.getBlock().getType().isLiteralType()) {
/* 131 */       this.currentFault = fault;
/* 132 */       ((LiteralType)fault.getBlock().getType()).accept(this);
/* 133 */       this.currentFault = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preVisitSOAPSimpleType(SOAPSimpleType type) throws Exception {
/* 140 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 143 */     typeVisited((AbstractType)type);
/*     */   }
/*     */   
/*     */   protected void preVisitSOAPAnyType(SOAPAnyType type) throws Exception {
/* 147 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 150 */     typeVisited((AbstractType)type);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void preVisitSOAPEnumerationType(SOAPEnumerationType type) throws Exception {
/* 155 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 158 */     typeVisited((AbstractType)type);
/*     */   }
/*     */   
/*     */   protected void preVisitSOAPArrayType(SOAPArrayType type) throws Exception {
/* 162 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 165 */     typeVisited((AbstractType)type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void preVisitSOAPStructureType(SOAPStructureType type) throws Exception {
/* 170 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 173 */     typeVisited((AbstractType)type);
/*     */     try {
/* 175 */       writeObjectSerializerForType((AbstractType)type);
/* 176 */     } catch (IOException e) {
/* 177 */       fail("generator.cant.write", type.getName().getLocalPart());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void preVisitLiteralSequenceType(LiteralSequenceType type) throws Exception {
/* 183 */     visitLiteralStructuredType((LiteralStructuredType)type);
/*     */   }
/*     */   
/*     */   public void preVisitLiteralAllType(LiteralAllType type) throws Exception {
/* 187 */     visitLiteralStructuredType((LiteralStructuredType)type);
/*     */   }
/*     */ 
/*     */   
/*     */   private void visitLiteralStructuredType(LiteralStructuredType type) throws Exception {
/* 192 */     if (haveVisited((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 195 */     typeVisited((AbstractType)type);
/*     */     try {
/* 197 */       writeObjectSerializerForType((AbstractType)type);
/* 198 */     } catch (IOException e) {
/* 199 */       fail("generator.cant.write", type.getName().getLocalPart());
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean haveVisited(AbstractType type) {
/* 204 */     return this.visitedTypes.contains(type);
/*     */   }
/*     */   
/*     */   private void typeVisited(AbstractType type) {
/* 208 */     this.visitedTypes.add(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObjectSerializerForType(AbstractType type) throws IOException {
/* 217 */     boolean isInterface = (((JavaStructureType)type.getJavaType()).getAllSubclasses() != null);
/*     */ 
/*     */     
/* 220 */     if (!isInterface) {
/*     */       return;
/*     */     }
/* 223 */     JavaType javaType = type.getJavaType();
/* 224 */     String className = this.env.getNames().typeInterfaceSerializerClassName(this.servicePackage, type);
/*     */ 
/*     */ 
/*     */     
/* 228 */     if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/* 229 */       log("Class " + className + " exists. Not overriding.");
/*     */       return;
/*     */     } 
/* 232 */     File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 242 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 243 */       fi.setFile(classFile);
/* 244 */       fi.setType("InterfaceSerializer");
/* 245 */       this.env.addGeneratedFile(fi);
/*     */       
/* 247 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 250 */       writeObjectSerializerCode(out, type);
/* 251 */       out.close();
/*     */     }
/* 253 */     catch (IOException e) {
/* 254 */       fail("generator.cant.write", classFile.toString());
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
/*     */   private void writeObjectSerializerCode(IndentingWriter p, AbstractType type) throws IOException {
/* 267 */     log("writing  serializer/deserializer for: " + type.getName().getLocalPart());
/*     */ 
/*     */     
/* 270 */     String className = this.env.getNames().typeInterfaceSerializerClassName(this.servicePackage, type);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 275 */     writePackage(p, className);
/* 276 */     writeImports(p);
/* 277 */     p.pln();
/*     */     
/* 279 */     writeClassDecl(p, className);
/* 280 */     writeMembers(p, type);
/* 281 */     p.pln();
/* 282 */     writeConstructor(p, className, type);
/* 283 */     p.pln();
/* 284 */     writeInitialize(p, type);
/* 285 */     p.pln();
/* 286 */     writeDoDeserializeMethod(p, type);
/* 287 */     p.pln();
/* 288 */     writeDoSerializeInstanceMethod(p, type);
/*     */     
/* 290 */     if (type instanceof com.sun.xml.rpc.processor.model.soap.RPCResponseStructureType)
/*     */     {
/* 292 */       writeVerifyNameOverrideMethod(p, type);
/*     */     }
/*     */     
/* 295 */     p.pOln("}");
/*     */   }
/*     */   
/*     */   private void writeImports(IndentingWriter p) throws IOException {
/* 299 */     p.pln("import com.sun.xml.rpc.encoding.*;");
/* 300 */     p.pln("import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;");
/*     */     
/* 302 */     p.pln("import com.sun.xml.rpc.encoding.soap.SOAPConstants;");
/* 303 */     p.pln("import com.sun.xml.rpc.soap.SOAPVersion;");
/* 304 */     p.pln("import com.sun.xml.rpc.streaming.*;");
/* 305 */     p.pln("import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;");
/* 306 */     p.pln("import javax.xml.namespace.QName;");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeClassDecl(IndentingWriter p, String className) throws IOException {
/* 311 */     String baseClass = "InterfaceSerializerBase";
/* 312 */     p.plnI("public class " + Names.stripQualifier(className) + " extends " + baseClass + " implements Initializable {");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeMembers(IndentingWriter p, AbstractType type) throws IOException {
/*     */     LiteralSequenceSerializerWriter literalSequenceSerializerWriter;
/* 322 */     Set processedTypes = new HashSet();
/* 323 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */     
/* 325 */     Set subclassSet = new TreeSet(new GeneratorUtil.SubclassComparator());
/* 326 */     subclassSet.addAll(javaStructure.getAllSubclassesSet());
/* 327 */     Iterator<JavaStructureType> iterator = subclassSet.iterator();
/* 328 */     while (iterator != null && iterator.hasNext()) {
/* 329 */       if (type.isSOAPType()) {
/* 330 */         SOAPEncoding.writeStaticSerializer(p, this.servicePackage, (SOAPType)((JavaStructureType)iterator.next()).getOwner(), processedTypes, this.writerFactory, this.env.getNames());
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 338 */       LiteralEncoding.writeStaticSerializer(p, this.servicePackage, (LiteralType)((JavaStructureType)iterator.next()).getOwner(), processedTypes, this.writerFactory, this.env.getNames());
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 348 */     if (type.isSOAPType()) {
/* 349 */       SOAPObjectSerializerWriter sOAPObjectSerializerWriter = new SOAPObjectSerializerWriter(this.servicePackage, (SOAPType)type, this.env.getNames());
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 355 */       literalSequenceSerializerWriter = new LiteralSequenceSerializerWriter(this.servicePackage, (LiteralType)type, this.env.getNames());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 360 */     p.pln("private CombinedSerializer " + literalSequenceSerializerWriter.serializerMemberName() + ";");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeConstructor(IndentingWriter p, String className, AbstractType type) throws IOException {
/* 371 */     if (type.isSOAPType()) {
/* 372 */       p.plnI("public " + Names.stripQualifier(className) + "(QName type, boolean encodeType, " + "boolean isNullable, String encodingStyle) {");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 377 */       p.pln("super(type, encodeType, isNullable, encodingStyle);");
/* 378 */       p.pOln("}");
/*     */     } else {
/* 380 */       p.plnI("public " + Names.stripQualifier(className) + "(QName type, String encodingStyle, " + "boolean encodeType) {");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 385 */       p.pln("super(type, encodeType, true, encodingStyle);");
/* 386 */       p.pOln("}");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeInitialize(IndentingWriter p, AbstractType type) throws IOException {
/*     */     LiteralSequenceSerializerWriter literalSequenceSerializerWriter;
/* 392 */     p.plnI("public void initialize(InternalTypeMappingRegistry registry) throws Exception {");
/*     */     
/* 394 */     Set processedTypes = new HashSet();
/*     */ 
/*     */     
/* 397 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */     
/* 399 */     Set subclassSet = new TreeSet(new GeneratorUtil.SubclassComparator());
/*     */ 
/*     */ 
/*     */     
/* 403 */     subclassSet.addAll(javaStructure.getAllSubclassesSet());
/* 404 */     Iterator<JavaStructureType> iterator = subclassSet.iterator();
/* 405 */     while (iterator != null && iterator.hasNext()) {
/* 406 */       AbstractType abstractType = (AbstractType)((JavaStructureType)iterator.next()).getOwner();
/*     */       
/* 408 */       SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, abstractType);
/* 409 */       writer.initializeSerializer(p, this.env.getNames().getTypeQName(abstractType.getName()), "registry");
/*     */ 
/*     */ 
/*     */       
/* 413 */       p.pln(writer.serializerMemberName() + " = " + writer.serializerMemberName() + ".getInnermostSerializer();");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 419 */     if (type.isSOAPType()) {
/* 420 */       SOAPObjectSerializerWriter sOAPObjectSerializerWriter = new SOAPObjectSerializerWriter(this.servicePackage, (SOAPType)type, this.env.getNames());
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 426 */       literalSequenceSerializerWriter = new LiteralSequenceSerializerWriter(this.servicePackage, (LiteralType)type, this.env.getNames());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 431 */     StringBuffer typeName = new StringBuffer(40);
/* 432 */     typeName.append("type");
/* 433 */     literalSequenceSerializerWriter.createSerializer(p, typeName, "interfaceSerializer", this.encodeTypes, false, null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 440 */     p.pln(literalSequenceSerializerWriter.serializerMemberName() + " = interfaceSerializer.getInnermostSerializer();");
/*     */ 
/*     */     
/* 443 */     p.plnI("if (" + literalSequenceSerializerWriter.serializerMemberName() + " instanceof Initializable) {");
/*     */ 
/*     */ 
/*     */     
/* 447 */     p.pln("((Initializable)" + literalSequenceSerializerWriter.serializerMemberName() + ").initialize(registry);");
/*     */ 
/*     */ 
/*     */     
/* 451 */     p.pOln("}");
/* 452 */     p.pOln("}");
/*     */   }
/*     */   
/*     */   private void writeDoDeserializeMethod(IndentingWriter p, AbstractType type) throws IOException {
/*     */     LiteralSequenceSerializerWriter literalSequenceSerializerWriter;
/* 457 */     p.plnI("public java.lang.Object doDeserialize(javax.xml.namespace.QName name, XMLReader reader,");
/* 458 */     p.pln("SOAPDeserializationContext context) throws Exception {");
/* 459 */     JavaStructureType javaType = (JavaStructureType)type.getJavaType();
/* 460 */     Set subclassSet = new TreeSet(new GeneratorUtil.SubclassComparator());
/* 461 */     subclassSet.addAll(javaType.getAllSubclassesSet());
/* 462 */     Iterator<JavaStructureType> subclasses = subclassSet.iterator();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 467 */     p.pln("javax.xml.namespace.QName elementType = getType(reader);");
/*     */     int i;
/* 469 */     for (i = 0; subclasses != null && subclasses.hasNext(); i++) {
/* 470 */       JavaStructureType subclass = subclasses.next();
/* 471 */       AbstractType abstractType = (AbstractType)subclass.getOwner();
/* 472 */       SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, abstractType);
/* 473 */       String str = writer.deserializerMemberName();
/* 474 */       if (i > 0)
/* 475 */         p.p(" else "); 
/* 476 */       p.plnI("if (elementType != null && elementType.equals(" + str + ".getXmlType())) {");
/*     */ 
/*     */ 
/*     */       
/* 480 */       p.pln("return " + str + ".deserialize(name, reader, context);");
/*     */ 
/*     */ 
/*     */       
/* 484 */       p.pO("}");
/*     */     } 
/* 486 */     if (i > 0)
/* 487 */       p.p(" else "); 
/* 488 */     if (type.isSOAPType()) {
/* 489 */       SOAPObjectSerializerWriter sOAPObjectSerializerWriter = new SOAPObjectSerializerWriter(this.servicePackage, (SOAPType)type, this.env.getNames());
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 495 */       literalSequenceSerializerWriter = new LiteralSequenceSerializerWriter(this.servicePackage, (LiteralType)type, this.env.getNames());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 500 */     String serializer = literalSequenceSerializerWriter.deserializerMemberName();
/* 501 */     p.plnI("if (elementType == null || elementType.equals(" + serializer + ".getXmlType())) {");
/*     */ 
/*     */ 
/*     */     
/* 505 */     p.pln("Object obj = " + serializer + ".deserialize(name, reader, context);");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 512 */     if (javaType instanceof com.sun.xml.rpc.processor.model.java.JavaException) {
/* 513 */       p.plnI("while (reader.getState() == XMLReader.START) {");
/* 514 */       p.pln("reader.skipElement();");
/* 515 */       p.pln("reader.nextElementContent();");
/* 516 */       p.pOln("}");
/*     */     } 
/* 518 */     p.pln("return obj;");
/* 519 */     p.pOln("}");
/* 520 */     p.pln("throw new DeserializationException(\"soap.unexpectedElementType\", new Object[] {\"\", elementType.toString()});");
/*     */     
/* 522 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeDoSerializeInstanceMethod(IndentingWriter p, AbstractType type) throws IOException {
/*     */     LiteralSequenceSerializerWriter literalSequenceSerializerWriter;
/* 529 */     p.plnI("public void doSerializeInstance(java.lang.Object obj, javax.xml.namespace.QName name, SerializerCallback callback,");
/*     */     
/* 531 */     p.pln("XMLWriter writer, SOAPSerializationContext context) throws Exception {");
/*     */     
/* 533 */     p.pln(type.getJavaType().getName() + " instance = (" + type.getJavaType().getName() + ")obj;");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 538 */     p.pln();
/* 539 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */     
/* 541 */     Set subclassSet = new TreeSet(new GeneratorUtil.SubclassComparator());
/*     */ 
/*     */ 
/*     */     
/* 545 */     subclassSet.addAll(javaStructure.getAllSubclassesSet());
/* 546 */     Iterator<JavaStructureType> subclasses = subclassSet.iterator();
/* 547 */     Iterator iterator = javaStructure.getMembers();
/*     */ 
/*     */     
/* 550 */     if (subclasses != null) {
/*     */ 
/*     */       
/* 553 */       for (int i = 0; subclasses.hasNext(); i++) {
/* 554 */         JavaStructureType subclass = subclasses.next();
/* 555 */         AbstractType abstractType = (AbstractType)subclass.getOwner();
/* 556 */         SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, abstractType);
/*     */         
/* 558 */         String str = writer.deserializerMemberName();
/* 559 */         if (i > 0)
/* 560 */           p.p(" else "); 
/* 561 */         p.plnI("if (obj instanceof " + subclass.getName() + ") {");
/* 562 */         p.pln(str + ".serialize(obj, name, callback, writer, context);");
/*     */ 
/*     */         
/* 565 */         p.pO("}");
/*     */       } 
/* 567 */       p.plnI(" else {");
/*     */     } 
/* 569 */     if (type.isSOAPType()) {
/* 570 */       SOAPObjectSerializerWriter sOAPObjectSerializerWriter = new SOAPObjectSerializerWriter(this.servicePackage, (SOAPType)type, this.env.getNames());
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 576 */       literalSequenceSerializerWriter = new LiteralSequenceSerializerWriter(this.servicePackage, (LiteralType)type, this.env.getNames());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 581 */     String serializer = literalSequenceSerializerWriter.deserializerMemberName();
/* 582 */     p.pln(serializer + ".serialize(obj, name, callback, writer, context);");
/* 583 */     p.pOln("}");
/* 584 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeVerifyNameOverrideMethod(IndentingWriter p, AbstractType type) throws IOException {
/* 591 */     p.plnI("protected void verifyName(XMLReader reader, javax.xml.namespace.QName expectedName) throws java.lang.Exception {");
/*     */     
/* 593 */     p.pOln("}");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\InterfaceSerializerGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */