/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.generator.writer.SimpleTypeSerializerWriter;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Parameter;
/*     */ import com.sun.xml.rpc.processor.model.Request;
/*     */ import com.sun.xml.rpc.processor.model.Response;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralListType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPAnyType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPCustomType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPEnumerationType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPListType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPSimpleType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnumerationEncoderGenerator
/*     */   extends GeneratorBase
/*     */ {
/*     */   private Set types;
/*     */   
/*     */   public EnumerationEncoderGenerator() {}
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties) {
/*  84 */     return new EnumerationEncoderGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*  92 */     return new EnumerationEncoderGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumerationEncoderGenerator(Model model, Configuration config, Properties properties) {
/*  99 */     super(model, config, properties);
/*     */   }
/*     */   
/*     */   protected void preVisitModel(Model model) throws Exception {
/* 103 */     this.types = new HashSet();
/*     */   }
/*     */   
/*     */   protected void postVisitModel(Model model) throws Exception {
/* 107 */     this.types = null;
/*     */   }
/*     */   
/*     */   protected void visitParameter(Parameter param) throws Exception {
/* 111 */     AbstractType type = param.getType();
/* 112 */     if (type.isSOAPType()) {
/* 113 */       ((SOAPType)type).accept(this);
/*     */     } else {
/* 115 */       ((LiteralType)type).accept(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void preVisitResponse(Response response) throws Exception {
/* 120 */     Iterator<Parameter> iter = response.getParameters();
/*     */     
/* 122 */     while (iter.hasNext()) {
/* 123 */       ((Parameter)iter.next()).accept(this);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void preVisitRequest(Request request) throws Exception {
/* 128 */     Iterator<Parameter> iter = request.getParameters();
/*     */     
/* 130 */     while (iter.hasNext()) {
/* 131 */       ((Parameter)iter.next()).accept(this);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void visitFault(Fault fault) throws Exception {
/* 136 */     if (fault.getBlock().getType().isSOAPType()) {
/* 137 */       ((SOAPType)fault.getBlock().getType()).accept(this);
/*     */     }
/* 139 */     JavaException exception = fault.getJavaException();
/*     */     
/* 141 */     Iterator<JavaStructureMember> members = exception.getMembers();
/* 142 */     AbstractType aType = (AbstractType)exception.getOwner();
/* 143 */     if (aType.isSOAPType()) {
/*     */       
/* 145 */       while (members.hasNext()) {
/* 146 */         SOAPType type = ((SOAPStructureMember)((JavaStructureMember)members.next()).getOwner()).getType();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 151 */         type.accept(this);
/*     */       } 
/*     */     } else {
/* 154 */       LiteralType type = null;
/*     */       
/* 156 */       while (members.hasNext()) {
/* 157 */         JavaStructureMember javaMember = members.next();
/* 158 */         if (javaMember.getOwner() instanceof LiteralElementMember) {
/* 159 */           type = ((LiteralElementMember)javaMember.getOwner()).getType();
/*     */         
/*     */         }
/* 162 */         else if (javaMember.getOwner() instanceof LiteralAttributeMember) {
/*     */           
/* 164 */           type = ((LiteralAttributeMember)javaMember.getOwner()).getType();
/*     */         } 
/*     */         
/* 167 */         type.accept(this);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(SOAPCustomType type) throws Exception {
/* 174 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 177 */     registerType((AbstractType)type);
/*     */   }
/*     */   
/*     */   public void visit(SOAPSimpleType type) throws Exception {
/* 181 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 184 */     registerType((AbstractType)type);
/*     */   }
/*     */   
/*     */   public void visit(SOAPAnyType type) throws Exception {
/* 188 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 191 */     registerType((AbstractType)type);
/*     */   }
/*     */   
/*     */   public void visit(SOAPEnumerationType type) throws Exception {
/* 195 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 198 */     registerType((AbstractType)type);
/* 199 */     generateEnumerationSerializer(type);
/*     */   }
/*     */   
/*     */   protected void visitSOAPArrayType(SOAPArrayType type) throws Exception {
/* 203 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 206 */     registerType((AbstractType)type);
/* 207 */     super.visitSOAPArrayType(type);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void visitSOAPStructureType(SOAPStructureType type) throws Exception {
/* 212 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 215 */     registerType((AbstractType)type);
/* 216 */     super.visitSOAPStructureType(type);
/*     */   }
/*     */   
/*     */   public void visit(LiteralEnumerationType type) throws Exception {
/* 220 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 223 */     registerType((AbstractType)type);
/* 224 */     generateEnumerationSerializer(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(LiteralListType type) throws Exception {
/* 229 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 232 */     type.getItemType().accept(this);
/* 233 */     registerType((AbstractType)type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(SOAPListType type) throws Exception {
/* 238 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 241 */     type.getItemType().accept(this);
/* 242 */     registerType((AbstractType)type);
/*     */   }
/*     */   
/*     */   private boolean isRegistered(AbstractType type) {
/* 246 */     return this.types.contains(type);
/*     */   }
/*     */   
/*     */   private void registerType(AbstractType type) {
/* 250 */     this.types.add(type);
/*     */   }
/*     */   
/*     */   private void generateEnumerationSerializer(SOAPEnumerationType type) {
/* 254 */     log("generating Enumeration for: " + this.env.getNames().typeObjectSerializerClassName(this.servicePackage, (SOAPType)type));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 261 */       String className = type.getJavaType().getName() + "_Encoder";
/* 262 */       if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/* 263 */         log("Class " + className + " exists. Not overriding.");
/*     */         return;
/*     */       } 
/* 266 */       File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 274 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 275 */       fi.setFile(classFile);
/* 276 */       fi.setType("EnumerationEncoder");
/* 277 */       this.env.addGeneratedFile(fi);
/*     */       
/* 279 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 282 */       writePackage(out, className);
/* 283 */       out.pln();
/* 284 */       writeImports(out);
/* 285 */       out.pln();
/* 286 */       writeClassDecl(out, className);
/* 287 */       out.pln();
/* 288 */       writeMembers(out, type, className);
/* 289 */       out.pln();
/* 290 */       writeConstructor(out, className);
/* 291 */       out.pln();
/* 292 */       writeGetInstance(out);
/* 293 */       out.pln();
/* 294 */       writeObjectToString(out, (AbstractType)type);
/* 295 */       out.pln();
/* 296 */       writeStringToObject(out, (AbstractType)type);
/* 297 */       out.pln();
/* 298 */       writeGenericMethods(out, (AbstractType)type);
/* 299 */       out.pOln("}");
/* 300 */       out.close();
/* 301 */     } catch (Exception e) {
/* 302 */       fail(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void generateEnumerationSerializer(LiteralEnumerationType type) {
/* 307 */     log("generating Enumeration for: " + this.env.getNames().typeObjectSerializerClassName(this.servicePackage, (LiteralType)type));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 314 */       String className = type.getJavaType().getName() + "_Encoder";
/* 315 */       if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/* 316 */         log("Class " + className + " exists. Not overriding.");
/*     */         return;
/*     */       } 
/* 319 */       File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 327 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 328 */       fi.setFile(classFile);
/* 329 */       fi.setType("EnumerationEncoder");
/* 330 */       this.env.addGeneratedFile(fi);
/*     */       
/* 332 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 335 */       writePackage(out, className);
/* 336 */       out.pln();
/* 337 */       writeImports(out);
/* 338 */       out.pln();
/* 339 */       writeClassDecl(out, className);
/* 340 */       out.pln();
/* 341 */       writeMembers(out, type, className);
/* 342 */       out.pln();
/* 343 */       writeConstructor(out, className);
/* 344 */       out.pln();
/* 345 */       writeGetInstance(out);
/* 346 */       out.pln();
/* 347 */       writeObjectToString(out, (AbstractType)type);
/* 348 */       out.pln();
/* 349 */       writeStringToObject(out, (AbstractType)type);
/* 350 */       out.pln();
/* 351 */       writeGenericMethods(out, (AbstractType)type);
/* 352 */       out.pOln("}");
/* 353 */       out.close();
/* 354 */     } catch (Exception e) {
/* 355 */       fail(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeImports(IndentingWriter p) throws IOException {
/* 360 */     p.pln("import com.sun.xml.rpc.encoding.simpletype.*;");
/* 361 */     p.pln("import javax.xml.namespace.QName;");
/* 362 */     p.pln("import com.sun.xml.rpc.streaming.*;");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeClassDecl(IndentingWriter p, String className) throws IOException {
/* 367 */     p.plnI("public class " + Names.stripQualifier(className) + " extends SimpleTypeEncoderBase {");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeMembers(IndentingWriter p, SOAPEnumerationType type, String className) throws IOException {
/* 378 */     String encoder = SimpleTypeSerializerWriter.getTypeEncoder((AbstractType)type.getBaseType());
/*     */     
/* 380 */     if (encoder.equals("XSDListTypeEncoder")) {
/*     */       
/* 382 */       writeMembers(p, encoder, className, (AbstractType)type.getBaseType());
/*     */     } else {
/* 384 */       writeMembers(p, encoder, className);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeMembers(IndentingWriter p, LiteralEnumerationType type, String className) throws IOException {
/* 392 */     String encoder = SimpleTypeSerializerWriter.getTypeEncoder((AbstractType)type.getBaseType());
/*     */ 
/*     */     
/* 395 */     if (encoder.equals("XSDListTypeEncoder")) {
/*     */       
/* 397 */       writeMembers(p, encoder, className, (AbstractType)type.getBaseType());
/*     */     } else {
/* 399 */       writeMembers(p, encoder, className);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeMembers(IndentingWriter p, String encoder, String className) throws IOException {
/* 407 */     p.pln("private static final SimpleTypeEncoder encoder = " + encoder + ".getInstance();");
/*     */ 
/*     */ 
/*     */     
/* 411 */     p.pln("private static final " + Names.stripQualifier(className) + " instance = new " + Names.stripQualifier(className) + "();");
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
/*     */   private void writeMembers(IndentingWriter p, String encoder, String className, AbstractType baseType) throws IOException {
/* 425 */     p.pln("private static final SimpleTypeEncoder encoder = " + encoder + ".getInstance(" + getListBaseTypeEncoder(baseType.getName()) + ");");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 431 */     p.pln("private static final " + Names.stripQualifier(className) + " instance = new " + Names.stripQualifier(className) + "();");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getListBaseTypeEncoder(QName type) {
/* 440 */     if (type.equals(SchemaConstants.QNAME_TYPE_NMTOKENS)) {
/* 441 */       return "XSDStringEncoder.getInstance(), java.lang.String.class";
/*     */     }
/*     */     
/* 444 */     return "XSDStringEncoder";
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeConstructor(IndentingWriter p, String className) throws IOException {
/* 449 */     p.plnI("private " + Names.stripQualifier(className) + "() {");
/* 450 */     p.pOln("}");
/*     */   }
/*     */   
/*     */   private void writeGetInstance(IndentingWriter p) throws IOException {
/* 454 */     p.plnI("public static SimpleTypeEncoder getInstance() {");
/* 455 */     p.pln("return instance;");
/* 456 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeObjectToString(IndentingWriter p, AbstractType type) throws IOException {
/* 461 */     JavaEnumerationType javaEnum = (JavaEnumerationType)type.getJavaType();
/* 462 */     String baseTypeStr = javaEnum.getBaseType().getName();
/* 463 */     String className = Names.stripQualifier(type.getJavaType().getName());
/* 464 */     p.plnI("public java.lang.String objectToString(java.lang.Object obj, XMLWriter writer) throws java.lang.Exception {");
/*     */     
/* 466 */     p.pln(baseTypeStr + " value = ((" + className + ")obj).getValue();");
/* 467 */     String valueExp = "value";
/* 468 */     if (SimpleToBoxedUtil.isPrimitive(baseTypeStr)) {
/* 469 */       valueExp = SimpleToBoxedUtil.getBoxedExpressionOfType(valueExp, baseTypeStr);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 474 */     p.pln("return encoder.objectToString(" + valueExp + ", writer);");
/* 475 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeStringToObject(IndentingWriter p, AbstractType type) throws IOException {
/* 480 */     JavaEnumerationType javaEnum = (JavaEnumerationType)type.getJavaType();
/* 481 */     String baseTypeStr = javaEnum.getBaseType().getName();
/* 482 */     String className = Names.stripQualifier(type.getJavaType().getName());
/* 483 */     p.plnI("public java.lang.Object stringToObject(java.lang.String str, XMLReader reader) throws java.lang.Exception {");
/*     */     
/* 485 */     String objectExp = "(" + SimpleToBoxedUtil.getBoxedClassName(baseTypeStr) + ")encoder.stringToObject(str, reader)";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 490 */     if (SimpleToBoxedUtil.isPrimitive(baseTypeStr)) {
/* 491 */       objectExp = SimpleToBoxedUtil.getUnboxedExpressionOfType(objectExp, baseTypeStr);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 496 */     p.pln("return " + className + ".fromValue(" + objectExp + ");");
/* 497 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeGenericMethods(IndentingWriter p, AbstractType type) throws IOException {}
/*     */ 
/*     */   
/*     */   private void writeEquals(IndentingWriter p, SOAPEnumerationType type) throws IOException {
/* 506 */     String className = Names.stripQualifier(type.getJavaType().getName());
/* 507 */     p.plnI("public boolean equals(java.lang.Object obj) {");
/* 508 */     p.plnI("if (!obj instanceof " + className + ") {");
/* 509 */     p.pln("return false;");
/* 510 */     p.pOln("}");
/* 511 */     p.pln("((" + className + ")obj).value.equals(value);");
/* 512 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeHashCode(IndentingWriter p, SOAPEnumerationType type) throws IOException {
/* 517 */     p.plnI("public int hashCode() {");
/* 518 */     p.pln("return value.hashCode();");
/* 519 */     p.pOln("}");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\EnumerationEncoderGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */