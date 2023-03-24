/*     */ package com.sun.xml.wss;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import javax.security.auth.callback.CallbackHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class XWSSProcessorFactory
/*     */ {
/*     */   public static final String XWSS_PROCESSOR_FACTORY_PROPERTY = "com.sun.xml.wss.XWSSProcessorFactory";
/*     */   public static final String DEFAULT_XWSS_PROCESSOR_FACTORY = "com.sun.xml.wss.impl.misc.XWSSProcessorFactory2_0Impl";
/*     */   
/*     */   public static XWSSProcessorFactory newInstance() throws XWSSecurityException {
/*     */     ClassLoader classLoader;
/*     */     try {
/*  79 */       classLoader = Thread.currentThread().getContextClassLoader();
/*  80 */     } catch (Exception x) {
/*  81 */       throw new XWSSecurityException(x.toString(), x);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/*  86 */       String systemProp = System.getProperty("com.sun.xml.wss.XWSSProcessorFactory");
/*     */       
/*  88 */       if (systemProp != null) {
/*  89 */         return (XWSSProcessorFactory)newInstance(systemProp, classLoader);
/*     */       }
/*  91 */       return (XWSSProcessorFactory)newInstance("com.sun.xml.wss.impl.misc.XWSSProcessorFactory2_0Impl", classLoader);
/*     */     }
/*  93 */     catch (SecurityException se) {
/*  94 */       throw new XWSSecurityException(se.toString(), se);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract XWSSProcessor createProcessorForSecurityConfiguration(InputStream paramInputStream, CallbackHandler paramCallbackHandler) throws XWSSecurityException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object newInstance(String className, ClassLoader classLoader) throws XWSSecurityException {
/*     */     try {
/*     */       Class<?> spiClass;
/* 138 */       if (classLoader == null) {
/* 139 */         spiClass = Class.forName(className);
/*     */       } else {
/* 141 */         spiClass = classLoader.loadClass(className);
/*     */       } 
/* 143 */       return spiClass.newInstance();
/* 144 */     } catch (ClassNotFoundException x) {
/* 145 */       throw new XWSSecurityException("Processor Factory " + className + " not found", x);
/*     */     }
/* 147 */     catch (Exception x) {
/* 148 */       throw new XWSSecurityException("Processor Factory " + className + " could not be instantiated: " + x, x);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\wss\XWSSProcessorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */