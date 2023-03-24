/*      */ package com.sun.xml.rpc.processor.generator;
/*      */ 
/*      */ import com.sun.xml.rpc.processor.config.Configuration;
/*      */ import com.sun.xml.rpc.processor.generator.writer.LiteralSimpleSerializerWriter;
/*      */ import com.sun.xml.rpc.processor.generator.writer.SerializerWriter;
/*      */ import com.sun.xml.rpc.processor.model.AbstractType;
/*      */ import com.sun.xml.rpc.processor.model.Fault;
/*      */ import com.sun.xml.rpc.processor.model.Model;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*      */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAllType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralContentMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralFragmentType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralIDType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralSimpleType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*      */ import com.sun.xml.rpc.processor.model.literal.LiteralWildcardMember;
/*      */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*      */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*      */ import com.sun.xml.rpc.soap.SOAPVersion;
/*      */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*      */ import com.sun.xml.rpc.wsdl.document.soap.SOAPConstants;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import javax.xml.namespace.QName;
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
/*      */ public class LiteralObjectSerializerGenerator
/*      */   extends GeneratorBase
/*      */ {
/*      */   private Set visitedTypes;
/*      */   
/*      */   public LiteralObjectSerializerGenerator() {}
/*      */   
/*      */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties) {
/*   84 */     return new LiteralObjectSerializerGenerator(model, config, properties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*   92 */     return new LiteralObjectSerializerGenerator(model, config, properties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private LiteralObjectSerializerGenerator(Model model, Configuration config, Properties properties) {
/*   99 */     super(model, config, properties);
/*      */   }
/*      */   
/*      */   protected void preVisitModel(Model model) throws Exception {
/*  103 */     this.visitedTypes = new HashSet();
/*      */   }
/*      */   
/*      */   protected void postVisitModel(Model model) throws Exception {
/*  107 */     Iterator<AbstractType> types = model.getExtraTypes();
/*      */     
/*  109 */     while (types.hasNext()) {
/*  110 */       AbstractType type = types.next();
/*  111 */       if (type.isLiteralType())
/*  112 */         ((LiteralType)type).accept(this); 
/*      */     } 
/*  114 */     this.visitedTypes = null;
/*      */   }
/*      */   
/*      */   protected void visitFault(Fault fault) throws Exception {
/*  118 */     if (fault.getBlock().getType().isLiteralType()) {
/*  119 */       ((LiteralType)fault.getBlock().getType()).accept(this);
/*  120 */       JavaException exception = fault.getJavaException();
/*  121 */       Iterator<JavaStructureMember> members = exception.getMembers();
/*  122 */       AbstractType aType = (AbstractType)exception.getOwner();
/*  123 */       LiteralType type = null;
/*      */       
/*  125 */       while (members.hasNext()) {
/*  126 */         JavaStructureMember javaMember = members.next();
/*  127 */         if (javaMember.getOwner() instanceof LiteralElementMember) {
/*  128 */           type = ((LiteralElementMember)javaMember.getOwner()).getType();
/*      */         
/*      */         }
/*  131 */         else if (javaMember.getOwner() instanceof LiteralAttributeMember) {
/*      */           
/*  133 */           type = ((LiteralAttributeMember)javaMember.getOwner()).getType();
/*      */         } 
/*      */         
/*  136 */         type.accept(this);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preVisitLiteralSimpleType(LiteralSimpleType type) throws Exception {
/*  145 */     if (haveVisited((AbstractType)type)) {
/*      */       return;
/*      */     }
/*  148 */     typeVisited((AbstractType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitLiteralSequenceType(LiteralSequenceType type) throws Exception {
/*  153 */     if (haveVisited((AbstractType)type)) {
/*      */       return;
/*      */     }
/*  156 */     typeVisited((AbstractType)type);
/*  157 */     Iterator<LiteralAttributeMember> attributes = type.getAttributeMembers();
/*      */     
/*  159 */     while (attributes.hasNext()) {
/*  160 */       LiteralAttributeMember attribute = attributes.next();
/*  161 */       attribute.getType().accept(this);
/*      */     } 
/*  163 */     LiteralContentMember content = type.getContentMember();
/*  164 */     if (content != null) {
/*  165 */       content.getType().accept(this);
/*      */     }
/*  167 */     Iterator<LiteralElementMember> elements = type.getElementMembers();
/*      */     
/*  169 */     while (elements.hasNext()) {
/*  170 */       LiteralElementMember element = elements.next();
/*  171 */       element.getType().accept(this);
/*      */     } 
/*      */     try {
/*  174 */       generateObjectSerializerForType((LiteralStructuredType)type);
/*  175 */     } catch (IOException e) {
/*  176 */       fail("generator.cant.write", type.getName().getLocalPart());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitLiteralArrayWrapperType(LiteralArrayWrapperType type) throws Exception {
/*  182 */     preVisitLiteralSequenceType((LiteralSequenceType)type);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitLiteralAllType(LiteralAllType type) throws Exception {
/*  187 */     if (haveVisited((AbstractType)type)) {
/*      */       return;
/*      */     }
/*  190 */     typeVisited((AbstractType)type);
/*  191 */     Iterator<LiteralAttributeMember> attributes = type.getAttributeMembers();
/*      */     
/*  193 */     while (attributes.hasNext()) {
/*  194 */       LiteralAttributeMember attribute = attributes.next();
/*  195 */       attribute.getType().accept(this);
/*      */     } 
/*  197 */     LiteralContentMember content = type.getContentMember();
/*  198 */     if (content != null) {
/*  199 */       content.getType().accept(this);
/*      */     }
/*  201 */     Iterator<LiteralElementMember> elements = type.getElementMembers();
/*      */     
/*  203 */     while (elements.hasNext()) {
/*  204 */       LiteralElementMember element = elements.next();
/*  205 */       element.getType().accept(this);
/*      */     } 
/*      */     try {
/*  208 */       generateObjectSerializerForType((LiteralStructuredType)type);
/*  209 */     } catch (IOException e) {
/*  210 */       fail("generator.cant.write", type.getName().getLocalPart());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void preVisitLiteralFragmentType(LiteralFragmentType type) throws Exception {
/*  216 */     if (haveVisited((AbstractType)type)) {
/*      */       return;
/*      */     }
/*  219 */     typeVisited((AbstractType)type);
/*      */   }
/*      */   
/*      */   private boolean haveVisited(AbstractType type) {
/*  223 */     return this.visitedTypes.contains(type);
/*      */   }
/*      */   
/*      */   private void typeVisited(AbstractType type) {
/*  227 */     this.visitedTypes.add(type);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void generateObjectSerializerForType(LiteralStructuredType type) throws IOException {
/*  233 */     setIDAndIDREFFlags(type);
/*  234 */     writeObjectSerializerForType(type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeObjectSerializerForType(LiteralStructuredType type) throws IOException {
/*  244 */     JavaType javaType = type.getJavaType();
/*  245 */     if (javaType == null) {
/*  246 */       fail("generator.invalid.model.state.no.javatype", type.getName().getLocalPart());
/*      */     }
/*      */ 
/*      */     
/*  250 */     String className = this.env.getNames().typeObjectSerializerClassName(this.servicePackage, (LiteralType)type);
/*      */     
/*  252 */     if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/*  253 */       log("Class " + className + " exists. Not overriding.");
/*      */       return;
/*      */     } 
/*  256 */     File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  266 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/*  267 */       fi.setFile(classFile);
/*  268 */       fi.setType("LiteralObjectSerializer");
/*  269 */       this.env.addGeneratedFile(fi);
/*      */       
/*  271 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*      */ 
/*      */       
/*  274 */       writeObjectSerializerCode(out, type);
/*  275 */       out.close();
/*  276 */     } catch (IOException e) {
/*  277 */       fail("generator.cant.write", classFile.toString());
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
/*      */   private void writeObjectSerializerCode(IndentingWriter p, LiteralStructuredType type) throws IOException {
/*  290 */     log("writing  serializer/deserializer for: " + type.getName().getLocalPart());
/*      */ 
/*      */     
/*  293 */     String className = this.env.getNames().typeObjectSerializerClassName(this.servicePackage, (LiteralType)type);
/*      */     
/*  295 */     if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/*  296 */       log("Class " + className + " exists. Not overriding.");
/*      */       
/*      */       return;
/*      */     } 
/*  300 */     writePackage(p, className);
/*  301 */     writeImports(p);
/*  302 */     p.pln();
/*      */     
/*  304 */     writeClassDecl(p, className);
/*  305 */     writeMembers(p, type);
/*  306 */     p.pln();
/*  307 */     writeConstructor(p, className);
/*  308 */     p.pln();
/*  309 */     writeInitialize(p, type);
/*  310 */     p.pln();
/*  311 */     if (this.enableIDTypeHandling) {
/*  312 */       writeGetIDMethod(p, type);
/*  313 */       p.pln();
/*      */     } 
/*  315 */     writeDoDeserializeMethod(p, type);
/*  316 */     p.pln();
/*  317 */     writeDoSerializeAttributesMethod(p, type);
/*  318 */     writeDoSerializeMethod(p, type);
/*  319 */     if (this.enableIDREFTypeHandling) {
/*  320 */       writeIDObjectResolver(p, type);
/*      */     }
/*  322 */     p.pOln("}");
/*      */   }
/*      */   
/*      */   private void writeImports(IndentingWriter p) throws IOException {
/*  326 */     p.pln("import com.sun.xml.rpc.encoding.*;");
/*  327 */     p.pln("import com.sun.xml.rpc.encoding.xsd.XSDConstants;");
/*  328 */     p.pln("import com.sun.xml.rpc.encoding.literal.*;");
/*  329 */     p.pln("import com.sun.xml.rpc.encoding.literal.DetailFragmentDeserializer;");
/*      */     
/*  331 */     p.pln("import com.sun.xml.rpc.encoding.simpletype.*;");
/*  332 */     p.pln("import com.sun.xml.rpc.encoding.soap.SOAPConstants;");
/*  333 */     p.pln("import com.sun.xml.rpc.encoding.soap.SOAP12Constants;");
/*  334 */     p.pln("import com.sun.xml.rpc.streaming.*;");
/*  335 */     p.pln("import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;");
/*  336 */     p.pln("import javax.xml.namespace.QName;");
/*  337 */     p.pln("import java.util.List;");
/*  338 */     p.pln("import java.util.ArrayList;");
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeClassDecl(IndentingWriter p, String className) throws IOException {
/*  343 */     if (this.enableIDTypeHandling) {
/*  344 */       p.plnI("public class " + Names.stripQualifier(className) + " extends LiteralObjectSerializerBase implements Initializable, IDREFSerializerHelper {");
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  349 */       p.plnI("public class " + Names.stripQualifier(className) + " extends LiteralObjectSerializerBase implements Initializable  {");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeMembers(IndentingWriter p, LiteralStructuredType type) throws IOException {
/*  358 */     Set processedTypes = new HashSet();
/*      */     
/*  360 */     for (Iterator<LiteralAttributeMember> iterator = type.getAttributeMembers(); iterator.hasNext(); ) {
/*  361 */       LiteralAttributeMember member = iterator.next();
/*      */       
/*  363 */       JavaStructureMember javaMember = member.getJavaStructureMember();
/*  364 */       GeneratorUtil.writeQNameDeclaration(p, member.getName(), this.env.getNames());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  370 */     for (Iterator<LiteralElementMember> iter = type.getElementMembers(); iter.hasNext(); ) {
/*  371 */       LiteralElementMember member = iter.next();
/*  372 */       JavaStructureMember javaMember = member.getJavaStructureMember();
/*  373 */       if (!member.isWildcard()) {
/*  374 */         GeneratorUtil.writeQNameDeclaration(p, member.getName(), this.env.getNames());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  379 */       LiteralEncoding.writeStaticSerializer(p, this.servicePackage, member.getType(), processedTypes, this.writerFactory, this.env.getNames());
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  388 */     if (this.enableIDREFTypeHandling) {
/*  389 */       p.pln("private InternalTypeMappingRegistry registry;");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeConstructor(IndentingWriter p, String className) throws IOException {
/*  395 */     p.plnI("public " + Names.stripQualifier(className) + "(javax.xml.namespace.QName type, java.lang.String encodingStyle) {");
/*      */ 
/*      */ 
/*      */     
/*  399 */     p.pln("this(type, encodingStyle, false);");
/*  400 */     p.pOln("}");
/*  401 */     p.pln();
/*  402 */     p.plnI("public " + Names.stripQualifier(className) + "(javax.xml.namespace.QName type, java.lang.String encodingStyle, boolean encodeType) {");
/*      */ 
/*      */ 
/*      */     
/*  406 */     p.pln("super(type, true, encodingStyle, encodeType);");
/*  407 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */   
/*      */   private void writeInitialize(IndentingWriter p, LiteralStructuredType type) throws IOException {
/*  412 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*      */     
/*  414 */     Iterator iterator = javaStructure.getMembers();
/*  415 */     Set<String> processedTypes = new HashSet();
/*      */     
/*  417 */     p.plnI("public void initialize(InternalTypeMappingRegistry registry) throws Exception {");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  422 */     for (Iterator<LiteralElementMember> iter = type.getElementMembers(); iter.hasNext(); ) {
/*  423 */       LiteralElementMember member = iter.next();
/*  424 */       JavaStructureMember javaMember = member.getJavaStructureMember();
/*  425 */       LiteralType literalType = member.getType();
/*  426 */       if (!processedTypes.contains(literalType.getName() + ";" + literalType.getJavaType().getRealName())) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  431 */         SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, (AbstractType)literalType);
/*  432 */         writer.initializeSerializer(p, this.env.getNames().getTypeQName(literalType.getName()), "registry");
/*      */ 
/*      */ 
/*      */         
/*  436 */         processedTypes.add(member.getType().getName() + ";" + literalType.getJavaType().getRealName());
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  443 */     if (this.enableIDREFTypeHandling) {
/*  444 */       p.pln("this.registry = registry;");
/*      */     }
/*      */     
/*  447 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeDoDeserializeMethod(IndentingWriter p, LiteralStructuredType type) throws IOException {
/*  454 */     if (((JavaStructureType)type.getJavaType()).isAbstract()) {
/*  455 */       p.plnI("public java.lang.Object doDeserialize(XMLReader reader,");
/*  456 */       p.pln("SOAPDeserializationContext context) throws java.lang.Exception {");
/*  457 */       p.p("throw new DeserializationException(\"soap.unsupportedType\", ");
/*      */       
/*  459 */       GeneratorUtil.writeNewQName(p, type.getName());
/*  460 */       p.pln(".toString());");
/*  461 */       p.pOln("}");
/*  462 */     } else if (SOAPObjectSerializerGenerator.deserializeToDetail((AbstractType)type)) {
/*      */ 
/*      */       
/*  465 */       SOAPObjectSerializerGenerator.writeDetailDoDeserializeMethod(p, (AbstractType)type);
/*      */     }
/*      */     else {
/*      */       
/*  469 */       writeStandardDoDeserializeMethod(p, type);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeStandardDoDeserializeMethod(IndentingWriter p, LiteralStructuredType type) throws IOException {
/*  477 */     JavaStructureType javaType = (JavaStructureType)type.getJavaType();
/*  478 */     JavaStructureMember[] constructorArgs = SOAPObjectBuilderGenerator.getConstructorArgs(javaType);
/*      */     
/*  480 */     boolean usesConstructor = (constructorArgs.length > 0);
/*      */     
/*  482 */     p.plnI("public java.lang.Object doDeserialize(XMLReader reader,");
/*  483 */     p.pln("SOAPDeserializationContext context) throws java.lang.Exception {");
/*  484 */     if (usesConstructor) {
/*  485 */       p.pln(javaType.getName() + " instance = null;");
/*  486 */       Iterator<JavaStructureMember> members = javaType.getMembers();
/*      */ 
/*      */ 
/*      */       
/*  490 */       while (members.hasNext()) {
/*  491 */         JavaStructureMember member = members.next();
/*  492 */         String javaName = member.getType().getName();
/*  493 */         if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/*  494 */           javaName = SimpleToBoxedUtil.getBoxedClassName(javaName);
/*  495 */           String initVal = member.getType().getInitString();
/*  496 */           p.pln("Object " + member.getName() + "Temp = new " + javaName + "(" + initVal + ");");
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  505 */         p.pln("Object " + member.getName() + "Temp = null;");
/*      */       } 
/*      */     } else {
/*      */       
/*  509 */       p.pln(javaType.getName() + " instance = new " + javaType.getName() + "();");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  515 */     p.pln("java.lang.Object member=null;");
/*  516 */     p.pln("javax.xml.namespace.QName elementName;");
/*  517 */     p.pln("java.util.List values;");
/*  518 */     p.pln("java.lang.Object value;");
/*  519 */     p.pln();
/*  520 */     if (type.getAttributeMembersCount() > 0) {
/*  521 */       writeDeserializeAttributes(p, type, "reader", usesConstructor);
/*  522 */       p.pln();
/*      */     } 
/*      */     
/*  525 */     if (type.getContentMember() != null) {
/*  526 */       p.pln("reader.nextContent();");
/*  527 */       writeDeserializeContent(p, type, "reader", usesConstructor);
/*      */     } else {
/*  529 */       p.pln("reader.nextElementContent();");
/*  530 */       if (type.getElementMembersCount() > 0) {
/*  531 */         writeDeserializeElements(p, type, "reader", usesConstructor);
/*  532 */         p.pln();
/*      */       } 
/*      */     } 
/*      */     
/*  536 */     if (usesConstructor) {
/*      */       
/*  538 */       p.p("instance = new " + javaType.getName() + "(");
/*      */ 
/*      */       
/*  541 */       for (int i = 0; i < constructorArgs.length; i++) {
/*  542 */         String valueStr; if (i > 0)
/*  543 */           p.p(", "); 
/*  544 */         JavaStructureMember javaMember = constructorArgs[i];
/*  545 */         String javaName = javaMember.getType().getName();
/*  546 */         if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/*  547 */           String boxName = SimpleToBoxedUtil.getBoxedClassName(javaName);
/*      */           
/*  549 */           valueStr = SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")" + javaMember.getName() + "Temp", javaName);
/*      */         
/*      */         }
/*      */         else {
/*      */           
/*  554 */           valueStr = "(" + javaName + ")" + javaMember.getName() + "Temp";
/*      */         } 
/*      */         
/*  557 */         p.p(valueStr);
/*      */       } 
/*  559 */       p.pln(");");
/*      */     } 
/*      */     
/*  562 */     p.pln("XMLReaderUtil.verifyReaderState(reader, XMLReader.END);");
/*  563 */     p.pln("return (java.lang.Object)instance;");
/*  564 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeDeserializeAttributes(IndentingWriter p, LiteralStructuredType type, String reader, boolean usesConstructor) throws IOException {
/*  574 */     p.pln("Attributes attributes = reader.getAttributes();");
/*  575 */     p.pln("java.lang.String attribute = null;");
/*  576 */     Iterator<LiteralAttributeMember> iterator = type.getAttributeMembers();
/*  577 */     while (iterator.hasNext()) {
/*      */       String encoder;
/*  579 */       LiteralAttributeMember member = iterator.next();
/*      */       
/*  581 */       JavaStructureMember javaMember = member.getJavaStructureMember();
/*  582 */       String memberConstName = member.getName().getLocalPart().toUpperCase();
/*      */       
/*  584 */       String memberQName = this.env.getNames().getQNameName(member.getName());
/*  585 */       p.pln("attribute = attributes.getValue(" + memberQName + ");");
/*  586 */       p.plnI("if (attribute != null) {");
/*      */ 
/*      */       
/*  589 */       if (member.getType() instanceof com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType) {
/*  590 */         encoder = member.getType().getJavaType().getName() + "_Encoder";
/*      */       } else {
/*      */         
/*  593 */         encoder = LiteralSimpleSerializerWriter.getTypeEncoder((AbstractType)member.getType());
/*      */       } 
/*      */       
/*  596 */       if (usesConstructor) {
/*  597 */         p.pln(javaMember.getName() + "Temp = " + encoder + ".getInstance().stringToObject(attribute, reader);");
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */         
/*  603 */         p.pln("member = " + encoder + ".getInstance().stringToObject(attribute, reader);");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  608 */         if (member.getType().getName().equals(SchemaConstants.QNAME_TYPE_IDREF) && this.enableIDREFTypeHandling) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  613 */           p.pln("PostDeserializationAction action = (PostDeserializationAction)new " + this.env.getNames().getIDObjectResolverName(member.getName().getLocalPart()) + "((java.lang.String)member, instance);");
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  618 */           p.pln("context.addPostDeserializationAction(action);");
/*      */         } else {
/*  620 */           String valueStr, javaName = javaMember.getType().getName();
/*      */           
/*  622 */           if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/*  623 */             String boxName = SimpleToBoxedUtil.getBoxedClassName(javaName);
/*      */             
/*  625 */             valueStr = SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")member", javaName);
/*      */           
/*      */           }
/*      */           else {
/*      */             
/*  630 */             valueStr = "(" + javaName + ")member";
/*      */           } 
/*      */           
/*  633 */           if (javaMember.isPublic()) {
/*  634 */             p.pln("instance." + javaMember.getName() + " = " + valueStr + ";");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*  641 */           else if (javaMember.getDeclaringClass() != null) {
/*  642 */             p.pln("((" + javaMember.getDeclaringClass().replace('$', '.') + ")instance)." + javaMember.getWriteMethod() + "(" + valueStr + ");");
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  653 */             p.pln("instance." + javaMember.getWriteMethod() + "(" + valueStr + ");");
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  662 */           if (member.getType().getName().equals(SchemaConstants.QNAME_TYPE_ID) && this.enableIDTypeHandling)
/*      */           {
/*      */ 
/*      */ 
/*      */             
/*  667 */             p.pln("context.addXSDIdObjectSerializer((java.lang.String)member, instance);");
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  673 */       p.pOln("}");
/*  674 */       if (member.isRequired()) {
/*  675 */         p.plnI("else {");
/*  676 */         p.pln("throw new DeserializationException(\"literal.missingRequiredAttribute\", new Object[] {" + memberQName + "});");
/*      */ 
/*      */ 
/*      */         
/*  680 */         p.pOln("}");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeDeserializeContent(IndentingWriter p, LiteralStructuredType type, String reader, boolean usesConstructor) throws IOException {
/*      */     String encoder, valueStr;
/*  692 */     LiteralContentMember member = type.getContentMember();
/*  693 */     JavaStructureMember javaMember = member.getJavaStructureMember();
/*      */     
/*  695 */     if (member.getType() instanceof com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType) {
/*  696 */       encoder = member.getType().getJavaType().getName() + "_Encoder";
/*      */     } else {
/*  698 */       encoder = LiteralSimpleSerializerWriter.getTypeEncoder((AbstractType)member.getType());
/*      */     } 
/*      */     
/*  701 */     String javaName = javaMember.getType().getName();
/*      */     
/*  703 */     if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/*  704 */       String boxName = SimpleToBoxedUtil.getBoxedClassName(javaName);
/*  705 */       valueStr = SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")member", javaName);
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  710 */       valueStr = "(" + javaName + ")member";
/*      */     } 
/*      */     
/*  713 */     p.plnI("if (reader.getState() == XMLReader.CHARS) {");
/*  714 */     if (usesConstructor) {
/*  715 */       p.pln(javaMember.getName() + "Temp = " + encoder + ".getInstance().stringToObject(reader.getValue(), reader);");
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  721 */       p.pln("member = " + encoder + ".getInstance().stringToObject(reader.getValue(), reader);");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  727 */     p.pln("reader.nextContent();");
/*  728 */     p.pOln("}");
/*      */     
/*  730 */     p.plnI("else if (reader.getState() == XMLReader.END) {");
/*  731 */     p.pln("member = " + encoder + ".getInstance().stringToObject(\"\", reader);");
/*      */ 
/*      */ 
/*      */     
/*  735 */     p.pOln("}");
/*      */     
/*  737 */     p.plnI("else if (reader.getState() == XMLReader.START) {");
/*  738 */     p.pln("throw new DeserializationException(\"literal.simpleContentExpected\", new Object[] {reader.getName()});");
/*      */ 
/*      */     
/*  741 */     p.pOln("}");
/*      */     
/*  743 */     if (member.getType().getName().equals(SchemaConstants.QNAME_TYPE_IDREF) && this.enableIDREFTypeHandling) {
/*      */       
/*  745 */       p.pln("PostDeserializationAction action = (PostDeserializationAction)new " + this.env.getNames().getIDObjectResolverName(type.getName().getLocalPart()) + "((java.lang.String)member, instance);");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  750 */       p.pln("context.addPostDeserializationAction(action);");
/*      */     }
/*  752 */     else if (javaMember.isPublic()) {
/*  753 */       p.pln("instance." + javaMember.getName() + " = " + valueStr + ";");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  760 */     else if (javaMember.getDeclaringClass() != null) {
/*  761 */       p.pln("((" + javaMember.getDeclaringClass().replace('$', '.') + ")instance)." + javaMember.getWriteMethod() + "(" + valueStr + ");");
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/*  770 */       p.pln("instance." + javaMember.getWriteMethod() + "(" + valueStr + ");");
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
/*      */   private void writeDeserializeElements(IndentingWriter p, LiteralStructuredType type, String reader, boolean usesConstructor) throws IOException {
/*  788 */     if (type instanceof LiteralSequenceType) {
/*  789 */       Iterator<LiteralElementMember> iterator = type.getElementMembers();
/*  790 */       while (iterator.hasNext()) {
/*  791 */         LiteralElementMember elementMember = iterator.next();
/*      */         
/*  793 */         p.pln("elementName = " + reader + ".getName();");
/*  794 */         if (elementMember.isRepeated()) {
/*  795 */           writeArrayElementMemberDeserializer(p, type, elementMember, reader, false, usesConstructor);
/*      */ 
/*      */ 
/*      */           
/*      */           continue;
/*      */         } 
/*      */ 
/*      */         
/*  803 */         writeScalarElementMemberDeserializer(p, type, elementMember, reader, false, usesConstructor);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  814 */     else if (type.getElementMembersCount() > 0) {
/*  815 */       p.plnI("while (reader.getState() == XMLReader.START) {");
/*  816 */       p.pln("elementName = " + reader + ".getName();");
/*  817 */       Iterator<LiteralElementMember> iterator = type.getElementMembers();
/*  818 */       boolean gotOne = false;
/*  819 */       while (iterator.hasNext()) {
/*  820 */         if (gotOne)
/*      */         {
/*      */ 
/*      */           
/*  824 */           p.p("else ");
/*      */         }
/*  826 */         LiteralElementMember elementMember = iterator.next();
/*      */         
/*  828 */         if (elementMember.isRepeated()) {
/*  829 */           writeArrayElementMemberDeserializer(p, type, elementMember, reader, true, usesConstructor);
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */           
/*  837 */           writeScalarElementMemberDeserializer(p, type, elementMember, reader, true, usesConstructor);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  845 */         gotOne = true;
/*      */       } 
/*  847 */       p.plnI("else {");
/*  848 */       p.pln("throw new DeserializationException(\"literal.unexpectedElementName\", new Object[] { elementName, " + reader + ".getName()" + "});");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  853 */       p.pOln("}");
/*      */       
/*  855 */       p.pOln("}");
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
/*      */   private void writeScalarElementMemberDeserializer(IndentingWriter p, LiteralStructuredType type, LiteralElementMember member, String reader, boolean isAllType, boolean usesConstructor) throws IOException {
/*  870 */     JavaStructureMember javaMember = member.getJavaStructureMember();
/*  871 */     String memberQName = (member.getName() == null) ? null : this.env.getNames().getQNameName(member.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  876 */     if (!isAllType) {
/*  877 */       p.plnI("if (" + reader + ".getState() == XMLReader.START) {");
/*      */     }
/*      */     
/*  880 */     if (member.isWildcard()) {
/*  881 */       LiteralWildcardMember wildcard = (LiteralWildcardMember)member;
/*  882 */       if (wildcard.getExcludedNamespaceName() == null) {
/*  883 */         p.plnI("if (true) {");
/*      */       } else {
/*  885 */         p.plnI("if (!elementName.getNamespaceURI().equals(\"" + wildcard.getExcludedNamespaceName() + "\")) {");
/*      */       }
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  891 */       p.plnI("if (elementName.equals(" + memberQName + ")) {");
/*      */     } 
/*  893 */     SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, (AbstractType)member.getType());
/*      */     
/*  895 */     String serializer = writer.deserializerMemberName();
/*  896 */     String memberName = usesConstructor ? (javaMember.getName() + "Temp") : "member";
/*      */     
/*  898 */     p.pln(memberName + " = " + serializer + ".deserialize(" + memberQName + ", " + reader + ", context);");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  908 */     if (!member.isNillable()) {
/*  909 */       p.plnI("if (" + memberName + " == null) {");
/*  910 */       p.pln("throw new DeserializationException(\"literal.unexpectedNull\");");
/*      */       
/*  912 */       p.pOln("}");
/*      */     } 
/*      */     
/*  915 */     if (member.getType().getName().equals(SchemaConstants.QNAME_TYPE_IDREF) && this.enableIDREFTypeHandling) {
/*      */       
/*  917 */       if (member.isNillable()) {
/*  918 */         p.plnI("if (" + memberName + " != null) {");
/*      */       }
/*  920 */       p.pln("PostDeserializationAction action = (PostDeserializationAction)new " + this.env.getNames().getIDObjectResolverName(member.getName().getLocalPart()) + "((java.lang.String)member, instance);");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  925 */       p.pln("context.addPostDeserializationAction(action);");
/*  926 */       if (member.isNillable()) {
/*  927 */         p.pOln("}");
/*      */       }
/*      */     } else {
/*  930 */       String valueStr = null;
/*  931 */       String javaName = javaMember.getType().getName();
/*      */       
/*  933 */       if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/*  934 */         String boxName = SimpleToBoxedUtil.getBoxedClassName(javaName);
/*  935 */         valueStr = SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")" + memberName, javaName);
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  940 */         valueStr = "(" + javaName + ")" + memberName;
/*      */       } 
/*      */       
/*  943 */       if (!usesConstructor) {
/*  944 */         if (javaMember.isPublic()) {
/*  945 */           p.pln("instance." + javaMember.getName() + " = " + valueStr + ";");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         }
/*  952 */         else if (javaMember.getDeclaringClass() != null) {
/*  953 */           p.pln("((" + javaMember.getDeclaringClass().replace('$', '.') + ")instance)." + javaMember.getWriteMethod() + "(" + valueStr + ");");
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
/*  964 */           p.pln("instance." + javaMember.getWriteMethod() + "(" + valueStr + ");");
/*      */         } 
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  974 */       if (member.getType().getName().equals(SchemaConstants.QNAME_TYPE_ID) && this.enableIDTypeHandling)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/*  979 */         p.pln("context.addXSDIdObjectSerializer((java.lang.String)member, instance);");
/*      */       }
/*      */     } 
/*      */     
/*  983 */     p.pln(reader + ".nextElementContent();");
/*  984 */     p.pO("}");
/*  985 */     if (!isAllType && member.isRequired()) {
/*  986 */       p.plnI(" else {");
/*  987 */       p.pln("throw new DeserializationException(\"literal.unexpectedElementName\", new Object[] { " + memberQName + ", " + reader + ".getName()" + " });");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  994 */       p.pOln("}");
/*      */     } else {
/*  996 */       p.pln();
/*      */     } 
/*      */     
/*  999 */     if (!isAllType) {
/* 1000 */       p.pOln("}");
/* 1001 */       if (member.isRequired()) {
/* 1002 */         p.plnI("else {");
/* 1003 */         p.pln("throw new DeserializationException(\"literal.expectedElementName\", " + reader + ".getName().toString());");
/*      */ 
/*      */ 
/*      */         
/* 1007 */         p.pOln("}");
/*      */       } 
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
/*      */   private void writeArrayElementMemberDeserializer(IndentingWriter p, LiteralStructuredType type, LiteralElementMember member, String reader, boolean isAllType, boolean usesConstructor) throws IOException {
/* 1022 */     JavaStructureMember javaMember = member.getJavaStructureMember();
/* 1023 */     String memberQName = (member.getName() == null) ? null : this.env.getNames().getQNameName(member.getName());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1028 */     if (member.isWildcard()) {
/* 1029 */       LiteralWildcardMember wildcard = (LiteralWildcardMember)member;
/* 1030 */       if (wildcard.getExcludedNamespaceName() == null) {
/* 1031 */         p.plnI("if (" + reader + ".getState() == XMLReader.START) {");
/* 1032 */         p.pln("values = new ArrayList();");
/* 1033 */         p.plnI("for(;;) {");
/* 1034 */         p.plnI("if (" + reader + ".getState() == XMLReader.START) {");
/*      */       } else {
/* 1036 */         p.plnI("if ((" + reader + ".getState() == XMLReader.START) && (!elementName.getNamespaceURI().equals(\"" + wildcard.getExcludedNamespaceName() + "\"))) {");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1042 */         p.pln("values = new ArrayList();");
/* 1043 */         p.plnI("for(;;) {");
/* 1044 */         p.pln("elementName = " + reader + ".getName();");
/* 1045 */         p.plnI("if ((" + reader + ".getState() == XMLReader.START) && (!elementName.getNamespaceURI().equals(\"" + wildcard.getExcludedNamespaceName() + "\"))) {");
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */       
/* 1053 */       p.plnI("if ((" + reader + ".getState() == XMLReader.START) && (elementName.equals(" + memberQName + "))) {");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1059 */       p.pln("values = new ArrayList();");
/* 1060 */       p.plnI("for(;;) {");
/*      */       
/* 1062 */       p.pln("elementName = " + reader + ".getName();");
/* 1063 */       p.plnI("if ((" + reader + ".getState() == XMLReader.START) && (elementName.equals(" + memberQName + "))) {");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1071 */     SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, (AbstractType)member.getType());
/*      */     
/* 1073 */     String serializer = writer.deserializerMemberName();
/* 1074 */     p.pln("value = " + serializer + ".deserialize(" + memberQName + ", " + reader + ", context);");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1082 */     if (!member.isNillable()) {
/* 1083 */       p.plnI("if (value == null) {");
/* 1084 */       p.pln("throw new DeserializationException(\"literal.unexpectedNull\");");
/*      */       
/* 1086 */       p.pOln("}");
/*      */     } 
/*      */     
/* 1089 */     p.pln("values.add(value);");
/*      */     
/* 1091 */     String valueStr = null;
/* 1092 */     String javaName = member.getType().getJavaType().getName();
/* 1093 */     boolean javaTypeIsArray = javaName.endsWith("[]");
/* 1094 */     p.pln(reader + ".nextElementContent();");
/* 1095 */     p.pO("}");
/* 1096 */     p.plnI(" else {");
/* 1097 */     p.pln("break;");
/* 1098 */     p.pOln("}");
/*      */     
/* 1100 */     p.pOln("}");
/* 1101 */     String memberName = usesConstructor ? (javaMember.getName() + "Temp") : "member";
/*      */     
/* 1103 */     if (javaTypeIsArray) {
/* 1104 */       int idx = javaName.indexOf("[]");
/* 1105 */       p.pln(memberName + " = new " + javaName.substring(0, idx) + "[values.size()]" + javaName.substring(idx) + ";");
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 1113 */       p.pln(memberName + " = new " + javaName + "[values.size()];");
/*      */     } 
/*      */     
/* 1116 */     if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/* 1117 */       String boxName = SimpleToBoxedUtil.getBoxedClassName(javaName);
/* 1118 */       p.plnI("for (int i = 0; i < values.size(); ++i) {");
/* 1119 */       p.pln("((" + javaName + "[]) " + memberName + ")[i] = " + SimpleToBoxedUtil.getUnboxedExpressionOfType("(" + boxName + ")(values.get(i))", javaName) + ";");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1129 */       p.pOln("}");
/*      */     } else {
/* 1131 */       p.pln(memberName + " = values.toArray((Object[]) " + memberName + ");");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1138 */     if (!usesConstructor) {
/*      */       
/* 1140 */       String structArrayName = ((JavaStructureMember)((JavaStructureType)type.getJavaType()).getMembers().next()).getType().getName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1146 */       structArrayName = structArrayName.substring(0, structArrayName.length() - 2);
/*      */ 
/*      */       
/* 1149 */       if (member.getType() instanceof LiteralArrayWrapperType && !javaName.equals(structArrayName)) {
/*      */         
/* 1151 */         LiteralArrayWrapperType arrayType = (LiteralArrayWrapperType)member.getType();
/*      */         
/* 1153 */         p.pln("// LiteralArrayWrapper");
/* 1154 */         String javaTypeName = arrayType.getJavaArrayType().getName() + "[]";
/*      */         
/* 1156 */         int idx = javaTypeName.indexOf("[]") + 1;
/* 1157 */         String tmp = javaTypeName.substring(0, idx);
/* 1158 */         p.pln(javaTypeName + " tmpArray = new " + tmp + "values.size()" + javaTypeName.substring(idx) + ";");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1165 */         javaTypeName = arrayType.getJavaArrayType().getName();
/* 1166 */         idx = javaTypeName.indexOf("[]") + 1;
/* 1167 */         tmp = javaTypeName.substring(0, idx);
/* 1168 */         p.plnI("for (int i=0;i<tmpArray.length;i++) {");
/* 1169 */         p.pln(javaTypeName + " inner = ((" + javaName + ")((java.lang.Object[])member)[i]).toArray();");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1174 */         p.pln("tmpArray[i] = new " + tmp + "inner.length" + javaTypeName.substring(idx) + ";");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1180 */         p.plnI("for (int j=0; j<tmpArray[i].length;j++) {");
/* 1181 */         p.pln("tmpArray[i][j] = inner[j];");
/* 1182 */         p.pOln("}");
/* 1183 */         p.pOln("}");
/* 1184 */         valueStr = "tmpArray";
/*      */       } else {
/* 1186 */         valueStr = "(" + javaName + "[])" + memberName;
/*      */       } 
/* 1188 */       if (javaMember.isPublic()) {
/* 1189 */         p.pln("instance." + javaMember.getName() + " = " + valueStr + ";");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/* 1196 */       else if (javaMember.getDeclaringClass() != null) {
/* 1197 */         p.pln("((" + javaMember.getDeclaringClass().replace('$', '.') + ")instance)." + javaMember.getWriteMethod() + "(" + valueStr + ");");
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/* 1206 */         p.pln("instance." + javaMember.getWriteMethod() + "(" + valueStr + ");");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1216 */     p.pOln("}");
/*      */     
/* 1218 */     if (member.isRequired()) {
/*      */       
/* 1220 */       p.plnI("else if(!(reader.getState() == XMLReader.END)) {");
/*      */       
/* 1222 */       p.pln("throw new DeserializationException(\"literal.expectedElementName\", " + reader + ".getName().toString());");
/*      */ 
/*      */ 
/*      */       
/* 1226 */       p.pOln("}");
/*      */     } else {
/* 1228 */       p.plnI("else {");
/* 1229 */       if (!usesConstructor) {
/* 1230 */         if (convertArrayWrapper(member, type)) {
/* 1231 */           LiteralArrayWrapperType arrayType = (LiteralArrayWrapperType)member.getType();
/*      */           
/* 1233 */           String javaTypeName = arrayType.getJavaArrayType().getName() + "[]";
/*      */           
/* 1235 */           int idx = javaTypeName.indexOf("[]") + 1;
/* 1236 */           String tmp = javaTypeName.substring(0, idx);
/* 1237 */           javaName = tmp + "0" + javaTypeName.substring(idx);
/*      */         }
/* 1239 */         else if (javaName.equals("byte[]") || javaName.equals("java.lang.Byte[]")) {
/*      */           
/* 1241 */           javaName = javaName.substring(0, javaName.length() - 1) + "0][0]";
/*      */         }
/*      */         else {
/*      */           
/* 1245 */           javaName = javaName + "[0]";
/*      */         } 
/*      */         
/* 1248 */         if (javaMember.isPublic()) {
/* 1249 */           p.pln("instance." + javaMember.getName() + " = new " + javaName + ";");
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */           
/* 1256 */           p.pln("instance." + javaMember.getWriteMethod() + "(new " + javaName + ");");
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1264 */       p.pOln("}");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeDoSerializeAttributesMethod(IndentingWriter p, LiteralStructuredType type) throws IOException {
/* 1273 */     p.plnI("public void doSerializeAttributes(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {");
/*      */     
/* 1275 */     p.pln(type.getJavaType().getName() + " instance = (" + type.getJavaType().getName() + ")obj;");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1280 */     p.pln();
/* 1281 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1288 */     Iterator<LiteralAttributeMember> iterator = type.getAttributeMembers();
/* 1289 */     while (iterator.hasNext()) {
/*      */       String encoder;
/* 1291 */       LiteralAttributeMember member = iterator.next();
/*      */       
/* 1293 */       JavaStructureMember javaMember = member.getJavaStructureMember();
/* 1294 */       String memberConstName = member.getName().getLocalPart().toUpperCase();
/* 1295 */       String memberQName = this.env.getNames().getQNameName(member.getName());
/* 1296 */       SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, (AbstractType)member.getType());
/*      */       
/* 1298 */       String serializer = writer.serializerMemberName();
/* 1299 */       String valueStr = null;
/* 1300 */       String javaName = javaMember.getType().getName();
/*      */ 
/*      */ 
/*      */       
/* 1304 */       if (member.getType() instanceof com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType) {
/* 1305 */         encoder = member.getType().getJavaType().getName() + "_Encoder";
/*      */       } else {
/*      */         
/* 1308 */         encoder = LiteralSimpleSerializerWriter.getTypeEncoder((AbstractType)member.getType());
/*      */       } 
/*      */       
/* 1311 */       if (javaMember.isPublic()) {
/* 1312 */         valueStr = "instance." + javaMember.getName();
/*      */       } else {
/* 1314 */         String methName = javaMember.getReadMethod();
/* 1315 */         valueStr = "instance." + methName + "()";
/*      */       } 
/*      */ 
/*      */       
/* 1319 */       if (member.getType().getName().equals(SchemaConstants.QNAME_TYPE_IDREF) && this.enableIDREFTypeHandling) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1325 */         p.plnI("if (" + valueStr + " != null) {");
/* 1326 */         p.pln("java.lang.Object idObj = " + valueStr + ";");
/* 1327 */         p.pln("CombinedSerializer idSerializer = (CombinedSerializer)registry.getSerializer(\"\", idObj.getClass());");
/*      */ 
/*      */         
/* 1330 */         p.plnI("if((idSerializer !=null) && (idSerializer instanceof IDREFSerializerHelper)) {");
/*      */ 
/*      */         
/* 1333 */         p.pln("IDREFSerializerHelper idrefSerializer = (IDREFSerializerHelper)idSerializer;");
/*      */         
/* 1335 */         p.pln("writer.writeAttribute(" + memberQName + ", " + encoder + ".getInstance().objectToString(" + "idrefSerializer.getID(idObj)" + ", writer));");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1343 */         p.pOln("}");
/*      */         
/* 1345 */         p.plnI("else {");
/* 1346 */         p.pln("throw new DeserializationException(\"literal.notIdentifiableObject\", new Object[] {idObj.getClass()});");
/*      */         
/* 1348 */         p.pOln("}");
/* 1349 */         p.pOln("}");
/* 1350 */         if (member.isRequired()) {
/* 1351 */           p.plnI("else {");
/* 1352 */           p.pln("throw new DeserializationException(\"literal.requiredAttributeConstraint\", new Object[] {" + memberQName + "});");
/*      */ 
/*      */ 
/*      */           
/* 1356 */           p.pOln("}");
/*      */         } 
/*      */         
/*      */         continue;
/*      */       } 
/*      */       
/* 1362 */       if (!member.getType().isSOAPType() && isMustUnderstandHeader(member)) {
/*      */ 
/*      */         
/* 1365 */         if (!SimpleToBoxedUtil.isPrimitive(javaName)) {
/* 1366 */           valueStr = valueStr + ".booleanValue()";
/*      */         }
/* 1368 */         p.plnI("if (" + valueStr + ")");
/* 1369 */         p.pln("writer.writeAttribute(" + memberQName + ", \"1\");");
/* 1370 */         p.pOlnI("else");
/* 1371 */         p.pln("writer.writeAttribute(" + memberQName + ", \"0\");");
/* 1372 */         p.pO(); continue;
/*      */       } 
/* 1374 */       if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/* 1375 */         valueStr = SimpleToBoxedUtil.getBoxedExpressionOfType(valueStr, javaName);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1381 */       p.plnI("if (" + valueStr + " != null) {");
/* 1382 */       p.pln("writer.writeAttribute(" + memberQName + ", " + encoder + ".getInstance().objectToString(" + valueStr + ", writer));");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1390 */       p.pOln("}");
/* 1391 */       if (member.isRequired()) {
/* 1392 */         p.plnI("else {");
/* 1393 */         p.pln("throw new DeserializationException(\"literal.requiredAttributeConstraint\", new Object[] {" + memberQName + "});");
/*      */ 
/*      */ 
/*      */         
/* 1397 */         p.pOln("}");
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1403 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isMustUnderstandHeader(LiteralAttributeMember member) {
/* 1409 */     QName typeName = member.getType().getName();
/* 1410 */     if (member.getName().equals(SOAPConstants.QNAME_MUSTUNDERSTAND) && (typeName.equals(SOAPConstants.QNAME_TYPE_BOOLEAN) || typeName.equals(SchemaConstants.QNAME_TYPE_BOOLEAN)))
/*      */     {
/*      */       
/* 1413 */       return true;
/*      */     }
/* 1415 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean convertArrayWrapper(LiteralElementMember member, LiteralStructuredType type) {
/* 1421 */     String structArrayName = ((JavaStructureMember)((JavaStructureType)type.getJavaType()).getMembers().next()).getType().getName();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1427 */     String javaName = member.getType().getJavaType().getName() + "[]";
/* 1428 */     return (member.getType() instanceof LiteralArrayWrapperType && !javaName.equals(structArrayName));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeDoSerializeMethod(IndentingWriter p, LiteralStructuredType type) throws IOException {
/* 1439 */     p.plnI("public void doSerialize(java.lang.Object obj, XMLWriter writer, SOAPSerializationContext context) throws java.lang.Exception {");
/*      */     
/* 1441 */     p.pln(type.getJavaType().getName() + " instance = (" + type.getJavaType().getName() + ")obj;");
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1446 */     p.pln();
/* 1447 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1454 */     if (type.getContentMember() != null) {
/* 1455 */       String encoder; Iterator iterator = type.getElementMembers();
/* 1456 */       if (iterator.hasNext())
/*      */       {
/*      */         
/* 1459 */         fail("generator.unsupported.type.encountered", type.getName().getLocalPart(), type.getName().getNamespaceURI());
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1464 */       LiteralContentMember member = type.getContentMember();
/* 1465 */       JavaStructureMember javaMember = member.getJavaStructureMember();
/* 1466 */       SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, (AbstractType)member.getType());
/*      */       
/* 1468 */       String serializer = writer.serializerMemberName();
/* 1469 */       String valueStr = null;
/* 1470 */       String javaName = javaMember.getType().getName();
/*      */       
/* 1472 */       if (javaMember.isPublic()) {
/* 1473 */         valueStr = "instance." + javaMember.getName();
/*      */       } else {
/* 1475 */         String methName = javaMember.getReadMethod();
/* 1476 */         valueStr = "instance." + methName + "()";
/*      */       } 
/* 1478 */       if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/* 1479 */         valueStr = SimpleToBoxedUtil.getBoxedExpressionOfType(valueStr, javaName);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1487 */       if (member.getType() instanceof com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType) {
/* 1488 */         encoder = member.getType().getJavaType().getName() + "_Encoder";
/*      */       } else {
/* 1490 */         encoder = LiteralSimpleSerializerWriter.getTypeEncoder((AbstractType)member.getType());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1495 */       if (member.getType().getName().equals(SchemaConstants.QNAME_TYPE_IDREF) && this.enableIDREFTypeHandling) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1501 */         p.plnI("if (" + valueStr + " == null) {");
/* 1502 */         p.pln("writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, \"1\");");
/*      */ 
/*      */         
/* 1505 */         p.plnI("}else {");
/* 1506 */         p.pln("java.lang.Object idObj = " + valueStr + ";");
/* 1507 */         p.pln("CombinedSerializer idSerializer = (CombinedSerializer)registry.getSerializer(\"\", idObj.getClass());");
/*      */         
/* 1509 */         p.plnI("if((idSerializer !=null) && (idSerializer instanceof IDREFSerializerHelper)) {");
/*      */         
/* 1511 */         p.pln("IDREFSerializerHelper idrefSerializer = (IDREFSerializerHelper)idSerializer;");
/*      */         
/* 1513 */         p.pln("writer.writeChars(" + encoder + ".getInstance().objectToString(idrefSerializer.getID(idObj), writer));");
/*      */ 
/*      */ 
/*      */         
/* 1517 */         p.pOln("}");
/*      */         
/* 1519 */         p.plnI("else {");
/* 1520 */         p.pln("throw new DeserializationException(\"literal.notIdentifiableObject\", new Object[] {idObj.getClass()});");
/*      */         
/* 1522 */         p.pOln("}");
/* 1523 */         p.pOln("}");
/*      */       } else {
/*      */         
/* 1526 */         p.plnI("if (" + valueStr + " == null) {");
/* 1527 */       }  p.pln("writer.writeAttributeUnquoted(XSDConstants.QNAME_XSI_NIL, \"1\");");
/*      */       
/* 1529 */       p.pOln("}");
/* 1530 */       p.plnI("else {");
/* 1531 */       p.pln("writer.writeChars(" + encoder + ".getInstance().objectToString(" + valueStr + ", writer));");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1537 */       p.pOln("}");
/*      */     } else {
/* 1539 */       Iterator<LiteralElementMember> iterator = type.getElementMembers();
/* 1540 */       while (iterator.hasNext()) {
/*      */         
/* 1542 */         LiteralElementMember member = iterator.next();
/*      */         
/* 1544 */         JavaStructureMember javaMember = member.getJavaStructureMember();
/*      */         
/* 1546 */         String memberQName = (member.getName() == null) ? null : this.env.getNames().getQNameName(member.getName());
/*      */ 
/*      */ 
/*      */         
/* 1550 */         SerializerWriter writer = this.writerFactory.createWriter(this.servicePackage, (AbstractType)member.getType());
/*      */ 
/*      */ 
/*      */         
/* 1554 */         String serializer = writer.serializerMemberName();
/* 1555 */         String valueStr = null;
/* 1556 */         String javaName = javaMember.getType().getName();
/*      */         
/* 1558 */         if (javaMember.isPublic()) {
/* 1559 */           valueStr = "instance." + javaMember.getName();
/*      */         } else {
/* 1561 */           String methName = javaMember.getReadMethod();
/* 1562 */           valueStr = "instance." + methName + "()";
/*      */         } 
/* 1564 */         if (SimpleToBoxedUtil.isPrimitive(javaName)) {
/* 1565 */           valueStr = SimpleToBoxedUtil.getBoxedExpressionOfType(valueStr, javaName);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1571 */         if (!member.isNillable() && member.isRequired() && !member.isRepeated()) {
/*      */ 
/*      */           
/* 1574 */           p.plnI("if (" + valueStr + " == null) {");
/* 1575 */           p.pln("throw new SerializationException(\"literal.unexpectedNull\");");
/*      */           
/* 1577 */           p.pOln("}");
/*      */         } 
/*      */         
/* 1580 */         if (member.isRepeated()) {
/* 1581 */           String javaElementName = member.getType().getJavaType().getName();
/*      */           
/* 1583 */           p.plnI("if (" + valueStr + " != null) {");
/* 1584 */           p.plnI("for (int i = 0; i < " + valueStr + ".length; ++i) {");
/*      */           
/* 1586 */           if (SimpleToBoxedUtil.isPrimitive(javaElementName)) {
/* 1587 */             p.pln(serializer + ".serialize(" + SimpleToBoxedUtil.getBoxedExpressionOfType(valueStr + "[i]", javaElementName) + ", " + memberQName + ", null, writer, context);");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/* 1598 */           else if (convertArrayWrapper(member, type)) {
/* 1599 */             p.pln(serializer + ".serialize(new " + javaElementName + "(" + valueStr + "[i]), " + memberQName + ", null, writer, context);");
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */           else {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1609 */             p.pln(serializer + ".serialize(" + valueStr + "[i], " + memberQName + ", null, writer, context);");
/*      */           } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1618 */           p.pOln("}");
/* 1619 */           p.pOln("}"); continue;
/*      */         } 
/* 1621 */         boolean skipIfNull = (!member.isRequired() && !member.isNillable() && !SimpleToBoxedUtil.isPrimitive(javaName));
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1626 */         if (skipIfNull) {
/* 1627 */           p.plnI("if (" + valueStr + " != null) {");
/*      */         }
/*      */         
/* 1630 */         if (member.getType().getName().equals(SchemaConstants.QNAME_TYPE_IDREF) && this.enableIDREFTypeHandling) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1636 */           p.plnI("if (" + valueStr + " == null) {");
/* 1637 */           p.pln(serializer + ".serialize(null, " + memberQName + ",null, writer, context);");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1643 */           p.plnI("}else {");
/* 1644 */           p.pln("java.lang.Object idObj = " + valueStr + ";");
/* 1645 */           p.pln("CombinedSerializer idSerializer = (CombinedSerializer)registry.getSerializer(\"\", idObj.getClass());");
/*      */           
/* 1647 */           p.plnI("if((idSerializer !=null) && (idSerializer instanceof IDREFSerializerHelper)) {");
/*      */           
/* 1649 */           p.pln("IDREFSerializerHelper idrefSerializer = (IDREFSerializerHelper)idSerializer;");
/*      */           
/* 1651 */           p.pln(serializer + ".serialize(idrefSerializer.getID(idObj), " + memberQName + ",null, writer, context);");
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1656 */           p.pOln("}");
/*      */           
/* 1658 */           p.plnI("else {");
/* 1659 */           p.pln("throw new DeserializationException(\"literal.notIdentifiableObject\", new java.lang.Object[] {idObj.getClass()});");
/*      */           
/* 1661 */           p.pOln("}");
/* 1662 */           p.pOln("}");
/*      */         } else {
/* 1664 */           p.pln(serializer + ".serialize(" + valueStr + ", " + memberQName + ", null, writer, context);");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1672 */         if (skipIfNull) {
/* 1673 */           p.pOln("}");
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1678 */     p.pOln("}");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeGetIDMethod(IndentingWriter p, LiteralStructuredType type) throws IOException {
/* 1689 */     if (!this.enableIDTypeHandling)
/*      */       return; 
/* 1691 */     boolean done = false;
/* 1692 */     String idMember = null;
/*      */     
/* 1694 */     Iterator<LiteralAttributeMember> iterator = type.getAttributeMembers();
/* 1695 */     while (iterator.hasNext() && !done) {
/*      */       
/* 1697 */       LiteralAttributeMember member = iterator.next();
/*      */ 
/*      */       
/* 1700 */       if (member.getType().getName().equals(SchemaConstants.QNAME_TYPE_ID)) {
/*      */ 
/*      */ 
/*      */         
/* 1704 */         JavaStructureMember javaMember = member.getJavaStructureMember();
/*      */         
/* 1706 */         if (javaMember.isPublic()) {
/* 1707 */           idMember = javaMember.getName();
/*      */         } else {
/* 1709 */           idMember = javaMember.getReadMethod() + "()";
/*      */         } 
/* 1711 */         done = true;
/*      */       } 
/*      */     } 
/*      */     
/* 1715 */     Iterator<LiteralElementMember> elements = type.getElementMembers();
/* 1716 */     while (elements.hasNext() && !done) {
/* 1717 */       LiteralElementMember member = elements.next();
/*      */       
/* 1719 */       if (member.getType().getName().equals(SchemaConstants.QNAME_TYPE_ID)) {
/*      */ 
/*      */ 
/*      */         
/* 1723 */         JavaStructureMember javaMember = member.getJavaStructureMember();
/*      */         
/* 1725 */         if (javaMember.isPublic()) {
/* 1726 */           idMember = javaMember.getName();
/*      */         } else {
/* 1728 */           idMember = javaMember.getReadMethod() + "()";
/*      */         } 
/* 1730 */         done = true;
/*      */       } 
/*      */     } 
/*      */     
/* 1734 */     if (done) {
/* 1735 */       JavaStructureType javaType = (JavaStructureType)type.getJavaType();
/* 1736 */       p.plnI(" public java.lang.String getID(java.lang.Object obj) {");
/* 1737 */       p.pln("return ((" + javaType.getName() + ")obj)." + idMember + ";");
/* 1738 */       p.pOln("}");
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
/*      */   private void writeIDObjectResolver(IndentingWriter p, LiteralStructuredType type) throws IOException {
/* 1751 */     if (!this.enableIDREFTypeHandling)
/*      */       return; 
/* 1753 */     LiteralContentMember content = type.getContentMember();
/* 1754 */     if (content != null && content.getType().getName().equals(SchemaConstants.QNAME_TYPE_IDREF)) {
/*      */ 
/*      */       
/* 1757 */       String className = this.env.getNames().getIDObjectResolverName(type.getName().getLocalPart());
/*      */ 
/*      */       
/* 1760 */       p.plnI("private static class " + Names.stripQualifier(className) + " implements PostDeserializationAction {");
/*      */ 
/*      */ 
/*      */       
/* 1764 */       p.pln("private final java.lang.String value;");
/* 1765 */       p.pln("private final " + type.getJavaType().getName() + " " + this.env.getNames().validJavaMemberName(type.getName().getLocalPart()) + ";");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1773 */       p.plnI(className + "(java.lang.String value, " + type.getJavaType().getName() + " idObj) {");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1778 */       p.pln("this.value = value;");
/* 1779 */       p.pln("this." + this.env.getNames().validJavaMemberName(type.getName().getLocalPart()) + " = idObj;");
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1784 */       p.pOln("}");
/*      */ 
/*      */       
/* 1787 */       p.plnI("public void run(SOAPDeserializationContext deserContext) {");
/*      */       
/* 1789 */       String idObjInstance = this.env.getNames().validJavaMemberName(type.getName().getLocalPart());
/*      */ 
/*      */       
/* 1792 */       JavaStructureMember javaMember = content.getJavaStructureMember();
/* 1793 */       String valueStr = new String("(java.lang.Object) deserContext.getXSDIdObjectSerializer(value)");
/*      */       
/* 1795 */       if (javaMember.isPublic()) {
/* 1796 */         p.pln(idObjInstance + "." + javaMember.getName() + " = " + valueStr + ";");
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */         
/* 1804 */         p.pln(idObjInstance + "." + javaMember.getWriteMethod() + "(" + valueStr + ");");
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1812 */       p.pOln("}");
/* 1813 */       p.pOln("}");
/*      */     } 
/*      */     
/* 1816 */     Iterator<LiteralElementMember> elements = type.getElementMembers();
/* 1817 */     while (elements.hasNext()) {
/* 1818 */       LiteralElementMember element = elements.next();
/*      */       
/* 1820 */       if (element.getType().getName().equals(SchemaConstants.QNAME_TYPE_IDREF)) {
/*      */ 
/*      */ 
/*      */         
/* 1824 */         String className = this.env.getNames().getIDObjectResolverName(element.getName().getLocalPart());
/*      */ 
/*      */         
/* 1827 */         p.plnI("private static class " + Names.stripQualifier(className) + " implements PostDeserializationAction {");
/*      */ 
/*      */ 
/*      */         
/* 1831 */         p.pln("private final java.lang.String value;");
/* 1832 */         p.pln("private final " + type.getJavaType().getName() + " " + this.env.getNames().validJavaMemberName(type.getName().getLocalPart()) + ";");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1840 */         p.plnI(className + "(String value, " + type.getJavaType().getName() + " idObj) {");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1845 */         p.pln("this.value = value;");
/* 1846 */         p.pln("this." + this.env.getNames().validJavaMemberName(type.getName().getLocalPart()) + " = idObj;");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1851 */         p.pOln("}");
/*      */ 
/*      */         
/* 1854 */         p.plnI("public void run(SOAPDeserializationContext deserContext) {");
/*      */         
/* 1856 */         String idObjInstance = this.env.getNames().validJavaMemberName(type.getName().getLocalPart());
/*      */ 
/*      */         
/* 1859 */         JavaStructureMember javaMember = element.getJavaStructureMember();
/*      */         
/* 1861 */         String valueStr = new String("(java.lang.Object) deserContext.getXSDIdObjectSerializer(value)");
/*      */         
/* 1863 */         if (javaMember.isPublic()) {
/* 1864 */           p.pln(idObjInstance + "." + javaMember.getName() + " = " + valueStr + ";");
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */           
/* 1872 */           p.pln(idObjInstance + "." + javaMember.getWriteMethod() + "(" + valueStr + ");");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1880 */         p.pOln("}");
/* 1881 */         p.pOln("}");
/*      */       } 
/*      */     } 
/*      */     
/* 1885 */     Iterator<LiteralAttributeMember> attributes = type.getAttributeMembers();
/* 1886 */     while (attributes.hasNext()) {
/* 1887 */       LiteralAttributeMember member = attributes.next();
/*      */       
/* 1889 */       if (member.getType().getName().equals(SchemaConstants.QNAME_TYPE_IDREF)) {
/*      */ 
/*      */ 
/*      */         
/* 1893 */         String className = this.env.getNames().getIDObjectResolverName(member.getName().getLocalPart());
/*      */ 
/*      */         
/* 1896 */         p.plnI("private static class " + Names.stripQualifier(className) + " implements PostDeserializationAction {");
/*      */ 
/*      */ 
/*      */         
/* 1900 */         p.pln("private final java.lang.String value;");
/* 1901 */         p.pln("private final " + type.getJavaType().getName() + " " + this.env.getNames().validJavaMemberName(type.getName().getLocalPart()) + ";");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1909 */         p.plnI(className + "(java.lang.String value, " + type.getJavaType().getName() + " idObj) {");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1914 */         p.pln("this.value = value;");
/* 1915 */         p.pln("this." + this.env.getNames().validJavaMemberName(type.getName().getLocalPart()) + " = idObj;");
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1920 */         p.pOln("}");
/*      */ 
/*      */         
/* 1923 */         p.plnI("public void run(SOAPDeserializationContext deserContext) {");
/*      */         
/* 1925 */         String idObjInstance = this.env.getNames().validJavaMemberName(type.getName().getLocalPart());
/*      */ 
/*      */         
/* 1928 */         JavaStructureMember javaMember = member.getJavaStructureMember();
/*      */         
/* 1930 */         String valueStr = new String("(java.lang.Object) deserContext.getXSDIdObjectSerializer(value)");
/*      */         
/* 1932 */         if (javaMember.isPublic()) {
/* 1933 */           p.pln(idObjInstance + "." + javaMember.getName() + " = " + valueStr + ";");
/*      */ 
/*      */         
/*      */         }
/*      */         else {
/*      */ 
/*      */ 
/*      */           
/* 1941 */           p.pln(idObjInstance + "." + javaMember.getWriteMethod() + "(" + valueStr + ");");
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1949 */         p.pOln("}");
/* 1950 */         p.pOln("}");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setIDAndIDREFFlags(LiteralStructuredType type) {
/* 1960 */     Iterator<LiteralAttributeMember> attributes = type.getAttributeMembers();
/*      */ 
/*      */     
/* 1963 */     this.enableIDTypeHandling = false;
/* 1964 */     this.enableIDREFTypeHandling = false;
/* 1965 */     while (attributes.hasNext()) {
/* 1966 */       LiteralAttributeMember attribute = attributes.next();
/*      */       
/* 1968 */       LiteralType literalType = attribute.getType();
/* 1969 */       if (literalType.getName().equals(SchemaConstants.QNAME_TYPE_ID) && literalType instanceof LiteralIDType)
/*      */       {
/* 1971 */         if (((LiteralIDType)literalType).getResolveIDREF())
/* 1972 */           this.enableIDTypeHandling = true; 
/*      */       }
/* 1974 */       if (literalType.getName().equals(SchemaConstants.QNAME_TYPE_IDREF) && literalType instanceof LiteralIDType)
/*      */       {
/* 1976 */         if (((LiteralIDType)literalType).getResolveIDREF()) {
/* 1977 */           this.enableIDREFTypeHandling = true;
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/* 1982 */     LiteralContentMember content = type.getContentMember();
/* 1983 */     if (content != null) {
/* 1984 */       LiteralType literalType = content.getType();
/* 1985 */       if (literalType.getName().equals(SchemaConstants.QNAME_TYPE_IDREF) && literalType instanceof LiteralIDType)
/*      */       {
/* 1987 */         if (((LiteralIDType)literalType).getResolveIDREF()) {
/* 1988 */           this.enableIDREFTypeHandling = true;
/*      */         }
/*      */       }
/*      */     } 
/*      */     
/* 1993 */     Iterator<LiteralElementMember> elements = type.getElementMembers();
/*      */     
/* 1995 */     while (elements.hasNext()) {
/* 1996 */       LiteralElementMember element = elements.next();
/* 1997 */       LiteralType literalType = element.getType();
/*      */       
/* 1999 */       if (literalType.getName().equals(SchemaConstants.QNAME_TYPE_IDREF) && literalType instanceof LiteralIDType)
/*      */       {
/*      */         
/* 2002 */         if (((LiteralIDType)literalType).getResolveIDREF()) {
/* 2003 */           this.enableIDREFTypeHandling = true;
/*      */         }
/*      */       }
/* 2006 */       if (literalType.getName().equals(SchemaConstants.QNAME_TYPE_ID) && literalType instanceof LiteralIDType)
/*      */       {
/* 2008 */         if (((LiteralIDType)literalType).getResolveIDREF())
/* 2009 */           this.enableIDTypeHandling = true; 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean enableIDTypeHandling = false;
/*      */   private boolean enableIDREFTypeHandling = false;
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\LiteralObjectSerializerGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */