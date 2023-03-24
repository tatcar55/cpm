/*     */ package com.sun.xml.ws.model;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.xml.ws.WebServiceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Injector
/*     */ {
/*  60 */   private static final Logger LOGGER = Logger.getLogger(Injector.class.getName());
/*     */   
/*     */   private static final Method defineClass;
/*     */   private static final Method resolveClass;
/*     */   private static final Method getPackage;
/*     */   private static final Method definePackage;
/*     */   
/*     */   static {
/*     */     try {
/*  69 */       defineClass = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class });
/*  70 */       resolveClass = ClassLoader.class.getDeclaredMethod("resolveClass", new Class[] { Class.class });
/*  71 */       getPackage = ClassLoader.class.getDeclaredMethod("getPackage", new Class[] { String.class });
/*  72 */       definePackage = ClassLoader.class.getDeclaredMethod("definePackage", new Class[] { String.class, String.class, String.class, String.class, String.class, String.class, String.class, URL.class });
/*     */     
/*     */     }
/*  75 */     catch (NoSuchMethodException e) {
/*     */       
/*  77 */       throw new NoSuchMethodError(e.getMessage());
/*     */     } 
/*  79 */     AccessController.doPrivileged(new PrivilegedAction<Void>()
/*     */         {
/*     */           public Void run()
/*     */           {
/*  83 */             Injector.defineClass.setAccessible(true);
/*  84 */             Injector.resolveClass.setAccessible(true);
/*  85 */             Injector.getPackage.setAccessible(true);
/*  86 */             Injector.definePackage.setAccessible(true);
/*  87 */             return null;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static synchronized Class inject(ClassLoader cl, String className, byte[] image) {
/*     */     try {
/*  96 */       return cl.loadClass(className);
/*  97 */     } catch (ClassNotFoundException e) {
/*     */ 
/*     */       
/*     */       try {
/* 101 */         int packIndex = className.lastIndexOf('.');
/* 102 */         if (packIndex != -1) {
/* 103 */           String pkgname = className.substring(0, packIndex);
/*     */           
/* 105 */           Package pkg = (Package)getPackage.invoke(cl, new Object[] { pkgname });
/* 106 */           if (pkg == null) {
/* 107 */             definePackage.invoke(cl, new Object[] { pkgname, null, null, null, null, null, null, null });
/*     */           }
/*     */         } 
/*     */         
/* 111 */         Class c = (Class)defineClass.invoke(cl, new Object[] { className.replace('/', '.'), image, Integer.valueOf(0), Integer.valueOf(image.length) });
/* 112 */         resolveClass.invoke(cl, new Object[] { c });
/* 113 */         return c;
/* 114 */       } catch (IllegalAccessException illegalAccessException) {
/* 115 */         LOGGER.log(Level.FINE, "Unable to inject " + className, illegalAccessException);
/* 116 */         throw new WebServiceException(illegalAccessException);
/* 117 */       } catch (InvocationTargetException invocationTargetException) {
/* 118 */         LOGGER.log(Level.FINE, "Unable to inject " + className, invocationTargetException);
/* 119 */         throw new WebServiceException(invocationTargetException);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\model\Injector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */