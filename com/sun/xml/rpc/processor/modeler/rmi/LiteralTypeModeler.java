/*     */ package com.sun.xml.rpc.processor.modeler.rmi;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.InternalEncodingConstants;
/*     */ import com.sun.xml.rpc.processor.config.TypeMappingRegistryInfo;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaArrayType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralArrayWrapperType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralAttributeMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralElementMember;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralSequenceType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralStructuredType;
/*     */ import com.sun.xml.rpc.processor.model.literal.LiteralType;
/*     */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.util.ClassNameInfo;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class LiteralTypeModeler
/*     */   implements RmiConstants
/*     */ {
/*     */   private Map simpleTypeMap;
/*     */   private Map complexTypeMap;
/*     */   private Set typeNames;
/*     */   private RmiModeler modeler;
/*     */   private ProcessorEnvironment env;
/*     */   private TypeMappingRegistryInfo typeMappingRegistry;
/*     */   private LiteralSimpleTypeCreator literalTypes;
/*     */   
/*     */   public LiteralElementMember modelTypeLiteral(QName elemName, String typeUri, RmiType type) {
/*  69 */     return modelTypeLiteral(elemName, typeUri, type, false, false); } public LiteralElementMember modelTypeLiteral(QName elemName, String typeUri, RmiType type, boolean topLevel, boolean allowHolders) { RmiType elemRmiType; LiteralElementMember elemMember; String fixedupClassName;
/*     */     JavaArrayType javaArType;
/*     */     JavaStructureMember javaMember;
/*     */     LiteralElementMember holderMember;
/*     */     QName structName;
/*     */     LiteralSequenceType struct;
/*     */     JavaStructureType javaStruct;
/*     */     Map members, members2;
/*     */     Class typeClass;
/*     */     List sortedMembers;
/*  79 */     String tmp = (String)unsupportedClasses.get(type.toString());
/*  80 */     if (tmp != null) {
/*  81 */       if (tmp.equals("Object")) {
/*  82 */         throw new ModelerException("rmimodeler.object.is.not.supported");
/*     */       }
/*  84 */       throw new ModelerException("rmimodeler.type.is.not.supported", new Object[] { tmp, type.toString() });
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     LiteralType typeNode = getMappedLiteralType(type, (topLevel && type.getTypeCode() == 9));
/*     */ 
/*     */ 
/*     */     
/*  94 */     if (typeNode != null) {
/*  95 */       LiteralElementMember literalElementMember = new LiteralElementMember(elemName, typeNode);
/*     */       
/*  97 */       JavaStructureMember javaStructureMember = new JavaStructureMember(literalElementMember.getName().getLocalPart(), typeNode.getJavaType(), literalElementMember, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 103 */       this.env.getNames().setJavaStructureMemberMethodNames(javaStructureMember);
/* 104 */       literalElementMember.setJavaStructureMember(javaStructureMember);
/* 105 */       literalElementMember.setRepeated((!topLevel && type.getTypeCode() == 9 && !typeNode.equals(this.literalTypes.XSD_BYTE_ARRAY_LITERALTYPE)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 111 */       literalElementMember.setNillable((!topLevel && type.isNillable()));
/* 112 */       literalElementMember.setRequired(topLevel);
/* 113 */       return literalElementMember;
/*     */     } 
/* 115 */     int typeCode = type.getTypeCode();
/* 116 */     String packageName = ClassNameInfo.getQualifier(type.toString());
/* 117 */     String namespaceURI = this.modeler.getNamespaceURI(packageName);
/* 118 */     if (namespaceURI == null)
/* 119 */       namespaceURI = typeUri; 
/* 120 */     switch (typeCode) {
/*     */       case 11:
/* 122 */         return null;
/*     */       case 9:
/* 124 */         elemRmiType = type.getElementType();
/* 125 */         elemMember = null;
/*     */         
/* 127 */         if (elemRmiType.getTypeCode() == 9 && !"byte[]".equals(elemRmiType.toString())) {
/*     */           
/* 129 */           elemMember = modelTypeLiteral(elemName, typeUri, elemRmiType, true, false);
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */           
/* 137 */           elemMember = modelTypeLiteral(elemName, typeUri, elemRmiType);
/*     */         } 
/*     */         
/* 140 */         fixedupClassName = type.typeString(false);
/* 141 */         javaArType = new JavaArrayType(fixedupClassName, InternalEncodingConstants.ARRAY_ELEMENT_NAME.getLocalPart(), elemMember.getJavaStructureMember().getType());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 148 */         javaMember = new JavaStructureMember(elemMember.getName().getLocalPart(), (JavaType)javaArType, elemMember, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 154 */         this.env.getNames().setJavaStructureMemberMethodNames(javaMember);
/* 155 */         elemMember.setJavaStructureMember(javaMember);
/* 156 */         elemMember.setRepeated(true);
/* 157 */         elemMember.setNillable((!topLevel && elemMember.getType().isNillable()));
/*     */         
/* 159 */         elemMember.setRequired(topLevel);
/*     */         
/* 161 */         if (topLevel) {
/*     */ 
/*     */           
/* 164 */           String servicePackage = this.modeler.getServicePackage();
/* 165 */           if (servicePackage != null && servicePackage.length() > 0) {
/*     */             
/* 167 */             servicePackage = servicePackage + '.';
/*     */           } else {
/* 169 */             servicePackage = "";
/*     */           } 
/* 171 */           servicePackage = servicePackage + "_arrays";
/*     */           
/* 173 */           elemMember.setNillable(elemMember.getType().isNillable());
/* 174 */           elemMember.setRequired(false);
/* 175 */           LiteralElementMember topMember = new LiteralElementMember();
/* 176 */           topMember.setName(elemName);
/* 177 */           topMember.setNillable(false);
/* 178 */           topMember.setRequired(true);
/* 179 */           LiteralArrayWrapperType seqType = new LiteralArrayWrapperType();
/*     */           
/* 181 */           String tmpNamespace = typeUri;
/* 182 */           String elemPackage = Names.getPackageName(elemMember.getType().getJavaType().getName());
/*     */ 
/*     */           
/* 185 */           if (elemPackage.startsWith(servicePackage)) {
/* 186 */             tmpNamespace = elemMember.getType().getName().getNamespaceURI();
/*     */           }
/* 188 */           else if (!elemPackage.equals(servicePackage)) {
/* 189 */             tmpNamespace = Names.getAdjustedURI(tmpNamespace, "arrays/" + elemPackage.replace('.', '/'));
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 194 */           seqType.setName(new QName(tmpNamespace, generateSchemaNameForArrayWrapper(elemRmiType, this.env)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 200 */           String arraysPackage = servicePackage + '.';
/*     */ 
/*     */           
/* 203 */           if (elemRmiType.getTypeCode() == 9) {
/*     */             String str;
/*     */ 
/*     */             
/* 207 */             if (elemMember.getType() instanceof com.sun.xml.rpc.processor.model.literal.LiteralSimpleType) {
/*     */               
/* 209 */               str = arraysPackage + this.env.getNames().validJavaClassName(elemRmiType.toString());
/*     */             
/*     */             }
/*     */             else {
/*     */               
/* 214 */               str = elemMember.getType().getJavaType().getName();
/*     */             } 
/* 216 */             fixedupMemberClassName = str + "Array";
/* 217 */           } else if (elemRmiType.getTypeCode() == 10) {
/* 218 */             fixedupMemberClassName = arraysPackage + elemRmiType.getClassName() + "Array";
/*     */           
/*     */           }
/*     */           else {
/*     */             
/* 223 */             fixedupMemberClassName = arraysPackage + this.env.getNames().validJavaClassName(elemRmiType.toString() + "Array");
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 228 */           String fixedupMemberClassName = fixedupMemberClassName.replace('$', '_');
/*     */           
/* 230 */           JavaStructureType javaStructureType = new JavaStructureType(fixedupMemberClassName, false, seqType);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 235 */           JavaStructureMember jsMember = new JavaStructureMember(elemName.getLocalPart(), (JavaType)javaStructureType, topMember);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 240 */           this.env.getNames().setJavaStructureMemberMethodNames(jsMember);
/* 241 */           seqType.setJavaType((JavaType)javaStructureType);
/* 242 */           seqType.add(elemMember);
/* 243 */           javaMember.setName("value");
/* 244 */           this.env.getNames().setJavaStructureMemberMethodNames(javaMember);
/*     */ 
/*     */           
/* 247 */           if (elemRmiType.getTypeCode() == 9 && !(elemMember.getType() instanceof com.sun.xml.rpc.processor.model.literal.LiteralSimpleType)) {
/*     */ 
/*     */ 
/*     */             
/* 251 */             String memName = this.env.getNames().getClassMemberName(elemMember.getType().getJavaType().getRealName());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 257 */             JavaArrayType ja2 = new JavaArrayType(elemMember.getType().getJavaType().getName() + "[]", memName, elemMember.getType().getJavaType());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 263 */             JavaStructureMember tmpMember = new JavaStructureMember(memName, (JavaType)ja2, elemMember);
/*     */             
/* 265 */             this.env.getNames().setJavaStructureMemberMethodNames(tmpMember);
/*     */             
/* 267 */             javaStructureType.add(tmpMember);
/*     */             
/* 269 */             JavaArrayType javaArray = new JavaArrayType(((LiteralArrayWrapperType)elemMember.getType()).getJavaArrayType().getName() + "[]", memName, elemMember.getType().getJavaType());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 278 */             seqType.setJavaArrayType(javaArray);
/*     */           } else {
/* 280 */             javaStructureType.add(elemMember.getJavaStructureMember());
/* 281 */             seqType.setJavaArrayType((JavaArrayType)elemMember.getJavaStructureMember().getType());
/*     */           } 
/*     */ 
/*     */ 
/*     */           
/* 286 */           elemMember.setName(new QName("value"));
/* 287 */           topMember.setType((LiteralType)seqType);
/* 288 */           topMember.setJavaStructureMember(jsMember);
/* 289 */           javaMember.setOwner(topMember);
/* 290 */           mapLiteralType(type, topMember.getType(), true);
/* 291 */           return topMember;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 296 */         return elemMember;
/*     */       
/*     */       case 10:
/* 299 */         if (!allowHolders && getHolderValueType(this.env, this.modeler.getDefHolder(), type) != null)
/*     */         {
/*     */           
/* 302 */           throw new ModelerException("rmimodeler.no.literal.holders", new Object[] { type.toString() });
/*     */         }
/*     */ 
/*     */         
/* 306 */         holderMember = modelHolder(elemName, this.modeler, this.env, typeUri, type);
/*     */         
/* 308 */         if (holderMember != null) {
/* 309 */           mapLiteralType(type, holderMember.getType());
/* 310 */           return holderMember;
/*     */         } 
/* 312 */         structName = new QName(namespaceURI, type.typeString(true).replace('$', '.'));
/*     */ 
/*     */ 
/*     */         
/* 316 */         struct = new LiteralSequenceType(structName);
/*     */         
/* 318 */         struct.setNillable(true);
/*     */ 
/*     */         
/* 321 */         fixedupClassName = type.getClassName();
/* 322 */         addTypeName(structName, fixedupClassName);
/*     */ 
/*     */         
/* 325 */         mapLiteralType(type, (LiteralType)struct);
/*     */         
/* 327 */         javaStruct = new JavaStructureType(fixedupClassName, true, struct);
/*     */         
/* 329 */         struct.setJavaType((JavaType)javaStruct);
/*     */         
/* 331 */         members = RmiStructure.modelTypeSOAP(this.env, type);
/* 332 */         members2 = JavaBean.modelTypeSOAP(this.env, type);
/* 333 */         if (members.size() != 0 && members2.size() != 0) {
/* 334 */           Iterator<String> keys = members.keySet().iterator();
/*     */           
/* 336 */           while (keys.hasNext()) {
/* 337 */             String key = keys.next();
/* 338 */             if (members2.containsKey(key)) {
/* 339 */               throw new ModelerException("rmimodeler.javabean.property.has.public.member", new Object[] { type.toString(), key });
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 345 */         members.putAll(members2);
/* 346 */         if (members.size() == 0) {
/* 347 */           throw new ModelerException("rmimodeler.invalid.rmi.type", type.toString());
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 353 */           typeClass = type.getTypeClass(this.env.getClassLoader());
/* 354 */           if (typeClass.isInterface() || Modifier.isAbstract(typeClass.getModifiers()))
/*     */           {
/* 356 */             ((JavaStructureType)struct.getJavaType()).setAbstract(true);
/*     */           }
/*     */         }
/* 359 */         catch (ClassNotFoundException e) {
/* 360 */           throw new ModelerException("rmimodeler.class.not.found", type.toString());
/*     */         } 
/*     */ 
/*     */         
/* 364 */         sortedMembers = RmiTypeModeler.sortMembers(typeClass, members, this.env);
/*     */         
/* 366 */         fillInStructure(typeUri, (LiteralStructuredType)struct, javaStruct, sortedMembers, type);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 372 */         elemMember = new LiteralElementMember(elemName, (LiteralType)struct);
/* 373 */         elemMember.setNillable(!topLevel);
/* 374 */         javaMember = new JavaStructureMember(elemMember.getName().getLocalPart().replace('.', '_'), struct.getJavaType(), elemMember, false);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 380 */         this.env.getNames().setJavaStructureMemberMethodNames(javaMember);
/* 381 */         elemMember.setJavaStructureMember(javaMember);
/* 382 */         return elemMember;
/*     */     } 
/* 384 */     throw new ModelerException("rmimodeler.unexpected.type", type.toString()); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LiteralSimpleTypeCreator getLiteralTypes() {
/* 391 */     return this.literalTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LiteralElementMember modelHolder(QName elemName, RmiModeler modeler, ProcessorEnvironment env, String typeUri, RmiType type) {
/* 401 */     RmiType holderValueType = getHolderValueType(env, modeler.getDefHolder(), type);
/*     */     
/* 403 */     if (holderValueType == null)
/* 404 */       return null; 
/* 405 */     LiteralElementMember holderMember = modelTypeLiteral(elemName, typeUri, holderValueType, true, true);
/*     */     
/* 407 */     JavaType javaType = holderMember.getType().getJavaType();
/* 408 */     javaType.setHolder(true);
/* 409 */     javaType.setHolderPresent(true);
/* 410 */     javaType.setHolderName(type.toString());
/* 411 */     return holderMember;
/*     */   }
/*     */   
/*     */   private void modelSubclasses(String typeUri, JavaStructureType type) {
/* 415 */     if (!type.isPresent())
/*     */       return; 
/*     */     try {
/* 418 */       Class typeClass = RmiUtils.getClassForName(type.getRealName(), this.env.getClassLoader());
/*     */ 
/*     */ 
/*     */       
/* 422 */       Class[] interfaces = typeClass.getInterfaces();
/* 423 */       if (RmiTypeModeler.multipleClasses(interfaces, this.env)) {
/* 424 */         throw new ModelerException("rmimodeler.type.implements.more.than.one.interface", new Object[] { type.getRealName(), interfaces[0].getName(), interfaces[1].getName() });
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 431 */     catch (ClassNotFoundException e) {
/* 432 */       throw new ModelerException("rmimodeler.class.not.found", type.getRealName());
/*     */     } 
/*     */ 
/*     */     
/* 436 */     int startSize = this.complexTypeMap.size();
/* 437 */     int curSize = 0;
/* 438 */     while (curSize != startSize) {
/* 439 */       curSize = startSize;
/* 440 */       Iterator<Map.Entry> iterator = this.complexTypeMap.entrySet().iterator();
/*     */ 
/*     */       
/* 443 */       while (iterator.hasNext()) {
/* 444 */         LiteralType extraType = (LiteralType)((Map.Entry)iterator.next()).getValue();
/* 445 */         if (extraType instanceof LiteralStructuredType) {
/* 446 */           JavaStructureType javaType = (JavaStructureType)extraType.getJavaType();
/* 447 */           if (!type.equals(javaType) && javaType.isPresent() && isSubclass(javaType.getRealName(), type.getRealName(), this.env.getClassLoader())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 453 */             modelHierarchy(typeUri, javaType, type);
/* 454 */             curSize = this.complexTypeMap.size();
/* 455 */             if (curSize != startSize) {
/* 456 */               startSize = curSize;
/* 457 */               curSize = 0;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean modelHierarchy(String typeUri, JavaStructureType subclass, JavaStructureType superclassType) {
/* 472 */     String superClassName = superclassType.getRealName();
/* 473 */     LiteralStructuredType superLiteralType = (LiteralStructuredType)superclassType.getOwner();
/*     */     
/* 475 */     LiteralStructuredType literalType = (LiteralStructuredType)subclass.getOwner();
/*     */     
/* 477 */     if (literalType.getParentType() != null) {
/* 478 */       if (literalType.getParentType().equals(superLiteralType)) {
/* 479 */         return true;
/*     */       }
/*     */       
/* 482 */       if (isSubclass(literalType.getParentType().getJavaType().getRealName(), superClassName, this.env.getClassLoader())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 488 */         LiteralStructuredType tmpType = literalType;
/* 489 */         while (tmpType.getParentType() != null) {
/* 490 */           tmpType = tmpType.getParentType();
/* 491 */           if (tmpType.getJavaType().getRealName().equals(superClassName))
/*     */           {
/*     */ 
/*     */             
/* 495 */             return true; } 
/* 496 */           if (!isSubclass(tmpType.getJavaType().getRealName(), superClassName, this.env.getClassLoader()))
/*     */           {
/*     */             
/* 499 */             throw new ModelerException("rmimodeler.type.is.used.as.two.types", new Object[] { subclass.getRealName(), tmpType.getJavaType().getRealName(), superClassName });
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 507 */         return modelHierarchy(typeUri, (JavaStructureType)tmpType.getJavaType(), superclassType);
/*     */       } 
/*     */ 
/*     */       
/* 511 */       if (!isSubclass(superClassName, literalType.getParentType().getJavaType().getRealName(), this.env.getClassLoader())) {
/*     */ 
/*     */ 
/*     */         
/* 515 */         if (literalType.getParentType().getJavaType().getRealName().equals(superClassName))
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 520 */           return true;
/*     */         }
/* 522 */         throw new ModelerException("rmimodeler.type.is.used.as.two.types", new Object[] { subclass.getRealName(), literalType.getParentType().getJavaType().getRealName(), superClassName });
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 532 */       Class subclassClass = RmiUtils.getClassForName(subclass.getRealName(), this.env.getClassLoader());
/*     */ 
/*     */ 
/*     */       
/* 536 */       Class superclass = subclassClass.getSuperclass();
/*     */       
/* 538 */       if (superclass == null || (!superclass.getName().equals(superClassName) && !isSubclass(superclass.getName(), superClassName, this.env.getClassLoader()))) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 543 */         Class[] interfaces = subclassClass.getInterfaces();
/* 544 */         for (int i = 0; i < interfaces.length; i++) {
/* 545 */           if (interfaces[i].getName().equals(superClassName)) {
/* 546 */             superLiteralType.addSubtype((LiteralStructuredType)subclass.getOwner());
/*     */             
/* 548 */             ((JavaStructureType)superLiteralType.getJavaType()).addSubclass(subclass);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 553 */             return true;
/*     */           } 
/* 555 */           if (isSubclass(interfaces[i].getName(), superClassName, this.env.getClassLoader())) {
/*     */ 
/*     */             
/* 558 */             RmiType rmiType = RmiType.getRmiType(interfaces[i]);
/* 559 */             LiteralElementMember literalElementMember = modelTypeLiteral(new QName(this.modeler.generateNameFromType(rmiType, this.env)), typeUri, rmiType);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 565 */             LiteralStructuredType literalStructuredType = (LiteralStructuredType)literalElementMember.getType();
/*     */             
/* 567 */             literalStructuredType.addSubtype((LiteralStructuredType)subclass.getOwner());
/*     */             
/* 569 */             ((JavaStructureType)literalStructuredType.getJavaType()).addSubclass(subclass);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 574 */             return modelHierarchy(typeUri, (JavaStructureType)literalStructuredType.getJavaType(), superclassType);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 582 */       RmiType type = RmiType.getRmiType(superclass);
/* 583 */       LiteralElementMember elemMember = modelTypeLiteral(new QName(this.modeler.generateNameFromType(type, this.env)), typeUri, type);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 588 */       LiteralStructuredType superType = (LiteralStructuredType)elemMember.getType();
/*     */       
/* 590 */       superType.addSubtype((LiteralStructuredType)subclass.getOwner());
/* 591 */       ((JavaStructureType)superType.getJavaType()).addSubclass(subclass);
/*     */       
/* 593 */       if (!superclass.getName().equals(superClassName)) {
/* 594 */         return modelHierarchy(typeUri, (JavaStructureType)superType.getJavaType(), superclassType);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 599 */       return true;
/* 600 */     } catch (ClassNotFoundException e) {
/* 601 */       throw new ModelerException("rmimodeler.class.not.found", subclass.getRealName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isSubclass(String subtypeName, String supertypeName, ClassLoader classLoader) {
/* 612 */     return RmiTypeModeler.isSubclass(subtypeName, supertypeName, classLoader);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RmiType getHolderValueType(ProcessorEnvironment env, Class defHolder, RmiType type) {
/* 623 */     return RmiTypeModeler.getHolderValueType(env, defHolder, type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillInStructure(String typeUri, LiteralStructuredType struct, JavaStructureType javaStruct, List sortedMembers, RmiType type) {
/* 633 */     ProcessorEnvironment env = this.modeler.getProcessorEnvironment();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 638 */     for (Iterator<MemberInfo> iter = sortedMembers.iterator(); iter.hasNext(); ) {
/* 639 */       MemberInfo memInfo = iter.next();
/* 640 */       LiteralElementMember member = modelTypeLiteral(new QName(memInfo.getName()), typeUri, memInfo.getType());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 645 */       JavaStructureMember javaMember = member.getJavaStructureMember();
/* 646 */       javaMember.setReadMethod(memInfo.getReadMethod());
/* 647 */       javaMember.setWriteMethod(memInfo.getWriteMethod());
/* 648 */       javaMember.setPublic(memInfo.isPublic());
/* 649 */       if (memInfo.getDeclaringClass() != null) {
/* 650 */         javaMember.setDeclaringClass(memInfo.getDeclaringClass().getName());
/*     */       }
/*     */       
/* 653 */       javaStruct.add(javaMember);
/* 654 */       struct.add(member);
/*     */     } 
/* 656 */     markInheritedMembers(env, struct);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void markInheritedMembers(ProcessorEnvironment env, LiteralStructuredType struct) {
/* 663 */     String className = struct.getJavaType().getRealName();
/*     */     
/*     */     try {
/* 666 */       Iterator<LiteralElementMember> members = struct.getElementMembers();
/*     */ 
/*     */       
/* 669 */       Class javaClass = RmiUtils.getClassForName(className, env.getClassLoader());
/*     */       
/* 671 */       if (javaClass.isInterface() || Modifier.isAbstract(javaClass.getModifiers()))
/*     */       {
/* 673 */         ((JavaStructureType)struct.getJavaType()).setAbstract(true);
/*     */       }
/* 675 */       Class superclass = javaClass.getSuperclass();
/* 676 */       while (members.hasNext()) {
/* 677 */         LiteralElementMember literalMember = members.next();
/* 678 */         JavaStructureMember javaMember = literalMember.getJavaStructureMember();
/* 679 */         if (javaMember.isPublic()) {
/*     */           try {
/* 681 */             Field field = javaClass.getDeclaredField(javaMember.getName());
/*     */             
/* 683 */             if (!field.getDeclaringClass().equals(javaClass)) {
/* 684 */               javaMember.setInherited(true);
/* 685 */               literalMember.setInherited(true);
/*     */             } 
/* 687 */           } catch (NoSuchFieldException e) {
/* 688 */             javaMember.setInherited(true);
/* 689 */             literalMember.setInherited(true);
/*     */           } 
/*     */           continue;
/*     */         } 
/* 693 */         String methodName = javaMember.getReadMethod();
/* 694 */         Class[] args = new Class[0];
/* 695 */         boolean isInherited = RmiTypeModeler.isMethodInherited(methodName, args, javaClass);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 700 */         methodName = javaMember.getWriteMethod();
/* 701 */         if (methodName != null) {
/* 702 */           isInherited = isInherited ? RmiTypeModeler.isMethodInherited(methodName, args, javaClass) : false;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 710 */         if (isInherited) {
/* 711 */           javaMember.setInherited(true);
/* 712 */           literalMember.setInherited(true);
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 718 */       members = struct.getAttributeMembers();
/*     */       
/* 720 */       while (members.hasNext()) {
/* 721 */         LiteralAttributeMember attributeMember = (LiteralAttributeMember)members.next();
/* 722 */         JavaStructureMember javaMember = attributeMember.getJavaStructureMember();
/* 723 */         if (javaMember.isPublic()) {
/*     */           try {
/* 725 */             Field field = javaClass.getDeclaredField(javaMember.getName());
/*     */             
/* 727 */             if (!field.getDeclaringClass().equals(javaClass)) {
/* 728 */               javaMember.setInherited(true);
/* 729 */               attributeMember.setInherited(true);
/*     */             } 
/* 731 */           } catch (NoSuchFieldException e) {
/* 732 */             javaMember.setInherited(true);
/* 733 */             attributeMember.setInherited(true);
/*     */           } 
/*     */           continue;
/*     */         } 
/* 737 */         String methodName = javaMember.getReadMethod();
/* 738 */         Class[] args = new Class[0];
/* 739 */         boolean isInherited = RmiTypeModeler.isMethodInherited(methodName, args, javaClass);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 744 */         methodName = javaMember.getWriteMethod();
/* 745 */         if (methodName != null) {
/* 746 */           isInherited = isInherited ? RmiTypeModeler.isMethodInherited(methodName, args, javaClass) : false;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 754 */         if (isInherited) {
/* 755 */           javaMember.setInherited(true);
/* 756 */           attributeMember.setInherited(true);
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 761 */     } catch (ClassNotFoundException e) {
/* 762 */       throw new ModelerException("rmimodeler.class.not.found", className);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void modelSubclasses(String typeUri) {
/* 767 */     Set<LiteralType> abstractTypes = new HashSet();
/* 768 */     int startSize = this.complexTypeMap.size();
/* 769 */     Iterator<Map.Entry> iter = this.complexTypeMap.entrySet().iterator();
/*     */     
/* 771 */     while (iter.hasNext()) {
/* 772 */       LiteralType type = (LiteralType)((Map.Entry)iter.next()).getValue();
/* 773 */       if (type instanceof LiteralStructuredType)
/*     */       {
/* 775 */         abstractTypes.add(type);
/*     */       }
/*     */     } 
/* 778 */     iter = (Iterator)abstractTypes.iterator();
/* 779 */     while (iter.hasNext()) {
/* 780 */       LiteralType type = (LiteralType)iter.next();
/* 781 */       modelSubclasses(typeUri, (JavaStructureType)type.getJavaType());
/*     */     } 
/* 783 */     if (startSize != this.complexTypeMap.size()) {
/* 784 */       iter = this.complexTypeMap.entrySet().iterator();
/* 785 */       while (iter.hasNext()) {
/* 786 */         LiteralType type = (LiteralType)((Map.Entry)iter.next()).getValue();
/* 787 */         if (type instanceof LiteralStructuredType && ((JavaStructureType)type.getJavaType()).isAbstract() && !abstractTypes.contains(type))
/*     */         {
/*     */ 
/*     */           
/* 791 */           modelSubclasses(typeUri, (JavaStructureType)type.getJavaType());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean nameClashes(String name) {
/* 800 */     name = name.toUpperCase();
/*     */     
/* 802 */     Iterator<LiteralType> iter = this.complexTypeMap.values().iterator();
/*     */ 
/*     */     
/* 805 */     while (iter.hasNext()) {
/* 806 */       LiteralType type = iter.next();
/* 807 */       String typeName = ClassNameInfo.getName(type.getJavaType().getRealName()).toUpperCase();
/*     */ 
/*     */ 
/*     */       
/* 811 */       if (typeName.equals(name)) {
/* 812 */         return true;
/*     */       }
/*     */     } 
/* 815 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LiteralType getMappedLiteralType(RmiType type, boolean topLevel) {
/* 823 */     LiteralType retType = null;
/* 824 */     String name = type.toString();
/* 825 */     retType = (LiteralType)this.simpleTypeMap.get(name);
/* 826 */     if (retType == null)
/* 827 */       retType = (LiteralType)this.complexTypeMap.get(name + topLevel); 
/* 828 */     return retType;
/*     */   }
/*     */   
/*     */   private void mapLiteralType(RmiType type, LiteralType literalType) {
/* 832 */     mapLiteralType(type, literalType, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void mapLiteralType(RmiType type, LiteralType literalType, boolean topLevel) {
/* 840 */     mapLiteralType(type.toString() + topLevel, literalType);
/*     */   }
/*     */   
/*     */   private void mapLiteralType(String name, LiteralType literalType) {
/* 844 */     this.complexTypeMap.put(name, literalType);
/*     */   }
/*     */   
/*     */   private void addTypeName(QName typeName, String javaType) {
/* 848 */     if (this.typeNames.contains(typeName)) {
/* 849 */       throw new ModelerException("rmimodeler.duplicate.type.name", new Object[] { typeName.toString(), javaType });
/*     */     }
/*     */ 
/*     */     
/* 853 */     this.typeNames.add(typeName);
/*     */   }
/*     */   public String generateSchemaNameForArrayWrapper(RmiType type, ProcessorEnvironment env) {
/*     */     String base;
/*     */     RmiType holderValueType;
/*     */     String tmp;
/* 859 */     int typeCode = type.getTypeCode();
/*     */     
/* 861 */     switch (typeCode) {
/*     */       case 0:
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/* 870 */         base = type.toString();
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
/* 895 */         return base + "Array";case 9: base = generateSchemaNameForArrayWrapper(type.getElementType(), env); return base + "Array";case 10: holderValueType = RmiTypeModeler.getHolderValueType(env, this.modeler.getDefHolder(), type); if (holderValueType != null) return generateSchemaNameForArrayWrapper(holderValueType, env);  tmp = ClassNameInfo.getName(type.getClassName()); base = ClassNameInfo.replaceInnerClassSym(tmp); return base + "Array";
/*     */     } 
/*     */     throw new Error("unexpected type code: " + typeCode);
/*     */   } private static void log(ProcessorEnvironment env, String msg) {
/* 899 */     if (env.verbose()) {
/* 900 */       System.out.println("[RmiTypeModeler: " + msg + "]");
/*     */     }
/*     */   }
/*     */   
/*     */   public void initializeTypeMap(Map typeMap) {
/* 905 */     this.literalTypes.initializeTypeMap(typeMap);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LiteralTypeModeler() {
/* 929 */     this.typeNames = new HashSet(); this.typeNames = new HashSet(); this.simpleTypeMap = new HashMap<Object, Object>(); this.complexTypeMap = new HashMap<Object, Object>(); this.literalTypes = new LiteralSimpleTypeCreator(); initializeTypeMap(this.simpleTypeMap); } LiteralTypeModeler(RmiModeler modeler, ProcessorEnvironment env) { this.typeNames = new HashSet(); this.modeler = modeler; this.env = env;
/*     */     this.typeMappingRegistry = modeler.getTypeMappingRegistryInfo();
/*     */     this.typeNames = new HashSet();
/*     */     this.simpleTypeMap = new HashMap<Object, Object>();
/*     */     this.complexTypeMap = new HashMap<Object, Object>();
/*     */     this.literalTypes = new LiteralSimpleTypeCreator();
/* 935 */     initializeTypeMap(this.simpleTypeMap); } private static final Set boxedPrimitiveSet = new HashSet();
/* 936 */   private static final Map unsupportedClasses = new HashMap<Object, Object>();
/*     */   private static final String COLLECTION = "Collection";
/*     */   private static final String ATTACHMENT = "Attachment";
/*     */   private static final String OBJECT = "Object";
/*     */   
/*     */   static {
/* 942 */     boxedPrimitiveSet.add("java.lang.Boolean");
/* 943 */     boxedPrimitiveSet.add("java.lang.Byte");
/* 944 */     boxedPrimitiveSet.add("java.lang.Double");
/* 945 */     boxedPrimitiveSet.add("java.lang.Float");
/* 946 */     boxedPrimitiveSet.add("java.lang.Integer");
/* 947 */     boxedPrimitiveSet.add("java.lang.Long");
/* 948 */     boxedPrimitiveSet.add("java.lang.Short");
/*     */     
/* 950 */     unsupportedClasses.put("java.lang.Object", null);
/* 951 */     unsupportedClasses.put("java.awt.Image", "Attachment");
/* 952 */     unsupportedClasses.put("javax.mail.internet.MimeMultipart", "Attachment");
/* 953 */     unsupportedClasses.put("javax.xml.transform.Source", "Attachment");
/* 954 */     unsupportedClasses.put("javax.activation.DataHandler", "Attachment");
/*     */ 
/*     */     
/* 957 */     unsupportedClasses.put("java.util.Collection", "Collection");
/* 958 */     unsupportedClasses.put("java.util.List", "Collection");
/* 959 */     unsupportedClasses.put("java.util.Set", "Collection");
/* 960 */     unsupportedClasses.put("java.util.Vector", "Collection");
/* 961 */     unsupportedClasses.put("java.util.Stack", "Collection");
/* 962 */     unsupportedClasses.put("java.util.LinkedList", "Collection");
/* 963 */     unsupportedClasses.put("java.util.ArrayList", "Collection");
/* 964 */     unsupportedClasses.put("java.util.HashSet", "Collection");
/* 965 */     unsupportedClasses.put("java.util.TreeSet", "Collection");
/*     */ 
/*     */     
/* 968 */     unsupportedClasses.put("java.util.Map", "Collection");
/* 969 */     unsupportedClasses.put("java.util.HashMap", "Collection");
/* 970 */     unsupportedClasses.put("java.util.TreeMap", "Collection");
/* 971 */     unsupportedClasses.put("java.util.Hashtable", "Collection");
/* 972 */     unsupportedClasses.put("java.util.Properties", "Collection");
/*     */     
/* 974 */     unsupportedClasses.put("com.sun.xml.rpc.encoding.soap.JAXRpcMapEntry", "Collection");
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\LiteralTypeModeler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */