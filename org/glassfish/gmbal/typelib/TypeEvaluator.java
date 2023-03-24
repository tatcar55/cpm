/*     */ package org.glassfish.gmbal.typelib;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ import javax.management.ObjectName;
/*     */ import org.glassfish.gmbal.generic.Algorithms;
/*     */ import org.glassfish.gmbal.generic.Display;
/*     */ import org.glassfish.gmbal.generic.MethodMonitor;
/*     */ import org.glassfish.gmbal.generic.MethodMonitorFactory;
/*     */ import org.glassfish.gmbal.generic.Pair;
/*     */ import org.glassfish.gmbal.generic.UnaryFunction;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TypeEvaluator
/*     */ {
/*     */   private static boolean debug = false;
/*     */   private static boolean debugEvaluate = false;
/*  87 */   private static final MethodMonitor mm = MethodMonitorFactory.makeStandard(TypeEvaluator.class);
/*     */ 
/*     */   
/*  90 */   private static Map<Class<?>, EvaluatedType> immutableTypes = new HashMap<Class<?>, EvaluatedType>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  99 */   private static Map<EvalMapKey, EvaluatedClassDeclaration> evalClassMap = new HashMap<EvalMapKey, EvaluatedClassDeclaration>();
/*     */ 
/*     */   
/* 102 */   private static List<EvaluatedType> emptyETList = new ArrayList<EvaluatedType>(0);
/*     */ 
/*     */ 
/*     */   
/*     */   private static void mapPut(EvaluatedClassDeclaration ecd, Class<?> cls) {
/* 107 */     mm.enter(debug, "mapPut", new Object[] { ecd, cls });
/* 108 */     immutableTypes.put(cls, ecd);
/*     */     
/*     */     try {
/* 111 */       EvalMapKey key = new EvalMapKey(cls, emptyETList);
/* 112 */       evalClassMap.put(key, ecd);
/*     */     } finally {
/* 114 */       mm.exit(debug);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*     */     try {
/* 135 */       Class[] classes = { int.class, byte.class, char.class, short.class, long.class, boolean.class, float.class, double.class, void.class, Integer.class, Byte.class, Character.class, Short.class, Boolean.class, Float.class, Double.class, Long.class, BigDecimal.class, BigInteger.class, Date.class, ObjectName.class, Class.class, Number.class };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 145 */       Class<Object> objectClass = Object.class;
/* 146 */       Class<String> stringClass = String.class;
/* 147 */       Class<Void> voidClass = Void.class;
/*     */       
/* 149 */       Method toStringMethod = getDeclaredMethod(objectClass, "toString", new Class[0]);
/*     */ 
/*     */ 
/*     */       
/* 153 */       EvaluatedClassDeclaration objectECD = getECD(objectClass);
/* 154 */       EvaluatedClassDeclaration voidECD = getECD(voidClass);
/* 155 */       EvaluatedClassDeclaration stringECD = getECD(stringClass);
/*     */       
/* 157 */       EvaluatedMethodDeclaration toStringEMD = DeclarationFactory.emdecl(objectECD, 1, stringECD, "toString", emptyETList, toStringMethod);
/*     */ 
/*     */       
/* 160 */       List<EvaluatedMethodDeclaration> toStringList = Algorithms.list((Object[])new EvaluatedMethodDeclaration[] { toStringEMD });
/*     */ 
/*     */       
/* 163 */       List<EvaluatedClassDeclaration> objectList = Algorithms.list((Object[])new EvaluatedClassDeclaration[] { objectECD });
/*     */ 
/*     */ 
/*     */       
/* 167 */       voidECD.inheritance(objectList);
/* 168 */       voidECD.freeze();
/*     */       
/* 170 */       objectECD.methods(toStringList);
/* 171 */       objectECD.freeze();
/*     */       
/* 173 */       stringECD.inheritance(objectList);
/* 174 */       stringECD.freeze();
/*     */ 
/*     */       
/* 177 */       mapPut(voidECD, voidClass);
/* 178 */       mapPut(objectECD, objectClass);
/* 179 */       mapPut(stringECD, stringClass);
/*     */ 
/*     */       
/* 182 */       for (Class cls : classes) {
/* 183 */         EvaluatedClassDeclaration ecd = getECD(cls);
/* 184 */         ecd.inheritance(objectList);
/* 185 */         ecd.freeze();
/* 186 */         mapPut(ecd, cls);
/*     */       } 
/* 188 */     } catch (Exception exc) {
/* 189 */       throw Exceptions.self.internalTypeEvaluatorError(exc);
/*     */     } 
/*     */     
/* 192 */     setDebugLevel(Integer.getInteger("org.glassfish.gmbal.TypelibDebugLevel", 0).intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static EvaluatedType getImmutableEvaluatedType(Class<?> cls) {
/* 202 */     return immutableTypes.get(cls);
/*     */   }
/*     */   
/*     */   public static synchronized void setDebugLevel(int level) {
/* 206 */     debug = (level > 1);
/* 207 */     debugEvaluate = (level >= 1);
/*     */   }
/*     */   
/*     */   private static class EvalMapKey extends Pair<Class<?>, List<EvaluatedType>> {
/*     */     public EvalMapKey(Class<?> cls, List<EvaluatedType> decls) {
/* 212 */       super(cls, decls);
/*     */     }
/*     */     
/* 215 */     public static final EvalMapKey OBJECT_KEY = new EvalMapKey(Object.class, new ArrayList<EvaluatedType>(0));
/*     */   }
/*     */ 
/*     */   
/*     */   private static EvaluatedClassDeclaration getECD(Class cls) {
/* 220 */     return DeclarationFactory.ecdecl(1, cls.getName(), cls, true);
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Method> getDeclaredMethods(final Class<?> cls) {
/* 225 */     SecurityManager sman = System.getSecurityManager();
/* 226 */     if (sman == null) {
/* 227 */       return Arrays.asList(cls.getDeclaredMethods());
/*     */     }
/* 229 */     return AccessController.<List<Method>>doPrivileged(new PrivilegedAction<List<Method>>()
/*     */         {
/*     */           public List<Method> run() {
/* 232 */             return Arrays.asList(cls.getDeclaredMethods());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<Field> getDeclaredFields(final Class<?> cls) {
/* 241 */     SecurityManager sman = System.getSecurityManager();
/* 242 */     if (sman == null) {
/* 243 */       return Arrays.asList(cls.getDeclaredFields());
/*     */     }
/* 245 */     return AccessController.<List<Field>>doPrivileged(new PrivilegedAction<List<Field>>()
/*     */         {
/*     */           public List<Field> run() {
/* 248 */             return Arrays.asList(cls.getDeclaredFields());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Method getDeclaredMethod(final Class<?> cls, final String name, Class<?>... sig) throws NoSuchMethodException, PrivilegedActionException {
/* 260 */     SecurityManager sman = System.getSecurityManager();
/* 261 */     if (sman == null) {
/* 262 */       return cls.getDeclaredMethod(name, sig);
/*     */     }
/* 264 */     return AccessController.<Method>doPrivileged(new PrivilegedExceptionAction<Method>()
/*     */         {
/*     */           public Method run() throws Exception {
/* 267 */             return cls.getDeclaredMethod(name, sig);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized int evalClassMapSize() {
/* 275 */     return evalClassMap.size();
/*     */   }
/*     */   
/*     */   public static synchronized void dumpEvalClassMap() {
/* 279 */     System.out.println("TypeEvaluator: dumping eval class map");
/* 280 */     int numSystem = 0;
/* 281 */     int total = 0;
/*     */ 
/*     */     
/* 284 */     for (Map.Entry<EvalMapKey, EvaluatedClassDeclaration> entry : evalClassMap.entrySet()) {
/*     */       
/* 286 */       System.out.println("\tKey:" + entry.getKey() + "=>");
/* 287 */       System.out.println("\t\t" + entry.getValue());
/*     */       
/* 289 */       String name = ((Class)((EvalMapKey)entry.getKey()).first()).getName();
/* 290 */       if (!name.startsWith("org.glassfish.gmbal")) {
/* 291 */         numSystem++;
/*     */       }
/* 293 */       total++;
/*     */     } 
/*     */     
/* 296 */     System.out.printf("\nEvalClassMap contains %d entries, %d of which are system classes\n", new Object[] { Integer.valueOf(total), Integer.valueOf(numSystem) });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 305 */   private static Map<Class, EvaluatedType> classMap = (Map)new WeakHashMap<Class<?>, EvaluatedType>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized EvaluatedType getEvaluatedType(Class cls) {
/* 315 */     EvaluatedType etype = classMap.get(cls);
/* 316 */     if (etype == null) {
/* 317 */       TypeEvaluationVisitor visitor = new TypeEvaluationVisitor();
/* 318 */       etype = visitor.evaluateType(cls);
/* 319 */       classMap.put(cls, etype);
/*     */     } 
/*     */     
/* 322 */     return etype;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class PartialDefinitions
/*     */   {
/* 333 */     private Map<Pair<Class<?>, List<Type>>, EvaluatedType> table = new HashMap<Pair<Class<?>, List<Type>>, EvaluatedType>();
/*     */ 
/*     */     
/*     */     private Pair<Class<?>, List<Type>> getKey(Class cls) {
/* 337 */       List<Type> list = new ArrayList<Type>();
/* 338 */       for (TypeVariable tv : cls.getTypeParameters()) {
/*     */         Type<Object> type;
/* 340 */         Type[] bounds = tv.getBounds();
/* 341 */         if (bounds.length > 0) {
/* 342 */           if (bounds.length > 1) {
/* 343 */             throw Exceptions.self.multipleUpperBoundsNotSupported(tv);
/*     */           }
/*     */           
/* 346 */           type = bounds[0];
/*     */         } else {
/*     */           
/* 349 */           type = Object.class;
/*     */         } 
/*     */         
/* 352 */         list.add(type);
/*     */       } 
/*     */       
/* 355 */       return new Pair(cls, list);
/*     */     }
/*     */     
/*     */     private Pair<Class<?>, List<Type>> getKey(ParameterizedType pt) {
/* 359 */       List<Type> list = new ArrayList<Type>();
/* 360 */       for (Type type : pt.getActualTypeArguments()) {
/* 361 */         list.add(type);
/*     */       }
/*     */       
/* 364 */       return new Pair(pt.getRawType(), list);
/*     */     }
/*     */ 
/*     */     
/*     */     public EvaluatedType get(Class cls) {
/* 369 */       return this.table.get(getKey(cls));
/*     */     }
/*     */     
/*     */     public EvaluatedType get(ParameterizedType pt) {
/* 373 */       return this.table.get(getKey(pt));
/*     */     }
/*     */     
/*     */     public void put(Class cls, EvaluatedType et) {
/* 377 */       this.table.put(getKey(cls), et);
/*     */     }
/*     */     
/*     */     public void put(ParameterizedType pt, EvaluatedType et) {
/* 381 */       this.table.put(getKey(pt), et);
/*     */     }
/*     */     
/*     */     public void remove(Class cls) {
/* 385 */       this.table.remove(getKey(cls));
/*     */     }
/*     */     
/*     */     public void remove(ParameterizedType pt) {
/* 389 */       this.table.remove(getKey(pt));
/*     */     }
/*     */ 
/*     */     
/*     */     private PartialDefinitions() {}
/*     */   }
/*     */ 
/*     */   
/*     */   private static class TypeEvaluationVisitor
/*     */   {
/* 399 */     private final Display<String, EvaluatedType> display = new Display();
/*     */     
/* 401 */     private final TypeEvaluator.PartialDefinitions partialDefinitions = new TypeEvaluator.PartialDefinitions();
/*     */ 
/*     */ 
/*     */     
/*     */     public EvaluatedType evaluateType(Object type) {
/* 406 */       TypeEvaluator.mm.enter(TypeEvaluator.debugEvaluate, "evaluateType", new Object[] { type });
/*     */       
/* 408 */       EvaluatedType result = null;
/*     */       
/*     */       try {
/* 411 */         if (type == null)
/* 412 */         { result = null; }
/* 413 */         else if (type instanceof Class)
/* 414 */         { Class cls = (Class)type;
/* 415 */           result = visitClassDeclaration(cls); }
/* 416 */         else if (type instanceof ParameterizedType)
/* 417 */         { ParameterizedType pt = (ParameterizedType)type;
/* 418 */           result = visitParameterizedType(pt); }
/* 419 */         else if (type instanceof TypeVariable)
/* 420 */         { TypeVariable tvar = (TypeVariable)type;
/* 421 */           result = visitTypeVariable(tvar); }
/* 422 */         else if (type instanceof GenericArrayType)
/* 423 */         { GenericArrayType gat = (GenericArrayType)type;
/* 424 */           result = visitGenericArrayType(gat); }
/* 425 */         else if (type instanceof WildcardType)
/* 426 */         { WildcardType wt = (WildcardType)type;
/* 427 */           result = visitWildcardType(wt); }
/* 428 */         else { if (type instanceof Method) {
/* 429 */             throw Exceptions.self.evaluateTypeCalledWithMethod(type);
/*     */           }
/* 431 */           throw Exceptions.self.evaluateTypeCalledWithUnknownType(type); }
/*     */       
/*     */       } finally {
/* 434 */         TypeEvaluator.mm.exit(TypeEvaluator.debugEvaluate, result);
/*     */       } 
/*     */       
/* 437 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private EvaluatedType visitClassDeclaration(Class decl) {
/* 443 */       TypeEvaluator.mm.enter(TypeEvaluator.debug, "visitClassDeclaration", new Object[] { decl });
/*     */       
/* 445 */       EvaluatedType result = null;
/*     */       
/*     */       try {
/* 448 */         if (decl.isArray()) {
/* 449 */           TypeEvaluator.mm.info(TypeEvaluator.debug, new Object[] { "decl is an array" });
/*     */           
/* 451 */           return DeclarationFactory.egat(evaluateType(decl.getComponentType()));
/*     */         } 
/*     */         
/* 454 */         result = this.partialDefinitions.get(decl);
/* 455 */         if (result == null) {
/*     */ 
/*     */           
/* 458 */           EvaluatedClassDeclaration newDecl = DeclarationFactory.ecdecl(decl.getModifiers(), decl.getName(), decl);
/*     */ 
/*     */           
/* 461 */           this.partialDefinitions.put(decl, newDecl);
/*     */           
/*     */           try {
/* 464 */             OrderedResult<String, EvaluatedType> bindings = getBindings(decl);
/*     */ 
/*     */             
/* 467 */             result = getCorrectDeclaration(bindings, decl, newDecl);
/*     */           } finally {
/* 469 */             this.partialDefinitions.remove(decl);
/*     */           } 
/*     */         } else {
/* 472 */           TypeEvaluator.mm.info(TypeEvaluator.debug, new Object[] { "found result:" + result });
/*     */         } 
/*     */       } finally {
/*     */         
/* 476 */         TypeEvaluator.mm.exit(TypeEvaluator.debug, result);
/*     */       } 
/*     */       
/* 479 */       return result;
/*     */     }
/*     */     
/*     */     private EvaluatedType visitParameterizedType(ParameterizedType pt) {
/* 483 */       TypeEvaluator.mm.enter(TypeEvaluator.debug, "visitParameterizedType", new Object[] { pt });
/*     */       
/* 485 */       Class<?> decl = (Class)pt.getRawType();
/*     */       
/* 487 */       EvaluatedType result = null;
/*     */       try {
/* 489 */         result = this.partialDefinitions.get(pt);
/* 490 */         if (result == null) {
/*     */ 
/*     */           
/* 493 */           EvaluatedClassDeclaration newDecl = DeclarationFactory.ecdecl(decl.getModifiers(), decl.getName(), decl);
/*     */ 
/*     */           
/* 496 */           this.partialDefinitions.put(pt, newDecl);
/*     */           
/*     */           try {
/* 499 */             OrderedResult<String, EvaluatedType> bindings = getBindings(pt);
/*     */ 
/*     */             
/* 502 */             result = getCorrectDeclaration(bindings, decl, newDecl);
/*     */           } finally {
/* 504 */             this.partialDefinitions.remove(pt);
/*     */           } 
/*     */         } 
/*     */       } finally {
/* 508 */         TypeEvaluator.mm.exit(TypeEvaluator.debug, result);
/*     */       } 
/*     */       
/* 511 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     private EvaluatedFieldDeclaration visitFieldDeclaration(EvaluatedClassDeclaration cdecl, Field fld) {
/* 516 */       TypeEvaluator.mm.enter(TypeEvaluator.debug, "visitFieldDeclaration", new Object[] { cdecl, fld });
/*     */       
/* 518 */       EvaluatedFieldDeclaration result = null;
/*     */ 
/*     */       
/*     */       try {
/* 522 */         if (!Modifier.isFinal(fld.getModifiers())) {
/* 523 */           return null;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 530 */         Class<?> fieldType = fld.getType();
/* 531 */         EvaluatedType ftype = TypeEvaluator.getImmutableEvaluatedType(fieldType);
/* 532 */         if (ftype == null) {
/* 533 */           return null;
/*     */         }
/*     */         
/* 536 */         result = DeclarationFactory.efdecl(cdecl, fld.getModifiers(), ftype, fld.getName(), fld);
/*     */       }
/* 538 */       catch (Exception exc) {
/* 539 */         TypeEvaluator.mm.info(TypeEvaluator.debug, new Object[] { "Caught exception ", exc, " for field ", fld });
/*     */       } finally {
/* 541 */         TypeEvaluator.mm.exit(TypeEvaluator.debug, result);
/*     */       } 
/*     */       
/* 544 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     private EvaluatedMethodDeclaration visitMethodDeclaration(EvaluatedClassDeclaration cdecl, Method mdecl) {
/* 549 */       TypeEvaluator.mm.enter(TypeEvaluator.debug, "visitMethodDeclaration", new Object[] { cdecl, mdecl });
/*     */       
/* 551 */       EvaluatedMethodDeclaration result = null;
/*     */       
/*     */       try {
/* 554 */         List<EvaluatedType> eptypes = Algorithms.map(Arrays.asList(mdecl.getGenericParameterTypes()), new UnaryFunction<Type, EvaluatedType>()
/*     */             {
/*     */               public EvaluatedType evaluate(Type type)
/*     */               {
/* 558 */                 return TypeEvaluator.TypeEvaluationVisitor.this.evaluateType(type);
/*     */               }
/*     */             });
/* 561 */         TypeEvaluator.mm.info(TypeEvaluator.debug, new Object[] { "eptypes" + eptypes });
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 566 */         if (mdecl.getName().equals("getThing")) {
/* 567 */           TypeEvaluator.mm.info(TypeEvaluator.debug, new Object[] { "processing getThing method from test" });
/*     */         }
/*     */         
/* 570 */         result = DeclarationFactory.emdecl(cdecl, mdecl.getModifiers(), evaluateType(mdecl.getGenericReturnType()), mdecl.getName(), eptypes, mdecl);
/*     */       }
/*     */       finally {
/*     */         
/* 574 */         TypeEvaluator.mm.exit(TypeEvaluator.debug, result);
/*     */       } 
/*     */       
/* 577 */       return result;
/*     */     }
/*     */     
/*     */     private EvaluatedType visitTypeVariable(TypeVariable tvar) {
/* 581 */       TypeEvaluator.mm.enter(TypeEvaluator.debug, "visitTypeVariable", new Object[0]);
/*     */       
/* 583 */       EvaluatedType result = null;
/*     */       
/*     */       try {
/* 586 */         result = lookup(tvar);
/*     */       } finally {
/* 588 */         TypeEvaluator.mm.exit(TypeEvaluator.debug, result);
/*     */       } 
/*     */       
/* 591 */       return result;
/*     */     }
/*     */     
/*     */     private EvaluatedType visitGenericArrayType(GenericArrayType at) {
/* 595 */       TypeEvaluator.mm.enter(TypeEvaluator.debug, "visitGenericArrayType", new Object[0]);
/*     */       
/* 597 */       EvaluatedType result = null;
/*     */       
/*     */       try {
/* 600 */         result = DeclarationFactory.egat(evaluateType(at.getGenericComponentType()));
/*     */       } finally {
/*     */         
/* 603 */         TypeEvaluator.mm.exit(TypeEvaluator.debug, result);
/*     */       } 
/*     */       
/* 606 */       return result;
/*     */     }
/*     */     
/*     */     private EvaluatedType visitWildcardType(WildcardType wt) {
/* 610 */       TypeEvaluator.mm.enter(TypeEvaluator.debug, "visitWilcardType", new Object[0]);
/*     */       
/* 612 */       EvaluatedType result = null;
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 617 */         List<Type> ub = Arrays.asList(wt.getUpperBounds());
/* 618 */         if (ub.size() > 0) {
/* 619 */           if (ub.size() > 1) {
/* 620 */             throw Exceptions.self.multipleUpperBoundsNotSupported(wt);
/*     */           }
/*     */ 
/*     */           
/* 624 */           result = evaluateType(ub.get(0));
/*     */         } else {
/* 626 */           result = EvaluatedType.EOBJECT;
/*     */         } 
/*     */       } finally {
/* 629 */         TypeEvaluator.mm.exit(TypeEvaluator.debug, result);
/*     */       } 
/*     */       
/* 632 */       return result;
/*     */     }
/*     */     
/*     */     private EvaluatedType lookup(TypeVariable tvar) {
/* 636 */       TypeEvaluator.mm.enter(TypeEvaluator.debug, "lookup", new Object[] { tvar });
/*     */       
/* 638 */       EvaluatedType result = null;
/*     */       
/*     */       try {
/* 641 */         result = (EvaluatedType)this.display.lookup(tvar.getName());
/*     */         
/* 643 */         if (result == null) {
/* 644 */           TypeEvaluator.mm.info(TypeEvaluator.debug, new Object[] { "tvar not found in display" });
/*     */           
/* 646 */           Type[] bounds = tvar.getBounds();
/* 647 */           if (bounds.length > 0) {
/* 648 */             if (bounds.length > 1) {
/* 649 */               throw Exceptions.self.multipleUpperBoundsNotSupported(tvar);
/*     */             }
/*     */ 
/*     */             
/* 653 */             result = evaluateType(bounds[0]);
/*     */           } else {
/* 655 */             result = EvaluatedType.EOBJECT;
/*     */           } 
/*     */         } 
/*     */       } finally {
/* 659 */         TypeEvaluator.mm.exit(TypeEvaluator.debug, result);
/*     */       } 
/*     */       
/* 662 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private EvaluatedType getCorrectDeclaration(OrderedResult<String, EvaluatedType> bindings, Class<?> decl, EvaluatedClassDeclaration newDecl) {
/* 668 */       TypeEvaluator.mm.enter(TypeEvaluator.debug, "getCorrectDeclaration", new Object[] { decl });
/*     */       
/* 670 */       EvaluatedType result = null;
/*     */       
/*     */       try {
/* 673 */         List<EvaluatedType> blist = bindings.getList();
/* 674 */         TypeEvaluator.EvalMapKey key = new TypeEvaluator.EvalMapKey(decl, blist);
/* 675 */         if (blist.size() > 0) {
/* 676 */           newDecl.instantiations(blist);
/*     */         }
/*     */         
/* 679 */         result = (EvaluatedType)TypeEvaluator.evalClassMap.get(key);
/* 680 */         if (result == null) {
/* 681 */           TypeEvaluator.mm.info(TypeEvaluator.debug, new Object[] { "No result in evalClassMap" });
/*     */           
/* 683 */           TypeEvaluator.evalClassMap.put(key, newDecl);
/*     */           
/* 685 */           processClass(newDecl, bindings.getMap(), decl);
/*     */           
/* 687 */           result = newDecl;
/*     */         } else {
/* 689 */           TypeEvaluator.mm.info(TypeEvaluator.debug, new Object[] { "Found result in evalClassMap" });
/*     */         } 
/*     */       } finally {
/* 692 */         TypeEvaluator.mm.exit(TypeEvaluator.debug, result);
/*     */       } 
/*     */       
/* 695 */       return result;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private void processClass(final EvaluatedClassDeclaration newDecl, Map<String, EvaluatedType> bindings, Class decl) {
/* 701 */       TypeEvaluator.mm.enter(TypeEvaluator.debug, "processClass", new Object[] { bindings, decl });
/*     */       
/* 703 */       this.display.enterScope();
/* 704 */       this.display.bind(bindings);
/*     */       
/*     */       try {
/* 707 */         List<EvaluatedClassDeclaration> inheritance = Algorithms.map(getInheritance(decl), new UnaryFunction<Type, EvaluatedClassDeclaration>()
/*     */             {
/*     */               public EvaluatedClassDeclaration evaluate(Type pt)
/*     */               {
/* 711 */                 return (EvaluatedClassDeclaration)TypeEvaluator.TypeEvaluationVisitor.this.evaluateType(pt);
/*     */               }
/*     */             });
/* 714 */         TypeEvaluator.mm.info(TypeEvaluator.debug, new Object[] { "inheritance", inheritance });
/*     */         
/* 716 */         newDecl.inheritance(inheritance);
/*     */         
/* 718 */         List<EvaluatedFieldDeclaration> newFields = Algorithms.map(TypeEvaluator.getDeclaredFields(decl), new UnaryFunction<Field, EvaluatedFieldDeclaration>()
/*     */             {
/*     */               public EvaluatedFieldDeclaration evaluate(Field fld)
/*     */               {
/* 722 */                 return TypeEvaluator.TypeEvaluationVisitor.this.visitFieldDeclaration(newDecl, fld);
/*     */               }
/*     */             });
/* 725 */         newDecl.fields(newFields);
/*     */         
/* 727 */         List<EvaluatedMethodDeclaration> newMethods = Algorithms.map(TypeEvaluator.getDeclaredMethods(decl), new UnaryFunction<Method, EvaluatedMethodDeclaration>()
/*     */             {
/*     */ 
/*     */               
/*     */               public EvaluatedMethodDeclaration evaluate(Method md)
/*     */               {
/* 733 */                 return TypeEvaluator.TypeEvaluationVisitor.this.visitMethodDeclaration(newDecl, md);
/*     */               }
/*     */             });
/* 736 */         newDecl.methods(newMethods);
/* 737 */         newDecl.freeze();
/*     */         
/* 739 */         TypeEvaluator.mm.info(TypeEvaluator.debug, new Object[] { "newDecl" + newDecl });
/*     */       } finally {
/* 741 */         this.display.exitScope();
/*     */         
/* 743 */         TypeEvaluator.mm.exit(TypeEvaluator.debug);
/*     */       } 
/*     */     }
/*     */     
/*     */     private List<Type> getInheritance(Class cls) {
/* 748 */       TypeEvaluator.mm.enter(TypeEvaluator.debug, "getInheritance", new Object[] { cls });
/*     */       
/* 750 */       List<Type> result = null;
/*     */       
/*     */       try {
/* 753 */         result = new ArrayList<Type>(0);
/* 754 */         result.add(cls.getGenericSuperclass());
/* 755 */         result.addAll(Arrays.asList(cls.getGenericInterfaces()));
/*     */       } finally {
/* 757 */         TypeEvaluator.mm.exit(TypeEvaluator.debug, result);
/*     */       } 
/*     */       
/* 760 */       return result;
/*     */     }
/*     */     
/*     */     private OrderedResult<String, EvaluatedType> getBindings(Class decl) {
/* 764 */       OrderedResult<String, EvaluatedType> result = new OrderedResult<String, EvaluatedType>();
/*     */ 
/*     */       
/* 767 */       for (TypeVariable tv : decl.getTypeParameters()) {
/* 768 */         EvaluatedType res = lookup(tv);
/* 769 */         result.add(tv.getName(), res);
/*     */       } 
/*     */       
/* 772 */       return result;
/*     */     }
/*     */     
/*     */     private OrderedResult<String, EvaluatedType> getBindings(ParameterizedType pt) {
/* 776 */       OrderedResult<String, EvaluatedType> result = new OrderedResult<String, EvaluatedType>();
/*     */ 
/*     */       
/* 779 */       Iterator<Type> types = Arrays.<Type>asList(pt.getActualTypeArguments()).iterator();
/*     */       
/* 781 */       Iterator<TypeVariable> tvars = Arrays.<TypeVariable>asList(((Class)pt.getRawType()).getTypeParameters()).iterator();
/*     */ 
/*     */       
/* 784 */       while (types.hasNext() && tvars.hasNext()) {
/* 785 */         Type type = types.next();
/* 786 */         TypeVariable tvar = tvars.next();
/* 787 */         result.add(tvar.getName(), evaluateType(type));
/*     */       } 
/*     */       
/* 790 */       if (types.hasNext() != tvars.hasNext()) {
/* 791 */         throw Exceptions.self.listsNotTheSameLengthInParamType(pt);
/*     */       }
/*     */       
/* 794 */       return result;
/*     */     }
/*     */     
/*     */     public static class OrderedResult<K, V> {
/* 798 */       private List<V> list = new ArrayList<V>(0);
/* 799 */       private Map<K, V> map = new HashMap<K, V>();
/*     */       
/* 801 */       public List<V> getList() { return this.list; } public Map<K, V> getMap() {
/* 802 */         return this.map;
/*     */       }
/*     */       public void add(K key, V value) {
/* 805 */         this.list.add(value);
/* 806 */         this.map.put(key, value);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\TypeEvaluator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */