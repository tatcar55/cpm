/*     */ package org.glassfish.gmbal.generic;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Algorithms
/*     */ {
/*     */   public static <T> List<T> list(T... arg) {
/*  63 */     List<T> result = new ArrayList<T>();
/*  64 */     for (T obj : arg) {
/*  65 */       result.add(obj);
/*     */     }
/*  67 */     return result;
/*     */   }
/*     */   
/*     */   public static <S, T> Pair<S, T> pair(S first, T second) {
/*  71 */     return new Pair<S, T>(first, second);
/*     */   }
/*     */   
/*     */   public static <K, V> Map<K, V> map(Pair<K, V>... pairs) {
/*  75 */     Map<K, V> result = new HashMap<K, V>();
/*  76 */     for (Pair<K, V> pair : pairs) {
/*  77 */       result.put(pair.first(), pair.second());
/*     */     }
/*  79 */     return result;
/*     */   }
/*     */   
/*     */   public static <A, R> UnaryFunction<A, R> mapToFunction(final Map<A, R> map) {
/*  83 */     return new UnaryFunction<A, R>() {
/*     */         public R evaluate(A arg) {
/*  85 */           return (R)map.get(arg);
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static interface Action<T> {
/*     */     T run() throws Exception; }
/*     */   
/*     */   public static <A, R> void map(Collection<A> arg, Collection<R> result, UnaryFunction<A, R> func) {
/*  94 */     for (A a : arg) {
/*  95 */       R newArg = func.evaluate(a);
/*  96 */       if (newArg != null) {
/*  97 */         result.add(newArg);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, A, R> Map<K, R> map(Map<K, A> arg, UnaryFunction<A, R> func) {
/* 105 */     Map<K, R> result = new HashMap<K, R>();
/* 106 */     for (Map.Entry<K, A> entry : arg.entrySet()) {
/* 107 */       result.put(entry.getKey(), func.evaluate(entry.getValue()));
/*     */     }
/*     */ 
/*     */     
/* 111 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A, R> List<R> map(List<A> arg, UnaryFunction<A, R> func) {
/* 117 */     List<R> result = new ArrayList<R>();
/* 118 */     map(arg, result, func);
/* 119 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A> Predicate<A> and(final Predicate<A> arg1, final Predicate<A> arg2) {
/* 126 */     return new Predicate<A>() {
/*     */         public boolean evaluate(A arg) {
/* 128 */           return (arg1.evaluate(arg) && arg2.evaluate(arg));
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A> Predicate<A> or(final Predicate<A> arg1, final Predicate<A> arg2) {
/* 137 */     return new Predicate<A>() {
/*     */         public boolean evaluate(A arg) {
/* 139 */           return (arg1.evaluate(arg) || arg2.evaluate(arg));
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Predicate<T> FALSE(Class<T> cls) {
/* 147 */     return new Predicate<T>() {
/*     */         public boolean evaluate(T arg) {
/* 149 */           return false;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Predicate<T> TRUE(Class<T> cls) {
/* 157 */     return new Predicate<T>() {
/*     */         public boolean evaluate(T arg) {
/* 159 */           return true;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A> Predicate<A> not(final Predicate<A> arg1) {
/* 167 */     return new Predicate<A>() {
/*     */         public boolean evaluate(A arg) {
/* 169 */           return !arg1.evaluate(arg);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static <A> void filter(List<A> arg, List<A> result, final Predicate<A> predicate) {
/* 177 */     UnaryFunction<A, A> filter = new UnaryFunction<A, A>() {
/*     */         public A evaluate(A arg) {
/* 179 */           return predicate.evaluate(arg) ? arg : null; }
/*     */       };
/* 181 */     map(arg, result, filter);
/*     */   }
/*     */   
/*     */   public static <A> List<A> filter(List<A> arg, Predicate<A> predicate) {
/* 185 */     List<A> result = new ArrayList<A>();
/* 186 */     filter(arg, result, predicate);
/* 187 */     return result;
/*     */   }
/*     */   
/*     */   public static <A> A find(List<A> arg, Predicate<A> predicate) {
/* 191 */     for (A a : arg) {
/* 192 */       if (predicate.evaluate(a)) {
/* 193 */         return a;
/*     */       }
/*     */     } 
/*     */     
/* 197 */     return null;
/*     */   }
/*     */   
/*     */   public static <A, R> R fold(List<A> list, R initial, BinaryFunction<R, A, R> func) {
/* 201 */     R result = initial;
/* 202 */     for (A elem : list) {
/* 203 */       result = func.evaluate(result, elem);
/*     */     }
/* 205 */     return result;
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
/*     */   public static <S, T> List<T> flatten(List<S> list, final UnaryFunction<S, List<T>> map) {
/* 219 */     return fold(list, new ArrayList<T>(), (BinaryFunction)new BinaryFunction<List<S>, List<T>, List<S>>()
/*     */         {
/*     */           public List<T> evaluate(List<T> arg1, S arg2) {
/* 222 */             arg1.addAll(map.evaluate(arg2));
/* 223 */             return arg1;
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
/*     */ 
/*     */   
/*     */   public static <T> T getFirst(Collection<T> list, Runnable handleEmptyList) {
/* 237 */     Iterator<T> i$ = list.iterator(); if (i$.hasNext()) { T element = i$.next();
/* 238 */       return element; }
/*     */ 
/*     */     
/* 241 */     handleEmptyList.run();
/* 242 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List convertToList(Object arg) {
/* 249 */     List<Integer> result = new ArrayList();
/* 250 */     if (arg != null) {
/* 251 */       Class<?> cls = arg.getClass();
/* 252 */       if (cls.isArray()) {
/* 253 */         Class<?> cclass = cls.getComponentType();
/* 254 */         if (cclass.equals(int.class)) {
/* 255 */           for (int elem : (int[])arg) {
/* 256 */             result.add(Integer.valueOf(elem));
/*     */           }
/* 258 */         } else if (cclass.equals(byte.class)) {
/* 259 */           for (byte elem : (byte[])arg) {
/* 260 */             result.add(Byte.valueOf(elem));
/*     */           }
/* 262 */         } else if (cclass.equals(boolean.class)) {
/* 263 */           for (boolean elem : (boolean[])arg) {
/* 264 */             result.add(Boolean.valueOf(elem));
/*     */           }
/* 266 */         } else if (cclass.equals(char.class)) {
/* 267 */           for (char elem : (char[])arg) {
/* 268 */             result.add(Character.valueOf(elem));
/*     */           }
/* 270 */         } else if (cclass.equals(short.class)) {
/* 271 */           for (short elem : (short[])arg) {
/* 272 */             result.add(Short.valueOf(elem));
/*     */           }
/* 274 */         } else if (cclass.equals(long.class)) {
/* 275 */           for (long elem : (long[])arg) {
/* 276 */             result.add(Long.valueOf(elem));
/*     */           }
/* 278 */         } else if (cclass.equals(float.class)) {
/* 279 */           for (float elem : (float[])arg) {
/* 280 */             result.add(Float.valueOf(elem));
/*     */           }
/* 282 */         } else if (cclass.equals(double.class)) {
/* 283 */           for (double elem : (double[])arg) {
/* 284 */             result.add(Double.valueOf(elem));
/*     */           }
/*     */         } else {
/* 287 */           return Arrays.asList((Object[])arg);
/*     */         } 
/*     */       } else {
/* 290 */         result.add(arg);
/* 291 */         return result;
/*     */       } 
/*     */     } 
/*     */     
/* 295 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String convertToString(Object arg) {
/* 304 */     if (arg == null) {
/* 305 */       return "<NULL>";
/*     */     }
/*     */     
/* 308 */     Class<?> cls = arg.getClass();
/* 309 */     if (cls.isArray()) {
/* 310 */       Class<?> cclass = cls.getComponentType();
/* 311 */       if (cclass.equals(int.class)) {
/* 312 */         return Arrays.toString((int[])arg);
/*     */       }
/* 314 */       if (cclass.equals(byte.class)) {
/* 315 */         return Arrays.toString((byte[])arg);
/*     */       }
/* 317 */       if (cclass.equals(boolean.class)) {
/* 318 */         return Arrays.toString((boolean[])arg);
/*     */       }
/* 320 */       if (cclass.equals(char.class)) {
/* 321 */         return Arrays.toString((char[])arg);
/*     */       }
/* 323 */       if (cclass.equals(short.class)) {
/* 324 */         return Arrays.toString((short[])arg);
/*     */       }
/* 326 */       if (cclass.equals(long.class)) {
/* 327 */         return Arrays.toString((long[])arg);
/*     */       }
/* 329 */       if (cclass.equals(float.class)) {
/* 330 */         return Arrays.toString((float[])arg);
/*     */       }
/* 332 */       if (cclass.equals(double.class)) {
/* 333 */         return Arrays.toString((double[])arg);
/*     */       }
/* 335 */       return Arrays.toString((Object[])arg);
/*     */     } 
/* 337 */     return arg.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<Method> getDeclaredMethods(final Class<?> cls) {
/* 342 */     SecurityManager sman = System.getSecurityManager();
/* 343 */     if (sman == null) {
/* 344 */       return Arrays.asList(cls.getDeclaredMethods());
/*     */     }
/* 346 */     return AccessController.<List<Method>>doPrivileged(new PrivilegedAction<List<Method>>()
/*     */         {
/*     */           public List<Method> run() {
/* 349 */             return Arrays.asList(cls.getDeclaredMethods());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 359 */   private static Set<String> annotationMethods = new HashSet<String>(); static {
/* 360 */     for (Method m : getDeclaredMethods(Annotation.class)) {
/* 361 */       annotationMethods.add(m.getName());
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
/*     */   public static Map<String, Object> getAnnotationValues(Annotation ann, boolean convertArraysToLists) {
/* 379 */     Map<String, Object> result = new HashMap<String, Object>();
/* 380 */     for (Method m : getDeclaredMethods(ann.getClass())) {
/* 381 */       String name = m.getName();
/* 382 */       if (!annotationMethods.contains(name)) {
/* 383 */         Object<String, Object> value = null;
/*     */         
/*     */         try {
/* 386 */           value = (Object<String, Object>)m.invoke(ann, new Object[0]);
/* 387 */         } catch (IllegalAccessException ex) {
/* 388 */           Logger.getLogger(Algorithms.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 389 */         } catch (IllegalArgumentException ex) {
/* 390 */           Logger.getLogger(Algorithms.class.getName()).log(Level.SEVERE, (String)null, ex);
/* 391 */         } catch (InvocationTargetException ex) {
/* 392 */           Logger.getLogger(Algorithms.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */         } 
/*     */         
/* 395 */         if (value != null) {
/* 396 */           Class<?> valueClass = value.getClass();
/* 397 */           if (valueClass.isAnnotation()) {
/* 398 */             value = (Object<String, Object>)getAnnotationValues((Annotation)value, convertArraysToLists);
/*     */           }
/* 400 */           else if (convertArraysToLists && valueClass.isArray()) {
/* 401 */             value = (Object<String, Object>)convertToList(value);
/*     */           } 
/*     */         } 
/*     */         
/* 405 */         result.put(name, value);
/*     */       } 
/*     */     } 
/*     */     
/* 409 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static <T> PrivilegedAction<T> makePrivilegedAction(final Action<T> act) {
/* 419 */     return new PrivilegedAction<T>() {
/*     */         public T run() {
/*     */           try {
/* 422 */             return act.run();
/* 423 */           } catch (RuntimeException exc) {
/* 424 */             throw exc;
/* 425 */           } catch (Exception ex) {
/* 426 */             throw new RuntimeException(ex);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static <T> T doPrivileged(Action<T> func) {
/* 433 */     SecurityManager sman = System.getSecurityManager();
/*     */     try {
/* 435 */       if (sman == null) {
/* 436 */         return func.run();
/*     */       }
/* 438 */       return AccessController.doPrivileged(makePrivilegedAction(func));
/*     */     
/*     */     }
/* 441 */     catch (RuntimeException rex) {
/* 442 */       throw rex;
/* 443 */     } catch (Exception ex) {
/* 444 */       throw new RuntimeException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\Algorithms.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */