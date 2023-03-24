/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDBase64BinaryEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDDateTimeCalendarEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDHexBinaryEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDListTypeEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDStringEncoder;
/*     */ import com.sun.xml.rpc.encoding.simpletype.XSDTimeEncoder;
/*     */ import com.sun.xml.rpc.processor.config.Configuration;
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Model;
/*     */ import com.sun.xml.rpc.processor.model.Parameter;
/*     */ import com.sun.xml.rpc.processor.model.Request;
/*     */ import com.sun.xml.rpc.processor.model.Response;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaEnumerationEntry;
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
/*     */ import com.sun.xml.rpc.util.VersionUtil;
/*     */ import com.sun.xml.rpc.wsdl.document.schema.SchemaConstants;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Calendar;
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
/*     */ public class EnumerationGenerator
/*     */   extends GeneratorBase
/*     */ {
/*     */   private Set types;
/*     */   private AbstractType type;
/*     */   
/*     */   public EnumerationGenerator() {}
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties) {
/*  92 */     return new EnumerationGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneratorBase getGenerator(Model model, Configuration config, Properties properties, SOAPVersion ver) {
/* 100 */     return new EnumerationGenerator(model, config, properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumerationGenerator(Model model, Configuration config, Properties properties) {
/* 107 */     super(model, config, properties);
/*     */   }
/*     */   
/*     */   protected void preVisitModel(Model model) throws Exception {
/* 111 */     this.types = new HashSet();
/*     */   }
/*     */   
/*     */   protected void postVisitModel(Model model) throws Exception {
/* 115 */     this.types = null;
/*     */   }
/*     */   
/*     */   protected void visitParameter(Parameter param) throws Exception {
/* 119 */     AbstractType type = param.getType();
/* 120 */     if (type.isSOAPType()) {
/* 121 */       ((SOAPType)type).accept(this);
/*     */     } else {
/* 123 */       ((LiteralType)type).accept(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void preVisitResponse(Response response) throws Exception {
/* 128 */     Iterator<Parameter> iter = response.getParameters();
/*     */     
/* 130 */     while (iter.hasNext()) {
/* 131 */       ((Parameter)iter.next()).accept(this);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void preVisitRequest(Request request) throws Exception {
/* 136 */     Iterator<Parameter> iter = request.getParameters();
/*     */     
/* 138 */     while (iter.hasNext()) {
/* 139 */       ((Parameter)iter.next()).accept(this);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void visitFault(Fault fault) throws Exception {
/* 144 */     if (fault.getBlock().getType().isSOAPType()) {
/* 145 */       ((SOAPType)fault.getBlock().getType()).accept(this);
/*     */     }
/* 147 */     JavaException exception = fault.getJavaException();
/*     */     
/* 149 */     Iterator<JavaStructureMember> members = exception.getMembers();
/* 150 */     AbstractType aType = (AbstractType)exception.getOwner();
/* 151 */     if (aType.isSOAPType()) {
/*     */       
/* 153 */       while (members.hasNext()) {
/* 154 */         SOAPType type = ((SOAPStructureMember)((JavaStructureMember)members.next()).getOwner()).getType();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 159 */         type.accept(this);
/*     */       } 
/*     */     } else {
/* 162 */       LiteralType type = null;
/*     */       
/* 164 */       while (members.hasNext()) {
/* 165 */         JavaStructureMember javaMember = members.next();
/* 166 */         if (javaMember.getOwner() instanceof LiteralElementMember) {
/* 167 */           type = ((LiteralElementMember)javaMember.getOwner()).getType();
/*     */         
/*     */         }
/* 170 */         else if (javaMember.getOwner() instanceof LiteralAttributeMember) {
/*     */           
/* 172 */           type = ((LiteralAttributeMember)javaMember.getOwner()).getType();
/*     */         } 
/*     */         
/* 175 */         type.accept(this);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(SOAPCustomType type) throws Exception {
/* 182 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 185 */     registerType((AbstractType)type);
/*     */   }
/*     */   
/*     */   public void visit(SOAPSimpleType type) throws Exception {
/* 189 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 192 */     registerType((AbstractType)type);
/*     */   }
/*     */   
/*     */   public void visit(SOAPAnyType type) throws Exception {
/* 196 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 199 */     registerType((AbstractType)type);
/*     */   }
/*     */   
/*     */   public void visit(SOAPEnumerationType type) throws Exception {
/* 203 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 206 */     registerType((AbstractType)type);
/* 207 */     generateEnumeration((AbstractType)type);
/*     */   }
/*     */   
/*     */   protected void visitSOAPArrayType(SOAPArrayType type) throws Exception {
/* 211 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 214 */     registerType((AbstractType)type);
/* 215 */     super.visitSOAPArrayType(type);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void visitSOAPStructureType(SOAPStructureType type) throws Exception {
/* 220 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 223 */     registerType((AbstractType)type);
/* 224 */     super.visitSOAPStructureType(type);
/*     */   }
/*     */   
/*     */   public void visit(LiteralEnumerationType type) throws Exception {
/* 228 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 231 */     registerType((AbstractType)type);
/* 232 */     if (!type.getJavaType().isPresent()) {
/* 233 */       String className = type.getJavaType().getName();
/* 234 */       if (!this.donotOverride || !GeneratorUtil.classExists(this.env, className)) {
/*     */         
/* 236 */         generateEnumeration((AbstractType)type);
/*     */       } else {
/* 238 */         log("Class " + className + " exists. Not overriding.");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(LiteralListType type) throws Exception {
/* 245 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 248 */     type.getItemType().accept(this);
/* 249 */     registerType((AbstractType)type);
/*     */   }
/*     */ 
/*     */   
/*     */   public void visit(SOAPListType type) throws Exception {
/* 254 */     if (isRegistered((AbstractType)type)) {
/*     */       return;
/*     */     }
/* 257 */     type.getItemType().accept(this);
/* 258 */     registerType((AbstractType)type);
/*     */   }
/*     */   
/*     */   private boolean isRegistered(AbstractType type) {
/* 262 */     return this.types.contains(type);
/*     */   }
/*     */   
/*     */   private void registerType(AbstractType type) {
/* 266 */     this.types.add(type);
/*     */   }
/*     */   
/*     */   private void generateEnumeration(AbstractType type) {
/* 270 */     log("generating Enumeration for: " + type.getJavaType().getName());
/* 271 */     this.type = type;
/*     */     try {
/* 273 */       String className = type.getJavaType().getName();
/* 274 */       File classFile = this.env.getNames().sourceFileForClass(className, className, this.sourceDir, this.env);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 282 */       GeneratedFileInfo fi = new GeneratedFileInfo();
/* 283 */       fi.setFile(classFile);
/* 284 */       fi.setType("Enumeration");
/* 285 */       this.env.addGeneratedFile(fi);
/*     */       
/* 287 */       IndentingWriter out = new IndentingWriter(new OutputStreamWriter(new FileOutputStream(classFile)));
/*     */ 
/*     */       
/* 290 */       writePackage(out, className);
/* 291 */       out.pln();
/* 292 */       writeImports(out, (JavaEnumerationType)type.getJavaType());
/* 293 */       out.pln();
/* 294 */       writeClassDecl(out, className);
/* 295 */       writeMembers(out, (JavaEnumerationType)type.getJavaType());
/* 296 */       out.pln();
/* 297 */       writeClassConstructor(out, className, (JavaEnumerationType)type.getJavaType());
/*     */ 
/*     */ 
/*     */       
/* 301 */       out.pln();
/* 302 */       writeGetValue(out, (JavaEnumerationType)type.getJavaType());
/* 303 */       out.pln();
/* 304 */       writeFromValue(out, (JavaEnumerationType)type.getJavaType());
/* 305 */       out.pln();
/* 306 */       writeFromString(out, (JavaEnumerationType)type.getJavaType());
/* 307 */       out.pln();
/* 308 */       writeToString(out, (JavaEnumerationType)type.getJavaType());
/* 309 */       out.pln();
/* 310 */       writeResolveMethod(out, (JavaEnumerationType)type.getJavaType());
/* 311 */       out.pln();
/* 312 */       writeEquals(out, (JavaEnumerationType)type.getJavaType());
/* 313 */       out.pln();
/* 314 */       writeHashCode(out, (JavaEnumerationType)type.getJavaType());
/* 315 */       out.pOln("}");
/* 316 */       out.close();
/* 317 */     } catch (Exception e) {
/* 318 */       fail(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBaseTypeHexBinary() {
/* 326 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBaseTypeBase64Binary() {
/* 333 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeImports(IndentingWriter p, JavaEnumerationType javaEnum) throws IOException {
/* 342 */     String baseTypeStr = javaEnum.getBaseType().getName();
/* 343 */     p.pln("import java.util.Map;");
/* 344 */     p.pln("import java.util.HashMap;");
/* 345 */     if (isBaseArrayType(baseTypeStr)) {
/* 346 */       p.pln("import java.util.StringTokenizer;");
/* 347 */       p.pln("import java.util.Arrays;");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isBaseArrayType(String type) {
/* 353 */     return type.endsWith("[]");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeClassDecl(IndentingWriter p, String className) throws IOException {
/* 361 */     if (VersionUtil.isVersion101(this.targetVersion) || VersionUtil.isVersion103(this.targetVersion) || this.generateSerializableIf) {
/*     */ 
/*     */ 
/*     */       
/* 365 */       p.plnI("public class " + Names.stripQualifier(className) + " implements java.io.Serializable {");
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 370 */       writeClassDeclWithoutSerializable(p, className);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeClassDeclWithoutSerializable(IndentingWriter p, String className) throws IOException {
/* 378 */     p.plnI("public class " + Names.stripQualifier(className) + " {");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeMembers(IndentingWriter p, JavaEnumerationType javaEnum) throws IOException {
/* 383 */     String baseTypeStr = javaEnum.getBaseType().getName();
/* 384 */     String className = Names.stripQualifier(javaEnum.getName());
/* 385 */     p.pln("private " + baseTypeStr + " value;");
/* 386 */     p.pln("private static java.util.Map valueMap = new HashMap();");
/* 387 */     Iterator<JavaEnumerationEntry> enums = javaEnum.getEntries();
/*     */     
/* 389 */     if (!SimpleToBoxedUtil.isPrimitive(baseTypeStr)) {
/* 390 */       enums = javaEnum.getEntries();
/* 391 */       while (enums.hasNext()) {
/* 392 */         JavaEnumerationEntry entry = enums.next();
/* 393 */         p.pln("public static final java.lang.String _" + entry.getName() + "String = \"" + entry.getLiteralValue() + "\";");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 400 */       p.pln();
/*     */     } 
/* 402 */     enums = javaEnum.getEntries();
/* 403 */     while (enums.hasNext()) {
/* 404 */       JavaEnumerationEntry entry = enums.next();
/* 405 */       if (SimpleToBoxedUtil.isPrimitive(baseTypeStr)) {
/* 406 */         if (baseTypeStr.equals("long")) {
/* 407 */           p.pln("public static final " + baseTypeStr + " _" + entry.getName() + " = " + entry.getLiteralValue() + "L;");
/*     */ 
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 416 */         p.pln("public static final " + baseTypeStr + " _" + entry.getName() + " = (" + baseTypeStr + ")" + entry.getLiteralValue() + ";");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 428 */       if (baseTypeStr.equals("java.net.URI")) {
/* 429 */         p.pln("public static final " + baseTypeStr + " _" + entry.getName() + " = getURI(_" + entry.getName() + "String);");
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 437 */       if (baseTypeStr.equals("java.util.Calendar")) {
/* 438 */         Calendar val = null;
/* 439 */         if (this.type instanceof LiteralEnumerationType) {
/* 440 */           if (((LiteralEnumerationType)this.type).getBaseType().getName().equals(SchemaConstants.QNAME_TYPE_DATE_TIME) || ((LiteralEnumerationType)this.type).getBaseType().getName().equals(SchemaConstants.QNAME_TYPE_DATE)) {
/*     */ 
/*     */             
/*     */             try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 450 */               val = (Calendar)XSDDateTimeCalendarEncoder.getInstance().stringToObject(entry.getLiteralValue(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 458 */             catch (Exception e) {
/* 459 */               e.printStackTrace();
/*     */             } 
/* 461 */           } else if (((LiteralEnumerationType)this.type).getBaseType().getName().equals(SchemaConstants.QNAME_TYPE_TIME)) {
/*     */ 
/*     */             
/*     */             try {
/*     */ 
/*     */ 
/*     */               
/* 468 */               val = (Calendar)XSDTimeEncoder.getInstance().stringToObject(entry.getLiteralValue(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 474 */             catch (Exception e) {
/* 475 */               e.printStackTrace();
/*     */             }
/*     */           
/*     */           } 
/* 479 */         } else if (this.type instanceof SOAPEnumerationType) {
/* 480 */           if (((SOAPEnumerationType)this.type).getBaseType().getName().equals(SchemaConstants.QNAME_TYPE_DATE_TIME) || ((SOAPEnumerationType)this.type).getBaseType().getName().equals(SchemaConstants.QNAME_TYPE_DATE)) {
/*     */ 
/*     */             
/*     */             try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 490 */               val = (Calendar)XSDDateTimeCalendarEncoder.getInstance().stringToObject(entry.getLiteralValue(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 498 */             catch (Exception e) {
/* 499 */               e.printStackTrace();
/*     */             } 
/* 501 */           } else if (((SOAPEnumerationType)this.type).getBaseType().getName().equals(SchemaConstants.QNAME_TYPE_TIME)) {
/*     */ 
/*     */             
/*     */             try {
/*     */ 
/*     */ 
/*     */               
/* 508 */               val = (Calendar)XSDTimeEncoder.getInstance().stringToObject(entry.getLiteralValue(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 514 */             catch (Exception e) {
/* 515 */               e.printStackTrace();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 520 */         p.pln("public static final " + baseTypeStr + " _" + entry.getName() + " = getCalendar(" + val.getTimeInMillis() + "L);");
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 528 */       if (isBaseArrayType(baseTypeStr)) {
/* 529 */         byte[] val = null;
/* 530 */         String[] strVal = null;
/* 531 */         String str = null;
/*     */         
/* 533 */         if (this.type instanceof LiteralEnumerationType) {
/* 534 */           if (((LiteralEnumerationType)this.type).getBaseType().getName().equals(SchemaConstants.QNAME_TYPE_BASE64_BINARY)) {
/*     */ 
/*     */             
/*     */             try {
/*     */               
/* 539 */               val = (byte[])XSDBase64BinaryEncoder.getInstance().stringToObject(entry.getLiteralValue(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 545 */             catch (Exception e) {
/* 546 */               e.printStackTrace();
/*     */             } 
/* 548 */             str = "{" + getArrayInitializer(val) + "};";
/* 549 */           } else if (((LiteralEnumerationType)this.type).getBaseType().getName().equals(SchemaConstants.QNAME_TYPE_HEX_BINARY)) {
/*     */ 
/*     */             
/*     */             try {
/*     */ 
/*     */ 
/*     */               
/* 556 */               val = (byte[])XSDHexBinaryEncoder.getInstance().stringToObject(entry.getLiteralValue(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 562 */             catch (Exception e) {
/* 563 */               e.printStackTrace();
/*     */             } 
/* 565 */             str = "{" + getArrayInitializer(val) + "};";
/* 566 */           } else if (((LiteralEnumerationType)this.type).getBaseType().getName().equals(SchemaConstants.QNAME_TYPE_NMTOKENS)) {
/*     */ 
/*     */             
/*     */             try {
/*     */ 
/*     */ 
/*     */               
/* 573 */               strVal = (String[])XSDListTypeEncoder.getInstance(XSDStringEncoder.getInstance(), String.class).stringToObject(entry.getLiteralValue(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 581 */             catch (Exception e) {
/* 582 */               e.printStackTrace();
/*     */             } 
/* 584 */             str = "{" + getArrayInitializer(strVal) + "};";
/*     */           }
/*     */         
/* 587 */         } else if (this.type instanceof SOAPEnumerationType) {
/* 588 */           if (((SOAPEnumerationType)this.type).getBaseType().getName().equals(SchemaConstants.QNAME_TYPE_BASE64_BINARY)) {
/*     */ 
/*     */             
/*     */             try {
/*     */               
/* 593 */               val = (byte[])XSDBase64BinaryEncoder.getInstance().stringToObject(entry.getLiteralValue(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 599 */             catch (Exception e) {
/* 600 */               e.printStackTrace();
/*     */             } 
/* 602 */             str = "{" + getArrayInitializer(val) + "};";
/* 603 */           } else if (((SOAPEnumerationType)this.type).getBaseType().getName().equals(SchemaConstants.QNAME_TYPE_HEX_BINARY)) {
/*     */ 
/*     */             
/*     */             try {
/*     */ 
/*     */ 
/*     */               
/* 610 */               val = (byte[])XSDHexBinaryEncoder.getInstance().stringToObject(entry.getLiteralValue(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 616 */             catch (Exception e) {
/* 617 */               e.printStackTrace();
/*     */             } 
/* 619 */             str = "{" + getArrayInitializer(val) + "};";
/* 620 */           } else if (((SOAPEnumerationType)this.type).getBaseType().getName().equals(SchemaConstants.QNAME_TYPE_NMTOKENS)) {
/*     */ 
/*     */             
/*     */             try {
/*     */ 
/*     */ 
/*     */               
/* 627 */               strVal = (String[])XSDListTypeEncoder.getInstance(XSDStringEncoder.getInstance(), String.class).stringToObject(entry.getLiteralValue(), null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 635 */             catch (Exception e) {
/* 636 */               e.printStackTrace();
/*     */             } 
/* 638 */             str = "{" + getArrayInitializer(strVal) + "};";
/*     */           } 
/*     */         } 
/* 641 */         p.pln("public static final " + baseTypeStr + " _" + entry.getName() + " = new " + baseTypeStr + str);
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */       
/* 649 */       if (baseTypeStr.equals("javax.xml.namespace.QName")) {
/*     */         
/* 651 */         p.pln("public static final " + baseTypeStr + " _" + entry.getName() + " = javax.xml.namespace.QName.valueOf(\"" + entry.getValue().toString() + "\");");
/*     */ 
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 660 */       p.pln("public static final " + baseTypeStr + " _" + entry.getName() + " = new " + baseTypeStr + "(_" + entry.getName() + "String);");
/*     */     } 
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
/* 673 */     p.pln();
/* 674 */     enums = javaEnum.getEntries();
/* 675 */     while (enums.hasNext()) {
/* 676 */       JavaEnumerationEntry entry = enums.next();
/* 677 */       p.pln("public static final " + className + " " + entry.getName() + " = new " + className + "(_" + entry.getName() + ");");
/*     */     } 
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
/* 689 */     if (baseTypeStr.equals("java.net.URI")) {
/* 690 */       p.pln();
/* 691 */       p.plnI("private static java.net.URI getURI(String val) {");
/* 692 */       p.plnI("try {");
/* 693 */       p.pln("return new java.net.URI(val);");
/* 694 */       p.pOln("}catch(java.net.URISyntaxException e){e.printStackTrace();}");
/*     */       
/* 696 */       p.pln("return null;");
/* 697 */       p.pOln("}");
/*     */     } 
/*     */     
/* 700 */     if (baseTypeStr.equals("java.util.Calendar")) {
/* 701 */       p.pln();
/* 702 */       p.plnI("private static java.util.Calendar getCalendar(long val) {");
/* 703 */       p.pln("java.util.Calendar cal = new java.util.GregorianCalendar();");
/*     */       
/* 705 */       p.pln("cal.setTimeInMillis(val);");
/* 706 */       p.pln("return cal;");
/* 707 */       p.pOln("}");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String getArrayInitializer(Object obj) {
/* 716 */     if (null == obj)
/* 717 */       return null; 
/* 718 */     if (!obj.getClass().isArray())
/* 719 */       throw new IllegalArgumentException(); 
/* 720 */     StringBuffer ret = new StringBuffer();
/* 721 */     int len = Array.getLength(obj);
/* 722 */     if (len == 0)
/* 723 */       return ""; 
/* 724 */     for (int i = 0; i < len; i++) {
/* 725 */       if (i > 0)
/* 726 */         ret.append(", "); 
/* 727 */       if (obj instanceof String[]) {
/* 728 */         ret.append("\"" + Array.get(obj, i) + "\"");
/*     */       } else {
/* 730 */         ret.append(Array.get(obj, i));
/*     */       } 
/*     */     } 
/* 733 */     return ret.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeClassConstructor(IndentingWriter p, String className, JavaEnumerationType javaEnum) throws IOException {
/* 741 */     String baseTypeStr = javaEnum.getBaseType().getName();
/* 742 */     p.plnI("protected " + Names.stripQualifier(className) + "(" + baseTypeStr + " value) {");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 748 */     p.pln("this.value = value;");
/* 749 */     p.pln("valueMap.put(this.toString(), this);");
/* 750 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeGetValue(IndentingWriter p, JavaEnumerationType javaEnum) throws IOException {
/* 755 */     String baseTypeStr = javaEnum.getBaseType().getName();
/* 756 */     p.plnI("public " + baseTypeStr + " getValue() {");
/* 757 */     p.pln("return value;");
/* 758 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeFromValue(IndentingWriter p, JavaEnumerationType javaEnum) throws IOException {
/* 765 */     String baseTypeStr = javaEnum.getBaseType().getName();
/* 766 */     String className = Names.stripQualifier(javaEnum.getName());
/* 767 */     p.plnI("public static " + className + " fromValue(" + baseTypeStr + " value)");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 773 */     p.pln("throws java.lang.IllegalStateException {");
/* 774 */     Iterator<JavaEnumerationEntry> enums = javaEnum.getEntries();
/*     */     
/* 776 */     for (int i = 0; enums.hasNext(); i++) {
/* 777 */       JavaEnumerationEntry entry = enums.next();
/* 778 */       if (i > 0)
/* 779 */         p.p(" else "); 
/* 780 */       if (SimpleToBoxedUtil.isPrimitive(baseTypeStr)) {
/* 781 */         p.plnI("if (" + entry.getName() + ".value == value) {");
/*     */       }
/* 783 */       else if (isBaseArrayType(baseTypeStr)) {
/* 784 */         p.plnI("if (java.util.Arrays.equals(" + entry.getName() + ".value, value)) {");
/*     */ 
/*     */       
/*     */       }
/* 788 */       else if (baseTypeStr.equals("java.util.Calendar")) {
/* 789 */         p.plnI("if (" + entry.getName() + ".value.getTimeInMillis() == value.getTimeInMillis()) {");
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 794 */         p.plnI("if (" + entry.getName() + ".value.equals(value)) {");
/*     */       } 
/*     */ 
/*     */       
/* 798 */       p.pln("return " + entry.getName() + ";");
/* 799 */       p.pO("}");
/*     */     } 
/* 801 */     p.pln();
/* 802 */     p.pln("throw new java.lang.IllegalArgumentException();");
/* 803 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeFromString(IndentingWriter p, JavaEnumerationType javaEnum) throws IOException {
/* 810 */     String baseTypeStr = javaEnum.getBaseType().getName();
/* 811 */     String className = Names.stripQualifier(javaEnum.getName());
/* 812 */     p.plnI("public static " + className + " fromString(java.lang.String value)");
/* 813 */     p.pln("throws java.lang.IllegalStateException {");
/*     */     
/* 815 */     p.pln(className + " ret = (" + className + ")valueMap.get(value);");
/* 816 */     p.plnI("if (ret != null) {");
/* 817 */     p.pln("return ret;");
/* 818 */     p.pOln("}");
/*     */     
/* 820 */     Iterator<JavaEnumerationEntry> enums = javaEnum.getEntries();
/*     */     
/* 822 */     for (int i = 0; enums.hasNext(); i++) {
/* 823 */       JavaEnumerationEntry entry = enums.next();
/* 824 */       if (i > 0)
/* 825 */         p.p(" else "); 
/* 826 */       if (SimpleToBoxedUtil.isPrimitive(baseTypeStr)) {
/* 827 */         p.plnI("if (value.equals(\"" + entry.getLiteralValue() + "\")) {");
/*     */       
/*     */       }
/* 830 */       else if (isBaseArrayType(baseTypeStr) || baseTypeStr.equals("java.util.Calendar")) {
/*     */         
/* 832 */         p.plnI("if (value.equals(_" + entry.getName() + ".toString())) {");
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 837 */         p.plnI("if (value.equals(_" + entry.getName() + "String)) {");
/*     */       } 
/*     */ 
/*     */       
/* 841 */       p.pln("return " + entry.getName() + ";");
/* 842 */       p.pO("}");
/*     */     } 
/* 844 */     p.pln();
/* 845 */     p.pln("throw new IllegalArgumentException();");
/* 846 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeToString(IndentingWriter p, JavaEnumerationType javaEnum) throws IOException {
/* 851 */     String baseTypeStr = javaEnum.getBaseType().getName();
/* 852 */     String className = Names.stripQualifier(javaEnum.getName());
/* 853 */     p.plnI("public java.lang.String toString() {");
/* 854 */     String exp = "value";
/* 855 */     if (SimpleToBoxedUtil.isPrimitive(baseTypeStr)) {
/* 856 */       exp = SimpleToBoxedUtil.getBoxedExpressionOfType(exp, baseTypeStr);
/*     */     }
/* 858 */     p.pln("return " + exp + ".toString();");
/* 859 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeResolveMethod(IndentingWriter p, JavaEnumerationType javaEnum) throws IOException {
/* 866 */     String baseTypeStr = javaEnum.getBaseType().getName();
/* 867 */     String className = Names.stripQualifier(javaEnum.getName());
/* 868 */     p.plnI("private java.lang.Object readResolve()");
/* 869 */     p.pln("throws java.io.ObjectStreamException {");
/* 870 */     p.pln("return fromValue(getValue());");
/* 871 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeEquals(IndentingWriter p, JavaEnumerationType javaEnum) throws IOException {
/* 876 */     String baseTypeStr = javaEnum.getBaseType().getName();
/* 877 */     String className = Names.stripQualifier(javaEnum.getName());
/* 878 */     p.plnI("public boolean equals(java.lang.Object obj) {");
/* 879 */     p.plnI("if (!(obj instanceof " + className + ")) {");
/* 880 */     p.pln("return false;");
/* 881 */     p.pOln("}");
/* 882 */     if (SimpleToBoxedUtil.isPrimitive(baseTypeStr)) {
/* 883 */       p.pln("return ((" + className + ")obj).value == value;");
/*     */     } else {
/* 885 */       p.pln("return ((" + className + ")obj).value.equals(value);");
/*     */     } 
/* 887 */     p.pOln("}");
/*     */   }
/*     */ 
/*     */   
/*     */   private void writeHashCode(IndentingWriter p, JavaEnumerationType javaEnum) throws IOException {
/* 892 */     String baseTypeStr = javaEnum.getBaseType().getName();
/* 893 */     p.plnI("public int hashCode() {");
/* 894 */     if (SimpleToBoxedUtil.isPrimitive(baseTypeStr)) {
/* 895 */       String boxedExp = SimpleToBoxedUtil.getBoxedExpressionOfType("value", baseTypeStr);
/*     */ 
/*     */ 
/*     */       
/* 899 */       p.pln("return " + boxedExp + ".toString().hashCode();");
/*     */     } else {
/* 901 */       p.pln("return value.hashCode();");
/*     */     } 
/* 903 */     p.pOln("}");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\EnumerationGenerator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */