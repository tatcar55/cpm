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
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAllType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
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
/*     */ public class CustomClassGenerator
/*     */   extends GeneratorBase
/*     */ {
/*     */   private Set types;
/*     */   private Set faults;
/*     */   private boolean dontGenerateRPCStructures;
/*     */   private boolean dontGenerateWrapperClasses;
/*     */   
/*     */   public CustomClassGenerator() {}
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/*  83 */     return new CustomClassGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties) {
/*  90 */     return new CustomClassGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CustomClassGenerator(Model model, Configuration config, Properties properties) {
/*  97 */     super(model, config, properties);
/*  98 */     String key = "dontGenerateRPCStructures";
/*  99 */     this.dontGenerateRPCStructures = Boolean.valueOf(properties.getProperty(key)).booleanValue();
/*     */     
/* 101 */     key = "dontGenerateWrapperClasses";
/* 102 */     this.dontGenerateWrapperClasses = Boolean.valueOf(properties.getProperty(key)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void visitFault(Fault fault) throws Exception {
/* 107 */     if (isRegistered(fault))
/*     */       return; 
/* 109 */     registerFault(fault);
/* 110 */     JavaException exception = fault.getJavaException();
/* 111 */     AbstractType aType = (AbstractType)exception.getOwner();
/* 112 */     if (aType.isSOAPType()) {
/* 113 */       ((SOAPType)aType).accept(this);
/* 114 */       Iterator<JavaStructureMember> members = exception.getMembers();
/*     */       
/* 116 */       while (members.hasNext()) {
/* 117 */         SOAPType type = ((SOAPStructureMember)((JavaStructureMember)members.next()).getOwner()).getType();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 122 */         type.accept(this);
/*     */       } 
/*     */     } else {
/* 125 */       ((LiteralType)aType).accept(this);
/* 126 */       Iterator<JavaStructureMember> members = exception.getMembers();
/* 127 */       LiteralType type = null;
/*     */       
/* 129 */       while (members.hasNext()) {
/* 130 */         JavaStructureMember javaMember = members.next();
/* 131 */         if (javaMember.getOwner() instanceof LiteralElementMember) {
/* 132 */           type = ((LiteralElementMember)javaMember.getOwner()).getType();
/*     */         
/*     */         }
/* 135 */         else if (javaMember.getOwner() instanceof LiteralAttributeMember) {
/*     */           
/* 137 */           type = ((LiteralAttributeMember)javaMember.getOwner()).getType();
/*     */         } 
/*     */         
/* 140 */         type.accept(this);
/*     */       } 
/*     */     } 
/* 143 */     if (fault.getParentFault() != null)
/* 144 */       fault.getParentFault().accept(this); 
/* 145 */     Iterator<Fault> iter = fault.getSubfaults();
/* 146 */     while (iter != null && iter.hasNext())
/*     */     {
/* 148 */       ((Fault)iter.next()).accept(this);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void preVisitModel(Model model) throws Exception {
/* 153 */     this.types = new HashSet();
/* 154 */     this.faults = new HashSet();
/*     */   }
/*     */   
/*     */   protected void postVisitModel(Model model) throws Exception {
/* 158 */     this.types = null;
/* 159 */     this.faults = null;
/*     */   }
/*     */   
/*     */   public void preVisitSOAPArrayType(SOAPArrayType type) throws Exception {
/* 163 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 166 */     registerType((AbstractType)type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void preVisitSOAPStructureType(SOAPStructureType type) throws Exception {
/* 171 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 174 */     if (this.dontGenerateRPCStructures && (type instanceof com.sun.xml.rpc.processor.model.soap.RPCRequestOrderedStructureType || type instanceof com.sun.xml.rpc.processor.model.soap.RPCRequestUnorderedStructureType || type instanceof com.sun.xml.rpc.processor.model.soap.RPCResponseStructureType)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 180 */     registerType((AbstractType)type);
/* 181 */     if (!type.getJavaType().isPresent()) {
/* 182 */       String className = this.env.getNames().customJavaTypeClassName((AbstractType)type);
/* 183 */       if (!this.donotOverride || !GeneratorUtil.classExists(this.env, className)) {
/*     */         
/* 185 */         generateJavaClass(type);
/*     */       } else {
/* 187 */         log("Class " + className + " exists. Not overriding.");
/*     */       } 
/*     */     } 
/* 190 */     if (type.getParentType() != null)
/* 191 */       type.getParentType().accept(this); 
/* 192 */     Iterator<SOAPStructureType> iter = type.getSubtypes();
/* 193 */     while (iter != null && iter.hasNext())
/*     */     {
/* 195 */       ((SOAPStructureType)iter.next()).accept(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void preVisitLiteralSequenceType(LiteralSequenceType type) throws Exception {
/* 201 */     visitLiteralStructuredType((LiteralStructuredType)type);
/*     */   }
/*     */   
/*     */   public void preVisitLiteralAllType(LiteralAllType type) throws Exception {
/* 205 */     visitLiteralStructuredType((LiteralStructuredType)type);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void preVisitLiteralArrayWrapperType(LiteralArrayWrapperType type) throws Exception {
/* 210 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 213 */     registerType((AbstractType)type);
/* 214 */     if (!type.getJavaType().isPresent()) {
/* 215 */       String className = this.env.getNames().customJavaTypeClassName((AbstractType)type);
/* 216 */       if (!this.donotOverride || !GeneratorUtil.classExists(this.env, className)) {
/*     */         
/* 218 */         generateJavaClass(type);
/*     */       } else {
/* 220 */         log("Class " + className + " exists. Not overriding.");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void visitLiteralStructuredType(LiteralStructuredType type) throws Exception {
/* 227 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 230 */     if (this.dontGenerateRPCStructures && type.isRpcWrapper()) {
/*     */       return;
/*     */     }
/* 233 */     registerType((AbstractType)type);
/* 234 */     if (!type.getJavaType().isPresent()) {
/* 235 */       String className = this.env.getNames().customJavaTypeClassName((AbstractType)type);
/* 236 */       if (!this.donotOverride || !GeneratorUtil.classExists(this.env, className)) {
/*     */         
/* 238 */         generateJavaClass(type);
/*     */       } else {
/* 240 */         log("Class " + className + " exists. Not overriding.");
/*     */       } 
/*     */     } 
/* 243 */     if (type.getParentType() != null)
/* 244 */       type.getParentType().accept(this); 
/* 245 */     Iterator<LiteralStructuredType> iter = type.getSubtypes();
/* 246 */     while (iter != null && iter.hasNext())
/*     */     {
/* 248 */       ((LiteralStructuredType)iter.next()).accept(this);
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isRegistered(AbstractType type) {
/* 253 */     return this.types.contains(type);
/*     */   }
/*     */   
/*     */   private void registerType(AbstractType type) {
/* 257 */     this.types.add(type);
/*     */   }
/*     */   
/*     */   private boolean isRegistered(Fault fault) {
/* 261 */     return this.faults.contains(fault);
/*     */   }
/*     */   
/*     */   private void registerFault(Fault fault) {
/* 265 */     this.faults.add(fault);
/*     */   }
/*     */   
/*     */   private void generateJavaClass(SOAPStructureType type) {
/* 269 */     if (type.getJavaType() instanceof JavaException)
/*     */       return; 
/* 271 */     log("generating JavaClass for: " + type.getName().getLocalPart());
/*     */     
/*     */     try {
/* 274 */       String className = this.env.getNames().customJavaTypeClassName((AbstractType)type);
/* 275 */       File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 282 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 283 */       fi.setFile(classFile);
/* 284 */       fi.setType("ValueType");
/* 285 */       this.env.addGeneratedFile(fi);
/*     */       
/* 287 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 290 */       writePackage(out, className);
/* 291 */       out.pln();
/* 292 */       JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */       
/* 294 */       writeClassDecl(out, className, javaStructure);
/* 295 */       Iterator<JavaStructureMember> members = javaStructure.getMembers();
/*     */ 
/*     */       
/* 298 */       while (members.hasNext()) {
/* 299 */         JavaStructureMember member = members.next();
/* 300 */         if (member.isInherited() && javaStructure.getSuperclass() != null) {
/*     */           continue;
/*     */         }
/* 303 */         String typeName = member.getType().getName();
/* 304 */         if (member.isPublic()) {
/* 305 */           out.pln("public " + typeName + " " + member.getName() + ";");
/*     */           continue;
/*     */         } 
/* 308 */         out.pln("protected " + typeName + " " + member.getName() + ";");
/*     */       } 
/*     */ 
/*     */       
/* 312 */       out.pln();
/* 313 */       writeClassConstructor(out, className, javaStructure);
/* 314 */       members = javaStructure.getMembers();
/* 315 */       while (members.hasNext()) {
/* 316 */         JavaStructureMember member = members.next();
/* 317 */         if (member.isInherited() && javaStructure.getSuperclass() != null) {
/*     */           continue;
/*     */         }
/* 320 */         out.pln();
/* 321 */         out.plnI("public " + member.getType().getName() + " " + member.getReadMethod() + "() {");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 327 */         out.pln("return " + member.getName() + ";");
/* 328 */         out.pOln("}");
/* 329 */         out.pln();
/* 330 */         out.plnI("public void " + member.getWriteMethod() + "(" + member.getType().getName() + " " + member.getName() + ") {");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 338 */         out.pln("this." + member.getName() + " = " + member.getName() + ";");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 344 */         out.pOln("}");
/*     */       } 
/* 346 */       out.pOln("}");
/* 347 */       out.close();
/* 348 */     } catch (Exception e) {
/* 349 */       fail(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void generateJavaClass(LiteralStructuredType type) {
/* 354 */     if (type.getJavaType() instanceof JavaException || (this.dontGenerateWrapperClasses && type instanceof LiteralSequenceType && ((LiteralSequenceType)type).isUnwrapped())) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 364 */     log("generating JavaClass for: " + type.getName().getLocalPart());
/*     */     
/*     */     try {
/* 367 */       String className = this.env.getNames().customJavaTypeClassName((AbstractType)type);
/* 368 */       File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 375 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 376 */       fi.setFile(classFile);
/* 377 */       fi.setType("ValueType");
/* 378 */       this.env.addGeneratedFile(fi);
/*     */       
/* 380 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 383 */       writePackage(out, className);
/* 384 */       out.pln();
/* 385 */       JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */       
/* 387 */       writeClassDecl(out, className, javaStructure);
/* 388 */       Iterator<JavaStructureMember> members = javaStructure.getMembers();
/*     */ 
/*     */       
/* 391 */       while (members.hasNext()) {
/* 392 */         JavaStructureMember member = members.next();
/* 393 */         if (member.isInherited())
/*     */           continue; 
/* 395 */         String typeName = member.getType().getName();
/* 396 */         if (member.isPublic()) {
/* 397 */           out.pln("public " + typeName + " " + member.getName() + ";");
/*     */           continue;
/*     */         } 
/* 400 */         out.pln("protected " + typeName + " " + member.getName() + ";");
/*     */       } 
/*     */ 
/*     */       
/* 404 */       out.pln();
/* 405 */       writeClassConstructor(out, className, javaStructure);
/* 406 */       members = javaStructure.getMembers();
/* 407 */       while (members.hasNext()) {
/* 408 */         JavaStructureMember member = members.next();
/* 409 */         if (member.isInherited())
/*     */           continue; 
/* 411 */         out.pln();
/* 412 */         out.plnI("public " + member.getType().getName() + " " + member.getReadMethod() + "() {");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 418 */         out.pln("return " + member.getName() + ";");
/* 419 */         out.pOln("}");
/* 420 */         out.pln();
/* 421 */         out.plnI("public void " + member.getWriteMethod() + "(" + member.getType().getName() + " " + member.getName() + ") {");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 429 */         out.pln("this." + member.getName() + " = " + member.getName() + ";");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 435 */         out.pOln("}");
/*     */       } 
/* 437 */       out.pOln("}");
/* 438 */       out.close();
/* 439 */     } catch (Exception e) {
/* 440 */       fail(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void generateJavaClass(LiteralArrayWrapperType type) {
/* 445 */     if (type.getJavaType() instanceof JavaException) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 452 */     log("generating JavaClass for: " + type.getName().getLocalPart());
/*     */     
/*     */     try {
/* 455 */       String className = this.env.getNames().customJavaTypeClassName((AbstractType)type);
/* 456 */       File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 463 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 464 */       fi.setFile(classFile);
/* 465 */       fi.setType("ValueType");
/* 466 */       this.env.addGeneratedFile(fi);
/*     */       
/* 468 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 471 */       writePackage(out, className);
/* 472 */       out.pln();
/* 473 */       JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */       
/* 475 */       writeClassDecl(out, className, javaStructure);
/* 476 */       String structArrayName = ((JavaStructureMember)javaStructure.getMembers().next()).getType().getName();
/*     */ 
/*     */ 
/*     */       
/* 480 */       String memberName = ((JavaStructureMember)javaStructure.getMembers().next()).getName();
/*     */ 
/*     */       
/* 483 */       out.pln("private " + structArrayName + " " + memberName + ";");
/*     */       
/* 485 */       out.pln();
/* 486 */       writeArrayWrapperTypeClassConstructors(out, className, type);
/* 487 */       out.pln();
/* 488 */       writeFromArrayToArrayMethods(out, type);
/* 489 */       JavaStructureMember member = type.getElementMember().getJavaStructureMember();
/*     */       
/* 491 */       out.pln();
/* 492 */       out.plnI("public " + structArrayName + " " + member.getReadMethod() + "() {");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 498 */       out.pln("return " + memberName + ";");
/* 499 */       out.pOln("}");
/* 500 */       out.pln();
/* 501 */       out.plnI("public void " + member.getWriteMethod() + "(" + structArrayName + " " + memberName + ") {");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 509 */       out.pln("this." + memberName + " = " + memberName + ";");
/* 510 */       out.pOln("}");
/* 511 */       out.pOln("}");
/* 512 */       out.close();
/* 513 */     } catch (Exception e) {
/* 514 */       fail(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeClassDecl(IndentingWriter p, String className, JavaStructureType javaStruct) throws IOException {
/* 523 */     JavaStructureType superclass = javaStruct.getSuperclass();
/* 524 */     String classDeclStr = "public class " + Names.stripQualifier(className);
/* 525 */     if (superclass != null) {
/* 526 */       classDeclStr = classDeclStr + " extends " + superclass.getName();
/*     */     }
/* 528 */     if (this.generateSerializableIf) {
/* 529 */       classDeclStr = classDeclStr + " implements java.io.Serializable";
/*     */     }
/* 531 */     classDeclStr = classDeclStr + " {";
/* 532 */     p.plnI(classDeclStr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeClassConstructor(IndentingWriter p, String className, JavaStructureType javaStructure) throws IOException {
/* 540 */     p.pln("public " + Names.stripQualifier(className) + "() {");
/* 541 */     p.pln("}");
/* 542 */     Iterator<JavaStructureMember> members = javaStructure.getMembers();
/* 543 */     if (members.hasNext()) {
/* 544 */       p.pln();
/* 545 */       p.p("public " + Names.stripQualifier(className) + "(");
/*     */       int i;
/* 547 */       for (i = 0; members.hasNext(); i++) {
/* 548 */         if (i != 0)
/* 549 */           p.p(", "); 
/* 550 */         JavaStructureMember member = members.next();
/* 551 */         p.p(member.getType().getName() + " " + member.getName());
/*     */       } 
/* 553 */       p.plnI(") {");
/* 554 */       members = javaStructure.getMembers();
/* 555 */       for (i = 0; members.hasNext(); i++) {
/* 556 */         JavaStructureMember member = members.next();
/* 557 */         p.pln("this." + member.getName() + " = " + member.getName() + ";");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 564 */       p.pOln("}");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeArrayWrapperTypeClassConstructors(IndentingWriter p, String className, LiteralArrayWrapperType arrayWrapperType) throws IOException {
/* 573 */     String theClassName = Names.stripQualifier(className);
/* 574 */     String structArrayName = arrayWrapperType.getElementMember().getType().getJavaType().getName() + "[]";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 582 */     JavaStructureType javaStructure = (JavaStructureType)arrayWrapperType.getJavaType();
/*     */     
/* 584 */     JavaStructureMember member = javaStructure.getMembers().next();
/* 585 */     String memberName = member.getName();
/* 586 */     p.pln("public " + theClassName + "() {");
/* 587 */     p.pln("}");
/* 588 */     p.pln();
/* 589 */     p.p("public " + theClassName + "(" + structArrayName);
/* 590 */     p.p(" sourceArray");
/* 591 */     p.plnI(") {");
/* 592 */     p.pln(memberName + " = sourceArray;");
/* 593 */     p.pOln("}");
/* 594 */     if (arrayWrapperType.getElementMember().getType() instanceof LiteralArrayWrapperType) {
/*     */       
/* 596 */       p.pln();
/* 597 */       p.p("public " + theClassName + "(");
/* 598 */       p.p(arrayWrapperType.getJavaArrayType().getName() + " sourceArray");
/* 599 */       p.plnI(") {");
/* 600 */       p.pln("fromArray(sourceArray);");
/* 601 */       p.pOln("}");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeFromArrayToArrayMethods(IndentingWriter p, LiteralArrayWrapperType type) throws IOException {
/* 610 */     boolean elemTypeIsArrayWrapperType = type.getElementMember().getType() instanceof LiteralArrayWrapperType;
/*     */ 
/*     */ 
/*     */     
/* 614 */     JavaStructureType javaStructure = (JavaStructureType)type.getJavaType();
/*     */     
/* 616 */     JavaStructureMember member = javaStructure.getMembers().next();
/* 617 */     JavaType javaType = member.getType();
/* 618 */     String typeName = type.getJavaArrayType().getName();
/* 619 */     String elementTypeName = javaType.getName();
/* 620 */     String memberName = member.getName();
/*     */     
/* 622 */     p.plnI("public void fromArray(" + typeName + " sourceArray) {");
/* 623 */     if (elemTypeIsArrayWrapperType) {
/* 624 */       int idx = elementTypeName.indexOf("[]");
/* 625 */       String tmp = elementTypeName.substring(0, idx);
/* 626 */       p.pln(memberName + " = new " + tmp + "[sourceArray.length];");
/* 627 */       p.plnI("for (int i=0; i<sourceArray.length; i++) {");
/* 628 */       p.pln(memberName + "[i] = new " + tmp + "(sourceArray[i]);");
/* 629 */       p.pOln("}");
/*     */     } else {
/* 631 */       p.pln("this." + memberName + " = sourceArray;");
/*     */     } 
/* 633 */     p.pOln("}");
/* 634 */     p.pln();
/*     */     
/* 636 */     p.plnI("public " + typeName + " toArray() {");
/* 637 */     if (elemTypeIsArrayWrapperType) {
/* 638 */       String javaTypeName = type.getJavaArrayType().getName();
/* 639 */       int idx = javaTypeName.indexOf("[]") + 1;
/* 640 */       String tmp = javaTypeName.substring(0, idx);
/* 641 */       p.pln(javaTypeName + " tmpArray = new " + tmp + memberName + ".length" + javaTypeName.substring(idx) + ";");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 649 */       p.plnI("for (int i=0; i<" + memberName + ".length; i++) {");
/* 650 */       p.pln("tmpArray[i] = " + memberName + "[i].toArray();");
/* 651 */       p.pOln("}");
/* 652 */       p.pln("return tmpArray;");
/*     */     } else {
/* 654 */       p.pln("return " + memberName + ";");
/*     */     } 
/* 656 */     p.pOln("}");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\CustomClassGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */