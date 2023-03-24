/*     */ package com.sun.xml.bind.v2.runtime.reflect.opt;
/*     */ 
/*     */ import com.sun.xml.bind.Util;
/*     */ import com.sun.xml.bind.v2.bytecode.ClassTailor;
/*     */ import java.io.InputStream;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class AccessorInjector
/*     */ {
/*  55 */   private static final Logger logger = Util.getClassLogger();
/*     */   
/*  57 */   protected static final boolean noOptimize = (Util.getSystemProperty(ClassTailor.class.getName() + ".noOptimize") != null);
/*     */ 
/*     */   
/*     */   static {
/*  61 */     if (noOptimize) {
/*  62 */       logger.info("The optimized code generation is disabled");
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
/*     */   public static Class<?> prepare(Class beanClass, String templateClassName, String newClassName, String... replacements) {
/*  74 */     if (noOptimize) {
/*  75 */       return null;
/*     */     }
/*     */     try {
/*  78 */       ClassLoader cl = SecureLoader.getClassClassLoader(beanClass);
/*  79 */       if (cl == null) return null;
/*     */       
/*  81 */       Class<?> c = null;
/*  82 */       synchronized (AccessorInjector.class) {
/*  83 */         c = Injector.find(cl, newClassName);
/*  84 */         if (c == null) {
/*  85 */           byte[] image = tailor(templateClassName, newClassName, replacements);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  91 */           if (image == null)
/*  92 */             return null; 
/*  93 */           c = Injector.inject(cl, newClassName, image);
/*     */         } 
/*     */       } 
/*  96 */       return c;
/*  97 */     } catch (SecurityException e) {
/*     */       
/*  99 */       logger.log(Level.INFO, "Unable to create an optimized TransducedAccessor ", e);
/* 100 */       return null;
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
/*     */   private static byte[] tailor(String templateClassName, String newClassName, String... replacements) {
/*     */     InputStream resource;
/* 119 */     if (CLASS_LOADER != null) {
/* 120 */       resource = CLASS_LOADER.getResourceAsStream(templateClassName + ".class");
/*     */     } else {
/* 122 */       resource = ClassLoader.getSystemResourceAsStream(templateClassName + ".class");
/* 123 */     }  if (resource == null) {
/* 124 */       return null;
/*     */     }
/* 126 */     return ClassTailor.tailor(resource, templateClassName, newClassName, replacements);
/*     */   }
/*     */   
/* 129 */   private static final ClassLoader CLASS_LOADER = SecureLoader.getClassClassLoader(AccessorInjector.class);
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\bind\v2\runtime\reflect\opt\AccessorInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */