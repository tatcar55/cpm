/*     */ package com.sun.xml.rpc.processor.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ProcessorEnvironmentBase
/*     */   implements ProcessorEnvironment
/*     */ {
/*  42 */   protected URLClassLoader classLoader = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URLClassLoader getClassLoader() {
/*  48 */     if (this.classLoader == null) {
/*  49 */       this.classLoader = new URLClassLoader(pathToURLs(getClassPath().toString()), getClass().getClassLoader());
/*     */     }
/*     */ 
/*     */     
/*  53 */     return this.classLoader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static URL[] pathToURLs(String path) {
/*  64 */     StringTokenizer st = new StringTokenizer(path, File.pathSeparator);
/*  65 */     URL[] urls = new URL[st.countTokens()];
/*  66 */     int count = 0;
/*  67 */     while (st.hasMoreTokens()) {
/*  68 */       URL url = fileToURL(new File(st.nextToken()));
/*  69 */       if (url != null) {
/*  70 */         urls[count++] = url;
/*     */       }
/*     */     } 
/*  73 */     if (urls.length != count) {
/*  74 */       URL[] tmp = new URL[count];
/*  75 */       System.arraycopy(urls, 0, tmp, 0, count);
/*  76 */       urls = tmp;
/*     */     } 
/*  78 */     return urls;
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
/*     */   public static URL fileToURL(File file) {
/*     */     try {
/*  91 */       str = file.getCanonicalPath();
/*  92 */     } catch (IOException e) {
/*  93 */       str = file.getAbsolutePath();
/*     */     } 
/*  95 */     String str = str.replace(File.separatorChar, '/');
/*  96 */     if (!str.startsWith("/")) {
/*  97 */       str = "/" + str;
/*     */     }
/*     */ 
/*     */     
/* 101 */     if (!file.isFile()) {
/* 102 */       str = str + "/";
/*     */     }
/*     */     try {
/* 105 */       return new URL("file", "", str);
/* 106 */     } catch (MalformedURLException e) {
/* 107 */       throw new IllegalArgumentException("file");
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processo\\util\ProcessorEnvironmentBase.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */