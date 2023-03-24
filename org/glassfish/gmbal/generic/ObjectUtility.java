/*     */ package org.glassfish.gmbal.generic;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Enumeration;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import javax.management.ObjectName;
/*     */ import javax.management.openmbean.ArrayType;
/*     */ import javax.management.openmbean.CompositeData;
/*     */ import javax.management.openmbean.CompositeType;
/*     */ import javax.management.openmbean.TabularData;
/*     */ import javax.management.openmbean.TabularType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ObjectUtility
/*     */ {
/*     */   private static class ClassMap
/*     */   {
/*  90 */     List<Pair<Class<?>, ObjectUtility.ObjectPrinter>> data = new ArrayList<Pair<Class<?>, ObjectUtility.ObjectPrinter>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ObjectUtility.ObjectPrinter get(Class<?> cls) {
/*  99 */       for (Pair<Class<?>, ObjectUtility.ObjectPrinter> pair : this.data) {
/* 100 */         if (((Class)pair.first()).isAssignableFrom(cls)) {
/* 101 */           return pair.second();
/*     */         }
/*     */       } 
/*     */       
/* 105 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void put(Class<?> cls, ObjectUtility.ObjectPrinter obj) {
/* 112 */       Pair<Class<?>, ObjectUtility.ObjectPrinter> pair = new Pair<Class<?>, ObjectUtility.ObjectPrinter>(cls, obj);
/*     */       
/* 114 */       this.data.add(pair);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/* 119 */   private ObjectPrinter generalObjectPrinter = new ObjectPrinter()
/*     */     {
/*     */       public void print(IdentityHashMap printed, ObjectWriter buff, Object obj)
/*     */       {
/* 123 */         ObjectUtility.this.handleObject(printed, buff, obj);
/*     */       }
/*     */       public boolean alwaysPrint() {
/* 126 */         return false;
/*     */       }
/*     */     };
/* 129 */   private ObjectPrinter arrayPrinter = new ObjectPrinter()
/*     */     {
/*     */       public void print(IdentityHashMap printed, ObjectWriter buff, Object obj)
/*     */       {
/* 133 */         ObjectUtility.this.handleArray(printed, buff, obj);
/*     */       }
/*     */       public boolean alwaysPrint() {
/* 136 */         return false;
/*     */       }
/*     */     };
/* 139 */   private static ObjectPrinter propertiesPrinter = new ObjectPrinter()
/*     */     {
/*     */       public void print(IdentityHashMap printed, ObjectWriter buff, Object obj)
/*     */       {
/* 143 */         if (!(obj instanceof Properties)) {
/* 144 */           throw new Error();
/*     */         }
/*     */         
/* 147 */         Properties props = (Properties)obj;
/* 148 */         Enumeration<?> keys = props.propertyNames();
/* 149 */         while (keys.hasMoreElements()) {
/* 150 */           String key = (String)keys.nextElement();
/* 151 */           String value = props.getProperty(key);
/* 152 */           buff.startElement();
/* 153 */           buff.append(key);
/* 154 */           buff.append("=");
/* 155 */           buff.append(value);
/* 156 */           buff.endElement();
/*     */         } 
/*     */       }
/*     */       public boolean alwaysPrint() {
/* 160 */         return true;
/*     */       }
/*     */     };
/* 163 */   private ObjectPrinter collectionPrinter = new ObjectPrinter()
/*     */     {
/*     */       public void print(IdentityHashMap printed, ObjectWriter buff, Object obj)
/*     */       {
/* 167 */         if (!(obj instanceof Collection)) {
/* 168 */           throw new Error();
/*     */         }
/*     */         
/* 171 */         Collection coll = (Collection)obj;
/* 172 */         Iterator iter = coll.iterator();
/* 173 */         while (iter.hasNext()) {
/* 174 */           Object element = iter.next();
/* 175 */           buff.startElement();
/* 176 */           ObjectUtility.this.objectToStringHelper(printed, buff, element);
/* 177 */           buff.endElement();
/*     */         } 
/*     */       }
/*     */       public boolean alwaysPrint() {
/* 181 */         return false;
/*     */       }
/*     */     };
/* 184 */   private ObjectPrinter mapPrinter = new ObjectPrinter()
/*     */     {
/*     */       public void print(IdentityHashMap printed, ObjectWriter buff, Object obj)
/*     */       {
/* 188 */         if (!(obj instanceof Map)) {
/* 189 */           throw new Error();
/*     */         }
/*     */         
/* 192 */         Map map = (Map)obj;
/* 193 */         Iterator<Map.Entry> iter = map.entrySet().iterator();
/* 194 */         while (iter.hasNext()) {
/* 195 */           Map.Entry entry = iter.next();
/* 196 */           buff.startElement();
/* 197 */           ObjectUtility.this.objectToStringHelper(printed, buff, entry.getKey());
/* 198 */           buff.append("=>");
/* 199 */           ObjectUtility.this.objectToStringHelper(printed, buff, entry.getValue());
/* 200 */           buff.endElement();
/*     */         } 
/*     */       }
/*     */       public boolean alwaysPrint() {
/* 204 */         return false;
/*     */       }
/*     */     };
/* 207 */   private static ObjectPrinter toStringPrinter = new ObjectPrinter()
/*     */     {
/*     */       public void print(IdentityHashMap printed, ObjectWriter buff, Object obj)
/*     */       {
/* 211 */         buff.append(obj.toString());
/*     */       }
/*     */       public boolean alwaysPrint() {
/* 214 */         return true;
/*     */       }
/*     */     };
/*     */   
/* 218 */   private final Object[][] CLASS_MAP_DATA = new Object[][] { { Integer.class, toStringPrinter }, { BigInteger.class, toStringPrinter }, { BigDecimal.class, toStringPrinter }, { String.class, toStringPrinter }, { StringBuffer.class, toStringPrinter }, { StringBuilder.class, toStringPrinter }, { Long.class, toStringPrinter }, { Short.class, toStringPrinter }, { Byte.class, toStringPrinter }, { Character.class, toStringPrinter }, { Float.class, toStringPrinter }, { Double.class, toStringPrinter }, { Boolean.class, toStringPrinter }, { Date.class, toStringPrinter }, { ObjectName.class, toStringPrinter }, { CompositeData.class, toStringPrinter }, { CompositeType.class, toStringPrinter }, { TabularData.class, toStringPrinter }, { TabularType.class, toStringPrinter }, { ArrayType.class, toStringPrinter }, { Class.class, toStringPrinter }, { Method.class, toStringPrinter }, { Thread.class, toStringPrinter }, { AtomicInteger.class, toStringPrinter }, { AtomicLong.class, toStringPrinter }, { AtomicBoolean.class, toStringPrinter }, { Properties.class, propertiesPrinter }, { Collection.class, this.collectionPrinter }, { Map.class, this.mapPrinter } };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ClassMap classMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isIndenting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int initialLevel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int increment;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 255 */   private static ObjectUtility standard = new ObjectUtility(true, 0, 4);
/* 256 */   private static ObjectUtility compact = new ObjectUtility(false, 0, 4);
/*     */ 
/*     */ 
/*     */   
/*     */   public ObjectUtility(boolean isIndenting, int initialLevel, int increment) {
/* 261 */     this.isIndenting = isIndenting;
/* 262 */     this.initialLevel = initialLevel;
/* 263 */     this.increment = increment;
/* 264 */     this.classMap = new ClassMap();
/* 265 */     for (Object[] pair : this.CLASS_MAP_DATA) {
/* 266 */       Class<?> key = (Class)pair[0];
/* 267 */       ObjectPrinter value = (ObjectPrinter)pair[1];
/* 268 */       this.classMap.put(key, value);
/*     */     } 
/*     */   }
/*     */   
/*     */   public ObjectUtility useToString(Class cls) {
/* 273 */     this.classMap.put(cls, toStringPrinter);
/* 274 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String defaultObjectToString(Object object) {
/* 285 */     return standard.objectToString(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String compactObjectToString(Object object) {
/* 295 */     return compact.objectToString(object);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String objectToString(Object obj) {
/* 306 */     IdentityHashMap<Object, Object> printed = new IdentityHashMap<Object, Object>();
/* 307 */     ObjectWriter result = ObjectWriter.make(this.isIndenting, this.initialLevel, this.increment);
/*     */     
/* 309 */     objectToStringHelper(printed, result, obj);
/* 310 */     return result.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   ObjectPrinter classify(Class cls) {
/* 318 */     if (cls.isEnum())
/* 319 */       return toStringPrinter; 
/* 320 */     if (cls.isArray()) {
/* 321 */       return this.arrayPrinter;
/*     */     }
/* 323 */     ObjectPrinter result = this.classMap.get(cls);
/* 324 */     if (result == null) {
/* 325 */       return this.generalObjectPrinter;
/*     */     }
/* 327 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void objectToStringHelper(IdentityHashMap printed, ObjectWriter result, Object obj) {
/* 336 */     if (obj == null) {
/* 337 */       result.append("null");
/* 338 */       result.endElement();
/*     */     } else {
/* 340 */       Class<?> cls = obj.getClass();
/* 341 */       ObjectPrinter opr = classify(cls);
/* 342 */       result.startObject(obj);
/*     */       try {
/* 344 */         if (opr.alwaysPrint()) {
/* 345 */           opr.print(printed, result, obj);
/*     */         }
/* 347 */         else if (printed.keySet().contains(obj)) {
/* 348 */           result.append("*VISITED*");
/*     */         } else {
/* 350 */           printed.put(obj, null);
/* 351 */           opr.print(printed, result, obj);
/*     */         } 
/*     */       } finally {
/*     */         
/* 355 */         result.endObject();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkPackageAccess(Class cls) {
/* 363 */     SecurityManager sm = System.getSecurityManager();
/* 364 */     if (sm != null) {
/* 365 */       String cname = cls.getName().replace('/', '.');
/* 366 */       if (cname.startsWith("[")) {
/* 367 */         int lastBracket = cname.lastIndexOf("[") + 2;
/* 368 */         if (lastBracket > 1 && lastBracket < cname.length()) {
/* 369 */           cname = cname.substring(lastBracket);
/*     */         }
/*     */         
/* 372 */         int lastDot = cname.lastIndexOf('.');
/* 373 */         if (lastDot != -1) {
/* 374 */           String pname = cname.substring(0, lastDot);
/* 375 */           sm.checkPackageAccess(pname);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private Field[] getDeclaredFields(Class cls) {
/* 382 */     checkPackageAccess(cls);
/* 383 */     return cls.getDeclaredFields();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleObject(IdentityHashMap printed, ObjectWriter result, Object obj) {
/* 390 */     Class<?> cls = obj.getClass();
/*     */     
/*     */     try {
/* 393 */       SecurityManager security = System.getSecurityManager();
/* 394 */       List<Field> allFields = new ArrayList<Field>();
/* 395 */       Class<?> current = cls;
/* 396 */       while (!current.equals(Object.class)) {
/*     */         Field[] fields;
/*     */ 
/*     */         
/* 400 */         if (security != null && !Modifier.isPublic(current.getModifiers())) {
/* 401 */           fields = new Field[0];
/*     */         } else {
/* 403 */           fields = getDeclaredFields(current);
/*     */         } 
/*     */         
/* 406 */         for (Field fld : fields) {
/* 407 */           allFields.add(fld);
/*     */         }
/*     */         
/* 410 */         current = current.getSuperclass();
/*     */       } 
/*     */       
/* 413 */       for (Field fld : allFields) {
/* 414 */         int modifiers = fld.getModifiers();
/* 415 */         if (fld.isAnnotationPresent((Class)DumpIgnore.class)) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 423 */         if (Modifier.isStatic(modifiers) || (
/* 424 */           security != null && 
/* 425 */           !Modifier.isPublic(modifiers))) {
/*     */           continue;
/*     */         }
/*     */         
/* 429 */         result.startElement();
/* 430 */         result.append(fld.getName());
/* 431 */         result.append("=");
/*     */ 
/*     */ 
/*     */         
/* 435 */         AccessController.doPrivileged(new PrivilegedAction() {
/*     */               public Object run() {
/* 437 */                 fld.setAccessible(true);
/* 438 */                 return null;
/*     */               }
/*     */             });
/*     */ 
/*     */         
/* 443 */         Object value = fld.get(obj);
/* 444 */         if (fld.isAnnotationPresent((Class)DumpToString.class)) {
/* 445 */           toStringPrinter.print(printed, result, value);
/*     */         } else {
/* 447 */           objectToStringHelper(printed, result, value);
/*     */         } 
/*     */         
/* 450 */         result.endElement();
/*     */       }
/*     */     
/* 453 */     } catch (IllegalArgumentException ex) {
/*     */       
/* 455 */       result.append(obj.toString());
/* 456 */     } catch (IllegalAccessException ex) {
/*     */       
/* 458 */       result.append(obj.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void handleArray(IdentityHashMap printed, ObjectWriter result, Object obj) {
/* 465 */     Class<?> compClass = obj.getClass().getComponentType();
/* 466 */     if (compClass == boolean.class) {
/* 467 */       boolean[] arr = (boolean[])obj;
/* 468 */       for (int ctr = 0; ctr < arr.length; ctr++) {
/* 469 */         result.startElement();
/* 470 */         result.append(arr[ctr]);
/* 471 */         result.endElement();
/*     */       } 
/* 473 */     } else if (compClass == byte.class) {
/* 474 */       byte[] arr = (byte[])obj;
/* 475 */       for (int ctr = 0; ctr < arr.length; ctr++) {
/* 476 */         result.startElement();
/* 477 */         result.append((short)arr[ctr]);
/* 478 */         result.endElement();
/*     */       } 
/* 480 */     } else if (compClass == short.class) {
/* 481 */       short[] arr = (short[])obj;
/* 482 */       for (int ctr = 0; ctr < arr.length; ctr++) {
/* 483 */         result.startElement();
/* 484 */         result.append(arr[ctr]);
/* 485 */         result.endElement();
/*     */       } 
/* 487 */     } else if (compClass == int.class) {
/* 488 */       int[] arr = (int[])obj;
/* 489 */       for (int ctr = 0; ctr < arr.length; ctr++) {
/* 490 */         result.startElement();
/* 491 */         result.append(arr[ctr]);
/* 492 */         result.endElement();
/*     */       } 
/* 494 */     } else if (compClass == long.class) {
/* 495 */       long[] arr = (long[])obj;
/* 496 */       for (int ctr = 0; ctr < arr.length; ctr++) {
/* 497 */         result.startElement();
/* 498 */         result.append(arr[ctr]);
/* 499 */         result.endElement();
/*     */       } 
/* 501 */     } else if (compClass == char.class) {
/* 502 */       char[] arr = (char[])obj;
/* 503 */       for (int ctr = 0; ctr < arr.length; ctr++) {
/* 504 */         result.startElement();
/* 505 */         result.append(arr[ctr]);
/* 506 */         result.endElement();
/*     */       } 
/* 508 */     } else if (compClass == float.class) {
/* 509 */       float[] arr = (float[])obj;
/* 510 */       for (int ctr = 0; ctr < arr.length; ctr++) {
/* 511 */         result.startElement();
/* 512 */         result.append(arr[ctr]);
/* 513 */         result.endElement();
/*     */       } 
/* 515 */     } else if (compClass == double.class) {
/* 516 */       double[] arr = (double[])obj;
/* 517 */       for (int ctr = 0; ctr < arr.length; ctr++) {
/* 518 */         result.startElement();
/* 519 */         result.append(arr[ctr]);
/* 520 */         result.endElement();
/*     */       } 
/*     */     } else {
/* 523 */       Object[] arr = (Object[])obj;
/* 524 */       for (int ctr = 0; ctr < arr.length; ctr++) {
/* 525 */         result.startElement();
/* 526 */         objectToStringHelper(printed, result, arr[ctr]);
/* 527 */         result.endElement();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static interface ObjectPrinter {
/*     */     void print(IdentityHashMap param1IdentityHashMap, ObjectWriter param1ObjectWriter, Object param1Object);
/*     */     
/*     */     boolean alwaysPrint();
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\generic\ObjectUtility.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */