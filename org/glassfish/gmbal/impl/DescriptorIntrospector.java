/*     */ package org.glassfish.gmbal.impl;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.AnnotatedElement;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.management.Descriptor;
/*     */ import org.glassfish.gmbal.DescriptorFields;
/*     */ import org.glassfish.gmbal.DescriptorKey;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DescriptorIntrospector
/*     */ {
/*     */   public static Descriptor descriptorForElement(ManagedObjectManagerInternal mom, AnnotatedElement elmt) {
/*  77 */     if (elmt == null) {
/*  78 */       return DescriptorUtility.EMPTY_DESCRIPTOR;
/*     */     }
/*     */     
/*  81 */     Collection<Annotation> annots = null;
/*  82 */     if (mom == null) {
/*  83 */       annots = Arrays.asList(elmt.getAnnotations());
/*     */     } else {
/*  85 */       annots = mom.getAnnotations(elmt);
/*     */     } 
/*     */     
/*  88 */     return descriptorForAnnotations(annots);
/*     */   }
/*     */   
/*     */   private static Descriptor descriptorForAnnotations(Collection<Annotation> annots) {
/*  92 */     if (annots.size() == 0) {
/*  93 */       return DescriptorUtility.EMPTY_DESCRIPTOR;
/*     */     }
/*  95 */     Map<String, Object> descriptorMap = new HashMap<String, Object>();
/*  96 */     for (Annotation a : annots) {
/*  97 */       if (a instanceof DescriptorFields) {
/*  98 */         addDescriptorFieldsToMap(descriptorMap, (DescriptorFields)a);
/*     */       }
/*     */       
/* 101 */       addAnnotationFieldsToMap(descriptorMap, a);
/*     */     } 
/*     */     
/* 104 */     if (descriptorMap.isEmpty()) {
/* 105 */       return DescriptorUtility.EMPTY_DESCRIPTOR;
/*     */     }
/* 107 */     return DescriptorUtility.makeDescriptor(descriptorMap);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void addDescriptorFieldsToMap(Map<String, Object> descriptorMap, DescriptorFields df) {
/* 113 */     for (String field : df.value()) {
/* 114 */       int eq = field.indexOf('=');
/* 115 */       if (eq < 0) {
/* 116 */         throw Exceptions.self.excForAddDescriptorFieldsToMap(field);
/*     */       }
/* 118 */       String name = field.substring(0, eq);
/* 119 */       String value = field.substring(eq + 1);
/* 120 */       addToMap(descriptorMap, name, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addAnnotationFieldsToMap(Map<String, Object> descriptorMap, Annotation a) {
/* 126 */     Class<? extends Annotation> c = a.annotationType();
/* 127 */     Method[] elements = c.getMethods();
/* 128 */     for (Method element : elements) {
/* 129 */       DescriptorKey key = element.<DescriptorKey>getAnnotation(DescriptorKey.class);
/* 130 */       if (key != null) {
/* 131 */         Object value; String name = key.value();
/*     */         
/*     */         try {
/* 134 */           value = element.invoke(a, new Object[0]);
/* 135 */         } catch (RuntimeException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 140 */           throw e;
/* 141 */         } catch (Exception e) {
/* 142 */           throw Exceptions.self.excForAddAnnotationFieldsToMap(e);
/*     */         } 
/* 144 */         if (!key.omitIfDefault() || !equals(value, element.getDefaultValue())) {
/*     */           
/* 146 */           value = annotationToField(value);
/* 147 */           addToMap(descriptorMap, name, value);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addToMap(Map<String, Object> descriptorMap, String name, Object value) {
/* 155 */     Object oldValue = descriptorMap.put(name, value);
/* 156 */     if (oldValue != null && !equals(oldValue, value)) {
/* 157 */       throw Exceptions.self.excForAddToMap(name, value, oldValue);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object annotationToField(Object x) {
/* 166 */     if (x == null) {
/* 167 */       return null;
/*     */     }
/* 169 */     if (x instanceof Number || x instanceof String || x instanceof Character || x instanceof Boolean || x instanceof String[])
/*     */     {
/*     */       
/* 172 */       return x;
/*     */     }
/*     */ 
/*     */     
/* 176 */     Class<?> c = x.getClass();
/* 177 */     if (c.isArray()) {
/* 178 */       if (c.getComponentType().isPrimitive()) {
/* 179 */         return x;
/*     */       }
/* 181 */       Object[] xx = (Object[])x;
/* 182 */       String[] ss = new String[xx.length];
/* 183 */       for (int i = 0; i < xx.length; i++) {
/* 184 */         ss[i] = (String)annotationToField(xx[i]);
/*     */       }
/* 186 */       return ss;
/*     */     } 
/* 188 */     if (x instanceof Class) {
/* 189 */       return ((Class)x).getName();
/*     */     }
/* 191 */     if (x instanceof Enum) {
/* 192 */       return ((Enum)x).name();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 200 */     if (Proxy.isProxyClass(c)) {
/* 201 */       c = c.getInterfaces()[0];
/*     */     }
/* 203 */     throw Exceptions.self.excForAnnotationToField(c.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean equals(Object x, Object y) {
/* 210 */     return Arrays.deepEquals(new Object[] { x }, new Object[] { y });
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\glassfish\gmbal\impl\DescriptorIntrospector.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */