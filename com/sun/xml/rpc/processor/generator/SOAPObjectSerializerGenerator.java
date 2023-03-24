/*      */ package com.sun.xml.rpc.processor.generator;
/*      */ 
/*      */ import com.sun.xml.rpc.processor.config.Configuration;
/*      */ import com.sun.xml.rpc.processor.generator.writer.SerializerWriter;
/*      */ import com.sun.xml.rpc.processor.generator.writer.SimpleTypeSerializerWriter;
/*      */ import com.sun.xml.rpc.processor.model.AbstractType;
/*      */ import com.sun.xml.rpc.processor.model.Fault;
/*      */ import com.sun.xml.rpc.processor.model.Model;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*      */ import com.sun.xml.rpc.processor.model.soap.RPCResponseStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPAnyType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPAttributeMember;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPOrderedStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*      */ import com.sun.xml.rpc.processor.model.soap.SOAPUnorderedStructureType;
/*      */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*      */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*      */ import com.sun.xml.rpc.processor.util.StringUtils;
/*      */ import com.sun.xml.rpc.soap.SOAPVersion;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SOAPObjectSerializerGenerator
/*      */   extends GeneratorBase
/*      */ {
/*      */   private Set visitedTypes;
/*      */   private static final String OBJECT_SERIALIZER_BASE = "ObjectSerializerBase";
/*      */   private static final String INTERFACE_SERIALIZER_BASE = "InterfaceSerializerBase";
/*      */   
/*      */   public SOAPObjectSerializerGenerator() {}
/*      */   
/*      */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties) {
/*   87 */     return new SOAPObjectSerializerGenerator(model, config, properties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*   95 */     return new SOAPObjectSerializerGenerator(model, config, properties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private SOAPObjectSerializerGenerator(Model model, Configuration config, Properties properties) {
/*  102 */     super(model, config, properties);
/*      */   }
/*      */   
/*      */   protected void preVisitModel(Model model) throws Exception {
/*  106 */     this.visitedTypes = new HashSet();
/*      */   }
/*      */   
/*      */   protected void postVisitModel(Model model) throws Exception {
/*  110 */     Iterator<AbstractType> types = model.getExtraTypes();
/*      */     
/*  112 */     while (types.hasNext()) {
/*  113 */       AbstractType type = types.next();
/*  114 */       if (type.isSOAPType())
/*  115 */         ((SOAPType)type).accept(this); 
/*      */     } 
/*  117 */     this.visitedTypes = null;
/*      */   }
/*      */   
/*      */   protected void visitFault(Fault fault) throws Exception {
/*  121 */     if (fault.getBlock().getType().isSOAPType()) {
/*  122 */       ((SOAPType)fault.getBlock().getType()).accept(this);
/*      */     }
/*  124 */     JavaException exception = fault.getJavaException();
/*  125 */     Iterator<JavaStructureMember> members = exception.getMembers();
/*  126 */     AbstractType aType = (AbstractType)exception.getOwner();
/*  127 */     if (aType.isSOAPType()) {
/*      */       
/*  129 */       while (members.hasNext()) {
/*  130 */         SOAPType type = ((SOAPStructureMember)((JavaStructureMember)members.next()).getOwner()).getType();
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  135 */         type.accept(this);
/*      */       } 
/*      */     } else {
/*  138 */       LiteralType type = null;
/*      */       
/*  140 */       while (members.hasNext()) {
/*  141 */         JavaStructureMember javaMember = members.next();
/*  142 */         if (javaMember.getOwner() instanceof LiteralElementMember) {
/*  143 */           type = ((LiteralElementMember)javaMember.getOwner()).getType();
/*      */         
/*      */         }
/*  146 */         else if (javaMember.getOwner() instanceof LiteralAttributeMember) {
/*      */           
/*  148 */           type = ((LiteralAttributeMember)javaMember.getOwner()).getType();
/*      */         } 
/*      */         
/*  151 */         type.accept(this);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preVisitSOAPSimpleType(SOAPSimpleType type) throws Exception {
/*  159 */     if (haveVisited((SOAPType)type)) {
/*      */       return;
/*      */     }
/*  162 */     typeVisited((SOAPType)type);
/*      */   }
/*      */   
/*      */   protected void preVisitSOAPAnyType(SOAPAnyType type) throws Exception {
/*  166 */     if (haveVisited((SOAPType)type)) {
/*      */       return;
/*      */     }
/*  169 */     typeVisited((SOAPType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitSOAPEnumerationType(SOAPEnumerationType type) throws Exception {
/*  174 */     if (haveVisited((SOAPType)type)) {
/*      */       return;
/*      */     }
/*  177 */     typeVisited((SOAPType)type);
/*      */   }
/*      */   
/*      */   protected void preVisitSOAPArrayType(SOAPArrayType type) throws Exception {
/*  181 */     if (haveVisited((SOAPType)type)) {
/*      */       return;
/*      */     }
/*  184 */     typeVisited((SOAPType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   public void preVisitSOAPStructureType(SOAPStructureType type) throws Exception {
/*  189 */     if (haveVisited((SOAPType)type)) {
/*      */       return;
/*      */     }
/*  192 */     typeVisited((SOAPType)type);
/*      */     
/*      */     try {
/*  195 */       writeObjectSerializerForType(type);
/*  196 */     } catch (IOException e) {
/*  197 */       fail("generator.cant.write", type.getName().getLocalPart());
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean haveVisited(SOAPType type) {
/*  202 */     return this.visitedTypes.contains(type);
/*      */   }
/*      */   
/*      */   private void typeVisited(SOAPType type) {
/*  206 */     this.visitedTypes.add(type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObjectSerializerForType(SOAPStructureType type) throws IOException {
/*  216 */     JavaType javaType = type.getJavaType();
/*  217 */     String className = this.env.getNames().typeObjectSerializerClassName(this.servicePackage, (SOAPType)type);
/*      */     
/*  219 */     if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/*  220 */       log("Class " + className + " exists. Not overriding.");
/*      */       return;
/*      */     } 
/*  223 */     File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  232 */     GeneratedFileInfo fi = new GeneratedFileInfo();
/*  233 */     fi.setFile(classFile);
/*  234 */     fi.setType("SoapObjectSerializer");
/*  235 */     this.env.addGeneratedFile(fi);
/*      */     
/*      */     try {
/*  238 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*      */ 
/*      */       
/*  241 */       writeObjectSerializerCode(out, type);
/*  242 */       out.close();
/*  243 */     } catch (IOException e) {
/*  244 */       fail("generator.cant.write", classFile.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObjectSerializerCode(IndentingWriter p, SOAPStructureType type) throws IOException {
/*  257 */     log("writing  serializer/deserializer for: " + type.getName());
/*  258 */     String className = this.env.getNames().typeObjectSerializerClassName(this.servicePackage, (SOAPType)type);
/*      */ 
/*      */     
/*  261 */     writePackage(p, className);
/*  262 */     writeImports(p);
/*  263 */     p.pln();
/*      */     
/*  265 */     writeClassDecl(p, className);
/*  266 */     writeMembers(p, type);
/*  267 */     p.pln();
/*  268 */     writeConstructor(p, className);
/*  269 */     p.pln();
/*  270 */     writeInitialize(p, type);
/*  271 */     p.pln();
/*  272 */     writeDoDeserializeMethod(p, type);
/*  273 */     p.pln();
/*  274 */     writeDoSerializeAttributesMethod(p, type);
/*  275 */     p.pln();
/*  276 */     writeDoSerializeInstanceMethod(p, type);
/*      */     
/*  278 */     if (type instanceof RPCResponseStructureType)
/*      */     {
/*  280 */       writeVerifyNameOverrideMethod(p, type);
/*      */     }
/*      */     
/*  283 */     p.pOln("}");
/*      */   }
/*      */   
/*      */   private void writeImports(IndentingWriter p) throws IOException {
/*  287 */     p.pln("import com.sun.xml.rpc.encoding.*;");
/*  288 */     p.pln("import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;");
/*      */     
/*  290 */     p.pln("import com.sun.xml.rpc.encoding.simpletype.*;");
/*  291 */     p.pln("import com.sun.xml.rpc.encoding.soap.SOAPConstants;");
/*  292 */     p.pln("import com.sun.xml.rpc.encoding.soap.SOAP12Constants;");
/*  293 */     p.pln("import com.sun.xml.rpc.streaming.*;");
/*  294 */     p.pln("import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;");
/*  295 */     p.pln("import javax.xml.namespace.QName;");
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeClassDecl(IndentingWriter p, String className) throws IOException {
/*  300 */     String baseClass = "ObjectSerializerBase";
/*  301 */     p.plnI("public class " + Names.stripQualifier(className) + " extends " + baseClass + " implements Initializable {");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeMembers(IndentingWriter p, SOAPStructureType type) throws IOException {
/*  311 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*      */     
/*  313 */     Iterator<JavaStructureMember> iterator = javaStructure.getMembers();
/*  314 */     Set processedTypes = new HashSet();
/*      */ 
/*      */ 
/*      */     
/*  318 */     for (Iterator<SOAPAttributeMember> iter = type.getAttributeMembers(); iter.hasNext(); ) {
/*  319 */       SOAPAttributeMember attMember = iter.next();
/*  320 */       JavaStructureMember attJavaMember = attMember.getJavaStructureMember();
/*      */       
/*  322 */       GeneratorUtil.writeQNameDeclaration(p, attMember.getName(), this.env.getNames());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  327 */     while (iterator.hasNext()) {
/*  328 */       JavaStructureMember javaMember = iterator.next();
/*  329 */       if (javaMember.getOwner() instanceof SOAPAttributeMember)
/*      */         continue; 
/*  331 */       SOAPStructureMember member = (SOAPStructureMember)javaMember.getOwner();
/*  332 */       GeneratorUtil.writeQNameDeclaration(p, member.getName(), this.env.getNames());
/*      */ 
/*      */ 
/*      */       
/*  336 */       SOAPEncoding.writeStaticSerializer(p, this.servicePackage, member.getType(), processedTypes, this.writerFactory, this.env.getNames());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  344 */     iterator = javaStructure.getMembers();
/*  345 */     for (int i = 0; iterator.hasNext(); i++) {
/*  346 */       JavaStructureMember javaMember = iterator.next();
/*  347 */       p.p("private static final int ");
/*  348 */       p.pln(this.env.getNames().memberName(javaMember.getName().toUpperCase() + "_INDEX") + " = " + i + ";");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeConstructor(IndentingWriter p, String className) throws IOException {
/*  359 */     p.plnI("public " + Names.stripQualifier(className) + "(QName type, boolean encodeType, " + "boolean isNullable, String encodingStyle) {");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  364 */     p.pln("super(type, encodeType, isNullable, encodingStyle);");
/*  365 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeInitialize(IndentingWriter p, SOAPStructureType type) throws IOException {
/*  370 */     p.plnI("public void initialize(InternalTypeMappingRegistry registry) throws java.lang.Exception {");
/*      */     
/*  372 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*      */     
/*  374 */     Iterator<JavaStructureMember> iterator = javaStructure.getMembers();
/*  375 */     Set<String> processedTypes = new HashSet();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  380 */     while (iterator.hasNext()) {
/*  381 */       JavaStructureMember javaMember = iterator.next();
/*  382 */       if (javaMember.getOwner() instanceof SOAPAttributeMember)
/*      */         continue; 
/*  384 */       SOAPStructureMember member = (SOAPStructureMember)javaMember.getOwner();
/*  385 */       SOAPType sOAPType = member.getType();
/*  386 */       if (!processedTypes.contains(sOAPType.getName() + ";" + sOAPType.getJavaType().getRealName())) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  391 */         SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, (AbstractType)sOAPType);
/*  392 */         writer.initializeSerializer(p, this.env.getNames().getTypeQName(sOAPType.getName()), "registry");
/*      */ 
/*      */ 
/*      */         
/*  396 */         processedTypes.add(member.getType().getName() + ";" + sOAPType.getJavaType().getRealName());
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  402 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeDoDeserializeMethod(IndentingWriter p, SOAPStructureType type) throws IOException {
/*  409 */     if (((JavaStructureType)type.getJavaType()).isAbstract()) {
/*  410 */       p.plnI("public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,");
/*      */       
/*  412 */       p.pln("SOAPDeserializationContext context) throws java.lang.Exception {");
/*  413 */       p.p("throw new DeserializationException(\"soap.unsupportedType\", ");
/*      */       
/*  415 */       GeneratorUtil.writeNewQName(p, type.getName());
/*  416 */       p.pln(".toString());");
/*  417 */       p.pOln("}");
/*  418 */     } else if (deserializeToDetail((AbstractType)type)) {
/*      */ 
/*      */       
/*  421 */       writeDetailDoDeserializeMethod(p, (AbstractType)type);
/*      */     } else {
/*  423 */       writeStandardDoDeserializeMethod(p, type);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean deserializeToDetail(AbstractType type) {
/*  428 */     JavaStructureType javaType = (JavaStructureType)type.getJavaType();
/*      */     
/*  430 */     if (!(javaType instanceof JavaException)) {
/*  431 */       return false;
/*      */     }
/*  433 */     Iterator<JavaStructureMember> members = javaType.getMembers();
/*      */     
/*  435 */     while (members.hasNext()) {
/*  436 */       JavaStructureMember member = members.next();
/*  437 */       if (member.getConstructorPos() < 0 && member.getWriteMethod() == null)
/*      */       {
/*  439 */         return true;
/*      */       }
/*      */     } 
/*  442 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeStandardDoDeserializeMethod(IndentingWriter p, SOAPStructureType type) throws IOException {
/*  449 */     JavaStructureType javaType = (JavaStructureType)type.getJavaType();
/*  450 */     JavaStructureMember[] constructorArgs = SOAPObjectBuilderGenerator.getConstructorArgs(javaType);
/*      */     
/*  452 */     boolean usesConstructor = (constructorArgs.length > 0);
/*  453 */     p.plnI("public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,");
/*      */     
/*  455 */     p.pln("SOAPDeserializationContext context) throws java.lang.Exception {");
/*  456 */     if (usesConstructor) {
/*  457 */       p.pln(javaType.getName() + " instance = null;");
/*  458 */       Iterator<JavaStructureMember> members = javaType.getMembers();
/*      */ 
/*      */ 
/*      */       
/*  462 */       while (members.hasNext()) {
/*  463 */         JavaStructureMember member = members.next();
/*  464 */         String javaName = member.getType().getName();
/*  465 */         if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/*  466 */           javaName = SimpleToBoxedUtil.getBoxedClassName(javaName);
/*  467 */           String initVal = member.getType().getInitString();
/*  468 */           p.pln("java.lang.Object " + member.getName() + "Temp = new " + javaName + "(" + initVal + ");");
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  477 */         p.pln("java.lang.Object " + member.getName() + "Temp = null;");
/*      */       } 
/*      */     } else {
/*      */       
/*  481 */       p.pln(javaType.getName() + " instance = new " + javaType.getName() + "();");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  487 */     if (SOAPObjectBuilderGenerator.needBuilder(type)) {
/*  488 */       p.pln(this.env.getNames().typeObjectBuilderClassName(this.servicePackage, (SOAPType)type) + " builder = null;");
/*      */     }
/*      */ 
/*      */     
/*  492 */     p.pln("java.lang.Object member;");
/*  493 */     p.pln("boolean isComplete = true;");
/*  494 */     p.pln("javax.xml.namespace.QName elementName;");
/*  495 */     p.pln();
/*  496 */     if (type.getAttributeMembersCount() > 0) {
/*  497 */       writeDeserializeAttributes(p, type, "reader", usesConstructor);
/*  498 */       p.pln();
/*      */     } 
/*  500 */     p.pln("reader.nextElementContent();");
/*      */     
/*  502 */     if (type.getMembersCount() > 0) {
/*  503 */       if (type instanceof SOAPOrderedStructureType) {
/*  504 */         writeDeserializeElements(p, (SOAPOrderedStructureType)type, "reader", constructorArgs);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  509 */       else if (type instanceof RPCResponseStructureType) {
/*  510 */         writeDeserializeElements(p, (RPCResponseStructureType)type, "reader");
/*      */ 
/*      */       
/*      */       }
/*  514 */       else if (type instanceof SOAPUnorderedStructureType) {
/*  515 */         writeDeserializeElements(p, (SOAPUnorderedStructureType)type, "reader");
/*      */       } 
/*      */ 
/*      */       
/*  519 */       p.pln();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  524 */     if (type.getSubtypes() == null || !(javaType instanceof JavaException) || !type.getSubtypes().hasNext())
/*      */     {
/*      */       
/*  527 */       p.pln("XMLReaderUtil.verifyReaderState(reader, XMLReader.END);");
/*      */     }
/*  529 */     p.pln("return (isComplete ? (java.lang.Object)instance : (java.lang.Object)state);");
/*  530 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void writeDetailDoDeserializeMethod(IndentingWriter p, AbstractType type) throws IOException {
/*  538 */     if (type instanceof SOAPType) {
/*  539 */       p.plnI("public java.lang.Object doDeserialize(SOAPDeserializationState state, XMLReader reader,");
/*      */       
/*  541 */       p.pln("SOAPDeserializationContext context) throws Exception {");
/*  542 */     } else if (type instanceof LiteralType) {
/*  543 */       p.plnI("public java.lang.Object doDeserialize(XMLReader reader, SOAPDeserializationContext context) throws java.lang.Exception {");
/*      */     } else {
/*      */       
/*  546 */       fail("generator.unsupported.type.encountered", type.getName().getLocalPart(), type.getName().getNamespaceURI());
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  551 */     p.pln("java.lang.Object detail;");
/*  552 */     p.pln("DetailFragmentDeserializer detailDeserializer = new DetailFragmentDeserializer(type, encodingStyle);");
/*      */     
/*  554 */     p.pln("detail = detailDeserializer.deserialize(reader.getName(), reader, context);");
/*      */     
/*  556 */     p.pln("return detail;");
/*  557 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeDeserializeElements(IndentingWriter p, SOAPOrderedStructureType type, String reader, JavaStructureMember[] constructorArgs) throws IOException {
/*  567 */     boolean usesConstructor = (constructorArgs.length > 0);
/*  568 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*      */     
/*  570 */     Iterator<JavaStructureMember> iterator = javaStructure.getMembers();
/*      */     
/*  572 */     while (iterator.hasNext()) {
/*  573 */       JavaStructureMember javaMember = iterator.next();
/*  574 */       p.pln("elementName = " + reader + ".getName();");
/*  575 */       writeMemberDeserializer(p, (SOAPStructureType)type, javaMember, reader, true, true, false, false, usesConstructor);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  586 */     if (usesConstructor) {
/*      */       
/*  588 */       p.plnI("if (isComplete) {");
/*  589 */       p.p("instance = new " + javaStructure.getName() + "(");
/*      */       
/*  591 */       for (int i = 0; i < constructorArgs.length; i++) {
/*  592 */         String valueStr; if (i > 0)
/*  593 */           p.p(", "); 
/*  594 */         JavaStructureMember javaMember = constructorArgs[i];
/*  595 */         String javaName = javaMember.getType().getName();
/*  596 */         if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/*  597 */           String boxName = SimpleToBoxedUtil.getBoxedClassName(javaName);
/*      */           
/*  599 */           valueStr = SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")" + javaMember.getName() + "Temp", javaName);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  604 */           valueStr = "(" + javaName + ")" + javaMember.getName() + "Temp";
/*      */         } 
/*      */         
/*  607 */         p.p(valueStr);
/*      */       } 
/*  609 */       p.pln(");");
/*  610 */       p.pO("}");
/*  611 */       if (SOAPObjectBuilderGenerator.needBuilder((SOAPStructureType)type)) {
/*  612 */         p.plnI(" else {");
/*  613 */         iterator = javaStructure.getMembers();
/*  614 */         while (iterator.hasNext()) {
/*  615 */           String valueStr; JavaStructureMember javaMember = iterator.next();
/*  616 */           String javaName = javaMember.getType().getName();
/*  617 */           if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/*  618 */             String boxName = SimpleToBoxedUtil.getBoxedClassName(javaName);
/*      */             
/*  620 */             valueStr = SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")" + javaMember.getName() + "Temp", javaName);
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */ 
/*      */             
/*  629 */             valueStr = "(" + javaName + ")" + javaMember.getName() + "Temp";
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  636 */           String writeMethod = javaMember.getWriteMethod();
/*  637 */           if (writeMethod == null) {
/*  638 */             writeMethod = "set" + StringUtils.capitalize(javaMember.getName());
/*      */           }
/*      */ 
/*      */           
/*  642 */           p.pln("builder." + writeMethod + "(" + valueStr + ");");
/*      */         } 
/*  644 */         p.pOln("}");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeDeserializeElements(IndentingWriter p, RPCResponseStructureType type, String reader) throws IOException {
/*  655 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*      */     
/*  657 */     Iterator<JavaStructureMember> iterator = javaStructure.getMembers();
/*      */     
/*  659 */     int memberCnt = javaStructure.getMembersCount();
/*  660 */     if (this.curSOAPVersion.equals(SOAPVersion.SOAP_12)) {
/*  661 */       p.pln("// SOAP 1.2 deserializer result element");
/*  662 */       p.plnI("if (" + reader + ".getState() == XMLReader.START) {");
/*  663 */       p.plnI("if(reader.getName().equals(SOAP12Constants.QNAME_SOAP_RESULT)) {");
/*      */       
/*  665 */       p.pln("reader.skipElement();");
/*  666 */       p.pln("reader.nextElementContent();");
/*  667 */       p.pOln("}");
/*  668 */       p.pOln("}");
/*      */     } 
/*      */     
/*  671 */     if (memberCnt > 1) {
/*  672 */       p.plnI("for (int i=0; i<" + memberCnt + "; i++) {");
/*  673 */       p.pln("elementName = " + reader + ".getName();");
/*  674 */       p.plnI("if (" + reader + ".getState() == XMLReader.END) {");
/*  675 */       p.pln("break;");
/*  676 */       p.pOln("}");
/*      */     } else {
/*  678 */       p.pln("elementName = " + reader + ".getName();");
/*      */     } 
/*  680 */     for (int i = 0; iterator.hasNext(); i++) {
/*  681 */       JavaStructureMember javaMember = iterator.next();
/*  682 */       writeMemberDeserializer(p, (SOAPStructureType)type, javaMember, reader, true, true, (memberCnt > 1), !iterator.hasNext(), false);
/*      */     } 
/*      */     
/*  685 */     if (memberCnt > 1) {
/*  686 */       p.pOln("}");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeDeserializeElements(IndentingWriter p, SOAPUnorderedStructureType type, String reader) throws IOException {
/*  710 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*      */     
/*  712 */     Iterator<JavaStructureMember> iterator = javaStructure.getMembers();
/*      */     
/*  714 */     int memberCnt = javaStructure.getMembersCount();
/*  715 */     if (memberCnt > 1) {
/*  716 */       p.plnI("for (int i=0; i<" + memberCnt + "; i++) {");
/*  717 */       p.pln("elementName = " + reader + ".getName();");
/*  718 */       p.plnI("if (" + reader + ".getState() == XMLReader.END) {");
/*  719 */       p.pln("break;");
/*  720 */       p.pOln("}");
/*      */     } else {
/*  722 */       p.pln("elementName = " + reader + ".getName();");
/*      */     } 
/*  724 */     for (int i = 0; iterator.hasNext(); i++) {
/*  725 */       JavaStructureMember javaMember = iterator.next();
/*  726 */       writeMemberDeserializer(p, (SOAPStructureType)type, javaMember, reader, true, true, (memberCnt > 1), !iterator.hasNext(), false);
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  737 */     if (memberCnt > 1) {
/*  738 */       p.pOln("}");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeMemberDeserializer(IndentingWriter p, SOAPStructureType type, JavaStructureMember javaMember, String reader, boolean preCheckElementName, boolean checkElementName, boolean unOrdered, boolean writeThrow, boolean usesConstructor) throws IOException {
/*  756 */     if (javaMember.getOwner() instanceof SOAPAttributeMember)
/*      */       return; 
/*  758 */     SOAPStructureMember member = (SOAPStructureMember)javaMember.getOwner();
/*      */     
/*  760 */     String memberConstName = this.env.getNames().memberName(javaMember.getName().toUpperCase());
/*      */     
/*  762 */     String memberQName = this.env.getNames().getQNameName(member.getName());
/*  763 */     if (!checkElementName) {
/*  764 */       memberQName = "null";
/*      */     }
/*      */     
/*  767 */     if (!unOrdered) {
/*  768 */       p.plnI("if (" + reader + ".getState() == XMLReader.START) {");
/*      */     }
/*      */     
/*  771 */     if (preCheckElementName && checkElementName) {
/*  772 */       p.plnI("if (elementName.equals(" + memberQName + ")) {");
/*      */     }
/*  774 */     SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, (AbstractType)member.getType());
/*      */     
/*  776 */     String serializer = writer.deserializerMemberName();
/*  777 */     boolean referenceable = member.getType().isReferenceable();
/*  778 */     String memberName = "member";
/*      */     
/*  780 */     p.pln("member = " + serializer + ".deserialize(" + memberQName + ", " + reader + ", context);");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  788 */     if (referenceable) {
/*  789 */       p.plnI("if (member instanceof SOAPDeserializationState) {");
/*  790 */       p.plnI("if (builder == null) {");
/*  791 */       p.pln("builder = new " + this.env.getNames().typeObjectBuilderClassName(this.servicePackage, (SOAPType)type) + "();");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  797 */       p.pOln("}");
/*  798 */       p.pln("state = registerWithMemberState(instance, state, member, " + memberConstName + "_INDEX, builder);");
/*      */ 
/*      */ 
/*      */       
/*  802 */       p.pln("isComplete = false;");
/*  803 */       p.pOlnI("} else {");
/*  804 */       if (usesConstructor) {
/*  805 */         p.pln(javaMember.getName() + "Temp = member;");
/*  806 */       } else if (javaMember.isPublic()) {
/*  807 */         p.pln("instance." + javaMember.getName() + " = (" + javaMember.getType().getName() + ")member;");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  814 */       else if (javaMember.getDeclaringClass() != null) {
/*  815 */         p.pln("((" + javaMember.getDeclaringClass().replace('$', '.') + ")instance)." + javaMember.getWriteMethod() + "((" + javaMember.getType().getName() + ")member);");
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/*  824 */         p.pln("instance." + javaMember.getWriteMethod() + "((" + javaMember.getType().getName() + ")member);");
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  832 */       p.pOln("}");
/*      */     } else {
/*  834 */       String valueStr = null;
/*  835 */       String javaName = javaMember.getType().getName();
/*      */       
/*  837 */       if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/*  838 */         String boxName = SimpleToBoxedUtil.getBoxedClassName(javaName);
/*  839 */         valueStr = SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")member", javaName);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  844 */         valueStr = "(" + javaName + ")member";
/*      */       } 
/*      */       
/*  847 */       if (usesConstructor) {
/*  848 */         p.pln(javaMember.getName() + "Temp = member;");
/*  849 */       } else if (javaMember.isPublic()) {
/*  850 */         p.pln("instance." + javaMember.getName() + " = " + valueStr + ";");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*  857 */       else if (javaMember.getDeclaringClass() != null) {
/*  858 */         p.pln("((" + javaMember.getDeclaringClass().replace('$', '.') + ")instance)." + javaMember.getWriteMethod() + "(" + valueStr + ");");
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/*  867 */         p.pln("instance." + javaMember.getWriteMethod() + "(" + valueStr + ");");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  876 */     p.pln(reader + ".nextElementContent();");
/*  877 */     if (unOrdered)
/*  878 */       p.pln("continue;"); 
/*  879 */     if (preCheckElementName && checkElementName) {
/*  880 */       p.pO("}");
/*  881 */       if (writeThrow) {
/*  882 */         p.plnI(" else {");
/*  883 */         p.pln("throw new DeserializationException(\"soap.unexpectedElementName\", new Object[] {" + memberQName + ", elementName});");
/*      */ 
/*      */ 
/*      */         
/*  887 */         p.pOln("}");
/*      */       } else {
/*  889 */         p.pln();
/*      */       } 
/*      */     } 
/*  892 */     if (!unOrdered) {
/*  893 */       p.pOln("}");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeDoSerializeAttributesMethod(IndentingWriter p, SOAPStructureType type) throws IOException {
/*  901 */     p.plnI("public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {");
/*      */     
/*  903 */     p.pln(type.getJavaType().getName() + " instance = (" + type.getJavaType().getName() + ")obj;");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  908 */     p.pln();
/*  909 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  916 */     Iterator<SOAPAttributeMember> iterator = type.getAttributeMembers();
/*  917 */     while (iterator.hasNext()) {
/*      */       String encoder;
/*  919 */       SOAPAttributeMember member = iterator.next();
/*  920 */       JavaStructureMember javaMember = member.getJavaStructureMember();
/*  921 */       String memberConstName = member.getName().getLocalPart().toUpperCase();
/*  922 */       String memberQName = this.env.getNames().getQNameName(member.getName());
/*  923 */       SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, (AbstractType)member.getType());
/*      */       
/*  925 */       String serializer = writer.serializerMemberName();
/*  926 */       String valueStr = null;
/*  927 */       String javaName = javaMember.getType().getName();
/*      */ 
/*      */ 
/*      */       
/*  931 */       if (member.getType() instanceof SOAPEnumerationType) {
/*  932 */         encoder = member.getType().getJavaType().getName() + "_Encoder";
/*      */       } else {
/*      */         
/*  935 */         encoder = SimpleTypeSerializerWriter.getTypeEncoder((AbstractType)member.getType());
/*      */       } 
/*      */       
/*  938 */       if (javaMember.isPublic()) {
/*  939 */         valueStr = "instance." + javaMember.getName();
/*      */       } else {
/*  941 */         String methName = javaMember.getReadMethod();
/*  942 */         valueStr = "instance." + methName + "()";
/*      */       } 
/*      */       
/*  945 */       if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/*  946 */         valueStr = SimpleToBoxedUtil.getBoxedExpressionOfType(valueStr, javaName);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  952 */       p.plnI("if (" + valueStr + " != null) {");
/*  953 */       p.pln("writer.writeAttribute(" + memberQName + ", " + encoder + ".getInstance().objectToString(" + valueStr + ", writer));");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  961 */       p.pOln("}");
/*  962 */       if (member.isRequired()) {
/*  963 */         p.plnI("else {");
/*  964 */         p.pln("throw new DeserializationException(\"literal.requiredAttributeConstraint\", new java.lang.Object[] {" + memberQName + "});");
/*      */ 
/*      */ 
/*      */         
/*  968 */         p.pOln("}");
/*      */       } 
/*      */     } 
/*      */     
/*  972 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeDoSerializeInstanceMethod(IndentingWriter p, SOAPStructureType type) throws IOException {
/*  979 */     p.plnI("public void doSerializeInstance(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {");
/*      */     
/*  981 */     p.pln(type.getJavaType().getName() + " instance = (" + type.getJavaType().getName() + ")obj;");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  986 */     p.pln();
/*  987 */     boolean deserializeToDetail = deserializeToDetail((AbstractType)type);
/*  988 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*      */     
/*  990 */     Iterator<JavaStructureMember> iterator = javaStructure.getMembers();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  998 */     for (int i = 0; iterator.hasNext(); i++) {
/*  999 */       JavaStructureMember javaMember = iterator.next();
/* 1000 */       if (!(javaMember.getOwner() instanceof SOAPAttributeMember)) {
/*      */         
/* 1002 */         SOAPStructureMember member = (SOAPStructureMember)javaMember.getOwner();
/* 1003 */         String memberConstName = member.getName().getLocalPart().toUpperCase();
/* 1004 */         String memberQName = this.env.getNames().getQNameName(member.getName());
/* 1005 */         SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, (AbstractType)member.getType());
/*      */         
/* 1007 */         String serializer = writer.serializerMemberName();
/* 1008 */         String valueStr = null;
/* 1009 */         String javaName = javaMember.getType().getName();
/* 1010 */         if (i == 0 && type instanceof RPCResponseStructureType && this.curSOAPVersion.equals(SOAPVersion.SOAP_12)) {
/*      */ 
/*      */           
/* 1013 */           p.pln("// SOAP 1.2 - add rpc namespace, and add rpc:result and result element qname");
/*      */           
/* 1015 */           p.pln("writer.startElement(SOAP12Constants.QNAME_SOAP_RESULT);");
/*      */           
/* 1017 */           p.pln("writer.writeChars(writer.getPrefix(" + memberQName + ".getNamespaceURI())+\":\"+" + memberQName + ".getLocalPart());");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1023 */           p.pln("writer.endElement();//rpc:result");
/*      */         } 
/*      */         
/* 1026 */         if (javaMember.isPublic()) {
/* 1027 */           valueStr = "instance." + javaMember.getName();
/*      */         } else {
/* 1029 */           String methName = javaMember.getReadMethod();
/* 1030 */           valueStr = "instance." + methName + "()";
/*      */         } 
/* 1032 */         if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/* 1033 */           valueStr = SimpleToBoxedUtil.getBoxedExpressionOfType(valueStr, javaName);
/*      */ 
/*      */         
/*      */         }
/* 1037 */         else if (deserializeToDetail) {
/* 1038 */           p.plnI("if (" + valueStr + " != null) {");
/*      */         } 
/* 1040 */         p.pln(serializer + ".serialize(" + valueStr + ", " + memberQName + ", null, writer, context);");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1047 */         if (deserializeToDetail && !SimpleToBoxedUtil.isPrimitive(javaName))
/*      */         {
/* 1049 */           p.pOln("}"); } 
/*      */       } 
/*      */     } 
/* 1052 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeDeserializeAttributes(IndentingWriter p, SOAPStructureType type, String reader, boolean usesConstructor) throws IOException {
/* 1062 */     p.pln("Attributes attributes = reader.getAttributes();");
/* 1063 */     p.pln("String attribute = null;");
/* 1064 */     Iterator<SOAPAttributeMember> iterator = type.getAttributeMembers();
/* 1065 */     while (iterator.hasNext()) {
/*      */       String encoder;
/* 1067 */       SOAPAttributeMember member = iterator.next();
/* 1068 */       JavaStructureMember javaMember = member.getJavaStructureMember();
/* 1069 */       String memberConstName = member.getName().getLocalPart().toUpperCase();
/*      */       
/* 1071 */       String memberQName = this.env.getNames().getQNameName(member.getName());
/* 1072 */       p.pln("attribute = attributes.getValue(" + memberQName + ");");
/* 1073 */       p.plnI("if (attribute != null) {");
/*      */ 
/*      */       
/* 1076 */       if (member.getType() instanceof SOAPEnumerationType) {
/* 1077 */         encoder = member.getType().getJavaType().getName() + "_Encoder";
/*      */       } else {
/* 1079 */         encoder = SimpleTypeSerializerWriter.getTypeEncoder((AbstractType)member.getType());
/*      */       } 
/*      */       
/* 1082 */       if (usesConstructor) {
/* 1083 */         p.pln(javaMember.getName() + "Temp = " + encoder + ".getInstance().stringToObject(attribute, reader);");
/*      */       } else {
/*      */         String valueStr;
/*      */ 
/*      */ 
/*      */         
/* 1089 */         p.pln("member = " + encoder + ".getInstance().stringToObject(attribute, reader);");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1094 */         String javaName = javaMember.getType().getName();
/*      */         
/* 1096 */         if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/* 1097 */           String boxName = SimpleToBoxedUtil.getBoxedClassName(javaName);
/*      */           
/* 1099 */           valueStr = SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")member", javaName);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/* 1104 */           valueStr = "(" + javaName + ")member";
/*      */         } 
/* 1106 */         if (javaMember.isPublic()) {
/* 1107 */           p.pln("instance." + javaMember.getName() + " = " + valueStr + ";");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/* 1114 */         else if (javaMember.getDeclaringClass() != null) {
/* 1115 */           p.pln("((" + javaMember.getDeclaringClass().replace('$', '.') + ")instance)." + javaMember.getWriteMethod() + "(" + valueStr + ");");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1126 */           p.pln("instance." + javaMember.getWriteMethod() + "(" + valueStr + ");");
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1136 */       p.pOln("}");
/* 1137 */       if (member.isRequired()) {
/* 1138 */         p.plnI("else {");
/* 1139 */         p.pln("throw new DeserializationException(\"literal.missingRequiredAttribute\", new java.lang.Object[] {" + memberQName + "});");
/*      */ 
/*      */ 
/*      */         
/* 1143 */         p.pOln("}");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeVerifyNameOverrideMethod(IndentingWriter p, SOAPStructureType type) throws IOException {
/* 1152 */     p.plnI("protected void verifyName(XMLReader reader, javax.xml.namespace.QName expectedName) throws java.lang.Exception {");
/*      */     
/* 1154 */     p.pOln("}");
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\SOAPObjectSerializerGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */