/*     */ package com.sun.xml.rpc.processor.modeler.rmi;
/*     */ 
/*     */ import com.sun.xml.rpc.encoding.InternalEncodingConstants;
/*     */ import com.sun.xml.rpc.processor.config.TypeMappingInfo;
/*     */ import com.sun.xml.rpc.processor.config.TypeMappingRegistryInfo;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaArrayType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaCustomType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaStructureType;
/*     */ import com.sun.xml.rpc.processor.model.java.JavaType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPArrayType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPCustomType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPOrderedStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureMember;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPStructureType;
/*     */ import com.sun.xml.rpc.processor.model.soap.SOAPType;
/*     */ import com.sun.xml.rpc.processor.modeler.ModelerException;
/*     */ import com.sun.xml.rpc.processor.util.ProcessorEnvironment;
/*     */ import com.sun.xml.rpc.soap.SOAPConstantsFactory;
/*     */ import com.sun.xml.rpc.soap.SOAPEncodingConstants;
/*     */ import com.sun.xml.rpc.soap.SOAPVersion;
/*     */ import com.sun.xml.rpc.util.ClassNameInfo;
/*     */ import com.sun.xml.rpc.util.JAXRPCClassFactory;
/*     */ import com.sun.xml.rpc.util.VersionUtil;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class RmiTypeModeler
/*     */   implements RmiConstants
/*     */ {
/*     */   private Map soapTypeMap;
/*     */   private Set typeNames;
/*     */   private RmiModeler modeler;
/*     */   private ProcessorEnvironment env;
/*     */   private TypeMappingRegistryInfo typeMappingRegistry;
/*     */   private SOAPSimpleTypeCreatorBase soapTypes;
/*     */   private SOAPEncodingConstants soapEncodingConstants;
/*     */   private SOAPVersion soapVersion;
/*     */   private String targetVersion;
/*     */   
/*     */   public SOAPType modelTypeSOAP(String typeUri, RmiType type) {
/*     */     QName arrName;
/*     */     String fixedupClassName;
/*     */     JavaArrayType javaArType;
/*     */     SOAPArrayType arrType;
/*     */     SOAPType elemType;
/*     */     String tmp;
/*     */     SOAPType tmpSOAPType, holderType;
/*     */     QName structName;
/*     */     SOAPOrderedStructureType sOAPOrderedStructureType;
/*     */     JavaStructureType javaStruct;
/*     */     Map members;
/*     */     Class typeClass;
/*     */     List sortedMembers;
/*  76 */     if (this.modeler.isStrictCompliant() && optionalTypes.contains(type.typeString(false).replace('$', '.')))
/*     */     {
/*     */       
/*  79 */       throw new ModelerException("rmimodeler.type.not.strict.compliant", type.typeString(false).replace('$', '.'));
/*     */     }
/*     */ 
/*     */     
/*  83 */     SOAPType typeNode = getMappedSoapType(type);
/*  84 */     if (typeNode != null) {
/*  85 */       return typeNode;
/*     */     }
/*  87 */     if (this.typeMappingRegistry != null) {
/*  88 */       TypeMappingInfo typeMapping = this.typeMappingRegistry.getTypeMappingInfo(this.soapEncodingConstants.getURIEncoding(), type.toString());
/*     */ 
/*     */ 
/*     */       
/*  92 */       if (typeMapping != null) {
/*  93 */         return processTypeMapping(this.env, typeMapping);
/*     */       }
/*     */     } 
/*  96 */     int typeCode = type.getTypeCode();
/*  97 */     String packageName = ClassNameInfo.getQualifier(type.toString());
/*  98 */     String namespaceURI = this.modeler.getNamespaceURI(packageName);
/*  99 */     if (namespaceURI == null)
/* 100 */       namespaceURI = typeUri; 
/* 101 */     switch (typeCode) {
/*     */       case 11:
/* 103 */         return null;
/*     */ 
/*     */       
/*     */       case 9:
/* 107 */         arrName = new QName(namespaceURI, "tmp");
/*     */         
/* 109 */         fixedupClassName = type.typeString(false);
/*     */         
/* 111 */         javaArType = new JavaArrayType(fixedupClassName);
/*     */         
/* 113 */         javaArType.setElementName(InternalEncodingConstants.ARRAY_ELEMENT_NAME.getLocalPart());
/*     */ 
/*     */ 
/*     */         
/* 117 */         arrType = new SOAPArrayType(arrName, this.soapVersion);
/* 118 */         arrType.setJavaType((JavaType)javaArType);
/* 119 */         arrType.setElementName(InternalEncodingConstants.ARRAY_ELEMENT_NAME);
/*     */         
/* 121 */         mapSOAPType(type, (SOAPType)arrType);
/*     */ 
/*     */         
/* 124 */         elemType = modelTypeSOAP(typeUri, type.getElementType());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 132 */         tmp = boxedPrimitiveSet.contains(elemType.getJavaType().getRealName()) ? ClassNameInfo.getName(elemType.getJavaType().getName()) : elemType.getName().getLocalPart();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 139 */         arrName = new QName(namespaceURI, "ArrayOf" + tmp);
/* 140 */         tmpSOAPType = elemType;
/* 141 */         while (tmpSOAPType instanceof SOAPArrayType) {
/* 142 */           tmpSOAPType = ((SOAPArrayType)tmpSOAPType).getElementType();
/*     */         }
/*     */         
/* 145 */         if (!(tmpSOAPType instanceof com.sun.xml.rpc.processor.model.soap.SOAPSimpleType)) {
/* 146 */           addTypeName(arrName, fixedupClassName);
/*     */         }
/* 148 */         arrType.setName(arrName);
/*     */         
/* 150 */         arrType.setElementType(elemType);
/* 151 */         if (elemType instanceof SOAPArrayType) {
/* 152 */           arrType.setRank(((SOAPArrayType)elemType).getRank() + 1);
/*     */         } else {
/* 154 */           arrType.setRank(1);
/* 155 */         }  javaArType.setElementType(elemType.getJavaType());
/* 156 */         return (SOAPType)arrType;
/*     */       
/*     */       case 10:
/* 159 */         holderType = modelHolder(this.modeler, this.env, typeUri, type);
/* 160 */         if (holderType != null) {
/* 161 */           mapSOAPType(type, holderType);
/* 162 */           return holderType;
/*     */         } 
/* 164 */         structName = new QName(namespaceURI, type.typeString(true).replace('$', '.'));
/*     */ 
/*     */ 
/*     */         
/* 168 */         sOAPOrderedStructureType = new SOAPOrderedStructureType(structName, this.soapVersion);
/*     */ 
/*     */ 
/*     */         
/* 172 */         fixedupClassName = type.getClassName();
/* 173 */         addTypeName(structName, fixedupClassName);
/*     */ 
/*     */         
/* 176 */         mapSOAPType(type, (SOAPType)sOAPOrderedStructureType);
/*     */         
/* 178 */         javaStruct = new JavaStructureType(fixedupClassName, true, sOAPOrderedStructureType);
/*     */         
/* 180 */         sOAPOrderedStructureType.setJavaType((JavaType)javaStruct);
/* 181 */         members = collectMembers(this.env, type);
/* 182 */         if (members.size() == 0) {
/* 183 */           throw new ModelerException("rmimodeler.invalid.rmi.type", type.toString());
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 189 */           typeClass = type.getTypeClass(this.env.getClassLoader());
/* 190 */           if (typeClass.isInterface() || Modifier.isAbstract(typeClass.getModifiers()))
/*     */           {
/* 192 */             ((JavaStructureType)sOAPOrderedStructureType.getJavaType()).setAbstract(true);
/*     */           }
/*     */         }
/* 195 */         catch (ClassNotFoundException e) {
/* 196 */           throw new ModelerException("rmimodeler.class.not.found", type.toString());
/*     */         } 
/*     */ 
/*     */         
/* 200 */         sortedMembers = null;
/*     */         
/* 202 */         if (VersionUtil.isVersion101(this.targetVersion)) {
/* 203 */           sortedMembers = sortMembers101(typeClass, members, this.env);
/*     */         } else {
/* 205 */           sortedMembers = sortMembers(typeClass, members, this.env);
/* 206 */         }  fillInStructure(typeUri, (SOAPStructureType)sOAPOrderedStructureType, javaStruct, sortedMembers, type);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 212 */         return (SOAPType)sOAPOrderedStructureType;
/*     */     } 
/* 214 */     throw new ModelerException("rmimodeler.unexpected.type", type.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Map collectMembers(ProcessorEnvironment env, RmiType type) {
/* 221 */     Map members = RmiStructure.modelTypeSOAP(env, type);
/* 222 */     Map members2 = JavaBean.modelTypeSOAP(env, type);
/* 223 */     if (members.size() != 0 && members2.size() != 0) {
/* 224 */       Iterator<String> keys = members.keySet().iterator();
/*     */       
/* 226 */       while (keys.hasNext()) {
/* 227 */         String key = keys.next();
/* 228 */         if (members2.containsKey(key)) {
/* 229 */           throw new ModelerException("rmimodeler.javabean.property.has.public.member", new Object[] { type.toString(), key });
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 235 */     members.putAll(members2);
/* 236 */     return members;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List sortMembers(Class typeClass, Map members, ProcessorEnvironment env) {
/* 244 */     Set<MemberInfo> sorted = new TreeSet(new MemberInfoComparator());
/*     */     
/* 246 */     for (Iterator<Map.Entry> iter = members.entrySet().iterator(); iter.hasNext(); ) {
/* 247 */       MemberInfo memInfo = (MemberInfo)((Map.Entry)iter.next()).getValue();
/* 248 */       memInfo.setSortingClass(getDeclaringClass(typeClass, memInfo, env));
/* 249 */       sorted.add(memInfo);
/*     */     } 
/* 251 */     List<MemberInfo> list = new ArrayList(sorted.size());
/* 252 */     Iterator<MemberInfo> iterator = sorted.iterator();
/* 253 */     while (iterator.hasNext()) {
/* 254 */       MemberInfo memInfo = iterator.next();
/* 255 */       list.add(memInfo);
/*     */     } 
/* 257 */     return list;
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
/*     */   private static List sortMembers101(Class typeClass, Map members, ProcessorEnvironment env) {
/* 271 */     List<MemberInfo> sorted = new ArrayList();
/*     */     
/* 273 */     for (Iterator<Map.Entry> iter = members.entrySet().iterator(); iter.hasNext(); ) {
/* 274 */       MemberInfo memInfo = (MemberInfo)((Map.Entry)iter.next()).getValue();
/* 275 */       memInfo.setSortingClass(getDeclaringClass(typeClass, memInfo, env));
/* 276 */       sorted.add(memInfo);
/*     */     } 
/* 278 */     return sorted;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class getDeclaringClass(Class<?> theClass, MemberInfo memInfo, ProcessorEnvironment env) {
/* 286 */     Class<?> retClass = null;
/* 287 */     if (retClass == null) {
/* 288 */       if (memInfo.isPublic()) {
/* 289 */         Class superClass = theClass.getSuperclass();
/* 290 */         if (superClass != null)
/* 291 */           retClass = getDeclaringClass(superClass, memInfo, env); 
/* 292 */         if (retClass == null) {
/* 293 */           Class[] interfaces = theClass.getInterfaces();
/* 294 */           int i = 0;
/* 295 */           for (; i < interfaces.length && retClass == null; 
/* 296 */             i++) {
/* 297 */             retClass = getDeclaringClass(interfaces[i], memInfo, env);
/*     */           }
/*     */         } 
/*     */         try {
/* 301 */           Field field = theClass.getDeclaredField(memInfo.getName());
/* 302 */           if (field.getDeclaringClass().equals(theClass)) {
/* 303 */             retClass = theClass;
/*     */           }
/* 305 */         } catch (NoSuchFieldException e) {}
/*     */       } else {
/*     */         
/*     */         try {
/* 309 */           Class typeClass = memInfo.getType().getTypeClass(theClass.getClassLoader());
/*     */ 
/*     */           
/* 312 */           Class<?> readClass = getDeclaringClassMethod(theClass, memInfo.getReadMethod(), new Class[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 317 */           Class<?> writeClass = getDeclaringClassMethod(theClass, memInfo.getWriteMethod(), new Class[] { typeClass });
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 322 */           if (readClass.equals(writeClass)) {
/* 323 */             retClass = readClass;
/* 324 */           } else if (readClass.isAssignableFrom(writeClass)) {
/* 325 */             retClass = writeClass;
/*     */           } else {
/* 327 */             retClass = readClass;
/*     */           } 
/* 329 */         } catch (ClassNotFoundException e) {
/* 330 */           throw new ModelerException("rmimodeler.class.not.found", memInfo.getType().getClassName());
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 336 */     return retClass;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class getDeclaringClassMethod(Class theClass, String methodName, Class[] args) {
/* 344 */     Class retClass = null;
/* 345 */     Class superClass = theClass.getSuperclass();
/* 346 */     if (superClass != null)
/* 347 */       retClass = getDeclaringClassMethod(superClass, methodName, args); 
/* 348 */     if (retClass == null) {
/* 349 */       Class[] interfaces = theClass.getInterfaces();
/* 350 */       for (int i = 0; i < interfaces.length && retClass == null; i++) {
/* 351 */         retClass = getDeclaringClassMethod(interfaces[i], methodName, args);
/*     */       }
/*     */     } 
/* 354 */     if (retClass == null) {
/*     */       try {
/* 356 */         Method method = theClass.getMethod(methodName, args);
/* 357 */         if (method.getDeclaringClass().equals(theClass)) {
/* 358 */           retClass = theClass;
/*     */         }
/* 360 */       } catch (NoSuchMethodException e) {}
/*     */     }
/*     */     
/* 363 */     return retClass;
/*     */   }
/*     */   
/*     */   public SOAPSimpleTypeCreatorBase getSOAPTypes() {
/* 367 */     return this.soapTypes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPType modelHolder(RmiModeler modeler, ProcessorEnvironment env, String typeUri, RmiType type) {
/* 376 */     RmiType holderValueType = getHolderValueType(env, modeler.getDefHolder(), type);
/*     */     
/* 378 */     if (holderValueType == null)
/* 379 */       return null; 
/* 380 */     SOAPType holderSOAPType = modelTypeSOAP(typeUri, holderValueType);
/* 381 */     JavaType javaType = holderSOAPType.getJavaType();
/* 382 */     javaType.setHolder(true);
/* 383 */     javaType.setHolderPresent(true);
/* 384 */     javaType.setHolderName(type.toString());
/* 385 */     return holderSOAPType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean multipleClasses(Class[] classes, ProcessorEnvironment env) {
/* 391 */     if (classes.length < 2)
/* 392 */       return false; 
/* 393 */     ArrayList<Class<?>> tmpList = new ArrayList(classes.length);
/* 394 */     for (int i = 0; i < classes.length; i++) {
/* 395 */       RmiType type = RmiType.getRmiType(classes[i]);
/* 396 */       if (RmiStructure.modelTypeSOAP(env, type).size() > 0 || JavaBean.modelTypeSOAP(env, type).size() > 0)
/*     */       {
/* 398 */         tmpList.add(classes[i]); } 
/*     */     } 
/* 400 */     classes = new Class[tmpList.size()];
/* 401 */     classes = (Class[])tmpList.<Class<?>[]>toArray((Class<?>[][])classes);
/* 402 */     if (classes.length < 2)
/* 403 */       return false; 
/* 404 */     boolean isSubclass = true;
/* 405 */     for (int j = 0; j < classes.length && isSubclass; j++) {
/* 406 */       isSubclass = false;
/* 407 */       for (int k = j; k < classes.length && !isSubclass; k++) {
/* 408 */         if (classes[j].isAssignableFrom(classes[k]) || classes[k].isAssignableFrom(classes[j]))
/*     */         {
/* 410 */           isSubclass = true;
/*     */         }
/*     */       } 
/*     */     } 
/* 414 */     return isSubclass;
/*     */   }
/*     */   
/*     */   private void modelSubclasses(String typeUri, JavaStructureType type) {
/*     */     try {
/* 419 */       Class typeClass = RmiUtils.getClassForName(type.getRealName(), this.env.getClassLoader());
/*     */ 
/*     */ 
/*     */       
/* 423 */       Class[] interfaces = typeClass.getInterfaces();
/* 424 */       if (multipleClasses(interfaces, this.env)) {
/* 425 */         throw new ModelerException("rmimodeler.type.implements.more.than.one.interface", new Object[] { type.getRealName(), interfaces[0].getName(), interfaces[1].getName() });
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 432 */     catch (ClassNotFoundException e) {
/* 433 */       throw new ModelerException("rmimodeler.class.not.found", type.getRealName());
/*     */     } 
/*     */ 
/*     */     
/* 437 */     int startSize = this.soapTypeMap.size();
/* 438 */     int curSize = 0;
/* 439 */     while (curSize != startSize) {
/* 440 */       curSize = startSize;
/* 441 */       Iterator<Map.Entry> iterator = this.soapTypeMap.entrySet().iterator();
/*     */ 
/*     */       
/* 444 */       while (iterator.hasNext()) {
/* 445 */         SOAPType extraType = (SOAPType)((Map.Entry)iterator.next()).getValue();
/* 446 */         if (extraType instanceof SOAPStructureType) {
/* 447 */           JavaStructureType javaType = (JavaStructureType)extraType.getJavaType();
/* 448 */           if (!type.equals(javaType) && isSubclass(javaType.getRealName(), type.getRealName(), this.env.getClassLoader())) {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 453 */             modelHierarchy(typeUri, javaType, type);
/* 454 */             curSize = this.soapTypeMap.size();
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
/* 473 */     SOAPStructureType superSOAPType = (SOAPStructureType)superclassType.getOwner();
/*     */     
/* 475 */     SOAPStructureType soapType = (SOAPStructureType)subclass.getOwner();
/* 476 */     if (soapType.getParentType() != null) {
/* 477 */       if (soapType.getParentType().equals(superSOAPType)) {
/* 478 */         return true;
/*     */       }
/*     */       
/* 481 */       if (isSubclass(soapType.getParentType().getJavaType().getRealName(), superClassName, this.env.getClassLoader())) {
/*     */ 
/*     */         
/* 484 */         SOAPStructureType tmpType = soapType;
/* 485 */         while (tmpType.getParentType() != null) {
/* 486 */           tmpType = tmpType.getParentType();
/* 487 */           if (tmpType.getJavaType().getRealName().equals(superClassName))
/*     */           {
/*     */ 
/*     */             
/* 491 */             return true; } 
/* 492 */           if (!isSubclass(tmpType.getJavaType().getRealName(), superClassName, this.env.getClassLoader()))
/*     */           {
/*     */             
/* 495 */             throw new ModelerException("rmimodeler.type.is.used.as.two.types", new Object[] { subclass.getRealName(), tmpType.getJavaType().getRealName(), superClassName });
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 503 */         return modelHierarchy(typeUri, (JavaStructureType)tmpType.getJavaType(), superclassType);
/*     */       } 
/*     */ 
/*     */       
/* 507 */       if (!isSubclass(superClassName, soapType.getParentType().getJavaType().getRealName(), this.env.getClassLoader())) {
/*     */ 
/*     */ 
/*     */         
/* 511 */         if (soapType.getParentType().getJavaType().getRealName().equals(superClassName))
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 516 */           return true;
/*     */         }
/* 518 */         throw new ModelerException("rmimodeler.type.is.used.as.two.types", new Object[] { subclass.getRealName(), soapType.getParentType().getJavaType().getRealName(), superClassName });
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 528 */       Class subclassClass = RmiUtils.getClassForName(subclass.getRealName(), this.env.getClassLoader());
/*     */ 
/*     */ 
/*     */       
/* 532 */       Class superclass = subclassClass.getSuperclass();
/*     */       
/* 534 */       if (superclass == null || (!superclass.getName().equals(superClassName) && !isSubclass(superclass.getName(), superClassName, this.env.getClassLoader()))) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 539 */         Class[] interfaces = subclassClass.getInterfaces();
/* 540 */         for (int i = 0; i < interfaces.length; i++) {
/* 541 */           if (interfaces[i].getName().equals(superClassName)) {
/* 542 */             superSOAPType.addSubtype((SOAPStructureType)subclass.getOwner());
/*     */             
/* 544 */             ((JavaStructureType)superSOAPType.getJavaType()).addSubclass(subclass);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 549 */             return true;
/*     */           } 
/* 551 */           if (isSubclass(interfaces[i].getName(), superClassName, this.env.getClassLoader())) {
/*     */ 
/*     */             
/* 554 */             RmiType rmiType = RmiType.getRmiType(interfaces[i]);
/* 555 */             SOAPStructureType sOAPStructureType = (SOAPStructureType)modelTypeSOAP(typeUri, rmiType);
/*     */             
/* 557 */             sOAPStructureType.addSubtype((SOAPStructureType)subclass.getOwner());
/*     */             
/* 559 */             ((JavaStructureType)sOAPStructureType.getJavaType()).addSubclass(subclass);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 564 */             return modelHierarchy(typeUri, (JavaStructureType)sOAPStructureType.getJavaType(), superclassType);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 572 */       RmiType type = RmiType.getRmiType(superclass);
/* 573 */       SOAPStructureType superType = (SOAPStructureType)modelTypeSOAP(typeUri, type);
/*     */       
/* 575 */       superType.addSubtype((SOAPStructureType)subclass.getOwner());
/* 576 */       ((JavaStructureType)superType.getJavaType()).addSubclass(subclass);
/*     */       
/* 578 */       if (!superclass.getName().equals(superClassName)) {
/* 579 */         return modelHierarchy(typeUri, (JavaStructureType)superType.getJavaType(), superclassType);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 584 */       return true;
/* 585 */     } catch (ClassNotFoundException e) {
/* 586 */       throw new ModelerException("rmimodeler.class.not.found", subclass.getRealName());
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
/* 597 */     String className = subtypeName;
/* 598 */     if (subtypeName.equals(supertypeName))
/* 599 */       return false; 
/*     */     try {
/* 601 */       Class<?> subClass = RmiUtils.getClassForName(className, classLoader);
/* 602 */       className = supertypeName;
/* 603 */       Class<?> supertypeClass = Class.forName(className, true, classLoader);
/* 604 */       return supertypeClass.isAssignableFrom(subClass);
/* 605 */     } catch (ClassNotFoundException e) {
/* 606 */       throw new ModelerException("rmimodeler.class.not.found", className);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static RmiType getHolderValueType(ProcessorEnvironment env, Class defHolder, RmiType type) {
/* 615 */     if (type.getTypeCode() != 10) {
/* 616 */       return null;
/*     */     }
/* 618 */     Class def = null;
/*     */     try {
/* 620 */       def = type.getTypeClass(env.getClassLoader());
/* 621 */     } catch (ClassNotFoundException e) {
/* 622 */       throw new ModelerException("rmimodeler.class.not.found", type.toString());
/*     */     } 
/*     */ 
/*     */     
/* 626 */     Class[] interfaces = def.getInterfaces();
/* 627 */     for (int i = 0; i < interfaces.length; i++) {
/* 628 */       Class interfaceDef = interfaces[i];
/* 629 */       if (defHolder.isAssignableFrom(interfaces[i])) {
/* 630 */         Field member = getValueMember(env, def);
/* 631 */         return (member != null) ? RmiType.getRmiType(member.getType()) : null;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 636 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field getValueMember(ProcessorEnvironment env, Class classDef) {
/* 643 */     Field member = null;
/*     */     try {
/* 645 */       member = classDef.getDeclaredField("value");
/* 646 */     } catch (NoSuchFieldException e) {}
/*     */     
/* 648 */     if (member == null) {
/* 649 */       Class superDec = classDef.getSuperclass();
/* 650 */       member = getValueMember(env, superDec);
/*     */     } 
/* 652 */     return member;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SOAPType processTypeMapping(ProcessorEnvironment env, TypeMappingInfo typeMapping) {
/* 659 */     log(env, "creating custom type for: " + typeMapping.getJavaTypeName());
/*     */     
/* 661 */     SOAPCustomType soapType = new SOAPCustomType(typeMapping.getXMLType(), this.soapVersion);
/*     */     
/* 663 */     JavaCustomType javaType = new JavaCustomType(typeMapping.getJavaTypeName(), typeMapping);
/*     */     
/* 665 */     soapType.setJavaType((JavaType)javaType);
/* 666 */     mapSOAPType(javaType.getRealName(), (SOAPType)soapType);
/* 667 */     return (SOAPType)soapType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fillInStructure(String typeUri, SOAPStructureType struct, JavaStructureType javaStruct, List sortedMembers, RmiType type) {
/* 677 */     ProcessorEnvironment env = this.modeler.getProcessorEnvironment();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 682 */     for (Iterator<MemberInfo> iter = sortedMembers.iterator(); iter.hasNext(); ) {
/* 683 */       MemberInfo memInfo = iter.next();
/*     */       
/* 685 */       SOAPStructureMember member = new SOAPStructureMember(new QName(null, memInfo.getName()), modelTypeSOAP(typeUri, memInfo.getType()));
/*     */ 
/*     */ 
/*     */       
/* 689 */       JavaStructureMember javaMember = new JavaStructureMember(memInfo.getName(), member.getType().getJavaType(), member, memInfo.isPublic());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 695 */       member.setJavaStructureMember(javaMember);
/* 696 */       javaMember.setReadMethod(memInfo.getReadMethod());
/* 697 */       javaMember.setWriteMethod(memInfo.getWriteMethod());
/* 698 */       if (memInfo.getDeclaringClass() != null) {
/* 699 */         javaMember.setDeclaringClass(memInfo.getDeclaringClass().getName());
/*     */       }
/*     */       
/* 702 */       javaStruct.add(javaMember);
/* 703 */       struct.add(member);
/*     */     } 
/* 705 */     markInheritedMembers(env, struct);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void markInheritedMembers(ProcessorEnvironment env, SOAPStructureType struct) {
/* 712 */     String className = struct.getJavaType().getRealName();
/*     */     try {
/* 714 */       Iterator<SOAPStructureMember> members = struct.getMembers();
/*     */ 
/*     */       
/* 717 */       Class javaClass = RmiUtils.getClassForName(className, env.getClassLoader());
/*     */       
/* 719 */       if (javaClass.isInterface() || Modifier.isAbstract(javaClass.getModifiers()))
/*     */       {
/* 721 */         ((JavaStructureType)struct.getJavaType()).setAbstract(true);
/*     */       }
/* 723 */       Class superclass = javaClass.getSuperclass();
/* 724 */       while (members.hasNext()) {
/* 725 */         SOAPStructureMember soapMember = members.next();
/* 726 */         JavaStructureMember javaMember = soapMember.getJavaStructureMember();
/* 727 */         if (javaMember.isPublic()) {
/*     */           try {
/* 729 */             Field field = javaClass.getDeclaredField(javaMember.getName());
/*     */             
/* 731 */             if (!field.getDeclaringClass().equals(javaClass)) {
/* 732 */               javaMember.setInherited(true);
/* 733 */               soapMember.setInherited(true);
/*     */             } 
/* 735 */           } catch (NoSuchFieldException e) {
/* 736 */             javaMember.setInherited(true);
/* 737 */             soapMember.setInherited(true);
/*     */           } 
/*     */           continue;
/*     */         } 
/* 741 */         String methodName = javaMember.getReadMethod();
/* 742 */         Class[] args = new Class[0];
/* 743 */         boolean isInherited = isMethodInherited(methodName, args, javaClass);
/*     */         
/* 745 */         methodName = javaMember.getWriteMethod();
/* 746 */         if (methodName != null) {
/* 747 */           isInherited = isInherited ? isMethodInherited(methodName, args, javaClass) : false;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 752 */         if (isInherited) {
/* 753 */           javaMember.setInherited(true);
/* 754 */           soapMember.setInherited(true);
/*     */         }
/*     */       
/*     */       } 
/* 758 */     } catch (ClassNotFoundException e) {
/* 759 */       throw new ModelerException("rmimodeler.class.not.found", className);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isMethodInherited(String methodName, Class[] args, Class javaClass) {
/* 768 */     return (methodMemberClass(methodName, args, javaClass) != javaClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class methodMemberClass(String methodName, Class[] args, Class javaClass) {
/* 776 */     Class retClass = null;
/* 777 */     Class superclass = javaClass.getSuperclass();
/* 778 */     if (superclass != null && (
/* 779 */       retClass = methodMemberClass(methodName, args, superclass)) != null)
/*     */     {
/* 781 */       return retClass;
/*     */     }
/*     */     
/* 784 */     Class[] interfaces = javaClass.getInterfaces();
/* 785 */     for (int i = 0; i < interfaces.length; i++) {
/* 786 */       if ((retClass = methodMemberClass(methodName, args, interfaces[i])) != null)
/*     */       {
/* 788 */         return retClass;
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 793 */       Method method = javaClass.getDeclaredMethod(methodName, args);
/* 794 */       return javaClass;
/* 795 */     } catch (NoSuchMethodException e) {
/*     */       
/* 797 */       return retClass;
/*     */     } 
/*     */   }
/*     */   public void modelSubclasses(String typeUri) {
/* 801 */     Set<SOAPType> abstractTypes = new HashSet();
/* 802 */     int startSize = this.soapTypeMap.size();
/* 803 */     Iterator<Map.Entry> iter = this.soapTypeMap.entrySet().iterator();
/*     */     
/* 805 */     while (iter.hasNext()) {
/* 806 */       SOAPType type = (SOAPType)((Map.Entry)iter.next()).getValue();
/* 807 */       if (type instanceof SOAPStructureType) {
/* 808 */         abstractTypes.add(type);
/*     */       }
/*     */     } 
/* 811 */     iter = (Iterator)abstractTypes.iterator();
/* 812 */     while (iter.hasNext()) {
/* 813 */       SOAPType type = (SOAPType)iter.next();
/* 814 */       modelSubclasses(typeUri, (JavaStructureType)type.getJavaType());
/*     */     } 
/* 816 */     if (startSize != this.soapTypeMap.size()) {
/* 817 */       iter = this.soapTypeMap.entrySet().iterator();
/* 818 */       while (iter.hasNext()) {
/* 819 */         SOAPType type = (SOAPType)((Map.Entry)iter.next()).getValue();
/* 820 */         if (type instanceof SOAPStructureType && ((JavaStructureType)type.getJavaType()).isAbstract() && !abstractTypes.contains(type))
/*     */         {
/*     */ 
/*     */           
/* 824 */           modelSubclasses(typeUri, (JavaStructureType)type.getJavaType());
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
/*     */   private SOAPType getMappedSoapType(RmiType type) {
/* 837 */     String name = type.toString();
/* 838 */     return (SOAPType)this.soapTypeMap.get(name);
/*     */   }
/*     */   
/*     */   private void mapSOAPType(RmiType type, SOAPType soapType) {
/* 842 */     mapSOAPType(type.toString(), soapType);
/*     */   }
/*     */   
/*     */   private void mapSOAPType(String name, SOAPType soapType) {
/* 846 */     this.soapTypeMap.put(name, soapType);
/*     */   }
/*     */   
/*     */   private void addTypeName(QName typeName, String javaType) {
/* 850 */     if (this.typeNames.contains(typeName)) {
/* 851 */       throw new ModelerException("rmimodeler.duplicate.type.name", new Object[] { typeName.toString(), javaType });
/*     */     }
/*     */ 
/*     */     
/* 855 */     this.typeNames.add(typeName);
/*     */   }
/*     */   
/*     */   public SOAPVersion getSOAPVersion() {
/* 859 */     return this.soapVersion;
/*     */   }
/*     */   
/*     */   private static void log(ProcessorEnvironment env, String msg) {
/* 863 */     if (env.verbose()) {
/* 864 */       System.out.println("[RmiTypeModeler: " + msg + "]");
/*     */     }
/*     */   }
/*     */   
/*     */   public void initializeTypeMap(Map typeMap) {
/* 869 */     this.soapTypes.initializeTypeMap(typeMap);
/*     */   }
/*     */   
/*     */   protected RmiTypeModeler(RmiModeler modeler, ProcessorEnvironment env) {
/* 873 */     this(modeler, env, SOAPVersion.SOAP_11);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RmiTypeModeler(RmiModeler modeler, ProcessorEnvironment env, SOAPVersion soapVersion) {
/* 901 */     this.typeNames = new HashSet();
/*     */     this.modeler = modeler;
/*     */     this.env = env;
/*     */     this.typeMappingRegistry = modeler.getTypeMappingRegistryInfo();
/*     */     this.typeNames = new HashSet();
/*     */     this.soapTypeMap = new HashMap<Object, Object>();
/*     */     this.soapVersion = soapVersion;
/*     */     this.soapEncodingConstants = SOAPConstantsFactory.getSOAPEncodingConstants(soapVersion);
/*     */     this.soapTypes = JAXRPCClassFactory.newInstance().createSOAPSimpleTypeCreator(modeler.isStrictCompliant(), soapVersion);
/*     */     initializeTypeMap(this.soapTypeMap);
/*     */     this.targetVersion = modeler.getOptions().getProperty("sourceVersion");
/* 912 */   } private static final Set boxedPrimitiveSet = new HashSet();
/* 913 */   private static final Set optionalTypes = new HashSet();
/*     */   static {
/* 915 */     boxedPrimitiveSet.add("java.lang.Boolean");
/* 916 */     boxedPrimitiveSet.add("java.lang.Byte");
/* 917 */     boxedPrimitiveSet.add("java.lang.Double");
/* 918 */     boxedPrimitiveSet.add("java.lang.Float");
/* 919 */     boxedPrimitiveSet.add("java.lang.Integer");
/* 920 */     boxedPrimitiveSet.add("java.lang.Long");
/* 921 */     boxedPrimitiveSet.add("java.lang.Short");
/*     */     
/* 923 */     optionalTypes.add("java.lang.Object");
/*     */ 
/*     */     
/* 926 */     optionalTypes.add("java.util.Collection");
/* 927 */     optionalTypes.add("java.util.List");
/* 928 */     optionalTypes.add("java.util.Set");
/* 929 */     optionalTypes.add("java.util.Vector");
/* 930 */     optionalTypes.add("java.util.Stack");
/* 931 */     optionalTypes.add("java.util.LinkedList");
/* 932 */     optionalTypes.add("java.util.ArrayList");
/* 933 */     optionalTypes.add("java.util.HashSet");
/* 934 */     optionalTypes.add("java.util.TreeSet");
/*     */ 
/*     */     
/* 937 */     optionalTypes.add("java.util.Map");
/* 938 */     optionalTypes.add("java.util.HashMap");
/* 939 */     optionalTypes.add("java.util.TreeMap");
/* 940 */     optionalTypes.add("java.util.Hashtable");
/* 941 */     optionalTypes.add("java.util.Properties");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MemberInfoComparator
/*     */     implements Comparator
/*     */   {
/*     */     public int compare(Object o1, Object o2) {
/* 951 */       MemberInfo mem1 = (MemberInfo)o1;
/* 952 */       MemberInfo mem2 = (MemberInfo)o2;
/* 953 */       return sort(mem1, mem2);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int sort(MemberInfo mem1, MemberInfo mem2) {
/* 958 */       String key1 = mem1.getName();
/* 959 */       String key2 = mem2.getName();
/* 960 */       Class<?> class1 = mem1.getSortingClass();
/* 961 */       Class<?> class2 = mem2.getSortingClass();
/* 962 */       if (class1.equals(class2)) {
/* 963 */         return key1.compareTo(key2);
/*     */       }
/* 965 */       if (class1.isAssignableFrom(class2) && !class2.isAssignableFrom(class1))
/*     */       {
/* 967 */         return -1;
/*     */       }
/* 969 */       return 1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processor\modeler\rmi\RmiTypeModeler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */