/*     */ package org.glassfish.gmbal.impl;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.ReflectPermission;
/*     */ import java.security.AccessController;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import javax.management.Attribute;
/*     */ import javax.management.AttributeChangeNotification;
/*     */ import javax.management.AttributeList;
/*     */ import javax.management.AttributeNotFoundException;
/*     */ import javax.management.Descriptor;
/*     */ import javax.management.InvalidAttributeValueException;
/*     */ import javax.management.JMException;
/*     */ import javax.management.MBeanException;
/*     */ import javax.management.MBeanParameterInfo;
/*     */ import javax.management.NotificationBroadcasterSupport;
/*     */ import javax.management.ReflectionException;
/*     */ import javax.management.modelmbean.ModelMBeanAttributeInfo;
/*     */ import javax.management.modelmbean.ModelMBeanInfoSupport;
/*     */ import javax.management.modelmbean.ModelMBeanOperationInfo;
/*     */ import javax.management.openmbean.OpenMBeanParameterInfoSupport;
/*     */ import org.glassfish.gmbal.AMXMetadata;
/*     */ import org.glassfish.gmbal.ManagedOperation;
/*     */ import org.glassfish.gmbal.NameValue;
/*     */ import org.glassfish.gmbal.ParameterNames;
/*     */ import org.glassfish.gmbal.generic.BinaryFunction;
/*     */ import org.glassfish.gmbal.generic.DumpIgnore;
/*     */ import org.glassfish.gmbal.generic.DumpToString;
/*     */ import org.glassfish.gmbal.generic.FacetAccessor;
/*     */ import org.glassfish.gmbal.generic.MethodMonitor;
/*     */ import org.glassfish.gmbal.generic.MethodMonitorFactory;
/*     */ import org.glassfish.gmbal.generic.Pair;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedAccessibleDeclaration;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedClassAnalyzer;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedClassDeclaration;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedDeclaration;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedFieldDeclaration;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedMethodDeclaration;
/*     */ import org.glassfish.gmbal.typelib.EvaluatedType;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MBeanSkeleton
/*     */ {
/*  96 */   private static Descriptor DEFAULT_AMX_DESCRIPTOR = DescriptorIntrospector.descriptorForElement(null, ManagedObjectManagerImpl.DefaultAMXMetadataHolder.class);
/*     */   
/*     */   private AMXMetadata mbeanType;
/*     */   
/*     */   private final String type;
/*     */   
/*     */   private Descriptor descriptor;
/*     */   
/*     */   @DumpToString
/*     */   private final AtomicLong sequenceNumber;
/*     */   
/*     */   @DumpToString
/*     */   private final ManagedObjectManagerInternal mom;
/*     */   
/*     */   @DumpIgnore
/*     */   private final MethodMonitor mm;
/*     */   
/*     */   private final Map<String, AttributeDescriptor> setters;
/*     */   
/*     */   private final Map<String, AttributeDescriptor> getters;
/*     */   
/*     */   private AttributeDescriptor nameAttributeDescriptor;
/*     */   private final Map<String, Map<List<String>, Operation>> operations;
/*     */   private final List<ModelMBeanAttributeInfo> mbeanAttributeInfoList;
/*     */   private final List<ModelMBeanOperationInfo> mbeanOperationInfoList;
/*     */   private final ModelMBeanInfoSupport mbInfo;
/*     */   
/*     */   private <K, L, V> void addToCompoundMap(Map<K, Map<L, V>> source, Map<K, Map<L, V>> dest) {
/* 124 */     for (Map.Entry<K, Map<L, V>> entry : source.entrySet()) {
/* 125 */       Map<L, V> dmap = dest.get(entry.getKey());
/* 126 */       if (dmap == null) {
/* 127 */         dmap = new HashMap<L, V>();
/* 128 */         dest.put(entry.getKey(), dmap);
/*     */       } 
/* 130 */       dmap.putAll(entry.getValue());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MBeanSkeleton(EvaluatedClassDeclaration annotatedClass, EvaluatedClassAnalyzer ca, ManagedObjectManagerInternal mom) {
/* 138 */     boolean isDefaultAMXMetadata = false;
/* 139 */     this.mbeanType = mom.<AMXMetadata>getFirstAnnotationOnClass(annotatedClass, AMXMetadata.class);
/* 140 */     if (this.mbeanType == null) {
/* 141 */       isDefaultAMXMetadata = true;
/* 142 */       this.mbeanType = mom.getDefaultAMXMetadata();
/*     */     } 
/*     */     
/* 145 */     this.type = mom.getTypeName(annotatedClass.cls(), "AMX_TYPE", this.mbeanType.type());
/*     */ 
/*     */     
/* 148 */     Descriptor ldesc = DescriptorIntrospector.descriptorForElement(mom, annotatedClass.cls());
/*     */ 
/*     */     
/* 151 */     if (isDefaultAMXMetadata)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 156 */       ldesc = DescriptorUtility.union(new Descriptor[] { DEFAULT_AMX_DESCRIPTOR, ldesc });
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 161 */     this.descriptor = makeValidDescriptor(ldesc, DescriptorType.mbean, this.type);
/*     */     
/* 163 */     this.sequenceNumber = new AtomicLong();
/*     */     
/* 165 */     this.mom = mom;
/*     */     
/* 167 */     this.mm = MethodMonitorFactory.makeStandard(getClass());
/*     */     
/* 169 */     this.setters = new HashMap<String, AttributeDescriptor>();
/* 170 */     this.getters = new HashMap<String, AttributeDescriptor>();
/* 171 */     this.operations = new HashMap<String, Map<List<String>, Operation>>();
/* 172 */     this.mbeanAttributeInfoList = new ArrayList<ModelMBeanAttributeInfo>();
/* 173 */     this.mbeanOperationInfoList = new ArrayList<ModelMBeanOperationInfo>();
/*     */     
/* 175 */     analyzeAttributes(ca);
/* 176 */     analyzeOperations(ca);
/* 177 */     analyzeObjectNameKeys(ca);
/*     */     
/* 179 */     this.mbInfo = makeMbInfo(mom.getDescription((EvaluatedDeclaration)annotatedClass));
/*     */   }
/*     */ 
/*     */   
/*     */   private MBeanSkeleton(MBeanSkeleton first, MBeanSkeleton second) {
/* 184 */     this.mbeanType = second.mbeanType;
/*     */     
/* 186 */     this.type = second.type;
/*     */     
/* 188 */     this.descriptor = DescriptorUtility.union(new Descriptor[] { first.descriptor, second.descriptor });
/*     */ 
/*     */     
/* 191 */     this.sequenceNumber = new AtomicLong();
/*     */     
/* 193 */     this.mom = second.mom;
/*     */     
/* 195 */     this.mm = MethodMonitorFactory.makeStandard(getClass());
/*     */     
/* 197 */     this.setters = new HashMap<String, AttributeDescriptor>();
/* 198 */     this.setters.putAll(first.setters);
/* 199 */     this.setters.putAll(second.setters);
/*     */     
/* 201 */     this.getters = new HashMap<String, AttributeDescriptor>();
/* 202 */     this.getters.putAll(first.getters);
/* 203 */     this.getters.putAll(second.getters);
/*     */     
/* 205 */     this.nameAttributeDescriptor = second.nameAttributeDescriptor;
/*     */     
/* 207 */     this.operations = new HashMap<String, Map<List<String>, Operation>>();
/* 208 */     addToCompoundMap(first.operations, this.operations);
/* 209 */     addToCompoundMap(second.operations, this.operations);
/*     */     
/* 211 */     this.mbeanAttributeInfoList = new ArrayList<ModelMBeanAttributeInfo>();
/* 212 */     this.mbeanAttributeInfoList.addAll(first.mbeanAttributeInfoList);
/* 213 */     this.mbeanAttributeInfoList.addAll(second.mbeanAttributeInfoList);
/*     */     
/* 215 */     this.mbeanOperationInfoList = new ArrayList<ModelMBeanOperationInfo>();
/* 216 */     this.mbeanOperationInfoList.addAll(first.mbeanOperationInfoList);
/* 217 */     this.mbeanOperationInfoList.addAll(second.mbeanOperationInfoList);
/*     */ 
/*     */ 
/*     */     
/* 221 */     this.mbInfo = makeMbInfo(second.mbInfo.getDescription());
/*     */   }
/*     */   
/*     */   private ModelMBeanInfoSupport makeMbInfo(String description) {
/* 225 */     ModelMBeanAttributeInfo[] attrInfos = this.mbeanAttributeInfoList.<ModelMBeanAttributeInfo>toArray(new ModelMBeanAttributeInfo[this.mbeanAttributeInfoList.size()]);
/*     */     
/* 227 */     ModelMBeanOperationInfo[] operInfos = this.mbeanOperationInfoList.<ModelMBeanOperationInfo>toArray(new ModelMBeanOperationInfo[this.mbeanOperationInfoList.size()]);
/*     */ 
/*     */     
/* 230 */     return new ModelMBeanInfoSupport(this.type, description, attrInfos, null, operInfos, null, this.descriptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MBeanSkeleton compose(MBeanSkeleton skel) {
/* 239 */     return new MBeanSkeleton(this, skel);
/*     */   }
/*     */   
/* 242 */   private enum DescriptorType { mbean, attribute, operation; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Descriptor makeValidDescriptor(Descriptor desc, DescriptorType dtype, String dname) {
/* 249 */     Map<String, Object> map = new HashMap<String, Object>();
/* 250 */     String[] names = desc.getFieldNames();
/* 251 */     Object[] values = desc.getFieldValues((String[])null);
/* 252 */     for (int ctr = 0; ctr < names.length; ctr++) {
/* 253 */       map.put(names[ctr], values[ctr]);
/*     */     }
/*     */     
/* 256 */     map.put("descriptorType", dtype.toString());
/* 257 */     if (dtype == DescriptorType.operation) {
/* 258 */       map.put("role", "operation");
/* 259 */       map.put("targetType", "ObjectReference");
/* 260 */     } else if (dtype == DescriptorType.mbean) {
/* 261 */       map.put("persistPolicy", "never");
/* 262 */       map.put("log", "F");
/* 263 */       map.put("visibility", "1");
/*     */     } 
/*     */     
/* 266 */     map.put("name", dname);
/* 267 */     map.put("displayName", dname);
/*     */     
/* 269 */     return DescriptorUtility.makeDescriptor(map);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 274 */     return "DynamicMBeanSkeleton[type" + this.type + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processAttribute(AttributeDescriptor getter, AttributeDescriptor setter) {
/* 281 */     this.mm.enter(this.mom.registrationFineDebug(), "processAttribute", new Object[] { getter, setter });
/*     */ 
/*     */     
/*     */     try {
/* 285 */       if (setter == null && getter == null) {
/* 286 */         throw Exceptions.self.notBothNull();
/*     */       }
/*     */       
/* 289 */       if (setter != null && getter != null && !setter.type().equals(getter.type()))
/*     */       {
/* 291 */         throw Exceptions.self.typesMustMatch();
/*     */       }
/*     */       
/* 294 */       AttributeDescriptor nonNullDescriptor = (getter != null) ? getter : setter;
/*     */ 
/*     */       
/* 297 */       String name = nonNullDescriptor.id();
/* 298 */       String description = nonNullDescriptor.description();
/* 299 */       Descriptor desc = DescriptorUtility.EMPTY_DESCRIPTOR;
/* 300 */       if (getter != null) {
/* 301 */         desc = DescriptorUtility.union(new Descriptor[] { desc, DescriptorIntrospector.descriptorForElement(this.mom, getter.accessible()) });
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 306 */       if (setter != null) {
/* 307 */         desc = DescriptorUtility.union(new Descriptor[] { desc, DescriptorIntrospector.descriptorForElement(this.mom, setter.accessible()) });
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 312 */       desc = makeValidDescriptor(desc, DescriptorType.attribute, name);
/*     */       
/* 314 */       this.mm.info(this.mom.registrationFineDebug(), new Object[] { name, description, desc });
/*     */       
/* 316 */       TypeConverter tc = this.mom.getTypeConverter(nonNullDescriptor.type());
/*     */       
/* 318 */       ModelMBeanAttributeInfo ainfo = new ModelMBeanAttributeInfo(name, tc.getManagedType().getClassName(), description, (getter != null), (setter != null), false, desc);
/*     */ 
/*     */ 
/*     */       
/* 322 */       this.mm.info(this.mom.registrationFineDebug(), new Object[] { ainfo });
/*     */       
/* 324 */       this.mbeanAttributeInfoList.add(ainfo);
/*     */     } finally {
/* 326 */       this.mm.exit(this.mom.registrationFineDebug());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void analyzeAttributes(EvaluatedClassAnalyzer ca) {
/* 331 */     this.mm.enter(this.mom.registrationFineDebug(), "analyzeAttributes", new Object[] { ca });
/*     */     
/*     */     try {
/* 334 */       Pair<Map<String, AttributeDescriptor>, Map<String, AttributeDescriptor>> amap = this.mom.getAttributes(ca, ManagedObjectManagerInternal.AttributeDescriptorType.MBEAN_ATTR);
/*     */ 
/*     */ 
/*     */       
/* 338 */       this.getters.putAll((Map<? extends String, ? extends AttributeDescriptor>)amap.first());
/* 339 */       this.setters.putAll((Map<? extends String, ? extends AttributeDescriptor>)amap.second());
/*     */       
/* 341 */       this.mm.info(this.mom.registrationFineDebug(), new Object[] { "attributes", amap });
/*     */       
/* 343 */       Set<String> setterNames = new HashSet<String>(this.setters.keySet());
/* 344 */       this.mm.info(this.mom.registrationFineDebug(), new Object[] { "(Before removing getters):setterNames=", setterNames });
/*     */ 
/*     */       
/* 347 */       for (String str : this.getters.keySet()) {
/* 348 */         processAttribute(this.getters.get(str), this.setters.get(str));
/* 349 */         setterNames.remove(str);
/*     */       } 
/*     */       
/* 352 */       this.mm.info(this.mom.registrationFineDebug(), new Object[] { "(After removing getters):setterNames=", setterNames });
/*     */ 
/*     */ 
/*     */       
/* 356 */       for (String str : setterNames) {
/* 357 */         processAttribute(null, this.setters.get(str));
/*     */       }
/*     */     } finally {
/* 360 */       this.mm.exit(this.mom.registrationFineDebug());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void analyzeObjectNameKeys(EvaluatedClassAnalyzer ca) {
/* 365 */     this.mm.enter(this.mom.registrationFineDebug(), "analyzeObjectNameKeys", new Object[] { ca });
/*     */     
/*     */     try {
/* 368 */       List<EvaluatedFieldDeclaration> annotatedFields = ca.findFields(this.mom.forAnnotation((Class)NameValue.class, EvaluatedFieldDeclaration.class));
/*     */ 
/*     */ 
/*     */       
/* 372 */       List<EvaluatedMethodDeclaration> annotatedMethods = ca.findMethods(this.mom.forAnnotation((Class)NameValue.class, EvaluatedMethodDeclaration.class));
/*     */ 
/*     */ 
/*     */       
/* 376 */       if (annotatedMethods.size() == 0 && annotatedFields.size() == 0) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 382 */       EvaluatedMethodDeclaration annotatedMethod = annotatedMethods.get(0);
/* 383 */       if (annotatedMethods.size() > 1) {
/* 384 */         EvaluatedMethodDeclaration second = annotatedMethods.get(1);
/*     */         
/* 386 */         if (annotatedMethod.containingClass().equals(second.containingClass()))
/*     */         {
/*     */           
/* 389 */           throw Exceptions.self.duplicateObjectNameKeyAttributes(annotatedMethod, second, annotatedMethod.containingClass().name());
/*     */         }
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 395 */       this.mm.info(this.mom.registrationFineDebug(), new Object[] { "annotatedMethod", annotatedMethod });
/*     */ 
/*     */       
/* 398 */       this.nameAttributeDescriptor = AttributeDescriptor.makeFromAnnotated(this.mom, (EvaluatedAccessibleDeclaration)annotatedMethod, "NameValue", Exceptions.self.nameOfManagedObject(), ManagedObjectManagerInternal.AttributeDescriptorType.MBEAN_ATTR);
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 403 */       this.mm.exit(this.mom.registrationFineDebug());
/*     */     } 
/*     */   }
/*     */   
/* 407 */   private static final Permission accessControlPermission = new ReflectPermission("suppressAccessChecks");
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Pair<Operation, ModelMBeanOperationInfo> makeOperation(final EvaluatedMethodDeclaration m) {
/* 413 */     this.mm.enter(this.mom.registrationFineDebug(), "makeOperation", new Object[] { m });
/*     */     
/* 415 */     AccessController.doPrivileged(new PrivilegedAction<Method>()
/*     */         {
/*     */           public Method run() {
/* 418 */             m.method().setAccessible(true);
/* 419 */             return m.method();
/*     */           }
/*     */         });
/*     */     try {
/* 423 */       String desc = this.mom.getDescription((EvaluatedDeclaration)m);
/* 424 */       EvaluatedType rtype = m.returnType();
/* 425 */       final TypeConverter rtc = (rtype == null) ? null : this.mom.getTypeConverter(rtype);
/*     */       
/* 427 */       List<EvaluatedType> atypes = m.parameterTypes();
/* 428 */       final List<TypeConverter> atcs = new ArrayList<TypeConverter>();
/* 429 */       ManagedOperation mo = this.mom.<ManagedOperation>getAnnotation(m.element(), ManagedOperation.class);
/*     */ 
/*     */       
/* 432 */       Descriptor modelDescriptor = makeValidDescriptor(DescriptorIntrospector.descriptorForElement(this.mom, m.element()), DescriptorType.operation, m.name());
/*     */ 
/*     */ 
/*     */       
/* 436 */       for (EvaluatedType ltype : atypes) {
/* 437 */         atcs.add(this.mom.getTypeConverter(ltype));
/*     */       }
/*     */       
/* 440 */       if (this.mom.registrationFineDebug()) {
/* 441 */         this.mm.info(true, new Object[] { "desc", desc });
/* 442 */         this.mm.info(true, new Object[] { "rtype", rtype });
/* 443 */         this.mm.info(true, new Object[] { "rtc", rtc });
/* 444 */         this.mm.info(true, new Object[] { "atcs", atcs });
/* 445 */         this.mm.info(true, new Object[] { "atypes", atypes });
/* 446 */         this.mm.info(true, new Object[] { "descriptor", modelDescriptor });
/*     */       } 
/*     */       
/* 449 */       Operation oper = new Operation() {
/*     */           public Object evaluate(FacetAccessor target, List<Object> args) {
/* 451 */             MBeanSkeleton.this.mm.enter(MBeanSkeleton.this.mom.runtimeDebug(), "Operation:evaluate", new Object[] { target, args });
/*     */ 
/*     */             
/* 454 */             Object[] margs = new Object[args.size()];
/* 455 */             Iterator<Object> argsIterator = args.iterator();
/* 456 */             Iterator<TypeConverter> tcIterator = atcs.iterator();
/* 457 */             int ctr = 0;
/* 458 */             while (argsIterator.hasNext() && tcIterator.hasNext()) {
/* 459 */               Object arg = argsIterator.next();
/* 460 */               TypeConverter tc = tcIterator.next();
/* 461 */               margs[ctr++] = tc.fromManagedEntity(arg);
/*     */             } 
/*     */             
/* 464 */             MBeanSkeleton.this.mm.info(MBeanSkeleton.this.mom.runtimeDebug(), new Object[] { "Before invoke: margs=", margs });
/*     */             
/* 466 */             Object result = target.invoke(m.method(), MBeanSkeleton.this.mom.runtimeDebug(), margs);
/*     */ 
/*     */             
/* 469 */             MBeanSkeleton.this.mm.info(MBeanSkeleton.this.mom.runtimeDebug(), new Object[] { "After invoke: result=", result });
/*     */             
/* 471 */             if (rtc == null) {
/* 472 */               return null;
/*     */             }
/* 474 */             return rtc.toManagedEntity(result);
/*     */           }
/*     */         };
/*     */ 
/*     */       
/* 479 */       ParameterNames pna = this.mom.<ParameterNames>getAnnotation(m.element(), ParameterNames.class);
/*     */       
/* 481 */       this.mm.info(this.mom.registrationFineDebug(), new Object[] { "pna", pna });
/*     */       
/* 483 */       if (pna != null && (pna.value()).length != atcs.size()) {
/* 484 */         throw Exceptions.self.parameterNamesLengthBad();
/*     */       }
/*     */       
/* 487 */       OpenMBeanParameterInfoSupport[] arrayOfOpenMBeanParameterInfoSupport = new OpenMBeanParameterInfoSupport[atcs.size()];
/*     */       
/* 489 */       int ctr = 0;
/* 490 */       for (TypeConverter tc : atcs) {
/* 491 */         String name = "";
/*     */         try {
/* 493 */           name = (pna == null) ? ("arg" + ctr) : pna.value()[ctr];
/* 494 */           arrayOfOpenMBeanParameterInfoSupport[ctr] = new OpenMBeanParameterInfoSupport(name, Exceptions.self.noDescriptionAvailable(), tc.getManagedType());
/*     */ 
/*     */           
/* 497 */           ctr++;
/* 498 */         } catch (IllegalArgumentException ex) {
/* 499 */           Exceptions.self.excInOpenParameterInfo(ex, name, m);
/*     */         } 
/*     */       } 
/*     */       
/* 503 */       ModelMBeanOperationInfo operInfo = new ModelMBeanOperationInfo(m.name(), desc, (MBeanParameterInfo[])arrayOfOpenMBeanParameterInfoSupport, rtc.getManagedType().getClassName(), mo.impact().ordinal(), modelDescriptor);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 508 */       this.mm.info(this.mom.registrationFineDebug(), new Object[] { "operInfo", operInfo });
/*     */       
/* 510 */       return new Pair(oper, operInfo);
/*     */     } finally {
/* 512 */       this.mm.exit(this.mom.registrationFineDebug());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void analyzeOperations(EvaluatedClassAnalyzer ca) {
/* 517 */     this.mm.enter(this.mom.registrationFineDebug(), "analyzeOperations", new Object[] { ca });
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 522 */       List<EvaluatedMethodDeclaration> ops = ca.findMethods(this.mom.forAnnotation((Class)ManagedOperation.class, EvaluatedMethodDeclaration.class));
/*     */       
/* 524 */       for (EvaluatedMethodDeclaration m : ops) {
/* 525 */         Pair<Operation, ModelMBeanOperationInfo> data = makeOperation(m);
/*     */         
/* 527 */         ModelMBeanOperationInfo info = (ModelMBeanOperationInfo)data.second();
/*     */         
/* 529 */         List<String> dataTypes = new ArrayList<String>();
/* 530 */         for (MBeanParameterInfo pi : info.getSignature())
/*     */         {
/* 532 */           dataTypes.add(pi.getType());
/*     */         }
/*     */         
/* 535 */         Map<List<String>, Operation> map = this.operations.get(m.name());
/* 536 */         if (map == null) {
/* 537 */           map = new HashMap<List<String>, Operation>();
/* 538 */           this.operations.put(m.name(), map);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 543 */         this.mom.putIfNotPresent((Map)map, dataTypes, data.first());
/*     */         
/* 545 */         this.mbeanOperationInfoList.add(info);
/*     */       } 
/*     */     } finally {
/* 548 */       this.mm.exit(this.mom.registrationFineDebug());
/*     */     } 
/*     */   }
/*     */   public static interface Operation extends BinaryFunction<FacetAccessor, List<Object>, Object> {}
/*     */   
/*     */   public String getType() {
/* 554 */     return this.type;
/*     */   }
/*     */   
/*     */   public AMXMetadata getMBeanType() {
/* 558 */     return this.mbeanType;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getAttribute(FacetAccessor fa, String name) throws AttributeNotFoundException, MBeanException, ReflectionException {
/* 564 */     this.mm.enter(this.mom.runtimeDebug(), "getAttribute", new Object[] { fa, name });
/*     */     
/* 566 */     Object result = null;
/*     */     try {
/* 568 */       AttributeDescriptor getter = this.getters.get(name);
/* 569 */       if (getter == null) {
/* 570 */         throw Exceptions.self.couldNotFindAttribute(name);
/*     */       }
/* 572 */       result = getter.get(fa, this.mom.runtimeDebug());
/*     */     } finally {
/* 574 */       this.mm.exit(this.mom.runtimeDebug(), result);
/*     */     } 
/*     */     
/* 577 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(NotificationBroadcasterSupport emitter, FacetAccessor fa, Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
/* 585 */     this.mm.enter(this.mom.runtimeDebug(), "setAttribute", new Object[] { emitter, fa, attribute });
/*     */     
/*     */     try {
/* 588 */       String name = attribute.getName();
/* 589 */       Object value = attribute.getValue();
/* 590 */       AttributeDescriptor getter = this.getters.get(name);
/* 591 */       Object oldValue = (getter == null) ? null : getter.get(fa, this.mom.runtimeDebug());
/*     */ 
/*     */       
/* 594 */       this.mm.info(this.mom.runtimeDebug(), new Object[] { "oldValue", oldValue });
/*     */       
/* 596 */       AttributeDescriptor setter = this.setters.get(name);
/* 597 */       if (setter == null) {
/* 598 */         throw Exceptions.self.couldNotFindWritableAttribute(name);
/*     */       }
/*     */       
/* 601 */       setter.set(fa, value, this.mom.runtimeDebug());
/*     */       
/* 603 */       AttributeChangeNotification notification = new AttributeChangeNotification(emitter, this.sequenceNumber.incrementAndGet(), System.currentTimeMillis(), "Changed attribute " + name, name, setter.tc().getManagedType().getClassName(), oldValue, value);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 611 */       this.mm.info(this.mom.runtimeDebug(), new Object[] { "sending notification ", notification });
/*     */       
/* 613 */       emitter.sendNotification(notification);
/*     */     } finally {
/* 615 */       this.mm.exit(this.mom.runtimeDebug());
/*     */     } 
/*     */   }
/*     */   
/*     */   public AttributeList getAttributes(FacetAccessor fa, String[] attributes) {
/* 620 */     this.mm.enter(this.mom.runtimeDebug(), "getAttributes", new Object[] { attributes });
/*     */     
/*     */     try {
/* 623 */       AttributeList result = new AttributeList();
/* 624 */       for (String str : attributes) {
/* 625 */         Object value = null;
/*     */         
/*     */         try {
/* 628 */           value = getAttribute(fa, str);
/* 629 */         } catch (JMException ex) {
/* 630 */           Exceptions.self.attributeGettingError(ex, str);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 638 */         if (value != null) {
/* 639 */           Attribute attr = new Attribute(str, value);
/* 640 */           result.add(attr);
/*     */         } 
/*     */       } 
/*     */       
/* 644 */       return result;
/*     */     } finally {
/* 646 */       this.mm.exit(this.mom.runtimeDebug());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AttributeList setAttributes(NotificationBroadcasterSupport emitter, FacetAccessor fa, AttributeList attributes) {
/* 654 */     this.mm.enter(this.mom.runtimeDebug(), "setAttributes", new Object[] { emitter, fa, attributes });
/*     */     
/* 656 */     AttributeList result = new AttributeList();
/*     */     
/*     */     try {
/* 659 */       for (Object elem : attributes) {
/* 660 */         Attribute attr = (Attribute)elem;
/*     */         
/*     */         try {
/* 663 */           setAttribute(emitter, fa, attr);
/* 664 */           result.add(attr);
/* 665 */         } catch (JMException ex) {
/* 666 */           Exceptions.self.attributeSettingError(ex, attr.getName());
/*     */         } 
/*     */       } 
/*     */       
/* 670 */       return result;
/*     */     } finally {
/* 672 */       this.mm.exit(this.mom.runtimeDebug(), result);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Object invoke(FacetAccessor fa, String actionName, Object[] params, String[] sig) throws MBeanException, ReflectionException {
/* 679 */     List<String> signature = Arrays.asList(sig);
/* 680 */     List<Object> parameters = Arrays.asList(params);
/* 681 */     Object result = null;
/*     */     
/* 683 */     this.mm.enter(this.mom.runtimeDebug(), "invoke", new Object[] { fa, actionName, parameters, signature });
/*     */ 
/*     */     
/*     */     try {
/* 687 */       Map<List<String>, Operation> opMap = this.operations.get(actionName);
/*     */       
/* 689 */       if (opMap == null) {
/* 690 */         throw Exceptions.self.couldNotFindOperation(actionName);
/*     */       }
/*     */       
/* 693 */       Operation op = opMap.get(signature);
/* 694 */       if (op == null) {
/* 695 */         throw Exceptions.self.couldNotFindOperationAndSignature(actionName, signature);
/*     */       }
/*     */ 
/*     */       
/* 699 */       result = op.evaluate(fa, parameters);
/*     */     } finally {
/* 701 */       this.mm.exit(this.mom.runtimeDebug(), result);
/*     */     } 
/*     */     
/* 704 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNameValue(FacetAccessor fa) throws MBeanException, ReflectionException {
/* 710 */     this.mm.enter(this.mom.runtimeDebug(), "getNameValue", new Object[] { fa });
/*     */     
/* 712 */     String value = null;
/*     */     try {
/* 714 */       if (this.nameAttributeDescriptor == null) {
/* 715 */         this.mm.info(this.mom.runtimeDebug(), new Object[] { "nameAttributeDescriptor is null" });
/*     */       } else {
/* 717 */         value = this.nameAttributeDescriptor.get(fa, this.mom.runtimeDebug()).toString();
/*     */       } 
/*     */     } finally {
/*     */       
/* 721 */       this.mm.exit(this.mom.runtimeDebug(), value);
/*     */     } 
/*     */     
/* 724 */     return value;
/*     */   }
/*     */   
/*     */   public ModelMBeanInfoSupport getMBeanInfo() {
/* 728 */     return this.mbInfo;
/*     */   }
/*     */   
/*     */   public ManagedObjectManagerInternal mom() {
/* 732 */     return this.mom;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\impl\MBeanSkeleton.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */