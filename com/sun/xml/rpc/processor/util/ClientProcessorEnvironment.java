/*     */ package com.sun.xml.rpc.processor.util;
/*     */ 
/*     */ import com.sun.xml.rpc.processor.ProcessorNotificationListener;
/*     */ import com.sun.xml.rpc.processor.generator.Names;
/*     */ import com.sun.xml.rpc.util.JAXRPCClassFactory;
/*     */ import com.sun.xml.rpc.util.localization.Localizable;
/*     */ import java.io.File;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientProcessorEnvironment
/*     */   extends ProcessorEnvironmentBase
/*     */   implements ProcessorEnvironment
/*     */ {
/*     */   private OutputStream out;
/*     */   private PrintStream outprintstream;
/*     */   private ProcessorNotificationListener listener;
/*     */   private String classPath;
/*  72 */   private List generatedFiles = new ArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int nwarnings;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int nerrors;
/*     */ 
/*     */ 
/*     */   
/*     */   private int flags;
/*     */ 
/*     */ 
/*     */   
/*     */   private Names names;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientProcessorEnvironment(OutputStream out, String classPath, ProcessorNotificationListener listener) {
/*  96 */     this.out = out;
/*  97 */     this.classPath = classPath;
/*  98 */     this.listener = listener;
/*  99 */     this.flags = 0;
/*     */ 
/*     */     
/* 102 */     this.names = JAXRPCClassFactory.newInstance().createNames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFlags(int flags) {
/* 109 */     this.flags = flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFlags() {
/* 116 */     return this.flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getClassPath() {
/* 123 */     return this.classPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean verbose() {
/* 130 */     return ((this.flags & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addGeneratedFile(GeneratedFileInfo file) {
/* 138 */     this.generatedFiles.add(file);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator getGeneratedFiles() {
/* 145 */     return this.generatedFiles.iterator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteGeneratedFiles() {
/* 154 */     synchronized (this.generatedFiles) {
/* 155 */       Iterator<GeneratedFileInfo> iter = this.generatedFiles.iterator();
/* 156 */       while (iter.hasNext()) {
/* 157 */         File file = ((GeneratedFileInfo)iter.next()).getFile();
/* 158 */         if (file.getName().endsWith(".java")) {
/* 159 */           file.delete();
/*     */         }
/*     */       } 
/* 162 */       this.generatedFiles.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 170 */     this.listener = null;
/* 171 */     this.generatedFiles = null;
/*     */   }
/*     */   
/*     */   public void error(Localizable msg) {
/* 175 */     if (this.listener != null) {
/* 176 */       this.listener.onError(msg);
/*     */     }
/* 178 */     this.nerrors++;
/*     */   }
/*     */   
/*     */   public void warn(Localizable msg) {
/* 182 */     if (warnings()) {
/* 183 */       this.nwarnings++;
/* 184 */       if (this.listener != null) {
/* 185 */         this.listener.onWarning(msg);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void info(Localizable msg) {
/* 191 */     if (this.listener != null) {
/* 192 */       this.listener.onInfo(msg);
/*     */     }
/*     */   }
/*     */   
/*     */   public void printStackTrace(Throwable t) {
/* 197 */     if (this.outprintstream == null) {
/* 198 */       this.outprintstream = new PrintStream(this.out);
/*     */     }
/* 200 */     t.printStackTrace(this.outprintstream);
/*     */   }
/*     */   
/*     */   public Names getNames() {
/* 204 */     return this.names;
/*     */   }
/*     */   
/*     */   public int getErrorCount() {
/* 208 */     return this.nerrors;
/*     */   }
/*     */   
/*     */   public int getWarningCount() {
/* 212 */     return this.nwarnings;
/*     */   }
/*     */   
/*     */   private boolean warnings() {
/* 216 */     return ((this.flags & 0x2) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNames(Names names) {
/* 222 */     this.names = names;
/*     */   }
/*     */ }


/* Location:              C:\Users\carlos\Documents\InnoExtractor\Cyber Password Manager 1_0\{app}\cpm_1.0.jar!\com\sun\xml\rpc\processo\\util\ClientProcessorEnvironment.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */