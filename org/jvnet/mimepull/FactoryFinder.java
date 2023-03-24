/*     */ package org.jvnet.mimepull;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.UnsupportedEncodingException;
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
/*     */ class FactoryFinder
/*     */ {
/*  52 */   private static ClassLoader cl = FactoryFinder.class.getClassLoader();
/*     */ 
/*     */   
/*     */   static Object find(String factoryId) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
/*  56 */     String systemProp = System.getProperty(factoryId);
/*  57 */     if (systemProp != null) {
/*  58 */       return newInstance(systemProp);
/*     */     }
/*     */     
/*  61 */     String providerName = findJarServiceProviderName(factoryId);
/*  62 */     if (providerName != null && providerName.trim().length() > 0) {
/*  63 */       return newInstance(providerName);
/*     */     }
/*     */     
/*  66 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   static Object newInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
/*  71 */     Class<?> providerClass = cl.loadClass(className);
/*  72 */     Object instance = providerClass.newInstance();
/*  73 */     return instance;
/*     */   }
/*     */   
/*     */   private static String findJarServiceProviderName(String factoryId) {
/*  77 */     String factoryClassName, serviceId = "META-INF/services/" + factoryId;
/*     */     
/*  79 */     InputStream is = cl.getResourceAsStream(serviceId);
/*     */     
/*  81 */     if (is == null) {
/*  82 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  86 */     BufferedReader rd = null;
/*     */     try {
/*     */       try {
/*  89 */         rd = new BufferedReader(new InputStreamReader(is, "UTF-8"));
/*  90 */       } catch (UnsupportedEncodingException e) {
/*  91 */         rd = new BufferedReader(new InputStreamReader(is));
/*     */       } 
/*     */       try {
/*  94 */         factoryClassName = rd.readLine();
/*  95 */       } catch (IOException x) {
/*  96 */         return null;
/*     */       } 
/*     */     } finally {
/*  99 */       if (rd != null) {
/*     */         try {
/* 101 */           rd.close();
/* 102 */         } catch (IOException ex) {
/* 103 */           Logger.getLogger(FactoryFinder.class.getName()).log(Level.INFO, (String)null, ex);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 108 */     return factoryClassName;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\org\jvnet\mimepull\FactoryFinder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */