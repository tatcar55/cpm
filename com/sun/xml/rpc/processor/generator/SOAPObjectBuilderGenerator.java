/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaException;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPAttributeMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.util.GeneratedFileInfo;
/*     */ import com.sun.xml.rpc.processor.util.IndentingWriter;
/*     */ import com.sun.xml.rpc.processor.util.StringUtils;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.util.ArrayList;
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
/*     */ public class SOAPObjectBuilderGenerator
/*     */   extends GeneratorBase
/*     */ {
/*     */   private ArrayList soapBuilders;
/*     */   
/*     */   public SOAPObjectBuilderGenerator() {
/*  67 */     this.soapBuilders = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties) {
/*  74 */     return new SOAPObjectBuilderGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*  82 */     return new SOAPObjectBuilderGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPObjectBuilderGenerator(Model model, Configuration config, Properties properties) {
/*  89 */     super(model, config, properties);
/*  90 */     this.soapBuilders = new ArrayList();
/*     */   }
/*     */   
/*     */   protected void postVisitModel(Model model) throws Exception {
/*  94 */     Iterator<AbstractType> types = model.getExtraTypes();
/*     */     
/*  96 */     while (types.hasNext()) {
/*  97 */       AbstractType type = types.next();
/*  98 */       if (type.isSOAPType())
/*  99 */         ((SOAPType)type).accept(this); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void visitFault(Fault fault) throws Exception {
/* 104 */     if (fault.getBlock().getType().isSOAPType()) {
/* 105 */       ((SOAPType)fault.getBlock().getType()).accept(this);
/*     */     }
/* 107 */     JavaException exception = fault.getJavaException();
/* 108 */     Iterator<JavaStructureMember> members = exception.getMembers();
/* 109 */     AbstractType aType = (AbstractType)exception.getOwner();
/* 110 */     if (aType.isSOAPType()) {
/*     */       
/* 112 */       while (members.hasNext()) {
/* 113 */         SOAPType type = ((SOAPStructureMember)((JavaStructureMember)members.next()).getOwner()).getType();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 118 */         type.accept(this);
/*     */       } 
/*     */     } else {
/* 121 */       LiteralType type = null;
/*     */       
/* 123 */       while (members.hasNext()) {
/* 124 */         JavaStructureMember javaMember = members.next();
/* 125 */         if (javaMember.getOwner() instanceof LiteralElementMember) {
/* 126 */           type = ((LiteralElementMember)javaMember.getOwner()).getType();
/*     */         
/*     */         }
/* 129 */         else if (javaMember.getOwner() instanceof LiteralAttributeMember) {
/*     */           
/* 131 */           type = ((LiteralAttributeMember)javaMember.getOwner()).getType();
/*     */         } 
/*     */         
/* 134 */         type.accept(this);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void preVisitSOAPStructureType(SOAPStructureType structureType) throws Exception {
/* 141 */     if (hasObjectBuilder((SOAPType)structureType)) {
/*     */       return;
/*     */     }
/*     */     try {
/* 145 */       generateObjectBuilderForType(structureType);
/* 146 */     } catch (IOException e) {
/* 147 */       fail("generator.cant.write", structureType.getName().getLocalPart());
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
/*     */   private void generateObjectBuilderForType(SOAPStructureType type) throws IOException {
/* 160 */     addObjectBuilder((SOAPType)type);
/* 161 */     if (needBuilder(type))
/* 162 */       writeObjectBuilderForType(type); 
/*     */   }
/*     */   
/*     */   public static boolean needBuilder(SOAPStructureType type) {
/* 166 */     Iterator<SOAPStructureMember> members = type.getMembers();
/*     */     
/* 168 */     boolean needBuilder = false;
/*     */     
/* 170 */     if (((JavaStructureType)type.getJavaType()).isAbstract())
/* 171 */       return false; 
/* 172 */     while (members.hasNext() && !needBuilder) {
/* 173 */       SOAPStructureMember member = members.next();
/* 174 */       JavaStructureMember javaMember = member.getJavaStructureMember();
/* 175 */       if (!javaMember.isPublic() && javaMember.getConstructorPos() < 0 && javaMember.getWriteMethod() == null)
/*     */       {
/*     */         
/* 178 */         return false;
/*     */       }
/* 180 */       if (member.getType().isReferenceable())
/* 181 */         needBuilder = true; 
/*     */     } 
/* 183 */     return needBuilder;
/*     */   }
/*     */   
/*     */   public static JavaStructureMember[] getConstructorArgs(JavaStructureType type) {
/* 187 */     ArrayList<JavaStructureMember> args = new ArrayList();
/*     */     
/* 189 */     for (int i = 0; i < type.getMembersCount(); i++) {
/* 190 */       Iterator<JavaStructureMember> members = type.getMembers();
/* 191 */       while (members.hasNext()) {
/* 192 */         JavaStructureMember member = members.next();
/* 193 */         if (member.getConstructorPos() == i) {
/* 194 */           args.add(member);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 199 */     JavaStructureMember[] argArray = new JavaStructureMember[args.size()];
/* 200 */     return args.<JavaStructureMember>toArray(argArray);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObjectBuilderForType(SOAPStructureType type) throws IOException {
/* 206 */     JavaType javaType = type.getJavaType();
/* 207 */     if (javaType == null) {
/* 208 */       fail("generator.invalid.model.state.no.javatype", type.getName().getLocalPart());
/*     */     }
/*     */ 
/*     */     
/* 212 */     String className = this.env.getNames().typeObjectBuilderClassName(this.servicePackage, (SOAPType)type);
/*     */     
/* 214 */     if (this.donotOverride && GeneratorUtil.classExists(this.env, className)) {
/* 215 */       log("Class " + className + " exists. Not overriding.");
/*     */       return;
/*     */     } 
/* 218 */     File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 227 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 228 */       fi.setFile(classFile);
/* 229 */       fi.setType("SOAPObject_Builder");
/* 230 */       this.env.addGeneratedFile(fi);
/*     */       
/* 232 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 235 */       writeObjectBuilderCode(out, type);
/* 236 */       out.close();
/* 237 */     } catch (IOException e) {
/* 238 */       fail("generator.cant.write", classFile.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObjectBuilderCode(IndentingWriter p, SOAPStructureType type) throws IOException {
/* 247 */     log("writing object builder for: " + type.getName().getLocalPart());
/* 248 */     String className = this.env.getNames().typeObjectBuilderClassName(this.servicePackage, (SOAPType)type);
/*     */ 
/*     */     
/* 251 */     writePackage(p, className);
/* 252 */     writeImports(p);
/* 253 */     p.pln();
/* 254 */     writeObjectClassDecl(p, className);
/* 255 */     writeMembers(p, type);
/* 256 */     p.pln();
/* 257 */     writeConstructor(p, className, type);
/* 258 */     p.pln();
/* 259 */     writeSetMembers(p, type);
/* 260 */     p.pln();
/* 261 */     writeMemberGateTypeMethod(p, type);
/* 262 */     p.pln();
/* 263 */     writeConstructMethod(p, type);
/* 264 */     p.pln();
/* 265 */     writeSetMemberMethod(p, type);
/* 266 */     p.pln();
/* 267 */     writeInitializeMethod(p, type);
/* 268 */     p.pln();
/* 269 */     writeGetSetInstanceMethods(p, type);
/* 270 */     p.pOln("}");
/*     */   }
/*     */   
/*     */   private void writeImports(IndentingWriter p) throws IOException {
/* 274 */     p.pln("import com.sun.xml.rpc.encoding.*;");
/* 275 */     p.pln("import com.sun.xml.rpc.util.exception.LocalizableExceptionAdapter;");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeObjectClassDecl(IndentingWriter p, String className) throws IOException {
/* 281 */     p.plnI("public class " + Names.stripQualifier(className) + " implements SOAPInstanceBuilder {");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeMembers(IndentingWriter p, SOAPStructureType type) throws IOException {
/* 289 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */     
/* 291 */     p.pln("private " + javaStructure.getName() + " _instance;");
/* 292 */     Iterator<JavaStructureMember> iterator = javaStructure.getMembers();
/*     */ 
/*     */     
/* 295 */     while (iterator.hasNext()) {
/* 296 */       JavaStructureMember javaMember = iterator.next();
/* 297 */       p.pln("private " + javaMember.getType().getName() + " " + this.env.getNames().validJavaName(javaMember.getName()) + ";");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     iterator = javaStructure.getMembers();
/*     */     
/* 306 */     for (int i = 0; iterator.hasNext(); i++) {
/* 307 */       JavaStructureMember javaMember = iterator.next();
/* 308 */       p.p("private static final int ");
/* 309 */       p.pln(this.env.getNames().memberName(javaMember.getName().toUpperCase() + "_INDEX") + " = " + i + ";");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean needsConstructor(SOAPStructureType type) {
/* 319 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */ 
/*     */     
/* 322 */     Iterator<JavaStructureMember> members = javaStructure.getMembers();
/* 323 */     boolean writeContent = false;
/* 324 */     for (int i = 0; members.hasNext(); i++) {
/* 325 */       JavaStructureMember member = members.next();
/* 326 */       if (member.getConstructorPos() >= 0) {
/* 327 */         return true;
/*     */       }
/*     */     } 
/* 330 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeConstructor(IndentingWriter p, String className, SOAPStructureType type) throws IOException {
/* 338 */     p.pln("public " + Names.stripQualifier(className) + "() {");
/* 339 */     p.pln("}");
/*     */     
/* 341 */     if (!needsConstructor(type))
/*     */       return; 
/* 343 */     p.pln();
/* 344 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */ 
/*     */     
/* 347 */     Iterator<JavaStructureMember> members = javaStructure.getMembers();
/* 348 */     if (members.hasNext()) {
/* 349 */       p.pln();
/* 350 */       p.p("public " + Names.stripQualifier(className) + "(");
/*     */       int i;
/* 352 */       for (i = 0; members.hasNext(); i++) {
/* 353 */         if (i != 0)
/* 354 */           p.p(", "); 
/* 355 */         JavaStructureMember member = members.next();
/* 356 */         JavaType javaType = member.getType();
/* 357 */         p.p(member.getType().getName() + " " + this.env.getNames().validJavaName(member.getName()));
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 362 */       p.plnI(") {");
/* 363 */       members = javaStructure.getMembers();
/* 364 */       for (i = 0; members.hasNext(); i++) {
/* 365 */         JavaStructureMember member = members.next();
/* 366 */         JavaType javaType = member.getType();
/* 367 */         p.pln("this." + this.env.getNames().validJavaName(member.getName()) + " = " + this.env.getNames().validJavaName(member.getName()) + ";");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 374 */       p.pOln("}");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeSetMembers(IndentingWriter p, SOAPStructureType type) throws IOException {
/* 380 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */     
/* 382 */     Iterator<JavaStructureMember> iterator = javaStructure.getMembers();
/*     */ 
/*     */     
/* 385 */     for (int i = 0; iterator.hasNext(); i++) {
/* 386 */       if (i > 0)
/* 387 */         p.pln(); 
/* 388 */       JavaStructureMember javaMember = iterator.next();
/* 389 */       String writeMethod = javaMember.getWriteMethod();
/* 390 */       if (writeMethod == null) {
/* 391 */         writeMethod = "set" + StringUtils.capitalize(javaMember.getName());
/*     */       }
/*     */       
/* 394 */       p.plnI("public void " + writeMethod + "(" + javaMember.getType().getName() + " " + this.env.getNames().validJavaName(javaMember.getName()) + ") {");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 402 */       p.pln("this." + this.env.getNames().validJavaName(javaMember.getName()) + " = " + this.env.getNames().validJavaName(javaMember.getName()) + ";");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 408 */       p.pOln("}");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeMemberGateTypeMethod(IndentingWriter p, SOAPStructureType type) throws IOException {
/* 416 */     p.plnI("public int memberGateType(int memberIndex) {");
/* 417 */     p.plnI("switch (memberIndex) {");
/* 418 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */     
/* 420 */     Iterator<JavaStructureMember> iterator = javaStructure.getMembers();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 425 */     for (int i = 0; iterator.hasNext(); i++) {
/* 426 */       JavaStructureMember javaMember = iterator.next();
/* 427 */       Object owner = javaMember.getOwner();
/* 428 */       SOAPType ownerType = null;
/*     */       
/* 430 */       if (owner instanceof SOAPStructureMember) {
/* 431 */         ownerType = ((SOAPStructureMember)owner).getType();
/*     */       } else {
/* 433 */         ownerType = ((SOAPAttributeMember)owner).getType();
/*     */       } 
/* 435 */       boolean referenceable = ownerType.isReferenceable();
/* 436 */       if (referenceable) {
/* 437 */         p.plnI("case " + this.env.getNames().memberName(javaMember.getName().toUpperCase() + "_INDEX") + ":");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 442 */         if (javaMember.getConstructorPos() < 0) {
/* 443 */           p.pln("return GATES_INITIALIZATION | REQUIRES_CREATION;");
/*     */         } else {
/* 445 */           p.pln("return GATES_CONSTRUCTION | REQUIRES_INITIALIZATION;");
/*     */         } 
/*     */         
/* 448 */         p.pO();
/*     */       } 
/*     */     } 
/* 451 */     p.plnI("default:");
/* 452 */     p.pln("throw new IllegalArgumentException();");
/* 453 */     p.pO();
/* 454 */     p.pOln("}");
/* 455 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeConstructMethod(IndentingWriter p, SOAPStructureType type) throws IOException {
/* 462 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */     
/* 464 */     JavaStructureMember[] constructorArgs = getConstructorArgs(javaStructure);
/*     */     
/* 466 */     p.plnI("public void construct() {");
/* 467 */     if (constructorArgs.length > 0) {
/*     */       
/* 469 */       p.p("_instance = new " + javaStructure.getName() + "(");
/* 470 */       for (int i = 0; i < constructorArgs.length; i++) {
/* 471 */         if (i > 0)
/* 472 */           p.p(", "); 
/* 473 */         JavaStructureMember javaMember = constructorArgs[i];
/* 474 */         p.p(this.env.getNames().validJavaName(javaMember.getName()));
/*     */       } 
/* 476 */       p.pln(");");
/*     */     } 
/* 478 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeSetMemberMethod(IndentingWriter p, SOAPStructureType type) throws IOException {
/* 485 */     p.plnI("public void setMember(int index, java.lang.Object memberValue) {");
/*     */     
/* 487 */     p.plnI("try {");
/* 488 */     p.plnI("switch(index) {");
/* 489 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */     
/* 491 */     Iterator<JavaStructureMember> iterator = javaStructure.getMembers();
/*     */ 
/*     */ 
/*     */     
/* 495 */     for (int i = 0; iterator.hasNext(); i++) {
/* 496 */       JavaStructureMember javaMember = iterator.next();
/* 497 */       Object owner = javaMember.getOwner();
/* 498 */       SOAPType ownerType = null;
/*     */       
/* 500 */       if (owner instanceof SOAPStructureMember) {
/* 501 */         ownerType = ((SOAPStructureMember)owner).getType();
/*     */       } else {
/* 503 */         ownerType = ((SOAPAttributeMember)owner).getType();
/*     */       } 
/* 505 */       boolean referenceable = ownerType.isReferenceable();
/* 506 */       if (referenceable) {
/* 507 */         p.plnI("case " + this.env.getNames().memberName(javaMember.getName().toUpperCase() + "_INDEX") + ":");
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 512 */         if (javaMember.isPublic()) {
/* 513 */           p.p("_instance.");
/* 514 */           p.pln(this.env.getNames().validJavaName(javaMember.getName()) + " = (" + javaMember.getType().getName() + ")memberValue;");
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 519 */         else if (javaMember.getConstructorPos() < 0) {
/* 520 */           p.p("_instance.");
/* 521 */           p.pln(javaMember.getWriteMethod() + "((" + javaMember.getType().getName() + ")memberValue);");
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 527 */           String writeMethod = javaMember.getWriteMethod();
/* 528 */           if (writeMethod == null) {
/* 529 */             writeMethod = "set" + StringUtils.capitalize(javaMember.getName());
/*     */           }
/*     */ 
/*     */           
/* 533 */           p.pln(writeMethod + "((" + javaMember.getType().getName() + ")memberValue);");
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 539 */         p.pln("break;");
/* 540 */         p.pO();
/*     */       } 
/*     */     } 
/* 543 */     p.plnI("default:");
/* 544 */     p.pln("throw new java.lang.IllegalArgumentException();");
/* 545 */     p.pO();
/* 546 */     p.pOln("}");
/*     */     
/* 548 */     p.pOln("}");
/* 549 */     p.plnI("catch (java.lang.RuntimeException e) {");
/* 550 */     p.pln("throw e;");
/* 551 */     p.pOln("}");
/* 552 */     p.plnI("catch (java.lang.Exception e) {");
/* 553 */     p.pln("throw new DeserializationException(new LocalizableExceptionAdapter(e));");
/*     */     
/* 555 */     p.pOln("}");
/*     */     
/* 557 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeInitializeMethod(IndentingWriter p, SOAPStructureType type) throws IOException {
/* 564 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */     
/* 566 */     Iterator<JavaStructureMember> iterator = javaStructure.getMembers();
/*     */ 
/*     */     
/* 569 */     boolean writeContent = false;
/* 570 */     p.plnI("public void initialize() {");
/* 571 */     if (writeContent) {
/* 572 */       iterator = javaStructure.getMembers();
/* 573 */       p.plnI("for (int i=0; i<memberSet.length; i++) {");
/* 574 */       p.plnI("if (!memberSet[i]) {");
/* 575 */       p.pln("continue;");
/* 576 */       p.pOln("}");
/* 577 */       p.plnI("switch(i) {");
/* 578 */       for (int i = 0; iterator.hasNext(); i++) {
/* 579 */         JavaStructureMember javaMember = iterator.next();
/* 580 */         SOAPStructureMember soapMember = (SOAPStructureMember)javaMember.getOwner();
/* 581 */         if (soapMember.getType().isReferenceable() && !javaMember.isPublic() && javaMember.getConstructorPos() < 0) {
/*     */ 
/*     */           
/* 584 */           p.plnI("case " + this.env.getNames().memberName(javaMember.getName().toUpperCase() + "_INDEX") + ":");
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 589 */           p.pln("_instance." + javaMember.getWriteMethod() + "(" + this.env.getNames().validJavaName(javaMember.getName()) + ");");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 595 */           p.pln("break;");
/* 596 */           p.pO();
/*     */         } 
/*     */       } 
/* 599 */       p.pOln("}");
/* 600 */       p.pOln("}");
/*     */     } 
/* 602 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeGetSetInstanceMethods(IndentingWriter p, SOAPStructureType type) throws IOException {
/* 609 */     p.plnI("public void setInstance(java.lang.Object instance) {");
/* 610 */     p.pln("_instance = (" + type.getJavaType().getName() + ")instance;");
/* 611 */     p.pOln("}");
/* 612 */     p.pln();
/* 613 */     p.plnI("public java.lang.Object getInstance() {");
/* 614 */     p.pln("return _instance;");
/* 615 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasObjectBuilder(SOAPType type) {
/* 620 */     return this.soapBuilders.contains(type);
/*     */   }
/*     */   
/*     */   private void addObjectBuilder(SOAPType type) throws IOException {
/* 624 */     if (this.soapBuilders.contains(type))
/*     */     {
/* 626 */       fail("Internal error: attempting to add duplicate SOAP builder");
/*     */     }
/* 628 */     this.soapBuilders.add(type);
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\SOAPObjectBuilderGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */