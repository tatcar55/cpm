/*     */ package org.glassfish.gmbal.typelib;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.glassfish.gmbal.generic.DumpToString;
/*     */ import org.glassfish.gmbal.generic.MethodMonitor;
/*     */ import org.glassfish.gmbal.generic.MethodMonitorFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DeclarationFactory
/*     */ {
/*     */   private static boolean DEBUG = false;
/*  63 */   private static MethodMonitor mm = MethodMonitorFactory.makeStandard(DeclarationFactory.class);
/*     */ 
/*     */   
/*  66 */   private static final Map<EvaluatedType, EvaluatedArrayType> arrayMap = new HashMap<EvaluatedType, EvaluatedArrayType>();
/*     */ 
/*     */   
/*  69 */   private static final Map<String, EvaluatedClassDeclaration> simpleClassMap = new HashMap<String, EvaluatedClassDeclaration>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized EvaluatedArrayType egat(EvaluatedType compType) {
/*  81 */     EvaluatedArrayType result = arrayMap.get(compType);
/*  82 */     if (result == null) {
/*  83 */       mm.enter(DEBUG, "egat", new Object[] { "compType", compType });
/*     */       
/*     */       try {
/*  86 */         result = new EvaluatedArrayTypeImpl(compType);
/*  87 */         arrayMap.put(compType, result);
/*     */       } finally {
/*  89 */         mm.exit(DEBUG, result);
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized EvaluatedClassDeclaration ecdecl(int modifiers, String name, List<EvaluatedClassDeclaration> inheritance, List<EvaluatedMethodDeclaration> methods, List<EvaluatedFieldDeclaration> fields, Class cls, boolean isImmutable) {
/* 102 */     EvaluatedClassDeclaration result = null;
/* 103 */     if ((cls.getTypeParameters()).length == 0)
/*     */     {
/* 105 */       result = simpleClassMap.get(name);
/*     */     }
/*     */     
/* 108 */     if (result == null) {
/* 109 */       mm.enter(DEBUG, "ecdecl", new Object[] { name });
/*     */       
/*     */       try {
/* 112 */         result = new EvaluatedClassDeclarationImpl(modifiers, name, inheritance, methods, fields, cls, isImmutable);
/*     */         
/* 114 */         if (result.simpleClass()) {
/* 115 */           simpleClassMap.put(name, result);
/*     */         }
/*     */       } finally {
/* 118 */         mm.exit(DEBUG, result);
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized EvaluatedFieldDeclaration efdecl(EvaluatedClassDeclaration ecdecl, int modifiers, EvaluatedType ftype, String name, Field field) {
/* 129 */     mm.enter(DEBUG, "efdecl", new Object[] { name, ftype });
/*     */     
/* 131 */     EvaluatedFieldDeclaration result = null;
/*     */     
/*     */     try {
/* 134 */       result = new EvaluatedFieldDeclarationImpl(ecdecl, modifiers, ftype, name, field);
/*     */     } finally {
/*     */       
/* 137 */       mm.exit(DEBUG, result);
/*     */     } 
/*     */     
/* 140 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized EvaluatedMethodDeclaration emdecl(EvaluatedClassDeclaration ecdecl, int modifiers, EvaluatedType rtype, String name, List<EvaluatedType> ptypes, Method method) {
/* 148 */     mm.enter(DEBUG, "emdecl", new Object[] { name, ptypes });
/*     */     
/* 150 */     EvaluatedMethodDeclaration result = null;
/*     */     
/*     */     try {
/* 153 */       result = new EvaluatedMethodDeclarationImpl(ecdecl, modifiers, rtype, name, ptypes, method);
/*     */     } finally {
/*     */       
/* 156 */       mm.exit(DEBUG, result);
/*     */     } 
/*     */     
/* 159 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EvaluatedClassDeclaration ecdecl(int modifiers, String name, Class cls) {
/* 164 */     return ecdecl(modifiers, name, cls, false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static EvaluatedClassDeclaration ecdecl(int modifiers, String name, Class cls, boolean isImmutable) {
/* 170 */     return ecdecl(modifiers, name, new ArrayList<EvaluatedClassDeclaration>(0), new ArrayList<EvaluatedMethodDeclaration>(0), new ArrayList<EvaluatedFieldDeclaration>(0), cls, isImmutable);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EvaluatedArrayTypeImpl
/*     */     extends EvaluatedArrayTypeBase
/*     */   {
/*     */     private EvaluatedType compType;
/*     */ 
/*     */     
/*     */     public EvaluatedArrayTypeImpl(EvaluatedType compType) {
/* 181 */       this.compType = compType;
/*     */     }
/*     */     public EvaluatedType componentType() {
/* 184 */       return this.compType;
/*     */     }
/*     */     public String name() {
/* 187 */       return this.compType.name() + "[]";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EvaluatedFieldDeclarationImpl
/*     */     extends EvaluatedFieldDeclarationBase
/*     */   {
/*     */     private final EvaluatedClassDeclaration container;
/*     */     
/*     */     private final int modifiers;
/*     */     
/*     */     private final EvaluatedType fieldType;
/*     */     
/*     */     private final String name;
/*     */     
/*     */     @DumpToString
/*     */     private final Field field;
/*     */     
/*     */     public EvaluatedFieldDeclarationImpl(EvaluatedClassDeclaration cdecl, int modifiers, EvaluatedType fieldType, String name, Field field) {
/* 207 */       this.container = cdecl;
/* 208 */       this.modifiers = modifiers;
/* 209 */       this.fieldType = fieldType;
/* 210 */       this.name = name;
/* 211 */       this.field = field;
/*     */     }
/*     */     
/*     */     public <T extends Annotation> T annotation(Class<T> annotationType) {
/* 215 */       if (this.field == null) {
/* 216 */         throw new UnsupportedOperationException("Not supported in constructed ClassDeclaration.");
/*     */       }
/*     */       
/* 219 */       return this.field.getAnnotation(annotationType);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Annotation> annotations() {
/* 224 */       if (this.field == null) {
/* 225 */         throw new UnsupportedOperationException("Not supported in constructed ClassDeclaration.");
/*     */       }
/*     */       
/* 228 */       return Arrays.asList(this.field.getAnnotations());
/*     */     }
/*     */     
/*     */     public String name() {
/* 232 */       return this.name;
/*     */     } public int modifiers() {
/* 234 */       return this.modifiers;
/*     */     } public AnnotatedElement element() {
/* 236 */       return this.field;
/*     */     } public AccessibleObject accessible() {
/* 238 */       return this.field;
/*     */     } public EvaluatedType fieldType() {
/* 240 */       return this.fieldType;
/*     */     } public EvaluatedClassDeclaration containingClass() {
/* 242 */       return this.container;
/*     */     }
/*     */     public Field field() {
/* 245 */       return this.field;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EvaluatedMethodDeclarationImpl
/*     */     extends EvaluatedMethodDeclarationBase
/*     */   {
/*     */     private final EvaluatedClassDeclaration container;
/*     */     
/*     */     private final int modifiers;
/*     */     
/*     */     private final EvaluatedType rtype;
/*     */     
/*     */     private final String name;
/*     */     private final List<EvaluatedType> ptypes;
/*     */     @DumpToString
/*     */     private final Method method;
/*     */     
/*     */     public EvaluatedMethodDeclarationImpl(EvaluatedClassDeclaration cdecl, int modifiers, EvaluatedType rtype, String name, List<EvaluatedType> ptypes, Method method) {
/* 265 */       this.container = cdecl;
/* 266 */       this.modifiers = modifiers;
/* 267 */       this.rtype = rtype;
/* 268 */       this.name = name;
/* 269 */       this.ptypes = ptypes;
/* 270 */       this.method = method;
/*     */     }
/*     */     public String name() {
/* 273 */       return this.name;
/*     */     } public int modifiers() {
/* 275 */       return this.modifiers;
/*     */     } public List<EvaluatedType> parameterTypes() {
/* 277 */       return this.ptypes;
/*     */     } public EvaluatedType returnType() {
/* 279 */       return this.rtype;
/*     */     } public EvaluatedClassDeclaration containingClass() {
/* 281 */       return this.container;
/*     */     } public Method method() {
/* 283 */       return this.method;
/*     */     }
/*     */     public <T extends Annotation> T annotation(Class<T> annotationType) {
/* 286 */       if (this.method == null) {
/* 287 */         throw new UnsupportedOperationException("Not supported in constructed ClassDeclaration.");
/*     */       }
/*     */       
/* 290 */       return this.method.getAnnotation(annotationType);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Annotation> annotations() {
/* 295 */       if (this.method == null) {
/* 296 */         throw new UnsupportedOperationException("Not supported in constructed ClassDeclaration.");
/*     */       }
/*     */       
/* 299 */       return Arrays.asList(this.method.getAnnotations());
/*     */     }
/*     */     
/*     */     public AnnotatedElement element() {
/* 303 */       return this.method;
/*     */     } public AccessibleObject accessible() {
/* 305 */       return this.method;
/*     */     } }
/*     */   
/*     */   private static class EvaluatedClassDeclarationImpl extends EvaluatedClassDeclarationBase {
/*     */     private final int modifiers;
/*     */     private final String name;
/*     */     private List<EvaluatedClassDeclaration> inheritance;
/*     */     private List<EvaluatedMethodDeclaration> methods;
/*     */     @DumpToString
/*     */     private final Class cls;
/* 315 */     private List<EvaluatedType> instantiations = new ArrayList<EvaluatedType>(0);
/*     */ 
/*     */     
/*     */     private boolean simpleClass;
/*     */ 
/*     */     
/*     */     private boolean frozen;
/*     */ 
/*     */     
/*     */     private List<EvaluatedFieldDeclaration> fields;
/*     */ 
/*     */     
/*     */     private boolean isImmutable;
/*     */ 
/*     */     
/*     */     public EvaluatedClassDeclarationImpl(int modifiers, String name, List<EvaluatedClassDeclaration> inheritance, List<EvaluatedMethodDeclaration> methods, List<EvaluatedFieldDeclaration> fields, Class cls, boolean isImmutable) {
/* 331 */       this.modifiers = modifiers;
/* 332 */       this.name = name;
/* 333 */       this.inheritance = inheritance;
/* 334 */       this.methods = methods;
/* 335 */       this.fields = fields;
/* 336 */       this.cls = cls;
/* 337 */       this.simpleClass = ((cls.getTypeParameters()).length == 0);
/* 338 */       this.frozen = false;
/* 339 */       this.isImmutable = isImmutable;
/*     */     }
/*     */     
/*     */     public void freeze() {
/* 343 */       this.frozen = true;
/*     */     }
/*     */     
/*     */     public boolean simpleClass() {
/* 347 */       return this.simpleClass;
/*     */     }
/*     */     
/*     */     public <T extends Annotation> T annotation(Class<T> annotationType) {
/* 351 */       if (this.cls == null) {
/* 352 */         throw new UnsupportedOperationException("Not supported in constructed ClassDeclaration.");
/*     */       }
/*     */       
/* 355 */       return (T)this.cls.getAnnotation(annotationType);
/*     */     }
/*     */ 
/*     */     
/*     */     public List<Annotation> annotations() {
/* 360 */       if (this.cls == null) {
/* 361 */         throw new UnsupportedOperationException("Not supported in constructed ClassDeclaration.");
/*     */       }
/*     */       
/* 364 */       return Arrays.asList(this.cls.getAnnotations());
/*     */     }
/*     */     
/*     */     public String name() {
/* 368 */       return this.name;
/*     */     } public int modifiers() {
/* 370 */       return this.modifiers;
/*     */     } public Class cls() {
/* 372 */       return this.cls;
/*     */     } public List<EvaluatedMethodDeclaration> methods() {
/* 374 */       return this.methods;
/*     */     } public List<EvaluatedClassDeclaration> inheritance() {
/* 376 */       return this.inheritance;
/*     */     }
/*     */     private void checkFrozen() {
/* 379 */       if (this.frozen) {
/* 380 */         throw new IllegalStateException("Cannot modify frozen instance for " + this);
/*     */       }
/*     */     }
/*     */     
/*     */     public void methods(List<EvaluatedMethodDeclaration> meths) {
/* 385 */       checkFrozen();
/* 386 */       this.methods = meths;
/*     */     }
/*     */     
/*     */     public void inheritance(List<EvaluatedClassDeclaration> inh) {
/* 390 */       checkFrozen();
/* 391 */       this.inheritance = inh;
/*     */     }
/*     */     public AnnotatedElement element() {
/* 394 */       return this.cls;
/*     */     } public List<EvaluatedType> instantiations() {
/* 396 */       return this.instantiations;
/*     */     }
/*     */     public void instantiations(List<EvaluatedType> arg) {
/* 399 */       checkFrozen();
/* 400 */       if (this.simpleClass) {
/* 401 */         throw new IllegalStateException("Cannot add instantiations to a class with no type args");
/*     */       }
/*     */       
/* 404 */       this.instantiations = arg;
/*     */     }
/*     */     
/*     */     public List<EvaluatedFieldDeclaration> fields() {
/* 408 */       return this.fields;
/*     */     }
/*     */     public void fields(List<EvaluatedFieldDeclaration> arg) {
/* 411 */       checkFrozen();
/* 412 */       this.fields = arg;
/*     */     }
/*     */     
/*     */     public boolean isImmutable() {
/* 416 */       return this.isImmutable;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\typelib\DeclarationFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */