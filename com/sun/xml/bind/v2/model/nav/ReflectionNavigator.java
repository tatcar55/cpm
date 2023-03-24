/*     */ package com.sun.xml.bind.v2.model.nav;
/*     */ 
/*     */ import com.sun.xml.bind.v2.runtime.Location;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.GenericDeclaration;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.lang.reflect.WildcardType;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ReflectionNavigator
/*     */   implements Navigator<Type, Class, Field, Method>
/*     */ {
/*     */   public Class getSuperClass(Class<Object> clazz) {
/*  73 */     if (clazz == Object.class) {
/*  74 */       return null;
/*     */     }
/*  76 */     Class<? super Object> sc = clazz.getSuperclass();
/*  77 */     if (sc == null) {
/*  78 */       sc = Object.class;
/*     */     }
/*  80 */     return sc;
/*     */   }
/*  82 */   private static final TypeVisitor<Type, Class> baseClassFinder = new TypeVisitor<Type, Class>()
/*     */     {
/*     */       public Type onClass(Class c, Class sup)
/*     */       {
/*  86 */         if (sup == c) {
/*  87 */           return sup;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*  92 */         Type sc = c.getGenericSuperclass();
/*  93 */         if (sc != null) {
/*  94 */           Type r = visit(sc, sup);
/*  95 */           if (r != null) {
/*  96 */             return r;
/*     */           }
/*     */         } 
/*     */         
/* 100 */         for (Type i : c.getGenericInterfaces()) {
/* 101 */           Type r = visit(i, sup);
/* 102 */           if (r != null) {
/* 103 */             return r;
/*     */           }
/*     */         } 
/*     */         
/* 107 */         return null;
/*     */       }
/*     */       
/*     */       public Type onParameterizdType(ParameterizedType p, Class sup) {
/* 111 */         Class raw = (Class)p.getRawType();
/* 112 */         if (raw == sup)
/*     */         {
/* 114 */           return p;
/*     */         }
/*     */         
/* 117 */         Type r = raw.getGenericSuperclass();
/* 118 */         if (r != null) {
/* 119 */           r = visit(bind(r, raw, p), sup);
/*     */         }
/* 121 */         if (r != null) {
/* 122 */           return r;
/*     */         }
/* 124 */         for (Type i : raw.getGenericInterfaces()) {
/* 125 */           r = visit(bind(i, raw, p), sup);
/* 126 */           if (r != null) {
/* 127 */             return r;
/*     */           }
/*     */         } 
/* 130 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public Type onGenericArray(GenericArrayType g, Class sup) {
/* 136 */         return null;
/*     */       }
/*     */       
/*     */       public Type onVariable(TypeVariable v, Class sup) {
/* 140 */         return visit(v.getBounds()[0], sup);
/*     */       }
/*     */ 
/*     */       
/*     */       public Type onWildcard(WildcardType w, Class sup) {
/* 145 */         return null;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       private Type bind(Type t, GenericDeclaration decl, ParameterizedType args) {
/* 157 */         return ReflectionNavigator.binder.visit(t, new ReflectionNavigator.BinderArg(decl, args.getActualTypeArguments()));
/*     */       }
/*     */     };
/*     */   
/*     */   private static class BinderArg
/*     */   {
/*     */     final TypeVariable[] params;
/*     */     final Type[] args;
/*     */     
/*     */     BinderArg(TypeVariable[] params, Type[] args) {
/* 167 */       this.params = params;
/* 168 */       this.args = args;
/* 169 */       assert params.length == args.length;
/*     */     }
/*     */     
/*     */     public BinderArg(GenericDeclaration decl, Type[] args) {
/* 173 */       this((TypeVariable[])decl.getTypeParameters(), args);
/*     */     }
/*     */     
/*     */     Type replace(TypeVariable v) {
/* 177 */       for (int i = 0; i < this.params.length; i++) {
/* 178 */         if (this.params[i].equals(v)) {
/* 179 */           return this.args[i];
/*     */         }
/*     */       } 
/* 182 */       return v;
/*     */     } }
/*     */   
/* 185 */   private static final TypeVisitor<Type, BinderArg> binder = new TypeVisitor<Type, BinderArg>()
/*     */     {
/*     */       public Type onClass(Class c, ReflectionNavigator.BinderArg args) {
/* 188 */         return c;
/*     */       }
/*     */       public Type onParameterizdType(ParameterizedType p, ReflectionNavigator.BinderArg args) {
/*     */         int j;
/* 192 */         Type[] params = p.getActualTypeArguments();
/*     */         
/* 194 */         boolean different = false;
/* 195 */         for (int i = 0; i < params.length; i++) {
/* 196 */           Type t = params[i];
/* 197 */           params[i] = visit(t, args);
/* 198 */           j = different | ((t != params[i]) ? 1 : 0);
/*     */         } 
/*     */         
/* 201 */         Type newOwner = p.getOwnerType();
/* 202 */         if (newOwner != null) {
/* 203 */           newOwner = visit(newOwner, args);
/*     */         }
/* 205 */         j |= (p.getOwnerType() != newOwner) ? 1 : 0;
/*     */         
/* 207 */         if (j == 0) {
/* 208 */           return p;
/*     */         }
/*     */         
/* 211 */         return new ParameterizedTypeImpl((Class)p.getRawType(), params, newOwner);
/*     */       }
/*     */       
/*     */       public Type onGenericArray(GenericArrayType g, ReflectionNavigator.BinderArg types) {
/* 215 */         Type c = visit(g.getGenericComponentType(), types);
/* 216 */         if (c == g.getGenericComponentType()) {
/* 217 */           return g;
/*     */         }
/*     */         
/* 220 */         return new GenericArrayTypeImpl(c);
/*     */       }
/*     */       
/*     */       public Type onVariable(TypeVariable v, ReflectionNavigator.BinderArg types) {
/* 224 */         return types.replace(v);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public Type onWildcard(WildcardType w, ReflectionNavigator.BinderArg types) {
/*     */         int j;
/* 231 */         Type[] lb = w.getLowerBounds();
/* 232 */         Type[] ub = w.getUpperBounds();
/* 233 */         boolean diff = false;
/*     */         int i;
/* 235 */         for (i = 0; i < lb.length; i++) {
/* 236 */           Type t = lb[i];
/* 237 */           lb[i] = visit(t, types);
/* 238 */           j = diff | ((t != lb[i]) ? 1 : 0);
/*     */         } 
/*     */         
/* 241 */         for (i = 0; i < ub.length; i++) {
/* 242 */           Type t = ub[i];
/* 243 */           ub[i] = visit(t, types);
/* 244 */           j |= (t != ub[i]) ? 1 : 0;
/*     */         } 
/*     */         
/* 247 */         if (j == 0) {
/* 248 */           return w;
/*     */         }
/*     */         
/* 251 */         return new WildcardTypeImpl(lb, ub);
/*     */       }
/*     */     };
/*     */   
/*     */   public Type getBaseClass(Type t, Class sup) {
/* 256 */     return baseClassFinder.visit(t, sup);
/*     */   }
/*     */   
/*     */   public String getClassName(Class clazz) {
/* 260 */     return clazz.getName();
/*     */   }
/*     */   
/*     */   public String getTypeName(Type type) {
/* 264 */     if (type instanceof Class) {
/* 265 */       Class c = (Class)type;
/* 266 */       if (c.isArray()) {
/* 267 */         return getTypeName(c.getComponentType()) + "[]";
/*     */       }
/* 269 */       return c.getName();
/*     */     } 
/* 271 */     return type.toString();
/*     */   }
/*     */   
/*     */   public String getClassShortName(Class clazz) {
/* 275 */     return clazz.getSimpleName();
/*     */   }
/*     */   
/*     */   public Collection<? extends Field> getDeclaredFields(Class clazz) {
/* 279 */     return Arrays.asList(clazz.getDeclaredFields());
/*     */   }
/*     */   
/*     */   public Field getDeclaredField(Class clazz, String fieldName) {
/*     */     try {
/* 284 */       return clazz.getDeclaredField(fieldName);
/* 285 */     } catch (NoSuchFieldException e) {
/* 286 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Collection<? extends Method> getDeclaredMethods(Class clazz) {
/* 291 */     return Arrays.asList(clazz.getDeclaredMethods());
/*     */   }
/*     */   
/*     */   public Class getDeclaringClassForField(Field field) {
/* 295 */     return field.getDeclaringClass();
/*     */   }
/*     */   
/*     */   public Class getDeclaringClassForMethod(Method method) {
/* 299 */     return method.getDeclaringClass();
/*     */   }
/*     */   
/*     */   public Type getFieldType(Field field) {
/* 303 */     if (field.getType().isArray()) {
/* 304 */       Class<?> c = field.getType().getComponentType();
/* 305 */       if (c.isPrimitive()) {
/* 306 */         return Array.newInstance(c, 0).getClass();
/*     */       }
/*     */     } 
/* 309 */     return fix(field.getGenericType());
/*     */   }
/*     */   
/*     */   public String getFieldName(Field field) {
/* 313 */     return field.getName();
/*     */   }
/*     */   
/*     */   public String getMethodName(Method method) {
/* 317 */     return method.getName();
/*     */   }
/*     */   
/*     */   public Type getReturnType(Method method) {
/* 321 */     return fix(method.getGenericReturnType());
/*     */   }
/*     */   
/*     */   public Type[] getMethodParameters(Method method) {
/* 325 */     return method.getGenericParameterTypes();
/*     */   }
/*     */   
/*     */   public boolean isStaticMethod(Method method) {
/* 329 */     return Modifier.isStatic(method.getModifiers());
/*     */   }
/*     */   
/*     */   public boolean isFinalMethod(Method method) {
/* 333 */     return Modifier.isFinal(method.getModifiers());
/*     */   }
/*     */   
/*     */   public boolean isSubClassOf(Type sub, Type sup) {
/* 337 */     return erasure(sup).isAssignableFrom(erasure(sub));
/*     */   }
/*     */   
/*     */   public Class ref(Class c) {
/* 341 */     return c;
/*     */   }
/*     */   
/*     */   public Class use(Class c) {
/* 345 */     return c;
/*     */   }
/*     */   
/*     */   public Class asDecl(Type t) {
/* 349 */     return erasure(t);
/*     */   }
/*     */   
/*     */   public Class asDecl(Class c) {
/* 353 */     return c;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 358 */   private static final TypeVisitor<Class, Void> eraser = new TypeVisitor<Class, Void>()
/*     */     {
/*     */       public Class onClass(Class c, Void v) {
/* 361 */         return c;
/*     */       }
/*     */ 
/*     */       
/*     */       public Class onParameterizdType(ParameterizedType p, Void v) {
/* 366 */         return visit(p.getRawType(), null);
/*     */       }
/*     */       
/*     */       public Class onGenericArray(GenericArrayType g, Void v) {
/* 370 */         return Array.newInstance(visit(g.getGenericComponentType(), null), 0).getClass();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public Class onVariable(TypeVariable tv, Void v) {
/* 376 */         return visit(tv.getBounds()[0], null);
/*     */       }
/*     */       
/*     */       public Class onWildcard(WildcardType w, Void v) {
/* 380 */         return visit(w.getUpperBounds()[0], null);
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Class<T> erasure(Type t) {
/* 400 */     return eraser.visit(t, null);
/*     */   }
/*     */   
/*     */   public boolean isAbstract(Class clazz) {
/* 404 */     return Modifier.isAbstract(clazz.getModifiers());
/*     */   }
/*     */   
/*     */   public boolean isFinal(Class clazz) {
/* 408 */     return Modifier.isFinal(clazz.getModifiers());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Type createParameterizedType(Class<?> rawType, Type... arguments) {
/* 415 */     return new ParameterizedTypeImpl(rawType, arguments, null);
/*     */   }
/*     */   
/*     */   public boolean isArray(Type t) {
/* 419 */     if (t instanceof Class) {
/* 420 */       Class c = (Class)t;
/* 421 */       return c.isArray();
/*     */     } 
/* 423 */     if (t instanceof GenericArrayType) {
/* 424 */       return true;
/*     */     }
/* 426 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isArrayButNotByteArray(Type t) {
/* 430 */     if (t instanceof Class) {
/* 431 */       Class<byte[]> c = (Class)t;
/* 432 */       return (c.isArray() && c != byte[].class);
/*     */     } 
/* 434 */     if (t instanceof GenericArrayType) {
/* 435 */       t = ((GenericArrayType)t).getGenericComponentType();
/* 436 */       return (t != byte.class);
/*     */     } 
/* 438 */     return false;
/*     */   }
/*     */   
/*     */   public Type getComponentType(Type t) {
/* 442 */     if (t instanceof Class) {
/* 443 */       Class c = (Class)t;
/* 444 */       return c.getComponentType();
/*     */     } 
/* 446 */     if (t instanceof GenericArrayType) {
/* 447 */       return ((GenericArrayType)t).getGenericComponentType();
/*     */     }
/*     */     
/* 450 */     throw new IllegalArgumentException();
/*     */   }
/*     */   
/*     */   public Type getTypeArgument(Type type, int i) {
/* 454 */     if (type instanceof ParameterizedType) {
/* 455 */       ParameterizedType p = (ParameterizedType)type;
/* 456 */       return fix(p.getActualTypeArguments()[i]);
/*     */     } 
/* 458 */     throw new IllegalArgumentException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isParameterizedType(Type type) {
/* 463 */     return type instanceof ParameterizedType;
/*     */   }
/*     */   
/*     */   public boolean isPrimitive(Type type) {
/* 467 */     if (type instanceof Class) {
/* 468 */       Class c = (Class)type;
/* 469 */       return c.isPrimitive();
/*     */     } 
/* 471 */     return false;
/*     */   }
/*     */   
/*     */   public Type getPrimitive(Class primitiveType) {
/* 475 */     assert primitiveType.isPrimitive();
/* 476 */     return primitiveType;
/*     */   }
/*     */   
/*     */   public Location getClassLocation(final Class clazz) {
/* 480 */     return new Location()
/*     */       {
/*     */         public String toString()
/*     */         {
/* 484 */           return clazz.getName();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public Location getFieldLocation(final Field field) {
/* 490 */     return new Location()
/*     */       {
/*     */         public String toString()
/*     */         {
/* 494 */           return field.toString();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public Location getMethodLocation(final Method method) {
/* 500 */     return new Location()
/*     */       {
/*     */         public String toString()
/*     */         {
/* 504 */           return method.toString();
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public boolean hasDefaultConstructor(Class c) {
/*     */     try {
/* 511 */       c.getDeclaredConstructor(new Class[0]);
/* 512 */       return true;
/* 513 */     } catch (NoSuchMethodException e) {
/* 514 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isStaticField(Field field) {
/* 519 */     return Modifier.isStatic(field.getModifiers());
/*     */   }
/*     */   
/*     */   public boolean isPublicMethod(Method method) {
/* 523 */     return Modifier.isPublic(method.getModifiers());
/*     */   }
/*     */   
/*     */   public boolean isPublicField(Field field) {
/* 527 */     return Modifier.isPublic(field.getModifiers());
/*     */   }
/*     */   
/*     */   public boolean isEnum(Class<?> c) {
/* 531 */     return Enum.class.isAssignableFrom(c);
/*     */   }
/*     */   
/*     */   public Field[] getEnumConstants(Class<Object> clazz) {
/*     */     try {
/* 536 */       Object[] values = clazz.getEnumConstants();
/* 537 */       Field[] fields = new Field[values.length];
/* 538 */       for (int i = 0; i < values.length; i++) {
/* 539 */         fields[i] = clazz.getField(((Enum<Enum>)values[i]).name());
/*     */       }
/* 541 */       return fields;
/* 542 */     } catch (NoSuchFieldException e) {
/*     */       
/* 544 */       throw new NoSuchFieldError(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public Type getVoidType() {
/* 549 */     return Void.class;
/*     */   }
/*     */   
/*     */   public String getPackageName(Class clazz) {
/* 553 */     String name = clazz.getName();
/* 554 */     int idx = name.lastIndexOf('.');
/* 555 */     if (idx < 0) {
/* 556 */       return "";
/*     */     }
/* 558 */     return name.substring(0, idx);
/*     */   }
/*     */ 
/*     */   
/*     */   public Class findClass(String className, Class referencePoint) {
/*     */     try {
/* 564 */       ClassLoader cl = SecureLoader.getClassClassLoader(referencePoint);
/* 565 */       if (cl == null) {
/* 566 */         cl = SecureLoader.getSystemClassLoader();
/*     */       }
/* 568 */       return cl.loadClass(className);
/* 569 */     } catch (ClassNotFoundException e) {
/* 570 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isBridgeMethod(Method method) {
/* 575 */     return method.isBridge();
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
/*     */   public boolean isOverriding(Method method, Class base) {
/* 589 */     String name = method.getName();
/* 590 */     Class[] params = method.getParameterTypes();
/*     */     
/* 592 */     while (base != null) {
/*     */       try {
/* 594 */         if (base.getDeclaredMethod(name, params) != null) {
/* 595 */           return true;
/*     */         }
/* 597 */       } catch (NoSuchMethodException e) {}
/*     */ 
/*     */ 
/*     */       
/* 601 */       base = base.getSuperclass();
/*     */     } 
/*     */     
/* 604 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isInterface(Class clazz) {
/* 608 */     return clazz.isInterface();
/*     */   }
/*     */   
/*     */   public boolean isTransient(Field f) {
/* 612 */     return Modifier.isTransient(f.getModifiers());
/*     */   }
/*     */   
/*     */   public boolean isInnerClass(Class clazz) {
/* 616 */     return (clazz.getEnclosingClass() != null && !Modifier.isStatic(clazz.getModifiers()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSameType(Type t1, Type t2) {
/* 621 */     return t1.equals(t2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Type fix(Type t) {
/* 631 */     if (!(t instanceof GenericArrayType)) {
/* 632 */       return t;
/*     */     }
/*     */     
/* 635 */     GenericArrayType gat = (GenericArrayType)t;
/* 636 */     if (gat.getGenericComponentType() instanceof Class) {
/* 637 */       Class<?> c = (Class)gat.getGenericComponentType();
/* 638 */       return Array.newInstance(c, 0).getClass();
/*     */     } 
/*     */     
/* 641 */     return t;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\model\nav\ReflectionNavigator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */