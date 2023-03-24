/*     */ package com.sun.xml.rpc.processor.util;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.generator.GeneratorException;
/*     */ import com.sun.xml.rpc.util.ClassNameInfo;
/*     */ import java.io.File;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DirectoryUtil
/*     */ {
/*     */   public static File getOutputDirectoryFor(String theClass, File rootDir, ProcessorEnvironment env) throws GeneratorException {
/*  44 */     File outputDir = null;
/*  45 */     String qualifiedClassName = theClass;
/*  46 */     String packagePath = null;
/*  47 */     String packageName = ClassNameInfo.getQualifier(qualifiedClassName);
/*  48 */     if (packageName == null) {
/*  49 */       packageName = "";
/*  50 */     } else if (packageName.length() > 0) {
/*  51 */       packagePath = packageName.replace('.', File.separatorChar);
/*     */     } 
/*     */ 
/*     */     
/*  55 */     if (rootDir != null) {
/*     */ 
/*     */       
/*  58 */       if (packagePath != null)
/*     */       {
/*     */         
/*  61 */         outputDir = new File(rootDir, packagePath);
/*     */ 
/*     */         
/*  64 */         ensureDirectory(outputDir, env);
/*     */       }
/*     */       else
/*     */       {
/*  68 */         outputDir = rootDir;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  73 */       String workingDirPath = System.getProperty("user.dir");
/*  74 */       File workingDir = new File(workingDirPath);
/*     */ 
/*     */       
/*  77 */       if (packagePath == null) {
/*     */ 
/*     */         
/*  80 */         outputDir = workingDir;
/*     */       }
/*     */       else {
/*     */         
/*  84 */         outputDir = new File(workingDir, packagePath);
/*     */ 
/*     */         
/*  87 */         ensureDirectory(outputDir, env);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  92 */     return outputDir;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void ensureDirectory(File dir, ProcessorEnvironment env) throws GeneratorException {
/*  98 */     if (!dir.exists()) {
/*  99 */       dir.mkdirs();
/* 100 */       if (!dir.exists())
/* 101 */         throw new GeneratorException("generator.cannot.create.dir", dir.getAbsolutePath()); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processo\\util\DirectoryUtil.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */