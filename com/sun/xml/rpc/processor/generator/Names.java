/*     */ package com.sun.xml.rpc.processor.generator;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.model.AbstractType;
/*     */ import com.sun.xml.rpc.processor.model.Block;
/*     */ import com.sun.xml.rpc.processor.model.Fault;
/*     */ import com.sun.xml.rpc.processor.model.Operation;
/*     */ import com.sun.xml.rpc.processor.model.Port;
/*     */ import com.sun.xml.rpc.processor.model.Service;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaArrayType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaInterface;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralFragmentType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPCustomType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.util.DirectoryUtil;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.processor.util.StringUtils;
/*     */ import com.sun.xml.rpc.spi.model.JavaInterface;
/*     */ import com.sun.xml.rpc.spi.model.Port;
/*     */ import com.sun.xml.rpc.spi.tools.Names;
/*     */ import com.sun.xml.rpc.streaming.PrefixFactory;
/*     */ import com.sun.xml.rpc.streaming.PrefixFactoryImpl;
/*     */ import com.sun.xml.rpc.util.ClassNameInfo;
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Names
/*     */   implements Names, GeneratorConstants
/*     */ {
/*     */   public String stubFor(Port port) {
/*  73 */     return stubFor((Port)port, null);
/*     */   }
/*     */   
/*     */   public String stubFor(Port port, String infix) {
/*  77 */     String result = (String)port.getProperty("com.sun.xml.rpc.processor.model.StubClassName");
/*     */     
/*  79 */     if (result == null) {
/*  80 */       result = makeDerivedClassName(port.getJavaInterface(), "_Stub", infix);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String skeletonFor(JavaInterface javaInterface) {
/*  93 */     String name = ClassNameInfo.replaceInnerClassSym(javaInterface.getRealName());
/*     */     
/*  95 */     return name + "_Skeleton";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String tieFor(Port port) {
/* 102 */     return tieFor(port, this.serializerNameInfix);
/*     */   }
/*     */   
/*     */   public String tieFor(Port port, String infix) {
/* 106 */     String result = (String)port.getProperty("com.sun.xml.rpc.processor.model.TieClassName");
/*     */     
/* 108 */     if (result == null) {
/* 109 */       result = makeDerivedClassName(port.getJavaInterface(), "_Tie", infix);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeDerivedClassName(JavaInterface javaInterface, String suffix, String infix) {
/* 122 */     String name = ClassNameInfo.replaceInnerClassSym(javaInterface.getRealName());
/*     */     
/* 124 */     return name + ((infix == null) ? "" : ("_" + infix)) + suffix;
/*     */   }
/*     */   
/*     */   public static String getPortName(Port port) {
/* 128 */     String javaPortName = (String)port.getProperty("com.sun.xml.rpc.processor.model.JavaPortName");
/*     */     
/* 130 */     if (javaPortName != null) {
/* 131 */       return javaPortName;
/*     */     }
/* 133 */     QName portName = (QName)port.getProperty("com.sun.xml.rpc.processor.model.WSDLPortName");
/*     */ 
/*     */     
/* 136 */     if (portName != null) {
/* 137 */       return portName.getLocalPart();
/*     */     }
/* 139 */     String name = stripQualifier(port.getJavaInterface().getName());
/* 140 */     return ClassNameInfo.replaceInnerClassSym(name);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String stripQualifier(Class classObj) {
/* 146 */     String name = classObj.getName();
/* 147 */     return stripQualifier(name);
/*     */   }
/*     */   
/*     */   public static String stripQualifier(String name) {
/* 151 */     return ClassNameInfo.getName(name);
/*     */   }
/*     */   
/*     */   public static String getPackageName(String className) {
/* 155 */     String packageName = ClassNameInfo.getQualifier(className);
/* 156 */     return (packageName != null) ? packageName : "";
/*     */   }
/*     */   
/*     */   public static String getUnqualifiedClassName(String className) {
/* 160 */     return ClassNameInfo.getName(className).replace('$', '.');
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
/*     */   public File sourceFileForClass(String className, String outputClassName, File destDir, ProcessorEnvironment env) throws GeneratorException {
/* 174 */     File packageDir = DirectoryUtil.getOutputDirectoryFor(className, destDir, env);
/*     */     
/* 176 */     String outputName = stripQualifier(outputClassName);
/*     */     
/* 178 */     String outputFileName = outputName + ".java";
/* 179 */     return new File(packageDir, outputFileName);
/*     */   }
/*     */   
/*     */   public String typeClassName(SOAPType type) {
/* 183 */     return typeClassName(type.getJavaType());
/*     */   }
/*     */   
/*     */   public String typeClassName(JavaType type) {
/* 187 */     String typeName = type.getName();
/* 188 */     return typeName;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String typeObjectSerializerClassName(String basePackage, SOAPType type) {
/* 194 */     return typeObjectSerializerClassName(basePackage, type.getJavaType(), "_SOAPSerializer");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String typeObjectArraySerializerClassName(String basePackage, SOAPType type) {
/* 203 */     return typeObjectArraySerializerClassName(basePackage, type.getJavaType(), "Array_SOAPSerializer");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String typeObjectSerializerClassName(String basePackage, LiteralType type) {
/* 212 */     return typeObjectSerializerClassName(basePackage, type.getJavaType(), "_LiteralSerializer");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String typeObjectArraySerializerClassName(String basePackage, LiteralType type) {
/* 221 */     return typeObjectArraySerializerClassName(basePackage, type.getJavaType(), "Array_LiteralSerializer");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String typeObjectSerializerClassName(String basePackage, JavaType type, String suffix) {
/* 231 */     String typeName = type.getRealName();
/* 232 */     return serializerClassName(basePackage, typeName, suffix);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String typeInterfaceSerializerClassName(String basePackage, AbstractType type) {
/* 238 */     return typeInterfaceSerializerClassName(basePackage, type.getJavaType(), "_InterfaceSOAPSerializer");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String typeInterfaceSerializerClassName(String basePackage, JavaType type, String suffix) {
/* 248 */     String typeName = type.getRealName();
/* 249 */     return serializerClassName(basePackage, typeName, suffix);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String serializerClassName(String basePackage, String className, String suffix) {
/* 256 */     if (this.serializerNameInfix != null)
/* 257 */       className = className + this.serializerNameInfix; 
/* 258 */     return (className + suffix).replace('$', '_');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String typeObjectArraySerializerClassName(String basePackage, JavaType type, String suffix) {
/* 265 */     String typeName = type.getRealName();
/* 266 */     int idx = typeName.indexOf("[]");
/* 267 */     if (idx > 0) {
/* 268 */       typeName = typeName.substring(0, idx);
/*     */     }
/* 270 */     return serializerClassName(basePackage, typeName, suffix);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String typeObjectBuilderClassName(String basePackage, SOAPType type) {
/* 276 */     return typeObjectBuilderClassName(basePackage, type.getJavaType());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String typeObjectBuilderClassName(String basePackage, JavaType type) {
/* 282 */     return builderClassName(basePackage, type.getRealName(), "_SOAPBuilder");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String builderClassName(String basePackage, String className, String suffix) {
/* 292 */     if (this.serializerNameInfix != null)
/* 293 */       className = className + this.serializerNameInfix; 
/* 294 */     return (className + suffix).replace('$', '_');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String faultBuilderClassName(String basePackage, Port port, Operation operation) {
/* 301 */     String typeName = port.getJavaInterface().getName() + "_" + validExternalJavaIdentifier(operation.getUniqueName());
/*     */ 
/*     */ 
/*     */     
/* 305 */     return builderClassName(basePackage, typeName, "_Fault_SOAPBuilder");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String faultSerializerClassName(String basePackage, Port port, Operation operation) {
/* 312 */     String name = port.getJavaInterface().getName() + "_" + validExternalJavaIdentifier(operation.getUniqueName());
/*     */ 
/*     */ 
/*     */     
/* 316 */     return serializerClassName(basePackage, name, "_Fault_SOAPSerializer");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPackageName(Service service) {
/* 323 */     String portPackage = getPackageName(service.getJavaInterface().getName());
/*     */     
/* 325 */     return portPackage;
/*     */   }
/*     */   
/*     */   public String customJavaTypeClassName(JavaInterface intf) {
/* 329 */     String intName = intf.getName();
/* 330 */     return intName;
/*     */   }
/*     */   
/*     */   public String customJavaTypeClassName(AbstractType type) {
/* 334 */     String typeName = type.getJavaType().getName();
/* 335 */     return typeName;
/*     */   }
/*     */   
/*     */   private String customJavaTypeClassName(String typeName) {
/* 339 */     return typeName;
/*     */   }
/*     */   
/*     */   public String customExceptionClassName(Fault fault) {
/* 343 */     String typeName = fault.getJavaException().getName();
/* 344 */     return typeName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String interfaceImplClassName(JavaInterface intf) {
/* 349 */     String intName = intf.getName() + "_Impl";
/* 350 */     return intName;
/*     */   }
/*     */   
/*     */   public String serializerRegistryClassName(JavaInterface intf) {
/* 354 */     String intName = intf.getName() + "_SerializerRegistry";
/* 355 */     return intName;
/*     */   }
/*     */   
/*     */   public String holderClassName(Port port, AbstractType type) {
/* 359 */     return holderClassName(port, type.getJavaType());
/*     */   }
/*     */   
/*     */   public String holderClassName(Port port, JavaType type) {
/* 363 */     if (type.getHolderName() != null) {
/* 364 */       return type.getHolderName();
/*     */     }
/* 366 */     String typeName = type.getName();
/* 367 */     if (type instanceof JavaArrayType && (
/* 368 */       (JavaArrayType)type).getSOAPArrayHolderName() != null) {
/* 369 */       typeName = ((JavaArrayType)type).getSOAPArrayHolderName();
/*     */     }
/* 371 */     return holderClassName(port, typeName);
/*     */   }
/*     */   
/*     */   protected String holderClassName(Port port, String typeName) {
/* 375 */     String holderTypeName = (String)holderClassNames.get(typeName);
/* 376 */     if (holderTypeName == null) {
/*     */       
/* 378 */       String className = port.getJavaInterface().getName();
/* 379 */       String packageName = getPackageName(className);
/* 380 */       if (packageName.length() > 0) {
/* 381 */         packageName = packageName + ".holders.";
/*     */       } else {
/* 383 */         packageName = "holders.";
/*     */       } 
/* 385 */       typeName = stripQualifier(typeName);
/* 386 */       int idx = typeName.indexOf("[]");
/* 387 */       while (idx > 0) {
/*     */         
/* 389 */         typeName = typeName.substring(0, idx) + "Array" + typeName.substring(idx + 2);
/*     */ 
/*     */ 
/*     */         
/* 393 */         idx = typeName.indexOf("[]");
/*     */       } 
/*     */       
/* 396 */       holderTypeName = packageName + validJavaClassName(typeName) + "Holder";
/*     */     } 
/*     */     
/* 399 */     return holderTypeName;
/*     */   }
/*     */   
/*     */   public static boolean isInJavaOrJavaxPackage(String typeName) {
/* 403 */     return (typeName.startsWith("java.") || typeName.startsWith("javax."));
/*     */   }
/*     */ 
/*     */   
/*     */   public String memberName(String name) {
/* 408 */     return ("my" + name).replace('.', '$');
/*     */   }
/*     */   
/*     */   public String getClassMemberName(String className) {
/* 412 */     className = getUnqualifiedClassName(className);
/* 413 */     return memberName(className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassMemberName(String className, AbstractType type, String suffix) {
/* 420 */     className = getUnqualifiedClassName(className);
/* 421 */     String additionalClassName = type.getJavaType().getName().replace('.', '_');
/*     */     
/* 423 */     int idx = additionalClassName.indexOf('[');
/* 424 */     if (idx > 0)
/* 425 */       additionalClassName = additionalClassName.substring(0, idx); 
/* 426 */     return memberName(getPrefix(type.getName()) + "_" + validJavaName(type.getName().getLocalPart()) + "__" + additionalClassName + "_" + className + suffix);
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
/*     */   public String getClassMemberName(String className, AbstractType type) {
/* 438 */     className = getUnqualifiedClassName(className);
/* 439 */     return getClassMemberName(getPrefix(type.getName()) + "_" + validJavaName(type.getName().getLocalPart()) + "__" + className);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTypeMemberName(AbstractType type) {
/* 448 */     return getTypeMemberName(type.getJavaType());
/*     */   }
/*     */   
/*     */   public String getTypeMemberName(JavaType javaType) {
/* 452 */     String typeName = javaType.getRealName();
/* 453 */     return getTypeMemberName(typeName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTypeMemberName(String typeName) {
/* 461 */     typeName = getUnqualifiedClassName(typeName);
/* 462 */     int i = 0;
/* 463 */     while (typeName.endsWith("[]")) {
/* 464 */       typeName = typeName.substring(0, typeName.length() - 2);
/* 465 */       i++;
/*     */     } 
/* 467 */     for (; i < 0; i--) {
/* 468 */       typeName = typeName + "Array";
/*     */     }
/* 470 */     return memberName(typeName);
/*     */   }
/*     */   
/*     */   public String getCustomTypeSerializerMemberName(SOAPCustomType type) {
/* 474 */     return getTypeQName(type.getName()) + "_Serializer";
/*     */   }
/*     */   
/*     */   public String getCustomTypeDeserializerMemberName(SOAPCustomType type) {
/* 478 */     return getTypeQName(type.getName()) + "_Deserializer";
/*     */   }
/*     */   
/*     */   public String getLiteralFragmentTypeSerializerMemberName(LiteralFragmentType type) {
/* 482 */     return getTypeQName(type.getName()) + "_Serializer";
/*     */   }
/*     */   
/*     */   public String getOPCodeName(String name) {
/* 486 */     String qname = name + "_OPCODE";
/* 487 */     return validInternalJavaIdentifier(qname);
/*     */   }
/*     */   
/*     */   public String getQNameName(QName name) {
/* 491 */     String qname = getPrefix(name) + "_" + name.getLocalPart() + "_QNAME";
/*     */     
/* 493 */     return validInternalJavaIdentifier(qname);
/*     */   }
/*     */   
/*     */   public String getBlockQNameName(Operation operation, Block block) {
/* 497 */     QName blockName = block.getName();
/* 498 */     String qname = getPrefix(blockName);
/* 499 */     if (operation != null)
/* 500 */       qname = qname + "_" + operation.getUniqueName(); 
/* 501 */     qname = qname + "_" + blockName.getLocalPart() + "_QNAME";
/* 502 */     return validInternalJavaIdentifier(qname);
/*     */   }
/*     */   
/*     */   public void setJavaStructureMemberMethodNames(JavaStructureMember javaMember) {
/* 506 */     javaMember.setReadMethod(getJavaMemberReadMethod(javaMember));
/* 507 */     javaMember.setWriteMethod(getJavaMemberWriteMethod(javaMember));
/*     */   }
/*     */   
/*     */   public String getBlockUniqueName(Operation operation, Block block) {
/* 511 */     QName blockName = block.getName();
/* 512 */     String qname = getPrefix(blockName);
/* 513 */     if (operation != null)
/* 514 */       qname = qname + "_" + operation.getUniqueName(); 
/* 515 */     qname = qname + "_" + blockName.getLocalPart();
/* 516 */     return validInternalJavaIdentifier(qname);
/*     */   }
/*     */   
/*     */   public String getTypeQName(QName name) {
/* 520 */     String qname = getPrefix(name) + "_" + name.getLocalPart() + "_TYPE_QNAME";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 525 */     return validInternalJavaIdentifier(qname);
/*     */   }
/*     */   
/*     */   public String validJavaClassName(String name) {
/* 529 */     return validJavaName(StringUtils.capitalize(name));
/*     */   }
/*     */   
/*     */   public String validJavaMemberName(String name) {
/* 533 */     return validJavaName(StringUtils.decapitalize(name));
/*     */   }
/*     */   
/*     */   public String validJavaPackageName(String name) {
/* 537 */     return validJavaName(StringUtils.decapitalize(name));
/*     */   }
/*     */   
/*     */   public String getIDObjectResolverName(String name) {
/* 541 */     return validJavaClassName(name) + "IDObjectResolver";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String validInternalJavaIdentifier(String name) {
/* 548 */     StringBuffer sb = new StringBuffer();
/* 549 */     for (int i = 0; i < name.length(); i++) {
/* 550 */       char ch = name.charAt(i);
/* 551 */       if (i == 0) {
/* 552 */         if (Character.isJavaIdentifierStart(ch)) {
/* 553 */           sb.append(ch);
/*     */         } else {
/* 555 */           sb.append("_$");
/* 556 */           sb.append(Integer.toHexString(ch));
/* 557 */           sb.append("$");
/*     */         }
/*     */       
/* 560 */       } else if (Character.isJavaIdentifierPart(ch)) {
/* 561 */         sb.append(ch);
/*     */       } else {
/* 563 */         sb.append("$");
/* 564 */         sb.append(Integer.toHexString(ch));
/* 565 */         sb.append("$");
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 570 */     String id = sb.toString();
/*     */     
/* 572 */     String tmp = (String)reservedWords.get(id);
/* 573 */     if (tmp != null)
/* 574 */       id = tmp; 
/* 575 */     return id;
/*     */   }
/*     */   
/*     */   public String validExternalJavaIdentifier(String name) {
/* 579 */     return validInternalJavaIdentifier(name).replace('$', '_');
/*     */   }
/*     */   
/*     */   public String validJavaName(String name) {
/* 583 */     name = wordBreakString(name);
/* 584 */     name = removeWhiteSpace(name);
/*     */     
/* 586 */     String tmp = (String)reservedWords.get(name);
/* 587 */     if (tmp != null)
/* 588 */       name = tmp; 
/* 589 */     return name;
/*     */   }
/*     */   
/*     */   public boolean isJavaReservedWord(String name) {
/* 593 */     return (reservedWords.get(name) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getJavaMemberReadMethod(JavaStructureMember member) {
/* 601 */     String return_value = null;
/* 602 */     if (member.getType().getRealName() == "boolean") {
/* 603 */       return_value = "is" + StringUtils.capitalize(member.getName());
/*     */     } else {
/* 605 */       return_value = "get" + StringUtils.capitalize(member.getName());
/*     */     } 
/* 607 */     return return_value;
/*     */   }
/*     */   
/*     */   public String getJavaMemberWriteMethod(JavaStructureMember member) {
/* 611 */     return "set" + StringUtils.capitalize(member.getName());
/*     */   }
/*     */   
/*     */   public String getResponseName(String messageName) {
/* 615 */     return messageName + "Response";
/*     */   }
/*     */   
/*     */   public String removeWhiteSpace(String str) {
/* 619 */     String tmp = removeCharacter(32, str);
/* 620 */     return tmp;
/*     */   }
/*     */   
/*     */   public String wordBreakString(String str) {
/* 624 */     StringBuffer buf = new StringBuffer(str);
/*     */     
/* 626 */     for (int i = 0; i < buf.length(); i++) {
/* 627 */       char ch = buf.charAt(i);
/* 628 */       if (Character.isDigit(ch)) {
/* 629 */         if (i + 1 < buf.length() && !Character.isDigit(buf.charAt(i + 1)))
/*     */         {
/* 631 */           buf.insert(1 + i++, ' ');
/*     */         }
/* 633 */       } else if (!Character.isSpaceChar(ch) && ch != '_') {
/*     */         
/* 635 */         if (!Character.isJavaIdentifierPart(ch)) {
/* 636 */           buf.setCharAt(i, ' ');
/* 637 */         } else if (!Character.isLetter(ch)) {
/* 638 */           buf.setCharAt(i, ' ');
/*     */         } 
/*     */       } 
/* 641 */     }  return buf.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String removeCharacter(int ch, String str) {
/* 646 */     int idx = str.indexOf(ch);
/* 647 */     while (idx >= 0) {
/* 648 */       str = str.substring(0, idx) + StringUtils.capitalize(str.substring(idx + 1).trim());
/*     */ 
/*     */       
/* 651 */       idx = str.indexOf(' ');
/*     */     } 
/*     */     
/* 654 */     return str;
/*     */   }
/*     */   
/*     */   public String getPrefix(QName name) {
/* 658 */     return getPrefix(name.getNamespaceURI());
/*     */   }
/*     */   
/*     */   public String getPrefix(String uri) {
/* 662 */     return this.prefixFactory.getPrefix(uri);
/*     */   }
/*     */   
/*     */   public void resetPrefixFactory() {
/* 666 */     this.prefixFactory = (PrefixFactory)new PrefixFactoryImpl("ns");
/*     */   }
/*     */   
/*     */   public void setSerializerNameInfix(String serNameInfix) {
/* 670 */     if (serNameInfix != null && serNameInfix.length() > 0) {
/* 671 */       this.serializerNameInfix = "_" + serNameInfix;
/*     */     }
/*     */   }
/*     */   
/*     */   public String getSerializerNameInfix() {
/* 676 */     String str = this.serializerNameInfix;
/* 677 */     if (this.serializerNameInfix != null && this.serializerNameInfix.charAt(0) == '_')
/*     */     {
/* 679 */       str = this.serializerNameInfix.substring(1); } 
/* 680 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getAdjustedURI(String namespaceURI, String pkgName) {
/* 685 */     if (pkgName == null)
/* 686 */       return namespaceURI; 
/* 687 */     if (namespaceURI == null) {
/* 688 */       return pkgName;
/*     */     }
/* 690 */     int length = namespaceURI.length();
/* 691 */     int i = namespaceURI.lastIndexOf('/');
/* 692 */     if (i == -1) {
/*     */       
/* 694 */       i = namespaceURI.lastIndexOf(':');
/* 695 */       if (i == -1)
/*     */       {
/* 697 */         return pkgName;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 702 */     if (i != -1 && i + 1 == length) {
/* 703 */       return namespaceURI + pkgName;
/*     */     }
/* 705 */     int j = namespaceURI.indexOf('.', i);
/* 706 */     if (j != -1) {
/* 707 */       return namespaceURI.substring(0, i + 1) + pkgName;
/*     */     }
/* 709 */     return namespaceURI + "/" + pkgName;
/*     */   }
/*     */ 
/*     */   
/* 713 */   protected String serializerNameInfix = null;
/* 714 */   protected PrefixFactory prefixFactory = (PrefixFactory)new PrefixFactoryImpl("ns");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 719 */   protected static Map reservedWords = new HashMap<Object, Object>(); static {
/* 720 */     reservedWords.put("abstract", "_abstract");
/* 721 */     reservedWords.put("assert", "_assert");
/* 722 */     reservedWords.put("boolean", "_boolean");
/* 723 */     reservedWords.put("break", "_break");
/* 724 */     reservedWords.put("byte", "_byte");
/* 725 */     reservedWords.put("case", "_case");
/* 726 */     reservedWords.put("catch", "_catch");
/* 727 */     reservedWords.put("char", "_char");
/* 728 */     reservedWords.put("class", "_class");
/* 729 */     reservedWords.put("const", "_const");
/* 730 */     reservedWords.put("continue", "_continue");
/* 731 */     reservedWords.put("default", "_default");
/* 732 */     reservedWords.put("do", "_do");
/* 733 */     reservedWords.put("double", "_double");
/* 734 */     reservedWords.put("else", "_else");
/* 735 */     reservedWords.put("extends", "_extends");
/* 736 */     reservedWords.put("false", "_false");
/* 737 */     reservedWords.put("final", "_final");
/* 738 */     reservedWords.put("finally", "_finally");
/* 739 */     reservedWords.put("float", "_float");
/* 740 */     reservedWords.put("for", "_for");
/* 741 */     reservedWords.put("goto", "_goto");
/* 742 */     reservedWords.put("if", "_if");
/* 743 */     reservedWords.put("implements", "_implements");
/* 744 */     reservedWords.put("import", "_import");
/* 745 */     reservedWords.put("instanceof", "_instanceof");
/* 746 */     reservedWords.put("int", "_int");
/* 747 */     reservedWords.put("interface", "_interface");
/* 748 */     reservedWords.put("long", "_long");
/* 749 */     reservedWords.put("native", "_native");
/* 750 */     reservedWords.put("new", "_new");
/* 751 */     reservedWords.put("null", "_null");
/* 752 */     reservedWords.put("package", "_package");
/* 753 */     reservedWords.put("private", "_private");
/* 754 */     reservedWords.put("protected", "_protected");
/* 755 */     reservedWords.put("public", "_public");
/* 756 */     reservedWords.put("return", "_return");
/* 757 */     reservedWords.put("short", "_short");
/* 758 */     reservedWords.put("static", "_static");
/* 759 */     reservedWords.put("strictfp", "_strictfp");
/* 760 */     reservedWords.put("super", "_super");
/* 761 */     reservedWords.put("switch", "_switch");
/* 762 */     reservedWords.put("synchronized", "_synchronized");
/* 763 */     reservedWords.put("this", "_this");
/* 764 */     reservedWords.put("throw", "_throw");
/* 765 */     reservedWords.put("throws", "_throws");
/* 766 */     reservedWords.put("transient", "_transient");
/* 767 */     reservedWords.put("true", "_true");
/* 768 */     reservedWords.put("try", "_try");
/* 769 */     reservedWords.put("void", "_void");
/* 770 */     reservedWords.put("volatile", "_volatile");
/* 771 */     reservedWords.put("while", "_while");
/*     */   }
/* 773 */   protected static Map holderClassNames = new HashMap<Object, Object>(); static {
/* 774 */     holderClassNames.put("int", "javax.xml.rpc.holders.IntHolder");
/* 775 */     holderClassNames.put("long", "javax.xml.rpc.holders.LongHolder");
/* 776 */     holderClassNames.put("short", "javax.xml.rpc.holders.ShortHolder");
/* 777 */     holderClassNames.put("float", "javax.xml.rpc.holders.FloatHolder");
/* 778 */     holderClassNames.put("double", "javax.xml.rpc.holders.DoubleHolder");
/* 779 */     holderClassNames.put("boolean", "javax.xml.rpc.holders.BooleanHolder");
/* 780 */     holderClassNames.put("byte", "javax.xml.rpc.holders.ByteHolder");
/* 781 */     holderClassNames.put("java.lang.Integer", "javax.xml.rpc.holders.IntegerWrapperHolder");
/*     */ 
/*     */     
/* 784 */     holderClassNames.put("java.lang.Long", "javax.xml.rpc.holders.LongWrapperHolder");
/*     */ 
/*     */     
/* 787 */     holderClassNames.put("java.lang.Short", "javax.xml.rpc.holders.ShortWrapperHolder");
/*     */ 
/*     */     
/* 790 */     holderClassNames.put("java.lang.Float", "javax.xml.rpc.holders.FloatWrapperHolder");
/*     */ 
/*     */     
/* 793 */     holderClassNames.put("java.lang.Double", "javax.xml.rpc.holders.DoubleWrapperHolder");
/*     */ 
/*     */     
/* 796 */     holderClassNames.put("java.lang.Boolean", "javax.xml.rpc.holders.BooleanWrapperHolder");
/*     */ 
/*     */     
/* 799 */     holderClassNames.put("java.lang.Byte", "javax.xml.rpc.holders.ByteWrapperHolder");
/*     */ 
/*     */     
/* 802 */     holderClassNames.put("java.lang.String", "javax.xml.rpc.holders.StringHolder");
/*     */ 
/*     */     
/* 805 */     holderClassNames.put("java.math.BigDecimal", "javax.xml.rpc.holders.BigDecimalHolder");
/*     */ 
/*     */     
/* 808 */     holderClassNames.put("java.math.BigInteger", "javax.xml.rpc.holders.BigIntegerHolder");
/*     */ 
/*     */     
/* 811 */     holderClassNames.put("java.util.Calendar", "javax.xml.rpc.holders.CalendarHolder");
/*     */ 
/*     */     
/* 814 */     holderClassNames.put("javax.xml.namespace.QName", "javax.xml.rpc.holders.QNameHolder");
/*     */ 
/*     */     
/* 817 */     holderClassNames.put("java.lang.Object", "javax.xml.rpc.holders.ObjectHolder");
/*     */ 
/*     */     
/* 820 */     holderClassNames.put("byte[]", "javax.xml.rpc.holders.ByteArrayHolder");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\generator\Names.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */