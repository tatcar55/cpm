/*    */ package com.sun.xml.ws.model;
/*    */ 
/*    */ import com.sun.xml.ws.api.databinding.MetadataReader;
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.Method;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReflectAnnotationReader
/*    */   implements MetadataReader
/*    */ {
/*    */   public Annotation[] getAnnotations(Method m) {
/* 59 */     return m.getAnnotations();
/*    */   }
/*    */   
/*    */   public Annotation[][] getParameterAnnotations(final Method method) {
/* 63 */     return AccessController.<Annotation[][]>doPrivileged((PrivilegedAction)new PrivilegedAction<Annotation[][]>() {
/*    */           public Annotation[][] run() {
/* 65 */             return method.getParameterAnnotations();
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public <A extends Annotation> A getAnnotation(final Class<A> annType, final Method m) {
/* 71 */     return (A)AccessController.<Annotation>doPrivileged(new PrivilegedAction<A>() {
/*    */           public A run() {
/* 73 */             return m.getAnnotation(annType);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public <A extends Annotation> A getAnnotation(final Class<A> annType, final Class<?> cls) {
/* 79 */     return (A)AccessController.<Annotation>doPrivileged(new PrivilegedAction<A>() {
/*    */           public A run() {
/* 81 */             return (A)cls.getAnnotation(annType);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public Annotation[] getAnnotations(final Class<?> cls) {
/* 87 */     return AccessController.<Annotation[]>doPrivileged((PrivilegedAction)new PrivilegedAction<Annotation[]>() {
/*    */           public Annotation[] run() {
/* 89 */             return cls.getAnnotations();
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void getProperties(Map<String, Object> prop, Class<?> cls) {}
/*    */   
/*    */   public void getProperties(Map<String, Object> prop, Method method) {}
/*    */   
/*    */   public void getProperties(Map<String, Object> prop, Method method, int pos) {}
/*    */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\ReflectAnnotationReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */