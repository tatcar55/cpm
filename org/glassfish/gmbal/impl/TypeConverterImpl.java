/*      */ package org.glassfish.gmbal.impl;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Dictionary;
/*      */ import java.util.Enumeration;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javax.management.JMException;
/*      */ import javax.management.ObjectName;
/*      */ import javax.management.openmbean.ArrayType;
/*      */ import javax.management.openmbean.CompositeData;
/*      */ import javax.management.openmbean.CompositeDataSupport;
/*      */ import javax.management.openmbean.CompositeType;
/*      */ import javax.management.openmbean.OpenDataException;
/*      */ import javax.management.openmbean.OpenType;
/*      */ import javax.management.openmbean.SimpleType;
/*      */ import javax.management.openmbean.TabularData;
/*      */ import javax.management.openmbean.TabularDataSupport;
/*      */ import javax.management.openmbean.TabularType;
/*      */ import org.glassfish.gmbal.AMXClient;
/*      */ import org.glassfish.gmbal.ManagedData;
/*      */ import org.glassfish.gmbal.ManagedObject;
/*      */ import org.glassfish.gmbal.generic.Algorithms;
/*      */ import org.glassfish.gmbal.generic.DumpToString;
/*      */ import org.glassfish.gmbal.generic.FacetAccessor;
/*      */ import org.glassfish.gmbal.generic.MethodMonitor;
/*      */ import org.glassfish.gmbal.generic.MethodMonitorFactory;
/*      */ import org.glassfish.gmbal.generic.Pair;
/*      */ import org.glassfish.gmbal.generic.Predicate;
/*      */ import org.glassfish.gmbal.typelib.EvaluatedArrayType;
/*      */ import org.glassfish.gmbal.typelib.EvaluatedClassAnalyzer;
/*      */ import org.glassfish.gmbal.typelib.EvaluatedClassDeclaration;
/*      */ import org.glassfish.gmbal.typelib.EvaluatedDeclaration;
/*      */ import org.glassfish.gmbal.typelib.EvaluatedMethodDeclaration;
/*      */ import org.glassfish.gmbal.typelib.EvaluatedType;
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
/*      */ public abstract class TypeConverterImpl
/*      */   implements TypeConverter
/*      */ {
/*   95 */   private static final Map<EvaluatedType, OpenType> simpleTypeMap = new HashMap<EvaluatedType, OpenType>();
/*      */   
/*   97 */   private static final Map<OpenType, EvaluatedClassDeclaration> simpleOpenTypeMap = new HashMap<OpenType, EvaluatedClassDeclaration>();
/*      */   
/*   99 */   private static final MethodMonitor mm = MethodMonitorFactory.makeStandard(TypeConverterImpl.class);
/*      */ 
/*      */   
/*      */   public static final String NULL_STRING = "<NULL>";
/*      */ 
/*      */   
/*      */   private static void initMaps(OpenType otype, EvaluatedClassDeclaration... types) {
/*  106 */     boolean first = true;
/*  107 */     for (EvaluatedClassDeclaration type : types) {
/*  108 */       simpleTypeMap.put(type, otype);
/*  109 */       if (first) {
/*  110 */         first = false;
/*  111 */         simpleOpenTypeMap.put(otype, type);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   static {
/*  117 */     initMaps(SimpleType.BOOLEAN, new EvaluatedClassDeclaration[] { EvaluatedType.EBOOLEANW, EvaluatedType.EBOOLEAN });
/*  118 */     initMaps(SimpleType.CHARACTER, new EvaluatedClassDeclaration[] { EvaluatedType.ECHARW, EvaluatedType.ECHAR });
/*  119 */     initMaps(SimpleType.INTEGER, new EvaluatedClassDeclaration[] { EvaluatedType.EINTW, EvaluatedType.EINT });
/*  120 */     initMaps(SimpleType.SHORT, new EvaluatedClassDeclaration[] { EvaluatedType.ESHORTW, EvaluatedType.ESHORT });
/*  121 */     initMaps(SimpleType.LONG, new EvaluatedClassDeclaration[] { EvaluatedType.ELONGW, EvaluatedType.ELONG });
/*  122 */     initMaps(SimpleType.BYTE, new EvaluatedClassDeclaration[] { EvaluatedType.EBYTEW, EvaluatedType.EBYTE });
/*  123 */     initMaps(SimpleType.FLOAT, new EvaluatedClassDeclaration[] { EvaluatedType.EFLOATW, EvaluatedType.EFLOAT });
/*  124 */     initMaps(SimpleType.DOUBLE, new EvaluatedClassDeclaration[] { EvaluatedType.EDOUBLEW, EvaluatedType.EDOUBLE });
/*      */     
/*  126 */     initMaps(SimpleType.STRING, new EvaluatedClassDeclaration[] { EvaluatedType.ESTRING });
/*  127 */     initMaps(SimpleType.VOID, new EvaluatedClassDeclaration[] { EvaluatedType.EVOID });
/*  128 */     initMaps(SimpleType.DATE, new EvaluatedClassDeclaration[] { EvaluatedType.EDATE });
/*  129 */     initMaps(SimpleType.OBJECTNAME, new EvaluatedClassDeclaration[] { EvaluatedType.EOBJECT_NAME });
/*  130 */     initMaps(SimpleType.BIGDECIMAL, new EvaluatedClassDeclaration[] { EvaluatedType.EBIG_DECIMAL });
/*  131 */     initMaps(SimpleType.BIGINTEGER, new EvaluatedClassDeclaration[] { EvaluatedType.EBIG_INTEGER });
/*      */   }
/*      */   
/*      */   public static Class getJavaClass(OpenType ot) {
/*  135 */     if (ot instanceof SimpleType) {
/*  136 */       SimpleType st = (SimpleType)ot;
/*  137 */       return ((EvaluatedClassDeclaration)simpleOpenTypeMap.get(st)).cls();
/*  138 */     }  if (ot instanceof ArrayType) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  145 */       ArrayType at = (ArrayType)ot;
/*  146 */       OpenType<?> cot = at.getElementOpenType();
/*  147 */       Class<?> cjt = getJavaClass(cot);
/*  148 */       Object temp = Array.newInstance(cjt, 0);
/*  149 */       return temp.getClass();
/*  150 */     }  if (ot instanceof TabularType)
/*  151 */       return TabularData.class; 
/*  152 */     if (ot instanceof CompositeType) {
/*  153 */       return CompositeData.class;
/*      */     }
/*  155 */     throw Exceptions.self.unsupportedOpenType(ot);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static EvaluatedType canonicalType(EvaluatedType et) {
/*  162 */     OpenType ot = simpleTypeMap.get(et);
/*  163 */     if (ot == null) {
/*  164 */       return et;
/*      */     }
/*  166 */     return (EvaluatedType)simpleOpenTypeMap.get(ot);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Class getJavaClass(EvaluatedType type) {
/*  171 */     if (type instanceof EvaluatedClassDeclaration)
/*  172 */       return ((EvaluatedClassDeclaration)type).cls(); 
/*  173 */     if (type instanceof EvaluatedArrayType) {
/*      */       
/*  175 */       EvaluatedArrayType gat = (EvaluatedArrayType)type;
/*  176 */       EvaluatedType ctype = canonicalType(gat.componentType());
/*  177 */       Class<?> cclass = getJavaClass(ctype);
/*  178 */       Object temp = Array.newInstance(cclass, 0);
/*  179 */       return temp.getClass();
/*      */     } 
/*  181 */     throw Exceptions.self.cannotConvertToJavaType(type);
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
/*      */   public static TypeConverter makeTypeConverter(EvaluatedType type, ManagedObjectManagerInternal mom) {
/*  274 */     mm.enter(mom.registrationDebug(), "makeTypeConverter", new Object[] { type, mom });
/*      */     
/*  276 */     TypeConverter result = null;
/*      */     try {
/*  278 */       OpenType stype = simpleTypeMap.get(type);
/*  279 */       if (stype != null) {
/*  280 */         result = handleSimpleType(type, stype);
/*  281 */       } else if (type instanceof EvaluatedClassDeclaration) {
/*  282 */         EvaluatedClassDeclaration cls = (EvaluatedClassDeclaration)type;
/*  283 */         ManagedObject mo = mom.<ManagedObject>getFirstAnnotationOnClass(cls, ManagedObject.class);
/*      */         
/*  285 */         ManagedData md = mom.<ManagedData>getFirstAnnotationOnClass(cls, ManagedData.class);
/*      */ 
/*      */         
/*  288 */         if (mo != null) {
/*  289 */           result = handleManagedObject(cls, mom, mo);
/*  290 */         } else if (md != null) {
/*  291 */           result = handleManagedData(cls, mom, md);
/*  292 */         } else if (cls.cls().isEnum()) {
/*  293 */           result = handleEnum(cls);
/*      */         } else {
/*  295 */           result = handleClass(cls, mom);
/*      */         } 
/*  297 */       } else if (type instanceof EvaluatedArrayType) {
/*  298 */         result = handleArrayType((EvaluatedArrayType)type, mom);
/*      */       } else {
/*      */         
/*  301 */         throw new IllegalArgumentException("Unknown kind of Type " + type);
/*      */       }
/*      */     
/*  304 */     } catch (RuntimeException exc) {
/*  305 */       throw exc;
/*  306 */     } catch (OpenDataException exc) {
/*  307 */       throw new RuntimeException(exc);
/*      */     } finally {
/*  309 */       mm.exit(mom.registrationDebug(), result);
/*      */     } 
/*      */     
/*  312 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TypeConverter handleSimpleType(final EvaluatedType type, OpenType stype) {
/*  318 */     final EvaluatedType canType = canonicalType(type);
/*      */     
/*  320 */     return new TypeConverterImpl(type, stype) {
/*      */         public Object toManagedEntity(Object obj) {
/*  322 */           return obj;
/*      */         }
/*      */ 
/*      */         
/*      */         public Object fromManagedEntity(Object entity) {
/*  327 */           return entity;
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean isIdentity() {
/*  332 */           return canType.equals(type);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TypeConverter handleManagedObject(EvaluatedClassDeclaration type, final ManagedObjectManagerInternal mom, ManagedObject mo) {
/*  343 */     TypeConverter result = null;
/*  344 */     mm.enter(mom.registrationDebug(), "handleManagedObject", new Object[] { type, mom, mo });
/*      */ 
/*      */     
/*      */     try {
/*  348 */       result = new TypeConverterImpl((EvaluatedType)type, SimpleType.OBJECTNAME) {
/*      */           public Object toManagedEntity(Object obj) {
/*  350 */             if (obj == null) {
/*  351 */               return AMXClient.NULL_OBJECTNAME;
/*      */             }
/*  353 */             return mom.getObjectName(obj);
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public Object fromManagedEntity(Object entity) {
/*  359 */             if (!(entity instanceof ObjectName)) {
/*  360 */               throw Exceptions.self.entityNotObjectName(entity);
/*      */             }
/*      */             
/*  363 */             ObjectName oname = (ObjectName)entity;
/*  364 */             if (oname.equals(AMXClient.NULL_OBJECTNAME)) {
/*  365 */               return null;
/*      */             }
/*  367 */             return mom.getObject(oname);
/*      */           }
/*      */         };
/*      */     } finally {
/*      */       
/*  372 */       mm.exit(mom.registrationDebug(), result);
/*      */     } 
/*      */     
/*  375 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Collection<AttributeDescriptor> analyzeManagedData(EvaluatedClassDeclaration cls, ManagedObjectManagerInternal mom) {
/*  381 */     mm.enter(mom.registrationDebug(), "analyzeManagedData", new Object[] { cls, mom });
/*      */     
/*  383 */     Collection<AttributeDescriptor> result = null;
/*      */     
/*      */     try {
/*  386 */       EvaluatedClassAnalyzer ca = (EvaluatedClassAnalyzer)mom.getClassAnalyzer(cls, (Class)ManagedData.class).second();
/*      */ 
/*      */ 
/*      */       
/*  390 */       Pair<Map<String, AttributeDescriptor>, Map<String, AttributeDescriptor>> ainfos = mom.getAttributes(ca, ManagedObjectManagerInternal.AttributeDescriptorType.COMPOSITE_DATA_ATTR);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  395 */       result = ((Map)ainfos.first()).values();
/*      */     } finally {
/*  397 */       mm.exit(mom.registrationDebug(), result);
/*      */     } 
/*      */     
/*  400 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static CompositeType makeCompositeType(EvaluatedClassDeclaration cls, ManagedObjectManagerInternal mom, ManagedData md, Collection<AttributeDescriptor> minfos) {
/*  408 */     mm.enter(mom.registrationDebug(), "makeCompositeType", new Object[] { cls, mom, md, minfos });
/*      */ 
/*      */     
/*  411 */     CompositeType result = null;
/*      */     
/*      */     try {
/*  414 */       String name = md.name();
/*  415 */       if (name.equals("")) {
/*  416 */         name = mom.getTypeName(cls.cls(), "GMBAL_TYPE", md.name());
/*      */       }
/*      */ 
/*      */       
/*  420 */       mm.info(mom.registrationDebug(), new Object[] { "name=", name });
/*      */       
/*  422 */       String mdDescription = mom.getDescription((EvaluatedDeclaration)cls);
/*  423 */       mm.info(mom.registrationDebug(), new Object[] { "mdDescription=", mdDescription });
/*      */       
/*  425 */       int length = minfos.size();
/*  426 */       String[] attrNames = new String[length];
/*  427 */       String[] attrDescriptions = new String[length];
/*  428 */       OpenType[] attrOTypes = new OpenType[length];
/*      */       
/*  430 */       int ctr = 0;
/*  431 */       for (AttributeDescriptor minfo : minfos) {
/*  432 */         attrNames[ctr] = minfo.id();
/*  433 */         attrDescriptions[ctr] = minfo.description();
/*  434 */         attrOTypes[ctr] = minfo.tc().getManagedType();
/*  435 */         ctr++;
/*      */       } 
/*      */       
/*  438 */       mm.info(mom.registrationDebug(), new Object[] { "attrNames=", Arrays.asList(attrNames), "attrDescriptions=", Arrays.asList(attrDescriptions), "attrOTypes=", Arrays.asList(attrOTypes) });
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/*  444 */         result = new CompositeType(name, mdDescription, attrNames, attrDescriptions, (OpenType<?>[])attrOTypes);
/*      */       }
/*  446 */       catch (OpenDataException exc) {
/*  447 */         throw Exceptions.self.exceptionInMakeCompositeType(exc);
/*      */       } 
/*      */     } finally {
/*  450 */       mm.exit(mom.registrationDebug(), result);
/*      */     } 
/*      */     
/*  453 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TypeConverter handleManagedData(final EvaluatedClassDeclaration cls, final ManagedObjectManagerInternal mom, ManagedData md) {
/*  460 */     mm.enter(mom.registrationDebug(), "handleManagedData", new Object[] { cls, mom, md });
/*      */     
/*  462 */     TypeConverter result = null;
/*      */     try {
/*  464 */       final Collection<AttributeDescriptor> minfos = analyzeManagedData(cls, mom);
/*      */       
/*  466 */       final CompositeType myType = makeCompositeType(cls, mom, md, minfos);
/*  467 */       mm.info(mom.registrationDebug(), new Object[] { "minfos=", minfos, "myType=", myType });
/*      */ 
/*      */       
/*  470 */       result = new TypeConverterImpl((EvaluatedType)cls, myType) {
/*      */           public Object toManagedEntity(Object obj) {
/*  472 */             TypeConverterImpl.mm.enter(mom.runtimeDebug(), "(ManagedData):toManagedEntity", new Object[] { obj });
/*      */ 
/*      */             
/*  475 */             Object runResult = null;
/*      */             try {
/*  477 */               Map<String, Object> data = new HashMap<String, Object>();
/*  478 */               for (AttributeDescriptor minfo : minfos) {
/*  479 */                 TypeConverterImpl.mm.info(mom.runtimeDebug(), new Object[] { "Fetching attribute " + minfo.id() });
/*      */ 
/*      */                 
/*  482 */                 Object value = null;
/*  483 */                 if (minfo.isApplicable(obj)) {
/*      */                   try {
/*  485 */                     FacetAccessor fa = mom.getFacetAccessor(obj);
/*  486 */                     value = minfo.get(fa, mom.runtimeDebug());
/*  487 */                   } catch (JMException ex) {
/*  488 */                     Exceptions.self.errorInConstructingOpenData(cls.name(), minfo.id(), ex);
/*      */                   } 
/*      */                 }
/*      */ 
/*      */                 
/*  493 */                 data.put(minfo.id(), value);
/*      */               } 
/*      */               
/*      */               try {
/*  497 */                 runResult = new CompositeDataSupport(myType, data);
/*  498 */               } catch (OpenDataException exc) {
/*  499 */                 throw Exceptions.self.exceptionInHandleManagedData(exc);
/*      */               } 
/*      */             } finally {
/*  502 */               TypeConverterImpl.mm.exit(mom.runtimeDebug(), runResult);
/*      */             } 
/*      */             
/*  505 */             return runResult;
/*      */           }
/*      */         };
/*      */     } finally {
/*  509 */       mm.exit(mom.registrationDebug(), result);
/*      */     } 
/*      */     
/*  512 */     return result;
/*      */   }
/*      */ 
/*      */   
/*      */   private static TypeConverter handleEnum(final EvaluatedClassDeclaration cls) {
/*  517 */     return new TypeConverterImpl((EvaluatedType)cls, SimpleType.STRING) {
/*      */         public Object toManagedEntity(Object obj) {
/*  519 */           if (obj == null) {
/*  520 */             return "<NULL>";
/*      */           }
/*  522 */           return obj.toString();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public Object fromManagedEntity(Object entity) {
/*  528 */           if ("<NULL>".equals(entity)) {
/*  529 */             return null;
/*      */           }
/*      */           
/*  532 */           if (!(entity instanceof String)) {
/*  533 */             throw Exceptions.self.notAString(entity);
/*      */           }
/*      */           
/*  536 */           return Enum.valueOf(cls.cls(), (String)entity);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ArrayType getArrayType(OpenType<?> ot) throws OpenDataException {
/*      */     ArrayType result;
/*  545 */     if (ot instanceof ArrayType) {
/*  546 */       ArrayType atype = (ArrayType)ot;
/*  547 */       int dim = atype.getDimension();
/*  548 */       OpenType<?> lowestComponentType = atype.getElementOpenType();
/*  549 */       result = new ArrayType(dim + 1, lowestComponentType);
/*      */     } else {
/*  551 */       result = new ArrayType(1, ot);
/*      */     } 
/*      */     
/*  554 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TypeConverter handleArrayType(EvaluatedArrayType type, final ManagedObjectManagerInternal mom) throws OpenDataException {
/*  561 */     mm.enter(mom.registrationDebug(), "handleArrayType", new Object[] { type.name() });
/*      */ 
/*      */     
/*  564 */     TypeConverter result = null;
/*      */     try {
/*  566 */       final EvaluatedType ctype = type.componentType();
/*  567 */       final TypeConverter ctypeTc = mom.getTypeConverter(ctype);
/*  568 */       final OpenType cotype = ctypeTc.getManagedType();
/*  569 */       OpenType ot = getArrayType(cotype);
/*      */       
/*  571 */       mm.info(mom.registrationDebug(), new Object[] { "ctype=", ctype, "ctypeTc=", ctypeTc, "cotype=", cotype, "ot=", ot });
/*      */ 
/*      */       
/*  574 */       result = new TypeConverterImpl((EvaluatedType)type, ot) {
/*      */           public Object toManagedEntity(Object obj) {
/*  576 */             if (isIdentity()) {
/*  577 */               return obj;
/*      */             }
/*  579 */             Class<?> cclass = getJavaClass(cotype);
/*  580 */             int length = (obj == null) ? 0 : Array.getLength(obj);
/*      */             
/*  582 */             Object result = Array.newInstance(cclass, length);
/*  583 */             for (int ctr = 0; ctr < length; ctr++) {
/*  584 */               TypeConverterImpl.mm.enter(mom.runtimeDebug(), "(handleArrayType):toManagedEntity", new Object[] { Integer.valueOf(ctr) });
/*      */             } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  595 */             return result;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public Object fromManagedEntity(Object entity) {
/*  601 */             if (isIdentity()) {
/*  602 */               return entity;
/*      */             }
/*  604 */             Class<?> cclass = getJavaClass(ctype);
/*      */             
/*  606 */             int length = (entity == null) ? 0 : Array.getLength(entity);
/*      */             
/*  608 */             Object result = Array.newInstance(cclass, length);
/*  609 */             for (int ctr = 0; ctr < length; ctr++) {
/*  610 */               TypeConverterImpl.mm.enter(mom.runtimeDebug(), "(handleArrayType):fromManagedEntity", new Object[] { Integer.valueOf(ctr) });
/*      */             } 
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
/*  622 */             return result;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/*      */           public boolean isIdentity() {
/*  628 */             return ctypeTc.isIdentity();
/*      */           }
/*      */         };
/*      */     } finally {
/*  632 */       mm.exit(mom.registrationDebug(), result);
/*      */     } 
/*      */     
/*  635 */     return result;
/*      */   }
/*      */   
/*  638 */   private static final Runnable NoOp = new Runnable() { public void run() {} }
/*      */   ;
/*      */   
/*      */   @DumpToString
/*      */   protected final EvaluatedType dataType;
/*      */   
/*      */   private static EvaluatedMethodDeclaration findMethod(EvaluatedClassAnalyzer eca, final String mname) {
/*  645 */     return (EvaluatedMethodDeclaration)Algorithms.getFirst(eca.findMethods(new Predicate<EvaluatedMethodDeclaration>()
/*      */           {
/*      */             public boolean evaluate(EvaluatedMethodDeclaration m) {
/*  648 */               return m.name().equals(mname);
/*      */             }
/*      */           },  ), NoOp);
/*      */   }
/*      */   
/*      */   @DumpToString
/*      */   protected final OpenType managedType;
/*      */   
/*      */   private static EvaluatedType getReturnType(EvaluatedClassDeclaration decl, String mname) {
/*  657 */     EvaluatedClassAnalyzer eca = new EvaluatedClassAnalyzer(decl);
/*  658 */     EvaluatedMethodDeclaration meth = findMethod(eca, mname);
/*      */     
/*  660 */     if (meth == null) {
/*  661 */       return null;
/*      */     }
/*  663 */     return meth.returnType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static EvaluatedType getParameterType(EvaluatedClassDeclaration decl, String mname, int pindex) {
/*  670 */     EvaluatedClassAnalyzer eca = new EvaluatedClassAnalyzer(decl);
/*  671 */     EvaluatedMethodDeclaration meth = findMethod(eca, mname);
/*      */     
/*  673 */     if (meth == null) {
/*  674 */       return null;
/*      */     }
/*  676 */     if (pindex < meth.parameterTypes().size()) {
/*  677 */       return meth.parameterTypes().get(pindex);
/*      */     }
/*  679 */     throw new IndexOutOfBoundsException("Parameter index is out of bounds");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Table emptyTable() {
/*  686 */     return new Table<Object, Object>()
/*      */       {
/*      */         public Object get(Object key) {
/*  689 */           return null;
/*      */         }
/*      */         
/*      */         public Iterator iterator() {
/*  693 */           return TypeConverterImpl.emptyIterator();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   private static Iterator emptyIterator() {
/*  700 */     List list = new ArrayList();
/*  701 */     return list.iterator();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TypeConverter handleClass(EvaluatedClassDeclaration type, ManagedObjectManagerInternal mom) {
/*  708 */     mm.enter(mom.registrationDebug(), "handleClass", new Object[] { "type", type });
/*      */     
/*  710 */     TypeConverter result = null;
/*      */ 
/*      */     
/*      */     try {
/*  714 */       if (Iterable.class.isAssignableFrom(type.cls())) {
/*  715 */         EvaluatedClassDeclaration type2 = (EvaluatedClassDeclaration)getReturnType(type, "iterator");
/*      */         
/*  717 */         if (type2 == null) {
/*  718 */           throw Exceptions.self.iteratorNotFound(type);
/*      */         }
/*      */         
/*  721 */         EvaluatedType tcType = getReturnType(type2, "next");
/*  722 */         if (tcType == null) {
/*  723 */           throw Exceptions.self.nextNotFound(type);
/*      */         }
/*      */         
/*  726 */         TypeConverter tc = mom.getTypeConverter(tcType);
/*      */         
/*  728 */         result = new TypeConverterListBase((EvaluatedType)type, tc) {
/*      */             protected Iterator getIterator(Object obj) {
/*  730 */               if (obj == null) {
/*  731 */                 return TypeConverterImpl.emptyIterator();
/*      */               }
/*  733 */               return ((Iterable)obj).iterator();
/*      */             }
/*      */           };
/*      */       }
/*  737 */       else if (Collection.class.isAssignableFrom(type.cls())) {
/*  738 */         EvaluatedClassDeclaration type2 = (EvaluatedClassDeclaration)getReturnType(type, "iterator");
/*      */         
/*  740 */         EvaluatedType tcType = getReturnType(type2, "next");
/*  741 */         TypeConverter tc = mom.getTypeConverter(tcType);
/*      */         
/*  743 */         result = new TypeConverterListBase((EvaluatedType)type, tc) {
/*      */             protected Iterator getIterator(Object obj) {
/*  745 */               if (obj == null) {
/*  746 */                 return TypeConverterImpl.emptyIterator();
/*      */               }
/*  748 */               return ((Iterable)obj).iterator();
/*      */             }
/*      */           };
/*      */       }
/*  752 */       else if (Iterator.class.isAssignableFrom(type.cls())) {
/*  753 */         EvaluatedType tcType = getReturnType(type, "next");
/*  754 */         TypeConverter tc = mom.getTypeConverter(tcType);
/*      */         
/*  756 */         result = new TypeConverterListBase((EvaluatedType)type, tc) {
/*      */             protected Iterator getIterator(Object obj) {
/*  758 */               if (obj == null) {
/*  759 */                 return TypeConverterImpl.emptyIterator();
/*      */               }
/*  761 */               return (Iterator)obj;
/*      */             }
/*      */           };
/*      */       }
/*  765 */       else if (Enumeration.class.isAssignableFrom(type.cls())) {
/*  766 */         EvaluatedType tcType = getReturnType(type, "next");
/*      */         
/*  768 */         TypeConverter tc = mom.getTypeConverter(tcType);
/*  769 */         result = new TypeConverterListBase((EvaluatedType)type, tc)
/*      */           {
/*      */             protected Iterator getIterator(Object obj) {
/*  772 */               if (obj == null) {
/*  773 */                 return TypeConverterImpl.emptyIterator();
/*      */               }
/*  775 */               return new TypeConverterImpl.EnumerationAdapter((Enumeration)obj);
/*      */             }
/*      */           };
/*      */       }
/*  779 */       else if (Map.class.isAssignableFrom(type.cls())) {
/*  780 */         EvaluatedType type1 = getParameterType(type, "put", 0);
/*  781 */         TypeConverter firstTc = mom.getTypeConverter(type1);
/*  782 */         EvaluatedType type2 = getReturnType(type, "put");
/*  783 */         TypeConverter secondTc = mom.getTypeConverter(type2);
/*      */         
/*  785 */         result = new TypeConverterMapBase((EvaluatedType)type, firstTc, secondTc)
/*      */           {
/*      */             protected TypeConverterImpl.Table getTable(Object obj) {
/*  788 */               if (obj == null) {
/*  789 */                 return TypeConverterImpl.emptyTable();
/*      */               }
/*  791 */               return new TypeConverterImpl.TableMapImpl<Object, Object>((Map<?, ?>)obj);
/*      */             }
/*      */           };
/*      */       }
/*  795 */       else if (Dictionary.class.isAssignableFrom(type.cls())) {
/*  796 */         EvaluatedType type1 = getParameterType(type, "put", 0);
/*  797 */         TypeConverter firstTc = mom.getTypeConverter(type1);
/*  798 */         EvaluatedType type2 = getReturnType(type, "put");
/*  799 */         TypeConverter secondTc = mom.getTypeConverter(type2);
/*      */         
/*  801 */         result = new TypeConverterMapBase((EvaluatedType)type, firstTc, secondTc)
/*      */           {
/*      */             protected TypeConverterImpl.Table getTable(Object obj) {
/*  804 */               if (obj == null) {
/*  805 */                 return TypeConverterImpl.emptyTable();
/*      */               }
/*  807 */               return new TypeConverterImpl.TableDictionaryImpl<Object, Object>((Dictionary<?, ?>)obj);
/*      */             }
/*      */           };
/*      */       } else {
/*      */         
/*  812 */         result = handleAsString(type);
/*      */       } 
/*      */     } finally {
/*      */       
/*  816 */       mm.exit(mom.registrationDebug(), result);
/*      */     } 
/*      */     
/*  819 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TypeConverter handleAsString(final EvaluatedClassDeclaration cls) {
/*      */     Constructor constructor1;
/*      */     try {
/*  828 */       SecurityManager sman = System.getSecurityManager();
/*  829 */       if (sman == null) {
/*  830 */         constructor1 = cls.cls().getDeclaredConstructor(new Class[] { String.class });
/*      */       } else {
/*  832 */         constructor1 = (Constructor)Algorithms.doPrivileged(new Algorithms.Action<Constructor>()
/*      */             {
/*      */               public Constructor run() throws Exception {
/*  835 */                 return cls.cls().getDeclaredConstructor(new Class[] { String.class });
/*      */               }
/*      */             });
/*      */       } 
/*  839 */     } catch (Exception exc) {
/*  840 */       Exceptions.self.noStringConstructorAvailable(exc, cls.name());
/*  841 */       constructor1 = null;
/*      */     } 
/*  843 */     final Constructor cons = constructor1;
/*      */     
/*  845 */     return new TypeConverterImpl((EvaluatedType)cls, SimpleType.STRING) {
/*      */         public Object toManagedEntity(Object obj) {
/*  847 */           if (obj == null) {
/*  848 */             return "<NULL>";
/*      */           }
/*  850 */           return obj.toString();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public Object fromManagedEntity(Object entity) {
/*  856 */           if (entity == null) {
/*  857 */             return null;
/*      */           }
/*      */           
/*  860 */           if (cons == null) {
/*  861 */             throw Exceptions.self.noStringConstructor(cls.cls());
/*      */           }
/*      */           
/*      */           try {
/*  865 */             String str = (String)entity;
/*  866 */             return cons.newInstance(new Object[] { str });
/*  867 */           } catch (InstantiationException exc) {
/*  868 */             throw Exceptions.self.stringConversionError(cls.cls(), exc);
/*  869 */           } catch (IllegalAccessException exc) {
/*  870 */             throw Exceptions.self.stringConversionError(cls.cls(), exc);
/*  871 */           } catch (InvocationTargetException exc) {
/*  872 */             throw Exceptions.self.stringConversionError(cls.cls(), exc);
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private static class EnumerationAdapter<T> implements Iterator<T> {
/*      */     private final Enumeration<T> enumeration;
/*      */     
/*      */     public EnumerationAdapter(Enumeration<T> en) {
/*  882 */       this.enumeration = en;
/*      */     }
/*      */     
/*      */     public boolean hasNext() {
/*  886 */       return this.enumeration.hasMoreElements();
/*      */     }
/*      */     
/*      */     public T next() {
/*  890 */       return this.enumeration.nextElement();
/*      */     }
/*      */     
/*      */     public void remove() {
/*  894 */       throw Exceptions.self.removeNotSupported();
/*      */     }
/*      */   }
/*      */   
/*      */   public static class TypeConverterPlaceHolderImpl
/*      */     implements TypeConverter
/*      */   {
/*      */     private EvaluatedType et;
/*      */     
/*      */     public TypeConverterPlaceHolderImpl(EvaluatedType type) {
/*  904 */       this.et = type;
/*      */     }
/*      */     
/*      */     public EvaluatedType getDataType() {
/*  908 */       throw Exceptions.self.recursiveTypesNotSupported(this.et);
/*      */     }
/*      */     
/*      */     public OpenType getManagedType() {
/*  912 */       throw Exceptions.self.recursiveTypesNotSupported(this.et);
/*      */     }
/*      */     
/*      */     public Object toManagedEntity(Object obj) {
/*  916 */       throw Exceptions.self.recursiveTypesNotSupported(this.et);
/*      */     }
/*      */     
/*      */     public Object fromManagedEntity(Object entity) {
/*  920 */       throw Exceptions.self.recursiveTypesNotSupported(this.et);
/*      */     }
/*      */     
/*      */     public boolean isIdentity() {
/*  924 */       throw Exceptions.self.recursiveTypesNotSupported(this.et);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static abstract class TypeConverterListBase
/*      */     extends TypeConverterImpl
/*      */   {
/*      */     final TypeConverter memberTc;
/*      */ 
/*      */     
/*      */     public TypeConverterListBase(EvaluatedType dataType, TypeConverter memberTc) {
/*  936 */       super(dataType, makeArrayType(memberTc.getManagedType()));
/*  937 */       this.memberTc = memberTc;
/*      */     }
/*      */ 
/*      */     
/*      */     private static ArrayType makeArrayType(OpenType ot) {
/*      */       try {
/*  943 */         return TypeConverterImpl.getArrayType(ot);
/*  944 */       } catch (OpenDataException exc) {
/*  945 */         throw Exceptions.self.openTypeInArrayTypeException(ot, exc);
/*      */       } 
/*      */     }
/*      */     
/*      */     protected abstract Iterator getIterator(Object param1Object);
/*      */     
/*      */     public Object toManagedEntity(Object obj) {
/*  952 */       Iterator iter = getIterator(obj);
/*  953 */       List<Object> list = new ArrayList();
/*  954 */       while (iter.hasNext()) {
/*  955 */         list.add(iter.next());
/*      */       }
/*      */       
/*  958 */       Class<?> cclass = getJavaClass(this.memberTc.getManagedType());
/*  959 */       Object result = Array.newInstance(cclass, list.size());
/*  960 */       int ctr = 0;
/*  961 */       for (Object elem : list) {
/*  962 */         Object mappedElem = this.memberTc.toManagedEntity(elem);
/*  963 */         Array.set(result, ctr++, mappedElem);
/*      */       } 
/*      */       
/*  966 */       return result;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class TableMapImpl<K, V>
/*      */     implements Table<K, V>
/*      */   {
/*      */     private final Map<K, V> map;
/*      */ 
/*      */     
/*      */     public TableMapImpl(Map<K, V> map) {
/*  978 */       this.map = map;
/*      */     }
/*      */     
/*      */     public Iterator<K> iterator() {
/*  982 */       return this.map.keySet().iterator();
/*      */     }
/*      */     
/*      */     public V get(K key) {
/*  986 */       return this.map.get(key);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class TableDictionaryImpl<K, V> implements Table<K, V> {
/*      */     private final Dictionary<K, V> dict;
/*      */     
/*      */     public TableDictionaryImpl(Dictionary<K, V> dict) {
/*  994 */       this.dict = dict;
/*      */     }
/*      */ 
/*      */     
/*      */     public Iterator<K> iterator() {
/*  999 */       return new TypeConverterImpl.EnumerationAdapter<K>(this.dict.keys());
/*      */     }
/*      */     
/*      */     public V get(K key) {
/* 1003 */       return this.dict.get(key);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static abstract class TypeConverterMapBase
/*      */     extends TypeConverterImpl
/*      */   {
/*      */     private final TypeConverter keyTypeConverter;
/*      */     
/*      */     private final TypeConverter valueTypeConverter;
/*      */     
/*      */     public TypeConverterMapBase(EvaluatedType dataType, TypeConverter keyTypeConverter, TypeConverter valueTypeConverter) {
/* 1016 */       super(dataType, makeMapTabularType(keyTypeConverter, valueTypeConverter));
/*      */       
/* 1018 */       this.keyTypeConverter = keyTypeConverter;
/* 1019 */       this.valueTypeConverter = valueTypeConverter;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     private static TabularType makeMapTabularType(TypeConverter firstTc, TypeConverter secondTc) {
/* 1025 */       String mapType = firstTc + "->" + secondTc;
/*      */       
/* 1027 */       String[] itemNames = { "key", "value" };
/*      */       
/* 1029 */       String description = Exceptions.self.rowTypeDescription(mapType);
/*      */ 
/*      */       
/* 1032 */       String[] itemDescriptions = { Exceptions.self.keyFieldDescription(mapType), Exceptions.self.valueFieldDescription(mapType) };
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1037 */       OpenType[] itemTypes = { firstTc.getManagedType(), secondTc.getManagedType() };
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1042 */         CompositeType rowType = new CompositeType(mapType, description, itemNames, itemDescriptions, (OpenType<?>[])itemTypes);
/*      */ 
/*      */         
/* 1045 */         String[] keys = { "key" };
/* 1046 */         String tableName = Exceptions.self.tableName(mapType);
/*      */         
/* 1048 */         String tableDescription = Exceptions.self.tableDescription(mapType);
/*      */ 
/*      */         
/* 1051 */         TabularType result = new TabularType(tableName, tableDescription, rowType, keys);
/*      */ 
/*      */         
/* 1054 */         return result;
/* 1055 */       } catch (OpenDataException exc) {
/* 1056 */         throw Exceptions.self.exceptionInMakeMapTabularType(exc);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected abstract TypeConverterImpl.Table getTable(Object param1Object);
/*      */     
/*      */     public Object toManagedEntity(Object obj) {
/*      */       try {
/* 1065 */         TypeConverterImpl.Table table = getTable(obj);
/* 1066 */         TabularType ttype = (TabularType)getManagedType();
/* 1067 */         CompositeType ctype = ttype.getRowType();
/* 1068 */         TabularData result = new TabularDataSupport(ttype);
/* 1069 */         for (Object key : table) {
/*      */           
/* 1071 */           Object value = table.get(key);
/* 1072 */           Object mappedKey = this.keyTypeConverter.toManagedEntity(key);
/*      */           
/* 1074 */           Object mappedValue = this.valueTypeConverter.toManagedEntity(value);
/*      */           
/* 1076 */           Map<Object, Object> items = new HashMap<Object, Object>();
/* 1077 */           items.put("key", mappedKey);
/* 1078 */           items.put("value", mappedValue);
/* 1079 */           CompositeDataSupport cdata = new CompositeDataSupport(ctype, (Map)items);
/*      */           
/* 1081 */           result.put(cdata);
/*      */         } 
/*      */         
/* 1084 */         return result;
/* 1085 */       } catch (OpenDataException exc) {
/* 1086 */         throw Exceptions.self.excInMakeMapTabularDataToManagedEntity(exc);
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
/*      */   protected TypeConverterImpl(EvaluatedType dataType, OpenType managedType) {
/* 1101 */     this.dataType = dataType;
/* 1102 */     this.managedType = managedType;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final EvaluatedType getDataType() {
/* 1108 */     return this.dataType;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public final OpenType getManagedType() {
/* 1114 */     return this.managedType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object fromManagedEntity(Object entity) {
/* 1124 */     throw Exceptions.self.openToJavaNotSupported(this.managedType, this.dataType);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isIdentity() {
/* 1130 */     return false;
/*      */   }
/*      */   
/*      */   private String displayOpenType(OpenType otype) {
/* 1134 */     if (otype instanceof SimpleType) {
/* 1135 */       SimpleType stype = (SimpleType)otype;
/* 1136 */       return "SimpleType(" + stype.getTypeName() + ")";
/* 1137 */     }  if (otype instanceof ArrayType) {
/* 1138 */       ArrayType atype = (ArrayType)otype;
/* 1139 */       return "ArrayType(" + displayOpenType(atype.getElementOpenType()) + "," + atype.getDimension() + ")";
/*      */     } 
/* 1141 */     if (otype instanceof CompositeType) {
/* 1142 */       CompositeType ctype = (CompositeType)otype;
/* 1143 */       return "CompositeType(" + ctype.getTypeName() + ")";
/* 1144 */     }  if (otype instanceof TabularType) {
/* 1145 */       TabularType ttype = (TabularType)otype;
/* 1146 */       return "TabularType(" + ttype.getTypeName() + "," + "rowType=" + ttype.getRowType() + "indexNames=" + ttype.getIndexNames() + ")";
/*      */     } 
/*      */ 
/*      */     
/* 1150 */     return "UNKNOWN(" + otype + ")";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1156 */     return "TypeConverter[dataType=" + this.dataType + ",managedType=" + displayOpenType(this.managedType) + "]";
/*      */   }
/*      */   
/*      */   public abstract Object toManagedEntity(Object paramObject);
/*      */   
/*      */   private static interface Table<K, V> extends Iterable<K> {
/*      */     V get(K param1K);
/*      */   }
/*      */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\impl\TypeConverterImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */