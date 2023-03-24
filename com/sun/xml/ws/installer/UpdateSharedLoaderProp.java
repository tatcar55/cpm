/*     */ package com.sun.xml.ws.installer;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Properties;
/*     */ import org.apache.tools.ant.BuildException;
/*     */ import org.apache.tools.ant.Task;
/*     */ import org.apache.tools.ant.taskdefs.Replace;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class UpdateSharedLoaderProp
/*     */   extends Task
/*     */ {
/*     */   String tomcatLib;
/*     */   String catalinaProps;
/*     */   
/*     */   public void settomcatLib(String tomcatLib) {
/*  60 */     this.tomcatLib = tomcatLib;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCatalinaProps(String catalinaProps) {
/*  65 */     this.catalinaProps = catalinaProps;
/*     */   }
/*     */ 
/*     */   
/*     */   public void execute() {
/*  70 */     if (this.tomcatLib == null)
/*     */     {
/*  72 */       this.tomcatLib = new String("${catalina.home}/shared/lib");
/*     */     }
/*  74 */     if (this.catalinaProps == null) {
/*  75 */       throw new BuildException("No catalinaProps set!");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  80 */     String jarWildcard = new String("/*.jar");
/*  81 */     String metroJars = new String(this.tomcatLib + jarWildcard);
/*     */     
/*  83 */     FileInputStream propsFileStream = null;
/*  84 */     Properties properties = new Properties();
/*     */     try {
/*  86 */       propsFileStream = new FileInputStream(this.catalinaProps);
/*  87 */       if (propsFileStream != null) {
/*  88 */         properties.load(propsFileStream);
/*  89 */         propsFileStream.close();
/*     */       } 
/*  91 */     } catch (IOException e) {
/*  92 */       throw new BuildException("Missing or inaccessible " + this.catalinaProps + " file");
/*     */     } 
/*     */     
/*  95 */     String sharedLoader = properties.getProperty("shared.loader");
/*  96 */     String newSharedLoader = null;
/*  97 */     if (sharedLoader == null || sharedLoader.length() == 0) {
/*  98 */       newSharedLoader = metroJars;
/*     */     } else {
/* 100 */       if (sharedLoader.contains(metroJars)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 106 */       newSharedLoader = new String(metroJars + "," + sharedLoader);
/*     */     } 
/*     */     
/* 109 */     Replace replace = new Replace();
/* 110 */     File propsFile = new File(this.catalinaProps);
/* 111 */     replace.setProject(getProject());
/* 112 */     replace.setFile(propsFile);
/* 113 */     replace.setToken("shared.loader=" + sharedLoader);
/* 114 */     replace.setValue("shared.loader=" + newSharedLoader);
/*     */     try {
/* 116 */       replace.execute();
/*     */     }
/* 118 */     catch (Exception e) {
/* 119 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\ws\installer\UpdateSharedLoaderProp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */